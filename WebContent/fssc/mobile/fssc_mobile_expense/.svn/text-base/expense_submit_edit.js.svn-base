	//流程提交校验公司预算币和预算数据币种汇率转换配置
	Com_Parameter.event["submit"].push(function(){ 
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		var pass=true;
		var params = [],len = $("#TABLE_DocList_fdDetailList_Form").find("input[name$='.fdBudgetInfo']").length;
		var fdCompanyId=$("[name='fdCompanyId']").val();
		for(var i=0;i<len;i++){
			var param = {
				fdBudgetInfo:$("[name='fdDetailList_Form["+i+"].fdBudgetInfo']").val(),
			}
			params.push(param);
		}
		$.post(
				Com_Parameter.ContextPath+'fssc/budget/fssc_budget_data/fsscBudgetData.do?method=checkBudgetExchangeRate',
				{params:JSON.stringify(params),fdCompanyId:fdCompanyId},
				function(rtn){
					rtn = JSON.parse(rtn);
					if(rtn.msg){
						pass=false;
						jqalert({
				              title:'',
				              content:rtn.msg,
				              yestext:fsscLang['button.ok']
				          })
					}
				}
			);
		return pass;
	 });