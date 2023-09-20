package com.landray.kmss.sys.oms.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.oms.service.ISysOmsTempDeptService;
import com.landray.kmss.sys.oms.temp.SysOmsTempConstants;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.sys.oms.model.SysOmsTempDept;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.util.SpringBeanUtil;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;

public class SysOmsTempDeptServiceImp extends ExtendDataServiceImp implements ISysOmsTempDeptService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
	public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysOmsTempDept) {
            SysOmsTempDept sysOmsTempDept = (SysOmsTempDept) model;
        }
        return model;
    }

    @Override
	public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysOmsTempDept sysOmsTempDept = new SysOmsTempDept();
        sysOmsTempDept.setFdIsAvailable(Boolean.valueOf("true"));
        SysOmsUtil.initModelFromRequest(sysOmsTempDept, requestContext);
        return sysOmsTempDept;
    }

    @Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysOmsTempDept sysOmsTempDept = (SysOmsTempDept) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public List<SysOmsTempDept> findListByTrxId(String fdTrxId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdTrxId=:fdTrxId");
		hqlInfo.setParameter("fdTrxId",fdTrxId);
		return findList(hqlInfo);
	}

	@Override
	public SysOmsTempDept finDept(SysOmsTempDp dp) throws Exception {
		SysOmsTempDept dept = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOmsTempDept.fdDeptId=:fdDeptId and sysOmsTempDept.fdTrxId=:fdTrxId");
		hqlInfo.setParameter("fdTrxId", dp.getFdTrxId());
		hqlInfo.setParameter("fdDeptId", dp.getFdDeptId());
		List<SysOmsTempDept> depts = findList(hqlInfo);
		if(depts != null && !depts.isEmpty()) {
            dept = depts.get(0);
        }
		return dept;
	}

	@Override
	public List<SysOmsTempDept> findFailListByTrxId(String fdTrxId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdTrxId=:fdTrxId and fdStatus=:fdStatus");
		hqlInfo.setParameter("fdTrxId",fdTrxId);
		hqlInfo.setParameter("fdStatus", SysOmsTempConstants.SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL);
		return findList(hqlInfo);
	}

	@Override
	public SysOmsTempDept findByDeptId(String fdDeptId, String fdTrxId) throws Exception {
		SysOmsTempDept dept = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOmsTempDept.fdDeptId=:fdDeptId and sysOmsTempDept.fdTrxId=:fdTrxId");
		hqlInfo.setParameter("fdTrxId", fdTrxId);
		hqlInfo.setParameter("fdDeptId",fdDeptId);
		List<SysOmsTempDept> depts = findList(hqlInfo);
		if(depts != null && !depts.isEmpty()) {
            dept = depts.get(0);
        }
		return dept;
	}
}
