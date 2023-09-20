<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|json2.js|dialog.js|formula.js|xml.js|doclist.js");
</script>

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

function query_submit()
{
	var queryFormData = serializeFormToJson("jdbc_query_form");
	$("#fdJsonResult_id").val(queryFormData);
	Com_Submit(document.ticJdbcQueryForm, 'getQueryResult');
}

//序列化form表单数据为json格式字符串
function serializeFormToJson(formId)
{
	var obj = {};
    var formArray = $("#"+formId).serializeArray();
    $.each(formArray, function () {
        if (obj[this.name] !== undefined) {
            if (!obj[this.name].push) {
                obj[this.name] = [obj[this.name]];
            }
            obj[this.name].push(this.value || '');
        } else {
            obj[this.name] = this.value || '';
        }
    });
    return JSON.stringify(obj);
}
</script>

<div id="optBarDiv">

   	<input type="button"
		value="<bean:message bundle="tic-jdbc" key="ticJdbcQuery.query.queryResult"/>"
		onclick="query_submit();">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<center>
<p class="txttitle">${ticJdbcDataSetName }</p>

<html:form action="/tic/jdbc/tic_jdbc_query/ticJdbcQuery.do">
	<input type="hidden" name="ticJdbcDataSetId" value="${ticJdbcDataSetId}">
  <table class="tb_normal" width=81%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcQuery.title"/>
		</td><td colspan="3" width="85%">
			<xform:text property="docSubject"  showStatus="edit" style="width:85%" />
		</td>
    </tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdSqlExpression"/>
		</td>
		<td colspan="3" width="85%">${fdSqlExpression}</td>
   </tr>
   </table>
  <br>
  <input name="fdJsonResult" id="fdJsonResult_id" type="hidden"/>
</html:form>
<form id="jdbc_query_form" action="">
  <table class="tb_normal" width=81% id="inParamValueBefore">
    		<tr>
			       <td class="td_normal_title" width="40%">
							${lfn:message('tic-core-common:ticCoreFuncBase.fdParaIn')}
					</td>
					<td class="td_normal_title" width="30%">
							${lfn:message('tic-core-common:ticCoreCommon.dataType')}
					</td>	
			        <td class="td_normal_title" width="30%">
							${lfn:message('tic-core-common:ticCoreCommon.writeData')}
					</td>	
			</tr>
  </table>
</form>
</center>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/custom_validations.js" type="text/javascript"></script>
<script type="text/javascript">
$KMSSValidation();
$(function(){
	// 验证标题
	FUN_AddValidates("docSubject:required");
	//初始化数据
	init_setInParams();
});
function init_setInParams(){
	var inJsonObj = $.parseJSON('${dataJson}')["in"]; 
	if(!inJsonObj || inJsonObj==null){
		return;
	}
	for (var len = inJsonObj.length, i = len - 1; i >= 0 ; i--) {
		var html =  $("<tr><td>"+inJsonObj[i].tagName+"</td><td>"+inJsonObj[i].ctype+"</td><td><input type='hidden' name='"+inJsonObj[i].tagName+"' value='"+inJsonObj[i].ctype+"'><input type='text' name='"+inJsonObj[i].tagName+"'></td></tr>");
		$("#inParamValueBefore").append(html);	
	}  
}
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>
