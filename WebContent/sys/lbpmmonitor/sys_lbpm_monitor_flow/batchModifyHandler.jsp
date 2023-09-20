<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
<template:replace name="head">
<%@ include file="/sys/lbpmservice/include/sysLbpmProcessConstant.jsp"%>
<style type="text/css">
	ul.errorMsg{color:red;}
	ul.errorMsg li a{text-decoration:underline;color:red;}
</style>
<script type="text/javascript">
	Com_IncludeFile("data.js|dialog.js|jquery.js", null, "js");
</script>
<script>
	var status='<%= ResourceUtil.getString("sysLbpmMonitor.notify.status","sys-lbpmmonitor") %>';
	var urlTemplate = '${pageContext.request.contextPath}/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=view&fdId={fdId}&fdModelName={fdModelName}';
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
		var targetElementVal=$("input[name='targetOrgElemName']").val();
		if(!targetElementVal){
			alert('<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.flow.handlerNotEmpty" />');
			checkSubmitFlag=false;
			return false;
		}
		var html="";
		$.ajax({
			url:"${pageContext.request.contextPath}/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=checkProcess",
			type:"post",
			async:false,
			dataType:"json",
			data:{"docIds":$("input[name='docIds']").val()},
			success:function(data){
				if(data && data.errorCode){
					var errCodes = data.errorCode;
					for(var i=0;i<errCodes.length;i++){
						html+=buildErrHtml(errCodes[i]);
					}
				}
			}
		});
		if(html && html!=""){
			$("#errorMsg").html(html);
			$("#errorTabs").show();
			checkSubmitFlag=false;
			return false;
		}
		formObj.submit();
	}
	function buildErrHtml(obj) {
		var temp = status
				.replace("{0}", getDocUrlHtml(obj.id,obj.fdModelName,obj.docSubject))
				.replace("{1}", obj.status);
		return "<li>" + temp + "</li>";
	}
	
	function getDocUrlHtml(fdId, fdModelName, docSubject) {
		var url = urlTemplate.replace("{fdId}", fdId).replace("{fdModelName}",
				fdModelName);
		return "<a href='"+url+"' target='_blank'>"
				+ docSubject + "</a>";
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
		<table class="tb_normal" width=80%>
			<tr>
				<td class="td_normal_title" width="12%"><bean:message
						bundle="sys-lbpmmonitor" key="sysLbpmMonitor.flow.newHandler" />
				</td>
				<td width="48%">
					<input type="hidden" name="targetOrgElemId">
					<input type="text" name="targetOrgElemName" class="inputSgl" readonly style="width:85%">
					<span class="txtstrong">*</span>
					<a href="javascript:void(0);" 
						onclick="Dialog_Address(true, 'targetOrgElemId','targetOrgElemName', ';', ORG_TYPE_POSTORPERSON, null, null, null, true);">
						<bean:message key="dialog.selectOrg"/></a>
					</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="12%">&nbsp;</td>
				<td  width="48%">
					<input type="checkbox" name="onlyModifyInValidHandler" value="true"/>&nbsp;<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.flow.onlyModifyInvalidHandler" /><br/>
					&nbsp;&nbsp;<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.flow.onlyModifyInvalidHandlerNotify" />
				</td>
			</tr>
		</table>
		<table id="errorTabs" width="60%" style="margin:10px auto;color:red;display:none;">
			<tr>
				<td>
					<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.notify.tips" />：
					<div style="margin-left:20px;"><ul id="errorMsg" class="errorMsg"></ul></div>
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
	<input type="hidden" name="method" value="updateHandler" />
</form>
</template:replace>
</template:include>