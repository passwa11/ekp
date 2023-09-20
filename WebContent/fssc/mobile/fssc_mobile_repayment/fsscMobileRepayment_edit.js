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
 	});

   
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
    
    //选择借款
    function selectLoanMain(){
    	selectObject('fdLoanMainId','fdLoanMainName',Com_Parameter.ContextPath+'fssc/loan/fssc_loan_repay_mobile/fsscLoanRepayMobile.do?method=getLoanMain',{type:'repayment',fdLoanReCategoryId:$("input[name='fdTemplateId']").val()},afterSelectLoan);
    }
    //选择付款方式
    function selectPayway(){
    	var fdLoanId=$("input[name='fdLoanMainId']").val();
    	if(fdLoanId){
    		selectObject('fdBasePayWayId','fdBasePayWayName',Com_Parameter.ContextPath+'fssc/loan/fssc_loan_repay_mobile/fsscLoanRepayMobile.do?method=getFsBasePay',{fdLoanId:fdLoanId});
    	}else{
    		jqtoast(message['select.loan.before']);
    	}
    }
    //获取借款未冲销金额
    function afterSelectLoan(){
    	var fdLoanId=$("input[name='fdLoanMainId']").val();
    	var fdOldLoanId=$("input[name='fdOldLoanMainId']").val();
    	if(fdLoanId&&fdLoanId!=fdOldLoanId){
    		$("input[name='fdBasePayWayId']").val("");//付款方式
            $("input[name='fdBasePayWayName']").val("");
            getFdCanOffsetMoney(fdLoanId);
    	}
    }
/*************************************************************************
 * 选择对象
*************************************************************************/
function selectObject(id,name,dataSource,params,callback){
	var docTemplateId=$("[name='fdTemplateId']").val();
	var event = event ? event : window.event;
	var obj = event.srcElement ? event.srcElement : event.target;
	$("#ld-main-data-loading").show();
	if(docTemplateId){
		$.ajax({
			url: dataSource,
			type: 'post',
			data:params,
			async:true,
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
	      	 if(curValue!=null&&objData){
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
		        		  callback(); 
		        	  }
	        	  }
	           });
	          $("#ld-main-data-loading").hide();
	          picker.show();
	          //回车搜索
	          $("#search_input").keypress(function (e) {
	              if (e.which == 13) {
	              	var keyword=$("[name='pick_keyword']").val();
	              	params.keyword=keyword;
	              	if(keyword){
	              		$.ajax({
	              	           type: 'post',
	              	           url:dataSource,
	              	           data: params,
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
  
  /*************************************************************************
   * 保存表单
   *************************************************************************/
  function submitForm(form,status,method){
	  if(!$("[name='docSubject']").val()&&$("[name='docSubject']").attr('readonly')&&$("[name='docSubject']").attr('readonly')!='readonly'){
		  jqtoast(fsscLang['errors.required'].replace('{0}',fsscLang['fssc-loan:fsscLoanRepayment.docSubject']));
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
	  var action = document.forms['fsscLoanRepaymentForm'].action;
	  document.forms['fsscLoanRepaymentForm'].action=Com_SetUrlParameter(action,"status",status);
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
	  //校验必填
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
		  xhr.open("POST", Com_Parameter.ContextPath +"fssc/mobile/fssc_mobile_public/fsscMobilePublic.do?method=saveAtt&key=attachment&fdModelId="+fdModelId+"&fdModelName=com.landray.kmss.fssc.loan.model.FsscLoanMain&filename="+files[i].name+"&size="+files[i].size,false);
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
  			fdModelName:'com.landray.kmss.fssc.loan.model.FsscLoanRepayment'
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
  
  	//还款提交时，校验还款金额不能大于未还金额
	Com_Parameter.event.submit.push(function(){
		if($("[name='docStatus']")=='10'){
			return true;
		}
		var pass = true;
	    var fdRepaymentMoney = $("input[name='fdRepaymentMoney']").val();
	    var fdLoanMainId = $("input[name='fdLoanMainId']").val();
	    if(fdRepaymentMoney&&fdLoanMainId){
	    	var fdCanOffsetMoney = getFdCanOffsetMoney(fdLoanMainId);//获取未冲销金额
		    if(fdCanOffsetMoney*1 < fdRepaymentMoney*1){
		    	jqtoast(message['repay.less.loan'].replace("%money%", fdCanOffsetMoney));
		        pass = false;
		    }
	    }
		return pass;
	});
//获取借款单未冲销金额
  function getFdCanOffsetMoney(fdLoanMainId){
      var fdCanOffsetMoney = 0;
      var data = new KMSSData();
      var results = data.AddBeanData("fsscLoanMainService&authCurrent=true&flag=getCanUseMoney&fdLoanMainId=" + fdLoanMainId).GetHashMapArray();
      if (results.length > 0&&results[0].fdMoney) {
          $("input[name='fdCanOffsetMoney']").val(formatScientificToNum(results[0].fdMoney,2));//未冲销金额
          fdCanOffsetMoney = results[0].fdMoney;
      }
      return fdCanOffsetMoney;
  }
  
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
  
