<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.landray.kmss.sys.appconfig.model.BaseAppConfig" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%-- Hibernate二级缓存统计页面 --%>

<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="title"><bean:message bundle="sys-cache" key="hibernate.cache" /></template:replace>
<template:replace name="content">
    <%
        int referTime = 5*60;
        BaseAppConfig stat_config =BaseAppConfig.getAppConfigByClassName("com.landray.kmss.sys.cache.hibernate.HibernateCacheStatisConfig");
        Map map = stat_config==null?null:stat_config.getDataMap();
        if(map!=null&&"true".equals(map.get("referEnabled"))&& map.get("referTime")!=null){
            referTime=Integer.valueOf(map.get("referTime").toString())*60;
            response.setIntHeader("Refresh", referTime);
        }
    %>
    <html:form action="/sys/cache/HibernateCacheStatisConfig.do">
	<div style="margin-top:25px">
	<p class="configtitle"><bean:message bundle="sys-cache" key="hibernate.cache.statistics" />
        <br/>  <span style="outline: none;color: #F95A5A;"><bean:message bundle="sys-cache" key="hibernate.cache.statistics.des" /></span></p>

		<center>
			<table class="tb_normal" width="85%" >
                <tr>
                    <td class="td_normal_title" style="text-align: center" width=15%>
                        <%--缓存统计开关--%>
                        <bean:message bundle="sys-cache" key="hibernate.sysHcacheEnabled" />
                    </td>
                    <td>
                        <ui:switch property="value(sysHcacheEnabled)" onValueChange="config_chgEnabled(this);" enabledText="${lfn:message('sys-cache:btn.enable')}" disabledText="${lfn:message('sys-cache:btn.disable')}">
                        </ui:switch>
                    </td>
                </tr>
                <tr id="refer_enable_row">
                    <td class="td_normal_title" style="text-align: center" width=15%>
                        <%--自动刷新开关--%>
                        <bean:message bundle="sys-cache" key="hibernate.sysHcacheReferEnabled" />
                    </td>
                    <td>
                        <ui:switch property="value(referEnabled)" onValueChange="refer_chgEnabled(this);" enabledText="${lfn:message('sys-cache:btn.enable')}" disabledText="${lfn:message('sys-cache:btn.disable')}">
                        </ui:switch>
                    </td>
                </tr>
                <tr id="refer_row">
                    <td class="td_normal_title" style="text-align: center" width=15%>
                        <%--刷新频率--%>
                        <bean:message bundle="sys-cache" key="hibernate.sysHcacheReferTime" />
                    </td>
                    <td>
                        <xform:text property="value(referTime)" validators="digits"  style="width:10%" /><bean:message bundle="sys-cache" key="hibernate.sysHcacheReferTime.desc" />
                    </td>
                </tr>
            </table>
            <br>
            <table class="tb_normal" width="85%" id="dataTableRow">
                <tr>
                    <td class="td_normal_title" style="text-align: center"><bean:message bundle="sys-cache" key="hibernate.sysHcacheEnabled.name" /></td>
                    <td class="td_normal_title" style="text-align: center"><bean:message bundle="sys-cache" key="hibernate.sysHcacheEnabled.des" /></td>
                </tr>
                <c:forEach var="cache" items="${cacheList}">
                    <c:forEach var="map" items="${cache}">
                        <tr class="tr_normal_title" style="text-align:left;cursor:pointer;">
                            <td width="15%" style="text-align: center">${map.key}</td>
                            <td width="85%" style="word-wrap:break-word;">
                                <%--${map.value}--%>
                                <table class="tb_normal" width="100%">
                                    <tr>
                                        <td class="td_normal_title" style="text-align: center"><bean:message bundle="sys-cache" key="hibernate.cache.regoinName" /></td>
                                        <td class="td_normal_title" style="text-align: center"><bean:message bundle="sys-cache" key="hibernate.cache.hitCount" /></td>
                                        <td class="td_normal_title" style="text-align: center"><bean:message bundle="sys-cache" key="hibernate.cache.missCount" /></td>
                                        <td class="td_normal_title" style="text-align: center"><bean:message bundle="sys-cache" key="hibernate.cache.putCount" /></td>
                                        <td class="td_normal_title" style="text-align: center"><bean:message bundle="sys-cache" key="hibernate.cache.elementCountInMemory" /></td>
                                        <td class="td_normal_title" style="text-align: center"><bean:message bundle="sys-cache" key="hibernate.cache.elementCountOnDisk" /></td>
                                        <td class="td_normal_title" style="text-align: center"><bean:message bundle="sys-cache" key="hibernate.cache.sizeInMemory" /></td>
                                    </tr>
                                    <c:if test="${map.value != null}">
                                        <c:forEach var="mapval" items="${map.value}">
                                            <tr class="tr_normal_title" style="text-align:left;cursor:pointer;">
                                                <td width="30%" style="text-align: center">${mapval.region}</td>
                                                <td width="10%" style="text-align: center">${mapval.hitCount}</td>
                                                <td width="10%" style="text-align: center">${mapval.missCount}</td>
                                                <td width="10%" style="text-align: center">${mapval.putCount}</td>
                                                <td width="15%" style="text-align: center">${mapval.elementCountInMemory}</td>
                                                <td width="15%" style="text-align: center">${mapval.elementCountOnDisk}</td>
                                                <td width="20%" style="text-align: center">${mapval.sizeInMemory}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${map.value == null}">
                                        <c:forEach var="mapval" items="${map.value}">
                                            <tr class="tr_normal_title" style="text-align:left;cursor:pointer;">
                                                <td colspan="7" width="30%" style="text-align: center">数据获取中...请等待!</td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </table>
			<div style="margin-bottom: 10px;margin-top:25px">
				  <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="commitMethod();"></ui:button>
			</div>
		</center>
	</div>
    <html:hidden property="method_GET" />
    <html:hidden property="modelName"  value="com.landray.kmss.sys.cache.hibernate.HibernateCacheStatisConfig"/>
</html:form>
<script>
    $KMSSValidation();
</script>
<script type="text/javascript">
 	function commitMethod(){
 			Com_Submit(document.sysAppConfigForm, 'update');
 	}

    LUI.ready(function() {
        config_chgEnabled();
        refer_chgEnabled();
    });

    function config_chgEnabled(){
        var isChecked = "true" == $("input[name='value\\\(sysHcacheEnabled\\\)']").val();
        var tableInfo = $("#dataTableRow");
        var refer_enable_row = $("#refer_enable_row");
        if (isChecked) {
            refer_enable_row.show();
            tableInfo.show();
        } else {
            refer_enable_row.hide();
            tableInfo.hide();
        }
        refer_chgEnabled();
    }

    function refer_chgEnabled(){
        var isChecked_s = "true" == $("input[name='value\\\(sysHcacheEnabled\\\)']").val();
        var isChecked = "true" == $("input[name='value\\\(referEnabled\\\)']").val();
        var refer_row = $("#refer_row");
        if (isChecked&&isChecked_s) {
            refer_row.show();
        } else {
            refer_row.hide();
        }
    }

</script>
</template:replace>
</template:include>

