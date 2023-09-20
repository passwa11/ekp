<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.list">
	<template:replace name="title">
		<bean:message bundle="sys-xform-maindata" key="tree.relation.jdbc.root"/>
	</template:replace>
	<template:replace name="head">
	   <link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/xform/maindata/mobile/resource/css/list.css" />
	   <link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/xform/maindata/main_data_show/mobile/css/mobilekeydata.css" />
	</template:replace>
	<template:replace name="content">
			<div data-dojo-type="mui/header/Header" fixed="top"
				data-dojo-mixins="sys/xform/maindata/mobile/resource/js/MainDataHeaderMixin"
				data-dojo-props="dataRequest:'/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=getMainDataInfo&fdId=${param.fdId}&modelName=${param.modelName}&defId=${param.defId}'">
				<div id="secpageTabs"
					class="mui_keydata_secpage_tabs"
					data-dojo-type="mui/nav/MobileCfgNavBar" 
					data-dojo-props="url:'/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=getMainDataShowRelates&modelName=${param.modelName}&fdId=${param.fdId}'">
				</div>
			</div>
			<div data-dojo-type="mui/list/iframe/NavSwapIframeView"></div>
	</template:replace>
</template:include>
