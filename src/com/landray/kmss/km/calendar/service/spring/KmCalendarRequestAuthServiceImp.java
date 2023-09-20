package com.landray.kmss.km.calendar.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.calendar.model.KmCalendarRequestAuth;
import com.landray.kmss.km.calendar.service.IKmCalendarRequestAuthService;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.notify.util.SysNotifyConfigUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class KmCalendarRequestAuthServiceImp extends BaseServiceImp
		implements IKmCalendarRequestAuthService {

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	@Override
	public KmCalendarRequestAuth findByCreateId(String createId)
			throws Exception {
		KmCalendarRequestAuth requestAuth = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"kmCalendarRequestAuth.docCreator.fdId=:createId");
		hqlInfo.setParameter("createId", createId);
		List<KmCalendarRequestAuth> list = findList(hqlInfo);
		if (list != null && list.size() > 0) {
			requestAuth = list.get(0);
		}
		return requestAuth;
	}

	@Override
	public void saveSendRequestNotify(
			KmCalendarRequestAuth kmCalendarRequestAuth, List notifyTarget)
			throws Exception {
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("km-calendar:kmCalendarRquestAuth.request");
		NotifyReplace notifyReplace = new NotifyReplace();
		notifyReplace.addReplaceModel("km-calendar:requester",
				kmCalendarRequestAuth.getDocCreator(), "fdName");
		notifyContext.setKey("requestAuthKey");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
		notifyContext
				.setNotifyType(SysNotifyConfigUtil.getNotifyDefaultValue());// 通知方式
		if (notifyTarget == null) {
			notifyContext.setNotifyTarget(
					kmCalendarRequestAuth.getFdRequestPerson());// 通知人员
		} else {
			notifyContext.setNotifyTarget(notifyTarget);
		}
		notifyContext.setDocCreator(kmCalendarRequestAuth.getDocCreator());
		notifyContext.setLink(
				"/km/calendar/km_calendar_auth/kmCalendarAuth.do?method=requestAuth&fdRequestAuthId="
						+ kmCalendarRequestAuth.getFdId());
		sysNotifyMainCoreService.sendNotify(kmCalendarRequestAuth,
				notifyContext,
				notifyReplace);
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmCalendarRequestAuth kmCalendarRequestAuth = (KmCalendarRequestAuth) modelObj;
		kmCalendarRequestAuth.setDocCreator(UserUtil.getUser());
		saveSendRequestNotify(kmCalendarRequestAuth, null);
		return super.add(modelObj);
	}

	@Override
	public void saveSendRequestNoNotify(
			KmCalendarRequestAuth kmCalendarRequestAuth, SysOrgPerson requestee,
			List notifyTarget)
			throws Exception {
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("km-calendar:kmCalendarRquestAuth.requestNo");
		NotifyReplace notifyReplace = new NotifyReplace();
		notifyReplace.addReplaceModel("km-calendar:requestee", requestee,
				"fdName");
		notifyContext.setKey("requestNoAuthKey");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		notifyContext
				.setNotifyType(SysNotifyConfigUtil.getNotifyDefaultValue());// 通知方式
		notifyContext.setNotifyTarget(notifyTarget);
		notifyContext.setDocCreator(requestee);
		sysNotifyMainCoreService.sendNotify(kmCalendarRequestAuth,
				notifyContext,
				notifyReplace);
	}

	@Override
	public void saveSendRequestYesNotify(
			KmCalendarRequestAuth kmCalendarRequestAuth, SysOrgPerson requestee,
			List notifyTarget, RequestContext requestContext) throws Exception {
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("km-calendar:kmCalendarRquestAuth.requestYes");
		NotifyReplace notifyReplace = new NotifyReplace();
		notifyReplace.addReplaceModel("km-calendar:requestee", requestee,
				"fdName");
		notifyReplace.addReplaceText("km-calendar:requestAuth",
				getRequestAuthText(kmCalendarRequestAuth.getDocCreator(),
						requestContext));
		notifyContext.setKey("requestYesAuthKey");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		notifyContext
				.setNotifyType(SysNotifyConfigUtil.getNotifyDefaultValue());// 通知方式
		notifyContext.setNotifyTarget(notifyTarget);
		notifyContext.setDocCreator(requestee);
		sysNotifyMainCoreService.sendNotify(kmCalendarRequestAuth,
				notifyContext, notifyReplace);
	}

	private String getRequestAuthText(SysOrgPerson person,
			RequestContext requestContext) {
		String text = "";
		String isAuthRead = requestContext.getParameter("authRead");
		String isAuthEdit = requestContext.getParameter("authEdit");
		String isAuthModify = requestContext.getParameter("authModify");
		if (StringUtil.isNotNull(isAuthRead) && "true".equals(isAuthRead)) {
			text += ResourceUtil.getStringValue(
					"kmCalendarRquestAuth.fdRequestAuth.authRead",
					"km-calendar",
					ResourceUtil.getLocale(person.getFdDefaultLang())) + ";";
		}
		if (StringUtil.isNotNull(isAuthEdit) && "true".equals(isAuthEdit)) {
			text += ResourceUtil.getStringValue(
					"kmCalendarRquestAuth.fdRequestAuth.authEdit",
					"km-calendar",
					ResourceUtil.getLocale(person.getFdDefaultLang())) + ";";
		}
		if (StringUtil.isNotNull(isAuthModify) && "true".equals(isAuthModify)) {
			text += ResourceUtil.getStringValue(
					"kmCalendarRquestAuth.fdRequestAuth.authModify",
					"km-calendar",
					ResourceUtil.getLocale(person.getFdDefaultLang())) + ";";
		}
		text = StringUtil.isNotNull(text) ? text.substring(0, text.length() - 1)
				: text;
		return text;
	}

}
