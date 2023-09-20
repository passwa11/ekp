<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html:form action="/fssc/loan/fssc_loan_main/fsscLoanMain.do">
    <c:if test="${queryPage == null || queryPage.totalrows == 0}">
        <%@ include file="/resource/jsp/list_norecord.jsp"%>
    </c:if>
    <c:if test="${queryPage.totalrows > 0}">
        <c:set var="totalMoney" value="0"/>
        <table id="List_ViewTable">
            <tr>
                <sunbor:columnHead htmlTag="td">
                    <td width="40pt"><bean:message key="page.serial"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.docSubject"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.docNumber"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.fdCompany"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.fdCostCenter"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.docTemplate"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.fdLoanPerson"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.fdOrgLoanMoney"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.fdLoanMoney"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.fdLoanMoney30"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.fdLoanMoneyNot"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.fdLoanMoney20"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.fdLoanMoneyCanUse"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.fdLoanMoneyTransfer"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.fdLoanChargePerson"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.fdExpectedDate"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.fdMoreDay"/></td>
                    <td><bean:message bundle="fssc-loan" key="fsscLoanMain.searchList.docStatuse"/></td>
                </sunbor:columnHead>
            </tr>
            <c:forEach items="${queryPage.list}" var="fsscLoanMain" varStatus="vstatus">
                <tr kmss_href="<c:url value="/fssc/loan/fssc_loan_main/fsscLoanMain.do" />?method=view&fdId=${fsscLoanMain.fdId}">
                    <td>${vstatus.index+1}</td>
                    <td>${fsscLoanMain.docSubject}</td>
                    <td>${fsscLoanMain.docNumber}</td>
                    <td>${fsscLoanMain.fdCompany.fdName}</td>
                    <td>${fsscLoanMain.fdCostCenter.fdName}</td>
                    <td>${fsscLoanMain.docTemplate.fdName}</td>
                    <td>${fsscLoanMain.fdLoanPerson.fdName}</td>
                    <td><fmt:formatNumber value="${fsscLoanMain.fdLoanMoney}" pattern="#.##" minFractionDigits="2" /></td>
                    <c:choose>
                        <c:when test="${moneyMap[fsscLoanMain.fdId] != null}">
                            <td><fmt:formatNumber value="${moneyMap[fsscLoanMain.fdId]['fdLoanMoney']}" pattern="#.##" minFractionDigits="2" /></td>
                            <td><fmt:formatNumber value="${moneyMap[fsscLoanMain.fdId]['fdLoanMoney30']}" pattern="#.##" minFractionDigits="2" /></td>
                            <td><fmt:formatNumber value="${moneyMap[fsscLoanMain.fdId]['fdLoanMoneyNot']}" pattern="#.##" minFractionDigits="2" /></td>
                            <td><fmt:formatNumber value="${moneyMap[fsscLoanMain.fdId]['fdLoanMoney20']}" pattern="#.##" minFractionDigits="2" /></td>
                            <td><fmt:formatNumber value="${moneyMap[fsscLoanMain.fdId]['fdLoanMoneyCanUse']}" pattern="#.##" minFractionDigits="2" /></td>
                            <c:if test="${moneyMap[fsscLoanMain.fdId]['fdLoanMoneyTransfer'] > 0 }">
                                <td><fmt:formatNumber value="${moneyMap[fsscLoanMain.fdId]['fdLoanMoneyTransfer']}" pattern="#.##" minFractionDigits="2" /></td>
                                <td>${fsscLoanMain.fdLoanChargePerson.fdName}</td>
                            </c:if>
                            <c:if test="${moneyMap[fsscLoanMain.fdId]['fdLoanMoneyTransfer'] <= 0 }">
                                <td></td>
                                <td></td>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </c:otherwise>
                    </c:choose>
                    <td><fmt:formatDate value="${fsscLoanMain.fdExpectedDate}"/> </td>
                    <td>${dayMap[fsscLoanMain.fdId]['day']}</td>
                    <td><sunbor:enumsShow value="${fsscLoanMain.docStatus}" enumsType="fssc_loan_doc_status" /></td>
                </tr>
            </c:forEach>
        </table>
        <%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
    </c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
