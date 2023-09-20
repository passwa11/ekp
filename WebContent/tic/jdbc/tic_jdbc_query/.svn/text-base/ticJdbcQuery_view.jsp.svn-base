<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<link href="${KMSS_Parameter_ContextPath}tic/core/resource/css/jquery.json-viewer.css" rel="stylesheet" type="text/css" />
<link href="${KMSS_Parameter_ContextPath}tic/core/resource/css/bootstrap.css" rel="stylesheet" type="text/css" />
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/jquery.json-viewer.js" type="text/javascript"></script>
<script>
Com_IncludeFile("jquery.js|json2.js|dialog.js|formula.js|xml.js|doclist.js");
	function confirmDelete(msg){
		var del = confirm("<bean:message key="page.comfirmDelete"/>");
		return del;
	}
	
	$(function(){
		var jsonstr = JSON.stringify(${ticJdbcQueryForm.fdJsonResult});
		var jsonValue = eval("(" + jsonstr + ")");
		$('#json_result_view_in').jsonViewer(jsonValue.in);
		if(JSON.stringify(jsonValue.out).length > 2){
			$('#json_result_view_out').jsonViewer(jsonValue.out);
		}else{
			$('#json_result_view_out').parent().hide();
		}
	});
</script>
<div id="optBarDiv">
	<input type="button"
		value="<bean:message bundle="tic-jdbc" key="ticJdbcQuery.reQuery"/>"
		onclick="Com_Submit(document.ticJdbcQueryForm, 'reQuery');">
	<kmss:auth requestURL="/tic/jdbc/tic_jdbc_query/ticJdbcQuery.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticJdbcQuery.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-jdbc" key="table.ticJdbcQuery"/></p>
<html:form action="/tic/jdbc/tic_jdbc_query/ticJdbcQuery.do">
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcQuery.docSubject"/>
		</td><td colspan="3" width="85%">
			${ticJdbcQueryForm.docSubject }
			<input type="hidden" name="docSubject" value="${ticJdbcQueryForm.docSubjectShow }"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcQuery.docCreator"/>
		</td><td width="35%">
			<c:out value="${ticJdbcQueryForm.docCreatorName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcQuery.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			 ${lfn:message('tic-core-common:ticCoreCommon.selectSql')}
		</td><td colspan="3" width="85%">
			${ticJdbcQueryForm.ticJdbcDataSetName }
			<input type="hidden" name="docSubject" value="${ticJdbcQueryForm.docSubject }"/>
		</td>
	</tr>
</table>
<input type="hidden" name="fdId" value="${ticJdbcQueryForm.ticJdbcDataSetId }"/>
<input type="hidden" name="ticJdbcDataSetId" value="${ticJdbcQueryForm.ticJdbcDataSetId }"/>
<div class="jq22-container">
  <div class="container" style="margin-top: 1em;">
    <div class="row" align="left">
		<p>${lfn:message('tic-core-common:jsonView.inParam')}</p>
		<pre style="padding-left: 25px;" id="json_result_view_in" ></pre>
		<div>
		<p>${lfn:message('tic-core-common:jsonView.returnParam')}</p>
		<pre style="padding-left: 25px;" id="json_result_view_out" ></pre>
		</div>
	</div>
  </div>
</div>
</center>
</html:form>
<%@ include file="/resource/jsp/view_down.jsp"%>
