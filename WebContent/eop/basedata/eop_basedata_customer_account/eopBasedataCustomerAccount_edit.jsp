<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<table class="tb_normal" width="100%" id="TABLE_DocList_fdAccountList_Form" align="center" tbdraggable="true">
    <tr align="center" class="tr_normal_title">
        <td style="width:20px;"></td>
        <td style="width:40px;">
            ${lfn:message('page.serial')}
        </td>
        <td>
            ${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdSupplierArea')}
        </td>
        <td>
            ${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdAccountName')}
        </td>
        <td>
            ${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankName')}
        </td>
        <td>
            ${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankNo')}
        </td>
        <td>
            ${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankAccount')}
        </td>
        <fssc:checkUseBank fdBank="CMB,CBS,CMInt">
	        <td style="width:100px;">
	            ${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdAccountAreaName')}
	        </td>
        </fssc:checkUseBank>
        <td>
            ${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankSwift')}
        </td>
        <td>
            ${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdReceiveCompany')}
        </td>
        <td>
            ${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdReceiveBankName')}
        </td>
        <td>
            ${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdReceiveBankAddress')}
        </td>
        <td>
            ${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdInfo')}
        </td>
        <td style="width:80px;">
        </td>
    </tr>
    <tr KMSS_IsReferRow="1" style="display:none;">
        <td align="center">
            <input type='checkbox' name='DocList_Selected' />
        </td>
        <td align="center" KMSS_IsRowIndex="1">
            !{index}
        </td>
        <td align="center">
            <%-- 所在区域--%>
             <div>
             	<label>
             	<input type="radio" onchange="changeArea(this.value,this);" name="fdAccountList_Form[!{index}].fdSupplierArea" value="0" validate=" maxLength(2)" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdSupplierArea')}">
             	${lfn:message('eop-basedata:enums.supplier_area.0') }
             	</label>
             </div>
             <div>
             	<label>
             	<input type="radio" onchange="changeArea(this.value,this);" name="fdAccountList_Form[!{index}].fdSupplierArea" value="1" validate=" maxLength(2)" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdSupplierArea')}">
             	${lfn:message('eop-basedata:enums.supplier_area.1') }
             	</label>
             </div>
        </td>
        <td align="center">
            <%-- 收款账户名--%>
            <input type="hidden" name="fdAccountList_Form[!{index}].fdId" value="" disabled="true" />
            <div id="_xform_fdAccountList_Form[!{index}].fdAccountName" _xform_type="text">
                <xform:text property="fdAccountList_Form[!{index}].fdAccountName" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdAccountName')}" validators=" maxLength(200)" style="width:85%;" />
            </div>
        </td>
        <td align="center">
            <%-- 开户行--%>
            <div id="_xform_fdAccountList_Form[!{index}].fdBankName" _xform_type="text">
                <xform:text property="fdAccountList_Form[!{index}].fdBankName" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankName')}" validators=" maxLength(300)" style="width:85%;" />
            </div>
        </td>
        <td align="center">
            <%-- 联行号--%>
            <div id="_xform_fdAccountList_Form[!{index}].fdBankNo" _xform_type="text">
                <xform:text property="fdAccountList_Form[!{index}].fdBankNo" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankNo')}" validators=" maxLength(20)" style="width:85%;" />
            </div>
        </td>
        <td align="center">
            <%-- 账号--%>
            <div id="_xform_fdAccountList_Form[!{index}].fdBankAccount" _xform_type="text">
                <xform:text property="fdAccountList_Form[!{index}].fdBankAccount" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankAccount')}" validators=" maxLength(50)" style="width:85%;" />
            </div>
        </td>
        <fssc:checkUseBank fdBank="CMB" >
	 		<td align="center">
	 			<div id="_xform_fdAccountList_Form[!{index}].fdAccountAreaCode" _xform_type="dialog">
	                <xform:dialog propertyId="fdAccountList_Form[!{index}].fdAccountAreaCode" propertyName="fdAccountList_Form[!{index}].fdAccountAreaName" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdAccountAreaName')}" showStatus="edit" required="false" style="width:95%;">
	                    FSSC_SelectAccountArea();
	                </xform:dialog>
	            </div>
            </td>
        </fssc:checkUseBank>
        <fssc:checkUseBank fdBank="CBS" >
            <td align="center">
                <div id="_xform_fdAccountList_Form[!{index}].fdAccountAreaCode" _xform_type="dialog">
                    <xform:dialog propertyId="fdAccountList_Form[!{index}].fdAccountAreaCode" propertyName="fdAccountList_Form[!{index}].fdAccountAreaName" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdAccountAreaName')}" showStatus="edit" required="false" style="width:95%;">
                        FSSC_SelectCbsAccountArea();
                    </xform:dialog>
                </div>
            </td>
        </fssc:checkUseBank>
        <fssc:checkUseBank fdBank="CMInt" >
            <td align="center">
                <div id="_xform_fdAccountList_Form[!{index}].fdAccountAreaCode" _xform_type="dialog">
                    <xform:dialog propertyId="fdAccountList_Form[!{index}].fdAccountAreaCode" propertyName="fdAccountList_Form[!{index}].fdAccountAreaName" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdAccountAreaName')}" showStatus="edit" required="false" style="width:95%;">
                        FSSC_SelectCmbIntAccountArea();
                    </xform:dialog>
                </div>
            </td>
        </fssc:checkUseBank>
        <td align="center">
            <%-- 收款银行swift号--%>
            <div id="_xform_fdAccountList_Form[!{index}].fdBankSwift" _xform_type="text" class="vat">
                <xform:text property="fdAccountList_Form[!{index}].fdBankSwift" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankSwift')}" validators=" maxLength(200)" style="width:85%;" />
                <span class="txtstrong" style="display:none;">*</span>
            </div>
        </td>
        <td align="center">
            <%-- 收款公司名称--%>
            <div id="_xform_fdAccountList_Form[!{index}].fdReceiveCompany" _xform_type="text" class="vat">
                <xform:text property="fdAccountList_Form[!{index}].fdReceiveCompany" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdReceiveCompany')}" validators=" maxLength(200)" style="width:85%;" />
                <span class="txtstrong" style="display:none;">*</span>
            </div>
        </td>
        <td align="center">
            <%-- 收款银行名称（境外）--%>
            <div id="_xform_fdAccountList_Form[!{index}].fdReceiveBankName" _xform_type="text" class="vat">
                <xform:text property="fdAccountList_Form[!{index}].fdReceiveBankName" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdReceiveBankName')}" validators=" maxLength(200)" style="width:85%;" />
                <span class="txtstrong" style="display:none;">*</span>
            </div>
        </td>
        <td align="center">
            <%-- 收款银行地址（境外）--%>
            <div id="_xform_fdAccountList_Form[!{index}].fdReceiveBankAddress" _xform_type="text" class="vat">
                <xform:text property="fdAccountList_Form[!{index}].fdReceiveBankAddress" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdReceiveBankAddress')}" validators=" maxLength(200)" style="width:85%;" />
                <span class="txtstrong" style="display:none;">*</span>
            </div>
        </td>
        <td align="center">
            <%-- 其他信息--%>
            <div id="_xform_fdAccountList_Form[!{index}].fdInfo" _xform_type="text">
                <xform:text property="fdAccountList_Form[!{index}].fdInfo" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdInfo')}" validators=" maxLength(200)" style="width:85%;" />
            </div>
        </td>
        <td align="center">
            <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
                <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
            </a>
            &nbsp;
            <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
            </a>
        </td>
    </tr>
    <c:forEach items="${eopBasedataCustomerForm.fdAccountList_Form}" var="fdAccountList_FormItem" varStatus="vstatus">
        <tr KMSS_IsContentRow="1">
            <td align="center">
                <input type="checkbox" name="DocList_Selected" />
            </td>
            <td align="center">
                ${vstatus.index+1}
            </td>
            <td align="center">
                <%-- 所在区域--%>
                <div id="_xform_fdAccountList_Form[${vstatus.index}].fdSupplierArea" _xform_type="radio">
                    <div>
                    	<label>
                    	<input type="radio" onchange="changeArea(this.value,this);" <c:if test="${fdAccountList_FormItem.fdSupplierArea=='0'}">checked</c:if> name="fdAccountList_Form[${vstatus.index}].fdSupplierArea" value="0" validate=" maxLength(2)" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdSupplierArea')}">
                    	${lfn:message('eop-basedata:enums.supplier_area.0') }
                    	</label>
                    </div>
                    <div>
                    	<label>
                    	<input type="radio" onchange="changeArea(this.value,this);" <c:if test="${fdAccountList_FormItem.fdSupplierArea=='1'}">checked</c:if> name="fdAccountList_Form[${vstatus.index}].fdSupplierArea" value="1" validate=" maxLength(2)" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdSupplierArea')}">
                    	${lfn:message('eop-basedata:enums.supplier_area.1') }
                    	</label>
                    </div>
                </div>
            </td>
            <td align="center">
                <%-- 收款账户名--%>
                <input type="hidden" name="fdAccountList_Form[${vstatus.index}].fdId" value="${fdAccountList_FormItem.fdId}" />
                <div id="_xform_fdAccountList_Form[${vstatus.index}].fdAccountName" _xform_type="text">
                    <xform:text property="fdAccountList_Form[${vstatus.index}].fdAccountName" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdAccountName')}" validators=" maxLength(200)" style="width:85%;" />
                </div>
            </td>
            <td align="center">
                <%-- 开户行--%>
                <div id="_xform_fdAccountList_Form[${vstatus.index}].fdBankName" _xform_type="text">
                    <xform:text property="fdAccountList_Form[${vstatus.index}].fdBankName" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankName')}" validators=" maxLength(300)" style="width:85%;" />
                </div>
            </td>
            <td align="center">
                <%-- 联行号--%>
                <div id="_xform_fdAccountList_Form[${vstatus.index}].fdBankNo" _xform_type="text">
                    <xform:text property="fdAccountList_Form[${vstatus.index}].fdBankNo" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankNo')}" validators=" maxLength(20)" style="width:85%;" />
                </div>
            </td>
            <td align="center">
                <%-- 账号--%>
                <div id="_xform_fdAccountList_Form[${vstatus.index}].fdBankAccount" _xform_type="text">
                    <xform:text property="fdAccountList_Form[${vstatus.index}].fdBankAccount" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankAccount')}" validators=" maxLength(50)" style="width:85%;" />
                </div>
            </td>
            <fssc:checkUseBank fdBank="CMB">
			 	<td align="center">
			 		<div id="_xform_fdAccountList_Form[${vstatus.index}].fdAccountAreaCode" _xform_type="dialog">
	                    <xform:dialog propertyId="fdAccountList_Form[${vstatus.index}].fdAccountAreaCode" propertyName="fdAccountList_Form[${vstatus.index}].fdAccountAreaName" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdAccountAreaName')}" showStatus="edit" required="false" style="width:95%;">
	                        FSSC_SelectAccountArea();
	                    </xform:dialog>
	                </div>
                </td>
            </fssc:checkUseBank>
            <fssc:checkUseBank fdBank="CBS">
                <td align="center">
                    <div id="_xform_fdAccountList_Form[${vstatus.index}].fdAccountAreaCode" _xform_type="dialog">
                        <xform:dialog propertyId="fdAccountList_Form[${vstatus.index}].fdAccountAreaCode" propertyName="fdAccountList_Form[${vstatus.index}].fdAccountAreaName" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdAccountAreaName')}" showStatus="edit" required="false" style="width:95%;">
                            FSSC_SelectCbsAccountArea();
                        </xform:dialog>
                    </div>
                </td>
            </fssc:checkUseBank>
            <fssc:checkUseBank fdBank="CMInt">
                <td align="center">
                    <div id="_xform_fdAccountList_Form[${vstatus.index}].fdAccountAreaCode" _xform_type="dialog">
                        <xform:dialog propertyId="fdAccountList_Form[${vstatus.index}].fdAccountAreaCode" propertyName="fdAccountList_Form[${vstatus.index}].fdAccountAreaName" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdAccountAreaName')}" showStatus="edit" required="false" style="width:95%;">
                            FSSC_SelectCmbIntAccountArea();
                        </xform:dialog>
                    </div>
                </td>
            </fssc:checkUseBank>
            <td align="center">
                <%-- 收款银行swift号--%>
                <div id="_xform_fdAccountList_Form[${vstatus.index}].fdBankSwift" _xform_type="text" class="vat">
                    <xform:text property="fdAccountList_Form[${vstatus.index}].fdBankSwift" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdBankSwift')}" validators=" maxLength(200)" style="width:85%;" />
                    <span class="txtstrong" style="display:none;">*</span>
                </div>
            </td>
            <td align="center">
                <%-- 收款公司名称--%>
                <div id="_xform_fdAccountList_Form[${vstatus.index}].fdReceiveCompany" _xform_type="text" class="vat">
                    <xform:text property="fdAccountList_Form[${vstatus.index}].fdReceiveCompany" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdReceiveCompany')}" validators=" maxLength(200)" style="width:85%;" />
                    <span class="txtstrong" style="display:none;">*</span>
                </div>
            </td>
            <td align="center">
                <%-- 收款银行名称（境外）--%>
                <div id="_xform_fdAccountList_Form[${vstatus.index}].fdReceiveBankName" _xform_type="text" class="vat">
                    <xform:text property="fdAccountList_Form[${vstatus.index}].fdReceiveBankName" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdReceiveBankName')}" validators=" maxLength(200)" style="width:85%;" />
                    <span class="txtstrong" style="display:none;">*</span>
                </div>
            </td>
            <td align="center">
                <%-- 收款银行地址（境外）--%>
                <div id="_xform_fdAccountList_Form[${vstatus.index}].fdReceiveBankAddress" _xform_type="text" class="vat">
                    <xform:text property="fdAccountList_Form[${vstatus.index}].fdReceiveBankAddress" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdReceiveBankAddress')}" validators=" maxLength(200)" style="width:85%;" />
                    <span class="txtstrong" style="display:none;">*</span>
                </div>
            </td>
            <td align="center">
                <%-- 其他信息--%>
                <div id="_xform_fdAccountList_Form[${vstatus.index}].fdInfo" _xform_type="text">
                    <xform:text property="fdAccountList_Form[${vstatus.index}].fdInfo" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomerAccount.fdInfo')}" validators=" maxLength(200)" style="width:85%;" />
                </div>
            </td>
            <td align="center">
                <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
                    <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
                </a>
                &nbsp;
                <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                    <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                </a>
            </td>
        </tr>
    </c:forEach>
    <tr type="optRow" class="tr_normal_opt" invalidrow="true">
        <td colspan="13">
            <a href="javascript:void(0);" onclick="DocList_AddRow();">
                <img src="${KMSS_Parameter_StylePath}icons/icon_add.png" border="0" />${lfn:message('doclist.add')}
            </a>
            &nbsp;
            <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);">
                <img src="${KMSS_Parameter_StylePath}icons/icon_up.png" border="0" />${lfn:message('doclist.moveup')}
            </a>
            &nbsp;
            <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);">
                <img src="${KMSS_Parameter_StylePath}icons/icon_down.png" border="0" />${lfn:message('doclist.movedown')}
            </a>
            &nbsp;
        </td>
    </tr>
</table>
<input type="hidden" name="fdAccountList_Flag" value="1">
<script>
    Com_IncludeFile("doclist.js");
</script>
<script>
    DocList_Info.push('TABLE_DocList_fdAccountList_Form');
</script>
