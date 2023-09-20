Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("dingTool.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("common.css", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/css/","css",true);

var methodStatus;//当前页面类型：add/view/edit
seajs.use(["lui/jquery","lui/topic", "lui/util/str", "lui/util/env", "lui/dialog"],function($, topic, strUtil, env, dialog){
	//页面初始化
	function Ding_Init(){	 
		var status = DingCommonnGetStatus();
		//获取到后置为全局参数-后面会用到
		methodStatus = status;
		switch(status){
			case "add":
			case "edit":
				Ding_Init_Edit(status);
				break;
			case "view":
				Ding_Init_View();
				break;
		}
	}
	Com_AddEventListener(window, "load", Ding_Init);
});

//编辑页(add/edit)
function Ding_Init_Edit(status){
	initLabelWidth(status);
	//事件
	initChangeEvent();
	//校验器
	initCheckEvent();
	if("edit"==status){
		showTipInfo();
	}
}

/**
 * 校验器
 * @returns
 */
function initCheckEvent(){
	addBatchChangeCompareTimeValidation('compareTime',"还班日期必须晚于换班日期");//还班日期控件
	addBatchChangeCheckChangeTimeValidation('checkChangeTime',"换班记录重复，请重新选择");//换班日期控件
	addBatchChangeCheckReturnTimeValidation('checkReturnTime',"还班记录重复，请重新选择");//还班日期控件
	addBatchChangeCheckInterfaceValidation('checkChangeInterface',"换班异常，请重新选择");//接口校验
	addBatchChangecheckChangeUserValidation('checkChangeUser',"换班人和替班人相同时，还班日期不能为空");//还班日期控件
}

/**
 * 选择换班人和替班人后，取换班日期和还班日期，如不为空，则获取焦点触发失焦校验
 * @param trObj 行对象
 */
function checkTime(trObj,flag){
	var fd_change_date =  $(trObj).find("[name*='fd_change_date']").val();
	if(fd_change_date){
		$(fd_change_date).focus();
	}
	var fd_return_date =  $(trObj).find("[name*='fd_return_date']").val();
	if(fd_return_date){
		$(fd_return_date).focus();
	}
	var fd_change_apply_user =  $(trObj).find("[name*='fd_change_apply_user.id']").val();
	if(fd_change_apply_user){
		$(trObj).find("[name*='fd_change_apply_user.name']").focus();
	}
	var fd_change_swap_user =  $(trObj).find("[name*='fd_change_swap_user.id']").val();
	if(fd_change_swap_user){
		$(trObj).find("[name*='fd_change_swap_user.name']").focus();
	}
}

/**
 * 初始化事件
 */
function initChangeEvent(){
	$(document).on('table-add-new','table[showStatisticRow]',function(e,argus){	
		//日期控件绑定校验器
		$("[name*='fd_change_date']").attr("validate","required __date compareTime checkChangeTime checkChangeInterface");
	    $("[name*='fd_return_date']").attr("validate","__date compareTime checkReturnTime checkChangeUser");
	    
	    $("[name*='fd_change_apply_user.name']").attr("validate","required checkReturnTime checkChangeTime");
	    $("[name*='fd_change_swap_user.name']").attr("validate","required checkReturnTime checkChangeTime");
	    
	    //换班人变更事件
	    $("[name*='fd_change_apply_user.id']").on('change',function(){
	    	var trObj = $(this).parent(".inputselectsgl").parent("xformflag").parents(".xform_address").parent("td").parent("tr");
	    	$(this).focus();
	    	checkTime(trObj,false);
	    	changeCancelUserEvent(trObj);
	    });
	    
	    //替班人变更事件
	    $("[name*='fd_change_swap_user.id']").on('change',function(){
	    	var trObj = $(this).parent(".inputselectsgl").parent("xformflag").parents(".xform_address").parent("td").parent("tr");
	    	$(this).focus();
	    	checkTime(trObj,false);
	    	changeCancelUserEvent(trObj);
	    });
	    
		//换班日期变更事件-必填
		$("[name*='fd_change_date']").on('change',function(){
			var trObj = $(this).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr");
			if(!this.value){
				$(trObj).find(".fd_change_date_span").text("");
				$(trObj).find(".fd_change_date_span").css("display","none");
			}
			$(this).focus();
			checkTime(trObj,true);
			//校验换班人、还班人、换班日期是否都有值，都有值则请求接口
			changeCancelUserEvent(trObj);
		});
		
		//还班日期变更事件-非必填
		$("[name*='fd_return_date']").on('change',function(){
			var trObj = $(this).parents(".inputselectsgl").parents(".xform_datetime").parent("td").parent("tr");
			if(!this.value){
				$(trObj).find(".fd_return_date_span").text("");
				$(trObj).find(".fd_return_date_span").css("display","none");
			}			
			$(this).focus();
			checkTime(trObj,true);
			changeCancelUserEvent(trObj);
		});
	});
}

/**
 * 校验当前行内的换班人、替班人、换班日期是否都有值
 * @param tr 当前行对象
 * @returns 都有值返回true，任一无值则返回false
 */
function checkTrValue(tr){
	var fd_change_date = $(tr).find("[name*='fd_change_date']").val();
	var fd_change_swap_user = $(tr).find("[name*='fd_change_swap_user.id']").val();
	var fd_change_apply_user = $(tr).find("[name*='fd_change_apply_user.id']").val();	
	if(fd_change_date && fd_change_swap_user && fd_change_apply_user){
		return true;
	}
	return false;
}

/**
 * 校验是否可换班，并获取班次
 * @returns
 */
function requestCanRelieveCheck(trObj){
	 var applicantStaff_ekpid = $(trObj).find("[name*='fd_change_apply_user.id']").val();//换班人id
	 var reliefStaff_ekpid = $(trObj).find("[name*='fd_change_swap_user.id']").val();//替班人id
	 var relieveDatetime = $(trObj).find("[name*='fd_change_date']").val();//换班日期-yyyy-mm-dd
	 var backDatetime = $(trObj).find("[name*='fd_return_date']").val();//还班日期	
	 if(applicantStaff_ekpid == reliefStaff_ekpid && !backDatetime){
		 $(trObj).find("[name*='fd_return_date']").focus();
		 return;
	 }
	 //后期需把test=true&flag=true两个参数去掉（因接口还没和钉钉联通，加此两个参数仅供模拟调试）
	 var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=canRelieveCheck&applicantStaff_ekpid="+applicantStaff_ekpid +
	 		"&reliefStaff_ekpid="+reliefStaff_ekpid+"&relieveDatetime="+relieveDatetime;
	 if(backDatetime){
		 url += "&backDatetime="+backDatetime;
	 }
	 var pram ={};
	 $(trObj).find(".fd_change_date_tip").text("");
	 $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        data:pram,
        error : function(data) {
        	$(trObj).find(".fd_change_date_span").addClass("noChange");
        	$(trObj).find(".fd_change_date_tip").text("接口请求异常，请刷新后重试");
			$(trObj).find(".fd_change_date_span").focus();
      	  	networkFailure("请求异常，请刷新后重新操作");
      	  	console.log("页面请求异常，请刷新后重新操作。url:"+url+"---->responseData:"+JSON.stringify(data));
        },
		 success: function(data) {
			if(data && data.errcode==0){//允许换班
				var formList = data.result.form_data_list;
				if(null != formList && formList.length>0  && null != formList[0]["value"]){
					$(trObj).find("[name*='change_extend_value']").val(formList[0]["extend_value"]);
					$(trObj).find("[name*='change_value']").val(formList[0]["value"]);
					$(trObj).find(".fd_change_date_span").removeClass("noChange");
					$(trObj).find(".fd_change_date_span").focus();
					var _extend_value = formList[0]["extend_value"];
					var _value = JSON.parse(formList[0]["value"]);
					var relieveInfo = _value["relieveInfo"];
					if(relieveInfo){
						$(trObj).find(".fd_change_date_span").html(relieveInfo);
						$(trObj).find(".fd_change_date_span").css("display","block");
					}
					var backInfo = _value["backInfo"];
					if(backInfo){
						$(trObj).find(".fd_return_date_span").html(backInfo);
						$(trObj).find(".fd_return_date_span").css("display","block");
					}
					return;
				}
			}
			//
			$(trObj).find(".fd_change_date_span").addClass("noChange");
			$(trObj).find("[name*='fd_change_date']").focus();
			$(trObj).find(".fd_return_date_tip").text(data.errmsg);
			$(trObj).find(".fd_change_date_tip").text(data.errmsg);
			
			$(trObj).find("[name*='change_extend_value']").val("");
			$(trObj).find("[name*='change_value']").val("");
			$(trObj).find(".fd_change_date_span").html("");
			$(trObj).find(".fd_change_date_span").css("display","none");
			$(trObj).find(".fd_return_date_span").html("");
			$(trObj).find(".fd_return_date_span").css("display","none");
		 }
    });
}

/**
 * 申请人/替班人变更事件-
 */
function changeCancelUserEvent(trObj){
	if(trObj && checkTrValue(trObj)){
		requestCanRelieveCheck(trObj);
	}
}

//查看页
function Ding_Init_View(){
	showTipInfo();
}

function showTipInfo(){
	$("[name*='change_value']").each(function() {
		if($(this).val()){
			var _value = JSON.parse($(this).val());
			var tr = $(this).parent("xformflag").parent(".xform_inputText").parent("td").parent("tr");
			var relieveInfo = _value["relieveInfo"];
			if(relieveInfo){
				$(tr).find(".fd_change_date_span").html(relieveInfo);
				$(tr).find(".fd_change_date_span").html(relieveInfo);
				$(tr).find(".fd_change_date_span").css("display","inline-block");
			}
			var backInfo = _value["backInfo"];
			if(backInfo){
				$(tr).find(".fd_return_date_span").html(backInfo);
				$(tr).find(".fd_return_date_span").css("display","inline-block");
			}
		}
	});
}

//左侧label所在td的宽度和主题label所在td宽度保持一致
function initLabelWidth(status){	
	var docSubjectLabelWidth = $("[name='docSubject']").parent("div").parent("td").prev("td").css('width');
	$('[name="extendDataFormInfo.value(fd_change_remark)"]').parent("xformflag").parent(".xform_textArea").parent("td").prev("td").css("width",docSubjectLabelWidth);
}