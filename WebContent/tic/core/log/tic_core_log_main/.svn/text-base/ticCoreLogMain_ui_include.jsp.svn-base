<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/tic/core/tic_ui_list.jsp">
	<template:replace name="title">${ lfn:message('tic-core:module.tic.manage') }</template:replace>
<%-- 	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" style="height:40px;line-height:40px;" id="categoryId">
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_parent">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('tic-core:module.tic.core') }" href="/tic/core/" target="_parent">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('tic-core-log:ticCoreLogMain.moduleLog') }">
			</ui:menu-item>
			<ui:menu-item text="${displayName}"  target="_self">
			</ui:menu-item>
			
			<ui:menu-item text="${logName}" href="javascript:location.reload();" target="_self">
			</ui:menu-item>
		</ui:menu>
	</template:replace> --%>
	
	<%-- 右边框内容 --%>
	<template:replace name="content">
		<%-- 筛选器 --%>
		<list:criteria>
			<list:cri-ref key="funcName" ref="criterion.sys.docSubject"  title="${ lfn:message('tic-core-log:ticCoreLogMain.funcName') }">
			</list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.tic.core.log.model.TicCoreLogMain" 
				property="fdStartTime;fdEndTime" />
		</list:criteria>
		<%-- 显示列表按钮行 --%>
		<div class="lui_list_operation">
			<table width="100%">
				<tr>
					<td style='color: #979797;width: 65px;'>
						${ lfn:message('list.orderType') }：
					</td>
					<td style='width: 180px;'>
						<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
							<list:sort property="fdStartTime" text="${lfn:message('tic-core-log:ticCoreLogMain.fdStartTime') }" group="sort.list"></list:sort>
							<list:sort property="fdEndTime" text="${lfn:message('tic-core-log:ticCoreLogMain.fdEndTime') }" group="sort.list"></list:sort>
						</ui:toolbar>
				</td>		
				<td>				
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			
					</td>
					
			
			
					<td align="right">
							<ui:toolbar>
							<ui:button text="${lfn:message('home.help')}" order="1" onclick="window.open(Com_Parameter.ContextPath+ 'tic/core/log/help/log_main.html');"></ui:button>
						<kmss:auth requestURL="/tic/core/log/tic_core_log_main/ticCoreLogMain.do?method=deleteall">
								<ui:button text="${lfn:message('button.deleteall')}" order="4" onclick="delDoc('${LUI_ContextPath}/tic/core/log/tic_core_log_main/ticCoreLogMain.do?method=deleteall')"></ui:button>
							</kmss:auth>
						</ui:toolbar>						
					</td>
				</tr>
			</table>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/tic/core/log/tic_core_log_main/ticCoreLogMainIndex.do?method=list&isError=${param.isError}&fdType=${param.fdType}&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}'}
			</ui:source>
			<%--列表形式--%>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/tic/core/log/tic_core_log_main/ticCoreLogMain.do?method=view&fdId=!{fdId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>   
		</list:listview> 
		<script type="text/javascript">
		seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic'], function($, strutil, dialog , topic) {
		 	//删除
	 		window.delDoc = function(url){
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
						window.del_load = dialog.loading();
						$.post('<c:url value="'+ url +'"/>',
								$.param({"List_Selected":values},true),delCallback,'json');
					}
				});
			};
			window.delCallback = function(data){
				if(window.del_load!=null) {
					window.del_load.hide();
				}
				if(data!=null && data.status==true){
					topic.publish("list.refresh");
					dialog.success('<bean:message key="return.optSuccess" />');
				}else{
					dialog.failure('<bean:message key="return.optFailure" />');
				}
			};
	 	});
		</script>
		<br>
	 	<list:paging></list:paging>
		<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
	</template:replace>
</template:include>