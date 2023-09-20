package com.landray.kmss.fssc.fee.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.fee.constant.FsscFeeConstant;
import com.landray.kmss.fssc.fee.forms.FsscFeeMainForm;
import com.landray.kmss.fssc.fee.model.FsscFeeExpenseItem;
import com.landray.kmss.fssc.fee.model.FsscFeeLedger;
import com.landray.kmss.fssc.fee.model.FsscFeeMain;
import com.landray.kmss.fssc.fee.model.FsscFeeMapp;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.fssc.fee.service.IFsscFeeExpenseItemService;
import com.landray.kmss.fssc.fee.service.IFsscFeeMainService;
import com.landray.kmss.fssc.fee.service.IFsscFeeMappService;
import com.landray.kmss.fssc.fee.util.FsscFeeUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.query.Query;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FsscFeeMainServiceImp extends ExtendDataServiceImp implements IFsscFeeMainService {
	private IFsscFeeMappService fsscFeeMappService;
	public void setFsscFeeMappService(IFsscFeeMappService fsscFeeMappService) {
		this.fsscFeeMappService = fsscFeeMappService;
	}
	private IFsscCommonBudgetOperatService fsscBudgetOperatService;
	
	public IFsscCommonBudgetOperatService getFsscBudgetOperatService() {
		if(fsscBudgetOperatService==null){
			fsscBudgetOperatService = (IFsscCommonBudgetOperatService) SpringBeanUtil.getBean("fsscBudgetOperatService");
		}
		return fsscBudgetOperatService;
	}
    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    private ISysNumberFlowService sysNumberFlowService;
    
    private IFsscFeeExpenseItemService fsscFeeExpenseItemService;

    public void setFsscFeeExpenseItemService(IFsscFeeExpenseItemService fsscFeeExpenseItemService) {
		this.fsscFeeExpenseItemService = fsscFeeExpenseItemService;
	}
    
	public ISysOrgCoreService sysOrgCoreService;
	
	public ISysOrgCoreService getSysOrgCoreService() {
		if(sysOrgCoreService==null){
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscFeeMain) {
            FsscFeeMain fsscFeeMain = (FsscFeeMain) model;
            FsscFeeMainForm fsscFeeMainForm = (FsscFeeMainForm) form;
            if (fsscFeeMain.getDocStatus() == null || fsscFeeMain.getDocStatus().startsWith("1")) {
                if (fsscFeeMainForm.getDocStatus() != null && (fsscFeeMainForm.getDocStatus().startsWith("1") || fsscFeeMainForm.getDocStatus().startsWith("2"))) {
                    fsscFeeMain.setDocStatus(fsscFeeMainForm.getDocStatus());
                }
            }
            if (fsscFeeMain.getDocNumber() == null && (fsscFeeMain.getDocStatus().startsWith("2") || fsscFeeMain.getDocStatus().startsWith("3"))) {
                fsscFeeMain.setDocNumber(sysNumberFlowService.generateFlowNumber(fsscFeeMain));
            }
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscFeeMain fsscFeeMain = new FsscFeeMain();
        fsscFeeMain.setDocCreateTime(new Date());
        fsscFeeMain.setDocCreator(UserUtil.getUser());
        FsscFeeUtil.initModelFromRequest(fsscFeeMain, requestContext);
        if (fsscFeeMain.getDocTemplate() != null) {
            fsscFeeMain.setExtendFilePath(XFormUtil.getFileName(fsscFeeMain.getDocTemplate(), "fsscFeeMain"));
            if (Boolean.FALSE.equals(fsscFeeMain.getDocTemplate().getDocUseXform())) {
                fsscFeeMain.setDocXform(fsscFeeMain.getDocTemplate().getDocXform());
            }
            fsscFeeMain.setDocUseXform(fsscFeeMain.getDocTemplate().getDocUseXform());
        }
        if(fsscFeeMain.getFdIsClosed()==null){
        	fsscFeeMain.setFdIsClosed(false);
        }
        return fsscFeeMain;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscFeeMain fsscFeeMain = (FsscFeeMain) model;
        if (fsscFeeMain.getDocTemplate() != null) {
            dispatchCoreService.initFormSetting(form, "fsscFeeMain", fsscFeeMain.getDocTemplate(), "fsscFeeMain", requestContext);
        }
    }

    @Override
    public List<FsscFeeMain> findByDocTemplate(FsscFeeTemplate docTemplate) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscFeeMain.docTemplate.fdId=:fdId");
        hqlInfo.setParameter("fdId", docTemplate.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    public void setSysNumberFlowService(ISysNumberFlowService sysNumberFlowService) {
        this.sysNumberFlowService = sysNumberFlowService;
    }

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		//如果有预算模块，需要占用预算
		if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
			saveOrUpdateBudgetInfo(modelObj);
		}
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		//如果有预算模块，需要占用预算
		if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
			saveOrUpdateBudgetInfo(modelObj);
		}
		super.update(modelObj);
	}
	
	private void saveOrUpdateBudgetInfo(IBaseModel modelObj) throws Exception{
		FsscFeeMain main = (FsscFeeMain) modelObj;
		if(SysDocConstant.DOC_STATUS_DRAFT.equals(main.getDocStatus())){
			return;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fsscFeeMapp.fdTemplate.fdId=:fdTemplateId");
		hqlInfo.setParameter("fdTemplateId",main.getDocTemplate().getFdId());
		List<FsscFeeMapp> list = fsscFeeMappService.findList(hqlInfo);
		//必须要配置了台账映射
		if(!ArrayUtil.isEmpty(list)){
			FsscFeeMapp mapp = list.get(0);
			//必须要配置预算规则
			if(StringUtil.isNotNull(mapp.getFdRuleId())){
				String fdRuleId = mapp.getFdRuleId().replaceAll("\\$", "").split("_budget_status")[0];
				//配置了明细表，说明预算在明细中
				if(fdRuleId.indexOf(".")>-1){
					String fdTableId = fdRuleId.split("\\.")[0];
					fdRuleId = fdRuleId.split("\\.")[1];
					List<Map> tableList = (List<Map>) main.getExtendDataModelInfo().getModelData().get(fdTableId);
					for(Map detail:tableList){
						String fdBudgetInfo = (String) detail.get(fdRuleId+"_budget_info");
						if(StringUtil.isNull(fdBudgetInfo)){
							continue;
						}
						JSONArray data = JSONArray.fromObject(fdBudgetInfo.replaceAll("'", "\""));
						JSONArray params = new JSONArray();
						for(int i=0;i<data.size();i++){
							JSONObject obj = data.getJSONObject(i);
							JSONObject row = new JSONObject();
							row.put("fdModelId", main.getFdId());
							row.put("fdModelName", FsscFeeMain.class.getName());
							Number fdMoney = (Number) getFormValue(mapp.getFdMoneyId(),main,detail,false,null);
							Double money = fdMoney==null?0d:fdMoney.doubleValue();
							Object rate = getFormValue(mapp.getFdCurrencyId(),main,detail,false,"_budget_rate");
							Double rr = 1d;
							try{
								rr = Double.parseDouble(rate.toString());
							}catch(Exception e){
							}
							money = FsscNumberUtil.getMultiplication(money, rr);
							row.put("fdMoney", money);
							row.put("fdBudgetId", obj.get("fdBudgetId"));
							row.put("fdDetailId", detail.get("fdId"));
							row.put("fdBudgetItemId", obj.get("fdBudgetItemId"));
							row.put("fdType", "2");
							row.put("fdCompanyId", getFormValue(mapp.getFdCompanyId(),main,detail,false,null));
							if(StringUtil.isNotNull(mapp.getFdCostCenterId())){
								row.put("fdCostCenterId", getFormValue(mapp.getFdCostCenterId(),main,detail,false,null));
							}
							if(StringUtil.isNotNull(mapp.getFdProjectId())){
								row.put("fdProjectId", getFormValue(mapp.getFdProjectId(),main,detail,false,null));
							}
							if(StringUtil.isNotNull(mapp.getFdWbs())){
								row.put("fdWbsId", getFormValue(mapp.getFdWbsId(),main,detail,false,null));
							}
							if(StringUtil.isNotNull(mapp.getFdInnerOrderId())){
								row.put("fdInnerOrderId", getFormValue(mapp.getFdInnerOrderId(),main,detail,false,null));
							}
							if(StringUtil.isNotNull(mapp.getFdPersonId())){
								row.put("fdPersonId", getFormValue(mapp.getFdPersonId(),main,detail,true,null));
							}
							if(StringUtil.isNotNull(mapp.getFdDeptId())){
								row.put("fdDeptId", getFormValue(mapp.getFdDeptId(),main,detail,true,null));
							}
							//由于结转情况下，需要重新匹配新的预算，所以需要传费用类型ID
							if(StringUtil.isNotNull(mapp.getFdExpenseItemId())){
								row.put("fdExpenseItemId", getFormValue(mapp.getFdExpenseItemId(),main,detail,false,null));
							}
							params.add(row);
						}
						getFsscBudgetOperatService().updateFsscBudgetExecute(params);
					}
				}else{
					String fdBudgetInfo = (String) main.getExtendDataModelInfo().getModelData().get(fdRuleId+"_budget_info");
					if(StringUtil.isNull(fdBudgetInfo)){
						return;
					}
					JSONArray data = JSONArray.fromObject(fdBudgetInfo.replaceAll("'", "\""));
					JSONArray params = new JSONArray();
					for(int i=0;i<data.size();i++){
						JSONObject obj = data.getJSONObject(i);
						JSONObject row = new JSONObject();
						row.put("fdModelId", main.getFdId());
						row.put("fdModelName", FsscFeeMain.class.getName());
						Object mon = main.getExtendDataModelInfo().getModelData().get(mapp.getFdMoneyId().replaceAll("\\$", ""));
						mon = mon==null?"0":mon.toString();
						Number fdMoney = Double.parseDouble(mon.toString()) ;
						Double money = fdMoney==null?0d:fdMoney.doubleValue();
						Object rate = main.getExtendDataModelInfo().getModelData().get(mapp.getFdCurrencyId().replaceAll("\\$", "")+"_budget_rate");
						Double rr = 1d;
						try{
							rr = Double.parseDouble(rate.toString());
						}catch(Exception e){
						}
						money = FsscNumberUtil.getMultiplication(money, rr);
						row.put("fdMoney", money);
						row.put("fdBudgetId", obj.get("fdBudgetId"));
						row.put("fdDetailId", main.getFdId());
						row.put("fdBudgetItemId", obj.get("fdBudgetItemId"));
						row.put("fdType", "2");
						row.put("fdCompanyId", main.getExtendDataModelInfo().getModelData().get(mapp.getFdCompanyId().replaceAll("\\$", "")));
						if(StringUtil.isNotNull(mapp.getFdCostCenterId())){
							row.put("fdCostCenterId", main.getExtendDataModelInfo().getModelData().get(mapp.getFdCostCenterId().replaceAll("\\$", "")));
						}
						if(StringUtil.isNotNull(mapp.getFdProjectId())){
							row.put("fdProjectId", main.getExtendDataModelInfo().getModelData().get(mapp.getFdProjectId().replaceAll("\\$", "")));
						}
						if(StringUtil.isNotNull(mapp.getFdWbs())){
							row.put("fdWbsId", main.getExtendDataModelInfo().getModelData().get(mapp.getFdWbsId().replaceAll("\\$", "")));
						}
						if(StringUtil.isNotNull(mapp.getFdInnerOrderId())){
							row.put("fdInnerOrderId", main.getExtendDataModelInfo().getModelData().get(mapp.getFdInnerOrderId().replaceAll("\\$", "")));
						}
						if(StringUtil.isNotNull(mapp.getFdPersonId())){
							Map person = (Map) main.getExtendDataModelInfo().getModelData().get(mapp.getFdPersonId().replaceAll("\\$", ""));
							row.put("fdPersonId", person==null?"":person.get("id"));
						}
						params.add(row);
					}
					getFsscBudgetOperatService().updateFsscBudgetExecute(params);
				}
			}
		}
	}
	
	private Object getFormValue(String property,FsscFeeMain main,Map detail,Boolean isOrg,String suff) throws Exception{
		property = property.replaceAll("\\$", "");
		Object value = null;
		if(property.indexOf(".")>-1){
			property = property.split("\\.")[1];
			if(StringUtil.isNotNull(suff)){
				property = property+suff;
			}
			value = detail.get(property);
		}else{
			if(StringUtil.isNotNull(suff)){
				property = property+suff;
			}
			value = main.getExtendDataModelInfo().getModelData().get(property);
		}
		return isOrg?((Map)value).get("id"):value;
	}

	@Override
	public List<FsscFeeLedger> getLedgerData(String fdFeeId, String fdType) throws Exception {
		String hql = "from "+FsscFeeLedger.class.getName()+" where fdLedgerId in(select t.fdId from "+FsscFeeLedger.class.getName()+" t where t.fdModelId=:fdFeeId)";
		if(StringUtil.isNotNull(fdType)){
			hql += " and fdType=:fdType";
		}
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdFeeId", fdFeeId);
		if(StringUtil.isNotNull(fdType)){
			query.setParameter("fdType", fdType);
		}
		return query.list();
	}

	@Override
	public Boolean checkCanCloseFee(String fdId) throws Exception {
		List<FsscFeeLedger> list = getLedgerData(fdId, FsscFeeConstant.FSSC_FEE_LEDGER_TYPE_USING);
		return ArrayUtil.isEmpty(list);
	}

	@Override
	public void updateCloseFee(String fdId) throws Exception {
		if(getFsscBudgetOperatService()!=null){
			JSONObject executeJson = new JSONObject();
			executeJson.put("fdModelId", fdId);
			getFsscBudgetOperatService().deleteFsscBudgetExecute(executeJson);
		}
		getBaseDao().getHibernateSession().createQuery("update FsscFeeMain set fdIsClosed=:fdIsClosed where fdId=:fdId").setParameter("fdId", fdId).setParameter("fdIsClosed", true).executeUpdate();
	}

	/**
	 * 获取申请单本币金额，申请金额当有外币展现不合逻辑，1美元+1人民币=2，无法展示。统一为本币
	 */
	@Override
	public Double getTotalMoney(String fdId) throws Exception {
		List<FsscFeeLedger> data = this.getLedgerData(fdId, FsscFeeConstant.FSSC_FEE_LEDGER_TYPE_INIT);
		Double total = 0d;
		for(FsscFeeLedger ledger:data){
			try {
				total += ledger.getFdStandardMoney();
			} catch (Exception e) {
			}
		}
		return total;
	}
	
	/**
	 * porlet获取未报费用
	 */
	@Override
	public Page listPortlet(HttpServletRequest request) throws Exception {
		Page page = Page.getEmptyPage();// 简单列表使用
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setGetCount(false);
		String type=request.getParameter("type");
		if("ower".equals(type)){
			String where="";
			where=StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "fsscFeeMain.docCreator.fdId=:creatorId");
			hqlInfo.setWhereBlock(where);
			hqlInfo.setParameter("creatorId", UserUtil.getUser().getFdId());
		}
		hqlInfo.setOrderBy("fsscFeeMain.fdId desc");
		page = findPage(hqlInfo);
		UserOperHelper.logFindAll(page.getList(), getModelName());
		List<FsscFeeMain> feeList = page.getList();
		return page;
	}
	
	@Override
	public List<Map<String, String>> getAttData(HttpServletRequest request) throws Exception {
		List<Map<String,String>> rtnList=new ArrayList<>();
		String fdId=request.getParameter("fdId");
		List<SysAttMain> attList=this.getBaseDao().getHibernateSession().createQuery("select t from SysAttMain t where t.fdModelId=:fdModelId")
				.setParameter("fdModelId", fdId).list();
		Map<String,String> iconMap=new HashMap();
	    String[] iconArr=new String[]{"aiv","aud","bmp","documents","excel","gif","html","image","jpg","js","movie","mp4","outlook","pdf","png","ppt","text","tif","video","visio","vsd","word","zip"};
	    for(int i=0,len=iconArr.length;i<len;i++){
		  iconMap.put(iconArr[i],iconArr[i]);
	    }
		for(SysAttMain att:attList){
			Map<String,String> map=new HashMap<>();
			String fileName=att.getFdFileName();
			if(StringUtil.isNotNull(fileName)){
				String suffix_name=fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
				String icon_name="";
				if("doc".equals(suffix_name)){
					  icon_name="word";
				  }else if("xlsx".equals(suffix_name)||"xls".equals(suffix_name)){
					  icon_name="excel";
				  }else if("pptx".equals(suffix_name)||"ppt".equals(suffix_name)){
					  icon_name="ppt";
				  }else{
					  icon_name= iconMap.get(suffix_name);
				  }
				  if(StringUtil.isNull(icon_name)){
					  icon_name="documents";
				  }
				map.put("fdId", att.getFdId());
				map.put("icon", icon_name);
				map.put("fileName", fileName);
				rtnList.add(map);
			}
		}
		return rtnList;
	}
	
	/**
	 * 获取对应的费用类型是否必须要有预算
	* Description:  
	* @author xiexingxing
	* @date 2020年4月8日
	 */
	@Override
    public Boolean getIsNeedBudgetByItem(JSONObject row) throws Exception{
		Boolean fdIsNeedBudget=Boolean.FALSE;  //默认非必须
		String fdTemplateId=row.containsKey("fdTemplateId")?row.getString("fdTemplateId"):"";
		String fdExpenseItemId=row.containsKey("fdExpenseItemId")?row.getString("fdExpenseItemId"):"";
		String fdCompanyId=row.containsKey("fdCompanyId")?row.getString("fdCompanyId"):"";
		if(StringUtil.isNotNull(fdTemplateId)&&StringUtil.isNotNull(fdExpenseItemId)&&StringUtil.isNotNull(fdCompanyId)){
			if(StringUtil.isNotNull(fdExpenseItemId)){
				HQLInfo hqlInfo=new HQLInfo();
				hqlInfo.setWhereBlock("fsscFeeExpenseItem.fdItemList.fdId=:fdExpenseItemId and fsscFeeExpenseItem.fdTemplate.fdId=:fdTemplateId and fsscFeeExpenseItem.fdCompany.fdId=:fdCompanyId");
				hqlInfo.setParameter("fdExpenseItemId", fdExpenseItemId);
				hqlInfo.setParameter("fdTemplateId", fdTemplateId);
				hqlInfo.setParameter("fdCompanyId", fdCompanyId);
				List<FsscFeeExpenseItem> configList=fsscFeeExpenseItemService.findList(hqlInfo);
				if(!ArrayUtil.isEmpty(configList)){
					fdIsNeedBudget=configList.get(0).getFdIsNeedBudget();
				}
			}
		}
		return fdIsNeedBudget;
	}
}
