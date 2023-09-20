package com.landray.kmss.third.ding.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingApp;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IThirdDingAppService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ThirdDingAppServiceImp extends ExtendDataServiceImp implements IThirdDingAppService, ApplicationListener {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingAppServiceImp.class);

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	protected IPdaModuleConfigMainService pdaModuleConfigMainService;

	protected IBaseService getPdaModuleConfigMainServiceImp() {
		if (pdaModuleConfigMainService == null) {
            pdaModuleConfigMainService = (IPdaModuleConfigMainService) SpringBeanUtil.getBean("pdaModuleConfigMainService");
        }
		return pdaModuleConfigMainService;
	}


	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof ThirdDingApp) {
			ThirdDingApp thirdDingApp = (ThirdDingApp) model;
			thirdDingApp.setDocAlterTime(new Date());
			thirdDingApp.setDocAlteror(UserUtil.getUser());
		}
		return model;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		ThirdDingApp thirdDingApp = new ThirdDingApp();
		thirdDingApp.setDocCreateTime(new Date());
		thirdDingApp.setDocAlterTime(new Date());
		thirdDingApp.setDocCreator(UserUtil.getUser());
		thirdDingApp.setDocAlteror(UserUtil.getUser());
		ThirdDingUtil.initModelFromRequest(thirdDingApp, requestContext);
		return thirdDingApp;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		ThirdDingApp thirdDingApp = (ThirdDingApp) model;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		try {
			//EKP移动应用变更事件监听
			if ("mobileApp".equals(event.getSource().toString())) {
				Map param = ((Event_Common) event).getParams();
				if (null == param || param.size() <= 0) {
					return;
				}
				changeMobileApp(param);
			}
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		}
	}

	private void changeMobileApp(Map param) throws Exception {
		String fdId = param.get("fdId").toString();
		String method = param.get("mehtod").toString();
		if (StringUtil.isNull(fdId) || StringUtil.isNull(method)) {
			logger.error("fdId或者method不能为空！！！");
		}
		if ("appClose".equals(method) || "appStart".equals(method)) {
			String[] fdIds = (String[]) param.get("fdId");
			updateStat(method, fdIds);
			return;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(" thirdDingApp.fdEkpId = :fdEkpId");
		hqlInfo.setParameter("fdEkpId", fdId);
		ThirdDingApp dingApp = (ThirdDingApp) this.findFirstOne(hqlInfo);
		if ("add".equals(method) || "update".equals(method)) {
			if (null != dingApp) {
				updateMicroApp(fdId, dingApp.getFdDingId());
			} else {
				createMicroApp(fdId);
			}
		}
		if ("delete".equals(method)) {
			if (null != dingApp) {
				deleteMicroApp(dingApp.getFdId(), dingApp.getFdDingId());
			}
		}
	}

	/**
	 * <p>启用、禁用移动应用</p>
	 * @param method
	 * @param ids
	 * @author 孙佳
	 * @throws Exception 
	 */
	private void updateStat(String method, String[] ids) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("thirdDingApp.fdEkpId", ArrayUtil.convertArrayToList(ids)));
		List<ThirdDingApp> dingApp = this.findList(hqlInfo);
		//启用
		if ("appStart".equals(method)) {
			for (int i = 0; i < ids.length; i++) {
				createMicroApp(ids[i]);
			}
		}
		//禁用
		if ("appClose".equals(method)) {
			if (null == dingApp || dingApp.size() <= 0) {
				return;
			}
			for (ThirdDingApp app : dingApp) {
				deleteMicroApp(app.getFdId(), app.getFdDingId());
			}
		}
	}

	private void createMicroApp(String fdId) throws Exception {
		try {
			DingApiService dingApiService = DingUtils.getDingApiService();
			String dingDomain = DingConfig.newInstance().getDingDomain();
			JSONObject app = null;
			PdaModuleConfigMain main = (PdaModuleConfigMain) getPdaModuleConfigMainServiceImp().findByPrimaryKey(fdId);

			if (null != main) {
				//先调用钉钉上传接口，上传应用icon
				String mediaId = uploadAppIconByDing(PluginConfigLocationsUtil.getWebContentPath() + main.getFdIconUrl());
				if (StringUtil.isNotNull(mediaId)) {
					app = new JSONObject();
					app.put("appIcon", mediaId); //应用图标
					app.put("appName", main.getFdName()); //应用名称
					app.put("appDesc",
							StringUtil.isNotNull(main.getFdDescription()) ? main.getFdDescription() : "应用描述"); //应用描述
					app.put("homepageUrl",
							dingDomain + "/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=open&fdId="
									+ main.getFdId() + "&oauth=ekp"); //应用的移动端主页
					app.put("pcHomepageUrl",
							dingDomain + "/third/ding/pc/pcpg.jsp?pg=" + main.getFdUrlPrefix() + "&oauth=ekp"); //应用的PC端主页
					JSONObject object = dingApiService.appCreate(app);
					if ("0".equals(object.get("errcode").toString())) {
						ThirdDingApp modelObj = new ThirdDingApp();
						modelObj.setFdEkpId(main.getFdId());
						modelObj.setFdDingId(object.get("agentid").toString());
						this.add(modelObj);
					} else {
						logger.warn("创建钉钉应用失败：返回错误码：" + object.get("errcode") + "，错误信息：" + object.get("errmsg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateMicroApp(String fdId, String agentId) {
		try {
			DingApiService dingApiService = DingUtils.getDingApiService();
			String dingDomain = DingConfig.newInstance().getDingDomain();
			JSONObject app = null;
			PdaModuleConfigMain main = (PdaModuleConfigMain) pdaModuleConfigMainService.findByPrimaryKey(fdId);
			
			if (null != main) {
				app = new JSONObject();
				//先调用钉钉上传接口，上传应用icon
				String mediaId = uploadAppIconByDing(PluginConfigLocationsUtil.getWebContentPath() + main.getFdIconUrl());
				if (StringUtil.isNotNull(mediaId)) {
					app.put("appIcon", mediaId); //应用图标
					app.put("appName", main.getFdName()); //应用名称
					app.put("appDesc", StringUtil.isNotNull(main.getFdDescription()) ? main.getFdDescription() : "应用描述"); //应用描述
					app.put("homepageUrl",
							dingDomain + "/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=open&fdId="
									+ main.getFdId() + "&oauth=ekp"); //应用的移动端主页
					app.put("pcHomepageUrl",
							dingDomain + "/third/ding/pc/pcpg.jsp?pg=" + main.getFdUrlPrefix() + "&oauth=ekp"); //应用的PC端主页
					app.put("agentId", agentId);
					JSONObject object = dingApiService.appUpdate(app);
					if ("0".equals(object.get("errcode").toString())) {
						ThirdDingApp modelObj = new ThirdDingApp();
						modelObj.setDocAlterTime(new Date());
						modelObj.setDocAlteror(UserUtil.getUser());
						this.add(modelObj);
					} else {
						logger.warn("更新钉钉应用失败：返回错误码：" + object.get("errcode") + "，错误信息：" + object.get("errmsg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteMicroApp(String fdId, String agentId) {
		try {
			DingApiService dingApiService = DingUtils.getDingApiService();
			JSONObject app = new JSONObject();
			app.put("agentId", agentId);
			JSONObject object = dingApiService.appDelete(agentId);
			if ("0".equals(object.get("errcode").toString())) {
				this.delete(fdId);
			} else {
				logger.warn("删除钉钉应用失败：返回错误码：" + object.get("errcode") + "，错误信息：" + object.get("errmsg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
    public String uploadAppIconByDing(String file) throws Exception {
		DingApiService dingApiService = DingUtils.getDingApiService();
		JSONObject object = dingApiService.upload("image", new File(file));
		if ("0".equals(object.get("errcode").toString())) {
			return object.get("media_id").toString();
		} else {
			logger.warn("上传icon到钉钉失败：返回错误码：" + object.get("errcode") + "，错误信息：" + object.get("errmsg"));
		}
		return null;
	}
}
