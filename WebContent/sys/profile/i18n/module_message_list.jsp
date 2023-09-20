<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.landray.kmss.web.Globals"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.profile.util.SysProfileI18nConfigUtil"%>
<%@ page import="com.landray.kmss.sys.config.util.LanguageUtil"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String module = request.getParameter("bundle");
%>
<template:include ref="default.edit" sidebar="auto" showQrcode="false">
	<template:replace name="head">
        <link rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/i18n.css?s_cache=${LUI_Cache}" />
        <style>
			.lui_handover_searchResult table th {
				text-align: center;
			}
			.lui_handover_searchResult table th:last-child, td:last-child {
				display:none;
			}
			table td.rd_title {
				font-weight: bold;
			}
			.module_title {
			   font-size: 16px;
			   font-weight: bold;
			   text-align: center;
			}
			.lui_handover_content, .lui_handover_searchResult{
				margin: auto;
			}
			.lui_form_content{
				padding:0px;
			}
			.lui_form_path_frame, .lui_form_body{
			   background-color: #fff;
			}
		</style>
	</template:replace>
	<template:replace name="content"> 
		<script>
			Com_IncludeFile("base64.js");
			seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
				// 提交操作
				submitOperation = function() {
					$("input[name=methodType]").val("module");
					// 针对某些资源信息里包含类似脚本信息，普通的提交会被拦截，所以统一进行Base64编码后再提交，后台接收到数据时进行Base64解码
					var dataArray = $("form[name=resultContentForm]").serializeArray();
					var newDatas = {};
					for(var i=0; i<dataArray.length; i++) {
						var data = dataArray[i];
						newDatas[data['name']] = Base64.encode(data['value']);
					}
					$.post('<c:url value="/sys/profile/i18n/sysProfileI18nConfig.do?method=saveMessage"/>',
							newDatas, function(result) {
						document.postCompleted = "true";
						if(result.state) {
							console.log("<%=module%>:" + "${ lfn:message('sys-profile:sys.profile.i18n.saveMessage.success') }");
						} else {
							console.log("<%=module%>:" + result.message);
						}
					}, "json");
				}
			});
			LUI.ready(function() {
				loadData("module", "<%=module%>");
				LUI("resultDataview").on("load", function() {
					$("table.tempTB").each(function(){
						$(this).css('min-width', 'auto').css('width', '953px');
					});
					var messageKeys = top.moduleMessages["<%=module%>"];
					var messageKey;
					$("#messageListTable").children("tbody").children("tr").each(function(){
						messageKey = $(this).children("td:first-child").text();
						for(var index=0; index < messageKeys.length; index++){
							if(messageKeys[index] === messageKey){
								$(this).show();
								break;
							}
						}
					});
					var docIframe = $(window.frameElement);
					var perfectlyHeight = $("div.main_body")[0].scrollHeight + 50;
					docIframe.height(perfectlyHeight);
					document.loaded = "true";
				});
			});
		</script>
		
        <div id="lui_handover_w main_body" class="lui_handover_w main_body">
			<div class="lui_handover_searchResult">
	             <%@include file="/sys/profile/i18n/message_list.jsp"%>
	        </div>
        </div>
	</template:replace>
</template:include>
