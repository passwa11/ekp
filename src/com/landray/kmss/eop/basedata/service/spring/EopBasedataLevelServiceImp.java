package com.landray.kmss.eop.basedata.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataLevelForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataLevel;
import com.landray.kmss.eop.basedata.service.IEopBasedataLevelService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.hibernate.spi.HibernateWrapper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataLevelServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataLevelService {
	
	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

    public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}
	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataLevel) {
            EopBasedataLevel eopBasedataLevel = (EopBasedataLevel) model;
            eopBasedataLevel.setDocAlterTime(new Date());
            eopBasedataLevel.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }
    @Override
	public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
    	EopBasedataLevelForm mainForm = (EopBasedataLevelForm) super.initFormSetting(form, requestContext);
		return mainForm;
	}
    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataLevel eopBasedataLevel = new EopBasedataLevel();
        eopBasedataLevel.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataLevel.setDocCreateTime(new Date());
        eopBasedataLevel.setDocAlterTime(new Date());
        eopBasedataLevel.setDocCreator(UserUtil.getUser());
        eopBasedataLevel.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataLevel, requestContext);
        return eopBasedataLevel;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataLevel eopBasedataLevel = (EopBasedataLevel) model;
    }

    @Override
    public List<EopBasedataLevel> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataLevel.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }
	@Override
	public void updateSynLevel() throws Exception {
		List<EopBasedataLevel> list = findList("eopBasedataLevel.fdIsAvailable=1","");
		List<SysOrganizationStaffingLevel> orgList = sysOrganizationStaffingLevelService.findList(new HQLInfo());
		Map<String,EopBasedataLevel> map =  new HashMap<String,EopBasedataLevel>();
		List<EopBasedataLevel> updateList = new ArrayList<EopBasedataLevel>();
		for(EopBasedataLevel level:list) {
			if(level.getFdPersonList()==null) {
				List<SysOrgPerson> persons  =  new ArrayList<SysOrgPerson>();
				level.setFdPersonList(persons);
			}else {
				level.getFdPersonList().clear();
			}
			updateList.add(level);
			map.put(level.getFdName()+level.getFdCode(), level);
		}
		for(SysOrganizationStaffingLevel orgLevel:orgList) {
			String key = orgLevel.getFdName()+orgLevel.getFdLevel();
			if(map.containsKey(key)) {
				EopBasedataLevel level = map.get(key);
				ArrayUtil.concatTwoList(orgLevel.getFdPersons(), level.getFdPersonList());
			}
		}
		if(!ArrayUtil.isEmpty(updateList)) {
			getBaseDao().saveOrUpdateAll(updateList);
		}
	}
}
