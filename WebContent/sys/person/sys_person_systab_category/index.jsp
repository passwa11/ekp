<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/person/resource/css/person.css?s_cache=${LUI_Cache }"/>
	</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-person:sysPersonSysTabCategory.fdName') }">
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
						<list:sort property="fdOrder" text="${lfn:message('sys-person:sysPersonSysTabCategory.fdOrder') }" group="sort.list" value="up"></list:sort>
						<list:sort property="docCreateTime" text="${lfn:message('sys-person:sysPersonSysTabCategory.docCreateTime') }" group="sort.list"></list:sort>
						<list:sort property="fdName" text="${lfn:message('sys-person:sysPersonSysTabCategory.fdName') }" group="sort.list"></list:sort>
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
					<ui:toolbar id="Btntoolbar" count="5">
						<kmss:authShow roles="ROLE_SYSPERSON_PORTAL_ADMIN">
						    <ui:button text="${lfn:message('sys-person:btn.start')}" onclick="PersonOnUpdateStatus(2);" order="1" ></ui:button>
						    <ui:button text="${lfn:message('sys-person:btn.stop')}" onclick="PersonOnUpdateStatus(1);" order="2" ></ui:button>
						    <ui:button text="${lfn:message('button.add')}" onclick="add();" order="3" ></ui:button>
						    <ui:button text="${lfn:message('button.deleteall')}" onclick="PersonOnDeleteAll();" order="4" ></ui:button>
						    <!-- 快速排序 -->
							<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.sys.person.model.SysPersonSysTabCategory"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
							</c:import>
						</kmss:authShow>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/person/sys_person_systab_category/sysPersonSysTabCategory.do?method=list&type=page'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdName,fdShortName,fdStatus,docCreateTime,docCreator.fdName,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	<div style="margin:20px 0px; border-top: 1px #C0C0C0 dashed; text-align: center; padding-top:10px;">
			<div style="display:inline;vertical-align: top;"><bean:message bundle="sys-person" key="nav.config.example" /></div>
			<div style="display:inline; margin-left:20px;">
			<!-- 配置中英文图例 -->
			<!-- <%
					pageContext.setAttribute("pageLang", ResourceUtil.getLocaleStringByUser(request));
				%>
				<c:choose>
					<c:when
						test="${empty pageScope.pageLang or pageScope.pageLang eq 'zh-cn' or pageScope.pageLang eq 'zh-hk'}">
						<img
							src="<c:url value="/sys/person/resource/images/sample_zn.jpg" />"
							style="border: 1px #C0C0C0 solid; width: 400px;">
					</c:when>
					<c:otherwise>
						<img
							src="<c:url value="/sys/person/resource/images/sample_en.jpg" />"
							style="border: 1px #C0C0C0 solid; width: 400px;">
					</c:otherwise>
				</c:choose> -->
				<div class="lui_person_nav_legend" style="margin-left:20px;">
					<div class="legend_nav"><span class="legend_txt">${lfn:message('sys-person:home.legend.nav') }</span><span class="legend_bg"></span></div>
					<div class="legend_window"><span class="legend_txt">${lfn:message('sys-person:home.legend.window') }</span><span class="legend_bg"></span></div>
					<img src="<c:url value="/sys/person/resource/images/sample.png" />" style="border:1px #C0C0C0 solid; width:400px;">
				</div>
			</div>
		</div>
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/person/sys_person_systab_category/sysPersonSysTabCategory.do?method=add&type=${HtmlParam.type}" />', '_blank');
		 		};
		 	   // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/person/sys_person_systab_category/sysPersonSysTabCategory.do" />?method=edit&fdId=' + id);
		 		};
		 		window.PersonOnUpdateStatus = function(status, id){
					var values = [];
					if(id) {
						values.push(id);
					} else {
						$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					window.del_load = dialog.loading();
					$.post('<c:url value="/sys/person/sys_person_systab_category/sysPersonSysTabCategory.do?method=updateStatus"/>',
							$.param({"List_Selected":values,"status": status},true),delCallback,'json');
				};
		 		
		 		window.PersonOnDeleteAll = function(id){
					var values = [];
					if(id) {
		 				values.push(id);
			 		} else {
						$("input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.post('<c:url value="/sys/person/sys_person_systab_category/sysPersonSysTabCategory.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
						}
					});
				};
		 		window.PersonOnDeleteById = function(id){
					var values = [];
					if(id) {
		 				values.push(id);
			 		} else {
						$("input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.get('<c:url value="/sys/person/sys_person_systab_category/sysPersonSysTabCategory.do?method=delete"/>',
									$.param({"fdId":id},true),delCallback,'json');
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
		 	});
	 	</script>
	</template:replace>
</template:include>
