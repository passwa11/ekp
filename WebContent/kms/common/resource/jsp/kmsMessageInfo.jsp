<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
Com_RegisterFile(KMS.kmsResourcePath+"/jsp/"+"kmsMessageInfo.jsp");

if(typeof Kms_MessageInfo == "undefined")
	Kms_MessageInfo = new Array();
if(Kms_MessageInfo.length==0) {
	//#common.js
	Kms_MessageInfo["kms.common.search.input"]="<bean:message bundle="kms-common" key="kms.common.search.input" source="js" />";
	Kms_MessageInfo["kms.common.search.empty"]="<bean:message bundle="kms-common" key="kms.common.search.empty" source="js" />";
	Kms_MessageInfo["kms.common.logout"]="<bean:message bundle="kms-common" key="kms.common.logout" source="js" />";
	Kms_MessageInfo["kms.common.closeAlter"]="<bean:message bundle="kms-common" key="kms.common.closeAlter" source="js" />";
	Kms_MessageInfo["button.enter"]="<bean:message bundle="kms-common" key="button.enter" source="js" />";

	//#filter.js
	Kms_MessageInfo["kms.filter.caseSelect"]="<bean:message bundle="kms-common" key="kms.filter.caseSelect" source="js" />";
	Kms_MessageInfo["kms.filter.caseSelect"]="<bean:message bundle="kms-common" key="kms.filter.caseSelect" source="js" />";
	Kms_MessageInfo["kms.filter.query"]="<bean:message bundle="kms-common" key="kms.filter.query" source="js" />";
	Kms_MessageInfo["kms.filter.multiple"]="<bean:message bundle="kms-common" key="kms.filter.multiple" source="js" />";
	Kms_MessageInfo["kms.filter.onlySelect"]="<bean:message bundle="kms-common" key="kms.filter.onlySelect" source="js" />";
	Kms_MessageInfo["kms.filter.noLimit"]="<bean:message bundle="kms-common" key="kms.filter.noLimit" source="js" />";
	Kms_MessageInfo["kms.filter.more"]="<bean:message bundle="kms-common" key="kms.filter.more" source="js" />";
	Kms_MessageInfo["kms.filter.moreSelect"]="<bean:message bundle="kms-common" key="kms.filter.moreSelect" source="js" />";
	Kms_MessageInfo["kms.filter.input"]="<bean:message bundle="kms-common" key="kms.filter.input" source="js" />";
	Kms_MessageInfo["kms.filter.number"]="<bean:message bundle="kms-common" key="kms.filter.number" source="js" />";
	Kms_MessageInfo["kms.filter.enter"]="<bean:message bundle="kms-common" key="kms.filter.enter" source="js" />";
	Kms_MessageInfo["kms.filter.select"]="<bean:message bundle="kms-common" key="kms.filter.select" source="js" />";
	Kms_MessageInfo["kms.filter.folded"]="<bean:message bundle="kms-common" key="kms.filter.folded" source="js" />";
	Kms_MessageInfo["kms.filter.rightNumber"]="<bean:message bundle="kms-common" key="kms.filter.rightNumber" source="js" />";

	//#kms_navi_selector
	Kms_MessageInfo["kms.navi.noSelect"]="<bean:message bundle="kms-common" key="kms.navi.noSelect" source="js" />";
	Kms_MessageInfo["kms.navi.left"]="<bean:message bundle="kms-common" key="kms.navi.left" source="js" />";
	Kms_MessageInfo["kms.navi.right"]="<bean:message bundle="kms-common" key="kms.navi.right" source="js" />";
	Kms_MessageInfo["kms.navi.category"]="<bean:message bundle="kms-common" key="kms.navi.category" source="js" />";
	Kms_MessageInfo["kms.navi.load"]="<bean:message bundle="kms-common" key="kms.navi.load" source="js" />";
	Kms_MessageInfo["kms.navi.msg"]="<bean:message bundle="kms-common" key="kms.navi.msg" source="js" />";
	Kms_MessageInfo["category.noLimits"]="<bean:message bundle="kms-common" key="category.noLimits" source="js" />";

	//#kms_opera
	//Kms_MessageInfo["category.noLimits"]="<bean:message bundle="kms-common" key="category.noLimits" source="js" />";
	Kms_MessageInfo["kms.opera.selectCategory"]="<bean:message bundle="kms-common" key="kms.opera.selectCategory" source="js" />";
	Kms_MessageInfo["category.select"]="<bean:message bundle="kms-common" key="category.select" source="js" />";
	Kms_MessageInfo["kms.opera.selectCategory"]="<bean:message bundle="kms-common" key="kms.opera.selectCategory" source="js" />";
	Kms_MessageInfo["kms.opera.noSelectData"]="<bean:message bundle="kms-common" key="kms.opera.noSelectData" source="js" />";
	Kms_MessageInfo["kms.opera.deleteMsg"]="<bean:message bundle="kms-common" key="kms.opera.deleteMsg" source="js" />";
	Kms_MessageInfo["kms.opera.deleteError"]="<bean:message bundle="kms-common" key="kms.opera.deleteError" source="js" />";
}
