package com.landray.kmss.fssc.voucher.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.util.EopBasedataAuthUtil;
import com.landray.kmss.fssc.common.util.DataBaseUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.voucher.constant.FsscVoucherConstant;
import com.landray.kmss.fssc.voucher.model.FsscVoucherMain;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherMainService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.hibernate.query.Query;
import com.landray.kmss.util.StringUtil;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FsscVoucherPortletService extends ExtendDataServiceImp{

	private IFsscVoucherMainService fsscVoucherMainService;

	public void setFsscVoucherMainService(IFsscVoucherMainService fsscVoucherMainService) {
		if (fsscVoucherMainService == null) {
			fsscVoucherMainService =  (IFsscVoucherMainService) SpringBeanUtil.getBean("fsscVoucherMainService");
		}
		this.fsscVoucherMainService = fsscVoucherMainService;
	}

	public JSONObject getBookingSuccessData(HttpServletRequest request) throws Exception{
		JSONObject rtnJson=new JSONObject();
		Calendar cal = Calendar.getInstance();
		Integer year=cal.get(Calendar.YEAR);
		//年度需记账总量（未记账成功的数量）
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setSelectBlock("count(fdId)");
		hqlInfo.setWhereBlock(" fsscVoucherMain.docCreateTime between :startDate and :endDate and fsscVoucherMain.fdBookkeepingStatus<>:fdBookkeepingStatus");
		hqlInfo.setParameter("startDate", DateUtil.convertStringToDate(year+"-01-01"));
		hqlInfo.setParameter("endDate", DateUtil.convertStringToDate(year+"-12-31 23:59:59"));
		hqlInfo.setParameter("fdBookkeepingStatus", FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30);
		List<Long> totalList=fsscVoucherMainService.findValue(hqlInfo);   //今年需记账凭证数
		Long needTotal=!ArrayUtil.isEmpty(totalList)&&totalList.get(0)!=null?totalList.get(0):0L;
		rtnJson.put("total", needTotal);
		//年度已记账总量
		hqlInfo.setWhereBlock(" fsscVoucherMain.fdBookkeepingDate between :startDate and :endDate and fsscVoucherMain.fdBookkeepingStatus=:fdBookkeepingStatus ");
		hqlInfo.setParameter("fdBookkeepingStatus", FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30);
		List<Long> successList=fsscVoucherMainService.findValue(hqlInfo);  //今年记账成功凭证数
		Long success=!ArrayUtil.isEmpty(successList)&&successList.get(0)!=null?successList.get(0):0L;
		if((success+needTotal)>0){
			rtnJson.put("percent", FsscNumberUtil.getMultiplication(FsscNumberUtil.getDivide(success,success+needTotal,4),100,2));
		}else{
			rtnJson.put("percent", 100.00);
		}
		//统计每月处理凭证数
		StringBuilder sql=new StringBuilder(" select ");
		for(int i=1;i<=12;i++){
			sql.append("count(case when main.fd_bookkeeping_date between :startDate"+i+" and :endDate"+i+" then fd_id end) as count_"+i );
			if(i<12){
				sql.append(",");
			}
		}
		sql.append(" from fssc_voucher_main main where main.fd_bookkeeping_status=:success");
		Query query=fsscVoucherMainService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString());
		for(int i=1;i<=12;i++){
			String dateStr=year+(i<10?"-0"+i:"-"+i)+"-01";
			Date startDate=DateUtil.convertStringToDate(dateStr);
			query.setParameter("startDate"+i,startDate);
			query.setParameter("endDate"+i,getMonehStartOrEnd(startDate,"last"));
		}
		query.setParameter("success",FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_30);
		List<Object[]> resultList=query.list();
		JSONObject monthData=new JSONObject();
		if(!ArrayUtil.isEmpty(resultList)){
			Object[] obj=resultList.get(0);
			for(int i=0,size=obj.length;i<size;i++){
				if(obj[i]!=null){
					monthData.put((i+1)<=12?(i+1):"-"+(i+1),obj[i]);
				}
			}
			rtnJson.put("monthData",monthData);
		}
		return rtnJson;
	}

	/**
	 * 获取某月的第一天和最后一天
	 * @param date
	 * @param type
	 * @return
	 */
	public static Date getMonehStartOrEnd(Date date, String type) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		if ("last".equals(type)) {
			ca.add(Calendar.MONTH, 1);
			ca.add(Calendar.DAY_OF_MONTH, -1);
		}
		return ca.getTime();
	}

	public Long getApprovedModelCount(Date startDate,Date endDate) throws  Exception{
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setSelectBlock("count(distinct fdModelId)");
		String whereBlock=hqlInfo.getWhereBlock();
		SysOrgElement user=UserUtil.getUser();
		if(!UserUtil.getKMSSUser().isAdmin()){
			//只能看自己记账的
			whereBlock=StringUtil.linkString(whereBlock, " and ", "  fsscVoucherMain.fdBookkeepingPerson=:fdBookkeepingPerson   ");
			hqlInfo.setParameter("fdBookkeepingPerson", UserUtil.getUser());
		}
			whereBlock=StringUtil.linkString(whereBlock, " and ", " fsscVoucherMain.fdBookkeepingDate between :startDate and :endDate   ");
			hqlInfo.setParameter("startDate",startDate);
		    hqlInfo.setParameter("endDate",endDate);
			whereBlock=StringUtil.linkString(whereBlock, " and ", " fsscVoucherMain.fdBookkeepingStatus=:fdBookkeepingStatus ");
			hqlInfo.setParameter("fdBookkeepingStatus",FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_30);
		    hqlInfo.setWhereBlock(whereBlock);
		List<Long> totalList=fsscVoucherMainService.findValue(hqlInfo);
		return !ArrayUtil.isEmpty(totalList)&&totalList.get(0)!=null?totalList.get(0):0L;
	}

	/**
	 *获取当前登录人当前处理的条数
	 * @return
	 * @throws Exception
	 */
	public Long getApprovedCount(String year,String fdBookkeepingStatus) throws  Exception{
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setSelectBlock("count(distinct fdId)");
		String whereBlock=hqlInfo.getWhereBlock();
		if(StringUtil.isNotNull(year)){
			whereBlock=StringUtil.linkString(whereBlock, " and ", " fsscVoucherMain.docCreateTime>=:beginTime and fsscVoucherMain.docCreateTime<=:endTime ");
			hqlInfo.setParameter("beginTime", DateUtil.convertStringToDate(year+"-01-01",DateUtil.PATTERN_DATE));
			hqlInfo.setParameter("endTime",  DateUtil.convertStringToDate(year+"-12-31 23:59:59",DateUtil.PATTERN_DATETIME));
		}
		if(StringUtil.isNotNull(fdBookkeepingStatus)){
			whereBlock=StringUtil.linkString(whereBlock, " and ", " fsscVoucherMain.fdBookkeepingStatus=:fdBookkeepingStatus ");
			hqlInfo.setParameter("fdBookkeepingStatus", fdBookkeepingStatus);
		}
		hqlInfo.setWhereBlock(whereBlock);
		List<Long> totalList=fsscVoucherMainService.findValue(hqlInfo);
		return !ArrayUtil.isEmpty(totalList)&&totalList.get(0)!=null?totalList.get(0):0L;
	}



	public JSONObject   findApprovedCount(String year,String fdBookkeepingStatus) throws  Exception{
		List<Object[]> list=new ArrayList<>();
		StringBuilder builder = new StringBuilder();

		String driveName = DataBaseUtil.getDataBaseType();
		if ("oracle".equals(driveName)) {
			builder.append(
					"	select  " +
							"		SUM(case TO_CHAR(main.fd_voucher_date,'MM') when '1' then 1 else 0 end) as one,  " +
							"		SUM(case TO_CHAR(main.fd_voucher_date,'MM') when '2' then 1 else 0 end) as two,  " +
							"		SUM(case TO_CHAR(main.fd_voucher_date,'MM') when '3' then 1 else 0 end) as three,  " +
							"		SUM(case TO_CHAR(main.fd_voucher_date,'MM') when '4' then 1 else 0 end) as four,  " +
							"		SUM(case TO_CHAR(main.fd_voucher_date,'MM') when '5' then 1 else 0 end) as five,  " +
							"		SUM(case TO_CHAR(main.fd_voucher_date,'MM') when '6' then 1 else 0 end) as six,  " +
							"		SUM(case TO_CHAR(main.fd_voucher_date,'MM') when '7' then 1 else 0 end) as seven,  " +
							"		SUM(case TO_CHAR(main.fd_voucher_date,'MM') when '8' then 1 else 0 end) as eight,  " +
							"		SUM(case TO_CHAR(main.fd_voucher_date,'MM') when '9' then 1 else 0 end) as nine,  " +
							"		SUM(case TO_CHAR(main.fd_voucher_date,'MM') when '10' then 1 else 0 end) as ten,  " +
							"		SUM(case TO_CHAR(main.fd_voucher_date,'MM') when '11' then 1 else 0 end) as eleven,  " +
							"		SUM(case TO_CHAR(main.fd_voucher_date,'MM') when '12' then 1 else 0 end) as twelve  " +
							"	from  fssc_voucher_main main " +
							"WHERE main.fd_bookkeeping_status="+fdBookkeepingStatus+
							" AND TO_CHAR(main.fd_voucher_date,'yyyy') = '"+year+"' ");
		}else if("mysql".equals(driveName)){
			builder.append(
					"	select  " +
							"		SUM(case month(main.fd_voucher_date) when '1' then 1 else 0 end) as one,  " +
							"		SUM(case month(main.fd_voucher_date) when '2' then 1 else 0 end) as two,  " +
							"		SUM(case month(main.fd_voucher_date) when '3' then 1 else 0 end) as three,  " +
							"		SUM(case month(main.fd_voucher_date) when '4' then 1 else 0 end) as four,  " +
							"		SUM(case month(main.fd_voucher_date) when '5' then 1 else 0 end) as five,  " +
							"		SUM(case month(main.fd_voucher_date) when '6' then 1 else 0 end) as six,  " +
							"		SUM(case month(main.fd_voucher_date) when '7' then 1 else 0 end) as seven,  " +
							"		SUM(case month(main.fd_voucher_date) when '8' then 1 else 0 end) as eight,  " +
							"		SUM(case month(main.fd_voucher_date) when '9' then 1 else 0 end) as nine,  " +
							"		SUM(case month(main.fd_voucher_date) when '10' then 1 else 0 end) as ten,  " +
							"		SUM(case month(main.fd_voucher_date) when '11' then 1 else 0 end) as eleven,  " +
							"		SUM(case month(main.fd_voucher_date) when '12' then 1 else 0 end) as twelve  " +
							"	from  fssc_voucher_main main " +
							"WHERE main.fd_bookkeeping_status="+fdBookkeepingStatus+
							" AND YEAR(main.fd_voucher_date) = '"+year+"' ");
		}else if("sqlserver".equals(driveName)){
			builder.append(
					"	select  " +
							"		SUM(case DATEPART(mm,main.fd_voucher_date) when '1' then 1 else 0 end) as one,  " +
							"		SUM(case DATEPART(mm,main.fd_voucher_date) when '2' then 1 else 0 end) as two,  " +
							"		SUM(case DATEPART(mm,main.fd_voucher_date) when '3' then 1 else 0 end) as three,  " +
							"		SUM(case DATEPART(mm,main.fd_voucher_date) when '4' then 1 else 0 end) as four,  " +
							"		SUM(case DATEPART(mm,main.fd_voucher_date) when '5' then 1 else 0 end) as five,  " +
							"		SUM(case DATEPART(mm,main.fd_voucher_date) when '6' then 1 else 0 end) as six,  " +
							"		SUM(case DATEPART(mm,main.fd_voucher_date) when '7' then 1 else 0 end) as seven,  " +
							"		SUM(case DATEPART(mm,main.fd_voucher_date) when '8' then 1 else 0 end) as eight,  " +
							"		SUM(case DATEPART(mm,main.fd_voucher_date) when '9' then 1 else 0 end) as nine,  " +
							"		SUM(case DATEPART(mm,main.fd_voucher_date) when '10' then 1 else 0 end) as ten,  " +
							"		SUM(case DATEPART(mm,main.fd_voucher_date) when '11' then 1 else 0 end) as eleven,  " +
							"		SUM(case DATEPART(mm,main.fd_voucher_date) when '12' then 1 else 0 end) as twelve  " +
							"	from  fssc_voucher_main main " +
							"WHERE main.fd_bookkeeping_status="+fdBookkeepingStatus+
							" AND DATEPART(yy,main.fd_voucher_date) = '"+year+"' ");
		}
		Query result = fsscVoucherMainService.getBaseDao().getHibernateSession().createNativeQuery(builder.toString());
         List<Object[]> resultList=result.list();
		JSONObject json = null;
		if(resultList!=null) {
			for (int i = 0; i < resultList.size(); i++) {
				json = new JSONObject();
				Object[] object = resultList.get(i);
				json.put("one", (null != object[0] ? object[0] : 0));
				json.put("two", (null != object[1] ? object[1] : 0));
				json.put("three", (null != object[2] ? object[2] : 0));
				json.put("four", (null != object[3] ? object[3] : 0));
				json.put("five", (null != object[4] ? object[4] : 0));
				json.put("six", (null != object[5] ? object[5] : 0));
				json.put("seven", (null != object[6] ? object[6] : 0));
				json.put("eight", (null != object[7] ? object[7] : 0));
				json.put("nine", (null != object[8] ? object[8] : 0));
				json.put("ten", (null != object[9] ? object[9] : 0));
				json.put("eleven", (null != object[10] ? object[10] : 0));
				json.put("twelve", (null != object[11] ? object[11] : 0));
			}
		}
		return json;
	}




}
