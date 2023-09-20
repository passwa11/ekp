<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="template.t">
	<template:replace name="aside">
		<link rel="stylesheet" href="../css/help-theme.css">
		<%@ include file="../jsp/nav.jsp" %>
		<%@ include file="../jsp/changeheader.jsp" %>
		<%@ include file="../jsp/changetemplate.jsp" %>
		<%@ include file="../jsp/usertitle.jsp" %>
		<%@ include file="../jsp/side-nav.jsp" %>
	</template:replace>
	<template:replace name="content">
		<table class="lux-portal-layout-table">
			<tr>
				<td style="width:70%">
					<ui:tabpanel height="370" scroll="true" layout="sys.ui.tabpanel.vertical">
						<ui:content title="待办">
							<portal:portlet title="待办" subtitle="" titleicon="" var-sortType="datetime" var-rowSize="6"
								var-showDocCreator="true" var-showFdCreateTime="true" var-target="_blank">
								<ui:dataview format="sys.ui.iframe">
									<ui:source ref="sys.notify.simple.todo.source" var-sortType="datetime"
										var-rowSize="6" var-showDocCreator="true" var-showFdCreateTime="true"
										var-target="_blank"></ui:source>
									<ui:render ref="sys.ui.iframe.default" var-frameName=""></ui:render>
									<ui:var name="showNoDataTip" value="true"></ui:var>
									<ui:var name="showErrorTip" value="true"></ui:var>
								</ui:dataview>
							</portal:portlet>
							<ui:operation name="更多" type="more" href="#" />
						</ui:content>
						<ui:content title="待阅">
							<portal:portlet title="待阅" subtitle="" titleicon="" var-sortType="datetime" var-rowSize="6"
								var-showDocCreator="true" var-showFdCreateTime="true" var-target="_blank">
								<ui:dataview format="sys.ui.iframe">
									<ui:source ref="sys.notify.simple.todo.source" var-sortType="datetime"
										var-rowSize="6" var-showDocCreator="true" var-showFdCreateTime="true"
										var-target="_blank"></ui:source>
									<ui:render ref="sys.ui.iframe.default" var-frameName=""></ui:render>
									<ui:var name="showNoDataTip" value="true"></ui:var>
									<ui:var name="showErrorTip" value="true"></ui:var>
								</ui:dataview>
							</portal:portlet>
							<ui:operation name="更多" type="more" href="#" />
						</ui:content>
					</ui:tabpanel>
				</td>
				<td style="min-width:10px"></td>
				<td style="width:400px;">
					<ui:panel layout="sys.ui.nonepanel.transparent" height="370" scroll="false"
						id="p_371c6e52dd8be65efebc">
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
			</tr>
		</table>
		<div style="min-height: 10px;"></div>
		<table class="lux-portal-layout-table" style="width:100%">
			<tr>
				<td>
					<ui:panel layout="sys.ui.nonepanel.transparent" height="68" scroll="false">
						<ui:content title="快捷方式">
							<template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.picMenu"
								render="sys.ui.picMenu.default" showMore="true"/>
						</ui:content>
					</ui:panel>
				</td>
			</tr>
		</table>
		<div style="min-height: 10px;"></div>
		<table class="lux-portal-layout-table" style="width:100%">
			<tr>
				<td style="width:557px;">
					<ui:panel layout="sys.ui.nonepanel.transparent" height="240" scroll="false">
						<ui:content title="幻灯片新闻">
							<template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.slide"
								render="sys.ui.slide.default" />
						</ui:content>
					</ui:panel>
				</td>
				<td style="min-width:10px"></td>
				<td style="width:30%">
					<ui:tabpanel height="240" scroll="true" layout="sys.ui.tabpanel.default">
						<ui:content title="最新发文">
							<template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
								<template:replace name="render">
									<ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
								</template:replace>
							</template:include>
							<ui:operation name="更多" type="more" href="#" />
						</ui:content>
						<ui:content title="最新制度">
							<template:include file="/sys/ui/help/dataview/dataview-demo.jsp" format="sys.ui.classic">
								<template:replace name="render">
									<ui:render ref="sys.ui.classic.tile" var-highlight="true" var-newDay="2" />
								</template:replace>
							</template:include>
							<ui:operation name="更多" type="more" href="#" />
						</ui:content>
					</ui:tabpanel>
				</td>
				<td style="min-width:10px"></td>
				<td style="width:30%">
					<ui:panel layout="sys.ui.panel.light" toggle="false" height="240" scroll="false">
						<ui:content title="任务管理">
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
		</table>
	</template:replace>
</template:include>