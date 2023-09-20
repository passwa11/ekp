package com.landray.kmss.sys.portal.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.service.IKmssSystemInitBean;
import com.landray.kmss.sys.cluster.interfaces.ClusterDiscover;
import com.landray.kmss.sys.cluster.interfaces.Event_ClusterReady;
import com.landray.kmss.sys.cluster.interfaces.group.GroupConfigChangeMessage;
import com.landray.kmss.sys.cluster.interfaces.group.IGroupConfigStorer;
import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.sys.cluster.model.SysClusterGroup;
import com.landray.kmss.sys.config.xml.NamespaceHandlerSupport;
import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.sys.portal.util.SysPortalConfig;
import com.landray.kmss.sys.portal.xml.parser.FooterElementParser;
import com.landray.kmss.sys.portal.xml.parser.HeaderElementParser;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.SysUiConstant;
import com.landray.kmss.sys.ui.xml.model.SysUiFormat;
import com.landray.kmss.sys.ui.xml.model.SysUiPortlet;
import com.landray.kmss.sys.ui.xml.model.SysUiRender;
import com.landray.kmss.sys.ui.xml.model.SysUiSource;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;

public class PortalNamespaceHandler extends NamespaceHandlerSupport implements
		ApplicationListener, IKmssSystemInitBean, IMessageReceiver {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PortalNamespaceHandler.class);
	private IGroupConfigStorer groupConfigStorer;

	public IGroupConfigStorer getGroupConfigStorer() {
		return groupConfigStorer;
	}

	public void setGroupConfigStorer(IGroupConfigStorer groupConfigStorer) {
		this.groupConfigStorer = groupConfigStorer;
	}

	protected IMessageQueue messageQueue = new UniqueMessageQueue();

	@Override
    public IMessageQueue getMessageQueue() {
		return messageQueue;
	}

	@Override
    public void init() {
		registerBeanDefinitionParser("header", new HeaderElementParser());
		registerBeanDefinitionParser("footer", new FooterElementParser());
	}

	@Override
    public void beforeLoad() {
		PortalUtil.getPortalHeaders().clear();
		PortalUtil.getPortalFooters().clear();
	}

	/** 群集准备好后，安排相关任务 */
	@Override
    public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof Event_ClusterReady) {
			loadOtherGroupPortletInfo();
		}
	}

	/**
	 * 其它系统启动时，读取它的配置信息
	 */

	@Override
	public void receiveMessage(IMessage message) throws Exception {
		if (message instanceof GroupConfigChangeMessage) {
			GroupConfigChangeMessage info = (GroupConfigChangeMessage) message;
			loadOtherGroupPortletInfoByGroupKey(info.getGroupKey());
		}
	}

	/**
	 * 系统初始化时，存储自己的信息
	 */
	@Override
	public KmssMessages initializeData() {
		try {
			JSONObject json = new JSONObject();
			String[] types = new String[] { "portlet", "render", "format",
					"source" };
			for (int i = 0; i < types.length; i++) {
				JSONArray array = new JSONArray();
				String type = types[i];
				Map xxx = null;
				if ("portlet".equals(type)) {
					xxx = SysUiPluginUtil.getPortlets();
				} else if ("render".equals(type)) {
					xxx = SysUiPluginUtil.getRenders();
				} else if ("format".equals(type)) {
					xxx = SysUiPluginUtil.getFormats();
				} else if ("source".equals(type)) {
					xxx = SysUiPluginUtil.getSources();
				}
				if (xxx != null) {
					Iterator iterator = xxx.keySet().iterator();
					while (iterator.hasNext()) {
						String key = iterator.next().toString();
						if (key.indexOf(SysUiConstant.SEPARATOR) > 0) {
						} else {
							array.add(JSONObject.fromObject(xxx.get(key)));
						}
					}
					json.put(type, array);
				}
			}
			groupConfigStorer.publishGroupConfig(this.getClass().getName(),
					json);
		} catch (Exception e) {
			return new KmssMessages().addError(new KmssMessage(
					"sys-portal:portal.config.publish.failure"), e);
		}
		return new KmssMessages().addMsg(new KmssMessage(
				"sys-portal:portal.config.publish.succeed"));
	}

	@Override
	public String initName() {
		return ResourceUtil.getString("desgin.msg.configwidget.init.name",
				"sys-portal");
	}

	public void loadOtherGroupPortletInfo() {
		List<SysClusterGroup> groups = ClusterDiscover.getInstance()
				.getAllGroup();
		for (int i = 0; i < groups.size(); i++) {
			SysClusterGroup group = groups.get(i);
			if (!group.getFdLocal()) {
				try {
					String code = group.getFdKey();
					loadOtherGroupPortletInfoByGroupKey(code);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void loadOtherGroupPortletInfoByGroupKey(String code)
			throws Exception {
		String[] types = new String[] { "portlet", "render", "format", "source" };
		JSONObject json = groupConfigStorer.loadGroupConfig(this.getClass()
				.getName(), code);
		if (json != null && !json.isEmpty()) {
			for (int i = 0; i < types.length; i++) {
				String type = types[i];
				JSONArray array = json.getJSONArray(type);
				if (array != null) {
					for (int j = 0; j < array.size(); j++) {
						JSONObject obj = array.getJSONObject(j);
						try {
							if ("render".equals(type)) {
								SysUiRender render = SysPortalConfig
										.newRenderByJson(code, obj);
								SysUiPluginUtil.getRenders().put(
										render.getFdId(), render);
							} else if ("format".equals(type)) {
								SysUiFormat format = SysPortalConfig
										.newFormatByJson(code, obj);
								SysUiPluginUtil.getFormats().put(
										format.getFdId(), format);
							} else if ("source".equals(type)) {
								SysUiSource source = SysPortalConfig
										.newSourceByJson(code, obj);
								SysUiPluginUtil.getSources().put(
										source.getFdId(), source);
							} else if ("portlet".equals(type)) {
								SysUiPortlet portlet = SysPortalConfig
										.newPortletByJson(code, obj);
								SysUiPluginUtil.getPortlets().put(
										portlet.getFdId(), portlet);
							}
						} catch (Exception e) {
							logger.error(obj.toString(), e);
						}
					}
				}
			}
		}
	}

	@Override
    public void afterLoad() {

	}
}
