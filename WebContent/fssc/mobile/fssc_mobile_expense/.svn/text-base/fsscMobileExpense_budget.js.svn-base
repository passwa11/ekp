/**
 * 匹配预算、标准js
 */
	//单行明细匹配预算
	window.FSSC_MatchBudget = function(v,i){
		var fdMoney=$("[name='fdDetailList_Form["+i+"].fdApplyMoney']").val();
		var fdNoTaxMoney=$("[name='fdDetailList_Form["+i+"].fdNoTaxMoney']").val();
		if(!fdNoTaxMoney||fdNoTaxMoney==0){
			fdNoTaxMoney=fdMoney;
		}
		if(!$("input[name='fdCompanyId']").val()){
			jqtoast(fsscLang['fssc-expense:tips.pleaseSelectCompany']);
			return ;
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
			'fdMoney':fdMoney,
			'fdNoTaxMoney':fdNoTaxMoney,
			'fdCurrencyId':$("[name='fdDetailList_Form["+i+"].fdCurrencyId']").val(),
			'fdDetailId':i,
			'fdModelId':$("[name=fdId]").val()
		}
		var fdFeeIds = $("[name=fdFeeIds]").val(),pass = true;
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
							alert(rtn.message?rtn.message:rtn.budget.errorMessage);
							return;
						}
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
							}else{//没有超事前，则不需要再匹配预算
								pass = false;
							}
							$("[name$='["+i+"].fdFeeStatus']").val(fdFeeStatus);
							$("#fdDetailListId>li").eq(i).find(".feeStatus").html(fdFeeStatus=='2'?"超申请":"申请内");
							$("#fdDetailListId>li").eq(i).find(".feeStatus").attr("class","feeStatus ld-newApplicationForm-travelInfo-top-buget-"+fdFeeStatus);
						}else{
							$("[name$='["+i+"].fdFeeInfo']").val('[]');
							$("[name$='["+i+"].fdFeeStatus']").val('0');
							$("#fdDetailListId>li").eq(i).find(".feeStatus").html("无申请");
							$("#fdDetailListId>li").eq(i).find(".feeStatus").attr("class","feeStatus ld-newApplicationForm-travelInfo-top-buget-0");
						}
					}
			});
		}
		if(!pass){
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
					jqtoast(rtn.message?rtn.message:rtn.budget.errorMessage);
					return;
				}
				//未匹配到预算
				if(!rtn.budget.data||rtn.budget.data.length==0){
					FSSC_ShowBudgetInfo(i);
					return;
				}
				console.log(rtn.budget.data);
				FSSC_ShowBudgetInfo(i,rtn.budget.data);
			}
		);
	}
	
	
	//显示预算状态信息
	window.FSSC_ShowBudgetInfo = function(index,info){
		$("#fdDetailListId>li").eq(index).find(".bugetStatus").attr("class","bugetStatus ld-newApplicationForm-travelInfo-top-buget-0");//清除预算信息
		var fdFeeStatus = $("[name$='["+index+"].fdFeeStatus']").val();
		var fdBudgetShowType = $("[name=fdBudgetShowType]").val();
		$("[name$='["+index+"].fdBudgetInfo']").val("[]");
		//没有预算信息,显示为无预算
		if(!info){
			$("[name$='["+index+"].fdBudgetStatus']").val("0");
			$("#fdDetailListId>li").eq(index).find(".bugetStatus").html("无预算");
			$("#fdDetailListId>li").eq(index).find(".bugetStatus").attr("class","bugetStatus ld-newApplicationForm-travelInfo-top-buget-0");
			
		}else{
			var budgetInfo = {},fdBudgetStatus = '1';
			var fdBudgetMoney = $("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val();
			$("[name$='["+index+"].fdBudgetInfo']").val(JSON.stringify(info).replace(/\"/g,"'"));
			//迭代所有明细的预算信息，处理多条明细匹配到同一条预算的情况
			$("#TABLE_DocList_fdDetailList_Form tr").find("[name$=fdBudgetInfo]").each(function(){
				var budget = JSON.parse((this.value||'[]').replace(/\'/g,'"'));
				var k = this.name.replace(/\S+\[(\d+)\]\S+/g,'$1');
				var money = $("[name='fdDetailList_Form["+k+"].fdBudgetMoney']").val()*1;
				
				for(var i=0;i<budget.length;i++){
					//如果累计的明细在当前明细之后，不统计在内
					if(k>index){
						continue;
					}
					if(!budgetInfo[budget[i].fdBudgetId]){
						budgetInfo[budget[i].fdBudgetId] = {money:money,index:[k]};
					}else{
						budgetInfo[budget[i].fdBudgetId].money = numAdd(budgetInfo[budget[i].fdBudgetId].money,money);
						budgetInfo[budget[i].fdBudgetId].index.push(k);
					}
				}
			});
			
			
			var showBudget = null,overBudget = [];
			for(var i=0;i<info.length;i++){
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
					if(budgetInfo[info[i].fdBudgetId].money>info[i].fdCanUseAmount){
						fdBudgetStatus = '2';
					}
				}
			}
			$("[name$='["+index+"].fdBudgetStatus']").val(fdBudgetStatus);
			//显示红灯及提示
			$("#fdDetailListId>li").eq(index).find(".bugetStatus").html(fdBudgetStatus=='2'?"超预算":"预算内");
			$("#fdDetailListId>li").eq(index).find(".bugetStatus").addClass("ld-newApplicationForm-travelInfo-top-buget-"+fdBudgetStatus);
		}
	}
	
	
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
	
	/**
	 * 匹配标准
	 */
	window.FSSC_MathStandard = function(i){
		if(!$("[name=checkVersion]").val()){
			return;
		}
		var params = [],fdExpenseType = $("[name=fdExpenseType]").val();
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
			'fdModelId':$("[name=fdId]").val(),
			'index':i
		})
		//如果是差旅费类型，参数中需要加上地域、舱位等
		//行程独立，从行程明细中获得数据
		var fdIsTravelAlone = $("[name=fdIsTravelAlone]").val();
		if(fdExpenseType=='2'&&fdIsTravelAlone=='true'){
			var travel = {};
			$("#TABLE_DocList_fdTravelList_Form tr").each(function(){
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
				console.log(rtn);
				if(rtn.result=='failure'){
					alert(rtn.message);
					return;
				}
				for(var i=0;i<rtn.data.length;i++){
					if(rtn.data[i].dayCount){//如果是按日标准，需要校验当前单中是否重复报销
						var fdExpenseItemId = params[0].fdExpenseItemId;
						var fdSchemeId = rtn.data[i].fdSchemeId;
						var fdBeginDate = params[0].fdBeginDate;
						var fdEndDate = params[0].fdEndDate;
						var fdPersonId = params[0].fdPersonId;
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
							}
						})
					}
					if(rtn.data[i].monthCount){//如果是月次标准，需要校验当前单中是否重复报销
						var fdExpenseItemId = params[0].fdExpenseItemId;
						var fdSchemeId = rtn.data[i].fdSchemeId;
						var fdPersonId = params[0].fdPersonId;
						var month = params[0].fdEndDate.split('\-')[1];
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
							}
						})
					}
					$("#fdDetailListId>li").eq(rtn.data[i].index).find(".fdStandardStatus").attr("class","fdStandardStatus");
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
					$("#fdDetailListId>li").eq(rtn.data[i].index).find(".fdStandardStatus").html(status);
					$("#fdDetailListId>li").eq(rtn.data[i].index).find(".fdStandardStatus").addClass("ld-newApplicationForm-travelInfo-top-buget-"+rtn.data[i].status);
				}
			}
		});
	}
	
	//校验所选择的事前是否存在在途单据
	window.checkFeeRelation=function(v,o){
		if(v){
			var fdFeeIds=$("[name='fdFeeIds']").val();
			if(fdFeeIds){
					$.ajax({
					url:Com_Parameter.ContextPath + 'fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=checkFeeRelation&fdFeeIds='+fdFeeIds,
					async:false,
					success:function(rtn){
						rtn = JSON.parse(rtn);
						if(!rtn.result){//不允许关闭
							alert(fsscLang['fssc-expense:msg.fee.tips.examineing']);
						}
					}
				});
			}
		}
	};
	window.getTravelDate = function(type,index){
		var fdExpenseType = $("[name=fdExpenseType]").val(),fdIsTravelAlone = $("[name=fdIsTravelAlone]").val();
		//行程独立，从行程明细中获得数据
		if(fdExpenseType=='2'&&fdIsTravelAlone=='true'){
			var travel = $("[name='fdDetailList_Form["+index+"].fdTravel']").val();
			var date = '';
			$("#TABLE_DocList_fdTravelList_Form tr").each(function(){
				if($(this).find("[name$=fdSubject]").val()==travel){
					date=type=='begin'?$(this).find("[name$=fdBeginDate]").val():$(this).find("[name$=fdEndDate]").val();
					return;
				}
			});
			return date;
		}else{//行程合并，从费用明细中获取数据
			return type=='begin'?$("[name='fdDetailList_Form["+index+"].fdStartDate']").val():$("[name='fdDetailList_Form["+index+"].fdHappenDate']").val();
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