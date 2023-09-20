<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple4list">
<template:replace name="head">
		<style>
			.lui-attend-stat-table {
			    padding: 0px 30px;
			}
		</style>
	</template:replace>
	<template:replace name="content">
		<div class="lui-attend-stat-table">
		<div class="lui_list_operation">
					<div style='color: #979797;float: left;padding-top:1px;'>
						${ lfn:message('list.orderType') }：
					</div>
					<div style="float:left">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
								<list:sort property="sysAttendMain.docCreateTime" text="${lfn:message('sys-attend:sysAttendMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
							</ui:toolbar>
						</div>
					</div>
				</div>
				
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
					<list:listview id="attendValidListview" cfg-needMinHeight="false">
						<ui:source type="AjaxJson">
							{url:'/sys/attend/sys_attend_main/sysAttendMain.do?method=validListDetail&fdEndTime=${fdEndTime}&fdTargetId=${fdTargetId}'}
						</ui:source>
						<!-- 列表视图 -->	
						<list:colTable isDefault="false"
							rowHref="/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId=!{fdId}"  name="columntable">
							<list:col-serial></list:col-serial>
							<list:col-auto props="docCreator.fdName;docCreateTime;fdSignType;fdLocation;fdAppName;fdCategory.fdName;fdStatus;fdState"></list:col-auto>
						</list:colTable>
					<ui:event topic="list.loaded">
					setTimeout(function(){
						onResize();
					},100);
				</ui:event>
				</list:listview>
				<list:paging></list:paging>
			</div>
			<script>
				seajs.use([ 'lui/jquery' ], function($) {
					window.onResize = function() {
						$(window.parent.document).find('iframe#validIframe').height(
								$(document.body).height());
					}
				})
		</script>
	</template:replace>
</template:include>