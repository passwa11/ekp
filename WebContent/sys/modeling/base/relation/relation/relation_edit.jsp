<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil" %>

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
            Com_IncludeFile("xform.js");

        </script>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/relation/relation/css/relation.css?s_cache=${LUI_Cache}"/>
        <style>
            label.lui-lbpm-radio {
                margin-right: 12px;
                display: inline-block;
            }

            .tb_simple.modeling_form_table label {
                margin-right: 30px;
            }

            #_xform_fdShowType .txtstrong {
                margin-left: -26px;
            }

           .lui-mulit-zh-cn-html{
                background-color: #ffff !important;
            }

            body .modeling_form_table input {
                margin-right: 8px;
            }

            .model-mask-panel .model-mask-panel-table-radio-item {
                line-height: 33px;
                margin-right: 30px;
                margin-bottom: -20px;
            }

            .model-mask-panel-output-select-right {
                margin-left: 22px;
            }

            .model-mask-panel .model-mask-panel-table-radio-item .model-mask-panel-output-select-left {
                padding-top: 10px;
            }

        </style>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/modeling/base/sysModelingRelation.do">
            <div class="model-mask-panel medium" style="padding-bottom: 72px">
                <div>
                    <div class="model-mask-panel-table">
                        <ul id="relationEditContainer">
                            <li class="model-table-item" mdlng-rltn-mrk="region" mdlng-rltn-data="basic">
                                <div class="model-table-left">
                                    <i></i>
                                    <p>${lfn:message('sys-modeling-base:kmReviewDocumentLableName.baseInfo')}</p>
                                </div>
                                <div class="model-table-right">
                                    <table class="tb_simple modeling_form_table " mdlng-rltn-mrk="regionTable">
                                        <tbody>
                                        <tr>
                                            <td class="td_normal_title" width="112px">
                                                    ${lfn:message('sys-modeling-base:sysModelingRelation.fdName')}
                                            </td>
                                            <td width="620px">
                                                <div _xform_type="text" class="relation-form-input-div"
                                                     style="border: none">
                                                    <xform:text property="fdName" showStatus="edit"
                                                                validators="required" required="true"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdShowType">
                                            <td class="td_normal_title" width="112px">
                                                    ${lfn:message('sys-modeling-base:sysModelingRelation.fdShowType')}

                                            </td>
                                                <%--                                          勿删！！！，此处用做替换原始的radio样式模板，后续使用时可参考此处--%>
                                                <%--                                            <td width="620px">--%>
                                                <%--                                                <div class="hiddenField">--%>
                                                <%--                                                    <input mdlng-rltn-data="fdThrough" type="hidden" name="throughJson"--%>
                                                <%--                                                           value="<c:out value='${throughJson}' />">--%>
                                                <%--                                                </div>--%>
                                                <%--                                                <div mdlng-rltn-prprty-value="fdThrough"--%>
                                                <%--                                                     mdlng-rltn-prprty-type="through">--%>
                                                <%--                                                    <div class="model-mask-panel-table-radio"--%>
                                                <%--                                                         mdlng-rltn-prprty-type="throughradio">--%>
                                                <%--                                                        <div class="model-mask-panel-table-radio-item">--%>
                                                <%--                                                            <label for="fdThroughIsThrough1">--%>
                                                <%--                                                                <div class="model-mask-panel-output-select-left">--%>
                                                <%--                                                                    <input type="radio" value="1" name="fdThroughIsThrough"--%>
                                                <%--                                                                           id="fdThroughIsThrough1">--%>
                                                <%--                                                                    <i class="model-mask-panel-output-i"></i>--%>
                                                <%--                                                                </div>--%>
                                                <%--                                                                <div class="model-mask-panel-output-select-right">--%>
                                                <%--                                                                    <div><span>&nbsp;是&emsp;</span>--%>
                                                <%--                                                                    </div>--%>
                                                <%--                                                                </div>--%>
                                                <%--                                                            </label>--%>
                                                <%--                                                        </div>--%>
                                                <%--                                                        <div class="model-mask-panel-table-radio-item">--%>
                                                <%--                                                            <label for="fdThroughIsThrough0">--%>
                                                <%--                                                                <div class="model-mask-panel-output-select-left">--%>
                                                <%--                                                                    <input type="radio" value="0" name="fdThroughIsThrough"--%>
                                                <%--                                                                           checked id="fdThroughIsThrough0">--%>
                                                <%--                                                                    <i class="model-mask-panel-output-i"></i>--%>
                                                <%--                                                                </div>--%>
                                                <%--                                                                <div class="model-mask-panel-output-select-right">--%>
                                                <%--                                                                    <div><span>&nbsp;否&emsp;</span>--%>
                                                <%--                                                                    </div>--%>
                                                <%--                                                                </div>--%>
                                                <%--                                                            </label>--%>
                                                <%--                                                        </div>--%>
                                                <%--                                                    </div>--%>
                                                <%--                                                    <div class="model-mask-panel-table-show"--%>
                                                <%--                                                         mdlng-rltn-prprty-type="throughdialog"></div>--%>
                                                <%--                                                </div>--%>
                                                <%--                                            </td>--%>
                                            <td width="620px">
                                                    <%-- 显示类型--%>
                                                <div id="_xform_fdShowType" _xform_type="radio">
                                                        <%--                                                    enums.relation.show.0=单选选择框--%>
                                                        <%--                                                    enums.relation.show.1=多选选择框--%>
                                                        <%--                                                    enums.relation.show.2=下拉列表--%>
                                                        <%--                                                    enums.relation.show.3=单选按钮--%>
                                                        <%--                                                    enums.relation.show.4=多选按钮--%>

                                                    <c:if test="${sysModelingRelationForm.modelMainId eq sysModelingRelationForm.modelPassiveId }">
                                                        <xform:radio property="fdShowType"
                                                                     htmlElementProperties="id='fdShowType'"
                                                                     onValueChange="showTypeChange"
                                                                     validators="required" required="true"
                                                                     showStatus="edit">
                                                            <xform:enumsDataSource
                                                                    enumsType="sys_modeling_relation_show"/>
                                                        </xform:radio>
                                                    </c:if>
                                                    <c:if test="${sysModelingRelationForm.modelMainId ne sysModelingRelationForm.modelPassiveId }">
                                                        <xform:radio property="fdShowType"
                                                                     htmlElementProperties="id='fdShowType'"
                                                                     onValueChange="showTypeChange"
                                                                     validators="required" required="true"
                                                                     showStatus="edit">
                                                            <xform:enumsDataSource
                                                                    enumsType="sys_modeling_relation_show_1"/>
                                                        </xform:radio>
                                                    </c:if>
                                                </div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>

                            <li class="model-table-item" mdlng-rltn-mrk="region" mdlng-rltn-data="setting">
                                <div class="model-table-left">
                                    <i></i>
                                    <p>${lfn:message('sys-modeling-base:relation.configuration.relationship')}</p>
                                </div>
                                <div class="model-table-right">
                                    <table class="tb_simple modeling_form_table ">
                                        <tbody>
                                        <tr>
                                            <td class="td_normal_title modeling_relation_fdRelation" width="112px">
                                                    ${lfn:message('sys-modeling-base:sysModelingRelation.fdWidgetName')}
                                            </td>
                                            <td width="620px">
                                                    <%-- 关联控件名--%>
                                                <div class="hiddenField">
                                                    <input type="hidden" name="fdWidgetName"
                                                           value="<c:out value='${sysModelingRelationForm.fdWidgetName}' />">${widgets.holders}
                                                </div>
                                                <div id="_xform_fdWidgetId" _xform_type="select"
                                                     class="modeling-form-left">
                                                    <select style="width:610px" name="fdWidgetId" subject="关联控件Id"
                                                            class="inputsgl">
                                                        <c:forEach var="moduleMap" varStatus="vstatus"
                                                                   items="${widgets.holders}">
                                                            <c:if test="${moduleMap.value.relation==sysModelingRelationForm.fdId }">
                                                                <option selected
                                                                        title="${moduleMap.value.fullLabel==null?moduleMap.value.label:moduleMap.value.fullLabel}"
                                                                        value="${moduleMap.key}">${moduleMap.value.fullLabel==null?moduleMap.value.label:moduleMap.value.fullLabel}</option>
                                                            </c:if>
                                                            <c:if test="${moduleMap.value.relation==null }">
                                                                <option title="${moduleMap.value.fullLabel==null?moduleMap.value.label:moduleMap.value.fullLabel}"
                                                                        value="${moduleMap.key}">${moduleMap.value.fullLabel==null?moduleMap.value.label:moduleMap.value.fullLabel}</option>
                                                            </c:if>
                                                        </c:forEach>

                                                    </select>
                                                        <%--                                                    <xform:select  property="fdWidgetId" style="width:200px;"--%>
                                                        <%--                                                                  showStatus="edit" showPleaseSelect="false">--%>
                                                        <%--                                                        <c:forEach var="moduleMap" varStatus="vstatus"--%>
                                                        <%--                                                                   items="${widgets.holders}">--%>
                                                        <%--                                                            <c:if test="${moduleMap.value.relation==sysModelingRelationForm.fdId || moduleMap.value.relation==null}">--%>
                                                        <%--                                                                <xform:simpleDataSource value="${moduleMap.key}"> ${moduleMap.value.label}--%>
                                                        <%--                                                                </xform:simpleDataSource>--%>
                                                        <%--                                                            </c:if>--%>
                                                        <%--                                                        </c:forEach>--%>
                                                        <%--                                                    </xform:select>--%>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdThrough">
                                            <td class="td_normal_title" width="112px">
                                                ${lfn:message('sys-modeling-base:sysModelingRelation.fdIsThrough')}
                                            </td>
                                            <td width="620px">
                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdThrough" type="hidden" name="throughJson"
                                                           value="<c:out value='${throughJson}' />">
                                                </div>
                                                <div mdlng-rltn-prprty-value="fdThrough"
                                                     mdlng-rltn-prprty-type="through">
                                                    <div class="model-mask-panel-table-radio"
                                                         mdlng-rltn-prprty-type="throughradio">
                                                        <div class="model-mask-panel-table-radio-item">
                                                            <label for="fdThroughIsThrough1">
                                                                <div class="model-mask-panel-output-select-left">
                                                                    <input type="radio" value="1"
                                                                           name="fdThroughIsThrough"
                                                                           id="fdThroughIsThrough1">
                                                                    <i class="model-mask-panel-output-i"></i>
                                                                </div>
                                                                <div class="model-mask-panel-output-select-right">
                                                                    <div><span>&nbsp;${lfn:message('sys-modeling-base:Designer_Lang.tree_attr_yes')}&emsp;</span>
                                                                    </div>
                                                                </div>
                                                            </label>
                                                        </div>
                                                        <div class="model-mask-panel-table-radio-item">
                                                            <label for="fdThroughIsThrough0">
                                                                <div class="model-mask-panel-output-select-left">
                                                                    <input type="radio" value="0"
                                                                           name="fdThroughIsThrough"
                                                                           checked id="fdThroughIsThrough0">
                                                                    <i class="model-mask-panel-output-i"></i>
                                                                </div>
                                                                <div class="model-mask-panel-output-select-right">
                                                                    <div><span>&nbsp;${lfn:message('sys-modeling-base:Designer_Lang.tree_attr_no')}&emsp;</span>
                                                                    </div>
                                                                </div>
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="model-mask-panel-table-show"
                                                         mdlng-rltn-prprty-type="throughdialog"></div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdSourceType" >
                                            <td class="td_normal_title" id = "type" width="112px">${lfn:message('sys-modeling-base:Designer_Lang.tree_attr_displayType')}</td>
                                            <td width="620px">
                                                <div class="model-mask-panel-table-base">
                                                    <div class="detailOrMain" _xform_type="radio" style="margin-left: 0">
                                                        <xform:radio property="fdSourceType"
                                                                     htmlElementProperties="id='fdSourceType'">
                                                            <xform:enumsDataSource
                                                                    enumsType="sys_modeling_relation_source_type"/>
                                                        </xform:radio>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdTargetDetail" style="display: none">
                                            <td class="td_normal_title" width="112px">${lfn:message('sys-modeling-base:sysModelingRelation.fdTargetDetail')}</td>
                                            <td width="620px">
                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdTargetDetail" type="hidden" name="fdTargetDetail"
                                                           value="<c:out value='${sysModelingRelationForm.fdTargetDetail}' />">
                                                </div>
                                                <div id="detailChecked" _xform_type="checkbox">
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdReturn">
                                            <td class="td_normal_title" width="112px">
                                                ${lfn:message('sys-modeling-base:sysModelingRelation.fdReturn')}
                                            </td>
                                            <td width="620px">
                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdReturn" type="hidden" name="fdReturn"
                                                           value="<c:out value='${sysModelingRelationForm.fdReturn}' />">
                                                </div>
                                                <div mdlng-rltn-prprty-value="fdReturn" mdlng-rltn-prprty-type="dialog"
                                                     class="model-mask-panel-table-show"></div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdOutExtend">
                                            <td class="td_normal_title" width="112px">
                                             ${lfn:message('sys-modeling-base:sysModelingRelation.fdOutExtend')}
                                            </td>
                                            <td width="620px">
                                                <div id="_xform_fdOutExtend" _xform_type="radio">
                                                    <xform:radio property="fdOutExtend"
                                                                 htmlElementProperties="id='fdOutExtend'">
                                                        <xform:enumsDataSource
                                                                enumsType="sys_modeling_relation_out_ext"/>
                                                    </xform:radio>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdOutParam">
                                            <td class="td_normal_title" width="112px">
                                                ${lfn:message('sys-modeling-base:relation.outgoing.parameters')}
                                            </td>
                                            <td width="620px">
                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdOutParam" type="hidden" name="fdOutParam"
                                                           value="<c:out value='${sysModelingRelationForm.fdOutParam}' />">
                                                </div>
                                                <div mdlng-rltn-prprty-value="fdOutParam"
                                                     mdlng-rltn-prprty-type="table">

                                                    <div class="model-mask-panel-table-base">
                                                        <table>
                                                            <thead>
                                                            <tr>
                                                                <td>${sysModelingRelationForm.modelMainName}
                                                                    <small style="color: #999;">(target)</small>
                                                                </td>
                                                                <td>${sysModelingRelationForm.modelPassiveName}
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
                                                </div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>

                            <li class="model-table-item" mdlng-rltn-mrk="region" mdlng-rltn-data="source">
                                <div class="model-table-left">
                                    <i></i>
                                    <p>${lfn:message('sys-modeling-base:relation.data.source')}</p>
                                </div>
                                <div class="model-table-right">
                                    <table class="tb_simple modeling_form_table modeling_data_source_table">
                                        <tbody>
                                        <tr mdlng-rltn-property="fdInWhere">
                                            <td class="td_normal_title" width="112px">
                                                ${lfn:message('sys-modeling-base:sysModelingRelation.fdDetailWhere')}
                                            </td>
                                            <td width="620px">
                                                <div style="margin-left:15px;margin-top: 5px;">
                                                    <label><input type="radio" value="0" name="fdInWhereType"
                                                                  <c:if test="${sysModelingRelationForm.fdInWhereType eq '0' or empty sysModelingRelationForm.fdInWhereType }">checked</c:if>/>${lfn:message('sys-modeling-base:relation.meet.all.conditions')}</label>
                                                    <label><input type="radio" value="1" name="fdInWhereType"
                                                                  <c:if test="${sysModelingRelationForm.fdInWhereType eq '1'}">checked</c:if> />${lfn:message('sys-modeling-base:relation.meet.any.conditions')}</label>
                                                    <label style="margin-left:10px;"><input type="radio" value="2"
                                                                                            name="fdInWhereType"
                                                                                            <c:if test="${sysModelingRelationForm.fdInWhereType eq '2'}">checked</c:if>/>${lfn:message('sys-modeling-base:relation.query.all.data')}</label>
                                                </div>

                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdInWhere" type="hidden" name="fdInWhere"
                                                           value="<c:out value='${sysModelingRelationForm.fdInWhere}' />">
                                                </div>

                                                <div mdlng-rltn-prprty-value="fdInWhere"
                                                     mdlng-rltn-prprty-type="table" id="fdInWhereTable">
                                                    <div class="model-mask-panel-table-base">
                                                        <table>
                                                            <thead>
                                                            <tr>
                                                                <td style="min-width: 120px">${lfn:message('sys-modeling-base:relation.field')}</td>
                                                                <td style="min-width: 80px">${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperator')}</td>
                                                                <td style="min-width: 80px">${lfn:message('sys-modeling-base:relation.value.type')}</td>
                                                                <td style="min-width: 200px">${lfn:message('sys-modeling-base:modelingAppViewincpara.fdValue')}</td>
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
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdDetailWhereTemp" class="detailWhereTemp" style="display: none">
                                            <td class="td_normal_title detail_table_name" width="112px">
                                                ${lfn:message('sys-modeling-base:relation.detail.list.query.conditions')}
                                            </td>
                                            <td width="620px">
                                                <div style="margin-left:15px;margin-top: 5px;" class="detailWhereTypediv">
                                                    <label><input type="radio" value="0" name="detailWhereType"/>${lfn:message('sys-modeling-base:relation.meet.all.conditions')}</label>
                                                    <label><input type="radio" value="1" name="detailWhereType" />${lfn:message('sys-modeling-base:relation.meet.any.conditions')}</label>
                                                    <label style="margin-left:10px;"><input type="radio" value="2"
                                                                                            name="detailWhereType"/>${lfn:message('sys-modeling-base:relation.query.all.data')}</label>
                                                </div>

                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdDetailWhereTemp" type="hidden" name="fdDetailWhereTemp"
                                                           value="<c:out value='${sysModelingRelationForm.fdDetailWhere}' />">
                                                </div>

                                                <div mdlng-rltn-prprty-value="fdDetailWhereTemp"
                                                     mdlng-rltn-prprty-type="table">
                                                    <div class="model-mask-panel-table-base">
                                                        <table class="view_field_detail_where_table">
                                                            <thead>
                                                            <tr>
                                                                <td style="min-width: 120px">${lfn:message('sys-modeling-base:relation.field')}</td>
                                                                <td style="min-width: 80px">${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperator')}</td>
                                                                <td style="min-width: 80px">${lfn:message('sys-modeling-base:relation.value.type')}</td>
                                                                <td style="min-width: 200px">${lfn:message('sys-modeling-base:modelingAppViewincpara.fdValue')}</td>
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
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdOutSearch">
                                            <td class="td_normal_title" width="112px">
                                                ${lfn:message('sys-modeling-base:modelingAppListview.fdCondition')}
                                            </td>
                                            <td width="620px">
                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdOutSearch" type="hidden"
                                                           name="fdOutSearch"
                                                           value="<c:out value='${sysModelingRelationForm.fdOutSearch}' />">
                                                    <input mdlng-rltn-data="fdOutSearchNumber" type="hidden"
                                                           name="fdOutSearchNumber"
                                                           value="<c:out value='${sysModelingRelationForm.fdOutSearchNumber}' />">
                                                </div>
                                                <div mdlng-rltn-prprty-value="fdOutSearch"
                                                     mdlng-rltn-prprty-type="dialog"
                                                     class="model-mask-panel-table-show"></div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdOutSelect">
                                            <td class="td_normal_title" width="112px">
                                                 ${lfn:message('sys-modeling-base:modelingAppListview.fdDisplay')}
                                            </td>
                                            <td width="620px">
                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdOutSelect" type="hidden"
                                                           name="fdOutSelect"
                                                           value="<c:out value='${sysModelingRelationForm.fdOutSelect}' />">
                                                </div>
                                                <div mdlng-rltn-prprty-value="fdOutSelect"
                                                     mdlng-rltn-prprty-type="dialog"
                                                     class="modeling-out-select model-mask-panel-table-show"></div>
                                                <span class="txtstrong">*</span>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdOutSort">
                                            <td class="td_normal_title" width="112px">
                                                ${lfn:message('sys-modeling-base:modelingAppListview.fdOrderBy')}
                                            </td>
                                            <td width="620px">
                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdOutSort" type="hidden" name="fdOutSort"
                                                           value="<c:out value='${sysModelingRelationForm.fdOutSort}' />">
                                                </div>
                                                <div mdlng-rltn-prprty-value="fdOutSort"
                                                     mdlng-rltn-prprty-type="table">
                                                    <div class="model-mask-panel-table-base">
                                                        <table>
                                                            <thead>
                                                            <tr>
                                                                <td>${lfn:message('sys-modeling-base:relation.field')}</td>
                                                                <td>${lfn:message('sys-modeling-base:modelingAppVersion.fdOrder')}</td>
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
                                                </div>
                                            </td>
                                        </tr>
                                        <tr mdlng-rltn-property="fdListThrough">
                                            <td class="td_normal_title" width="112px">
                                                ${lfn:message('sys-modeling-base:sysModelingRelation.fdIsThroughList')}
                                            </td>
                                            <td width="620px">
                                                <div class="hiddenField">
                                                    <input mdlng-rltn-data="fdListThrough" type="hidden"
                                                           name="listThroughJson"
                                                           value="<c:out value='${listThroughJson}' />">
                                                </div>
                                                <div mdlng-rltn-prprty-value="fdListThrough"
                                                     mdlng-rltn-prprty-type="through">
                                                    <div class="model-mask-panel-table-radio"
                                                         mdlng-rltn-prprty-type="throughradio">
                                                        <div class="model-mask-panel-table-radio-item">
                                                            <label for="fdListThroughIsThrough1">
                                                                <div class="model-mask-panel-output-select-left">
                                                                    <input type="radio" value="1"
                                                                           name="fdListThroughIsThrough"
                                                                           id="fdListThroughIsThrough1">
                                                                    <i class="model-mask-panel-output-i"></i>
                                                                </div>
                                                                <div class="model-mask-panel-output-select-right">
                                                                    <div><span>&nbsp;${lfn:message('sys-modeling-base:Designer_Lang.tree_attr_yes')}&emsp;</span>
                                                                    </div>
                                                                </div>
                                                            </label>
                                                        </div>
                                                        <div class="model-mask-panel-table-radio-item">
                                                            <label for="fdListThroughIsThrough0">
                                                                <div class="model-mask-panel-output-select-left">
                                                                    <input type="radio" value="0"
                                                                           name="fdListThroughIsThrough" checked
                                                                           id="fdListThroughIsThrough0">
                                                                    <i class="model-mask-panel-output-i"></i>
                                                                </div>
                                                                <div class="model-mask-panel-output-select-right">
                                                                    <div><span>&nbsp;${lfn:message('sys-modeling-base:Designer_Lang.tree_attr_no')}&emsp;</span>
                                                                    </div>
                                                                </div>
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="model-mask-panel-table-show"
                                                         mdlng-rltn-prprty-type="throughdialog"></div>
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
                <div id="relationHideProperty" style="display: none">
                    <input type="hidden" name="modelPassiveId"
                           value="<c:out value='${sysModelingRelationForm.modelPassiveId}' />"/>
                    <input type="hidden" name="modelMainId"
                           value="<c:out value='${sysModelingRelationForm.modelMainId}' />">
                    <html:hidden property="fdId"/>
                    <html:hidden property="fdType" value="${empty sysModelingRelationForm.fdType?0:sysModelingRelationForm.fdType}"></html:hidden>
                    <html:hidden property="method_GET"/>
                </div>
                <div class="toolbar-bottom">
                    <ui:button styleClass="lui_toolbar_btn_gray" text="${ lfn:message('button.close') }" order="5"
                               onclick="onclose();"/>
                    <c:choose>
                        <c:when test="${ sysModelingRelationForm.method_GET == 'updata' }">
                            <ui:button text="${ lfn:message('button.update') }"
                                       onclick="dosubmit('update')"/>
                        </c:when>
                        <c:when test="${ sysModelingRelationForm.method_GET == 'edit' }">
                            <ui:button text="${ lfn:message('button.update') }"
                                       onclick="dosubmit('update')"/>
                        </c:when>
                        <c:when test="${ sysModelingRelationForm.method_GET == 'add' }">
                            <ui:button text="${ lfn:message('button.save') }"
                                       onclick="dosubmit('save')"/>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </html:form>
        <script type="text/javascript">
            $KMSSValidation();
            seajs.use(["sys/modeling/base/relation/res/js/relation", "lui/dialog", "lui/jquery",
                    "sys/modeling/base/formlog/res/mark/relationFMMark"]
                , function (relation, dialog, $, relationFMMark) {
                    function init() {
                        $("[name='fdWidgetId']").find("option")
                        var cfg = {
                            container: $("#relationEditContainer"),
                            widgets:${widgets},
                            xformId: "${xformId}",
                            modelMainId: "${sysModelingRelationForm.modelMainId}",
                            modelPassiveId: "${sysModelingRelationForm.modelPassiveId}",
                            widgets_docStatus:${widgets_docStatus}
                        };
                        window.relationInst = new relation.Relation(cfg);
                        relationInst.startup();
                        /* if (cfg.modelPassiveId !== cfg.modelMainId) {
                            $("[name='fdShowType'][value='5']").closest(".lui-lbpm-radio").hide();
                            $("[name='fdShowType'][value='6']").closest(".lui-lbpm-radio").hide();
                        } */
                        window.fmmark = new relationFMMark.RelationFMMark({fdId: "${sysModelingRelationForm.fdId}"});
                        fmmark.startup();
                    }

                    init();
                    window.showTypeChange = function (v, n) {
                        if (!window.relationInst) {
                            init();
                        }
                        relationInst.refreshWhere(v);
                        if (v == "0" || v == "1") {

                            relationInst.showEles(["fdListThrough", "fdOutSelect", "fdOutSearch", "fdOutParam", "fdOutExtend","fdSourceType"]);
                        } else {
                            relationInst.hideEles(["fdListThrough", "fdOutSelect", "fdOutSearch", "fdOutExtend", "fdOutParam","fdSourceType"]);
                        }
                    };
                    window.onclose = function () {
                        $dialog.hide(null);
                    };
                    window.dosubmit = function (type) {
                        relationInst.getKeyData();
                        var fdWidgetName = $("[name='fdWidgetId'] option:selected").attr("title");
                        $("[name='fdWidgetName']").val(fdWidgetName);
                        var fdShowType = $("[name='fdShowType']:checked").val();
                        if (fdShowType == "0" || fdShowType == "1") {
                            if (!$("[name='fdOutSelect']").val() || $("[name='fdOutSelect']").val() == "[]") {
                                dialog.alert("${lfn:message('sys-modeling-base:relation.set.display.item')}");
                                return
                            }

                        }
                        if (checkMainWhere()){
                            Com_Submit(document.sysModelingRelationForm, type);
                        }
                    };
                    window.checkMainWhere = function(){
                        var fdInWhere = $("[mdlng-rltn-data=\"fdInWhere\"]").val();
                        if(fdInWhere){
                            fdInWhere = JSON.parse(fdInWhere);
                        }
                        var createType= $("[name='fdSourceType']:checked").val();		//数据来源（明细表）
                        var whereType = $("[name='fdInWhereType']:checked").val();
                        //数据来源是明细表，并且主表查询条件设置的是满足所有条件、满足任一条件，则必须设置查询条件
                        if(createType === "1" && (!fdInWhere || fdInWhere.length <= 0) && whereType != "2"){
                            var $tipContainer = $("[mdlng-rltn-prprty-value='fdInWhere']");
                            $tipContainer.find(".mainModelWhereTip").remove();
                            $tipContainer.append("<div style='color:red;margin-left:16px;' class='mainModelWhereTip'>${lfn:message('sys-modeling-base:relation.configure.query.conditions')}</div>");
                            return false;
                        }
                        return true;
                    }

                });
            //查询条件的显示和隐藏
            Com_AddEventListener(window, "load", function () {
                var whereTypeValue = $('input:radio[name="fdInWhereType"]:checked').val();
                if (whereTypeValue == 2) {
                    $("#fdInWhereTable").css("display", "none");
                }
            })
            $("input[type=radio][name='fdInWhereType']").change(function () {
                var whereTypeValue = $('input:radio[name="fdInWhereType"]:checked').val();
                if (whereTypeValue == 2) {
                    $("#fdInWhereTable").css("display", "none");
                } else {
                    $("#fdInWhereTable").css("display", "block");
                }
            })
            //#134875
            window.onload=function (){
                    $(".fdOutParam_main").trigger("change");
            }
        </script>


    </template:replace>
</template:include>