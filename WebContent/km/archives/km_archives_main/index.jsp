<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
    <template:replace name="body">
    <script>
    	seajs.use(['theme!list']);
    </script>
            <!-- 筛选 -->
            <list:criteria id="queryDateCriteria">
            	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('km-archives:kmArchivesMain.docSubject')}"></list:cri-ref>
                <list:cri-ref ref="criterion.sys.simpleCategory" key="docTemplate" multi="false" title="${lfn:message('km-archives:kmArchivesMain.docTemplate')}" expand="false">
                    <list:varParams modelName="com.landray.kmss.km.archives.model.KmArchivesCategory" />
                </list:cri-ref>
                <list:cri-criterion expand="false" title="${ lfn:message('km-archives:kmArchivesMain.fdLibrary') }" key="fdLibrary" multi="false">
                    <list:box-select>
                        <list:item-select>
                            <ui:source type="AjaxJson">
								{"url":"/km/archives/km_archives_library/kmArchivesLibrary.do?method=criteria"} 
							</ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="docNumber" />
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="docCreator" />
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="fdFileDate" />
				<list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="fdValidityDate" />
				<!-- 属性库自定义筛选属性 -->
				<list:cri-property
						modelName="com.landray.kmss.km.archives.model.KmArchivesCategory" 
						cfg-spa="true" cfg-cri="docTemplate"/>
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
	                            <list:sort property="fdFileDate" text="${lfn:message('km-archives:kmArchivesMain.fdFileDate')}" group="sort.list" value="down" />
	                            <list:sort property="fdLibrary" text="${lfn:message('km-archives:kmArchivesMain.fdLibrary')}" group="sort.list"/>
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
                            <kmss:auth requestURL="/km/archives/km_archives_appraise/kmArchivesAppraise.do?method=add">
                                <ui:button text="${lfn:message('km-archives:kmArchivesAppraise.appraise')}" onclick="appraiseDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/km/archives/km_archives_destroy/kmArchivesDestroy.do?method=add">
                                <ui:button text="${lfn:message('km-archives:kmArchivesDestroy.destroy')}" onclick="destroyDoc()" order="2" />
                            </kmss:auth>
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
                    {url:appendQueryParameter('/km/archives/km_archives_index/kmArchivesIndex.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.archives.model.KmArchivesMain" layout="sys.ui.listview.columntable" isDefault="false" rowHref="/km/archives/km_archives_main/kmArchivesMain.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto />
                </list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        <script>
        	var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.archives.model.KmArchivesMain;com.landray.kmss.km.archives.model.KmArchivesBorrow";
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.km.archives.model.KmArchivesMain',
                templateName: 'com.landray.kmss.km.archives.model.KmArchivesCategory',
                basePath: '/km/archives/km_archives_main/kmArchivesMain.do',
                canDelete: '${canDelete}',
                mode: 'main_scategory',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
            seajs.use(['lui/jquery','lui/dialog','lui/topic'],function($,dialog,topic) {
            	var getSelects = function() {
            		var selected = [];
                    $("input[name='List_Selected']:checked").each(function(){
                        selected.push($(this).val());
                    });
                    return selected.join(';');
            	};
            	window.appraiseDoc = function() {
            		var selects = getSelects();
            		if(selects == "") {
            			dialog.alert('${lfn:message("page.noSelect")}');
            			return;
            		}
            		var url = "/km/archives/km_archives_appraise/kmArchivesAppraise.do?method=add&selectedIds="+selects;
            		dialog.iframe(url,"${lfn:message('km-archives:kmArchivesAppraise.newAppraise')}", function(value) {
    					topic.publish('list.refresh');
    				}, {
    					"width" : 1000,
    					"height" : 500
    				});
            	};
				window.destroyDoc = function() {
					var selects = getSelects();
            		if(selects == "") {
            			dialog.alert('${lfn:message("page.noSelect")}');
            			return;
            		}
            		var url = "/km/archives/km_archives_destroy/kmArchivesDestroy.do?method=add&selectedIds="+selects;
            		dialog.iframe(url,"${lfn:message('km-archives:kmArchivesDestroy.newDestroy')}", function(value) {
    					topic.publish('list.refresh');
    				}, {
    					"width" : 1000,
    					"height" : 500
    				});
            	};
            });
            
        </script>
    </template:replace>
</template:include>