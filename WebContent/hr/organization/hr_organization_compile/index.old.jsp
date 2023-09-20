<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
    <template:replace name="body">
    	<link rel="stylesheet" href="../resource/css/organization.css">
    	
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('hr-organization:hrOrganizationPostSeq.fdName')}" />
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
                            <list:sort property="hrOrganizationElement.fdCreateTime" text="${lfn:message('hr-organization:hrOrganizationPostSeq.docCreateTime')}" group="sort.list" value="down"/>
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
							<!-- 增加 -->
							<ui:button text="新建机构" onclick="addOrg()" order="1" ></ui:button>
							<ui:button text="新建部门" onclick="addDept()" order="2" ></ui:button>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/hr/organization/hr_organization_element/hrOrganizationElement.do?method=data')}
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
                jPath: 'post',
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
		    	
		    	//新增
		    	window.addOrg = function(){
		    		var url = '/hr/organization/hr_organization_org/hrOrganizationOrg.do?method=add';
    				dialog.iframe(url,"新建机构",function(value){
						if(value == 'success')
							topic.publish("list.refresh");
					},{
						width:700,
						height:500
					});
		    	};
		    	
		    	window.addDept = function(){
		    		var url = '/hr/organization/hr_organization_dept/hrOrganizationDept.do?method=add';
    				dialog.iframe(url,"新建部门",function(value){
						if(value == 'success')
							topic.publish("list.refresh");
					},{
						width:700,
						height:500
					});
		    	};
		    	
		    	//设置编制
		    	window.setCompile = function(fdId, fdName){
		    		var path = "/hr/organization/hr_organization_element/hrOrganizationElement.do?method=updateCompilePage&fdId="+fdId;
	    			dialog.iframe(path,"设置编制-" + fdName,function(value){
	    	   			if(value == "success"){
	    	    			location.reload();
	    	   			}
	        		},{
	    				width : 700,
	    				height : 550
	    			});
		    	}
		    	
		    	//禁用岗位
		    	window.changeDisabled = function(fdId){
		    		dialog.confirm('是否确定启用组织？',function(value){
						if(value==true){
							var url = "${LUI_ContextPath}/hr/organization/hr_organization_element/hrOrganizationElement.do?method=changeDisabled";
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
										dialog.alert("禁用成功！");
										topic.publish("list.refresh");
									}else {
										dialog.alert("禁用失败，岗位中存在人员！");
									}
								}
		    				});
						}
		    		});
		    	};
		    });
        </script>
    </template:replace>
</template:include>