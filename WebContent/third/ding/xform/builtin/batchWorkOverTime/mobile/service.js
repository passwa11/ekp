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
          // init 初始方法，ff 方法定义， show 显示界面， save_hideInput_xxx 隐藏的input ,  save_showInput_xxx 保存显示的input
          // netWork_cancelLeaveExpire  网络请求 ， cancelLeaveExpire_successCallback 成功，cancelLeaveExpire_failCallback 失败
          // extendDataFormInfo.value(fd_batch_work_table.0.fd_work_user.id) 加班人
	   // 业务逻辑
		var Service = declare("third.ding.xform.builtin.batchWorkOverTime.mobile.service", null , {
	        // 下拉框的窗口关闭事件
            SELECT_CALLBACK : 'mui/form/select/callback',
            // 地址本值改变的时候
            ADDRESS_CALLBACK : '/mui/form/valueChanged',
            // 日期控件的值改变事件
            DATE_VALUE_CHANGE: "/mui/form/datetime/change",




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
			   _self = this;
			   Tip1 = "加班记录重复,请重新选择";
               this.init_ff();
            },
            init_view : function(status){
                 var i =1;
            },
            init_edit : function(status){
                 this.listenValueChange();
                 validatorUtil.addBatchBatchWorkOverTimeValidation('validateBatchWorkOverTime',"");
                 // 这里异步原因：线上加载属性不同
                 if(status=="add"){
                    this.mySetTimeout(function(){
                       window.detail_fd_batch_work_table_delRow(document.querySelector("tr[kmss_iscontentrow='1']"))
                       window.detail_fd_batch_work_table_addRow();
                    },50)
                 }
            },
            init_ff: function(){// 初始化方法
            	window.getRequest=this.getRequest;
            	window.mySetTimeout=this.mySetTimeout;
            	window.getTrInputValue=this.getTrInputValue;

                window.setExistInfo = this.setExistInfo;
                window.validateElement = this.validateElement;
                window.clear_fd_work_end_time_Error = this.clear_fd_work_end_time_Error;
                window.validate_tr = this.validate_tr;
                window.validate_1 = this.validate_1;
                window.validate_2 = this.validate_2;
                window.validate_3 = this.validate_3;
                window.validate_4 = this.validate_4;
                window.validate_5 = this.validate_5;
                window.save_hideInput_xxx = this.save_hideInput_xxx;
                window.clear_hideInput_xxx = this.clear_hideInput_xxx;
                window.excute_validate = this.excute_validate;
                window.validateAndexcuteValidateAsAllTr = this.validateAndexcuteValidateAsAllTr;
                window.getValueByName=this.getValueByName;
                window.getXformflagByInputName=this.getXformflagByInputName;
                window.get_trRequireDatas=this.get_trRequireDatas;
                window.netWork_getOvertimeDuration=this.netWork_getOvertimeDuration;
                window.netWork_getOvertimeDuration_params = this.netWork_getOvertimeDuration_params;
                window.netWork_getOvertimeDuration_successCallback = this.netWork_getOvertimeDuration_successCallback;
                window.netWork_getOvertimeDuration_failCallback = this.netWork_getOvertimeDuration_failCallback;
                window.netWork_getOvertimeDuration_completeCallback = this.netWork_getOvertimeDuration_completeCallback;
            },
            listenValueChange : function(){
                var self = this;
                // 地址本
                topic.subscribe(this.ADDRESS_CALLBACK, function(triggerWidget,obj){
				       if(triggerWidget){
				            var value = triggerWidget.value;
				            var inputId = triggerWidget.idField;
                            var xformflagId = triggerWidget.id;// 组件id
                            var valueField = triggerWidget.valueField
                            if(xformflagId && inputId && inputId.indexOf("fd_work_user.id") > 0){
                                // 加班人
                                var tr_table = $("[name='"+inputId+"']").closest("table");
                                var myObj = window.get_trRequireDatas(tr_table,inputId.substring(41,42));
                                myObj.tr_table = tr_table;
                                myObj.xformflagId = xformflagId;
                                window.clear_fd_work_end_time_Error(tr_table);// 清除 error数据
                                window.excute_validate(tr_table);// 去除该行已经显示的error
                                var result = window.validate_tr(tr_table); // 非接口的数据校验
                                if(result && myObj.exist){
                                    window.netWork_getOvertimeDuration(myObj);
                                }else {
                                     window.clear_hideInput_xxx(tr_table);
                                     window.excute_validate(tr_table);// 执行校验
                                     window.validateAndexcuteValidateAsAllTr();
                                }
                            }

				       }
				       if(obj.eventType && obj.eventType == "detailsTable-addRow"){//新增行
                       		 self.addRow_ObjValidate($(obj.row));
                       }
				       if(obj.eventType && obj.eventType == "detailsTable-delRow"){//新增行
                       		 window.validateAndexcuteValidateAsAllTr();
                       }


                });
                topic.subscribe(this.DATE_VALUE_CHANGE, function(triggerWidget){
                    if(triggerWidget){
                        var value = triggerWidget.value;
                        var idField = triggerWidget.idField;
                        var xformflagId = triggerWidget.id;// 组件id
                        var valueField = triggerWidget.valueField;
                        if(valueField && valueField.indexOf("fd_work_start_time") > 0){
                            var tr_table = $("[name='"+valueField+"']").closest("table");
                            var myObj = window.get_trRequireDatas(tr_table,valueField.substring(41,42));
                            myObj.tr_table = tr_table;
                            myObj.xformflagId = xformflagId;
                            window.clear_fd_work_end_time_Error(tr_table);// 清除 error数据
                            window.excute_validate(tr_table);// 去除该行已经显示的error
                            var result = window.validate_tr(tr_table);
                            if(result && myObj.exist){
                                window.netWork_getOvertimeDuration(myObj);
                            }else {
                                window.clear_hideInput_xxx(tr_table);
                                window.excute_validate(tr_table);// 执行校验
                                window.validateAndexcuteValidateAsAllTr();
                            }
                        }
                        if(valueField && valueField.indexOf("fd_work_end_time") > 0){
                            var tr_table = $("[name='"+valueField+"']").closest("table");
                            var myObj = window.get_trRequireDatas(tr_table,valueField.substring(41,42));
                            myObj.tr_table = tr_table;
                            myObj.xformflagId = xformflagId;
                            window.clear_fd_work_end_time_Error(tr_table);// 清除 error数据
                            window.excute_validate(tr_table);// 去除该行已经显示的error
                            var result = window.validate_tr(tr_table);
                            if(result && myObj.exist){
                                window.netWork_getOvertimeDuration(myObj);
                            }else {
                                window.clear_hideInput_xxx(tr_table);
                                window.excute_validate(tr_table);// 执行校验
                                window.validateAndexcuteValidateAsAllTr();
                            }
                        }
                    }

                });
            },
            // 公用方法------------start--------------------
            getRequest : function (urlStr, params,myObj ,successCallback, failCallback,completeCallback) {
                 if(!params) parms={};
                 $.ajax({
                 	   url : urlStr,
                 	   dataType : "json",
                 	   type : "GET",
                 	   data:params,
                 	   async:true,
                 	   jsonp:"jsonpcallback",
                 	   success: function(result){
                 	      successCallback(result,myObj)
                 	      return result;
                 	   },error : function(data,e) {
                 	   	  if (failCallback != null) {
                            failCallback(e);
                          }
                        },complete: function(XMLHttpRequest, textStatus) {
                             if(completeCallback){
                                 completeCallback(myObj)
                             }
                        }
                 });
            },
            mySetTimeout : function(ff,time){
            			     setTimeout(
            			       ff
            			     ,time)
            },
            getValueByName :function(tr_table,name){
                 return tr_table.find("[name*='"+name+"']").val();
            },
            getXformflagByInputName :function(tr_table,name){
                 return tr_table.find("[name*='"+name+"']").parent();
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
            getTrInputValue(numberIndex,sourceInputName,simpleInputName){
                 var sourceInput =  $("[name='"+sourceInputName+"']");
                 var closestTable = sourceInput.closest("table");
                 var jqueryInputName = sourceInputName.substring(0,numberIndex+2)+simpleInputName+")";
                 var value = $("[name='"+jqueryInputName+"']").val();
                 return value;
            },
            // 公用方法------------end--------------------
            // 私有业务方法------------start--------------------
            // 获取必填的三个参数
            get_trRequireDatas(tr_table){
                  var curry_fd_work_start_time = window.getValueByName(tr_table,"fd_work_start_time");//加班开始时间
                  var curry_fd_work_end_time = window.getValueByName(tr_table,"fd_work_end_time");//加班结束时间
                  var curry_fd_work_user_id = window.getValueByName(tr_table,"fd_work_user.id");// 加班人
                 var myObj = {"curry_fd_work_start_time":curry_fd_work_start_time,"curry_fd_work_end_time":curry_fd_work_end_time,"curry_fd_work_user_id":curry_fd_work_user_id};
                 if(curry_fd_work_start_time.length >0 && curry_fd_work_end_time.length >0 && curry_fd_work_user_id.length >0 ){
                     myObj.exist = true;
                 }else {
                     myObj.exist = false;
                 }
                 return myObj;
            },
            netWork_getOvertimeDuration :function(myObj){
                 var urlStr = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=getOvertimeDuration";
                 var parms = window.netWork_getOvertimeDuration_params(myObj);
                 window.getRequest(urlStr,parms,myObj,window.netWork_getOvertimeDuration_successCallback,window.netWork_getOvertimeDuration_failCallback,window.netWork_getOvertimeDuration_completeCallback);

            },
            netWork_getOvertimeDuration_successCallback : function(result,myObj){
                   // {"errcode":-1,"errmsg":"换班仅支持排班制考勤，你所在考勤组为非排班制考勤组，无法换班","canRelieve":false}
                   if(result){
                     if(result.errcode != '0'){
                        window.setExistInfo($("[id='"+myObj.xformflagId+"']")[0],result.errmsg)
                     }else{
                          var tr_table = $("[id='"+myObj.xformflagId+"']").closest("table");
                          window.save_hideInput_xxx(tr_table,result)
                     }
                   }else {
                     Tip.tip({text:"批量换班接口异常result："+result});
                   }

            },
             netWork_getOvertimeDuration_failCallback : function(result){

             },
             netWork_getOvertimeDuration_params(myObj){
                 var params = {
                      "ekpUserIds":window.getValueByName(myObj.tr_table,"fd_work_user"),
                      "startTime":window.getValueByName(myObj.tr_table,"fd_work_start_time"),
                      "finishTime":window.getValueByName(myObj.tr_table,"fd_work_end_time"),
                 };
                 return params;
             },
            netWork_getOvertimeDuration_completeCallback : function (myObj){
                   window.excute_validate(myObj.tr_table);
                   window.validateAndexcuteValidateAsAllTr();
            },
            clear_fd_work_end_time_Error : function (tr_table){
                 // 清除所有error
                 window.setExistInfo(tr_table.find("[id*='fd_work_end_time']").children("div")[0],"")
            },
            // 清除多余的行
            clear_moreTr : function (){
                var maxNumber = 20;
                var nowNumber =  $("tr[kmss_iscontentrow='1']").length;
                if(nowNumber > maxNumber){
                     window.detail_fd_batch_work_table_delRow($("tr[kmss_iscontentrow='1']:last"));//  删除最后一行
                }
            },
            // 针对所有行做一次校验，去除可以去除的 加班重复的提示
            validateAndexcuteValidateAsAllTr : function (){
                 // 获取所有 error = "加班记录重复，请重新选择"  的行
                 $("[error='"+Tip1+"']").forEach(function(obj,index){
                      var tr_table = $(obj).closest("table");
                      var pass = window.validate_3(tr_table);
                      if(pass){
                          window.clear_fd_work_end_time_Error(tr_table);
                          window.excute_validate(tr_table);
                      }
                 })
                 // 循环，然后 validate_tr，然后判断这些行哪些没有 error ="true"  再执行 excute_validate

            },
            excute_validate : function (tr_table){
                        window.validateElement(tr_table,"fd_work_end_time","");


            },
            validateElement : function (tr_table,id,children){
                    var obj = tr_table.find("[id*='"+id+"']").children("div"+children);
                    if(obj.length>0){
                       var xformflagId = obj[0].id;
                       if(xformflagId.length > 0 ){
                            var idEle= registry.byId(xformflagId);
                            validatorUtil.getValidation().validateElement(idEle);
                       }
                    }
            },
            validate_tr : function(tr_table){
                    return validate_1(tr_table) && validate_2(tr_table)
                        && validate_3(tr_table) ;

            },
            // 校验加班人数量是否大于50
/*            validate_fd_work_user_number : function(xformflagId,tr_table){
                //换班人和还班人相同时，还班日期不能为空
                var maxPersionNumber = 50;
                var fd_work_user_id = window.getValueByName(tr_table,"fd_work_user.id");
                if(true) {
                      $("[id='"+xformflagId+"']")[0].setAttribute("error","最多可选择50人");
                      return false;
                }
                return true;
            },*/
            validate_1 : function(tr_table){
                // 加班结束时间不能小于加班开始时间
                var fd_work_start_time = window.getValueByName(tr_table,"fd_work_start_time");//加班开始时间
                var fd_work_end_time = window.getValueByName(tr_table,"fd_work_end_time");//加班结束时间
                if(fd_work_start_time && fd_work_end_time
                      && fd_work_start_time.length>0 && fd_work_end_time.length > 0
                      && new Date(fd_work_start_time).getTime() >= new Date(fd_work_end_time).getTime()) {
                      window.getXformflagByInputName(tr_table,"fd_work_end_time")[0].setAttribute("error","加班结束时间不能小于加班开始时间")
                      return false;
                }
                return true;
            },
            validate_2 : function(tr_table){
                var fd_work_start_time = window.getValueByName(tr_table,"fd_work_start_time");//加班开始时间
                var fd_work_end_time = window.getValueByName(tr_table,"fd_work_end_time");//加班结束时间
                if(fd_work_start_time && fd_work_end_time
                      && fd_work_start_time.length > 0 && fd_work_end_time.length > 0
                      && fd_work_start_time.substring(0,10) != fd_work_end_time.substring(0,10)) {
                      window.getXformflagByInputName(tr_table,"fd_work_end_time")[0].setAttribute("error","加班开始时间和加班结束时间需为同一天，跨天可新增明细行");
                      return false;
                }
                return true;
            },
            validate_3 : function(tr_table){
                 // 加班时间重复
                 // 1 获取当前行，判断是否三个字段都填好了如果填好了则接着走
                 var curry_fd_work_start_time = window.getValueByName(tr_table,"fd_work_start_time");//加班开始时间
                 var curry_fd_work_end_time = window.getValueByName(tr_table,"fd_work_end_time");//加班结束时间
                 var curry_fd_work_user_id = window.getValueByName(tr_table,"fd_work_user.id");// 加班人
                 var curry_fd_work_user_id_array = curry_fd_work_user_id.indexOf(";")?curry_fd_work_user_id.split(";"):[curry_fd_work_user_id];
                 var curry_index = parseInt(tr_table.find("[name*='fd_work_user.id']")[0].name.substring(45,46));
                 if(curry_fd_work_start_time.length > 0 && curry_fd_work_end_time.length > 0 && curry_fd_work_user_id.length > 0){
                 var trList = $("tr[kmss_iscontentrow='1']");

                 // 2 判断当前行的加班人集合和其他行的加班人集合是否有重复，如果有，则在对应的下方显示  error,existError =true ，以及  显示对应校验器提示
                 var arrayObj1 = trList.map(function (index,node){
                          var fd_work_user_id = window.getValueByName($(node),"fd_work_user.id");//加班人
                          var fd_work_start_time = window.getValueByName($(node),"fd_work_start_time");//加班开始时间
                          var fd_work_end_time = window.getValueByName($(node),"fd_work_end_time");//加班结束时间
                           var index = parseInt($(node).find("[name*='fd_work_user.id']")[0].name.substring(45,46));
                          return {"index":index,"fd_work_user_id":fd_work_user_id,"fd_work_start_time":fd_work_start_time,"fd_work_end_time":fd_work_end_time};
                 });
                 var arrayObj = [];
                 arrayObj1.forEach(function(obj){
                    arrayObj.push(obj);
                 });
                 var resultArray = arrayObj.filter(
                              function (obj, index, array)  {   //过滤当前的填写行，且 3个字段都填写完成
                                   if(obj.index !=curry_index &&  obj.fd_work_user_id.length>0 && obj.fd_work_start_time.length > 0 && obj.fd_work_end_time.length > 0){
                                       return true;
                                   }else {
                                       return false;
                                   }
                               }
                               ).filter(obj => {// 保留加班人有重复的 行
                                      var tempNumber = curry_fd_work_user_id_array.filter(obj1 => {
                                            return obj.fd_work_user_id.includes(obj1);
                                       }).length;
                                       if(tempNumber > 0){
                                           return true;
                                       }else {
                                           return false;
                                       }
                               }).filter(obj => {  // 保留时间重复的行
                                   var curry_fd_work_start_time_ppp =  new Date(curry_fd_work_start_time).getTime();
                                   var curry_fd_work_end_time_ppp =  new Date(curry_fd_work_end_time).getTime();
                                   var fd_work_start_time_ppp = new Date(obj.fd_work_start_time).getTime();
                                   var fd_work_end_time_ppp = new Date(obj.fd_work_end_time).getTime();

                                   var t1 = curry_fd_work_start_time_ppp == fd_work_start_time_ppp && curry_fd_work_end_time_ppp == fd_work_end_time_ppp;// 完全相同
                                   var t2 = curry_fd_work_start_time_ppp >= fd_work_start_time_ppp && curry_fd_work_end_time_ppp <= fd_work_end_time_ppp;// 第1个包含于第2个
                                   var t3 = curry_fd_work_start_time_ppp < fd_work_start_time_ppp && curry_fd_work_end_time_ppp < fd_work_end_time_ppp && curry_fd_work_end_time_ppp > fd_work_start_time_ppp ;// 左突 有交集
                                   var t4 = curry_fd_work_start_time_ppp > fd_work_start_time_ppp && curry_fd_work_end_time_ppp > fd_work_end_time_ppp && curry_fd_work_start_time_ppp < fd_work_end_time_ppp ;// 右突 有交集
                                   var t5 = curry_fd_work_start_time_ppp <= fd_work_start_time_ppp && curry_fd_work_end_time_ppp >= fd_work_end_time_ppp;// 第2个包含于第1个
                                   if(t1 || t2 || t3 || t4 || t5) {
                                       return true;
                                   }else {
                                       return false;
                                   }
                               });
                     if(resultArray.length >0 ){
                          window.setExistInfo(window.getXformflagByInputName(tr_table,"fd_work_end_time")[0],Tip1)
                          resultArray.forEach(function (obj,index,array){
                                  var i = obj.index;
                                  var xformflag = $("[name='extendDataFormInfo.value(fd_batch_work_table."+ i +".fd_work_end_time)']").parent();
                                  var existError = xformflag[0].getAttribute("existError");
                                  if(!existError || existError == "false"){
                                       window.setExistInfo(xformflag[0],Tip1)
                                  }
                          })
                          return false;
                     }
                 }
                 return true;
                 // 查询所有existError = true 的行，判断是否存在数据重复，如果不存在则撤销提示和 error,existError ???
            },
            setExistInfo : function (div,error){
                  if(!div) return;
                  if(error && error.length >0){
                      div.setAttribute("existError","true");
                      div.setAttribute("error",error);
                  }else {
                      div.setAttribute("existError","true");
                      div.setAttribute("error","");
                  }
            },
            save_hideInput_xxx : function (tr_table,result){
            	   if(result.isCanOvertime){
                       tr_table.find("[name*='fd_duration']").val(result.durationInHour);
                       tr_table.find("[name*='fd_extend_value']").val(result.durationData);
            	   }else{
            	       tr_table.find("[name*='fd_duration']").val("");
                       tr_table.find("[name*='fd_extend_value']").val("");
            	   }
            },
            clear_hideInput_xxx : function (tr_table){
                  tr_table.find("[name*='change_extend_value']").val("");
                  tr_table.find("[name*='change_value']").val("");
            },


            // 校验器添加
            addRow_ObjValidate : function(tr_table){

                var fd_work_end_time = tr_table.find("[name*='fd_work_end_time']");// 换班人
                // 获取input名称
                var fd_work_end_time_name =fd_work_end_time[0].name;
                // 获取组件对象 widget
                fd_work_end_time_name_widget = commonUtil.getWidgetFrom$Form($form(fd_work_end_time_name));
                // 设置 validate
                fd_work_end_time_name_widget._set("validate", "validateBatchWorkOverTime");
                // 设置 _validate
                fd_work_end_time_name_widget._set("_validate", "validateBatchWorkOverTime");



            },



		});
	return Service;
})