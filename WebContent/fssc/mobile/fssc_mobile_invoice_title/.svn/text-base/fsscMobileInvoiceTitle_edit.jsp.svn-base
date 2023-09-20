<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
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
    if("${fsscMobileInvoiceTitleForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('fssc-mobile:table.fsscMobileInvoiceTitle') }";
    }
    if("${fsscMobileInvoiceTitleForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('fssc-mobile:table.fsscMobileInvoiceTitle') }";
    }
    var formInitData = {

    };
    var messageInfo = {

    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/mobile/fssc_mobile_invoice_title/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/mobile/fssc_mobile_invoice_title/fsscMobileInvoiceTitle.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscMobileInvoiceTitleForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.fsscMobileInvoiceTitleForm, 'update');}">
            </c:when>
            <c:when test="${fsscMobileInvoiceTitleForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.fsscMobileInvoiceTitleForm, 'save');}">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-mobile:table.fsscMobileInvoiceTitle') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdTaxNo')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 税号--%>
                        <div id="_xform_fdTaxNo" _xform_type="text">
                            <xform:text property="fdTaxNo" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdAddress')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 单位地址--%>
                        <div id="_xform_fdAddress" _xform_type="text">
                            <xform:text property="fdAddress" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdPhone')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 电话号码--%>
                        <div id="_xform_fdPhone" _xform_type="text">
                            <xform:text property="fdPhone" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdBankName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 开户银行--%>
                        <div id="_xform_fdBankName" _xform_type="text">
                            <xform:text property="fdBankName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdBankAccount')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 银行账户--%>
                        <div id="_xform_fdBankAccount" _xform_type="text">
                            <xform:text property="fdBankAccount" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdUserList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 可使用者--%>
                        <div id="_xform_fdUserListIds" _xform_type="address">
                            <xform:address propertyId="fdUserListIds" propertyName="fdUserListNames" mulSelect="true" orgType="ORG_TYPE_ALLORG" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdEditorList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 可维护者--%>
                        <div id="_xform_fdEditorListIds" _xform_type="address">
                            <xform:address propertyId="fdEditorListIds" propertyName="fdEditorListNames" mulSelect="true" orgType="ORG_TYPE_ALLORG" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 创建人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${fsscMobileInvoiceTitleForm.docCreatorId}" personName="${fsscMobileInvoiceTitleForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.docCreateTime')}
                    </td>
                    <td width="35%">
                        <%-- 创建时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
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
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
