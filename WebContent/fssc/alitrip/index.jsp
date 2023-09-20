<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.list"  spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-alitrip:module.fssc.alitrip') }-${ lfn:message('fssc-alitrip:table.fsscAlitripOrder') }" />
    </template:replace>
    <template:replace name="nav">
        <ui:combin ref="menu.nav.title">
            <ui:varParam name="title" value="${ lfn:message('fssc-alitrip:module.fssc.alitrip') }"></ui:varParam>
            <ui:varParam name="operation">
                <ui:source type="Static">

                </ui:source>
            </ui:varParam>
        </ui:combin>
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>
                <ui:content title="${ lfn:message('list.search') }">
                    <ui:combin ref="menu.nav.simple">
                        <ui:varParam name="source">
                            <ui:source type="Static">
                                [{
                                "text" : "${ lfn:message('fssc-alitrip:table.fsscAlitripOrder') }",
                                "href" :  "/listOrder",
                                "router" : true,
                                "icon" : "lui_iconfont_navleft_com_query"
                                },
                                {
                                "text" : "${ lfn:message('fssc-alitrip:table.fsscAlitripLog') }",
                                "href" :  "/listLog",
                                "router" : true,
                                "icon" : "lui_iconfont_navleft_com_query"
                                }]
                            </ui:source>
                        </ui:varParam>
                    </ui:combin>
                </ui:content>
                <ui:content title="${ lfn:message('list.otherOpt') }" expand="true">
                    <ui:combin ref="menu.nav.simple">
                        <ui:varParam name="source">
                            <ui:source type="Static">
                                [{
                                "text" : "${ lfn:message('list.manager') }",
                                "href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/sys/profile/moduleindex.jsp?nav=/fssc/alitrip/tree.jsp','_rIframe');",
                                "icon" : "lui_iconfont_navleft_com_background"
                                }]
                            </ui:source>
                        </ui:varParam>
                    </ui:combin>
                </ui:content>
            </ui:accordionpanel>
        </div>
    </template:replace>
    <template:replace name="content">
        <ui:tabpanel id="fsscalitripPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
            <ui:content id="fsscalitripContent" title="" cfg-route="{path:'/listOrder'}">
                <!-- 筛选 -->
                <list:criteria id="criteria1">
                    <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="fdType" />
                    <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="corpName" />
                    <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="userName" />
                    <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="departName" />
                    <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="city" />
                    <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="costCenterName" />
                    <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="costCenterNumber" />
                    <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="orderStatus" />
                    <list:cri-auto modelName="com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder" property="orderType" />

                </list:criteria>
                <!-- 操作 -->
                <div class="lui_list_operation">

                    <div style='color: #979797;float: left;padding-top:1px;'>
                            ${ lfn:message('list.orderType') }：
                    </div>
                    <div style="float:left">
                        <div style="display: inline-block;vertical-align: middle;">
                            <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                                <list:sort property="fsscAlitripOrder.fdType" text="${lfn:message('fssc-alitrip:fsscAlitripOrder.fdType')}" group="sort.list" />
                                <list:sort property="fsscAlitripOrder.corpName" text="${lfn:message('fssc-alitrip:fsscAlitripOrder.corpName')}" group="sort.list" />
                                <list:sort property="fsscAlitripOrder.userName" text="${lfn:message('fssc-alitrip:fsscAlitripOrder.userName')}" group="sort.list" />
                            </ui:toolbar>
                        </div>
                    </div>
                    <div style="float:left;">
                        <list:paging layout="sys.ui.paging.top" />
                    </div>
                    <div style="float:right">
                        <div style="display: inline-block;vertical-align: middle;">
                            <ui:toolbar count="3">

                                <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do?method=add">
                                    <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                                </kmss:auth>
                                <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do?method=deleteall">
                                    <c:set var="canDelete" value="true" />
                                </kmss:auth>
                                <!---->
                                <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                                <kmss:auth requestURL="/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder">
                                    <ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.fssc.alitrip.model.com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder')">
                                    </ui:button>
                                </kmss:auth>
                            </ui:toolbar>
                        </div>
                    </div>
                </div>
                <ui:fixed elem=".lui_list_operation" />
                <!-- 列表 -->
                <list:listview id="listview">
                    <ui:source type="AjaxJson">
                        {url:appendQueryParameter('/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do?method=data')}
                    </ui:source>
                    <!-- 列表视图 -->
                    <list:colTable isDefault="false" rowHref="/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do?method=view&fdId=!{fdId}" name="columntable">
                        <list:col-checkbox />
                        <list:col-serial/>
                        <list:col-auto props="" url="/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.fssc.alitrip.model.fsscAlitripOrder" /></list:colTable>
                </list:listview>
                <!-- 翻页 -->
                <list:paging />
            </ui:content>
        </ui:tabpanel>
    </template:replace>
    <template:replace name="script">
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'order',
                modelName: 'com.landray.kmss.fssc.alitrip.model.FsscAlitripOrder',
                templateName: '',
                basePath: '/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-alitrip:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            seajs.use(['lui/framework/module'],function(Module){
                Module.install('fsscAlitrip',{
                    //模块变量
                    $var : {

                    },
                    //模块多语言
                    $lang : {

                    },
                    //搜索标识符
                    $search : ''
                });
            });
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/alitrip/resource/js/", 'js', true);
        </script>
        <!-- 引入JS -->
        <script type="text/javascript" src="${LUI_ContextPath}/fssc/alitrip/resource/js/index.js"></script>
    </template:replace>
</template:include>
