package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataGood;
import com.landray.kmss.eop.basedata.service.IEopBasedataGoodService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class EopBasedataGoodServiceImp extends ExtendDataServiceImp implements IEopBasedataGoodService, IXMLDataBean {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataGood) {
            EopBasedataGood eopBasedataGood = (EopBasedataGood) model;
            eopBasedataGood.setDocAlterTime(new Date());
            eopBasedataGood.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataGood eopBasedataGood = new EopBasedataGood();
        eopBasedataGood.setDocCreateTime(new Date());
        eopBasedataGood.setDocAlterTime(new Date());
        eopBasedataGood.setDocCreator(UserUtil.getUser());
        eopBasedataGood.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataGood, requestContext);
        return eopBasedataGood;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataGood eopBasedataGood = (EopBasedataGood) model;
    }

    @Override
    public List<EopBasedataGood> findByFdParent(EopBasedataGood fdParent) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataGood.fdParent.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdParent.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public List<Map<String, String>> getDataList(RequestContext requestInfo) throws Exception {
        String parentId = requestInfo.getParameter("parentId");
        String fdId = requestInfo.getParameter("fdId");
        HQLInfo hqlInfo = new HQLInfo();
        if (StringUtil.isNull(parentId)) {
            hqlInfo.setWhereBlock("eopBasedataGood.hbmParent is null");
        } else {
            hqlInfo.setWhereBlock("eopBasedataGood.hbmParent.fdId=:parentId");
            hqlInfo.setParameter("parentId", parentId);
        }
        List<EopBasedataGood> result = findList(hqlInfo);
        List rtnList = new java.util.ArrayList();
        for (int i = 0; i < result.size(); i++) {
            EopBasedataGood eopBasedataGood = result.get(i);
            Map node = new java.util.HashMap();
            node.put("isShowCheckBox", true);
            if (StringUtil.isNotNull(fdId)) {
                if (fdId.equals(eopBasedataGood.getFdId())) {
                    node.put("isShowCheckBox", false);
                }
            }
            node.put("text", eopBasedataGood.getFdName());
            node.put("value", eopBasedataGood.getFdId());
            rtnList.add(node);
        }
        return rtnList;
    }
}
