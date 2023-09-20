<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('hr-ratify:module.hr.ratify') }"></c:out>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-ratify:module.hr.ratify') }" href="/hr/ratify/" target="_self"></ui:menu-item>
			<ui:menu-item text="员工关系"></ui:menu-item>
			<ui:menu-item text="入职管理"></ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);
			Com_IncludeCSSFiles(['../resource/style/lib/common.css','../resource/style/hr.css']);
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript" src="${LUI_ContextPath}/hr/staff/resource/js/hrImageUtil.js?s_cache=${LUI_Cache}"></script>
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/hr/staff/resource/css/hr_box.css?s_cache=${LUI_Cache}">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
			<list:cri-ref key="searchKey" ref="criterion.sys.docSubject" title="${ lfn:message('hr-ratify:hrStaffPersonInfo.search.placehoder') }" />
			<%-- <list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffEntry" property="fdMobileNo" /> --%>
			<list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffEntry" property="fdPlanEntryDept" />
			<list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffEntry" property="fdPlanEntryTime" />
			<list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffEntry" property="fdQRStatus" />
		</list:criteria>
		<!-- 排序 -->
		<div class="lui_list_operation">
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
					<list:sortgroup>
					    <list:sort property="fdPlanEntryTime" text="${lfn:message('hr-staff:hrStaffEntry.fdPlanEntryTime') }" group="sort.list" value="down"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" >
				</list:paging>
			</div>
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="2">
						<kmss:authShow roles="ROLE_HRSTAFF_CREATE">
							<ui:button text="${lfn:message('hr-staff:hrStaffEntry.add.employer') }" onclick="addStaff();"  order="1"/>
						</kmss:authShow>
						<kmss:authShow roles="ROLE_HRSTAFF_CREATE">
							<ui:button text="${lfn:message('hr-staff:hrStaffEntry.create.title') }" onclick="add();" order="2"></ui:button>
						</kmss:authShow>
						<c:if test="${param.fdStatus eq '1' }">
							<ui:button text="${lfn:message('hr-staff:hrStaffEntry.abandon.employment.batch') }" onclick="abandonEntryBatch();" order="3"></ui:button>
							<kmss:authShow roles="ROLE_HRRATIFY_STAFF_CONCERN_BATCH">
								<kmss:auth requestURL="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=fileUpload">
									<ui:button text="${lfn:message('hr-staff:hrStaffPerson.batch.add.btn')}" onclick="_modify();" order="5"></ui:button>
								</kmss:auth>
								<ui:button text="${lfn:message('hr-staff:hrStaffEntry.batch.add.btn')}" onclick="batchAdd();" order="5"></ui:button>
							</kmss:authShow>
						</c:if>
						<kmss:authShow roles="ROLE_HRRATIFY_STAFF_CONCERN_BATCH">
							<ui:button text="${lfn:message('button.export')}" id="export" onclick="exportEntry(${param.fdStatus })" order="3"></ui:button>
						</kmss:authShow>
						<kmss:authShow roles="ROLE_HRSTAFF_DELETE">
							<ui:button text="${lfn:message('button.deleteall') }" onclick="del();" order="4"></ui:button>
						</kmss:authShow>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 列表 -->
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/hr/staff/hr_staff_entry/hrStaffEntry.do?method=entryManageList&fdStatus=${param.fdStatus }'}
			</ui:source>
			<!-- 列表视图 -->	
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="${param.fdStatus eq 2 ? '/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=!{rowHref}' : '/hr/staff/hr_staff_entry/hrStaffEntry.do?method=view&fdId=!{rowHref}' }" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial> 
				<c:if test="${param.fdStatus eq 1 }">
					<list:col-auto props="fdName;fdPlanEntryDept;fdOrgPosts;fdPlanEntryTime;fdMobileNo;fdEmail;fdQRStatus;creator;operations"></list:col-auto>
				</c:if>
				<c:if test="${param.fdStatus eq 2 }">
					<list:col-auto props="fdName;fdStaffNo;fdPlanEntryDept;fdOrgPosts;fdPersonStatus;fdEntryTime;fdMobileNo;fdEmail"></list:col-auto>
				</c:if>
				<c:if test="${param.fdStatus eq 3 }">
					<list:col-auto props="fdName;fdPlanEntryDept;fdOrgPosts;fdAbandonEntryTime;fdMobileNo;fdEmail;fdAbandonReason;fdAbandonRemark;creator;docCreateTime;operations"></list:col-auto>
				</c:if>
			</list:colTable>
		</list:listview> 
		<list:paging></list:paging>
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/dialog','lui/topic', 'lang!sys-ui'], function($, dialog , topic, ui_lang) {
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					setTimeout(function() {
						topic.publish('list.refresh');
					}, 100);
				});
				window.add = function() {
				    Com_OpenWindow('<c:url value="/hr/staff/hr_staff_entry/hrStaffEntry.do" />?method=addEntryMobile');
				};
				window.addStaff = function(){
	    			Com_OpenWindow('<c:url value="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do" />?method=add');
	    		}
				window.check = function(id) {
					if(id){
						var url = '/hr/staff/hr_staff_entry/hrStaffEntry.do?method=editCheck&fdId='+id;
						dialog.iframe(url,'确认到岗',function(value){
							if(value == 'success')
								topic.publish("list.refresh");
						},{
							width:580,height:650,
							buttons : [
								{
									name : ui_lang['ui.dialog.button.ok'], value : true, focus : true,
								    fn : function(value,_dialog) {
								  		if(_dialog.frame && _dialog.frame.length > 0){
											var _frame = _dialog.frame[0];
								          	var contentWindow = $(_frame).find("iframe")[0].contentWindow;
								          	if(contentWindow.clickOk()) {
												_dialog.hide(value);
											}
								  		}
									}
								},
								{
									name : ui_lang['ui.dialog.button.cancel'], value : false, styleClass : 'lui_toolbar_btn_gray',
								   	fn : function(value, dialog) {
								     	dialog.hide(value);
								   	}
								}
							]
						});
					}
				};
				window.addRatifyEntry = function(id) {
					if(id){
						dialog.category('com.landray.kmss.hr.ratify.model.HrRatifyTemplate','docTemplateId','docTemplateName',false,function(rtn){
							if(rtn != false&&rtn != null){
								var tempId = rtn.id;
								var tempName = rtn.name;
								if(tempId !=null && tempId != ''){
									var url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_entry/hrRatifyEntry.do?method=add&i.docTemplate="+tempId+"&fdEntryId="+id;
									Com_OpenWindow(url, '_blank');
								}
							}
						},null,null,null,null,null,'HrRatifyEntryDoc');
					}
				};
				window.abandonEntry = function(id) {
					if(id){
						var url = '/hr/staff/hr_staff_entry/hrStaffEntry.do?method=editAbandon&ids='+id;
						dialog.iframe(url,'${lfn:message('hr-staff:hrStaffEntry.abandon.employment') }',function(value){
							if(value == 'success')
								topic.publish("list.refresh");
						},{
							width:600,height:285,
							buttons : [
								{
									name : ui_lang['ui.dialog.button.ok'], value : true, focus : true,
								    fn : function(value,_dialog) {
								  		if(_dialog.frame && _dialog.frame.length > 0){
											var _frame = _dialog.frame[0];
								          	var contentWindow = $(_frame).find("iframe")[0].contentWindow;
								          	if(contentWindow.clickOk()) {
												_dialog.hide(value);
											}
								  		}
									}
								},
								{
									name : ui_lang['ui.dialog.button.cancel'], value : false, styleClass : 'lui_toolbar_btn_gray',
								   	fn : function(value, dialog) {
								     	dialog.hide(value);
								   	}
								}
							]
						});
					}
				};
				window.edit = function(id) {
					if(id)
						Com_OpenWindow('${LUI_ContextPath}/hr/staff/hr_staff_entry/hrStaffEntry.do?method=edit&fdId='+id,'_blank');
				};
				window.abandonEntryBatch = function(){
					var _values = [];
					$("input[name='List_Selected']:checked").each(function() {
						_values.push($(this).val());
					});
					if(_values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var ids = _values.join(";");
					abandonEntry(ids);
				};
				// 删除
				window.del = function(id) {
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
					var url  = '<c:url value="/hr/staff/hr_staff_entry/hrStaffEntry.do?method=deleteall"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
						if(value == true) {
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values}, true),
								dataType : 'json',
								error : function(data) {
									if(window.del_load != null) {
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(data) {
									if(window.del_load != null){ 
										window.del_load.hide();
									}
									topic.publish("list.refresh");
									dialog.result(data);
								}
						   });
						}
					});
				};
				// 批量修改
				window._modify = function() {
					seajs.use(['lui/dialog','lui/topic'],function(dialog,topic){
						dialog.iframe('/hr/ratify/hr_staff_concern/common_upload_download.jsp?uploadActionUrl=${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=fileUpload&downLoadUrl=${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=downloadTemplet', 
								'${lfn:message("hr-staff:hrStaffPerson.batch.add.btn")}', function(data) {
								topic.publish('list.refresh');
						}, {
							width : 680,
							height : 380
						});
					});
				};
				window.batchAdd = function(){
					dialog.iframe('/hr/ratify/hr_staff_concern/common_upload_download.jsp?uploadActionUrl=${LUI_ContextPath}/hr/staff/hr_staff_entry/hrStaffEntry.do?method=fileUpload&downLoadUrl=${LUI_ContextPath}/hr/staff/hr_staff_entry/hrStaffEntry.do?method=downloadTemplet', 
							'${lfn:message("hr-staff:hrStaffEntry.batch.add.btn")}', function(data) {
							topic.publish('list.refresh');
					}, {
						width : 680,
						height : 380
					});
				};
				window.exportEntry = function(fdStatus){
					var _values = [];
					$("input[name='List_Selected']:checked").each(function() {
						_values.push($(this).val());
					});
					if(_values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var ids = _values.join(";");
					var form = document.createElement('form');
					form.action = '${LUI_ContextPath}/hr/staff/hr_staff_entry/hrStaffEntry.do?method=export';
					form.method = 'POST';
					var List_Selected = document.createElement('input');
					List_Selected.type = 'hidden';
					List_Selected.name = 'List_Selected';
					List_Selected.value = ids;
					form.appendChild(List_Selected);
					var status = document.createElement('input');
					status.type = 'hidden';
					status.name = 'fdStatus';
					status.value = fdStatus;
					form.appendChild(status);
					$(document.body).append(form);
					form.submit();
				}
			});
		</script>
	</template:replace>
</template:include>