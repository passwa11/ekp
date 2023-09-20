<!-- 离职管理 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
    <template:replace name="body">
	    <script>
	    	seajs.use(['theme!list']);
	    	Com_IncludeFile("dialog.js");
	    	Com_IncludeCSSFiles(['../resource/style/lib/common.css','../resource/style/hr.css']);
	    </script>
	    <!-- 筛选 -->
	    <list:criteria id="criteria2">
			<list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${ lfn:message('hr-staff:hrStaffPersonInfo.criteria.fdKey') }" style="width:300px;">
			</list:cri-ref>
				<list:cri-criterion title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }" key="_fdStatus">
					<list:box-select>
						<list:item-select>
								<ui:source type="Static">
								[{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.dismissal') }',value:'dismissal'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.leave') }',value:'leave'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.retire') }',value:'retire'}]
							</ui:source>
						</list:item-select>
					</list:box-select> 
				</list:cri-criterion>
			<list:cri-ref key="_fdDept" ref="criterion.sys.dept" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }"></list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffPersonInfo" property="fdLeaveTime" />
		</list:criteria>
	  	<!-- 操作 -->
	    <div class="lui_list_operation">
	
	        <!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
               <div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
	                <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
	                    <list:sort property="fdLeaveApplyDate" text="${lfn:message('hr-staff:hrStaffPersonInfo.fdLeaveApplyDate')}" group="sort.list" />
	                </ui:toolbar>
	            </div>
	        </div>
	        <div class="lui_list_operation_page_top">
	            <list:paging layout="sys.ui.paging.top" />
	        </div>
	        <div style="float:right">
	            <div style="display: inline-block;vertical-align: middle;">
	                <ui:toolbar count="3">
	                	<kmss:authShow roles="ROLE_HRRATIFY_STAFF_CONCERN_BATCH">
	                		<ui:button text="导出" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.hr.staff.model.HrStaffPersonInfo')" />
	                	</kmss:authShow>
	                </ui:toolbar>
	            </div>
	        </div>
	    </div>
	    <ui:fixed elem=".lui_list_operation" />
	    <!-- 列表 -->
	    <list:listview id="listview">
	        <ui:source type="AjaxJson">
	            {url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=leaveManageList&personStatus=quit&beforeTime=true'}
	        </ui:source>
	        <!-- 列表视图 -->
	        <list:colTable isDefault="false" rowHref="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=!{fdId}" name="columntable">
	            <list:col-checkbox />
	            <list:col-serial/>
	            <list:col-auto props="message;fdStatus;fdLeaveStatus;fdWorkingYears;fdLeaveTime;fdLeaveReason;fdNextCompany;operations" />
	        </list:colTable>
	    </list:listview>
	    <!-- 翻页 -->
	    <list:paging />
	    <script>
	    	var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'leave',
                modelName: 'com.landray.kmss.hr.ratify.model.HrRatifyLeave',
                templateName: 'com.landray.kmss.hr.ratify.model.HrRatifyTemplate',
                basePath: '/hr/ratify/hr_ratify_leave/hrRatifyLeave.do',
                canDelete: '${canDelete}',
                mode: 'main_template',
                templateService: 'hrRatifyTemplateService',
                templateAlert: '${lfn:message("hr-ratify:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
	    	Com_IncludeFile("list.js", "${LUI_ContextPath}/hr/ratify/resource/js/", 'js', true);
		    seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lang!sys-ui'],function($, dialog, topic, ui_lang){
		    	// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					setTimeout(function() {
						seajs.use(['lui/topic'], function(topic) {
							topic.publish('list.refresh');
						});
					}, 100);
				});
		    	var cateId= '', nodeType = '',docStatus = 0;
				//根据筛选器分类异步校验权限
				topic.subscribe('criteria.spa.changed',function(evt){
					//筛选器变化时清空分类和节点类型的值
					cateId = "";  
					nodeType = "";
					var hasCate = false;
					for(var i=0;i<evt['criterions'].length;i++){
						  //获取分类id和类型
		             	  if(evt['criterions'][i].key == "docTemplate"){
		             		 hasCate = true;
		                 	 cateId= evt['criterions'][i].value[0];
			                 nodeType = evt['criterions'][i].nodeType;
		             	  }
		             	   if(evt['criterions'][i].key == 'docStatus') {
							  docStatus = evt['criterions'][i].value[0];
						  }
					}		
				});
		    	//新建文档
				window.addDoc0 = function(){
					dialog.category('com.landray.kmss.hr.ratify.model.HrRatifyTemplate','docTemplateId','docTemplateName',false,function(rtn){
						if(rtn != false&&rtn != null){
							var tempId = rtn.id;
							var tempName = rtn.name;
							if(tempId !=null && tempId != ''){
								var url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_leave/hrRatifyLeave.do?method=add&i.docTemplate="+tempId;
								Com_OpenWindow(url, '_blank');
							}
						}
					},null,null,null,null,null,'HrRatifyLeaveDoc');
				};
				//删除文档
	    		window.delDoc = function(draft){
	    			var values = [];
	    			$("input[name='List_Selected']:checked").each(function(){
	    				values.push($(this).val());
	    			});
	    			if(values.length==0){
	    				dialog.alert('${lfn:message("page.noSelect")}');
	    				return;
	    			}
	    			var url = Com_Parameter.ContextPath + 'hr/ratify/hr_ratify_leave/hrRatifyLeave.do?method=deleteall';
	    			url = Com_SetUrlParameter(url, 'categoryId', cateId);
	    			url = Com_SetUrlParameter(url, 'nodeType', nodeType); 
	    			if(draft == '0'){
	    				url = Com_Parameter.ContextPath + 'hr/ratify/hr_ratify_leave/hrRatifyLeave.do?method=deleteall&status=10';
	    			}
	    			var config = {
	    				url : url, // 删除数据的URL
	    				data : $.param({"List_Selected":values},true), // 要删除的数据
	    				modelName : "com.landray.kmss.hr.ratify.model.HrRatifyLeave" // 主要是判断此文档是否有部署软删除
	    			};
	    			// 通用删除方法
	    			function delCallback(data){
	    				topic.publish("list.refresh");
	    				dialog.result(data);
	    			}
	    			Com_Delete(config, delCallback);
	    		};
	    		window.dealLeave = function(){
	    			Dialog_Address(false,null,null,null,ORG_TYPE_PERSON,function(data){
	    				var user = data.data[0];
	    				var userId = user.id;
	    				var url = Com_GetCurDnsHost()+Com_Parameter.ContextPath+'hr/ratify/hr_ratify_leave_dr/hrRatifyLeaveDR.do?method=addLeave&fdUserId='+userId;
	    				dialog.iframe(url,'${lfn:message('hr-staff:hrStaffEntry.handlingResignation')}',function(value){
							if(value == 'success')
								topic.publish("list.refresh");
						},{
							width:800,height:700,
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
	    			});
	    		};
	    		window.checkLeave = function(fdUserId,fdRatifyLeaveId){
	    			var url = Com_GetCurDnsHost()+Com_Parameter.ContextPath+'hr/ratify/hr_ratify_leave_dr/hrRatifyLeaveDR.do?method=addLeave&fdUserId='+fdUserId+"&fdRatifyLeaveId="+fdRatifyLeaveId;
	    			dialog.iframe(url,'确认离职',function(value){
						if(value == 'success')
							topic.publish("list.refresh");
					},{
						width:800,height:700,
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
	    		};
	    		window.addRatifyRehire = function(fdStaffId){
	    			if(fdStaffId){
						dialog.category('com.landray.kmss.hr.ratify.model.HrRatifyTemplate','docTemplateId','docTemplateName',false,function(rtn){
							if(rtn != false&&rtn != null){
								var tempId = rtn.id;
								var tempName = rtn.name;
								if(tempId !=null && tempId != ''){
									var url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_rehire/hrRatifyRehire.do?method=add&i.docTemplate="+tempId+"&fdStaffId="+fdStaffId;
									Com_OpenWindow(url, '_blank');
								}
							}
						},null,null,null,null,null,'HrRatifyRehireDoc');
					}
	    		};
	    		window.abandonLeave = function(fdStaffId,fdLeaveId){
	    			var url = Com_GetCurDnsHost()+Com_Parameter.ContextPath+'hr/ratify/hr_staff_concern/hrLeaveManage_abandon.jsp?fdUserId='+fdStaffId;
	    			dialog.iframe(url,'放弃离职',function(value){
	    				if(value == "success"){
	    					topic.publish("list.refresh");
	    	   			}	
					},{
						width:444,height:205,
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
	    		};
	    		window.cancelLeave = function(fdStaffId,fdStaffName){
	    			var url = Com_GetCurDnsHost()+Com_Parameter.ContextPath+'hr/ratify/hr_staff_concern/hrLeaveManage_cancel.jsp?fdUserId='+fdStaffId+"&fdUserName="+fdStaffName;
	    			dialog.iframe(url,'撤销离职',function(value){
	    				if(value == "success"){
	    					topic.publish("list.refresh");
	    	   			}	
					},{
						width:460,height:255,
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
		    });
	    </script>
    </template:replace>
</template:include>