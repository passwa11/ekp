
 	$(document).ready(function(){
 		$('.txtstrong').remove();
 		$('.inputsgl').removeClass();
 		$(".ld-remember-attact-info img").each(function(){
 			//this.src = getSrcByName($(this).data("file"));
 		})
 	});
 	
 	var costList = new Swiper('.swiper-container', {
        slidesPerView: 1,
        slidesPerColumn: 3,
        slidesPerColumnFill: "row",
        pagination: '.swiper-pagination',
    })
 	
 	 // 禁用body滚动
    function forbiddenScroll() {
        $('.ld-newApplicationForm').addClass('ld-newApplicationForm-hidden')
    }
    // 启用滚动
    function ableScroll() {
        $('.ld-newApplicationForm').removeClass('ld-newApplicationForm-hidden')
    }

    //---------------------------新增行程明细start---------------------------//
    //保存行程信息
    $('.ld-addTrip-btn-single').click(function() {
    	  $('.ld-addTravel-body').removeClass('ld-addTravel-body-show')
          ableScroll()
    })
    
    //编辑行程
    $(document).on('mousedown','#fdTravelListId>li', function (e) {
    	var  travelId =  $(this).find('.ld-newApplicationForm-trip-top input').val();
         $("[name='travelId']").val($("[name='fdTravelList_Form["+travelId+"].fdSubject']").val());
		 $("[name='fdPersonList']").val($("[name='fdTravelList_Form["+travelId+"].fdPersonListNames']").val());
	     $("[name='fdPersonListId']").val($("[name='fdTravelList_Form["+travelId+"].fdPersonListIds']").val());
	     $("[name='days']").val( $("[name='fdTravelList_Form["+travelId+"].fdTravelDays']").val()+"天");
	     $("[name='startCity']").val($("[name='fdTravelList_Form["+travelId+"].fdStartPlace']").val());
	     $("[name='endCity']").val( $("[name='fdTravelList_Form["+travelId+"].fdArrivalPlace']").val());
	     $("[name='endCityId']").val( $("[name='fdTravelList_Form["+travelId+"].fdArrivalId']").val());
	     $("[name='trafficToolsId']").val($("[name='fdTravelList_Form["+travelId+"].fdBerthId']").val());
	     var showName = $("[name='fdTravelList_Form["+travelId+"].fdBerthName']").val();
	     var v = $("[name='fdTravelList_Form["+travelId+"].fdVehicleName']").val();
	     if(v){
	    	 showName+="("+v+")";
	     }
	     $("[name='trafficTools']").val(showName);
	     $("[name='fdVehicleId']").val($("[name='fdTravelList_Form["+travelId+"].fdVehicleId']").val());
	     $("[name='fdBerthId']").val($("[name='fdTravelList_Form["+travelId+"].fdBerthId']").val());
	     $("[name='fdBerthName']").val($("[name='fdTravelList_Form["+travelId+"].fdBerthName']").val());
	     $("#fdBeginDate").val( $("[name='fdTravelList_Form["+travelId+"].fdBeginDate']").val());
	     $("#fdEndDate").val($("[name='fdTravelList_Form["+travelId+"].fdEndDate']").val());
	     $('.ld-addTravel-body').addClass('ld-addTravel-body-show')
	})
	  
   
    var mySwiper = new Swiper('.swiper-container',{
    	onlyExternal: true,
    	speed: 500,
	})
	
	
	
    //------------------------------费用明细start-------------------------------//
    //返回费用明细
    $('.ld-entertain-detail-btn-single').click(function() {
         $('.ld-entertain-main-body').removeClass('ld-entertain-main-body-show')
         ableScroll()
     })
     
     //查看费用
      $(document).on('mousedown','#fdDetailListId>li>div.ld-notSubmit-list-bottom', function (e) {
    	    var index  =  $(this).parent().find('.ld-newApplicationForm-travelInfo-top input').val();
    	    $("#fdTravel").val($("[name='fdDetailList_Form["+index+"].fdTravel']").val());
    	    $("#fdRealUser").val($("[name='fdDetailList_Form["+index+"].fdRealUserName']").val());
    	    $("#fdRealUserId").val($("[name='fdDetailList_Form["+index+"].fdRealUserId']").val());
    	    $("#fdDept").val($("[name='fdDetailList_Form["+index+"].fdDeptName']").val());
    	    $("#fdDeptId").val($("[name='fdDetailList_Form["+index+"].fdDeptId']").val());
    	    $("#fdExpenseItem").val($("[name='fdDetailList_Form["+index+"].fdExpenseItemName']").val());
    	    $("#fdExpenseItemId").val($("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val());
    	    $("#fdCostCenterDetail").val($("[name='fdDetailList_Form["+index+"].fdCostCenterName']").val());
    	    $("#fdCostCenterDetailId").val($("[name='fdDetailList_Form["+index+"].fdCostCenterId']").val());
    	    $("#fdWbs").val($("[name='fdDetailList_Form["+index+"].fdWbsName']").val());
    	    $("#fdWbsId").val($("[name='fdDetailList_Form["+index+"].fdWbsId']").val());
    	    $("#fdInnerOrder").val($("[name='fdDetailList_Form["+index+"].fdInnerOrderName']").val());
    	    $("#fdInnerOrderId").val($("[name='fdDetailList_Form["+index+"].fdInnerOrderId']").val());
    	    $("#fdHappenDate").val($("[name='fdDetailList_Form["+index+"].fdHappenDate']").val());
    	    $("#fdEndDate").val($("[name='fdDetailList_Form["+index+"].fdEndDate']").val());
    	    $("#fdStartDate").val($("[name='fdDetailList_Form["+index+"].fdStartDate']").val());
    	    $("#fdTravelDays").val($("[name='fdDetailList_Form["+index+"].fdTravelDays']").val()+"天");
    	    $("#fdCurrency").val($("[name='fdDetailList_Form["+index+"].fdCurrencyName']").val());
    	    $("#fdCurrencyId").val($("[name='fdDetailList_Form["+index+"].fdCurrencyId']").val());
    	    $("#fdApplyMoney").val($("[name='fdDetailList_Form["+index+"].fdApplyMoney']").val());
    	    $("#fdUse").val($("[name='fdDetailList_Form["+index+"].fdUse']").val());
    	    $("#fdPersonNumber").val($("[name='fdDetailList_Form["+index+"].fdPersonNumber']").val());
    	    $("#fdStandardMoney").val($("[name='fdDetailList_Form["+index+"].fdStandardMoney']").val());
    	    $("#startCityId").val($("[name='fdDetailList_Form["+index+"].fdStartPlaceId']").val());
    	    $("#startCity").val($("[name='fdDetailList_Form["+index+"].fdStartPlace']").val());
    	    $("#endCityId").val($("[name='fdDetailList_Form["+index+"].fdArrivalPlaceId']").val());
    	    $("#endCity").val($("[name='fdDetailList_Form["+index+"].fdArrivalPlace']").val());
    	    var showName = $("[name='fdDetailList_Form["+index+"].fdBerthName']").val();
	   	    var v = $("[name='fdDetailList_Form["+index+"].fdVehicleName']").val();
	   	    if(v){
	   	    	showName+="("+v+")";
	   	    }
    	    $("#trafficTools").val(showName);
    	    $("#trafficToolId").val($("[name='fdDetailList_Form["+index+"].fdBerthId']").val());
    	    $("#fdInputTaxMoney").val($("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val());
    	    $("input[name='fdInputTaxRate']").val($("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val());
    	    $("#fdNonDeductMoney").val($("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val());
    	    var fdIsDeduct =$("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val();
    	    if(fdIsDeduct) {
				$("[id=fdIsDeduct]").parent().find(".checkbox_item").removeClass("checked");
				$("[name=_fdIsDeduct][value=" + fdIsDeduct + "]").parent().addClass("checked");
				if (!fdIsDeduct) {
					$(".fdIsDeduct").hide();
				} else {
					$(".fdIsDeduct").show();
				}
			}
    	    $("#fdNoTaxMoneyExpense").val($("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val());
		    $("#fdDetailProjectId").val($("[name='fdDetailList_Form["+index+"].fdProjectId']").val());
		    $("#fdDetailProjectName").val($("[name='fdDetailList_Form["+index+"].fdProjectName']").val());
    	    $('.ld-entertain-main-body').addClass('ld-entertain-main-body-show')
      });
    
    //------------------------------收款账户明细start------------------------------//
    //返回收款账户信息
    $('.ld-addAccount-btn-single').click(function() {
         $('.ld-addAccount-body').removeClass('ld-addAccount-body-show')
         ableScroll()
     })
     
     //编辑收款账户
      $(document).on('mousedown','#fdAccountsListId>li', function (e) {
    	  var accounId = $(this).find('.ld-newApplicationForm-account-top input').val();
    	  $("#fdBankId").val( $("[name='fdAccountsList_Form["+accounId+"].fdBankId']").val());
    	  $("#fdPayWayId").val($("[name='fdAccountsList_Form["+accounId+"].fdPayWayId']").val());
    	  $("#fdPayWayName").val($("[name='fdAccountsList_Form["+accounId+"].fdPayWayName']").val());
    	  $("#fdCurrencyIdAccount").val( $("[name='fdAccountsList_Form["+accounId+"].fdCurrencyId']").val());
    	  $("#fdCurrencyAccount").val( $("[name='fdAccountsList_Form["+accounId+"].fdCurrencyName']").val());
    	  $("#fdExchangeRateAccount").val($("[name='fdAccountsList_Form["+accounId+"].fdExchangeRate']").val());
    	  $("#fdAccountId").val($("[name='fdAccountsList_Form["+accounId+"].fdAccountId']").val());
    	  $("#fdAccountName").val( $("[name='fdAccountsList_Form["+accounId+"].fdAccountName']").val());
    	  $("#fdBankAccount").val($("[name='fdAccountsList_Form["+accounId+"].fdBankAccount']").val());
    	  $("#fdBankName").val($("[name='fdAccountsList_Form["+accounId+"].fdBankName']").val());
    	  $("#fdAccountAreaName").val($("[name='fdAccountsList_Form["+accounId+"].fdAccountAreaName']").val());  
    	  $("#fdMoney").val($("[name='fdAccountsList_Form["+accounId+"].fdMoney']").val());
    	  $('.ld-addAccount-body').addClass('ld-addAccount-body-show')
      });
     //---------------------------收款账户明细end------------------------//
    
     
    //-----------------------------发票明细start-------------------------------//
	
    //返回发票
    $('.ld-save-btn').click(function() {
        $('.ld-travel-detail-body').removeClass('ld-travel-detail-body-show')
        ableScroll()
     })
     
     //查看发票
     $(document).on('mousedown','#fdInvoiceListId>li', function (e) {
    	 var index = $(this).find('.ld-newApplicationForm-invioce-top input').val();
    	 var fdInvoiceTypeName = $("[name='fdInvoiceList_Form["+index+"].fdInvoiceType").find("option:selected").text();
    	 var fdInvoiceTypeId = $("[name='fdInvoiceList_Form["+index+"].fdInvoiceType").val();
    	 $("#fdInvoiceType").val(fdInvoiceTypeName);
     	 $("#fdInvoiceTypeId").val(fdInvoiceTypeId);
     	 $("#fdExpenseType").val($("[name='fdInvoiceList_Form["+index+"].fdExpenseTypeName']").val());
     	 $("#fdTaxValue").val($("[name='fdInvoiceList_Form["+index+"].fdTax']").val());
     	 var inputArr=["fdExpenseTypeId","fdTravel","fdInvoiceNumber","fdTravel","fdInvoiceNumber","fdInvoiceCode",
     		"fdCheckCode","fdInvoiceDate","fdInvoiceMoney","fdTaxMoney","fdNoTaxMoney","fdCheckStatus","fdState","fdPurchName","fdTaxNumber"];
     	 for(var n=0;n<inputArr.length;n++){
     		$("#"+inputArr[n]).val($("[name='fdInvoiceList_Form["+index+"]."+inputArr[n]+"']").val());
     	 }
     	 $('.ld-travel-detail-body').addClass('ld-travel-detail-body-show')
      })
     
	//--------------------------------------冲抵借款明细start--------------------------------//

	 //查看冲抵借款金额
    function  editLoanOffset (index) {
		loanIndex = index;
    	$("#docSubject").val($("[name='fdOffsetList_Form["+index+"].docSubject']").val());
    	$("#fdNumber").val($("[name='fdOffsetList_Form["+index+"].fdNumber']").val());
    	$("#fdLoanMoney").val($("[name='fdOffsetList_Form["+index+"].fdLoanMoney']").val());
    	$("#fdCanOffsetMoney").val($("[name='fdOffsetList_Form["+index+"].fdCanOffsetMoney']").val());
    	$("#fdLeftMoney").val($("[name='fdOffsetList_Form["+index+"].fdLeftMoney']").val());
    	$("#fdOffsetMoney").val($("[name='fdOffsetList_Form["+index+"].fdOffsetMoney']").val());
    	$("#fdCanOffsetMoney").val($("[name='fdOffsetList_Form["+index+"].fdCanOffsetMoney']").val());
    	$('.ld-addLoan-body').addClass('ld-addLoan-body-show')
    }
    
    //返回
    $('.ld-addLoan-btn-single').click(function() {
		 $('.ld-addLoan-body').removeClass('ld-addLoan-body-show');
	 });
	 
    //-----------------------------附件----------------------------------//
	 //显示附件
	 $('.ld-newApplicationForm-attach-btn').click(function() {
		 
	 })
	 
   /** 
    * 新增一列
    * @param optTB
    * @param content
    * @param fieldValues
    * @returns
    */
   function DocList_AddRows(optTB, content, fieldValues){
	if(optTB==null)
		optTB = DocListFunc_GetParentByTagName("TABLE");
	else if(typeof(optTB)=="string")
		optTB = document.getElementById(optTB);
	if(content==null)
		content = new Array;
	var tbInfo = DocList_TableInfo[optTB.id];
	var index = tbInfo.lastIndex - tbInfo.firstIndex;
	var htmlCode, newCell;
	var newRow = optTB.insertRow(tbInfo.lastIndex);
	tbInfo.lastIndex++;
	newRow.className = tbInfo.className;
	for(var i=0; i<tbInfo.cells.length; i++){
		newCell = newRow.insertCell(-1);
		newCell.className = tbInfo.cells[i].className;
		newCell.align = tbInfo.cells[i].align ? tbInfo.cells[i].align : '';
		newCell.vAlign = tbInfo.cells[i].vAlign ? tbInfo.cells[i].vAlign : '';
		newCell.style.display ='none';
		if(tbInfo.cells[i].isIndex){
			htmlCode = content[i]==null?tbInfo.cells[i].innerHTML:content[i];
			if(htmlCode==null || htmlCode==""){
				htmlCode = ''+(index+1);
			}else{
				htmlCode = DocListFunc_ReplaceIndex(htmlCode, index + 1);
				htmlCode =  htmlCode.replace("{1}", index + 1);//自定义表单中明细表处理
			}
		}else
			htmlCode = DocListFunc_ReplaceIndex(content[i]==null?(DocList_formatHtml(tbInfo.cells[i])):content[i], index);
		newCell.innerHTML = htmlCode;
		}
    }
	 
	 //删除行
	 function DocList_DeleteRow1(optTR,rowIndex){
			var optTB = document.getElementById(optTR);
			var tbInfo = DocList_TableInfo[optTB.id];
			var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
			var rowIndex =rowIndex;
			var index = DocList_GetRowIndex(tbInfo,optTR);
			optTB.deleteRow(rowIndex);
			tbInfo.lastIndex--;
		}
	 
	 window.viewInvoiceInfo = function(e){
		 $(".ld-temp-body-content-invoice-attachment").html('');
		 $(".ld-temp-body-content_invoice>div:gt(0)").remove();
		 var e = e||window.event;
		 var ele = e.srcElement||e.target;
		 e.stopPropagation?e.stopPropagation():(e.cancelBubble = true);
		 ele = DocListFunc_GetParentByTagName("LI",ele);
		 var index = $(ele).find("input[name=detailId]").val();
		 var fdExpenseTempId = $("[name='fdDetailList_Form["+index+"].fdExpenseTempId']").val();
		 if(fdExpenseTempId){
			 var data = new KMSSData();
			 data.AddBeanData("fsscExpenseMobileService&authCurrent=true&type=getTmpInfo&fdExpenseTempId="+fdExpenseTempId);
			 data = data.GetHashMapArray();
			 if(data&&data.length>0){
				 var atts = JSON.parse(data[0].attachments?data[0].attachments:'[]'),invoices=JSON.parse(data[0].invoices?data[0].invoices:'[]');
				 var att = [],inv=[];
				 $(".ld-temp-body-content-invoice-attachment").html('');
				 for(var i=0;i<atts.length;i++){
					 var type = atts[i].fdName.split('\.');
					 att.push('<div class="ld-temp-body-content-invoice-attachment-row">');
					 att.push('<div class="ld-temp-body-content-invoice-attachment-row-icon attachment-icon-');
					 att.push(type[type.length-1].toLowerCase());
					 att.push('"></div><div onclick="showAtt(\''+atts[i].fdId+'\',\''+atts[i].fdName+'\');" class="lld-temp-body-content-invoice-attachment-row-name">');
					 att.push(atts[i].fdName);
					 att.push('</div></div>');
				 }
				 $(".ld-temp-body-content-invoice-attachment").html(att.join(''));
				 $(".ld-temp-body-content_invoice>div:gt(0)").remove();
				 for(var i=0;i<invoices.length;i++){
					 inv.push('<div class="ld-temp-body-content_invoice_info">');
					 inv.push('<div class="ld-temp-body-content_invoice_info_item">');
					 inv.push(invoices[i].fdType||'&nbsp;');
					 inv.push('</div><div class="ld-temp-body-content_invoice_info_item">');
					 inv.push(invoices[i].fdExpenseItemName||'&nbsp;');
					 inv.push('</div><div class="ld-temp-body-content_invoice_info_item">');
					 inv.push(invoices[i].fdInvoiceNumber||'&nbsp;');
					 inv.push('</div><div class="ld-temp-body-content_invoice_info_item">');
					 inv.push(invoices[i].fdInvoiceCode||'&nbsp;');
					 inv.push('</div><div class="ld-temp-body-content_invoice_info_item">');
					 inv.push(invoices[i].fdInvoiceMoney||'&nbsp;');
					 inv.push('</div></div>');
				 }
				 $(".ld-temp-body-content_invoice>div:eq(0)").after(inv.join(''));
			 }
		 }
		 $(".ld-footer-temp").css("display","flex");
		 $('.ld-temp-body').addClass('ld_temp_body_show');
		 forbiddenScroll()
	 }
	 
	 function cancelTemp(){
		 $(".ld-footer-temp").css("display","none");
		 $('.ld-temp-body').removeClass('ld_temp_body_show');
		 ableScroll()
	 }
	 
	 //查看交单退单数据
	function openPresData() {
		var data = $("[name=data]").val();
		data = JSON.parse(data.replaceAll("'",'"'));
		var html = "<div><table>" +
			"<tr align='center' class='tr_normal_title'>"+
			"<td align='center' style='width:20%;'>类型</td>"+
			"<td align='center' style='width:80%;'>描述</td>"+
			"</tr>"+
			"<tr class='ld-line20px'></tr>";
		for(var i=0;i<data.length;i++){
			var fdType = "";
			if(data[i].fdType==1){
				fdType="交单";
			}else if(data[i].fdType==2){
				fdType="退单";
			}
			html += "<tr KMSS_IsContentRow='1'>";
			html += "<td align='center' style='width:20%;'>"+fdType+"</td>"+
				"<td align='center' style='width:80%;'>"+data[i].fdDesc+"</td>";
			html += "</tr><tr class='ld-line20px'></tr>";
		}
		html += "</table></div>";
		jqalert({
			title:'交单退单',
			content:html,
			yestext:'返回'
		})
	}
	
    
    
