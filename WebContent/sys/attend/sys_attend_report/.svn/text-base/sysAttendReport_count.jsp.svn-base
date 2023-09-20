<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<%@ page import="java.util.Calendar,java.util.List,java.util.ArrayList,java.util.Collections,java.util.Date,java.util.Calendar,com.landray.kmss.util.DateUtil,com.landray.kmss.util.UserUtil,com.landray.kmss.util.StringUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.attend.util.AttendUtil,com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService,com.landray.kmss.sys.time.model.SysTimeLeaveRule,com.landray.kmss.sys.attend.service.ISysAttendStatService" %>
<%
	// 获取请假细分信息
	ISysTimeLeaveRuleService sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil.getBean("sysTimeLeaveRuleService");
	List<SysTimeLeaveRule> list = sysTimeLeaveRuleService.findList("sysTimeLeaveRule.fdIsAvailable=true", "");
	List list1 = new ArrayList<SysTimeLeaveRule>();
	if(list != null && !list.isEmpty()){
		for(int j=0;j<list.size();j++)
		for(int i=0;i<list.size();i++){
			SysTimeLeaveRule sysTimeLeaveRule = (SysTimeLeaveRule)list.get(i);
			SysTimeLeaveRule tmpSysTimeLeaveRule = new SysTimeLeaveRule();
			if(sysTimeLeaveRule.getFdName().equals("事假")){
				Collections.swap(list,i ,0 );
			}
			if(sysTimeLeaveRule.getFdName().equals("病假")){
				Collections.swap(list,  i,1);
			}
			if(sysTimeLeaveRule.getFdName().equals("婚假")){
				Collections.swap(list, i, 2);
			}
			if(sysTimeLeaveRule.getFdName().equals("丧假")){
				Collections.swap(list, i, 3);
			}
			if(sysTimeLeaveRule.getFdName().equals("产假")){
				Collections.swap(list, i, 4);
			}
			if(sysTimeLeaveRule.getFdName().equals("陪产假")){
				Collections.swap(list, i, 5);
			}
			if(sysTimeLeaveRule.getFdName().equals("年休假")){
				Collections.swap(list, i, 6);
			}
			if(sysTimeLeaveRule.getFdName().equals("调休")){
				Collections.swap(list, i, 7);
			}
			if(sysTimeLeaveRule.getFdName().equals("工伤假")){
				Collections.swap(list, i, 8);
			}
			if(sysTimeLeaveRule.getFdName().equals("产前检查假")){
				Collections.swap(list, i, 9);
			}
			if(sysTimeLeaveRule.getFdName().equals("哺乳假")){
				Collections.swap(list, i, 10);
			}
			if(sysTimeLeaveRule.getFdName().equals("产前工间休息")){
				Collections.swap(list, i, 11);
			}
			if(sysTimeLeaveRule.getFdName().equals("计划生育假")){
				Collections.swap(list, i, 12);
			}
		}
		request.setAttribute("leaveRuleList",  list);
	}
	// 每日明细表头
	Calendar cal = Calendar.getInstance();
	List<String> statKeyList = new ArrayList<String>();
	cal.set(Calendar.YEAR, 2016);
	cal.set(Calendar.DAY_OF_YEAR, 1);
	Date startStat = cal.getTime();
	cal.add(Calendar.YEAR, 1);
	Date endStat = cal.getTime();
	for(cal.setTime(startStat);cal.getTime().before(endStat); cal.add(Calendar.DATE, 1)){
		statKeyList.add("statKey_" + (cal.get(Calendar.MONTH) + 1) + "_" + cal.get(Calendar.DATE));
	}
	request.setAttribute("statKeyList", statKeyList);
	
	// 时间
	cal.setTime(new Date());
	int startYear = cal.get(Calendar.YEAR) - 10;
	int endYear = cal.get(Calendar.YEAR) + 10;
	int curYear = cal.get(Calendar.YEAR);
	int curMonth = cal.get(Calendar.MONTH) + 1;
	request.setAttribute("curYear", curYear);
	request.setAttribute("curMonth", curMonth);
	Date fdMonth = AttendUtil.getMonth(new Date(), 0);
	Date nowDate = AttendUtil.getDate(new Date(), 0);
	request.setAttribute("fdMonth", DateUtil.convertDateToString(fdMonth, DateUtil.TYPE_DATETIME, null));
	request.setAttribute("fdStartTime", DateUtil.convertDateToString(fdMonth, DateUtil.TYPE_DATE, null));
	request.setAttribute("fdEndTime", DateUtil.convertDateToString(nowDate, DateUtil.TYPE_DATE, null));
	
	// 部门
	String deptId =  UserUtil.getUser().getFdParent() == null ? "" :
		UserUtil.getUser().getFdParent().getFdId();
	String deptName = UserUtil.getUser().getFdParentsName();
	// 报表名
	String fdName = curYear + ResourceUtil.getString("sys-attend:sysAttendReport.fdName.fdYearPart")
					+ curMonth + ResourceUtil.getString("sys-attend:sysAttendReport.fdName.fdMonthPartNew")
					+ ResourceUtil.getString("sys-attend:sysAttendReport.report");
	request.setAttribute("fdName", fdName);
	// 权限
	ISysAttendStatService sysAttendStatService = (ISysAttendStatService) SpringBeanUtil.getBean("sysAttendStatService");
	request.setAttribute("isStatDeptLeader", sysAttendStatService.isStatDeptLeader());
	request.setAttribute("isStatCateReader", sysAttendStatService.isStatCateReader());
%>
<template:include ref="default.simple4list">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/attend.css" />
		<style type="text/css">
			td.orgTd {position: relative;}
			td.orgTd label{position: absolute;top: 10px;margin-left: 5px;}
		</style>
		<script type="text/javascript">
			seajs.use(['theme!form']);
			Com_IncludeFile("validation.js|plugin.js|validation.jsp|xform.js|eventbus.js");
			/**
			 * 添加js和样式到顶部
			 * @type {HTMLScriptElement}
			 */
			var link = document.createElement('link');
			link.rel = 'Stylesheet';
			link.href = "${LUI_ContextPath}/sys/attend/resource/css/export.css";
			window.top.document.body.appendChild(link);

			var script = document.createElement('script');
			script.type = 'text/javascript';
			script.src = "${LUI_ContextPath}/sys/attend/resource/js/reportDrawer.js";
			window.top.document.body.appendChild(script);
		</script>
	</template:replace>
	<template:replace name="nav">
		<div class="lui_list_noCreate_frame">
			<ui:combin ref="menu.nav.create">
				<ui:varParam name="title" value="${ lfn:message('sys-attend:module.sys.attend') }" />
				<ui:varParam name="button">
					[
						{
							"text": "${ lfn:message('sys-attend:module.sys.attend') }",
							"href": "javascript:void(0)",
							"icon": "lui_icon_l_icon_89"
						}
					]
				</ui:varParam>				
			</ui:combin>
		</div>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
			 	<c:import url="/sys/attend/nav.jsp" charEncoding="UTF-8">
				   <c:param name="key" value="reportMonth"></c:param>
				</c:import>		 
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${ lfn:message('sys-attend:sysAttend.nav.month.stat') }">
				<form id="monthForm" name="monthForm" action="${LUI_ContextPath}/sys/attend/sys_attend_report/sysAttendReport.do?method_GET=add" method="post" >
				<%-- 报表名 --%>
			 	<input type="hidden" name="fdName" value="${fdName }" />
			 	<input type="hidden" name="fdExportShowCols"/>
			 	<input type="hidden" name="fdDateFormat"/>
			 	<c:set var="fdoffTypeNames" scope="page">
	 				<c:forEach items="${leaveRuleList }" var="item">
	 					${fn:escapeXml(item.fdName)};
	 				</c:forEach>
	 			</c:set>
			 	<input type="hidden" name="fdoffTypeNames" value="${fdoffTypeNames }" />
			 	
			 	<div style="margin-top: 20px; padding: 0 30px;">
				 	<table class="tb_normal" width=100%>
				 		<tr>
							<td class="td_normal_title" width="150px">
								${ lfn:message('sys-attend:sysAttendCategory.range') }
							</td>			
							<td class="orgTd">
								<c:set var="showTargetType" value="${isStatDeptLeader ? 1 : (isStatCateReader ? 2 : 1) }"></c:set>
								<c:if test="${isStatDeptLeader || isStatCateReader}">
								<div style="width: 150px;float:left;">
									<xform:select property="fdTargetType" style="width: 90%;height: 25px;margin-top: 2px;" showPleaseSelect="false" showStatus="edit" onValueChange="changeTargetType" value="${showTargetType}">
										<c:if test="${isStatDeptLeader}">
											<xform:simpleDataSource value="1">${lfn:message('sys-attend:sysAttendReport.range.byDepartment') }</xform:simpleDataSource>
										</c:if>
										<c:if test="${isStatCateReader}">
											<xform:simpleDataSource value="2">${lfn:message('sys-attend:sysAttendReport.range.byAttendanceGroup') }</xform:simpleDataSource>
										</c:if>
									</xform:select>
								</div>
								</c:if>
								<%-- 部门 --%>
								<c:if test="${isStatDeptLeader}">
								<div id='targetDept' style="width: 50%;float:left;${showTargetType != 1 ? 'display:none;' : ''}">
									<xform:address 
									propertyId="fdDeptIds" propertyName="fdDeptNames" validators="${showTargetType == 1 ? 'required' : ''}" mulSelect="true" showStatus="edit" subject="${ lfn:message('sys-attend:sysAttendStatDetail.dept') }" idValue="<%=deptId %>" nameValue="<%=deptName %>" style="width:95%;height: 25px;"
									orgType="ORG_FLAG_AVAILABLEALL"
									>
									</xform:address>
									<span class="txtstrong">*</span>
								</div>
								</c:if>
								<%-- 考勤组 --%>
								<c:if test="${isStatCateReader}">
								<div id='targetCate' style="width: 50%;float:left;${showTargetType != 2 ? 'display:none;' : ''}">
									<xform:dialog propertyId="fdCategoryIds" propertyName="fdCategoryNames" showStatus="edit" 
										style="width:95%;height: 26px;" validators="${showTargetType == 2 ? 'required' : ''}" subject="${ lfn:message('sys-attend:sysAttendCategory.attend') }">
								  	 	selectCategory();
									</xform:dialog>
									<span class="txtstrong">*</span>
								</div>
								</c:if>
								<c:if test="${isStatDeptLeader || isStatCateReader}">
									<xform:checkbox property="fdIsQuit" showStatus="edit">
										<xform:simpleDataSource value="true">${ lfn:message('sys-attend:sysAttendStatDetail.fdIsQuit') }</xform:simpleDataSource>
									</xform:checkbox>
								</c:if>
							</td>
				 		</tr>
				 		<tr>
				 			<%-- 时间 --%>
							<td class="td_normal_title" width="150px">
								${ lfn:message('sys-attend:sysAttendReport.fdMonth') }
							</td>		
							<td>
								<div style="width: 150px;float:left;">
									<xform:select property="fdDateType" style="width: 90%;height: 25px;" showStatus="edit" showPleaseSelect="false" onValueChange="changeDateType">
										<xform:simpleDataSource value="1">${ lfn:message('sys-attend:sysAttendReport.month.statisticsByMonth') }</xform:simpleDataSource>
										<xform:simpleDataSource value="2">${ lfn:message('sys-attend:sysAttendReport.month.statisticsByDateInterval') }</xform:simpleDataSource>
									</xform:select>
								</div>
								<%-- 按月统计 --%>
								<div id="monthType" style="width: 500px;float: left;">
									<xform:select property="fdYearPart" onValueChange="updateFdMonth();updateFdName();" validators="required" subject="${ lfn:message('sys-attend:sysAttendReport.fdName.fdYearPart') }" value="${curYear }" showStatus="edit" showPleaseSelect="false" style="width: 100px;height: 25px;">
									<c:forEach begin="<%=startYear %>" end="<%=endYear %>" var="year">
										<xform:simpleDataSource value="${year }"></xform:simpleDataSource>
									</c:forEach>
									</xform:select>
									<xform:select property="fdMonthPart" onValueChange="updateFdMonth();updateFdName();" validators="required" subject="${ lfn:message('sys-attend:sysAttendReport.fdName.fdMonthPart') }" value="${curMonth }" showStatus="edit" showPleaseSelect="false" style="width: 100px;height: 25px;">
										<c:forEach begin="1" end="12" var="month">
											<xform:simpleDataSource value="${month }"></xform:simpleDataSource>
										</c:forEach>
									</xform:select>
									<input type="hidden" name="fdMonth" value="${fdMonth }" />
								</div>
								<%-- 按日期区间统计 --%>
								<div id="dateType" style="display: none;width: 500px;float: left;">
									<xform:datetime property="fdStartTime" dateTimeType="date" showStatus="edit" value="${fdStartTime }" onValueChange="updateFields" subject="${ lfn:message('sys-attend:sysAttendStatDetail.fdStartTime') }" style="width: 150px;height: 24px;"></xform:datetime><span class="txtstrong">*</span>
									<span style="position: relative;top:-5px;margin: 0 5px;">—</span>
									<xform:datetime property="fdEndTime" dateTimeType="date" showStatus="edit" value="${fdEndTime }" onValueChange="updateFdNameByDate" subject="${ lfn:message('sys-attend:sysAttendStatDetail.fdEndTime') }" style="width: 150px;height: 24px;"></xform:datetime><span class="txtstrong">*</span>
								</div>
							</td>
					 	</tr>
					 	<tr>
					 		<td class="td_normal_title" width="150px">
					 			${ lfn:message('sys-attend:sysAttendReport.showColumn') }
					 			<xform:checkbox property="fdAllShowCols" showStatus="edit" isArrayValue="false" onValueChange="selectAll">
					 				<xform:simpleDataSource value="">${ lfn:message("sys-ui:ui.listview.selectall") }</xform:simpleDataSource>
					 			</xform:checkbox>
					 		</td>
					 		<td>
					 			<xform:checkbox property="fdShowCols" showStatus="edit" isArrayValue="false" value="fdStaffNo;docCreator.fdName;fdDept;fdEntryTime;enterDays;fdCategoryName;fdMonth;fdShouldDays;fdHolidays;fdWorkDateDays;fdActualDays;fdStatusDays;fdAbsentDays;fdTripDays;fdOffDays;fdLateCount;fdLateExcCount;fdLateTime;fdLeftCount;fdLeftExcCount;fdLeftTime;fdOutsideCount;fdMissedCount;fdMissedExcCount;fdTotalTime;fdOverTime;fdWorkOverTime;fdOffOverTime;fdHolidayOverTime;fdOutgoingTime;${fdoffTypeNames }" onValueChange="changeColumn">
									<xform:simpleDataSource value="fdAffiliatedCompany">所属公司</xform:simpleDataSource>
									<xform:simpleDataSource value="fdFirstLevelDepartment">一级部门</xform:simpleDataSource>
									<xform:simpleDataSource value="fdSecondLevelDepartment">二级部门</xform:simpleDataSource>
									<xform:simpleDataSource value="fdThirdLevelDepartment">三级部门</xform:simpleDataSource>
									<xform:simpleDataSource value="fdStaffNo">人员编号</xform:simpleDataSource>
									<xform:simpleDataSource value="fdOrgPost">岗位</xform:simpleDataSource>
									<xform:simpleDataSource value="fdStaffingLevel.fdName">职务</xform:simpleDataSource>
									<%--<xform:simpleDataSource value="fdDept">${ lfn:message('sys-attend:sysAttendStatMonth.dept') }</xform:simpleDataSource>--%>
									<xform:simpleDataSource value="fdEntryTime">入职日期</xform:simpleDataSource>
									<xform:simpleDataSource value="fdResignationDate">离职日期</xform:simpleDataSource>
									<xform:simpleDataSource value="fdStaffType">人员类别</xform:simpleDataSource>
									<!--当年年假-->
									<xform:simpleDataSource value="fdTotalDays">年假天数</xform:simpleDataSource>
									<xform:simpleDataSource value="fdUsedDays">已休年假天数</xform:simpleDataSource>
									<xform:simpleDataSource value="fdRestDays">剩余年假天数</xform:simpleDataSource>
									<!--当年调休假-->
									<xform:simpleDataSource value="fdTxTotalDays">调休假天数</xform:simpleDataSource>
									<xform:simpleDataSource value="fdTxUsedDays">已休调休假天数</xform:simpleDataSource>
									<xform:simpleDataSource value="fdTxRestDays">剩余调休假天数</xform:simpleDataSource>

									<xform:simpleDataSource value="fdCategoryName">${ lfn:message('sys-attend:sysAttendStatMonth.category') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdShouldDays">${ lfn:message('sys-attend:sysAttendStatMonth.fdShouldDays') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdHolidays">${ lfn:message('sys-attend:sysAttendStatMonth.fdShouldDaysAndHolidays') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdWorkDateDays">${ lfn:message('sys-attend:sysAttendStatMonth.fdWorkDateDays') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdActualDays">${ lfn:message('sys-attend:sysAttendStatMonth.fdActualDays') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdTotalTime">${ lfn:message('sys-attend:sysAttendStatMonth.fdTotalTime') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdStatusDays">${ lfn:message('sys-attend:sysAttendStatMonth.fdStatusDays') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdAbsentDays">${ lfn:message('sys-attend:sysAttendStatMonth.fdAbsentDays') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdTripDays">${ lfn:message('sys-attend:sysAttendStatMonth.fdTripDays') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdOutgoingTime">${ lfn:message('sys-attend:sysAttendStatMonth.fdOutgoingTime') }</xform:simpleDataSource>
									
									<xform:simpleDataSource value="fdLateCount">${ lfn:message('sys-attend:sysAttendStatMonth.fdLateCount') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdLateTime">${ lfn:message('sys-attend:sysAttendStatMonth.fdLateTime') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdLateExcCount">${ lfn:message('sys-attend:sysAttendStatMonth.fdLateExcCount') }</xform:simpleDataSource>
									
									<xform:simpleDataSource value="fdLeftCount">${ lfn:message('sys-attend:sysAttendStatMonth.fdLeftCount') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdLeftTime">${ lfn:message('sys-attend:sysAttendStatMonth.fdLeftTime') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdLeftExcCount">${ lfn:message('sys-attend:sysAttendStatMonth.fdLeftExcCount') }</xform:simpleDataSource>
									
									<xform:simpleDataSource value="fdOutsideCount">${ lfn:message('sys-attend:sysAttendStatMonth.fdOutsideCount') }</xform:simpleDataSource>
<%-- 									<xform:simpleDataSource value="fdMissedCount">${ lfn:message('sys-attend:sysAttendStatMonth.fdMissedCount') }</xform:simpleDataSource> --%>
									<xform:simpleDataSource value="fdMissedExcCount">${ lfn:message('sys-attend:sysAttendStatMonth.fdMissedExcCount') }</xform:simpleDataSource>
									
									<xform:simpleDataSource value="fdWorkOverTime">${ lfn:message('sys-attend:sysAttendStatMonth.fdWorkOverTime') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdOffOverTime">${ lfn:message('sys-attend:sysAttendStatMonth.fdOffOverTime') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdHolidayOverTime">${ lfn:message('sys-attend:sysAttendStatMonth.fdHolidayOverTime') }</xform:simpleDataSource>
									<xform:simpleDataSource value="fdOverTime">${ lfn:message('sys-attend:sysAttendStatMonth.fdOverTime') }</xform:simpleDataSource>

									<xform:simpleDataSource value="fdWorkOverApplyTime">申请工作日加班</xform:simpleDataSource>
									<xform:simpleDataSource value="fdOffOverApplyTime">申请休息日加班</xform:simpleDataSource>
									<xform:simpleDataSource value="fdHolidayOverApplyTime">申请节假日加班</xform:simpleDataSource>
<%-- 									<xform:simpleDataSource value="fdOverApplyTime">加班申请工时</xform:simpleDataSource> --%>
									<xform:simpleDataSource value="fdOverApplyTime">申请加班合计</xform:simpleDataSource>
									<xform:simpleDataSource value="enterDays">新进天数</xform:simpleDataSource>
									<xform:simpleDataSource value="leaveDays">离职天数</xform:simpleDataSource>
									
									<c:forEach items="${leaveRuleList }" var="item">
										<xform:simpleDataSource value="${fn:escapeXml(item.fdName)}">${fn:escapeXml(item.fdName)}</xform:simpleDataSource>
									</c:forEach>
									<xform:simpleDataSource value="fdOffDays">${ lfn:message('sys-attend:sysAttendStatMonth.fdOffDays') }</xform:simpleDataSource>
									
									<xform:simpleDataSource value="fdDateDetail">${ lfn:message('sys-attend:sysAttendStatMonth.fdDateDetail') }</xform:simpleDataSource>
								</xform:checkbox>
					 		</td>
					 	</tr>
				 	</table>
				 	<div style="text-align: center;padding: 20px;">
				 		<ui:button text="${ lfn:message('button.list') }" order="1" style="margin-right: 10px;"
					      onclick="listReport();">
						</ui:button>
						<ui:button text="${ lfn:message('sys-attend:sysAttendReport.export') }" order="2" style="margin-right: 10px;"
						  onclick="exportExcel();">
						</ui:button>
						<kmss:auth requestURL="/sys/attend/sys_attend_report/sysAttendReport.do?method=add">
							<ui:button text="${ lfn:message('sys-attend:sysAttendReport.generateReport') }" order="3"  style="margin-right: 10px;"
							  onclick="generateReport();">
							</ui:button>
						</kmss:auth>

						<ui:button text="${ lfn:message('sys-attend:sysAttendReportLog.list') }" order="4"
								   onclick="window.top.exportLog('2');">
						</ui:button>
				 	</div>
			 	</div>
			 </form>
			 <div class="lui-attend-stat-table lui_attend_report_list" style="display:none;">
			 	<list:listview id="monthListview">
					<ui:source type="AjaxJson">
							{url:""}
					</ui:source>
				<!-- 列表视图 -->	
				<list:colTable isDefault="false" layout="sys.attend.listview.month">
					<list:col-serial></list:col-serial>
					<list:col-auto props="fdAffiliatedCompany;fdFirstLevelDepartment;fdSecondLevelDepartment;fdThirdLevelDepartment"></list:col-auto>
					<list:col-auto props="docCreator.fdName;fdStaffNo;docCreator.fdLoginName;fdCategoryName;fdOrgPost;fdStaffingLevel"></list:col-auto>
					<list:col-auto props="fdEntryTime;fdResignationDate;fdMonth;fdStaffType"></list:col-auto>
					<list:col-auto props="fdTotalDays;fdUsedDays;fdRestDays;fdTxTotalDays;fdTxUsedDays;fdTxRestDays"></list:col-auto>
					<list:col-column property="fdShouldDays" sort="fdShouldDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdShouldDays.br') }" headerStyle="min-width: 80px;"></list:col-column>
					<list:col-column property="fdHolidays" sort="fdHolidays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdShouldDaysAndHolidays.br') }" headerStyle="min-width: 80px;"></list:col-column>
					<list:col-column property="fdActualDays" sort="fdActualDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdActualDays.br') }" headerStyle="min-width: 80px;"></list:col-column>
					<list:col-auto props="enterDays;leaveDays;"></list:col-auto>
					<list:col-column property="fdWorkDateDays" sort="fdWorkDateDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdWorkDateDays.br') }" headerStyle="min-width: 80px;"></list:col-column>
					<list:col-column property="fdTotalTime" sort="fdTotalTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdTotalTime.br') }" headerStyle="min-width: 80px;"></list:col-column>
					<list:col-column property="fdStatusDays" sort="fdStatusDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdStatusDays.br') }" headerStyle="min-width: 70px;"></list:col-column>
					<c:forEach items="${leaveRuleList }" var="item">
						<list:col-column headerClass="lui_thead" property="${item.fdName }" title="${fn:escapeXml(item.fdName)}<br/>${ lfn:message('sys-attend:sysAttendStatMonth.day') }" headerStyle="min-width: 60px;"></list:col-column>
					</c:forEach>
					<list:col-column property="fdOffDays" sort="fdOffDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOffDays.br') }" headerStyle="min-width: 60px;"></list:col-column>
					<list:col-column property="fdAbsentDays" sort="fdAbsentDaysCount" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdAbsentDays.br') }" headerStyle="min-width: 70px;"></list:col-column>
					<list:col-column property="fdLateCount" sort="fdLateCount" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLateCount.br') }" headerStyle="min-width: 70px;"></list:col-column>
					<list:col-column property="fdLateTime" sort="fdLateTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLateTime.br') }" headerStyle="min-width: 70px;"></list:col-column>
					<list:col-column property="fdTripDays" sort="fdTripDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdTripDays.br') }" headerStyle="min-width: 70px;"></list:col-column>
					<list:col-column property="fdOutgoingTime" sort="fdOutgoingTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOutgoingTime.br') }" headerStyle="min-width: 70px;"></list:col-column>
					
					<list:col-column property="fdLateExcCount" sort="fdLateExcCount" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLateExcCount.br') }" headerStyle="min-width: 80px;"></list:col-column>
					
					<list:col-column property="fdLeftCount" sort="fdLeftCount" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLeftCount.br') }" headerStyle="min-width: 70px;"></list:col-column>
					<list:col-column property="fdLeftTime" sort="fdLeftTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLeftTime.br') }" headerStyle="min-width: 70px;"></list:col-column>
					<list:col-column property="fdLeftExcCount" sort="fdLeftExcCount" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLeftExcCount.br') }" headerStyle="min-width: 80px;"></list:col-column>
					
					<list:col-column property="fdOutsideCount" sort="fdOutsideCount" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOutsideCount.br') }" headerStyle="min-width: 70px;"></list:col-column>
					<%--<list:col-column property="fdMissedCount" sort="fdMissedCount" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdMissedCount.br') }" headerStyle="min-width: 70px;"></list:col-column>--%>
					<list:col-column property="fdMissedExcCount" sort="fdMissedExcCount" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdMissedExcCount.br') }" headerStyle="min-width: 80px;"></list:col-column>
					
					<list:col-column property="fdWorkOverTime" sort="fdWorkOverTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdWorkOverTime.br') }" headerStyle="min-width: 100px;"></list:col-column>
					<list:col-column property="fdOffOverTime" sort="fdOffOverTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOffOverTime.br') }" headerStyle="min-width: 100px;"></list:col-column>
					<list:col-column property="fdHolidayOverTime" sort="fdHolidayOverTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdHolidayOverTime.br') }" headerStyle="min-width: 100px;"></list:col-column>
					<list:col-column property="fdOverTime" sort="fdOverTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOverTime.br') }" headerStyle="min-width: 100px;"></list:col-column>

					<list:col-column property="fdWorkOverApplyTime" sort="fdWorkOverApplyTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdWorkOverTime.br') }" headerStyle="min-width: 100px;"></list:col-column>
					<list:col-column property="fdOffOverApplyTime" sort="fdOffOverApplyTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOffOverTime.br') }" headerStyle="min-width: 100px;"></list:col-column>
					<list:col-column property="fdHolidayOverApplyTime" sort="fdHolidayOverApplyTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdHolidayOverTime.br') }" headerStyle="min-width: 100px;"></list:col-column>
					<list:col-column property="fdOverApplyTime" sort="fdOverApplyTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOverTime.br') }" headerStyle="min-width: 100px;"></list:col-column>

					

					
					<c:forEach items="${statKeyList }" var="item">
						<list:col-auto props="${item }"></list:col-auto>
					</c:forEach>
				</list:colTable>
				</list:listview> 
				 
			 	<list:paging></list:paging>
			 </div>
			</ui:content>
		</ui:tabpanel>
		 
	 	<script type="text/javascript">	
	 		var monthFormVal = $KMSSValidation(document.forms['monthForm']);
	 		
	 		seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/data/source','sys/attend/resource/js/dateUtil'], function($, dialog , topic, Source, dateUtil){
				LUI.ready(function(){
					window.top.initAttendReport("2");
				});
				window.listReport = function(){
					if(!validateForm())
						return;
					var doSearch = function(dateType){
						if(dateType == '1'){
							var __url = '/sys/attend/sys_attend_report/sysAttendReport.do?method=listReport&r='+Math.random();
							var listview = LUI('monthListview'),
								source = new Source.AjaxJson({
									url : __url,
									params:$(document.forms['monthForm']).serialize(),
									commitType:'POST'
								});
							listview.table.redrawBySource(source);
							// listview.table.source.get();
							$('.lui-attend-stat-table').show();
						} else if(dateType == '2'){
							var statLoad = dialog.loading();
							$.ajax({
								url : '${LUI_ContextPath}/sys/attend/sys_attend_report/sysAttendReport.do?method=statPeriod',
								type: 'POST',
								data:$(document['monthForm']).serialize(),
								success : function(data){
									statLoad.hide();
									if(data && data.result) {
										var __url = '/sys/attend/sys_attend_report/sysAttendReport.do?method=listPeriod&r='+Math.random();
										var listview = LUI('monthListview'),
											source = new Source.AjaxJson({
												url : __url,
												params:$(document.forms['monthForm']).serialize(),
												commitType:'POST'
											});
										listview.table.redrawBySource(source);
										// listview.table.source.get();
										$('.lui-attend-stat-table').show();
									}
								},
								error : function(e) {
									console.error(e);
								}
							});
						}
					};
					var dateType = $('[name="fdDateType"]').val();
					var reportyType = dateType == 1 ? "listReport" : "statPeriod";
					$.ajax({
						url : '${LUI_ContextPath}/sys/attend/sys_attend_report/sysAttendReport.do?method=checkDuplicatedRequest&reportType='+reportyType,
						type: 'get',
						dataType: 'json',
						success : function(data){
							if(data.status == 'processing'){
								dialog.alert(data.message);
								return;
							}
							doSearch(dateType);
						},
						error : function(e) {
							console.error(e);
						}
					});
				};
				
				window.exportReport = function(){
					if(!validateForm())
						return;
					var dateType = $('[name="fdDateType"]').val();
					console.log(777777777);
					console.log(dateType);
					if(dateType == '1'){
						window.top.exportCommon('${KMSS_Parameter_ContextPath}sys/attend/sys_attend_report/sysAttendReport.do?method=exportReport',$(document.forms['monthForm']).serializeArray(),"2");

						//Com_SubmitNoEnabled(document.forms['monthForm'], 'exportReport');
					} else if(dateType == '2'){
						var statLoad = dialog.loading();
						$.ajax({
							url : '${LUI_ContextPath}/sys/attend/sys_attend_report/sysAttendReport.do?method=statPeriod',
							type: 'POST',
							data:$(document['monthForm']).serialize(),
							success : function(data){
								statLoad.hide();
								if(data && data.result) {
									//按照时间区间查询
									window.top.exportCommon('${KMSS_Parameter_ContextPath}sys/attend/sys_attend_report/sysAttendReport.do?method=exportPeriod',$(document.forms['monthForm']).serializeArray(),"2");

									//Com_SubmitNoEnabled(document.forms['monthForm'], 'exportPeriod');
								}
							},
							error : function(e) {
								console.error(e);
							}
						});
					}
				};
				
				window.generateReport = function(){
					var dateType = $('[name="fdDateType"]').val();
					if(dateType == '2'){
						dialog.alert('暂不支持日期区间生成报表');
						return;
					}
					if(!validateForm())
						return;
				//	window.open("${LUI_ContextPath}/sys/attend/sys_attend_report/sysAttendReport.do?method=add&" + $(document['monthForm']).serialize(), '_blank');
					var formObj = document.monthForm;
					formObj.id='monthForm_'+new Date().getTime();
					Com_SubmitNoEnabled(formObj, 'add'); 
				}
				
				function validateForm() {
					if(window.monthFormVal && window.monthFormVal.validate()) {
						return true;
					} else {
						return false;
					}
				};
				
				window.updateFdMonth = function updateFdMonth(){
					var year = $('[name="fdYearPart"]').val();
					var month = $('[name="fdMonthPart"]').val();
					if(year && month) {
						var date = new Date();
						date.setFullYear(year, parseInt(month) - 1, 1);
						date.setHours(0, 0, 0);
						$('[name="fdMonth"]').val(dateUtil.formatDate(date, Com_Parameter.DateTime_format));
					} else {
						$('[name="fdMonth"]').val('');
					}
				};
				
				window.updateFdName = function updateFdName(){
					var year = $('[name="fdYearPart"]').val();
					var month = $('[name="fdMonthPart"]').val();
					if(year && month){
						$('[name="fdName"]').val(year + "${ lfn:message('sys-attend:sysAttendReport.fdName.fdYearPart') }" 
									+ month + "${ lfn:message('sys-attend:sysAttendReport.fdName.fdMonthPart') }" 
									+ "${ lfn:message('sys-attend:sysAttendReport.report') }");
					} else {
						$('[name="fdName"]').val('');
					}
				};
				
				window.updateFdNameByDate = function() {
					var start = $('#dateType input[name="fdStartTime"]').val();
					var end = $('#dateType input[name="fdEndTime"]').val();
					if(start && end){
						$('[name="fdName"]').val(start + "${ lfn:message('sys-attend:sysAttendReport.fdName.to') }" + end
								+ "${ lfn:message('sys-attend:sysAttendReport.report') }");
						return;
					}
					$('[name="fdName"]').val('');
				};
				
				window.updateFields = function() {
					updateEndTime();
					setTimeout(function(){
						updateFdNameByDate();
					}, 0);
				}
				
				window.updateEndTime = function() {
					var start = $('#dateType input[name="fdStartTime"]').val();
					if(start){
						var date = dateUtil.parseDate(start);
						var end = new Date(date.getFullYear(), date.getMonth() + 1, 0);
						end.setHours(0, 0, 0);
						if(!isBeforeNow(end)) {
							var now = new Date();
							now.setHours(0, 0, 0);
							end = now;
						}
						$('#dateType input[name="fdEndTime"]').val(dateUtil.formatDate(end, Com_Parameter.Date_format));
					}
				};
				
				window.changeColumn = function(){
					var count=0;
					$('input[name="_fdShowCols"]').each(function(){
						if(this.checked){
							count++;
						}
					});
					if($('input[name="_fdShowCols"]').length!=0&&$('input[name="_fdShowCols"]').length==count){
						$('input[name="_fdAllShowCols"]').prop("checked",true);
					}
					else{
						$('input[name="_fdAllShowCols"]').prop("checked",false);
					}
					listReport();
				};
				
				window.selectAll=function(){
					var fdShowCols="";
					var allcols=$('input[name="_fdAllShowCols"]').is(':checked');
					$('input[name="_fdShowCols"]').each(function(){
						if(allcols){
							this.checked=true;
							if(fdShowCols){
								fdShowCols+=";"+this.value;
							}else{
								fdShowCols+=this.value;
							}
						}
						else{
							this.checked=false;
						}
					});
					$('input[name="fdShowCols"]').val(fdShowCols);
					listReport();
				}
				
				window.selectCategory = function(){
					var categoryIds = $('[name="fdCategoryIds"]').val();
					var url="/sys/attend/sys_attend_category/sysAttendCategory_showDialog.jsp?categoryIds=" + categoryIds;
					dialog.iframe(url, "${ lfn:message('sys-attend:sysAttendCategory.select.attend')}", function(arg){
						if(arg){
							$('[name="fdCategoryIds"]').val(arg.cateIds);
							$('[name="fdCategoryNames"]').val(arg.cateNames);
						}
					},{width:800,height:540});
				};
				
				window.changeTargetType = function(value) {
					if(value){
						if(value == '1') {
							$('#targetCate').hide();
							$('#targetCate input').each(function(){
								monthFormVal.removeElements($(this)[0]);
							});
							$('#targetDept').show();
							monthFormVal.addElements($('#targetDept input[name="fdTargetName"]')[0], 'required');
							monthFormVal.validate();
						} else if(value == '2'){
							$('#targetDept').hide();
							$('#targetDept input').each(function(){
								monthFormVal.removeElements($(this)[0]);
							});
							$('#targetCate').show();
							monthFormVal.addElements($('#targetCate input[name="fdCategoryNames"]')[0], 'required');
							monthFormVal.validate();
						}
					}
				};
				
				window.changeDateType = function(value) {
					if(value){
						if(value == '1') {
							$('#dateType').hide();
							$('#dateType input').each(function(){
								monthFormVal.removeElements($(this)[0]);
							});
							$('#monthType').show();
							monthFormVal.addElements($('#monthType [name="fdYearPart"]')[0], 'required');
							monthFormVal.addElements($('#monthType [name="fdMonthPart"]')[0], 'required');
							updateFdName();
						} else if(value == '2'){
							$('#monthType').hide();
							$('#monthType input,select').each(function(){
								monthFormVal.removeElements($(this)[0]);
							});
							$('#dateType').show();
							var startTime = $('#dateType input[name="fdStartTime"]')[0];
							var endTime = $('#dateType input[name="fdEndTime"]')[0];
							monthFormVal.addElements(startTime, "__date");
							monthFormVal.addElements(endTime, "__date");
							monthFormVal.addElements(startTime, 'beforeEnd');
							monthFormVal.addElements(startTime, 'required');
							monthFormVal.addElements(endTime, 'required');
							monthFormVal.addElements(endTime, 'beforeNow');
							monthFormVal.addElements(endTime, 'maxDatePeriod(31)');
							updateFdNameByDate();
						}
					}
				};
				
				monthFormVal.addValidator('beforeEnd',"${ lfn:message('sys-attend:sysAttendReport.validate.beforeEnd') }",function(v,e,o){
					var fdStartTime = $('[name="fdStartTime"]').val(),
						fdEndTime = $('[name="fdEndTime"]').val();
					if(fdStartTime && fdEndTime) {
						return fdStartTime <= fdEndTime;
					} else {
						return true;
					}
				});
				
				monthFormVal.addValidator('beforeNow',"${ lfn:message('sys-attend:sysAttendReport.validate.beforeNow') }",function(v,e,o){
					if(v) {
						var valueObj = dateUtil.parseDate(v);
						if(valueObj) {
							return isBeforeNow(valueObj);
						}
					}
					return true;
				});
				
				monthFormVal.addValidator('maxDatePeriod(num)',"${ lfn:message('sys-attend:sysAttendReport.validate.maxDatePeriod') }",function(v,e,o){
					var num = isNaN(o['num']) ? 31 : parseInt(o['num']);
					var fdStartTime = $('[name="fdStartTime"]').val(),
						fdEndTime = $('[name="fdEndTime"]').val();
					if(fdStartTime && fdEndTime) {
						var start = dateUtil.parseDate(fdStartTime);
						var end = dateUtil.parseDate(fdEndTime);
						if(start && end) {
							return (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24) + 1 <= num;
						}
					}
					return true;
				});
				
				var isBeforeNow = function(dateObj) {
					if(dateObj){
						var now = new Date();
						now.setHours(0,0,0,0);
						if(dateObj.getTime() <= now.getTime()){
							return true;	
						}
					}
					return false;
				};
				
				window.switchAttendPage = function(url,hash){
					url = Com_SetUrlParameter(url,'j_iframe','true');
					url = Com_SetUrlParameter(url,'j_aside','false');
					if(hash){
						url = url + hash;
					}
					LUI.pageOpen(url,'_rIframe');
				}
				
				window.exportExcel=function() {
					if(!validateForm())
						return;
				 	dialog.iframe('/sys/attend/sys_attend_report/sysAttendReport_export.jsp',
							"${lfn:message('sys-attend:sysAttendReport.month.stat.export')}",function(rtn){
							if(rtn){
								console.log(1111111111122);
								console.log(rtn);
								var fdShowCols="";
								if(rtn.fdExportShowCols=="fdExportAllCols"){
									$('input[name="_fdShowCols"]').each(function(){
											if(fdShowCols){
												fdShowCols+=";"+this.value;
											}else{
												fdShowCols+=this.value;
											}
									});

									$('[name="fdExportShowCols"]').val("fdExportSelectCols");
								}else{
									$('input[name="_fdShowCols"]').each(function(){
										console.log(87877);
										if(this.checked){
										if(fdShowCols){
											fdShowCols+=";"+this.value;
										}else{
											fdShowCols+=this.value;
										}
										}
								});
								}

								$('input[name="fdShowCols"]').val(fdShowCols);
// 								$('[name="fdExportShowCols"]').val(rtn.fdExportShowCols);
								$('[name="fdDateFormat"]').val(rtn.fdDateFormat);
								exportReport();
							}
						},{height:'195',width:'425'});
				}
			});
		</script>
	</template:replace>
</template:include>
