seajs.use(['lui/jquery','lui/dialog','lui/util/env','lang!fssc-budget','lang!'], function($, dialog ,env,lang,commonLang) {
	if(window.navigator.userAgent.toLowerCase().indexOf("msie")>-1||window.navigator.userAgent.toLowerCase().indexOf("trident")>-1){//IE
		setTimeout(function(){initData();},2000);
	}else{//非IE
		LUI.ready(function(){
			initData();
		});
	};
	window.initData=function(){
		var data = new KMSSData();
		data.UseCache = false;
		var rtn = data.AddBeanData("fsscBudgetReportService&authCurrent=true&fdType=getYear").GetHashMapArray();
		var obj=document.getElementsByName('fdYear')[0];
		obj.options.length=0; 
		obj.add(new Option("==请选择==",""));
		if(rtn.length > 0){
			for(var i=0;i<rtn.length;i++){
				obj.add(new Option(rtn[i].text,rtn[i].value));
			}
			// 默认为当前年份
			//var yearValue=new Date().getFullYear();
			//$('select[name="fdYear"] option[value="'+yearValue+'"]').attr("selected", true);	 
		}	
	};
	window.checkCompany=function(){
		var fdCompanyId=$("[name='fdCompanyId']").val();
		if(fdCompanyId){
			return true;
		}else{
			dialog.alert(lang["message.pleaseSelectCompany"]);
			return false;
		}
	};
	//选择成本中心组
	window.selectCostCenterGroup=function(){
		dialogSelect(false,'eop_basedata_cost_center_group_fdCostCenterGroup','fdCostCenterGroupId','fdCostCenterGroupName',null,{'fdCompanyId':$("[name='fdCompanyId']").val()});
	};
	
	//选择成本中心所屬組
	window.selectCostCenterParent=function(){
		dialogSelect(false,'eop_basedata_cost_center_group_fdCostCenterGroup','fdCostCenterParentId','fdCostCenterParentName',null,{'fdCompanyId':$("[name='fdCompanyId']").val()});
	};
	//选择成本中心
	window.selectCostCenter=function(){
		var fdCostCenterGroupId=$("[name='fdCostCenterParentId']").val();
		dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdCostCenterId','fdCostCenterName',null,{'fdCompanyId':$("[name='fdCompanyId']").val(),'fdCostCenterGroupId':fdCostCenterGroupId});
	};
	//选择项目
	window.selectProject=function(){
		dialogSelect(false,'eop_basedata_project_project','fdProjectId','fdProjectName',null,{'fdCompanyId':$("[name='fdCompanyId']").val()});
	};
	//选择WBS
	window.selectWbs=function(){
		dialogSelect(false,'eop_basedata_wbs_fdWbs','fdWbsId','fdWbsName',null,{'fdCompanyId':$("[name='fdCompanyId']").val()});
	};
	//选择内部订单
	window.selectInnerOrder=function(){
		dialogSelect(false,'eop_basedata_inner_order_fdInnerOrder','fdInnerOrderId','fdInnerOrderName',null,{'fdCompanyId':$("[name='fdCompanyId']").val()});
	};
	//选择预算项目
	window.selectBudgetItem=function(){
		dialogSelect(false,'eop_basedata_budget_item_com_fdBudgetItem','fdBudgetItemId','fdBudgetItemName',null,{'fdCompanyId':$("[name='fdCompanyId']").val()});
	};
	
	/*******************************************
	 * 期间类型发生变化
	 * @param : val 期间类型的值（1:月；3:季；5:年）
	 *******************************************/
	window.FS_ChangePeriodType=function(val){
		$("#fdPeriod_td").find(".validation-advice").remove();
		if(val=="5"){
			$('#periodSpan').html("");
		}else{
			$('#periodSpan').html("");
			$('#periodSpan').html(FS_GetPeriodHTML(val));
		}
	}
	/*******************************************
	 * 返回费用预算期间HTML
	 * @param : periodType 期间类型（1:月；3:季；5:年）
	 *******************************************/
	window.FS_GetPeriodHTML=function(periodType){
		var PeriodHTML ='';
		if(periodType == "1"){
			var months=["fdJanMoney","fdFebMoney","fdMarMoney","fdAprMoney","fdMayMoney","fdJunMoney","fdJulMoney","fdAugMoney","fdSeptMoney","fdOctMoney","fdNovMoney","fdDecMoney"];
			var val=["00","01","02","03","04","05","06","07","08","09","10","11"];
			PeriodHTML += '<select name="fdPeriod">';
			PeriodHTML += '<option value="">'+commonLang["page.firstOption"]+'</option>';
			for(var i=0;i<12;i++){
				PeriodHTML += '<option value="'+val[i]+'">'+lang["fsscBudgetDetail."+months[i]]+'</option>';
			}
			PeriodHTML += '</select>';
		}else if(periodType == "3"){
			var quarter=["first","second","third","fourth"];
			var val=["00","01","02","03"];
			PeriodHTML += '<select name="fdPeriod">';
			PeriodHTML += '<option value="">'+commonLang["page.firstOption"]+'</option>';
			for(var i=0;i<4;i++){
				PeriodHTML += '<option value="'+val[i]+'">'+lang["report.quarter."+quarter[i]]+'</option>';
			}
			PeriodHTML += '</select>';
		}
		return PeriodHTML;
	}
	//根据方案展现不同的筛选条件
	window.displaySearch=function(data){
		if(data&&data.length>0){
			clearValue();//清除其他筛选条件的值
			var all=[1,2,3,4,5,6,7,8,9,10,11];
			$("[name='fdDimension']").val(data[0]["fdDimension"]);
			var dis=data[0]["fdDimension"].split(";");
			//显示对应筛选条件
			for(var i=0;i<dis.length;i++){
				$("#fdDimension"+dis[i]).attr("style","");
				delete all[dis[i]-1];  //删除
			}
			//其他隐藏
			for(var i=0;i<all.length;i++){
				if(all[i]){
					$("#fdDimension"+all[i]).attr("style","display:none;");
				}
			}
		}
	}
	//清除字段值，extendField不需要清空的值，多个;连接
	window.clearValue=function(extendField){
		var property=["fdCompanyGroup","fdCompany","fdCostCenterGroup","fdCostCenter","fdProject","fdWbs","fdInnerOrder","fdBudgetItem","fdDept","fdPerson"];
		for(var i=0;i<property.length;i++){
			if((";"+extendField+";").indexOf(";"+property[i]+";")>-1){//说明不需要清空值
				continue;
			}
			$('[name="'+property[i]+'Id"]').val('');
			$('[name="'+property[i]+'Name"]').val('');
			if("fdDept"==property[i]||"fdPerson"==property[i]){
				emptyNewAddress(property[i]+"Name",null,null,false);
			}
		}
		$("[name='_fdBudgetStatus']").prop("checked","");
	}
	window.changeCompany=function(data){
		if(data){
			clearValue('fdCompany');
		}
	}
	window.FSSC_Search=function(method){
		if(!method||method==''){
    		method='executeLedger';
    	}
    	var src = env.fn.formatUrl('/fssc/budget/fssc_budget_data/fsscBudgetData.do?method='+method);
    	//预算方案
    	var fdSchemeId=$("[name='fdSchemeId']").val();
    	if(!fdSchemeId){
    		dialog.alert(lang["message.select.budget.scheme.tips"]);
    		return false;
    	}else{
    		src = Com_SetUrlParameter(src,"fdSchemeId",fdSchemeId);
    	}
		var del_load = dialog.loading();
    	//方案维度
    	var fdDimension = $('[name="fdDimension"]').val();
    	if(fdDimension){
    		src = Com_SetUrlParameter(src,"fdDimension",fdDimension);
    	}
    	//公司组
    	var fdCompanyGroupId = $('[name="fdCompanyGroupId"]').val();
    	if(fdCompanyGroupId){
    		src = Com_SetUrlParameter(src,"fdCompanyGroupId",fdCompanyGroupId);
    	}
    	//公司
    	var fdCompanyId = $('[name="fdCompanyId"]').val();
    	if(fdCompanyId){
    		src = Com_SetUrlParameter(src,"fdCompanyId",fdCompanyId);
    	}
    	//成本中心组
    	var fdCostCenterGroupId = $('[name="fdCostCenterGroupId"]').val();
    	if(fdCostCenterGroupId){
    		src = Com_SetUrlParameter(src,"fdCostCenterGroupId",fdCostCenterGroupId);
    	}
    	//成本中心 所属组
    	var fdCostCenterParentId = $('[name="fdCostCenterParentId"]').val();
    	if(fdCostCenterParentId){
    		src = Com_SetUrlParameter(src,"fdCostCenterParentId",fdCostCenterParentId);
    	}
    	//成本中心
    	var fdCostCenterId = $('[name="fdCostCenterId"]').val();
    	if(fdCostCenterId){
    		src = Com_SetUrlParameter(src,"fdCostCenterId",fdCostCenterId);
    	}
    	//项目
    	var fdProjectId = $('[name="fdProjectId"]').val();
    	if(fdProjectId){
    		src = Com_SetUrlParameter(src,"fdProjectId",fdProjectId);
    	}
    	//WBS
    	var fdWbsId = $('[name="fdWbsId"]').val();
    	if(fdWbsId){
    		src = Com_SetUrlParameter(src,"fdWbsId",fdWbsId);
    	}
    	//内部订单
    	var fdInnerOrderId = $('[name="fdInnerOrderId"]').val();
    	if(fdInnerOrderId){
    		src = Com_SetUrlParameter(src,"fdInnerOrderId",fdInnerOrderId);
    	}
    	//预算科目
    	var fdBudgetItemId = $('[name="fdBudgetItemId"]').val();
    	if(fdBudgetItemId){
    		src = Com_SetUrlParameter(src,"fdBudgetItemId",fdBudgetItemId);
    	}
    	//部门
    	var fdDeptId = $('[name="fdDeptId"]').val();
    	if(fdDeptId){
    		src = Com_SetUrlParameter(src,"fdDeptId",fdDeptId);
    	}
    	//人员
    	var fdPersonId = $('[name="fdPersonId"]').val();
    	if(fdPersonId){
    		src = Com_SetUrlParameter(src,"fdPersonId",fdPersonId);
    	}
    	//年份
    	var fdYear = $("select[name='fdYear']").val();
    	if(fdYear){
    		src = Com_SetUrlParameter(src,"fdYear",fdYear);
    	}
    	//期间类型
    	var fdPeriodType = $("input[name='fdPeriodType']:checked").val();
    	if(fdPeriodType){
    		src = Com_SetUrlParameter(src,"fdPeriodType",fdPeriodType);
    	}
    	//期间
    	var fdPeriod = $("select[name='fdPeriod']").val();
    	if(fdPeriod){
    		src = Com_SetUrlParameter(src,"fdPeriod",fdPeriod);
    	}
    	//预算状态
    	var fdBudgetStatus = $("[name='fdBudgetStatus']").val();
    	if(fdBudgetStatus){
    		src = Com_SetUrlParameter(src,"fdBudgetStatus",fdBudgetStatus);
    	}
    	$('#searchIframe').attr("src",src);
    }
    
    //重置
    window.FSSC_Reset=function(method){
    	if(!method||method==''){
    		method='executeLedger';
    	}
    	var src = env.fn.formatUrl('/fssc/budget/fssc_budget_data/fsscBudgetData.do?method='+method);
    	$('#searchIframe').attr("src",src);
    	$("[name='fdSchemeId']").val('');
    	$("[name='fdSchemeName']").val('');
    	clearValue();
    	if(!method||method==''){
    		document.getElementsByName("fdYear")[0].value= "5"+new Date().getFullYear()+"0000";
        	document.getElementsByName("fdPeriodType")[0].value= "";
    	}
    	
    }
    //导出
    window.exportResult=function(method){
		var del_load = dialog.loading();
		if(!method||method==''){
    		method='exportExecuteLedger';
    	}
    	var src = env.fn.formatUrl('/fssc/budget/fssc_budget_data/fsscBudgetData.do?method='+method);
//    	//预算方案
//    	var fdSchemeId=$("[name='fdSchemeId']").val();
//    	if(!fdSchemeId){
//    		dialog.alert(lang["message.select.budget.scheme.tips"]);
//			$(".lui_mask_l").hide();
//			$(".lui_dialog_main").hide();
//    		return false;
//    	}else{
//    		src = Com_SetUrlParameter(src,"fdSchemeId",fdSchemeId);
//    	}
//    	//方案维度
//    	var fdDimension = $('[name="fdDimension"]').val();
//    	if(fdDimension){
//    		src = Com_SetUrlParameter(src,"fdDimension",fdDimension);
//    	}
//    	//公司组
//    	var fdCompanyGroupId = $('[name="fdCompanyGroupId"]').val();
//    	if(fdCompanyGroupId){
//    		src = Com_SetUrlParameter(src,"fdCompanyGroupId",fdCompanyGroupId);
//    	}
//    	//公司
//    	var fdCompanyId = $('[name="fdCompanyId"]').val();
//    	if(fdCompanyId){
//    		src = Com_SetUrlParameter(src,"fdCompanyId",fdCompanyId);
//    	}
//    	//成本中心组
//    	var fdCostCenterGroupId = $('[name="fdCostCenterGroupId"]').val();
//    	if(fdCostCenterGroupId){
//    		src = Com_SetUrlParameter(src,"fdCostCenterGroupId",fdCostCenterGroupId);
//    	}
//    	//成本中心
//    	var fdCostCenterId = $('[name="fdCostCenterId"]').val();
//    	if(fdCostCenterId){
//    		src = Com_SetUrlParameter(src,"fdCostCenterId",fdCostCenterId);
//    	}
//    	//项目
//    	var fdProjectId = $('[name="fdProjectId"]').val();
//    	if(fdProjectId){
//    		src = Com_SetUrlParameter(src,"fdProjectId",fdProjectId);
//    	}
//    	//WBS
//    	var fdWbsId = $('[name="fdWbsId"]').val();
//    	if(fdWbsId){
//    		src = Com_SetUrlParameter(src,"fdWbsId",fdWbsId);
//    	}
//    	//内部订单
//    	var fdInnerOrderId = $('[name="fdInnerOrderId"]').val();
//    	if(fdInnerOrderId){
//    		src = Com_SetUrlParameter(src,"fdInnerOrderId",fdInnerOrderId);
//    	}
//    	//预算科目
//    	var fdBudgetItemId = $('[name="fdBudgetItemId"]').val();
//    	if(fdBudgetItemId){
//    		src = Com_SetUrlParameter(src,"fdBudgetItemId",fdBudgetItemId);
//    	}
//    	//部门
//    	var fdDeptId = $('[name="fdDeptId"]').val();
//    	if(fdDeptId){
//    		src = Com_SetUrlParameter(src,"fdDeptId",fdDeptId);
//    	}
//    	//人员
//    	var fdPersonId = $('[name="fdPersonId"]').val();
//    	if(fdPersonId){
//    		src = Com_SetUrlParameter(src,"fdPersonId",fdPersonId);
//    	}
//    	//年份
//    	var fdYear = $("select[name='fdYear']").val();
//    	if(fdYear){
//    		src = Com_SetUrlParameter(src,"fdYear",fdYear);
//    	}
//    	//期间类型
//    	var fdPeriodType = $("input[name='fdPeriodType']:checked").val();
//    	if(fdPeriodType){
//    		src = Com_SetUrlParameter(src,"fdPeriodType",fdPeriodType);
//    	}
//    	//期间
//    	var fdPeriod = $("select[name='fdPeriod']").val();
//    	if(fdPeriod){
//    		src = Com_SetUrlParameter(src,"fdPeriod",fdPeriod);
//    	}
//    	//预算状态
//    	var fdBudgetStatus = $("[name='fdBudgetStatus']").val();
//    	if(fdBudgetStatus){
//    		src = Com_SetUrlParameter(src,"fdBudgetStatus",fdBudgetStatus);
//    	}
//		$('#searchIframe').attr("src",src);
    	
       	//预算方案
    	var fdSchemeId=$("[name='fdSchemeId']").val();
    	if(!fdSchemeId){
    		dialog.alert(lang["message.select.budget.scheme.tips"]);
    		return false;
    	}else{
    		src = Com_SetUrlParameter(src,"fdSchemeId",fdSchemeId);
    	}
		var del_load = dialog.loading();
    	//方案维度
    	var fdDimension = $('[name="fdDimension"]').val();
    	if(fdDimension){
    		src = Com_SetUrlParameter(src,"fdDimension",fdDimension);
    	}
    	//公司组
    	var fdCompanyGroupId = $('[name="fdCompanyGroupId"]').val();
    	if(fdCompanyGroupId){
    		src = Com_SetUrlParameter(src,"fdCompanyGroupId",fdCompanyGroupId);
    	}
    	//公司
    	var fdCompanyId = $('[name="fdCompanyId"]').val();
    	if(fdCompanyId){
    		src = Com_SetUrlParameter(src,"fdCompanyId",fdCompanyId);
    	}
    	//成本中心组
    	var fdCostCenterGroupId = $('[name="fdCostCenterGroupId"]').val();
    	if(fdCostCenterGroupId){
    		src = Com_SetUrlParameter(src,"fdCostCenterGroupId",fdCostCenterGroupId);
    	}
    	//成本中心 所属组
    	var fdCostCenterParentId = $('[name="fdCostCenterParentId"]').val();
    	if(fdCostCenterParentId){
    		src = Com_SetUrlParameter(src,"fdCostCenterParentId",fdCostCenterParentId);
    	}
    	//成本中心
    	var fdCostCenterId = $('[name="fdCostCenterId"]').val();
    	if(fdCostCenterId){
    		src = Com_SetUrlParameter(src,"fdCostCenterId",fdCostCenterId);
    	}
    	//项目
    	var fdProjectId = $('[name="fdProjectId"]').val();
    	if(fdProjectId){
    		src = Com_SetUrlParameter(src,"fdProjectId",fdProjectId);
    	}
    	//WBS
    	var fdWbsId = $('[name="fdWbsId"]').val();
    	if(fdWbsId){
    		src = Com_SetUrlParameter(src,"fdWbsId",fdWbsId);
    	}
    	//内部订单
    	var fdInnerOrderId = $('[name="fdInnerOrderId"]').val();
    	if(fdInnerOrderId){
    		src = Com_SetUrlParameter(src,"fdInnerOrderId",fdInnerOrderId);
    	}
    	//预算科目
    	var fdBudgetItemId = $('[name="fdBudgetItemId"]').val();
    	if(fdBudgetItemId){
    		src = Com_SetUrlParameter(src,"fdBudgetItemId",fdBudgetItemId);
    	}
    	//部门
    	var fdDeptId = $('[name="fdDeptId"]').val();
    	if(fdDeptId){
    		src = Com_SetUrlParameter(src,"fdDeptId",fdDeptId);
    	}
    	//人员
    	var fdPersonId = $('[name="fdPersonId"]').val();
    	if(fdPersonId){
    		src = Com_SetUrlParameter(src,"fdPersonId",fdPersonId);
    	}
    	//年份
    	var fdYear = $("select[name='fdYear']").val();
    	if(fdYear){
    		src = Com_SetUrlParameter(src,"fdYear",fdYear);
    	}
    	//期间类型
    	var fdPeriodType = $("input[name='fdPeriodType']:checked").val();
    	if(fdPeriodType){
    		src = Com_SetUrlParameter(src,"fdPeriodType",fdPeriodType);
    	}
    	//期间
    	var fdPeriod = $("select[name='fdPeriod']").val();
    	if(fdPeriod){
    		src = Com_SetUrlParameter(src,"fdPeriod",fdPeriod);
    	}
    	//预算状态
    	var fdBudgetStatus = $("[name='fdBudgetStatus']").val();
    	if(fdBudgetStatus){
    		src = Com_SetUrlParameter(src,"fdBudgetStatus",fdBudgetStatus);
    	}
    	$('#searchIframe').attr("src",src);
    	 var newWindow = window.open(name);  
 	    if (!newWindow)  
 	        return false;  
 	    var html = "";  
 	    html += "<html><head></head><body><form id='formid' method='post' action='" + src + "'>";  
 	    
 	    html += "</form><script type='text/javascript'>document.getElementById('formid').submit();";  
 	    html += "<\/script></body></html>".toString().replace(/^.+?\*|\\(?=\/)|\*.+?$/gi, "");   
 	    newWindow.document.write(html);  
 	   return newWindow; 
		listenEnd();
	}

	function listenEnd() {//定时监听 
		$("[name='endFlag']").val("");
		var loop = setInterval(function() {
			var txtendflag=$("[name='endFlag']").val();
			if (txtendflag=="1") {
				clearInterval(loop);//停止定时任务
			} else {
				getEndFlag();
			}
		}, 1000 );//单位毫秒  注意：如果导出页面很慢时，建议循环时间段稍长一点
	}

	function getEndFlag() {//请求session标记位             
		$.ajax({
			url: env.fn.formatUrl('/fssc/budget/fssc_budget_data/fsscBudgetData.do?method=getEndFlag'),
			type: 'post',
			dataType: 'json',
			error: function(data){
				dialog.result(data.responseJSON);
			},
			success: function(data){
				if(data.endFlag){
					$("[name='endFlag']").val("1");
					$(".lui_mask_l").hide();
					$(".lui_dialog_main").hide();
				}
			}
		});
	}
});
