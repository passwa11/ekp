Com_AddEventListener(window,'load',function(){
	seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str','lui/util/env','lang!fssc-budgeting','lang!'], function($, dialog, dialogCommon,strutil,env,lang,comlang){
		$KMSSValidation().addValidator('checkOrgDept()', lang["fssc.budgeting.orgOrDept.message"],function(){return checkOrgDept();});
		window.checkOrgDept=function(){
			var fdOrgListIds = $("input[name='fdOrgListIds']").val();
			var fdCostCenterListIds = $("input[name='fdCostCenterListIds']").val();
			//页面上前后去空格
			fdOrgListIds=$.trim(fdOrgListIds);
			fdCostCenterListIds=$.trim(fdCostCenterListIds);
			if(!fdOrgListIds&&!fdCostCenterListIds){
					return false;
			}else{
				  return true;
			}

		};
	});
});

LUI.ready(function(){	
	seajs.use('lui/jquery',function($){
		
	});
});
