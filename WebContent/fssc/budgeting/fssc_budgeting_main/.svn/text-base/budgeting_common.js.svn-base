seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str','lui/util/env','lang!fssc-budgeting','lang!'], function($, dialog, dialogCommon,strutil,env,lang,comlang){
	//上级预算
	var parentFields=["fdParentTotal","fdParentPeriodOne","fdParentPeriodTwo","fdParentPeriodThree","fdParentPeriodFour","fdParentPeriodFive",
	            "fdParentPeriodSix","fdParentPeriodSeven","fdParentPeriodEight","fdParentPeriodNine","fdParentPeriodTen","fdParentPeriodEleven","fdParentPeriodTwelve"];
	//可申请金额
	var canApplyFields=["fdCanApplyTotal","fdCanApplyPeriodOne","fdCanApplyPeriodTwo","fdCanApplyPeriodThree","fdCanApplyPeriodFour","fdCanApplyPeriodFive",
	            "fdCanApplyPeriodSix","fdCanApplyPeriodSeven","fdCanApplyPeriodEight","fdCanApplyPeriodNine","fdCanApplyPeriodTen","fdCanApplyPeriodEleven","fdCanApplyPeriodTwelve"];
	//新预算额
	var fields=["fdTotal","fdPeriodOne","fdPeriodTwo","fdPeriodThree","fdPeriodFour","fdPeriodFive",
	            "fdPeriodSix","fdPeriodSeven","fdPeriodEight","fdPeriodNine","fdPeriodTen","fdPeriodEleven","fdPeriodTwelve"];
	//根据预算方案ID显示控制规则和运用规则
	window.getPeriodBySchemeId=function(schemeId){
		var params="&fdSchemeId="+schemeId;
		params+="&fdCompanyId="+$("[name='fdCompanyId']").val();
		var data = new KMSSData();
		data.UseCache = false;
		var rtn = data.AddBeanData("fsscBudgetingDataService&authCurrent=true&source=scheme"+params).GetHashMapArray();
		if(rtn&&rtn.length > 0){
			displayPeriod(rtn[0]["period"]);
		}
	};
	//根据预算方案的不同期间设置显示控制规则和运用规则
	window.displayPeriod=function(period){
		if(period.indexOf("2;")>-1){//年度
			$("#_xform_fdYearRule").attr("style","display:block;float:left;");
			$("[name='fdYearRule']").attr("validate","required");
			$("#_xform_fdYearApply").attr("style","display:block;float:left;");
			$("[name='fdYearApply']").attr("validate","required");
		}
		if(period.indexOf("3;")>-1){//季度
			$("#_xform_fdQuarterRule").attr("style","display:block;float:left;");
			$("[name='fdQuarterRule']").attr("validate","required");
			$("#_xform_fdQuarterApply").attr("style","display:block;float:left;");
			$("[name='fdQuarterApply']").attr("validate","required");
		}
		if(period.indexOf("4;")>-1){//月度
			$("#_xform_fdMonthRule").attr("style","display:block;float:left;");
			$("[name='fdMonthRule']").attr("validate","required");
			$("#_xform_fdMonthApply").attr("style","display:block;float:left;");
			$("[name='fdMonthApply']").attr("validate","required");
		}
	};
	//通过/驳回预算：1、勾选部分，点击驳回，则勾选的被驳回，未勾选的被审核通过；2、勾选部分，点击通过，则勾选的被通过，未勾选的被驳回；
	window.approvalDoc=function(status){
		var selected = [];  //选中的复选框
		var allSelect=[];   //所有的复选框，当当前人员只是审核部分科目的时候，并不是整个主文档的明细ID
        $("input[name='List_Selected']:checked").each(function(){
            selected.push($(this).val());
        });
        $("input[name='List_Selected']").each(function(){
        	//只需要末级科目的
        	allSelect.push($(this).val());
        });
        if(selected.length==0){
            dialog.alert(comlang['page.noSelect']);
            return;
        }
        var tips="";
        if(status=='3'){//驳回，提示未勾选的默认是通过的
        	tips=lang["budgeting.approval.reject"];
        }else if(status=='4'){//审核通过，整个预算主文档都是审核通过的
        	tips=lang["budgeting.approval.pass"];
        }
        dialog.confirm(tips,function(ok){
            if(ok==true){
	            var del_load = dialog.loading();
	            var param = {"List_Selected":selected,"status":status,"fdMainId":$("[name='fdId']").val(),"allSelect":allSelect,"approveOption":$("[name='fdApprovalOpinions']").val()};
	            $.ajax({
	            	url:env.fn.formatUrl('/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=approvalDoc'),
	            	data:$.param(param,true),
	            	dataType:'json',
	            	type:'POST',
	            	success:function(data){
	            		if(del_load!=null){
	                        del_load.hide();
	                        window.location.reload();
	                    }
	                    dialog.result(data);
	            	},
	            	error:function(req){
	            		if(req.responseJSON){
	            			var data = req.responseJSON;
	            			dialog.failure(data.title);
	            		}else{
	            			dialog.failure('操作失败');
	            		}
	            		del_load.hide();
	            	}
	            });
            }
        });
	};
	//预算生效
	window.effectDoc=function(status){
		var selected = [];  //选中的复选框
		var allSelect=[];   //所有的复选框，当当前人员只是审核部分科目的时候，并不是整个主文档的明细ID
		$("input[name='List_Selected']:checked").each(function(){
			selected.push($(this).val());
		});
		$("input[name='List_Selected']").each(function(){
			//只需要末级科目的
        	allSelect.push($(this).val());
        });
		if(selected.length==0){
			dialog.alert(comlang['page.noSelect']);
			return;
		}
		var del_load = dialog.loading();
		var param = {"List_Selected":selected,"fdMainId":$("[name='fdId']").val(),"allSelect":allSelect,"status":status};
		$.ajax({
			url:env.fn.formatUrl('/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=effectDoc'),
			data:$.param(param,true),
			dataType:'json',
			type:'POST',
			success:function(data){
				if(del_load!=null){
					del_load.hide();
					window.location.reload();
				}
				dialog.result(data);
			},
			error:function(req){
				if(req.responseJSON){
					var data = req.responseJSON;
					dialog.failure(data.title);
				}else{
					dialog.failure('操作失败');
				}
				del_load.hide();
			}
		});
	};
	//控制规则选择弹性，填写弹性比例
	window.changRule=function(val,obj){
		var fdYearRule=$("[name='fdYearRule']:checked").val();
		var fdQuarterRule=$("[name='fdQuarterRule']:checked").val();
		var fdMonthRule=$("[name='fdMonthRule']:checked").val();
		if(fdYearRule==3||fdQuarterRule==3||fdMonthRule==3){
			$("[name='fdElasticPercent']").attr("validate","required number range(0,100)");
			$("#_xform_fdElasticPercent").attr("style","display:block;float:left;");
		}else{
			$("[name='fdElasticPercent']").val("");
			$("[name='fdElasticPercent']").attr("validate","");
			$("#_xform_fdElasticPercent").attr("style","display:none;");
		}
	};
	//预算编制选择完后台获取信息
	window.afterSelectBudgetItem=function(data){
		if(!data){
			return;
		}
		var index=data.index;
		var params="";
		var fdSchemeId=$("[name='fdSchemeId']").val();
		if(fdSchemeId){
			params+="&fdSchemeId="+fdSchemeId;
		}
		var fdOrgId=$("[name='fdOrgId']").val();
		if(fdOrgId){
			params+="&fdOrgId="+fdOrgId;
		}
		var fdYear=$("[name='fdYear']").val();
		if(fdYear){
			params+="&fdYear="+fdYear;
		}
		var fdOrgType=$("[name='fdOrgType']").val();
		if(fdOrgType){
			params+="&fdOrgType="+fdOrgType;
		}
		var demis=["fdBudgetItemId","fdProjectId","fdWbsId","fdInnerOrderId","fdPersonId"];
		for(var r=0;r<demis.length;r++){
			var val=$("input[name='fdDetailList_Form["+index+"]."+demis[r]+"']").val();
			if(val){
				params+="&"+demis[r]+"="+val;
			}
		}
		var data = new KMSSData();
		data.UseCache = false;
		var rtn = data.AddBeanData("fsscBudgetingDataService&source=budgetItemInfo&authCurrent=true"+params).GetHashMapArray();
		if(rtn&&rtn.length > 0){
			$("input[name='fdDetailList_Form["+index+"].fdParentId']").val(rtn[0]["fdParentId"]);
			$("input[name='fdDetailList_Form["+index+"].fdIsLastStage']").val(rtn[0]["fdIsLastStage"]);
			var canApply=rtn[0]["canApply"];
			if(canApply){
				canApply=LUI.toJSON(canApply);
				for(var n=0;n<13;n++){
					var tarName="fdDetailList_Form["+index+"]."+fields[n];
					if(fields[n]=='fdTotal'){//年度需要必填
						$("[name='"+tarName+"']").attr("validate","required number range(0,"+formatFloat(canApply[fields[n]],2)+")");//添加校验
					}else{
						$("[name='"+tarName+"']").attr("validate","number range(0,"+formatFloat(canApply[fields[n]],2)+")");//添加校验
					}
					tarName="fdDetailList_Form["+index+"]."+canApplyFields[n];
					$("[name='"+tarName+"']").val(formatFloat(canApply[fields[n]],2)); //可申请金额显示
					tarName=tarName="fdDetailList_noShow_Form["+index+"]."+fields[n];
					$("[name='"+tarName+"']").val(formatFloat(canApply[fields[n]],2));//可申请金额隐藏域赋值
				}
			}
			var parent=rtn[0]["parent"];
			if(parent){
				parent=LUI.toJSON(parent);
				for(var n=0;n<13;n++){
					var tarName="fdDetailList_Form["+index+"]."+parentFields[n];
					if(parent[fields[n]]){
						$("[name='"+tarName+"']").val(formatFloat(parent[fields[n]],2));
					}else{
						$("[name='"+tarName+"']").val(formatFloat(0,2));
					}
				}
			}
		}
	};
	
	window.afterSelect=function(data,objs){
		if(!data){
			return;
		}
		var index=data.index;
		//员工部门index会为undefine，重新获取
		if(index!=0&&!index){
			var field=data.field;
			if(!field){
				field=objs[0].name;
			}
			index=field.substring(field.indexOf("[")+1,field.indexOf("]"));
		}
		var params="";
		var fdSchemeId=$("[name='fdSchemeId']").val();
		if(fdSchemeId){
			params+="&fdSchemeId="+fdSchemeId;
		}
		var fdOrgId=$("[name='fdOrgId']").val();
		if(fdOrgId){
			params+="&fdOrgId="+fdOrgId;
		}
		var fdYear=$("[name='fdYear']").val();
		if(fdYear){
			params+="&fdYear="+fdYear;
		}
		var fdOrgType=$("[name='fdOrgType']").val();
		if(fdOrgType){
			params+="&fdOrgType="+fdOrgType;
		}
		var demis=["fdBudgetItemId","fdProjectId","fdWbsId","fdInnerOrderId","fdPersonId"];
		for(var r=0;r<demis.length;r++){
			var val=$("input[name='fdDetailList_Form["+index+"]."+demis[r]+"']").val();
			if(val){
				params+="&"+demis[r]+"="+val;
			}
		}
		var data = new KMSSData();
		data.UseCache = false;
		var rtn = data.AddBeanData("fsscBudgetingDataService&source=budgetItemInfo&authCurrent=true"+params).GetHashMapArray();
		if(rtn&&rtn.length > 0){
			var canApply=rtn[0]["canApply"];
			if(canApply){
				canApply=LUI.toJSON(canApply);
				for(var n=0;n<13;n++){
					var tarName="fdDetailList_Form["+index+"]."+fields[n];
					if(fields[n]=='fdTotal'){//年度需要必填
						$("[name='"+tarName+"']").attr("validate","required number range(0,"+formatFloat(canApply[fields[n]],2)+")");//添加校验
					}else{
						$("[name='"+tarName+"']").attr("validate","number range(0,"+formatFloat(canApply[fields[n]],2)+")");//添加校验
					}
				}
			}
		}
	};
});
LUI.ready(function(){	
	seajs.use('lui/jquery',function($){
		//点击标题行复选框，全部选中或全部不选中
		$('input[name="List_Tongle"]').on("click",function(){
			if($(this).is(':checked')){
				$('input[name="List_Selected"]').each(function(){
					$(this).prop("checked",true);
				});
			}else{
				$('input[name="List_Selected"]').each(function(){
					$(this).prop("checked",false);
				});
			}
		});
		//点击数据行，全选中的话自动勾选标题行复选框
		$('input[name="List_Selected"]').on("click",function(){
			var tongle = $('input[name="List_Tongle"]')[0];
			if(typeof(tongle) != "undefined" && tongle != null){
				//全选后，取消子项勾选，全选勾选跟着消失
				if(this.checked == false && tongle.checked == true){
					tongle.checked = false;
				}
				
				//子项全部勾选后，全选勾选跟着勾选
				if(tongle.checked == false){
					var selectAll = true;
					$("input[name='List_Selected']").each(function(){
						if(!this.checked){
							selectAll = false;
						}
					});
					if(selectAll){
						tongle.checked = true;
					}
				}
			}
		});
	})
});
