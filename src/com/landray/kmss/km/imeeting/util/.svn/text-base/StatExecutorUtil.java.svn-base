package com.landray.kmss.km.imeeting.util;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatExecutorUtil {

	/**
	 * 组装部门条件
	 * 
	 * @param itemName
	 *            HQL工作项中处理人属性
	 * @param condition
	 *            条件值
	 * @return
	 */
	public final static StatHqlInfo getOrgHqlInfo(Session session,
			String itemName, Object params) {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		List<String> orgList = formatCondtionParameter(params);
		if (orgList != null && orgList.size() > 0) {
			String hql = "select sysOrgElement.fdHierarchyId from "
					+ SysOrgElement.class.getName() + " sysOrgElement "
					+ "where sysOrgElement.fdId in (:orgIds)";
			Query q = session.createQuery(hql);
			q.setCacheable(true);
			q.setCacheMode(CacheMode.NORMAL);
			q.setCacheRegion("km-imeeting");
			q.setParameterList("orgIds", orgList);
			List result = q.list();
			if (result.isEmpty()) {
				return hqlInfo;
			}
			itemName += ".fdHierarchyId";
			StringBuffer rtnVal = new StringBuffer();
			for (int i = 0; i < result.size(); i++) {
				rtnVal.append(" or ").append(itemName)
						.append(" like :orgId" + i + " ");
				hqlInfo.setParameter("orgId" + i, ((String) result.get(i))
						+ "%");
			}
			hqlInfo.setHqlBlock("(" + rtnVal.substring(4) + ")");
		}
		return hqlInfo;
	}

	/**
	 * 组装会议类型条件
	 */
	public final static StatHqlInfo getCategoryHqlInfo(String itemName,
			String templateId)
			throws Exception {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		if (StringUtil.isNotNull(templateId)) {
			List<String> ids = ArrayUtil.convertArrayToList(templateId
					.split(";"));
			hqlInfo.setHqlBlock(HQLUtil.buildLogicIN(itemName + ".fdId",
					ids));
		}
		return hqlInfo;
	}

	/**
	 * 组装会议室资源条件
	 * 
	 * @param itemName
	 * @param resId
	 * @return
	 * @throws Exception
	 */
	public final static StatHqlInfo getResHqlInfo(String itemName, String resId)
			throws Exception {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		if (StringUtil.isNotNull(resId)) {
			hqlInfo.setHqlBlock(" " + itemName + ".fdId=:resId");
			hqlInfo.setParameter("resId", resId);
		}
		return hqlInfo;
	}

	/**
	 * 组装时间条件
	 * 
	 * @param docItemName
	 *            文档
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public final static StatHqlInfo getTimeScopeHqlInfo(Session session,
			String docItemName, Object startTime, Object endTime) {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		StringBuffer tmpWhereBlock = new StringBuffer();
		if (startTime != null) {
			tmpWhereBlock.append(docItemName).append(
					".fdHoldDate >= :startTime ");
			hqlInfo.setParameter("startTime", clearTime(startTime));
		}
		if (endTime != null) {
			if (StringUtil.isNotNull(tmpWhereBlock.toString())) {
				tmpWhereBlock.append("and " + docItemName).append(
						".fdHoldDate <= :endTime ");
			} else {
				tmpWhereBlock.append(docItemName).append(
						".fdHoldDate <= :endTime ");
			}
			hqlInfo.setParameter("endTime", fillTime(endTime));
		}
		hqlInfo.setHqlBlock(tmpWhereBlock.toString());
		return hqlInfo;
	}

	public final static Object clearTime(Object dateTime) {
		if (dateTime instanceof Date) {
			Date date = (Date) dateTime;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			return calendar.getTime();
		}
		return dateTime;
	}

	public final static Object fillTime(Object dateTime) {
		if (dateTime instanceof Date) {
			Date date = (Date) dateTime;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			return calendar.getTime();
		}
		return dateTime;
	}

	/**
	 * 根据参数格式化参数为数组
	 * 
	 * @param paramInfo
	 * @return
	 */
	public static List<String> formatCondtionParameter(Object paramInfo) {
		if (paramInfo != null) {
			if (paramInfo instanceof List) {
				return (List<String>) paramInfo;
			} else {
				if (StringUtil.isNotNull((String) paramInfo)) {
					return ArrayUtil.convertArrayToList(((String) paramInfo)
							.split("\\s*;\\s*"));
				}
			}
		}
		return null;
	}

}
