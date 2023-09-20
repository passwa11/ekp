Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
define(["dojo/_base/declare",
		"mui/tabbar/TabBarButton",
		"dojo/dom-class",
		"mui/device/adapter",
		"dojo/_base/lang",	
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
		"dijit/registry", 
		"mui/dialog/Dialog",
		"dojo/dom-attr",
		"mui/dialog/Tip"
],function(declare,TabBarButton,domClass,adapter,lang, query, xformUtil, commonUtil, domStyle, validatorUtil,msg, topic, util, request, array, domConstruct,right, registry,Dialog,domAttr,Tip){	
	
	// 业务逻辑
		var Service = declare("third.ding.third_ding_xform.builtin.destroyleave.mobile.service", null , {
			// 时长计算结果详情，请求计算时长之后缓存的变量
			durationInfo : null,
			
			level_forms : null,//请假单下拉框
			
			// 下拉框的窗口关闭事件
			SELECT_CALLBACK : 'mui/form/select/callback',
			
			// 日期控件的值改变事件
			DATE_VALUE_CHANGE: "/mui/form/datetime/change",
			
			methodStatus:null,
			
			//当前请假单的单位
			unitStr : "天",
			
			//当前请假单的-请假时长
			durationTime : null,
			
			//当前请假单请假单位
			dw : "day",
			
			//请假开始时间
			fromTimes : null,
			
			//请假结束时间
			toTimes : null,
			
			//当前请假单请假类型
			levelCode : null,
			
			from_half_day_str : "AM",
			
			to_half_day_str : "AM",
			
			//单位
			unit : null,
			
			//按明细销假时，所需的时长接口返回数据
			detailDate : null,
			
			classInfo : null,

			dialogObj : null,
			
			methodStatus : null,
			
			cardNo : null,
			//选择的请假单
			fd_name : null,
			doInit : function(){
				// 保存常用变量
				$(".view_level_form").css("display","none");//pc端查看页请假单展示隐藏
				$(".level_form_div").css("display","none");//pc端卡片隐藏
				$("input[name*='extendDataFormInfo.value(duration)']").attr("disabled",true);
				$("input[name*='extendDataFormInfo.value(haveLeaveTime)']").attr("disabled",true);
//				$("input[name*='extendDataFormInfo.value(cancelLevelInfo)']").removeAttr("readonly");
//				$("input[name*='extendDataFormInfo.value(cancelLevelInfo)']").attr("disabled",true);
				var status = commonUtil.getStatus();
				methodStatus = status;
				if("add" == status || "edit" == status){
					this.initInEdit(status);
				}else{
					this.ding_Init_View();
				}
				this._export(this);
			},
			initInEdit : function(status){
				this.initEventInEdit(status);
			},
			initEventInEdit : function(status){
				//默认隐藏按时间销假展示的两行
				this.noShowTimeTr(false);	
				//默认隐藏卡片行
				this.noShowMobile(false);
				//初始化请假单下拉框
				this.initLeaveForm(status);
				//监听请假单触发事件
				this.listenLeaveCodeChange();
				//监听销假时间、时间段
				this.listenFromToTimeChange();
				
				this.selectCancelType(this);//销假类型点选触发事件
				this.selectDateType();
				
				if("edit"==status){//修改页
					this.ding_Init_View(); 
				}
			},			
			//初始化加载请假单
			initLeaveForm : function(status){
				 var levelFormHidden = $("input[name*='extendDataFormInfo.value(level_form)']");
				 var levelForm =  $(levelFormHidden).parent('.oldMui');
				 var cardInfoStr = $("[name*='extendDataFormInfo.value(cardInfo)']").val();
				 var cardInfo="";
				 if(cardInfoStr){
					 cardInfo = JSON.parse(cardInfoStr);
				 }
				 $(levelForm).addClass("qjdForm");
				 var wgt = registry.byNode(levelForm[0]);
				 level_forms = wgt;
				 if(!wgt){
					 Tip.tip({text:"页面加载异常"});
					 return ;//需要登录
				  }
				  var pram = {
				  };
				 /* wgt.empty();
				  levelForm.append('<option value="">==请选择==</option>');*/
				  var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=cancelLeave";
				  var dataLevelForm = new Array();
				  
				  var defultOpt={"text":"===请选择===","value":"","selected":"true"};
				  dataLevelForm[0] = defultOpt;
				  $.ajax({
			         url: url,
			         type: 'GET',
			         dataType: 'json',
			         data:pram,
			         async:false, 
			         error : function(data) {
			        	 Tip.tip({text:"请假单初始化异常"});
			         },
					 success: function(data) {
						if(data && data.length>0){
							var prams=[];
							for (var i = 0; i < data.length; i++) {
								var prams1={};
								prams1["text"]=data[i].fd_name;
								prams1["value"]=data[i].fd_ekp_instance_id;
								prams1["selected"] = false;
								if("edit"==status && data[i].fd_ekp_instance_id == cardInfo["cardNo"]){
									prams1["selected"] = true;
								}else{
									prams1["selected"] = false;
								}								
								dataLevelForm[i+1]=prams1;
								prams.push(data[i]);
							}								
							wgt.store.data=dataLevelForm;
							wgt.values=dataLevelForm;
							$("#cancelLeave").val(JSON.stringify(prams));
							//修改页
							if("edit"==methodStatus){
								var cardInfoStrs = $("#cardInfo").val();
								var card = JSON.parse(cardInfoStrs);
								$("input[name*='extendDataFormInfo.value(level_form)']").val(card["cardNo"]);
							}
						
						}		
					 }
			     });				  
			},
			
			//选择请假单
			selectLevelForm :function(){
				this.noShowMobile(true);				
				var levelForm = $("input[name*='extendDataFormInfo.value(level_form)']").val();//选择的下拉选项value
				$(".detail_div").css("display","none");//选择请假单，隐藏上一张请假单的明细表
				if(!levelForm){
					$(".level_form_div").css("display","none");
					return;
				}				
				//取返回所有值
				var cancelLeave = $("#cancelLeave").val();
				if(!cancelLeave){
					return;
				}				
				var level = eval("("+cancelLeave+")");
				var $singerLeave = $("#singerLeave");
				for (var i = 0; i < level.length; i++) {
					if(levelForm == level[i].fd_ekp_instance_id){
						$singerLeave.val(JSON.stringify(level[i].extend_value));
						$("#leaveCode").val(level[i].leaveCode);
						levelCode = level[i].leave_code;
						from_half_day_str = level[i].from_half_day_str;
						to_half_day_str = level[i].to_half_day_str;
						fromTimes = level[i].from_time_str;
						toTimes = level[i].to_time_str;
						fd_name = level[i].fd_name;
						unit = level[i].unit;
						break;
					}
				}	
				
				var singerLeave = JSON.parse($singerLeave.val());
				var extension = singerLeave["extension"];
				var durationInHour = singerLeave["durationInHour"]; //小时
				var durationInDay = singerLeave["durationInDay"]; //天
				var approveInfo = singerLeave["detailList"][0]["approveInfo"]; 
				var durations = "";
				if("DAY"==unit || "day"==unit || "HALFDAY" == unit || "halfDay"==unit){
					durations = durationInDay+"天"
					durationTime = durationInDay;
					unitStr = "天";
					if("HALFDAY" == unit || "halfDay"==unit){
						dw ="halfDay";
					}else{
						dw="day";
					}
				}
				if("HOUR"== unit|| "hour"==unit){
					durations = durationInHour+"小时"
					durationTime = durationInHour;
					unitStr = "小时";
					dw ="hour";
				}
				//准备组装cardInfo
				var startTime=fromTimes;
				var toTime = toTimes;			
				if("halfDay"===dw){
					startTime = startTime.substring(0,10);
					toTime = toTime.substring(0,10);
					if("AM"===from_half_day_str){
						startTime = startTime+" 上午";
					}else{
						startTime = startTime+" 下午";
					}
					if("AM"===to_half_day_str){
						toTime = toTime+" 上午";
					}else{
						toTime = toTime+" 下午";
					}				
				}else if("day"===dw){
					startTime = startTime.substring(0,10);
					toTime = toTime.substring(0,10);
				}
						
				var cardInfo = {};
				cardInfo["startTime"] = startTime;
				cardInfo["toTime"] = toTime;
				cardInfo["durations"] = durations;
				cardInfo["tag"] = extension["tag"];
				cardInfo["cardNo"] = $("input[name*='extendDataFormInfo.value(level_form)']").val();
				cardInfo["selectFormName"] = fd_name;
				$("input[name*='extendDataFormInfo.value(level_start_time)']").val(startTime);	
				$("input[name*='extendDataFormInfo.value(level_end_time)']").val(toTime);	
				$("input[name*='extendDataFormInfo.value(level_time)']").val(durations);	
				$("input[name*='extendDataFormInfo.value(level_no)']").val($("input[name*='extendDataFormInfo.value(level_form)']").val());	
				$("input[name*='extendDataFormInfo.value(cardInfo)']").val(JSON.stringify(cardInfo));
				//选中请假单，清空已选的明细
				$("[name='extendDataFormInfo.value(cancelLevelInfo)']").val('');
				$("[name='extendDataFormInfo.value(detailDate)']").val('');
				
				fromTimes = this.to_time_dw(fromTimes,dw,from_half_day_str);
				toTimes = this.to_time_dw(toTimes,dw,to_half_day_str);
				//初始化销假时长和剩余时长
				$("input[name*='extendDataFormInfo.value(duration)']").val(durationTime+unitStr);
				$("input[name*='extendDataFormInfo.value(haveLeaveTime)']").val("0"+unitStr);
				//默认全部销假
				this.initSelectType();
			},
			
			//三种销假类型-默认选中全部销假
			initSelectType :function(){
				//隐藏按时间销假的展示框
				this.noShowTimeTr(false);
				
				//初始化全局变量
				unitStr = "";
				durationTime="";
				dw ="";
				fromTimes ="";
				toTimes ="";
				levelCode ="";
				from_half_day_str ="";
				to_half_day_str ="";
				fd_name ="";
				
				//默认全部销假
				var cancelTypeGroup = $("input[name*='extendDataFormInfo.value(cancelType)']");
				for (var i = 0; i < cancelTypeGroup.length; i++) {					
					console.log(cancelTypeGroup[i].value);
					if("all"==cancelTypeGroup[i].value){
						$(cancelTypeGroup[i]).click(); 
						$(cancelTypeGroup[i]).parent(".muiRadioItem").find(".muiRadioCircle").addClass("active"); 
					}else{
						$(cancelTypeGroup[i]).parent(".muiRadioItem").find(".muiRadioCircle").removeClass("active"); 
					}
				}
			},
			//选择销假类型
			selectCancelType : function(v){
				$("input[name*='extendDataFormInfo.value(cancelType)']").on("change",function(){
					var levelForm = $("input[name*='extendDataFormInfo.value(level_form)']").val();//请假单
					if(!levelForm){
						//恢复为默认全部销假被选中
						v.initSelectType();
						Tip.tip({text:"请选择请假单"});
						return;
					}
					var levelType = $("input[name='extendDataFormInfo.value(cancelType)']").val();
					if("all"===levelType){
						v.selectAlls();
						$("[name='extendDataFormInfo.value(cancelLevelInfo)']").val('');
						$("[name='extendDataFormInfo.value(detailDate)']").val('');
						v.noShowCancelDetailTr(false);
					}
					if("time"===levelType){
						v.selectTimes();
						$("[name='extendDataFormInfo.value(cancelLevelInfo)']").val('');
						$("[name='extendDataFormInfo.value(detailDate)']").val('');
						v.noShowCancelDetailTr(false);
					}
					if("detail"===levelType){
						v.noShowCancelDetailTr(true);
						v.selectDetails();//按明细销假
					}
				});
			},
			_export:function(v){
				window.saveCancelDetail=function(){
					var levelType = $("input[name='extendDataFormInfo.value(cancelType)']").val();
					if("detail"==levelType){
						$(".detail_div").html();
						$("input[name*='extendDataFormInfo.value(detailInfoForm)']").val('');
						$("input[name*='extendDataFormInfo.value(detailInfoForm)']").val($(".detail_div").html());
					}
					var card = $("input[name*='extendDataFormInfo.value(cardInfo)']").val();
					var cardInfo = JSON.parse(card);
					cardInfo["unitStr"] = unitStr;
					cardInfo["durationTime"] = durationTime;
					cardInfo["dw"] = dw;
					cardInfo["fromTimes"] = fromTimes;
					cardInfo["fromTimes"] = fromTimes;
					cardInfo["toTimes"] = toTimes;
					cardInfo["levelCode"] = levelCode;
					cardInfo["from_half_day_str"] = from_half_day_str;
					cardInfo["to_half_day_str"] = to_half_day_str;
					cardInfo["unit"] = unit;
					//cardInfo["classInfo"] = classInfo;
					//cardInfo["detailDate"] = detailDate;
					var cancelStartTime = $("input[name*='extendDataFormInfo.value(cancelStartTime)']").val();
					var cancelEndTime = $("input[name*='extendDataFormInfo.value(cancelEndTime)']").val();					
					$("input[name*='extendDataFormInfo.value(cancelLevelStartTime)']").val(cancelStartTime);
					$("input[name*='extendDataFormInfo.value(cancelLevelEndTime)']").val(cancelEndTime);					
					$("input[name*='extendDataFormInfo.value(cardInfo)']").val(JSON.stringify(cardInfo));
				},
				
				//选中弹框节点事件
				$(".muisDestroyLeaveTimeSelectorItem").live('click',function() {  
					if($(this).hasClass('active')){
						$(this).removeClass('active');
					}else{
						$(this).addClass('active');
					}
					
				    var item = $(this).parent().find('.muisDestroyLeaveTimeSelectorItem');
				    var count =0;
				    if($(this).hasClass('active')){
					    for (var i = 0; i < item.length; i++) {
							if($(item[i]).hasClass('active')){
								count++;
							}else{
								break;
							}
						}
					    $(this).parent().prev('.muiDestroyLeaveCheckbox').find("[name='mobileItem']").prop("checked" , item.length == count ? true :false); 
					    $(this).parent().prev('.muiDestroyLeaveCheckbox').find("[name='mobileItem']").addClass('selectedCheckBox');
				    }else{
				    	$(this).parent().prev('.muiDestroyLeaveCheckbox').find("[name='mobileItem']").prop("checked" , false);  
				    	$(this).parent().prev('.muiDestroyLeaveCheckbox').find("[name='mobileItem']").removeClass('selectedCheckBox');
				    }
				}); 
				//选中弹窗复选框事件
				$(".muiDestroyLeaveCheckbox").live('click',function() { 
					var mobileItem = $(this).find("[name='mobileItem']");
					 if($(mobileItem).is(":checked")){
						 $(mobileItem).prop("checked",false);
						 $(mobileItem).removeClass('selectedCheckBox');
						 $(mobileItem).parent().next('.muisDestroyLeaveTimeSelector').find('.muisDestroyLeaveTimeSelectorItem').removeClass('active'); 
					 }else{
						 $(mobileItem).prop("checked",true);
						 $(mobileItem).addClass('selectedCheckBox');
						 $(mobileItem).parent().next('.muisDestroyLeaveTimeSelector').find('.muisDestroyLeaveTimeSelectorItem').addClass('active');
					 }											 
				});
			},
			//勾选按天的复选框-组合detailDate数据
			selectDayCheckBox:function(o){
				var day_span = $(o).parent('.muiDestroyLeaveCheckbox').find('.day_span').text();
				day_span = day_span.substring(0,10);
				if($(o).is(":checked")){
					this.setCancelDetailDate(day_span,"");
				}else{
					this.setDetailDate(day_span,"");
				}
			},
			//勾选按半天的复选框-组合detailDate数据
			selectHalfDayCheckBox:function(o){
				var day_span = $(o).parent('.muiDestroyLeaveCheckbox').find('.day_span').text();
				day_span = day_span.substring(0,10);
				if($(o).is(":checked")){
					this.setCancelDetailDate(day_span,"");
				}else{
					this.setDetailDate(day_span,"");
				}
			},
			setDetailDate:function(date,time){
				var detaildate = $("#detailDate").val();
				var jsonStr = "";
				if(detaildate){//不为空
					if(detaildate.indexOf(date)<0){
						detaildate = detaildate.replace("]","");
						detaildate+=',{';
						detaildate+='"date":"'+date+'",';
						detaildate+='"time":"'+time+'"';
						detaildate+='}]';
						jsonStr = detaildate;
					}else{
						var dataJson = JSON.parse(detaildate);
						for (var i = 0; i < dataJson.length; i++) {
							if(date == dataJson[i]["date"]){//如果detailDate存在值，则重新赋值
								if(dataJson[i]["time"]){
									if(dataJson[i]["time"].indexOf(time)<0){
										dataJson[i]["time"] += (";"+time);
									}
								}else{
									dataJson[i]["time"] += time;
								}								
							}
						}
						jsonStr=JSON.stringify(dataJson);
					}		
				}else{//为空
					jsonStr+='[{';
					jsonStr+='"date":"'+date+'",';
					jsonStr+='"time":"'+time+'"';
					jsonStr+='}]';
				}
				$("#detailDate").val(jsonStr);
			},
			//取消选中
			setCancelDetailDate:function(date,time){
				var detaildate = $("#detailDate").val();
				var jsonStr = [];
				if(detaildate){
					if(detaildate.indexOf(date)>0){
						var detailJson = JSON.parse(detaildate);
						for (var i = 0; i < detailJson.length; i++) {
							if(detailJson[i] && date != detailJson[i]["date"]){
								jsonStr[jsonStr.length]=detailJson[i];
							}else{
								if(time){
									if(time.indexOf(",")<0){//不是勾选复选框，点击时间段一个一个取消的，无,
										var timeStr = detailJson[i]["time"].split(",");
										for (var m = 0; m < timeStr.length; m++) {
											if(time == timeStr[m]){
												timeStr = timeStr.splice(m,1);
												detailJson[i]["time"] = timeStr.toString();
											}
										}
										jsonStr[jsonStr.length]=detailJson[i];
									}else{//勾选复选框取消的，含，
//										jsonStr[jsonStr.length]="";
									}
								}
							}
						}
						if(jsonStr.length>0){
							$("#detailDate").val(JSON.stringify(jsonStr));
						}else{
							$("#detailDate").val("");
						}
					}
				}
			},
			//全部销假
			selectAlls:function(){
				//Ding_Time_Validate_Change("");
				this.noShowTimeTr(false);	
				$(".detail_div").css("display","none");
				this.hideTimeTr();
				//重置销假时长和剩余时长
				$("input[name*='extendDataFormInfo.value(duration)']").val(durationTime+unitStr);
				$("input[name*='extendDataFormInfo.value(haveLeaveTime)']").val("0"+unitStr);	
			},
			
			//按时间销假
			selectTimes:function(){
				//展示按时间两行
				$(".detail_div").css("display","none");
				var $cancelFromDay = $("input[name*='extendDataFormInfo.value(cancelFromDay)']");
				var $cancelToDay = $("input[name*='extendDataFormInfo.value(canceToDay)']");
				if("halfDay"!=unit){		
					$($cancelFromDay).parent(".oldMui").css("display","none");
					$($cancelToDay).parent(".oldMui").css("display","none");
				}
				this.noShowTimeTr(true);	
				//清空
				this.hideTimeTr();
				//Ding_Time_Validate_Change(unit);
			},
			
			//按明细销假
			selectDetails : function(){
				//1、销假明细
				var cancelLevelInfoInput = $("input[name*='extendDataFormInfo.value(cancelLevelInfo)']");
				var cancelLevelInfotr = $(cancelLevelInfoInput).parent('.oldMui').parents('td').parent('tr');
				$(cancelLevelInfotr).css("display","table-row");	
				this.noShowTimeTr(false);	
				this.hideTimeTr();
				//2、请求请假预计算时长,将需要的参数加载更新进全局参数
				this.initDetailDate();
			},
			
			//事件注册--主要适用按时间销假
			selectDateType : function(){			
				this.listenFromToTimeChange();
				var _this = this;
				$("input[name*='extendDataFormInfo.value(cancelLevelInfo)']").on("click", function(){
					_this.initDetailsForUnit();
				});
			},
			initDetailsForUnit:function(){
				//1、取到请假开始时间、和结束时间、单位、请假时长
				var startLevelTimes = fromTimes;
				var endLevelTimes = fromTimes;
				var levelDuration = durationTime;
				var levelUnit = dw;
				
				//3、按请假单位分别初始化明细表
				var htmlStr = '<div class="container_m"><div class="muisDestroyLeaveContent">';
			//	htmlStr += '<input id="detailDate" name="extendDataFormInfo.value(detailDate)" type="hidden" value="">';
				if("day"===levelUnit){
					htmlStr += this.initDayDetail();
				}
				if("halfDay"===levelUnit){
					htmlStr += this.initHalfDayDetail();
				}
				if("hour"===levelUnit){
					htmlStr += this.initHourDetail();
				}
				htmlStr +='</div></div>';
				
				var muiDialogElement = $(".muiDialogElement");
				
				//4、跳转新页面或弹窗				
				var contentNode = domConstruct.create('div', {
					className : 'muiBackDialogElement',
					innerHTML : '<div>'+htmlStr+'<div>'
				});
					
				Dialog.element({
					'title' : '销假明细',
					'showClass' : 'muiBackDialogShow cancelLevel_dialogElement',
					'element' : contentNode,
					'scrollable' : false,
					'parseable': false,
					'width':'100%',
					'buttons' : [ {
						title : "取消",
						fn : function(dialog) {
							dialog.hide();
						}
					} ,{
						title : "确定",
						'showClass':'muiOkDialogShow',
						fn : lang.hitch(this,function(dialog) {							
							var selectDate ="";							
							 if('day'==unit){
								 var mobileItems = $("[name='mobileItem']");
								 for (var i = 0; i < mobileItems.length; i++) {
									 var day_span = $(mobileItems[i]).parent('.muiDestroyLeaveCheckbox').find('.day_span').text();
									 day_span = day_span.substring(0,10);
									 if($(mobileItems[i]).is(":checked")){//销假明细展示被选中的; 										 
										 if(selectDate){
											 selectDate+= (";"+day_span);
										 }else{
											 selectDate+= day_span;
										 }
									 }else{//没被选中的 ,传后台
										 this.selectDayCheckBox(mobileItems[i]);
									 }								
								}
								 $("[name*='extendDataFormInfo.value(cancelLevelInfo)']").val(selectDate);
								 var selectDate_sz = selectDate.split(";").length;
								 $("[name*='extendDataFormInfo.value(duration)']").val(selectDate_sz+unitStr);
								 $("[name*='extendDataFormInfo.value(haveLeaveTime)']").val((parseFloat(durationTime-selectDate_sz).toFixed(2))+unitStr);
							 }
							 
							 if('hour'==unit){
								 var item = $(".muisDestroyLeaveTimeSelectorItem");
								 for (var i = 0; i < item.length; i++) {
									 //取小时时间段的日期
									 var day_span_text =  $(item[i]).parent('.muisDestroyLeaveTimeSelector').prev('.muiDestroyLeaveCheckbox').find('.day_span').text();
									 day_span_text = day_span_text.substring(0,10);
									 var hour = $(item[i]).text();
									 if($(item[i]).hasClass("active")){//被选中的时间段--->展示
										 if(selectDate){
											 selectDate+= (";"+day_span_text+" "+hour);
										 }else{
											 selectDate+= (day_span_text+" "+hour);
										 }
									 }else{
										 this.setDetailDate(day_span_text,hour);
									 }
								 }
								 $("[name*='extendDataFormInfo.value(cancelLevelInfo)']").val(selectDate);
								 var selectDate_sz = selectDate.split(";").length;
								 $("[name*='extendDataFormInfo.value(duration)']").val(selectDate_sz+unitStr);
								 $("[name*='extendDataFormInfo.value(haveLeaveTime)']").val((parseFloat(durationTime-selectDate_sz).toFixed(2))+unitStr);
							 }
							 if('halfDay'==unit){
								 var item = $(".muisDestroyLeaveTimeSelectorItem");
								 for (var i = 0; i < item.length; i++) {
									 //取上午下午的日期
									 var day_span_text =  $(item[i]).parent('.muisDestroyLeaveTimeSelector').prev('.muiDestroyLeaveCheckbox').find('.day_span').text();
									 day_span_text = day_span_text.substring(0,10);
									 var half = $(item[i]).text();
									 if($(item[i]).hasClass("active")){//被选中的上/下午--->展示
										 if(selectDate){
											 selectDate+= (";"+day_span_text+" "+half);
										 }else{
											 selectDate+= (day_span_text+" "+half);
										 }
									 }else{//未被选中的上/下午--传值
										 if("上午"==half){
											 half = "AM";
										 }else{
											 half="PM";
										 }										 
										 this.setDetailDate(day_span_text,half);
									 }									
								}
								 $("[name*='extendDataFormInfo.value(cancelLevelInfo)']").val(selectDate);
								 var selectDate_sz = selectDate.split(";").length*0.5;
								 $("[name*='extendDataFormInfo.value(duration)']").val(selectDate_sz+unitStr);
								 $("[name*='extendDataFormInfo.value(haveLeaveTime)']").val((parseFloat(durationTime-selectDate_sz).toFixed(2))+unitStr);
							 }
							 dialog.hide();
						})
					} ]
				});	
				this.initselect();
			},
			initselect:function(){
				if("day"==unit){
					return;
				}
				var mobileItemObj =	$("[name*='mobileItem']");
				for (var i = 0; i < mobileItemObj.length; i++) {
					var muisDestroyLeaveTimeSelectorItem = $(mobileItemObj[i]).parent(".muiDestroyLeaveCheckbox").next('.muisDestroyLeaveTimeSelector').find(".muisDestroyLeaveTimeSelectorItem").length;
					var active = $(mobileItemObj[i]).parent(".muiDestroyLeaveCheckbox").next('.muisDestroyLeaveTimeSelector').find(".active").length;
					if(muisDestroyLeaveTimeSelectorItem ==active){
						$(mobileItemObj[i]).prop("checked" , true);  
						$(mobileItemObj[i]).addClass("selectedCheckBox" );  
					}
				}
				
			},
			//初始化按小时销假明细表--按明细销假
			initHourDetail:function(){
				var detailDayHtml = "";
				if(!detailDate){
					detailDate = this.initDetailDate();
				}
				if(!detailDate || !detailDate.success){
					//时长响应为空,或者响应success为false，不初始化
					return detailDayHtml;
				}		
				var record = detailDate.result.form_data_list[0]["extend_value"];
				var _object = this;
				if(record){
					var detailList = new Array();
					record = eval("("+record+")");
					var halfDayFromTime = fromTimes.substring(0,10);
					var halfDayToTimes = toTimes.substring(0,10);
					detailList = record["detailList"];
					var details;
					var cancelLevelInfo = $("[name='extendDataFormInfo.value(cancelLevelInfo)']").val();
					this.getClassInfoJson(detailList);//获取每天的班次信息存入全局变量classInfo
					for (var i = 0; i < detailList.length; i++) {
						details = detailList[i];
						var dateStartTime = details["approveInfo"]["fromTime"];
						var dateToTime = details["approveInfo"]["toTime"];
						dateStartTime = this.to_time(dateStartTime,"hour");
						dateToTime = this.to_time(dateToTime,"hour");
						var workDate = this.getLocalTime(details["workDate"],"date");
						var week = this.getWeek(workDate);
						var hour2 = parseInt(dateStartTime.split(":")[0]);
						var hour3 = parseInt(dateToTime.split(":")[0]);
						if(details["isRest"]){//休息日-预留
							
						}else{//工作日
							var classInfos = this.getClassInfo(workDate);//获取当天的班次信息
							if(classInfos){//有班次
								var timesimpe=[];
								var startDateTime = classInfos[0]["startTime"];
								var endDateTime = classInfos[classInfos.length-1]["endTime"];
								for (var w = 0; w < classInfos.length; w++) {
									var timesimpes  = this.getEveryHours(classInfos[w]["startTime"],classInfos[w]["endTime"]);
								}
								
								detailDayHtml += '<label class="muiDestroyLeaveCheckbox"><input type="checkbox"  id="mobile_1" name="mobileItem"><label for="mobile_1"></label>';
								detailDayHtml += '<span class="day_span">'+workDate+'</span>';
								detailDayHtml += '<span class="day_span_week">'+week+'('+dateStartTime+' ~ '+dateToTime+')</span>';
								
								detailDayHtml += '<span class="timesEveryDay">'+details["approveInfo"]["durationInHour"]+'小时</span></label>';//请假几个小时
								
								detailDayHtml += ' <div class="muisDestroyLeaveTimeSelector">';
								var flag = true;
								for (var f = 0; f < classInfos.length; f++) {//开始初始化工作日明细
									timesimpe  = this.getEveryHours(classInfos[f]["startTime"],classInfos[f]["endTime"]);
									//初始化时间段
									for (var m = 0; m <= timesimpe.length-1; m++) {
										if(timesimpe[m]&&timesimpe[m+1]){
											var timed = timesimpe[m];
											var timedInt = parseInt(timed.split(":")[0]);
											
											var timedAfter = timesimpe[m+1];
											var timedIntAfter= parseInt(timedAfter.split(":")[0]);
											var cancelLevelInfo_sz = cancelLevelInfo.split(";");											
											if(cancelLevelInfo && cancelLevelInfo_sz.length>0){//销假明细控件有数据
												//取detailDate 反向勾选
												var detailStr = $("[name*='extendDataFormInfo.value(detailDate)']").val();
												if(null != cancelLevelInfo_sz && cancelLevelInfo_sz.length>0){
													var detailJson = JSON.parse(detailStr)
													var dateStr;
													var timeStr;
													
													var times1="";
													var times2="";
													if(hour2 == timedInt && hour3 >= timedIntAfter){
														if(hour3==timedIntAfter){
															times1=dateStartTime;
															times2=dateToTime;
														}else{
															times1=dateStartTime;
															times2=timesimpe[m+1];
														}
													}
													if(hour2 < timedInt && timedInt < hour3 && hour3 >= timedIntAfter){
														if(hour3==timedIntAfter){
															times1=timesimpe[m];
															times2=dateToTime;
														}else{
															times1=timesimpe[m];
															times2=timesimpe[m+1];
														}
													}
													if(hour2 < timedInt && timedInt == hour3 && hour3 >= timedIntAfter){
														if(hour3==timedIntAfter){
															times1=timesimpe[m];
															times2=dateToTime;
														}else{
															times1=timesimpe[m];
															times2=timesimpe[m+1];
														}
													}
													if(!times1 && !times2){
														times1="00:00";
														times2="23:59";
													}
													var timeStr1 = workDate+" "+times1+"~"+times2;
													if(cancelLevelInfo_sz.indexOf(timeStr1)>-1){//当天至少勾选了一个														
														for (var sa = 0; sa < cancelLevelInfo_sz.length; sa++) {
															dateStr = cancelLevelInfo_sz[sa].split(" ")[0]
															timeStr = cancelLevelInfo_sz[sa].split(" ")[1];
															var dateflag = this.compareTwoTimes(dateStr, workDate);
															if(dateflag && timeStr){
																if(cancelLevelInfo_sz.indexOf(timeStr1)>-1){
																	detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem active" >'+times1+'~'+times2+'</div>';
																	break;
																}else{
																	if("00:00" != times1 && "23:59"!=times2){
																		detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem" >'+times1+'~'+times2+'</div>';
																		break;
																	}
																}
															}
														}
													}else{//当天一个都没选
														if("" != times1 && ""!=times2 && "00:00" != times1 && "23:59"!=times2){
															detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem" >'+times1+'~'+times2+'</div>';
														}
													}
												}									
											}else{//销假明细控件无数据、默认全选中
												if(hour2 == timedInt && hour3 >= timedIntAfter){
													if(hour3==timedIntAfter){
														detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem active" >'+dateStartTime+'~'+dateToTime+'</div>';
													}else{
														detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem active" >'+dateStartTime+'~'+timesimpe[m+1]+'</div>';
													}
												}
												if(hour2 < timedInt && timedInt < hour3 && hour3 >= timedIntAfter){
													if(hour3==timedIntAfter){
														detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem active" >'+timesimpe[m]+'~'+dateToTime+'</div>';
													}else{
														detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem active" >'+timesimpe[m]+'~'+timesimpe[m+1]+'</div>';
													}
												}
												if(hour2 < timedInt && timedInt == hour3 && hour3 >= timedIntAfter){
													if(hour3==timedIntAfter){
														detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem active" >'+timesimpe[m]+'~'+dateToTime+'</div>';
													}else{
														detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem active" >'+timesimpe[m]+'~'+timesimpe[m+1]+'</div>';
													}
												}
											}
										}
									}
								}
								detailDayHtml += '</div>';					
							}else{//没有班次，当休息日请假,预留
								
							}
						}
					}			
				}
				return detailDayHtml;					
			},
			
			//初始化按天销假明细表---按明细销假
			initDayDetail:function(){
				var detailDayHtml = "";
				if(!detailDate){
					this.initDetailDate();
				}
				if(!detailDate || !detailDate.success){
					//时长响应为空,或者响应success为false，不初始化
					return detailDayHtml;
				}		
				var record = detailDate.result.form_data_list[0]["extend_value"];
				if(record){
					var detailList = new Array();
					record = eval("("+record+")");
					detailList = record["detailList"];
					var details;
					
					var cancelLevelInfo = $("[name='extendDataFormInfo.value(cancelLevelInfo)']").val();
					for (var i = 0; i < detailList.length; i++) {
						details = detailList[i];
						var workDate = this.getLocalTime(details["workDate"],"date");
						var week = this.getWeek(workDate);
						if(details["isRest"]){//休息日--移动端未见UI，预留着，暂不展示
							
						}else{//工作日							
							var cancelLevelInfo_sz = cancelLevelInfo.split(";");
						
							if(cancelLevelInfo && cancelLevelInfo_sz.length>0){
								if(cancelLevelInfo_sz.indexOf(workDate) > -1){
									for (var a = 0; a < cancelLevelInfo_sz.length; a++) {
										var flag = this.compareTwoTimes(cancelLevelInfo_sz[a], workDate);
										if(flag){//相等-被选中
											detailDayHtml += '<label class="muiDestroyLeaveCheckbox"><input type="checkbox" checked="checked" class="initDayCancel selectedCheckBox" id="mobile_1" name="mobileItem"><label for="mobile_1"></label>';
											detailDayHtml += '<span class="day_span">'+workDate+'  '+week+'</span><span class="timesEveryDay">1天</span></label>';
											detailDayHtml += '<div class="muisDestroyLeaveTimeSelector"></div>';										
										}
									}
								}else{//不被选中
									detailDayHtml += '<label class="muiDestroyLeaveCheckbox"><input type="checkbox" class="initDayCancel" id="mobile_1" name="mobileItem"><label for="mobile_1"></label>';
									detailDayHtml += '<span class="day_span">'+workDate+'  '+week+'</span><span class="timesEveryDay">1天</span></label>';
									detailDayHtml += '<div class="muisDestroyLeaveTimeSelector"></div>';
								}
								
							}else{//销假明细为空，则默认全选中
								detailDayHtml += '<label class="muiDestroyLeaveCheckbox"><input type="checkbox" checked="checked" class="initDayCancel selectedCheckBox" id="mobile_1" name="mobileItem"><label for="mobile_1"></label>';
								detailDayHtml += '<span class="day_span">'+workDate+'  '+week+'</span><span class="timesEveryDay">1天</span></label>';
								detailDayHtml += '<div class="muisDestroyLeaveTimeSelector"></div>';
							}
						}
					}			
				}
				return detailDayHtml;
			},
			//初始化按半天销假明细表---按明细销假
			initHalfDayDetail:function(){
				var detailDayHtml = "";
				if(!detailDate){
					this.initDetailDate();
				}
				if(!detailDate || !detailDate.success){
					//时长响应为空,或者响应success为false，不初始化
					return detailDayHtml;
				}		
				var record = detailDate.result.form_data_list[0]["extend_value"];
				var halfDayFromTime = fromTimes.substring(0,10);
				var halfDayToTimes = toTimes.substring(0,10);
				var allHalfDay=new Array();
				allHalfDay[0]="上午";
				allHalfDay[1]="下午";
				if(record){
					var detailList = new Array();
					record = eval("("+record+")");
					detailList = record["detailList"];
					var details;
					var cancelLevelInfo = $("[name='extendDataFormInfo.value(cancelLevelInfo)']").val();
					for (var i = 0; i < detailList.length; i++) {
						details = detailList[i];
						var workDate = this.getLocalTime(details["workDate"],"date");
						var week = this.getWeek(workDate);
						if(details["isRest"]){//休息日--移动端未见UI，预留着，暂不展示
							
						}else{//工作日
							detailDayHtml += '<label class="muiDestroyLeaveCheckbox"><input type="checkbox" id="mobile_1" class="selectedCheckBox" checked="checked" name="mobileItem"><label for="mobile_1"></label>';
							detailDayHtml += '<span class="day_span">'+workDate+'  '+week+'</span><span class="timesEveryDay">1天</span></label>';
							detailDayHtml += '<div class="muisDestroyLeaveTimeSelector">';
							var cancelLevelInfo_sz = cancelLevelInfo.split(";");
							if(cancelLevelInfo && cancelLevelInfo_sz.length>0){
								if(cancelLevelInfo.indexOf(workDate) > -1){//有，被选中
									var Startflag = this.compareTwoTimes(halfDayFromTime, workDate);
									var endflag = this.compareTwoTimes(halfDayToTimes, workDate);
									for (var s = 0; s < cancelLevelInfo_sz.length; s++) {
										var day1 = cancelLevelInfo_sz[s];
										var day_sz = day1.split(" ");
										var dayflag = this.compareTwoTimes(day_sz[0], workDate);
										if(dayflag){
											for (var w = 0; w < allHalfDay.length; w++) {
												if(allHalfDay[w]!=day_sz[1]){
													detailDayHtml += ' <div class="muisDestroyLeaveTimeSelectorItem">'+allHalfDay[w]+'</div>';
												}else{
													detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem active">'+allHalfDay[w]+'</div>';
												}							
											}
										}
									}									
								}else{//没有，不选中									
									detailDayHtml += this.initHalfDaySelectedALL(halfDayFromTime,halfDayToTimes,workDate,"");
								}								
							}else{//全选中
								detailDayHtml += this.initHalfDaySelectedALL(halfDayFromTime,halfDayToTimes,workDate,"active");		
							}
							detailDayHtml += '</div>';	
						}
					}			
				}
				return detailDayHtml;
			},
			
			//销假明细默认为空，点击全选中场景
			initHalfDaySelectedALL:function(halfDayFromTime,halfDayToTimes,workDate,classNames){
				var detailDayHtml="";
				//未选，全部选中				
				var Startflag = this.compareTwoTimes(halfDayFromTime, workDate);
				var endflag = this.compareTwoTimes(halfDayToTimes, workDate);
				if(Startflag){//首天
					if("AM"==from_half_day_str){
						detailDayHtml += ' <div class="muisDestroyLeaveTimeSelectorItem '+classNames+'">上午</div>';
						detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem '+classNames+'">下午</div>';
					}else{
						detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem '+classNames+'">下午</div>';
					}
				}else if(endflag){//尾天
					if("PM"==to_half_day_str){
						detailDayHtml += ' <div class="muisDestroyLeaveTimeSelectorItem '+classNames+'">上午</div>';
						detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem '+classNames+'">下午</div>';
					}else{
						detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem '+classNames+'">上午</div>';
					}
				}else{
					detailDayHtml += ' <div class="muisDestroyLeaveTimeSelectorItem '+classNames+'">上午</div>';
					detailDayHtml += '<div class="muisDestroyLeaveTimeSelectorItem '+classNames+'">下午</div>';	
				}								
				return detailDayHtml;
			},
			
			//日期格式化（按flag返回格式）
			getLocalTime:function(nS,flag) { 
				var date = new Date(parseInt(nS)).format("yyyy-MM-dd HH:mm");
				if(date){
					if("date"==flag){
						date=date.substring(0,10);
					}else if("time"==flag){
						date=date.substring(11,16);
					}else{
						return date;  
					}
				}
				return date;  
			},
			
			//获取一个日期是星期几
			getWeek:function(dateStr) {
				var date = new Date(dateStr);
			    var week;
			    if(date.getDay() == 0) week = "周日"
			    if(date.getDay() == 1) week = "周一"
			    if(date.getDay() == 2) week = "周二"
			    if(date.getDay() == 3) week = "周三"
			    if(date.getDay() == 4) week = "周四"
			    if(date.getDay() == 5) week = "周五"
			    if(date.getDay() == 6) week = "周六"
			    return week;
			},
			
			//初始化明细表之前请求预计算时长
			initDetailDate:function(){
				if(!levelCode){
					levelCode=$("input[name*='extendDataFormInfo.value(level_form)']").val();
				}
				// 假期类型详情
				if(!levelCode){
					Tip.tip({text:"请选择请假单"});
					return false;
				}
				$("[name='extendDataFormInfo.value(leaveCode)']").val(levelCode);
				var requestUrl = "/third/ding/thirdDingAttendance.do?method=preCalculate&startTime="+fromTimes+"&finishTime="+toTimes+"&leaveCode="+levelCode;
				var params = {};
				requestUrl = util.formatUrl(requestUrl, params);				
				this.Ding_RequestCalcuateDuration(requestUrl);
				return detailDate;
			},
			// 监听【请假类型】的值变更事件
			listenLeaveCodeChange : function(){
				var _self = this;
				topic.subscribe(this.SELECT_CALLBACK, function(triggerWidget){
					if(triggerWidget.name === "extendDataFormInfo.value(level_form)"){
						_self.selectLevelForm();					
					}
					if(triggerWidget.name === "extendDataFormInfo.value(cancelFromDay)"){
						_self.cancelFromDayChange();				
					}
					if(triggerWidget.name === "extendDataFormInfo.value(canceToDay)"){
						_self.cancelToDayChange();					
					}
				});
			},
			cancelFromDayChange:function(){
				var cancelStartTime1 = $("input[name*='cancelStartTime']").val();//销假开始时间
				var cancelFromDay1 = $("input[name*='cancelFromDay']").val();//销假开始时间段
				var flag = this.checkHalfDay(cancelStartTime1,cancelFromDay1);
				if(!flag){
					$("input[name*='cancelFromDay']").val('');
				}
				this.checkValues();
			},
			cancelToDayChange:function(){
				var cancelEndTime1 = $("input[name*='cancelEndTime']").val();//销假开始时间
				var canceToDay1 = $("input[name*='canceToDay']").val();//销假开始时间段
				var flag = this.checkHalfDay(cancelEndTime1,canceToDay1);
				if(!flag){
					$("input[name*='canceToDay']").val('');
				}
				this.checkValues();
			},
			//比较时间段-上午下午（半天制）
			checkHalfDay : function(time,day){
				var levelStartTime1 = fromTimes;//请假开始时间
				var levelEndTime1 = toTimes;//请假结束时间
				if(!time){
					Tip.tip({text:"请先选择日期时间"});
					return false;
				}
				if('day'===dw || 'halfDay'===dw){
					levelStartTime1 = fromTimes.substring(0,10);
					levelEndTime1 = toTimes.substring(0,10);
				}
				//先和请假开始时间比
				var date1 = new Date(levelStartTime1);
				var date2 = new Date(time);
				if(date1.getTime()-date2.getTime()==0){//选择的时间等于开始时间，比较选择的时间段和开始时间段
					//如果请假开始时间段是上午，那么选择销假时间段就可以随便选，因为销假时间和请假开始时间一样
					//如果请假开始时间段是下午，那么在销假时间和请假开始时间一样的情况下，销假时间段只能选择下午（上午被拦住）
					if("PM" == from_half_day_str && "AM" == day){
						Tip.tip({text:"请重新选择时间段"});
						return false;
					}
				}
				date1  = new Date(levelEndTime1);
				if(date1.getTime()-date2.getTime()==0){//选择的时间等于结束时间，比较选择的时间段和结束时间段
					if("AM" == to_half_day_str && "PM" == day){
						Tip.tip({text:"请重新选择时间段"});
						return false;
					}
				}
				return true;
			},
			// 监听【销假开始时间】的值改变事件
			listenFromToTimeChange : function(){
				var _self = this;
				topic.subscribe(this.DATE_VALUE_CHANGE, function(triggerWidget){
					if(triggerWidget.valueField === "extendDataFormInfo.value(cancelStartTime)"){
						_self.formTimeChange();					
					}
					if(triggerWidget.valueField === "extendDataFormInfo.value(cancelEndTime)"){
						_self.toTimeChange();					
					}
				});				
			},
			
			formTimeChange : function(){
				var selectTime = $("input[name*='cancelStartTime']").val();
				var flag = this.checkTimes(selectTime);
				if(!flag){
					$("input[name*='cancelStartTime']").val('');
					Tip.tip({text:"销假开始时间必须在请假时间范围内，请重新选择。"});
				}
				this.checkValues();
			},
			toTimeChange:function(){
				var selectTime = $("input[name*='cancelEndTime']").val();
				var flag = this.checkTimes(selectTime);
				if(!flag){
					$("input[name*='cancelEndTime']").val('');
					Tip.tip({text:"销假结束时间必须在请假时间范围内，请重新选择。"});
				}
				this.checkValues();
			},
			
			checkValues:function(){
				if(this.Ding_IsAllDateSet()){
					var requestUrl = this.getUrl();
					this.Ding_RequestCalcuateDuration(requestUrl);
				}
			},
			//请求时长-获取url
			getUrl :function(){
				// 假期类型详情
				if(!levelCode){
					Tip.tip({text:"请选择请假单"});
					return false;
				}
				$("#leaveCode").val(levelCode);
				var params = {};
				var ding_starttime = this.Ding_GetStartTime();
				var ding_endtime = this.Ding_GetEndTime();
				//判断请求时长的结束时间是否小于开始时间
				var time1 = ding_starttime;
				var time2 = ding_endtime;
				if('halfDay' === dw || 'day' === dw){
					time1 = time1.substring(0,10);
					time2 = time2.substring(0,10);
				}
				if('day' === dw || 'hour' ===dw){
					var dateTime1 = new Date(time1).getTime();
					var dateTime2 = new Date(time2).getTime();
					if(dateTime2-dateTime2<0){
						Tip.tip({text:"销假结束时间不得早于销假开始时间"});
						return;
					}
				}
				if('halfDay' === dw){
					
				}
				params["startTime"] = ding_starttime;
				params["finishTime"] = ding_endtime;
				params["leaveCode"] = levelCode;
				var requestUrl = "/third/ding/thirdDingAttendance.do?method=preCalculate&startTime="+ding_starttime+"&finishTime="+ding_endtime+"&leaveCode="+levelCode;
				return util.formatUrl(requestUrl, params);
			},
			
			//请求计算时长
			Ding_RequestCalcuateDuration:function (requestUrl){
				$.ajax({
					url : requestUrl,
					dataType : "json",
					type : "GET",
					async:false, 
			    	success: function(data){
			    		if(data && data.success){
			    			this.detailDate = data;
			    			var duration = 0;//销假时长
			    			var record = data.result.form_data_list[0]["extend_value"];
			    			record = eval("("+record+")");
			    			try{
			    				if(dw === "hour"){
			    					duration = parseFloat(record["durationInHour"]);
			    				}else{
			    					duration = parseFloat(record["durationInDay"]);
			    				}
			    			}catch(e){
			    				duration = record["durationInHour"];
			    				console.log("can't parse " + record["durationInDay"] +" to float!");
			    			}
			    			if(!durationTime){//请假单的时长为0或为空
			    				Tip.tip({text:"未获取到请假单的请假时长，请刷新后再试"});
			    				return false;
			    			}else{
			    				var levelsTime = parseFloat(durationTime);
			    				var cancelTime = parseFloat(duration);
			    				var dwStr = "小时";
			    				if('day'=== dw || 'halfDay' === dw){
			    					dwStr = "天";
			    					$("[name='extendDataFormInfo.value(duration)']").val(cancelTime+""+dwStr);
				    				$("[name='extendDataFormInfo.value(haveLeaveTime)']").val((levelsTime-cancelTime)+""+dwStr);
			    				}else{
			    					//按时间销假
				    				$("[name='extendDataFormInfo.value(duration)']").val(cancelTime+""+dwStr);
				    				$("[name='extendDataFormInfo.value(haveLeaveTime)']").val((levelsTime-cancelTime)+""+dwStr);
			    				}
			    			}
			    		}else{
			    			console.log(data.errmsg);
			    		}
			    	}
				});	
			},
			Ding_GetStartTime:function(){
				var rs = "";
				var $fromTime = $("input[name*='cancelStartTime']");
				rs = $fromTime.val();
				if("halfDay"===unit){
					rs += " " + $("input[name*='cancelFromDay']").val();
				}
				return rs;
			},
			Ding_GetEndTime:function(){
				var rs = "";
				var $toTime = $("input[name*='cancelEndTime']");
				rs = $toTime.val();
				if("halfDay"===unit){
					rs += " " + $("input[name*='canceToDay']").val();
				}
				return rs;
			},
			//判断是否所有的日期都已经设置值
			Ding_IsAllDateSet : function (){
				var $fromTime = $("input[name*='cancelStartTime']");
				var $toTime = $("input[name*='cancelEndTime']");
				// 开始时间和结束事件是否已经设置值
				if($fromTime.val() && $toTime.val()){
					// 【单位】是否设置为半天，是则校验
					if("halfDay"===unit){
						if($("input[name*='cancelFromDay']").val() && $("input[name*='canceToDay']").val()){
							return true;
						}
					}else{
						return true;
					}
				}
				return false;
			},
			
			
			checkTimes : function(selectTime){
				var fromTimes1 =fromTimes;
				var toTimes1 = toTimes;
				if('day'===dw || 'halfDay'===dw){
					fromTimes1 = fromTimes.substring(0,10);
					toTimes1 = toTimes.substring(0,10);
				}
				if(selectTime){
					var flag1 = this.compareTimes(selectTime, fromTimes1,false);
					var flag2 = this.compareTimes(selectTime, toTimes1,true);
					
					if(flag1 || !flag2){
						return false;
					}
				}
				return true;
			},
			compareTwoTimes : function(startDate, endDate) {   
				 var date1 = new Date(startDate).getTime();
				 var date2 = new Date(endDate).getTime();
				 if(date1-date2==0){
					 return true;  //相等
				 }else{
					 return false;  
				 }
			},
			
			//判断日期，时间大小  
			compareTimes : function(startDate, endDate,flag) {   
				 var date1 = new Date(startDate).getTime();
				 var date2 = new Date(endDate).getTime();
				 if(!flag){
					 if(date1-date2>=0){
						 return false;  //startDate>=endDate
					 }else{
						 return true;  
					 }
				 }else{
					 if(date1-date2>0){
						 return false;  //startDate>endDate
					 }else{
						 return true;  
					 }
				 }
			},
			
			to_time_dw : function (times ,dw,str){
				if('day'===dw){
					return times.substring(0,10);
				}
				if('halfDay'===dw){
					return times.substring(0,10)+" "+str;
				}
				return times;
			},
			
			//隐藏展示时间两行
			noShowTimeTr:function(flag){				
				 var cancelEndTimeHidden = $("input[name*='extendDataFormInfo.value(cancelEndTime)']");
				 var cancelEndTimetr =  $(cancelEndTimeHidden).parent('.oldMui').parents("td").parent("tr");
				 
				 var cancelStartTimeHidden = $("input[name*='extendDataFormInfo.value(cancelStartTime)']");
				 var cancelStartTimetr =  $(cancelStartTimeHidden).parent('.oldMui').parents("td").parent("tr");
				 if(flag){//展示
					 $(cancelEndTimetr).css("display","table-row");
					 $(cancelStartTimetr).css("display","table-row");
				 }else{
					 $(cancelEndTimetr).css("display","none");
					 $(cancelStartTimetr).css("display","none");
				 }				 
			},
			//隐藏展示时间两行--移动端
			noShowTimeTrView:function(flag){				
				var cancelEndTimeHidden = $("[data-dojo-props*='extendDataFormInfo.value(cancelEndTime)']");
				var cancelEndTimetr =  $(cancelEndTimeHidden).parents('.muiFormMultiControlView').parent("tr");
				
				var cancelStartTimeHidden = $("[data-dojo-props*='extendDataFormInfo.value(cancelStartTime)']");
				var cancelStartTimetr =  $(cancelStartTimeHidden).parents('.muiFormMultiControlView').parent("tr");
				if(flag){//展示
					$(cancelEndTimetr).css("display","table-row");
					$(cancelStartTimetr).css("display","table-row");
				}else{
					$(cancelEndTimetr).css("display","none");
					$(cancelStartTimetr).css("display","none");
				}				 
			},
			//隐藏移动端的卡片行
			noShowMobile : function(flag){
				//请假开始时间-移动端
				var level_start_time_input = $("input[name*='extendDataFormInfo.value(level_start_time)']");
				var level_start_time_tr = $(level_start_time_input).parent('.oldMui').parents('td').parent('tr');
				
				//请假结束时间-移动端
				var level_end_time_input = $("input[name*='extendDataFormInfo.value(level_end_time)']");
				var level_end_time_tr = $(level_end_time_input).parent('.oldMui').parents('td').parent('tr');
				
				//请假时长-移动端
				var level_time_input = $("input[name*='extendDataFormInfo.value(level_time)']");
				var level_time_tr = $(level_time_input).parent('.oldMui').parents('td').parent('tr');
				
				//请假编号
				var level_no_input = $("input[name*='extendDataFormInfo.value(level_no)']");
				var level_no_tr = $(level_no_input).parent('.oldMui').parents('td').parent('tr');
				
				//销假明细--默认隐藏，点击按明细销假才展开
				this.noShowCancelDetailTr(false);
								
				if(flag){
					$(level_start_time_tr).css("display","table-row");
					$(level_end_time_tr).css("display","table-row");
					$(level_time_tr).css("display","table-row");		
					$(level_no_tr).css("display","table-row");
				}else{
					$(level_start_time_tr).css("display","none");
					$(level_end_time_tr).css("display","none");
					$(level_time_tr).css("display","none");		
					$(level_no_tr).css("display","none");
				}
			},
			noShowCancelDetailTr:function(flag){
				//销假明细--默认隐藏，点击按明细销假才展开
				var cancelLevelInfoInput = $("input[name*='extendDataFormInfo.value(cancelLevelInfo)']");
				var cancelLevelInfotr = $(cancelLevelInfoInput).parent('.oldMui').parents('td').parent('tr');
				if(flag){
					$(cancelLevelInfotr).css("display","table-row");
				}else{
					$(cancelLevelInfotr).css("display","none");
				}
			},
			noShowCancelDetailTrView:function(flag){
				//销假明细--默认隐藏，点击按明细销假才展开
				var cancelLevelInfoInput = $("[data-dojo-props*='extendDataFormInfo.value(cancelLevelInfo)']");
				var cancelLevelInfotr = $(cancelLevelInfoInput).parents('td').parent('tr');
				if(flag){
					$(cancelLevelInfotr).css("display","table-row");
				}else{
					$(cancelLevelInfotr).css("display","none");
				}
			},
			//清空时间两行的值
			hideTimeTr : function(){
				//清空时间两行的值
				var cancelEndTime = $("input[name*='cancelEndTime']");
				var cancelStartTime = $("input[name*='cancelStartTime']");
				$(cancelStartTime).parent(".oldMui").find(".muiSelInput").text('');
				$(cancelEndTime).parent(".oldMui").find(".muiSelInput").text('');
				
				var cancelFromDay = $("input[name*='extendDataFormInfo.value(cancelFromDay)']");
				var canceToDay = $("input[name*='extendDataFormInfo.value(canceToDay)']");
				$(cancelFromDay).parent(".oldMui").find(".muiSelInput").text('');
				$(canceToDay).parent(".oldMui").find(".muiSelInput").text('');
			},
			
			getClassInfoJson:function(detailList){
				var dateStr ="";
				for (var i = 0; i < detailList.length; i++) {
					if(!detailList[i] || !detailList[i]["workDate"]){
						continue;
					}
					var workDate = this.getLocalTime(detailList[i]["workDate"],"date");
					if(workDate){
						if(!dateStr){
							dateStr += workDate;
						}else{
							dateStr += ";"+workDate;
						}
					}
				}		
				var pram ={};
				pram.dateList=dateStr;
				var url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=getUserAttendenceClassInfo&dateList="+dateStr;
				$.ajax({
			         url: url,
			         type: 'GET',
			         dataType: 'json',
			         async:false,
			         error : function(data) {
			        	 Tip.tip({text:"初始化异常"});
			         },
					 success: function(data) {
						 classInfo = data;		
					 }
			     });
			},
			
			getClassInfo:function(workDate){		
				if(classInfo){//一天的
					var classInfoJson = classInfo[workDate];
					if(!classInfoJson){
						return null;//没有排班信息，算休息日
					}
					var sections = classInfoJson["result"]["sections"];
					if(sections && sections.length>0){
						var classJson =[];
						for (var i = 0; i < sections.length; i++) {
							if(sections[i]["rests"] && sections[i]["rests"].length>0){//有休息时间
								var punches = sections[i]["punches"];//排班
								var rests = sections[i]["rests"];//休息
								if(punches && punches.length>0){
									for (var w = 0; w < punches.length; w++) {
										var classPunches ={};
										if("OnDuty" == punches[w]["check_type"]){
											var check_time = punches[w]["check_time"];
											classPunches["startTime"]=check_time.substring(check_time.length-8,check_time.length-3);
											for (var n = 0; n < rests.length; n++) {
												if("OnDuty" == rests[n]["check_type"]){
													var rests_time = rests[n]["check_time"];
													classPunches["endTime"]=rests_time.substring(rests_time.length-8,rests_time.length-3);
												}
											}
											classJson.push(classPunches);
										}
										if("OffDuty" == punches[w]["check_type"]){
											for (var s = 0; s < rests.length; s++) {
												if("OffDuty" == rests[s]["check_type"]){
													var rests_time1 = rests[s]["check_time"];
													classPunches["startTime"]=rests_time1.substring(rests_time1.length-8,rests_time1.length-3);
												}
											}									
											var check_time = punches[w]["check_time"];
											classPunches["endTime"]=check_time.substring(check_time.length-8,check_time.length-3);
											classJson.push(classPunches);
										}	
									}
								}
							}else{//没有休息时间
								var classPunches ={};
								var punches = sections[i]["punches"];//排班
								if(punches && punches.length>0){
									for (var w = 0; w < punches.length; w++) {
										if("OnDuty" == punches[w]["check_type"]){
											var check_time = punches[w]["check_time"];
											classPunches["startTime"]=check_time.substring(check_time.length-8,check_time.length-3);
										}
										if("OffDuty" == punches[w]["check_type"]){
											var check_time = punches[w]["check_time"];
											classPunches["endTime"]=check_time.substring(check_time.length-8,check_time.length-3);
										}							
									}
									classJson.push(classPunches);
								}
							}
						}
						return classJson;
					}
				}
				return null ;
			},
			//时间戳转年月日时分秒
			to_time : function(times,dw){
				if(!times||!dw){
					return "";
				}
				var time = new Date(times);
				let year = time.getFullYear();
				const month = (time.getMonth() + 1).toString().padStart(2, '0');
				const date = (time.getDate()).toString().padStart(2, '0');
				const hours = (time.getHours()).toString().padStart(2, '0');
				const minute = (time.getMinutes()).toString().padStart(2, '0');
				const second = (time.getSeconds()).toString().padStart(2, '0');
				if("day"===dw || "halfDay"===dw){
					return year + '-' + month + '-' + date;
				}
				if("hour" == dw){
					return  hours + ':' + minute;
				}
				return year + '-' + month + '-' + date + ' ' + hours + ':' + minute + ':' + second;
			},
			
			//获取两个时间中的按1小时间隔时间列表
			getEveryHours :function(startTime ,endtime) { 
				var dateList = new Array();
				var startHour =  startTime.split(":"); 
				var endHour =  endtime.split(":"); 
				
				var hour1 = parseInt(startHour[0]);//8
				var hour2 = parseInt(endHour[0]);//17
				var num = 0;
				for (var i = hour1; i <= hour2; i++) {
					if(i < hour2){
						dateList[num]=i+":"+startHour[1];
					}
					if(i == hour2){
						dateList[num]=i+":"+endHour[1];
					}
					num++;
				}	
			    return dateList
			},
			ding_Init_View : function(){
				var cancelType =  $("[name*='extendDataFormInfo.value(cancelType)']");
				var type="all";
				for (var iw = 0; iw < cancelType.length; iw++) {
					if($(cancelType[iw]).is(":checked")){
						cancelType=$(cancelType[iw]).val();
					}
				}
				if("time"!=cancelType){
					//隐藏
					this.noShowTimeTrView(false);
				}
				if("detail"!=cancelType){
					//隐藏
					this.noShowCancelDetailTrView(false);
				}
				if("edit"==methodStatus || "view"==methodStatus){
					//展示信息卡片
					var cardInfoStr = $("[name*='extendDataFormInfo.value(cardInfo)']").val();
					var cardInfo = JSON.parse(cardInfoStr);
					//初始化全局变量--start
					unitStr= cardInfo["unitStr"];
					durationTime = cardInfo["durationTime"];
					dw = cardInfo["dw"];
					fromTimes = cardInfo["fromTimes"];
					toTimes = cardInfo["toTimes"];
					levelCode = cardInfo["levelCode"] ;
					from_half_day_str = cardInfo["from_half_day_str"];
					to_half_day_str = cardInfo["to_half_day_str"];
					unit = cardInfo["unit"];	
					classInfo = cardInfo["classInfo"];
					detailDate = cardInfo["detailDate"];
					cardNo = cardInfo["cardNo"];
					//初始化全局变量--end
					var cancelFormName = cardInfo["selectFormName"];
					if("view"==methodStatus){
						$(".view_level_form_span").text(cancelFormName);
						$(".view_level_form").css("display","block");
					}			
					$("[data-dojo-props*='extendDataFormInfo.value(level_start_time)']").find(".muiInput_View").text(cardInfo["startTime"]);
					$("[data-dojo-props*='extendDataFormInfo.value(level_end_time)']").find(".muiInput_View").text(cardInfo["toTime"]);
					$("[data-dojo-props*='extendDataFormInfo.value(level_time)']").find(".muiInput_View").text(cardInfo["durations"]);
					$("[data-dojo-props*='extendDataFormInfo.value(level_no)']").find(".muiInput_View").text(cardInfo["cardNo"]);
					
					if("detail"==cancelType){
						if("edit"==methodStatus){
							this.noShowCancelDetailTrView(true);
						}	
						
					}
				}
				
			}
			
		});
	return Service;
})