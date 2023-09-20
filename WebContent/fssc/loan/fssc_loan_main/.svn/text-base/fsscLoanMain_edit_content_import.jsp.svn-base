<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<ui:content title="${ lfn:message('fssc-loan:py.JiBenXinXi') }">
    <table class="tb_normal" width="100%">
        <tr>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanMain.docCreator')}
            </td>
            <td width="16.6%">
                <%-- 创建人--%>
                <div id="_xform_docCreatorId" _xform_type="address">
                    <ui:person personId="${fsscLoanMainForm.docCreatorId}" personName="${fsscLoanMainForm.docCreatorName}" />
                </div>
            </td>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanMain.docCreatorDept')}
            </td>
            <td width="16.6%">
                <%-- 创建人部门--%>
                <div id="_xform_docCreatorDeptId" _xform_type="address">
                    <xform:address propertyId="docCreatorDeptId" propertyName="docCreatorDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
                </div>
            </td>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanMain.fdLoanChargePerson')}
            </td>
            <td width="16.6%">
                <%-- 负责人--%>
                <div id="_xform_fdLoanChargePersonId" _xform_type="address">
                    <ui:person personId="${fsscLoanMainForm.fdLoanChargePersonId}" personName="${fsscLoanMainForm.fdLoanChargePersonName}" />
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanMain.docNumber')}
            </td>
            <td width="16.6%">
                <%-- 编号--%>
                <div id="_xform_docNumber" _xform_type="text">
                    <xform:text property="docNumber" showStatus="view" style="width:95%;" />
                </div>
            </td>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanMain.docStatus')}
            </td>
            <td width="16.6%">
                <%-- 文档状态--%>
                <div id="_xform_docStatus" _xform_type="select">
                    <xform:select property="docStatus" htmlElementProperties="id='docStatus'" showStatus="view">
                        <xform:enumsDataSource enumsType="common_status" />
                    </xform:select>
                </div>
            </td>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanMain.docTemplate')}
            </td>
            <td width="16.6%">
                <%-- 分类模板--%>
                <div id="_xform_docTemplateId" _xform_type="dialog">
                    <xform:dialog propertyId="docTemplateId" propertyName="docTemplateName" showStatus="view" style="width:95%;">
                        dialogCategory('com.landray.kmss.fssc.loan.model.FsscLoanCategory','docTemplateId','docTemplateName',false);
                    </xform:dialog>
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanMain.fdPaymentStatus')}
            </td>
            <td width="16.6%">
                <%-- 付款状态--%>
                <div id="_xform_fdPaymentStatus" _xform_type="select">
                    <xform:select property="fdPaymentStatus" showStatus="view" style="width:95%;" >
                        <xform:enumsDataSource enumsType="eop_basedata_payment_status" />
                    </xform:select>
                </div>
            </td>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanMain.fdVoucherStatus')}
            </td>
            <td width="16.6%">
                <%-- 制证状态--%>
                <div id="_xform_fdVoucherStatus" _xform_type="select">
                    <xform:select property="fdVoucherStatus" showStatus="view" style="width:95%;" >
                        <xform:enumsDataSource enumsType="eop_basedata_fd_voucher_status" />
                    </xform:select>
                </div>
            </td>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanMain.fdBookkeepingStatus')}
            </td>
            <td width="16.6%">
                <%-- 记账状态--%>
                <div id="_xform_fdBookkeepingStatus" _xform_type="select">
                    <xform:select property="fdBookkeepingStatus" showStatus="view" style="width:95%;" >
                        <xform:enumsDataSource enumsType="eop_basedata_fd_bookkeeping_status" />
                    </xform:select>
                </div>
            </td>
        </tr>
        <tr>
        	<td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanMain.docPublishTime')}
            </td>
            <td width="16.6%">
                <div id="_xform_docPublishTime" _xform_type="text">
                    <xform:text property="docPublishTime" showStatus="view" style="width:95%;" />
                </div>
            </td>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanMain.fdBillStatus')}
            </td>
            <td width="16.6%" colspan="3">
                <%-- 实体单据状态--%>
                <div id="_xform_fdBillStatus" _xform_type="select">
                    <xform:select property="fdBillStatus" showStatus="view" style="width:95%;" >
                        <xform:enumsDataSource enumsType="eop_basedata_bill_status" />
                    </xform:select>
                </div>
            </td>
        </tr>
    </table>
</ui:content>
