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
 * 人事月报-人员入离职率汇总
 * 
 * @author sunny
 * @version 创建时间：2022年9月30日下午5:22:15
 */
public class PersonLeaveMonthReport {
	private static final Log log = LogFactory.getLog(PersonLeaveMonthReport.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 人事月报-人员入离职率汇总
	 * 
	 * @return
	 */
	public List<Map<String, Object>> leaveMonthReport(String y,String m) {
		
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date()); // 设置时间为当前时间

		ca.set(Calendar.YEAR, Integer.valueOf(y));
		ca.set(Calendar.MONTH, Integer.valueOf(m)-1);
		ca.set(Calendar.DATE, 1);
		String ben_yue_1 = sdf.format(ca.getTime());

		// 本月最后一天
		ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
		String ben_yue_31 = sdf.format(ca.getTime());

		// 上月最后一天
		ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
		ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
		Date shang_yue_31_date = ca.getTime();
		String shang_yue_31 = sdf.format(ca.getTime());

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String[] type_array = { "O", "P", "S", "M" };

		StringBuffer sql_mubiao = new StringBuffer();
		sql_mubiao.append("select * from hr_turnover_annual where fd_year=" + y + " order by doc_create_time desc");

		Map<String, Object> map_mubiao = new HashMap<String, Object>();
		try {
			map_mubiao = HrCurrencyParams.getMapBySql(sql_mubiao.toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (String type : type_array) {
			Map<String, Object> map = new HashMap<String, Object>();

			String mubiao_year = "0.00";
			if (map_mubiao != null) {
				System.out.println(type.toLowerCase());
				mubiao_year = map_mubiao.get("fd_rate_" + type.toLowerCase()) + "";
			}

			// OPSM人员
			map.put("fd_type", type);
			// 本月最后一天在职人数
			String benyue_31_work = get_fd_person_work(type, ben_yue_31);
			map.put("benyue_31_work", benyue_31_work);
			// 5月31日在职人数
			String shangyue_31_work = get_fd_person_work(type, shang_yue_31);
			map.put("shangyue_31_work", shangyue_31_work);

			BigDecimal b_benyue_31_work = new BigDecimal(benyue_31_work);
			BigDecimal b_shangyue_31_work = new BigDecimal(shangyue_31_work);
			BigDecimal chayi = b_benyue_31_work.subtract(b_shangyue_31_work);
			// 差异 本月-上月人数
			map.put("chayi_size", chayi);
			
			// 离职率本月 离职率=离职人数/（离职人数+期末数/2）×100%
			String ben_yue_leave = get_fd_person_leave_Range(type, ben_yue_1, ben_yue_31);
			BigDecimal b_leave_benyue = new BigDecimal(ben_yue_leave);
			String ben_yue_lv = "0.00";
			if (!ben_yue_leave.equals("0") && !benyue_31_work.equals("0")) {
				//（离职人数+期末数/2）
				BigDecimal b_a = b_leave_benyue.add(b_benyue_31_work.divide(new BigDecimal("2")));
				ben_yue_lv = b_leave_benyue.divide(b_a,2,b_a.ROUND_HALF_UP).multiply(new BigDecimal("100")) + "";
			}
			map.put("benyue_leave_lv", ben_yue_lv);
			
			// 5月31日离职率
			map.put("shangyue_leave_lv", yueDu_leave_lv(type, shang_yue_31_date));
			
			BigDecimal zhi_jin_leave_lv = new BigDecimal(leiji_leave_lv(type, shang_yue_31_date));
			// 年至今离职率=年度累计离职人数/(年度在岗人数+年度离职人数/2)*100%
			map.put("leiji_leave_lv", zhi_jin_leave_lv.multiply(new BigDecimal("100")));
			
			// 公司目标值
			map.put("mubiao_year", mubiao_year);
			
			// 年度目标值/12*8 
			BigDecimal mubiao_year_lv = new BigDecimal("0.00") ;
			if(!mubiao_year.equals("0")){
				BigDecimal b_mubiao_year = new BigDecimal(mubiao_year);
				mubiao_year_lv = b_mubiao_year.divide(new BigDecimal("12"),2,new BigDecimal("12").ROUND_HALF_UP).multiply(new BigDecimal("8")).multiply(new BigDecimal("100"));
			}
			map.put("mubiao_lv", mubiao_year_lv);
			
			
			BigDecimal mubiao_leave_lv = new BigDecimal("0.00") ;
			if(zhi_jin_leave_lv.intValue() != 0){
				mubiao_leave_lv = mubiao_year_lv.divide(zhi_jin_leave_lv,2,zhi_jin_leave_lv.ROUND_HALF_UP).multiply(new BigDecimal("100"));
			}

			// 截止本月离职率目标/年至今累计离职率
			map.put("mubiao_leave_lv", mubiao_leave_lv);
			list.add(map);
		}

		return list;

	}
	
	/**
	 * // 年至今离职率=年度累计离职人数/(年度在岗人数+年度离职人数/2)*100%
	 * @param type
	 * @param shang_yue_31_date
	 * @return
	 */
	public String leiji_leave_lv(String type, Date shang_yue_31_date) {
		String ben_yue_31 = sdf.format(shang_yue_31_date);
		//年度累计离职人数所有离职人员
		String leiji_nian_leave = get_fd_person_leave(type, ben_yue_31);
		//年度离职人数
		String ben_nian_leave = get_fd_person_leave_Range(type, shang_yue_31_date.getYear() + "-01-01", ben_yue_31);
		//年度在岗人数
		String benyue_31_work = get_fd_person_work(type, ben_yue_31);
		//年至今离职率=年度累计离职人数/(年度在岗人数+年度离职人数/2)*100%
		String ben_yue_lv = "0.00";
		if (!ben_nian_leave.equals("0") && !benyue_31_work.equals("0")) {
			BigDecimal b_benyue_31_work = new BigDecimal(benyue_31_work);
			BigDecimal b_ben_nian_leave = new BigDecimal(ben_nian_leave);
			BigDecimal b_leiji_nian_leave = new BigDecimal(leiji_nian_leave);
			
			//(年度在岗人数+年度离职人数/2)
			BigDecimal b_a = b_benyue_31_work.add(b_ben_nian_leave.divide(new BigDecimal("2")));
			ben_yue_lv = b_leiji_nian_leave.divide(b_a,2,b_a.ROUND_HALF_UP) + "";
		}
		return ben_yue_lv;
	}
	
	public String yueDu_leave_lv(String type, Date shang_yue_31_date) {
		String ben_yue_31 = sdf.format(shang_yue_31_date);
		
		String ben_yue_1 = sdf.format(shang_yue_31_date).substring(0, 7) + "01";
		String ben_yue_leave = get_fd_person_leave_Range(type, ben_yue_1, ben_yue_31);
		String benyue_31_work = get_fd_person_work(type, ben_yue_31);
		// 离职率本月 离职率=离职人数/（离职人数+期末数/2）×100%
		String ben_yue_lv = "0.00";
		if (!ben_yue_leave.equals("0") && !benyue_31_work.equals("0")) {
			BigDecimal b_benyue_31_work = new BigDecimal(benyue_31_work);
			BigDecimal b_ben_yue_leave = new BigDecimal(ben_yue_leave);
			
			//（离职人数+期末数/2）
			BigDecimal b_a = b_ben_yue_leave.add(b_benyue_31_work.divide(new BigDecimal("2")));
			ben_yue_lv = b_ben_yue_leave.divide(b_a) + "";
		}
		return ben_yue_lv;
	}

	/**
	 * 例：5月31日之前在职员工 取离职时间大于5月31日或者离职时间空的员工
	 * 
	 * @param type
	 * @param end_date
	 * @return
	 */
	public String get_fd_person_work(String type, String end_date) {
		StringBuffer sql = new StringBuffer();
		sql.append(get_sql(type));
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
	public String get_fd_person_leave(String type, String yiqian_date) {
		StringBuffer sql = new StringBuffer();
		sql.append(get_sql(type));
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
	public String get_fd_person_leave_Range(String type, String start_date, String end_date) {
		StringBuffer sql = new StringBuffer();
		sql.append(get_sql(type));

		sql.append(" and (h.fd_leave_time >='" + start_date + "' and h.fd_leave_time <='" + end_date + "')");
		return get_sql_result(sql.toString());
	}

	public String get_fd_person_entry(String type, String start_date, String end_date) {
		// 入职员工
		StringBuffer sql = new StringBuffer();
		sql.append(get_sql(type));
		sql.append(" and h.fd_entry_time >'" + end_date + "'");
		return get_sql_result(sql.toString());
	}

	public String get_sql(String type) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as count from hr_staff_person_info h ");
		sql.append(" left join hr_org_rank r on h.fd_org_rank_id=r.fd_id where ");
		sql.append(" r.fd_name like  '%" + type + "%' ");

		return sql.toString();
	}

	public String get_sql_result(String sql) {
		String result = "0";
		try {
			result = HrCurrencyParams.getStringBySql(sql, "count");
		} catch (Exception e) {
			log.debug(sql);
			e.printStackTrace();
		}
		return result;
	}
}
