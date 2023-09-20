<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<ui:content title="${ lfn:message('fssc-loan:py.JiBenXinXi') }" expand="false">
    <table class="tb_normal" width="100%">
        <tr>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanTransfer.docCreator')}
            </td>
            <td width="16.6%">
                <%-- 创建人--%>
                <div id="_xform_docCreatorId" _xform_type="address">
                    <ui:person personId="${fsscLoanTransferForm.docCreatorId}" personName="${fsscLoanTransferForm.docCreatorName}" />
                </div>
            </td>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanTransfer.docCreatorDept')}
            </td>
            <td width="16.6%">
                <%-- 创建人部门--%>
                <div id="_xform_docCreatorDeptId" _xform_type="address">
                    <xform:address propertyId="docCreatorDeptId" propertyName="docCreatorDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
                </div>
            </td>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanTransfer.docNumber')}
            </td>
            <td width="16.6%">
                <%-- 编号--%>
                <div id="_xform_docNumber" _xform_type="text">
                    <xform:text property="docNumber" showStatus="view" style="width:95%;" />
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanTransfer.docStatus')}
            </td>
            <td width="16.6%">
                <%-- 文档状态--%>
                <div id="_xform_docStatus" _xform_type="text">
                    <xform:select property="docStatus" htmlElementProperties="id='docStatus'" showStatus="view">
                        <xform:enumsDataSource enumsType="fssc_loan_doc_status" />
                    </xform:select>
                </div>
            </td>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanTransfer.fdVoucherStatus')}
            </td>
            <td width="16.6%">
                <%-- 制证状态--%>
                <div id="_xform_fdVoucherStatus" _xform_type="select">
                    <xform:select property="fdVoucherStatus" showStatus="view" style="width:95%;" >
                        <xform:enumsDataSource enumsType="fssc_loan_fd_voucher_status" />
                    </xform:select>
                </div>
            </td>
            <td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanTransfer.fdBookkeepingStatus')}
            </td>
            <td width="16.6%">
                <%-- 记账状态--%>
                <div id="_xform_fdBookkeepingStatus" _xform_type="select">
                    <xform:select property="fdBookkeepingStatus" showStatus="view" style="width:95%;" >
                        <xform:enumsDataSource enumsType="fssc_loan_fd_bookkeeping_status" />
                    </xform:select>
                </div>
            </td>
        </tr>
        <tr>
        	<td class="td_normal_title" width="16.6%">
                ${lfn:message('fssc-loan:fsscLoanTransfer.docPublishTime')}
            </td>
            <td width="16.6%" colspan="5">
                <div id="_xform_docPublishTime" _xform_type="text">
                    <xform:text property="docPublishTime" showStatus="view" style="width:95%;" />
                </div>
            </td>
        </tr>
    </table>
</ui:content>
