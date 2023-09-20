<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<%
	String fdModelName = request.getParameter("fdModelName");
	Set propertyNameSet =  SysDataDict.getInstance().getModel(fdModelName).getPropertyMap().keySet();
%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>


<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<%@ page import="com.landray.kmss.kms.knowledge.borrow.util.KmsKnowledgeBorrowUtil" %>
<c:set var="kmsCategoryEnabled" value="false"></c:set>	
<c:set var="tableKmsKnowledgeCategory" value="${lfn:message('kms-knowledge:table.kmsKnowledgeCategory') }"></c:set>	
<c:set var="msgHasExist" value="${lfn:message('kms-knowledge:msg.hasExist') }"></c:set>	
<c:set var="categoryInformation" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.edit.CategoryInformation') }"></c:set>
<c:if test="${kms_professional}">
	<%
		KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
		String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
		if ("true".equals(kmsCategoryEnabled)) {
	%>
		<c:set var="kmsCategoryEnabled" value="true"></c:set>
		<c:set var="tableKmsKnowledgeCategory" value="${lfn:message('kms-knowledge:table.kmsKnowledgeCategory.categoryTrue') }"></c:set>
		<c:set var="msgHasExist" value="${lfn:message('kms-knowledge:msg.hasExist.categoryTrue') }"></c:set>
		<c:set var="categoryInformation" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.edit.CategoryInformation.categoryTrue') }"></c:set>
	<%
		}
	%>
</c:if>
<kmss:windowTitle
	subjectKey="kms-knowledge:table.kmsKnowledgeCategory"
	moduleKey="kms-knowledge:table.kmKnowledge" />
<script language="JavaScript">
Com_IncludeFile("dialog.js");

//提交校验
Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
	
	
	//知识模版必须
	var isSelectedTemplate = false;
	$("input[name=_fdTemplateType]").each(function(){
		if(this.checked){
			isSelectedTemplate = true;
		}
	});
	if(!isSelectedTemplate){
		seajs.use([ 'lui/dialog'], function(dialog) {
		dialog.alert('<bean:message key="kmsKnowledgeCategory.noKnowledgeTemplate" bundle="kms-knowledge"/>');
		})
		return false; 
	}

	var fdvalue=document.getElementsByName("fdOrder")[0].value;
	var fdShowWorning=false;
	if(fdvalue!=""){
			if(isNaN(fdvalue)||Number(fdvalue)<0||parseInt(fdvalue, 10)!=fdvalue){
				fdShowWorning=true;
				} 
	}
	if(fdShowWorning){
		seajs.use([ 'lui/dialog'], function(dialog) {
		dialog.alert("<bean:message bundle='kms-common' key='category.orderSubmitValidate'/>");
		})
		return false;
	}
	
  	//提交前，校验类别名称唯一性
	if(checkMultidocName()){
		 return true ;
	}
		
	else 
		return false ;
	
}


function checkMultidocName(){
    var fdName=document.getElementsByName("fdName")[0].value ;
	var fdId='${kmsKnowledgeCategoryForm.fdId}';
	var parentId=$("input[name=fdParentId]").val(); 
	if(fdName != "" && fdName != null){
		fdName = encodeURIComponent(fdName);//防止url存在敏感字符冒号、正斜杠、问号和井号等
		var url="kmsKnowledgeCategoryCheckService&fdName="+fdName+"&fdId="+fdId+"&parentId="+parentId;
		var data = new KMSSData();
        try{
            var isExist =data.AddBeanData(url).GetHashMapArray()[0];
            if(isExist["key0"]=='false'){
                return true;
            }else{
                seajs.use([ 'lui/dialog'], function(dialog) {
                    dialog.alert('${msgHasExist}');
                })
                return false;
            }
        }catch (e) {
            seajs.use([ 'lui/dialog'], function(dialog) {
                dialog.alert("${lfn:message('kms-knowledge:kms.knowledge.noHtml.tip')}");
            })
			return false;
        }
	}
}

$(document).ready(function(){
	var val = $("input[name=fdTemplateType]").val();
	settingTemplates(val);
});


function settingTemplates(e){

	var multidocTemplate = $("#multidoc_TemplateName");
	var wikiTemplate = $("#wiki_TemplateName");
	if(~e.indexOf("1")){
		multidocTemplate.show();
	}else{
		multidocTemplate.hide();
	}
	if(~e.indexOf("2")){
		wikiTemplate.show();
	}else{
		wikiTemplate.hide();
	}
}

</script>
<html:form
	action="/kms/knowledge/kms_knowledge_category/kmsKnowledgeCategory.do">
		
	<c:import url="/sys/simplecategory/sys_simple_category/sysCategoryMain_edit_button.jsp"
		charEncoding="UTF-8">
		<c:param name="formName" value="kmsKnowledgeCategoryForm" />
	</c:import>

	<p class="txttitle">${tableKmsKnowledgeCategory }</p>

	<center>
	<table
		id="Label_Tabel"
		width="95%">
		<c:set var="selectEmpty" value="true" />
		<kmss:auth
			requestURL="${param.requestURL}"
			requestMethod="Get">
			<c:set var="selectEmpty" value="false" />
		</kmss:auth>
		<!-- 类别信息  -->
		<c:import url="/kms/knowledge/resource/ui/sysCategoryMain_edit.jsp"
			charEncoding="UTF-8">
			<c:param name="fdCopyId" value="${param.fdCopyId }"/>
			<c:param name="formName" value="kmsKnowledgeCategoryForm" />
			<c:param name="requestURL" value="kms/knowledge/kms_knowledge_category/kmsKnowledgeCategory.do?method=add" />
			<c:param name="fdModelName" value="${param.fdModelName}" />
			<c:param name="mainModelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" />
			<c:param name="cateTitle" value="${categoryInformation}" />
		</c:import>
		<tr LKS_LabelName="${lfn:message('kms-knowledge:kmsKnowledgeCategory.edit.docTemplate')}" > 
			<td>
			<table
				class="tb_normal"
				width=100%>
				<c:if test="${kms_professional}">
					<tr id="multidoc_TemplateName" >
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-knowledge" key="kmsKnowledgeCategory.docTemplate"/>
						</td><td colspan="3">
							<html:hidden property="docTemplateId" />
							<html:text property="docTemplateName" readonly="true"  styleClass="inputsgl" style="width:75%" />
							<a href="#" onclick="Dialog_List(false, 'docTemplateId', 'docTemplateName', null, 'kmsKnowledgeDocTemplateTree&type=child', null, 'kmsKnowledgeDocTemplateTree&type=search&key=!{keyword}', null, null, '<bean:message  bundle="kms-knowledge" key="table.kmsKnowledgeDocTemplate"/>');">
							<bean:message key="dialog.selectOther" />
							</a>
						</td>
					</tr>
					<tr id="wiki_TemplateName">
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-knowledge" key="kmsKnowledgeCategory.wikiTemplate"/>
						</td><td colspan="3">
							<html:hidden property="wikiTemplateId" />
							<html:text property="wikiTemplateName" readonly="true" styleClass="inputsgl" style="width:75%" />
							<a href="#" onclick="Dialog_List(false, 'wikiTemplateId', 'wikiTemplateName', null, 'kmsKnowledgeWikiTemplateTree&type=child', null, 'kmsKnowledgeWikiTemplateTree&type=search&key=!{keyword}', null, null, '<bean:message  bundle="kms-knowledge" key="table.kmsKnowledgeWikiTemplate"/>');">
							<bean:message key="dialog.selectOther" />
							</a>
						</td>
					</tr>
				</c:if>
				<!-- 标签机制 -->
				<c:import url="/sys/tag/import/sysTagTemplate_edit.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="kmsKnowledgeCategoryForm" />
					<c:param name="fdKey" value="mainDoc" />
					<c:param name="diyTitle" value="默认关键字" />
                    <c:param name="readonly" value="true"/>
				</c:import>
			</table>
			</td>
		</tr>
		
		<%----编号机制开始--%>
		<% if(com.landray.kmss.sys.number.util.NumberResourceUtil.isModuleNumberEnable("com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc")){ %>
			<c:import url="/sys/number/include/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsKnowledgeCategoryForm" />
				<c:param name="modelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"/>
			</c:import>
		<%} %>
		<%----编号机制结束--%>
		
		<!-- 流程设置  -->
		<c:import url="/sys/workflow/include/sysWfTemplate_edit.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="kmsKnowledgeCategoryForm" />
			<c:param name="fdKey" value="mainDoc" /> 
			<c:param name="diyTitle" value="设置" /> 
		</c:import>
		
		<%--关联机制--%>
		<tr LKS_LabelName="${lfn:message('kms-knowledge:kmsKnowledgeCategory.edit.knowledgeRelationSetting') }">
		<!--  bean:message bundle="sys-relation" key="sysRelationMain.tab.label"  -->
			<c:set
				var="mainModelForm"
				value="${kmsKnowledgeCategoryForm}"
				scope="request" />
			<c:set
				var="currModelName"
				value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
				scope="request" />
			<td><%@ include file="/sys/relation/include/sysRelationMain_edit.jsp"%></td>
		</tr>

		<%----发布机制开始--%>
		<c:import url="/sys/news/include/sysNewsPublishCategory_edit.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="kmsKnowledgeCategoryForm" />
			<c:param name="fdKey" value="mainDoc" /> 
			<c:param name="messageKey"
					value="kms-knowledge:kmsKnowledgeCategory.lbl.publish" />
		</c:import>
		<%----发布机制结束--%>

		<c:if test="${kms_professional}">
			<kmss:ifModuleExist path="/kms/learn">
				<kmss:ifModuleExist path="/kms/multidoc">
					<%-- 发布到课件 --%>
					<c:import url="/kms/learn/kms_learn_courseware/import/KmsLearnCoursewarePublishCategory_edit.jsp"
							  charEncoding="UTF-8">
						<c:param name="formName" value="kmsKnowledgeCategoryForm" />
						<c:param name="fdKey" value="mainDoc" />
					</c:import>
				</kmss:ifModuleExist>
			</kmss:ifModuleExist>
		</c:if>
		<%----权限--%>
		<tr LKS_LabelName="${lfn:message('kms-knowledge:kmsKnowledgeCategory.edit.RightSetting') }">
			<td>
			<table
				class="tb_normal" 
				width=100%>
				<c:import
					url="/sys/right/tmp_right_edit.jsp"
					charEncoding="UTF-8">
					<c:param
						name="formName"
						value="kmsKnowledgeCategoryForm" />
					<c:param
						name="moduleModelName"
						value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />
				</c:import>
			</table>
			</td>
		</tr>

		<c:if test="${kms_professional}">
			<!-- 纠错流程设置  -->
			<c:import url="/sys/workflow/include/sysWfTemplate_edit.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="kmsKnowledgeCategoryForm" />
				<c:param name="fdKey" value="knowledgeErrorCorrectionFlow" />
				<c:param name="diyTitle" value="设置" />
				<c:param name="messageKey"  value="kms-knowledge:kmsKnowledgeCategory.kmsCommonDocErrorCorrectionFlow" />
			</c:import>
		</c:if>
		<!-- 推荐流程设置  -->
		<c:import url="/sys/workflow/include/sysWfTemplate_edit.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="kmsKnowledgeCategoryForm" />
			<c:param name="fdKey" value="introDoc" /> 
			<c:param name="messageKey"  value="kms-knowledge:kmsKnowledgeCategory.introFlow" />
		</c:import>

		<!-- 规则机制 -->
		<c:if test="${kms_professional}">
		    <c:set var="knowledgeMsg" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.knowledge.msg1') }"></c:set>
		    <c:if test="${borrowEnabled}">
		        <c:set var="knowledgeMsg" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.knowledge.msg1') };${lfn:message('kms-knowledge:kmsKnowledgeCategory.knowledge.msg3') }"></c:set>
		    </c:if>
			<c:import url="/sys/rule/sys_ruleset_temp/sysRuleTemplate_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsKnowledgeCategoryForm" />
				<c:param name="fdKey" value="mainDoc;knowledgeErrorCorrectionFlow;introDoc${borrowEnabled ? ';kmsKnowledgeCategoryBorrow' : ''}" />
				<c:param name="messageKey" value="${knowledgeMsg}" />
				<c:param name="templateModelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory"></c:param>
			</c:import>
		</c:if>
		<c:if test="${!kms_professional}">
			<c:import url="/sys/rule/sys_ruleset_temp/sysRuleTemplate_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsKnowledgeCategoryForm" />
				<c:param name="fdKey" value="mainDoc;introDoc" />
				<c:param name="messageKey" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.knowledge.msg2') }" />
				<c:param name="templateModelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory"></c:param>
			</c:import>
		</c:if>

		<c:if test="${kms_professional}">
			<!-- 借阅申请流程  -->
			<c:if test="${borrowEnabled}">
				<c:import url="/sys/workflow/include/sysWfTemplate_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsKnowledgeCategoryForm" />
					<c:param name="fdKey" value="kmsKnowledgeCategoryBorrow" />
					<c:param name="messageKey"  value="kms-knowledge:kmsKnowledgeCategory.borrowFlow" />
				</c:import>
				<!-- 附件权限申请流程  -->
				<c:import url="/sys/workflow/include/sysWfTemplate_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsKnowledgeCategoryForm" />
					<c:param name="fdKey" value="kmsKnowledgeBorrowAttAuth" />
					<c:param name="messageKey"  value="kms-knowledge:kmsKnowledgeCategory.borrowAttachmentFlow" />
				</c:import>
			</c:if>
		</c:if>
	</table>
	</center>
	<html:hidden property="fdId" />
	<html:hidden property="method_GET" />
	
	
</html:form>

<%@ include file="/resource/jsp/edit_down.jsp"%>
<script>
    function loadExtendPropertyData(modelName) {
        var proTemplateId = $("input[name=fdSysPropTemplateId]").val();
		if (!modelName || "com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" != modelName){
			// 非知识仓库主model不能加载属性
			proTemplateId = "";
		}
        if(!proTemplateId || proTemplateId.length == 0){
			// 如果没有属性模板，就传值""，避免出现undefined
			proTemplateId = "";
        }
        return new KMSSData().AddBeanData("kmsKnowledgeFormulaDictVarTree&modelName="+modelName+"&proTemplateId="+proTemplateId).GetHashMapArray();
    }

    var Formula_GetVarInfoByModelName_old = window.Formula_GetVarInfoByModelName;
    //获取主文档和表单数据字典
    function Formula_GetVarInfoByModelName(modelName) {
        if(window.loadExtendPropertyData){
            return loadExtendPropertyData(modelName);
        }else{
            return Formula_GetVarInfoByModelName_old(modelName);
        }
    }


/**
 * 模版改变通知
 */
function updateNotice(val){
	if(proTemplateId && (!val.data[0] || proTemplateId!=val.data[0].id)){
		seajs.use([ 'lui/dialog'], function(dialog) {
			dialog.alert("模板发生变更,如果在流程公式中设置了扩展属性,请手动修改!");});
	}
}


$(function() {
	var validation = 
		$.isFunction(window.$KMSSValidation) ? $KMSSValidation(document.forms[0]) : window.$KMSSValidation
	if(!validation || !validation.addValidators) {
		return;
	}
	var validations = {
			"checkType" : {
				error : "{error}",
				test : function(v, e, o) {
					var flag = true, id = $("[name='fdParentId']").val();
					$.ajax("${KMSS_Parameter_ContextPath}kms/knowledge/kms_knowledge_category/kmsKnowledgeCategory.do?method=getTemplateType&fdId=" + id , {
							type : "GET" ,
							dataType : "json",
							async : false,
							success : function(data) {
								if(data.templateType) {
									var cType = $("[name='fdTemplateType']").val();
									if(data.templateType == "2" && cType.indexOf("1") > -1) {
										o.error = "<bean:message bundle='kms-knowledge' key='kmsKnowledge.templateType.onlywiki'/>";
										flag = false;
									} else if(data.templateType == "1" && cType.indexOf("2") > -1) {
										o.error = "<bean:message bundle='kms-knowledge' key='kmsKnowledge.templateType.onlymultidoc'/>";
										flag = false;
									}
								}
							}
					});
					return flag;
				}
			}
	}
	validation.addValidators(validations)

});
</script>
