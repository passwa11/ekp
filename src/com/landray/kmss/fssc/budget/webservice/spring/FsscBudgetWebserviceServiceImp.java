package com.landray.kmss.fssc.budget.webservice.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budget.constant.FsscBudgetConstant;
import com.landray.kmss.fssc.budget.model.FsscBudgetData;
import com.landray.kmss.fssc.budget.service.IFsscBudgetExecuteService;
import com.landray.kmss.fssc.budget.webservice.IFsscBudgetWebserviceService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetMatchService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/api/fssc-budget/fsscBudgetWebserviceService", method = RequestMethod.POST)
@RestApi(docUrl = "/fssc/budget/fssc_budget_webservice/fsscBudgetWebserviceHelp.jsp", name = "fsscBudgetRestService", resourceKey = "fssc-budget:fsscBudgetWebserviceService.title")
public class FsscBudgetWebserviceServiceImp implements IFsscBudgetWebserviceService{

	protected IFsscCommonBudgetMatchService fsscBudgetMatchService;
	
	public void setFsscBudgetMatchService(IFsscCommonBudgetMatchService fsscBudgetMatchService) {
		this.fsscBudgetMatchService = fsscBudgetMatchService;
	}
	
	protected IFsscCommonBudgetOperatService fsscBudgetOperatService;
	
	public void setFsscBudgetOperatService(IFsscCommonBudgetOperatService fsscBudgetOperatService) {
		this.fsscBudgetOperatService = fsscBudgetOperatService;
	}	
	
	protected IFsscBudgetExecuteService fsscBudgetExecuteService;
	
	public void setFsscBudgetExecuteService(IFsscBudgetExecuteService fsscBudgetExecuteService) {
		this.fsscBudgetExecuteService = fsscBudgetExecuteService;
	}

	/***************************************************
	 * 功能：匹配预算
	 * ************************************************/
	@ResponseBody
	@RequestMapping(value = "/matchBudget")
	@Override
	public String matchBudget(@RequestBody String messageJson) {
		JSONObject jsonObject=new JSONObject();
		try {
			JSONObject obj=JSONObject.fromObject(messageJson);
			obj.put("queryType", "match");
			jsonObject=fsscBudgetMatchService.matchFsscBudget(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
	/***************************************************
	 * 功能：添加预算执行
	 * ************************************************/
	@ResponseBody
	@RequestMapping(value = "/addBudgetExecute")
	@Override
	public String addBudgetExecute(@RequestBody String executeJson) {
		JSONObject jsonObject=new JSONObject();
		try {
			String fdKnots=EopBasedataFsscUtil.getSwitchValue("fdKnots");
	    	if(StringUtil.isNull(fdKnots)){
	    		fdKnots=EopBasedataConstant.FSSC_FDKNOTS_NO; //未设置结转，默认不接转
	    	}
	    	JSONArray jsonArray=JSONArray.fromObject(executeJson);
	    	for (int k=0,size=jsonArray.size();k<size;k++) {
	    		JSONObject param=jsonArray.getJSONObject(k);
	    		param.put("queryType", "execute");
				JSONObject json=fsscBudgetMatchService.matchFsscBudget(param);
				JSONArray budgetArray=json.getJSONArray("data");
				for(int i=0;i<budgetArray.size();i++){
					JSONObject obj=budgetArray.getJSONObject(i);
					if(FsscBudgetConstant.FSSC_BUDGET_RULE_FIXED.equals(obj.get("fdApply"))
							||FsscBudgetConstant.FSSC_BUDGET_RULE_ROLL.equals(obj.get("fdApply"))&&EopBasedataConstant.FSSC_FDKNOTS_YES.equals(fdKnots)){
						//固定预算或者滚动但是启用了结转，不需要滚动前面的预算
						String fdBudgetId=(obj.containsKey("fdBudgetId")&&obj.get("fdBudgetId")!=null)?obj.getString("fdBudgetId"):"";
						Double fdMoney=(param.containsKey("fdMoney")&&param.get("fdMoney")!=null)?param.getDouble("fdMoney"):0.0;
						addExecute(param,fdBudgetId,fdMoney);
					}else if(FsscBudgetConstant.FSSC_BUDGET_RULE_ROLL.equals(obj.get("fdApply"))&&EopBasedataConstant.FSSC_FDKNOTS_NO.equals(fdKnots)){
						Double canUse=(obj.containsKey("fdCanUseAmount")&&obj.get("fdCanUseAmount")!=null)?obj.getDouble("fdCanUseAmount"):0.0;
						Double fdMoney=(param.containsKey("fdMoney")&&param.get("fdMoney")!=null)?param.getDouble("fdMoney"):0.0;
						Double tempMoney=fdMoney;  //剩余需执行金额
						if(fdMoney!=0){//操作金额不为0
							if(canUse-tempMoney>=0){//说明当前预算金额足够操作
								String fdBudgetId=(obj.containsKey("fdBudgetId")&&obj.get("fdBudgetId")!=null)?obj.getString("fdBudgetId"):"";
								addExecute(param,fdBudgetId,tempMoney);
							}else{//当前月预算不足扣减，循环往前面的月份、季度扣减
								//当前期间全部扣除
								String fdBudgetId=(obj.containsKey("fdBudgetId")&&obj.get("fdBudgetId")!=null)?obj.getString("fdBudgetId"):"";
								addExecute(param,fdBudgetId,canUse);
								tempMoney=tempMoney-canUse;
								if(StringUtil.isNotNull(fdBudgetId)){
									FsscBudgetData data=(FsscBudgetData) fsscBudgetExecuteService.findByPrimaryKey(fdBudgetId, FsscBudgetData.class, true);
									JSONArray rtnArray=fsscBudgetExecuteService.getBudgets(data,param.optString("fdModelId"),param.optString("fdDetailId"));
									Double currMoney=0.0;  //当前需执行的金额
									//获取当前预算前面可滚动的预算（不包含当前期间预算）
									for (int n = 0,len=rtnArray.size(); n <len ; n++) {
										JSONObject budgetObj=rtnArray.getJSONObject(n);
										//获取当前预算的可使用金额
										Double fdCanUse=budgetObj.getDouble("canUseAmount");
										if(fdCanUse-tempMoney>=0){//预算可使用足够执行
											currMoney=tempMoney;   //当前执行金额为剩余执行金额
										}else{//当前预算可使用不足
											currMoney=fdCanUse;   //整条预算金额执行
										}
										String childBudgetId=(budgetObj.containsKey("fdBudgetId")&&budgetObj.get("fdBudgetId")!=null)?budgetObj.getString("fdBudgetId"):"";
										addExecute(param,childBudgetId,currMoney);
										tempMoney=tempMoney-fdCanUse;
										if(tempMoney<=0){  //说明金额已经全部占用完毕
											break;
										}
									}
									//循环所有预算后，执行金额还是大于0，说明所有预算都被扣完，还剩金额，直接扣在当月
									if(tempMoney>0){
										addExecute(param,fdBudgetId,tempMoney);
									}
								}
							}
						}
					}
				}
			}
			jsonObject.put("result","success");
		} catch (Exception e) {
			jsonObject.put("result","failure");
			jsonObject.put("message", e.toString());
		}
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/addExecute")
	public void addExecute(@RequestBody JSONObject param, String fdBudgetId, Double fdMoney) throws Exception{
		JSONObject infoMessage=new JSONObject();
		infoMessage.put("fdModelId",(param.containsKey("fdModelId")&&param.get("fdModelId")!=null)?param.get("fdModelId"):"" );//单据ID
		infoMessage.put("fdBudgetId", fdBudgetId);//扣减预算ID
		infoMessage.put("fdModelName",(param.containsKey("fdModelName")&&param.get("fdModelName")!=null)?param.get("fdModelName"):"" );//域名
		String fdType=(param.containsKey("fdType")&&param.get("fdType")!=null)?param.getString("fdType"):"";
		infoMessage.put("fdType", fdType);//执行动作，2：占用，3：使用
		infoMessage.put("fdDetailId",(param.containsKey("fdDetailId")&&param.get("fdDetailId")!=null)?param.get("fdDetailId"):"" );//单据明细ID，无明细可空
		infoMessage.put("fdMoney", fdMoney);//操作金额
		fsscBudgetExecuteService.addFsscBudgetExecute(JSONObject.fromObject(infoMessage));
	}
	
	/***************************************************
	 * 功能：删除对应的预算占用记录
	 * ************************************************/
	@ResponseBody
	@RequestMapping(value = "/deleteBudgetExecue")
	@Override
	public String deleteBudgetExecue(@RequestBody String messageJson) {
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject=fsscBudgetOperatService.deleteFsscBudgetExecute(JSONObject.fromObject(messageJson));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
	/***************************************************
	 * 先删除对应维度的占用，重新插入新的占用或者使用等记录
	 * ***********************************************/
	@ResponseBody
	@RequestMapping(value = "/updateBudgetExecue")
	@Override
	public String updateBudgetExecue(@RequestBody String executeJson) {
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject=fsscBudgetOperatService.updateFsscBudgetExecute(JSONArray.fromObject(executeJson));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
	/***************************************************
	 * 根据信息获取对应的执行信息
	 * ***********************************************/
	@ResponseBody
	@RequestMapping(value = "/matchBudgetExecue")
	@Override
	public String matchBudgetExecue(@RequestBody String dataJson) {
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject=fsscBudgetOperatService.matchFsscBudgetExecute(JSONObject.fromObject(dataJson));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	/***************************************************
	 * 新增预算信息
	 * ***********************************************/
	@ResponseBody
	@RequestMapping(value = "/addBudgetData")
	@Override
	public String addBudgetData(String dataJson) {
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject = fsscBudgetOperatService.addFsscBudgetData(JSONArray.fromObject(dataJson));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

}
