Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
debug_flag = true;
function networkFailure(){
	seajs.use(['lui/dialog'], function (dialog) {
        dialog.failure('网络异常，请稍后再试!');
    });
}
/**
 * 清除同行人
 */
function clearTripPersons() {
	clearAddress('extendDataFormInfo.value(trip_persons.id)','extendDataFormInfo.value(trip_persons.name)');
}


/**
 * 计算时长
 */
function countTimes(obj){
	var jsonStr = "";
	var from_time = $(obj).closest(".ding_detailstable_row").find("[name*='from_time']").val();
	var to_time = $(obj).closest(".ding_detailstable_row").find("[name*='to_time']").val();
	
	var from_day_type = $(obj).closest(".ding_detailstable_row").find("[name*='from_day_type']").val();
	var to_day_type = $(obj).closest(".ding_detailstable_row").find("[name*='to_day_type']").val();
	var userId = Com_Parameter.CurrentUserId;//取当前登录用户
    if(!from_time || !to_time || !from_day_type || !to_day_type || !userId){
	  return jsonStr;
    }
    var pram = {
		  fdId:userId,
		  from_time:from_time+" "+from_day_type,
		  to_time:to_time+" "+to_day_type,
		  duration_unit:"halfDay",
		  calculate_model:"0"
    };
	var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=preCalcuateTripTime";
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
					$(obj).closest(".ding_detailstable_row").find("[name*='duration']").val(data.result.duration);
				}else{
					//networkFailure();
				}
				
			}
      });
	  countDays();
}
/**
 * 计算出差天数
 */
function countDays(){
	var day = 0; 
	 $("input[name*='duration']").each(function(j,item){
		    day += Math.ceil(item.value);
	 });
	 $("input[name*='days']").val(day);
}

//清空地址本操作
function clearAddress(ele_id,ele_name) {
	var address = Address_GetAddressObj(ele_name);
	address.reset(";","ORG_TYPE_ALL",true,null);
	$("input[name='"+ele_id+"']").val("");
	$("textarea[name='"+ele_name+"']").val("");
}


seajs.use(["lui/jquery","lui/topic", "lui/util/str", "lui/util/env", "lui/dialog"],function($, topic, strUtil, env, dialog){
	//校验开始时间不得大于结束时间
	 $("input[name*='from_time']").attr("validate","required __date compareTime compareTimeScope");
	 $("input[name*='to_time']").attr("validate","required __date compareTime compareTimeScope");
	 $("input[name*='duration']").attr("validate","required number min(0)");
});

Com_AddEventListener(window, "load", Ding_Init);

function Ding_Init(){
	//初始化校验器
	addCompareDateValidation('compareTime', "结束时间必须晚于开始时间");
	addCompareMoreDateValidation('compareTimeScope', "多个行程之间，开始结束时间不能有重叠");
	
	$("input[name*='days']").on("click", function(){
		countDays();
	});	
}

/**
 * 如果是日期类型则加上时分秒
 */
function toDateTime(newdate){
	if(newdate && newdate.indexOf(':') == -1 ){
		newdate = newdate + " 00:00:00";
	}
	return newdate;
}
/**
 * 多个行程之间，开始结束时间不能有重叠。
 * 即：下一个行程的开始时间和结束时间不能在上一条行程的开始结束时间之间
 */
function addCompareMoreDateValidation(validatorName,errorMessage) {
	let validation = $KMSSValidation();
	var fdStartTimeTr;
	var fdEndTimeTr;	
	var trText;
	validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		trText = $(e).closest(".ding_detailstable_row").find(".ding_detailstable_desc_txt").text();//取当前行程
		//1、截取当前行程序号
		var trNo = parseInt(trText.substr(trText.length-2,1));
		if(trNo == 1){//如果当前是第一个行程，则无需此校验
			return true;
		}
		//2、取出当前行的开始时间和结束时间
		fdStartTimeTr = $(e).closest(".ding_detailstable_row").find("[name*='from_time']").val();
		fdEndTimeTr = $(e).closest(".ding_detailstable_row").find("[name*='to_time']").val();
		if(!fdStartTimeTr){
			return true;
		}		
		fdStartTimeTr = toDateTime(fdStartTimeTr);
		fdEndTimeTr = toDateTime(fdEndTimeTr);			
		var start = Date.parse(fdStartTimeTr);
		var end = Date.parse(fdEndTimeTr);
		
		var fromTimeName;
		var from_time;
		var toTimeName;
		var to_time;
		//3、循环获取当前行程上面的行程，并且循环校验当前行程和上面的行程是否重叠
		for (i = 0; i < trNo-1; i++) {
			  fromTimeName = "extendDataFormInfo.value(fd_trips_div."+i+".from_time)";
			  toTimeName = "extendDataFormInfo.value(fd_trips_div."+i+".to_time)";
			  
			  from_time = $("input[name*='"+ fromTimeName +"']").val();//从第一个行程起，循环获取每个行程的开始时间
			  to_time = $("input[name*='"+ toTimeName +"']").val();//从第一个行程起，循环获取每个行程的结束时间
			  //4、如果上面的开始时间或者结束时间为空，则不做改行的校验
			  if(!from_time || !to_time){
				  continue;
			  }
			  
			  from_time = toDateTime(from_time);
			  to_time = toDateTime(to_time);
				
			  var startbefor = Date.parse(from_time);
			  var endbefor = Date.parse(to_time);
			  //校验1：当前的开始时间在上面的开始时间和结束时间之间，不行
			  if(start >= startbefor && start <= endbefor){
				  console.log("startTime:"+start+"在"+startbefor+"--"+endbefor+"之间！");
				  return false;
			  }
			  //校验2：当前的开始时间在上面的开始时间之前，则需校验结束时间是否小于上面的开始时间
			  if(start <= startbefor){
				  if(!end){
					  //当前结束时间为空，则暂不校验
					  continue;
				  }else{
					  if(end >= startbefor){
						  console.log("start:"+start+"-end:"+end+"--startbefor:"+startbefor);
						  return false;  
					  }
				  }
			  }
		}
		return true;
	});
}
