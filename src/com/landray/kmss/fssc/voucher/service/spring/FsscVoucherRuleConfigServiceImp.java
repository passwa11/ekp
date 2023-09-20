package com.landray.kmss.fssc.voucher.service.spring;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;
import com.landray.kmss.fssc.voucher.model.FsscVoucherModelConfig;
import com.landray.kmss.fssc.voucher.model.FsscVoucherRuleConfig;
import com.landray.kmss.fssc.voucher.model.FsscVoucherRuleDetail;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherModelConfigService;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherRuleConfigService;
import com.landray.kmss.fssc.voucher.util.FsscVoucherUtil;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

public class FsscVoucherRuleConfigServiceImp extends ExtendDataServiceImp implements IFsscVoucherRuleConfigService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    private IFsscVoucherModelConfigService fsscVoucherModelConfigService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscVoucherRuleConfig) {
            FsscVoucherRuleConfig fsscVoucherRuleConfig = (FsscVoucherRuleConfig) model;
            fsscVoucherRuleConfig.setDocAlterTime(new Date());
            fsscVoucherRuleConfig.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscVoucherRuleConfig fsscVoucherRuleConfig = new FsscVoucherRuleConfig();
        fsscVoucherRuleConfig.setFdIsAvailable(Boolean.valueOf("true"));
        fsscVoucherRuleConfig.setDocCreateTime(new Date());
        fsscVoucherRuleConfig.setDocAlterTime(new Date());
        fsscVoucherRuleConfig.setDocCreator(UserUtil.getUser());
        fsscVoucherRuleConfig.setDocAlteror(UserUtil.getUser());
        FsscVoucherUtil.initModelFromRequest(fsscVoucherRuleConfig, requestContext);
        return fsscVoucherRuleConfig;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscVoucherRuleConfig fsscVoucherRuleConfig = (FsscVoucherRuleConfig) model;
    }

    @Override
    public List<FsscVoucherRuleConfig> findByFdVoucherModelConfig(FsscVoucherModelConfig fdVoucherModelConfig) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscVoucherRuleConfig.fdVoucherModelConfig.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdVoucherModelConfig.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscVoucherRuleConfig> findByFdVoucherType(EopBasedataVoucherType fdVoucherType) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscVoucherRuleConfig.fdVoucherType.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdVoucherType.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscVoucherRuleConfig> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscVoucherRuleConfig.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscVoucherRuleConfig> findByFdCurrency(EopBasedataCurrency fdCurrency) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscVoucherRuleConfig.fdCurrency.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCurrency.getFdId());
        return this.findList(hqlInfo);
    }

    /**
     * 获取凭证规则
     * @param fsscVoucherModelConfig
     * @param fdCategoryId
     * @return
     * @throws Exception
     */
    @Override
    public List<FsscVoucherRuleConfig> getFsscVoucherRuleConfig(FsscVoucherModelConfig fsscVoucherModelConfig, String fdCategoryId) throws Exception{
        if(fsscVoucherModelConfig == null){
            return null;
        }
        //如果有分类Model,没有分类id
        /*if(StringUtil.isNotNull(fsscVoucherModelConfig.getFdCategoryName()) && StringUtil.isNull(fdCategoryId)){
            return null;
        }*/
        HQLInfo hqlInfo = new HQLInfo();
        StringBuilder where = new StringBuilder();
        where.append(" fsscVoucherRuleConfig.fdIsAvailable = :fdIsAvailable ");
        hqlInfo.setParameter("fdIsAvailable",true);
        where.append(" and fsscVoucherRuleConfig.fdVoucherModelConfig.fdId = :fdVoucherModelConfigId ");
        hqlInfo.setParameter("fdVoucherModelConfigId", fsscVoucherModelConfig.getFdId());
        if(StringUtil.isNotNull(fdCategoryId)){
            where.append(" and (fsscVoucherRuleConfig.fdCategoryId = :fdCategoryId or fsscVoucherRuleConfig.fdCategoryId is null or fsscVoucherRuleConfig.fdCategoryId = '') ");
            hqlInfo.setParameter("fdCategoryId", fdCategoryId);
        }
        hqlInfo.setWhereBlock(where.toString());
        return this.findList(hqlInfo);
    }
    public FsscVoucherRuleConfig getFsscVoucherRuleConfig(String fdName, String fdVoucherModelConfigId) throws Exception {
        if(StringUtil.isNull(fdName) || StringUtil.isNull(fdVoucherModelConfigId)){
            return null;
        }
        HQLInfo hqlInfo = new HQLInfo();
        StringBuilder where = new StringBuilder();
        where.append(" fsscVoucherRuleConfig.fdIsAvailable = :fdIsAvailable ");
        hqlInfo.setParameter("fdIsAvailable",true);
        where.append(" and fsscVoucherRuleConfig.fdVoucherModelConfig.fdId = :fdVoucherModelConfigId" +
                " and fsscVoucherRuleConfig.fdName = :fdName ");
        hqlInfo.setParameter("fdName", fdName);
        hqlInfo.setParameter("fdVoucherModelConfigId", fdVoucherModelConfigId);
        hqlInfo.setWhereBlock(where.toString());
        List<FsscVoucherRuleConfig> list = this.findList(hqlInfo);
        return ArrayUtil.isEmpty(list)?null:list.get(0);
    }

    /**
     * 初始化
     * @return
     * @throws Exception
     */
    @Override
    public String updateInit(String fileName) throws Exception{
        String path = ConfigLocationsUtil.getWebContentPath();
        String dir = path+"/fssc/voucher/resource/json/"+fileName;
        File filePath = new File(dir);
        List<Map<String, Object>> list = null;

        //读取文件
        String input = FileUtils.readFileToString(filePath, "UTF-8");
        //将读取的数据转换为JSONObject
        Map<String, Object> map = JSONObject.fromObject(input);

        if (map != null) {
            list = (List<Map<String, Object>>) map.get("datas");
        }
        if(ArrayUtil.isEmpty(list)){
            return null;
        }
        Iterator iter =  list.iterator();
        Map<String, Object> tempMap = null;
        FsscVoucherRuleConfig ruleMain = null;
        FsscVoucherRuleDetail ruleDetail = null;
        while (iter.hasNext()){
            tempMap = JSONObject.fromObject(iter.next());
            Iterator tempMapIter = tempMap.keySet().iterator();
            FsscVoucherModelConfig fsscVoucherModelConfig = fsscVoucherModelConfigService.getFsscVoucherModelConfig(tempMap.get("fdVoucherModelConfigModelName")+"");
            if(fsscVoucherModelConfig==null){
                continue;
            }
            ruleMain = getFsscVoucherRuleConfig(tempMap.get("fdName")+"", fsscVoucherModelConfig.getFdId());
            if(ruleMain == null){
                ruleMain = new FsscVoucherRuleConfig();
            }
            ruleMain.setFdVoucherModelConfig(fsscVoucherModelConfig);
            ruleMain.setFdCategoryModelName(fsscVoucherModelConfig.getFdCategoryName());
            List<FsscVoucherRuleDetail> detailList = new ArrayList<>();
            //主表信息
            while (tempMapIter.hasNext()){
                String key = (String) tempMapIter.next();  //获取map的key
                if("fdVoucherModelConfigModelName".equals(key)){
                    continue;
                }
                if("fdDetails".equals(key)){
                    List<Map<String, Object>> detailListMap = (List<Map<String, Object>>) tempMap.get(key);
                    Iterator detailListMapIter = detailListMap.iterator();
                    //明细
                    while (detailListMapIter.hasNext()){
                        Map<String, Object> detailMap = JSONObject.fromObject(detailListMapIter.next());
                        Iterator detailMapIter = detailMap.keySet().iterator();
                        ruleDetail = new FsscVoucherRuleDetail();
                        while (detailMapIter.hasNext()){
                            String detailKey = (String) detailMapIter.next();  //获取map的key
                            Object detailValue = detailMap.get(detailKey);  //得到value的值
                            PropertyUtils.setProperty(ruleDetail, detailKey, detailValue);
                        }
                        detailList.add(ruleDetail);
                    }
                }else{
                    Object value = tempMap.get(key);  //得到value的值
                    PropertyUtils.setProperty(ruleMain, key, value);
                }
            }
            if(ruleMain.getFdDetail() != null){
                ruleMain.getFdDetail().clear();
                ruleMain.getFdDetail().addAll(detailList);
            }else{
                ruleMain.setFdDetail(detailList);
            }
            this.update(ruleMain);
        }
        return null;
    }
    
    
    /**
     * 复制
     * @throws Exception 
     */
	@Override
	public void updateCopyDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] ids = request.getParameterValues("List_Selected");
		List<FsscVoucherRuleConfig> paymentList= this.findByPrimaryKeys(ids);
		for (FsscVoucherRuleConfig rule : paymentList) {
			FsscVoucherRuleConfig ruleConfig = new FsscVoucherRuleConfig();
			ruleConfig.setFdName(ResourceUtil.getString("button.copy", "eop-basedata")+" "+rule.getFdName());
			ruleConfig.setFdVoucherTextFormula(rule.getFdVoucherTextFormula());
			ruleConfig.setFdVoucherTextText(rule.getFdVoucherTextText());
			ruleConfig.setFdVoucherModelConfig(rule.getFdVoucherModelConfig());
			ruleConfig.setFdCompanyFlag(rule.getFdCompanyFlag());
			ruleConfig.setFdCompany(rule.getFdCompany());
			ruleConfig.setFdCompanyFormula(rule.getFdCompanyFormula());
			ruleConfig.setFdCompanyText(rule.getFdCompanyText());
			ruleConfig.setFdCurrency(rule.getFdCurrency());
			ruleConfig.setFdCurrencyFlag(rule.getFdCurrencyFlag());
			ruleConfig.setFdCurrencyFormula(rule.getFdCurrencyFormula());
			ruleConfig.setFdCurrencyText(rule.getFdCurrencyText());
			ruleConfig.setDocCreateTime(new Date());
			ruleConfig.setDocCreator(UserUtil.getUser());
			ruleConfig.setFdIsAvailable(true);
			ruleConfig.setFdModelNumberFormula(rule.getFdModelNumberFormula());
			ruleConfig.setFdModelNumberText(rule.getFdModelNumberText());
			ruleConfig.setFdOrderMakingPerson(rule.getFdOrderMakingPerson());
			ruleConfig.setFdOrderMakingPersonFlag(rule.getFdOrderMakingPersonFlag());
			ruleConfig.setFdOrderMakingPersonFormula(rule.getFdOrderMakingPersonFormula());
			ruleConfig.setFdOrderMakingPersonText(rule.getFdOrderMakingPersonText());
			ruleConfig.setFdPushType(rule.getFdPushType());
			ruleConfig.setFdNumberFormula(rule.getFdNumberFormula());
			ruleConfig.setFdNumberText(rule.getFdNumberText());
			ruleConfig.setFdModelNumberFormula(rule.getFdModelNumberFormula());
			ruleConfig.setFdModelNumberText(rule.getFdModelNumberText());
			ruleConfig.setFdRuleText(rule.getFdRuleText());
			ruleConfig.setFdRuleFormula(rule.getFdRuleFormula());
			ruleConfig.setFdVoucherDateFormula(rule.getFdVoucherDateFormula());
			ruleConfig.setFdVoucherDateText(rule.getFdVoucherDateText());
			ruleConfig.setFdCategoryModelName(rule.getFdCategoryModelName());
			ruleConfig.setFdVoucherType(rule.getFdVoucherType());
			ruleConfig.setFdVoucherTypeFlag(rule.getFdVoucherTypeFlag());
			ruleConfig.setFdVoucherTypeFormula(rule.getFdVoucherTypeFormula());
			ruleConfig.setFdVoucherTypeText(rule.getFdVoucherTypeText());
			//ruleConfig.setFdCategoryId(rule.getFdCategoryId());
			//ruleConfig.setFdCategoryName(rule.getFdCategoryName());
			List<FsscVoucherRuleDetail>  list = rule.getFdDetail();
			List<FsscVoucherRuleDetail>  newList = new ArrayList<FsscVoucherRuleDetail>();
			for (FsscVoucherRuleDetail detail : list) {
				FsscVoucherRuleDetail ruleDetail = new FsscVoucherRuleDetail();
				ruleDetail.setFdBaseAccounts(detail.getFdBaseAccounts());
				ruleDetail.setFdBaseAccountsFlag(detail.getFdBaseAccountsFlag());
				ruleDetail.setFdBaseAccountsFormula(detail.getFdBaseAccountsFormula());
				ruleDetail.setFdBaseAccountsText(detail.getFdBaseAccountsText());
				ruleDetail.setFdBaseCostCenter(detail.getFdBaseCostCenter());
				ruleDetail.setFdBaseCostCenterFlag(detail.getFdBaseCostCenterFlag());
				ruleDetail.setFdBaseCostCenterFormula(detail.getFdBaseCostCenterFormula());
				ruleDetail.setFdBaseCostCenterText(detail.getFdBaseCostCenterText());
				ruleDetail.setFdBaseCustomer(detail.getFdBaseCustomer());
				ruleDetail.setFdBaseCustomerFlag(detail.getFdBaseCustomerFlag());
				ruleDetail.setFdBaseCustomerFormula(detail.getFdBaseCustomerFormula());
				ruleDetail.setFdBaseCustomerText(detail.getFdBaseCustomerText());
				ruleDetail.setFdBaseCashFlow(detail.getFdBaseCashFlow());
				ruleDetail.setFdBaseCashFlowFlag(detail.getFdBaseCashFlowFlag());
				ruleDetail.setFdBaseCashFlowFormula(detail.getFdBaseCashFlowFormula());
				ruleDetail.setFdBaseCashFlowText(detail.getFdBaseCashFlowText());
				ruleDetail.setFdBaseErpPerson(detail.getFdBaseErpPerson());
				ruleDetail.setFdBaseErpPersonFlag(detail.getFdBaseErpPersonFlag());
				ruleDetail.setFdBaseErpPersonFormula(detail.getFdBaseErpPersonFormula());
				ruleDetail.setFdBaseErpPersonText(detail.getFdBaseErpPersonText());
				ruleDetail.setFdBaseInnerOrder(detail.getFdBaseInnerOrder());
				ruleDetail.setFdBaseInnerOrderFlag(detail.getFdBaseInnerOrderFlag());
				ruleDetail.setFdBaseInnerOrderFormula(detail.getFdBaseInnerOrderFormula());
				ruleDetail.setFdBaseInnerOrderText(detail.getFdBaseInnerOrderText());
				ruleDetail.setFdBasePayBank(detail.getFdBasePayBank());
				ruleDetail.setFdBasePayBankFlag(detail.getFdBasePayBankFlag());
				ruleDetail.setFdBasePayBankFormula(detail.getFdBasePayBankFormula());
				ruleDetail.setFdBasePayBankText(detail.getFdBasePayBankText());
				ruleDetail.setFdBaseProject(detail.getFdBaseProject());
				ruleDetail.setFdBaseProjectFlag(detail.getFdBaseProjectFlag());
				ruleDetail.setFdBaseProjectFormula(detail.getFdBaseProjectFormula());
				ruleDetail.setFdBaseProjectText(detail.getFdBaseProjectText());
				ruleDetail.setFdBaseSupplier(detail.getFdBaseSupplier());
				ruleDetail.setFdBaseSupplierFlag(detail.getFdBaseSupplierFlag());
				ruleDetail.setFdBaseSupplierFormula(detail.getFdBaseSupplierFormula());
				ruleDetail.setFdBaseSupplierText(detail.getFdBaseSupplierText());
				ruleDetail.setFdBaseWbs(detail.getFdBaseWbs());
				ruleDetail.setFdBaseWbsFlag(detail.getFdBaseWbsFlag());
				ruleDetail.setFdBaseWbsFormula(detail.getFdBaseWbsFormula());
				ruleDetail.setFdBaseWbsText(detail.getFdBaseWbsText());
				ruleDetail.setFdIsPayment(detail.getFdIsPayment());
				ruleDetail.setFdMoneyFormula(detail.getFdMoneyFormula());
				ruleDetail.setFdMoneyText(detail.getFdMoneyText());
				ruleDetail.setFdRuleFormula(detail.getFdRuleFormula());
				ruleDetail.setFdRuleText(detail.getFdRuleText());
				ruleDetail.setFdTypeFormula(detail.getFdTypeFormula());
				ruleDetail.setFdTypeText(detail.getFdTypeText());
				ruleDetail.setFdVoucherTextFormula(detail.getFdVoucherTextFormula());
				ruleDetail.setFdVoucherTextText(detail.getFdVoucherTextText());
				newList.add(ruleDetail);
			}
			ruleConfig.setFdDetail(newList);
			this.add(ruleConfig);
		}
	}


    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    public void setFsscVoucherModelConfigService(IFsscVoucherModelConfigService fsscVoucherModelConfigService) {
        this.fsscVoucherModelConfigService = fsscVoucherModelConfigService;
    }
}
