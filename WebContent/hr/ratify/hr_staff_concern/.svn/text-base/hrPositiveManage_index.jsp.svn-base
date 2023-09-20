<!-- 转正管理 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
    <template:replace name="body">
    	<link rel="stylesheet" href="../resource/style/lib/common.css">
		<link rel="stylesheet" href="../resource/style/hr.css">
	    <script>
	    	seajs.use(['theme!list']);
	    </script>
	    <!-- 筛选 -->
	    <list:criteria id="criteria2">
	    	 <list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${ lfn:message('hr-ratify:hrStaffPersonInfo.search.placehoder') }"></list:cri-ref>
		     <list:cri-ref key="_fdDept" ref="criterion.sys.dept" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }"></list:cri-ref>
		     <list:cri-ref key="_fdPost" ref="criterion.sys.postperson.availableAll" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }"></list:cri-ref>
		     <list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffPersonInfo" property="fdPositiveTime" />
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
	                    <list:sort property="fdPositiveTime" text="${lfn:message('hr-staff:hrStaffPersonInfo.fdPositiveTime')}" group="sort.list" />
	                </ui:toolbar>
	            </div>
	        </div>
	        <div class="lui_list_operation_page_top">
	            <list:paging layout="sys.ui.paging.top" />
	        </div>
	        <div style="float:right">
	            <div style="display: inline-block;vertical-align: middle;">
	                <ui:toolbar count="3">
	                    <ui:button text="${lfn:message('button.export')}" onclick="exportStaff();" order="1" />
	                    <ui:button text="${lfn:message('hr-staff:hrStaff.import.button.adjust')}" onclick="batchAdjust();" order="2" />
	                </ui:toolbar>
	            </div>
	        </div>
	    </div>
	    <ui:fixed elem=".lui_list_operation" />
	    <!-- 列表 -->
	    <list:listview id="listview">
	        <ui:source type="AjaxJson">
	            {url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=positiveManageList&q._fdStatus=trial&q._fdStatus=practice&q._fdStatus=trialDelay'}
	        </ui:source>
	        <!-- 列表视图 -->
	        <list:colTable isDefault="false" name="columntable">
	            <list:col-checkbox />
	            <list:col-serial/>
	    		<list:col-auto props="fdName;fdStatus;fdTrialOperationPeriod;fdPositiveTime;positiveDocNumber;lbpm_main_listcolumn_node;operations"></list:col-auto>
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
					// 创建下载表单
					var exportForm = $('<form action="${KMSS_Parameter_ContextPath}hr/ratify/hr_ratify_positive/hrRatifyPositive.do?method=export" method="post"/>');
					exportForm.appendTo("body");
					exportForm.append($("<input type='hidden' name='ids' />").val(ids));
					// 执行下载
					exportForm.submit();
					// 移除表单
					exportForm.remove();
					//Com_OpenWindow(url, '_blank');
		    	};
		    	//批量调整
		    	window.batchAdjust = function(){
		    		var values = [];
	    			$("input[name='List_Selected']:checked").each(function(){
	    				values.push($(this).val());
	    			});
	    			if(values.length==0){
	    				dialog.alert('${lfn:message("page.noSelect")}');
	    				return;
	    			}
		    		var path = "/hr/ratify/hr_ratify_positive/hrRatifyPositive.do?method=batchAdjust&ids="+values;
	    	   		dialog.iframe(path,"${lfn:message('hr-staff:hrStaff.import.button.adjust')}",function(value){
	    	   			if(value == "success"){
	    	    			location.reload();
	    	   			}
	        		},{
	    				width : 550,
	    				height : 400,
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
		    	//查看流程
				window.findFlow = function(fdId){
					var url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_positive/hrRatifyPositive.do?method=view&fdId="+fdId;
					Com_OpenWindow(url, '_blank');
				};
				//办理转正
	    		window.positive = function(fdId){
	    			var path = "/hr/ratify/hr_ratify_positive/hrRatifyPositive.do?method=transactionPositive&&fdId="+fdId;
	    	   		dialog.iframe(path,"${ lfn:message('hr-staff:hrStaffPersonInfo.applyConfirmation') }",function(value){
	    	   			if(value == "success"){
	    	    			location.reload();
	    	   			}
	        		},{
	    				width : 800,
	    				height : 467,
	    				buttons : [
	    					{
	    						name : ui_lang['ui.dialog.button.ok'], value : true, focus : true,
	    						fn : function(value,_dialog) {
							  		if(_dialog.frame && _dialog.frame.length > 0){
										var _frame = _dialog.frame[0];
							          	var contentWindow = $(_frame).find("iframe")[0].contentWindow;
							          	if(contentWindow._submit()) {
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
	    		window.openPersonInfo = function(fdId){
	    			var url = env.fn.formatUrl("/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId="+fdId);
	    			Com_OpenWindow(url);
	    		};
		    });
	    </script>
    </template:replace>
</template:include>