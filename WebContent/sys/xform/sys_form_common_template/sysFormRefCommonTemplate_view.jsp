<%@ include file="/resource/jsp/common.jsp"%>
<script>	
Com_IncludeFile("optbar.js");
function templateRefCommon_LoadIframe(){
	 Doc_LoadFrame("templateRefCommonContent","<c:url value="/sys/xform/sys_form_template/sysFormRefCommonTemplate.do?method=getTemplateRefCommon&modelName=${sysFormCommonTemplateForm.fdModelName}&commonId="/>${sysFormCommonTemplateForm.fdId}&orderby=fdCreateTime&ordertype=down");
	}
</script>
<tr LKS_LabelName="<bean:message  bundle="sys-xform" key="sysFormTemplate.templateRefCommon"/>" style="display:none">
	<td id="templateRefCommonContent" onresize="templateRefCommon_LoadIframe();">
		<iframe src="" width="100%" height="100%" frameborder="0" scrolling="no">
		</iframe>
	</td>
</tr>