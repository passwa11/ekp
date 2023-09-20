<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.fssc.common.util.FsscCommonUtil" %>
    
        <%
            pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="default.view">
        <template:replace name="head">
            <style type="text/css">
                
                			.lui_paragraph_title{
                				font-size: 15px;
                				color: #15a4fa;
                		    	padding: 15px 0px 5px 0px;
                			}
                			.lui_paragraph_title span{
                				display: inline-block;
                				margin: -2px 5px 0px 0px;
                			}
                			.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
                			    border: 0px;
                			    color: #868686
                			}
                		
            </style>
            <script type="text/javascript">
                var formInitData = {

                };
                var messageInfo = {

                    'fdDetailLsit': '${lfn:escapeJs(lfn:message("fssc-common:table.fsscCommonTransferDetail"))}'
                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${fsscCommonTransferFieldForm.fdSourceTableName} - " />
            <c:out value="${ lfn:message('fssc-common:table.fsscCommonTransferField') }" />
        </template:replace>
        <template:replace name="toolbar">
            <script>
                function deleteDoc(delUrl) {
                    seajs.use(['lui/dialog'], function(dialog) {
                        dialog.confirm('${ lfn:message("page.comfirmDelete") }', function(isOk) {
                            if(isOk) {
                                Com_OpenWindow(delUrl, '_self');
                            }
                        });
                    });
                }

                function openWindowViaDynamicForm(popurl, params, target) {
                    var form = document.createElement('form');
                    if(form) {
                        try {
                            target = !target ? '_blank' : target;
                            form.style = "display:none;";
                            form.method = 'post';
                            form.action = popurl;
                            form.target = target;
                            if(params) {
                                for(var key in params) {
                                    var
                                    v = params[key];
                                    var vt = typeof
                                    v;
                                    var hdn = document.createElement('input');
                                    hdn.type = 'hidden';
                                    hdn.name = key;
                                    if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                                        hdn.value =
                                        v +'';
                                    } else {
                                        if($.isArray(
                                            v)) {
                                            hdn.value =
                                            v.join(';');
                                        } else {
                                            hdn.value = toString(
                                                v);
                                        }
                                    }
                                    form.appendChild(hdn);
                                }
                            }
                            document.body.appendChild(form);
                            form.submit();
                        } finally {
                            document.body.removeChild(form);
                        }
                    }
                }

                function doCustomOpt(fdId, optCode) {
                    if(!fdId || !optCode) {
                        return;
                    }

                    if(viewOption.customOpts && viewOption.customOpts[optCode]) {
                        var param = {
                            "List_Selected_Count": 1
                        };
                        var argsObject = viewOption.customOpts[optCode];
                        if(argsObject.popup == 'true') {
                            var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                            for(var arg in argsObject) {
                                param[arg] = argsObject[arg];
                            }
                            openWindowViaDynamicForm(popurl, param, '_self');
                            return;
                        }
                        var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
                        Com_OpenWindow(optAction, '_self');
                    }
                }
                window.doCustomOpt = doCustomOpt;
                var viewOption = {
                    contextPath: '${LUI_ContextPath}',
                    basePath: '/fssc/common/fssc_common_transfer_field/fsscCommonTransferField.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/fssc/common/fssc_common_transfer_field/fsscCommonTransferField.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscCommonTransferField.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/fssc/common/fssc_common_transfer_field/fsscCommonTransferField.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscCommonTransferField.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('fssc-common:table.fsscCommonTransferField') }" href="/fssc/common/fssc_common_transfer_field/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('fssc-common:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('fssc-common:table.fsscCommonTransferField')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-common:fsscCommonTransferField.fdSourceModelSubject')}
                            </td>
                            <td width="35%">
                                <%-- 原表名--%>
                                <div id="_xform_fdSourceTableName" _xform_type="text">
                                    <xform:text property="fdSourceModelSubject" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-common:fsscCommonTransferField.fdTargetModelSubject')}
                            </td>
                            <td width="35%">
                                <%-- 目标表名--%>
                                <div id="_xform_fdTargetTableName" _xform_type="text">
                                    <xform:text property="fdTargetModelSubject" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-common:fsscCommonTransferField.fdIsProcessed')}
                            </td>
                            <td width="35%">
                                <%-- 是否已迁移--%>
                                <div id="_xform_fdIsProcessed" _xform_type="radio">
                                    <xform:radio property="fdIsProcessed" htmlElementProperties="id='fdIsProcessed'" showStatus="view">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-common:fsscCommonTransferField.fdFinishedTime')}
                            </td>
                            <td width="35%">
                                <%-- 迁移完成时间--%>
                                <div id="_xform_fdFinishedTime" _xform_type="datetime">
                                    <xform:datetime property="fdFinishedTime" showStatus="view" dateTimeType="date" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" width="100%">
                                <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetailLsit_Form" align="center" tbdraggable="true">
                                    <tr align="center" class="tr_normal_title">
                                        <td style="width:40px;">
                                            ${lfn:message('page.serial')}
                                        </td>
                                        <td>
                                            ${lfn:message('fssc-common:fsscCommonTransferDetail.fdSourceFieldText')}
                                        </td>
                                        <td>
                                            ${lfn:message('fssc-common:fsscCommonTransferDetail.fdSourceFieldType')}
                                        </td>
                                        <td>
                                            ${lfn:message('fssc-common:fsscCommonTransferDetail.fdTargetFieldText')}
                                        </td>
                                        <td>
                                            ${lfn:message('fssc-common:fsscCommonTransferDetail.fdTargetFieldType')}
                                        </td>
                                    </tr>
                                    <c:forEach items="${fsscCommonTransferFieldForm.fdDetailLsit_Form}" var="fdDetailLsit_FormItem" varStatus="vstatus">
                                        <tr KMSS_IsContentRow="1" class="docListTr">
                                            <td class="docList" align="center">
                                                ${vstatus.index+1}
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 原字段名--%>
                                                <input type="hidden" name="fdDetailLsit_Form[${vstatus.index}].fdId" value="${fdDetailLsit_FormItem.fdId}" />
                                                <div id="_xform_fdDetailLsit_Form[${vstatus.index}].fdSourceField" _xform_type="text">
                                                    <xform:text property="fdDetailLsit_Form[${vstatus.index}].fdSourceFieldText" showStatus="view" style="width:95%;" />
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 字段类型--%>
                                                <div id="_xform_fdDetailLsit_Form[${vstatus.index}].fdFieldType" _xform_type="select">
                                                    <xform:select property="fdDetailLsit_Form[${vstatus.index}].fdSourceFieldType" htmlElementProperties="id='fdDetailLsit_Form[${vstatus.index}].fdFieldType'" showStatus="view">
                                                        <xform:enumsDataSource enumsType="fssc_common_transfer_field_type" />
                                                    </xform:select>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 目标字段名--%>
                                                <div id="_xform_fdDetailLsit_Form[${vstatus.index}].fdTargetField" _xform_type="text">
                                                    <xform:text property="fdDetailLsit_Form[${vstatus.index}].fdTargetFieldText" showStatus="view" style="width:95%;" />
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 字段类型--%>
                                                <div id="_xform_fdDetailLsit_Form[${vstatus.index}].fdFieldType" _xform_type="select">
                                                    <xform:select property="fdDetailLsit_Form[${vstatus.index}].fdTargetFieldType" htmlElementProperties="id='fdDetailLsit_Form[${vstatus.index}].fdFieldType'" showStatus="view">
                                                        <xform:enumsDataSource enumsType="fssc_common_transfer_field_type" />
                                                    </xform:select>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </td>
                        </tr>
                    </table>
                </ui:content>
            </ui:tabpage>
        </template:replace>

    </template:include>