<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>	
Com_IncludeFile("optbar.js");

function setHeight(obj){
	$("#templateRef").show();
	var win=obj;
    if (document.getElementById)
    {
        if (win && !window.opera)
        { 
            if (win.contentDocument && win.contentDocument.body.scrollHeight) 
                win.height = win.contentDocument.body.scrollHeight + 100; 
            else if(win.Document && win.Document.forms[0] && win.Document.forms[0].scrollHeight)
                win.height = win.Document.forms[0].scrollHeight + 100 ;
        }
    } 
}
</script>
<tr LKS_LabelName="<bean:message  bundle="sys-xform-maindata" key="sysFormMainData.templateRef"/>">
	<td id="refTemplateContent">
		<iframe style="display:none" src="<c:url value="/sys/xform/maindata/sysFormMainDataCited.do?method=getTemplateRef&fdModelName=${param.fdModelName}&fdId=${param.fdId}"/>&ordertype=down" width="100%" height="100%" frameborder="0" scrolling="no" id="templateRef" onload="setHeight(this);">
		</iframe>
	</td>
</tr>