<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.edit" sidebar="no">
    <template:replace name="head">
        <%--		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/form/css/frame.css" />--%>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/iconfont/iconfont.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/normalize.css"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
    </template:replace>
    <template:replace name="content">
        <kmss:windowTitle moduleKey="sys-modeling-base:module.model.name"
                          subjectKey="sys-modeling-base:module.form.design" subject="${name}"/>
        <div class="app_index lui_modeling_index ">
            <!-- 页眉 starts -->
            <div class="model-header clearfix">
                <div class="model-header-left">
                    <!-- 当前应用标题信息 -->
                    <div class="model-header-origin" onclick="backToForm();" style="cursor: pointer;"><i
                            class="iconfont_nav ${appIcon}"></i>${appName }</div>
                    <div class="model-header-nav">
                        <span class="model_select_selected">${name }</span>
                        <div class="model_select_box">
                            <ul>
                                <c:forEach items="${modelList}" var="modelMap" varStatus="vstatus">
                                    <li data-model-fdid="${modelMap[0]}" title="${modelMap[1]}"
                                        onclick="switchModel('${modelMap[0]}')">${modelMap[1]}</li>
                                </c:forEach>
                            </ul>

                        </div>
                    </div>
                </div>
                <div class="model-header-right" style="cursor: pointer">
                    <div class="model-header-operation"
                         onclick="goToHelp()">${lfn:message('sys-modeling-base:modeling.form.OperationGuide')}</div>
                </div>
            </div>
            <!-- 侧栏 starts -->
            <div class="model-sidebar-box">
                <div class="model-sidebar-mask"></div>
                <div class="model-sidebar">
                        <%--                <div class="model-sidebar-btn"></div>--%>
                    <div class="model-sidebar-opt">
                        <div class="model-sidebar-back" onclick="backToForm();"><i
                                class="icon iconfont_modeling lui_iconfont_modeling_return"></i>${lfn:message('sys-modeling-base:modeling.form.ReturnApplication')}
                        </div>
                        <i class="icon iconfont_modeling lui_iconfont_modeling_fold model-sidebar-btn
"></i>
                    </div>
                        <%-- @formatter:off--%>
                <ui:dataview>
                    <ui:source type="Static">
                        [{
                            "text": "${lfn:message('sys-modeling-base:modeling.model.frame.form')}",
                            "href": "#expansion",
                            "icon": "lui_iconfont_modeling_design",
                            "children" : [{
                            		"text": "${lfn:message('sys-modeling-base:modeling.form.FoundationDesign')}",
                                    "href": "/form_base_design",
                                    "icon": ""
                            },{
                            		"text": "${lfn:message('sys-modeling-base:modeling.form.FormConfiguration')}",
                                    "href": "/form_base_cfg",
                                    "icon": ""
                            }],
                            "key" : "form"
                        },
						{
                            "text": "${lfn:message('sys-modeling-base:modeling.form.MechanismDeployment')}",
                            "href": "/mechanism",
                            "icon": "lui_iconfont_modeling_mechanism"
                        },
                        {
                            "text": "${lfn:message('sys-modeling-base:modeling.model.frame.relation')}",
                            "href": "#expansion",
                            "icon": "lui_iconfont_modeling_relationship",
                            "children": [{
                                    "text": "${lfn:message('sys-modeling-base:modeling.form.RelationshipSet')}",
                                    "href": "/relation_relation",
                                    "icon": ""
                                }, {
                                    "text": "${lfn:message('sys-modeling-base:modeling.form.BusinessTriggered')}",
                                    "href": "/relation_trigger",
                                    "icon": ""
                                }, {
                                    "text": "${lfn:message('sys-modeling-base:modeling.form.BusinessOperations')}",
                                    "href": "/relation_operation",
                                    "icon": ""
                                }]
                        },
                        {
                            "text": "${lfn:message('sys-modeling-base:modeling.model.frame.flow')}",
                            "href": "/lbpm",
                            "show": "${enableFlow}",
                            "icon": "lui_iconfont_modeling_process"
                        },
                        {
                            "text": "${lfn:message('sys-modeling-base:modeling.model.frame.view')}",
                            "href": "#expansion",
                            "icon": "lui_iconfont_modeling_view",
                            "children": [{
                                    "text": "${lfn:message('sys-modeling-base:modeling.form.ListView')}",
                                    "href": "/listview_main",
                                    "icon": ""
                                }, {
                                    "text": "${lfn:message('sys-modeling-base:modeling.form.LookAtView')}",
                                    "href": "/view_main",
                                    "icon": ""
                                }, {
                                    "text": "${lfn:message('sys-modeling-base:modeling.form.BusinessScenario')}",
                                    "href": "/views_business",
                                    "icon": ""
                                },
                                {
                                "text": "${lfn:message('sys-modeling-base:modeling.form.TreeView')}",
                                "href": "/treeView_main",
                                "icon": ""
                                },
                                {
                                    "text": "${lfn:message('sys-modeling-base:modeling.form.PortletView')}",
                                    "href": "/portletView_main",
                                    "icon": ""
                              }]
                        },
                        {
                            "text": "${lfn:message('sys-modeling-base:modeling.model.frame.right')}",
                            "href": "#expansion",
                            "icon": "lui_iconfont_modeling_permission",
                            "children": [{
                                    "text": "${lfn:message('sys-modeling-base:modeling.form.FormPermissions')}",
                                    "href": "/right_form",
                                    "icon": ""
                                }, {
                                    "text": "${lfn:message('sys-modeling-base:modeling.form.OperationPermissions')}",
                                    "href": "/right_opr",
                                    "icon": ""
                                }
                                <c:if test="${enableFlow == true }">
                                	, {
		                                    "text": "${lfn:message('sys-modeling-base:modeling.form.FlowPermissions')}",
		                                    "href": "/right_flow",
		                                    "icon": ""
		                                }
                                </c:if>
                                , {
                                    "text": "${lfn:message('sys-modeling-base:modeling.form.DefaultPermissions')}",
                                    "href": "/right_default",
                                    "icon": ""
                                }
                                ]
                        }]
                    </ui:source>
                    <ui:render type="Javascript">
                        <c:import url="/sys/modeling/base/form/js/frameRender.js" charEncoding="UTF-8"></c:import>
                    </ui:render>
                </ui:dataview>
                    <%-- @formatter:on--%>
                </div>
            </div>

            <!-- 侧栏 ends -->

            <!-- content -->
            <div class="model-body-iframe">
                <div class="model-body-wrap-iframe 2222">
                    <div data-lui-type="sys/modeling/base/resources/js/appDesign/moduleMain!ModuleMain"
                         style="display:none;height:100%;" id="iframeMain"></div>

                </div>
            </div>
        </div>

        <script type="text/javascript">
            $(document).ready(function () {
                $('.model-sidebar-btn').on('click', function () {
                    $('.model-sidebar').toggleClass('shrink')
                    $('.model-body-iframe').toggleClass('spread');
                    $('.model-sidebar-box').toggleClass('change');
                })
            });
            seajs.use(['lui/framework/module', 'lui/util/env'], function (Module, env) {

                window.backToForm = function () {
                    //Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/base/form/form.jsp?fdId=${appId}","_self");
                    Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=appIndex&fdId=${appId}", "_self");
                }
                window.goToHelp = function () {
                    //Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/base/form/form.jsp?fdId=${appId}","_self");
                    Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/help/docs/index.jsp#/pages/page-1/start", "_blank");
                }
                window.switchModel = function (id) {
                    Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=frame&fdId=" + id, "_self");
                }

                window.openPage = function (url, params) {
                    LUI("iframeMain").get({url: url});
                }

                Module.install('appform', {
                    //模块变量
                    $var: {
                        "id": "${id}",
                        "enableFlow": "${enableFlow}",
                        "appName": encodeURIComponent("${appName}"),
                        "appIcon": "${appIcon}"
                    },
                    //模块多语言
                    $lang: {},
                    //搜索标识符
                    $search: ''
                });
            });
        </script>
        <script type="text/javascript" src="${LUI_ContextPath}/sys/modeling/base/form/router.js"></script>
    </template:replace>
</template:include>