<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="mainForm" value="${requestScope[param.formBeanName]}" scope="page" />
<c:set var="lbpmPostscriptMap" value="${requestScope.lbpmPostscriptMap}" scope="page" />
<style type="text/css">
.tb_normal .td_normal_title {
	text-align: center;
}
.lbpmPostscriptTableWrap{
	margin-top:10px;
	padding-bottom:10px;
	background-color:#EEEEEE;
	border-radius:4px;
    border:1px solid #bfbdbd;
}
.lbpmPostscriptTableWrap .tb_noborder{
	background-color:#EEEEEE;
}
.lbpmPostscriptHead{
	margin-right:10px;
}
.lbpmPostscriptContent{
	padding-top:5px !important;
	padding-left:10px !important;
	word-wrap: break-word;
	word-break: break-all;
}
.lbpmPostscriptAttachmentContent{
	padding-top:5px !important;
	padding-left:10px !important;
}
.lbpmPostscriptTop{
	padding-top:10px !important;
	display:inline-block;
}
.lbpmPostscriptTableWrap .upload_list_filename_s{
	min-width:130px;
}
</style>
<c:forEach items="${lbpmPostscriptMap[param.fdAuditNoteId]}" var="lbpmPostscript" varStatus="vStatus">
	<div class="lbpmPostscriptTableWrap">
		<table class="tb_noborder" width="100%">
			<c:if test="${lbpmPostscript.fdIsHide=='2'}">
				<tr>
					<td class="lbpmPostscriptContent lbpmPostscriptTop">
						<span class="lbpmPostscriptHead">
							<kmss:showDate type="datetime" value="${lbpmPostscript.fdCreateTime}"/>
						</span>
						<span class="lbpmPostscriptHead" title='<c:out value="${lbpmPostscript.handlerName}" />'>
							<c:out value="${lbpmPostscript.handlerName}" escapeXml="false"/>
						</span>
						<span class="lbpmPostscriptHead">
							<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.list.postscript" />
						</span>
					</td>
				</tr>
				<tr>
					<td class="lbpmPostscriptContent">
						<kmss:showText value="${lbpmPostscript.fdPostscript}" />
					</td>
				</tr>
				<c:if test="${not empty mainForm.attachmentForms[lbpmPostscript.fdId] and not empty mainForm.attachmentForms[lbpmPostscript.fdId].attachments}">
					<tr>
						<td colspan="2" class="lbpmPostscriptAttachmentContent">
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
					          <c:param name="formBeanName" value="${formBeanName}"/>
					          <c:param name="fdKey" value="${lbpmPostscript.fdId}"/>
					          <c:param name="fdModelId" value="${lbpmPostscript.fdProcess.fdModelId}"/>
					          <c:param name="fdModelName" value="${lbpmPostscript.fdProcess.fdModelName}"/>
					          <c:param name="fdViewType" value="simple" />
					          <c:param name="fdForceDisabledOpt" value="edit" />
						      <c:param name="isShowDownloadCount" value="false" />
					        </c:import>
						</td>
					</tr>
				</c:if>
			</c:if>
			<c:if test="${lbpmPostscript.fdIsHide=='1'}" >
				<tr>
					<td colspan="2" class="lbpmPostscriptContent">
						<font style="font-style:italic">
							<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_1" />
						</font>
					</td>
				</tr>
			</c:if>
			<c:if test="${lbpmPostscript.fdIsHide=='3'}" >
				<tr>
					<td colspan="2" class="lbpmPostscriptContent">
						<font style="font-style:italic">
							<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_3" />
						</font>
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty lbpmPostscript.fdPostscriptFrom}">
				<tr>
					<td colspan="2" align="right" style="color:#999;">
						<kmss:showText value="${lbpmPostscript.postscriptFrom}" />
					</td>
				</tr>
			</c:if>
		</table>
	</div>
</c:forEach>