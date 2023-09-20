Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
function showSelectDetail() {
	rightShow("外出明细","将在出勤统计中记为","共加班136.5小时","都快加死了！");
}

function networkFailure(){
	seajs.use(['lui/dialog'], function (dialog) {
        dialog.failure('网络异常，请稍后再试');
    });
}

/**
 * 计算外出时长
 */
function countTimes() {
	  var from_time = $("input[name*='from_time']").val();
	  var to_time = $("input[name*='to_time']").val();
	  var jsonStr = "";
	  var userId = Com_Parameter.CurrentUserId;//取当前登录用户
	  if(!from_time || !to_time || !userId){
		  return jsonStr;
	  }
	  var pram = {
			  fdId:userId,
			  from_time:from_time,
			  to_time:to_time,
			  duration_unit:"hour",
			  biz_type:2,
			  calculate_model:"1"
	  };
	  var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=preCalcuateBusinessTime";
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
				}else{
					//networkFailure();
				}
				
			}
      });
	  return jsonStr;
}
seajs.use(["lui/jquery","lui/topic"],function($, topic){
	 $("input[name*='from_time']").attr("validate","required __datetime dateLimit(31)");
	 $("input[name*='to_time']").attr("validate","required __datetime compareTime dateMaxLength(31)");
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
		  countTimes();
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
	addDateLengthValidation('dateMaxLength(length)', from_time, to_time, "外出时长不得超过{maxLength}天", 31);
	console.log(new Date().format("yyyy-MM-dd HH:mm"));
	addDateLengthValidation('dateLimit(length)', from_time, new Date().format("yyyy-MM-dd HH:mm"), "已超过审批截止时间", 31);
}
