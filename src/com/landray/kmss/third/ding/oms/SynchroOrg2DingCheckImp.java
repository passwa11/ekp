package com.landray.kmss.third.ding.oms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.landray.kmss.sys.organization.interfaces.ISysOrgElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SynchroOrg2DingCheckImp extends BaseServiceImp
		implements
			SynchroOrg2DingCheck {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrg2DingCheckImp.class);

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
		List<Object []> depts = null;
		StringBuffer temp = new StringBuffer();
		for (String hier : hiers) {
			depts=sysOrgElementService.findValue("fdId,fdName","fdOrgType in (1,2) and fdIsAvailable=1 and fdHierarchyId like '"
					+ hier + "%'",null);
			if(depts==null || depts.isEmpty()) {
                continue;
            }

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
								"third.ding.check.dept.repeat", "third-ding")
								+ "(" + obj[0] + ")");
					}
				}
			}
			// 部门名称检查校验
			for (Object[] dept : depts) {

				String deptId =  (String)dept[0];
				String deptName = (String)dept[1];
				temp.append(ResourceUtil.getString("third.ding.check.dept.name",
						"third-ding") + "(<a target='_blank' href='" + urlPre
						+ deptId + "'>" + deptName + "</a>)");
				if (StringUtil.isNull(deptName)) {
					temp.append(ResourceUtil.getString(
							"third.ding.check.dept.notnull", "third-ding"));
				}
				if (StringUtil.isNotNull(deptName)) {
					if(deptName.length() > 64){
						temp.append(ResourceUtil.getString(
							"third.ding.check.dept.namelimit", "third-ding"));
					}else if(deptName.indexOf("-")!=-1||deptName.indexOf(",")!=-1||deptName.indexOf("，")!=-1){
						temp.append(ResourceUtil.getString(
								"third.ding.check.dept.namelimit", "third-ding"));
					}
				}
				if (!temp.toString()
						.equals(ResourceUtil.getString(
								"third.ding.check.dept.name", "third-ding")
								+ "(<a target='_blank' href='" + urlPre
								+ deptId + "'>" + deptName
								+ "</a>)")) {
					rtnList.add(temp + ResourceUtil.getString(
							"third.ding.check.dept.notsysnc", "third-ding"));
				}
				temp.setLength(0);
			}
		}
		return rtnList;
	}

	private String getParentName(String parentId) throws Exception {
		if(StringUtil.isNull(parentId)){
			return null;
		}
		ISysOrgElement element = (ISysOrgElement)sysOrgElementService.findByPrimaryKey(parentId);
		if(element==null){
			return null;
		}
		return element.getFdName();
	}

	private String getPersonShowMsg(String personId, String personName, String personLoginName, String parentId) throws Exception {
		return personName+" 登录名："+personLoginName+"，所在部门："+ getParentName(parentId)
				+"，ID："+personId;
	}

	@Override
	public List<String> checkPersonInfo(List<String> hiers) throws Exception {
		String urlPre = ResourceUtil.getKmssConfigString("kmss.urlPrefix")
				+ "/sys/organization/sys_org_person/sysOrgPerson.do?method=view&amp;fdId=";
		List<String> rtnList = new ArrayList<String>();
		List<Object[]> persons = null;
		Map<String, List<String>> mobileMap = null;
		Map<String, List<String>> mailMap = null;
		Map<String, List<String>> telMap = null;
		List<String> mobileList = null;
		List<String> mailList = null;
		List<String> telList = null;
		String wp = DingConfig.newInstance().getDingWorkPhoneEnabled();
		Pattern p = Pattern.compile("[a-zA-Z0-9-_@\\.]+");
		StringBuffer temp = new StringBuffer();
		for (String hier : hiers) {

			String sql = "select p.fd_id,p.fd_login_name,ele.fd_name,p.fd_mobile_no,p.fd_email,p.fd_work_phone,ele.fd_no,ele.fd_parentid from sys_org_person p,sys_org_element ele WHERE p.fd_id=ele.fd_id AND ele.fd_is_available=1 and ele.fd_hierarchy_id like '"+ hier + "%'";
			persons = sysOrgElementService.getBaseDao().getHibernateSession()
					.createSQLQuery(sql).list();

			if(persons==null||persons.isEmpty()) {
                continue;
            }
			// 电话号码、邮箱重复处理
			mobileMap = new HashMap<String, List<String>>(persons.size());
			mailMap = new HashMap<String, List<String>>(persons.size());
			telMap = new HashMap<String, List<String>>(persons.size());

			String personId = null;
			String personLoginName = null;
			String personName = null;
			String personMobileNo = null;
			String personEmail = null;
			String personWorkPhone = null;
			String personNo = null;
			String parentId = null;

			for (Object[] person: persons) {
				personId =  (String)person[0];
				personName =  (String)person[2];
				personMobileNo =(String) person[3];
				personEmail = (String) person[4];
				personWorkPhone = (String) person[5];
				parentId = (String)person[7];

				if (StringUtil.isNotNull(personMobileNo)) {
					if (mobileMap.containsKey(personMobileNo)) {
						mobileList = mobileMap.get(personMobileNo);
					} else {
						mobileList = new ArrayList<String>();
					}
					mobileList.add("<a target='_blank' href='" + urlPre
							+ personId + "'>" +personName
							+ "</a>");
					mobileMap.put(personMobileNo, mobileList);
				}
				if (StringUtil.isNotNull(personEmail)) {
					if (mailMap.containsKey(personEmail)) {
						mailList = mailMap.get(personEmail);
					} else {
						mailList = new ArrayList<String>();
					}
					mailList.add("<a target='_blank' href='" + urlPre
							+ personId + "'>" + personName
							+ "</a>");
					mailMap.put(personEmail, mailList);
				}
				if (StringUtil.isNotNull(wp) && "true".equals(wp)
						&& StringUtil.isNotNull(personWorkPhone)) {
					if (telMap.containsKey(personWorkPhone)) {
						telList = telMap.get(personWorkPhone);
					} else {
						telList = new ArrayList<String>();
					}
					telList.add("<a target='_blank' href='" + urlPre
							+ personId+ "'>" +personName
							+ "</a>");
					telMap.put(personWorkPhone, telList);
				}
			}
			// 校验检查
			for (Object[] person : persons) {
				personId = (String) person[0];
				personLoginName=  (String)person[1];
				personName =  (String)person[2];
				personMobileNo =(String) person[3];
				personEmail = (String) person[4];
				personWorkPhone =  (String)person[5];
				personNo = (String)person[6];
				temp.append(
						ResourceUtil.getString("third.ding.check.person.name",
								"third-ding") + "(<a target='_blank' href='"
								+ urlPre + personId + "'>"
								+ personName + "</a>)");
				if (StringUtil.isNull(personName)) {
					temp.append(ResourceUtil.getString(
							"third.ding.check.person.notnull", "third-ding"));
				}
				if (StringUtil.isNotNull(personName)
						&& personName.length() > 64) {
					temp.append(ResourceUtil.getString(
							"third.ding.check.person.long64", "third-ding"));
				}
				if (StringUtil.isNotNull(personWorkPhone)
						&& personWorkPhone.length() > 50) {
					temp.append(ResourceUtil.getString(
							"third.ding.check.person.long50", "third-ding"));
				}
				if (StringUtil.isNull(personMobileNo)) {
					temp.append(ResourceUtil.getString(
							"third.ding.check.person.telnotnull",
							"third-ding"));
				}
				if (mobileMap.containsKey(personMobileNo)) {
					mobileList = mobileMap.get(personMobileNo);
					if (mobileList.size() > 1) {
						temp.append(ResourceUtil.getString(
								"third.ding.check.person.telrepeat",
								"third-ding") + "(");
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
				if (mailMap.containsKey(personEmail)) {
					mailList = mailMap.get(personEmail);
					if (mailList.size() > 1) {
						temp.append(ResourceUtil.getString(
								"third.ding.check.person.emailrepeat",
								"third-ding") + "(");
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
				if (StringUtil.isNotNull(wp) && "true".equals(wp)
						&& telMap.containsKey(personWorkPhone)) {
					telList = telMap.get(personWorkPhone);
					if (telList.size() > 1) {
						temp.append(ResourceUtil.getString(
								"third.ding.check.person.workphonerepeat",
								"third-ding") + "(");
						for (int i = 0; i < telList.size(); i++) {
							if (i == 0) {
								temp.append(telList.get(i));
							} else {
								temp.append("," + telList.get(i));
							}
						}
						temp.append("),");
					}
				}
				// 人员登录名、邮件和电话的空格校验
				isValidate(personLoginName,personMobileNo,personEmail,personNo, temp, p);
				String tempMsg = temp.toString();
				if (!tempMsg
						.equals(ResourceUtil.getString(
								"third.ding.check.person.name", "third-ding")
								+ "(<a target='_blank' href='" + urlPre
								+ personId + "'>" + personName
								+ "</a>)")) {
					String personShowMsg = getPersonShowMsg(personId,personName,personLoginName,parentId);
					tempMsg = tempMsg.replace(">"+personName+"</a>",">"+personShowMsg+"</a>");
					rtnList.add(tempMsg + ResourceUtil.getString(
							"third.ding.check.person.notsysnc", "third-ding"));
				}
				temp.setLength(0);
			}
		}
		mobileMap = null;
		mailMap = null;
		telMap = null;
		return rtnList;
	}

	/**
	 * @param temp
	 * @throws Exception
	 *             人员的登录名、邮件和电话里面不能有空格
	 */
	private void isValidate(String login,String mobile,String mail,String personNo, StringBuffer temp, Pattern p)
			throws Exception {
		if (temp == null) {
            return;
        }
		Matcher m = null;
		if(StringUtil.isNotNull(login)){
			m = p.matcher(login);
			if (!m.matches()) {
				temp.append(ResourceUtil.getString(
						"third.ding.check.person.loginnametip", "third-ding"));
			}
			if (StringUtil.isNotNull(login) && login.indexOf(" ") != -1) {
				temp.append(ResourceUtil.getString(
						"third.ding.check.person.loginnamenulltip", "third-ding"));
			}
		}else{
			temp.append(ResourceUtil.getString(
					"third.ding.check.person.loginnull", "third-ding"));
		}
		if (StringUtil.isNotNull(mail) && mail.indexOf(" ") != -1) {
			temp.append(ResourceUtil.getString(
					"third.ding.check.person.emailnulltip", "third-ding"));
		}
		if (StringUtil.isNotNull(mobile) && mobile.indexOf(" ") != -1) {
			temp.append(ResourceUtil.getString(
					"third.ding.check.person.telnulltip", "third-ding"));
		}
		String no = DingConfig.newInstance().getDingNoEnabled();
		if (StringUtil.isNotNull(no) && "true".equals(no)
				&& StringUtil.isNotNull(personNo)) {
			m = p.matcher(personNo);
			if (!m.matches()) {
				temp.append(ResourceUtil.getString(
						"third.ding.check.person.notip", "third-ding"));
			}
		}
	}

	@Override
    public List<String> getCheckHierarchy() throws Exception {
		List<String> rtn = new ArrayList<String>();
		// 如果配置过了同步的根目录，那么只校验根目录下的数据，否则校验全部数据
		String cid = DingConfig.newInstance().getDingOrgId();
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
		} else {
			List<SysOrgElement> roots = sysOrgElementService.findList(
					"fdOrgType in (1,2) and fdIsAvailable=1 and hbmParent is null",
					null);
			for (SysOrgElement root : roots) {
				rtn.add(root.getFdHierarchyId());
				logger.info("检查的根目录名称为：" + root.getFdName());
			}
		}
		return rtn;
	}

}
