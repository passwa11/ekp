package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataMateUnit;
import com.landray.kmss.eop.basedata.service.IEopBasedataMateUnitService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.ftsearch.apache.commons.collections4.CollectionUtils;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangwh
 * @description:物料单位业务类实现类
 * @date 2021/5/7
 */
public class EopBasedataMateUnitServiceImp extends ExtendDataServiceImp implements IEopBasedataMateUnitService, IXMLDataBean {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataMateUnit) {
            EopBasedataMateUnit eopBasedataMateUnit = (EopBasedataMateUnit) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataMateUnit eopBasedataMateUnit = new EopBasedataMateUnit();
        eopBasedataMateUnit.setDocCreateTime(new Date());
        eopBasedataMateUnit.setFdStatus(Integer.valueOf("0"));
        eopBasedataMateUnit.setDocCreator(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataMateUnit, requestContext);
        return eopBasedataMateUnit;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataMateUnit eopBasedataMateUnit = (EopBasedataMateUnit) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public List getDataList(RequestContext requestInfo) throws Exception {
        // 校验项目名称唯一性
        String fdName = requestInfo.getRequest().getParameter("fdName");
        String fdCode = requestInfo.getRequest().getParameter("fdCode");
        String fdId = requestInfo.getRequest().getParameter("fdId");
        List rtnList = new ArrayList();
        boolean isExist = checkCodeOrNameExist(fdCode,fdName,fdId);
        rtnList.add(isExist);
        return rtnList;
    }

    private boolean checkCodeOrNameExist(String fdCode, String fdName, String fdId) throws Exception {
        //验证code与name是否存在
        boolean flag = false;

        HQLInfo hqlInfo = new HQLInfo();
        String whereString = "(eopBasedataMateUnit.fdName=:fdName";
        hqlInfo.setParameter("fdName", fdName);
        if (StringUtil.isNotNull(fdCode)) {
            whereString += " or eopBasedataMateUnit.fdCode = :fdCode)";
            hqlInfo.setParameter("fdCode", fdCode);
        } else {
            whereString += ")";
        }
        if (StringUtil.isNotNull(fdId)) {
            whereString += " and eopBasedataMateUnit.fdId != :fdId";
            hqlInfo.setParameter("fdId", fdId);
        }
        hqlInfo.setWhereBlock(whereString);
        List<EopBasedataMateUnit> results = findList(hqlInfo);
        if(CollectionUtils.isNotEmpty(results)) {
            flag = true;
        }
        return flag;
    }

    @Override
    public List<EopBasedataMateUnit> findByName(String fdName) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataMateUnit.fdName=:fdName");
        hqlInfo.setParameter("fdName", fdName);
        return this.findList(hqlInfo);
    }
}
