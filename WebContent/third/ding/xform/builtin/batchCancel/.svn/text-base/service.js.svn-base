Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
Com_IncludeFile("dingTool.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
Com_IncludeFile("common.css", Com_Parameter.ContextPath + "third/ding/xform/resource/css/","css",true);
Com_IncludeFile("cancelLevelDetail.css", Com_Parameter.ContextPath + "third/ding/xform/resource/css/","css",true);

var sumLeaveTime="";//当前被选中的请假单请假总时长
var methodStatus;//当前页面类型：add/view/edit
var cancelUserId;//选中的销假人
var singerLeaveForm;//被选中的单条请假单
var fd_all_form_json ={}; //接口获取到的销假人的请假单json数据

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
		initNoShowContent("none");//页面加载时需处理的部分
	}
	Com_AddEventListener(window, "load", Ding_Init);
});

//编辑页(add/edit)
function Ding_Init_Edit(status){
	cancelUserId = $("[name='extendDataFormInfo.value(fd_cancel_user.id)']").val();//销假人-全局参数
	initLeaveFormData();
	initCancelEvent();
}

/**
 * 加载时去获取当前销假人的请假单数据--请求接口后置为全局参数
 */
function initLeaveFormData(){
	 var userId = cancelUserId;//取当前登录用户
	 var levelForm = $("select[name*='extendDataFormInfo.value(fd_leave_form)']");
	  if(!userId || !levelForm){
		  networkFailure("页面初始化异常，请刷新后重新操作");
		  console.log("页面初始化异常，请刷新后重新操作---->initLeaveFormData--userId"+userId+",levelForm:"+levelForm);
		  return ;
	  }
	  var pram = {};
	  levelForm.empty();
	  levelForm.append('<option value="">==请选择==</option>');
	  var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=cancelLeave&ekpUserId="+userId;
	  $.ajax({
         url: url,
         type: 'GET',
         dataType: 'json',
         data:pram,
         error : function(data) {
       	  	networkFailure("初始化请假单接口请求异常");
       	  	console.log("初始化请假单接口请求异常。url:"+url);
         },
		 success: function(data) {
			if(data && data.length>0){
				var prams=[];
				for (var i = 0; i < data.length; i++) {
					 prams.push(data[i]);
					 if(data[i] && data[i].fd_ekp_instance_id && data[i].fd_name){
						 levelForm.append('<option value="' + data[i].fd_ekp_instance_id + '">' + data[i].fd_name + '</option>');//初始化下拉选择
						 fd_all_form_json[data[i].fd_ekp_instance_id]=data[i];//组装请假单全局参数
					 }
				}		
				if(fd_all_form_json){
					$("[name*='extendDataFormInfo.value(fd_all_form)']").val(JSON.stringify(fd_all_form_json));//保存获取到的所有请假单
				}
			}else{
				networkFailure("当前销假人无可销假的请假单");
	       	  	console.log("当前销假人无可销假的请假单。data:"+data);
			}		
		 }
     });	  
}

//销假类型-默认选中全部销假
function initSelectType(){
	//初始化部分全局变量
	sumLeaveTime="";
	singerLeaveForm="";
	
	//默认选中全部销假
	$("input[name*='extendDataFormInfo.value(fd_cancel_type)']").each(function() {
		if ($(this).val() != "all") {
			$(this).removeAttr("checked");
		} else {
			$(this).prop("checked", "checked");
		}
	});
}

//为批量请假的请假单渲染请假单行下面的table
function loadFormInfoForBatch(singer_form_json,formTable){
	var batch_leave_table = singer_form_json["batch_leave_table"];
	if(batch_leave_table && batch_leave_table.length>0){
		var type = "";
		var startTime = "";
		var endTime = "";
		var sc = "";
		var unit = "";
		var startTimeOne="";
		var endTimeOne="";
		var trStr;
		$(formTable).append("<tr class='formtr trtitle'><td class='typeTd'>请假类型</td><td class='startTimeTd'>请假开始时间</td><td class='endTimeTd'>请假结束时间</td><td class='scTd'>请假时长</td></tr>")
		for (var i = 0; i < batch_leave_table.length; i++) {
			trStr ="";
			type = batch_leave_table[i]["fd_type_name"];//请假类型
			if(type.indexOf("（")>0){//截取请假类型
				type= type.substring(0,type.indexOf("（"))
			}
			trStr += "<tr class='formtr'><td class='typeTd'>"+type+"</td>";
			startTime = batch_leave_table[i]["fd_leave_start_time"]["time"];//请假开始时间
			endTime = batch_leave_table[i]["fd_leave_end_time"]["time"];//请假结束时间
			sc = batch_leave_table[i]["fd_item_duration"];//请假时长
			unit = batch_leave_table[i]["type_unit"];
			startTime = timespaceToTime(startTime,unit);
			endTime = timespaceToTime(endTime,unit);
			if("halfDay"==unit){//半天制组装请假时间
				if("AM"==batch_leave_table[i]["fd_start_time_one"]){
					startTimeOne = "上午";
				}else{
					startTimeOne = "下午";
				}
				if("AM"==batch_leave_table[i]["fd_end_time_one"]){
					endTimeOne = "上午";
				}else{
					endTimeOne = "下午";
				}
				startTime = startTime+" "+startTimeOne;
				endTime = endTime+" "+endTimeOne;
			}
			
			//组装tr和td塞进table
			trStr += "<td class='startTimeTd'>"+startTime+"</td>";
			trStr += "<td class='endTimeTd'>"+endTime+"</td>";
			trStr += "<td class='scTd'>"+sc+"</td></tr>";
			$(formTable).append(trStr);
		}
	}
}

//为单个请假的请假单渲染请假单行下面的table
function loadFormInfoForSinger (singer_form_json,formTable){
	if(singer_form_json && singer_form_json["leave_txt"]){
		var type = singer_form_json["leave_txt"];
		var startTime = singer_form_json["from_time_str"];
		var endTime = singer_form_json["to_time_str"];
		var sc = "";
		var unit = singer_form_json["unit"];
		var startTimeOne=singer_form_json["from_half_day_str"];
		var endTimeOne=singer_form_json["to_half_day_str"];
		var trStr;
		$(formTable).append("<tr class='formtr trtitle'><td class='typeTd'>请假类型</td><td class='startTimeTd'>请假开始时间</td><td class='endTimeTd'>请假结束时间</td><td class='scTd'>请假时长</td></tr>")

		startTime = timespaceToTime(startTime,unit);
		endTime = timespaceToTime(endTime,unit);
		if("halfDay"==unit){//半天制组装请假时间
			if("AM"==startTimeOne){
				startTimeOne = "上午";
			}else{
				startTimeOne = "下午";
			}
			if("AM"==endTimeOne){
				endTimeOne = "上午";
			}else{
				endTimeOne = "下午";
			}
			startTime = startTime+" "+startTimeOne;
			endTime = endTime+" "+endTimeOne;
		}
		var extend_value = JSON.parse(singer_form_json["extend_value"]);
		var sysc = "0";
		if("DAY"==extend_value["unit"]){
			sc = extend_value["durationInDay"]+" 天";
			sysc = $("[name*='extendDataFormInfo.value(fd_cancel_surplus_time)']").val()+" 天";
		}else{
			sc = extend_value["durationInHour"]+" 小时";
			sysc = $("[name*='extendDataFormInfo.value(fd_cancel_surplus_time)']").val()+" 小时";
		}
		//单个请假数据类型不同，需重新给销假总时长、剩余请假时长赋值
		$("[name*='extendDataFormInfo.value(fd_cancel_sum_time)']").val(sc);
		$("[name*='extendDataFormInfo.value(fd_cancel_surplus_time)']").val(sysc);
		
		trStr += "<tr class='formtr'><td class='typeTd'>"+type+"</td>";
		//组装tr和td塞进table
		trStr += "<td class='startTimeTd'>"+startTime+"</td>";
		trStr += "<td class='endTimeTd'>"+endTime+"</td>";
		trStr += "<td class='scTd'>"+sc+"</td></tr>";
		$(formTable).append(trStr);
	}
}

/**
 * 请假单选择事件
 * 选中一条请假单后，处理请假时长等信息
 */
function selectLevelFormEvent(){
	$("[name*='extendDataFormInfo.value(fd_leave_form)']").on('change',function(){
		//1、默认全部销假
		initSelectType();
		var levelForm = $("select[name*='extendDataFormInfo.value(fd_leave_form)']").children('option:selected').val();//被选中下拉选项value
		var levelFormText = $("select[name*='extendDataFormInfo.value(fd_leave_form)']").children('option:selected').text();//被选中下拉选项value
		if(!levelForm){
			$(".form_div").css("display","none");
			$("[name*='extendDataFormInfo.value(fd_cancel_sum_time)']").val("系统自动计算");
			$("[name*='extendDataFormInfo.value(fd_cancel_surplus_time)']").val("系统自动计算");
			$("[name*='extendDataFormInfo.value(fd_select_form)']").val("");
			$("[name*='extendDataFormInfo.value(fd_form_name)']").val("");
			$("[name*='extendDataFormInfo.value(fd_table_all_tr)']").val("");
			$(".fd_leave_form_tip").css("display","none");
			return;
		}
		$("[name*='extendDataFormInfo.value(fd_form_name)']").val(levelFormText);
		
		//2、从所有请假单信息中取出选中的请假单的信息，存进fd_select_form
		var fd_all_form = $("[name*='extendDataFormInfo.value(fd_all_form)']").val();
		fd_all_form = JSON.parse(fd_all_form);		
		var singer_form_json = fd_all_form[levelForm];
		$("[name*='extendDataFormInfo.value(fd_select_form)']").val(JSON.stringify(singer_form_json));
		
		//2.1：展示提示语
		var leaveEndTime = singer_form_json["leaveEndTime"];
		if(leaveEndTime){
			leaveEndTime = addDateTime(leaveEndTime,30);
			$(".fd_leave_form_tip_date").text(leaveEndTime);
			$(".fd_leave_form_tip").css("display","inline-block");
		}else{
			$(".fd_leave_form_tip").css("display","none");
		}
		
		//3、填充销假总时长和剩余请假时长
		$("[name*='extendDataFormInfo.value(fd_cancel_sum_time)']").val(singer_form_json["fd_sum_duration"]);
		$("[name*='extendDataFormInfo.value(fd_cancel_surplus_time)']").val(0);
				
		//4、取选中的下拉选项，渲染请假单列表：需同时兼容批量请假和单个请假
		$(".form_div").css("display","block");
		var formTable = $(".form_div").find("table");
		$(formTable).find("tr").remove();
		if(singer_form_json["batch"]){//批量请假
			loadFormInfoForBatch(singer_form_json,formTable);
		}else{//单个请假
			loadFormInfoForSinger(singer_form_json,formTable);
		}
		//console.log($(".form_div").find("table").find("tbody").html());
		$("[name*='extendDataFormInfo.value(fd_table_all_tr)']").val($(".form_div").find("table").find("tbody").html());
	});
}

/**
 * 销假选择类型事件--预留-后续部分销假时补充
 */
function selectCancelTypeEvent(){
	
}
/**
 * 销假人变更事件-
 * 1、清除已选的数据
 * 2、重新用新的销假人请求接口获取请假单数据
 */
function changeCancelUserEvent(){
	$("[name='extendDataFormInfo.value(fd_cancel_user.id)']").on('change',function(){
		//1、重置全局参数
		cancelUserId = $("[name='extendDataFormInfo.value(fd_cancel_user.id)']").val();//销假人-全局参数
		sumLeaveTime="";
		singerLeaveForm="";
		fd_all_form_json ={};
		
		//2、隐藏请假单下方的table列表（清掉tr再隐藏）
		var formTableChange = $(".form_div").find("table");
		$(formTableChange).find("tr").remove();
		$(".form_div").css("display","none");
		
		//3、清掉页面数据和隐藏值
		$("[name*='extendDataFormInfo.value(fd_cancel_sum_time)']").val("系统自动计算");
		$("[name*='extendDataFormInfo.value(fd_cancel_surplus_time)']").val("系统自动计算");
		$("[name*='extendDataFormInfo.value(fd_select_form)']").val("");
		$("[name*='extendDataFormInfo.value(fd_all_form)']").val("");
		$("[name*='extendDataFormInfo.value(fd_form_name)']").val("");
		$("[name*='extendDataFormInfo.value(fd_table_all_tr)']").val("");
		//4、重新请求接口初始化变更后销假人的请假单下拉列表
		initLeaveFormData();
		//5、默认选中全部销假
		initSelectType();
	});
}

/**
 * 初始化事件
 */
function initCancelEvent(){
	//请假单点选事件
	selectLevelFormEvent();
	//销假选择事件--预留-后续部分销假时补充
	selectCancelTypeEvent();
	//销假人变更事件
	changeCancelUserEvent();
}

window.validTips = function (msg){
	var fd_leave_form = $("[name='extendDataFormInfo.value(fd_leave_form)']");
	if($(fd_leave_form).hasClass("checkDocValid")){
		networkFailure(msg);
	}
}

window.Ding_Validate_Submit = function(){
	//点击提交时，判断是否超期，如超期，且选择的是通过，则提示，并且阻止提交
	var fd_leave_form = $("[name='extendDataFormInfo.value(fd_leave_form)']");
	if($(fd_leave_form).hasClass("checkDocValid")){
		var oprGroups = $("[name*='oprGroup']");
		networkFailure("已超出最晚销假时间，操作无效");
		return false;
	}
	return true;
}

function checkDocValid(){
	//取消通过的默认选中
	$("[name*='oprGroup']").each(function() {
		if($(this).val().indexOf("handler_pass") != -1){
			this.checked=false;
		}
	});
	var leave_code = $("[name='extendDataFormInfo.value(fd_leave_form)']").val();
	if(!leave_code){
		networkFailure("操作异常，请刷新后重试。");
		return;
	}	
	var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=cancelLeaveExpire&fd_ekp_instance_id="+leave_code;
	$.ajax({
       url: url,
       type: 'GET',
       dataType: 'json',
       error : function(data) {
     	  	networkFailure("thirdDingAttendance.cancelLeaveExpire is exception： fd_ekp_instance_id"+leave_code);
     	  	console.log("thirdDingAttendance.cancelLeaveExpireis exception。data:"+data);
     	  	$("[name='extendDataFormInfo.value(fd_leave_form)']").addClass("checkDocValid");
       },
       success: function(data) {
    	   if(data && data.errcode == 0 && data.cancel){
    		   //可以销假
    		   $("[name='extendDataFormInfo.value(fd_leave_form)']").removeClass("checkDocValid");
    	   }else{
    		   $("[name='extendDataFormInfo.value(fd_leave_form)']").addClass("checkDocValid");
    	   }
       }
	});
}

//查看页
function Ding_Init_View(){
	//1、隐藏部分销假的明细表-后续开发
	$("#TABLE_DL_fd_cancel_detail_table").parent("td").parent("tr").css("display","none");
	
	//2、隐藏部分销假单选按钮--暂时不做，后续做时再删除-放开--start
	$(".xform_inputRadio").find("xformflag").find("label").each(function() {
		//console.log($(this).text())
		if ($(this).text().indexOf("部分销假") >= 0) {
			$(this).css("display","none");
		}
	});
	//2、隐藏部分销假单选按钮--暂时不做，后续做时再删除-放开--end
	
	//2.1：查看页加载时，请求接口校验，判断是否超期
	checkDocValid();
	//2.2：审批时：点击通过按钮时，如接口校验超期，则给出提示
	$("[name*='oprGroup']").on('click',function(){
		var oprValue = $(this).val();
		if(oprValue.indexOf("handler_pass") != -1){
			var fd_leave_form = $("[name='extendDataFormInfo.value(fd_leave_form)']");
			if($(fd_leave_form).hasClass("checkDocValid")){
				networkFailure("已超出最晚销假时间，操作无效");
			}
		}
	});
	/*#150022-钉钉套件，批量销假，流程特权处理时无反应-开始
	var authorityOptButtonAttr = $("#authorityOptButton").attr("onclick");
	$("#authorityOptButton").removeAttr("onclick");
	#150022-钉钉套件，批量销假，流程特权处理时无反应-结束*/
	//2.3：特权人
	$("#authorityOptButton").on('click',function(){
		if(!this.hasAttribute("onclick")){
			var _self = this;
			var fd_leave_form = $("[name='extendDataFormInfo.value(fd_leave_form)']");
			if($(fd_leave_form).hasClass("checkDocValid")){
				seajs.use(['lui/dialog'], function(dialog) {
					dialog.confirm('已超出最晚销假时间，终审通过无效，请勿选择！',function(value){
						if(value==true){
							_self.setAttribute("onclick",authorityOptButtonAttr);
							_self.click();
						}
					});
				});
			}
		}
	});
	
	Com_Parameter.event["submit"].push(Ding_Validate_Submit);

	//3、展示请假单和请假单下面的table
	var trStr = $("[name*='fd_table_all_tr']").val();
	if(trStr.indexOf("tr") > -1 && trStr.indexOf("td") > -1 && trStr.indexOf("formtr")>-1){
		var formTable = $(".form_div").find("table");
		$(formTable).find("tr").remove();
		$(".form_div").find("table").find("tbody").append(trStr);
		$(".form_div").css("display","block");
	}else{
		//兼容移动端的数据（copy移动端）
		var fd_table_all_tr_str = trStr
		var fd_table_all_tr = JSON.parse(fd_table_all_tr_str);
        var dataList = new Array;
        if(fd_table_all_tr.batch){
            var batch_leave_table =  fd_table_all_tr.batch_leave_table;
            dataList = batch_leave_table.map(function(ele,index,list){
                 var type_unit =  ele.type_unit;
                 var leaveType = ele.fd_type_name.substring(0,ele.fd_type_name.indexOf("(")==-1?(ele.fd_type_name.indexOf("（")):ele.fd_type_name.indexOf("("));
                 leaveType = leaveType.length == 0 ? ele.fd_type_name : leaveType;
                 var startDate = window.timespaceToTime(ele.fd_leave_start_time.time,type_unit);
                 var endDate = window.timespaceToTime(ele.fd_leave_end_time.time,type_unit);
                 var often = ele.fd_item_duration;
                 if(type_unit){
                     if(type_unit.toLowerCase() == 'day'){

                     }else if(type_unit.toLowerCase() == 'halfday'){
                          var  fd_start_time_one =  ele.fd_start_time_one;
                          var  fd_end_time_one =  ele.fd_end_time_one;
                          if(fd_start_time_one.toLowerCase()=="am"){
                               startDate = startDate.substr(0,10) +" 上午";
                          }else if(fd_start_time_one.toLowerCase()=="pm"){
                               startDate = startDate.substr(0,10) +" 下午"
                          }
                          if(fd_end_time_one.toLowerCase()=="am"){
                               endDate = endDate.substr(0,10) +" 上午";
                          }else if(fd_end_time_one.toLowerCase()=="pm"){
                               endDate = endDate.substr(0,10) +" 下午"
                          }

                     }else if(type_unit.toLowerCase() == 'hour'){

                     }
                 }
                 var returnObj ={"leaveType":leaveType,"startDate":startDate,"endDate":endDate,"often":often};
                 return returnObj;
            });
        }else{
            var unit = fd_table_all_tr.unit;
            var leaveType = fd_table_all_tr.leave_txt;
            var to_half_day_str = fd_table_all_tr.to_half_day_str;//am,pm
            var startDate = fd_table_all_tr.from_time_str;
            var endDate = fd_table_all_tr.to_time_str;
            var extend_value = JSON.parse(fd_table_all_tr.extend_value);
            var often = "";
            if(unit.toLowerCase()=='day'){
                  startDate = startDate.substr(0,10);
                  endDate = endDate.substr(0,10);
                  often = extend_value.durationInDay + "天";
            }else if(unit.toLowerCase()=='halfday'){
                  startDate = startDate.substr(0,10);
                  endDate = endDate.substr(0,10);
                  often = extend_value.durationInDay + "天";
                  if(to_half_day_str.toLowerCase()=='am'){
                     startDate = startDate + " 上午";
                  }else if(to_half_day_str.toLowerCase()=='pm'){
                     endDate = endDate + " 下午";
                  }

            }else if(unit.toLowerCase()=='hour'){
                 often = extend_value.durationInHour + "小时";
            }
            var data = {
                "leaveType":leaveType,
                "startDate":startDate,
                "endDate":endDate,
                "often":often
            }
            dataList.push(data);
        }
        $("#selectLeaveFormItem_detailView_tr").show();//显示
        var tableHtml = drow_table(dataList);
	}
	//3.1 展示名称
	$(".view_level_form_span").text($("[name*='fd_form_name']").val());
	$(".view_level_form").css("display","block");
	
	//4、隐藏编辑按钮
	hideEditBtn();
}

function drow_table(dataList){
    if(!dataList || dataList.length ==0)return;
    $(formTable).find("tr").remove();
    var formTable = $(".form_div").find("table").find("tbody");
    var trStr;
	$(formTable).append("<tr class='formtr trtitle'><td class='typeTd'>请假类型</td><td class='startTimeTd'>请假开始时间</td><td class='endTimeTd'>请假结束时间</td><td class='scTd'>请假时长</td></tr>")
	for (var i = 0; i < dataList.length; i++) {
		trStr ="";
		trStr += "<tr class='formtr'><td class='typeTd'>"+dataList[i].leaveType+"</td>";
		//组装tr和td塞进table
		trStr += "<td class='startTimeTd'>"+dataList[i].startDate+"</td>";
		trStr += "<td class='endTimeTd'>"+dataList[i].endDate+"</td>";
		trStr += "<td class='scTd'>"+dataList[i].often+"</td></tr>";
		$(formTable).append(trStr);
		$(".form_div").css("display","block");
	}
}

/**
 * 页面加载时，需隐藏的内容：
 * 明细表、请假单下面的请假列表等
 */
window.initNoShowContent = function (value){
	//1、部分销假的明细表-
	$("#TABLE_DL_fd_cancel_detail_table").parent("td").parent("tr").css("display",value);
	
	//2、隐藏部分销假单选按钮--暂时不做，后续做时再删除-放开--start
	$("[name*='extendDataFormInfo.value(fd_cancel_type)']").each(function() {
		if ("part" == $(this).val()) {
			$(this).parent(".lui-lbpm-radio").css("display","none");
		}
	});
	//2、隐藏部分销假单选按钮--暂时不做，后续做时再删除-放开--end
	
	//3、优化页面控件样式
	$("[name*='extendDataFormInfo.value(fd_cancel_sum_time)']").css("background","#dddddd");
	$("[name*='extendDataFormInfo.value(fd_cancel_sum_time)']").width(300);
	$("[name*='extendDataFormInfo.value(fd_cancel_sum_time)']").height(30);
	$("[name*='extendDataFormInfo.value(fd_cancel_surplus_time)']").css("background","#dddddd");
	$("[name*='extendDataFormInfo.value(fd_cancel_surplus_time)']").width(300);
	$("[name*='extendDataFormInfo.value(fd_cancel_surplus_time)']").height(30);
	if("view"!=methodStatus){
		$("[name*='extendDataFormInfo.value(fd_leave_form)']").width(295);
	}	
}