<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%
	request.setAttribute("fdAppModelId", request.getParameter("fdAppModelId"));
%>
<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:out value="${modelingAppModelMainForm.fdModelName}"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<%@ include file="/sys/modeling/main/formview/model_printButton.jsp"%>
	</template:replace>
	<template:replace name="content">
		<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/main/resources/css/print.css"/>
		<div align="center" id="printTable">
		<div id="toolBarDiv" style="padding-top: 12px;" data-remove="false">
			 <table class="tb_normal" width=100%>
				<tr>
					<td>
						<label>
						<input  id="subject" type="checkbox" onclick="changeDisplay(this);" <c:if test="${'true' eq printConfig.subject}">checked="checked"</c:if>/>
						 <bean:message bundle="sys-modeling-main" key="print.config.subject" />
						</label>
					</td>
					<td>
						<label>
						<input id="info" type="checkbox" onclick="changeDisplay(this);" <c:if test="${'true' eq printConfig.info}">checked="checked"</c:if>/>
						<bean:message bundle="sys-modeling-main" key="print.reviewContent" />
						</label>
					</td>
					<td>
						<label>
						<input id="note" type="checkbox" onclick="changeDisplay(this);" <c:if test="${'true' eq printConfig.note}">checked="checked"</c:if>/>
						<bean:message bundle="sys-modeling-main" key="print.flow.trail" />
						</label>
					</td>
					<td>
						<label>
						<input id="qrcodex" type="checkbox" onclick="changeDisplay(this);" <c:if test="${'true' eq printConfig.qrcodex}">checked="checked"</c:if>/>
						<bean:message bundle="sys-modeling-main" key="print.config.qrcode" />
						</label>
					</td>
				</tr>
			</table>
		</div>
		<!-- 标题 -->
		<div class="print_title_header" id="subjectDiv">
			<p id="title" class="print_txttitle">${modelingAppModelMainForm.fdModelName}</p>
			<div class="printDate">
			  <bean:message bundle="sys-modeling-main" key="print.date" />:<% out.print(DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATE, request.getLocale()));%>
			</div>
		</div>
		<%-- 审批内容 --%>
		<div id="infoDiv">
		    <div class="tr_label_title">
			    <div class="title">
			       <bean:message bundle="sys-modeling-main" key="print.reviewContent" />
			    </div>
		    </div>
		    <%-- 表单 --%>
		    <html:hidden property="fdModelId" />
			<div id="sysModelingXform">
				<c:import url="/sys/xform/include/sysForm_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppModelMainForm" />
					<c:param name="fdKey" value="modelingApp" />
					<c:param name="messageKey" value="sys-modeling-main:print.reviewContent" />
					<c:param name="useTab" value="false" />
					<c:param name="isPrint" value="true" />
				</c:import>
			</div>
		</div>
		 <%-- 审批记录 --%>
		<div id="noteDiv">
		    <div class="tr_label_title">
			    <div class="title">
			       <bean:message bundle="sys-modeling-main" key="print.flow.trail" />
			    </div>
		    </div>
			<table width=100%>
				<!-- 审批记录 -->
				<tr>
					<td colspan=4>
						<c:import url="/sys/workflow/include/sysWfProcess_log.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="modelingAppModelMainForm" />
						</c:import>
					</td>
				</tr>
			</table>
		</div>
		<%
			String agent = request.getHeader("User-Agent").toLowerCase();
			if(agent.indexOf("msie 8") < 0){
		%>
		<div id="qrcodexDiv">
			<c:set var="modelViewUrl" value="sys/modeling/main/modelingAppModelMain.do?method=view&fdId=${param.fdId }"/>
			<%@ include file="/sys/modeling/main/formview/model_printQrCode.jsp"%>
		</div>
		<%
			}
		%>
		</div>
		<script>
			function changeDisplay(obj){
				if(obj.checked){
			      $("#"+obj.id+"Div").show();
				}else{
					$("#"+obj.id+"Div").hide();
				}
			}
			seajs.use(['lui/jquery'], function($) {
				$(document).ready(function(){
					if("${printConfig.subject}" != 'true' ){
						$("#subjectDiv").hide();
					}
					if("${printConfig.info}" != 'true' ){
						$("#infoDiv").hide();
					}
					if("${printConfig.note}" != 'true' ){
						$("#noteDiv").hide();
					}
					if("${printConfig.qrcodex}" != 'true' ){
						$("#qrcodexDiv").hide();
					}
				});
			});
		</script>
	</template:replace>
</template:include>