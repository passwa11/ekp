<%@ page import="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<c:if test="${not empty fsscLoanRepaymentForm.fdVoucherStatus }">
<kmss:ifModuleExist path="/fssc/voucher/">
<c:set var="voucherView"  value="false"></c:set>
<!-- 凭证查看权限 -->
<kmss:authShow roles="ROLE_FSSCVOUCHER_VIEW">
	<c:set var="voucherView"  value="true"></c:set>
</kmss:authShow>
<!-- 财务人员 -->
<fssc:auth authType="staff" fdCompanyId="${fsscLoanMainForm.fdCompanyId}">
	<c:set var="voucherView"  value="true"></c:set>
</fssc:auth>
<!-- 重新制证权限 -->
<kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=refreshVoucher&fdId=${param.fdId}&fdModelName=com.landray.kmss.fssc.loan.model.FsscLoanRepayment">
	<c:set var="voucherView"  value="true"></c:set>
</kmss:auth>
<c:if test="${voucherView=='true'}">
    <ui:content title="${lfn:message('fssc-voucher:fsscVoucherMain.title.message')}">
        <c:import url="/fssc/voucher/fssc_voucher_main/fsscVoucherMain_modelView.jsp" charEncoding="UTF-8">
            <c:param name="fdModelId" value="${fsscLoanRepaymentForm.fdId}" />
            <c:param name="fdModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
            <c:param name="fdModelNumber" value="${fsscLoanRepaymentForm.docNumber}" />
            <c:param name="fdBookkeepingStatus" value="${fsscLoanRepaymentForm.fdBookkeepingStatus}" />
            <c:param name="fdIsVoucherVariant" value="${fsscLoanRepaymentForm.sysWfBusinessForm.fdNodeAdditionalInfo.voucherVariant}" />
        </c:import>
    </ui:content>
</c:if>
</kmss:ifModuleExist>
</c:if>
