<%@page import="com.landray.kmss.sys.lbpmext.auditpoint.util.AuditPointUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%
	AuditPointUtil.loadAuditPoint4View(request);
%>
<c:if test="${not empty lbpmExtAuditPointList }">
<div class="lbpmext-audit-point-hidden" id="lbpmext_auditpoint_button_${param.auditNoteFdId}" onclick="_extAuditPointShowAndHide('${param.auditNoteFdId}')">
	<bean:message key="lbpmExtAuditPoint.show" bundle="sys-lbpmext-auditpoint" />
</div>
<script type="text/javascript">
<!--
_extAuditPointShowAndHide=function(id){
	if($("#lbpmext_auditpoint_content_"+id).css('display')=='none'){
		$("#lbpmext_auditpoint_content_"+id).slideToggle();
		$("#lbpmext_auditpoint_button_"+id).html("<bean:message key="lbpmExtAuditPoint.hide" bundle="sys-lbpmext-auditpoint" />");
	}else{
		$("#lbpmext_auditpoint_content_"+id).slideToggle();
		$("#lbpmext_auditpoint_button_"+id).html("<bean:message key="lbpmExtAuditPoint.show" bundle="sys-lbpmext-auditpoint" />");
	}
}
//-->
</script>
<div lbpmext-auditpoint-data-content="lbpmext_auditpoint_content" id="lbpmext_auditpoint_content_${param.auditNoteFdId}" style="display: none;">
	<div>
		<c:forEach var="record" items="${lbpmExtAuditPointList }">
		
		<div>
			<label>
				<input type="checkbox"  ${record.fdIsPass?'checked':'' } disabled="disabled"/>
				<xlang:lbpmlang property="" value="${record.fdTitle}" langs="${record.fdLangs}" showStatus="list"/>				
				<!--kmss:showText value="${record.fdTitle}" /-->
				<c:if test="${record.fdIsImportant}"><span class="txtstrong">*</span></c:if>
			</label>
		</div>
		</c:forEach>
	</div>
</div>

<style>
<!--
	.lbpmext-audit-point-hidden{
		color: #eea236;
	    display: inline-block;
	    /* font-size: 1.5rem; */
	    /* height: 2.0rem;
	    line-height: 2.0rem; */
	    padding: 0rem .3rem;
	    border-radius: .2rem;
	    border-width: 1px;
	    border-style: solid;
	    margin-right: .5rem;
	    position: relative;
	    top: -0.1rem;
	    margin-top: 5px;
	    cursor: pointer;
	}
	div[lbpmext-auditpoint-data-content='lbpmext_auditpoint_content']{
	  	margin-top: 5px;
	}
-->
</style>
<!-- ------------------ 控制打印-------------- -->
<c:if test="${param.method=='print' }">
	<style>
	  .lbpmext-audit-point-hidden{
		display: none !important;
	  }
	</style>
</c:if>
<style>
@media print{
  .lbpmext-audit-point-hidden{
  	display: none !important;
  }
}
</style>
<c:if test="${lbpmext_auditpoint_isPrintShow!='true' }">
	<style>
	<!--
	@media print{
	  div[lbpmext-auditpoint-data-content='lbpmext_auditpoint_content']{
		display: none  !important;
	  }
	}
	-->
	</style>
	<c:if test="${param.method=='print' }">
		<style>
		<!--
		div[lbpmext-auditpoint-data-content='lbpmext_auditpoint_content']{
			display: none !important;
		  }
		-->
		</style>
	</c:if>
</c:if>
<c:if test="${lbpmext_auditpoint_isPrintShow=='true' }">
	<c:if test="${param.method=='print' }">
		<style>
		<!--
		div[lbpmext-auditpoint-data-content='lbpmext_auditpoint_content']{
			display: block !important;
		  }
		-->
		</style>
	</c:if>
	<style>
	<!--
	@media print{
	  div[lbpmext-auditpoint-data-content='lbpmext_auditpoint_content']{
		display: block  !important;
	  }
	}
	-->
	</style>
</c:if>
<!-- ------------------ 控制打印-------------- -->

</c:if>
