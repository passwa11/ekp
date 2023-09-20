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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_tax_rate/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_tax_rate/eopBasedataTaxRate.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataTaxRateForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataTaxRateForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataTaxRateForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataTaxRateForm, 'save');">
                <input type="button" value="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.eopBasedataTaxRateForm, 'saveadd');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataTaxRate') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataTaxRate.fdCompany')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCompanyId" _xform_type="dialog">
                            <fssc:switchOff property="fdShowType">
	                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="view" style="width:95%;">
	                                dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
	                            </xform:dialog>
	                            <html:hidden property="fdCompanyId"/>
                            </fssc:switchOff>
                        	<fssc:switchOn property="fdShowType">
	                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" subject="${lfn:message('eop-basedata:eopBasedataTaxRate.fdCompany')}" showStatus="edit" required="true" style="width:95%;">
	                                dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName',changeCompany);
	                            </xform:dialog>
                            </fssc:switchOn>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataTaxRate.fdRate')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdRate" _xform_type="text">
                            <xform:text property="fdRate" showStatus="edit" style="width:95%;" />%
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataTaxRate.fdAccount')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdAccountId" _xform_type="dialog">
                            <xform:dialog propertyId="fdAccountId" propertyName="fdAccountName" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataTaxRate.fdAccount')}" style="width:95%;">
                                dialogSelect(false,'eop_basedata_accounts_com_fdAccount','fdAccountId','fdAccountName');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataTaxRate.fdOrder')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataTaxRate.fdIsAvailable')}
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
                        ${lfn:message('eop-basedata:eopBasedataTaxRate.docCreator')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataTaxRateForm.docCreatorId}" personName="${eopBasedataTaxRateForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataTaxRate.docCreateTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataTaxRate.docAlteror')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${eopBasedataTaxRateForm.docAlterorId}" personName="${eopBasedataTaxRateForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataTaxRate.docAlterTime')}
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
        function changeCompany(){
        	$("[name=fdAccountId],[name=fdAccountName]").val("")
        }
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
