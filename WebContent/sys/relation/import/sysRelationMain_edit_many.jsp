<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.landray.kmss.sys.relation.util.SysRelationUtil" %>
<%@ page import="com.landray.kmss.sys.relation.forms.SysRelationStaticNewForm" %>
<%@ page import="com.landray.kmss.sys.relation.forms.SysRelationEntryForm" %>
<%@ page import="com.landray.kmss.sys.relation.forms.SysRelationConditionForm" %>
<%@ page import="com.landray.kmss.sys.relation.forms.SysRelationTextForm" %>
<%@ page import="com.landray.kmss.sys.relation.forms.SysRelationMainDataForm" %>
<%@ page import="com.landray.kmss.sys.relation.forms.SysRelationMainForm" %>
<%@page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<!-- czk2019 -->

<c:set var="mainModelForm" value="${requestScope[param.formName]}" />
<script type="text/javascript">
Com_IncludeFile("jquery.js", null, "js"); 
//主表单对象
var Relation_docMainFormObj = eval("document.forms['${JsParam.formName}']");
var relationMains = {};
<c:forEach items="${mainModelForm.sysRelationMainFormMap}" varStatus="vsMain" var="sysRelationMainForm">

	<%
		Map.Entry entry = (Map.Entry) pageContext.getAttribute("sysRelationMainForm");
		SysRelationMainForm formForMany = (SysRelationMainForm) entry.getValue();
		String docContentStr = StringUtil.isNotNull(formForMany.getFdDesContent())
						? SysRelationUtil.replaceJsonQuotes(formForMany.getFdDesContent()) : "";
	%>

	relationMains["<c:out value='${sysRelationMainForm.key}' />"]={
		fdId:"<c:out value='${sysRelationMainForm.value.fdId}' />",
		fdOtherUrl:"<c:out value='${sysRelationMainForm.value.otherUrlNoPattern}' />",
		fdKey:"<c:out value='${sysRelationMainForm.value.fdKey}' />",
		fdModelName:"<c:out value='${sysRelationMainForm.value.fdModelName}' />",
		fdModelId:"<c:out value='${sysRelationMainForm.value.fdModelId}' />",
		fdParameter:"<c:out value='${sysRelationMainForm.value.fdParameter}' />",
		fdDesSubject:"<c:out value='${sysRelationMainForm.value.fdDesSubject}'/>",
		fdDesContent: "<%=docContentStr%>"
	};    
	var relationEntrys = {};// 需要排序，改成数组
	<c:forEach items="${sysRelationMainForm.value.sysRelationEntryFormList}" varStatus="vstatus" var="sysRelationEntryForm">
		<%SysRelationEntryForm sysRelationEntryForm = (SysRelationEntryForm) pageContext.getAttribute("sysRelationEntryForm");%>
		<c:set var="entryFdIndex" value="${vstatus.index}" />
		<c:if test="${sysRelationEntryForm.fdIndex!=null && sysRelationEntryForm.fdIndex!=''}">
			<c:set var="entryFdIndex" value="<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdIndex())%>"/>
		</c:if>
		
		relationEntrys["<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())%>"] = {
			fdId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())%>",
			fdType:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdType())%>",
			fdModuleName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdModuleName())%>", //中文模块名
			fdModuleModelName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdModuleModelName())%>",
			fdOrderBy:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdOrderBy())%>",
			fdOrderByName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdOrderByName())%>",
			fdPageSize:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdPageSize())%>",
			fdParameter:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdParameter())%>",
			fdKeyWord:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdKeyWord())%>",
			docCreatorId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getDocCreatorId())%>",
			docCreatorName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getDocCreatorName())%>",
			fdFromCreateTime:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdFromCreateTime())%>",
			fdToCreateTime:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdToCreateTime())%>",
			fdSearchScope:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdSearchScope())%>",
			fdOtherUrl:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getOtherUrlNoPattern())%>",
			fdCCType:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdCCType())%>",
			fdChartId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdChartId())%>",
			fdChartName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdChartName())%>",
			fdChartType:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdChartType())%>",
			fdDynamicData:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdDynamicData())%>",
			fdIndex:"${entryFdIndex}"
		};
		var relationConditions={};
		<c:forEach items="${sysRelationEntryForm.sysRelationConditionFormList}" varStatus="vs" var="sysRelationConditionForm">
			<%SysRelationConditionForm sysRelationConditionForm = (SysRelationConditionForm) pageContext.getAttribute("sysRelationConditionForm");%>
			relationConditions["<%=SysRelationUtil.replaceJsonQuotes(sysRelationConditionForm.getFdItemName())%>"] = {
				fdId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationConditionForm.getFdId())%>",
				fdItemName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationConditionForm.getFdItemName())%>",
				fdParameter1:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationConditionForm.getFdParameter1())%>",
				fdParameter2:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationConditionForm.getFdParameter2())%>",
				fdParameter3:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationConditionForm.getFdParameter3())%>",
				fdBlurSearch:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationConditionForm.getFdBlurSearch())%>",
				fdVarName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationConditionForm.getFdVarName())%>"
			};
		</c:forEach>
		relationEntrys["<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())%>"].relationConditions = relationConditions;

		var staticInfos={};
		var staticEntry = {};
		<c:forEach items="${sysRelationEntryForm.sysRelationStaticNewFormList}" varStatus="vs" var="sysRelationStaticNewForm">
			<%SysRelationStaticNewForm sysRelationStaticNewForm = (SysRelationStaticNewForm) pageContext.getAttribute("sysRelationStaticNewForm");
			%>
			<c:set var="fdIndex" value="${vs.index}" />
			<c:if test="${sysRelationStaticNewForm.fdIndex!=null && sysRelationStaticNewForm.fdIndex!=''}">
				<c:set var="fdIndex" value="<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdIndex())%>"/>
			</c:if>
			
			staticEntry["${fdIndex}"] = { 
				fdId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdId())%>",
				fdSourceId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdSourceId())%>",
				fdSourceModelName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdSourceModelName())%>",
				fdSourceDocSubject:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdSourceDocSubject())%>",
				fdRelatedId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdRelatedId())%>",
				fdRelatedModelName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdRelatedModelName())%>",

				fdRelatedUrl:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdRelatedUrl())%>",
				fdRelatedName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdRelatedName() != null ?
					sysRelationStaticNewForm.getFdRelatedName().trim() : "")%>",
				fdRelatedType:"<%=sysRelationStaticNewForm.getFdRelatedType()%>",
				fdEntryId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdEntryId())%>",
				fdIndex:"${fdIndex}"
			};
		</c:forEach>
		staticInfos["<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())%>"] = staticEntry;
		relationEntrys["<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())%>"].staticInfos = staticInfos;
		
		
		<c:if test="${sysRelationEntryForm.fdType==6}">
			var relationTexts={};
			<c:forEach items="${sysRelationEntryForm.sysRelationTextFormList}" varStatus="vs" var="sysRelationTextFormList">
			
			<%
				SysRelationTextForm sysRelationTextFormList = (SysRelationTextForm) pageContext.getAttribute("sysRelationTextFormList");
			%>
				relationTexts = {
					fdDescription: "<%= StringEscapeUtils.escapeJavaScript(sysRelationTextFormList.getFdDescription()) %>"
				};
				
			</c:forEach>
			relationEntrys["<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())%>"].relationTexts = relationTexts;
		</c:if>
		
		<c:if test="${sysRelationEntryForm.fdType== 7}">
			var mainDatas={};
			var mainEntry = {};
			<c:forEach items="${sysRelationEntryForm.sysRelationMainDataFormList}" varStatus="vs" var="sysRelationMainDataForm">
				<%
				SysRelationMainDataForm sysRelationMainDataForm 
					= (SysRelationMainDataForm) pageContext.getAttribute("sysRelationMainDataForm");
				%>
				<c:set var="fdIndex" value="${vs.index}" />
				<c:if test="${sysRelationMainDataForm.fdIndex!=null && sysRelationMainDataForm.fdIndex!=''}">
					<c:set var="fdIndex" value="<%=SysRelationUtil.replaceJsonQuotes(sysRelationMainDataForm.getFdIndex())%>"/>
				</c:if>
				
				mainEntry["${fdIndex}"] = { 
					fdId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationMainDataForm.getFdId())%>",
					fdName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationMainDataForm.getFdName())%>",
					fdTemplateId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationMainDataForm.getFdTemplateId())%>",
					fdTemplateModelName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationMainDataForm.getFdTemplateModelName())%>",
					fdTemplateSubject:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationMainDataForm.getFdTemplateSubject())%>",
					fdMainDataId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationMainDataForm.getFdMainDataId())%>",
					fdMainDataModelName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationMainDataForm.getFdMainDataModelName())%>",
					fdMainDataName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationMainDataForm.getFdMainDataName())%>",
					fdEntryId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationMainDataForm.getFdEntryId())%>",
					fdIndex:"${fdIndex}"
				};
			</c:forEach>
			mainDatas["<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())%>"] = mainEntry;
			relationEntrys["<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())%>"].mainDatas = mainDatas;
		</c:if>
		
		<!--czk2019 -->
		<c:if test="${sysRelationEntryForm.fdType==8}">
			var relationPersons={'fdPersonIds':'','fdPersonNames':''};
			<c:if test = "${not empty sysRelationEntryForm.fdPersonIds}">
				relationPersons['fdPersonIds'] = "<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdPersonIds())%>";
				relationPersons['fdPersonNames'] = "<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdPersonNames())%>";
			</c:if>
			relationEntrys["<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())%>"].relationPersons = relationPersons;
	    </c:if>
		
		
	</c:forEach>
	relationMains["<c:out value='${sysRelationMainForm.key}' />"].relationEntrys = relationEntrys;
</c:forEach>

var mainPrefix = "sysRelationMainFormMap";
var entryPrefix = "sysRelationEntryFormList";
var conditionPrefix = "sysRelationConditionFormList";
var staticPrefix = "sysRelationStaticNewFormList";
var textPrefix="sysRelationTextFormList";
var mainDatasfix = "sysRelationMainDataFormList";
function createRelationMain(){
	return {fdId:"",fdOtherUrl:"",fdKey:"",fdModelName:"",fdModelId:"",fdParameter:"",relationEntrys:[]};
}


//删除关联机制，必须传入参数fdKey
function Relation_deleteRelation(fdKey){
	if(typeof fdKey == "undefined" || fdKey == null || fdKey == "" ){		
		alert('<bean:message bundle="sys-relation" key="relation.error.required.fdKey"/>');
		return;
	}	
	delete relationMains[fdKey];
}
function isValidateRelationCondition(relationCondition){
	return (relationCondition.fdParameter1 == "" 
		&& relationCondition.fdParameter2 == ""
		&& relationCondition.fdParameter3 == ""
		&& relationCondition.fdVarName == "");
}
// 拼装表单的隐藏域
function getRelationMainElementsHTML() {
	var j = 0, k = 0;
	for(var fdKey in relationMains) {
		for(var mainProp in relationMains[fdKey]) {
			var mainValue = relationMains[fdKey][mainProp];
			if(typeof mainValue == "string") {
				Relation_docMainFormObj.appendChild(createHidden(mainPrefix + "." + fdKey + "." + mainProp,mainValue));
			} else if(typeof mainValue == "object") {
				for(var index in mainValue){
					for(var entryProp in mainValue[index]) {
						var entryValue = mainValue[index][entryProp];
						if(typeof entryValue == "string") {
							Relation_docMainFormObj.appendChild(createHidden(mainPrefix + "." + fdKey + "." + entryPrefix + "[" + j + "]." + entryProp, entryValue));					
						}
						if(typeof entryValue == "object") {
							if(entryProp == "mainDatas") {
								var mainDatas = entryValue;		
								for(var mainData in mainDatas){
									for(var index1 in mainDatas[mainData]){
										for(var condProp in mainDatas[mainData][index1]){
											var mValue = mainDatas[mainData][index1][condProp];
											Relation_docMainFormObj.appendChild(createHidden(mainPrefix + "." + fdKey + "." + entryPrefix + "[" + j + "]." + mainDatasfix + "[" + index1 + "]." + condProp, mValue));	
										}
									}
								}
							} 
							//静态关联
							else if(entryProp == "staticInfos"){
								var staticInfos = entryValue;		
								for(var staticInfo in staticInfos){
									for(var index1 in staticInfos[staticInfo]){
										for(var condProp in staticInfos[staticInfo][index1]){
											var staticValue = staticInfos[staticInfo][index1][condProp];
											Relation_docMainFormObj.appendChild(createHidden(mainPrefix + "." + fdKey + "." + entryPrefix + "[" + j + "]." + staticPrefix + "[" + index1 + "]." + condProp, staticValue));	
										}
									}
								}
							}//文本
							else if(entryProp == "relationTexts") {
								var textInfos = entryValue;		
								for(var textInfo in textInfos){
									var textValue = textInfos[textInfo];
									Relation_docMainFormObj.appendChild(createHidden(mainPrefix + "." + fdKey + "." + entryPrefix + "[" + j + "]." + textPrefix + "[0]." + textInfo, textValue));	
									
								}
							} //人员关联
							else if(entryProp == "relationPersons") {
								var personInfos = entryValue;		
								for(var personInfo in personInfos){
									var personValue = personInfos[personInfo];
									Relation_docMainFormObj.appendChild(createHidden(mainPrefix + "." + fdKey + "." + entryPrefix + "[" + j + "]." + personInfo, personValue));	
									
								}
							} 
							else{	
								var conditions = entryValue;				
								for(var condition in conditions){
									if(isValidateRelationCondition(condition)){
										continue;
									}		
									for(var condProp in conditions[condition]){		
										
										var conditionValue = conditions[condition][condProp];
										Relation_docMainFormObj.appendChild(createHidden(mainPrefix + "." + fdKey + "." + entryPrefix + "[" + j + "]." + conditionPrefix + "[" + k + "]." + condProp, conditionValue));	
									}
									k++;
								}
								k = 0;
							}
						}
					}
					j++;
				}
				j=0;
			}			
		}		
	}	
}
function Relation_HtmlEscape(s){
	if(s==null || s=="")
		return "";
	if(typeof s != "string")
		return s;
	var re = /\"/g;
	s = s.replace(re, "&quot;");
	re = /'/g;
	s = s.replace(re, '&#39;');
	re = /</g;
	s = s.replace(re, "&lt;");
	re = />/g;
	return s.replace(re, "&gt;");
}

function createHidden( name, value ){	
	var e =$("<input type='hidden' name='" + name + "' value='" + Relation_HtmlEscape(value) + "' />")[0];
	return e;
}

String.prototype.trim= function(){    
    return this.replace(/(^\s*)|(\s*$)/g, "");  
}

//清空 NAME属性名字包含有{attribute}的所有Hidden域
function removeElementByAttributeName(attribute){
	if(attribute==null || attribute.length == 0)return;
	var arr = new Array();
	var _name, _type, _elements = Relation_docMainFormObj.elements;
	for (var n = 0, len = _elements.length; n < len; n++) {
		_name = _elements[n].name;
		_type = _elements[n].type;
		if(_type)
			_type = _type.toLowerCase();
		if(_type == "hidden" && _name != null && _name != "" && _name.trim().indexOf(attribute)!=-1){
			// 找到需要清空的hidden域
			arr.push(_name);
		}
	}
	for (var m = 0, le = arr.length; m < le; m++) {
		var elem = Relation_docMainFormObj.elements[arr[m]];
		elem.parentNode.removeChild(elem);
	}
}

//edit页面加载后，根据json对像，初始化sysRelationMainFormMap hidden元素
<c:if test="${fn:length(mainModelForm.sysRelationMainFormMap)>0}">
	Com_AddEventListener(window, "load", getRelationMainElementsHTML);
</c:if>
Com_Parameter.event["submit"].push(Relation_submit);

function Relation_submit() {
	//首先清空hidden域
	removeElementByAttributeName(mainPrefix);
	getRelationMainElementsHTML();
	return true;
}
</script>
<script type="text/javascript">
Com_IncludeFile("rela.js","${KMSS_Parameter_ContextPath}sys/relation/import/resource/","js",true);
</script>
<script type="text/javascript">
var rela_params = {
		'varName':'rela_opt',
		'rela.mainform.name':'${JsParam.formName}',
		'rela.button.ok':'<bean:message key="button.ok"/>',
		'rela.button.cancel':'<bean:message key="button.cancel"/>',
		'rela.setting.title':'<bean:message key="title.sysRelationMain.setting" bundle="sys-relation"/>'
};

var ____relationEvent = [];
//编辑关联机制，必须传入参数fdKey
function Relation_editRelation(fdKey) {
	
	var relation = new RelationOpt('${JsParam.currModelName}', '${mainModelForm.fdId}', fdKey, rela_params);
	if (typeof fdKey == "undefined" || fdKey == null || fdKey == "") {
		alert('<bean:message bundle="sys-relation" key="relation.error.required.fdKey"/>');
		return;
	}
	relation.editConfig(function() {
		for (var j = 0; j < ____relationEvent.length; j++) {
			____relationEvent[j](fdKey);
		}
	});
}
</script>
