seajs.use(['lui/dialog','lang!fssc-expense'],function(dialog,lang){
	Com_Parameter.event.submit.push(function(){
		if(!LUI('ytContent')){
			return true;
		}
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		var params = [],len = $("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").length;
		for(var i=0;i<len;i++){
			var fdDetailId = $("[name='fdDetailList_Form["+i+"].fdId']").val();
			var param = {
				fdCompanyId:$("[name='fdCompanyId']").val(),
				fdExpenseItemId:$("[name='fdDetailList_Form["+i+"].fdExpenseItemId']").val(),
				fdCostCenterId:$("[name='fdDetailList_Form["+i+"].fdCostCenterId']").val(),
				fdProjectId:$("[name='fdDetailList_Form["+i+"].fdProjectId']").val(),
				fdInnerOrderId:$("[name='fdDetailList_Form["+i+"].fdInnerOrderId']").val(),
				fdWbsId:$("[name='fdDetailList_Form["+i+"].fdWbsId']").val(),
				fdProappId:$("[name=fdProappId]").val()||'',
				fdDetailId:fdDetailId||i,
				index:i
			}
			if(!param.fdExpenseItemId){
				$("[name='fdDetailList_Form["+i+"].fdProvisionMoney']").val(0);
				$("[name='fdDetailList_Form["+i+"].fdProvisionInfo']").val("[]");
				return true;
			}
			params.push(param);
		}
		
		$.post(
			Com_Parameter.ContextPath+'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=matchProvision',
			{params:JSON.stringify(params)},
			function(rtn){
				rtn = JSON.parse(rtn);
				if(rtn.result!='success'){
					return;
				}
				for(var index in rtn){
					if(!isNaN(index)){//rtn循环有result座位key
						if(rtn[index]){
							var data = rtn[index].data;
							if(!data){
								continue;
							}
							var money = 0,apply = $("[name='fdDetailList_Form["+rtn[index].index+"].fdStandardMoney']").val();
							if(apply<0){
								return ;
							}
							for(var i=0;i<data.length;i++){
								money = numAdd(money,data[i].fdMoney);
							}
							if(money>apply){
								$("[name='fdDetailList_Form["+rtn[index].index+"].fdProvisionMoney']").val(apply);
							}else{
								$("[name='fdDetailList_Form["+rtn[index].index+"].fdProvisionMoney']").val(money);
							}
							$("[name='fdDetailList_Form["+rtn[index].index+"].fdProvisionInfo']").val(JSON.stringify(data));
						}else{
							$("[name='fdDetailList_Form["+rtn[index].index+"].fdProvisionMoney']").val(0);
							$("[name='fdDetailList_Form["+rtn[index].index+"].fdProvisionInfo']").val("[]");
						}
					}
				}
				FSSC_ReloadProvisionInfo();
			}
		);
		return true;
	})
	//提交前重新校验下不含税占用还是含税占用
	Com_Parameter.event["submit"].push(function(){ 
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		var fdCompanyId=$("input[name='fdCompanyId']").val();
		var data = new KMSSData();
		data.AddBeanData("eopBasedataCompanyService&type=getStandardCurrencyInfo&authCurrency=true&fdCompanyId="+fdCompanyId);
		data = data.GetHashMapArray();
		if(data.length>0){
			$("[name='fdDeduFlag']").val(data[0].fdDeduRule);
		}
		var len=$("input[name$='.fdApplyMoney']").length;
		for(var i=0;i<len;i++){
			reCalBudgetMoney(i);
		}
		return true;
	 });
	Com_Parameter.event.submit.push(function(){
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		var fdProappId = $("[name=fdProappId]").val(),pass = true;
		if(fdProappId){//如果有立项
			var params = [],len = $("#TABLE_DocList_fdDetailList_Form [name$=fdExpenseItemId]").length;
			for(var i=0;i<len;i++){
				var fdMoney=$("[name='fdDetailList_Form["+i+"].fdBudgetMoney']").val();
				var fdNoTaxMoney=$("[name='fdDetailList_Form["+i+"].fdNoTaxMoney']").val();
				if(!fdNoTaxMoney||fdNoTaxMoney==0){
					fdNoTaxMoney=fdMoney;
				}
				params.push({
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
					'fdCurrencyId':$("[name='fdDetailList_Form["+i+"].fdCurrencyId']").val(),
					'fdDetailId':i
				})
			}
			$.ajax({
				url:Com_Parameter.ContextPath+'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=matchProapp',
				data:{data:JSON.stringify({data:params,fdProappId:fdProappId})},
				async:false,
				type:'POST',
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
					for(var i in rtn){
						if(i=='result')continue;
						if(Com_GetUrlParameter(window.location.href,'method')=='view'){
							FSSC_ApproveShowProappInfo(rtn[i],i,params[i]);
						}else{
							FSSC_ShowProappInfo(rtn[i],i,params[i]);
						}
					}
				}
			});
			//检查状态
			var indexs = [];
			for(var i=0;i<len;i++){
				var status = $("[name='fdDetailList_Form["+i+"].fdProappStatus']").val();
				if(status=='2'||status=='0'){
					indexs.push(i+1);
				}
			}
			if(indexs.length>0){
				dialog.alert(lang['tips.proapp.over'].replace('{row}',indexs.join('、')));
				pass = false;
			}
		}
		return pass;
	});
	Com_Parameter.event.submit.push(function(){
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		var fdFeeIds = $("[name=fdFeeIds]").val(),pass = true;
		//如果有事前
		if(fdFeeIds){
			//暂存不作校验
			if($("[name=docStatus]").val()=='10'){
				return true;
			}
			var params = [],len = $("#TABLE_DocList_fdDetailList_Form [name$=fdExpenseItemId]").length;
			for(var i=0;i<len;i++){
				params.push({
					'fdCompanyId':$("[name='fdDetailList_Form["+i+"].fdCompanyId']").val(),
					'fdProjectId':$("[name='fdDetailList_Form["+i+"].fdProjectId']").val(),
					'fdWbsId':$("[name='fdDetailList_Form["+i+"].fdWbsId']").val(),
					'fdInnerOrderId':$("[name='fdDetailList_Form["+i+"].fdInnerOrderId']").val(),
					'fdExpenseItemId':$("[name='fdDetailList_Form["+i+"].fdExpenseItemId']").val(),
					'fdCostCenterId':$("[name='fdDetailList_Form["+i+"].fdCostCenterId']").val(),
					'fdPersonId':$("[name='fdDetailList_Form["+i+"].fdRealUserId']").val(),
					'fdDeptId':$("[name='fdDetailList_Form["+i+"].fdDeptId']").val(),
					'fdMoney':$("[name='fdDetailList_Form["+i+"].fdApplyMoney']").val(),
					'fdCurrencyId':$("[name='fdDetailList_Form["+i+"].fdCurrencyId']").val(),
					'fdDetailId':i,
					'fdModelId':$("[name=fdId]").val()
				})
			}
			$.ajax({
					url:Com_Parameter.ContextPath+'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=matchFee',
					data:{data:JSON.stringify({data:params,fdFeeIds:fdFeeIds})},
					async:false,
					type:'POST',
					success:function(rtn){
						if(!rtn){
							return;
						}
						rtn = JSON.parse(rtn);
						//匹配失败
						if(rtn.result=='failure'){
							dialog.alert(rtn.message?rtn.message:rtn.budget.errorMessage);
							pass = false;
							return;
						}
						for(var i=0;i<len;i++){
							var fdFeeStatus = "0";
							//如果找到了事前
							if(rtn[i]&&rtn[i].length>0){
								var fdFeeInfoDetail = FSSC_ShowFeeInfo(i,rtn[i]);
								var fdFeeStatus = fdFeeInfoDetail.fdFeeStatus;
								//如果金额超过事前的可用额度且事前为刚控，则提示用户不可以报销
								if(fdFeeStatus=='2'&&rtn['fdForbid']=='1'){
									dialog.alert(lang['tips.fee.over']);
									pass = false;
								}
								//显示红灯及超申请提示
								$("#fee_status_"+i).attr("class","budget_container budget_status_"+fdFeeStatus);
								$("#fee_status_"+i).attr("title",lang['py.fee.'+fdFeeStatus]);
							}else{
								$("[name$='["+i+"].fdFeeInfo']").val('[]');
								$("[name$='["+i+"].fdFeeStatus']").val('0');
							}
						}
					}
			});
		}
		return pass;
	});
	//查找预算
	Com_Parameter.event.submit.push(function(){
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		var fdProappId = $("[name=fdProappId]").val();
		//如果有立项，不校验预算
		if(fdProappId){
			return true;
		}
		var method=$("[name='method_GET']").val();
		var params = [],len = $("#TABLE_DocList_fdDetailList_Form [name$=fdExpenseItemId]").length;
		var indexMap = {};
		for(var i=0;i<len;i++){
			var fee = $("[name$='["+i+"].fdFeeInfo']").val()||'[]';
			fee = JSON.parse(fee.replace(/\'/g,"\""));
			var fdFeeInfoDetail = FSSC_ShowFeeInfo(i,fee);
			var fdFeeStatus = fdFeeInfoDetail.fdFeeStatus;
			var fdIsNeedBudget = fdFeeInfoDetail.fdIsNeedBudget;
			if(!fdIsNeedBudget||fdFeeStatus=='2'&&fee.fdForbid=='1'){
				$("[name$='["+i+"].fdBudgetInfo']").val('[]');
				$("[name$='["+i+"].fdBudgetStatus']").val('0');
				FSSC_ShowBudgetInfo(i);
				continue;
			}
			params.push({
				'fdCompanyId':$("[name='fdDetailList_Form["+i+"].fdCompanyId']").val(),
				'fdProjectId':$("[name='fdDetailList_Form["+i+"].fdProjectId']").val(),
				'fdWbsId':$("[name='fdDetailList_Form["+i+"].fdWbsId']").val(),
				'fdInnerOrderId':$("[name='fdDetailList_Form["+i+"].fdInnerOrderId']").val(),
				'fdExpenseItemId':$("[name='fdDetailList_Form["+i+"].fdExpenseItemId']").val(),
				'fdCostCenterId':$("[name='fdDetailList_Form["+i+"].fdCostCenterId']").val(),
				'fdPersonId':$("[name='fdDetailList_Form["+i+"].fdRealUserId']").val(),
				'fdDeptId':$("[name='fdDetailList_Form["+i+"].fdDeptId']").val(),
				'fdMoney':$("[name='fdDetailList_Form["+i+"].fdBudgetMoney']").val(),
				'fdCurrencyId':$("[name='fdDetailList_Form["+i+"].fdCurrencyId']").val(),
				'fdDetailId':i,
				'fdModelId':$("[name='fdId']").val(),
				'fdBudgetInfo':(formInitData["docStatus"]=='10'||formInitData["docStatus"]=='11')?"":$("[name='fdDetailList_Form["+i+"].fdBudgetInfo']").val()
			})
			//保存对应的下标，防止没有匹配到事前跳过后导致后续取金额报错。
			indexMap[i] = params.length-1;
			if(method=="view"&&$("[name='fdDetailList_Form["+i+"].fdBudgetInfo']").val()){
				params[indexMap[i]]['fdBudgetInfo']=$("[name='fdDetailList_Form["+i+"].fdBudgetInfo']").val();
			}
		}
		var pass = true;
		if(params.length>0){
			$.ajax({
				url:Com_Parameter.ContextPath+'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=getBudgetData',
				data:$.param({"data":JSON.stringify(params)},true),
				dataType:'json',
				async:false,
				type:'POST',
				success:function(rtn){
					var money = {};
					var budgetObj = $("[name$='budgetObj']").val()||'[]';
					var budgetOldObj = JSON.parse((budgetObj).replace(/\'/g,'"'));
					//校验单条明细是否超预算
					for(var i=0;i<rtn.length;i++){
						if(rtn[i].budget.result=='0'&&pass){
							dialog.alert(rtn[i].budget.message);
							pass = false;
							continue;
						}
						var budget = rtn[i].budget.data||[];
						$("[name='fdDetailList_Form["+rtn[i].fdDetailId+"].fdBudgetInfo']").val(JSON.stringify(budget).replace(/\"/g,"'"));
						var applyMoney = params[indexMap[rtn[i].fdDetailId]].fdMoney;
						var bmoney = applyMoney;
						//扣除事前可用额
						var feeInfo = $("[name$='["+rtn[i].fdDetailId+"].fdFeeInfo']").val()||'[]';
						feeInfo = JSON.parse(feeInfo.replace(/\'/g,'"'));
						for(var k=0;k<feeInfo.length;k++){
							//如果该事前未占用预算，不统计在内
							if(!feeInfo[k].fdIsUseBudget){
								continue;
							}
							applyMoney = numSub(applyMoney,feeInfo[k].fdUsableMoney);
						}
						for(var k=0;k<budget.length;k++){
							//弹性控制，需要计算弹性比例
							if(budget[k].fdRule=='3'){
								budget[k].fdElasticMoney = numDiv(numMulti(budget[k].fdTotalAmount,budget[k].fdElasticPercent),100);
							}else{
								budget[k].fdElasticMoney = 0;
							}
							if(money[budget[k].fdBudgetId]){
								money[budget[k].fdBudgetId].fdApplyMoney = numAdd(money[budget[k].fdBudgetId].fdApplyMoney,bmoney*1);
								money[budget[k].fdBudgetId].fdDetailId.push(rtn[i].fdDetailId)
							}else{
								money[budget[k].fdBudgetId] = {
									fdApplyMoney:bmoney*1,
									fdCanUseMoney : budget[k].fdCanUseAmount*1,
									fdDetailId:[rtn[i].fdDetailId],
									fdRule : budget[k].fdRule,
									fdElasticMoney : budget[k].fdElasticMoney
								}
							}
							//财务复核的情况下需要加上原申报金额避免重复计算
							if(Com_GetUrlParameter(window.location.href,"method")=='view'||formInitData['docStatus']=='20'){
								var detail_id=$("[name='fdDetailList_Form["+rtn[i].fdDetailId+"].fdId']").val();
								var money_old=budgetOldObj[detail_id];
								if(!money_old){
									money_old=0;
								}
								money[budget[k].fdBudgetId].fdApplyMoney = numSub(money[budget[k].fdBudgetId].fdApplyMoney,money_old);
								applyMoney = numSub(applyMoney,money_old);
							}
							//如果不是柔性控制，需要判断是否超预算
							if(budget[k].fdRule!='2'&&applyMoney>numAdd(budget[k].fdCanUseAmount,budget[k].fdElasticMoney)&&pass){
								dialog.alert(lang['tips.budget.over.row'].replace('{row}',rtn[i].fdDetailId*1+1));
								pass = false;
							}
						}
					}
					//校验多条明细占用同一个预算的情况下是否超预算，并显示预算状态图标
					var status = {};
					for(var i in money){
						var feeInfo = $("[name$='["+money[i].fdDetailId[0]+"].fdFeeInfo']").val()||'[]';
						feeInfo = JSON.parse(feeInfo.replace(/\'/g,'"'));
						for(var k=0;k<feeInfo.length;k++){
							money[i].fdApplyMoney = numSub(money[i].fdApplyMoney,feeInfo[k].fdUsableMoney);
						}
						if(pass&&money[i].fdRule!='2'&&money[i].fdApplyMoney>numAdd(money[i].fdCanUseMoney,money[i].fdElasticMoney)){
							dialog.alert(lang['tips.budget.over']);
							pass = false;
						}
						if(money[i].fdApplyMoney>money[i].fdCanUseMoney){
							for(var k=0;k<money[i].fdDetailId.length;k++){
								status[money[i].fdDetailId[k]] = '2';
							}
						}else{
							for(var k=0;k<money[i].fdDetailId.length;k++){
								if(!status[money[i].fdDetailId[k]]){
									status[money[i].fdDetailId[k]] = '1';
								}
							}
						}
					}
					//设置预算状态显示
					var fdBudgetShowType = $("[name=fdBudgetShowType]").val();
					for(var i=0;i<len;i++){
						status[i] = status[i]?status[i]:'0';
						$("[name='fdDetailList_Form["+i+"].fdBudgetStatus']").val(status[i]);
						var budget = $("[name='fdDetailList_Form["+i+"].fdBudgetInfo']").val();
						if(fdBudgetShowType=='1'){
							$("#buget_status_"+i).attr("class","budget_container");
							$("#buget_status_"+i).addClass("budget_status_"+status[i]);
							$("#buget_status_"+i).attr("title",lang['py.budget.'+status[i]]);
						}else{
							budget = budget||'[]';
							budget = JSON.parse(budget.replace(/\'/g,'"'));
							var showBudget = null;
							for(var k=0;k<budget.length;k++){
								if(!showBudget||showBudget.fdCanUseAmount>budget[k].fdCanUseAmount){
									showBudget = budget[k];
								}
							}
							showBudget = showBudget?showBudget:{fdTotalAmount:0,fdOccupyAmount:0,fdAlreadyUsedAmount:0,fdCanUseAmount:0,canUseAmountDisplay:0};
							if(showBudget){
								$("#buget_status_"+i).html(lang['py.money.total']+showBudget.fdTotalAmount+"<br>"+lang['py.money.using']+showBudget.fdOccupyAmount+"<br>"+lang['py.money.used']+showBudget.fdAlreadyUsedAmount+"<br>"+lang['py.money.usable']+"<span class='budget_money_"+i+"'>"+(showBudget.canUseAmountDisplay?showBudget.canUseAmountDisplay:showBudget.fdCanUseAmount)+"</span>");
								$(".budget_money_"+i).css("color",status[i]=='2'?"red":"#333");
							}
						}
					}
				}
			})
		}
		return pass;
	});
	
	//校验标准
	Com_Parameter.event.submit.push(function(){
		if(!$("[name=checkVersion]").val()){
			return true;
		}
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		var params = [],len = $("#TABLE_DocList_fdDetailList_Form tr [name$=fdExpenseItemId]").length,fdExpenseType = $("[name=fdExpenseType]").val();
		var method_GET=$("input[name='method_GET']").val();
		var fdId = $("[name=fdId]").val();
		if(Com_GetUrlParameter(window.location.href,'method')=='view'){
			fdId = Com_GetUrlParameter(window.location.href,'fdId');
		}
		for(var i=0;i<len;i++){
			var fdMoney=$("[name='fdDetailList_Form["+i+"].fdApplyMoney']").val();
			if(method_GET=='view'){
				fdMoney=$("[name='fdDetailList_Form["+i+"].fdApprovedApplyMoney']").val();
			}
			params.push({
				'fdCompanyId':$("[name='fdDetailList_Form["+i+"].fdCompanyId']").val(),
				'fdExpenseItemId':$("[name='fdDetailList_Form["+i+"].fdExpenseItemId']").val(),
				'fdPersonId':$("[name='fdDetailList_Form["+i+"].fdRealUserId']").val(),
				'fdDeptId':$("[name='fdDetailList_Form["+i+"].fdDeptId']").val(),
				'fdMoney':fdMoney,
				'fdPersonNumber':$("[name='fdDetailList_Form["+i+"].fdPersonNumber']").val(),
				'fdCurrencyId':$("[name='fdDetailList_Form["+i+"].fdCurrencyId']").val(),
				'fdTravel':$("[name='fdDetailList_Form["+i+"].fdTravel']").val(),
				'fdDetailId':$("[name='fdDetailList_Form["+i+"].fdId']").val(),
				'fdHappenDate':$("[name='fdDetailList_Form["+i+"].fdHappenDate']").val(),
				'fdModelId':fdId,
				'model':'expense',
				'index':i
			})
		}
		//如果是差旅费类型，参数中需要加上地域、舱位等
		//行程独立，从行程明细中获得数据
		var fdExpenseType = $("[name=fdExpenseType]").val();
		var fdIsTravelAlone = $("[name=fdIsTravelAlone]").val();
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
				params[i].fdTravelDays = $("[name='fdDetailList_Form["+i+"].fdTravelDays']").val();
				params[i].fdBerthId = $("[name='fdDetailList_Form["+i+"].fdBerthId']").val();
				params[i].fdAreaId = $("[name='fdDetailList_Form["+i+"].fdArrivalPlaceId']").val();
				params[i].fdBeginDate = $("[name='fdDetailList_Form["+i+"].fdStartDate']").val()||'';
				params[i].fdEndDate = $("[name='fdDetailList_Form["+i+"].fdHappenDate']").val();
			}
		}
		var pass = true;
		$.ajax({
			url:Com_Parameter.ContextPath + 'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=getStandardData',
			data:{params:JSON.stringify(params)},
			async:false,
			type:'POST',
			success:function(rtn){
				rtn = JSON.parse(rtn);
				if(rtn.result=='failure'){
					dialog.alert(rtn.message);
					pass = false;
					return;
				}
				var method = Com_GetUrlParameter(window.location.href,'method');
				for(var i=0;i<rtn.data.length;i++){
					if(rtn.data[i].dayCount){//如果是按日标准，需要校验当前单中是否重复报销
						var fdExpenseItemId = params[rtn.data[i].index].fdExpenseItemId;
						var fdSchemeId = rtn.data[i].fdSchemeId;
						var fdBeginDate = getTravelDate('begin',rtn.data[i].index)
						var fdEndDate = getTravelDate('end',rtn.data[i].index)
						var fdPersonId = params[rtn.data[i].index].fdPersonId;
						$("#TABLE_DocList_fdDetailList_Form tr:gt(0)").each(function(index){
							var standard = $(this).find("[name$=fdStandardInfo]").val()||'[]';
							standard = JSON.parse(standard.replace(/\'/g,'"'));
							if(rtn.data[i].status=='2'||index>=rtn.data[i].index||standard.length==0){
								return;
							}
							var expense = $(this).find("[name$=fdExpenseItemId]").val();
							var begin = getTravelDate('begin',index);
							var end = getTravelDate('end',index);
							var personId = $(this).find("[name$=fdRealUserId]").val();
							var containsScheme = false;
							for(var k=0;k<standard.length;k++){
								if(standard[k].fdSchemeId==fdSchemeId){
									containsScheme=true;
									break;
								}
							}
							if(isInter(fdBeginDate,fdEndDate,begin,end)&&expense==fdExpenseItemId&&containsScheme&&fdPersonId==personId){
								rtn.data[i].status='2';
								if(rtn.data[i].fdForbid=='1'){
									rtn.data[i].submit='0'
								}
							}
						})
					}
					if(rtn.data[i].monthCount){//如果是月次标准，需要校验当前单中是否重复报销
						var fdExpenseItemId = params[rtn.data[i].index].fdExpenseItemId;
						var fdSchemeId = rtn.data[i].fdSchemeId;
						var fdPersonId = params[rtn.data[i].index].fdPersonId;
						var month = params[rtn.data[i].index].fdEndDate.split('\-')[1];
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
								if(rtn.data[i].fdForbid=='1'){
									rtn.data[i].submit='0'
								}
							}
						})
					}
					$("[name='fdDetailList_Form["+rtn.data[i].index+"].fdStandardStatus']").val(rtn.data[i].status);
					if(rtn.data[i].info){
						$("[name='fdDetailList_Form["+rtn.data[i].index+"].fdStandardInfo']").val((rtn.data[i].info).replace(/\"/g,"'"));
					}
					if(rtn.data[i].status=='2'&&rtn.data[i].submit=='0'&&pass){
						dialog.alert(lang['tips.standard.over'].replace("{row}",rtn.data[i].index*1+1));
						pass = false;
					}
					$("[name='fdDetailList_Form["+rtn.data[i].index+"].fdStandardStatus']").parent().parent().find("[id^=standard_status]").attr("class","budget_container");
					$("[name='fdDetailList_Form["+rtn.data[i].index+"].fdStandardStatus']").parent().parent().find("[id^=standard_status]").addClass("budget_status_"+rtn.data[i].status);
					$("[name='fdDetailList_Form["+rtn.data[i].index+"].fdStandardStatus']").parent().parent().find("[id^=standard_status]").attr("title",lang['py.standard.'+rtn.data[i].status]);
				}
			}
		});
		return pass;
	});
	
	//校验摊销比例
	Com_Parameter.event.submit.push(function(){
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		var fdIsAmortize = $("[name=fdIsAmortize]:checked").val();
		if('true'==fdIsAmortize){
			var fdPercent = 0;
			$("#TABLE_DocList_Amortize tr:gt(0)").find("[name$=fdPercent]").each(function(){
				fdPercent = addPoint(fdPercent,this.value*1);
			});
			if(fdPercent!=100){
				dialog.alert(lang['tips.amortize.percent.error']);
				return false;
			}
		}
		return true;
	});
	
	//校验金额
	Com_Parameter.event.submit.push(function(){
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		/*var fdExpenseMoney = 0,fdLoanMoney = 0,fdPaymentMoney = 0;
		$("#TABLE_DocList_fdDetailList_Form").find("[name$=fdStandardMoney]").each(function(){
			fdExpenseMoney = numAdd(fdExpenseMoney,$(this).val()*1);
		});
		$("#TABLE_DocList_fdAccountsList_Form tr").each(function(){
			var fdMoney = $(this).find("[name$=fdMoney]");
			var fdExchangeRate = $(this).find("[name$=fdExchangeRate]");
			if(fdMoney.length>0){
				fdPaymentMoney = numAdd(fdPaymentMoney,numMulti(fdMoney.val(),fdExchangeRate.val()));
			}
		});
		$("#TABLE_DocList_fdOffsetList_Form").find("[name$=fdOffsetMoney]").each(function(){
			fdLoanMoney = numAdd(fdLoanMoney,$(this).val()*1);
		});
		if(Math.abs(formatFloat(fdExpenseMoney-addPoint(fdLoanMoney,fdPaymentMoney),2))>getDiffOfRate()){
			dialog.alert(lang['tips.moneyInvalid']);
			return false;
		}*/
		return true;
	});
	//#166975 判断申请币种和收款币种是否都为本位币，非本位币每行明细允许汇率差
	window.getDiffOfRate=function(){
		var diffOfRate=0.00;  //默认都是本币，不存在汇率差
		var fdCompanyId=$("[name='fdCompanyId']").val();
		var data = new KMSSData();
		data.AddBeanData("eopBasedataCompanyService&type=getStandardCurrencyInfo&authCurrency=true&fdCompanyId="+fdCompanyId);
		data = data.GetHashMapArray();
		var foreignFlag=false;
		if(data.length>0){
			var fdCurrencyId=data[0].fdCurrencyId; //本位币
			var len=$("#TABLE_DocList_fdDetailList_Form tr").length-1;
			for(var i=0;i<len;i++){
				var currencyId=$("[name='fdDetailList_Form["+i+"].fdCurrencyId']").val();
				if(fdCurrencyId!=currencyId){
					foreignFlag=true;
					break;
				}
			}
			if(!foreignFlag){
				var length=$("#TABLE_DocList_fdAccountsList_Form tr").length-1;
				for(var i=0;i<length;i++){
					var currencyId=$("[name='fdAccountsList_Form["+i+"].fdCurrencyId']").val();
					if(fdCurrencyId!=currencyId){
						foreignFlag=true;
						break;
					}
				}
			}
			if(foreignFlag){
				diffOfRate=formatFloat(0.01*len,2);
			}
		}
		return diffOfRate;
	};
	//校验明细是否为空
	Com_Parameter.event.submit.push(function(){
		var len = 0;
		$("#TABLE_DocList_fdDetailList_Form").find("[name$=fdStandardMoney]").each(function(){
			if(this.name.indexOf('!{index}')==-1){
				len++;
			}
		});
		if(len==0){
			dialog.alert(lang['tips.expense.detail.empty']);
			return false;
		}
		return true;
	});
	//校验费用类型是否必须要有预算
	Com_Parameter.event.submit.push(function(){
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		var fdProappId = $("[name=fdProappId]").val();
		if(fdProappId){
			return true;
		}
		var pass = true,exp=[],docTemplateId = $("[name=docTemplateId]").val(),fdCompanyId = $("[name=fdCompanyId]").val();
		$("#TABLE_DocList_fdDetailList_Form tr:gt(0)").each(function(){
			var fdExpenseItemId = $(this).find("[name$=fdExpenseItemId]").val();
			if(!fdExpenseItemId){
				return;
			}
			var fdFeeStatus = $(this).find("[name$='fdFeeStatus']").val();
			var fdFeeInfo = $(this).find("[name$='fdFeeInfo']").val()||'[]';
			fdFeeInfo = JSON.parse(fdFeeInfo.replace(/\'/g,'"'));
			var isUseBudget = false;
			for(var k=0;k<fdFeeInfo.length;k++){
				if(fdFeeInfo[k].fdIsUseBudget){
					isUseBudget = true;
					break;
				}
			}
			//如果在未超事前且事前占了预算，则不需要校验预算
			if(isUseBudget&&fdFeeStatus=='1'){
				return;
			}
			var fdBudgetStatus = $(this).find("[name$=fdBudgetStatus]").val(); 
			if(fdBudgetStatus=='0'){
				var data = new KMSSData();
				data.AddBeanData("fsscExpenseItemConfigService&fdExpenseItemId="+fdExpenseItemId+"&docTemplateId="+docTemplateId+"&fdCompanyId="+fdCompanyId);
				data = data.GetHashMapArray();
				if(data&&data[0].required=='true'){
					var name = $(this).find("[name$=fdExpenseItemName]").val();
					if(!exp.contains(name)){
						exp.push(name)
					}
					pass = false;
				}
			}
		});
		if(!pass){
			dialog.alert(lang['tips.expense.item.need.budget'].replace("{0}",exp.join("、")));
		}
		return pass;
	});
	
	 Com_Parameter.event["submit"].push(function(){ 
		var flag=true;
		var fdFeeIds=$("[name='fdFeeIds']").val();
		var fdId=$("[name='fdId']").val();
		if(!fdId){
			fdId=Com_GetUrlParameter(window.location.href,'fdId');
		}
		if(fdFeeIds){
				$.ajax({
				url:Com_Parameter.ContextPath + 'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=checkFeeRelation&fdFeeIds='+fdFeeIds+'&fdId='+fdId,
				async:false,
				type:'POST',
				success:function(rtn){
					rtn = JSON.parse(rtn);
					if(!rtn.result){//不允许关闭
						flag=false;
						dialog.alert(lang['msg.fee.tips.examineing']);
					}
				}
			});
		}
	 	return flag;
	 });
	 
	  //校验发票是否重复
	Com_Parameter.event["submit"].push(function(){ 
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		var flag=true;
		var length=$("#TABLE_DocList_fdInvoiceList_Form [name$=fdInvoiceNumber]").length;
		var number=[];
		if(length>0){
			for(var i=0;i<length;i++){
				var fdInvoiceNumber=$("[name='fdInvoiceList_Form["+i+"].fdInvoiceNumber']").val();
				var fdInvoiceCode=$("[name='fdInvoiceList_Form["+i+"].fdInvoiceCode']").val();
				if((fdInvoiceNumber+fdInvoiceCode)&&number.indexOf(fdInvoiceNumber+";"+fdInvoiceCode)>-1){
					dialog.alert(lang['tips.invoice.repeat']);
					return false;
				}else{
					number.push(fdInvoiceNumber+";"+fdInvoiceCode);
				}
			}
		}
		var fdId = Com_GetUrlParameter(window.location.href,'fdId');
		if(!fdId){
			fdId=$("[name='fdId']").val();
		}
		//校验是否有其他单据关联了发票明细的发票
		if(number.length>0){
				$.ajax({
				url:Com_Parameter.ContextPath + 'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=checkInvoiceDetail',
				data:{data:JSON.stringify({number:number,fdModelId:fdId})},
				async:false,
				type:'POST',
				success:function(rtn){
					if(rtn){
						rtn = JSON.parse(rtn);
						if(rtn.msg){//有重复发票
							flag=false;
							dialog.alert(rtn.msg);
						}
					}
				}
			});
		}
	 	return flag;
	 });
	
})
