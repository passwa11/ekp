 DocList_Info.push('TABLE_DocList_fdTravelList_Form');
 DocList_Info.push('TABLE_DocList_fdDetailList_Form');
 DocList_Info.push('TABLE_DocList_fdInvoiceList_Form');
 DocList_Info.push('TABLE_DocList_fdAccountsList_Form');
 DocList_Info.push('TABLE_DocList_fdOffsetList_Form');
 DocList_Info.push('TABLE_DocList_fdTranDataList_Form');
 	$(document).ready(function(){
 		getDefaultPlaceInit();//获取当前定位城市（百度地图api）
 		$(".ld-footer-travel,.ld-footer-expense,.ld-footer-invoice,.ld-footer-account,.ld-footer-loan").hide();
 		$('.txtstrong').remove();
 		$('.inputsgl').removeClass();
 		$(".ld-remember-attact-info img").each(function(){
 			this.src = getSrcByName($(this).data("file"));
 		})
 		 var timer = setTimeout(function(){
 			FSSC_InitCurrencyAndRate(null,"account");//收款账户初始化汇率
 			var method=$("[name='method_GET']").val();
 			if(method=='add'){//未报费用转报销的
 				var len=$("#TABLE_DocList_fdDetailList_Form").find("tr").length;
 				for(var i=0;i<len;i++){
 					 //预算的匹配
 			    	 FSSC_MatchBudget(null,i);
 			    	 //标准匹配
 			    	 FSSC_MathStandard(i);
 				}
 			}
         },1000)
//         if(kk.isKK()){
//        	 	kk.app.on('back',function(){
//        	 		history.go(-1);
//        	 		return false;
//        	 	})
//         }else{
//        	 	dd.biz.navigation.setLeft({
//                 control:true,
//                 text:'',
//                 onSuccess:function(){
//                	 	history.go(-1);
//                 },
//                 onFail:function(){
//                 
//                 }
//             })
//         }
 	});
 	var costList = new Swiper('.swiper-container', {
        slidesPerView: 1,
        slidesPerColumn: 3,
        slidesPerColumnFill: "row",
        pagination: '.swiper-pagination',
    })
 	
 	 // 禁用body滚动
    function forbiddenScroll() {
        document.body.style.overflow='hidden'
    }
    // 启用滚动
    function ableScroll() {
    		document.body.style.overflow='auto'
    }

    
/***********************************************************************
 * 选择人员
 **********************************************************************/
  
    //重新选择人员、数据重新初始化
    function changeFdClaimant(person){
    	var fdPersonId='';
    	if(formInitData['fdIsAuthorize']=='true'){//启用授权
    		fdPersonId=person.value;
    	}else{
    		fdPersonId=person;
    	}
    	if(fdPersonId){
    		$("[name=fdCompanyId]").val("");
    		$("[name=fdCompanyName]").val("");
        	var data = new KMSSData();
    		data.AddBeanData("fsscExpenseDataService&type=getDefaultCompany&fdPersonId="+fdPersonId);
    		data = data.GetHashMapArray();
    		if(data.length>0){
    			$("[name=fdCompanyId]").val(data[0].fdId);
    			$("[name=fdCompanyName]").val(data[0].fdName);
    		}
    		clearDetailWhenCompanyChanged();
    		FSSC_LoadLoanInfo();
    		FSSC_LoadAccountInfo($("input[name='fdClaimantId']").val());
			//清空交易数据明细
			$("#TABLE_DocList_fdTranDataList_Form > tbody > tr").each(function(i){
				DocList_DeleteRow1("TABLE_DocList_fdTranDataList_Form",i);
			});
			$("#fdTranDataListId li").remove();
    	}
    }
    
    function FSSC_LoadAccountInfo(fdPersonId){
    	var len = $("#fdAccountsListId>li").length;
    	for(var i=0;i<len;i++){
    		DocList_DeleteRow($("#TABLE_DocList_fdAccountsList_Form>tbody>tr").get(0));
    	}
    	$("#fdAccountsListId>li").remove();
    	$.post(
    			Com_Parameter.ContextPath+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getAccountInfo',
    			{type:'default',fdPersonId:fdPersonId},
    			function(data){
    				data = JSON.parse(data);
    				data = data.data;
    				if(data&&data.length>0){
    					var fdMoney = 0;
    					$("#TABLE_DocList_fdDetailList_Form [name$=fdStandardMoney]").each(function(){
    						fdMoney = numAdd(fdMoney,this.value);
    					})
    					DocList_AddRow("TABLE_DocList_fdAccountsList_Form");
    		    		var src =Com_Parameter.ContextPath+ "fssc/mobile/resource/images/collectionAccount.png";
    		     		var html1 = $("<div><img src="+src+" alt=''><span  class='accountName' >账户1</span>" +
    		     				"<input name='accountId' value=\"0\" hidden='true'></div><span onclick=\"deleteAccount()\"></span>") ;
    		     		var html2 = $("<div><p  class='fdBankAccount'>"+data[0].fdPayeeAccount+"</p></div>" +
    		     					"<div><span>收款金额：</span><div><span class='fdMoney'>"+fdMoney.toFixed(2)+"</span><span></span></div></div>");
    		     		var div1 = $("<div class=\"ld-newApplicationForm-account-top\"></div>").append(html1);
    		     		var div2 = $("<div class=\"ld-newApplicationForm-account-bottom\"></div>").append(html2);
    		     		var li = $("<li onclick='editAccountDetail()'></li>").append(div1,div2);
    		     		$("#fdAccountsListId").append(li);//拼接收款账户
	    		       	//$("[name='fdAccountsList_Form[0].fdCurrencyName']").val(fdCurrency);
	    		       	$("[name='fdAccountsList_Form[0].fdAccountId']").val(data[0].value);
	    		       	$("[name='fdAccountsList_Form[0].fdAccountName']").val(data[0].fdName);
	    		       	$("[name='fdAccountsList_Form[0].fdBankAccount']").val(data[0].fdPayeeAccount);
	    		       	$("[name='fdAccountsList_Form[0].fdBankName']").val(data[0].accountName);
	    		    	$("[name='fdAccountsList_Form[0].fdAccountAreaName']").val(data[0].fdAccountAreaName);
	    		       	$("[name='fdAccountsList_Form[0].fdMoney']").val(fdMoney);
	    		       	var fdCompanyId = $("[name=fdCompanyId]").val();
	    		       	if(fdCompanyId){
	    		       		data = new KMSSData();
	    		       		data.AddBeanData("eopBasedataCompanyService&fdCompanyId="+fdCompanyId+"&type=getStandardCurrencyInfo");
	    		       		data = data.GetHashMapArray();
	    		       		if(data&&data.length>0){
	    		       			$("[name='fdAccountsList_Form[0].fdCurrencyId']").val(data[0].fdCurrencyId);
	    		       			$("[name='fdAccountsList_Form[0].fdCurrencyName']").val(data[0].fdCurrencyName);
	    		       			$("[name='fdAccountsList_Form[0].fdExchangeRate']").val(data[0].fdExchangeRate);
	    		       		}
	    		       	}
    				}
    			}
    	)
    }
    
    //清空明细
    function clearDetailWhenCompanyChanged(){
    	// 清空关联事前
    	$("input[name='fdFeeNames']").val("");
    	$("input[name='fdFeeIds']").val("");
		var fdCompanyId = $("[name=fdCompanyId]").val(),fdCompanyName = $("[name=fdCompanyName]").val(),fdCompanyIdOld = $("[name=fdCompanyIdOld]").val();
		if(fdCompanyId!=fdCompanyIdOld){
			 jqalert({
	             title:'提示',
	             content:'您正在切换费用归属公司，切换后将会清空已经填写的公司相关信息，确认切换吗？',
	             yestext:'确定',
	             notext:'取消',
	             yesfn:function () {
	            	$("[name=fdCompanyIdOld]").val(fdCompanyId);
					$("[name=fdCompanyNameOld]").val(fdCompanyName);
					$("[name=fdCostCenterId]").val('');
					$("[name=fdCostCenterName]").val('');
					FSSC_ReloadCostCenter();
	            	//重新带出默认币种
					var fdCurrencyId = '',fdCurrencyName='',fdRate = '',fdBudgetRate='';
					data = new KMSSData();
					data.AddBeanData("eopBasedataCompanyService&type=getStandardCurrencyInfo&authCurrency=true&fdCompanyId="+fdCompanyId);
					data = data.GetHashMapArray();
					if(data.length>0){
						fdCurrencyId=data[0].fdCurrencyId;
						fdCurrencyName = data[0].fdCurrencyName;
						fdRate = data[0].fdExchangeRate;
						fdBudgetRate=data[0].fdBudgetRate;
					}
					//行程明细
					$("#TABLE_DocList_fdTravelList_Form > tbody > tr").each(function(i){
						DocList_DeleteRow1("TABLE_DocList_fdTravelList_Form",i);
					});
					$("#fdTravelListId li").remove();
					
					//清空费用明细
					$("#TABLE_DocList_fdDetailList_Form > tbody > tr ").each(function(i){
						DocList_DeleteRow1("TABLE_DocList_fdDetailList_Form",i);
					});
					$("#fdDetailListId li").remove();
					$("#fdExpenseItemName").val("");
					$("#fdExpenseItemId").val("");
					
					//清空发票明细
					$("#TABLE_DocList_fdInvoiceList_Form > tbody > tr").each(function(i){
						DocList_DeleteRow1("TABLE_DocList_fdInvoiceList_Form",i);
					});
					$("#fdInvoiceListId li").remove();
					
					//清空收款明细
					$("#TABLE_DocList_fdAccountsList_Form > tbody > tr").each(function(i){
						$(this).find("input[name*=fdPayWay]").val("");
						$(this).find("input[name*=fdPayBank]").val("");
						$(this).find("[name$=fdCurrencyId]").val(fdCurrencyId);
						$(this).find("[name$=fdCurrencyName]").val(fdCurrencyName);
						$(this).find("[name$=fdExchangeRate]").val(fdRate);
						$(this).find("input[name*=fdAccount]").val("");
						$(this).find("input[name*=fdBank]").val("");
						$("[name='fdAccountsList_span["+i+"].fdBankAccount']").text("");
					});
					$("[name=fdCompanyId]").val(fdCompanyId)
					$("[name=fdCompanyName]").val(fdCompanyName)
					FSSC_LoadLoanInfo();
					FSSC_LoadAccountInfo($("input[name='fdClaimantId']").val());
	             }
         	})
		}else{
			$("[name=fdCompanyId]").val($("[name=fdCompanyIdOld]").val())
			$("[name=fdCompanyName]").val($("[name=fdCompanyNameOld]").val())
		}
		window.FSSC_ReloadCostCenter();
    }
    
    //重新加载成本中心
    function FSSC_ReloadCostCenter (){
		var fdCompanyId = $("[name=fdCompanyId]").val();
		//重新带出成本中心
		var data = new KMSSData();
		data.AddBeanData("fsscExpenseDataService&type=getDefaultCostCenter&fdCompanyId="+fdCompanyId+"&fdPersonId="+$("[name=fdClaimantId]").val());
		data = data.GetHashMapArray();
		if(data.length>0){
			$("[name=fdCostCenterId]").val(data[0].fdId);
			$("[name=fdCostCenterName]").val(data[0].fdName);
		}else{
			$("[name=fdCostCenterId]").val("");
			$("[name=fdCostCenterName]").val("");
		}
		
	}
    
   //选择公司
    function selectFdCompany(){
    	var fdPersonId = $("[name='fdClaimantId']").val();
    	if(fdPersonId==''){
    		 jqalert({
                 title:'提示',
                 content:'请选择申请人'
             })
             return;
    	}
	     $.ajax({
             type: 'post',
             url:Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getFsCompany',
             data: {"fdPersonId":fdPersonId},
         }).success(function (data) {
	      	    var rtn = JSON.parse(data);
	      	    if(rtn.result=='success'){
		      	     var fdCompanyData = rtn.data;
		      	     var fdCompanyId = document.getElementById("fdCompanyId");
		      	     var fdCompanyName = document.getElementById("fdCompanyName");
			      	 var picker = new Picker({
				        data:[fdCompanyData]
				     });
				     picker.on('picker.select', function (selectedVal, selectedIndex) {
				    	 fdCompanyName.value = fdCompanyData[selectedIndex[0]].text;
				    	 fdCompanyId.value = fdCompanyData[selectedIndex[0]].value;
				     });
				     picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
				    	 fdCompanyName.value = fdCompanyData[selectedIndex[0]].text;
				    	 fdCompanyId.value = fdCompanyData[selectedIndex[0]].value;
				      });
			    	  picker.show();
	      	 } else {
	      		jqtoast("获取公司失败");
	      	 }
         });
    }

    /**********************************************************************
     * 行程明细
     * **********************************************************************/
    var travelId = 1;//行程主题index
    function addTravelDetail() {
	    	var index = $("#fdTravelListId>li").length;
	    	$("[name='row-travel-index']").val(index);
		$("input[name='startCity']").val(currentCity);
		$("input[name='endCity']").val(currentCity);
		$("input[name='trafficTools']").val("");
		$("input[name='fdPersonList']").val("");
		$("input[name='fdPersonListId']").val("");
		$("#days0").val("1天")
        selectTime('fdBeginDate','fdBeginDate','0');
	    selectTime('fdEndDate','fdEndDate','0');
    		$('.ld-addTravel-body').addClass('ld-addTravel-body-show')
    		$(".ld-footer-travel").css("display","flex");
        $("[name='fdSubject']").val("行程"+(index+1));
        forbiddenScroll()
    }
    
    //保存行程信息
    function saveTravelDetail() {
    	var index = $("[name='row-travel-index']").val();
    	var fdtravelId =$("[name='fdSubject']").val();
    	var fdPersonListNames =$("[name='fdPersonList']").val();
    	var fdPersonListId =$("[name='fdPersonListId']").val();
    	if(fdPersonListNames==""){
    		jqtoast("请选择人员")
    		return false;
    	}
    	var fdBeginDate =$("#fdBeginDate0").val();
    	var fdEndDate =$("#fdEndDate0").val();
    	var days =$("[name='days']").val().replace("天","");;
    	var startCity =$("[name='startCity']").val();
    	var endCity =$("[name='endCity']").val();
    	var endCityId = $("[name='endCityId']").val();
    	var trafficTools =$("[name='trafficTools']").val();
    	var trafficToolId = $("[name='trafficToolsId']").val();
    	var fdVehicleId = $("[name='fdVehicleId']").val();
    	var fdBerthId = $("[name='fdBerthId']").val();
    	var fdBerthName = $("[name='fdBerthName']").val();
    	var fdVehicleName = $("[name='trafficTools']").val();
    	if(fdBeginDate==""){
    		jqtoast("请选择开始日期")
    		return false;
    	}
    	if(fdEndDate==""){
    		jqtoast("请选择结束日期")
    		return false;
    	}
    	if(startCity==""){
    		jqtoast("请选择出发城市")
    		return false;
    	}
    	if(endCity==""){
    		jqtoast("请选择到达城市")
    		return false;
    	}
		if($("#fdBerthName").length>0) {
			if (trafficTools == "") {
				jqtoast("请选择交通工具")
				return false;
			}
		}else{
			fdBerthName="";
		}
    	//新增
    	if(index==$("#fdTravelListId>li").length){
    		travelId++;
    		DocList_AddRows('TABLE_DocList_fdTravelList_Form');
        	var url = Com_Parameter.ContextPath+"fssc/mobile/resource/images/train.png";
        	var html1 = $("<div><img src="+url+" ><span  name='travelSubject' id=\"travelId"+index+"\">"+fdtravelId+"</span>" +
        			     "<input name='travelId' value="+index+" hidden='true'></div><span onclick=\"deleteTravelDetail()\"></span>") ;
        	var html2 = $("<div><span class='fdStartPlace' >"+startCity+"</span>" +
        			     "<span class='fdBerthName' >"+fdBerthName+"</span>" +
        			     "<span class='fdArrivalPlace' >"+endCity+"</span></div>" +
        			     "<div><span class='fdBeginDate' >"+fdBeginDate+"</span>" +
        			     "<span class='fdPersonListNames' >"+fdPersonListNames+"</span>" +
        			     "<span class='fdEndDate' >"+fdEndDate+"</span></div>");
        	var div1 = $("<div class=\"ld-newApplicationForm-trip-top\"></div>").append(html1);
        	var div2 = $("<div class=\"ld-newApplicationForm-trip-bottom\"></div>").append(html2);
        	var li = $("<li onclick=\"editTravelDetail()\"></li>").append(div1,div2);
    		$("#fdTravelListId").append(li);//拼接行程明细
    	} else{
    		var li = $("#fdTravelListId>li").eq(index);

    		li.find(".fdBerthName").html(fdBerthName);
    		li.find(".fdBerthId").html(fdBerthId);
    		li.find(".fdVehicleName").html(fdVehicleName);
    		li.find(".fdTravelDays").html(days+"天");
    		li.find(".fdBeginDate").html(fdBeginDate);
    		li.find(".fdEndDate").html(fdEndDate);
    		li.find(".fdStartPlace").html(startCity);
    		li.find(".fdArrivalId").html(endCityId);
    		li.find(".fdArrivalPlace").html(endCity);
    		li.find(".fdSubject").html(fdtravelId);
    		li.find(".fdPersonListNames").html(fdPersonListNames);
    		li.find(".fdPersonListIds").html(fdPersonListId);
    	}
    	$("[name='fdTravelList_Form["+index+"].fdTravelDays']").val(days);
		$("[name='fdTravelList_Form["+index+"].fdBeginDate']").val(fdBeginDate);
		$("[name='fdTravelList_Form["+index+"].fdEndDate']").val(fdEndDate);
		$("[name='fdTravelList_Form["+index+"].fdStartPlace']").val(startCity);
		$("[name='fdTravelList_Form["+index+"].fdArrivalId']").val(endCityId);
		$("[name='fdTravelList_Form["+index+"].fdArrivalPlace']").val(endCity);
		$("[name='fdTravelList_Form["+index+"].fdBerthName']").val(fdBerthName);
		$("[name='fdTravelList_Form["+index+"].fdBerthId']").val(fdBerthId);
		$("[name='fdTravelList_Form["+index+"].fdVehicleId']").val(fdVehicleId);
		$("[name='fdTravelList_Form["+index+"].fdVehicleName']").val(fdVehicleName);
		$("[name='fdTravelList_Form["+index+"].fdSubject']").val(fdtravelId);
		$("[name='fdTravelList_Form["+index+"].fdPersonListNames']").val(fdPersonListNames);
		$("[name='fdTravelList_Form["+index+"].fdPersonListIds']").val(fdPersonListId);
    		$('.ld-addTravel-body').removeClass('ld-addTravel-body-show')
    		$(".ld-footer-travel").css("display","none");
    		ableScroll()
    		//清空原值
    		$("#fdBerthName").val("");
    		$("#fdBerthId").val("");
    		$("#fdVechileName").val("");
    		$("#fdVechileId").val("");
    		//判断是否有费用明细	
    		var len=$("span[name='travelSubject']").length;
    		if(len>0){
    			var travelName=$("span[name='travelSubject']").eq(0).text()
    			var index=$("#TABLE_DocList_fdDetailList_Form").find("tr").length;
        		for(var i=0;i<index;i++){
        			var value=$("input[name='fdDetailList_Form["+i+"].fdTravel']").val();
        			if(value==""){
        				$("input[name='fdDetailList_Form["+i+"].fdTravel']").val(travelName);
        			}
        		}
    		}
    		
    }
    
    function cancelTripDetail() {
	    	$('.ld-addTravel-body').removeClass('ld-addTravel-body-show');
	    	$('.ld-newApplicationForm').removeClass('ld-newApplicationForm-hidden');
	    	$(".ld-footer-travel").css("display","none");
	    	ableScroll()
    }
    
    
    
    //编辑行程
	function editTravelDetail(e) {
		e = e||window.event;
    	var ele = e.target||e.srcElement;
    	ele = DocListFunc_GetParentByTagName("LI",ele);
    	var index= $(ele).find('[name$=travelId]').val();
        $("[name='row-travel-index']").val(index);
        $("[name='fdSubject']").val($("[name='fdTravelList_Form["+index+"].fdSubject']").val());
		$("[name='fdPersonList']").val($("[name='fdTravelList_Form["+index+"].fdPersonListNames']").val());
	    $("[name='fdPersonListId']").val($("[name='fdTravelList_Form["+index+"].fdPersonListIds']").val());
	    $("[name='days']").val( $("[name='fdTravelList_Form["+index+"].fdTravelDays']").val()+"天");
	    $("[name='startCity']").val($("[name='fdTravelList_Form["+index+"].fdStartPlace']").val());
	    $("[name='endCity']").val( $("[name='fdTravelList_Form["+index+"].fdArrivalPlace']").val());
	    $("[name='endCityId']").val( $("[name='fdTravelList_Form["+index+"].fdArrivalId']").val());
	    $("[name='trafficToolsId']").val($("[name='fdTravelList_Form["+index+"].fdBerthId']").val());
	    $("[name='trafficTools']").val($("[name='fdTravelList_Form["+index+"].fdVehicleName']").val());
	    $("[name='fdVehicleId']").val($("[name='fdTravelList_Form["+index+"].fdVehicleId']").val());
	    $("[name='fdBerthId']").val($("[name='fdTravelList_Form["+index+"].fdBerthId']").val());
	    $("[name='fdBerthName']").val($("[name='fdTravelList_Form["+index+"].fdBerthName']").val());
	    $("#fdBeginDate0").val( $("[name='fdTravelList_Form["+index+"].fdBeginDate']").val());
	    $("#fdEndDate0").val($("[name='fdTravelList_Form["+index+"].fdEndDate']").val());
	    $('.ld-addTravel-body').addClass('ld-addTravel-body-show')
	    $(".ld-footer-travel").css("display","flex");
	    forbiddenScroll()
	}
	  
	 //删除行程明细
	function deleteTravelDetail(e) {
    	e = e||window.event;
    	e.cancelBubble?(e.cancelBubble=true):e.stopPropagation();
    	var ele = e.target||e.srcElement;
    	var index= $(ele).parent().parent().find('[name$=travelId]').val();
    	var val=$("input[name='fdTravelList_Form["+index+"].fdSubject']").val();
    	DocList_DeleteRow($("#TABLE_DocList_fdTravelList_Form>tbody>tr").get(index));
    	$(ele).parent().parent().remove();
    	refreshTravelIndex(val);
    	refreshTravelfdSubject();
    }
	//刷新行程明细的下标
	function refreshTravelIndex(val){
		$("#fdTravelListId>li").each(function(i){
			$(this).find("input[name=travelId]").val(i);
		})
		//对已关联删除行程明细的数据刷新
		var index=$("#TABLE_DocList_fdDetailList_Form").find("tr").length;
		for(var i=0;i<index;i++){
			var value=$("input[name='fdDetailList_Form["+i+"].fdTravel']").val();
			if(val==value){
				$("input[name='fdDetailList_Form["+i+"].fdTravel']").val(""); //清空费用明细所属行程
			}
		}
	}

	function refreshTravelfdSubject(){
		var len=$("#TABLE_DocList_fdTravelList_Form tr").length;
		for(var i=0;i<len;i++){
			var fdSubject=$("[name='fdTravelList_Form["+i+"].fdSubject']").val(fsscLang["fssc-expense:fsscExpenseTravelDetail.fdSubject"]+(i+1));
		}
		var len=$("#fdTravelListId li").length;
		for(var i=0;i<len;i++){
			$("#fdTravelListId li").eq(i).find("[name='travelSubject']").html(fsscLang["fssc-expense:fsscExpenseTravelDetail.fdSubject"]+(i+1));
		}
	}
   
    //--------------------------- 时间初始化start----------------------------// 
    function selectTime(id,name,index){
           id=id+index;
           getTime(id,name,index);
    	   new Mdate(id, {
		   acceptId: id,
		   acceptName: name,
		   beginYear: "2019",
		   beginMonth: "1",
		   beginDay: "1",
		   endYear: "2100",
		   endMonth: "12",
		   endDay: "31",
		   format: "-",
		});	
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
      	$("#"+id).val(year+"-"+month+"-"+day);
    }
    
    var mySwiper = new Swiper('.swiper-container',{
    	onlyExternal: true,
    	speed: 500,
	})
    
    
    //--------------------------- 行程明细城市选择start----------------------------//
	
    function selectCity(fdType) {
    	if(!fdType){
    		$(".ld-city-modal-main").show();
    	}
		if($("#fdBerthName").length==0){
    	$(".trafficTools").attr('style','display:none;');//没有交通工具，隐藏选择项
			$(".startCity").css("width","50%");
			$(".endCity").css("width","50%");
		}
    	$("#trafficTools").attr('style','display:none;');
		if(fdType){
			$(".currentCityType>div").removeClass("active");
			$("."+fdType).addClass('active');
			var className = fdType=='startCity'?'':(fdType=='endCity'?'twoLine':'threeLine');
			$(".currentCityType").addClass(className);
			if(fdType=='trafficTools'){
				$("#trafficTools").attr('style','display:block;');
				selectVehicle();
			}
		}
    	//初始值判断上
    	var startCity=$("#startCity").val();
    	var endCity=$("[id='endCity']").val();
    	var trafficTools=$("[id='fdBerthName']").val();
		$('.ld-city-mask').addClass('ld-city-mask-show');
	  	var fdCompanyId = $("[name='fdCompanyId']").val();
    	$(".startCity").html(startCity||"出发地点");
    	$(".endCity").html(endCity||"到达地点");
    	$(".trafficTools").html(trafficTools||"交通工具");
		//$("."+fdType).click();
	  	if(fdCompanyId==""){
	  		jqtoast("请选择成本中心");
	  		return false;
	  	}
	  	var keyword = $("#searchItem").val();
	  	if(fdType=='国际'){
	  		fdType = "1";
	  	} else {
	  		fdType = "2";
	  	}
	  	 $.ajax({
	           type: 'post',
	           url:Com_Parameter.ContextPath + formOption['url']['getCityData'],
	           data: {"fdCompanyId":fdCompanyId,"fdType":fdType,"keyword":keyword},
	       }).success(function (data) {
	      	 var rtn = JSON.parse(data);
	      	 if(rtn.result=='success'){
	      		fomartCityData(rtn.data);
	      	  }
	       }).error(function () {
	           console.log("获取城市失败");
	   	})
	}
    
    // 回车搜索
	$("input[name='searchCity']").keypress(function(e) {
		if (e.which == 13) {
			var keyword = $("input[name='searchCity']").val();
			var fdCompanyId = $("[name='fdCompanyId']").val();
			var fdType = $(".ld-city-modal-tab").find(".active").data("type");
			var source=$(".currentCityType").find('.active').data("source");
			if("vehicle"==source){
				selectVehicle();
			}else{
				if (keyword) {
					$.ajax({
						type : 'post',
						url : Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getCityData',
						data : {
							"keyword" : keyword,
							"fdCompanyId" : fdCompanyId,
							"fdType" : fdType
						},
					}).success(function(rtn_data) {
						console.log('获取信息成功');
						var rtn_ = JSON.parse(rtn_data);
						if (rtn_.result == 'success') {
							fomartCityData(rtn_.data);
						}
					}).error(function(data) {
						console.log('获取信息失败');
					})
				}
			}
		}
	});
	// 获取到焦点
	$("input[name='searchCity']").focus(function() {
		$(".ld-city-modal-search").find('i').remove();
		$(".cancel-btn").show();
	})
	// 取消
	$(".cancel-btn").click(function(e) {
		var fdType = $(".ld-city-modal-tab").find(".active").data("type");
		var fdCompanyId = $("[name='fdCompanyId']").val();
		$("[name='searchCity']").val('');
		$(".ld-city-modal-search").append('<i></i>');
		$(".cancel-btn").hide();
		var source=$(".currentCityType").find('.active').data("source");
		if("vehicle"==source){
			selectVehicle();
		}else{
			$.ajax({
				type : 'post',
				url : Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getCityData',
				data : {
					"keyword" : '',
					"fdCompanyId" : fdCompanyId,
					"fdType" : fdType
				},
			}).success(function(data) {
				var rtn = JSON.parse(data);
				if (rtn.result == 'success') {
					fomartCityData(rtn.data);
				}
			}).error(function(data) {
				console.log('获取分类信息失败' + data);
			})
		}
	});
    
  //搜索城市及交通工具
    function searchCity(fdCompanyId,fdType,keyword){
    	if(!fdCompanyId){
    		fdCompanyId = $("[name='fdCompanyId']").val();
    	}
    	if(fdType=='国际'){
	  		fdType = "1";
	  	} else {
	  		fdType = "2";
	  	}
    	$.ajax({
	           type: 'post',
	           url:Com_Parameter.ContextPath + formOption['url']['getCityData'],
	           data: {"fdCompanyId":fdCompanyId,"fdType":fdType,"keyword":keyword?keyword:''},
	       }).success(function (data) {
	    	   	var rtn = JSON.parse(data);
	      	 	if(rtn.result=='success'){
	      	 		fomartCityData(rtn.data);
	      	 	}
	       }).error(function (data) {
	    	   console.log('获取信息失败');
	       })
    }
  //按拼音排序
	function fomartCityData(data){
		$(".ld-city-modal-main").attr('style','');
		var rtn = {};
		for(var i=0;i<data.length;i++){
			data[i].name = $.trim(data[i].name)
			var ch = window.ChineseToPY.get(data[i].name)[0].charAt(0);
			if(rtn[ch]){
				rtn[ch].push(data[i]);
			}else{
				rtn[ch] = [data[i]];
			}
		}
		for(var i in rtn){
			rtn[i].sort(function(o,p){
				return o.name<p.name;
			})
		}
		$(".cityList ul li").remove();
		for(var i in rtn){
			var cityData = rtn[i];
			for(var k=0;k<cityData.length;k++){
				$("[id='"+i+"'] ul").append("<li>"+cityData[k].name+"<span hidden='true'>"+cityData[k].id+"<span/></li>");
				$("[class='cityList'][id='"+i+"']").attr('style',"display:block;");
			}
		}
		//无城市的隐藏
		$(".cityList").each(function(){
			if(!$(this).find('li').html()){
				$(this).attr('style','display:none;');
			}
		});
	}
	       
	$(".ld-city-modal-tab div").on('touchstart mousedown', function (e) {
	    e.preventDefault();
	    $(".ld-city-modal-tab .active").removeClass('active');
	    $(this).addClass('active');
	    var fdType = $(this).text();
	    selectCity(fdType);
	});
	$(".ld-city-modal-tab div").click(function (e) {
	    e.preventDefault()
	});
	
	//取消
	function closeSelectCity(){
	    $('.ld-city-mask').removeClass('ld-city-mask-show');
	    $("input[name='searchCity']").val('');
	    $(".currentCityType").attr('class','currentCityType');
	    $(".currentCityType").find(".active").removeClass('active');
	}
	//选择
	function sureSelectCity(){
		$('.ld-city-mask').removeClass('ld-city-mask-show')
		$("input[name='searchCity']").val('');
		$(".currentCityType").attr('class','currentCityType');
		$(".currentCityType").find(".active").removeClass('active');
	}
	
	$('.ld-city-mask').click(function(e){
		e.stopPropagation?e.stopPropagation():e.cancelBubble = true;
	    if((e.target||e.srcElement).id=="ld-city-mask"){
	        closeSelectCity();
	    }
	})
	// 城市字母索引
	$('body').on('click', '.city-letter a', function () {
	    var s = $(this).html();
	    console.log(s)
	    $('.ld-city-modal-main').scrollTop($('#' + s ).offset().top);
	    $("#showLetter span").html(s);
	    if(s!='常用'){
	        $("#showLetter").show().delay(500).hide(0);
	    }
	});
	
	$('.currentCityType div').click(function(){
	    $('.currentCityType div').removeClass('active');
	    $("input[name='searchCity']").val('');
	    $(this).addClass('active');
	    if($('.startCity').hasClass('active')){
	        $('.currentCityType').removeClass('twoLine')
	        $('.currentCityType').removeClass('threeLine')
	        $('.ld-city-modal-main').css("display","block");
	        $('.ld-city-modal-tab').css("display","flex");
	      	$('.city-letter').css("display","block");
	      	selectCity();
	      	$("#trafficTools").attr('style','display:none;');  //隐藏交通工具DIV
	    }
	    if($('.endCity').hasClass('active')){
	        $('.currentCityType').addClass('twoLine')
	        $('.currentCityType').removeClass('threeLine')
	        $('.ld-city-modal-main').css("display","block");
	        $('.ld-city-modal-tab').css("display","flex");
	      	$('.city-letter').css("display","block");
	      	selectCity();
	      	$("#trafficTools").attr('style','display:none;');  //隐藏交通工具DIV
	    }
	    if($('.trafficTools').hasClass('active')){
	        $('.currentCityType').addClass('threeLine');
	        $(".ld-city-modal-main").attr('style','display:none;');
	        $("#trafficTools").attr('style','display:block;');  //显示交通工具DIV
	        //获取交通工具
	        selectVehicle();
	    }
	});
	
	//选择交通工具
	function selectVehicle(){
		 var fdCompanyId = $("[name='fdCompanyId']").val();
		 var keyword=$("input[name='searchCity']").val();
         $.ajax({
           type: 'post',
           url:Com_Parameter.ContextPath +formOption['url']['getVehicleData'],
           data: {"fdCompanyId":fdCompanyId,"keyword":keyword},
       }).success(function (data) {
      	 var rtn = JSON.parse(data);
      	 $('.ld-city-modal-main').css("display","none");
      	 $('.city-letter').css("display","none");
      	 $('.ld-city-modal-tab').css("display","none");
      	 $(".ld-city-modal-main-trafficTools-list div ul li").remove();
      	 var vehicleData =  rtn.data;
      	 var trafficTools = "";
      	 for(var k=0;k<vehicleData.length;k++){
      		trafficTools += ("<li>"+vehicleData[k].name+
      				        "<input hidden='true' name='fdBerthId' value="+vehicleData[k].id+">" +
      				        "<input hidden='true' name='fdvehicleDataName' value="+vehicleData[k].name+">" +
      				        "<input hidden='true' name='fdBerthName' value="+vehicleData[k].fdBerthName+">" +
      				        "<input hidden='true' name='fdVehicleName'  value="+vehicleData[k].fdVehicleName+">"+
      				        "<input hidden='true' name='fdVehicleId'  value="+vehicleData[k].fdVehicleId+"></li>");
		 }
      	$(".ld-city-modal-main-trafficTools-list div ul").append(trafficTools);
      	$("")
      	setTimeout(function(){
      		$(".ld-city-modal-main-trafficTools").find("ul").css("height",($(window).height()-$(".ld-city-modal-main-trafficTools").offset().top-10)+'px')
      	},500)
       }).error(function () {
           console.log("获取交通工具失败");
   		})
	}
	
	 // 选择消费城市,交通工具
	$(document).on('mousedown','.ld-city-modal-main .ld-city-modal-main-city-list ul li , .ld-city-modal-main-trafficTools-list div ul li', function (e) {  
		if($('.startCity').hasClass('active')){
	    	$('.startCity').html($(this).html());
	    	var endCity = $(this).html();
	    	var arr = endCity.split("<span");
	    	$("[name='startCity']").val(arr[0]);
	    	$("[name='startCityId']").val($(this).find("span").text());
	    	$(".startCity").text(arr[0]);
	    } else if ($('.endCity').hasClass('active')) {
	    	$('.endCity').html($(this).html());
	    	var endCity = $(this).html();
	    	var arr = endCity.split("<span");
	    	$("[name='endCity']").val(arr[0]);
	    	$("[name='endCityId']").val($(this).find("span").text());
	    	$(".endCity").text(arr[0]);
	    } else if($('.trafficTools').hasClass('active')){
	    	$(".trafficTools").text($(this).find("input[name='fdvehicleDataName']").val());
	    	$("[name='fdVehicleId']").val($(this).find("input[name='fdVehicleId']").val());
	    	$("[name='fdBerthId']").val($(this).find("input[name='fdBerthId']").val());
	    	$("[name='fdVehicleName']").val($(this).find("input[name='fdVehicleName']").val());
	    	$("[name='fdBerthName']").val($(this).find("input[name='fdBerthName']").val());
	    	$("[name='trafficTools']").val($(this).find("input[name='fdvehicleDataName']").val());
	    	$("#trafficToolsId").val($(this).find("input[name='fdBerthId']").val());
	    }
	})
	
	 /**********************************************************************
     * 费用明细
     * **********************************************************************/
	var expenEditInit = 0;//费用编辑类型，0,新增；1编辑
	var detailId = 0;
	//新增费用明细
    function addExpensePre() {
	    	var fdCompanyId = $("[name=fdCompanyId]").val();
	    	if(fdCompanyId==""){
	    		jqtoast("请先选择公司！");
	    		return;
	    	}
	    	document.body.style.overflow = 'hidden'
	    	$(".ld-remember-modal").addClass('ld-remember-modal-show');
    }
    
    //取消选择费用
    function cancelAddExpense() {
    		ableScroll();
    	 $(".ld-remember-modal").removeClass('ld-remember-modal-show');
    	 $(".ld-footer-expense").css("display","none");
    }
    
    /**
     * 选择随手记后新增费用明细
     */
    function addExpenseDeteilBySelectNote(note){
    	var index = $("#fdDetailListId>li").length;
    	DocList_AddRow("TABLE_DocList_fdDetailList_Form");//新增一行明细行
    	appendExpenseHtml(index,note['fdExpenseItemName'],note['fdHappenDate'],note['fdRealUserName'],note['fdMoney']||0,note['fdDesc']);//拼接显示内容
    	//给明细行赋值
    	$("[name='fdDetailList_Form["+index+"].fdCompanyId']").val($("[name='fdCompanyId']").val());
		$("[name='fdDetailList_Form["+index+"].fdRealUserName']").val(note['fdRealUserName']);
		$("[name='fdDetailList_Form["+index+"].fdRealUserId']").val(note['fdRealUserId']);
		$("[name='fdDetailList_Form["+index+"].fdDeptName']").val(note['fdDeptName']);
		$("[name='fdDetailList_Form["+index+"].fdDeptId']").val(note['fdDeptId']);
		$("[name='fdDetailList_Form["+index+"].fdExpenseItemName']").val(note['fdExpenseItemName']);
		$("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val(note['fdExpenseItemId']);
		$("[name='fdDetailList_Form["+index+"].fdCostCenterName']").val(note['fdCostCenterName']);
		$("[name='fdDetailList_Form["+index+"].fdCostCenterId']").val(note['fdCostCenterId']);
		$("[name='fdDetailList_Form["+index+"].fdHappenDate']").val(note['fdHappenDate']);
		$("[name='fdDetailList_Form["+index+"].fdStartDate']").val(note['fdHappenDate']);
		$("[name='fdDetailList_Form["+index+"].fdEndDate']").val(note['fdHappenDate']);
		$("[name='fdDetailList_Form["+index+"].fdTravelDays']").val(note['fdTravelDays']);
		$("[name='fdDetailList_Form["+index+"].fdApplyMoney']").val(note['fdMoney']||0);
		$("[name='fdDetailList_Form["+index+"].fdInvoiceMoney']").val(note['fdInvoiceMoney']||0);
		$("[name='fdDetailList_Form["+index+"].fdStandardMoney']").val(note['fdMoney']||0);
		$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(note['fdMoney']||0);
		$("[name='fdDetailList_Form["+index+"].fdUse']").val(note['fdDesc']||'');
		$("[name='fdDetailList_Form["+index+"].fdStartPlaceId']").val(note['fdEndAreaId']);
		$("[name='fdDetailList_Form["+index+"].fdStartPlace']").val(note['fdStartArea']);
		$("[name='fdDetailList_Form["+index+"].fdArrivalPlaceId']").val(note['fdEndAreaId']);
		$("[name='fdDetailList_Form["+index+"].fdArrivalPlace']").val(note['fdEndArea']);
		$("[name='fdDetailList_Form["+index+"].fdCurrencyId']").val(note['fdCurrencyId']);
		$("[name='fdDetailList_Form["+index+"].fdCurrencyName']").val(note['fdCurrencyName']);
		$("[name='fdDetailList_Form["+index+"].fdNoteId']").val(note['fdNoteId']);
		$("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val(note['fdIsDeduct']);
		$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(note['fdNoTaxMoney']);
		$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val(note['fdTaxRate']);
		$("[name='fdDetailList_Form["+index+"].fdDayCalType']").val(note['fdDayCalType']);
		FSSC_LoadTempInfo(note['fdNoteId'],index);
		FSSC_InitCurrencyAndRate(index);
		
    	 //计算报销金额
    	 FSSC_ChangeMoney(null,null,index);
    	 //预算的匹配
    	 FSSC_MatchBudget(null,index);
    	 //标准匹配
    	 FSSC_MathStandard(index);
    	 //设置是否抵扣
    	 setIsDeductFromSelectNote(index);
    	 //判断是否有行程明细
    	 FSSC_CheckTravelDetail();
    }
    
    function FSSC_CheckTravelDetail(){
    	//判断是否有行程数据
		var len=$("span[name='travelSubject']").length;
		if(len>0){
			var travelName=$("span[name='travelSubject']").eq(0).text()
			var index=$("#TABLE_DocList_fdDetailList_Form").find("tr").length;
    		for(var i=0;i<index;i++){
    			var value=$("input[name='fdDetailList_Form["+i+"].fdTravel']").val();
    			if(value==""){
    				$("input[name='fdDetailList_Form["+i+"].fdTravel']").val(travelName);
    			     }
    		    }
		    }
      }
    
    function setIsDeductFromSelectNote(index){
    	var fdExpenseItemId = $("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val();
    	var data = new KMSSData();
		data = data.AddBeanData("eopBasedataInputTaxService&authCurrent=true&fdExpenseItemId="+fdExpenseItemId);
		data = data.GetHashMapArray();
		if(data&&data.length>0){
			var fdApplyMoney = $("[name='fdDetailList_Form["+index+"].fdApplyMoney']").val();
			if(!data[0].fdIsInputTax){
				$("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val(false);
				$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdApplyMoney)
				return;
			}
			$("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val(true);
			var fdTaxRate = divPoint(data[0].fdTaxRate,100);//(票面金额÷(1+税额)*税额)
		    fdTaxMoney = multiPoint(divPoint(fdApplyMoney,numAdd(fdTaxRate,1.00)),fdTaxRate);
			$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(fdTaxMoney);  	//进项税额
			$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val(data[0].fdTaxRate);		//进项税率
			$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(numSub(fdApplyMoney,fdTaxMoney));//不含税金额
		}
    }
    
    function selectExpenseCurrency(){
	    	var fdCompanyId = $("[name='fdCompanyId']").val();
	    var ids = [];
	    $("#TABLE_DocList_fdDetailList_Form>tbody>tr [name$=fdCurrencyId]").each(function(){
	    		ids.push(this.value);
	    })
	    	if(fdCompanyId){
	    		$.ajax({
	    			url: Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getCurrencyData',
	    			type: 'post',
	    			async:false,
	    			data: {fdCompanyId:fdCompanyId,inIds:ids.join(';'),type:'expense'},
	    		}).error(function(data){
	    				console.log("获取信息失败"+data);
	    		}).success(function(data){
	    			 console.log("获取信息成功");
	    	      	 var rtn = JSON.parse(data);  //json数组
	    	      	 var objData=rtn["data"];
	    	      	 var nameObj=$("[name='fdCurrency']");
	    	      	 if(nameObj.length==0){
	    	      		nameObj=$("[id='fdCurrency']");
	    	      	 }
	    	      	 var idObj=$("[name='fdCurrencyId']");
	    	      	 if(idObj.length==0){
	    	      		idObj=$("[id='fdCurrencyId']");
	    	      	 }
	    	      	 //由于picker.js每次只是隐藏，下次重新创建导致无法通过id绑定控件，所以每次新建前清除上一次的对象
	    	      	 $(".picker").remove();
	    	      	 var curValue = idObj.val(),selectedIndex=0;
	    	      	 if(curValue!=null){
	    	      		 for(var i=0;i<objData.length;i++){
	    	      			 if(objData[i].value==curValue){
	    	      				 selectedIndex = i;
	    	      				 break;
	    	      			 }
	    	      		 }
	    	      	 }
	    	         var picker = new Picker({
	    	             data: [objData],
	    	             selectedIndex:[selectedIndex]
	    	          });
	    	          picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
	    	        	  if(objData[selectedIndex]){
	    		        	  nameObj.val(objData[selectedIndex].text);
	    		        	  idObj.val(objData[selectedIndex].value);
	    		        	  changeCurrency(objData[selectedIndex]); 
	    	        	  }
	    	           });
	    	          picker.show();
	    	          //回车搜索
	    	          $("#search_input").keypress(function (e) {
	    	              if (e.which == 13) {
	    	              	var keyword=$("[name='pick_keyword']").val();
	    	              	if(keyword){
	    	              		$.ajax({
	    	              	           type: 'post',
	    	              	           url:Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getCurrencyData',
	    	              	           data: {fdCompanyId:fdCompanyId,keyword:keyword,ids:ids.join(';')},
	    	              	       }).success(function (data) {
	    	              	    	   console.log('获取信息成功');
	    	              	    	   var rtn = JSON.parse(data);
	    	              	    	   picker.refillColumn(0, rtn.data);
	    	              	    	   objData=rtn.data;
	    	              	       }).error(function (data) {
	    	              	    	   console.log('获取信息失败');
	    	              	       })
	    	              	}
	    	              }
	    	      	});
	    	        //获取到焦点
	    	        $("#search_input").focus(function(){
	    	      		$(".weui-icon-clear").attr("style","display:block;");
	    	      	}) 
	    		      //取消
	    	          $(".weui-icon-clear").click(function (e) {
	    	          	$.ajax({
	    	   	           type: 'post',
	    	   	           url:Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getCurrencyData',
	    	   	           data: {fdCompanyId:fdCompanyId,"keyword":'',ids:ids.join(';')},
	    	   	       }).success(function (data) {
	    	   	    	   		console.log('获取分类信息成功');
	    	   	    	   		var rtn = JSON.parse(data);
	    	   	    	   		picker.refillColumn(0, rtn.data);
	    	   	    	   		objData=rtn.data;
	    	   	    	   		$("[name='pick_keyword']").val('');
	    	   	       }).error(function (data) {
	    	   	    	   		console.log('获取分类信息失败');
	    	   	       })
	    	      	});
	    		});
	    	}
    }
    
    //新增费用明细
    function addExpenseDeteil(resource){
    	var index = $("#fdDetailListId>li").length;
    	$("[name='row-expense-index']").val(index);
    	//清空历史值
		clearValue();
		getTime('fdHappenDate','fdHappenDate','');
		if($("#fdHappenDate").length>0){
			selectTime('fdHappenDate','fdHappenDate','');
		}
		getTime('fdEndDate1','fdEndDate1','');
		if($("#fdEndDate1").length>0){
			selectTime('fdEndDate','fdEndDate','1');
		}
		getTime('fdBeginDate1','fdBeginDate1','');
		if($("#fdBeginDate1").length>0){
			selectTime('fdBeginDate','fdBeginDate','1');
		}
    	//增加一行费用明细，初始化相关数据
//        if(index==0){
        	$("#fdRealUser").val($("[name='fdClaimantName']").val());
          	$("#fdRealUserId").val($("[name='fdClaimantId']").val());
          	if(currentCity&&currentCityId){
          		$("#startCity").val(currentCity);
              	$("#endCity").val(currentCity);	
          		$("#startCityId").val(currentCityId);
              	$("#endCityId").val(currentCityId);	
          	}

          	$("#fdCostCenterDetail").val($("[name='fdCostCenterName']").val());
          	$("#fdCostCenterDetailId").val($("[name='fdCostCenterId']").val());
          	FSSC_InitCurrencyAndRate(index);
//        }
  		$(".ld-footer-expense").css("display","flex");
  		$('.ld-entertain-main-body').addClass('ld-entertain-main-body-show');
        $(".ld-remember-modal").removeClass('ld-remember-modal-show');
        forbiddenScroll()
    }
    function clearValue(){
    		$("#fdInputTaxMoney").val('')
    		$("#fdExpenseItemName").val('')
    		$("#fdExpenseItemId").val('')
    		$("#fdNonDeductMoney").val('')
    		$("#fdApplyMoney").val('')
    		$("#fdUse").val('')
    		$("#fdNoTaxMoneyExpense").val('')
    		$("#fdStandardMoney").val('')
    		$("#fdInputTaxRate").val('')
    		$("_fdIsDeduct").parent().click();
    		$("#fdBerthId").val('')
    		$("#fdBerthName").val('')
    		$("#endCity").val('')
    		$("#endCityId").val('')
    		$("#startCity").val('')
    		$("#startCityId").val('')
		    $("#fdDayCalType").val('')
		    $("#days0").val('1天');
		    $("#days1").val('1天');
    }
    
    //获取未报费用列表
    function addExpenseByRemember(){
    		document.body.style.overflow = 'hidden'
    		var ids = [];
    		$("#TABLE_DocList_fdDetailList_Form [name$=fdNoteId]").each(function(){
    			if(!this.value){
    				return ;
    			}
    			ids.push(this.value);
    		})
//    		$.post(
//    			Com_Parameter.ContextPath+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=data&select=expense',
//    			{"selectNote":"selectNote",ids:ids.join(';')},
//    			function(data){
//    				layer.open({
//    		    		  type: 2,
//    		    		  content: data,
//    		    		  anim: 'up',
//    		    		  style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; overflow: auto;',
//    				});
//    				
//    			}
//    		)
    		var fdTemplateId=$("[name='fdTemplateId']").val();
    		var fdCompanyId=$("[name='fdCompanyId']").val();
    		var src = Com_Parameter.ContextPath+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=data&selectNote=selectNote&ids='+ids.join(';')+"&fdTemplateId="+fdTemplateId+"&fdCompanyId="+fdCompanyId;
    		var h = $(window).height();
    		layer.open({
			  type: 1,
			  content: '<iframe src="'+src+'" height="'+h+'px" width="100%" id="selectNoteIframe"></iframe>',
			  anim: 'up',
			  style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; overflow: auto;'
    		});
    }

    //从未报费用选择
	function selectExpense(ids){
		document.body.style.overflow = 'auto'
		 $(".ld-remember-modal").removeClass('ld-remember-modal-show');
		 layer.closeAll();
		 if(!ids){
			return;
		}
		$.ajax({
            type: 'post',
            url:Com_Parameter.ContextPath + formOption['url']['getNoteByIds'],
            data: {"ids":ids},
        }).success(function (data) {
      	    var rtn = JSON.parse(data);
      	    if(rtn.result=='success'){
 	      	    var arr = rtn.data;
 	      	    //新增费用
 	      	    if(arr.note.length > 0){
	 	      	    var notes = arr.note;
	 	      	    for(var i=0;i<notes.length;i++){
	 	      	    	addExpenseDeteilBySelectNote(notes[i]);
	 	      	    }
	 	      	    sumfdTotalStandaryMoney();
 	      	    }
 	      	    //新增发票
 	      	    if(arr.invoice.length > 0){
	 	      	    	var invoice = arr.invoice;
	 	      	    	for(var i=0;i<invoice.length;i++){
	 	      	    		var key = (invoice[i].fdInvoiceNo||'')+(invoice[i].fdInvoiceCode||'')
	 	      	    		//如果存在发票代码或者发票号码，需要校验当前发票在明细中是否已存在，不存在才需要添加
	 	      	    		if(key){
	 	      	    			var add = true;
	 	      	    			$("#TABLE_DocList_fdInvoiceList_Form>tbody>tr").each(function(){
	 	      	    				var fdInvoiceNumber = $(this).find("[name$=fdInvoiceNumber]").val()||'';
	 	      	    				var fdInvoiceCode = $(this).find("[name$=fdInvoiceCode]").val()||'';
	 	      	    				if((fdInvoiceNumber||fdInvoiceCode)&&key==(fdInvoiceNumber+fdInvoiceCode)){
	 	      	    					add = false;
	 	      	    				}
	 	      	    			})
	 	      	    			if(!add){
	 	      	    				continue;
	 	      	    			}
	 	      	    		}
	 	      	    		addInvoiceByNote(invoice[i]);
 	      			}
 	      	    }
      	    }
        })
	}
	
	function cancelSelect(){
		ableScroll();
		layer.closeAll();
		$(".ld-remember-modal").removeClass('ld-remember-modal-show');
	}
  
    /**************************************************************************
     * 保存费用明细
     **************************************************************************/
   function saveExpeseDeteil() {
	   var index = $("[name='row-expense-index']").val();
    	var fdApplyMoney = $("#fdApplyMoney").val();
    	var fdRealUser = $("#fdRealUser").val();
    	var fdRealUserId = $("#fdRealUserId").val();
    	var fdExpenseItemName = $("#fdExpenseItemName").val();
    	var fdExpenseItemId = $("#fdExpenseItemId").val();
    	var fdCostCenter = $("#fdCostCenterDetail").val();
    	var fdCostCenterId = $("#fdCostCenterDetailId").val();
    	var fdHappenDate = $("#fdHappenDate").length>0?$("#fdHappenDate").val():$("#fdEndDate1").val();
    	var fdStartDate = $("#fdBeginDate1").val();
    	var fdEndDate = $("#fdEndDate1").val();
    	var fdTravelDays=$("#days1").length>0?$("#days1").val().replace("天",""):"";
    	var fdUse = $("#fdUse").val();
    	var fdStandardMoney = $("#fdStandardMoney").val();
    	var startCityId = $("#startCityId").val();
    	var startCity = $("#startCity").val();
    	var endCityId = $("#endCityId").val();
    	var endCity = $("#endCity").val();
    	var trafficTool = $("#fdBerthName").val();
    	var trafficToolId = $("#fdBerthId").val();
    	var fdInputTaxMoney = $("#fdInputTaxMoney").val();
    	var fdInputTaxRate = $("input[name='fdInputTaxRate']").val();
    	var fdIsDeduct = $("[name=fdIsDeduct]").val();
    	var fdNoTaxMoney=$("#fdNoTaxMoneyExpense").val();
    	var fdNonDeductMoney=$("#fdNonDeductMoney").val();
    	var fdBudgetMoney=$("#fdBudgetMoney").val();
    	var fdDayCalType=$("#fdDayCalType").val();
    	if($("#fdPersonNumber").length>0){
    		var fdPersonNumber = $("#fdPersonNumber").val();
    		var digits = /^[1-9]\d*$/;
    		if(fdPersonNumber&&!digits.test(fdPersonNumber)){
    			jqtoast("招待人数,请填写正整数");
	    		return ;
    		}
    	}
    
    	var number = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;//两位小数且数字校验
    	if(fdNonDeductMoney){
	        if(!number.test(fdNonDeductMoney) ){
	    		jqtoast("不可抵扣额,请填写数字，且只能是两位小数");
	    		return ;
	    	}
    	}
    	if(fdInputTaxMoney){
	    	if(!number.test(fdInputTaxMoney) ){
	    		jqtoast("进项税额,请填写数字，且只能是两位小数");
	    		return ;
	    	}
    	}
    	if(!number.test(fdApplyMoney) ){
    		jqtoast("申报金额,请填写数字，且只能是两位小数");
    		return ;
    	}
    	if(fdNoTaxMoney&&!number.test(fdNoTaxMoney) ){
    		jqtoast("不含税金额,请填写数字，且只能是两位小数");
    		return ;
    	}
    	
    	//行程合并，费用明细要填城市和交通工具
		var fdExpenseType = $("[name=fdExpenseType]").val();
		var fdIsTravelAlone = $("[name=fdIsTravelAlone]").val();
		if(fdExpenseType=='2'&&fdIsTravelAlone=='false'){
			if(!$("#startCityId").val()){
				jqtoast("请填写出发城市");
	    		return ;
			}
			if(!$("#endCityId").val()){
				jqtoast("请填写到达城市");
	    		return ;
			}
			if($("#fdBerthId").length>0&&!$("#fdBerthId").val()){
				jqtoast("请填写交通工具");
	    		return ;
			}
		}
		//行程独立，需要校验是否关联了行程
		if(fdExpenseType=='2'&&fdIsTravelAlone=='true'){
			if(!$("#fdTravel").val()){
				jqtoast("请选择行程");
	    		return ;
			}
		}
    	
    	if(fdRealUser == ""){
    		 jqtoast('请填写报销人');
    		 return ;
    	}else if (fdExpenseItemId == "") {
    		 jqtoast('请填写费用类型');
    		 return ;
    	}else if(fdApplyMoney==""){
    		jqtoast("请填写申报金额");
    		return ;
    	}
    	if($("#fdPersonNumber").length>0&&!$("#fdPersonNumber").val()){
    		jqtoast("请填写招待人数");
    		return ;
    	}
    	//如果当前下标等于已有明细行数，则说明是新增明细,否则是编辑
    	if (index==$("#fdDetailListId>li").length) {
   		 	//拼接费用明细
    		DocList_AddRow("TABLE_DocList_fdDetailList_Form");
    		appendExpenseHtml(index,fdExpenseItemName,fdHappenDate,fdRealUser,fdApplyMoney,fdUse);
   	} else{
   		var li = $("#fdDetailListId>li").eq(index);
   		li.find(".fdExpenseItemName").html(fdExpenseItemName);
   		li.find(".fdHappenDate").html(fdHappenDate);
   		li.find(".fdRealUserName").html(fdRealUser);
   		li.find(".fdApplyMoney").html(fdApplyMoney);
   		if(fdUse){
   			if(li.find(".fdUse").length==0){
   	   			li.find(".ld-notSubmit-list-bottom").append("<p class='fdUse'></p>");
   	   		}
   	   		li.find(".fdUse").html("事由："+fdUse);
   		}else{
   			li.find(".fdUse").remove();
   		}
   	}
    	$("[name='fdDetailList_Form["+index+"].fdCompanyId']").val($("[name='fdCompanyId']").val());
    	$("[name='fdDetailList_Form["+index+"].fdTravel']").val($("#fdTravel").val());
    	$("[name='fdDetailList_Form["+index+"].fdRealUserName']").val(fdRealUser);
		$("[name='fdDetailList_Form["+index+"].fdRealUserId']").val(fdRealUserId);
		$("[name='fdDetailList_Form["+index+"].fdDeptName']").val($("#fdDept").val());
		$("[name='fdDetailList_Form["+index+"].fdDeptId']").val($("#fdDeptId").val());
		$("[name='fdDetailList_Form["+index+"].fdExpenseItemName']").val(fdExpenseItemName);
		$("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val(fdExpenseItemId);
		$("[name='fdDetailList_Form["+index+"].fdCostCenterName']").val(fdCostCenter);
		$("[name='fdDetailList_Form["+index+"].fdCostCenterId']").val(fdCostCenterId);
		$("[name='fdDetailList_Form["+index+"].fdWbsName']").val($("#fdWbs").val());
		$("[name='fdDetailList_Form["+index+"].fdWbsId']").val( $("#fdWbsId").val());
		$("[name='fdDetailList_Form["+index+"].fdInnerOrderName']").val($("#fdInnerOrder").val());
		$("[name='fdDetailList_Form["+index+"].fdInnerOrderId']").val($("#fdInnerOrderId").val());
		$("[name='fdDetailList_Form["+index+"].fdHappenDate']").val(fdHappenDate);
		$("[name='fdDetailList_Form["+index+"].fdEndDate']").val(fdEndDate);
		$("[name='fdDetailList_Form["+index+"].fdStartDate']").val(fdStartDate);
		$("[name='fdDetailList_Form["+index+"].fdTravelDays']").val(fdTravelDays);
		
		$("[name='fdDetailList_Form["+index+"].fdCurrencyName']").val($("#fdCurrency").val());
		$("[name='fdDetailList_Form["+index+"].fdCurrencyId']").val($("#fdCurrencyId").val());
		$("[name='fdDetailList_Form["+index+"].fdExchangeRate']").val( $("#fdExchangeRate").val());
		
		var fdBudgetRate = $("#fdBudgetRate").val()*1;
		fdBudgetRate=fdBudgetRate?fdBudgetRate:1;
		$("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val(fdBudgetRate);
		$("[name='fdDetailList_Form["+index+"].fdApplyMoney']").val(fdApplyMoney);
		$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(fdBudgetMoney);
		$("[name='fdDetailList_Form["+index+"].fdUse']").val(fdUse);
		$("[name='fdDetailList_Form["+index+"].fdPersonNumber']").val( $("#fdPersonNumber").val());
		
		$("[name='fdDetailList_Form["+index+"].fdStandardMoney']").val(fdStandardMoney);
		$("[name='fdDetailList_Form["+index+"].fdApprovedApplyMoney']").val(fdApplyMoney);
		$("[name='fdDetailList_Form["+index+"].fdApprovedStandardMoney']").val(fdStandardMoney);
		
		$("[name='fdDetailList_Form["+index+"].fdStartPlaceId']").val(startCityId);
		$("[name='fdDetailList_Form["+index+"].fdStartPlace']").val(startCity);
		$("[name='fdDetailList_Form["+index+"].fdArrivalPlaceId']").val(endCityId);
		$("[name='fdDetailList_Form["+index+"].fdArrivalPlace']").val(endCity);
		$("[name='fdDetailList_Form["+index+"].fdBerthName']").val(trafficTool);
		$("[name='fdDetailList_Form["+index+"].fdBerthId']").val(trafficToolId);
		$("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val(fdIsDeduct);
		$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(fdInputTaxMoney);
		$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val(fdInputTaxRate);
		$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdNoTaxMoney);
		$("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val( $("#fdNonDeductMoney").val());
		$("[name='fdDetailList_Form["+index+"].fdDayCalType']").val(fdDayCalType);
		var fdProjectId=$("[name='fdProjectId']").val();
		var fdProject=$("[name='fdProject']").val();
		var fdDetailProjectId=$("[name='fdDetailProjectId']").val();
		var fdDetailProjectName=$("[name='fdDetailProjectName']").val();
		if(fdProjectId){//说明项目在主表，保存时把主表项目信息赋值给明细
			$("[name='fdDetailList_Form["+index+"].fdProjectId']").val(fdProjectId);
			$("[name='fdDetailList_Form["+index+"].fdProjectName']").val(fdProject);
		}else if(fdDetailProjectId){//项目在明细
			$("[name='fdDetailList_Form["+index+"].fdProjectId']").val(fdDetailProjectId);
			$("[name='fdDetailList_Form["+index+"].fdProjectName']").val(fdDetailProjectName);
		}
    	
    	//计算合计总额
    	 sumfdTotalStandaryMoney();
    	 //计算报销金额
    	 FSSC_ChangeMoney(null,null,index);
    	 //预算的匹配
    	 FSSC_MatchBudget(null,index);
    	 //标准匹配
    	 FSSC_MathStandard(index);
         $('.ld-entertain-main-body').removeClass('ld-entertain-main-body-show');
         $('.ld-footer-expense').attr('style','display:none;');
         ableScroll()
         clearValue()
     }
   
   	function cancelSaveDetail(){
   		$('.ld-entertain-main-body').removeClass('ld-entertain-main-body-show')
        ableScroll();
   		clearValue()
   		$(".ld-footer-expense").hide(); 
   	}
     
     //拼接费用明细
     function appendExpenseHtml(detailId,fdExpenseItemName,fdHappenDate,fdRealUser,fdApplyMoney,fdUse){
	    	 fdExpenseItemName = fdExpenseItemName||'其它费用';
	    	 var fdStandardHtml ="";//标准显示
	    	 if($("[name=checkVersion]").val()){
				fdStandardHtml="<span class='fdStandardStatus ld-newApplicationForm-travelInfo-top-buget-0'>无标准</span>";
	    	 }
	    	 var src = Com_Parameter.ContextPath+"fssc/mobile/resource/images/taxi.png";
	    	 var html1 = $("<div class=\"icon_type\"><img src="+src+" ><span class='fdExpenseItemName'>"+fdExpenseItemName+"</span></div>" +
						"<div class=\"ld-newApplicationForm-travelInfo-top-status\"><span class=\"feeStatus ld-newApplicationForm-travelInfo-top-buget-0\">无申请</span>" +
						"<span class=\"bugetStatus ld-newApplicationForm-travelInfo-top-buget-0\">无预算</span>" +fdStandardHtml+
	    				"<input name='detailId' value="+detailId+" hidden='true'></div><i class=\"viewInvoiceInfo\" onclick=\"viewInvoiceInfo();\"></i><i onclick=\"deleteExpenseDeteil()\" ></i>") ;
	    	 var html2 = $("<div class=\"ld-notSubmit-list-bottom-info\">" +
	    				" <div><span class='fdHappenDate' >"+fdHappenDate+"</span>" +
	    				"<span class='ld-verticalLine'></span><span class='fdRealUserName'>"+fdRealUser+"</span></div>" +
	    				"<div><span class='fdApplyMoney'>"+fdApplyMoney+"</span><span></span>" +
	    				"</div></div>"+(fdUse?("<p class='fdUse'>事由："+fdUse)+"</p>":''));
	    	 var div1 = $("<div class=\"ld-newApplicationForm-travelInfo-top\"></div>").append(html1);
	    	 var div2 = $("<div class=\"ld-notSubmit-list-bottom\"></div>").append(html2);
	    	 var li = $("<li class=\"ld-notSubmit-list-item\" onclick=\"editExpenseDeteil()\"></li>").append(div1,div2);
	    	 $("#fdDetailListId").append(li);//拼接明细
    }
    
    //费用明细赋值
    function addExpenseDeteilValue(param){
    	$("[name='fdDetailList_Form["+detailId+"].fdCompanyId']").val($("[name='fdCompanyId']").val());
		$("[name='fdDetailList_Form["+detailId+"].fdRealUserName']").val(param['fdRealUserName']);
		$("[name='fdDetailList_Form["+detailId+"].fdRealUserId']").val(param['fdRealUserId']);
		$("[name='fdDetailList_Form["+detailId+"].fdDeptName']").val(param['fdDeptName']);
		$("[name='fdDetailList_Form["+detailId+"].fdDeptId']").val(param['fdDeptId']);
		$("[name='fdDetailList_Form["+detailId+"].fdExpenseItemName']").val(param['fdExpenseItemName']);
		$("[name='fdDetailList_Form["+detailId+"].fdExpenseItemId']").val(param['fdExpenseItemId']);
		$("[name='fdDetailList_Form["+detailId+"].fdCostCenterName']").val(param['fdCostCenterName']);
		$("[name='fdDetailList_Form["+detailId+"].fdCostCenterId']").val(param['fdCostCenterId']);
		$("[name='fdDetailList_Form["+detailId+"].fdHappenDate']").val(param['fdHappenDate']);
		$("[name='fdDetailList_Form["+detailId+"].fdTravelDays']").val(param['fdTravelDays']);
		$("[name='fdDetailList_Form["+detailId+"].fdApplyMoney']").val(param['fdMoney']);
		$("[name='fdDetailList_Form["+detailId+"].fdUse']").val(param['fdDesc']||'');
		$("[name='fdDetailList_Form["+detailId+"].fdStartPlaceId']").val(param['fdStartAreaId']);
		$("[name='fdDetailList_Form["+detailId+"].fdStartPlace']").val(param['fdStartArea']);
		$("[name='fdDetailList_Form["+detailId+"].fdArrivalPlaceId']").val(param['fdEndAreaId']);
		$("[name='fdDetailList_Form["+detailId+"].fdArrivalPlace']").val(param['fdEndArea']);
		$("[name='fdDetailList_Form["+detailId+"].fdCurrencyId']").val(param['fdCurrencyId']);
		$("[name='fdDetailList_Form["+detailId+"].fdCurrencyName']").val(param['fdCurrencyName']);
		$("[name='fdDetailList_Form["+detailId+"].fdCurrencyName']").val(param['fdCurrencyName']);
		$("[name='fdDetailList_Form["+detailId+"].fdNoteId']").val(param['fdNoteId']);
		FSSC_InitCurrencyAndRate(detailId);
    }
     
     //编辑费用
      function editExpenseDeteil(e) {
    	  var e = e||window.event;
    	  var ele = e.srcElement||e.target;
    	  e.stopPropagation?e.stopPropagation():(e.cancelBubble = true);
    	  ele = DocListFunc_GetParentByTagName("LI",ele);
    	  var index = $(ele).find("input[name=detailId]").val();
    	  $("[name='row-expense-index']").val(index);
    	  	$(".ld-footer-expense").css("display","flex");
    	    $("#fdTravel").val($("[name='fdDetailList_Form["+index+"].fdTravel']").val());
    	    $("#fdRealUser").val($("[name='fdDetailList_Form["+index+"].fdRealUserName']").val());
    	    $("#fdRealUserId").val($("[name='fdDetailList_Form["+index+"].fdRealUserId']").val());
    	    $("#fdDept").val($("[name='fdDetailList_Form["+index+"].fdDeptName']").val());
    	    $("#fdDeptId").val($("[name='fdDetailList_Form["+index+"].fdDeptId']").val());
    	    $("#fdExpenseItemName").val($("[name='fdDetailList_Form["+index+"].fdExpenseItemName']").val());
    	    $("#fdExpenseItemId").val($("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val());
    	    $("#fdCostCenterDetail").val($("[name='fdDetailList_Form["+index+"].fdCostCenterName']").val());
    	    $("#fdCostCenterDetailId").val($("[name='fdDetailList_Form["+index+"].fdCostCenterId']").val());
    	    $("#fdExchangeRate").val($("[name='fdDetailList_Form["+index+"].fdExchangeRate']").val());
    	    $("#fdBudgetRate").val($("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val());
    	    $("#fdBudgetMoney").val($("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val());
    	    $("#fdWbs").val($("[name='fdDetailList_Form["+index+"].fdWbsName']").val());
    	    $("#fdWbsId").val($("[name='fdDetailList_Form["+index+"].fdWbsId']").val());
    	    $("#fdInnerOrder").val($("[name='fdDetailList_Form["+index+"].fdInnerOrderName']").val());
    	    $("#fdInnerOrderId").val($("[name='fdDetailList_Form["+index+"].fdInnerOrderId']").val());
    	    $("#fdHappenDate").val($("[name='fdDetailList_Form["+index+"].fdHappenDate']").val());
    	    $("#fdEndDate1").val($("[name='fdDetailList_Form["+index+"].fdHappenDate']").val());
    	    $("#fdBeginDate1").val($("[name='fdDetailList_Form["+index+"].fdStartDate']").val());
    	    $("#fdTravelDays").val("[name='fdDetailList_Form["+index+"].fdTravelDays']"+"天");
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
    	    $("#fdBerthName").val($("[name='fdDetailList_Form["+index+"].fdBerthName']").val());
    	    $("#fdBerthId").val($("[name='fdDetailList_Form["+index+"].fdBerthId']").val());
    	    $("#fdInputTaxMoney").val($("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val());
    	    var fdIsDeduct =$("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val();
    	    if(fdIsDeduct=='true'){
    	    	$("[name=_fdIsDeduct][value=true]").parent().click();
    	    }else{
    	    	$("[name=_fdIsDeduct][value=false]").parent().click();
    	    }
    	    $("#fdNonDeductMoney").val($("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val());
    	    $("#fdNoTaxMoneyExpense").val($("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val());
		    $("#fdDayCalType").val($("[name='fdDetailList_Form["+index+"].fdDayCalType']").val());
		    $("#fdDetailProjectId").val($("[name='fdDetailList_Form["+index+"].fdProjectId']").val());
		    $("#fdDetailProjectName").val($("[name='fdDetailList_Form["+index+"].fdProjectName']").val());
    	    $('.ld-entertain-main-body').addClass('ld-entertain-main-body-show')
    	    forbiddenScroll()
      };
    
    //删除费用
    function deleteExpenseDeteil(e) {
    	var e = e||window.event;
    	var ele = e.srcElement||e.target;
    	e.stopPropagation?e.stopPropagation():(e.cancelBubble = true);
    	ele = DocListFunc_GetParentByTagName("LI",ele);
    	var index = $(ele).find("input[name=detailId]").val();
    	var fdNoteId=$("input[name='fdDetailList_Form["+index+"].fdNoteId']").val();
        if(fdNoteId){//存在随手记ID，删除级联删除页面发票
        	 var url =Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getNoteInvoice';
        	 $.ajax({
                 type: 'post',
                 url:url,
                 data: {"fdNoteId":fdNoteId},
             }).success(function (data) {
    	      	    var rtn = JSON.parse(data);
    	      	    if(rtn.result=='success'){
    			    	  DocList_DeleteRow($("#TABLE_DocList_fdDetailList_Form>tbody>tr").get(index));
    			          $(ele).remove();
    			          refreshExpenseDetailIndex();
    			          sumfdTotalStandaryMoney();//重新计算报销总额
    			          if(rtn.data&&rtn.data.length>0){
    			        	  for(var i=0;i<rtn.data.length;i++){
        			        	  var number=rtn.data[i]["number"];
        			        	  var proName=$("input[name*='fdInvoiceNumber'][value='"+number+"']").attr("name");
        			        	  var invoiceIndex=proName.substring(proName.indexOf("[")+1,proName.indexOf("]"));
        			        	  $("#fdInvoiceListId").find("li").eq(invoiceIndex).remove();
        			        	  DocList_DeleteRow($("#TABLE_DocList_fdInvoiceList_Form>tbody>tr").get(invoiceIndex));
        			        	  refreshInvoiceIndex();
        			          }
    			          }
    	      	 }
             })
        }else{
        	 DocList_DeleteRow($("#TABLE_DocList_fdDetailList_Form>tbody>tr").get(index));
             $(ele).remove();
             refreshExpenseDetailIndex();
             sumfdTotalStandaryMoney();//重新计算报销总额
        }
    };
    
    //刷新费用明细下标
    function refreshExpenseDetailIndex(){
    	$("#fdDetailListId>li").each(function(i){
    		$(this).find("input[name=detailId]").val(i);
    	})
    }
    
    //合计申请金额(本币)
    function sumfdTotalStandaryMoney(){
    	 var len = $("#TABLE_DocList_fdDetailList_Form > tbody > tr").length,fdTotalStandaryMoney=0;
    	 for(var i=0;i<len; i++){
    		 var fdStandardMoney = $("[name='fdDetailList_Form["+i+"].fdStandardMoney']").val();
    		 var fdStandardMoneys = parseFloat(fdStandardMoney);
    		 fdTotalStandaryMoney += fdStandardMoneys;
    	 }
    	 $("[name='fdTotalApprovedMoney']").val(fdTotalStandaryMoney.toFixed(2));
    	 $("[name='fdTotalStandaryMoney']").val(fdTotalStandaryMoney.toFixed(2));
    	 if($("#fdAccountsListId>li").length==1){
    		 $("#fdAccountsListId>li .fdMoney").html(fdTotalStandaryMoney.toFixed(2));
        	 $("[name='fdAccountsList_Form[0].fdMoney']").val(fdTotalStandaryMoney.toFixed(2));
    	 }
    }
    
    //初始化币种及汇率
    function FSSC_InitCurrencyAndRate(index,flag) {
		var fdCompanyId = $("#fdCompanyId").val();
		if(!fdCompanyId){
			return;
		}
		var data = new KMSSData();
		data.AddBeanData("eopBasedataCompanyService&type=getStandardCurrencyInfo&authCurrency=true&fdCompanyId="+fdCompanyId);
		data = data.GetHashMapArray();
		if(data.length>0){
			if("account"==flag){
				$("[name='fdAccountsList_Form[0].fdCurrencyName']").val(data[0].fdCurrencyName);
				$("[name='fdAccountsList_Form[0].fdCurrencyId']").val(data[0].fdCurrencyId);
				$("[name='fdAccountsList_Form[0].fdExchangeRate']").val(data[0].fdExchangeRate);
			}else{
				var fdApplyMoney = ($("[name='fdDetailList_Form["+index+"].fdApplyMoney']").val()||0)*1;
				$("#fdCurrencyId").val(data[0].fdCurrencyId);
				$("#fdCurrency").val(data[0].fdCurrencyName);
				$("#fdExchangeRate").val(data[0].fdExchangeRate);
				$("#fdBudgetRate").val(data[0].fdBudgetRate);
				$("[name='fdDetailList_Form["+index+"].fdExchangeRate']").val(data[0].fdExchangeRate);
				$("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val(data[0].fdBudgetRate);
				$("[name='fdDetailList_Form["+index+"].fdStandardMoney']").val(fdApplyMoney);
				$("[name='fdDetailList_Form["+index+"].fdApprovedApplyMoney']").val(multiPoint(fdApplyMoney,data[0].fdExchangeRate));
				$("[name='fdDetailList_Form["+index+"].fdApprovedStandardMoney']").val(multiPoint(fdApplyMoney,data[0].fdExchangeRate));
				var fdBudgetMoney = "";
				var fdDeduFlag=$("[name='fdDeduFlag']").val();
				var fdNoTaxMoney=$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val()||0;
				if(data[0].fdBudgetRate){
					if("2"==fdDeduFlag){  //不含税金额
						fdBudgetMoney = multiPoint(fdNoTaxMoney,data[0].fdBudgetRate);
					}else{
						fdBudgetMoney = multiPoint(fdApplyMoney,data[0].fdBudgetRate);
					}
				}
				if(!isNaN(fdBudgetMoney)){
					$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(fdBudgetMoney);
				}
				FSSC_ChangeMoney(null,null,index);
			}
		}
	}
    
    function  FSSC_ChangeIsDeduct(init){
    	//选择费用类型，回写天数计算规则
    	if(init&&init.isExpense=='1'){
          $("#fdDayCalType").val(init.fdDayCalType);
          //重新计算天数
			var sDate1 = $("#fdBeginDate1").val();
			var sDate2 =  $("#fdEndDate1").val();
			console.log(sDate1+"===="+sDate2)
			if(sDate1 != '' && sDate2 != ''){
				var aDate,oDate1,oDate2;
				aDate=sDate1.split("-");
				oDate1=new  Date(aDate[1]+'/'+aDate[2]+'/'+aDate[0]) //转换为xx/xx/xxxx格式
				aDate=sDate2.split("-")
				oDate2=new Date(aDate[1]+'/'+aDate[2]+'/'+aDate[0])
				if(oDate1>oDate2){
					$('#days1').val("");
					return ;
				}
				days = parseInt(Math.abs(oDate1 - oDate2)/1000/60/60/24) //把相差的毫秒数转换为天数
				//根据费用类型的计算规则来计算天数
				var fdDayCalType=$("#fdDayCalType").val();
				if(fdDayCalType&&fdDayCalType=='2'&&days>0){
					$('#days1').val(days+"天");
				}else{
					$('#days1').val((days+1)+"天");
				}
			}
		}
		var data = new KMSSData();
		data = data.AddBeanData("eopBasedataInputTaxService&authCurrent=true&fdExpenseItemId="+$("[name='fdExpenseItemId']").val());
		data = data.GetHashMapArray();
		if(data&&data.length>0){
			var fdApplyMoney = $("[name='fdApplyMoney']").val();
			if(!data[0].fdIsInputTax){
				$("[name=_fdIsDeduct][value=false]").parent().click();
				$("#fdInputTaxMoney").val(0);
				$("#fdInputTaxRate").val("");
				$("#fdNoTaxMoneyExpense").val(fdApplyMoney);
				$("#fdInputTaxMoney").prop("readonly",true);
				$("input[name='fdIsDeduct']").val('');
				if(fdApplyMoney){
					$("#fdNoTaxMoneyExpense").val(Number(fdApplyMoney).toFixed(2));//不含税金额
				}
				return;
			}
			$("[name=_fdIsDeduct][value=true]").parent().click();
			$("#fdInputTaxMoney").show();
			var fdNonDeductMoney = $("[name='fdNonDeductMoney']").val(); //不可抵扣金额
			if(!fdNonDeductMoney||isNaN(fdNonDeductMoney)){
				fdNonDeductMoney=0;
			}
			$("#fdInputTaxMoney").prop("readonly",false);
			var fdTaxMoney=numSub(fdApplyMoney,fdNonDeductMoney);
			var fdTaxRate = divPoint(data[0].fdTaxRate,100);
		    fdTaxMoney = multiPoint(divPoint(fdTaxMoney,numAdd(fdTaxRate,1.00)),fdTaxRate);
			$("#fdInputTaxMoney").val(fdTaxMoney);  	//进项税额
			$("#fdInputTaxRate").val(data[0].fdTaxRate);		//进项税率
			if(data[0].fdTaxRate&&fdNonDeductMoney>0){
				$("#fdNoTaxMoneyExpense").val(divPoint(numSub(fdApplyMoney,fdNonDeductMoney),numAdd(1,fdTaxRate)));//不含税金额
			}else{
				$("#fdNoTaxMoneyExpense").val(subPoint(fdApplyMoney,fdTaxMoney));//不含税金额
			}
			$("input[name='fdIsDeduct']").val('true');
		}
    }
    
    function  FSSC_ChangeIsDeductOnCheckbox(){
    	var fdIsDeduct = $("[name=fdIsDeduct]").val();
    	var fdExpenseItemId = $("[name='fdExpenseItemId']").val();
    	var fdApplyMoney = $("[name='fdApplyMoney']").val();
    	if(fdIsDeduct!=='true'){
			$("#fdInputTaxMoney").val("");
			$("#fdInputTaxMoney").val("");  	//进项税额
			$("#fdInputTaxRate").val("");		//进项税率
			$("#fdNoTaxMoneyExpense").val("");//不含税金额
			$(".fdIsDeduct").hide();
			$("#fdNoTaxMoneyExpense").val(fdApplyMoney)
			return;
    	}
    	$(".fdIsDeduct").show();
		var data = new KMSSData();
		data = data.AddBeanData("eopBasedataInputTaxService&authCurrent=true&fdExpenseItemId="+fdExpenseItemId);
		data = data.GetHashMapArray();
		if(data&&data.length>0){
			if(!data[0].fdIsInputTax){
				$("#fdNoTaxMoneyExpense").val(fdApplyMoney)
				return;
			}
			var fdNonDeductMoney = $("[name='fdNonDeductMoney']").val(); //不可抵扣金额
			if(!fdNonDeductMoney||isNaN(fdNonDeductMoney)){
				fdNonDeductMoney=0;
			}
			var fdTaxMoney=numSub(fdApplyMoney,fdNonDeductMoney);
			var fdTaxRate = divPoint(data[0].fdTaxRate,100);//(票面金额÷(1+税额)*税额)
		    fdTaxMoney = multiPoint(divPoint(fdTaxMoney,numAdd(fdTaxRate,1.00)),fdTaxRate);
			$("#fdInputTaxMoney").val(fdTaxMoney);  	//进项税额
			$("#fdInputTaxRate").val(data[0].fdTaxRate);		//进项税率
			if(data[0].fdTaxRate&&fdNonDeductMoney>0){
				$("#fdNoTaxMoneyExpense").val(numDiv(numSub(fdApplyMoney,fdNonDeductMoney),numAdd(1,fdTaxRate)));//不含税金额
			}else{
				$("#fdNoTaxMoneyExpense").val(numSub(fdApplyMoney,fdTaxMoney));//不含税金额
			}
		}
    }
    
    
    
    /*********************************************
	  * 修改进项税额，重新计算不含税金额
	  *********************************************/
	 function FSSC_CalculateNoTaxMoney(val,obj){
		 var fdApplyMoney=$("[name='fdApplyMoney']").val();  //申请金额
		 var fdInputTaxMoney=$("[name='fdInputTaxMoney']").val();  //进项税额
		 var fdNonDeductMoney = $("[name='fdNonDeductMoney']").val(); //不可抵扣金额
		 if(!fdNonDeductMoney||isNaN(fdNonDeductMoney)){
			fdNonDeductMoney=0;
		 }
		 var fdTaxRate=$("input[name='fdInputTaxRate']").val();
		 if(fdApplyMoney&&fdInputTaxMoney){
			if(data[0].fdTaxRate&&fdNonDeductMoney>0){
				$("#fdNoTaxMoneyExpense").val(numDiv(numSub(fdApplyMoney,fdNonDeductMoney),numAdd(1,numDiv(fdTaxRate,100))));//不含税金额
			}else{
				$("#fdNoTaxMoneyExpense").val(numSub(fdApplyMoney,fdInputTaxMoney));//不含税金额
			}
			reCalBudgetMoney();
		 }
	 }
    
	//修改报销明细金额
    function FSSC_ChangeMoney(v,e,index){
		var fdApplyMoney = $("#fdApplyMoney").val();
		var fdExchangeRate = $("#fdExchangeRate").val();
		var fdBudgetRate = $("#fdBudgetRate").val();
		if(!fdApplyMoney||isNaN(fdApplyMoney)){
			return;
		}
		var fdStandardMoney = "";
		if(fdExchangeRate){
			fdStandardMoney = multiPoint(fdApplyMoney,fdExchangeRate);
		}
		$("#fdStandardMoney").val(fdStandardMoney);
		//同步设置核准金额
		$("#fdApprovedApplyMoney").val(fdApplyMoney);
		$("#fdApprovedStandardMoney").val(fdStandardMoney);
		var fdInputTaxMoney=$("#fdInputTaxMoney").val();
		if(!fdInputTaxMoney){
			fdInputTaxMoney=0;
		}
		$("#fdNoTaxMoney").val(subPoint(fdApplyMoney,fdInputTaxMoney));
		FSSC_ChangeIsDeduct();
		reCalBudgetMoney();
	}
    
    function changeCurrency(){
    		var data = new KMSSData();
    		var fdCurrencyId = $("#fdCurrencyId").val();
    		var fdCompanyId = $("#fdCompanyId").val();
    		data.AddBeanData('eopBasedataExchangeRateService&authCurrent=true&type=getRateByCurrency&fdCurrencyId='+fdCurrencyId+'&fdCompanyId='+fdCompanyId);
    		data = data.GetHashMapArray();
		if(data.length>0){
			$("#fdExchangeRate").val(data[0].fdExchangeRate);
			$("#fdBudgetRate").val(data[0].fdBudgetRate);
			FSSC_ChangeMoney();
		}
    }
    
    /**
     * 选择所属行程
     * @returns
     */
    function selectTravel(){
		var li = document.getElementById("fdTravelListId").getElementsByTagName("li");
		var travelIdJson = [];
		var travelIds="";
		$("span[name='travelSubject']").each(function(){ 
			travelIds={
					"text":$(this).text()
				    }
			travelIdJson.push(travelIds);
		});
		var fdTravelId = document.getElementById("fdTravel");
		var picker = new Picker({
		      data:[travelIdJson]
	     });
	     picker.on('picker.select', function (selectedVal, selectedIndex) {
	    	 fdTravelId.value = travelIdJson[selectedIndex[0]].text;
	     });
	     picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
	    	 fdTravelId.value = travelIdJson[selectedIndex[0]].text;
	     });
	     picker.show();
	}

    /************************************************************************
     * 收款账户明细
     * **********************************************************************/
	 var accounEditInit = 0;//费用编辑类型，0,新增；1编辑
	 var accounId = 0;
    //新增
    function addAccountDetail(e) {
    		$(".ld-footer-account").css("display","flex");
    		var index = $("#fdAccountsListId>li").length;
    		$("[name='row-account-index']").val(index);
        $('.ld-addAccount-body').addClass('ld-addAccount-body-show')
        forbiddenScroll()
        //清空历史
        $("#fdBankId").val('');
        $("#fdAccountId").val('');
        $("#fdAccountName").val('');
        $("#fdBankAccount").val('');
        $("#fdBankName").val('');
        $("#fdMoney").val('');
        	var fdCompanyId = $("#fdCompanyId").val();
    		if(!fdCompanyId){
    			return;
    		}
    		var data = new KMSSData();
    		data.AddBeanData("eopBasedataCompanyService&type=getStandardCurrencyInfo&authCurrency=true&fdCompanyId="+fdCompanyId);
    		data = data.GetHashMapArray();
    		if(data&&data.length>0){
    			$("#fdCurrencyIdAccount").val(data[0].fdCurrencyId)
    			$("#fdCurrencyAccount").val(data[0].fdCurrencyName)
    			$("#fdExchangeRateAccount").val(data[0].fdExchangeRate)
    		}
    		data = new KMSSData();
    		data.AddBeanData("eopBasedataPayWayService&type=default&fdCompanyId="+fdCompanyId);
    		data = data.GetHashMapArray();
    		if(data&&data.length>0){
    			$("#fdPayWayId").val(data[0].value)
    			$("#fdPayWayName").val(data[0].text)
    			$("#fdBankId").val(data[0].fdBankId)
    			$("#fdIsTransfer").val(data[0].fdIsTransfer)
    		}
    		forbiddenScroll();
    		var fdIsTransfer = $("#fdIsTransfer").val();
    		initFS_GetFdIsTransfer(fdIsTransfer);
    }
    
    //初始化收款账户信息是否必填
    function initFS_GetFdIsTransfer(fdIsTransfer){
    	if(fdIsTransfer != 'false'){
    		$(".vat").show();
    	} else {
    		$(".vat").hide();
    	}
    }
    
    //保存收款账户信息
    function saveAccountDetail() {
    		var index = $('[name="row-account-index"]').val();
    	 var fdPayWayName = $("#fdPayWayName").val();
    	 var fdPayWayId = $("#fdPayWayId").val();
    	 var fdIsTransfer = $("#fdIsTransfer").val();
    	 var fdBankId = $("#fdBankId").val();
    	 var fdCurrencyId = $("#fdCurrencyIdAccount").val();
    	 var fdCurrency = $("#fdCurrencyAccount").val();
    	 var fdExchangeRate = $("#fdExchangeRateAccount").val();
    	 var fdAccountId = $("#fdAccountId").val();
    	 var fdAccountName = $("#fdAccountName").val();
    	 var fdBankAccount = $("#fdBankAccount").val();
    	 var fdBankName = $("#fdBankName").val();
    	 var fdAccountAreaName= $("#fdAccountAreaName").val();
    	 var fdMoney = $("#fdMoney").val();
    	 if(fdPayWayName==""){
    		 jqtoast("请选择付款方式");
    		 return ;
    	 }else if (fdMoney==""){
    		 jqtoast("请输入收款金额");
    		 return ;
    	 }
    	 if(fdIsTransfer !='false'){
    		 if (fdAccountName==""){
	    		 jqtoast("请输入账户名");
	    		 return ;
	    	 }else if (fdBankAccount==""){
	    		 jqtoast("请输入账号");
	    		 return ;
	    	 }else if (fdBankName==""){
	    		 jqtoast("请输入开户行");
	    		 return ;
	    	 }
	    	 else if (fdAccountAreaName==""){
	    		 jqtoast("请输入账户归属地");
	    		 return ;
	    	 }
    	 }
    	 var str = /\s+/g;
    	 if(str.test(fdBankAccount)){
    		 jqtoast("账号有空格，请重新输入");
    		 return ;
    	 }else if(str.test(fdBankName)){
    		 jqtoast("开户行有空格，请重新输入");
    		 return ;
    	 }
    	 var number = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;//两位小数且数字校验
    	 if(!number.test(fdMoney) ){
    		jqtoast("收款金额请填写数字，且只能是两位小数");
    		return ;
    	 }
    	 
    	 
    	 if(index==$("#fdAccountsListId>li").length){//新增
    		 DocList_AddRow("TABLE_DocList_fdAccountsList_Form");
    		var src =Com_Parameter.ContextPath+ "fssc/mobile/resource/images/collectionAccount.png";
     		var html1 = $("<div><img src="+src+" alt=''><span  class='accountName' >"+fdAccountName+"</span>" +
     				"<input name='accountId' value=\""+index+"\" hidden='true'></div><span onclick=\"deleteAccount()\"></span>") ;
     		var html2 = $("<div><p  class='fdBankAccount'>"+fdBankAccount+"</p></div>" +
     					"<div><span>收款金额：</span><div><span class='fdMoney'>"+fdMoney+"</span><span></span></div></div>");
     		var div1 = $("<div class=\"ld-newApplicationForm-account-top\"></div>").append(html1);
     		var div2 = $("<div class=\"ld-newApplicationForm-account-bottom\"></div>").append(html2);
     		var li = $("<li onclick='editAccountDetail()'></li>").append(div1,div2);
     		$("#fdAccountsListId").append(li);//拼接收款账户
    	 }else{
    		 var li = $("#fdAccountsListId>li").eq(index);
    		 li.find(".accountName").html(fdAccountName);
    		 li.find("[class='fdBankAccount']").html(fdBankAccount);
    		 li.find("[class='fdMoney']").html(fdMoney);
    	 }
    	 $("[name='fdAccountsList_Form["+index+"].fdBankId']").val(fdBankId);
    	 $("[name='fdAccountsList_Form["+index+"].fdPayWayId']").val(fdPayWayId);
    	 $("[name='fdAccountsList_Form["+index+"].fdPayWayName']").val(fdPayWayName);
    	 $("[name='fdAccountsList_Form["+index+"].fdCurrencyId']").val(fdCurrencyId);
    	 $("[name='fdAccountsList_Form["+index+"].fdCurrencyName']").val(fdCurrency);
    	 $("[name='fdAccountsList_Form["+index+"].fdAccountId']").val(fdAccountId);
    	 $("[name='fdAccountsList_Form["+index+"].fdAccountName']").val(fdAccountName);
    	 $("[name='fdAccountsList_Form["+index+"].fdBankAccount']").val(fdBankAccount);
    	 $("[name='fdAccountsList_Form["+index+"].fdBankName']").val(fdBankName);
    	 $("[name='fdAccountsList_Form["+index+"].fdMoney']").val(fdMoney);
    	 $("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").val(fdAccountAreaName); 
    	 $("[name='fdAccountsList_Form["+index+"].fdExchangeRate']").val(fdExchangeRate);
         $('.ld-addAccount-body').removeClass('ld-addAccount-body-show');
         $(".ld-footer-account").css("display","none");
         clearAccount_Div();
         ableScroll();
     }
     
     //取消收款账户明细
     function cancelAccount(){
	    	$('.ld-addAccount-body').removeClass('ld-addAccount-body-show');
	    	clearAccount_Div();
        ableScroll();
        $(".ld-footer-account").css("display","none");
    }
    
    //清除收款账户DIV信息
    function clearAccount_Div(){
    	$(".ld-addAccount-body-content").find('input').each(function(){
    		$(this).val('');
    	});
    }
     
       //编辑收款账户
        function editAccountDetail(e) {
        	e = e||window.event;
    		var ele = e.srcElement||e.target;
    		ele = DocListFunc_GetParentByTagName("LI",ele);
    		var index = $(ele).find("[name$=accountId]").val();
    		$("[name='row-account-index']").val(index);
    	  $("#fdBankId").val( $("[name='fdAccountsList_Form["+index+"].fdBankId']").val());
    	  $("#fdPayWayId").val($("[name='fdAccountsList_Form["+index+"].fdPayWayId']").val());
    	  $("#fdPayWayName").val($("[name='fdAccountsList_Form["+index+"].fdPayWayName']").val());
    	  $("#fdCurrencyIdAccount").val( $("[name='fdAccountsList_Form["+index+"].fdCurrencyId']").val());
    	  $("#fdCurrencyAccount").val( $("[name='fdAccountsList_Form["+index+"].fdCurrencyName']").val());
    	  $("#fdExchangeRateAccount").val($("[name='fdAccountsList_Form["+index+"].fdExchangeRate']").val());
    	  $("#fdAccountId").val($("[name='fdAccountsList_Form["+index+"].fdAccountId']").val());
    	  $("#fdAccountName").val( $("[name='fdAccountsList_Form["+index+"].fdAccountName']").val());
    	  $("#fdBankAccount").val($("[name='fdAccountsList_Form["+index+"].fdBankAccount']").val());
    	  $("#fdBankName").val($("[name='fdAccountsList_Form["+index+"].fdBankName']").val());
    	  $("#fdMoney").val($("[name='fdAccountsList_Form["+index+"].fdMoney']").val());
    	  $("#fdAccountAreaName").val($("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").val());
    	  $('.ld-addAccount-body').addClass('ld-addAccount-body-show')
    	  $(".ld-footer-account").css("display","flex");
    	  forbiddenScroll()
    	  var data = new KMSSData();
    	  var fdPayWayId = $("#fdPayWayId").val();
		  data.AddBeanData("eopBasedataPayWayService&fdPayWayId="+fdPayWayId);
		  data = data.GetHashMapArray();
		  if(data.length>0){
			  $("#fdIsTransfer").val(data[0].fdIsTransfer);
			  initFS_GetFdIsTransfer($("#fdIsTransfer").val());//初始化收款账户信息是否必填
		  }
      }
    
       //删除收款账户
       function deleteAccount(e) {
    	   e = e||window.event;
	   		var ele = e.srcElement||e.target;
	   		ele = DocListFunc_GetParentByTagName("LI",ele);
	   		var index = $(ele).find("[name$='accountId']").val();
	   		$(ele).remove();
           	DocList_DeleteRow($("#TABLE_DocList_fdAccountsList_Form>tbody>tr").get(index));
           	e.stopPropagation?e.stopPropagation():(e.cancelBubble = true);
           	$("#fdAccountsListId>li").each(function(i){
           		$(this).find("[name$='accountId']").val(i);
           	})
      };
      
    
    /**
     * 选择付款方式
     * @returns
     */
     function selectPayWay(){
    	 var fdCompanyId = $("[name='fdCompanyId']").val();
    	 if(fdCompanyId==""){
    		 jqtoast("请选择公司");
    		 return false;
    	 }
    	 var url =Com_Parameter.ContextPath + formOption['url']['getFsBasePay'];
    	 $.ajax({
             type: 'post',
             url:url,
             data: {"fdCompanyId":fdCompanyId,"keyword":""},
         }).success(function (data) {
	      	    var rtn = JSON.parse(data);
	      	    if(rtn.result=='success'){
		      	     var fdPayWayData = rtn.data;
		      	     console.log(fdPayWayData);
		      	     var fdPayWayName = document.getElementById('fdPayWayName');
		      	     var fdPayWayId = document.getElementById('fdPayWayId');
			      	 var picker = new Picker({
				        data:[fdPayWayData]
				     });
				     picker.on('picker.select', function (selectedVal, selectedIndex) {
				    	 fdPayWayId.value = fdPayWayData[selectedIndex[0]].value;
				    	 fdPayWayName.value = fdPayWayData[selectedIndex[0]].name;
				    	 $("#fdBankId").val(fdPayWayData[selectedIndex[0]].fdBankId);

				     });
				     picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
				    	 fdPayWayId.value = fdPayWayData[selectedIndex[0]].value;
				    	 fdPayWayName.value = fdPayWayData[selectedIndex[0]].name;
				    	 $("#fdBankId").val(fdPayWayData[selectedIndex[0]].fdBankId);
				      });
			    	  picker.show();
	      	 } else {
	      		jqtoast("获取付款方式失败！");
	      	 }
         }).error(function () {
	           console.log("获取付款方式");
	   	 })
     }
     
    /**
     * 选择收款账户
     * @returns
     */
     function selectAccount(){
    	 var fdCompanyId = $("[name='fdCompanyId']").val();
    	 if(fdCompanyId==""){
    		 jqtoast("请选择公司");
    		 return false;
    	 }
    	 var url =Com_Parameter.ContextPath +  formOption['url']['getAccountInfo'];
    	 $.ajax({
             type: 'post',
             url:url,
             data: {"fdCompanyId":fdCompanyId,"keyword":""},
         }).success(function (data) {
	      	    var rtn = JSON.parse(data);
	      	    if(rtn.result=='success'){
		      	     var fdAccountData = rtn.data;
		      	     console.log(fdAccountData);
		      	     var fdAccountName = document.getElementById('fdAccountName');
		      	     var fdAccountId = document.getElementById('fdAccountId');
		      	     var index = 0;
			  	     if(fdAccountId.value){
			  	    	 	for(var i=0;i<fdAccountData.length;i++){
			  	    	 		if(fdAccountId.value == fdAccountData[i].value){
			  	    	 			index = i;
			  	    	 			break;
			  	    	 		}
			  	    	 	}
			  	     }
			      	 var picker = new Picker({
				        data:[fdAccountData],
				        selectedIndex:[index]
				     });
				     picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
				    	 fdAccountName.value = fdAccountData[selectedIndex[0]].fdName;
				    	 fdAccountId.value = fdAccountData[selectedIndex[0]].value;
				    	 $("#fdBankAccount").val(fdAccountData[selectedIndex[0]].fdPayeeAccount);
				    	 $("#fdBankName").val(fdAccountData[selectedIndex[0]].accountName);
				      });
			    	  picker.show();
	      	 } else {
	      		jqtoast("获取收款账户失败！");
	      	 }
         }).error(function () {
	           console.log("获取收款账户失败");
	   	 })
     }
     //选择付款方式默认带出对应的银行
     function afterSelectPayWay(data){
    	 $("#fdBankId").val(data.fdBankId);
    	 $("#fdIsTransfer").val(data.fdIsTransfer);
    	 initFS_GetFdIsTransfer($("#fdIsTransfer").val());
     }
     
     
     function afterSelectAccount(data){
    	 $("#fdBankAccount").val(data.fdPayeeAccount);
    	 $("#fdBankName").val(data.accountName);
    	 $("#fdAccountName").val(data.text.split("|")[0]);
    	 $("#fdAccountAreaName").val(data.fdAccountAreaName);
     }
     
	 
	 /**
	  * 选择币种
	  * @returns
	  */
	 function selectAccountCurrency(type){
		 var fdCompanyId = $("[name='fdCompanyId']").val();
    	 if(fdCompanyId==""){
    		 jqtoast("请选择公司");
    		 return false;
    	 }
    	 var inIds = [];
    	 $("#TABLE_DocList_fdDetailList_Form [name$=fdCurrencyId]").each(function(){
    		 inIds.push(this.value);
    	 })
    	 $.ajax({
             type: 'post',
             url:Com_Parameter.ContextPath + formOption['url']['getCurrencyData'],
             data: {"fdCompanyId":fdCompanyId,"keyword":"",inIds:inIds.join(';'),type:type},
         }).success(function (data) {
	      	    var rtn = JSON.parse(data);
	      	    if(rtn.result=='success'){
		      	     var fdCurrencyData = rtn.data;
		      	     var fdCurrency = document.getElementById('fdCurrencyAccount');
		      	     var fdCurrencyId = document.getElementById('fdCurrencyIdAccount');
		      	     var index = 0;
			  	     if(fdCurrencyId.value){
			  	    	 	for(var i=0;i<fdCurrencyData.length;i++){
			  	    	 		if(fdCurrencyId.value == fdCurrencyData[i].value){
			  	    	 			index = i;
			  	    	 			break;
			  	    	 		}
			  	    	 	}
			  	     }
			  	     //由于picker.js每次只是隐藏，下次重新创建导致无法通过id绑定控件，所以每次新建前清除上一次的对象
			      	 $(".picker").remove();
			      	 var picker = new Picker({
				        data:[fdCurrencyData],
				        selectedIndex:[index]
				     });
				     picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
				    	 fdCurrency.value = fdCurrencyData[selectedIndex[0]].text;
				    	 fdCurrencyId.value = fdCurrencyData[selectedIndex[0]].value;
				    	 getExchangeRate(fdCurrencyData[selectedIndex[0]].value,fdCompanyId);
				      });
			    	  picker.show();
			    	  //回车搜索
	    	          $("#search_input").keypress(function (e) {
	    	              if (e.which == 13) {
	    	              	var keyword=$("[name='pick_keyword']").val();
	    	              	if(keyword){
	    	              		$.ajax({
	    	              	           type: 'post',
	    	              	           url:Com_Parameter.ContextPath + formOption['url']['getCurrencyData'],
	    	              	           data: {"fdCompanyId":fdCompanyId,inIds:inIds.join(';'),keyword:keyword},
	    	              	       }).success(function (data) {
	    	              	    	   console.log('获取信息成功');
	    	              	    	   var rtn = JSON.parse(data);
	    	              	    	   picker.refillColumn(0, rtn.data);
	    	              	    	   fdCurrencyData=rtn.data;
	    	              	       }).error(function (data) {
	    	              	    	   console.log('获取信息失败');
	    	              	       })
	    	              	}
	    	              }
	    	      	});
	    	        //获取到焦点
	    	        $("#search_input").focus(function(){
	    	      		$(".weui-icon-clear").attr("style","display:block;");
	    	      	}) 
	    		      //取消
	    	          $(".weui-icon-clear").click(function (e) {
	    	          	$.ajax({
	    	   	           type: 'post',
	    	   	           url:Com_Parameter.ContextPath + formOption['url']['getCurrencyData'],
	    	   	           data: {"fdCompanyId":fdCompanyId,"keyword":"",inIds:inIds.join(';')},
	    	   	       }).success(function (data) {
	    	   	    	   console.log('获取分类信息成功');
	    	   	    	   var rtn = JSON.parse(data);
	    	   	    	  picker.refillColumn(0, rtn.data);
	    	   	    	  objData=rtn.data;
	    	   	    	  $("[name='pick_keyword']").val('');
	    	   	    	$(".weui-icon-clear").hide()
	    	   	       }).error(function (data) {
	    	   	    	   console.log('获取分类信息失败');
	    	   	       })
	    	      	});
	      	 } else {
	      		jqtoast("获取货币失败");
	      	 }
         })
	 }
	 
	 function getExchangeRate(fdCurrencyId,fdCompanyId){
		data = new KMSSData();
		console.log(fdCurrencyId);
		data = data.AddBeanData('eopBasedataExchangeRateService&type=getRateByCurrency&authCurrent=true&fdCurrencyId='+fdCurrencyId+'&fdCompanyId='+fdCompanyId).GetHashMapArray();
		if(data.length>0){
			console.log(data);
			$("#fdExchangeRateAccount").val(data[0].fdExchangeRate)
		}
	 }

	/**********************************************************************
	 * 交易数据明细
	 * **********************************************************************/
	 function selectTranData(){
		document.body.style.overflow = 'hidden'
		var src = Com_Parameter.ContextPath+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=selectTranData&fdPersonId='+$("[name=fdClaimantId]").val();
		var h = $(window).height();
		layer.open({
			type: 1,
			content: '<iframe src="'+src+'" height="'+h+'px" width="100%" id="selectTranDataIframe"></iframe>',
			anim: 'up',
			style: 'position:fixed; left:0; top:0; width:100%; height:100%; border: none; overflow: auto;'
		});
	 }

	 function selectTranDataList(ids){
		 document.body.style.overflow = 'auto'
		 $(".ld-remember-modal").removeClass('ld-remember-modal-show');
		 layer.closeAll();
		 if(!ids){
			 return;
		 }
		 $.ajax({
			 type: 'post',
			 url:Com_Parameter.ContextPath+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=selectTranData',
			 data: {"ids":ids},
		 }).success(function (data) {
			 var rtn = JSON.parse(data);
			 if(rtn.result=='success'){
				 var arr = rtn.data;
				 //新增交易数据
				 if(arr.length > 0){
					 for(var i=0;i<arr.length;i++){
						 addTranDataDeteil(arr[i]);
					 }
				 }
			 }
		 })
	 }

	 function addTranDataDeteil(tranData){
		 var detail = $("#TABLE_DocList_fdTranDataList_Form>tbody>tr [name$=fdTranDataId][value="+tranData['fdId']+"]");
		 if(detail.length>0){//交易数据已存在，跳过
			 return;
		 }
		 var index = $("#fdTranDataListId>li").length;
		 DocList_AddRow("TABLE_DocList_fdTranDataList_Form");//新增一行明细行
		 appendTranDataHtml(index,tranData['fdId'],tranData['fdCrdNum'],tranData['fdActChiNam'],tranData['fdTrsDte'],tranData['fdOriCurAmt']||0,tranData['fdTrsCod']);//拼接显示内容
		 //给明细行赋值
		 $("[name='fdTranDataList_Form["+index+"].fdTranDataId']").val(tranData['fdId']);
		 $("[name='fdTranDataList_Form["+index+"].fdCrdNum']").val(tranData['fdCrdNum']);
		 $("[name='fdTranDataList_Form["+index+"].fdActChiNam']").val(tranData['fdActChiNam']);
		 $("[name='fdTranDataList_Form["+index+"].fdTrsDte']").val(tranData['fdTrsDte']);
		 $("[name='fdTranDataList_Form["+index+"].fdTrxTim']").val(tranData['fdTrxTim']);
		 $("[name='fdTranDataList_Form["+index+"].fdOriCurAmt']").val(tranData['fdOriCurAmt']);
		 $("[name='fdTranDataList_Form["+index+"].fdOriCurCod']").val(tranData['fdOriCurCod']);
		 $("[name='fdTranDataList_Form["+index+"].fdTrsCod']").val(tranData['fdTrsCod']);
		 $("[name='fdTranDataList_Form["+index+"].fdState']").val(tranData['fdState']);
	 }

	 //拼接交易数据
	 function appendTranDataHtml(index,fdTranDataId,fdCrdNum,fdActChiNam,fdTrsDte,fdOriCurAmt,fdTrsCod){
		 var src =Com_Parameter.ContextPath+ "fssc/mobile/resource/images/persionBorrow.png";
		 var html1 = $("<div><img src="+src+" alt=''><span class='fdActChiNam' >"+fdActChiNam+"</span><span class='ld-verticalLine'></span><span class='fdTrsDte'>"+fdTrsDte+"</span>" +
			 "<input name='index' value=\""+index+"\" hidden='true'><input name='fdTranDataId' value=\""+fdTranDataId+"\" hidden='true'><input name='fdTrsCod' value=\""+fdTrsCod+"\" hidden='true'></div><span onclick=\"deleteTranData()\"></span>") ;
		 var html2 = $("<div><p class='fdCrdNum'>"+fdCrdNum+"</p></div>" +
			 "<div><span>交易金额：</span><div><span class='fdOriCurAmt'>"+fdOriCurAmt+"</span><span></span></div></div>");
		 var div1 = $("<div class=\"ld-newApplicationForm-account-top\"></div>").append(html1);
		 var div2 = $("<div class=\"ld-newApplicationForm-account-bottom\"></div>").append(html2);
		 var li = $("<li class=\"ld-notSubmit-list-item\"></li>").append(div1,div2);
		 $("#fdTranDataListId").append(li);//拼接明细
	 }

 	 //删除交易数据
	 function deleteTranData(e) {
		 e = e||window.event;
		 var ele = e.srcElement||e.target;
		 ele = DocListFunc_GetParentByTagName("LI",ele);
		 var index = $(ele).find("[name$='index']").val();
		 $(ele).remove();
		 DocList_DeleteRow($("#TABLE_DocList_fdTranDataList_Form>tbody>tr").get(index));
		 e.stopPropagation?e.stopPropagation():(e.cancelBubble = true);
		 $("#fdTranDataListId>li").each(function(i){
			 $(this).find("[name$='index']").val(i);
		 })
	 };

 	/**********************************************************************
     * 发票明细
     * **********************************************************************/
    var invioceEditInit = 0;//费用编辑类型，0,新增；1编辑
	var invioceId = 0;
     //新增发票明细
    function addInvioceDeteil(flag) {
    	var index = $("#fdInvoiceListId>li").length;
    	$("[name='row-invoice-index']").val(index);
    	$(".ld-footer-invoice").css("display",'flex');
    	getTime('fdInvoiceDate','fdInvoiceDate','');
		$('.ld-travel-detail-body').addClass('ld-travel-detail-body-show')
        forbiddenScroll()
    	selectTime('fdInvoiceDate','fdInvoiceDate','');
    	var arr=["fdInvoiceNumber","fdInvoiceType","fdInvoiceTypeId","fdInvoiceCode","fdInvoiceMoney","fdTaxValue","fdNoTaxMoney",
    	        "fdTaxMoney","fdCheckCode","fdCheckStatus","fdState","fdPurchName","fdTaxNumber"];
    	for(var i=0;i<arr.length;i++){
    	    $("#"+arr[i]).val("");
    	}
		forbiddenScroll()
    }
    
    //保存发票明细
    function saveInvoiceDetail() {
    	var index = $("[name='row-invoice-index']").val();
      	var fdInvoiceTypeId = $("#fdInvoiceTypeId").val();
      	var fdInvoiceType = $("#fdInvoiceType").val();
    	var fdInvoiceNumber = $("#fdInvoiceNumber").val();
    	var fdExpenseTypeId = $("#fdExpenseTypeId").val();
    	var fdExpenseTypeName = $("#fdExpenseType").val();
    	var fdInvoiceCode = $("#fdInvoiceCode").val();
    	var fdInvoiceDate = $("#fdInvoiceDate").val();
    	var fdInvoiceMoney = $("#fdInvoiceMoney").val();
    	var fdTaxValue = $("#fdTaxValue").val();
    	var fdTaxMoney = $("#fdTaxMoney").val();
    	var fdNoTaxMoney = $("#fdNoTaxMoney").val();
    	var fdNonDeductMoney =$("#fdNonDeductMoney").val();
    	var number = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;//两位小数且数字校验
    	
    	if(!fdInvoiceType){
    		jqtoast("发票类型不能为空");
    		return ;
    	}else if(!fdInvoiceNumber){
			jqtoast("发票号码不能为空");
			return ;
		}else if(!fdInvoiceMoney){
			jqtoast("税价合计不能为空");
			return ;
		}else if(!number.test(fdInvoiceMoney)){
			jqtoast("税价合计请填写数字，且只能是两位小数");
			return ;
		}
    	if("10100"==fdInvoiceTypeId||"30100"==fdInvoiceTypeId){
			if(!fdInvoiceCode){
				jqtoast("发票代码不能为空");
				return ;
			}  else if(!fdInvoiceDate){
				jqtoast("发票日期不能为空");
				return ;
			}else if(!fdTaxValue){
				jqtoast("税率不能为空");
				return ;
			}
		}
	 	var param_data = {
                fdCompanyId:$("input[name='fdCompanyId']").val(),
                fdInvoiceType:$("#fdInvoiceTypeId").val(),
                fdTaxNumber:$("#fdTaxNumber").val(),
                fdPurchName:$("#fdPurchName").val()
        }
        $.ajax({
                type: 'post',
                async:false,
                url:Com_Parameter.ContextPath+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=checkTaxNumberAndPurchName',
                data:{params:JSON.stringify(param_data)},
        }).success(function (data) {
            var rtn = JSON.parse(data);
            var fdCheckStatus=$("#fdCheckStatus").val();
            //新增
            if(index == $("#fdInvoiceListId>li").length) {
                DocList_AddRow("TABLE_DocList_fdInvoiceList_Form");
                addInvioceDeteilHtml(index,fdInvoiceType,fdInvoiceNumber,fdInvoiceMoney,fdCheckStatus,rtn.fdIsCurrent);
            } else {//编辑
                var li = $("#fdInvoiceListId>li").eq(index);
                li.find(".selectViewText").html(fdInvoiceType);
                li.find(".fdIsVatName").html(fdInvoiceType);
                li.find(".fdInvoiceNumber").html(fdInvoiceNumber);
                li.find(".fdInvoiceMoney").html(fdInvoiceMoney);
                li.find(".fdInvoiceType").find("option[value="+fdInvoiceTypeId+"]").prop("selected", true);
            }
            $("[name='fdInvoiceList_Form["+index+"].fdInvoiceNumber']").val(fdInvoiceNumber);
            $("[name='fdInvoiceList_Form["+index+"].fdExpenseTypeId']").val(fdExpenseTypeId);
            $("[name='fdInvoiceList_Form["+index+"].fdExpenseTypeName']").val(fdExpenseTypeName);
            $("[name='fdInvoiceList_Form["+index+"].fdInvoiceCode']").val(fdInvoiceCode);
            $("[name='fdInvoiceList_Form["+index+"].fdInvoiceDate']").val(fdInvoiceDate);
            $("[name='fdInvoiceList_Form["+index+"].fdInvoiceMoney']").val(fdInvoiceMoney);
            $("[name='fdInvoiceList_Form["+index+"].fdTax']").val(fdTaxValue);
            $("[name='fdInvoiceList_Form["+index+"].fdTaxMoney']").val(fdTaxMoney);
            $("[name='fdInvoiceList_Form["+index+"].fdNoTaxMoney']").val(fdNoTaxMoney);
            $("[name='fdInvoiceList_Form["+index+"].fdCheckCode']").val($("#fdCheckCode").val());
            $("[name='fdInvoiceList_Form["+index+"].fdPurchName']").val($("#fdPurchName").val());
            $("[name='fdInvoiceList_Form["+index+"].fdTaxNumber']").val($("#fdTaxNumber").val());
            $("[name='fdInvoiceList_Form["+index+"].fdInvoiceType']").find("option[value="+fdInvoiceTypeId+"]").prop("selected", true);
            $("[name='fdInvoiceList_Form["+index+"].fdCheckStatus']").val(fdCheckStatus);
            $("[name='fdInvoiceList_Form["+index+"].fdState']").val($("#fdState").val());
            $("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").val(rtn.fdIsCurrent);
            if(!fdCheckStatus){
                fdCheckStatus="0";
            }
            $("#fdInvoiceListId").find("li").eq(index).find("[class*=ld-newApplicationForm-travelInfo-top-buget]").eq(0).addClass("ld-newApplicationForm-travelInfo-top-buget-"+fdCheckStatus);
            $("#fdInvoiceListId").find("li").eq(index).find("[class*=ld-newApplicationForm-travelInfo-top-buget]").eq(0).removeClass("ld-newApplicationForm-travelInfo-top-buget-"+(fdCheckStatus=="0"?"1":"0"));
            $("#fdInvoiceListId").find("li").eq(index).find("[class*=ld-newApplicationForm-travelInfo-top-buget]").eq(0).html(fsscLang['fssc-mobile:invoice.satuts.'+fdCheckStatus]);
            if(rtn.fdIsCurrent){
                $("#fdInvoiceListId").find("li").eq(index).find("[class*=ld-newApplicationForm-travelInfo-top-buget]").show();
                $("#fdInvoiceListId").find("li").eq(index).find("[class*=ld-newApplicationForm-travelInfo-top-buget]").eq(1).addClass("ld-newApplicationForm-travelInfo-top-buget-"+rtn.fdIsCurrent);
                $("#fdInvoiceListId").find("li").eq(index).find("[class*=ld-newApplicationForm-travelInfo-top-buget]").eq(1).removeClass("ld-newApplicationForm-travelInfo-top-buget-"+(rtn.fdIsCurrent=="0"?"1":"0"));
                $("#fdInvoiceListId").find("li").eq(index).find("[class*=ld-newApplicationForm-travelInfo-top-buget]").eq(1).html(fsscLang['fssc-mobile:invoice.is.current']);
            }else{
                $("#fdInvoiceListId").find("li").eq(index).find("[class*=ld-newApplicationForm-travelInfo-top-buget]").hide();
            }
            $('.ld-travel-detail-body').removeClass('ld-travel-detail-body-show')
            $(".ld-footer-invoice").css("display",'none');
			$(".isVat").hide();//隐藏专票必填字段提示
            ableScroll()
        });
    }
     
     //拼接发票
     function addInvioceDeteilHtml(index,fdInvoiceType,fdInvoiceNumber,fdInvoiceMoney,fdCheckStatus,fdIsCurrent){
    	var checkStatusMsg = fsscLang['fssc-mobile:invoice.satuts.0'];
 		if(fdCheckStatus=='1'){
 			checkStatusMsg=fsscLang['fssc-mobile:invoice.satuts.1'];
 		}
    	var src =Com_Parameter.ContextPath+ "fssc/mobile/resource/images/specialTicket.png";
		var html1 = "<div><img src="+src+" alt=''><span  class='fdIsVatName' >"+fdInvoiceType+"</span>" +
				"<span class='ld-newApplicationForm-travelInfo-top-buget-"+(fdCheckStatus||0)+"'>"+checkStatusMsg+"</span>";
		if(fdIsCurrent){
            html1+="<span class=\"ld-newApplicationForm-travelInfo-top-buget-"+fdIsCurrent+"\">"+fsscLang["fssc-mobile:invoice.is.current"]+"</span>";
        }
		html1+="<input name='invioceId' value="+index+" type='hidden'></div><i  onclick=\"deleteInvioce()\"></i>" ;
		var html2 = $("<span class='fdInvoiceNumber'>"+fdInvoiceNumber+"</span>" +
				"<div><span class='fdInvoiceMoney'>"+fdInvoiceMoney+"</span><span></span></div>");
		var divTop = $("<div class=\"ld-newApplicationForm-invioce-top\"></div>").append(html1);
		var divbottom = $("<div class=\"ld-newApplicationForm-invioce-bottom\"></div>").append(html2);
		var li = $("<li onclick=\"editInvioce()\"></li>").append(divTop,divbottom);
		$("#fdInvoiceListId").append(li);//拼接发票
    }
     
     //选择随手记带出的发票
     function addInvoiceByNote(invoice){
    	var index = $("#fdInvoiceListId>li").length;
    	addInvioceDeteilHtml(index,invoice['fdInvoiceType'],invoice['fdInvoiceNo'],invoice['fdInvoiceMoney'],invoice['fdCheckStatus'],invoice['fdIsCurrent']);
    	DocList_AddRow("TABLE_DocList_fdInvoiceList_Form");
    	$("[name='fdInvoiceList_Form["+index+"].fdInvoiceNumber']").val(invoice.fdInvoiceNo);
 		$("[name='fdInvoiceList_Form["+index+"].fdInvoiceCode']").val(invoice.fdInvoiceCode);
 		$("[name='fdInvoiceList_Form["+index+"].fdInvoiceDate']").val(invoice.fdInvoiceDate);
 		$("[name='fdInvoiceList_Form["+index+"].fdCheckCode']").val(invoice.fdCheckCode);
 		$("[name='fdInvoiceList_Form["+index+"].fdPurchName']").val(invoice.fdPurchName);
 		$("[name='fdInvoiceList_Form["+index+"].fdTaxNumber']").val(invoice.fdTaxNumber);
 		$("[name='fdInvoiceList_Form["+index+"].fdInvoiceMoney']").val(invoice.fdInvoiceMoney);
 		$("[name='fdInvoiceList_Form["+index+"].fdTaxMoney']").val(invoice.fdTax);
 		var fdTaxRate = numDiv(invoice.fdTax?invoice.fdTax:0,invoice.fdNoTax)*100;
 		$("[name='fdInvoiceList_Form["+index+"].fdTax']").val(fdTaxRate.toFixed(2));
 		$("[name='fdInvoiceList_Form["+index+"].fdNoTaxMoney']").val(invoice.fdNoTax);
 		$("[name='fdInvoiceList_Form["+index+"].fdCheckStatus']").val(invoice.fdCheckStatus);
 		$("[name='fdInvoiceList_Form["+index+"].fdState']").val(invoice.fdState);
 		$("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").val(invoice.fdIsCurrent);
 		$("[name='fdInvoiceList_Form["+index+"].fdInvoiceType']").find("option[value="+invoice.fdInvoiceTypeId+"]").prop("selected", true);
     }
    
    
     //编辑发票
	function editInvioce(e) {
		e = e||window.event;
		var ele = e.srcElement||e.target;
		ele = DocListFunc_GetParentByTagName("LI",ele);
		var index = $(ele).find("[name$=invioceId]").val();
		$("[name='row-invoice-index']").val(index);
		$(".ld-footer-invoice").css("display",'flex');
		var fdInvoiceTypeName = $("[name='fdInvoiceList_Form["+index+"].fdInvoiceType").find("option:selected").text();
		var fdInvoiceTypeId = $("[name='fdInvoiceList_Form["+index+"].fdInvoiceType").val();
		if("10100"==fdInvoiceTypeId||"30100"==fdInvoiceTypeId){
			$(".isVat").show();
		}
		$("#fdInvoiceType").val(fdInvoiceTypeName);
     	$("#fdInvoiceTypeId").val(fdInvoiceTypeId);
     	$("#fdExpenseType").val($("[name='fdInvoiceList_Form["+index+"].fdExpenseTypeName']").val());
     	$("#fdTaxValue").val($("[name='fdInvoiceList_Form["+index+"].fdTax']").val());
     	var inputArr=["fdExpenseTypeId","fdTravel","fdInvoiceNumber","fdInvoiceCode",
     		"fdCheckCode","fdInvoiceDate","fdInvoiceMoney","fdTaxMoney","fdNoTaxMoney",
     		"fdCheckStatus","fdState","fdPurchName","fdTaxNumber"];
     	for(var n=0;n<inputArr.length;n++){
     		$("#"+inputArr[n]).val($("[name='fdInvoiceList_Form["+index+"]."+inputArr[n]+"']").val());
     	}
     	$('.ld-travel-detail-body').addClass('ld-travel-detail-body-show')
     	forbiddenScroll()
	}
	
	function cancelInvoiceDetail(){
		$('.ld-travel-detail-body').removeClass('ld-travel-detail-body-show')
        $(".ld-footer-invoice").css("display",'none');
        ableScroll()
	}
      
      //删除发票
	function deleteInvioce(e) {
    	e = e||window.event;
		var ele = e.srcElement||e.target;
  		ele = DocListFunc_GetParentByTagName("LI",ele);
  		var index = $(ele).find("[name$=invioceId]").val();
  		DocList_DeleteRow($("#TABLE_DocList_fdInvoiceList_Form>tbody>tr").get(index));
  		$(ele).remove();
  		e.stopPropagation?e.stopPropagation():(e.cancelBubble = true);
  		refreshInvoiceIndex();
	}
	
	function refreshInvoiceIndex(){
		$("#fdInvoiceListId>li [name$=invioceId]").each(function(i){
			this.value = i;
		})
	}
	
     //获取税率
     function selectTaxrate(){
    	 var fdTaxRate =  $("#fdTaxValue").val();
    	 var fdInvoiceMoney = $("#fdInvoiceMoney").val();
    	 var number = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
    	 if(fdInvoiceMoney=="" || fdTaxRate==""){
    		 return ; 
    	 }
    	 if(!number.test(fdInvoiceMoney)){
    		 jqtoast("价税合计请填写数字，且只能是两位小数");
     		 return ; 
    	 }else if(!number.test(fdTaxRate)){
    		 jqtoast("税率请填写数字，且只能是两位小数");
      		 return ; 
    	 }
    	 var fdTax = (fdInvoiceMoney/(1+fdTaxRate/100.00)*fdTaxRate/100.00).toFixed(2);
		 $("#fdTaxMoney").val(fdTax);
		 $("#fdNoTaxMoney").val((fdInvoiceMoney-fdTax).toFixed(2));
    	 
     }
     
       

    
    /**
	 * 发票明细，获取发票类型
	 * @returns
	 */
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
			 var fdInvoiceType = document.getElementById('fdInvoiceType');
	  	     var fdInvoiceTypeId = document.getElementById('fdInvoiceTypeId');
	  	     var index = 0;
	  	     if(fdInvoiceTypeId.value){
	  	    	 	for(var i=0;i<fdInvoiceItemData.length;i++){
	  	    	 		if(fdInvoiceTypeId.value == fdInvoiceItemData[i].value){
	  	    	 			index = i;
	  	    	 			break;
	  	    	 		}
	  	    	 	}
	  	     }
	  	     //由于picker.js每次只是隐藏，下次重新创建导致无法通过id绑定控件，所以每次新建前清除上一次的对象
	      	 $(".picker").remove();
			 var picker = new Picker({
		        data:[fdInvoiceItemData],
		        selectedIndex:[index]
		     });
		     picker.on('picker.select', function (selectedVal, selectedIndex) {
		    	 fdInvoiceType.value = fdInvoiceItemData[selectedIndex[0]].text;
		    	 fdInvoiceTypeId.value = fdInvoiceItemData[selectedIndex[0]].value;
		    	 if("10100"==fdInvoiceItemData[selectedIndex[0]].value||"30100"==fdInvoiceItemData[selectedIndex[0]].value){//显示专票必填信息
					$(".isVat").show();
				 }else{//隐藏专票字段的必填提示
					 $(".isVat").hide();
				 }
		     });
		     picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
		    	 fdInvoiceType.value = fdInvoiceItemData[selectedIndex[0]].text;
		    	 fdInvoiceTypeId.value = fdInvoiceItemData[selectedIndex[0]].value;
		      });
	    	  picker.show();
	    	  //回车搜索
	          $("#search_input").keypress(function (e) {
	              if (e.which == 13) {
	              	var keyword=$("[name='pick_keyword']").val();
	              	if(keyword){
	              		var searchData=[];
	              		for(var i=0;i<fdInvoiceItemData.length;i++){
	              			if(fdInvoiceItemData[i].text.indexOf(keyword)>-1){
	              				searchData.push(fdInvoiceItemData[i]);
	              			}
	              		}
	              		picker.refillColumn(0, searchData);
	              		fdInvoiceItemData=searchData;
	              	}
	              }
	      	});
	          //获取到焦点
	          $("#search_input").focus(function(){
	      		$(".weui-icon-clear").attr("style","display:block;");
	      	}) 
		      //取消
	          $(".weui-icon-clear").click(function (e) {
	   	    	  picker.refillColumn(0, fdInvoiceItemData);
	   	    	  $("[name='pick_keyword']").val('');
	      	});
	 }
	 
	 
	 /******************************************************************
	  * 冲抵借款明细
	  * *****************************************************************/
	//加载冲抵借款明细
	window.FSSC_LoadLoanInfo = function(){
		var fdId = Com_GetUrlParameter(window.location.href,'fdId');
		$("#fdOffsetListId li").remove();
		var len = $("#TABLE_DocList_fdOffsetList_Form  > tbody > tr").length;
		for(var i=0 ;i<len;i++){
			DocList_DeleteRow1("TABLE_DocList_fdOffsetList_Form",i);
		}
		$.post(
			Com_Parameter.ContextPath+formOption['url']['getLoanData'],
			{fdId:fdId,fdPersonId:$("[name=fdClaimantId]").val(),fdCompanyId:$("[name=fdCompanyId]").val(),flag:"mobile"},
			function(data){
				 var rtn = JSON.parse(data);
				 appendLoan(rtn.data);
			}
		);
	}
	//自动计算剩余金额
	window.FSSC_GetLeftMoney = function(v,e){
		var index = e.name.replace(/\S+\[(\d+)\]\S+/g,'$1');
		var fdOffsetMoney = $("[name='fdOffsetList_Form["+index+"].fdOffsetMoney']").val();
		var fdCanOffsetMoney = $("[name='fdOffsetList_Form["+index+"].fdCanOffsetMoney']").val();
		var fdLeftMoney = numSub(fdCanOffsetMoney,fdOffsetMoney)*1;
		
		if(isNaN(fdLeftMoney)){
			return;
		}
		$("[name='fdOffsetList_Form["+index+"].fdLeftMoney']").val(fdLeftMoney.toFixed(2));
	}
	
	window.FSSC_ChangeIsOffsetLoan = function(){
		var fdIsOffsetLoan = $("[name=fdIsOffsetLoan]").val();
		if(fdIsOffsetLoan=='true'){
			$("#fdOffsetListId").show();
		}else{
			$("#fdOffsetListId").hide();
		}
	}
	
	$(function(){
		//若有借款明细，则加载
		setTimeout(function(){ 
			if(Com_GetUrlParameter(window.location.href,'method')=='add'){
				$("[name=fdIsOffsetLoan]").val(true);
				$("[name=_fdIsOffsetLoan][value=true]").parent().click();
			}
			FSSC_LoadLoanInfo();
			FSSC_ChangeIsOffsetLoan();
		}, 800);
	})
	
	/**
	 * 拼接借款的数据
	 * @param data
	 * @returns
	 */
	function appendLoan(data){
		for(var i=0 ; i<data.length ;i++){
			DocList_AddRows('TABLE_DocList_fdOffsetList_Form');//table表新增明细
			var src =Com_Parameter.ContextPath+ "fssc/mobile/resource/images/specialTicket.png";
	 		var html1 = $("<div><div><img src="+src+" alt=''><span  name='fdOffsetList_span["+i+"].docSubject' class='subject'>"+data[i].docSubject+"</span></div>" +
	 					"<div><span class=\"ld-newApplicationForm-loan-top-div\">"+fsscLang['fssc-loan:fsscLoanMain.fdApplyMoney']+":</span>" +
	 					"<span name='fdOffsetList_span["+i+"].fdLoanMoney'>"+data[i].fdLoanMoney+"</span><span></span>" +
	 					"</div></div>") ;
	 		var html2 = $("<div><p name='fdOffsetList_span["+i+"].fdNumber'>"+data[i].fdNumber+"</p>" +
	 					"</div><input type=\"hidden\" name=\"loanId\" value=\"" +i+"\"/>"+
	 					 "<div><span>"+fsscLang['fssc-expense:fsscExpenseOffsetLoan.fdCanOffsetMoney']+"：</span><span name='fdOffsetList_span["+i+"].fdCanOffsetMoney'>"+data[i].fdCanOffsetMoney+"</span><span></span>" +
	 					 "&nbsp&nbsp<span>"+fsscLang['fssc-expense:fsscExpenseOffsetLoan.fdOffsetMoney']+":</span><span name='fdOffsetList_span["+i+"].fdOffsetMoney'>"+(data[i].fdOffsetMoney||'')+"</span><span></span>" +
	 					 "<input name='fdOffsetList_span["+i+"].loanId' value="+i+" hidden='true'>" +			
	 					 "</div>");
	 		var div1 = $("<div class=\"ld-newApplicationForm-loan-top\"></div>").append(html1);
	 		var div2 = $("<div class=\"ld-newApplicationForm-loan-bottom\"></div>").append(html2);
	 		var li = $("<li onclick=\"editLoanOffset()\"></li>").append(div1,div2);
	 		console.log(li);
	 		$("#fdOffsetListId").append(li);//拼接借款明细
	 		//借款数据赋值
	 		$("[name='fdOffsetList_Form["+i+"].docSubject']").val(data[i].docSubject);
	 		$("[name='fdOffsetList_Form["+i+"].fdLoanId']").val(data[i].fdLoanId);
	 		$("[name='fdOffsetList_Form["+i+"].fdNumber']").val(data[i].fdNumber);
	 		$("[name='fdOffsetList_Form["+i+"].fdLoanMoney']").val(data[i].fdLoanMoney);
	 		$("[name='fdOffsetList_Form["+i+"].fdCanOffsetMoney']").val(data[i].fdCanOffsetMoney);
	 		$("[name='fdOffsetList_Form["+i+"].fdOffsetMoney']").val(data[i].fdOffsetMoney||'');
	 		$("[name='fdOffsetList_Form["+i+"].fdLeftMoney']").val(data[i].fdLeftMoney||0);
	 		$("[name='fdOffsetList_Form["+i+"].fdCanOffsetMoney']").val(data[i].fdCanOffsetMoney);
		}
	}

	var loanIndex = 0;
	 //编辑冲抵借款金额
	function  editLoanOffset (e) {
		e = e||window.event;
		var ele = e.srcElement||e.target;
		ele = DocListFunc_GetParentByTagName("LI",ele);
		var index = $(ele).find("[name$=loanId]").val();
		$("[name='row-loan-index']").val(index);
	    	$("#docSubject").val($("[name='fdOffsetList_Form["+index+"].docSubject']").val());
	    	$("#fdNumber").val($("[name='fdOffsetList_Form["+index+"].fdNumber']").val());
	    	$("#fdLoanMoney").val($("[name='fdOffsetList_Form["+index+"].fdLoanMoney']").val());
	    	$("#fdCanOffsetMoney").val($("[name='fdOffsetList_Form["+index+"].fdCanOffsetMoney']").val());
	    	$("#fdLeftMoney").val($("[name='fdOffsetList_Form["+index+"].fdLeftMoney']").val());
	    	$("#fdOffsetMoney").val($("[name='fdOffsetList_Form["+index+"].fdOffsetMoney']").val());
	    	$("#fdCanOffsetMoney").val($("[name='fdOffsetList_Form["+index+"].fdCanOffsetMoney']").val());
	    	$('.ld-addLoan-body').addClass('ld-addLoan-body-show')
	    	$(".ld-footer-loan").css("display","flex");
	    	forbiddenScroll()
    }
    
    //编辑保存
	function  saveLoanOffset() {
		var index = $("[name='row-loan-index']").val();
		 var fdOffsetMoney = $("#fdOffsetMoney").val();
		 var fdLeftMoney = $("#fdLeftMoney").val();
		 $("[name='fdOffsetList_Form["+index+"].fdLeftMoney']").val(fdLeftMoney);
		 $("[name='fdOffsetList_Form["+index+"].fdOffsetMoney']").val(fdOffsetMoney||'');
		 $("[name='fdOffsetList_span["+index+"].fdLeftMoney']").html(fdLeftMoney);
		 $("[name='fdOffsetList_span["+index+"].fdOffsetMoney']").html(fdOffsetMoney||'') ;
		 offsetMoneyChange();
		 $('.ld-addLoan-body').removeClass('ld-addLoan-body-show');
		 $(".ld-footer-loan").css("display","none");
		 ableScroll()
	 }
	 
	 
	 function  offsetMoneyChange(){
    	 var fdOffsetMoney = $("#fdOffsetMoney").val()||0;
		 var fdCanOffsetMoney = $("#fdCanOffsetMoney").val();
		 var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;//两位小数且数字校验
    	 if(!reg.test(fdOffsetMoney)&&fdOffsetMoney!=''){
    		 jqtoast("请输入数字,且只能是两位小数");
			 $("#fdOffsetMoney").val("");
			 $("#fdLeftMoney").val(fdCanOffsetMoney);
			 return;
		 }
    	 var fdOffsetMoney1 = parseFloat(fdOffsetMoney);
    	 var fdCanOffsetMoney1 = parseFloat(fdCanOffsetMoney);
    	 if((fdCanOffsetMoney1 - fdOffsetMoney1) < 0 ){
    		 jqtoast("本次冲抵金额不能大于可冲抵金额");
			 $("#fdOffsetMoney").val("");
			 $("#fdLeftMoney").val(fdCanOffsetMoney);
			 return;
    	 }
    	 $("#fdLeftMoney").val((fdCanOffsetMoney1 - fdOffsetMoney1).toFixed(2));
	}
	 
	 function cancelLoanOffset(){
		 $('.ld-addLoan-body').removeClass('ld-addLoan-body-show');
		 $(".ld-footer-loan").css("display","none");
		 ableScroll();
	 }
	 
     /************************************************************************
      * 附件
      ************************************************************************/
	 //附件上传
	 $(function(){
		 var file  =  $('<input type=\"file\" id=\"attachId\" name=\"file\" multiple=\"false\" >');
		 $('.ld-newApplicationForm-attach-btn').click(function(){
			  file.click();
		 });
		 file.change(function(e){
			var select_file = file[0].files;
			console.log(select_file);
			 $.ajax({
					url:Com_Parameter.ContextPath +formOption['url']['getGeneratorId'],
					data:{length:select_file.length},
					async:false,
					success:function(data){
						var rtn = JSON.parse(data);
						var attid = rtn.fdIds.split(";");
						for(var i=0;i<attid.length-1;i++){
							upload(select_file[i],attid[i]);
						}
					}
			  }); 
		  })
	 })
	
	function upload(binary,attid){
		var fdModelId = $("[name='fdId']").val();
		var fdModelName = "com.landray.kmss.fssc.expense.model.FsscExpenseMain";
	    var xhr = new XMLHttpRequest();
	    xhr.open("POST", Com_Parameter.ContextPath +"fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=saveAtt&fdId="+attid+"&fdModelId="+fdModelId+"&fdModelName="+fdModelName+"&filename="+binary.name+"&size="+binary.size);
	    xhr.overrideMimeType("application/octet-stream");
	    //直接发送二进制数据
	    if(xhr.sendAsBinary){
	        xhr.sendAsBinary(binary);
	    } else {
	        xhr.send(binary);
	    }
	    // 监听变化
		xhr.onreadystatechange = function(e){
		     if(xhr.readyState===4){
		            if(xhr.status===200){
		                //上传成功、添加附件的名称
		            	fdAppendAttaHtml(binary.name,attid);
		            }else{
		            	jqtoast("上传附件失败！");
		            }
		        }
	    }
	}
	 function fdAppendAttaHtml(attName,attId,type){
		 var html = "<li><div class=\"ld-remember-attact-info\" onclick=\"showAtt('"+attId+"','"+attName+"')\"><img src="+getSrcByName(attName)+" alt=\"\"><input name=\"attId\" hidden=\"true\" value="+attId+"><span>"+attName+"</span></div><span  onclick=\"deleteAtt('"+attId+"',this,'"+type+"');\"></span></li>"
		 $(fdAttachListId).append(html);
	 }
	 //删除附件
    function deleteAtt(id,obj,tpye){
    	if(tpye!="note"){
    		$.ajax({
    			url:Com_Parameter.ContextPath +'fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=deleteAtt&fdId='+id,
    			data:{},
    			async:false,
    			success:function(data){
    				var rtn = JSON.parse(data);
    				if(rtn.result=='success'){
    					jqtoast("删除成功");
    				}else{
    					jqtoast("删除失败");
    				}
    			}
    	    }); 
    	}
    	obj.parentNode.remove();
	}



    /*************************************************************************
     * 选择对象
    *************************************************************************/
    function selectObject(id,name,dataSource,callback,extendParam){
    	// 报销人Id
		var fdPersonId = $("[name=fdClaimantId]").val();
    	var categoryId=$("[name='fdTemplateId']").val();
    	var fdCompanyId = $("[name='fdCompanyId']").val();
    	var fdExpenseItemId = $("[name='fdExpenseItemId']").val();
    	if(id=='fdInputTaxRate'&&!fdExpenseItemId){//选择进项税率前校验是否选择了费用类型
    		jqtoast("请选择费用类型");
    		return ;
    	}
    	if(id!='fdCompanyId'&&!fdCompanyId){//选择其他前判断是否选择了公司
    		jqtoast(fsscLang["fssc-mobile:fssc.mobile.placeholder.select"]+fsscLang["fssc-mobile:fsscMobileNote.fdCompany"]);
    		return ;
    	}
    	var fdFeildId=id;
		if(extendParam){
			for(var key in extendParam){
				dataSource+='&'+key+"="+extendParam[key];
			}
		}
		$.ajax({
			url: Com_Parameter.ContextPath + dataSource,
			type: 'post',
			async:false,
			data: {"fdCompanyId":fdCompanyId,"categoryId":categoryId,"flag":"expense","keyword":"","fdExpenseItemId":fdExpenseItemId,fdTemplateId:categoryId, fdPersonId:fdPersonId,fdModelName:"com.landray.kmss.fssc.expense.model.FsscExpenseMain"},
		}).error(function(data){
				console.log("获取信息失败"+data);
		}).success(function(data){
			 console.log("获取信息成功");
	      	 var rtn = JSON.parse(data);  //json数组
	      	 var objData=rtn["data"];
	      	 var nameObj=$("[name='"+name+"']");
	      	 if(nameObj.length==0){
	      		nameObj=$("[id='"+name+"']");
	      	 }
	      	 var idObj=$("[name='"+id+"']");
	      	 if(idObj.length==0){
	      		idObj=$("[id='"+id+"']");
	      	 }
	      	 //由于picker.js每次只是隐藏，下次重新创建导致无法通过id绑定控件，所以每次新建前清除上一次的对象
	      	 $(".picker").remove();
	      	 var curValue = idObj.val(),selectedIndex=0;
	      	 if(curValue&&objData){
	      		 for(var i=0;i<objData.length;i++){
	      			 if(objData[i].value==curValue){
	      				 selectedIndex = i;
	      				 break;
	      			 }
	      		 }
	      	 }
	         var picker = new Picker({
	             data: [objData],
	             selectedIndex:[selectedIndex]
	          });
	          picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
	        	  if(objData[selectedIndex]){
		        	  nameObj.val(objData[selectedIndex].text);
		        	  idObj.val(objData[selectedIndex].value);
		        	  if(callback){
		        		  callback(objData[selectedIndex]); 
		        	  }
	        	  }
	           });
	          picker.show();
	          //回车搜索
	          $("#search_input").keypress(function (e) {
	              if (e.which == 13) {
	              	var keyword=$("[name='pick_keyword']").val();
	              	if(keyword){
	              		$.ajax({
	              	           type: 'post',
	              	           url:Com_Parameter.ContextPath + dataSource,
	              	           data: {"keyword":keyword,"fdCompanyId":fdCompanyId,"categoryId":categoryId,"flag":"expense",fdTemplateId:categoryId, fdPersonId:fdPersonId},
	              	       }).success(function (data) {
	              	    	   console.log('获取信息成功');
	              	    	   var rtn = JSON.parse(data);
	              	    	   picker.refillColumn(0, rtn.data);
	              	    	   objData=rtn.data;
	              	       }).error(function (data) {
	              	    	   console.log('获取信息失败');
	              	       })
	              	}
	              }
	      	});
	        //获取到焦点
	        $("#search_input").focus(function(){
	      		$(".weui-icon-clear").attr("style","display:block;");
	      	}) 
		      //取消
	          $(".weui-icon-clear").click(function (e) {
	          	$.ajax({
	   	           type: 'post',
	   	           url:Com_Parameter.ContextPath + dataSource,
	   	           data: {"keyword":''},
	   	       }).success(function (data) {
	   	    	   console.log('获取分类信息成功');
	   	    	   var rtn = JSON.parse(data);
	   	    	  picker.refillColumn(0, rtn.data);
	   	    	  objData=rtn.data;
	   	    	  $("[name='pick_keyword']").val('');
	   	       }).error(function (data) {
	   	    	   console.log('获取分类信息失败');
	   	       })
	      	});
		});
    }
    
    //选择完成本中心触发函数
    function afterSelectCostCenter(data){
    	if(formInitData['fdIsShare']=='1'&&data.value&&data.text){//日常分摊，给明细的所有成本中心重新赋值
    		var len=$("[name$='.fdCostCenterId']").length;
    		for(var i=0;i<len;i++){
    			$("[name='fdDetailList_Form["+i+"].fdCostCenterId']").val(data.value);
    			$("[name='fdDetailList_Form["+i+"].fdCostCenterName']").val(data.text);
    		}
    	}
    }
		
    
    /*************************************************************************
	 * 选择区域
	*************************************************************************/
	function selectAreaObject(id,name,dataSource,baseOn){
		var fdFeildId=id.substring(id.indexOf("(")+1,id.indexOf(")"));
		var event = event ? event : window.event;
		var obj = event.srcElement ? event.srcElement : event.target;
		$.ajax({
			url: formInitData.LUI_ContextPath+'/fssc/mobile/fs_mobile_data/fsscMobileRestful?method=getAccountArea',
			type: 'post',
			async:false,
		}).error(function(data){
				console.log("获取信息失败"+data);
		}).success(function(data){
	      	 var rtn = JSON.parse(data);  //json数组
	      	 var objData=rtn["data"];
	      	 var nameObj=$("[name='"+name+"']");
	      	 var idObj=$("[name='"+id+"']");
	      	 //由于picker.js每次只是隐藏，下次重新创建导致无法通过id绑定控件，所以每次新建前清除上一次的对象
	      	 $(".picker").remove();
	      	 var curValue = idObj.val(),selectedIndex=0;
	      	 if(curValue!=null){
	      		 for(var i=0;i<objData.length;i++){
	      			 if(objData[i].value==curValue){
	      				 selectedIndex = i;
	      				 break;
	      			 }
	      		 }
	      	 }
	         var picker = new Picker({
	             data: [objData],
	             selectedIndex:[selectedIndex]
	           });
	          picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
	        	  for(var i=0;i<objData.length;i++){
	      			 if(objData[i].value==selectedVal[0]){
	      				$("[name="+name+"]").val(objData[i].name);
	      				 break;
	      			 }
	      		  }
	          });
	          picker.show();
	          //回车搜索
	          $("#search_input").keypress(function (e) {
	              if (e.which == 13) {
	              	var keyword=$("[name='pick_keyword']").val();
	              	if(keyword){
	              		$.ajax({
	              	           type: 'post',
	              	           url:formInitData.LUI_ContextPath+'/fssc/mobile/fs_mobile_data/fsscMobileRestful?method=getAccountArea',
	              	           data: {"keyword":keyword},
	              	       }).success(function (data) {
	              	    	   console.log('获取信息成功');
	              	    	   var rtn = JSON.parse(data);
	              	    	   picker.refillColumn(0, rtn.data);
	              	    	   objData=rtn.data;
	              	       }).error(function (data) {
	              	    	   console.log('获取信息失败');
	              	       })
	              	}
	              }
	      	});
	          //获取到焦点
	          $("#search_input").focus(function(){
	      		$(".weui-icon-clear").attr("style","display:block;");
	      	}) 
		      //取消
	          $(".weui-icon-clear").click(function (e) {
	          	$.ajax({
	   	           type: 'post',
	   	           url:formInitData.LUI_ContextPath+'/fssc/mobile/fs_mobile_data/fsscMobileRestful?method=getAccountArea',
	   	           data: {"keyword":''},
	   	       }).success(function (data) {
	   	    	   console.log('获取信息成功');
	   	    	   var rtn = JSON.parse(data);
	   	    	  picker.refillColumn(0, rtn.data);
	   	    	  objData=rtn.data;
	   	    	  $("[name='pick_keyword']").val('');
	   	       }).error(function (data) {
	   	    	   console.log('获取信息失败');
	   	       })
	      	});
		});
	}
		
   /************************************************************************ 
    * 新增一列
    * ************************************************************************/
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
	 
   /************************************************************************ 
    * 删除行
    * ************************************************************************/
	 function DocList_DeleteRow1(optTR,rowIndex){
			var optTB = document.getElementById(optTR);
			var tbInfo = DocList_TableInfo[optTB.id];
			var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
			var rowIndex =rowIndex;
			var index = DocList_GetRowIndex(tbInfo,optTR);
			optTB.deleteRow(rowIndex);
			tbInfo.lastIndex--;
		}
	 /*
	  * 选择税率重新计算税额等信息
	  */
	 function afterSelectInputTax(obj){
		 if(!obj.value){
			 return ;
		 }
		 var fdApplyMoney=$("input[name='fdApplyMoney']").val();
		 var fdNonDeductMoney = $("[name='fdNonDeductMoney']").val(); //不可抵扣金额
		 if(!fdNonDeductMoney||isNaN(fdNonDeductMoney)){
			fdNonDeductMoney=0;
		 }
		 var fdTaxMoney=numSub(fdApplyMoney,fdNonDeductMoney);
		 var fdTaxRate = divPoint(obj.value,100);
		 fdTaxMoney = multiPoint(divPoint(fdTaxMoney,numAdd(fdTaxRate,1.00)),fdTaxRate);
		 $("#fdInputTaxMoney").val(fdTaxMoney);  	//进项税额
		 if(data[0].fdTaxRate&&fdNonDeductMoney>0){
			$("#fdNoTaxMoneyExpense").val(numDiv(numSub(fdApplyMoney,fdNonDeductMoney),numAdd(1,fdTaxRate)));//不含税金额
		 }else{
			$("#fdNoTaxMoneyExpense").val(numSub(fdApplyMoney,fdTaxMoney));//不含税金额
		 }
		 reCalBudgetMoney();
	 }
	 //保存明细时计算
	 function reCalBudgetMoney(){
		 var fdBudgetMoney = "";
		 var fdBudgetRate = $("[name='fdBudgetRate']").val();
		 var fdNoTaxMoney = $("#fdNoTaxMoneyExpense").val();
		 var fdDeduFlag=$("[name='fdDeduFlag']").val();
		 var fdApplyMoney=$("input[name='fdApplyMoney']").val();
		 if(fdBudgetRate){
			if("2"==fdDeduFlag&&fdNoTaxMoney){  // 不含税金额
				fdBudgetMoney = multiPoint(fdNoTaxMoney,fdBudgetRate);
			}else{
				fdBudgetMoney = multiPoint(fdApplyMoney,fdBudgetRate);
			}
		 }
		 $("[name='fdBudgetMoney']").val(fdBudgetMoney);
	 }
	 
	 //提交时计算
	 window.reCalBudgetMoneyOfSubmit=function(index){
		 var fdBudgetMoney = "";
		 var fdBudgetRate = $("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val();
		 var fdApplyMoney = $("input[name='fdDetailList_Form["+index+"].fdApplyMoney']").val();
		 var fdNoTaxMoney = $("input[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val();
		 var fdDeduFlag=$("[name='fdDeduFlag']").val();
		 if(fdBudgetRate){
			if("2"==fdDeduFlag&&fdNoTaxMoney){  // 不含税金额
				fdBudgetMoney = multiPoint(fdNoTaxMoney,fdBudgetRate);
			}else{
				fdBudgetMoney = multiPoint(fdApplyMoney,fdBudgetRate);
			}
		 }
		 $("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(fdBudgetMoney);
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
					 att.push(type[type.length-1]);
					 att.push('"></div><div class="lld-temp-body-content-invoice-attachment-row-name">');
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
	 
	 function FSSC_LoadTempInfo(id,index){
		 var data = new KMSSData();
		 data.AddBeanData("fsscExpenseMobileService&authCurrent=true&type=addTmpInfo&fdNoteId="+id+"&fdMainId="+$("[name=fdId]").val());
		 data = data.GetHashMapArray();
		 if(data&&data.length>0){
			 $("[name='fdDetailList_Form["+index+"].fdExpenseTempId']").val(data[0].fdExpenseTempId);
			 $("[name='fdDetailList_Form["+index+"].fdExpenseTempDetailIds']").val(data[0].fdExpenseTempDetailIds);
		 }
	 }
	 
		function selectSubmitType(id,name){
			$.post(
					Com_Parameter.ContextPath+'fssc/mobile/fs_mobile_data/fsscMobileRestful?method=getClaimantStatu',
					{fdPersonId:$('[name=docCreatorId]').val()},
					function(data){
						data = JSON.parse(data);
						var picker = new Picker({
					        data:[data.data]
					     });
					     picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
					    	 $('[name='+name+']').val(data.data[selectedIndex[0]].text);
					    	 $('[name='+id+']').val(data.data[selectedIndex[0]].value)
					    	 $("[name=rolesSelectObj]").val(data.data[selectedIndex[0]].value);
					     });
				    	 picker.show();
					}
			);
		 }
		$(function(){
			var method = Com_GetUrlParameter(window.location.href,'method');
			if(method=='edit'){
				loadStatus();
			}else{
				$('[name=fdSubmitTypeId]').val($('[name=docCreatorId]').val())
				$('[name=fdSubmitType]').val($("[name=docCreatorName]").val())
			}
		})
		function loadStatus(){
			if($("[name=rolesSelectObj]").length==0||!$("[name=rolesSelectObj]").val()){
				setTimeout("loadStatus()",150)
			}else{
				$('[name=fdSubmitTypeId]').val($("[name=rolesSelectObj]").val())
				$('[name=fdSubmitType]').val($("[name=rolesSelectObj] option:selected").text())
			}
		}
		
//验真
function checkInvoice(){
	var currentIndex=$("input[name='row-invoice-index']").val();
	if(!currentIndex){
		currentIndex=0;
	}
	var fdCheckStatus=$("input[name='fdInvoiceList_Form["+currentIndex+"].fdCheckStatus']").val();
	$(".ld-main").show();//显示处理中
	if(fdCheckStatus!="1"){
		var fdTotalAmount=$("#fdInvoiceMoney").val();
		var fdTotalTax=$("#fdTaxMoney").val();
		if(!$("#fdInvoiceTypeId").val()||!$("#fdInvoiceNumber").val()||!$("#fdInvoiceCode").val()||!fdTotalAmount){
			jqtoast(fsscLang["fssc-mobile:message.check.invoiceInfo"]);
			$(".ld-main").hide();//隐藏处理中
			return ;
		}
		var params=[];
		var param= {
	        	"fdInvoiceType":$("#fdInvoiceTypeId").val(),
	        	"fdInvoiceNumber":$("#fdInvoiceNumber").val(),
	        	"fdInvoiceCode":$("#fdInvoiceCode").val(),
	        	"fdCheckCode":$("#fdCheckCode").val(),
	        	"fdInvoiceDate":$("#fdInvoiceDate").val(),
	        	"fdNoTaxMoney":fdTotalTax?subPoint(fdTotalAmount,fdTotalTax):fdTotalAmount
        	}
		params.push(param);
		 $.ajax({
		        type: 'post',
		        async:false,
		        url:Com_Parameter.ContextPath+'fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=checkInvoice',
		        data:{params:JSON.stringify(params)},
	    }).success(function (data) {
	    	$(".ld-main").hide();//隐藏处理中
	 	   	var rtn = JSON.parse(data);
	 	   	if(rtn.result =="success"){
	 		  jqtoast(fsscLang["fssc-mobile:check.success"]);
	 		  var key="";
	 		  var number=$("#fdInvoiceNumber").val();
	 		  if(number){
	 			  key+=number;
	 		  }
       		 var code=$("#fdInvoiceCode").val();
       		 if(code){
	 			  key+=code;
	 		  }
       		  if(rtn[key]&&rtn[key]['fdCheckStatus']){
       			  $("#fdCheckStatus").val(rtn[key]['fdCheckStatus']);
       		  }
       		  if(rtn[key]&&rtn[key]['fdState']){
       			  $("#fdState").val(rtn[key]['fdState']);
       		  }
	 	   }else{
	 		  jqtoast(fsscLang["fssc-mobile:check.failure"]+":"+rtn.errMsg);
	 		 $(".ld-main").hide();//隐藏处理中
	 	   }
	    }).error(function (data) {
	    	$(".ld-main").hide();//隐藏处理中
	    	jqtoast(fsscLang["fssc-mobile:check.failure"]);
	    })
	}else{//已经验真的发票提示无需重复验真
		$(".ld-main").hide();//隐藏处理中
		jqtoast(fsscLang["fssc-mobile:message.repeat.tips"]);
	}
}

//主表项目选择完赋值给明细里的项目
function afterSelectProject(){
	var fdProjectId=$("[name='fdProjectId']").val();
	var len=$("#TABLE_DocList_fdDetailList_Form tr").length;
	for(var i=0;i<len;i++){
		$("[name='fdDetailList_Form["+i+"].fdProjectId']").val(fdProjectId);
	}
}


 Com_Parameter.event["submit"].push(function(){
	 var flag=true;
	 var fdFeeIds=$("[name='fdFeeIds']").val();
	 var fdId=$("[name='fdId']").val();
	 if(!fdId){
		 fdId=Com_GetUrlParameter(window.location.href,'fdId');
	 }
	 if(fdFeeIds){
		 $.ajax({
			 url:Com_Parameter.ContextPath + 'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=checkFeeRelation&fdFeeIds='+fdFeeIds+'&fdId='+fdId,
			 async:false,
			 type:'POST',
			 success:function(rtn){
				 rtn = JSON.parse(rtn);
				 if(!rtn.result){//不允许关闭
					 flag=false;
					 jqalert({
						 title:'',
						 content:fsscLang['fssc-expense:msg.fee.tips.examineing'],
						 yestext:fsscLang['button.ok']
					 })
				 }
			 }
		 });
	 }
	 return flag;
 });
    
