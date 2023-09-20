seajs.use(['lui/dialog','lang!fssc-expense'],function(dialog,lang){
	//改变报销人时，清空相关信息
	window.clearInfoWhenCliamntChanged = function(rtn){
		$("[name=fdCompanyId]").val("");
		$("[name=fdCompanyName]").val("");
		$("[name=fdFeeIds]").val("");
		$("[name=fdFeeNames]").val("");
		if(window.buildMultiSelector){
			window.buildMultiSelector($("[name=fdFeeIds]").parent()[0]);
		}
		var fdPersonId = rtn[0].fdId||rtn[0];
		//带出默认公司
		var data = new KMSSData();
		data.AddBeanData("fsscExpenseDataService&type=getDefaultCompany&fdPersonId="+fdPersonId);
		data = data.GetHashMapArray();
		if(data.length>0){
			$("[name=fdCompanyId]").val(data[0].fdId);
			$("[name=fdCompanyName]").val(data[0].fdName);
		}
		clearDetailWhenCompanyChanged();
		FSSC_LoadLoanInfo();
		$("#TABLE_DocList_fdTranDataList_Form tr:gt(0)").remove();	//清空交易数据明细
	}
	//改变记账公司时,清空相关信息
	window.clearDetailWhenCompanyChanged = function(rtn){
		var fdCompanyId = $("[name=fdCompanyId]").val(),fdCompanyName = $("[name=fdCompanyName]").val(),fdCompanyIdOld = $("[name=fdCompanyIdOld]").val();
		if(fdCompanyId!=fdCompanyIdOld){
			dialog.confirm(lang['tips.switchCompany.clearInfo'],function(val){
				if(val){
					$("[name=fdCompanyIdOld]").val(fdCompanyId);
					$("[name=fdCompanyNameOld]").val(fdCompanyName);
					$("[name=fdCostCenterId]").val('');
					$("[name=fdCostCenterName]").val('');
					$("[name=fdProjectName]").val('');
					$("[name=fdProjectId]").val('');
					$("[name=fdProjectAccountingName]").val('');
					$("[name=fdProjectAccountingId]").val('');
					$("[name=fdProappName]").val('');
					$("[name=fdProappId]").val('');
					//重新带出默认币种
					var fdCurrencyId = '',fdCurrencyName='',fdRate = '',fdBudgetRate='';
					data = new KMSSData();
					data.AddBeanData("eopBasedataCompanyService&type=getStandardCurrencyInfo&authCurrency=true&fdCompanyId="+fdCompanyId);
					data = data.GetHashMapArray();
					if(data.length>0){
						fdCurrencyId=data[0].fdCurrencyId;
						fdCurrencyName = data[0].fdCurrencyName;
						fdRate = data[0].fdExchangeRate;
						fdBudgetRate=data[0].fdBudgetRate;
						$("[name='fdDeduFlag']").val(data[0].fdDeduRule);
					}
					//清空行程明细
					$("#TABLE_DocList_fdTravelList_Form tr:gt(0)").each(function(){
						$(this).find("[name$=fdCompanyId]").val(fdCompanyId);
						$(this).find("[name$=fdStartPlace]").val("");
						$(this).find("[name$=fdArrivalPlace]").val("");
						$(this).find("[name$=fdArrivalPlace1]").val("");
						$(this).find("[name$=fdVehicleId]").val("");
						$(this).find("[name$=fdVehicleName]").val("");
						$(this).find("input[name*=fdBerth]").val('');
					});
					//清空费用明细
					$("#TABLE_DocList_fdDetailList_Form tr:gt(0)").each(function(){
						$(this).find("[name$=fdCompanyId]").val(fdCompanyId);
						$(this).find("input[name*=fdCostCenter]").val("");
						$(this).find("input[name*=fdExpenseItem]").val("");
						$(this).find("[name$=fdCurrencyId]").val(fdCurrencyId);
						$(this).find("[name$=fdCurrencyName]").val(fdCurrencyName);
						$(this).find("[name$=fdExchangeRate]").val(fdRate);
						$(this).find("[name$=fdProjectId]").val("");
						$(this).find("[name$=fdProjectName]").val("");
						$(this).find("[name$=fdWbsId]").val("");
						$(this).find("[name$=fdWbsName]").val("");
						$(this).find("[name$=fdInnerOrderId]").val("");
						$(this).find("[name$=fdInnerOrderName]").val("");
						var tips="";
						if(!fdRate){
							tips=lang['tips.exchange.rate'];  //报销币种和本位币汇率未配置
						}
						$(this).find("[name$=fdBudgetRate]").val(fdBudgetRate);
						if(!fdBudgetRate){
							tips+='，'+lang['tips.budget.rate'];  //报销币种和预算币汇率未配置
						}
						if(tips){
							var tip=lang['tips.exchangeRate.not.exist'].replace('{0}',tips);
							dialog.alert(tip);
						}
						var fdDeduFlag=$("[name='fdDeduFlag']").val();
						var fdApplyMoney=$(this).find("[name$=fdApplyMoney]").val();
						var fdNoTaxMoney = $(this).find("[name$=fdNoTaxMoney]").val();
						var fdBudgetMoney="";
						if(fdBudgetRate){
							if("2"==fdDeduFlag&&fdNoTaxMoney){  //不含税金额
								fdBudgetMoney = multiPoint(fdNoTaxMoney,fdBudgetRate);
							}else if(fdApplyMoney){
								fdBudgetMoney = multiPoint(fdApplyMoney,fdBudgetRate);
							}
						}
						$(this).find("[name$=fdBudgetMoney]").val(fdBudgetMoney);
						$(this).find("[name$=fdStandardMoney]").val($(this).find("[name$=fdApplyMoney]").val());
						$(this).find("[name$=fdBudgetStatus]").val("0");
						$(this).find("[name$=fdBudgetInfo]").val("");
					});
					//清空发票明细
					$("#TABLE_DocList_fdInvoiceList_Form tr:gt(0)").each(function(){
						$(this).find("[name$=fdCompanyId]").val(fdCompanyId);
						$(this).find("[name$=fdExpenseTypeId]").val("");
						$(this).find("[name$=fdExpenseTypeName]").val("");
						$(this).find("[name$=fdTax]").val("");
						$(this).find("[name$=fdTaxMoney]").val("");
						$(this).find("[name$=fdNoTaxMoney]").val("");
					});
					//清空收款明细
					$("#TABLE_DocList_fdAccountsList_Form tr:gt(0)").each(function(){
						$(this).find("input[name*=fdPayWay]").val("");
						$(this).find("input[name*=fdPayBank]").val("");
						$(this).find("input[name*=fdAccount]").val("");
						$(this).find("input[name*=fdBank]").val("");
						$(this).find("[name$=fdCurrencyId]").val(fdCurrencyId);
						$(this).find("[name$=fdCurrencyName]").val(fdCurrencyName);
						$(this).find("[name$=fdExchangeRate]").val(fdRate);
					});
					window.FSSC_ReloadCostCenter();
					window.FSSC_ReloadPayway();  //加载默认的付款方式
					window.FSSC_ReloadAccount();  //加载报销人的付款账号信息
					window.FSSC_LoadLoanInfo()//加载报销人和记账公司的借款信息
				}else{
					$("[name=fdCompanyId]").val(fdCompanyIdOld);
					$("[name=fdCompanyName]").val($("[name=fdCompanyNameOld]").val());
					window.FSSC_ReloadCostCenter()
				}
			})
		}else{
			window.FSSC_ReloadCostCenter();
			window.FSSC_ReloadAccount();  //加载报销人的付款账号信息
		}
	}
	//加载默认的付款方式
	window.FSSC_ReloadPayway = function(){
		var fdCompanyId = $("[name=fdCompanyId]").val();
		var data = new KMSSData();
		data.AddBeanData("eopBasedataPayWayService&type=default&fdCompanyId="+fdCompanyId);
		data = data.GetHashMapArray();
		if(data.length>0){
			$("#TABLE_DocList_fdAccountsList_Form tr").each(function(){
				$(this).find("input[name$=fdPayWayId]").val(data[0].value);
				$(this).find("input[name$=fdPayWayName]").val(data[0].text);
				$(this).find("input[name$=fdBankId]").val(data[0].fdBankId);
				$(this).find("input[name$=fdIsTransfer]").val(data[0].fdIsTransfer);
			});
		}
		var len = $("#TABLE_DocList_fdAccountsList_Form tr").length-1;
		for(var i=0;i<len;i++){
			var fdIsTransfer = $("[name='fdAccountsList_Form["+i+"].fdIsTransfer']").val();
			initFS_GetFdIsTransfer(i,fdIsTransfer);//初始化收款账户信息是否必填
		}
	}
	
	function initFS_GetFdIsTransfer(index, fdIsTransfer){
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
	
	//加载报销人的付款账号信息
	window.FSSC_ReloadAccount = function(){
		var data = new KMSSData();
		data.AddBeanData("eopBasedataAccountService&type=default&fdPersonId="+$("[name=fdClaimantId]").val());
		data = data.GetHashMapArray();
		if(data.length>0){
			$("#TABLE_DocList_fdAccountsList_Form tr").each(function(){
				$(this).find("input[name$=fdAccountId]").val(data[0].fdAccountId);
				$(this).find("input[name$=fdAccountName]").val(data[0].fdAccountName);
				$(this).find("input[name$=fdBankName]").val(data[0].fdBankName);
				$(this).find("input[name$=fdBankAccount]").val(data[0].fdBankAccount);
				$(this).find("input[name$=fdAccountAreaName]").val(data[0].fdAccountAreaName);
				$(this).find("input[name$=fdBankAccountNo]").val(data[0].fdBankAccountNo);
			});
		}
	}
	window.FSSC_ReloadCostCenter = function(){
		$("[name=fdCostCenterId]").val('');
		$("[name=fdCostCenterName]").val('');
		var fdCompanyId = $("[name=fdCompanyId]").val();
		//重新带出成本中心
		var data = new KMSSData();
		data.useCache = false;
		data.AddBeanData("fsscExpenseDataService&type=getDefaultCostCenter&fdCompanyId="+fdCompanyId+"&fdPersonId="+$("[name=fdClaimantId]").val());
		data = data.GetHashMapArray();
		if(data.length>0){
			$("[name=fdCostCenterId]").val(data[0].fdId);
			$("[name=fdCostCenterName]").val(data[0].fdName);
			changeCostCenter(data[0].fdId,data[0].fdName);
			$("#TABLE_DocList_fdDetailList_Form tr").each(function(){
				$(this).find("input[name$=fdCostCenterId]").val(data[0].fdId);
				$(this).find("input[name$=fdCostCenterName]").val(data[0].fdName);
			});
		}
	}
	//主表中的成本中心发生变化后
	window.FSSC_MainCostCenterChanged = function(rtn){
		var fdAllocType = $("[name=fdAllocType]").val();
		//日常报销，同步改变明细中的成本中心
		if(fdAllocType=='1'){
			$("[name$='].fdCostCenterId']").val(rtn[0].fdId);
		}
	}
	//单行明细匹配预算
	window.FSSC_MatchBudget = function(v,e){
		var i = e.name.replace(/\S+\[(\d+)\]\S+/g,'$1');
		var fdMoney=$("[name='fdDetailList_Form["+i+"].fdApplyMoney']").val();
		var fdNoTaxMoney=$("[name='fdDetailList_Form["+i+"].fdNoTaxMoney']").val();
		if(!fdNoTaxMoney||fdNoTaxMoney==0){
			fdNoTaxMoney=fdMoney;
		}
		var param = {
			'fdCompanyId':$("[name='fdDetailList_Form["+i+"].fdCompanyId']").val(),
			'fdProjectId':$("[name='fdDetailList_Form["+i+"].fdProjectId']").val(),
			'fdWbsId':$("[name='fdDetailList_Form["+i+"].fdWbsId']").val(),
			'fdInnerOrderId':$("[name='fdDetailList_Form["+i+"].fdInnerOrderId']").val(),
			'fdExpenseItemId':$("[name='fdDetailList_Form["+i+"].fdExpenseItemId']").val(),
			'fdCostCenterId':$("[name='fdDetailList_Form["+i+"].fdCostCenterId']").val(),
			'fdPersonId':$("[name='fdDetailList_Form["+i+"].fdRealUserId']").val(),
			'fdDeptId':$("[name='fdDetailList_Form["+i+"].fdDeptId']").val(),
			'fdHappenDate':$("[name='fdDetailList_Form["+i+"].fdHappenDate']").val(),
			'fdMoney':fdMoney,
			'fdNoTaxMoney':fdNoTaxMoney,
			'fdCurrencyId':$("[name='fdDetailList_Form["+i+"].fdCurrencyId']").val(),
			'fdDetailId':i,
			'fdModelId':$("[name=fdId]").val()
		}
		if(!param.fdCompanyId){
			return;
		}
		var fdProappId = $("[name=fdProappId]").val(),pass = true;
		//如果有立项
		if(fdProappId){
			$.ajax({
				url:Com_Parameter.ContextPath+'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=matchProapp',
				data:{data:JSON.stringify({data:[param],fdProappId:fdProappId})},
				async:false,
				success:function(rtn){
					if(!rtn){
						return;
					}
					rtn = JSON.parse(rtn);
					//匹配失败
					if(rtn.result=='failure'){
						dialog.alert(rtn.message?rtn.message:rtn.budget.errorMessage);
						return;
					}
					var fdProappStatus = "0";
					//如果找到了立项
					FSSC_ShowProappInfo(rtn[i],i,param);
				}
			});
			$("[name$='["+i+"].fdBudgetInfo']").val('[]');
			$("[name$='["+i+"].fdBudgetStatus']").val('0');
			FSSC_ShowBudgetInfo(i);
			//立项默认刚控，无论有没有匹配到、有没有超，都不再匹配预算
			return;
		}
		var fdFeeIds = $("[name=fdFeeIds]").val(),pass = true,fdIsNeedBudget = true;
		//如果有事前
		if(fdFeeIds){
			$.ajax({
					url:Com_Parameter.ContextPath+'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=matchFee',
					data:{data:JSON.stringify({data:[param],fdFeeIds:fdFeeIds})},
					async:false,
					success:function(rtn){
						if(!rtn){
							return;
						}
						rtn = JSON.parse(rtn);
						//匹配失败
						if(rtn.result=='failure'){
							dialog.alert(rtn.message?rtn.message:rtn.budget.errorMessage);
							return;
						}
						//如果找到了事前
						if(rtn[i]&&rtn[i].length>0){
							var fdFeeInfoDetail = FSSC_ShowFeeInfo(i,rtn[i]);
							var fdFeeStatus = fdFeeInfoDetail.fdFeeStatus;
							fdIsNeedBudget = fdFeeInfoDetail.fdIsNeedBudget;
							//如果金额超过事前的可用额度且事前为刚控，则提示用户不可以报销
							if(fdFeeStatus=='2'&&rtn['fdForbid']=='1'){
								dialog.alert(lang['tips.fee.over']);
								pass = false;
							}
							//显示红灯及超申请提示
							$("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").find("[id^=fee_status]").eq(i).attr("class","budget_container budget_status_"+fdFeeStatus);
							$("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").find("[id^=fee_status]").eq(i).attr("title",lang['py.fee.'+fdFeeStatus]);
						}else{
							$("[name$='["+i+"].fdFeeInfo']").val('[]');
							$("[name$='["+i+"].fdFeeStatus']").val('0');
							$("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").find("[id^=fee_status]").eq(i).attr("class","budget_container budget_status_0");
							$("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").find("[id^=fee_status]").eq(i).attr("title",lang['py.fee.0']);
						}
					}
			});
		}else{
			$("[name$='["+i+"].fdFeeInfo']").val('[]');
			$("[name$='["+i+"].fdFeeStatus']").val('0');
			$("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").find("[id^=fee_status]").eq(i).attr("class","budget_container budget_status_0");
			$("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").find("[id^=fee_status]").eq(i).attr("title",lang['py.fee.0']);
		}
		if(!fdIsNeedBudget||!pass){//如果不需要匹配预算或者超事前且刚控
			$("[name$='["+i+"].fdBudgetInfo']").val('[]');
			$("[name$='["+i+"].fdBudgetStatus']").val('0');
			FSSC_ShowBudgetInfo(i);
			return;
		}
		$.post(
			Com_Parameter.ContextPath+'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=matchBudget',
			{data:JSON.stringify(param)},
			function(rtn){
				if(!rtn){
					return;
				}
				rtn = JSON.parse(rtn);
				//匹配失败
				if(rtn.result=='failure'||rtn.budget.result=='0'){
					dialog.alert(rtn.message?rtn.message:rtn.budget.errorMessage);
					return;
				}
				//未匹配到预算
				if(!rtn.budget.data||rtn.budget.data.length==0){
					FSSC_ShowBudgetInfo(i);
					return;
				}
				FSSC_ShowBudgetInfo(i,rtn.budget.data);
			}
		);
		
	}
	window.isInProappControlField = function(property,fdControlField){
		fdControlField = fdControlField.split(";");
		for(var i=0;i<fdControlField.length;i++){
			if(property.toLowerCase().indexOf(fdControlField[i])>-1){
				return true;
			}
		}
		return false;
	}
	window.FSSC_ShowProappInfo = function(data,i,param){
		var pass = true;
		var fdBudgetShowType = $("[name=fdBudgetShowType]").val();
		var div = $("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").eq(i).find("[id^=proapp_status]");
		if(data){
			fdProappStatus = "1";
			var fdMoney = data.fdUsableMoney;
			var fdUsableMoney = data.fdUsableMoney;
			var docStatus=$("input[name='docStatus']").val();
			$("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").each(function(k){
				if(k>i)return;
				var object = {
					fdExpenseItemId : $(this).find("[name$=fdExpenseItemId]").val(),
					fdCostCenterId : $(this).find("[name$=fdCostCenterId]").val(),
					fdProjectId : $(this).find("[name$=fdProjectId]").val(),
					fdWbsId : $(this).find("[name$=fdWbsId]").val(),
					fdInnerOrderId : $(this).find("[name$=fdInnerOrderId]").val()
				}
				for(var v in object){
					if(object[v]!=param[v]&&(isInProappControlField(v,data.fdControlField)||v=='fdExpenseItemId')){
						return;
					}
				}
				//期间处理，如果立项受益期不是不限，则不同期间的不视为重复
				var fdHappenDate = $(this).find("[name$=fdHappenDate]").val();
				if(data.fdPeriodType=='year'){//受益期类型为年,判断年是否相同
					fdHappenDate = fdHappenDate.substring(0,4);
					var year = param.fdHappenDate.substring(0,4);
					if(year!=fdHappenDate){
						return;
					}
				}else if(data.fdPeriodType=='yearMonth'){//受益期类型为年月,判断年月是否相同
					fdHappenDate = fdHappenDate.substring(0,7);
					var year = param.fdHappenDate.substring(0,7);
					if(year!=fdHappenDate){
						return;
					}
				}
				//待审编辑主文档，判断预算需加回已经占用的预算
				var fdBudgetMoneyOld=$(this).find("[name$=fdBudgetMoneyOld]").val();
				if(!fdBudgetMoneyOld){
					fdBudgetMoneyOld=0;
				}
				if(docStatus=='20'){
					fdMoney = numSub(numAdd(fdMoney,fdBudgetMoneyOld),$(this).find("[name$=fdBudgetMoney]").val()*1);
				}else{
					fdMoney = numSub(fdMoney,$(this).find("[name$=fdBudgetMoney]").val()*1);
				}
			})
			if(fdMoney<0){
				fdProappStatus = "2";
				pass = false;
			}
			if(data.empty){
				fdProappStatus = "0";
				pass = false;
			}
			$("[name$='["+i+"].fdProappInfo']").val(JSON.stringify(data).replace(/\"/g,"'"));
			$("[name$='["+i+"].fdProappStatus']").val(fdProappStatus);
			div.attr("class","budget_container");
			div.addClass("budget_status_"+fdProappStatus);
			div.attr("title",lang['py.proapp.'+fdProappStatus]);
		}else{
			div.attr("class","budget_container");
			div.addClass("budget_status_0");
			div.attr("title",lang['py.proapp.0']);
			$("[name$='["+i+"].fdProappInfo']").val('{}');
			$("[name$='["+i+"].fdProappStatus']").val('0');
		}
		return pass;
	}
	//计算明细占用事前台账的金额及状态，并保存到明细，返回事前的状态及需要占用预算的金额
	window.FSSC_ShowFeeInfo = function(index,info){
		//如果没有事前信息，则不需要计算
		if(!info||info.length==0){
			$("[name$='["+index+"].fdFeeInfo']").val('[]');
			$("[name$='["+index+"].fdFeeStatus']").val('0')
			return {fdFeeStatus:'0',fdIsNeedBudget:true};
		}
		var fdFeeStatus = "1";
		var fdBudgetMoney = $("[name$='["+index+"].fdBudgetMoney']").val()*1;//需要占用事前的金额
		var total = fdBudgetMoney;//需要占用预算的金额
		//需要查找前面已经占用的金额，以计算可以占用金额
		for(var m=0;m<info.length;m++){
			if(fdBudgetMoney<=0){//如果本明细已经占完了，直接结束
				break;
			}
			var fdMoney = info[m].fdUsableMoney//当前台账的可用金额，不包含当前单据的占用金额
			for(var i=0;i<index;i++){
				var fee = $("[name$='["+i+"].fdFeeInfo']").val()||'[]';
				fee = JSON.parse(fee.replace(/\'/g,"\""));
				for(var k=0;k<fee.length;k++){
					if(fee[k].fdLedgerId==info[m].fdLedgerId){//如果是同一条事前台账，则扣除这一条明细占用的金额
						fdMoney = numSub(fdMoney,fee[k].fdOffsetMoney||0);
					}
				}
			}
			if(fdMoney>0){//如果前面所有明细占完了还有余额，则当前明细继续占用
				if(fdMoney>fdBudgetMoney){//如果事前金额大于明细金额，则直接占用明细的
					info[m].fdOffsetMoney = fdBudgetMoney;
					fdBudgetMoney = 0;
					if(info[m].fdIsUseBudget){//如果当前事前台账占用了预算，则报销不再需要占用预算
						total = 0;
					}
				}else if(fdMoney<fdBudgetMoney){//如果事前金额小于明细金额，则直接占用事前的所有金额
					info[m].fdOffsetMoney = fdMoney;
					fdBudgetMoney = numSub(fdBudgetMoney,fdMoney);
					if(info[m].fdIsUseBudget){//如果当前事前台账占用了预算，则报销不再需要占用预算
						total = numSub(total,fdMoney);
					}
				}else{//如果相等，那么台账全占用了，明细金额也占完了
					info[m].fdOffsetMoney = fdBudgetMoney;
					fdBudgetMoney = 0;
					if(info[m].fdIsUseBudget){//如果当前事前台账占用了预算，则报销不再需要占用预算
						total = 0;
					}
				}
			}
		}
		if(fdBudgetMoney>0){//如果所有事前台账都已经占用完了，明细金额还有剩余，说明超了事前
			fdFeeStatus = "2";
		}
		//保存事前信息及状态
		$("[name$='["+index+"].fdFeeInfo']").val(JSON.stringify(info).replace(/\"/g,"'"));
		$("[name$='["+index+"].fdFeeStatus']").val(fdFeeStatus)
		return {fdFeeStatus:fdFeeStatus,fdIsNeedBudget:total>0};
	}
	//显示预算状态信息
	window.FSSC_ShowBudgetInfo = function(index,info){
		var fdBudgetShowType = $("[name=fdBudgetShowType]").val();
		var budgetObj = $("[name$='budgetObj']").val()||'[]';
		var budgetOldObj = JSON.parse((budgetObj).replace(/\'/g,'"'));
		$("[name$='["+index+"].fdBudgetInfo']").val("");
		var div = $("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").eq(index).find("[id^=buget_status]");
		//没有预算信息,显示为无预算
		if(!info){
			$("[name$='["+index+"].fdBudgetStatus']").val("0");
			if(fdBudgetShowType=='1'){//显示图标
				div.attr("class","budget_container");
				div.addClass("budget_status_0");
				div.attr("title",lang['py.budget.0']);
			}else{//显示金额
				div.html(lang['py.money.total']+"0<br>"+lang['py.money.using']+"0<br>"+lang['py.money.used']+"0<br>"+lang['py.money.usable']+"0");
			}
		}else{
			var budgetInfo = {},fdBudgetStatus = '1';
			var fdBudgetMoney = $("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val();
			$("[name$='["+index+"].fdBudgetInfo']").val(JSON.stringify(info).replace(/\"/g,"'"));
			//迭代所有明细的预算信息，处理多条明细匹配到同一条预算的情况
			$("#TABLE_DocList_fdDetailList_Form tr:gt(0)").find("[name$=fdBudgetInfo]").each(function(){
				var budget = JSON.parse((this.value||'[]').replace(/\'/g,'"'));
				var k = this.name.replace(/\S+\[(\d+)\]\S+/g,'$1');
				var money = $("[name='fdDetailList_Form["+k+"].fdBudgetMoney']").val()*1;
				var docStatus=$("input[name='docStatus']").val();
				//待审编辑主文档，判断预算需加回已经占用的预算
				var detail_id=$("[name='fdDetailList_Form["+k+"].fdId']").val();
				var money_old=budgetOldObj[detail_id];
				if(!money_old||docStatus<'20'){
					money_old=0;
				}
				for(var i=0;i<budget.length;i++){
					//如果累计的明细在当前明细之后，不统计在内
					if(k>index){
						continue;
					}
					if(!budgetInfo[budget[i].fdBudgetId]){
						budgetInfo[budget[i].fdBudgetId] = {money:money,index:[k],money_old:money_old};
					}else{
						budgetInfo[budget[i].fdBudgetId].money = numAdd(budgetInfo[budget[i].fdBudgetId].money,money);
						budgetInfo[budget[i].fdBudgetId].money_old = numAdd(budgetInfo[budget[i].fdBudgetId].money_old,money_old);
						budgetInfo[budget[i].fdBudgetId].index.push(k);
					}
				}
			});
			var showBudget = null,overBudget = [];
			for(var i=0;i<info.length;i++){
				//弹性控制，需要计算弹性比例
				if(info[i].fdRule=='3'){
					info[i].fdElasticMoney = numDiv(numMulti(info[i].fdTotalAmount,info[i].fdElasticPercent),100);
				}else{
					info[i].fdElasticMoney = 0;
				}
				//获取可用金额最少的预算用于展示
				if(!showBudget||showBudget.fdCanUseAmount>info[i].fdCanUseAmount){
					showBudget = info[i];
				}
				if(budgetInfo[info[i].fdBudgetId]!=null){
					//累计事前占用额
					var fee = $("[name$='["+budgetInfo[info[i].fdBudgetId].index[0]+"].fdFeeInfo']").val()||'[]';
					fee = JSON.parse(fee.replace(/\'/g,'"'));
					//扣除事前可用额
					for(var m=0;m<fee.length;m++){
						//如果该事前未占用预算，不统计在内
						if(!fee[0].fdIsUseBudget){
							continue;
						}
						budgetInfo[info[i].fdBudgetId].money = numSub(budgetInfo[info[i].fdBudgetId].money,fee[m].fdUsableMoney);
					}
					//超出预算
					if(budgetInfo[info[i].fdBudgetId].money>numAdd(info[i].fdCanUseAmount,budgetInfo[info[i].fdBudgetId].money_old)){
						fdBudgetStatus = '2';
					}
				}
			}
			$("[name$='["+index+"].fdBudgetStatus']").val(fdBudgetStatus);
			if(fdBudgetShowType=='1'){//显示图标
				div.attr("class","budget_container");
				div.addClass("budget_status_"+fdBudgetStatus);
				div.attr("title",lang['py.budget.'+fdBudgetStatus]);
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
				div.html(lang['py.money.total']+showBudget.fdTotalAmount+"<br>"+lang['py.money.using']+showBudget.fdOccupyAmount+"<br>"+lang['py.money.used']+showBudget.fdAlreadyUsedAmount+"<br>"+lang['py.money.usable']+"<span class='budget_money_"+index+"'>"+(showBudget.canUseAmountDisplay?showBudget.canUseAmountDisplay:showBudget.fdCanUseAmount)+"</span>");
				$(".budget_money_"+index).css("color",fdBudgetStatus=='2'?"red":"#333");
			}
	}
	}
	
	//改变是否摊销选项
	window.FSSC_ChangeIsAmortize = function(v,e){
		var fdIsAmortize = $("[name=fdIsAmortize]:checked").val();
		if(fdIsAmortize=='true'){
			$("#AmortizeInfo").show();
			//$("[name=fdAmortizeMonth]").val(0);
			$("[name=fdAmortizeMoney]").val($("[name=fdTotalStandaryMoney]").val());
		}else{
			$("#AmortizeInfo").hide();
			$("[name=fdAmortizeBegin]").html("");
			$("[name=fdAmortizeEnd]").html("");
			$("[name=fdAmortizeMonth]").val(0);
			//清空摊销明细
			var tr = $("#TABLE_DocList_Amortz tr");
			for(var i=1;i<tr.length;i++){
				DocList_DeleteRow(tr[i]);
			}
		}
	}
	//页面加载时自动显示或隐藏摊销信息
	//Com_AddEventListener(window,'load',FSSC_ChangeIsAmortize);
	Com_AddEventListener(window,'load',function(){
		if(!$("[name=checkVersion]").val()){
			return;
		}
		var len = $("#TABLE_DocList_fdDetailList_Form tr").length-1;
		for(var i=0;i<len;i++){
			FSSC_ShowStandardInfo(i);
		}
	})
	//显示标准信息
	window.FSSC_ShowStandardInfo=function(index){
		var fdStandardStatus = $("[name='fdDetailList_Form["+index+"].fdStandardStatus']").val()||0;
			$("#standard_status_"+index).attr("class","budget_container");
			$("#standard_status_"+index).addClass("standard_status_"+fdStandardStatus);
			//$("#standard_status_"+index).attr("title",lang['py.standard.'+fdStandardStatus]);
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
	   	 	  	}).mouseout(function(){
	   	 	  		layui.layer.closeAll("tips");
	   	 	  	})
	   		})
	}
	
	window.FSSC_AfterFeeSelected = function(data){
		if(data&&data.length>0){
			var ids = [],names=[];
			for(var i=0;i<data.length;i++){
				ids.push(data[i].fdId);
				names.push(data[i].docSubject);
			}
			$("[name=fdFeeIds]").val(ids.join(";"))
			$("[name=fdFeeNames]").val(names.join(";"))
		}
	}
	//切换项目清空wbs
	window.afterSelectProject = function(rtn){
		if(rtn){
			$("#TABLE_DocList_fdDetailList_Form tr:gt(0)").each(function(){
				$(this).find("[name$=fdWbsId]").val("");
				$(this).find("[name$=fdWbsName]").val("");
				$(this).find("[name$=fdProjectId]").val(rtn[0].fdId);
			});
		}
	}
	//校验所选择的事前是否存在在途单据
	window.checkFeeRelation=function(v,o){
		if(v){
			var fdFeeIds=$("[name='fdFeeIds']").val();
			var fdId=$("[name='fdId']").val();
			if(fdFeeIds){
					$.ajax({
					url:Com_Parameter.ContextPath + 'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=checkFeeRelation&fdFeeIds='+fdFeeIds+'&fdId='+fdId,
					async:false,
					success:function(rtn){
						rtn = JSON.parse(rtn);
						if(!rtn.result){//不允许关闭
							dialog.alert(lang['msg.fee.tips.examineing']);
						}
					}
				});
			}
		}
	};
	window.reCalBudgetMoney=function(index){
		 var fdBudgetMoney = "";
		 var fdBudgetRate = $("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val();
		 var fdApplyMoney = $("input[name='fdDetailList_Form["+index+"].fdApprovedApplyMoney']").val();
		 var fdNoTaxMoney = $("input[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val();
		 var fdDeduFlag=$("[name='fdDeduFlag']").val();
		 if(fdBudgetRate){
			if("2"==fdDeduFlag&&fdNoTaxMoney){  // 不含税金额
				fdBudgetMoney = multiPoint(fdNoTaxMoney,fdBudgetRate);
			}else{
				fdBudgetMoney = multiPoint(fdApplyMoney,fdBudgetRate);
			}
		 }
		 $("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(fdBudgetMoney);
	 }
	window.FSSC_AfterProappChanged = function(){//选择完立项，清除预算及事前信息
		$("[name*=fdBudgetInfo]").val("[]");
		$("[name*=fdBudgetStatus]").val("0");
		$("[name*=fdFeeInfo]").val("[]");
		$("[name*=fdFeeStatus]").val("0");
	}
	window.selectCostCenterMain = function(){
		var fdCompanyId = $("[name=fdCompanyId]").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return;
		}
		dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdCostCenterId','fdCostCenterName',null,{'fdCompanyId':$('[name=fdCompanyId]').val()},FSSC_MainCostCenterChanged);
	}
	window.selectProjectMain = function(){
		var fdCompanyId = $("[name=fdCompanyId]").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return;
		}
		var index = $("#TABLE_DocList_fdDetailList_Form > tbody > tr").length-2;
		dialogSelect(false,'eop_basedata_project_project','fdProjectId','fdProjectName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'fdProjectType':1},function(rtn){
			if(rtn){
				$("#TABLE_DocList_fdDetailList_Form tr:gt(0)").each(function(){
					$(this).find("[name$=fdWbsId]").val("");
					$(this).find("[name$=fdWbsName]").val("");
					$(this).find("[name$=fdProjectId]").val(rtn[0].fdId);
				});
			}
			FSSC_MatchBudget(null,$("[name='fdDetailList_Form["+index+"].fdApplyMoney']").get(0));
			FSSC_MatchProvsion(index);
		});
	}
	
	window.selectCostProject = function(){
		var fdCompanyId = $("[name=fdCompanyId]").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return;
		}
		dialogSelect(false,'eop_basedata_project_project','fdProjectAccountingId','fdProjectAccountingName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'fdProjectType':2});
	}
	
	//==========ljc加的方法
	window.getParentsName=function(fdId){
		var fdParentsName=""
			if(fdId&&fdId!=''){
				//如果改变了单位那么需要显示全名称
				 fdParentsName=  $.ajax({
                     url:Com_Parameter.ContextPath + 'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=findParentsNameById',
                     type:'GET',
                     dataType:'json',
                     async:false,
                     data:{'fdId':fdId},
                    }).responseText;
			}
		return fdParentsName;
	}
	window.changeShowParents=function(index){
		var fdId= $("input[name='fdClaimantId']").val();         		
		$("input[name*='fdDeptHint']").val(getParentsName(fdId));         		
	}
})
$(function(){
	setTimeout(function(){
		window.FSSC_ChangeIsAmortize();
	},1000);
	setTimeout(function(){
		var fdIsCloseFee=formInitData["fdIsCloseFee"];
		if(fdIsCloseFee=="true"){
			$("input[name='_fdIsCloseFee'][value='1']").attr("checked", true);
			$("input[name='fdIsCloseFee']").val("1");
		}
	},1000);
})
