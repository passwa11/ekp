seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str','lui/util/env','lang!fssc-budgeting','lang!'], function($, dialog, dialogCommon,strutil,env,lang,comlang){
	var fields=["fdTotal","fdPeriodOne","fdPeriodTwo","fdPeriodThree","fdPeriodFour","fdPeriodFive",
	            "fdPeriodSix","fdPeriodSeven","fdPeriodEight","fdPeriodNine","fdPeriodTen","fdPeriodEleven","fdPeriodTwelve"];
	if(window.navigator.userAgent.toLowerCase().indexOf("msie")>-1
			||window.navigator.userAgent.toLowerCase().indexOf("trident")>-1){//IE
		setTimeout(function(){initData();},3000);
	}else{//非IE
		LUI.ready(function(){
			initData();
		});
	};
	window.initData=function(){
		var schemeId=Com_GetUrlParameter(location.href,"fdSchemeId");
		if(schemeId){
			getPeriodBySchemeId(schemeId);
		}
		//增加页面宽度
		$(".tempTB").attr("style","width:100%; min-width:980px;margin:0px auto;");
		changRule(null,null);
	};
	//修改公司
	window.changeCompany=function(){
		var orgType=$("[name='orgType']").val();
		var fdOrgId=$("[name='fdOrgId']").val();
		var fdSchemeId=$("[name='fdSchemeId']").val();
		var fdCompanyId=$("[name='fdCompanyId']").val();
		var url=env.fn.formatUrl('/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingInit&fdSchemeId='+fdSchemeId
				+'&orgType='+orgType+'&fdOrgId='+fdOrgId+'&fdCompanyId='+fdCompanyId)
		dialog.confirm(lang['message.budgeting.change.company.confirm'],function(value){
		    if(!value){
		    	//取消还原公司值
		    	$("[name='fdCompanyId']").val($("[name='fdCurrentCompanyId']").val());
		    	$("[name='fdCompanyName']").val($("[name='fdCurrentCompanyName']").val());
				return;
			 }
	    	if(value==true){
	    		window.del_load = dialog.loading();
				Com_OpenWindow(url, '_self');
	    	}
	    });
	};
	/************************************************
	 * 填写完金额重新计算上级汇总金额
	 ************************************************/
	window.reCalculate=function(val,obj,isCheck){
		var index=obj.name.substring(obj.name.indexOf("[")+1,obj.name.indexOf("]"));
		var fieldName=obj.name.substring(obj.name.indexOf(".")+1,obj.name.length);
		var sumNum=0.0;  
		//月份赋值
		for(var n=1,size=fields.length;n<size;n++){
			var tarName="fdDetailList_Form["+index+"]."+fields[n];
			sumNum=addPoint(sumNum,$("[name='"+tarName+"']").val());
		}
		var fdTotal=$("[name='fdDetailList_Form["+index+"].fdTotal']").val();
		if("noCheck"!=isCheck&&fdTotal&&sumNum&&sumNum-fdTotal>0){
			dialog.alert(lang["budgeting.sumNum.total.tips"]);
			obj.value="";  //清空当前所填写的值
		}
		var fdParentId=$("[name='fdDetailList_Form["+index+"].fdParentId']").val();
		if(fdParentId){
			//获取fdParentId相同的列
			var inputObj=$("input[name$='fdParentId'][value="+fdParentId+"]");
			var totalAmount=0.0;
			for(var i=0;i<inputObj.length;i++){
				var _index=inputObj[i].name.substring(inputObj[i].name.indexOf("[")+1,inputObj[i].name.indexOf("]"));
				//累加本层级金额
				totalAmount=addPoint(totalAmount,$("[name='fdDetailList_Form["+_index+"]."+fieldName+"']").val());
			}
			//获取预算科目ID为fdParentId的列
			var parentObj=$("input[name$='fdBudgetItemId'][value="+fdParentId+"]");
			if(parentObj.length>0){
				var index_=parentObj[0].name.substring(parentObj[0].name.indexOf("[")+1,parentObj[0].name.indexOf("]"));
				var tarName="fdDetailList_Form["+index_+"]."+fieldName;
				$("[name='"+tarName+"']").val(Number(totalAmount).toFixed(2));
			}
			/*if(document.getElementById(tarId)){
				document.getElementById(tarId).value=Number(totalAmount).toFixed(2);
			}*/
		}
	};
	/************************************************
	 * 填写完年度金额自动平均分摊到12期
	 ************************************************/
	window.shareAmount=function(val,obj){
		if(isNaN(val)){
			return ;
		}
		var index=obj.name.substring(obj.name.indexOf("[")+1,obj.name.indexOf("]"));
		obj.value=formatFloat(val,2); //将当前填写金额格式化未两位小数
		reCalculate(val,obj);   //重新计算年度上级金额
		var shareVal=divPoint(val,12.00);  //年度金额除以12，保留两位小数
		var temp=0.0;
		//月份赋值
		for(var n=1,size=fields.length;n<size;n++){
			var tarName="fdDetailList_Form["+index+"]."+fields[n];
			if("fdPeriodTwelve"==fields[n]){
				$("[name='"+tarName+"']").val(subPoint(val,temp));
				reCalculate(subPoint(val,temp),$("[name='"+tarName+"']")[0]);   //重新计算月度上级金额
			}else{
				$("[name='"+tarName+"']").val(shareVal);
				temp=addPoint(temp,shareVal);
				reCalculate(shareVal,$("[name='"+tarName+"']")[0]);   //重新计算月度上级金额
			}
		}

	};
	/************************************************
	 * 填写完月度金额自动重新计算年度金额
	 ************************************************/
	window.gatherAmount=function(val,obj){
		if(isNaN(val)){
			return ;
		}
		var index=obj.name.substring(obj.name.indexOf("[")+1,obj.name.indexOf("]"));
		var sumNum=0.0;  
		//月份赋值
		for(var n=1,size=fields.length;n<size;n++){
			var tarName="fdDetailList_Form["+index+"]."+fields[n];
			sumNum=addPoint(sumNum,$("[name='"+tarName+"']").val());
		}
//		var fdTotal = $("[name='fdDetailList_Form["+index+"].fdTotal']").val();
//		if(fdTotal&&sumNum&&sumNum-fdTotal>0){
//			dialog.alert(lang["budgeting.sumNum.total.tips"]);
//			obj.value="";  //清空当前所填写的值
//		}else{
//			reCalculate(val,obj);   //重新计算该月度上级金额
//		}
		reCalculate(val,obj,"noCheck");   //重新计算该月度上级金额
		//重新累计全年金额
		$("[name='fdDetailList_Form["+index+"].fdTotal']").val(formatFloat(sumNum,2)); //将当前填写金额格式化未两位小数
		//reCalculate(fdTotal,$("[name='fdDetailList_Form["+index+"].fdTotal']")[0]);   //重新计算月度上级金额
	};
	/************************************************
	 * 重写提交事件
	 ************************************************/
	window.Fssc_SubmitForm=function(status, method, isDraft){
		if($("[name*='fdDetailList_Form[!{index}]']").length>0){ //明细基准行来不及隐藏，导致提交报错
			$("[kmss_isreferrow='1']").remove();
		}
		//新建页面，预算总额=所有顶级预算科目总额
		//获取fdParentId为空的列
		var inputObj=$("input[name$='fdParentId'][value='']");
		var totalAmount=0.0;
		for(var i=0;i<inputObj.length;i++){
			var _index=inputObj[i].name.substring(inputObj[i].name.indexOf("[")+1,inputObj[i].name.indexOf("]"));
			var tarName="fdDetailList_Form["+_index+"].fdTotal";
			if($("[name='"+tarName+"']").val()){
				totalAmount=addPoint(totalAmount,$("[name='"+tarName+"']").val());
			}
		}
		$("[name='fdTotalMoney']").val(totalAmount);
		//获取预算编制表格
		var len=$("#TABLE_DocList_fdDetailList_Form").find("tr").length-2;
		for(var i=0;i<len;i++){
			var fdStatus=$("[name='fdDetailList_Form["+i+"].fdStatus']").val();
			if(!fdStatus||fdStatus=='1'||fdStatus=='3'){
				//当明细审核状态为空、草稿、驳回状态，则给状态重新赋值
				if(isDraft){//暂存
					$("[name='fdDetailList_Form["+i+"].fdStatus']").val("1");  //草稿
				}else{//提交审核
					$("[name='fdDetailList_Form["+i+"].fdStatus']").val("2");  //提交
				}
			}
		}
		//主文档状态赋值
		if(isDraft){//暂存
			$("[name='fdStatus']").val("1");  //草稿
		}else{//提交审核
			$("[name='fdStatus']").val("2");  //提交
		}

		submitForm(status, method, isDraft);
	};
});
