<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        '<bean:message bundle="third-wps" key="module.third.wps" />',
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    node.isExpanded = true;
     
     <%-- WPS集成配置--%>
    <kmss:authShow roles="ROLE_THIRDWPS_SETTING">
		var	node_1_1_node = node.AppendURLChild("<bean:message bundle="third-wps"
				key="py.WpsJiChengPeiZhi" />",
			"<c:url
				value="/third/wps/thirdWpsConfig.do?method=select&modelName=com.landray.kmss.third.wps.model.ThirdWpsConfig" />");
	</kmss:authShow>
	
	<%-- WPS WebOffice集成配置--%>
    <%-- <kmss:authShow roles="ROLE_THIRDWPS_SETTING">
		var	node_1_1_node = node.AppendURLChild(
		"WPS WebOffice集成配置",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.third.wps.model.ThirdWpsWebOfficeConfig" />"
	
		);
	</kmss:authShow> --%>
	
	 <%-- WPS中台配置--%>
    <kmss:authShow roles="ROLE_THIRDWPS_SETTING">
		var	node_1_1_node = node.AppendURLChild("WPS中台配置",
			"<c:url
				value="/third/wps/thirdWpsConfig.do?method=selectWPSCenter&modelName=com.landray.kmss.third.wps.model.ThirdWpsConfig" />");
	</kmss:authShow>
	<%-- 在线预览配置--%>
    <kmss:authShow roles="ROLE_THIRDWPS_SETTING">
		var	node_1_1_node = node.AppendURLChild(
		"<bean:message bundle="third-wps"
				key="thirdWps.preview.online.config" />",
		"<c:url value="/third/wps/thirdWpsConfig.do?method=selectPreview&modelName=com.landray.kmss.third.wps.model.ThirdWpsConfig" />"
	
		);
	</kmss:authShow>
	<%-- 在WPS移动版配置--%>
	<kmss:authShow roles="ROLE_THIRDWPS_SETTING">
		var	node_1_1_node = node.AppendURLChild(
		"<bean:message bundle="third-wps"
					   key="thirdWps.mobile.config" />",
		"<c:url value="/third/wps/thirdWpsConfig.do?method=selectWPSMobile&modelName=com.landray.kmss.third.wps.model.ThirdWpsConfig" />"

		);
	</kmss:authShow>
	
	<%-- WPS集成配置--%>
    <%-- <kmss:authShow roles="ROLE_THIRDWPS_SETTING">
		var	node_1_2_node = node.AppendURLChild("<bean:message bundle="third-wps"
				key="py.ZuZhiJiaGouTong" />",
			"<c:url value="/third/wps/third_wps_org_element/thirdWpsOrgElement.do?method=syncOrgStruct" />");
	</kmss:authShow> --%>
    
    LKSTree.Show();
}
</template:replace>
</template:include>