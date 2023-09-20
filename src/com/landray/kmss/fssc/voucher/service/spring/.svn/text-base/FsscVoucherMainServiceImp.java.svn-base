package com.landray.kmss.fssc.voucher.service.spring;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.landray.kmss.fssc.common.util.FsscCommonProcessUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCashFlow;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataCustomer;
import com.landray.kmss.eop.basedata.model.EopBasedataErpPerson;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataPayBank;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.eop.basedata.service.IEopBasedataExchangeRateService;
import com.landray.kmss.eop.basedata.service.IEopBasedataProjectService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonCashierPaymentService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonEasService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonK3CloudService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonK3Service;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLedgerContractService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonU8Service;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.voucher.constant.FsscVoucherConstant;
import com.landray.kmss.fssc.voucher.forms.FsscVoucherDetailForm;
import com.landray.kmss.fssc.voucher.model.FsscVoucherDetail;
import com.landray.kmss.fssc.voucher.model.FsscVoucherMain;
import com.landray.kmss.fssc.voucher.model.FsscVoucherModelConfig;
import com.landray.kmss.fssc.voucher.model.FsscVoucherRuleConfig;
import com.landray.kmss.fssc.voucher.model.FsscVoucherRuleDetail;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherMainService;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherModelConfigService;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherRuleConfigService;
import com.landray.kmss.fssc.voucher.util.FsscVoucherUtil;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmNode;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmWorkitem;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainForm;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class FsscVoucherMainServiceImp extends ExtendDataServiceImp implements IFsscVoucherMainService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(FsscVoucherMainServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    private ISysNumberFlowService sysNumberFlowService;

    private IFsscCommonU8Service fsscCommonU8Service;

    private IFsscCommonEasService fsscCommonEasService;
    
    private IFsscCommonK3Service fsscCommonK3Service;
    
    private IFsscCommonK3CloudService fsscCommonK3CloudService;
    
    private IEopBasedataProjectService eopBasedataProjectService;

    private IFsscCommonCashierPaymentService fsscCommonCashierPaymentService;

    private IFsscVoucherRuleConfigService fsscVoucherRuleConfigService;

    private IFsscVoucherModelConfigService fsscVoucherModelConfigService;
    
    private  ISysOrgCoreService sysOrgCoreService;
    
    public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}
    
    protected IBackgroundAuthService backgroundAuthService;
    
    public void setBackgroundAuthService(IBackgroundAuthService backgroundAuthService) {
		this.backgroundAuthService = backgroundAuthService;
	}

	protected ILbpmProcessService lbpmProcessService;
	
	public void setLbpmProcessService(ILbpmProcessService lbpmProcessService) {
		this.lbpmProcessService = lbpmProcessService;
	}
	
	private IFsscCommonLedgerContractService fsscLedgerContractCommonService;
    
    public IFsscCommonLedgerContractService getFsscLedgerContractCommonService() {
    	 if (fsscLedgerContractCommonService == null) {
    		 fsscLedgerContractCommonService = (IFsscCommonLedgerContractService) SpringBeanUtil.getBean("fsscLedgerContractCommonService");
         }
		return fsscLedgerContractCommonService;
	}
    
	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscVoucherMain) {
            FsscVoucherMain fsscVoucherMain = (FsscVoucherMain) model;
            fsscVoucherMain.setDocAlterTime(new Date());
            fsscVoucherMain.setDocAlteror(UserUtil.getUser());
            if (fsscVoucherMain.getDocNumber() == null) {
                fsscVoucherMain.setDocNumber(sysNumberFlowService.generateFlowNumber(fsscVoucherMain));
            }
            if(StringUtil.isNull(fsscVoucherMain.getFdBookkeepingStatus())){
                fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_10);//记账状态：待记账
            }
        }
        return model;
    }

    @Override
    public IExtendForm convertBizModelToForm(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        form = super.convertBizModelToForm(form, model, context);
        this.sysMetadataService.convertModelToForm(form, model, context.getRequestContext());
        if(model instanceof  FsscVoucherDetail){
            FsscVoucherDetail fsscVoucherDetail = (FsscVoucherDetail) model;
            FsscVoucherDetailForm fsscVoucherDetailForm = (FsscVoucherDetailForm) form;
            fsscVoucherDetailForm.setFdMoney(FsscNumberUtil.doubleToUp(fsscVoucherDetail.getFdMoney())+"");
            return fsscVoucherDetailForm;
        }
        return form;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscVoucherMain fsscVoucherMain = new FsscVoucherMain();
        fsscVoucherMain.setDocCreateTime(new Date());
        fsscVoucherMain.setDocAlterTime(new Date());
        fsscVoucherMain.setDocCreator(UserUtil.getUser());
        fsscVoucherMain.setDocAlteror(UserUtil.getUser());
        fsscVoucherMain.setFdVoucherCreateType(FsscVoucherConstant.FSSC_VOUCHER_DELETE_TYPE_MANUALLYCREATE);//凭证生成类型  手动创建
        FsscVoucherUtil.initModelFromRequest(fsscVoucherMain, requestContext);

        return fsscVoucherMain;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscVoucherMain fsscVoucherMain = (FsscVoucherMain) model;
    }

    @Override
    public void update(IBaseModel modelObj) throws Exception {
        FsscVoucherMain fsscVoucherMain = (FsscVoucherMain) modelObj;
        if(StringUtil.isNull(fsscVoucherMain.getDocNumber())){
            fsscVoucherMain.setDocNumber(sysNumberFlowService.generateFlowNumber(fsscVoucherMain));
        }
        if(StringUtil.isNull(fsscVoucherMain.getFdBookkeepingStatus())){
            fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_10);//记账状态：待记账
        }
        super.update(fsscVoucherMain);
    }

    @Override
    public List<FsscVoucherMain> findByFdBaseCurrency(EopBasedataCurrency fdBaseCurrency) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscVoucherMain.fdBaseCurrency.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdBaseCurrency.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscVoucherMain> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscVoucherMain.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscVoucherMain> findByDeleteWhere(String fdVoucherCreateType, String fdModelId, String fdModelName, Date fdVoucherDate) throws Exception {
        if(StringUtil.isNull(fdModelId) || StringUtil.isNull(fdModelName)){
            return new ArrayList<FsscVoucherMain>();
        }
        HQLInfo hqlInfo = new HQLInfo();
        StringBuffer where = new StringBuffer();
        where.append(" fsscVoucherMain.fdModelId=:fdModelId and fsscVoucherMain.fdModelName=:fdModelName ");
        hqlInfo.setParameter("fdModelId", fdModelId);
        hqlInfo.setParameter("fdModelName", fdModelName);
        if(StringUtil.isNotNull(fdVoucherCreateType)){
            where.append(" and fsscVoucherMain.fdVoucherCreateType=:fdVoucherCreateType ");
            hqlInfo.setParameter("fdVoucherCreateType", fdVoucherCreateType);
        }
        if(fdVoucherDate != null){
            String fdAccountingYear = DateUtil.convertDateToString(fdVoucherDate, "yyyy");
            String fdPeriod = DateUtil.convertDateToString(fdVoucherDate, "MM");
            where.append(" and fsscVoucherMain.fdAccountingYear = :fdAccountingYear and fsscVoucherMain.fdPeriod = :fdPeriod ");
            hqlInfo.setParameter("fdAccountingYear", fdAccountingYear);
            hqlInfo.setParameter("fdPeriod", fdPeriod);
        }
        hqlInfo.setWhereBlock(where.toString());
        return this.findList(hqlInfo);
    }

    /**
     * 查询未记账或记账失败的凭证
     * @param fdModelId
     * @param fdModelName
     * @param fdPushType 推送方式
     * @param fdIsAmortize 是否是摊销 1是 0不是 否则不添加此条件
     * @return
     * @throws Exception
     */
    @Override
    public List<FsscVoucherMain> findNoBookkeeping(String fdModelId, String fdModelName, String fdPushType, String fdIsAmortize) throws Exception {
        if(StringUtil.isNull(fdModelId) || StringUtil.isNull(fdModelName)){
            return new ArrayList<FsscVoucherMain>();
        }
        HQLInfo hqlInfo = new HQLInfo();
        StringBuffer where = new StringBuffer();
        where.append(" fsscVoucherMain.fdBookkeepingStatus in ('10','11') and fsscVoucherMain.fdModelId=:fdModelId and fsscVoucherMain.fdModelName=:fdModelName ");
        if(StringUtil.isNotNull(fdPushType)){
            where.append(" and fsscVoucherMain.fdPushType = :fdPushType ");
        }
        if(StringUtil.isNotNull(fdIsAmortize) && (FsscVoucherConstant.FSSC_VOUCHER_FD_IS_AMORTIZE_TRUE.equals(fdIsAmortize)
                || FsscVoucherConstant.FSSC_VOUCHER_FD_IS_AMORTIZE_FALSE.equals(fdIsAmortize))){
            where.append(" and fsscVoucherMain.fdIsAmortize = '"+fdIsAmortize+"' ");
        }
        hqlInfo.setWhereBlock(where.toString());
        hqlInfo.setParameter("fdModelId", fdModelId);
        hqlInfo.setParameter("fdModelName", fdModelName);
        if(StringUtil.isNotNull(fdPushType)){
            hqlInfo.setParameter("fdPushType", fdPushType);//1手动推送 2自动
        }
        return this.findList(hqlInfo);
    }

    /**
     * 查询是否没有未记账和记账失败的凭证(除摊销凭证)
     * @param fdModelId
     * @param fdModelName
     * @return
     * @throws Exception
     */
    public boolean isNoBookkeeping(String fdModelId, String fdModelName) throws Exception {
        List<FsscVoucherMain> list = findNoBookkeeping(fdModelId, fdModelName, null, FsscVoucherConstant.FSSC_VOUCHER_FD_IS_AMORTIZE_FALSE);
        return ArrayUtil.isEmpty(list)?true:false;
    }

    /**
     * 查询未记账或记账失败的自动推送方式的凭证
     * @return
     * @throws Exception
     */
    @Override
    public List<FsscVoucherMain> findNoBookkeeping(Date date, String fdPushType) throws Exception {
        String fdAccountingYear = DateUtil.convertDateToString(date, "yyyy");
        String fdPeriod = DateUtil.convertDateToString(date, "MM");
        HQLInfo hqlInfo = new HQLInfo();
        StringBuffer where = new StringBuffer();
        where.append(" fsscVoucherMain.fdBookkeepingStatus in ('10','11')");
        where.append(" and fsscVoucherMain.fdAccountingYear = :fdAccountingYear and fsscVoucherMain.fdPeriod = :fdPeriod ");
        if(StringUtil.isNotNull(fdPushType)){
            where.append(" and fsscVoucherMain.fdPushType = :fdPushType ");
        }
        hqlInfo.setWhereBlock(where.toString());
        if(StringUtil.isNotNull(fdPushType)){
            hqlInfo.setParameter("fdPushType", fdPushType);//1手动推送 2自动
        }
        hqlInfo.setParameter("fdAccountingYear", fdAccountingYear);
        hqlInfo.setParameter("fdPeriod", fdPeriod);
        return this.findList(hqlInfo);
    }

    /**
     * 查询所有凭证
     * @param fdModelId
     * @param fdModelName
     * @return
     * @throws Exception
     */
    @Override
    public List<FsscVoucherMain> getFsscVoucherMain(String fdModelId, String fdModelName) throws Exception{
        if(StringUtil.isNull(fdModelId) || StringUtil.isNull(fdModelName)){
            return new ArrayList<>();
        }
        HQLInfo hqlInfo = new HQLInfo();
        StringBuffer where = new StringBuffer();
        where.append(" fsscVoucherMain.fdModelId=:fdModelId and fsscVoucherMain.fdModelName=:fdModelName ");
        hqlInfo.setWhereBlock(where.toString());
        hqlInfo.setParameter("fdModelId", fdModelId);
        hqlInfo.setParameter("fdModelName", fdModelName);
        hqlInfo.setOrderBy(" fsscVoucherMain.docCreateTime ");
        return this.findList(hqlInfo);
    }

    /**
     * 删除对应的所有摊销凭证
     * @param fdModelId
     * @param fdModelName
     * @return
     * @throws Exception
     */
    public void deleteAllAmortizeVoucher(String fdModelId, String fdModelName) throws Exception{
        if(StringUtil.isNull(fdModelId) || StringUtil.isNull(fdModelName)){
            return ;
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock(" fsscVoucherMain.fdId ");
        StringBuilder where = new StringBuilder();
        where.append(" fsscVoucherMain.fdIsAmortize = '1' and fsscVoucherMain.fdBookkeepingStatus in ('10','11') and fsscVoucherMain.fdModelId = :fdModelId and fsscVoucherMain.fdModelName = :fdModelName  ");
        hqlInfo.setParameter("fdModelId", fdModelId);
        hqlInfo.setParameter("fdModelName", fdModelName);
        hqlInfo.setWhereBlock(where.toString());
        List<String> idList = this.findList(hqlInfo);
        String[] ids = new String[idList.size()];
        idList.toArray(ids);
        this.delete(ids);
    }

    /**
     * 获取对应的所有摊销凭证
     * @param fdModelId
     * @param fdModelName
     * @return
     * @throws Exception
     */
    public int getAllAmortizeVoucher(String fdModelId, String fdModelName) throws Exception{
        if(StringUtil.isNull(fdModelId) || StringUtil.isNull(fdModelName)){
            return 0;
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock(" fsscVoucherMain.fdId ");
        StringBuilder where = new StringBuilder();
        where.append(" fsscVoucherMain.fdIsAmortize = '1' and fsscVoucherMain.fdModelId = :fdModelId and fsscVoucherMain.fdModelName = :fdModelName  ");
        hqlInfo.setParameter("fdModelId", fdModelId);
        hqlInfo.setParameter("fdModelName", fdModelName);
        hqlInfo.setWhereBlock(where.toString());
        List<String> idList = this.findList(hqlInfo);
        return ArrayUtil.isEmpty(idList)?0:idList.size();
    }

    /**
     * 生成凭证
     * @param model
     * @throws Exception
     */
    @Override
    public void addOrUpdateVoucher(IBaseModel model) throws Exception{
        //获取凭证模块
        FsscVoucherModelConfig fsscVoucherModelConfig = fsscVoucherModelConfigService.getFsscVoucherModelConfig(ModelUtil.getModelClassName(model));
        if(fsscVoucherModelConfig == null){
            return;
        }
        String fdCategoryId = null;
        //校验对应分类或者模版的字段名
        if(StringUtil.isNotNull(fsscVoucherModelConfig.getFdCategoryName()) && StringUtil.isNotNull(fsscVoucherModelConfig.getFdCategoryPropertyName())){
            fdCategoryId = PropertyUtils.getProperty(PropertyUtils.getProperty(model, fsscVoucherModelConfig.getFdCategoryPropertyName()), "fdId")+"";
        }

        SysOrgPerson anonymousUser = UserUtil.getAnonymousUser().getPerson();
        FormulaParser parser = FormulaParser.getInstance(model);
        List<FsscVoucherRuleConfig> fsscVoucherRuleConfigList = fsscVoucherRuleConfigService.getFsscVoucherRuleConfig(fsscVoucherModelConfig, fdCategoryId);
        for (int i=0;i<fsscVoucherRuleConfigList.size();i++){
            FsscVoucherRuleConfig fsscVoucherRuleConfig = fsscVoucherRuleConfigList.get(i);
            Object fdRuleFormulaObj = parser.parseValueScript(fsscVoucherRuleConfig.getFdRuleFormula());
            if(!((fdRuleFormulaObj instanceof String && "true".equals(fdRuleFormulaObj+""))
                    || (fdRuleFormulaObj instanceof Boolean && (Boolean) fdRuleFormulaObj))){//不满足规则
                continue;
            }
            SysOrgPerson fdOrderMakingPerson = (SysOrgPerson) getChooseObject("fdOrderMakingPerson", parser, fsscVoucherRuleConfig);

            //如果制单人为空，取匿名用户
            if(fdOrderMakingPerson == null){
                fdOrderMakingPerson = anonymousUser;
            }
            addOrUpdateVoucher(model, parser, fsscVoucherRuleConfig, fdOrderMakingPerson);
        }
    }

    private void addOrUpdateVoucher(IBaseModel model, FormulaParser parser, FsscVoucherRuleConfig fsscVoucherRuleConfig, SysOrgPerson person) throws Exception{
        System.out.println("开始生成凭证："+fsscVoucherRuleConfig.getFdName());
        logger.info("开始生成凭证："+fsscVoucherRuleConfig.getFdName());
        Object fdVoucherDateObj = parser.parseValueScript(fsscVoucherRuleConfig.getFdVoucherDateFormula());
        String fdModelName = ModelUtil.getModelClassName(model);
        String fdModelNumber = parser.parseValueScript(fsscVoucherRuleConfig.getFdModelNumberFormula())+"";
        String fdModelId = getFdModelId(fdModelName, fdModelNumber, fsscVoucherRuleConfig.getFdModelNumberFormula().replaceAll("\\$", ""));
        if(StringUtil.isNull(fdModelId)){
            return;
        }
        if(fdVoucherDateObj instanceof List){//待摊凭证
            addOrUpdateVoucherAmortize(person, parser, fsscVoucherRuleConfig, fdModelId, fdModelName, fdModelNumber);
        }else{//
            List<FsscVoucherMain> mainList = this.findByDeleteWhere(fsscVoucherRuleConfig.getFdId(), fdModelId, fdModelName, null);
            FsscVoucherMain main = null;
            if(!ArrayUtil.isEmpty(mainList)){
                main = mainList.get(0);
                if(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30.equals(main.getFdBookkeepingStatus())){//如果是已记账就跳过
                    return;
                }
            }else{
                main = new FsscVoucherMain();
            }
            EopBasedataCompany fdCompany = (EopBasedataCompany) getChooseObject("fdCompany", parser, fsscVoucherRuleConfig);
            EopBasedataVoucherType fdVoucherType = (EopBasedataVoucherType) getChooseObject("fdVoucherType", parser, fsscVoucherRuleConfig);
            EopBasedataCurrency fdCurrency = (EopBasedataCurrency) getChooseObject("fdCurrency", parser, fsscVoucherRuleConfig );
            Date fdVoucherDate = (Date) fdVoucherDateObj;
            String fdVoucherDateStr = DateUtil.convertDateToString(fdVoucherDate, "yyyy-MM-dd");

            Object fdNumberObj = parser.parseValueScript(fsscVoucherRuleConfig.getFdNumberFormula());
            int fdNumber = 1;
            if(fdNumberObj instanceof String){
                fdNumber = Integer.valueOf(fdNumberObj+"");
            }else if(fdNumberObj instanceof Integer){
                fdNumber = (int) fdNumberObj;
            }
            String fdVoucherText = parser.parseValueScript(fsscVoucherRuleConfig.getFdVoucherTextFormula())+"";

            main.setFdVoucherCreateType(fsscVoucherRuleConfig.getFdId());
            main.setFdPushType(fsscVoucherRuleConfig.getFdPushType());//推送方式
            main.setFdModelId(fdModelId);
            main.setFdModelName(fdModelName);
            main.setFdModelNumber(fdModelNumber);
            main.setFdCompany(fdCompany);//公司
            main.setFdBaseVoucherType(fdVoucherType);//凭证类型
            main.setFdBaseCurrency(fdCurrency);//币种
            main.setFdVoucherDate(fdVoucherDate);//凭证日期
            main.setFdAccountingYear(fdVoucherDateStr.substring(0, 4));//会计年度
            main.setFdPeriod(fdVoucherDateStr.substring(5, 7));//期间
            main.setFdNumber(fdNumber);//单据数
            main.setFdVoucherText(fdVoucherText);//凭证抬头文本
            main.setDocCreator(person);//制单人
            main.setFdMergeEntry(fsscVoucherRuleConfig.getFdMergeEntry() != null ? fsscVoucherRuleConfig.getFdMergeEntry() : null);//合并分录

            List<FsscVoucherRuleDetail> detailList = fsscVoucherRuleConfig.getFdDetail();
            if(ArrayUtil.isEmpty(detailList)){
                return;
            }
            double fdTotoalMoney1 = 0;//借方总金额

            List<FsscVoucherDetail> voucherDetails = new ArrayList<FsscVoucherDetail>();
            FsscVoucherDetail voucherDetail = null;
            Map<String,FsscVoucherDetail> map = new HashMap<>();
            for(int i = 0; i<detailList.size(); i++){
                FsscVoucherRuleDetail detail = detailList.get(i);
                if(detail.getFdIsPayment()){//与付款单有关
                    getPaymentDetail(parser, voucherDetails, detail, fdTotoalMoney1, fsscVoucherRuleConfig.getFdName(), fdModelId, fdModelName);
                }else{
                    int sizeInt = 1;//生成明细条数
                    List fdMoneyList = new ArrayList();
                    Object fdMoneyObj = parser.parseValueScript(detail.getFdMoneyFormula());
                    if(fdMoneyObj instanceof  List){//金额为集合，生成多条明细
                        sizeInt = ((List) fdMoneyObj).size();
                        fdMoneyList.addAll((List) fdMoneyObj);
                    }else{
                        fdMoneyList.add(fdMoneyObj);
                    }
                    List fdRuleList = this.getList("fdRule", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                    if(ArrayUtil.isEmpty(fdRuleList)){
                        continue;
                    }
                    List fdTypeList = this.getList("fdType", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                    List fdBaseAccountsList = this.getChooseList("fdBaseAccounts", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                    List fdBaseCostCenterList = this.getChooseList("fdBaseCostCenter", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                    List fdBaseErpPersonList = this.getChooseList("fdBaseErpPerson", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                    List fdBaseCashFlowList = this.getChooseList("fdBaseCashFlow", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                    List fdBaseCustomerList = this.getChooseList("fdBaseCustomer", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                    List fdBaseSupplierList = this.getChooseList("fdBaseSupplier", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                    List fdBaseWbsList = this.getChooseList("fdBaseWbs", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                    List fdBaseInnerOrderList = this.getChooseList("fdBaseInnerOrder", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                    List fdBaseProjectList = this.getChooseList("fdBaseProject", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                    List fdBasePayBankList = this.getChooseList("fdBasePayBank", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                    List fdDeptList = this.getChooseList("fdDept", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());	//部门
                    List fdVoucherDetailTextList = this.getList("fdVoucherText", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                    List fdContractCodeList = this.getList("fdContractCode", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());	//合同编号
                    for(int j=0;j<sizeInt;j++){
                        Object fdRuleFormulaObj = fdRuleList.get(j);
                        if(!((fdRuleFormulaObj instanceof String && "true".equals(fdRuleFormulaObj+""))
                                || (fdRuleFormulaObj instanceof Boolean && (Boolean) fdRuleFormulaObj))){//不满足规则
                            continue;
                        }
                        Object fdTypeObj = fdTypeList.get(j);
                        String fdType = FsscVoucherConstant.FSSC_VOUCHER_FD_TYPE_1;//默认借
                        if(fdTypeObj instanceof String){
                            fdType = fdTypeObj+"";
                        }else if(fdTypeObj instanceof Integer){
                            fdType = fdTypeObj+"";
                        }

                        EopBasedataAccounts fdBaseAccounts = (EopBasedataAccounts) fdBaseAccountsList.get(j);//会计科目
                        EopBasedataCostCenter fdBaseCostCenter = (EopBasedataCostCenter) fdBaseCostCenterList.get(j);//成本中心
                        EopBasedataErpPerson fdBaseErpPerson = (EopBasedataErpPerson) fdBaseErpPersonList.get(j);//Erp个人
                        EopBasedataCashFlow fdBaseCashFlow = (EopBasedataCashFlow) fdBaseCashFlowList.get(j);//现金流量项目
                        EopBasedataCustomer fdBaseCustomer = (EopBasedataCustomer) fdBaseCustomerList.get(j);//客户
                        EopBasedataSupplier fdBaseSupplier = (EopBasedataSupplier) fdBaseSupplierList.get(j);//供应商
                        EopBasedataWbs fdBaseWbs = (EopBasedataWbs) fdBaseWbsList.get(j);//WBS号
                        EopBasedataInnerOrder fdBaseInnerOrder = (EopBasedataInnerOrder) fdBaseInnerOrderList.get(j);//内部订单
                        EopBasedataProject fdBaseProject = (EopBasedataProject) fdBaseProjectList.get(j);//项目
                        EopBasedataPayBank fdBasePayBank = (EopBasedataPayBank) fdBasePayBankList.get(j);//付款银行
                        SysOrgElement fdDept = (SysOrgElement) fdDeptList.get(j);//部门
                        String fdVoucherDetailText = fdVoucherDetailTextList.get(j)+"";//摘要文本
                        String fdContractCode = null!=fdContractCodeList.get(j)?fdContractCodeList.get(j)+"":"";//合同编号
                        double fdMoney = 0;
                        Object tempMoneyObj = fdMoneyList.get(j);
                        if(tempMoneyObj instanceof String){
                            fdMoney = FsscNumberUtil.doubleToUp(tempMoneyObj+"");
                        }else if(tempMoneyObj instanceof Double){
                            fdMoney = (Double) tempMoneyObj;
                        }

                        if(FsscVoucherConstant.FSSC_VOUCHER_FD_TYPE_1.equals(fdType)){
                            fdTotoalMoney1 = FsscNumberUtil.getAddition(fdTotoalMoney1, fdMoney);
                        }
                        voucherDetail = new FsscVoucherDetail();
                        voucherDetail.setFdType(fdType);//
                        voucherDetail.setFdBaseAccounts(fdBaseAccounts);//会计科目
                        voucherDetail.setFdBaseCostCenter(fdBaseCostCenter);//成本中心
                        voucherDetail.setFdBaseErpPerson(fdBaseErpPerson);//个人
                        voucherDetail.setFdBaseCashFlow(fdBaseCashFlow);//现金流量项目
                        voucherDetail.setFdBaseCustomer(fdBaseCustomer);//客户
                        voucherDetail.setFdBaseSupplier(fdBaseSupplier);//供应商
                        voucherDetail.setFdBaseProject(fdBaseProject);//项目
                        voucherDetail.setFdBasePayBank(fdBasePayBank);//银行
                        voucherDetail.setFdDept(fdDept);//部门
                        voucherDetail.setFdBaseWbs(fdBaseWbs);//WBS号
                        voucherDetail.setFdBaseInnerOrder(fdBaseInnerOrder);//内部订单
                        voucherDetail.setFdMoney(FsscNumberUtil.doubleToUp(fdMoney));//金额
                        voucherDetail.setFdVoucherText(fdVoucherDetailText);//
                        voucherDetail.setFdContractCode(fdContractCode);//合同编号
                        clearNotCostItemValue(voucherDetail);//清除没有勾选的辅助核算项值
                        voucherDetails.add(voucherDetail);
                        
                    }
                }
            }
            main.setFdModelMoney(fdTotoalMoney1);//单据总金额
            //判断分录合并
           FsscVoucherMain ma = judgeMergeEntry(main, fsscVoucherRuleConfig, voucherDetails);
           this.update(ma);
        }
        //写入成功，修改单据制证状态
        //固定记账状态字段为fdVoucherStatus，并且数据字典里面也要有
        SysDictModel dict = SysDataDict.getInstance().getModel(fdModelName);
        if(dict != null){
            Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
            if(StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdVoucherStatus") && dictMap.containsKey("fdBookkeepingStatus")){
                SysDictCommonProperty sysDictCommonPropertyVoucherS = dictMap.get("fdVoucherStatus");
                SysDictCommonProperty sysDictCommonPropertyBookkeeping = dictMap.get("fdBookkeepingStatus");
                StringBuffer sql = new StringBuffer();
                sql.append(" update ").append(dict.getTable());
                sql.append(" set ").append(sysDictCommonPropertyVoucherS.getColumn()).append(" = '"+FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_30 +"'");
                sql.append(",").append(sysDictCommonPropertyBookkeeping.getColumn()).append(" = '"+FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_10 +"'");
                sql.append(" where fd_id = '").append(fdModelId).append("' ");
                NativeQuery query=this.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
                query.addSynchronizedQuerySpace(dict.getTable());
                query.executeUpdate();
            }
        }
        System.out.println("生成凭证成功："+fsscVoucherRuleConfig.getFdName());
        logger.info("生成凭证成功："+fsscVoucherRuleConfig.getFdName());
    }
    
    /**
     * 判断分录合并
     * @param main
     * @param fsscVoucherRuleConfig
     * @param voucherDetails
     */
    private FsscVoucherMain judgeMergeEntry(FsscVoucherMain main,FsscVoucherRuleConfig fsscVoucherRuleConfig,List<FsscVoucherDetail> voucherDetails) {
    	if(main.getFdDetail() != null){
            main.getFdDetail().clear();
            //合并分录
            if(StringUtil.isNotNull(fsscVoucherRuleConfig.getFdMergeEntry()) && fsscVoucherRuleConfig.getFdMergeEntry().equals(FsscVoucherConstant.FSSC_VOUCHER_FD_MERGE_ENTRY_TRUE)) {
            	List<FsscVoucherDetail> mergeResult = compareAndMerge(voucherDetails);
            	main.getFdDetail().addAll(mergeResult);
            }else {
            	main.getFdDetail().addAll(voucherDetails);
            }
        }else{
        	//合并分录
        	if(StringUtil.isNotNull(fsscVoucherRuleConfig.getFdMergeEntry()) && fsscVoucherRuleConfig.getFdMergeEntry().equals(FsscVoucherConstant.FSSC_VOUCHER_FD_MERGE_ENTRY_TRUE)) {
        		List<FsscVoucherDetail> mergeResult = compareAndMerge(voucherDetails);
            	main.setFdDetail(mergeResult);
        	}else {
            	 main.setFdDetail(voucherDetails);
            }
        }
    	return main;
    }
    
    
    /**
     * 借/贷(比较合并)
     * @param list
     * @return List<FsscVoucherDetail> 
     */
    private List<FsscVoucherDetail>  compareAndMerge(List<FsscVoucherDetail> list) {
    	List<FsscVoucherDetail> dataList = new ArrayList<>();
    	Map<String,FsscVoucherDetail> map = new LinkedHashMap<>();
    	StringBuilder build = null;
    	FsscVoucherDetail detail = null;
    	for (FsscVoucherDetail fsscVoucherDetail : list) {
    		detail = new FsscVoucherDetail();
    		//科目code
			String accountsCode = fsscVoucherDetail.getFdBaseAccounts() != null ? fsscVoucherDetail.getFdBaseAccounts().getFdCode() : null;
			//成本中心code
			String costCenterCode = fsscVoucherDetail.getFdBaseCostCenter() != null ? fsscVoucherDetail.getFdBaseCostCenter().getFdCode() : null;
			//个人code
			String personCode = fsscVoucherDetail.getFdBaseErpPerson() != null ? fsscVoucherDetail.getFdBaseErpPerson().getFdCode() : null;
			//现金流量项目code
			String cashFlowCode = fsscVoucherDetail.getFdBaseCashFlow() != null ? fsscVoucherDetail.getFdBaseCashFlow().getFdCode() : null;
			//客户code
			String customerCode = fsscVoucherDetail.getFdBaseCustomer() != null ? fsscVoucherDetail.getFdBaseCustomer().getFdCode() : null;
			//供应商code
			String supplierCode = fsscVoucherDetail.getFdBaseSupplier() != null ? fsscVoucherDetail.getFdBaseSupplier().getFdCode() : null;
			//核算项目code
			String probjectCode = fsscVoucherDetail.getFdBaseProject() != null ? fsscVoucherDetail.getFdBaseProject().getFdCode() : null;
			//银行code
			String bankCode = fsscVoucherDetail.getFdBasePayBank() != null ? fsscVoucherDetail.getFdBasePayBank().getFdCode() : null;
			//部门code
			String deptCode = fsscVoucherDetail.getFdDept() != null ? fsscVoucherDetail.getFdDept().getFdNo() : null;
			//合同编号
			String contractCode = fsscVoucherDetail.getFdContractCode();
			//摘要
			String voucherText = fsscVoucherDetail.getFdVoucherText();
			build = new StringBuilder();
			build.append(fsscVoucherDetail.getFdType()).append(accountsCode).append(costCenterCode).append(personCode)
					.append(cashFlowCode).append(customerCode).append(supplierCode).append(probjectCode)
					.append(bankCode).append(deptCode).append(contractCode).append(voucherText);
			detail = convert(fsscVoucherDetail);
			if(!map.isEmpty()) {
				for (Map.Entry<String, FsscVoucherDetail> mp : map.entrySet()) {
					if(mp.getKey().equals(build.toString())) {
						Double money = null;
						BigDecimal lastMoney = new BigDecimal(Double.toString(mp.getValue().getFdMoney()));
						BigDecimal nextMoney = new BigDecimal(Double.toString(fsscVoucherDetail.getFdMoney()));
						money = lastMoney.add(nextMoney).doubleValue();
						detail.setFdMoney(FsscNumberUtil.doubleToUp(money));//金额
					}
				}
				map.put(build.toString(), detail);
			}else {
				map.put(build.toString(), detail);
			}
		}
    	Iterator it = map.values().iterator(); 
    	for(;it.hasNext();) {
    		FsscVoucherDetail voucherDetail = (FsscVoucherDetail) it.next();
    		dataList.add(voucherDetail);
    	}
    	return dataList;
    }
    
    
    /**
     * 转换
     */
    private FsscVoucherDetail convert(FsscVoucherDetail vDetail) {
    	FsscVoucherDetail voucherDetail = new FsscVoucherDetail();
    	voucherDetail.setFdType(vDetail.getFdType());
   	 	voucherDetail.setFdBaseAccounts(vDetail.getFdBaseAccounts());//会计科目
        voucherDetail.setFdBaseCostCenter(vDetail.getFdBaseCostCenter());//成本中心
        voucherDetail.setFdBaseErpPerson(vDetail.getFdBaseErpPerson());//个人
        voucherDetail.setFdBaseCashFlow(vDetail.getFdBaseCashFlow());//现金流量项目
        voucherDetail.setFdBaseCustomer(vDetail.getFdBaseCustomer());//客户
        voucherDetail.setFdBaseSupplier(vDetail.getFdBaseSupplier());//供应商
        voucherDetail.setFdBaseProject(vDetail.getFdBaseProject());//项目
        voucherDetail.setFdBasePayBank(vDetail.getFdBasePayBank());//银行
        voucherDetail.setFdDept(vDetail.getFdDept());//部门
        voucherDetail.setFdBaseWbs(vDetail.getFdBaseWbs());//WBS号
        voucherDetail.setFdBaseInnerOrder(vDetail.getFdBaseInnerOrder());//内部订单
        voucherDetail.setFdMoney(FsscNumberUtil.doubleToUp(vDetail.getFdMoney()));//金额
        voucherDetail.setFdVoucherText(vDetail.getFdVoucherText());//
        voucherDetail.setFdContractCode(vDetail.getFdContractCode());//合同编号
    	return voucherDetail;
    }
    
    
    /**
     * 获取付款单凭证明细
     */
    private void getPaymentDetail(FormulaParser parser, List<FsscVoucherDetail> voucherDetails, FsscVoucherRuleDetail ruleDetail
            , double fdTotoalMoney1, String categoryName, String fdModelId, String fdModelName) throws Exception{
        Map<String, String> map = new HashMap<String, String>();
        Double money = null;
        map.put("fdModelId", fdModelId);
        map.put("fdModelName", fdModelName);
        Map<String, Object> tempMap = getFsscCommonCashierPaymentService().getPaymentDetailInfo(map);
        if("failure".equals(tempMap.get("result")+"")){
            throw new Exception(tempMap.get("message")+"");
        }
        List<Map<String, Object>> paymentDetailList = (List<Map<String, Object>>) tempMap.get("paymentDetailList");
        if(ArrayUtil.isEmpty(paymentDetailList)){
            return;
        }
        List fdRuleList = this.getList("fdRule", parser, ruleDetail, paymentDetailList.size(), categoryName, ruleDetail.getFdRuleText());
        if(ArrayUtil.isEmpty(fdRuleList)){
            return;
        }
        List fdTypeList = this.getList("fdType", parser, ruleDetail, paymentDetailList.size(), categoryName, ruleDetail.getFdRuleText());
        List fdBaseCostCenterList = this.getChooseList("fdBaseCostCenter", parser, ruleDetail, paymentDetailList.size(), categoryName, ruleDetail.getFdRuleText());
        List fdBaseErpPersonList = this.getChooseList("fdBaseErpPerson", parser, ruleDetail, paymentDetailList.size(), categoryName, ruleDetail.getFdRuleText());
        List fdBaseCashFlowList = this.getChooseList("fdBaseCashFlow", parser, ruleDetail, paymentDetailList.size(), categoryName, ruleDetail.getFdRuleText());
        List fdBaseCustomerList = this.getChooseList("fdBaseCustomer", parser, ruleDetail, paymentDetailList.size(), categoryName, ruleDetail.getFdRuleText());
        List fdBaseSupplierList = this.getChooseList("fdBaseSupplier", parser, ruleDetail, paymentDetailList.size(), categoryName, ruleDetail.getFdRuleText());
        List fdDeptList = this.getChooseList("fdDept", parser, ruleDetail, paymentDetailList.size(), categoryName, ruleDetail.getFdRuleText());	//部门
        List fdBaseWbsList = this.getChooseList("fdBaseWbs", parser, ruleDetail, paymentDetailList.size(), categoryName, ruleDetail.getFdRuleText());
        List fdBaseInnerOrderList = this.getChooseList("fdBaseInnerOrder", parser, ruleDetail, paymentDetailList.size(), categoryName, ruleDetail.getFdRuleText());
        List fdBaseProjectList = this.getChooseList("fdBaseProject", parser, ruleDetail, paymentDetailList.size(), categoryName, ruleDetail.getFdRuleText());
        List fdVoucherDetailTextList = this.getList("fdVoucherText", parser, ruleDetail, paymentDetailList.size(), categoryName, ruleDetail.getFdRuleText());
        List fdContractCodeList = this.getList("fdContractCode", parser, ruleDetail, paymentDetailList.size(), categoryName, ruleDetail.getFdRuleText());	//合同编号

        FsscVoucherDetail voucherDetail = null;
        for(int j=0;j<paymentDetailList.size();j++){
            Object fdRuleFormulaObj = fdRuleList.get(j);
            if(!((fdRuleFormulaObj instanceof String && "true".equals(fdRuleFormulaObj+""))
                    || (fdRuleFormulaObj instanceof Boolean && (Boolean) fdRuleFormulaObj))){//不满足规则
                continue;
            }
            Object fdTypeObj = fdTypeList.get(j);
            String fdType = FsscVoucherConstant.FSSC_VOUCHER_FD_TYPE_1;//默认借
            if(fdTypeObj instanceof String){
                fdType = fdTypeObj+"";
            }else if(fdTypeObj instanceof Integer){
                fdType = fdTypeObj+"";
            }

            EopBasedataAccounts fdBaseAccounts = (EopBasedataAccounts) paymentDetailList.get(j).get("fdAccount");//会计科目
            EopBasedataPayBank fdBasePayBank = (EopBasedataPayBank) paymentDetailList.get(j).get("fdBasePayBank");//付款银行

            EopBasedataCostCenter fdBaseCostCenter = (EopBasedataCostCenter) fdBaseCostCenterList.get(j);//成本中心
            EopBasedataErpPerson fdBaseErpPerson = (EopBasedataErpPerson) fdBaseErpPersonList.get(j);//Erp个人
            EopBasedataCashFlow fdBaseCashFlow = (EopBasedataCashFlow) fdBaseCashFlowList.get(j);//现金流量项目
            EopBasedataCustomer fdBaseCustomer = (EopBasedataCustomer) fdBaseCustomerList.get(j);//客户
            EopBasedataSupplier fdBaseSupplier = (EopBasedataSupplier) fdBaseSupplierList.get(j);//供应商
            SysOrgElement fdDept = (SysOrgElement) fdDeptList.get(j);//部门
            EopBasedataWbs fdBaseWbs = (EopBasedataWbs) fdBaseWbsList.get(j);//WBS号
            EopBasedataInnerOrder fdBaseInnerOrder = (EopBasedataInnerOrder) fdBaseInnerOrderList.get(j);//内部订单
            EopBasedataProject fdBaseProject = (EopBasedataProject) fdBaseProjectList.get(j);//项目
            String fdVoucherDetailText = fdVoucherDetailTextList.get(j)+"";//摘要文本
            String fdContractCode = null!=fdContractCodeList.get(j)?fdContractCodeList.get(j)+"":"";//合同编号
            double fdMoney=0.0;
            if("0".equals(String.valueOf(paymentDetailList.get(j).get("fdPaymentType")))){//对公，原币
            	fdMoney = Double.parseDouble(String.valueOf(paymentDetailList.get(j).get("fdPaymentMoney")));
            }else{//对私，本位币
            	fdMoney = FsscNumberUtil.getMultiplication(Double.parseDouble(String.valueOf(paymentDetailList.get(j).get("fdPaymentMoney"))), (Double) paymentDetailList.get(j).get("fdRate"), 2);
            }

            if(FsscVoucherConstant.FSSC_VOUCHER_FD_TYPE_1.equals(fdType)){
                fdTotoalMoney1 = FsscNumberUtil.getAddition(fdTotoalMoney1, fdMoney);
            }
            voucherDetail = new FsscVoucherDetail();
            voucherDetail.setFdType(fdType);//
            voucherDetail.setFdBaseAccounts(fdBaseAccounts);//会计科目
            voucherDetail.setFdBaseCostCenter(fdBaseCostCenter);//成本中心
            voucherDetail.setFdBaseErpPerson(fdBaseErpPerson);//个人
            voucherDetail.setFdBaseCashFlow(fdBaseCashFlow);//现金流量项目
            voucherDetail.setFdBaseCustomer(fdBaseCustomer);//客户
            voucherDetail.setFdBaseSupplier(fdBaseSupplier);//供应商
            voucherDetail.setFdBaseProject(fdBaseProject);//项目
            voucherDetail.setFdBasePayBank(fdBasePayBank);//银行
            voucherDetail.setFdDept(fdDept);//部门
            voucherDetail.setFdBaseWbs(fdBaseWbs);//WBS号
            voucherDetail.setFdBaseInnerOrder(fdBaseInnerOrder);//内部订单
            voucherDetail.setFdMoney(FsscNumberUtil.doubleToUp(fdMoney));//金额
            voucherDetail.setFdVoucherText(fdVoucherDetailText);//
            voucherDetail.setFdContractCode(fdContractCode);//合同编号
            clearNotCostItemValue(voucherDetail);//清除没有勾选的辅助核算项值
            voucherDetails.add(voucherDetail);
        }
    }

    /**
     * 待摊凭证
     */
    private void addOrUpdateVoucherAmortize(SysOrgPerson person, FormulaParser parser, FsscVoucherRuleConfig fsscVoucherRuleConfig
            , String fdModelId, String fdModelName, String fdModelNumber) throws Exception{
        EopBasedataCompany fdCompany = (EopBasedataCompany) getChooseObject("fdCompany", parser, fsscVoucherRuleConfig);
        EopBasedataVoucherType fdVoucherType = (EopBasedataVoucherType) getChooseObject("fdVoucherType", parser, fsscVoucherRuleConfig);
        EopBasedataCurrency fdCurrency = (EopBasedataCurrency) getChooseObject("fdCurrency", parser, fsscVoucherRuleConfig );
        Object fdNumberObj = parser.parseValueScript(fsscVoucherRuleConfig.getFdNumberFormula());
        int fdNumber = 1;
        if(fdNumberObj instanceof String){
            fdNumber = Integer.valueOf(fdNumberObj+"");
        }else if(fdNumberObj instanceof Integer){
            fdNumber = (int) fdNumberObj;
        }
        String fdVoucherText = parser.parseValueScript(fsscVoucherRuleConfig.getFdVoucherTextFormula())+"";

        List fdVoucherDateList = (List) parser.parseValueScript(fsscVoucherRuleConfig.getFdVoucherDateFormula());
        List fdRuleList = null;
        List fdTypeList = null;
        List fdBaseAccountsList = null;
        List fdBaseCostCenterList = null;
        List fdBaseErpPersonList = null;
        List fdBaseCashFlowList = null;
        List fdBaseCustomerList = null;
        List fdBaseSupplierList = null;
        List fdBaseWbsList = null;
        List fdBaseInnerOrderList = null;
        List fdBaseProjectList = null;
        List fdBasePayBankList = null;
        List fdDeptList = null;
        List fdMoneyList = null;
        List fdVoucherDetailTextList = null;
        List fdContractCodeList = null;	//合同编号

        int oldSize = getAllAmortizeVoucher(fdModelId, fdModelName);//查询现有摊销凭证数量
        int sizeInt = fdVoucherDateList.size();//生成明细条数
        if(oldSize != sizeInt){//如果变动
            deleteAllAmortizeVoucher(fdModelId, fdModelName);//删掉摊销凭证
        }
        for(int j=0;j<fdVoucherDateList.size();j++){
            String fdVoucherDateStr = DateUtil.convertDateToString((Date)fdVoucherDateList.get(j), "yyyy-MM-dd");

            List<FsscVoucherMain> mainList = this.findByDeleteWhere(fsscVoucherRuleConfig.getFdId(), fdModelId, fdModelName, (Date)fdVoucherDateList.get(j));
            FsscVoucherMain main = null;
            if(!ArrayUtil.isEmpty(mainList)){
                main = mainList.get(0);
                if(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30.equals(main.getFdBookkeepingStatus())){//如果是已记账就跳过
                    continue;
                }
            }else{
                main = new FsscVoucherMain();
            }
            main.setFdVoucherCreateType(fsscVoucherRuleConfig.getFdId());
            main.setFdPushType(fsscVoucherRuleConfig.getFdPushType());//推送方式
            main.setFdModelId(fdModelId);
            main.setFdModelName(fdModelName);
            main.setFdModelNumber(fdModelNumber);
            main.setFdCompany(fdCompany);//公司
            main.setFdBaseVoucherType(fdVoucherType);//凭证类型
            main.setFdBaseCurrency(fdCurrency);//币种
            main.setFdVoucherDate((Date)fdVoucherDateList.get(j));//凭证日期
            main.setFdAccountingYear(fdVoucherDateStr.substring(0, 4));//会计年度
            main.setFdPeriod(fdVoucherDateStr.substring(5, 7));//期间
            main.setFdNumber(fdNumber);//单据数
            main.setFdVoucherText(fdVoucherText);//凭证抬头文本
            main.setDocCreator(person);//制单人
            main.setFdIsAmortize(true);//摊销凭证

            List<FsscVoucherRuleDetail> detailList = fsscVoucherRuleConfig.getFdDetail();
            if(ArrayUtil.isEmpty(detailList)){
                return;
            }
            double fdTotoalMoney1 = 0;//借方总金额

            List<FsscVoucherDetail> voucherDetails = new ArrayList<FsscVoucherDetail>();
            FsscVoucherDetail voucherDetail = null;
            for(int i = 0; i<detailList.size(); i++){
                FsscVoucherRuleDetail detail = detailList.get(i);
                fdRuleList = this.getList("fdRule", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(),detail.getFdRuleText());
                fdTypeList = this.getList("fdType", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(),detail.getFdRuleText());
                fdBaseAccountsList = this.getChooseList("fdBaseAccounts", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                fdBaseCostCenterList = this.getChooseList("fdBaseCostCenter", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                fdBaseErpPersonList = this.getChooseList("fdBaseErpPerson", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                fdBaseCashFlowList = this.getChooseList("fdBaseCashFlow", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                fdBaseCustomerList = this.getChooseList("fdBaseCustomer", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                fdBaseSupplierList = this.getChooseList("fdBaseSupplier", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                fdBaseWbsList = this.getChooseList("fdBaseWbs", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                fdBaseInnerOrderList = this.getChooseList("fdBaseInnerOrder", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                fdBaseProjectList = this.getChooseList("fdBaseProject", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                fdBasePayBankList = this.getChooseList("fdBasePayBank", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                fdDeptList = this.getChooseList("fdDept", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                fdMoneyList = this.getList("fdMoney", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                fdVoucherDetailTextList = this.getList("fdVoucherText", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());
                fdContractCodeList = this.getList("fdContractCode", parser, detail, sizeInt, fsscVoucherRuleConfig.getFdName(), detail.getFdRuleText());	//合同编号
                
                Object fdRuleFormulaObj = fdRuleList.get(j);
                if(!((fdRuleFormulaObj instanceof String && "true".equals(fdRuleFormulaObj+""))
                        || (fdRuleFormulaObj instanceof Boolean && (Boolean) fdRuleFormulaObj))){//不满足规则
                    continue;
                }
                Object fdTypeObj = fdTypeList.get(j);
                String fdType = FsscVoucherConstant.FSSC_VOUCHER_FD_TYPE_1;//默认借
                if(fdTypeObj instanceof String){
                    fdType = fdTypeObj+"";
                }else if(fdTypeObj instanceof Integer){
                    fdType = fdTypeObj+"";
                }

                EopBasedataAccounts fdBaseAccounts = (EopBasedataAccounts) fdBaseAccountsList.get(j);//会计科目
                EopBasedataCostCenter fdBaseCostCenter = (EopBasedataCostCenter) fdBaseCostCenterList.get(j);//成本中心
                EopBasedataErpPerson fdBaseErpPerson = (EopBasedataErpPerson) fdBaseErpPersonList.get(j);//Erp个人
                EopBasedataCashFlow fdBaseCashFlow = (EopBasedataCashFlow) fdBaseCashFlowList.get(j);//现金流量项目
                EopBasedataCustomer fdBaseCustomer = (EopBasedataCustomer) fdBaseCustomerList.get(j);//客户
                EopBasedataSupplier fdBaseSupplier = (EopBasedataSupplier) fdBaseSupplierList.get(j);//供应商
                EopBasedataWbs fdBaseWbs = (EopBasedataWbs) fdBaseWbsList.get(j);//WBS号
                EopBasedataInnerOrder fdBaseInnerOrder = (EopBasedataInnerOrder) fdBaseInnerOrderList.get(j);//内部订单
                EopBasedataProject fdBaseProject = (EopBasedataProject) fdBaseProjectList.get(j);//项目
                EopBasedataPayBank fdBasePayBank = (EopBasedataPayBank) fdBasePayBankList.get(j);//付款银行
                SysOrgElement fdDept = (SysOrgElement) fdDeptList.get(j);//部门
                String fdVoucherDetailText = fdVoucherDetailTextList.get(j)+"";//摘要文本
                String fdContractCode = null!=fdContractCodeList.get(j)?fdContractCodeList.get(j)+"":"";//合同编号
                double fdMoney = 0;
                Object tempMoneyObj = fdMoneyList.get(j);
                if(tempMoneyObj instanceof String){
                    fdMoney = FsscNumberUtil.doubleToUp(tempMoneyObj+"");
                }else if(tempMoneyObj instanceof Double){
                    fdMoney = (Double) tempMoneyObj;
                }

                if(FsscVoucherConstant.FSSC_VOUCHER_FD_TYPE_1.equals(fdType)){
                    fdTotoalMoney1 = FsscNumberUtil.getAddition(fdTotoalMoney1, fdMoney);
                }
                voucherDetail = new FsscVoucherDetail();
                voucherDetail.setFdType(fdType);//
                voucherDetail.setFdBaseAccounts(fdBaseAccounts);//会计科目
                voucherDetail.setFdBaseCostCenter(fdBaseCostCenter);//成本中心
                voucherDetail.setFdBaseErpPerson(fdBaseErpPerson);//个人
                voucherDetail.setFdBaseCashFlow(fdBaseCashFlow);//现金流量项目
                voucherDetail.setFdBaseCustomer(fdBaseCustomer);//客户
                voucherDetail.setFdBaseSupplier(fdBaseSupplier);//供应商
                voucherDetail.setFdBaseProject(fdBaseProject);//项目
                voucherDetail.setFdBasePayBank(fdBasePayBank);//银行
                voucherDetail.setFdDept(fdDept);//部门
                voucherDetail.setFdBaseWbs(fdBaseWbs);//WBS号
                voucherDetail.setFdBaseInnerOrder(fdBaseInnerOrder);//内部订单
                voucherDetail.setFdMoney(FsscNumberUtil.doubleToUp(fdMoney));//金额
                voucherDetail.setFdVoucherText(fdVoucherDetailText);//
                voucherDetail.setFdContractCode(fdContractCode);//合同编号
                clearNotCostItemValue(voucherDetail);//清除没有勾选的辅助核算项值
                voucherDetails.add(voucherDetail);
            }
            main.setFdModelMoney(fdTotoalMoney1);//单据总金额

            //判断分录合并
            FsscVoucherMain ma = judgeMergeEntry(main, fsscVoucherRuleConfig, voucherDetails);
            this.update(ma);
        }
    }

    public List getList(String fdPropertyName, FormulaParser parser, IBaseModel ruleModel, int sizeInt, String categoryName, String ruleText) throws Exception{
        List list = new ArrayList();
        String formula = PropertyUtils.getProperty(ruleModel, fdPropertyName+"Formula")+"";
        if(StringUtil.isNotNull(formula)){
            Object obj = parser.parseValueScript(formula);
            if(obj instanceof List){
                List objList = (List) obj;
                if(objList.size() != sizeInt){
                    if(objList.size() == 1){
                        for(int i=0;i<sizeInt;i++){
                            list.add(objList.get(0));
                        }
                    }else{//长度不一致抛异常
                        throw new Exception(ResourceUtil.getString("addOrUpdateVoucher.error.size", "fssc-voucher")
                                .replace("%name%", categoryName).replace("%ruleText%", ruleText).replace("%pripertyName%", fdPropertyName));
                    }
                }else{
                    list.addAll(objList);
                }
            }else{
                for(int i=0;i<sizeInt;i++){
                    list.add(obj);
                }
            }
        }else{
            for(int i=0;i<sizeInt;i++){
                list.add(null);
            }
        }
        return list;
    }

    public List getChooseList(String fdPropertyName, FormulaParser parser, IBaseModel ruleModel, int sizeInt, String categoryName, String ruleText) throws Exception{
        List list = new ArrayList();
        String flag = PropertyUtils.getProperty(ruleModel, fdPropertyName+"Flag")+"";
        if(FsscVoucherConstant.FSSC_VOUCHER_FLAG_1.equals(flag)){//选择
            for(int i=0;i<sizeInt;i++){
                list.add(PropertyUtils.getProperty(ruleModel, fdPropertyName));
            }
        }else if(FsscVoucherConstant.FSSC_VOUCHER_FLAG_2.equals(flag)){//公式定义器
            return this.getList(fdPropertyName, parser, ruleModel, sizeInt, categoryName, ruleText);
        }else{
            for(int i=0;i<sizeInt;i++){
                list.add(null);
            }
        }
        return list;
    }

    public Object getChooseObject(String fdPropertyName, FormulaParser parser, IBaseModel ruleModel) throws Exception{
        String flag = PropertyUtils.getProperty(ruleModel, fdPropertyName+"Flag")+"";
        if(FsscVoucherConstant.FSSC_VOUCHER_FLAG_1.equals(flag)){//选择
            return PropertyUtils.getProperty(ruleModel, fdPropertyName);
        }else if(FsscVoucherConstant.FSSC_VOUCHER_FLAG_2.equals(flag)){//公式定义器
            String formula = PropertyUtils.getProperty(ruleModel, fdPropertyName+"Formula")+"";
            if(StringUtil.isNotNull(formula)){
                return parser.parseValueScript(formula);
            }
        }
        return null;
    }

    /**
     * 清除没有勾选的辅助核算项值
     * @param detail
     */
    public void clearNotCostItemValue(FsscVoucherDetail detail){
        EopBasedataAccounts accounts = detail.getFdBaseAccounts();
        if(accounts == null){
            return;
        }
        String properyStr = accounts.getFdCostItem();
        if(properyStr == null){
            properyStr = "";
        }
        properyStr += ";";

        //1 成本中心      2 项目      3 客户
        //4 现金流量项目      5 人员      6 WBS号
        //7 内部订单 	  8   银行
        //9供应商                   10合同     11 部门
        if(!FsscCommonUtil.isContain(properyStr, "1;", ";")){//成本中心
            detail.setFdBaseCostCenter(null);
        }
        if(!FsscCommonUtil.isContain(properyStr, "2;", ";")){//项目
            detail.setFdBaseProject(null);
        }
        if(!FsscCommonUtil.isContain(properyStr, "3;", ";")){//客户
            detail.setFdBaseCustomer(null);
        }
        if(!FsscCommonUtil.isContain(properyStr, "4;", ";")){//现金流量项目
            detail.setFdBaseCashFlow(null);
        }
        if(!FsscCommonUtil.isContain(properyStr, "5;", ";")){//人员
            detail.setFdBaseErpPerson(null);
        }
        if(!FsscCommonUtil.isContain(properyStr, "6;", ";")){//WBS号
            detail.setFdBaseWbs(null);
        }
        if(!FsscCommonUtil.isContain(properyStr, "7;", ";")){//内部订单
            detail.setFdBaseInnerOrder(null);
        }
        if(!FsscCommonUtil.isContain(properyStr, "8;", ";")){//银行
            detail.setFdBasePayBank(null);
        }
        if(!FsscCommonUtil.isContain(properyStr, "9;", ";")){//供应商
            detail.setFdBaseSupplier(null);
        }
        if(!FsscCommonUtil.isContain(properyStr, "10;", ";")){//合同
            detail.setFdContractCode("");
        }
        if(!FsscCommonUtil.isContain(properyStr, "11;", ";")){//部门
            detail.setFdDept(null);
        }
    }

    public String getFdModelId(String fdModelName, String fdModelNumber, String fdModelNumberPropertyName) throws Exception{
        SysDictModel dict = SysDataDict.getInstance().getModel(fdModelName);
        if(dict == null || !dict.getPropertyMap().containsKey(fdModelNumberPropertyName)){
            return null;
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" select fd_id from ").append(dict.getTable());
        sql.append(" where ").append(dict.getPropertyMap().get(fdModelNumberPropertyName).getColumn()).append("='").append(fdModelNumber).append("'");
        List list = this.getBaseDao().getHibernateSession().createNativeQuery(sql.toString()).list();
        return ArrayUtil.isEmpty(list)?"":list.get(0)+"";
    }

    /**
     * 【财务共享】凭证中心--摊销凭证自动记账定时任务
     * @param date
     * @return
     * @throws Exception
     */
    @Override
    public String updateAutoBookkeeping(Date date) throws Exception {
        String logMessage = "";
        List<FsscVoucherMain> fsscVoucherMainList = this.findNoBookkeeping(date, FsscVoucherConstant.FSSC_VOUCHER_FD_PUSH_TYPE_2);
        if (!ArrayUtil.isEmpty(fsscVoucherMainList)) {
            try{
                Map<String, String> map = this.updateBookkeeping(fsscVoucherMainList);
                logMessage = "总凭证数："+map.get("countInt")+",记账成功数："+map.get("successInt")+",记账失败数："+map.get("failureInt")
                        +"失败凭证编号："+map.get("failureCodes")+"     成功凭证编号："+map.get("successCodes");
            }catch (Exception e){
                e.printStackTrace();
                logMessage = "运行异常："+e.getMessage();
            }
        }else{
            logMessage = "无需要记账的摊销凭证";
        }
        return logMessage;
    }

    /**
     *	获取凭证信息
     */
    @Override
    public List<Map<String, Object>> getVoucherInfo(String fdModelId, String fdModelName) throws Exception{
        List<FsscVoucherMain> mainList = this.getFsscVoucherMain(fdModelId, fdModelName);
        List<Map<String, Object>> voucherList = new ArrayList<>();
        if(ArrayUtil.isEmpty(mainList)){
            return voucherList;
        }
        Map<String, Object> voucherInfoMap = null;
        List<Map<String, Object>> voucherDetailList = null;
        Map<String, Object> tempMap = null;
        for(FsscVoucherMain main : mainList){
            voucherInfoMap = new HashMap<>();
            voucherDetailList = new ArrayList<Map<String, Object>>();

            voucherInfoMap.put("fdId", main.getFdId());
            voucherInfoMap.put("docFinanceNumber", main.getDocFinanceNumber());
            voucherInfoMap.put("docNumber", main.getDocNumber());
            voucherInfoMap.put("fdModelNumber", main.getFdModelNumber());
            if(StringUtil.isNotNull(main.getFdModelId()) && StringUtil.isNotNull(main.getFdModelName())){
                SysDictModel dict = SysDataDict.getInstance().getModel(main.getFdModelName());
                if(dict != null){
                    voucherInfoMap.put("fdModelUrl", dict.getUrl().replace("${fdId}", main.getFdModelId()));
                }
            }
            voucherInfoMap.put("fdBaseVoucherTypeName", main.getFdBaseVoucherType()==null?"":main.getFdBaseVoucherType().getFdName());
            voucherInfoMap.put("fdVoucherDate", DateUtil.convertDateToString(main.getFdVoucherDate(), "yyyy-MM-dd"));
            voucherInfoMap.put("fdBookkeepingDate", DateUtil.convertDateToString(main.getFdBookkeepingDate(), "yyyy-MM-dd"));
            voucherInfoMap.put("fdAccountingYear", main.getFdAccountingYear());
            voucherInfoMap.put("fdPeriod", main.getFdPeriod());
            voucherInfoMap.put("fdBaseCurrencyName", main.getFdBaseCurrency()==null?"":main.getFdBaseCurrency().getFdName());
            voucherInfoMap.put("fdCompanyName", main.getFdCompany()==null?"":main.getFdCompany().getFdName());
            voucherInfoMap.put("fdCompanyCode", main.getFdCompany()==null?"":main.getFdCompany().getFdCode());
            voucherInfoMap.put("fdNumber", main.getFdNumber());
            voucherInfoMap.put("fdVoucherText", main.getFdVoucherText());
            voucherInfoMap.put("fdBookkeepingStatus", main.getFdBookkeepingStatus());
            voucherInfoMap.put("fdBookkeepingMessage", main.getFdBookkeepingMessage());
            voucherInfoMap.put("fdBookkeepingPersonName", main.getFdBookkeepingPerson()==null?"":main.getFdBookkeepingPerson().getFdName());
            voucherInfoMap.put("docCreatorName", main.getDocCreator()==null?"":main.getDocCreator().getFdName());
            voucherInfoMap.put("fdPushType", main.getFdPushType());
            voucherInfoMap.put("fdMergeEntry", main.getFdMergeEntry());
            for(FsscVoucherDetail detail : main.getFdDetail()){
                tempMap = new HashMap<String, Object>();
                tempMap.put("docMainId", main.getFdId());
                tempMap.put("docMainCompanyName", main.getFdCompany()==null?"":main.getFdCompany().getFdName());
                tempMap.put("docMainNumber", main.getDocNumber());
                tempMap.put("docMainFinanceNumber", main.getDocFinanceNumber());
                tempMap.put("docMainCurrencyName", main.getFdBaseCurrency()==null?"":main.getFdBaseCurrency().getFdName());

                tempMap.put("fdType", detail.getFdType());
                tempMap.put("fdAccounts", detail.getFdBaseAccounts());
                tempMap.put("fdAccountsCode", detail.getFdBaseAccounts()==null?"":detail.getFdBaseAccounts().getFdCode());
                tempMap.put("fdAccountsName", detail.getFdBaseAccounts()==null?"":detail.getFdBaseAccounts().getFdName());
                tempMap.put("fdCostCenterName", detail.getFdBaseCostCenter()==null?"":detail.getFdBaseCostCenter().getFdName());
                tempMap.put("fdBaseErpPersonName", detail.getFdBaseErpPerson()==null?"":detail.getFdBaseErpPerson().getFdName());
                tempMap.put("fdBaseCashFlowName", detail.getFdBaseCashFlow()==null?"":detail.getFdBaseCashFlow().getFdName());
                tempMap.put("fdBaseCustomerName", detail.getFdBaseCustomer()==null?"":detail.getFdBaseCustomer().getFdName());
                tempMap.put("fdBaseSupplierName", detail.getFdBaseSupplier()==null?"":detail.getFdBaseSupplier().getFdName());
                tempMap.put("fdBaseWbsName", detail.getFdBaseWbs()==null?"":detail.getFdBaseWbs().getFdName());
                tempMap.put("fdBaseInnerOrderName", detail.getFdBaseInnerOrder()==null?"":detail.getFdBaseInnerOrder().getFdName());
                tempMap.put("fdBaseProjectName", detail.getFdBaseProject()==null?"":detail.getFdBaseProject().getFdName());
                tempMap.put("fdBasePayBank", detail.getFdBasePayBank());
                tempMap.put("fdBasePayBankName", detail.getFdBasePayBank()==null?"":(detail.getFdBasePayBank().getFdAccountName()+"("+detail.getFdBasePayBank().getFdBankAccount()+")"));
                tempMap.put("fdMoney", detail.getFdMoney()+"");
                tempMap.put("fdVoucherText", detail.getFdVoucherText());
                tempMap.put("fdContractCode", detail.getFdContractCode());	//合同编号
                tempMap.put("fdDeptName", detail.getFdDept()==null?"":detail.getFdDept().getFdName());	//部门
                voucherDetailList.add(tempMap);
            }
            voucherInfoMap.put("voucherDetailList", voucherDetailList);
            voucherList.add(voucherInfoMap);
        }
        return voucherList;
    }

    /**
     * 记账
     * @param fsscVoucherMainList
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> updateBookkeeping(List<FsscVoucherMain> fsscVoucherMainList) throws Exception {
        Map<String, String> rtnMap = new HashMap<>();
        StringBuilder message = new StringBuilder();
        int countInt = 0;//总数
        int successInt = 0;//成功数
        int failureInt = 0;//失败数
        StringBuilder successCodes = new StringBuilder();//成功编号
        StringBuilder failureCodes = new StringBuilder();//失败编号
        if(!ArrayUtil.isEmpty(fsscVoucherMainList)){
            countInt = fsscVoucherMainList.size();
            EopBasedataCompany eopBasedataCompany = fsscVoucherMainList.get(0).getFdCompany();
            String fdJoinSystem = eopBasedataCompany.getFdJoinSystem();//对接财务系统
            if(EopBasedataConstant.FSSC_BASE_U8.equals(fdJoinSystem)){//存在u8模块
                if(StringUtil.isNull(eopBasedataCompany.getFdUEightUrl())){
                    throw new Exception(ResourceUtil.getString("bookkeeping.voucher.U8.url.null.error", "fssc-voucher"));
                }
                for(FsscVoucherMain fsscVoucherMain : fsscVoucherMainList){
                    try{
                        this.updateBookkeepingU8(fsscVoucherMain);
                        successInt++;
                        if(successInt==countInt){//多张凭证情况下，成功的凭证数和总凭证数相等，则自动通过六成熟
                        	updateAutoPassNode(fsscVoucherMain);
                        }
                        successCodes.append(fsscVoucherMain.getDocNumber()+";");
                    } catch (Exception e) {
                        failureInt++;
                        failureCodes.append(fsscVoucherMain.getDocNumber()+";");
                        e.printStackTrace();
                        message.append(e.getMessage()+"<br/>");
                        //如果是 U8已存在相同费控编号的凭证，写入不成功！ 就不修改
                        if(!ResourceUtil.getString("bookkeeping.voucher.u8.exist.error", "fssc-voucher").equals(e.getMessage())){
                            //保存失败原因
                            fsscVoucherMain.setFdBookkeepingMessage(e.getMessage());
                            fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11);
                            this.update(fsscVoucherMain);

                            //记账失败，写入记账状态和记账失败原因
                            //固定记账状态字段为fdBookkeepingStatus，记账失败原因字段为fdBookkeepingMessage，并且数据字典里面也要有
                            SysDictModel dict = SysDataDict.getInstance().getModel(fsscVoucherMain.getFdModelName());
                            if(dict != null){
                                Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
                                if(StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdBookkeepingMessage") && dictMap.containsKey("fdBookkeepingStatus")){
                                    SysDictCommonProperty fdBookkeepingStatusProperty = dictMap.get("fdBookkeepingStatus");
                                    SysDictCommonProperty fdBookkeepingMessageProperty = dictMap.get("fdBookkeepingMessage");
                                    StringBuffer sql = new StringBuffer();
                                    sql.append(" update ").append(dict.getTable());
                                    sql.append(" set ").append(fdBookkeepingStatusProperty.getColumn()).append(" = '"+FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_11 +"',");
                                    sql.append(fdBookkeepingMessageProperty.getColumn()).append(" = '"+ e.getMessage() +"'");
                                    sql.append(" where fd_id = '").append(fsscVoucherMain.getFdModelId()).append("' ");
                                    NativeQuery query=this.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
                                    query.addSynchronizedQuerySpace(dict.getTable());
                                    query.executeUpdate();
                                }
                            }
                        }
                    }
                }
            } else if(EopBasedataConstant.FSSC_BASE_EAS.equals(fdJoinSystem)){//存在eas模块
                for(FsscVoucherMain fsscVoucherMain : fsscVoucherMainList){
                    try{
                        this.updateBookkeepingEas(fsscVoucherMain);
                        successInt++;
                        if(successInt==countInt){//多张凭证情况下，成功的凭证数和总凭证数相等，则自动通过六成熟
                        	updateAutoPassNode(fsscVoucherMain);
                        }
                        successCodes.append(fsscVoucherMain.getDocNumber()+";");
                    } catch (Exception e) {
                        failureInt++;
                        failureCodes.append(fsscVoucherMain.getDocNumber()+";");
                        e.printStackTrace();
                        message.append(e.getMessage()+"<br/>");
                        //保存失败原因
                        fsscVoucherMain.setFdBookkeepingMessage(e.getMessage());
                        fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11);
                        this.update(fsscVoucherMain);

                        //记账失败，写入记账状态和记账失败原因
                        //固定记账状态字段为fdBookkeepingStatus，记账失败原因字段为fdBookkeepingMessage，并且数据字典里面也要有
                        SysDictModel dict = SysDataDict.getInstance().getModel(fsscVoucherMain.getFdModelName());
                        if(dict != null){
                            Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
                            if(StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdBookkeepingMessage") && dictMap.containsKey("fdBookkeepingStatus")){
                                SysDictCommonProperty fdBookkeepingStatusProperty = dictMap.get("fdBookkeepingStatus");
                                SysDictCommonProperty fdBookkeepingMessageProperty = dictMap.get("fdBookkeepingMessage");
                                StringBuffer sql = new StringBuffer();
                                sql.append(" update ").append(dict.getTable());
                                sql.append(" set ").append(fdBookkeepingStatusProperty.getColumn()).append(" = '"+FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_11 +"',");
                                sql.append(fdBookkeepingMessageProperty.getColumn()).append(" = '"+ e.getMessage() +"'");
                                sql.append(" where fd_id = '").append(fsscVoucherMain.getFdModelId()).append("' ");
                                NativeQuery query=this.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
                                query.addSynchronizedQuerySpace(dict.getTable());
                                query.executeUpdate();
                            }
                        }
                    }
                }
            } else if(EopBasedataConstant.FSSC_BASE_K3.equals(fdJoinSystem)){//存在k3模块
            	if(StringUtil.isNull(eopBasedataCompany.getFdKUrl())){
                    throw new Exception(ResourceUtil.getString("bookkeeping.voucher.k3.url.null.error", "fssc-voucher"));
                }
                for(FsscVoucherMain fsscVoucherMain : fsscVoucherMainList){
                    try{
                        this.updateBookkeepingK3(fsscVoucherMain);
                        successInt++;
                        if(successInt==countInt){//多张凭证情况下，成功的凭证数和总凭证数相等，则自动通过六成熟
                        	updateAutoPassNode(fsscVoucherMain);
                        }
                        successCodes.append(fsscVoucherMain.getDocNumber()+";");
                    } catch (Exception e) {
                        failureInt++;
                        failureCodes.append(fsscVoucherMain.getDocNumber()+";");
                        e.printStackTrace();
                        message.append(e.getMessage()+"<br/>");
                        //如果是K3已存在相同费控编号的凭证，写入不成功！ 就不修改
                        if(!ResourceUtil.getString("bookkeeping.voucher.k3.exist.error", "fssc-voucher").equals(e.getMessage())){
	                        //保存失败原因
	                        fsscVoucherMain.setFdBookkeepingMessage(e.getMessage());
	                        fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11);
	                        this.update(fsscVoucherMain);
	
	                        //记账失败，写入记账状态和记账失败原因
	                        //固定记账状态字段为fdBookkeepingStatus，记账失败原因字段为fdBookkeepingMessage，并且数据字典里面也要有
	                        SysDictModel dict = SysDataDict.getInstance().getModel(fsscVoucherMain.getFdModelName());
	                        if(dict != null){
	                            Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
	                            if(StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdBookkeepingMessage") && dictMap.containsKey("fdBookkeepingStatus")){
	                                SysDictCommonProperty fdBookkeepingStatusProperty = dictMap.get("fdBookkeepingStatus");
	                                SysDictCommonProperty fdBookkeepingMessageProperty = dictMap.get("fdBookkeepingMessage");
	                                StringBuffer sql = new StringBuffer();
	                                sql.append(" update ").append(dict.getTable());
	                                sql.append(" set ").append(fdBookkeepingStatusProperty.getColumn()).append(" = '"+FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_11 +"',");
	                                sql.append(fdBookkeepingMessageProperty.getColumn()).append(" = '"+ e.getMessage() +"'");
	                                sql.append(" where fd_id = '").append(fsscVoucherMain.getFdModelId()).append("' ");
                                    NativeQuery query=this.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
                                    query.addSynchronizedQuerySpace(dict.getTable());
                                    query.executeUpdate();
	                            }
	                        }
                        }
                    }
                }
            }else if(EopBasedataConstant.FSSC_BASE_K3CLOUD.equals(fdJoinSystem)) {//K3Cloud
            	if(StringUtil.isNull(eopBasedataCompany.getFdK3cUrl())||StringUtil.isNull(eopBasedataCompany.getFdK3cId())||StringUtil.isNull(eopBasedataCompany.getFdK3cPersonName())||StringUtil.isNull(eopBasedataCompany.getFdK3cPassword())||StringUtil.isNull(eopBasedataCompany.getFdK3cIcid())){
                    throw new Exception(ResourceUtil.getString("bookkeeping.voucher.k3Cloud.url.null.error", "fssc-voucher"));
                }
            	
            	for(FsscVoucherMain fsscVoucherMain : fsscVoucherMainList){
                    try{
                        this.updateBookkeepingK3Cloud(fsscVoucherMain);
                        successInt++;
                        if(successInt==countInt){//多张凭证情况下，成功的凭证数和总凭证数相等，则自动通过六成熟
                        	updateAutoPassNode(fsscVoucherMain);
                        }
                        successCodes.append(fsscVoucherMain.getDocNumber()+";");
                    } catch (Exception e) {
                        failureInt++;
                        failureCodes.append(fsscVoucherMain.getDocNumber()+";");
                        e.printStackTrace();
                        message.append(e.getMessage()+"<br/>");
                        //如果是K3已存在相同费控编号的凭证，写入不成功！ 就不修改
                        if(!ResourceUtil.getString("bookkeeping.voucher.K3cloud.exist.error", "fssc-voucher").equals(e.getMessage())){
	                        //保存失败原因
	                        fsscVoucherMain.setFdBookkeepingMessage(e.getMessage());
	                        fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11);
	                        this.update(fsscVoucherMain);
	
	                        //记账失败，写入记账状态和记账失败原因
	                        //固定记账状态字段为fdBookkeepingStatus，记账失败原因字段为fdBookkeepingMessage，并且数据字典里面也要有
	                        SysDictModel dict = SysDataDict.getInstance().getModel(fsscVoucherMain.getFdModelName());
	                        if(dict != null){
	                            Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
	                            if(StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdBookkeepingMessage") && dictMap.containsKey("fdBookkeepingStatus")){
	                                SysDictCommonProperty fdBookkeepingStatusProperty = dictMap.get("fdBookkeepingStatus");
	                                SysDictCommonProperty fdBookkeepingMessageProperty = dictMap.get("fdBookkeepingMessage");
	                                StringBuffer sql = new StringBuffer();
	                                sql.append(" update ").append(dict.getTable());
	                                sql.append(" set ").append(fdBookkeepingStatusProperty.getColumn()).append(" = '"+FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_11 +"',");
	                                sql.append(fdBookkeepingMessageProperty.getColumn()).append(" = '"+ e.getMessage() +"'");
	                                sql.append(" where fd_id = '").append(fsscVoucherMain.getFdModelId()).append("' ");
                                    NativeQuery query=this.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
                                    query.addSynchronizedQuerySpace(dict.getTable());
                                    query.executeUpdate();
	                            }
	                        }
                        }
                    }
                }
            	
            	
            	
            } else {
                //找不到对应的凭证推送方式，请检查！
                throw new Exception(ResourceUtil.getString("bookkeeping.voucher.type.error", "fssc-voucher"));
            }
        }
        rtnMap.put("message", message.toString());
        rtnMap.put("countInt", countInt+"");
        rtnMap.put("successInt", successInt+"");
        rtnMap.put("successCodes", successCodes.toString());
        rtnMap.put("failureInt", failureInt+"");
        rtnMap.put("failureCodes", failureCodes.toString());
        return rtnMap;
    }

    @Override
    public synchronized void updateBookkeepingU8(FsscVoucherMain fsscVoucherMain) throws Exception {
        EopBasedataCompany eopBasedataCompany = fsscVoucherMain.getFdCompany();
        // 系统号
        String fdUeai = eopBasedataCompany.getFdSystemParam();
        if (StringUtil.isNull(fdUeai)) {
            throw new Exception(ResourceUtil.getString("bookkeeping.voucher.u8.fdUeai.error", "fssc-voucher"));
        }
        boolean isOk = false;
        // 查询u8中凭证费控编号
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fdUeai", fdUeai);
        map.put("docNumber", fsscVoucherMain.getDocNumber());
        map.put("companyId", eopBasedataCompany.getFdId());
        Map<String, String> rtnMap = getFsscCommonU8Service().isExist(map);
        if("failure".equals(rtnMap.get("result"))){
            throw new Exception(rtnMap.get("message"));
        }
        // 存在相同费控编号的凭证，不写入
        if ("true".equals(rtnMap.get("fdIsExist"))) {
            //U8已存在相同费控编号的凭证，写入不成功！
            throw new Exception(ResourceUtil.getString("bookkeeping.voucher.u8.exist.error", "fssc-voucher"));
        } else {
            // 写入操作
            isOk = updateVoucherU8(fdUeai, fsscVoucherMain);
            if (isOk) {
            	updateBookkeepingTime(fsscVoucherMain);
            }
        }
    }

    /**
     * U8写入操作执行
     *
     * @throws Exception
     */
    private boolean updateVoucherU8(String fdUeai, FsscVoucherMain fsscVoucherMain) throws Exception {
        // 凭证写入
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fdUeai", fdUeai);//系统号
        map.put("docNumber", fsscVoucherMain.getDocNumber());//费控凭证号
        map.put("fdVoucherType", fsscVoucherMain.getFdBaseVoucherType()==null?"":fsscVoucherMain.getFdBaseVoucherType().getFdCode());//凭证类别
        map.put("fdAccountingYear", fsscVoucherMain.getFdAccountingYear());//会计年度
        map.put("fdPeriod", fsscVoucherMain.getFdPeriod());//期间
        map.put("fdNumber", fsscVoucherMain.getFdNumber());//单据数
        map.put("fdVoucherDate", DateUtil.convertDateToString(fsscVoucherMain.getFdVoucherDate(), DateUtil.PATTERN_DATE));//凭证日期（yyyy-MM-dd）
        map.put("docCreatorName", fsscVoucherMain.getDocCreator().getFdName());//制单人名称
        map.put("fdCashierName", fsscVoucherMain.getDocCreator().getFdName());//出纳人名称
        map.put("fdBaseCurrencyCode", fsscVoucherMain.getFdBaseCurrency().getFdAbbreviation());//币种编号
        List<Map<String, String>> detailMapList = new ArrayList<Map<String, String>>();
        Map<String, String> detailMap = null;
        for(FsscVoucherDetail detail : fsscVoucherMain.getFdDetail()){
            detailMap = new HashMap<String, String>();
            detailMap.put("fdBaseAccountsCode", detail.getFdBaseAccounts()!=null?detail.getFdBaseAccounts().getFdCode():"");//科目编码
            detailMap.put("fdVoucherText", detail.getFdVoucherText());//凭证摘要
            detailMap.put("fdType", detail.getFdType());//借贷 1借 2贷
            detailMap.put("fdMoney", detail.getFdMoney()+"");//金额
            if(detail.getFdBaseAccounts()!=null){
            	detailMap.put("fdAccountProperty", detail.getFdBaseAccounts().getFdCostItem()==null?"":detail.getFdBaseAccounts().getFdCostItem());//核算属性
            }else{
            	detailMap.put("fdAccountProperty", "");//核算属性
            }
            String fdBaseErpPersonCode = "";
            String fdBaseErpPersonDeptCode = "";
            if(detail.getFdBaseErpPerson() != null){
                fdBaseErpPersonCode = detail.getFdBaseErpPerson().getFdCode();
                fdBaseErpPersonDeptCode = detail.getFdBaseErpPerson().getFdDeptCode();	//部门编码
            }
            detailMap.put("fdBaseErpPersonCode", fdBaseErpPersonCode);//erp个人编号
            detailMap.put("fdBaseErpPersonDeptCode", fdBaseErpPersonDeptCode);//erp部门编号
            detailMap.put("fdBaseCostCenterCode", detail.getFdBaseCostCenter()==null?"":detail.getFdBaseCostCenter().getFdCode());//成本中心编号
            detailMap.put("fdBaseCustomerCode", detail.getFdBaseCustomer()==null?"":detail.getFdBaseCustomer().getFdCode());//客户编号
            String fdBaseCashFlowName = "";
            String fdBaseCashFlowCode = "";
            String fdBaseCashFlowParentCode = "";
            if(detail.getFdBaseCashFlow() != null){
                fdBaseCashFlowName = detail.getFdBaseCashFlow().getFdName();
                fdBaseCashFlowCode = detail.getFdBaseCashFlow().getFdCode();
                if(detail.getFdBaseCashFlow().getFdParent() != null){
                    fdBaseCashFlowParentCode = ((EopBasedataCashFlow)detail.getFdBaseCashFlow().getFdParent()).getFdCode();
                }
            }
            detailMap.put("fdBaseCashFlowName", fdBaseCashFlowName);//现金流量项目名称
            detailMap.put("fdBaseCashFlowCode", fdBaseCashFlowCode);//现金流量项目编号
            detailMap.put("fdBaseCashFlowParentCode", fdBaseCashFlowParentCode);//现金流量项目父类编号
            String fdBaseProjectCode = "";
            String fdBaseProjectCategoryCode = "";
            if(detail.getFdBaseProject() != null) {
                 fdBaseProjectCode = detail.getFdBaseProject().getFdCode();
                if (detail.getFdBaseProject().getFdParent() != null) {
                	String data[]=((EopBasedataProject)detail.getFdBaseProject()).getFdHierarchyId().split("x");
                	fdBaseProjectCategoryCode=((EopBasedataProject)getEopBasedataProjectService().findByPrimaryKey(data[1])).getFdCode();
                	
                }
            }
            detailMap.put("fdBaseProjectCode", fdBaseProjectCode);//项目编号
            detailMap.put("fdBaseProjectCategoryCode", fdBaseProjectCategoryCode);//项目大类
            detailMap.put("fdBaseSupplierCode", detail.getFdBaseSupplier()!=null?detail.getFdBaseSupplier().getFdCode():"");//供应商
            detailMap.put("fdDeptCode", detail.getFdDept()!=null?detail.getFdDept().getFdNo():"");//部门
            detailMap.put("fdContractCode", detail.getFdContractCode());	//合同编号
            detailMapList.add(detailMap);
        }
        map.put("detailMapList", detailMapList);
        map.put("companyId", fsscVoucherMain.getFdCompany().getFdId());
        List returnlist = getFsscCommonU8Service().synVoucher(map);
        if (returnlist.size() > 0) {
            String succeed = ((Map<String, String>) returnlist.get(0)).get("succeed");
            if ("0".equals(succeed)) {//成功
                String docFinanceNumber = ((Map<String, String>) returnlist.get(0)).get("u8voucher_id");
                fsscVoucherMain.setDocFinanceNumber(docFinanceNumber);//财务凭证号
                fsscVoucherMain.setFdBookkeepingMessage("");
                fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30);//记账状态
                fsscVoucherMain.setFdBookkeepingDate(Calendar.getInstance().getTime());//记账日期
                fsscVoucherMain.setFdBookkeepingPerson(UserUtil.getUser());//记账人
                //如果制单人是匿名用户，就修改为当前登录人
                if(fsscVoucherMain.getDocCreator() == UserUtil.getAnonymousUser().getPerson()){
                    fsscVoucherMain.setDocCreator(UserUtil.getUser());
                }
                this.update(fsscVoucherMain);
                logger.info(fsscVoucherMain.getDocNumber()+"凭证写入u8成功:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                System.out.println(fsscVoucherMain.getDocNumber()+"凭证写入u8成功:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                return true;
            } else {
                String dsc = "";
                int size = returnlist.size();
                for (int i = 0; i < size; i++) {
                    if (i == size - 1) {
                        dsc += ((Map<String, String>) returnlist.get(i)).get("dsc");
                    } else {
                        dsc += ((Map<String, String>) returnlist.get(i)).get("dsc")+ ";</br>";
                    }

                }
                //记账失败，请检查制证是否符合U8规范！
                throw new Exception(ResourceUtil.getString("bookkeeping.voucher.u8.standard.error", "fssc-voucher")
                        .replace("%text%", dsc));
            }
        } else {
            //凭证写入U8失败,请检查制证信息配置！
            throw new Exception(ResourceUtil.getString("bookkeeping.voucher.u8.write.error", "fssc-voucher"));
        }
    }
   

    //写入EAS凭证
    @Override
    public synchronized void updateBookkeepingEas(FsscVoucherMain fsscVoucherMain) throws Exception {
        Map<String, String> loginMap = getFsscCommonEasService().login(fsscVoucherMain.getFdCompany()==null?"":fsscVoucherMain.getFdCompany().getFdId());
        if("failure".equals(loginMap.get("result"))){
            throw new Exception(loginMap.get("message"));
        }
        String fdSessionId = loginMap.get("fdSessionId");
        // 凭证写入
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fdSessionId", fdSessionId);//登录fdSessionId
        map.put("docNumber", fsscVoucherMain.getDocNumber());//费控凭证号
        map.put("fdBaseVoucherTypeCode", fsscVoucherMain.getFdBaseVoucherType()==null?"":fsscVoucherMain.getFdBaseVoucherType().getFdCode());//凭证类别
        map.put("fdCompanyCode", fsscVoucherMain.getFdCompany()==null?"":fsscVoucherMain.getFdCompany().getFdCode());//记账公司编码
        map.put("fdBaseCurrencySymbol", fsscVoucherMain.getFdBaseCurrency()==null?"":fsscVoucherMain.getFdBaseCurrency().getFdAbbreviation());//币种符号
        map.put("fdNumber", fsscVoucherMain.getFdNumber());//单据数
        map.put("fdVoucherDate", DateUtil.convertDateToString(fsscVoucherMain.getFdVoucherDate(), DateUtil.PATTERN_DATE));//凭证日期（yyyy-MM-dd）
        map.put("fdBookkeepingDate", DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATE));//记账日期（yyyy-MM-dd）
        List<Map<String, Object>> detailMapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> detailMap = null;
        for(FsscVoucherDetail detail : fsscVoucherMain.getFdDetail()){
            detailMap = new HashMap<String, Object>();
            detailMap.put("fdBaseAccountsCode", detail.getFdBaseAccounts().getFdCode());//科目编码
            detailMap.put("fdVoucherText", detail.getFdVoucherText());//凭证摘要
            detailMap.put("fdType", detail.getFdType());//借贷 1借 2贷
            detailMap.put("fdMoney", detail.getFdMoney());//金额
            detailMap.put("eopBasedataCostCenter", detail.getFdBaseCostCenter());//核算项目之部门
            detailMap.put("eopBasedataErpPerson", detail.getFdBaseErpPerson());//erp个人
            detailMap.put("eopBasedataSupplier", detail.getFdBaseSupplier());//供应商
            detailMap.put("eopBasedataCustomer", detail.getFdBaseCustomer());//客户
            detailMap.put("eopBasedataCashFlow", detail.getFdBaseCashFlow());//现金流量项目
            detailMap.put("eopBasedataProject", detail.getFdBaseProject());//核算项目
            detailMap.put("eopBasedataPayBank", detail.getFdBasePayBank());//银行
            detailMap.put("fdDept", detail.getFdDept());//部门
            detailMap.put("fdContractCode", detail.getFdContractCode());//合同编号
            String fdContractName = "";
            if(StringUtil.isNotNull(detail.getFdContractCode())){
            	if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
            		fdContractName = getFsscLedgerContractCommonService().getFdContracNameByCode(detail.getFdContractCode());
            	}
            }
            detailMap.put("fdContractName", fdContractName);//合同名称
            detailMapList.add(detailMap);
        }
        map.put("detailMapList", detailMapList);
        map.put("fdEImportVoucherWsdlUrl", fsscVoucherMain.getFdCompany()==null?"":fsscVoucherMain.getFdCompany().getFdEImportVoucherWsdlUrl());
        Map<String, String> rtnMap = getFsscCommonEasService().importVoucher(map);
        if("success".equals(rtnMap.get("result"))){//成功
            String docFinanceNumber = rtnMap.get("fdEasNo");
            fsscVoucherMain.setDocFinanceNumber(docFinanceNumber);//财务凭证号
            fsscVoucherMain.setFdBookkeepingMessage("");
            fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30);//记账状态
            fsscVoucherMain.setFdBookkeepingDate(Calendar.getInstance().getTime());//记账日期
            fsscVoucherMain.setFdBookkeepingPerson(UserUtil.getUser());//记账人
            //如果制单人是匿名用户，就修改为当前登录人
            if(fsscVoucherMain.getDocCreator() == UserUtil.getAnonymousUser().getPerson()){
                fsscVoucherMain.setDocCreator(UserUtil.getUser());
            }
            this.update(fsscVoucherMain);
            updateBookkeepingTime(fsscVoucherMain);  //回写记账时间
            logger.info(fsscVoucherMain.getDocNumber()+"凭证写入Eas成功:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            System.out.println(fsscVoucherMain.getDocNumber()+"凭证写入Eas成功:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            return;
        }else{//失败
            throw new Exception(ResourceUtil.getString("bookkeeping.voucher.eas.standard.error", "fssc-voucher")
                    .replace("%text%", rtnMap.get("message")));
        }
    }
    
    @Override
    public synchronized void updateBookkeepingK3(FsscVoucherMain fsscVoucherMain) throws Exception {
        EopBasedataCompany eopBasedataCompany = fsscVoucherMain.getFdCompany();
        // 账套ID
        String fdKId = eopBasedataCompany.getFdSystemParam();
        if (StringUtil.isNull(fdKId)) {
            throw new Exception(ResourceUtil.getString("bookkeeping.voucher.k3.fdKId.error", "fssc-voucher"));
        }
        boolean isOk = false;
        // 查询k3中凭证费控编号
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fdKId", fdKId);
        map.put("docNumber", fsscVoucherMain.getDocNumber());
        map.put("companyId", eopBasedataCompany.getFdId());
        Map<String, String> rtnMap = getFsscCommonK3Service().isExist(map);
        if("failure".equals(rtnMap.get("result"))){
            throw new Exception(rtnMap.get("message"));
        }
        // 存在相同费控编号的凭证，不写入
        if ("true".equals(rtnMap.get("fdIsExist"))) {
            //K3已存在相同费控编号的凭证，写入不成功！
            throw new Exception(ResourceUtil.getString("bookkeeping.voucher.k3.exist.error", "fssc-voucher"));
        } else {
            // 写入操作
            isOk = updateVoucherK3(fdKId, fsscVoucherMain);
            if (isOk) {
            	updateBookkeepingTime(fsscVoucherMain);
            }
        }
    }
    
    /**
     * 记账成功回写记账时间
     * @param fsscVoucherMain
     * @throws Exception
     */
    public void updateBookkeepingTime(FsscVoucherMain fsscVoucherMain) throws Exception{
    	//写入成功，判断对应单据的所有凭证是否记账成功，如果是对应单据的所有凭证记账成功就修改单据的记账状态为已记账
        if(isNoBookkeeping(fsscVoucherMain.getFdModelId(), fsscVoucherMain.getFdModelName())) {
            //固定记账状态字段为fdBookkeepingStatus，并且数据字典里面也要有
            SysDictModel dict = SysDataDict.getInstance().getModel(fsscVoucherMain.getFdModelName());
            if (dict != null) {
                Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
                if (StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdBookkeepingStatus") && dictMap.containsKey("fdBookkeepingMessage")) {
                    SysDictCommonProperty sysDictCommonProperty = dictMap.get("fdBookkeepingStatus");
                    SysDictCommonProperty sysDictCommonProperty1 = dictMap.get("fdBookkeepingMessage");
                    StringBuffer sql = new StringBuffer();
                    sql.append(" update ").append(dict.getTable());
                    sql.append(" set ").append(sysDictCommonProperty.getColumn()).append(" = '" + FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30 + "'");
                    sql.append(",").append(sysDictCommonProperty1.getColumn()).append(" = ''");
                    if(dictMap.containsKey("fdBookkeepingTime")) {	//有记账时间字段，写入记账时间
                    	sql.append(",").append(dictMap.get("fdBookkeepingTime").getColumn()).append(" = :fdBookkeepingTime");
                    }
                    sql.append(" where fd_id = '").append(fsscVoucherMain.getFdModelId()).append("' ");
                    NativeQuery query=this.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
                    if(dictMap.containsKey("fdBookkeepingTime")) {	//有记账时间字段
                    	query.setParameter("fdBookkeepingTime", new Date());
                    }
                    query.addSynchronizedQuerySpace(dict.getTable());
                    query.executeUpdate();
                }
            }
        }
    }
    
    /**
     * K3写入操作执行
     * @throws Exception
     */
    private boolean updateVoucherK3(String fdKId, FsscVoucherMain fsscVoucherMain) throws Exception {
        // 凭证写入
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fdKId", fdKId);//系统号
        map.put("docNumber", fsscVoucherMain.getDocNumber());//费控凭证号
        map.put("fdVoucherType", fsscVoucherMain.getFdBaseVoucherType()==null?"":fsscVoucherMain.getFdBaseVoucherType().getFdCode());//凭证类别
        map.put("fdAccountingYear", fsscVoucherMain.getFdAccountingYear());//会计年度
        map.put("fdPeriod", fsscVoucherMain.getFdPeriod());//期间
        map.put("fdNumber", fsscVoucherMain.getFdNumber());//单据数
        map.put("fdVoucherDate", DateUtil.convertDateToString(fsscVoucherMain.getFdVoucherDate(), DateUtil.PATTERN_DATE));//凭证日期（yyyy-MM-dd）
        map.put("fdVoucherText", fsscVoucherMain.getFdVoucherText());//凭证抬头文本
        map.put("docCreatorName", fsscVoucherMain.getDocCreator().getFdName());//制单人名称
        map.put("fdCashierName", fsscVoucherMain.getDocCreator().getFdName());//出纳人名称
        List<Map<String, Object>> detailMapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> detailMap = null;
        for(FsscVoucherDetail detail : fsscVoucherMain.getFdDetail()){
            detailMap = new HashMap<String, Object>();
            detailMap.put("fdBaseAccountsCode", detail.getFdBaseAccounts().getFdCode());//科目编码
            detailMap.put("fdBaseAccountsName", detail.getFdBaseAccounts().getFdName());//科目名称
            detailMap.put("fdVoucherText", detail.getFdVoucherText());//凭证摘要
            detailMap.put("fdType", detail.getFdType());//借贷 1借 2贷
            detailMap.put("fdMoney", detail.getFdMoney()+"");//金额
            detailMap.put("fdAccountProperty", detail.getFdBaseAccounts().getFdCostItem()==null?"":detail.getFdBaseAccounts().getFdCostItem());//核算属性
            detailMap.put("eopBasedataCostCenter", detail.getFdBaseCostCenter());//成本中心
            detailMap.put("eopBasedataProject", detail.getFdBaseProject());//核算项目
            detailMap.put("eopBasedataCustomer", detail.getFdBaseCustomer());//客户
            detailMap.put("eopBasedataCashFlow", detail.getFdBaseCashFlow());//现金流量项目
            detailMap.put("eopBasedataErpPerson", detail.getFdBaseErpPerson());//个人
            detailMap.put("eopBasedataPayBank", detail.getFdBasePayBank());//银行
            detailMap.put("eopBasedataSupplier", detail.getFdBaseSupplier());//供应商
            detailMap.put("fdDept", detail.getFdDept());//部门
            detailMap.put("fdContractCode", detail.getFdContractCode());//合同编号
            String fdContractName = "";
            if(StringUtil.isNotNull(detail.getFdContractCode())){
            	if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
            		fdContractName = getFsscLedgerContractCommonService().getFdContracNameByCode(detail.getFdContractCode());
            	}
            }
            detailMap.put("fdContractName", fdContractName);//合同名称
            detailMapList.add(detailMap);
        }
        map.put("detailMapList", detailMapList);
        map.put("companyId", fsscVoucherMain.getFdCompany().getFdId());
        List returnlist = getFsscCommonK3Service().synVoucher(map);
        if (returnlist.size() > 0) {
            String succeed = ((Map<String, String>) returnlist.get(0)).get("succeed");
            if ("0".equals(succeed)) {//成功
                String docFinanceNumber = ((Map<String, String>) returnlist.get(0)).get("u8voucher_id");
                String dsc = ((Map<String, String>) returnlist.get(0)).get("dsc");
                fsscVoucherMain.setDocFinanceNumber(docFinanceNumber);//财务凭证号
                fsscVoucherMain.setFdBookkeepingMessage(dsc);
                fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30);//记账状态
                fsscVoucherMain.setFdBookkeepingDate(Calendar.getInstance().getTime());//记账日期
                fsscVoucherMain.setFdBookkeepingPerson(UserUtil.getUser());//记账人
                //如果制单人是匿名用户，就修改为当前登录人
                if(fsscVoucherMain.getDocCreator() == UserUtil.getAnonymousUser().getPerson()){
                    fsscVoucherMain.setDocCreator(UserUtil.getUser());
                }
                this.update(fsscVoucherMain);
                logger.info(fsscVoucherMain.getDocNumber()+"凭证写入k3成功:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                System.out.println(fsscVoucherMain.getDocNumber()+"凭证写入k3成功:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                return true;
            } else {
                String dsc = "";
                int size = returnlist.size();
                for (int i = 0; i < size; i++) {
                    if (i == size - 1) {
                        dsc += ((Map<String, String>) returnlist.get(i)).get("dsc");
                    } else {
                        dsc += ((Map<String, String>) returnlist.get(i)).get("dsc")+ ";</br>";
                    }

                }
                //记账失败，请检查制证是否符合K3规范！
                throw new Exception(ResourceUtil.getString("bookkeeping.voucher.k3.standard.error", "fssc-voucher")
                        .replace("%text%", dsc));
            }
        } else {
            //凭证写入K3失败,请检查制证信息配置！
            throw new Exception(ResourceUtil.getString("bookkeeping.voucher.k3.write.error", "fssc-voucher"));
        }
    }
    
    /**
     * 
     * 批量记账
     * @throws Exception 
     * 
     */
    @Override
	public Map<String, String> updateBatchBookkeeping(String fdVoucherMainIds) throws Exception {
    	 Map<String, String> rtnMap = new HashMap<>(); 
    	int countInt = 0;//总数
        int successInt = 0;//成功数
        int failureInt = 0;//失败数
        StringBuilder successCodes = new StringBuilder();//成功编号
        StringBuilder failureCodes = new StringBuilder();//失败编号
        Map<String,Boolean> successMap=new HashMap<String,Boolean>();  //接收单据凭证是否全部记账成功，记账成功标识为true
        Map<String,FsscVoucherMain> handleMap=new HashMap<String,FsscVoucherMain>();  //需要处理的凭证，因为流程处理只要fdModelId和fdModelName，同一个只要put一次
        StringBuilder message = new StringBuilder();
    	if(StringUtil.isNotNull(fdVoucherMainIds)){
    		HQLInfo hqlInfo = new HQLInfo();
	        hqlInfo.setWhereBlock(" fsscVoucherMain.fdId in (:fdId) ");
	        hqlInfo.setParameter("fdId", Arrays.asList(fdVoucherMainIds.split(",")));
	    	List<FsscVoucherMain> fsscVoucherMainList =  this.findList(hqlInfo);
			if(!fsscVoucherMainList.isEmpty()){
				for(int i=0;i<fsscVoucherMainList.size();i++){
					message.append(i+1+"、");
					FsscVoucherMain fsscVoucherMain = fsscVoucherMainList.get(i);
					handleMap.put(fsscVoucherMain.getFdModelId(), fsscVoucherMain);
					if(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30.equals(fsscVoucherMain.getFdBookkeepingStatus())){//如果是已记账就跳过
						message.append(ResourceUtil.getString("enums.fd_bookkeeping_status.30", "fssc-voucher"));
	                    continue;
	                }
					Boolean successFlag=false;
					EopBasedataCompany  eopBasedataCompany =  fsscVoucherMain.getFdCompany();
					String fdJoinSystem = eopBasedataCompany.getFdJoinSystem();
					if(EopBasedataConstant.FSSC_BASE_U8.equals(fdJoinSystem)){//存在u8模块
						if(StringUtil.isNull(eopBasedataCompany.getFdUEightUrl())){
		                    throw new Exception(ResourceUtil.getString("bookkeeping.voucher.U8.url.null.error", "fssc-voucher"));
		                }
	                    try{
	                        this.updateBookkeepingU8(fsscVoucherMain);
	                        successFlag=true;
	                        message.append(ResourceUtil.getString("bookkeeping.success", "fssc-voucher")+"<br/>");
	                        successInt++;
	                        if(!successMap.containsKey(fsscVoucherMain.getFdModelId())) { //前面未出现该单据的凭证，或者前面出现失败、成功,都不需要覆盖以前状态
	                        	successMap.put(fsscVoucherMain.getFdModelId(), Boolean.TRUE);
	                        }
	                        successCodes.append(fsscVoucherMain.getDocNumber()+";");
	                    } catch (Exception e) {
	                        failureInt++;
	                        if(!successMap.containsKey(fsscVoucherMain.getFdModelId())||successMap.get(fsscVoucherMain.getFdModelId())) { //前面未出现该单据的凭证,或者前面是成功的，只要有一个是失败的，整个流程不允许通过
	                        	successMap.put(fsscVoucherMain.getFdModelId(), Boolean.FALSE);
	                        }
	                        failureCodes.append(fsscVoucherMain.getDocNumber()+";");
	                        e.printStackTrace();
	                        message.append(e.getMessage()+"<br/>");
	                        //如果是 U8已存在相同费控编号的凭证，写入不成功！ 就不修改
	                        if(!ResourceUtil.getString("bookkeeping.voucher.u8.exist.error", "fssc-voucher").equals(e.getMessage())){
	                            //保存失败原因
	                            fsscVoucherMain.setFdBookkeepingMessage(e.getMessage());
	                            fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11);
	                            this.update(fsscVoucherMain);

	                            //记账失败，写入记账状态和记账失败原因
	                            //固定记账状态字段为fdBookkeepingStatus，记账失败原因字段为fdBookkeepingMessage，并且数据字典里面也要有
	                            SysDictModel dict = SysDataDict.getInstance().getModel(fsscVoucherMain.getFdModelName());
	                            if(dict != null){
	                                Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
	                                if(StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdBookkeepingMessage") && dictMap.containsKey("fdBookkeepingStatus")){
	                                    SysDictCommonProperty fdBookkeepingStatusProperty = dictMap.get("fdBookkeepingStatus");
	                                    SysDictCommonProperty fdBookkeepingMessageProperty = dictMap.get("fdBookkeepingMessage");
	                                    StringBuffer sql = new StringBuffer();
	                                    sql.append(" update ").append(dict.getTable());
	                                    sql.append(" set ").append(fdBookkeepingStatusProperty.getColumn()).append(" = '"+FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_11 +"',");
	                                    sql.append(fdBookkeepingMessageProperty.getColumn()).append(" = '"+ e.getMessage() +"'");
	                                    sql.append(" where fd_id = '").append(fsscVoucherMain.getFdModelId()).append("' ");
                                        NativeQuery query=this.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
                                        query.addSynchronizedQuerySpace(dict.getTable());
                                        query.executeUpdate();
	                                }
	                            }
	                        }
	                    }
					}else if(EopBasedataConstant.FSSC_BASE_EAS.equals(fdJoinSystem)){//存在eas模块
	                    try{
	                        this.updateBookkeepingEas(fsscVoucherMain);
	                        successFlag=true;
	                        message.append(ResourceUtil.getString("bookkeeping.success", "fssc-voucher")+"<br/>");
	                        successInt++;
	                        if(!successMap.containsKey(fsscVoucherMain.getFdModelId())) { //前面未出现该单据的凭证，或者前面出现失败、成功,都不需要覆盖以前状态
	                        	successMap.put(fsscVoucherMain.getFdModelId(), Boolean.TRUE);
	                        }
	                        successCodes.append(fsscVoucherMain.getDocNumber()+";");
	                    } catch (Exception e) {
	                        failureInt++;
	                        if(!successMap.containsKey(fsscVoucherMain.getFdModelId())||successMap.get(fsscVoucherMain.getFdModelId())) { //前面未出现该单据的凭证,或者前面是成功的，只要有一个是失败的，整个流程不允许通过
	                        	successMap.put(fsscVoucherMain.getFdModelId(), Boolean.FALSE);
	                        }
	                        failureCodes.append(fsscVoucherMain.getDocNumber()+";");
	                        e.printStackTrace();
	                        message.append(e.getMessage()+"<br/>");
	                        //保存失败原因
	                        fsscVoucherMain.setFdBookkeepingMessage(e.getMessage());
	                        fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11);
	                        this.update(fsscVoucherMain);

	                        //记账失败，写入记账状态和记账失败原因
	                        //固定记账状态字段为fdBookkeepingStatus，记账失败原因字段为fdBookkeepingMessage，并且数据字典里面也要有
	                        SysDictModel dict = SysDataDict.getInstance().getModel(fsscVoucherMain.getFdModelName());
	                        if(dict != null){
	                            Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
	                            if(StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdBookkeepingMessage") && dictMap.containsKey("fdBookkeepingStatus")){
	                                SysDictCommonProperty fdBookkeepingStatusProperty = dictMap.get("fdBookkeepingStatus");
	                                SysDictCommonProperty fdBookkeepingMessageProperty = dictMap.get("fdBookkeepingMessage");
	                                StringBuffer sql = new StringBuffer();
	                                sql.append(" update ").append(dict.getTable());
	                                sql.append(" set ").append(fdBookkeepingStatusProperty.getColumn()).append(" = '"+FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_11 +"',");
	                                sql.append(fdBookkeepingMessageProperty.getColumn()).append(" = '"+ e.getMessage() +"'");
	                                sql.append(" where fd_id = '").append(fsscVoucherMain.getFdModelId()).append("' ");
                                    NativeQuery query=this.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
                                    query.addSynchronizedQuerySpace(dict.getTable());
                                    query.executeUpdate();
	                            }
	                        }
	                    }
		            }else if(EopBasedataConstant.FSSC_BASE_K3.equals(fdJoinSystem)){//存在k3模块
		            	if(StringUtil.isNull(eopBasedataCompany.getFdKUrl())){
		                    throw new Exception(ResourceUtil.getString("bookkeeping.voucher.k3.url.null.error", "fssc-voucher"));
		                }
	                    try{
	                        this.updateBookkeepingK3(fsscVoucherMain);
	                        successFlag=true;
	                        message.append(ResourceUtil.getString("bookkeeping.success", "fssc-voucher")+"<br/>");
	                        successInt++;
	                        if(!successMap.containsKey(fsscVoucherMain.getFdModelId())) { //前面未出现该单据的凭证，或者前面出现失败、成功,都不需要覆盖以前状态
	                        	successMap.put(fsscVoucherMain.getFdModelId(), Boolean.TRUE);
	                        }
	                        successCodes.append(fsscVoucherMain.getDocNumber()+";");
	                    } catch (Exception e) {
	                        failureInt++;
	                        if(!successMap.containsKey(fsscVoucherMain.getFdModelId())||successMap.get(fsscVoucherMain.getFdModelId())) { //前面未出现该单据的凭证,或者前面是成功的，只要有一个是失败的，整个流程不允许通过
	                        	successMap.put(fsscVoucherMain.getFdModelId(), Boolean.FALSE);
	                        }
	                        failureCodes.append(fsscVoucherMain.getDocNumber()+";");
	                        e.printStackTrace();
	                        message.append(e.getMessage()+"<br/>");
	                        //如果是K3已存在相同费控编号的凭证，写入不成功！ 就不修改
	                        if(!ResourceUtil.getString("bookkeeping.voucher.k3.exist.error", "fssc-voucher").equals(e.getMessage())){
		                        //保存失败原因
		                        fsscVoucherMain.setFdBookkeepingMessage(e.getMessage());
		                        fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11);
		                        this.update(fsscVoucherMain);
		
		                        //记账失败，写入记账状态和记账失败原因
		                        //固定记账状态字段为fdBookkeepingStatus，记账失败原因字段为fdBookkeepingMessage，并且数据字典里面也要有
		                        SysDictModel dict = SysDataDict.getInstance().getModel(fsscVoucherMain.getFdModelName());
		                        if(dict != null){
		                            Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
		                            if(StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdBookkeepingMessage") && dictMap.containsKey("fdBookkeepingStatus")){
		                                SysDictCommonProperty fdBookkeepingStatusProperty = dictMap.get("fdBookkeepingStatus");
		                                SysDictCommonProperty fdBookkeepingMessageProperty = dictMap.get("fdBookkeepingMessage");
		                                StringBuffer sql = new StringBuffer();
		                                sql.append(" update ").append(dict.getTable());
		                                sql.append(" set ").append(fdBookkeepingStatusProperty.getColumn()).append(" = '"+FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_11 +"',");
		                                sql.append(fdBookkeepingMessageProperty.getColumn()).append(" = '"+ e.getMessage() +"'");
		                                sql.append(" where fd_id = '").append(fsscVoucherMain.getFdModelId()).append("' ");
                                        NativeQuery query=this.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
                                        query.addSynchronizedQuerySpace(dict.getTable());
                                        query.executeUpdate();
		                            }
		                        }
	                        }
	                    }
		            }else if(EopBasedataConstant.FSSC_BASE_K3CLOUD.equals(fdJoinSystem)){//存在k3cloud模块
		            	String fdK3cUrl=eopBasedataCompany.getFdK3cUrl();//站点地址
		        		String fdK3cId=eopBasedataCompany.getFdK3cId();//账套id
		        		String fdK3cPersonName=eopBasedataCompany.getFdK3cPersonName();//K3C用户名
		        		String fdK3cPassword=eopBasedataCompany.getFdK3cPassword();//K3C密码
		        		String fdK3cIcid=eopBasedataCompany.getFdK3cIcid();//K3Clcid
		        		
		        		if(StringUtil.isNull(fdK3cUrl)||StringUtil.isNull(fdK3cId)||StringUtil.isNull(fdK3cPersonName)||StringUtil.isNull(fdK3cPassword)||StringUtil.isNull(fdK3cIcid)){
		        			 throw new Exception(ResourceUtil.getString("bookkeeping.voucher.k3cloud.url.null.error", "fssc-voucher"));
		                }
	                    try{
	                        this.updateBookkeepingK3Cloud(fsscVoucherMain);
	                        successFlag=true;
	                        message.append(ResourceUtil.getString("bookkeeping.success", "fssc-voucher")+"<br/>");
	                        successInt++;
	                        if(!successMap.containsKey(fsscVoucherMain.getFdModelId())) { //前面未出现该单据的凭证，或者前面出现失败、成功,都不需要覆盖以前状态
	                        	successMap.put(fsscVoucherMain.getFdModelId(), Boolean.TRUE);
	                        }
	                        successCodes.append(fsscVoucherMain.getDocNumber()+";");
	                    } catch (Exception e) {
	                        failureInt++;
	                        if(!successMap.containsKey(fsscVoucherMain.getFdModelId())||successMap.get(fsscVoucherMain.getFdModelId())) { //前面未出现该单据的凭证,或者前面是成功的，只要有一个是失败的，整个流程不允许通过
	                        	successMap.put(fsscVoucherMain.getFdModelId(), Boolean.FALSE);
	                        }
	                        failureCodes.append(fsscVoucherMain.getDocNumber()+";");
	                        e.printStackTrace();
	                        message.append(e.getMessage()+"<br/>");
	                        //如果是K3已存在相同费控编号的凭证，写入不成功！ 就不修改
	                        if(!ResourceUtil.getString("bookkeeping.voucher.K3cloud.exist.error", "fssc-voucher").equals(e.getMessage())){
		                        //保存失败原因
		                        fsscVoucherMain.setFdBookkeepingMessage(e.getMessage());
		                        fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11);
		                        this.update(fsscVoucherMain);
		
		                        //记账失败，写入记账状态和记账失败原因
		                        //固定记账状态字段为fdBookkeepingStatus，记账失败原因字段为fdBookkeepingMessage，并且数据字典里面也要有
		                        SysDictModel dict = SysDataDict.getInstance().getModel(fsscVoucherMain.getFdModelName());
		                        if(dict != null){
		                            Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
		                            if(StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdBookkeepingMessage") && dictMap.containsKey("fdBookkeepingStatus")){
		                                SysDictCommonProperty fdBookkeepingStatusProperty = dictMap.get("fdBookkeepingStatus");
		                                SysDictCommonProperty fdBookkeepingMessageProperty = dictMap.get("fdBookkeepingMessage");
		                                StringBuffer sql = new StringBuffer();
		                                sql.append(" update ").append(dict.getTable());
		                                sql.append(" set ").append(fdBookkeepingStatusProperty.getColumn()).append(" = '"+FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_11 +"',");
		                                sql.append(fdBookkeepingMessageProperty.getColumn()).append(" = '"+ e.getMessage() +"'");
		                                sql.append(" where fd_id = '").append(fsscVoucherMain.getFdModelId()).append("' ");
		                                this.getBaseDao().getHibernateSession().createSQLQuery(sql.toString()).executeUpdate();
		                            }
		                        }
	                        }
	                    }
		            } else {
		                //找不到对应的凭证推送方式，请检查！
		                message.append(ResourceUtil.getString("bookkeeping.voucher.type.error", "fssc-voucher"));
		            }
				}
				for(Entry<String, Boolean> entry : successMap.entrySet()){
				    String fdModelId = entry.getKey();
				    if(StringUtil.isNotNull(fdModelId)&&successMap.get(fdModelId)) {
				    	updateAutoPassNode(handleMap.get(fdModelId));
				    }
				}
			}else{
				//查找的数据为空
				message.append(ResourceUtil.getString("bookkeeping.voucher.query.null", "fssc-voucher"));
			}
    	}
    	rtnMap.put("message", message.toString());
        rtnMap.put("countInt", countInt+"");
        rtnMap.put("successInt", successInt+"");
        rtnMap.put("successCodes", successCodes.toString());
        rtnMap.put("failureInt", failureInt+"");
        rtnMap.put("failureCodes", failureCodes.toString());
		return rtnMap;
	}
    
    /**
     * 自动跳过当前节点
     *
     * @throws Exception
     */
    public void updateAutoPassNode(FsscVoucherMain voucher) throws Exception {
    	String fdModelId = voucher.getFdModelId();
        String fdModelName = voucher.getFdModelName();
        if (StringUtil.isNull(fdModelId) || StringUtil.isNull(fdModelName)) {
            return;
        }
        BaseModel model = (BaseModel) this.findByPrimaryKey(fdModelId, fdModelName, true);
        if(model!=null){
        	ISysLbpmMainForm tempForm = (ISysLbpmMainForm) this.convertModelToForm(null, model, new RequestContext());
            LbpmProcessForm sysWfBusinessForm = tempForm.getSysWfBusinessForm();
            LbpmProcess lbpmProcess = (LbpmProcess) this.findByPrimaryKey(fdModelId, LbpmProcess.class, true);
            if (ArrayUtil.isEmpty(lbpmProcess.getFdNodes())) {
                return;
            }
            LbpmNode node = lbpmProcess.getFdNodes().get(0);
            List<LbpmWorkitem> items = node.getFdWorkitems();
            if (ArrayUtil.isEmpty(items)) {
                return;
            }
            List tempPerson = FsscCommonProcessUtil.getProcessCurrentHandlers(fdModelId);
            List persons = sysOrgCoreService.expandToPerson(tempPerson);
            if (!ArrayUtil.isEmpty(persons)) {
                SysOrgPerson person = (SysOrgPerson) persons.get(0);
                StringBuilder paramBuilder = getStringBuilder(items.get(0)
                        .getFdId(), lbpmProcess.getFdId(), items.get(0).getFdActivityType(), sysWfBusinessForm.getFdModelId());
                sysWfBusinessForm.setFdParameterJson(paramBuilder.toString());
                backgroundAuthService.switchUserById(person.getFdId(),
                        new Runner() {
                            @Override
                            public Object run(Object parameter) throws Exception {
                                lbpmProcessService
                                        .updateByPanel((LbpmProcessForm) parameter);
                                return null;
                            }
                        }, sysWfBusinessForm);
            }
        }
    }
    
    /**
     * 拼接参数
     *
     * @param taskId
     * @param ProcessId
     * @param activeType
     * @param fdId
     * @return
     */
    private StringBuilder getStringBuilder(String taskId, String ProcessId, String activeType, String fdId) {
        StringBuilder paramBuilder = new StringBuilder();
        paramBuilder.append("{\"taskId\":\"").append(taskId)
                .append("\",\"processId\":\"").append(ProcessId)
                .append("\",\"activityType\":\"").append(activeType);
        paramBuilder.append("\",\"operationType\":\"handler_pass\",")
                .append("\"param\":{\"operationName\":\""+ResourceUtil.getString("pass.message", "fssc-cashier")+"\",")
                .append("\"auditNote\":\""+ResourceUtil.getString("pass.message", "fssc-cashier")+"\",");
        // 是否发送代办
        paramBuilder.append("\"notifyType\":\"todo\",").append(
                "\"notifyOnFinish\":true}}");
        return paramBuilder;
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

    public void setFsscVoucherModelConfigService(IFsscVoucherModelConfigService fsscVoucherModelConfigService) {
        this.fsscVoucherModelConfigService = fsscVoucherModelConfigService;
    }

    public void setFsscVoucherRuleConfigService(IFsscVoucherRuleConfigService fsscVoucherRuleConfigService) {
        this.fsscVoucherRuleConfigService = fsscVoucherRuleConfigService;
    }

    public IFsscCommonU8Service getFsscCommonU8Service() {
        if (fsscCommonU8Service == null) {
            fsscCommonU8Service = (IFsscCommonU8Service) SpringBeanUtil.getBean("fsscCommonU8Service");
        }
        return fsscCommonU8Service;
    }

    public IFsscCommonEasService getFsscCommonEasService() {
        if (fsscCommonEasService == null) {
            fsscCommonEasService = (IFsscCommonEasService) SpringBeanUtil.getBean("fsscCommonEasService");
        }
        return fsscCommonEasService;
    }
    
    public IFsscCommonK3Service getFsscCommonK3Service() {
        if (fsscCommonK3Service == null) {
            fsscCommonK3Service = (IFsscCommonK3Service) SpringBeanUtil.getBean("fsscCommonK3Service");
        }
        return fsscCommonK3Service;
    }

    public IFsscCommonCashierPaymentService getFsscCommonCashierPaymentService() {
        if (fsscCommonCashierPaymentService == null) {
            fsscCommonCashierPaymentService = (IFsscCommonCashierPaymentService) SpringBeanUtil.getBean("fsscCommonCashierPaymentService");
        }
        return fsscCommonCashierPaymentService;
    }
    
    public IEopBasedataProjectService getEopBasedataProjectService() {
        if (eopBasedataProjectService == null) {
        	eopBasedataProjectService = (IEopBasedataProjectService) SpringBeanUtil.getBean("eopBasedataProjectService");
        }
        return eopBasedataProjectService;
    }
    
    public IFsscCommonK3CloudService getFsscCommonK3CloudService() {
        if (fsscCommonK3CloudService == null) {
        	fsscCommonK3CloudService = (IFsscCommonK3CloudService) SpringBeanUtil.getBean("fsscCommonK3CloudService");
        }
        return fsscCommonK3CloudService;
    }
    public IEopBasedataExchangeRateService getFsscBaseExchangeRateService() {
    	return (IEopBasedataExchangeRateService) SpringBeanUtil.getBean("eopBasedataExchangeRateService");
    }
    
    public synchronized void updateBookkeepingK3Cloud(FsscVoucherMain fsscVoucherMain) throws Exception {
        EopBasedataCompany eopBasedataCompany = fsscVoucherMain.getFdCompany();
        String fdK3cUrl=eopBasedataCompany.getFdK3cUrl();//站点地址
		String fdK3cId=eopBasedataCompany.getFdK3cId();//账套id
		String fdK3cPersonName=eopBasedataCompany.getFdK3cPersonName();//K3C用户名
		String fdK3cPassword=eopBasedataCompany.getFdK3cPassword();//K3C密码
		String fdK3cIcid=eopBasedataCompany.getFdK3cIcid();//K3Clcid
		
		if(StringUtil.isNull(fdK3cUrl)||StringUtil.isNull(fdK3cId)||StringUtil.isNull(fdK3cPersonName)||StringUtil.isNull(fdK3cPassword)||StringUtil.isNull(fdK3cIcid)){
			 throw new Exception(ResourceUtil.getString("bookkeeping.voucher.k3cloud.url.null.error", "fssc-voucher"));
        }
        boolean isOk = false;
        // 查询k3中凭证费控编号
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fdK3cUrl", fdK3cUrl);
        map.put("fdK3cId", fdK3cId);
        map.put("fdK3cPersonName", fdK3cPersonName);
        map.put("fdK3cPassword", fdK3cPassword);
        map.put("fdK3cIcid", fdK3cIcid);
        map.put("docNumber", fsscVoucherMain.getDocNumber());
        map.put("fdCompanyId", fsscVoucherMain.getFdCompany().getFdId());
        Map<String, String> rtnMap = getFsscCommonK3CloudService().isExist(map);
        if("failure".equals(rtnMap.get("result"))){
            throw new Exception(rtnMap.get("message"));
        }
        // 存在相同费控编号的凭证，不写入
        if ("true".equals(rtnMap.get("fdIsExist"))) {
            //K3已存在相同费控编号的凭证，写入不成功！
            throw new Exception(ResourceUtil.getString("bookkeeping.voucher.K3cloud.exist.error", "fssc-voucher"));
        } else {
            // 写入操作
            isOk = updateVoucherK3Cloud(fdK3cUrl,fdK3cId,fdK3cPersonName,fdK3cPassword,fdK3cIcid,fsscVoucherMain);
            if (isOk) {
            	updateBookkeepingTime(fsscVoucherMain);
            }
        }
    }
    
    /**
     * K3Cloud写入操作执行
     * @throws Exception
     */
    private boolean updateVoucherK3Cloud(String fdK3cUrl,String fdK3cId,String fdK3cPersonName,String fdK3cPassword,String fdK3cIcid, FsscVoucherMain fsscVoucherMain) throws Exception {
        // 凭证写入
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fdK3cUrl", fdK3cUrl);//站点地址
        map.put("fdK3cId", fdK3cId);//账套id
        map.put("fdK3cPersonName", fdK3cPersonName);//K3C用户名
        map.put("fdK3cPassword", fdK3cPassword);//K3C密码
        map.put("fdK3cIcid", fdK3cIcid);//K3Clcid
        map.put("docNumber", fsscVoucherMain.getDocNumber());//费控凭证号
        map.put("fdVoucherType", fsscVoucherMain.getFdBaseVoucherType()==null?"":fsscVoucherMain.getFdBaseVoucherType().getFdCode());//凭证类别
        map.put("fdAccountingYear", fsscVoucherMain.getFdAccountingYear());//会计年度
        map.put("fdPeriod", fsscVoucherMain.getFdPeriod());//期间
        map.put("fdNumber", fsscVoucherMain.getFdNumber());//单据数
        map.put("fdVoucherDate", DateUtil.convertDateToString(fsscVoucherMain.getFdVoucherDate(), DateUtil.PATTERN_DATE));//凭证日期（yyyy-MM-dd）
        map.put("fdVoucherText", fsscVoucherMain.getFdVoucherText());//凭证抬头文本
        map.put("docCreatorName", fsscVoucherMain.getDocCreator().getFdName());//制单人名称
        map.put("fdCashierName", fsscVoucherMain.getDocCreator().getFdName());//出纳人名称
        map.put("fdBaseCurrencyCode", fsscVoucherMain.getFdBaseCurrency()!=null?fsscVoucherMain.getFdBaseCurrency().getFdCode():"");//币种编码
        double fdRate = getFsscBaseExchangeRateService().getRateByAccountCurrency(fsscVoucherMain.getFdCompany(), fsscVoucherMain.getFdBaseCurrency()!=null?fsscVoucherMain.getFdBaseCurrency().getFdId():"");
        map.put("fdRate", fdRate);//汇率
        
        
        List<Map<String, Object>> detailMapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> detailMap = null;
        for(FsscVoucherDetail detail : fsscVoucherMain.getFdDetail()){
            detailMap = new HashMap<String, Object>();
            detailMap.put("fdBaseAccountsComCode", detail.getFdBaseAccounts().getFdCode());//科目编码
            detailMap.put("fdBaseAccountsComName", detail.getFdBaseAccounts().getFdName());//科目名称
            detailMap.put("fdVoucherText", detail.getFdVoucherText());//凭证摘要
            detailMap.put("fdType", detail.getFdType());//借贷 1借 2贷
            detailMap.put("fdMoney", detail.getFdMoney()+"");//金额
            detailMap.put("fdAccountProperty", detail.getFdBaseAccounts().getFdCostItem()==null?"":detail.getFdBaseAccounts().getFdCostItem());//核算属性
            detailMap.put("eopBasedataCostCenter", detail.getFdBaseCostCenter());//成本中心
            detailMap.put("eopBasedataProject", detail.getFdBaseProject());//核算项目
            detailMap.put("eopBasedataCustomer", detail.getFdBaseCustomer());//客户
            detailMap.put("eopBasedataCashFlow", detail.getFdBaseCashFlow());//现金流量项目
            detailMap.put("eopBasedataErpPerson", detail.getFdBaseErpPerson());//个人
            detailMap.put("eopBasedataPayBank", detail.getFdBasePayBank());//银行
            detailMap.put("eopBasedataSupplier", detail.getFdBaseSupplier());//供应商
            detailMap.put("fdDept", detail.getFdDept());//部门
            detailMap.put("fdContractCode", detail.getFdContractCode());//合同编号
            String fdContractName = "";
            if(StringUtil.isNotNull(detail.getFdContractCode())){
            	if(FsscCommonUtil.checkHasModule("/fssc/ledger/")){
            		fdContractName = getFsscLedgerContractCommonService().getFdContracNameByCode(detail.getFdContractCode());
            	}
            }
            detailMap.put("fdContractName", fdContractName);//合同名称
            detailMapList.add(detailMap);
        }
        map.put("detailMapList", detailMapList);
        map.put("fdCompanyId", fsscVoucherMain.getFdCompany().getFdId());
        List returnlist = getFsscCommonK3CloudService().synVoucher(map);
        if (returnlist.size() > 0) {
            String succeed = ((Map<String, String>) returnlist.get(0)).get("succeed");
            if ("0".equals(succeed)) {//成功
                String docFinanceNumber = ((Map<String, String>) returnlist.get(0)).get("u8voucher_id");
                String dsc = ((Map<String, String>) returnlist.get(0)).get("dsc");
                fsscVoucherMain.setDocFinanceNumber(docFinanceNumber);//财务凭证号
                fsscVoucherMain.setFdBookkeepingMessage(dsc);
                fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30);//记账状态
                fsscVoucherMain.setFdBookkeepingDate(Calendar.getInstance().getTime());//记账日期
                fsscVoucherMain.setFdBookkeepingPerson(UserUtil.getUser());//记账人
                //如果制单人是匿名用户，就修改为当前登录人
                if(fsscVoucherMain.getDocCreator() == UserUtil.getAnonymousUser().getPerson()){
                    fsscVoucherMain.setDocCreator(UserUtil.getUser());
                }
                this.update(fsscVoucherMain);
                logger.info(fsscVoucherMain.getDocNumber()+"凭证写入k3Cloud成功:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                System.out.println(fsscVoucherMain.getDocNumber()+"凭证写入k3Cloud成功:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                return true;
            } else {
                String dsc = ((Map<String, String>)returnlist.get(0)).get("dsc");
                
                //记账失败，请检查制证是否符合K3规范！
                throw new Exception(ResourceUtil.getString("bookkeeping.voucher.k3cloud.standard.error", "fssc-voucher")
                        .replace("%text%", dsc));
            }
        } else {
            //凭证写入K3Cloud失败,请检查制证信息配置！
            throw new Exception(ResourceUtil.getString("bookkeeping.voucher.k3cloud.write.error", "fssc-voucher"));
        }
    }
    


}
