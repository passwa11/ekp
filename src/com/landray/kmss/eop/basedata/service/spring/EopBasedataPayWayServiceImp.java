package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataPayBank;
import com.landray.kmss.eop.basedata.model.EopBasedataPayWay;
import com.landray.kmss.eop.basedata.service.IEopBasedataPayWayService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.ftsearch.apache.commons.collections4.CollectionUtils;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import java.util.*;

/**
 * @author wangwh
 * @description:付款方式业务类实现类
 * @date 2021/5/7
 */
public class EopBasedataPayWayServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataPayWayService, IXMLDataBean {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataPayWay) {
            EopBasedataPayWay eopBasedataPayWay = (EopBasedataPayWay) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataPayWay eopBasedataPayWay = new EopBasedataPayWay();
        eopBasedataPayWay.setFdIsDefault(true);
        eopBasedataPayWay.setFdIsTransfer(true);
        eopBasedataPayWay.setDocCreateTime(new Date());
        eopBasedataPayWay.setFdStatus(Integer.valueOf("0"));
        eopBasedataPayWay.setDocCreator(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataPayWay, requestContext);
        return eopBasedataPayWay;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataPayWay eopBasedataPayWay = (EopBasedataPayWay) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public List getDataList(RequestContext requestInfo) throws Exception {
        String type = requestInfo.getParameter("type");
        if("default".equals(type)) {
        	String fdCompanyId = requestInfo.getParameter("fdCompanyId");
            String fdPayWayId = requestInfo.getParameter("fdPayWayId");
            String model = requestInfo.getParameter("model");
        	HQLInfo hqlInfo = new HQLInfo();
            StringBuilder where = new StringBuilder(" eopBasedataPayWay.fdStatus = :fdIsAvailable ");
            if(!"cashier".equals(model)){	//出纳工作台筛选付款方式
	    		where.append(" and eopBasedataPayWay.fdIsDefault=:fdIsDefault");
	    		hqlInfo.setParameter("fdIsDefault",true);
            }
            hqlInfo.setParameter("fdIsAvailable", 0);
            StringBuilder join = new StringBuilder();
            join.append(" left join eopBasedataPayWay.fdDefaultPayBank bank");
            join.append(" left join eopBasedataPayWay.fdCompanyList comp");
            if(StringUtil.isNotNull(fdCompanyId)){
                where.append(" and (comp.fdId = :fdCompanyId or comp.fdId is null)");
                hqlInfo.setParameter("fdCompanyId", fdCompanyId);
            }else {
                where.append(" and (comp.fdId is null)");
            }
            hqlInfo.setJoinBlock(join.toString());
            if(StringUtil.isNotNull(fdPayWayId)){
                where.append(" and eopBasedataPayWay.fdId = :fdPayWayId");
                hqlInfo.setParameter("fdPayWayId", fdPayWayId);
            }
            hqlInfo.setWhereBlock(where.toString());
            List<EopBasedataPayWay> rtn = findList(hqlInfo);
            List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
            if(rtn.size()>0) {
            	rtn = EopBasedataFsscUtil.sortByCompany(rtn);
            	for(EopBasedataPayWay payWay:rtn){
	            	Map<String,Object> map = new HashMap<String,Object>();
	            	map.put("value", payWay.getFdId());
	            	map.put("text", payWay.getFdName());
	            	map.put("fdIsTransfer", payWay.getFdIsTransfer());
	            	EopBasedataPayBank bank = payWay.getFdDefaultPayBank();
	            	map.put("fdBankId", bank==null?"":bank.getFdId());
	            	map.put("fdBankName", bank==null?"":bank.getFdBankName());
	            	data.add(map);
            	}
            }
            return data;
        }
        if("isTransfer".equals(type)) {
        	String ids = requestInfo.getParameter("ids");
        	HQLInfo hqlInfo = new HQLInfo();
        	hqlInfo.setSelectBlock(" new Map(eopBasedataPayWay.fdId as value,eopBasedataPayWay.fdIsTransfer as fdIsTransfer) ");
            hqlInfo.setParameter("ids", Arrays.asList(ids.split(";")));
            hqlInfo.setWhereBlock(" eopBasedataPayWay.fdId in(:ids) ");
            return findList(hqlInfo);
        }
        // 校验项目名称唯一性
        String fdName = requestInfo.getRequest().getParameter("fdName");
        String fdCode = requestInfo.getRequest().getParameter("fdCode");
        List rtnList = new ArrayList();
        boolean isExist = checkCodeOrNameExist(fdCode,fdName);
        rtnList.add(isExist);
        return rtnList;
    }

    private boolean checkCodeOrNameExist(String fdCode, String fdName) throws Exception {
        //验证code与name是否存在
        boolean flag = false;

        HQLInfo hqlInfo = new HQLInfo();
        String whereString = "eopBasedataPayWay.fdName=:fdName";
        hqlInfo.setParameter("fdName", fdName);
        if (StringUtil.isNotNull(fdCode)) {
            whereString += " or eopBasedataPayWay.fdCode = :fdCode";
            hqlInfo.setParameter("fdCode", fdCode);
        }
        hqlInfo.setWhereBlock(whereString);
        List<EopBasedataPayWay> results = findList(hqlInfo);
        if(CollectionUtils.isNotEmpty(results)) {
            flag = true;
        }
        return flag;
    }
    
    @Override
	public EopBasedataPayWay getDefaultPayWay(EopBasedataCompany fdCompany) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock("left join eopBasedataPayWay.fdCompanyList comp");
		hqlInfo.setWhereBlock("eopBasedataPayWay.fdIsDefault=:fdIsDefault and (comp.fdId=:fdCompanyId or comp.fdId is null)  and eopBasedataPayWay.fdStatus=:fdIsAvailable");
		hqlInfo.setParameter("fdIsDefault",true);
		hqlInfo.setParameter("fdIsAvailable",0);
		if(null != fdCompany) {
			hqlInfo.setParameter("fdCompanyId",fdCompany.getFdId());
		}
		List<EopBasedataPayWay> list = findList(hqlInfo);
		return ArrayUtil.isEmpty(list)?null:list.get(0);
	}

	@Override
	public List<EopBasedataPayWay> getEopBasedataPayWayAvailable(String companyId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock("left join eopBasedataPayWay.fdCompanyList comp");
		hqlInfo.setWhereBlock("  (comp.fdId=:fdCompanyId or comp.fdId is null)  and eopBasedataPayWay.fdStatus=:fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable",0);
		hqlInfo.setParameter("fdCompanyId",companyId);
		return findList(hqlInfo);
	}
}
