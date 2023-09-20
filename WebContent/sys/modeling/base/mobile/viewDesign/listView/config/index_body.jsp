<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.profile.list">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/newIndexBody.css?s_cache=${LUI_Cache}" />
	</template:replace>
	<template:replace name="content">
		<div class="new_listview_tips">
			<div class="new_listview_text">
				<i></i>
				<div class="new_listview_tips_content">
					${lfn:message('sys-modeling-base:listview.old.version.only.maintenance') }
				</div>
			</div>
			<div class="new_listview_button_new" onclick="goToNewVersion()">
				<i style=""></i>
				<div class="new_listview_button_text">${lfn:message('sys-modeling-base:listview.go.to.new.version') }</div>
			</div>
		</div>
	<!-- 筛选器 -->
	<list:criteria id="criteria1">
		<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-modeling-base:modelingAppListview.fdName') }"></list:cri-ref>
	</list:criteria>
	
	<!-- 操作栏 -->
	<div class="lui_list_operation">
		<!-- 全选 -->
		<div class="lui_list_operation_order_btn">
			<list:selectall></list:selectall>
		</div>
		<!-- 分割线 -->
		<div class="lui_list_operation_line"></div>
		<!-- 排序 -->
		<div class="lui_list_operation_sort_btn">
			<div class="lui_list_operation_order_text">
				${ lfn:message('list.orderType') }：
			</div>
			<div class="lui_list_operation_sort_toolbar">
				<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					<list:sort property="fdName" text="${lfn:message('sys-modeling-base:modelingAppListview.fdName') }" group="sort.list" ></list:sort>
				</ui:toolbar>
			</div>
		</div>
		<!-- 分页 -->
		<div class="lui_list_operation_page_top">	
			<list:paging layout="sys.ui.paging.top" > 		
			</list:paging>
		</div>
		<!-- 操作按钮 -->
		<div style="float:right">
			<div style="display: inline-block;vertical-align: middle;">
				<ui:toolbar id="Btntoolbar">
					<!-- 增加 -->
					<ui:button text="${lfn:message('button.add')}" onclick="addListview()" order="1" ></ui:button>
					<!-- 删除 -->
					<ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="2" ></ui:button>
				</ui:toolbar>
			</div>
		</div>
	</div>
	</div>
	<ui:fixed elem=".lui_list_operation"></ui:fixed>
	<!-- 内容列表 -->
	<list:listview>
		<ui:source type="AjaxJson">
			{url:'/sys/modeling/base/mobile/modelingAppMobileListView.do?method=list&fdModelId=${param.fdModelId}'}
		</ui:source>
		<list:colTable isDefault="true" layout="sys.ui.listview.columntable" onRowClick="edit('!{fdId}')" >
			<list:col-checkbox></list:col-checkbox>
			<list:col-auto props="fdName,docCreator,docCreateTime"></list:col-auto>
		</list:colTable>
		<ui:event topic="list.loaded">
			Dropdown.init();
		</ui:event>
	</list:listview>
	<!-- 分页 -->
 	<list:paging/>
 	
 	<script type="text/javascript">
    	var listOption = {
       		param : {
       			fdAppModelId : '${param.fdModelId}'
       		},
            contextPath: '${LUI_ContextPath}',
            basePath: '/sys/modeling/base/mobile/modelingAppMobileListView.do',
            templateName: '',
            mode: '',
            templateService: '',
            templateAlert: '${lfn:message("sys-modeling-base:treeModel.alert.templateAlert")}',
            lang: {
                noSelect: '${lfn:message("page.noSelect")}',
                comfirmDelete: '${lfn:message("page.comfirmDelete")}',
                dialogTitle : '${lfn:message("sys-modeling-base:modelingAppListview.editDialogTitle")}',
                relatedDialogTitle : '${lfn:message("sys-modeling-base:modelingAppListview.relatedDialogTitle")}'
            }
        };
    	Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
	 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	 		// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				topic.publish("list.refresh");
			});
			topic.subscribe("list.loaded",function(){
                var bodyHeight = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true)-75;
                $("body",parent.document).find('#trigger_iframe').height(bodyHeight);
                $("body",parent.document).find("#modelingAside").css("display","block");
                $("body",parent.document).find(".lui_modeling_main.aside_main").eq(0).css("padding-top","10px");
            });
			// 增加
	 		window.addListview = function() {
		 		var url = '${LUI_ContextPath}/sys/modeling/base/mobile/modelingAppMobileListView.do?method=add&fdModelId=' + listOption.param.fdAppModelId; 
	 			/* openPageInDialog(url, listOption.lang.dialogTitle); */
	 			var iframe = window.parent.document.getElementById("trigger_iframe");
	    		$(iframe).attr("src",url);
	    		//修改样式
	    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
	    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","none");
	 		};
	 		// 编辑
	 		window.edit = function(id) {
		 		if(id){
		 			var url = '${LUI_ContextPath}/sys/modeling/base/mobile/modelingAppMobileListView.do?method=edit&fdModelId=' + listOption.param.fdAppModelId + '&fdId=' + id;
		 			/* openPageInDialog(url, listOption.lang.dialogTitle); */
		 			var iframe = window.parent.document.getElementById("trigger_iframe");
		    		$(iframe).attr("src",url);
		    		//修改样式
		    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
		    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","none");
		 		}
	 		};
			// 前往新版
			window.goToNewVersion = function() {
				var  url ='${LUI_ContextPath}/sys/modeling/base/views/collection/index_body.jsp?fdModelId='+ listOption.param.fdAppModelId +'&method=edit';
				var iframe = window.parent.document.getElementById("trigger_iframe");
				$(iframe).attr("src",url);
				$(window.parent.document.getElementById("modelingAsideOld")).css("display","none");
			};
	 	});
 	</script>
 	</template:replace>
 </template:include>