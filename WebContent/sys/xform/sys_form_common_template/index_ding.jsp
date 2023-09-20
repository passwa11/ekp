<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<c:if test='<%="true".equals(SysFormDingUtil.getEnableDing())%>'>
		<template:replace name="head">
			<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/ding_list.css?s_cache=${LUI_Cache }"/>
		</template:replace>
	</c:if>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-xform:sysFormCommonTemplate.fdName') }">
			</list:cri-ref>
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
						<list:sortgroup>
							<list:sort property="fdCreateTime" text="${lfn:message('sys-xform:sysFormCommonTemplate.fdCreateTime') }" group="sort.list" value="down"></list:sort>
							<list:sort property="fdName" text="${lfn:message('sys-xform:sysFormCommonTemplate.fdName') }" group="sort.list"></list:sort>
						</list:sortgroup>
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
						<kmss:auth requestURL="/sys/xform/sys_xform_template/sysFormCommonTemplate.do?method=add" requestMethod="GET">
						     <ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/xform/sys_xform_template/sysFormCommonTemplate.do?method=deleteall" requestMethod="GET">
							<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						</kmss:auth>
						<%-- <c:import url="/sys/xform/lang/include/sysFormCommonMultiLang_button_new.jsp" charEncoding="UTF-8">
							<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate" />
							<c:param name="isCommonTemplate" value ="true"/>
						</c:import> --%>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=list&fdModelName=${JsParam.fdModelName}&fdMainModelName=${JsParam.fdMainModelName}&fdKey=${JsParam.fdKey}&newUi=true'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" onRowClick="openDetail('!{fdId}')">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,fdCreator.fdName,fdCreateTime,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do">
							<c:param name="method" value="add" />
							<c:param name="fdModelName" value="${JsParam.fdModelName}" />
							<c:param name="fdKey" value="${JsParam.fdKey}" />
							<c:param name="fdMainModelName" value="${JsParam.fdMainModelName}" />
					</c:url>');
		 			//Com_OpenWindow('<c:url value="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=add&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}&fdMainModelName=${JsParam.fdMainModelName}"/>');
		 		};
		 		window.openDetail = function(fdId) {
		 			Com_OpenWindow('<c:url value="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=view&fdId='+fdId+'&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}&fdMainModelName=${JsParam.fdMainModelName}'"/>);
		 		};
		 		
		 	// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do" />?method=edit&fdId=' + id+'&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}&fdMainModelName=${JsParam.fdMainModelName}');
		 		};
		 		
		 		window.deleteAll = function(id){
					var values = [];
					if(id){
						values.push(id);
					}else{
					   $("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
					   });
					}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '<c:url value="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=deleteall&fdMainModelName=${JsParam.fdMainModelName}"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values},true),
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: delCallback
						   });
						}
					});
				};
				window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
						topic.publish("list.refresh");
					}
					dialog.result(data);
				};
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
		 	});
	 	</script>
	</template:replace>
</template:include>
