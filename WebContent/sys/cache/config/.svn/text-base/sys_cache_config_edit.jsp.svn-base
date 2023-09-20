<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.cache.filter.WebContentCacheUtils"%>
<%@ page import="com.landray.kmss.sys.cache.filter.AbstractWebContentCache"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>

<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="title"><bean:message bundle="sys-cache" key="base.config" /></template:replace>
<template:replace name="content">
<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
	<div style="margin-top:25px">
	<p class="configtitle"><bean:message bundle="sys-cache" key="base.config" /></p>
		<center>
			<table class="tb_normal" width=80%>
				<tr>
                    <td class="td_normal_title" width=25%>
                        <b><bean:message bundle="sys-cache" key="contentCacheEnabled.title"/></b>
                    </td>
                    <td colspan="3">
                        <ui:switch property="value(contentCacheEnabled)"
                            enabledText="${lfn:message('sys-cache:btn.enable')}"
                            disabledText="${lfn:message('sys-cache:btn.disable')}">
                        </ui:switch>
                        <span class="message">
                            <font color="red">
                                <bean:message bundle="sys-cache" key="contentCacheEnabled.desc"/>
                            </font>
                        </span>
                    </td>
                </tr>
                <%
                    Iterator<AbstractWebContentCache> iterator =
                        WebContentCacheUtils.getAllPlugin().values().iterator();
                    while(iterator.hasNext()){
                    AbstractWebContentCache cache = iterator.next();
                %>
                <c:set value="<%=cache.getName()%>" var="cacheName"></c:set>
                <tr>
                    <td class="td_normal_title" width=25%>
                        <%=ResourceUtil.getMessage("{"+cache.getDisplayName()+"}")%>
                    </td>
                    <td colspan="3">
                        <ui:switch property="value(${cacheName})"
                            enabledText="${lfn:message('sys-cache:btn.enable')}"
                            disabledText="${lfn:message('sys-cache:btn.disable')}">
                        </ui:switch>
                        <span class="message">
                            <font>
                                <%=ResourceUtil.getMessage("{"+cache.getDescription()+"}")%>
                            </font>
                        </span>
                    </td>
                </tr>
                <%}%>
				<html:hidden property="method_GET" />
			</table>
			<div style="margin-bottom: 10px;margin-top:25px">
				  <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="commitMethod();"></ui:button>
			</div>
		</center>
	</div>
</html:form>
<script type="text/javascript">
 	function commitMethod(){
 			Com_Submit(document.sysAppConfigForm, 'update');
 	}
</script>
</template:replace>
</template:include>
