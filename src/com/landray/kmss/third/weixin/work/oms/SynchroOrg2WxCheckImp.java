package com.landray.kmss.third.weixin.work.oms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
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
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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

	private ThreadPoolTaskExecutor wxWorkOrgCheckExecutor;


	public void setWxWorkOrgCheckExecutor(ThreadPoolTaskExecutor wxWorkOrgCheckExecutor) {
		this.wxWorkOrgCheckExecutor = wxWorkOrgCheckExecutor;
	}



	private static List<String> rtnDeptList = null;

	CountDownLatch countDownLatch =null;
	CountDownLatch checkPersonCountDownLatch =null;

	String dept_urlPre = ResourceUtil.getKmssConfigString("kmss.urlPrefix")
			+ "/sys/organization/sys_org_dept/sysOrgDept.do?method=view&amp;fdId=";
	String dept_reup = ResourceUtil.getKmssConfigString("kmss.urlPrefix")
			+ "/sys/organization/sys_org_dept/index.jsp?parent=";

	@Override
	public List<String> checkDeptInfo(List<String> hiers) throws Exception {
		rtnDeptList = new ArrayList<String>();
		List<Object[]> depts = null;
		List<Object[]> allDepts = new ArrayList<Object[]>();
		for (String hier : hiers) {
			depts=sysOrgElementService.findValue("fdId,fdName","fdOrgType in (1,2) and fdIsAvailable=1 and fdHierarchyId like '"
					+ hier + "%'",null);
			if(depts==null || depts.isEmpty()) {
                continue;
            }
			allDepts.addAll(depts);
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
						rtnDeptList.add(ResourceUtil.getString(
								"third.wx.check.dept.repeat", "third-weixin")
								+ "(" + obj[0] + ")");
					}
				}
			}


		}
		//分批处理
		int rowsize =500; //每批500
		int count = allDepts.size() % rowsize == 0 ? allDepts.size() / rowsize
				: allDepts.size() / rowsize + 1;
		logger.warn("待检查的部门数据有：{}个",allDepts.size());
		logger.warn("分{}检查部门数据",count);
		countDownLatch = new CountDownLatch(count);
		for (int i = 0; i < count; i++) {
			logger.warn("执行第" + (i + 1) + "批");
			List<Object[]> deptTempList;
			if (allDepts.size() > rowsize * (i + 1)) {
				deptTempList = allDepts.subList(rowsize * i, rowsize * (i + 1));
			} else {
				deptTempList = allDepts.subList(rowsize * i, allDepts.size());
			}
			wxWorkOrgCheckExecutor.execute(new SynchroOrg2WxCheckImp.CheckDeptRunner(deptTempList));
		}
		try {
			countDownLatch.await(1, TimeUnit.HOURS);
		} catch (InterruptedException exc) {
			exc.printStackTrace();
			logger.error(exc.getMessage(), exc);
		}
		return rtnDeptList;
	}

	class CheckDeptRunner implements Runnable {
		private final List<Object[]> depts;

		public CheckDeptRunner(List<Object[]> depts) {
			this.depts = depts;
		}
		@Override
		public void run() {
			try {
				handleDeptCheckByRunnble(depts);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				countDownLatch.countDown();
			}
		}
	}

	private void handleDeptCheckByRunnble(List<Object[]> depts) {
		StringBuffer temp = new StringBuffer();
		Pattern pattern = Pattern.compile("[\\\\:*?\"<>|]");
		// 部门名称检查校验
		for (Object[] dept : depts) {
			String deptId = (String) dept[0];
			String deptName = (String) dept[1];
			temp.append(ResourceUtil.getString("third.wx.check.dept.name",
					"third-weixin") + "(<a target='_blank' href='" + dept_urlPre
					+ deptId + "'>" + deptName + "</a>)");
			if (StringUtil.isNull(deptName)) {
				temp.append(ResourceUtil.getString("third.wx.check.dept.notnull", "third-weixin"));
			}
			if (StringUtil.isNotNull(deptName)
					&& deptName.length() > 32) {
				temp.append(ResourceUtil.getString(
						"third.wx.check.dept.namelimit", "third-weixin"));
			}
			if (StringUtil.isNotNull(deptName)) {
				Matcher matcher = pattern.matcher((String)dept[1]);
				if (matcher.find()) {
					temp.append(ResourceUtil.getString(
							"third.wx.check.dept.namesp", "third-weixin"));
				}
			}
			if (!temp.toString()
					.equals(ResourceUtil.getString(
							"third.wx.check.dept.name", "third-weixin")
							+ "(<a target='_blank' href='" + dept_urlPre
							+ deptId+ "'>" + deptName
							+ "</a>)")) {
				rtnDeptList.add(temp + ResourceUtil.getString(
						"third.wx.check.dept.notsysnc", "third-weixin"));
			}
			temp.setLength(0);
		}
	}


	List<String> rtnPersonsList =null;
	Map<String, List<String>> mobileMap = null;
	Map<String, List<String>> mailMap = null;
	String person_urlPre = ResourceUtil.getKmssConfigString("kmss.urlPrefix")
			+ "/sys/organization/sys_org_person/sysOrgPerson.do?method=view&amp;fdId=";
	List<Object[]> allPersons = null;
	Pattern p = Pattern.compile("[a-zA-Z0-9-_@\\.]+");

	@Override
	public List<String> checkPersonInfo(List<String> hiers) throws Exception {

		allPersons = new ArrayList<Object[]>();
		rtnPersonsList = new ArrayList<String>();
		List<String> mobileList = null;
		List<String> mailList = null;
		// 电话号码、邮箱重复处理
		mobileMap = new HashMap<String, List<String>>();
		mailMap = new HashMap<String, List<String>>();
		List<Object[]> persons = null;

		String personId = null;
		String personName = null;
		String personMobileNo = null;
		String personEmail = null;
		for (String hier : hiers) {

			String sql = "select p.fd_id,p.fd_login_name,ele.fd_name,p.fd_mobile_no,p.fd_email from sys_org_person p,sys_org_element ele WHERE p.fd_id=ele.fd_id AND ele.fd_is_available=1 and ele.fd_hierarchy_id like '"+ hier + "%'";
			persons = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(sql).list();

			if(persons==null||persons.isEmpty()) {
                continue;
            }

			allPersons.addAll(persons);
			for(Object[] person:persons){
				 personId = (String) person[0];
				 personName = (String) person[2];
				 personMobileNo = (String) person[3];
				 personEmail = (String) person[4];
				 if (StringUtil.isNotNull(personMobileNo)) {
					if (mobileMap.containsKey(personMobileNo)) {
						mobileList = mobileMap.get(personMobileNo);
					} else {
						mobileList = new ArrayList<String>();
					}
					mobileList.add("<a target='_blank' href='" + person_urlPre
							+ personId + "'>" + personName
							+ "</a>");
					mobileMap.put(personMobileNo, mobileList);
				}
				if (StringUtil.isNotNull(personEmail)) {
					if (mailMap.containsKey(personEmail)) {
						mailList = mailMap.get(personEmail);
					} else {
						mailList = new ArrayList<String>();
					}
					mailList.add("<a target='_blank' href='" + person_urlPre
							+ personId + "'>" + personName
							+ "</a>");
					mailMap.put(personEmail, mailList);
				}
			}
		}

		logger.warn("检查的总人数有："+allPersons.size());
		//分批处理
		int rowsize =500; //每批500
		int count = allPersons.size() % rowsize == 0 ? allPersons.size() / rowsize
				: allPersons.size() / rowsize + 1;
		logger.warn("分{} 批处理检查人员数据",count);
		checkPersonCountDownLatch = new CountDownLatch(count);
		List<Object[]> personsTempList;
		for (int i = 0; i < count; i++) {
			logger.warn("执行第" + (i + 1) + "批");
			if (allPersons.size() > rowsize * (i + 1)) {
				personsTempList = allPersons.subList(rowsize * i, rowsize * (i + 1));
			} else {
				personsTempList = allPersons.subList(rowsize * i, allPersons.size());
			}
			wxWorkOrgCheckExecutor.execute(new SynchroOrg2WxCheckImp.CheckPersonRunner(personsTempList));
		}
		try {
			checkPersonCountDownLatch.await(1, TimeUnit.HOURS);
		} catch (InterruptedException exc) {
			exc.printStackTrace();
			logger.error(exc.getMessage(), exc);
		}

		return rtnPersonsList;
	}

	class CheckPersonRunner implements Runnable {
		private final List<Object[]> persons;

		public CheckPersonRunner(List<Object[]> persons) {
			this.persons = persons;
		}

		@Override
		public void run() {
			try {
				handlePersonsCheckByRunnble(persons);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				checkPersonCountDownLatch.countDown();
			}
		}
	}

	private void handlePersonsCheckByRunnble(List<Object[]> persons) {
		String personId = null;
		String personLoginName = null;
		String personName = null;
		String personMobileNo = null;
		String personEmail = null;
		StringBuffer temp = new StringBuffer();
		List mobileList =null;
		List mailList=null;

		// 校验检查
		for (Object[] person : persons) {
			personId = (String) person[0];
			personLoginName = (String) person[1];
			personName = (String) person[2];
			personMobileNo = (String) person[3];
			personEmail = (String) person[4];

			temp.append(ResourceUtil.getString("third.wx.check.person.name",
					"third-weixin") + "(<a target='_blank' href='" + person_urlPre
					+ personId + "'>" + personName
					+ "</a>)");
			if (StringUtil.isNull(personName)) {
				temp.append(ResourceUtil.getString(
						"third.wx.check.dept.notnull", "third-weixin"));
			}
			if (StringUtil.isNotNull(personName)
					&& personName.length() > 32) {
				temp.append(ResourceUtil.getString(
						"third.wx.check.person.notnull", "third-weixin"));
			}
			if (StringUtil.isNull(personEmail)
					&& StringUtil.isNull(personMobileNo)) {
				temp.append(ResourceUtil.getString(
						"third.wx.check.person.emailtelnotnull",
						"third-weixin"));
			}
			if (mobileMap.containsKey(personMobileNo)) {
				mobileList = mobileMap.get(personMobileNo);
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
			if (mailMap.containsKey(personEmail)) {
				mailList = mailMap.get(personEmail);
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
			isValidate(personLoginName,personMobileNo,personEmail, temp, p);
			if (!temp.toString()
					.equals(ResourceUtil.getString(
							"third.wx.check.person.name", "third-weixin")
							+ "(<a target='_blank' href='" + person_urlPre
							+ personId + "'>" + personName
							+ "</a>)")) {
				rtnPersonsList.add(temp + ResourceUtil.getString(
						"third.wx.check.dept.notsysnc", "third-weixin"));
			}
			temp.setLength(0);
		}

	}

	/**
	 * @param temp
	 * @throws Exception
	 *             人员的登录名、邮件和电话里面不能有空格
	 */
	private void isValidate(String login,String mobile,String mail, StringBuffer temp, Pattern p){
		if (temp == null || p == null) {
            return;
        }
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
		if (StringUtil.isNotNull(mail) && mail.indexOf(" ") != -1) {
			temp.append(ResourceUtil.getString(
					"third.wx.check.person.emailnulltip", "third-weixin"));
		}
		if (StringUtil.isNotNull(mobile) && mobile.indexOf(" ") != -1) {
			temp.append(ResourceUtil.getString(
					"third.wx.check.person.telnulltip", "third-weixin"));
		}
	}

	@Override
    public List<String> getCheckHierarchy() throws Exception {
		List<String> rtn = new ArrayList<String>();
		// 如果配置过了同步的根目录，那么只校验根目录下的数据，否则校验全部数据
		String cid = WeixinWorkConfig.newInstance().getWxOrgId();
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
