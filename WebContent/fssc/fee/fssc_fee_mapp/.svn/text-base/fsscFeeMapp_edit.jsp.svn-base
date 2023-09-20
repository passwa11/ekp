<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
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
    var formInitData = {

    };
    var messageInfo = {

    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/fee/fssc_fee_mapp/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/fee/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/fee/fssc_fee_mapp/fsscFeeMapp.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscFeeMappForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscFeeMappForm, 'update');">
            </c:when>
            <c:when test="${fsscFeeMappForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscFeeMappForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
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
                            <xform:text property="fdName" style="width:95%;" />
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
                            <xform:dialog propertyId="fdTemplateId" propertyName="fdTemplateName" subject="${lfn:message('fssc-fee:fsscFeeMapp.fdTemplate') }"  required="true" style="width:95%;">
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
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
        Com_IncludeFile("formula.js");
        function XForm_Util_UnitArray(array, sysArray, extArray) {
    		<%-- // 合并 --%>
    		array = array.concat(sysArray);
    		if (extArray != null) {
    			array = array.concat(extArray);
    		}
    		<%-- // 结果 --%>
    		return array;
    	}
    	
    	function selectFormula(id,name){
    		var tempId = $("input[name='fdTemplateId']").val();
    		if(typeof tempId=="undefined" ||null==tempId||""==tempId){
                seajs.use(['lui/jquery','lui/dialog','lang!fssc-fee'], function($, dialog,lang) {
                    dialog.alert(lang["tips.selectTemplateFirst"]);
                });
    			return ;
    		}else{
                Formula_Dialog(id, name, Formula_GetVarInfoByModelName_New('com.landray.kmss.fssc.fee.model.FsscFeeMain',tempId),'String');
    		}
    	}
    	function Formula_GetVarInfoByModelName_New(modelName,tempId){
    		var obj = [];
    		var sysObj = new KMSSData().AddBeanData("sysFormulaDictVarTree&authCurrent=true&modelName="+modelName).GetHashMapArray();
    		var extObj = new KMSSData().AddBeanData("fsscFeeSysDictExtendModelService&authCurrent=true&tempType=template&tempId="+tempId).GetHashMapArray();
    		return XForm_Util_UnitArray(obj, sysObj, extObj);
    	}
    	//提交时校验模板是否已经映射
    	Com_Parameter.event.submit.push(function(){
    		var pass = true;
    		var data = new KMSSData();
    		data.AddBeanData("fsscFeeDataService&type=checkTemplateMappExist&fdTemplateId="+$("[name='fdTemplateId']").val()+"&fdId="+$("[name='fdId']").val());
    		data = data.GetHashMapArray();
    		if(data&&data.length>0){
    			if(data[0].exists=='true'){
    				seajs.use(['lui/jquery','lui/dialog','lang!fssc-fee'], function($, dialog,lang) {
                        dialog.alert(lang["tips.templateMappExists"]);
                    });
    				pass = false;
    			}
    		}
    		console.log(data)
    		return pass;
    	})
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
