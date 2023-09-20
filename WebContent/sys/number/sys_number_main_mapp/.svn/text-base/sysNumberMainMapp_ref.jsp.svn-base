<%@ include file="/resource/jsp/common.jsp"%>
<script>	
Com_IncludeFile("optbar.js");
function numberMainMapp_LoadIframe(){
	 Doc_LoadFrame("numberMainMappContent","<c:url value="/sys/number/sys_number_main_mapp/sysNumberMainMapp.do?method=getTemplateRefNow&fdKey=${param.fdKey}&fdId="/>${sysNumberMainForm.fdId}");
	}
</script>
<tr LKS_LabelName="${ lfn:message('sys-number:sysNumberMain.templateRefCommon') }" style="display:none">
	<td id="numberMainMappContent" onresize="numberMainMapp_LoadIframe();">
		<iframe style="min-height: 340px" src="" width="100%" height="100%" frameborder="0" scrolling="no">
		</iframe>
	</td>
</tr>
