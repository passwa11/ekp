<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.view">
    <template:replace name="head">
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
    </template:replace>
    <template:replace name="title">
        <c:out value="${fsscCashierPaymentForm.docSubject} - " />
        <c:out value="${ lfn:message('fssc-cashier:table.fsscCashierPayment') }" />
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <kmss:auth requestURL="/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do?method=edit&fdId=${param.fdId}">
                <c:if test="${fdIsShowEdit}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscCashierPayment.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </c:if>
            </kmss:auth>
            <fssc:checkUseBank fdBank="BANK">
	                <ui:button text="${lfn:message('fssc-cashier:button.payment')}" onclick="payment();" order="2" />
	           	    <ui:button text="${lfn:message('fssc-cashier:button.queryPayment')}" onclick="queryPayment();" order="2" />
          	</fssc:checkUseBank> 
            <kmss:auth requestURL="/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscCashierPayment.do?method=delete&fdId=${param.fdId}','_self');" order="4" />
            </kmss:auth>
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('fssc-cashier:table.fsscCashierPayment') }" href="/fssc/cashier/fssc_cashier_payment/" target="_self" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <div class='lui_form_title_frame'>
            <div class='lui_form_subject'>
                <table width="100%">
                    <tr>
                        <td align="center" height="80px" style="font-size:25px;">
                            ${ lfn:message('fssc-cashier:table.fsscCashierPayment') }
                        </td>
                    </tr>
                </table>
            </div>
            <div class='lui_form_baseinfo'>

            </div>
        </div>
        <center>
        <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-cashier:fsscCashierPayment.fdCompany')}
                </td>
                <td width="16.6%">
                        <%-- 费用归属公司--%>
                    <div id="_xform_fdCompanyId" _xform_type="dialog">
                        <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="view" required="true" subject="${lfn:message('fssc-cashier:fsscCashierPayment.fdCompany')}" style="width:95%;">
                        </xform:dialog>
                    </div>
                </td>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-cashier:fsscCashierPayment.fdPaymentMoney')}
                </td>
                <td width="16.6%">
                    <%-- 付款总金额--%>
                    <div _xform_type="textarea">
                        <xform:text property="fdPaymentMoney" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-cashier:fsscCashierPayment.fdModel')}
                </td>
                <td width="16.6%">
                    <%-- 关联单据--%>
                    <div _xform_type="textarea">
                        <a href="${LUI_ContextPath}${fsscCashierPaymentForm.fdModelUrl}" target="_blank">${fsscCashierPaymentForm.fdModelNumber}</a>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-cashier:fsscCashierPayment.fdDesc')}
                </td>
                <td colspan="5" width="83.0%">
                    <%-- 付款说明--%>
                    <div id="_xform_fdDesc" _xform_type="textarea">
                        <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="6" width="100%">
                    <span class="lui_icon_s lui_icon_s_icon_18"></span>
                        ${lfn:message('fssc-cashier:table.fsscCashierPaymentDetail')}
                </td>
            </tr>
            <tr>
                <td colspan="6" width="100%">
                    <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="true">
                        <tr align="center" class="tr_normal_title">
                            <td style="width:50px;" >
                                <input type='checkbox' id='List_Tongle' class="list_select_all" />
                                   ${lfn:message('page.serial')} 
                            </td>
                            <td>
                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.docNumber')}
                            </td>
                            <td>
                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdBasePayWay')}
                            </td>
                            <td>
                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdBasePayBank')}
                            </td>
                            <td>
                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdBaseCurrency')}
                            </td>
                            <%-- <td>
                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdRate')}
                            </td> --%>
                            <td>
                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPayeeName')}
                            </td>
                            <td>
                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPayeeAccount')}
                            </td>
                            <td>
                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPayeeBankName')}
                            </td>
                             <fssc:checkUseBank fdBank="BOC">
                                <td style="width:9%;">
                                        ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPayeeBankNo')}
                                </td>
                            </fssc:checkUseBank>
                            <fssc:checkUseBank fdBank="CMB,CBS,CMInt">
                                <td style="width:6%;">
                                        ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPayeeCity')}
                                </td>
                            </fssc:checkUseBank>
                             <fssc:checkUseBank fdBank="BANK">
                                    <td >
                                    	${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdSkipFlag')}
                                	</td>
                             </fssc:checkUseBank>
                            <fssc:checkUseBank fdBank="CBS">
                                <td >
                                    ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdSameDiffCity')}
                                </td>
                             </fssc:checkUseBank>
                             <td>
                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPlanPaymentDate')}
                            </td>
                            <td>
                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPaymentMoney')}
                            </td>
                             <td>
                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdRemarks')}
                            </td>
                            <fssc:checkUseBank fdBank="CMB,CMInt">
                             <td>
                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdCStlchn')}
                            </td>
                            </fssc:checkUseBank>
                            <fssc:checkUseBank fdBank="CBS">
                                <td>
                                        ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdSerialNumber')}
                                </td>
                            </fssc:checkUseBank>
                            <td>
                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdStatus')}
                            </td>
                             <fssc:checkUseBank fdBank="BANK">
	                             <td>
	                                ${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdRespMsg')}
	                            </td>
                            </fssc:checkUseBank>
                        </tr>
                        <c:forEach items="${fsscCashierPaymentForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
                            <tr KMSS_IsContentRow="1">
                                <td align="center">
                                  <input type='checkbox' name='DocList_Selected' value="${fdDetail_FormItem.fdId}" />
                                    ${vstatus.index+1}
                                </td>
                                <td align="center">
                                    <%-- 编号--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].docNumber" _xform_type="radio">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].docNumber" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 付款方式--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayWayId" _xform_type="radio">
                                        <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBasePayWayId" propertyName="fdDetail_Form[${vstatus.index}].fdBasePayWayName" showStatus="view" required="true" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBasePayWay')}" style="width:95%;">
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 付款银行--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayBankId" _xform_type="radio">
                                        <xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBasePayBankId" propertyName="fdDetail_Form[${vstatus.index}].fdBasePayBankName" showStatus="view" required="true" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBasePayBank')}" style="width:95%;">
                                        </xform:dialog>
                                    </div>
                                </td>
                                <td align="center" style="width:10%;">
                                    <%-- 货币--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCurrencyId" xform_type="text">
                                        <input type="text"  name="fdDetail_Form[${vstatus.index}].fdBaseCurrencyName" value="${fdDetail_FormItem.fdBaseCurrencyName }" class="inputsgl" readonly="readonly" style="width:35%;color:#000000;"/>
                                        <input type="text"  name="fdDetail_Form[${vstatus.index}].fdRate" value="${fdDetail_FormItem.fdRate }" class="inputsgl" readonly="readonly" style="width:15%;color:#000000;"/>
                                        <input name="fdDetail_Form[${vstatus.index}].fdBaseCurrencyId" value="${fdDetail_FormItem.fdBaseCurrencyId }"  type="hidden"/>
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 收款人名称--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeName" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdPayeeName" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 收款人账号--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeAccount" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdPayeeAccount" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 收款行名称--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeBankName" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdPayeeBankName" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                                <fssc:checkUseBank fdBank="BOC">
                                    <td align="center">
                                            <%-- 银联号--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeBankNo" _xform_type="text">
                                            <xform:text property="fdDetail_Form[${vstatus.index}].fdPayeeBankNo" showStatus="${fdDetail_FormItem.fdShowStatus}" subject="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPayeeBankNo')}" style="width:88%;" />
                                        </div>
                                    </td>
                                </fssc:checkUseBank>
                                <fssc:checkUseBank fdBank="CMB">
                                    <td align="center">
                                            <%-- 所屬地区--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeCity">
                                            <div  style="width:95%;">
                                                <input  name="fdDetail_Form[${vstatus.index}].fdPayeeCityCode" value="${fdDetail_FormItem.fdPayeeCityCode }"  type="hidden">
                                                <div class="input">
                                                        ${fdDetail_FormItem.fdPayeeCity }
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </fssc:checkUseBank>
                                <fssc:checkUseBank fdBank="CBS">
                                    <td align="center">
                                            <%-- 所屬地区--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeCity">
                                            <div style="width:95%;">
                                                <input  name="fdDetail_Form[${vstatus.index}].fdPayeeCityCode" value="${fdDetail_FormItem.fdPayeeCityCode }"  type="hidden">
                                                <div class="input">
                                                        ${fdDetail_FormItem.fdPayeeCity }
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </fssc:checkUseBank>
                                <fssc:checkUseBank fdBank="CMInt">
                                    <td align="center">
                                            <%-- 所屬地区--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdPayeeCity">
                                            <div style="width:95%;">
                                                <input  name="fdDetail_Form[${vstatus.index}].fdPayeeCityCode" value="${fdDetail_FormItem.fdPayeeCityCode }"  type="hidden">
                                                <div class="input">
                                                        ${fdDetail_FormItem.fdPayeeCity }
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </fssc:checkUseBank>
                                   <fssc:checkUseBank fdBank="BANK">
	                                   <td align="center">
	                                    <%-- 是否跨行--%>
	                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdSkipFlag" >
	                                   	 		<xform:select property="fdDetail_Form[${vstatus.index}].fdSkipFlag">
	                                        		<xform:enumsDataSource enumsType="fs_base_skip_flag" />
	                                        	</xform:select>
	                                    </div>
	                                	</td>
                                   </fssc:checkUseBank>
                                <fssc:checkUseBank fdBank="CBS">
                                    <td >
                                        <%-- 是否同城--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdSameDiffCity" >
                                            <xform:select property="fdDetail_Form[${vstatus.index}].fdSameDiffCity">
                                                <xform:enumsDataSource enumsType="fssc_cashier_is_same_city" />
                                            </xform:select>
                                        </div>
                                    </td>
                                </fssc:checkUseBank>
                                <td align="center">
                                    <%-- 预计付款时间--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdPlanPaymentDate" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdPlanPaymentDate" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 付款金额--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdPaymentMoney" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdPaymentMoney" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                                <td align="center">
                                    <%-- 备注--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdRemarks" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdRemarks" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                                <fssc:checkUseBank fdBank="CMB">
                                <td align="center">
                                    <%-- 结算方式--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdCStlchn" _xform_type="text">
                                    <xform:select property="fdDetail_Form[${vstatus.index}].fdCStlchn"  required="true">
	                                        		<xform:enumsDataSource enumsType="fssc_cashier_c_stlchn" />
	                                        	</xform:select>
                                    </div>
                                </td>
                                </fssc:checkUseBank>
                                <fssc:checkUseBank fdBank="CMInt">
                                    <td align="center">
                                            <%-- 结算方式--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdCStlchn" _xform_type="text">
                                            <xform:select property="fdDetail_Form[${vstatus.index}].fdCStlchn"  required="true">
                                                <c:if test="${fdDetail_FormItem.fdPaymentType  == 0}">
                                                    <xform:enumsDataSource enumsType="fssc_cashier_cmbint_stlchn" />
                                                </c:if>
                                                <c:if test="${fdDetail_FormItem.fdPaymentType  == 1}">
                                                    <xform:enumsDataSource enumsType="fssc_cashier_cmbint_pri_stlchn" />
                                                </c:if>
                                            </xform:select>
                                        </div>
                                    </td>
                                </fssc:checkUseBank>
                                <fssc:checkUseBank fdBank="CBS">
                                    <td align="center">
                                            <%-- 银行流水号--%>
                                        <div id="_xform_fdDetail_Form[${vstatus.index}].fdSerialNumber" _xform_type="text">
                                            <xform:text property="fdDetail_Form[${vstatus.index}].fdSerialNumber" showStatus="view" required="true" subject="${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdSerialNumber')}"  style="width:88%;" />
                                        </div>
                                    </td>
                                </fssc:checkUseBank>
                                <td align="center">
                                    <%-- 付款状态--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdStatus" _xform_type="text">
                                        <xform:select property="fdDetail_Form[${vstatus.index}].fdStatus" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdStatus'" showStatus="view">
                                            <xform:enumsDataSource enumsType="fssc_cashier_fd_status" />
                                        </xform:select>
                                    </div>
                                </td>
                                   <fssc:checkUseBank fdBank="BANK">
	                                 <td align="center">
	                                    <%-- 响应信息--%>
	                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdRespMsg" _xform_type="text">
	                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdRespMsg" showStatus="view" style="width:95%;" />
	                                    </div>
	                                </td>
                                </fssc:checkUseBank>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </table>
    </div>
     <input name="fdId" value="${param.fdId}" hidden="true">
</center>
<script>
	$(function() {
		 $("#List_Tongle").click(function() {
	        $(":checkbox[name='DocList_Selected']").prop("checked", this.checked); 
	    });
	});
		
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
        basePath: '/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("fsscCashierPayment_edit.js", "${LUI_ContextPath}/fssc/cashier/fssc_cashier_payment/", 'js', true);
</script>
</template:replace>

</template:include>
