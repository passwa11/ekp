<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="content">
		<%-- <ui:panel layout="sys.ui.panel.light" scroll="false" toggle="false">
			<ui:content title="" toggle="false"> --%>
				<div style="width:100%;overflow: hidden;">
				<ui:toolbar style="float:right;">
					<ui:button text="${lfn:message('button.refresh')}" 
							onclick="location.reload();">
					</ui:button>
					<kmss:auth requestURL="/sys/time/sys_time_work/sysTimeWork.do?method=add&sysTimeAreaId=${JsParam.sysTimeAreaId}" requestMethod="GET">
						<ui:button text="${lfn:message('button.add')}" 
							onclick="Com_OpenWindow('${LUI_ContextPath}/sys/time/sys_time_work/sysTimeWork.do?method=add&sysTimeAreaId=${JsParam.sysTimeAreaId}');">
						</ui:button>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_work/sysTimeWork.do?method=deleteall&sysTimeAreaId=${JsParam.sysTimeAreaId}" requestMethod="GET">
						<ui:button text="${lfn:message('button.deleteall')}" onclick="delDoc();"></ui:button>
					</kmss:auth>
				</ui:toolbar>
				</div>
				<list:listview id="listview">
					<ui:source type="AjaxJson">
							{url:'/sys/time/sys_time_work/sysTimeWork.do?method=list&sysTimeAreaId=${JsParam.sysTimeAreaId}&forward=listUi&rowsize=8'}
					</ui:source>
					  <!-- 列表视图 -->	
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
						rowHref="/sys/time/sys_time_work/sysTimeWork.do?method=view&fdId=!{fdId}"  name="columntable">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial> 
						<list:col-auto props="hbmStartTime;fdWeekStartTime;timeType;sysTimeCommonTime.fdName;timeWorkColor;docCreator.fdName;docCreateTime"></list:col-auto>
					</list:colTable>
				</list:listview>
				<list:paging></list:paging>	
			<%-- </ui:content>
		</ui:panel>  --%>
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {

				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//删除
				window.delDoc = function(){
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							var del_load = dialog.loading();
							$.post('<c:url value="/sys/time/sys_time_work/sysTimeWork.do?method=deleteall&sysTimeAreaId=${JsParam.sysTimeAreaId}"/>',
									$.param({"List_Selected":values},true),function(data){
									if(del_load!=null)
										del_load.hide();
									if(data!=null && data.status==true){
										topic.publish("list.refresh");
										dialog.success('<bean:message key="return.optSuccess" />');
									}else{
										dialog.failure('<bean:message key="return.optFailure" />');
									}
								},'json').error(function(){
									dialog.failure('<bean:message key="errors.noRecord" />');
									if(del_load!=null)
										del_load.hide();
									topic.publish("list.refresh");
								});
						}
					});
				};
			});
			window.onload=function(){
				//window.frameElement.style.height="500px";
			}
		</script>
	</template:replace>
</template:include>