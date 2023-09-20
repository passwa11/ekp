<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.support.util.LbpmPrivilegeLogUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.list">
	
	<template:replace name="content">
			<!-- 查询条件  -->
		<list:criteria id="criteria">
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-lbpmservice-support:lbpmPrivilegeLog.fdSubject') }">
			</list:cri-ref>
			<list:cri-ref ref="criterion.sys.person" key="docCreator" multi="false" title="${lfn:message('sys-lbpmservice-support:lbpmPrivilegeLog.docCreator') }" />			  
			<list:cri-criterion title="${lfn:message('sys-lbpmservice-support:lbpmPrivilegeLog.fdActionInfo')}" key="fdActionKey" multi="false"> 
					<list:box-select>
					   <list:item-select>
					    <ui:source type="AjaxJson">
								{url:'/sys/lbpmservice/support/lbpm_privilege_log/lbpmPrivilegeLog.do?method=getFdActionName'}
						  </ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>		 	
		   <list:cri-auto modelName="com.landray.kmss.sys.lbpmservice.support.model.LbpmPrivilegeLog" 
				property="fdCreateTime" /> 
			<%--模块--%> 
			<c:if test="${param.fdModelName eq null}">
				<list:cri-criterion title="${lfn:message('sys-lbpmservice-support:lbpmPrivilegeLog.fdModuleName')}" key="fdModelName" multi="false"> 
					<list:box-select>
					   <list:item-select>
						  <ui:source type="AjaxJson">
								{url:'/sys/lbpmservice/support/lbpm_privilege_log/lbpmPrivilegeLog.do?method=getModule'}
						  </ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
			</c:if>
		 
		</list:criteria>
		
		 
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					    <list:sortgroup>
							<list:sort property="fdCreateTime" text="${lfn:message('sys-lbpmservice-support:lbpmPrivilegeLog.fdCreateTime') }" group="sort.list"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
				
				<!-- 分页 -->
				<div class="lui_list_operation_page_top">
					<%@ include file="/sys/profile/showconfig/showConfig_paging_top.jsp" %>
				</div>
				
				<div style="float:right">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar id="Btntoolbar">
							<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_privilege_log/lbpmPrivilegeLog.do?method=deleteall">					 								
								<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4"></ui:button>
							</kmss:auth>
						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation" id="hack_fix"></ui:fixed>
		
		 
		 
	  <list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/sys/lbpmservice/support/lbpm_privilege_log/lbpmPrivilegeLog.do?method=data&&categoryId=${param.categoryId}&nodeType=${param.nodeType}&fdModelName=${param.fdModelName}&fdTemplateModelName=${param.fdTemplateModelName}'}
 			</ui:source>
			
			<!-- 列表视图 -->
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" rowHref="/sys/lbpmservice/support/lbpm_privilege_log/lbpmPrivilegeLog.do?method=view&modelName=${param.fdModelName}&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdSubject;fdActionInfo;fdModelName;fdHandlerName;fdCreateTime;fdIpAddr;authAreaName"></list:col-auto>
			</list:colTable>
		</list:listview> 
		 
		 <br>
		<%@ include file="/sys/profile/showconfig/showConfig_paging_buttom.jsp"%>
	 	

	    <script>
        	seajs.use(['lui/dialog','lui/topic'],function(dialog,topic) {
        		window.deleteAll = function(id){
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
    				var url = '<c:url value="/sys/lbpmservice/support/lbpm_privilege_log/lbpmPrivilegeLog.do?method=deleteall"/>';
    				var config = {
   	     				url : url, // 删除数据的URL
   	     				data : $.param({"List_Selected":values},true), // 要删除的数据
         			};
    	     			
         			Com_Delete(config, delCallback);
    			};
    			window.delCallback = function(data){
    				if(window.del_load!=null){
    					window.del_load.hide();
    					topic.publish("list.refresh");
    				}
    				dialog.result(data);
    			};
        	});
        </script>
	</template:replace>
</template:include>


