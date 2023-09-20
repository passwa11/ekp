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
    
        
<c:if test="${hrRatifySignForm.method_GET == 'edit' || (param['i.docTemplate']!=null && param['i.docTemplate']!='')}">
<template:replace name="content">
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="hrRatifySignForm"></c:param>
	</c:import>
	<c:if test="${param.approveModel ne 'right'}">
	<form name="hrRatifySignForm" method="post" action ="${KMSS_Parameter_ContextPath}hr/ratify/hr_ratify_sign/hrRatifySign.do">
		
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
				<c:import url="/hr/ratify/hr_ratify_sign/hrRatifySign_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
			</ui:tabpage>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true" var-extend="true" var-average='false' var-useMaxWidth='true'>
				<%--流程--%>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifySignForm" />
					<c:param name="fdKey" value="${fdTempKey }" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
				</c:import>
				<c:import url="/hr/ratify/hr_ratify_sign/hrRatifySign_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="baseInfo"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_sign/hrRatifySign_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%" >
				<c:import url="/hr/ratify/hr_ratify_sign/hrRatifySign_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_sign/hrRatifySign_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="baseInfo"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_sign/hrRatifySign_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	  			</c:import>
			</ui:tabpage>
			</form>
		</c:otherwise>
	</c:choose>
	
	<script>
	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'],function($, dialog, topic){
		window.addDeptPost = function(obj){
			var fdSignDept = Address_GetAddressObj('fdSignDeptName');
			var fdSignStaffId = $('input[name="fdSignStaffId"]').val();
			if(fdSignStaffId){
				$.ajax({
					url : '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getPersonInfo',
					type: 'POST',
					dataType: 'json',
					data : {
						id : fdSignStaffId
					},
					success : function(data, textStatus, xhr){
						var d = eval(data);
						if(fdSignDept && d.dept){
							var values = [{id : d.dept.id, name : d.dept.name}];
							fdSignDept.reset(";",ORG_TYPE_ORGORDEPT,false,values);
						}
					}
				});
			} else {
				if(fdSignDept)
					fdSignDept.emptyAddress();
			}
		};
		
		 <c:if test="${hrRatifySignForm.sysWfBusinessForm.fdNodeAdditionalInfo.yqqSign =='true' && yqqFlag=='true' && hrRatifySignForm.fdSignEnable=='true'}">
		 Com_Parameter.event["submit"].push(function(){
			//操作类型为通过类型 ，才判断是否已经签署
			if(lbpm.globals.getCurrentOperation().operation && lbpm.globals.getCurrentOperation().operation['isPassType'] == true){
				var flag = true;
				 var url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_sign/hrRatifyOutSign.do?method=queryFinish&signId=${param.fdId}";
				 $.ajax({
						url : url,
						type : 'post',
						data : {},
						dataType : 'text',
						async : false,     
						error : function(){
							dialog.alert('请求出错');
						} ,   
						success:function(data){
							if(data == "true"){
								flag = true;
							}else{
								dialog.alert("当前签署任务未完成，无法提交！！");
								flag = false;
							}
						}
					});
				 return flag;
			}else{
				return true;
			}
			 
		 });
	 </c:if>
		//校验合同是否重复
		Com_Parameter.event["submit"].push(function(){
			var flag = true;
			var fdSignStaffId = $('input[name="fdSignStaffId"]').val();
			var fdSignName = $('[name="fdSignName"]').val();
			var fdSignEndDate = $('[name="fdSignEndDate"]').val();
			var fdSignBeginDate = $('[name="fdSignBeginDate"]').val();
			var fdIsLongtermContract = $('[name="fdIsLongtermContract"]').val();
			var fdSignStaffContTypeId = $('select[name="fdSignStaffContTypeId"] option:selected').val();
			$.ajax({
				url : "${LUI_ContextPath}/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=getRepeatContract",
				type : 'POST',
				data: {
					"personInfoId":fdSignStaffId,
					"fdContType":fdSignStaffContTypeId,
					"fdName":fdSignName,
					"fdBeginDate":fdSignBeginDate,
					"fdEndDate":fdSignEndDate,
					"fdIsLongtermContract":fdIsLongtermContract
				},
				dataType : 'json',
				async : false,
				success: function(data) {
					if(data.result){
						flag = true;
					}else{
						dialog.alert("${ lfn:message('hr-staff:hrStaff.import.error.contract.repeat') }");
						flag = false;
					}
				}
			});
			return flag;
		});
	});
	</script>
</template:replace>
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
				<%--流程--%>
				<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="hrRatifySignForm" />
                    <c:param name="fdKey" value="${fdTempKey }" />
                    <c:param name="isExpand" value="true" />
                    <c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
                </c:import>
                 <!-- 关联机制(与原有机制有差异) -->
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifySignForm" />
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
				<c:param name="formName" value="hrRatifySignForm" />
			</c:import>
		</template:replace>
	</c:otherwise>
</c:choose>
</c:if>