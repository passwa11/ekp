<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Date, java.util.Calendar,com.landray.kmss.util.*"%>
<% 
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.MONTH, -1);//月份减一
	String startTime = DateUtil.convertDateToString(calendar.getTime(),DateUtil.TYPE_DATE, request.getLocale());
	String endTime = DateUtil.convertDateToString(new Date(),DateUtil.TYPE_DATE, request.getLocale());
	request.setAttribute("formatter", ResourceUtil.getString("date.format.date"));
%>
<template:include ref="default.dialog">
	<template:replace name="content" >
		<script>
			seajs.use(['lui/dialog', 'lui/jquery'],function(dialog,$) {
				//未勾选的标签
				window.clickCheckbox=function(self){
					checkedSelectAll(self);
				};
				//确认
				window.clickOk=function(){
					var startTime=document.getElementsByName("startTime")[0];
					if(startTime.value==null||startTime.value==""){
						dialog.alert("<kmss:message key='errors.required' argKey0='km-calendar:kmCalendarMain.docStartTime' />");
						return;
					}
					var endTime=document.getElementsByName("endTime")[0];
					if(endTime.value==null||endTime.value==""){
						dialog.alert("<kmss:message key='errors.required' argKey0='km-calendar:kmCalendarMain.docFinishTime' />");
						return;
					}
					var startTimeObj = formatDate(startTime.value,'${formatter}'),
						endTimeObj = formatDate(endTime.value,'${formatter}');
					if(startTimeObj.getTime() >= endTimeObj.getTime()){
						dialog.alert("<kmss:message key='kmCalendarMain.tip.validateDate.errorDate' bundle='km-calendar' />");
						return;
					}
					var labels = $('[name="label_checkbox"]'),
						hasSelect = false;
					var exceptLabelIds = [];
					for(var i=0;i<labels.length;i++){
						if(labels.eq(i).prop('checked')){
							hasSelect=true;
						}else{
							exceptLabelIds.push(labels.eq(i).val());
						}
					}
					if(!hasSelect && '${JsParam.type}' == 'myCalendar'){
						dialog.alert("<kmss:message bundle='km-calendar' key='kmCalendarMain.tip.validatelabel.notNull' />");
						return;
					}
					//var form=document.getElementById("form");
					//form.submit();
					var url='<c:url value="/km/calendar/km_calendar_main/kmCalendarMain.do?method=exportCalendar&type=${JsParam.type}"></c:url>';
					url+="&startTime="+startTime.value+"&endTime="+endTime.value;
					//群组ID(导出群组日程时必需)
					if("${JsParam.groupId}"!=""){
						url+="&groupId=${JsParam.groupId}";
					}
					if("${JsParam.personGroupId}"!=""){
						url+="&personGroupId=${JsParam.personGroupId}";
					}
					//未选中标签ID(导出个人日程时必需)
					if(exceptLabelIds.length>0){
						url+="&exceptLabelIds="+exceptLabelIds;
					}
					
					window.location.href=url;
				};
				// 全选
				window.searchCheckAll = function () {
					var isChecked = $('#_searchSelectAll').is(":checked");
					if (isChecked) {
						$("[name = label_checkbox]:checkbox").each( function() {
							$(this).prop("checked", 'checked');
						});	
					}else{
						$("[name = label_checkbox]:checkbox").each( function() {
							$(this).removeAttr("checked");
						});							
					}	
				};
				//全选联动
				window.checkedSelectAll = function(obj) {
					var isChecked = $(obj).is(":checked");
					var selectAll = $("#_searchSelectAll");
					if (!isChecked) {
						if (selectAll.is(":checked")) {
							selectAll.removeAttr("checked");
						}
					} else {
						var checked = true;
						$("#label_checkbox").find(":checkbox").each( function() {
							if (!$(this).is(":checked")) {
								checked = false;
							}
						});
						if (checked) {
							selectAll.prop("checked", "checked");
						}
					}
				};
				//反选
				window.disSelect = function() {
					$("#label_checkbox").find(":checkbox").each( function() {
						if ($(this).is(":checked")) {
							$(this).removeAttr("checked");
						}else{
							$(this).prop("checked", 'checked');
						}
					});
				};
			});
		</script>
		
		
		
		<html:form styleId="form" action="/km/calendar/km_calendar_main/kmCalendarMain.do?method=exportCalendar&type=${HtmlParam.type}">
		<c:if test="${param.type=='groupCalendar' || param.type=='personGroupCalendar' }">
			<div style="height: 50px;"></div>
		</c:if>
		<c:if test="${param.type=='myCalendar' }">
			<div style="height: 20px;"></div>
		</c:if>
		<div style="margin:0px auto;text-align: center;">
			<div class="txttitle">
				<bean:message bundle="km-calendar" key="kmCalendarMain.setTimeTitle" />
			</div>
			<br/>
			<table id="Table_Main" class="tb_normal"width="95%"align="center">
				<%--导出开始时间--%>
				<tr>
					<td class="td_normal_title">
						<bean:message	bundle="km-calendar" key="kmCalendarMain.setTimeTitle.startTime" />
					</td>
					<td width="70%">
						<xform:datetime property="startTime" style="width:98%;" value="<%=startTime%>" showStatus="edit" dateTimeType="date"></xform:datetime>
					</td>
				</tr>
				<%--导出结束时间--%>
				<tr>
					<td class="td_normal_title">
						<bean:message	bundle="km-calendar" key="kmCalendarMain.setTimeTitle.endTime" />
					</td>
					<td width="70%">
						<xform:datetime property="endTime" style="width:98%;" value="<%=endTime%>" showStatus="edit" dateTimeType="date"></xform:datetime>
					</td>
				</tr>
				<%--所选标签--%>
				<c:if test="${param.type=='myCalendar' }">
					<tr>
						<td class="td_normal_title">
							<bean:message	bundle="km-calendar" key="kmCalendarMain.docLabel" />
							<label style="margin-left:10px;display:inline-block">
								<input type="checkbox" name="_searchSelectAll" id="_searchSelectAll" onclick="searchCheckAll();"/><bean:message key="kmCalendarMain.selectAll" bundle="km-calendar" />
							</label>
							<label style="display:inline-block">
								<input type="checkbox" name="_searchDisSelect" id="_searchDisSelect" onclick="disSelect();"/><bean:message key="kmCalendarMain.disSelect" bundle="km-calendar" />
							</label>
						</td>
						<td width="70%">
							<ui:dataview id="label_checkbox">
								<ui:source type="AjaxJson">
									{url:'/km/calendar/km_calendar_label/kmCalendarLabel.do?method=listJson'}
								</ui:source>
								<ui:render type="Template">
									<c:import url="/km/calendar/tmpl/label_checkbox.jsp" charEncoding="UTF-8"></c:import>
								</ui:render>
							</ui:dataview>
						</td>
					</tr>					
				</c:if>
				<tr>
					<td colspan="2" align="center" >
						<ui:button text="${lfn:message('button.ok') }" onclick="clickOk();" >
						</ui:button>&nbsp;
						<ui:button  text="${lfn:message('button.cancel') }" onclick="window.$dialog.hide(null);" styleClass="lui_toolbar_btn_gray">
						</ui:button>
					</td>
				</tr>
			</table>
			
		</div>	
		</html:form>
	</template:replace>
</template:include>