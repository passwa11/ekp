<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|json2.js|dialog.js|formula.js|xml.js|doclist.js");
</script>
<script>

	function confirmDelete(msg) {
		var del = confirm("<bean:message key="page.comfirmDelete"/>");
		return del;
	}
</script>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/mustache.js" type="text/javascript"></script>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/erp.parser.js" type="text/javascript"></script>
<link href="${KMSS_Parameter_ContextPath}tic/core/resource/css/jquery.treeTable.css" rel="stylesheet" type="text/css" />
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/jquery.treeTable.js" type="text/javascript"></script>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/custom_validations.js" type="text/javascript"></script>
<script src="${KMSS_Parameter_ContextPath}tic/soap/connector/tic_soap_query/view_script.js" type="text/javascript"></script>
<script type="text/javascript">
/************标记修正。。。************
 * 使用国际化的资源文件
 ************************/
$(function(){
	var spanNode = document.createElement("span");
	spanNode.id = "Include_Custom_Validations_Span_Id";
	document.body.appendChild(spanNode);
	$("#Include_Custom_Validations_Span_Id").load(Com_Parameter.ContextPath +
			"tic/core/resource/jsp/custom_validations.jsp");
});

function transCacheData(nm){
	document.getElementsByName("dataNodeName")[0].value=nm;
	Com_Submit(document.ticSoapQueryForm, 'transCacheData');
}

function saveResultInfo()
{
	var fdJsonResult = ${ticSoapQueryForm.fdJsonResult};
	$("#fdJsonResultId").val(JSON.stringify(fdJsonResult));
	Com_Submit(document.ticSoapQueryForm, 'save');
}

function json_result_view()
{
	var url = Com_Parameter.ContextPath + 
	"tic/soap/connector/tic_soap_query/ticSoapQuery.do"+
	"?method=getJsonResultView";
	$("#jsonViewform").attr("action",url)
	document.jsonViewform.submit();
}

</script>
<div id="optBarDiv">
	<input type="button"
		value="${lfn:message('tic-core-common:JSONInterfaceView')}"
		onclick="json_result_view();">

	<input type="button"
		value="<bean:message bundle="tic-soap-connector" key="ticSoapMain.query.saveAs"/>"
		onclick="saveResultInfo();">
	
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<html:form action="/tic/soap/connector/tic_soap_query/ticSoapQuery.do">

<input type="hidden" name="fdId" id="fdId" value="${ticSoapQueryForm.fdId}"/>
<input type="hidden" name="ticSoapMainId" value="${ticSoapQueryForm.ticSoapMainId }"/>
<input type="hidden" name="docSubject" id="docSubject" value="${ticSoapQueryForm.docSubjectShow }"/>
<textarea name="fdJsonResult" id="fdJsonResultId" style="display:none;">${ticSoapQueryForm.fdJsonResult}</textarea>
<textarea name="docInputParam" id="docInputParam" style="display:none;">${ticSoapQueryForm.docInputParam }</textarea>
<textarea name="docOutputParam" id="docOutputParam" style="display:none;">${ticSoapQueryForm.docOutputParam }</textarea>
<textarea name="docFaultInfo" id="docFaultInfo" style="display:none;">${ticSoapQueryForm.docFaultInfo }</textarea>
<textarea name="resultXml" style="display:none;" id="resultXml">${resultXml }</textarea>
<input type="hidden" name="dataNodeName" id="dataNodeName" value=""/>

</html:form>
<form id="jsonViewform" name="jsonViewform" action='' method="post"  target="_blank" >
	<textarea name="fdJsonResult" style="display:none;">${ticSoapQueryForm.fdJsonResult}</textarea>
</form>
<center>
<p class="txttitle">${ticSoapQueryForm.docSubject }</p>

	<table class="tb_normal" id="Label_Tabel" width=95%>
		<tr LKS_LabelName="<bean:message bundle="tic-soap-connector" key="ticSoapMain.query.inputParam"/>">
			<td>
				<table class="tb_normal" width="100%">
				<tr><td>
				<div id="erp_input_div"></div>
				</td></tr>
				</table>
			</td>
		</tr>
		<tr LKS_LabelName="<bean:message bundle="tic-soap-connector" key="ticSoapMain.query.outputParam"/>">
			<td>
			<table class="tb_normal" width="100%">
				<tr><td>
				<div id="erp_output_div"></div>
				</td></tr>
				</table>
			</td>
		</tr>
		
		<tr LKS_LabelName="<bean:message bundle="tic-soap-connector" key="ticSoapMain.func.faultParam"/>">
			<td>
			<table class="tb_normal" width="100%">
				<tr><td>
				<div id="erp_fault_div"></div>
				</td></tr>
				</table>
			</td>
		</tr>
	</table>
</center>

<script id="erp_query_template" type="text/mustache">
<table class="erp_template" style="width:100%" >
	<caption>{{info.caption}}</caption>
	<thead>
		<tr>
		{{#info.thead}}
			<th width='{{width}}' >{{th}}</th>
		{{/info.thead}}
		</tr>
	</thead>
	<tbody>
		{{#info.tbody}}
		<tr  id="{{nodeKey}}"  {{^root}} class="child-of-{{parentKey}}" {{/root}} >
			<td><span {{^hasNext}} class='file' {{/hasNext}} {{#hasNext}} class='folder' {{/hasNext}}  >{{nodeName}}</span>
			
			{{^hasNext}} {{#comment.isDataList}} 数据列表属性 
					<input type="button"
					value="缓存数据测试"
					onclick="transCacheData('{{nodeName}}');">
			{{/comment.isDataList}} {{/hasNext}} 

			</td>
			<td>{{dataType}}</td>
			<td>{{#comment.minOccurs}}  min:{{comment.minOccurs}} {{/comment.minOccurs}}  {{#comment.maxOccurs}} max:{{comment.maxOccurs}}{{/comment.maxOccurs}} </td>
			<td>
				<label> {{#comment.title}} {{comment.title}} {{/comment.title}}</label>
            <td>
		  {{^hasNext}}
 			<input type="text" style="width:100%" readOnly class="inputsgl"  erpNodeValue="true" nodeKey="{{nodeKey}}" {{#nodeValue}} value="{{nodeValue}}" {{/nodeValue}} > 
			{{/hasNext}}
			</td>			
			</tr>
	{{/info.tbody}}
	</tbody>
</table>
</script>

<script type="text/javascript">

</script>

<%@ include file="/resource/jsp/view_down.jsp"%>
