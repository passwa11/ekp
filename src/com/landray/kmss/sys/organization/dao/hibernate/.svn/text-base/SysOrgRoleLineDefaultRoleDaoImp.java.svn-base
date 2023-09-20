package com.landray.kmss.sys.organization.dao.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.dao.ISysOrgRoleLineDefaultRoleDao;
import com.landray.kmss.sys.organization.forms.SysOrgRoleLineDefaultRoleForm;
import com.landray.kmss.sys.organization.model.SysOrgRoleLineDefaultRole;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 创建日期 2008-十一月-27
 * 
 * @author 陈亮 角色是否默认数据访问接口实现
 */
public class SysOrgRoleLineDefaultRoleDaoImp extends BaseDaoImp implements
		ISysOrgRoleLineDefaultRoleDao {
	@Override
	@SuppressWarnings("unchecked")
	public Collection<SysOrgRoleLineDefaultRoleForm> loadDefaultRoleForm(
			String confId) throws Exception {
		// 查找所有已经定义的默认角色
		List<SysOrgRoleLineDefaultRole> defaultRoleList = findList(
				"sysOrgRoleLineDefaultRole.sysOrgRoleConf.fdId='" + confId
						+ "'", null);
		Map<String, SysOrgRoleLineDefaultRoleForm> person2form = new HashMap<String, SysOrgRoleLineDefaultRoleForm>();

		// 查找重复的个人ID
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						"select person.fd_personid from sys_org_role_line line left join sys_org_post_person person on person.fd_postid = line.fd_member_id where person.fd_personid is not null and line.fd_role_line_conf_id = '")
				.append(confId).append(
						"' group by person.fd_personid having count(*)>1");
		List<String> personIds = getHibernateSession().createNativeQuery(
				sql.toString()).list();

		// 删除多余的记录，把剩余的记录转换到id2form中
		for (int i = defaultRoleList.size() - 1; i >= 0; i--) {
			SysOrgRoleLineDefaultRole defaultRole = defaultRoleList.get(i);
			if (personIds.contains(defaultRole.getFdPerson().getFdId())) {
				SysOrgRoleLineDefaultRoleForm form = new SysOrgRoleLineDefaultRoleForm();
				form.setFdRoleLineConfId(confId);
				form.setFdId(defaultRole.getFdId());
				form.setFdPersonId(defaultRole.getFdPerson().getFdId());
				form.setFdRoleLineConfId(confId);
				form.setFdPostId(defaultRole.getFdPost().getFdId());
				person2form.put(form.getFdPersonId(), form);
			} else {
				defaultRoleList.remove(i);
				delete(defaultRole);
			}
		}
		if (personIds.isEmpty()) {
            return person2form.values();
        }

		// 查找岗位的ID
		sql = new StringBuffer();
		sql
				.append(
						"select person.fd_personid, line.fd_member_id from sys_org_role_line line left join sys_org_post_person person on person.fd_postid = line.fd_member_id where ")
				.append(HQLUtil.buildLogicIN("person.fd_personid", personIds))
				.append(" and line.fd_role_line_conf_id = '").append(confId)
				.append("'");
		List<Object[]> personPostIds = getHibernateSession().createNativeQuery(
				sql.toString()).list();
		Map<String, List<String>> person2post = new HashMap<String, List<String>>();
		for (int i = 0; i < personPostIds.size(); i++) {
			Object[] values = personPostIds.get(i);
			List<String> posts = person2post.get(values[0]);
			if (posts == null) {
				posts = new ArrayList<String>();
				person2post.put(values[0].toString(), posts);
			}
			posts.add(values[1].toString());
		}

		// 查找个人/岗位名称
		List<String> orgIds = new ArrayList<String>();
		orgIds.addAll(personIds);
		for (int i = 0; i < personPostIds.size(); i++) {
			Object[] values = personPostIds.get(i);
			if (!orgIds.contains(values[1])) {
                orgIds.add(values[1].toString());
            }
		}
		sql = new StringBuffer();
		sql.append("select fd_id, fd_name from sys_org_element where ").append(
				HQLUtil.buildLogicIN("fd_id", orgIds));
		List<Object[]> nameList = getHibernateSession().createNativeQuery(
				sql.toString()).list();
		Map<String, String> id2name = new HashMap<String, String>();
		for (int i = 0; i < nameList.size(); i++) {
			Object[] values = nameList.get(i);
			id2name.put(values[0].toString(), values[1].toString());
		}

		// 完善所有Form的数据
		for (int i = 0; i < personIds.size(); i++) {
			String personId = personIds.get(i);
			List<String> postIdList = person2post.get(personId);
			SysOrgRoleLineDefaultRoleForm form = person2form.get(personId);
			if (form == null) {
				// 初始化form数据
				form = new SysOrgRoleLineDefaultRoleForm();
				form.setFdId("");
				form.setFdRoleLineConfId(confId);
				form.setFdPersonId(personId);
				form.setFdRoleLineConfId(confId);
				person2form.put(form.getFdPersonId(), form);
			} else {
				// 完善post数据
				String postId = form.getFdPostId();
				if (StringUtil.isNotNull(postId)) {
					if (!postIdList.contains(postId)) {
						form.setFdPostId(null);
					}
				}
			}
			// 完善人员姓名
			form.setFdPersonName(id2name.get(personId));
			// 完善岗位列表
			StringBuffer postIds = new StringBuffer();
			StringBuffer postNames = new StringBuffer();
			for (int j = 0; j < postIdList.size(); j++) {
				String postId = postIdList.get(j);
				postIds.append(';').append(postId);
				postNames.append(';').append(id2name.get(postId));
			}
			form.setFdPostIds(postIds.substring(1));
			form.setFdPostNames(postNames.substring(1));
		}

		return person2form.values();
	}
	
	/**
	 * 查询所有机构ID
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public List loadElementIds(String confId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select line.fd_member_id from sys_org_role_line line where line.fd_role_line_conf_id = '").append(confId).append("'");
		return getHibernateSession().createNativeQuery(sql.toString()).list();
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysOrgRoleLineDefaultRole defaultRole = (SysOrgRoleLineDefaultRole) modelObj;
		Date current = new Date();
		defaultRole.setFdCreateTime(current);
		defaultRole.setFdAlterTime(current);
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysOrgRoleLineDefaultRole defaultRole = (SysOrgRoleLineDefaultRole) modelObj;
		defaultRole.setFdAlterTime(new Date());
		super.update(modelObj);
	}
}
