<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp"%>
<%@page import="com.landray.kmss.third.im.kk.util.KK5Util"%>
<%
	request.setAttribute("KK5ServerUrl", KK5Util
			.getKK5Url("info/listService.ajax"));
	request.setAttribute("GZH", KK5Util
			.getGZHJson("info/listService.ajax"));
%>
<script type="text/javascript">
	Com_IncludeFile("document.js", "style/" + Com_Parameter.Style + "/doc/");
	var kk5ServerUrl = "${KK5ServerUrl}";
	var gzh= ${GZH};
	
	function contains(string, substr, isIgnoreCase) {
		if (isIgnoreCase) {
			string = string.toLowerCase();
			substr = substr.toLowerCase();
		}
		var startChar = substr.substring(0, 1);
		var strLen = substr.length;
		for (var j = 0; j < string.length - strLen + 1; j++) {
			if (string.charAt(j) == startChar)//如果匹配起始字符,开始查找
			{
				if (string.substring(j, j + strLen) == substr)//如果从j开始的字符与str匹配，那ok
				{
					return true;
				}
			}
		}
		return false;
	}

	function getPuIdFromContent() {
		var contentPropMappings = getContentPropMappings();
		if (contentPropMappings != null && contentPropMappings.length >= 1) {
			var puid=contentPropMappings[0].substring(contentPropMappings[0].indexOf("=") + 1);
			return puid;
		}
		return "";
	}

	function getContentPropMappings() {
		if (parent.NodeContent === undefined || parent.NodeContent == "") {
			return new Array();
		}
		var contentPropMappings = new Array();
		var nodeContent = new String(parent.NodeContent);
		if (nodeContent != null && nodeContent != "") {
			if (contains(nodeContent, ",", false)) {
				contentPropMappings = nodeContent.split(',');
			} else {
				contentPropMappings[0] = nodeContent;
			}
		}
		return contentPropMappings;
	}

	var commonAccountListTdParam = {
		"border" : false,
		"width" : "60px",
		"type" : "select",
		"id" : "commonAccountList",
		"defaultInputText" : "",
		"checkInputText" : "",
		"defaultSelectValue" : "",
		"checkSelectValue" : "",
		"innerText" : ""
	};

	var commonAccountDescriptionTdParam = {
		"border" : false,
		"width" : "150px",
		"type" : "",
		"id" : "",
		"defaultInputText" : "",
		"checkInputText" : "",
		"defaultSelectValue" : "",
		"checkSelectValue" : "",
		"innerText" : ""
	};

	var commonAccountValues;

	function getCommonAccountValues() {
		if (commonAccountValues === undefined) {
			return new Array();
		}
		return commonAccountValues;
	}

	function check() {
		return true;
	}

	// 必须实现的方法，供父窗口(attribute_robotnode.html)调用。
	function returnValue() {
		if (!check()) {
			return null;
		}
		var rtnJson = new Array();
		var puId = getSelectValue(document.getElementById("commonAccountList"));
		if (null != puId) {
			if (puId == "choose") {
				alert("${ lfn:message('third-im-kk:robotnode_hint_6') }");
				return null;
			}
			rtnJson.push("docPubId=" + puId);
		}
		else{
			alert("${ lfn:message('third-im-kk:robotnode_hint_5') }");
			return null;
		}
		return rtnJson.join('');
	}

	function getSelectValue(select) {
		if (select != null) {
			var idx = select.selectedIndex, option, value;
			if (idx > -1) {
				option = select.options[idx];
				value = option.attributes.value;
				return (value && value.specified) ? option.value : option.text;
			}
		}
		return null;
	}

	function createTdElement(trElement, tdParam, options) {
		var tdElement = trElement.insertCell(trElement.cells.length);
		if (tdParam.border == true) {
			tdElement.style.borderTop = "0px";
		}
		if (tdParam.width != "") {
			tdElement.style.width = tdParam.width;
		}
		if (tdParam.type == "select") {
			tdElement.innerHTML = getSelectElementHTML(tdParam.id, options,
					tdParam.checkSelectValue, tdParam.defaultSelectValue)
					+ tdParam.innerText;
			return tdElement;
		}
		tdElement.innerHTML = tdParam.innerText;
		return tdElement;
	}

	function getSelectElementHTML(id, options, checkSelectValue,
			defaultSelectValue) {
		var rtnResult = new Array();
		rtnResult.push('<option value=\'choose\'>${ lfn:message("third-im-kk:robotnode_hint_3") }</option>');
		if (options == null || options.length == 0) {
			var value = checkSelectValue || defaultSelectValue;
			if (value != '')
				rtnResult.push('<option value=\'' + value + '\' selected>'
						+ value + '</option>');
		} else {
			var value = checkSelectValue || defaultSelectValue;
			for (var i = 0, length = options.length; i < length; i++) {
				var option = options[i], optionValue = option.value;
				rtnResult.push('<option value=\'' + optionValue + '\'');
				if (optionValue == value) {
					rtnResult.push(' selected');
				}
				rtnResult.push('>' + option.name + '</option>');
			}
		}
		if (id == "commonAccountList") {
			return '<select onchange="changeDescription(this);" id="' + id
					+ '"' + ' style=\'width: 160px;\'>' + rtnResult.join('')
					+ '</select>';
		}
		return '<select id="' + id + '"' + ' style=\'width: 160px;\'>'
				+ rtnResult.join('') + '</select>';
	}

	function changeDescription(select) {
		if (select.value == "choose") {
			document.getElementById("commonAccountDescription").innerHTML = "";
		} else {
			var values = getCommonAccountValues();
			for (var i = 0, length = values.length; i < length; i++) {
				if (values[i].value == select.value) {
					document.getElementById("commonAccountDescription").innerHTML = values[i].description;
					break;
				}
			}
		}
	}

	function createCommonAccountList(trElement, options) {
		if (trElement != null && options != null) {
			var tdElement = createTdElement(trElement,
					commonAccountListTdParam, options);
			tdElement = createTdElement(trElement,
					commonAccountDescriptionTdParam, null);
			tdElement.id = "commonAccountDescription";
		}
	}

	function initDocument() {
		if(kk5ServerUrl != "noKK5ServerUrl"){
			initCommonAccountList();
		}
		else{
			alert("${ lfn:message('third-im-kk:robotnode_hint_1') }");
		}
	}

	function transCommonAccountList(jsonArray) {
		var rtnResult = new Array();
		if (!jsonArray)
			return rtnResult;
		for (var i = 0, length = jsonArray.length; i < length; i++) {
			if(jsonArray[i].serviceState==0){
				continue;
			}
			rtnResult.push({
				value : jsonArray[i].corp+"_"+jsonArray[i].service,
				name : jsonArray[i].serviceName,
				description : jsonArray[i].serviceDesc
			});
		}
		return rtnResult;
	};

	function initCommonAccountList() {
		commonAccountListTdParam.checkSelectValue = getPuIdFromContent();
		commonAccountValues=transCommonAccountList(gzh);
		var values = getCommonAccountValues();
		for (var i = 0, length = values.length; i < length; i++) {
			if (values[i].value == commonAccountListTdParam.checkSelectValue) {
				commonAccountDescriptionTdParam.innerText = values[i].description;
				break;
			}
		}
		createCommonAccountList(document
				.getElementById("commonAccount"), commonAccountValues);
	}
</script>
</head>
<body onload="initDocument();">
<table id="main" width="100%" class="tb_normal">
	<tr id="commonAccount">
		<td width="50px">${ lfn:message('third-im-kk:robotnode_hint_2') }</td>
	</tr>
</table>
</body>
</html>