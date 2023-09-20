seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str','lui/util/env','lang!fssc-budgeting','lang!'], function($, dialog, dialogCommon,strutil,env,lang,comlang){
	$(document).ready(function(){
		setTimeout(function(){
			FSSC_InitMonthSelector();
		},500);
	});
	//初始化预算编制期间
	window.FSSC_InitMonthSelector = function(){
		var enLang = {                            
	        name  : "en",
	        month : ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
	        weeks : [ "SUN","MON","TUR","WED","THU","FRI","SAT" ],
	        times : ["Hour","Minute","Second"],
	        timetxt: ["Time","Start Time","End Time"],
	        backtxt:"Back",
	        clear : "Clear",
	        today : "Now",
	        yes   : "Confirm",
	        close : "Close"
	    }
	    jeDate("#fdStartPeriod",{
	        format: "YYYY-MM",
	        donefun:function(){
	        	checkPeriod();
	        }
	    });
		jeDate("#fdEndPeriod",{
		    format: "YYYY-MM",
		    donefun:function(){
		    	checkPeriod();
	        }
		});
	};
	
	//校验预算编制开始期间需小于结束期间，且相隔为12个期间
	window.checkPeriod=function(){
		
	};
	/*******************************************
	* 校验预算编制期间
	*******************************************/
	Com_Parameter.event["submit"].push(function(){ // 通过submit队列来添加校验函数，这样校验失败，会终止表单提交。
		var submitFlag=true;
		var fdStartPeriod=$("[name='fdStartPeriod']").val();
		var fdEndPeriod=$("[name='fdEndPeriod']").val();
		if(fdStartPeriod&&fdEndPeriod ){
			var fdStartPeriod2 = fdStartPeriod.substring(fdStartPeriod.length-2, fdStartPeriod.length);
			if(fdStartPeriod2 != "01"){
				dialog.alert(lang["fsscBudgetingPeriod.start.tips"]);
				submitFlag=false;
			}
			fdStartPeriod=fdStartPeriod.replace("-","");
			fdEndPeriod=fdEndPeriod.replace("-","");
			//开始期间不能大于结束期间
			if(fdEndPeriod<fdStartPeriod&&submitFlag){
				dialog.alert(lang["fsscBudgetingPeriod.start.gt.end.message"]);
				submitFlag=false;
			}
			//开始期间与结束期间需包含12个期间
			if(!(fdEndPeriod-fdStartPeriod==11||fdEndPeriod-fdStartPeriod==99)&&submitFlag){
				//201802-201902==11或者201806-201905=99
				dialog.alert(lang["fsscBudgetingPeriod.start.and.end.12month.message"]);
				submitFlag=false;
			}
			//校验有效的期间不存在跨期间的情况
			var data = new KMSSData();
			data.UseCache = false;
			var param="&fdStartPeriod="+fdStartPeriod+"&fdEndPeriod="+fdEndPeriod;
			var method=$("[name='method_GET']").val();
			if(method=="edit"){
				param+="&fdId="+$("[name='fdId']").val();
			}
			var rtn = data.AddBeanData("fsscBudgetingPeriodService&authCurrent=true"+param).GetHashMapArray();
			if(rtn&&rtn.length > 0&&rtn[0]["checkStatus"]&&rtn[0]["checkStatus"]=='false'){
				dialog.alert(lang["fsscBudgetingPeriod.intersection.message"]);
				submitFlag=false;
			}
		}
		return submitFlag;
	});
});
LUI.ready(function(){	
	seajs.use('lui/jquery',function($){
		
	});
});
