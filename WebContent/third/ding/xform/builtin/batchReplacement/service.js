/**
 * 批量补卡流程函数接口
 */
Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
Com_IncludeFile("ding_right.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
Com_IncludeFile("dingTool.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
var ekpUserid = "";
//班次信息
var schedules = {};
seajs.use(["lui/jquery","lui/topic", "lui/util/str", "lui/util/env", "lui/dialog"],function($, topic, strUtil, env, dialog){

	//注册事件
	window.selectDateType = function(){
		var optdel = $("span.opt_del_style");
		var changeSchedule = $(".change_replacement_schedule");
		if(!optdel || optdel.length==0 || changeSchedule.length==0){
			setTimeout("initEvent();","500");
			return;
		}		
		$(document).on('table-add-new','table[showStatisticRow]',function(e,argus){
		//1、补卡时间变更事件
		$("[name*='fd_replacement_time']").on('change',function(){
				//取控件name
				var name = this.name;
				//取控件所在td
				var tdObj = $(this).parents(".inputselectsgl").parent("xformflag").parent(".xform_datetime").parent("td");
				Ding_ReplacementTimeChange(this,tdObj,name);
			});
		});
		
		//2、请假人发生变更触发事件
		$("[name='extendDataFormInfo.value(fd_user.id)']").on('change',function(){
			var userId = $("[name='extendDataFormInfo.value(fd_user.id)']").val();
			if(ekpUserid != userId){
				//1、重新赋值给全局变量ekpUserid
				ekpUserid = userId;
				//2、重新获取加载补卡次数
				initReplacementCounts();
				//3、清空每行内已选择数据--待补
				clearEveryTrData();
			}
		});
        
        //3、给时间控件绑定事件
        $("[name*='fd_replacement_time']").attr("validate","required __datetime checkReplacementTime checkTimeSchedule checkReplacementCounts checkReplacementException checkNotCanSupply");
	
        checkReplacementValidation('checkReplacementTime',"补卡时间重复，请重新选择。");
    	checkTimeScheduleValidation('checkTimeSchedule',"当前时间没有考勤异常，无需补卡。");
    	checkReplacementCountsValidation('checkReplacementCounts',"已超出剩余可申请补卡次数。");
    	checkReplacementExceptionValidation('checkReplacementException',"未获取到考勤异常信息。");//接口
    	addBatchUserValidation('checkUserISInDingList',"请确认当前补卡人是否已做钉钉映射");
    	addBatchCheckNotCanSupplyValidation('checkNotCanSupply',"超出补卡时间或剩余补卡次数无法申请");
	}
	
	// 点击更换补卡班次按钮
	window.Ding_ReplacementSchedule = function(dom) {
        let url = "/third/ding/xform/builtin/batchReplacement/dialog_replacement_schedule.jsp";
        //读取班次ID
        var tdObj = $(dom).parent(".form_tr_context_desc").parent("td");
        let currentSchedule = $(tdObj).find('input[name*="punchId"]').val();
        var schedules_tr = $(tdObj).parent("tr").find("[name*='fd_schedules']").val();
        schedules_tr = JSON.parse(schedules_tr);
        let length = Object.keys(schedules_tr).length;
        if (length > 0) {
            dialog.iframe(url, null, ReplacementScheduleResult, {
                width: 360, height: 120 + length * 40,
                params: {data: {"schedules": schedules_tr, "currentSchedule": currentSchedule}}
            });
        }

	    function ReplacementScheduleResult(result){//弹窗结束后回调事件，取被选中的
	        if (result) {
	            let schedule = schedules_tr[result.value];
	            $(tdObj).find('.replacement_schedule').text(schedule.text);
	            //保存班次ID
	            $(tdObj).find('input[name*="punchId"]').val(schedule.punchId);
	            $(tdObj).find('input[name*="punch_check_time"]').val(schedule.check_date_time);
	    	    $(tdObj).find('input[name*="work_date"]').val(schedule.work_date);
	            
	            //保存选中的班次信息
	            $(tdObj).parent("tr").find('[name*="singer_replacement_info"]').val(JSON.stringify(schedule));
	            canSupply(tdObj);
	        }
	    }
	}
	
	//补卡时间变更事件
	var ReplacementTime;
	function Ding_ReplacementTimeChange(dom,tdObj,name) {
	    if(ReplacementTime && ReplacementTime === $(dom).val()) {
	        return;
	    } else {
	        ReplacementTime = $(dom).val();
	    }
	    //获取日期对应补卡班次
	    loadScheduleByDay(dom,ReplacementTime,tdObj,name);
	    var trTimeObj = $('[name="'+name+'"]');
	    if(!$(trTimeObj).hasClass("noreplacement") && !$(trTimeObj).hasClass("nullinfo")){
		    //显示当前补卡班次及更换补卡班次按钮
		    showSchedule(dom,tdObj,name,getScheduleByDate(dom,tdObj,name));
		    canSupply(tdObj);
	    }
	}
	
	/**
	 * 显示当前补卡班次及更换补卡班次按钮
	 * schedule :默认选中的补卡班次--保存在行内singer_replacement_info
	 */
	window.showSchedule = function(dom,tdObj,name,schedule) {
	    if(!schedule) {
	        return;
	    }
	    $(tdObj).parent("tr").find('[name*="singer_replacement_info"]').val(JSON.stringify(schedule));
	    $(tdObj).find('.replacement_schedule').text(schedule.text);
	    //保存班次ID、排班时间、班次日期
	    $(tdObj).find('input[name*="punchId"]').val(schedule.punchId);
	    $(tdObj).find('input[name*="punch_check_time"]').val(schedule.check_date_time);
	    $(tdObj).find('input[name*="work_date"]').val(schedule.work_date);
	    //更换补卡班次
	    var trTimeObj = $('[name="'+name+'"]');
	    if(!$(trTimeObj).hasClass("noreplacement") && !$(trTimeObj).hasClass("nullinfo")){
	    	let $changeReplacementSchedule = $(tdObj).find('.change_replacement_schedule');
	    	$changeReplacementSchedule.text("更换补卡班次");
	    	$changeReplacementSchedule.show();
	    	
	    	let $form_tr_context_desc = $(tdObj).find('.form_tr_context_desc');
	    	$form_tr_context_desc.show();
	    }
	}
	
	/**
	 * 根据补卡时间计算应该取哪个班次--弹窗展示组装
	 * dom：当前控件-补卡时间input
	 * tdObj：当前控件所在的td
	 * name：当前控件name
	 */
	function getScheduleByDate(dom,tdObj,name){
		var schedules_tr = $(tdObj).parent("tr").find("[name*='fd_schedules']").val();
	    if(!schedules_tr) {
	        return;
	    }
	    schedules_tr = JSON.parse(schedules_tr);
	    //整理后续进行时间对比的格式
	    let minSchedule;
	    let maxSchedule;
	    for(let i in schedules_tr) {

	        let schedule = schedules_tr[i];
	        let real_plan_time = new Date(schedule.check_date_time).getTime();
	        if(!minSchedule || minSchedule.check_date_time > real_plan_time) {
	            minSchedule = {};
	            minSchedule.check_date_time = real_plan_time;
	            minSchedule.index = i;
	        }
	        if(!maxSchedule || maxSchedule.check_date_time < real_plan_time) {
	            maxSchedule = {};
	            maxSchedule.check_date_time = real_plan_time;
	            maxSchedule.index = i;
	        }
	    }
	    //根据补卡时间计算
	    let dateStr = $(tdObj).find('[name*="fd_replacement_time"]').val();
	    let date = new Date(dateStr).getTime();
	    if(date < minSchedule.check_date_time && date < maxSchedule.check_date_time) {
	        //选择的时间小于所有的排班时间，返回最小的排班时间
	        return schedules_tr[minSchedule.index];
	    } else if(date > minSchedule.check_date_time && date > maxSchedule.check_date_time) {
	        //选择的时间大于所有的排班时间，返回最大的排班时间
	        return schedules_tr[maxSchedule.index];
	    } else {
	        //返回最大的排班时间
	        return schedules_tr[maxSchedule.index];
	    }
	}
	
	// 获取班次信息
	function loadScheduleByDay(dom,fd_replacement_time,tdObj,name) {
	    if(!fd_replacement_time) {
	    	fd_replacement_time = $('[name="'+name+'"]').val();
	    }
	    
	    var date = new Date(fd_replacement_time);
	    var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=getSupplyDates&date=" + fd_replacement_time+"&ekpUserId="+ekpUserid;
	    $.ajax({
	        url: url,
	        type: 'GET',
	        dataType: 'json',
	        async: false,
	        error: function(data) {
	       	  	console.log("加载补卡信息失败。url:"+url);
	       	  	clearTrTimeData(name,"nullinfo");   
	         },
	        success: loadScheduleByDaySuccess
	    });
	    
	    function loadScheduleByDaySuccess(data) {
	    	var times = $("[name*='"+name+"']").val();
	        if (data.errcode === 0 && data.success) {
	            var result = data.result;
	            if (result && result.form_data_list.length>0) {
	            	var fd_schedule_info = result.form_data_list[0].value;
	            	$(tdObj).find("[name*='fd_schedule_info']").val(fd_schedule_info);//保存当前行的班次信息
	            	var fd_schedules = $(tdObj).parent("tr").find("[name*='fd_schedules']");
	            	var schedules_tr = {};
	                var infoJson = JSON.parse(fd_schedule_info);
	                for (var i = 0; i < infoJson.length; i++) {
	                	var work = infoJson[i];
	                    var schedule = {};
	                    if(!work.freeCheck){//非休息日
	                    	schedule.punchId = work.planId;
	                    	schedule.check_date_time = work.checkDateTime;
	                    	schedule.checkType = work.checkType;
	                    	schedule.checkStatus = work.checkStatus;
	                    	schedules_tr[schedule.punchId] = schedule;
	                    }else{
	                    	schedule.punchId = work.workDate+"_"+work.supplyDate;
	                    	schedule.check_date_time = work.supplyDate;
	                    }
	                    schedule.planText = work.planText;
	                    schedule.text = work.planTip;
	                    schedule.work_date = work.workDate;
	                    schedules_tr[schedule.punchId] = schedule;
	                }
	                if(schedules_tr){
	                	$(fd_schedules).val(JSON.stringify(schedules_tr));//保存行内的组装弹窗所需信息-原全局schedules
	                }
	                //去掉校验class
	                $('[name="'+name+'"]').removeClass("nullinfo");
	                $('[name="'+name+'"]').removeClass("noreplacement");
		       	  	$('[name="'+name+'"]').focus();
	            }else{
	            	console.log("thirdDingAttendance.scheduleByDay ---> data.result is null..."+JSON.stringify(data));
	            	var times = $("[name*='"+name+"']").val();
	            	//没有需要补卡的
	            	clearTrTimeData(name,"noreplacement");           	
	            }
	        }else{
	        	console.log("thirdDingAttendance.scheduleByDay interface is exception..."+JSON.stringify(data));
	        	//接口请求失败，
	        	clearTrTimeData(name,"nullinfo");   
	        }
	    }
	    
	    /**
	     * 清掉当前行的时间控件相关数据
	     * name:当前行时间控件name-全
	     * checkClassName:校验class名称
	     */
	    window.clearTrTimeData = function(name,checkClassName){
	    	//1、时间控件下面展示的信息藏起来---需优化为当前行的
        	$(".form_tr_context_desc").css("display","none");
        	//2、触发时间控件的校验
        	$('[name="'+name+'"]').addClass(checkClassName);
        	$('[name="'+name+'"]').focus();
        	//3、清掉和时间控件相关的隐藏值
        	var tdObj = $('[name="'+name+'"]').parents(".inputselectsgl").parent("xformflag").parent(".xform_datetime").parent("td");
    	    $(tdObj).find('input[name*="punchId"]').val("");
    	    $(tdObj).find('input[name*="punch_check_time"]').val("");
    	    $(tdObj).find('input[name*="work_date"]').val("");
	    }
	}
	
	function Ding_Init(){
		var status = DingCommonnGetStatus();
		switch(status){
			case "add":
			case "edit":
				Ding_Init_Edit(status);
				break;
			case "view":
				Ding_Init_View(status);
				break;
		}
	}
	Com_AddEventListener(window, "load", Ding_Init);
});

/**
 * 判断能否补卡
 * @param tr
 * @returns
 */
function canSupply(tr){
	var punchId = $(tr).find("[name*='punchId']").val();
	var fd_schedule_info = $(tr).find("[name*='fd_schedule_info']").val();
	var extendValue = null;
	if(punchId && fd_schedule_info){
		//休息日
		var workDate = null ;
		var supplyDate = null ;
		var scheduleInfo = JSON.parse(fd_schedule_info);
		//取extendValue
		for (var i = 0; i < scheduleInfo.length; i++) {
			if(punchId.indexOf("_")>0){//如果是休息日组装起来的punchId 则需重新取
				workDate = punchId.split("_")[0];
				supplyDate = punchId.split("_")[1];
				if(workDate == scheduleInfo[i]["workDate"] && supplyDate == scheduleInfo[i]["supplyDate"]){
					extendValue = scheduleInfo[i];
				}
			}else{
				if(punchId == scheduleInfo[i]["planId"]){
					extendValue = scheduleInfo[i];
				}
			}
		}
		var fd_replacement_time = $(tr).find("[name*='fd_replacement_time']").val();
		var fd_user_id = $("[name*='extendDataFormInfo.value(fd_user.id)']").val();
		if(fd_replacement_time && extendValue){
			 var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=canSupply";
			 var parm = {};
			 parm.extendValue = JSON.stringify(extendValue);
			 parm.date = fd_replacement_time;
			 parm.ekpUserId = fd_user_id;
		     $.ajax({
		        url: url,
		        type: 'POST',
		        data: parm,
		        dataType: 'json',
		        success: function(data){   
		        	if(data && data.errcode != 0){//先用errcode，后续可能会变
		        		$(tr).find("[name*='fd_replacement_time']").addClass("notCanSupply");
		        		$(tr).find("[name*='fd_replacement_time']").focus();
		        		networkFailure("超出补卡时间或剩余补卡次数无法申请");
		        		console.log("check canSupply is not ok  :"+ data);
		        	}else{
		        		$(tr).find("[name*='fd_replacement_time']").removeClass("notCanSupply");
		        		$(tr).find("[name*='fd_replacement_time']").focus();
		        	}     		
		        },error : function(data) {
		        	$(tr).find("[name*='fd_replacement_time']").addClass("notCanSupply");
	        		$(tr).find("[name*='fd_replacement_time']").focus();
	        		networkFailure("校验不通过：超出补卡时间或剩余补卡次数无法申请");
	        		console.log("check canSupply interface is error  :"+ data);
		         }
		    });
		}
	}	
}

function Ding_Init_Edit(status){
	//初始化补卡次数和剩余次数
	initReplacementCounts();
	
	// 添加监听事件
	Ding_Edit_InitEvent();
	
	if("edit"==status){
		showTrContextDescSpan(status);
	}
	$("[name*='extendDataFormInfo.value(fd_user.name)']").attr("validate","required checkUserISInDingList");
}

//获取当前补卡人的补卡次数和剩余次数
function initReplacementCounts(){
   ekpUserid = $("[name='extendDataFormInfo.value(fd_user.id)']").val();
   var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=getCheckInfo&ekpUserId="+ekpUserid;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function(data){        	
        	if(data && null !=data.result&& null != data.result.form_data_list){
        		$("[name*='extendDataFormInfo.value(fd_user.name)']").removeClass("notInDingList");
        		$("[name*='extendDataFormInfo.value(fd_user.id)']").parent(".inputselectsgl").parent("xformflag").parent(".xform_address").parent("td").find(".validation-advice").remove();
        		$("[name='extendDataFormInfo.value(fd_replacement_count)']").addClass("fdReplacementCount");
        		$("[name='extendDataFormInfo.value(fd_replacement_count)']").val(data.result.form_data_list[0].value);
        	}else{
        		$("[name*='extendDataFormInfo.value(fd_user.name)']").addClass("notInDingList");
    			networkFailure("请确认当前补卡人是否已做钉钉映射");
        	}        		
        },error : function(data) {
    		networkFailure("初始化补卡次数异常");
       	  	console.log("初始化补卡次数异常。url:"+url);
         }
    });
}

function clearEveryTrData(){
	//全选-删除行-新增行
	$("[name*='DocList_SelectAll']").parent(".opt_ck_style").click();
	$(".opt_batchDel_style").parent("span").click();
	$(".opt_add_style").parent("span").click();
}

function Ding_Edit_InitEvent(){
	//注册事件
	this._export(this);
	selectDateType();
}

function _export(v){
	window.initEvent = function(){
		v.selectDateType();
	}
}

function Ding_Init_View(status){
	showTrContextDescSpan(status);
}

function showTrContextDescSpan(status){
	var singers = $("[name*='singer_replacement_info']");
	console.log("singer_replacement_info.length:"+singers.length);
	if(singers.length>0){
		for (var i = 0; i < singers.length; i++) {
			var singerInfoSelect = $(singers[i]).val();
			if(singerInfoSelect){
				singerInfoSelect = JSON.parse(singerInfoSelect);
				var text = singerInfoSelect.text;
				var trContextDesc = $(singers[i]).parent("xformflag").parent(".xform_inputText").parent("td").parent("tr").find(".form_tr_context_desc");
				$(trContextDesc).find(".replacement_schedule").text(text);
				$(trContextDesc).css("display","table-row");
				if("edit"==status){
					$(".change_replacement_schedule").text("更换补卡班次");
				}else{
					$(".change_replacement_schedule").css("display","none");
				}
			}
		}
	}else{
		setTimeout("Ding_Init_View('"+status+"');","1000");
	}
}

//单独处理ie11的view页面初次打开span信息未渲染问题（刷新ok）
$(function(){
	var status = DingCommonnGetStatus();
	if("view" == status){
		Ding_Init_View(status);
	}
});