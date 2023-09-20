<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.km.forum.model.KmForumConfig"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<%
	ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
%>
<%
    request.setAttribute("globalIsAnonymous",new KmForumConfig().getAnonymous());
%>
<script type="text/javascript">
Com_IncludeFile("common.js|doclist.js|dialog.js");
</script>

<html:form action="/km/forum/km_forum_cate/kmForumCategory.do">
<div style="margin-top:25px">
<p class="configtitle">
<bean:message bundle="km-forum" key="menu.kmForum.manage"/>
</p>
<center>
<table class="tb_normal" width=90%>
		<html:hidden property="fdId"/>
		<html:hidden property="docCreateTime"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.forumVisits"/>
		</td>
		<td colspan=3>
			<xform:address propertyName="authReaderNames" propertyId="authReaderIds" mulSelect="true" textarea="true" showStatus="edit"></xform:address>
			<br>
<!-- 			<bean:message bundle="km-forum" key="kmForumCategory.fdVisitNames"/> -->
                     <% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
                     <!-- 保存当前页面form名称，用于生态组织判断时从request中取值 -->
    				 <c:set var="formName" value="kmForumCategoryForm" scope="request"/>
				    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
				        <!-- （为空则本组织人员可访问） -->
				        <bean:message  bundle="km-forum" key="kmForumCategory.fdVisitNames.orgnization" arg0="${ecoName}"/>
				    <% } else { %>
				        <!-- （为空则所有内部人员可访问） -->
				        <bean:message  bundle="km-forum" key="kmForumCategory.fdVisitNames.inner" />
				    <% } %>
				<% } else { %>
				    <!-- （为空则所有人可访问） -->
				    <bean:message  bundle="km-forum" key="kmForumCategory.fdVisitNames" />
				<% } %>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.forumPosters"/>
		</td><td colspan=3>
			<xform:address propertyId="fdPosterIds" propertyName="fdPosterNames" mulSelect="true" orgType="ORG_TYPE_ALL" splitChar=";" textarea="true" showStatus="edit">
			</xform:address>
			<%-- 添加说明 (如果为空则所有人允许发帖) -modify by zhouchao ----%>
			<br>
			<bean:message  bundle="km-forum" key="kmForumCategory.fdPosterNames"/>			
		</td>
	</tr>

	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.fdAnonymous"/>
		</td><td>
			<ui:switch property="fdAnonymous" checked="${globalIsAnonymous}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.fdMainScore"/>
		</td><td>
			<bean:write name="kmForumCategoryForm" property="fdMainScore"/>
			<html:hidden property="fdMainScore" />
		</td>
	</tr>

	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.fdResScore"/>
		</td><td>
			<bean:write name="kmForumCategoryForm" property="fdResScore"/>
			<html:hidden property="fdResScore" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.fdPinkScore"/>
		</td><td>
			<bean:write name="kmForumCategoryForm" property="fdPinkScore"/>
			<html:hidden property="fdPinkScore" />
		</td>
	</tr>

	<tr>
		<td colspan=4><bean:message  bundle="km-forum" key="kmForumCategory.forumManager.msg"/></td>
	</tr>	

</table>
<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.kmForumCategoryForm, 'manageUpdate');" order="1" ></ui:button>
</div>
</center>
</div>
<html:hidden property="method_GET"/>
</html:form>
<script language="JavaScript">Com_IncludeFile("calendar.js");</script>
<html:javascript formName="kmForumCategoryForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
</template:replace>
</template:include>

<ui:top id="top"></ui:top>
<kmss:ifModuleExist path="/sys/help">
	<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
</kmss:ifModuleExist>