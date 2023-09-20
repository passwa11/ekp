<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@page import="net.sf.json.JSONObject" %>
<%@page import="com.landray.kmss.sys.modeling.base.util.ListviewEnumUtil"%>
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
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/query.css?s_cache=${LUI_Cache}" />
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/views/business/res/gantt.css?s_cache=${LUI_Cache}"/>
<style>

    .model-view-panel-table .model-view-panel-table-td {
        padding-left: 0;
        padding-right: 0;
        padding-bottom: 0;
        padding: 0 20px 20px;
    }
    #editContent_gantt textarea,
    #editContent_gantt .input input,
    #editContent_gantt .inputsgl,
    #editContent_gantt select {
        color: #1b83d8;
        padding-left: 4px;
        font-size: 12px;
    }
    .model-edit-view-oper {
        width: auto;
        margin-bottom: 20px;
    }
</style>

<div class="model-body-content" id="editContent_gantt">
    <div class="model-edit">
        <!-- 左侧预览 starts -->
        <div class="model-edit-left" style="overflow: auto;padding-bottom:50px">
            <div data-lui-type="sys/modeling/base/resources/js/preview/preview!Preview" id="view_preview"
                 style="display: none">
                <div data-lui-type="sys/modeling/base/resources/js/preview/previewSource!PreviewSource"></div>
                <div data-lui-type="lui/view/render!Template" style="display:none;">
                    <script type="text/config">
 						{
							src : '/sys/modeling/base/views/business/res/preview/ganttTable.html#'
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
                    <c:if test="${modelingGanttForm.method_GET=='edit' || modelingGanttForm.method_GET=='editTemplate'}">
                        <div onclick="dosubmit('update');"><bean:message key="button.update"/></div>
                    </c:if>
                    <c:if test="${modelingGanttForm.method_GET=='add'}">
                        <div onclick="dosubmit('save');"><bean:message key="button.save"/></div>
                    </c:if>
                </div>
                <div class="model-edit-view-bar">
                    <div gantt-bar-mark="basic">${lfn:message('sys-modeling-base:respanel.baseinfo')}</div>
                    <div gantt-bar-mark="frame">${lfn:message('sys-modeling-base:Gantt.display.set')}</div>
                </div>
                <div class="model-edit-view-content">
                    <div class="model-edit-view-content-wrap">
                        <html:form action="/sys/modeling/base/gantt.do">

                            <ta style="height: 100%;box-sizing: border-box;margin-top: 40px" id="resPanelEdit">
                                <center>
                                    <html:hidden property="fdId"/>
                                    <input type="hidden" name="modelMainId"
                                           value="<c:out value='${modelingGanttForm.modelMainId}' />">
                                    <table class="tb_simple model-view-panel-table" width="100%" id="resPanelEditTable">
                                            <%--基本信息--%>
                                        <tr class="gantt-bar-content" gantt-bar-content="basic" >
                                            <td>
                                                <table id="resPanelBasicDom" class="tb_simple model-view-panel-table"
                                                       width="100%">
                                                    <tr data-lui-local='tableName_local'>
                                                        <td class="td_normal_title title_required">
                                                            <bean:message bundle="sys-modeling-base" key="modelingBusiness.fdName"/>
                                                        </td>
                                                    </tr>
                                                    <tr data-lui-local='tableName_local'>
                                                        <td class="model-view-panel-table-td">
                                                            <div id="_xform_fdName" _xform_type="text" <%--onclick="__rpClick(this,'tableNameOnClick');"--%>
                                                                 style="width:100%">
                                                                <xform:text property="fdName" style="width:95%"
                                                                            htmlElementProperties="id='tableNameText'"
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
                                                            ${lfn:message('sys-modeling-base:modelingAppNav.fdAuthReaders')}
                                                        </td>
                                                    </tr>
                                                    <tr id="fdOrgElementTr">
                                                        <td width=85% class="model-view-panel-table-td">
                                                            <xform:address textarea="true" mulSelect="true"
                                                                           propertyId="authSearchReaderIds"
                                                                           propertyName="authSearchReaderNames"
                                                                           style="width: 95%;height:120px;"></xform:address>
                                                            <div style="color: #999999;">${lfn:message('sys-modeling-base:respanel.Empty.everyone.operate')}</div>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                            <%--面板框架--%>
                                        <tr class="gantt-bar-content" gantt-bar-content="frame">
                                            <td>
                                                <table id="resPanelTableDom" class="tb_simple model-view-panel-table"
                                                       width="100%">
                                                    <!-- 图表设置 -->
                                                    <tr data-lui-local='tableTitle_local'>
                                                        <td class="td_normal_title" title="${lfn:message('sys-modeling-base:modeling.app.setting.rpt')}">
                                                            <div class="model-edit-view-oper" data-lui-position='fdOperation-!{index}'>
                                                                <div class="model-edit-view-oper-head">
                                                                    <div class="model-edit-view-oper-head-title">
                                                                        <div onclick="changeToOpenOrClose(this)">
                                                                            <i class="open"></i>
                                                                        </div>
                                                                        <span>${lfn:message('sys-modeling-base:modeling.app.setting.rpt')}</span>
                                                                    </div>
                                                                    <div class="model-edit-view-oper-head-item" style="padding-top:0px;">
                                                                    </div>
                                                                </div>
                                                                <div class="model-edit-view-oper-content listview-content" onclick="switchSelectPosition(this,'right')" style="padding: 0;">
                                                                    <!-- 显示项 -->
                                                                    <div class='model-edit-view-oper-content-item first-item' style="width: 100%;">
                                                                        <table style="width: 95%;margin-top: 10px;">
                                                                            <tr data-lui-local="fdDisplay_local">
                                                                                <td class="model-view-panel-table-td height28">
                                                                                    <div class=" " data-fm-position="show">
                                                                                        <div class="onlineTitle"  data-lui-position='fdDisplay' title="${lfn:message('sys-modeling-base:modelingAppListview.fdDisplay')}" style="margin: 0 20px;float: left"><p style="line-height: 30px">&emsp;${lfn:message('sys-modeling-base:modelingAppListview.fdDisplay')}</p></div>
                                                                                        <div class="onlineContext" style="width: 70%;float: left;">
                                                                                            <xform:dialog propertyName="fdDisplayText" htmlElementProperties="placeholder='${lfn:message('sys-modeling-base:modeling.page.choose')}' data-lui-position='fdDisplay'" propertyId="fd_display" dialogJs="__rpClick(this,'fdDisplay');">
                                                                                            </xform:dialog>
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </div>
                                                                    <!-- 图表内容 -->
                                                                    <div class='model-edit-view-oper-content-item first-item'>
                                                                            <table  class="tb_simple model-view-panel-table" width="100%" id="view_content_table">
                                                                                <tr>
                                                                                    <td class="td_normal_title " title="${lfn:message('sys-modeling-base:Gantt.chart.content')}">
                                                                                        ${lfn:message('sys-modeling-base:Gantt.chart.content')}&emsp;
                                                                                    </td>
                                                                                </tr>
                                                                            </table>
                                                                            <table class="model_view_content" data-lui-local="viewContent_local" data-lui-position="viewContent" onclick="__rpClick(this,'viewContent');">
                                                                                <tr>
                                                                                    <td class="model-view-panel-table-td height28">
                                                                                            <div class=" " data-fm-position="show">
                                                                                                <div class="onlineTitle" data-lui-position="fdStartTime" title="${lfn:message('sys-modeling-base:Gantt.start.time')}" style="margin: 0 20px;float: left"><p style="line-height: 30px">${lfn:message('sys-modeling-base:Gantt.start.time')}</p></div>
                                                                                                <div id="_gantt_start_time" class="onlineContext" style="width: 70%;float: left;"></div>
                                                                                            </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="model-view-panel-table-td height28">
                                                                                            <div class="" data-fm-position="show">
                                                                                                <div class="onlineTitle"  data-lui-position="fdEndTime"  title="${lfn:message('sys-modeling-base:Gantt.end.time')}" style="margin: 0 20px;float: left"><p style="line-height: 30px">${lfn:message('sys-modeling-base:Gantt.end.time')}</p></div>
                                                                                                <div id="_gantt_end_time" class="onlineContext" style="width: 70%;float: left;"></div>
                                                                                            </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="model-view-panel-table-td height28">
                                                                                        <div class=""
                                                                                             data-fm-position="show">
                                                                                            <div class="onlineTitle" data-lui-position="fdProgress" title="${lfn:message('sys-modeling-base:Gantt.schedule')}" style="margin: 0 20px;float: left"><p style="line-height: 30px">&emsp;&emsp;${lfn:message('sys-modeling-base:Gantt.schedule')}</p></div>
                                                                                            <div id="_gantt_progress" class="onlineContext" style="width: 70%;float: left;position: relative;"></div>
                                                                                        </div>
                                                                                        <div class="model-mask-panel-table-base">
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="model-view-panel-table-td height28">
                                                                                        <div class=""
                                                                                             data-fm-position="show">
                                                                                            <div class="onlineTitle" data-lui-position="fdShow" title="${lfn:message('sys-modeling-base:respanel.show.field')}" style="margin: 0 20px;float: left"><p style="line-height: 30px">${lfn:message('sys-modeling-base:respanel.show.field')}</p></div>
                                                                                            <div id="_gantt_show_field" class="onlineContext" style="width: 70%;float: left;"></div>
                                                                                        </div>
                                                                                        <div class="model-mask-panel-table-base">
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="model-view-panel-table-td height28">
                                                                                        <div class=""
                                                                                             data-fm-position="show">
                                                                                            <div class="onlineTitle" title="${lfn:message('sys-modeling-base:Gantt.overview.card')}" style="margin: 0 20px;float: left;"><p style="line-height: 30px">${lfn:message('sys-modeling-base:Gantt.overview.card')}</p></div>
                                                                                            <div id="dialogDetailPreview"  onclick="dialogDetailPreview()" style="float: left;text-align: center;width: auto;border: 1px solid #DDDDDD;line-height: 28px;width:90px">${lfn:message('sys-modeling-base:sys.profile.modeling.preview')}</div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                             </table>
                                                                            <!-- 默认状态 -->
                                                                            <div class='model-edit-view-oper-content-item first-item' style="margin-top: 20px;">
                                                                                <table  class="tb_simple model-view-panel-table" width="100%" id="default_status_table">
                                                                                    <tr data-lui-local="">
                                                                                        <td class="td_normal_title " title="${lfn:message('sys-modeling-base:Gantt.default.state')}">
                                                                                            ${lfn:message('sys-modeling-base:Gantt.default.state')}&emsp;
                                                                                        </td>
                                                                                    </tr>
                                                                                </table>
                                                                                <table  style="border: 1px solid #DDDDDD;width: 90%;margin-top: 10px;margin-left: 20px;margin-bottom: 20px;padding: 10px 0;">
                                                                                    <tr>
                                                                                        <td class="model-view-panel-table-td height28">
                                                                                            <div class="" data-fm-position="show">
                                                                                                <div class="onlineTitle" title="${lfn:message('sys-modeling-base:Gantt.color.scheme')}" style="margin: 0 20px;float: left"><p style="line-height: 30px">${lfn:message('sys-modeling-base:Gantt.color.scheme')}</p></div>
                                                                                                <div class="onlineContext" style="width: 70%;float: left;">
                                                                                                    <select id="colorProgrammeSelect" class="inputsgl width100Pe">
                                                                                                        <option value='1'>${lfn:message('sys-modeling-base:Gantt.classic.color')}</option>
                                                                                                        <option value='2'>${lfn:message('sys-modeling-base:Gantt.mighty.sand.sea')}</option>
                                                                                                        <option value='3'>${lfn:message('sys-modeling-base:Gantt.quiet.elegant')}</option>
                                                                                                        <option value='4'>${lfn:message('sys-modeling-base:Gantt.sweet.words')}</option>
                                                                                                        <option value='5'>${lfn:message('sys-modeling-base:Gantt.inclusive.all.rivers')}</option>
                                                                                                        <option value='6'>${lfn:message('sys-modeling-base:Gantt.Tree.lined')}</option>
                                                                                                    </select>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="model-mask-panel-table-base">

                                                                                            </div>
                                                                                        </td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td class="model-view-panel-table-td height28"  data-lui-local="timeDimension_local">
                                                                                            <div class=""
                                                                                                 data-fm-position="show">
                                                                                                <div class="onlineTitle" title="${lfn:message('sys-modeling-base:Gantt.time.dimension')}" style="margin: 0 20px;float: left"><p style="line-height: 30px">${lfn:message('sys-modeling-base:Gantt.time.dimension')}</p></div>
                                                                                                <div class="onlineContext" style="width: 70%;float: left;">
                                                                                                    <select  id="timeDimensionSelect" class="inputsgl width100Pe">
                                                                                                        <option value='date'>${lfn:message('sys-modeling-base:respanel.day')}</option>
                                                                                                        <option value='month'>${lfn:message('sys-modeling-base:respanel.month')}</option>
                                                                                                        <option value='quarter'>${lfn:message('sys-modeling-base:Gantt.season')}</option>
                                                                                                        <option value='year'>${lfn:message('sys-modeling-base:Gantt.year')}</option>
                                                                                                    </select>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="model-mask-panel-table-base">

                                                                                            </div>
                                                                                        </td>
                                                                                    </tr>
                                                                                </table>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                        </td>
                                                    </tr>
                                                    <!-- 操作设置 -->
                                                    <tr>
                                                        <td class="td_normal_title" title="${lfn:message('sys-modeling-base:Gantt.operation.set')}" >
                                                            <div class="model-edit-view-oper" data-lui-position='fdOperation-!{index}' style="margin-bottom: 0px;">
                                                                <div class="model-edit-view-oper-head">
                                                                    <div class="model-edit-view-oper-head-title">
                                                                        <div onclick="changeToOpenOrClose(this)">
                                                                            <i class="open"></i>
                                                                        </div>
                                                                        <span>${lfn:message('sys-modeling-base:Gantt.operation.set')}</span>
                                                                    </div>
                                                                    <div class="model-edit-view-oper-head-item" style="padding-top:0px;">
                                                                    </div>
                                                                </div>
                                                                <div class="model-edit-view-oper-content listview-content" onclick="switchSelectPosition(this,'right')">
                                                                    <!-- 筛选项 -->
                                                                    <div class='model-edit-view-oper-content-item first-item' data-lui-local='fdCondition_local' style="width: 95%;padding: 10px 0;">
                                                                        <table  class="tb_simple model-view-panel-table" width="100%" id="fdCondition_local_table">
                                                                           <div class="" data-fm-position="show" >
                                                                               <div class="onlineTitle" data-lui-position="fdCondition" title="${lfn:message('sys-modeling-base:modelingAppListview.fdCondition')}" style="margin: 0 20px;float: left"><p style="line-height: 30px;">&emsp;${lfn:message('sys-modeling-base:modelingAppListview.fdCondition')}</p></div>
                                                                               <div class="onlineContext" style="width: 70%;float: left;">
                                                                                   <xform:dialog propertyName="tableConditionText"
                                                                                                 htmlElementProperties="placeholder='${lfn:message('sys-modeling-base:modeling.page.choose')}' data-lui-mark='dialogText'"
                                                                                                 propertyId="tableCondition"
                                                                                                 dialogJs="__rpClick(this,'tableCondition');"
                                                                                   ></xform:dialog>
                                                                               </div>
                                                                           </div>
                                                                           <div class="model-mask-panel-table-base">
                                                                           </div>
                                                                        </table>
                                                                    </div>
                                                                    <!-- 排序设置 -->
                                                                    <div class='model-edit-view-oper-content-item first-item' style="width: 95%;">
                                                                        <table  class="tb_simple model-view-panel-table" width="100%" id="fdOrderBy_local_table" data-lui-local='fdOrderBy_local'>
                                                                               <tr>
                                                                                   <td class="model-view-panel-table-td height28" style="padding: 10px 0 !important;">
                                                                                       <div class=""
                                                                                            data-fm-position="show">
                                                                                           <div class="onlineTitle" title="${lfn:message('sys-modeling-base:modelingAppListview.fdOrderBy')}" data-lui-position="fdOrderBy" style="float: left;margin: 0 20px;"><p style="line-height: 30px">${lfn:message('sys-modeling-base:modelingAppListview.fdOrderBy')}</p></div>
                                                                                           <div class="onlineContext" style="width: 70%;float: left;">
                                                                                               <input type="hidden" name="fdOrderBy"/>
                                                                                               <div class="model-panel-table-base" style="margin-bottom:0">
                                                                                                   <table id="xform_main_data_orderbyTable" class="tb_simple model-edit-view-oper-content-table" style="width:100%;">
                                                                                                   </table>
                                                                                               </div>
                                                                                               <div class="model-data-create" onclick="xform_main_data_addOrderbyItem();">
                                                                                                   <div>${lfn:message('sys-modeling-base:button.add')}</div>
                                                                                               </div>
                                                                                           </div>
                                                                                       </div>
                                                                                       <div class="model-mask-panel-table-base">
                                                                                       </div>
                                                                                   </td>
                                                                               </tr>
                                                                        </table>
                                                                    </div>
                                                                    <!-- 业务操作 -->
                                                                    <div class='model-edit-view-oper-content-item first-item' style="margin-top: 20px;" data-lui-local='fdOperation_local'>
                                                                        <table  class="tb_simple model-view-panel-table" width="100%"  data-lui-local='fdOperation_local'>
                                                                            <tr data-lui-local="">
                                                                                <td class="td_normal_title " title="${lfn:message('sys-modeling-base:modelingGantt.listOperation')}">
                                                                                    ${lfn:message('sys-modeling-base:modelingGantt.listOperation')}&emsp;
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                        <table  style="border: 1px solid #DDDDDD;width: 90%;margin-top: 10px;margin-left: 20px;margin-bottom: 20px;padding: 10px 0;" >
                                                                            <tr>
                                                                                <td class="model-view-panel-table-td">
                                                                                    <div class="model-opt-panel-table-base model-panel-table-base" style="margin-bottom:0px;" id="_xform_listOperationIds" _xform_type="dialog">
                                                                                        <c:set var="listOperationNames" value="${modelingGanttForm.listOperationNames }"></c:set>
                                                                                        <c:set var="listOperationIds" value="${modelingGanttForm.listOperationIds }"></c:set>
                                                                                        <%
                                                                                            //解析names
                                                                                            String listOperationNames = (String)pageContext.getAttribute("listOperationNames");
                                                                                            String listOperationIds = (String)pageContext.getAttribute("listOperationIds");
                                                                                            if(StringUtil.isNotNull(listOperationIds)){
                                                                                                String[] listOperationNameArr = listOperationNames.split(";");
                                                                                                String[] listOperationIdArr = listOperationIds.split(";");
                                                                                                pageContext.setAttribute("listOperationNameArr", listOperationNameArr);
                                                                                                pageContext.setAttribute("listOperationIdArr", listOperationIdArr);
                                                                                            }
                                                                                        %>
                                                                                        <table id="operationTable" class="tb_simple model-edit-view-oper-content-table" width="100%">
                                                                                                <%-- 基准行，KMSS_IsReferRow = 1 --%>
                                                                                            <tr  style="display:none;" KMSS_IsReferRow="1" class="operationTr">
                                                                                                <td>
                                                                                                    <div class="model-edit-view-oper" data-lui-position='fdOperation-!{index}' onclick="switchSelectPosition(this,'right')">
                                                                                                        <div class="model-edit-view-oper-head">
                                                                                                            <div class="model-edit-view-oper-head-title">
                                                                                                                <div onclick="changeToOpenOrClose(this)">
                                                                                                                    <i class="open"></i>
                                                                                                                </div>
                                                                                                                <span>${lfn:message('sys-modeling-base:listview.action.item')}<span class='title-index'>!{index}</span></span>
                                                                                                            </div>
                                                                                                            <div class="model-edit-view-oper-head-item" style="padding-top:0px;">
                                                                                                                <div class="del"
                                                                                                                     onclick="xform_main_data_beforeDelOperationRow();updateRowAttr(0);DocList_DeleteRow();updateRowAttr();">
                                                                                                                    <i></i>
                                                                                                                </div>
                                                                                                                <div style="display:none" class="down"
                                                                                                                     onclick="DocList_MoveRow(1);updateRowAttr(1);">
                                                                                                                    <i></i>
                                                                                                                </div>
                                                                                                                <div class="up"
                                                                                                                     onclick="DocList_MoveRow(-1);updateRowAttr(-1);">
                                                                                                                    <i></i>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                        <div class="model-edit-view-oper-content listview-content">
                                                                                                            <div class='model-edit-view-oper-content-item first-item last-item'>
                                                                                                                <div class='item-title'>${ lfn:message('sys-modeling-base:modelingAppListview.operationButton') }</div>
                                                                                                                <div class='item-content'>
                                                                                                                    <!-- <p onclick="xform_main_data_operationDialog()"  class="highLight listOperationName label">&nbsp;</p> -->
                                                                                                                        <%-- 操作--%>
                                                                                                                    <div class='operation' id="_xform_listOpers_Form[!{index}].fdOperationId" _xform_type="dialog" style="width:100%">
                                                                                                                        <xform:dialog style="width:100%;vertical-align: middle;" subject="${ lfn:message('sys-modeling-base:modelingAppListview.operationButton') }" propertyId="listOperationIdArr[!{index}]" propertyName="listOperationNameArr[!{index}]" showStatus="edit" validators=" required">
                                                                                                                            xform_main_data_operationDialog()
                                                                                                                        </xform:dialog>
                                                                                                                    </div>
                                                                                                                    <div id="listOperationTipsArr[!{index}]">
                                                                                                                    </div>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                            <!-- <input type="hidden" name="listOperationIdArr[!{index}]">
                                                                                                            <input type="hidden" name="listOperationNameArr[!{index}]"> -->
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </td>
                                                                                            </tr>
                                                                                                <%-- 内容行 --%>
                                                                                            <c:forEach items="${listOperationNameArr}" var="listOperationName" varStatus="vstatus">
                                                                                                <tr KMSS_IsContentRow="1" class="operationTr">
                                                                                                    <td>
                                                                                                        <div class="model-edit-view-oper" data-lui-position='fdOperation-${vstatus.index}' onclick="switchSelectPosition(this,'right')">
                                                                                                            <div class="model-edit-view-oper-head">
                                                                                                                <div class="model-edit-view-oper-head-title">
                                                                                                                    <div onclick="changeToOpenOrClose(this)">
                                                                                                                        <i class="open"></i>
                                                                                                                    </div>
                                                                                                                    <span>${listOperationName }</span>
                                                                                                                </div>
                                                                                                                <div class="model-edit-view-oper-head-item " style="padding-top:0px;">
                                                                                                                    <div class="del"
                                                                                                                         onclick="xform_main_data_beforeDelOperationRow();updateRowAttr(0);DocList_DeleteRow();updateRowAttr();">
                                                                                                                        <i></i>
                                                                                                                    </div>
                                                                                                                    <div class="down"
                                                                                                                         onclick="DocList_MoveRow(1);updateRowAttr(1);">
                                                                                                                        <i></i>
                                                                                                                    </div>
                                                                                                                    <div class="up"
                                                                                                                         onclick="DocList_MoveRow(-1);updateRowAttr(-1);">
                                                                                                                        <i></i>
                                                                                                                    </div>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                            <div class="model-edit-view-oper-content listview-content">
                                                                                                                <div class='model-edit-view-oper-content-item first-item last-item'>
                                                                                                                    <div class='item-title'>${lfn:message('sys-modeling-base:modelingAppListview.operationButton')}</div>
                                                                                                                    <div class='item-content'>
                                                                                                                            <%-- 操作--%>
                                                                                                                        <div class='operation' id="_xform_listOpers_Form[!{index}].fdOperationId" _xform_type="dialog" style="width:100%">
                                                                                                                            <xform:dialog style="width:100%;vertical-align: middle;" propertyId="listOperationIdArr[${vstatus.index}]" propertyName="listOperationNameArr[${vstatus.index}]" idValue="${listOperationIdArr[vstatus.index] }" nameValue="${listOperationName }" showStatus="edit" validators=" required">
                                                                                                                                xform_main_data_operationDialog()
                                                                                                                            </xform:dialog>
                                                                                                                        </div>
                                                                                                                        <div id="listOperationTipsArr[${vstatus.index}]">
                                                                                                                                <%-- 操作按钮提示需开启机制--%>
                                                                                                                            <c:forEach items="${sysModelingOperationList}" var="sysModelingOperation" varStatus="vstatus_">
                                                                                                                                <c:if test="${(listOperationIdArr[vstatus.index] eq sysModelingOperation.fdId)
                                                                                                                                             and ((sysModelingOperation.fdDefType eq '8') or (sysModelingOperation.fdDefType eq '9')
                                                                                                                                             or (sysModelingOperation.fdDefType eq '10') or (sysModelingOperation.fdDefType eq '11')
                                                                                                                                             or (sysModelingOperation.fdDefType eq null and sysModelingOperation.fdOperationScenario eq '1')
                                                                                                                                             ) }">
                                                                                                                                    <div class="operateTips">${lfn:message('sys-modeling-base:modeling.model.mechanism.tips')}</div>
                                                                                                                                </c:if>
                                                                                                                            </c:forEach>
                                                                                                                         </div>
                                                                                                                    </div>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </td>
                                                                                                </tr>
                                                                                            </c:forEach>
                                                                                        </table>
                                                                                        <input name="listOperationIds" type="hidden" value="${modelingGanttForm.listOperationIds }">
                                                                                        <input name="listOperationNames" type="hidden" value="${modelingGanttForm.listOperationNames }">
                                                                                        <input name="listOperationIds_last" type="hidden" value="${modelingGanttForm.listOperationIds }">
                                                                                        <input name="listOperationNames_last" type="hidden" value="${modelingGanttForm.listOperationNames }">
                                                                                    </div>
                                                                                    <div class="model-data-create" onclick="xform_main_data_addOperation('operationTable');">
                                                                                        <div>${lfn:message('sys-modeling-base:button.add')}</div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <!-- 数据设置 -->
                                                    <tr>
                                                        <td class="td_normal_title" title="${lfn:message('sys-modeling-base:Gantt.data.set')}">
                                                            <div class="model-edit-view-oper" >
                                                                <div class="model-edit-view-oper-head">
                                                                    <div class="model-edit-view-oper-head-title">
                                                                        <div onclick="changeToOpenOrClose(this)">
                                                                            <i class="open"></i>
                                                                        </div>
                                                                        <span>${lfn:message('sys-modeling-base:Gantt.data.set')}</span>
                                                                    </div>
                                                                    <div class="model-edit-view-oper-head-item" style="padding-top:0px;">
                                                                    </div>
                                                                </div>
                                                                <div class="model-edit-view-oper-content listview-content" onclick="switchSelectPosition(this,'right')">
                                                                    <!-- 查询规则 -->
                                                                    <div class='model-edit-view-oper-content-item first-item' data-lui-local='fdOrderBy_local' style="width: 100%">
                                                                        <table  class="tb_simple model-view-panel-table" width="100%" id="fdOrderBy_local_table1" style="margin-top: 10px;padding-top: 20px;">
                                                                                <tr>
                                                                                    <td class="model-view-panel-table-td">
                                                                                        <div class="model-query">
                                                                                            <div class="model-query-wrap">
                                                                                                <div class="model-query-tab">
                                                                                                    <ul class="clearfix">
                                                                                                        <li class="active"><span>${lfn:message('sys-modeling-base:modeling.custom.query')}</span></li>
                                                                                                        <li><span>${lfn:message('sys-modeling-base:modeling.builtIn.query')}</span></li>
                                                                                                    </ul>
                                                                                                </div>
                                                                                                <div class="model-query-content">
                                                                                                    <!-- 自定义查询 -->
                                                                                                    <div class="model-query-content-cust model-query-cont">
                                                                                                        <div class="model-query-content-rule clearfix">
                                                                                                            <p>${lfn:message('sys-modeling-base:listview.query.rules')}</p>
                                                                                                            <div>
                                                                                                                <label style="margin-left:10px;">
                                                                                                                    <input type="radio" value="0" name="fdCustomWhereType"
                                                                                                                           <c:if test="${fdCustomWhereType eq '0' or empty fdCustomWhereType }">checked</c:if>/>
                                                                                                                    ${lfn:message('sys-modeling-base:relation.meet.all.conditions')}
                                                                                                                </label>
                                                                                                                <label>
                                                                                                                    <input type="radio" value="1" name="fdCustomWhereType"
                                                                                                                           <c:if test="${fdCustomWhereType eq '1'}">checked</c:if> />
                                                                                                                    ${lfn:message('sys-modeling-base:relation.meet.any.conditions')}
                                                                                                                </label>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                        <div class="model-query-content-add">
                                                                                                            <input type="hidden" name="fdWhereBlock"/>
                                                                                                            <div class="model-panel-table-base" style="margin-bottom:0">
                                                                                                                <table id="xform_main_data_whereTable" class="tb_simple model-edit-view-oper-content-table" style="width:100%;">
                                                                                                                </table>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                        <div class="model-data-create" onclick="xform_main_data_addWhereItem(null, this, '0');">
                                                                                                            <div>${lfn:message('sys-modeling-base:button.add')}</div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <!-- 内置查询 -->
                                                                                                    <div class="model-query-content-sys model-query-cont">
                                                                                                        <div class="model-query-content-rule clearfix">
                                                                                                            <p>${lfn:message('sys-modeling-base:listview.query.rules')}</p>
                                                                                                            <div>
                                                                                                                <label style="margin-left:10px;">
                                                                                                                    <input type="radio"  checked/>
                                                                                                                    ${lfn:message('sys-modeling-base:relation.meet.all.conditions')}
                                                                                                                </label>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                        <div class="model-query-content-add">
                                                                                                            <div class="model-panel-table-base" style="margin-bottom:0">
                                                                                                                <table id="xform_main_data_whereTable2" class="tb_simple model-edit-view-oper-content-table" style="width:100%;">
                                                                                                                </table>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                        <div class="model-data-create" onclick="xform_main_data_addWhereItem(null,this,'1');">
                                                                                                            <div>${lfn:message('sys-modeling-base:button.add')}</div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>

                                                                                <!-- 列表业务穿透 -->
                                                                                <tr>
                                                                                    <td class="td_normal_title">
                                                                                        ${lfn:message('sys-modeling-base:sysModelingRelation.fdIsThrough')}
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="model-view-panel-table-td">
                                                                                        <input type="hidden" name="fdViewFlag">
                                                                                        <div class="view_flag_radio" style="display: inline-block;">
                                                                                            <div class="view_flag_radio_yes" style="display: inline-block;cursor: pointer;"  onclick="changeFlag(this,'fdViewFlag',1,'fdViewId')"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.1')}</div>
                                                                                            <div class="view_flag_radio_no view_flag_last" style="display:inline-block;cursor: pointer;"  onclick="changeFlag(this,'fdViewFlag',0,'fdViewId')"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.0')}</div>
                                                                                        </div>
                                                                                        <span style="margin-right:20px"></span>
                                                                                        <xform:select property="fdViewId" showPleaseSelect="false" style="width: 60%">
                                                                                            <xform:simpleDataSource value="">${lfn:message('sys-modeling-base:sysModelingOperation.fdViewDef')}</xform:simpleDataSource>
                                                                                            <xform:beanDataSource serviceBean="modelingAppViewService" selectBlock="fdId,fdName" whereBlock="fdModel.fdId='${modelingGanttForm.modelMainId }' and fdMobile=0">
                                                                                            </xform:beanDataSource>
                                                                                        </xform:select>
                                                                                    </td>
                                                                                </tr>
                                                                            <!-- 开启权限过滤 -->
                                                                            <tr>
                                                                                <td class="td_normal_title">
                                                                                    ${lfn:message('sys-modeling-base:modelingBusiness.fdAuthEnabled')}
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="model-view-panel-table-td">
                                                                                    <input type="hidden" name="fdAuthEnabled">
                                                                                    <div class="view_flag_radio" style="display: inline-block;">
                                                                                    <div class="view_flag_radio_yes" style="display: inline-block;cursor: pointer;"  onclick="changeFlag(this,'fdAuthEnabled',1)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.1')}</div>
                                                                                    <div class="view_flag_radio_no view_flag_last" style="display:inline-block;cursor: pointer;"  onclick="changeFlag(this,'fdAuthEnabled',0)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.0')}</div>
                                                                                    </div>
                                                                                        <div style="color:red;white-space:normal;word-break:break-all;word-wrap:break-word"><bean:message bundle="sys-modeling-base" key="modelingAppListview.fdAuthEnabled.hit"/></div>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </center>
                                <br>
                            <input type="hidden" name="modelMainId"
                                   value="<c:out value='${modelingGanttForm.modelMainId}' />">
                            <%--<input type="hidden" name="modelTargetId"
                                   value="<c:out value='${modelingGanttForm.modelTargetId}' />">--%>
                            <input type="hidden" name="docCreateTime"
                                   value="<c:out value='${modelingGanttForm.docCreateTime}' />">
                            <input type="hidden" name="docCreatorId"
                                   value="<c:out value='${modelingGanttForm.docCreatorId}' />">
                            <input type="hidden" name="fdConfig"
                                   value="<c:out value='${modelingGanttForm.fdConfig}' />">
                            <html:hidden property="method_GET"/>
                        </html:form>
                    </div>
                </div>
            </div>
        </div>
        <!-- 右侧编辑 end -->
    </div>
</div>
<!-- 预定义查询相关 start -->
<%
    JSONObject enumJSON = ListviewEnumUtil.getAllEnum();
    String enumString = enumJSON.toString();
%>
<script type="text/javascript">
    DocList_Info.push('operationTable');
    $KMSSValidation();
    var _main_data_insystem_enumCollection =
        <%=enumString%>
    Com_IncludeFile("spectrum.js", Com_Parameter.ContextPath + 'resource/js/colorpicker/', 'js', true);
    Com_IncludeFile("spectrum.css", Com_Parameter.ContextPath + 'resource/js/colorpicker/css/', 'css', true);
    Com_IncludeFile("spectrumColorPicker.js", Com_Parameter.ContextPath + 'sys/modeling/base/views/business/res/', 'js', true);
    Com_IncludeFile("ganttTable.js", Com_Parameter.ContextPath + "sys/modeling/base/views/business/res/preview/", "js", true);
    Com_IncludeFile("ganttTableSortEdit.js", Com_Parameter.ContextPath + "sys/modeling/base/views/business/res/preview/", "js", true);
    Com_IncludeFile("view_common.js", Com_Parameter.ContextPath + 'sys/modeling/base/resources/js/', 'js', true);
    Com_IncludeFile("gantt_edit.js", Com_Parameter.ContextPath + "sys/modeling/base/views/business/res/", "js", true);
    Com_IncludeFile('calendar.js');
    window.colorChooserHintInfo = {
        cancelText: '取消',
        chooseText: '确定'
    };
    var ganttOption = {
        param : {
        },
        modelingGanttForm : {
            fdModelName : '${fdModelName}',
            fdOrderBy : '${fdOrderBy}',
            fdCondition : '${fdCondition}',
            <c:if test="${empty modelDict}">   modelDict : '',</c:if>
            <c:if test="${not empty modelDict}">   modelDict : JSON.stringify(${modelDict}),</c:if>
            <c:if test="${empty fdDisplay}">   fdDisplay : '',</c:if>
            <c:if test="${not empty fdDisplay}">   fdDisplay : JSON.stringify(${fdDisplay}),</c:if>
            fdEnableFlow : '${flowInfo.fdEnableFlow}',
            allField : '${allField}'
        },
        dialogs : {
            sys_modeling_operation_selectListviewOperation : {
                modelName : 'com.landray.kmss.sys.modeling.base.model.SysModelingOperation',
                sourceUrl : '/sys/modeling/base/sysModelingOperationData.do?method=selectListviewOperation&fdAppModelId=${modelingGanttForm.modelMainId}'
            }
        },
        lang : {
            alreadyToDown : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.alreadyToDown')}",
            alreadyToUp : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.alreadyToUp')}",
            fdKeyWaring : "${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKeyWaring')}",
            chooseModuleFirst : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.chooseModuleFirst')}",
            docCategory : "${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory')}",
            searchPreview : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.searchPreview')}",
            systemData : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.systemData')}",
            lookLog : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.lookLog')}",
            fdOrderBy : "${lfn:message('sys-modeling-base:modelingAppListview.fdOrderBy')}",
            fdWhereBlock : "${lfn:message('sys-modeling-base:modelingAppListview.fdWhereBlock')}",
            cantHaveRepeatItem : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.cantHaveRepeatItem')}",
            returnValCantBeNull : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.returnValCantBeNull')}",
            deleteIt : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.delete')}",
            up : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.up')}",
            down : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.down')}",
            asc : "${lfn:message('sys-modeling-base:modelingAppListview.fdOrderType.asc')}",
            desc : "${lfn:message('sys-modeling-base:modelingAppListview.fdOrderType.desc')}",
            selectDisplay : " ${lfn:message('button.select')}${lfn:message('sys-modeling-base:modelingAppListview.fdDisplay')}",
            selectCondition : " ${lfn:message('button.select')}${lfn:message('sys-modeling-base:modelingAppListview.fdCondition')}",
            sortItem : " ${lfn:message('sys-modeling-base:listview.sort.item')}",
            field : " ${lfn:message('sys-modeling-base:relation.field')}",
            fdOperator : " ${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperator')}",
            fdValue : " ${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}",
            builtInQuery : " ${lfn:message('sys-modeling-base:modeling.builtIn.query')}",
            type : " ${lfn:message('sys-modeling-base:behavior.type')}",
            customQueryItem : " ${lfn:message('sys-modeling-base:listview.custom.query.items')}",
            builtInQueryItem : " ${lfn:message('sys-modeling-base:listview.built-in.query.items')}",
            notContain : " ${lfn:message('sys-modeling-base:modelingAppListview.enum.notContain')}",
            yes : " ${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.1')}",
            no : " ${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.0')}",
            choose : " ${lfn:message('sys-modeling-base:modeling.page.choose')}",
            invalidOper : " ${lfn:message('sys-modeling-base:Gantt.invalid.label.operation')}",
            URLparameters : " ${lfn:message('sys-modeling-base:Gantt.whether.receive.URL.parameters')}",
            curDialogMiss : " ${lfn:message('sys-modeling-base:Gantt.current.dialog.box.miss.parameter')}",
            checkFormConf : " ${lfn:message('sys-modeling-base:Gantt.check.form.configuration')}",
            closePreview : " ${lfn:message('sys-modeling-base:Gantt.close.preview')}",
            preview : " ${lfn:message('sys-modeling-base:sysform.preview')}",
            docSubject : " ${lfn:message('sys-modeling-base:modelingAppNav.docSubject')}",
            fdOutSort : " ${lfn:message('sys-modeling-base:modelingPortletCfg.fdOutSort')}",
        }
    };
    var lastSelectPostionObj;
    var lastSelectPostionDirect;
    var lastSelectPosition;
    // 全局变量，存储当前model的信息

    Com_AddEventListener(window,"load",function(){
        //初始化数据字典变量
        this.xform_main_data_init();
        //是否穿透
        var fdViewFlag = $("[name='fdViewFlag']");
        var value = fdViewFlag.val();
        if(value === 0 || value === "0" || value === "false"){
            fdViewFlag.nextAll().find(".view_flag_radio_no i").addClass("view_flag_yes");
            fdViewFlag.nextAll().find(".view_flag_radio_yes i").removeClass("view_flag_yes");
            $("[name='fdViewId']").hide();
        }else{
            fdViewFlag.nextAll().find(".view_flag_radio_no i").removeClass("view_flag_yes");
            fdViewFlag.nextAll().find(".view_flag_radio_yes i").addClass("view_flag_yes");
        }
        //开启权限过滤
        var fdAuthEnabled = $("[name='fdAuthEnabled']");
        value = fdAuthEnabled.val();
        if(!value){
            fdAuthEnabled.val(1);
        }
        if(value === 0 || value === "0" || value === "false"){
            fdAuthEnabled.nextAll().find(".view_flag_radio_no i").addClass("view_flag_yes");
            fdAuthEnabled.nextAll().find(".view_flag_radio_yes i").removeClass("view_flag_yes");
        }else{
            fdAuthEnabled.nextAll().find(".view_flag_radio_no i").removeClass("view_flag_yes");
            fdAuthEnabled.nextAll().find(".view_flag_radio_yes i").addClass("view_flag_yes");
        }
        var num = setInterval(function(){
            //初始化编辑页面预览
            if(LUI("view_preview")){
                clearInterval(num);
                LUI("view_preview").setSourceData(getGanttData);
                LUI("view_preview").reRender();
                //恢复选择的位置
                if(lastSelectPostionObj && lastSelectPostionDirect){
                    switchSelectPosition(lastSelectPostionObj,lastSelectPostionDirect,lastSelectPosition);
                }
            }
        }, 200);

    });

    //--------------公式定义器
    var isFormulaFieldListBuilded = false;
    var formulaFieldList=[];
    function onClick_Formula_Dialog(idField,nameField,returnType){
        //#117231 列表视图查询条件屏蔽变量
        var formulaUrl = formulaDialogUrl = Com_Parameter.ContextPath + "sys/formula/dialog_edit.jsp";
        var formulaParam = {modelingListviewHideVar:true};
        // Formula_Dialog(idField,nameField, '', 'String','',null,null,formulaParam,formulaUrl);
        Formula_Dialog(idField,nameField, formulaFieldList || "", returnType || "Object", null, null, null, null, null);
        //列表视图无法解析公式定义器中的变量，撤回
    }

    seajs.use(["sys/modeling/base/views/business/res/gantt", "lui/dialog", "lui/jquery", 'lui/topic', "sys/modeling/base/formlog/res/mark/ganttFMMark"]
        , function (gantt, dialog, $, topic, ganttFMMark) {
            //初始化可使用者
            function initAuthReaders(){
                var authReaders = [];
                var authReaderIds = '${modelingGanttForm.authSearchReaderIds}';
                var authReaderNames =  '${modelingGanttForm.authSearchReaderNames}';
                if (authReaderIds && authReaderNames) {
                    var idsArr = authReaderIds.split(";");
                    var namesArr = authReaderNames.split(";");
                    if (idsArr.length > 0) {
                        for (var i = 0; i < idsArr.length; i++) {
                            if (namesArr.length > i) {
                                var auth = {
                                    id: idsArr[i],
                                    name: namesArr[i]
                                }
                                authReaders.push(auth);
                            }
                        }
                    }
                }
                Address_QuickSelection("fdOrgElementIds", "fdOrgElementNames",
                    ";", ORG_TYPE_ALL | ORG_FLAG_BUSINESSYES, true, authReaders, null, null, "");
            }
            initAuthReaders();
            //显示项下拉框数据
            var texts = getGanttData()[0].fdDisplays;
            if(ganttOption.modelingGanttForm.fdDisplay != ""){
                var modelDictData = $.parseJSON(ganttOption.modelingGanttForm.modelDict);
                var allFieldData = $.parseJSON(ganttOption.modelingGanttForm.allField);
                var fields = $.parseJSON(ganttOption.modelingGanttForm.fdDisplay);
                var data = {};
                var text = [];
                for(var i = 0;i < texts.length;i++){
                    text.push(texts[i].text);
                }
                data.selected = doAdaptorOldData(modelDictData,allFieldData,fields);
                data.text = text;
                //显示项样式改变事件
                topic.publish("modeling.selectDisplay.change",{'thisObj':null,'data':data});
            }
            //兼容旧数据，补全多选框的枚举数据
            function doAdaptorOldData(modelDictData,allFieldData,fields){
                var objArr = [];
                for(var n = 0;n < fields.length;n++){
                    for(var m = 0;m < allFieldData.length;m++){
                        if(fields[n].field == allFieldData[m].field.split(".")[0]){
                            objArr.push(allFieldData[m]);
                        }
                    }
                }
                for(var i = 0;i < objArr.length;i++){
                    for(var j = 0;j < modelDictData.length;j++){
                        if(modelDictData[j].field == objArr[i].field.split(".")[0] && modelDictData[j].hasOwnProperty("enumValues")){
                            objArr[i].enumValues = modelDictData[j].enumValues;
                        }
                    }
                }
                return objArr;
            }

            //窗口大小自适应-------------------------------
            function onResizeFitWindow() {
                var height = $('body').height();
                if ("${param.isInDialog}") {
                    $('.model-edit-view-title:eq(0)').hide()
                } else {
                    height = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true) - 25;
                }

                $("body", parent.document).find('#trigger_iframe').height(height);
                $(".model-edit-right-wrap .model-edit-view-content").height(height - 180);
                // $(".model-edit-left-wrap .model-edit-view-content").height(height - 60);
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

            //预览更新事件
            topic.subscribe('preview.refresh',function(){
                try{
                    //刷新预览
                    LUI("view_preview").setSourceData(getGanttData);
                    LUI("view_preview").reRender();
                    //恢复选择的位置
                    if(lastSelectPostionObj && lastSelectPostionDirect){
                        switchSelectPosition(lastSelectPostionObj,lastSelectPostionDirect,lastSelectPosition);
                    }
                }catch(err){
                }
            });

            //窗口大小自适应-------------------------------end
            function init() {
                var cfg = {
                    xformId: "${xformId}",
                    flowInfo:${flowInfo},
                    modelMainId: "${modelingGanttForm.modelMainId}",
                    <%--modelTargetId: "${modelingGanttForm.modelTargetId}",--%>
                    widgets: ${widgets},
                    fdConfig: $("[name='fdConfig']").val()
                };
                window.gantt = new gantt.Gantt(cfg);
                window.gantt.startup();
                window.__rpClick = window.gantt.__rpClick;
                window.fmmark = new ganttFMMark.GanttFMMark({fdId: "${modelingGanttForm.fdId}"});
                fmmark.startup();
            }

            init();

            window.onclose = function () {
                $dialog.hide(null);
            };
            window.dosubmit = function (type) {
                var ganttConfig = window.gantt.getKeyData();
                console.debug("ganttConfig::", ganttConfig);
                var validateResult = window.GanttValidate.validate(ganttConfig)
                if (!validateResult) {
                    $("[name='fdConfig']").val(JSON.stringify(ganttConfig));
                    // $("[name='modelTargetId']").val(ganttConfig.source.model.id);
                    Com_Submit(document.modelingGanttForm, type);
                }else {
                    dialog.alert(validateResult)
                }
            };
        })

    function returnListPage() {
        var url = Com_Parameter.ContextPath + 'sys/modeling/base/views/business/gantt/gantt_index.jsp?fdModelId=${modelingGanttForm.modelMainId}';
        var iframe = window.parent.document.getElementById("trigger_iframe");
        $(iframe).attr("src", url);
        return false;
    }

</script>
<%@ include file="/resource/jsp/edit_down.jsp" %>