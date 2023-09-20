package com.landray.kmss.sys.attend.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.dbop.ds.DataSet;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.staff.report.HrCurrencyParams;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ResourceUtil;

/**
 * 人事月报-人员结构分布表（职务类别不含实习生临时工，其他均含）
 * 
 * @author sunny
 * @version 创建时间：2022年9月30日下午5:30:03
 */
public class AttendMonthDeptOver40Report {
	private static final Log log = LogFactory.getLog(AttendMonthDeptOver40Report.class);

	/**
	 * 人事月报-人员结构分布表（职务类别不含实习生临时工，其他均含）
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> monthReport(String y,String m) throws SQLException {
		if(y.equals("") || m.equals("")){
			return new ArrayList<Map<String, Object>>();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date()); // 设置时间为当前时间

		Calendar ca3 = Calendar.getInstance();// 得到一个Calendar的实例
		ca3.setTime(new Date()); // 设置时间为当前时间
		Calendar ca4 = Calendar.getInstance();// 得到一个Calendar的实例
		ca4.setTime(new Date()); // 设置时间为当前时间
		ca.set(Calendar.YEAR, Integer.valueOf(y));
		ca.set(Calendar.MONTH, Integer.valueOf(m)-1);
		ca3.set(Calendar.YEAR, Integer.valueOf(y));
		ca3.set(Calendar.MONTH, Integer.valueOf(m)-2);

		ca4.set(Calendar.YEAR, Integer.valueOf(y));
		ca4.set(Calendar.MONTH, Integer.valueOf(m)-2);
		// 本月最后一天
		Calendar ca1 = Calendar.getInstance();// 得到一个Calendar的实例
		ca1.setTime(new Date()); // 设置时间为当前时间

		ca1.set(Calendar.YEAR, Integer.valueOf(y));
		ca1.set(Calendar.MONTH, Integer.valueOf(m)-1);
		// 本月最后一天
		ca.set(Calendar.DATE, ca.getLeastMaximum(Calendar.DATE));
		ca3.set(Calendar.DATE, ca3.getLeastMaximum(Calendar.DATE));
		ca1.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		ca4.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String benyue_31 = sdf.format(ca.getTime());
		String benyue_1 = sdf.format(ca1.getTime());
		String shangyue_31 = sdf.format(ca3.getTime());
		String shangeyue_1 = sdf.format(ca4.getTime());
		Calendar ca2 = Calendar.getInstance();// 得到一个Calendar的实例
		ca2.set(Calendar.YEAR, Integer.valueOf(y));
		ca2.set(Calendar.MONTH, Integer.valueOf(1)-1);
		ca2.set(Calendar.DATE, ca2.getActualMinimum(Calendar.DATE));
		String yiyue_1 = sdf.format(ca2.getTime());
		Calendar cb2 = Calendar.getInstance();// 得到一个Calendar的实例
		cb2.set(Calendar.YEAR, Integer.valueOf(y));
		cb2.set(Calendar.MONTH, Integer.valueOf(1)-1);
		cb2.set(Calendar.DATE, ca.getLeastMaximum(Calendar.DATE));
		String yiyue_31 = sdf.format(cb2.getTime());
		Calendar ca21 = Calendar.getInstance();// 得到一个Calendar的实例
		ca21.set(Calendar.YEAR, Integer.valueOf(y));
		ca21.set(Calendar.MONTH, Integer.valueOf(2)-1);
		ca21.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String eryue_1 = sdf.format(ca21.getTime());
		Calendar cb21 = Calendar.getInstance();// 得到一个Calendar的实例
		cb21.set(Calendar.YEAR, Integer.valueOf(y));
		cb21.set(Calendar.MONTH, Integer.valueOf(2)-1);
		cb21.set(Calendar.DATE, ca.getLeastMaximum(Calendar.DATE));
		String eryue_31 = sdf.format(cb21.getTime());
		Calendar ca22 = Calendar.getInstance();// 得到一个Calendar的实例
		ca22.set(Calendar.YEAR, Integer.valueOf(y));
		ca22.set(Calendar.MONTH, Integer.valueOf(3)-1);
		ca22.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String sanyue_1 = sdf.format(ca22.getTime());
		Calendar cb22 = Calendar.getInstance();// 得到一个Calendar的实例
		cb22.set(Calendar.YEAR, Integer.valueOf(y));
		cb22.set(Calendar.MONTH, Integer.valueOf(3)-1);
		cb22.set(Calendar.DATE, ca.getLeastMaximum(Calendar.DATE));
		String sanyue_31 = sdf.format(cb22.getTime());
		Calendar ca212 = Calendar.getInstance();// 得到一个Calendar的实例
		ca212.set(Calendar.YEAR, Integer.valueOf(y));
		ca212.set(Calendar.MONTH, Integer.valueOf(4)-1);
		ca212.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String siyue_1 = sdf.format(ca212.getTime());
		Calendar cb215 = Calendar.getInstance();// 得到一个Calendar的实例
		cb215.set(Calendar.YEAR, Integer.valueOf(y));
		cb215.set(Calendar.MONTH, Integer.valueOf(2)-1);
		cb215.set(Calendar.DATE, ca.getLeastMaximum(Calendar.DATE));
		String siyue_31 = sdf.format(cb215.getTime());
		Calendar ca233 = Calendar.getInstance();// 得到一个Calendar的实例
		ca233.set(Calendar.YEAR, Integer.valueOf(y));
		ca233.set(Calendar.MONTH, Integer.valueOf(5)-1);
		ca233.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String wuyue_1 = sdf.format(ca233.getTime());
		Calendar cb245 = Calendar.getInstance();// 得到一个Calendar的实例
		cb245.set(Calendar.YEAR, Integer.valueOf(y));
		cb245.set(Calendar.MONTH, Integer.valueOf(5)-1);
		cb245.set(Calendar.DATE, ca.getLeastMaximum(Calendar.DATE));
		String wuyue_31 = sdf.format(cb245.getTime());
		Calendar ca216 = Calendar.getInstance();// 得到一个Calendar的实例
		ca216.set(Calendar.YEAR, Integer.valueOf(y));
		ca216.set(Calendar.MONTH, Integer.valueOf(6)-1);
		ca216.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String liuyue_1 = sdf.format(ca216.getTime());
		Calendar cb217 = Calendar.getInstance();// 得到一个Calendar的实例
		cb217.set(Calendar.YEAR, Integer.valueOf(y));
		cb217.set(Calendar.MONTH, Integer.valueOf(6)-1);
		cb217.set(Calendar.DATE, ca.getLeastMaximum(Calendar.DATE));
		String liuyue_31 = sdf.format(cb217.getTime());
		Calendar ca228 = Calendar.getInstance();// 得到一个Calendar的实例
		ca228.set(Calendar.YEAR, Integer.valueOf(y));
		ca228.set(Calendar.MONTH, Integer.valueOf(7)-1);
		ca228.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String qiyue_1 = sdf.format(ca228.getTime());
		Calendar cb229 = Calendar.getInstance();// 得到一个Calendar的实例
		cb229.set(Calendar.YEAR, Integer.valueOf(y));
		cb229.set(Calendar.MONTH, Integer.valueOf(7)-1);
		cb229.set(Calendar.DATE, ca.getLeastMaximum(Calendar.DATE));
		String qiyue_31 = sdf.format(cb229.getTime());
		Calendar ca212a = Calendar.getInstance();// 得到一个Calendar的实例
		ca212a.set(Calendar.YEAR, Integer.valueOf(y));
		ca212a.set(Calendar.MONTH, Integer.valueOf(8)-1);
		ca212a.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String bayue_1 = sdf.format(ca212a.getTime());
		Calendar cb215b = Calendar.getInstance();// 得到一个Calendar的实例
		cb215b.set(Calendar.YEAR, Integer.valueOf(y));
		cb215b.set(Calendar.MONTH, Integer.valueOf(8)-1);
		cb215b.set(Calendar.DATE, ca.getLeastMaximum(Calendar.DATE));
		String bayue_31 = sdf.format(cb215b.getTime());
		Calendar ca2r = Calendar.getInstance();// 得到一个Calendar的实例
		ca2r.set(Calendar.YEAR, Integer.valueOf(y));
		ca2r.set(Calendar.MONTH, Integer.valueOf(9)-1);
		ca2r.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String jiuyue_1 = sdf.format(ca2r.getTime());
		Calendar cb2h = Calendar.getInstance();// 得到一个Calendar的实例
		cb2h.set(Calendar.YEAR, Integer.valueOf(y));
		cb2h.set(Calendar.MONTH, Integer.valueOf(9)-1);
		cb2h.set(Calendar.DATE, ca.getLeastMaximum(Calendar.DATE));
		String jiuyue_31 = sdf.format(cb2h.getTime());
		Calendar ca21n = Calendar.getInstance();// 得到一个Calendar的实例
		ca21n.set(Calendar.YEAR, Integer.valueOf(y));
		ca21n.set(Calendar.MONTH, Integer.valueOf(10)-1);
		ca21n.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String shiyue_1 = sdf.format(ca21n.getTime());
		Calendar cb21g = Calendar.getInstance();// 得到一个Calendar的实例
		cb21g.set(Calendar.YEAR, Integer.valueOf(y));
		cb21g.set(Calendar.MONTH, Integer.valueOf(10)-1);
		cb21g.set(Calendar.DATE, ca.getLeastMaximum(Calendar.DATE));
		String shiyue_31 = sdf.format(cb21g.getTime());
		Calendar ca22y = Calendar.getInstance();// 得到一个Calendar的实例
		ca22y.set(Calendar.YEAR, Integer.valueOf(y));
		ca22y.set(Calendar.MONTH, Integer.valueOf(11)-1);
		ca22y.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String shiyiyue_1 = sdf.format(ca22y.getTime());
		Calendar cb22b = Calendar.getInstance();// 得到一个Calendar的实例
		cb22b.set(Calendar.YEAR, Integer.valueOf(y));
		cb22b.set(Calendar.MONTH, Integer.valueOf(11)-1);
		cb22b.set(Calendar.DATE, ca.getLeastMaximum(Calendar.DATE));
		String shiyiyue_31 = sdf.format(cb22b.getTime());
		Calendar ca212t = Calendar.getInstance();// 得到一个Calendar的实例
		ca212t.set(Calendar.YEAR, Integer.valueOf(y));
		ca212t.set(Calendar.MONTH, Integer.valueOf(12)-1);
		ca212t.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String shieryue_1 = sdf.format(ca212t.getTime());
		Calendar cb215z = Calendar.getInstance();// 得到一个Calendar的实例
		cb215z.set(Calendar.YEAR, Integer.valueOf(y));
		cb215z.set(Calendar.MONTH, Integer.valueOf(12)-1);
		cb215z.set(Calendar.DATE, ca.getLeastMaximum(Calendar.DATE));
		String shieryue_31 = sdf.format(cb215z.getTime());
		DecimalFormat df = new DecimalFormat("0.0");
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
			Map<String, Object> map1 = new HashMap<String, Object>();
			int time2=0;
			int time3=0;
			for (Map<String, Object> dep_map : dept_list) {
				String dep_id = dep_map.get("fd_id") + "";
				Map<String, Object> map = new HashMap<String, Object>();
				//部门名称
				map.put("fd_dept_name", dep_map.get("fd_name") + "");
				String month1 = null;
				String month31 = null;
				int over40 = 0;
				int holiday = 0;
				int off = 0;
				int work = 0;
				for(int mn=1;mn<=Integer.parseInt(m);mn++){
					if(mn==1){
						month1=yiyue_1;
						month31= yiyue_31;
					}
					if(mn==2){
						month1=eryue_1;
						month31= eryue_31;
					}
					if(mn==3){
						month1=sanyue_1;
						month31= sanyue_31;
					}
					if(mn==4){
						month1=siyue_1;
						month31= siyue_31;
					}
					if(mn==5){
						month1=wuyue_1;
						month31= wuyue_31;
					}
					if(mn==6){
						month1=liuyue_1;
						month31= liuyue_31;
					}
					if(mn==7){
						month1=qiyue_1;
						month31= qiyue_31;
					}
					if(mn==8){
						month1=bayue_1;
						month31= bayue_31;
					}
					if(mn==9){
						month1=jiuyue_1;
						month31= jiuyue_31;
					}
					if(mn==10){
						month1=shiyue_1;
						month31= shiyue_31;
					}
					if(mn==11){
						month1=shiyiyue_1;
						month31= shiyiyue_31;
					}
					if(mn==12){
						month1=shieryue_1;
						month31= shieryue_31;
					}
				}	String sql562 = "select distinct fd_proposer from  ekp_h13_workovertime where fd_start_time>='"+month1+"' and fd_start_time<='"+month31+"' and fd_proposer in (select fd_id from sys_org_element where fd_hierarchy_id like '%"+dep_id+"%' and fd_org_type=8) and fd_overtime_welfare='2'";
				String sql561 = "select * from  ekp_h13_workovertime2_detail where fd_jiaBanRiQi>='"+month1+"' and fd_jiaBanRiQi<='"+month31+"' and fd_jiaBanRenXingMing in (select fd_id from sys_org_element where fd_hierarchy_id like '%"+dep_id+"%' and fd_org_type=8) and fd_jiaBanRenXingMing not in (select fd_proposer from  ekp_h13_workovertime where fd_start_time>='"+month1+"' and fd_start_time<='"+month31+"' and fd_overtime_welfare='2')";
				
				String sql122 = "select * from sys_attend_stat_month where fd_month>='"+month1+"' and fd_month<='"+month31+"' and doc_creator_id in (select fd_id from sys_org_element where fd_hierarchy_id like '%"+dep_id+"%'  and fd_org_type=8)";
				String sql56 =  "select * from sys_org_element where fd_hierarchy_id like '%"+dep_id+"%' and fd_org_type=8";
//				String sql1222 = "select * from (select * from sys_attend_stat_month where fd_month>='"+eryue_1+"' and fd_month<='"+eryue_31+"') a left join (select * from sys_org_element where fd_hierarchy_id like '%"+dep_id+"%')b on a.doc_creator_id=b.fd_id ";
				List<Map<String, Object>> person_list = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> person_list1 = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> person_list2 = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> person_list3 = new ArrayList<Map<String, Object>>();
				try {
					person_list = HrCurrencyParams.getListBySql(sql122);
					person_list2 = HrCurrencyParams.getListBySql(sql562);
					person_list3 = HrCurrencyParams.getListBySql(sql561);
			} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					person_list1 = HrCurrencyParams.getListBySql(sql56);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int time=0;
				int time6=0;

				int time8=0;
				int time9=0;
				int time10=0;
				int time20=0;
				for (Map<String, Object> person_map : person_list) {
//					if(Integer.parseInt(person_map.get("fd_over_rest_time").toString())>(40*60)){
//						over40++;
//						time+=Integer.parseInt(person_map.get("fd_over_rest_time").toString());
//					}

					if(!"".equals(person_map.get("fd_over_rest_time").toString())){
						time20+=(Integer.parseInt(person_map.get("fd_over_rest_time").toString()));
					if(Integer.parseInt(person_map.get("fd_over_rest_time").toString())>(40*60)){
						over40++;
						time6+=Integer.parseInt(person_map.get("fd_holiday_over_time").toString());
					}}
					if(Integer.parseInt(person_map.get("fd_holiday_over_time").toString())>(40*60)){
						holiday++;
						time6+=Integer.parseInt(person_map.get("fd_holiday_over_time").toString());
					}
					if(!"".equals(person_map.get("fd_off_over_time").toString()))
					if(Integer.parseInt(person_map.get("fd_off_over_time").toString())>(40*60)){
						off++;
						time8+=Integer.parseInt(person_map.get("fd_off_over_time").toString());
					}
					if(!"".equals(person_map.get("fd_work_over_time").toString()))
					if(Integer.parseInt(person_map.get("fd_work_over_time").toString())>(40*60)){
						work++;
					time9+=Integer.parseInt(person_map.get("fd_work_over_time").toString());
					}
					int a=0,b=0,c=0;
					if(!"".equals(person_map.get("fd_holiday_over_time").toString()))
						a=Integer.parseInt(person_map.get("fd_holiday_over_time").toString());
					if(!"".equals(person_map.get("fd_off_over_time").toString()))
					b=Integer.parseInt(person_map.get("fd_off_over_time").toString());
					if(!"".equals(person_map.get("fd_work_over_time").toString()))
					c=Integer.parseInt(person_map.get("fd_work_over_time").toString());
//					time20=a+b+c;
				}
				time10=time6+time8+time9;
				int time1;
				if(person_list.size()!=0)
				time1=time/person_list.size();
				else time1=0;
//				for (Map<String, Object> person_map : person_list1) {
//					time1+=Integer.parseInt(person_map.get("fd_over_rest_time").toString());
//				}
				time2+=time;
//				time3+=time1;
				int hour = time/60;
				int mins = time%60;
				String hourTxt = "";
				String hTxt = ResourceUtil.getString("date.interval.hour");
				String mTxt = ResourceUtil.getString("date.interval.minute");
				if(hour>0){
					hourTxt+=hour+hTxt;
				}
				if(mins>0){
					hourTxt+=mins+mTxt;
				}
				int hour6 = time6/60;
				int mins6 = time6%60;
				String hourTxt6 = "";
				String hTxt6 = ResourceUtil.getString("date.interval.hour");
				String mTxt6 = ResourceUtil.getString("date.interval.minute");
				if(hour6>0){
					hourTxt6+=hour6+hTxt6;
				}
				if(mins6>0){
					hourTxt6+=mins6+mTxt6;
				}
				int hour8 = time8/60;
				int mins8 = time8%60;
				String hourTxt8 = "";
				String hTxt8 = ResourceUtil.getString("date.interval.hour");
				String mTxt8 = ResourceUtil.getString("date.interval.minute");
				if(hour8>0){
					hourTxt8+=hour8+hTxt8;
				}
				if(mins8>0){
					hourTxt8+=mins8+mTxt8;
				}
				int hour9 = time9/60;
				int mins9 = time9%60;
				String hourTxt9 = "";
				String hTxt9 = ResourceUtil.getString("date.interval.hour");
				String mTxt9 = ResourceUtil.getString("date.interval.minute");
				if(hour9>0){
					hourTxt9+=hour9+hTxt9;
				}
				if(mins9>0){
					hourTxt9+=mins9+mTxt9;
				}
				int hour10 = time10/60;
				int mins10 = time10%60;
				String hourTxt10 = "";
				String hTxt10 = ResourceUtil.getString("date.interval.hour");
				String mTxt10 = ResourceUtil.getString("date.interval.minute");
				if(hour10>0){
					hourTxt10+=hour10+hTxt10;
				}
				if(mins10>0){
					hourTxt10+=mins10+mTxt10;
				}
				int hour20 = time20/60;
				int mins20 = time20%60;
				String hourTxt20 = "";
				String hTxt20 = ResourceUtil.getString("date.interval.hour");
				String mTxt20 = ResourceUtil.getString("date.interval.minute");
				if(hour20>0){
					hourTxt20+=hour20+hTxt20;
				}
				if(mins20>0){
					hourTxt20+=mins20+mTxt20;
				}
				int hour1 = time1/60;
				int mins1 = time1%60;
				String hourTxt1 = "";
				String hTxt1 = ResourceUtil.getString("date.interval.hour");
				String mTxt1 = ResourceUtil.getString("date.interval.minute");
				if(hour1>0){
					hourTxt1+=hour1+hTxt1;
				}
				if(mins1>0){
					hourTxt1+=mins1+mTxt1;
				}
				map.put("personCount", person_list1.size());
				map.put("jiabanCount", person_list2.size()+ person_list3.size());
				map.put("over40", over40);
				if((person_list2.size()+ person_list3.size())==0)
					map.put("jiabanlv", 0.0);
				else
				map.put("jiabanlv", over40*1.0/(person_list2.size()+ person_list3.size()));
				map.put("holiday", hourTxt6);
				map.put("off", hourTxt8);
				map.put("work", hourTxt9);
				map.put("countover40", hourTxt10);
				map.put("monthcount", hourTxt20);
				//人数小计
//				map.put("fd_begin_month_count", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%正编%'"));
//
//				map.put("fd_end_month_count", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%正编%'"));
//				map.put("fd_new_month_count", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%正编%'"));
//				map.put("fd_leave_month_count", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_staff_type like '%正编%'"));
//				map.put("fd_leave_count", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_staff_type like '%正编%'"));
//				String sql1 = "select fd_id from sys_org_element where fd_hierarchy_id like '%"+dep_id+"%'";
//				String deptIds = "";
//				List<String> list1;
//				try {
//					list1 = HrCurrencyParams.getValueBySql(sql1, "fd_id");
//					for(int i = 0;i<list1.size();i++){
//						if(i==list1.size()-1)
//							deptIds+="'"+list1.get(i)+"'";
//						else
//							deptIds=deptIds+"'"+list1.get(i)+"'"+",";
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				String sql2 = "select fd_person_info_id from hr_staff_move_record where fd_before_first_dept_name!=fd_after_first_dept_name and fd_after_dept_id in ("+deptIds+") and fd_move_date<='"+benyue_31+"' and fd_move_date>='"+benyue_1+"'";
//				List<String> list2;
//				String person_info_ids="";
//				try {
//					list2 = HrCurrencyParams.getValueBySql(sql2, "fd_person_info_id");
//					if(list2==null){
//						map.put("fd_in_month_count", 0);
//						map.put("fd_in_month_count1", 0);
//						map.put("fd_in_month_count2", 0);
//						map.put("fd_in_month_count3", 0);
//					}
//					else{
//					for(int i = 0;i<list2.size();i++){
//						if(i==list2.size()-1)
//							person_info_ids+="'"+list2.get(i)+"'";
//						else
//							person_info_ids=person_info_ids+"'"+list2.get(i)+"'"+",";
//					}
//					map.put("fd_in_month_count1", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_staff_type like '%派遣%'"));
//					map.put("fd_in_month_count2", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_staff_type like '%实习生%'"));
//					map.put("fd_in_month_count3", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"));
//					map.put("fd_in_month_count", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_staff_type like '%正编%'"));
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				String sql3 = "select fd_person_info_id from hr_staff_move_record where fd_before_first_dept_name!=fd_after_first_dept_name and fd_before_dept_id in ("+deptIds+") and fd_move_date<='"+benyue_31+"' and fd_move_date>='"+benyue_1+"'";
//				List<String> list3;
//				String person_info_ids1="";
//				try {
//					list3 = HrCurrencyParams.getValueBySql(sql3, "fd_person_info_id");
//					if(list3==null){
//						map.put("fd_out_month_count", 0);
//						map.put("fd_out_month_count1", 0);
//						map.put("fd_out_month_count2", 0);
//						map.put("fd_out_month_count3", 0);
//					}
//					else{
//					for(int i = 0;i<list3.size();i++){
//						if(i==list3.size()-1)
//							person_info_ids1+="'"+list3.get(i)+"'";
//						else
//							person_info_ids1=person_info_ids1+"'"+list3.get(i)+"'"+",";
//					}
//					map.put("fd_out_month_count", getWhereBy(dep_id, "fd_id in("+person_info_ids1+") and fd_staff_type like '%正编%'"));
//					map.put("fd_out_month_count1", getWhereBy(dep_id, "fd_id in("+person_info_ids1+") and fd_staff_type like '%派遣%'"));
//					map.put("fd_out_month_count2", getWhereBy(dep_id, "fd_id in("+person_info_ids1+") and fd_staff_type like '%实习生%'"));
//					map.put("fd_out_month_count3", getWhereBy(dep_id, "fd_id in("+person_info_ids1+") and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"));
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%正编%'"))==0){
//					map.put("fd_flow_month_rate",0.0);
//					map.put("fd_leave_month_rate", 0.0);
//				}else{
//					map.put("fd_flow_month_rate", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%正编%'")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%正编%'")))));
//					map.put("fd_leave_month_rate", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_staff_type like '%正编%'")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%正编%'")))));
//				}
//				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%正编%'"))==0)
//					map.put("fd_leave_rate",0.0);
//				else
//					map.put("fd_leave_rate", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_staff_type like '%正编%'")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%正编%'")))));
//				map.put("fd_begin_month_count1", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%派遣%'"));
//
//				map.put("fd_end_month_count1", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%派遣%'"));
//				map.put("fd_new_month_count1", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%派遣%'"));
//				map.put("fd_leave_month_count1", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_staff_type like '%派遣%'"));
//				map.put("fd_leave_count1", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_staff_type like '%派遣%'"));
//			
//				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%派遣%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%派遣%'"))==0){
//					map.put("fd_flow_month_rate1",0.0);
//					map.put("fd_leave_month_rate1",0.0);
//				}else{
//					map.put("fd_flow_month_rate1", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%派遣%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%派遣%'")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%派遣%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%派遣%'")))));
//					map.put("fd_leave_month_rate1", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_staff_type like '%派遣%'")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%派遣%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%派遣%'")))));
//				}
//				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_staff_type like '%派遣%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%派遣%'"))==0){
//					map.put("fd_leave_rate1", 0.0);
//				}else
//					map.put("fd_leave_rate1", (Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_staff_type like '%派遣%'")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_staff_type like '%派遣%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%派遣%'"))));
//				map.put("fd_begin_month_count2", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"));
//
//				map.put("fd_end_month_count2", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%'"));
//				map.put("fd_new_month_count2", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%实习生%'"));
//				map.put("fd_leave_month_count2", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_staff_type like '%实习生%'"));
//				map.put("fd_leave_count2", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_staff_type like '%实习生%'"));
//				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%实习生%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%'"))==0){
//					map.put("fd_flow_month_rate2",0.0);
//					map.put("fd_leave_month_rate2",0.0);
//				}
//				else{
//					map.put("fd_flow_month_rate2", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%实习生%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%实习生%'")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%实习生%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%'")))));
//					map.put("fd_leave_month_rate2", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_staff_type like '%实习生%'")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%实习生%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%'")))));
//				}
//				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_staff_type like '%实习生%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%'"))==0)
//					map.put("fd_leave_rate2",0.0);
//				else
//					map.put("fd_leave_rate2", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_staff_type like '%实习生%'")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_staff_type like '%实习生%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%'")))));
//				map.put("fd_begin_month_count3", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"));
//
//				map.put("fd_end_month_count3", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"));
//				map.put("fd_new_month_count3", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"));
//				map.put("fd_leave_month_count3", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"));
//				map.put("fd_leave_count3", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"));
//				if((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"))==0)){
//					map.put("fd_flow_month_rate3",0.0);
//					map.put("fd_leave_month_rate3",0.0);
//				}else{
//					map.put("fd_flow_month_rate3", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'")))));
//					map.put("fd_leave_month_rate3", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'")))));
//				}
//				if((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"))==0))
//					map.put("fd_leave_rate3",0.0);
//				else
//					map.put("fd_leave_rate3", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'")))));
//			
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
		sb.append("where fd_hierarchy_id like '%" + depID + "%'");
		
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

		sb.append("SELECT count(*) as result from hr_staff_person_info h left join hr_org_rank o on fd_org_rank_id=h.fd_id");
		sb.append(" where h.fd_status='official' and h.fd_hierarchy_id like '%" + depID + "%'");
		
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
