<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<title><bean:message key="sys.common.moduleInfo.title"/></title>
<div id="optBarDiv">
	<input type="button" value="<bean:message key="sys.common.moduleInfo.exportModuleVersion"/>" onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}admin.do?method=exportModuleVersion','_self');">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<center>
<p class="txttitle"><bean:message key="sys.common.moduleInfo.versionMsg"/></p>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width="5%">
			<center><bean:message key="sys.common.moduleInfo.orderNum"/></center>
		</td>
		<td class="td_normal_title" width="12%">
			<center><bean:message key="sys.common.moduleInfo.componentName"/></center>
		</td>
		<td class="td_normal_title" width="15%">
			<center><bean:message key="sys.common.moduleInfo.componentPath"/></center>
		</td>
		<td class="td_normal_title" width="10%">
			<center><bean:message key="sys.common.moduleInfo.parallelVersion"/></center>
		</td>		
		<td class="td_normal_title" width="14%">
			<center><bean:message key="sys.common.moduleInfo.currentVersion"/></center>
		</td>
		<td class="td_normal_title" width="10%">
			<center><bean:message key="sys.common.moduleInfo.versionSerialNum"/></center>
		</td>		
		<td class="td_normal_title" width="14%">
			<center><bean:message key="sys.common.moduleInfo.versionIdentity"/></center>
		</td>
		<td class="td_normal_title" width="10%">
			<center><bean:message key="sys.common.moduleInfo.isCustom"/></center>
		</td>
		<td class="td_normal_title" width="10%">
			<center><bean:message key="sys.common.moduleInfo.isTempVersion"/></center>
		</td>
	</tr>
	<% 
		String __path = "/";
		if(!com.landray.kmss.sys.authorization.util.TripartiteAdminUtil.isGeneralUser()) { // 非三员管理 才显示的内容
			__path = "/sys/profile/tripartiteAdminAction.do?method=showVersion&path=/";
		}
		pageContext.setAttribute("__path", __path);
	%>
	<c:forEach items="${descriptionList}" var="description" varStatus="vstatus">
		<tr style="cursor:pointer;"
			onmouseover="this.style.backgroundColor='#F3F3F3'" onmouseout="this.style.backgroundColor='#FFFFFF'"
			onclick="Com_OpenWindow('<c:url value='${__path}${description.module.modulePath}.version' />','_blank');">
			<td>
				<center>${vstatus.index+1}</center>
			</td>
			<td>
				<c:out value="${description.module.moduleName}" />
			</td>
			<td>
				<c:out value="${description.module.modulePath}" />
			</td>
			<td>
				<c:out value="${description.module.parallelVersion}" />
			</td>
			<td>
				<c:out value="${description.module.baseline}" />
			</td>
			<td>
				<c:out value="${description.module.serialNum}" />
			</td>			
			<td>
				<c:out value="${description.module.sourceMd5}" />
			</td>
			<td>
				<c:out value="${description.module.isCustom}" />
			</td>
			<td>
				<c:if test="${not empty description.module.tempVersion}">
					是（<c:out value="${description.module.tempVersion}" />）
				</c:if>
			</td>
		</tr>
	</c:forEach>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>