<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>

<template:include ref="default.simple" spa="true">
    <template:replace name="body">
    	<ui:tabpanel  layout="sys.ui.tabpanel.list" id="tabpanel">
	    	<ui:content title="${lfn:message('hr-organization:table.hrOrganizationDuty') }">
    	<link rel="stylesheet" href="../resource/css/organization.css">
    	
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('hr-organization:hrOrganizationDuty.fdName')}" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="sysOrganizationStaffingLevel.docCreateTime" text="${lfn:message('hr-organization:hrOrganizationDuty.docCreateTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                        	<c:if test="${hrToEkpEnable }">
								<kmss:auth requestURL="/hr/organization/hr_organization_staffing_level/hrOrganizationStaffingLevel.do?method=add">
									<!-- 增加 -->
									<ui:button text="${lfn:message('button.add') }" onclick="editStaffingLevel()" order="2" ></ui:button>
									<!-- 数据导入 -->
									<ui:button text="${lfn:message('hr-organization:hrOrganization.import.all.import') }" onclick="importStaffingLevel();" order="3"></ui:button>
									<!-- 导出 -->
									<ui:button text="${lfn:message('button.export') }" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel')" order="3"></ui:button>
								</kmss:auth>
							</c:if>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:'/hr/organization/hr_organization_staffing_level/hrOrganizationStaffingLevel.do?method=list'}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdLevel;fdNum;docCreator.name;docCreateTime;operations" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
        var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'rank',
                modelName: '',
                templateName: '',
                basePath: '',
                canDelete: '',
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
		    	
		    	//批量导入
		    	window.importStaffingLevel = function(){
		    		var path = "/hr/organization/hr_organization_staffing_level/hrOrganizationStaffingLevel_import.jsp";
	    	   		dialog.iframe(path,"${lfn:message('hr-organization:hrOrganization.import.all.import') }",function(value){
	    	   			if(value == "success"){
	    	    			location.reload();
	    	   			}
	        		},{
	    				width : 700,
	    				height : 500
	    			});
		    	};
		    	//新增、编辑职务
		    	window.editStaffingLevel = function(fdId){
		    		var title = "${lfn:message('button.add') }";
		    		var url = Com_GetCurDnsHost()+Com_Parameter.ContextPath+'hr/organization/hr_organization_staffing_level/hrOrganizationStaffingLevel.do?method=updateStaffingLevelPage';
		    		if(null != fdId){
		    			title = "${lfn:message('button.edit') }";
		    			url = url + '&fdId='+fdId;
		    		}
    				var dialogObj = dialog.iframe(url,title,function(value){
						if(value == 'success')
							topic.publish("list.refresh");
					},{
						width:450,
						height:400,
						buttons:[{
							name:"${lfn:message('button.ok') }",
							fn:function(){
								dialogObj.element.find("iframe").get(0).contentWindow._submit();
							}
						},{
							name:"${lfn:message('button.cancel') }",
							fn:function(){
								dialogObj.hide();
							}
						}]
					});
		    	};
		    	
		    	//删除职务
		    	window.delStaffingLevel = function(fdId){
		    		var path = "/hr/organization/hr_organization_staffing_level/hrOrganizationStaffingLevel.do?method=deleteStaffingLevelPage&fdIds="+fdId;
	    			var dialogObj = dialog.iframe(path,"${lfn:message('button.delete') }",function(value){
	    	   			if(value == "success"){
	    	    			location.reload();
	    	   			}
	        		},{
	    				width : 500,
	    				height : 300,
						buttons:[{
							name:"${lfn:message('button.ok') }",
							fn:function(){
								dialogObj.element.find("iframe").get(0).contentWindow.clickOk();
							}
						},{
							name:"${lfn:message('button.cancel') }",
							fn:function(){
								dialogObj.hide();
							}
						}]
	    			});
		    	}
			
		    });
	    </script>
        </script>
        </ui:content>
        </ui:tabpanel>
    </template:replace>
</template:include>