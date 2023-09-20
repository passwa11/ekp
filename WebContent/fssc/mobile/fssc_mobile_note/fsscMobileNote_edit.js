$(document).ready(function(){
	selectTime('fdInvoiceDate','fdInvoiceDate');
	selectTime('fdHappenDate','fdHappenDate');
	$(".ld-remember-attact-info img").each(function(){
		this.src = getSrcByName($(this).data("file"));
	});
	getDefaultPlaceInit(setPlaceInit);//获取定位地址
});		

function setPlaceInit(){
	$("input[name='fdEndPlace']").val(currentCity);
	$("input[name='fdEndAreaId']").val(currentCityId);
}

function disableBodyScroll(){
	document.body.style.overflow = 'hidden'
}
function enableBodyScroll(){
	document.body.style.overflow = 'auto'
}
//提价校验数据是否为空
Com_Parameter.event.submit.push(function(){
	var pass = true;
	 //校验必填
	  $("input[validate*='required']").each(function(){
		  var subject=$(this).attr('subject');
		  if(!this.value){
			 jqtoast(message['errors.required'].replace('{0}',subject?subject:''));
			 pass=false;
			 return false;
		  }
	  });
	  //校验必填
	  $("input[validate*='currency-dollar']").each(function(){
		 var subject=$(this).attr('subject');
		 var val=$(this).val();
		 if(!isCurrencyDollar(val)){
			var subject=$(this).attr('subject');
			jqtoast(message['errors.dollar'].replace('{0}',subject?subject:''));
			pass=false;
			return false;
		 }
	  });
	return pass;
});	
//校验发票是否重复
Com_Parameter.event["submit"].push(function(){ 
	var flag=true;
	var length=$("#invoiceListId li").length;
	var number=[];
	if(length>0){
		for(var i=0;i<length;i++){
			var fdInvoiceNumber=$("[name='fdInvoiceList_Form["+i+"].fdInvoiceNumber']").val();
			var fdInvoiceCode=$("[name='fdInvoiceList_Form["+i+"].fdInvoiceCode']").val();
			if((fdInvoiceNumber+fdInvoiceCode)&&number.indexOf(fdInvoiceNumber+";"+fdInvoiceCode)>-1){
				dialog.alert('发票明细存在相同的发票号码，请重新填写');
				return false;
			}else{
				number.push(fdInvoiceNumber+";"+fdInvoiceCode);
			}
		}
	}
	var fdId = Com_GetUrlParameter(window.location.href,'fdId');
	if(!fdId){
		fdId=$("[name='fdId']").val();
	}
	//校验是否有其他单据关联了发票明细的发票
	if(number.length>0){
			$.ajax({
			url:Com_Parameter.ContextPath + 'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=checkInvoiceDetail',
			data:{data:JSON.stringify({number:number,fdModelId:fdId})},
			async:false,
			success:function(rtn){
				if(rtn){
					rtn = JSON.parse(rtn);
					if(rtn.msg){//有重复发票
						flag=false;
						dialog.alert(rtn.msg);
					}
				}
			}
		});
	}
 	return flag;
 });

//保存随手记之前先保存发票
Com_Parameter.event.submit.push(function(){
	var params = [],pass = true;
	$("#invoiceListId>li").each(function(i){
		params.push({
			fdTotalAmount:$(this).find("input[name$=fdTotalAmount]").val()*1,
			fdInvoiceCode:$(this).find("input[name$=fdInvoiceCode]").val(),
			fdInvoiceNumber:$(this).find("input[name$=fdInvoiceNumber]").val(),
			fdCheckCode:$(this).find("input[name$=fdCheckCode]").val(),
			fdInvoiceDate:$(this).find("input[name$=fdInvoiceDate]").val(),
			fdPurchaserName:$(this).find("input[name$=fdPurchaserName]").val(),
			fdPurchaserTaxNo:$(this).find("input[name$=fdPurchaserTaxNo]").val(),
			fdSalesName:$(this).find("input[name$=fdSalesName]").val(),
			fdTotalTax:$(this).find("input[name$=fdTotalTax]").val()*1,
			fdAttId:$(this).find("input[name$=fdAttId]").val(),
			fdId:$(this).find("input[name$=fdId]").val(),
			fdCheckStatus:$(this).find("input[name$=fdCheckStatus]").val(),
			fdState:$(this).find("input[name$=fdState]").val(),
			fdInvoiceTypeId:$(this).find("input[name$=fdInvoiceTypeId]").val(),
			fdInvoiceType:$(this).find("input[name$=fdInvoiceType]").val(),
			fdModelName:'com.landray.kmss.fssc.mobile.model.FsscMobileNote',
			fdModelId:$('input[name=fdId]').val(),
			index:i
		})
	})
	if(params.length>0){
		$.ajax({
			url:Com_Parameter.ContextPath+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=saveOrUpdateInvoice',
			data:{params:JSON.stringify(params)},
			dataType:'json',
			async:false,
			success:function(rtn){
				if(rtn.result!='success'){
					pass = false;
					jqtoast('保存发票异常：'+rtn.message);
				}else{
					var invs = rtn.invs;
					for(var i=0;i<invs.length;i++){
						$("#invoiceListId>li").eq(invs[i].index).find("[name*=fdId]").val(invs[i].fdId);
					}
				}
			}
		})
	}
	//$(".invoice_detail").remove();
	return pass;
})

//关联明细的、附件的数据
	Com_Parameter.event.submit.push(function(){
		 var invoiceIndex = $("#invoiceListId li").length;
		 var invoiceParam =[];
		 for(var i=0;i<invoiceIndex;i++){
			 invoiceParam.push({
				 'fdTotalAmount':$("#invoiceListId>li").eq(i).find("[name*=fdTotalAmount]").val(),
			 	 'fdInvoiceNumber':$("#invoiceListId>li").eq(i).find("[name*=fdInvoiceNumber]").val(),
			 	'fdInvoiceCode':$("#invoiceListId>li").eq(i).find("[name*=fdInvoiceCode]").val(),
			 	 'fdId':$("#invoiceListId>li").eq(i).find("[name*=fdId]").val()
			 });
		 }
		 var attaIndex = $("#fdAttachListId li").length;
		 var attaParam = [];
		 for(var i=0;i<attaIndex;i++){
			 attaParam.push({
				 'fdId':$("[name='fdAttList_form["+i+"].fdId']").val(),
			 	 'fdName':$("[name='fdAttList_form["+i+"].fdName']").val()
			 });
		 }
		 if(attaParam.length>0){
			 $("[name='attaParam']").val(JSON.stringify(attaParam));
		 }
		 $("[name='invoiceParam']").val(JSON.stringify(invoiceParam));
		return true;
	});
	
    //---------------------------------选择费用类型start -----------------------------//
	function selectExpenseItem(fdParent,order) {
    	if(!fdParent){
    		$("#second_item").html('');
    		$("#third_item").html('');
    		$("#third_name").html('');
    	}
    	var fdCostCenter = $("[name='fdCostCenterId']").val();
    	var fdCompanyId = $("[name='fdCompanyId']").val();
    	if(!fdCompanyId){
    		jqtoast(fsscLang["fssc-mobile:fssc.mobile.placeholder.select"]+fsscLang["fssc-mobile:fsscMobileNote.fdCompany"]);
    		return false;
    	}
    	var keyword = $("#searchItem").val();
    	 $.ajax({
             type: 'post',
             url:Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getEopBasedataExpenseItem',
             data: {"fdCompanyId":fdCompanyId,"flag":"treeNote","fdParent":fdParent,"keyword":keyword},
         }).success(function (data) {
        	 var rtn = JSON.parse(data);
        	 if(rtn.result=='success'){
        		 var dataArr = rtn.data;
        		 if(keyword){
        			 $(".ld-cost-modal-main-left").html('');  //清空第一级
        			 $('#second_item li').remove();		//清空第二级
        			 $("#third_name").html('');	//清空第三级显示名称
        			 $('#third_item li').remove();	//清空第三级
        			 for(var i=0;i<dataArr.length;i++){
        				 $('#second_item').append("<li >"+dataArr[i]["name"]+"<span hidden='true'>"+dataArr[i]["value"]+"</span></li>");
        			 }
        		 }else if(!order){
        			 $('.ld-cost-modal-main-left div').remove();
        			 for(var i=0;i<dataArr.length;i++){
        				 $('.ld-cost-modal-main-left').append("<div >"+dataArr[i]["name"]+"<span hidden='true'>"+dataArr[i]["value"]+"</span></div>");
            		 }
        		 } else if(order=="second"){
        			 $('#second_item li').remove();
        			 $("#third_name").html('');
        			 $('#third_item li').remove();
        			 for(var i=0;i<dataArr.length;i++){
        				 $('#second_item').append("<li >"+dataArr[i]["name"]+"<span hidden='true'>"+dataArr[i]["value"]+"</span></li>");
        			 }
        		 } else if(order=="third"){
        			 if(dataArr.length==0){
        				 $("#third_name").html('');
        			 }
        			 $("#third_name").attr('style','display:block;');
        			 $('#third_item li').remove();
        			 for(var i=0;i<dataArr.length;i++){
        				 $('#third_item').append("<li >"+dataArr[i]["name"]+"<span hidden='true'>"+dataArr[i]["value"]+"</span></li>");
        			 }
        		 }
        		 disableBodyScroll();
        	 }
         }).error(function () {
             console.log("获取失败");
     	})
     	$('.ld-cost-mask').addClass('ld-cost-mask-show'); 
    }
    //费用类型回车搜索
    $("#searchItem").keypress(function (e) {
         if (e.which == 13) {
        	 	selectExpenseItem("all",null);
         }
	 });
    
    function openSelectExpenseItem(){
    		enableBodyScroll()
        $('.ld-cost-mask').removeClass('ld-cost-mask-show') 
        var id = $(".ld-cost-modal-main-left .active  span").text();
        var name = $(".ld-cost-modal-main-left .active").html();
        var secondId =$(".ld-cost-modal-main-right ul .active span").text();
        var secondName =$(".ld-cost-modal-main-right ul .active").html();
        if(secondName!=undefined ){
         	var arr = secondName.split("<span");
         	$(".ld-consumerType").val(arr[0]);
	        	$("[name='fdExpenseItemId']").val(secondId);
	        	$("[name='fdExpenseItemName']").val(arr[0]);
        } else {
	        	if(!name){
	        		return ;
	        	}
	        	var arr = name.split("<span");
	        	$(".ld-consumerType").val(arr[0]);
	        	$("[name='fdExpenseItemId']").val(id);
	        	$("[name='fdExpenseItemName']").val(arr[0]);
        }
        $("#searchItem").val('');  //清空搜索值，不然会影响下次显示
    }
    function colseSelectExpenseItem(){
	    enableBodyScroll()
	    	$(".ld-consumerType").val("");
	    	$("[name='fdExpenseItemId']").val('');
	    	$('.ld-cost-mask').removeClass('ld-cost-mask-show') ;
	    	$("#searchItem").val('');  //清空搜索值，不然会影响下次显示
    }
    
    $('.ld-cost-mask').click(function(e){
        if((e.target||e.srcElement).id=="ld-cost-mask"){
        	$("#searchItem").val('');  //清空搜索值，不然会影响下次显示
        	openSelectExpenseItem();
        }
    })
    var myCostSwiper = new Swiper('.cost-swiper-container',{
        onlyExternal: true,
        direction: 'vertical',
        speed: 1000,
    })
    //一级费用类型选择
     $(document).on('touchstart mousedown','.ld-cost-modal-main-left div', function (e) {
        $(".ld-cost-modal-main-left .active").removeClass('active');
        $(this).addClass('active');
        myCostSwiper.slideTo($(this).index());
        var fdParent = $(".ld-cost-modal-main-left .active span").text();
        selectExpenseItem(fdParent,"second");
    });
    //二级费用类型选择
    $(document).on('touchstart','#second_item li', function (e) {
    	$("#second_item .active").removeClass('active');
        $(this).addClass('active');
        if(!$("#searchItem").val()){  //搜素不做后续处理
        	var fdParent =  $(this).find("span").text();
            selectExpenseItem(fdParent,"third");
            var current=$(this).html();
            if(current){
            	var arr = current.split("<span");
            	$("#third_name").attr('style','display:none;');
            	$("#third_name").html(arr[0]);
            	 $('#third_item li').remove();
            }
        }
    })
    //三级费用类型选择
    $(document).on('touchstart','#third_item li', function (e) {
    	$("#second_item .active").removeClass('active');
    	$("#third_item .active").removeClass('active');
    	$(this).addClass('active')
    })
    //搜索
     $(document).on('touchstart mousedown','.ld-cost-modal-search i', function (e) {
    	 selectExpenseItem("all",undefined);
    })
   
   
    //---------------------------------选择费用类型end --------------------------------//
    
    
    //---------------------------------选择出发到达地点start --------------------------//
      var mySwiper = new Swiper('.swiper-container',{
        onlyExternal: true,
        // autoHeight: true,
        speed: 500,
    })

    function selectCity(fdType) {
    	if(!fdType){//第一次点开选择城市
    		$(".ld-city-modal-tab").find('div').eq(0).attr('class','active');  //选中第一个
    	}
      	var fdCompanyId = $("[name='fdCompanyId']").val();
	  	if(fdCompanyId==""){
	  		jqtoast(fsscLang["fssc-mobile:fssc.mobile.placeholder.select"]+fsscLang["fssc-mobile:fsscMobileNote.fdCompany"]);
	  		return ;
	  	}
	  	$('.ld-city-mask').addClass('ld-city-mask-show');
	  	var keyword = $("#searchItem").val();
	  	if(fdType=='国际'){
	  		fdType = "1";
	  	}else{
	  		fdType = "2";
	  	}
	  	 $.ajax({
	           type: 'post',
	           url:Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getCityData',
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
			$("[name='searchCity']").val('');
			$(".ld-city-modal-search").append('<i></i>');
			$(".cancel-btn").hide();
		}).error(function(data) {
			console.log('获取分类信息失败' + data);
		})
	});
    
    // 按拼音排序
	function fomartCityData(data){
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
	 //选择国内、国外      
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
        $(".ld-city-modal-tab .active").removeClass('active');
    }
    //选择
    function sureSelectCity(){
    	$("[name='fdEndAreaId']").val($('#endCityId').val());
    	$("[name='fdEndPlace']").val($('#endCity').val());
    	$('.ld-city-mask').removeClass('ld-city-mask-show');
    	$("input[name='searchCity']").val('');
    	$(".ld-city-modal-tab .active").removeClass('active');
    }
    
    $('.ld-city-mask').click(function(e){
        if((e.target||e.srcElement).id=="ld-city-mask"){
            closeSelectCity()
        }
    })
    // 城市索引
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
        $(this).addClass('active');
        if($('.startCity').hasClass('active')){
            $('.currentCityType').removeClass('twoLine')
            $('.currentCityType').removeClass('threeLine')
        }
        if($('.endCity').hasClass('active')){
            $('.currentCityType').addClass('twoLine')
            $('.currentCityType').removeClass('threeLine')
        }
        if($('.trafficTools').hasClass('active')){
            $('.currentCityType').addClass('threeLine')
        }
    })
    
    $(document).on('mousedown','.ld-city-modal-main .ld-city-modal-main-city-list ul li', function (e) {     
        // 选择消费城市
        if($('.endCity').hasClass('active')){
        	$('.endCity').html('消费城市：'+$(this).html());
        	var endCity = $(this).html();
        	var arr = endCity.split("<span");
        	$('#endCity').val(arr[0]);
        	$('#endCityId').val($(this).find("span").text());
        } 
    })
    // 选择出发地点
    $('.ld-city-modal-main .commonlyUsed ul li').click(function(){
        if($('.startCity').hasClass('active')){
            $('.startCity').html('出发：'+$(this).html())
            $('.startCity').removeClass('active')
            $('.endCity').addClass('active')
            $('.currentCityType').addClass('twoLine')
            $('.fdStartPlace').val($(this).html())
        }else{
            // 选择到达地点
            if($('.endCity').hasClass('active')){
            $('.endCity').html('到达：'+$(this).html())
            $('.endCity').removeClass('active')
            $('.trafficTools').addClass('active')
            $('.currentCityType').addClass('threeLine')
            $('.fdEndPlace').val($(this).html())
        }
        }
    })
    //---------------------------------选择出发地点end ----------------------------------------//

    /**
     * 时间选择
     * @param id
     * @param name
     * @returns
     */
     function getTime(){
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
      	$("#fdInvoiceDate").val(year+"-"+month+"-"+day);
    }
    
    
	 function selectTime(id,name){
	  	var myDate = new Date(); 
	  	var year = myDate.getFullYear();
	  	var month = myDate.getMonth()*1+1;
	  	var day =myDate.getDate()
	  	new Mdate(id, {
			   acceptId: id,
			   acceptName: name,
			   beginYear: "2015",
			   beginMonth: "1",
			   beginDay: "1",
			   endYear: year,
			   endMonth: month,
			   endDay: day,
			   format: "-"
			});	
	}
    
	 /*************************************************************************
	     * 选择对象
	    *************************************************************************/
	    function selectObject(id,name,dataSource,callback){
	    	var fdCompanyId=$("input[name='fdCompanyId']").val();
	    	if(id=="fdCostCenterId"&&!fdCompanyId){
	    		jqtoast(fsscLang["fssc-mobile:fssc.mobile.placeholder.select"]+fsscLang["fssc-mobile:fsscMobileNote.fdCompany"]);
	    		return ;
	    	}
    		$.ajax({
    			url: Com_Parameter.ContextPath + dataSource,
    			type: 'post',
    			async:false,
    			data: {fdCompanyId:fdCompanyId,fdPersonId:currentUserId},
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
    		        	  if(idObj.val()&&objData[selectedIndex].value&&objData[selectedIndex].value!=idObj.val()&&callback){
    		        		  eval(callback+"()");//执行callback()函数
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
    	              	           data: {"keyword":keyword,fdCompanyId:fdCompanyId,fdPersonId:currentUserId},
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
	    
	    //选择完公司,清除成本中心、费用类型
	    function afterSelectCompany(){
    		$("input[name='fdCostCenterId']").val("");
    		$("input[name='fdCostCenterName']").val("");
    		$("input[name='fdExpenseItemId']").val("");
    		$("input[name='fdExpenseItemName']").val("");
	    }
    
	//-----------------------------------新增附件start---------------------------------------//
	$(function(){
		var file;
		if(navigator.userAgent.toLowerCase().match(/MicroMessenger/i) == "micromessenger"){ //微信，安卓无法使用
			file = $('<input type="file" />'); 
		}else{ //钉钉、KK能适用input
			file = $('<input type="file" accept=".png,.jpg,.gif,.jpeg,.pdf" />'); 
		}
		$('.ld-remember-attach-btn').click(function(){
			file.click();
		});

		file.change(function(e){
			 //document.getElementById("ld-main-upload").style.display="";
			var select_file = file[0].files;
			var formData = new FormData();
			  //上传成功
            document.getElementById("ld-main-upload").style.display="none";
            if(window.needOcr||(select_file[0].name).indexOf(".pdf")>-1){
            	document.getElementById("ld-main-scan").style.display="";
    		}
			 $.ajax({
					url:Com_Parameter.ContextPath +'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getGeneratorId',
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
			 file.val('');
		})
	})
	
	//校验附件是否已经上传过同名的，若同名，则提示，停止上传，苹果拍照图片都是image.jpg，故不用
	
	function isSameAtt(select_file){
		var name=select_file[0].name;
		var existNames='';
		$('#fdAttachListId').find('li').find('.ld-remember-attact-info').find('span').each(function(){
			existNames+=$(this).html()+';';
		});
		if(existNames.indexOf(name+';')>-1){
			return true;
		}
		return false;
	}
	
	function upload(binary,attid){
		var suffix_name=(binary.name).substring((binary.name).lastIndexOf('.')+1);
		var fdModelId = $("[name='fdModelId']").val();
	    var xhr = new XMLHttpRequest();
	    var fileName = new Date().getTime();
	    xhr.open("POST", Com_Parameter.ContextPath +"fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=saveAtt&fdId="+attid+"&fdModelId="+fdModelId+"&fdModelName=com.landray.kmss.fssc.mobile.model.FsscMobileNote&filename="+encodeURIComponent(binary.name));
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
		            	if(!window.needOcr&&(binary.name).indexOf(".pdf")==-1){  //没有ocr模块，且不是pdf（是图片），不做识别
		        			addAttList(binary.name,attid);//附件
		        		}
		            	saveInvoiceInfoFormRayky(attid,fdModelId,binary.name);
		            }else{
		         	   jqtoast("上传附件失败！");
		            }
		        }
	    }
	}
	//附件识别,保存
	function saveInvoiceInfoFormRayky(attid,fdModelId,fileName){
		if(!window.needOcr&&fileName.indexOf(".pdf")==-1){  //没有ocr模块，且不是pdf（是图片），不做识别
			return;
		}
	   $.ajax({
			url: Com_Parameter.ContextPath +'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=saveInvoiceInfoFormRayky',
			data:{"fdId":attid,"fdModelId":fdModelId,"flag":"noteEdit"},
			async:true,
			success:function(data){
				var rtn = JSON.parse(data);
				if(rtn.result=='success'){
					//识别成功
					if(notHasAtCurrent(rtn.invoice)){
						jqtoast("识别成功");
						addInvoice(rtn.invoice);//发票明细
						addAttList(fileName,attid);//附件
					}else{
						jqtoast(message["tips.invoice.same"]);  //当前页面已存在
					}
				} else {
					//识别失败
					jqtoast(rtn.message);
				}
				
				document.getElementById("ld-main-scan").style.display="none";
			},
			error:function(data,status){
				$("#ld-main").attr('style','display:none');
				jqtoast('识别失败statusText:'+data.statusText);
			}
		}); 
	}
	
	//校验当前页面是否有相同发票
	function notHasAtCurrent(param){
		var number_code=param.number+param.code;
		var notHasFlag=true;  //默认不存在
		var len=$("[name$='.fdInvoiceCode']").length;
		for(var i=0;i<len;i++){
			var code=$("[name='fdInvoiceList_form["+i+"].fdInvoiceCode']").val();
			var number=$("[name='fdInvoiceList_form["+i+"].fdInvoiceNumber']").val();
			if(number_code==number+code){
				notHasFlag=false;
				return false;
			}
		}
		return notHasFlag;
	}
	
	//拼接发票
	function addInvoice(param){
		console.log(param);
		if(param.length>0){
			$("[name=fdExpenseItemId]").val(param[0].fdExpenseItemId);
			$("[name=fdExpenseItemName]").val(param[0].fdExpenseItemName);
			$("[id=fdExpenseItemName]").val(param[0].fdExpenseItemName);
		}
		for(var i=0;i<param.length;i++){
			var  invoice= {
					 'fdId':param[i].fdId,
					 'InvoiceCode':param[i].code,
					 'InvoiceNumber':param[i].number,
					 'CheckCode':param[i].check_code,
					 'BillingDate':param[i].date,
					 'TotalTax':param[i].tax,
					 'TotalAmount':param[i].total,
					 'PurchaserName':param[i].buyer,
					 'PurchaserTaxNo':param[i].buyer_tax_id,
					 'SalesName':param[i].seller,
					 'fdModelId':$("[name='fdModelId']").val(),
					 'fdModelName':$("[name='fdModelName']").val(),
					 'fdInvoiceType':param[i].fdInvoiceType,
					 'fdInvoiceTypeId':param[i].fdInvoiceTypeId?param[i].fdInvoiceTypeId:param[i].type,
					 'fdStateName':param[i].fdStateName,
					 'fdCheckStatus':param[i].fdCheckStatus,
					 'fdDeductibleName':param[i].fdDeductibleName,
					 'fdCheckStatus':param[i].fdCheckStatus,
					 'fdState':param[i].fdState,
					 'attId':param[i].attId
				};
				addInvoiceList(invoice);
		}
		sumTotalMoney();
	}
	
	//重新计算消费金额
	function sumTotalMoney(){
		var totalMoney=0.0;
		$("#invoiceListId").find('li').each(function(){
			var money=$(this).find("[name$='.fdTotalAmount']").val();
			if(money){
				totalMoney=numAdd(totalMoney,money);
			}
		});
		$("input[name='fdMoney']").val(formatFloat(totalMoney,2));
	}

	//拼接附件
	var fdAttacId = 0;
	function addAttList(fileName,attId){
		fdAttacId = $("#fdAttachListId>li").length;
		 var html = "<li><div class=\"ld-remember-attact-info\" onclick=\"showAtt('"+attId+"','"+fileName+"');\"><img src="+getSrcByName(fileName)+" alt=\"\"><input name='fdAttList_form["+fdAttacId+"].fdId' hidden=\"true\" value="+attId+"><input name='fdAttList_form["+fdAttacId+"].fdName' hidden=\"true\" value="+fileName+"><span>"+fileName+"</span></div><span onclick=\"deleteAtt('"+attId+"');\"></span></li>"
		 $("#fdAttachListId").append(html);
		 fdAttacId++;
	}
	
	//-----------------------------------新增附件end----------------------------------------//
	
	/**
	 * 删除附件
	 * @param attid
	 * @returns
	 */
	function deleteAtt(attid){
		  var event = event ? event : window.event;
		  var obj = event.srcElement ? event.srcElement : event.target;
		  obj.parentElement.remove();  //移除js样式
		  $.ajax({
	          type: 'post',
	          async:false,
	          url:Com_Parameter.ContextPath +'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=deleteAtt',
	          data:{"fdId":attid},
	      }).success(function (data) {
	    	  var rtn = JSON.parse(data);
				if(rtn.result=='success'){
					//删除成功
					jqtoast("删除成功");
					$("[name$='.fdAttId'][value='"+attid+"']").val(''); //清除发票对应的附件ID
				} else {
					//删除失败
					jqtoast(rtn.message);
				}
	      }).error(function () {
	          console.log("删除附件失败");
	  	   })
	}
	
	//-----------------------------------删除附件end--------------------------------------//
	
	
	/**********************************************************************************
	 * 发票明细
	 * **********************************************************************************/
	function addInvoiceByHand() {
		//因为删除会破坏index顺序，故直接获取最后的index加1处理
		var len=$("#invoiceListId>li").length;
		$("input[name='editFlag']").val(0);  //0,新增；1编辑
		var index=$("#invoiceListId>li").eq(len-1).find("[name^='row-invoice-index']").val();
		if(!index){
			index=0;
		}else{
			index=index*1+1;
		}
		$("input[name='currentInvoiceIndex']").val(index);
		$("input[name='fdInvoiceType']").val("");
		$("input[name='fdInvoiceCode']").val("");
		$("input[name='fdInvoiceNumber']").val("");
		$("input[name='fdCheckCode']").val("");
		$("input[name='fdTotalTax']").val("");
		$("input[name='fdJshj']").val("");
		$("input[name='fdPurchaserName']").val("");
		$("input[name='fdPurchaserTaxNo']").val("");
		$("input[name='fdSalesName']").val("");
		$("input[name='fdCheckStatus']").val("");
		$("input[name='fdState']").val("");
		getTime();
		$(".ld-invoice-img").attr('style','display:none;');
		showInvoiceDetailView();
	   // forbiddenScroll()
	}
	
		
	function deleteInvoice(e) {
		if(!window.confirm("确认要删除该发票吗？")){
			return;
		}
		if (e) {
            e.stopPropagation();
            e.preventDefault();
          } else {
            window.event.returnValue = false;
            window.event.cancelBubble = true;
          }
		 e = e||window.event;
		 var ele = e.target||e.srcElement;
		 var index=$(ele).parent().parent().find("[name^='row-invoice-index']").val();
		 var fdInvoiceNumber=$("[name='fdInvoiceList_form["+index+"].fdInvoiceNumber']").val();
		 var fdInvoiceCode=$("[name='fdInvoiceList_form["+index+"].fdInvoiceCode']").val();
		 $(ele).parent().parent().remove();
		 $.ajax({
		        type: 'post',
		        url:Com_Parameter.ContextPath+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=deleteInvoiceDetail',
		        data: {"fdInvoiceCode":fdInvoiceCode,"fdInvoiceNumber":fdInvoiceNumber},
	    }).success(function (data) {
	 	   var rtn = JSON.parse(data);
	 	   if(rtn.result =="success"){
	 		  jqtoast("删除成功");
	 		 sumTotalMoney();
	 	   }else{
	 		  jqtoast("删除失败");
	 	   }
	    }).error(function (data) {
	    	jqtoast("删除失败");
	    })
		 if(e.cancelBubble){
			 e.cancelBubble = true;
		 }else{
			 e.stopPropagation();
		 }
	}
	
	function showInvoiceDetailView(){
		$("[name='currentInvoiceIndex']").val('');  	//清除currentInvoiceIndex，否则影响下次
		$('.ld-invoice-detail-body').addClass('ld-invoice-detail-body-show')
	    $('.ld-rememberOne-footer').addClass('ld-rememberOne-footer-top')
	}
	
	function backToMainView(){
		$("[name='currentInvoiceIndex']").val('');  	//清除currentInvoiceIndex，否则影响下次
		$('.ld-invoice-detail-body').removeClass('ld-invoice-detail-body-show');
	    $('.ld-rememberOne-footer').removeClass('ld-rememberOne-footer-top');
	}
	
	//保存发票  0:新增1 修改
	function saveInvoice() {
      	if (!$("input[name='fdInvoiceTypeId']").val()) {
      		jqtoast("发票类型不能为空");
			 return false;
		}else if (!$("input[name='fdInvoiceNumber']").val()){
			 jqtoast("发票号码不能为空");
			 return false;
		}else if (!$("input[name='fdInvoiceDate']").val()){
			 jqtoast("发票时间不能为空");
			 return false;
		}else if (!$("input[name='fdJshj']").val()){
			 jqtoast("价税合计不能为空");
			 return false;
		}else if (!isCurrencyDollar($("input[name='fdJshj']").val())){
			 jqtoast("价税合计需为数字类型");
			 return false;
		}
		if("10100"==$("input[name='fdInvoiceTypeId']").val()||"30100"==$("input[name='fdInvoiceTypeId']").val()){
			if(!$("input[name='fdInvoiceCode']").val()){
				jqtoast("发票代码不能为空");
				return ;
			}  else if(!$("input[name='fdTotalTax']").val()){
				jqtoast("税额不能为空");
				return ;
			}
		}
      	var currentIndex = $("[name='currentInvoiceIndex']").val();
		if(!currentIndex){  //上传附件为undefine，是新增
			currentIndex=$("#invoiceListId>li").length*1;  //索引从0开始的
		}
		var param_data = {
                fdCompanyId:$("input[name='fdCompanyId']").val(),
                fdInvoiceType:$("input[name='fdInvoiceTypeId']").val(),
                fdTaxNumber:$("input[name='fdPurchaserTaxNo']").val(),
                fdPurchName:$("input[name='fdPurchaserName']").val()
        }
		$.ajax({
                type: 'post',
                async:false,
                url:Com_Parameter.ContextPath+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=checkTaxNumberAndPurchName',
                data:{params:JSON.stringify(param_data)},
        }).success(function (data) {
            var rtn = JSON.parse(data);
            var editFlag=$("input[name='editFlag']").val();
            if(editFlag=='0'){//新建
                var params = {
                        TotalAmount:$("input[name='fdJshj']").val(),
                        InvoiceCode:$("input[name='fdInvoiceCode']").val(),
                        InvoiceNumber:$("input[name='fdInvoiceNumber']").val(),
                        CheckCode:$("input[name='fdCheckCode']").val(),
                        BillingDate:$("input[name='fdInvoiceDate']").val(),
                        PurchaserName:$("input[name='fdPurchaserName']").val(),
                        PurchaserTaxNo:$("input[name='fdPurchaserTaxNo']").val(),
                        SalesName:$("input[name='fdSalesName']").val(),
                        TotalTax:$("input[name='fdTotalTax']").val(),
                        fdInvoiceType:$("input[name='fdInvoiceType']").val(),
                        fdInvoiceTypeId:$("input[name='fdInvoiceTypeId']").val(),
                        fdCheckStatus:$("input[name='fdCheckStatus']").val(),
                        fdState:$("input[name='fdState']").val(),
                        fdIsCurrent:rtn.fdIsCurrent
                }
                addInvoiceList(params);
            }else{
                $("[name='fdInvoiceList_form["+currentIndex+"].fdInvoiceCode']").val($("[name='fdInvoiceCode']").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdInvoiceNumber']").val($("[name='fdInvoiceNumber']").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdCheckCode']").val($("[name='fdCheckCode']").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdTotalTax']").val($("[name='fdTotalTax']").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdTotalAmount']").val($("[name='fdJshj']").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdPurchaserName']").val($("[name='fdPurchaserName']").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdPurchaserTaxNo']").val($("[name='fdPurchaserTaxNo']").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdSalesName']").val($("[name='fdSalesName']").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdInvoiceDate']").val($("[name='fdInvoiceDate']").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdInvoiceType']").val($("#fdInvoiceType").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdInvoiceTypeId']").val($("#fdInvoiceTypeId").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdCheckStatus']").val($("#fdCheckStatus").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdState']").val($("#fdState").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdTotalAmount']").parent().parent().find(".fdInvoiceNumber").html($("[name='fdInvoiceNumber']").val())
                $("[name='fdInvoiceList_form["+currentIndex+"].fdTotalAmount']").parent().parent().find(".fdTotalAmount").html($("[name='fdJshj']").val());
                $("[name='fdInvoiceList_form["+currentIndex+"].fdTotalAmount']").parent().parent().find(".fdInvoiceType").html($("#fdInvoiceType").val());
                var status=$("#fdCheckStatus").val();  //验真状态
                if(!status){
                    status="0";
                }
                $("[name='fdInvoiceList_form["+currentIndex+"].fdTotalAmount']").parent().parent().find(".ld-remember-invoiceInfo-top-satuts").html(fsscLang["fssc-mobile:invoice.satuts."+status]);
                $("[name='fdInvoiceList_form["+currentIndex+"].fdTotalAmount']").parent().parent().find(".ld-remember-invoiceInfo-top-satuts-0").addClass("ld-remember-invoiceInfo-top-satuts-"+status);
                $("[name='fdInvoiceList_form["+currentIndex+"].fdTotalAmount']").parent().parent().find(".ld-remember-invoiceInfo-top-satuts-0").removeClass("ld-remember-invoiceInfo-top-satuts-"+(status=="1"?"0":"1"));
                if(rtn.fdIsCurrent){
                    $("[name='fdInvoiceList_form["+currentIndex+"].fdTotalAmount']").parent().parent().find("span[id^='isCurrent']").show();
                    $("[name='fdInvoiceList_form["+currentIndex+"].fdTotalAmount']").parent().parent().find("span[id^='isCurrent']").html(fsscLang["fssc-mobile:invoice.is.current"]);
                    $("[name='fdInvoiceList_form["+currentIndex+"].fdTotalAmount']").parent().parent().find("span[id^='isCurrent']").addClass("ld-remember-invoiceInfo-top-satuts-"+rtn.fdIsCurrent);
                    $("[name='fdInvoiceList_form["+currentIndex+"].fdTotalAmount']").parent().parent().find("span[id^='isCurrent']").removeClass("ld-remember-invoiceInfo-top-satuts-"+(rtn.fdIsCurrent==0?1:0));
                }else{
                    $("[name='fdInvoiceList_form["+currentIndex+"].fdTotalAmount']").parent().parent().find("span[id^='isCurrent']").hide();
                }
            }
			$(".isVat").hide();//隐藏专票必填字段提示
            backToMainView();
            sumTotalMoney();
        })
	}
	
	
	//拼接发票详细信息
	function addInvoiceList(params,data){
		var  currentIndex= $("[name='currentInvoiceIndex']").val();
		if(!currentIndex){  //上传附件为undefine，是新增
			var currentIndex=$("#invoiceListId>li").length*1;  //索引从0开始的
		}
		var fdId = params.fdId?params.fdId:'';
		var fdCheckStatus = message['invoice.satuts.0'];
		if(params.fdCheckStatus&&params.fdCheckStatus=='1'){
			fdCheckStatus=message['invoice.satuts.1'];
		}
		var fdDeductibleName = message['invoice.fdDeductible.0'];
		if(params.fdDeductibleName&&params.fdDeductibleName=='1'){
			fdDeductibleName=message['invoice.fdDeductible.1'];
		}
		var fdInvoiceType=params.fdInvoiceType;
		if(!fdInvoiceType&&params.fdInvoiceTypeId){
			fdInvoiceType=message['enums.invoice_type.'+params.fdInvoiceTypeId];
		}
		var src = Com_Parameter.ContextPath+ "fssc/mobile/resource/images/specialTicket.png";
		var html1 = "<div class=\"ld-remember-invoiceInfo-top\"><img src="+src+" alt=''><span class='fdInvoiceType'>"+fdInvoiceType+"</span>" +
					"<span class=\"ld-remember-invoiceInfo-top-satuts ld-remember-invoiceInfo-top-satuts-"+(params.fdCheckStatus||0)+"\">"+fdCheckStatus+"</span>";
	    if(params.fdIsCurrent){
            html1+="<span id=\"isCurrent"+currentIndex+"\" style=\"margin-left:0.2rem;font-size:0.2rem;width:1rem;\" class=\"ld-remember-invoiceInfo-top-satuts-"+params.fdIsCurrent+"\">"+fsscLang["fssc-mobile:invoice.is.current"]+"</span>";
	    }
	    html1+="<input name='row-invoice-index"+currentIndex+"' value="+currentIndex+" hidden='true'></div><i onclick=\"deleteInvoice()\"></i>";
		var html2 = $("<span class='fdInvoiceNumber'>"+params.InvoiceNumber+"</span>"+
					"<div><span>￥</span><span class='fdTotalAmount'>"+(params.TotalAmount?params.TotalAmount:"")+"</span></div>");
		var divTop = $("<div class=\"ld-remember-invoiceInfo-top\"></div>").append(html1);
		var divBottom = $("<div class=\"ld-remember-invoiceInfo-bottom\"></div>").append(html2);
		var divHidden = $("<div style=\"display: none;\">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdTotalAmount' value="+(params.TotalAmount?params.TotalAmount:'')+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdInvoiceCode' value="+(params.InvoiceCode?params.InvoiceCode:'')+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdInvoiceNumber' value="+(params.InvoiceNumber?params.InvoiceNumber:'')+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdCheckCode' value="+(params.CheckCode?params.CheckCode:'')+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdInvoiceDate' value="+(params.BillingDate?params.BillingDate:'')+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdPurchaserName' value="+(params.PurchaserName?params.PurchaserName:'')+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdPurchaserTaxNo' value="+(params.PurchaserTaxNo?params.PurchaserTaxNo:'')+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdSalesName' value="+(params.SalesName?params.SalesName:'')+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdTotalTax' value="+(params.TotalTax?params.TotalTax:'')+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdAttId' value="+(params.attId?params.attId:'')+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdInvoiceType' value="+fdInvoiceType+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdInvoiceTypeId' value="+(params.fdInvoiceTypeId?params.fdInvoiceTypeId:'')+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdCheckStatus' value="+(params.fdCheckStatus?params.fdCheckStatus:'0')+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdState' value="+(params.fdState?params.fdState:'0')+">"+
						  "<input name='fdInvoiceList_form["+currentIndex+"].fdId' value="+fdId+">"+
				    "</div>");
		var li = $("<li onclick=\"editInvoice(this)\"></li>").append(divTop,divBottom,divHidden);
		$("#invoiceListId").append(li);
	}	
	
	//验真
	function checkInvoice(){
		var currentIndex=$("input[name='currentInvoiceIndex']").val();
		if(!currentIndex){
			currentIndex=0;
		}
		var fdCheckStatus=$("input[name='fdInvoiceList_form["+currentIndex+"].fdCheckStatus']").val();
		$(".ld-main").show();//显示处理中
		if(fdCheckStatus!="1"){
			var fdTotalAmount=$("input[name='fdJshj']").val();
			var fdTotalTax=$("input[name='fdTotalTax']").val();
			if(!$("input[name='fdInvoiceTypeId']").val()||!$("input[name='fdInvoiceNumber']").val()||!$("input[name='fdInvoiceDate']").val()||!fdTotalAmount){
				jqtoast(fsscLang["fssc-mobile:message.check.invoiceInfo"]);
				$(".ld-main").hide();//隐藏处理中
				return ;
			}
			var params=[];
			var param= {
		        	"fdInvoiceType":$("input[name='fdInvoiceTypeId']").val(),
		        	"fdInvoiceNumber":$("input[name='fdInvoiceNumber']").val(),
		        	"fdInvoiceCode":$("input[name='fdInvoiceCode']").val(),
		        	"fdCheckCode":$("input[name='fdCheckCode']").val(),
		        	"fdInvoiceDate":$("input[name='fdInvoiceDate']").val(),
		        	"fdNoTaxMoney":fdTotalTax?subPoint(fdTotalAmount,fdTotalTax):fdTotalAmount
	        	}
			params.push(param);
			 $.ajax({
			        type: 'post',
			        async:false,
			        url:Com_Parameter.ContextPath+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=checkInvoice',
			        data:{params:JSON.stringify(params)},
		    }).success(function (data) {
		    	$(".ld-main").hide();//隐藏处理中
		 	   	var rtn = JSON.parse(data);
		 	   	if(rtn.result =="success"){
		 		  jqtoast(fsscLang["fssc-mobile:check.success"]);
		 		  var key="";
		 		  var number=$("input[name='fdInvoiceNumber']").val();
		 		  if(number){
		 			  key+=number;
		 		  }
	       		  var code=$("input[name='fdInvoiceCode']").val();
	       		 if(code){
		 			  key+=code;
		 		  }
	       		  if(rtn[key]&&rtn[key]['fdCheckStatus']){
	       			  $("input[name='fdCheckStatus']").val(rtn[key]['fdCheckStatus']);
	       		  }
	       		  if(rtn[key]&&rtn[key]['fdState']){
	       			  $("input[name='fdState']").val(rtn[key]['fdState']);
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
	
	//编辑
	function editInvoice(e) {
		 var index =  $(e).find("input[name^='row-invoice-index']").val();
		 $("input[name='currentInvoiceIndex']").val(index);
		 $("input[name='editFlag']").val(1);  //0,新增；1编辑
		 $("[name='fdJshj']").val($("[name='fdInvoiceList_form["+index+"].fdTotalAmount']").val());
		 $("[name='fdInvoiceCode']").val($("[name='fdInvoiceList_form["+index+"].fdInvoiceCode']").val());
		 $("[name='fdInvoiceNumber']").val($("[name='fdInvoiceList_form["+index+"].fdInvoiceNumber']").val());
		 $("[name='fdCheckCode']").val($("[name='fdInvoiceList_form["+index+"].fdCheckCode']").val());
		 $("[name='fdInvoiceDate']").val($("[name='fdInvoiceList_form["+index+"].fdInvoiceDate']").val());
		 $("[name='fdTotalTax']").val($("[name='fdInvoiceList_form["+index+"].fdTotalTax']").val());
		 $("[name='fdPurchaserName']").val($("[name='fdInvoiceList_form["+index+"].fdPurchaserName']").val());
		 $("[name='fdPurchaserTaxNo']").val($("[name='fdInvoiceList_form["+index+"].fdPurchaserTaxNo']").val());
		 $("[name='fdSalesName']").val($("[name='fdInvoiceList_form["+index+"].fdSalesName']").val());
		 $("[name='fdTotalTax']").val($("[name='fdInvoiceList_form["+index+"].fdTotalTax']").val());
		 $("[name='fdInvoiceType']").val($("[name='fdInvoiceList_form["+index+"].fdInvoiceType']").val());
		 $("[name='fdInvoiceTypeId']").val($("[name='fdInvoiceList_form["+index+"].fdInvoiceTypeId']").val());
		if("10100"==$("[name='fdInvoiceList_form["+index+"].fdInvoiceTypeId']").val()||"30100"==$("[name='fdInvoiceList_form["+index+"].fdInvoiceTypeId']").val()){
			$(".isVat").show();
		}
		 $("[name='fdCheckStatus']").val($("[name='fdInvoiceList_form["+index+"].fdCheckStatus']").val());
		 $("[name='fdState']").val($("[name='fdInvoiceList_form["+index+"].fdState']").val());
		 var fdAttId=$("[name='fdInvoiceList_form["+index+"].fdAttId']").val();
		 if(fdAttId){
			 $.ajax({
				url:Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getAttName',
				data:{data:JSON.stringify({fdAttId:fdAttId})},
				async:false,
				success:function(rtn){
					if(rtn){
						rtn = JSON.parse(rtn);
						var attName=rtn.attName;
						if(attName&&attName.indexOf(".pdf")>-1){//是pdf文件，不做展现
							 $("#detail_img").attr('src',Com_Parameter.ContextPath +'fssc/mobile/fssc_mobile_public/fsscMobilePublic.do?method=readDownload&fdId='+fdAttId);
							$(".ld-invoice-img").attr('style','display:none;');
						}else{
							 $("#detail_img").attr('src',Com_Parameter.ContextPath +'fssc/mobile/fssc_mobile_public/fsscMobilePublic.do?method=readDownload&fdId='+fdAttId);
							 $(".ld-invoice-img").attr('style','display:block;');
						}
					}
				}
			});
		 }else{
			 $(".ld-invoice-img").attr('style','display:none;');
		 }
		 $('.ld-invoice-detail-body').addClass('ld-invoice-detail-body-show');
		 $('.ld-rememberOne-footer').addClass('ld-rememberOne-footer-top');
	}
	
	function viewMoreInfo(obj){
		var src=$('#detail_img').attr('src');
		$("#more_info").attr('src',src);
		$(".ld-invoice-modal").attr('style','display:block;');
	}
	
	function closeImg(){
		$(".ld-invoice-modal").attr('style','display:none;');
	}
	
	

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
			 var picker = new Picker({
		        data:[fdInvoiceItemData]
		     });
		     picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
		    	 fdInvoiceType.value = fdInvoiceItemData[selectedIndex[0]].text;
		    	 fdInvoiceTypeId.value = fdInvoiceItemData[selectedIndex[0]].value;
				 if("10100"==fdInvoiceItemData[selectedIndex[0]].value||"30100"==fdInvoiceItemData[selectedIndex[0]].value){//显示专票必填信息
					 $(".isVat").show();
				 }else{//隐藏专票字段的必填提示
					 $(".isVat").hide();
				 }
		     });
	    	 picker.show();
	 }

$(".ld-cost-modal-main-left").click(function(){
	var divHeight=$(window).height()-$("#second_item").offset().top;   //二级费用类型div高度,窗口高度-div距离顶部高度=div高度;
	var itemHeight=$("#second_item").find("li").length*($("#second_item").find("li").height()+2);  //展现的费用类型个数*高度
	if(itemHeight>divHeight){
		$(".ld-cost-modal-main-right").attr("style","overflow-y:scroll;overflow-x：hidden");//设置滚动
		$(".ld-cost-modal-main-right-item").css("height",itemHeight);
	}else{
		$(".ld-cost-modal-main-right").attr("style","");
		$(".ld-cost-modal-main-right-item").css("height",divHeight);
	}
});
