<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%-- Hibernate二级缓存管理页面，只管理声明了管理扩展的缓存 --%>

<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="title"><bean:message bundle="sys-cache" key="hibernate.cache" /></template:replace>
<template:replace name="content">
<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
	<div style="margin-top:25px">
	<p class="configtitle"><bean:message bundle="sys-cache" key="hibernate.cache" /></p>
		<center>
			<table class="tr_normal_title" width="85%" >
            <tr>
                <td class="td_normal_title" width=15%>
                    <%--缓存总开关--%>
                    全部清理
                </td>
                <td>
                    <ui:button text="清理" height="35" width="120" onclick=""></ui:button>(此功能会清除声明过Hibernate管理扩展的所有缓存)
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width=15%>
                    <%--缓存总开关--%>
                    全部禁用/启用
                </td>
                <td>
                    <ui:switch property="aa" onValueChange="config_chgEnabled();" enabledText="${lfn:message('sys-cache:btn.enable')}" disabledText="${lfn:message('sys-cache:btn.disable')}">
                    </ui:switch>
                </td>
            </tr>
            <%--缓存数据--%>
            <tr id="dataRow">
                <td colspan="2">
                    <table class="tr_normal_title" width="85%" >
                        <tr>
                            <td class="td_normal_title">缓存名称</td>
                            <td class="td_normal_title" style="text-align: center">描述</td>
                            <td class="td_normal_title">缓存类型</td>
                            <td class="td_normal_title">所属模块</td>
                            <td class="td_normal_title">操作</td>
                        </tr>
                        <c:forEach var="cache" items="${cacheList}">
                            <tr class="tr_normal_title" style="text-align:left;cursor:pointer;" onclick="showRow(this);">
                                <td colspan="5">
                                    <img src="${KMSS_Parameter_StylePath}icons/plus.gif"/>&nbsp;&nbsp;
                                    <c:out value="${cache.modelName}" />
                                    <label onclick="selectOpt(this,event);">
                                        <input type="checkbox" <c:if test="${fn:length(_sysAuthAssignMap[moduleMap.key]) == fn:length(moduleMap.value)}">checked</c:if> />
                                        <bean:message key="sysAuthRole.selectAll" bundle="sys-authorization"/>
                                    </label>
                                </td>
                            </tr>
                            <%-- 缓存数据 --%>
                            <c:forEach var="cachevo" items="${cache.modelList}">
                            <tr style="text-align:left;cursor:pointer;">
                                <td>${cachevo.nameMsg}</td>
                                <td>${cachevo.descMsg}</td>
                                <td>${cachevo.typeMsg}</td>
                                <td>${cachevo.moduleMsg}</td>
                                <td>
                                    <ui:switch property="value(${cachevo.regionName})" enabledText="${lfn:message('sys-cache:btn.disable')}" disabledText="${lfn:message('sys-cache:btn.enable')}"></ui:switch>
                                </td>
                            </tr>
                            </c:forEach>
                        </c:forEach>
                    </table>
                </td>
            </tr>
            <html:hidden property="method_GET" />
            <html:hidden property="modelName"  value="com.landray.kmss.sys.cache.hibernate.HibernateRegionConfig"/>
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
 	function allCheck(obj){
        var s = $("[name*='value(']");
        var value = ''+obj.checked;
        s.each(function(ele){
            //ele.val('true');
            $(this).val(value);
        });
    }
</script>
</template:replace>
</template:include>

