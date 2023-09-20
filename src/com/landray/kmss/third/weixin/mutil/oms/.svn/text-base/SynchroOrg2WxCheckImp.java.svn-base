package com.landray.kmss.third.weixin.mutil.oms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SynchroOrg2WxCheckImp extends BaseServiceImp
		implements
			ISynchroOrg2WxworkCheck {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrg2WxCheckImp.class);

	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	@Override
	public List<String> checkDeptInfo(List<String> hiers) throws Exception {
		String urlPre = ResourceUtil.getKmssConfigString("kmss.urlPrefix")
				+ "/sys/organization/sys_org_dept/sysOrgDept.do?method=view&amp;fdId=";
		String reup = ResourceUtil.getKmssConfigString("kmss.urlPrefix")
				+ "/sys/organization/sys_org_dept/index.jsp?parent=";
		List<String> rtnList = new ArrayList<String>();
		List<SysOrgElement> depts = null;
		StringBuffer temp = new StringBuffer();
		Pattern pattern = Pattern.compile("[\\\\:*?\"<>|]");
		for (String hier : hiers) {
			depts = sysOrgElementService.findList(
					"fdOrgType in (1,2) and fdIsAvailable=1 and fdHierarchyId like '"
							+ hier + "%'",
					null);
			// 部门名称重复检查
			String sql = "select fd_name,fd_parentid from sys_org_element where fd_is_available=1 and fd_org_type in (1,2) and fd_hierarchy_id like '"
					+ hier
					+ "%' group by fd_parentid,fd_name having count(fd_name)>1";
			List<Object[]> reDept = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(sql).list();
			if (reDept != null && !reDept.isEmpty()) {
				for (Object[] obj : reDept) {
					if (obj[0] != null && obj[1] != null) {
						// 地址没拦截，不会跳到登录界面，导致重复刷新，暂时取消直接访问
						// rtnList.add("同级部门存在名称重复(<a target='_blank' href='"+
						// reup + obj[1] + "'>" + obj[0] + "</a>)");
						rtnList.add(ResourceUtil.getString(
								"third.wx.check.dept.repeat", "third-weixin")
								+ "(" + obj[0] + ")");
					}
				}
			}
			// 部门名称检查校验
			for (SysOrgElement dept : depts) {
				temp.append(ResourceUtil.getString("third.wx.check.dept.name",
						"third-weixin") + "(<a target='_blank' href='" + urlPre
						+ dept.getFdId() + "'>" + dept.getFdName() + "</a>)");
				if (StringUtil.isNull(dept.getFdName())) {
					temp.append(ResourceUtil.getString(
							"third.wx.check.dept.notnull", "third-weixin"));
				}
				if (StringUtil.isNotNull(dept.getFdName())
						&& dept.getFdName().length() > 32) {
					temp.append(ResourceUtil.getString(
							"third.wx.check.dept.namelimit", "third-weixin"));
				}
				if (StringUtil.isNotNull(dept.getFdName())) {
					Matcher matcher = pattern.matcher(dept.getFdName());
					if (matcher.find()) {
						temp.append(ResourceUtil.getString(
								"third.wx.check.dept.namesp", "third-weixin"));
					}
				}
				if (!temp.toString()
						.equals(ResourceUtil.getString(
								"third.wx.check.dept.name", "third-weixin")
								+ "(<a target='_blank' href='" + urlPre
								+ dept.getFdId() + "'>" + dept.getFdName()
								+ "</a>)")) {
					rtnList.add(temp + ResourceUtil.getString(
							"third.wx.check.dept.notsysnc", "third-weixin"));
				}
				temp.setLength(0);
			}
		}
		return rtnList;
	}

	@Override
	public List<String> checkPersonInfo(List<String> hiers) throws Exception {
		String urlPre = ResourceUtil.getKmssConfigString("kmss.urlPrefix")
				+ "/sys/organization/sys_org_person/sysOrgPerson.do?method=view&amp;fdId=";
		List<String> rtnList = new ArrayList<String>();
		List<SysOrgElement> persons = null;
		SysOrgPerson orgPerson = null;
		Map<String, List<String>> mobileMap = null;
		Map<String, List<String>> mailMap = null;
		List<String> mobileList = null;
		List<String> mailList = null;
		Pattern p = Pattern.compile("[a-zA-Z0-9-_@\\.]+");
		StringBuffer temp = new StringBuffer();
		for (String hier : hiers) {
			persons = sysOrgElementService
					.findList("fdOrgType=" + SysOrgConstant.ORG_TYPE_PERSON
							+ " and fdIsAvailable=1 and fdHierarchyId like '"
							+ hier + "%'", null);
			// 电话号码、邮箱重复处理
			mobileMap = new HashMap<String, List<String>>(persons.size());
			mailMap = new HashMap<String, List<String>>(persons.size());
			for (SysOrgElement person : persons) {
				orgPerson = (SysOrgPerson) sysOrgCoreService.format(person);
				if (StringUtil.isNotNull(orgPerson.getFdMobileNo())) {
					if (mobileMap.containsKey(orgPerson.getFdMobileNo())) {
						mobileList = mobileMap.get(orgPerson.getFdMobileNo());
					} else {
						mobileList = new ArrayList<String>();
					}
					mobileList.add("<a target='_blank' href='" + urlPre
							+ orgPerson.getFdId() + "'>" + orgPerson.getFdName()
							+ "</a>");
					mobileMap.put(orgPerson.getFdMobileNo(), mobileList);
				}
				if (StringUtil.isNotNull(orgPerson.getFdEmail())) {
					if (mailMap.containsKey(orgPerson.getFdEmail())) {
						mailList = mailMap.get(orgPerson.getFdEmail());
					} else {
						mailList = new ArrayList<String>();
					}
					mailList.add("<a target='_blank' href='" + urlPre
							+ orgPerson.getFdId() + "'>" + orgPerson.getFdName()
							+ "</a>");
					mailMap.put(orgPerson.getFdEmail(), mailList);
				}
			}
			// 校验检查
			for (SysOrgElement person : persons) {
				orgPerson = (SysOrgPerson) sysOrgCoreService.format(person);
				temp.append(ResourceUtil.getString("third.wx.check.person.name",
						"third-weixin") + "(<a target='_blank' href='" + urlPre
						+ person.getFdId() + "'>" + person.getFdName()
						+ "</a>)");
				if (StringUtil.isNull(person.getFdName())) {
					temp.append(ResourceUtil.getString(
							"third.wx.check.dept.notnull", "third-weixin"));
				}
				if (StringUtil.isNotNull(person.getFdName())
						&& person.getFdName().length() > 32) {
					temp.append(ResourceUtil.getString(
							"third.wx.check.person.notnull", "third-weixin"));
				}
				if (StringUtil.isNull(orgPerson.getFdEmail())
						&& StringUtil.isNull(orgPerson.getFdMobileNo())) {
					temp.append(ResourceUtil.getString(
							"third.wx.check.person.emailtelnotnull",
							"third-weixin"));
				}
				if (mobileMap.containsKey(orgPerson.getFdMobileNo())) {
					mobileList = mobileMap.get(orgPerson.getFdMobileNo());
					if (mobileList.size() > 1) {
						temp.append(ResourceUtil.getString(
								"third.wx.check.person.telrepeat",
								"third-weixin") + "(");
						for (int i = 0; i < mobileList.size(); i++) {
							if (i == 0) {
								temp.append(mobileList.get(i));
							} else {
								temp.append("," + mobileList.get(i));
							}
						}
						temp.append("),");
					}
				}
				if (mailMap.containsKey(orgPerson.getFdEmail())) {
					mailList = mailMap.get(orgPerson.getFdEmail());
					if (mailList.size() > 1) {
						temp.append(ResourceUtil.getString(
								"third.wx.check.person.emailrepeat",
								"third-weixin") + "(");
						for (int i = 0; i < mailList.size(); i++) {
							if (i == 0) {
								temp.append(mailList.get(i));
							} else {
								temp.append("," + mailList.get(i));
							}
						}
						temp.append("),");
					}
				}
				// 人员登录名、邮件和电话的空格校验
				isValidate(orgPerson, temp, p);
				if (!temp.toString()
						.equals(ResourceUtil.getString(
								"third.wx.check.person.name", "third-weixin")
								+ "(<a target='_blank' href='" + urlPre
								+ person.getFdId() + "'>" + person.getFdName()
								+ "</a>)")) {
					rtnList.add(temp + ResourceUtil.getString(
							"third.wx.check.dept.notsysnc", "third-weixin"));
				}
				temp.setLength(0);
			}
		}
		mobileMap = null;
		mailMap = null;
		return rtnList;
	}

	/**
	 * @param person
	 * @param temp
	 * @throws Exception
	 *             人员的登录名、邮件和电话里面不能有空格
	 */
	private void isValidate(SysOrgPerson person, StringBuffer temp, Pattern p)
			throws Exception {
		if (person == null || temp == null || p == null) {
            return;
        }
		String login = person.getFdLoginName();
		if (StringUtil.isNotNull(login)) {
			Matcher m = p.matcher(login);
			if (!m.matches()) {
				temp.append(ResourceUtil.getString(
						"third.wx.check.person.loginnametip", "third-weixin"));
			}
			if (StringUtil.isNotNull(login) && login.indexOf(" ") != -1) {
				temp.append(ResourceUtil.getString(
						"third.wx.check.person.loginnamenulltip",
						"third-weixin"));
			}
		} else {
			temp.append(ResourceUtil.getString(
					"third.wx.check.person.loginnamenull", "third-weixin"));
		}
		String mail = person.getFdEmail();
		if (StringUtil.isNotNull(mail) && mail.indexOf(" ") != -1) {
			temp.append(ResourceUtil.getString(
					"third.wx.check.person.emailnulltip", "third-weixin"));
		}
		String mobile = person.getFdMobileNo();
		if (StringUtil.isNotNull(mobile) && mobile.indexOf(" ") != -1) {
			temp.append(ResourceUtil.getString(
					"third.wx.check.person.telnulltip", "third-weixin"));
		}
	}

	@Override
    public List<String> getCheckHierarchy() throws Exception {
		List<String> rtn = new ArrayList<String>();
		// 如果配置过了同步的根目录，那么只校验根目录下的数据，否则校验全部数据
		// 多企业微信配置检查，检查所有开启企业微信配置的数据
		Map<String, Map<String, String>> map = WeixinMutilConfig.getWxConfigDataMap();
		for(Map.Entry<String, Map<String, String>> entry : map.entrySet()){
			String cid = WeixinMutilConfig.newInstance(entry.getKey()).getWxOrgId();
			if (StringUtil.isNotNull(cid)) {
				String[] orgIds = cid.split(";");
				for (String orgId : orgIds) {
					if (StringUtil.isNotNull(orgId.trim())) {
						SysOrgElement rootOrg = (SysOrgElement) sysOrgElementService
								.findByPrimaryKey(orgId, null, true);
						if (rootOrg != null) {
							rtn.add(rootOrg.getFdHierarchyId());
							logger.info("检查的根目录名称为：" + rootOrg.getFdName());
						}
					}
				}
				// 追加子机构检查
				List<SysOrgElement> subOrgs = sysOrgElementService
						.findList(
								"hbmParent.fdId='" + cid
										+ "' and fdOrgType=1 and fdIsAvailable=1",
								null);
				for (SysOrgElement subOrg : subOrgs) {
					rtn.add(subOrg.getFdHierarchyId());
				}
			} /*else {
				List<SysOrgElement> roots = sysOrgElementService.findList(
						"fdOrgType in (1,2) and fdIsAvailable=1 and hbmParent is null",
						null);
				for (SysOrgElement root : roots) {
					rtn.add(root.getFdHierarchyId());
					logger.info("检查的根目录名称为：" + root.getFdName());
				}
				}*/
		}
		return rtn;
	}

}
