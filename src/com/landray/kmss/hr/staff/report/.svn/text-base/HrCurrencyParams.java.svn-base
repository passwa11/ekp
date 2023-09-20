package com.landray.kmss.hr.staff.report;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.component.dbop.ds.DSTemplate;
import com.landray.kmss.component.dbop.ds.DataSet;
import com.landray.kmss.component.dbop.ds.IDsAction;

/**
 * 
 * @author sunny
 * @version 创建时间：2022年9月27日下午2:37:42
 */
public class HrCurrencyParams {

	// 彭舒燕ID
	private final static String staff_quartz_creator = "HG31395";

	/**
	 * 获取定时器发起人员 fd_id fd_create_id fd_create_time fd_leavehandel
	 * fd_expcontractexpiration fd_becomeregularworker
	 * 
	 * @return
	 */
	public static String getStaffQuartzCreator(String type) {
		String creat_id = "{\"PersonNo\": \"" + staff_quartz_creator + "\"}";
		String sql = "select * from ekp_staff_quartz_creator order by fd_create_time desc";
		try {
			List<Map<String, Object>> list = getListBySql(sql);
			if (list.size() > 0) {
				
				if (type.equals("HrBecomeRegularWorkerRemind")) {// 拟转正提醒
					creat_id = "{\"Id\": \"" + list.get(0).get("fd_becomeregularworker") + "\"}";
				} else if (type.equals("HrExpContractExpirationRemind")) {// 合同到期提醒
					creat_id = "{\"Id\": \"" + list.get(0).get("fd_expcontractexpiration") + "\"}";
				} else if (type.equals("HrLeaveHandelRemind")) {// 离职办理提醒
					creat_id = "{\"Id\": \"" + list.get(0).get("fd_leavehandel") + "\"}";
				} else if (type.equals("HrPersonalIncomeTaxRemind")) {// 个税提醒
					creat_id = "{\"Id\": \"" + list.get(0).get("fd_personalincometax") + "\"}";
				} else {
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return creat_id;
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getListBySql(final String sql) throws Exception {
		return (List<Map<String, Object>>) DSTemplate.execute(null, new IDsAction() {
			public Object doAction(DataSet ds) throws Exception {

				List<Map<String, Object>> list = new ArrayList<>();

				ResultSet rs = ds.getConnection().createStatement().executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();

				while (rs.next()) {
					Map<String, Object> valueMap = new LinkedHashMap<>();
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i).toLowerCase();
						Object value = JdbcUtils.getResultSetValue(rs, i);
						if (value == null) {
							value = "";
						}
						valueMap.put(key, value);
					}
					list.add(valueMap);
				}
				return list;
			}
		});

	}

	public static String getStringBySql(final String sql, final String field) throws Exception {
		return (String) DSTemplate.execute(null, new IDsAction() {
			public Object doAction(DataSet ds) throws Exception {

				List<Map<String, Object>> list = new ArrayList<>();

				ResultSet rs = ds.getConnection().createStatement().executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();

				while (rs.next()) {
					Map<String, Object> valueMap = new LinkedHashMap<>();
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i).toLowerCase();
						Object value = JdbcUtils.getResultSetValue(rs, i);
						if (value == null) {
							value = "";
						}
						valueMap.put(key, value);
					}
					list.add(valueMap);
				}

				if (list.size() > 0) {
					return list.get(0).get(field) + "";
				} else {
					return "";
				}
			}
		});
	}
	public static List getValueBySql(final String sql, final String field) throws Exception {
		return (List) DSTemplate.execute(null, new IDsAction() {
			public Object doAction(DataSet ds) throws Exception {

				List<Map<String, Object>> list = new ArrayList<>();

				ResultSet rs = ds.getConnection().createStatement().executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();

				List list1 = new ArrayList<String>();
				while (rs.next()) {
					Map<String, Object> valueMap = new LinkedHashMap<>();
						Object value = JdbcUtils.getResultSetValue(rs, 1);
						if (value == null) {
							value = "";
						}
					list1.add(value);
				}
				if (list1.size() > 0) {
					return list1;
				} else {
					return null;
				}
			}
		});
	}
	public static int getCountBySql(final String sql) throws Exception {
		return (int) DSTemplate.execute(null, new IDsAction() {
			public Object doAction(DataSet ds) throws Exception {

				List<Map<String, Object>> list = new ArrayList<>();

				ResultSet rs = ds.getConnection().createStatement().executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();

				List list1 = new ArrayList<String>();
				rs.next();
				Long value =  (Long)JdbcUtils.getResultSetValue(rs, 1);
				
				return value.intValue();
			}
		});
	}
	public static int getWhereBy(String sqlWhere) {
		StringBuffer sb = new StringBuffer();

		sb.append("select count(*) as result from hr_staff_person_info ");
//		sb.append("where fd_hierarchy_id like '%" + depID + "%'");
		
		if (sqlWhere != null) {
			sb.append(" where (" + sqlWhere + ")");
		}
		int result = 0;
		try {
			result = Integer.parseInt(HrCurrencyParams.getStringBySql(sb.toString(), "result"));
		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapBySql(final String sql) throws Exception {
		return (Map<String, Object>) DSTemplate.execute(null, new IDsAction() {
			public Object doAction(DataSet ds) throws Exception {

				List<Map<String, Object>> list = new ArrayList<>();

				ResultSet rs = ds.getConnection().createStatement().executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();

				while (rs.next()) {
					Map<String, Object> valueMap = new LinkedHashMap<>();
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i).toLowerCase();
						Object value = JdbcUtils.getResultSetValue(rs, i);
						if (value == null) {
							value = "";
						}
						valueMap.put(key, value);
					}
					list.add(valueMap);
				}
				if (list.size() > 0) {
					return list.get(0);
				} else {
					return null;
				}

			}
		});
	}

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		// ca.setTime(new Date()); // 设置时间为当前时间
		//
		// String y = "201";
		// String m = "01";
		//
		// // 每月28日自动提醒上月26日至当月25日入职的员工
		// int yy = 0;
		// int mm = 0;
		//
		// try {
		// yy = Integer.valueOf(y);
		// mm = Integer.valueOf(m);
		// } catch (NumberFormatException e) {
		// yy = ca.get(Calendar.YEAR);
		// mm = ca.get(Calendar.MONTH);
		// }
		// ca.set(Calendar.YEAR, yy);
		// ca.set(Calendar.MONTH, mm - 1);
		// ca.set(Calendar.DAY_OF_MONTH, 25);
		// System.out.println("本月25日" + sdf.format(ca.getTime()));
		// ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
		// ca.set(Calendar.DAY_OF_MONTH, 26);
		// System.out.println("上月26日" + sdf.format(ca.getTime()));
		//
		//
		// if (y == "" || m == "") {
		// // 每月28日自动提醒上月26日至当月25日入职的员工
		// ca.set(Calendar.DAY_OF_MONTH, 25);
		// System.out.println("本月25日" + sdf.format(ca.getTime()));
		// ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
		// ca.set(Calendar.DAY_OF_MONTH, 26);
		// System.out.println("上月26日" + sdf.format(ca.getTime()));
		// } else {
		//
		// }
		String mo = "2022-01-02 00:00:000";
		try {
			sdf.format(sdf.parse(mo));

			System.out.println(sdf.format(sdf.parse(mo)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 获取凌晨
	 * 
	 * @param nowTime
	 */
	public static Long start_time(Date startTime) {
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(startTime);
		ca.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DATE), 0, 0, 0);
		long set_close = ca.getTimeInMillis();
		return set_close;
	}
	
	/**
	 * 获取凌晨
	 * 
	 * @param nowTime
	 */
	public static Long end_time(Date startTime) {
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(startTime);
		ca.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DATE), 23, 59, 59);
		long set_close = ca.getTimeInMillis();
		return set_close;
	}

	/**
	 * 判断当前时间是否在[startTime, endTime]区间，注意三个参数的时间格式要一致
	 * 
	 * @param nowTime
	 * @param startTime
	 * @param endTime
	 * @return 在时间段内返回true，不在返回false
	 */
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);
		begin.set(begin.get(Calendar.YEAR), begin.get(Calendar.MONTH), begin.get(Calendar.DATE), 0, 0, 0);
		
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		end.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH), end.get(Calendar.DATE), 23, 59, 59);
		
		return date.after(begin) && date.before(end);
	}
}
