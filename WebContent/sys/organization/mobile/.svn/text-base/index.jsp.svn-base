<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.organization.util.SysOrgEcoUtil"%>

<%
	if(SysOrgEcoUtil.IS_ENABLED_ECO) {
%>
<template:include ref="mobile.list">
	<template:replace name="title">
		${ lfn:message('sys-organization:sysOrgElement.ecoDefName') }
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/organization/mobile/css/index/index.css">
    </template:replace>
	<template:replace name="content">
		<div class="muiSysOrgEcoIndexContainer">
			<div class="muiSysOrgEcoIndexMargin">
				<div class="muiSysOrgEcoIndexTitle">
					<div class="muiSysOrgEcoIndexLabel">${ lfn:message('sys-organization:sysOrgElement.ecoDefName') }</div>
					<div class="muiSysOrgEcoIndexMessage">${ lfn:message('sys-organization:sysOrgElement.desc') }</div>
				</div>
				
				<div class="muiSysOrgEcoIndexCard"
					 data-dojo-type="sys/organization/mobile/js/index/muiOrgIndexCard"
					 data-dojo-props="isExternal:'false',isExt:true"></div>
				
				<div class="muiSysOrgEcoItem"
					 data-dojo-type="sys/organization/mobile/js/index/muiOrgIndexItem"
					 data-dojo-props="label:'${ lfn:message('sys-organization:sysOrgElementExternal.auth') }',href:'/sys/authorization/mobile/template/index.jsp',desc:'${ lfn:message('sys-organization:sysOrgElementExternal.auth.desc') }',icon:'auto'">
				</div>
				<kmss:ifModuleExist path="/km/review/">
					<kmss:authShow roles="ROLE_KMREVIEW_BACKSTAGE_MANAGER">
						<div class="muiSysOrgEcoItem"
							 data-dojo-type="sys/organization/mobile/js/index/muiOrgIndexItem"
							 data-dojo-props="label:'${ lfn:message('sys-organization:sysOrgElementExternal.lbpm') }',href:'/km/review/km_review_template/kmReviewTemplate.do?method=index',desc:'${ lfn:message('sys-organization:sysOrgElementExternal.lbpm.desc') }',icon:'lbpm'">
						</div>
					</kmss:authShow>
				</kmss:ifModuleExist>
			</div>
		</div>
	</template:replace>
</template:include>
<%
	} else {
%>
<template:include ref="mobile.list">
	<template:replace name="content">
		<div style="text-align: center;padding: 20px;">
			未开启生态组织，暂时不支持！
		</div>
	</template:replace>
</template:include>
<%
	}
%>
