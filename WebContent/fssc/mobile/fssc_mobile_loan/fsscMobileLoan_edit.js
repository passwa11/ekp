var dayFlag=true;  //全局变量
var apply_money_params,total_money_target,detail_div_body;
$(document).ready(function(){
 		$('.txtstrong').remove();
 		$('.inputsgl').removeClass();
 		var dateObj=$(".dateClass").find("input");
 		for(var i=0;i<dateObj.length;i++){
 			if($($(".dateClass").find("input").get(i)).data("show")!="readOnly"){
 				var id=$(".dateClass").find("input").get(i).getAttribute("id");
 	 			var name=$(".dateClass").find("input").get(i).getAttribute("name");
 	 			selectTime(id,name,true);
 			}
 		}
 		var fdIsTransfer = $("input[name='fdIsTransfer']").val();
		initFS_GetFdIsTransfer(fdIsTransfer);
 	});

	//初始化收款账户信息是否必填
	function initFS_GetFdIsTransfer(fdIsTransfer){
		if(fdIsTransfer != 'false'){
			$('input[name="fdAccPayeeName"]').attr("validator","required");
			$('input[name="fdPayeeBank"]').attr("validator","required");
			$('input[name="fdPayeeAccount"]').attr("validator","required");
			$('input[name="fdBankAccountNo"]').attr("validator","required");
			$('input[name="fdAccountAreaName"]').attr("validator","required");
			$(".vat").show();
		} else {
			$('input[name="fdAccPayeeName"]').attr("validator","");
			$('input[name="fdPayeeBank"]').attr("validator","");
			$('input[name="fdPayeeAccount"]').attr("validator","");
			$('input[name="fdBankAccountNo"]').attr("validator","");
			$('input[name="fdAccountAreaName"]').attr("validator","");
			$(".vat").hide();
		}
	}
   
    if (isdingding()) {
        dd.ready(function() {
            dd.ui.webViewBounce.disable();
        });
    }
    // 禁用body滚动
    function forbiddenScroll() {
    		document.body.style.overflow='hidden'
    }
    // 启用滚动
    function ableScroll() {
        document.body.style.overflow='auto'
    }
    // 新增账户
    $('.ld-newApplicationForm-account-btn').click(function() {
        $('.ld-addAccount-body').addClass('ld-addAccount-body-show')
        forbiddenScroll();
    })
    $('.ld-addAccount-btn').click(function() {
        $('.ld-addAccount-body').removeClass('ld-addAccount-body-show')
        ableScroll();
    })


/*************************************************************************
 * 选择对象
*************************************************************************/
function selectObject(id,name,dataSource,callback){
	var docTemplateId=$("[name='fdTemplateId']").val();
	// 当前借款人id
	var fdPersonId = $("input[name='fdLoanPersonId']").val();
	var event = event ? event : window.event;
	var obj = event.srcElement ? event.srcElement : event.target;
	if(docTemplateId){
		$.ajax({
			url: dataSource,
			type: 'post',
			data:{fdCompanyId:$("input[name='fdCompanyId']").val(),fdPersonId:fdPersonId,fdModelName:"com.landray.kmss.fssc.loan.model.FsscLoanMain"},
			async:false,
		}).error(function(data){
				console.log("获取信息失败"+data);
		}).success(function(data){
			 console.log("获取信息成功");
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
	          picker.on('picker.select', function (selectedVal, selectedIndex) {
	        	  
	           });
	          picker.on('picker.change', function (index, selectedIndex) {
	        	  
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
	              	           url:dataSource,
	              	           data: {"keyword":keyword,fdCompanyId:$("input[name='fdCompanyId']").val(),fdPersonId:fdPersonId},
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
	   	           url:dataSource,
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
}
    
  //切换借款人重新加载借款人相应信息
    function changeFdLoanPerson(){
        var fdLoanPersonId = $('input[name=fdLoanPersonId]').val();
        var fdLoanPersonName = $('input[name=fdLoanPersonName]').val();
        var fdOldLoanPersonId=$("input[name='fdOldLoanPersonId']").val();
        var fdOldLoanPersonName=$("input[name='fdOldLoanPersonName']").val();
        var fdLoanCategoryId=$("input[name='fdTemplateId']").val();
        if(fdLoanPersonId&&fdOldLoanPersonId&&fdLoanPersonId!=fdOldLoanPersonId){
        	$.ajax({
                url: Com_Parameter.ContextPath+"fssc/loan/fssc_loan_main/fsscLoanMain.do?method=loadPersonInfo&fdPersonId="+fdLoanPersonId+"&fdLoanCategoryId="+fdLoanCategoryId,
                dataType:"json",
                success: function(data){
                	clearData();
        			$('input[name="fdLoanDeptId"]').val(data['fdDeptId']);
        			$('input[name="fdLoanDeptName"]').val(data['fdDeptName']);
                    $('input[name="fdCompanyId"]').val(data['fdCompanyId']);
                    $('input[name="fdCompanyName"]').val(data['fdCompanyName']);
                    $('input[name="fdBaseCurrencyId"]').val(data['fdBaseCurrencyId']);
                    $('input[name="fdBaseCurrencyName"]').val(data['fdBaseCurrencyName']);
                    $('input[name="fdCostCenterId"]').val(data['fdCostCenterId']);
                    $('input[name="fdCostCenterName"]').val(data['fdCostCenterName']);
                    $('input[name="fdBasePayWayId"]').val(data['fdBasePaywayId']);
                    $('input[name="fdBasePayWayName"]').val(data['fdBasePaywayName']);
                    $('input[name="fdAccPayeeName"]').val(data['fdBaseAccountName']);
                    $('input[name="fdPayeeAccount"]').val(data['fdBaseAccountBankAccount']);
                    $('input[name="fdPayeeBank"]').val(data['fdBaseAccountBankName']);
					$('input[name="fdAccountAreaName"]').val(data['fdBaseAccountAreaName']);
					$('input[name="fdBankAccountNo"]').val(data['fdBaseBankAccountNo']);
                    $('input[name="fdTotalLoanMoney"]').val(formatFloat(data['fdTotalLoanMoney'],2));
                    $('#fdTotalLoanMoney').html(formatFloat(data['fdTotalLoanMoney'],2));
                    $('input[name="fdTotalRepaymentMoney"]').val(formatFloat(data['fdTotalRepaymentMoney'],2));
                    $('#fdTotalRepaymentMoney').html(formatFloat(data['fdTotalRepaymentMoney'],2));
                    $('input[name="fdTotalNotRepaymentMoney"]').val(formatFloat(data['fdTotalNotRepaymentMoney'],2));
                    $('#fdTotalNotRepaymentMoney').html(formatFloat(data['fdTotalNotRepaymentMoney'],2));
                    $(".accountName").html(data['fdBaseAccountName']);
              	    $(".fdBankAccount").html(data['fdBaseAccountBankAccount']);
                }
            });
            //添加可冲销者
            var fdOffsetterIds = $("input[name='fdOffsetterIds']").val()+';';
            var fdOffsetterNames = $("input[name='fdOffsetterNames']").val()+';';
            var docCreatorId = $("input[name='docCreatorId']").val();
            //若新借款人不在可冲销者，则增加新借款人到可冲销者。
            if(fdOffsetterIds.indexOf(fdLoanPersonId)==-1){
            	fdOffsetterIds+=fdLoanPersonId+';';
            	fdOffsetterNames+=fdLoanPersonName+';';
            }
            //若旧借款人不是创建者则移除旧借款人
            if(fdOffsetterIds.indexOf(fdOldLoanPersonId)>-1&&fdOldLoanPersonId!=docCreatorId){
            	fdOffsetterIds=fdOffsetterIds.replace(fdOldLoanPersonId+';','').replace(';'+fdOldLoanPersonId,'');
            	fdOffsetterNames=fdOffsetterNames.replace(fdOldLoanPersonName+';','').replace(';'+fdOldLoanPersonName,'');
            }
            $("input[name='fdOldLoanPersonId']").val(fdLoanPersonId);
            $("input[name='fdOldLoanPersonName']").val(fdLoanPersonName);
            $("input[name='fdOffsetterIds']").val(fdOffsetterIds.substring(0,fdOffsetterIds.length-1));
            $("input[name='fdOffsetterNames']").val(fdOffsetterNames.substring(0,fdOffsetterNames.length-1));
        }
    }
  
  /*************************************************************************
   * 选择时间,isCurrent，是否从当前日期开始
   *************************************************************************/
  function selectTime(id,name,isCurrent){
  	var myDate = new Date(); 
  	var year = myDate.getFullYear();
  	var month = myDate.getMonth()*1+1;
  	var day =myDate.getDate();
  	var changeFunc=$("[id='"+id+"']").attr("changeFunc");
  	if(changeFunc){
  		new Mdate(id, {
 		   acceptId: id,
 		   acceptName: name,
 		   beginYear: year,
 		   beginMonth: isCurrent?month:"1",
 		   beginDay: isCurrent?day:"1",
 		   endYear: "2100",
 		   endMonth: "12",
 		   endDay: "1",
 		   format: "-",
 		   action:changeTime,
 		   params:'{"func":"'+changeFunc+'"}'
 		});	
  	}else{
  		new Mdate(id, {
 		   acceptId: id,
 		   acceptName: name,
 		   beginYear: year,
 		   beginMonth: isCurrent?month:"1",
 		   beginDay: isCurrent?day:"1",
 		   endYear: "2100",
 		   endMonth: "12",
 		   endDay: "1",
 		   format: "-"
 		});	
  	}
  }
  
  /**
   * 统一触发校验函数
   */
  function changeTime(json){
	  if(json){
		  var jsonObj=JSON.parse(json);
		  eval(jsonObj.func+"()");
	  }
  }
  
//预计还款日期不能早于填单日期
  function onChangFdExpectedDate() {
  	var docCreateTime = $('input[name="docCreateTime"]').val();
  	var fdExpectedDate = $('input[name="fdExpectedDate"]').val();
  	var time1 = new Date(docCreateTime.replace(/\-/gi, "/")).getTime();
  	var time2 = new Date(fdExpectedDate.replace(/\-/gi, "/")).getTime();
  	if (time1 != null && time1 != "" && time2 != null
  			&& time2 != "") {
  		if (time2 < time1) {
  			jqtoast(message["expectedDate.early"]);
			$('input[name="fdExpectedDate"]').val("");
  		}
  	}
  	var fdIsContRepayDay=$("[name='fdIsContRepayDay']").val();
	//无控制还款天数不作校验
	if(fdIsContRepayDay){
		var fdExpectedDate = Date.parse(fdExpectedDate);
		var currentDay = new Date();
		var total = (fdExpectedDate-currentDay.getTime());
		var day  = parseInt(total / (1000*24*60*60));//计算预计还款天数
		if(day-fdIsContRepayDay>0){ //预计还款日期相隔天数超过限定天数
			jqtoast(message["msg.expected.date.invalid.error"].replace('{day}',fdIsContRepayDay));
			$('input[name="fdExpectedDate"]').val("");
		}
	}
  }
  
  /*************************************************************************
   * 保存表单
   *************************************************************************/
  function submitForm(form,status,method){
	  if(!$("[name='docSubject']").val()&&$("[name='docSubject']").attr('readonly')&&$("[name='docSubject']").attr('readonly')!='readonly'){
		  jqtoast(fsscLang['errors.required'].replace('{0}',fsscLang['fssc-loan:fsscLoanMain.docSubject']));
		  return false;
	  }
	  var subFlag=true;
	  if('10'!=status){
		  subFlag= validateMainFeild();
	  }
	  if(!subFlag){
		 return false; 
	  }
	  $("[name=docStatus]").val(status);
	  var action = document.forms['fsscLoanMainForm'].action;
	  document.forms['fsscLoanMainForm'].action=Com_SetUrlParameter(action,"status",status);
	  //借款控制
      var fdPersonId = $("input[name='fdLoanPersonId']").val();
      var fdLoanCategoryId = $("input[name='fdTemplateId']").val();
      var fdLoanMoney = $("input[name='fdLoanMoney']").val();
      if(!fdPersonId || !fdLoanCategoryId || fdLoanMoney == '' || fdLoanMoney <=0){
          //满足条件，清空借款提示
          $("input[name='fdRemind']").val("");
          $("#fdRemindSpan").html("");
          Com_Submit(form, method); 
		}else if($("[name='checkVersion']").val()=="true"){
          var data = new KMSSData();
          var results = data.AddBeanData("fsscLoanMainService&authCurrent=true&flag=loanControl&fdPersonId="+fdPersonId+"&fdLoanCategoryId="+fdLoanCategoryId+"&fdLoanMoney="+fdLoanMoney).GetHashMapArray();
          if(results.length>0){
				var isTrue = results[0].isTrue;//是否满足条件 true满足条件，false不满足条件
				if(isTrue == "false"){
					var fdForbid = results[0].fdForbid;//刚柔控 1刚控，2柔控
					if(fdForbid == "1"){//刚控不允许提交
						jqtoast(results[0].errorMessage);
					}else{//柔控，弹出确认框
						jqalert({
				            title:'',
				            content:results[0].errorMessage,
				            yestext:message['button.ok'],
				            notext:message['button.cancel'],
				            yesfn:function () {
				            	 $("input[name='fdRemind']").val(results[0].errorMessage);
				                 $("#fdRemindSpan").html(results[0].errorMessage);
				                 Com_Submit(form, method); 
				            }
				        })
					}
				}else{
				    //满足条件，清空借款提示
	              $("input[name='fdRemind']").val("");
	              $("#fdRemindSpan").html("");
	              Com_Submit(form, method); 
				}
          }else{
	          //满足条件，清空借款提示
	          $("input[name='fdRemind']").val("");
	          $("#fdRemindSpan").html("");
	          Com_Submit(form, method); 
			}
		}else{
			Com_Submit(form, method); 
		}
	}
  
  /*************************************************************************
   * 校验主表单必填字段、数字类型等
   *************************************************************************/
  function validateMainFeild(form,status,type){
	  var subFlag=true;
	  //校验必填
	  $("input[validator*='required']:not([name$='_t'])").each(function(){
		  var subject=$(this).attr('subject');
		  if(!this.value){
			 jqtoast(message['errors.required'].replace('{0}',subject?subject:''));
			 subFlag=false;
			 return false;
		  }
	  });
	  if(!subFlag){
		  return false;
	  }
	  //校验货币
	  $("input[validator*='currency-dollar']").each(function(){
		 var subject=$(this).attr('subject');
		 var val=$(this).val();
		 if(!isCurrencyDollar(val)){
			var subject=$(this).attr('subject');
			jqtoast(message['errors.dollar'].replace('{0}',subject?subject:''));
			subFlag=false;
			return false;
		 }
	  });
	  return subFlag;
  }

  /*************************************************************************
   * 保存附件
   *************************************************************************/
  function uploadFile(files){
	  if(files.length==0){
		  return ;
	  }
	  $("#ld-main-upload").attr("style","display: block;");
	  var fdModelId = $("[name='fdId']").val();
	  for(var i=0;i<files.length;i++){
		  var xhr = new XMLHttpRequest();
		  xhr.open("POST", Com_Parameter.ContextPath +"fssc/mobile/fssc_mobile_public/fsscMobilePublic.do?method=saveAtt&key=attPayment&fdModelId="+fdModelId+"&fdModelName=com.landray.kmss.fssc.loan.model.FsscLoanMain&filename="+files[i].name+"&size="+files[i].size,false);
		  xhr.overrideMimeType("application/octet-stream");
		    // 监听变化
			xhr.onreadystatechange = function(e){
			     if(xhr.readyState===4){
			            if(xhr.status===200){
			                //上传成功
			            	if(xhr.response){
			            		var rtn = JSON.parse(xhr.response);
			            		var attId=rtn.fdId;
			            		var attName=rtn.fdName;
			            		var attHtml="";
			            		attHtml+="<li><div data-attid=\""+attId+"\" class=\"ld-remember-attact-info\" onclick=\"showAtt('"+attId+"','"+attName+"');\"><img src="+getSrcByName(attName)+" alt=\"\">";
			          		  	attHtml+="<span>"+attName+"</span></div><span onclick=\"deleteAtt('"+attId+"','"+attName+"');\"></span></li>";
			          		  $("#fdAttachListId").append(attHtml);
			            	}
			            	$("#ld-main-upload").attr("style","display: none;");
			            }else{
			         	   alert("上传附件失败！");
			            }
			        }
		    }
			 //直接发送二进制数据
		    if(xhr.sendAsBinary){
		        xhr.sendAsBinary(files[i]);
		    } else {
		        xhr.send(files[i]);
		    }
	  }
	  $("#loanAtt").val('');
  }
  
  
  //选择完公司，清空对应的数据
  function afterSelectCompany(){
	  var fdOldCompanyId=$("input[name='fdCompanyIdOld']").val();
	  var fdCompanyId=$("input[name='fdCompanyId']").val();
	  if(fdCompanyId&&fdOldCompanyId != fdCompanyId){//公司改动
		  clearData();
			$("input[name='fdCompanyIdOld']").val(fdCompanyId);
			var data = new KMSSData();
			data.AddBeanData("eopBasedataCompanyService&type=getStandardCurrencyInfo&authCurrency=true&fdCompanyId="+fdCompanyId);
			data = data.GetHashMapArray();
			if(data.length>0){
				$("input[name='fdBaseCurrencyId']").val(data[0].fdCurrencyId);
				$("input[name='fdBaseCurrencyName']").val(data[0].fdCurrencyName);
			}
			FSSC_ReloadCostCenter();	//加载默认的成本中心
			FSSC_ReloadPayway();  //加载默认的付款方式
		    FSSC_reloadBaseCurreny();//加载汇率，重新计算金额
		}
  }

//币种变动，重新计算汇率和金额
function FSSC_reloadBaseCurreny(){
	var fdCurrencyId=$("input[name='fdBaseCurrencyId']").val();
	var fdCompanyId=$("input[name='fdCompanyId']").val();
	data = new KMSSData();
	data = data.AddBeanData('eopBasedataExchangeRateService&authCurrent=true&type=getRateByCurrency&fdCurrencyId=' + fdCurrencyId + '&fdCompanyId=' + fdCompanyId).GetHashMapArray();
	if (data.length > 0 && data[0].fdExchangeRate != null) {
		$('input[name="fdExchangeRate"]').val(data[0].fdExchangeRate);
		FSSC_ChangeStandardMoney();
	} else {
		jqtoast(fsscLang['fssc-loan:tips.exchangeRateNotExist']);
		$('input[name="fdBaseCurrencyId"]').val("");
		$('input[name="fdBaseCurrencyName"]').val("");
		$("[name=fdExchangeRate]").val("");
	}
}

window.FSSC_ChangeStandardMoney= function(){
	var fdApplyMoney = $("[name='fdApplyMoney']").val();
	var fdExchangeRate= $('input[name="fdExchangeRate"]').val();
	if(fdApplyMoney&&fdExchangeRate){
		fdStandardMoney = multiPoint(fdApplyMoney,fdExchangeRate);
		$("[name='fdStandardMoney']").val(fdStandardMoney);
	}
}


function clearData(){
	  $("input[name='fdCostCenterId']").val("");//成本中心
		$("input[name='fdCostCenterName']").val("");
		$("input[name='fdFeeMainId']").val("");//关联事前
		$("input[name='fdFeeMainName']").val("");
		$("input[name='fdBaseProjectId']").val("");//关联事前
		$("input[name='fdBaseProjectName']").val("");
		$("input[name='fdBasePayWayId']").val("");//付款方式
		$("input[name='fdBasePayWayName']").val("");
		$("input[name='fdBaseWbsId']").val("");//wbs
		$("input[name='fdBaseWbsName']").val("");
		$("input[name='fdBaseInnerOrderId']").val("");//内部订单
		$("input[name='fdBaseInnerOrderName']").val("");
		$("input[name='fdBaseProjectAccountingId']").val("");//核算项目
		$("input[name='fdBaseProjectAccountingName']").val("");
  }
  
//加载默认的成本中心
  function FSSC_ReloadCostCenter(){
  	var fdCompanyId = $("[name=fdCompanyId]").val();
  	//重新带出成本中心
  	var data = new KMSSData();
  	data.AddBeanData("eopBasedataCostCenterService&authCurrent=true&flag=defaultCostCenter&fdCompanyId="+fdCompanyId+"&fdPersonId="+$("[name=fdLoanPersonId]").val());
  	data = data.GetHashMapArray();
  	if(data.length>0){
  		$("[name=fdCostCenterId]").val(data[0].id);
  		$("[name=fdCostCenterName]").val(data[0].name);
  	}
  }

  //加载默认的付款方式
  function FSSC_ReloadPayway(){
  	var fdCompanyId = $("[name=fdCompanyId]").val();
  	var data = new KMSSData();
  	data.AddBeanData("eopBasedataPayWayService&type=default&fdCompanyId="+fdCompanyId);
  	data = data.GetHashMapArray();
  	if(data.length>0){
  		$("input[name='fdBasePayWayId']").val(data[0].value);
  		$("input[name='fdBasePayWayName']").val(data[0].text);
  		$("input[name='fdIsTransfer']").val(data[0].fdIsTransfer);
  	 	initFS_GetFdIsTransfer($("input[name='fdIsTransfer']").val());
  	}
  }
  
  //选择付款方式默认带出对应的银行
  function afterSelectPayWay(data){
	 $("input[name='fdIsTransfer']").val(data.fdIsTransfer);
 	 initFS_GetFdIsTransfer($("input[name='fdIsTransfer']").val());
  }
  
  //选择完账户名处理
  function afterSelectAccount(data){
	  if(data){
		  $("input[name='fdAccPayeeName']").val(data.fdName);
		  $("input[name='fdPayeeAccount']").val(data.fdPayeeAccount);
		  $("input[name='fdPayeeBank']").val(data.accountName);
	  }
  }
  
 function afterSelectWbs(data){
	 if(data){
		 $("input[name='fdBaseWbsName']").val(data.name.split('.')[0]);
	 }
 }
 
 function afterSelectOrder(data){
	 if(data){
		 $("input[name='fdBaseInnerOrderName']").val(data.name.split('.')[0]);
	 }
 }
  
//切换项目清空wbs
  window.afterSelectProject = function(){
	  var fdOldBaseProjectId=$("input[name='fdOldBaseProjectId']").val();
	  var fdBaseProjectId=$("input[name='fdBaseProjectId']").val();
	  if(fdOldBaseProjectId!=fdBaseProjectId){
		  $("[name=fdBaseWbsId]").val("");
		  $("[name=fdBaseWbsName]").val("");
		  $("input[name='fdOldBaseProjectId']").val(fdBaseProjectId);
	  }
  }
 
  /****************************联动函数  end***************************************/
  
  
//保存单据之前更新附件fdModelId，fdModelName
  Com_Parameter.event.submit.push(function(){
	 if($("[name='docStatus']")=='10'){
		 return true;
	 }
  	var params = [],pass = true;
  	$(".ld-remember-attact-info").each(function(){
  		params.push({
  			fdAttId:$(this).data('attid'),
  			fdModelId:$("[name='fdId']").val(),
  			fdModelName:'com.landray.kmss.fssc.loan.model.FsscLoanMain'
  		})
  	})
  	if(params.length>0){
  		$.ajax({
  			url:Com_Parameter.ContextPath +"fssc/mobile/fssc_mobile_public/fsscMobilePublic.do?method=updateAtt",
  			data:{params:JSON.stringify(params)},
  			dataType:'json',
  			async:false,
  			success:function(rtn){
  				if(rtn.result!='success'){
  					pass = false;
  					jqtoast('更新附件异常：'+rtn.message);
  				}
  			}
  		})
  	}
  	return pass;
  })
  
  	//借款提交时，校验借款如果关联事前，借款金额不允许超出事前申请总金额
	Com_Parameter.event.submit.push(function(){
		var fdFeeId=$("[name='fdFeeMainId']").val();
		var fdLoanMoney=$("input[name='fdStandardMoney']").val();  //本次借款金额
		//无事前不作校验
		if(!fdFeeId){
			return true;
		}
		var pass = true;
		$.ajax({
			url:Com_Parameter.ContextPath + 'fssc/loan/fssc_loan_main/fsscLoanMain.do?method=checkLoanMoney&fdFeeId='+fdFeeId,
			async:false,
			success:function(rtn){
				rtn = JSON.parse(rtn);
				if(rtn.result=='1'){//正确返回结果
					if(rtn.rtnMoney-fdStandardMoney<0){//返回金额小于借款金额，说明不允许提交
						jqtoast(message['message.feeMoney.less.loanMoney']);
						pass=false;
					}
				}
			}
		});
		return pass;
	});
  //借款提交时，校验预计还款日期和创建时间差不能大于后台设置的还款天数
  Com_Parameter.event.submit.push(function(){
	  if($("[name='docStatus']")=='10'){
		return true;
	  }
	  var fdIsContRepayDay=$("[name='fdIsContRepayDay']").val();
	  var fdExpectedDate=$("input[name='fdExpectedDate']").val();  //创建时间
	  //无控制还款天数不作校验
	  if(!fdIsContRepayDay){
		  return true;
	  }
	  var pass = true;
	  var fdExpectedDate = Date.parse(fdExpectedDate);
		var currentDay = new Date();
		var total = (fdExpectedDate-currentDay.getTime());
		var day  = parseInt(total / (1000*24*60*60));//计算预计还款天数
		if(day-fdIsContRepayDay>0){
			this.getValidator('checkExpectedDate').error = lang['msg.expected.date.invalid.error'];
			return false;
		}
	  return pass;
  });
  
  //借款提交时，校验开户行、账号不能有空格
  Com_Parameter.event.submit.push(function(){
	  if($("[name='docStatus']")=='10'){
		return true;
	  }
	  var fdPayeeBank=$("[name='fdPayeeBank']").val();	//开户行
	  var fdPayeeAccount=$("[name='fdPayeeAccount']").val();  //账户
	  var fdBankAccountNo=$("[name='fdBankAccountNo']").val();  //银联号
	  var pass=true;
	  var str = /\s+/g;
	  if(str.test(fdPayeeBank)){
		  jqtoast(message["msg.check.fdPayeeBank.null"]);
		  return false;
	  }else if(str.test(fdPayeeAccount)){
		  jqtoast(message["msg.check.fdPayeeAccount.null"]);
		  return false;
	  }else if(str.test(fdBankAccountNo)){
		  jqtoast(message["msg.check.fdBankAccountNo.null"]);
		  return false;
	  }
	  return pass;
  });
  
  function selectSubmitType(id,name){
			$.post(
					Com_Parameter.ContextPath+'fssc/mobile/fs_mobile_data/fsscMobileRestful?method=getClaimantStatu',
					{fdPersonId:$("[name=docCreatorId]").val()},
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
  
  /****************************提交校验  end***************************************/
  function changeLoanMoney(obj){
  	$("[name=fdLoanMoney]").val(obj.value);
	  FSSC_ChangeStandardMoney();
  }
  
