<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/hr/staff/resource/css/hr_staff.css?s_cache=${LUI_Cache}"/>
	<template:replace name="content">
		<center>
		<form id="uploadForm" action="" method="post" enctype="multipart/form-data">
			<%-- 上传路径 --%>
			<input type="hidden" name="uploadActionUrl">
			<!--  下载-->
			<input type="hidden" name="downLoadUrl">
			<%-- 异常后数据是否需要回滚，默认为true --%>
			<input type="hidden" name="isRollBack">
			<table class="staff_resume_simple_tb" style="margin: 20px 0" width=98%>
				<tr>
					<td width="30%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaff.import.stepone_msg')}
					</td>
					<td>
						<input type="button" value="${lfn:message('hr-staff:hrStaff.import.button.download')}" onclick="download();" style=" border-radius: 5px;background:#ccc;border-color: white;border-width:1px;border-top-color: #c7c5c2;border-left-color: #c7c5c2;width: 200px;height: 35px;box-shadow: none;background: white;"/>
					</td>
					
				</tr>
				<tr>
					<td>
					</td>
					<td style="color: #828282">
						${lfn:message('hr-staff:hrStaff.import.stepone_title')}
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaff.import.steptwo_msg')}
					</td>
					<td colspan="3">
						<input  type="file" name="file" accept=".xls,.xlsx" value="上传Excel文档"/>
					</td>
				</tr>
				<tr>
					<td>
					</td>
					<td style="color: #828282">
						${lfn:message('hr-staff:hrStaff.import.steptwo_title')}
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2">
						<ui:button text="${lfn:message('hr-staff:hrStaff.import.button.import')}" onclick="_submit();">
						</ui:button>
						<ui:button text="${lfn:message('button.close')}" onclick="_cancel();" style="padding-left:20px;">
						</ui:button>
					<td>
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
					var downLoadUrl = "${JsParam.downLoadUrl}";
					if(uploadActionUrl.length < 1) {
						uploadActionUrl = "${uploadActionUrl}";
					}
					if(downLoadUrl.length < 1) {
						downLoadUrl = "${downLoadUrl}";
					}
					$("form").attr("action", uploadActionUrl); // 设置form请求路径
					$("form [name=uploadActionUrl]").val(uploadActionUrl); // 保存form请求路径
					$("form [name=downLoadUrl]").val(downLoadUrl); // 保存下载请求路径
					var isRollBack = "${JsParam.isRollBack}";
					if(isRollBack.length < 1) {
						isRollBack = "${isRollBack}";
					}
					$("form [name=isRollBack]").val(isRollBack);
				});
				
				// 上传
				window._submit = function() {
					$("#uploadForm").submit();
				};
				// 取消
				window._cancel = function() {
					window.$dialog.hide();
				};
				
				window.download =function(){
					var downLoadUrl = $("input[name='downLoadUrl']").val();
					var temp = document.createElement("form");
					  temp.action = downLoadUrl;
					  temp.method = "post";
					  temp.id = "downloadTempletForm";
					  temp.style.display = "none";
					  document.body.appendChild(temp);
					  temp.submit();
					  $('#downloadTempletForm').remove();
				};
			});
		</script>
	</template:replace>
</template:include>