<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="head">
		<style>
			.cacheconfigtitle {
				color: #35a1d0;
				margin-bottom: 15px;
				margin-top: 20px;
				text-align: center;
				font-size: 20px;
				line-height: 30px;
			}
		</style>
	</template:replace>
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use([ 'theme!list' ]);
			//根据name和scope清楚缓存
			window.cleanCache = function (name,scope,infoTip) {
				seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog){
					$.ajax({
						type : "POST",
						dataType : "json",
						url : "${LUI_ContextPath}/sys/cache/KmssCache.do?method=removeCaches",
						data : {
							name:name,
							scope:scope
						},
						success : function(result) {
							if(result.success){
								if(infoTip&&infoTip=='true'){
									dialog.alert('${lfn:message('sys-cache:sysCache.clean.success')}');
								}
								LUI('listview').tableRefresh();
							}else{
								dialog.alert(result.error);
							}
						},
						error : function(s, s2, s3) {

						}
					});
				});
			}
			//弹出多选框
			var dialogAllKeys = [];
			var dialogRemoveName = "";
			var dialogRemoveScope = 0;
			window.dialogRemoveKeys = function (name, keys, scope) {
				keys = keys.replace(/^(\s|\[)+|(\s|\])+$/g, '');
				dialogAllKeys = keys.split(',');
				dialogRemoveName = name;
				dialogRemoveScope = scope;
				seajs.use(['lui/dialog'], function (dialog) {
					window.removeKeyDialog = dialog.iframe('/sys/cache/removeKey_dialog.jsp?dialogRemoveName='+dialogRemoveName+'&dialogRemoveScope='+dialogRemoveScope, 'KEYS', function (rtn) {
						if(rtn){
							//刷新
							LUI('listview').tableRefresh();
						}
					}, {height:600,width:1000,params:{dialogAllKeys:dialogAllKeys}});
				});
			}

			/*清除多条缓存*/
			window.cleansysCache = function(){
				seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog){
					var scope = ${JsParam.scope};
					var cacheNamed = [];
					$("input[name='List_Selected']:checked").each(function() {
						cacheNamed.push($(this).parents("tr:first").children('td').eq(2).text());
					});
					if(cacheNamed.length == 0) {
						dialog.alert('${lfn:message('sys-cache:sysCache.clean.cache')}');
						return;
					}
					for(var i=0;i<cacheNamed.length;i++){
						if(i==cacheNamed.length-1){
							cleanCache(cacheNamed[i],scope,'true');
						}else{
							cleanCache(cacheNamed[i],scope,'false');
						}

					}
				});
			}
		</script>

		<div style="margin:5px 10px;">
			<!-- 筛选 标题-->
            <c:if test="${JsParam.scope==1}">
				<p class="cacheconfigtitle">${lfn:message('sys-cache:sysCache.local')}
					<br/>  <span style="outline: none;color: #F95A5A;"><bean:message bundle="sys-cache" key="hibernate.cache.system.tip" /></span>
				</p>
			</c:if>
			<c:if test="${JsParam.scope==2}">
				<p class="cacheconfigtitle">${lfn:message('sys-cache:sysCache.cluster')}
					<br/>  <span style="outline: none;color: #F95A5A;"><bean:message bundle="sys-cache" key="hibernate.cache.system.tip" /></span>
				</p>
			</c:if>
			<c:if test="${JsParam.scope==3}">
				<p class="cacheconfigtitle">${lfn:message('sys-cache:sysCache.redis')}
					<br/>  <span style="outline: none;color: #F95A5A;"><bean:message bundle="sys-cache" key="hibernate.cache.system.tip" /></span>
				</p>
			</c:if>
			<!-- 分页  操作 -->
			<div class="lui_list_operation">
				<div class="lui_list_operation_page_top">
					<list:paging layout="sys.ui.paging.top">
					</list:paging>
				</div>

				<div style="float:right">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar count="3">
							<ui:button text="${lfn:message('sys-cache:sysCache.clean')}" onclick="cleansysCache();" order="4" id="cleancache" />
						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation"></ui:fixed>
			<!-- 列表 -->
			<list:listview id="listview">
				<ui:source type="AjaxJson">
					{url:'/sys/cache/KmssCache.do?method=data&scope=${JsParam.scope}'}
				</ui:source>
				<!-- 列表视图 -->
				<list:colTable id="coltable_local" isDefault="false" name="columntable">
					<list:col-checkbox />
					<list:col-serial />
					<list:col-auto props="name;keys;oper" url="" />
				</list:colTable>
			</list:listview>
			<!-- 翻页 -->
			<list:paging />
		</div>
	</template:replace>
</template:include>