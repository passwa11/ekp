seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/util/env','lang!',"lang!eop-basedata"], function($, dialog , topic,env,lang,baseLang) {
	window.validateService=function(){
		var submit=true;
		submit=validateSameModule();
		if(submit){
			submit=validateSameCom_budget();
		}
		if(submit){
			submit=validateSameCom_matchBudget();
		}
		if(submit){
			submit=validateSameCom_matchProvision();
		}
		return submit;
	}

	/* 同一个模块不允许设置重复的开关账时间
	 * 
	*******************************************/
	window.validateSameModule=function(){
		var submit=true;
		var array=new Array();
		var len=$("#TABLE_DocList").find("tr").length-2;
		var idStr="";
		for(var i=0;i<len;i++){
			var current=$("input[name='fdDetail."+i+".fdModule']").val();
			if(current){
				array=current.split(';');
				for(k=0;k<array.length;k++){
					if(idStr.indexOf(array[k])==-1){//无重复
						idStr+=array[k];
					}else{//有重复
						submit=false;
						dialog.alert(messageInfo['fssc.base.switch.openOrClose.tips']);
						break;
					}
				}
			}
		}
		return submit;
	};
	/* 预算导入规则同公司校验
	 * 
	 *******************************************/
	window.validateSameCom_budget=function(){
		var submit=true;
		var array=new Array();
		var len=$("#TABLE_DocList_budgetRule").find("tr").length-2;
		var idStr="";
		for(var i=0;i<len;i++){
			var current=$("input[name='fdBudgetRuleDetail."+i+".fdRuleCompanyId']").val();
			if(current){
				array=current.split(';');
				for(k=0;k<array.length;k++){
					if(idStr.indexOf(array[k])==-1){//无重复
						idStr+=array[k];
					}else{//有重复
						submit=false;
						dialog.alert(baseLang['fssc.base.switch.import.budget.tips']);
						break;
					}
				}
			}
		}
		return submit;
	};
	/* 预算扣减规则同公司校验
	 * 
	 *******************************************/
	window.validateSameCom_matchBudget=function(){
		var submit=true;
		var array=new Array();
		var len=$("#TABLE_DocList_deduBudgetRule").find("tr").length-2;
		var idStr="";
		for(var i=0;i<len;i++){
			var current=$("input[name='fdDeduBudgetRuleDetail."+i+".fdRuleCompanyId']").val();
			if(current){
				array=current.split(';');
				for(k=0;k<array.length;k++){
					if(idStr.indexOf(array[k])==-1){//无重复
						idStr+=array[k];
					}else{//有重复
						submit=false;
						dialog.alert(baseLang['fssc.base.switch.operate.budget.tips']);
						break;
					}
				}
			}
		}
		return submit;
	};
	/*预提扣减规则同公司校验
	 * 
	 *******************************************/
	window.validateSameCom_matchProvision=function(){
		var submit=true;
		var array=new Array();
		var len=$("#TABLE_DocList_deduProvisionRule").find("tr").length-2;
		var idStr="";
		for(var i=0;i<len;i++){
			var current=$("input[name='fdDeduProvisionRuleDetail."+i+".fdProvisionCompanyId']").val();
			if(current){
				array=current.split(';');
				for(k=0;k<array.length;k++){
					if(idStr.indexOf(array[k])==-1){//无重复
						idStr+=array[k];
					}else{//有重复
						submit=false;
						dialog.alert(baseLang['fssc.base.switch.operate.provision.tips']);
						break;
					}
				}
			}
		}
		return submit;
	};
	/* ******************************************
	 * 验证银行对接配置(不能同时选择CMB和CBS、CMInt)
	*******************************************/
	window.validateUseBank=function(){
		var submit=true;
		var fdUseBank=$("[name='fdUseBank']").val();
		if(""!=fdUseBank&&typeof(fdUseBank)!="undefined"&&fdUseBank.indexOf('CMB')!=-1&&fdUseBank.indexOf('CBS')!=-1){
			submit=false;
			dialog.alert(baseLang['fssc.base.switch.useBank.tips']);
		}else if((""!=fdUseBank&&typeof(fdUseBank)!="undefined"&&fdUseBank.indexOf('CMB')!=-1&&fdUseBank.indexOf('CMInt')!=-1) ||
			(""!=fdUseBank&&typeof(fdUseBank)!="undefined"&&fdUseBank.indexOf('CBS')!=-1&&fdUseBank.indexOf('CMInt')!=-1)){
			submit=false;
			dialog.alert(baseLang['fssc.base.switch.useBank.tips']);
		}
		return submit;
	}
	
	/* ******************************************
	 * 选择完模块名给字段赋值
	*******************************************/
	window.afterSelectModule=function(data, index){
		if(!data){
			return ;
		}
		var idValue="",nameValue="";
		for(var k=0;k<data.length;k++){
			idValue+=data[k].fdId+";";
			nameValue+=data[k].fdName+";";
		}
		if(idValue){
			$("[name='fdDetail."+index+".fdModule']").val(idValue.substring(0,idValue.length-1));
		}
		if(nameValue){
			$("[name='fdDetail."+index+".fdModuleName']").val(nameValue.substring(0,nameValue.length-1));
		}
	};
	
	//保存基本信息
	window.updateSwitch=function() {
		var valid = $KMSSValidation(document.forms['eopBasedataSwitchForm']);
		$.ajax({
			url :env.fn.formatUrl('/eop/basedata/eop_basedata_switch/eopBasedataSwitch.do?method=updateSwitch'),
			type : 'POST',
			dataType : 'json',
			async : false,
			data : $("#eopBasedataSwitchForm").serialize(),
			success:function(data) {
				if (data == true) {
					dialog.success(lang['return.optSuccess']);
				} else {
					dialog.failure(lang['return.optFailure']);
				}
			},
			error: function() {
				dialog.failure(lang['return.optFailure']);
			}
		});
	};
	/*****************************************************
	 * 选择预算导入规则，全年或者年、季、月组合，年度必填
	 * **************************************************/
	window.changeValue=function(val,dom){
		var name=$(dom).attr("name");
		var index=name.substring(name.indexOf(".")+1,name.lastIndexOf("."));
		var e = arguments.callee.caller.caller.arguments[0] || window.event; 
		e=e.srcElement||e.target;
		var val=$("input[name='fdBudgetRuleDetail."+index+".fdRulePeriod']").val();
		if(e.checked){
			$("input[name='_fdBudgetRuleDetail."+index+".fdRulePeriod'][value="+e.value+"]").prop("checked",true);
		}else{
			$("input[name='_fdBudgetRuleDetail."+index+".fdRulePeriod'][value="+e.value+"]").prop("checked",false);
		}
		if(val&&e.value==1&&e.checked){//全年
			 $("input[name='_fdBudgetRuleDetail."+index+".fdRulePeriod'][value=2]").prop("checked",false);
			 $("input[name='_fdBudgetRuleDetail."+index+".fdRulePeriod'][value=3]").prop("checked",false);
			 $("input[name='_fdBudgetRuleDetail."+index+".fdRulePeriod'][value=4]").prop("checked",false);
		}else if(val){
			$("input[name='_fdBudgetRuleDetail."+index+".fdRulePeriod'][value=1]").prop("checked",false);  //选择季、月，清除全年
			$("input[name='_fdBudgetRuleDetail."+index+".fdRulePeriod'][value=2]").prop("checked",true);//选择季、月，自动勾选年
		}
		var array =[]; 
		$('input[name="_fdPeriod"]:checked').each(function(){  
			array.push($(this).val());
		}); 
		var fdPeriod="";
		for(var i=0,s=array.length;i<s;i++){
			fdPeriod+=array[i]+";";
		}
		fdPeriod+=$('input[name="fdBudgetRuleDetail.'+index+'.fdRulePeriod"]').val()+";2";
	    if(fdPeriod){
	    	$('input[name="fdBudgetRuleDetail.'+index+'.fdRulePeriod"]').val(fdPeriod);
	    }
	}

	//开关帐设置选择模块
    window.FSSC_SelectModule = function(){
    	var index = DocListFunc_GetParentByTagName('TR').rowIndex-2;
    	dialogSelect(false,'eop_basedata_switch_module','fdDetail.*.fdModule','fdDetail.*.fdModuleName',function(data){afterSelectModule(data, index);});
    }
    //开关帐设置选择公司
    window.FSSC_SelectCompany2 = function(){
    	dialogSelect(true,'eop_basedata_company_fdCompany','fdDetail.*.fdCompanyId','fdDetail.*.fdCompanyName');
    }
    //选择公司
    window.FSSC_SelectCompany = function(idFiled,nameFiled){
    	dialogSelect(true,'eop_basedata_company_fdCompany',idFiled,nameFiled);
    }
    //切换预算编制类型
    window.changeType=function(){
    	$("#discardBudgeting").attr("style","float:right;display:block;");
    }
    //一键废弃目前期间的预算编制
    window.updateBudgetingStatus=function(){
    	var url = env.fn.formatUrl("/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=updateBudgetingStatus");
    	dialog.confirm(baseLang['message.one.key.discard'],function(value){
    		if(value==true){
    			$.ajax({
    				url :url,
    				type : 'POST',
    				dataType : 'json',
    				async : false,
    				success:function(data) {
    					if (data == true) {
    						dialog.success(lang['return.optSuccess']);
    					} else {
    						dialog.failure(lang['return.optFailure']);
    					}
    				},
    				error: function() {
    					dialog.failure(lang['return.optFailure']);
    				}
    			});
    		}
    	});
    }
    //不启用公司组，显示清空按钮
    window.displayClearCompanyGroup=function(obj,val){
		if(!$(obj).prop("checked")){//切换为不启用公司组
			$("#clearCompanyGroup").attr("style","float:right;display:block;");
			$("div[id='_xform_fdCompanyGroup']").find(".com_help").show();
		}else{
			$("#clearCompanyGroup").attr("style","float:right;display:none;");
			$("input[name='fdCompanyGroup']").parent().attr("style","");
			$("div[id='_xform_fdCompanyGroup']").find(".com_help").hide();
		}
    }
	window.fdContractIsStartChange =function(obj){
		if($(obj).prop("checked")){
			$("#fdContractAccountTypeTr").show();
		}else{
			$("#fdContractAccountTypeTr").hide();
		}
	}
    //一键废弃目前期间的预算编制
    window.clearCompanyGroup=function(){
    	var url = env.fn.formatUrl("/eop/basedata/eop_basedata_company/eopBasedataCompany.do?method=clearCompanyGroup");
			$.ajax({
				url :url,
				type : 'POST',
				dataType : 'json',
				async : false,
				success:function(data) {
					if (data == true) {
						dialog.success(lang['return.optSuccess']);
					} else {
						dialog.failure(lang['return.optFailure']);
					}
				},
				error: function() {
					dialog.failure(lang['return.optFailure']);
				}
			});
    }
});

LUI.ready(function(){
	seajs.use('lui/jquery',function($){
		$(".div_title").click(function(){
			if($(this).next().hasClass("hideClass")){
				 $(this).next().show();
				 $(this).next().removeClass("hideClass");
				 $(this).find(".arrow").html(messageInfo['fssc.base.switch.packup']);
			 }else{
				 $(this).next().hide();
				 $(this).next().addClass("hideClass");
				 $(this).find(".arrow").html(messageInfo['button.edit']);
			 }
		 }); 
	});
	var fdContractIsStart=$("input[name='fdContractIsStart']").val();
	if(fdContractIsStart=="false"){
		$("#fdContractAccountTypeTr").hide();
	}
});
