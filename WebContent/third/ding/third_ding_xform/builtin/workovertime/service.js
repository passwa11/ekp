Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("record.css", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/css/","css",true);
debug_flag = true;
var showFlag = true;
/**
 * 查询明细右侧框
 */
function showCardTimes() {
	rightShow("计算明细","将在出勤统计中记为","共加班136.5小时","都快加死了！");
}

/**
 * 获取加班规则
 */
function showWorkInfo() {
	var ruleHTML = "";
	ruleHTML += "<tr><td>";
	ruleHTML += "<div class='d_lui_mix_smallrulefont'>工作日加班计算方式</div>";
	ruleHTML += "<div class='d_lui_mix_bigrulefont'>加班起算时间：最后一次下班后30分钟，开始计算加班</div>";
	ruleHTML += "<div class='d_lui_mix_bigrulefont'>最小加班时长：30分钟</div>";
	ruleHTML += " </td></tr>";
	
	ruleHTML += "<tr><td>";
	ruleHTML += "<div class='d_lui_mix_smallrulefont'>休息日加班计算方式</div>";
	ruleHTML += "<div class='d_lui_mix_bigrulefont'>加班时长：以审批单为准</div>";
	ruleHTML += " </td></tr>";
	
	ruleHTML += "<tr><td>";
	ruleHTML += "<div class='d_lui_mix_smallrulefont'>节假日加班计算方式</div>";
	ruleHTML += "<div class='d_lui_mix_bigrulefont'>加班时长：以考勤打卡时长为准</div>";
	ruleHTML += "<div class='d_lui_mix_bigrulefont'>最小加班时长：30分钟</div>";
	ruleHTML += " </td></tr>";
	rightShowRule("加班规则详情",ruleHTML);
}

/**
 * 多人加班校验班次
 * 逻辑：选择多加班人了，通过使用每个人的时长和第一个人的时长进行比较，来来校验班次和规则。
 * 和第一个人的时长一样，则是同一班次，可以提交（不弹框，用户自己正常提交即可）；
 * 
 * 如果非第一个选择的员工里，有时长和第一个员工的时长不一样的员工，则这个员工和第一个员工不是同一班次，
 * 不是同一班次的员工需另行提交（存在需另行提交的员工，则弹框）。
 */
function checkClass(){
	//取参数
	var userid = $("input[name*='userid']").val(); //获取多个加班人
	var from_time = $("input[name*='from_time']").val();
	var to_time = $("input[name*='to_time']").val();
	if(!from_time || !to_time || !userid){
		return;
	}
	if(userid.indexOf(";") == -1){
		return;//未选择多个加班人，则不触发以下多人加班校验逻辑
	}
	var pram = {
		  fdId:userid,
		  from_time:from_time,
		  to_time:to_time,
		  calculate_model:"1"
	};
	var submitUser ;
	var diffUser ;
	//请求接口，获取同班次和不同班次的人员数据
	var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=checkCalcuateOvertimeTime";
	  $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        data:pram,
        error : function(data) {
      	  networkFailure();
		},
		success: function(data) {
			submitUser = data.submitUser ;
			diffUser = data.diffUser ;
		}
    });
   //如果存在不同班次的人，则弹窗处理
   var counts =Object.keys(diffUser).length;//获取需另行提交的总人数
   if(counts > 0){
	   var ids = "";
	   var names = "";
	   for(var item in diffUser){
		   if(item > 2){//标题处默认三个人名，超过3个人后面用等{人数}
			   continue;
		   }
		   if(diffUser[item].name && diffUser[item].id){
			   names = names+ diffUser[item].name + '、';
			   ids = ids+  diffUser[item].id + '、';
		   }
       }
	   if(names.indexOf('、') != -1){//如果存在顿号则去掉最后一个顿号
		   names = names.substr(0,names.length-1);
	   }
	   showFlag = false;
	   seajs.use(['lui/dialog'], function (dialog) {
		    var headerStr = names+"等"+counts+"人需另行提交";
	        var url = "/third/ding/third_ding_xform/builtin/workovertime/dialog_worktime_diff.jsp";
	            dialog.iframe(url, null, function(data){
	            	if(data&&data.result=="continueSubmit"){
	            		continueSubmit(diffUser,submitUser);//继续提交
	            	}
	            	if(data&&data.result=="showInfo"){
	            		showInfo(diffUser,submitUser);//查看详情
	            	}
	    		}, {
	                width: 460, height: 220,
	                params: {"ids": ids, "headerStr": headerStr}
	            });
	    });
   }
}

/**
 * 弹窗-继续提交
 * 1、地址本仅选中submitUser,删除需另行提交的人
 */
function continueSubmit(diffUser,submitUser){
	showFlag = true;
	var counts =Object.keys(submitUser).length;
	if(counts == 0){
		return;
	}
	var names = 'extendDataFormInfo.value(userid.name)';
	resetAddress(names,submitUser);
}
/**
 * 弹窗-查看详情
 */
function showInfo(diffUser,submitUser){
	showFlag = true;
	//1、地址本仅选中submitUser,删除需另行提交的人
	continueSubmit(diffUser,submitUser);
	//2、弹出右侧框查看详情
	document.getElementById("showInfoId").style.display="block";
	//3、头像样式调整
	$(".lui-ding-address-imgcontainer-sm").addClass("lui-ding-small-imgcontainer");
	$(".lui-ding-address-name").addClass("lui-ding-small-imgcontainer");
	$(".muiLbpmserviceAuditNoteHandler").addClass("muiLbpmserviceAuditNoteHandler_small");
}


/**
 * 校验时长
 */
function checkWorkTime() {
	var jsonStr = countWorkTimes();
	if(jsonStr && showFlag){
		checkClass();
	}
}
/**
 * 计算加班时长
 */
function countWorkTimes() {
	  var userid = $("input[name*='userid']").val(); //取加班人
	  var from_time = $("input[name*='from_time']").val();
	  var to_time = $("input[name*='to_time']").val();
	  var jsonStr = "";
	  if(!from_time || !to_time || !userid){
		  return jsonStr;
	  }
	  var pram = {
			  fdId:userid,
			  from_time:from_time,
			  to_time:to_time,
			  calculate_model:"1"
	  };
	  var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=preCalcuateOvertimeTime";
	  $.ajax({
          url: url,
          type: 'GET',
          dataType: 'json',
          data:pram,
          error : function(data) {
        	  networkFailure();
			},
			success: function(data) {
				if('0' == data.errcode){
					jsonStr = data.result ;
					//解析响应json串，将时长值赋值给文本框
					$("input[name*='duration']").val(data.result.duration);
					$("input[name*='duration']").focus();
				}else{
					//networkFailure();
				}
			}
      });
	  return jsonStr;
}

function networkFailure(){
	seajs.use(['lui/dialog'], function (dialog) {
        dialog.failure('网络异常，请稍后再试!');
    });
}

/**
 * 清除加班人
 */
function clearWorkPerson() {
	clearAddress('extendDataFormInfo.value(userid.id)','extendDataFormInfo.value(userid.name)');
}

//清空地址本操作
function clearAddress(ele_id,ele_name) {
	var address = Address_GetAddressObj(ele_name);
	address.reset(";","ORG_TYPE_ALL",true,null);
	$("input[name='"+ele_id+"']").val("");
	$("textarea[name='"+ele_name+"']").val("");
}

//重置地址本操作
function resetAddress(ele_name,diffUser) {
	var address = Address_GetAddressObj(ele_name);
	address.reset(";","ORG_TYPE_ALL",true,diffUser);
}
seajs.use(["lui/jquery","lui/topic"],function($, topic){
	
	 $("input[name*='from_time']").attr("validate","required __datetime dateLimit(31)");
	 $("input[name*='to_time']").attr("validate","required __datetime compareTime");
	 $("input[name*='duration']").attr("validate","required number checkDuration checkDurationMaxLength");
});

function ding_edit_times(){
	var $startTimeDiv = $("input[name*='from_time']").closest(".inputselectsgl");
	$startTimeDiv.on("click", function(){
		var $from_time = $("input[name='extendDataFormInfo.value(from_time)']");
		$from_time.on("change",function(){
			checkCountTimes('from_time')
		});
	});
	
	var $endTimeDiv = $("input[name*='to_time']").closest(".inputselectsgl");
	$endTimeDiv.on("click", function(){
		var $to_times = $("input[name='extendDataFormInfo.value(to_time)']");
		$to_times.on("change",function(){
			checkCountTimes('to_time')
		});
		  
	});
}

function checkCountTimes(name){
	  name = "extendDataFormInfo.value("+ name +")";
	  var from_time = $("input[name*='from_time']").val();
	  var to_time = $("input[name*='to_time']").val();
	  var userId = Com_Parameter.CurrentUserId;//取当前登录用户
	  if(from_time && to_time && userId){
		  checkWorkTime();
	  }
}

$(function(){
	var status = DingCommonnGetStatus();
	if("add" == status || "edit" == status){
		ding_edit_times();
	}
});

Com_AddEventListener(window, "load", Ding_Init);

function Ding_Init(){
	//校验器初始化
	let from_time = $("input[name*='extendDataFormInfo.value(from_time)']");
	let to_time = $("input[name*='extendDataFormInfo.value(to_time)']");
	addCompareTimeValidation('compareTime', from_time, to_time, "开始时间不能大于结束时间");
	addDateLengthValidation('dateLimit(length)', from_time, new Date().format("yyyy-MM-dd HH:mm"), "已超过审批截止时间", 31);
	addCheckDurationValidation('checkDuration', "加班总时长必须大于0");
	addCheckDurationMaxLengthValidation('checkDurationMaxLength', "加班时长不能超过1天");
}

/**
 * 校验时长大于0
 */
function addCheckDurationValidation(validatorName,errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		if(!v){
			return true;
		}
		duration = parseFloat(v);
		if (duration <= 0) {
			return false;
		}
		return true;
	});
}

/**
 * 时长不得超过1天
 */
function addCheckDurationMaxLengthValidation(validatorName,errorMessage) {
	let validation = $KMSSValidation();
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		if(!v){
			return true;
		}
		duration = parseFloat(v);
		if (duration > 24) {
			return false;
		}
		return true;
	});
}

function chosedPersonInfo(){
	document.getElementById("showInfoId").style.display = "none";
}
