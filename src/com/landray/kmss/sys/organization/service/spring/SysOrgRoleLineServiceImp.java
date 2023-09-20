package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.forms.SysOrgRoleLineForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.sys.organization.model.SysOrgRoleLine;
import com.landray.kmss.sys.organization.service.ISysOrgRoleConfService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleLineService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 角色线业务接口实现
 * 
 * 创建日期 2008-11-21
 * 
 * @author 叶中奇
 */
public class SysOrgRoleLineServiceImp extends BaseServiceImp implements
		ISysOrgRoleLineService {
	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.landray.kmss.sys.organization.service.ISysOrgRoleLineService#quickAdd
	 * (com.landray.kmss.common.actions.RequestContext)
	 */
	@Override
	public List<Map<String, String>> quickAdd(RequestContext requestInfo)
			throws Exception {
		String confId = requestInfo.getParameter("confId");
		String parentId = requestInfo.getParameter("parentId");
		String orgIds = requestInfo.getParameter("orgIds");
		String[] idArr = orgIds.split(";");
		StringBuffer notAddInfo = new StringBuffer();
		
		// 记录日志
		if (UserOperHelper.allowLogOper("sysOrgRoleLineOption", null)) {
			UserOperHelper.setEventType(ResourceUtil.getString("sys-organization:sysOrgRoleLine.opt.quickCreate"));
			UserOperHelper.setModelNameAndModelDesc("com.landray.kmss.sys.organization.model.SysOrgRoleLine");
		}

		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		Map<String, String> msg = new HashMap<String, String>();
		msg.put("success", "true");
		rtnList.add(msg);
		for (int i = 0; i < idArr.length; i++) {
			SysOrgRoleLineForm form = new SysOrgRoleLineForm();
			form.setFdConfId(confId);
			form.setFdMemberId(idArr[i]);
			form.setFdParentId(parentId);
			SysOrgRoleLine model = (SysOrgRoleLine) convertFormToModel(form,
					null, requestInfo);
			if (UserOperHelper.allowLogOper("sysOrgRoleLineOption", null)) {
				UserOperContentHelper.putAdd(model.getSysOrgRoleMember(), "fdId", "fdName");
			}
			if (check(form)) {
				add(model);
				Map<String, String> map = new HashMap<String, String>();
				SysOrgElement sysOrgRoleMember = model.getSysOrgRoleMember();
				String fdName = model.getFdName();
				
				if (sysOrgRoleMember != null) {
					if(SysOrgConstant.ORG_TYPE_POST== model.getSysOrgRoleMember().getFdOrgType()){

						String disName = sysOrgRoleMember.getFdName()+"<"+ sysOrgRoleMember.getFdParentsName() +">";
						if (sysOrgRoleMember.getFdIsAvailable() != null && !sysOrgRoleMember.getFdIsAvailable()) // 无效
                        {
                            disName += "(" + ResourceUtil.getString("sysOrg.address.info.disable", "sys-organization") + ")";
                        }
						if (StringUtil.isNotNull(fdName)) {
                            disName = fdName + "(" + disName + ")";
                        }
						map.put("text", disName);
					}else {
                        map.put("text", model.getDisName());
                    }
				}else {
                    map.put("text", model.getDisName());
                }
				
				map.put("value", model.getFdId());
				if (model.getSysOrgRoleMember() != null) {
					map.put("nodeType", model.getSysOrgRoleMember()
							.getFdOrgType().toString());
					map.put("isExternal", sysOrgRoleMember.getFdIsExternal().toString());
				}
				rtnList.add(map);
			} else {
				notAddInfo.append(',').append(
						model.getSysOrgRoleMember().getFdName());
			}
		}
		if (notAddInfo.length() > 1) {
			msg.put("message", ResourceUtil.getString(
					"sysOrgRoleLine.msg.notAdd", "sys-organization",
					requestInfo.getLocale(), notAddInfo.substring(1)));
		}
		return rtnList;
	}

	protected ISysOrgRoleConfService sysOrgRoleConfService;

	protected ISysOrgRoleConfService getSysOrgRoleConfServiceImp() {
		if (sysOrgRoleConfService == null) {
            sysOrgRoleConfService = (ISysOrgRoleConfService) SpringBeanUtil
                    .getBean("sysOrgRoleConfService");
        }
		return sysOrgRoleConfService;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.sys.organization.service.ISysOrgRoleLineService#
	 * deleteChildren(com.landray.kmss.common.actions.RequestContext)
	 */
	@Override
	public List<Map<String, String>> deleteChildren(RequestContext requestInfo)
			throws Exception {
		String id = requestInfo.getParameter("id");
		SysOrgRoleLine roleLine = (SysOrgRoleLine) findByPrimaryKey(id);
		if (roleLine != null) {
			SysOrgRoleConf roleConf = roleLine.getSysOrgRoleConf();
			if (roleConf != null) {
				roleConf.setFdRoleLineAlterTime(new Date());
				getSysOrgRoleConfServiceImp().update(roleConf);
			}
		}
		delete(id);
		return null;
	}
	@Override
	public List<Map<String, String>> deleteAllChildren(RequestContext requestInfo)
		     throws Exception {
		HQLInfo hql  = new HQLInfo();
		String id = requestInfo.getParameter("id");
		SysOrgRoleLine roleLine = (SysOrgRoleLine) findByPrimaryKey(id);
		if (roleLine != null) {
			SysOrgRoleConf roleConf = roleLine.getSysOrgRoleConf();
			if (roleConf != null) {
				roleConf.setFdRoleLineAlterTime(new Date());
				getSysOrgRoleConfServiceImp().update(roleConf);
			}
		}
		hql.setWhereBlock("sysOrgRoleLine.fdHierarchyId like:fdId");
		hql.setParameter("fdId", "%"+id+"%");
		List<SysOrgRoleLine> rolelist = new ArrayList<SysOrgRoleLine>();
		rolelist = findList(hql);
		Collections.sort(rolelist, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				SysOrgRoleLine rl1 = (SysOrgRoleLine) o1;
				SysOrgRoleLine rl2 = (SysOrgRoleLine) o2;
				String h1 = rl1.getFdHierarchyId();
				String h2 = rl2.getFdHierarchyId();
				if (h1.length() > h2.length()) {
					return 1;
				} else if (h1.length()==h2.length()) {
					return 0;
				} else {
					return -1;
				}
			 }
		});
		for(int i=rolelist.size()-1;i>=0;i--){
			delete(rolelist.get(i).getFdId());
		}
		return null;
	}
	
	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.landray.kmss.sys.organization.service.ISysOrgRoleLineService#move
	 * (com.landray.kmss.common.actions.RequestContext)
	 */
	@Override
	public List<Map<String, String>> move(RequestContext requestInfo)
			throws Exception {
		String id = requestInfo.getParameter("id");
		String parentId = requestInfo.getParameter("parentId");
		SysOrgRoleLine model = (SysOrgRoleLine) findByPrimaryKey(id);
		if (StringUtil.isNull(parentId)) {
			model.setFdParent(null);
		} else {
			model.setFdParent((SysOrgRoleLine) findByPrimaryKey(parentId));
		}
		// 记录日志
		if (UserOperHelper.allowLogOper("sysOrgRoleLineOption", null)) {
			UserOperHelper.setEventType(ResourceUtil.getString("sys-organization:sysOrgRoleLine.opt.move"));
			UserOperHelper.setModelNameAndModelDesc("com.landray.kmss.sys.organization.model.SysOrgRoleLine");
			UserOperContentHelper.putUpdate(model.getFdId(), model.getDisName(), SysOrgRoleLine.class.getName());
		}
		update(model);
		return null;
	}

	private boolean check(SysOrgRoleLineForm form) throws Exception {
		if (StringUtil.isNull(form.getFdMemberId())) {
			return true;
		}
		StringBuffer whereBlock = new StringBuffer();
		whereBlock.append("sysOrgRoleLine.fdId!='").append(form.getFdId())
				.append("'");
		whereBlock.append(" and sysOrgRoleLine.sysOrgRoleConf.fdId='").append(
				form.getFdConfId()).append("'");
		whereBlock.append(" and sysOrgRoleLine.sysOrgRoleMember.fdId='")
				.append(form.getFdMemberId()).append("'");
		return findValue("sysOrgRoleLine.fdId", whereBlock.toString(), null)
				.isEmpty();
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		if (!check((SysOrgRoleLineForm) form)) {
			throw new KmssRuntimeException(new KmssMessage(
					"sys-organization:sysOrgRoleLine.error.memberMustUnique"));
		}
		return super.add(form, requestContext);
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		if (!check((SysOrgRoleLineForm) form)) {
			throw new KmssRuntimeException(new KmssMessage(
					"sys-organization:sysOrgRoleLine.error.memberMustUnique"));
		}
		super.update(form, requestContext);
	}
}
