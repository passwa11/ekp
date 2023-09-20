<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
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
	"eopBasedataCostCenter.fdType":"${lfn:message('eop-basedata:eopBasedataCostCenter.fdType')}",
	"eopBasedataCostCenter.fdSystemParam.U8":"${lfn:message('eop-basedata:eopBasedataCostCenter.fdSystemParam.U8')}",
	"eopBasedataCostCenter.fdSystemParam.K3":"${lfn:message('eop-basedata:eopBasedataCostCenter.fdSystemParam.K3')}"
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("common.js|data.js");
    Com_IncludeFile("config_fssc_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_cost_center/", 'js', true);
    Com_IncludeFile("fsscCostCenter.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_cost_center/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenter.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataCostCenterForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataCostCenterForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataCostCenterForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataCostCenterForm, 'save');">
                <input type="button" value="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.eopBasedataCostCenterForm, 'saveadd');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataCostCenter') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.fdCompanyList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCompanyId" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataCostCenter.fdCompanyList')}" showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames',afterSelect);
                            </xform:dialog>
                        </div>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.fdParent')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdParentId" _xform_type="dialog">
                            <xform:dialog propertyId="fdParentId" propertyName="fdParentName" subject="${lfn:message('eop-basedata:eopBasedataCostCenter.fdParent')}"  showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_cost_center_fdParent','fdParentId','fdParentName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()});
                            </xform:dialog>
                        </div>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.fdName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.fdCode')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCode" _xform_type="text">
                            <c:if test="${empty eopBasedataCostCenterForm.fdCode}">
                        		<xform:text property="fdCode" showStatus="edit" required="true" style="width:95%;" />
                        	</c:if>
                        	<c:if test="${not empty eopBasedataCostCenterForm.fdCode}">
                        		<xform:text property="fdCode" showStatus="readOnly" style="width:95%;color:#333;" />
                        	</c:if>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.fdIsGroup')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdIsGroup" _xform_type="radio">
                            <xform:radio property="fdIsGroup" showStatus="edit" required="true">
                                <xform:enumsDataSource enumsType="eop_basedata_cost_type" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.fdEkpOrg')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdEkpOrgIds" _xform_type="address">
                            <xform:address propertyId="fdEkpOrgIds" propertyName="fdEkpOrgNames" mulSelect="true" orgType="ORG_TYPE_ALLORG" showStatus="edit" textarea="true" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.fdFirstCharger')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdFirstChargerIds" _xform_type="address">
                            <xform:address propertyId="fdFirstChargerIds" propertyName="fdFirstChargerNames" mulSelect="true" orgType="ORG_TYPE_ALLORG" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.fdSecondCharger')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdSecondChargerIds" _xform_type="address">
                            <xform:address propertyId="fdSecondChargerIds" propertyName="fdSecondChargerNames" mulSelect="true" orgType="ORG_TYPE_ALLORG" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.fdManager')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdManagerIds" _xform_type="address">
                            <xform:address propertyId="fdManagerIds" propertyName="fdManagerNames" mulSelect="true" orgType="ORG_TYPE_ALLORG" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.fdBudgetManager')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdBudgetManagerIds" _xform_type="address">
                            <xform:address propertyId="fdBudgetManagerIds" propertyName="fdBudgetManagerNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.fdType')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdTypeId" _xform_type="dialog">
                        	<xform:radio property="fdTypeId" required="true" value="${eopBasedataCostTypeForm.fdTypeId}">
                        		<xform:beanDataSource serviceBean="eopBasedataCostTypeService" selectBlock="fdId,fdName" whereBlock="eopBasedataCostType.fdIsAvailable=true"></xform:beanDataSource>
                        	</xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('eop-basedata:eopBasedataCostCenter.fdOrder')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.fdIsAvailable')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdIsAvailable" _xform_type="radio">
                            <xform:radio property="fdIsAvailable" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <fssc:switchOn property="fdCostcenterToAccount">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.fdJoinSystem')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdJoinSystem" _xform_type="text">
                            <xform:radio property="fdJoinSystem" value="${eopBasedataCostCenterForm.fdJoinSystem}" onValueChange="changeSystem">
                            	<c:forEach items="${financialSystemList}" var="system">
                            		<xform:simpleDataSource value="${system}"></xform:simpleDataSource>
                            	</c:forEach>
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr style="display:none;" id="systemParam">
                    <td class="td_normal_title" width="15%" id="systemTitle">
                        
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdJoinSystem" _xform_type="text">
                            <xform:text property="fdSystemParam" style="width:95%;"></xform:text>
                        </div>
                    </td>
                </tr>
                </fssc:switchOn>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.docCreator')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataCostCenterForm.docCreatorId}" personName="${eopBasedataCostCenterForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.docCreateTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.docAlteror')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${eopBasedataCostCenterForm.docAlterorId}" personName="${eopBasedataCostCenterForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCostCenter.docAlterTime')}
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
    <html:hidden property="fdType" value="${eopBasedataCostCenterForm.fdTypeId}" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
        Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
