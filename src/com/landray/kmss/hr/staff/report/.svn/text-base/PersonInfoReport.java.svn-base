package com.landray.kmss.hr.staff.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.util.SpringBeanUtil;

import oracle.jdbc.proxy.annotation.Post;

/**
 * 
 * 
 * @author sunny
 * @version 创建时间：2022年9月14日下午4:47:44
 */
public class PersonInfoReport {
	private static final Log log = LogFactory.getLog(PersonInfoReport.class);

	/**
	 * 个税人员信息采集表(每月26日将上月26日至当月25日入职的人员信息按财务模板反馈人事)
	 * 
	 * @return
	 */
	public String incomeTaxGetCompany(String fd_affiliated_company) {
		String spn = "select * from hr_staff_person_info_set_new where fd_type='fdAffiliatedCompany'";
		List<Map<String, Object>> list_spn;
		String result = "";
		try {
			list_spn = HrCurrencyParams.getListBySql(spn + " and fd_order ='" + fd_affiliated_company + "'");
			if (list_spn.size() > 0) {
				result = list_spn.get(0).get("fd_name") + "";
			}
		} catch (Exception e) {
			log.error(e + spn);
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 个税人员信息采集表(每月26日将上月26日至当月25日入职的人员信息按财务模板反馈人事)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> incomeTaxReport(String shangyue_26, String benyue_25, String company) {

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		// ca.setTime(new Date()); // 设置时间为当前时间
		//
		// // 每月28日自动提醒上月26日至当月25日入职的员工
		// ca.set(Calendar.DAY_OF_MONTH, 25);
		// String benyue_25 = sdf.format(ca.getTime());
		//
		// ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
		// ca.set(Calendar.DAY_OF_MONTH, 26);
		// String shangyue_26 = sdf.format(ca.getTime());

		List<Map<String, Object>> personlist = new ArrayList<Map<String, Object>>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from hr_staff_person_info where ");// fd_status='official'
		sql.append(" fd_entry_time between '" + shangyue_26 + "' and '" + benyue_25 + "'");

		if (company != null) {
			String company_name = incomeTaxGetCompany(company);
			sql.append("and fd_affiliated_company like '%" + company_name + "%'");
		}

		try {
			personlist = HrCurrencyParams.getListBySql(sql.toString());
		} catch (Exception e) {
			log.error(e + sql.toString());
			e.printStackTrace();
		}
		return personlist;

	}

	/**
	 * 人事月报-当月新员工入职统计(统计一级部门人员)
	 * 1日至31日
	 * @return
	 */
	public List<Map<String, Object>> entryMonthReport(String y,String m) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date()); // 设置时间为当前时间

		//m月1日
		ca.set(Calendar.YEAR, Integer.valueOf(y));
		ca.set(Calendar.MONTH, Integer.valueOf(m)-1);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		String benyue_1 = sdf.format(ca.getTime());

		//m月最后一日
		ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
		String benyue_31 = sdf.format(ca.getTime());
		List<Map<String, Object>> list_result = new ArrayList<Map<String, Object>>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from hr_staff_person_info where ");// fd_status='official'
		sql.append(" fd_entry_time between '" + benyue_1 + "' and '" + benyue_31 + "'");

		IHrStaffPersonInfoService hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil
				.getBean("hrStaffPersonInfoService");

		try {
			List<Map<String, Object>> list_data = HrCurrencyParams.getListBySql(sql.toString());
			if (list_data.size() > 0) {
				for (int i = 0; i < list_data.size(); i++) {

					Map<String, Object> map_result = list_data.get(i);
					String fd_person_id = list_data.get(i).get("fd_id") + "";
					HrStaffPersonInfo personInfo = (HrStaffPersonInfo) hrStaffPersonInfoService
							.findByPrimaryKey(fd_person_id);

					if (personInfo.getFdFirstLevelDepartment() != null) {
						map_result.put("fd_first_level_department",
								personInfo.getFdFirstLevelDepartment().getFdName() + "");
					} else {
						map_result.put("fd_first_level_department", "");
					}
					if (personInfo.getFdSecondLevelDepartment() != null) {
						map_result.put("fd_second_level_department",
								personInfo.getFdSecondLevelDepartment().getFdName() + "");
					} else {
						map_result.put("fd_second_level_department", "");
					}
					if (personInfo.getFdThirdLevelDepartment() != null) {
						map_result.put("fd_third_level_department",
								personInfo.getFdThirdLevelDepartment().getFdName() + "");
					} else {
						map_result.put("fd_third_level_department", "");
					}

					map_result.put("fd_work_in_this_company", personInfo.getFdWorkingYears() + "");
					map_result.put("fd_age", personInfo.getFdAge() + "");
					map_result.put("fd_staffinglevel_name",
							personInfo.getFdStaffingLevel() != null ? personInfo.getFdStaffingLevel().getFdName() : "");

					map_result.put("fd_rank_name",
							personInfo.getFdOrgRank() != null ? personInfo.getFdOrgRank().getFdName() : "");

					String fd_post_name = "";
					if (personInfo.getFdPosts().size() > 0) {
						List<HrOrganizationElement> hrOrganizationElementList = personInfo.getFdPosts();
						for(HrOrganizationElement p : hrOrganizationElementList){
							fd_post_name = fd_post_name + " " + p.getFdName();
						}
					}
					map_result.put("fd_post_name", fd_post_name);

					List<Map<String, Object>> list_count = HrCurrencyParams
							.getListBySql("select * from hr_staff_person_exp_cont where fd_person_info_id='"
									+ fd_person_id + "' ORDER BY fd_create_time desc");

					if (list_count.size() > 0) {
						Map<String, Object> map = list_count.get(0);
						map_result.put("cont_fd_begin_date", map.get("fd_begin_date"));
						map_result.put("cont_fd_end_date", map.get("fd_end_date"));
						map_result.put("fd_contract_year", map.get("fd_contract_year"));
						map_result.put("fd_contract_month", map.get("fd_contract_month"));
					} else {
						map_result.put("cont_fd_begin_date", "");
						map_result.put("cont_fd_end_date", "");
						map_result.put("fd_contract_year", "0");
						map_result.put("fd_contract_month", "0");
					}
					list_result.add(map_result);

				}
			}
		} catch (Exception e) {
			log.error(e + sql.toString());
			e.printStackTrace();
		}

		return list_result;

	}

	public String dateToString(Object str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.format(sdf.parse(str + ""));
		} catch (ParseException e) {
			log.error(e + "时间转换错误" + str);
			return "";
		}
	}

}
