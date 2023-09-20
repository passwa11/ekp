package com.landray.kmss.third.ding.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.response.OapiProcessSaveResponse;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingDtemplate;
import com.landray.kmss.third.ding.model.ThirdDingTemplateDetail;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.IThirdDingDinstanceService;
import com.landray.kmss.third.ding.service.IThirdDingDtaskService;
import com.landray.kmss.third.ding.service.IThirdDingDtemplateService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class ThirdDingDtemplateServiceImp extends ExtendDataServiceImp implements IThirdDingDtemplateService {

	protected final Logger logger = LoggerFactory.getLogger(ThirdDingDtemplateServiceImp.class);

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof ThirdDingDtemplate) {
			ThirdDingDtemplate thirdDingDtemplate = (ThirdDingDtemplate) model;
		}
		return model;
	}

	private IThirdDingDtaskService thirdDingDtaskService = null;

	public IThirdDingDtaskService getThirdDingDtaskService() {
		if (thirdDingDtaskService == null) {
			thirdDingDtaskService = (IThirdDingDtaskService) SpringBeanUtil.getBean("thirdDingDtaskService");
		}
		return thirdDingDtaskService;
	}
	
	private IThirdDingDinstanceService thirdDingDinstanceService = null;

	public IThirdDingDinstanceService getThirdDingDinstanceService() {
		if (thirdDingDinstanceService == null) {
			thirdDingDinstanceService = (IThirdDingDinstanceService) SpringBeanUtil
					.getBean("thirdDingDinstanceService");
		}
		return thirdDingDinstanceService;
	}
	
	
	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		ThirdDingDtemplate thirdDingDtemplate = new ThirdDingDtemplate();
		thirdDingDtemplate.setFdType(Boolean.valueOf("false"));
		thirdDingDtemplate.setFdFlow(Boolean.valueOf("true"));
		thirdDingDtemplate.setFdDisableFormEdit(Boolean.valueOf("true"));
		ThirdDingUtil.initModelFromRequest(thirdDingDtemplate, requestContext);
		return thirdDingDtemplate;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		ThirdDingDtemplate thirdDingDtemplate = (ThirdDingDtemplate) model;
	}

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	@Override
	public ThirdDingDtemplate updateCommonTemplate(String fdLang)
			throws Exception {
		updateCopid(fdLang);
		ThirdDingDtemplate template = null;
		List<ThirdDingDtemplate> list = null;
		if (StringUtil.isNotNull(fdLang)) {
			list = findList(
					"fdType=1 and fdLang='" + fdLang + "'",
					"docCreateTime desc");

		} else {
			list = findList(
					"fdType=1", "docCreateTime desc");
		}
		DingConfig config = DingConfig.newInstance();
		for(ThirdDingDtemplate temp:list){
			if(config.getDingCorpid().equals(temp.getFdCorpId())&&temp.getFdIsAvailable()){
				if (StringUtil.isNull(fdLang)) {
					if (StringUtil.isNull(temp.getFdLang())) {
						template = temp;
						break;
					}
				} else {
					template = temp;
					break;
				}
			}
		}
		if (template == null) {
			for(ThirdDingDtemplate temp:list){
				if(config.getDingCorpid().equals(temp.getFdCorpId())&&!temp.getFdIsAvailable()){
					if (StringUtil.isNull(fdLang)) {
						if (StringUtil.isNull(temp.getFdLang())) {
							template = temp;
							break;
						}
					} else {
						template = temp;
						break;
					}
				}
			}
		}
		if (template != null) {
			for (ThirdDingDtemplate temp : list) {
				if (!temp.getFdId().equals(template.getFdId()) && temp.getFdIsAvailable()) {
					temp.setFdIsAvailable(false);
					super.update(temp);
				}
			}
		}
		return template;
	}

	private String name = "通用待办模板";
	private String desc = "通用待办";
	
	@Override
	public String addCommonTemplate(ThirdDingDtemplate temp, String fdLang)
			throws Exception {
		String code = null;
		DingConfig config = DingConfig.newInstance();
		if (StringUtil.isNotNull(config.getDingAgentid())) {
			String token = DingUtils.getDingApiService().getAccessToken();
			if (temp != null && StringUtil.isNotNull(temp.getFdProcessCode())) {
				code = temp.getFdProcessCode();
				name = temp.getFdName();
			} else {
				// 待办模块新建
				name = "通用待办模板" + System.currentTimeMillis();
			}
			OapiProcessSaveResponse response = DingNotifyUtil.createTemplate(token,
					Long.parseLong(config.getDingAgentid()), name, desc, true,
					code, fdLang);
			if (response.getErrcode() == 0) {
				if(temp==null){
					code = response.getResult().getProcessCode();
					ThirdDingDtemplate template = new ThirdDingDtemplate();
					template.setFdAgentId(Long.parseLong(config.getDingAgentid()));
					template.setFdName(name);
					template.setFdDesc(desc);
					template.setFdFlow(true);
					template.setFdDisableFormEdit(true);
					template.setFdProcessCode(code);
					template.setFdCorpId(config.getDingCorpid());
					template.setFdType(true);
					template.setFdIsAvailable(true);
					template.setFdLang(fdLang);
					List<ThirdDingTemplateDetail> details = new ArrayList<ThirdDingTemplateDetail>();
					ThirdDingTemplateDetail detail = new ThirdDingTemplateDetail();
					detail.setFdName("标题");
					detail.setFdType("TextField");
					details.add(detail);
					detail = new ThirdDingTemplateDetail();
					detail.setFdName("创建者");
					detail.setFdType("TextField");
					details.add(detail);
					detail = new ThirdDingTemplateDetail();
					detail.setFdName("创建时间");
					detail.setFdType("TextField");
					details.add(detail);
					template.setFdDetail(details);
					template.setDocCreateTime(new Date());
					super.add(template);
				}else{
					temp.setFdAgentId(Long.parseLong(config.getDingAgentid()));
					if(StringUtil.isNull(temp.getFdCorpId())) {
                        temp.setFdCorpId(config.getDingCorpid());
                    }
					temp.setFdDesc(desc);
					temp.setFdIsAvailable(true);
					if (temp.getDocCreateTime() == null) {
                        temp.setDocCreateTime(new Date());
                    }
					super.update(temp);
				}
			} else {
				logger.error("创建待办模板失败，详细错误：" + response.getBody()+",name="+name);
			}
		} else {
			logger.debug("钉钉集成的应用Id为空，无法创建待办模板，导致无法发送待办");
		}
		return code;
	}
	
	public void updateCopid(String fdLang) throws Exception {
		DingConfig config = DingConfig.newInstance();
		List<ThirdDingDtemplate> list = null;
		if (StringUtil.isNotNull(fdLang)) {
			list = findList(
					"fdType=1 and fdLang='" + fdLang + "'",
					"docCreateTime desc");
		} else {
			list = findList(
					"fdType=1", "docCreateTime desc");
		}
		for(ThirdDingDtemplate temp:list){
			if(StringUtil.isNull(temp.getFdCorpId())&&StringUtil.isNotNull(config.getDingAgentid())){
				String token = DingUtils.getDingApiService().getAccessToken();
				OapiProcessSaveResponse response = DingNotifyUtil.createTemplate(token,
								Long.parseLong(config.getDingAgentid()),
								temp.getFdName(), desc, true,
								temp.getFdProcessCode(), fdLang);
				if (response.getErrcode() == 0) {
					temp.setFdCorpId(config.getDingCorpid());
					if (temp.getDocCreateTime() == null) {
                        temp.setDocCreateTime(new Date());
                    }
					super.update(temp);
					logger.debug("更新待办模板的corpid=" + config.getDingCorpid() + "成功,name=" + name);
				}else if(response.getErrcode()==830001){
					logger.error("无法更新待办模板的corpid(此模板不是当前钉钉组织【corpid=" + config.getDingCorpid() + "】创建的)，详细错误："
							+ response.getBody() + ",name=" + name);
				}else{
					logger.error("无法更新待办模板的corpid，详细错误：" + response.getBody()+",name="+name);
				}
			}
		}
	}
}
