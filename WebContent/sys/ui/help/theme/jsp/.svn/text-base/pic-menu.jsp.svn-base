<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.portal.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 快捷方式 -->
<table class="lux-portal-layout-table">
    <tr>
        <td>
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="false">
                <ui:content title="快捷方式-图片菜单默认展现-全">
                    <ui:dataview format="sys.ui.picMenu" cfg-showMore="true">
                        <ui:source type="AjaxJson">
                            {url:'/sys/ui/help/theme/data/portlet-picMenu.jsp'}
                        </ui:source>
                        <ui:render ref="sys.ui.picMenu.default" cfg-showMore="true"></ui:render>
                    </ui:dataview>
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
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="false">
                <ui:content title="快捷方式-模块导航图片菜单">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.picMenu"
                        render="sys.ui.picMenu.module" />
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
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="false">
                <ui:content title="快捷方式-图标文本表格展现">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.picMenu"
                        render="sys.ui.picMenu.shortCut" />
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
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="false">
                <ui:content title="自适应快捷">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.picMenu"
                        render="sys.ui.picMenu.autoWorkRow" />
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
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="false">
                <ui:content title="快捷方式-文本菜单默认展现">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.picMenu"
                        render="sys.ui.textMenu.default" var-cols="3" />
                </ui:content>
            </ui:panel>
        </td>
    </tr>
    <tr>
        <td>
            <div style="height: 10px;"></div>
        </td>
    </tr>

</table>