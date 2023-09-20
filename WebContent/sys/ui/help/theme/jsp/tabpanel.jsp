<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.portal.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<table class="lux-portal-layout-table">
    <tr>
        <td style="width:33%">
            <ui:tabpanel layout="sys.ui.tabpanel.default" height="240" scroll="true">
                <ui:content title="默认多标签">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
                <ui:content title="最新新闻">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
            </ui:tabpanel>
        </td>
        <td style="width:10px"></td>
        <td style="width:33%">
            <ui:tabpanel layout="sys.ui.tabpanel.light" height="240" scroll="true">
                <ui:content title="浅色多标签">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
                <ui:content title="最新新闻">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
            </ui:tabpanel>
        </td>
        <td style="width:10px"></td>
        <td>
            <ui:tabpanel layout="sys.ui.tabpanel.simple" height="240" scroll="true">
                <ui:content title="简约多标签">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
                <ui:content title="最新新闻">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
            </ui:tabpanel>
        </td>
    </tr>
    <tr>
        <td>
            <div style="height: 10px;"></div>
        </td>
    </tr>
    <tr>
        <td style="width:33%">
            <ui:tabpanel layout="sys.ui.tabpanel.vertical" height="240" scroll="true">
                <ui:content title="垂直多标签">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
                <ui:content title="最新新闻">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
            </ui:tabpanel>
        </td>
        <td style="width:10px"></td>
        <td style="width:33%">
            <ui:tabpanel layout="sys.ui.tabpanel.multiCollapse" height="240" scroll="true">
                <ui:content title="分组多标签">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
                <ui:content title="最新新闻">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
            </ui:tabpanel>
        </td>
        <td style="width:10px"></td>
        <td style="width:33%">
            <ui:tabpanel layout="sys.ui.tabpanel.sucktop" height="240" scroll="true">
                <ui:content title="吸顶多标签">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
                <ui:content title="最新新闻">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
            </ui:tabpanel>
        </td>
    </tr>
    <tr>
        <td>
            <div style="height: 10px;"></div>
        </td>
    </tr>
    <tr>
        <td style="width:33%">
            <ui:tabpanel layout="sys.ui.tabpanel.collapse" height="240" scroll="true">
                <ui:content title="折叠多标签">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
                <ui:content title="最新新闻">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
            </ui:tabpanel>
        </td>
        <td style="width:10px"></td>
        <td style="width:33%">
            <ui:tabpanel layout="sys.ui.tabpanel.border" height="240" scroll="true">
                <ui:content title="边框多标签">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
                <ui:content title="最新新闻">
                    <template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
                        <template:replace name="render">
                            <ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
                        </template:replace>
                    </template:include>
                    <ui:operation name="更多" type="more" href="#" />
                </ui:content>
            </ui:tabpanel>
        </td>
        <td style="width:10px"></td>
        <td style="width:33%">
            <ui:accordionpanel>
				<ui:content title="默认折叠多标签">
					<template:include file="/sys/ui/help/dataview/dataview-demo.jsp"
						format="sys.ui.cate" render="sys.ui.cate.default"/>
					<ui:operation name="更多"  type="more" href="#" />
				</ui:content>
				<ui:content title="系统导航">
					<template:include file="/sys/ui/help/dataview/dataview-demo.jsp"
                    format="sys.ui.sysnav" render="sys.ui.sysnav.default"/>
				</ui:content>
			</ui:accordionpanel>
        </td>
    </tr>
    <tr>
        <td>
            <div style="height: 10px;"></div>
        </td>
    </tr>
    
</table>