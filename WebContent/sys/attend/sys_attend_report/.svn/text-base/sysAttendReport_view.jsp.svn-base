<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<%@ page import="java.util.Calendar,java.util.List,net.sf.json.JSONObject" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService,com.landray.kmss.sys.time.model.SysTimeLeaveRule,com.landray.kmss.util.StringUtil" %>
<%
	// 获取请假细分信息
	ISysTimeLeaveRuleService sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil.getBean("sysTimeLeaveRuleService");
	List list = sysTimeLeaveRuleService.findList("sysTimeLeaveRule.fdIsAvailable=true", "");
	if(list != null && !list.isEmpty()){
		request.setAttribute("leaveRuleList",  list);
	}
%>
<template:include ref="default.view">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<script>
		function deleteDoc(delUrl){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
					if(isOk){
						Com_OpenWindow(delUrl,'_self');
					}	
				});
			});
		}
		</script>
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<kmss:auth requestURL="/sys/attend/sys_attend_report/sysAttendReport.do?method=edit&fdId=${param.fdId}">
				<ui:button text="${lfn:message('button.edit')}" 
							onclick="Com_OpenWindow('sysAttendReport.do?method=edit&fdId=${param.fdId}','_self');" order="2">
				</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/sys/attend/sys_attend_report/sysAttendReport.do?method=delete&fdId=${param.fdId}">
				<ui:button text="${lfn:message('button.delete')}" order="4"
							onclick="deleteDoc('sysAttendReport.do?method=delete&fdId=${param.fdId}');">
				</ui:button>
			</kmss:auth>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>	
			<ui:menu-item text="${ lfn:message('sys-attend:module.sys.attend') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-attend:table.sysAttendReport') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/attend.css" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/export.css">
		<script type="text/javascript" src="${LUI_ContextPath}/sys/attend/resource/js/reportDrawer.js"></script>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/attend/sys_attend_report/sysAttendReport.do">
			<html:hidden property="fdId" />
			<html:hidden property="fdMonth" />
			<html:hidden property="fdName" />
			<html:hidden property="docCreatorId" />
			<html:hidden property="docCreatorName" />
			<html:hidden property="docCreateTime" />
			<html:hidden property="fdDeptIds" />
			<html:hidden property="fdDeptNames" />
			<html:hidden property="authReaderIds" />
			<html:hidden property="authReaderNames" />
			<html:hidden property="fdCategoryIds" />
			<html:hidden property="fdCategoryNames" />
			<html:hidden property="fdTargetType" />
			
			<c:set var="fdoffTypeNames" scope="page">
 				<c:forEach items="${leaveRuleList }" var="item">
 					${item.fdName };
 				</c:forEach>
 			</c:set>
 			<html:hidden property="fdShowCols" value="fdStaffNo;docCreator.fdName;fdDept;fdEntryTime;fdCategoryName;fdMonth;fdShouldDays;fdHolidays;fdWorkDateDays;fdActualDays;fdStatusDays;fdAbsentDays;fdTripDays;fdOffDays;fdLateCount;fdLateExcCount;fdLateTime;fdLeftCount;fdLeftExcCount;fdLeftTime;fdOutsideCount;fdMissedCount;fdMissedExcCount;fdTotalTime;fdOverTime;fdWorkOverTime;fdOffOverTime;fdHolidayOverTime;fdOutgoingTime;${fdoffTypeNames }" />
			
			<p class="lui_form_subject">
				${ lfn:message('sys-attend:sysAttendReport.report') }
			</p>
			<div class="lui_form_content_frame" style="padding-top:20px">
				<div class="lui-attend-report-container">
					<div class="lui-attend-report-section">
						<div class="lui-attend-report-section-line"></div>
						<div class="lui-attend-report-section-title"  onclick="slideToggle(this, '#conditionArea');">
							${ lfn:message('sys-attend:sysAttendReport.criterion') }
							<span class="lui-attend-report-line-icon"></span>
						</div>
					</div>
				</div>
				<div class="lui-attend-report-container" id="conditionArea">
					<table class="tb_simple" width="100%">
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-attend" key="sysAttendReport.fdName"/>：
							</td>
							<td width="85%">
								<xform:text property="fdName" style="width:85%" 
									subject='<bean:message bundle="sys-attend" key="sysAttendReport.fdName"/>'/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title">
								<bean:message bundle="sys-attend" key="sysAttendReport.fdMonth"/>：
							</td>
							<td>
								${sysAttendReportForm.fdYearPart }${ lfn:message('sys-attend:sysAttendReport.fdName.fdYearPart') }${sysAttendReportForm.fdMonthPart }${ lfn:message('sys-attend:sysAttendReport.fdName.fdMonthPart') }
							</td>
						</tr>
						<tr>
							<td class="td_normal_title">
								<bean:message bundle="sys-attend" key="sysAttendReport.authReaders"/>：
							</td>
							<td>
								<xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" style="width:85%" 
									subject='<bean:message bundle="sys-attend" key="sysAttendReport.authReaders"/>'/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title">
								<bean:message bundle="sys-attend" key="sysAttendCategory.range"/>：
							</td>
							<td>
								<c:if test="${empty sysAttendReportForm.fdTargetType || sysAttendReportForm.fdTargetType == '1'}">
									<xform:address propertyId="fdDeptIds" propertyName="fdDeptNames" mulSelect="true" orgType="ORG_TYPE_ALL" style="" />
								</c:if>
								<c:if test="${empty sysAttendReportForm.fdTargetType || sysAttendReportForm.fdTargetType == '2'}">
									<xform:dialog propertyId="fdCategoryIds" propertyName="fdCategoryNames" showStatus="view" style="" >
									</xform:dialog>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title">
								<bean:message bundle="sys-attend" key="sysAttendReport.fdIsQuit.title"/>
							</td>
							<td>
								<xform:checkbox property="fdIsQuit" showStatus="readOnly">
									<xform:simpleDataSource value="true">${ lfn:message('sys-attend:sysAttendReport.fdIsQuit') }</xform:simpleDataSource>
								</xform:checkbox>
							</td>
						</tr>
					</table>
				</div>
				<div class="lui-attend-report-btn-group">
					<ui:button text="${ lfn:message('sys-attend:sysAttendReport.exportExcel') }" onclick="exportExcel();"></ui:button>
					<ui:button text="${ lfn:message('sys-attend:sysAttendReportLog.list') }" order="3"  style="margin-right: 10px;"
							   onclick="window.top.exportLog('${param.fdId}');">
					</ui:button>

				</div>
				<div class="lui-attend-report-container">
					<div class="lui-attend-report-section">
						<div class="lui-attend-report-section-line"></div>
						<div class="lui-attend-report-section-title"  onclick="slideToggle(this, '#resultArea');">
							${ lfn:message('sys-attend:sysAttendReport.resultshow') }
							<span class="lui-attend-report-line-icon"></span>
						</div>
					</div>
				</div>
				<div class="lui-attend-report-container" id="resultArea">
				 	<list:listview id="reportListview">
						<ui:source type="AjaxJson">
								{url:""}
						</ui:source>
					<list:colTable isDefault="false"
						rowHref="">
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdStaffNo;docCreator.fdName;fdDept;fdEntryTime;fdCategoryName;fdMonth;fdShouldDays;fdHolidays;fdWorkDateDays;fdActualDays;fdTotalTime;fdStatusDays;fdAbsentDays;fdTripDays;fdOutgoingTime;fdLateCount;fdLateTime;fdLateExcCount;fdLeftCount;fdLeftTime;fdLeftExcCount;fdOutsideCount;fdMissedCount;fdMissedExcCount;fdWorkOverTime;fdOffOverTime;fdHolidayOverTime;fdOverTime;"></list:col-auto>
						<c:forEach items="${leaveRuleList }" var="item">
							<list:col-column property="${item.fdName }" title="${fn:escapeXml(item.fdName)}"></list:col-column>
						</c:forEach>
						<list:col-column headerStyle="min-width:120px;" property="fdOffDays"></list:col-column>
					</list:colTable>
					</list:listview> 
				 	<list:paging></list:paging>
				</div>
			</div>
		</html:form>
		<script>
			seajs.use(['lui/jquery','lui/data/source'],function($,Source){
				LUI.ready(function(){
					var __url = '/sys/attend/sys_attend_report/sysAttendReport.do?method=listReportMonth&' + $(document['sysAttendReportForm']).serialize();
					var listview = LUI('reportListview'),
						source = new Source.AjaxJson({
							url : __url,
						});
					 listview.table.redrawBySource(source);
					 // listview.table.source.get();
					$(".lui_form_content").css("max-width","${fdPageMaxWidth}").css("padding","0px");

					window.top.initAttendReport('${param.fdId}');
				});

				window.slideToggle = function(srcObj, targetObj){
					$(targetObj).slideToggle();
					$(srcObj).find(".lui-attend-report-line-icon").toggleClass('slideUp');
				};
				
				window.exportExcel = function(){
					let url ='${LUI_ContextPath}/sys/attend/sys_attend_report/sysAttendReport.do?method=exportMonthReport';
					window.top.exportCommon(url,$(document.forms['sysAttendReportForm']).serializeArray(),'${param.fdId}');

					//Com_SubmitNoEnabled(document.forms['sysAttendReportForm'], 'exportMonthReport');
				};
				
			});
		</script>
	</template:replace>
</template:include>