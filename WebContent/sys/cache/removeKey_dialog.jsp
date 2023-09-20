<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.dialog">
	<template:replace name="head">
		<style>
			#tableDiv{
				display: flex;
				flex-direction: row;
				flex-wrap: wrap;
				border: 1px solid black;
				justify-content: space-around;
			}
			.tdSpan{
				display: inline-block;
				flex:1 1 auto;
				width:24%;
				border: 1px solid black;
			}
		</style>
		<script type="text/javascript">
			var __dialogAllKeys;
			var removeName = '${JsParam.dialogRemoveName}';
			var removeScope = '${JsParam.dialogRemoveScope}';
			var drawTable = function(){
				var allKeys =__dialogAllKeys.dialogAllKeys;
				seajs.use(['lui/jquery'], function($){
					var tableHtml="<table class=\"tb_normal\" width=\"95%\" style=\"table-layout:fixed;\"><tbody>";
					var tableEnd ="</tbody></table>";
					for(var i = 0; i < allKeys.length; i ++){
						if(allKeys[i] && allKeys[i] != ""){
							var inputH = "<td style=\"word-wrap:break-word;\"><input type=\"checkbox\" name=\"removeKeys\" value=\""+allKeys[i].replace(/(^\s*)|(\s*$)/g, "")+"\"/>"+allKeys[i].replace(/(^\s*)|(\s*$)/g, "")+"</td>";
							if(i%3==0){
								inputH="<tr>"+inputH;
							}else if(i%3==2){
								inputH=inputH+"</tr>";
							}
							tableHtml+=inputH;
						}
					}
					$("#tableDiv").append(tableHtml+tableEnd);
				})
			}
			var interval = setInterval(____Interval, "50");
			function ____Interval() {
				if (!window['$dialog'])
					return;
				__dialogAllKeys = $dialog.___params;
				drawTable();
				clearInterval(interval);
			}
			//移除某些key的缓存
			window.removeKeys = function () {
				seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog){
					var keys = "";
					$("input[name='removeKeys']:checked").each(function(i){
						 keys += $(this).val() + ",";
					});
					if(!keys){
						dialog.alert('${lfn:message('sys-cache:sysCache.remove.select')}');
						return;
					}
					$.ajax({
						type : "POST",
						dataType : "json",
						url : "${LUI_ContextPath}/sys/cache/KmssCache.do?method=removeKeys",
						data : {
							name:removeName,
							keys:keys,
							scope:removeScope
						},
						success : function(result) {
							if(result.success){
								dialog.alert('${lfn:message('sys-cache:sysCache.remove.success')}');
								$dialog.hide(true);
							}else{
								dialog.alert(result.error);
							}
						},
						error : function(s, s2, s3) {

						}
					});
				});
			}
		</script>
	</template:replace>
	<template:replace name="content">
		<div style="width: 95%;margin: 10px auto;">
			<div style="text-align: left;margin-top: 15px;">
				<span>${lfn:message('sys-cache:sysCache.remove.select')}</span>
			</div>
			<div style="text-align: right;margin-top: -20px;">
				<ui:button text="'${lfn:message('sys-cache:sysCache.remove.name')}'" onclick="removeKeys()"></ui:button>
			</div>
			<br/>
		    <div id="tableDiv" style="width: 98%"></div>
		</div>
		<%--<ui:button text="remove" onclick="removeKeys()"></ui:button>--%>
	</template:replace>
</template:include>
