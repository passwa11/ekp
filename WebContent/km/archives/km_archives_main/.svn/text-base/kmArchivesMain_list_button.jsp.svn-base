<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>Com_Parameter.IsAutoTransferPara = true;</script>
<script language="JavaScript">
	Com_IncludeFile("dialog.js");
</script>
<%--bookmark--%>
<c:import url="/sys/bookmark/include/bookmark_bar_all.jsp"
	charEncoding="UTF-8">
	<c:param name="fdTitleProName" value="docSubject" />
	<c:param name="fdModelName"
		value="com.landray.kmss.km.archives.model.KmArchivesMain" />
</c:import>
<c:import
	url="/resource/jsp/search_bar.jsp"
	charEncoding="UTF-8">
	<c:param
		name="fdModelName"
		value="com.landray.kmss.km.archives.model.KmArchivesMain" />
</c:import>
<c:import
	url="/sys/right/doc_right_change_button.jsp"
	charEncoding="UTF-8">
	<c:param
		name="modelName"
		value="com.landray.kmss.km.archives.model.KmArchivesMain" />
</c:import>


<c:import
	url="/sys/simplecategory/include/doc_cate_change_button.jsp"
	charEncoding="UTF-8">
	<c:param
		name="modelName"
		value="com.landray.kmss.km.archives.model.KmArchivesMain" />
	<c:param
		name="docFkName"
		value="fdCategory" />
	<c:param
		name="cateModelName"
		value="com.landray.kmss.km.archives.model.KmArchivesCategory" />
</c:import>

<script type="text/javascript">

function dyniFrameSize() {
	try {
		// 调整高度
		var arguObj = document.getElementsByTagName("table")[0];
		if (arguObj != null && window.frameElement != null && window.frameElement.tagName == "IFRAME") {
			window.frameElement.style.height = (arguObj.offsetHeight + 50) + "px";
		}
	} catch (e) {}
}

window.onload=function(){
	var oldUrl = location.href;
	var docCreatorId = Com_GetUrlParameter(oldUrl,'docCreatorId');
	if(docCreatorId != null &&  docCreatorId != ''){
		document.getElementsByName("docCreatorId")[0].value = "${requestScope.docCreatorId}";
		document.getElementsByName("docCreatorName")[0].value = "${requestScope.docCreatorName}";
	}
	//文档标题
	var docSubject = Com_GetUrlParameter(oldUrl,'docSubject');
	if(docSubject != null &&  docSubject != '')
		document.getElementsByName("docSubject")[0].value = docSubject;
	//录入人
	var docCreatorId = Com_GetUrlParameter(oldUrl,'docCreatorId');
	if(docCreatorId != null &&  docCreatorId != ''){
		document.getElementsByName("docCreatorId")[0].value = "${requestScope.docCreatorId}";
		document.getElementsByName("docCreatorName")[0].value = "${requestScope.docCreatorName}";
	}
	//分类（选择）
	var fdDocTemplateId = Com_GetUrlParameter(oldUrl,'fdDocTemplateId');
	if(fdDocTemplateId != null &&  fdDocTemplateId != ''){
		document.getElementsByName("fdDocTemplateId")[0].value = "${requestScope.fdDocTemplateId}";
		document.getElementsByName("fdDocTemplateName")[0].value = "${requestScope.fdDocTemplateName}";
	}
	//录入时间（区间） 
	var docStartdate = Com_GetUrlParameter(oldUrl,'docStartdate');
	if(docStartdate != null &&  docStartdate != ''){
		document.getElementsByName("docStartdate")[0].value=docStartdate;
	}
	var docFinishdate = Com_GetUrlParameter(oldUrl,'docFinishdate');
	if(docFinishdate != null &&  docFinishdate != ''){
		document.getElementsByName("docFinishdate")[0].value=docFinishdate;
	}
	//文档状态 （下拉框）
	var docStatus = Com_GetUrlParameter(oldUrl,'docStatus');
	if(docStatus != null &&  docStatus != '')
		document.getElementsByName("docStatus")[0].value = docStatus;
};
</script>
<div id="optBarDiv">
<c:if test="${param.pink!='true'}">
	<kmss:authShow
		roles="ROLE_CERTIFICATE_CREATE">
		<c:if test="${empty param.categoryId}">
		<input type="button" value="<bean:message key="button.add"/>"
			onclick="Dialog_SimpleCategoryForNewFile('com.landray.kmss.km.archives.model.KmArchivesCategory','<c:url value="/km/archives/km_archives_main/kmArchivesMain.do" />?method=add&i.docTemplate=!{id}&fdTemplateName=!{name}');">
		</c:if>
		<c:if test="${not empty param.categoryId}">
		<c:set var="flg" value="no"/>
		<kmss:auth
		requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=add&fdTemplateId=${param.categoryId}"
		requestMethod="GET">
		<input type="button" value="<bean:message key="button.add"/>"
			onclick="Com_OpenWindow('<c:url value="/km/archives/km_archives_main/kmArchivesMain.do" />?method=add&i.docTemplate=${JsParam.categoryId}');">
		<c:set var="flg" value="yes"/>
		</kmss:auth>
		<c:if test="${flg eq 'no'}">
		<input type="button" value="<bean:message key="button.add"/>"
			onclick="Dialog_SimpleCategoryForNewFile('com.landray.kmss.km.archives.model.KmArchivesCategory','<c:url value="/km/archives/km_archives_main/kmArchivesMain.do" />?method=add&i.docTemplate=!{id}&fdTemplateName=!{name}');">
		</c:if>
		</c:if>
	</kmss:authShow>

	<kmss:auth
		requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=deleteall&status=${param.status}&categoryId=${param.categoryId}&nodeType=${param.nodeType}"
		requestMethod="GET">
		<input
			type="button"
			value="<bean:message key="button.deleteall"/>"
			onclick="if(!List_ConfirmDel())return;Com_Submit(document.kmArchivesMainForm, 'deleteall');">
	</kmss:auth>
</c:if> 
<c:if test="${param.pink=='true'}">
	<c:import
		url="/sys/introduce/include/sysIntroduceMain_cancelbtn.jsp"
		charEncoding="UTF-8">
		<c:param
			name="fdModelName"
			value="com.landray.kmss.km.archives.model.KmArchivesMain" />
	</c:import>
</c:if> 
<input
	type="button"
	value="<bean:message key="button.search"/>"
	onclick="Search_Show();"></div>
