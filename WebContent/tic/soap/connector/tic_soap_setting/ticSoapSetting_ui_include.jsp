<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/tic/core/tic_ui_list.jsp">
	<%-- 右边框内容 --%>
	<template:replace name="content">
		<%-- 筛选器 --%>
		<list:criteria>
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${ lfn:message('tic-soap-connector:ticSoapSetting.docSubject') }">
			</list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.tic.soap.connector.model.TicSoapSetting" property="fdProtectWsdl"/>
			<list:cri-auto modelName="com.landray.kmss.tic.soap.connector.model.TicSoapSetting" property="fdCheck"/>
		</list:criteria>
		
		<%-- 显示列表按钮行 --%>
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<div style="float: right">
				<div style="display: inline-block; vertical-align: middle;">
						<ui:toolbar>
							<kmss:auth requestURL="/tic/soap/connector/tic_soap_setting/ticSoapSetting.do?method=add&fdAppType=${param.fdAppType}">
								<ui:button text="${lfn:message('button.add')}" onclick="addDocByCate();" order="2" ></ui:button>
							</kmss:auth>
							<kmss:auth requestURL="/tic/soap/connector/tic_soap_setting/ticSoapSetting.do?method=deleteall&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}">
								<ui:button text="${lfn:message('button.deleteall')}" order="4" onclick="delDoc('${LUI_ContextPath}/tic/soap/connector/tic_soap_setting/ticSoapSetting.do?method=deleteall&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}')"></ui:button>
							</kmss:auth>
						</ui:toolbar>
					</div>
			</div>	
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/tic/soap/connector/tic_soap_setting/ticSoapSettingIndex.do?method=list&categoryId=${param.categoryId}&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}'}
			</ui:source>
			<%--列表形式--%>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/tic/soap/connector/tic_soap_setting/ticSoapSetting.do?method=view&fdId=!{fdId}&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>   
		</list:listview> 
		<script type="text/javascript">
		seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic'], function($, strutil, dialog , topic) {
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				setTimeout(function(){
						topic.publish('list.refresh');
				}, 100);
			});
			
			var cateId = "${param.categoryId}";
			window.addDocByCate = function(){
				window.open('${LUI_ContextPath}/tic/soap/connector/tic_soap_setting/ticSoapSetting.do?method=add&categoryId='+ cateId+'&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}');
			};
			//根据筛选器分类异步校验权限
			topic.subscribe('criteria.changed',function(evt){
				if(!cateId){
					cateId="";
				}
				//每次都重置分类id的值,因为可能存在直接点叉清除分类筛选项
				cateId = parent.getCateId(evt, cateId);
			});
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
		window.addFunc=function(fdId){
			Com_OpenWindow('<c:url value="/tic/soap/connector/tic_soap_main/ticSoapMain.do" />?method=add&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}&wsServerSettingId='+fdId);
		}
		</script>
		<br>
	 	<list:paging></list:paging>
		<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
	</template:replace>
</template:include>