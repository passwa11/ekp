package com.landray.kmss.hr.staff.report;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 人事月报-人员结构分布表（职务类别不含实习生临时工，其他均含）
 * 
 * @author sunny
 * @version 创建时间：2022年9月30日下午5:30:03
 */
public class PersonStructureMonthReport {
	private static final Log log = LogFactory.getLog(PersonStructureMonthReport.class);

	/**
	 * 人事月报-人员结构分布表（职务类别不含实习生临时工，其他均含）
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> structureMonthReport(String y,String m) throws SQLException {
		if(y.equals("") || m.equals("")){
			return new ArrayList<Map<String, Object>>();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date()); // 设置时间为当前时间
		
		ca.set(Calendar.YEAR, Integer.valueOf(y));
		ca.set(Calendar.MONTH, Integer.valueOf(m)-1);
		// 本月最后一天
		ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
		String benyue_31 = sdf.format(ca.getTime());

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		// 先取出机构
		String sql = "select h.* from hr_org_element h where fd_is_available=1";
		List<Map<String, Object>> jigou_list = new ArrayList<Map<String, Object>>();
		try {
			jigou_list = HrCurrencyParams.getListBySql(sql + " and fd_org_type=1");
		} catch (Exception e) {
			log.error(e + sql.toString());
			e.printStackTrace();
		}

		if (jigou_list.size() == 0) {
			return list;
		}

		for (Map<String, Object> jigou_map : jigou_list) {
			List<Map<String, Object>> dept_list = new ArrayList<Map<String, Object>>();
			try {
				dept_list = HrCurrencyParams
						.getListBySql(sql + " and fd_org_type=2 and fd_parentid='" + jigou_map.get("fd_id") + "'");
			} catch (Exception e) {
				log.error(e + sql.toString());
				e.printStackTrace();
			}

			if (dept_list.size() == 0) {
				continue;
			}
			for (Map<String, Object> dep_map : dept_list) {
				String dep_id = dep_map.get("fd_id") + "";
				Map<String, Object> map = new HashMap<String, Object>();

				//部门名称
				map.put("fd_dept_name", dep_map.get("fd_name") + "");
				
				//人数小计
				map.put("fd_dept_person_count", getWhereBy1(dep_id, null));
				
				// 婚姻(未婚人数 已婚人数 已育人数)
				map.put("fd_marital_status_weihun", getWhereBy1(dep_id, "fd_marital_status='未婚'"));
				map.put("fd_marital_status_yihun", getWhereBy1(dep_id, "fd_marital_status like '%已婚%'"));
				map.put("fd_marital_status_yiyu", getWhereBy1(dep_id, "fd_marital_status like '%已育%'"));

				// 性别
				map.put("fd_sex_f", getWhereBy1(dep_id, "fd_sex='M'"));// 男
				map.put("fd_sex_m", getWhereBy1(dep_id, "fd_sex='F'"));// 女

				// 学历
				map.put("fd_highest_education_dazhuanyixia", getWhereBy1(dep_id, "fd_highest_education in ('中专','高中','初中及以下')"));
				map.put("fd_highest_education_dazhuan", getWhereBy1(dep_id, "fd_highest_education='大专'"));
				map.put("fd_highest_education_benke", getWhereBy1(dep_id, "fd_highest_education='本科'"));
				map.put("fd_highest_education_shuoshi", getWhereBy1(dep_id, "fd_highest_education='研究生'"));
				map.put("fd_highest_education_boshi", getWhereBy1(dep_id, "fd_highest_education='博士'"));

				// 年龄结构
				map.put("fd_age_18_20_f", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') >=18 and TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') <= 20 and fd_sex='M' "));
				map.put("fd_age_18_20_m", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') >=18 and TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') <= 20 and fd_sex='F' "));
				map.put("fd_age_21_25_f", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') >=21 and TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') <= 25 and fd_sex='M' "));
				map.put("fd_age_21_25_m", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') >=21 and TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') <= 25 and fd_sex='F' "));
				map.put("fd_age_26_35_f", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') >=26 and TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') <= 35 and fd_sex='M' "));
				map.put("fd_age_26_35_m", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') >=26 and TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') <= 35 and fd_sex='F' "));
				map.put("fd_age_36_45_f", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') >=36 and TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') <= 45 and fd_sex='M' "));
				map.put("fd_age_36_45_m", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') >=36 and TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') <= 45 and fd_sex='F' "));
				map.put("fd_age_46_50_f", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') >=46 and TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') <= 50 and fd_sex='M' "));
				map.put("fd_age_46_50_m", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') >=46 and TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') <= 50 and fd_sex='F' "));
				map.put("fd_age_50_100_f",
						getWhereBy1(dep_id, "TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') >=50 and fd_sex='F' "));
				map.put("fd_age_50_100_m",
						getWhereBy1(dep_id, "TIMESTAMPDIFF(YEAR,fd_date_of_birth, '" + benyue_31 + "') >=50 and fd_sex='M' "));

				// 司龄 1年以内
				map.put("fd_siling_0_1", getWhereBy1(dep_id, "TIMESTAMPDIFF(YEAR, fd_entry_time, '" + benyue_31 + "') < 1"));
				// 司龄 1-3年以下
				map.put("fd_siling_1_3", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_entry_time, '" + benyue_31 + "') >= 1 and TIMESTAMPDIFF(YEAR,fd_entry_time, '" + benyue_31 + "') < 3"));
				// 司龄 3-5年以下
				map.put("fd_siling_3_5", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_entry_time, '" + benyue_31 + "') >= 3 and TIMESTAMPDIFF(YEAR,fd_entry_time, '" + benyue_31 + "') < 5"));
				// 司龄 5-8年以下
				map.put("fd_siling_5_8", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_entry_time, '" + benyue_31 + "') >= 5 and TIMESTAMPDIFF(YEAR,fd_entry_time, '" + benyue_31 + "') < 8"));
				// 司龄 8-10年以下
				map.put("fd_siling_8_10", getWhereBy1(dep_id,
						"TIMESTAMPDIFF(YEAR,fd_entry_time, '" + benyue_31 + "') >= 8 and TIMESTAMPDIFF(YEAR,fd_entry_time, '" + benyue_31 + "') < 10"));
				// 司龄 10年以上
				map.put("fd_siling_10", getWhereBy1(dep_id, "TIMESTAMPDIFF(YEAR,fd_entry_time, '" + benyue_31 + "') >= 10"));

				// 职务类别（不含实习生临时工）M类 P类 O类 S类
				map.put("fd_rank_m", getRankBy(dep_id, "M"));
				map.put("fd_rank_p", getRankBy(dep_id, "P"));
				map.put("fd_rank_o", getRankBy(dep_id, "O"));
				map.put("fd_rank_s", getRankBy(dep_id, "S"));

				list.add(map);
			}

		}

		return list;

	}

	/**
	 * 
	 * @param depID
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public String getWhereBy(String depID, String sqlWhere) {
		StringBuffer sb = new StringBuffer();

		sb.append("select count(*) as result from hr_staff_person_info ");
		sb.append("where fd_status='official' and fd_hierarchy_id like '%" + depID + "%'");
		
		if (sqlWhere != null) {
			sb.append(" and (" + sqlWhere + ")");
		}
		log.info(sb.toString());
		String result = "";
		try {
			result = HrCurrencyParams.getStringBySql(sb.toString(), "result");
		} catch (Exception e) {
			log.error(e + sb.toString());
			e.printStackTrace();

		}
		return result;
	}

	public String getWhereBy1(String depID, String sqlWhere) {
		StringBuffer sb = new StringBuffer();

		sb.append("select count(*) as result from hr_staff_person_info ");
		sb.append("where fd_status in('official','trial','rehireAfterRetirement') and fd_hierarchy_id like '%" + depID + "%'");
		
		if (sqlWhere != null) {
			sb.append(" and (" + sqlWhere + ")");
		}
		log.info(sb.toString());
		String result = "";
		try {
			result = HrCurrencyParams.getStringBySql(sb.toString(), "result");
		} catch (Exception e) {
			log.error(e + sb.toString());
			e.printStackTrace();

		}
		return result;
	}
	public String getRankBy(String depID, String sqlRank) {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT count(*) as result from hr_staff_person_info h left join hr_org_rank o on h.fd_org_rank_id=o.fd_id");
		sb.append(" where h.fd_status in('official','trial','rehireAfterRetirement') and h.fd_hierarchy_id like '%" + depID + "%'");
		
		if (sqlRank != null) {
			sb.append(" and o.fd_name like '%" + sqlRank + "%'");
		}
		log.info(sb.toString());
		String result = "";
		try {
			result = HrCurrencyParams.getStringBySql(sb.toString(), "result");
		} catch (Exception e) {
			log.error(e + sb.toString());
			e.printStackTrace();

		}
		return result;
	}
}
