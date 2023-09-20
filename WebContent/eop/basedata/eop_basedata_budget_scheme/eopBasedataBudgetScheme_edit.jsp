<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
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
    	fdCompanyGroup:'${fdCompanyGroup}',
    	dbDimension:"${eopBasedataBudgetSchemeForm.fdDimension}"
    };
    var messageInfo = {

    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js|common.js|data.js");
    Com_IncludeFile("config_fssc_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_budget_scheme/", 'js', true);
    Com_IncludeFile("fsscBudgetScheme.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_budget_scheme/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_budget_scheme/eopBasedataBudgetScheme.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataBudgetSchemeForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataBudgetSchemeForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataBudgetSchemeForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataBudgetSchemeForm, 'save');">
                <input type="button" value="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.eopBasedataBudgetSchemeForm, 'saveadd');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataBudgetScheme') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" validators="checkName" showStatus="edit" style="width:95%;" />
                            <xform:text property="fdHiddenName" showStatus="noShow" value="${eopBasedataBudgetSchemeForm.fdName}"></xform:text>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdCode')}
                    </td>
                     <td colspan="3" width="85.0%">
                    	<%-- 编码--%>
	                    <c:choose>
	                        <c:when test="${not empty eopBasedataBudgetSchemeForm.fdCode}">
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
                        ${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdDimension')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdDimension" _xform_type="checkbox">
                        <fssc:checkVersion version="true">
                            <xform:checkbox property="fdDimension" showStatus="edit" onValueChange="changeDimension">
                                <xform:enumsDataSource enumsType="eop_basedata_budget_dimension" />
                            </xform:checkbox>
                        </fssc:checkVersion>
                        <fssc:checkVersion version="false">
                            <xform:checkbox property="fdDimension" showStatus="edit">
                                <xform:enumsDataSource enumsType="eop_basedata_budget_dimension_ekp" />
                            </xform:checkbox>
                        </fssc:checkVersion>
                        <c:if test="${version=='true' }">
                        <br><span class="com_help">${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdDimension.tips')}</span>
                        </c:if>
                         <input type="hidden" name="version" value="${version }"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdType')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdType" _xform_type="radio">
                            <xform:radio property="fdType" showStatus="edit">
                                <xform:enumsDataSource enumsType="eop_basedata_dimension_type" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdPeriod')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdPeriod" _xform_type="radio">
                            <xform:checkbox property="fdPeriod" showStatus="edit" onValueChange="changeValue">
                                <xform:enumsDataSource enumsType="eop_basedata_budget_period" />
                            </xform:checkbox>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdCompanys')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCompanys" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyIds" propertyName="fdCompanyNames" textarea="true" subject="${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdCompanys')}" showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyIds','fdCompanyNames');
                            </xform:dialog>
                            <br><span class="com_help">${lfn:message('eop-basedata:budget.scheme.company.tips')}</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdOrder')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdIsAvailable')}
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
                        ${lfn:message('eop-basedata:eopBasedataBudgetScheme.docCreator')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataBudgetSchemeForm.docCreatorId}" personName="${eopBasedataBudgetSchemeForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataBudgetScheme.docCreateTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataBudgetScheme.docAlteror')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${eopBasedataBudgetSchemeForm.docAlterorId}" personName="${eopBasedataBudgetSchemeForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataBudgetScheme.docAlterTime')}
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
