package com.landray.kmss.sys.oms.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;
import com.landray.kmss.sys.oms.service.ISysOmsTempPersonService;
import com.landray.kmss.sys.oms.temp.SysOmsTempConstants;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOmsTempPersonServiceImp extends ExtendDataServiceImp implements ISysOmsTempPersonService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
	public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysOmsTempPerson) {
            SysOmsTempPerson sysOmsTempPerson = (SysOmsTempPerson) model;
        }
        return model;
    }

    @Override
	public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysOmsTempPerson sysOmsTempPerson = new SysOmsTempPerson();
        sysOmsTempPerson.setFdIsAvailable(Boolean.valueOf("true"));
        SysOmsUtil.initModelFromRequest(sysOmsTempPerson, requestContext);
        return sysOmsTempPerson;
    }

    @Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysOmsTempPerson sysOmsTempPerson = (SysOmsTempPerson) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public List<SysOmsTempPerson> findListByTrxId(String fdTrxId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdTrxId=:fdTrxId");
		hqlInfo.setParameter("fdTrxId",fdTrxId);
		return findList(hqlInfo);
	}

	@Override
	public SysOmsTempPerson findByDp(SysOmsTempDp dp) throws Exception {
		SysOmsTempPerson person = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOmsTempPerson.fdPersonId=:fdPersonId and sysOmsTempPerson.fdTrxId=:fdTrxId");
		hqlInfo.setParameter("fdTrxId", dp.getFdTrxId());
		hqlInfo.setParameter("fdPersonId", dp.getFdPersonId());
		List<SysOmsTempPerson> persons = findList(hqlInfo);
		if(persons != null && !persons.isEmpty()) {
            person = persons.get(0);
        }
		return person;
	}

	@Override
	public SysOmsTempPerson findByPp(SysOmsTempPp pp) throws Exception {
		SysOmsTempPerson person = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOmsTempPerson.fdPersonId=:fdPersonId and sysOmsTempPerson.fdTrxId=:fdTrxId");
		hqlInfo.setParameter("fdTrxId", pp.getFdTrxId());
		hqlInfo.setParameter("fdPersonId", pp.getFdPersonId());
		List<SysOmsTempPerson> persons = findList(hqlInfo);
		if(persons != null && !persons.isEmpty()) {
            person = persons.get(0);
        }
		return person;
	}

	@Override
	public List<SysOmsTempPerson> findFailListByTrxId(String fdTrxId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdTrxId=:fdTrxId and fdStatus=:fdStatus");
		hqlInfo.setParameter("fdTrxId",fdTrxId);
		hqlInfo.setParameter("fdStatus", SysOmsTempConstants.SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL);
		return findList(hqlInfo);
	}

	@Override
	public SysOmsTempPerson findByPersonId(String fdPersonId, String fdTrxId) throws Exception {
		SysOmsTempPerson person = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOmsTempPerson.fdPersonId=:fdPersonId and sysOmsTempPerson.fdTrxId=:fdTrxId");
		hqlInfo.setParameter("fdTrxId",fdTrxId);
		hqlInfo.setParameter("fdPersonId", fdPersonId);
		List<SysOmsTempPerson> persons = findList(hqlInfo);
		if(persons != null && !persons.isEmpty()) {
            person = persons.get(0);
        }
		return person;
	}
}
