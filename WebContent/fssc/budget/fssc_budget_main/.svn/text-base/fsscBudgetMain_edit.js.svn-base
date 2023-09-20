seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str','lui/util/env','lang!fssc-budget','lang!','lang!eop-basedata'], function($, dialog, dialogCommon,strutil,env,lang,comlang,baseLang){
	$(document).ready(function(){
		var width=$(".tempTB").width()*0.945+"px;";
		$("#detailDiv").attr("style","overflow:auto;width:"+width);
		var td_width=($("#TABLE_DocList_fdDetailList_Form >tbody>tr>td").length-2)*50;
		if(td_width>$(".tempTB").width()*0.945){
			$("#TABLE_DocList_fdDetailList_Form").attr('width',td_width+"px;");
		}else{
			$("#TABLE_DocList_fdDetailList_Form").attr('width',width);
		}
	},500);
	window.downTemplate=function(){
		var fdSchemeId=$("[name='fdBudgetSchemeId']").val();
		var url=env.fn.formatUrl('/fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=downTemplate&fdSchemeId='+fdSchemeId)
		window.open(url,"_blank");
	};
	//导入
	window.importData = function(){
		var fdSchemeId=$("[name='fdBudgetSchemeId']").val();
		var fdCompanyId=$("[name='fdCompanyId']").val()||"";
		var fdYear=$("[name='fdYear']").val();
		var url = "/fssc/budget/resource/jsp/fsscBudgetImport.jsp?fdSchemeId="+fdSchemeId+"&fdCompanyId="+fdCompanyId+"&fdYear="+fdYear;
		window.dia = dialog.iframe(url,lang['message.import.title'],
				function(data) {
					if(data){
						var len=$("#TABLE_DocList_fdDetailList_Form>tbody>tr").length-1;
						//重新选择excel清除上次的值
						var arrayData = LUI.toJSON(data);
						var index=len;
						for(var i=0;i<arrayData.length;i++){
							index=len+i;
							DocList_AddRow("TABLE_DocList_fdDetailList_Form");
							var dataJson = arrayData[i];
							//遍历Json串获取其属性,后台设置的key和页面name属性对应
						    for(var key in dataJson){
					            var value=dataJson[key];//key所对应的value 
					            if(key=='fdYearRule'||key=='fdQuarterRule'||key=='fdMonthRule'||key=='fdRule'
					            	||key=='fdYearApply'||key=='fdQuarterApply'||key=='fdMonthApply'||key=='fdApply'){
					            	$("input[name='fdDetailList_Form["+index+"]."+key+"'][value='"+value+"']").attr('checked','true');
					            	if(value=='3'){
					            		$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").attr("style","width:95%;diaplay:block;");
					            		$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").attr("validate","required  range(-100,100)");
					            		$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").attr("readOnly",false);
					        			$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").parent().find("span").attr("style","");
					            	}
					            }else{
					            	if(value.indexOf("@")>-1){//已经存在的预算的值
					            		$("input[name='fdDetailList_Form["+index+"]."+key+"']").val(value.replace('@',''));
					            		$("input[name='fdDetailList_Form["+index+"]."+key+"']").attr('readOnly',true);
					            		$("[name='fdDetailList_Form["+index+"]."+key+"']").parent().find("span").attr("style","");
					            		if(key=='fdYearMoney'){
					            			if(value&&document.getElementById("_xform_fdDetailList_Form["+index+"].fdYearRule"+key)&&key=='fdYearRule'){
							            		document.getElementById("_xform_fdDetailList_Form["+index+"].fdYearRule").parentNode.getElementsByTagName("div")[1].classList.add("div-b");
							            	}
					            			if(value&&document.getElementById("_xform_fdDetailList_Form["+index+"].fdYearApply"+key)&&key=='fdYearApply'){
					            				document.getElementById("_xform_fdDetailList_Form["+index+"].fdYearApply").parentNode.getElementsByTagName("div")[1].classList.add("div-b");
					            			}
					            		}
					            	}else{//正常赋值
					            		$("input[name='fdDetailList_Form["+index+"]."+key+"']").val(value);
					            	}
					            }
						    } 
						    var addressInput = $("[xform-name='mf_fdDetailList_Form["+index+"].fdPersonName']")[0];
						    var addressValues = new Array();
						    if(addressInput){
						    	var addressValues = new Array();
							    addressValues.push({id:$('input[name="fdDetailList_Form['+index+'].fdPersonId"]').val(),name:$('input[name="fdDetailList_Form['+index+'].fdPersonName"]').val()});
								newAddressAdd(addressInput,addressValues,null,true);
						    }
							addressInput = $("[xform-name='mf_fdDetailList_Form["+index+"].fdDeptName']")[0];
							if(addressInput){
								addressValues = new Array();
								addressValues.push({id:$('input[name="fdDetailList_Form['+index+'].fdDeptId"]').val(),name:$('input[name="fdDetailList_Form['+index+'].fdDeptName"]').val()});
								newAddressAdd(addressInput,addressValues,null,true);
							}
						}
					}
				},
				{
					height:'600',
					width:'800',
					"buttons" : [ 
									{
									name : comlang['button.cancel'],
									value : false,
									fn : function(value, dialog) {
											dialog.hide();
										}
									} 
							],
					"content" : {
								scroll :  false
						}
				});				
	}
	//修改公司
	window.changeCompany=function(){
		var fdSchemeId=$("[name='fdBudgetSchemeId']").val();
		var fdCompanyId=$("[name='fdCompanyId']").val();
		var url=env.fn.formatUrl('/fssc/budget/fssc_budget_main/fsscBudgetMain.do?method=add&fdSchemeId='+fdSchemeId+'&fdCompanyId='+fdCompanyId)
		dialog.confirm(lang['message.budget.change.company.confirm'],function(value){
		    if(!value){
		    	//取消还原公司值
		    	$("[name='fdCompanyId']").val($("[name='fdCurrentCompanyId']").val());
		    	$("[name='fdCompanyName']").val($("[name='fdCurrentCompanyName']").val());
				return;
			 }
	    	if(value==true){
	    		window.del_load = dialog.loading();
				Com_OpenWindow(url, '_self');
	    	}
	    });
	};
	
	//选择成本中心组
	window.Fssc_selectCostCenterGroup=function(){
		dialogSelect(false,'eop_basedata_cost_center_group_selectCostCenterGroup',
				'fdDetailList_Form[*].fdCostCenterGroupId','fdDetailList_Form[*].fdCostCenterGroupName',getDetailMoney,{'fdCompanyId':$('[name=fdCompanyId]').val()||"noCompany"});  //无公司值则传入不存在的公司ID限制，只查出公共
	}
	
	//选择成本中心
	window.Fssc_selectCostCenter=function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		dialogSelect(false,'eop_basedata_cost_center_selectCostCenter',
				'fdDetailList_Form[*].fdCostCenterId','fdDetailList_Form[*].fdCostCenterName',getDetailMoney,{'fdCompanyId':$('[name=fdCompanyId]').val()||"noCompany",'fdCostCenterGroupId':$("input[name='fdDetailList_Form["+index+"].fdCostCenterGroupId']").val()||""});
	}
	
	//选择项目
	window.Fssc_selectProject=function(){
		dialogSelect(false,'eop_basedata_project_project',
				'fdDetailList_Form[*].fdProjectId','fdDetailList_Form[*].fdProjectName',getDetailMoney,{'fdCompanyId':$('[name=fdCompanyId]').val()||"noCompany"});
	}
	
	//选择WBS
	window.Fssc_selectWbs=function(){
		dialogSelect(false,'eop_basedata_wbs_fdWbs',
				'fdDetailList_Form[*].fdWbsId','fdDetailList_Form[*].fdWbsName',getDetailMoney,{'fdCompanyId':$('[name=fdCompanyId]').val()||"noCompany"});
	}
	
	//选择内部订单
	window.Fssc_selectInnerOrder=function(){
		dialogSelect(false,'eop_basedata_inner_order_fdInnerOrder',
				'fdDetailList_Form[*].fdInnerOrderId','fdDetailList_Form[*].fdInnerOrderName',getDetailMoney,{'fdCompanyId':$('[name=fdCompanyId]').val()||"noCompany"});
	}
	
	//选择预算科目
	window.Fssc_selectBudgetItem=function(){
		dialogSelect(false,'eop_basedata_budget_item_com_fdBudgetItem',
				'fdDetailList_Form[*].fdBudgetItemId','fdDetailList_Form[*].fdBudgetItemName',getDetailMoney,{'fdCompanyId':$('[name=fdCompanyId]').val()||"noCompany"});
	}
	
	//选择/切换明细维度，重新加载对应的预算信息
	window.getDetailMoney=function(data,objs){
		if(!data){
			return ;
		}
		var field=data.field;
		if(!field){
			field=objs.length==2?objs[0].name:objs;
		}
		var index=field.substring(field.indexOf("[")+1,field.indexOf("]"));
		var params="";
		params+="&fdSchemeId="+$("input[name='fdBudgetSchemeId']").val();
		params+="&fdYear="+$("select[name='fdYear']").val();
		params+="&fdCompanyId="+$("[name='fdCompanyId']").val();
		var desimion=['fdCostCenterGroupId','fdCostCenterId','fdProjectId','fdWbsId','fdInnerOrderId','fdBudgetItemId','fdPersonId',"fdDeptId"];  //枚举所有维度
		for(var i=0;i<desimion.length;i++){
			var val=$('input[name="fdDetailList_Form['+index+'].'+desimion[i]+'"]').val();
			if(val){
				params+='&'+desimion[i]+'='+val;
			}
		}
		var data = new KMSSData();
		data.UseCache = false;
		var rtn = data.AddBeanData("fsscBudgetCommonDataServiceImp&authCurrent=true&source=detailMoney"+params).GetHashMapArray();
		if(rtn&&rtn.length > 0){
			var monthPro=['fdYearMoney','fdFirstQuarterMoney','fdJanMoney','fdFebMoney','fdMarMoney','fdSecondQuarterMoney',
			              'fdAprMoney','fdMayMoney','fdJunMoney','fdThirdQuarterMoney','fdJulMoney','fdAugMoney','fdSeptMoney',
			              'fdFourthQuarterMoney','fdOctMoney','fdNovMoney','fdDecMoney'];  //页面字段属性
			var money=["money5","money300","money100","money101","money102","money301","money103","money104","money105",
			           "money302","money106","money107","money108","money303","money109","money110","money111"];  //后台查询数据库字段；"money"+fdPeriodType+fdPeriod
			for(var i=0;i<money.length;i++){
				var dbmoney=rtn[0][money[i]];
				if(dbmoney){
					$('input[name="fdDetailList_Form['+index+'].'+monthPro[i]+'"]').val(dbmoney);
					$('input[name="fdDetailList_Form['+index+'].'+monthPro[i]+'"]').attr("readOnly",true);
					$("[name='fdDetailList_Form["+index+"]."+monthPro[i]+"']").parent().find("span").attr("style","display:none;");
				}else{//没值清空上一次的赋值
					$('input[name="fdDetailList_Form['+index+'].'+monthPro[i]+'"]').val("");
					$('input[name="fdDetailList_Form['+index+'].'+monthPro[i]+'"]').attr("readOnly",false);
					$("[name='fdDetailList_Form["+index+"]."+monthPro[i]+"']").parent().find("span").attr("style","");
				}
			}
			var radios=["fdYearRule","fdYearApply","fdQuarterRule","fdQuarterApply","fdMonthRule","fdMonthApply"];
			for(var k=0;k<6;k++){
				if(rtn[0][radios[k]]){
					$("input[name='fdDetailList_Form["+index+"]."+radios[k]+"'][value='"+rtn[0][radios[k]]+"']").prop('checked',true);
					if(rtn[0][radios[k]]&&document.getElementById("_xform_fdDetailList_Form["+index+"]."+radios[k])&&(radios[k]=='fdYearRule'||radios[k]=='fdYearApply')){
						document.getElementById("_xform_fdDetailList_Form["+index+"]."+radios[k]).parentNode.getElementsByTagName("div")[1].classList.add("div-b");
					}
					if(radios[k].indexOf("Rule")&&rtn[0][radios[k]]=='3'){
						$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").attr("style","width:95%;");
						$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").attr("validate","required  range(-100,100)");
						$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").parent().find("span").attr("style","");
					}
				}else{
					$("input[name='fdDetailList_Form["+index+"]."+radios[k]+"']").prop('checked',false);
					if(document.getElementById("_xform_fdDetailList_Form["+index+"]."+radios[k])){
						document.getElementById("_xform_fdDetailList_Form["+index+"]."+radios[k]).parentNode.getElementsByTagName("div")[1].classList.remove("div-b");
					}
				}
			}
		}
	};
	
	/*******************************************
	* 切换刚柔控，改变弹性的读写状态
	*******************************************/
	window.changeRule=function(obj,value){
		var field=obj.name;
		var index=field.substring(field.indexOf("[")+1,field.indexOf("]"));
		var fdRule=$("[name='fdDetailList_Form["+index+"].fdRule']:checked").val();
		var fdYearRule=$("[name='fdDetailList_Form["+index+"].fdYearRule']:checked").val();
		var fdQuarterRule=$("[name='fdDetailList_Form["+index+"].fdQuarterRule']:checked").val();
		var fdMonthRule=$("[name='fdDetailList_Form["+index+"].fdMonthRule']:checked").val();
		if(fdRule=='3'||fdMonthRule=='3'||fdQuarterRule=='3'||fdYearRule=='3'){
			$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").attr("style","width:95%;");
			$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").attr("validate","required  range(-100,100)");
			$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").parent().find("span").attr("style","");
		}else{
			$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").attr("style","width:95%;display:none;");
			$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").val("");
			$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").attr("validate","");
			$("[name='fdDetailList_Form["+index+"].fdElasticPercent']").parent().find("span").attr("style","display:none;");
		}
	};
	/*******************************************
	* 校验预算明细，月度金额总和<=季度金额总和<=年度金额总和
	*******************************************/
	Com_Parameter.event["submit"].push(function(){ // 通过submit队列来添加校验函数，这样校验失败，会终止表单提交。
		var submitFlag=true;
		var quarterArr=["fdFirstQuarterMoney","fdSecondQuarterMoney","fdThirdQuarterMoney","fdFourthQuarterMoney"];
		var monthArr=["fdJanMoney","fdFebMoney","fdMarMoney","fdAprMoney","fdMayMoney","fdJunMoney","fdJulMoney",
		              "fdAugMoney","fdSeptMoney","fdOctMoney","fdNovMoney","fdDecMoney"];
		var len=$("#TABLE_DocList_fdDetailList_Form>tbody>tr").length-1;
		for(var i=0;i<len;i++){
			var fdYearTotal=0,fdQuarterTotal=0,fdMonthTotal=0;
			var fdYearTotal=$("input[name='fdDetailList_Form["+i+"].fdYearMoney']").val();
			for(var n=0;n<4;n++){
				var money=$("input[name='fdDetailList_Form["+i+"]."+quarterArr[n]+"']").val();
				if(money){
					fdQuarterTotal=numAdd(fdQuarterTotal,money);
				}
			}
			for(var n=0;n<12;n++){
				var money=$("input[name='fdDetailList_Form["+i+"]."+monthArr[n]+"']").val();
				if(money){
					fdMonthTotal=numAdd(fdMonthTotal,money);
				}
			}
			if(fdMonthTotal&&fdQuarterTotal&&fdMonthTotal>fdQuarterTotal){
				submitFlag=false;
				dialog.alert(lang['message.budget.main.di']+(i+1)+lang['message.budget.main.monthQuarter']);
				break;
			}
			if(fdQuarterTotal&&fdYearTotal&&fdQuarterTotal>fdYearTotal){
				submitFlag=false;
				dialog.alert(lang['message.budget.main.di']+(i+1)+lang['message.budget.main.quarterYear']);
				break;
			}
			if(fdMonthTotal&&fdYearTotal&&fdMonthTotal>fdYearTotal){
				submitFlag=false;
				dialog.alert(lang['message.budget.main.di']+(i+1)+lang['message.budget.main.monthYear']);
				break;
			}
		}
		return submitFlag;
	});
	/*******************************************
	 * 校验预算币种必需有值
	 *******************************************/
	Com_Parameter.event["submit"].push(function(){ // 通过submit队列来添加校验函数，这样校验失败，会终止表单提交。
		var submitFlag=true;
		if(!$("input[name='fdCurrencyId']").val()){
			dialog.alert(baseLang["message.common.budget.currency.notSet"]);
			submitFlag=false;
		}
		return submitFlag;
	});
	//增加一行预算明细
	window.FSSC_AddBudgetDetail = function(){
		DocList_AddRow('TABLE_DocList_fdDetailList_Form');
	}
});
LUI.ready(function(){	
	seajs.use('lui/jquery',function($){
		var method=$("input[name='method_GET']").val();
		if(method=="edit"){
			var len=$("#TABLE_DocList_fdDetailList_Form>tbody>tr").length-1;
			if(len>0){
				for(var i=0;i<len;i++){
					//部门编辑初始化显示
					var id=$("[name='fdDetailList_Form["+i+"].fdDeptId']").val();
					var name=$("[name='fdDetailList_Form["+i+"].fdDeptName']").val();
					if(id&&name){
						emptyNewAddress("fdDetailList_Form["+i+"].fdDeptName",null,null,false);
						$("[name='fdDetailList_Form["+i+"].fdDeptId']").val(id);
						$("[name='fdDetailList_Form["+i+"].fdDeptName']").val(name);
						var addressInput = $("[xform-name='mf_fdDetailList_Form["+i+"].fdDeptName']")[0];
					    var addressValues = new Array();
					    addressValues.push({id:id,name:name});
						newAddressAdd(addressInput,addressValues);
					}
					//员工编辑初始化显示
					id=$("[name='fdDetailList_Form["+i+"].fdPersonId']").val();
					name=$("[name='fdDetailList_Form["+i+"].fdPersonName']").val();
					if(id&&name){
						emptyNewAddress("fdDetailList_Form["+i+"].fdPersonName",null,null,false);
						$("[name='fdDetailList_Form["+i+"].fdPersonId']").val(id);
						$("[name='fdDetailList_Form["+i+"].fdPersonName']").val(name);
						var addressInput = $("[xform-name='mf_fdDetailList_Form["+i+"].fdPersonName']")[0];
					    var addressValues = new Array();
					    addressValues.push({id:id,name:name});
						newAddressAdd(addressInput,addressValues);
					}
				}
			}
		}
	});
});
