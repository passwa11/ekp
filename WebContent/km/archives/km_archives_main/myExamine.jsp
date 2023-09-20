<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<template:include ref="default.simple" spa="true">
    <template:replace name="body">
    <script>
    	seajs.use(['theme!list']);
    </script>
		<!-- 筛选 -->
            <list:criteria id="examineCriteria">
            	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('km-archives:kmArchivesMain.docSubject')}"></list:cri-ref>
                <list:cri-ref ref="criterion.sys.simpleCategory" key="docTemplate" multi="false" title="${lfn:message('km-archives:kmArchivesMain.docTemplate')}" expand="false">
                    <list:varParams modelName="com.landray.kmss.km.archives.model.KmArchivesCategory" />
                </list:cri-ref>
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="docNumber" />
                <%-- <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="docCreator" /> --%>
                <list:cri-auto modelName="com.landray.kmss.km.archives.model.KmArchivesMain" property="fdFileDate" />
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

                            <kmss:authShow roles="ROLE_KMARCHIVES_CREATE">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:authShow>
                            <kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=batchUpdate">
                                <ui:button text="${lfn:message('km-archives:kmArchivesMain.batchUpdate')}" onclick="batchUpdate()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=importArchives">
                                <ui:button id="importBtnApproval" text="${lfn:message('km-archives:kmArchivesMain.importArchives')}" onclick="importArchives()" order="2" />
                            </kmss:auth>
                            <!---->
                            <kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=deleteall">
                            	<ui:button text="${lfn:message('button.deleteall')}" onclick="delDoc()" order="4" id="btnDeleteApproval" />
                        	</kmss:auth>
                        	<c:import url="/sys/right/import/doc_right_change_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
							</c:import>
							<kmss:authShow roles="ROLE_KMARCHIVES_TRANSPORT_EXPORT">
							<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.km.archives.model.KmArchivesMain')" order="4" ></ui:button>
							</kmss:authShow>	
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listviewExamine">
                <ui:source type="AjaxJson">
                    {url:'/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.j_path=/examineDoc&q.mydoc=${JsParam.mydoc }'}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/km/archives/km_archives_main/kmArchivesMain.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="docSubject;docNumber;docCreator.fdName;fdFileDate;fdLibrary;lbpm_main_listcolumn_node;lbpm_main_listcolumn_handler" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        <script>
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
            seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/spa/const'],function($,dialog,topic,spaConst) {
            	var cateId;
            	window.importArchives = function() {
                	if(cateId == null) {
                		dialog.alert("${lfn:message('km-archives:please.choose.category')}");
                	}else {
                		 Com_OpenWindow("${LUI_ContextPath}/km/archives/km_archives_main/kmArchivesMain_upload.jsp?docTemplate="+cateId);
                	}
            	};
            	window.batchUpdate = function() {
            		var selected = [];
                    $("input[name='List_Selected']:checked").each(function(){
                        selected.push($(this).val());
                    });
                    if(selected.length == 0) {
                    	dialog.alert('${lfn:message("page.noSelect")}');
            			return;
                    }
            		var url = "/km/archives/km_archives_main/kmArchivesMain_batchUpdate.jsp?selectedIds="+selected.join(";");
            		dialog.iframe(url,"${lfn:message('km-archives:kmArchivesMain.batchUpdate')}", function(value) {
    					topic.publish('list.refresh');
    				}, {
    					"width" : 500,
    					"height" : 300
    				});
            	};
            	topic.subscribe('criteria.spa.changed',function(evt){
            		cateId = null;
            		for(var i=0;i<evt['criterions'].length;i++){
						//获取分类id和类型
		             	if(evt['criterions'][i].key=="docTemplate"){
			                cateId= evt['criterions'][i].value[0];
		             	}
					}
            	});
            	//删除
				window.delDoc = function(){
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var delUrl = '<c:url value="/km/archives/km_archives_main/kmArchivesMain.do?method=deleteall"/>';
					dialog.iframe('/sys/edition/import/doc_delete_iframe.jsp?fdModelName=com.landray.kmss.km.archives.model.KmArchivesMain&fdType=POST',
							"<bean:message key='ui.dialog.operation.title' bundle='sys-ui'/>",
							function (value){
		                        // 回调方法
								if(value) {
									delCallback(value);
								}
							},
							{width:400,height:160,params:{url:delUrl,data:$.param({"List_Selected":values},true)}}
					);
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