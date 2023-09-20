<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<script language="JavaScript">
Com_IncludeFile("optbar.js|dialog.js");
Com_IncludeFile("jquery.js");
function trans_history_LoadIframe(){
	var iframe = document.getElementById("trans_history").getElementsByTagName("IFRAME")[0];
	if($(iframe).attr("src").length<1){
		 var srcValue="<c:url value="/tic/core/common/tic_core_trans_sett/ticCoreTransSett.do" />?method=list&fdId=${param.fdId}";
		$(iframe).attr("src",srcValue);
	}
};
function setHeight(obj){
	 var bodyHeight;
	 var isChrome = navigator.userAgent.toLowerCase().match(/chrome/) != null;
   if (!!window.ActiveXObject || "ActiveXObject" in window ||isChrome) {
       bodyHeight = window.frames["core_ifameName"].document.body.scrollHeight;
   } else {
       bodyHeight = thisFrame.contentWindow.document.documentElement.scrollHeight;
   }
   
   $(obj).height(bodyHeight+20);
}

</script>
<tr LKS_LabelName="<bean:message bundle="tic-core-common" key="table.ticCoreTransSett" />" style="display:none">
	<td>
		<table class="tb_normal" width="100%">
			<tr>
				<td id="trans_history" onresize="trans_history_LoadIframe();">
					<iframe src="" name="core_ifameName" width=100% height=150px frameborder=0 onload="setHeight(this)">
					</iframe>
				</td>
			</tr>
		</table>
	</td>
</tr>

