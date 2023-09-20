<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.KmssMessageWriter"%>
<%@page import="com.landray.kmss.util.*"%>
<%@page import="java.lang.*"%>
<%@page import="com.landray.kmss.component.locker.interfaces.*"%>
<% if(request.getAttribute("KMSS_RETURNPAGE")!=null
		|| StringUtil.isNotNull(request.getParameter("msg_success"))) {
	KmssMessageWriter msgWriter = new KmssMessageWriter(request, (KmssReturnPage)request.getAttribute("KMSS_RETURNPAGE"));
	boolean isComponentLockerVersionException = false;
	KmssReturnPage returnPage = (KmssReturnPage)request.getAttribute("KMSS_RETURNPAGE");
	if(returnPage != null) {
		KmssMessages kmssMessages = returnPage.getMessages();
		if(kmssMessages!=null && kmssMessages.hasError()){
			List<KmssMessage> msgs = kmssMessages.getMessages();
			if(msgs!=null && msgs.size()>0){
				KmssMessage msg = msgs.get(0);
				Throwable t = msg.getThrowable();
				if(t!=null && t instanceof ComponentLockerVersionException){
					isComponentLockerVersionException = true;
				}
			}
		}
	}
%>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<script type="text/javascript" src="${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/scripts/shBrushBash.js"></script>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/styles/shCore.css"/>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/styles/shThemeDefault.css"/>
	<script type="text/javascript">
		SyntaxHighlighter.config.clipboardSwf = '${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/scripts/clipboard.swf';

		SyntaxHighlighter.all();

		setTimeout(function(){
			seajs.use(['theme!prompt']);
		}, 3000 );

		function showMoreErrInfo(index, spanObj) {
			seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
				var obj = document.getElementById("moreErrInfo" + index);
				if (obj != null) {
					if (obj.style.display == "none") {

						dialog.build( {
							config : {
								width : 700,
								height : 400,
								lock : true,
								cache : true,
								content : {
									type : "element",
									elem : obj
								},
								title : $(spanObj).parent().find('.errorlist').text()
							},

							callback : function(value, dialog) {

							},
							actor : {
								type : "default"
							},
							trigger : {
								type : "default"
							}
						}).show();
					}
				}
			});
		}

	<%
    if(isComponentLockerVersionException){
		%>
	function submitVersion(){
		seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
			var resolution = $("input[name='conflict_resolution']:checked").val();
			if(resolution=='overwrite'){
				//覆盖提交需要先做校验
				if(window.validateBeforeSumbitVersion && !window.validateBeforeSumbitVersion()){
					return false;
				}
				var fdVersionObj = $('input[name="componentLockerVersionForm.fdVersion"]');
					var value = fdVersionObj.val();
				if(value==''){
					value = "1";
				}else{
					var valueInt = parseInt(value);
					valueInt = valueInt+1;
					fdVersionObj.val(valueInt);
				}
				if(window.versionOverwrite){
					versionOverwrite();
					}else{
						Com_Submit(document.forms[0],'update');
				}
				}else if(resolution=='cancel'){
					if(window.versionCancel){
						versionCancel();
					}else{
						var href = window.location.href;
						var method = Com_GetUrlParameter(href, "method");
						href = href.replace('method='+method,'method=edit');
						window.location.href = href;
					}
				}else{
					dialog.alert("<%=ResourceUtil.getString("componentLockerVersion.select.opt", "component-locker")%>");
				}
			});
		}
		<%} %>

		<%if(StringUtil.isNotNull(request.getParameter("msg_success"))) {%>
				(function() {
					if(window.history && window.history.replaceState) {
						var url = location.href;
						url = Com_SetUrlParameter(url, "msg_success", null);
						window.history.replaceState(null, null, url);
					}
				})()
		<% }%>
	</script>
	<%
		if(!isComponentLockerVersionException){
	%>
	<div class="prompt_frame_simple">
		<table>
			<tr>
				<td class="caution_msg">



					<span class="caution_msg_title"><%=msgWriter.DrawTitle()%></span>
					<%=msgWriter.DrawMessages()%>

				</td>
			</tr>
		</table>
		<%
		}else{

		%>

		<script type="text/javascript">

			var modifyPerson='${modifyPerson }';
			var modifyTime='${modifyTime }';
			var buttons = [
				{
				name : "<%=ResourceUtil.getString("button.continue.submit")%>",
				value : true,
				focus : true,
				fn : function(value, dialog) {
					var fdVersionObj = $('input[name="componentLockerVersionForm.fdVersion"]');
					var value = fdVersionObj.val();
					if(value==''){
						value = "1";
					}else{
						var valueInt = parseInt(value);
						valueInt = valueInt+1;
						fdVersionObj.val(valueInt);
					}
					if(window.versionOverwrite){
						versionOverwrite();
					}else{

						Com_Submit(document.forms[0],'update');
					}
				}
			},
			{
				name : "<%=ResourceUtil.getString("button.cancel.refresh")%>",
				value : true,
				focus : true,
				fn : function(value, dialog) {
					if(window.versionCancel){
						versionCancel();
					}else{
						var href = window.location.href;
						var method = Com_GetUrlParameter(href, "method");
						href = href.replace('method='+method,'method=edit');
						window.location.href = href;
					}
				}
			}
		];
		var content = {"html":"当前文档已被【"+modifyPerson+"】"+modifyTime+"分钟之前提交，继续提交将覆盖之前更新的内容。</br>是否继续提交？",
			"title":"文档已被其他人更新",
			"width":"500px","height":"200px","buttons":buttons};
		setTimeout(function(){
			seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
				dialog.confirm(content,function(isOk){

				},null);

			});
		},1000);

	</script>

</div>
<%} %>
<% }%>
