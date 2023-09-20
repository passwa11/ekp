<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.portal.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<table class="lux-portal-layout-table">
    <tr>
        <td style="width:49%">
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="false">
                <ui:content title="日程周月视图">
                    <portal:portlet title="日程周月视图" subtitle="" titleicon="" var-rowsize="6">
                        <ui:dataview format="sys.ui.html">
                            <ui:source ref="km.calendar.portlet.month.source" var-rowsize="6"></ui:source>
                            <ui:render ref="sys.ui.html.default"></ui:render>
                            <ui:var name="showNoDataTip" value="true"></ui:var>
                            <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                    </portal:portlet>
                    <ui:operation name="更多" type="more" href="#" />
                    <ui:operation name="新建日程" type="create" href="#" />
                </ui:content>
            </ui:panel>
        </td>
        <td style="width:10px"></td>
        <td style="width:49%">
            <ui:panel layout="sys.ui.panel.light" toggle="false" height="240" scroll="false">
                <ui:content title="日程左右视图">
                    <portal:portlet title="日程周月视图" subtitle="" titleicon="" var-rowsize="6">
                        <ui:dataview format="sys.ui.html">
                            <ui:source ref="km.calendar.portlet.direction.source" var-rowsize="6"></ui:source>
                            <ui:render ref="sys.ui.html.default"></ui:render>
                            <ui:var name="showNoDataTip" value="true"></ui:var>
                            <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                    </portal:portlet>
                    <ui:operation name="更多" type="more" href="#" />
                    <ui:operation name="新建日程" type="create" href="#" />
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
        <td style="width:49%">
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="false">
                <ui:content title="日程周月视图">
                    <portal:portlet title="日程周月视图" subtitle="" titleicon="" var-rowsize="6">
                        <ui:dataview format="sys.ui.html">
                            <ui:source ref="km.calendar.portlet.white.source" var-rowsize="6"></ui:source>
                            <ui:render ref="sys.ui.html.default"></ui:render>
                            <ui:var name="showNoDataTip" value="true"></ui:var>
                            <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                    </portal:portlet>
                    <ui:operation name="更多" type="more" href="#" />
                    <ui:operation name="新建日程" type="create" href="#" />
                </ui:content>
            </ui:panel>
        </td>
        <td style="width:10px"></td>
        <td style="width:49%">
            <ui:panel layout="sys.ui.panel.light" toggle="false" height="240" scroll="false">
                <ui:content title="新版日历">
                    <portal:portlet title="新版日历" subtitle="" titleicon="" var-rowsize="6">
                        <ui:dataview format="sys.ui.html">
                            <ui:source ref="km.calendar.portletx.source" var-rowsize="6"></ui:source>
                            <ui:render ref="sys.ui.html.default"></ui:render>
                            <ui:var name="showNoDataTip" value="true"></ui:var>
                            <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                    </portal:portlet>
                    <ui:operation name="更多" type="more" href="#" />
                    <ui:operation name="新建日程" type="create" href="#" />
                </ui:content>
            </ui:panel>
        </td>
    </tr>
    <tr>
        <td style="width:49%">
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="false">
                <ui:content title="旧版日历">
                    <portal:portlet title="日历" subtitle="" titleicon="">
                        <ui:dataview format="sys.ui.html">
                            <ui:source ref="km.calendar.portlet.source"></ui:source>
                            <ui:render ref="sys.ui.html.default"></ui:render>
                            <ui:var name="showNoDataTip" value="true"></ui:var>
                            <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                    </portal:portlet>
                    <ui:operation name="更多" type="more" href="#" />
                    <ui:operation name="新建笔记" type="create" href="#" />
                    <ui:operation name="新建日程" type="create" href="#" />
                </ui:content>
            </ui:panel>
        </td>
        <td style="width:10px"></td>
        <td style="width:49%">

        </td>
    </tr>
    <tr>
        <td>
            <div style="height:10px;"></div>
        </td>
    </tr>

</table>