//流程提交校验公司预算币和预算数据币种汇率转换配置
Com_Parameter.event["submit"].push(function(){ 
	//暂存不作校验
	if($("[name=docStatus]").val()=='10'){
		return true;
	}
	var fdMappFeild=$("[name='fdMappFeild']").val();
 	if(fdMappFeild){
 		fdMappFeild=JSON.parse(fdMappFeild); 
 	}
 	var budgetStatusId=fdMappFeild["fdRuleId"];
 	if(!budgetStatusId){//无设置预算，不校验预算`
 		return true;
 	}
	var budgetInfoId=budgetStatusId.substring(0,budgetStatusId.lastIndexOf('status'))+'info';
  	var params = [],len = $("#TABLE_DL_"+fdMappFeild['fdTableId']).find("[name*='."+budgetInfoId.split('.')[1]+"']").length;
  	if(formInitData['budgetMatchList']){
  		len=1;  //获取主表信息，一次
  	}
  	var pass=true;
	var fdCompanyId=getFieldValue(fdMappFeild["fdCompanyId"],0)
	for(var i=0;i<len;i++){
		var budgetStatusId=fdMappFeild["fdRuleId"];
  		var budgetInfoId=budgetStatusId.substring(0,budgetStatusId.lastIndexOf('status'))+'info';
  		var param = {
  			"fdBudgetInfo":getFieldValue(budgetInfoId,i)
  		}
  		params.push(param);
  	}
	$.post(
			Com_Parameter.ContextPath+'fssc/budget/fssc_budget_data/fsscBudgetData.do?method=checkBudgetExchangeRate',
			{params:JSON.stringify(params),fdCompanyId:fdCompanyId},
			function(rtn){
				rtn = JSON.parse(rtn);
				if(rtn.msg){
					jqalert({
			              title:'',
			              content:rtn.msg,
			              yestext:fsscLang['button.ok']
			          })
					pass=false;
				}
			}
		);
	return pass;
 });
