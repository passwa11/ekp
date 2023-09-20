package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.dao.ISysOrgRoleLineDefaultRoleDao;
import com.landray.kmss.sys.organization.forms.SysOrgRoleConfForm;
import com.landray.kmss.sys.organization.forms.SysOrgRoleLineDefaultRoleForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.sys.organization.model.SysOrgRoleLine;
import com.landray.kmss.sys.organization.model.SysOrgRoleLineDefaultRole;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleConfService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleLineService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 叶中奇 角色线配置业务接口实现
 */
public class SysOrgRoleConfServiceImp extends BaseServiceImp implements
		ISysOrgRoleConfService {

	private ISysOrgRoleLineDefaultRoleDao sysOrgRoleLineDefaultRoleDao;

	public void setSysOrgRoleLineDefaultRoleDao(
			ISysOrgRoleLineDefaultRoleDao sysOrgRoleLineDefaultRoleDao) {
		this.sysOrgRoleLineDefaultRoleDao = sysOrgRoleLineDefaultRoleDao;
	}

	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public SysOrgRoleConfForm loadRepeatRoleForm(String id) throws Exception {
		SysOrgRoleConfForm form = new SysOrgRoleConfForm();
		form.setFdId(id);
		List<SysOrgRoleLineDefaultRoleForm> defaultRoleList = form
				.getDefaultRoleList();
		defaultRoleList.clear();
		defaultRoleList.addAll(sysOrgRoleLineDefaultRoleDao
				.loadDefaultRoleForm(id));
		return form;
	}
	
	@Override
	public List loadInvalidElement(String id) throws Exception {
		List list = new ArrayList();
		List<String> personIds = sysOrgRoleLineDefaultRoleDao.loadElementIds(id);
		if (personIds != null && personIds.size() > 0) {
			for (String personId : personIds) {
				SysOrgElement element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(personId);
				if (element != null && element.getFdIsAvailable() != null && !element.getFdIsAvailable()) { // 无效
					list.add(element);
				}
			}
		}

		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void updateRepeatRoleForm(SysOrgRoleConfForm form) throws Exception {
		SysOrgRoleConf roleConf = (SysOrgRoleConf) findByPrimaryKey(form
				.getFdId());
		List<SysOrgRoleLineDefaultRoleForm> defaultRoleFormList = form
				.getDefaultRoleList();
		for (int i = 0; i < defaultRoleFormList.size(); i++) {
			SysOrgRoleLineDefaultRoleForm defaultRoleForm = defaultRoleFormList
					.get(i);
			SysOrgRoleLineDefaultRole defaultRole = null;
			if (StringUtil.isNotNull(defaultRoleForm.getFdId())) {
				defaultRole = (SysOrgRoleLineDefaultRole) sysOrgRoleLineDefaultRoleDao
						.findByPrimaryKey(defaultRoleForm.getFdId());
			} else {
				defaultRole = new SysOrgRoleLineDefaultRole();
				defaultRole.setSysOrgRoleConf(roleConf);
				defaultRole.setFdPerson((SysOrgElement) sysOrgElementService
						.findByPrimaryKey(defaultRoleForm.getFdPersonId()));
			}
			defaultRole.setFdPost((SysOrgElement) sysOrgElementService
					.findByPrimaryKey(defaultRoleForm.getFdPostId()));
			sysOrgRoleLineDefaultRoleDao.update(defaultRole);
		}
	}

	@Override
	public void updateInvalidated(String id, RequestContext requestContext) throws Exception {
		boolean fdEnabled = false;
		try {
			fdEnabled = Boolean.valueOf(requestContext.getParameter("fdEnabled"));
		} catch (Exception e) {
		}
		SysOrgRoleConf sysOrgRoleConf = (SysOrgRoleConf) findByPrimaryKey(id);
		sysOrgRoleConf.setFdIsAvailable(fdEnabled);
		update(sysOrgRoleConf);
		flushHibernateSession();
		// 记录日志
		if (UserOperHelper.allowLogOper("setInvalidated", getModelName())) {
			UserOperContentHelper.putUpdate(sysOrgRoleConf);
			if(fdEnabled) {
                UserOperHelper.setEventType(ResourceUtil.getString("sys-organization:sys.org.available.true"));
            } else {
                UserOperHelper.setEventType(ResourceUtil.getString("sys-organization:sys.org.available.false"));
            }
		}
	}

	@Override
	public void updateInvalidated(String[] ids, RequestContext requestContext) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			this.updateInvalidated(ids[i], requestContext);
		}
	}

	@Override
	public void updateCopy(String fdId) throws Exception {
		// 复制角色线配置基本信息
		SysOrgRoleConf sysOrgRoleConf = (SysOrgRoleConf) this
				.findByPrimaryKey(fdId);
		SysOrgRoleConf sysOrgRoleConfCopy = new SysOrgRoleConf();
		sysOrgRoleConfCopy.setFdName("copy of " + sysOrgRoleConf.getFdName());
		// 默认为有效
		sysOrgRoleConfCopy.setFdIsAvailable(new Boolean(true));
		sysOrgRoleConfCopy.setFdOrder(sysOrgRoleConf.getFdOrder());
		List editors = new ArrayList();
		if (sysOrgRoleConf.getSysRoleLineEditors() != null) {
			editors.addAll(sysOrgRoleConf.getSysRoleLineEditors());
			sysOrgRoleConfCopy.setSysRoleLineEditors(editors);
		}
		// 添加日志信息
		if (UserOperHelper.allowLogOper("Service_Add", getModelName())) {
			UserOperContentHelper.putAdd(sysOrgRoleConfCopy, "fdName",
					"fdOrder", "fdIsAvailable", "sysRoleLineEditors");
		}
		add(sysOrgRoleConfCopy);

		// 找出角色关系，并复制
		String whereBlock = "sysOrgRole.fdRoleConf.fdId='" + fdId + "'";
		String orderBy = "sysOrgRole.fdOrder";
		List sysOrgRoleList = ((ISysOrgRoleService) SpringBeanUtil
				.getBean("sysOrgRoleService")).findList(whereBlock, orderBy);
		for (int i = 0; i < sysOrgRoleList.size(); i++) {
			SysOrgRole sysOrgRole = (SysOrgRole) sysOrgRoleList.get(i);
			SysOrgRole sysOrgRoleCopy = new SysOrgRole();
			sysOrgRoleCopy.setFdRoleConf(sysOrgRoleConfCopy);
			sysOrgRoleCopy.setFdName(sysOrgRole.getFdName());
			// 默认为有效
			sysOrgRoleCopy.setFdIsAvailable(new Boolean(true));
			sysOrgRoleCopy.setFdOrder(sysOrgRole.getFdOrder());
			sysOrgRoleCopy.setFdPlugin(sysOrgRole.getFdPlugin());
			sysOrgRoleCopy.setFdParameter(sysOrgRole.getFdParameter());
			sysOrgRoleCopy.setFdIsMultiple(sysOrgRole.getFdIsMultiple());
			sysOrgRoleCopy.setFdRtnValue(sysOrgRole.getFdRtnValue());
			sysOrgRoleCopy.setFdNamePinYin(sysOrgRole.getFdNamePinYin());
			sysOrgRoleCopy.setFdNameSimplePinyin(sysOrgRole.getFdNameSimplePinyin());
			((ISysOrgRoleService) SpringBeanUtil.getBean("sysOrgRoleService"))
					.add(sysOrgRoleCopy);
		}

		// 找出角色线成员，并复制
		whereBlock = "sysOrgRoleLine.sysOrgRoleConf.fdId='" + fdId + "'";
		// 按照层级ID进行排序
		orderBy = "sysOrgRoleLine.fdHierarchyId";
		// 原ID与新对象的对应表，用于复制后更新关系
		Map<String, SysOrgRoleLine> sysOrgRoleLineCopyMap = new HashMap<String, SysOrgRoleLine>();
		List<SysOrgRoleLine> sysOrgRoleLineCopyList = new ArrayList<SysOrgRoleLine>();
		List<SysOrgRoleLine> sysOrgRoleLineList = ((ISysOrgRoleLineService) SpringBeanUtil
				.getBean("sysOrgRoleLineService"))
				.findList(whereBlock, orderBy);
		// 复制对象
		for (int i = 0; i < sysOrgRoleLineList.size(); i++) {
			SysOrgRoleLine sysOrgRoleLine = sysOrgRoleLineList.get(i);
			SysOrgRoleLine sysOrgRoleLineCopy = new SysOrgRoleLine();
			sysOrgRoleLineCopy.setSysOrgRoleConf(sysOrgRoleConfCopy);
			sysOrgRoleLineCopy.setFdName(sysOrgRoleLine.getFdName());
			sysOrgRoleLineCopy.setFdOrder(sysOrgRoleLine.getFdOrder());
			sysOrgRoleLineCopy.setSysOrgRoleMember(sysOrgRoleLine
					.getSysOrgRoleMember());
			sysOrgRoleLineCopy.setHbmParent(sysOrgRoleLine.getHbmParent());
			sysOrgRoleLineCopyMap.put(sysOrgRoleLine.getFdId(),
					sysOrgRoleLineCopy);
			sysOrgRoleLineCopyList.add(sysOrgRoleLineCopy);
		}
		// 更新关系
		for (int i = 0; i < sysOrgRoleLineCopyList.size(); i++) {
			SysOrgRoleLine sysOrgRoleLine = sysOrgRoleLineCopyList.get(i);
			if (sysOrgRoleLine.getFdParent() != null) {
				SysOrgRoleLine parent = sysOrgRoleLineCopyMap
						.get(sysOrgRoleLine.getFdParent().getFdId());
				sysOrgRoleLine.setHbmParent(parent);
			}
			((ISysOrgRoleLineService) SpringBeanUtil
					.getBean("sysOrgRoleLineService")).add(sysOrgRoleLine);
		}
	}
	
}
