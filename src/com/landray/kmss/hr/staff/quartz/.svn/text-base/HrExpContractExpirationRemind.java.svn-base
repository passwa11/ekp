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
 * @see 根据人事系统的合同结束日期的45天前提醒到人事，人事批量选择触发续签提醒流程
 */
public class HrExpContractExpirationRemind {
	private static final Log log = LogFactory.getLog(HrExpContractExpirationRemind.class);

	/**
	 * 合同结束日期的45天前提醒
	 */
	public void createForm(SysQuartzJobContext context) throws Exception {
		KmReviewParamterForm form = new KmReviewParamterForm();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// 文档模板id
		form.setFdTemplateId("183216635033ca23c93d210446e9e13f");

		// 文档标题
		form.setDocSubject(sdf.format(new Date()) + "合同结束日期的45天前发起提醒到人事");

		// 流程发起人（固定人员：彭舒燕）
		form.setDocCreator(HrCurrencyParams.getStaffQuartzCreator("HrExpContractExpirationRemind"));

		// 文档关键字
		form.setFdKeyword("[\"人事\", \"合同\"]");

		// 流程参数
		String flowParam = "{auditNode:\"系统定时器起草单据\"}";
		form.setFlowParam(flowParam);

		List<Map<String, Object>> personlist = new ArrayList<Map<String, Object>>();
		JSONObject jo = new JSONObject();
		jo.put("fd_3b124d33a1b4a0", sdf.format(new Date()));// 提交时间

		personlist = getExpirationPerson();

		if (personlist.size() > 0) {
			JSONObject ja_person = new JSONObject();

			JSONArray ja_1 = new JSONArray();
			JSONArray ja_2 = new JSONArray();
			JSONArray ja_3 = new JSONArray();

			for (Map<String, Object> map : personlist) {
				// fd_3b1248c0be2a0c合同到期人员
				if(map.get("fd_person_info_id") != null){
					ja_1.add(map.get("fd_person_info_id"));
				} else {
					ja_1.add("");
				}
				
				// fd_3b1248cd2a5bc6所属部门
				if(map.get("fd_org_parent_id") != null){
					ja_2.add(map.get("fd_org_parent_id"));
				} else {
					ja_2.add("");
				}
				
				// fd_3b1248dbc43756合同到期时间
				if(map.get("fd_end_date") != null){
					ja_3.add(map.get("fd_end_date")+"");
				} else {
					ja_3.add("");
				}
			}
			
			if(ja_1.size() > 0){// fd_3b1248c0be2a0c合同到期人员
				ja_person.put("fd_3b1248b45e2076.fd_3b1248c0be2a0c", ja_1);
			}
			
			if(ja_2.size() > 0){// fd_3b1248cd2a5bc6所属部门
				ja_person.put("fd_3b1248b45e2076.fd_3b1248cd2a5bc6", ja_2);
			}

			if(ja_3.size() > 0){// fd_3b1248dbc43756合同到期时间
				ja_person.put("fd_3b1248b45e2076.fd_3b1248dbc43756", ja_3);
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
		context.logMessage("结束【人事系统的合同结束日期的45天前发起提醒】到期提醒");
	}

	private List<Map<String, Object>> getExpirationPerson() throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		sb.append("select c.*,i.fd_org_parent_id,i.fd_org_person_id from hr_staff_person_exp_cont c ");
		sb.append("left join hr_staff_person_info i on c.fd_person_info_id=i.fd_id ");
		sb.append("where DATE_ADD(CURDATE(), INTERVAL 45 DAY)=date(c.fd_end_date) and c.fd_cont_status=1 order  by c.fd_end_date desc");

		list = HrCurrencyParams.getListBySql(sb.toString());
		return list;

	}
}
