<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.view">
    <template:replace name="head">
        <script type="text/javascript">
            var formInitData = {};
            var messageInfo = {};
        </script>
    </template:replace>
    <template:replace name="title">

    </template:replace>
    <template:replace name="toolbar">
        <script>

            window.doCustomOpt = doCustomOpt;
            var viewOption = {
                contextPath: '${LUI_ContextPath}',
                basePath: '/sys/modeling/base/modelingFormMapping.do',
                customOpts: {

                    ____fork__: 0
                }
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
    </template:replace>
    <template:replace name="content">

        <ui:tabpage expand="false" var-navwidth="90%">
            <ui:content title="${ lfn:message('prod-manage:py.JiBenXinXi') }"
                        expand="true">
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="15%">
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdComClass" _xform_type="text">
                                <xform:text property="fdComClass" showStatus="view"
                                            style="width:95%;"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdComName" _xform_type="text">
                                <xform:text property="fdComName" showStatus="view"
                                            style="width:95%;"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdComId" _xform_type="text">
                                <xform:text property="fdComId" showStatus="view"
                                            style="width:95%;"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_mappingAppModelNames" _xform_type="text">
                                <xform:text property="mappingAppModelNames" showStatus="view"
                                            style="width:95%;"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdFieldAbstract" _xform_type="text">
                                <xform:text property="fdFieldAbstract" showStatus="view"
                                            style="width:95%;"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdFieldDetail" _xform_type="text">
                                <xform:text property="fdFieldDetail" showStatus="view"
                                            style="width:95%;"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdIsAvailable" _xform_type="text">
                                <xform:text property="fdIsAvailable" showStatus="view"
                                            style="width:95%;"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdExplain" _xform_type="text">
                                <xform:text property="fdExplain" showStatus="view"
                                            style="width:95%;"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_modelMainName" _xform_type="text">
                                <xform:text property="modelMainName" showStatus="view"
                                            style="width:95%;"/>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td class="td_normal_title" width="15%"></td>
                        <td width="35%">
                            <div id="_xform_docCreateTime" _xform_type="datetime">
                                <xform:datetime property="docCreateTime" showStatus="view"
                                                dateTimeType="datetime" style="width:95%;"/>
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                                ${lfn:message('prod-manage:prodManageWorkhours.docCreator')}</td>
                        <td width="35%">
                            <div id="_xform_docCreatorId" _xform_type="address">
                                <ui:person personId="${modelingFormMappingForm.docCreatorId}"
                                           personName="${modelingFormMappingForm.docCreatorName}"/>
                            </div>
                        </td>
                    </tr>

                </table>
            </ui:content>
        </ui:tabpage>
    </template:replace>

</template:include>