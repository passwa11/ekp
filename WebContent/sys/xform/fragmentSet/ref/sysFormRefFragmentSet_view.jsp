<%@ include file="/resource/jsp/common.jsp"%>
<script>	
Com_IncludeFile("optbar.js");
function templateRefFragmentSet_LoadIframe(){
	 Doc_LoadFrame("templateRefFragmentSetContent","<c:url value="/sys/xform/fragmentSet/ref/xFormRefFragmentSet.do?method=getTemplateRefFragmentSet&isHistory=${param.isHistory}&modelName=${sysFormFragmentSetForm.fdModelName}&fdFragmentSetId="/>${sysFormFragmentSetForm.fdId}&orderby=fdAlterTime&ordertype=down");
	}
</script>
<tr LKS_LabelName="<bean:message  bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.templateRefFragmentSet"/>" style="display:none">
	<td id="templateRefFragmentSetContent" onresize="templateRefFragmentSet_LoadIframe();">
		<iframe src="" width="100%" height="100%" frameborder="0" scrolling="no" id="templateRefFragmentSet">
		</iframe>
	</td>
</tr>