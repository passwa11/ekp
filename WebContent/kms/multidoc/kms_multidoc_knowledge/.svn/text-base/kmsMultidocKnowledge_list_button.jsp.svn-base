<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>Com_Parameter.IsAutoTransferPara = true;</script>
<script language="JavaScript">
	  Com_IncludeFile("dialog.js");
</script>
<%--doc list button show bookmark--%>
<c:import url="/sys/bookmark/include/bookmark_bar_all.jsp"
	charEncoding="UTF-8">
	<c:param name="fdTitleProName" value="docSubject" />
	<c:param name="fdModelName"
		value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
</c:import>
<c:import
	url="/resource/jsp/search_bar.jsp"
	charEncoding="UTF-8">
	<c:param
		name="fdModelName"
		value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
</c:import>
<c:import
	url="/sys/right/doc_right_change_button.jsp"
	charEncoding="UTF-8">
	<c:param
		name="modelName"
		value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
</c:import>

<c:import
	url="/sys/simplecategory/include/doc_cate_change_button.jsp"
	charEncoding="UTF-8">
	<c:param
		name="modelName"
		value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
	<c:param
		name="docFkName"
		value="docCategory" />
	<c:param
		name="cateModelName"
		value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />
	<c:param
		name="extProps"
		value="fdTemplateType:1;fdTemplateType:3" />
</c:import>

<script type="text/javascript">

function updateExtendFilePath(fdIds,templateId){
    //ajax 
	var url="kmsMultidocKnowledgeXMLService&type=4&docIds="+fdIds+"&templateId="+templateId ;
	var data = new KMSSData(); 
	data.SendToBean(url,function (rtnData){ 
		 var obj = rtnData.GetHashMapArray()[0]; 
 		 var count=obj['count'];
 		 if(count==0){
 	 		 alert('操作成功') ;
 	 	 }else{
 	 		alert('操作失败') ;
         }
	});	  
}
	
function checkSelect() {
	var values="";
	var selected;
	var select = document.getElementsByName("List_Selected");
	for(var i=0;i<select.length;i++) {
		if(select[i].checked){
			values+=select[i].value;
			values+=",";
			selected=true;
		}
	}
	if(selected) {
		values = values.substring(0,values.length-1);
		if(selected) {
			Com_OpenWindow(Com_Parameter.ContextPath+'kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge_change_template.jsp?method=changeTemplate&values='+values,'_blank','height=300, width=450, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
			return;
		}
	}
	alert("<bean:message bundle="kms-multidoc" key="message.trans_doc_select" />");
	return false;
}

</script>
<div id="optBarDiv">
<c:if test="${param.pink!='true'}">
	
	<c:if test="${not empty param.categoryId}">
		<kmss:auth
		requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add&fdTemplateId=${param.categoryId}"
		requestMethod="GET">
			<c:set var="flg" value="no"/>
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do" />?method=add&fdTemplateId=${param.categoryId}');">
		</kmss:auth>
	</c:if>

	<kmss:auth
		requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=deleteall&status=${param.status}&categoryId=${param.categoryId}&nodeType=${param.nodeType}"
		requestMethod="GET">
		<input
			type="button"
			value="<bean:message bundle="kms-knowledge" key="kmsKnowledge.button.delete"/>"
			onclick="if(!List_ConfirmDel())return;Com_Submit(document.kmsMultidocKnowledgeForm, 'deleteall');">
	</kmss:auth>
</c:if> 
<c:if test="${param.pink=='true'}">
	<c:import
		url="/sys/introduce/include/sysIntroduceMain_cancelbtn.jsp"
		charEncoding="UTF-8">
		<c:param
			name="fdModelName"
			value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
	</c:import>
</c:if> 
<input
	type="button"
	value="<bean:message key="button.search"/>"
	onclick="Search_Show();">
 
 </div>
