<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit">
	<template:replace name="content">
		<style>
			.lui_form_path_frame{
				display: none !important;
			}
			.com_qrcode {
				display:none!important;
			}
			.tempTB {
				width: 100% !important;
				max-width:100% !important;
			}
		</style>
		<html:hidden property="fdId" />
		<html:hidden property="fdModelId" />
		<html:hidden property="fdModelName" />
		<html:hidden property="docStatus" />

		<%-- 流程 --%>
		<form id="lbpmForm" method="post">
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true" var-expand="${empty requestScope[param.formName].docStatus || requestScope[param.formName].docStatus<30}" var-average='false' var-useMaxWidth='true'>
				<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
					 <c:param name="formName" value="${param.formName}"/>
					 <c:param name="fdKey" value="${param.fdKey}"/>
					 <c:param name="isExpand" value="true"/>
				 </c:import>
			</ui:tabpanel>
		</form>
		<script>
			//表单提交前通知当前页面
			window.addEventListener("message", receiveMessage, false);
			function receiveMessage(event)
			{
				if(event.data.event === 'beforeFormSubmit') {
					//向父窗口返回流程的内容
					let lbpmForm = getSysWfBusinessForm();
					//console.log(parent);
					parent.postMessage({"event": "sysWfBusinessForm", "form": lbpmForm}, "*");
				}
			}

			//获取流程的内容
			function getSysWfBusinessForm() {
				//提交表单事件触发修改表单的内容
				lbpm.globals.submitFormEvent();
                var formSerial = {};
                $($("#lbpmForm").serializeArray()).each(function() {
                    formSerial[this.name] = this.value;
                });
                return formSerial;
            }
		</script>
	</template:replace>
</template:include>