package com.landray.kmss.hr.staff.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.hr.staff.model.HrStaffPayrollIssuance;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffSalaryInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPayrollIssuanceService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffSalaryInfoService;
import com.landray.kmss.hr.staff.util.ExcelParseCache;
import com.landray.kmss.hr.staff.util.ResultBean;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.profile.context.SendMailContext;
import com.landray.kmss.sys.profile.service.ISysSendEmailService;
import com.landray.kmss.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class HrStaffPayrollIssuanceSendThread implements Runnable {

	private HrStaffPayrollIssuance hrStaffPayrollIssuance;
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	private IHrStaffSalaryInfoService hrStaffSalaryInfoService;
	private IHrStaffPayrollIssuanceService hrStaffPayrollIssuanceService;
	private ISysOrgPersonService sysOrgPersonService;
	private static final Log logger = LogFactory.getLog(HrStaffPayrollIssuanceSendThread.class);

	/**
	 * 统一消息门户中的发送邮件人
	 */
	private ISysSendEmailService sysSendEmailService;

	public HrStaffPayrollIssuanceSendThread(HrStaffPayrollIssuance hrStaffPayrollIssuance) {
		super();
		this.hrStaffPayrollIssuance = hrStaffPayrollIssuance;
		this.sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		this.hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil.getBean("hrStaffPersonInfoService");
		this.hrStaffSalaryInfoService = (IHrStaffSalaryInfoService) SpringBeanUtil.getBean("hrStaffSalaryInfoService");
		this.hrStaffPayrollIssuanceService = (IHrStaffPayrollIssuanceService) SpringBeanUtil
				.getBean("hrStaffPayrollIssuanceService");
		this.sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");

		this.sysSendEmailService = (ISysSendEmailService) SpringBeanUtil.getBean("sysSendEmailService");

	}

	@Override
	public void run() {
		logger.debug("异步处理发送工资单发送开始...");
		ExcelParseCache cache = ExcelParseCache.getCache(hrStaffPayrollIssuance.getFdId());
		if (cache == null || cache.getParams() == null || cache.getRows() == null) {
			logger.warn("异步处理发送工资单未找到数据：" + hrStaffPayrollIssuance.getFdId());
			ExcelParseCache.clearCache(hrStaffPayrollIssuance.getFdId());
			return;
		}

		String type = (String) cache.getParams().get("type");
		if (StringUtil.isNull(type) || !(type.contains("todo") || type.contains("email"))) {
			logger.warn("异步处理发送工资单类型异常：" + type);
			ExcelParseCache.clearCache(hrStaffPayrollIssuance.getFdId());
			return;
		}
		List<Map<String, Object>> rows = cache.getRows();

		boolean emailFlag = true;
		boolean todoFlag = true;
		ResultBean resultTodo = new ResultBean();
		ResultBean resultEmail = new ResultBean();
		StringBuffer rsTodo = new StringBuffer();
		StringBuffer rsEmail = new StringBuffer();
		rsTodo.append(ResourceUtil.getString("hr-staff:hrStaff.payroll.todo.error"));
		rsTodo.append("\n");
		rsEmail.append(ResourceUtil.getString("hr-staff:hrStaff.payroll.email.error"));
		rsEmail.append("\n");
		int i = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("发送行数：" + rows.size());
		}
		for (Map<String, Object> row : rows) {
			try {
				String userName = (String) row.get("userName");
				String deptName = (String) row.get("deptName");
				String loginName = (String) row.get("loginName");
				String staffNo = (String) row.get("staffNo");
				String fdNo = (String) row.get("fdNo");
				JSONObject jsonContent = (JSONObject) row.get("jsonContent");
				StringBuilder emailContent = (StringBuilder) row.get("emailContent");
				if(StringUtil.isNull(userName) || StringUtil.isNull(loginName) || StringUtil.isNull(staffNo) || StringUtil.isNull(deptName)) {
					if (type.contains("todo")) {
						resultTodo.addFailCount();
						rsTodo.append(ResourceUtil.getString("hrStaff.import.error.num", "hr-staff", null, i + 1));
						if(StringUtil.isNotNull(userName)){
							rsTodo.append(userName);
						}
						rsTodo.append(ResourceUtil.getString("hrStaff.payroll.message.empty.column", "hr-staff"));
						rsTodo.append("\n");
						todoFlag = false;


					}
					//如果是邮件，邮箱内容必须存在
					if (type.contains("email")) {
						//姓名跟工号是必填（考虑到无账号的人员，这里系统账号可以不必填）
						resultEmail.addFailCount();
						rsEmail.append(ResourceUtil.getString("hrStaff.import.error.num", "hr-staff", null, i + 1));
						if(StringUtil.isNotNull(userName)){
							rsEmail.append(userName);
						}
						rsEmail.append(ResourceUtil.getString("hrStaff.payroll.message.empty.column", "hr-staff"));
						rsEmail.append("\n");
						emailFlag = false;
					}
					continue;
				}
				HQLInfo hqlInfo = new HQLInfo();
				StringBuilder sBuilder = new StringBuilder();
				sBuilder.append("1=1");
				if (StringUtil.isNotNull(loginName)) {
					sBuilder.append(" and hrStaffPersonInfo.fdOrgPerson.fdLoginName =:loginName ");
					hqlInfo.setParameter("loginName", loginName);
				}
				if (StringUtil.isNotNull(fdNo)) {
					sBuilder.append(" and hrStaffPersonInfo.fdOrgPerson.fdNo =:fdNo ");
					hqlInfo.setParameter("fdNo", fdNo);
				}
				if (StringUtil.isNotNull(staffNo)) {
					sBuilder.append(" and hrStaffPersonInfo.fdStaffNo =:fdStaffNo ");
					hqlInfo.setParameter("fdStaffNo", staffNo);
				}
				hqlInfo.setWhereBlock(sBuilder.toString());
				List personList = hrStaffPersonInfoService.findList(hqlInfo);
				// 过滤无效数据
				if (personList.isEmpty()) {
					resultTodo.addFailCount();
					rsTodo.append(ResourceUtil.getString("hrStaff.import.error.num", "hr-staff", null, i + 1));
					rsTodo.append(userName);
					rsTodo.append("(").append(deptName).append(")");
					rsTodo.append("\n");
					todoFlag = false;

					emailFlag = false;
					resultEmail.addFailCount();
					rsEmail.append(ResourceUtil.getString("hrStaff.import.error.num", "hr-staff", null, i + 1));
					rsEmail.append(userName);
					rsEmail.append("(").append(deptName).append(")");
					rsEmail.append("\n");
					continue;
				}

				HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) personList.get(0);
				if(hrStaffPersonInfo.getFdOrgPerson() !=null) {
					SysOrgPerson sysOrgPerson = (SysOrgPerson) sysOrgPersonService
							.findByPrimaryKey(hrStaffPersonInfo.getFdOrgPerson().getFdId(), null, true);
					NotifyContext notifyContext = sysNotifyMainCoreService
							.getContext("hr-staff:hrStaffPayrollNotify.notify");
					// 设置发布通知人
					notifyContext.setNotifyTarget(Arrays.asList(new Object[]{sysOrgPerson}));
					if (type.contains("todo")) {
						String salaryId = IDGenerator.generateID();
						HashMap hashMap = new HashMap();
						hashMap.put("hr-staff:hrStaffPayrollIssuance.fdMessageName",
								hrStaffPayrollIssuance.getFdMessageName());
						hashMap.put("hrStaffPayrollNotify.notify.content", hrStaffPayrollIssuance.getFdMessageName());

						notifyContext.setKey(sysOrgPerson.getFdId());
						notifyContext.setNotifyType("todo");
						notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
						notifyContext.setLink(
								"/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do?method=viewSalary&fdId="
										+ salaryId);
						sysNotifyMainCoreService.send(hrStaffPayrollIssuance, notifyContext, hashMap);
						resultTodo.addSuccessCount();

						DESEncrypt des = new DESEncrypt();

						if (jsonContent != null) {
							jsonContent.put("salaryTitle", hrStaffPayrollIssuance.getFdMessageName());
							String content = des.encryptString(jsonContent.toString());

							HrStaffSalaryInfo hrStaffSalaryInfo = new HrStaffSalaryInfo();
							hrStaffSalaryInfo.setFdId(salaryId);
							hrStaffSalaryInfo.setFdSalaryContent(content);
							hrStaffSalaryInfo.setFdPayrollIssuanceId(hrStaffPayrollIssuance.getFdId());
							hrStaffSalaryInfo.setFdPersonId(hrStaffPersonInfo.getFdOrgPerson().getFdId());
							hrStaffSalaryInfoService.add(hrStaffSalaryInfo);
						}
					}
					if (type.contains("email")) {
						String fdEmail = sysOrgPerson.getFdEmail();
						if (StringUtil.isNull(fdEmail)) {
							emailFlag = false;
							resultEmail.addFailCount();
							rsEmail.append(ResourceUtil.getString("hrStaff.import.error.num", "hr-staff", null, i+1));
							rsEmail.append(userName);
							rsEmail.append("(").append(deptName).append(")");
							rsEmail.append(ResourceUtil.getString("hrStaff.payroll.message.empty.emal", "hr-staff"));
							rsEmail.append("\n");
						} else {
							emailContent.append("\n\n").append(
									ResourceUtil.getString("hrStaffPayrollNotify.email.notify.subject", "hr-staff"));
							if (StringUtil.isNotNull(hrStaffPayrollIssuance.getFdSendEmail())
									&& hrStaffPayrollIssuance.getFdSendEmail().startsWith("RECRUIT|")) {
								//人事招聘配置中的发件人
								SendMailContext mailContext = new SendMailContext();
								mailContext.setSubject(hrStaffPayrollIssuance.getFdMessageName());//主题
								mailContext.setHtmlMessage(emailContent.toString());//内容
								List<String> toEmail = new ArrayList();
								toEmail.add(fdEmail);
								mailContext.setMails(toEmail);//收件人
								boolean result = sysSendEmailService.doSendEamil(mailContext, hrStaffPayrollIssuance.getFdSendEmail().replaceAll("RECRUIT\\|", ""), ResourceUtil.getLocaleByUser());
								if (result) {
									resultEmail.addSuccessCount();
								} else {
									resultEmail.addFailCount();
								}
							} else {
								//默认系统发件人
								notifyContext.setKey("payrollKey");

								HashMap hashMap = new HashMap();
								hashMap.put("hr-staff:hrStaffPayrollIssuance.fdMessageName", hrStaffPayrollIssuance.getFdMessageName());
								hashMap.put("hrStaffPayrollNotify.notify.content", emailContent.toString());
								notifyContext.setNotifyType("email");
								notifyContext.setNotAsynchProviderKey("email");
								sysNotifyMainCoreService.send(null, notifyContext, hashMap);
								resultEmail.addSuccessCount();
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
				continue;
			} finally {
				i++;
			}
		}
		try {
			hrStaffPayrollIssuance = (HrStaffPayrollIssuance) hrStaffPayrollIssuanceService.findByPrimaryKey(hrStaffPayrollIssuance.getFdId(), null, true);
			if (!emailFlag || !todoFlag) {
				if ((type.contains("email") && !emailFlag) && (type.contains("todo") && !todoFlag)) {
                    hrStaffPayrollIssuance.setFdResultDetailMseeage(rsTodo.toString() + ";" + rsEmail.toString());
                } else if (type.contains("todo") && !todoFlag) {
                    hrStaffPayrollIssuance.setFdResultDetailMseeage(rsTodo.toString());
                } else if (type.contains("email") && !emailFlag) {
                    hrStaffPayrollIssuance.setFdResultDetailMseeage(rsEmail.toString());
                }
			} else {
				hrStaffPayrollIssuance
						.setFdResultDetailMseeage(ResourceUtil.getString("hr-staff:hrStaff.payroll.success"));
			}
			if (type.contains("email") && type.contains("todo")) {
				hrStaffPayrollIssuance.setFdResultMseeage(ResourceUtil.getString("hrStaff.payroll.todo.result",
						"hr-staff", null, new Object[] { resultTodo.getSuccessCount(), resultTodo.getFailCount() })
						+ ";" + ResourceUtil.getString("hrStaff.payroll.email.result", "hr-staff", null,
								new Object[] { resultEmail.getSuccessCount(), resultEmail.getFailCount() }));

			} else if (type.contains("todo")) {
				hrStaffPayrollIssuance.setFdResultMseeage(ResourceUtil.getString("hrStaff.payroll.todo.result",
						"hr-staff", null, new Object[] { resultTodo.getSuccessCount(), resultTodo.getFailCount() }));

			} else if (type.contains("email")) {
				hrStaffPayrollIssuance.setFdResultMseeage(ResourceUtil.getString("hrStaff.payroll.email.result",
						"hr-staff", null, new Object[] { resultEmail.getSuccessCount(), resultEmail.getFailCount() }));
			}
			hrStaffPayrollIssuanceService.update(hrStaffPayrollIssuance);

			logger.debug(hrStaffPayrollIssuance.getFdMessageName());

			// 发送待办通知发放人
			SysOrgPerson sender = (SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(hrStaffPayrollIssuance.getFdCreator().getFdId(), null, true);
			NotifyContext notifyContext = sysNotifyMainCoreService
					.getContext("hr-staff:hrStaffPayrollNotify.notify.result");
			// 设置发布通知人
			notifyContext.setNotifyTarget(Arrays.asList(new Object[] { sender }));

			// 您于#发送时间#发送的#推送消息名称##发送消息#
			Date fdCreateTime = hrStaffPayrollIssuance.getFdCreateTime();
			String fdMessageName = hrStaffPayrollIssuance.getFdMessageName();
			String result = "发送完成";
			notifyContext.setKey(sender.getFdId());
			notifyContext.setNotifyType("todo");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
			notifyContext.setLink("/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do?method=view&fdId="
					+ hrStaffPayrollIssuance.getFdId());
			HashMap hashMap = new HashMap();
			hashMap.put("result.sendTime", DateFormatUtils.format(fdCreateTime, "yyyy年MM月dd日HH时mm分"));
			hashMap.put("result.messageName", fdMessageName);
			hashMap.put("result.sendResult", result);

			sysNotifyMainCoreService.send(hrStaffPayrollIssuance, notifyContext, hashMap);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			ExcelParseCache.clearCache(hrStaffPayrollIssuance.getFdId());
		}
	}
}
