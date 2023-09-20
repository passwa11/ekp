<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	var n1, n2, n3;
	n1 = LKSTree.treeRoot;
	n2 = n1.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-person:home.portlet')) }"
	);
	n3 = n2.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-person:home.mynav')) }",
		"<c:url value="/sys/person/sys_person_sysnav_category/sysPersonSysNavCategory.do?method=list" />"
	);
	n3 = n2.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-person:home.taball')) }",
		"<c:url value="/sys/person/sys_person_systab_category/sysPersonSysTabCategory.do?method=list&type=page" />"
	);
	<kmss:authShow roles="ROLE_SYSPERSON_SETTING_ADMIN">
	n3 = n2.AppendURLChild(
		"${lfn:escapeJs(lfn:message('sys-person:home.params')) }",
		"<c:url value="/sys/person/sys_person_config/sysPersonConfig.do?method=edit&modelName=com.landray.kmss.sys.person.model.SysPersonConfig" />"
	);
	</kmss:authShow>
	LKSTree.ExpandNode(n2);