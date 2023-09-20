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
<c:if test="${hrRatifyTransferForm.method_GET == 'edit' || (param['i.docTemplate']!=null && param['i.docTemplate']!='')}">
<template:replace name="content">
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="hrRatifyTransferForm"></c:param>
	</c:import>
	<c:if test="${param.approveModel ne 'right'}">
		<form name="hrRatifyTransferForm" method="post" action ="${KMSS_Parameter_ContextPath}hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do">
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
				<c:import url="/hr/ratify/hr_ratify_transfer/hrRatifyTransfer_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
			</ui:tabpage>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true" var-extend="true" var-average='false' var-useMaxWidth='true'>
				<%--流程--%>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifyTransferForm" />
					<c:param name="fdKey" value="${fdTempKey }" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
				</c:import>
				<c:import url="/hr/ratify/hr_ratify_transfer/hrRatifyTransfer_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="baseInfo"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_transfer/hrRatifyTransfer_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%" >
				<c:import url="/hr/ratify/hr_ratify_transfer/hrRatifyTransfer_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_transfer/hrRatifyTransfer_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="baseInfo"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_transfer/hrRatifyTransfer_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	  			</c:import>
			</ui:tabpage>
			</form>
		</c:otherwise>
	</c:choose>
	
	<script>
		seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'],function($, dialog, topic){
			window.addDeptPost = function(obj){
				//var fdTransferLeaveDept = Address_GetAddressObj('fdTransferLeaveDeptName');
				//var fdTransferLeavePost = Address_GetAddressObj('fdTransferLeavePostNames');
				var fdTransferLeavePostIds = $("[name='fdTransferLeavePostIds']")[0];
				var fdTransferLeavePostNames = $("[name='fdTransferLeavePostNames']")[0];
				var fdTransferLeaveDeptId = $('[name="fdTransferLeaveDeptId"]')[0];
				var fdTransferLeaveDeptName = $('[name="fdTransferLeaveDeptName"]')[0];
				var fdTransferOldLevelId = $('[name="fdTransferOldLevelId"]')[0];
				var fdTransferOldLevelName = $('[name="fdTransferOldLevelName"]')[0];
				var fdTransferLeaveSalary = $('[name="fdTransferLeaveSalary"]')[0];
				var fdTransferStaffId = $('input[name="fdTransferStaffId"]').val();
				if(fdTransferStaffId){
					$.ajax({
						url : '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getPersonInfo',
						type: 'POST',
						dataType: 'json',
						data : {
							id : fdTransferStaffId,
							type:"leavepost"
						},
						success : function(data, textStatus, xhr){
							var d = eval(data);
							if(d.dept){
								//var values = [{id : d.dept.id, name : d.dept.name}];
								//fdTransferLeaveDept.reset(";",ORG_TYPE_ORGORDEPT,false,values);
								$('input[name="fdTransferLeaveDeptId"]').val(d.dept.id);
								$('input[name="fdTransferLeaveDeptName"]').val(d.dept.name);
							}
							if(d.posts){
								//var values = [{id : d.post.id, name : d.post.name}];
								//fdTransferLeavePost.reset(";",ORG_TYPE_POST,false,values);
								$("#fdTransferLeavePost").html('');
								for(var i=0;i<d.posts.length;i++){
									$("#fdTransferLeavePost").append("<option value='"+d.posts[i].id+"'>"+d.posts[i].name+"</option>");
								}
							}
							if(fdTransferOldLevelId && d.level)
								fdTransferOldLevelId.value = d.level.id;
							if(fdTransferOldLevelName && d.level)
								fdTransferOldLevelName.value = d.level.name;
							if(fdTransferLeaveSalary && d.salary)
								fdTransferLeaveSalary.value = d.salary;
						}
					});
				}else{
					/* if(fdTransferLeaveDept)
						fdTransferLeaveDept.emptyAddress();
					if(fdTransferLeavePost)
						fdTransferLeavePost.emptyAddress(); */
					if(fdTransferLeavePostIds){
						fdTransferLeavePostIds.value='';
						var html = $("#fdTransferLeavePost").find("option:eq(0)").html();
						$(fdTransferLeavePostIds).empty();
						$(fdTransferLeavePostIds).append("<option value=''>"+html+"</option>");
					}
					if(fdTransferLeavePostNames)
						fdTransferLeavePostNames.value='';
					if(fdTransferLeaveDeptId)
						fdTransferLeaveDeptId.value='';
					if(fdTransferLeaveDeptName)
						fdTransferLeaveDeptName.value='';
					if(fdTransferOldLevelId)
						fdTransferOldLevelId.value = '';
					if(fdTransferOldLevelName)
						fdTransferOldLevelName.value = '';
					if(fdTransferLeaveSalary)
						fdTransferLeaveSalary.value = '';
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
                    <c:param name="formName" value="hrRatifyTransferForm" />
                    <c:param name="fdKey" value="${fdTempKey }" />
                    <c:param name="isExpand" value="true" />
                    <c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
                </c:import>
              	<!-- 关联机制(与原有机制有差异) -->
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifyTransferForm" />
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
				<c:param name="formName" value="hrRatifyTransferForm" />
			</c:import>
		</template:replace>
	</c:otherwise>
</c:choose>
</c:if>