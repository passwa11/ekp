package com.landray.kmss.sys.praise.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IKmssSystemInitBean;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.sys.cluster.model.SysClusterParameter;
import com.landray.kmss.sys.config.design.SysCfgModuleInfo;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoConfigForm;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoConfigMainForm;
import com.landray.kmss.sys.praise.message.SysPraiseConfigInfoUpdateMessage;
import com.landray.kmss.sys.praise.model.SysPraiseInfoConfig;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoConfigService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SysPraiseInfoConfigServiceImp extends BaseServiceImp
		implements ISysPraiseInfoConfigService, IKmssSystemInitBean {
	private static final Log logger = LogFactory.getLog(SysPraiseInfoConfigServiceImp.class);

	private String defaultModules;

	public String getDefaultModules() {
		return defaultModules;
	}

	public void setDefaultModules(String defaultModules) {
		this.defaultModules = defaultModules;
	}

	private List<JSONObject> moduleList = null;

	private Map<String, String> moduleMap = null;

	private List<String> cacheModuleList = null;

	private String fdUsefulLink = null;

	@Override
	public List<SysPraiseInfoConfigForm> updateFindConfigList()
			throws Exception {
		List<SysPraiseInfoConfigForm> configList =
				new ArrayList<SysPraiseInfoConfigForm>();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy("sysPraiseInfoConfig.fdId ASC");
		List<SysPraiseInfoConfig> list = findList(hqlInfo);
		cacheModuleList = new ArrayList<String>();
		if (moduleList == null) {
			getModuleList();
		}

		for (SysPraiseInfoConfig sysPraiseInfoConfig : list) {
			if (moduleMap
					.containsKey(sysPraiseInfoConfig.getFdModuleUrlPrefix())) {
				cacheModuleList.add(sysPraiseInfoConfig.getFdModuleUrlPrefix());
				configList
						.add((SysPraiseInfoConfigForm) (convertModelToForm(null,
								sysPraiseInfoConfig, new RequestContext())));

			}

		}

		// 查询记录日志
		UserOperHelper.logFindAll(list, getModelName());

		if (configList.size() == 0) {
			RequestContext requestContext = new RequestContext();
			String urlPrefix = null;
			for (JSONObject object : moduleList) {
				urlPrefix = object.getString("urlPrefix").toString();
				if (defaultModules.indexOf(urlPrefix) > -1) {
					SysPraiseInfoConfigForm sysPraiseInfoConfigForm =
							new SysPraiseInfoConfigForm();
					sysPraiseInfoConfigForm.setFdIsUsed("true");
					sysPraiseInfoConfigForm.setFdModuleKey(
							object.getString("moduleKey").toString());
					sysPraiseInfoConfigForm.setFdModuleUrlPrefix(urlPrefix);
					cacheModuleList.add(urlPrefix);
					configList.add(sysPraiseInfoConfigForm);
					update(sysPraiseInfoConfigForm, requestContext);

				}
			}
		}
		return configList;
	}

	private List<String> getModuleList() {
		moduleList = new ArrayList<JSONObject>();
		moduleMap = new HashMap<String, String>();
		List<SysCfgModuleInfo> moduleInfoList =
				SysConfigs.getInstance().getModuleInfoList();
		String urlPrefix = null;
		for (SysCfgModuleInfo sysCfgModuleInfo : moduleInfoList) {
			JSONObject object = new JSONObject();
			if (StringUtil.isNotNull(sysCfgModuleInfo.getUrlPrefix())
					&& StringUtil.isNotNull(sysCfgModuleInfo.getMessageKey())
					&& StringUtil.isNotNull(ResourceUtil
							.getString(sysCfgModuleInfo.getMessageKey()))) {
				urlPrefix = sysCfgModuleInfo.getUrlPrefix();
				urlPrefix = urlPrefix.substring(1, urlPrefix.length() - 1);
				object.accumulate("moduleKey",
						sysCfgModuleInfo.getMessageKey());
				object.accumulate("urlPrefix", urlPrefix);
				moduleMap.put(urlPrefix, sysCfgModuleInfo.getMessageKey());
				moduleList.add(object);
			}
		}
		return null;
	}

	@Override
	public List<JSONObject> getShowModule() throws Exception {
		List<JSONObject> showList = new ArrayList<JSONObject>();
		if (moduleList == null) {
			getModuleList();
		}
		if (cacheModuleList.size() > 0) {
			for (JSONObject object : moduleList) {
				if (!cacheModuleList.contains(object.get("urlPrefix"))) {
					showList.add(object);

					if (UserOperHelper.allowLogOper("importModule",
							getModelName())) {
						UserOperContentHelper.putFind(
								object.getString("urlPrefix"),
								object.getString("moduleKey"), null);
					}
				}
			}
		} else {
			showList.addAll(moduleList);

			if (UserOperHelper.allowLogOper("importModule", getModelName())) {

				for (JSONObject object : moduleList) {
					UserOperContentHelper.putAdd(object.getString("urlPrefix"),
							object.getString("moduleKey"), null);
				}
			}
		}

		// 记录日志
		// TODO
		if (UserOperHelper.allowLogOper("importModule", getModelName())) {
			for (JSONObject obj : showList) {
				UserOperContentHelper.putFind(obj.getString("urlPrefix"),
						obj.getString("moduleKey"), null);
			}
		}

		return showList;
	}

	private void getCacheModuleList() {
		String[] modules = defaultModules.split(";");
		cacheModuleList = new ArrayList(Arrays.asList(modules));
	}

	@Override
	public List<JSONObject> updateCache(String moduleInfo) {
		List<JSONObject> rtnList = new ArrayList<JSONObject>();
		if (StringUtil.isNotNull(moduleInfo)) {
			String[] modules = moduleInfo.split(";");
			for (String str : modules) {
				JSONObject obj = new JSONObject();
				obj.accumulate("moduleKey", moduleMap.get(str));
				obj.accumulate("moduleName",
						ResourceUtil.getString(moduleMap.get(str)));
				obj.accumulate("urlPrefix", str);

				rtnList.add(obj);
				if (cacheModuleList.contains(str)) {
					continue;
				}

				if (UserOperHelper.allowLogOper("updateCache",
						getModelName())) {
					UserOperContentHelper.putAdd(obj.getString("urlPrefix"),
							obj.getString("moduleName"), null);
				}

				cacheModuleList.add(str);
			}

			// 记录日志
			if (UserOperHelper.allowLogOper("updateCache", getModelName())) {
				for (JSONObject obj : rtnList) {
					UserOperContentHelper.putFind(obj.getString("urlPrefix"),
							obj.getString("moduleName"), null);
				}
			}

		}

		return rtnList;
	}

	@Override
	public String getUsefulLink() throws Exception {
		// 集群环境直接查询，避免同步消息不可控
		if (SysClusterParameter.getInstance().getAllServers().size() > 1
				&& null != fdUsefulLink) {
			fdUsefulLink = null;
		}

		if (StringUtil.isNull(fdUsefulLink)) {
			List<SysPraiseInfoConfigForm> configList =
					new ArrayList<SysPraiseInfoConfigForm>();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("sysPraiseInfoConfig.fdIsUsed =:fdIsUsed");
			hqlInfo.setParameter("fdIsUsed", "true");
			hqlInfo.setOrderBy("sysPraiseInfoConfig.fdId ASC");
			List<SysPraiseInfoConfig> list = findList(hqlInfo);
			for (SysPraiseInfoConfig sysPraiseInfoConfig : list) {
				if (StringUtil.isNull(fdUsefulLink)) {
					fdUsefulLink = sysPraiseInfoConfig.getFdModuleUrlPrefix();
				} else {
					fdUsefulLink +=
							";" + sysPraiseInfoConfig.getFdModuleUrlPrefix();
				}
			}
		}
		return fdUsefulLink;
	}

	@Override
	public void updateConfig(
			SysPraiseInfoConfigMainForm sysPraiseInfoConfigMainForm,
			HttpServletRequest request) throws Exception {
		if (sysPraiseInfoConfigMainForm != null) {
			String fdUsefulLinkNew = "";
			List<SysPraiseInfoConfigForm> configList =
					sysPraiseInfoConfigMainForm.getConfigList();
			for (SysPraiseInfoConfigForm sysPraiseInfoConfigForm : configList) {
				if (StringUtil.isNotNull(sysPraiseInfoConfigForm.getFdIsUsed())
						&& "true"
								.equals(sysPraiseInfoConfigForm.getFdIsUsed())) {
					if (StringUtil.isNull(fdUsefulLinkNew)) {
						fdUsefulLinkNew =
								sysPraiseInfoConfigForm.getFdModuleUrlPrefix();
					} else {
						fdUsefulLinkNew += ";" + sysPraiseInfoConfigForm
								.getFdModuleUrlPrefix();
					}
				}
				update(sysPraiseInfoConfigForm, new RequestContext(request));
			}
			fdUsefulLink = fdUsefulLinkNew;
		}
	}

	@Override
	public KmssMessages initializeData() {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy("sysPraiseInfoConfig.fdId ASC");
			Object obj = findFirstOne(hqlInfo);
			if (obj == null) {
				if (moduleList == null) {
					getModuleList();
				}
				if (StringUtil.isNotNull(defaultModules)) {
					String[] defaultValues = defaultModules.split(";");
					for (String str : defaultValues) {
						if (moduleMap.containsKey(str)) {
							SysPraiseInfoConfig sysPraiseInfoConfig =
									new SysPraiseInfoConfig();
							sysPraiseInfoConfig.setFdIsUsed("true");
							sysPraiseInfoConfig
									.setFdModuleKey(moduleMap.get(str));
							sysPraiseInfoConfig.setFdModuleUrlPrefix(str);
							update(sysPraiseInfoConfig);
						}
					}
				}
			}
		} catch (Exception e) {
			return new KmssMessages().addError(new KmssMessage(
					"sys-praise:sysPraiseInfoConfig.init.failure"), e);
		}
		return new KmssMessages().addMsg(
				new KmssMessage("sys-praise:sysPraiseInfoConfig.init.succeed"));
	}

	@Override
	public String initName() {

		return ResourceUtil.getString("sysPraiseInfoConfig.init.name",
				"sys-praise");
	}

	// =================
	// 集群处理
	// =================

	/**
	 * 更新通知集群其他节点同步更新
	 *
	 * @author tangyw
	 * @date 2021-08-02
	 */
	@Override
	public void update(IBaseModel baseModel) throws Exception {
		super.update(baseModel);

		logger.debug("node->" + SysClusterParameter.getInstance().getLocalServer().getFdId() + " send message");

		// 向群集中的所有节点发送消息（本节点除外）
		MessageCenter.getInstance().sendToOther(new SysPraiseConfigInfoUpdateMessage(baseModel));
	}

	@Override
	public void updateToCluster(IBaseModel baseModel) throws Exception {
		logger.debug("node->" + SysClusterParameter.getInstance().getLocalServer().getFdId() + " updateToCluster");
		fdUsefulLink = null;
	}
	// =================
	// =================
}
