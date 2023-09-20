<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<link href="${KMSS_Parameter_ContextPath}tic/core/resource/css/jquery.json-viewer.css" rel="stylesheet" type="text/css" />
<link href="${KMSS_Parameter_ContextPath}tic/core/resource/css/bootstrap.css" rel="stylesheet" type="text/css" />
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/jquery.json-viewer.js" type="text/javascript"></script>
<script>
Com_IncludeFile("jquery.js|json2.js|dialog.js|formula.js|xml.js|doclist.js");
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
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle">${lfn:message('tic-core-common:JSONInterfaceView')}</p>
<center>
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
<%@ include file="/resource/jsp/view_down.jsp"%>
