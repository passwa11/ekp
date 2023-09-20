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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_standard/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_standard/eopBasedataStandard.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataStandardForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataStandardForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataStandardForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataStandardForm, 'save');">
                <input type="button" value="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.eopBasedataStandardForm, 'saveadd');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataStandard') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdCompanyList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCompanyId" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataStandard.fdCompanyList')}" showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames',changeCompany);
                            </xform:dialog>
                        </div>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdItem')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdItemId" _xform_type="dialog">
                            <xform:dialog propertyId="fdItemId" propertyName="fdItemName" subject="${lfn:message('eop-basedata:eopBasedataStandard.fdItem')}" showStatus="edit" required="true" style="width:95%;">
                                dialogSelect(false,'eop_basedata_expense_item_fdParent','fdItemId','fdItemName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val(),multi:'true'});
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdPerson')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdLevelId" _xform_type="dialog">
                            <xform:address propertyName="fdPersonName" propertyId="fdPersonId" orgType="ORG_TYPE_PERSON" style="width:95%;"></xform:address>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdDept')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdDeptId" _xform_type="dialog">
                            <xform:address propertyName="fdDeptName" propertyId="fdDeptId" orgType="ORG_TYPE_DEPT" style="width:95%;"></xform:address>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdLevel')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdLevelId" _xform_type="dialog">
                            <xform:dialog propertyId="fdLevelId" propertyName="fdLevelName" subject="${lfn:message('eop-basedata:eopBasedataStandard.fdLevel')}"  showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_level_fdLevel','fdLevelId','fdLevelName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()});
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdArea')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdAreaId" _xform_type="dialog">
                            <xform:dialog propertyId="fdAreaId" propertyName="fdAreaName" subject="${lfn:message('eop-basedata:eopBasedataStandard.fdArea')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_area_fdArea','fdAreaId','fdAreaName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()});
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdCity')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdAreaId" _xform_type="dialog">
                            <xform:dialog propertyId="fdCityId" propertyName="fdCityName" subject="${lfn:message('eop-basedata:eopBasedataStandard.fdCity')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_city_selectCity','fdCityId','fdCityName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()});
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdVehicle')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdVehicleId" _xform_type="dialog">
                            <xform:dialog propertyId="fdVehicleId" propertyName="fdVehicleName" subject="${lfn:message('eop-basedata:eopBasedataStandard.fdVehicle')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_vehicle_fdVehicle','fdVehicleId','fdVehicleName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()},afterVehicleSelected);
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdBerth')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdBerthId" _xform_type="dialog">
                            <xform:dialog propertyId="fdBerthId" propertyName="fdBerthName" subject="${lfn:message('eop-basedata:eopBasedataStandard.fdBerth')}" showStatus="edit" style="width:95%;">
                                FSSC_SelectBerth();
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <%-- <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdSpecialItem')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdBerthId" _xform_type="dialog">
                            <xform:dialog propertyId="fdSpecialItemId" propertyName="fdSpecialItemName" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_special_item','fdSpecialItemId','fdSpecialItemName');
                            </xform:dialog>
                        </div>
                    </td>
                </tr> --%>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdMoney')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdMoney" _xform_type="text">
                            <xform:text property="fdMoney" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdCurrency')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCurrencyId" _xform_type="dialog">
                            <xform:dialog propertyId="fdCurrencyId" propertyName="fdCurrencyName" subject="${lfn:message('eop-basedata:eopBasedataStandard.fdCurrency')}" showStatus="edit" required="true" style="width:95%;">
                                dialogSelect(false,'eop_basedata_currency_fdCurrency','fdCurrencyId','fdCurrencyName');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdOrder')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.fdIsAvailable')}
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
                        ${lfn:message('eop-basedata:eopBasedataStandard.docCreator')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataStandardForm.docCreatorId}" personName="${eopBasedataStandardForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.docCreateTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.docAlteror')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${eopBasedataStandardForm.docAlterorId}" personName="${eopBasedataStandardForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataStandard.docAlterTime')}
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
        function FSSC_SelectBerth(){
        	var fdVehicleId = $("[name=fdVehicleId]").val();
            var fdCompanyId=$("input[name='fdCompanyListIds']").val();
        	if(!fdVehicleId){
        		seajs.use(['lui/dialog'],function(dialog){
        			dialog.alert("${lfn:message('eop-basedata:message.standard.pleaseSelectVehicle')}");
        		});
        		return false;
        	}
        	dialogSelect(false,'eop_basedata_berth_fdBerth','fdBerthId','fdBerthName',null,{"fdVehicleId":$("[name=fdVehicleId]").val(),fdCompanyId:fdCompanyId});
        }
        function afterVehicleSelected(){
        	$("[name=fdBerthId],[name=fdBerthName]").val("");
        }
        function changeCompany(){
        	$("[name=fdAreaId],[name=fdAreaName],[name=fdItemId],[name=fdItemName],[name=fdBerthId],[name=fdBerthName]").val("");
        	$("[name=fdLevelId],[name=fdLevelName],[name=fdVehicleId],[name=fdVehicleName],[name=fdCityId],[name=fdCityName]").val("");
        }
        Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
