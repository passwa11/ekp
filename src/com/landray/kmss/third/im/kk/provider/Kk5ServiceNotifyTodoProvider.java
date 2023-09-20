package com.landray.kmss.third.im.kk.provider;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProviderExtend;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.third.im.kk.util.KK5Util;
import com.landray.kmss.third.im.kk.util.NotifyConfigUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Kk5ServiceNotifyTodoProvider extends BaseSysNotifyProviderExtend {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(Kk5ServiceNotifyTodoProvider.class);

	protected IKkImConfigService kkImConfigService;

	public void setKkImConfigService(IKkImConfigService kkImConfigService) {
		this.kkImConfigService = kkImConfigService;
	}

	@Override
	public void add(SysNotifyTodo todo, NotifyContext context) throws Exception {
		if (needSendToKK5Service()) {
			String corp_service = kkImConfigService.getValuebyKey(KeyConstants.EKP_SELECT_SERVICE);
			if (StringUtil.isNull(corp_service)) {
				log.info(ResourceUtil.getString("hint.no.corpservice", "third-im-kk"));
				return;
			}
			try {
				KK5Util.pushToServiceUser(getJsonNotifyInfo(todo, context, corp_service));
			} catch (Exception e) {
				log.error("error", e);
			}
		}

	}

	private boolean needSendToKK5Service() throws Exception {
		String notifyType = kkImConfigService.getValuebyKey(KeyConstants.EKP_NOTIFY_TYPE);
		String notify = kkImConfigService.getValuebyKey(KeyConstants.EKP_NOTIFY);
		if (StringUtil.isNotNull(notifyType) && "gzhNotify".equals(notifyType) && StringUtil.isNotNull(notify) && "true".equals(notify)) {
			return true;
		} else {
			/*String kk5ServiceEnable = kkImConfigService.getValuebyKey(KeyConstants.KK_CONFIG_SATUS);
			if (StringUtil.isNotNull(kk5ServiceEnable) && "true".equals(kk5ServiceEnable)) {
				return true;
			}*/
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getJsonNotifyInfo(SysNotifyTodo todo, NotifyContext context, String corp_service_str)
			throws Exception {
		JSONObject notifyInfo = new JSONObject();
		List<SysOrgPerson> notifyPersons = ((NotifyContextImp) context).getNotifyPersons();
		String[] corp_Service = corp_service_str.split(",");
		notifyInfo.accumulate("corp", corp_Service[0]);
		notifyInfo.accumulate("service", corp_Service[1]);
		notifyInfo.accumulate("userType", 1);
		JSONArray notifyUsers = new JSONArray();
		for (SysOrgPerson user : notifyPersons) {
			notifyUsers.add(user.getFdLoginName());
		}
		notifyInfo.accumulate("users", notifyUsers);
		notifyInfo.accumulate("type", 2);
		JSONObject mainMsg = new JSONObject();
		mainMsg.accumulate("title", context.getSubject());
		mainMsg.accumulate("content", context.getLinkSubject() + "\n\n" + context.getSubject());
		mainMsg.accumulate("picurl", "");
		mainMsg.accumulate("url", getContentUrl(context.getLink()));
		mainMsg.accumulate("time", DateUtil.convertDateToString(new Date(), "yyyyMMddHHmmss"));
		notifyInfo.accumulate("mainMsg", mainMsg);
		return notifyInfo;
	}

	private String getContentUrl(String docUrl) throws Exception {
		StringBuffer fullUrlBuffer = new StringBuffer();
		String ekpUrl = NotifyConfigUtil.getNotifyUrlPrefix();
		fullUrlBuffer.append(ekpUrl.endsWith("/") ? ekpUrl.substring(0, ekpUrl.length() - 1) : ekpUrl).append(docUrl);
		return fullUrlBuffer.toString();
	}

	@Override
	public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
	}

	@Override
	public void remove(SysNotifyTodo todo) throws Exception {
	}

	@Override
	public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person) throws Exception {
	}

	@Override
	public void setPersonsDone(SysNotifyTodo todo, List persons) throws Exception {
	}

	@Override
	public void setTodoDone(SysNotifyTodo todo) throws Exception {
	}

}