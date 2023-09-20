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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_accounts/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_accounts/eopBasedataAccounts.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataAccountsForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataAccountsForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataAccountsForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataAccountsForm, 'save');">
                <input type="button" value="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.eopBasedataAccountsForm, 'saveadd');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataAccounts') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAccounts.fdCompanyList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCompanyId" _xform_type="dialog">
                        	<xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataAccounts.fdCompanyList')}"  showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames',afterChangeCompany);
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAccounts.fdParent')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdParentId" _xform_type="dialog">
                        	<xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataAccounts.fdParent')}" style="width:95%;">
                                dialogSelect(false,'eop_basedata_accounts_fdAccount','fdParentId','fdParentName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()});
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAccounts.fdName')}
                    </td>
	                <td colspan="3" width="85.0%">
	                        <div id="_xform_fdName" _xform_type="text">
	                        	<xform:text property="fdName" showStatus="edit" style="width:95%;" />
	                        </div>
	                 </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAccounts.fdCode')}
                    </td>
                    <td colspan="3" width="85.0%">
                    	<%-- 编码--%>
	                    <c:choose>
	                        <c:when test="${not empty eopBasedataAccountsForm.fdCode}">
		                        <div id="_xform_fdCode" _xform_type="text">
		                        	<xform:text property="fdCode" showStatus="view" style="width:95%;" />
		                        </div>
	                    	</c:when>
	                    	<c:otherwise>
		                        <div id="_xform_fdCode" _xform_type="text">
		                        	<xform:text property="fdCode" showStatus="edit" style="width:95%;" />
		                        </div>
		                    </c:otherwise>
		                </c:choose>
	                </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAccounts.fdIsAvailable')}
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
                        ${lfn:message('eop-basedata:eopBasedataAccounts.fdCostItem')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCostItem" _xform_type="radio">
                            <xform:checkbox property="fdCostItem" showStatus="edit">
                                <xform:enumsDataSource enumsType="eop_basedata_cost_item" />
                            </xform:checkbox>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAccounts.docCreator')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataAccountsForm.docCreatorId}" personName="${eopBasedataAccountsForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAccounts.docCreateTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAccounts.docAlteror')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${eopBasedataAccountsForm.docAlterorId}" personName="${eopBasedataAccountsForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataAccounts.docAlterTime')}
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
