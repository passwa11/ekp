<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/integrateIndex.css?s_cache=${LUI_Cache}">
	</template:replace>
	<template:replace name="body">
		<ui:dataview>
			<ui:source type="AjaxJson">
				{url:'/sys/profile/sys_profile_main/sysCfgProfileConfig.do?method=data&type=integrate'}
			</ui:source>
			<ui:render type="Template">
				var keys = '';
				for(var i = 0; i < data.length;i++){
					keys += data[i].key + ';';
				}
				{$
					<div class="lui_profile_integrate_overview_wrap">
						<div class="lui_profile_integrate_overview_center">
							<div class="lui_profile_integrate_overview_circle">
								<div class="lui_profile_integrate_overview_logo"></div>
							</div>
						</div>
						<div class="lui_profile_integrate_overview_textRight">
							<div class="lui_profile_integrate_overview_item item01">
								<h4><bean:message bundle="sys-profile" key="sys.profile.integrate.im"/></h4>
							</div>
							<div class="lui_profile_integrate_overview_item item02">
								<h4><bean:message bundle="sys-profile" key="sys.profile.integrate.other"/></h4>
							</div>
							<div class="lui_profile_integrate_overview_item item03">
								<h4><bean:message bundle="sys-profile" key="sys.profile.integrate.user"/></h4>
							</div>
							<div class="lui_profile_integrate_overview_item item04">
								<h4><bean:message bundle="sys-profile" key="sys.profile.integrate.mail"/></h4>
							</div>
						</div>
						<div class="lui_profile_integrate_overview_textLeft">
							<div class="lui_profile_integrate_overview_item item05">
								<h4><bean:message bundle="sys-profile" key="sys.profile.integrate.report"/></h4>
							</div>
							<div class="lui_profile_integrate_overview_item item06">
								<h4><bean:message bundle="sys-profile" key="sys.profile.integrate.webservice"/></h4>
							</div>
							<div class="lui_profile_integrate_overview_item item07">
								<h4><bean:message bundle="sys-profile" key="sys.profile.integrate.tib"/></h4>
							</div>
							<div class="lui_profile_integrate_overview_item item08">
								<h4><bean:message bundle="sys-profile" key="sys.profile.integrate.saas"/></h4>
							</div>
						</div>
					</div>
					
				$}
			</ui:render>
		</ui:dataview>
	</template:replace>
</template:include>	