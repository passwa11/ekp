Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
Com_IncludeFile("validatorUtil.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
define(["dojo/_base/declare",
		"mui/tabbar/TabBarButton",
		"dojo/dom-class",
		"mui/device/adapter",
		"dojo/_base/lang",
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
		"mui/dialog/Dialog",
		"dojo/dom-attr",
		"mui/dialog/Tip"
],function(declare,TabBarButton,domClass,adapter,lang, query, xformUtil, commonUtil, domStyle, validatorUtil,msg, topic, util, request, array, domConstruct,right, registry,Dialog,domAttr,Tip){

	// 业务逻辑duan
		var Service = declare("third.ding.third_ding_xform.builtin.batchCancel.mobile.service", null , {
			// 下拉框的窗口关闭事件
            SELECT_CALLBACK : 'mui/form/select/callback',
            // 地址本值改变的时候
            ADDRESS_CALLBACK : '/mui/form/valueChanged',
            // 日期控件的值改变事件
            DATE_VALUE_CHANGE: "/mui/form/datetime/change",
            // init 初始方法，ff 方法定义， show 显示界面， save_hideInput_xxx 隐藏的input ,  save_showInput_xxx 保存显示的input
            // netWork_cancelLeaveExpire  网络请求 ， cancelLeaveExpire_successCallback 成功，cancelLeaveExpire_failCallback 失败
			doInit : function(){
			    this.init_urlInfo();
				var status = this.methodStatus;
				this.init_common();
                switch(status){
                	case "add":
                	case "edit":
                		this.init_edit(status);
                		break;
                	case "view":
                		this.init_view(status);
                		break;
                }
			},
			init_common : function(){
			     // 1 初始化方法
			     this.init_ff();
			     // 2 隐藏对应不需要显示的数据
			     this.mySetTimeout(this.hide_cancelRadio,100);// 隐藏销假选择中的  “部分销假 ”
			     this.mySetTimeout(this.hide_cancelDetail,100);// 隐藏销假选择中的  “部分销假 ” 对应的  销假明细
			     this.init_html_selectLeaveFormItem_detailView();
			},
            init_view : function(status){
                 // 选择请假单 赋值
                 $("[id='_xform_extendDataFormInfo.value(fd_leave_form)']").before($("[name='extendDataFormInfo.value(fd_form_name)']").val());
                 this.show_selectLeaveFormItem_detailView();
                 mySetTimeout(this.init_view_submit(),100);
            },
            init_view_submit(){
                 var length = Com_Parameter.event.submit.length;
                 this.netWork_cancelLeaveExpire(this.cancelLeaveExpire_successCallback,this.cancelLeaveExpire_failCallback);
                 Com_Parameter.event.submit[length]=function (){
                     var operation = lbpm.operationButtonType=='handler_pass' || lbpm.operationButtonType=='handler_pass';
                     if(operation){
                        if(!window.cancel){
                            Tip.tip({text:"批量销假接口cancel异常"});
                        }
                        if(window.cancel =='nopass'){
                            Tip.tip({text:"已超出最晚销假时间，操作无效"});
                            return false;
                        }
                     }
                     return true;

                 };
            },
			init_edit : function(status){
                 this.init_edit_data();// 初始化数据
                 this.listenValueChange(); // 监听 选择请假单  的值
                 this.show_selectLeaveFormTip();
			},
			init_edit_data : function(){   // 初始化数据
			     this.netWork_thirdDingAttendance(window.thirdDingAttendance_successCallback, window.thirdDingAttendance_failCallback);
            },
            init_html_selectLeaveFormItem_detailView : function(){
                 var trObj = $("[id='_xform_extendDataFormInfo.value(fd_leave_form)']").closest("tr");
                 $("<tr id='selectLeaveFormItem_detailView_tr' style='display:none'><td colspan='2'><div style='overflow:scroll' id= 'selectLeaveFormItem_detailView_div'><div/></td></tr>").insertAfter(trObj);
                 $("<tr id= 'selectLeaveFormTip_tr' style='display:none'><td colspan='2'><div style='font-size:1rem;' id= 'selectLeaveFormTip_div'><div/></td></tr>").insertAfter(trObj);
            },
			init_ff: function(){// 初始化方法
			     window.addDateTime=this.addDateTime;
			     window.getRequest=this.getRequest;
			     window.mySetTimeout=this.mySetTimeout;
                 window.cancelLeaveExpire_successCallback=this.cancelLeaveExpire_successCallback; 
                 window.cancelLeaveExpire_failCallback=this.cancelLeaveExpire_failCallback;
			     window.netWork_thirdDingAttendance=this.netWork_thirdDingAttendance;
			     window.thirdDingAttendance_successCallback=this.thirdDingAttendance_successCallback;
			     window.thirdDingAttendance_failCallback=this.thirdDingAttendance_failCallback;
			     window.timespaceToTime=this.timespaceToTime;
			     window.getFdSelectFormObj = this.getFdSelectFormObj;
			     window.save_selectLeaveFormHideInput_fdAllForm = this.save_selectLeaveFormHideInput_fdAllForm;
            	 window.save_selectLeaveFormHideInput_many = this.save_selectLeaveFormHideInput_many;
                 window.show_selectLeaveForm_optionList = this.show_selectLeaveForm_optionList;
			     window.show_selectLeaveForm_viewInput = this.show_selectLeaveForm_viewInput;
			     window.show_selectLeaveFormItem_detailView = this.show_selectLeaveFormItem_detailView;
			     window.show_selectLeaveFormItem_detailView_pc = this.show_selectLeaveFormItem_detailView_pc;
			     window.show_selectLeaveFormTip = this.show_selectLeaveFormTip;
			     window.drow_table = this.drow_table;
			     window.getCancelPersonId = this.getCancelPersonId;
			},
            hide_cancelRadio : function (){
                 // view,edit 隐藏销假选择中的  “部分销假 ”
                var labelList = $("[id='_xform_extendDataFormInfo.value(fd_cancel_type)']").find("label.muiRadioItem");
                if(labelList && labelList.length>1) labelList[1].remove();
            },
            hide_cancelDetail : function (){
                 // view,edit 全部销假-----------隐藏销假明细
                 $(".detailTableContent").closest("tr").remove();
            },
            //显示请假单到期提示
            show_selectLeaveFormTip(){
                  var fd_select_form_str = $("[name='extendDataFormInfo.value(fd_select_form)']").val();
                  if(fd_select_form_str && fd_select_form_str.length>0){
                      var fd_select_form = JSON.parse(fd_select_form_str);
                      $("#selectLeaveFormTip_tr").show();
                      var showDateTime = window.addDateTime(fd_select_form.leaveEndTime,30);
                      var tipText = "请在"+showDateTime+"前完成该请假单的销假审批，否则销假无效。"
                      $("#selectLeaveFormTip_div").html(tipText);
                  }else {
                      $("#selectLeaveFormTip_tr").hide();
                      $("#selectLeaveFormTip_div").html("");
                  }

            },
            get_dataListByPc:function(){

            },
            show_selectLeaveFormItem_detailView_pc : function (str){
                 var trList = $(str);
                 if(trList && trList.length>0){
	                  var dataList =new Array();
	                  for(var i=0;i < trList.length;i++){
		                     var tr = trList[i];
                             var tdList = $(tr).find("td");
		                     var leaveType = tdList[0].innerText;
		                     var startDate = tdList[1].innerText;
		                     var endDate = tdList[2].innerText;
		                     var often = tdList[3].innerText;
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
            },
            show_selectLeaveFormItem_detailView : function(){
                // 选择具体的某一次请假单  展示当前的请假明细
                var fd_table_all_tr_str = $("[name='extendDataFormInfo.value(fd_table_all_tr)']").val();
                if(fd_table_all_tr_str && fd_table_all_tr_str.length > 0){
                    try{
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

                    }catch(e){
                         var str = $("[id='_xform_extendDataFormInfo.value(fd_table_all_tr)']").children().attr("data-dojo-props");

                         str = str.substring(str.indexOf("/tr")+4,str.lastIndexOf("/tr")+4);
                         str = str.replace(/\\/g,'')
                         window.show_selectLeaveFormItem_detailView_pc(str);
                    }

                }else{
                   $("#selectLeaveFormItem_detailView_div").html("");//清空显示
                   $("#selectLeaveFormItem_detailView_tr").hide();//清空显示
                }
            },
            drow_table : function(dataList){
                 if(!dataList || dataList.length ==0)return;
                 $("#selectLeaveFormItem_detailView_div").html(""); //每次先清空
                 var table = $("<table  cellSpacing=0 cellPadding=0 style='font-size:10px;text-align: center;border-collapse:collapse;' ><tbody>");
                 table.appendTo($("#selectLeaveFormItem_detailView_div")); //在id是createtable的后边加table
                 var tbody = $("#selectLeaveFormItem_detailView_div tbody");
                 var data = dataList[i];


                  var tr_head = $("<tr></tr>");
                  tr_head.appendTo(tbody);
                  var td1_head = $("<td style='width:100px'>请假类型</td>");
                  var td2_head = $("<td style='width:200px'>请假开始时间</td>");
                  var td3_head = $("<td style='width:200px'>请假结束时间</td>");
                  var td4_head = $("<td style='width:150px'>请假时常</td>");
                  td1_head.appendTo(tr_head);
                  td2_head.appendTo(tr_head);
                  td3_head.appendTo(tr_head);
                  td4_head.appendTo(tr_head);

                 for (var i = 0; i < dataList.length; i++) {
                     var data = dataList[i];
                     var tr = $("<tr></tr>");
                     tr.appendTo(tbody);
                     var td1 = $("<td style='width:100px'>" + data.leaveType+ "</td>");
                     var td2 = $("<td style='width:200px'>" + data.startDate+ "</td>");
                     var td3 = $("<td style='width:200px'>" + data.endDate+ "</td>");
                     var td4 = $("<td style='width:150px'>" + data.often+ "</td>");
                     td1.appendTo(tr);
                     td2.appendTo(tr);
                     td3.appendTo(tr);
                     td4.appendTo(tr);
                 }
                 $("#selectLeaveFormItem_detailView_div").append("</table>");
            },
            show_selectLeaveForm_viewInput : function(fd_ekp_instance_id){
                var fd_select_form_obj = window.getFdSelectFormObj(fd_ekp_instance_id);
                // 显示销假总时长，剩余请假时长---0）
                if(fd_select_form_obj){
                    if(fd_select_form_obj.batch){
                        $("[name='extendDataFormInfo.value(fd_cancel_sum_time)']").val(fd_select_form_obj.fd_sum_duration);
                    }else {
                        var extend_value_str = fd_select_form_obj.extend_value;
                        var extend_value = JSON.parse(extend_value_str);
                        if(extend_value){
                           if(extend_value.unit){
                               if(extend_value.unit.toLowerCase() == 'halfday' || extend_value.unit.toLowerCase() == 'day'){// 半天
                                  $("[name='extendDataFormInfo.value(fd_cancel_sum_time)']").val(extend_value.durationInDay+"天");
                               }else if(extend_value.unit.toLowerCase() == 'hour'){// 小时
                                  $("[name='extendDataFormInfo.value(fd_cancel_sum_time)']").val(extend_value.durationInHour+"小时");
                               }
                           }
                        }
                    }
                    $("[name='extendDataFormInfo.value(fd_cancel_surplus_time)']").val("0");
                }else{
                    $("[name='extendDataFormInfo.value(fd_cancel_sum_time)']").val("系统自动计算");
                    $("[name='extendDataFormInfo.value(fd_cancel_surplus_time)']").val("系统自动计算");
                }

            },
            show_selectLeaveForm_optionList : function(dataList){
                // 展示某一次请假单的数据明细   下拉列表
                var selectId = $("[id='_xform_extendDataFormInfo.value(fd_leave_form)']").find("[id*='mui_form_Select']")[0].id;
                var tempSelect =   registry.byId(selectId);
                var tempValues=[];
                tempValues.push({'text':"==请选择==",'value':""});
                if(!dataList || dataList.length ==0) {
                  tempSelect.values = tempValues;
                  tempSelect.editValueSet("");
                  return;
                }

                dataList.forEach(function(ele,index,list){
                     var fd_ekp_instance_id = ele.fd_ekp_instance_id
                     var fd_name = ele.fd_name
                     tempValues.push({'text':fd_name,'value':fd_ekp_instance_id});
                });
                tempSelect.values = tempValues;
            },
            // 填充 选择请假单 对应的hide input---------所有请假单 fd_all_form
            save_selectLeaveFormHideInput_fdAllForm : function(dataList){
                  if(dataList && dataList.length>0){
                     $("[name='extendDataFormInfo.value(fd_all_form)']").val(JSON.stringify(dataList));
                  }else $("[name='extendDataFormInfo.value(fd_all_form)']").val("");

            },
            save_selectLeaveFormHideInput_many : function(fd_ekp_instance_id,fd_name){
                // 选中的请假单 fd_select_form ,请假单名称 fd_form_name ,列表行 fd_table_all_tr
                var fd_select_form_obj = window.getFdSelectFormObj(fd_ekp_instance_id);
                if(fd_ekp_instance_id && fd_name && fd_select_form_obj){
                     $("[name='extendDataFormInfo.value(fd_select_form)']").val(JSON.stringify(fd_select_form_obj));
                     $("[name='extendDataFormInfo.value(fd_form_name)']").val(fd_name);
                     $("[name='extendDataFormInfo.value(fd_table_all_tr)']").val(JSON.stringify(fd_select_form_obj));
                     return ;
                }
                // 清空处理
                $("[name='extendDataFormInfo.value(fd_select_form)']").val("");
                $("[name='extendDataFormInfo.value(fd_form_name)']").val("");
                $("[name='extendDataFormInfo.value(fd_table_all_tr)']").val("");



            },

             // 监听
            listenValueChange : function(){
                var _self = this;
                topic.subscribe(this.SELECT_CALLBACK, function(triggerWidget,obj){
				          var value = triggerWidget.value;
                          var text = triggerWidget.text;
                      //选择请假单值改变的时候
				      if(triggerWidget && triggerWidget.valueField == 'extendDataFormInfo.value(fd_leave_form)'){
                       	  // 显示销假总时长，剩余请假时长---0）
                       	  window.show_selectLeaveForm_viewInput(value);
                       	  // 选中的请假单 fd_select_form ,请假单名称 fd_form_name ,列表行 fd_table_all_tr
                       	  window.save_selectLeaveFormHideInput_many(value,text);
                       	  // 显示销假总时长，剩余请假时长---0）
                       	  window.show_selectLeaveForm_viewInput(value);
                       	  // 选择具体的某一次请假单  展示当前的请假明细
                       	  window.show_selectLeaveFormItem_detailView(value);
                       	  window.show_selectLeaveFormTip();// 根据实际情况显示补卡提示
				      }
				});
				topic.subscribe(this.ADDRESS_CALLBACK, function(triggerWidget,obj){
				       var value = triggerWidget.value;
                       var text = triggerWidget.text;
				       // 销假人更改的时候
				      if(triggerWidget && triggerWidget.idField == 'extendDataFormInfo.value(fd_cancel_user.id)'){
                	      // 2  销假人---对应值改变的时候
                	      // 2.1  重新展示对应列表
                	      if(value){
                	          mySetTimeout(function(){
                	             window.netWork_thirdDingAttendance(window.thirdDingAttendance_successCallback, window.thirdDingAttendance_failCallback);
                	          },100)

                	      }else {
                	         // 重新显示对应的viewinput
                             window.save_selectLeaveFormHideInput_fdAllForm();
                             window.save_selectLeaveFormHideInput_many();
                             // 选择具体的某一次请假单  展示当前的请假明细
                             window.show_selectLeaveFormItem_detailView();
                             window.show_selectLeaveForm_optionList();
                             window.show_selectLeaveFormTip();// 根据实际情况显示补卡提示
                             mySetTimeout(window.show_selectLeaveForm_viewInput(),100);
                	      }

				      }

                });
            },
            getCancelPersonId : function (){
                 var cancelPersonId = $("[name='extendDataFormInfo.value(fd_cancel_user.id)']").val();
                 return cancelPersonId;
            },
			getRequest : function (urlStr, params, successCallback, failCallback) {
			   $.ajax({
              	   url : urlStr,
              	   dataType : "json",
              	   type : "GET",
              	   data:params,
              	   async:true,
              	   jsonp:"jsonpcallback",
              	   success: function(data){
              	      successCallback(data)
              	      return data;
              	   },error : function(data,e) {
              	   	  if (failCallback != null) {
                          failCallback(e);
                      }
                      return null;
              	   }
               });
			},
			netWork_cancelLeaveExpire : function(successCallback,failCallback){
			    var params = {};
                var fd_select_formStr = $("[name='extendDataFormInfo.value(fd_select_form)']").val();
                var fd_select_form = JSON.parse(fd_select_formStr);
                var fd_ekp_instance_id = fd_select_form.fd_ekp_instance_id
                var urlStr = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=cancelLeaveExpire&fd_ekp_instance_id="+fd_ekp_instance_id;
                return window.getRequest(urlStr,params, successCallback, failCallback)
			},
			cancelLeaveExpire_successCallback : function(result){
                  if(result && result.errcode == 0 && result.cancel){
                        window.cancel = "pass";
                  }else {
                        window.cancel = "nopass";
                  }
            },
            cancelLeaveExpire_failCallback : function(result){

            },
			//  根据当前用户获取 选择请假单  列表数据
			netWork_thirdDingAttendance : function(successCallback,failCallback){
                var params = {};
                var cancelPersonId = this.getCancelPersonId();
                var urlStr = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=cancelLeave&ekpUserId="+cancelPersonId;
                return window.getRequest(urlStr,params, successCallback, failCallback)
			},

			thirdDingAttendance_successCallback : function(dataList){
				 if(dataList && dataList.length == 0){
					Tip.tip({text:"当前销假人无可销假的数据"});
					return;
				 }
				 window.save_selectLeaveFormHideInput_fdAllForm(dataList);
				 // batch？批量请假：单个请假
			     window.show_selectLeaveForm_optionList(dataList);


			},

			thirdDingAttendance_failCallback : function(data){

			},
			mySetTimeout : function(ff,time){
			     setTimeout(
			       ff
			     ,time)
			},
			timespaceToTime : function(times,dww){
            	if(!times||!dww){
            		return "";
            	}
            	var dw = dww.toLowerCase();
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
            		return  year + '-' + month + '-' + date + ' ' + hours + ':' + minute ;
            	}
            	return year + '-' + month + '-' + date + ' ' + hours + ':' + minute + ':' + second;
            },
			getFdSelectFormObj : function(fd_ekp_instance_id){
			    var dataListStr = $("[name='extendDataFormInfo.value(fd_all_form)']").val();
			    if(dataListStr && dataListStr.length>0){
                     var dataList = JSON.parse(dataListStr);
                     var fd_select_form_obj = dataList.filter(data => data.fd_ekp_instance_id==fd_ekp_instance_id )[0];
                     return fd_select_form_obj;
			    }else return null;
			},
			init_urlInfo : function(){
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
            addDateTime : function(dateTime,days){
            	console.log("addDateTime in :"+dateTime);
            	var date1 = new Date(dateTime);
            	var date2 = new Date(date1);
            	date2.setDate(date1.getDate() + days);
            	var returnDate = date2.getFullYear() + "-" + (date2.getMonth() + 1) + "-" + date2.getDate();
            	return returnDate;
            }
		});
	return Service;
})