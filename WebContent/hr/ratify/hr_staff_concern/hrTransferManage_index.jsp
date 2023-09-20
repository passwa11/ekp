<!-- 人事调动 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
    <template:replace name="body">
		<link rel="stylesheet" href="../resource/style/hr.css">
	    <script>
	    	seajs.use(['theme!list']);
	    	Com_IncludeFile("dialog.js");
	    </script>
	    <!-- 筛选 -->
	    <list:criteria id="criteria2">
	    	 <list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${ lfn:message('hr-ratify:hrStaffPersonInfo.search.placehoder') }"></list:cri-ref>
		     <list:cri-ref key="_fdDept" ref="criterion.sys.dept" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }"></list:cri-ref>
	  	</list:criteria>
	  	<!-- 操作 -->
	    <div class="lui_list_operation">
	        <!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<%-- <div class="lui_list_operation_line"></div>
	        <div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
	                <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
	                    <list:sort property="hrRatifyRemove.docCreateTime" text="${lfn:message('hr-ratify:hrRatifyMain.docCreateTime')}" group="sort.list" />
	                    <list:sort property="hrRatifyRemove.docPublishTime" text="${lfn:message('hr-ratify:hrRatifyMain.docPublishTime')}" group="sort.list" />
	                </ui:toolbar>
	            </div>
	        </div> --%>
	        <div class="lui_list_operation_page_top">
	            <list:paging layout="sys.ui.paging.top" />
	        </div>
	        <div style="float:right">
	            <div style="display: inline-block;vertical-align: middle;">
	                <ui:toolbar count="4">
	                    <ui:button text="${ lfn:message('hr-staff:hrStaff.import.button.add') }" onclick="importTransfer();" order="1" />
	                    <ui:button text="${ lfn:message('hr-staff:hrStaff.import.button.export') }" onclick="exportStaff();" order="2" />
	                    <ui:button text="${ lfn:message('hr-staff:hrStaffPersonInfo.position.salary.adjust') }" onclick="addTransfer();" order="3" />
	                </ui:toolbar>
	            </div>
	        </div>
	    </div>
	    <ui:fixed elem=".lui_list_operation" />
	    <!-- 列表 -->
	    <list:listview id="listview">
	        <ui:source type="AjaxJson">
	            {url:'/hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=transferManageList&type=${param.type}'}
	        </ui:source>
	        <!-- 列表视图 -->
	        <list:colTable isDefault="false" name="columntable">
	            <list:col-checkbox />
	            <list:col-serial/>
				<list:col-auto props="fdName;fdArrow;fdTransferInfo;fdEffectTime;docStatus;fdIsEffective;docNumber;handlerName;operations"></list:col-auto>
			</list:colTable>
	    </list:listview>
	    <!-- 翻页 -->
	    <list:paging />
	    <script>
	    	var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'remove',
                modelName: 'com.landray.kmss.hr.ratify.model.HrRatifyRemove',
                templateName: 'com.landray.kmss.hr.ratify.model.HrRatifyTemplate',
                basePath: '/hr/ratify/hr_ratify_remove/hrRatifyRemove.do',
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
		    seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/util/env', 'lang!sys-ui'],function($, dialog, topic, env, ui_lang){
		    
		    	// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					setTimeout(function() {
						seajs.use(['lui/topic'], function(topic) {
							topic.publish('list.refresh');
						});
					}, 100);
				});
		    	
		    	//导出
		    	window.exportStaff = function(){
		    		var _values = [];
					$("input[name='List_Selected']:checked").each(function() {
						_values.push($(this).val());
					});
					if(_values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var ids = _values.join(";");
		    		var url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=export&type=${param.type}&ids="+ids;
					Com_OpenWindow(url, '_blank');
		    	};
		    	//批量导入
		    	window.importTransfer = function(){
		    		var path = "/hr/ratify/hr_staff_concern/hrTransferManage_import.jsp";
	    	   		dialog.iframe(path,"批量调动调薪",function(value){
	    	   			if(value == "success"){
	    	    			location.reload();
	    	   			}
	        		},{
	    				width : 700,
	    				height : 500
	    			});
		    	};
		    	//调动调薪
		    	window.addTransfer = function(){
		    		Dialog_Address(false,null,null,null,ORG_TYPE_PERSON,function(data){
	    				var user = data.data[0];
	    				if (!user){
	    					return;
						}
	    				var userId = user.id;
	    				var url = Com_GetCurDnsHost()+Com_Parameter.ContextPath+'hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=addTransferPage&fdId='+userId;
	    				dialog.iframe(url,'新增调动调薪',function(value){
							if(value == 'success')
								topic.publish("list.refresh");
						},{
							width:850,height:700,
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
		    	
		    	//撤销调薪
		    	window.delSalary = function(personId, fdId){
		    		var path = "/hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=deleteSalaryPage&personId="+personId+"&fdId="+fdId;
	    			dialog.iframe(path,"撤销调薪",function(value){
	    	   			if(value == "success"){
	    	    			location.reload();
	    	   			}
	        		},{
	    				width : 500,
	    				height : 300,
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
		    	//撤销调动
		    	window.delTrack = function(personId, fdId){
		    		var path = "/hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=deleteTrackPage&personId="+personId+"&fdId="+fdId;
	    			dialog.iframe(path,"撤销调动",function(value){
	    	   			if(value == "success"){
	    	    			location.reload();
	    	   			}
	        		},{
	    				width : 500,
	    				height : 300,
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
		    	
		    	//查看流程
				window.findFlow = function(fdId, fdTransferType){
					var url = null;
		    		if(fdTransferType == '1'){
		    			url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_salary/hrRatifySalary.do?method=view&fdId="+fdId;
		    		}else{
						url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=view&fdId="+fdId;
		    		}
					Com_OpenWindow(url, '_blank');
				};
				//查看档案
				window.findStaffDetail = function(fdId){
					var url = Com_Parameter.ContextPath+"hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId="+fdId;;
					Com_OpenWindow(url, '_blank');
				}
				
				window.openPersonInfo = function(fdId){
	    			var url = env.fn.formatUrl("/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId="+fdId);
	    			Com_OpenWindow(url);
	    		};
	    		
	    		//调岗编辑
		    	window.editHrStaffTrackRecord = function(fdId,orgId){
    				var url = Com_GetCurDnsHost()+Com_Parameter.ContextPath+"hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=editHrStaffTrackRecord&fdId="+fdId+"&orgId="+orgId;
    				dialog.iframe(url,'修改调岗',function(value){
						if(value == 'success')
							topic.publish("list.refresh");
					},{
						width:850,height:700,
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
	    		//调薪编辑
		    	window.editSalary = function(fdId ,orgId){
    				var url = Com_GetCurDnsHost()+Com_Parameter.ContextPath+"hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=editSalary&fdId="+fdId+"&orgId="+orgId;
    				dialog.iframe(url,'修改调薪',function(value){
						if(value == 'success')
							topic.publish("list.refresh");
					},{
						width:850,height:700,
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
			
		    });
	    </script>
    </template:replace>
</template:include>