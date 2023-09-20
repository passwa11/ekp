<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
	<script>
		function confirmDelete(msg){
			var del = confirm("<bean:message key="page.comfirmDelete"/>");
			return del;
		}
	</script>
	<div id="optBarDiv">
		<input type="button" value="<bean:message key="button.edit"/>" onclick="Com_OpenWindow('hrRatifyLeaveReason.do?method=edit&fdId=${JsParam.fdId}','_self');">
		<input type="button" value="<bean:message key="button.delete"/>" onclick="if(!confirmDelete())return;Com_OpenWindow('hrRatifyLeaveReason.do?method=delete&fdId=${JsParam.fdId}','_self');">
		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>
	<p class="txttitle">
	
		<c:choose>
			<c:when test="${hrRatifyLeaveReasonForm.fdType eq 'entry' }">
				<bean:message bundle="hr-ratify" key="hrRatifyLeaveReason.fdName.entry"/>
			</c:when>
			<c:otherwise>
				<bean:message bundle="hr-ratify" key="table.hrRatifyLeaveReason"/>
			</c:otherwise>
		</c:choose>
	
	</p>
	<center>
		<html:hidden name="hrRatifyLeaveReasonForm" property="fdId"/>
		<table class="tb_normal" width=95%>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="hr-ratify" key="hrRatifyLeaveReason.fdOrder"/>
				</td><td width=35%>
					<c:out value="${hrRatifyLeaveReasonForm.fdOrder}" />
				</td>
				<td class="td_normal_title" width=15%>
					<c:choose>
						<c:when test="${hrRatifyLeaveReasonForm.fdType eq 'entry' }">
							<bean:message bundle="hr-ratify" key="hrRatifyLeaveReason.fdName.entry"/>
						</c:when>
						<c:otherwise>
							<bean:message  bundle="hr-ratify" key="hrRatifyLeaveReason.fdName"/>
						</c:otherwise>
					</c:choose>
				</td><td width=35%>
					<c:out value="${hrRatifyLeaveReasonForm.fdName}" />
				</td>
			</tr>
		</table>
	</center>
<%@ include file="/resource/jsp/view_down.jsp"%>