<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
<template:replace name="head">
<%@ include file="/sys/lbpmservice/include/sysLbpmProcessConstant.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("data.js|dialog.js|jquery.js", null, "js");
</script>
<script>
	//获取list页面选择的流程记录的id
	function initDocIds() {
		var values = [];
		var contentId = "${JsParam.contentId}";
		if(contentId){
			$("#"+contentId+" input[name='List_Selected']:checked",parent.document).each(function() {
				values.push($(this).val());
			});
		}else{
			$("input[name='List_Selected']:checked",parent.document).each(function() {
				values.push($(this).val());
			});
		}
		if(values.length <= 0 && top){//为了兼容某些情况下需要遮罩全覆盖问题
			values = top.selectDocIds || [];
		}
		$("input[name='docIds']").val(values);
	}
	var checkSubmitFlag = false; //防止重复提交

	function checkSubmit(){
		if(checkSubmitFlag==true){
			return true; 
		} 
		checkSubmitFlag=true; 
		return false;
	}
	 		
	function doSubmit(formObj){
		var targetElementVal=$("textarea[name='targetOrgElemName']").val();
		if(!targetElementVal){
			alert('<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.updatePrivileger.privileger.required" />');
			checkSubmitFlag=false;
			return false;
		}
		formObj.submit();
	}

	function oprOnclickFunc(el){
		var opTypeValue = el.value;
		var tt = '<bean:message key="sysLbpmMonitor.updatePrivileger.targetPrivileger" bundle="sys-lbpmmonitor"/>';
		var td='<bean:message key="sysLbpmMonitor.updatePrivileger.srcPrivileger" bundle="sys-lbpmmonitor"/>';
		if(opTypeValue=="1" || opTypeValue=="3"){
			document.getElementById("targetOrgElemTdId").innerHTML=tt;
		}else{
			document.getElementById("targetOrgElemTdId").innerHTML=td;
		}
	}
	
	$(function() {
		initDocIds();
	});
</script>
</template:replace>
<template:replace name="content">
<form
	action="${pageContext.request.contextPath}/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do"
	method="post">
	<p>&nbsp;</p>
	<div class="lui_form_content_frame">
		<table class="tb_normal" width=95%>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message key="sysLbpmMonitor.updatePrivileger.oprType.title" bundle="sys-lbpmmonitor"/>
				</td>
				<td width=85%>
					<label><input type="radio" value="1" name="oprType" checked onclick='oprOnclickFunc(this);' ><bean:message key="sysLbpmMonitor.updatePrivileger.oprType.1" bundle="sys-lbpmmonitor"/> </label>
					<label><input type="radio" value="2" name="oprType" onclick='oprOnclickFunc(this);' ><bean:message key="sysLbpmMonitor.updatePrivileger.oprType.2" bundle="sys-lbpmmonitor"/> </label>
					<label><input type="radio" value="3" name="oprType" onclick='oprOnclickFunc(this);' ><bean:message key="sysLbpmMonitor.updatePrivileger.oprType.3" bundle="sys-lbpmmonitor"/> </label>
				</td>
			</tr>		
		
			<tr>
				<td class="td_normal_title"  width=15% id="targetOrgElemTdId">
					<bean:message key="sysLbpmMonitor.updatePrivileger.targetPrivileger" bundle="sys-lbpmmonitor"/>
				</td>
				<td width=85%>			
					<input type="hidden" name="targetOrgElemId">
					<textarea name="targetOrgElemName" readonly="readonly" style="width:90%" class="inputmul"></textarea>
					<a href="#" onclick="Dialog_Address(true, 'targetOrgElemId','targetOrgElemName', ';', ORG_TYPE_POSTORPERSON, null, null, null, true);">
						<bean:message key="dialog.selectOrg"/>
					</a>
				</td>
			</tr>
		</table>
	</div>
	<center>
		<div data-lui-mark="dialog.content.buttons" style="position: fixed;bottom:0px;left: 15px;width:95%;background: #fff;padding-bottom:20px;">
			<ui:button style="width:80px" onclick="if(checkSubmit()){return;};doSubmit(document.forms[0]);" text="${lfn:message('button.update') }" />
		</div>
	</center>

	<input type="hidden" name="docIds" value="" /> 
	<input type="hidden" name="method" value="updatePrivileger" />
</form>
</template:replace>
</template:include>