seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/util/env','lang!fssc-budget','lang!','lang!sys-right'], function($, dialog , topic,env,lang,comlang,rightLang) {
	$(document).ready(function(){
		
	});
	//修改预算状态：暂停、重启、关闭
	window.updateBudgetStatus = function(operation){
		var values = [];
		values.push(getUrlParam("fdId"));
		var tips="";
		var error="";
		if("stop"==operation){
			tips=lang['message.budget.batch.stop.tips'];
			error=lang['message.budget.exist.frozen.budget.stop'];
		}else if("restart"==operation){
			tips=lang['message.budget.batch.restart.tips'];
		}else if("close"==operation){
			tips=lang['message.budget.batch.close.tips'];
			error=lang['message.budget.exist.frozen.budget.close'];
		}
		dialog.confirm(tips,function(value){
			if(value==true){
				var param = {"List_Selected":values,"operation":operation};
				window.del_load = dialog.loading();
				$.ajax({
					url: env.fn.formatUrl('/fssc/budget/fssc_budget_data/fsscBudgetData.do?method=alterBudgetStatus'),
					type: 'post',
					data:$.param(param,true),
					dataType: 'json',
					error: function(data){
						if(window.del_load!=null){
							window.del_load.hide(); 
						}
						dialog.result(data.responseJSON);
					},
					success: function(data){
						if(window.del_load!=null)
							window.del_load.hide();
						if(data.value=="0"){
							dialog.alert(error);
						}else if(data.value=="1"){
							dialog.success(comlang['return.optSuccess']);
							window.location.reload();
						}else if(data.value=="2"){
							dialog.failure(data.message);
						}
					}
				});
			}
		});
	};
	 //修改权限
    window.changeRightCheckSelect=function(fdBudgetId) {
        var url= "/sys/right/rightDocChange.do?method=docRightEdit&modelName=com.landray.kmss.fssc.budget.model.FsscBudgetData&authReaderNoteFlag=2&fdIds="+fdBudgetId;
        window.dia = dialog.iframe(url,rightLang['right.button.changeRight.view'],
            function(data) {
                if(data){

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
	window.adjustBudgetData=function(id){
		Com_OpenWindow(env.fn.formatUrl('/fssc/budget/fssc_budget_data/fsscBudgetData.do?method=adjustBudgetData&fdId='+id));
	};
	//获取url中的参数
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); 
        return null; //返回参数值
    };
});

LUI.ready(function(){	
	seajs.use('lui/jquery',function($){
		var url = location.href;
		$.ajax({
			  type: 'post',
			  url: Com_Parameter.ContextPath+"fssc/budget/fssc_budget_data/fsscBudgetData.do?method=getBudgetInfo",
			  async:false,
			  data : {fdBudgetId:Com_GetUrlParameter(url,'fdId')},
			  success: function(result){
				  var fdCanUseAmount=0.0;
				  if(result){
					  var result=JSON.parse(result);
					  var fdInitAmount=result["fdInitAmount"];
						//预算初始额度
						if (!fdInitAmount){
							fdInitAmount=0.00;
						}
						$('#_xform_fdMoney').html(formatFloat(fdInitAmount,2));
						var fdAdjustAmount=result["fdAdjustAmount"];
						//预算调整额度
						if (!fdAdjustAmount){
							fdAdjustAmount=0.00;
						}
						$('#_xform_fdAdjustMoney').html(formatFloat(fdAdjustAmount,2));
						//预算总额度
						var totalMoney=addPoint(fdInitAmount,fdAdjustAmount);
						$('#_xform_fdTotalMoney').html(totalMoney);
						//滚动可使用额
						var rollMoney = result["rollMoney"];
						if (!rollMoney){
							rollMoney=0.00;
						}
						$('#_xform_fdRollMoney').html(formatFloat(rollMoney,2));
						totalMoney=addPoint(totalMoney,rollMoney);
						//预算已用
						var fdAlreadyUsedAmount = result["fdAlreadyUsedAmount"];
						if (!fdAlreadyUsedAmount){
							fdAlreadyUsedAmount=0.00;
						}
						$('#_xform_fdAlreadyUsedMoney').html(formatFloat(fdAlreadyUsedAmount,2));
						//预算占用
						var fdOccupyAmount = result["fdOccupyAmount"];
						if (!fdOccupyAmount){
							fdOccupyAmount=0.0;
						}
						$('#_xform_fdOccupyMoney').html(formatFloat(fdOccupyAmount,2));
						//预算可使用
						$('#_xform_fdCanUseMoney').html(subPoint(totalMoney,fdAlreadyUsedAmount+fdOccupyAmount));
					  }
			  },
			  error:function(jqXHR, textStatus, e){
				  console.log(e);
			  }
			});
			var fdBudgetStatus=$("input[name='fdBudgetStatus']").val();
			if(fdBudgetStatus=="1"&&LUI('btnRestart')){//预算是启用的，不显示重启按钮
				LUI('btnRestart').setVisible(false);
			}
			if(fdBudgetStatus=="2"){//预算是暂停的，不显示暂停、调整按钮
				if(LUI('btnStop')){
					LUI('btnStop').setVisible(false);
				}
				if(LUI('btnAdjust')){
					LUI('btnAdjust').setVisible(false);
				}
			}
			if(fdBudgetStatus=="3"){//预算是关闭的，暂停、关闭、重启、调整按钮
				if(LUI('btnRestart')){
					LUI('btnRestart').setVisible(false);
				}
				if(LUI('btnClose')){
					LUI('btnClose').setVisible(false);
				}
				if(LUI('btnStop')){
					LUI('btnStop').setVisible(false);
				}
				if(LUI('btnAdjust')){
					LUI('btnAdjust').setVisible(false);
				}
			}
	});
});
