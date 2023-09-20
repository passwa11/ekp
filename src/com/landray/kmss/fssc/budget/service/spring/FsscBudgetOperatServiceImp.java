package com.landray.kmss.fssc.budget.service.spring;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetExecuteService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetMainService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetMatchService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.SpringBeanUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscBudgetOperatServiceImp extends ExtendDataServiceImp implements IFsscCommonBudgetOperatService {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(FsscBudgetOperatServiceImp.class);
	
	protected IFsscBudgetExecuteService fsscBudgetExecuteService;
	
    public void setFsscBudgetExecuteService(IFsscBudgetExecuteService fsscBudgetExecuteService) {
		this.fsscBudgetExecuteService = fsscBudgetExecuteService;
	}
    
    protected IFsscBudgetMainService fsscBudgetMainService;
    
	public void setFsscBudgetMainService(IFsscBudgetMainService fsscBudgetMainService) {
		this.fsscBudgetMainService = fsscBudgetMainService;
	}
	
	protected IFsscCommonBudgetMatchService fsscBudgetMatchService;
	
	public void setFsscBudgetMatchService(
			IFsscCommonBudgetMatchService fsscBudgetMatchService) {
		this.fsscBudgetMatchService = fsscBudgetMatchService;
	}
	
	private IFsscBudgetDataService fsscBudgetDataService;

	public void setFsscBudgetDataService(IFsscBudgetDataService fsscBudgetDataService) {
		if(fsscBudgetDataService==null){
			fsscBudgetDataService=(IFsscBudgetDataService) SpringBeanUtil.getBean("fsscBudgetDataService");
		}
		this.fsscBudgetDataService = fsscBudgetDataService;
	}

	/********************************************************
     * 功能：根据对应信息添加预算执行信息
     * @param executeJson 预算执行信息
     * ******************************************************/
    @Override
    public JSONObject addFsscBudgetExecute(JSONArray executeJson) throws Exception{
    	JSONObject rtnObj=new JSONObject();
    	try {
        	for(int i=0,size=executeJson.size();i<size;i++){
        		fsscBudgetExecuteService.addFsscBudgetExecute(executeJson.getJSONObject(i));
        	}
        	rtnObj.put("result", "success");
		} catch (Exception e) {
			rtnObj.put("result", "failure");
			rtnObj.put("message", e.toString());
		}
    	
    	return rtnObj;
    }

    /********************************************************
     * 功能：根据对应信息删除预算执行信息
     * @param executeJson 预算执行信息,有fdDetailId则清楚明细的占用数据，无则清楚整个表单的占用数据
     * ******************************************************/
	@Override
	public JSONObject deleteFsscBudgetExecute(JSONObject executeJson)
			throws Exception {
		return fsscBudgetExecuteService.deleteFsscBudgetExecute(executeJson);
	}
	@Override
    public JSONObject updateFsscBudgetExecute(JSONArray executeJson)
			throws Exception {
		JSONObject rtnObj=new JSONObject();
    	try {
    		List<String> idList=new ArrayList<>();
    		Boolean isKnots=false;  //未结转
			for(int n=0,size=executeJson.size();n<size;n++){
				JSONObject json=executeJson.getJSONObject(n);
				idList=new ArrayList<>();
				if(json.containsKey("fdBudgetId")){
					idList.add(json.optString("fdBudgetId"));
					if(fsscBudgetDataService.checkBudgetIsKnots(idList)) {
						isKnots=true;  //年度、月度只要一个存在已结转，则认为单据匹配到的预算已经被结转了
					}
				}
			}
    		if(isKnots){
    			//启用了结转,且原有的预算全部已结转，单据前期的预算,起草和发布出现跨月
    			if(!executeJson.isEmpty()){
    				JSONObject oldJSON=executeJson.getJSONObject(0);
    				//查找当前月份预算
    				JSONObject budgetObj=fsscBudgetMatchService.matchFsscBudget(oldJSON);
    				if("2".equals(budgetObj.get("result"))&&
    						budgetObj.containsKey("data")&&budgetObj.get("data")!=null){
    					JSONArray budgetArray=budgetObj.getJSONArray("data");
    					for(int i=0,size=budgetArray.size();i<size;i++){
    						JSONObject budgetJSON=budgetArray.getJSONObject(i);
    						JSONObject newJSON=new JSONObject();
    						newJSON.put("fdModelId", oldJSON.containsKey("fdModelId")?oldJSON.get("fdModelId"):"");
    						newJSON.put("fdModelName", oldJSON.containsKey("fdModelName")?oldJSON.get("fdModelName"):"");
    						newJSON.put("fdDetailId", oldJSON.containsKey("fdDetailId")?oldJSON.get("fdDetailId"):"");
    						newJSON.put("fdType", oldJSON.containsKey("fdType")?oldJSON.get("fdType"):"");
    						newJSON.put("fdMoney", oldJSON.containsKey("fdMoney")?oldJSON.get("fdMoney"):"");
    						newJSON.put("fdBudgetId", budgetJSON.containsKey("fdBudgetId")?budgetJSON.get("fdBudgetId"):"");
    						deleteFsscBudgetExecute(newJSON); //将结转过来的占用释放
                    		fsscBudgetExecuteService.addFsscBudgetExecute(newJSON);  //将占用转为使用
                    	}
    				}
    			}
    		}else{
    			//未启用结转，单据参数会传预算ID参数
    			for(int i=0,size=executeJson.size();i<size;i++){
            		deleteFsscBudgetExecute(executeJson.getJSONObject(i)); 
            		fsscBudgetExecuteService.addFsscBudgetExecute(executeJson.getJSONObject(i));
            	}
    		}
        	rtnObj.put("result", "success");
		} catch (Exception e) {
			rtnObj.put("result", "failure");
			rtnObj.put("message", e.toString());
		}
    	
    	return rtnObj;
	}
	
	/********************************************************
	 * 功能：根据对应参数查询预算执行信息
	 * @param executeJson 预算执行信息
	 * ******************************************************/
	@Override
	public JSONObject matchFsscBudgetExecute(JSONObject dataJson) throws Exception {
		JSONObject rtnObj=new JSONObject();
    	try {
    		rtnObj = fsscBudgetExecuteService.matchFsscBudgetExecute(dataJson);
        	rtnObj.put("result", "success");
		} catch (Exception e) {
			rtnObj.put("result", "failure");
			rtnObj.put("message", e.toString());
		}
    	return rtnObj;
	}
	/**
	 * 保存预算信息
	 */
	@Override
	public JSONObject addFsscBudgetData(JSONArray dataJson) throws Exception {
		JSONObject rtnObj=new JSONObject();
    	try {
    		fsscBudgetMainService.addFsscBudgetData(dataJson);
        	rtnObj.put("result", "success");
		} catch (Exception e) {
			rtnObj.put("result", "failure");
			rtnObj.put("message", e.toString());
			e.printStackTrace();
			logger.error("生成预算报错：",e);
		}
    	
    	return rtnObj;
	}
}
