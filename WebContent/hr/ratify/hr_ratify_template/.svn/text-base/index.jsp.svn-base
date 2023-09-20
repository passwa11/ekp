<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include file="/sys/profile/resource/template/list.jsp">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('hr-ratify:hrRatifyTemplate.fdName')}" />
                <list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyTemplate" property="fdIsAvailable" />
                <list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyTemplate" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyTemplate" property="docCreateTime" />

            </list:criteria>
            <!-- 操作 -->
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
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="hrRatifyTemplate.fdOrder" text="${lfn:message('hr-ratify:hrRatifyTemplate.fdOrder')}" group="sort.list" />
                            <list:sort property="hrRatifyTemplate.docCreateTime" text="${lfn:message('hr-ratify:hrRatifyTemplate.docCreateTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <!-- 分页 -->
				<div class="lui_list_operation_page_top">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/hr/ratify/hr_ratify_template/hrRatifyTemplate.do?method=add&parentId=${param.parentId}">
                                <ui:button text="${lfn:message('button.add')}" onclick="selectTemp()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/hr/ratify/hr_ratify_template/hrRatifyTemplate.do?method=deleteall&parentId=${param.parentId}">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll0()" order="4" id="btnDelete" />

                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <input type="hidden" name="fdkey" value=""/>
			<input type="hidden" name="fdTempName" value=""/>
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:'/hr/ratify/hr_ratify_template/hrRatifyTemplate.do?method=data&q.docCategory=${param.docCategory}&parentId=${param.parentId}'}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/hr/ratify/hr_ratify_template/hrRatifyTemplate.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdOrder;fdName;fdIsAvailable.name;docCreateTime;operations" url="" />
                </list:colTable>
            	<ui:event topic="list.loaded">
					Dropdown.init();
				</ui:event>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.hr.ratify.model.HrRatifyTemplate',
                templateName: '',
                basePath: '/hr/ratify/hr_ratify_template/hrRatifyTemplate.do',
                canDelete: '${canDelete}',
                mode: 'config_template',
                templateService: '',
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
            Com_IncludeFile("dialog.js|data.js");
            function selectTemp(){
				var fdkey = document.getElementsByName("fdkey")[0];
				var fdTempName = document.getElementsByName("fdTempName")[0];
				fdkey.value="";
				fdTempName.value="";
				Dialog_Tree(false, 'fdkey', 'fdTempName', null, 'hrRatifyTreeService', '选择模板类型', null, afterTempSelect, null, null, true);
			}
			function afterTempSelect(){
				var fdkey = document.getElementsByName("fdkey")[0].value;
				var fdTempName = document.getElementsByName("fdTempName")[0].value;
				if(""!=fdkey||null!=fdkey.value){
			        var createUrl = '${KMSS_Parameter_ContextPath}hr/ratify/hr_ratify_template/hrRatifyTemplate.do?method=add';
			       window.top.open(createUrl+'&parentId=${param.docCategory}&fdkey='+fdkey,'_blank');
				}
			}
			
			seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
				// 新建申请
		 		window.add = function(fdId, fdType) {
					var url = '';
					if (fdType == 'HrRatifyEntryTemplateForm') {
						url = '<c:url value="/hr/ratify/hr_ratify_entry/hrRatifyEntry.do" />?method=add&i.docTemplate='+fdId;
					} else if (fdType == 'HrRatifyPositiveTemplateForm'){
						url = '<c:url value="/hr/ratify/hr_ratify_positive/hrRatifyPositive.do" />?method=add&i.docTemplate='+fdId;
					} else if (fdType == 'HrRatifyTransferTemplateForm'){
						url = '<c:url value="/hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do" />?method=add&i.docTemplate='+fdId;
					} else if (fdType == 'HrRatifyLeaveTemplateForm'){
						url = '<c:url value="/hr/ratify/hr_ratify_leave/hrRatifyLeave.do" />?method=add&i.docTemplate='+fdId;
					} else if (fdType == 'HrRatifyFireTemplateForm'){
						url = '<c:url value="/hr/ratify/hr_ratify_fire/hrRatifyFire.do" />?method=add&i.docTemplate='+fdId;
					} else if (fdType == 'HrRatifyRetireTemplateForm'){
						url = '<c:url value="/hr/ratify/hr_ratify_retire/hrRatifyRetire.do" />?method=add&i.docTemplate='+fdId;
					} else if (fdType == 'HrRatifyRehireTemplateForm'){
						url = '<c:url value="/hr/ratify/hr_ratify_rehire/hrRatifyRehire.do" />?method=add&i.docTemplate='+fdId;
					} else if (fdType == 'HrRatifySalaryTemplateForm'){
						url = '<c:url value="/hr/ratify/hr_ratify_salary/hrRatifySalary.do" />?method=add&i.docTemplate='+fdId;
					} else if (fdType == 'HrRatifySignTemplateForm'){
						url = '<c:url value="/hr/ratify/hr_ratify_sign/hrRatifySign.do" />?method=add&i.docTemplate='+fdId;
					} else if (fdType == 'HrRatifyChangeTemplateForm'){
						url = '<c:url value="/hr/ratify/hr_ratify_change/hrRatifyChange.do" />?method=add&i.docTemplate='+fdId;
					} else if (fdType == 'HrRatifyRemoveTemplateForm'){
						url = '<c:url value="/hr/ratify/hr_ratify_remove/hrRatifyRemove.do" />?method=add&i.docTemplate='+fdId;
					} else if (fdType == 'HrRatifyOtherTemplateForm'){
						url = '<c:url value="/hr/ratify/hr_ratify_other/hrRatifyOther.do" />?method=add&i.docTemplate='+fdId;
					}
					if (url) {
						Com_OpenWindow(url);
					}
		 		};
		 		
		 	    // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/hr/ratify/hr_ratify_template/hrRatifyTemplate.do" />?method=edit&fdId=' + id);
		 		};
		 		
		 		 // 搜索设置
		 		window.setSearch = function(fdTemplateId,fdTemplateName,fdTempKey) {
		 			 var fdModelName;
		 			 switch(fdTempKey){
			 			 case 'HrRatifyEntryDoc':
			 				fdModelName = 'com.landray.kmss.hr.ratify.model.HrRatifyEntry';
			 				break;
			 			 case 'HrRatifyPositiveDoc':
			 				fdModelName = 'com.landray.kmss.hr.ratify.model.HrRatifyPositive';
			 				break;
			 			 case 'HrRatifyTransferDoc':
			 				fdModelName = 'com.landray.kmss.hr.ratify.model.HrRatifyTransfer';
			 				break;
			 			 case 'HrRatifyLeaveDoc':
			 				fdModelName = 'com.landray.kmss.hr.ratify.model.HrRatifyLeave';
			 				break;
			 			 case 'HrRatifyFireDoc':
			 				fdModelName = 'com.landray.kmss.hr.ratify.model.HrRatifyFire';
			 				break;
			 			 case 'HrRatifyRetireDoc':
			 				fdModelName = 'com.landray.kmss.hr.ratify.model.HrRatifyRetire';
			 				break;
			 			 case 'HrRatifyRehireDoc':
			 				fdModelName = 'com.landray.kmss.hr.ratify.model.HrRatifyRehire';
			 				break;
			 			 case 'HrRatifySalaryDoc':
			 				fdModelName = 'com.landray.kmss.hr.ratify.model.HrRatifySalary';
			 				break;
			 			 case 'HrRatifySignDoc':
			 				fdModelName = 'com.landray.kmss.hr.ratify.model.HrRatifySign';
			 				break;
			 			 case 'HrRatifyChangeDoc':
			 				fdModelName = 'com.landray.kmss.hr.ratify.model.HrRatifyChange';
			 				break;
			 			 case 'HrRatifyRemoveDoc':
			 				fdModelName = 'com.landray.kmss.hr.ratify.model.HrRatifyRemove';
			 				break;
			 			 case 'HrRatifyOtherDoc':
			 				fdModelName = 'com.landray.kmss.hr.ratify.model.HrRatifyOther';
			 				break;
		 			 }
		 			 Com_OpenWindow('<c:url value="/sys/search/sys_search_main/sysSearchMain.do" />?method=add&fdModelName='+fdModelName+'&fdKey='+fdTempKey+'&fdTemplateId=' + fdTemplateId+'&fdTemplateName='+encodeURIComponent(fdTemplateName));
		 		};
		 		
		 		window.deleteDoc = function(id){
		 			var url = '<c:url value="/hr/ratify/hr_ratify_template/hrRatifyTemplate.do?method=delete"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'GET',
								data:{fdId:id},
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
				
				window.deleteAll0 = function(id){
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
					var url = '<c:url value="/hr/ratify/hr_ratify_template/hrRatifyTemplate.do?method=deleteall"/>&parentId=${param.parentId}';
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
				
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
			});
        </script>
    </template:replace>
</template:include>