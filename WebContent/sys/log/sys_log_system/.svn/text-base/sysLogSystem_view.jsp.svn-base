<%@page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@page import="com.landray.kmss.sys.log.util.LogConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/log/resource/import/jshead.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="webservice" value="<%=LogConstant.LogSystemType.WEBSERVICE.getVal() %>"/> 
<c:set var="restservice" value="<%=LogConstant.LogSystemType.RESTSERVICE.getVal() %>"/>
<div id="optBarDiv">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<div class="txttitle"><bean:message bundle="sys-log" key="table.sysLogSystem"/></div>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogSystem.fdStartTime"/>
		</td><td width=35%>
			<fmt:formatDate value="${sysLogSystem.fdStartTime }"  type="both" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogSystem.fdEndTime"/>
		</td><td width=35%>
			<fmt:formatDate value="${sysLogSystem.fdEndTime }"  type="both" />
		</td>
	</tr>
	<%-- 开启三员时不显示标题 --%>
	<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogSystem.fdSubject"/>
		</td><td colspan=3>
			<c:out value="${sysLogSystem.fdSubject}"/>
		</td>
	</tr>
	<% } %>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogSystem.fdServiceBean"/>
		</td><td width=35%>
			<c:out value="${sysLogSystem.fdServiceBean}"/>
		</td>
		<%-- 显示请求路径或请求方法 --%>
		<c:choose>
	  		<c:when test="${sysLogSystem.fdType == restservice}">
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-log" key="sysLogSystem.fdOriginUri"/>
				</td>
				<td width=35%>
		            <c:out value="${sysLogSystem.fdOriginUri}"/>		
		        </td>
	        </c:when>
	        <c:otherwise>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-log" key="sysLogSystem.fdServiceMethod"/>
				</td>
				<td width=35%>
		            <c:out value="${sysLogSystem.fdServiceMethod}"/>		
		        </td>
	        </c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogSystem.fdSuccess"/>
		</td><td colspan=3>
			<sunbor:enumsShow value="${sysLogSystem.fdSuccess}" enumsType="sysLogSystem_enum_fdSuccess" />
		</td>
	</tr>
	<%-- WebService--%>
	<c:if test="${sysLogSystem.fdType == webservice}">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-log" key="sysLogSystem.fdRequestMsg"/>
			</td>
			<td colspan=3 class="valign_top">
				<c:out value="${sysLogSystem.fdRequestMsg}"/>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-log" key="sysLogSystem.fdResponseMsg"/>
			</td>
			<td colspan=3 class="valign_top">
				<c:out value="${sysLogSystem.fdResponseMsg}"/>
			</td>
		</tr>
	</c:if>
	<%-- RestService--%>
	<c:if test="${sysLogSystem.fdType == restservice}">
		<%-- 请求报头、报文 --%>
		<tr>
			<td class="td_normal_title" width=50% colspan="2">
				<bean:message bundle="sys-log" key="sysLogSystem.fdRequestHeader"/>
			</td>
			<td class="td_normal_title" width=50% colspan="2">
				<bean:message bundle="sys-log" key="sysLogSystem.fdRequestMsg"/>
			</td>
		</tr>
		<tr>
			<td width=50% colspan="2" class="valign_top">
				<xform:textarea property="fdRequestHeader" value="${sysLogSystem.fdRequestHeader}" style="width:85%"/>
			</td>
			<td width=50% colspan="2" class="valign_top">
				<div class="pre_hide">
					<xform:textarea property="fdRequestMsg" value="${sysLogSystem.fdRequestMsg}" style="width:85%"/>
				</div>
			</td>
		</tr>
		<%-- 响应报头、报文 --%>
		<tr>
			<td class="td_normal_title" width=50% colspan="2">
				<bean:message bundle="sys-log" key="sysLogSystem.fdResponseHeader"/>
			</td>
			<td class="td_normal_title" width=50% colspan="2">
				<bean:message bundle="sys-log" key="sysLogSystem.fdResponseMsg"/>
			</td>
		</tr>
		<tr>
			<td width=50% colspan="2" class="valign_top">
				<xform:textarea property="fdResponseHeader" value="${sysLogSystem.fdResponseHeader}" style="width:85%"/>
			</td>
			<td width=50% colspan="2" class="valign_top">
				<div class="pre_hide">
					<xform:textarea property="fdResponseMsg" value="${sysLogSystem.fdResponseMsg}" style="width:85%"/>
				</div>
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogSystem.fdDesc"/>
		</td><td colspan=3>
			<pre><c:out value="${sysLogSystem.fdDesc}"/></pre>
		</td>
	</tr>
</table>
</center>
<link type="text/css" rel="styleSheet"  href="${LUI_ContextPath}/sys/log/resource/css/systemLog_view.css" />
<script type="text/javascript" src="${LUI_ContextPath}/sys/log/resource/js/systemLog_view.js"></script>
<%@ include file="/resource/jsp/view_down.jsp"%>