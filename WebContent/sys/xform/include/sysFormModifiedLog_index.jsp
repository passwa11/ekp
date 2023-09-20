<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.profile.list">
    <template:replace name="content">
        <!-- 筛选器 -->
		<list:criteria id="criteria">
			<%-- <list:cri-ref ref="criterion.sys.category" key="fdTemplate" multi="false" expand="false" title="${ lfn:message('km-review:kmReviewMain.criteria.fdTemplate') }">
				  <list:varParams modelName="${ param.fdTemplateModelName }"/>
			</list:cri-ref> --%>
		     <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-xform-base:sysFormModifiedLog.fdTemplateName') }">
			</list:cri-ref>
			<list:cri-ref ref="criterion.sys.person" key="docCreator" multi="false" title="${lfn:message('sys-xform-base:sysFormModifiedLog.docCreator') }" />
			<list:cri-auto modelName="com.landray.kmss.sys.xform.base.model.SysFormModifiedLog" property="docCreateTime"/>
		</list:criteria>
        <!-- 操作栏 -->
        <div class='lui_list_operation'>
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
							<list:sort property="docCreateTime" text="${lfn:message('sys-xform-base:sysFormModifiedLog.docCreateTime') }" group="sort.list"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<div style="display: inline-block;vertical-align: middle;float:right;">
				<ui:toolbar id="Btntoolbar" count="10">
					<kmss:auth
						requestURL="/sys/xform/base/sysFormModifiedLogAction.do?method=deleteall&fdTemplateModelName=${JsParam.fdTemplateModelName}"
						requestMethod="GET">
						<ui:button id="del" text="${lfn:message('button.deleteall')}" order="8" onclick="deleteAll()"></ui:button>
					</kmss:auth>
				</ui:toolbar>
			</div>
		</div>
        <ui:fixed elem=".lui_list_operation"/>
        <!-- 列表 -->
        <list:listview id="listview">
            <ui:source type="AjaxJson">
                {url:'/sys/xform/base/sysFormModifiedLogAction.do?method=data&&categoryId=${param.categoryId}&nodeType=${param.nodeType}&fdTemplateModelName=${param.fdTemplateModelName}'}
            </ui:source>
            <!-- 列表视图 -->
            <list:colTable isDefault="true" layout="sys.ui.listview.columntable" rowHref="/sys/xform/base/sysFormModifiedLogAction.do?method=view&fdId=!{fdId}&fdModelId=!{fdModelId}">
                <list:col-checkbox></list:col-checkbox>
                <list:col-serial/>
                <list:col-auto props="fdName;fdFormVersion;docCreator.name;docCreateTime;fdIp;authAreaName"/>
            </list:colTable>
        </list:listview>
        <!-- 翻页 -->
        </div>
        <list:paging/>

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
    				var url = '<c:url value="/sys/xform/base/sysFormModifiedLogAction.do?method=deleteall"/>';
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