<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%@page import="com.landray.kmss.hr.organization.util.HrOrganizationUtil" %>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>

<template:include ref="default.simple" bodyClass="lui_list_content_body">
    <template:replace name="body">
    	<ui:tabpanel  layout="sys.ui.tabpanel.list" id="tabpanel">
	    	<ui:content title="${lfn:message('hr-organization:py.GangWeiGuanLi')}">
	    	<link rel="stylesheet" href="../resource/css/organization.css">
	    	
	        <div style="margin:5px 10px;">
	            <!-- 筛选 -->
	            <list:criteria id="criteria1">
	                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('hr-organization:hrOrganizationPost.fdName')}" />
	                <list:cri-criterion title="${lfn:message('hr-organization:hrOrganizationPost.org')}" key="fdParent" multi="false">
	                       <list:box-select>
	                           <list:item-select>
	                               <ui:source type="Static">
	                                   <%=HrOrganizationUtil.buildCriteria("hrOrganizationElementService", "hrOrganizationElement.fdId,hrOrganizationElement.fdName", "hrOrganizationElement.fdOrgType in ('1', '2')", null) %>
	                               </ui:source>
	                           </list:item-select>
	                       </list:box-select>
	                   </list:cri-criterion>
	                   <list:cri-criterion title="${ lfn:message('hr-organization:hr.organization.info.post.establishmentStatus') }" key="fdIsCompileOpen">
							<list:box-select>
								<list:item-select>
										<ui:source type="Static">
										[{text:'${ lfn:message('hr-organization:hrOrganizationPost.fdIsAvailable.true') }', value:'true'},
										{text:'${ lfn:message('hr-organization:hrOrganizationPost.fdIsAvailable.false') }', value:'false'}]
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
					<%-- <div class="lui_list_operation_line"></div>
	                <div class="lui_list_operation_sort_btn">
						<div class="lui_list_operation_order_text">
	                    	${ lfn:message('list.orderType') }：
	                	</div>
	                	<div class="lui_list_operation_sort_toolbar">
	                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
	                            <list:sort property="hrOrganizationElement.fdCreateTime" text="${lfn:message('hr-organization:hrOrganizationPostSeq.docCreateTime')}" group="sort.list" />
	                        </ui:toolbar>
	                    </div>
	                </div> --%>
	                <!-- 分页 -->
					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top" >
						</list:paging>
					</div>
	                <div style="float:right">
	                    <div style="display: inline-block;vertical-align: middle;">
	                        <ui:toolbar count="3">
	                        	<c:if test="${hrToEkpEnable }">
	                        		<kmss:auth requestURL="/hr/organization/hr_organization_post/hrOrganizationPost.do?method=add">
										<!-- 增加 -->
										<ui:button text="${lfn:message('button.add') }" onclick="editOrgPost()" order="1" ></ui:button>
										<!-- 数据导入 -->
										<ui:button text="${lfn:message('hr-organization:hrOrganization.import.all.import') }" onclick="importOrgPost();" order="2"></ui:button>
										<!-- 导出 -->
										<ui:button text="${lfn:message('button.export') }" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.hr.organization.model.HrOrganizationPost')" order="3"></ui:button>
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
	                    {url:appendQueryParameter('/hr/organization/hr_organization_post/hrOrganizationPost.do?method=data&isNew=true')}
	                </ui:source>
	                <!-- 列表视图 -->
	                <list:colTable isDefault="false" rowHref="" name="columntable">
	                    <list:col-checkbox />
	                    <list:col-serial/>
	                    <list:col-auto props="fdName;fdParent.fdName;fdPostSeq.fdName;fdCompile;fdNum;fdCompilationNum;fdCompileDesc;fdPostRank;fdPostGrade;fdIsKey;fdIsSecret;operations" url="" /></list:colTable>
	            </list:listview>
	            <!-- 翻页 -->
	            <list:paging />
	        </div>
	        <script>
	            var listOption = {
	                contextPath: '${LUI_ContextPath}',
	                jPath: 'post',
	                modelName: 'com.landray.kmss.hr.organization.model.HrOrganizationPost',
	                templateName: '',
	                basePath: '/hr/organization/hr_organization_post/hrOrganizationPost.do',
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
			    	window.importOrgPost = function(){
			    		var path = "/hr/organization/hr_organization_post/hrOrganizationPost_import.jsp";
		    	   		dialog.iframe(path,"${lfn:message('hr-organization:hrOrganization.import.all.import') }",function(value){
		    	   			topic.publish('list.refresh');
		        		},{
		    				width : 700,
		    				height : 500,
		    			});
			    	};
			    	//新增、编辑
			    	window.editOrgPost = function(fdId){
			    		var title = "${lfn:message('button.add') }";
			    		var url = '/hr/organization/hr_organization_post/hrOrganizationPost.do?method=updateOrgPostPage';
			    		if(null != fdId){
			    			title = "${lfn:message('button.edit') }";
			    			url = url + '&fdId='+fdId;
			    		}
	    				var dialogObj = dialog.iframe(url,title,function(value){
							if(value == 'success')
								topic.publish("list.refresh");
						},{
							width:550,
							height:550,
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
			    	/* window.delOrgPost = function(fdId){
			    		var path = "/hr/organization/hr_organization_post/hrOrganizationPost.do?method=deleteOrgPostPage&fdIds="+fdId;
		    			dialog.iframe(path,"删除岗位",function(value){
		    	   			if(value == "success"){
		    	    			location.reload();
		    	   			}
		        		},{
		    				width : 500,
		    				height : 300
		    			});
			    	} */
			    	
			    	//禁用岗位
			    	window.changeDisabled = function(fdId){
			    		dialog.confirm("${lfn:message('hr-organization:hr.organization.info.tip.22') }",function(value){
							if(value==true){
								var url = "${LUI_ContextPath}/hr/organization/hr_organization_post/hrOrganizationPost.do?method=changeDisabled";
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
											dialog.alert("${lfn:message('hr-organization:hr.organization.info.tip.23') }");
											topic.publish("list.refresh");
										}else {
											dialog.alert("${lfn:message('hr-organization:hr.organization.info.tip.24') }");
										}
									}
			    				});
							}
			    		});
			    	};
			    	
			    	//设置编制
			    	window.setCompile = function(fdId, fdName){
			    		var path = "/hr/organization/hr_organization_post/hrOrganizationPost.do?method=updateCompilePage&fdId="+fdId;
		    			var dialogObj = dialog.iframe(path,"${lfn:message('hr-organization:hrOrganization.set.compile')}-" + fdName,function(value){
		    	   			if(value == "success"){
		    	    			location.reload();
		    	   			}
		        		},{
		    				width : 800,
		    				height : 500,
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
			    	}
			    });
	        </script>
	        </ui:content>
        </ui:tabpanel>
    </template:replace>
</template:include>