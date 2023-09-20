<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
    <template:replace name="body">
	    <script>
	    	seajs.use(['theme!list']);
	    </script>
	    <!-- 筛选 -->
	    <list:criteria id="criteria2">
	       	<list:tab-criterion title="${lfn:message('hr-ratify:hrRatifyMain.docStatus') }" key="docStatus"> 
		 		<list:box-select>
			 		<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="true" >
						<ui:source type="Static">
							[{text:'${ lfn:message('hr-ratify:enums.doc_status.10') }', value:'10'},
							{text:'${ lfn:message('hr-ratify:enums.doc_status.20') }', value:'20'},
							{text:'${ lfn:message('hr-ratify:enums.doc_status.11') }', value:'11'},
							{text:'${ lfn:message('hr-ratify:enums.doc_status.30') }', value:'30'},
							{text:'${ lfn:message('hr-ratify:enums.doc_status.00') }',value:'00'}]
						</ui:source>
					</list:item-select>
		  		</list:box-select>
	  		</list:tab-criterion>
		  	<list:tab-criterion title="" key="feedStatus">
			 	<list:box-select>
			 		<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="false" cfg-required="true"  cfg-if="param['doctype'] == 'feedback'">
						<ui:source type="Static">
							[{text:'未反馈', value:'41'},
							 {text:'${ lfn:message('hr-ratify:enums.doc_status.31') }',value:'31'}]
						</ui:source>
					</list:item-select>
			  	</list:box-select>
			 </list:tab-criterion>
		     <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('hr-ratify:hrRatifyMain.docSubject')}" />
		     <list:cri-ref ref="criterion.sys.category" key="docTemplate" multi="false" title="${lfn:message('hr-ratify:hrRatifyMain.docTemplate')}" expand="false">
		         <list:varParams modelName="com.landray.kmss.hr.ratify.model.HrRatifyTemplate" />
		     </list:cri-ref>
		     <list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyRemove" property="docNumber" />
		     <list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyRemove" property="docCreator" />
		     <list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyRemove" property="docCreateTime" />
		     <list:cri-auto modelName="com.landray.kmss.hr.ratify.model.HrRatifyRemove" property="docPublishTime" />
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
	                    <list:sort property="hrRatifyRemove.docCreateTime" text="${lfn:message('hr-ratify:hrRatifyMain.docCreateTime')}" group="sort.list" />
	                    <list:sort property="hrRatifyRemove.docPublishTime" text="${lfn:message('hr-ratify:hrRatifyMain.docPublishTime')}" group="sort.list" />
	                </ui:toolbar>
	            </div>
	        </div>
	        <div class="lui_list_operation_page_top">
	            <list:paging layout="sys.ui.paging.top" />
	        </div>
	        <div style="float:right">
	            <div style="display: inline-block;vertical-align: middle;">
	                <ui:toolbar count="3">
	
	                    <kmss:authShow roles="ROLE_HRRATIFY_CREATE">
	                        <ui:button text="${lfn:message('button.add')}" onclick="addDoc0();" order="2" />
	                    </kmss:authShow>
	                    <kmss:auth requestURL="/hr/ratify/hr_ratify_remove/hrRatifyRemove.do?method=deleteall">
	                        <c:set var="canDelete" value="true" />
	                    </kmss:auth>
	                    <!--删除-->
	                    <ui:button text="${lfn:message('button.deleteall')}" onclick="delDoc();" order="4" id="btnDelete" />
	                </ui:toolbar>
	            </div>
	        </div>
	    </div>
	    <ui:fixed elem=".lui_list_operation" />
	    <!-- 列表 -->
	    <list:listview id="listview">
	        <ui:source type="AjaxJson">
	            {url:'/hr/ratify/hr_ratify_remove/hrRatifyRemove.do?method=data'}
	        </ui:source>
	        <!-- 列表视图 -->
	        <list:colTable isDefault="false" rowHref="/hr/ratify/hr_ratify_remove/hrRatifyRemove.do?method=view&fdId=!{fdId}" name="columntable">
	            <list:col-checkbox />
	            <list:col-serial/>
	            <list:col-auto props="docSubject;docNumber;docCreator.name;docCreateTime;docPublishTime;docStatus.name;lbpm_main_listcolumn_node;lbpm_main_listcolumn_handler" url="" /></list:colTable>
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
		    seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'],function($, dialog, topic){
		    	var cateId= '', nodeType = '',docStatus = 0;
				//根据筛选器分类异步校验权限
				topic.subscribe('criteria.spa.changed',function(evt){
					//筛选器变化时清空分类和节点类型的值
					cateId = "";  
					nodeType = "";
					var hasCate = false;
					for(var i=0;i<evt['criterions'].length;i++){
						  //获取分类id和类型
		             	  if(evt['criterions'][i].key == "docTemplate"){
		             		 hasCate = true;
		                 	 cateId= evt['criterions'][i].value[0];
			                 nodeType = evt['criterions'][i].nodeType;
		             	  }
		             	   if(evt['criterions'][i].key == 'docStatus') {
							  docStatus = evt['criterions'][i].value[0];
						  }
					}		
				});
		    	//新建文档
				window.addDoc0 = function(){
					dialog.category('com.landray.kmss.hr.ratify.model.HrRatifyTemplate','docTemplateId','docTemplateName',false,function(rtn){
						if(rtn != false&&rtn != null){
							var tempId = rtn.id;
							var tempName = rtn.name;
							if(tempId !=null && tempId != ''){
								var url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_remove/hrRatifyRemove.do?method=add&i.docTemplate="+tempId;
								Com_OpenWindow(url, '_blank');
							}
						}
					},null,null,null,null,null,'HrRatifyRemoveDoc');
				};
				//删除文档
	    		window.delDoc = function(draft){
	    			var values = [];
	    			$("input[name='List_Selected']:checked").each(function(){
	    				values.push($(this).val());
	    			});
	    			if(values.length==0){
	    				dialog.alert('${lfn:message("page.noSelect")}');
	    				return;
	    			}
	    			var url = Com_Parameter.ContextPath + 'hr/ratify/hr_ratify_remove/hrRatifyRemove.do?method=deleteall';
	    			url = Com_SetUrlParameter(url, 'categoryId', cateId);
	    			url = Com_SetUrlParameter(url, 'nodeType', nodeType); 
	    			if(draft == '0'){
	    				url = Com_Parameter.ContextPath + 'hr/ratify/hr_ratify_remove/hrRatifyRemove.do?method=deleteall&status=10';
	    			}
	    			var config = {
	    				url : url, // 删除数据的URL
	    				data : $.param({"List_Selected":values},true), // 要删除的数据
	    				modelName : "com.landray.kmss.hr.ratify.model.HrRatifyRemove" // 主要是判断此文档是否有部署软删除
	    			};
	    			// 通用删除方法
	    			function delCallback(data){
	    				topic.publish("list.refresh");
	    				dialog.result(data);
	    			}
	    			Com_Delete(config, delCallback);
	    		};
		    });
	    </script>
    </template:replace>
</template:include>