<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">

Com_IncludeFile("jquery.js|json2.js|dialog.js|formula.js|xml.js|doclist.js");

function saveResultInfo()
{
	Com_Submit(document.ticJdbcQueryForm, 'saveSearchResult');
}

function json_result_view()
{
	var url = Com_Parameter.ContextPath + 
	"tic/jdbc/tic_jdbc_query/ticJdbcQuery.do"+
	"?method=getJsonResult";
	$("#jsonViewform").attr("action",url)
	document.jsonViewform.submit(); 
}

$(document).ready(function(){
	var resultInfo = JSON.stringify(${ticJdbcQueryForm.fdJsonResult});
	var resultJo = JSON.parse(resultInfo);
	var inJo = resultJo.view;
	var outJo = resultJo.out;
	
	//入参值显示
	var inHtml = '<tr class="td_normal_title"><td>字段名称</td><td>数据类型</td><td>字段值</td></tr>';
	$.each(inJo, function(key, value) {
		inHtml += '<tr><td>'+key+'</td><td>'+value[0]+'</td><td>'+value[1]+'</td></tr>';
	});
	$("#IMPORT_TABLE").html(inHtml);
	
	//出参值显示
	var outHtml;
	$.each(outJo, function(index, obj) {
		var tableHeader = '<tr class="td_normal_title">';
		var rowHtml = '<tr>';
		//行数据处理
		$.each(obj, function(key, value) {
			if(!outHtml)//需要生成头部
			{
				tableHeader += '<td>'+key+'</td>';
			}
			rowHtml += '<td>'+value+'</td>';
		});
		tableHeader += '</tr>';
		rowHtml += '</tr>';
		if(!outHtml)
		{
			outHtml += tableHeader;
		}
		outHtml += rowHtml;
	});
	$("#EXPORT_TABLE").html(outHtml);
});



</script>

<div id="optBarDiv">
	<input type="button"
		value="${lfn:message('tic-core-common:JSONInterfaceView')}"
		onclick="json_result_view();">

	<input type="button"
		value="<bean:message key="button.save"/>"
		onclick="saveResultInfo();">
	
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<%-- <html:form action="/tic/sap/connector/tic_sap_rfc_search_info/ticSapRfcSearchInfo.do">
<input id="fdRfcId" name="fdRfcId" type="hidden" value="${ticSapRfcSearchInfoForm.fdRfcId}">
<input id="fdRfcJsonResultId" name="fdRfcJsonResult" type="hidden" >
</html:form> --%>
<form id="jsonViewform" name="jsonViewform" action='' method="post"  target="_blank" >
	<textarea name="fdJsonResult" style="display:none;">${ticJdbcQueryForm.fdJsonResult}</textarea>
</form>
<p class="txttitle"><label id="bapiName"></label></p>
<center>

<html:form action="/tic/jdbc/tic_jdbc_query/ticJdbcQuery.do">
	<input name="fdId" type="hidden" value="${ticJdbcQueryForm.fdId}"/>
	<input name="docSubject" type="hidden" value="${ticJdbcQueryForm.docSubjectShow}"/>
	<textarea name="fdJsonResult" style="display: none;">${ticJdbcQueryForm.fdJsonResult}</textarea>
</html:form>
<p class="txttitle">${ticJdbcQueryForm.docSubject}</p>
<table id="Label_Tabel"  width=95%>
	<tr LKS_LabelName="${lfn:message('tic-core-common:ticCoreFuncBase.fdParaIn')}" >
				<td>
	<table id="IMPORT_TABLE" class="tb_normal" width="100%" >
		<tr class="td_normal_title"><td>${lfn:message('tic-core-common:ticCoreCommon.fieldName')}</td><td>${lfn:message('tic-core-common:ticCoreCommon.fieldValue')}</td></tr>
	</table>
  </td></tr>
<tr LKS_LabelName="${lfn:message('tic-core-common:ticCoreFuncBase.fdParaOut')}"><td>
  <table id="EXPORT_TABLE" class="tb_normal" width="100%">
	<tr class="td_normal_title"  style="">
	</tr>
  </table> 
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
