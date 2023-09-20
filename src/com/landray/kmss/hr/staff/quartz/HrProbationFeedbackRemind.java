package com.landray.kmss.hr.staff.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.hr.staff.report.HrCurrencyParams;
import com.landray.kmss.km.review.webservice.IKmReviewWebserviceService;
import com.landray.kmss.km.review.webservice.KmReviewParamterForm;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.SpringBeanUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author sunny
 * @version 创建时间：2022年10月27日下午1:50:54
 * @see 员工试用期报告-入职第一周
 * 
 */
public class HrProbationFeedbackRemind {

	private static final Log log = LogFactory.getLog(HrProbationFeedbackRemind.class);

	/**
	 * 员工试用期报告-入职第一周
	 */
	public void createForm(SysQuartzJobContext context) throws Exception {
		KmReviewParamterForm form = new KmReviewParamterForm();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date()); // 设置时间为当前时间

		List<Map<String, Object>> personlist = new ArrayList<Map<String, Object>>();
		personlist = getBecomeRegularWorkerPerson();
		if (personlist.size() > 0) {
			for (Map<String, Object> map : personlist) {

				// 文档模板id
				form.setFdTemplateId("18413160333a99da20d63f945d5a8fff");

				// 文档标题Employee care


				form.setDocSubject(
						map.get("fd_name") + "(" + map.get("fd_staff_no") + ")" + sdf.format(new Date()) + "入职第一周试用期报告");

				// 流程发起人（固定人员：彭舒燕）
				form.setDocCreator(HrCurrencyParams.getStaffQuartzCreator("HrProbationFeedbackRemind"));

				// 文档关键字
				form.setFdKeyword("[\"人事\", \"试用期\"]");

				// 流程参数
				String flowParam = "{auditNode:\"系统定时器起草单据\"}";
				form.setFlowParam(flowParam);

				JSONObject jo = new JSONObject();
				if (map.get("fd_org_person_id") != null) {
					jo.put("fd_3aea330ccbc13e", map.get("fd_org_person_id"));// 报告人员
				} else {
					context.logMessage(map.get("fd_name") + "无组织架构，发送失败：" + jo);
					continue;
				}
				// fd_3aea3315260f52 提交人部门部门
				jo.put("fd_3aea3315260f52", map.get("fd_org_parent_id"));
				// fd_3aea3325b15802 报告类型
				jo.put("fd_3aea3325b15802", "7个工作日");
				// fd_3b25638560499c 职类 fd_category
				jo.put("fd_3b25638560499c", map.get("fd_category"));
				// fd_3b256384169650 职级系数 fd_weight
				jo.put("fd_3b256384169650", map.get("rank_weight"));
				// fd_3b256386a344c0 职级 rank_name
				jo.put("fd_3b256386a344c0", map.get("rank_name"));

				// fd_3b376d21660fc6 入职1个月的时间
				jo.put("fd_3b376d21660fc6", map.get("fd_entry_time_1") + "");
				// fd_3b376d63d2b3b6 拟转正减30天
				jo.put("fd_3b376d63d2b3b6", map.get("fd_entry_time_2") + "");
				// fd_3b376dacd58ed4 拟转正减3天
				jo.put("fd_3b376dacd58ed4", map.get("fd_entry_time_5") + "");
				
				// fd_3b37e1eb2e5a40  试用期
				jo.put("fd_3b37e1eb2e5a40", map.get("fd_probation_period") + "");

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
					context.logMessage(e.getMessage());
					log.error(e);
				}
			}
		}
	}

	private List<Map<String, Object>> getBecomeRegularWorkerPerson() throws Exception {

		List<Map<String, Object>> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer();

		sb.append("select h.*,r.fd_name as rank_name,r.fd_weight as rank_weight, ");
		sb.append(" DATE_ADD(date(fd_entry_time), INTERVAL 30 DAY) as fd_entry_time_1, ");
		sb.append(" DATE_SUB(date(fd_proposed_employment_confirmation_date), INTERVAL 3 DAY) as fd_entry_time_2, ");
		sb.append(" DATE_SUB(date(fd_proposed_employment_confirmation_date), INTERVAL 30 DAY) as fd_entry_time_5 ");
		sb.append(" from hr_staff_person_info h ");
		sb.append(" left join hr_org_rank r on h.fd_org_rank_id=r.fd_id ");
		sb.append(" where DATE_ADD(date(fd_entry_time), INTERVAL 7 DAY)= CURDATE() ");

		list = HrCurrencyParams.getListBySql(sb.toString());
		return list;
	}

	
}
