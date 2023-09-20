Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
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
	"dojo/dom-construct"
	],function(declare, query, xformUtil, commonUtil, domStyle, validatorUtil, msg, topic, util, request, array, domConstruct){
		
	// 业务逻辑
		var Service = declare("third.ding.xform.builtin.goout.mobile.service", null , {
			// 日期控件的值改变事件
			DATE_VALUE_CHANGE: "/mui/form/datetime/change",
			
			// 【开始时间】组件（$form）
			fromTimeWidget$form : null,
			
			// 【开始时间】组件
			fromTimeWidget : null,
			
			// 【结束时间】组件
			toTimeWidget : null,
			
			// 【结束时间】组件（$form）
			toTimeWidget$form : null,	
		
			// 【时长】组件
			durationWidget : null,
			
			// 时长计算结果详情，请求计算时长之后缓存的变量
			durationInfo : null,
			
			doInit : function(){
				// 保存常用变量
				this.fromTimeWidget$form = $form("from_time");
				this.toTimeWidget$form = $form("to_time");				
				this.fromTimeWidget = commonUtil.getWidgetFrom$Form(this.fromTimeWidget$form);
				this.toTimeWidget = commonUtil.getWidgetFrom$Form(this.toTimeWidget$form);				
				this.durationWidget = commonUtil.getWidgetFrom$Form($form("duration"));
				
				var status = commonUtil.getStatus();
				if("add" == status || "edit" == status){
					this.initInEdit(status);
				}
			},
			initInEdit : function(status){
				this.initEventInEdit();
			},
			initEventInEdit : function(){
				// 校验器初始化
				validatorUtil.addCompareTimeValidation('compareTime', this.fromTimeWidget$form, this.toTimeWidget$form, "开始时间不能大于结束时间");
				validatorUtil.addDateLengthValidation('dateMaxLength(length)', this.fromTimeWidget$form, this.toTimeWidget$form, "外出时长不得超过{maxLength}天");
				validatorUtil.addDateLengthValidation('dateLimit(length)', this.fromTimeWidget$form, new Date().format("yyyy-MM-dd HH:mm"), "已超过审批截止时间");
				
				// 添加校验
				this.fromTimeWidget._set("validate", "required __datetime dateLimit(31)");
				this.toTimeWidget._set("validate", "required __datetime compareTime dateMaxLength(31)");
				
				// 【开始时间】和【结束时间】的值改变事件监听
				this.listenFromToTimeChange();
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
			
			// 如果条件满足则计算时长
			calcuateDuration : function(){
				if(this.isAllDateSet()){
					this.requestCalcuateDuration();
				}
			},
			
			// 检查是否所有相关的日期控件都已经设值了
			isAllDateSet : function(){
				if(this.fromTimeWidget$form.val() && this.toTimeWidget$form.val()){
					return true;
				}
				return false;
			},
			requestCalcuateDuration : function(){
			  var _self = this;
			  var userId = Com_Parameter.CurrentUserId;
			  if(!this.fromTimeWidget$form.val() || !this.toTimeWidget$form.val() || !userId){
				  return;
			  }
			  var pram = {
				  fdId:userId,
				  from_time:this.fromTimeWidget$form.val(),
				  to_time:this.toTimeWidget$form.val(),
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
		        	  console.log("error:"+data);
					},
				  success: function(data) {
					  if('0' == data.errcode){
						_self.durationWidget.set("value", data["result"]["duration"]);
	        			_self.durationInfo = data["result"];
					  }else{
						console.log("error:"+data);
						_self.durationWidget.set("value", "");
	        			_self.durationInfo = null;
					  }
				  }
		      });
			},
		});
	return Service;
})