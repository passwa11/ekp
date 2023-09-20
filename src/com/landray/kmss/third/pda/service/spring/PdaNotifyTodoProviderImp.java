package com.landray.kmss.third.pda.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.provider.BaseSysNotifyProviderExtend;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.pda.model.PdaMessagePushInfo;
import com.landray.kmss.third.pda.service.IPdaMessagePushInfoService;
import com.landray.kmss.third.pda.service.IPdaMessagePushMemberService;
import com.landray.kmss.third.pda.util.LicenseUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;

public class PdaNotifyTodoProviderImp extends BaseSysNotifyProviderExtend {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PdaNotifyTodoProviderImp.class);

	private IPdaMessagePushInfoService pdaMessagePushInfoService;

	public void setPdaMessagePushInfoService(
			IPdaMessagePushInfoService pdaMessagePushInfoService) {
		this.pdaMessagePushInfoService = pdaMessagePushInfoService;
	}

	private IPdaMessagePushMemberService pdaMemberService;

	public void setPdaMemberService(
			IPdaMessagePushMemberService pdaMemberService) {
		this.pdaMemberService = pdaMemberService;
	}

	/**
	 * 增加待办(后)
	 */
	@Override
	public void add(SysNotifyTodo todo, NotifyContext context) throws Exception {
		int phoneLicense = LicenseUtil.getPhoneLicence();
		if (phoneLicense != LicenseUtil.LICENSE_NOFUNCTION) { // license不支持
			boolean isNeedMap = phoneLicense != LicenseUtil.LICENSE_UNLIMIT;
			Map<String, String> personMap = null;
			if (isNeedMap) {
                personMap = LicenseUtil.getPersonInfoMap();
            }
			List orgList = ((NotifyContextImp) context).getNotifyPersons();
			if (orgList != null && orgList.size() > 0) {
				String notifyId = todo.getFdId();
				// 性能优化
				List<String> idList = ArrayUtil.convertArrayToList(ArrayUtil
						.joinProperty(orgList, "fdId", ";")[0].split(";"));
				Map<String, String> pushMemberMap = convertPushMember(findPushMember(idList));
				for (Iterator iterator = orgList.iterator(); iterator.hasNext();) {
					SysOrgElement tmpOrg = (SysOrgElement) iterator.next();
					if ((isNeedMap && personMap != null && StringUtil
							.isNotNull(personMap.get(tmpOrg.getFdId())))
							|| (!isNeedMap)) {
						if (pushMemberMap.containsKey(tmpOrg.getFdId())) {
							PdaMessagePushInfo pushInfo = new PdaMessagePushInfo();
							pushInfo.setFdAvailable("1");
							pushInfo.setFdCreateTime(new Date());
							pushInfo.setFdHasPushed("0");
							pushInfo.setFdNotifyId(notifyId);
							pushInfo.setFdPerson(tmpOrg);
							pdaMessagePushInfoService.add(pushInfo);
						}
					}
				}
			}
		}
	}

	/**
	 * 将待办中所有待处理人清空，不需要设为已办
	 */
	@Override
	public void clearTodoPersons(SysNotifyTodo todo) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("pdaMessagePushInfo.fdNotifyId=:notifyId");
		hql.setParameter("notifyId", todo.getFdId());
		if (logger.isDebugEnabled()) {
            logger.debug("清空待办中所有待处理人：" + hql.getWhereBlock());
        }
		List list = pdaMessagePushInfoService.findList(hql);
		if (list != null && list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				PdaMessagePushInfo pushInfo = (PdaMessagePushInfo) iterator
						.next();
				pushInfo.setFdAvailable("0");
				pdaMessagePushInfoService.update(pushInfo);
			}
		}
	}

	/**
	 * 删除待办（前）
	 */
	@Override
	public void remove(SysNotifyTodo todo) throws Exception {
		clearTodoPersons(todo);
	}

	/**
	 * 删除已办的用户（前）
	 */
	@Override
	public void removeDonePerson(SysNotifyTodo todo, SysOrgPerson person)
			throws Exception {
		HQLInfo hql = new HQLInfo();
		hql
				.setWhereBlock("pdaMessagePushInfo.fdNotifyId=:notifyId and pdaMessagePushInfo.fdPerson.fdId=:personId");
		hql.setParameter("notifyId", todo.getFdId());
		hql.setParameter("personId", person.getFdId());
		if (logger.isDebugEnabled()) {
            logger.debug("删除已办的用户：" + hql.getWhereBlock());
        }
		List list = pdaMessagePushInfoService.findList(hql);
		if (list != null && list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				PdaMessagePushInfo pushInfo = (PdaMessagePushInfo) iterator
						.next();
				pushInfo.setFdAvailable("0");
				pdaMessagePushInfoService.update(pushInfo);
			}
		}
	}

	/**
	 * 将待办中指定人设置为已办（前）
	 */
	@Override
	public void setPersonsDone(SysNotifyTodo todo, List persons)
			throws Exception {
		String sqlInStr = "";
		for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
			SysOrgElement tmpPersom = (SysOrgElement) iterator.next();
			sqlInStr += ",'" + tmpPersom.getFdId() + "'";
		}
		if (StringUtil.isNotNull(sqlInStr)) {
			sqlInStr = sqlInStr.substring(1);
		}
		if (StringUtil.isNotNull(sqlInStr)) {
			HQLInfo hql = new HQLInfo();
			hql
					.setWhereBlock("pdaMessagePushInfo.fdNotifyId=:notifyId and pdaMessagePushInfo.fdPerson.fdId in("
							+ sqlInStr + ")");
			hql.setParameter("notifyId", todo.getFdId());
			if (logger.isDebugEnabled()) {
                logger.debug("将待办中指定人设置为已办：" + hql.getWhereBlock());
            }
			List list = pdaMessagePushInfoService.findList(hql);
			if (list != null && list.size() > 0) {
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					PdaMessagePushInfo pushInfo = (PdaMessagePushInfo) iterator
							.next();
					pushInfo.setFdAvailable("0");
					pdaMessagePushInfoService.update(pushInfo);
				}
			}
		}
	}

	/**
	 * 将待办中的所有人设置为已办（前）
	 */
	@Override
	public void setTodoDone(SysNotifyTodo todo) throws Exception {
		clearTodoPersons(todo);
	}

	private Map<String, String> convertPushMember(List<Object[]> list) {
		Map<String, String> rtnMap = new HashMap<String, String>();

		if (!ArrayUtil.isEmpty(list)) {
			for (Object[] arr : list) {
				if (arr[0] != null && arr[1] != null) {
					String personId = arr[0].toString();
					String pushMemberId = arr[1].toString();
					rtnMap.put(personId, pushMemberId);
				}
			}
		}

		return rtnMap;
	}

	private List<Object[]> findPushMember(List<String> idList) throws Exception {
		List<Object[]> rtnList = new ArrayList();

		if (!idList.isEmpty()) {
			int beginIndex = 0, endIndex = 0;

			while (endIndex < idList.size()) {
				endIndex = beginIndex + 2000;
				if (endIndex > idList.size()) {
					endIndex = idList.size();
				}

				List<String> subList = idList.subList(beginIndex, endIndex);
				HQLWrapper hqlWrapper = HQLUtil.buildPreparedLogicIN(
						"pdaMessagePushMember.fdPerson.fdId","pdaMessagePushMember"+ "0_", subList);
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo
						.setSelectBlock("pdaMessagePushMember.fdPerson.fdId, pdaMessagePushMember.fdId");
				hqlInfo.setWhereBlock(hqlWrapper.getHql()
						+ " and pdaMessagePushMember.fdStatus='1'");
				hqlInfo.setParameter(hqlWrapper.getParameterList());
				List<Object[]> results = pdaMemberService.findValue(hqlInfo);

				if (!ArrayUtil.isEmpty(results)) {
					rtnList.addAll(results);
				}

				beginIndex += 2000;
			}
		}

		return rtnList;
	}

}
