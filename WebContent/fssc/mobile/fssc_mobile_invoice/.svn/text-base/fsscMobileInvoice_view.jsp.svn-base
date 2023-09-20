<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
   		<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/invoiceEdit.css?s_cache=${LUI_Cache }">
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js?s_cache=${LUI_Cache }"></script>
    <title>发票明细</title>
</head>
<body>
    <div class="ld-invoice-detail">
		<c:if test="${not empty data.fdAttId and fn:indexOf(data.attName, 'pdf')==-1}">
       <div class="ld-invoice-img">
           <img src="${LUI_ContextPath}/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${data.fdAttId}&open=1" alt="">
       </div>
       </c:if>
       <div class="ld-invoice-detail-info">
            <div class="ld-invoice-detail-info-top">
            	<div>
                    <span>发票类型</span>
                    <input type="text" name="fdInvoiceTypeName" readonly="readonly" value="${data.fdInvoiceTypeName}">
                     <input type="text" name="fdInvoiceType" readonly="readonly" value="${data.fdInvoiceType}" hidden="true">
                </div>
                 <div>
                    <span>发票代码</span>
                    <input type="text" name="fdInvoiceCode" readonly="readonly" value="${data.fdInvoiceCode}">
                </div>
                <div>
                    <span>发票号码</span>
                    <input type="text"  name="fdInvoiceNumber" readonly="readonly" value="${data.fdInvoiceNumber}">
                </div>
                <div>
                    <span>发票校验码</span>
                    <input type="text"  name="fdCheckCode" readonly="readonly"  value="${data.fdCheckCode}">
                </div>
                <div>
                    <span>发票时间</span>
                    <input type="text"  name="fdInvoiceDate" readonly="readonly"  value="${data.fdInvoiceDate}">
                </div>
            </div>
            <div class="ld-invoice-detail-info-bottom">
                <div>
                    <span>税额</span>
                    <kmss:showNumber value="${data.fdTotalTax}" pattern="0.00"/>
                </div>
                <div>
                    <span>价税合计</span>
                    <kmss:showNumber value="${data.fdJshj}" pattern="0.00"/>
                </div>
                <div>
                    <span>购方名称</span>
                    <input type="text" readonly  name="fdPurchaserName" value="${data.fdPurchaserName}">
                </div>
                <div>
                    <span>购方税号</span>
                    <input type="text"  name="fdPurchaserTaxNo" readonly="readonly" value="${data.fdPurchaserTaxNo}">
                </div>
                <div>
                    <span>销方名称</span>
                    <input type="text"  name="fdSalesName"  readonly="readonly" value="${data.fdSalesName}">
                </div>
            </div>
       </div>
    </div>
    <c:if test="${empty param.viewOnly }">
    <div class="ld-footer">
        <div class="ld-footer-blueBg" onclick="editInvoice()">编辑</div>
        <div class="ld-footer-whiteBg" style="margin-left:15px;" onclick="deleteInvoice();" >删除</div>
    </div>
    </c:if>
    <c:if test="${not empty param.viewOnly }">
    <div class="ld-footer">
        <div class="ld-footer-whiteBg" style="width:100%;" onclick="window.open('${LUI_ContextPath}/fssc/mobile/fssc_mobile_invoice/fsscMobileInvoice.do?method=listMyInvoice','_self');" >返回</div>
    </div>
    </c:if>
    
     <div class="ld-invoice-modal" id="ld-invoice-img" style="display: none">
        <i></i>
        <div class="ld-invoice-modal-invoice">
            <img src="${LUI_ContextPath}/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${data.fdAttId}&open=1" alt="">
        </div>
    </div> 
    <input hidden="true" name="fdId" value="${fdId }">
    <input hidden="true" name="fdModelName" value="${fdModelName }">
    <input hidden="true" name="fdModelId" value="${fdModelId }">
    <input hidden="true" name="formMethod" value="${formMethod }">
</body>

<%@ include file="/resource/jsp/edit_down.jsp" %>
<script type="text/javascript">
//编辑
function editInvoice(){
	var fdId =$("[name='fdId']").val();
	var fdModelName =$("[name='fdModelName']").val();
	var fdModelId =$("[name='fdModelId']").val();
	var formMethod =$("[name='formMethod']").val();
	window.open("${LUI_ContextPath}/fssc/mobile/fssc_mobile_invoice/fsscMobileInvoice.do?method=edit&fdId="+fdId+"&fdModelName="+fdModelName+"&fdModelId="+fdModelId+"&formMethod="+formMethod, '_self');
}

$(".ld-invoice-img").click(function (){
	document.getElementById("ld-invoice-img").style.display="";
})

$(".ld-invoice-modal i").click(function (){
	document.getElementById("ld-invoice-img").style.display="none";
})

	 function deleteInvoice(){
	     var fdId=$("[name='fdId']").val();
		 var fdModelId = $("[name='fdModelId']").val();
		 var formMethod =$("[name='formMethod']").val();
		 $.ajax( {
	         url:Com_Parameter.ContextPath+"fssc/mobile/fssc_mobile_invoice/fsscMobileInvoice.do?method=delete&fdId="+fdId,
	         type: 'POST', 
	         dataType:"json",
	         data:{fdModelId:fdModelId},
	         async:false,    //用同步方式   async. 默认是true，即为异步方式
	         success:function(data){
	        	 var rtn = data;
	        	 if(data.result=='success'){
	        		 alert("删除成功！");
	        		 window.location.href = Com_Parameter.ContextPath+"/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method="+formMethod+"&fdId="+fdModelId;
	        	 } else {
	        		 alert("删除失败！");
	        		 window.location.href = Com_Parameter.ContextPath+"/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method="+formMethod+"&fdId="+fdModelId;
	        	 } 
	         }
		 })
	 }
	 

</script>
