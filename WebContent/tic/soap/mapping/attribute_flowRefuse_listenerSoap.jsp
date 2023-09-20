<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|tree.js|dialog.js|json2.js|data.js");
</script>
<script>

//初始化
function initValue(config, eventType, nodeObj){
	// config返回的保存信息
	if (config != null) {
		var configObj = JSON.parse(config);
		var soapFuncValue = configObj["soapFuncValue"];
		var templateId = configObj["templateId"];
		//alert(config+","+sapFuncValue);
		var isTemplateId = $("#templateId").val();
		if (templateId != isTemplateId) {
			// id不一致则证明需要重新加载才行
			getInfo(templateId, soapFuncValue);
		} else {
			$("#soapFunc").val(soapFuncValue);
		}
	}	
	
};

//返回值
function returnValue() {
	var templateId = $("#templateId").val();
	var jsonCfg = '{';
	jsonCfg += '"soapFuncValue":"'+ $("#soapFunc").val() +'",';
	jsonCfg += '"templateId":"'+ templateId +'"';
	jsonCfg += '}';
	return jsonCfg;
};

//校验
function checkValue() {
	var soapFuncValue = $("#soapFunc").val();
	if (soapFuncValue == "") {
		alert("请选择SOAP函数");
		return false;
	} else {
		return true;
	}
		
};

$(function(){
	var templateId = getTemplateId();
	$("#templateId").val(templateId);
	getInfo(templateId);
});

function getInfo(templateId, soapFuncValue) {
	var xmlUrl = XMLDATABEANURL+"ticCoreMappingFuncListService&fdType=3&fdInvokeType=6&fdTemplateId="+templateId;
	var data = new KMSSData();
	data.SendToUrl(
		xmlUrl,
		function(http_request){
			var nodes = XML_GetNodesByPath(http_request.responseXML, "/dataList/data");
			rtnVal = new Array;
			for(var i=0; i<nodes.length; i++){
				rtnVal[i] = new Array;
				var attNodes = nodes[i].attributes;
				for(var j=0; j<attNodes.length; j++)
					rtnVal[i][attNodes[j].nodeName] = attNodes[j].nodeValue;
			}
			var rtnData = new KMSSData();
			rtnData.AddHashMapArray(rtnVal);
			setFuncOption(rtnData, soapFuncValue);
		},
		false
	);
	/*var data = new KMSSData();
	data.SendToBean("ticCoreMappingFuncListService&fdType=3&fdInvokeType=6&fdTemplateId="+templateId,
	function(rtnData){
		setFuncOption(rtnData, soapFuncValue);
	});*/
}

function setFuncOption(rtnData, soapFuncValue) {
	var soapFunc = $("#soapFunc");
	var rtnDataArray = rtnData.GetHashMapArray();
	var length = rtnDataArray.length;
	if (length == 0) {
		return;
	}
	for ( var i = 0; i < length; i++) {
		soapFunc.append("<option value='"+ rtnDataArray[i]["value"] +"'>"+ rtnDataArray[i]["text"] +"</option>");
	}
	if (soapFuncValue != null) {
		$("#soapFunc").val(soapFuncValue);
	}
}

function getTemplateId() {
	// 流程模版取ID
	var fdIdObj = parent.dialogObject.Window.parent.parent.document.getElementById('fdId');
	if (fdIdObj == null) {
		fdIdObj = window.parent.parent.dialogObject.Window.parent.parent.document.getElementsByName('fdId')[0];
	}
	return fdIdObj.value;
}

</script>
<center>
<table id="List_ViewTable" class="tb_normal" width="98%">
	<tr>
		<td width="30%" class="td_normal_title">${lfn:message('tic-core-common:ticCoreCommon.mappingSettingFunc')}</td>
		<td>
			<select id="soapFunc">
				<option value="">${lfn:message('tic-core-common:ticCoreCommon.pleaseSelect')}</option>
			</select>
			<input type="hidden" id="templateId" value=""/>
		</td>
	</tr>
</table>
<%@ include file="/resource/jsp/edit_down.jsp"%></center>