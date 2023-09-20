seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str','lui/util/env','lang!fssc-budgeting','lang!'], function($, dialog, dialogCommon,strutil,env,lang,comlang){
	//上级预算
	var parentFields=["fdParentTotal","fdParentPeriodOne","fdParentPeriodTwo","fdParentPeriodThree","fdParentPeriodFour","fdParentPeriodFive",
	            "fdParentPeriodSix","fdParentPeriodSeven","fdParentPeriodEight","fdParentPeriodNine","fdParentPeriodTen","fdParentPeriodEleven","fdParentPeriodTwelve"];
	//可申请金额
	var canApplyFields=["fdCanApplyTotal","fdCanApplyPeriodOne","fdCanApplyPeriodTwo","fdCanApplyPeriodThree","fdCanApplyPeriodFour","fdCanApplyPeriodFive",
	            "fdCanApplyPeriodSix","fdCanApplyPeriodSeven","fdCanApplyPeriodEight","fdCanApplyPeriodNine","fdCanApplyPeriodTen","fdCanApplyPeriodEleven","fdCanApplyPeriodTwelve"];
	//新预算额
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
		var method=Com_GetUrlParameter(window.location.href,'method');
		var schemeId=Com_GetUrlParameter(location.href,"fdSchemeId");
		if(schemeId){
			getPeriodBySchemeId(schemeId);
		}
		//增加页面宽度
		$(".tempTB").attr("style","width:100%; min-width:980px;margin:0px auto;");
		var parentAmountJson,canApplyAmountJson;
		if(formInitData['parentAmountJson']){//当出现除预算意外其他维度时，不展现上级
			parentAmountJson=JSON.parse(formInitData['parentAmountJson']);//上级预算额
		}
		if(formInitData['canApplyAmountJson']){//当出现除预算意外其他维度时，不展现上级
			canApplyAmountJson=JSON.parse(formInitData['canApplyAmountJson']);//可分配预算额
		}
		//获取预算编制表格所有的上级科目预算额赋值
		var len=$("#TABLE_DocList_fdDetailList_Form").find("tr").length-2;
		for(var i=0;i<len;i++){
			var id=$("[name='fdDetailList_Form["+i+"].fdBudgetItemId']").val();
			if(parentAmountJson){
				var arr=parentAmountJson[id];//上级预算额
			}
			if(canApplyAmountJson){
				var applyArr=canApplyAmountJson[id]; //可分配预算额
			}
			if(id&&arr){
				for(var n=0,size=parentFields.length;n<size;n++){
					//上级预算额赋值
					var tarName="fdDetailList_Form["+i+"]."+parentFields[n];
					$("[name='"+tarName+"']").val(formatFloat(arr[n],2));
					//可分配预算额
					tarName="fdDetailList_Form["+i+"]."+canApplyFields[n];
					$("[name='"+tarName+"']").val(formatFloat(applyArr[n],2));
					//可分配预算额
					tarName="fdDetailList_Form["+i+"]."+fields[n];
					
					//设置新预算额不允许超过可分配预算额,月度不设置必填
					if("budgetingEdit"==method||"newEdition"==method){//暂存后编辑或者新版本,校验范围金额需加回自己
						var oldVal=$("[name='"+tarName+"']").val();
						if(!oldVal){
							oldVal=0.0;
						}
						if(fields[n]=="fdTotal"){
							$("[name='"+tarName+"']").attr("validate","required number range(0,"+addPoint(Number(applyArr[n]),oldVal)+")");
						}else{
							$("[name='"+tarName+"']").attr("validate","number range(0,"+addPoint(Number(applyArr[n]),oldVal)+")");
						}
					}else{
						if(fields[n]=="fdTotal"){
							$("[name='"+tarName+"']").attr("validate","required number range(0,"+formatFloat(Number(applyArr[n]),2)+")");
						}else{
							$("[name='"+tarName+"']").attr("validate","number range(0,"+formatFloat(Number(applyArr[n]),2)+")");
						}
					}
					//数据库隐藏值
					tarName="fdDetailList_noShow_Form["+i+"]."+fields[n];
					$("[name='"+tarName+"']").val(formatFloat(Number(applyArr[n]),2));
				}
			}else{
				for(var n=0,size=parentFields.length;n<size;n++){
					var tarName="fdDetailList_Form["+i+"]."+fields[n];
					//设置新预算额不允许超过可分配预算额,月度不设置必填
					if(fields[n]=="fdTotal"){
						$("[name='"+tarName+"']").attr("validate","required number range(0,0)");
					}else{
						$("[name='"+tarName+"']").attr("validate","number range(0,0)");
					}
				}
			}
		}
		changRule(null,null);
	};
	//修改公司
	window.changeCompany=function(){
		var orgType=$("[name='orgType']").val();
		var fdOrgId=$("[name='fdOrgId']").val();
		var fdSchemeId=$("[name='fdSchemeId']").val();
		var fdCompanyId=$("[name='fdCompanyId']").val();
		var url=env.fn.formatUrl('/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingChild&fdSchemeId='+fdSchemeId
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
	    	}
	    });
	};
	/************************************************
	 * 填写完年度金额自动计算可申请金额
	 ************************************************/
	window.reCalYear=function(val,obj){
		var index=obj.name.substring(obj.name.indexOf("[")+1,obj.name.indexOf("]"));
		var fieldName=obj.name.substring(obj.name.indexOf(".")+1,obj.name.length);
		var sumNum=0.0;  
		//填写月份汇总计算年份
		for(var n=1,size=fields.length;n<size;n++){
			var tarName="fdDetailList_Form["+index+"]."+fields[n];
			sumNum=addPoint(sumNum,$("[name='"+tarName+"']").val());
		}
		var fdTotal=$("[name='fdDetailList_Form["+index+"].fdTotal']").val();
		if(fdTotal&&sumNum&&sumNum-fdTotal>0){
			dialog.alert(lang["budgeting.sumNum.total.tips"]);
			obj.value="";  //清空当前所填写的值
		}
		recountAmount(index,fieldName);
	};
	/************************************************
	 * 填写完年度金额手动平均分摊到12期
	 ************************************************/
	window.shareAmount=function(val,obj){
		if(isNaN(val)){
			return ;
		}
		var index=obj.name.substring(obj.name.indexOf("[")+1,obj.name.indexOf("]"));
		obj.value=formatFloat(val,2); //将当前填写金额格式化未两位小数
		var fieldName=obj.name.substring(obj.name.indexOf(".")+1,obj.name.length);
		//重新计算预算可申请金额
		recountAmount(index,fieldName);
		var shareVal=divPoint(val,12.00);  //年度金额除以12，保留两位小数
		var temp=0.0;
		//月份赋值
		for(var n=1,size=fields.length;n<size;n++){
			var tarName="fdDetailList_Form["+index+"]."+fields[n];
			if("fdPeriodTwelve"==fields[n]){
				$("[name='"+tarName+"']").val(subPoint(val,temp));
			}else{
				$("[name='"+tarName+"']").val(shareVal);
				temp=addPoint(temp,shareVal);
			}
			recountAmount(index,fields[n]);//重新计算预算可申请
		}
	};
	/************************************************
	 * 填写完月度金额自动重新计算月度可申请金额
	 ************************************************/
	window.recountMonthAmount=function(val,obj){
		if(isNaN(val)){
			return ;
		}
		var index=obj.name.substring(obj.name.indexOf("[")+1,obj.name.indexOf("]"));
		var fieldName=obj.name.substring(obj.name.indexOf(".")+1,obj.name.length);
		var sumNum=0.0;  
		//填写月份汇总计算年份
		for(var n=1,size=fields.length;n<size;n++){
			var tarName="fdDetailList_Form["+index+"]."+fields[n];
			sumNum=addPoint(sumNum,$("[name='"+tarName+"']").val());
		}
		//重新计算该月度可申请金额
		recountAmount(index,fieldName);
		//重新累计全年金额
		$("[name='fdDetailList_Form["+index+"].fdTotal']").val(formatFloat(sumNum,2)); //将当前填写金额格式化未两位小数
		recountAmount(index,"fdTotal");   //重新计算年度预算可申请金额
	};
	/************************************************
	 * 填写完新预算金额自动重新计算可申请金额
	 ************************************************/
	window.recountAmount=function(index,fieldName){
		var method=$("[name='method_GET']").val();
		//重新计算剩余可申请
		var tarName="fdDetailList_noShow_Form["+index+"]."+fieldName;  
		var CanApplyTotalDb=$("[name='"+tarName+"']").val();//可申请金额初始额
		if(!CanApplyTotalDb){
			CanApplyTotalDb=0.0;
		}
		var fdValue=$("input[name='fdDetailList_Form["+index+"]."+fieldName+"']").val();//新预算额
		//非新建，则可申请金额需加回本身分配的金额
		if(method!="add"){
			CanApplyTotalDb=addPoint(CanApplyTotalDb,$("input[name='fdDetailList_db_Form["+index+"]."+fieldName+"']").val());
		}
		//重新计算可申请金额
		var sunVal=subPoint(CanApplyTotalDb,fdValue);
		tarName="fdDetailList_Form["+index+"]."+fieldName.replace("fd","fdCanApply");  
		$("[name='"+tarName+"']").val(formatFloat(sunVal,2));//可申请金额赋值显示
		reCalculate(index,fieldName);
	};
	/************************************************
	 * 1、填写完金额重新计算上级科目预算可申请金额
	 * 2、汇总填写的新预算额
	 ************************************************/
	window.reCalculate=function(index,fieldName){
		var method=$("[name='method_GET']").val();
		var fdParentId=$("[name='fdDetailList_Form["+index+"].fdParentId']").val();
		if(fdParentId){
			//获取fdParentId相同的列
			var inputObj=$("input[name$='fdParentId'][value="+fdParentId+"]");
			var totalAmount=0.0;
			var canApplyAmount=0.0;
			for(var i=0;i<inputObj.length;i++){
				var _index=inputObj[i].name.substring(inputObj[i].name.indexOf("[")+1,inputObj[i].name.indexOf("]"));
				//累加新预算额
				var newVal=$("[name='fdDetailList_Form["+_index+"]."+fieldName+"']").val();
				if(!newVal){
					newVal=0.0;
				}
				totalAmount=addPoint(totalAmount,newVal);
				//累加剩余可使用额
				var _tarName="fdDetailList_noShow_Form["+_index+"]."+fieldName;
				var canApplyVal=$("[name='"+_tarName+"']").val();
				if(!canApplyVal){
					canApplyVal=0.0;
				}
				canApplyAmount=addPoint(canApplyAmount,canApplyVal);
				if(!canApplyAmount){
					canApplyAmount=0.0;
				}
				//非新建，则可申请金额需加回本身分配的金额
				if(method!="add"){
					canApplyAmount=addPoint(canApplyAmount,$("input[name='fdDetailList_db_Form["+_index+"]."+fieldName+"']").val());
				}
			}
			//获取预算科目ID为fdParentId的列
			var parentObj=$("input[name$='fdBudgetItemId'][value="+fdParentId+"]");
			if(parentObj.length>0){
				var index_=parentObj[0].name.substring(parentObj[0].name.indexOf("[")+1,parentObj[0].name.indexOf("]"));
				var tarName="fdDetailList_Form["+index_+"]."+fieldName.replace("fd","fdCanApply");
				//总预算可申请金额-总新预算额
				$("[name='"+tarName+"']").val(formatFloat(subPoint(canApplyAmount,totalAmount),2));
				tarName="fdDetailList_Form["+index_+"]."+fieldName;
				//总新预算额
				$("input[name='"+tarName+"']").val(totalAmount);
			}
		}
	};
	/************************************************
	 * 编辑状态下重写提交事件
	 ************************************************/
	window.Fssc_SubmitForm=function(status, method, isDraft){
		//新建页面，预算总额=下级预算汇总=所有顶级预算科目总额
		if($("[name*='fdDetailList_Form[!{index}]']").length>0){ //明细基准行来不及隐藏，导致提交报错
			$("[kmss_isreferrow='1']").remove();
		}
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
