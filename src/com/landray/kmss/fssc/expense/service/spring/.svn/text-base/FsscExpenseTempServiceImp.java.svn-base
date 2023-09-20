package com.landray.kmss.fssc.expense.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLedgerService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.expense.forms.FsscExpenseTempDetailForm;
import com.landray.kmss.fssc.expense.forms.FsscExpenseTempForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseTemp;
import com.landray.kmss.fssc.expense.model.FsscExpenseTempDetail;
import com.landray.kmss.fssc.expense.service.IFsscExpenseTempDetailService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseTempService;
import com.landray.kmss.fssc.expense.util.FsscExpenseUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FsscExpenseTempServiceImp extends ExtendDataServiceImp implements IFsscExpenseTempService,IXMLDataBean {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscExpenseTemp) {
            FsscExpenseTemp fsscExpenseTemp = (FsscExpenseTemp) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscExpenseTemp fsscExpenseTemp = new FsscExpenseTemp();
        FsscExpenseUtil.initModelFromRequest(fsscExpenseTemp, requestContext);
        requestContext.setAttribute("fdCreatorCheck", EopBasedataFsscUtil.getSwitchValue("fdCreatorCheck"));
        return fsscExpenseTemp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscExpenseTemp fsscExpenseTemp = (FsscExpenseTemp) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
    
    private IFsscCommonLedgerService fsscCommonLedgerService;
    
	public IFsscCommonLedgerService getFsscCommonLedgerService() {
		if (fsscCommonLedgerService == null) {
			fsscCommonLedgerService = (IFsscCommonLedgerService) SpringBeanUtil.getBean("fsscCommonLedgerService");
        }
		return fsscCommonLedgerService;
	}
	
	private IFsscExpenseTempDetailService fsscExpenseTempDetailService;
	
	public IFsscExpenseTempDetailService getFsscExpenseTempDetailService() {
		if (fsscExpenseTempDetailService == null) {
			fsscExpenseTempDetailService = (IFsscExpenseTempDetailService) SpringBeanUtil.getBean("fsscExpenseTempDetailService");
        }
		return fsscExpenseTempDetailService;
	}

	@Override
	public void getTempInvoiceInfo(FsscExpenseTempForm fsscExpenseTempForm,HttpServletRequest request) throws Exception {
		JSONObject rtnObj=new JSONObject();
		JSONObject detailInfo=new JSONObject();
		AutoArrayList detailListForm=fsscExpenseTempForm.getFdInvoiceListTemp_Form();
		List<String> keyList=new ArrayList<>();  //发票号码和代码拼接
		String keys="";
		for(int i=0,size=detailListForm.size();i<size;i++){
			FsscExpenseTempDetailForm detailForm=(FsscExpenseTempDetailForm) detailListForm.get(i);
			keys=(StringUtil.isNotNull(detailForm.getFdInvoiceNumber())?detailForm.getFdInvoiceNumber():"")
			+(StringUtil.isNotNull(detailForm.getFdInvoiceCode())?detailForm.getFdInvoiceCode():"");
			keyList.add(keys);
		}
		JSONObject checkStatusJson=new JSONObject();
		JSONObject stateJson=new JSONObject();
		if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
			JSONObject statusObj=getFsscCommonLedgerService().getCheckStatusJson(keyList);
			checkStatusJson=statusObj.optJSONObject("checkStatusObj");
			stateJson=statusObj.optJSONObject("stateStatusObj");
		}
		for(int i=0,size=detailListForm.size();i<size;i++){
			FsscExpenseTempDetailForm detailForm=(FsscExpenseTempDetailForm) detailListForm.get(i);
			String key=(StringUtil.isNotNull(detailForm.getFdExpenseTypeId())?detailForm.getFdExpenseTypeId():"")
				+(StringUtil.isNotNull(detailForm.getFdInvoiceType())?detailForm.getFdInvoiceType():"")
				+(StringUtil.isNotNull(detailForm.getFdIsVat())?detailForm.getFdIsVat():"")
				+(StringUtil.isNotNull(detailForm.getFdTax())?detailForm.getFdTax():"");
			if(detailInfo.containsKey(key)){//已经有相同的费用类型了，金额进行累加
				JSONObject valueJson=detailInfo.getJSONObject(key);
				Double fdInvoiceMoney=valueJson.getDouble("fdInvoiceMoney");  //发票金额
				Double fdNonDeductMoney=valueJson.getDouble("fdNonDeductMoney");  //不可抵扣金额
				Double fdTaxMoney=valueJson.getDouble("fdTaxMoney");	//税额
				Double fdNoTaxMoney=valueJson.getDouble("fdNoTaxMoney");	//不含税金额
				String fdInvoiceMoneyTemp=detailForm.getFdInvoiceMoney();
				String fdNonDeductMoneyTemp=detailForm.getFdNonDeductMoney();
				String fdTaxMoneyTemp=detailForm.getFdTaxMoney();
				String fdNoTaxMoneyTemp=detailForm.getFdNoTaxMoney();
				valueJson.put("fdInvoiceMoney", FsscNumberUtil.getAddition(fdInvoiceMoney, StringUtil.isNotNull(fdInvoiceMoneyTemp)?Double.parseDouble(fdInvoiceMoneyTemp):0.0, 2));
				valueJson.put("fdNonDeductMoney", FsscNumberUtil.getAddition(fdNonDeductMoney, StringUtil.isNotNull(fdNonDeductMoneyTemp)?Double.parseDouble(fdNonDeductMoneyTemp):0.0, 2));
				valueJson.put("fdTaxMoney", FsscNumberUtil.getAddition(fdTaxMoney, StringUtil.isNotNull(fdTaxMoneyTemp)?Double.parseDouble(fdTaxMoneyTemp):0.0, 2));
				valueJson.put("fdNoTaxMoney", FsscNumberUtil.getAddition(fdNoTaxMoney, StringUtil.isNotNull(fdNoTaxMoneyTemp)?Double.parseDouble(fdNoTaxMoneyTemp):0.0, 2));
				valueJson.put("fdExpenseTempDetailIds", StringUtil.isNotNull(valueJson.getString("fdExpenseTempDetailIds"))
						?(valueJson.getString("fdExpenseTempDetailIds")+";"+detailForm.getFdId())
								:detailForm.getFdId());
				String fdIsVat=valueJson.containsKey("fdIsVat")?valueJson.getString("fdIsVat"):"";	//是否进项抵扣
				if(StringUtil.isNotNull(fdIsVat)&&"false".equals(fdIsVat)){//只要有一个可抵扣，则fdIsVat为true，标识可抵扣
					valueJson.put("fdIsVat", detailForm.getFdIsVat());
				}
				valueJson.put("fdCheckCode", detailForm.getFdCheckCode());
			}else{
				JSONObject valueJson=new JSONObject();
				valueJson.put("fdExpenseItemId", detailForm.getFdExpenseTypeId());
				valueJson.put("fdExpenseItemName", detailForm.getFdExpenseTypeName());
				valueJson.put("fdInvoiceDate", detailForm.getFdInvoiceDate());
				valueJson.put("fdInvoiceMoney", StringUtil.isNotNull(detailForm.getFdInvoiceMoney())?detailForm.getFdInvoiceMoney():"0.0");
				valueJson.put("fdTaxMoney", StringUtil.isNotNull(detailForm.getFdTaxMoney())?detailForm.getFdTaxMoney():"0.0");
				valueJson.put("fdNonDeductMoney", StringUtil.isNotNull(detailForm.getFdNonDeductMoney())?detailForm.getFdNonDeductMoney():"0.0");
				valueJson.put("fdNoTaxMoney", StringUtil.isNotNull(detailForm.getFdNoTaxMoney())?detailForm.getFdNoTaxMoney():"0.0");
				valueJson.put("fdExpenseTempId", fsscExpenseTempForm.getFdId());
				valueJson.put("fdExpenseTempDetailIds", detailForm.getFdId());
				valueJson.put("fdIsVat", detailForm.getFdIsVat());
				valueJson.put("fdTax", detailForm.getFdTax());
				valueJson.put("fdInvoiceType", detailForm.getFdInvoiceType());
				valueJson.put("fdCheckCode", detailForm.getFdCheckCode());
				valueJson.put("fdTaxNumber", detailForm.getFdTaxNumber());
				valueJson.put("fdPurchName", detailForm.getFdPurchName());
				valueJson.put("fdIsCurrent", getIsCurrent(request.getParameter("fdCompanyId"),detailForm.getFdTaxNumber(),detailForm.getFdPurchName(),detailForm.getFdInvoiceType()));
				detailInfo.put(key, valueJson);
			}
		}
		rtnObj.put("detailInfo", detailInfo);
		rtnObj.put("params", getInvoiceArr( detailListForm, checkStatusJson, stateJson,request));
		request.setAttribute("rtnObj", rtnObj);
		
	}

	@Override
	public void getNewDetailTempInvoiceInfo(FsscExpenseTempForm fsscExpenseTempForm, HttpServletRequest request)
			throws Exception {
		JSONObject rtnObj=new JSONObject();
		String index=request.getParameter("index");  //本次操作的费用明细ID
		String method=request.getParameter("method_GET");
		JSONArray params=new JSONArray();
		JSONObject valueJson=new JSONObject();
		valueJson.put("fdInvoiceMoney", 0.0);
		valueJson.put("fdTaxMoney", 0.0);
		valueJson.put("fdNoTaxMoney", 0.0);
		valueJson.put("fdNonDeductMoney", 0.0);
		valueJson.put("fdExpenseTempDetailIds",request.getParameter("fdExpenseTempDetailIds"));
		AutoArrayList detailListForm=fsscExpenseTempForm.getFdInvoiceListTemp_Form();
		List<String> keyList=new ArrayList<>();  //发票号码和代码拼接
		String keys="";
		for(int i=0,size=detailListForm.size();i<size;i++){
			FsscExpenseTempDetailForm detailForm=(FsscExpenseTempDetailForm) detailListForm.get(i);
			keys=(StringUtil.isNotNull(detailForm.getFdInvoiceNumber())?detailForm.getFdInvoiceNumber():"")
			+(StringUtil.isNotNull(detailForm.getFdInvoiceCode())?detailForm.getFdInvoiceCode():"");
			keyList.add(keys);
		}
		JSONObject checkStatusJson=new JSONObject();
		JSONObject stateJson=new JSONObject();
		if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
			JSONObject statusObj=getFsscCommonLedgerService().getCheckStatusJson(keyList);
			checkStatusJson=statusObj.optJSONObject("checkStatusObj");
			stateJson=statusObj.optJSONObject("stateStatusObj");
		}
		for(int i=0,size=detailListForm.size();i<size;i++){
			FsscExpenseTempDetailForm detailForm=(FsscExpenseTempDetailForm) detailListForm.get(i);
			if("true".equals(detailForm.getFdThisFlag())){//已经有相同的费用类型了，金额进行累加
				if("add".equals(method)){
					valueJson.put("fdExpenseItemId", detailForm.getFdExpenseTypeId());
					valueJson.put("fdExpenseItemName", detailForm.getFdExpenseTypeName());
					valueJson.put("fdExpenseTempId", fsscExpenseTempForm.getFdId());
					valueJson.put("fdInvoiceDate", detailForm.getFdInvoiceDate());
				}
				Double fdInvoiceMoney=valueJson.getDouble("fdInvoiceMoney");  //发票金额
				Double fdNonDeductMoney=valueJson.getDouble("fdNonDeductMoney");  //不可抵扣金额
				Double fdTaxMoney=valueJson.getDouble("fdTaxMoney");	//税额
				Double fdNoTaxMoney=valueJson.getDouble("fdNoTaxMoney");	//不含税金额
				String fdInvoiceMoneyTemp=detailForm.getFdInvoiceMoney();
				String fdNonDeductMoneyTemp=detailForm.getFdNonDeductMoney();
				String fdTaxMoneyTemp=detailForm.getFdTaxMoney();
				String fdNoTaxMoneyTemp=detailForm.getFdNoTaxMoney();
				String fdExpenseTempDetailIds=valueJson.containsKey("fdExpenseTempDetailIds")?valueJson.getString("fdExpenseTempDetailIds"):"";
				valueJson.put("fdInvoiceMoney", FsscNumberUtil.getAddition(fdInvoiceMoney, StringUtil.isNotNull(fdInvoiceMoneyTemp)?Double.parseDouble(fdInvoiceMoneyTemp):0.0, 2));
				valueJson.put("fdTaxMoney", FsscNumberUtil.getAddition(fdTaxMoney, StringUtil.isNotNull(fdTaxMoneyTemp)?Double.parseDouble(fdTaxMoneyTemp):0.0, 2));
				valueJson.put("fdNoTaxMoney", FsscNumberUtil.getAddition(fdNoTaxMoney, StringUtil.isNotNull(fdNoTaxMoneyTemp)?Double.parseDouble(fdNoTaxMoneyTemp):0.0, 2));
				valueJson.put("fdNonDeductMoney", FsscNumberUtil.getAddition(fdNonDeductMoney, StringUtil.isNotNull(fdNonDeductMoneyTemp)?Double.parseDouble(fdNonDeductMoneyTemp):0.0, 2));
				if(fdExpenseTempDetailIds.indexOf(detailForm.getFdId())==-1){
					valueJson.put("fdExpenseTempDetailIds", StringUtil.isNotNull(fdExpenseTempDetailIds)?(fdExpenseTempDetailIds+";"+detailForm.getFdId()):detailForm.getFdId());
				}
				valueJson.put("fdInvoiceType", detailForm.getFdInvoiceType());
				String fdIsVat=valueJson.containsKey("fdIsVat")?valueJson.getString("fdIsVat"):"";	//是否进项抵扣
				if(!valueJson.containsKey("fdIsVat")||"false".equals(fdIsVat)){//只要有一个可抵扣，则fdIsVat为true，标识可抵扣
					valueJson.put("fdIsVat", detailForm.getFdIsVat());
				}
				valueJson.put("fdTax", detailForm.getFdTax());
				valueJson.put("fdInvoiceType", detailForm.getFdInvoiceType());
			}
		}
		rtnObj.put("valueJson", valueJson);
		rtnObj.put("params", getInvoiceArr( detailListForm, checkStatusJson, stateJson,request));
		rtnObj.put("index", index);
		request.setAttribute("rtnObj", rtnObj);
	}

	public JSONArray getInvoiceArr(AutoArrayList detailListForm,JSONObject checkStatusJson,JSONObject stateJson, HttpServletRequest request) throws Exception{
		JSONArray params=new JSONArray();
		JSONObject invoiceJson=new JSONObject();
		String keys="",key="";
		for(int i=0,size=detailListForm.size();i<size;i++) {
			FsscExpenseTempDetailForm detailForm = (FsscExpenseTempDetailForm) detailListForm.get(i);
			keys=(StringUtil.isNotNull(detailForm.getFdInvoiceNumber())?detailForm.getFdInvoiceNumber():"")
					+(StringUtil.isNotNull(detailForm.getFdInvoiceCode())?detailForm.getFdInvoiceCode():"");
			key=keys+(StringUtil.isNotNull(detailForm.getFdInvoiceDocId())?detailForm.getFdInvoiceDocId():"");//同一发票附件
			JSONObject param=new JSONObject();
			if(invoiceJson.containsKey(key)){
				param=invoiceJson.optJSONObject(key);
			}
			param.put("fdInvoiceType", detailForm.getFdInvoiceType());
			param.put("fdInvoiceNumber", detailForm.getFdInvoiceNumber());
			param.put("fdExpenseItemId", detailForm.getFdExpenseTypeId());
			param.put("fdExpenseItemName", detailForm.getFdExpenseTypeName());
			param.put("fdIsVat", detailForm.getFdIsVat());
			param.put("fdInvoiceCode", detailForm.getFdInvoiceCode());
			param.put("fdInvoiceDate", detailForm.getFdInvoiceDate());
			param.put("fdCheckStatus", checkStatusJson.optString(keys, "0"));  //默认未验真
			param.put("fdState", stateJson.optString(keys, "0"));   //默认正常
			param.put("fdCheckCode", detailForm.getFdCheckCode());
			param.put("fdTaxNumber", detailForm.getFdTaxNumber());
			param.put("fdPurchName", detailForm.getFdPurchName());
			param.put("fdIsCurrent", getIsCurrent(request.getParameter("fdCompanyId"),detailForm.getFdTaxNumber(),detailForm.getFdPurchName(),detailForm.getFdInvoiceType()));
			if(!invoiceJson.containsKey(key)){//同一附件，发票号码+发票代码，则是多税率，只返回一行
				param.put("fdInvoiceMoney", detailForm.getFdInvoiceMoney());
				param.put("fdNonDeductMoney", detailForm.getFdNonDeductMoney());
				param.put("fdTaxMoney", detailForm.getFdTaxMoney());
				param.put("fdNoTaxMoney", detailForm.getFdNoTaxMoney());
			}else{//多税率，税额不含税金额等信息需要累加
				Double tempAmount=0.00;
				tempAmount=StringUtil.isNotNull(detailForm.getFdInvoiceMoney())?Double.parseDouble(detailForm.getFdInvoiceMoney()):0.00;
				tempAmount=FsscNumberUtil.getAddition(tempAmount,param.optDouble("fdInvoiceMoney",0.00));
				param.put("fdInvoiceMoney", FsscNumberUtil.doubleToUp(tempAmount));
				tempAmount=StringUtil.isNotNull(detailForm.getFdNonDeductMoney())?Double.parseDouble(detailForm.getFdNonDeductMoney()):0.00;
				tempAmount=FsscNumberUtil.getAddition(tempAmount,param.optDouble("fdNonDeductMoney",0.00));
				param.put("fdNonDeductMoney", FsscNumberUtil.doubleToUp(tempAmount));
				tempAmount=StringUtil.isNotNull(detailForm.getFdTaxMoney())?Double.parseDouble(detailForm.getFdTaxMoney()):0.00;
				tempAmount=FsscNumberUtil.getAddition(tempAmount,param.optDouble("fdTaxMoney",0.00));
				param.put("fdTaxMoney", FsscNumberUtil.doubleToUp(tempAmount));
				tempAmount=StringUtil.isNotNull(detailForm.getFdNoTaxMoney())?Double.parseDouble(detailForm.getFdNoTaxMoney()):0.00;
				tempAmount=FsscNumberUtil.getAddition(tempAmount,param.optDouble("fdNoTaxMoney",0.00));
				param.put("fdNoTaxMoney", FsscNumberUtil.doubleToUp(tempAmount));
			}
			invoiceJson.put(key,param);
		}
		for (Object entrySet : invoiceJson.entrySet()) {
			Map.Entry entry=(Map.Entry) entrySet;
			params.add(entry.getValue());
		}
		return params;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnList=new ArrayList();
		String type=requestInfo.getParameter("type");
		if("getInvoiceByTempDetailIds".equals(type)){
			String tempDetailIds=requestInfo.getParameter("tempDetailIds");
			if(StringUtil.isNotNull(tempDetailIds)){
				HQLInfo hqlInfo =new HQLInfo();
				hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("fsscExpenseTempDetail.fdId", ArrayUtil.convertArrayToList(tempDetailIds.split(";"))));
				List<FsscExpenseTempDetail> tempDetailList=getFsscExpenseTempDetailService().findList(hqlInfo);
				if(!ArrayUtil.isEmpty(tempDetailList)){
					for(FsscExpenseTempDetail tempDetail:tempDetailList){
						Map<String,String> map=new HashMap<String,String>();
						String invoice_type=StringUtil.isNotNull(tempDetail.getFdInvoiceType())?tempDetail.getFdInvoiceType():"";
						String number=StringUtil.isNotNull(tempDetail.getFdInvoiceNumber())?tempDetail.getFdInvoiceNumber():"";
						String code=StringUtil.isNotNull(tempDetail.getFdInvoiceCode())?tempDetail.getFdInvoiceCode():"";
						map.put("key",invoice_type+number+code); 
						rtnList.add(map);
					}
				}
			}
		}
		return rtnList;
	}

	/**
	 * 判断当前发票是否为本公司发票，否返回0，是返回1，只校验增值税专用发票、增值税普通发票、增值税电子普通发票、增值税普通发票(卷票)
	 * @param fdCompanyId
	 * @param fdTaxNumber
	 * @param fdInvoiceType
	 * @return
	 * @throws Exception
	 */
	@Override
    public String getIsCurrent(String fdCompanyId, String fdTaxNumber, String fdPurchName, String fdInvoiceType) throws Exception{
		String fdIsCurrent=null;
		if(StringUtil.isNotNull(fdCompanyId)){
			EopBasedataCompany company=(EopBasedataCompany) this.findByPrimaryKey(fdCompanyId, EopBasedataCompany.class,true);
			if(company!=null&&StringUtil.isNotNull(company.getFdDutyParagraph())
					&&("10100".equals(fdInvoiceType)||"10101".equals(fdInvoiceType)||"10102".equals(fdInvoiceType)||"10103".equals(fdInvoiceType)||"30100".equals(fdInvoiceType))){
				if(company.getFdDutyParagraph().equals(fdTaxNumber)&&company.getFdName().equals(fdPurchName)){
					fdIsCurrent="1";  //是本公司发票
				}else{
					fdIsCurrent="0";  //不是本公司发票
				}
			}
		}
		return fdIsCurrent;
	}
}
