<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view" showQrcode="false">
	<c:if test="${'0'!=param.showButton}">
		<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			
			<c:if test='${isAttention=="1"}'><!--已关注-->
				<kmss:auth requestURL="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=deleteMyAttentionEcharts&fdId=${param.fdId}">
					<ui:button text="${lfn:message('dbcenter-echarts:module.echarts.nofollowing.title')}" order="1" onclick="deleteMyAttentionEcharts('${LUI_ContextPath}/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=deleteMyAttentionEcharts&fdId=${param.fdId}');"></ui:button>
				</kmss:auth>
			</c:if>
			<c:if test='${isAttention=="0"}'><!--未关注-->
				<kmss:auth requestURL="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=createMyAttentionEcharts&fdId=${param.fdId}">
					<ui:button text="${lfn:message('dbcenter-echarts:module.echarts.following.title')}" order="1" onclick="createMyAttentionEcharts('${LUI_ContextPath}/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=createMyAttentionEcharts&fdId=${param.fdId}');"></ui:button>
				</kmss:auth>
			</c:if>

				<kmss:auth requestURL="/dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
                    <ui:button text="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('dbEchartsCustom.do?method=edit&fdId=${param.fdId}','_self');"></ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
					<ui:button text="${ lfn:message('button.copy') }" onclick="Com_OpenWindow('dbEchartsCustom.do?method=clone&cloneModelId=${param.fdId}','_blank');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do?method=delete&fdId=${param.fdId}">
				    <ui:button text="${lfn:message('button.delete')}" order="4" onclick="deleteDoc('${LUI_ContextPath}/dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do?method=delete&fdId=${param.fdId}');"></ui:button>
				</kmss:auth>
				<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
			</ui:toolbar>
		</template:replace>
		<!-- 占位DIV -->
		<div style="height:10px" ></div>
	</c:if>
	<template:replace name="content">
	<kmss:windowTitle moduleKey="dbcenter-echarts:module.dbcenter.piccenter" subjectKey="dbcenter-echarts:table.dbEchartsCustom" subject="${dbEchartsCustomForm.docSubject}" />
	<center>
		<script>
		Com_IncludeFile('dbEchartsCustom.css',Com_Parameter.ContextPath+'dbcenter/echarts/db_echarts_custom/css/','css',true);
		</script>
		<c:if test="${true==param.contentPaved}">
			<style>
				.lui-chartData-info-board {
					max-width: inherit;
				}
			</style>
		</c:if>
		<c:if test="${'0'!=param.showButton}">
			<p class="txttitle">${dbEchartsCustomForm.docSubject}</p>
			<br/>		
		</c:if>
		<div class="lui-chartData-info-board" >
		  <div class="board-txt" id="borad-txt">${customTxt }</div>
		</div>
	</center>
	<c:if test="${'0'!=param.showButton}">
		<ui:tabpage expand="false" collapsed="true">
			<!--权限机制 -->
			<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="dbEchartsCustomForm" />
			<c:param name="moduleModelName" value="com.landray.kmss.dbcenter.echarts.model.DbEchartsCustom" />
			</c:import>
		</ui:tabpage>
	</c:if>
	<script>
		domain.autoResize();
		seajs.use(["lui/dialog"],function(dialog){
			window.deleteDoc = function(delUrl){
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
					if(isOk){
						Com_OpenWindow(delUrl,'_self');
					}	
				});
				return;
			};
			
			//取消关注方法方法
			window.deleteMyAttentionEcharts = function(confirmUrl){
				dialog.confirm('${lfn:message('dbcenter-echarts:module.echarts.my.following.noConfirm') }',function(isOk){
					if(isOk){
						Com_OpenWindow(confirmUrl,'_self');
					}
				});
				return;
			};

			//关注方法
			window.createMyAttentionEcharts = function(confirmUrl){
				dialog.confirm('${lfn:message('dbcenter-echarts:module.echarts.my.following.confirm') }',function(isOk){
					if(isOk){
						Com_OpenWindow(confirmUrl,'_self');
					}
				});
				return;
			};
			
		});
	</script>
	</template:replace>
</template:include>