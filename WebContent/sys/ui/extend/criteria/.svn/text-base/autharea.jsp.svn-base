<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<list:cri-criterion title="${lfn:message('sys-ui:ui.criteria.autharea') }" expand="false" key="${criterionAttrs['key']}">
	<list:box-select>
		<list:item-select>
			<ui:source type="AjaxJson">
				{url:'/sys/authorization/sys_auth_area/sysAuthArea.do?method=getAccessibleArea'}
			</ui:source>
		</list:item-select>
	</list:box-select>
</list:cri-criterion>