<%@page import="com.landray.kmss.kms.lservice.util.ConfigUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<%
	if (ConfigUtil.refsEnable()) {
%>
<ui:content
	title="${lfn:message('kms-lservice:lserviceProducer.refs') }">


	<list:listview channel="refs">

		<list:colTable rowHref="!{url}" cfg-norecodeLayout="simple">

			<ui:source type="AjaxJson">
				{url:'/kms/lservice/Lservice.do?method=refs&fdModelName=${JsParam.modelName }&fdId=${JsParam.modelId}'}
			</ui:source>

			<list:col-serial />
			<list:col-auto props="docSubject,module,docCreateTime" />

		</list:colTable>

	</list:listview>

	<list:paging channel="refs"></list:paging>

</ui:content>

<%
	}
%>
