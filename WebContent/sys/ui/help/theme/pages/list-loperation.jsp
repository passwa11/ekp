<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.kms.loperation.util.PluginUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="java.util.*"%>
<template:include ref="default.list">
	<template:replace name="head">
        <%@ include file="../jsp/nav.jsp" %>
		<%@ include file="../jsp/changeheader.jsp" %>
		<%@ include file="../jsp/changelist.jsp" %>
		<link rel="stylesheet" href="./../css/help-theme.css">
		<link charset="utf-8" rel="stylesheet"
					href="${LUI_ContextPath}/kms/loperation/calculate/css/card.css" />
		<link charset="utf-8" rel="stylesheet"
			href="${ LUI_ContextPath}/kms/loperation/style/index.css" />
	</template:replace>
	<template:replace name="title">
		<c:out value="${lfn:message('kms-loperation:module.kms.loperation') }" />-<c:out value="${lfn:message('kms-loperation:caculate.index') }" />
	</template:replace>

	<template:replace name="nav">

			<ui:combin ref="menu.nav.simple.icon">
	
								<%
									List<Map<String, String>> navs = PluginUtil.getNavs();
									Integer a=0;
									for (int i = 0; i < navs.size(); i ++) {
											Map<String, String> nav = navs.get(i);
										
											String title = ResourceUtil.getString(nav.get("navMsg"));
											String navJsp = nav.get("navJsp");
											String varParam = a.toString();
											a++;
								%>
								<kmss:auth requestURL="<%=navJsp %>">
									<ui:varParam name="<%=varParam %>">
										<ui:source type="AjaxJson">
											{url:"<%=navJsp %>"}
										</ui:source>
								    </ui:varParam>
								</kmss:auth>
								<%
									}
								%>
						
			</ui:combin>	

	</template:replace>

	<template:replace name="content">
		<div class="luiLoperationContent" >
			<div class="clearfloat luiLoperationCards" style="margin-bottom: 0;">
				<ui:dataview>
					<ui:source type="AjaxJson">
						{"url" : "/kms/loperation/kms_loperation_count/kmsLoperationCount.do?method=getAllIndexCounts"}
					</ui:source>
					<ui:render type="Template">
						if(data && data.length > 0) {
							for(var i = 0; i < data.length; i ++) {
						{$
							<div class="lui-component luiLoperationCard" style="width:25%">
							 	<div class="lui_loperation_card clearfloat">
								 	<div class="lui_loperation_card_content">
								 		<div class="card_header">
								 			<span class="card_title">{%data[i].text%}</span>
								 			<div class="card_type">${lfn:message('kms-loperation:cartInfo.total') }</div>
								 		</div>
								 		<div class="card_content" style="padding-left: 0; text-align: center">
								 			<em class="report_num">{%data[i].count%}</em>
								 		</div>
								 	</div>
								 </div>
							</div>
						$}
							}
						}
					</ui:render>
				</ui:dataview>
			</div>
			
			<div style="padding: 5px;overflow: hidden;margin-left: -4px;">
				<%
					List<Map<String, String>> ps = PluginUtil.getIndexPortlets();
					for(int  i = 0 ; i < ps.size(); i ++) {
						Map<String, String> item = ps.get(i);
						
						String url = item.get("url");
						String width = item.get("widthType");
						String msg = item.get("messageText");
						
				%>	
				<kmss:auth requestURL="<%=url%>">
					<div class="lui_loperation_panel_inner lui_loperation_index_portlet lui_loperation_index_portlet_with<%=width%>">
						<c:import url="<%=url%>" charEncoding="UTF-8"/>
					</div>
				</kmss:auth>
				<% 
					}
				%>
			</div>
		</div>
		<%@ include file="/kms/loperation/calculate/index_js.jsp"%>
	</template:replace>

</template:include>