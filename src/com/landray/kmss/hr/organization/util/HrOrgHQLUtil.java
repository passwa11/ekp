package com.landray.kmss.hr.organization.util;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.StringUtil;

public class HrOrgHQLUtil implements HrOrgConstant {
	/**
	 * 拼凑跟组织架构相关的HQL中where语句
	 * 
	 * @param rtnType
	 *            SysOrgConstant中的常量组合
	 * @param whereBlock
	 *            原有的where语句，将跟组织架构的条件进行and运算
	 * @param orgProperty
	 *            组织架构的属性
	 * @return where语句拼凑结果
	 */
	public static String buildWhereBlock(int rtnType, String whereBlock,
			String orgProperty) {
		String m_where = null;
		String filter = null;

		int orgFlag = rtnType;
		if (orgFlag != HR_TYPE_ALL) {
			if ((orgFlag & HR_TYPE_ORG) == HR_TYPE_ORG) {
				filter = orgProperty + ".fdOrgType=" + HR_TYPE_ORG;
			}
			if ((orgFlag & HR_TYPE_DEPT) == HR_TYPE_DEPT) {
				filter = StringUtil.linkString(filter, " or ", orgProperty
						+ ".fdOrgType=" + HR_TYPE_DEPT);
			}
			if ((orgFlag & HR_TYPE_POST) == HR_TYPE_POST) {
				filter = StringUtil.linkString(filter, " or ", orgProperty
						+ ".fdOrgType=" + HR_TYPE_POST);
			}
			if ((orgFlag & HR_TYPE_PERSON) == HR_TYPE_PERSON) {
				filter = StringUtil.linkString(filter, " or ", orgProperty
						+ ".fdOrgType=" + HR_TYPE_PERSON);
			}
			m_where = StringUtil.isNull(filter) ? null : "(" + filter + ")";
		}

		orgFlag = rtnType & HR_FLAG_AVAILABLEALL;
		if (orgFlag == 0) {
			orgFlag = HR_FLAG_AVAILABLEDEFAULT;
		}
		if (orgFlag != HR_FLAG_AVAILABLEALL) {
			if (orgFlag == HR_FLAG_AVAILABLENO) {
				filter = orgProperty + ".fdIsAvailable = " + HibernateUtil.toBooleanValueString(false);
			} else {
				filter = orgProperty + ".fdIsAvailable = " + HibernateUtil.toBooleanValueString(true);
			}
			m_where = StringUtil.linkString(m_where, " and ", filter);
		}

		orgFlag = rtnType & HR_FLAG_BUSINESSALL;
		if (orgFlag == 0) {
			orgFlag = HR_FLAG_BUSINESSDEFAULT;
		}
		if (orgFlag != HR_FLAG_BUSINESSALL) {
			if (orgFlag == HR_FLAG_BUSINESSNO) {
				filter = orgProperty + ".fdIsBusiness = " + HibernateUtil.toBooleanValueString(false);
			} else {
				filter = orgProperty + ".fdIsBusiness = " + HibernateUtil.toBooleanValueString(true);
			}
			m_where = StringUtil.linkString(m_where, " and ", filter);
		}

		return StringUtil.linkString(StringUtil.isNull(whereBlock) ? null : "("
				+ whereBlock + ")", " and ", m_where);
	}

	/**
	 * 拼凑组织架构的子查询语句
	 * 
	 * @param element
	 *            查找该组织架构下的所有架构元素
	 * @param whereBlock
	 *            原有的where语句，将跟组织架构的条件进行and运算
	 * @param orgProperty
	 *            组织架构的属性
	 * @return where语句拼凑结果
	 */
	public static String buildAllChildrenWhereBlock(
			HrOrganizationElement element,
			String whereBlock, String orgProperty) {
		// return StringUtil.linkString(StringUtil.isNull(whereBlock) ? null :
		// "("
		// + whereBlock + ")", " and ", "substring(" + orgProperty
		// + ".fdHierarchyId,1," + element.getFdHierarchyId().length()
		// + ")='" + element.getFdHierarchyId() + "'");
		return StringUtil.linkString(StringUtil.isNull(whereBlock) ? null : "("
				+ whereBlock + ")", " and ", orgProperty
						+ ".fdHierarchyId like '" + element.getFdHierarchyId()
						+ "%'");
	}

	/**
	 * 拼凑组织架构的子查询语句
	 * 
	 * @param orgElements
	 *            组织架构列表
	 * @param whereBlock
	 *            原有的where语句，将跟组织架构的条件进行and运算
	 * @param orgProperty
	 *            组织架构的属性
	 * @return where语句拼凑结果
	 */
	public static String buildAllChildrenWhereBlock(List orgElements,
			String whereBlock, String orgProperty) {
		if (orgElements.isEmpty()) {
			return whereBlock;
		}
		List hierarchyIds = new ArrayList();
		StringBuffer personIds = new StringBuffer();
		for (int i = 0; i < orgElements.size(); i++) {
			HrOrganizationElement element = (HrOrganizationElement) orgElements
					.get(i);
			int orgType = element.getFdOrgType().intValue();
			if (orgType == HR_TYPE_POST)
				/* if (orgType == ORG_TYPE_GROUP || orgType == ORG_TYPE_POST) */ {
				throw new KmssRuntimeException(
						new KmssMessage(
								"sys-organization:SysOrgHQLUtil.error.cannotincludegroupandpost"));
			} else if (orgType == HR_TYPE_PERSON) {
				personIds.append(",'" + element.getFdId() + "'");
			} else {
				hierarchyIds.add(((HrOrganizationElement) orgElements.get(i))
						.getFdHierarchyId());
			}
		}
		hierarchyIds = formatHierarchyIdList(hierarchyIds);
		StringBuffer whereBf = new StringBuffer();
		if (personIds.length() > 0) {
			whereBf.append(" or ").append(orgProperty).append(".fdId in (")
					.append(personIds.substring(1)).append(")");
		}
		for (int i = 0; i < hierarchyIds.size(); i++) {
			String hId = hierarchyIds.get(i).toString();
			// whereBf.append(" or substring(" + orgProperty +
			// ".fdHierarchyId,1,"
			// + hId.length() + ")='" + hId + "'");
			whereBf.append(" or " + orgProperty + ".fdHierarchyId like '" + hId
					+ "%'");
		}
		return StringUtil.linkString(StringUtil.isNull(whereBlock) ? null : "("
				+ whereBlock + ")", " and ", whereBf.length() > 0 ? "("
						+ whereBf.substring(4) + ")" : null);
	}

	public static List formatHierarchyIdList(List srcList) {
		List rtnList = new ArrayList();
		outloop: for (int i = 0; i < srcList.size(); i++) {
			String insertHid = (String) srcList.get(i);
			for (int j = 0; j < rtnList.size(); j++) {
				String hid = (String) rtnList.get(j);
				if (hid.startsWith(insertHid)) {
					rtnList.set(j, insertHid);
					continue outloop;
				} else if (insertHid.startsWith(hid)) {
					continue outloop;
				} else if (insertHid.compareTo(hid) > 0) {
					break;
				}
			}
			rtnList.add(insertHid);
		}
		return rtnList;
	}

	//not in
	public static String buildLogicNotIN(String item, List valueList) {
		int n = (valueList.size() - 1) / 1000;
		StringBuffer rtnStr = new StringBuffer();
		Object obj = valueList.get(0);
		boolean isString = false;
		if (obj instanceof Character || obj instanceof String) {
			isString = true;
		}
		String tmpStr;
		for (int i = 0; i <= n; i++) {
			int size = i == n ? valueList.size() : (i + 1) * 1000;
			if (i > 0) {
				rtnStr.append(" and ");
			}
			rtnStr.append(item + " in (");
			if (isString) {
				StringBuffer tmpBuf = new StringBuffer();
				for (int j = i * 1000; j < size; j++) {
					tmpStr = valueList.get(j).toString().replaceAll("'", "''");
					tmpBuf.append(",'").append(tmpStr).append("'");
				}
				tmpStr = tmpBuf.substring(1);
			} else {
				tmpStr = valueList.subList(i * 1000, size).toString();
				tmpStr = tmpStr.substring(1, tmpStr.length() - 1);
			}
			rtnStr.append(tmpStr);
			rtnStr.append(")");
		}
		if (n > 0) {
			return "(" + rtnStr.toString() + ")";
		} else {
			return rtnStr.toString();
		}
	}
}
