<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysFormTemplateRefHistory" value="${requestScope[param.formName]}" />
<script>	
	Com_IncludeFile("optbar.js");
	function setHeight(obj){
		$("#templateHistoryRefMain").show();
		var win=obj;
	    if (document.getElementById)
	    {
	        if (win && !window.opera)
	        { 
	            if (win.contentDocument && win.contentDocument.body.scrollHeight) 
	                win.height = win.contentDocument.body.scrollHeight + 10; 
	            else if(win.Document && win.Document.forms[0] && win.Document.forms[0].scrollHeight)
	                win.height = win.Document.forms[0].scrollHeight + 10 ;
	        }
	    } 
	    
	}
</script>
<tr LKS_LabelName="<bean:message bundle="sys-xform" key="sysFormTemplate.history.quoteThisProcessDocument"/>">
	<td>
		<iframe style="display:none" id="templateHistoryRefMain" src="<c:url value="/sys/xform/base/sys_form_template_history/sysFormTemplateHistory.do?method=getTemplateHistoryRefMain&fdMainModelName=${param.fdMainModelName}&fdFileName=${sysFormTemplateRefHistory.fdFormFileName}&orderby=fdId&ordertype=down"/>" width="100%"  height="100%" frameborder="0" scrolling="no" onload="setHeight(this);">
		</iframe>
	</td>
</tr>