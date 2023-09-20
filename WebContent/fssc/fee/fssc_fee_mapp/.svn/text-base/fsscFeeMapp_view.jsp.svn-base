<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
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
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/fssc/fee/fssc_fee_mapp/fsscFeeMapp.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscFeeMapp.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/fee/fssc_fee_mapp/fsscFeeMapp.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscFeeMapp.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('fssc-fee:table.fsscFeeMapp') }</p>
<center>

    <div style="width:95%;">
       <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName"  style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdTemplate')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 模板--%>
                        <div id="_xform_fdTemplateId" _xform_type="dialog">
                            <xform:dialog propertyId="fdTemplateId" propertyName="fdTemplateName" required="true" style="width:95%;">
                                dialogSelect(false,'fssc_fee_template_getTemplate','fdTemplateId','fdTemplateName');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdTable')}
                    </td>
                    <td width="35%">
                        <%-- 公司--%>
                        <div id="_xform_fdTableId" _xform_type="text">
                            <xform:dialog propertyName="fdTable" propertyId="fdTableId" style="width:95%;">
                            	selectFormula('fdTableId','fdTable');
                            </xform:dialog>
                        </div>
                    </td>
                    
                
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdCompany')}
                    </td>
                    <td width="35%">
                        <%-- 公司--%>
                        <div id="_xform_fdCompanyId" _xform_type="text">
                            <xform:dialog propertyName="fdCompany" propertyId="fdCompanyId" style="width:95%;">
                            	selectFormula('fdCompanyId','fdCompany');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdCostCenter')}
                    </td>
                    <td width="35%">
                        <%-- 成本中心--%>
                        <div id="_xform_fdCostCenterId" _xform_type="text">
                            <xform:dialog propertyName="fdCostCenter" propertyId="fdCostCenterId" style="width:95%;">
                            	selectFormula('fdCostCenterId','fdCostCenter');
                            </xform:dialog>
                        </div>
                    </td>
                
                
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdExpenseItem')}
                    </td>
                    <td width="35%">
                        <%-- 费用类型--%>
                        <div id="_xform_fdExpenseItem" _xform_type="text">
                            <xform:dialog propertyName="fdExpenseItem" propertyId="fdExpenseItemId" style="width:95%;">
                            	selectFormula('fdExpenseItemId','fdExpenseItem');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdProject')}
                    </td>
                    <td width="35%">
                        <%-- 项目--%>
                        <div id="_xform_fdProject" _xform_type="text">
                            <xform:dialog propertyName="fdProject" propertyId="fdProjectId" style="width:95%;">
                            	selectFormula('fdProjectId','fdProject');
                            </xform:dialog>
                        </div>
                    </td>
                
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdInnerOrder')}
                    </td>
                    <td width="35%">
                        <%-- 内部订单--%>
                        <div id="_xform_fdInnerOrder" _xform_type="text">
                            <xform:dialog propertyName="fdInnerOrder" propertyId="fdInnerOrderId" style="width:95%;">
                            	selectFormula('fdInnerOrderId','fdInnerOrder');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdWbs')}
                    </td>
                
                    <td width="35%">
                        <%-- WBS--%>
                        <div id="_xform_fdWbs" _xform_type="text">
                            <xform:dialog propertyName="fdWbs" propertyId="fdWbsId" style="width:95%;">
                            	selectFormula('fdWbsId','fdWbs');
                            </xform:dialog>
                        </div>
                    </td>
                
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdDept')}
                    </td>
                    <td width="35%">
                        <%-- 部门--%>
                        <div id="_xform_fdDept" _xform_type="text">
                            <xform:dialog propertyName="fdDept" propertyId="fdDeptId" style="width:95%;">
                            	selectFormula('fdDeptId','fdDept');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdPerson')}
                    </td>
                    <td width="35%">
                        <%-- 人员--%>
                        <div id="_xform_fdPerson" _xform_type="text">
                            <xform:dialog propertyName="fdPerson" propertyId="fdPersonId" style="width:95%;">
                            	selectFormula('fdPersonId','fdPerson');
                            </xform:dialog>
                        </div>
                    </td>
                
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdMoney')}
                    </td>
                    <td width="35%">
                        <%-- 金额--%>
                        <div id="_xform_fdMoney" _xform_type="text">
                            <xform:dialog propertyName="fdMoney" propertyId="fdMoneyId" style="width:95%;">
                            	selectFormula('fdMoneyId','fdMoney');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <fssc:checkVersion version="true">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdCurrency')}
                    </td>
                    <td width="35%">
                        <%-- 人员--%>
                        <div id="_xform_fdPerson" _xform_type="text">
                            <xform:dialog propertyName="fdCurrency" propertyId="fdCurrencyId" style="width:95%;">
                            	selectFormula('fdCurrencyId','fdCurrency');
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdRule')}
                    </td>
                    <td width="35%">
                        <%-- 成本中心--%>
                        <div id="_xform_fdRuleId" _xform_type="text">
                            <xform:dialog propertyName="fdRule" propertyId="fdRuleId" style="width:95%;">
                            	selectFormula('fdRuleId','fdRule');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdTravelDays')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdPerson" _xform_type="text">
                            <xform:dialog propertyName="fdTravelDays" propertyId="fdTravelDaysId" style="width:95%;">
                            	selectFormula('fdTravelDaysId','fdTravelDays');
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdPersonNum')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdRuleId" _xform_type="text">
                            <xform:dialog propertyName="fdPersonNum" propertyId="fdPersonNumId" style="width:95%;">
                            	selectFormula('fdPersonNumId','fdPersonNum');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdArea')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdPerson" _xform_type="text">
                            <xform:dialog propertyName="fdArea" propertyId="fdAreaId" style="width:95%;">
                            	selectFormula('fdAreaId','fdArea');
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdVehicle')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdRuleId" _xform_type="text">
                            <xform:dialog propertyName="fdVehicle" propertyId="fdVehicleId" style="width:95%;">
                            	selectFormula('fdVehicleId','fdVehicle');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeMapp.fdStandard')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdPerson" _xform_type="text">
                            <xform:dialog propertyName="fdStandard" propertyId="fdStandardId" style="width:95%;">
                            	selectFormula('fdStandardId','fdStandard');
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                    </td>
                    <td width="35%">
                    </td>
                </tr>
                </fssc:checkVersion>
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
        basePath: '/fssc/fee/fssc_fee_mapp/fsscFeeMapp.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
