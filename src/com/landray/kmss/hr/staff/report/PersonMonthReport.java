package com.landray.kmss.hr.staff.report;

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
import com.landray.kmss.sys.attend.model.SysMonthCount;
import com.landray.kmss.sys.attend.service.ISysMonthCountService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 人事月报-人员结构分布表（职务类别不含实习生临时工，其他均含）
 * 
 * @author sunny
 * @version 创建时间：2022年9月30日下午5:30:03
 */
public class PersonMonthReport {
	private static final Log log = LogFactory.getLog(PersonMonthReport.class);
	private ISysMonthCountService sysMonthCountService;
	public ISysMonthCountService getSysMonthCountService() {
		if(sysMonthCountService==null){
			sysMonthCountService=(ISysMonthCountService)SpringBeanUtil.getBean("sysMonthCountService");
		}
		return sysMonthCountService;
	}
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
		
		ca.set(Calendar.YEAR, Integer.valueOf(y));
		ca.set(Calendar.MONTH, Integer.valueOf(m)-1);
		// 本月最后一天
		ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
		Calendar ca1 = Calendar.getInstance();// 得到一个Calendar的实例
		ca1.setTime(new Date()); // 设置时间为当前时间

		Calendar ca2 = Calendar.getInstance();// 得到一个Calendar的实例
		ca2.setTime(new Date()); // 设置时间为当前时间
		ca1.set(Calendar.YEAR, Integer.valueOf(y));
		ca1.set(Calendar.MONTH, Integer.valueOf(m)-1);
		ca2.set(Calendar.YEAR, Integer.valueOf(y));
		ca2.set(Calendar.MONTH, Integer.valueOf(1)-1);
		// 本月最后一天
		ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
		ca1.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		ca2.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String benyue_31 = sdf.format(ca.getTime());
		String benyue_1 = sdf.format(ca1.getTime());
		String yiyue_1 = sdf.format(ca2.getTime());
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
			int p = Integer.parseInt(m)-1;
			int o = Integer.parseInt(y)-1;
			String yearMonth=""+y+p;
			String yearMonth1=""+y+m;
			String yearMonth2=""+o+12;
			int beforyearBegin1 = 0;
			int beforyearBegin2 = 0;
			int beforyearBegin = 0;
			int beforyearBegin3 = 0;
			for (Map<String, Object> dep_map : dept_list) {
				String dep_id = dep_map.get("fd_id") + "";
				Map<String, Object> map = new HashMap<String, Object>();
				try {
					Object obj = getSysMonthCountService().findFirstOne("fdYearMonth="+yearMonth+" and fdDepartmentId='"+dep_id+"'","");
					Object obj2 = getSysMonthCountService().findFirstOne("fdYearMonth="+yearMonth1+" and fdDepartmentId='"+dep_id+"'","");
					Object obj3 = getSysMonthCountService().findFirstOne("fdYearMonth="+yearMonth2+" and fdDepartmentId='"+dep_id+"'","");
					if(obj==null){
						map.put("fd_begin_month_count", 0);
						map.put("fd_begin_month_count3", 0);
						map.put("fd_begin_month_count1", 0);
						map.put("fd_begin_month_count2", 0);
					}else{
					SysMonthCount obj1 = (SysMonthCount)obj;
					map.put("fd_begin_month_count", obj1.getFdOfficial()+obj1.getFdRehireAfterRetirement());
					map.put("fd_begin_month_count3", obj1.getFdOfficial()+obj1.getFdDispatch()+obj1.getFdPractice()+obj1.getFdRehireAfterRetirement());
					map.put("fd_begin_month_count1", obj1.getFdDispatch());
					map.put("fd_begin_month_count2", obj1.getFdPractice());
					}
					if(obj3!=null){
						SysMonthCount obj1 = (SysMonthCount)obj3;
						beforyearBegin=obj1.getFdOfficial()+obj1.getFdRehireAfterRetirement();
						beforyearBegin1=obj1.getFdDispatch();
						beforyearBegin2=obj1.getFdPractice();
						beforyearBegin3=obj1.getFdOfficial()+obj1.getFdDispatch()+obj1.getFdPractice()+obj1.getFdRehireAfterRetirement();
					}
					if(obj2==null){
					map.put("fd_end_month_count", 0);
					map.put("fd_end_month_count1", 0);
					map.put("fd_end_month_count2", 0);
					map.put("fd_end_month_count3", 0);
					map.put("fd_in_month_count", 0);
					map.put("fd_in_month_count1", 0);
					map.put("fd_in_month_count2", 0);
					map.put("fd_in_month_count3", 0);
					map.put("fd_out_month_count", 0);
					map.put("fd_out_month_count1", 0);
					map.put("fd_out_month_count2", 0);
					map.put("fd_out_month_count3", 0);
					}else{
						SysMonthCount obj1 = (SysMonthCount)obj2;
						map.put("fd_end_month_count", obj1.getFdOfficial()+obj1.getFdRehireAfterRetirement());
						map.put("fd_end_month_count3", obj1.getFdOfficial()+obj1.getFdDispatch()+obj1.getFdPractice()+obj1.getFdRehireAfterRetirement());
						map.put("fd_end_month_count1", obj1.getFdDispatch());
						map.put("fd_end_month_count2", obj1.getFdPractice());
						map.put("fd_in_month_count", obj1.getFdOfficialIn()+obj1.getFdRehireAfterRetirementIn());
						map.put("fd_in_month_count1", obj1.getFdDispatchIn());
						map.put("fd_in_month_count2", obj1.getFdPracticeIn());
						map.put("fd_in_month_count3", obj1.getFdOfficialIn()+obj1.getFdDispatchIn()+obj1.getFdPracticeIn()+obj1.getFdRehireAfterRetirementIn());
						map.put("fd_out_month_count", obj1.getFdOfficialOut()+obj1.getFdRehireAfterRetirementOut());
						map.put("fd_out_month_count1", obj1.getFdDispatchOut());
						map.put("fd_out_month_count2", obj1.getFdPracticeOut());
						map.put("fd_out_month_count3", obj1.getFdOfficialOut()+obj1.getFdDispatchOut()+obj1.getFdPracticeOut()+obj1.getFdRehireAfterRetirementOut());
						}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//部门名称
				map.put("fd_dept_name", dep_map.get("fd_name") + "");
				
				//人数小计
//				map.put("fd_begin_month_count", getWhereBy(dep_id, "fd_status in ('rehireAfterRetirement','trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%正编%' or (fd_status='leave'  and fd_entry_time<'"+benyue_1+"' and fd_leave_time<='"+benyue_31+"') and fd_staff_type like '%正编%'"));
//				map.put("fd_begin_month_count3", getWhereBy(dep_id, "fd_status in ('rehireAfterRetirement','trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%') or (fd_status='leave'  and fd_entry_time<'"+benyue_1+"' and fd_leave_time<='"+benyue_31+"') and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%')"));
//				map.put("fd_begin_month_count1", getWhereBy(dep_id, "fd_status in ('rehireAfterRetirement','trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"'  and fd_staff_type like '%派遣%' or (fd_status='leave'  and fd_entry_time<'"+benyue_1+"' and fd_leave_time<='"+benyue_31+"') and fd_staff_type like '%派遣%'"));
//				map.put("fd_begin_month_count2", getWhereBy(dep_id, "fd_status in ('rehireAfterRetirement','trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"'  and fd_staff_type like '%实习生%' or (fd_status='leave'  and fd_entry_time<'"+benyue_1+"' and fd_leave_time<='"+benyue_31+"') and fd_staff_type like '%实习生%'"));
//				
//				map.put("fd_end_month_count", getWhereBy(dep_id, "fd_status in ('rehireAfterRetirement','trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%正编%'"));
				map.put("fd_new_month_count", getWhereBy(dep_id, "fd_status in ('rehireAfterRetirement','leave','trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and (fd_staff_type like '%正编%' or fd_staff_type like '%返聘%')"));
				map.put("fd_leave_month_count", getWhereBy3(dep_id, "fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+benyue_1+"' and( fd_staff_type like '%离职正编%' or fd_staff_type like '%离职返聘%' )"));
				map.put("fd_leave_count", getWhereBy3(dep_id, "fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+yiyue_1+"' and ( fd_staff_type like '%离职正编%' or fd_staff_type like '%离职返聘%' )"));
				String sql1 = "select fd_id from sys_org_element where fd_hierarchy_id like '%"+dep_id+"%'";
				String deptIds = "";
				List<String> list1;
				try {
					list1 = HrCurrencyParams.getValueBySql(sql1, "fd_id");
					for(int i = 0;i<list1.size();i++){
						if(i==list1.size()-1)
							deptIds+="'"+list1.get(i)+"'";
						else
							deptIds=deptIds+"'"+list1.get(i)+"'"+",";
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
//					map.put("fd_in_month_count2", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_staff_type like '%实习生%'"));
//
//					map.put("fd_in_month_count1", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_staff_type like '%派遣%'"));
//					map.put("fd_in_month_count2", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_staff_type like '%实习生%'"));
//					map.put("fd_in_month_count3", getWhereBy2("fd_id in("+person_info_ids+") and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%' or fd_staff_type like '%在职返聘%')"));
//					map.put("fd_in_month_count3", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"));
//					map.put("fd_in_month_count", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_staff_type like '%正编%' or fd_staff_type like '%在职返聘%'"));
//					map.put("fd_begin_month_count", getWhereBy(dep_id, "fd_status in ('rehireAfterRetirement','trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"'   and fd_staff_type like '%正编%' and fd_id not in("+person_info_ids+") or (fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_31+"') and fd_staff_type like '%正编%' and fd_id not in("+person_info_ids+")"));
//					map.put("fd_begin_month_count3", getWhereBy(dep_id, "fd_status in ('rehireAfterRetirement','trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%' and fd_id not in("+person_info_ids+") or (fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_31+"') and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%' and fd_id not in("+person_info_ids+")"));
//					map.put("fd_begin_month_count1", getWhereBy(dep_id, "fd_status in ('rehireAfterRetirement','trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%派遣%' and fd_id not in("+person_info_ids+") or (fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_31+"') and fd_staff_type like '%派遣%' and fd_id not in("+person_info_ids+")"));
//					map.put("fd_begin_month_count2", getWhereBy(dep_id, "fd_status in ('rehireAfterRetirement','trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%实习生%' and fd_id not in("+person_info_ids+") or (fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_31+"') and fd_staff_type like '%实习生%' and fd_id not in("+person_info_ids+")"));
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
//
//					if((int)map.get("fd_end_month_count1")-(int)map.get("fd_begin_month_count")>0)
//					map.put("fd_in_month_count1", (int)map.get("fd_end_month_count1")-(int)map.get("fd_begin_month_count1"));
//					else
//						map.put("fd_out_month_count1", (int)map.get("fd_end_month_count1")-(int)map.get("fd_begin_month_count1"));
//					if((int)map.get("fd_end_month_count3")-(int)map.get("fd_begin_month_count3")>0)
//					map.put("fd_in_month_count3", (int)map.get("fd_end_month_count3")-(int)map.get("fd_begin_month_count3"));
//					else
//						map.put("fd_out_month_count3", (int)map.get("fd_end_month_count1")-(int)map.get("fd_begin_month_count1"));
//					if((int)map.get("fd_end_month_count2")-(int)map.get("fd_begin_month_count2")>0)
//					map.put("fd_in_month_count2", (int)map.get("fd_end_month_count2")-(int)map.get("fd_begin_month_count2"));
//					else
//						map.put("fd_out_month_count2", (int)map.get("fd_end_month_count2")-(int)map.get("fd_begin_month_count2"));
//					if((int)map.get("fd_end_month_count")-(int)map.get("fd_begin_month_count")>0)
//					map.put("fd_in_month_count", (int)map.get("fd_end_month_count")-(int)map.get("fd_begin_month_count"));
//					else
//						map.put("fd_out_month_count", (int)map.get("fd_end_month_count")-(int)map.get("fd_begin_month_count"));
////					map.put("fd_out_month_count", getWhereBy2("fd_id in("+person_info_ids1+") and fd_staff_type like '%正编%'  or fd_staff_type like '%在职返聘%'"));
////					map.put("fd_out_month_count1", getWhereBy2("fd_id in("+person_info_ids1+") and fd_staff_type like '%派遣%'"));
////					map.put("fd_out_month_count2", getWhereBy2( "fd_id in("+person_info_ids1+") and fd_staff_type like '%实习生%'"));
////					map.put("fd_out_month_count3", getWhereBy2("fd_id in("+person_info_ids1+") and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%'  or fd_staff_type like '%在职返聘%')"));
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%正编%'"))==0){
				if((Integer) map.get("fd_begin_month_count")+((Integer)map.get("fd_end_month_count"))==0){
					map.put("fd_flow_month_rate",0.0);
					map.put("fd_leave_month_rate", 0.0);
				}else{
					map.put("fd_flow_month_rate", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%正编%'")))*200.0/((Integer) map.get("fd_begin_month_count")+((Integer)map.get("fd_end_month_count")))));
					map.put("fd_leave_month_rate", df.format(((Integer.parseInt(getWhereBy3(dep_id, "fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+benyue_1+"') and ( fd_staff_type like '%离职正编%' or fd_staff_type like '%离职返聘%' ")))*200.0/((Integer) map.get("fd_begin_month_count")+((Integer)map.get("fd_end_month_count"))))));
				}
//				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_staff_type like '%正编%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%正编%'"))==0)
				if(beforyearBegin+((Integer) map.get("fd_end_month_count"))==0)
					map.put("fd_leave_rate",0.0);
				else
					map.put("fd_leave_rate", df.format((Integer.parseInt(getWhereBy3(dep_id, "fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+yiyue_1+"' and ( fd_staff_type like '%离职正编%' or fd_staff_type like '%离职返聘%' )")))*200.0/(beforyearBegin+((Integer) map.get("fd_end_month_count")))));
				
//				map.put("fd_end_month_count1", getWhereBy(dep_id, "fd_status in ('rehireAfterRetirement','trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%派遣%'"));
				map.put("fd_new_month_count1", getWhereBy(dep_id, "fd_status in ('leave','trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%派遣%'"));
				map.put("fd_leave_month_count1", getWhereBy3(dep_id, "fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+benyue_1+"' and fd_staff_type like '%离职派遣%'"));
				map.put("fd_leave_count1", getWhereBy3(dep_id, "fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+yiyue_1+"' and fd_staff_type like '%离职派遣%'"));
			
//				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%派遣%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%派遣%'"))==0){
				if(((Integer) map.get("fd_begin_month_count1"))+((Integer) map.get("fd_end_month_count1"))==0){
					map.put("fd_flow_month_rate1",0.0);
					map.put("fd_leave_month_rate1",0.0);
				}else{
					map.put("fd_flow_month_rate1", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%派遣%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%派遣%'")))*200.0/(((Integer) map.get("fd_begin_month_count1"))+((Integer) map.get("fd_end_month_count1")))));
					map.put("fd_leave_month_rate1", df.format((Integer.parseInt(getWhereBy3(dep_id, "fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+benyue_1+"' and fd_staff_type like '%离职派遣%'")))*200.0/(((Integer) map.get("fd_begin_month_count1"))+((Integer) map.get("fd_end_month_count1")))));
				}
//				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_staff_type like '%派遣%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%派遣%'"))==0){
				if(beforyearBegin1+((Integer) map.get("fd_end_month_count1"))==0){
					map.put("fd_leave_rate1", 0.0);
				}else
					map.put("fd_leave_rate1", df.format((Integer.parseInt(getWhereBy3(dep_id, "fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+yiyue_1+"' and fd_staff_type like '%离职派遣%'")))*200.0/(beforyearBegin1+((Integer) map.get("fd_end_month_count1")))));
				
//				map.put("fd_end_month_count2", getWhereBy(dep_id, "fd_status in ('rehireAfterRetirement','trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%'"));
				map.put("fd_new_month_count2", getWhereBy(dep_id, "fd_status in ('leave','trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%实习生%'"));
				map.put("fd_leave_month_count2", getWhereBy3(dep_id, "fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+benyue_1+"' and fd_staff_type like '%离职实习生%'"));
				map.put("fd_leave_count2", getWhereBy3(dep_id, "fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+yiyue_1+"' and fd_staff_type like '%离职实习生%'"));
//				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_staff_type like '%实习生%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%'"))==0){
				if(((Integer) map.get("fd_begin_month_count2"))+((Integer) map.get("fd_end_month_count2"))==0){
					map.put("fd_flow_month_rate2",0.0);
					map.put("fd_leave_month_rate2",0.0);
				}
				else{
					map.put("fd_flow_month_rate2", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%实习生%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_staff_type like '%实习生%'")))*200.0/(((Integer) map.get("fd_begin_month_count2"))+((Integer) map.get("fd_end_month_count2")))));
					map.put("fd_leave_month_rate2", df.format((Integer.parseInt(getWhereBy3(dep_id, "fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+benyue_1+"' and fd_staff_type like '%离职实习生%'")))*200.0/(((Integer) map.get("fd_begin_month_count2"))+((Integer) map.get("fd_end_month_count2")))));
				}
//				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_staff_type like '%实习生%'"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_staff_type like '%实习生%'"))==0)
				if(beforyearBegin2+((Integer) map.get("fd_end_month_count2"))==0)
					map.put("fd_leave_rate2",0.0);
				else
					map.put("fd_leave_rate2", df.format((Integer.parseInt(getWhereBy3(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_staff_type like '%实习生%'")))*200.0/(beforyearBegin2+((Integer) map.get("fd_end_month_count2")))));
			
//				map.put("fd_end_month_count3", getWhereBy(dep_id, "fd_status in ('rehireAfterRetirement','trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%')"));
				map.put("fd_new_month_count3", getWhereBy(dep_id, "fd_status in ('leave','trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%')"));
				map.put("fd_leave_month_count3", getWhereBy3(dep_id, " fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+benyue_1+"' and (fd_staff_type like '%离职实习生%' or fd_staff_type like '%离职派遣%' or fd_staff_type like '%离职正编%'  or fd_staff_type like '%离职返聘%')"));
				map.put("fd_leave_count3", getWhereBy3(dep_id, "fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+yiyue_1+"' and (fd_staff_type like '%离职实习生%' or fd_staff_type like '%离职派遣%' or fd_staff_type like '%离职正编%'  or fd_staff_type like '%离职返聘%')"));
//				if((In teger.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%')"))==0)){
				if(((Integer) map.get("fd_begin_month_count3"))+((Integer) map.get("fd_end_month_count3"))==0){
					map.put("fd_flow_month_rate3",0.0);
					map.put("fd_leave_month_rate3",0.0);
				}else{
					map.put("fd_flow_month_rate3", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%')")))*200.0/(((Integer) map.get("fd_begin_month_count3"))+((Integer) map.get("fd_end_month_count3")))));
					map.put("fd_leave_month_rate3", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and (fd_staff_type like '%离职实习生%' or fd_staff_type like '%离职派遣%' or fd_staff_type like '%离职正编%'  or fd_staff_type like '%离职返聘%')")))*200.0/(((Integer) map.get("fd_begin_month_count3"))+((Integer) map.get("fd_end_month_count3")))));
				}
//				if((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and (fd_staff_type like '%实习生%' or fd_staff_type like '%派遣%' or fd_staff_type like '%正编%')"))==0))
				if(beforyearBegin3+((Integer) map.get("fd_end_month_count3"))==0)
					map.put("fd_leave_rate3",0.0);
				else
					map.put("fd_leave_rate3", df.format((Integer.parseInt(getWhereBy3(dep_id, "fd_resignation_date<='"+benyue_31+"' and fd_resignation_date>='"+yiyue_1+"' and (fd_staff_type like '%离职实习生%' or fd_staff_type like '%离职派遣%' or fd_staff_type like '%离职正编%' or fd_staff_type like '%离职返聘%')")))*200.0/(beforyearBegin3+((Integer) map.get("fd_end_month_count3")))));
			
				list.add(map);
			}

		}

		return list;

	}
	public List<Map<String, Object>> monthReport1(String y,String m) throws SQLException {
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
		Calendar ca1 = Calendar.getInstance();// 得到一个Calendar的实例
		ca1.setTime(new Date()); // 设置时间为当前时间

		Calendar ca2 = Calendar.getInstance();// 得到一个Calendar的实例
		ca2.setTime(new Date()); // 设置时间为当前时间
		ca1.set(Calendar.YEAR, Integer.valueOf(y));
		ca1.set(Calendar.MONTH, Integer.valueOf(m)-1);
		ca2.set(Calendar.YEAR, Integer.valueOf(y));
		ca2.set(Calendar.MONTH, Integer.valueOf(1)-1);
		// 本月最后一天
		ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
		ca1.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		ca2.set(Calendar.DATE, ca.getActualMinimum(Calendar.DATE));
		String benyue_31 = sdf.format(ca.getTime());
		String benyue_1 = sdf.format(ca1.getTime());
		String yiyue_1 = sdf.format(ca2.getTime());
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
			for (Map<String, Object> dep_map : dept_list) {
				String dep_id = dep_map.get("fd_id") + "";
				Map<String, Object> map = new HashMap<String, Object>();

				//部门名称
				map.put("fd_dept_name", dep_map.get("fd_name") + "");
				
				//人数小计
				map.put("fd_begin_month_count", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"'  and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"));
				map.put("fd_begin_month_count4", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"'  and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"));

//				map.put("fd_end_month_count", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"'  and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"));
				map.put("fd_end_month_count4", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"'  and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"));
				map.put("fd_new_month_count", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"'  and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"));
				map.put("fd_new_month_count4", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"'  and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"));
				map.put("fd_leave_month_count", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"'  and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"));
				map.put("fd_leave_month_count4", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"'  and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"));
				map.put("fd_leave_count", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"'  and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"));
				String sql1 = "select fd_id from sys_org_element where fd_hierarchy_id like '%"+dep_id+"%'";
				String deptIds = "";
				List<String> list1;
				try {
					list1 = HrCurrencyParams.getValueBySql(sql1, "fd_id");
					for(int i = 0;i<list1.size();i++){
						if(i==list1.size()-1)
							deptIds+="'"+list1.get(i)+"'";
						else
							deptIds=deptIds+"'"+list1.get(i)+"'"+",";
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String sql2 = "select fd_person_info_id from hr_staff_move_record where fd_before_first_dept_name!=fd_after_first_dept_name and fd_after_dept_id in ("+deptIds+") and fd_move_date<='"+benyue_31+"' and fd_move_date>='"+benyue_1+"'";
				List<String> list2;
				String person_info_ids="";
				try {
					list2 = HrCurrencyParams.getValueBySql(sql2, "fd_person_info_id");
					if(list2==null){
						map.put("fd_in_month_count", 0);
						map.put("fd_in_month_count1", 0);
						map.put("fd_in_month_count2", 0);
						map.put("fd_in_month_count4", 0);
						map.put("fd_in_month_count3", 0);
					}
					else{
					for(int i = 0;i<list2.size();i++){
						if(i==list2.size()-1)
							person_info_ids+="'"+list2.get(i)+"'";
						else
							person_info_ids=person_info_ids+"'"+list2.get(i)+"'"+",";
					}
					map.put("fd_in_month_count1", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"));
					map.put("fd_in_month_count2", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"));
					map.put("fd_in_month_count4", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"));
					map.put("fd_in_month_count3", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"));
					map.put("fd_in_month_count", getWhereBy(dep_id, "fd_id in("+person_info_ids+") and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')"));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String sql3 = "select fd_person_info_id from hr_staff_move_record where fd_before_first_dept_name!=fd_after_first_dept_name and fd_before_dept_id in ("+deptIds+") and fd_move_date<='"+benyue_31+"' and fd_move_date>='"+benyue_1+"'";
				List<String> list3;
				String person_info_ids1="";
				try {
					list3 = HrCurrencyParams.getValueBySql(sql3, "fd_person_info_id");
					if(list3==null){
						map.put("fd_out_month_count", 0);
						map.put("fd_out_month_count1", 0);
						map.put("fd_out_month_count2", 0);
						map.put("fd_out_month_count3", 0);
						map.put("fd_out_month_count4", 0);
					}
					else{
					for(int i = 0;i<list3.size();i++){
						if(i==list3.size()-1)
							person_info_ids1+="'"+list3.get(i)+"'";
						else
							person_info_ids1=person_info_ids1+"'"+list3.get(i)+"'"+",";
					}
					map.put("fd_out_month_count", getWhereBy(dep_id, "fd_id in("+person_info_ids1+") and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')"));
					map.put("fd_out_month_count1", getWhereBy(dep_id, "fd_id in("+person_info_ids1+") and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"));
					map.put("fd_out_month_count2", getWhereBy(dep_id, "fd_id in("+person_info_ids1+") and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"));
					map.put("fd_out_month_count4", getWhereBy(dep_id, "fd_id in("+person_info_ids1+") and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"));
					map.put("fd_out_month_count3", getWhereBy(dep_id, "fd_id in("+person_info_ids1+") and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"'  and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"'  and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')"))==0){
					map.put("fd_flow_month_rate",0.0);
					map.put("fd_leave_month_rate", 0.0);
				}else{
					map.put("fd_flow_month_rate", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')")))));
					map.put("fd_leave_month_rate", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')")))));
				}
				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"'  and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"'  and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"))==0){
					map.put("fd_flow_month_rate4",0.0);
					map.put("fd_leave_month_rate4", 0.0);
				}else{
					map.put("fd_flow_month_rate4", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')")))));
					map.put("fd_leave_month_rate4", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')")))));
				}
				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')"))==0)
					map.put("fd_leave_rate",0.0);
				else
					map.put("fd_leave_rate", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%M%')")))));
				map.put("fd_begin_month_count1", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"));

				map.put("fd_end_month_count1", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"));
				map.put("fd_new_month_count1", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"));
				map.put("fd_leave_month_count1", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"));
				map.put("fd_leave_count1", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"));
				map.put("fd_leave_count4", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"));
				
				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"))==0){
					map.put("fd_flow_month_rate1",0.0);
					map.put("fd_leave_month_rate1",0.0);
				}else{
					map.put("fd_flow_month_rate1", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')")))));
					map.put("fd_leave_month_rate1", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')")))));
				}
				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"))==0){
					map.put("fd_leave_rate1", 0.0);
				}else
					map.put("fd_leave_rate1", (Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%S%')"))));
				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"))==0){
					map.put("fd_leave_rate4", 0.0);
				}else
					map.put("fd_leave_rate4", (Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%')"))));
				map.put("fd_begin_month_count2", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"));

				map.put("fd_end_month_count2", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"));
				map.put("fd_new_month_count2", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"));
				map.put("fd_leave_month_count2", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"));
				map.put("fd_leave_count2", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"));
				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"))==0){
					map.put("fd_flow_month_rate2",0.0);
					map.put("fd_leave_month_rate2",0.0);
				}
				else{
					map.put("fd_flow_month_rate2", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')")))));
					map.put("fd_leave_month_rate2", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')")))));
				}
				if(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"))==0)
					map.put("fd_leave_rate2",0.0);
				else
					map.put("fd_leave_rate2", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%P%')")))));
				map.put("fd_begin_month_count3", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"));

				map.put("fd_end_month_count3", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"));
				map.put("fd_new_month_count3", getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"));
				map.put("fd_leave_month_count3", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"));
				map.put("fd_leave_count3", getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"));
				if((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"))==0)){
					map.put("fd_flow_month_rate3",0.0);
					map.put("fd_leave_month_rate3",0.0);
				}else{
					map.put("fd_flow_month_rate3", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_entry_time<='"+benyue_31+"' and fd_entry_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')")))));
					map.put("fd_leave_month_rate3", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+benyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')")))));
				}
				if((Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"))==0))
					map.put("fd_leave_rate3",0.0);
				else
					map.put("fd_leave_rate3", df.format((Integer.parseInt(getWhereBy(dep_id, "fd_status='leave' and fd_leave_time<='"+benyue_31+"' and fd_leave_time>='"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')")))*200.0/(Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<'"+yiyue_1+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')"))+Integer.parseInt(getWhereBy(dep_id, "fd_status in ('trial','official','temporary','trialDelay','practice') and fd_entry_time<='"+benyue_31+"' and fd_org_rank_id in (select fd_id from hr_org_rank where fd_name like '%O%' or fd_name like '%S%' or fd_name like '%P%' or fd_name like '%M%')")))));
			
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
		sb.append("where (fd_hierarchy_id like '%" + depID + "%' or fd_hierarchy_id='0' and fd_first_level_department_id='"+depID+"')");
		
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
	public String getWhereBy3(String depID, String sqlWhere) {
		StringBuffer sb = new StringBuffer();

		sb.append("select count(*) as result from hr_staff_person_info ");
		sb.append("where fd_first_level_department_id= '" + depID + "'");
		
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
	public String getWhereBy2( String sqlWhere) {
		StringBuffer sb = new StringBuffer();

		sb.append("select count(*) as result from hr_staff_person_info ");
		
		if (sqlWhere != null) {
			sb.append(" where (" + sqlWhere + ")");
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
