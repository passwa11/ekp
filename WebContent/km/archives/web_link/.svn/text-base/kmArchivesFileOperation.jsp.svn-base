<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@page import="com.landray.kmss.km.archives.util.KmArchivesUtil"%>
<%
	String fdId = request.getParameter("fdId");
	String fdModelName = request.getParameter("fdModelName");
	String serviceName = request.getParameter("serviceName");
	String userSetting = request.getParameter("userSetting");
	String cateName = request.getParameter("cateName");
	String moduleUrl = request.getParameter("moduleUrl");
	
	request.setAttribute("fdId", fdId);
	request.setAttribute("fdModelName", fdModelName);
	request.setAttribute("serviceName", serviceName);
	request.setAttribute("userSetting", userSetting);
	request.setAttribute("cateName", cateName);
	request.setAttribute("moduleUrl", moduleUrl);
	
	if(KmArchivesUtil.isStartFile(moduleUrl)) {
%>
<!-- 归档-->
<c:set var="canArchive" value="0" />
<kmss:auth requestURL="/km/archives/km_archives_file_template/kmArchivesFileTemplate.do?method=fileDoc&modelName=${fdModelName }&fdId=${fdId}" requestMethod="GET">
	<c:set var="canArchive" value="1" />
<script>
	seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
		$(window).load(function(){
			file_doc('${fdId}');
		});
		
		var serviceName = '${serviceName}';
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
					if('${userSetting}' == 'true') {
						var checkUrl = '${LUI_ContextPath}/km/archives/km_archives_file_template/kmArchivesFileTemplate.do?method=checkHasTmp';
						$.ajax({
							url: checkUrl,
							type: 'GET',
							data:{fdModelId:id,serviceName:serviceName,cateName:'${cateName}'},
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
								    var userSettingUrl = '/km/archives/km_archives_file_template/kmArchivesFileTemplate.do?method=archivesFileUserSetting&fdMainModelName=${fdModelName}&fdMainModelId='+id+'&serviceName='+serviceName+'&cateName=${cateName}';
									dialog.iframe(userSettingUrl,"${lfn:message('km-archives:table.kmArchivesFileTemplate')}", function(value) {
				    					//window.location.reload(true);
				    					if (value) {
				    						window.opener=null;
				    						window.open('','_self');
				    						window.close();
				    					} else {
				    						window.opener=null;
				    						window.open('','_self');
				    						window.close();
				    					}
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
				} else {
					window.opener=null;
					window.open('','_self');
					window.close();
				}
			});
		};
	});
</script>
</kmss:auth>

<c:if test="${canArchive eq '0'}">
	<script>
		seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
			dialog.alert("对不起，您没有归档操作的权限",function(){
				window.opener=null;
				window.open('','_self');
				window.close();
			});
		});
	</script>
</c:if>

<%}%>