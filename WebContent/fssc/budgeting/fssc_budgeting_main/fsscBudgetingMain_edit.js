 DocList_Info.push('TABLE_DocList_fdDetailList_Form');
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
		$(".tempTB").attr("style","width: 100%; min-width: 980px; margin: 0px auto;");
		var selfAmountJson,childAmountJson;
		if(formInitData['childAmountJson']){
			childAmountJson=JSON.parse(formInitData['childAmountJson']);//将json字符串转为对象
		}
		if(formInitData['selfAmountJson']){
			selfAmountJson=JSON.parse(formInitData['selfAmountJson']);//将json字符串转为对象
		}
		var fdChildTotalMoney=0.0;
		//获取预算编制表格所有的科目循环给汇总赋值
		var len=$("#TABLE_DocList_fdDetailList_Form").find("tr").length-2;
		for(var i=0;i<len;i++){
			var id=$("[name='fdDetailList_Form["+i+"].fdBudgetItemId']").val();
			var fdIsLastStage=$("[name='fdDetailList_Form["+i+"].fdIsLastStage']").val();
			var arr;
			if(childAmountJson){
				arr=childAmountJson[id];//非最末级，展现下级汇总
			}
			if(fdIsLastStage=='1'){//最末级，则从selfAmountJson获取值
				if(selfAmountJson){
					arr=selfAmountJson[id];
				}
			}
			if(id&&arr){
				for(var n=0,size=fields.length;n<size;n++){//用jquery赋值无效
					var tarId="fdDetailList_Form_display["+i+"]."+fields[n];
					$("[name='"+tarId+"']").val(Number(arr[n]).toFixed(2));
					var tarName="fdDetailList_noShow_Form["+i+"]."+fields[n];
					$("[name='"+tarName+"']").val(Number(arr[n]).toFixed(2));
					if(fdIsLastStage=='1'&&fields[n]=='fdTotal'){
						fdChildTotalMoney=addPoint(fdChildTotalMoney,arr[n]);
					}
				}
			}
		}
		$("[name='fdChildTotalMoney']").val(formatFloat(fdChildTotalMoney,2));
		var method=Com_GetUrlParameter(location.href,"method");
		if(method=='add'){  //编辑直接显示数据库值
			//新建页面，预算总额=下级预算汇总=所有顶级预算科目总额
			//获取fdParentId为空的列
			var inputObj=$("input[name$='fdParentId'][value='']");
			var totalAmount=0.0;
			for(var i=0;i<inputObj.length;i++){
				var _index=inputObj[i].name.substring(inputObj[i].name.indexOf("[")+1,inputObj[i].name.indexOf("]"));
				var tarName="fdDetailList_noShow_Form["+_index+"].fdTotal";
				totalAmount=addPoint(totalAmount,$("[name='"+tarName+"']").val());
			}
			$("[name='fdTotalMoney']").val(totalAmount);
			$("[name='fdChildTotalMoney']").val(totalAmount);
		}
		changRule(null,null);
	};
	
	//修改公司
	window.changeCompany=function(){
		var orgType=$("[name='orgType']").val();
		var fdOrgId=$("[name='fdOrgId']").val();
		var fdSchemeId=$("[name='fdSchemeId']").val();
		var fdCompanyId=$("[name='fdCompanyId']").val();
		var url=env.fn.formatUrl('/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgeting&fdSchemeId='+fdSchemeId
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
				//累加下级汇总金额
				var tarName="fdDetailList_noShow_Form["+_index+"]."+fieldName;
				totalAmount=addPoint(totalAmount,$("[name='"+tarName+"']").val());
			}
			//获取预算科目ID为fdParentId的列
			var parentObj=$("input[name$='fdBudgetItemId'][value="+fdParentId+"]");
			if(parentObj.length>0){
				var index_=parentObj[0].name.substring(parentObj[0].name.indexOf("[")+1,parentObj[0].name.indexOf("]"));
				var tarName="fdDetailList_Form_display["+index_+"]."+fieldName;
				$("[name='"+tarName+"']").val(formatFloat(totalAmount,2));
			}
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
	 * 填写完月度金额校验月度金额总和是否大于年度金额总和
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
		reCalculate(val,obj,"noCheck");   //重新计算该月度上级金额，不做月度金额大于汇总金额校验，汇总金额直接由月度相加
		//重新累计全年金额
		$("[name='fdDetailList_Form["+index+"].fdTotal']").val(formatFloat(sumNum,2)); //将当前填写金额格式化未两位小数
		//reCalculate(fdTotal,$("[name='fdDetailList_Form["+index+"].fdTotal']")[0]);   //重新计算月度上级金额
	};
	/************************************************
	 * 编辑状态下重写提交事件
	 ************************************************/
	window.Fssc_SubmitForm=function(status, method, isDraft){
		//新建页面，预算总额=所有末级科目预算总和
		if($("[name*='fdDetailList_Form[!{index}]']").length>0){ //明细基准行来不及隐藏，导致提交报错
			$("[kmss_isreferrow='1']").remove();
		}
		//获取fdIsLastStage=1的列
		var inputObj=$("input[name$='fdIsLastStage'][value='1']");
		var totalAmount=0.0;
		var childTotalAmount=0.0;
		for(var i=0;i<inputObj.length;i++){
			var _index=inputObj[i].name.substring(inputObj[i].name.indexOf("[")+1,inputObj[i].name.indexOf("]"));
			var tarId="fdDetailList_noShow_Form["+_index+"].fdTotal"; //下级汇总额隐藏域
			var tarName="fdDetailList_Form["+_index+"].fdTotal";  //新预算额
			var val=$("[name='"+tarId+"']").val();
			if(val){
				childTotalAmount=addPoint(childTotalAmount,val);
			}
			if(document.getElementsByName(tarName)&&document.getElementsByName(tarName)[0]){
				totalAmount=addPoint(totalAmount,document.getElementsByName(tarName)[0].value);
			}
		}
		$("[name='fdChildTotalMoney']").val(childTotalAmount);
		$("[name='fdTotalMoney']").val(addPoint(childTotalAmount,totalAmount));
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
