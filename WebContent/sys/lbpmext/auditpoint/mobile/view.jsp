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
(function(window,$){
	 var _extTime=0;
	 window._extAuditPointShowAndHide=function(id){
		var nowTime = new Date();		 
		if(nowTime-_extTime < 350){
			return;
		}
		_extTime = nowTime;
		require(["dojo/query" ,"dojo/dom","dojo/fx"], function(query,dom,fx){
			if($("#lbpmext_auditpoint_content_"+id).css('display')=='none'){
				fx.wipeIn({  
			        node: dom.byId("lbpmext_auditpoint_content_"+id)  
			    }).play();
				$("#lbpmext_auditpoint_button_"+id).html("<bean:message key="lbpmExtAuditPoint.hide" bundle="sys-lbpmext-auditpoint" />");
			}else{
				fx.wipeOut({  
			        node: dom.byId("lbpmext_auditpoint_content_"+id)  
			    }).play();
				$("#lbpmext_auditpoint_button_"+id).html("<bean:message key="lbpmExtAuditPoint.show" bundle="sys-lbpmext-auditpoint" />");
			}
		});
	}; 
})(window,$);
</script>
<div id="lbpmext_auditpoint_content_${param.auditNoteFdId}" style="display: none;">
<c:forEach var="record" items="${lbpmExtAuditPointList }">
<div>
	<label>
		<xform:checkbox showStatus="view" htmlElementProperties="id='${record.fdId }'" mobile="true" value="${record.fdIsPass?'true':'' }" property="auditNoteFdId_${record.fdId }">
			<xform:simpleDataSource value="true">
				<xlang:lbpmlang property="" value="${record.fdTitle}" langs="${record.fdLangs}" showStatus="list"/>				
			</xform:simpleDataSource>
		</xform:checkbox>
		<style>
			[id='${record.fdId }'] .muiCheckBoxWrap{
			    margin-top: 0 !important;
			    padding-top: 1rem;
		        padding-bottom: 1rem;
			}
			[id='${record.fdId }'] .muiCheckItem{
			    margin-top: 0 !important;
			}
		    <c:if test="${record.fdIsImportant }">
			[id='${record.fdId }'] .muiCheckItem:AFTER{
			    position: absolute;
				content:'*';
				color: red;
				top: .4rem;
                right: .8rem;
			}
		    </c:if>
		</style>
	</label>
</div>
</c:forEach>
</div>

<style>
<!--
	div[id*='lbpmext_auditpoint_button_']{
		font-size: 16px;
		color: #eea236;
	    display: inline-block;
	    font-size: 1.5rem;
	    height: 2.0rem;
	    line-height: 2.0rem;
	    padding: 0rem .3rem;
	    border-radius: .2rem;
	    border-width: 1px;
	    border-style: solid;
	    margin-right: .5rem;
	    position: relative;
	    top: -0.1rem;
	}
-->
</style>
</c:if>