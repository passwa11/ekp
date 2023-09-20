<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('third-ding:thirdDingEvent.fdName')}" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingEvent" property="fdTag" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingEvent" property="fdCallbackUrl" />
                <list:cri-auto modelName="com.landray.kmss.third.ding.model.ThirdDingEvent" property="fdIsAvailable" />

            </list:criteria>
            <!-- 操作 -->
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
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="thirdDingEvent.fdName" text="${lfn:message('third-ding:thirdDingEvent.fdName')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <!-- 分页 -->
				<div class="lui_list_operation_page_top">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
							
							 	<ui:button text="${lfn:message('button.import')}" onclick="importEvent()" order="2" />
                           <%--  <kmss:auth requestURL="/third/ding/third_ding_event/thirdDingEvent.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth> --%>
                            <kmss:auth requestURL="/third/ding/third_ding_event/thirdDingEvent.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <%-- <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" /> --%>

                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/third/ding/third_ding_event/thirdDingEvent.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/third/ding/third_ding_event/thirdDingEvent.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdTag;fdCallbackUrl;fdIsStatus.name;docCreator.name;docCreateTime;operations" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.third.ding.model.ThirdDingEvent',
                templateName: '',
                basePath: '/third/ding/third_ding_event/thirdDingEvent.do',
                canDelete: '${canDelete}',
                mode: '',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
            
            function importEvent(){
            	$.ajax({
        	        type: "post",
        	        url: "${LUI_ContextPath}/third/ding/third_ding_event/thirdDingEvent.do?method=importEvent",
        	        async : false,
        	        dataType: "json",
        	        success: showResult
        	     });
            }
            seajs.use(['lui/dialog', 'lui/topic'], function(dialog,topic){
				window.showResult = function(data){
					if(true == data.result){
						dialog.alert("导入成功！", function(){
		            		window.location.href = "${LUI_ContextPath}/third/ding/third_ding_event/index.jsp";
		            	});
		            }else{
		            	dialog.alert("导入失败！", function(){
		            		window.location.href = "${LUI_ContextPath}/third/ding/third_ding_event/index.jsp";
		            	});
		                return false;
		            }
				};
				window.deleteDoc = function(id){
		 			var url = '<c:url value="/third/ding/third_ding_event/thirdDingEvent.do?method=delete"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'GET',
								data:{fdId:id},
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: delCallback
						   });
						}
					});
		 		};
		 		window.updateStatus = function(id, status){
		 			var url = '<c:url value="/third/ding/third_ding_event/thirdDingEvent.do?method=updateStatus"/>';
					window.del_load = dialog.loading();
					$.ajax({
						url: url,
						type: 'post',
						data:{"fdId":id,"status":status},
						dataType: 'json',
						error: function(data){
							if(window.del_load!=null){
								window.del_load.hide(); 
							}
							dialog.result(data.msg);
						},
						success: delCallback
				   });
		 		};
		 		window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
						topic.publish("list.refresh");
					}
		 			if(data.code == "0"){
		 				dialog.alert("操作成功！");
		 			}else{
						dialog.alert(data.msg);
		 			}
				};
			});
        </script>
    </template:replace>
</template:include>