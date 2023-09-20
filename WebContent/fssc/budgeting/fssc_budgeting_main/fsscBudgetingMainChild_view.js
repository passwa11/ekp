seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str','lui/util/env','lang!fssc-budgeting','lang!'], function($, dialog, dialogCommon,strutil,env,lang,comlang){
	//上级预算
	var parentFields=["fdParentTotal","fdParentPeriodOne","fdParentPeriodTwo","fdParentPeriodThree","fdParentPeriodFour","fdParentPeriodFive",
	            "fdParentPeriodSix","fdParentPeriodSeven","fdParentPeriodEight","fdParentPeriodNine","fdParentPeriodTen","fdParentPeriodEleven","fdParentPeriodTwelve"];
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
		var schemeId=$("[name='fdSchemeId']").val();
		if(schemeId){
			getPeriodBySchemeId(schemeId);
		}

		//增加页面宽度
		$(".tempTB").attr("style","width: 100%; min-width: 980px; margin: 0px auto;");
		var parentAmountJson,canApplyAmountJson;
		if(formInitData['parentAmountJson']){
			parentAmountJson=JSON.parse(formInitData['parentAmountJson']);//上级预算额
		}
		if(formInitData['canApplyAmountJson']){
			canApplyAmountJson=JSON.parse(formInitData['canApplyAmountJson']);//可分配预算额
		}
		//获取预算编制表格所有的上级科目预算额赋值
		var len=$("#TABLE_DocList_fdDetailList_Form").find("tr").length-1;
		for(var i=0;i<len;i++){
			var id=$("[name='fdDetailList_Form["+i+"].fdBudgetItemId']").val();
			var arr,applyArr;
			if(parentAmountJson){
				arr=parentAmountJson[id];//上级预算额	
			}
			if(canApplyAmountJson){
				applyArr=canApplyAmountJson[id]; //可分配预算额
			}
			if(id&&arr){
				for(var n=0,size=parentFields.length;n<size;n++){//用jquery赋值无效
					//上级预算额赋值
					var tarId="fdDetailList_Form["+i+"]."+parentFields[n];
					if(document.getElementById(tarId)){
						document.getElementById(tarId).value=Number(arr[n]).toFixed(2);
					}
					//可分配预算额
					tarId="fdDetailList_Form["+i+"]."+fields[n];
					if(document.getElementById(tarId)){
						document.getElementById(tarId).value=Number(applyArr[n]).toFixed(2);
					}
					//数据库隐藏值
					tarId="fdDetailList_noShow_Form["+i+"]."+fields[n];
					if(document.getElementById(tarId)){
						document.getElementById(tarId).value=Number(applyArr[n]).toFixed(2);
					}
				}
			}
		}
	};
	//编辑按钮js事件，若是草稿状态，则跳转edit方法，若是驳回状态则跳转新版本
	window.editDoc=function(fdId,fdStatus,fdSchemeId,fdCompanyId){
		var url;
		var fdOrgType=$("[name='fdOrgType']").val();
		if(fdStatus=='1'){ //草稿
			url=env.fn.formatUrl('/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=budgetingEdit&fdId='+fdId+"&fdSchemeId="+fdSchemeId+"&orgType="+fdOrgType+"&fdCompanyId="+fdCompanyId);
		}else if(fdStatus=='3'){
			url=env.fn.formatUrl('/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=newEdition&fdId='+fdId+"&fdSchemeId="+fdSchemeId+"&orgType="+fdOrgType+"&fdCompanyId="+fdCompanyId);
		}
		Com_OpenWindow(url,'_self');
	}
});
