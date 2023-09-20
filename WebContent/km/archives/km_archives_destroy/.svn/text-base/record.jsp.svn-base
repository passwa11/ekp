<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
    <template:replace name="body">
    <script>
    	seajs.use(['theme!list']);
    </script>
            <!-- 筛选 -->
            <list:criteria id="destroyCriteria">
            	<list:cri-ref key="fdArchivesName" ref="criterion.sys.docSubject" title="${lfn:message('km-archives:kmArchivesMain.docSubject')}"></list:cri-ref>
            	<list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesDestroy" property="fdArchivesNumber" />
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesDestroy" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesDestroy" property="docCreateTime" />
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
                            <list:sort property="docCreateTime" text="${lfn:message('km-archives:kmArchivesDestroy.docCreateTime')}" group="sort.list" value="down"/>
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
                            <kmss:auth requestURL="/km/archives/km_archives_destroy/kmArchivesDestroy.do?method=printDestroyList">
                                <ui:button text="${lfn:message('km-archives:kmArchivesDestroy.printDestroyList')}" onclick="printDestroyList()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/km/archives/km_archives_destroy/kmArchivesDestroy.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAllRecord()" order="4" id="btnDelete" />
							<kmss:authShow roles="ROLE_KMARCHIVES_TRANSPORT_EXPORT">
							<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.km.archives.model.KmArchivesMain')" order="4" ></ui:button>
							</kmss:authShow>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/km/archives/km_archives_destroy/kmArchivesDestroy.do?method=data&isRecord=true&q.j_path=/destroy')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.archives.model.KmArchivesDestroy" layout="sys.ui.listview.columntable" isDefault="false" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto/>
                </list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        <form name="printForm" method="post" action="${LUI_ContextPath }/km/archives/km_archives_destroy/kmArchivesDestroy.do" target="_blank" style="display:none;">
        	<input type="hidden" name="selectIds"/>
        </form>
        <script>
            var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.archives.model.KmArchivesBorrow;com.landray.kmss.km.archives.model.KmArchivesMain";
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.km.archives.model.KmArchivesDestroy',
                templateName: '',
                basePath: '/km/archives/km_archives_destroy/kmArchivesDestroy.do',
                canDelete: '${canDelete}',
                mode: '',
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
            seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
            	window.printDestroyList = function() {
	            	var selected = [];
	                $("input[name='List_Selected']:checked").each(function(){
	                    selected.push($(this).val());
	                });
	                if(selected.length == 0) {
	                	dialog.alert('${lfn:message("page.noSelect")}');
	        			return;
	                }
	                document.printForm.selectIds.value = selected.join(';');
	                Com_SubmitNoEnabled(document.printForm,'printDestroyList');
                };
            });
            seajs.use(['lui/jquery','lui/dialog','lui/topic'],function($,dialog,topic) {
	        	// 软删除
	  	 		window.deleteAllRecord = function(){
	  	 			var values = [];
	  				$("input[name='List_Selected']:checked").each(function(){
	  						values.push($(this).val());
	  					});
	  				if(values.length==0){
	  					dialog.alert('<bean:message key="page.noSelect"/>');
	  					return;
	  				}
	  				var config = {
	  					url : "${LUI_ContextPath}/km/archives/km_archives_destroy/kmArchivesDestroy.do?method=deleteall", // 删除数据的URL
	  					data : $.param({"List_Selected":values},true), // 要删除的数据
	  					modelName : "com.landray.kmss.km.archives.model.KmArchivesDestroy" // 主要是判断此文档是否有部署软删除
	  				};
	  				// 通用删除方法
	  				Com_Delete(config, delCallback);
	  			};
	  			
	  			window.delCallback = function(data){
	  				topic.publish("list.refresh");
	  				dialog.result(data);
	  			};
            });
        </script>
    </template:replace>
</template:include>