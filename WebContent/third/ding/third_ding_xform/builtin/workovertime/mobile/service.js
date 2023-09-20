Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("record.css", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/css/","css",true);
define(["dojo/_base/declare",
	"dojo/query",
	"sys/xform/mobile/controls/xformUtil",
	"third/ding/third_ding_xform/resource/js/mobile/common",
	"dojo/dom-style",
	"third/ding/third_ding_xform/resource/js/mobile/validatorUtil",
	"sys/mobile/js/mui/dialog/Dialog",
	"mui/i18n/i18n!sys-mobile:mui",
	"dojo/topic",
	"mui/util",
	"dojo/request",
	"dojo/_base/array",
	"dojo/dom-construct"
	],function(declare, query, xformUtil, commonUtil, domStyle, validatorUtil, Dialog, msg, topic, util, request, array, domConstruct){
		
	// 业务逻辑
		var Service = declare("third.ding.third_ding_xform.builtin.workovertime.mobile.service", null , {
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
			
			// 加班人
			usersIdWidget : null,
			// 加班人
			usersId$form : null,
			
			showFlag : null,
			
			requestFlag : null,
			
			submitUser :null,
			
			diffUser : null,
			
			doInit : function(){
				// 保存常用变量
				this.fromTimeWidget$form = $form("from_time");
				this.toTimeWidget$form = $form("to_time");				
				this.usersId$form = $form("userid");				
				this.fromTimeWidget = commonUtil.getWidgetFrom$Form(this.fromTimeWidget$form);
				this.toTimeWidget = commonUtil.getWidgetFrom$Form(this.toTimeWidget$form);				
				this.durationWidget = commonUtil.getWidgetFrom$Form($form("duration"));
				this.usersIdWidget = commonUtil.getWidgetFrom$Form(this.usersId$form);
				this.showFlag = true;
				this.requestFlag = true;
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
			//校验多加班人
			requestCalcuateDuration : function(){
				var _self = this;
				//取参数
				var userid =this.usersId$form.val(); //获取多个加班人
				var from_time = this.fromTimeWidget$form.val();
				var to_time = this.toTimeWidget$form.val();
				if(!from_time || !to_time || !userid){
					return;
				}
				var pram = {
					  fdId:userid,
					  from_time:from_time,
					  to_time:to_time,
					  calculate_model:"1"
				};
				//请求接口，获取同班次和不同班次的人员数据
				var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=checkCalcuateOvertimeTime";
				  $.ajax({
			        url: url,
			        type: 'GET',
			        dataType: 'json',
			        data:pram,
			        error : function(data) {
			      	  	console.log("校验多加班人失败：thirdDingAttendance.checkCalcuateOvertimeTime");
						_self.durationWidget.set("value", "");
	        			_self.durationInfo = null;
					},
					success: function(data) {
						_self.durationWidget.set("value", data.firstDuration);
	        			_self.durationInfo = data.firstDuration;
	        			if(userid.indexOf(";") != -1){ //如果选择了多加班人
	        				submitUser = data.submitUser ;
	        				diffUser = data.diffUser ;
	        				if(diffUser){
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
	        						//var address = Address_GetAddressObj('extendDataFormInfo.value(userid.name)');
	        						//address.reset(";","ORG_TYPE_ALL",true,submitUser);
	        						alert(names+"等"+counts+"人需另行提交");
	        					}
	        				}
	    				}
					}
			    });
			},
			
			// 检查是否所有相关的日期控件都已经设值了
			isAllDateSet : function(){
				if(this.fromTimeWidget$form.val() && this.toTimeWidget$form.val()){
					return true;
				}
				return false;
			},
		});
	return Service;
})