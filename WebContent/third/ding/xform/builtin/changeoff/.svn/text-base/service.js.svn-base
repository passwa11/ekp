/**
 * 请假流程函数接口
 */
Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
Com_IncludeFile("ding_right.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);

seajs.use(["lui/jquery","lui/topic", "lui/util/str", "lui/util/env", "lui/dialog"],function($, topic, strUtil, env, dialog){
	
	
});

function Ding_Init(){
	//清除替班人
	$("[name='clear_replacer']").on('click',function(){
		Ding_ChangeOff_ClearPerson("substitute");
	});
	var method = Com_GetUrlParameter(location.href,"method");
	//替班人为自己时,还班日期为必填
	if (method != "view") {
		var substitue = $form("substitute.id");
		if (substitue.target && substitue.target.element) {
			substitue.target.element.on('change',function(){
				var useId = Com_Parameter.CurrentUserId;
				var replacer = $form("substitute.id").val();
				if (useId === replacer) {
					$form("return_date").required(true);
				} else {
					$form("return_date").required(false);
				}
			});
		}
		$("[name='clear_replacer']").removeClass("xform_label_hide");
		$("xformflag[flagid='substitute']").closest("label").width("524px");
	}
	if (method === "add") {
		Com_Parameter["event"]["submit"].push(Ding_Changeoff_Submit_Validate);
	}
}

function Ding_ChangeOff_ClearPerson(name){
	$form(name+".id").val("");
	$form(name+".name").val("");
}

function Ding_Changeoff_Submit_Validate(){
	//获取申请人的排班方式
	var useId = Com_Parameter.CurrentUserId;
	var useGroup = Ding_Changeoff_GetUserGroup(useId);
	//获取替换人的排班方式
	var replacer = $form("substitute.id").val();
	var replacerGroup = Ding_Changeoff_GetUserGroup(replacer);
	//当选择非排班制员工,提示
	if (useGroup && useGroup.group_id && replacerGroup && replacerGroup.group_id) {
		return true;
	} else {
		seajs.use( [ 'lui/dialog' ], function(dialog) {
			dialog.alert("换班仅支持排班制考勤,你所在考勤组为非排班考勤组,无法换班成功");
		});
		return false;
	}
	
}

function Ding_Changeoff_GetUserGroup(useId){
	var requestUrl = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=getUserGroup" +
	"&userid=" + useId;
	var useGroup;
	$.ajax({
		url : requestUrl,
		dataType : "json",
		async: false,
		type : "GET",
    	jsonp:"jsonpcallback",
    	success: function(data){
    		console.log(data);
    		if (data.errcode === 0) {
    			useGroup = data.result;
    		}
    	}
	});
	return useGroup
}

Com_AddEventListener(window, "load", Ding_Init);