package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.service.IEopBasedataCurrencyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPullAndPushService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPullAndPushService.DataAction;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.ftsearch.apache.commons.collections4.CollectionUtils;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import java.util.*;

/**
 * @author wangwh
 * @description:货币业务类实现类
 * @date 2021/5/7
 */
public class EopBasedataCurrencyServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataCurrencyService, IXMLDataBean {
	
    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataCurrency) {
            EopBasedataCurrency eopBasedataCurrency = (EopBasedataCurrency) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataCurrency eopBasedataCurrency = new EopBasedataCurrency();
        eopBasedataCurrency.setDocCreateTime(new Date());
        eopBasedataCurrency.setFdStatus(Integer.valueOf("0"));
        eopBasedataCurrency.setDocCreator(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataCurrency, requestContext);
        return eopBasedataCurrency;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataCurrency eopBasedataCurrency = (EopBasedataCurrency) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
    
    
	/**
     * 检验编码与名称唯一性
     * @param requestInfo
     *            数据请求信息
     * @return
     * @throws Exception
     */
    @Override
    public List getDataList(RequestContext requestInfo) throws Exception {
        List rtnList = new ArrayList();
        String type=requestInfo.getParameter("type");
		//根据选择的币种获取对应公司本位币的汇率以及预算币种的汇率
		if("localCurrencyInfo".equals(type)){
			String fdCompanyId = requestInfo.getParameter("fdCompanyId");
			EopBasedataCompany comp = (EopBasedataCompany) findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
			Map<String,String> map = new HashMap<String,String>();
			rtnList.add(map);
			EopBasedataCurrency currency=comp.getFdAccountCurrency();
			if(currency!=null){
				((Map<String, String>) rtnList.get(0)).put("code", currency.getFdSymbol());
			}
			return rtnList;
		}else {
			 String fdName = requestInfo.getRequest().getParameter("fdName");
		     String fdCode = requestInfo.getRequest().getParameter("fdCode");
			 boolean isExist = checkCodeOrNameExist(fdCode,fdName);
		     rtnList.add(isExist);
		}
        return rtnList;
    }

    private boolean checkCodeOrNameExist(String fdCode, String fdName) throws Exception {
        //验证code与name是否存在
        boolean flag = false;

        HQLInfo hqlInfo = new HQLInfo();
        String whereString = "eopBasedataCurrency.fdName=:fdName";
        hqlInfo.setParameter("fdName", fdName);
        if (StringUtil.isNotNull(fdCode)) {
            whereString += " or eopBasedataCurrency.fdCode = :fdCode";
            hqlInfo.setParameter("fdCode", fdCode);
        }
        hqlInfo.setWhereBlock(whereString);
        List<EopBasedataCurrency> results = findList(hqlInfo);
        if(CollectionUtils.isNotEmpty(results)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据中文名称查找货币
     * @param fdName
     * @return
     * @throws Exception
     */
    @Override
    public List<EopBasedataCurrency> findByName(String fdName) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataCurrency.fdName=:fdName");
        hqlInfo.setParameter("fdName", fdName);
        return this.findList(hqlInfo);
    }

	@Override
	public void saveEnable(String ids, String modelName) throws Exception {
		super.saveEnable(ids, modelName);
		
		this.manualData2BizMod(DataAction.enable, ids);
	}

	@Override
	public void saveDisable(String ids, String modelName) throws Exception {
		
		super.saveDisable(ids, modelName);
		
		this.manualData2BizMod(DataAction.disable, ids);
	}
	
	
	/**
	 * 手动同步状态到业务模块
	 * @param action
	 * @param ids
	 * @throws Exception
	 */
	private void manualData2BizMod(DataAction action, String ids) throws Exception {
		
		List<IEopBasedataPullAndPushService> ppServiceList = EopBasedataUtil.getPullAndPushService(EopBasedataCurrency.class.getName());
		if(CollectionUtils.isEmpty(ppServiceList)) {
			return;
		}
		
		String[] eopIds = ids.split(";");
		for(String eopId : eopIds) {
			EopBasedataCurrency eopCurrency = (EopBasedataCurrency) this.findByPrimaryKey(eopId);
			ppServiceList.get(0).asyncData2BizMod(action, eopCurrency);
		}
	}

    @Override
    public List<EopBasedataCurrency> findByAbbreviation(String fdAbbreviation) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataCurrency.fdAbbreviation=:fdAbbreviation");
        hqlInfo.setParameter("fdAbbreviation", fdAbbreviation);
        return this.findList(hqlInfo);
    }
}
