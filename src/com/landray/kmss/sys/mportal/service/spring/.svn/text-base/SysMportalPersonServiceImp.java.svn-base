package com.landray.kmss.sys.mportal.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.mportal.MobilePortalCache;
import com.landray.kmss.sys.mportal.forms.SysMportalPersonDetailForm;
import com.landray.kmss.sys.mportal.forms.SysMportalPersonForm;
import com.landray.kmss.sys.mportal.model.SysMportalPerson;
import com.landray.kmss.sys.mportal.service.ISysMportalPersonService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysMportalPersonServiceImp extends BaseServiceImp implements
		ISysMportalPersonService {

	private MobilePortalCache mobilePortalCache;

	public void setMobilePortalCache(MobilePortalCache mobilePortalCache) {
		this.mobilePortalCache = mobilePortalCache;
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		SysMportalPersonForm personForm = (SysMportalPersonForm) form;
		if (StringUtil.isNull(personForm.getFdPersonId())) {
			String userId = UserUtil.getUser().getFdId();
			personForm.setFdPersonId(userId);
			SysMportalPerson personal = this.getByPersonId(userId);
			if(personal != null) {
				personForm.setFdId(personal.getFdId());
			}
		}
		super.update(personForm, requestContext);
		mobilePortalCache.put(UserUtil.getUser().getFdId(), personForm, true);
	}
	
	public SysMportalPerson getByPersonId(String id) throws Exception{
		SysMportalPerson model = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysMportalPerson.fdPerson.fdId=:pesonId");
		hqlInfo.setParameter("pesonId", id);
		Object obj = this.findFirstOne(hqlInfo);
		return (SysMportalPerson)obj;
	}
	
	@Override
	public SysMportalPersonForm findByPersonId(String id,
			RequestContext request) throws Exception {
		SysMportalPersonForm personForm = mobilePortalCache.get(id);
		if (personForm == null) {
			String personId = UserUtil.getUser().getFdId();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("sysMportalPerson.fdPerson.fdId=:pesonId");
			hqlInfo.setParameter("pesonId", personId);
			Object obj = this.findFirstOne(hqlInfo);
			if (obj!=null) {
				SysMportalPerson model = (SysMportalPerson)obj;
				personForm = (SysMportalPersonForm) this
						.convertModelToForm(new SysMportalPersonForm(),
								model, request);
				mobilePortalCache.put(personId, personForm);
			}
		}
		return personForm;
	}

	@Override
	public JSONObject findById(String id, RequestContext request)
			throws Exception {
		SysMportalPersonForm person = findByPersonId(id, request);
		if (person == null) {
            return new JSONObject();
        }
		JSONObject json = new JSONObject();
	/*	json.put("uuids", JSONArray.fromObject(StringUtil.isNull(person
				.getUuids()) ? "" : person.getUuids().split(";")));*/
		JSONArray arr = new JSONArray();
		for(Object obj : person.getFdDetailForms()) {
			SysMportalPersonDetailForm detail = (SysMportalPersonDetailForm)obj;
			JSONObject item = new JSONObject();
			item.put("fdId", detail.getFdId());
			item.put("fdMportalPersonId", detail.getFdMportalPersonId());
			item.put("fdCardId", detail.getFdCardId());
			item.put("fdOrder", StringUtil.isNotNull(detail.getFdOrder())?
							Integer.valueOf(detail.getFdOrder()) : 0);
			arr.add(item);
		}
		json.put("details", arr);
		json.put("personId", person.getFdPersonId());
		json.put("fdId", person.getFdId());
		return json;
	}

}
