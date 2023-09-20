seajs.use(['lui/dialog','lang!fssc-expense'],function(dialog,lang){
	//改变费用公司时,清空相关信息
	window.clearDetailWhenCompanyChanged = function(rtn){
		var fdCompanyId = $("[name=fdCompanyId]").val(),fdCompanyName = $("[name=fdCompanyName]").val(),fdCompanyIdOld = $("[name=fdCompanyIdOld]").val();
		if(fdCompanyId!=fdCompanyIdOld){
			dialog.confirm(lang['tips.switchCompany.clearInfo'],function(val){
				if(val){
					$("[name=fdCompanyIdOld]").val(fdCompanyId);
					$("[name=fdCompanyNameOld]").val(fdCompanyName);
					$("[name=fdCostCenterId]").val('');	//清空成本中心
					$("[name=fdCostCenterName]").val('');
					$("[name=fdVoucherTypeId]").val('');	//清空凭证类型
					$("[name=fdVoucherTypeName]").val('');
					$("[name=fdCurrencyId]").val('');	//清空币种
					$("[name=fdCurrencyName]").val('');
					//重新带出默认币种
					var fdCurrencyId = '',fdCurrencyName='',fdRate = '',fdBudgetRate='';
					data = new KMSSData();
					data.AddBeanData("eopBasedataCompanyService&type=getStandardCurrencyInfo&authCurrency=true&fdCompanyId="+fdCompanyId);
					data = data.GetHashMapArray();
					if(data.length>0){
						$("[name=fdCurrencyId]").val(data[0].fdCurrencyId);
						$("[name=fdCurrencyName]").val(data[0].fdCurrencyName);
					}
					//清空明细
					$("#TABLE_DocList_fdDetailList_Form tr:gt(0)").each(function(){
						$(this).find("[name$=fdExpenseItemId]").val("");	//清空费用类型
						$(this).find("[name$=fdExpenseItemName]").val("");
						$(this).find("[name$=fdAccountId]").val("");	//清空会计科目
						$(this).find("[name$=fdAccountName]").val("");
						$(this).find("[name$=fdCostCenterId]").val("");	//清空成本中心
						$(this).find("[name$=fdCostCenterName]").val("");
						$(this).find("[name$=fdCashFlowId]").val("");	//清空现金流量项目
						$(this).find("[name$=fdCashFlowName]").val("");
						$(this).find("[name$=fdProjectId]").val("");	//清空项目
						$(this).find("[name$=fdProjectName]").val("");
					});
					
					window.FSSC_ReloadCostCenter();
				}else{
					$("[name=fdCompanyId]").val(fdCompanyIdOld);
					$("[name=fdCompanyName]").val($("[name=fdCompanyNameOld]").val());
					window.FSSC_ReloadCostCenter()
				}
			})
		}else{
			window.FSSC_ReloadCostCenter();
		}
	}
	
	window.FSSC_ReloadCostCenter = function(){
		var fdCompanyId = $("[name=fdCompanyId]").val();
		//重新带出成本中心
		var data = new KMSSData();
		data.AddBeanData("fsscExpenseDataService&type=getDefaultCostCenter&fdCompanyId="+fdCompanyId+"&fdPersonId="+$("[name=fdClaimantId]").val());
		data = data.GetHashMapArray();
		if(data.length>0){
			$("[name=fdCostCenterId]").val(data[0].fdId);
			$("[name=fdCostCenterName]").val(data[0].fdName);
		}
	}
	
})
