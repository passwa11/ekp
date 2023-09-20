<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.relation.util.SysRelationUtil" %>
<%@ page import="com.landray.kmss.sys.relation.forms.SysRelationEntryForm" %>
<%@ page import="com.landray.kmss.sys.relation.forms.SysRelationConditionForm" %>
<%@ page import="com.landray.kmss.sys.relation.forms.SysRelationStaticNewForm" %>
<%@ page import="com.landray.kmss.sys.relation.forms.SysRelationTextForm" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>

<!-- czk2019 -->

<script type="text/javascript">
	var relationEntrys = {};
	//替换所有字符串
	String.prototype.replaceAll  = function(s1,s2){
		return this.replace(new RegExp(s1,"gm"), s2);
	};
	//初始化关联项
	<c:forEach items="${sysRelationMainForm.sysRelationEntryFormList}" varStatus="vstatus" var="sysRelationEntryForm">
	<%SysRelationEntryForm sysRelationEntryForm = (SysRelationEntryForm) pageContext.getAttribute("sysRelationEntryForm");%>
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
		docStatus:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getDocStatus())%>",
		fdDiffusionAuth:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdDiffusionAuth())%>",
		fdIsTemplate:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdIsTemplate())%>".replaceAll("<br>","\r\n")
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
	var relationTexts={};
	var staticEntry = new Array();
	<c:forEach items="${sysRelationEntryForm.sysRelationStaticNewFormList}" varStatus="vs" var="sysRelationStaticNewForm">
	<%
    SysRelationStaticNewForm sysRelationStaticNewForm = (SysRelationStaticNewForm) pageContext.getAttribute("sysRelationStaticNewForm");
    %>
	staticEntry["${vs.index}"] = {
		fdId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdId())%>",
		fdRelatedName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdRelatedName())%>",
		fdSourceId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdSourceId())%>",
		fdSourceModelName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdSourceModelName())%>",
		fdSourceDocSubject:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdSourceDocSubject())%>",
		fdRelatedId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdRelatedId())%>",
		fdRelatedModelName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdRelatedModelName())%>",

		fdRelatedUrl:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdRelatedUrl())%>",
		fdRelatedName:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdRelatedName())%>",
		fdRelatedType:"<%=sysRelationStaticNewForm.getFdRelatedType()%>",
		fdEntryId:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdEntryId())%>",
		fdIndex:"<%=SysRelationUtil.replaceJsonQuotes(sysRelationStaticNewForm.getFdIndex())%>"
	};
	</c:forEach>
	//staticInfos["<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())%>"] = staticEntry;
	relationEntrys["<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())%>"].staticInfos = staticEntry;

	//czk2019
	var relationPersons={'fdPersonIds':'','fdPersonNames':''};
	<c:if test = "${not empty sysRelationEntryForm.fdPersonIds}">
	relationPersons['fdPersonIds'] = "<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdPersonIds())%>";
	relationPersons['fdPersonNames'] = "<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdPersonNames())%>";
	</c:if>
	relationEntrys["<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())%>"].relationPersons = relationPersons;

	<c:forEach items="${sysRelationEntryForm.sysRelationTextFormList}" varStatus="vs" var="sysRelationTextForm">

	<%
	SysRelationTextForm sysRelationTextForm = (SysRelationTextForm) pageContext.getAttribute("sysRelationTextForm");
	
	if(StringUtil.isNotNull(sysRelationTextForm.getFdDescription())){
		pageContext.setAttribute("desc",sysRelationTextForm.getFdDescription());
	%>
	//处理拷贝场景，ajax返回的对象没有值的问题
	relationTexts['fdDescription'] = '${lfn:escapeJs(desc)}';
	$.ajax({
		type:"GET",
		url: Com_Parameter.ContextPath + 'sys/relation/sys_relation_main/sysRelationMain.do?method=textValue&fdId='+'<%=sysRelationTextForm.getFdId()%>',
		async:false,
		success:function(data){
			var json = eval(data);
			if (json.textValue && json.textValue != "") {
				relationTexts['fdDescription'] = json.textValue;
			}
		}
	});
	<% } %>

	</c:forEach>
	relationEntrys["<%=SysRelationUtil.replaceJsonQuotes(sysRelationEntryForm.getFdId())%>"].relationTexts = relationTexts;
	</c:forEach>

	var entryPrefix = "${requestScope.sysRelationMainPrefix}sysRelationEntryFormList";
	var conditionPrefix = "sysRelationConditionFormList";
	var staticPrefix = "sysRelationStaticNewFormList";
	var textPrefix = "sysRelationTextFormList";
	var noDataLength = 1;
	DocList_Info[DocList_Info.length] = "sysRelationZone";
	//根据类型获取类型显示的名称
	//czk2019
	function getRelationTypeNameByType(type) {
		var typeName = "";
		switch(type) {
			case "1":
				typeName = '<bean:message bundle="sys-relation" key="sysRelationEntry.fdType1" />';
				break;
			case "2":
				typeName = '<bean:message bundle="sys-relation" key="sysRelationEntry.fdType2" />';
				break;
			case "3":
				typeName = '<bean:message bundle="sys-relation" key="sysRelationEntry.fdType3" />';
				break;
			case "4":
				typeName = '<bean:message bundle="sys-relation" key="sysRelationEntry.fdType4" />';
				break;
			case "5":
				typeName = '<bean:message bundle="sys-relation" key="sysRelationEntry.fdType5" />';
				break;
			case "6":
				typeName = '<bean:message bundle="sys-relation" key="sysRelationEntry.fdType6" />';
				break;
			case "8":
				typeName = '<bean:message bundle="sys-relation" key="sysRelationEntry.fdType8" />';
				break;
			case "9":
				typeName = '<bean:message bundle="sys-relation" key="sysRelationEntry.fdType9" />';
				break;
			default:
				typeName = '<bean:message bundle="sys-relation" key="sysRelationEntry.fdType2" />';
		}
		return typeName;
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
	// 拼装关联项字段
	function getRelationEntryElementsHTML(index, fdId, exceptValue){
		var html = "";
		var entry = relationEntrys[fdId];
		for(var property in entry){
			if (exceptValue && property == exceptValue) {
				continue;
			}
			if(property == "staticInfos"){
				//html+=getRelationStaticHTML(entry[property],index);
			}
			if(typeof entry[property] == "string"){
				html+="<input type=\"hidden\" name=\""+entryPrefix+"["+index+"]."+property+"\" value=\""+Relation_HtmlEscape(entry[property])+"\">";
			}
		}
		return html;
	}
	//拼装静态关联链接字段
	function getRelationStaticHTML(staticInfos,index){
		var staticElementsHTML = "";
		for(var i=0;i<staticInfos.length;i++){
			for(var staticProperty in staticInfos[i]){
				staticElementsHTML+="<input type=\"hidden\" name=\""+entryPrefix+"[" + index +
						"]."+staticPrefix+"["+i+"]."+staticProperty+"\" value=\""+staticInfos[i][staticProperty]+"\">";
			}
		}
		return staticElementsHTML;
	}
	//拼装条件字段（避免动态行刷新，提交时在拼装）
	function getRelationEntryConditionElementsHTML(index, fdId){
		var html = "";
		var conditions = relationEntrys[fdId].relationConditions;
		var count = 0;
		for(var condition in conditions){
			for(var condProp in conditions[condition]){
				html+="<input type=\"hidden\" name=\""+entryPrefix+"["+index+"]."+conditionPrefix+"["+(count)+"]."
						+condProp+"\" value=\""+Relation_HtmlEscape(conditions[condition][condProp])+"\">";
			}
			count++;
		}
		return html;
	}
	//拼装静态链接字段（避免动态行刷新，提交时在拼装）
	function getRelationEntryStaticElementsHTML(index, fdId){
		var html = "";
		var staticInfos = relationEntrys[fdId].staticInfos;
		var count = 0;

		for(var i in staticInfos){
			for(var condProp in staticInfos[i]){
				html += '<input type="hidden" name="'+entryPrefix+'['+index+'].' + staticPrefix+'['+(count)+'].' +
						condProp+'" value="'+Relation_HtmlEscape(staticInfos[i][condProp])+'">';
			}
			count++;
		}
		return html;
	}
	//拼装人员关联信息字段（避免动态行刷新，提交时在拼装）czk2019
	function getRelationEntryPersonElementsHTML(index, fdId){
		var html = "";
		var relationPersons = relationEntrys[fdId].relationPersons;
		for(var condProp in relationPersons){
			html += '<input type="hidden" name="'+entryPrefix+'['+index+'].' +
					condProp+'" value="'+Relation_HtmlEscape(relationPersons[condProp])+'">';
		}
		return html;
	}

	//拼装文本信息字段（避免动态行刷新，提交时在拼装）
	function getRelationEntryTextElementsHTML(index, fdId){
		var html = "";
		var relationTexts = relationEntrys[fdId].relationTexts;

		for(var condProp in relationTexts){
			html += '<input type="hidden" name="'+entryPrefix+'['+index+'].' + textPrefix+'[0].' +
					condProp+'" value="'+Relation_HtmlEscape(relationTexts[condProp])+'">';
		}

		return html;
	}
	//编辑“文档关联”配置信息
	function editRelationDoc() {
		var dialog = new KMSSDialog();
		// 获取文档关联的下标（如有）
		var index = findRelationDocEntry();
		if(index > -1) {
			var _entrlyId = document.getElementsByName(entryPrefix+"["+index+"].fdId");
			if(_entrlyId && _entrlyId.length > 0) {
				var relationEntry = relationEntrys[_entrlyId[0].value];
				if(relationEntry) {
					dialog.relationEntry = relationEntry;
					dialog.tableCurIndex = index;
				}
			}
		}

		dialog.SetAfterShow(function(rtnRelaData) {
			var rtnVal = null;
			if(rtnRelaData == null){
				rtnVal = $.parseJSON(window._rela_dialog.rtnRelaData);
			}else{
				rtnVal = rtnRelaData;
			}
			if(typeof(rtnVal) == "undefined" || rtnVal==null || $.isEmptyObject(rtnVal)) return;

			index = findRelationDocEntry();

			relationEntrys[rtnVal.fdId] = rtnVal;
			if(index > -1) {
				// 更新数据
				setTimeout(function(){
					var _fdId = document.getElementsByName(entryPrefix+"["+index+"].fdId")[0].value;
					document.getElementsByName(entryPrefix+"["+index+"].fdSearchScope")[0].value = rtnVal.fdSearchScope;
					document.getElementsByName(entryPrefix+"["+index+"].docStatus")[0].value = rtnVal.docStatus;
					document.getElementsByName(entryPrefix+"["+index+"].fdDiffusionAuth")[0].value = rtnVal.fdDiffusionAuth == "true";
					document.getElementsByName(entryPrefix+"["+index+"].fdIsTemplate")[0].value = "true";
					relationEntrys[_fdId] = rtnVal;
				},100);
				return;
			} else {
				// 新增数据
				index = document.getElementById("sysRelationZone").rows.length - noDataLength;
				var cellOneHTML = getRelationEntryElementsHTML(index, rtnVal.fdId, "fdModuleName");
				var content = new Array();
				content.push("<input type=\"text\" name=\""+entryPrefix+"["+index+"].fdModuleName\" class=\"inputsgl\" value=\""+Relation_HtmlEscape(rtnVal.fdModuleName)+"\" style=\"width:90%\"/>&nbsp;<span class=\"txtstrong\">*</span>" + cellOneHTML);
				content.push(getRelationTypeNameByType(rtnVal.fdType));
				var row = DocList_AddRow("sysRelationZone", content);
				// 此项为配置类，不需要显示
				row.style.display="none";
			}
		});
		dialog.URL = Com_Parameter.ContextPath + "sys/relation/sys_relation_main/configuration_type/sysRelationConfig.jsp?currModelName=${currModelName}&currModelId=${sysRelationMainForm.fdModelId}&flowkey=${flowkey}";
		window._rela_dialog = dialog;
		dialog.Show(800, 670);
	}


	// 增加关联项
	function addRelationEntry() {
		xformLoad();
		var dialog = new KMSSDialog();
		dialog.SetAfterShow(addRelationEntryHTML);
		dialog.URL = Com_Parameter.ContextPath + "sys/relation/sys_relation_main/sysRelationEntry.jsp?currModelName=${currModelName}&currModelId=${sysRelationMainForm.fdModelId}&flowkey=${flowkey}";
		window._rela_dialog = dialog;
		dialog.Show(800, 580);
	}
	function addRelationEntryHTML(rtnRelaData) {
		var rtnVal = null;
		if(rtnRelaData==null){
			rtnVal = $.parseJSON(window._rela_dialog.rtnRelaData);
		}else{
			rtnVal = rtnRelaData;
		}
		if(typeof(rtnVal) == "undefined" || rtnVal==null || $.isEmptyObject(rtnVal)) return;
		relationEntrys[rtnVal.fdId] = rtnVal;
		var index = document.getElementById("sysRelationZone").rows.length - noDataLength;
		var cellOneHTML = getRelationEntryElementsHTML(index, rtnVal.fdId, "fdModuleName");
		var content = new Array();
		content.push("<input type=\"text\" name=\""+entryPrefix+"["+index+"].fdModuleName\" class=\"inputsgl\" value=\""+Relation_HtmlEscape(rtnVal.fdModuleName)+"\" style=\"width:90%\"/>&nbsp;<span class=\"txtstrong\">*</span>" + cellOneHTML);
		content.push(getRelationTypeNameByType(rtnVal.fdType));
		var row = DocList_AddRow("sysRelationZone", content);
		//row.cells[0].innerHTML = "<input type=\"text\" name=\""+entryPrefix+"["+index+"].fdModuleName\" class=\"inputsgl\" value=\""+rtnVal.fdModuleName+"\" style=\"width:90%\"/>&nbsp;<span class=\"txtstrong\">*</span>" + cellOneHTML;
		//row.cells[1].innerHTML = getRelationTypeNameByType(rtnVal.fdType);

	}

	// 查找“文档关联”类型的配置
	function findRelationDocEntry(){
		var index = -1;
		var length = document.getElementById("sysRelationZone").rows.length - noDataLength;
		for(var i=0; i<length; i++) {
			var fdType = document.getElementsByName(entryPrefix+"["+i+"].fdType")[0].value;
			if(fdType == '5') { // 文档关联的类型值为5
				index = i;
				break;
			}
		}
		return index;
	}

	// 修改关联项
	function editRelationEntry(){
		xformLoad();
		var row = DocListFunc_GetParentByTagName("TR");
		var index = getRelationRowIndex(row) - noDataLength;
		var fdId = document.getElementsByName(entryPrefix+"["+index+"].fdId")[0].value;
		var relationEntry = relationEntrys[fdId];
		var dialog = new KMSSDialog();

		var relationPersons = relationEntry.relationPersons;
		if(relationPersons && relationPersons!=null){
		} else {
			var relationPersons = {};
			relationPersons['fdPersonIds'] = relationEntry.fdPersonIds;
			relationPersons['fdPersonNames'] = relationEntry.fdPersonNames;
			relationEntry.relationPersons = relationPersons;
		}

		dialog.SetAfterShow(editRelationEntryHTML);
		dialog.relationEntry = relationEntry;
		dialog.tableCurIndex = index;
		dialog.URL = Com_Parameter.ContextPath + "sys/relation/sys_relation_main/sysRelationEntry.jsp?currModelName=${currModelName}&currModelId=${sysRelationMainForm.fdModelId}&flowkey=${flowkey}";
		window._rela_dialog = dialog;
		dialog.Show(800, 580);
	}
	function editRelationEntryHTML() {
		var rtnVal = $.parseJSON(window._rela_dialog.rtnRelaData);
		if(typeof(rtnVal) == "undefined" || rtnVal==null || $.isEmptyObject(rtnVal)) return;
		delete relationEntrys[rtnVal.fdId];
		relationEntrys[rtnVal.fdId] = rtnVal;
		var index =  window._rela_dialog.tableCurIndex;
		var cellOneHTML = getRelationEntryElementsHTML(index, rtnVal.fdId, "fdModuleName");
		//如果类型没有修改，则取界面输入的fdModuleName，如果更改类型则取默认值
		var fdModuleName = rtnVal.fdModuleName;
		var row = DocList_TableInfo['sysRelationZone'].DOMElement.rows[index+1];
		row.cells[0].innerHTML = "<input type=\"text\" name=\""+entryPrefix+"["+index+"].fdModuleName\" class=\"inputsgl\" value=\""+Relation_HtmlEscape(fdModuleName)+"\" style=\"width:90%\">&nbsp;<span class=\"txtstrong\">*</span>" + cellOneHTML;
		row.cells[1].innerHTML = getRelationTypeNameByType(rtnVal.fdType);
	}
	// 获取行号
	function getRelationRowIndex(optTR){
		if(optTR==null)
			optTR = DocListFunc_GetParentByTagName("TR");
		var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
		var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
		return rowIndex;
	}
	// 删除关联项
	function deleteRelationEntry(){
		if(confirm('<bean:message bundle="sys-relation" key="sysRelationEntry.alert.deleteEntry"/>')){
			var row = DocListFunc_GetParentByTagName("TR");
			var index = getRelationRowIndex(row) - noDataLength;
			var fdId = document.getElementsByName(entryPrefix+"["+index+"].fdId")[0].value;
			delete relationEntrys[fdId];
			DocList_DeleteRow(row);
		}
	}
	//提交校验
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function(){
		var index = document.getElementById("sysRelationZone").rows.length - noDataLength;
		var _td, _fdId, _fdName;
		for (var i = 0; i < index; i++) {
			_fdId = document.getElementsByName(entryPrefix+"["+i+"].fdId")[0];
			_fdName = document.getElementsByName(entryPrefix+"["+i+"].fdModuleName")[0];
			document.getElementsByName(entryPrefix+"["+i+"].fdModuleName")[0].setAttribute("value", _fdName.value);
			if (_fdId != null &&  _fdId.value != "") {
				_td = DocListFunc_GetParentByTagName("TD", _fdId);
				// 拼装条件域
				var oldHtml = _td.innerHTML;
				_td.innerHTML = oldHtml + getRelationEntryConditionElementsHTML(i, _fdId.value) + getRelationEntryStaticElementsHTML(i, _fdId.value)
						+getRelationEntryTextElementsHTML(i, _fdId.value)+getRelationEntryPersonElementsHTML(i, _fdId.value);
			}
			if (_fdName.value == "") {
				// 分类名称不能为空
				_fdName.focus();
				alert('<bean:message key="errors.required" argKey0="sys-relation:sysRelationEntry.fdName" />');
				return false;
			}
		}
		return true;
	};
	Com_IncludeFile("data.js", null, "js");
	// string转成json
	function stringToJSON(obj){
		return eval('(' + obj + ')');
	}
	// 从模板导入
	function importFromTemplate() {
		var _relationEntrysTmp = new Array();
		var _relationEntrys = {};
		var _relationEntry = {};
		var _relationCondition = {};
		var _relationStatic = [];
		var url = Com_Parameter.ContextPath+"sys/relation/relation.do?method=importFromTemplate";
		new KMSSData().SendToUrl(url, function(http_request){
			_relationEntrysTmp = stringToJSON(unescape(http_request.responseText));
		}, false);
		if (_relationEntrysTmp.length == 0) {
			alert('<bean:message bundle="sys-relation" key="button.insertFromTemplate.alert" />');
		}
		// string转成json
		for (var i = 0; i < _relationEntrysTmp.length; i++) {
			_relationEntry = stringToJSON(_relationEntrysTmp[i]);
			_relationCondition = stringToJSON(_relationEntry.relationConditions);
			for (var fdItemName in _relationCondition) {
				_relationCondition[fdItemName] = stringToJSON(_relationCondition[fdItemName]);
			}
			_relationStatic = stringToJSON(_relationEntry.staticInfos);
			_relationEntry.staticInfos = _relationStatic;

			_relationEntry.relationConditions = _relationCondition;
			_relationEntrys[_relationEntry.fdId] = _relationEntry;
			// 添加关联项
			addRelationEntryHTML(_relationEntrys[_relationEntry.fdId]);
		}
	}
	Com_AddEventListener(window, "load", function(){
		if ("${requestScope.sysRelationMainPrefix}" == "") {
			<%-- 系统模板中使用 --%>
			var table1 = document.getElementById("sysRelationZoneTop");
			var table2 = document.getElementById("sysRelationZone");
			var table3 = document.getElementById("sysRelationZoneFooter");
			table1.width = "95%";
			table2.width = "95%";
			table3.width = "95%";
		}
	});
	var xmlHttpRequest;
	//Ajax创建XMLHttpRequest对象
	function createXmlHttpRequest(){
		if (window.XMLHttpRequest) {
			xmlHttpRequest = new XMLHttpRequest();
			if (xmlHttpRequest.overrideMimeType) {
				xmlHttpRequest.overrideMimeType("text/xml");
			}
		} else if (window.ActiveXObject) {
			var activeName = ["Msxml2.XMLHTTP", "Microsoft.XMLHTTP"];
			for (var i = 0; i < activeName.length; i++) {
				try {
					xmlHttpRequest = new ActiveXObject(activeName[i]);
					break;
				} catch(e) {
				}
			}
		}
	}
	//得到分类搜索条件的参数
	function getParams(){
		var params = "";
		var tbInfo = DocList_TableInfo['sysRelationZone'];
		var fdId = document.getElementsByName("sysRelationMainForm.fdId")[0];
		var fdKey = document.getElementsByName("sysRelationMainForm.fdKey")[0];
		var fdModelName = document.getElementsByName("sysRelationMainForm.fdModelName")[0];
		var fdModelId = document.getElementsByName("sysRelationMainForm.fdModelId")[0];
		var fdParameter = document.getElementsByName("sysRelationMainForm.fdParameter")[0];
		var moduleModelName = "";
		params += "&fdId="+fdId.value;
		params += "&fdKey="+fdKey.value;
		params += "&fdModelName="+fdModelName.value;
		params += "&fdModelId="+fdModelId.value;
		params += "&fdParameter="+fdParameter.value;
		var entryPrefix = "sysRelationMainForm.sysRelationEntryFormList";
		params += "&totalRow="+(tbInfo.lastIndex-1);//动态行总行数

		for(var i=0;i<tbInfo.lastIndex-1;i++){
			var fdId = document.getElementsByName(entryPrefix+"["+i+"].fdId")[0];
			var entry = relationEntrys[fdId.value];
			index = 0;
			for(var property in entry){
				if(typeof entry[property] == "string"){
					params += "&entry["+i+"]."+property+"="+ entry[property];
					if(property == "fdModuleModelName")
						moduleModelName = entry[property];
				}
			}

			var staticInfos = entry.staticInfos;
			var staticCount = 0;

			for(var n in staticInfos){
				for(var condProp in staticInfos[n]){
					params += "&entry["+i+"].static["+(staticCount)+"]."
							+condProp+"=";
					if(condProp=='fdRelatedUrl'){
						params += encodeURIComponent(staticInfos[n][condProp]);
					}else{
						params += staticInfos[n][condProp];
					}
				}
				staticCount++;
			}
			params += "&entry["+i+"].staticCount="+staticCount;

			//czk2019 人员关联
			var personInfos = relationEntrys[fdId.value].relationPersons;
			if(personInfos && personInfos!=null){
			} else {
				var relationPersons = {};
				relationPersons['fdPersonIds'] = relationEntrys[fdId.value].fdPersonIds;
				relationPersons['fdPersonNames'] = relationEntrys[fdId.value].fdPersonNames;
				personInfos = relationPersons;
			}

			var personCount = 0;
			for(var condProp in personInfos){
				params += "&entry["+i+"].person["+(personCount)+"]."+condProp+"=";
				params += personInfos[condProp];
			}
			personCount++;
			params += "&entry["+i+"].personCount="+personCount;


			var conditions = entry.relationConditions;
			var count = 0;
			for(var condition in conditions){
				for(var condProp in conditions[condition]){
					params += "&entry["+i+"].condition["+(count)+"]."
							+condProp+"="+conditions[condition][condProp];
				}
				count++;
			}
			;
			if(entry.relationTexts!=null){
				var textCount = 0;
				var texts = entry.relationTexts;
				for(var text in texts){
					params += "&entry["+i+"].text["+(textCount)+"]."+text+"="+encodeURIComponent(texts[text]);
					//params["entry["+i+"].text["+(textCount)+"]."+text] = texts[text];

					textCount++;
				}
				params += "&entry["+i+"].textCount="+textCount;
			}
			params += "&moduleModelName="+moduleModelName;
			params += "&entry["+i+"].count="+count;
			params += "&separator=0";//当做每一个分类的分割符
		}
		return window.encodeURI(params);
	}
	//预览
	function preview() {
		var tbInfo = DocList_TableInfo['sysRelationZone'];
		var params = getParams();
		for(var i = 0;i<tbInfo.lastIndex-1;i++){
			var n = document.getElementsByName("sysRelationMainForm.sysRelationEntryFormList["+i+"].fdModuleName")[0].value;
			params += "&entry["+i+"].fdModuleName1="+encodeURIComponent(n);
		}
		var dialog = new KMSSDialog();
		dialog.SetAfterShow(addRelationEntryHTML);
		dialog.relationEntry = {p:params};
		dialog.URL = Com_Parameter.ContextPath + "sys/relation/sys_relation_main/sysRelationMain_preview.jsp";
		dialog.Show(800, 580);
	}

</script>