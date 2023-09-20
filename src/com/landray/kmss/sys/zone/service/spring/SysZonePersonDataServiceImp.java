package com.landray.kmss.sys.zone.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.zone.forms.SysZonePersonDataForm;
import com.landray.kmss.sys.zone.model.SysZonePersonDataCate;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.sys.zone.service.ISysZonePersonDataService;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.util.StringUtil;

/**
 * 个人资料业务接口实现
 * 
 * @author XuJieYang
 * @version 1.0 2014-08-28
 */
public class SysZonePersonDataServiceImp extends BaseServiceImp implements
		ISysZonePersonDataService {

	protected ISysZonePersonInfoService sysZonePersonInfoService;

	public void setSysZonePersonInfoService(
			ISysZonePersonInfoService sysZonePersonInfoService) {
		this.sysZonePersonInfoService = sysZonePersonInfoService;
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext)
		throws Exception {
		SysZonePersonDataForm dataForm = (SysZonePersonDataForm) form;
		String personId = dataForm.getFdPersonId();
		String dataCateId = dataForm.getFdDataCateId();
		
		// 防止：页面提交的：fdPersonId、fdDataCateId的值（含有如 1#########），造成sql注入攻击
		if (StringUtil.isNotNull(dataCateId)) {
			SysZonePersonDataCate dataCate = (SysZonePersonDataCate) this.findByPrimaryKey(
					dataCateId, SysZonePersonDataCate.class, true);
			if (dataCate == null) {
				dataCateId = null;
			}
		}
		if (StringUtil.isNotNull(personId)) {
			SysOrgPerson person = (SysOrgPerson) this.findByPrimaryKey(personId,
					SysOrgPerson.class, true);
			if (person == null) {
				personId = null;
			}
		}

		if (StringUtil.isNotNull(personId) && StringUtil.isNotNull(dataCateId)) {
			SysZonePersonInfo person = sysZonePersonInfoService.updateGetPerson(personId);
			super.update(form, requestContext);
			person.setFdLastModifiedTime(new Date());
			sysZonePersonInfoService.update(person);
		}
	}
}
