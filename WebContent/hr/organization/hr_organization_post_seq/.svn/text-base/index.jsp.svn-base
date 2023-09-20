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
	    	<ui:content title="${lfn:message('hr-organization:table.hrOrganizationPostSeq') }">
    	<link rel="stylesheet" href="../resource/css/organization.css">
    	
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('hr-organization:hrOrganizationPostSeq.fdName')}" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="hrOrganizationPostSeq.docCreateTime" text="${lfn:message('hr-organization:hrOrganizationPostSeq.docCreateTime')}" group="sort.list" />
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
                        		<kmss:auth requestURL="/hr/organization/hr_organization_post_seq/hrOrganizationPostSeq.do?method=add">
									<!-- 增加 -->
									<ui:button text="${lfn:message('hr-organization:hr.organization.info.post.addseq') }" onclick="editPostSeq()" order="2" ></ui:button>
									<!-- 数据导入 -->
									<ui:button text="${lfn:message('hr-organization:hrOrganization.import.all.import') }" onclick="importPostSeq();" order="3"></ui:button>
		                            <!-- 导出 -->
									<ui:button text="${lfn:message('button.export') }" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.hr.organization.model.HrOrganizationPostSeq')" order="3"></ui:button>
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
                    {url:appendQueryParameter('/hr/organization/hr_organization_post_seq/hrOrganizationPostSeq.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;postNum;personNum;docCreator.name;docCreateTime;operations" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'post_seq',
                modelName: 'com.landray.kmss.hr.organization.model.HrOrganizationPostSeq',
                templateName: '',
                basePath: '/hr/organization/hr_organization_post_seq/hrOrganizationPostSeq.do',
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
		    	window.importPostSeq = function(){
		    		var path = "/hr/organization/hr_organization_post_seq/hrOrganizationPostSeq_import.jsp";
	    	   		dialog.iframe(path,
	    	   				"${lfn:message('hr-organization:hrOrganization.import.all.import') } ${lfn:message('hr-organization:py.GangWeiXuLie') }",
	    	   				function(value){
	    	   			if(value == "success"){
	    	    			location.reload();
	    	   			}
	        		},{
	    				width : 700,
	    				height : 500,
	    				buttons:[]
	    			});
		    	};
		    	//新增、编辑
		    	window.editPostSeq = function(fdId){
		    		var title = "${lfn:message('hr-organization:hr.organization.info.post.addseq') }";
		    		var url = '/hr/organization/hr_organization_post_seq/hrOrganizationPostSeq.do?method=updatePostSeqPage';
		    		if(null != fdId){
		    			title = "${lfn:message('hr-organization:hr.organization.info.post.editseq') }";
		    			url = url + '&fdId='+fdId;
		    		}
    				var dialogObj= dialog.iframe(url,title,function(value){
						if(value == 'success')
							topic.publish("list.refresh");
					},{
						width:450,
						height:340,
						buttons:[{
							name:"${lfn:message('button.ok') }",
							fn:function(value){
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
		    	window.delPostSeq = function(fdId){
		    		var path = "/hr/organization/hr_organization_post_seq/hrOrganizationPostSeq.do?method=delPostSeqPage&fdIds="+fdId;
	    			var dialogObj = dialog.iframe(path,
	    					"${lfn:message('hr-organization:hr.organization.info.post.delseq') }"
	    					,function(value){
	    	   			if(value == "success"){
	    	    			location.reload();
	    	   			}
	        		},{
						buttons:[{
							name:"${lfn:message('button.ok') }",
							fn:function(value){
								dialogObj.element.find("iframe").get(0).contentWindow.clickOk();
							}
						},{
							name:"${lfn:message('button.cancel') }",
							fn:function(){
								dialogObj.hide();
							}
						}],	        			
	    				width : 500,
	    				height : 240
	    			});
		    	}
		    });
        </script>
        </ui:content>
        </ui:tabpanel>
    </template:replace>
</template:include>