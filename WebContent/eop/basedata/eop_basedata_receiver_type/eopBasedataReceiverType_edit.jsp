<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
    <head>
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

                };

                var initData = {
                    contextPath: '${LUI_ContextPath}'
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_receiver_type/", 'js', true);
                Com_IncludeFile("config_fssc_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
    </head>

        <title>
            <c:choose>
                <c:when test="${eopBasedataReceiverTypeForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('eop-basedata:table.eopBasedataReceiverType') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${eopBasedataReceiverTypeForm.fdName} - " />
                    <c:out value="${ lfn:message('eop-basedata:table.eopBasedataReceiverType') }" />
                </c:otherwise>
            </c:choose>
        </title>
            <html:form action="/eop/basedata/eop_basedata_receiver_type/eopBasedataReceiverType.do">
                <div id="optBarDiv">
                    <c:choose>
                        <c:when test="${eopBasedataReceiverTypeForm.method_GET=='edit'}">
                            <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataReceiverTypeForm, 'update');">
                        </c:when>
                        <c:when test="${eopBasedataReceiverTypeForm.method_GET=='add'}">
                            <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataReceiverTypeForm, 'save');">
                        </c:when>
                    </c:choose>
                    <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
                </div>
                <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataReceiverType') }</p>
                <div style="width:95%;margin:0 auto;">
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                    ${lfn:message('eop-basedata:eopBasedataReceiverType.fdCompanyList')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdCompanyId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataReceiverType.fdCompanyList')}" showStatus="edit" style="width:95%;">
                                        dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames');
                                    </xform:dialog>
                                </div>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                    ${lfn:message('eop-basedata:eopBasedataReceiverType.fdName')}
                            </td>
                            <td width="35.0%">
                                    <%-- 名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                    ${lfn:message('eop-basedata:eopBasedataReceiverType.fdCode')}
                            </td>
                            <td width="35.0%">
                                    <%-- 编码--%>
                                <div id="_xform_fdCode" _xform_type="text">
                                    <xform:text property="fdCode" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                    ${lfn:message('eop-basedata:eopBasedataReceiverType.fdAccounts')}
                            </td>
                            <td width="35.0%">
                                    <%-- 会计科目--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:dialog propertyId="fdAccountsId" propertyName="fdAccountsName" subject="${lfn:message('eop-basedata:eopBasedataReceiverType.fdAccounts')}" required="true" showStatus="edit" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_accounts_com_fdAccount','fdAccountsId','fdAccountsName');
                                    </xform:dialog>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('eop-basedata:eopBasedataReceiverType.fdType')}
                            </td>
                            <td width="35.0%">
                                <div id="_xform_fdType" _xform_type="radio">
                                    <xform:radio property="fdType" required="true" showStatus="edit">
                                        <xform:enumsDataSource enumsType="eop_basedata_receiver_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                    ${lfn:message('eop-basedata:eopBasedataReceiverType.fdIsAvailable')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdIsAvailable" _xform_type="radio">
                                    <xform:radio property="fdIsAvailable" showStatus="edit">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                    ${lfn:message('eop-basedata:eopBasedataReceiverType.docCreateTime')}
                            </td>
                            <td width="35%">
                                    <%-- 创建时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                    ${lfn:message('eop-basedata:eopBasedataReceiverType.docCreator')}
                            </td>
                            <td width="35%">
                                    <%-- 创建人--%>
                                <div id="_xform_docCreatorId" _xform_type="address">
                                    <ui:person personId="${eopBasedataReceiverTypeForm.docCreatorId}" personName="${eopBasedataReceiverTypeForm.docCreatorName}" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <html:hidden property="fdId" />


                <html:hidden property="method_GET" />
                <script>
                    $KMSSValidation();
                    function changeCompany(){
                        $("[name=fdAccountsId],[name=fdAccountsName]").val("")
                    }
                    Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
                </script>
            </html:form>

