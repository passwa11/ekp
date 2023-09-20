<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.hr.organization.util.HrOrganizationUtil" %>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>

<template:include ref="default.simple" spa="true">
        <template:replace name="body">
        	<ui:tabpanel  layout="sys.ui.tabpanel.list" id="tabpanel">
	    	<ui:content title="${lfn:message('hr-organization:table.hrOrganizationConPost') }">
        	<link rel="stylesheet" href="../resource/css/organization.css">
        	
            <div style="margin:5px 10px;">
                <!-- 筛选 -->
                <list:criteria id="criteria1">
                	<list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${lfn:message('hr-organization:hrOrganizationConPost.fdPerson')}" />
                	
                    <list:cri-criterion title="${lfn:message('hr-organization:hrOrganizationConPost.fdPerson')}" key="fdPerson" multi="false">
                        <list:box-select>
                            <list:item-select>
                                <ui:source type="Static">
                                    <%=HrOrganizationUtil.buildCriteria( "hrStaffPersonInfoService", "hrStaffPersonInfo.fdId,hrStaffPersonInfo.fdName", null, null) %>
                                </ui:source>
                            </list:item-select>
                        </list:box-select>
                    </list:cri-criterion>
                    
                    <list:cri-criterion title="${lfn:message('hr-organization:hr.organization.info.post.dept')}" key="_fdDept" multi="false">
                       <list:box-select>
                           <list:item-select>
                               <ui:source type="Static">
                                   <%=HrOrganizationUtil.buildCriteria("hrOrganizationElementService", "hrOrganizationElement.fdId,hrOrganizationElement.fdName", "hrOrganizationElement.fdOrgType in ('1', '2')", null) %>
                               </ui:source>
                           </list:item-select>
                       </list:box-select>
                   </list:cri-criterion>
                   
                   <list:cri-criterion title="${lfn:message('hr-organization:hrOrganizationConPost.fdType')}" key="fdStatus">
						<list:box-select>
							<list:item-select>
									<ui:source type="Static">
									[{text:"${lfn:message('hr-organization:hr.organization.info.post.injob')}", value:'1'},
									{text:"${lfn:message('hr-organization:hr.organization.info.post.isover')}", value:'2'}]
								</ui:source>
							</list:item-select>
						</list:box-select> 
					</list:cri-criterion>
	                   
                </list:criteria>
                <!-- 操作 -->
                <div class="lui_list_operation">
					<!-- 全选 -->
						<div class="lui_list_operation_order_btn">
							<list:selectall></list:selectall>
						</div>
					<!-- 分割线 -->
					
                    <div style="display:inline-block;">
                        <list:paging layout="sys.ui.paging.top" />
                    </div>
                    <div class="lui_list_operation_line"></div>					
					<div class="lui_list_operation_sort_btn">
						<div class="lui_list_operation_order_text">
                        	${ lfn:message('list.orderType') }：
                    	</div>
                    	<div class="lui_list_operation_sort_toolbar">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                                <list:sort property="fdEntranceBeginDate" text="${lfn:message('hr-organization:hrOrganizationConPost.fdStartTime')}" group="sort.list" />
                                <list:sort property="fdEntranceEndDate" text="${lfn:message('hr-organization:hrOrganizationConPost.fdEndTime')}" group="sort.list" />
                            </ui:toolbar>
                        </div>
                    </div>
                   	

                    <div style="float:right">
                        <div style="display: inline-block;vertical-align: middle;">
	                       <ui:toolbar count="4">
	                       		<c:if test="${hrToEkpEnable }">
	                        		<kmss:auth requestURL="/hr/organization/hr_organization_con_post/hrOrganizationConPost.do?method=add">
										<!-- 增加 -->
										<ui:button text="${lfn:message('button.add') }" onclick="editConPost()" order="1" ></ui:button>
										<ui:button text="${lfn:message('hr-organization:hrOrganizationConPost.batch.finish.conPost') }" onclick="batchFinishConPost();" order="2"></ui:button>
										<!-- 数据导入 -->
										<ui:button text="${lfn:message('hr-organization:hrOrganization.import.all.import') }" onclick="importConPost();" order="3"></ui:button>
		                                <!-- 导出 -->
										<ui:button text="${lfn:message('button.export') }" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.hr.organization.model.HrOrganizationConPost')" order="4"></ui:button>
                                		<ui:button text="${lfn:message('hr-organization:hr.organization.info.post.batchdeljg') }" onclick="batchDelConPost();" order="5"></ui:button>
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
						{url:'/hr/organization/hr_organization_con_post/hrOrganizationConPost.do?method=list&q.fdType=2'}
					</ui:source>
					<!-- 列表视图 -->	
					<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
						rowHref="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=!{personInfoId}" name="columntable">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial> 
						<list:col-auto props="fdName,fdType,fdEntranceBeginDate,fdEntranceEndDate,fdRatifyDept,fdStaffingLevel,fdOrgPosts,fdStatus,hrOrgoperations"></list:col-auto>
					</list:colTable>
				</list:listview>
                <!-- 翻页 -->
                <list:paging />
            </div>
            <script>
                var listOption = {
                    contextPath: '${LUI_ContextPath}',
                    jPath: 'con_post',
                    modelName: 'com.landray.kmss.hr.organization.model.HrOrganizationConPost',
                    templateName: '',
                    basePath: '/hr/organization/hr_organization_con_post/hrOrganizationConPost.do',
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
    		    	
    		    	//批量导入
    		    	window.importConPost = function(){
    		    		var path = "/hr/organization/hr_organization_con_post/hrOrganizationConPost_import.jsp";
    	    	   		dialog.iframe(path,
    	    	   				"${lfn:message('hr-organization:hr.organization.info.post.batchimportjg') }",
    	    	   				function(value){
    	    	   			if(value == "success"){
    	    	    			location.reload();
    	    	   			}
    	        		},{
    	    				width : 700,
    	    				height : 500
    	    			});
    		    	};
    		    	//新增、编辑
    		    	window.editConPost = function(fdId){
    		    		var title = "${lfn:message('button.add') }";
    		    		var url = '/hr/organization/hr_organization_con_post/hrOrganizationConPost.do?method=updateConPostPage';
    		    		if(null != fdId){
    		    			title = "${lfn:message('button.edit') }";
    		    			url = url + '&fdId='+fdId;
    		    		}
        				var dialogObj=dialog.iframe(url,title,function(value){
    						if(value == 'success')
    							topic.publish("list.refresh");
    					},{
    						width:550,
    						height:600,
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
    		    	
    		    	//结束兼职
    		    	window.finishConPost = function(fdId){
    		    		var path = "/hr/organization/hr_organization_con_post/hrOrganizationConPost.do?method=finishConPostPage&fdIds="+fdId;
    	    			var dialogObj = dialog.iframe(path,
    	    					"${lfn:message('hr-organization:hrOrganizationConPost.finish.conPost') }"
    	    					,function(value){
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
    		    	
    		    	//批量结束兼职
    		    	window.batchFinishConPost = function(){
    		    		var _values = [];
    					$("input[name='List_Selected']:checked").each(function() {
    						_values.push($(this).val());
    					});
    					if(_values.length==0){
    						dialog.alert('<bean:message key="page.noSelect"/>');
    						return;
    					}
    					var ids = _values.join(";");
    		    		var path = "/hr/organization/hr_organization_con_post/hrOrganizationConPost.do?method=finishConPostPage&fdIds="+ids;
    	    			var dialogObj = dialog.iframe(path,
    	    					"${lfn:message('hr-organization:hr.organization.info.post.batchstopjg') }",
    	    					function(value){
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
    		    	
    		    	//删除
    		    	window.batchDelConPost = function(){
    		    		var _values = [];
    					$("input[name='List_Selected']:checked").each(function() {
    						_values.push($(this).val());
    					});
    					if(_values.length==0){
    						dialog.alert('<bean:message key="page.noSelect"/>');
    						return;
    					}
    					var ids = _values.join(";");
    		    		var path = "/hr/organization/hr_organization_con_post/hrOrganizationConPost.do?method=delConPostPage&fdIds="+ids;
    	    			var dialogObj = dialog.iframe(path,
    	    					"${lfn:message('hr-organization:hr.organization.info.post.deljg') }",
    	    					function(value){
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