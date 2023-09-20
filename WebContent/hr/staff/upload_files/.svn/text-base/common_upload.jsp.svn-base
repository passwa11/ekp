<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.dialog">
	<template:replace name="content">
		<div style="position:relative;padding:7px 25px 5px 0px;text-align: right">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
				<ui:button text="${lfn:message('hr-staff:hrStaff.import.button.import')}" onclick="_submit();" order="1">
				</ui:button>
				<ui:button text="${lfn:message('button.close')}" onclick="_cancel();" order="2">
				</ui:button>
			</ui:toolbar>
		</div>
		<center>
		<form action="" method="post" enctype="multipart/form-data">
			<%-- 上传路径 --%>
			<input type="hidden" name="uploadActionUrl">
			<%-- 异常后数据是否需要回滚，默认为true --%>
			<input type="hidden" name="isRollBack">
			<table class="tb_normal" style="margin: 20px 0" width=98%>
				<tr>
					<td width="15%" class="td_normal_title">
						${lfn:message('common.fileUpLoad.selectFile')}
					</td>
					<td colspan="3">
						<input class="input_file" type="file" name="file" accept=".xls,.xlsx"/>
					</td>
				</tr>
			</table>
		</form>
		</center>
		
		<c:if test="${!empty resultMsg}">
			<div style="margin: auto 15px;">
					${lfn:message('hr-staff:hrStaff.import.result')}
				<c:choose>
					<c:when test="${state}">
						<img src="${LUI_ContextPath}/hr/staff/resource/images/status_succ.gif" /> ${resultMsg}
					</c:when>
					<c:otherwise>
						<img src="${LUI_ContextPath}/hr/staff/resource/images/status_faile.gif" /> ${lfn:message('hr-staff:hrStaff.import.failed')}
						<br><br>
						${resultMsg}
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>
		
		<script type="text/javascript">
			seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
				$(function() {
					var uploadActionUrl = "${JsParam.uploadActionUrl}";
					if(uploadActionUrl.length < 1) {
						uploadActionUrl = "${uploadActionUrl}";
					}
					$("form").attr("action", uploadActionUrl); // 设置form请求路径
					$("form [name=uploadActionUrl]").val(uploadActionUrl); // 保存form请求路径

					var isRollBack = "${JsParam.isRollBack}";
					if(isRollBack.length < 1) {
						isRollBack = "${isRollBack}";
					}
					$("form [name=isRollBack]").val(isRollBack);
				});
				
				// 上传
				window._submit = function() {
					$("form").submit();
				};
	
				// 取消
				window._cancel = function() {
					window.$dialog.hide();
				};
			});
		</script>
	</template:replace>
</template:include>