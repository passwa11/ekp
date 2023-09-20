<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_out_tax/", 'js', true);
</script>

<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_out_tax/eopBasedataOutTax.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataOutTaxForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataOutTaxForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataOutTaxForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataOutTaxForm, 'save');">
                <input type="button" value="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.eopBasedataOutTaxForm, 'saveadd');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataOutTax') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataOutTax.fdCompanyList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCompanyListIds" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataOutTax.fdCompanyList')}" showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames',selectFdCompanyNameCallback);
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataOutTax.fdRate')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdRate" _xform_type="text">
                            <xform:text property="fdRate" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataOutTax.fdAccount')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdAccountId" _xform_type="dialog">
                            <xform:dialog propertyId="fdAccountId" propertyName="fdAccountName" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataOutTax.fdAccount')}" style="width:95%;">
                                dialogSelect(false,'eop_basedata_accounts_com_fdAccount','fdAccountId','fdAccountName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()});
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataOutTax.fdOrder')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataOutTax.fdIsAvailable')}
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
                        ${lfn:message('eop-basedata:eopBasedataOutTax.docCreator')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataOutTaxForm.docCreatorId}" personName="${eopBasedataOutTaxForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataOutTax.docCreateTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataOutTax.docAlteror')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${eopBasedataOutTaxForm.docAlterorId}" personName="${eopBasedataOutTaxForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataOutTax.docAlterTime')}
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
        //选择公司回调函数
        function selectFdCompanyNameCallback(rtnData){
            $("input[name='fdAccountId']").val("");
            $("input[name='fdAccountName']").val("");
        }
        Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
