<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<html>

<head>
  <meta charset="utf-8">
  <link rel="stylesheet" type="text/css" href="../resource/css/msg.css">
  <title>${lfn:message('fssc-expense:fssc.expense.msg.title') }</title>
</head>
<script type="text/javascript">
var monthObject = JSON.parse('${monthObject}');
var expenseObject = JSON.parse("${expenseObject}");
</script>
<body>
  <div class="fc-apMain">
    <div class="fc-apMain-header">
      <ul>
        <li class="fc-apMain-currentTab">${lfn:message('fssc-expense:fssc.expense.msg.auxiliary') }</li>
        <li>${lfn:message('fssc-expense:fssc.expense.msg.intelligent.detection') }</li>
      </ul>
    </div>
    <!--辅助信息 start-->
    <div class="fc-apMain-content displayStatus">
      <!--单据主要信息 start-->
      <div class="fc-apMain-content-item ">
        <div class="fc-apMain-content-itemHeader">
          <div class="left">
            <span class="fc-apMain-content-itemHeader-span"></span>
            <span class="fc-apMain-content-itemHeader-name">${lfn:message('fssc-expense:fssc.expense.msg.main.imp.information') }</span>
          </div>
          <div class="right itemHeader-icon"></div>
        </div>
        <div class="fc-apMain-content-itemContent itemContent_assist">
          <div class="fc-apMain-content-itemContent-first">
            <div class="amount-detail">
              <div class="amount"><span><kmss:showNumber value='${fdTotalMoney }' pattern="0.00"></kmss:showNumber></span></div>
              <div class="categoryName"><span>${lfn:message('fssc-expense:fssc.expense.msg.total.reimbursement.amount') }</span></div>
            </div>
            <div class="amount-detail">
              <div class="amount amount-zero"><span><kmss:showNumber value='${fdOffsetMoney }' pattern="0.00"></kmss:showNumber></span></div>
              <div class="categoryName"><span>${lfn:message('fssc-expense:fssc.expense.msg.offset.amount') }</span></div>
            </div>
            <div class="amount-detail">
              <div class="amount amount-normal"><span><kmss:showNumber value='${fdAccountsMoney }' pattern="0.00"></kmss:showNumber></span></div>
              <div class="categoryName"><span>${lfn:message('fssc-expense:fssc.expense.msg.payment.amount') }<!-- 支付金额（元） --></span></div>
            </div>
          </div>
<c:if test="${isHasBudget }">
          <div class="fc-apMain-content-itemContent-second">
            <table>
              <thead>
                <tr>
                  <th>${lfn:message('fssc-expense:fssc.expense.msg.budget.account') }<!-- 预算科目 --></th>
                  <th>${lfn:message('fssc-expense:fssc.expense.msg.budget.fee') }<!-- 费用预算 --></th>
                  <th>${lfn:message('fssc-expense:fssc.expense.msg.budget.using') }<!-- 冻结金额 --></th>
                  <th>${lfn:message('fssc-expense:fssc.expense.msg.budget.used') }<!-- 已用金额 --></th>
                  <th>${lfn:message('fssc-expense:fssc.expense.msg.budget.canuse') }<!-- 可用金额 --></th>
                </tr>
              </thead>
              <tbody>
               <c:forEach items="${budgetMapList}" var="budgetMapList" varStatus="vstatus">
                <tr>
                  <td>${ budgetMapList.fdSubject}</td>
                  <td><kmss:showNumber value="${ budgetMapList.fdTotalAmount}" pattern="0.00"></kmss:showNumber></td>
                  <td><kmss:showNumber value="${ budgetMapList.fdOccupyAmount}" pattern="0.00"></kmss:showNumber></td>
                  <td><kmss:showNumber value="${ budgetMapList.fdAlreadyUsedAmount}" pattern="0.00"></kmss:showNumber></td>
                  <td><kmss:showNumber value="${ budgetMapList.fdCanUseAmount}" pattern="0.00"></kmss:showNumber></td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
</c:if>
          <div class="fc-apMain-content-itemContent-third">
            <ul>
             <c:forEach items="${fdStandardMapList}" var="fdStandardMapList" varStatus="vstatus">
              <li>
                <div class="liName"><span class="li-icon"></span><span class="li-name">${fdStandardMapList.fdItemName }</span></div>
                <div class="liStatus liStatus-assist"></span><kmss:showNumber value="${fdStandardMapList.fdMoney }" pattern="0.00"></kmss:showNumber></div>
              </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
      <!--单据主要信息 end-->
      <!--单据相关信息 start-->
      <div class="fc-apMain-content-item ">
        <div class="fc-apMain-content-itemHeader">
          <div class="left">
            <span class="fc-apMain-content-itemHeader-span"></span>
            <span class="fc-apMain-content-itemHeader-name">${lfn:message('fssc-expense:fssc.expense.msg.main.info') }<!-- 单据相关信息 --></span>
          </div>
          <div class="right itemHeader-icon"></div>
        </div>
        <div class="fc-apMain-content-itemContent itemContent_assist">
          <div class="fc-apMain-content-itemContent-left">
            <div class="total-application-title">
              <div class="total-application-title-mount"><span id="totalApplicationMount"><kmss:showNumber value="${feeByIdsObject.fdStandardMoney }" pattern="0.00"></kmss:showNumber></span></div>
              <div class="total-application-title-name"><span>${lfn:message('fssc-expense:fssc.expense.msg.1') }<!-- 申请总额（元） --></span></div>
            </div>
            <div class="total-application-legend">
              <div class="total-application-legendStatus not-reimbursed" id="not-reimbursed"></div>
              <div class="total-application-legendStatus reimbursement" id="reimbursement"></div>
              <div class="total-application-legendStatus reimbursed" id="reimbursed"></div>
            </div>
            <div class="total-application-definition">
              <div class="total-application-definition-item">
                <span class="definition-color definition-color-1"></span>
                <label class="definition-name definition-name-1">${lfn:message('fssc-expense:fssc.expense.msg.3') }<!-- 未报销金额 -->：</label>
                <span class="definition-mount definition-name-1" id="notApplicationMount"><kmss:showNumber value="${feeByIdsObject.fdCanUseMoney }" pattern="0.00"></kmss:showNumber></span>
              </div>
              <div class="total-application-definition-item">
                <span class="definition-color definition-color-2"></span>
                <label class="definition-name ">${lfn:message('fssc-expense:fssc.expense.msg.4') }<!-- 报销中金额 -->：</label>
                <span class="definition-mount " id="underApplicationMount"><kmss:showNumber value="${feeByIdsObject.fdUseingMoney }" pattern="0.00" ></kmss:showNumber></span>
              </div>
              <div class="total-application-definition-item">
                <span class="definition-color definition-color-3"></span>
                <label class="definition-name">${lfn:message('fssc-expense:fssc.expense.msg.5') }<!-- 已报销金额 -->：</label>
                <span class="definition-mount" id="finishedApplicationMount"><kmss:showNumber value="${feeByIdsObject.fdUsedMoney }" pattern="0.00" ></kmss:showNumber></span>
              </div>
            </div>
          </div>
          <div class="fc-apMain-content-itemContent-right">
            <div class="total-application-title">
              <div class="total-application-title-mount"><span id="totalApplyMount"><kmss:showNumber value='${fdLoanTotalObj["1"] }' pattern="0.00" ></kmss:showNumber></span></div>
              <div class="total-application-title-name"><span>${lfn:message('fssc-expense:fssc.expense.msg.2') }<!-- 借款总额（元） --></span></div>
            </div>
            <div class="total-application-legend">
              <div class="total-application-legendStatus not-flushable" id="not-flushable"></div>
              <div class="total-application-legendStatus replaying" id="replaying"></div>
              <div class="total-application-legendStatus replayed" id="replayed"></div>
            </div>
            <div class="total-application-definition">
              <div class="total-application-definition-item">
                <span class="definition-color definition-color-4"></span>
                <label class="definition-name definition-name-2">${lfn:message('fssc-expense:fssc.expense.msg.7') }<!-- 可冲抵金额 -->：</label>
                <span class="definition-mount definition-name-2" id="notApplyMount"><kmss:showNumber value='${fdLoanTotalObj["canUse"] }' pattern="0.00" ></kmss:showNumber></span>
              </div>
              <div class="total-application-definition-item">
                <span class="definition-color definition-color-5"></span>
                <label class="definition-name ">${lfn:message('fssc-expense:fssc.expense.msg.8') }<!-- 还款中金额 -->：</label>
                <span class="definition-mount " id="underApplyMount"><kmss:showNumber value='${fdLoanTotalObj["2"] }' pattern="0.00" ></kmss:showNumber></span>
              </div>
              <div class="total-application-definition-item">
                <span class="definition-color definition-color-3"></span>
                <label class="definition-name">${lfn:message('fssc-expense:fssc.expense.msg.9') }<!-- 已还款金额 -->：</label>
                <span class="definition-mount" id="finishedApplyMount"><kmss:showNumber value='${fdLoanTotalObj["3"] }' pattern="0.00" ></kmss:showNumber></span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--单据相关信息 end-->
      <!--个人统计信息 start-->
      <div class="fc-apMain-content-item">
        <div class="fc-apMain-content-itemHeader">
          <div class="left">
            <span class="fc-apMain-content-itemHeader-span"></span>
            <span class="fc-apMain-content-itemHeader-name">${lfn:message('fssc-expense:fssc.expense.msg.10') }<!-- 个人统计信息 --></span>
          </div>
          <div class="right itemHeader-icon"></div>
        </div>
        <div class="fc-apMain-content-itemContent itemContent_assist">
          <div class="fc-apMain-content-itemContent-chartHistogram">
            <div class="itemContent-chartHistogram-header">
              <span>${lfn:message('fssc-expense:fssc.expense.msg.11') }<!-- 个人前12个月报销统计图 --></span>
            </div>
            <div class="itemContent-chartHistogram-chart" id="reimbursement-statisticsChart"></div>
          </div>
          <div class="fc-apMain-content-itemContent-chartHistogram">
            <div class="itemContent-chartHistogram-header">
              <span>${lfn:message('fssc-expense:fssc.expense.msg.12') }<!-- 个人申请统计图 --></span>
            </div>
            <div class="chartHistogram-left">
              <div class="itemContent-chartCircular-chart" id="individual-applicationChart"></div>
            </div>
            <div class="chartHistogram-right">
              <div class="total-application-title">
                <div class="total-application-title-mount"><span id="feeByCreaObject_fdStandardMoney"><kmss:showNumber value="${feeByCreaObject.fdStandardMoney }" pattern="0.00"></kmss:showNumber></span></div>
                <div class="total-application-title-name"><span>${lfn:message('fssc-expense:fssc.expense.msg.1') }<!-- 申请总额（元） --></span></div>
              </div>
              <div class="total-application-definition">
                <div class="total-application-definition-item">
                  <span class="definition-color definition-color-6"></span>
                  <label class="definition-name definition-name-3">${lfn:message('fssc-expense:fssc.expense.msg.3') }<!-- 未报销金额 -->：</label>
                  <span class="definition-mount definition-name-3" id="feeByCreaObject_fdCanUseMoney"><kmss:showNumber value="${feeByCreaObject.fdCanUseMoney }" pattern="0.00"></kmss:showNumber></span>
                </div>
                <div class="total-application-definition-item">
                  <span class="definition-color definition-color-7"></span>
                  <label class="definition-name ">${lfn:message('fssc-expense:fssc.expense.msg.4') }<!-- 报销中金额 -->：</label>
                  <span class="definition-mount " id="feeByCreaObject_fdUseingMoney"><kmss:showNumber value="${feeByCreaObject.fdUseingMoney }" pattern="0.00"></kmss:showNumber></span>
                </div>
                <div class="total-application-definition-item">
                  <span class="definition-color definition-color-10"></span>
                  <label class="definition-name">${lfn:message('fssc-expense:fssc.expense.msg.5') }<!-- 已报销金额 -->：</label>
                  <span class="definition-mount" id="feeByCreaObject_fdUsedMoney"><kmss:showNumber value="${feeByCreaObject.fdUsedMoney }" pattern="0.00"></kmss:showNumber></span>
                </div>
              </div>
            </div>
          </div>
          <div class="fc-apMain-content-itemContent-chartHistogram">
            <div class="itemContent-chartHistogram-header">
              <span>${lfn:message('fssc-expense:fssc.expense.msg.13') }<!-- 个人借款统计图 --></span>
            </div>
            <div class="chartHistogram-left">
              <div class="itemContent-chartCircular-chart" id="personal-loanChart"></div>
            </div>
            <div class="chartHistogram-right">
              <div class="total-application-title">
                <div class="total-application-title-mount"><span id="fdLoanTotalObj_total"><kmss:showNumber value='${fdLoanTotalObj["1"] }' pattern="0.00"></kmss:showNumber></span></div>
                <div class="total-application-title-name"><span>${lfn:message('fssc-expense:fssc.expense.msg.2') }<!-- 借款总额（元） --></span></div>
              </div>
              <div class="total-application-definition">
                <div class="total-application-definition-item">
                  <span class="definition-color definition-color-8"></span>
                  <label class="definition-name definition-name-4">${lfn:message('fssc-expense:fssc.expense.msg.6') }<!-- 未还款金额 -->：</label>
                  <span class="definition-mount definition-name-4" id="fdLoanTotalObj_canUse"><kmss:showNumber value='${fdLoanTotalObj["canUse"] }' pattern="0.00"></kmss:showNumber></span>
                </div>
                <div class="total-application-definition-item">
                  <span class="definition-color definition-color-9"></span>
                  <label class="definition-name ">${lfn:message('fssc-expense:fssc.expense.msg.8') }<!-- 还款中金额 -->：</label>
                  <span class="definition-mount " id="fdLoanTotalObj_useing"><kmss:showNumber value='${fdLoanTotalObj["2"] }' pattern="0.00"></kmss:showNumber></span>
                </div>
                <div class="total-application-definition-item">
                  <span class="definition-color definition-color-10"></span>
                  <label class="definition-name">${lfn:message('fssc-expense:fssc.expense.msg.9') }<!-- 已还款金额 -->：</label>
                  <span class="definition-mount" id="fdLoanTotalObj_used"><kmss:showNumber value='${fdLoanTotalObj["3"] }' pattern="0.00"></kmss:showNumber></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--个人统计信息 end-->
      <!--历史单据 start-->
      <div class="fc-apMain-content-item">
        <div class="fc-apMain-content-itemHeader">
          <div class="left">
            <span class="fc-apMain-content-itemHeader-span"></span>
            <span class="fc-apMain-content-itemHeader-name">${lfn:message('fssc-expense:fssc.expense.msg.14') }<!-- 历史单据 --></span>
          </div>
          <div class="right itemHeader-icon"></div>
        </div>
        <div class="fc-apMain-content-itemContent itemContent_assist">
          <div class="fc-apMain-content-itemContent-list">
            <ul>
             <c:forEach items="${expenseList}" var="fsscExpenseMainItem" varStatus="vstatus">
	              <li>
	                <a href="${LUI_ContextPath }/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=view&fdId=${fsscExpenseMainItem.fdId}" target="_blank">
	                  <div class="liName"><span class="li-icon"></span><span class="li-name">${fsscExpenseMainItem.docSubject }</span></div>
	                  <div class="liName"><span class="receipt">${fsscExpenseMainItem.docNumber }</span></div>
	                  <div class="liStatus liStatus-assist"><kmss:showNumber value="${fsscExpenseMainItem.fdTotalApprovedMoney }" pattern="0.00" ></kmss:showNumber></div>
	                </a>
	              </li>
              </c:forEach>
             
            </ul>
            <div class="fc-apMain-content-itemContent-listMore">
              <div>
                <a href="${LUI_ContextPath }/fssc/expense/index.jsp?j_module=true#mydoc=create&j_path=%2FlistCreate" target="_blank">
                <span class="itemContent-listMore-iconLeft"></span>
                <span class="itemContent-listMore-name">${lfn:message('fssc-expense:fssc.expense.msg.17') }<!-- 查看更多单据 --></span>
                <span class="itemContent-listMore-iconRight"></span>
              </a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--历史单据 end-->

    </div>
    <!--辅助信息 end-->
    <!--智能检测 start-->
    <div class="fc-apMain-content">
      <!--业务关联检测 start-->
      <div class="fc-apMain-content-item">
        <div class="fc-apMain-content-itemHeader">
          <div class="left">
            <span class="fc-apMain-content-itemHeader-span"></span>
            <span class="fc-apMain-content-itemHeader-name">${lfn:message('fssc-expense:fssc.expense.msg.15') }<!-- 业务关联检测 --></span>
          </div>
          <div class="right itemHeader-icon" id="icon-1"></div>
        </div>
        <div class="fc-apMain-content-itemContent" id="itemContent-1">
          <ul>
            <li>
              <div class="liName"><span class="li-icon"></span><span class="li-name">${lfn:message('fssc-expense:fssc.expense.msg.16') }<!-- 是否关联项目 --></span></div>
              <c:choose>
              	<c:when test="${isProject }">
              		 <div class="liStatus liStatus-link"><span class="liStatus-icon liStatus-icon-link"></span>${lfn:message('fssc-expense:fssc.expense.msg.20') }<!-- 关联 --></div>
              	</c:when>
              	<c:otherwise>
              		<div class="liStatus liStatus-unLink"><span class="liStatus-icon liStatus-icon-unLink"></span>${lfn:message('fssc-expense:fssc.expense.msg.21') }<!-- 不关联 --></div>
              	</c:otherwise>
              </c:choose>
            </li>
            <li>
              <div class="liName"><span class="li-icon"></span><span class="li-name">${lfn:message('fssc-expense:fssc.expense.msg.18') }<!-- 是否关联申请 --></span></div>
              <c:choose>
              	<c:when test="${isHaveFee }">
              		 <div class="liStatus liStatus-link"><span class="liStatus-icon liStatus-icon-link"></span>${lfn:message('fssc-expense:fssc.expense.msg.20') }<!-- 关联 --></div>
              	</c:when>
              	<c:otherwise>
              		<div class="liStatus liStatus-unLink"><span class="liStatus-icon liStatus-icon-unLink"></span>${lfn:message('fssc-expense:fssc.expense.msg.21') }<!-- 不关联 --></div>
              	</c:otherwise>
              </c:choose>
            </li>
            <li>
              <div class="liName"><span class="li-icon"></span><span class="li-name">${lfn:message('fssc-expense:fssc.expense.msg.19') }<!-- 是否冲抵借款 --></span></div>
              <c:choose>
              	<c:when test="${isOffsetLoan }">
              		 <div class="liStatus liStatus-link"><span class="liStatus-icon liStatus-icon-link"></span>${lfn:message('fssc-expense:fssc.expense.msg.20') }<!-- 关联 --></div>
              	</c:when>
              	<c:otherwise>
              		<div class="liStatus liStatus-unLink"><span class="liStatus-icon liStatus-icon-unLink"></span>${lfn:message('fssc-expense:fssc.expense.msg.21') }<!-- 不关联 --></div>
              	</c:otherwise>
              </c:choose>
            </li>
          </ul>
        </div>
      </div>
      <!--业务关联检测 end-->
      <!--数据合规检测 start-->
      <div class="fc-apMain-content-item">
        <div class="fc-apMain-content-itemHeader">
          <div class="left">
            <span class="fc-apMain-content-itemHeader-span"></span>
            <span class="fc-apMain-content-itemHeader-name">${lfn:message('fssc-expense:fssc.expense.msg.22') }<!-- 数据合规检测 --></span>
          </div>
          <div class="right itemHeader-icon"></div>
        </div>
        <div class="fc-apMain-content-itemContent">
          <ul>
            <li>
              <div class="liName"><span class="li-icon"></span><span class="li-name">${lfn:message('fssc-expense:fssc.expense.msg.23') }<!-- 预算检测 --></span></div>
              <c:choose>
              	<c:when test="${budgetStatus == '0' || 0 == budgetStatus }">
              		 <div class="liStatus liStatus-overLink"><span class="liStatus-icon liStatus-icon-overLink"></span>${lfn:message('fssc-expense:fssc.expense.msg.26') }<!-- 无预算 --></div>
              	</c:when>
              	<c:when test="${budgetStatus == '1' || 1 == budgetStatus }">
              		<div class="liStatus liStatus-link"><span class="liStatus-icon liStatus-icon-link"></span>${lfn:message('fssc-expense:fssc.expense.msg.24') }<!-- 预算内 --></div>
              	</c:when>
              	<c:otherwise>
              		<div class="liStatus liStatus-overLink"><span class="liStatus-icon liStatus-icon-overLink"></span>${lfn:message('fssc-expense:fssc.expense.msg.25') }<!-- 超预算 --></div>
              	</c:otherwise>
              </c:choose>
              
              
              
              
            </li>
            <li>
              <div class="liName"><span class="li-icon"></span><span class="li-name">${lfn:message('fssc-expense:fssc.expense.msg.27') }<!-- 标准检测 --></span></div>
              <c:choose>
              	<c:when test="${fdStandStatus == '0' || 0 == fdStandStatus }">
              		 <div class="liStatus liStatus-overLink"><span class="liStatus-icon liStatus-icon-overLink"></span>${lfn:message('fssc-expense:fssc.expense.msg.30') }<!-- 无标准 --></div>
              	</c:when>
              	<c:when test="${fdStandStatus == '1' || 1 == fdStandStatus }">
              		<div class="liStatus liStatus-link"><span class="liStatus-icon liStatus-icon-link"></span>${lfn:message('fssc-expense:fssc.expense.msg.28') }<!-- 标准内 --></div>
              	</c:when>
              	<c:otherwise>
              		<div class="liStatus liStatus-overLink"><span class="liStatus-icon liStatus-icon-overLink"></span>${lfn:message('fssc-expense:fssc.expense.msg.29') }<!-- 超标准 --></div>
              	</c:otherwise>
              </c:choose>
            </li>
            <li>
              <div class="liName"><span class="li-icon"></span><span class="li-name">${lfn:message('fssc-expense:fssc.expense.msg.31') }<!-- 申请检测 --></span></div>
              <c:choose>
              	<c:when test="${fdFeeStatus == '0' || 0 == fdFeeStatus }">
              		 <div class="liStatus liStatus-overLink"><span class="liStatus-icon liStatus-icon-overLink"></span>${lfn:message('fssc-expense:fssc.expense.msg.34') }<!-- 无申请 --></div>
              	</c:when>
              	<c:when test="${fdFeeStatus == '1' || 1 == fdFeeStatus }">
              		<div class="liStatus liStatus-link"><span class="liStatus-icon liStatus-icon-link"></span>${lfn:message('fssc-expense:fssc.expense.msg.33') }<!-- 申请内 --></div>
              	</c:when>
              	<c:otherwise>
              		<div class="liStatus liStatus-overLink"><span class="liStatus-icon liStatus-icon-overLink"></span>${lfn:message('fssc-expense:fssc.expense.msg.32') }<!-- 超申请 --></div>
              	</c:otherwise>
              </c:choose>
            </li>
          </ul>
        </div>
      </div>
      <!--数据合规检测 end-->
      <!--其它项目检测 start-->
     <%--  <div class="fc-apMain-content-item">
        <div class="fc-apMain-content-itemHeader">
          <div class="left">
            <span class="fc-apMain-content-itemHeader-span"></span>
            <span class="fc-apMain-content-itemHeader-name">${lfn:message('fssc-expense:fssc.expense.msg.35') }<!-- 其它项目检测 --></span>
          </div>
          <div class="right itemHeader-icon"></div>
        </div>
        <div class="fc-apMain-content-itemContent">
          <ul>
            <li>
              <div class="liName"><span class="li-icon"></span><span class="li-name">${lfn:message('fssc-expense:fssc.expense.msg.36') }<!-- 发票与费用时间检测 --></span></div>
              <div class="liStatus liStatus-link"><span class="liStatus-icon liStatus-icon-link"></span>${lfn:message('fssc-expense:fssc.expense.msg.38') }<!-- 通过 --></div>
            </li>
            <li>
              <div class="liName"><span class="li-icon"></span><span class="li-name">${lfn:message('fssc-expense:fssc.expense.msg.37') }<!-- 进项抵扣填写检测 --></span></div>
              <div class="liStatus liStatus-overLink"><span class="liStatus-icon liStatus-icon-notLink"></span>${lfn:message('fssc-expense:fssc.expense.msg.39') }<!-- 不通过 --></div>
            </li>
          </ul>
        </div>
      </div>
    </div> --%>
    <!--其它项目检测 end-->
    <!--智能检测 end-->
  </div>
</body>
<script src="../resource/js/jquery.js"></script>
<script src="../resource/js/echarts.min.js"></script>
<script src="../resource/js/main.js"></script>

</html>
