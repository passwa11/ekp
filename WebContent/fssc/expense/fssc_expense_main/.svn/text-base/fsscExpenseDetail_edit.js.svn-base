DocList_Info.push('TABLE_DocList_fdDetailList_Form');
DocList_Info.push('TABLE_DocList_fdAccountsList_Form');
var fdExpenseType = $("[name=fdExpenseType]").val();
var fdIsTravelAlone = $("[name=fdIsTravelAlone]").val();
if(fdExpenseType==2&&fdIsTravelAlone=='true'){
	DocList_Info.push('TABLE_DocList_fdTravelList_Form');
}
DocList_Info.push('TABLE_DocList_fdInvoiceList_Form');
DocList_Info.push('TABLE_DocList_Amortize');
DocList_Info.push('TABLE_DocList_fdDidiDetail');
DocList_Info.push('TABLE_DocList_fdTranDataList_Form');
window.travelIndex =$("#TABLE_DocList_fdTravelList_Form tr").length-1;
seajs.use(['lui/dialog','lang!fssc-expense','lang!','lui/util/env'],function(dialog,lang,comlang,env){
	Com_AddEventListener(window,'load',function(){
		var len = $("#TABLE_DocList_fdDetailList_Form tr").length-1;
		for(var i=0;i<len;i++){
			FSSC_ShowDetailBudgetInfo(i);
			FSSC_ChangeIsDeductInit(i);  //修复移动端转报销PC端编辑页面进项税提示必填
		}
		if($("[name=method_GET]").val()=='add'){
			FSSC_AddExpenseDetail();
			FSSC_ReloadCostCenter();
			if(fdExpenseType==2&&fdIsTravelAlone=='true'){
				DocList_AddRow("TABLE_DocList_fdTravelList_Form");
	    		var index = $("#TABLE_DocList_fdTravelList_Form tr").length-2;
	    		window.travelIndex = index;
	    		$("[name='fdTravelList_Form["+index+"].fdSubject']").val(lang['fsscExpenseTravelDetail.fdSubject']+(window.travelIndex));
				$("[id='fdTravelList_Form["+index+"].fdSubject']").html(lang['fsscExpenseTravelDetail.fdSubject']+(window.travelIndex));
				window.travelIndex = window.travelIndex+1;
				FSSC_SetDefaultValue();
			}
			var fdIsTransfer = $("input[name='fdIsTransfer']").val();
			var len = $("#TABLE_DocList_fdAccountsList_Form tr").length-1;
			for(var i=0;i<len;i++){
				$("[name='fdAccountsList_Form["+i+"].fdIsTransfer']").val(fdIsTransfer);
				initFS_GetFdIsTransfer(i,fdIsTransfer);//初始化收款账户信息是否必填
			}
		}else if($("[name=method_GET]").val()=='edit'){
			var len = $("#TABLE_DocList_fdAccountsList_Form tr").length-1;
			for(var i=0;i<len;i++){
				var fdPayWayId = $("[name='fdAccountsList_Form["+i+"].fdPayWayId']").val();
				if(null != fdPayWayId && '' != fdPayWayId && 'undefined' != fdPayWayId){
					var data = new KMSSData();
					data.UseCache=false;
					data.AddBeanData("eopBasedataPayWayService&type=isTransfer&ids="+fdPayWayId);
					data = data.GetHashMapArray();
					if(data.length>0){
						$("[name='fdAccountsList_Form["+i+"].fdIsTransfer']").val(data[0].fdIsTransfer);
						initFS_GetFdIsTransfer(i,data[0].fdIsTransfer);//初始化收款账户信息是否必填
					}
				}else{
					initFS_GetFdIsTransfer(i,true);//初始化收款账户信息是否必填
				}
			}
			
			var detailLen = $("#TABLE_DocList_fdDetailList_Form tr").length-1;
			for(var i=0;i<detailLen;i++){
				var fdInputTaxRate=$("[name='fdDetailList_Form["+i+"].fdInputTaxRate']").val();
				$("[name='fdDetailList_Form["+i+"].fdInputTaxRate_select']").val(fdInputTaxRate*1);
				
			}
		}
	})

	//-----------------------------------以下为行程明细相关JS代码--------------------------------------
	
	//校验是否选了公司
	window.FSSC_CheckCompanySelected = function(){
		var fdCompanyId = $("[name='fdCompanyId']").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return false;
		}
		return true;
	}
	//初始化行程明细
	window.FSSC_SetDefaultValue = function(copy){
		var index = $("#TABLE_DocList_fdTravelList_Form > tbody > tr").length-2;
		if(!copy){
			$("[name='fdTravelList_Form["+index+"].fdCompanyId']").val($("[name='fdCompanyId']").val());
			$("[name='fdTravelList_Form["+index+"].fdCompanyName']").val($("[name='fdCompanyName']").val());
			$("[name='fdTravelList_Form["+index+"].fdCostCenterId']").val($("[name='fdCostCenterId']").val());
			$("[name='fdTravelList_Form["+index+"].fdCostCenterName']").val($("[name='fdCostCenterName']").val());
		}
		$("[name='fdTravelList_Form["+index+"].fdSubject']").val(lang['fsscExpenseTravelDetail.fdSubject']+(window.travelIndex));
		$("[id='fdTravelList_Form["+index+"].fdSubject']").html(lang['fsscExpenseTravelDetail.fdSubject']+window.travelIndex);
		window.travelIndex = window.travelIndex+1;
		FSSC_RefreshTravelOption();
	}
	//选择出发地/到达地
	window.FSSC_SelectPlace = function(id,name){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		id = id.replace(/\*/g,index);
		name = name.replace(/\*/g,index);
		if(!FSSC_CheckCompanySelected()){
			return false;
		}
		var fdCompanyId = $("[name='fdCompanyId']").val();
		dialogSelect(false,'eop_basedata_city_selectCity',id,name,null,{fdCompanyId:fdCompanyId},function(data){
			window.formValiduteObj.validateElement($("[name='"+name+"']")[0]);
			FSSC_MathStandard(index);	//标准匹配
		});
	}
	//选择交通工具
	window.FSSC_SelectBerth = function(id,name){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var id = id.replace(/\*/g,index);
		var name = name.replace(/\*/g,index);
		if(!FSSC_CheckCompanySelected()){
			return false;
		}
		var fdCompanyId = $("[name='fdCompanyId']").val();
		dialog.iframe(
			'/fssc/expense/fssc_expense_detail/fsscExpenseDetail_selectBerth.jsp?fdCompanyId='+fdCompanyId,
			lang['fsscExpenseDetail.fdBerth'],
			function(data){
				if(!data)return;
				$("[name='"+id+"']").val(data.id);
				$("[name='"+name+"']").val(data.name);
				window.formValiduteObj.validateElement($("[name='"+name+"']")[0]);
				FSSC_MathStandard(index);	//标准匹配
			},{width:800,height:600}
		);
	}
	//选择舱位
//	window.FSSC_SelectBerth = function(){
//		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
//		var fdVehicleId = $("[name='fdTravelList_Form["+index+"].fdVehicleId']").val();
//		if(!fdVehicleId){
//			dialog.alert(lang['tips.pleaseSelectVehicle']);
//			return false;
//		}
//		dialogSelect(false,'eop_basedata_berth_fdBerth','fdTravelList_Form[*].fdBerthId','fdTravelList_Form[*].fdBerthName',null,{fdVehicleId:fdVehicleId});
//	}
	
	//-----------------------------------以下为费用明细相关JS代码--------------------------------------
	
	//选择项目
	window.FSSC_SelectProjectDetail = function(){
		var fdCompanyId = $("[name=fdCompanyId]").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return;
		}
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		dialogSelect(false,'eop_basedata_project_project','fdDetailList_Form[*].fdProjectId','fdDetailList_Form[*].fdProjectName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'fdProjectType':1},function(rtn){
			if(rtn){
				$("[name='fdDetailList_Form["+index+"].fdWbsId']").val("");
				$("[name='fdDetailList_Form["+index+"].fdWbsName']").val("");
			}
			FSSC_MatchBudget(null,$("[name='fdDetailList_Form["+index+"].fdApplyMoney']").get(0));
			FSSC_MatchProvsion(index);
		});
	}
	//切换报销人员，重新进行标准匹配
	window.changeRealUser = function(){
		var index = $("#TABLE_DocList_fdDetailList_Form > tbody > tr").length-2;
		FSSC_MathStandard(index);  //标准匹配
	}

	//切换承担部门，重新进行标准匹配
	window.changeDept = function(){
		var index = $("#TABLE_DocList_fdDetailList_Form > tbody > tr").length-2;
		FSSC_MathStandard(index);  //标准匹配
	}

	//切换招待人数，重新进行标准匹配
	window.changePersonNumber = function(){
		var index = $("#TABLE_DocList_fdDetailList_Form > tbody > tr").length-2;
		FSSC_MathStandard(index);  //标准匹配
	}

	//切换开始日期(或结束日期)，重新进行标准匹配
	window.changeDate = function(){
		var index = $("#TABLE_DocList_fdDetailList_Form > tbody > tr").length-2;
		FSSC_MathStandard(index);  //标准匹配
	}

	//增加一行费用明细，初始化相关数据
	window.FSSC_AddExpenseDetail = function(){
		DocList_AddRow('TABLE_DocList_fdDetailList_Form');
		var index = $("#TABLE_DocList_fdDetailList_Form > tbody > tr").length-2;
		$("[name='fdDetailList_Form["+index+"].fdCompanyId']").val($("[name=fdCompanyId]").val());
		$("[name='fdDetailList_Form["+index+"].fdCompanyName']").val($("[name=fdCompanyName]").val());
		FSSC_RefreshTravelOption(index);
		//复制上一行的成本中心、费用类型、币种、wbs、订单、人员、项目等信息
		if(index>0){
			var props = ['fdCostCenter','fdExpenseItem','fdCurrency','fdWbs',"fdInnerOrder"];
			for(var i=0;i<props.length;i++){
				$("[name='fdDetailList_Form["+index+"]."+props[i]+"Id']").val($("[name='fdDetailList_Form["+(index-1)+"]."+props[i]+"Id']").val());
				$("[name='fdDetailList_Form["+index+"]."+props[i]+"Name']").val($("[name='fdDetailList_Form["+(index-1)+"]."+props[i]+"Name']").val());
			}
			var data = new KMSSData();
			data = data.AddBeanData("eopBasedataInputTaxService&authCurrent=true&fdExpenseItemId="+$("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val());
			data = data.GetHashMapArray();
			if(data.length>0&&data){
				$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val(data[0].fdTaxRate)
			}
			$("[name='fdDetailList_Form["+index+"].fdTravel']").val($("[name='fdDetailList_Form["+(index-1)+"].fdTravel']").val())
			$("[name='fdDetailList_Form["+index+"].fdHappenDate']").val($("[name='fdDetailList_Form["+(index-1)+"].fdHappenDate']").val())
			$("[name='fdDetailList_Form["+index+"].fdExchangeRate']").val($("[name='fdDetailList_Form["+(index-1)+"].fdExchangeRate']").val())
			$("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val($("[name='fdDetailList_Form["+(index-1)+"].fdBudgetRate']").val())
			$("[name='fdDetailList_Form["+index+"].fdProjectId']").val($("[name='fdDetailList_Form["+(index-1)+"].fdProjectId']").val())
			$("[name='fdDetailList_Form["+index+"].fdProjectName']").val($("[name='fdDetailList_Form["+(index-1)+"].fdProjectName']").val())
			// 实际使用人
			var fdRealUserId = $("[name='fdDetailList_Form["+(index-1)+"].fdRealUserId']").val();
			var fdRealUserName = $("[name='fdDetailList_Form["+(index-1)+"].fdRealUserName']").val();
			emptyNewAddress("fdDetailList_Form["+index+"].fdRealUserName",null,null,false);
			$('[name="fdDetailList_Form['+index+'].fdRealUserId"]').val(fdRealUserId);
			$('[name="fdDetailList_Form['+index+'].fdRealUserName"]').val(fdRealUserName);
			var addressInput = $("[xform-name='mf_fdDetailList_Form["+index+"].fdRealUserName']")[0];
		    var addressValues = new Array();
		    addressValues.push({id:fdRealUserId,name:fdRealUserName});
			newAddressAdd(addressInput,addressValues);
			//部门
			var fdDept = $("[name='fdDetailList_Form["+(index-1)+"].fdDeptId']");
			if(fdDept.length>0){
				var fdDeptId = $("[name='fdDetailList_Form["+(index-1)+"].fdDeptId']").val();
				var fdDeptName = $("[name='fdDetailList_Form["+(index-1)+"].fdDeptName']").val();
				emptyNewAddress("fdDetailList_Form["+index+"].fdDeptName",null,null,false);
				$('[name="fdDetailList_Form['+index+'].fdDeptId"]').val(fdDeptId);
				$('[name="fdDetailList_Form['+index+'].fdDeptName"]').val(fdDeptName);
				addressInput = $("[xform-name='mf_fdDetailList_Form["+index+"].fdDeptName']")[0];
			    addressValues = new Array();
			    addressValues.push({id:fdDeptId,name:fdDeptName});
				newAddressAdd(addressInput,addressValues);
			}
		}else{
			$("[name='fdDetailList_Form["+index+"].fdCostCenterId']").val($("[name='fdCostCenterId']").val());
			$("[name='fdDetailList_Form["+index+"].fdCostCenterName']").val($("[name='fdCostCenterName']").val());
			$("[name='fdDetailList_Form["+index+"].fdProjectId']").val($("[name='fdProjectId']").val());
			$("[name='fdDetailList_Form["+index+"].fdProjectName']").val($("[name='fdProjectName']").val());
			// 实际使用人
			var fdRealUserId = $("[name='fdClaimantId']").val();
			var fdRealUserName = $("[name='fdClaimantName']").val();
			emptyNewAddress("fdDetailList_Form["+index+"].fdRealUserName",null,null,false);
			$('[name="fdDetailList_Form['+index+'].fdRealUserId"]').val(fdRealUserId);
			$('[name="fdDetailList_Form['+index+'].fdRealUserName"]').val(fdRealUserName);
			var addressInput = $("[xform-name='mf_fdDetailList_Form["+index+"].fdRealUserName']")[0];
		    var addressValues = new Array();
		    addressValues.push({id:fdRealUserId,name:fdRealUserName});
			newAddressAdd(addressInput,addressValues);
			FSSC_InitCurrencyAndRate(index);
		}
	}
	
	
	//费用明细选择税率
  	window.FSSC_SelectInputTaxRate=function(){
  		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
  		var fdCompanyId =$("[name='fdDetailList_Form["+index+"].fdCompanyId']").val();
  		var fdExpenseItemId =$("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val();
  		if(!fdCompanyId){
  			dialog.alert(lang['tips.pleaseSelectCompany']);
  			return;
  		}
  		if(!fdExpenseItemId){
  			dialog.alert(lang['tips.pleaseSelectExpenseCategory']);
  			return;
  		}
  		$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").attr("subject",lang['fsscExpenseDetail.fdInputTaxRate']);
  		dialogSelect(false,'eop_basedata_input_tax_getInputTax','fdDetailList_Form['+index+'].fdInputTaxRateId','fdDetailList_Form['+index+'].fdInputTaxRate',null,{fdCompanyId:fdCompanyId,fdExpenseItemId:fdExpenseItemId},function(rtn){
  			if(!rtn){
  				return;
  			}
  			var rate = rtn[0].fdTaxRate.replace("%","");
  			$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val(rate);
  			//计算税额、不含税额
  			FSSC_selectTaxMoney(index);
  			
  		});
  		
  	}	
  	
  	window.FSSC_selectTaxMoney = function(index){ 	
  		var fdInputTaxRateSelect=$("select[name='fdDetailList_Form["+index+"].fdInputTaxRate_select']").val();
  		$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val(fdInputTaxRateSelect);
  		var fdInputTaxRate = $("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val();
  		//计算税额、不含税额
  		var fdNonDeductMoney=$("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val()*1;
  		if(!fdNonDeductMoney){
  			fdNonDeductMoney=0;
  		}
  		var fdInputTaxMoney = $("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val()*1;
  		var fdNoTaxMoney = $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val()*1;
  		var fdApplyMoney = $("[name='fdDetailList_Form["+index+"].fdApplyMoney']").val()*1;
  		//不含税额=发票金额/(1+税率)
  		fdNoTaxMoney = numDiv(numSub(fdApplyMoney,fdNonDeductMoney),numAdd(1,numDiv(fdInputTaxRate,100)));
  		fdNoTaxMoney = parseFloat(fdNoTaxMoney).toFixed(2);
  		$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(numSub(numSub(fdApplyMoney*1,fdNonDeductMoney*1),fdNoTaxMoney*1));
  		$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdNoTaxMoney);
  		//重新计算fdBudgetMoney
  		var fdBudgetMoney="";
  		var fdBudgetRate=$("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val();
  		var fdDeduFlag=$("[name='fdDeduFlag']").val();
		if(fdBudgetRate){
			if("2"==fdDeduFlag&&fdNoTaxMoney){  //不含税金额
				fdBudgetMoney = multiPoint(fdNoTaxMoney,fdBudgetRate);
			}else if(fdApplyMoney){
				fdBudgetMoney = multiPoint(fdApplyMoney,fdBudgetRate);
			}
		}
		$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(fdBudgetMoney);
  	}
	window.changeCostCenter = function(id,name){
		$.ajax({
			url:Com_Parameter.ContextPath + 'eop/basedata/eop_basedata_cost_center/eopBasedataCostCenter.do?method=getEkpOrgById',
			data:{fdId:id},
			async:false,
			success:function(rtn){
				if(rtn){
					rtn = JSON.parse(rtn);
					console.log(rtn)
					$("[name='fdExpenseDeptId']").val(rtn.fdOrgId);
					$("[name='fdExpenseDeptName']").val(rtn.fdOrgName);
				}
			}
		});
		var len = $("#TABLE_DocList_fdDetailList_Form tr").length-1;
		for(var i=0;i<len;i++){
			$("[name='fdDetailList_Form["+i+"].fdCostCenterId']").val(id);
			$("[name='fdDetailList_Form["+i+"].fdCostCenterName']").val(name);
		}
	}
	//费用明细选择成本中心
	window.FSSC_SelectCostCenter = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
//		if(index>0){
//			dialog.alert("只有第一行才能选择");
//			return ;
//		}
		var fdCompanyId = $("[name='fdDetailList_Form["+index+"].fdCompanyId']").val();
		var fdCategoryId = $("[name=docTemplateId]").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return;
		}
		dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetailList_Form[*].fdCostCenterId','fdDetailList_Form[*].fdCostCenterName',null,{fdCompanyId:fdCompanyId,fdNotId:'fdNotId'},function(rtn){
			var id=$("[name='fdDetailList_Form["+0+"].fdCostCenterId']").val();
			var name=$("[name='fdDetailList_Form["+0+"].fdCostCenterName']").val();
//			changeCostCenter(id,name);
			FSSC_MatchBudget(null,$("[name='fdDetailList_Form["+index+"].fdApplyMoney']").get(0));
			FSSC_MatchProvsion(index);
		});
	}
	//费用明细选择费用类型
	window.FSSC_SelectExpenseItem = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var fdCompanyId = $("[name='fdDetailList_Form["+index+"].fdCompanyId']").val();
		var fdCategoryId = $("[name=docTemplateId]").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return;
		}
		dialogSelect(false,'eop_basedata_expense_item_selectExpenseItem','fdDetailList_Form[*].fdExpenseItemId','fdDetailList_Form[*].fdExpenseItemName',null,{fdCompanyId:fdCompanyId,fdNotId:'fdNotId',fdCategoryId:fdCategoryId},function(data){
			if(!data){
				return;
			}
			if(data.length>0){
				var fdDayCalType=data[0].fdDayCalType;
				if(fdDayCalType){
					$("[name='fdDetailList_Form["+index+"].fdDayCalType']").val(fdDayCalType);
				}else{
					$("[name='fdDetailList_Form["+index+"].fdDayCalType']").val("");
				}
			}
			//FSSC_ChangeIsDeduct("init",null,index); 2022.10.09取消费用类型喝抵扣关联
			FSSC_MatchBudget(null,$("[name='fdDetailList_Form["+index+"].fdApplyMoney']").get(0));
			FSSC_MatchProvsion(index);
			FSSC_MathStandard(index);  //标准匹配
			FSSC_ChangeExpenseTravelDays(index);
		});
	}
	window.FSSC_ChangeExpenseTravelDays = function(index){
		var fdBeginDate = $("[name='fdDetailList_Form["+index+"].fdStartDate']").val()||'';
		var fdEndDate = $("[name='fdDetailList_Form["+index+"].fdHappenDate']").val()||'';
		if(fdBeginDate&&fdEndDate){
			var begin = new Date(fdBeginDate.replace(/\-/g,'/'));
			var end = new Date(fdEndDate.replace(/\-/g,'/'));
			var days = end.getTime()-begin.getTime();
			days = days/1000/3600/24+1;
			//按天数计算规则，2为正常天数-1
			var fdDayCalType=$("[name='fdDetailList_Form["+index+"].fdDayCalType']").val();
			if(fdDayCalType=='2'&&days>0){
				days=days-1;
			}
			$("[name='fdDetailList_Form["+index+"].fdTravelDays']").val(days);
		}
	}
	//费用明细选择WBS(非项目费用)
	window.FSSC_SelectWbs = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var fdCompanyId = $("[name='fdDetailList_Form["+index+"].fdCompanyId']").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return;
		}
		dialogSelect(false,'eop_basedata_expense_wbs_selectWbs','fdDetailList_Form[*].fdWbsId','fdDetailList_Form[*].fdWbsName',null,{fdCompanyId:fdCompanyId,fdNotId:'fdNotId'},function(rtn){
			if(rtn){
				FSSC_MatchProvsion(index);
			}
		});
	}
	//费用明细选择WBS(项目费用)
	window.FSSC_SelectWbsIsProject = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var fdProjectId = $("[name='fdDetailList_Form["+index+"].fdProjectId']").val();
		var fdCompanyId = $("[name='fdDetailList_Form["+index+"].fdCompanyId']").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return;
		}
		dialogSelect(false,'eop_basedata_expense_wbs_selectWbs','fdDetailList_Form[*].fdWbsId','fdDetailList_Form[*].fdWbsName',null,{fdCompanyId:fdCompanyId,fdProjectId:fdProjectId,fdNotId:'fdNotId'},function(rtn){
			if(rtn){
				FSSC_MatchProvsion(index);
			}
		});
	}
	//费用明细选择内部订单
	window.FSSC_SelectInnerOrder = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var fdCompanyId = $("[name='fdDetailList_Form["+index+"].fdCompanyId']").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return;
		}
		// XXX 选择完成后内部订单名称未自动回写到页面，原因未知，暂时用回调事件处理
		dialogSelect(false,'eop_basedata_expense_order_selectInnerOrder','fdDetailList_Form[*].fdInnerOrderId','fdDetailList_Form[*].fdInnerOrderName',null,{fdCompanyId:fdCompanyId,fdNotId:'fdNotId'},function(rtn){
			if(rtn&&rtn.length>0){
				$("[name='fdDetailList_Form["+index+"].fdInnerOrderName']").val(rtn[0].fdName);
				FSSC_MatchProvsion(index);
			}
		});
	}
	//费用明细选择币种
	window.FSSC_SelectCurrency = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var existCurrency = [];
		$("#TABLE_DocList_fdDetailList_Form [name$=fdCurrencyId]").each(function(){
			if(!existCurrency.contains(this.value)){
				existCurrency.push(this.value);
			}
		});
		var fdCompanyId = $("[name='fdDetailList_Form["+index+"].fdCompanyId']").val();
		dialogSelect(false,'eop_basedata_currency_fdCurrency','fdDetailList_Form[*].fdCurrencyId','fdDetailList_Form[*].fdCurrencyName',null,{existCurrency:existCurrency.join(';'),fdCompanyId:fdCompanyId},function(rtn){
			if(!rtn||rtn.length==0){
				$("[name='fdDetailList_Form["+index+"].fdExchangeRate']").val("");
				return;
			}
			var fdCurrencyId = rtn[0].fdId,
    			data = new KMSSData();
			$("[name='fdDetailList_Form["+index+"].fdExchangeRate']").val("");
    		data = data.AddBeanData('eopBasedataExchangeRateService&authCurrent=true&type=getRateByCurrency&fdCurrencyId='+fdCurrencyId+'&fdCompanyId='+fdCompanyId).GetHashMapArray();
			if(data.length>0){
				$("[name='fdDetailList_Form["+index+"].fdExchangeRate']").val(data[0].fdExchangeRate);
				$("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val(data[0].fdBudgetRate);
				FSSC_ChangeExchangeRate(data[0].fdExchangeRate,null,index);
			}
		});
	}
	//初始化币种及汇率
	window.FSSC_InitCurrencyAndRate = function(index){
		if(index==null){
			index = $("#TABLE_DocList_fdDetailList_Form tr").length-3;
		}
		var fdCompanyId = $("[name='fdDetailList_Form["+index+"].fdCompanyId']").val();
		if(!fdCompanyId){
			return;
		}
		var data = new KMSSData();
		data.AddBeanData("eopBasedataCompanyService&type=getStandardCurrencyInfo&authCurrency=true&fdCompanyId="+fdCompanyId);
		data = data.GetHashMapArray();
		if(data.length>0){
			$("[name='fdDetailList_Form["+index+"].fdCurrencyId']").val(data[0].fdCurrencyId);
			$("[name='fdDetailList_Form["+index+"].fdCurrencyName']").val(data[0].fdCurrencyName);
			$("[name='fdDetailList_Form["+index+"].fdExchangeRate']").val(data[0].fdExchangeRate);
			$("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val(data[0].fdBudgetRate);
			FSSC_ChangeMoney(null,null,index);
		}
	}
	//匹配预提 ，并展示
	window.FSSC_MatchProvsion = function(index){
		if(!LUI('ytContent')){
			return;
		}
		var fdDetailId = $("[name='fdDetailList_Form["+index+"].fdId']").val();
		var param = {
			fdCompanyId:$("[name='fdCompanyId']").val(),
			fdExpenseItemId:$("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val(),
			fdCostCenterId:$("[name='fdDetailList_Form["+index+"].fdCostCenterId']").val(),
			fdProjectId:$("[name='fdDetailList_Form["+index+"].fdProjectId']").val(),
			fdInnerOrderId:$("[name='fdDetailList_Form["+index+"].fdInnerOrderId']").val()||'',
			fdWbsId:$("[name='fdDetailList_Form["+index+"].fdWbsId']").val()||'',
			fdProappId:$("[name=fdProappId]").val()||'',
			fdDetailId:fdDetailId||index,
			index:index
		}
		if(!param.fdExpenseItemId){
			return;
		}
		$.post(
			Com_Parameter.ContextPath+'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=matchProvision',
			{params:JSON.stringify([param])},
			function(rtn){
				rtn = JSON.parse(rtn);
				if(rtn.result!='success'){
					return;
				}
				var fdDetailId = $("[name='fdDetailList_Form["+index+"].fdId']").val();
				if(!fdDetailId){//新建fdDetailId就是index，编辑，审批fdDetailId是具体明细ID
					fdDetailId=index;
				}
				if(rtn[fdDetailId]){
					var data = rtn[fdDetailId].data;
					var money = 0,apply = $("[name='fdDetailList_Form["+index+"].fdStandardMoney']").val();
					if(apply<0){
						return ;
					}
					for(var i=0;i<data.length;i++){
						money = numAdd(money,data[i].fdMoney);
					}
					if(money>apply){
						$("[name='fdDetailList_Form["+index+"].fdProvisionMoney']").val(apply);
					}else{
						$("[name='fdDetailList_Form["+index+"].fdProvisionMoney']").val(money);
					}
					$("[name='fdDetailList_Form["+index+"].fdProvisionInfo']").val(JSON.stringify(data));
				}else{
					$("[name='fdDetailList_Form["+index+"].fdProvisionMoney']").val(0);
					$("[name='fdDetailList_Form["+index+"].fdProvisionInfo']").val("[]");
				}
				FSSC_ReloadProvisionInfo();
			}
		);
	}
	//加载预提信息进行展示
	window.FSSC_ReloadProvisionInfo = function(){
		if(!LUI('ytContent')){
			return;
		}
		$("#ytTable tr:gt(0)").remove();
		var content = LUI("ytContent").element.closest(".lui_tabpage_float_content");
		var contentIndex = 0;
		for(var i=0;i<LUI("ytContent").parent.children.length;i++){
			if(LUI("ytContent").parent.children[i].id=='ytContent'){
				contentIndex = i-2;
			}
		}
		var toggle =$(".lui_tabpage_float_nav_item").eq(contentIndex);
		var provisionInfo = {},fdProvisionLedger = {};
		$("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").each(function(){
			var fdProvisionInfo = $(this).find("[name$=fdProvisionInfo]").val()||'[]';
			fdProvisionInfo = JSON.parse(fdProvisionInfo);
			for(var i=0;i<fdProvisionInfo.length;i++){
				if(fdProvisionLedger[fdProvisionInfo[i].fdLedgerId]){
					continue;
				}
				var key = [],title = '';
				if(fdProvisionInfo[i].fdProappName){
					key.push(fdProvisionInfo[i].fdProappName);
				}
				if(fdProvisionInfo[i].fdExpenseItemName){
					key.push(fdProvisionInfo[i].fdExpenseItemName);
				}
				if(fdProvisionInfo[i].fdCostCenterName){
					key.push(fdProvisionInfo[i].fdCostCenterName);
				}
				if(fdProvisionInfo[i].fdProjectName){
					key.push(fdProvisionInfo[i].fdProjectName);
				}
				if(fdProvisionInfo[i].fdInnerOrderName){
					key.push(fdProvisionInfo[i].fdInnerOrderName);
				}
				if(fdProvisionInfo[i].fdWbsName){
					key.push(fdProvisionInfo[i].fdWbsName);
				}
				title = key.join('/');
				if(provisionInfo[title]!=null){
					provisionInfo[title] = numAdd(provisionInfo[title],fdProvisionInfo[i].fdMoney);
				}else{
					provisionInfo[title] = fdProvisionInfo[i].fdMoney
				}
				fdProvisionLedger[fdProvisionInfo[i].fdLedgerId] = true;
			}
		})
		//如果有预提信息，则拼接到明细表，并显示content
		if(JSON.stringify(provisionInfo)!='{}'){
			var index = 1;
			for(var i in provisionInfo){
				$("<tr><td align='center'>"+(index++)+"</td><td>"+i+"</td><td>"+provisionInfo[i]+"</td></tr>").appendTo($("#ytTable"));
				content.css({position:'relative',top:0});
				toggle.addClass("selected")
			}
		}else{//没有预提信息，隐藏content
			//content.css({position:'absolute',top:'-9999em'});
			toggle.removeClass("selected")
		}
	}
	//修改报销金额
	window.FSSC_ChangeMoney = function(v,e,index){
		if(index==null){
			index = DocListFunc_GetParentByTagName("TR");
    		index = index.rowIndex - 1;
    		FSSC_MatchProvsion(index);
		}
		var fdApplyMoney = $("[name='fdDetailList_Form["+index+"].fdApplyMoney']").val();
		var fdExchangeRate = $("[name='fdDetailList_Form["+index+"].fdExchangeRate']").val();
		var fdBudgetRate = $("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val();
		if(!fdApplyMoney||isNaN(fdApplyMoney)){
			return;
		}
		var fdStandardMoney = "",fdBudgetMoney = "",tips="";
		if(fdExchangeRate){
			fdStandardMoney = multiPoint(fdApplyMoney,fdExchangeRate);
		}else{
			tips=lang['tips.exchange.rate'];  //报销币种和本位币汇率未配置
		}
		$("[name='fdDetailList_Form["+index+"].fdStandardMoney']").val(fdStandardMoney);
		//同步设置核准金额
		$("[name='fdDetailList_Form["+index+"].fdApprovedApplyMoney']").val(fdApplyMoney);
		$("[name='fdDetailList_Form["+index+"].fdApprovedStandardMoney']").val(fdStandardMoney);
		if(fdBudgetRate){
			fdBudgetMoney = multiPoint(fdApplyMoney,fdBudgetRate);
		}else{
			tips+='，'+lang['tips.budget.rate'];  //报销币种和预算币汇率未配置
		}
		if(tips){
			var tip=lang['tips.exchangeRate.not.exist'].replace('{0}',tips);
			dialog.alert(tip);
		}
		$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(fdBudgetMoney);
		//计算总额
		FSSC_GetTotalMoney();
		FSSC_MathStandard(index);
		FSSC_ChangeIsDeduct(null,e,index);
	}
	//计算总额
	window.FSSC_GetTotalMoney = function(){
		var totalStandardMoney = 0;
		$("[name$=fdStandardMoney]").each(function(){
			totalStandardMoney = addPoint(totalStandardMoney,this.value);
		});
		if(isNaN(totalStandardMoney)){
			return;
		}
		$("[name=fdTotalStandaryMoney]").val(totalStandardMoney);
		FSSC_XFormDispatch(totalStandardMoney,$("[name=fdTotalStandaryMoney]")[0]);
		$("[name=fdTotalApprovedMoney]").val(totalStandardMoney);
		FSSC_XFormDispatch(totalStandardMoney,$("[name=fdTotalApprovedMoney]")[0]);
		//设置摊销总金额，重置摊销明细
		$("[name=fdAmortizeMoney]").val(totalStandardMoney);
		FSSC_XFormDispatch(totalStandardMoney,$("[name=fdAmortizeMoney]")[0]);
		FSSC_ChangeAmortizeDate();
		var len = $("#TABLE_DocList_fdAccountsList_Form>tbody>tr:gt(0)").length;
		if(len==1){//如果只有一行收款账户，自动计算总金额
			var tr = $("#TABLE_DocList_fdAccountsList_Form>tbody>tr:eq(1)");
			$("[name$=fdOffsetMoney]").each(function(){
				if(!isNaN(this.value)){
					totalStandardMoney = numSub(totalStandardMoney,this.value);
				}
			})
			var fdRate = tr.find("[name$=fdExchangeRate]").val()*1;
			if(!isNaN(fdRate)&&fdRate!=0){
				tr.find("[name$=fdMoney]").val(divPoint(totalStandardMoney,fdRate));
			}
		}
	}

	//值改变后发布事件
	window.FSSC_XFormDispatch = function(val,dom){
		if (typeof __xformDispatch != "undefined") {
			__xformDispatch(val, dom);	
		}
	}
	//根据行程明细动态更新费用明细中的关联选项
	window.FSSC_RefreshTravelOption = function(index){
		var options = $("[name$=fdSubject]"),str = '',optList = [],fdExpenseType = $("[name=fdExpenseType]").val();
		//非差旅类别不做操作
		if(fdExpenseType!=2){
			return;
		}
		for(var k=0;k<options.length;k++){
			str+=options[k].value+";";
			optList.push(options[k].value);
		}
		var opts = $("[name$=fdTravel]");
		if(index!=null){
			opts = opts.eq(index);
		}
		var list = '',newOpt = [];
		opts.find("option").each(function(i){
			if(str.indexOf(this.value)==-1){
				$(this).remove();
			}else{
				list+=this.value+";";
			}
		});
		$(optList).each(function(){
			if(list.indexOf(this)==-1){
				newOpt.push($("<option value='"+this+"'>"+this+"</option>"));
			}
		})
		opts.each(function(){
			for(var i=0;i<newOpt.length;i++){
				$(this).append(newOpt[i]);
			}
		});
	}
	//修改汇率时同步修改金额、收款明细中的汇率、金额
	window.FSSC_ChangeExchangeRate = function(v,e,i){
		var index = DocListFunc_GetParentByTagName("TR");
		index = i==null?index.rowIndex - 1:i;
		var fdCompanyId = $("[name=fdCompanyId]").val();
		var data = new KMSSData();
		data.AddBeanData("eopBasedataCompanyService&authCurrent=true&type=isCurrencySame&fdCompanyId="+fdCompanyId);
		data = data.GetHashMapArray();
		data = data&&data.length>0?data[0].result:'false';
		//同步汇率
		var fdCurrencyId = $("[name='fdDetailList_Form["+index+"].fdCurrencyId']").val();
		$("#TABLE_DocList_fdDetailList_Form [name$='.fdCurrencyId'][value="+fdCurrencyId+"]").each(function(){
			var TR = DocListFunc_GetParentByTagName("TR",this);
			$(TR).find("[name$=fdExchangeRate]").val(v);
			//如果公司的本位币和预算币是一样的，同步更新预算汇率
			if(data=='true'){
				$(TR).find("[name$='fdBudgetRate']").val(v);
			}
			var rate = $(TR).find("[name$='fdBudgetRate']").val()
			var mon = $(TR).find("[name$='fdApplyMoney']").val()
			if(!rate||!mon){
				return;
			}
			$(TR).find("[name$='fdBudgetMoney']").val(multiPoint(rate,mon));
		});
		//同步收款明细中的汇率
		$("#TABLE_DocList_fdAccountsList_Form [name$='.fdCurrencyId'][value="+fdCurrencyId+"]").each(function(){
			var TR = DocListFunc_GetParentByTagName("TR",this);
			$(TR).find("[name$=fdExchangeRate]").val(v);
		});
		//重新计算报销金额 
		$("[name$=fdApplyMoney]").each(function(i){
			FSSC_ChangeMoney(v,e,i);
		});
	}
	
	//显示预算状态信息
	window.FSSC_ShowDetailBudgetInfo = function(index){
		var fdBudgetShowType = $("[name=fdBudgetShowType]").val();
		var fdBudgetStatus = $("[name='fdDetailList_Form["+index+"].fdBudgetStatus']").val()||0;
		var fdFeeStatus = $("[name='fdDetailList_Form["+index+"].fdFeeStatus']").val()||0;
		var showBudget = null,showInfo = null;
		var info = $("[name='fdDetailList_Form["+index+"].fdBudgetInfo']").val()||'[]';
		var fee = $("[name='fdDetailList_Form["+index+"].fdFeeInfo']").val()||'[]';
		info = JSON.parse(info.replace(/\'/g,'"'));
		fee = JSON.parse(fee.replace(/\'/g,'"'));
		var fdProappId = $("[name=fdProappId]").val();
		if(fdProappId){//如果有立项
			var fdProappStatus = $("[name='fdDetailList_Form["+index+"].fdProappStatus']").val();
			//显示红灯及超申请提示
			$("#proapp_status_"+index).attr("class","budget_container budget_status_"+fdProappStatus);
			$("#proapp_status_"+index).attr("title",lang['py.proapp.'+fdProappStatus]);
			if(fdBudgetShowType==1){
				$("#buget_status_"+index).attr("class","budget_container budget_status_0");
				$("#buget_status_"+index).attr("title",lang['py.budget.0']);
			}else{
				$("#buget_status_"+index).html(lang['py.money.total']+"0<br>"+lang['py.money.using']+"0<br>"+lang['py.money.used']+"0<br>"+lang['py.money.usable']+"<span class='budget_money_"+i+"'>"+"0</span>");
				$(".budget_money_"+index).css("color",fdBudgetStatus=='2'?"red":"#333");
			}
			return;
		}
		//显示事前信息
		$("#fee_status_"+index).attr("class","budget_container budget_status_"+fdFeeStatus);
		$("#fee_status_"+index).attr("title",lang['py.fee.'+fdFeeStatus]);
		//显示预算信息
		for(var i=0;i<info.length;i++){
			//获取可用金额最少的预算用于展示
			if(!showBudget||showBudget.fdCanUseAmount>info[i].fdCanUseAmount){
				showBudget = info[i];
			}
		}
		if(!showBudget){
			showBudget = {fdTotalAmount:0,fdOccupyAmount:0,fdAlreadyUsedAmount:0,fdCanUseAmount:0,canUseAmountDisplay:0}
		}
		if(fdBudgetShowType=='1'){//显示图标
			$("#buget_status_"+index).attr("class","budget_container");
			$("#buget_status_"+index).addClass("budget_status_"+fdBudgetStatus);
			$("#buget_status_"+index).attr("title",lang['py.budget.'+fdBudgetStatus]);
			layui.use('layer', function(){
   	   			$("[id*='buget_status_']").mouseover(function(){
	   	 	  		var info = $(this).parent().parent().find("[name$=fdBudgetInfo]").val();
		   	 	  	if(!info){
	   	 	  			return;
	   	 	  		}
	   	 	  		info = JSON.parse(info.replace(/\'/ig,'\"'));
	   	 	  		if(info.length==0){
	   	 	  			return;
	   	 	  		}
	   	 	  		var text = '';
	   	 	  		for(var i=0;i<info.length;i++){
	   	 	  			text+=info[i].fdSubject+"<br>";
	   	 	  		}
	   	 	  		var id = layui.layer.tips(text, this, {
	   	 	  			tips: [1, '#6AA237'],
	   	 	  			time:0,
	   	 	  			//offset:'50px',
	   	 	  			anim: 1
	   	 	  		});
	   	 	  		$(this).attr("title","")
	   	 	  	}).mouseout(function(){
	   	 	  		layui.layer.closeAll("tips");
	   	 	  	})
   			})
		}else{//显示金额
			$("#buget_status_"+index).html(lang['py.money.total']+showBudget.fdTotalAmount+"<br>"+lang['py.money.using']+showBudget.fdOccupyAmount+"<br>"+lang['py.money.used']+showBudget.fdAlreadyUsedAmount+"<br>"+lang['py.money.usable']+"<span class='budget_money_"+i+"'>"+(showBudget.canUseAmountDisplay?showBudget.canUseAmountDisplay:showBudget.fdCanUseAmount)+"</span>");
			$(".budget_money_"+index).css("color",fdBudgetStatus=='2'?"red":"#333");
		}
	}
	
	window.FSSC_MathStandard = function(i){
		if(!$("[name=checkVersion]").val()){
			return;
		}
		var params = [],fdExpenseType = $("[name=fdExpenseType]").val(),fdIsTravelAlone = $("[name=fdIsTravelAlone]").val();
		params.push({
			'fdCompanyId':$("[name='fdDetailList_Form["+i+"].fdCompanyId']").val(),
			'fdExpenseItemId':$("[name='fdDetailList_Form["+i+"].fdExpenseItemId']").val(),
			'fdPersonId':$("[name='fdDetailList_Form["+i+"].fdRealUserId']").val(),
			'fdDeptId':$("[name='fdDetailList_Form["+i+"].fdDeptId']").val(),
			'fdMoney':$("[name='fdDetailList_Form["+i+"].fdApplyMoney']").val(),
			'fdPersonNumber':$("[name='fdDetailList_Form["+i+"].fdPersonNumber']").val(),
			'fdCurrencyId':$("[name='fdDetailList_Form["+i+"].fdCurrencyId']").val(),
			'fdTravel':$("[name='fdDetailList_Form["+i+"].fdTravel']").val(),
			'fdDetailId':$("[name='fdDetailList_Form["+i+"].fdId']").val(),
			'fdHappenDate':$("[name='fdDetailList_Form["+i+"].fdHappenDate']").val(),
			'fdModelId':$("[name=fdId]").val(),
			fdDeptId:$("[name='fdDetailList_Form["+i+"].fdDeptId']").val(),
			'model':'expense',
			'index':i
		})
		//如果是差旅费类型，参数中需要加上地域、舱位等
		//行程独立，从行程明细中获得数据
		if(fdExpenseType=='2'&&fdIsTravelAlone=='true'){
			var travel = {};
			$("#TABLE_DocList_fdTravelList_Form tr:gt(0)").each(function(){
				travel[$(this).find("[name$=fdSubject]").val()] = {
					'fdTravelDays':$(this).find("[name$=fdTravelDays]").val(),
					'fdVehicleId':$(this).find("[name$=fdVehicleId]").val(),
					'fdBerthId':$(this).find("[name$=fdBerthId]").val(),
					'fdBeginDate':$(this).find("[name$=fdBeginDate]").val(),
					'fdEndDate':$(this).find("[name$=fdEndDate]").val(),
					'fdAreaId':$(this).find("[name$=fdArrivalId]").val()
				}
			});
			for(var i=0;i<params.length;i++){
				if(travel[params[i].fdTravel]){
					for(var k in travel[params[i].fdTravel]){
						params[i][k] = travel[params[i].fdTravel][k];
					}
				}
			}
		}else{//行程合并，从费用明细中获取数据
			for(var i=0;i<params.length;i++){
				params[i].fdTravelDays = $("[name='fdDetailList_Form["+params[i].index+"].fdTravelDays']").val();
				params[i].fdBerthId = $("[name='fdDetailList_Form["+params[i].index+"].fdBerthId']").val();
				params[i].fdAreaId = $("[name='fdDetailList_Form["+params[i].index+"].fdArrivalPlaceId']").val();
				params[i].fdBeginDate = $("[name='fdDetailList_Form["+params[i].index+"].fdStartDate']").val()||'';
				params[i].fdEndDate = $("[name='fdDetailList_Form["+params[i].index+"].fdHappenDate']").val();
			}
		}
		$.ajax({
			url:Com_Parameter.ContextPath + 'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=getStandardData',
			data:{params:JSON.stringify(params)},
			async:false,
			success:function(rtn){
				rtn = JSON.parse(rtn);
				console.log(rtn)
				if(rtn.result=='failure'){
					dialog.alert(rtn.message);
					return;
				}
				for(var i=0;i<rtn.data.length;i++){
					if(rtn.data[i].dayCount){//如果是按日标准，需要校验当前单中是否重复报销
						var fdExpenseItemId = params[0].fdExpenseItemId;
						var fdSchemeId = rtn.data[i].fdSchemeId;
						var fdBeginDate = params[0].fdBeginDate;
						var fdEndDate = params[0].fdEndDate;
						var fdPersonId = params[0].fdPersonId;
						$("#TABLE_DocList_fdDetailList_Form tr:gt(0)").each(function(index){
							var standard = $(this).find("[name$=fdStandardInfo]").val()||'[]';
							standard = JSON.parse(standard.replace(/\'/g,'"'));
							if(rtn.data[i].status=='2'||index>=rtn.data[i].index||standard.length==0){
								return;
							}
							var expense = $(this).find("[name$=fdExpenseItemId]").val();
							var personId = $(this).find("[name$=fdRealUserId]").val();
							var begin = getTravelDate('begin',index);
							var end = getTravelDate('end',index);
							var containsScheme = false;
							for(var k=0;k<standard.length;k++){
								if(standard[k].fdSchemeId==fdSchemeId){
									containsScheme=true;
									break;
								}
							}
							if(isInter(fdBeginDate,fdEndDate,begin,end)&&expense==fdExpenseItemId&&containsScheme&&fdPersonId==personId){
								rtn.data[i].status='2';
							}
						})
					}
					if(rtn.data[i].monthCount){//如果是月次标准，需要校验当前单中是否重复报销
						var fdExpenseItemId = params[0].fdExpenseItemId;
						var fdSchemeId = rtn.data[i].fdSchemeId;
						var fdPersonId = params[0].fdPersonId;
						var month = params[0].fdEndDate.split('\-')[1];
						$("#TABLE_DocList_fdDetailList_Form tr:gt(0)").each(function(index){
							var standard = $(this).find("[name$=fdStandardInfo]").val()||'[]';
							standard = JSON.parse(standard.replace(/\'/g,'"'));
							if(rtn.data[i].status=='2'||index>=rtn.data[i].index||standard.length==0){
								return;
							}
							var mon = getTravelDate('end',index);
							if(!mon){
								return;
							}
							mon = mon.split('\-')[1];
							var expense = $(this).find("[name$=fdExpenseItemId]").val();
							var personId = $(this).find("[name$=fdRealUserId]").val();
							var containsScheme = false;
							for(var k=0;k<standard.length;k++){
								if(standard[k].fdSchemeId==fdSchemeId){
									containsScheme=true;
									break;
								}
							}
							if(month==mon&&containsScheme&&fdExpenseItemId==expense&&fdPersonId==personId){
								rtn.data[i].status='2';
							}
						})
					}
					
					$("[name='fdDetailList_Form["+rtn.data[i].index+"].fdStandardStatus']").val(rtn.data[i].status);
					if(rtn.data[i].info){
						$("[name='fdDetailList_Form["+rtn.data[i].index+"].fdStandardInfo']").val((rtn.data[i].info).replace(/\"/g,"'"));
					}
					var div = $("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").eq(rtn.data[i].index).find("[id^=standard_status]");
					div.attr("class","budget_container");
					div.addClass("standard_status_"+rtn.data[i].status);
					//div.attr("title",lang['py.standard.'+rtn.data[i].status]);
					layui.use('layer', function(){
		   	   			$("[id*='standard_status']").mouseover(function(){
			   	 	  		var info = $(this).parent().parent().find("[name$=fdStandardInfo]").val();
				   	 	  	if(!info){
			   	 	  			return;
			   	 	  		}
			   	 	  		info = JSON.parse(info.replace(/\'/ig,'\"'));
			   	 	  		if(info.length==0){
			   	 	  			return;
			   	 	  		}
			   	 	  		var text = '';
			   	 	  		for(var i=0;i<info.length;i++){
			   	 	  			text+=info[i].subject+"<br>";
			   	 	  		}
			   	 	  		var id = layui.layer.tips(text, this, {
			   	 	  			tips: [1, '#6AA237'],
			   	 	  			time:0,
			   	 	  			//offset:'50px',
			   	 	  			anim: 1
			   	 	  		});
			   	 	  		$(this).attr("title","")
			   	 	  	}).mouseout(function(){
			   	 	  		layui.layer.closeAll("tips");
			   	 	  	})
			   		})
				}
			}
		});
	}
	window.getTravelDate = function(type,index){
		var fdExpenseType = $("[name=fdExpenseType]").val(),fdIsTravelAlone = $("[name=fdIsTravelAlone]").val();
		//行程独立，从行程明细中获得数据
		if(fdExpenseType=='2'&&fdIsTravelAlone=='true'){
			var travel = $("[name='fdDetailList_Form["+index+"].fdTravel']").val();
			var date = '';
			$("#TABLE_DocList_fdTravelList_Form tr:gt(0)").each(function(){
				if($(this).find("[name$=fdSubject]").val()==travel){
					date=type=='begin'?$(this).find("[name$=fdBeginDate]").val():$(this).find("[name$=fdEndDate]").val();
					return;
				}
			});
			return date;
		}else{//行程合并，从费用明细中获取数据
			return type=='begin'?($("[name='fdDetailList_Form["+index+"].fdStartDate']").val()||""):$("[name='fdDetailList_Form["+index+"].fdHappenDate']").val();
		}
	}
	//判断两段日期是否有交集
	window.isInter = function(begin1,end1,begin2,end2){
		if(begin1==null||begin2==null||end1==null||end2==null){
			return false;
		}
		if(begin1<=begin2&&end1>=begin2){
			return true;
		}
		if(begin1<=end2&&end1>=end2){
			return true;
		}
		if(begin1<=begin2&&end1>=end2){
			return true;
		}
		if(begin1>=begin2&&end1<=end2){
			return true;
		}
		return false;
	}
	//改变是否抵扣时自动计算进项税额
	window.FSSC_ChangeIsDeduct = function(v,e,i){
		var index = i;
		if(index==null){
			if(e){
				e = e.length?e[0]:e;
				index = DocListFunc_GetParentByTagName("TR",e);
			}else{
				index = DocListFunc_GetParentByTagName("TR");
			}
			index = i==null?index.rowIndex - 1:i;
		}
		var fdApplyMoney = $("[name='fdDetailList_Form["+index+"].fdApplyMoney']").val(); //申请金额
		if("init"==v){
			var data = new KMSSData();
			data = data.AddBeanData("eopBasedataInputTaxService&authCurrent=true&fdExpenseItemId="+$("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val());
			data = data.GetHashMapArray();
			if(data&&data.length>0){
				var fdNonDeductMoney = $("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val(); //不可抵扣金额
				var fdInputTaxRate = $("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val(); //不可抵扣金额
				if(!fdNonDeductMoney){
					fdNonDeductMoney=0;
				}
				var fdTaxMoney=numSub(fdApplyMoney,fdNonDeductMoney);
				if(data[0].fdIsInputTax=='true'){
					$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxMoney']").show();
					$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxRate']").show();
					var fdTaxRate = divPoint(data[0].fdTaxRate,100);//(票面金额÷(1+税额)*税额)
					fdTaxMoney = multiPoint(divPoint(fdTaxMoney,numAdd(fdTaxRate,1.00)),fdTaxRate);
					$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(fdTaxMoney);  	//进项税额
					$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val(data[0].fdTaxRate);		//进项税率
					$("[name='_fdDetailList_Form["+index+"].fdIsDeduct']").prop('checked',true);
					$("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val(true);
					var fdInputTaxMoney=$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val();
					if(!fdInputTaxMoney||fdInputTaxMoney==0){
						$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdApplyMoney);//不含税金额
					}else{
					$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(numSub(numSub(fdApplyMoney,fdNonDeductMoney),fdTaxMoney));//不含税金额
					}
				}else{
					var fdNonDeductMoney = $("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val(); //不可抵扣金额
					if(!fdNonDeductMoney){
						fdNonDeductMoney=0;
					}
					$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxMoney']").hide();
					$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxRate']").hide();
					$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(0);
					$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val(data[0].fdTaxRate);		//进项税率
					$("[name='_fdDetailList_Form["+index+"].fdIsDeduct']").prop('checked',false);
					$("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val(false);
					$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdApplyMoney);//不含税金额
				}
			}
		}else{
			var val = $("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val();
			if(val=='true'){
				$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxMoney']").show();
				$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxRate']").show();
				$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").attr("validate","required  currency-dollar");
				var data = new KMSSData();
				data = data.AddBeanData("eopBasedataInputTaxService&authCurrent=true&fdExpenseItemId="+$("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val());
				data = data.GetHashMapArray();
				if(data&&data.length>0){
					var fdApplyMoney = $("[name='fdDetailList_Form["+index+"].fdApplyMoney']").val();
					var fdNonDeductMoney = $("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val(); //不可抵扣金额
					var fdInputTaxRate= $("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val(); //不可抵扣金额
					if(!fdNonDeductMoney){
						fdNonDeductMoney=0;
					}
					var fdTaxMoney=numSub(fdApplyMoney,fdNonDeductMoney);
					fdInputTaxRate = divPoint(fdInputTaxRate,100);//(票面金额÷(1+税额)*税额)
					fdTaxMoney = multiPoint(divPoint(fdTaxMoney,numAdd(fdInputTaxRate,1.00)),fdInputTaxRate);
					$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(fdTaxMoney);
					var fdInputTaxMoney=$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val();
					if(!fdInputTaxMoney||fdInputTaxMoney==0){
						$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdApplyMoney);//不含税金额
					}else{
					$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(numSub(numSub(fdApplyMoney,fdNonDeductMoney),fdTaxMoney));//不含税金额
					}
				}
			}else{
				var fdNonDeductMoney = $("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val(); //不可抵扣金额
				if(!fdNonDeductMoney){
					fdNonDeductMoney=0;
				}
				$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").attr("validate"," currency-dollar");
				$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxMoney']").hide();
				$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxRate']").hide();
				$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(0);
				$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdApplyMoney);//不含税金额
			}
		}
		var fdBudgetMoney = "";
		var fdDeduFlag=$("[name='fdDeduFlag']").val();
		var fdBudgetRate = $("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val();
		var fdNoTaxMoney = $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val();
		if(fdBudgetRate){
			if("2"==fdDeduFlag&&fdNoTaxMoney){  //不含税金额
				fdBudgetMoney = multiPoint(fdNoTaxMoney,fdBudgetRate);
			}else{
				fdBudgetMoney = multiPoint(fdApplyMoney,fdBudgetRate);
			}
			$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(fdBudgetMoney);
			FSSC_MatchBudget(null,$("[name='fdDetailList_Form["+index+"].fdApplyMoney']").get(0));  //预算匹配
		}
	}
	//行程与费用合并时，计算费用明细中的天数
	window.FSSC_GetTravelDays = function(v,e){
		var index = DocListFunc_GetParentByTagName('TR',e).rowIndex-1;
		var fdStartDate = $("[name='fdDetailList_Form["+index+"].fdStartDate']").val()||'';
		var fdHappenDate = $("[name='fdDetailList_Form["+index+"].fdHappenDate']").val()||'';
		if(!fdHappenDate||!fdStartDate){
			return;
		}
		fdStartDate = new Date(fdStartDate.replace(/\-/g,'/'));
		fdHappenDate = new Date(fdHappenDate.replace(/\-/g,'/'));
	}
	//选择未报费用
	window.FSSC_SelectNote = function(){
		// 当报销类型选择通用报销时，则将行程独立设置未否
		if ("1" == fdExpenseType ) {
			fdIsTravelAlone = false;
		}
		var fdCategoryId=$("[name='docTemplateId']").val();
		var fdCompanyId=$("[name='fdCompanyId']").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return;
		}
		$("[name='fdNoteNames']").attr("subject",lang['fsscExpenseDetail.selectFeeNote.title']);
		dialogSelect(true,'fssc_expense_detail_selectNote','fdNoteIds','fdNoteNames',null,{'fdCategoryId':fdCategoryId,'fdCompanyId':fdCompanyId},function(rtn){
			if(!rtn||rtn.length==0){
				return;
			}
			var dia = dialog.loading();
			for(var i=0;i<rtn.length;i++){
				var detail = $("#TABLE_DocList_fdDetailList_Form>tbody>tr [name$=fdId][value="+rtn[i].fdId+"]");
				if(detail.length>0){//费用已存在，跳过
					continue;
				}
				FSSC_AddExpenseDetail();
				var index = $("#TABLE_DocList_fdDetailList_Form>tbody>tr").length-2;
				var tr = $("#TABLE_DocList_fdDetailList_Form>tbody>tr").eq(index+1);
				tr.find("[name$='["+index+"].fdExpenseItemId']").val(rtn[i].fdExpenseItemId);
				tr.find("[name$='["+index+"].fdExpenseItemName']").val(rtn[i].fdExpenseItemName);
				tr.find("[name$='["+index+"].fdCostCenterId']").val(rtn[i].fdCostCenterId);
				tr.find("[name$='["+index+"].fdCostCenterName']").val(rtn[i].fdCostCenterName);
				tr.find("[name$='["+index+"].fdHappenDate']").val(rtn[i].fdHappenDate);
				tr.find("[name$='["+index+"].fdEndDate']").val(rtn[i].fdHappenDate);
				tr.find("[name$='["+index+"].fdApplyMoney']").val(rtn[i].fdMoney);
				tr.find("[name$='["+index+"].fdStandardMoney']").val(rtn[i].fdMoney);
				tr.find("[name$='["+index+"].fdApprovedApplyMoney']").val(rtn[i].fdMoney);
				tr.find("[name$='["+index+"].fdApprovedStandardMoney']").val(rtn[i].fdMoney);
				tr.find("[name$='["+index+"].fdUse']").val(rtn[i].fdSubject);
				tr.find("[name$='["+index+"].fdNoteId']").val(rtn[i].fdId);
				var fdBudgetRate = tr.find("[name$='["+index+"].fdBudgetRate']").val()||1;
				tr.find("[name$='["+index+"].fdBudgetMoney']").val(multiPoint(rtn[i].fdMoney,fdBudgetRate));
				tr.find("[name$='["+index+"].fdNoteId']").val(rtn[i].fdId);
				//行程独立，自动生成行程，并设置关联关系
				if(fdIsTravelAlone=='true'){
					DocList_AddRow('TABLE_DocList_fdTravelList_Form');
					FSSC_SetDefaultValue();
					var tindex = $("#TABLE_DocList_fdTravelList_Form > tbody > tr").length-2;
					var ttr = $("#TABLE_DocList_fdTravelList_Form > tbody > tr").eq(tindex+1);
					ttr.find("[name$='["+tindex+"].fdBeginDate']").val(rtn[i].fdHappenDate);
					ttr.find("[name$='["+tindex+"].fdEndDate']").val(rtn[i].fdHappenDate);
					ttr.find("[name$='["+tindex+"].fdStartPlace']").val(rtn[i].fdEndPlace);
					ttr.find("[name$='["+tindex+"].fdArrivalPlace']").val(rtn[i].fdEndPlace);
					ttr.find("[name$='["+tindex+"].fdTravelDays']").val(1);
					tr.find("[name$='["+index+"].fdTravel']").val(ttr.find("[name$='["+tindex+"].fdSubject']").val());
					$('[name="fdTravelList_Form['+index+'].fdPersonListIds"]').val(rtn[i].docCreatorId);
					$('[name="fdTravelList_Form['+index+'].fdPersonListNames"]').val(rtn[i].docCreatorName);
					var addressInput = $("[xform-name='mf_fdTravelList_Form["+tindex+"].fdPersonListNames']")[0];
				    var addressValues = new Array();
				    addressValues.push({id:rtn[i].docCreatorId,name:rtn[i].docCreatorName});
					newAddressAdd(addressInput,addressValues);
				}else{//行程合并，给相关字段赋值
					tr.find("[name$='["+index+"].fdStartPlace']").val(rtn[i].fdEndPlace);
					tr.find("[name$='["+index+"].fdArrivalPlace']").val(rtn[i].fdEndPlace);
					tr.find("[name$='["+index+"].fdTravelDays']").val(1);
				}
				var data = new KMSSData();
				var fdCompanyId = $("[name=fdCompanyId]").val();
				var fdMainId = $("[name=fdId]").val();
				data.AddBeanData("fsscExpenseDetailService&fdNoteId="+rtn[i].fdId+"&fdMainId="+fdMainId+"&fdCompanyId="+fdCompanyId);
				data = data.GetHashMapArray();
				if(data&&data.length>0){
					tr.find("[name$='["+index+"].fdExpenseTempId']").val(data[0].fdTempId);
					tr.find("[name$='["+index+"].fdExpenseTempDetailIds']").val(data[0].fdDetailId);
					tr.find("[name$='["+index+"].fdInvoiceMoney']").val(data[0].fdInvoiceMoney);
					var invoices = JSON.parse(data[0].invoices||'[]');
					for(var m=0;m<invoices.length;m++){
						DocList_AddRow('TABLE_DocList_fdInvoiceList_Form');
						var iindex = $("#TABLE_DocList_fdInvoiceList_Form > tbody > tr").length-2;
						var itr = $("#TABLE_DocList_fdInvoiceList_Form > tbody > tr").eq(iindex+1);
						for(var k in invoices[m]){
							itr.find("[name$='"+k+"']").val(invoices[m][k]);
						}
						if(invoices[m]["fdIsCurrent"]){
                            if(invoices[m]["fdIsCurrent"]=="1"){  //是本公司发票
                                $("[name='fdInvoiceList_Form["+iindex+"].fdIsCurrent']").parent().find("span").html(comlang["message.yes"]);
                            }else if(invoices[m]["fdIsCurrent"]=="0"){  //不是本公司发票
                                $("[name='fdInvoiceList_Form["+iindex+"].fdIsCurrent']").parent().find("span").html(comlang["message.no"]);
                            }
                        }
					}
				}
				FSSC_ChangeIsDeduct("init",null,index);
			}
			FSSC_GetTotalMoney()
			dia.hide();
		})
	}

	//选择交易数据
	window.FSSC_SelectTranData = function(){
		var fdPersonId = $("[name=fdClaimantId]").val();
		$("[name='fdTranDataName']").attr("subject",lang['fsscExpenseDetail.selectTranData.title']);
		dialogSelect(true,'fssc_expense_detail_selectTranData','fdTranDataId','fdTranDataName',null,{'fdPersonId':fdPersonId},function(rtn){
			if(!rtn||rtn.length==0){
				return;
			}
			var dia = dialog.loading();
			for(var i=0;i<rtn.length;i++){
				var detail = $("#TABLE_DocList_fdTranDataList_Form>tbody>tr [name$=fdTranDataId][value="+rtn[i].fdId+"]");
				if(detail.length>0){//交易数据已存在，跳过
					continue;
				}
				DocList_AddRow('TABLE_DocList_fdTranDataList_Form');
				var index = $("#TABLE_DocList_fdTranDataList_Form>tbody>tr").length-2;
				$("[name$='["+index+"].fdCrdNum']").val(rtn[i].fdCrdNum);
				$("[name$='["+index+"].fdActChiNam']").val(rtn[i].fdActChiNam);
				$("[name$='["+index+"].fdTrsDte']").val(rtn[i].fdTrsDte);
				$("[name$='["+index+"].fdTrxTim']").val(rtn[i].fdTrxTim);
				$("[name$='["+index+"].fdOriCurAmt']").val(rtn[i].fdOriCurAmt);
				$("[name$='["+index+"].fdOriCurCod']").val(rtn[i].fdOriCurCod);
				$("[name$='["+index+"].fdTrsCod']").val(rtn[i].fdTrsCod);
				$("[name$='["+index+"].fdState']").val(rtn[i].fdState);
				$("[name$='["+index+"].fdTranDataId']").val(rtn[i].fdId);
			}
			dia.hide();
		})
	}
	
	//--------------------------------------以下为收款明细相关JS代码----------------------------------------
	
	//收款明细选择币种
	window.FSSC_SelectPayCurrency = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var existCurrency = [];
		$("#TABLE_DocList_fdDetailList_Form [name$=fdCurrencyId]").each(function(){
			if(!existCurrency.contains(this.value)){
				existCurrency.push(this.value);
			}
		});
		var fdCompanyId = $("[name='fdCompanyId']").val();
		var fdPayCurrencyType = "fdPayCurrencyType";
		dialogSelect(false,'eop_basedata_currency_fdCurrency','fdAccountsList_Form[*].fdCurrencyId','fdAccountsList_Form[*].fdCurrencyName',null,{existCurrency:existCurrency.join(';'),fdCompanyId:fdCompanyId,fdPayCurrencyType:fdPayCurrencyType},function(rtn){
			if(!rtn||rtn.length==0){
				return;
			}
			var fdCurrencyId = rtn[0].fdId,
    			fdCompanyId = $("[name='fdCompanyId']").val(),
    			data = new KMSSData();
			$("[name='fdAccountsList_Form["+index+"].fdExchangeRate']").val("");
			//如果费用明细已存在相同的币种，直接取该行汇率，防止手动改了汇率后不一致的问题
			var rate = $("#TABLE_DocList_fdDetailList_Form [name$=fdCurrencyId][value="+fdCurrencyId+"]");
			if(rate.length>0){
				rate = DocListFunc_GetParentByTagName('TR',rate[0]);
				rate = $(rate).find("[name$=fdExchangeRate]").val();
				if(rate){
					$("[name='fdAccountsList_Form["+index+"].fdExchangeRate']").val(rate);
				}
			}else{
				data = data.AddBeanData('eopBasedataExchangeRateService&type=getRateByCurrency&authCurrent=true&fdCurrencyId='+fdCurrencyId+'&fdCompanyId='+fdCompanyId).GetHashMapArray();
				if(data.length>0){
					$("[name='fdAccountsList_Form["+index+"].fdExchangeRate']").val(data[0].fdExchangeRate)
				}
			}
		});
	}
	//收款明细选择收款人账户
	window.FSSC_SelectAccount = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		dialogSelect(false,'eop_basedata_account_fdAccount','fdAccountsList_Form[*].fdAccountId','fdAccountsList_Form[*].fdAccountName',null,null,function(rtn){
			if(rtn){
				$("[name='fdAccountsList_Form["+index+"].fdBankName']").val(rtn[0]['fdBankName']);
				$("[name='fdAccountsList_Form["+index+"].fdBankAccount']").val(rtn[0]['fdBankAccount']);
				$("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").val(rtn[0]['fdAccountArea']);
				$("[name='fdAccountsList_Form["+index+"].fdBankAccountNo']").val(rtn[0]['fdBankNo']);
			}
		});
	}
	//收款明细选择收款账户归属地区
	window.FSSC_SelectAccountArea = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		 dialogSelect(false,'fssc_cmb_city_code','fdAccountsList_Form[*].fdAccountAreaCode','fdAccountsList_Form[*].fdAccountAreaName',null,null,function(rtn){
			 if(rtn){
					$("[name='fdAccountsList_Form["+index+"].fdAccountAreaCode']").val(rtn[0]['fdProvincialCode']);
					$("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").val(rtn[0]['fdCity']);
				}
		 });
	}

	//收款明细选择收款账户归属地区(CBS)
	window.FSSC_SelectCbsAccountArea = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		dialogSelect(false,'fssc_cbs_city','fdAccountsList_Form[*].fdAccountAreaCode','fdAccountsList_Form[*].fdAccountAreaName',null,null,function(rtn){
			if(rtn){
				$("[name='fdAccountsList_Form["+index+"].fdAccountAreaCode']").val(rtn[0]['fdProvince']);
				$("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").val(rtn[0]['fdCity']);
			}
		});
	}

	//收款明细选择收款账户归属地区
	window.FSSC_SelectCmbIntAccountArea = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		dialogSelect(false,'fssc_cmbint_city_code','fdAccountsList_Form[*].fdAccountAreaCode','fdAccountsList_Form[*].fdAccountAreaName',null,null,function(rtn){
			if(rtn){
				$("[name='fdAccountsList_Form["+index+"].fdAccountAreaCode']").val(rtn[0]['fdProvincialCode']);
				$("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").val(rtn[0]['fdCity']);
			}
		});
	}

	//收款明细选择付款方式
	window.FSSC_SelectPayWay = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var fdCompanyId = $("[name=fdCompanyId]").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return;
		}
		dialogSelect(false,'eop_basedata_pay_way_getPayWay','fdAccountsList_Form[*].fdPayWayId','fdAccountsList_Form[*].fdPayWayName',null,{fdCompanyId:fdCompanyId},function(rtn){
			if(rtn){
				$("[name='fdAccountsList_Form["+index+"].fdBankId']").val(rtn[0]['fdDefaultPayBank.fdId']);
				$("[name='fdAccountsList_Form["+index+"].fdPayBankName']").val((rtn[0]['fdDefaultPayBank.fdBankName']||'')+(rtn[0]['fdDefaultPayBank.fdBankAccount']||''));
				$("[name='fdAccountsList_Form["+index+"].fdIsTransfer']").val(rtn[0]['fdIsTransfer']);
				initFS_GetFdIsTransfer(index, rtn[0]['fdIsTransfer']);
			}
		});
	}

	window. initFS_GetFdIsTransfer = function(index, fdIsTransfer){
		if(fdIsTransfer =='false'){	//收款账户信息非必填
			$("[name='fdAccountsList_Form["+index+"].fdAccountName']").attr("validate","");
			$("[name='fdAccountsList_Form["+index+"].fdBankName']").attr("validate","maxLength(400) checkNull");
			$("[name='fdAccountsList_Form["+index+"].fdBankAccount']").attr("validate","maxLength(200) checkNull");
			$("[name='fdAccountsList_Form["+index+"].fdBankAccountNo']").attr("validate","maxLength(400) checkNull");
			$("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").attr("validate","");
			$(".vat_"+index).hide();
		}else{	//收款账户信息必填
			$("[name='fdAccountsList_Form["+index+"].fdAccountName']").attr("validate","required");
			$("[name='fdAccountsList_Form["+index+"].fdBankName']").attr("validate","required maxLength(400) checkNull");
			$("[name='fdAccountsList_Form["+index+"].fdBankAccount']").attr("validate","required maxLength(200) checkNull");
			$("[name='fdAccountsList_Form["+index+"].fdBankAccountNo']").attr("validate","required maxLength(400) checkNull");
			$("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").attr("validate","required");
			$(".vat_"+index).show();
		}
	}
	
	//收款明细选择付款银行
	window.FSSC_SelectPayBank = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var fdCompanyId = $("[name=fdCompanyId]").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return;
		}
		dialogSelect(false,'eop_basedata_pay_bank_fdBank','fdAccountsList_Form[*].fdBankId','fdAccountsList_Form[*].fdPayBankName',null,{fdCompanyId:fdCompanyId},function(data){
			if(data){
			$("[name='fdAccountsList_Form["+index+"].fdPayBankName']").val(data[0]['fdBankName']+data[0]['fdBankAccount']);
			}
		});
	}
	//增加一行时自动设置币种汇率
	window.FSSC_SetDefaultCurrency = function(){
		var index = $("#TABLE_DocList_fdAccountsList_Form>tbody>tr").length-2;
		var fdCompanyId = $("[name=fdCompanyId]").val();
		var data = new KMSSData();
		data.AddBeanData("eopBasedataCompanyService&authCurrent=true&type=getStandardCurrencyInfo&fdCompanyId="+fdCompanyId);
		data = data.GetHashMapArray();
		if(data.length>0){
			$("[name='fdAccountsList_Form["+index+"].fdCurrencyId']").val(data[0]['fdCurrencyId']);
			$("[name='fdAccountsList_Form["+index+"].fdExchangeRate']").val(data[0]['fdExchangeRate']);
			$("[name='fdAccountsList_Form["+index+"].fdCurrencyName']").val(data[0]['fdCurrencyName']);
			$("[name='fdAccountsList_Form["+index+"].fdBankAccountNo']").val(data[0]['fdBankNo']);
		}
		$("[name='fdAccountsList_Form["+index+"].fdAccountName']").attr("validate","required");
		$("[name='fdAccountsList_Form["+index+"].fdBankName']").attr("validate","required maxLength(400) checkNull");
		$("[name='fdAccountsList_Form["+index+"].fdBankAccount']").attr("validate","required maxLength(200) checkNull");
		$("[name='fdAccountsList_Form["+index+"].fdBankAccountNo']").attr("validate","required maxLength(400) checkNull");
		$("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").attr("validate","required checkNull");
	}
	
	//--------------------------------------以下为发票明细相关JS代码----------------------------------------
	//增加一行时自动设置默认公司
	window.FSSC_SetDefaultCompany = function(){
		var index = $("#TABLE_DocList_fdInvoiceList_Form>tbody>tr").length-2;
		var fdCompanyId = $("[name=fdCompanyId]").val();
		$("[name='fdInvoiceList_Form["+index+"].fdCompanyId']").val(fdCompanyId);
	}
	//选择发票
	window.FSSC_SelectInvoice = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		dialogSelect(false,'fssc_ledger_fdInvoice','fdInvoiceList_Form[*].fdInvoiceNumberId','fdInvoiceList_Form[*].fdInvoiceNumber',null,null,function(rtn){
			if(rtn){
				$("[name='fdInvoiceList_Form["+index+"].fdInvoiceNumber']").val(rtn[0]['fdInvoiceNumber']);
				$("[name='fdInvoiceList_Form["+index+"].fdInvoiceCode']").val(rtn[0]['fdInvoiceCode']);
				getInvoiceInfo(rtn[0]['fdInvoiceNumber'],rtn[0]['fdInvoiceCode'],index);
			}
		});
	}
	//选择发票
	window.FSSC_Invoice = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var fdInvoiceNumber=$("[name='fdInvoiceList_Form["+index+"].fdInvoiceNumber']").val();
		var fdInvoiceCode=$("[name='fdInvoiceList_Form["+index+"].fdInvoiceCode']").val();
		getInvoiceInfo(fdInvoiceNumber,fdInvoiceCode,index);
	}
	
	/**
	 * 根据发票号码获取发票信息
	 */
	
	window.getInvoiceInfo=function(fdInvoiceNumber,fdInvoiceCode,index){
		var fdCompanyId=$("[name='fdCompanyId']").val();
		//通过发票单号自动带出对应的信息
		var data = new KMSSData();
		data.AddBeanData("fsscExpenseDataService&type=getInvoiceInfoByCode&authCurrent=true&fdInvoiceNumber="+fdInvoiceNumber+"&fdInvoiceCode="+fdInvoiceCode);
		var rtnVal = data.GetHashMapArray();
		if(rtnVal.length>0){
			$("[name='fdInvoiceList_Form["+index+"].fdInvoiceCode']").val("");
			$("[name='fdInvoiceList_Form["+index+"].fdInvoiceDate']").val("");
			$("[name='fdInvoiceList_Form["+index+"].fdInvoiceMoney']").val("");
			$("[name='fdInvoiceList_Form["+index+"].fdTax']").val("");
			$("[name='fdInvoiceList_Form["+index+"].fdTaxMoney']").val("");
			$("[name='fdInvoiceList_Form["+index+"].fdNoTaxMoney']").val("");
			$("[name='fdInvoiceList_Form["+index+"].fdInvoiceCode']").val(rtnVal[0].fdInvoiceCode);
			$("[name='fdInvoiceList_Form["+index+"].fdInvoiceDate']").val(rtnVal[0].fdInvoiceDate);
			$("[name='fdInvoiceList_Form["+index+"].fdCheckCode']").val(rtnVal[0].fdCheckCode);
			$("[name='fdInvoiceList_Form["+index+"].fdCompanyId']").val(fdCompanyId);
			if(rtnVal[0].fdJshj){
				$("[name='fdInvoiceList_Form["+index+"].fdInvoiceMoney']").val(formatFloat(rtnVal[0].fdJshj,2));
			}
			if(rtnVal[0].fdSl){
				$("[name='fdInvoiceList_Form["+index+"].fdTax']").val(formatFloat(rtnVal[0].fdSl,2));
			}
			if(rtnVal[0].fdTotalTax){
				$("[name='fdInvoiceList_Form["+index+"].fdTaxMoney']").val(formatFloat(rtnVal[0].fdTotalTax,2));
			}
			if(rtnVal[0].fdJshj&&rtnVal[0].fdTotalTax){
				$("[name='fdInvoiceList_Form["+index+"].fdNoTaxMoney']").val(subPoint(rtnVal[0].fdJshj,rtnVal[0].fdTotalTax));
			}
		}
	}
	
	//发票明细选择费用类型
	window.FSSC_SelectInvoiceType = function(obj){
		var index = DocListFunc_GetParentByTagName('TR',obj).rowIndex-1;
		var fdCompanyId = $("[name='fdInvoiceList_Form["+index+"].fdCompanyId']").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return false;
		}
		dialogSelect(false,'eop_basedata_expense_item_selectExpenseItem','fdInvoiceList_Form[*].fdExpenseTypeId','fdInvoiceList_Form[*].fdExpenseTypeName',null,{fdCompanyId:fdCompanyId,fdNotId:'fdNotId'});
	}
	//发票明细切换是否增值税发票
	window.FSSC_ChangeIsVat = function(v,e,index){
		var tr = DocListFunc_GetParentByTagName("TR");
		if(index==null||isNaN(index)){
    		index = tr.rowIndex - 1;
		}
		var fdInvoiceType = $("[name='fdInvoiceList_Form["+index+"].fdInvoiceType']").val();
		if("10100"==fdInvoiceType){
			$(tr).find(".vat").find(".txtstrong").show();
			$(tr).find(".vat").find("input[type=text]").each(function(){
				var validate = $(this).attr("validate")||'';
				if(validate.indexOf('required')==-1){
					validate += ' required';
				}
				$(this).attr("validate",validate);
			});
		}else{
			$(tr).find(".vat").find(".txtstrong").hide();
			$(tr).find(".vat").parent().find(".validation-advice").hide();
			$(tr).find(".vat").find("input[type=text]").each(function(){
				var validate = $(this).attr("validate")||'';
					validate = validate.replace(/required/g,'');
				$(this).attr("validate",validate);
			});
			$(tr).find(".vat").find("[type=text],[type=hidden]").each(function(){
				$(this).val("");
			});
		}
	}
	
	window.FSSC_ChangeIsVat_target = function(e,index){
		var tr= DocListFunc_GetParentByTagName("TR",e);
		if(index==null||isNaN(index)){
    		index = tr.rowIndex - 1;
		}
		var fdInvoiceType = $("[name='fdInvoiceList_Form["+index+"].fdInvoiceType']").val();
		if("10100"==fdInvoiceType){
			$(tr).find(".vat").find(".txtstrong").show();
			$(tr).find(".vat").find("input[type=text]").each(function(){
				var validate = $(this).attr("validate")||'';
				if(validate.indexOf('required')==-1){
					validate += ' required';
				}
				$(this).attr("validate",validate);
			});
		}else{
			$(tr).find(".vat").find(".txtstrong").hide();
			$(tr).find(".vat").parent().find(".validation-advice").hide();
			$(tr).find(".vat").find("input[type=text]").each(function(){
				var validate = $(this).attr("validate")||'';
					validate = validate.replace(/required/g,'');
				$(this).attr("validate",validate);
			});
		}
	}
	//选择税率
	window.FSSC_SelectTaxRate = function(obj){
		var index = DocListFunc_GetParentByTagName('TR',obj).rowIndex-1;
		var fdCompanyId = $("[name='fdInvoiceList_Form["+index+"].fdCompanyId']").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return false;
		}
		dialogSelect(false,'eop_basedata_tax_rate_getTaxRate','fdInvoiceList_Form[*].fdTax1','fdInvoiceList_Form[*].fdTax',null,{fdCompanyId:fdCompanyId,fdNotId:'fdNotId'},function(rtn){
			if(!rtn){
				return;
			}
			var rate = rtn[0].fdRate.split('%')[0];
			$("[name='fdInvoiceList_Form["+index+"].fdTax']").val(rate);
			//计算税额、不含税额
			FSSC_GetTaxMoney(obj);
		});
	}
	
	window.FSSC_GetTaxMoney = function(obj,e){
		if(e){
			obj = e;
		}
		var index = DocListFunc_GetParentByTagName('TR',obj).rowIndex-1;
		var rate = $("[name='fdInvoiceList_Form["+index+"].fdTax']").val()*1;
		//计算税额、不含税额
		var fdInvoiceMoney = $("[name='fdInvoiceList_Form["+index+"].fdInvoiceMoney']").val()*1;
		//不含税额=发票金额/(1+税率)
		var fdNotTaxMoney = numDiv(fdInvoiceMoney,numAdd(1,numDiv(rate,100)));
		fdNotTaxMoney = parseFloat(fdNotTaxMoney).toFixed(2);
		$("[name='fdInvoiceList_Form["+index+"].fdNoTaxMoney']").val(fdNotTaxMoney);
		$("[name='fdInvoiceList_Form["+index+"].fdTaxMoney']").val(numSub(fdInvoiceMoney*1,fdNotTaxMoney*1));
	}
	
	//编辑时加载必填项
	$(function(){
		$("#TABLE_DocList_fdInvoiceList_Form tr:gt(0)").each(function(){
			var fdIsVat = $(this).find("[name$=fdIsVat]:checked").val()
			if(fdIsVat=='true'){
				$(this).find(".txtstrong").show();
				var validate = $(this).find("input[type=text]").attr("validate");
				$(this).find("input[type=text]").attr("validate",validate+" required");
			}
		});
	});
	
	//--------------------------------------以下为冲抵借款明细相关JS代码----------------------------------------
	
	//加载冲抵借款明细
	window.FSSC_LoadLoanInfo = function(){
		var fdId = Com_GetUrlParameter(window.location.href,'fdId');
		$("#LoanTable").html("");
		$.post(
			Com_Parameter.ContextPath+'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=getLoanData',
			{fdId:fdId,fdPersonId:$("[name=fdClaimantId]").val(),flag:'edit',fdCompanyId:$("[name=fdCompanyId]").val()},
			function(rtn){
				$("#LoanTable").html(rtn);
			}
		);
	}
	//自动计算剩余金额
	window.FSSC_GetLeftMoney = function(v,e){
		var index = e.name.replace(/\S+\[(\d+)\]\S+/g,'$1');
		var fdOffsetMoney = $("[name='fdOffsetList_Form["+index+"].fdOffsetMoney']").val();
		var fdCanOffsetMoney = $("[name='fdOffsetList_Form["+index+"].fdCanOffsetMoney']").val();
		var fdLeftMoney = numSub(fdCanOffsetMoney,fdOffsetMoney)*1;
		if(isNaN(fdLeftMoney)){
			return;
		}
		$("[name='fdOffsetList_Form["+index+"].fdLeftMoney']").val(fdLeftMoney.toFixed(2));
		$("[id='_xform_fdOffsetList_Form["+index+"].fdLeftMoney']").html(fdLeftMoney.toFixed(2))
		var totalStandardMoney = 0;
		$("[name$=fdStandardMoney]").each(function(){
			totalStandardMoney = addPoint(totalStandardMoney,this.value);
		});
		if(isNaN(totalStandardMoney)){
			return;
		}
		var len = $("#TABLE_DocList_fdAccountsList_Form>tbody>tr:gt(0)").length;
		if(len==1){//如果只有一行收款账户，自动计算总金额
			var tr = $("#TABLE_DocList_fdAccountsList_Form>tbody>tr:eq(1)");
			$("[name$=fdOffsetMoney]").each(function(){
				if(!isNaN(this.value)){
					totalStandardMoney = numSub(totalStandardMoney,this.value);
				}
			})
			var fdRate = tr.find("[name$=fdExchangeRate]").val()*1;
			if(!isNaN(fdRate)&&fdRate!=0){
				tr.find("[name$=fdMoney]").val(divPoint(totalStandardMoney,fdRate));
			}
		}
	}
	
	window.FSSC_ChangeIsOffsetLoan = function(){
		var fdIsOffsetLoan = $("[name=fdIsOffsetLoan]").val();
		if(fdIsOffsetLoan=='true'){
			$("#LoanTable").show();
		}else{
			$("#LoanTable").hide();
			FSSC_ClearFormValues();
		}
	}
	// 清空 借款信息 表单得值
	window.FSSC_ClearFormValues = function(){
		var len = $("#TABLE_DocList_fdOffsetList_Form tr").length-1;
		for(var index=0;index<len;index++){
			var fdCanOffsetMoney = $("[name='fdOffsetList_Form["+index+"].fdCanOffsetMoney']").val();
			$("[name='fdOffsetList_Form["+index+"].fdLeftMoney']").val(fdCanOffsetMoney);
			$("[id='_xform_fdOffsetList_Form["+index+"].fdLeftMoney']").html(fdCanOffsetMoney);
			$("[name='fdOffsetList_Form["+index+"].fdOffsetMoney']").val("");
		}
	}

	$(function(){
		//若有借款明细，则加载
		if($("#LoanTable").length>0){
			if(Com_GetUrlParameter(window.location.href,'method')=='add'){
				$("[name=fdIsOffsetLoan]").val(true);
				$("[name=_fdIsOffsetLoan]").prop("checked",true);
			}
			FSSC_LoadLoanInfo();
			FSSC_ChangeIsOffsetLoan();
		}
	})
	//--------------------------------------以下为摊销明细相关JS代码----------------------------------------
	
	//初始化摊销期间选择器
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
	    jeDate("#fdAmortizeBegin",{
	        format: "YYYY-MM",
	        donefun:function(){
	        	FSSC_ChangeAmortizeDate();
	        }
	    });
		jeDate("#fdAmortizeEnd",{
		    format: "YYYY-MM",
		    donefun:function(){
	        	FSSC_ChangeAmortizeDate();
	        }
		});
	}
	
	//选择摊销月份
	window.FSSC_ChangeAmortizeDate = function(){
		var len = $("#TABLE_DocList_Amortize tr").length;
		for(var i=1;i<len;i++){
			DocList_DeleteRow($("#TABLE_DocList_Amortize tr")[1]);
		}
		if(window.formValiduteObj&&!window.formValiduteObj.validateElement($("[name=fdAmortizeBegin]")[0])||window.formValiduteObj&&!window.formValiduteObj.validateElement($("[name=fdAmortizeEnd]")[0])){
			return;
		}
		var fdAmortizeMoney = $("[name=fdTotalApprovedMoney]").val(); 
		$("[name=fdAmortizeMoney]").val(fdAmortizeMoney);
		var fdAmortizeBegin = $("[name=fdAmortizeBegin]").val().split("-");
		var fdAmortizeEnd = $("[name=fdAmortizeEnd]").val().split("-");
		var monthes = parseInt((fdAmortizeEnd[0]*1-fdAmortizeBegin[0]*1)*12+fdAmortizeEnd[1]*1-fdAmortizeBegin[1]*1)+1;
		if(isNaN(monthes)){
			return;
		}
		$("[name=fdAmortizeMonth]").val(monthes);
		var use = 0,percent = parseFloat(divPoint(100,monthes)).toFixed(2),peruse = 0;
		for(var i=0;i<monthes;i++){
			DocList_AddRow('TABLE_DocList_Amortize');
			if(i==monthes-1){
				$("[name='fdAmortizeList_Form["+i+"].fdMoney']").val(parseFloat(numSub(fdAmortizeMoney,use)).toFixed(2));
				$("[name='fdAmortizeList_Form["+i+"].fdPercent']").val(parseFloat(numSub(100,peruse)).toFixed(2));
			}else{
				var mon = fdAmortizeMoney*percent/100;
				$("[name='fdAmortizeList_Form["+i+"].fdMoney']").val(mon.toFixed(2));
				$("[name='fdAmortizeList_Form["+i+"].fdPercent']").val(percent);
			}
			use = numAdd(use,mon.toFixed(2));
			peruse = numAdd(peruse,percent);
			var year = fdAmortizeBegin[0]*1+parseInt((fdAmortizeBegin[1]*1+i)/13),mon1 = (fdAmortizeBegin[1]*1+i)%12;
			mon1 = mon1||12;
			mon1 = mon1>9?mon1:('0'+mon1);
			$("[name='fdAmortizeList_Form["+i+"].fdMonth']").val(year+"-"+mon1);
		}
	}
	
	window.FSSC_ChangeAmortizePercent = function(v,e){
		var index = e.name.replace(/\S+\[(\d+)\]\S+/g,'$1');
		var fdAmortizeMoney = $("[name=fdAmortizeMoney]").val();
		if(isNaN(v)){
			return;
		}
		var fdMoney = numMulti(fdAmortizeMoney,v);
		fdMoney = divPoint(fdMoney,100)*1;
		$("[name=fdAmortizeMoney]").val();
		$("[name='fdAmortizeList_Form["+index+"].fdMoney']").val(fdMoney.toFixed(2));
		//FSSC_ChangeAmortizeDate();
	}
	
	/***************************************添加发票  start*******************************************/
	/***********************************
	 * 添加发票，弹出页面上传发票，识别后自动增加报销明细和发票明细
	 ************************************/
	window.FSSC_AddInvoice=function(){
		var fdCompanyId=$("[name='fdCompanyId']").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return false;
		}
		var fdMainId=$("[name='fdId']").val();
		var fdCategoryId = $("[name=docTemplateId]").val();
		var url = "/fssc/expense/fssc_expense_temp/fsscExpenseTemp.do?method=add&fdCompanyId="+fdCompanyId+"&fdMainId="+fdMainId+"&fdCategoryId="+fdCategoryId;
		window.dia = dialog.iframe(url,lang['button.addInvoice'],
				function(data) {
					if(!data){
						return;
					}else{
						data=JSON.parse(data.replace(/\'/g,'"'));
						//新增费用明细
						var detailInfo=data.detailInfo;
						for(var key in detailInfo){
							var detail=detailInfo[key];
						  	var index = $("#TABLE_DocList_fdDetailList_Form [name$=fdExpenseItemId]").length;
							FSSC_AddExpenseDetail();
							clearDetailValue(index);//由于报销加行会复制上一行数据，影响发票赋值，故清空重新赋值
							var taxRate = $("[name='fdDetailList_Form["+index+"].fdExchangeRate']").val()||1;
							var budgetRate = $("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val()||1; 
							var smon = multiPoint(detail.fdInvoiceMoney||0,taxRate,2);
							var bmon = multiPoint(detail.fdInvoiceMoney||0,budgetRate,2);
							if(!$("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val()){
								$("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val(detail.fdExpenseItemId);
							}
							if(!$("[name='fdDetailList_Form["+index+"].fdExpenseItemName']").val()){
								$("[name='fdDetailList_Form["+index+"].fdExpenseItemName']").val(detail.fdExpenseItemName);
							}
							$("[name='fdDetailList_Form["+index+"].fdApplyMoney']").val(detail.fdInvoiceMoney);
							$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(bmon);
							$("[name='fdDetailList_Form["+index+"].fdStandardMoney']").val(smon);
							$("[name='fdDetailList_Form["+index+"].fdApprovedStandardMoney']").val(smon);
							$("[name='fdDetailList_Form["+index+"].fdApprovedApplyMoney']").val(smon);
							if(!$("[name='fdDetailList_Form["+index+"].fdHappenDate']").val()){
								$("[name='fdDetailList_Form["+index+"].fdHappenDate']").val(detail.fdInvoiceDate);
							}
							$("[name='fdDetailList_Form["+index+"].fdInvoiceMoney']").val(detail.fdInvoiceMoney);
							$("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val(detail.fdNonDeductMoney);
							$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(detail.fdNoTaxMoney);
							$("[name='fdDetailList_Form["+index+"].fdExpenseTempId']").val(detail.fdExpenseTempId);
							$("[name='fdDetailList_Form["+index+"].fdExpenseTempDetailIds']").val(detail.fdExpenseTempDetailIds);
							$("[name='fdDetailList_Form["+index+"].fdCheckCode']").val(detail.fdCheckCode);
							//设置是否抵扣，专票&&配置了对应税率时才需要抵扣
							$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxMoney']").hide();
							$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxRate']").hide();
							$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").attr("validate","currency-dollar");
							$("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val(false);
							$("[name='_fdDetailList_Form["+index+"].fdIsDeduct'][value=true]").prop('checked',false);
							$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(0);
							$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val('');
							$("[name='fdDetailList_Form["+index+"].fdInputTaxRateId']").val('');
							$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(detail.fdInvoiceMoney);//不含税金额
							var fdInvoiceType = detail.fdInvoiceType;
							var fdTaxRate = detail.fdTax;
							if(fdInvoiceType=='10100'){//专票
								var data1 = new KMSSData();
								data1 = data1.AddBeanData("eopBasedataInputTaxService&authCurrent=true&multi=true&fdExpenseItemId="+$("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val());
								data1 = data1.GetHashMapArray();
								if(data1&&data1.length>0){
									var rate = null;
									for(var k=0;k<data1.length;k++){
										if(fdTaxRate*1==data1[k].fdTaxRate*1){//如果配置了同样的税率，则取该税率
											rate = data1[k];
											break;
										}
									}
//									if(!rate){
//										rate = data1[data1.length-1];
//									}
									$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxMoney']").show();
									$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxRate']").show();
									$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").attr("validate","required  currency-dollar");
									$("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val(true);
									$("[name='_fdDetailList_Form["+index+"].fdIsDeduct'][value=true]").prop('checked',true);
									//如果进项税率匹配不上基础数据，进项税率、税额和不含税金额不做计算
									var fdNoTaxMoney = null;
									if(null !== rate){
										fdNoTaxMoney = divPoint(detail.fdInvoiceMoney*1,numAdd(1,numDiv(rate.fdTaxRate*1,100)));
										$("[name='fdDetailList_Form["+index+"].fdInputTaxRateId']").val(rate.fdTaxRateId);
										$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(detail.fdTaxMoney);
										$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val(rate.fdTaxRate);
									}else{
										fdNoTaxMoney = detail.fdInvoiceMoney*1;
									}
									$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdNoTaxMoney);//不含税金额
									$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(subPoint(detail.fdInvoiceMoney*1,fdNoTaxMoney));
								}
							}
							FSSC_MatchBudget(null,$("[name='fdDetailList_Form["+index+"].fdApplyMoney']").get(0));  //预算匹配
							FSSC_MathStandard(index);  //标准匹配
							FSSC_GetTotalMoney();
						}
						//新增发票明细
						data=data.params;
						var number=new Map(),n=0;
						$($("#TABLE_DocList_fdInvoiceList_Form [name$=fdInvoiceNumber]")).each(function(){
								number.set($(this).val(),n);
								n++;
							}
						)
						var num_code = [];
						for(var i=0;i<data.length;i++){
							if(number.has(data[i].fdInvoiceNumber)){
								num_code.push(data[i].fdInvoiceNumber+'|'+data[i].fdInvoiceCode);
								var index=number.get(data[i].fdInvoiceNumber);
								//更新现有发票信息
								$("[name='fdInvoiceList_Form["+index+"].fdInvoiceType']").val(data[i].fdInvoiceType);
								$("[name='_fdInvoiceList_Form["+index+"].fdInvoiceType']").val(data[i].fdInvoiceType);
								$("[name='fdInvoiceList_Form["+index+"].fdInvoiceCode']").val(data[i].fdInvoiceCode);
								$("[name='fdInvoiceList_Form["+index+"].fdInvoiceDate']").val(data[i].fdInvoiceDate);
								$("[name='fdInvoiceList_Form["+index+"].fdInvoiceMoney']").val(data[i].fdInvoiceMoney);
								$("[name='fdInvoiceList_Form["+index+"].fdTaxMoney']").val(data[i].fdTaxMoney);
								$("[name='fdInvoiceList_Form["+index+"].fdNoTaxMoney']").val(data[i].fdNoTaxMoney);
								$("[name='fdInvoiceList_Form["+index+"].fdCheckCode']").val(data[i].fdCheckCode);
								$("[name='fdInvoiceList_Form["+index+"].fdTaxNumber']").val(data[i].fdTaxNumber);
								$("[name='fdInvoiceList_Form["+index+"].fdPurchName']").val(data[i].fdPurchName);
								if(data[i].fdIsCurrent){
									$("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").val(data[i].fdIsCurrent);
									if(data[i].fdIsCurrent=="1"){  //是本公司发票
										$("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").parent().find("span").html(comlang["message.yes"]);
									}else if(data[i].fdIsCurrent=="0"){  //不是本公司发票
										$("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").parent().find("span").html(comlang["message.no"]);
									}
								}
								//如果是增值税专用发票
								if("10100"==data[i].fdInvoiceType||"true"==data[i].fdIsVat){
									$("[name='_fdInvoiceList_Form["+index+"].fdIsVat']").prop("checked",true);
									$("[name='fdInvoiceList_Form["+index+"].fdIsVat']").val(true);
									if("10100"==data[i].fdInvoiceType){
										FSSC_ChangeIsVat_target($("[name='fdInvoiceList_Form["+index+"].fdIsVat']").get(0),index);
									}
								}else{
									$("[name='_fdInvoiceList_Form["+index+"].fdIsVat']").prop("checked",false);
									$("[name='fdInvoiceList_Form["+index+"].fdIsVat']").val(false);
									FSSC_ChangeIsVat_target($("[name='fdInvoiceList_Form["+index+"].fdIsVat']").get(0),index);
								}
								$("[name='fdInvoiceList_Form["+index+"].fdExpenseTypeId']").val(data[i].fdExpenseItemId);
								$("[name='fdInvoiceList_Form["+index+"].fdExpenseTypeName']").val(data[i].fdExpenseItemName);
								$("[name='fdInvoiceList_Form["+index+"].fdTax']").val(data[i].fdTax);
								$("[name='fdInvoiceList_Form["+index+"].fdTax1']").val(data[i].fdTaxId);
								$("[name='fdInvoiceList_Form["+index+"].fdCheckStatus']").val(data[i].fdCheckStatus);
								$("[name='fdInvoiceList_Form["+index+"].fdState']").val(data[i].fdState);
							}else{
								num_code.push(data[i].fdInvoiceNumber+'|'+data[i].fdInvoiceCode);
								var index = $("#TABLE_DocList_fdInvoiceList_Form [name$=fdInvoiceNumber]").length;
								DocList_AddRow("TABLE_DocList_fdInvoiceList_Form");
								FSSC_SetDefaultCompany();
								$("[name='fdInvoiceList_Form["+index+"].fdInvoiceType']").val(data[i].fdInvoiceType);
								$("[name='_fdInvoiceList_Form["+index+"].fdInvoiceType']").val(data[i].fdInvoiceType);
								$("[name='fdInvoiceList_Form["+index+"].fdInvoiceNumber']").val(data[i].fdInvoiceNumber);
								$("[name='fdInvoiceList_Form["+index+"].fdInvoiceCode']").val(data[i].fdInvoiceCode);
								$("[name='fdInvoiceList_Form["+index+"].fdInvoiceDate']").val(data[i].fdInvoiceDate);
								$("[name='fdInvoiceList_Form["+index+"].fdInvoiceMoney']").val(data[i].fdInvoiceMoney);
								$("[name='fdInvoiceList_Form["+index+"].fdTaxMoney']").val(data[i].fdTaxMoney);
								$("[name='fdInvoiceList_Form["+index+"].fdNoTaxMoney']").val(data[i].fdNoTaxMoney);
								$("[name='fdInvoiceList_Form["+index+"].fdCheckStatus']").val(data[i].fdCheckStatus);
								$("[name='fdInvoiceList_Form["+index+"].fdState']").val(data[i].fdState);
								$("[name='fdInvoiceList_Form["+index+"].fdCheckCode']").val(data[i].fdCheckCode);
								$("[name='fdInvoiceList_Form["+index+"].fdTaxNumber']").val(data[i].fdTaxNumber);
								$("[name='fdInvoiceList_Form["+index+"].fdPurchName']").val(data[i].fdPurchName);
								if(data[i].fdIsCurrent){
									$("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").val(data[i].fdIsCurrent);
									if(data[i].fdIsCurrent=="1"){  //是本公司发票
										$("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").parent().find("span").html(comlang["message.yes"]);
									}else if(data[i].fdIsCurrent=="0"){  //不是本公司发票
										$("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").parent().find("span").html(comlang["message.no"]);
									}
								}
								//如果是增值税专用发票
								if("10100"==data[i].fdInvoiceType||"true"==data[i].fdIsVat){
									$("[name='_fdInvoiceList_Form["+index+"].fdIsVat']").prop("checked",true);
									$("[name='fdInvoiceList_Form["+index+"].fdIsVat']").val(true);
									if("10100"==data[i].fdInvoiceType){
										FSSC_ChangeIsVat_target($("[name='fdInvoiceList_Form["+index+"].fdIsVat']").get(0),index);
									}
								}else{
									$("[name='_fdInvoiceList_Form["+index+"].fdIsVat']").prop("checked",false);
									$("[name='fdInvoiceList_Form["+index+"].fdIsVat']").val(false);
									FSSC_ChangeIsVat_target($("[name='fdInvoiceList_Form["+index+"].fdIsVat']").get(0),index);
								}
								$("[name='fdInvoiceList_Form["+index+"].fdExpenseTypeId']").val(data[i].fdExpenseItemId);
								$("[name='fdInvoiceList_Form["+index+"].fdExpenseTypeName']").val(data[i].fdExpenseItemName);
								$("[name='fdInvoiceList_Form["+index+"].fdTax']").val(data[i].fdTax);
								$("[name='fdInvoiceList_Form["+index+"].fdTax1']").val(data[i].fdTaxId);
							}
						}
						clearInvoiceDetail();  //清除页面不存在发票
						if($("#TABLE_DocList_fdDidiDetail").length>0){//如果有滴滴行程明细，同步带出来
							var kmssData = new KMSSData();
							kmssData.AddBeanData("fsscDidiOrderInfoService&authCurrent=true&type=getOrderByInvoice&invoiceInfo="+encodeURI(num_code.join(';')));
							kmssData = kmssData.GetHashMapArray();
							if(kmssData.length>0){
								for(var i=0;i<kmssData.length;i++){
									var tr = DocList_AddRow("TABLE_DocList_fdDidiDetail");
									for(var key in kmssData[i]){
										$(tr).find("[name$="+key+"]").val(kmssData[i][key]);
									}
								}
							}
						}
					}
				},
				{
					height:'600',
					width:'1200',
					"buttons" : [],
					"content" : {
								scroll :  false
						}
				});
	};
	
	window.clearDetailValue=function(index){
		$("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val("");
		$("[name='fdDetailList_Form["+index+"].fdExpenseItemName']").val("");
		$("[name='fdDetailList_Form["+index+"].fdHappenDate']").val("");
		// 实际使用人
		FSSC_InitCurrencyAndRate(index);
	}
	window.viewInvoiceTemp=function(){
		var fdCompanyId=$("[name='fdCompanyId']").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return false;
		}
		var fdCategoryId = $("[name=docTemplateId]").val();
		var fdMainId=$("[name='fdId']").val();
		var index= DocListFunc_GetParentByTagName("TR").rowIndex-1;
		var fdExpenseTempId=$("[name='fdDetailList_Form["+index+"].fdExpenseTempId']").val();
		var fdExpenseTempDetailIds=$("[name='fdDetailList_Form["+index+"].fdExpenseTempDetailIds']").val();
		if(fdExpenseTempId){//是由发票识别生成的，点击查看发票
			var url = "/fssc/expense/fssc_expense_temp/fsscExpenseTemp.do?method=edit&fdId="+fdExpenseTempId+"&index="+index+"&fdCompanyId="+fdCompanyId+"&fdExpenseTempDetailIds="+fdExpenseTempDetailIds+"&fdMainId="+fdMainId+"&fdCategoryId="+fdCategoryId;;
			window.dia = dialog.iframe(url,lang['button.addInvoice'],
					function(data) {
						if(!data){
							return;
						}else{
							updateExpenseAndInvoice(data,'edit');
						}
					},
					{
						height:'600',
						width:'1200',
						"buttons" : [],
						"content" : {
									scroll :  false
							}
					});
		}else{//手动新建的，点击可上传发票
			var url = "/fssc/expense/fssc_expense_temp/fsscExpenseTemp.do?method=add&fdCompanyId="+fdCompanyId+"&index="+index+"&fdMainId="+fdMainId+"&fdCategoryId="+fdCategoryId;;
			window.dia = dialog.iframe(url,lang['button.addInvoice'],
					function(data) {
						if(!data){
							return;
						}else{
							updateExpenseAndInvoice(data,'add');
						}
					},
					{
						height:'600',
						width:'1200',
						"buttons" : [],
						"content" : {
									scroll :  false
							}
					});
		}
	}
	
	/*************************************************************
	 * 明细中添加发票或者编辑发票
	 * ***********************************************************/
	 window.updateExpenseAndInvoice=function(data,method){
			data=JSON.parse(data.replace(/\'/g,'"'));
			//重新给当前明细赋值
			var currentIndex=data.index;
			var valueJson=data.valueJson;
			if(method=='add'){
				if(!$("[name='fdDetailList_Form["+currentIndex+"].fdExpenseItemId']").val()){
					$("[name='fdDetailList_Form["+currentIndex+"].fdExpenseItemId']").val(valueJson.fdExpenseItemId);
				}
				if(!$("[name='fdDetailList_Form["+currentIndex+"].fdExpenseItemName']").val()){
					$("[name='fdDetailList_Form["+currentIndex+"].fdExpenseItemName']").val(valueJson.fdExpenseItemName);
				}
				if(!$("[name='fdDetailList_Form["+currentIndex+"].fdHappenDate']").val()){
					$("[name='fdDetailList_Form["+currentIndex+"].fdHappenDate']").val(valueJson.fdInvoiceDate);
				}
				$("[name='fdDetailList_Form["+currentIndex+"].fdExpenseTempId']").val(valueJson.fdExpenseTempId);
			}
			$("[name='fdDetailList_Form["+currentIndex+"].fdApplyMoney']").val(valueJson.fdInvoiceMoney);
			$("[name='fdDetailList_Form["+currentIndex+"].fdInvoiceMoney']").val(valueJson.fdInvoiceMoney);
			$("[name='fdDetailList_Form["+currentIndex+"].fdNonDeductMoney']").val(valueJson.fdNonDeductMoney);
			$("[name='fdDetailList_Form["+currentIndex+"].fdExpenseTempDetailIds']").val(valueJson.fdExpenseTempDetailIds);
			$("[name='fdDetailList_Form["+currentIndex+"].fdNoTaxMoney']").val(valueJson.fdNoTaxMoney);
			//设置是否抵扣，专票&&配置了对应税率时才需要抵扣
			$("[id='_xform_fdDetailList_Form["+currentIndex+"].fdInputTaxMoney']").hide();
			$("[id='_xform_fdDetailList_Form["+currentIndex+"].fdInputTaxRate']").hide();
			$("[name='fdDetailList_Form["+currentIndex+"].fdInputTaxMoney']").attr("validate","currency-dollar");
			$("[name='fdDetailList_Form["+currentIndex+"].fdIsDeduct']").val(false);
			$("[name='_fdDetailList_Form["+currentIndex+"].fdIsDeduct'][value=true]").prop('checked',false);
			$("[name='fdDetailList_Form["+currentIndex+"].fdInputTaxMoney']").val(0);
			$("[name='fdDetailList_Form["+currentIndex+"].fdInputTaxRate']").val('');
			$("[name='fdDetailList_Form["+currentIndex+"].fdInputTaxRateId']").val('');
			$("[name='fdDetailList_Form["+currentIndex+"].fdNoTaxMoney']").val(valueJson.fdInvoiceMoney);//不含税金额
			var fdInvoiceType = valueJson.fdInvoiceType;
			var fdTaxRate = valueJson.fdTax;
			if(fdInvoiceType=='10100'){//专票
				var data1 = new KMSSData();
				data1 = data1.AddBeanData("eopBasedataInputTaxService&authCurrent=true&multi=true&fdExpenseItemId="+$("[name='fdDetailList_Form["+currentIndex+"].fdExpenseItemId']").val());
				data1 = data1.GetHashMapArray();
				if(data1&&data1.length>0){
					var rate = null;
					for(var k=0;k<data1.length;k++){
						if(fdTaxRate*1==data1[k].fdTaxRate*1){//如果配置了同样的税率，则取该税率
							rate = data1[k];
							break;
						}
					}
//					if(!rate){
//						rate = data1[data1.length-1];
//					}
					$("[id='_xform_fdDetailList_Form["+currentIndex+"].fdInputTaxMoney']").show();
					$("[id='_xform_fdDetailList_Form["+currentIndex+"].fdInputTaxRate']").show();
					$("[name='fdDetailList_Form["+currentIndex+"].fdInputTaxMoney']").attr("validate","required  currency-dollar");
					$("[name='fdDetailList_Form["+currentIndex+"].fdIsDeduct']").val(true);
					$("[name='_fdDetailList_Form["+currentIndex+"].fdIsDeduct'][value=true]").prop('checked',true);
					//如果进项税率匹配不上基础数据，进项税率、税额和不含税金额不做计算
					var fdNoTaxMoney = null;
					if(null !== rate){
						fdNoTaxMoney = divPoint(valueJson.fdInvoiceMoney*1,numAdd(1,numDiv(rate.fdTaxRate*1,100)));
						$("[name='fdDetailList_Form["+currentIndex+"].fdInputTaxRateId']").val(rate.fdTaxRateId);
						$("[name='fdDetailList_Form["+currentIndex+"].fdInputTaxMoney']").val(valueJson.fdTaxMoney);
						$("[name='fdDetailList_Form["+currentIndex+"].fdInputTaxRate']").val(rate.fdTaxRate);
					}else{
						fdNoTaxMoney = valueJson.fdInvoiceMoney*1;
					}
					$("[name='fdDetailList_Form["+currentIndex+"].fdNoTaxMoney']").val(fdNoTaxMoney);//不含税金额
					$("[name='fdDetailList_Form["+currentIndex+"].fdInputTaxMoney']").val(subPoint(valueJson.fdInvoiceMoney*1,fdNoTaxMoney));
				}
			}
			FSSC_ChangeMoney(null,$("[name='fdDetailList_Form["+currentIndex+"].fdApplyMoney']").get(0),currentIndex);
			FSSC_MatchBudget(null,$("[name='fdDetailList_Form["+currentIndex+"].fdApplyMoney']").get(0));//预算匹配
			FSSC_MathStandard(currentIndex);  //标准匹配
			var number=new Map(),n=0;
			$($("#TABLE_DocList_fdInvoiceList_Form [name$=fdInvoiceNumber]")).each(function(){
					var type=$("input[name='fdInvoiceList_Form["+n+"].fdInvoiceType']").val();
					var code=$("input[name='fdInvoiceList_Form["+n+"].fdInvoiceCode']").val();
					number.set(type+$(this).val()+code,n);
					n++;
				}
			)
			//当前发费用明细发票信息更新，新的发票则新增发票信息
			data=data.params;
			for(var i=0;i<data.length;i++){
				if(number.has(data[i].fdInvoiceType+data[i].fdInvoiceNumber+data[i].fdInvoiceCode)){
					var index=number.get(data[i].fdInvoiceType+data[i].fdInvoiceNumber+data[i].fdInvoiceCode);
					//更新现有发票信息
					$("[name='fdInvoiceList_Form["+index+"].fdInvoiceType']").val(data[i].fdInvoiceType);
					$("[name='_fdInvoiceList_Form["+index+"].fdInvoiceType']").val(data[i].fdInvoiceType);
					$("[name='fdInvoiceList_Form["+index+"].fdInvoiceCode']").val(data[i].fdInvoiceCode);
					$("[name='fdInvoiceList_Form["+index+"].fdInvoiceDate']").val(data[i].fdInvoiceDate);
					$("[name='fdInvoiceList_Form["+index+"].fdInvoiceMoney']").val(data[i].fdInvoiceMoney);
					$("[name='fdInvoiceList_Form["+index+"].fdTaxMoney']").val(data[i].fdTaxMoney);
					$("[name='fdInvoiceList_Form["+index+"].fdNoTaxMoney']").val(data[i].fdNoTaxMoney);
					$("[name='fdInvoiceList_Form["+index+"].fdCheckCode']").val(data[i].fdCheckCode);
					//如果是增值税专用发票
					if("10100"==data[i].fdInvoiceType||"true"==data[i].fdIsVat){
						$("[name='_fdInvoiceList_Form["+index+"].fdIsVat']").prop("checked",true);
						$("[name='fdInvoiceList_Form["+index+"].fdIsVat']").val(true);
						if("10100"==data[i].fdInvoiceType){
							FSSC_ChangeIsVat_target($("[name='fdInvoiceList_Form["+index+"].fdIsVat']").get(0),index);
						}
					}else{
						$("[name='_fdInvoiceList_Form["+index+"].fdIsVat']").prop("checked",false);
						$("[name='fdInvoiceList_Form["+index+"].fdIsVat']").val(false);
						FSSC_ChangeIsVat_target($("[name='fdInvoiceList_Form["+index+"].fdIsVat']").get(0),index);
					}
					$("[name='fdInvoiceList_Form["+index+"].fdExpenseTypeId']").val(data[i].fdExpenseItemId);
					$("[name='fdInvoiceList_Form["+index+"].fdExpenseTypeName']").val(data[i].fdExpenseItemName);
					$("[name='fdInvoiceList_Form["+index+"].fdTax']").val(data[i].fdTax);
					$("[name='fdInvoiceList_Form["+index+"].fdTax1']").val(data[i].fdTaxId);
					$("[name='fdInvoiceList_Form["+index+"].fdCheckStatus']").val(data[i].fdCheckStatus);
					$("[name='fdInvoiceList_Form["+index+"].fdState']").val(data[i].fdState);
					$("[name='fdInvoiceList_Form["+index+"].fdTaxNumber']").val(data[i].fdTaxNumber);
					$("[name='fdInvoiceList_Form["+index+"].fdPurchName']").val(data[i].fdPurchName);
					if(data[i].fdIsCurrent){
						$("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").val(data[i].fdIsCurrent);
						if(data[i].fdIsCurrent=="1"){  //是本公司发票
							$("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").parent().find("span").html(comlang["message.yes"]);
						}else if(data[i].fdIsCurrent=="0"){  //不是本公司发票
							$("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").parent().find("span").html(comlang["message.no"]);
						}
					}
				}else{
					//新增的发票
					var index = $("#TABLE_DocList_fdInvoiceList_Form [name$=fdInvoiceNumber]").length;
					DocList_AddRow("TABLE_DocList_fdInvoiceList_Form");
					FSSC_SetDefaultCompany();
					$("[name='fdInvoiceList_Form["+index+"].fdInvoiceType']").val(data[i].fdInvoiceType);
					$("[name='_fdInvoiceList_Form["+index+"].fdInvoiceType']").val(data[i].fdInvoiceType);
					$("[name='fdInvoiceList_Form["+index+"].fdInvoiceNumber']").val(data[i].fdInvoiceNumber);
					$("[name='fdInvoiceList_Form["+index+"].fdInvoiceCode']").val(data[i].fdInvoiceCode);
					$("[name='fdInvoiceList_Form["+index+"].fdInvoiceDate']").val(data[i].fdInvoiceDate);
					$("[name='fdInvoiceList_Form["+index+"].fdInvoiceMoney']").val(data[i].fdInvoiceMoney);
					$("[name='fdInvoiceList_Form["+index+"].fdTaxMoney']").val(data[i].fdTaxMoney);
					$("[name='fdInvoiceList_Form["+index+"].fdNoTaxMoney']").val(data[i].fdNoTaxMoney);
					$("[name='fdInvoiceList_Form["+index+"].fdCheckStatus']").val(data[i].fdCheckStatus);
					$("[name='fdInvoiceList_Form["+index+"].fdState']").val(data[i].fdState);
					$("[name='fdInvoiceList_Form["+index+"].fdCheckCode']").val(data[i].fdCheckCode);
					//如果是增值税发票
					if("10100"==data[i].fdInvoiceType||"true"==valueJson.fdIsVat){
						$("[name='_fdInvoiceList_Form["+index+"].fdIsVat']").prop("checked",true);
						$("[name='fdInvoiceList_Form["+index+"].fdIsVat']").val(true);
						if("10100"==data[i].fdInvoiceType){
							FSSC_ChangeIsVat_target($("[name='fdInvoiceList_Form["+index+"].fdIsVat']").get(0),index);
						}else{//非专票可抵扣
							FSSC_ChangeIsVat_target($("[name='fdInvoiceList_Form["+index+"].fdIsVat']").get(0),index);
						}
					}else{
						$("[name='_fdInvoiceList_Form["+index+"].fdIsVat']").prop("checked",false);
						$("[name='fdInvoiceList_Form["+index+"].fdIsVat']").val(false);
						FSSC_ChangeIsVat_target($("[name='fdInvoiceList_Form["+index+"].fdIsVat']").get(0),index);
					}
					$("[name='fdInvoiceList_Form["+index+"].fdExpenseTypeId']").val(data[i].fdExpenseItemId);
					$("[name='fdInvoiceList_Form["+index+"].fdExpenseTypeName']").val(data[i].fdExpenseItemName);
					$("[name='fdInvoiceList_Form["+index+"].fdTax']").val(data[i].fdTax);
					$("[name='fdInvoiceList_Form["+index+"].fdTax1']").val(data[i].fdTaxId);
					$("[name='fdInvoiceList_Form["+index+"].fdTaxNumber']").val(data[i].fdTaxNumber);
					$("[name='fdInvoiceList_Form["+index+"].fdPurchName']").val(data[i].fdPurchName);
					if(data[i].fdIsCurrent){
						$("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").val(data[i].fdIsCurrent);
						if(data[i].fdIsCurrent=="1"){  //是本公司发票
							$("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").parent().find("span").html(comlang["message.yes"]);
						}else if(data[i].fdIsCurrent=="0"){  //不是本公司发票
							$("[name='fdInvoiceList_Form["+index+"].fdIsCurrent']").parent().find("span").html(comlang["message.no"]);
						}
					}
				}
			}
			clearInvoiceDetail();  //清除页面不存在发票
	 }
	 
	 /*********************************************
	  * 修改不可抵扣金额，重新计算可抵扣金额和不含税金额
	  *********************************************/
	 window.FSSC_CalculateMoney=function(val,obj){
		 var index = obj.name.replace(/\S+\[(\d+)\]\S+/g,'$1');
		 var fdInputTaxRate=$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val();  //进项税率
		 var fdApplyMoney=$("[name='fdDetailList_Form["+index+"].fdApplyMoney']").val();  //申请金额
		 var isDeduct=$("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val();
		 var fdNonDeductMoney=$("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val();  //不可抵扣金额
		 if(!fdNonDeductMoney){
			 fdNonDeductMoney=0;
		 }
		 if(fdApplyMoney&&isDeduct=="true"){
			 fdNoTaxMoney = numDiv(numSub(fdApplyMoney,fdNonDeductMoney),numAdd(1,numDiv(fdInputTaxRate,100)));
		 	 fdNoTaxMoney = parseFloat(fdNoTaxMoney).toFixed(2);
		 	 var inputTaxMoney=parseFloat(numSub(numSub(fdApplyMoney*1,fdNonDeductMoney*1),fdNoTaxMoney*1)).toFixed(2);
		 	 $("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(inputTaxMoney);
		 	 var inputTaxMoney=$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val();
		 	 if(!inputTaxMoney||inputTaxMoney==0){
		 		 $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdApplyMoney);//不含税金额
		 	 }else{
		 		 $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdNoTaxMoney);//不含税金额 
		 	 }
		 }else{
			 $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdApplyMoney);//不含税金额
		 }
	 }
	 /*********************************************
	  * 修改进项税额，重新计算不含税金额
	  *********************************************/
	 window.FSSC_CalculateNoTaxMoney=function(val,obj){
		 var index = obj.name.replace(/\S+\[(\d+)\]\S+/g,'$1');
		 var fdApplyMoney=$("[name='fdDetailList_Form["+index+"].fdApplyMoney']").val();  //申请金额
		 var fdInputTaxMoney=$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val();  //进项税额
		 var fdNonDeductMoney=$("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val();  //不可抵扣金额
		 if(!fdNonDeductMoney){
			 fdNonDeductMoney=0;
		 }
		 if(fdApplyMoney&&fdInputTaxMoney){
			$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(numSub(numSub(fdApplyMoney,fdNonDeductMoney),fdInputTaxMoney));  //不含税金额
			var fdBudgetMoney = "";
			var fdDeduFlag=$("[name='fdDeduFlag']").val();
			var fdBudgetRate = $("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val();
			var fdNoTaxMoney = $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val();
			if(fdBudgetRate){
				if("2"==fdDeduFlag){  //不含税金额
					fdBudgetMoney = multiPoint(fdNoTaxMoney,fdBudgetRate);
				}else{
					fdBudgetMoney = multiPoint(fdApplyMoney,fdBudgetRate);
				}
			}
			$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(fdBudgetMoney);
			if("2"==fdDeduFlag){
				FSSC_MatchBudget(null,$("[name='fdDetailList_Form["+index+"].fdApplyMoney']").get(0));  //预算匹配
			}
		 }
	 }
	 //保存完发票，按发票号码+发票代码组合清空不存在的发票
	 window.clearInvoiceDetail=function(){
		var len=$("input[name$='.fdApplyMoney']").length,tempDetailIds='';
		for(var i=0;i<len;i++){
			var id=$("input[name='fdDetailList_Form["+i+"].fdExpenseTempDetailIds']").val();
			if(id){
				tempDetailIds+=id+';';
			}
		}
		var invoice_data = new KMSSData();
		invoice_data.UseCache=false;  //不使用缓存，实时查询，因为发票可以一直在修改
		var data = invoice_data.AddBeanData("fsscExpenseTempService&authCurrent=true&type=getInvoiceByTempDetailIds&fdExpenseMainId="+$("input[name='fdId']").val()+"&tempDetailIds="+tempDetailIds);
		var rtnData = data.GetHashMapArray();
		if(rtnData&&rtnData.length>0){
			var keys=[],indexs=[];
			for(var i=0;i<rtnData.length;i++){
				keys.push(rtnData[i]['key']);
			}
			var invoiceDetailLen=$("#TABLE_DocList_fdInvoiceList_Form").find("input[name$='fdInvoiceNumber']").length;
			for(var i=0;i<invoiceDetailLen;i++){
				var type=$("input[name='fdInvoiceList_Form["+i+"].fdInvoiceType']").val()?$("input[name='fdInvoiceList_Form["+i+"].fdInvoiceType']").val():'';
				var number=$("input[name='fdInvoiceList_Form["+i+"].fdInvoiceNumber']").val()?$("input[name='fdInvoiceList_Form["+i+"].fdInvoiceNumber']").val():'';
				var code=$("input[name='fdInvoiceList_Form["+i+"].fdInvoiceCode']").val()?$("input[name='fdInvoiceList_Form["+i+"].fdInvoiceCode']").val():'';
				if((type+number+code)&&keys.indexOf(type+number+code)==-1){
					indexs.push(i);
				}
			}
			var del_flag=false;  //明细删除标记，true为删了一行，下次循环的index需要-删除的行数
			var del=0;
			for(var n=0;n<indexs.length;n++){
				var index=indexs[n];
				if(del_flag){
					index=index-del;
				}
				var optTR=$("#TABLE_DocList_fdInvoiceList_Form").find("tr").not(".tr_normal_title")[index];
				DocList_DeleteRow(optTR);
				del_flag=true;
				del++;
			}
		}else{//无发票信息
			var len=$("#TABLE_DocList_fdInvoiceList_Form").find("tr").not(".tr_normal_title").length;
			var contentObj=$("#TABLE_DocList_fdInvoiceList_Form").find("tr").not(".tr_normal_title");
			for(var i=0;i<len;i++){
				DocList_DeleteRow(contentObj[i]);
			}
		}
	 }
	 //删除费用明细，级联清除发票明细发票
	 window.cascade_DeleteInvoice=function(){
		 var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		 var tempDetailIds=$("input[name='fdDetailList_Form["+index+"].fdExpenseTempDetailIds']").val();
		 if(tempDetailIds){
			var invoice_data = new KMSSData();
			invoice_data.UseCache=false;  //不使用缓存，实时查询，因为发票可以一直在修改
			var data = invoice_data.AddBeanData("fsscExpenseTempService&authCurrent=true&type=getInvoiceByTempDetailIds&tempDetailIds="+tempDetailIds);
			var rtnData = data.GetHashMapArray();
			if(rtnData&&rtnData.length>0){
				var keys=[],indexs=[];
				for(var i=0;i<rtnData.length;i++){
					keys.push(rtnData[i]['key']);
				}
				var invoiceDetailLen=$("#TABLE_DocList_fdInvoiceList_Form").find("input[name$='fdInvoiceNumber']").length;
				for(var i=0;i<invoiceDetailLen;i++){
					var type=$("input[name='fdInvoiceList_Form["+i+"].fdInvoiceType']").val()?$("input[name='fdInvoiceList_Form["+i+"].fdInvoiceType']").val():'';
					var number=$("input[name='fdInvoiceList_Form["+i+"].fdInvoiceNumber']").val()?$("input[name='fdInvoiceList_Form["+i+"].fdInvoiceNumber']").val():'';
					var code=$("input[name='fdInvoiceList_Form["+i+"].fdInvoiceCode']").val()?$("input[name='fdInvoiceList_Form["+i+"].fdInvoiceCode']").val():'';
					if((type+number+code)&&keys.indexOf(type+number+code)>-1){
						indexs.push(i);
					}
				}
				var del_flag=false;  //明细删除标记，true为删了一行，下次循环的index需要-删除的行数
				var del=0;
				for(var n=0;n<indexs.length;n++){
					var index=indexs[n];
					if(del_flag){
						index=index-del;
					}
					var optTR=$("#TABLE_DocList_fdInvoiceList_Form").find("tr").not(".tr_normal_title")[index];
					DocList_DeleteRow(optTR);
					del_flag=true;
					del++;
				}
			}
		 }
	 }
	/***************************************添加发票  end*******************************************/
	$(function(){
		window.FSSC_InitMonthSelector();
	})
	
	window.FSSC_ChangeIsDeductInit = function(index){
		var fdIsDeduct = $("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val();
		if(fdIsDeduct=='true'){
			$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxMoney']").show();
			$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxRate']").show();
			$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").attr("validate","required  currency-dollar");
		}else{
			$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxMoney']").hide();
			$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxRate']").hide();
			$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").attr("validate","currency-dollar");
		}
	}
	
	//导入费用明细后的回调，参数：datas-导入的数据，cols-导入的表格信息，firstIndex-导入起始行
	window.FSSC_DetailImported = function(datas,cols,firstIndex){
		var fdCompanyId = $("[name=fdCompanyId]").val();
		var len = $("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").length;
		for(var i=firstIndex;i<len;i++){
			$("[name='fdDetailList_Form["+i+"].fdCompanyId']").val(fdCompanyId);
			if($("[name=fdAllocType]").val()=='1'){//部门分摊
				$("[name='fdDetailList_Form["+i+"].fdCostCenterId']").val($("[name=fdCostCenterId]").val());
			}
			FSSC_InitCurrencyAndRate(i);
		}
		FSSC_CheckDetailImportData(datas,cols,firstIndex);
		if(window.buildSglObject){
			var len = $("#TABLE_DocList_fdDetailList_Form>tbody>tr").length;
			$("#TABLE_DocList_fdDetailList_Form>tbody>tr").eq(len-1).find("div.inputselectsgl").each(function(){
				window.buildSglObject.call(this);
			});
			$("#TABLE_DocList_fdDetailList_Form>tbody>tr").eq(len-1).find("div.inputselectmul").each(function(){
				window.buildMulObject.call(this);
			});
		}
	}
	
	window.FSSC_CheckDetailImportData = function(datas,cols,firstIndex){
		var fdExpenseType = $("[name=fdExpenseType]").val();
		var fdIsTravelAlone = $("[name=fdIsTravelAlone]").val();
		var fdExpenseType = $("[name=fdExpenseType]").val();
		var fdExtendField = ';'+$("[name=fdExtendField]").val()+';';
		//如果是差旅报销且行程合并，需要校验城市是否存在
		if(fdExpenseType=='2'&&fdIsTravelAlone=='false'){
			var scityArray = [],cmap= {};
			$("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt("+(firstIndex)+")").each(function(){
				var fdStartArea = $(this).find("[name$=fdStartPlace]").val();
				if(!cmap[fdStartArea]&&fdStartArea){
					scityArray.push(fdStartArea);
					cmap[fdStartArea] = true;
				}
				var fdEndArea = $(this).find("[name$=fdArrivalPlace]").val();
				if(!cmap[fdEndArea]&&fdEndArea){
					scityArray.push(fdEndArea);
					cmap[fdEndArea] = true;
				}
			});
			if(scityArray.length>0){
				$.post(
					Com_Parameter.ContextPath+'eop/basedata/eop_basedata_area/eopBasedataArea.do?method=checkCityExists',
					{city:scityArray.join(';'),fdCompanyId:$("[name=fdCompanyId]").val()},
					function(data){
						data = JSON.parse(data);
						for(var i=0;i<data.length;i++){
							if(!data[i].fdId){
								$("[name$=fdStartPlace][value="+data[i].fdCity+"]").val("");
								$("[name$=fdArrivalPlace][value="+data[i].fdCity+"]").val("");
							}else{
								// #129701 给出发地和到达地id赋值
								$("[name$=fdArrivalPlace][value="+data[i].fdCity+"]").each(function(){
									var tr = DocListFunc_GetParentByTagName("TR",this);
									$(tr).find("[name$=fdArrivalPlaceId]").val(data[i].fdId);
								})
								$("[name$=fdStartPlace][value="+data[i].fdCity+"]").each(function(){
									var tr = DocListFunc_GetParentByTagName("TR",this);
									$(tr).find("[name$=fdStartPlaceId]").val(data[i].fdId);
								})
							}
						}
					}
				);
			}
		}
		//如果有进项税率，需要校验导入的税率是否存在
		if(fdExtendField.indexOf(';2;')>-1){
			var rateArray = [],rmap= {};
			$("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt("+(firstIndex)+")").each(function(){
				var fdIsDeduct = $(this).find("[name$=fdIsDeduct][type=hidden]").val();
				if(fdIsDeduct!='true'){//不抵扣，清空导入的税率和税额
					$(this).find("[name$=fdInputTaxRate]").val("");
					$(this).find("[name$=fdInputTaxMoney]").val("");
					return;
				}
				var fdTaxRate  =  $(this).find("[name$=fdInputTaxRate]").val()*1;
				if(!rmap[fdTaxRate]){
					rateArray.push(fdTaxRate);
					rmap[fdTaxRate] = true;
				}
			})
			if(rateArray.length>0){
				$.post(
					Com_Parameter.ContextPath+'eop/basedata/eop_basedata_input_tax/eopBasedataInputTax.do?method=checkRateExists',
					{rate:rateArray.join(';'),fdCompanyId:$("[name=fdCompanyId]").val()},
					function(data){
						data = JSON.parse(data);
						for(var i=0;i<data.length;i++){
							if(!data[i].fdId){
								$("[name$=fdInputTaxRate][value="+data[i].fdRate+"]").val("");
								var tr = DocListFunc_GetParentByTagName("TR",this);
								$(tr).find("[name$=fdInputTaxMoney]").val('');
							}
						}
					}
				);
			}
		}
	};
	//#168279 行程独立删除行程，按顺序刷新行程值
	window.Fssc_DeleteTravelRow=function(dom){
		dialog.confirm(lang["tips.delete.travel.detail"],function(ok){
			if(ok==true){
				var name=$(dom).parent().parent().find("td").eq(2).find("input").eq(0).attr("name");
				var delIndex=name.substring(name.indexOf("[")+1,name.indexOf("]"));//被删除的行程行的索引
				var delSubject=lang['fsscExpenseTravelDetail.fdSubject']+(delIndex*1+1);//被删除的行程行的行程
				var len=$("#TABLE_DocList_fdTravelList_Form tr").length-1;
				//刷新行程明细显示
				for(var i=0;i<len;i++){
					if(i<delIndex){//被删除行之前的行，索引+1
						$("[name='fdTravelList_Form["+i+"].fdSubject']").val(lang['fsscExpenseTravelDetail.fdSubject']+(i+1));
						$("[name='fdTravelList_Form["+i+"].fdSubject']").parent().find("[id$='fdSubject']").html(lang['fsscExpenseTravelDetail.fdSubject']+(i+1));
					}else{//被删除行之前的行，直接赋值索引ii，因为是删除行之前执行，删除后续行数的索引会-1
						$("[name='fdTravelList_Form["+i+"].fdSubject']").val(lang['fsscExpenseTravelDetail.fdSubject']+i);
						$("[name='fdTravelList_Form["+i+"].fdSubject']").parent().find("[id$='fdSubject']").html(lang['fsscExpenseTravelDetail.fdSubject']+i);
					}
				}
				FSSC_RefreshTravelOption(); //刷新费用明细行程选项
				//将删除的行程相关联的费用明细的行程清空
				var len=$("#TABLE_DocList_fdDetailList_Form tr").length-1;
				for(var i=0;i<len;i++){
					var fdTravel=$("[name='fdDetailList_Form["+i+"].fdTravel']").val();
					if(delSubject==fdTravel){
						$("[name='fdDetailList_Form[0].fdTravel']").val(""); //所选行程为删除的行程，清空行程
					}
				}
				window.travelIndex =$("#TABLE_DocList_fdTravelList_Form tr").length-1;
				var TR=$(dom).parent();
				for(var i=0;i<5;i++){
					if($(TR).get(0).tagName=="TR"){
						break;
					}else{
						TR=$(TR).parent();
					}
				}
				DocList_DeleteRow(TR[0]);
			}
		});
	}
})
