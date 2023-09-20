<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page language="java"
	import="com.landray.kmss.framework.plugin.core.config.IExtension"%>
<%@page language="java"
	import="com.landray.kmss.framework.plugin.core.config.IExtensionPoint"%>
<%@page language="java"
	import="com.landray.kmss.framework.service.plugin.Plugin"%>
<%@page language="java" import="com.landray.kmss.util.StringUtil"%>

<%
	IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.kms.common.courseNotes");
	IExtension[] extensions = point.getExtensions();
	Boolean hasNotes = false;
	for (IExtension extension : extensions) {
		if ("basicInfo".equals(extension.getAttribute("name"))) {
			String fdModelName = Plugin.getParamValueString(extension, "fdModelName");
			//System.out.println(jspUrl);
			if (StringUtil.isNotNull(fdModelName)) {
				hasNotes = true;
				break;
			}

		}
	}
	request.setAttribute("hasNotes", hasNotes);
%>
<c:if test="${hasNotes}">

	<ui:combin ref="menu.nav.simple">
		<ui:varParam name="source">
			<ui:source type="Static">
				  					[{
					  					"text" : "${lfn:message('kms-common:module.kms.allNotes') }",
										"href" :  "javascript:lservice.navOpenPage('${LUI_ContextPath }/kms/common/kms_course_notes/teacher/index.jsp')",
					  					"icon" : "lui_iconfont_navleft_note_my"
					  				}]
			</ui:source>
		</ui:varParam>
	</ui:combin>
</c:if>
