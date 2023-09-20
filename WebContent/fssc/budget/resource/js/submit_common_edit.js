seajs.use(['lui/jquery','lui/dialog','lang!fssc-budget'],function($,dialog,lang){
	//流程提交校验公司预算币和预算数据币种汇率转换配置
	Com_Parameter.event["submit"].push(function(){ 
		//暂存不作校验
		if($("[name=docStatus]").val()=='10'){
			return true;
		}
		var pass=true;
		var params = [],detailId="fdDetailList_Form",len = $("#TABLE_DocList_"+detailId+">tbody>tr:gt(0)").length;
		if(!len){
			detailId="fd_detail_Form";
			len = $("#TABLE_DocList_"+detailId+">tbody>tr:gt(0)").length;  //立项、立项变更
		}
		var fdCompanyId=$("[name='fdCompanyId']").val();
		for(var i=0;i<len;i++){
			var param = {
				fdBudgetInfo:$("[name='"+detailId+"["+i+"].fdBudgetInfo']").val(),
			}
			params.push(param);
		}
		$.post(
				Com_Parameter.ContextPath+'fssc/budget/fssc_budget_data/fsscBudgetData.do?method=checkBudgetExchangeRate',
				{params:JSON.stringify(params),fdCompanyId:fdCompanyId},
				function(rtn){
					rtn = JSON.parse(rtn);
					if(rtn.msg){
						dialog.alert(rtn.msg);
						pass=false;
					}
				}
			);
		return pass;
	 });
})