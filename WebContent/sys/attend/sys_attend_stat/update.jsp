<%@ page import="org.springframework.transaction.TransactionStatus" %>
<%@ page import="com.landray.kmss.util.TransactionUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.apache.commons.collections4.CollectionUtils" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.landray.kmss.sys.attend.model.SysAttendBusiness" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="com.landray.kmss.sys.attend.util.AttendUtil" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendBusinessService" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendListenerCommonService" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendSynDingService" %>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.util.DateUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	// 该功能仅允许系统管理员可使用
	if(!UserUtil.getKMSSUser().isAdmin()) {
		return;
	}
%>
<%!
	private ISysAttendListenerCommonService sysAttendListenerCommonService;

	public ISysAttendListenerCommonService getSysAttendListenerCommonService() {
		if (sysAttendListenerCommonService == null) {
			sysAttendListenerCommonService = (ISysAttendListenerCommonService) SpringBeanUtil
					.getBean("sysAttendListenerCommonService");
		}
		return sysAttendListenerCommonService;
	}
	private ISysAttendBusinessService sysAttendBusinessService;

	public ISysAttendBusinessService getSysAttendBusinessService() {
		if (sysAttendBusinessService == null) {
			sysAttendBusinessService = (ISysAttendBusinessService) SpringBeanUtil
					.getBean("sysAttendBusinessService");
		}
		return sysAttendBusinessService;
	}

	public String test(){
		String msg="";
		ISysAttendListenerCommonService sysAttendListenerCommonService = (ISysAttendListenerCommonService) SpringBeanUtil
				.getBean("sysAttendListenerCommonService");
		ISysAttendSynDingService sysAttendSynDingService = (ISysAttendSynDingService) SpringBeanUtil.getBean("sysAttendSynDingService");

		ISysAttendBusinessService sysAttendBusinessService = (ISysAttendBusinessService) SpringBeanUtil
				.getBean("sysAttendBusinessService");
		TransactionStatus status = null;
		boolean isException = false;
		List<Date> dateList = new ArrayList<>();
		Date tempDate = new Date();
		for(int i=0;i < 1;i++) {
			dateList.add(AttendUtil.getDate(tempDate, i+1));
			msg += DateUtil.convertDateToString(AttendUtil.getDate(tempDate, i+1),"yyyy-MM-dd");
		}
		List<String> orgList = new ArrayList<>();
		orgList.add("17bedbe7bd0bdcae490bd4b49e0bed5a");

//		try {
//			//重新生成有效考勤记录
//			status = TransactionUtils.beginTransaction();
//
//			//根据流程、原始考勤记录重新生成有效考勤记录
//			List<Integer> fdTypes = new ArrayList<Integer>();
//			fdTypes.add(4);
//			fdTypes.add(5);
//			fdTypes.add(7);
//			//过滤所有重复的流程记录
//			Set<SysAttendBusiness> businessSet = new HashSet<>();
//			for (Date date : dateList) {
//				// 出差/请假/外出记录
//				List<SysAttendBusiness> busList = sysAttendBusinessService.findBussList(orgList, date, AttendUtil.getDate(date, 1), fdTypes);
//				if (CollectionUtils.isNotEmpty(busList)) {
//					businessSet.addAll(busList);
//				}
//			}
//			//根据流程（流程分类）来生成对应的有效考勤记录
//			for (SysAttendBusiness business : businessSet) {
//				if (Integer.valueOf(5).equals(business.getFdType())) {
//					sysAttendListenerCommonService.updateSysAttendMainByLeaveBis(business);
//				} else if (Integer.valueOf(7).equals(business.getFdType())) {
//					sysAttendListenerCommonService.updateSysAttendMainByOutgoing(business);
//				} else if (Integer.valueOf(4).equals(business.getFdType())) {
//					sysAttendListenerCommonService.updateSysAttendMainByBusiness(business);
//				}
//			}
//		} catch (Exception e) {
//			isException = true;
//			e.printStackTrace();
//		} finally {
//			if (isException && status != null) {
//				if (status != null) {
//					TransactionUtils.getTransactionManager()
//							.rollback(status);
//				}
//			} else if (status != null) {
//				TransactionUtils.getTransactionManager().commit(status);
//			}
//			isException = false;
//			status = null;
//		}
//		try {
//			status = TransactionUtils.beginTransaction();
//			sysAttendSynDingService.recalMergeClockAllStatus(dateList, orgList);
//		} catch (Exception e) {
//			isException = true;
//			e.printStackTrace();
//		} finally {
//			if (isException && status != null) {
//				if (status != null) {
//					TransactionUtils.getTransactionManager()
//							.rollback(status);
//				}
//			} else if (status != null) {
//				TransactionUtils.getTransactionManager().commit(status);
//			}
//			isException = false;
//		}
		try {
			//重新生成有效考勤记录
			status = TransactionUtils.beginTransaction();
			//过滤所有重复的流程记录
			Set<SysAttendBusiness> businessSet = new HashSet<>();
			List<Integer> fdTypes = new ArrayList<Integer>();
			fdTypes.add(4);
			fdTypes.add(5);
			fdTypes.add(7);
			for (Date date : dateList) {
				// 出差/请假/外出记录
				List<SysAttendBusiness> busList = this.getSysAttendBusinessService().findBussList(orgList, date, AttendUtil.getDate(date, 1), fdTypes);
				if (CollectionUtils.isNotEmpty(busList)) {
					businessSet.addAll(busList);
				}
			}
			//根据流程（流程分类）来生成对应的有效考勤记录
			for (SysAttendBusiness business : businessSet) {
				if (Integer.valueOf(5).equals(business.getFdType())) {
					getSysAttendListenerCommonService().updateSysAttendMainByLeaveBis(business);
				} else if (Integer.valueOf(7).equals(business.getFdType())) {
					getSysAttendListenerCommonService().updateSysAttendMainByOutgoing(business);
				} else if (Integer.valueOf(4).equals(business.getFdType())) {
					getSysAttendListenerCommonService().updateSysAttendMainByBusiness(business);
				}
			}
		} catch (Exception e) {
			isException = true;
			e.printStackTrace();
		} finally {
			if (isException && status != null) {
				if (status != null) {
					TransactionUtils.getTransactionManager()
							.rollback(status);
				}
			} else if (status != null) {
				TransactionUtils.getTransactionManager().commit(status);
			}
			isException = false;
			status =null;
		}
		return msg;
	}
%>
<%
	String action="update.jsp";
	String msg = null;
	Object obj =session.getAttribute("resetTime202109012");
	if(obj !=null){
		msg ="正在执行，请稍后查看数据";
	} else {
		String p = request.getParameter("p");
		String resetNumber = request.getParameter("resetNumber");
		if ("1".equals(resetNumber)) {
			session.setAttribute("resetTime202109012", true);
			msg =test();
			session.removeAttribute("resetTime202109012");
		}else{

		}
	}
%>
<template:include ref="default.simple">
	<template:replace name="title">
		重新生成有效考勤记录--员工组
	</template:replace>
	<template:replace name="body">

		<div style="margin: 10px;">
			<div class="">
				<h2>重新生成有效考勤记录--员工组</h2>
				<form id="submitForm" action="" method="post" onSubmit="javascript:return checkCFSubmit();">

					<select name="resetNumber">
						<option value ="0">请选择</option>
						<option value ="1">重置</option>
					</select>
					<input type="button" value="开始"  onClick="submitFormBtn()" >
				</form>
			</div>
			<% if(StringUtil.isNotNull(msg)) { %>
			<div style="margin: 20px 0;">
				<pre style="background-color: aliceblue;">
				<%=msg%>
			</pre>
			</div>
			<% } %>
		</div>
		<script>
			var resubmit = false;
			function checkCFSubmit()
			{
				if (resubmit )
				{
					document.getElementById("submitForm").action=""
					return false;
				}
				else
				{
					resubmit = true;
					return true;
				}
			}

			function submitFormBtn(){
				resubmit =false;
				document.getElementById("submitForm").action="update.jsp"
				document.getElementById("submitForm").submit();
			}
		</script>
	</template:replace>
</template:include>
