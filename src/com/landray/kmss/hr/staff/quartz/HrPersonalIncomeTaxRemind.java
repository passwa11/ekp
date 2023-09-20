package com.landray.kmss.hr.staff.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.hr.staff.report.HrCurrencyParams;
import com.landray.kmss.hr.staff.report.PersonInfoReport;
import com.landray.kmss.km.review.webservice.IKmReviewWebserviceService;
import com.landray.kmss.km.review.webservice.KmReviewParamterForm;
import com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.util.SysNotifyTypeEnum;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author sunny
 * @version 创建时间：2022年9月9日下午1:50:54
 * @see 个税报表与自动提醒功能，每月28日自动提醒上月26日至当月25日入职的员工
 * 
 */
public class HrPersonalIncomeTaxRemind {

	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private static final Log log = LogFactory.getLog(HrPersonalIncomeTaxRemind.class);

	/**
	 * 个税个人提醒
	 */
	public void createFormPerson(SysQuartzJobContext context) throws Exception {
		KmReviewParamterForm form = new KmReviewParamterForm();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date()); // 设置时间为当前时间

		// 每月28日自动提醒上月26日至当月25日入职的员工
		ca.set(Calendar.DAY_OF_MONTH, 25);
		String end = sdf.format(ca.getTime());

		ca.set(Calendar.DAY_OF_MONTH, 28);
		SimpleDateFormat sdfh = new SimpleDateFormat("yyyy年MM月dd日");
		String tixing_date = sdfh.format(ca.getTime());

		ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
		ca.set(Calendar.DAY_OF_MONTH, 26);
		String start = sdf.format(ca.getTime());

		List<Map<String, Object>> personlist = new ArrayList<Map<String, Object>>();
		personlist = getBecomeRegularWorkerPerson(start, end);
		if (personlist.size() > 0) {
			for (Map<String, Object> map : personlist) {

				// 文档模板id
				form.setFdTemplateId("1832fcea73ab3fd2615df004de386d38");

				// 文档标题
				form.setDocSubject(
						map.get("fd_name") + "(" + map.get("fd_staff_no") + ")" + sdf.format(new Date()) + "个税自动提醒");

				// 流程发起人（固定人员：彭舒燕）
				form.setDocCreator(HrCurrencyParams.getStaffQuartzCreator("HrLeaveHandelRemind"));

				// 文档关键字
				form.setFdKeyword("[\"人事\", \"个税\"]");

				// 流程参数
				String flowParam = "{auditNode:\"系统定时器起草单据\"}";
				form.setFlowParam(flowParam);

				JSONObject jo = new JSONObject();
				if (map.get("fd_org_person_id") != null) {
					jo.put("fd_3b147cad7dd482", map.get("fd_org_person_id"));// 提醒人员
				} else {
					jo.put("fd_3b147cad7dd482", "");// 提醒人员
				}

				jo.put("fd_3b147d08b91c8a", tixing_date);// 处理时间（文本）

				// 流程表单
				form.setFormValues(jo.toString());

				IKmReviewWebserviceService kmReviewWebserviceService = (IKmReviewWebserviceService) SpringBeanUtil
						.getBean("kmReviewWebserviceService");
				try {
					String result = kmReviewWebserviceService.addReview(form);
					if (result.length() == 32) {
						context.logMessage("执行成功" + jo);
					} else {
						context.logMessage("执行失败" + jo + "  返回值：" + result);
					}
				} catch (Exception e) {
					context.logMessage("执行失败" + jo);
					log.error(e);
				}
			}
		}
		context.logMessage("总行数:" + personlist.size());
		context.logMessage("结束【个税自动提醒个人功能】到期提醒");
	}

	private List<Map<String, Object>> getBecomeRegularWorkerPerson(String start, String end) throws Exception {

		List<Map<String, Object>> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer();

		sb.append("select * from hr_staff_person_info where fd_entry_time between ");
		sb.append("'" + start + "' and ");
		sb.append("'" + end + "'");

		list = HrCurrencyParams.getListBySql(sb.toString());
		return list;
	}

	private ISysOrgPersonService sysOrgPersonService;

	private SysOrgPerson getCompanyCaiWuPerson(String company) throws Exception {

		sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");

		List<Map<String, Object>> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer();

		sb.append("select c.*,m.fd_hr_person_id from ekp_remind_set_child c LEFT JOIN ekp_remind_set_main ");
		sb.append(" m on m.fd_id=c.fd_parent_id where 1=1 ");

		String field = null;
		if (company == null) {// 人事
			sb.append(" and m.fd_hr_person_id is not null ");
			field = "fd_hr_person_id";
		} else {// 财务
			sb.append(" and c.fd_company_name like '%" + company + "%' and c.fd_company_person_id is not null ");
			field = "fd_company_person_id";
		}
		sb.append(" order by m.fd_create_time desc");

		list = HrCurrencyParams.getListBySql(sb.toString());
		if (list.size() > 0) {
			return (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(list.get(0).get(field) + "");
		} else {
			return null;
		}

	}

	/**
	 * 提醒人事财务
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void createFormFinance(SysQuartzJobContext context) throws Exception {
		// 提醒人事：所有数据
		// 提醒财务：按照所属公司提醒
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date()); // 设置时间为当前时间

		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH) + 1;

		// 每月28日自动提醒上月26日至当月25日入职的员工
		ca.set(Calendar.DAY_OF_MONTH, 25);
		String benyue_25 = sdf.format(ca.getTime());

		ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
		ca.set(Calendar.DAY_OF_MONTH, 26);
		String shangyue_26 = sdf.format(ca.getTime());

		PersonInfoReport hrPersonalReport = new PersonInfoReport();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		list = hrPersonalReport.incomeTaxReport(shangyue_26, benyue_25, null);

		//
		List<String> company_name_list = new ArrayList<String>();
		List<Map<String, Object>> send_url_list = new ArrayList<Map<String, Object>>();

		String spn = "select * from hr_staff_person_info_set_new where fd_type='fdAffiliatedCompany'";
		if (list.size() > 0) {
			for (Map<String, Object> map : list) {
				String fd_affiliated_company = map.get("fd_affiliated_company") + "";
				if (!fd_affiliated_company.equals("null")) {
					if (!company_name_list.contains(fd_affiliated_company)) {
						
						List<Map<String, Object>> list_spn = HrCurrencyParams
								.getListBySql(spn + " and fd_name like '%" + fd_affiliated_company + "%'");
						if (list_spn.size() > 0) {
							Map<String, Object> data_map = new HashMap<String, Object>();
							data_map.put("fd_order_remind", list_spn.get(0).get("fd_order"));
							data_map.put("fd_affiliated_company", list_spn.get(0).get("fd_name"));
							send_url_list.add(data_map);
							company_name_list.add(list_spn.get(0).get("fd_name") + "");
						}
						
					}
				}
			}
		}
		context.logMessage("发财务人员:");
		if (send_url_list.size() > 0) {
			for (Map<String, Object> map : send_url_list) {
				String fd_affiliated_company = map.get("fd_affiliated_company") + "";
				String fd_order_remind = map.get("fd_order_remind") + "";
				SysOrgPerson sysOrgPerson = getCompanyCaiWuPerson(fd_affiliated_company);
				if (sysOrgPerson != null) {
					context.logMessage(
							fd_affiliated_company + "财务人员：" + sysOrgPerson.getFdName() + sysOrgPerson.getFdLoginName());
					context.logMessage(sendNotifyPay(getCompanyCaiWuPerson(fd_affiliated_company), year, month,
							fd_affiliated_company, fd_order_remind));
				} else {
					context.logMessage(fd_affiliated_company + "未设置财务人员");
				}
			}
		}

		context.logMessage("发人事人员:");
		context.logMessage(sendNotifyPay(getCompanyCaiWuPerson(null), year, month, null, null));

		context.logMessage("结束【个税自动报表提醒功能】提醒");
	}

	public String sendNotifyPay(SysOrgPerson sendPerson, int paramsYear, int paramsMonth, String companyName,
			String paramsCompany) {
		String type = paramsCompany == null ? "人事" : companyName + "财务";

		String error = "个税 " + type + "报表提醒人员：" + sendPerson.getFdName() + sendPerson.getFdLoginName();
		if (sendPerson != null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
			ModelingAppSimpleMain m = null;
			String id = IDGenerator.generateID();
			String docSubject = paramsYear + "年" + paramsMonth + "月个税报表提醒（" + type + "）";
			String url = "/hr/staff/report/hrPersonalIncomeTaxReport.jsp" + "?y=" + paramsYear + "&m=" + paramsMonth
					+ "&id=" + id;

			NotifyContext notifyContext = sysNotifyMainCoreService.getContext(null);
			notifyContext.setSubject(docSubject);
			notifyContext.setContent(docSubject);
			notifyContext.setLinkSubject(docSubject); // 链接主题

			// 責任人員
			List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
			targets.add(sendPerson);

			notifyContext.setNotifyTarget(targets);
			notifyContext.setNotifyType(SysNotifyTypeEnum.fdNotifyType.TODO.getKey());// 处理类型

			// notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
			notifyContext.setKey(id); // 设置key方便后面删除

			if (paramsCompany != null) {
				url = url + "&c=" + paramsCompany;
			}
			notifyContext.setLink(url);
			try {
				sysNotifyMainCoreService.sendNotify(m, notifyContext, null);
				error = error + "成功";
			} catch (Exception e) {
				log.info(error);
				log.info(e.getMessage());
			}
		}
		return error;
	}

}
