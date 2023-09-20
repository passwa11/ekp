package com.landray.kmss.sys.portal.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.portal.forms.SysPortalMapTplForm;
import com.landray.kmss.sys.portal.forms.SysPortalMapTplNavForm;
import com.landray.kmss.sys.portal.model.SysPortalMapTpl;
import com.landray.kmss.sys.portal.model.SysPortalMapTplNav;
import com.landray.kmss.sys.portal.model.SysPortalMapTplNavCustom;
import com.landray.kmss.sys.portal.model.SysPortalNav;
import com.landray.kmss.sys.portal.service.ISysPortalMapTplService;
import com.landray.kmss.sys.portal.util.SysPortalMapUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

public class SysPortalMapTplServiceImp extends ExtendDataServiceImp implements ISysPortalMapTplService {
	
    @Override
    @SuppressWarnings("unchecked")
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
    	
		SysPortalMapTpl tpl = (SysPortalMapTpl) super.convertFormToModel(form,
				model, requestContext);
		// 封装关系
		SysPortalMapTplForm tplForm = (SysPortalMapTplForm) form;
		List<SysPortalMapTplNavForm> fdNavForms = tplForm.getFdPortalNavForms();

		if (!fdNavForms.isEmpty()) {

			List<SysPortalMapTplNav> fdPortalNav = new ArrayList<>();

			for (SysPortalMapTplNavForm fdNavForm : fdNavForms) {

				SysPortalMapTplNav tplNav = new SysPortalMapTplNav();
				SysPortalNav fdNav = new SysPortalNav();
				fdNav.setFdId(fdNavForm.getFdNavId());

				tplNav.setFdMain(tpl);
				tplNav.setFdAttachmentId(fdNavForm.getFdAttachmentId());
				tplNav.setFdNav(fdNav);

				fdPortalNav.add(tplNav);

			}
			tpl.setFdPortalNav(fdPortalNav);
		}

		return tpl;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
		
        return super.convertBizFormToModel(form, model, context);
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysPortalMapTpl sysPortalMapTpl = new SysPortalMapTpl();
        sysPortalMapTpl.setDocCreateTime(new Date());
        sysPortalMapTpl.setDocCreator(UserUtil.getUser());
		SysPortalMapUtil.initModelFromRequest(sysPortalMapTpl, requestContext);
        return sysPortalMapTpl;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
    }

    @Override
    @SuppressWarnings("unchecked")
	public List<SysPortalMapTpl> findByFdNav(SysPortalMapTpl fdNav) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysPortalMapTpl.fdNav.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdNav.getFdId());
        return this.findList(hqlInfo);
    }
    
	@Override
    @SuppressWarnings("unchecked")
	public JSONArray getCustomDatasByTpl(SysPortalMapTpl tpl) throws Exception {

		JSONArray array = new JSONArray();
		List<SysPortalMapTplNavCustom> list = tpl.getFdNavCustom();
		
		for (int i = 0; i < tpl.getFdNavCustom().size(); i++) {
			SysPortalMapTplNavCustom custom = list.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("content", custom.getFdContent());
			jsonObject.accumulate("name", custom.getFdName());
			jsonObject.accumulate("type", tpl.getTplType());
			// 背景附件key
			String key = custom.getFdAttachmentId();
			if (StringUtil.isNotNull(key)) {
				List<SysAttMain> atts = sysAttMainCoreInnerService
						.findByModelKey(SysPortalMapTpl.class.getName(),
								tpl.getFdId(), key);
				if (atts.size() > 0) {
					jsonObject.accumulate("attId", atts.get(0).getFdId());
				}
			}
			array.add(jsonObject);
		}

		return array;

	}
	
	
	@Override
    public JSONArray getDatasByTpl(SysPortalMapTpl tpl) throws Exception {

		List<SysPortalMapTplNav> list = tpl.getFdPortalNav();

		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			SysPortalMapTplNav tplNav = (SysPortalMapTplNav) list.get(i);
			JSONObject jsonObject = new JSONObject();
			SysPortalNav nav = tplNav.getFdNav();
			
			KMSSUser kmssUser = UserUtil.getKMSSUser();
			String langKey = kmssUser.getLocale().getLanguage() + "-" + kmssUser.getLocale().getCountry();
			
			net.sf.json.JSONArray arr = net.sf.json.JSONArray.fromObject(nav.getFdContent());
			
			arr = getLangJson(arr,langKey);
			
			jsonObject.accumulate("content", arr);
			
			jsonObject.accumulate("name", nav.getFdName());
			jsonObject.accumulate("type", tpl.getTplType());

			// 背景附件key
			String key = tplNav.getFdAttachmentId();
			if (StringUtil.isNotNull(key)) {
				@SuppressWarnings("unchecked")
				List<SysAttMain> atts = sysAttMainCoreInnerService
						.findByModelKey(SysPortalMapTpl.class.getName(),
								tpl.getFdId(), key);
				if (atts.size() > 0) {
					jsonObject.accumulate("attId", atts.get(0).getFdId());
				}
			}
			array.add(jsonObject);
		}

		return array;

	}
    
    protected ISysAttMainCoreInnerService sysAttMainCoreInnerService = null;

	public void setSysAttMainCoreInnerService(
			ISysAttMainCoreInnerService sysAttMainCoreInnerService) {
		this.sysAttMainCoreInnerService = sysAttMainCoreInnerService;
	}
	
	
	//递归获得当前语言的系统导航text
	private net.sf.json.JSONArray getLangJson(net.sf.json.JSONArray array , String key){
		
		for(int i=0;i<array.size();i++){
			JSONObject obj = (JSONObject)array.get(i);
			if(obj.containsKey(key)){
				if(StringUtil.isNotNull(obj.getString(key))){
					obj.put("text", obj.getString(key));
				}
			}
			
			if(obj.containsKey("children")){
				net.sf.json.JSONArray childArray = obj.getJSONArray("children");
				getLangJson(childArray,key);
			}
		}
		
		return array;
	}
    
}
