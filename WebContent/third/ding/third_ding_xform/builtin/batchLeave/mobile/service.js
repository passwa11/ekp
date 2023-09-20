define(["dojo/_base/declare",
		"dojo/query",
		"sys/xform/mobile/controls/xformUtil",
		"third/ding/third_ding_xform/resource/js/mobile/common",
		"dojo/dom-style",
		"third/ding/third_ding_xform/resource/js/mobile/validatorUtil",
		"mui/i18n/i18n!sys-mobile:mui",
		"dojo/topic",
		"mui/util",
		"dojo/request",
		"dojo/_base/array",
		"dojo/dom-construct",
		"third/ding/third_ding_xform/resource/js/ding_right",
		"dijit/registry", 
		"mui/dialog/Tip"
],function(declare, query, xformUtil, commonUtil, domStyle, validatorUtil, msg, topic, util, request, array, domConstruct,right,registry ,Tip){	
	// 业务逻辑
	var Service = declare("third.ding.third_ding_xform.builtin.batchLeave.mobile.service", null , {
		// 下拉框的窗口关闭事件
		SELECT_CALLBACK : 'mui/form/select/callback',
		
		ADDRESS_CALLBACK : '/mui/form/valueChanged',
		
		// 日期控件的值改变事件
		DATE_VALUE_CHANGE: "/mui/form/datetime/change",
		
		ekpUserid : null,
		
		docId : null,
	
		methodStatus : null,
		
		doInit : function(){
			this.getUrlInfo();
			this._export(this);
			var status = this.methodStatus;
			switch(status){
				case "add":
				case "edit":
					this.initInEdit(status);
					break;
				case "view":
					this._initInView(this);
					break;
			}
		},
		
		updatePageStyle:function(){
			//时长展示居右
			var fd_item_duration = $("[name*='fd_item_duration']");
			if(fd_item_duration && fd_item_duration.length>0){
				for (var i = 0; i < fd_item_duration.length; i++) {
					$(fd_item_duration[i]).css("text-align","right");
				}
			}
		},
		
		viewPageStyle:function(){
			//移动端查看页居右
			$('div.muiSelInput').css("text-align","right");
			$('div.muiInput_View').css("text-align","right");
			$('div.muiFormTextareaContent').css("text-align","right");
		},
		
		getUrlInfo : function(){
			var urlParm = window.location.search;
			if (urlParm.indexOf("?") != -1) {
				var strs = urlParm.substr(1).split("&");
				for(var i=0;i<strs.length;i++){
			        var kv = strs[i].split('=');
			        if(kv[0] == 'fdId'){
			        	this.docId = kv[1];
			        }
			        if(kv[0] == 'method'){
			        	this.methodStatus = kv[1];
			        }
				}
			}
		},
		
		_export:function(v){
			this.initPageData(false);
			window.initpage = function(){
				v.initPageData(false);
				v.updatePageStyle();
			}
		},
		
		initInEdit : function(status){		
			//注册事件
			this.initEditEvent(status);
			
			validatorUtil.addBatchCompareTimeValidation('compareTime',"请假开始时间不能大于请假结束时间");
			validatorUtil.addBatchDurationValidation('validateDuration',"请假时长超过了假期余额，请重新选择");
			validatorUtil.addBatchSunDurationValidation('validateSunDuration',"请假时长超过了假期余额，请重新选择");
			validatorUtil.addBatchRepeatTimeValidation('repeatTime',"请假时间重复，请重新选择");
			validatorUtil.addBatchIsLeaveValidation('validateIsLeave',"存在已有请假记录或者其它异常");
		},	
		
		initEditEvent :function(status){			
			_this = this;		
						
			//监听【请假类型】的值变更事件
			this.listenLeaveCodeChange();
			// 【开始时间】和【结束时间】的值改变事件监听
			this.listenFromToTimeChange();			
			 
			$(document).on('table-delete','table[id="TABLE_DL_fd_batch_leave_table"]',function(e,argus){
				_this.refreshSumDuration();
			});
			$(document).on('table-add-new','table[id="TABLE_DL_fd_batch_leave_table"]',function(e,argus){
				_this.initPageData(false);
			});
		},
		
		ekpUserChange:function(obj){
			if(obj.value && obj.value != this.ekpUserid){
				//1、重新赋值给全局变量ekpUserid
				this.ekpUserid = obj.value;
				//2、明细表所有请假类型重新初始化
				this.initPageData(true);
				//3、清空每行内已选择数据
				this.clearEveryTrData();	
			}
		},
		
		clearEveryTrData:function(){
			var trs = $("#TABLE_DL_fd_batch_leave_table").find(".muiDetailTable");
			if(trs.length>0){
				for (var i = 0; i < trs.length; i++) {
					var fd_leave_type = $(trs[i]).find("[name*='fd_leave_type']");
					this.ClearDateValue(fd_leave_type);
					$(fd_leave_type).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("input[name*='fd_item_duration']").val("系统自动计算");
					$(fd_leave_type).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("input[name*='fd_type_code']").val("");
					$(fd_leave_type).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("input[name*='fd_type_name']").val("");
					$(fd_leave_type).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("input[name*='fd_type_extend_value']").val("");
					$(fd_leave_type).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("input[name*='fd_item_unit']").val("");
				}
			}
			$("[name='extendDataFormInfo.value(fd_sum_duration)']").val("0");
		},
		
		//初始化控件校验--obj为当前行的请假类型
		initObjValidate:function(obj,optunit){
			var startTimeObjName =$(obj).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("[name*='fd_leave_start_time']")[0].name;
			var endTimeObjName =$(obj).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("[name*='fd_leave_end_time']")[0].name;
			var itemDurationObjName =$(obj).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("[name*='fd_item_duration']")[0].name;
			var startTimeObj = $form(startTimeObjName);
			var endTimeObj = $form(endTimeObjName);
			var itemDurationObj = $form(itemDurationObjName);
			
			var fromTimeWidget = commonUtil.getWidgetFrom$Form(startTimeObj);
			var toTimeWidget = commonUtil.getWidgetFrom$Form(endTimeObj);
			var itemDurationWidget = commonUtil.getWidgetFrom$Form(itemDurationObj);
			
			fromTimeWidget._set("validate", "required compareTime repeatTime validateDuration validateSunDuration validateIsLeave");
			toTimeWidget._set("validate", "required compareTime repeatTime validateDuration validateSunDuration validateIsLeave");
			fromTimeWidget._set("_validate", "required compareTime repeatTime validateDuration validateSunDuration validateIsLeave");
			toTimeWidget._set("_validate", "required compareTime repeatTime validateDuration validateSunDuration validateIsLeave");
			
			itemDurationWidget._set("validate", "required  validateDuration validateSunDuration validateIsLeave ");		
			itemDurationWidget._set("_validate", "required validateDuration validateSunDuration validateIsLeave");		
			
			//根据单位将时间控件置为date或datetime类型--新增行时，单位为空
			if(optunit){
				switch(optunit){
				case "day":
				case "halfDay":
					this.changeDateWidget(fromTimeWidget,toTimeWidget,"date");
					break;
				case "hour":
					this.changeDateWidget(fromTimeWidget,toTimeWidget,"dateTime");
					break;
				default:
					console.log("can't match unit("+ optunit +")!");
				break;
				}
			}
		},
		
		// 监听【请假类型】的值变更事件
		listenLeaveCodeChange : function(){
			var _self = this;
			topic.subscribe(this.ADDRESS_CALLBACK, function(triggerWidget,obj){
				if(null !=triggerWidget && "extendDataFormInfo.value(fd_leave_user.id)"==triggerWidget.idField){
					_self.ekpUserChange(obj);
				}
			});
			topic.subscribe(this.SELECT_CALLBACK, function(triggerWidget,obj){
				var _name = triggerWidget.name;
				var _value = triggerWidget.value;
				var _text_value = triggerWidget.text;
				if(null != triggerWidget && triggerWidget.name.indexOf("fd_leave_type")>0){
					_self.LeaveTypeChange(_value,_name,_text_value);			
				}
				
				if(triggerWidget != null && triggerWidget.name.indexOf("fd_start_time_one")>0 || triggerWidget.name.indexOf("fd_end_time_one")>0){
					_self.dateTimeOneChange(_value,_name);
					var obj = $("[name='"+_name+"']").parents(".muiDetailTable");
					_self.timeObjFocus(obj);
				}
			});
		},
		
		timeObjFocus:function(trObj){
			var starttime = $(trObj).find("[name*='fd_leave_start_time']");
			var endtime = $(trObj).find("[name*='fd_leave_end_time']");
			if(starttime.val()){
				$(starttime).focus();
			}
			if(endtime.val()){
				$(endtime).focus();
			}
		},
		
		// 监听【开始时间】和【结束时间】的值改变事件
		listenFromToTimeChange : function(){
			var _self = this;
			topic.subscribe(this.DATE_VALUE_CHANGE, function(triggerWidget){
				if(null != triggerWidget){
					var _name = triggerWidget.valueField;
					var _value = triggerWidget.value;
					if(_name.indexOf("fd_leave_start_time")>0 || _name.indexOf("fd_leave_end_time")>0){
						_self.dateTimeChange(_value,_name);					
					}				
				}
			});
		},
		
		//开始时间和结束时间值变更事件
		dateTimeChange:function(optvalue,optname){
			 var obj = $("[name='"+optname+"']");
			 var trObj = $(obj).parents(".muiDetailTable");
			if(this.Ding_IsAllDateSet(trObj,obj)){
				this.requestCalcuateDuration(trObj);
			}
		},
		
		Ding_IsAllDateSet:function(obj,_this){
			var fd_leave_type = $(obj).find("[name*='fd_leave_type']").val();
			if(!fd_leave_type){
				$(obj).find("[name*='fd_leave_type']").focus();
				Tip.tip({text:"需先选择请假类型"});
				$(_this).val("");
				return;
			}
			var fd_item_unit = $(obj).find("[name*='fd_item_unit']").val();
			if(!fd_item_unit){
				Tip.tip({text:"未获取到假期单位，请重新选择请假类型"});
				return;
			}	
			var fd_leave_start_time = $(obj).find("[name*='fd_leave_start_time']");
			var fd_leave_end_time = $(obj).find("[name*='fd_leave_end_time']");
			var fd_start_time_one = $(obj).find("[name*='fd_start_time_one']").val();
			var fd_end_time_one = $(obj).find("[name*='fd_end_time_one']").val();
			// 开始时间和结束事件是否已经设置值
			if(fd_leave_start_time.val() && fd_leave_end_time.val()){
				// 【单位】是否设置为半天，是则校验
				if(fd_item_unit === "halfDay"){
					if(fd_start_time_one && fd_end_time_one){
						return true;
					}
				}else{
					return true;
				}
			}
			return false;
		},
		
		//开始时间段和结束时间段变更事件
		dateTimeOneChange:function(optvalue,optname){
			 var obj = $("[name='"+optname+"']");
			 var trObj = $(obj).parents(".muiDetailTable");
			if(this.Ding_IsAllDateSet(trObj,obj)){
				this.requestCalcuateDuration(trObj);
			}
		},
		
		//选择请假类型事件
		LeaveTypeChange:function (optvalue,optname,_text_value){
			var obj = $("[name='"+optname+"']");
			var data_list_type = $(obj).attr("data-list-type");
			var jsonList = JSON.parse(data_list_type);
			var optunit = "";
			var ye = 0.00;
			//1、取当前选择的请假类型单位和余额
			if(jsonList && jsonList.length>0){
				for (var w = 0; w < jsonList.length; w++) {
					if(jsonList[w]["value"] == optvalue){
						optunit = jsonList[w]["unit"];
						var dataValue = jsonList[w]["dataValue"];
						if("" != dataValue){
							ye = parseFloat(dataValue);
						}
					}
				}
			}
			
			if(optunit){
				//2、存每行被选中的单位、name、code、余额
				$(obj).parent(".oldMui").parent("xformflag").parent("td").find("[name*='fd_type_code']").val(optvalue);		
				$(obj).parent(".oldMui").parent("xformflag").parent("td").find("[name*='fd_type_name']").val(_text_value);		
				$(obj).parent(".oldMui").parent("xformflag").parent("td").find("[name*='type_unit']").val(optunit);		
				$(obj).parent(".oldMui").parent("xformflag").parent("td").find("[name*='type_ye']").val(ye);		
				$(obj).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("[name*='fd_item_unit']").val(optunit);		
				//3、清空日期选择的已选值
				this.ClearDateValue(obj);		
				
				//4、选择请假类型后初始化控件校验
				this.initObjValidate(obj,optunit);
				
				//5、根据单位显示上下午字段
				this.Ding_HalfDay_Display(obj,optunit);
				
				/*// 变更当前行的开始时间和结束时间的onclick事件
				Ding_ChangeDateWidgetEvent(obj,optunit);*/
			}else{
				Tip.tip({text:"请选择请假类型。"});
				console.log("please select option info("+ optunit +")!");
			}
		},
		
		//根据单位显示上下午字段
		Ding_HalfDay_Display : function(obj,optunit){
			var startTimeObjName =$(obj).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("[name*='fd_start_time_one']")[0].name;
			var endTimeObjName =$(obj).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("[name*='fd_end_time_one']")[0].name;
			
			var startTimeObj = $form(startTimeObjName);
			var endTimeObj = $form(endTimeObjName);

			var fromTimeWidget = commonUtil.getWidgetFrom$Form(startTimeObj);
			var toTimeWidget = commonUtil.getWidgetFrom$Form(endTimeObj);
			// 半天的必填 #162387 --在这里设必填为false,是半天的在判断中加必填
			$form(fromTimeWidget.name).required(false);
			$form(toTimeWidget.name).required(false);
			// #162387 开始和结束分别默认上午和下午--取消赋值
			$form(fromTimeWidget.name).val('');
			$form(toTimeWidget.name).val('');
			
			// 根据不同的单位，时间选择不同的选择
			switch(optunit){
				case "halfDay":
					// 显示上下午
					startTimeObj.display(true);
					endTimeObj.display(true);
					
					// 半天的有上下午的必填 #162387
					$form(fromTimeWidget.name).required(true);
					$form(toTimeWidget.name).required(true);
					// 开始和结束分别默认上午和下午 #162387
					$form(fromTimeWidget.name).val('AM');
					$form(toTimeWidget.name).val('PM');
					break;
				case "day":
				case "hour":
					// 隐藏上下午
					startTimeObj.display(false);
					endTimeObj.display(false);
					break;
				default:
					console.log("can't match unit("+ optunit +")!");
					break;
			}
		},
		
		// 根据【单位】把【开始时间】和【结束时间】的类型变更为dateTime或者date
		changeDateWidget : function(fromTimeWidget,toTimeWidget,type){
			// 根据不同的单位，时间选择不同的选择
			this._changeDateBaseAttr(type, fromTimeWidget);
			this._changeDateBaseAttr(type, toTimeWidget);
		},
		
		// 变更日期控件的基本属性值
		_changeDateBaseAttr : function(type, dateWidget){
			var info = dateWidgetInfo[type];
			for(var attrKey in info){
				dateWidget[attrKey] = info[attrKey];
			}
		},
		
		//清空日期选择的已选项
		ClearDateValue:function (obj){
			var startTimeObjName =$(obj).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("[name*='fd_leave_start_time']")[0].name;
			var endTimeObjName =$(obj).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("[name*='fd_leave_end_time']")[0].name;
			
			var startTimeObj = $form(startTimeObjName);
			var endTimeObj = $form(endTimeObjName);
			
			var fromTimeWidget = commonUtil.getWidgetFrom$Form(startTimeObj);
			var toTimeWidget = commonUtil.getWidgetFrom$Form(endTimeObj);
			this._clearValue(startTimeObj,fromTimeWidget,false);
			this._clearValue(endTimeObj,toTimeWidget,false);
			var fd_leave_start_time = $(obj).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("[name*='fd_leave_start_time']");	
			var fd_leave_end_time = $(obj).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("[name*='fd_leave_end_time']");	
			var fd_start_time_one = $(obj).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("[name*='fd_start_time_one']");	
			var fd_end_time_one = $(obj).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("[name*='fd_end_time_one']");	
			
			fd_leave_start_time.val("");
			$(fd_leave_start_time).parent(".oldMui").find(".muiSelInput").text("");
			fd_leave_end_time.val("");
			$(fd_leave_end_time).parent(".oldMui").find(".muiSelInput").text("");
		
			$(fd_start_time_one).val("");
			$(fd_start_time_one).prev(".muiSelInput").text("");
			$(fd_end_time_one).val("");
			$(fd_end_time_one).prev(".muiSelInput").text("");
			this._clearValue(startTimeObj,fromTimeWidget,true);
			this._clearValue(endTimeObj,toTimeWidget,true);
			
			$(obj).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parent("tbody").find("[name*='fd_item_duration']").removeClass("validateIsLeave");
		},
		
		// 清除值
		_clearValue : function(timeObj,timeWidget,flag){
			// 临时把"马上校验"移除，随后补回来
			if(!flag){
				timeWidget.validateImmediate = false;
			}else{
				timeObj.val("");
				timeWidget.validateImmediate = true;
				timeWidget.validation.hideWarnHint(timeWidget.domNode);			
			}
		},
		
		initPageData:function(falg){
			_this = this;
			var muiDetailTable =  $("#TABLE_DL_fd_batch_leave_table").find(".muiDetailTable");
			if(!muiDetailTable || muiDetailTable.length==0){
				setTimeout("initpage();","1000");
				return;
			}
			for (var a = 0; a < muiDetailTable.length; a++) {
				var fd_leave_types = $(muiDetailTable[a]).find("[name*='fd_leave_type']");
				if(fd_leave_types && fd_leave_types.length>0){
					if(!falg){
						this.ekpUserid = $("[name='extendDataFormInfo.value(fd_leave_user.id)']").val();
					}
					for (var i = 0; i < fd_leave_types.length; i++) {//这里永远只有一个
						var fd_leave_type = $(fd_leave_types[i]);
						if(!falg && $(fd_leave_type).hasClass("isInits")){//已初始化，则跳过
							continue;
						}
						//未初始化，则初始化
						this.initLeaveType(fd_leave_type);
						//初始化完成，添加必填校验
						var typeName =$(fd_leave_type)[0].name;
						var typeObj = $form(typeName);			
						var typeWidget = commonUtil.getWidgetFrom$Form(typeObj);				
						typeWidget._set("validate", "required");
						typeWidget._set("validate", "required");	
						$(fd_leave_type).parents(".muiDetailTable").find("[name*='fd_item_duration']").val("系统自动计算");
						$(fd_leave_types[i]).val("");
						$(fd_leave_types[i]).prev(".muiSelInput").text("");
					}
				}else{
					setTimeout("initpage();","1000");
					return;
				}
			}
		},
			
		// 请求计算时长
		requestCalcuateDuration : function(obj){
			var unit = $(obj).find("[name*='fd_item_unit']").val();
			var dw = "天";
			var $duration = $(obj).find("[name*='fd_item_duration']");
			var requestUrl = this.getDurationUrl(obj);
			requestUrl = util.formatUrl(requestUrl);
			_this = this;
			$.ajax({
				url : requestUrl,
				dataType : "json",
				type : "GET",
	        	jsonp:"jsonpcallback",
	        	success: function(data){
	        		if(data.errcode === 0){
	        			var duration = 0;
	        			var record = data.result.form_data_list[0]["extend_value"];
	        			$(obj).find("[name*='fd_type_extend_value']").val(record);
	        			record = eval("("+record+")");
	        			try{
	        				if(unit === "hour"){
	        					duration = parseFloat(record["durationInHour"]);
	        				}else{
	        					duration = parseFloat(record["durationInDay"]);
	        				}
	        			}catch(e){
	        				duration = record["durationInHour"];
	        				console.log("can't parse " + record["durationInDay"] +" to float!");
	        			}        			
	        			if("hour"==unit){
	        				dw = "小时";
	        			}
	        			$duration.val(duration+" "+dw);//将计算的当前行时长赋值在当前行的请假时长
	        			$duration.data("durationInfo", record);
//	        			$(obj).find("[name*='fd_item_duration']").parents(".xform_inputText").css("display","inline-block");//放开时长
						if(duration == "0"){
							Tip.tip({text:"时长不能为0"});
						}
//						$(obj).find(".fd_item_duration_span").css("display","none");//隐藏系统自动计算字体
						$($duration).focus();
//						//当前行时长赋值完成，更新总时长
						_this.refreshSumDuration();
//						//判断是否能请假
						_this.isLeave(obj);
	        		}else{
	        			console.log(data.errmsg);
	        		}
	        	}
			});
		},
		
		isLeave:function(obj){//obj为行对象
			var leave_code = $(obj).find("[name*='fd_type_code']").val();
			var durationInfo = $(obj).find("[name*='fd_type_extend_value']").val();
			var unit = $(obj).find("[name*='type_unit']").val();
			if(!leave_code || !durationInfo || !unit){
				return;
			}
			var docSubject = $("input[name='docSubject']").val();//审批高级版
			var reason = $("[name='extendDataFormInfo.value(fd_leave_remark)']").val();		
			
			var url = Com_Parameter.ContextPath+'third/ding/thirdDingAttendance.do?method=canLeave&leaveType=batch&ekpUserid='+this.ekpUserid;
			var requestUrl = util.urlResolver(url, params);
			var params = {};
			var status = this.methodStatus;
			var fdId = this.docId;
		
			params["startTime"] = this.Ding_GetStartTime(obj);
			params["finishTime"] = this.Ding_GetEndTime(obj);
			params["leaveCode"] = leave_code;
			params["leaveTimeInfo"] = durationInfo;
			params["methodCode"] = status;
			params["docSubject"] = docSubject;
			params["unit"] = unit;
			params["reason"] = reason;
			params["fdId"] = fdId;
			var flag = true;
			var msg = "";
			$.ajax({
				url : requestUrl,
				data:params,
				async:false,
				dataType : "json",
				type : "POST",
	        	success: function(data){
	        		if(data && data.success){
	        			//success为true放行
	        			if(data.result && data.result.form_data_list){
	        				var record = data.result.form_data_list[0]["extend_value"];
	        				if(record){
	        					record = eval("("+record+")");
	        					var canLeave = record["canLeave"];
	        					if(!canLeave){
	        						var errmsg ="假期余额已不足,请刷新页面后重新申请";
	        						console.log("是否可请假接口校验：【余额不足】，请刷新页面后重新申请");
	        						//dialog.alert(errmsg);
	        						Tip.tip({text:"余额不足"});
	        						flag = false;
	        						$(obj).find("[name*='fd_leave_start_time']").focus();
	        						$(obj).find("[name*='fd_leave_end_time']").focus();
	        					}        					
	        				}
	        			}
	        			
	        		}else{
	        			flag = false;
	        			var errcode = data.errcode;
	        			if(831000==errcode){
	        				msg = "不存在的请假类型";
	        			}else if(831001==errcode){
	        				msg = "请假开始/结束时间非法";
	        			}else if(831002==errcode){
	        				msg = "请假余额不足";
	        			}else if(831003==errcode){
	        				msg = "已有请假记录";
	        			}else if(831004==errcode){
	        				msg = "（考勤）时间校验不通过";
	        			}else{
	        				msg = data.errmsg
	        				if(!msg){
	        					msg = "网络异常，请稍后再试。";
	        				}
	        			}
	        			Tip.tip({text:msg});
	        		}
	        	}
			});
			if(!flag){
				$(obj).find("input[name*='fd_item_duration']").addClass("validateIsLeave");
				$(obj).find("input[name*='fd_item_duration']").focus();
			}else{
				$(obj).find("input[name*='fd_item_duration']").removeClass("validateIsLeave");
			}
			return flag;
		},
		
		//更新总时长
		refreshSumDuration:function(){			
			var fd_item_durations = $("#TABLE_DL_fd_batch_leave_table").find("[name*='fd_item_duration']");
			if(fd_item_durations.length==0){
				$("[name*='extendDataFormInfo.value(fd_sum_duration)']").val("0");
				return;
			}
			var sumDurations="";
			var days = 0.00;
			var hours = 0.00;
			for (var i = 0; i < fd_item_durations.length; i++) {
				var durations = $(fd_item_durations[i]).val();
				var fd_item_unit = $(fd_item_durations[i]).parents(".muiDetailTable").find("[name*='fd_item_unit']").val();
				if(""==durations || "系统自动计算" == durations){
					durations = 0+" 天";
				}
				durations = parseFloat(durations.split(" ")[0]);
				if("hour"==fd_item_unit){
					hours+=durations;
				}else{
					days+=durations;
				}
			}
			if(days>0){
				sumDurations += days+"天  ";
			}
			if(hours>0){
				sumDurations += hours+"小时  ";
			}
			if(""==sumDurations){
				sumDurations="0";
			}
			$("[name*='extendDataFormInfo.value(fd_sum_duration)']").val(sumDurations);
		},
	
		//获取请求时长的url
		getDurationUrl : function(obj){
			var requestUrl = "/third/ding/thirdDingAttendance.do?method=preCalculate" +
			"&startTime=!{startTime}&finishTime=!{finishTime}&leaveCode=!{leaveCode}&methodCode=!{methodCode}&fdId=!{fdId}&leaveType=batch&ekpUserid=!{ekpUserid}";
			// 假期类型详情
			var leave_code = $(obj).find("[name*='fd_leave_type']").val();
			if(!leave_code){
				Tip.tip({text:"请先选择请假类型"});
				return false;
			}
			var methodCode = this.methodStatus;
			var fdId = this.docId;
		
			var params = {};
			params["startTime"] = this.Ding_GetStartTime(obj);
			params["finishTime"] = this.Ding_GetEndTime(obj);
			params["leaveCode"] = leave_code;
			params["methodCode"] = methodCode;
			params["fdId"] = fdId;
			params["ekpUserid"] = this.ekpUserid;
			return util.urlResolver(requestUrl, params);
		},
		
		// 判断当前行-是否所有的日期控件都已经设置值
		Ding_GetStartTime:function (obj){	
			var rs = "";
			var fd_leave_start_time = $(obj).find("[name*='fd_leave_start_time']");
			var fd_item_unit = $(obj).find("[name*='fd_item_unit']").val();
			rs = fd_leave_start_time.val();
			if(fd_item_unit === "halfDay"){
				var fd_start_time_one = $(obj).find("[name*='fd_start_time_one']").val();
				rs += " " + fd_start_time_one;
			}
			return rs;
		},

		Ding_GetEndTime:function (obj){
			var rs = "";
			var fd_leave_end_time = $(obj).find("[name*='fd_leave_end_time']");
			var fd_item_unit = $(obj).find("[name*='fd_item_unit']").val();
			rs = fd_leave_end_time.val();
			if(fd_item_unit === "halfDay"){
				var fd_end_time_one = $(obj).find("[name*='fd_end_time_one']").val();
				rs += " " + fd_end_time_one;
			}
			return rs; 
		},
		
		//初始化请假类型下拉框
		initLeaveType : function(typeObj){
			 var fd_leave_type = typeObj;
			var fd_leave_type_default = typeObj[0].defaultValue;
			 if(!fd_leave_type){
				 return;//不初始化
			 }
			 var status = this.methodStatus;
			 if("view" == status){
				return;
			 }
			 _this = this;
			 var levelForm = $(fd_leave_type).parent('.oldMui');
			 var wgt = registry.byNode(levelForm[0]);
			
			 var url = '/third/ding/thirdDingAttendance.do?method=getBizsuiteTypes&findRest=true&leaveType=batch';			
			var fdId = this.docId;
			var pram = {};
			var jsonData = [];
			url +="&methodCode="+status+"&fdId="+fdId+"&ekpUserid="+this.ekpUserid;
			url = util.formatUrl(url);
			
			var dataLevelForm = new Array();
			var defultOpt={"text":"===请选择===","value":"","selected":"true"};
			dataLevelForm[0] = defultOpt;
			$.ajax({
				url : url,
				dataType : "json",
				type : "GET",
		    	jsonp:"jsonpcallback",
		    	success: function(data){
		    		if(data && data.success && null != data.result){		    			
						var typeValues= "";
						var typeNames= "";
						var unit= "";
						var dataValue= "";
						var dataText= "";
						for(var i = 0;i < data.result.form_data_list.length;i++){
							var prams1={};
							var record = data.result.form_data_list[i]["extend_value"];
							var recordJson = eval("("+record+")");							
							prams1["selected"] = false;
							prams1["text"] = _this.getOptionText(record);
							prams1["value"] = _this.getOptionValue(record);
							var par_value=_this.getOptionValue(record);
							if(fd_leave_type_default==par_value){
								prams1["selected"] = true;
								dataText = _this.getOptionText(record);
								dataValue=fd_leave_type_default;
							}else{
								prams1["selected"] = false;
							}
							prams1["unit"] = _this.getOptionUnit(record);
							prams1["dataValue"] = _this.getOptionDataValue(record);
							
							dataLevelForm[i+1]=prams1;
							jsonData[i]=prams1;
						}	
						wgt.store.data=dataLevelForm;
						wgt.values=dataLevelForm;
						//当前控件置为已初始化
						$(typeObj).addClass("isInits");
						$(typeObj).attr("data-list-type",JSON.stringify(jsonData));
						$(typeObj).parent(".oldMui").find(".muiSelInput").text(dataText);
						$(typeObj).parent(".oldMui").find("input").val(dataValue);
		    		}
		    	},error : function(data) {
		    		Tip.tip({text:"请假类型初始化异常"});
		       	  	console.log("请假类型初始化异常。url:"+url);
		         }
			});
		},
		
		//取请假类型-展示
		getOptionText:function(record){
			record = eval("("+record+")");
			
			var name = record["leaveName"];
			var unit = record["leaveViewUnit"];
			var unitTxt = (unit === "day" || unit === "halfDay") ? "天" : "小时";
			
			// record["leave_rest"]为null则不需要显示剩余额度
			if(record.hasOwnProperty("leaveBalanceQuotaVo")){
			     if(unit === "day" || unit === "halfDay"){
			       var span = "<span class='unitClass'> "+unit+"</span><span class='yeClass'> "+record["leaveBalanceQuotaVo"]["quotaNumPerDay"]+"</span>";
			       name += "（剩余" + record["leaveBalanceQuotaVo"]["quotaNumPerDay"] + unitTxt + ")";
			     }else{
			       var span = "<span class='unitClass'> "+unit+"</span><span class='yeClass'> "+record["leaveBalanceQuotaVo"]["quotaNumPerDay"]+"</span>";
			       name += ("（剩余" + record["leaveBalanceQuotaVo"]["quotaNumPerHour"] + unitTxt + ")");
			     }
			}
			return name;
		},
		//取请假类型leaveCode
		getOptionValue:function(record){
			record = eval("("+record+")");
			var value = record["leaveCode"];
			return value;
		},
		//取请假类型对应的余额
		getOptionDataValue:function (record){
			record = eval("("+record+")");
			var unit = record["leaveViewUnit"];
			var dataValue ="";
			// record["leave_rest"]为null则不需要显示剩余额度
			if(record.hasOwnProperty("leaveBalanceQuotaVo")){
			     if(unit === "day" || unit === "halfDay"){
			       dataValue = record["leaveBalanceQuotaVo"]["quotaNumPerDay"];
			     }else{
			       dataValue = record["leaveBalanceQuotaVo"]["quotaNumPerHour"];
			     }
			}
			return dataValue;
		},
		//取请假类型对应的单位
		getOptionUnit:function(record){
			record = eval("("+record+")");
			var unit = record["leaveViewUnit"];
			return unit;
		},

		_initInView:function(v){
			this.initInView();
			window.initview = function(){
				v.initInView();
			}
		},
		
		initInView : function(){
			//1、查看页根据每行的单位，控制展示的时间格式
			var units = $("input[name*='type_unit']");
			if(units.length==0){
				setTimeout("initview();","500");
			}
			if(units.length>0){
				for (var i = 0; i < units.length; i++) {
					console.log("units:"+$(units[i]).val());
					var unit = $(units[i]).val();
					if(!unit){
						continue;
					}
					if('day'==unit || 'halfDay' == unit){
						var fd_leave_start_time_tr = $(units[i]).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parents(".muiDetailTable").find("xformflag[id*='fd_leave_start_time']");
						var fd_leave_end_time_tr = $(units[i]).parent(".oldMui").parent("xformflag").parent("td").parent("tr").parents(".muiDetailTable").find("xformflag[id*='fd_leave_end_time']");
						var start_time_div = $(fd_leave_start_time_tr).find(".oldMui ").find(".muiSelInput");
						var end_time_div = $(fd_leave_end_time_tr).find(".oldMui ").find(".muiSelInput");
						if($(start_time_div).text()){
							var startTimeView = $(start_time_div).text().substring(0,10);
							$(start_time_div).text(startTimeView);
						}
						if($(end_time_div).text()){
							var endTimeView = $(end_time_div).text().substring(0,10);
							$(end_time_div).text(endTimeView);
						}
					}
				}
			}
			//2、查看页展示明细表每行的请假类型
			//$("[name*='fd_leave_type']").parents('.xform_Select').text("890")
			var fd_type_names = $("input[name*='fd_type_name']");
			if(fd_type_names.length>0){
				for (var t = 0; t < fd_type_names.length; t++) {
					console.log("fd_type_names:"+fd_type_names[t]);
					var fd_type_name = $(fd_type_names[t]).val();
					if(!fd_type_name){
						continue;
					}
					 $(fd_type_names[t]).parent(".oldMui").parent("xformflag").parent("td").append("<span style='float: right;' class='view_ding_batch_leave'>"+fd_type_name+"</span>");
				}
			}
			//移动端查看页靠右展示
			this.viewPageStyle();
		},

	});
	var dateWidgetInfo = {
			"dateTime" : {
				type: "datetime",
				
				tmplStr:
				      '<div data-dojo-type="mui/datetime/DatePicker" id="_date_picker" data-dojo-props="value:\'!{dateValue}\'"></div><div data-dojo-type="mui/datetime/TimePicker" id="_time_picker" data-dojo-props="value:\'!{timeValue}\', minuteStep:\'!{minuteStep}\'"></div>',
				      
				title: msg["mui.datetime.select"],

				_contentExtendClass: "muiDateTimeDialogDisContent",
				
				mixinIds : null
			},
			"date" : {
				type: "date",
				
			    tmplStr:
			        '<div data-dojo-type="mui/datetime/DatePicker" id="_date_picker" data-dojo-props="value:\'!{dateValue}\'"></div>',

				title: msg["mui.date.select"],
				
				_contentExtendClass: "muiDateDialogDisContent",
							      
				mixinIds : null
			}
		};
	return Service;
})