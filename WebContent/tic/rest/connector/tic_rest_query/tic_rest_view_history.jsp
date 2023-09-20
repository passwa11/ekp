<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="ticRestMainForm" value="${requestScope[param.formName]}" />
<%@ page import="com.landray.kmss.util.StringUtil"%>
<script language="JavaScript">
Com_IncludeFile("optbar.js|dialog.js");
Com_IncludeFile("jquery.js");

function func_history_LoadIframe(){
	var iframe = document.getElementById("func_history").getElementsByTagName("IFRAME")[0];

	if($(iframe).attr("src").length<1){
		 var srcValue="<c:url value="/tic/rest/connector/tic_rest_query/ticRestQuery.do" />?method=list&funcId=${param.fdId}";
		$(iframe).attr("src",srcValue);
	}
	
};
function setWinHeight(obj){
	/*var win=obj;
	if (document.getElementById){
		if (win && !window.opera){
			if (win.contentDocument && win.contentDocument.body.offsetHeight)  
				win.height = win.contentDocument.body.offsetHeight;   
			else if(win.Document && win.Document.body.scrollHeight)
				win.height = win.Document.body.scrollHeight;
		}
	}*/


	 var bodyHeight;
	 var isChrome = navigator.userAgent.toLowerCase().match(/chrome/) != null;
	   if (!!window.ActiveXObject || "ActiveXObject" in window ||isChrome) {
	       bodyHeight = window.frames["_ifameName"].document.body.scrollHeight;
	   } else {
	       bodyHeight = thisFrame.contentWindow.document.documentElement.scrollHeight;
	   }

      $(obj).height(bodyHeight+20);
}

</script>
<tr LKS_LabelName="<bean:message bundle="tic-rest-connector" key="ticRestQuery.history"/>" style="display:none">
	<td>
		<table class="tb_normal" width="100%">
			<tr>
				<td id="func_history" onresize="func_history_LoadIframe();">
					<iframe name="_ifameName" src="" width=100% height=150px frameborder=0 onload="setWinHeight(this)">
					</iframe>
				</td>
			</tr>
		</table>
	</td>
</tr>

