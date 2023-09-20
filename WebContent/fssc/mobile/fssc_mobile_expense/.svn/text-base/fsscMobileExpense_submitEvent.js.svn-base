
	window.submitForm = function(status, method, isDraft){
		if(isDraft == true){
			validateOpt(true);
		}else{
			validateOpt(false);
		}
		$("[name=docStatus]").val(status);
		var action = document.forms[formOption.formName].action;
		document.forms[formOption.formName].action = Com_SetUrlParameter(action,"status",status);
		Com_Submit(document.forms[formOption.formName], method);
	}
	
	
	function validateOpt(cancel){
		if(window.formValiduteObj!=null && formOption.subjectField!=''){
			if(cancel){
				window.formValiduteObj.removeElements(document.forms[formOption.formName],'required');
				window.formValiduteObj.resetElementsValidate($("input[name='" + formOption.subjectField + "']").get(0));
			}else{
				window.formValiduteObj.resetElementsValidate(document.forms[formOption.formName]);
			}
		}
	} 
	
	
	//校验必填项、校验金额
	Com_Parameter.event.submit.push(function(){
		var docStatus = $("[name=docStatus]").val();
		if(docStatus=='10'){
			return true;
		}
		var msg=fsscLang['errors.required'];
		var isShowDraftsmanStatus = $("[name=isShowDraftsmanStatus]").val();
		var isPopupWindowRemindSubmitter = $("[name=isPopupWindowRemindSubmitter]").val();
		if(isShowDraftsmanStatus=='true'&&isPopupWindowRemindSubmitter=='false'){
			var fdSubmitTypeId = $("[name=fdSubmitTypeId]").val();
			if($("[name=fdSubmitTypeId]").length>0&&!fdSubmitTypeId){
				  jqtoast(msg.replace("{0}",fsscLang['fssc-mobile:py.submitType']));
				  subFlag=false;
				  return false;
			}
		}
		var pass=true,
		fdContent = $("[name='fdContent']").val();
		fdProjectId = $("[name='fdProjectId']").val(); 
		fdFeeIds = $("[name='fdFeeIds']").val();
		fdIsFee = $("[name='fdIsFee']").val();
		fdIsProject = $("[name='fdIsProject']").val();
		fdAttNumber = $("[name='fdAttNumber']").val();
	 	len = $("table#TABLE_DocList_fdDetailList_Form>tbody>tr").length;
		if($("[name='docSubject']").val()==""){
			jqtoast(fsscLang['fssc-expense:fsscExpenseMain.docSubject']+fsscLang["errors.required"].replace("{0}",""));
			return false;
		}
		if(fdContent==""){
			jqtoast(fsscLang['fssc-expense:fsscExpenseMain.fdContent']+"不能为空!");
			return false;
		}
		if(fdAttNumber==""){
			jqtoast(fsscLang['fssc-expense:fsscExpenseMain.fdAttNumber']+"不能为空!");
			return false;
		}
		if(fdIsProject=='true' && fdProjectId==""){
			jqtoast(fsscLang['fssc-expense:fsscExpenseMain.fdProject']+"不能为空!");
			return false;
		}
		if(fdIsFee=='true' && fdFeeIds==""){
			jqtoast(fsscLang['fssc-expense:fsscExpenseMain.fdFeeNames']+"不能为空!");
			return false;
		}
		
		if(len==0) {
			jqtoast("费用明细至少有一行!");		
			return false;
		 }
		//行程独立，费用明细要填对应行程
		var fdExpenseType = $("[name=fdExpenseType]").val();
		var fdIsTravelAlone = $("[name=fdIsTravelAlone]").val();
		if(fdExpenseType=='2'&&fdIsTravelAlone=='true'){
			var detailIndex = null;
			$("#TABLE_DocList_fdDetailList_Form>tbody>tr [name$=fdTravel]").each(function(i){
				if(!this.value){
					detailIndex = i+1;
					return;
				}
			})
			if(detailIndex){
				jqtoast('第'+detailIndex+'条费用明细的所属行程为空，请补充后再提交！');
				return false;
			}
		}
		if(fdExpenseType=='2'&&fdIsTravelAlone=='false'){
			var detailIndex = null;
			$("#TABLE_DocList_fdDetailList_Form>tbody>tr [name$=fdStartPlaceId]").each(function(i){
				if(!this.value){
					detailIndex = i+1;
					return;
				}
			})
			if(detailIndex){
				jqtoast('第'+detailIndex+'条费用明细的出发城市为空，请补充后再提交！');
				return false;
			}
			$("#TABLE_DocList_fdDetailList_Form>tbody>tr [name$=fdArrivalPlaceId]").each(function(i){
				if(!this.value){
					detailIndex = i+1;
					return;
				}
			})
			if(detailIndex){
				jqtoast('第'+detailIndex+'条费用明细的到达城市为空，请补充后再提交！');
				return false;
			}
			$("#TABLE_DocList_fdDetailList_Form>tbody>tr [name$=fdBerthId]").each(function(i){
				if(!this.value){
					detailIndex = i+1;
					return;
				}
			})
			if(detailIndex){
				jqtoast('第'+detailIndex+'条费用明细的交通工具为空，请补充后再提交！');
				return false;
			}
		}
		//检查费用类型是否填了，防止选择随手记后不编辑直接提交，但随手记中没有费用类型的情况
		$("#TABLE_DocList_fdDetailList_Form>tbody>tr [name$=fdExpenseItemId]").each(function(i){
			if(!this.value){
				detailIndex = i+1;
				return;
			}
		})
		if(detailIndex){
			jqtoast('第'+detailIndex+'条费用明细的费用类型为空，请补充后再提交！');
			return false;
		}
		//检查招待人数是否填了，防止选择随手记后不编辑直接提交，但随手记中没有费用类型的情况
		var fdExtendFields = $("[name=fdExtendFields]").val()||'';
		fdExtendFields =";"+fdExtendFields+";";
		if(fdExtendFields.indexOf(';1;')>-1){
			$("#TABLE_DocList_fdDetailList_Form>tbody>tr [name$=fdPersonNumber]").each(function(i){
				if(!this.value){
					detailIndex = i+1;
					return;
				}
			})
			if(detailIndex){
				jqtoast('第'+detailIndex+'条费用明细的招待人数为空，请补充后再提交！');
				return false;
			}
		}
		var payIndex = null;
		$("#TABLE_DocList_fdAccountsList_Form>tbody>tr [name$=fdPayWayId]").each(function(i){
			if(!this.value){
				payIndex = i+1;
				return;
			}
		})
		if(payIndex){
			jqtoast('第'+payIndex+'条收款明细的付款方式为空，请补充后再提交！');
			return false;
		}
		
		//校验金额
		var fdExpenseMoney = 0,fdLoanMoney = 0,fdPaymentMoney = 0;
		$("#TABLE_DocList_fdDetailList_Form").find("[name$=fdStandardMoney]").each(function(){
			fdExpenseMoney = numAdd(fdExpenseMoney,$(this).val()*1);
			console.log(fdExpenseMoney+"fdExpenseMoney");
		});
		console.log(fdExpenseMoney+"fdExpenseMoney");
		$("#TABLE_DocList_fdAccountsList_Form tr").each(function(){
			var fdMoney = $(this).find("[name$=fdMoney]");
			var fdExchangeRate = $(this).find("[name$=fdExchangeRate]");
			if(fdMoney.length>0){
				fdPaymentMoney = numAdd(fdPaymentMoney,numMulti(fdMoney.val(),fdExchangeRate.val()));
			}
			console.log(fdExchangeRate.val()+"fdExchangeRate.val()");
		});
		console.log(fdPaymentMoney+"fdPaymentMoney");
		$("#TABLE_DocList_fdOffsetList_Form").find("[name$=fdOffsetMoney]").each(function(){
			fdLoanMoney = numAdd(fdLoanMoney,$(this).val()*1);
		});
		
		if(fdExpenseMoney!=addPoint(fdLoanMoney,fdPaymentMoney)){
			jqtoast(fsscLang['fssc-expense:tips.moneyInvalid']);
			return false;
		 }
		$("#TABLE_DocList_fdDetailList_Form").find("[name$=fdStandardMoney]").each(function(){
			fdExpenseMoney = numAdd(fdExpenseMoney,$(this).val()*1);
			
		});
		var len=$("input[name$='.fdApplyMoney']").length;
		for(var i=0;i<len;i++){
			reCalBudgetMoneyOfSubmit(i);
		}
		return true;
	})
	
	//事前检验
	Com_Parameter.event.submit.push(function(){
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		var fdFeeIds = $("#fdFeeIds").val(),pass = true;
		if(fdFeeIds){
			var params = [],len = $("#TABLE_DocList_fdDetailList_Form [name$=fdApplyMoney]").length;
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
					'fdDetailId':i
				})
			}
			
			$.ajax({
				url:Com_Parameter.ContextPath+'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=matchFee',
				data:{data:JSON.stringify({data:params,fdFeeIds:fdFeeIds})},
				async:false,
				success:function(rtn){
					if(!rtn){
						return;
					}
					rtn = JSON.parse(rtn);
					//匹配失败
					if(rtn.result=='failure'){
						alert(rtn.message?rtn.message:rtn.budget.errorMessage);
						pass = false;
						return;
					}
					for(var i=0;i<len;i++){
						var fdFeeStatus = "0"; 	
						//如果找到了事前
						if(rtn[i]&&rtn[i].length>0){
							fdFeeStatus = "1";
							var money = FSSC_ShowFeeInfo(i,rtn[i]);
							
							//如果金额超过事前的可用额度且事前为刚控，则提示用户不可以报销
							if(money>0){
								if(rtn['fdForbid']=='1'){
									jqtoast(fsscLang['fssc-expense:tips.fee.over']);
									pass = false;
								}
								fdFeeStatus = "2";
							}
							$("[name='fdDetailList_Form["+i+"].fdFeeStatus']").val(fdFeeStatus);
							
							//显示红灯及超申请提示
							$("#fdDetailListId>li").eq(i).find(".feeStatus").attr("class","feeStatus ld-newApplicationForm-travelInfo-top-buget-"+fdFeeStatus);
							$("#fdDetailListId>li").eq(i).find(".feeStatus").html(fdFeeStatus=='2'?"超申请":"申请内");
						}else{
							$("[name='[fdDetailList_Form"+i+"].fdFeeInfo']").val('[]');
							$("[name='[fdDetailList_Form"+i+"].fdFeeStatus']").val('0');
							$("#fdDetailListId>li").eq(i).find(".feeStatus").attr("class","feeStatus ld-newApplicationForm-travelInfo-top-buget-0");
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
		var method=$("[name='method_GET']").val();
		var params = [],len = $("#TABLE_DocList_fdDetailList_Form [name$=fdApplyMoney]").length;
		for(var i=0;i<len;i++){
			var fdFeeStatus = $("[name='fdDetailList_Form["+i+"].fdFeeStatus']").val();
			var fdFeeInfo = $("[name='fdDetailList_Form["+i+"].fdFeeInfo']").val()||'[]';
			fdFeeInfo = JSON.parse(fdFeeInfo.replace(/\'/g,'"'));
			var isUseBudget = false;
			for(var k=0;k<fdFeeInfo.length;k++){
				if(fdFeeInfo[k].fdIsUseBudget){
					isUseBudget = true;
					break;
				}
			}
			//如果在未超事前且事前占了预算，则不需要匹配预算
			if(isUseBudget&&fdFeeStatus=='1'){
				$("[name='fdDetailList_Form["+i+"].fdBudgetInfo']").val('[]');
				$("[name='fdDetailList_Form["+i+"].fdBudgetStatus']").val('0');
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
				'fdDetailId':i
			})
			if(method=="view"&&$("[name='fdDetailList_Form["+i+"].fdBudgetInfo']").val()){
				params[i]['fdBudgetInfo']=$("[name='fdDetailList_Form["+i+"].fdBudgetInfo']").val();
			}
		}
		var pass = true;
		$.ajax({
			url:Com_Parameter.ContextPath+'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=getBudgetData',
			data:$.param({"data":JSON.stringify(params)},true),
			dataType:'json',
			async:false,
			success:function(rtn){
				var money = {};
				//校验单条明细是否超预算
				for(var i=0;i<rtn.length;i++){
					if(rtn[i].budget.result=='0'&&pass){
						jqtoast(rtn[i].budget.message);
						pass = false;
						continue;
					}
					var budget = rtn[i].budget.data||[];
					$("[name='fdDetailList_Form["+rtn[i].fdDetailId+"].fdBudgetInfo']").val(JSON.stringify(budget).replace(/\"/g,"'"));
					var applyMoney = 0;
					for(var k=0;k<params.length;k++){
						if(params[k].fdDetailId==rtn[i].fdDetailId){
							applyMoney = params[k].fdMoney;
							break;
						}
					}
					var bmoney = applyMoney;
					//扣除事前可用额
					var feeInfo = $("[name$='["+rtn[i].fdDetailId+"].fdFeeInfo']").val()||'[]';
					feeInfo = JSON.parse(feeInfo.replace(/\'/g,'"'));
					for(var k=0;k<feeInfo.length;k++){
						//如果该事前未占用预算，不统计在内
						if(feeInfo[k].fdIsUseBudget!='true'){
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
						if(Com_GetUrlParameter(window.location.href,"method")=='view'){
							money[budget[k].fdBudgetId].fdApplyMoney = numSub(money[budget[k].fdBudgetId].fdApplyMoney,$("[name='fdDetailList_Form["+rtn[i].fdDetailId+"].fdBudgetMoneyOld']").val());
							applyMoney = numSub(applyMoney,$("[name='fdDetailList_Form["+rtn[i].fdDetailId+"].fdBudgetMoneyOld']").val());
						}
						//如果不是柔性控制，需要判断是否超预算
						if(budget[k].fdRule!='2'&&applyMoney>numAdd(budget[k].fdCanUseAmount,budget[k].fdElasticMoney)&&pass){
							jqtoast(fsscLang['fssc-expense:tips.budget.over.row'].replace('{row}',rtn[i].fdDetailId*1+1));
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
						alert(fsscLang['fssc-expense:tips.budget.over']);
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
				for(var i=0;i<len;i++){
					status[i] = status[i]?status[i]:'0';
					$("[name='fdDetailList_Form["+i+"].fdBudgetStatus']").val(status[i]);
					//直接提示
					var statusDesc=fsscLang["fssc-mobile:budget.no"];  //默认无预算
					if(status[i]=='1'){ //预算内
						statusDesc=fsscLang["fssc-mobile:budget.in"];
					}else if(status[i]=='2'){  //超预算
						statusDesc=fsscLang["fssc-mobile:budget.out"];
					}
					$("#fdDetailListId>li").eq(i).find(".bugetStatus").html(statusDesc?statusDesc:"");
					$("#fdDetailListId>li").eq(i).find(".bugetStatus").attr("class","bugetStatus ld-newApplicationForm-travelInfo-top-buget-"+status[i]);
				}
			}
		})
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
		var params = [],len = $("#TABLE_DocList_fdDetailList_Form tr [name$=fdApplyMoney]").length,fdExpenseType = $("[name=fdExpenseType]").val();
		for(var i=0;i<len;i++){
			params.push({
				'fdCompanyId':$("[name='fdDetailList_Form["+i+"].fdCompanyId']").val(),
				'fdExpenseItemId':$("[name='fdDetailList_Form["+i+"].fdExpenseItemId']").val(),
				'fdPersonId':$("[name='fdDetailList_Form["+i+"].fdRealUserId']").val(),
				'fdMoney':$("[name='fdDetailList_Form["+i+"].fdApplyMoney']").val(),
				'fdPersonNumber':$("[name='fdDetailList_Form["+i+"].fdPersonNumber']").val(),
				'fdCurrencyId':$("[name='fdDetailList_Form["+i+"].fdCurrencyId']").val(),
				'fdTravel':$("[name='fdDetailList_Form["+i+"].fdTravel']").val(),
				'fdDetailId':$("[name='fdDetailList_Form["+i+"].fdId']").val(),
				'fdHappenDate':$("[name='fdDetailList_Form["+i+"].fdHappenDate']").val(),
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
			$("#TABLE_DocList_fdTravelList_Form>tbody>tr").each(function(){
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
			success:function(rtn){
				rtn = JSON.parse(rtn);
				if(rtn.result=='failure'){
					jqtoast(rtn.message);
					pass = false;
					return;
				}
				for(var i=0;i<rtn.data.length;i++){
					if(rtn.data[i].dayCount){//如果是按日标准，需要校验当前单中是否重复报销
						var fdExpenseItemId = params[rtn.data[i].index].fdExpenseItemId;
						var fdSchemeId = rtn.data[i].fdSchemeId;
						var fdBeginDate = params[rtn.data[i].index].fdBeginDate;
						var fdEndDate = params[rtn.data[i].index].fdEndDate;
						var fdPersonId = params[rtn.data[i].index].fdPersonId;
						$("#TABLE_DocList_fdDetailList_Form tr").each(function(index){
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
						$("#TABLE_DocList_fdDetailList_Form tr").each(function(index){
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
					if(rtn.data[i].status=='2'&&rtn.data[i].submit=='0'&&pass){
						jqtoast(fsscLang['fssc-expense:tips.standard.over'].replace("{row}",rtn.data[i].index*1+1));
						pass = false;
					}
					$("#fdDetailListId>li").eq(i).find(".fdStandardStatus").attr("class","fdStandardStatus");
					var status = "";
					if(rtn.data[i].status=='2'){
						status = "超标准";
					}else if(rtn.data[i].status=='1'){
						status = "标准内";
					}else{
						status = "无标准";
					}
					$("[name='fdDetailList_Form["+rtn.data[i].index+"].fdStandardStatus']").val(rtn.data[i].status);
					if(rtn.data[i].info){
						$("[name='fdDetailList_Form["+rtn.data[i].index+"].fdStandardInfo']").val((rtn.data[i].info).replace(/\"/g,"'"));
					}
					$("#fdDetailListId>li").eq(i).find(".fdStandardStatus").html(status);
					$("#fdDetailListId>li").eq(i).find(".fdStandardStatus").addClass("ld-newApplicationForm-travelInfo-top-buget-"+rtn.data[i].status);
				}
			}
		});
		return pass;
	});
	
	
	window.FSSC_ShowFeeInfo = function(index,info){
		var total = $("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val()*1;
		//保存事前信息
		$("[name$='["+index+"].fdFeeInfo']").val(JSON.stringify(info).replace(/\"/g,"'"));
		//迭代所有明细，统计相同事前的金额
		$("#TABLE_DocList_fdDetailList_Form tr").each(function(i){
			if(i>=index){
				return;
			}
			var fee = $(this).find("[name*=fdFeeInfo]").val();
			var money = $(this).find("[name*=fdBudgetMoney]").val()*1;
			if(fee){
				fee = JSON.parse(fee.replace(/\'/g,"\""));
				//如果当前明细与该明细占用了同一事前台账，需要计算累计金额
				if(FSSC_IsContainsFee(info,fee)){
					total = numAdd(money,total);
				}
			}
		});
		for(var i=0;i<info.length;i++){
			total = numSub(total,info[i].fdUsableMoney);
		}
		return total;
	}
	
	window.FSSC_IsContainsFee = function(fee,info){
		for(var i=0;i<fee.length;i++){
			for(var k=0;k<info.length;k++){
				if(fee[i].fdLedgerId===info[k].fdLedgerId){
					return true;
				}
			}
		}
		return false;
	}
	
	//校验费用类型是否必须要有预算
	Com_Parameter.event.submit.push(function(){
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		var pass = true,exp=[],ext='|',docTemplateId = Com_GetUrlParameter(window.location.href,'docTemplate'),fdCompanyId = $("[name=fdCompanyId]").val();
		if(!docTemplateId){//编辑链接不带分类ID，从input获取
			docTemplateId=$("[name='fdTemplateId']").val();
		}
		$("#TABLE_DocList_fdDetailList_Form>tbody>tr").each(function(){
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
					if(ext.indexOf(name)==-1){
						exp.push(name);
						ext+=name+"|";
					}
					pass = false;
				}
			}
		});
		if(!pass){
			jqtoast(fsscLang['fssc-expense:tips.expense.item.need.budget'].replace("{0}",exp.join("、")));
		}
		return pass;
	});





