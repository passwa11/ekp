<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@page import="net.sf.json.JSONObject" %>
<%@page import="com.landray.kmss.sys.modeling.base.util.ListviewEnumUtil" %>
<%@page import="com.landray.kmss.sys.modeling.base.forms.ModelingAppListviewForm" %>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<script type="text/javascript">
    Com_IncludeFile("select.js");
    Com_IncludeFile("dialog.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("formula.js");
    Com_IncludeFile("doclist.js");
    Com_IncludeFile("view.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
    Com_IncludeFile("dialog.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);

</script>

<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/preview.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/views/business/res/sourcePanel.css?s_cache=${LUI_Cache}"/>
<style>


</style>

<div class="model-body-content" id="editContent_resPanel">
    <div class="model-edit">
        <!-- 左侧预览 starts -->
        <div class="model-edit-left">
            <div data-lui-type="sys/modeling/base/resources/js/preview/preview!Preview" id="view_preview"
                 style="display: none">
                <div data-lui-type="sys/modeling/base/resources/js/preview/previewSource!PreviewSource"></div>
                <div data-lui-type="lui/view/render!Template" style="display:none;">
                    <script type="text/config">
 						{
							src : '/sys/modeling/base/views/business/res/preview/resPanelTable.html#'
						}
                    </script>
                </div>
            </div>
        </div>
        <!-- 左侧预览 end -->
        <!-- 右侧编辑 starts -->
        <div class="model-edit-right">
            <div class="model-edit-right-wrap">
                <div class="model-edit-view-title">
                    <p>${lfn:message('sys-modeling-base:respanel.fdViewCfg')}</p>
                    <c:if test="${modelingResourcePanelForm.method_GET=='edit' || modelingResourcePanelForm.method_GET=='editTemplate'}">
                        <div onclick="dosubmit('update');"><bean:message key="button.update"/></div>
                    </c:if>
                    <c:if test="${modelingResourcePanelForm.method_GET=='add'}">
                        <div onclick="dosubmit('save');"><bean:message key="button.save"/></div>
                    </c:if>
                </div>
                <div class="model-edit-view-bar">
                    <div resPanel-bar-mark="basic">${lfn:message('sys-modeling-base:respanel.baseinfo')}</div>
                    <div resPanel-bar-mark="frame">${lfn:message('sys-modeling-base:respanel.panel.frame')}</div>
                    <div resPanel-bar-mark="content">${lfn:message('sys-modeling-base:respanel.panel.chart.content')}</div>
                </div>
                <div class="model-edit-view-content">
                    <div class="model-edit-view-content-wrap">
                        <html:form action="/sys/modeling/base/resPanel.do">

                            <div style="height: 100%;box-sizing: border-box;margin-top: 40px" id="resPanelEdit">
                                <center>
                                    <html:hidden property="fdId"/>
                                    <input type="hidden" name="modelMainId"
                                           value="<c:out value='${modelingResourcePanelForm.modelMainId}' />">
                                    <table class="tb_simple model-view-panel-table" width="100%" id="resPanelEditTable">
                                            <%--基本信息--%>
                                        <tr class="resPanel-bar-content" resPanel-bar-content="basic">
                                            <td>
                                                <table id="resPanelBasicDom" class="tb_simple model-view-panel-table"
                                                       width="100%">
                                                    <tr>
                                                        <td class="td_normal_title title_required"><bean:message
                                                                bundle="sys-modeling-base"
                                                                key="modelingBusiness.fdName"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <div id="_xform_fdName" _xform_type="text"
                                                                 style="width:100%">
                                                                <xform:text property="fdName" style="width:95%"
                                                                            showStatus="edit"
                                                                            validators="required"
                                                                            required="true"/>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="td_normal_title">
                                                            <bean:message bundle="sys-modeling-base"
                                                                          key="modelingBusiness.fdDesc"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <div id="_xform_fdDesc" _xform_type="text"
                                                                 style="width:100%">
                                                                <xform:textarea property="fdDesc" style="width:95%"
                                                                                showStatus="edit"/>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="td_normal_title title_required">
                                                            ${lfn:message('sys-modeling-base:respanel.fdAuthReaders')}
                                                        </td>
                                                    </tr>
                                                    <tr id="fdOrgElementTr">
                                                        <td width=85% class="model-view-panel-table-td">
                                                            <xform:address textarea="true" mulSelect="true"
                                                                           propertyId="authSearchReaderIds"
                                                                           propertyName="authSearchReaderNames"
                                                                           style="width: 95%;height:120px;"></xform:address>
                                                            <br>
                                                            <div style="color: #999999;">${lfn:message('sys-modeling-base:respanel.Empty.everyone.operate')}</div>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                            <%--面板框架--%>
                                        <tr class="resPanel-bar-content" resPanel-bar-content="frame">
                                            <td>
                                                <table id="resPanelTableDom" class="tb_simple model-view-panel-table"
                                                       width="100%">
                                                    <tr>
                                                        <td>
                                                            <p class="model-cfg-tips" title="${lfn:message('sys-modeling-base:respanel.data.from.current.form')}">
                                                               ${lfn:message('sys-modeling-base:respanel.data.from.current.form')}
                                                            </p>
                                                        </td>
                                                    </tr>
                                                    <tr data-lui-local='tableTitle_local'>
                                                        <td class="td_normal_title" title="${lfn:message('sys-modeling-base:respanel.chart.title')}">
                                                            ${lfn:message('sys-modeling-base:respanel.chart.title')}
                                                        </td>
                                                    </tr>
                                                    <tr data-lui-local='tableTitle_local'>
                                                        <td class="model-view-panel-table-td height28">
                                                            <div id="_resPanel_table_title">
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr data-lui-local='tableCol_local'>
                                                        <td class="td_normal_title" title="${lfn:message('sys-modeling-base:respanel.horizontal.axis')}">
                                                            ${lfn:message('sys-modeling-base:respanel.horizontal.axis')}
                                                        </td>
                                                    </tr>
                                                    <tr class="panel_online">
                                                        <td class="model-view-panel-table-td ">
                                                            <div id="_resPanel_table_rowTitle">
                                                                <div class="onlineTitle" title="${lfn:message('sys-modeling-base:respanel.horizontal.axis.type')}">
                                                                   ${lfn:message('sys-modeling-base:respanel.horizontal.axis.type')}
                                                                </div>
                                                                <div class="onlineContext">
                                                                    <select rp-table-mark="category"
                                                                            rp-table-mark-content="_resPanel_table_rowContext"
                                                                            class="inputsgl width100Pe" id="rowCategory"
                                                                            value="dateTime" disabled>
                                                                        <option value="dateTime" selected>${lfn:message('sys-modeling-base:respanel.time')}</option>
                                                                        <option value="field">${lfn:message('sys-modeling-base:relation.field')}</option>
                                                                    </select>
                                                                </div>
                                                                <div class="clearfix" style="margin-bottom: 10px"></div>
                                                                <div id="_resPanel_table_rowContext"
                                                                ></div>
                                                            </div>

                                                        </td>
                                                    </tr>
                                                    <tr data-lui-local='tableRow_local'>
                                                        <td class="td_normal_title" title="${lfn:message('sys-modeling-base:respanel.vertical.axis')}">
                                                            ${lfn:message('sys-modeling-base:respanel.vertical.axis')}
                                                        </td>
                                                    </tr>
                                                    <tr class="panel_online">
                                                        <td class="model-view-panel-table-td ">
                                                            <div id="_resPanel_table_colTitle">
                                                                <div class="onlineTitle" title="${lfn:message('sys-modeling-base:respanel.vertical.axis.type')}">
                                                                    ${lfn:message('sys-modeling-base:respanel.vertical.axis.type')}
                                                                </div>
                                                                <div class="onlineContext">
                                                                    <select rp-table-mark="category" disabled
                                                                            rp-table-mark-content="_resPanel_table_colContext"
                                                                            class="inputsgl width100Pe" id="colCategory"
                                                                            value="field">
                                                                        <option value="dateTime">${lfn:message('sys-modeling-base:respanel.time')}</option>
                                                                        <option value="field" selected>${lfn:message('sys-modeling-base:relation.field')}</option>
                                                                    </select>
                                                                </div>
                                                                <div class="clearfix" style="margin-bottom: 10px"></div>
                                                                <div id="_resPanel_table_colContext"
                                                                ></div>
                                                            </div>

                                                        </td>
                                                    </tr>
                                                    <tr data-lui-local="fdCondition_local">
                                                        <td class="td_normal_title " title="${lfn:message('sys-modeling-base:modelingAppListview.fdCondition')}">
                                                            ${lfn:message('sys-modeling-base:modelingAppListview.fdCondition')}&emsp;
                                                        </td>
                                                    </tr>
                                                    <tr data-lui-local="fdCondition_local">
                                                        <td class="model-view-panel-table-td height28 noEllipsis">
                                                            <xform:dialog propertyName="tableConditionText"
                                                                          htmlElementProperties="placeholder='请选择' data-lui-mark='dialogText'"
                                                                          propertyId="tableCondition"
                                                                          dialogJs="__rpClick(this,'tableCondition');"
                                                            ></xform:dialog>
                                                        </td>
                                                    </tr>
                                                    <tr data-lui-local="fdTableWhereBlock">
                                                        <td class="td_normal_title" title="${lfn:message('sys-modeling-base:modelingAppListview.fdWhereBlock')}">
                                                            ${lfn:message('sys-modeling-base:modelingAppListview.fdWhereBlock')}
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <input type="hidden" name="fdWhereBlock"/>
                                                            <div class="model-panel-table-base" style="margin-bottom:0">
                                                                <table id="table_whereTable"
                                                                       class="tb_simple model-edit-view-oper-content-table"
                                                                       style="width:100%;">
                                                                </table>
                                                            </div>
                                                            <div class="model-data-create"
                                                                 onclick="__rpClick(this,'tableWhere');"
                                                                 data-lui-mark="table_whereTable">
                                                                <div>${lfn:message('sys-modeling-base:button.add')}</div>
                                                            </div>
                                                        </td>
                                                    </tr>

                                                </table>
                                            </td>
                                        </tr>
                                            <%--面板图表内容--%>
                                        <tr class="resPanel-bar-content" resPanel-bar-content="content">
                                            <td>
                                                <table id="resPanelSourceDom_model"
                                                       class="tb_simple model-view-panel-table" width="100%">
                                                    <tr>
                                                        <td>
                                                            <p class="model-cfg-tips">
                                                                ${lfn:message('sys-modeling-base:respanel.data.from.target.form')}
                                                            </p>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="td_normal_title" title="${lfn:message('sys-modeling-base:modelingBusiness.modelTargetName')}">
                                                            ${lfn:message('sys-modeling-base:modelingBusiness.modelTargetName')}
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td height28 noEllipsis">
                                                            <xform:dialog propertyName="modelTargetName"
                                                                          htmlElementProperties="placeholder='${lfn:message('sys-modeling-base:modeling.page.choose')}' data-lui-mark='dialogText'"
                                                                          propertyId="modelTarget"
                                                                          dialogJs="__rpClick(this,'modelTarget');"></xform:dialog>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                            <%--面板图表内容--%>
                                        <tr class="resPanel-bar-content" resPanel-bar-content="content">
                                            <td>
                                                <table id="resPanelSourceDom"
                                                       class="tb_simple model-view-panel-table" width="100%">
                                                    <tr data-lui-local="fdSourceWhereBlock">
                                                        <td class="td_normal_title" title="${lfn:message('sys-modeling-base:modelingAppListview.fdWhereBlock')}">
                                                            ${lfn:message('sys-modeling-base:modelingAppListview.fdWhereBlock')}
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <input type="hidden" name="fdWhereBlock"/>
                                                            <div class="model-panel-table-base" style="margin-bottom:0">
                                                                <table id="source_whereTable"
                                                                       class="tb_simple model-edit-view-oper-content-table"
                                                                       style="width:100%;">
                                                                </table>
                                                            </div>
                                                            <div class="model-data-create"
                                                                 onclick="__rpClick(this,'sourceWhere');"
                                                                 data-lui-mark="source_whereTable">
                                                                <div>${lfn:message('sys-modeling-base:button.add')}</div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr class="panel_online" data-lui-local='contentTable_local'>
                                                        <td>
                                                            <div class="model-view-panel-table-td "
                                                                 data-fm-position="matchCol">
                                                                <div class=" onlineTitle" title="${lfn:message('sys-modeling-base:respanel.horizontal.axis.match.field')}">
                                                                    ${lfn:message('sys-modeling-base:respanel.horizontal.axis.match.field')}
                                                                </div>
                                                                <div id="_resPanel_source_matchRow"
                                                                     class="onlineContext">
                                                                </div>
                                                            </div>

                                                        </td>
                                                    </tr>

                                                    <tr class="panel_online" data-lui-local='contentTable_local'>
                                                        <td>
                                                            <div class="model-view-panel-table-td "
                                                                 data-fm-position="matchRow">
                                                                <div class=" onlineTitle" title="${lfn:message('sys-modeling-base:respanel.Vertical.axis.match.field')}">
                                                                    ${lfn:message('sys-modeling-base:respanel.Vertical.axis.match.field')}
                                                                </div>
                                                                <div id="_resPanel_source_matchCol"
                                                                     class="onlineContext">
                                                                </div>
                                                            </div>
                                                        </td>

                                                    </tr>
                                                    <tr class="panel_online">
                                                        <td>
                                                            <div class="model-view-panel-table-td "
                                                                 data-fm-position="show">
                                                                <div class=" onlineTitle" title="${lfn:message('sys-modeling-base:respanel.show.field')}">
                                                                        ${lfn:message('sys-modeling-base:respanel.show.field')}
                                                                </div>
                                                                <div id="_resPanel_source_show"
                                                                     class="onlineContext"></div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="td_normal_title" title="${lfn:message('sys-modeling-base:respanel.view.penetration')}">
                                                            ${lfn:message('sys-modeling-base:respanel.view.penetration')}
                                                        </td>
                                                    </tr>
                                                    <tr class="panel_online" id="_resPanel_source_through">
                                                        <td>
                                                            <div class="model-view-panel-table-td onlineTitle"
                                                                 style="width: 24%">
                                                                <div id="through_isThrough" class="view_flag_radio "
                                                                     view-flag-radio-value="0">
                                                                    <div class="view_flag_radio_item"
                                                                         view-flag-radio-value="1">
                                                                        &emsp; <i
                                                                            class="view_flag_no view_flag_yes"></i>&nbsp;${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.1')}
                                                                    </div>
                                                                    <div class="view_flag_radio_item"
                                                                         view-flag-radio-value="0">
                                                                        &emsp; <i class="view_flag_no "></i>&nbsp;${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.0')}
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div id="source_through_view" class="onlineContext"
                                                                 style="display: inline-block;width: 55%">
                                                            </div>

                                                        </td>
                                                    </tr>
                                                    <tr data-fm-position="dialog">
                                                        <td class="td_normal_title" title="${lfn:message('sys-modeling-base:respanel.details.show')}">
                                                            ${lfn:message('sys-modeling-base:respanel.details.show')}<span class="dialogDetailPreview"
                                                                      onclick="dialogDetailPreview()">${lfn:message('sys-modeling-base:sys.profile.modeling.preview')}</span>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td height28">
                                                            <div class="model-mask-panel-table-base "
                                                                 id="source_dialog_table">
                                                                <table class="tb_normal field_table " width="100%">
                                                                    <thead>
                                                                    <tr>
                                                                        <td style="width: 30%">${lfn:message('sys-modeling-base:respanel.display.field')}</td>
                                                                        <td style="width: 65%">${lfn:message('sys-modeling-base:respanel.mapping.field')}</td>
                                                                    </tr>

                                                                    </thead>
                                                                    <tbody>
                                                                    <tr>
                                                                        <td>${lfn:message('sys-modeling-base:modelingAppNav.docSubject')}</td>
                                                                        <td>
                                                                            <div class="source_dialog_table_item"
                                                                                 source_dialog_table="title"></div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>${lfn:message('sys-modeling-base:respanel.occupation.time')}</td>
                                                                        <td>
                                                                            <div class="source_dialog_table_item"
                                                                                 source_dialog_table="time"></div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>${lfn:message('sys-modeling-base:respanel.submitter')}</td>
                                                                        <td>
                                                                            <div class="source_dialog_table_item"
                                                                                 source_dialog_table="person"></div>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>${lfn:message('sys-modeling-base:kmReviewMain.docContent')}</td>
                                                                        <td>
                                                                            <div class="source_dialog_table_item"
                                                                                 source_dialog_table="content"></div>
                                                                        </td>
                                                                    </tr>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                            <div class="model-mask-panel-table-base">

                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr data-lui-local='fdColor_local'>
                                                        <td class="td_normal_title" title="${lfn:message('sys-modeling-base:respanel.legend.settings')}">
                                                            ${lfn:message('sys-modeling-base:respanel.legend.settings')}
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <input type="hidden" name="fdColorBlock"/>
                                                            <div class="model-panel-table-base" style="margin-bottom:0">
                                                                <table id="source_colorTable"
                                                                       class="tb_simple model-edit-view-oper-content-table"
                                                                       style="width:100%;">
                                                                </table>
                                                            </div>
                                                            <div class="model-data-create"
                                                                 onclick="__rpClick(this,'sourceColor');"
                                                                 data-lui-mark="source_colorTable">
                                                                <div>${lfn:message('sys-modeling-base:button.add')}</div>
                                                            </div>

                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                    <div id="resPanelTempHtml">
                                        <div id="categoryTime">
                                            <div class="model-content-item" rp-table-mark="default"
                                                 data-lui-local="dayOrWeek_local">
                                                <div class="item-title onlineTitle" title="${lfn:message('sys-modeling-base:respanel.default.display.period')}">
                                                    ${lfn:message('sys-modeling-base:respanel.default.display.period')}</div>
                                                <div class="item-content onlineContext">
                                                    <div class="view_flag_radio" view-flag-radio-value="0"
                                                         style="display: inline-block;">
                                                        <div class="view_flag_radio_item" view-flag-radio-value="1">
                                                            <i class="view_flag_no "></i>${lfn:message('sys-modeling-base:respanel.day')}
                                                        </div>
                                                        <div class="view_flag_radio_item" view-flag-radio-value="0">
                                                            <i class="view_flag_no "></i>${lfn:message('sys-modeling-base:respanel.week')}
                                                        </div>
                                                        <div class="view_flag_radio_item" view-flag-radio-value="2">
                                                            <i class="view_flag_no "></i>${lfn:message('sys-modeling-base:respanel.month')}
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="model-content-item" rp-table-mark="section">
                                                <div class="item-title onlineTitle" title="${lfn:message('sys-modeling-base:respanel.period')}">
                                                    ${lfn:message('sys-modeling-base:respanel.period')}</div>
                                                <div class="item-content onlineContext sectionSelectContent">
                                                    <select rp-table-mark="sectionStart" class="inputsgl width45Pe"
                                                            value="8">
                                                        <option value='0'>0:00</option>
                                                        <option value='1'>1:00</option>
                                                        <option value='2'>2:00</option>
                                                        <option value='3'>3:00</option>
                                                        <option value='4'>4:00</option>
                                                        <option value='5'>5:00</option>
                                                        <option value='6'>6:00</option>
                                                        <option value='7'>7:00</option>
                                                        <option value='8'>8:00</option>
                                                        <option value='9'>9:00</option>
                                                        <option value='10'>10:00</option>
                                                        <option value='11'>11:00</option>
                                                        <option value='12'>12:00</option>
                                                        <option value='13'>13:00</option>
                                                        <option value='14'>14:00</option>
                                                        <option value='15'>15:00</option>
                                                        <option value='16'>16:00</option>
                                                        <option value='17'>17:00</option>
                                                        <option value='18'>18:00</option>
                                                        <option value='19'>19:00</option>
                                                        <option value='20'>20:00</option>
                                                        <option value='21'>21:00</option>
                                                        <option value='22'>22:00</option>
                                                        <option value='23'>23:00</option>
                                                    </select>
                                                    <i style="width: 8%;text-align: center;display: inline-block">-</i>
                                                    <select rp-table-mark="sectionEnd" class="inputsgl width45Pe"
                                                            value="20">
                                                        <option value='0'>0:00</option>
                                                        <option value='1'>1:00</option>
                                                        <option value='2'>2:00</option>
                                                        <option value='3'>3:00</option>
                                                        <option value='4'>4:00</option>
                                                        <option value='5'>5:00</option>
                                                        <option value='6'>6:00</option>
                                                        <option value='7'>7:00</option>
                                                        <option value='8'>8:00</option>
                                                        <option value='9'>9:00</option>
                                                        <option value='10'>10:00</option>
                                                        <option value='11'>11:00</option>
                                                        <option value='12'>12:00</option>
                                                        <option value='13'>13:00</option>
                                                        <option value='14'>14:00</option>
                                                        <option value='15'>15:00</option>
                                                        <option value='16'>16:00</option>
                                                        <option value='17'>17:00</option>
                                                        <option value='18'>18:00</option>
                                                        <option value='19'>19:00</option>
                                                        <option value='20'>20:00</option>
                                                        <option value='21'>21:00</option>
                                                        <option value='22'>22:00</option>
                                                        <option value='23'>23:00</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="model-content-item">
                                                <div class="item-title onlineTitle" title="${lfn:message('sys-modeling-base:respanel.time.interval')}">
                                                    ${lfn:message('sys-modeling-base:respanel.time.interval')}</div>
                                                <div class="item-content onlineContext">
                                                    <input rp-table-mark="splitNumber" name="splitNumber"
                                                           class="inputsgl input-time" value="1" step="1" max="23"
                                                           min="1"
                                                           type="number" style="width: 80%; float: left">
                                                    <select rp-table-mark="splitUnit" class="inputsgl input-unit"
                                                            style="width: 20%;" value="h">
                                                        <option value="h" selected>${lfn:message('sys-modeling-base:respanel.hour')}</option>
                                                        <option value="m">${lfn:message('sys-modeling-base:respanel.minute')}</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="categoryField">
                                            <div class="model-content-item" rp-table-mark="categoryFieldText">
                                                <div class="onlineTitle" title="${lfn:message('sys-modeling-base:respanel.display.value')}">
                                                    ${lfn:message('sys-modeling-base:respanel.display.value')}
                                                </div>
                                                <div class="onlineContext">
                                                </div>
                                            </div>
                                            <div class="model-content-item" rp-table-mark="categoryFieldValue">
                                                <div class="onlineTitle" title="${lfn:message('sys-modeling-base:respanel.actual.value')}">
                                                    ${lfn:message('sys-modeling-base:respanel.actual.value')}
                                                </div>
                                                <div class="onlineContext">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </center>
                                <br>
                            </div>
                            <input type="hidden" name="modelMainId"
                                   value="<c:out value='${modelingResourcePanelForm.modelMainId}' />">
                            <input type="hidden" name="modelTargetId"
                                   value="<c:out value='${modelingResourcePanelForm.modelTargetId}' />">
                            <input type="hidden" name="docCreateTime"
                                   value="<c:out value='${modelingResourcePanelForm.docCreateTime}' />">
                            <input type="hidden" name="docCreateTime"
                                   value="<c:out value='${modelingResourcePanelForm.docCreateTime}' />">
                            <input type="hidden" name="docCreatorId"
                                   value="<c:out value='${modelingResourcePanelForm.docCreatorId}' />">
                            <input type="hidden" name="fdConfig"
                                   value="<c:out value='${modelingResourcePanelForm.fdConfig}' />">
                            <html:hidden property="method_GET"/>
                        </html:form>
                    </div>
                </div>
            </div>
        </div>
        <!-- 右侧编辑 end -->
    </div>
</div>
<script type="text/javascript">
    $KMSSValidation();
    window.colorChooserHintInfo = {
        cancelText: '取消',
        chooseText: '确定'
    };
    Com_IncludeFile("spectrum.js", Com_Parameter.ContextPath + 'resource/js/colorpicker/', 'js', true);
    Com_IncludeFile("spectrum.css", Com_Parameter.ContextPath + 'resource/js/colorpicker/css/', 'css', true);
    Com_IncludeFile("spectrumColorPicker.js", Com_Parameter.ContextPath + 'sys/modeling/base/views/business/res/', 'js', true);
    Com_IncludeFile("resPanelTable.js", Com_Parameter.ContextPath + "sys/modeling/base/views/business/res/preview/", "js", true);

    function returnListPage() {
        var url = Com_Parameter.ContextPath + 'sys/modeling/base/views/business/resourcePanel/resPanel_index.jsp?fdModelId=${modelingResourcePanelForm.modelMainId}';
        var iframe = window.parent.document.getElementById("trigger_iframe");
        $(iframe).attr("src", url);
        return false;
    }

    seajs.use(["sys/modeling/base/views/business/res/resPanel", "lui/dialog", "lui/jquery", 'lui/topic', "sys/modeling/base/formlog/res/mark/resPanelFMMark"]
        , function (resPanel, dialog, $, topic, resPanelFMMark) {
            //窗口大小自适应-------------------------------
            function onResizeFitWindow() {
                var height = $('body').height();
                if ("${param.isInDialog}") {
                    $('.model-edit-view-title:eq(0)').hide()
                } else {
                    height = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true) - 25;
                }

                $("body", parent.document).find('#trigger_iframe').height(height);
                $(".model-edit-right-wrap .model-edit-view-content").height(height - 80);
                $(".model-edit-left-wrap .model-edit-view-content").height(height - 60);
                $("body", parent.document).css("overflow", "hidden");
            }

            $(window).resize(function () {
                onResizeFitWindow();
            });
            onResizeFitWindow();
            //预览加载完毕事件
            topic.subscribe('preview_load_finish', function (ctx) {
                onResizeFitWindow();
            });

            //窗口大小自适应-------------------------------end

            function init() {
                var cfg = {
                    xformId: "${xformId}",
                    flowInfo:${flowInfo},
                    modelMainId: "${modelingResourcePanelForm.modelMainId}",
                    modelTargetId: "${modelingResourcePanelForm.modelTargetId}",
                    widgets: ${widgets},
                    fdConfig: $("[name='fdConfig']").val()
                };
                window.resPanel = new resPanel.ResPanel(cfg);
                window.resPanel.startup();
                window.__rpClick = window.resPanel.__rpClick;
                window.fmmark = new resPanelFMMark.ResPanelFMMark({fdId: "${modelingResourcePanelForm.fdId}"});
                fmmark.startup();
            }

            init();

            window.onclose = function () {
                $dialog.hide(null);
            };
            window.dosubmit = function (type) {
                var resPanelConfig = window.resPanel.getKeyData();
                console.debug("resPanelConfig::", resPanelConfig);
                var validateResult = window.ResPanelValidate.validate(resPanelConfig)
                if (!validateResult) {
                    $("[name='fdConfig']").val(JSON.stringify(resPanelConfig));
                    $("[name='modelTargetId']").val(resPanelConfig.source.model.id);
                    Com_Submit(document.modelingResourcePanelForm, type);
                }else {
                    dialog.alert(validateResult)
                }
            };

        })

</script>
<%@ include file="/resource/jsp/edit_down.jsp" %>