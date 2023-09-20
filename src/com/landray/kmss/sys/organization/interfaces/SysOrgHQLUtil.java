package com.landray.kmss.sys.organization.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.config.loader.KmssHibernateLocalSessionFactoryBean;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;

public class SysOrgHQLUtil implements SysOrgConstant {
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
		// if (orgFlag == 0)
		// orgFlag = ORG_TYPE_DEFAULT;
		if (orgFlag != ORG_TYPE_ALL
				&& orgFlag != (ORG_TYPE_ALL | ORG_TYPE_ROLE)) {
			if ((orgFlag & ORG_TYPE_ORG) == ORG_TYPE_ORG) {
                filter = orgProperty + ".fdOrgType=" + ORG_TYPE_ORG;
            }
			if ((orgFlag & ORG_TYPE_DEPT) == ORG_TYPE_DEPT) {
                filter = StringUtil.linkString(filter, " or ", orgProperty
                        + ".fdOrgType=" + ORG_TYPE_DEPT);
            }
			if ((orgFlag & ORG_TYPE_POST) == ORG_TYPE_POST) {
                filter = StringUtil.linkString(filter, " or ", orgProperty
                        + ".fdOrgType=" + ORG_TYPE_POST);
            }
			if ((orgFlag & ORG_TYPE_PERSON) == ORG_TYPE_PERSON) {
                filter = StringUtil.linkString(filter, " or ", orgProperty
                        + ".fdOrgType=" + ORG_TYPE_PERSON);
            }
			if ((orgFlag & ORG_TYPE_GROUP) == ORG_TYPE_GROUP) {
                filter = StringUtil.linkString(filter, " or ", orgProperty
                        + ".fdOrgType=" + ORG_TYPE_GROUP);
            }
			// 增加对角色的搜索，由于原ALL不包含角色，如果改变ALL则可能会引起业务不同，只能在此增加判断 modify by yirf
			if ((orgFlag & ORG_TYPE_ROLE) == ORG_TYPE_ROLE) {
                filter = StringUtil.linkString(filter, " or ", orgProperty
                        + ".fdOrgType=" + ORG_TYPE_ROLE);
            }
			m_where = StringUtil.isNull(filter) ? null : "(" + filter + ")";
		} else if (orgFlag == ORG_TYPE_ALL) {
			// 原ALL中不包含角色，如果参数为ALL应排除角色 modify by yirf
			filter = orgProperty + ".fdOrgType!=" + ORG_TYPE_ROLE;
			m_where = StringUtil.isNull(filter) ? null : "(" + filter + ")";
		}

		//由于这个方法无法做到参数化信息向外传递，所以这里特殊处理一下，通过Dialect.toBooleanValueString来做hardcode
		orgFlag = rtnType & ORG_FLAG_AVAILABLEALL;
		if (orgFlag == 0) {
            orgFlag = ORG_FLAG_AVAILABLEDEFAULT;
        }
		if (orgFlag != ORG_FLAG_AVAILABLEALL) {
			if (orgFlag == ORG_FLAG_AVAILABLENO){
				filter = orgProperty + ".fdIsAvailable = "+toBooleanValueString(false);
			}else{
				filter = orgProperty + ".fdIsAvailable = "+toBooleanValueString(true);
			}
			m_where = StringUtil.linkString(m_where, " and ", filter);
		}

		orgFlag = rtnType & ORG_FLAG_BUSINESSALL;
		if (orgFlag == 0) {
            orgFlag = ORG_FLAG_BUSINESSDEFAULT;
        }
		if (orgFlag != ORG_FLAG_BUSINESSALL) {
			if (orgFlag == ORG_FLAG_BUSINESSNO){
				filter = orgProperty + ".fdIsBusiness = "+toBooleanValueString(false);
			}
			else{
				filter = orgProperty + ".fdIsBusiness = "+toBooleanValueString(true);
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
	public static String buildAllChildrenWhereBlock(SysOrgElement element,
			String whereBlock, String orgProperty) {
//		return StringUtil.linkString(StringUtil.isNull(whereBlock) ? null : "("
//				+ whereBlock + ")", " and ", "substring(" + orgProperty
//				+ ".fdHierarchyId,1," + element.getFdHierarchyId().length()
//				+ ")='" + element.getFdHierarchyId() + "'");
		return StringUtil.linkString(StringUtil.isNull(whereBlock) ? null : "("
				+ whereBlock + ")", " and ", orgProperty
				+ ".fdHierarchyId like '" + element.getFdHierarchyId() + "%'");
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
			SysOrgElement element = (SysOrgElement) orgElements.get(i);
			int orgType = element.getFdOrgType().intValue();
			if (orgType == ORG_TYPE_GROUP || orgType == ORG_TYPE_POST) {
                throw new KmssRuntimeException(
                        new KmssMessage(
                                "sys-organization:SysOrgHQLUtil.error.cannotincludegroupandpost"));
            } else if (orgType == ORG_TYPE_PERSON) {
                personIds.append(",'" + element.getFdId() + "'");
            } else {
                hierarchyIds.add(((SysOrgElement) orgElements.get(i))
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
//			whereBf.append(" or substring(" + orgProperty + ".fdHierarchyId,1,"
//					+ hId.length() + ")='" + hId + "'");
			whereBf.append(" or " + orgProperty + ".fdHierarchyId like '" + hId + "%'");
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

	private static Dialect cachedDialect = null;
	private static Dialect getCurDialect(){
		if(cachedDialect == null){
			KmssHibernateLocalSessionFactoryBean bean  =
					(KmssHibernateLocalSessionFactoryBean) SpringBeanUtil.getBean("&sessionFactory");
			Configuration configuration =   bean.getConfiguration();
			cachedDialect = Dialect.getDialect(configuration.getProperties());
		}
		return cachedDialect;
	}
	/**
	 * 获取boolean值对应的sql、hql语句中使用的字符串表达，某些数据库使用0|1，某些数据库使用true|false
	 * @param value
	 * @return
	 */
	public static String toBooleanValueString(boolean value){
		if(cachedDialect==null){
			getCurDialect();
		}
		return cachedDialect.toBooleanValueString(value);
	}
}
