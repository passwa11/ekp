<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.elec.core.certification.util.ElecCoreCertUtil"%>
<%
	boolean isHasCert = ElecCoreCertUtil.isHasCert(UserUtil.getKMSSUser().getUserId(), "com.landray.kmss.sys.organization.model.SysOrgPerson");
	request.setAttribute("isHasCert", isHasCert);
%>
<template:include ref="default.simple">
	<template:replace name="head">
	<template:super />
	<script>Com_IncludeFile('jquery.js');</script>
	<script type="text/javascript">
	
	function buildSubmitData(){
		var data = {};
		data.fdCertId = $("input[name='List_Selected']:checked").val();
		data.pin = document.getElementsByName("_pin")[0].value;
		return data;
	}

	function validateResult( msg){
		//debugger;
		seajs.use(['lui/jquery','lui/dialog'], function($,dialog) {
			dialog.failure(msg);
		});
	}

	function dataValidate(){
		var fdCertId = $("input[name='List_Selected']:checked").val();
		var pin = document.getElementsByName("_pin")[0].value;
		if(!fdCertId || fdCertId==''){
			$("#errMsg").text("请选择证书");
			return false;
		}
		if(!pin || pin==''){
			$("#errMsg").text("请输入pin码");
			return false;
		}
		return true;
	}
	
	</script>
		
	</template:replace>
	<template:replace name="body">
			<br/><br/>
			<c:if test="${isHasCert}">
			 	请输入pin码: <xform:text property="_pin" showStatus="edit" htmlElementProperties="type='password'"></xform:text>
			</c:if>
			<div ><font color="red" size="3pt" id="errMsg"></font></div>
			<br/><br/>
			请选择一个证书进行验证：
			<c:import url="/elec/core/ca/import_cert_view.jsp" charEncoding="UTF-8">
			    <c:param name="fdModelId" value="${KMSS_Parameter_CurrentUserId }"/>
			    <c:param name="formName" value="SysOrgPersonForm"/>
				<c:param name="usage" value="valid"/>
			    <c:param name="fdModelName" value="com.landray.kmss.sys.organization.model.SysOrgPerson"/>
			 </c:import>
	</template:replace>
</template:include>
