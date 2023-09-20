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
		var schemeId=$("[name='fdSchemeId']").val();
		if(schemeId){
			getPeriodBySchemeId(schemeId);
		}
		//增加页面宽度
		$(".tempTB").attr("style","width: 100%; min-width: 980px; margin: 0px auto;");
	};
	//编辑按钮js事件，若是草稿状态，则跳转edit方法，若是驳回状态则跳转新版本
	window.editDoc=function(fdId,fdStatus,fdSchemeId){
		var url;
		var fdOrgType=$("[name='fdOrgType']").val();
		if(fdStatus=='1'){ //草稿
			url=env.fn.formatUrl('/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=initEdit&fdId='+fdId+"&fdSchemeId="+fdSchemeId+"&orgType="+fdOrgType);
		}else if(fdStatus=='3'){
			url=env.fn.formatUrl('/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=newEdition&fdId='+fdId+"&fdSchemeId="+fdSchemeId+"&orgType="+fdOrgType);
		}
		Com_OpenWindow(url,'_self');
	}
});
