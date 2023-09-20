<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.edit" sidebar="no">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/iconfont/iconfont.css"/>
        <%--		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/normalize.css" />--%>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/mobile/resources/css/swiper.css?s_cache=${LUI_Cache}"/>
        <%--		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/appDesign.css" />--%>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
    </template:replace>
    <template:replace name="content">
        <kmss:windowTitle moduleKey="sys-modeling-base:module.model.name" subject="${appTitle }"/>
        <div class="app_index lui_modeling_index" id="appIndex">
            <!-- 页眉 starts -->
            <div class="model-header clearfix">
                <div class="model-header-left">
                    <!-- 当前应用标题信息 -->
                    <div class="model-header-origin" onclick="backToForm();" style="cursor: pointer;"><i
                            class="iconfont_nav ${appIcon}"></i>${appTitle }</div>
                </div>
                <div class="model-header-right" style="cursor: pointer">
                    <div class="model-header-operation" onclick="goToHelp()">${lfn:message('sys-modeling-base:sys.profile.modeling.operationGuide')}</div>
                </div>
            </div>
            <!-- 侧栏 starts -->
            <div class="model-sidebar app_index_sidebar">
                <div class="model-sidebar-opt">
                        <%--					<div class="model-sidebar-back"><i></i>返回应用</div>--%>
                    <i class="icon iconfont_modeling lui_iconfont_modeling_fold model-sidebar-btn"></i>

                </div>

                <ul class="model-sidebar-wrap">
                    <div data-lui-type="lui/base!DataView" style="display:none;">
                        <ui:source type="Static">
                            [{
                            "text" : "${lfn:message('sys-modeling-base:kmReviewDocumentLableName.baseInfo')}",
                            "href" : "/baseinfo",
                            "icon": "lui_iconfont_modeling_information"
                            },{
                            "text" : "${lfn:message('sys-modeling-base:modeling.business.form')}",
                            "href" : "/form",
                            "icon": "lui_iconfont_modeling_form "
                            }<%--,{
                            "text" : "${lfn:message('sys-modeling-base:table.modelingAppNav')}",
                            "href" : "/menu",
                            "icon": "lui_iconfont_modeling_navigation "
                            },{
                            "text" : "${lfn:message('sys-modeling-base:table.modelingAppMobile')}",
                            "href" : "/mobile",
                            "icon": "lui_iconfont_modeling_mobile"
                            }--%>
                            ,{
                            "text" : "${lfn:message('sys-modeling-base:modelingAppSpace.table.space')}",
                            "href" : "/space",
                            "icon": "lui_iconfont_modeling_navigation "
                            }
                            <kmss:ifModuleExist path="/dbcenter/echarts/">
                                ,{
                                "text" : "${lfn:message('sys-modeling-base:modeling.app.setting.rpt')}",
                                "href" : "/rpt",
                                "icon": "lui_iconfont_modeling_chart"
                                }
                            </kmss:ifModuleExist>
                            ]
                        </ui:source>
                        <ui:render type="Javascript">
                            <c:import url="/sys/modeling/base/resources/js/appDesign/appNavRender.js"
                                      charEncoding="UTF-8"></c:import>
                        </ui:render>

                    </div>
                </ul>
            </div>
            <!-- 侧栏 ends -->
            <!-- 右侧主内容 -->
            <div class="model-body-iframe ">
                <div class="model-body-wrap-iframe">
                    <div data-lui-type="sys/modeling/base/resources/js/appDesign/moduleMain!ModuleMain"
                         style="display:none;height:100%;" id="moduleMain"></div>
                </div>

            </div>
        </div>
        <!-- 后面有时间完善 -->
        <div class="form_frame">
            <div data-lui-type="sys/modeling/base/resources/js/appDesign/moduleMain!ModuleMain"
                 style="display:none;height:100%;" id="formIFrame"></div>
        </div>
        <script type="text/javascript">
            window.backToForm = function () {
                //Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/base/form/form.jsp?fdId=${appId}","_self");
                Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=appIndex&fdId=${appId}", "_self");
            }
            window.goToHelp = function () {
                //Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/base/form/form.jsp?fdId=${appId}","_self");
                Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/help/docs/index.jsp#/pages/page-1/start", "_blank");
            }
            $(document).ready(function () {
                $('.model-sidebar-btn').on('click', function () {
                    $('.model-sidebar').toggleClass('shrink')
                    $('.model-body-iframe').toggleClass('spread')
                })
            });
            seajs.use(['lui/framework/module', 'lui/util/env'], function (Module, env) {

                window.openPage = function (url, params) {
                    LUI("moduleMain").get({url: url});
                };

                Module.install('modelingApp', {
                    //模块变量
                    $var: {
                        "appId": "${appId}"
                    },
                    //模块多语言
                    $lang: {},
                    //搜索标识符
                    $search: ''
                });
            });
        </script>
        <script type="text/javascript"
                src="${LUI_ContextPath}/sys/modeling/base/resources/js/appDesign/router.js"></script>
    </template:replace>
</template:include>
