<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
<%@page import="com.landray.kmss.fssc.expense.util.FsscExpenseUtil" %>
    <template:include ref="default.edit">
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
                		.com_qrcode {
                			display: none !important;
                		}
                		
            </style>
            <script type="text/javascript">
                var formInitData = {
                	fdCompanyId:'${param.fdCompanyId}',
                	fdMainId:'${param.fdMainId}',
                	fdExpenseTempDetailIds:'${param.fdExpenseTempDetailIds}',
                	index:"${param.index}",
                	method:"${param.method}",
                	fdCategoryId:"${param.fdCategoryId}",
                };
                var messageInfo = {

                    'fdInvoiceListTemp': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseTempDetail"))}'
                };

                var initData = {
                    contextPath: '${LUI_ContextPath}',
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form.js|doclist.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_temp/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
                Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
                Com_IncludeFile("fsscExpenseTemp_edit.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_temp/", 'js', true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${fsscExpenseTempForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-expense:table.fsscExpenseTemp') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${ lfn:message('fssc-expense:table.fsscExpenseTemp') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <!-- 存在合力中税模块，且开启提交人验真，且是费控解决方案版 -->
                <fssc:checkVersion version="true">
                <kmss:ifModuleExist path="/fssc/baiwang">
		            <c:if test="${fdCreatorCheck}">
		            	<ui:button text="${lfn:message('fssc-expense:button.check')}" onclick="checkTempInvoice();"></ui:button>
		            </c:if>
	            </kmss:ifModuleExist>
                </fssc:checkVersion>
                <c:choose>
                    <c:when test="${ fsscExpenseTempForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="FSSC_Submit('update','20');" />
                    </c:when>
                    <c:when test="${ fsscExpenseTempForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="FSSC_Submit('save','20');" />
                    </c:when>
                </c:choose>
                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="content">
            <html:form action="/fssc/expense/fssc_expense_temp/fsscExpenseTemp.do">
				<kmss:ifModuleExist path="/fssc/ledger/"><c:set value="true" var="ledgerExist"></c:set></kmss:ifModuleExist>
                        <table class="tb_normal" width="100%" style="margin-top:15px;">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-expense:fsscExpenseTemp.attInvoice')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- 上传发票--%>
                                    <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
                                        <c:param name="fdKey" value="attInvoice" />
                                        <c:param name="formBeanName" value="fsscExpenseTempForm" />
                                        <c:param name="fdMulti" value="true" />
                                    </c:import>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4" width="100%">
                                    <table class="tb_normal" width="100%" id="TABLE_DocList_fdInvoiceListTemp_Form" align="center" tbdraggable="true">
                                        <tr align="center" class="tr_normal_title">
                                            <td style="width:3%;">
                                                <div class="tr_normal_opt_l">
                                                    <label class="opt_ck_style">
                                                        <input type="checkbox" name="DocList_SelectAll" onclick="DocList_SelectAllRow(this);">
                                                    </label>
                                                </div>
                                            </td>
                                            <td width="10%">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceType')}
                                            </td>
                                            <td width="8%">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceNumber')}
                                            </td>
                                            <td width="8%">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdExpenseType')}
                                            </td>
                                            <td width="8%">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceCode')}
                                            </td>
                                            <td width="8%">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdCheckCode')}
                                            </td>
                                            <td width="8%">
                                                    ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdPurchName')}
                                            </td>
                                            <td width="8%">
                                                    ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdTaxNumber')}
                                            </td>
                                            <td width="8%">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceDate')}
                                            </td>
                                            <td width="7%">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceMoney')}
                                            </td>
                                            <td width="5%">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdNonDeductMoney')}
                                            </td>
                                            <td width="5%">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdTax')}
                                            </td>
                                            <td width="5%">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdTaxMoney')}
                                            </td>
                                            <td width="6%">
                                                ${lfn:message('fssc-expense:fsscExpenseTempDetail.fdNoTaxMoney')}
                                            </td>
                                            <td style="width:80px;">
                                            	<a href="javascript:void(0);" onclick="DocList_AddRow();">
                                                    <img src="${KMSS_Parameter_StylePath}icons/add.gif" border="0" />
                                                </a>
                                            </td>
                                        </tr>
                                        <tr KMSS_IsReferRow="1" style="display:none;" class="docListTr">
                                            <td class="docList" align="center">
                                                <input type='checkbox' name='DocList_Selected' onclick="DocList_SelectRow(this);" />
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 发票类型--%>
                                                <input type="hidden" name="fdInvoiceListTemp_Form[!{index}].fdId" value="" disabled="true" />
                                                <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdInvoiceType" _xform_type="text">
                                                	<xform:select property="fdInvoiceListTemp_Form[!{index}].fdInvoiceType" validators="required" style="width:90%;"  htmlElementProperties="id='fdInvoiceType'" onValueChange="FSSC_ChangeIsVat" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceType')}" showStatus="edit">
						                                <xform:enumsDataSource enumsType="fssc_invoice_type" />
						                            </xform:select>
                                                    <span class="txtstrong">*</span>
                                                	<xform:text property="fdInvoiceListTemp_Form[!{index}].fdInvoiceDocId" showStatus="noShow"></xform:text>
                                                	<xform:text property="fdInvoiceListTemp_Form[!{index}].fdCompanyId" value="${param.fdCompanyId}" showStatus="noShow"/>
                                               		<xform:text property="fdInvoiceListTemp_Form[!{index}].fdThisFlag" value="true" showStatus="noShow"/>
                                               		<%-- 是否可抵扣--%>
                                               		<xform:text property="fdInvoiceListTemp_Form[!{index}].fdIsVat" showStatus="noShow"/>
                                               		<xform:text property="fdInvoiceListTemp_Form[!{index}].fdCheckStatus" showStatus="noShow"/>
                                               		<xform:text property="fdInvoiceListTemp_Form[!{index}].fdState" showStatus="noShow"/>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 发票编号--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdInvoiceNumber" _xform_type="text">
                                               		<c:if test="${not empty ledgerExist}">
                                               			<div class="inputselectsgl" style="width:85%;">
											                <!-- 字段冗余，model不存在，但是没有隐藏域dialog_select会报错 -->
										                	<input name="fdInvoiceListTemp_Form[!{index}].fdInvoiceNumberId" value="" type="hidden">
										                	<div class="input">
										                		<input onchange="FSSC_Invoice();"  subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceNumber') }" name="fdInvoiceListTemp_Form[!{index}].fdInvoiceNumber" validate="required" style="width:85%;">
										                	</div>
										                	<div class="selectitem" onclick="FSSC_SelectInvoice();"></div>
									                	</div>
									                </c:if>
									                <c:if test="${empty ledgerExist }">
									                	<div class="inputselectsgl">
									                		<input subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber') }" name="fdInvoiceListTemp_Form[!{index}].fdInvoiceNumber" validate="required" style="width:85%;">
									                	</div>
									                </c:if>
									                <span class="txtstrong">*</span>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 费用类型--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdExpenseTypeId" _xform_type="text">
                                                	<xform:dialog propertyName="fdInvoiceListTemp_Form[!{index}].fdExpenseTypeName" required="true" propertyId="fdInvoiceListTemp_Form[!{index}].fdExpenseTypeId" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdExpenseType')}" validators=" maxLength(200)" style="width:85%;" >
									                	FSSC_SelectInvoiceType(this);
									                </xform:dialog>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 发票号码--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdInvoiceCode" _xform_type="text" class="vat">
                                                    <xform:text property="fdInvoiceListTemp_Form[!{index}].fdInvoiceCode" showStatus="edit" subject="${lfn:message('fssc-payment:fsscPaymentTempDetail.fdInvoiceCode')}" onValueChange="FSSC_Invoice" validators=" maxLength(50)" style="width:80%;" />
                                                	<span class="txtstrong" style="display:none;">*</span>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 校验码--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdCheckCode" _xform_type="text">
									                <xform:text property="fdInvoiceListTemp_Form[!{index}].fdCheckCode"  showStatus="edit" style="width:85%"></xform:text>
									            	<span class="txtstrong" style="display:none;">*</span>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 购方名称--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdPurchName" _xform_type="text">
									                <xform:text property="fdInvoiceListTemp_Form[!{index}].fdPurchName" validators="maxLength(500)"  showStatus="edit" style="width:70%"></xform:text>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 购方税号--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdTaxNumber" _xform_type="text">
									                <xform:text property="fdInvoiceListTemp_Form[!{index}].fdTaxNumber" validators="maxLength(50)"  showStatus="edit" style="width:70%"></xform:text>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 开票日期--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdInvoiceDate" _xform_type="datetime" class="vat">
                                                    <xform:datetime property="fdInvoiceListTemp_Form[!{index}].fdInvoiceDate" showStatus="edit" dateTimeType="date" style="width:85%;" />
                                                	<span class="txtstrong" style="display:none;">*</span>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 发票金额--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdInvoiceMoney" _xform_type="text">
                                                    <xform:text property="fdInvoiceListTemp_Form[!{index}].fdInvoiceMoney" onValueChange="FSSC_GetTaxMoney" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceMoney')}" validators="currency-dollar" style="width:85%;" />
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 不可抵扣金额--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdNonDeductMoney" _xform_type="text">
                                                    <xform:text property="fdInvoiceListTemp_Form[!{index}].fdNonDeductMoney" onValueChange="FSSC_GetTaxMoney" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdNonDeductMoney')}" validators="currency-dollar" style="width:85%;" />
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 税率--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdTax" _xform_type="text" class="vat">
									                <xform:text property="fdInvoiceListTemp_Form[!{index}].fdTax" onValueChange="FSSC_GetTaxMoney"  showStatus="edit" style="width:40%"></xform:text>%
									            	<span class="txtstrong" style="display:none;">*</span>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 税额--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdTaxMoney" _xform_type="text" class="vat">
                                                    <xform:text property="fdInvoiceListTemp_Form[!{index}].fdTaxMoney" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdTaxMoney')}" validators="currency-dollar" style="width:80%;" />
                                                	<span class="txtstrong" style="display:none;">*</span>
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 不含税金额--%>
                                                <div id="_xform_fdInvoiceListTemp_Form[!{index}].fdNoTaxMoney" _xform_type="text" class="vat">
                                                   <xform:text property="fdInvoiceListTemp_Form[!{index}].fdNoTaxMoney" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdNoTaxMoney')}" validators="currency-dollar" style="width:80%;" />
                                                	<span class="txtstrong" style="display:none;">*</span> 
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                                                    <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                                                </a>
                                            </td>
                                        </tr>
                                        <c:forEach items="${fsscExpenseTempForm.fdInvoiceListTemp_Form}" var="fdInvoiceListTemp_FormItem" varStatus="vstatus">
                                        	<c:set var="fdThisFlag" value="false"></c:set>
                                        	<c:set var="showStatus" value="readOnly"></c:set>
                                        	<c:if test="${fn:contains(param.fdExpenseTempDetailIds,fdInvoiceListTemp_FormItem.fdId)}">
                                        		<c:set var="fdThisFlag" value="true"></c:set>
                                        		<c:set var="showStatus" value="edit"></c:set>
                                        	</c:if>
                                            <tr KMSS_IsContentRow="1" class="docListTr">
                                                <td class="docList" align="center">
                                                    <input type="checkbox" name="DocList_Selected" onclick="DocList_SelectRow(this);" />
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 发票类型--%>
                                                    <input type="hidden" name="fdInvoiceListTemp_Form[${vstatus.index}].fdId" value="${fdInvoiceListTemp_FormItem.fdId}" />
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceType" _xform_type="text">
                                                   		<xform:select property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceType" validators="required" style="width:90%;"   onValueChange="FSSC_ChangeIsVat" htmlElementProperties="id='fdInvoiceType'" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceType')}" showStatus="${showStatus}">
							                                <xform:enumsDataSource enumsType="fssc_invoice_type" />
							                            </xform:select>
                                                        <span class="txtstrong">*</span>
                                                   		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceDocId" showStatus="noShow" />
                                                    	<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdCompanyId" showStatus="noShow"  />
                                                		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdThisFlag" value="${fdThisFlag}" showStatus="noShow"/>
                                                		 <%-- 是否可抵扣--%>
                                                		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdIsVat" showStatus="noShow"/>
                                                		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdCheckStatus" showStatus="noShow"/>
                                                		<xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdState" showStatus="noShow"/>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 发票编号--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceNumber" _xform_type="text">
                                                    <c:if test="${showStatus=='edit'}">
                                                        <c:if test="${not empty ledgerExist}">
	                                               			<div class="inputselectsgl" style="width:85%;">
												                <!-- 字段冗余，model不存在，但是没有隐藏域dialog_select会报错 -->
											                	<input name="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceNumberId" value="" type="hidden">
											                	<div class="input">
											                		<input onchange="FSSC_Invoice();" value="${fdInvoiceListTemp_FormItem.fdInvoiceNumber}"  subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceNumber') }" name="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceNumber" validate="required" style="width:85%;" >
											                	</div>
											                	<div class="selectitem" onclick="FSSC_SelectInvoice(${vstatus.index});"></div>
										                	</div>
										                </c:if>
										                <c:if test="${empty ledgerExist }">
										                	<div class="inputselectsgl">
										                		<input subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber') }" value="${fdInvoiceListTemp_FormItem.fdInvoiceNumber}" name="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceNumber" validate="required" style="width:85%;">
										                	</div>
										                </c:if>
										                <span class="txtstrong">*</span>
										            </c:if>
                                                    <c:if test="${showStatus=='readOnly'}">
								                		<input subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber') }" value="${fdInvoiceListTemp_FormItem.fdInvoiceNumber}" name="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceNumber" readOnly="true" style="width:85%;ime-mode:disabled" class="inputsgl" />
										            </c:if>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 费用类型--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdExpenseTypeId" _xform_type="text">
                                                        <xform:dialog propertyName="fdInvoiceListTemp_Form[${vstatus.index}].fdExpenseTypeName" required="true" propertyId="fdInvoiceListTemp_Form[${vstatus.index}].fdExpenseTypeId" showStatus="${showStatus}" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdExpenseType')}" validators=" maxLength(200)" style="width:85%;" >
										                	FSSC_SelectInvoiceType(this);
										                </xform:dialog>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 发票号码--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceCode" _xform_type="text" class="vat">
                                                        <xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceCode" showStatus="${showStatus}" subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceCode')}" onValueChange="FSSC_Invoice"  validators=" maxLength(50)" style="width:80%;" />
                                                    	<span class="txtstrong" style="display:none;">*</span>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
	                                                <%-- 校验码--%>
	                                                <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdCheckCode" _xform_type="text">
										                <xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdCheckCode"  showStatus="${showStatus}" style="width:85%"></xform:text>
	                                                </div>
	                                            </td>
                                                <td class="docList" align="center">
	                                                <%-- 购方名称--%>
	                                                <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdPurchName" _xform_type="text">
										                <xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdPurchName" validators="maxLength(500)" showStatus="${showStatus}" style="width:70%"></xform:text>
	                                                </div>
	                                            </td>
                                                <td class="docList" align="center">
	                                                <%-- 购方税号--%>
	                                                <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdTaxNumber" _xform_type="text">
										                <xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdTaxNumber" validators="maxLength(50)"  showStatus="${showStatus}" style="width:70%"></xform:text>
	                                                </div>
	                                            </td>
                                                <td class="docList" align="center">
                                                    <%-- 开票日期--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceDate" _xform_type="datetime" class="vat">
                                                        <xform:datetime property="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceDate" showStatus="${showStatus}" dateTimeType="date" style="width:85%;" />
                                                    	<span class="txtstrong" style="display:none;">*</span>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 发票金额--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceMoney" _xform_type="text">
                                                        <input type="text" name="fdInvoiceListTemp_Form[${vstatus.index}].fdInvoiceMoney" class="inputsgl" value="<kmss:showNumber value="${fdInvoiceListTemp_FormItem.fdInvoiceMoney }" pattern="0.00"/>" onchange="FSSC_GetTaxMoney(this.value,this)"  <c:if test="${showStatus=='readOnly'}">readonly="true"</c:if>  subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdInvoiceMoney')}" validate="currency-dollar required" style="width:85%;" />
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 不可抵扣金额--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdNonDeductMoney" _xform_type="text">
                                                        <input type="text" name="fdInvoiceListTemp_Form[${vstatus.index}].fdNonDeductMoney" class="inputsgl" value="<kmss:showNumber value="${fdInvoiceListTemp_FormItem.fdNonDeductMoney }" pattern="0.00" />" onchange="FSSC_GetTaxMoney(this.value,this)" <c:if test="${showStatus=='readOnly'}">readonly="true"</c:if> subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdNonDeductMoney')}" validate="currency-dollar" style="width:85%;" />
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 税率--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdTax" _xform_type="text" class="vat">
										                <xform:text property="fdInvoiceListTemp_Form[${vstatus.index}].fdTax" onValueChange="FSSC_GetTaxMoney" showStatus="${showStatus}" style="width:40%"></xform:text>%
										            	<span class="txtstrong" style="display:none;">*</span>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 税额--%>
                                                    <div id="_xform_fdInvoiceListTemp_Form[${vstatus.index}].fdTaxMoney" _xform_type="text" class="vat">
                                                    	<input type="text" name="fdInvoiceListTemp_Form[${vstatus.index}].fdTaxMoney" class="inputsgl" value="<kmss:showNumber value="${fdInvoiceListTemp_FormItem.fdTaxMoney}" pattern="0.00" />" <c:if test="${showStatus=='readOnly'}">readonly="true"</c:if> subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdTaxMoney')}" validate="currency-dollar" style="width:80%;" />
                                                    	<span class="txtstrong" style="display:none;">*</span>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                    <%-- 不含税金额--%>
                                                    	<input type="text" name="fdInvoiceListTemp_Form[${vstatus.index}].fdNoTaxMoney" class="inputsgl" value="<kmss:showNumber value="${fdInvoiceListTemp_FormItem.fdNoTaxMoney }" pattern="0.00"></kmss:showNumber>" <c:if test="${showStatus=='readOnly'}">readonly="true"</c:if> subject="${lfn:message('fssc-expense:fsscExpenseTempDetail.fdNoTaxMoney')}" validate="currency-dollar" style="width:80%;"/>
                                                    	<span class="txtstrong" style="display:none;">*</span>
                                                    </div>
                                                </td>
                                                <td class="docList" align="center">
                                                	<!-- 不是可编辑发票，不出现删除按钮 -->
	                                                <c:if test="${showStatus=='edit'}">
	                                                   <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                                                       <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
	                                                   </a>
	                                                </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                    <input type="hidden" name="fdInvoiceListTemp_Flag" value="1">
                                    <input type="hidden" name="fdMainId" value="${param.fdMainId}">
                                    <script>
                                        Com_IncludeFile("doclist.js");
                                    </script>
                                    <script>
                                        DocList_Info.push('TABLE_DocList_fdInvoiceListTemp_Form');
                                    </script>
                                </td>
                            </tr>
                        </table>
                <html:hidden property="fdId" />


                <html:hidden property="method_GET" />
                <script src="${LUI_ContextPath }/eop/basedata/resource/js/quickSelect.js"></script>
            </html:form>
        </template:replace>


    </template:include>
