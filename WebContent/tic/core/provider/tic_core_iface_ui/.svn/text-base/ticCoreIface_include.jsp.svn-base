<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/tic/core/tic_ui_list.jsp">
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" style="height:40px;line-height:40px;" id="categoryId">
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_parent">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('tic-core-provider:tic.core.provider') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('tic-core-provider:table.ticCoreIface') }" href="javascript:location.reload();" target="_self">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<%-- 接口定义 --%>
	<template:replace name="content"> 
		<%-- 接口定义筛选器 --%>
		<list:criteria id="criteriaTicCoreIface" expand="true">
			<list:cri-ref key="fdIfaceName" ref="criterion.sys.docSubject">
			</list:cri-ref>
			<%-- 按标签查询 --%>
			<list:cri-criterion  title="${ lfn:message('tic-core-provider:table.ticCoreTag') }" key="tag" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="AjaxJson">
				         {"url":"/tic/core/provider/tic_core_iface_ui/ticCoreIfaceIndex.do?method=getLui_source"} 
				        </ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-auto modelName="com.landray.kmss.tic.core.provider.model.TicCoreIface" property="fdIfaceControl"/>
		</list:criteria>
		
		<%-- 接口定义显示列表 --%>
		<div class="lui_list_operation">
			<table width="100%">
				<tr>
					<td align="right">
						<ui:toolbar>
							<kmss:authShow roles="ROLE_TICSYSCOREPROVIDER_TAGMANAGE">
								<ui:button text="${lfn:message('tic-core-provider:ticCoreIface.tagMaintain')}" onclick="parent.openPage('${LUI_ContextPath}/tic/core/provider/tic_core_tag/ticCoreTag.do?method=list');" order="2" ></ui:button>
							</kmss:authShow>
							<kmss:auth requestMethod="GET"
								requestURL="/tic/core/provider/tic_core_iface/ticCoreIface.do?method=add">
							<ui:button text="${lfn:message('button.add')}" onclick="parent.addDoc('${LUI_ContextPath}/tic/core/provider/tic_core_iface/ticCoreIface.do?method=add')" order="2" ></ui:button>
							</kmss:auth>
							<kmss:auth requestMethod="GET"
								requestURL="/tic/core/provider/tic_core_iface/ticCoreIface.do?method=deleteall">
							<ui:button text="${lfn:message('button.deleteall')}" order="4" onclick="delDoc('${LUI_ContextPath}/tic/core/provider/tic_core_iface/ticCoreIface.do?method=deleteall')"></ui:button>
							</kmss:auth>
						</ui:toolbar>						
					</td>
				</tr>
			</table>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/tic/core/provider/tic_core_iface_ui/ticCoreIfaceIndex.do?method=list'}
			</ui:source>
			<%--列表形式--%>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/tic/core/provider/tic_core_iface/ticCoreIface.do?method=view&fdId=!{fdId}" name="columntable">
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
	</template:replace>
</template:include>