<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
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
<div id="optBarDiv">

    <kmss:auth requestURL="/eop/basedata/eop_basedata_standard/eopBasedataStandard.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('eopBasedataStandard.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/eop/basedata/eop_basedata_standard/eopBasedataStandard.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('eopBasedataStandard.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
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
                        <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataStandard.fdCompanyList')}" showStatus="view" style="width:95%;">
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
                        <xform:dialog propertyId="fdItemId" propertyName="fdItemName" showStatus="view" style="width:95%;">
                            dialogSelect(false,'eop_basedata_expense_item_fdParent','fdItemId','fdItemName');
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
                    <div id="_xform_fdLevelId" _xform_type="dialog">
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
                        <xform:dialog propertyId="fdLevelId" propertyName="fdLevelName" showStatus="view" style="width:95%;">
                            dialogSelect(false,'eop_basedata_level_fdLevel','fdLevelId','fdLevelName');
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
                        <xform:dialog propertyId="fdAreaId" propertyName="fdAreaName" showStatus="view" style="width:95%;">
                            dialogSelect(false,'eop_basedata_area_fdArea','fdAreaId','fdAreaName');
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
                            <xform:dialog propertyId="fdCityId" propertyName="fdCityName" subject="${lfn:message('eop-basedata:eopBasedataStandard.fdCity')}" style="width:95%;">
                                dialogSelect(false,'eop_basedata_city_selectCity','fdCityId','fdCityName');
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
                        <xform:dialog propertyId="fdVehicleId" propertyName="fdVehicleName" showStatus="view" style="width:95%;">
                            dialogSelect(false,'eop_basedata_vehicle_fdVehicle','fdVehicleId','fdVehicleName');
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
                        <xform:dialog propertyId="fdBerthId" propertyName="fdBerthName" showStatus="view" style="width:95%;">
                            dialogSelect(false,'eop_basedata_berth_fdBerth','fdBerthId','fdBerthName');
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
                            <xform:dialog propertyId="fdSpecialItemId" propertyName="fdSpecialItemName" showStatus="view" style="width:95%;">
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
                        <xform:text property="fdMoney" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataStandard.fdCurrency')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdCurrencyId" _xform_type="dialog">
                        <xform:dialog propertyId="fdCurrencyId" propertyName="fdCurrencyName" showStatus="view" style="width:95%;">
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
                        <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataStandard.fdIsAvailable')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdIsAvailable" _xform_type="radio">
                        <xform:radio property="fdIsAvailable" showStatus="view">
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
<script>
    var formInitData = {

    };

    function confirmDelete(msg) {
        return confirm('${ lfn:message("page.comfirmDelete") }');
    }

    function openWindowViaDynamicForm(popurl, params, target) {
        var form = document.createElement('form');
        if(form) {
            try {
                target = !target ? '_blank' : target;
                form.style = "display:none;";
                form.method = 'post';
                form.action = popurl;
                form.target = target;
                if(params) {
                    for(var key in params) {
                        var
                        v = params[key];
                        var vt = typeof
                        v;
                        var hdn = document.createElement('input');
                        hdn.type = 'hidden';
                        hdn.name = key;
                        if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                            hdn.value =
                            v +'';
                        } else {
                            if($.isArray(
                                v)) {
                                hdn.value =
                                v.join(';');
                            } else {
                                hdn.value = toString(
                                    v);
                            }
                        }
                        form.appendChild(hdn);
                    }
                }
                document.body.appendChild(form);
                form.submit();
            } finally {
                document.body.removeChild(form);
            }
        }
    }

    function doCustomOpt(fdId, optCode) {
        if(!fdId || !optCode) {
            return;
        }

        if(viewOption.customOpts && viewOption.customOpts[optCode]) {
            var param = {
                "List_Selected_Count": 1
            };
            var argsObject = viewOption.customOpts[optCode];
            if(argsObject.popup == 'true') {
                var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                for(var arg in argsObject) {
                    param[arg] = argsObject[arg];
                }
                openWindowViaDynamicForm(popurl, param, '_self');
                return;
            }
            var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
            Com_OpenWindow(optAction, '_self');
        }
    }
    window.doCustomOpt = doCustomOpt;
    var viewOption = {
        contextPath: '${LUI_ContextPath}',
        basePath: '/eop/basedata/eop_basedata_standard/eopBasedataStandard.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
