<%@page import="com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<template:include ref="default.simple" spa="true" rwd="true">
    <link rel="stylesheet" href="${KMSS_Parameter_ContextPath}dbcenter/echarts/common/echart_index.css" />

    <template:replace name="title">
        <c:out value="${ lfn:message('dbcenter-echarts:module.table.dbEchartsTotal') }"></c:out>
    </template:replace>
    <template:replace name="body">
    	
        <ui:tabpanel id="echartsPanel" layout="sys.ui.tabpanel.list" >
			<ui:content id="docCategory" title="${ lfn:message('dbcenter-echarts:module.echarts.menu.sysSimpleCategory') }">
			
	
		<ui:combin ref="menu.path.simplecategory" >
			<ui:varParams
				id="simplecategoryId"
				moduleTitle="${ lfn:message('dbcenter-echarts:module.echarts.menu.sysSimpleCategory') }"
				modelName="com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate" 
				categoryId="${JsParam.docCategory}" href="javascript:toRedirect('!{docCategory}')"/>
		</ui:combin>
	
				 <!-- 查询条件  -->
        <list:criteria id="criteria">
            <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('dbcenter-echarts:dbEchartsCustom.docSubject') }">
            </list:cri-ref>
            <list:cri-auto modelName="com.landray.kmss.dbcenter.echarts.model.DbEchartsTotal"
                           property="dbEchartsTemplate" />
        </list:criteria>

        <!-- 操作栏 -->
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
                        <list:sort property="docCreateTime" text="${lfn:message('dbcenter-echarts:dbEchartsCustom.docCreateTime') }" group="sort.list" value="down"></list:sort>
                    </ui:toolbar>
                </div>
            </div>
            <!-- 分页 -->
			<div class="lui_list_operation_page_top">
                <list:paging layout="sys.ui.paging.top" >
                </list:paging>
            </div>
            <!-- 操作按钮-->
            <div style="float:right">
                <div style="display: inline-block;vertical-align: middle;">
                    
                </div>
            </div>

        </div>
        <ui:fixed elem=".lui_list_operation"></ui:fixed>

        <!-- 内容列表 -->
        <list:listview>
            <ui:source type="AjaxJson">
                {url:'/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=list&orderby=docCreateTime&ordertype=down&categoryId=${param.categoryId}'}
            </ui:source>
            <list:colTable isDefault="true" layout="sys.ui.listview.columntable"
                           rowHref="!{echartTypeUrl}!{fdId}">
                <list:col-checkbox></list:col-checkbox>
                <list:col-serial></list:col-serial>
                <list:col-auto props="docSubject,dbEchartsTemplate.fdName,docCreateTime,docCreator.fdName,operations"></list:col-auto>
            </list:colTable>
        </list:listview>

        <list:paging></list:paging>
			</ui:content>
			
	</ui:tabpanel>
       
    </template:replace>
</template:include>
<script type="text/javascript">
    function toRedirect(docCategory){
    	var redirectUrl = "echart_total.jsp?docCategory=";
    	if(docCategory == '!{docCategory}'){
    		docCategory = "";
    	}else{
    		redirectUrl = "echart_total.jsp?docCategory="+docCategory+"#cri.q=dbEchartsTemplate:"+docCategory;
    	}
    	window.location = redirectUrl;
    }
    seajs.use(['lui/jquery','lui/dialog','lui/topic'],function($,dialog,topic){
		
    	/**------ 取消关注我的报表  ------**/
	 	window.deleteMyAttentionEcharts = function(id){
			dialog.confirm('${ lfn:message("dbcenter-echarts:module.echarts.my.following.noConfirm") }',function(isOk){
				if(isOk){
					window.del_load = dialog.loading();
					var attentionUrl = '<c:url value="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=deleteMyAttentionEcharts&fdId="/>'+id;
					$.ajax({
						url: attentionUrl,
						type: "GET",
						dataType: "json",
						success: delCallback,
						error: delErrorCallback
					});
				}
			});			 		
	 	};
	 	
	 	/**------ 关注我的报表  ------**/
	 	window.createMyAttentionEcharts = function(id){
			dialog.confirm('${ lfn:message("dbcenter-echarts:module.echarts.my.following.confirm") }',function(isOk){
				if(isOk){
					window.del_load = dialog.loading();
					var attentionUrl = '<c:url value="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=createMyAttentionEcharts&fdId="/>'+id;
					$.ajax({
						url: attentionUrl,
						type: "GET",
						dataType: "json",
						success: delCallback,
						error: delErrorCallback
					});
				}
			});			 		
	 	};
	 	
	 	
	 	
        /**------ 删除 自定义数据  ------**/
        window.deleteAll = function(id){
            var values = [];
            if(id){
                values.push(id);
            }else{
                $("input[name='List_Selected']:checked").each(function(){
                    values.push($(this).val());
                });
            }
            if(values.length==0){
                dialog.alert('${lfn:message("page.noSelect")}');
                return;
            }
            dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
                if(value==true){
                    window.del_load = dialog.loading();
                    $.ajax({
                        url:'<c:url value="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=deleteall"/>',
                        type:"POST",
                        data:$.param({"List_Selected":values},true),
                        dataType:"json",
                        success:delCallback,
                        error:delErrorCallback
                    });
                }
            });
        };

        /**------ 删除成功回调函数  ------**/
        window.delCallback = function(data){
            if(window.del_load!=null){
                window.del_load.hide();
            }
            topic.publish("list.refresh");
            dialog.result(data);
        };

        /**------ 删除失败回调函数  ------**/
        window.delErrorCallback = function(data){
            var messages=data.responseJSON.message;
            var message=messages[0];
            if(window.del_load!=null){
                window.del_load.hide();
            }
            dialog.alert(message.msg);
            topic.publish("list.refresh");
        }

        /**------ 监听筛选器内容修改，记录下选择的分类  ------**/
        var cateId = ""; // 选中的分类ID
        topic.subscribe('criteria.spa.changed',function(evt){
        	
            cateId = ""; // 清空选中分类ID
            for(var i=0;i<evt['criterions'].length;i++){
                //获取分类id和类型
                if(evt['criterions'][i].key == "dbEchartsTemplate"){
                    cateId= evt['criterions'][i].value[0];
                    
                    console.log(""+cateId);
                }
            }
            
            parent.moduleAPI.dbEcharts.openPreview(cateId,false);
            
        });

    });
</script>