define([
	"dojo/_base/declare",
	"dojo/dom-attr",
	"dijit/registry"
],function(declare, domAttr, registry){
	
	var validatorUtil = declare("third.ding.xform.resource.js.mobile.validatorUtil", null, {
		
		validation : null,
		
		disabledList : [],
		
		getValidation : function(){
			if(!this.validation){
				// 页面的校验框架是以对象的方式存放于"mui/view/DocScrollableView"视图里面，widgetid为"scrollView"
				var panelViewWidget = registry.byId("scrollView");
				if(panelViewWidget){
					this.validation = panelViewWidget._validation;
				}else{
					console.error("can't find widgt:scrollView");
				}
			}
			return this.validation;
		},
		
		setDisabled : function(dom){
			if(!dom){
				return;
			}
			var width = $(dom).width();
		    var height = $(dom).height();
		    var offset = $(dom).offset();

		    var $div = $("<div/>");
		    $div.addClass("_disabled");
		    $div.css("width", width);
		    $div.css("height", height);
		    $div.css("left", offset.left);
		    var top = offset.top;
		    if(dojoConfig.dingXForm && dojoConfig.dingXForm === "true"){
		    	// 减去44，是因为要减去头部的高度
		    	top = top - 44;
		    }
		    $div.css("top", top);
		    $div.appendTo(dom);

		    this.disabledList.push($div);
		},
		
		removeAllDisabled : function(){
			for (var i = 0; i < this.disabledList.length; i++) {
				this.disabledList[i].remove();
		    }
			this.disabledList = [];
		},
		
		addCompareTimeValidation : function(validatorName, startTime$form, endTime$form, errorMessage){
			var validation = this.getValidation();
			validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		        var result = true;
		        if (startTime$form.val() && endTime$form.val()) {
		        	// 传递进来不一定是dateTime
		            var start = new Date(startTime$form.val());
		            var end = new Date(endTime$form.val());
		            if (end.getTime() < start.getTime()) {
		                result = false;
		            }
		        }
		        return result;
		    });
		},
		
		/**
		 * 添加开始时间与结束时间的日期比较校验---适用例如批量请假的明细表中
		 */
		addBatchCompareTimeValidation:function (validatorName, errorMessage) {
		    var validation = this.getValidation();
		    validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		        let result = true;
		        var obj = $("[name='"+e.valueField+"']");
		        var fd_leave_start_time = $(obj).parents(".muiDetailTable").find("[name*='fd_leave_start_time']").val();
		        var fd_leave_end_time =  $(obj).parents(".muiDetailTable").find("[name*='fd_leave_end_time']").val();
		        if (fd_leave_start_time && fd_leave_end_time) {
		        	// 传递进来不一定是dateTime
		        	// Com_GetDate(fdEndTime.val(), 'datetime', Com_Parameter.DateTime_format);
		            let start = new Date(fd_leave_start_time);
		            let end = new Date(fd_leave_end_time);
		            if (end.getTime() < start.getTime()) {
		                result = false;
		            }
		        }
		        return result;
		    });
		},
		
		/**
		 * 添加校验当前行的请假时长是否超过假期余额---适用例如批量请假的明细表中
		 */
		addBatchDurationValidation:function(validatorName, errorMessage) {
			  var validation = this.getValidation();
		    validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		        let result = true;
		        var obj = $("[name='"+e.valueField+"']");
		        var jqye =  $(obj).parents(".muiDetailTable").find("[name*='type_ye']").val();
		        var qjsc =  $(obj).parents(".muiDetailTable").find("[name*='fd_item_duration']").val();
		        var fd_type_name =  $(obj).parents(".muiDetailTable").find("[name*='fd_type_name']").val();
		        qjsc = qjsc.split(" ")[0];
		        if (jqye && qjsc && fd_type_name) {
		        	if(fd_type_name.indexOf("剩余")>0){
		        		qjsc=parseFloat(qjsc);
		        		jqye=parseFloat(jqye);
		        		// 传递进来不一定是dateTime
		        		// Com_GetDate(fdEndTime.val(), 'datetime', Com_Parameter.DateTime_format);
		        		if (jqye - qjsc < 0) {
		        			result = false;
		        		}
		        	}
		        }
		        return result;
		    });
		},
		
		/**
		 * 校验是否能请假---适用例如批量请假的明细表中
		 */
		addBatchIsLeaveValidation:function (validatorName, errorMessage) {
			 var validation = this.getValidation();
			validation.addValidator(validatorName, errorMessage, function (v, e, o) {
				let result = true;
				 var obj = $("[name='"+e.valueField+"']");
				var itemDuration =  $(obj).parents(".muiDetailTable").find("[name*='fd_item_duration']");
				if(itemDuration.length>0){
					if($(itemDuration).hasClass("validateIsLeave")){
						result = false;
					}
				}
				return result;
			});
		},
		
		/**
		 * 校验是否能补卡---适用例如批量补卡的明细表中
		 */
		addBatchReplaceValidation :function (validatorName, errorMessage,m) {
			 var temp_self = this;
			 var validation = this.getValidation();
		     var ff =function (v, e, o) {
			     var id = e.id;
				 var hasdata=$("#"+id).siblings("[name*='fd_replacement_ul']")[0].getAttribute("hasdata");
                 if(hasdata && hasdata=='true'){
	                 return true;
                 }else {
     				 return false;
                 }
			 };
		     ff.m=m;
			 validation.addValidator(validatorName, errorMessage,ff );
		},
	    addBatchCheckNotCanSupplyValidation(validatorName, errorMessage) {
        	var temp_self = this;
            var validation = this.getValidation();
            var ff =function (v, e, o) {
                 var id = e.id;
                 var validatorresult=$("#"+id).siblings("[name*='fd_replacement_ul']").find("[name*='nowData']")[0].getAttribute("validatorresult");
                 if(validatorresult && validatorresult == 'pass'){
                      return true;
                 }else if(validatorresult && validatorresult == 'nopass'){
                      return false;
                 }else {
                      return false;
                 }
            };
            validation.addValidator(validatorName, errorMessage,ff );
        },
         addBatchChangeValidation(validatorName, errorMessage) {
                	var temp_self = this;
                    var validation = this.getValidation();
                    var ff =function (v, e, o) {
                         var id = e.id;
                         var error = $("#"+id)[0].getAttribute("error");
                         if(error && error.length >0 ){
                             var interfacevalidator = this.getValidator("validateBatchChange");
                             interfacevalidator.error = error;
                             return false;
                         }else {
                             return true;
                         }

                    };
                    validation.addValidator(validatorName, errorMessage,ff );
          },
         addBatchBatchWorkOverTimeValidation(validatorName, errorMessage) {
                	var temp_self = this;
                    var validation = this.getValidation();
                    var ff =function (v, e, o) {
                         var id = e.id;
                         var error = $("#"+id)[0].getAttribute("error");
                         if(error && error.length >0 ){
                             var interfacevalidator = this.getValidator("validateBatchWorkOverTime");
                             interfacevalidator.error = error;
                             return false;
                         }else {
                             return true;
                         }
                    };
                    validation.addValidator(validatorName, errorMessage,ff );
          },

		/**
		 * 添加校验同一类型的请假时长是否超额---适用例如批量请假的明细表中
		 */
		addBatchSunDurationValidation:function(validatorName, errorMessage) {
			var validation = this.getValidation();
			validation.addValidator(validatorName, errorMessage, function (v, e, o) {
				let result = true;
				var obj = $("[name='"+e.valueField+"']");
				//当前行假期类型的余额
				var jqye =  $(obj).parents(".muiDetailTable").find("[name*='type_ye']").val();
				var fd_type_name =  $(obj).parents(".muiDetailTable").find("[name*='fd_type_name']").val();
				if(fd_type_name && fd_type_name.indexOf("剩余") < 0){
					return result;
				}
				//当前行的请假类型
				var tr_code = $(obj).parents(".muiDetailTable").find("[name*='fd_leave_type']").val();
				
				var _table = $(obj).parents("#TABLE_DL_fd_batch_leave_table");
				var trs = $(_table).find(".muiDetailTable"); 
				var sunDuration = 0.00;
		         //循环所有tr
		         for (var i = 0; i < trs.length; i++) {
		        	 //获取每行选中的假期类型code
					var leaveCode = $(trs[i]).find("[name*='fd_leave_type']").val();
					if(leaveCode != tr_code){
						continue;
					}			
					var duration_Str = $(trs[i]).find("[name*='fd_item_duration']").val();
					if(!duration_Str){
						continue
					}
					var duration  = duration_Str.split(" ")[0];
					sunDuration += parseFloat(duration);
		         }
		         var ed = parseFloat(jqye);
		         if(sunDuration-ed >0){
		        	 result = false;
		         }		
				return result;
			});
		},
		
		/**
		 * 添加校验请假时间重复---适用例如批量请假的明细表中
		 */
		addBatchRepeatTimeValidation:function (validatorName, errorMessage) {
			var validation = this.getValidation();
		    validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		        let result = true;
		        if(!v){
		        	return;
		        }
		        var obj = $("[name='"+e.valueField+"']");
		        //当前选择的时间
		        var objTime = new Date(v);
		        var _table = $(obj).parents("#TABLE_DL_fd_batch_leave_table");
				var trs = $(_table).find(".muiDetailTable");    
		        //当前行的开始时间和结束时间
				var fd_leave_start_time_tr = $(obj).parents(".muiDetailTable").find("[name*='fd_leave_start_time']").val();
				var fd_leave_end_time_tr = $(obj).parents(".muiDetailTable").find("[name*='fd_leave_end_time']").val();
		        //当前行
		        var nowTrIndex = $(obj).parents(".muiDetailTable")[0].innerText;
		        //循环所有tr
		        for (var i = 0; i < trs.length; i++) {
					var fd_leave_start_time = $(trs[i]).find("[name*='fd_leave_start_time']");
					var fd_leave_end_time = $(trs[i]).find("[name*='fd_leave_end_time']");			
					
					if(!fd_leave_start_time || !fd_leave_end_time ){//没有请假开始时间和结束时间的行： 跳过
						continue;
					}
					if(!fd_leave_start_time.val() || !fd_leave_end_time.val()){//请假开始时间或请假结束时间为空的行：跳过
						continue;
					}
					var startTime = new Date(fd_leave_start_time.val());
					var endTime = new Date(fd_leave_end_time.val());
					if(objTime.getTime() >startTime.getTime() && objTime.getTime()< endTime.getTime()){
						 result = false;
						 break;
					}
					//校验复制行时的重复
					if(!fd_leave_start_time_tr || !fd_leave_end_time_tr){
						continue;
					}
					var rowIndex = trs[i].innerText;
					if(rowIndex != nowTrIndex){
						var startTime_tr = new Date(fd_leave_start_time_tr);
						var endTime_tr = new Date(fd_leave_end_time_tr);
						if(startTime_tr.getTime() == startTime.getTime() && endTime_tr.getTime() == endTime.getTime()){
							result = false;
						}
					}
				}
		        return result;
		    });
		},
		
		addDateLengthValidation : function(validatorName, startTime$form, endTime$form, errorMessage) {
			var validation = this.getValidation();
		    validation.addValidator(validatorName, errorMessage, function (v, e, o) {
		        var result = true;
		        if (startTime$form.val()) {
		            var fdEndTimeDate;
		            if(typeof(endTime$form) != 'string') {
		                fdEndTimeDate = endTime$form.val();
		            } else {
		                fdEndTimeDate = endTime$form;
		            }
		            var length = isNaN(o['length']) ? 0 : parseInt(o['length']);
		            if (length === 0 || this.getValidator('isEmpty').test(v)) {
		                return true;
		            }
		            var start = new Date(startTime$form.val());
		            var end = new Date(fdEndTimeDate);
		            if ((end.getTime() - start.getTime()) / 1000 / 60 > length * 24 * 60) {
		                result = false;
		                o['maxLength'] = length;
		            }
		        }
		        return result;
		    });
		}
	})
	
	return new validatorUtil();
})