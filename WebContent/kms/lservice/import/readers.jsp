<%@page import="com.landray.kmss.kms.lservice.util.ConfigUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<%
	if (ConfigUtil.authEnable() && ConfigUtil.readersEnable()) {
%>

<ui:content
	title="${lfn:message('kms-lservice:lserviceProducer.readers') }">


	<list:listview channel="readers">

		<list:colTable rowHref="!{url}" cfg-norecodeLayout="simple">

			<ui:source type="AjaxJson">
				{url:'/kms/lservice/Lservice.do?method=readers&fdModelName=${JsParam.modelName }&fdId=${JsParam.modelId}'}
			</ui:source>

			<list:col-serial />
			<list:col-auto props="subject,module,reader" />

		</list:colTable>

	</list:listview>

	<list:paging channel="readers"></list:paging>

</ui:content>

<%
	}
%>
