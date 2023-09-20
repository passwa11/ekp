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
    
        
<c:if test="${hrRatifySalaryForm.method_GET == 'edit' || (param['i.docTemplate']!=null && param['i.docTemplate']!='')}">
<template:replace name="content">
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="hrRatifySalaryForm"></c:param>
	</c:import>
	<c:if test="${param.approveModel ne 'right'}">
	<form name="hrRatifySalaryForm" method="post" action ="${KMSS_Parameter_ContextPath}hr/ratify/hr_ratify_salary/hrRatifySalary.do">
		
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
				<c:import url="/hr/ratify/hr_ratify_salary/hrRatifySalary_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
			</ui:tabpage>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true" var-extend="true" var-average='false' var-useMaxWidth='true'>
				<%--流程--%>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifySalaryForm" />
					<c:param name="fdKey" value="${fdTempKey }" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
				</c:import>
				<c:import url="/hr/ratify/hr_ratify_salary/hrRatifySalary_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="baseInfo"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_salary/hrRatifySalary_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%" >
				<c:import url="/hr/ratify/hr_ratify_salary/hrRatifySalary_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_salary/hrRatifySalary_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="baseInfo"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_salary/hrRatifySalary_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	  			</c:import>
			</ui:tabpage>
			</form>
		</c:otherwise>
	</c:choose>
	 <script>
                	var _validation = $KMSSValidation(document.forms['hrRatifySalaryForm']);
                	//自动计算工资差额
            		function calculateDiff(type,value){
                		var fdSalaryDiff = document.getElementsByName("fdSalaryDiff")[0];
                		var fdSalaryAfter = document.getElementsByName("fdSalaryAfter")[0];
                		var fdSalaryBefore = document.getElementsByName("fdSalaryBefore")[0];
                		if(type == 'before' && fdSalaryAfter.value != ''){
                			var afterV = fdSalaryAfter.value;
                			fdSalaryDiff.value = Number((afterV - value)).toFixed(getFixed(afterV,value)) ;
                		}
                		if(type == 'after' && fdSalaryBefore.value != ''){
                			var beforeV = fdSalaryBefore.value;
                			fdSalaryDiff.value = Number((value-beforeV)).toFixed(getFixed(value,beforeV));
                		}
                	}
                	
            		function getFixed(beforeV,afterV){
                		var a =beforeV.toString().split(".");
                		var b =afterV.toString().split(".");
                		var len =0;
                		if(a.length ==2 && b.length ==2){
                			len = a.length >= b.length ? a.length : b.length;
                		}
                		if(a.length ==1 && b.length ==2){
                			len =  b.length;
                		}
                		if(b.length ==1 && a.length ==2){
                			len = a.length;
                		}
                		return len;
                	}
                	
                </script>
	<script>
		seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'],function($, dialog, topic){
			window.addDeptPost = function(obj){
				var fdSalaryDept = Address_GetAddressObj('fdSalaryDeptName');
				var fdSalaryStaffId = $('input[name="fdSalaryStaffId"]').val();
				var fdSalaryBefore = $('input[name="fdSalaryBefore"]');
				if(fdSalaryStaffId){
					$.ajax({
						url : '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getPersonInfo',
						type: 'POST',
						dataType: 'json',
						data : {
							id : fdSalaryStaffId
						},
						success : function(data, textStatus, xhr){
							var d = eval(data);
							if(fdSalaryDept && d.dept){
								var values = [{id : d.dept.id, name : d.dept.name}];
								fdSalaryDept.reset(";",ORG_TYPE_ORGORDEPT,false,values);
							}
							if(fdSalaryBefore && d.salary){
								fdSalaryBefore.val(d.salary);
							}
						}
					});
				}else{
					if(fdSalaryDept)
						fdSalaryDept.emptyAddress();
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
                    <c:param name="formName" value="hrRatifySalaryForm" />
                    <c:param name="fdKey" value="${fdTempKey }" />
                    <c:param name="isExpand" value="true" />
                    <c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
                </c:import>
               	 <!-- 关联机制(与原有机制有差异) -->
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifySalaryForm" />
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
				<c:param name="formName" value="hrRatifySalaryForm" />
			</c:import>
		</template:replace>
	</c:otherwise>
</c:choose>
</c:if>