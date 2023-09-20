package com.landray.kmss.sys.handover.support.config.organization;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverSearchContext;
import com.landray.kmss.sys.handover.interfaces.config.IHandoverHandler;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLogDetail;
import com.landray.kmss.sys.handover.support.util.HandModelUtil;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgOrg;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrgRoleLine;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgGroupService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleLineService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 组织架构handler
 * 
 * @author tanyouhao
 * @date 2014-7-23
 * 
 */
public class SysOrgHandler implements IHandoverHandler {


	/* 标识 */
	private static final String IDEN_POST = "POST";
	private static final String IDEN_GROUP = "GROUP";
	private static final String IDEN_LEADER = "LEADER";
	private static final String IDEN_SU_LEADER = "SU_LEADER";
	private static final String IDEN_LINE = "LINE";
	private static final String IDEN_ADMIN = "ADMIN";

	protected ISysOrgElementService sysOrgElementService = null;
	protected ISysOrgRoleLineService sysOrgRoleLineService = null;
	protected ISysOrgGroupService sysOrgGroupService = null;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
                    .getBean("sysOrgElementService");
        }
		return sysOrgElementService;
	}

	public ISysOrgRoleLineService getSysOrgRoleLineService() {
		if (sysOrgRoleLineService == null) {
            sysOrgRoleLineService = (ISysOrgRoleLineService) SpringBeanUtil
                    .getBean("sysOrgRoleLineService");
        }
		return sysOrgRoleLineService;
	}

	public ISysOrgGroupService getSysOrgGroupService() {
		if (sysOrgGroupService == null) {
            sysOrgGroupService = (ISysOrgGroupService) SpringBeanUtil
                    .getBean("sysOrgGroupService");
        }
		return sysOrgGroupService;
	}

	@Override
	public synchronized void execute(HandoverExecuteContext context) throws Exception {
		SysOrgElement fromOrg = context.getFrom();
		SysOrgElement toOrg = context.getTo();
		List<String> ids = context.getSelectedRecordIds();
		for (int i = 0; i < ids.size(); i++) {
			String id = ids.get(i).trim();
			String fdId = "";
			if (id.indexOf(IDEN_POST) > -1) {// 岗位
				fdId = id.substring(IDEN_POST.length());
				executePost(fromOrg, toOrg, context, fdId);
			} else if (id.indexOf(IDEN_GROUP) > -1) {// 群组
				fdId = id.substring(IDEN_GROUP.length());
				executeGroup(fromOrg, toOrg, context, fdId);
			} else if (id.indexOf(IDEN_SU_LEADER) > -1) {// 上级领导
				fdId = id.substring(IDEN_SU_LEADER.length());
				executeSuperLeader(fromOrg, toOrg, context, fdId);
			} else if (id.indexOf(IDEN_LEADER) > -1) {// 领导
				fdId = id.substring(IDEN_LEADER.length());
				executeLeader(fromOrg, toOrg, context, fdId);
			} else if (id.indexOf(IDEN_LINE) > -1) {// 角色线
				fdId = id.substring(IDEN_LINE.length());
				executeOrgRoleLine(fromOrg, toOrg, context, fdId);
			} else if (id.indexOf(IDEN_ADMIN) > -1) {// 管理员
				fdId = id.substring(IDEN_ADMIN.length());
				executeAdmin(fromOrg, toOrg, context, fdId);
			}
		}
	}

	@Override
	public void search(HandoverSearchContext context) throws Exception {
		SysOrgElement sysOrgElement = context.getHandoverOrg();
		boolean isPerson = sysOrgElement.getFdOrgType().equals(
				SysOrgConstant.ORG_TYPE_PERSON);
		int total = 0;
		if (isPerson) {
			// 岗位信息
			List<SysOrgElement> posts = sysOrgElement.getHbmPosts();
			for (SysOrgElement post : posts) {
				String desc = "";
				String postPersonNames = "";
				List<SysOrgElement> postPersons = post.getHbmPersons();
				for (SysOrgElement orgElement : postPersons) {
					postPersonNames += "," + orgElement.getFdName();
				}
				desc += getDesc(post) + "(" + postPersonNames.substring(1)
						+ ")";
				context.addHandoverRecord(getIdWithPrefix(context, IDEN_POST,
						post.getFdId()), getUrl(post), new String[] { desc });
				total++;
			}
		}
		// 群组信息
		List<SysOrgGroup> groups = sysOrgElement.getFdGroups();
		for (SysOrgGroup group : groups) {
			String desc = "";
			String groupPersonNames = "";
			List<SysOrgElement> groupPersons = group.getHbmMembers();
			for (SysOrgElement orgElement : groupPersons) {
				groupPersonNames += "," + orgElement.getFdName();
			}
			desc += getDesc(group) + "(" + groupPersonNames.substring(1) + ")";
			context.addHandoverRecord(getIdWithPrefix(context, IDEN_GROUP,
					group.getFdId()), getUrl(group), new String[] { desc });
			total++;
		}
		// 领导信息
		total += searchOrgLeader(context);
		// 上级领导信息
		total += searchOrgSuperLeader(context);
		// 角色先信息
		total += searchOrgRoleLine(context);
		// 管理员信息
		total += searchAdmin(context);
		context.setTotal(total);
	}

	/**
	 * 更新岗位信息
	 * 
	 * @param context
	 * @param fdId
	 * @throws Exception
	 */
	public void executePost(SysOrgElement fromOrg, SysOrgElement toOrg,
			HandoverExecuteContext context, String fdId) throws Exception {
		// 人的信息不可交接为岗位
		if(toOrg!=null){
			if (toOrg.getFdOrgType() == SysOrgConstant.ORG_TYPE_POST
					&& fromOrg.getFdOrgType() != SysOrgConstant.ORG_TYPE_POST) {
				// 记录信息
				String msg = ResourceUtil.getString(
						"sysHandoverConfigMain.skip1", "sys-handover", null,
						new Object[] { ResourceUtil.getString("sys-handover-support-config-organization:sysHandoverConfigHandler.post"),
								fromOrg.getFdName(), toOrg.getFdName() });
				context.info(context.getModule() + ID_SPLIT + IDEN_POST + fdId, msg);
				return;
			}
			// 跳过接收人为：部门
			if (toOrg.getFdOrgType() == SysOrgConstant.ORG_TYPE_DEPT) {
				// 记录信息
				String msg = ResourceUtil.getString(
						"sysHandoverConfigMain.skip", "sys-handover", null,
						new Object[] { ResourceUtil.getString("sys-handover-support-config-organization:sysHandoverConfigHandler.post"),
								fromOrg.getFdName(), toOrg.getFdName() });
				context.info(context.getModule() + ID_SPLIT + IDEN_POST + fdId, msg);
				return;
			}
		}
		SysOrgElement postElement = (SysOrgElement) getSysOrgElementService()
				.findByPrimaryKey(fdId);
		List persons = postElement.getHbmPersons();
		
		//处理标记
		boolean isExecutedFlag = true;
		for (int i = 0; i < persons.size(); i++) {
			// 更新岗位人信息,若接收人在岗位成员中，则删除交接人
			if (((SysOrgElement) persons.get(i)).getFdId().equals(
					fromOrg.getFdId())) {
				isExecutedFlag = false;
				if (!persons.contains(toOrg)&&toOrg!=null) {
					persons.set(i, toOrg);
				} else {
					persons.remove(i);
				}
				break;
			}
		}
		if(isExecutedFlag){
			addOrgErr(context,IDEN_POST,fdId);
			return;
		}
		
		postElement.setHbmPersons(persons);
		// 更新岗位信息
		getSysOrgElementService().update(postElement);

		String newMembers = "";
		if(persons.size()>0){
			for (int i = 0; i < persons.size(); i++) {
				SysOrgElement orgElement = (SysOrgElement) persons.get(i);
				newMembers += "," + orgElement.getFdName();
			}
		}
		
		// 记录详细日志
		String desc = getDesc(postElement);
		if (StringUtil.isNotNull(newMembers)) {
			desc += "(" + newMembers.substring(1) + ")";
		}
		context.log(fdId, getModelName(postElement), desc, getUrl(postElement), null, SysHandoverConfigLogDetail.STATE_SUCC);
	}

	/**
	 * 更新群组信息
	 * 
	 * @param context
	 * @param fdId
	 * @throws Exception
	 */
	public void executeGroup(SysOrgElement fromOrg, SysOrgElement toOrg,
			HandoverExecuteContext context, String fdId) throws Exception {
		SysOrgGroup orgGroup = (SysOrgGroup) getSysOrgGroupService()
				.findByPrimaryKey(fdId);
		List persons = orgGroup.getFdMembers();
		//处理标记
		boolean isExecutedFlag = true;
		for (int i = 0; i < persons.size(); i++) {
			// 更新群组信息，若接收人在群组成员中，则删除交接人
			if (((SysOrgElement) persons.get(i)).getFdId().equals(
					fromOrg.getFdId())) {
				isExecutedFlag = false;
				if (!persons.contains(toOrg)&&toOrg!=null) {
					persons.set(i, toOrg);
				} else {
					persons.remove(i);
				}
				break;
			}
		}
		if(isExecutedFlag){
			 addOrgErr(context,IDEN_GROUP,fdId);
			 return;
		}
		orgGroup.setFdMembers(persons);
		// 更新群组信息
		getSysOrgGroupService().update(orgGroup);

		String newMembers = "";
		if (persons.size() > 0) {
			for (int i = 0; i < persons.size(); i++) {
				SysOrgElement orgElement = (SysOrgElement) persons.get(i);
				newMembers += "," + orgElement.getFdName();
			}
		}
		// 记录详细日志
		String desc = getDesc(orgGroup);
		if (StringUtil.isNotNull(newMembers)) {
			desc += "(" + newMembers.substring(1) + ")";
		}
		context.log(fdId, getModelName(orgGroup), desc, getUrl(orgGroup), null, SysHandoverConfigLogDetail.STATE_SUCC);
	}

	/**
	 * 获取领导信息
	 * 
	 * @param context
	 * @throws Exception
	 */
	private long searchOrgLeader(HandoverSearchContext context)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOrgElement.hbmThisLeader.fdId =:fdId");
		hqlInfo.setParameter("fdId", context.getHandoverOrg().getFdId());
		List orgList = getSysOrgElementService().findList(hqlInfo);
		if (orgList.size() == 0) {
			return 0;
		}
		for (int i = 0; i < orgList.size(); i++) {
			SysOrgElement sysOrgElement = (SysOrgElement) orgList.get(i);
			String desc = getDesc(sysOrgElement) + CONN_SYM
					+ getMessageLeader();
			context.addHandoverRecord(getIdWithPrefix(context, IDEN_LEADER,
					sysOrgElement.getFdId()), getUrl(sysOrgElement),
					new String[] { desc });
		}
		return orgList.size();
	}

	/**
	 * 更新员工领导信息
	 * 
	 * @param context
	 * @param fdId
	 * @throws Exception
	 */
	public void executeLeader(SysOrgElement fromOrg, SysOrgElement toOrg,
			HandoverExecuteContext context, String fdId) throws Exception {
		SysOrgElement sysOrgElement = (SysOrgElement) getSysOrgElementService()
				.findByPrimaryKey(fdId);
		
		if (toOrg != null) {
			// 跳过接收人为：部门
			if (toOrg.getFdOrgType() == SysOrgConstant.ORG_TYPE_DEPT) {
				// 记录信息
				String msg = ResourceUtil.getString(
						"sysHandoverConfigMain.skip", "sys-handover", null,
						new Object[] { ResourceUtil.getString("sys-handover-support-config-organization:sysHandoverConfigHandler.leader"),
								fromOrg.getFdName(), toOrg.getFdName() });
				context.info(context.getModule() + ID_SPLIT + IDEN_LEADER + fdId, msg);
				return;
			}
		}

		//处理标记
		if(!sysOrgElement.getHbmThisLeader().getFdId().equals(fromOrg.getFdId())){
			addOrgErr(context,IDEN_LEADER,fdId);
			return;
		}
		sysOrgElement.setHbmThisLeader(toOrg);
		// 更新
		getSysOrgGroupService().update(sysOrgElement);
		// 记录详细日志
		String desc = getDesc(sysOrgElement) + CONN_SYM + getMessageLeader();
		context.log(fdId, getModelName(sysOrgElement), desc, getUrl(sysOrgElement), null, SysHandoverConfigLogDetail.STATE_SUCC);
	}

	/**
	 * 获取上级领导信息
	 * 
	 * @param context
	 * @throws Exception
	 */
	private long searchOrgSuperLeader(HandoverSearchContext context)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOrgElement.hbmSuperLeader.fdId =:fdId");
		hqlInfo.setParameter("fdId", context.getHandoverOrg().getFdId());
		List orgList = getSysOrgElementService().findList(hqlInfo);
		if (orgList.size() == 0) {
			return 0;
		}
		for (int i = 0; i < orgList.size(); i++) {
			SysOrgElement sysOrgElement = (SysOrgElement) orgList.get(i);
			String desc = getDesc(sysOrgElement) + CONN_SYM
					+ getMessageSuperLeader();
			context.addHandoverRecord(getIdWithPrefix(context, IDEN_SU_LEADER,
					sysOrgElement.getFdId()), getUrl(sysOrgElement),
					new String[] { desc });
		}
		return orgList.size();
	}

	/**
	 * 更新上级领导信息
	 * 
	 * @param context
	 * @param fdId
	 * @throws Exception
	 */
	public void executeSuperLeader(SysOrgElement fromOrg, SysOrgElement toOrg,
			HandoverExecuteContext context, String fdId) throws Exception {
		SysOrgElement sysOrgElement = (SysOrgElement) getSysOrgElementService()
				.findByPrimaryKey(fdId);
		if (toOrg != null) {
			// 跳过接收人为：部门
			if (toOrg.getFdOrgType() == SysOrgConstant.ORG_TYPE_DEPT) {
				// 记录信息
				String msg = ResourceUtil.getString(
						"sysHandoverConfigMain.skip", "sys-handover", null,
						new Object[] { ResourceUtil.getString("sys-handover-support-config-organization:sysHandoverConfigHandler.su_leader"),
								fromOrg.getFdName(), toOrg.getFdName() });
				context.info(context.getModule() + ID_SPLIT + IDEN_SU_LEADER + fdId, msg);
				return;
			}
		}
		//处理标记
		if(!sysOrgElement.getHbmSuperLeader().getFdId().equals(fromOrg.getFdId())){
			addOrgErr(context,IDEN_SU_LEADER,fdId);
			return;
		}

		sysOrgElement.setHbmSuperLeader(toOrg);
		// 更新
		getSysOrgGroupService().update(sysOrgElement);
		// 记录详细日志
		String desc = getDesc(sysOrgElement) + CONN_SYM
				+ getMessageSuperLeader();
		context.log(fdId, getModelName(sysOrgElement), desc, getUrl(sysOrgElement), null, SysHandoverConfigLogDetail.STATE_SUCC);
	}

	/**
	 * 获取角色线
	 * 
	 * @param context
	 */
	private long searchOrgRoleLine(HandoverSearchContext context)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOrgRoleLine.sysOrgRoleMember.fdId =:fdId");
		hqlInfo.setParameter("fdId", context.getHandoverOrg().getFdId());
		List orgList = getSysOrgRoleLineService().findList(hqlInfo);
		if (orgList.size() == 0) {
			return 0;
		}
		for (int i = 0; i < orgList.size(); i++) {
			SysOrgRoleLine sysOrgRoleLine = (SysOrgRoleLine) orgList.get(i);
			String desc = getDesc(sysOrgRoleLine.getSysOrgRoleConf())
					+ getParentRoleMember(sysOrgRoleLine) + CONN_SYM
					+ sysOrgRoleLine.getDisName();
			String url = "/sys/organization/sys_org_role_line/sysOrgRoleLine.do?method=roleTree&fdConfId="
					+ sysOrgRoleLine.getSysOrgRoleConf().getFdId()+"&orgId="+context.getHandoverOrg().getFdId();
			context.addHandoverRecord(getIdWithPrefix(context, IDEN_LINE,
					sysOrgRoleLine.getFdId()), url, new String[] { desc });
		}
		return orgList.size();
	}

	private String getParentRoleMember(SysOrgRoleLine sysOrgRoleLine)
			throws Exception {
		String memberName = "";
		if (sysOrgRoleLine.getFdParent() != null) {
			String fdHierarchyId = sysOrgRoleLine.getFdHierarchyId();
			String[] ids = fdHierarchyId.split("x");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				if (StringUtil.isNotNull(id)
						&& !sysOrgRoleLine.getFdId().equals(id)) {
					SysOrgRoleLine roleLine = (SysOrgRoleLine) getSysOrgRoleLineService()
							.findByPrimaryKey(id);
					memberName += CONN_SYM + roleLine.getDisName();
				}
			}
		}
		return memberName;
	}

	/**
	 * 更新角色线信息
	 * 
	 * @param fromOrg
	 * @param toOrg
	 * @param context
	 * @param fdId
	 * @throws Exception
	 */
	public void executeOrgRoleLine(SysOrgElement fromOrg, SysOrgElement toOrg,
			HandoverExecuteContext context, String fdId) throws Exception {
		// 接收人不能为空
		if (toOrg == null) {
			throw new KmssException(new KmssMessage(
					"sys-handover:sysHandoverConfigMain.fdToName.line.nonull"));
		}

		SysOrgRoleLine sysOrgRoleLine = (SysOrgRoleLine) getSysOrgRoleLineService()
				.findByPrimaryKey(fdId);

		// 跳过接收人为：部门
		if (toOrg.getFdOrgType() == SysOrgConstant.ORG_TYPE_DEPT
				&& !sysOrgRoleLine.getHbmChildren().isEmpty()) {
			// 记录信息
			String msg = ResourceUtil.getString(
					"sysHandoverConfigMain.skip2", "sys-handover", null,
					new Object[] { fromOrg.getFdName(), toOrg.getFdName() });
			context.info(context.getModule() + ID_SPLIT + IDEN_LINE + fdId, msg);
			return;
		}

		// 处理标记
		if (!sysOrgRoleLine.getSysOrgRoleMember().getFdId().equals(
				fromOrg.getFdId())) {
			addOrgErr(context,IDEN_LINE,fdId);
			return;
		}
		
		// 检查被交接人是否已在该角色线上存在
		StringBuffer whereBlock = new StringBuffer();
		whereBlock.append("sysOrgRoleLine.sysOrgRoleConf.fdId='").append(
				sysOrgRoleLine.getSysOrgRoleConf().getFdId()).append("'");
		whereBlock.append(" and sysOrgRoleLine.sysOrgRoleMember.fdId='")
				.append(toOrg.getFdId()).append("'");
		List findValue = getSysOrgRoleLineService()
				.findValue("sysOrgRoleLine.fdId", whereBlock.toString(), null);
		if (!ArrayUtil.isEmpty(findValue)) {
			context.error(context.getModule() + ID_SPLIT + IDEN_LINE
					+ fdId, ResourceUtil.getString("sysHandoverConfigMain.error2", "sys-handover"));
			return;
		}

		sysOrgRoleLine.setSysOrgRoleMember(toOrg);
		// 更新
		getSysOrgRoleLineService().update(sysOrgRoleLine);
		// 记录详细日志
		String desc = getDesc(sysOrgRoleLine.getSysOrgRoleConf())
				+ getParentRoleMember(sysOrgRoleLine) + CONN_SYM
				+ sysOrgRoleLine.getDisName();
		// 角色线数据字典没有配置url
		String url = "/sys/organization/sys_org_role_line/sysOrgRoleLine.do?method=roleTree&fdConfId="
				+ sysOrgRoleLine.getSysOrgRoleConf().getFdId()+"&orgId="+toOrg.getFdId();
		context.log(fdId, getModelName(sysOrgRoleLine), desc, url, null, SysHandoverConfigLogDetail.STATE_SUCC);
	}

	/**
	 * 获取管理员信息
	 * 
	 * @param context
	 */
	private long searchAdmin(HandoverSearchContext context) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOrgElement.authElementAdmins.fdId =:fdId");
		hqlInfo.setParameter("fdId", context.getHandoverOrg().getFdId());
		List orgList = getSysOrgElementService().findList(hqlInfo);
		if (orgList.size() == 0) {
			return 0;
		}
		for (int i = 0; i < orgList.size(); i++) {
			SysOrgElement sysOrgElement = (SysOrgElement) orgList.get(i);
			String adminNames = "";
			List<SysOrgElement> admins = sysOrgElement.getAuthElementAdmins();
			for (int j = 0; j < admins.size(); j++) {
				adminNames += "," + ((SysOrgElement) admins.get(j)).getFdName();
			}
			String desc = getDesc(sysOrgElement) + CONN_SYM + getMessageAdmin()
					+ "(" + adminNames.substring(1) + ")";
			context.addHandoverRecord(getIdWithPrefix(context, IDEN_ADMIN,
					sysOrgElement.getFdId()), getUrl(sysOrgElement),
					new String[] { desc });
		}
		return orgList.size();
	}

	/**
	 * 更新管理员信息
	 * 
	 * @param fromOrg
	 * @param toOrg
	 * @param context
	 * @param fdId
	 * @throws Exception
	 */
	public void executeAdmin(SysOrgElement fromOrg, SysOrgElement toOrg,
			HandoverExecuteContext context, String fdId) throws Exception {
		SysOrgElement orgElement = (SysOrgElement) getSysOrgElementService()
				.findByPrimaryKey(fdId);
		if (toOrg != null) {
			// 跳过接收人为：部门
			if (toOrg.getFdOrgType() == SysOrgConstant.ORG_TYPE_DEPT) {
				// 记录信息
				String msg = ResourceUtil.getString(
						"sysHandoverConfigMain.skip", "sys-handover", null,
						new Object[] { ResourceUtil.getString("sys-handover-support-config-organization:sysHandoverConfigHandler.admin"),
								fromOrg.getFdName(), toOrg.getFdName() });
				context.info(context.getModule() + ID_SPLIT + IDEN_ADMIN + fdId, msg);
				return;
			}
		}

		List members = orgElement.getAuthElementAdmins();
		//处理标记
		boolean isExecutedFlag = true;		
		for (int i = 0; i < members.size(); i++) {
			// 更新管理员信息,若交接人在管理员列表中则删除，反之更新
			if (((SysOrgElement) members.get(i)).getFdId().equals(
					fromOrg.getFdId())) {
				isExecutedFlag = false;
				if (!members.contains(toOrg)) {
					members.set(i, toOrg);
				} else {
					members.remove(i);
				}
				break;
			}
		}
		if(isExecutedFlag){
			addOrgErr(context,IDEN_LEADER,fdId);
			return;
		}
		// 更新
		getSysOrgElementService().update(orgElement);
		String newAdmins = "";
		for (int i = 0; i < members.size(); i++) {
			SysOrgElement orgAdmin = (SysOrgElement) members.get(i);
			newAdmins += "," + orgAdmin.getFdName();
		}
		// 记录详细日志
		String desc = getDesc(orgElement) + CONN_SYM + "("
				+ newAdmins.substring(1) + ")";
		context.log(fdId, getModelName(orgElement), desc, getUrl(orgElement), null, SysHandoverConfigLogDetail.STATE_SUCC);
	}

	/**
	 * 获取modelName
	 * 
	 * @param model
	 * @return
	 */
	private String getModelName(IBaseModel model) {
		return ModelUtil.getModelClassName(model);
	}

	/**
	 * 获取url
	 * 
	 * @param model
	 * @return
	 */
	private String getUrl(IBaseModel model) {
		SysDictModel dictModel = getSysDictModel(model);
		String url = dictModel.getUrl();
		if (StringUtil.isNotNull(url)) {
			url = url.replace("${fdId}", model.getFdId());
		}
		return url;
	}
	
	/**
	 * 获取有前缀的id，如
	 * 岗位：handOverSysOrganization;;POST148ed9b496edee5f655d7ef45c294e02
	 * 
	 * @param context
	 * @param sign
	 * @param id
	 * @return
	 */
	private String getIdWithPrefix(HandoverSearchContext context, String sign,
			String id) {
		return context.getModule() + ID_SPLIT + sign + id;
	}

	/**
	 * 获取描述信息
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	private String getDesc(IBaseModel model) {
		SysDictModel dictModel = getSysDictModel(model);
		String fdName = HandModelUtil.getDocSubject(dictModel, model);
		return ResourceUtil.getString(dictModel.getMessageKey()) + "：" + fdName;
	}

	private SysDictModel getSysDictModel(IBaseModel model) {
		if (!(model instanceof SysOrgElement)) {
			return SysDataDict.getInstance().getModel(
					ModelUtil.getModelClassName(model));
		}
		SysOrgElement orgElement = (SysOrgElement) model;
		switch (orgElement.getFdOrgType()) {
		case SysOrgElement.ORG_TYPE_ORG:
			model = new SysOrgOrg();
			break;
		case SysOrgElement.ORG_TYPE_DEPT:
			model = new SysOrgDept();
			break;
		case SysOrgElement.ORG_TYPE_POST:
			model = new SysOrgPost();
			break;
		default:
			break;
		}
		return SysDataDict.getInstance().getModel(
				ModelUtil.getModelClassName(model));
	}

	private String getMessageAdmin() {
		return ResourceUtil.getString("sysOrgElement.authElementAdmins",
				"sys-organization");
	}

	private String getMessageLeader() {
		return ResourceUtil.getString("organization.hbmThisLeader",
				"sys-organization");
	}

	private String getMessageSuperLeader() {
		return ResourceUtil.getString("organization.hbmSuperLeader",
				"sys-organization");
	}
	
	/**
	 * 交接人已被更新err
	 * 
	 * @param id
	 * @param context
	 */
	private void addOrgErr(HandoverExecuteContext executeContext, String sign,
			String orgId) {
		executeContext.error(executeContext.getModule() + ID_SPLIT + sign
				+ orgId, ResourceUtil.getString("sysHandoverConfigMain.error", "sys-handover"));
	}
}
