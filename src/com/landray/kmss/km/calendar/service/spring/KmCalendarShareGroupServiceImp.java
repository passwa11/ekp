package com.landray.kmss.km.calendar.service.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.calendar.model.KmCalendarShareGroup;
import com.landray.kmss.km.calendar.service.IKmCalendarShareGroupService;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.notify.util.SysNotifyConfigUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * 日程共享组设置 业务接口实现
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarShareGroupServiceImp extends BaseServiceImp implements
		IKmCalendarShareGroupService {

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		String fdId = super.add(form, requestContext);
		KmCalendarShareGroup group = (KmCalendarShareGroup) findByPrimaryKey(form
				.getFdId());
		saveSendInviteNotify(group, null, requestContext);
		return fdId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		KmCalendarShareGroup group = (KmCalendarShareGroup) findByPrimaryKey(form
				.getFdId());
		ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
				.getBean("sysOrgCoreService");
		List<String> preIds = sysOrgCoreService.expandToPersonIds(group
				.getShareGroupMembers());
		super.update(form, requestContext);
		List<String> currentIds = new ArrayList<String>();
		List<SysOrgElement> fdGroupMembers = group.getShareGroupMembers();
		if (fdGroupMembers != null && fdGroupMembers.size() > 0) {
			for (SysOrgElement fdGroupMember : fdGroupMembers) {
				currentIds.add(fdGroupMember.getFdId());
			}
		}
		List<String> addIds = new ArrayList<String>();
		for (String id : currentIds) {
			if (!preIds.contains(id)) {
				addIds.add(id);
			}
		}
		if (addIds.size() > 0) {
			saveSendInviteNotify(group,
					sysOrgCoreService.findByPrimaryKeys(addIds
					.toArray(new String[0])), requestContext);
		}
	}

	@Override
	public void saveSendInviteNotify(KmCalendarShareGroup group,
									 List notifyTarget, RequestContext requestContext)
			throws Exception {
		// HashMap<String, String> hashMap = new HashMap<String, String>();
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("km-calendar:kmCalendarShareGroup.invite");
		// hashMap.put("km-calendar:invitor",
		// group.getDocCreator().getFdName());
		NotifyReplace notifyReplace = new NotifyReplace();
		notifyReplace.addReplaceModel("km-calendar:invitor",
				group.getDocCreator(), "fdName");
		notifyContext.setKey("InviteKey");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
		notifyContext
				.setNotifyType(SysNotifyConfigUtil.getNotifyDefaultValue());// 通知方式
		if (notifyTarget == null) {
			notifyContext.setNotifyTarget(group.getShareGroupMembers());// 通知人员
		} else {
			notifyContext.setNotifyTarget(notifyTarget);
		}
		notifyContext.setDocCreator(group.getDocCreator());
		String inviteUrl = requestContext.getParameter("inviteUrl");
		if (StringUtil.isNotNull(inviteUrl)) {
			int queryIndex = inviteUrl.indexOf("?");
			String url = queryIndex > -1 ? inviteUrl.substring(0, queryIndex)
					: inviteUrl;
			String query = queryIndex > -1 ? inviteUrl
					.substring(queryIndex + 1) : "";
			JSONObject extra = new JSONObject();
			extra.put("fdId", group.getFdId());
			query = StringUtil.setQueryParameter(query, "extra",
					extra.toString());
			notifyContext.setLink(url + "?" + query);
		} else {
			notifyContext
					.setLink("/km/calendar/km_calendar_auth/kmCalendarAuth.do?method=invite&fdGroupId="
							+ group.getFdId());
		}
		sysNotifyMainCoreService.sendNotify(group, notifyContext,
				notifyReplace);
	}

	@Override
	public List<KmCalendarShareGroup> getUserShareGroups(String personId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("kmCalendarShareGroup.docCreator.fdId=:personId");
		hqlInfo.setOrderBy("kmCalendarShareGroup.fdOrder");
		hqlInfo.setParameter("personId", personId);
		List<KmCalendarShareGroup> list = (List<KmCalendarShareGroup>) findList(hqlInfo);
		return list;

	}

	@Override
	public Map<String, List> getShareGroupMembers(
			RequestContext requestContext, Boolean loadAll) throws Exception {
		String groupId = requestContext.getParameter("groupId");// 群组id
		String personId = requestContext.getParameter("personsId");// 查询人员
		String loadAllPara = requestContext.getParameter("loadAll");// 加载所有标示位
		if (StringUtil.isNotNull(loadAllPara)) {
			loadAll = new Boolean(loadAllPara);
		}
		KmCalendarShareGroup kmCalendarShareGroup = (KmCalendarShareGroup) findByPrimaryKey(groupId);// 群组
		List list = kmCalendarShareGroup.getShareGroupMembers();// 群组成员
		
		List<SysOrgElement> totalPersons = new ArrayList<SysOrgElement>();// 全部日程人员
		List<SysOrgElement> persons = new ArrayList<SysOrgElement>();// 当前查询日程人员

		if (list != null && !list.isEmpty()) {
			// 移除无效成员
			for (int i = 0; i < list.size(); i++) {
				SysOrgElement person = (SysOrgElement) list.get(i);
				if (person.getFdIsAvailable()) {
					totalPersons.add(person);
				}
			}

			// 筛选指定人员
			if (StringUtil.isNotNull(personId)) {
				for (SysOrgElement person : totalPersons) {
					if (personId.indexOf(person.getFdId()) > -1) {
						persons.add(person);
					}
				}
			} else if (loadAll) {
				persons = totalPersons;
			} else {
				int fromIndex = 0;// 开始index
				int toIndex = fromIndex + 15 > totalPersons.size() ? totalPersons
						.size() : fromIndex + 15;// 结束index
				Iterator iterator = totalPersons.iterator();
				int index = 0;
				while (iterator.hasNext()) {
					SysOrgElement person = (SysOrgElement) iterator.next();
					if (index >= fromIndex) {
						persons.add(person);
					}
					index++;
					if (index >= toIndex) {
                        break;
                    }
				}
			}
		}
		Map<String, List> rtnMap = new HashMap<String, List>();
		rtnMap.put("totalPersons", totalPersons);
		rtnMap.put("persons", persons);
		return rtnMap;
	}

	@Override
	public Map<String, List> getShareGroupMembers(RequestContext requestContext)
			throws Exception {
		return getShareGroupMembers(requestContext, false);
	}
}
