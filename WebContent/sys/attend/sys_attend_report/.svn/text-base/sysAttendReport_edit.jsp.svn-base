<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<%@ page import="java.util.Calendar,java.util.List,net.sf.json.JSONObject" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService,com.landray.kmss.sys.time.model.SysTimeLeaveRule,com.landray.kmss.sys.attend.service.ISysAttendStatService,com.landray.kmss.util.StringUtil" %>
<%
	// 获取请假细分信息
	ISysTimeLeaveRuleService sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil.getBean("sysTimeLeaveRuleService");
	List<SysTimeLeaveRule> list = sysTimeLeaveRuleService.findList("sysTimeLeaveRule.fdIsAvailable=1", "");
	if(list != null && !list.isEmpty()){
		request.setAttribute("leaveRuleList",  list);
	}
	StringBuffer sb = new StringBuffer();
	for(SysTimeLeaveRule rule:list){
		sb.append(rule.getFdName()+";");
	}
	request.setAttribute("fdoffTypeNames",  sb.toString());
	// 权限
	ISysAttendStatService sysAttendStatService = (ISysAttendStatService) SpringBeanUtil
			.getBean("sysAttendStatService");
	request.setAttribute("isStatDeptLeader",
			sysAttendStatService.isStatDeptLeader());
	request.setAttribute("isStatCateReader",
			sysAttendStatService.isStatCateReader());
%>
<template:include ref="default.edit">
	<template:replace name="title">
		<c:choose>
			<c:when test="${sysAttendReportForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-attend:module.sys.attend') }"></c:out>	
			</c:when>
			<c:otherwise>
				<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ sysAttendReportForm.method_GET == 'edit' }">
					<kmss:auth requestURL="/sys/attend/sys_attend_report/sysAttendReport.do?method=update">
						<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysAttendReportForm, 'update');"></ui:button>
					</kmss:auth>
				</c:when>
				<c:when test="${ sysAttendReportForm.method_GET == 'add' }">
					<kmss:auth requestURL="/sys/attend/sys_attend_report/sysAttendReport.do?method=saveAndView">
						<ui:button text="${ lfn:message('sys-attend:sysAttendReport.button.saveAndView') }" 
							onclick="Com_Submit(document.sysAttendReportForm, 'saveAndView');"></ui:button>
					</kmss:auth>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
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
		<style type="text/css">
			td.orgTd {position: relative;}
			td.orgTd label{position: absolute;top: 10px;margin-left: 5px;}
		</style>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/attend/sys_attend_report/sysAttendReport.do">
			<html:hidden property="docCreateTime" />
			<html:hidden property="docCreatorId" />
			<html:hidden property="fdId" />
			<html:hidden property="method_GET" />
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
								<bean:message bundle="sys-attend" key="sysAttendReport.fdName"/>
							</td>
							<td width="85%">
								<xform:text property="fdName" style="width:85%" required="true" 
									subject="${ lfn:message('sys-attend:sysAttendReport.fdName') }" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title">
								<bean:message bundle="sys-attend" key="sysAttendReport.fdMonth"/>
							</td>
							<td>
								<xform:select property="fdYearPart" onValueChange="updateFdMonth();updateFdName();" validators="required">
									<%
										Calendar cal = Calendar.getInstance();
										int startYear = cal.get(Calendar.YEAR) - 10;
										int endYear = cal.get(Calendar.YEAR) + 10;
									%>
									<c:forEach begin="<%=startYear %>" end="<%=endYear %>" var="year">
										<xform:simpleDataSource value="${year }"></xform:simpleDataSource>
									</c:forEach>
								</xform:select>
								<xform:select property="fdMonthPart" onValueChange="updateFdMonth();updateFdName();" validators="required">
									<c:forEach begin="1" end="12" var="month">
										<xform:simpleDataSource value="${month }"></xform:simpleDataSource>
									</c:forEach>
								</xform:select>
								<html:hidden property="fdMonth" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title">
								<bean:message bundle="sys-attend" key="sysAttendReport.authReaders"/>
							</td>
							<td>
								<xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" style="width:85%"
									subject="${ lfn:message('sys-attend:sysAttendReport.authReaders') }" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title">
								<bean:message bundle="sys-attend" key="sysAttendCategory.range"/>
							</td>
							<td class="orgTd">
								<c:set var="showTargetType" value="${empty sysAttendReportForm.fdTargetType ? (isStatDeptLeader ? 1 : (isStatCateReader ? 2 : 1)) : sysAttendReportForm.fdTargetType}"></c:set>
								<c:if test="${isStatDeptLeader || isStatCateReader}">
								<div style="width: 150px;float:left;">
									<xform:select property="fdTargetType" style="width: 90%;height: 28px;" showPleaseSelect="false" showStatus="edit" onValueChange="changeTargetType" value="${showTargetType}">
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
								<div id='targetDept' style="width: 60%;float:left;${showTargetType != 1 ? 'display:none;' : ''}">
									<xform:address propertyId="fdDeptIds" propertyName="fdDeptNames" mulSelect="true"									
									 style="width:95%" validators="${showTargetType == 1 ? 'required' : ''}"
										subject="${ lfn:message('sys-attend:sysAttendReport.fdDepts') }"
												   orgType="ORG_FLAG_AVAILABLEALL"
									/>
									<span class="txtstrong">*</span>
								</div>
								</c:if>
								<%-- 考勤组 --%>
								<c:if test="${isStatCateReader}">
								<div id='targetCate' style="width: 60%;float:left;${showTargetType != 2 ? 'display:none;' : ''}">
									<xform:dialog propertyId="fdCategoryIds" propertyName="fdCategoryNames" showStatus="edit" 
										style="width:95%;height: 26px;" validators="${showTargetType == 2 ? 'required' : ''}" subject="${ lfn:message('sys-attend:sysAttendCategory.attend') }">
								  	 	selectCategory();
									</xform:dialog>
									<span class="txtstrong">*</span>
								</div>
								</c:if>
								<xform:checkbox property="fdIsQuit" showStatus="edit">
									<xform:simpleDataSource value="true">${ lfn:message('sys-attend:sysAttendReport.fdIsQuit') }</xform:simpleDataSource>
								</xform:checkbox>
							</td>
						</tr>
					</table>
				</div>
				<div class="lui-attend-report-btn-group">
					<ui:button text="${ lfn:message('sys-attend:sysAttendReport.preview') }" onclick="listReport();"></ui:button>
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
					<list:colTable isDefault="true"
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
			var reportValidation = $KMSSValidation(document.forms['sysAttendReportForm']);
			seajs.use(['lui/jquery','lui/data/source','lui/topic','sys/attend/resource/js/dateUtil','lui/dialog'],function($,Source,topic,dateUtil,dialog){
				window.updateFdMonth = function updateFdMonth(){
					var year = $('[name="fdYearPart"]').val();
					var month = $('[name="fdMonthPart"]').val();
					if(year && month) {
						var date = new Date();
						date.setFullYear(year, parseInt(month) - 1, 1);
						date.setHours(0, 0, 0);
						$('[name="fdMonth"]').val(dateUtil.formatDate(date, Data_GetResourceString('date.format.datetime')));
					} else {
						$('[name="fdMonth"]').val('');
					}
				};
				
				window.updateFdName = function updateFdName(){
					var year = $('[name="fdYearPart"]').val();
					var month = $('[name="fdMonthPart"]').val();
					if(year && month){
						$('[name="fdName"]').val(year + "${ lfn:message('sys-attend:sysAttendReport.fdName.fdYearPart') }" 
									+ month + "${ lfn:message('sys-attend:sysAttendReport.fdName.fdMonthPartNew') }" 
									+ "${ lfn:message('sys-attend:sysAttendReport.report') }");
					} else {
						$('[name="fdName"]').val('');
					}
				};
				
				window.slideToggle = function slideToggle(srcObj, targetObj){
					$(targetObj).slideToggle();
					$(srcObj).find(".lui-attend-report-line-icon").toggleClass('slideUp');
				};
				window.listReport = function listReport(){
					if(!(window.reportValidation && window.reportValidation.validate())) {
						return;
					}
					var doSearch = function(){						
						var params =serializeJson($(document['sysAttendReportForm']));
						var __url = '/sys/attend/sys_attend_report/sysAttendReport.do?method=listReport&r='+Math.random();
						var listview = LUI('reportListview'),
							source = new Source.AjaxJson({
								url : __url,
								commitType:'POST',
								params:params
							});
						listview.table.redrawBySource(source);
						//listview.table.source.get();
					};
					$.ajax({
						url : '${LUI_ContextPath}/sys/attend/sys_attend_report/sysAttendReport.do?method=checkDuplicatedRequest&reportType=listReport',
						type: 'get',
						dataType: 'json',
						success : function(data){
							if(data.status == 'processing'){
								dialog.alert(data.message);
								return;
							}
							doSearch();
						},
						error : function(e) {
							console.error(e);
						}
					});
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
								reportValidation.removeElements($(this)[0]);
							});
							$('#targetDept').show();
							reportValidation.addElements($('#targetDept input[name="fdTargetName"]')[0], 'required');
							reportValidation.validate();
						} else if(value == '2'){
							$('#targetDept').hide();
							$('#targetDept input').each(function(){
								reportValidation.removeElements($(this)[0]);
							});
							$('#targetCate').show();
							reportValidation.addElements($('#targetCate input[name="fdCategoryNames"]')[0], 'required');
							reportValidation.validate();
						}
					}
				};
				
			});
			var serializeJson=function($formDom){
		        var serializeObj={};
		        var array=$formDom.serializeArray();
		        var str=$formDom.serialize();
		        $(array).each(function(){
		          if(serializeObj[this.name]){
		            if($.isArray(serializeObj[this.name])){
		              serializeObj[this.name].push(this.value);
		            }else{
		              serializeObj[this.name]=[serializeObj[this.name],this.value];
		            }
		          }else{
		            serializeObj[this.name]=this.value;
		          }
		        });
		        return serializeObj;
		      };
		</script>
	</template:replace>
</template:include>