<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="com.landray.kmss.util.ArrayUtil" %>
<%@ page import="com.landray.kmss.fssc.common.interfaces.IFsscCommonCashierPaymentService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String fdModelId = request.getParameter("fdModelId");
	String fdModelName = request.getParameter("fdModelName");
	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put("fdModelId", fdModelId);
	paramMap.put("fdModelName", fdModelName);

	IFsscCommonCashierPaymentService fsscCommonCashierPaymentService = (IFsscCommonCashierPaymentService) SpringBeanUtil.getBean("fsscCommonCashierPaymentService");
	Map<String, Object> tempMap = fsscCommonCashierPaymentService.getPaymentDetailInfo(paramMap);
	//获取携程订单
	if(!"failure".equals(tempMap.get("result")+"")){
		List<Map<String, String>> paymentDetailList = (List<Map<String, String>>) tempMap.get("paymentDetailList");
		request.setAttribute("paymentDetailList", paymentDetailList);
		request.setAttribute("fdModelId",fdModelId);
		request.setAttribute("fdModelName",fdModelName);
		if(ArrayUtil.isEmpty(paymentDetailList)){
			%>
				<%--<%@ include file="/resource/jsp/list_norecord.jsp"%>--%>
				<script>
					//如果没值就隐藏
					setTimeout(function(){
						//旧版页签显示
						$("div .lui_tabpage_float_nav_item_c").each(function () {
							if("${lfn:message('fssc-cashier:table.fsscCashierPaymentDetail')}" == $(this).html()){
								$(this).parent().parent().parent().hide();
							}
						});
						//右侧页签显示
						$("div .lui_tabpanel_sucktop_navs_item_c").each(function () {
							if("${lfn:message('fssc-cashier:table.fsscCashierPaymentDetail')}" == $(this).find("span").html()){
								$(this).parent().parent().hide();
							}
						});
					}, 1000);
				</script>
			<%
		}else{
			%>
				<table id="List_cashier_ViewTable" class="tb_normal" width="100%">
					<tr align="center" class="tr_normal_title">
						<td  width="5%"><bean:message key="page.serial" /></td>
						<td width="10%"><bean:message bundle="fssc-cashier" key="fsscCashierPaymentDetail.fdCompany" /></td>
						<td width="10%"><bean:message bundle="fssc-cashier" key="fsscCashierPaymentDetail.fdBasePayBank" /></td>
						<td width="10%"><bean:message bundle="fssc-cashier" key="fsscCashierPaymentDetail.fdBasePayWay" /></td>
						<td width="10%"><bean:message bundle="fssc-cashier" key="fsscCashierPaymentDetail.fdBaseCurrency" /></td>
						<td width="8%"><bean:message bundle="fssc-cashier" key="fsscCashierPaymentDetail.fdRate" /></td>
						<td width="8%"><bean:message bundle="fssc-cashier" key="fsscCashierPaymentDetail.fdPayeeName" /></td>
						<td width="8%"><bean:message bundle="fssc-cashier" key="fsscCashierPaymentDetail.fdPayeeAccount" /></td>
						<td width="8%"><bean:message bundle="fssc-cashier" key="fsscCashierPaymentDetail.fdPayeeBankName" /></td>
						<td width="8%"><bean:message bundle="fssc-cashier" key="fsscCashierPaymentDetail.fdPaymentMoney" /></td>
						<td width="8%"><bean:message bundle="fssc-cashier" key="fsscCashierPaymentDetail.fdStatus" /></td>
						<td width="8%"><bean:message bundle="fssc-cashier" key="fsscCashierPaymentDetail.fdPlanPaymentDate" /></td>
					</tr>
					<c:forEach items="${paymentDetailList}" var="paymentDetail" varStatus="vstatus">
						<tr onclick="window.open('${KMSS_Parameter_ContextPath}fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do?method=view&fdId=${paymentDetail['docMainId']}');">
							<td>${vstatus.index+1}</td>
							<td name="cashierDetail_Form[${vstatus.index+1}].fdCompanyName"><c:out value="${paymentDetail['fdCompanyName']}" /></td>
							<td name="cashierDetail_Form[${vstatus.index+1}].fdBasePayBankName"><c:out value="${paymentDetail['fdBasePayBankName']}" /></td>
							<td name="cashierDetail_Form[${vstatus.index+1}].fdBasePayWayName"><c:out value="${paymentDetail['fdBasePayWayName']}" /></td>
							<td name="cashierDetail_Form[${vstatus.index+1}].fdBaseCurrencyName"><c:out value="${paymentDetail['fdBaseCurrencyName']}" /></td>
							<td name="cashierDetail_Form[${vstatus.index+1}].fdRate"><c:out value="${paymentDetail['fdRate']}" /></td>
							<td name="cashierDetail_Form[${vstatus.index+1}].fdPayeeName"><c:out value="${paymentDetail['fdPayeeName']}" /></td>
							<td name="cashierDetail_Form[${vstatus.index+1}].fdPayeeAccount"><c:out value="${paymentDetail['fdPayeeAccount']}" /></td>
							<td name="cashierDetail_Form[${vstatus.index+1}].fdPayeeBankName"><c:out value="${paymentDetail['fdPayeeBankName']}" /></td>
							<td name="cashierDetail_Form[${vstatus.index+1}].fdPaymentMoney"><c:out value="${paymentDetail['fdPaymentMoney']}" /></td>
							<td ><sunbor:enumsShow value="${paymentDetail['fdStatus']}" enumsType="fssc_cashier_fd_status" /></td>
							<td name="cashierDetail_Form[${vstatus.index+1}].fdPlanPaymentDate"><c:out value="${paymentDetail['fdPlanPaymentDate']}" /></td>
							
						</tr>
					</c:forEach>
				</table>
				<br/>
				<br/>
			<%
		}
	}
%>

<script type="text/javascript">

  //重新生成付款单
    window.refreshPaymentForm = function () {
    	  var len = $("#TABLE_DocList_fdAccountsList_Form tr").length-1;
   	      var fdTotalApprovedMoney = parseInt($("[name='fdTotalApprovedMoney']").val()).toFixed(2);
   	      var  payArr= [];
		  for(var i=0;i<len;i++){
			  payArr.push({
				    'fdId':$("[name='fdAccountsList_Form["+i+"].fdId']").val(),
		    		'fdPayWayId':$("[name='fdAccountsList_Form["+i+"].fdPayWayId']").val(),
		    		'fdBankId':$("[name='fdAccountsList_Form["+i+"].fdBankId']").val(),
		    		'fdCurrencyId':$("[name='fdAccountsList_Form["+i+"].fdCurrencyId']").val(),
		    		'fdExchangeRate':$("[name='fdAccountsList_Form["+i+"].fdExchangeRate']").val(),
		    		'fdAccountId':$("[name='fdAccountsList_Form["+i+"].fdAccountId']").val(),
		    		'fdMoney': $("[name='fdAccountsList_Form["+i+"].fdMoney']").val(),
		    		'fdBankName': $("[name='fdAccountsList_Form["+i+"].fdBankName']").val(),
		    		'fdBankAccount': $("[name='fdAccountsList_Form["+i+"].fdBankAccount']").val(),
		    		'fdPayWayName':$("[name='fdAccountsList_Form["+i+"].fdPayWayName']").val(),
		    		'fdPayBankName':$("[name='fdAccountsList_Form["+i+"].fdPayBankName']").val(),
		    		'fdCurrencyName':$("[name='fdAccountsList_Form["+i+"].fdCurrencyName']").val(),
		    		'fdAccountName':$("[name='fdAccountsList_Form["+i+"].fdAccountName']").val(),
		    		'fdAccountAreaName':$("[name='fdAccountsList_Form["+i+"].fdAccountAreaName']").val(),
		    	});
		    }  
           seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
                var fdModelId = '${fdModelId}';
                var fdModelName = '${fdModelName}';
                var paramJson = JSON.stringify(payArr);
               dialog.confirm('${lfn:message("fssc-cashier:button.refresh.paymentForm") }', function(isOk) {
                   if(isOk) {
                       var params={"fdModelId":fdModelId,"fdModelName":fdModelName,"paramJson":paramJson};
                       $.ajax( {
                           url: "${KMSS_Parameter_ContextPath}fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do?method=refreshPaymentForm",
                           type: 'POST', // POST or GET
                           dataType:"json",
                           data:params,
                           async:false,    //用同步方式   async. 默认是true，即为异步方式
                           success:function(data){
                               if(data.fdIsBoolean == "true"){
                                   dialog.success("${lfn:message('return.optSuccess')}");
                                   refreshCashierPayment(data);
                               }else{
                                   dialog.alert("${lfn:message('fssc-cashier:button.refresh.cashier.error.message')}".replace("%text%", data.messageStr))
                               }
                           }
                       });
                   }
               });
           });
        
      }
  
   //刷新表单页面
    function refreshCashierPayment(data){
    	 $("#List_cashier_ViewTable tr:gt(0)").remove();
    	 var payMentListHtml = "";
    	 var list = data.payMentList;
         for(var i=0;i<list.length;i++){
        	 var fdStatus = list[i].fdStatus;
        	 var fdStatusName= "";
        	 if(fdStatus=="10"){
        		 fdStatusName ="${lfn:message('fssc-cashier:enums.fd_status.10')}"
        	 }else if(fdStatus=="30"){
        		 fdStatusName ="${lfn:message('fssc-cashier:enums.fd_status.30')}"
        	 }
           payMentListHtml += "<tr><td>"+(i+1)+"</td><td>"+list[i].fdCompanyName+"</td><td>"+list[i].docNumber+"</td><td>"+list[i].fdBasePayBankName+"</td><td>"
           +list[i].fdBasePayWayName+"</td><td>"+list[i].fdBaseCurrencyName+"</td><td>"+list[i].fdRate+"</td><td>"+list[i].fdPayeeName+"</td><td>"
           +list[i].fdPayeeAccount+"</td><td>"+list[i].fdPayeeBankName+"</td><td>"+ list[i].fdPaymentMoney+"</td><td>"+fdStatusName+"</td></tr>";  
         } 
       $("#List_cashier_ViewTable").append(payMentListHtml);
  }

</script>

