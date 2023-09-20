<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.portal.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 其他组件 -->
<table class="lux-portal-layout-table">
    <tr>
        <td>
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="false">
                <ui:content title="办件效率小助手-默认">
                    <portal:portlet title="办件效率小助手">
                        <ui:dataview format="sys.ui.iframe">
                            <ui:source ref="dbcenter.flowstat.report.persontaskanalysis.source"></ui:source>
                            <ui:render ref="sys.ui.iframe.default" var-frameName=""></ui:render>
                        </ui:dataview>
                    </portal:portlet>
                </ui:content>
            </ui:panel>
        </td>
    </tr>
    <tr>
        <td>
            <div style="height: 10px;"></div>
        </td>
    </tr>
    <tr>
        <td>
        </td>
    </tr>

    <tr>
        <td>
            <div style="height: 10px;"></div>
        </td>
    </tr>

    <tr>
        <td>
        </td>
    </tr>
    <tr>
        <td>
            <div style="height: 10px;"></div>
        </td>
    </tr>
    <tr>
        <td>
        </td>
    </tr>
    <tr>
        <td>
            <div style="height: 10px;"></div>
        </td>
    </tr>
    
</table>