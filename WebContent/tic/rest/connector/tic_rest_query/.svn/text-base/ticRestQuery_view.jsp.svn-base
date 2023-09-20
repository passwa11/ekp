<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|base64.js");
</script>
<script>
$(document).ready(function(){
 	var fdQueryParam='${ticRestQueryForm.fdQueryParam}';
	if(fdQueryParam){
		var fdQueryParam_json=JSON.parse(fdQueryParam);
		var  body=fdQueryParam_json["body"];
		if(isBase64( body)){
			body= Base64.decode(body);
		}
		fdQueryParam_json["body"]=JSON.parse(body);
		fdQueryParam=JSON.stringify(fdQueryParam_json);
	}
	$("#fdQueryParam_td").text(fdQueryParam); 
	$('textarea[name="fdQueryParam"]').text(fdQueryParam);
});	
function isBase64(val){
	var base64Pattern =new RegExp("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$");
	return base64Pattern.test(val);
}
</script>
<div id="optBarDiv">
	<input type="button"
		value="<bean:message bundle="tic-core-common" key="ticCoreCommon.reQuery"/>"
		onclick="Com_Submit(document.ticRestQueryForm, 'reQuery');">
	<kmss:auth requestURL="/tic/rest/connector/tic_rest_query/ticRestQuery.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="Com_OpenWindow('ticRestQuery.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"></p>
<html:form action="/tic/rest/connector/tic_rest_query/ticRestQuery.do">
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			${lfn:message('tic-rest-connector:ticRestQuery.docSubject')}
		</td><td colspan="3" width="85%">
			${ticRestQueryForm.docSubject }
			<input type="hidden" name="docSubject" value="${ticRestQueryForm.docSubjectShow }"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${lfn:message('tic-core-common:ticCoreFuncBase.fdParaIn')}
		</td>
		<td colspan="3" width="85%" id="fdQueryParam_td">
		    ${ticRestQueryForm.fdQueryParam}
		</td>
		<textarea name="fdQueryParam" rows="" cols="" style="display:none;">${ticRestQueryForm.fdQueryParam}</textarea>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${lfn:message('tic-core-common:jsonView.returnParam')}
		</td><td colspan="3" width="85%">
			<c:out value="${ticRestQueryForm.fdJsonResult  }"/>
			<textarea name="fdJsonResult " rows="" cols="" style="display:none;"></textarea>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${lfn:message('tic-core-common:jsonView.errorInfo')}
		</td><td colspan="3" width="85%">
			<c:out value="${ticRestQueryForm.docFaultInfo}"/>
			<textarea name="docFaultInfo" rows="" cols="" style="display:none;">${ticRestQueryForm.docFaultInfo }</textarea>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${lfn:message('tic-core-common:ticCoreCommon.createUser')}
		</td><td width="35%">
			<c:out value="${ticRestQueryForm.docCreatorName}" />
		</td>
		<td class="td_normal_title" width=15%>
			${lfn:message('tic-core-common:ticCoreCommon.createTime')}
		</td><td width="35%">
		    <c:out value="${ticRestQueryForm.docCreateTime}" />
		</td>
	</tr>
</table>
<input type="hidden" name="fdId" value="${param.fdId }"/>
<input type="hidden" name="ticRestMainId" value="${ticRestQueryForm.ticRestMainId}"/>
</center>

</html:form>
<%@ include file="/resource/jsp/view_down.jsp"%>
