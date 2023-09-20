<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.fssc.common.util.FsscCommonUtil" %>
    
        <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
        pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="default.edit">
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

                var initData = {
                    contextPath: '${LUI_ContextPath}'
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/common/fssc_common_transfer_field/", 'js', true);
                Com_IncludeFile("fsscCommonTransferField_edit.js", "${LUI_ContextPath}/fssc/common/fssc_common_transfer_field/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${fsscCommonTransferFieldForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-common:table.fsscCommonTransferField') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscCommonTransferFieldForm.fdSourceTableName} - " />
                    <c:out value="${ lfn:message('fssc-common:table.fsscCommonTransferField') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ fsscCommonTransferFieldForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.fsscCommonTransferFieldForm, 'update');}" />
                    </c:when>
                    <c:when test="${ fsscCommonTransferFieldForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.fsscCommonTransferFieldForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('fssc-common:table.fsscCommonTransferField') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/fssc/common/fssc_common_transfer_field/fsscCommonTransferField.do">

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
						                <xform:dialog propertyId="fdSourceModelName" propertyName="fdSourceModelSubject" required="true" showStatus="edit" style="width:85%;">
						                	dialogSelect(false,'fssc_common_transfer_field_selectTable','fdSourceModelName','fdSourceTableName',{type:'source'},FSSC_SourceModelSelected);
						                </xform:dialog>
						                <xform:text property="fdSourceTableName" showStatus="noShow"/>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-common:fsscCommonTransferField.fdTargetModelSubject')}
                                </td>
                                <td width="35%">
                                    <%-- 目标表名--%>
                                    <xform:dialog propertyId="fdTargetModelName" propertyName="fdTargetModelSubject" required="true" showStatus="edit" style="width:85%;">
						                	dialogSelect(false,'fssc_common_transfer_field_selectTable','fdTargetModelName','fdTargetTableName',{type:'target'},FSSC_TargetModelSelected);
						                </xform:dialog>
						                <xform:text property="fdTargetTableName" showStatus="noShow"/>
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
                                            <td style="width:80px;">
                                            </td>
                                        </tr>
                                        <tr KMSS_IsReferRow="1" style="display:none;" class="docListTr">
                                            <td class="docList" align="center" KMSS_IsRowIndex="1">
                                                !{index}
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 原字段名--%>
                                                <input type="hidden" name="fdDetailLsit_Form[!{index}].fdId" value="" disabled="true" />
                                                <div id="_xform_fdDetailLsit_Form[!{index}].fdSourceField" _xform_type="text">
                                                    <xform:dialog propertyId="fdDetailLsit_Form[!{index}].fdSourceField" propertyName="fdDetailLsit_Form[!{index}].fdSourceFieldText" required="true" showStatus="edit" style="width:85%;">
									                	FSSC_SelectSourceField()
									                </xform:dialog>
									                <xform:text property="fdDetailLsit_Form[!{index}].fdSourceFieldName" showStatus="noShow"/>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 字段类型--%>
                                                <div id="_xform_fdDetailLsit_Form[!{index}].fdFieldType" _xform_type="select">
                                                    <xform:select property="fdDetailLsit_Form[!{index}].fdSourceFieldType" htmlElementProperties="id='fdDetailLsit_Form[!{index}].fdSourceFieldType'" showStatus="edit">
                                                        <xform:enumsDataSource enumsType="fssc_common_transfer_field_type" />
                                                    </xform:select>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 目标字段名--%>
                                                <div id="_xform_fdDetailLsit_Form[!{index}].fdTargetField" _xform_type="text">
                                                    <xform:dialog propertyId="fdDetailLsit_Form[!{index}].fdTargetField" propertyName="fdDetailLsit_Form[!{index}].fdTargetFieldText" required="true" showStatus="edit" style="width:85%;">
									                	FSSC_SelectTargetField()
									                </xform:dialog>
									                <xform:text property="fdDetailLsit_Form[!{index}].fdTargetFieldName" showStatus="noShow"/>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 字段类型--%>
                                                <div id="_xform_fdDetailLsit_Form[!{index}].fdFieldType" _xform_type="select">
                                                    <xform:select property="fdDetailLsit_Form[!{index}].fdTargetFieldType" htmlElementProperties="id='fdDetailLsit_Form[!{index}].fdTargetFieldType'" showStatus="edit">
                                                        <xform:enumsDataSource enumsType="fssc_common_transfer_field_type" />
                                                    </xform:select>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
                                                    <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
                                                </a>
                                                &nbsp;
                                                <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                                    <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                                                </a>
                                            </td>
                                        </tr>
                                        <c:forEach items="${fsscCommonTransferFieldForm.fdDetailLsit_Form}" var="fdDetailLsit_FormItem" varStatus="vstatus">
                                            <tr KMSS_IsContentRow="1" class="docListTr">
                                                <td class="docList" align="center">
                                                    ${vstatus.index+1}
                                                </td>
                                                <td class="docList" align="center">
                                                <%-- 原字段名--%>
                                                <input type="hidden" name="fdDetailLsit_Form[${vstatus.index}].fdId" value="${fdDetailLsit_FormItem.fdId }"/>
                                                <input type="hidden" name="fdDetailLsit_Form[${vstatus.index}].fdSourceFetchTable" value="${fdDetailLsit_FormItem.fdSourceFetchTable }"/>
                                                <input type="hidden" name="fdDetailLsit_Form[${vstatus.index}].fdSourceFetchFrom" value="${fdDetailLsit_FormItem.fdSourceFetchFrom }"/>
                                                <input type="hidden" name="fdDetailLsit_Form[${vstatus.index}].fdSourceFetchTo" value="${fdDetailLsit_FormItem.fdSourceFetchTo }"/>
                                                <input type="hidden" name="fdDetailLsit_Form[${vstatus.index}].fdTargetFetchTable" value="${fdDetailLsit_FormItem.fdTargetFetchTable }"/>
                                                <input type="hidden" name="fdDetailLsit_Form[${vstatus.index}].fdTargetFetchFrom" value="${fdDetailLsit_FormItem.fdTargetFetchFrom }"/>
                                                <input type="hidden" name="fdDetailLsit_Form[${vstatus.index}].fdTargetFetchTo" value="${fdDetailLsit_FormItem.fdTargetFetchTo }"/>
                                                <div id="_xform_fdDetailLsit_Form[${vstatus.index}].fdSourceField" _xform_type="text">
                                                    <xform:dialog propertyId="fdDetailLsit_Form[${vstatus.index}].fdSourceField" propertyName="fdDetailLsit_Form[${vstatus.index}].fdSourceFieldText" required="true" showStatus="edit" style="width:85%;">
									                	FSSC_SelectSourceField()
									                </xform:dialog>
									                <xform:text property="fdDetailLsit_Form[${vstatus.index}].fdSourceFieldName" showStatus="noShow"/>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                    <%-- 字段类型--%>
                                                    <div id="_xform_fdDetailLsit_Form[${vstatus.index}].fdFieldType" _xform_type="select">
                                                        <xform:select property="fdDetailLsit_Form[${vstatus.index}].fdSourceFieldType" htmlElementProperties="id='fdDetailLsit_Form[${vstatus.index}].fdSourceFieldType'" showStatus="edit">
                                                            <xform:enumsDataSource enumsType="fssc_common_transfer_field_type" />
                                                        </xform:select>
                                                    </div>
                                                </td>
                                            <td class="docList" align="center">
                                                <%-- 目标字段名--%>
                                                <div id="_xform_fdDetailLsit_Form[${vstatus.index}].fdTargetField" _xform_type="text">
                                                    <xform:dialog propertyId="fdDetailLsit_Form[${vstatus.index}].fdTargetField" propertyName="fdDetailLsit_Form[${vstatus.index}].fdTargetFieldText" required="true" showStatus="edit" style="width:85%;">
									                	FSSC_SelectTargetField()
									                </xform:dialog>
									                <xform:text property="fdDetailLsit_Form[${vstatus.index}].fdTargetFieldName" showStatus="noShow"/>
                                                </div>
                                            </td>
                                                <td class="docList" align="center">
                                                    <%-- 字段类型--%>
                                                    <div id="_xform_fdDetailLsit_Form[${vstatus.index}].fdFieldType" _xform_type="select">
                                                        <xform:select property="fdDetailLsit_Form[${vstatus.index}].fdTargetFieldType" htmlElementProperties="id='fdDetailLsit_Form[${vstatus.index}].fdTargetFieldType'" showStatus="edit">
                                                            <xform:enumsDataSource enumsType="fssc_common_transfer_field_type" />
                                                        </xform:select>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
                                                        <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
                                                    </a>
                                                    &nbsp;
                                                    <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                                        <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <tr type="optRow" class="tr_normal_opt" invalidrow="true">
                                            <td colspan="6">
                                                <a href="javascript:void(0);" onclick="DocList_AddRow();">
                                                    <img src="${KMSS_Parameter_StylePath}icons/icon_add.png" border="0" />${lfn:message('doclist.add')}
                                                </a>
                                                &nbsp;
                                                <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);">
                                                    <img src="${KMSS_Parameter_StylePath}icons/icon_up.png" border="0" />${lfn:message('doclist.moveup')}
                                                </a>
                                                &nbsp;
                                                <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);">
                                                    <img src="${KMSS_Parameter_StylePath}icons/icon_down.png" border="0" />${lfn:message('doclist.movedown')}
                                                </a>
                                                &nbsp;
                                            </td>
                                        </tr>
                                    </table>
                                    <input type="hidden" name="fdDetailLsit_Flag" value="1">
                                    <script>
                                        Com_IncludeFile("doclist.js");
                                    </script>
                                    <script>
                                        DocList_Info.push('TABLE_DocList_fdDetailLsit_Form');
                                    </script>
                                </td>
                            </tr>
                        </table>
                <html:hidden property="fdId" />


                <html:hidden property="method_GET" />
            </html:form>
        </template:replace>


    </template:include>