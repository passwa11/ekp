<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.portal.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<table class="lux-portal-layout-table">
    <tr>
        <td style="width:33%">
            <ui:panel layout="sys.ui.panel.default" toggle="false"  height="240"  scroll="true">
                <ui:content title="默认单标签-简单列表平铺展现" toggle="false">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
            </ui:panel>
        </td>
        <td style="width:10px"></td>
        <td style="width:33%">
            <ui:panel layout="sys.ui.panel.light" toggle="false" height="240" scroll="true">
                <ui:content title="浅色单标签-公告牌">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.billboard" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
            </ui:panel>
        </td>
        <td style="width:10px"></td>
        <td style="width:33%">
            <ui:panel layout="sys.ui.panel.simple" toggle="false" height="240" scroll="true">
                <ui:content title="简约单标签-方格展现">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.default" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
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
        <td style="width:33%">
            <ui:panel layout="sys.ui.panel.vertical" height="240" scroll="true">
                <ui:content title="垂直单标签-封面图片">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.coverPicture" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
            </ui:panel>
        </td>
        <td style="width:10px"></td>
        <td style="width:33%">
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="true">
                <ui:content title="默认单标签-带图标" subtitle="NEWS" titleicon="iconfont_nav lui_iconfont_nav_sys_news">
                    <portal:portlet title="办件效率小助手">
                        <ui:dataview format="sys.ui.iframe">
                            <ui:source ref="dbcenter.flowstat.report.persontaskanalysis.source"></ui:source>
                            <ui:render ref="sys.ui.iframe.default" var-frameName=""></ui:render>
                        </ui:dataview>
                    </portal:portlet>
                </ui:content>
            </ui:panel>
        </td>
        <td style="width:10px"></td>
        <td style="width:33%">
            <ui:panel layout="sys.ui.panel.light" toggle="false" height="240" scroll="true">
                <ui:content title="浅色单标签-带图标 去除边距" subtitle="NEWS" cfg-extClass="lui_panel_ext_padding" titleicon="iconfont_nav lui_iconfont_nav_sys_news">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" subtitle="NEWS"
                                titleicon="iconfont_nav lui_iconfont_nav_sys_news" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
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
        <td style="width:33%">
            <ui:panel layout="sys.ui.panel.simple" toggle="false" height="240" scroll="true">
                <ui:content title="简约单标签" subtitle="NEWS" titleicon="iconfont_nav lui_iconfont_nav_sys_news">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
            </ui:panel>
        </td>
        <td style="width:10px"></td>
        <td style="width:33%">
            <ui:panel layout="sys.ui.nonepanel.transparent" height="240" scroll="true">
                <ui:content title="无标题有背景">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
            </ui:panel>
        </td>
        <td style="width:10px"></td>
        <td style="width:33%">
            <ui:panel layout="sys.ui.nonepanel.default" height="240" scroll="true">
                <ui:content title="无标题透明">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
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