<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
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
	box-shadow:0 0 1px #000;
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
<script type="text/javascript">
function expandPostscript(src){
	var $lbpmPostscriptTableWrap = $(src).closest(".lbpmPostscriptTableWrap");
	$lbpmPostscriptTableWrap.removeClass("collapse");
	if (!$lbpmPostscriptTableWrap.hasClass("expand")) {
		$lbpmPostscriptTableWrap.addClass("expand");
	}
	$lbpmPostscriptTableWrap.find("[name='lbpmPostscriptCollapseBtn']").css("display","inline-block");
	$(src).hide();
}

function collapsePostscript(src){
	var $lbpmPostscriptTableWrap = $(src).closest(".lbpmPostscriptTableWrap");
	$lbpmPostscriptTableWrap.removeClass("expand");
	if (!$lbpmPostscriptTableWrap.hasClass("collapse")) {
		$lbpmPostscriptTableWrap.addClass("collapse");
	}
	$lbpmPostscriptTableWrap.find("[name='lbpmPostscriptExpandBtn']").css("display","inline-block");
	$(src).hide();
}
</script>
<c:forEach items="${lbpmPostscriptMap[param.fdAuditNoteId]}" var="lbpmPostscript" varStatus="vStatus">
	<div class="lbpmPostscriptTableWrap">
		<div class="tb_noborder" width="100%">
			<c:if test="${lbpmPostscript.fdIsHide=='2'}">
				<div>
					<div class="lbpmPostscriptContent lbpmPostscriptTop">
						<span class="lbpmPostscriptHeadTime">
							<kmss:showDate type="datetime" value="${lbpmPostscript.fdCreateTime}"/>
						</span>
						<c:if test='<%="true".equals(SysFormDingUtil.getEnableDing())%>'>
							<span class="lbpmPostscriptHeadBtns">
								<span class="muiLbpmserviceAuditExpandBtn" name="lbpmPostscriptExpandBtn" onclick="expandPostscript(this);" style="display: none;">
									展开
								</span>
								<span class="muiLbpmserviceAuditCollapseBtn" name="lbpmPostscriptCollapseBtn" onclick="collapsePostscript(this);" style="display: inline-block;">
									收起
								</span>
							</span>
						</c:if>
						<span class="lbpmPostscriptHeadHandlerName" title='<c:out value="${lbpmPostscript.handlerName}" />'>
							<c:out value="${lbpmPostscript.handlerName}" escapeXml="false"/>
						</span>
						<span class="lbpmPostscriptHeadContent">
							<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.list.postscript" />
						</span>
					</div>
				</div>
				<div>
					<div class="lbpmPostscriptContent">
						<kmss:showText value="${lbpmPostscript.fdPostscript}" />
					</div>
				</div>
				<c:if test="${not empty mainForm.attachmentForms[lbpmPostscript.fdId] and not empty mainForm.attachmentForms[lbpmPostscript.fdId].attachments}">
					<div>
						<div colspan="2" class="lbpmPostscriptAttachmentContent">
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
					          <c:param name="formBeanName" value="${formBeanName}"/>
					          <c:param name="fdKey" value="${lbpmPostscript.fdId}"/>
					          <c:param name="fdModelId" value="${lbpmPostscript.fdProcess.fdModelId}"/>
					          <c:param name="fdModelName" value="${lbpmPostscript.fdProcess.fdModelName}"/>
					          <c:param name="fdViewType" value="simple" />
					          <c:param name="fdForceDisabledOpt" value="edit" />
						      <c:param name="isShowDownloadCount" value="false" />
					        </c:import>
						</div>
					</div>
				</c:if>
			</c:if>
			<c:if test="${lbpmPostscript.fdIsHide=='1'}" >
				<div>
					<div colspan="2" class="lbpmPostscriptContent">
						<font style="font-style:italic">
							<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_1" />
						</font>
					</div>
				</div>
			</c:if>
			<c:if test="${lbpmPostscript.fdIsHide=='3'}" >
				<div>
					<div colspan="2" class="lbpmPostscriptContent">
						<font style="font-style:italic">
							<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_3" />
						</font>
					</div>
				</div>
			</c:if>
			<c:if test="${not empty lbpmPostscript.fdPostscriptFrom}">
				<div>
					<div colspan="2" align="right" style="color:#999;">
						<kmss:showText value="${lbpmPostscript.postscriptFrom}" />
					</div>
				</div>
			</c:if>
		</div>
	</div>
</c:forEach>