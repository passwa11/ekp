<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.hr.ratify.util.HrRatifyUtil" %>
    
<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
if(UserUtil.getUser().getFdParentOrg() != null) {
    pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
} else {
    pageContext.setAttribute("currentOrg", "");
} %>
<c:if test="${hrRatifyPositiveForm.method_GET == 'edit' || (param['i.docTemplate']!=null && param['i.docTemplate']!='')}">
<template:replace name="content">
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="hrRatifyPositiveForm"></c:param>
	</c:import>
	<c:if test="${param.approveModel ne 'right'}">
		<form name="hrRatifyPositiveForm" method="post" action ="${KMSS_Parameter_ContextPath}hr/ratify/hr_ratify_positive/hrRatifyPositive.do">
	</c:if>
	<html:hidden property="fdId" />
   	<html:hidden property="docStatus" />
    <html:hidden property="docTemplateId" />
 	<html:hidden property="method_GET" />
 	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<ui:tabpage collapsed="true" id="reviewTabPage">
				<script>
					LUI.ready(function(){
						setTimeout(function(){
							var reviewTabPage = LUI("reviewTabPage");
							if(reviewTabPage!=null){
								reviewTabPage.element.find(".lui_tabpage_float_collapse").hide();
								reviewTabPage.element.find(".lui_tabpage_float_navs_mark").hide();
							}
						},100)
					})
				</script>
				<c:import url="/hr/ratify/hr_ratify_positive/hrRatifyPositive_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
			</ui:tabpage>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true" var-extend="true" var-average='false' var-useMaxWidth='true'>
				<%--流程--%>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifyPositiveForm" />
					<c:param name="fdKey" value="${fdTempKey }" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
				</c:import>
				<c:import url="/hr/ratify/hr_ratify_positive/hrRatifyPositive_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="baseInfo"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_positive/hrRatifyPositive_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%" >
				<c:import url="/hr/ratify/hr_ratify_positive/hrRatifyPositive_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_positive/hrRatifyPositive_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="baseInfo"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_positive/hrRatifyPositive_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	  			</c:import>
			</ui:tabpage>
			</form>
		</c:otherwise>
	</c:choose>
	
	<script>
		seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'],function($, dialog, topic){
			window.selectStaffPersonInfo = function(){
				var staffId = $("[name='fdPositiveStaffId']").val();
				var staffName = $("[name='fdPositiveStaffName']").val();
				var url="/hr/ratify/import/showStaffPersonInfoDialog.jsp?staffId="+ staffId + "&staffName=" + encodeURI(staffName);
        		dialog.iframe(url,'选择待转正员工',function(arg){
        			if(arg){
						$("input[name='fdPositiveStaffId']").val(arg.staffId);
						$("input[name='fdPositiveStaffName']").val(arg.staffName);
						addDeptPost();
					}
        			var validation=$GetFormValidation(document.hrRatifyPositiveForm);
        			validation.validateElement($("input[name='fdPositiveStaffName']")[0]);
        		},{width:800,height:600});
			};
			window.addDeptPost = function(obj){
				var fdPositiveDept = Address_GetAddressObj('fdPositiveDeptName');
				var fdPositiveLeader = Address_GetAddressObj('fdPositiveLeaderName');
				var fdPositiveEnterDate = $("input[name='fdPositiveEnterDate']")[0];
				var fdPositivePeriodDate = $("input[name='fdPositivePeriodDate']")[0];
				var fdPositiveTrialPeriod = $("input[name='fdPositiveTrialPeriod']")[0];
				var fdPositiveStaffId = $('input[name="fdPositiveStaffId"]').val();
				if(fdPositiveStaffId){
					$.ajax({
						url : '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getPersonInfo',
						type: 'POST',
						dataType: 'json',
						data : {
							id : fdPositiveStaffId
						},
						success : function(data, textStatus, xhr){
							var d = eval(data);
							if(fdPositiveDept && d.dept){
								var values = [{id : d.dept.id, name : d.dept.name}];
								fdPositiveDept.reset(";",ORG_TYPE_ORGORDEPT,false,values);
							}
							if(fdPositiveLeader && d.fdReportLeaderId){
								var values = [{id : d.fdReportLeaderId, name : d.fdReportLeaderName}];
								fdPositiveLeader.reset(";",ORG_TYPE_PERSON,false,values);
							}
							if(fdPositiveEnterDate && d.entryTime)
								fdPositiveEnterDate.value = d.entryTime;
							if(fdPositivePeriodDate && d.trialTime)
								fdPositivePeriodDate.value = d.trialTime;
							if(fdPositiveTrialPeriod && d.trialPeriod)
								fdPositiveTrialPeriod.value = d.trialPeriod;
						}
					});
				}else{
					if(fdPositiveEnterDate)
						fdPositiveDept.emptyAddress();
					if(fdPositiveEnterDate)
						fdPositiveEnterDate.value = '';
					if(fdPositivePeriodDate)
						fdPositivePeriodDate.value = '';
					if(fdPositiveTrialPeriod)
						fdPositiveTrialPeriod.value = '';
				}
			};
		});

		Com_AddEventListener(window,'load',function(){
			window.addDeptPost();
		})
	</script>
</template:replace>
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
				<%--流程--%>
				<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="hrRatifyPositiveForm" />
                    <c:param name="fdKey" value="${fdTempKey }" />
                    <c:param name="isExpand" value="true" />
                    <c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
                </c:import>
                 <!-- 关联机制(与原有机制有差异) -->
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifyPositiveForm" />
					<c:param name="approveType" value="right" />
					<c:param name="needTitle" value="true" />
				</c:import>
			</ui:tabpanel>
		</template:replace>
	</c:when>
	<c:otherwise>
		<template:replace name="nav">
			<%--关联机制(与原有机制有差异)--%>
			<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="hrRatifyPositiveForm" />
			</c:import>
		</template:replace>
	</c:otherwise>
</c:choose>
</c:if>