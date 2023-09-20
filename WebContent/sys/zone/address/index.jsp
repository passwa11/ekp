<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.zone.model.SysZonePrivateConfig"%>
<%
	SysZonePrivateConfig sysZonePrivateConfig = new SysZonePrivateConfig();
	pageContext.setAttribute("hideQrCode", sysZonePrivateConfig.hideQrCode());
%>
<template:include file="/sys/zone/address/zoneAddressTemplate.jsp">
	<template:replace name="title">${lfn:message('sys-zone:sysZonePerson.address.list.zone')}</template:replace>
	<template:replace name="head">
		<script>Com_IncludeFile("data.js");</script>
		<link rel="stylesheet" type="text/css"
			href="${ LUI_ContextPath}/sys/zone/address/resource/css/zoneAddress_common.css?s_cache=${LUI_Cache }" />
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/ui/extend/theme/default/style/module.css?s_cache=${LUI_Cache }" />
		<script type="text/javascript">
			LUI.ready(function() {
				seajs.use([ 'lui/jquery',
						'sys/zone/address/resource/js/zoneAddress' ],
						function($, zoneAddress) {
							var addressParams={
									<kmss:authShow roles="ROLE_SYSZONE_ADMIN">
									auth:1,
									</kmss:authShow>
									elem:'#zoneAddress',
									hideQrCode:'${pageScope.hideQrCode}'
							};
							var _zoneAddress=new zoneAddress.ZoneAddress(addressParams);
							_zoneAddress.draw();
						});
			});
		</script>
	</template:replace>
	<%-- 当前路径 --%>
	<c:if test="${empty param['j_iframe']}">
		<template:replace name="path">
			<ui:menu layout="sys.ui.menu.nav">
				<ui:menu-item text="${lfn:message('home.home')}" href="/index.jsp"
					icon="lui_icon_s_home" />
				<ui:menu-item text="${lfn:message('sys-zone:module.sys.zone') }"
					href="/sys/zone" target="_self" />
				<ui:menu-item
					text="${lfn:message('sys-zone:sysZonePerson.address.list') }" />
			</ui:menu>
		</template:replace>
	</c:if>
	
	<template:replace name="content">
		<div class="lui_zone_address_containter" id="zoneAddress" top="${fn:escapeXml(param.top)}"></div>
	</template:replace>
</template:include>