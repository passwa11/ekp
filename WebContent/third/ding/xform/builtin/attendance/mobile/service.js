define(["dojo/_base/declare",
		"dojo/query",
		"sys/xform/mobile/controls/xformUtil",
		"third/ding/xform/resource/js/mobile/common",
		"dojo/dom-style",
		"third/ding/xform/resource/js/mobile/validatorUtil",
		"mui/i18n/i18n!sys-mobile:mui",
		"dojo/topic",
		"mui/util",
		"dojo/request",
		"dojo/_base/array",
		"dojo/dom-construct",
		"third/ding/xform/resource/js/ding_right",
		"mui/dialog/Tip"
],function(declare, query, xformUtil, commonUtil, domStyle, validatorUtil, msg, topic, util, request, array, domConstruct,right, Tip){
	
	// 业务逻辑
	var Service = declare("third.ding.xform.builtin.attendance.mobile.service", null , {
		// 下拉框的窗口关闭事件
		SELECT_CALLBACK : 'mui/form/select/callback',
		
		// 日期控件的值改变事件
		DATE_VALUE_CHANGE: "/mui/form/datetime/change",
		
		// 由于【单位】使用频率很高，直接设置为变量，方便使用
		leaveUnit : "day",
		
		// 【开始时间】组件（$form）
		fromTimeWidget$form : null,
		
		// 【开始时间】组件
		fromTimeWidget : null,
		
		// 【结束时间】组件
		toTimeWidget : null,
		
		// 【结束时间】组件（$form）
		toTimeWidget$form : null,
		
		// 【开始时间】上下午($form)
		fromHalfDay$form : null,
		
		// 【开始时间】上下午
		fromHalfDay : null,
		
		// 【结束时间】上下午($form)
		toHalfDay$form : null,
		
		// 【结束时间】上下午
		toHalfDay : null,
		
		// 【请假类型】组件
		leaveTypeWidget : null,
		
		// 【时长】组件
		durationWidget : null,
		
		// 时长计算结果详情，请求计算时长之后缓存的变量
		durationInfo : null,
		
		doInit : function(){
			// 保存常用变量
			this.leaveUnit = query("input[name*='extendDataFormInfo.value(unit)']").val() || "day";
			this.fromTimeWidget$form = $form("from_time");
			this.toTimeWidget$form = $form("to_time");
			this.fromTimeWidget = commonUtil.getWidgetFrom$Form(this.fromTimeWidget$form);
			this.toTimeWidget = commonUtil.getWidgetFrom$Form(this.toTimeWidget$form);
			this.fromHalfDay$form = $form("from_half_day");
			this.toHalfDay$form = $form("to_half_day");
			this.fromHalfDay = commonUtil.getWidgetFrom$Form(this.fromHalfDay$form);
			this.toHalfDay = commonUtil.getWidgetFrom$Form(this.toHalfDay$form);
			this.leaveTypeWidget = commonUtil.getWidgetFrom$Form($form("leave_code"));
			this.durationWidget = commonUtil.getWidgetFrom$Form($form("duration"));
			
			// 查找所有class为【ding_mobile_divide_flag】标识的div，在它所在行的上方添加一个分隔行并删除该标识
			commonUtil.addDivdieTr();
			commonUtil.setRightStyle();
			
			var status = commonUtil.getStatus();
			switch(status){
				case "add":
				case "edit":
					this.initInEdit(status);
					break;
				case "view":
					this.initInView();
					break;
			}
			this._export(this);
		},
		
		_export:function(v){
			window.isLeave = function (){
				var leave_code = $("[name='extendDataFormInfo.value(leave_code)']").val();
				var tips = "";
				if(!leave_code){
					tips="请先选择请假类型";
					Tip.tip({text:tips});
					return false;//选择请假类型
				}
				var durationInfo = $("[name='extendDataFormInfo.value(extend_value)']").val();
				if(!durationInfo){
					tips="请先选择时间";
					Tip.tip({text:tips});
					return false;//未请求时长接口
				}
				var methodCode = commonUtil.getStatus();
				var fdId = "";
				if("edit" == methodCode){
					var urlParm = window.location.search;
					if (urlParm.indexOf("?") != -1) {
						var strs = urlParm.substr(1).split("&");
						for(var i=0;i<strs.length;i++){
					        var kv = strs[i].split('=');
					        if(kv[0] == 'fdId'){
					        	fdId = kv[1];
					        	break;
					        }
						}
					}
				}
				var docSubject = $("input[name='docSubject']").val();
				var reason = $("[name='extendDataFormInfo.value(reason)']").val();
				var requestUrl = util.urlResolver(Com_Parameter.ContextPath+'third/ding/thirdDingAttendance.do?method=canLeave', params);
				var params = {};
				params["leaveCode"] = leave_code;
				params["leaveTimeInfo"] = durationInfo;
				params["startTime"] = v.getStartTime();
				params["methodCode"] = methodCode;
				params["fdId"] = fdId;
				params["docSubject"] = docSubject;
				params["reason"] = reason;
				params["unit"] = v.leaveUnit;				
				params["finishTime"] = v.getEndTime();
				console.log(params);
				var flag = false;
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
		        						var errmsg ="余额不足";
		        						dialog.alert(errmsg);
		        						flag = false;
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
				return flag;
			}
		},
		
		initInEdit : function(status){
			this.initLayoutInEdit();
			this.initEventInEdit();
		},
		
		initLayoutInEdit : function(){
			// 1.根据【单位】设置上下午的显示和隐藏
			this.halfDayDisplay();
			// 2.根据【开始类型】或清【结束时间】设置遮罩
			if(!this.fromTimeWidget$form.val() && !this.toTimeWidget$form.val()){
				//未选请假类型时禁用
				validatorUtil.setDisabled(query(this.fromTimeWidget.domNode).closest("tr")[0]);				
				validatorUtil.setDisabled(query(this.toTimeWidget.domNode).closest("tr")[0]);
				validatorUtil.setDisabled(query(this.durationWidget.domNode).closest("tr")[0]);
				// validatorUtil.setDisabled(this.durationWidget.detailTrDom);
			}else{
				// 3.设置【开始时间】和【结束时间】的值格式化
				switch(this.leaveUnit){
					case "halfDay":
					case "day":
						this.fromTimeWidget$form.val(this.formatDateStr(this.fromTimeWidget$form.val().trim(),null, "yyyy-MM-dd"));
						this.toTimeWidget$form.val(this.formatDateStr(this.toTimeWidget$form.val().trim(),null, "yyyy-MM-dd"));
						break;
					case "hour":
						break;
					default:
						console.log("can't match unit("+ leaveUnit +")!");
						break;
				}
			}
			// 4.隐藏所有的必填星号
			var requireDomArr = query(".muiFormRequiredShow");
			array.forEach(requireDomArr, function(item){
				domStyle.set(item, "display", "none");
			});
			
		},
		
		formatDateStr : function(dateStr, defaultFormat, targetFormat){
			defaultFormat = defaultFormat || "yyyy-MM-dd HH:mm";
			var date = util.parseDate(dateStr, defaultFormat);
			return util.formatDate(date, targetFormat);
		},
		
		//根据单位显示上下午字段
		halfDayDisplay : function(){
			// 根据不同的单位，时间选择不同的选择
			switch(this.leaveUnit){
				case "halfDay":
					// 显示上下午
					this.fromHalfDay$form.display(true);
					this.toHalfDay$form.display(true);
					break;
				case "day":
				case "hour":
					// 隐藏上下午
					this.fromHalfDay$form.display(false);
					this.toHalfDay$form.display(false);
					break;
				default:
					console.log("can't match unit("+ leaveUnit +")!");
					break;
			}
		},
		
		initEventInEdit : function(){
			// 根据【单位】把【开始时间】和【结束时间】的类型变更为dateTime或者date
			this.changeDateWidget();

			// 校验器初始化
			validatorUtil.addCompareTimeValidation('compareTime', this.fromTimeWidget$form, this.toTimeWidget$form, "开始时间不能大于结束时间");
			//validatorUtil.addDateLengthValidation('dateMaxLength(length)', this.fromTimeWidget$form, this.toTimeWidget$form, "请假时长不得超过{maxLength}天");
			validatorUtil.addDateLengthValidation('dateLimit(length)', this.fromTimeWidget$form, new Date().format("yyyy-MM-dd HH:mm"), "已超过审批截止时间");
			validatorUtil.getValidation().addValidator("greaterZero", "不能等于0", function (v, e, o) {
				if(v && v == "0"){
					return false;
				}
				return true;
			});
			
			// 添加校验
			this.fromTimeWidget._set("validate", "required dateLimit(31)");
			this.toTimeWidget._set("validate", "required compareTime");
			this.durationWidget._set("validate", "required greaterZero");

			// 请假类型逻辑
			this.listenLeaveCodeChange();
			
			// 【开始时间】和【结束时间】的值改变事件监听
			this.listenFromToTimeChange();

			// 【上下午】监听
			this.listenHalfDayChange();
			
			// 更新【时长】左侧单元格的显示文字
			this.updateUnitTxt();

		},

		// 根据【单位】把【开始时间】和【结束时间】的类型变更为dateTime或者date
		// 【注意】能切换日期和日期时间的基本原理是因为日期组件在关闭弹窗时会整个窗口销毁掉，如果后续日期组件在这方面做了变更，此处得重新设计
		changeDateWidget : function(){
			// 根据不同的单位，时间选择不同的选择
			switch(this.leaveUnit){
				case "day":
				case "halfDay":
					this._changeDateBaseAttr("date", this.fromTimeWidget);
					this._changeDateBaseAttr("date", this.toTimeWidget);
					break;
				case "hour":
					this._changeDateBaseAttr("dateTime", this.fromTimeWidget);
					this._changeDateBaseAttr("dateTime", this.toTimeWidget);
					break;
				default:
					break;
			}
		},
		
		// 变更日期控件的基本属性值
		_changeDateBaseAttr : function(type, dateWidget){
			var info = dateWidgetInfo[type];
			for(var attrKey in info){
				dateWidget[attrKey] = info[attrKey];
			}
		},
		
		// 监听【请假类型】的值变更事件
		listenLeaveCodeChange : function(){
			var _self = this;
			topic.subscribe(this.SELECT_CALLBACK, function(triggerWidget){
				if(triggerWidget.name === "extendDataFormInfo.value(leave_code)"){
					_self.leaveCodeChange();					
				}
			});
		},
		
		// 【请假类型】的值变更事件
		leaveCodeChange : function(){
			var curOptionInfo = this.leaveTypeWidget.findOptionByKey(this.leaveTypeWidget.value);
			if(curOptionInfo){
				this.leaveUnit = curOptionInfo["leaveViewUnit"];
				// 设置值到隐藏域
				$form("unit").val(curOptionInfo["leaveViewUnit"]);
				var calculationMode = curOptionInfo["naturalDayLeave"] ? 0 : 1;
				$form("calculate_model").val(calculationMode);
				$form("leave_txt").val(curOptionInfo["leaveName"]);
				
				// 移除遮罩
				validatorUtil.removeAllDisabled();
				// 清空日期选择的已选项
				this._clearValue();
				// 变更【开始时间】和【结束时间】的类型
				this.changeDateWidget();
				// 【上下午】显示和隐藏
				this.halfDayDisplay();
				// 更新【时长】左侧单元格的显示文字
				this.updateUnitTxt();
			}
		},
		
		// 清除值
		_clearValue : function(){
			// 临时把"马上校验"移除，随后补回来
			this.fromTimeWidget.validateImmediate = false;
			this.fromTimeWidget$form.val("");
			this.fromTimeWidget.validateImmediate = true;
			this.fromTimeWidget.validation.hideWarnHint(this.fromTimeWidget.domNode);			
			
			this.toTimeWidget.validateImmediate = false;
			this.toTimeWidget$form.val("");
			this.toTimeWidget.validateImmediate = true;
			this.toTimeWidget.validation.hideWarnHint(this.toTimeWidget.domNode);
			
			this.durationWidget.validateImmediate = false;
			this.durationWidget.set("value", "");
			this.durationWidget.validateImmediate = true;
			this.durationInfo = null;
		},
		
		// 监听【开始时间】和【结束时间】的值改变事件
		listenFromToTimeChange : function(){
			var _self = this;
			topic.subscribe(this.DATE_VALUE_CHANGE, function(triggerWidget){
				if(triggerWidget.valueField === "extendDataFormInfo.value(from_time)" || 
						triggerWidget.valueField === "extendDataFormInfo.value(to_time)"){
					_self.calcuateDuration();					
				}
			});
		},
		
		// 监听【上下午】的值改变事件
		listenHalfDayChange : function(){
			var _self = this;
			topic.subscribe(this.SELECT_CALLBACK, function(triggerWidget){
				if(triggerWidget.name === "extendDataFormInfo.value(from_half_day)" || 
						triggerWidget.name === "extendDataFormInfo.value(to_half_day)"){
					_self.calcuateDuration();					
				}
			});
		},
		
		// 如果条件满足则计算时长
		calcuateDuration : function(){
			if(this.isAllDateSet()){
				this.requestCalcuateDuration();
			}
		},
		
		// 检查是否所有相关的日期控件都已经设值了
		isAllDateSet : function(){
			if(this.fromTimeWidget$form.val() && this.toTimeWidget$form.val()){
				if(this.leaveUnit === "halfDay"){
					if(this.fromHalfDay$form.val() && this.toHalfDay$form.val()){
						return true;
					}
				}else{
					return true;
				}
			}
			return false;
		},
		
		// 请求计算时长
		requestCalcuateDuration : function(){
			var _self = this;
			var requestUrl = this.getDurationUrl();
			requestUrl = util.formatUrl(requestUrl);
			request
				.post(requestUrl, {handleAs: "json"})
				.then(function(data){
					if(data.errcode === 0){
						var duration = 0;
						var record = data.result.form_data_list[0]["extend_value"];
	        			record = eval("("+record+")");
	        			try{
	        				if(record["unit"] === "HOUR"){
	        					duration = parseFloat(record["durationInHour"]);
	        				}else{
	        					duration = parseFloat(record["durationInDay"]);
	        				}
	        			}catch(e){
	        				duration = record["durationInHour"];
	        				console.log("can't parse " + record["durationInDay"] +" to float!");
	        			}
	        			_self.durationWidget.set("value", duration);
	        			_self.durationInfo = record;
	        			$form("extend_value").val(data.result.form_data_list[0]["extend_value"]);
	        			$("#extendValue").val(data.result.form_data_list[0]["extend_value"]);
	        		}else{
	        			console.error(data.errmsg);
	        		}
				}, function(){
					console.error("请求计算时长失败！");
				});
		},
		
		getDurationUrl : function(){
			var requestUrl = "/third/ding/thirdDingAttendance.do?method=preCalculate" +
			"&startTime=!{startTime}&finishTime=!{finishTime}&leaveCode=!{leaveCode}&methodCode=!{methodCode}&fdId=!{fdId}";
			// 假期类型详情
			var leave_code = $("[name='extendDataFormInfo.value(leave_code)']").val();
			if(!leave_code){
				dialog.alert("请先选择请假类型");
				return false;//选择请假类型
			}
			var params = {};
			var methodCode = commonUtil.getStatus();
			var fdId = "";
			if("edit" == methodCode){
				var urlParm = window.location.search;
				if (urlParm.indexOf("?") != -1) {
					var strs = urlParm.substr(1).split("&");
					for(var i=0;i<strs.length;i++){
				        var kv = strs[i].split('=');
				        if(kv[0] == 'fdId'){
				        	fdId = kv[1];
				        	break;
				        }
					}
				}
			}
			params["startTime"] = this.getStartTime();
			params["finishTime"] = this.getEndTime();
			params["leaveCode"] = leave_code;
			params["methodCode"] = methodCode;
			params["fdId"] = fdId;
			return util.urlResolver(requestUrl, params);
		},
		
		getStartTime : function(){
			var rs = "";
			rs = this.fromTimeWidget$form.val();
			if(this.leaveUnit === "halfDay"){
				rs += " " + this.fromHalfDay$form.val();
			}
			return rs;
		},
		
		getEndTime : function(){
			var rs = "";
			rs = this.toTimeWidget$form.val();
			if(this.leaveUnit === "halfDay"){
				rs += " " + this.toHalfDay$form.val();
			}
			return rs;
		},
		
		initInView : function(){
			// 根据单位显示上下午字段
			this.halfDayDisplay();
			// 设置【开始时间】和【结束时间】的值格式化
			switch(this.leaveUnit){
				case "halfDay":
				case "day":
					this.fromTimeWidget.set("value",this.formatDateStr(this.fromTimeWidget.get("value"),null, "yyyy-MM-dd"));
					this.toTimeWidget.set("value",this.formatDateStr(this.toTimeWidget.get("value"),null, "yyyy-MM-dd"));
					break;
				case "hour":
					break;
				default:
					console.log("can't match unit("+ leaveUnit +")!");
					break;
			}
			
			// 显示请假类型
			var leaveTxt = $form("leave_txt").val();
			domStyle.set(this.leaveTypeWidget.domNode,"display","block");
			query(this.leaveTypeWidget.domNode).append(leaveTxt);
			
			// 时长单位初始化
			this.updateUnitTxt();
			
		},
		
		updateUnitTxt : function(){
			// 假期单位
			var leaveUnitName = "时长";
			switch (this.leaveUnit) {
				case "halfDay":
				case "day":
					leaveUnitName = "时长(天)";
					break;
				case "hour":
					leaveUnitName = "时长(小时)";
					break;
				default:
					console.log("can't match unit(" + leaveUnit + ")!");
					break;
			}
			
			query(this.durationWidget.domNode).closest("tr").query("label.xform_label").html(leaveUnitName);
		}
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