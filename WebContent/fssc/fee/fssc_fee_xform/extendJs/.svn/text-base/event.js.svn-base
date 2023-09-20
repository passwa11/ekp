/**
 * 公司改变时重新加载成本中心
 * @param rtn
 */
function Designer_Control_Cost_Center_Load(rtn){
	var fdCompanyId = rtn&&rtn.length>0?rtn[0].fdId:'';
	var data = new KMSSData();
	data.AddBeanData("fsscFeeDataService&type=getDefaultCostCenter&fdCompanyId="+fdCompanyId);
	data = data.GetHashMapArray();
	for(var i=0;i<window.Designer_Control_Cost_Center_List.length;i++){
		var id = 'extendDataFormInfo.value('+window.Designer_Control_Cost_Center_List[i]+')',name = 'extendDataFormInfo.value('+window.Designer_Control_Cost_Center_List[i]+'_name)';
		//明细表
		if(id.indexOf(".!{index}.")>-1 && window.DocListFunc_GetParentByTagName){
			var tb = DocListFunc_GetParentByTagName("TABLE",$("[name='"+name.replace("!{index}",0)+"']")[0]);
			if(!tb){
				return;
			}
			var len = tb.rows.length-3;
			for(var k=0;k<len;k++){
				$("[name='"+id.replace("!{index}",k)+"']").val("");
				$("[name='"+name.replace("!{index}",k)+"']").val("");
				if(data&&data.length>0){
					$("[name='"+id.replace("!{index}",k)+"']").val(data[0].id);
					$("[name='"+name.replace("!{index}",k)+"']").val(data[0].name);
				}
			}
		}else{
			$("[name='"+id+"']").val("");
			$("[name='"+name+"']").val("");
			if(data&&data.length>0){
				$("[name='"+id+"']").val(data[0].id);
				$("[name='"+name+"']").val("").val(data[0].name);
			}
		}
	}
}

/**
 * 公司改变时清空项目
 * @param rtn
 */
function Designer_Control_Project_Load(rtn){
	for(var i=0;i<window.Designer_Control_Project_List.length;i++){
		var id = 'extendDataFormInfo.value('+window.Designer_Control_Project_List[i]+')',name = 'extendDataFormInfo.value('+window.Designer_Control_Project_List[i]+'_name)';
		//明细表
		if(id.indexOf(".!{index}.")>-1 && window.DocListFunc_GetParentByTagName){
			var tb = DocListFunc_GetParentByTagName("TABLE",$("[name='"+name.replace("!{index}",0)+"']")[0]);
			if(!tb){
				return;
			}
			var len = tb.rows.length-3;
			for(var k=0;k<len;k++){
				$("[name='"+id.replace("!{index}",k)+"']").val("");
				$("[name='"+name.replace("!{index}",k)+"']").val("");
			}
		}else{
			$("[name='"+id+"']").val("");
			$("[name='"+name+"']").val("");
		}
	}
}

/**
 * 公司改变时清空职级
 * @param rtn
 */
function Designer_Control_Post_Level_Load(rtn){
	for(var i=0;i<window.Designer_Control_Post_Level_List.length;i++){
		var id = 'extendDataFormInfo.value('+window.Designer_Control_Post_Level_List[i]+')',name = 'extendDataFormInfo.value('+window.Designer_Control_Post_Level_List[i]+'_name)';
		//明细表
		if(id.indexOf(".!{index}.")>-1 && window.DocListFunc_GetParentByTagName){
			var tb = DocListFunc_GetParentByTagName("TABLE",$("[name='"+name.replace("!{index}",0)+"']")[0]);
			if(!tb){
				return;
			}
			var len = tb.rows.length-3;
			for(var k=0;k<len;k++){
				$("[name='"+id.replace("!{index}",k)+"']").val("");
				$("[name='"+name.replace("!{index}",k)+"']").val("");
			}
		}else{
			$("[name='"+id+"']").val("");
			$("[name='"+name+"']").val("");
		}
	}
}

/**
 * 公司改变时清空费用类型
 * @param rtn
 */
function Designer_Control_Expense_Item_Load(rtn){
	for(var i=0;i<window.Designer_Control_Expense_Item_List.length;i++){
		var id = 'extendDataFormInfo.value('+window.Designer_Control_Expense_Item_List[i]+')',name = 'extendDataFormInfo.value('+window.Designer_Control_Expense_Item_List[i]+'_name)';
		//明细表
		if(id.indexOf(".!{index}.")>-1 && window.DocListFunc_GetParentByTagName){
			var tb = DocListFunc_GetParentByTagName("TABLE",$("[name='"+name.replace("!{index}",0)+"']")[0]);
			if(!tb){
				return;
			}
			var len = tb.rows.length-3;
			for(var k=0;k<len;k++){
				$("[name='"+id.replace("!{index}",k)+"']").val("");
				$("[name='"+name.replace("!{index}",k)+"']").val("");
			}
		}else{
			$("[name='"+id+"']").val("");
			$("[name='"+name+"']").val("");
		}
	}
}
/**
 * 公司改变时清空交通工具、舱位
 * @param rtn
 */
function Designer_Control_Vehicle_Berth_Load(){
	for(var i=0;i<window.Designer_Control_Vehicle_Berth_List.length;i++){
		var id = 'extendDataFormInfo.value('+window.Designer_Control_Vehicle_Berth_List[i]+')',name = 'extendDataFormInfo.value('+window.Designer_Control_Vehicle_Berth_List[i]+'_name)';
		var vehicleId = 'extendDataFormInfo.value('+window.Designer_Control_Vehicle_Berth_List[i]+'_vehicle_id)',vehicleName = 'extendDataFormInfo.value('+window.Designer_Control_Vehicle_Berth_List[i]+'_vehicle_name)';
		//明细表
		if(id.indexOf(".!{index}.")>-1 && window.DocListFunc_GetParentByTagName){
			var tb = DocListFunc_GetParentByTagName("TABLE",$("[name='"+name.replace("!{index}",0)+"']")[0]);
			if(!tb){
				return;
			}
			var len = tb.rows.length-3;
			for(var k=0;k<len;k++){
				$("[name='"+id.replace("!{index}",k)+"']").val("");
				$("[name='"+name.replace("!{index}",k)+"']").val("");
				$("[name='"+vehicleId.replace("!{index}",k)+"']").val("");
				$("[name='"+vehicleName.replace("!{index}",k)+"']").val("");
			}
		}else{
			$("[name='"+id+"']").val("");
			$("[name='"+name+"']").val("");
			$("[name='"+vehicleId+"']").val("");
			$("[name='"+vehicleName+"']").val("");
		}
	}
}

/**
 * 公司改变时清空地域
 * @param rtn
 */
function Designer_Control_AreaCategory_Load(rtn){
    for(var i=0;i<window.Designer_Control_AreaCategory_List.length;i++){
        var id = 'extendDataFormInfo.value('+window.Designer_Control_AreaCategory_List[i]+')',name = 'extendDataFormInfo.value('+window.Designer_Control_AreaCategory_List[i]+'_name)';
        //明细表
        if(id.indexOf(".!{index}.")>-1 && window.DocListFunc_GetParentByTagName){
            var tb = DocListFunc_GetParentByTagName("TABLE",$("[name='"+name.replace("!{index}",0)+"']")[0]);
            if(!tb){
                return;
            }
            var len = tb.rows.length-3;
            for(var k=0;k<len;k++){
                $("[name='"+id.replace("!{index}",k)+"']").val("");
                $("[name='"+name.replace("!{index}",k)+"']").val("");
            }
        }else{
            $("[name='"+id+"']").val("");
            $("[name='"+name+"']").val("");
        }
    }
}

/**
 * 公司改变时清空WBS号
 * @param rtn
 */
function Designer_Control_WbsNumber_Load(rtn){
    for(var i=0;i<window.Designer_Control_WbsNumber_List.length;i++){
        var id = 'extendDataFormInfo.value('+window.Designer_Control_WbsNumber_List[i]+')',name = 'extendDataFormInfo.value('+window.Designer_Control_WbsNumber_List[i]+'_name)';
        //明细表
        if(id.indexOf(".!{index}.")>-1 && window.DocListFunc_GetParentByTagName){
            var tb = DocListFunc_GetParentByTagName("TABLE",$("[name='"+name.replace("!{index}",0)+"']")[0]);
            if(!tb){
                return;
            }
            var len = tb.rows.length-3;
            for(var k=0;k<len;k++){
                $("[name='"+id.replace("!{index}",k)+"']").val("");
                $("[name='"+name.replace("!{index}",k)+"']").val("");
            }
        }else{
            $("[name='"+id+"']").val("");
            $("[name='"+name+"']").val("");
        }
    }
}
/**
 * 公司改变时清空内部订单
 * @param rtn
 */
function Designer_Control_InternalOrder_Load(rtn){
    for(var i=0;i<window.Designer_Control_InternalOrder_List.length;i++){
        var id = 'extendDataFormInfo.value('+window.Designer_Control_InternalOrder_List[i]+')',name = 'extendDataFormInfo.value('+window.Designer_Control_InternalOrder_List[i]+'_name)';
        //明细表
        if(id.indexOf(".!{index}.")>-1 && window.DocListFunc_GetParentByTagName){
            var tb = DocListFunc_GetParentByTagName("TABLE",$("[name='"+name.replace("!{index}",0)+"']")[0]);
            if(!tb){
                return;
            }
            var len = tb.rows.length-3;
            for(var k=0;k<len;k++){
                $("[name='"+id.replace("!{index}",k)+"']").val("");
                $("[name='"+name.replace("!{index}",k)+"']").val("");
            }
        }else{
            $("[name='"+id+"']").val("");
            $("[name='"+name+"']").val("");
        }
    }
}
/**
 * 公司改变时重新加载币种
 * @param rtn
 */
function Designer_Control_Currency_Load(rtn){
	if(!rtn){
		return;
	}
	var data = new KMSSData();
	data.AddBeanData("fsscFeeDataService&type=getDefaultCurrency&fdCompanyId="+rtn[0].fdId);
	data = data.GetHashMapArray();
	for(var i=0;i<window.Designer_Control_Currency_List.length;i++){
		var id = 'extendDataFormInfo.value('+window.Designer_Control_Currency_List[i]+')',name = 'extendDataFormInfo.value('+window.Designer_Control_Currency_List[i]+'_name)';
		var costRate = 'extendDataFormInfo.value('+window.Designer_Control_Currency_List[i]+'_cost_rate)',budgetRate = 'extendDataFormInfo.value('+window.Designer_Control_Currency_List[i]+'_budget_rate)';
		//明细表
		if(id.indexOf(".!{index}.")>-1 && window.DocListFunc_GetParentByTagName){
			var tb = DocListFunc_GetParentByTagName("TABLE",$("[name='"+name.replace("!{index}",0)+"']")[0]);
			if(!tb){
				return;
			}
			var len = tb.rows.length-3;
			for(var k=0;k<len;k++){
				$("[name='"+id.replace("!{index}",k)+"']").val("");
				$("[name='"+name.replace("!{index}",k)+"']").val("");
				$("[name='"+costRate.replace("!{index}",k)+"']").val("");
				$("[name='"+budgetRate.replace("!{index}",k)+"']").val("");
				if(data&&data.length>0){
					$("[name='"+id.replace("!{index}",k)+"']").val(data[0].id);
					$("[name='"+name.replace("!{index}",k)+"']").val(data[0].name);
					$("[name='"+costRate.replace("!{index}",k)+"']").val(data[0]['_cost_rate']);
					$("[name='"+budgetRate.replace("!{index}",k)+"']").val(data[0]['_budget_rate']);
				}
			}
		}else{
			$("[name='"+id+"']").val("");
			$("[name='"+name+"']").val("");
			$("[name='"+costRate+"']").val("");
			$("[name='"+budgetRate+"']").val("");
			if(data&&data.length>0){
				$("[name='"+id.replace("!{index}",k)+"']").val(data[0].id);
				$("[name='"+name.replace("!{index}",k)+"']").val(data[0].name);
				$("[name='"+costRate+"']").val(data[0]['_cost_rate']);
				$("[name='"+budgetRate+"']").val(data[0]['_budget_rate']);
			}
		}
	}
}
/**
 * 选择舱位后给交通工具赋值
 * @param rtn 选择的数据
 * @param id 当前控件id
 * @param vehicleId 对应交通工具控件的id
 * @param vehicleName 对应交通工具控件的name
 */
function Designer_Control_Vehicle_Berth_Changed(rtn,id,vehicleId,vehicleName){
	if(!rtn||rtn.length==0){
		return;
	}
	$("[name='extendDataFormInfo.value("+vehicleId+")']").val(rtn[0].fdVehicleId);
	$("[name='extendDataFormInfo.value("+vehicleName+")']").val(rtn[0].fdVehicleName);
	var name = 'extendDataFormInfo.value('+id.replace(/.+\((.+)\)/g,'$1')+'_name)';
	var berth = 'extendDataFormInfo.value('+id.replace(/.+\((.+)\)/g,'$1')+'_berth_id)';
	$("[name='"+name+"']").val(rtn[0].fdName+'('+rtn[0].fdVehicleName+')');
	$("[name='"+berth+"']").val(rtn[0].fdId);
}
Com_AddEventListener(window,'load',Designer_Control_Budget_Rule_Bind_Event);
/**
 * 为指定的字段绑定监听事件进行预算匹配
 */
function Designer_Control_Budget_Rule_Bind_Event(index){
	if(window.Designer_Control_Budget_Rule_Props){
		var fdListenToId = window.Designer_Control_Budget_Rule_Props.fdListenToId.split(";");
		for(var i=0;i<fdListenToId.length;i++){
			var name = fdListenToId[i];
			if(name.indexOf('.')>-1){
				name = name.split(".");
				name = name[name.length-1]
			}
			$(document).on('change','[name$="'+name+')"]',function(i){
				Designer_Control_Budget_Rule_Evnet_Match_Budget(this.value,this);
			})
		}
	}
	if(window.Designer_Control_Standard_Rule_Props){
		var fdListenToId = window.Designer_Control_Standard_Rule_Props.fdListenToId.split(";");
		for(var i=0;i<fdListenToId.length;i++){
			var name = fdListenToId[i];
			if(name.indexOf('.')>-1){
				name = name.split(".");
				name = name[name.length-1]
			}
			$(document).on('change','[name$="'+name+')"]',function(i){
				Designer_Control_Budget_Rule_Evnet_Match_Standard(this.value,this);
			})
		}
	}
}
/**
 * 匹配预算
 * @param v
 * @param e
 */
function Designer_Control_Budget_Rule_Evnet_Match_Budget(v,e){
	var props = window.Designer_Control_Budget_Rule_Props;
	var i = e.name.replace(/.+\.(\d+)\..+/g,'$1'),tb = props.fdMatchTable||'',fdMatchType = props.fdMatchType;
	var method=$("[name='method_GET']").val();
	var param = {
		'fdCompanyId':Designer_Control_Get_Field_Value(tb,props.fdCompanyId,i),
		'fdProjectId':Designer_Control_Get_Field_Value(tb,props.fdProjectId,i),
		'fdWbsId':Designer_Control_Get_Field_Value(tb,props.fdWbsId,i),
		'fdInnerOrderId':Designer_Control_Get_Field_Value(tb,props.fdInnerOrderId,i),
		'fdExpenseItemId':Designer_Control_Get_Field_Value(tb,props.fdExpenseItemId,i),
		'fdCostCenterId':Designer_Control_Get_Field_Value(tb,props.fdCostCenterId,i),
		'fdPersonId':Designer_Control_Get_Field_Value(tb,props.fdPersonId,i,true),
		'fdDeptId':Designer_Control_Get_Field_Value(tb,props.fdDeptId,i,true),
		'fdMoney':Designer_Control_Get_Field_Value(tb,props.fdMoneyId,i),
		'fdCurrencyId':Designer_Control_Get_Field_Value(tb,props.fdCurrencyId,i),
		'index':i
	}
	if(method=="view"&&Designer_Control_Get_Field_Value(tb,props.id+'_budget_info',i)){
		param[i]['fdBudgetInfo']=Designer_Control_Get_Field_Value(tb,props.id+'_budget_info',i);
	}
	$.post(
		Com_Parameter.ContextPath+'fssc/fee/fssc_fee_main/fsscFeeMain.do?method=matchBudget',
		{data:JSON.stringify(param)},
		function(rtn){
			rtn = JSON.parse(rtn);
			//匹配失败
			if(rtn.result=='failure'||rtn.budget.result=='0'){
				seajs.use(['lui/dialog'],function(dialog){
					dialog.alert(rtn.message?rtn.message:rtn.budget.errorMessage);
				})
				Designer_Control_Budget_Rule_Evnet_Show_Budget(i);
				return;
			}
			//未匹配到预算，校验该费用类型是否必须有预算
			if(!rtn.budget.data||rtn.budget.data.length==0){
				Designer_Control_Budget_Rule_Evnet_Show_Budget(i);
				return;
			}
			Designer_Control_Budget_Rule_Evnet_Show_Budget(i,rtn.budget.data);
		}
	);
}
/**
 * 根据配置获取对应字段的值
 * @param mapp
 * @param name
 * @param index
 */
function Designer_Control_Get_Field_Value(tb,name,index,isObject){
	index = index==null?"":index;
	var detail = false;
	if(name.indexOf('.')>-1){
		name = name.replace(/.+\.(.+)/,'$1');
		detail = true;
	}
	var selector = "[name='extendDataFormInfo.value(";
	if(detail){
		selector+=tb+"."+index+".";
	}
	selector+=name;
	if(isObject){
		selector+=".id";
	}
	selector+=")']";
	return $(selector).val();
}
function Designer_Control_Get_Field_Name(property,index){
	if(isNaN(index)){
		return property;
	}
	return index+"."+property;
}
/**
 * 显示预算
 * @param index
 * @param data
 */
function Designer_Control_Budget_Rule_Evnet_Show_Budget(index,info){
	var props = window.Designer_Control_Budget_Rule_Props;
	var fdBudgetShowType = props.fdShowType;
	$("[name$='"+Designer_Control_Get_Field_Name(props.id+"_budget_status",index)+"']").val("");
	//没有预算信息,显示为无预算
	if(!info){
		$("[name*='"+Designer_Control_Get_Field_Name(props.id+"_budget_status",index)+"']").val("0");
		var id = 'budget_status',name = props.id+"_budget_status",mon = "budget_money";
		if(!isNaN(index)){
			id += "_"+index;
			mon += "_"+index;
			name = index + "." +name;
		}
		if(fdBudgetShowType=='1'){//显示图标
			$("#"+id).attr("class","budget_container");
			$("#"+id).addClass("budget_status_0");
			$("#"+id).unbind("mouseover");//去掉悬浮框
			seajs.use(['lang!fssc-fee'],function(lang){
				$("#"+id).attr("title",lang['py.budget.0']);
			})
		}else{//显示金额
			var html = [];
			seajs.use(['lang!fssc-fee'],function(lang){
				if(props.fdShowMoney.indexOf("1")>-1){
					html.push(lang['py.money.total'],0,"<br>");
				}
				if(props.fdShowMoney.indexOf("2")>-1){
					html.push(lang['py.money.used'],0,"<br>");
				}
				if(props.fdShowMoney.indexOf("3")>-1){
					html.push(lang['py.money.using'],0,"<br>");
				}
				if(props.fdShowMoney.indexOf("4")>-1){
					html.push(lang['py.money.usable']+"<span class='",mon,"'>",0,"</span>");
				}
			})
			$("#"+id).html(html.join(''));
			$("[name*='"+name+"']").val(0);
		}
	}else{
		var budgetInfo = {},fdBudgetStatus = '1';
		var id = 'budget_status',name = props.id+"_budget_status",mon = "budget_money";
		if(!isNaN(index)){
			id += "_"+index;
			mon += "_"+index;
			name = index + "." +name;
		}
		var fdBudgetMoney = $("[name*='"+Designer_Control_Get_Field_Name(props.fdMoneyId.replace(/.+\.(.+)$/g,'$1'),index)+"']").val();
		var fdBudgetRate = $("[name*='"+Designer_Control_Get_Field_Name(props.fdCurrencyId.replace(/.+\.(.+)$/g,'$1')+"_budget_rate",index)+"']").val()||1;
		$("[name*='"+Designer_Control_Get_Field_Name(props.id+"_budget_info",index)+"']").val(JSON.stringify(info).replace(/\"/g,"'"));
		//如果在主表中
		if(isNaN(index)){
			var fdBudgetInfo = $("[name*='"+props.id+"_budget_info']").val()||'[]';
			fdBudgetInfo  = JSON.parse(fdBudgetInfo.replace(/\'/g,'"'));
			for(var i=0;i<fdBudgetInfo.length;i++){
				budgetInfo[fdBudgetInfo[i].fdBudgetId] = numMulti(fdBudgetMoney,fdBudgetRate);
			}
		}else{//如果在明细中，迭代所有明细的预算信息，处理多条明细匹配到同一条预算的情况
			$("#TABLE_DL_"+props.fdMatchTable+" tr:gt(0)").find("[name*="+props.id+"_budget_info]").each(function(){
				var budget = JSON.parse(this.value.replace(/\'/g,'"')||'[]');
				var k = this.name.replace(/\S+\.(\d+)\.\S+/g,'$1');
				var money = $("[name*='."+k+"."+props.fdMoneyId.replace(/.+\.(.+)$/g,'$1')+"']").val()*1;
				var rate = $("[name*='."+k+"."+props.fdCurrencyId.replace(/.+\.(.+)$/g,'$1')+"_budget_rate']").val()*1||1;
				for(var i=0;i<budget.length;i++){
					if(!budgetInfo[budget[i].fdBudgetId]){
						budgetInfo[budget[i].fdBudgetId] = numMulti(money,rate);
					}else{
						budgetInfo[budget[i].fdBudgetId] = numAdd(budgetInfo[budget[i].fdBudgetId],numMulti(money,rate));
					}
				}
			});
		}
		
		var showBudget = null,overBudget = [];
		for(var i=0;i<info.length;i++){
			//获取可用金额最少的预算用于展示
			if(!showBudget||showBudget.fdCanUseAmount>info[i].fdCanUseAmount){
				showBudget = info[i];
			}
			if(budgetInfo[info[i].fdBudgetId]!=null){
				//超出预算
				if(budgetInfo[info[i].fdBudgetId]>info[i].fdCanUseAmount){
					fdBudgetStatus = '2';
				}
			}
		}
		$("[name*='"+name+"']").val(fdBudgetStatus);
		if(fdBudgetShowType=='1'){//显示图标
			$("[name*='"+name+"']").parent().find("[id^='budget_status']").eq(0).attr("class","budget_container");
			$("[name*='"+name+"']").parent().find("[id^='budget_status']").eq(0).addClass("budget_status_"+fdBudgetStatus);
			seajs.use(['lang!fssc-fee'],function(lang){
				$("[name*='"+name+"']").parent().find("[id^='budget_status']").eq(0).attr("title",lang['py.budget.'+fdBudgetStatus]);
			});
			layui.use('layer', function(){
	   			$("div[id='"+id+"']").mouseover(function(){
   	 	  		var info = $(this).parent().find("[name*=budget_info]").val();
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
			var html = [];
			seajs.use(['lang!fssc-fee'],function(lang){
				if(props.fdShowMoney.indexOf("1")>-1){
					html.push(lang['py.money.total'],showBudget.fdTotalAmount,"<br>");
				}
				if(props.fdShowMoney.indexOf("2")>-1){
					html.push(lang['py.money.used'],showBudget.fdAlreadyUsedAmount,"<br>");
				}
				if(props.fdShowMoney.indexOf("3")>-1){
					html.push(lang['py.money.using'],showBudget.fdOccupyAmount,"<br>");
				}
				if(props.fdShowMoney.indexOf("4")>-1){
					html.push(lang['py.money.usable']+"<span class='",mon,"'>",(showBudget.canUseAmountDisplay?showBudget.canUseAmountDisplay:showBudget.fdCanUseAmount),"</span>");
				}
			})
			$("[name*='"+name+"']").parent().find("[id^='budget_status']").eq(0).html(html.join(""));
			$("."+mon).css("color",fdBudgetStatus=='2'?"red":"#333");
		}
	}
}
seajs.use(['lui/dialog','lang!fssc-fee'],function(dialog,lang){
//提交时查找预算
Com_Parameter.event.submit.push(function(){
	//暂存或查看不作校验
	if($("[name=docStatus]").val()=='10'||Com_GetUrlParameter(window.location.href,'method')=='view'){
		return true;
	}
	var method=$("[name='method_GET']").val();
	var props = window.Designer_Control_Budget_Rule_Props;
	//如果没有预算规则控件，不作校验
	if(!props){
		return true;
	}
	var params = [],len = $("#TABLE_DL_"+props.fdMatchTable+">tbody>tr:not(.tr_normal_title)").length,tb = props.fdMatchTable||'';
	len = len?len-2:1;
	var tips = props.fdMatchType=='1'?lang['tips.budget.rule.missing']:lang['tips.budget.rule.missing.detail'];
	var msg = {'fdCompanyId':lang['control.currency.company'],'fdExpenseItemId':lang['control.standardRule.expenseItem'],'fdMoney':lang['control.standardRule.money'],'fdCurrencyId':lang['control.currency.title']}
	if(tb){
		for(var i=0;i<len;i++){
			var param = {
					'fdCompanyId':Designer_Control_Get_Field_Value(tb,props.fdCompanyId,i),
					'fdProjectId':Designer_Control_Get_Field_Value(tb,props.fdProjectId,i),
					'fdWbsId':Designer_Control_Get_Field_Value(tb,props.fdWbsId,i),
					'fdInnerOrderId':Designer_Control_Get_Field_Value(tb,props.fdInnerOrderId,i),
					'fdExpenseItemId':Designer_Control_Get_Field_Value(tb,props.fdExpenseItemId,i),
					'fdCostCenterId':Designer_Control_Get_Field_Value(tb,props.fdCostCenterId,i),
					'fdPersonId':Designer_Control_Get_Field_Value(tb,props.fdPersonId,i,true),
					'fdDeptId':Designer_Control_Get_Field_Value(tb,props.fdDeptId,i,true),
					'fdMoney':Designer_Control_Get_Field_Value(tb,props.fdMoneyId,i),
					'fdCurrencyId':Designer_Control_Get_Field_Value(tb,props.fdCurrencyId,i),
					'index':i
			}
			if(method=="view"&&Designer_Control_Get_Field_Value(tb,props.id+'_budget_info',i)){
				param[i]['fdBudgetInfo']=Designer_Control_Get_Field_Value(tb,props.id+'_budget_info',i);
			}
			params.push(param);
			for( var k in msg){
				if(!param[k]){
					dialog.alert(tips.replace('{0}',i+1).replace('{1}',msg[k]));
					return false;
				}
			}
		}
	}else{
		var param = {
				'fdCompanyId':Designer_Control_Get_Field_Value(tb,props.fdCompanyId,0),
				'fdProjectId':Designer_Control_Get_Field_Value(tb,props.fdProjectId,0),
				'fdWbsId':Designer_Control_Get_Field_Value(tb,props.fdWbsId,0),
				'fdInnerOrderId':Designer_Control_Get_Field_Value(tb,props.fdInnerOrderId,0),
				'fdExpenseItemId':Designer_Control_Get_Field_Value(tb,props.fdExpenseItemId,0),
				'fdCostCenterId':Designer_Control_Get_Field_Value(tb,props.fdCostCenterId,0),
				'fdPersonId':Designer_Control_Get_Field_Value(tb,props.fdPersonId,0,true),
				'fdDeptId':Designer_Control_Get_Field_Value(tb,props.fdDeptId,0,true),
				'fdMoney':Designer_Control_Get_Field_Value(tb,props.fdMoneyId,0),
				'fdCurrencyId':Designer_Control_Get_Field_Value(tb,props.fdCurrencyId,0),
				'index':0
		}
		if(method=="view"&&Designer_Control_Get_Field_Value(tb,props.id+'_budget_info',0)){
			param[0]['fdBudgetInfo']=Designer_Control_Get_Field_Value(tb,props.id+'_budget_info',0);
		}
		params.push(param);
		for( var k in msg){
			if(!param[k]){
				dialog.alert(tips.replace('{0}',i+1).replace('{1}',msg[k]));
				return false;
			}
		}
	}
	
		
	
	var pass = true;
	$.ajax({
		url:Com_Parameter.ContextPath+'fssc/fee/fssc_fee_main/fsscFeeMain.do?method=getBudgetData',
		data:$.param({"data":JSON.stringify(params)},true),
		dataType:'json',
		type:'post',
		async:false,
		success:function(rtn){
			if(tb){
				var money = {};
				//校验单条明细是否超预算
				for(var i=0;i<rtn.length;i++){
					if(rtn[i].budget.success=='0'&&pass){
						dialog.alert(rtn[i].budget.success);
						pass = false;
						continue;
					}
					var rate = $("[name*='."+rtn[i].fdDetailId+"."+props.fdCurrencyId.replace(/.+\.(.+)$/g,'$1')+"_budget_rate']").val()*1;
					var budget = rtn[i].budget.data||[];
					$("[name='extendDataFormInfo.value("+tb+"."+i+"."+props.id+"_budget_info)']").val(JSON.stringify(budget).replace(/\"/g,"'"));
					var applyMoney = params[rtn[i].fdDetailId].fdMoney;
					applyMoney = numMulti(applyMoney,rate);
					for(var k=0;k<budget.length;k++){
						//弹性控制，需要计算弹性比例
						if(budget[k].fdRule=='3'){
							budget[k].fdElasticMoney = numDiv(numMulti(budget[k].fdTotalAmount,budget[k].fdElasticPercent),100);
						}else{
							budget[k].fdElasticMoney = 0;
						}
						if(money[budget[k].fdBudgetId]){
							money[budget[k].fdBudgetId].fdApplyMoney = numAdd(money[budget[k].fdBudgetId].fdApplyMoney,applyMoney*1);
							money[budget[k].fdBudgetId].fdDetailId.push(rtn[i].fdDetailId)
						}else{
							money[budget[k].fdBudgetId] = {
								fdApplyMoney:applyMoney,
								fdCanUseMoney : budget[k].fdCanUseAmount*1,
								fdDetailId:[rtn[i].fdDetailId],
								fdRule : budget[k].fdRule,
								fdElasticMoney : budget[k].fdElasticMoney
							}
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
					if(money[i].fdRule!='2'&&money[i].fdApplyMoney>numAdd(money[i].fdCanUseMoney,money[i].fdElasticMoney)&&pass){
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
				var fdBudgetShowType = props.fdShowType;
				for(var i=0;i<len;i++){
					status[i] = status[i]?status[i]:'0';
					$("[name='extendDataFormInfo.value("+tb+"."+i+"."+props.id+"_budget_status)']").val(status[i]);
					var budget = $("[name='extendDataFormInfo.value("+tb+"."+i+"."+props.id+"_budget_info)']").val();
					if(fdBudgetShowType=='1'){
						$("[name='extendDataFormInfo.value("+tb+"."+i+"."+props.id+"_budget_status)']").parent().find("[id^='budget_status_']").eq(0).attr("class","budget_container");
						$("[name='extendDataFormInfo.value("+tb+"."+i+"."+props.id+"_budget_status)']").parent().find("[id^='budget_status_']").eq(0).addClass("budget_status_"+status[i]);
						seajs.use(['lang!fssc-fee'],function(lang){
							$("[name='extendDataFormInfo.value("+tb+"."+i+"."+props.id+"_budget_status)']").parent().find("[id^='budget_status_']").eq(0).attr("title",lang['py.budget.'+status[i]]);
						});
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
							var html = [];
							if(props.fdShowMoney.indexOf("1")>-1){
								html.push(lang['py.money.total'],showBudget.fdTotalAmount,"<br>");
							}
							if(props.fdShowMoney.indexOf("2")>-1){
								html.push(lang['py.money.used'],showBudget.fdAlreadyUsedAmount,"<br>");
							}
							if(props.fdShowMoney.indexOf("3")>-1){
								html.push(lang['py.money.using'],showBudget.fdOccupyAmount,"<br>");
							}
							if(props.fdShowMoney.indexOf("4")>-1){
								html.push(lang['py.money.usable']+"<span class='budget_money_",i,"'>",(showBudget.canUseAmountDisplay?showBudget.canUseAmountDisplay:showBudget.fdCanUseAmount),"</span>");
							}
							$("[name='extendDataFormInfo.value("+tb+"."+i+"."+props.id+"_budget_status)']").parent().find("[id^='budget_status_']").eq(0).html(html.join(''));
							$(".budget_money_"+i).css("color",status[i]=='2'?"red":"#333");
						}
					}
				}
			}else{
				if(rtn[0].budget.success=='0'&&pass){
					dialog.alert(rtn[0].budget.success);
					pass = false;
					return;
				}
				var rate = $("[name*='"+props.fdCurrencyId.replace(/.+\.(.+)$/g,'$1')+"_budget_rate']").val()*1;
				var budget = rtn[0].budget.data||[];
				$("[name='extendDataFormInfo.value("+props.id+"_budget_info)']").val(JSON.stringify(budget).replace(/\"/g,"'"));
				var applyMoney = numMulti(params[0].fdMoney,rate);
				var fdBudgetStatus = "0";
				for(var i=0;i<budget.length;i++){
					fdBudgetStatus=fdBudgetStatus=="0"?fdBudgetStatus = "1":fdBudgetStatus;
					if(budget[i].fdRule=='3'){
						budget[i].fdElasticMoney = numDiv(numMulti(budget[i].fdTotalAmount,budget[i].fdElasticPercent),100);
					}else{
						budget[i].fdElasticMoney = 0;
					}
					if(applyMoney>budget[i].fdCanUseAmount){
						fdBudgetStatus = "2";
						if(budget[i].fdRule!='2'&&applyMoney>numAdd(budget[i].fdCanUseAmount,budget[i].fdElasticMoney)){
							dialog.alert(lang['tips.budget.over.main'])
							pass = false;
						}
					}
				}
				$("[name='extendDataFormInfo.value("+props.id+"_budget_status)']").val(fdBudgetStatus);
				//显示预算状态
				if(!pass){
					if(fdBudgetShowType=='1'){
						$("[name='extendDataFormInfo.value("+props.id+"_budget_status)']").parent().find("[id^='budget_status']").eq(0).attr("class","budget_container");
						$("[name='extendDataFormInfo.value("+props.id+"_budget_status)']").parent().find("[id^='budget_status']").eq(0).addClass("budget_status_"+fdBudgetStatus);
						seajs.use(['lang!fssc-fee'],function(lang){
							$("[name='extendDataFormInfo.value("+props.id+"_budget_status)']").parent().find("[id^='budget_status']").eq(0).attr("title",lang['py.budget.'+fdBudgetStatus]);
						});
					}else{
						var showBudget = null;
						for(var k=0;k<budget.length;k++){
							if(!showBudget||showBudget.fdCanUseAmount<budget[k].fdCanUseAmount){
								showBudget = budget[k];
							}
						}
						showBudget = showBudget?showBudget:{fdTotalAmount:0,fdOccupyAmount:0,fdAlreadyUsedAmount:0,fdCanUseAmount:0,canUseAmountDisplay:0};
						if(showBudget){
							var html = [];
							if(props.fdShowMoney.indexOf("1")>-1){
								html.push(lang['py.money.total'],showBudget.fdTotalAmount,"<br>");
							}
							if(props.fdShowMoney.indexOf("2")>-1){
								html.push(lang['py.money.used'],showBudget.fdAlreadyUsedAmount,"<br>");
							}
							if(props.fdShowMoney.indexOf("3")>-1){
								html.push(lang['py.money.using'],showBudget.fdOccupyAmount,"<br>");
							}
							if(props.fdShowMoney.indexOf("4")>-1){
								html.push(lang['py.money.usable']+"<span class='budget_money_",0,"'>",(showBudget.canUseAmountDisplay?showBudget.canUseAmountDisplay:showBudget.fdCanUseAmount),"</span>");
							}
							$("[name='extendDataFormInfo.value("+props.id+"_budget_status)']").parent().find("[id^='budget_status']").eq(0).html(html.join(''));
							$(".budget_money_"+0).css("color",fdBudgetStatus=='2'?"red":"#333");
						}
					}
				}
			}
			
		}
	})
	return pass;
});
Com_Parameter.event.submit.push(function(){
	//暂存不作校验
	if($("[name=docStatus]").val()=='10'||Com_GetUrlParameter(window.location.href,'method')=='view'){
		return true;
	}
	var pass = true;
	var props = window.Designer_Control_Budget_Rule_Props;
	//如果没有预算规则控件，不作校验
	if(!props){
		return true;
	}
	$("[name*='"+props.id+"_budget_info']").each(function(){
		if(!pass||this.name.indexOf('index')>-1){
			return;
		}
		//未匹配到预算，校验该费用类型是否必须有预算
		if(!this.value||this.value=='[]'){
			var index = this.name.replace(/\S+\.(\d+)\.\S+/,'$1');
			var data = new KMSSData();
			var fdCompanyId = $("[name*='"+props.fdCompanyId+"']").val();
			var fdExpenseItemId = "";
			if(props.fdMatchTable){
				fdExpenseItemId = $("[name*='."+index+"."+props.fdExpenseItemId.replace(/\S*\.(\S+)/,'$1')+"']").val();
			}else{
				fdExpenseItemId = $("[name*='"+props.fdExpenseItemId+"']").val();
			}
			var docTemplateId = $("[name=docTemplateId]").val();
			data.AddBeanData("fsscFeeExpenseItemService&fdCompanyId="+fdCompanyId+"&fdExpenseItemId="+fdExpenseItemId+"&docTemplateId="+docTemplateId);
			data = data.GetHashMapArray();
			if(data&&data.length>0){
				if(data[0].result=='true'){
					var fdExpenseItemName = $("[name*='"+index+props.fdExpenseItemId.replace(/\S*\.(\S+)/,'$1')+"_name']").val();
					if(props.fdMatchTable){
						fdExpenseItemName = $("[name*='"+index+"."+props.fdExpenseItemId.replace(/\S*\.(\S+)/,'$1')+"_name']").val();
						dialog.alert(lang['tips.expense.budgetRequired'].replace("{1}",fdExpenseItemName).replace("{0}",index*1+1));
					}else{
						dialog.alert(lang['tips.expense.budgetRequired.main']);
					}
					
					pass = false;
				}
			}
		}
	})
	return pass;
})
//校验标准
Com_Parameter.event.submit.push(function(){
	//暂存不作校验
	if($("[name=docStatus]").val()=='10'||Com_GetUrlParameter(window.location.href,'method')=='view'){
		return true;
	}
	var props = window.Designer_Control_Standard_Rule_Props;
	//如果没有预算规则控件，不作校验
	if(!props){
		return true;
	}
	var params = [],len = $("#TABLE_DL_"+props.fdMatchTable+">tbody>tr:not(.tr_normal_title)").length-1;
	var tb = props.fdMatchTable||'';
	if(props.fdMatchType=='1'){//标准在主表
		params.push({
			'fdCompanyId':Designer_Control_Get_Field_Value(tb,props.fdCompanyId,0),
			'fdExpenseItemId':Designer_Control_Get_Field_Value(tb,props.fdExpenseItemId,0),
			'fdPersonId':Designer_Control_Get_Field_Value(tb,props.fdPersonId,0,true),
			'fdDeptId':Designer_Control_Get_Field_Value(tb,props.fdDeptId,0,true),
			'fdMoney':Designer_Control_Get_Field_Value(tb,props.fdMoneyId,0)||0,
			'fdCurrencyId':Designer_Control_Get_Field_Value(tb,props.fdCurrencyId,0),
			'fdTravelDays':Designer_Control_Get_Field_Value(tb,props.fdTravelDays,0),
			'fdVehicleId':Designer_Control_Get_Field_Value(tb,props.fdVehicleId+'_vehicle_id',0),
			'fdBerthId':Designer_Control_Get_Field_Value(tb,props.fdVehicleId+'_berth_id',0),
			'fdAreaId':Designer_Control_Get_Field_Value(tb,props.fdAreaId,0),
			'fdPersonNumber':(Designer_Control_Get_Field_Value(tb,props.fdPersonNumberId,0)||1),
			'index':0
		})
	}else{
		for(var i=0;i<len;i++){
			if($("[name*='."+i+"."+props.fdExpenseItemId.replace(/.+\.(.+)/,"$1")+")']").length==0){
				continue;
			}
			params.push({
				'fdCompanyId':Designer_Control_Get_Field_Value(tb,props.fdCompanyId,i),
				'fdExpenseItemId':Designer_Control_Get_Field_Value(tb,props.fdExpenseItemId,i),
				'fdPersonId':Designer_Control_Get_Field_Value(tb,props.fdPersonId,i,true),
				'fdDeptId':Designer_Control_Get_Field_Value(tb,props.fdDeptId,i,true),
				'fdMoney':Designer_Control_Get_Field_Value(tb,props.fdMoneyId,i)||0,
				'fdCurrencyId':Designer_Control_Get_Field_Value(tb,props.fdCurrencyId,i),
				'fdTravelDays':Designer_Control_Get_Field_Value(tb,props.fdTravelDays,i),
				'fdVehicleId':Designer_Control_Get_Field_Value(tb,props.fdVehicleId+'_vehicle_id',i),
				'fdBerthId':Designer_Control_Get_Field_Value(tb,props.fdVehicleId+'_berth_id',i),
				'fdAreaId':Designer_Control_Get_Field_Value(tb,props.fdAreaId,i),
				'fdPersonNumber':(Designer_Control_Get_Field_Value(tb,props.fdPersonNumberId,i)||1),
				'index':i
			})
		}
	}
	
	var pass = true;
	$.ajax({
		url:Com_Parameter.ContextPath + 'fssc/fee/fssc_fee_main/fsscFeeMain.do?method=getStandardData',
		data:{params:JSON.stringify(params)},
		async:false,
		type:'post',
		success:function(rtn){
			rtn = JSON.parse(rtn);
			if(rtn.result=='failure'){
				dialog.alert(rtn.message);
				pass = false;
				return;
			}
			for(var i=0;i<rtn.data.length;i++){
				if(props.fdMatchType=='1'){//标准在主表
					if(rtn.data[i].status=='2'&&rtn.data[i].submit=='0'&&pass){
						dialog.alert(lang['tips.standard.over.main']);
						pass = false;
					}
					$("[name*='"+props.id+"_standard_status']").val(rtn.data[i].status);
					$("[name*='"+props.id+"_standard_info']").val(rtn.data[i].info);
					if(rtn.data[i].status!='0'){
						$("[name*='."+props.id+"_standard_status']").parent().find("[id^='standard_status']").eq(0).attr("class","budget_container");
						$("[name*='"+props.id+"_standard_status']").parent().find("[id^='standard_status']").eq(0).addClass("standard_status_"+rtn.data[i].status);
						seajs.use(['lang!fssc-fee'],function(lang){
							$("[name*='"+props.id+"_standard_status']").parent().find("[id^='standard_status']").eq(0).attr("title",lang['py.standard.'+rtn.data[i].status]);
						});
					}
				}else{
					$("[name*='."+rtn.data[i].index+"."+props.id+"_standard_status']").val(rtn.data[i].status);
					$("[name*='."+rtn.data[i].index+"."+props.id+"_standard_info']").val(rtn.data[i].info);
					if(rtn.data[i].status=='2'&&rtn.data[i].submit=='0'&&pass){
						dialog.alert(lang['tips.standard.over'].replace("{row}",rtn.data[i].index*1+1));
						pass = false;
					}
					if(rtn.data[i].status!='0'){
						$("[name*='."+rtn.data[i].index+"."+props.id+"_standard_status']").parent().find("[id^='standard_status_']").eq(0).attr("class","budget_container");
						$("[name*='."+rtn.data[i].index+"."+props.id+"_standard_status']").parent().find("[id^='standard_status_']").eq(0).addClass("standard_status_"+rtn.data[i].status);
						seajs.use(['lang!fssc-fee'],function(lang){
							$("[name*='."+rtn.data[i].index+"."+props.id+"_standard_status']").parent().find("[id^='standard_status_']").eq(0).attr("title",lang['py.standard.'+rtn.data[i].status]);
						});
					}
				}
			}
		}
	});
	return pass;
});
})
Com_IncludeFile("data.js");


//匹配标准
function Designer_Control_Budget_Rule_Evnet_Match_Standard(v,e){
	var props = window.Designer_Control_Standard_Rule_Props;
	//如果没有预算规则控件，不作校验
	if(!props){
		return true;
	}
	var i = e.name.replace(/.+\.(\d+)\..+/g,"$1"),params=[];
	var tb = props.fdMatchTable||'';
	var params = [{
		'fdCompanyId':Designer_Control_Get_Field_Value(tb,props.fdCompanyId,i||0),
		'fdExpenseItemId':Designer_Control_Get_Field_Value(tb,props.fdExpenseItemId,i||0),
		'fdPersonId':Designer_Control_Get_Field_Value(tb,props.fdPersonId,i||0,true),
		'fdDeptId':Designer_Control_Get_Field_Value(tb,props.fdDeptId,i||0,true),
		'fdMoney':Designer_Control_Get_Field_Value(tb,props.fdMoneyId,i||0)||0,
		'fdCurrencyId':Designer_Control_Get_Field_Value(tb,props.fdCurrencyId,i||0),
		'fdTravelDays':Designer_Control_Get_Field_Value(tb,props.fdTravelDays,i||0),
		'fdBerthId':Designer_Control_Get_Field_Value(tb,props.fdVehicleId,i||0),
		'fdAreaId':Designer_Control_Get_Field_Value(tb,props.fdAreaId,i||0),
		'fdPersonNumber':(Designer_Control_Get_Field_Value(tb,props.fdPersonNumberId,i||0)||1),
		'index':i||0
	}]
	if(!params[0].fdCompanyId||!params[0].fdExpenseItemId){
		return;
	}
	var pass = true;
	$.ajax({
		url:Com_Parameter.ContextPath + 'fssc/fee/fssc_fee_main/fsscFeeMain.do?method=getStandardData',
		data:{params:JSON.stringify(params)},
		async:false,
		type:'post',
		success:function(rtn){
			rtn = JSON.parse(rtn);
			if(rtn.result=='failure'){
				seajs.use(['lui/dialog'],function(dialog){
					dialog.alert(rtn.message);
				})
				return;
			}
			seajs.use(['lui/dialog','lang!fssc-fee'],function(dialog,lang){
			for(var k=0;k<rtn.data.length;k++){
				if(props.fdMatchType=='1'){//标准在主表
					$("[name*='"+props.id+"_standard_status']").val(rtn.data[k].status);
					$("[name*='"+props.id+"_standard_info']").val(rtn.data[k].info);
					if(rtn.data[k].status=='2'&&rtn.data[k].submit=='0'&&pass){
						dialog.alert(lang['tips.standard.over.main']);
						pass = false;
					}
					$("[name*='"+props.id+"_standard_status']").parent().find("[id^='standard_status']").eq(0).attr("class","budget_container");
					$("[name*='"+props.id+"_standard_status']").parent().find("[id^='standard_status']").eq(0).addClass("standard_status_"+rtn.data[k].status);
					seajs.use(['lang!fssc-fee'],function(lang){
						$("[name*='"+props.id+"_standard_status']").parent().find("[id^='standard_status']").eq(0).attr("title",lang['py.standard.'+rtn.data[k].status]);
					});
				}else{
					$("[name*='."+rtn.data[k].index+"."+props.id+"_standard_status']").val(rtn.data[k].status);
					$("[name*='."+rtn.data[k].index+"."+props.id+"_standard_info']").val(rtn.data[k].info);
					if(rtn.data[k].status=='2'&&rtn.data[k].submit=='0'&&pass){
						dialog.alert(lang['tips.standard.over'].replace("{row}",rtn.data[k].index*1+1));
						pass = false;
					}
					$("[name*='."+rtn.data[k].index+"."+props.id+"_standard_status']").parent().find("[id^='standard_status_']").eq(0).attr("class","budget_container");
					$("[name*='."+rtn.data[k].index+"."+props.id+"_standard_status']").parent().find("[id^='standard_status_']").eq(0).addClass("standard_status_"+rtn.data[k].status);
					seajs.use(['lang!fssc-fee'],function(lang){
						$("[name*='."+rtn.data[k].index+"."+props.id+"_standard_status']").parent().find("[id^='standard_status_']").eq(0).attr("title",lang['py.standard.'+rtn.data[k].status]);
					});
				}
				layui.use('layer', function(){
	   	   			$("div[id*='standard_status']").mouseover(function(){
		   	 	  		var info = $(this).parent().find("[name*=standard_info]").val();
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
			})
		}
	});
}


//查看或编辑时默认显示预算状态
Com_AddEventListener(window,'load',function(){
	var props = window.Designer_Control_Budget_Rule_Props;
	//如果没有预算规则控件，不显示
	if(!props){
		return true;
	}
	//预算在主表
	if(props.fdMatchType=='1'){
		var fdBudgetInfo = JSON.parse($("[name='extendDataFormInfo.value("+props.id+"_budget_info)']").val().replace(/\'/g,'"')||'[]');
		fdBudgetInfo = Designer_Control_Get_Show_Budget_Data(fdBudgetInfo);
		var fdBudgetStatus = $("[name='extendDataFormInfo.value("+props.id+"_budget_status)']").val()||'0';
		//显示图标
		if(props.fdShowType=="1"){
			$("#budget_status").attr("class","budget_container");
			$("#budget_status").addClass("budget_status_"+fdBudgetStatus);
			seajs.use(['lang!fssc-fee'],function(lang){
				$("#budget_status").attr("title",lang['py.budget.'+fdBudgetStatus]);
			})
			layui.use('layer', function(){
   	   			$("div[id*='budget_status']").mouseover(function(){
	   	 	  		var info = $(this).parent().find("[name*=budget_info]").val();
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
		}
		//显示金额
		else{
			var html = [];
			seajs.use(['lang!fssc-fee'],function(lang){
				if(props.fdShowMoney.indexOf("1")>-1){
					html.push(lang['py.money.total'],fdBudgetInfo.fdTotalAmount||0,"<br>");
				}
				if(props.fdShowMoney.indexOf("2")>-1){
					html.push(lang['py.money.used'],fdBudgetInfo.fdAlreadyUsedAmount||0,"<br>");
				}
				if(props.fdShowMoney.indexOf("3")>-1){
					html.push(lang['py.money.using'],fdBudgetInfo.fdOccupyAmount||0,"<br>");
				}
				if(props.fdShowMoney.indexOf("4")>-1){
					html.push(lang['py.money.usable']+"<span class='budget_money'>",(fdBudgetInfo.canUseAmountDisplay?fdBudgetInfo.canUseAmountDisplay:fdBudgetInfo.fdCanUseAmount)||0,"</span>");
				}
			})
			$("#budget_status").html(html.join(''));
			$(".budget_money").css("color",fdBudgetStatus=='2'?"red":"#333");
		}
	}
	//预算在明细
	else{
		$("input").filter(function(){return /\S+\.\d+\.\S+_budget_status/.test(this.name)}).each(function(i){
			var fdBudgetInfo = JSON.parse($("[name*='."+i+"."+props.id+"_budget_info)']").val().replace(/\'/g,'"')||'[]');
			fdBudgetInfo = Designer_Control_Get_Show_Budget_Data(fdBudgetInfo);
			var fdBudgetStatus = $("[name*='."+i+"."+props.id+"_budget_status)']").val()||'0';
			//显示图标
			if(props.fdShowType=="1"){
				$("#budget_status_"+i).attr("class","budget_container");
				$("#budget_status_"+i).addClass("budget_status_"+fdBudgetStatus);
				seajs.use(['lang!fssc-fee'],function(lang){
					$("#budget_status_"+i).attr("title",lang['py.budget.'+fdBudgetStatus]);
				})
				if(fdBudgetStatus!='0'){
				layui.use('layer', function(){
					$("#budget_status_"+i).mouseover(function(){
	   	 	  		var info = $(this).parent().find("[name*=budget_info]").val();
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
				}
			}
			//显示金额
			else{
				var html = [];
				seajs.use(['lang!fssc-fee'],function(lang){
					if(props.fdShowMoney.indexOf("1")>-1){
						html.push(lang['py.money.total'],fdBudgetInfo.fdTotalAmount||0,"<br>");
					}
					if(props.fdShowMoney.indexOf("2")>-1){
						html.push(lang['py.money.used'],fdBudgetInfo.fdAlreadyUsedAmount||0,"<br>");
					}
					if(props.fdShowMoney.indexOf("3")>-1){
						html.push(lang['py.money.using'],fdBudgetInfo.fdOccupyAmount||0,"<br>");
					}
					if(props.fdShowMoney.indexOf("4")>-1){
						html.push(lang['py.money.usable']+"<span class='budget_money_",i,"'>",(fdBudgetInfo.canUseAmountDisplay?fdBudgetInfo.canUseAmountDisplay:fdBudgetInfo.fdCanUseAmount)||0,"</span>");
					}
				})
				$("#budget_status_"+i).html(html.join(""));
				$(".budget_money_"+i).css("color",fdBudgetStatus=='2'?"red":"#333");
			}
		})
	}
});

//查看或编辑时默认显示标准状态
Com_AddEventListener(window,'load',function(){
	seajs.use(['lang!fssc-fee'],function(lang){
		var props = window.Designer_Control_Standard_Rule_Props;
		//如果没有标准规则控件，不显示
		if(!props){
			return true;
		}
		//标准在主表
		if(props.fdMatchType=='1'){
			var fdStandardInfo = JSON.parse($("[name='extendDataFormInfo.value("+props.id+"_standard_info)']").val().replace(/\'/g,'"')||'{}');
			var fdStandardStatus = $("[name='extendDataFormInfo.value("+props.id+"_standard_status)']").val()||'0';
			//显示图标
			$("#standard_status").attr("class","budget_container");
			$("#standard_status").addClass("standard_status_"+fdStandardStatus);
			$("#standard_status").attr("title",lang['py.standard.'+fdStandardStatus])
			layui.use('layer', function(){
   	   			$("div[id*='standard_status']").mouseover(function(){
	   	 	  		var info = $(this).parent().find("[name*=standard_info]").val();
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
		//标准在明细
		else{
			$("input").filter(function(){return /\S+\.\d+\.\S+_standard_status/.test(this.name)}).each(function(i){
				var fdStandardInfo = JSON.parse($("[name*='."+i+"."+props.id+"_standard_info)']").val().replace(/\'/g,'"')||'{}');
				var fdStandardStatus = $("[name*='."+i+"."+props.id+"_standard_status)']").val()||'0';
				//显示图标
				$("#standard_status_"+i).attr("class","budget_container");
				$("#standard_status_"+i).addClass("standard_status_"+fdStandardStatus);
				seajs.use(['lang!fssc-fee'],function(lang){
					$("#standard_status_"+i).attr("title",lang['py.standard.'+fdStandardStatus])
				});
				layui.use('layer', function(){
	   	   			$("div[id*='standard_status']").mouseover(function(){
		   	 	  		var info = $(this).parent().find("[name*=standard_info]").val();
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
			})
		}
	})
});
function Designer_Control_Get_Show_Budget_Data(budgetData){
	var rtn = {};
	for(var i=0;i<budgetData.length;i++){
		if(!rtn.fdCanUseAmount||rtn.fdCanUseAmount>budgetData[i].fdCanUseAmount){
			rtn = budgetData[i];
		}
	}
	return rtn;
}
//地域选择
function Designer_SelectArea(fdCompanyId,id){
	
}
//改变币种后更新汇率
function Designer_Control_Currency_Evnet_Changed(data,id,params){
	id = id.replace(/\S+\((\S+)\)/g,'$1');
	if(!data){
		return;
	}
	var fdCompanyId = $("[name*='"+params.fdCompanyId+"']").val();
	var kms = new KMSSData();
	kms.AddBeanData("eopBasedataExchangeRateService&authCurrent=true&type=getRateByCurrency&fdCurrencyId="+data[0].fdId+"&fdCompanyId="+fdCompanyId);
	kms = kms.GetHashMapArray();
	if(kms&&kms.length>0){
		$("[name*='"+id+"_budget_rate']").val(kms[0].fdBudgetRate);
		$("[name*='"+id+"_cost_rate']").val(kms[0].fdExchangeRate);
        if(window.Designer_Control_AccountMoney_Props){
            Designer_Control_AccountMoneyCount_Evnet(null,null,id.split(".")[1]);
		}
	}
}
Com_AddEventListener(window,'load',Designer_Control_AccountMoney_Bind_Event);
/**
 * 为指定的字段绑定监听事件进行本币金额计算
 */
function Designer_Control_AccountMoney_Bind_Event(){
    if(window.Designer_Control_AccountMoney_Props){
        var matchType = window.Designer_Control_AccountMoney_Props.matchType;
        if(matchType == "2"){//明细
            var fdCurrencyId = window.Designer_Control_AccountMoney_Props.currencyId.split(".")[1];
            var fdMoneyId = window.Designer_Control_AccountMoney_Props.moneyId.split(".")[1];
            $(document).on('change','[name$="'+fdCurrencyId+')"]',function(i){
                if(i>0)return;
                var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
                var fdMoneyObj = $("[name$='."+index+"."+fdMoneyId+")']");
                Designer_Control_AccountMoneyCount_Evnet(fdMoneyObj.val(), fdMoneyObj[0]);
            })
            $(document).on('change','[name$="'+fdMoneyId+')"]',function(i){
                if(i>0)return;
                Designer_Control_AccountMoneyCount_Evnet(this.value,this);
            })
        }else{
            var fdCurrencyId = window.Designer_Control_AccountMoney_Props.currencyId;
            var fdMoneyId = window.Designer_Control_AccountMoney_Props.moneyId;
            $(document).on('change','[name="extendDataFormInfo.value('+fdCurrencyId+')"]',function(i){
                if(i>0)return;
                Designer_Control_AccountMoneyCount_Evnet(this.value,this);
            })
            $(document).on('change','[name="extendDataFormInfo.value('+fdMoneyId+')"]',function(i){
                if(i>0)return;
                Designer_Control_AccountMoneyCount_Evnet(this.value,this);
            })
        }
    }
}

/**
 * 计算本币金额
 */
function Designer_Control_AccountMoneyCount_Evnet(v, e, i){
    if(window.Designer_Control_AccountMoney_Props){
        var props = window.Designer_Control_AccountMoney_Props;
        var matchType = props.matchType;
        if(matchType == "2"){//明细
            var tb = props.matchTableId;
        	if(i == undefined || i == null){
                i = e.name.replace(/.+\.(\d+)\..+/g,'$1')
			}
            var fdAccountMoneyId = props.id;
            var fdCurrencyId = props.currencyId.split(".")[1];
            var fdMoneyId = props.moneyId.split(".")[1];
            var fdRate = $("[name='extendDataFormInfo.value("+tb+"."+i+"."+fdCurrencyId+"_cost_rate)']").val();
            var fdMoney = $("[name='extendDataFormInfo.value("+tb+"."+i+"."+fdMoneyId+")']").val();
            $("[name='extendDataFormInfo.value("+tb+"."+i+"."+fdAccountMoneyId+")']").val(multiPoint(fdRate, fdMoney));
        }else{
            var fdAccountMoneyId = props.id;
            var fdCurrencyId = props.currencyId;
            var fdMoneyId = props.moneyId;
            var fdRate = $("[name='extendDataFormInfo.value("+fdCurrencyId+"_cost_rate)']").val();
            var fdMoney = $("[name='extendDataFormInfo.value("+fdMoneyId+")']").val();
            $("[name='extendDataFormInfo.value("+fdAccountMoneyId+")']").val(multiPoint(fdRate, fdMoney));
        }
    }
}

Com_AddEventListener(window,'load',Designer_Control_DayCount_Bind_Event);
/**
 * 为指定的字段绑定监听事件进行天数计算
 */
function Designer_Control_DayCount_Bind_Event(){
    if(window.Designer_Control_DayCount_Props){
        var matchType = window.Designer_Control_DayCount_Props.matchType;
        if(matchType == "2"){//明细
            var fdStartDateId = window.Designer_Control_DayCount_Props.startDateId.split(".")[1];
            var fdEndDateId = window.Designer_Control_DayCount_Props.endDateId.split(".")[1];
            $(document).on('change','[name$="'+fdStartDateId+')"]',function(i){
                if(i>0)return;
                Designer_Control_DayCount_Evnet(this.value,this);
            })
            $(document).on('change','[name$="'+fdEndDateId+')"]',function(i){
                if(i>0)return;
                Designer_Control_DayCount_Evnet(this.value,this);
            })
        }else{
            var fdStartDateId = window.Designer_Control_DayCount_Props.startDateId;
            var fdEndDateId = window.Designer_Control_DayCount_Props.endDateId;
            $(document).on('change','[name="extendDataFormInfo.value('+fdStartDateId+')"]',function(i){
                if(i>0)return;
                Designer_Control_DayCount_Evnet(this.value,this);
            })
            $(document).on('change','[name="extendDataFormInfo.value('+fdEndDateId+')"]',function(i){
                if(i>0)return;
                Designer_Control_DayCount_Evnet(this.value,this);
            })
        }
    }
}
/**
 * 天数计算
 */
function Designer_Control_DayCount_Evnet(v,e){
    if(window.Designer_Control_DayCount_Props){
        var props = window.Designer_Control_DayCount_Props;
        var matchType = props.matchType;
        var isPlusOne = props.isPlusOne;//是否加1： 1是 2否
        var fdDaysId = props.id;
        if(matchType == "2"){//明细
            var i = e.name.replace(/.+\.(\d+)\..+/g,'$1'),tb = props.matchTableId;
            var fdStartDateId = props.startDateId.split(".")[1];
            var fdEndDateId = props.endDateId.split(".")[1];
            var fdStartDate = $("[name='extendDataFormInfo.value("+tb+"."+i+"."+fdStartDateId+")']").val();
            var fdEndDate = $("[name='extendDataFormInfo.value("+tb+"."+i+"."+fdEndDateId+")']").val();
            if(!fdStartDate || !fdEndDate){
                $("[name='extendDataFormInfo.value("+tb+"."+i+"."+fdDaysId+")']").val("");
			}else{
                var fdDays = DateMinus(fdStartDate, fdEndDate);
                if(fdDays<0){
                	seajs.use(['lui/dialog','lang!fssc-fee'],function(dialog,lang){
                		dialog.alert(lang['tips.date.invalid'])
                	})
                	e.value = "";
            		return;
                }
                if(isPlusOne == '1'){//加1
                    fdDays += 1;
                }
                $("[name='extendDataFormInfo.value("+tb+"."+i+"."+fdDaysId+")']").val(fdDays).change();
			}
        }else{
            var fdStartDateId = props.startDateId;
            var fdEndDateId = props.endDateId;
            var fdStartDate = $("[name='extendDataFormInfo.value("+fdStartDateId+")']").val();
            var fdEndDate = $("[name='extendDataFormInfo.value("+fdEndDateId+")']").val();
            if(!fdStartDate || !fdEndDate){
                $("[name='extendDataFormInfo.value("+tb+"."+i+"."+fdDaysId+")']").val("");
            }else{
                var fdDays = DateMinus(fdStartDate, fdEndDate);
                if(fdDays<0){
                	seajs.use(['lui/dialog','lang!fssc-fee'],function(dialog,lang){
                		dialog.alert(lang['tips.date.invalid'])
                	})
                	e.value = "";
            		return;
                }
                if(isPlusOne == '1'){//加1
                    fdDays += 1;
                }
                $("[name='extendDataFormInfo.value("+fdDaysId+")']").val(fdDays).change();
            }
        }
    }
}

/**
 * 天数计算（中英文都支持）
 * @param fdStartDate 开始时间
 * @param fdEndDate 结束时间
 * @returns {number}
 * @constructor
 */
function DateMinus(fdStartDate, fdEndDate){
    var startDate = new Date(fdStartDate.replace(/-/g, "/"));
    var endDate = new Date(fdEndDate.replace(/-/g, "/"));
    var days = endDate.getTime() - startDate.getTime();
    var day = parseInt(days / (1000 * 60 * 60 * 24));
    return day;
}
function Designer_Control_ReplaceTag(id){
	$("[name$='"+id.replace(/\w+\.\!\{index\}\.(\w+)/,'$1')+")']").attr("type","text").hide().each(function(){
		var obj = this.parentNode;
		$(obj).find('div.selectitem').css("bottom",0);
		$(this).attr("validate",$(obj).find('input[name*="_name"]').attr("validate")).attr("validator","true").attr("subject",$(obj).find('input[name*="_name"]').attr("subject"));
		$(obj).find('input[name*="_name"]').attr("validate","")
	});
}
$(function(){
	$form.regist({//注册事件实现
		support : function(target){
			return target.type=='currency'||target.type=='company'||target.type=='costCenter'||target.type=='expenseItem'||target.type=='project'||target.type=='wbs'||target.type=='vehicle'||target.type=='area'||target.type=='internalOrder';
		},
		readOnly : function(target, value){
			var element = target.display || target.element;
			var operationDiv = target.root.find(".selectitem");
			if(value == null){
				if(operationDiv.length > 0){
					return !operationDiv.is(':visible');
				}
				return;
			}
			if(operationDiv.length > 0){
				var conDiv = target.root.find("div:eq(0)");
				if(value){
					operationDiv.hide();
					conDiv.removeClass("inputselectsgl").attr("_onclick",conDiv.attr("onclick")).removeAttr("onclick");
					conDiv.find("input[name*='_name']").css("border","none");
					target.element.attr("cache_validate",target.element.attr("validate")).removeAttr("validate");
					if(conDiv.get(0).nextSibling.className=='txtstrong'){
						$(conDiv.get(0).nextSibling).hide();
					}
				}else{
					operationDiv.show();
					conDiv.addClass("inputselectsgl").attr("onclick",conDiv.attr("_onclick"));
					target.element.attr("validate",target.element.attr("cache_validate"));
					if(conDiv.get(0).nextSibling.className=='txtstrong'){
						$(conDiv.get(0).nextSibling).show();
					}
				}
			}else{
				return false;
			}
		},
		required : function(target, value){
			var element = this.getValidateElement(target);
			if(element && element.length){
				var validate = element.attr('validate');
				if(value){
					if(validate){
						element.attr('validate', validate + ' required');
					}else{
						element.attr('validate', 'required');
					}
					if(target.root.find("span.txtstrong").length>0){
						target.root.find("span.txtstrong").show();
					}else{
						target.root.append("<span class='txtstrong'>*</span>");
					}
				}else{
					if(!validate || validate=='required'){
						element.removeAttr('validate');
					}else{
						element.attr('validate', validate.replace(/(\s*required\s*)/, ''));
					}
					if(target.root.find("span.txtstrong").length>0){
						target.root.find("span.txtstrong").hide();
					}
					// 校验框架BUG
					if(target.root.parent().find(".validation-advice").length>0){
						target.root.parent().find(".validation-advice").hide();
					}
				}
			}else{
				return false;
			}
		}
	});
})
