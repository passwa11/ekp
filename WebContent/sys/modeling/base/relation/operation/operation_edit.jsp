<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<%@page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil" %>
<%
    pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
    pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
    pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
    pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
    if (UserUtil.getUser().getFdParentOrg() != null) {
        pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
    } else {
        pageContext.setAttribute("currentOrg", "");
    }
%>

<template:include ref="default.dialog" sidebar="no">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>

        <style>
            body{
                background-color: #fff !important;
            }
            .model-mask-panel-table-com {
                width: 95%;
                margin-top: 20px;
                height: 30px;

                position: relative;
            }

            .model-mask-panel-table-com .table-com-title {
                display: inline-block;
                height: 30px;
                line-height: 30px;
                padding: 0 4px 0 16px;
                overflow: hidden;
                left: 0;
                width: 10%;
                text-align: left;
            }

            .model-mask-panel .model-mask-panel-table-select {
                display: block;
                border: 1px solid #DFE3E9;
                border-radius: 2px;
                height: 30px;
                cursor: pointer;
                position: relative;
                margin-left: 20px;
            }

            .model-mask-panel .model-mask-panel-table-select.active:before {
                border-bottom: 6px solid #999999;
                border-top: 0px;
                top: 40%;
            }

            .model-mask-panel .model-mask-panel-table-select:before {
                right: 12px;
            }

            .model-mask-panel .model-mask-panel-table-select p {
                float: left;
                height: 30px;
                line-height: 30px;
                font-size: 12px;
                color: #333333;
                margin: 0 4px;

            }

            .model-mask-panel-table-com .model-mask-panel-table-select {
                margin-left: 0;
                width: 85%;
                display: inline-block;
            }


            .model-mask-panel-table-select.active .model-mask-panel-table-option {
                display: block;
            }

            .model-mask-panel-table-select .model-mask-panel-table-option {
                display: none;
                position: absolute;
                top: 30px;
                width: 100%;
                background-color: #FFFFFF;
                z-index: 10;
                outline: 1px solid #DFE3E9;
                max-height: 160px;
                overflow-x: hidden;
                overflow-y: scroll;
            }

            .model-mask-panel-table-select .model-mask-panel-table-option div {
                height: 30px;
                width: 100%;
                line-height: 30px;
                font-size: 12px;
                color: #333333;
                padding-left: 4px;
            }

            .model-mask-panel-table-select .model-mask-panel-table-option div:hover {
                color: #4285F4;
            }
            .mainModelNameTip,.payMerchantTip,.payUserTip,.payMoneyTip,.payParamTip,.payViewLocationTip,.viewLocationTip{
                display:inline-block;
                margin: 5px 0 10px 14px;
                font-size: 12px;
                padding-right: 10px;
                padding-left: 16px;
                color:#FF9431;
                background: url(../base/resources/images/form-tip@2x.png) no-repeat left;
                background: url(../base/resources/images/form-tip.png) no-repeat \9;
                background-size: 12px 12px;
            }
            .viewModelNameTip{
                display:inline-block;
                margin: 5px 0 0 14px;
                font-size: 12px;
                padding-right: 10px;
                padding-left: 16px;
                color:#FF9431;
                background: url(../base/resources/images/form-tip@2x.png) no-repeat left;
                background: url(../base/resources/images/form-tip.png) no-repeat \9;
                background-size: 12px 12px;
                position: relative;
                left: 10%;
            }
            #_xform_fdName{
                margin-bottom:0px;
            }
            .view-model-txt-strong{
                position: relative;
                float: right;
                top: -24px;
                right: -10px;
            }

            .model-mask-panel .model-mask-panel-table-com .model-mask-panel-table-show {
                border: 1px solid #DFE3E9;
                border-radius: 2px;
                padding: 0;
                overflow: hidden;
                height: 30px;
                cursor: pointer;
                display: inline-block;
                width: 84%;
                margin-left: 0;
            }

            .txtstrong {
                font-weight: normal;
                display: inline-block;
                top:8px;
            }
            #_xform_fdViewLocation{
                margin-bottom: 0px;
            }
        </style>

        <script type="text/javascript">
            var formInitData = {};
            var messageInfo = {};

            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("jquery.js");
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("xform.js");
            Com_IncludeFile("xml.js");
            Com_IncludeFile("dialog.js");
            Com_IncludeFile("formula.js");

            Com_IncludeFile("plugin.js");
            Com_IncludeFile("validation.js");
        </script>
    </template:replace>
    <template:replace name="content">

        <html:form action="/sys/modeling/base/sysModelingOperation.do">
            <div class="model-mask-panel medium" style="padding-bottom: 72px" id="operationEditContainer">
                <div>
                    <div class="model-mask-panel-table">
                        <table class="tb_simple modeling_form_table operationMainForm" mdlng-prtn-mrk="regionTable">
                            <tbody>
                            <tr>
                                <td class="td_normal_title" width="15%" >
                                        ${lfn:message('sys-modeling-base:sysModelingOperation.fdName')}</td>
                                <td width="85%">
                                        <%-- 名称--%>
                                    <div id="_xform_fdName" _xform_type="text">
                                            <%-- 规避表单隐式提交--%>
                                        <input type="text" style="display:none" />
                                        <xform:text property="fdName" showStatus="edit" required="true"  style="width:95%;"/>
                                    </div>
                                    <div class="mainModelNameTip" style="display: none">${lfn:message('sys-modeling-base:operation.operation.name.cannot.6.characters')}</div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                        ${lfn:message('sys-modeling-base:sysModelingOperation.fdDesc')}</td>
                                <td colspan="3" width="85.0%">
                                        <%-- 描述--%>
                                    <div id="_xform_fdDesc" _xform_type="textarea">
                                        <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;"/>
                                    </div>
                                </td>
                            </tr>
                                <%-- 当操作为内置操作0时不允许更改操作类型与设置视图位置--%>
                            <c:if test="${sysModelingOperationForm.fdType eq '0'}">
                                <html:hidden property="fdViewLocation"/>
                                <html:hidden property="fdOperationScenario"/>
                            </c:if>
                            <c:if test="${!(sysModelingOperationForm.fdType eq '0')}">
                                <kmss:ifModuleExist path="/third/payment/">
                                    <c:if test="${mechanism.payment eq true}">
                                        <tr>
                                            <td class="td_normal_title" width="15%">
                                                    ${lfn:message('sys-modeling-base:sysModelingOperation.fdOperationScenario')}
                                            </td>
                                            <td width="35%">
                                                    <%-- 操作场景--%>
                                                <div id="_xform_fdOperationScenario" _xform_type="radio">
                                                    <xform:radio property="fdOperationScenario"
                                                                 htmlElementProperties="id='fdOperationScenario'" onValueChange="localScenarioChange"
                                                                 showStatus="edit" value="">
                                                        <xform:enumsDataSource enumsType="sys_modeling_operation_scenario"/>
                                                    </xform:radio>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdPayeeMerchant" style="display: none">
                                            <td class="td_normal_title" width="112px">
                                                    ${lfn:message('sys-modeling-base:sysModelingOperation.fdPayeeMerchant')}
                                            </td>
                                            <td width="620px">
                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdPayeeMerchant" type="hidden"
                                                           name="fdPayeeMerchant"
                                                           value="<c:out value='${sysModelingOperationForm.fdPayeeMerchant}' />">
                                                </div>
                                                <div mdlng-rltn-prprty-value="fdPayeeMerchant">
                                                    <div class="model-mask-panel-table-base">
                                                    </div>
                                                    <div class="payMerchantTip" style="display: none"> ${lfn:message('sys-modeling-base:modeling.fdPayeeMerchant.isnotnull')}</div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdPayType" style="display: none">
                                            <td class="td_normal_title" width="15%">
                                                    ${lfn:message('sys-modeling-base:sysModelingOperation.fdPayType')}</td>
                                            <td width="85%">
                                                    <%-- 支付方式--%>
                                                <div id="_xform_fdPayType" _xform_type="radio">
                                                    <xform:checkbox property="fdPayType" showStatus="readOnly"
                                                                    value="${empty sysModelingOperationForm.fdPayType ? 0:sysModelingOperationForm.fdPayType}">
                                                        <xform:enumsDataSource enumsType="sys_modeling_operation_payType"/>
                                                    </xform:checkbox>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdPayMethod" style="display: none">
                                            <td class="td_normal_title" width="15%">
                                                    ${lfn:message('sys-modeling-base:sysModelingOperation.fdPayMethod')}
                                            </td>
                                            <td width="35%">
                                                    <%-- 支付类型--%>
                                                <div id="_xform_fdPayMethod" _xform_type="radio">
                                                    <xform:radio property="fdPayMethod"
                                                                 htmlElementProperties="id='fdPayMethod'"
                                                                 showStatus="readOnly" value="${empty sysModelingOperationForm.fdPayMethod ? 1:sysModelingOperationForm.fdPayMethod}">
                                                        <xform:enumsDataSource enumsType="sys_modeling_operation_paymethod"/>
                                                    </xform:radio>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdPayUser" style="display: none">
                                            <td class="td_normal_title" width="112px">
                                                    ${lfn:message('sys-modeling-base:sysModelingOperation.fdPayUser')}
                                            </td>
                                            <td width="620px">
                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdPayUser" type="hidden"
                                                           name="fdPayUser"
                                                           value="<c:out value='${sysModelingOperationForm.fdPayUser}' />">
                                                </div>
                                                <div mdlng-rltn-prprty-value="fdPayUser">
                                                    <div class="model-mask-panel-table-base">
                                                    </div>
                                                    <div class="payUserTip" style="display: none">${lfn:message('sys-modeling-base:modeling.fdPayUser.isnotnull')}</div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdPayMoney" style="display: none">
                                            <td class="td_normal_title" width="112px">
                                                    ${lfn:message('sys-modeling-base:sysModelingOperation.fdPayMoney')}
                                            </td>
                                            <td width="620px">
                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdPayMoney" type="hidden"
                                                           name="fdPayMoney"
                                                           value="<c:out value='${sysModelingOperationForm.fdPayMoney}' />">
                                                </div>
                                                <div mdlng-rltn-prprty-value="fdPayMoney">
                                                    <div class="model-mask-panel-table-base">
                                                    </div>
                                                    <div class="payMoneyTip" style="display: none">${lfn:message('sys-modeling-base:modeling.fdPayMoney.isnotnull')}</div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdOutParam" style="display: none">
                                            <td class="td_normal_title" width="112px">
                                                    ${lfn:message('sys-modeling-base:sysModelingOperation.fdOutParam')}
                                            </td>
                                            <td width="620px">
                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdOutParam" type="hidden" name="fdOutParam"
                                                           value="<c:out value='${sysModelingOperationForm.fdOutParam}' />">
                                                </div>
                                                <div mdlng-rltn-prprty-value="fdOutParam"
                                                     mdlng-rltn-prprty-type="table">

                                                    <div class="model-mask-panel-table-base">
                                                        <table>
                                                            <thead>
                                                            <tr>
                                                                <td>${lfn:message('sys-modeling-base:modeling.payment.form.field')}
                                                                    <small style="color: #999;">(target)</small>
                                                                </td>
                                                                <td>${lfn:message('sys-modeling-base:modeling.payment.parameter.selection')}
                                                                    <small style="color: #999;">(source)</small>
                                                                </td>
                                                                <td>${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}</td>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <div class="model-mask-panel-table-create" prprty-click="create">
                                                        <div>${lfn:message('sys-modeling-base:button.add')}</div>
                                                    </div>
                                                    <div class="payParamTip" style="display: none">${lfn:message('sys-modeling-base:modeling.fdOutParam.isnotnull')}</div>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:if>
                                </kmss:ifModuleExist>
                                <tr>
                                    <td class="td_normal_title" width="15%">
                                            ${lfn:message('sys-modeling-base:sysModelingOperation.fdViewLocation')}
                                    </td>
                                    <td width="35%">
                                            <%-- 视图位置--%>
                                        <div id="_xform_fdViewLocation" _xform_type="radio">
                                            <xform:radio property="fdViewLocation"
                                                         htmlElementProperties="id='fdViewLocation'"
                                                         showStatus="edit" value="" onValueChange="localChange">
                                                <xform:enumsDataSource enumsType="sys_modeling_operation_location"/>
                                            </xform:radio>
                                        </div>
                                                <div class="viewLocationTip">${lfn:message('sys-modeling-base:operation.view.location.tip')}</div>
                                        <div class="payViewLocationTip" style="display: none"> ${lfn:message('sys-modeling-base:modeling.model.payment.operviewmsg')}</div>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${ !(sysModelingOperationForm.fdViewLocation eq '0')}">
                                <tr mdlng-prtn-property="fdListViewIdValid" style="display: none">
                                    <td class="td_normal_title" width="15%">
                                            ${lfn:message('sys-modeling-base:operation.check.operation.data')}
                                    </td>
                                    <td width="35%">
                                            <%--     勾选操作数据--%>
                                        <div id="_xform_fdListViewIdValid" _xform_type="radio">
                                            <xform:radio property="fdListViewIdValid"
                                                         htmlElementProperties="id='fdListViewIdValid'"
                                                         showStatus="edit"
                                                         value="${sysModelingOperationForm.fdListViewIdValid==null?'0':sysModelingOperationForm.fdListViewIdValid}">
                                                <xform:simpleDataSource value="1"> ${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.1')}${lfn:message('sys-modeling-base:operation.pc.tip')}
                                                </xform:simpleDataSource>
                                                <xform:simpleDataSource value="0">  ${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.0')}
                                                </xform:simpleDataSource>
                                            </xform:radio>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${!(sysModelingOperationForm.fdType eq '0') && (sysModelingOperationForm.fdViewLocation eq '0')}">
                                <tr mdlng-prtn-property="fdListViewIdValid">
                                    <td class="td_normal_title" width="15%">
                                            ${lfn:message('sys-modeling-base:operation.check.operation.data')}
                                    </td>
                                    <td width="35%">
                                            <%--     勾选操作数据--%>
                                        <div id="_xform_fdListViewIdValid" _xform_type="radio">
                                            <xform:radio property="fdListViewIdValid"
                                                         htmlElementProperties="id='fdListViewIdValid'"
                                                         showStatus="edit"
                                                         onValueChange="fdListViewIdValidValChange"
                                                         value="${sysModelingOperationForm.fdListViewIdValid==null?'0':sysModelingOperationForm.fdListViewIdValid}">
                                                <xform:simpleDataSource value="1"> ${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.1')}${lfn:message('sys-modeling-base:operation.pc.tip')}
                                                </xform:simpleDataSource>
                                                <xform:simpleDataSource value="0"> ${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.0')}
                                                </xform:simpleDataSource>
                                            </xform:radio>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                            <tr mdlng-prtn-property="fdType">
                                <td class="td_normal_title" width="20%">
                                        ${lfn:message('sys-modeling-base:sysModelingOperation.fdType')}
                                </td>
                                <td width="80%">
                                    <div class="hiddenField">
                                    </div>
                                    <div mdlng-prtn-prprty-value="fdType" _xform_type="radio">
                                            <%-- 当操作为内置操作0时不允许更改操作类型与设置视图位置--%>
                                        <c:if test="${sysModelingOperationForm.fdType eq '0'}">
                                            <xform:radio property="fdType" htmlElementProperties="id='fdType'"
                                                         showStatus="readOnly">
                                                <xform:simpleDataSource
                                                        value="0">${lfn:message('sys-modeling-base:enums.operation.0')}
                                                </xform:simpleDataSource>
                                            </xform:radio>
                                        </c:if>
                                        <c:if test="${!(sysModelingOperationForm.fdType eq '0')}">
                                            <%-- 操作类型--%>
                                            <xform:radio property="fdType" htmlElementProperties="id='fdType'"
                                                         showStatus="edit"
                                                         onValueChange="typeChange">
                                                <xform:simpleDataSource
                                                        value="1">${lfn:message('sys-modeling-base:enums.operation.1')}
                                                </xform:simpleDataSource>
                                                <xform:simpleDataSource
                                                        value="2">${lfn:message('sys-modeling-base:enums.operation.2')}
                                                </xform:simpleDataSource>
                                            </xform:radio>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                            <tr mdlng-prtn-property="fdView" style="display: none">
                                <td class="td_normal_title" width="20%">
                                </td>
                                <td width="80%">
                                    <div class="hiddenField" mdlng-prtn-data-group="fdView">
                                            <%--此处包含视图的4个参数，1、视图主model（modelingId，modelingName），2、视图类型、3、视图 4、视图入参--%>
                                            <%--                                                补充参数：一个移动列表参数fdMobileListViewName/Id和pcAndMobile模式所需要的fdPcAndMobileType,fdPamListViewId/Name，fdPamViewId/Name--%>
                                        <input name="fdViewDef" type="hidden"
                                               value="${sysModelingOperationForm.fdViewDef}">
                                        <input name="fdViewType" type="hidden"
                                               value="${sysModelingOperationForm.fdViewType}">
                                        <input name="fdPcAndMobileType" type="hidden"
                                               value="${sysModelingOperationForm.fdPcAndMobileType}">
                                        <input name="viewModelId" type="hidden"
                                               value="${sysModelingOperationForm.viewModelId}">
                                        <input name="viewModelName" type="hidden"
                                               value="${sysModelingOperationForm.viewModelName}">
                                        //查看
                                        <input name="fdViewId" type="hidden"
                                               value="${sysModelingOperationForm.fdViewId}">
                                        <input name="fdViewName" type="hidden"
                                               value="${sysModelingOperationForm.fdViewName}">
                                        <input name="fdPamViewId" type="hidden"
                                               value="${sysModelingOperationForm.fdPamViewId}">
                                        <input name="fdPamViewName" type="hidden"
                                               value="${sysModelingOperationForm.fdPamViewName}">
                                        //列表
                                        <input name="fdListViewId" type="hidden"
                                               value="${sysModelingOperationForm.fdListViewId}">
                                        <input name="fdListViewName" type="hidden"
                                               value="${sysModelingOperationForm.fdListViewName}">
                                        <input name="fdMobileListViewId" type="hidden"
                                               value="${sysModelingOperationForm.fdMobileListViewId}">
                                        <input name="fdMobileListViewName" type="hidden"
                                               value="${sysModelingOperationForm.fdMobileListViewName}">
                                        <input name="fdPamListViewId" type="hidden"
                                               value="${sysModelingOperationForm.fdPamListViewId}">
                                        <input name="fdPamListViewName" type="hidden"
                                               value="${sysModelingOperationForm.fdPamListViewName}">
                                        <input name="fdCollectionViewId" type="hidden"
                                               value="${sysModelingOperationForm.fdCollectionViewId}">
                                        <input name="fdCollectionViewName" type="hidden"
                                               value="${sysModelingOperationForm.fdCollectionViewName}">
                                        //入参
                                        <input name="viewInc" type="hidden"
                                               value="<c:out value="${sysModelingOperationForm.fdInParam}"/>"/>
                                    </div>
                                    <div mdlng-prtn-prprty-value="fdView" style="margin-top: -48px;background: #f8f8f8;padding-bottom: 16px;">
                                        <div class="model-mask-panel-table-com" mdlng-prtn-prprty-value="" style="padding-top: 16px;height: auto;">
                                            <div class="table-com-title">${lfn:message('sys-modeling-base:sysModelingRelation.fdSourceType')}</div>
                                            <div mdlng-prtn-prprty-value="fdView_model" mdlng-prtn-prprty-type="dialog"
                                                 class="model-mask-panel-table-show"></div>
                                            <span class="txtstrong view-model-txt-strong">*</span>
                                            <div class="viewModelNameTip" style="display: none">${lfn:message('sys-modeling-base:operation.operation.view.cannot.empty')}</div>
                                        </div>
                                        <div class="model-mask-panel-table-com" mdlng-prtn-prprty-value="fdView_type">
                                            <div class="table-com-title">${lfn:message('sys-modeling-base:modelingAppListview.fdType')}</div>
                                            <div class="model-mask-panel-table-select">
                                                <p class="model-mask-panel-table-select-val" option-value="0">${lfn:message('sys-modeling-base:table.modelingAppCollectionView')}</p>
                                                <div class="model-mask-panel-table-option">
                                                    <div option-value="0">${lfn:message('sys-modeling-base:table.modelingAppCollectionView')}</div>
                                                    <div option-value="1">${lfn:message('sys-modeling-base:table.modelingAppView')}</div>
                                                    <div option-value="2">${lfn:message('sys-modeling-base:enums.operation_view.2')}</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="model-mask-panel-table-com" mdlng-prtn-prprty-value="fdView_view">
                                            <div class="table-com-title">${lfn:message('sys-modeling-base:sysModelingOperation.fdView')}</div>
                                            <div class="model-mask-panel-table-select">
                                                <p class="model-mask-panel-table-select-val">${lfn:message('sys-modeling-base:relation.please.choose')}</p>
                                                <div class="model-mask-panel-table-option">
                                                </div>
                                            </div>
                                        </div>
                                            <%-- 新建类型--%>
                                        <div class="model-mask-panel-table-com" mdlng-prtn-prprty-value="">
                                            <div class="table-com-title" id="_xform_fdNewViewAddTypeTitle">${lfn:message('sys-modeling-base:operation.new.view.add.type')}</div>
                                            <div id="_xform_fdNewViewAddType" _xform_type="radio" style="position: absolute;display:inline-block;margin: 0 0 0 4px; width: 85%;">
                                                <xform:radio property="fdNewViewAddType"
                                                             htmlElementProperties="id='fdNewViewAddType'"
                                                             showStatus="edit" value="" >
                                                    <xform:enumsDataSource enumsType="sys_modeling_operation_add_type"/>
                                                </xform:radio>
                                            </div>
                                        </div>
                                        <div class="model-mask-panel-table-base" style="margin-top: 20px;width:81%;display: inline-block;margin-left: 13%"
                                             mdlng-prtn-prprty-value="fdView_inc">
                                            <table>
                                                <thead>
                                                <tr>
                                                    <td>${lfn:message('sys-modeling-base:relation.field')}</td>
                                                    <td>${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperator')}</td>
                                                    <td>${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}</td>
                                                    <td>${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}</td>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                </tbody>
                                            </table>
                                            <div class="model-mask-panel-table-create" style="margin-left: 0"
                                                 prprty-click="create">
                                                <div>${lfn:message('sys-modeling-base:button.add')}</div>
                                            </div>
                                        </div>

                                    </div>
                                </td>
                            </tr>
                            <c:if test="${sysModelingOperationForm.fdDefType ne '6' and sysModelingOperationForm.fdDefType ne '8' and sysModelingOperationForm.fdDefType ne '10'}">
                                <tr mdlng-prtn-property="fdTrigger" style="visibility: hidden">
                                    <td class="td_normal_title" width="20%">
                                            ${lfn:message('sys-modeling-base:modeling.form.BusinessTriggered')}
                                    </td>
                                    <td width="80%">
                                        <div class="hiddenField" mdlng-prtn-data-group="fdTrigger">
                                            <input name="fdScenesId" type="hidden"
                                                   value="${sysModelingOperationForm.fdScenesId}">
                                            <input name="fdBehaviorId" type="hidden"
                                                   value="${sysModelingOperationForm.fdBehaviorId}">
                                            <input name="fdScenesName" type="hidden"
                                                   value="${sysModelingOperationForm.fdScenesName}">
                                            <input name="fdBehaviorName" type="hidden"
                                                   value="${sysModelingOperationForm.fdBehaviorName}">
                                            <input name="fdTriggerType" type="hidden"
                                                   value="${sysModelingOperationForm.fdTriggerType}">
                                            <input name="triggerInc" type="hidden"
                                                   value="<c:out value="${sysModelingOperationForm.fdInParam}"/>"/>
                                                <%--                                        此处包含业务触发的3个参数，1、业务触发类型（无，动作，场景），2、触发对象（modelingId，modelingName）3、业务触发入参--%>
                                        </div>
                                        <div mdlng-prtn-prprty-value="fdTrigger">
                                            <div mdlng-prtn-prprty-value="fdTriggerType"
                                                 class="model-mask-panel-table-select">
                                                <p class="model-mask-panel-table-select-val">${lfn:message('sys-modeling-base:relation.please.choose')}</p>
                                                <div class="model-mask-panel-table-option">
                                                    <div option-value="">${lfn:message('sys-modeling-base:relation.please.choose')}</div>
                                                    <div option-value="0">${lfn:message('sys-modeling-base:sysModelingOperation.fdScenes')}</div>
                                                    <div option-value="1">${lfn:message('sys-modeling-base:sysModelingOperation.fdBehavior')}</div>
                                                </div>
                                            </div>
                                            <div mdlng-prtn-prprty-type="dialog"
                                                 class="model-mask-panel-table-show"
                                                 style=" margin-top: 20px;display: none"></div>
                                            <div mdlng-prtn-prprty-value="fdTriggerInc"
                                                 mdlng-prtn-prprty-type="table" style=" margin-top: 20px;display: none">
                                                <div class="model-mask-panel-table-base">
                                                    <table>
                                                        <thead>
                                                        <tr>
                                                            <td>${lfn:message('sys-modeling-base:behavior.trigger.name')}：</td>
                                                            <td>${lfn:message('sys-modeling-base:sysModelingOperation.searchAndTarget')}</td>
                                                            <td>${lfn:message('sys-modeling-base:sysModelingOperation.typeAndField')}</td>
                                                            <td>${lfn:message('sys-modeling-base:sysModelingOperation.value')}</td>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div id="hideProperty" style="display: none">
                    <!-- 隐藏域 -->

                    <input name="fdInParam" type="hidden"
                           value="<c:out value='${sysModelingOperationForm.fdInParam}' />">

                    <html:hidden property="modelMainId"/>
                    <html:hidden property="fdId"/>
                    <html:hidden property="method_GET"/>
                </div>
                <div class="toolbar-bottom">
                    <ui:button text="${ lfn:message('button.close') }" order="5" onclick="closeWindow();"/>

                    <c:choose>
                        <c:when test="${ sysModelingOperationForm.method_GET == 'update' }">
                            <ui:button text="${ lfn:message('button.update') }" onclick="dosubmit( 'update');"/>
                        </c:when>
                        <c:when test="${ sysModelingOperationForm.method_GET == 'edit' }">
                            <ui:button text="${ lfn:message('button.update') }" onclick="dosubmit( 'update');"/>
                        </c:when>
                        <c:when test="${ sysModelingOperationForm.method_GET == 'add' }">
                            <ui:button text="${ lfn:message('button.save') }" onclick="dosubmit('save');"/>
                        </c:when>
                    </c:choose>
                </div>

            </div>
        </html:form>
        <script type="text/javascript">
            var validation = $KMSSValidation();
            seajs.use(["sys/modeling/base/relation/res/js/operation",
                    "lui/dialog", "lui/jquery", "sys/modeling/base/formlog/res/mark/operationFMMark"]
                , function (operation, dialog, $, operationFMMark) {

                    LUI.ready(function(){
                        var methodName = "${ sysModelingOperationForm.method_GET }";
                        if(methodName == "edit" || methodName =="update"){
                            initName();
                        }

                    });

                    function initName(){
                        var dialogTitle = $(window.parent.document).find('.lui_dialog_head_left');
                        var fdType = "${sysModelingOperationForm.fdType}";
                        if(!dialogTitle){
                            return;
                        }
                        if(fdType == '0'){
                            var realName = "${lfn:message('sys-modeling-base:enums.operation.0')}" + ": " + "${sysModelingOperationForm.realName}";
                            dialogTitle.attr('title',realName);
                            dialogTitle.html(realName);
                        }else{
                            var realName = "${lfn:message('sys-modeling-base:sysModelingOperation.customName')}";
                            dialogTitle.attr('title',realName);
                            dialogTitle.html(realName);
                        }

                    }

                    function init() {
                        var cfg = {
                                container: $("#operationEditContainer"),
                                xformId: "${xformId}",
                                formFdType: "${sysModelingOperationForm.fdType}",
                                modelMainId: "${sysModelingOperationForm.modelMainId}",
                                fdAppId: "${sysModelingOperationForm.fdApplicationId}",
                                sourceData: ${sourceData},
                                paramSourceData:${paramSourceData},
                                payMerchantsData:${payMerchantsData}
                            }
                        ;
                        window.operationInst = new operation.Operation(cfg);
                        operationInst.startup();
                        window.fmmark = new operationFMMark.OperationFMMark({fdId: "${sysModelingOperationForm.fdId}"});
                        fmmark.startup();
                        //打印、批量打印、删除屏蔽业务触发
                        if("${sysModelingOperationForm.fdDefType}" != "6" && "${sysModelingOperationForm.fdDefType}" != "8" && "${sysModelingOperationForm.fdDefType}" != "10"){
                            $("[mdlng-prtn-property='fdTrigger']").css("visibility","visible");
                        }
                    }

                    init();
                    window.typeChange = function (v, n) {
                        operationInst.fdTypeChange(v, n);
                    };
                    window.localChange = function (v, n) {
                        var fdOperationScenario = $("[name='fdOperationScenario']:checked").val();

                        if(fdOperationScenario ==="1"){
                            //支付场景，默认隐藏勾选数据按钮
                            $("[mdlng-prtn-property=\"fdListViewIdValid\"]").hide();
                            if (v === "1") {
                                //隐藏提示
                                $(".payViewLocationTip").hide();
                            }else{
                                //显示提示
                                $(".payViewLocationTip").show();
                            }
                        }else{
                            if (v === "1") {
                                $("[mdlng-prtn-property=\"fdListViewIdValid\"]").hide();
                            } else {
                                $("[mdlng-prtn-property=\"fdListViewIdValid\"]").show();
                            }
                        }

                        window.operationInst.showOrHideAddType();
                    };
                    window.fdListViewIdValidValChange =function(v, n){
                        window.operationInst.showOrHideAddType();
                    };
                    window.localScenarioChange =function(v,n){
                        if (v === "1") {
                            $("[mdlng-rltn-property=\"fdPayeeMerchant\"]").show();
                            $("[mdlng-rltn-property=\"fdPayType\"]").show();
                            $("[mdlng-rltn-property=\"fdPayMethod\"]").show();
                            $("[mdlng-rltn-property=\"fdPayUser\"]").show();
                            $("[mdlng-rltn-property=\"fdPayMoney\"]").show();
                            $("[mdlng-rltn-property=\"fdOutParam\"]").show();
                            var fdViewLocation=$("[name='fdViewLocation']:checked").val();
                            if(fdViewLocation=="0"){
                                $(".payViewLocationTip").show();
                            }
                            $("[mdlng-prtn-property=\"fdListViewIdValid\"]").hide();
                            $("[mdlng-prtn-property=\"fdType\"]").hide();
                            $("[mdlng-prtn-property=\"fdView\"]").hide();
                            $("[mdlng-prtn-property=\"fdTrigger\"]").hide();
                        } else {
                            $("[mdlng-rltn-property=\"fdPayeeMerchant\"]").hide();
                            $("[mdlng-rltn-property=\"fdPayType\"]").hide();
                            $("[mdlng-rltn-property=\"fdPayMethod\"]").hide();
                            $("[mdlng-rltn-property=\"fdPayUser\"]").hide();
                            $("[mdlng-rltn-property=\"fdPayMoney\"]").hide();
                            $("[mdlng-rltn-property=\"fdOutParam\"]").hide();
                            $("[mdlng-prtn-property=\"fdListViewIdValid\"]").show();
                            $("[mdlng-prtn-property=\"fdType\"]").show();
                            $("[mdlng-prtn-property=\"fdTrigger\"]").show();
                            $(".payViewLocationTip").hide();
                        }

                        window.operationInst.showOrHideAddType();
                    };
                    window.onload=function (){
                        var fdOperationScenario="${sysModelingOperationForm.fdOperationScenario}";
                        if (fdOperationScenario === "1") {
                            $("[mdlng-rltn-property=\"fdPayeeMerchant\"]").show();
                            $("[mdlng-rltn-property=\"fdPayType\"]").show();
                            $("[mdlng-rltn-property=\"fdPayMethod\"]").show();
                            $("[mdlng-rltn-property=\"fdPayUser\"]").show();
                            $("[mdlng-rltn-property=\"fdPayMoney\"]").show();
                            $("[mdlng-rltn-property=\"fdOutParam\"]").show();
                            $("[mdlng-prtn-property=\"fdListViewIdValid\"]").hide();
                            $("[mdlng-prtn-property=\"fdType\"]").hide();
                            $("[mdlng-prtn-property=\"fdView\"]").hide();
                            $("[mdlng-prtn-property=\"fdTrigger\"]").hide();
                        }
                    }
                    window.dosubmit = function (type) {
                        var $KMSSValidation = $GetKMSSDefaultValidation();
                        var fdName = $("input[name='fdName']",document)[0];
                        var result = $KMSSValidation.validateElement(fdName);
                        var maxLength = $KMSSValidation.getValidator("maxLength(18)");
                        if (result){
                            if(maxLength.test($("input[name='fdName']").val(),fdName) == false){
                                $(".mainModelNameTip").show();

                                return;
                            }};
                        operationInst.getKeyData();
                        // console.log("operationInst.getKeyData()",operationInst.getKeyData());
                        if(!operationViewModelValidate()){
                            $(".viewModelNameTip").show();
                            return;
                        }
                        var fdOperationScenario = $("[name='fdOperationScenario']:checked").val()
                        //拓展操作选择支付场景后，收款商户，支付用户，支付金额，返回参数不能为空
                        if(fdOperationScenario ==="1"){
                            // 收款商户
                            var fdPayeeMerchant= $("[name='fdPayeeMerchant']").val();
                            if(fdPayeeMerchant=="null"){
                                $(".payMerchantTip").show();
                                return;
                            }
                            //支付用户
                            var fdPayUser= $("[name='fdPayUser']").val();
                            if(fdPayUser=="null"){
                                $(".payUserTip").show();
                                return;
                            }
                            //支付金额
                            var fdPayMoney=$("[name='fdPayMoney']").val();
                            if(fdPayMoney=="null"){
                                $(".payMoneyTip").show();
                                return;
                            }
                            //返回参数
                            var fdOutParam= $("[mdlng-rltn-data=\"fdOutParam\"]").val();
                            if(fdOutParam=="[]"){
                                $(".payParamTip").show();
                                return;
                            }
                            //校验操作位置
                            var fdViewLocation=$("[name='fdViewLocation']:checked").val();
                            if(fdViewLocation=="0"){
                                $(".payViewLocationTip").show();
                                return;
                            }
                        }else{
                            $("[name='fdPayeeMerchant']").val("");
                            $("[name='fdPayUser']").val("");
                            $("[name='fdPayMoney']").val("");
                            $("[name='fdPayType']").val("");
                            $("[name='fdPayMethod']").val("");
                            $("[mdlng-rltn-data=\"fdOutParam\"]").val("");
                        }
                        if (operationConfigValidata()) {
                            Com_Submit(document.sysModelingOperationForm, type, undefined, {});
                        } else {
                            dialog.confirm("${lfn:message('sys-modeling-base:operation.notSelect.data.tips')}", function (t) {
                                if (t) {
                                    Com_Submit(document.sysModelingOperationForm, type, undefined, {});
                                }
                            })
                        }
                    };

                    //#115937
                    function operationConfigValidata() {
                        //列表视图，不勾选操作
                        var fdViewLocation = $("[name='fdViewLocation']:checked").val();
                        if (fdViewLocation != '0') {
                            return true;
                        }
                        var fdListViewIdValid = $("[name='fdListViewIdValid']:checked").val();
                        if (fdListViewIdValid != '0') {
                            return true;
                        }
                        var fdInParam = $("[name='fdInParam']").val();
                        try {
                            var inParam = JSON.parse(fdInParam);
                            var trigger = inParam.trigger;
                            if (!validateExpression(trigger)) {
                                return false;
                            }
                            var viewNew = inParam.viewNew;
                            if (!validateExpression(viewNew)) {
                                return false;
                            }
                            return true;
                        } catch (e) {
                            console.error(e)
                        }
                        return false;
                    }
                    //扩展操作（有操作视图）时，操作视图必填
                    function operationViewModelValidate() {
                        var viewModelId = $("[name='viewModelId']").val();
                        var fdType = $("[name='fdType']:checked").val();
                        if(fdType ==="1" && !viewModelId){
                            return false;
                        }
                        return true;
                    }
                    function validateExpression(array) {
                        if (!array) {
                            return true;
                        }
                        for (var i = 0; i < array.length; i++) {
                            var tri = array[i];
                            var exp = tri.expression;
                            var reg = new RegExp('\\\$.+?\\\$', 'g');
                            if (exp.value && exp.value.match(reg)) {
                                return false;
                            }
                        }
                        return true;
                    }

                    window.checkPayMerchantValue =function(){
                        var fdPayMerchant= $(".fdPayMerchantSelect option:selected").val();
                        if(fdPayMerchant==""){
                            $(".payMerchantTip").show();
                        }else{
                            $(".payMerchantTip").hide();
                        }
                    }

                    window.checkPayuserValue =function(){
                        var fdPayUser= $(".fdPayuserSelect option:selected").val();
                        if(fdPayUser==""){
                            $(".payUserTip").show();
                        }else{
                            $(".payUserTip").hide();
                        }
                    }

                    window.checkPaymoneyValue =function(){
                        var fdPayMoney= $(".fdPaymoneySelect option:selected").val();
                        if(fdPayMoney==""){
                            $(".payMoneyTip").show();
                        }else{
                            $(".payMoneyTip").hide();
                        }
                    }
                    window.checkOutParam =function(){
                        //返回参数
                        var fdOutParamMain= $(".fdOutParam_main option:selected").val();
                        var fdOutParamPassive= $(".fdOutParam_passive option:selected").val();
                        if(fdOutParamMain=="" && fdOutParamPassive==""){
                            $(".payParamTip").show();
                        }else{
                            $(".payParamTip").hide();
                        }
                    }

                    window.closeWindow=function(){
                        $dialog.hide("close");
                    }
                })
        </script>
        </script>
    </template:replace>
</template:include>