<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		
      <html:form action="/kms/medal/kms_medal_category/kmsMedalCategory.do">
	    
	    <input type="hidden" name="modelName" value="com.landray.kmss.kms.medal.model.KmsMedalCategory">
	    <input type="hidden" name="mainModelName" value="com.landray.kmss.kms.medal.model.KmsMedalMain">
	    
        <!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			
			<div class="lui_list_operation_order_text">
                <%-- 排序文本 --%>
                ${ lfn:message('list.orderType') }：
            </div>
            
            <%--排序按钮  --%>
            <div class="lui_list_operation_sort_toolbar">
				<ui:toolbar layout="sys.ui.toolbar.sort" >
					<list:sort property="docCreateTime" text="${lfn:message('kms-medal:kmsMedalMain.docCreateTime') }"></list:sort>
				</ui:toolbar>
			</div>
			<!-- 刷新按钮 -->
			<div  class="lui_list_operation_page_top">
                <list:paging layout="sys.ui.paging.top" ></list:paging>
            </div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div  class="lui_table_toolbar_inner">
                    <ui:toolbar count="2">
                       <kmss:auth requestURL="/kms/medal/kms_medal_category/kmsMedalCategory.do?method=add">
                        	<!-- 新增 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
                        </kmss:auth>
                       <kmss:auth requestURL="/kms/medal/kms_medal_category/kmsMedalCategory.do?method=deleteall">
                        	<!-- 批量删除 -->
                        	<ui:button text="${lfn:message('button.deleteall')}" onclick="del()"></ui:button>
                        </kmss:auth>
                    </ui:toolbar>
                </div>
			</div>
		</div>
        
        <ui:fixed elem=".lui_list_operation"></ui:fixed>
        
        <list:listview id="listview">
            <ui:source type="AjaxJson">
                    {url:'/kms/medal/kms_medal_category/kmsMedalCategory.do?method=data&orderby=docCreateTime&ordertype=down'}
            </ui:source>
            
            <!-- 列表视图 -->
            <list:colTable isDefault="false"
                rowHref="/kms/medal/kms_medal_category/kmsMedalCategory.do?method=view&fdId=!{fdId}" name="columntable">
                <list:col-checkbox></list:col-checkbox>
                <list:col-serial></list:col-serial>
                <list:col-auto props="fdName;docCreator.fdName;docCreateTime;"></list:col-auto>
            </list:colTable>
        </list:listview> 
        
        <list:paging></list:paging>
        </html:form>
	</template:replace>

</template:include>	

<script type="text/javascript">
seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	// 监听新建更新等成功后刷新
	topic.subscribe('successReloadPage', function() {
		topic.publish("list.refresh");
	});
	// 增加
	window.add = function() {
		Com_OpenWindow('<c:url value="/kms/medal/kms_medal_category/kmsMedalCategory.do" />?method=add');
	};
	// 删除
	window.del = function(id) {
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
		
		dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
			if(value == true) {
				Com_Submit(document.kmsMedalCategoryForm, 'deleteall');
		} });
	};
});
</script>
