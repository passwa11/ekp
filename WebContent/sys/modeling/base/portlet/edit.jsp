<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
    pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
    pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
    pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
    if (UserUtil.getUser().getFdParentOrg() != null) {
        pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
    } else {
        pageContext.setAttribute("currentOrg", "");
    }

%>

<template:include ref="default.dialog">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
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
        <style>
            .inputselectsgl .selectitem::before {
                background: none
            }

            .lui_custom_list_boxs {
                border-top: 1px solid #d5d5d5;
                position: fixed;
                bottom: 0;
                width: 100%;
                background-color: #fff;
                z-index: 1000;
                height: 63px;
            }

            .lui_form_content {
                padding: 0;
            }

            body {
                background-color: #fff !important;
            }

            .portlet-data-mapping > td,
            .portlet-operation-mapping > td {
                padding: 0 !important;
                padding-left: 10px !important;
                padding-bottom: 20px !important;
            }

            .portlet-data-mapping,
            .portlet-operation-mapping {
                line-height: 40px
            }

            .table_relation {
                margin: 0;
                width: 100%;
            }

            .table_relation tr {
                line-height: 40px
            }

            .table_relation tr td {
                padding: 0 !important;
                padding-left: 10px !important;
            }

            /*.table_relation tr:first-child td,.vieSetTable tr:first-child td{*/
            /*    color: #354052;*/
            /*    padding: 0;*/
            /*    padding-left: 10px;*/
            /*    vertical-align: inherit;*/
            /*    text-align: left;*/
            /*}*/
            /*.portlet-data-mapping>td:last-child,.portlet-operation-mapping>td:last-child{*/
            /*	padding-left: 20px!important;*/
            /*	padding-bottom: 30px!important;*/
            /*}*/
            .model_view_info {
                position: relative;
                overflow: hidden;
                padding-bottom: 35px;
            }

            .model_view_base_info:before {
                content: '';
                width: 1px;
                height: 100%;
                background: #4285f4;
                position: absolute;
                top: 0;
                left: 3px;
                z-index: 1;
                top: 4px;
                margin-left: 15px;
            }

            .model_view_info > i {
                width: 8px;
                height: 8px;
                border-radius: 50%;
                background: #4285f4;
                float: left;
                margin-top: 4px;
                margin-right: 10px;
                margin-left: 15px;
            }

            .model_view_info > i:before {
                content: '';
                height: 4px;
                width: 1px;
                background: #4285f4;
                position: absolute;
                top: -4px;
                left: 3px;
                z-index: 2;
            }

            .model_view_info > p {
                font-size: 14px;
                color: #333333;
                margin-bottom: 20px;
                margin-top: 0;
            }
            .model-mask-panel-table .model-table-items {
                position: relative;

            }
            .validateInfo-flag{
                position: absolute;
                top: 12px;
                right: -2px;
                color: red;
            }
            .validateInfo-flag1{
                margin-left: -5px;
                color: red;
            }
            .inputsgl{
                white-space: nowrap;
                text-overflow: ellipsis;
                overflow: hidden;
            }
        </style>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/modeling/base/modelingPortletCfg.do" style="margin-bottom:63px">
            <div class="model-mask-panel medium" style="padding-bottom: 72px">
                <div>
                    <div class="model-mask-panel-table">
                        <ul id="editContainer">
                            <li class="model-table-items">
                                <div class="model-table-left">
                                    <i></i>
                                    <%--<p>基本信息</p>--%>
                                    <p>${lfn:message('sys-modeling-base:respanel.baseinfo')}</p>
                                </div>
                                <div class="model-table-right">
                                    <table class="tb_simple modeling_form_table " id="portletBaseContainer">
                                        <tbody>
                                        <tr>
                                            <td class="td_normal_title" width="112px">
                                                <%--名称--%>
                                                ${lfn:message('sys-modeling-base:modeling.import.name')}
                                            </td>
                                            <td width="620px">
                                                <div id="_xform_fdName" _xform_type="text" title="${modelingPortletCfgForm.fdName}">
                                                    <xform:text property="fdName" showStatus="edit" validators="maxLength(36)" style="width:95%;" required="true" />
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="td_normal_title" width="15%">
                                                <%--所属模块--%>
                                                ${lfn:message('sys-modeling-base:modelingPortletCfg.fdModuleName')}
                                            </td>
                                            <td width="85%" class="model-view-panel-table-td">
                                                    <%-- 所属应用--%>
                                                <div id="_xform_fdModuleName" _xform_type="text">
                                                    <xform:text property="fdModuleName" showStatus="readOnly"
                                                                style="width:95%;color:#CCCCCC"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr modeling-validation="fdFormat;  ${lfn:message('sys-modeling-base:modelingPortletCfg.fdFormat')};required;name:fdFormat;id:_xform_fdFormat">
                                            <td class="td_normal_title" width="15%">
                                                <%--展现方式--%>
                                                ${lfn:message('sys-modeling-base:modelingPortletCfg.fdFormat')}
                                            </td>
                                            <td width="85%" class="model-view-panel-table-td" id="_xform_fdFormat" style="position: relative">
                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdFormat" type="hidden" name="fdFormat"
                                                           value="<c:out value='${modelingPortletCfgForm.fdFormat}' />">
                                                </div>
                                                <div mdlng-rltn-prprty-value="fdFormat" mdlng-rltn-prprty-type="dialog"
                                                     data-select-btn="fdFormat"
                                                     class="model-mask-panel-table-show selectitem">
                                                    <p mdlng-rltn-data="fdFormatText"></p>

                                                </div>
                                                <span class="validateInfo-flag">*</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="td_normal_title" width="15%">
                                                <%--描述--%>
                                                ${lfn:message('sys-modeling-base:modelingBusiness.fdDesc')}
                                            </td>
                                            <td width="85%" class="model-view-panel-table-td">
                                                    <%-- 所属应用--%>
                                                <div id="_xform_fdDescription" _xform_type="text">
                                                    <xform:textarea property="fdDescription" validators="maxLength(200)" showStatus="edit" style="width:95%;"/>
                                                </div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <html:hidden property="fdId"/>
                                    <html:hidden property="method_GET"/>
                                    <input type="hidden" name="fdFormatMapping"
                                           value="<c:out value='${modelingPortletCfgForm.fdFormatMapping}' />"/>
                                    <input type="hidden" name="fdVarMapping"
                                           value="<c:out value='${modelingPortletCfgForm.fdVarMapping}' />"/>
                                    <input type="hidden" name="fdOperationMapping"
                                           value="<c:out value='${modelingPortletCfgForm.fdOperationMapping}' />"/>
                                    <input type="hidden" name="fdOutSort"
                                           value="<c:out value='${modelingPortletCfgForm.fdOutSort}' />"/>
                                </div>
                            </li>
                            <li class="model-table-items" mdlng-rltn-mrk="region" mdlng-rltn-data="basic">
                                <div class="model-table-left">
                                    <i></i>
                                    <%--<p>配置信息</p>--%>
                                    <p>${lfn:message('sys-modeling-base:modeling.dataValidate.fdCfg')}</p>
                                </div>
                                <div class="model-table-right">
                                    <table class="tb_simple modeling_form_table " id="portletSetContainer">
                                        <tbody>
                                        <tr class="forSystem td_normal_title" style="display: none" >
                                            <td width="15%">所属系统</td>
                                            <td>
                                                <div style="width: 100px" >
                                                    <input type="radio" name="fdForSystem" value="ekp"  >EKP
                                                    <input type="radio" name="fdForSystem" value="cloud" >MK
                                            </div>
                                            </td>
                                        </tr>
                                        <tr data-portlet-area="dataMapping" style="display: none">
                                            <td class="td_normal_title" width="15%"><%--数据映射--%> ${lfn:message('sys-modeling-base:modelingPortletCfg.fdFormatMapping')}</td>
                                            <td class="td_normal_content model-view-panel-table-td" width="85%">
                                                <div class="model-mask-panel-table-base">
                                                    <table class="tb_normal model-panel-child-table"
                                                           data-portlet-area="dataMapping-val-table" width="100%">
                                                        <tr class="tr_normal_title">
                                                            <td class="td_normal_title head" width="15%"><%--展示字段 --%>${lfn:message('sys-modeling-base:respanel.display.field')}</td>
                                                            <td class="td_normal_title head" width="85%"><%--映射字段 --%>${lfn:message('sys-modeling-base:respanel.mapping.field')}</td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                        </ul>

                    </div>
                </div>

                <div class="toolbar-bottom">
                    <ui:button styleClass="lui_toolbar_btn_gray" text="${ lfn:message('button.close') }" order="5"
                               onclick="Com_CloseWindow();"/>
                    <c:choose>
                        <c:when test="${ modelingPortletCfgForm.method_GET == 'updata' }">
                            <ui:button text="${ lfn:message('button.update') }"
                                       onclick="dosubmit('update')"/>
                        </c:when>
                        <c:when test="${ modelingPortletCfgForm.method_GET == 'edit' }">
                            <ui:button text="${ lfn:message('button.update') }"
                                       onclick="dosubmit('update')"/>
                        </c:when>
                        <c:when test="${ modelingPortletCfgForm.method_GET == 'add' }">
                            <ui:button text="${ lfn:message('button.save') }"
                                       onclick="dosubmit('save')"/>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </html:form>
        <script type="text/javascript">
            var land ={
                required:{
                    text: "${lfn:message('sys-modeling-base:kmReviewMain.notNull')}"
                }
            };

            var validation = $KMSSValidation();
            Com_IncludeFile("localValidate.js", Com_Parameter.ContextPath
                + 'sys/modeling/base/resources/js/', 'js', true);
            seajs.use(["lui/dialog", "sys/modeling/base/portlet/js/portletcfg"
                , "sys/modeling/base/formlog/res/mark/portletFMMark","sys/modeling/base/relation/res/js/sortRecordGenerator"],
                function (dialog, portletcfg, portletFMMark,sortRecordGenerator) {
                function init() {
                    var cfg = {
                        "baseContainer": $("#portletBaseContainer"),
                        "settingContainer": $("#portletSetContainer"),
                        "modelId": "${modelingPortletCfgForm.modelMainId}",
                        "xformId": "${xformId}",
                        "form": getFormData(),
                        "fdDevice": '${modelingPortletCfgForm.method_GET == 'edit'?modelingPortletCfgForm.fdDevice:param.fdDevice}'
                    };
                    window.pCfgInst = new portletcfg.PortletCfg(cfg);
                    pCfgInst.startup();
                    pCfgInst.initByStoreData(getFormData());
                    window.fmmark = new portletFMMark.PortletFMMark({fdId: "${modelingPortletCfgForm.fdId}"});
                    fmmark.startup();
                }

                function getFormData() {
                    var fdFormatMapping = getFormDataJson('${modelingPortletCfgForm.fdFormatMapping}');
                    var fdOperationMapping = getFormDataJson('${modelingPortletCfgForm.fdOperationMapping}');
                    var fdVarMapping = getFormDataJson('${modelingPortletCfgForm.fdVarMapping}');
                    return {
                        "fdFormat": "${modelingPortletCfgForm.fdFormat}",
                        "fdFormatMapping": fdFormatMapping,
                        "fdOperationMapping": fdOperationMapping,
                        "fdVarMapping": fdVarMapping,
                        "fdForSystem": "${modelingPortletCfgForm.fdForSystem}"
                    }
                }

                function getFormDataJson(data) {
                    if (data === "") {
                        return {};
                    } else {
                        return JSON.parse(data);
                    }
                }

                function localValidate() {
                    var lv = _localValidation();
                    var moreOper = $(".portlet-operation-mapping").find("p[modeling-mark-data]").html();;
                    var showtype = $(".model-mask-panel-table-show p").html();
                    if (showtype!=""){
                    if (typeof(moreOper)=="undefined" || moreOper==""){
                        dialog.alert("${lfn:message('sys-modeling-base:more.links.not.configured')}");
                        return false;
                    }
                    }
                    if (validation.validate() && lv ) {
                        return true;
                    }
                    return false;
                }
                window.dosubmit = function (type) {
                    var mapping = window.pCfgInst.getKeyData();
                  /*  var sortField = window.fdOutSortEle.getKeyData();*/
                    if (mapping) {
                        $("[name='fdVarMapping']").val(mapping.varMapping)
                        $("[name='fdOperationMapping']").val(mapping.operationMapping)
                        $("[name='fdFormatMapping']").val(mapping.dataMapping)
                    }
                    if (localValidate()) {
                        Com_Submit(document.modelingPortletCfgForm, type);
                    }
                };
                init();
            });

        </script>
    </template:replace>
</template:include>