<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include file="/sys/profile/resource/template/list.jsp">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('km-archives:kmArchivesUnit.fdName')}" />

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
                            <list:sortgroup>
	                            <list:sort property="fdOrder" text="${lfn:message('km-archives:kmArchivesUnit.fdOrder')}" group="sort.list" value="up" />
	                            <list:sort property="fdName" text="${lfn:message('km-archives:kmArchivesUnit.fdName')}" group="sort.list" />
                        	</list:sortgroup>
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
                            <kmss:auth requestURL="/km/archives/km_archives_unit/kmArchivesUnit.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/km/archives/km_archives_unit/kmArchivesUnit.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
							<!-- 快速排序 -->
							<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.archives.model.KmArchivesUnit"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
							</c:import>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/km/archives/km_archives_unit/kmArchivesUnit.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/km/archives/km_archives_unit/kmArchivesUnit.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <%-- <list:col-serial/> --%>
                    <list:col-auto props="fdOrder;fdName;fdAdmin.fdName;docCreator.fdName;docCreateTime;operations" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.archives.model.KmArchivesBorrow;com.landray.kmss.km.archives.model.KmArchivesMain";
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.km.archives.model.KmArchivesUnit',
                templateName: '',
                basePath: '/km/archives/km_archives_unit/kmArchivesUnit.do',
                canDelete: '${canDelete}',
                mode: '',
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
            seajs.use(['lui/dialog','lui/jquery','lui/topic'],function(dialog,$,topic) {
            	window.deleteDoc = function(fdId) {
            		dialog.confirm('${lfn:message("page.comfirmDelete")}',function(isOk) {
            			if(isOk) {
            				var url = '<c:url value="/km/archives/km_archives_unit/kmArchivesUnit.do?method=deleteall"/>';
            				window.delete_load = dialog.loading();
            				var values = [fdId];
            				$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values},true),
								dataType: 'json',
								error: function(data){
									if(window.delete_load!=null){
										window.delete_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: deleteCallback
						   });
            			}
            		});
            	};
            	
            	window.deleteCallback = function(data){
					if(window.delete_load!=null){
						window.delete_load.hide();
						topic.publish("list.refresh");
					}
					dialog.result(data);
				};
            });
            function editDoc(fdId) {
            	Com_OpenWindow('${LUI_ContextPath}/km/archives/km_archives_unit/kmArchivesUnit.do?method=edit&fdId='+fdId);
            }
        </script>
    </template:replace>
</template:include>