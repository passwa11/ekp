<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
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
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("config_fssc_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_card_info/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_card_info/eopBasedataCardInfo.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataCardInfoForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataCardInfoForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataCardInfoForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataCardInfoForm, 'save');">
                <input type="button" value="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.eopBasedataCardInfoForm, 'saveadd');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataCardInfo') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdCorNum')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCorNum" _xform_type="text">
                            <xform:text property="fdCorNum" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdCorChiName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCorChiName" _xform_type="text">
                            <xform:text property="fdCorChiName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdCompany')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCompanyId" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" subject="${lfn:message('eop-basedata:eopBasedataCardInfo.fdCompany')}" required="true" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                            </xform:dialog>
                        </div>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdActNum')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdActNum" _xform_type="text">
                            <xform:text property="fdActNum" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdAcctNbr')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdAcctNbr" _xform_type="text">
                            <xform:text property="fdAcctNbr" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdCardNumber')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCardNumber" _xform_type="text">
                            <xform:text property="fdCardNumber"  showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdHolder')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdHolderChiName" _xform_type="address">
                            <xform:address required="true" propertyId="fdHolderId" propertyName="fdHolderName" orgType="ORG_TYPE_PERSON" subject="${lfn:message('eop-basedata:eopBasedataCardInfo.fdHolder')}" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdHolderChiName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdHolderChiName" _xform_type="text">
                            <xform:text property="fdHolderChiName" showStatus="edit" style="width:95%;"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdHolderEngName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdHolderEngName" _xform_type="text">
                            <xform:text property="fdHolderEngName" showStatus="edit" style="width:95%;"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdEmpNumber')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdEmpNumber" _xform_type="text">
                            <xform:text property="fdEmpNumber" showStatus="edit" style="width:95%;"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdIsAvailable')}
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
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdActivationCode')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdActivationCode">
                            <xform:select property="fdActivationCode">
                                <xform:enumsDataSource enumsType="eop_basedata_cardInfo_activationCode" />
                            </xform:select>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdActivationDate')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdActivationDate" _xform_type="datetime">
                            <xform:datetime property="fdActivationDate" showStatus="edit" required="true" style="width:95%;" dateTimeType="date"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdCirculationFlag')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCirculationFlag">
                            <xform:select property="fdCirculationFlag">
                                <xform:enumsDataSource enumsType="eop_basedata_cardInfo_circulationFlag" />
                            </xform:select>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.fdCancelDate')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdCancelDate" _xform_type="datetime">
                            <xform:datetime property="fdCancelDate" showStatus="edit" required="true" style="width:95%;" dateTimeType="date"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.docCreator')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataCardInfoForm.docCreatorId}" personName="${eopBasedataCardInfoForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.docCreateTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.docAlteror')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${eopBasedataCardInfoForm.docAlterorId}" personName="${eopBasedataCardInfoForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCardInfo.docAlterTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterTime" _xform_type="datetime">
                            <xform:datetime property="docAlterTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
        Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
