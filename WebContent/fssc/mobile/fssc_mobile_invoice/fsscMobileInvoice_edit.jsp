<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/invoiceEdit.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet"href="${LUI_ContextPath}/fssc/mobile/resource/css/swiper.min.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/Mdate.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/search.css">
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js?s_cache=${LUI_Cache }"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/Mdate.js?s_cache=${LUI_Cache }"></script>
	<script src="${LUI_ContextPath}/fssc/mobile/resource/js/iScroll.js?s_cache=${LUI_Cache }"></script>
	<script src="${LUI_ContextPath}/fssc/mobile/resource/js/picker.min.js"></script>
    <title>发票明细</title>
</head>
<body>
    <div class="ld-invoice-detail">
       <div class="ld-invoice-detail-info">
            <div class="ld-invoice-detail-info-top">
            	<div>
                    <span>发票类型</span>
                    <input type="text" name="fdInvoiceTypeName" id="fdInvoiceTypeName" style="position:relative;left:50px;" placeholder="请选择发票类型" readonly="readonly" value="${data.fdInvoiceTypeName}" onclick="selectInvoiceTypeItem();" >
                     <i class="ld-remember-arrow"></i>
                     <input type="text" name="fdInvoiceType" id="fdInvoiceType" readonly="readonly" value="${data.fdInvoiceType}" hidden="true">
                </div>
                <div>
                    <span>发票代码</span>
                    <input type="text" name="fdInvoiceCode" placeholder="请填写发票代码"  value="${data.fdInvoiceCode}">
                </div>
                <div>
                    <span>发票号码</span>
                    <input type="text"  name="fdInvoiceNumber" placeholder="请填写发票代码"  value="${data.fdInvoiceNumber}">
                </div>
                <div>
                    <span>发票校验码</span>
                    <input type="text"  name="fdCheckCode" placeholder="请填写发票校验码" value="${data.fdCheckCode}">
                </div>
                <div>
                    <span>发票时间</span>
                    <input type="text" id="fdInvoiceDate" name="fdInvoiceDate" placeholder="请选择发票时间" style="position:relative;left:50px;" readonly="readonly" value="${data.fdInvoiceDate}" >
                    <i class="ld-remember-arrow"></i>
                </div>
            </div>
            <div class="ld-invoice-detail-info-bottom">
                <div>
                    <span>税额</span>
                    <input type="text"  name="fdTotalTax" placeholder="请填写税额" style="position:relative;left:60px;"  value="${data.fdTotalTax}">%
                </div>
                <div>
                    <span>价税合计</span>
                    <input type="text"   name="fdJshj" placeholder="请填写价税合计"  value="${data.fdJshj}">
                </div>
                <div>
                    <span>购方名称</span>
                    <input type="text"   name="fdPurchaserName" placeholder="请填写购方名称"  value="${data.fdPurchaserName}">
                </div>
                <div>
                    <span>购方税号</span>
                    <input type="text"  name="fdPurchaserTaxNo" placeholder="请填写购方税号" value="${data.fdPurchaserTaxNo}">
                </div>
                <div>
                    <span>销方名称</span>
                    <input type="text"  name="fdSalesName" placeholder="请填写销方名称"  value="${data.fdSalesName}">
                </div>
            </div>
       </div>
    </div>
    <div class="ld-footer">
    	<div class="ld-footer-blueBg-edit" onclick="invoice();" >保存</div>
        <div class="ld-footer-whiteBg" style="margin-left:15px;" onclick="deleteInvoice(${data.fdId});">删除</div>
    </div>
   <input hidden="true" name="fdModelId" value="${fdModelId }" />
   <input hidden="true" name="fdModelName" value="${fdModelName }" />
   <input hidden="true" name="formMethod" value="${formMethod }" />
</body>
<script type="text/javascript">

       $(function(){
	        getTime();
     })

	 function invoice(){
		 var fdInvoiceType = $("[name='fdInvoiceType']").val();
		 if(fdInvoiceType==""){
			 alert("发票类型不能为空");
			 return false;
		 }else  if ($("[name='fdInvoiceCode']").val()=="") {
			 alert("发票代码不能为空");
			 return false;
		 }else if ($("[name='fdInvoiceNumber']").val()==""){
			 alert("发票号码不能为空");
			 return false;
		 }else if ($("[name='fdInvoiceDate']").val()==""){
			 alert("发票时间不能为空");
			 return false;
		 }else if ($("[name='fdTotalTax']").val()==""){
			 alert("税额不能为空");
			 return false;
		 }else if ($("[name='fdJshj']").val()==""){
			 alert("税价合计不能为空");
			 return false;
		 }
		var  invoice= {
			 'InvoiceCode':$("[name='fdInvoiceCode']").val(),
			 'InvoiceNumber':$("[name='fdInvoiceNumber']").val(),
			 'CheckCode':$("[name='fdCheckCode']").val(),
			 'BillingDate':$("[name='fdInvoiceDate']").val(),
			 'TotalTax':$("[name='fdTotalTax']").val(),
			 'TotalAmount':$("[name='fdJshj']").val(),
			 'PurchaserName':$("[name='fdPurchaserName']").val(),
			 'PurchaserTaxNo':$("[name='fdPurchaserTaxNo']").val(),
			 'SalesName':$("[name='fdSalesName']").val(),
			 'fdModelId':$("[name='fdModelId']").val(),
			 'fdModelName':$("[name='fdModelName']").val(),
			 'fdInvoiceTypeId':$("[name='fdInvoiceType']").val()
		};
		 var fdModelId = $("[name='fdModelId']").val();
		 var formMethod =$("[name='formMethod']").val();
		 var params = JSON.stringify(invoice);
		 $.ajax( {
	         url:Com_Parameter.ContextPath+"fssc/mobile/fssc_mobile_invoice/fsscMobileInvoice.do?method=update",
	         type: 'POST', 
	         dataType:"json",
	         data:{params:params,fdModelId:fdModelId},
	         async:false,    //用同步方式   async. 默认是true，即为异步方式
	         success:function(data){
	        	 var rtn = data;
	        	 if(data.result=='success'){
	        		 alert("保存成功！");
	        		// javascript:history.back();
	        		 window.location.href = Com_Parameter.ContextPath+"/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method="+formMethod+"&fdId="+fdModelId;
	        	 } else {
	        		 alert("保存失败！");
	        		 window.location.href = Com_Parameter.ContextPath+"/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method="+formMethod+"&fdId="+fdModelId;
	        	 } 
	         }
		 })
	}
	 
	 function deleteInvoice(fdId){
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
	 
	 
	  
	    function getTime(id,name,index){
	      	var myDate = new Date(); 
	      	var year=myDate.getFullYear()+"";
	      	var month=parseInt(myDate.getMonth()+1)+"";
	      	var day=myDate.getDate()+"";
	      	if(month.length==1){
	      		month="0"+month;
	      	}
	      	if(day.length==1){
	      		day="0"+day;
	      	}
	      	$("[name='fdInvoiceDate']").val(year+"-"+month+"-"+day);
	    }

	//发票时间
	new Mdate("fdInvoiceDate", {
	   acceptId: "fdInvoiceDate",
	   acceptName: "fdInvoiceDate",
	   beginYear: "2019",
	   beginMonth: "10",
	   beginDay: "24",
	   endYear: "2030",
	   endMonth: "1",
	   endDay: "1",
	   format: "-"
	});
	
	
	 //获取发票类型
	function selectInvoiceTypeItem(){
		 var fdInvoiceItemData =
			 [{"value":"10100","text":"增值税专用发票"},
	          {"value":"10101","text":"增值税普通发票"},
	          {"value":"10102","text":"增值税电子普通发票"},
	          {"value":"10103","text":"增值税普通发票(卷票)"},
	          {"value":"10104","text":"机动车销售统一发票"},
	          {"value":"10105","text":"二手车销售统一发票"},
	          {"value":"10200","text":"定额发票"},
	          {"value":"10400","text":"机打发票"},
	          {"value":"10500","text":"出租车发票"},
	          {"value":"10503","text":"火车票"},
	          {"value":"10505","text":"航空运输电子客票行程单"},
	          {"value":"10507","text":"过路费发票"},
	          {"value":"10900","text":"可报销其他发票"},
	          {"value":"20100","text":"国际小票"},
	          {"value":"20105","text":"滴滴出行行程单"},
	          {"value":"00000","text":"其他"},
                 {"value":"30100","text":"增值税电子专用发票"},
                 {"value":"30101","text":"财政票据"}];
			 var fdInvoiceTypeName = document.getElementById('fdInvoiceTypeName');
	  	     var fdInvoiceTypeId = document.getElementById('fdInvoiceType');
			 var picker = new Picker({
		        data:[fdInvoiceItemData]
		     });
		     picker.on('picker.select', function (selectedVal, selectedIndex) {
		    	 fdInvoiceTypeName.value = fdInvoiceItemData[selectedIndex[0]].text;
		    	 fdInvoiceTypeId.value = fdInvoiceItemData[selectedIndex[0]].value;
		     });
		     picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
		    	 fdInvoiceTypeName.value = fdInvoiceItemData[selectedIndex[0]].text;
		    	 fdInvoiceTypeId.value = fdInvoiceItemData[selectedIndex[0]].value;
		      });
	    	  picker.show();
	 }

</script>
<%@ include file="/resource/jsp/edit_down.jsp" %>
