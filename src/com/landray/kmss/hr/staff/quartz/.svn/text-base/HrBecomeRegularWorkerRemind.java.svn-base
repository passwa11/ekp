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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author sunny
 * @version 创建时间：2022年9月9日下午1:50:54
 * @see 转正流程，POS其他类人员：根据人事系统的拟转正日期的30天前提醒到人事（具体人员是彭舒燕），人事批量选择触发转正提醒流程至拟转正员工直接上级，直接上级审批。
 *      （POS其他类人员，是以职级来判断，排除职级为M开头的人员，剩下的就是POS其他类。M类包括M3到M20的职级。
 *      如果以职类来判断也可以，职类对应的职级是：M（管理类）、P（业务类、技术来、客户类、客服类）、O（操作类、操作辅助类）、S（销售类）、实习生。之前客户提供职类时没有提供管理类，所以现在系统上还没有加上这个类型。
 *      职级和职类都在人事档案的人员信息中）
 * 
 */
public class HrBecomeRegularWorkerRemind {
	
	private static final Log log = LogFactory.getLog(HrBecomeRegularWorkerRemind.class);

	/**
	 * 转正流程
	 */
	public void createForm(SysQuartzJobContext context) throws Exception {
		KmReviewParamterForm form = new KmReviewParamterForm();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		form.setFdTemplateId("18321a1351f04d36b5346794da383944");

		// 文档标题
		form.setDocSubject(sdf.format(new Date()) + "拟转正日期的30天前提醒人事");

		// 流程发起人（固定人员：彭舒燕）
		form.setDocCreator(HrCurrencyParams.getStaffQuartzCreator("HrBecomeRegularWorkerRemind"));

		// 文档关键字
		form.setFdKeyword("[\"人事\", \"合同\"]");

		// 流程参数
		String flowParam = "{auditNode:\"系统定时器起草单据\"}";
		form.setFlowParam(flowParam);

		List<Map<String, Object>> personlist = new ArrayList<Map<String, Object>>();
		JSONObject jo = new JSONObject();

		// fd_3b1248b45e2076 拟转正明细表
		personlist = getBecomeRegularWorkerPerson();

		if (personlist.size() > 0) {
			JSONObject ja_person = new JSONObject();

			JSONArray ja_1 = new JSONArray();
			JSONArray ja_2 = new JSONArray();
			JSONArray ja_3 = new JSONArray();
			JSONArray ja_4 = new JSONArray();

			for (Map<String, Object> map : personlist) {
				// fd_3b1248c0be2a0c 拟转正人员
				if (map.get("fd_org_person_id") != null) {
					ja_1.add(map.get("fd_org_person_id"));
				} else {
					ja_1.add("");
				}

				// fd_3b147ac64b4cca 职等名称
				if (map.get("rank_name") != null) {
					ja_2.add(map.get("rank_name"));
				} else {
					ja_2.add("");
				}

				// fd_3b1248cd2a5bc6 拟转正人员所属部门
				if (map.get("fd_org_parent_id") != null) {
					ja_3.add(map.get("fd_org_parent_id"));
				} else {
					ja_3.add("");
				}

				// fd_3b1248dbc43756  拟转正时间
				if (map.get("fd_proposed_employment_confirmation_date") != null) {
					ja_4.add(map.get("fd_proposed_employment_confirmation_date")+"");
				} else {
					ja_4.add("");
				}
			}

			if (ja_1.size() > 0) {// fd_3b1248c0be2a0c 拟转正人员
				ja_person.put("fd_3b1248b45e2076.fd_3b1248c0be2a0c", ja_1);
			}

			if (ja_2.size() > 0) {// fd_3b147ac64b4cca 职等名称
				ja_person.put("fd_3b1248b45e2076.fd_3b147ac64b4cca", ja_2);
			}

			if (ja_3.size() > 0) {// fd_3b1248cd2a5bc6 拟转正人员所属部门
				ja_person.put("fd_3b1248b45e2076.fd_3b1248cd2a5bc6", ja_3);
			}

			if (ja_4.size() > 0) {// fd_3b1248dbc43756 拟转正时间
				ja_person.put("fd_3b1248b45e2076.fd_3b1248dbc43756", ja_4);
			}

			jo.put("fd_3b1248b45e2076", ja_person);
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
		context.logMessage("总行数:" + personlist.size());
		context.logMessage("结束【人事系统的拟转正日期的30天前提醒】到期提醒");
	}

	private List<Map<String, Object>> getBecomeRegularWorkerPerson() throws Exception {

		List<Map<String, Object>> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer();

		sb.append("select h.*,r.fd_name as rank_name from hr_staff_person_info h ");
		sb.append("left join hr_org_rank r on h.fd_org_rank_id=r.fd_id ");
		sb.append("where DATE_ADD(CURDATE(), INTERVAL 30 DAY)=date(fd_proposed_employment_confirmation_date) ");
		// sb.append("and r.fd_name not like 'M%'");

		list = HrCurrencyParams.getListBySql(sb.toString());
		return list;
	}
}
