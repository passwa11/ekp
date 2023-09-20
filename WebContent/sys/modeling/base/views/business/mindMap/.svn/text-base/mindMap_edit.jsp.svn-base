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
    Com_IncludeFile("jquery.js");
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

<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/views/business/res/mindMapTreeView.css">

<div class="model-body-content" id="editContent_resPanel">
    <div class="model-mind-map-top">
        <div class="model-mind-map-left">
            <div class="modeling-pam-back">
                <div onclick="returnListPage()">
                    <i></i>
                    <p>${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.back')}</p>
                </div>
            </div>
            <div class="model-mind-map-title listviewName">
                <c:if test="${empty modelingMindMapForm.fdName}">
                    ${lfn:message('sys-modeling-base:modelingMindMap.no.name')}
                </c:if>
                ${modelingMindMapForm.fdName}
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
                                <%-- <div class="model-edit-tree-container" style="width: 25%;float: left;display: none">
                                     <img src="views/business/res/img/mindMap/mind_map_tree@2x.png">
                                 </div>--%>
                                <div id="treeviewMain" style="display: none">
                                    <span class="treeviewMainBtn"><i></i></span>
                                    <div class="treeviewMainContent">
                                    </div>
                                </div>
                                <div id="treeviewContent">
                                    <div class="model-source-wrap" style="width: 100%;float: right">
                                        <div class="model-source-msg">
                                            <div style=" height: 32px;line-height: 32px;color: #333;overflow: hidden;
                                                    text-align: center;cursor: pointer;font-size: 18px;font-weight: 600" data-lui-position='tableTitle'
                                                 onclick='switchSelectPositionItem(this,"left")'>
                                                <c:if test="${empty modelingMindMapForm.fdName}">
                                                    ${lfn:message('sys-modeling-base:modelingMindMap.no.name')}
                                                </c:if>
                                                ${modelingMindMapForm.fdName}
                                            </div>
                                        </div>
                                        <div id="container" class="model-source-right" style="width: 100%">
                                        </div>
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
                        <html:form action="/sys/modeling/base/mindMap.do">
                            <div style="height: 100%;box-sizing: border-box;padding-top: 40px" id="mindMapEdit">
                                <center>
                                    <html:hidden property="fdId"/>
                                    <input type="hidden" name="modelMainId"
                                           value="<c:out value='${modelingMindMapForm.modelMainId}' />">
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
                                                                <xform:text property="fdName" style="width:95%"
                                                                            showStatus="edit" validators="required"/>
                                                                <div class="mind-map-fdName-letter">
                                                                    <span class="letter-now"></span>/<span class="letter-max">12</span>
                                                                </div>
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
                                                                <xform:textarea property="fdDesc" style="width:95%"
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
                                                                            <div class="model-edit-view-oper-content">
                                                                                <ul class="list-content">
                                                                                    <li class="model-edit-view-oper-content-item">${lfn:message('sys-modeling-base:modelingMindMap.name')}</li>
                                                                                    <li class="model-edit-view-oper-content-item field first-item root-name">
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
                                                                                        </div>
                                                                                        <div class="item-content-view root-node-view">
                                                                                            <ui:switch
                                                                                                    property="fdViewEnable"
                                                                                                    checkVal="1"
                                                                                                    unCheckVal="0"></ui:switch>
                                                                                        </div>
                                                                                    </li>
                                                                                    <li class="model-mind-map-link"
                                                                                        style="display: none">
                                                                                        <div class="item-title">${lfn:message('sys-modeling-base:modelingAppViewtab.fdLinkParams')}
                                                                                        </div>
                                                                                        <div class="item-content">
                                                                                            <div class="inputselectsgl">
                                                                                                <div type="text"
                                                                                                     class="input">
                                                                                                    <input type="text"
                                                                                                           name="fdLink"
                                                                                                           validators="">
                                                                                                </div>
                                                                                            </div>
                                                                                            <span class="txtstrong">*</span>
                                                                                            <div class="fdLink-tips">
                                                                                                    ${lfn:message('sys-modeling-base:modelingMindMap.link.format.refer')}:http://baidu.com ${lfn:message('sys-modeling-base:modelingMindMap.or')} /sys/profile/index.jsp
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

                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <div class="mind-map-other">
                                                                <div class="mind-map-other-title">
                                                                    <span class="model-edit-view-oper-title common_title">${lfn:message('sys-modeling-base:modelingMindMap.other.nodes')}</span>
                                                                    <span class="model-data-create model-data-order">${lfn:message('sys-modeling-base:button.add')}</span>
                                                                </div>
                                                            </div>
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
                                                        <tr>
                                                            <td>
                                                                <div class="modeling-pam-back">
                                                                    <div class="returnNodeCfg">
                                                                        <i></i>
                                                                        <p>${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.back')}</p>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="model-view-panel-table-td">
                                                                <div class="target-form-title">
                                                                        ${lfn:message('sys-modeling-base:modelingAppViewtab.targetForm')}<span class="txtstrong">*</span>
                                                                </div>
                                                                <div class="target-form-content">
                                                                    <div class="inputselectsgl multiSelectDialog targetModel"
                                                                         data-lui-position='fdTargetModel'
                                                                         style="width:100%;height: 28px!important;">
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
                                                                <div class="target-form-title root-node">
                                                                        ${lfn:message('sys-modeling-base:modelingMindMap.root.node')}
                                                                </div>
                                                                <div class="target-form-content root-is-connect">
                                                                    <input type="checkbox" name="fdIsConnectRoot"
                                                                           value="1">
                                                                    <span class="connect-root">${lfn:message('sys-modeling-base:modelingMindMap.connect.root.node')}</span>
                                                                </div>
                                                                <div class="connect-root-config" style="display: none">
                                                                    <div class="target-form-content connect-root inputselectsgl no-target-model-temp">
                                                                            ${lfn:message('sys-modeling-base:modelingMindMap.set')}
                                                                        <span class="view-tip-icon">fx</span>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="model-view-panel-table-td connect-parent-node">
                                                                <div class="target-form-title root-node">
                                                                    <span class="model-edit-view-oper-title">${lfn:message('sys-modeling-base:modelingMindMap.connected.superior')}</span>
                                                                </div>
                                                                <div class="target-form-content root-is-connect">
                                                                    <span class="model-data-create connect-pre-node">${lfn:message('sys-modeling-base:button.add')}</span>
                                                                </div>
                                                                <div class="connect-root-tips">
                                                                    <i style="background:url(${LUI_ContextPath }/sys/modeling/base/views/business/res/img/mindMap/warm@2x.png) no-repeat 0 center"></i>
                                                                        ${lfn:message('sys-modeling-base:modelingMindMap.target.form.parent.same')}
                                                                </div>
                                                                <div class="pre-root-content">

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
                                                                        ${lfn:message('sys-modeling-base:respanel.view.penetration')}
                                                                </div>
                                                                <div class="item-content-view other-node-view"
                                                                     style="margin-top: 8px;">
                                                                    <ui:switch property="fdOtherNodeViewEnable"
                                                                               checkVal="1" unCheckVal="0"></ui:switch>
                                                                </div>
                                                                <div class="mind-map-show-select" style="display: none">
                                                                    <select class="inputsgl width45 marginWidth selectCover fdOtherNodeView"
                                                                            id="fdOtherNodeView" name="fdOtherNodeView">
                                                                    </select>
                                                                </div>
                                                                <input type="hidden" name="fdOtherNodeViewFlow"
                                                                       value="">
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
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                            <%--显示设置--%>
                                        <tr class="resPanel-bar-content" resPanel-bar-content="content">
                                            <td>
                                                <div class="mind-map-display-setting">
                                                        <%--<div class="mind-map-default-show">
                                                            <input type="checkbox" name="default-show" value="1" checked>默认展开分支
                                                            <div class="mind-map-show-to">
                                                                <span class="mind-map-show-title">展开至：</span>
                                                                <div class="mind-map-show-select">
                                                                    <select class="inputsgl width45 marginWidth selectCover" id="defaultShowTo">
                                                                        <option>为空则展开全部分支</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>--%>
                                                    <div class="mind-map-default-show">
                                                        <span class="mind-map-show-title">${lfn:message('sys-modeling-base:modelingMindMap.show.structure.outline')}</span>
                                                        <div class="mind-map-tree">
                                                            <input type="radio" name="showTree" value="0"
                                                                   checked="checked">${lfn:message('sys-modeling-base:modelingMindMap.show')}
                                                            <input type="radio" name="showTree" value="1">${lfn:message('sys-modeling-base:modelingMindMap.no.show')}
                                                        </div>
                                                    </div>
                                                    <div class="mind-map-style">
                                                        <div class="mind-map-show-title">${lfn:message('sys-modeling-base:modelingMindMap.default.layout')}</div>
                                                        <div class="mind-map-option">
                                                            <ul>
                                                                <li class="mind-map-style-row active"
                                                                    name="mind-map-style" value="0"
                                                                    onclick="changeLayout(this);">${lfn:message('sys-modeling-base:modelingMindMap.horizontal')}
                                                                </li>
                                                                <li class="mind-map-style-col" name="mind-map-style"
                                                                    value="1" onclick="changeLayout(this);">${lfn:message('sys-modeling-base:modelingMindMap.vertical')}
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                    <div class="mind-map-style">
                                                        <div class="mind-map-show-title">${lfn:message('sys-modeling-base:modelingMindMap.default.theme')}</div>
                                                        <div class="mind-map-option">
                                                            <div class="style_main">
                                                                <div class="select_Style" style="width: 164px;height: 124px;overflow: hidden;">
                                                                    <img class="defaule-skin" src="views/business/res/img/mindMap/style-steady@2x.png">
                                                                    <input class="defaule-skin-value" style="display: none" value="0">
                                                                    <div class="style_main_cover" >
                                                                        <div class="style_main_edit" onclick="changeStyle();" >${lfn:message('sys-modeling-base:modelingMindMap.replace')}</div>
                                                                    </div>
                                                                </div>

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
                                   value="<c:out value='${modelingMindMapForm.modelMainId}' />">
                            <input type="hidden" name="docCreateTime"
                                   value="<c:out value='${modelingMindMapForm.docCreateTime}' />">
                            <input type="hidden" name="docCreatorId"
                                   value="<c:out value='${modelingMindMapForm.docCreatorId}' />">
                            <input type="hidden" name="fdConfig"
                                   value="<c:out value='${modelingMindMapForm.fdConfig}' />">
                            <html:hidden property="method_GET"/>
                        </html:form>
                    </div>
                </div>
            </div>
        </div>
        <!-- 右侧编辑 end -->
    </div>
</div>
<script src="${LUI_ContextPath}/sys/modeling/base/views/business/res/jquery.treeview.js"></script>
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
    var defaultSkin = getDefaultSkin($("[name='fdConfig']").val());

    function getDefaultSkin(cfg){
        var defaultSkin;
        if(cfg ==""){
            defaultSkin = "0";
        }else{
            defaultSkin =   JSON.parse(cfg).fdRootNode.defaultSkin;
        }
        if (defaultSkin == undefined){
            defaultSkin = "0";
        }
        var bgColor ;
        if(defaultSkin =="3"){
            bgColor = "#F5FCFF";
        }else if(defaultSkin =="4"){
            bgColor = "#21273E";
        }else if(defaultSkin =="5"){
            bgColor = "#000000";
        }else {
            bgColor = "#FFFFFF";
        }
        $("#container canvas").css("background-color",bgColor);
        return defaultSkin;
    }
    function returnListPage() {
        var url = Com_Parameter.ContextPath + 'sys/modeling/base/views/business/mindMap/mindMap_index.jsp?fdModelId=${modelingMindMapForm.modelMainId}';
        var iframe = window.parent.document.getElementById("trigger_iframe");
        $(iframe).attr("src", url);
        return false;
    }

    seajs.use(["sys/modeling/base/views/business/res/mindMap", "lui/dialog", "lui/jquery", 'lui/topic','lui/dialog', "sys/modeling/base/views/business/show/mindMap/G6Graph"]
        , function (mindMap, dialog, $, topic,dialog, G6Graph) {

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

            $("[name='fdName']").on("change", function () {
                $("[data-lui-position='tableTitle']").text($(this).val())
            })

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
                // window.whereFieldGenerator.modelDict();
                var cfg = {
                    xformId: "${xformId}",
                    flowInfo:${flowInfo},
                    modelMainId: "${modelingMindMapForm.modelMainId}",
                    widgets: ${widgets},
                    fdConfig: $("[name='fdConfig']").val(),
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
                var validateResult = window.MindMapValidate.validate(mindMapConfig)
                if (!validateResult) {
                    $("[name='fdConfig']").val(JSON.stringify(mindMapConfig));
                    Com_Submit(document.modelingMindMapForm, type);
                } else {
                    // dialog.alert(validateResult)
                }
            };
            window.changeLayout = function (obj) {
                $(obj).siblings().removeClass("active");
                if ($(obj).hasClass("active")) {
                    return;
                }
                $(obj).addClass("active");
                if (!g6g) {
                    return;
                }
                if ($(obj).attr("value") == 1) {
                    g6g.switchLayout("TB");
                } else {
                    g6g.switchLayout("LR");
                }
            };
            window.changeStyle = function () {
                var dValue = $(".defaule-skin-value").val();
                var url = '/sys/modeling/base/views/business/mindMap/mindMap_style.jsp';
                dialog.iframe(url, '${lfn:message('sys-modeling-base:modelingMindMap.default.style')}',
                    function (value) {
                        $(".defaule-skin").attr("src",value.src);
                        $(".defaule-skin-value").attr("value",value.defaultSkin);
                        g6g.changeSkin(value.defaultSkin);
                    },
                    {
                        width: 740,
                        height: 550,
                        params:dValue
                    }
                );
            };
            /*******图****/
            const mindMapContainer = document.getElementById('container');
            var g6g;
            window.formatData = function (keyData) {
                var mindData = {
                    "id": "root",
                    "name": "root",

                };
                //图的生成
                if (keyData && keyData.fdRootNode) {
                    //根节点绘制
                    mindData.name = keyData.fdRootNode.fdRootNodeName
                    //其他节点绘制
                    if (keyData && keyData.fdOtherNode) {
                        var nodes = keyData.fdOtherNode;
                        var cacheNodes = [];
                        for (var i = 0; i < nodes.length; i++) {
                            var node = nodes[i];
                            if (node.fdIsConnectRoot) {
                                if (!mindData.children) {
                                    mindData.children = [];
                                }
                                var n = {
                                    // id: node.fdTargetModelId + "_" + mindData.children.length,
                                    id: node.nodeSettingId,
                                    modelId: node.fdTargetModelId,
                                    name: node.fdTargetModelName
                                }
                                mindData.children.push(n);
                                if (!node.fdPreNode || node.fdPreNode.length == 0) {
                                    continue
                                }
                            }
                            cacheNodes.push(node)
                        }
                        console.log("cacheNodes",cacheNodes)
                        //最多处理次数，避免死循环
                        var loopI = 10;
                        while (loopI > 0) {
                            nodes = cacheNodes;
                            cacheNodes = [];
                            for (var i = 0; i < nodes.length; i++) {
                                var node = nodes[i];
                                var fdPreNode = node.fdPreNode;
                                for (var j = 0; j < fdPreNode.length; j++) {
                                    var pnode = fdPreNode[j]
                                    var tnode = findNodeByTree(mindData, pnode.preNodeSettingId);
                                    if (tnode) {
                                        if (!tnode.children) {
                                            tnode.children = [];
                                        }
                                        var id =  node.nodeSettingId;
                                        if(pnode.preNodeSettingId == node.nodeSettingId ){
                                            id = node.nodeSettingId + "_" + tnode.children.length
                                        }
                                        var n = {
                                            // id: node.fdTargetModelId + tnode.modelId + "_" + tnode.children.length,
                                            id: id,
                                            modelId: node.fdTargetModelId,
                                            name: node.fdTargetModelName
                                        }
                                        tnode.children.push(n);
                                        fdPreNode.splice(j, 1);
                                    }else{
                                        console.log("pnode",pnode);
                                    }
                                }
                                if (!node.fdPreNode || node.fdPreNode.length == 0) {
                                    continue
                                }
                                cacheNodes.push(node)
                            }
                            loopI--;
                        }
                    }
                }
                console.debug("mindData", mindData);
                return mindData;
            }
            window.findNodeByTree = function (tree, preNodeSettingId) {
                if ( tree.id == preNodeSettingId) {
                    return tree;
                }
                if (tree.children) {
                    for (var i = 0; i < tree.children.length; i++) {
                        var node = findNodeByTree(tree.children[i], preNodeSettingId);
                        if (node) {
                            return node;
                        }
                    }
                }
                return null;
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
                    /*defaultEdge: {
                        type: 'cubic-horizontal',
                        style: {
                            stroke: '#A3B1BF',
                        },
                    },*/
                });
                if ($("[name='mind-map-style'].active").attr("value") == 1) {
                    g6g.options.layout.direction = "TB"
                }else{
                    g6g.options.layout.direction = "LR"
                }

                g6g.TreeGraph()
                g6g.graph.on('collapse-icon:click', (e) => {
                    const target = e.target;
                    const id = target.get('nodeId');
                    const item = g6g.graph.findById(id);
                    const nodeModel = item.getModel();
                    nodeModel.collapsed = !nodeModel.collapsed;
                    g6g.graph.layout();
                    var collapsed = nodeModel.collapsed;
                    g6g.updateItem(item, {
                        collapsed,
                    });
                });
                window.g6g = g6g;
            }
            topic.subscribe('mindMap.nodeChange', function (evt) {
                if (window.mindMap && window.g6g) {
                    var keyData = window.mindMap.getKeyData();
                    var mindData = formatData(keyData);
                    window.g6g.read(mindData)
                }
            });

            window.buildGraph();
            /***************************************************************/


        })

    var data = [
        {
            name: '${lfn:message('sys-modeling-base:modelingMindMap.root.node')}',
            child: [
                {
                    name: '${lfn:message('sys-modeling-base:modelingMindMap.branch.node')}1',
                    child: [
                        {
                            name: '${lfn:message('sys-modeling-base:modelingMindMap.branch.node')}1.1',
                            child: [
                                {
                                    name: '${lfn:message('sys-modeling-base:modelingMindMap.branch.node')}1.1.1'
                                }, {
                                    name: '${lfn:message('sys-modeling-base:modelingMindMap.branch.node')}1.1.2'
                                },
                                {
                                    name: '${lfn:message('sys-modeling-base:modelingMindMap.branch.node')}1.1.3'
                                }
                            ]
                        }, {
                            name: '${lfn:message('sys-modeling-base:modelingMindMap.branch.node')}1.2'
                        }
                    ]
                },
                {
                    name: '${lfn:message('sys-modeling-base:modelingMindMap.branch.node')}2',
                    child: [
                        {
                            name: '${lfn:message('sys-modeling-base:modelingMindMap.branch.node')}2.1'
                        }, {
                            name: '${lfn:message('sys-modeling-base:modelingMindMap.branch.node')}2.2',
                            child: [
                                {
                                    name: '${lfn:message('sys-modeling-base:modelingMindMap.branch.node')}2.2.1'
                                }, {
                                    name: '${lfn:message('sys-modeling-base:modelingMindMap.branch.node')}2.2.2'
                                },
                                {
                                    name: '${lfn:message('sys-modeling-base:modelingMindMap.branch.node')}2.2.3'
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ];

    $(".treeviewMainContent li").click(function () {
        event.stopPropagation(); //阻止事件冒泡
        $("#treeviewMain li").removeClass("selected");
        $(this).addClass("selected");
        $('#treeviewContent').text($(this).children("span").text())
    })
    // 侧边栏展开收起
    $('.treeviewMainBtn').click(function(){
        $('#treeviewMain').toggleClass("hide");
        var oldWidth = $("#container canvas").width();
        if($('#treeviewMain').hasClass("hide")){
            $("#container canvas").css("width",(oldWidth+215)+"px");
        }else{
            $("#container canvas").css("width",(oldWidth-215)+"px");
        }
    })
    //递归
    function createTree(data) {
        var str = '<ul>';
        for (var i = 0; i < data.length; i++) {
            str += `<li><span class="fileIcon"></span>` + data[i].name;
            if (data[i].child) {
                str += createTree(data[i].child);
            }
            str += '</li>';
        };
        str += '</ul>';
        return str;
    };
    $(".treeviewMainContent").html(createTree(data));
    $(".treeviewMainContent").treeview({collapsed:false});

    $('.letter-now').text($("input[name='fdName']").val().length);
    $("input[name='fdName']").bind("input propertychange", function() {
        var maxLetter = $('.letter-max').text();
        var curLetter = $(this).val().length;
        if($(this).length >= maxLetter){
            $(this).attr("disabled","disabled");
        }else{
            $(this).removeAttr("disabled");
        }
        $('.letter-now').text(curLetter);
        $(this).val($(this).val().substring(0, maxLetter-1));
    });
</script>
<%@ include file="/resource/jsp/edit_down.jsp" %>