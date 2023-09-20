seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str','lang!fssc-loan'], function($, dialog, dialogCommon,strutil,lang){
	if(window.navigator.userAgent.toLowerCase().indexOf("msie")>-1
			||window.navigator.userAgent.toLowerCase().indexOf("trident")>-1){//IE
				setTimeout(function(){initData();},3000);
		}else{//非IE
			LUI.ready(function(){
				initData();
			});
		};
		window.initData=function(){
			var loanMoney=$("input[name='fdLoanMoney']").val();
			$("#fdLoanUpperMoney").html(FSSC_MenoyToUppercase(loanMoney));
			initFS_GetFdIsTransfer();//初始化收款账户信息是否必填
		}
	window.initFS_GetFdIsTransfer = function(){
		var fdIsTransfer = $("input[name='fdIsTransfer']").val();
		if(fdIsTransfer =='false'){	//收款账户信息非必填
			$('input[name="fdAccPayeeName"]').attr("validate","");
			$('input[name="fdPayeeBank"]').attr("validate","checkNull");
			$('input[name="fdPayeeAccount"]').attr("validate","checkNull");
			$('input[name="fdBankAccountNo"]').attr("validate","checkNull");
			$('input[name="fdAccountAreaName"]').attr("validate","maxLength(200)");
			$(".vat").hide();
		}else{	//收款账户信息必填
			$('input[name="fdAccPayeeName"]').attr("validate","required");
			$('input[name="fdPayeeBank"]').attr("validate","required checkNull");
			$('input[name="fdPayeeAccount"]').attr("validate","required checkNull");
			$('input[name="fdBankAccountNo"]').attr("validate","required checkNull");
			$('input[name="fdAccountAreaName"]').attr("validate","required maxLength(200)");
			$(".vat").show();
		}
	}
	window.loanToRepayment=function(){
    	dialog.simpleCategoryForNewFile('com.landray.kmss.fssc.loan.model.FsscLoanReCategory', '/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=add&i.docTemplate=!{id}&fdLoanId='+Com_GetUrlParameter(location.href, 'fdId'),
    			false,null,null,getValueByHash("docTemplate"));
	};
	function getValueByHash(key){
        var value = Com_GetUrlParameter(location.href, 'q.'+key);
        if(value){
            return value;
        }
        var hash = window.location.hash;
        if(hash.indexOf(key)<0){
            return "";
        }
        var url = hash.split("cri.q=")[1];
            var reg = new RegExp("(^|;)"+ key +":([^;]*)(;|$)");
        var r=url.match(reg);
        if(r!=null){
            return unescape(r[2]);
        }
        return "";
    }
	LUI.ready(function(){
    	var extendFields = $(".extendFields>td").length;
    	$(".extendFields>td").eq(extendFields-1).attr("colspan",1+6-extendFields);
    })
  //收款明细选择收款人账户
    window.FSSC_SelectAccount = function(){
    	dialogSelect(false,'eop_basedata_account_fdAccount','fdAccPayeeId','fdAccPayeeName',null,{fdPersonId:$("[name=fdLoanPersonId]").val()},function(rtnData){
    		if(rtnData && rtnData.length > 0){
    			var obj = rtnData[0];
    			$("input[name='fdPayeeAccount']").val(obj.fdBankAccount);//开户行账号
    			$("input[name='fdPayeeBank']").val(obj.fdBankName);//开户行
    			$("input[name='fdAccountAreaName']").val(obj.fdAccountArea);//开户地
    			$("input[name='fdBankAccountNo']").val(obj.fdBankNo);//银联号
    		}
    	});
    }
	//选择付款方式回调函数
	window.afterSelectPayWay = function(rtnData){
		if(rtnData && rtnData.length > 0){
			var obj = rtnData[0];
			var fdIsTransfer = obj.fdIsTransfer;
			$("input[name='fdIsTransfer']").val(fdIsTransfer);
			$("[name='fdBankId']").val(rtnData[0]['fdDefaultPayBank.fdId']);
			$("[name='fdBankName']").val((rtnData[0]['fdDefaultPayBank.fdBankName']||'')+(rtnData[0]['fdDefaultPayBank.fdBankAccount']||''));
			initFS_GetFdIsTransfer();
		}
	}
	
	window. initFS_GetFdIsTransfer = function(){
		var fdIsTransfer = $("input[name='fdIsTransfer']").val();
		if(fdIsTransfer =='false'){	//收款账户信息非必填
			$('input[name="fdAccPayeeName"]').attr("validate","");
			$('input[name="fdPayeeBank"]').attr("validate","checkNull");
			$('input[name="fdPayeeAccount"]').attr("validate","checkNull");
			$('input[name="fdBankAccountNo"]').attr("validate","checkNull");
			$('input[name="fdAccountAreaName"]').attr("validate","maxLength(200)");
			$(".vat").hide();
		}else{	//收款账户信息必填
			$('input[name="fdAccPayeeName"]').attr("validate","required");
			$('input[name="fdPayeeBank"]').attr("validate","required checkNull");
			$('input[name="fdPayeeAccount"]').attr("validate","required checkNull");
			$('input[name="fdBankAccountNo"]').attr("validate","required checkNull");
			$('input[name="fdAccountAreaName"]').attr("validate","required maxLength(200)");
			$(".vat").show();
		}
	}	
	
	//核准金额改变事件
	window.FSSC_ChangeStandardMoney= function(){
		var fdLoanMoney = $("[name='fdLoanMoney']").val();
		var fdExchangeRate= $('input[name="fdExchangeRate"]').val();
		if(fdLoanMoney&&fdExchangeRate){
			fdStandardMoney = multiPoint(fdLoanMoney,fdExchangeRate);
			$("[name='fdStandardMoney']").val(fdStandardMoney);
		}
	}
	//选择账户归属城市回调函数
	window.  selectFdAccountAreaCallback= function(rtnData){
		if(rtnData && rtnData.length > 0){
			var obj = rtnData[0];
			$("input[name='fdAccountAreaCode']").val(obj.fdProvincial);//开户行账号
			$("input[name='fdAccountAreaName']").val(obj.fdCity);//开户行
		}
	}

	//选择账户归属城市回调函数（cbs)
	window.selectFdAccountAreaCbsCallback= function(rtnData){
		if(rtnData && rtnData.length > 0){
			var obj = rtnData[0];
			$("input[name='fdAccountAreaCode']").val(obj.fdProvince);//开户行账号
			$("input[name='fdAccountAreaName']").val(obj.fdCity);//开户行
		}
	}
	//选择账户归属城市回调函数
	window.selectFdAccountAreaCmbIntCallback=function(rtnData){
		if(rtnData && rtnData.length > 0){
			var obj = rtnData[0];
			$("input[name='fdAccountAreaCode']").val(obj.fdProvincial);//开户行账号
			$("input[name='fdAccountAreaName']").val(obj.fdCity);//开户行
		}
	}
	//映翰通退件码查询
	window.codeQuery = function(){
		var fdId = Com_GetUrlParameter(location.href, 'fdId');
		$.ajax({
			url:Com_Parameter.ContextPath + 'fssc/loan/fssc_loan_main/fsscLoanMain.do?method=codeQuery',
			data:{params:fdId},
			async:false,
			success:function(rtn){
				rtn = JSON.parse(rtn);
				dialog.alert(rtn.massege);
			}
		});
	}
});
