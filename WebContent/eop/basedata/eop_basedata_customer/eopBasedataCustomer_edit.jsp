<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
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
    if("${eopBasedataCustomerForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('eop-basedata:table.eopBasedataCustomer') }";
    }
    if("${eopBasedataCustomerForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('eop-basedata:table.eopBasedataCustomer') }";
    }
    var formInitData = {

    };
    var messageInfo = {

    };
    var initData = {
        contextPath: '${LUI_ContextPath}'
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_customer/", 'js', true);
    Com_IncludeFile("config_fssc_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
    Com_IncludeFile("eopBasedataCustomer.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_customer/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_customer/eopBasedataCustomer.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataCustomerForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataCustomerForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataCustomerForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataCustomerForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataCustomer') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
            	<tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdCompanyList')}
                    </td>
                    <td width="85.0%" colspan="3">
                            <%-- 公司--%>
                        <div id="_xform_fdCompanyList" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataCustomer.fdCompanyList')}" showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdCode')}
                    </td>
                    <td width="35%">
                        <%-- 编码--%>
                            <c:choose>
                            	<c:when test="${not empty eopBasedataCustomerForm.fdCode}">
                            		<xform:text property="fdCode" showStatus="readOnly" required="true" style="width:95%;color:#333;" />
                            	</c:when>
                            	<c:otherwise>
                            		<xform:text property="fdCode" showStatus="edit" required="true" style="width:95%;" />
                            	</c:otherwise>
                            </c:choose>
                    </td>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('eop-basedata:eopBasedataCustomer.fdUser')}
                    </td>
                    <td width="35%">
                            <%-- 对应登录人--%>
                        <div id="_xform_fdUserId" _xform_type="address">
                            <xform:address propertyId="fdUserId" propertyName="fdUserName" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdName')}
                    </td>
                    <td width="35%">
                        <%-- 名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdAbbreviation')}
                    </td>
                    <td width="35%">
                        <%-- 简称--%>
                        <div id="_xform_fdAbbreviation" _xform_type="text">
                            <xform:text property="fdAbbreviation" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>

                <tr>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('eop-basedata:eopBasedataCustomer.fdCharge')}
                    </td>
                    <td width="35%">
                            <%-- 负责人--%>
                        <div id="_xform_fdChargeId" _xform_type="address">
                             <xform:address propertyId="fdChargeId" propertyName="fdChargeName" required="true" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('eop-basedata:eopBasedataCustomer.fdTel')}
                    </td>
                    <td width="35%">
                            <%-- 电话--%>
                        <div id="_xform_fdTel" _xform_type="text">
                            <xform:text property="fdTel" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>

                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdTaxNo')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdTaxNo" _xform_type="text">
                            <xform:text property="fdTaxNo" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdErpNo')}
                    </td>
                    <td width="35%">
                        <div id="_xform_fdErpNo" _xform_type="text">
                            <xform:text property="fdErpNo" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdCreditCode')}
                    </td>
                    <td width="35%">
                        <%-- 统一社会信用代码--%>
                        <div id="_xform_fdCreditCode" _xform_type="text">
                            <xform:text property="fdCreditCode" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdCodeValidityPeriod')}
                    </td>
                    <td width="35%">
                        <%-- 信用证有效截止日期--%>
                        <div id="_xform_fdCodeValidityPeriod" _xform_type="datetime">
                            <xform:datetime property="fdCodeValidityPeriod" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomer.fdCodeValidityPeriod')}" dateTimeType="date" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdIndustry')}
                    </td>
                    <td width="35%">
                        <%-- 所属行业--%>
                        <div id="_xform_fdIndustry" _xform_type="text">
                            <xform:text property="fdIndustry" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdLegalPerson')}
                    </td>
                    <td width="35%">
                        <%-- 法人代表--%>
                        <div id="_xform_fdLegalPerson" _xform_type="text">
                            <xform:text property="fdLegalPerson" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdRegistCapital')}
                    </td>
                    <td width="35%">
                        <%-- 注册资金--%>
                        <div id="_xform_fdRegistCapital" _xform_type="text">
                            <xform:text property="fdRegistCapital" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdEstablishDate')}
                    </td>
                    <td width="35%">
                        <%-- 成立日期--%>
                        <div id="_xform_fdEstablishDate" _xform_type="datetime">
                            <xform:datetime property="fdEstablishDate" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataCustomer.fdEstablishDate')}" dateTimeType="date" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdEmail')}
                    </td>
                    <td width="85%" colspan="3">
                        <%-- 注册邮箱--%>
                        <div id="_xform_fdEmail" _xform_type="text">
                            <xform:text property="fdEmail" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdAddress')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 企业地址--%>
                        <div id="_xform_fdAddress" _xform_type="text">
                            <xform:text property="fdAddress" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdUrl')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 企业网址--%>
                        <div id="_xform_fdUrl" _xform_type="text">
                            <xform:text property="fdUrl" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdBusinessScope')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 经营范围--%>
                        <div id="_xform_fdBusinessScope" _xform_type="textarea">
                            <xform:textarea property="fdBusinessScope" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdDesc')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 企业简介--%>
                        <div id="_xform_fdDesc" _xform_type="textarea">
                            <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" width="85.0%">
                        <c:import url="/eop/basedata/eop_basedata_customer_account/eopBasedataCustomerAccount_edit.jsp" charEncoding="UTF-8"></c:import>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.attOther')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 资质附件--%>
                        <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
                            <c:param name="fdKey" value="attOther" />
                            <c:param name="formBeanName" value="eopBasedataCustomerForm" />
                            <c:param name="fdMulti" value="true" />
                        </c:import>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCustomer.fdIsAvailable')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 是否有效--%>
                        <div id="_xform_fdIsAvailable" _xform_type="radio">
                            <xform:radio property="fdIsAvailable" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
            </table>
            <div class="lui_paragraph_title">
                <span class="lui_icon_s lui_icon_s_icon_18"></span>${ lfn:message('eop-basedata:py.LianXiRenXinXi') }
            </div>
            <table class="tb_normal" width="100%" id="TABLE_DocList_fdContactPerson_Form" align="center" tbdraggable="true">
                <tr align="center" class="tr_normal_title">
                    <td style="width:20px;"></td>
                    <td style="width:40px;">
                            ${lfn:message('page.serial')}
                    </td>
                    <td>
                            ${lfn:message('eop-basedata:eopBasedataContact.fdName')}
                    </td>
                    <td>
                            ${lfn:message('eop-basedata:eopBasedataContact.fdPosition')}
                    </td>
                    <td>
                            ${lfn:message('eop-basedata:eopBasedataContact.fdPhone')}
                    </td>
                    <td>
                            ${lfn:message('eop-basedata:eopBasedataContact.fdEmail')}
                    </td>
                    <td>
                            ${lfn:message('eop-basedata:eopBasedataContact.fdAddress')}
                    </td>
                    <td>
                            ${lfn:message('eop-basedata:eopBasedataContact.fdRemarks')}
                    </td>
                    <td>
                            ${lfn:message('eop-basedata:eopBasedataContact.fdIsfirst')}
                    </td>
                    <td style="width:80px;">
                    </td>
                </tr>
                <tr KMSS_IsReferRow="1" style="display:none;" class="docListTr">
                    <td class="docList" align="center">
                        <input type='checkbox' name='DocList_Selected' />
                    </td>
                    <td class="docList" align="center" KMSS_IsRowIndex="1">
                        !{index}
                    </td>
                    <td class="docList" align="center">
                            <%-- 姓名--%>
                        <input type="hidden" name="fdContactPerson_Form[!{index}].fdId" value="" disabled="true" />
                        <div id="_xform_fdContactPerson_Form[!{index}].fdName" _xform_type="text">
                            <xform:text property="fdContactPerson_Form[!{index}].fdName" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataContact.fdName')}" validators=" maxLength(200)" style="width:95%;" />
                        </div>
                    </td>
                    <td class="docList" align="center">
                            <%-- 职务--%>
                        <div id="_xform_fdContactPerson_Form[!{index}].fdPosition" _xform_type="text">
                            <xform:text property="fdContactPerson_Form[!{index}].fdPosition" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataContact.fdPosition')}" validators=" maxLength(200)" style="width:95%;" />
                        </div>
                    </td>
                    <td class="docList" align="center">
                            <%-- 联系电话--%>
                        <div id="_xform_fdContactPerson_Form[!{index}].fdPhone" _xform_type="text">
                            <xform:text property="fdContactPerson_Form[!{index}].fdPhone" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataContact.fdPhone')}" validators=" maxLength(200)" style="width:95%;" />
                        </div>
                    </td>
                    <td class="docList" align="center">
                            <%-- 电子邮箱--%>
                        <div id="_xform_fdContactPerson_Form[!{index}].fdEmail" _xform_type="text">
                            <xform:text property="fdContactPerson_Form[!{index}].fdEmail" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataContact.fdEmail')}" validators=" maxLength(200)" style="width:95%;" />
                        </div>
                    </td>
                    <td class="docList" align="center">
                            <%-- 联系地址--%>
                        <div id="_xform_fdContactPerson_Form[!{index}].fdAddress" _xform_type="text">
                            <xform:text property="fdContactPerson_Form[!{index}].fdAddress" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataContact.fdAddress')}" validators=" maxLength(200)" style="width:95%;" />
                        </div>
                    </td>
                    <td class="docList" align="center">
                            <%-- 备注--%>
                        <div id="_xform_fdContactPerson_Form[!{index}].fdRemarks" _xform_type="text">
                            <xform:text property="fdContactPerson_Form[!{index}].fdRemarks" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataContact.fdRemarks')}" validators=" maxLength(200)" style="width:95%;" />
                        </div>
                    </td>
                    <td class="docList" align="center">
                            <%-- 第一联系人--%>
                        <div id="_xform_fdContactPerson_Form[!{index}].fdIsfirst" _xform_type="radio">
                            <xform:radio property="fdContactPerson_Form[!{index}].fdIsfirst" htmlElementProperties="id='fdContactPerson_Form[!{index}].fdIsfirst'" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                    <td class="docList" align="center">
                        <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
                            <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
                        </a>
                        &nbsp;
                        <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                            <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                        </a>
                    </td>
                </tr>
                <c:forEach items="${eopBasedataCustomerForm.fdContactPerson_Form}" var="fdContactPerson_FormItem" varStatus="vstatus">
                    <tr KMSS_IsContentRow="1" class="docListTr">
                        <td class="docList" align="center">
                            <input type="checkbox" name="DocList_Selected" />
                        </td>
                        <td class="docList" align="center">
                                ${vstatus.index+1}
                        </td>
                        <td class="docList" align="center">
                                <%-- 姓名--%>
                            <input type="hidden" name="fdContactPerson_Form[${vstatus.index}].fdId" value="${fdContactPerson_FormItem.fdId}" />
                            <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdName" _xform_type="text">
                                <xform:text property="fdContactPerson_Form[${vstatus.index}].fdName" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataContact.fdName')}" validators=" maxLength(200)" style="width:95%;" />
                            </div>
                        </td>
                        <td class="docList" align="center">
                                <%-- 职务--%>
                            <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdPosition" _xform_type="text">
                                <xform:text property="fdContactPerson_Form[${vstatus.index}].fdPosition" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataContact.fdPosition')}" validators=" maxLength(200)" style="width:95%;" />
                            </div>
                        </td>
                        <td class="docList" align="center">
                                <%-- 联系电话--%>
                            <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdPhone" _xform_type="text">
                                <xform:text property="fdContactPerson_Form[${vstatus.index}].fdPhone" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataContact.fdPhone')}" validators=" maxLength(200)" style="width:95%;" />
                            </div>
                        </td>
                        <td class="docList" align="center">
                                <%-- 电子邮箱--%>
                            <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdEmail" _xform_type="text">
                                <xform:text property="fdContactPerson_Form[${vstatus.index}].fdEmail" showStatus="edit" required="true" subject="${lfn:message('eop-basedata:eopBasedataContact.fdEmail')}" validators=" maxLength(200)" style="width:95%;" />
                            </div>
                        </td>
                        <td class="docList" align="center">
                                <%-- 联系地址--%>
                            <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdAddress" _xform_type="text">
                                <xform:text property="fdContactPerson_Form[${vstatus.index}].fdAddress" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataContact.fdAddress')}" validators=" maxLength(200)" style="width:95%;" />
                            </div>
                        </td>
                        <td class="docList" align="center">
                                <%-- 备注--%>
                            <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdRemarks" _xform_type="text">
                                <xform:text property="fdContactPerson_Form[${vstatus.index}].fdRemarks" showStatus="edit" subject="${lfn:message('eop-basedata:eopBasedataContact.fdRemarks')}" validators=" maxLength(200)" style="width:95%;" />
                            </div>
                        </td>
                        <td class="docList" align="center">
                                <%-- 第一联系人--%>
                            <div id="_xform_fdContactPerson_Form[${vstatus.index}].fdIsfirst" _xform_type="radio">
                                <xform:radio property="fdContactPerson_Form[${vstatus.index}].fdIsfirst" htmlElementProperties="id='fdContactPerson_Form[${vstatus.index}].fdIsfirst'" showStatus="edit">
                                    <xform:enumsDataSource enumsType="common_yesno" />
                                </xform:radio>
                            </div>
                        </td>
                        <td class="docList" align="center">
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
                    <td colspan="10">
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
            <input type="hidden" name="fdContactPerson_Flag" value="1">
            <script>
                Com_IncludeFile("doclist.js");
            </script>
            <script>
                DocList_Info.push('TABLE_DocList_fdContactPerson_Form');
            </script>

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
