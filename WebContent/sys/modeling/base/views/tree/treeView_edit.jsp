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
    Com_IncludeFile("calendar.js");
    Com_IncludeFile("view.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
    Com_IncludeFile("dialog.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
    Com_IncludeFile("Sortable.min.js", Com_Parameter.ContextPath + 'sys/modeling/base/resources/js/', 'js', true);
    Com_IncludeFile("view_common.js", Com_Parameter.ContextPath
        + 'sys/modeling/base/resources/js/', 'js', true);
    Com_IncludeFile("g6.4.3.3.min.js", Com_Parameter.ContextPath + 'sys/modeling/base/resources/antv/', 'js', true);
</script>

<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/preview.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/views/business/res/sourcePanel.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/collection.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/views/business/res/mindMap.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/views/tree/res/treeView.css?s_cache=${LUI_Cache}"/>

<div class="model-body-content" id="editContent_resPanel">
    <div class="model-mind-map-top">
        <div class="model-mind-map-left">
            <div class="modeling-pam-back">
                <div onclick="returnListPage()">
                    <i></i>
                    <p>${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.back')}</p>
                </div>
            </div>
            <div class="model-mind-map-title listviewName" title="${modelingTreeViewForm.fdName}">
                <c:if test="${empty modelingTreeViewForm.fdName}">
                    ${lfn:message('sys-modeling-base:modelingTreeView.no.name')}
                </c:if>
                ${modelingTreeViewForm.fdName}
            </div>
        </div>
        <div class="modeling-pam-top-right">
            <ul>
                <li onclick="dosubmit('update')" class="active">${lfn:message('sys-modeling-base:modeling.save')}</li>
            </ul>
        </div>
    </div>
    <div class="model-edit">
        <!-- 左侧预览 starts -->
        <div class="model-edit-left">
            <div class="model-edit-left-wrap">
                <div class="model-edit-view">

                    <div class="model-edit-view-content">
                        <div class="model-edit-view-content-bottom" data-lui-position="fdDisplay">
                            <div class="model-source">
                                <div class="model-source-wrap">
                                    <div class="model-source-msg">
                                        <div class="model-source-headline" data-lui-position='tableTitle'
                                             onclick='switchSelectPositionItem(this,"left")' title="${modelingTreeViewForm.fdName}">
                                            <c:if test="${empty modelingTreeViewForm.fdName}">
                                                ${lfn:message('sys-modeling-base:modelingTreeView.TreeView.name')}
                                            </c:if>
                                            ${modelingTreeViewForm.fdName}
                                        </div>
                                    </div>
                                    <div id="mindMap" class="model-source-right">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 左侧预览 end -->
        <!-- 右侧编辑 starts -->
        <div class="model-edit-right">
            <div class="model-edit-right-wrap">
                <div class="model-edit-view-bar">
                    <div resPanel-bar-mark="basic">${lfn:message('sys-modeling-base:listview.basic.set')}</div>
                    <div resPanel-bar-mark="frame">${lfn:message('sys-modeling-base:listview.view.set')}</div>
                    <div resPanel-bar-mark="content">${lfn:message('sys-modeling-base:modeling.display.set')}</div>
                </div>
                <div class="model-edit-view-content">
                    <div class="model-edit-view-content-wrap">
                        <html:form action="/sys/modeling/base/modelingTreeView.do">
                            <div style="height: 100%;box-sizing: border-box;margin-top: 30px" id="mindMapEdit">
                                <center>
                                    <html:hidden property="fdId"/>
                                    <input type="hidden" name="modelMainId"
                                           value="<c:out value='${modelingTreeViewForm.modelMainId}' />">
                                    <table class="tb_simple model-view-panel-table" width="100%" id="mindMapEditTable">
                                            <%--基本信息--%>
                                        <tr class="resPanel-bar-content" resPanel-bar-content="basic">
                                            <td>
                                                <table id="mindMapBasicDom" class="tb_simple model-view-panel-table"
                                                       width="100%">
                                                    <tr>
                                                        <td class="td_normal_title title_required common_title">
                                                            <bean:message bundle="sys-modeling-base"
                                                                          key="modelingBusiness.fdName"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <div id="_xform_fdName" _xform_type="text"
                                                                 class="mind-map-fdName" style="width:100%">
                                                                <xform:text property="fdName" style="width:100%"
                                                                            showStatus="edit" validators="required"
                                                                            required="true"/>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="td_normal_title common_title">
                                                            <bean:message bundle="sys-modeling-base"
                                                                          key="modelingBusiness.fdDesc"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <div id="_xform_fdDesc" _xform_type="text"
                                                                 style="width:100%">
                                                                <xform:textarea property="fdDesc" style="width:100%"
                                                                                showStatus="edit" placeholder="${lfn:message('sys-modeling-base:modeling.please.enter')}"
                                                                                validators="maxLength(450)"/>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="td_normal_title title_required common_title">
                                                                ${lfn:message('sys-modeling-base:respanel.fdAuthReaders')}
                                                        </td>
                                                    </tr>
                                                    <tr id="fdOrgElementTr">
                                                        <td width=100% class="model-view-panel-table-td">
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
                                            <%--内容设置--%>
                                        <tr class="resPanel-bar-content" resPanel-bar-content="frame">
                                            <td>
                                                <table id="mindMapTableDom" class="tb_simple model-view-panel-table"
                                                       width="100%">
                                                    <tr>
                                                        <td class="model-mind-map">
                                                            <div class="mind-map-root">
                                                                <div class="mind-map-root-title common_title">
                                                                        ${lfn:message('sys-modeling-base:modelingMindMap.root.node')}
                                                                </div>
                                                                <div class="mind-map-root-cfg">
                                                                    <div class="item operateItem sortItem" index="0">
                                                                        <div class="model-edit-view-oper">
                                                                            <%--<div class="model-edit-view-oper-head">
                                                                                <div class="model-edit-view-oper-head-title common_title root-name-title">
                                                                                    <span>根节点</span>
                                                                                </div>
                                                                            </div>--%>
                                                                            <div class="model-edit-view-oper-content">
                                                                                <ul class="list-content">
                                                                                    <li class="model-edit-view-oper-content-item field first-item root-name">
                                                                                        <div class="item-title">${lfn:message('sys-modeling-base:modelingTreeView.name')}</div>
                                                                                        <div class="item-content">
                                                                                            <div class="operation">
                                                                                                <div class="inputselectsgl">
                                                                                                    <input type="hidden"
                                                                                                           name="operationId"
                                                                                                           value="">
                                                                                                    <div type="text"
                                                                                                         class="input">
                                                                                                        <input type="text"
                                                                                                               name="fdRootName"
                                                                                                               validators="required maxLength(36)">
                                                                                                    </div>
                                                                                                </div>
                                                                                                <span class="txtstrong">*</span>
                                                                                            </div>
                                                                                        </div>
                                                                                    </li>
                                                                                    <li class="model-edit-view-oper-content-item field first-item">
                                                                                        <div class="item-title">${lfn:message('sys-modeling-base:listview.view.penetration')}
                                                                                            <span class="txtstrong">*</span>
                                                                                        </div>
                                                                                    </li>
                                                                                    <div class="common-auth-type choice-root-view">
                                                                                        <ul>
                                                                                            <li class="active" value="0"
                                                                                                name="view">${lfn:message('sys-modeling-base:relation.select.view')}
                                                                                            </li>
                                                                                            <li value="1" name="link">
                                                                                                    ${lfn:message('sys-modeling-base:modelingAppViewtab.fdLinkParams')}
                                                                                            </li>
                                                                                        </ul>
                                                                                    </div>
                                                                                    <li class="model-mind-map-view">
                                                                                        <div class="item-content">
                                                                                            <div class="content_opt listVieElement">
                                                                                                <p class="listViewText">
                                                                                                    ${lfn:message('sys-modeling-base: modeling.page.choose')}</p>
                                                                                                <i></i>
                                                                                            </div>
                                                                                            <input type="hidden"
                                                                                                   name="fdRootViewAppId"
                                                                                                   value="">
                                                                                            <input type="hidden"
                                                                                                   name="fdRootViewModelId"
                                                                                                   value="">
                                                                                            <input type="hidden"
                                                                                                   name="fdRootViewFdId"
                                                                                                   value="">
                                                                                            <input type="hidden"
                                                                                                   name="fdRootViewModelName"
                                                                                                   value="">
                                                                                            <input type="hidden"
                                                                                                   name="fdRootViewModelType"
                                                                                                   value="">
                                                                                        </div>
                                                                                    </li>
                                                                                    <li class="model-mind-map-link"
                                                                                        style="display: none">
                                                                                        <div class="item-content">
                                                                                            <div class="inputselectsgl">
                                                                                                <div type="text"
                                                                                                     class="input">
                                                                                                    <input type="text"
                                                                                                           name="fdLink"
                                                                                                           validators=""
                                                                                                           placeholder="${lfn:message('sys-modeling-base: modeling.page.choose')}">
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="fdLink-tips">
                                                                                                <p> ${lfn:message('sys-modeling-base:modelingMindMap.link.format.refer')}:http://baidu.com ${lfn:message('sys-modeling-base:modelingMindMap.or')} /sys/profile/index.jsp</p>
                                                                                                <span> ${lfn:message('sys-modeling-base:modelingTreeView.external.links.need.same-origin.policy')}</span>
                                                                                            </div>
                                                                                        </div>
                                                                                    </li>
                                                                                </ul>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-mind-map">
                                                            <div class="mind-map-other">
                                                                <div class="mind-map-other-title">
                                                                    <span class="model-edit-view-oper-title common_title">${lfn:message('sys-modeling-base:modelingTreeView.tree.node.set')}</span>
                                                                    <span class="model-data-create model-data-order">${lfn:message('sys-modeling-base:button.add')}</span>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <div class="model-panel-table-base"
                                                                 style="display:none;margin-bottom:0">
                                                                <div class="tb_simple model-edit-view-oper-content-table mind-map-other-node"
                                                                     id="otherNode" data-table-type="otherNode"
                                                                     style="width:100%;">
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <!--节点内容设置模板-->
                                                <div id="nodeSettingTable">
                                                    <table name="nodeSettingTable"
                                                           class="tb_simple model-view-panel-table nodeSettingTable nodeSetting"
                                                           width="100%" style="display: none">
                                                        <tr style="background: #F8F8F8;height: 40px;border-bottom: 1px solid #E6EAEE;">
                                                            <td>
                                                                <div class="modeling-pam-back">
                                                                    <div class="returnNodeCfg4Tree">
                                                                        <i></i>
                                                                        <p>${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.back')}</p>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="model-view-panel-table-td">
                                                                <div class="target-form-title">
                                                                    ${lfn:message('sys-modeling-base:sysModelingRelation.fdSourceType')}
                                                                </div>
                                                                <div class="target-form-content">
                                                                    <div class="inputselectsgl multiSelectDialog targetModel"
                                                                         data-lui-position='fdTargetModel'
                                                                         style="width:97%;height: 28px!important;">
                                                                        <input name="fdTargetModelId" value=''
                                                                               type="hidden">
                                                                        <div class="input">
                                                                            <input name="fdTargetModelName" value=""
                                                                                   type="text" style="display:none;"/>
                                                                            <span class="selectedItem"></span>
                                                                        </div>
                                                                        <div class="deleteAll"></div>
                                                                        <div class="selectitem"></div>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="model-view-panel-table-td">
                                                                <div class="target-form-title">
                                                                    ${lfn:message('sys-modeling-base:modelingTreeView.superior.node')}
                                                                    <span class="txtstrong">*</span>
                                                                </div>
                                                                <div class="tree-view-note-select">
                                                                    <select class="inputsgl width45 marginWidth selectCover tree-view-select">
                                                                        <option value="">${lfn:message('sys-modeling-base:modeling.page.choose')}</option>
                                                                        <option value="0" data-index="0">${lfn:message('sys-modeling-base:modelingMindMap.root.node')}</option>
                                                                    </select>
                                                                </div>
                                                                <div class="target-form-title">${lfn:message('sys-modeling-base:modelingTreeView.matching.relationship')}</div>
                                                                <div class="match-relation">
                                                                    <select class="cur-model-field-option no-cur-field-model"
                                                                            disabled="disabled">
                                                                        <option>${lfn:message('sys-modeling-base:modelingTreeView.node.form.field')}</option>
                                                                    </select>
                                                                    <div class="macth-relation-type-div">
                                                                        <span class="match-relation-type">=</span>
                                                                    </div>
                                                                    <select class="target-model-field-option no-target-field-model"
                                                                            disabled="disabled">
                                                                        <option>${lfn:message('sys-modeling-base:modelingTreeView.superior.node.form.field')}</option>
                                                                    </select>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="model-view-panel-table-td">
                                                                <div class="target-form-title">
                                                                    ${lfn:message('sys-modeling-base:respanel.show.field')}
                                                                </div>
                                                                <div class="target-form-content display-field inputselectsgl no-target-model-temp">
                                                                    ${lfn:message('sys-modeling-base:modelingMindMap.set')}
                                                                    <span class="view-tip-icon">fx</span>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="model-view-panel-table-td otherNodeView">
                                                                <div class="item-title target-form-title">
                                                                    ${lfn:message('sys-modeling-base:listview.view.penetration')}
                                                                </div>
                                                                <div class="common-auth-type choice-tree-note-view">
                                                                    <ul>
                                                                        <li class="active" value="0" name="view">${lfn:message('sys-modeling-base:relation.select.view')}
                                                                        </li>
                                                                        <li value="1" name="link">${lfn:message('sys-modeling-base:modelingAppViewtab.fdLinkParams')}</li>
                                                                    </ul>
                                                                </div>
                                                                <li class="model-mind-map-view">
                                                                    <div class="item-content">
                                                                        <div class="content_opt listVieElement">
                                                                            <p class="listViewText">${lfn:message('sys-modeling-base:modeling.page.choose')}</p>
                                                                            <i></i>
                                                                        </div>
                                                                        <input type="hidden" name="fdTreeNoteViewAppId"
                                                                               value="">
                                                                        <input type="hidden"
                                                                               name="fdTreeNoteViewModelId" value="">
                                                                        <input type="hidden" name="fdTreeNoteViewFdId"
                                                                               value="">
                                                                        <input type="hidden"
                                                                               name="fdTreeNoteViewModelName" value="">
                                                                        <input type="hidden" name="fdTreeNoteViewType"
                                                                               value="">
                                                                    </div>
                                                                </li>
                                                                <li class="model-mind-map-link"
                                                                    style="display: none">
                                                                    <div class="item-content">
                                                                        <div class="inputselectsgl">
                                                                            <div type="text"
                                                                                 class="input">
                                                                                <input type="text"
                                                                                       name="fdLink"
                                                                                       validators="" placeholder="${lfn:message('sys-modeling-base:modeling.please.enter')}">
                                                                            </div>
                                                                        </div>
                                                                        <div class="fdLink-tips">
                                                                            <p>${lfn:message('sys-modeling-base:modelingMindMap.link.format.refer')}:http://baidu.com ${lfn:message('sys-modeling-base:modelingMindMap.or')} /sys/profile/index.jsp</p>
                                                                            <span>${lfn:message('sys-modeling-base:modelingTreeView.external.links.need.same-origin.policy')}</span>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="model-view-panel-table-td whereBlock-table">
                                                                <div class="target-form-title">
                                                                    ${lfn:message('sys-modeling-base:modelingAppListview.fdWhereBlock')}
                                                                </div>
                                                                <div class="common-data-filter-temp">
                                                                    <div class="common-auth-type common-data-filter-whereType">
                                                                        <ul>
                                                                            <li class="active" value="0">${lfn:message('sys-modeling-base:modeling.custom.query')}</li>
                                                                            <li value="1">${lfn:message('sys-modeling-base:modeling.builtIn.query')}</li>
                                                                        </ul>
                                                                    </div>
                                                                    <div class="data-filter-content">
                                                                        <div class="common-data-filter-type">
                                                                            <input type="radio"
                                                                                   name="data-filter-whereType"
                                                                                   class="data-filter-whereType"
                                                                                   value="0" checked>${lfn:message('sys-modeling-base:relation.meet.all.conditions')}
                                                                            <input type="radio"
                                                                                   name="data-filter-whereType"
                                                                                   class="data-filter-whereType"
                                                                                   value="1">${lfn:message('sys-modeling-base:relation.meet.any.conditions')}
                                                                        </div>
                                                                        <table class='where-block-table tb_simple model-edit-view-oper-content-table'
                                                                               data-table-type='where'
                                                                               name='custom_query'>
                                                                        </table>
                                                                    </div>
                                                                    <div class="data-filter-content-sys"
                                                                         style="display: none">
                                                                        <div class="common-data-filter-type">
                                                                            <input type="radio"
                                                                                   name="sys-filter-whereType"
                                                                                   class="sys-filter-whereType"
                                                                                   value="0" checked>${lfn:message('sys-modeling-base:relation.meet.all.conditions')}
                                                                            <input type="radio"
                                                                                   name="sys-filter-whereType"
                                                                                   class="sys-filter-whereType"
                                                                                   value="1">${lfn:message('sys-modeling-base:relation.meet.any.conditions')}
                                                                        </div>
                                                                        <table class='where-block-table tb_simple model-edit-view-oper-content-table'
                                                                               data-table-type='where' name='sys_query'>
                                                                        </table>
                                                                    </div>
                                                                    <div class="model-data-create">
                                                                        <div>${lfn:message('sys-modeling-base:button.add')}</div>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="model-view-panel-table-td orderBlock-table">
                                                                <div class="mind-map-other-title">
                                                                    <span class="model-edit-view-oper-title target-form-title">${lfn:message('sys-modeling-base:modelingAppListview.fdOrderBy')}</span>
                                                                    <span class="model-data-create model-data-order">${lfn:message('sys-modeling-base:button.add')}</span>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="model-view-panel-table-td">
                                                                <div class="model-panel-table-base"
                                                                     style="display:none;margin-bottom:0">
                                                                    <div class="tb_simple model-edit-view-oper-content-table"
                                                                         data-table-type="order" style="width:100%;">
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                            <%--显示设置--%>
                                        <tr class="resPanel-bar-content" resPanel-bar-content="content">
                                            <td>
                                                <div class="mind-map-display-setting">
                                                    <div class="mind-map-default-show">
                                                        <div class="is-show-root">
                                                            <span class="target-form-title">${lfn:message('sys-modeling-base:modelingTreeView.whether.display.root.node')}</span>
                                                            <div class="is-show-radio"
                                                                 style="float: right">
                                                                <input type="radio" name="isShowRoot" value="0" checked>${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.1')}
                                                                <input type="radio" name="isShowRoot" value="1">${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.0')}
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="mind-map-extend">
                                                            <div class="mind-map-extend-level">
                                                                <span class="mind-map-extend-title">${lfn:message('sys-modeling-base:modelingTreeView.expand.level')}</span>
                                                                <div class="content-through-tabs">
                                                                    <div class="pre-node-title">${lfn:message('sys-modeling-base:modelingTreeView.expand.to')}</div>
                                                                    <select class="inputsgl marginWidth" id="mind-map-extend">
                                                                        <option value="1">1</option>
                                                                        <option value="2">2</option>
                                                                        <option value="3">3</option>
                                                                        <option value="4">4</option>
                                                                        <option value="-1">${lfn:message('sys-modeling-base:modelingTreeView.expand.all')}</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </center>
                                <br>
                            </div>
                            <input type="hidden" name="modelMainId"
                                   value="<c:out value='${modelingTreeViewForm.modelMainId}' />">
                            <input type="hidden" name="docCreateTime"
                                   value="<c:out value='${modelingTreeViewForm.docCreateTime}' />">
                            <input type="hidden" name="docCreatorId"
                                   value="<c:out value='${modelingTreeViewForm.docCreatorId}' />">
                            <input type="hidden" name="fdConfig"
                                   value="<c:out value='${modelingTreeViewForm.fdConfig}' />">
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
    var listviewOption = {
        isEnableFlow: {
            isFlowBoolean: "${isFlowBoolean}",
            isFlow: "${isFlow}"
        },
        xformId: "${xformId}",
        <c:if test="${flowInfo!=null}">flowInfo:${flowInfo}</c:if>,
        <c:if test="${widgets!=null}">widgets:${widgets}</c:if>
    };

    function returnListPage() {
        var url = Com_Parameter.ContextPath + 'sys/modeling/base/treeView/index_body.jsp?fdModelId=${modelingTreeViewForm.modelMainId}';
        var iframe = window.parent.document.getElementById("trigger_iframe");
        $(iframe).attr("src", url);
        return false;
    }

    seajs.use(["sys/modeling/base/views/business/res/mindMap", "lui/dialog", "lui/jquery", 'lui/topic', "sys/modeling/base/views/business/show/mindMap/G6Graph"]
        , function (mindMap, dialog, $, topic, G6Graph) {
            /********查询条件start***********/
            //选择框切换数据后事件
            topic.channel("modeling").subscribe("field.change", function (data) {
                var selectDom = data.dom;
                //更新标题
                var $parent = $(selectDom).parents("div.select_union").eq(0);
                var text = "";
                var fieldId = $parent.find("select").eq(0).val();
                var fieldText = $parent.find("select").eq(0).find("option[value='" + fieldId + "']").text();
                text = fieldText;
                fieldId = $parent.find("select").eq(1).val();
                fieldText = $parent.find("select").eq(1).find("option[value='" + fieldId + "']").text();
                if (fieldText) {
                    text += "|" + fieldText;
                }
                $(selectDom).parents(".model-edit-view-oper").eq(0).find(".model-edit-view-oper-head-title span").text(text);
                //刷新预览
                topic.publish("preview.refresh");
            })

            // 数据过滤——类型切换事件，更新头部标题
            topic.channel("modeling").subscribe("whereType.change", function (data) {
                var selectDom = data.dom;
                var text = "";
                // 0（自定义查询项）|1（内置查询项）
                if (data.value === "0") {
                    text = data.wgt.fieldWgt.getFieldText();
                } else if (data.value === "1") {
                    text = $(selectDom).closest(".list-content").find("[data-bind-type-value='1']").find("select option:selected").text();
                }
                $(selectDom).parents(".model-edit-view-oper").eq(0).find(".model-edit-view-oper-head-title span").text(text);
            });
            /********查询条件end***********/

            //窗口大小自适应-------------------------------
            function onResizeFitWindow() {
                var height = $('body').height();
                if ("${param.isInDialog}") {
                    $('.model-edit-view-title:eq(0)').hide()
                } else {
                    height = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true) - 25;
                }

                $("body", parent.document).find('#trigger_iframe').height(height);
                // #145837 页面滚动条问题
                // $(".model-edit-right-wrap .model-edit-view-content").height(height - 80);
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

            //窗口大小自适应-------------------------------end
            function init() {
                var cfg = {
                    xformId: "${xformId}",
                    flowInfo:${flowInfo},
                    modelMainId: "${modelingTreeViewForm.modelMainId}",
                    widgets: ${widgets},
                    fdConfig: $("[name='fdConfig']").val(),
                    type: "treeView"
                };
                window.mindMap = new mindMap.MindMap(cfg);
                window.mindMap.startup();
            }

            init();
            window.onclose = function () {
                $dialog.hide(null);
            };
            window.dosubmit = function (type) {
                var mindMapConfig = window.mindMap.getKeyData();
                var validateResult = window.MindMapValidate.validate4TreeView(mindMapConfig)
                if (!validateResult) {
                    $("[name='fdConfig']").val(JSON.stringify(mindMapConfig));
                    Com_Submit(document.modelingTreeViewForm, type);
                } else {
                    // dialog.alert(validateResult)
                }
            };
            window.changeStyle = function (obj) {
                $(obj).siblings().removeClass("active");
                $(obj).addClass("active");
            }

            /******************************预览start**********************************/
            const mindMapContainer = document.getElementById('mindMap');
            var g6g;
            window.formatData = function (keyData) {
                var mindData = {
                    "id": "root",
                    "name": "root",
                    "index": "0",
                    "children": [],
                };
                //1.格式化数据——构造G6Graph所需的前端树形数据
                var nodeCollection = [];
                formatConfig(nodeCollection, keyData, mindData);
                //2.构造每个子节点的孩子节点
                let nodeArr = $.extend(true, [], nodeCollection);
                let rootChildrenArr = generateChildrenArr(nodeArr, 0);
                //3、构造根节点的子节点
                for (var i = 0; i < rootChildrenArr.length; i++) {
                    mindData.children.push(rootChildrenArr[i]);
                }
                return mindData;
            }

            function formatConfig(nodeCollection, keyData, mindData) {
                if (keyData && keyData.fdRootNode) {
                    mindData.name = keyData.fdRootNode.fdRootNodeName
                    if (keyData && keyData.fdOtherNode) {
                        var nodes = keyData.fdOtherNode;
                        for (var i = 0; i < nodes.length; i++) {
                            var node = nodes[i];
                            var n = {
                                id: node.fdTargetModelId + node.fdIndex,
                                modelId: node.fdTargetModelId,
                                name: node.fdTargetModelName,
                                index: node.fdIndex,
                                preIndex: node.fdPreIndex
                            }
                            nodeCollection.push(n);
                        }
                    }
                }
                return nodeCollection;
            }

            function generateChildrenArr(arr, parent) {
                var childrenArr = [];
                for (var i in arr) {
                    if (arr[i].preIndex == parent) {
                        var children = generateChildrenArr(arr, arr[i].index)
                        if (children.length) {
                            arr[i].children = children;
                        }
                        childrenArr.push(arr[i]);
                    }
                }
                return childrenArr;
            }

            window.buildGraph = function () {
                var mindData = formatData();
                if (window.mindMap) {
                    var keyData = window.mindMap.getKeyData();
                    mindData = formatData(keyData);
                }

                g6g = new G6Graph(mindMapContainer, mindData, {
                    modes: {
                        default: [
                            'drag-canvas',
                            'zoom-canvas',
                        ],

                    },
                    defaultEdge: {
                        style: {
                            stroke: '#A3B1BF',
                        },
                    },
                    layout: {
                        type: 'indented',
                        direction: 'LR', // H / V / LR / RL / TB / BT
                        indent: 60,
                    },
                    getHeight: function getHeight() {
                        return 16;
                    },
                    getWidth: function getWidth() {
                        return 16;
                    }
                });
                g6g.FileGraph()
                window.g6g = g6g;

            }
            topic.subscribe('mindMap.nodeChange', function (evt) {
                console.log("m", evt);
                if (window.mindMap && window.g6g) {
                    console.log("m", evt);
                    var keyData = window.mindMap.getKeyData();
                    var mindData = formatData(keyData);
                    window.g6g.read(mindData)
                }
            });

            window.buildGraph();
            /***************************预览end***************************/

        })

</script>
<%@ include file="/resource/jsp/edit_down.jsp" %>