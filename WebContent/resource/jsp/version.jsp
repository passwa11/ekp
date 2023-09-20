<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@page import="com.landray.kmss.sys.config.design.SysConfigs"%>
<%@page import="com.landray.kmss.sys.config.design.SysCfgModule"%>
<title><bean:message key="sys.common.moduleInfo.title"/></title>
<div id="optBarDiv">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<center>
<p class="txttitle"><bean:message key="sys.common.moduleInfo.versionMsg"/></p>
<c:choose>
<c:when test="${not empty description}">
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message key="sys.common.moduleInfo.componentName"/>：
		</td>
		<td width="35%">
			<c:out value="${description.module.moduleName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message key="sys.common.moduleInfo.componentPath"/>：
		</td>
		<td width="35%">
			<c:out value="${description.module.modulePath}" />
		</td>	
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message key="sys.common.moduleInfo.parallelVersion"/>：
		</td>
		<td width="35%">
			<c:out value="${description.module.parallelVersion}" />
		</td>	
		<td class="td_normal_title" width=15%>
			<bean:message key="sys.common.moduleInfo.currentVersion"/>：
		</td>
		<td>
			<c:out value="${description.module.baseline}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message key="sys.common.moduleInfo.versionSerialNum"/>：
		</td>
		<td>
			<c:choose>
			<c:when test="${empty description.module.serialNum}">
				0
			</c:when>
			<c:otherwise>
				<c:out value="${description.module.serialNum}" />
			</c:otherwise>
			</c:choose>
		</td>	
		<td class="td_normal_title" width=15%>
			<bean:message key="sys.common.moduleInfo.versionIdentity"/>：
		</td>
		<td>
			<c:out value="${description.module.sourceMd5}" />
		</td>	
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message key="sys.common.moduleInfo.isCustom"/>：
		</td>
		<td>
			<c:out value="${description.module.isCustom}" />
		</td>
		<c:if test="${not empty description.module.tempVersion}">
			<td class="td_normal_title" width=15%>
				<bean:message key="sys.common.moduleInfo.isTempVersion"/>：
			</td>
			<td>
				<span><bean:message key="sys.common.moduleInfo.isTemp"/>（${description.module.tempVersion})</span>
			</td>
		</c:if>
		<c:if test="${empty description.module.tempVersion}">
			<td class="td_normal_title" width=15%>
			</td>
			<td>
			</td>
		</c:if>
	</tr>
</table>
<% 
	String __path = "/";
	String check = "?check=true";
	if(!com.landray.kmss.sys.authorization.util.TripartiteAdminUtil.isGeneralUser()) { // 非三员管理 才显示的内容
		__path = "/sys/profile/tripartiteAdminAction.do?method=showVersion&path=/";
		check = "&check=true";
	}
	pageContext.setAttribute("__path", __path);
	pageContext.setAttribute("check", check);
%>
<table border="0" width=95%>
	<tr>
		<td class="txtStrong">			
			<c:if test="${description.module.isCustom != '是'}">
				<br style="font-size: 8px"><a href="<c:url value="${__path}${description.module.modulePath}.version${check}"/>"><bean:message key="sys.common.moduleInfo.versionCheck"/></a>：<bean:message key="sys.common.moduleInfo.checkInfo"/>
			</c:if>
		</td>
	</tr>
</table>
<br/>
<table class="tb_normal" width=95%>
	<tbody>
	<tr>
		<td class="td_normal_title" colspan="6">
			<center><bean:message key="sys.common.moduleInfo.modifyRecord"/></center>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="40pt">
			<center><bean:message key="page.serial"/></center>
		</td>
		<td class="td_normal_title" width="60%">
			<center><bean:message key="sys.common.moduleInfo.description"/></center>
		</td>
		<%--
		<td class="td_normal_title" width="10%">
			<center>作者</center>
		</td>
		--%>
		<td class="td_normal_title" width="10%">
			<center><bean:message key="sys.common.moduleInfo.modifyTime"/></center>
		</td>
		<td class="td_normal_title" width="10%">
			<center><bean:message key="sys.common.moduleInfo.modifyAssociation"/></center>
		</td>
		<td class="td_normal_title">
			<center><bean:message key="sys.common.moduleInfo.versionMsg"/></center>
		</td>
	</tr>
	<c:forEach items="${modifyList}" var="modify" varStatus="vstatus">
		<tr>
			<td>
				<center>${vstatus.index+1}</center>
			</td>
			<td>
				<c:out value="${modify.description}" />
			</td>
			<%--
			<td>
				<center><c:out value="${modify.author}" /></center>
			</td>
			--%>
			<td>
				<center><kmss:showDate value="${modify.revisionTime}" type="date" /></center>
			</td>
			<td>
				<c:forEach items="${modify.relation.relationModuleList}" var="relationModule" varStatus="vstatus">
					<c:out value="${relationModule}" />
				</c:forEach>
			</td>
			<td>
				<c:out value="${modify.baseline}" />&nbsp;
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<br />
</c:when>
<c:otherwise>
<span class="txtstrong"><bean:message key="sys.common.moduleInfo.versionMsgNotFound"/></span>
</c:otherwise>
</c:choose>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>