<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.archives.util.KmArchivesUtil"%>
<%
	String moduleUrl = request.getParameter("moduleUrl");
	String buttonTitle = request.getParameter("buttonTitle");
	if(KmArchivesUtil.isStartFile(moduleUrl)) {
%>
<!-- 合同模块归档使用-->
<kmss:auth requestURL="/km/archives/km_archives_file_template/kmArchivesFileTemplate.do?method=fileDoc&modelName=${param.fdModelName }&fdId=${param.fdId}" requestMethod="GET">
	<div class="lui_cclh_title_handle_button" onclick="file_doc('${param.fdId}');">
         <span><c:out value="${ param.buttonTitle }" /></span>
    </div>
<script>
	seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
		var serviceName = '${JsParam.serviceName}';
		var file_sendRequest = function(id) {
			var url = '${LUI_ContextPath}/km/archives/km_archives_file_template/kmArchivesFileTemplate.do?method=fileDoc';
			$.ajax({
				url: url,
				type: 'POST',
				data:{fdMainModelId:id,serviceName:serviceName},
				dataType: 'json',
				error: function(data){
					if(window.file_load!=null){
						window.file_load.hide(); 
					}
					dialog.result(data.responseJSON);
				},
				success: function(data){
					if(window.file_load!=null){
						window.file_load.hide(); 
					}
					dialog.result(data);
					//延迟刷新
					setTimeout(function() {
						window.location.reload(true);
					},1000);
				}
		   });
		};
		
		window.file_doc = function(id) {
			if(id == null) return;
			dialog.confirm('<bean:message key="confirm.filed" bundle="km-archives"/>',function(value){
				if(value==true){
					window.file_load = dialog.loading();
					//用户级配置
					if('${param.userSetting}' == 'true') {
						var checkUrl = '${LUI_ContextPath}/km/archives/km_archives_file_template/kmArchivesFileTemplate.do?method=checkHasTmp';
						$.ajax({
							url: checkUrl,
							type: 'GET',
							data:{fdModelId:id,serviceName:serviceName,cateName:'${JsParam.cateName}'},
							dataType: 'json',
							error: function(data){
								if(window.file_load!=null){
									window.file_load.hide(); 
								}
								dialog.result(data.responseJSON);
							},
							success: function(data){
								//有模板，直接归档
								if(data.hasTmp == 'true') {
									file_sendRequest(id);
								}else {
									//无模板，弹出框支持用户配置
									if(window.file_load!=null){
										window.file_load.hide(); 
									}
								    var userSettingUrl = '/km/archives/km_archives_file_template/kmArchivesFileTemplate.do?method=archivesFileUserSetting&fdMainModelName=${JsParam.fdModelName}&fdMainModelId='+id+'&serviceName='+serviceName+'&cateName=${JsParam.cateName}';
									dialog.iframe(userSettingUrl,"${lfn:message('km-archives:table.kmArchivesFileTemplate')}", function(value) {
				    					window.location.reload(true);
				    				}, {
				    					"width" : 600,
				    					"height" : 300
				    				});
								}
							}
					   });
					}else {
						file_sendRequest(id);
					}
				}
			});
		};
	});
</script>
</kmss:auth>
<%}%>