<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
    <template:replace name="body">
    <ui:tabpanel  layout="sys.ui.tabpanel.list" id="tabpanel">
	    	<ui:content title="${lfn:message('hr-organization:py.YiTingYongZuZhi') }">
    	<link rel="stylesheet" href="../../resource/css/organization.css">
    	
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('hr-organization:hrOrganizationElement.fdName')}" />
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
                            <list:sort property="fdAlterTime" text="${lfn:message('hr-organization:hrOrganizationGrade.docCreateTime')}" group="sort.list" />
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
                        <ui:toolbar count="3">
                        	<kmss:auth requestURL="/hr/organization/hr_organization_element/hrOrganizationElement.do?method=add">
								<ui:button text="${lfn:message('button.export') }" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.hr.organization.model.HrOrganizationElement')" order="3"></ui:button>
                        	</kmss:auth>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/hr/organization/hr_organization_element/hrOrganizationElement.do?method=disableData&available=0')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdNo;fdName;fdParent.fdName;fdOrgType;fdCreateTime;fdAlterTime;operations" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'grade',
                modelName: 'com.landray.kmss.hr.organization.model.HrOrganizationElement',
                templateName: '',
                basePath: '/hr/organization/hr_organization_element/hrOrganizationElement.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("hr-organization:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/hr/organization/resource/js/", 'js', true);
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'],function($, dialog, topic){
		    	// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					setTimeout(function() {
						seajs.use(['lui/topic'], function(topic) {
							topic.publish('list.refresh');
						});
					}, 100);
				});
		    	
		    	//启用组织
		    	window.changeEnabled = function(fdId){
		    		dialog.confirm("${lfn:message('hr-organization:hr.organization.info.tip.8')}",function(value){
						if(value==true){
							var url = "${LUI_ContextPath}/hr/organization/hr_organization_element/hrOrganizationElement.do?method=changeEnabled";
							$.ajax({
								url: url,
								type: 'POST',
								data:{"fdId":fdId},
								dataType: 'json',
								error: function(data){
									if(window.file_load!=null){
										window.file_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(data){
									if(data.flag) {
										dialog.alert("${lfn:message('hr-organization:hr.organization.info.tip.9')}");
										topic.publish("list.refresh");
									}else {
										dialog.alert("${lfn:message('hr-organization:hr.organization.info.tip.10')}");
									}
								}
		    				});
						}
		    		});
		    	};
		    	
		    	//查看组织
		    	window.view = function(fdId, fdName){
		    		var path = "/hr/organization/hr_organization_element/hrOrganizationElement.do?method=view&fdId="+fdId+"&stopOrgInfo=true";
	    			var dialogObj = dialog.iframe(path,"${lfn:message('button.view')}-" + fdName,function(value){
	    	   			if(value == "success"){
	    	    			location.reload();
	    	   			}
	        		},{
	    				width : 900,
	    				height : 550,
	    				buttons:[{
	    					name:"${lfn:message('button.close')}",
	    					fn:function(){
	    						dialogObj.hide();
	    					}
	    				}]
	    			});
		    	}
		    	
		    	
		    });
        </script>
        </ui:content>
        </ui:tabpanel>
    </template:replace>
</template:include>