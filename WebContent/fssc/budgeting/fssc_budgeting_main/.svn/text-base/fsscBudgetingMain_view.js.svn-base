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
		}
	window.initData=function(){
		var schemeId=$("[name='fdSchemeId']").val();
		if(schemeId){
			getPeriodBySchemeId(schemeId);
		}
		//增加页面宽度
		$(".tempTB").attr("style","width: 100%; min-width: 980px; margin: 0px auto;");
		var selfAmountJson;
		if(formInitData['selfAmountJson']){
			selfAmountJson=JSON.parse(formInitData['selfAmountJson']);//将json字符串转为对象
		}
		//获取预算编制表格所有的科目循环给汇总赋值
		var len=$("#TABLE_DocList_fdDetailList_Form").find("tr").length-1;
		for(var i=0;i<len;i++){
			var id=$("[name='fdDetailList_Form["+i+"].fdBudgetItemId']").val();
			if(selfAmountJson){
				var arr=selfAmountJson[id];
			}
			if(id&&arr){
				for(var n=0,size=fields.length;n<size;n++){//用jquery赋值无效
					var tarId="fdDetailList_Form["+i+"]."+fields[n];
					if(document.getElementById(tarId)){
						document.getElementById(tarId).value=Number(arr[n]).toFixed(2);
					}
					var _tarId="fdDetailList_noShow_Form["+i+"]."+fields[n];
					if(document.getElementById(_tarId)){
						document.getElementById(_tarId).value=Number(arr[n]).toFixed(2);
					}
				}
			}
		}
		//查看页面，预算总额统计，下级预算汇总直接显示
		//获取fdParentId为空的列
		var inputObj=$("input[name$='fdParentId'][value='']");
		var totalAmount=0.0;
		for(var i=0;i<inputObj.length;i++){
			var _index=inputObj[i].name.substring(inputObj[i].name.indexOf("[")+1,inputObj[i].name.indexOf("]"));
			var tarId="fdDetailList_Form["+_index+"].fdTotal";
			if($("[name='"+tarId+"']").val()){
				totalAmount=addPoint(totalAmount,$("[name='"+tarId+"']").val());
			}
		}
		$("[name='fdTotalMoney']").val(totalAmount);
	};
	//编辑按钮js事件，若是草稿状态，则跳转edit方法，若是驳回状态则跳转新版本
	window.editDoc=function(fdId,fdStatus,fdSchemeId){
		var url;
		var fdOrgType=$("[name='fdOrgType']").val();
		if(fdStatus=='1'){ //草稿
			url=env.fn.formatUrl('/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=edit&fdId='+fdId+"&fdSchemeId="+fdSchemeId+"&orgType="+fdOrgType);
		}else if(fdStatus=='3'){
			url=env.fn.formatUrl('/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=newEdition&fdId='+fdId+"&fdSchemeId="+fdSchemeId+"&orgType="+fdOrgType);
		}
		Com_OpenWindow(url,'_self');
	}
});
