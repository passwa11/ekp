<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.list" spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-common:module.fssc.common') }-${ lfn:message('fssc-common:table.fsscCommonTransferField') }" />
    </template:replace>
    <template:replace name="nav">
    <ui:combin ref="menu.nav.title">
            <ui:varParam name="title" value="${ lfn:message('fssc-common:py.ShuJuQianYi') }" />
            <%-- 数据区 --%>
				<ui:varParam name="info" >
					<ui:source type="Static">
					</ui:source>
				</ui:varParam>
				<ui:varParam name="operation">
					<ui:source type="Static">
					[]
					</ui:source>
				</ui:varParam>
        </ui:combin>
    <div id="menu_nav" class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>
				<ui:content title="${ lfn:message('fssc-common:py.QianYiPeiZhi') }">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
		  					<ui:source type="Static">
			  					[{
			  						"text" : "${lfn:message('fssc-common:py.QianYiDaoHang') }",
			  						"href" :  "/listNav",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_all"
			  					},{
			  						"text" : "${lfn:message('fssc-common:table.fsscCommonTransferField') }",
			  						"href" :  "/listField",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_all"
			  					},{
			  						"text" : "${lfn:message('fssc-common:table.fsscCommonTransferLog') }",
			  						"href" :  "/listLog",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_com_all"
			  					}]
		 					</ui:source>
		 				</ui:varParam>
					</ui:combin>
				</ui:content>
				</ui:accordionpanel>
        </div>
        </template:replace>
    <template:replace name="content">
    <ui:tabpanel id="fsscCommonTransferFieldPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
		<ui:content id="fsscCommonTransferFieldContent" title="${lfn:message('fssc-common:table.fsscCommonTransferField') }" cfg-route="{path:'/listField'}">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdSourceTableName" ref="criterion.sys.docSubject" title="${lfn:message('fssc-common:fsscCommonTransferField.fdSourceTableName')}" />
                <list:cri-auto modelName="com.landray.kmss.fssc.common.model.FsscCommonTransferField" property="fdTargetTableName" />
                <list:cri-auto modelName="com.landray.kmss.fssc.common.model.FsscCommonTransferField" property="fdIsProcessed" />
                <list:cri-auto modelName="com.landray.kmss.fssc.common.model.FsscCommonTransferField" property="fdFinishedTime" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">
				<div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/fssc/common/fssc_common_transfer_field/fsscCommonTransferField.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/common/fssc_common_transfer_field/fsscCommonTransferField.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/common/fssc_common_transfer_field/fsscCommonTransferField.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/common/fssc_common_transfer_field/fsscCommonTransferField.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdSourceModelSubject;fdTargetModelSubject;" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        </ui:content>
        </ui:tabpanel>
        </template:replace>
        <template:replace name="script">
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'transfer_field',
                modelName: 'com.landray.kmss.fssc.common.model.FsscCommonTransferField',
                templateName: '',
                basePath: '/fssc/common/fssc_common_transfer_field/fsscCommonTransferField.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-common:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
            seajs.use(['lui/framework/module','lui/dialog','lui/topic'],function(Module,dialog,topic){
				Module.install('fsscCommon',{
					//模块变量
					$var : {},
					//模块多语言
					$lang : {
					},
					//搜索标识符
					$search : ''
				});
            });
        </script>
        <script type="text/javascript" src="${LUI_ContextPath}/fssc/common/resource/js/index.js"></script>
    </template:replace>
</template:include>