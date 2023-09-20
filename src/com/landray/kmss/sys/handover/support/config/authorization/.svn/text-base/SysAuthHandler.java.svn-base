package com.landray.kmss.sys.handover.support.config.authorization;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.authorization.model.SysAuthRole;
import com.landray.kmss.sys.authorization.model.SysAuthUra;
import com.landray.kmss.sys.authorization.service.ISysAuthRoleService;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverHandler;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLogDetail;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 全系统权限管理
 * 
 * @author 潘永辉 2017年11月22日
 *
 */
public class SysAuthHandler implements IHandoverHandler {

	protected ISysAuthRoleService roleService = null;

	public ISysAuthRoleService getRoleService() {
		if (roleService == null) {
            roleService = (ISysAuthRoleService) SpringBeanUtil.getBean("sysAuthRoleService");
        }
		return roleService;
	}

	@Override
	public synchronized void execute(HandoverExecuteContext context) throws Exception {
		SysOrgElement fromOrg = context.getFrom();
		SysOrgElement toOrg = context.getTo();
		List<String> ids = context.getSelectedRecordIds();

		SysAuthRole authRole = null;
		for (String roleId : ids) {
			authRole = (SysAuthRole) getRoleService().findByPrimaryKey(roleId, SysAuthRole.class, true);
			if (authRole != null) {
				// 需要交接的对象
				SysAuthUra ura = null;
				// 在此交接的权限中，是否有包含接收对象（确保交接后的组织机构不能有重复）
				boolean isRepeat = false;
				List<SysAuthUra> uras = authRole.getAuthUra();
				for (SysAuthUra _ura : uras) {
					String oid = _ura.getOrgElement().getFdId();
					if (oid.equals(fromOrg.getFdId())) {
                        ura = _ura;
                    }
					if (toOrg == null || oid.equals(toOrg.getFdId())) {
                        isRepeat = true;
                    }
				}
				// 如果没有找到交接的权限，可能是数据已更新
				if (ura == null) {
					addOrgErr(context, roleId);
					continue;
				}

				// 如果接收人已经存在了，就只需要删除交接人即可
				authRole.getAuthUra().remove(ura);
				if (!isRepeat) {
					// 接收人不存在，正常交接
					ura.setOrgElement(toOrg);
					authRole.getAuthUra().add(ura);
				}
				getRoleService().update(authRole);

				String[] info = getDescAndUrl(authRole);
				if (info == null) {
                    continue;
                }

				context.log(roleId, "com.landray.kmss.sys.authorization.model.SysAuthRole",
						info[0], info[1], null, SysHandoverConfigLogDetail.STATE_SUCC);
			}
		}

	}

	@Override
	public void search(HandoverSearchContext context) throws Exception {
		SysOrgElement sysOrgElement = context.getHandoverOrg();
		int total = 0;
		
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock(" inner join sysAuthRole.authUra as ura");
		hqlInfo.setWhereBlock("ura.orgElement.fdId= :element");
		hqlInfo.setParameter("element", sysOrgElement.getFdId());
		
		// 斩不区分分级权限
//		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "sysAuthRole.fdType = :type"));
//		hqlInfo.setParameter("type", 0);
		List<SysAuthRole> roles = getRoleService().findList(hqlInfo);
		
		// 排序
		Collections.sort(roles, new Comparator<SysAuthRole>() {
			@Override
			public int compare(SysAuthRole o1, SysAuthRole o2) {
				String a1 = o1.getFdAlias();
				if (a1 == null) {
                    a1 = "";
                }
				String a2 = o2.getFdAlias();
				if (a2 == null) {
                    a2 = "";
                }
				return a1.compareTo(a2);
			}
		});

		for (int i = 0; i < roles.size(); i++) {
			SysAuthRole role = roles.get(i);
			String[] info = getDescAndUrl(role);
			if (info == null) {
                continue;
            }
			
			context.addHandoverRecord(context.getModule() + ID_SPLIT + role.getFdId(), info[1], new String[] { info[0] });
			total++;
		}
		context.setTotal(total);
	}

	private String[] getDescAndUrl(SysAuthRole role) {
		String href = null;
		String desc = null;
		if (role.isSysRole()) {
			// 过滤无数据的角色
			String _text = ResourceUtil.getString(role.getFdName());
			if (StringUtil.isNull(_text)) {
				return null;
			}
			desc = _text;
			href = "/sys/authorization/sys_auth_sys_role/sysAuthSysRole.do" + "?method=view&fdId=" + role.getFdId();
		} else {
			String p_name = role.getSysAuthCategory() == null
					? ResourceUtil.getString("sysAuthCategory.other", "sys-authorization")
					: role.getSysAuthCategory().getFdName();
			desc = p_name + " >> [" + role.getFdName() + "]";
			href = "/sys/authorization/sys_auth_role/sysAuthRole.do" + "?method=view&fdId=" + role.getFdId();
		}
		return new String[] { desc, href };
	}

	
	/**
	 * 交接人已被更新err
	 * 
	 * @param id
	 * @param context
	 */
	private void addOrgErr(HandoverExecuteContext executeContext, String id) {
		executeContext.error(executeContext.getModule() + ID_SPLIT + id,
				ResourceUtil.getString("sysHandoverConfigMain.error", "sys-handover"));
	}
}
