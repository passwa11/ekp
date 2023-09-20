package com.landray.kmss.hr.staff.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import net.sf.json.JSONObject;

/**
 * 
 * @author sunny
 * @version 创建时间：2022年9月9日下午1:50:54
 * @see 离职手续办理提醒
 * 
 */
public class HrLeaveHandelRemind {
	private static final Log log = LogFactory.getLog(HrLeaveHandelRemind.class);

	/**
	 * 提前3天离职手续办理提醒
	 */
	public void createForm(SysQuartzJobContext context) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List<Map<String, Object>> personlist = new ArrayList<Map<String, Object>>();

		personlist = getLeavePerson();
		if (personlist.size() > 0) {
			for (Map<String, Object> map : personlist) {
				KmReviewParamterForm form = new KmReviewParamterForm();
				// 文档模板id
				form.setFdTemplateId("1833019f8e88894c137565f464b8dc60");

				// 文档标题
				form.setDocSubject(map.get("fd_name") + sdf.format(new Date()) + "离职手续办理提醒");

				// 流程发起人（固定人员：彭舒燕）
				form.setDocCreator(HrCurrencyParams.getStaffQuartzCreator("HrLeaveHandelRemind"));

				// 文档关键字
				form.setFdKeyword("[\"人事\", \"离职\"]");

				// 流程参数
				String flowParam = "{auditNode:\"系统定时器起草单据\"}";
				form.setFlowParam(flowParam);

				JSONObject jo = new JSONObject();
				jo.put("fd_staff_id", map.get("fd_staff_id"));// 提醒人员

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
		context.logMessage("结束【预离职日期提前3天提醒离职手续办理】到期提醒");
	}

	private List<Map<String, Object>> getLeavePerson() throws Exception {

		List<Map<String, Object>> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer();

		sb.append("select h.*,s.fd_name,s.fd_staff_no from ekp_staff_leave_apply h");
		sb.append(" left join km_review_main k on h.fd_id=k.fd_id left join hr_staff_person_info s on h.fd_staff_id=fd_org_person_id");
		sb.append(" where DATE_ADD(CURDATE(), INTERVAL 7 DAY)=date(h.fd_leave_apply_date)");
		sb.append(" and k.doc_status='30'");

		list = HrCurrencyParams.getListBySql(sb.toString());

		return list;
	}

}
