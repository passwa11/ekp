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
	    	<ui:content title="${lfn:message('hr-organization:table.hrOrganizationGrade') }">
    	<link rel="stylesheet" href="../resource/css/organization.css">
    	
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('hr-organization:hrOrganizationGrade.fdName')}" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="hrOrganizationGrade.docCreateTime" text="${lfn:message('hr-organization:hrOrganizationGrade.docCreateTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="4">
                        	<c:if test="${hrToEkpEnable }">
                        		<kmss:auth requestURL="/hr/organization/hr_organization_grade/hrOrganizationGrade.do?method=add">
                        			<ui:button text="${lfn:message('hr-organization:hr.organization.info.help') }" onclick="help()"></ui:button>
									<c:import url="/hr/organization/hr_organization_grade/change_grade_weight.jsp" charEncoding="UTF-8">
										<c:param name="modelName" value="com.landray.kmss.hr.organization.model.HrOrganizationGrade"></c:param>
										<c:param name="property" value="fdWeight"></c:param>
										<c:param name="column" value="4"></c:param>
									</c:import>                        		
									<!-- 增加 -->
									<ui:button text="${lfn:message('button.add') }" onclick="editGrade()" order="2" ></ui:button>
									<!-- 数据导入 -->
									<ui:button text="${lfn:message('hr-organization:hrOrganization.import.all.import') }" onclick="importGrade();" order="3"></ui:button>
		                            <!-- 导出 -->
									<ui:button text="${lfn:message('button.export') }" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.hr.organization.model.HrOrganizationGrade')" order="4"></ui:button>
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
                    {url:appendQueryParameter('/hr/organization/hr_organization_grade/hrOrganizationGrade.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdWeight;docCreator.name;docCreateTime;operations" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'grade',
                modelName: 'com.landray.kmss.hr.organization.model.HrOrganizationGrade',
                templateName: '',
                basePath: '/hr/organization/hr_organization_grade/hrOrganizationGrade.do',
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
    		    window.help=function(){
    		    	var html = {
    		    		html:"${lfn:message('hr-organization:hr.organization.info.grade.desc') }",
    		    		title:"${lfn:message('hr-organization:hr.organization.info.rant.operationDesc') }"
    		    	}
    		    	dialog.alert(html);
    		    }
		    	// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					setTimeout(function() {
						seajs.use(['lui/topic'], function(topic) {
							topic.publish('list.refresh');
						});
					}, 100);
				});
		    	
		    	//批量导入
		    	window.importGrade = function(){
		    		var path = "/hr/organization/hr_organization_grade/hrOrganizationGrade_import.jsp";
	    	   		dialog.iframe(path,"${lfn:message('hr-organization:hrOrganization.import.all.import') }",function(value){
	    	   			topic.publish('list.refresh');
	        		},{
	    				width : 700,
	    				height : 500
	    			});
		    	};
		    	//新增、编辑
		    	window.editGrade = function(fdId){
		    		var title = "${lfn:message('button.add') }";
		    		var url = '/hr/organization/hr_organization_grade/hrOrganizationGrade.do?method=updateGradePage';
		    		if(null != fdId){
		    			title = "${lfn:message('button.edit') }";
		    			url = url + '&fdId='+fdId;
		    		}
    				var dialogObj = dialog.iframe(url,title,function(value){
						if(value == 'success')
							topic.publish("list.refresh");
					},{
						width:450,
						height:380,
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
		    	
		    	//删除
		    	window.delGrade = function(fdId){
		    		var path = "/hr/organization/hr_organization_grade/hrOrganizationGrade.do?method=deleteGradePage&fdIds="+fdId;
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
        </ui:content>
        </ui:tabpanel>
    </template:replace>
</template:include>