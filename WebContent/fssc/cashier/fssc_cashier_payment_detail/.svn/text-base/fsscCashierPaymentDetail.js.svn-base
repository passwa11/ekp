seajs.use(['lui/framework/module','lui/jquery','lui/dialog','lui/topic','lui/spa/const','lui/util/env','lui/toolbar','lang!fssc-cashier','lang!'],
		function(Module, jquery, dialog, topic,spaConst,env ,toolbar,lang,comlang){
	/**重新生成付款单,
	 * fdModelId：主表ID
	 * fdModelName：主表modelName
	 * TABLE_DocList_ID：数据来源明细ID，若无则传null值
	 * extr:需要移除的明细行数，非数据行
	 * fdPayWayId:付款方式ID属性名;对应的td位置
	 * fdPayBankId：付款银行ID属性名;对应的td位置
	 * fdCurrencyId：币种属性名;对应的td位置
	 * fdExchangeRate：汇率属性名;对应的td位置
	 * fdAccountName：收款人账户名属性名;对应的td位置
	 * fdBankName：收款人开户行属性名;对应的td位置
	 * fdBankAccount：收款人账号属性名;对应的td位置
	 * fdMoney：收款金额属性名;对应的td位置
	 */
    window.refreshPaymentForm = function (fdModelId,TABLE_DocList_ID,extr,fdPayWayId,fdPayBankId,fdCurrencyId,fdExchangeRate,
    		fdAccountName,fdBankName,fdBankAccount,fdMoney,fdAccountAreaName,fdAccountAreaCode) {
    	var len = $("#"+TABLE_DocList_ID+" >tbody>tr").length-extr;
    	var tableName=TABLE_DocList_ID.substring(14,TABLE_DocList_ID.length);
		var  payArr= [];
		for(var i=0;i<len;i++){
		  payArr.push({
			    'fdDetailModelId':$("[name='"+tableName+"["+i+"].fdId']").val(),
			    'fdPayWayId':$("[name='"+tableName+"["+i+"]."+fdPayWayId.split(";")[0]+"']").val(),
	    		'fdBankId':$("[name='"+tableName+"["+i+"]."+fdPayBankId.split(";")[0]+"']").val(),
	    		'fdCurrencyId':$("[name='"+tableName+"["+i+"]."+fdCurrencyId.split(";")[0]+"']").val(),
	    		'fdExchangeRate':$("[name='"+tableName+"["+i+"]."+fdExchangeRate.split(";")[0]+"']").val(),
	    		'fdAccountName':$("[name='"+tableName+"["+i+"]."+fdAccountName.split(";")[0]+"']").val(),
	    		'fdBankName':$("[name='"+tableName+"["+i+"]."+fdBankName.split(";")[0]+"']").val(),
	    		'fdBankAccount':$("[name='"+tableName+"["+i+"]."+fdBankAccount.split(";")[0]+"']").val(),
	    		'fdMoney':$("[name='"+tableName+"["+i+"]."+fdMoney.split(";")[0]+"']").val(),
                'fdAccountAreaName':$("[name='"+tableName+"["+i+"]."+fdAccountAreaName.split(";")[0]+"']").val(),
                'fdAccountAreaCode':$("[name='"+tableName+"["+i+"]."+fdAccountAreaCode.split(";")[0]+"']").val()
	    	});
	    } 
        dialog.confirm(lang['button.refresh.paymentForm'], function(isOk) {
           if(isOk) {
        	   var paramJson = JSON.stringify(payArr);
               var params={"fdModelId":fdModelId,"paramJson":paramJson};
               $.ajax( {
                   url:env.fn.formatUrl("/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do?method=refreshPaymentForm"),
                   type: 'POST', // POST or GET
                   dataType:"json",
                   data:params,
                   async:false,    //用同步方式   async. 默认是true，即为异步方式
                   success:function(data){
                       if(data.fdIsBoolean){
                           dialog.success(comlang["return.optSuccess"]);
                           LUI("listview_paymentDetail").source.get();
                       }else{
                           dialog.alert(lang['button.refresh.cashier.error.message'].replace("%text%", data.messageStr))
                       }
                   }
               });
           }
       });
        
      }
    
    /**
     * 重新生成付款单，直接将数据以JSON格式传递进来
     */
    window.refreshPaymentMainForm = function(paramJson,fdModelId){
    	var param = JSON.stringify(paramJson);
    	dialog.confirm(lang['button.refresh.paymentForm'], function(isOk) {
            if(isOk) {
               var params={"fdModelId":fdModelId,"paramJson":param};
                $.ajax( {
                    url:env.fn.formatUrl("/fssc/cashier/fssc_cashier_payment/fsscCashierPayment.do?method=refreshPaymentForm"),
                    type: 'POST', // POST or GET
                    dataType:"json",
                    data:params,
                    async:false,    //用同步方式   async. 默认是true，即为异步方式
                    success:function(data){
                        if(data.fdIsBoolean){
                            dialog.success(comlang["return.optSuccess"]);
                            LUI("listview_paymentDetail").source.get();
                        }else{
                            dialog.alert(lang['button.refresh.cashier.error.message'].replace("%text%", data.messageStr))
                        }
                    }
                });
            }
    });
    }
});
