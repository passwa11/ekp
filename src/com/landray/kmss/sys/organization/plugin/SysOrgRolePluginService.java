package com.landray.kmss.sys.organization.plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;

import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.sys.organization.model.SysOrgRoleLine;
import com.landray.kmss.sys.organization.model.SysOrgRoleLineDefaultRole;
import com.landray.kmss.sys.organization.service.ISysOrgRoleLineService;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 角色线计算
 * 
 * @author 叶中奇
 * @version 创建时间：2008-12-11 下午04:35:26
 */
public class SysOrgRolePluginService implements ISysOrgRolePlugin {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgRolePluginService.class);

	private ISysOrgRoleLineService sysOrgRoleLineService;

	public void setSysOrgRoleLineService(
			ISysOrgRoleLineService sysOrgRoleLineService) {
		this.sysOrgRoleLineService = sysOrgRoleLineService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<SysOrgElement> parse(SysOrgRolePluginContext context)
			throws Exception {
		List<SysOrgElement> rtnList = new ArrayList<SysOrgElement>();
		if (context == null) {
            return rtnList;
        }
		SysOrgElement element = ((ISysOrgCoreService) SpringBeanUtil
				.getBean("sysOrgCoreService")).format(context.getBaseElement());
		SysOrgRole role = context.getRole();
		SysOrgRoleConf conf = role.getFdRoleConf();
		if (role.getFdIsAvailable() != null && !role.getFdIsAvailable()
				|| conf != null && conf.getFdIsAvailable() != null
				&& !conf.getFdIsAvailable()) {
			throw new KmssRuntimeException(new KmssMessage(
					"sysOrgRole.error.notAvailable", role.getFdName()));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("开始计算角色线：role=" + role.getFdName() + ", element="
					+ element.getFdName());
		}

		boolean deptFirst = "1".equals(role.getParameter("location"));
		SysOrgRoleLine roleLine = getRoleLine(element, role.getFdRoleConf()
				.getFdId(), deptFirst);
		if (roleLine == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("找不到角色线对应的成员");
			}
			return rtnList;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("找到角色线对应的成员：" + roleLine.getDisName());
			}
		}
		int type = Integer.parseInt(role.getParameter("type"));
		int lv = Integer.parseInt(role.getParameter("level"));
		boolean includeme = false;
		if ("true".equals(role.getParameter("includeme"))
				&& roleLine.getHbmChildren() != null
				&& roleLine.getHbmChildren().size() != 0) {
			// 包括提交人自己、该用户不属于角色线成员的最底层（属于领导）
			includeme = true;
			if (lv < 0) {
				// 仅查找上级时生效
				lv += 1;
			}
		}
		switch (type) {
		case 0:
			// 单一领导
			addRoleLine(rtnList, getRoleLine(roleLine, lv), element);
			break;
		case 1:
			// 多个领导
			int lv_e = Integer.parseInt(role.getParameter("end"));
			if (includeme) {
				if (lv_e < 0) {
					// 仅查找上级时生效
					lv_e += 1;
				}
			}
			String[] ids = roleLine.getFdHierarchyId().substring(1).split(
					BaseTreeConstant.HIERARCHY_ID_SPLIT);
			if (lv > 0 && lv_e > 0) {
				ids = (String[]) ArrayUtils.remove(ids, ids.length - 1);
			}

			int length = ids.length;
			// 获取开始和结束的索引号
			int i1 = getIndex(lv, length);
			int i2 = getIndex(lv_e, length);
			if (lv * lv_e < 0) {
				// 开始层级和结束层级不同方向，若出现交错现象，则不添加
				if (Math.abs(lv) + Math.abs(lv_e) > length) {
					break;
				}
				// 到这里，i1和i2肯定不会越界
			} else {
				// 开始层级和结束层级同方向，若双边越界，则不添加
				if (i1 < 0 && i2 < 0 || i1 >= length && i2 >= length) {
					break;
				}
				// 处理越界情况
				if (i1 < 0) {
					i1 = 0;
				} else if (i1 >= length) {
					i1 = length - 1;
				}
				if (i2 < 0) {
					i2 = 0;
				} else if (i2 >= length) {
					i2 = length - 1;
				}
			}
			// 注意：多个领导的返回是有序的
			int step = i1 > i2 ? -1 : 1;
			for (int i = i1; i1 > i2 ? i >= i2 : i <= i2; i += step) {
				addRoleLine(rtnList, (SysOrgRoleLine) sysOrgRoleLineService
						.findByPrimaryKey(ids[i]), element);
			}
			break;
		case 2:
			// 直接下属
		case 3:
			// 所有下属
			roleLine = getRoleLine(roleLine, lv);
			if (roleLine != null) {
				String hql = "select distinct sysOrgRoleLine.sysOrgRoleMember from "
						+ SysOrgRoleLine.class.getName()
						+ " sysOrgRoleLine where sysOrgRoleLine.sysOrgRoleMember is not null and sysOrgRoleLine.hbmParent.";
				if (type == 2) {
					hql += "fdId='" + roleLine.getFdId() + "'";
				} else {
					hql += "fdHierarchyId like '" + roleLine.getFdHierarchyId()
							+ "%'";
				}
				rtnList.addAll(sysOrgRoleLineService.getBaseDao()
						.getHibernateSession().createQuery(hql).list());
			}
			break;
		}
		// 增加根据返回值配置过滤数据 by fuyx 2010-10-16
		if (role.getFdRtnValue() != null && role.getFdRtnValue().length() > 0) {
			int value = Integer.parseInt(role.getFdRtnValue());
			for (Iterator<SysOrgElement> it = rtnList.iterator(); it.hasNext();) {
				SysOrgElement elem = it.next();
				if ((value & elem.getFdOrgType().intValue()) <= 0) {
					it.remove();
				}
			}
		}
		return rtnList;
	}

	/**
	 * 获取角色线中的成员
	 * 
	 * @param element
	 * @param confId
	 * @param deptFirst
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private SysOrgRoleLine getRoleLine(SysOrgElement element, String confId,
			boolean deptFirst) throws Exception {
		// 注意：ids的顺序是按优先度排序的
		List<String> ids = new ArrayList<String>();
		// 若部门优先，则先取部门ID，从最近的排到最远的
		if (deptFirst) {
			String[] deptIds = element.getFdHierarchyId().substring(1).split(
					BaseTreeConstant.HIERARCHY_ID_SPLIT);
			for (int i = deptIds.length - 2; i >= 0; i--) {
				ids.add(deptIds[i]);
			}
		}
		// 传递的element优先度最高
		ids.add(element.getFdId());
		int orgType = element.getFdOrgType().intValue();
		if (orgType == SysOrgConstant.ORG_TYPE_PERSON) {
			// 其次是岗位
			List<SysOrgElement> posts = element.getFdPosts();
			List postIds = new ArrayList<String>();
			for (int i = 0; i < posts.size(); i++) {
				postIds.add(posts.get(i).getFdId());
			}
			if (postIds.size() > 1) {
				// 岗位列表大于1，则查找默认岗位
				String hql = "select fdPost.fdId from "
						+ SysOrgRoleLineDefaultRole.class.getName()
						+ " where sysOrgRoleConf.fdId='" + confId
						+ "' and fdPerson.fdId='" + element.getFdId() + "'";
				List<String> result = sysOrgRoleLineService.getBaseDao()
						.getHibernateSession().createQuery(hql).list();
				if (!result.isEmpty()) {
					String defaultPostId = result.get(0);
					if (postIds.contains(defaultPostId)) {
						postIds.remove(defaultPostId);
						ids.add(defaultPostId);
					}
				}
			}
			ids.addAll(postIds);
		} else if (orgType == SysOrgConstant.ORG_TYPE_POST) {
			// 若岗位里面只有一个人，则这个人也加入搜索范围
			List<SysOrgElement> persons = element.getFdPersons();
			if (persons.size() == 1) {
                ids.add(persons.get(0).getFdId());
            }
		}
		// 若非部门优先，最后是部门id，从最近的排到最远的
		if (!deptFirst) {
			String[] deptIds = element.getFdHierarchyId().substring(1).split(
					BaseTreeConstant.HIERARCHY_ID_SPLIT);
			for (int i = deptIds.length - 2; i >= 0; i--) {
				ids.add(deptIds[i]);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("搜索角色线位置，ID顺序为：" + ids);
		}
		// 查找组织架构ID和角色线ID
		String whereBlock = HQLUtil.buildLogicIN(
				"sysOrgRoleLine.sysOrgRoleMember.fdId", ids);
		whereBlock += " and sysOrgRoleLine.sysOrgRoleConf.fdId='" + confId
				+ "'";
		List<Object[]> result = sysOrgRoleLineService.findValue(
				"sysOrgRoleLine.fdId, sysOrgRoleLine.sysOrgRoleMember.fdId",
				whereBlock, null);
		if (!result.isEmpty()) {
			// 根据优先度搜索返回值
			for (int i = 0; i < ids.size(); i++) {
				String id = ids.get(i);
				for (int j = 0; j < result.size(); j++) {
					Object[] values = result.get(j);
					if (id.equals(values[1])) {
						SysOrgRoleLine roleLine = (SysOrgRoleLine) sysOrgRoleLineService
								.findByPrimaryKey(values[0].toString());
						
						if(roleLine!=null){
							//如果不包含下级，则只计算成员为该部门下的人员、岗位（部门、机构需例外处理），忽略更下级的人员和岗位
							if(!roleLine.getFdHasChild() && roleLine.getSysOrgRoleMember()!=null){
								if(SysOrgConstant.ORG_TYPE_ORG == element.getFdOrgType() || SysOrgConstant.ORG_TYPE_DEPT == element.getFdOrgType()){
									if(!roleLine.getSysOrgRoleMember().getFdId().equals(element.getFdId())){
										continue;
									}
								}else{
									if(!roleLine.getSysOrgRoleMember().getFdId().equals(element.getFdParent().getFdId())){
										continue;
									}
								}
							}	
						}
						return roleLine;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取相对于roleLine的lv层级的的角色成员
	 * 
	 * @param roleLine
	 * @param lv
	 * @return
	 * @throws Exception
	 */
	private SysOrgRoleLine getRoleLine(SysOrgRoleLine roleLine, int lv)
			throws Exception {
		String[] ids = roleLine.getFdHierarchyId().substring(1).split(
				BaseTreeConstant.HIERARCHY_ID_SPLIT);
		int index = getIndex(lv, ids.length);
		int end = ids.length - 1;
		if (lv > 0) {
			end = ids.length - 2;
		}

		if (index >= 0 && index <= end) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取角色线时，超过指定层级：" + lv);
			}
			return (SysOrgRoleLine) sysOrgRoleLineService
					.findByPrimaryKey(ids[index]);
		} else {
			return null;
		}
	}

	/**
	 * 获取lv对应的索引号
	 * 
	 * @param lv
	 * @param length
	 * @return
	 */
	private int getIndex(int lv, int length) {
		if (lv > 0) {
			return lv - 1;
		} else {
			return length + lv - 1;
		}
	}

	/**
	 * 添加一个角色线中的成员（如果这个成员是机构或者部门，则返回原来的值）
	 * 
	 * @param rtnList
	 * @param roleLine
	 * @param element
	 */
	private void addRoleLine(List<SysOrgElement> rtnList,
			SysOrgRoleLine roleLine, SysOrgElement element) {
		if (roleLine != null && roleLine.getSysOrgRoleMember() != null) {
			int orgType = roleLine.getSysOrgRoleMember().getFdOrgType();
			if (orgType == SysOrgConstant.ORG_TYPE_ORG
					|| orgType == SysOrgConstant.ORG_TYPE_DEPT) {
				rtnList.add(element);
			} else {
				rtnList.add(roleLine.getSysOrgRoleMember());
			}
		}
	}
}
