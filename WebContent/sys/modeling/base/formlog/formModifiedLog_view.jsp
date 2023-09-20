<%@ page import="com.landray.kmss.sys.xform.base.service.spring.SysFormTemplateControlUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.edit">
    <template:replace name="title">
        ${lfn:message('sys-modeling-base:table.modelingFormModifiedLog') }
    </template:replace>
    <template:replace name="head">
        <script>
            <%@ include file="/sys/xform/designer/lang.jsp" %>
        </script>
        <script>
            Com_IncludeFile("jquery.js");
        </script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/dtree/dtree.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/builder.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/panel.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/control.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/dash.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/mobile/js/config_mobile.js""></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/mobile/js/control_ext.js""></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/config_ext.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/config.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/attachment.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/jspcontrol.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/buttons.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/toolbar.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/effect.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/treepanel.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/attrpanel.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/shortcuts.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/cache.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/rightmenu.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/relation/relation.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/hidden.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/right.js"></script>
        <link href="<%=request.getContextPath() %>/sys/xform/designer/mobile/css/mobileDesigner.css" type="text/css" rel="stylesheet" />

        <%
            pageContext.setAttribute("jsFiles", SysFormTemplateControlUtils.getAllControlJsFiles("com.landray.kmss.sys.modeling.base.model.ModelingAppModel"));
        %>
        <c:forEach items="${jsFiles}" var="jsFile">
            <script type="text/javascript" src="<c:url value="${jsFile}" />"></script>
        </c:forEach>
        <%
            // 单独的js嵌入
            pageContext.setAttribute("jsFiles", SysFormTemplateControlUtils.getDesignJsFiltes());
        %>
        <c:forEach items="${jsFiles}" var="jsFile">
            <script type="text/javascript" src="<c:url value="${jsFile}" />"></script>
        </c:forEach>
        <script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/designer.js"></script>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
            <ui:button text="${ lfn:message('button.close') }" onclick="window.close();">
            </ui:button>
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
    </template:replace>
    <template:replace name="content">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/mobile/resources/css/swiper.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>

        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/formlog.css?s_cache=${LUI_Cache}"/>
        <style>
            body, .lui_form_body .tempTB {
                background: none !important;;
            }

        </style>
        <script type="text/javascript">
            var formInitData = {};
            var messageInfo = {};

            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("jquery.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("plugin.js");
            Com_IncludeFile("validation.js");
            Com_IncludeFile("view.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
            Com_IncludeFile("dialog.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
        </script>

        <html:form action="/sys/modeling/base/modelingFormModified.do" style="margin-bottom:63px">
            <div class="model">
                <!-- 内容区 starts -->
                <div class="model-change">
                    <div class="model-change-wrap">
                            <%--                        <div class="model-change-nav">--%>
                            <%--                            <div class="model-change-nav-item">修改日志</div>--%>
                            <%--                            <div class="model-change-nav-item">修改详情</div>--%>
                            <%--                        </div>--%>

                        <div class="model-change-content">
                            <c:forEach items="${modelingFormModifiedLogForm.listField_Form}" var="listField_Form"
                                       varStatus="vstatus">
                                <div class="model-change-content-item" mapping-log-field="${listField_Form.fdFieldId}">
                                    <ul class="model-change-list">
                                        <li class="model-change-list-line">
                                        <%--修改控件 --%>
                                            <p>${lfn:message('sys-modeling-base:modeling.formlog.UpdateControls')}</p>
                                            <p class="fieldLabel">
                                                <c:choose>
                                                <c:when test="${empty listField_Form.fdFieldLabel && empty listField_Form.fdOrgFieldLabel}">
                                                ${lfn:message("sys-modeling-base:modeling.formlog.control.no.name")}
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${'2'.equals(listField_Form.fdModifiedType)}">
                                                        ${listField_Form.fdOrgFieldLabel}
                                                    </c:if>
                                                    <c:if test="${'1'.equals(listField_Form.fdModifiedType)}">
                                                        ${listField_Form.fdOrgFieldLabel}
                                                    </c:if>
                                                    <c:if test="${'0'.equals(listField_Form.fdModifiedType)}">
                                                        ${listField_Form.fdFieldLabel}
                                                    </c:if>

                                            </c:otherwise>
                                            </c:choose>
                                            <c:if test="${not empty listField_Form.fdFieldId}">
                                                (${listField_Form.fdFieldId})
                                            </c:if>
                                            <span class="fieldType"></span>
                                            </p>
                                        </li>
                                        <li class="model-change-list-line" mapping-log-mark="fdChangeLog">
                                            <p>${lfn:message('sys-modeling-base:modeling.formlog.UpdateContent')}</p>
                                            <input class="model-change-list-desc-hidden"
                                                   name="fdChanglog_${listField_Form.fdFieldId}" type="hidden"
                                                   value="<c:out value='${listField_Form.fdChangeLog}'/>"/>
                                            <input class="model-change-list-desc-hidden"
                                                   name="fdModifiedType_${listField_Form.fdFieldId}" type="hidden"
                                                   value="<c:out value='${listField_Form.fdModifiedType}'/>"/>
                                            <c:if test="${'2'.equals(listField_Form.fdModifiedType)}">
                                                <input
                                                        name="fdBussinessType" type="hidden"
                                                        value="<c:out value='${empty listField_Form.fdOrgBusinessType ? listField_Form.fdOrgFieldType:listField_Form.fdOrgBusinessType}'/>"/>
                                                <p>${lfn:message('sys-modeling-base:modeling.formlog.ControlDeleted')}</p>
                                            </c:if>
                                            <c:if test="${'0'.equals(listField_Form.fdModifiedType)}">
                                                <input
                                                        name="fdBussinessType" type="hidden"
                                                        value="<c:out value='${empty listField_Form.fdBusinessType?listField_Form.fdFieldType:listField_Form.fdBusinessType}'/>"/>
                                                <p>${lfn:message('sys-modeling-base:modeling.formlog.AddControls')}</p>
                                            </c:if>
                                            <c:if test="${'1'.equals(listField_Form.fdModifiedType)}">
                                                <input
                                                        name="fdBussinessType" type="hidden"
                                                        value="<c:out value='${empty listField_Form.fdOrgBusinessType?listField_Form.fdOrgFieldType:listField_Form.fdOrgBusinessType}'/>"/>
                                            </c:if>

                                        </li>
                                        <li class="model-change-list-line"
                                            mapping-log-mapping="${listField_Form.fdFieldId}">
                                            <p>${lfn:message('sys-modeling-base:modeling.formlog.MappingRelationship')}</p>
                                            <div class="model-change-slide">
                                                <div class="model-change-slide-header">
                                                    <div class="model-change-slide-prev"></div>
                                                    <div class="model-change-slide-next"></div>
                                                    <div class="swiper-container swiper-container-${listField_Form.fdFieldId}">
                                                        <div class="swiper-wrapper">

                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="model-change-slide-content">
                                                    <div class="model-change-slide-content-wrap model-mask-panel-table-base">

                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <!-- 内容区 ends -->

            </div>

            <script src="${KMSS_Parameter_ContextPath}sys/modeling/base/formlog/res/swiper2.7.6.min.js"></script>
            <script>

            </script>
        </html:form>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.sys.modeling.base.formlog.form.ModelingFormModifiedLog',
                templateName: '',
                basePath: '/sys/modeling/base/modelingFormModified.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("sys-modeling-base:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            seajs.use(["lui/jquery", "sys/ui/js/dialog", "lui/topic", "sys/modeling/base/formlog/res/logRender"], function ($, dialog, topic, logRender) {
                topic.subscribe("list.loaded", function () {
                    var bodyHeight = $(document.body).outerHeight(true) + 70;
                    $("body", parent.document).find('#trigger_iframe').height(bodyHeight);
                });

                function init() {
                    var cfg = {
                        formlogId:"${param.fdId}",
                        fieldMapping: ${fieldMapping},
                        comClass:${comClass},
                        mySwiper:Swiper
                    };
                    window.logRenderInst = new logRender.LogRender(cfg);
                    logRenderInst.startup();
                    // var mySwiper = new Swiper('.swiper-container', {
                    //     calculateHeight: true,
                    //     // autoResize:false,
                    //     slidesPerView: 'auto',
                    //     // slidesPerGroup : 1,
                    //
                    // });
                    // $('.model-change-slide-next').on('click', function () {
                    //     mySwiper.swipeNext();
                    // })
                    // $('.model-change-slide-prev').on('click', function () {
                    //     mySwiper.swipePrev();
                    // })
                }

                init();


            });
        </script>
    </template:replace>
</template:include>