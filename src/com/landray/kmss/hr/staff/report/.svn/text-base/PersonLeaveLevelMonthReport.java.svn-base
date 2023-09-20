package com.landray.kmss.hr.staff.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.sys.ftsearch.ibm.icu.math.BigDecimal;

/**
 * 
 * @author sunny
 * @version 创建时间：2022年9月30日下午5:23:46
 */
public class PersonLeaveLevelMonthReport {
	private static final Log log = LogFactory.getLog(PersonLeaveLevelMonthReport.class);

	/**
	 * 人事月报-按职位类别统计离职率
	 * 
	 * @return
	 */
	public List<Map<String, Object>> leaveLevelMonthReport(String y, String m) {
		if(y.equals("") || m.equals("")){
			return new ArrayList<Map<String, Object>>();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date()); // 设置时间为当前时间
		ca.set(Calendar.YEAR, Integer.valueOf(y));
		ca.set(Calendar.MONTH, Integer.valueOf(m) - 1);

		// 本年1月1日
		String ben_nian_1yue_1ri = ca.get(Calendar.YEAR) + "-01-01";

		// 本月第一天
		ca.set(Calendar.DATE, 1);
		String ben_yue_1 = sdf.format(ca.getTime());

		// 本月最后一天
		ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
		String ben_yue_31 = sdf.format(ca.getTime());

		// 上月最后一天
		ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
		ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
		String shang_yue_31 = sdf.format(ca.getTime());

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
			String[] type_array = { "O", "P", "S", "M" };
			for (Map<String, Object> dep_map : dept_list) {
				String dep_id = dep_map.get("fd_id") + "";
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fd_dept_name", dep_map.get("fd_name") + "");
				for (String type : type_array) {

					// 本月（例6月1日至6月30日）O类离职人员总数
					String fd_benyue_leave = get_fd_person_leave_Range(dep_id, type, ben_yue_1, ben_yue_31);
					String typeo = type.toLowerCase();

					// 操作人员月初总数
					// 系统人事资料5月31日在职O类人员的人数
					map.put("fd_shangyue_work_" + typeo, get_fd_person_work(dep_id, type, shang_yue_31));
					// 操作人员月末总数
					// 系统人事资料6月30日在职O类人员的人数
					map.put("fd_benyue_work_" + typeo, get_fd_person_work(dep_id, type, ben_yue_31));
					// 操作人员流失人数
					// 系统人事资料6月1日 - 6月30日离职O类人员的人数
					map.put("fd_benyue_leave_" + typeo, fd_benyue_leave);

					// 操作人员流失率%
					// 员工流失人数 = 6月1日 - 6月30日离职O类人员的人数
					// 期初员工人数 = 6月1日在职人数
					String qichu_yue = get_fd_person_work(dep_id, type, ben_yue_1);
					// 本期增加人数 = 6月1日-6月30日入职人数
					String benyue_entry = get_fd_person_entry(dep_id, type, ben_yue_1, ben_yue_31);
					String fd_benyue_liu_lv = "0.00";
					BigDecimal b_qichu_yue = new BigDecimal(qichu_yue);
					BigDecimal b_benyue_entry = new BigDecimal(benyue_entry);
					BigDecimal b_1 = (b_qichu_yue.add(b_benyue_entry));
					if (b_1.floatValue() > 0) {
						BigDecimal b_fd_benyue_leave = new BigDecimal(fd_benyue_leave);
						BigDecimal b_fd_benyue_liu_lv_ = b_fd_benyue_leave.divide(b_1,2,b_1.ROUND_HALF_UP).multiply(new BigDecimal(100));
						fd_benyue_liu_lv = b_fd_benyue_liu_lv_.toString();
					}

					// O类员工流失率=员工O类流失人数/（期初O类员工人数+本期增加O类员工人数）*100%
					map.put("fd_benyue_liu_lv_" + typeo, fd_benyue_liu_lv);

					// 系统人事资料1月1-6月30日O类累计离职人数
					String leave_nian_1yue_31 = get_fd_person_leave_Range(dep_id, type, ben_nian_1yue_1ri, ben_yue_31);

					// 操作人员累计离职人数
					// 系统人事资料1月1-6月30日O类累计离职人数
					map.put("fd_all_year_leiji_" + typeo, leave_nian_1yue_31);

					// 操作人员累计流失率%
					// 期初员工人数
					String qichu_nian = get_fd_person_work(dep_id, type, ben_nian_1yue_1ri);
					String ben_nian_entry = get_fd_person_entry(dep_id, type, ben_nian_1yue_1ri, ben_yue_31);
					String fd_bennian_liu_lv = "0.00";
					BigDecimal b_fd_ben_nian_leave = new BigDecimal(leave_nian_1yue_31);
					BigDecimal b_qichu_nian = new BigDecimal(qichu_nian);
					BigDecimal b_ben_nian_entry = new BigDecimal(ben_nian_entry);
					BigDecimal b_2 = b_qichu_nian.add(b_ben_nian_entry);
					if (b_2.floatValue() > 0) {
						BigDecimal b_fd_ben_nian_liu_lv_ = b_fd_ben_nian_leave.divide(b_2,2,b_2.ROUND_HALF_UP).multiply(new BigDecimal(100));
						fd_bennian_liu_lv = b_fd_ben_nian_liu_lv_.toString();

					}
					// 员工流失率=员工流失人数/（期初员工人数+本期增加员工人数）*100%
					map.put("fd_all_year_leiji_lv_" + typeo, fd_bennian_liu_lv);

				}

				list.add(map);
			}
		}

		return list;

	}

	/**
	 * 例：5月31日之前在职员工 取离职时间大于5月31日或者离职时间空的员工
	 * 
	 * @param type
	 * @param end_date
	 * @return
	 */
	public String get_fd_person_work(String dept_id, String type, String end_date) {
		StringBuffer sql = new StringBuffer();
		sql.append(get_sql(dept_id, type));
		sql.append(" and (h.fd_leave_time >'" + end_date + "' or h.fd_leave_time is null)");
		return get_sql_result(sql.toString());
	}

	/**
	 * 例：5月31日之前在职员工 取离职时间大于5月31日或者离职时间空的员工
	 * 
	 * @param type
	 * @param end_date
	 * @return
	 */
	public String get_fd_person_leave(String dept_id, String type, String yiqian_date) {
		StringBuffer sql = new StringBuffer();
		sql.append(get_sql(dept_id, type));
		sql.append(" and h.fd_leave_time <='" + yiqian_date + "'");

		return get_sql_result(sql.toString());
	}

	/**
	 * 某段时间内离职人员 比如 6月1日到6月30日离职员工
	 * 
	 * @param type
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public String get_fd_person_leave_Range(String dept_id, String type, String start_date, String end_date) {
		StringBuffer sql = new StringBuffer();
		sql.append(get_sql(dept_id, type));

		sql.append(" and (h.fd_leave_time >='" + start_date + "' and h.fd_leave_time <='" + end_date + "')");
		return get_sql_result(sql.toString());
	}

	public String get_fd_person_entry(String dept_id, String type, String start_date, String end_date) {
		// 入职员工
		StringBuffer sql = new StringBuffer();
		sql.append(get_sql(dept_id, type));
		sql.append(" and h.fd_entry_time >'" + end_date + "'");
		return get_sql_result(sql.toString());
	}

	public String get_sql(String dept_id, String type) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as count from hr_staff_person_info h ");
		sql.append(" left join hr_org_rank r on h.fd_org_rank_id=r.fd_id where ");
		sql.append(" r.fd_name like  '%" + type + "%' and h.fd_hierarchy_id like '%" + dept_id + "%'");

		return sql.toString();
	}

	public String get_sql_result(String sql) {
		String result = "";
		try {
			result = HrCurrencyParams.getStringBySql(sql, "count");
		} catch (Exception e) {
			log.debug(sql);
			e.printStackTrace();
		}
		return result;
	}
}
