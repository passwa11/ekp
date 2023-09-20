<%@ page import="java.util.Calendar" %>
<%@ page import="com.landray.kmss.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple4list">
	<template:replace name="head">
	<style>
	.com_qrcode, .com_gototop{
		display:none !important
	}
	</style>
	</template:replace>
	<template:replace name="nav">
		<!-- 新建按钮 -->
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('sys-attend:module.sys.attend') }" />
			<ui:varParam name="title" value="${lfn:message('sys-attend:table.sysAttendHisCategory') }">
			</ui:varParam>				
		</ui:combin>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${ lfn:message('sys-attend:sysAttendCategory.attend') }">
				<!-- 查询条件  -->
				<list:criteria id="cateAttendCriteria" expand="false">
					<list:cri-ref title="${lfn:message('sys-attend:sysAttendCategory.attend') }" key="fdName" ref="criterion.sys.docSubject"/>
					<list:cri-auto modelName="com.landray.kmss.sys.attend.model.SysAttendHisCategory" property="fdBeginTime" />

				</list:criteria>
				 
				<!-- 列表工具栏 -->
				<div class="lui_list_operation" >
					<!-- 排序 -->
					<div class="lui_list_operation_sort_btn">
						<div class="lui_list_operation_sort_toolbar">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
							   <list:sort property="sysAttendHisCategory.docAlterTime" text="${ lfn:message('sys-attend:sysAttendCategoryTargetNew.docAlterTime') }" group="sort.list" value="down"></list:sort>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">	
						<list:paging layout="sys.ui.paging.top" > 		
						</list:paging>
					</div>
				</div>
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
			 	<list:listview id="listview">
					<ui:source type="AjaxJson">
							{url:'/sys/attend/sys_attend_his_category/sysAttendHisCategory.do?method=list'}
					</ui:source>
					<!-- 列表视图 -->	
					<list:colTable isDefault="false" name="columntable">
						<list:col-serial></list:col-serial>
						<list:col-html style="width:0px;">{$ <input type="hidden" id="_{%row['fdId']%}" value="{%row['_fdStatus']%}"> $}</list:col-html>
						<list:col-html title="${lfn:message('sys-attend:sysAttendCategory.attend.fdName')}">{$ <span class="com_subject"> {%row['fdName']%} </span>$}</list:col-html>
						<list:col-html title="${lfn:message('sys-attend:sysAttendMain.export.shouldTime.attend')}">{$ {%row['fdBeginTime']%}-{%row['fdEndTime']%} $}</list:col-html>
						<list:col-html title="${lfn:message('sys-attend:sysAttendMain.operation')}">{$ <span class="com_subject" style="    cursor: pointer;" onclick=editHis("{%row['fdId']%}")> ${lfn:message('button.edit')} </span>$}</list:col-html>
					</list:colTable>
				</list:listview> 
				 
			 	<list:paging></list:paging>	
			</ui:content>
		</ui:tabpanel>
		 
	 	
	 	<script>
		seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){

			topic.subscribe('successReloadPage', function() {
				topic.publish('list.refresh');
			});
			window.editHis =function(id){
				Com_OpenWindow("<c:url value='/sys/attend/sys_attend_his_category/sysAttendHisCategory.do?method=edit&fdId='/>"+id);
			}
			window.switchAttendPage = function(url,hash){
				url = Com_SetUrlParameter(url,'j_iframe','true');
				url = Com_SetUrlParameter(url,'j_aside','false');
				if(hash){
					url = url + hash;
				}
				LUI.pageOpen(url,'_rIframe');
			}			
		});
		</script>
	</template:replace>
</template:include>
