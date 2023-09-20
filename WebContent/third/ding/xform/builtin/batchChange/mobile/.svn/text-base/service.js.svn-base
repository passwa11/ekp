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
        //fd_change_apply_user.id 换班人 ，  fd_change_swap_user_id 替班人，  fd_change_date 换班时间，fd_return_date 还班时间
        // 	fd_change_remark 换班原因
	   // 业务逻辑

		var Service = declare("third.ding.xform.builtin.batchChange.mobile.service", null , {
	        // 下拉框的窗口关闭事件
            SELECT_CALLBACK : 'mui/form/select/callback',
            // 地址本值改变的时候
            ADDRESS_CALLBACK : '/mui/form/valueChanged',
            // 日期控件的值改变事件
            DATE_VALUE_CHANGE: "/mui/form/datetime/change",

//            tip1 : "还班日期必须晚于换班日期";
//            tip2 : "换班记录重复，请重新选择";
//            tip3 : "还班记录重复，请重新选择";
//            tip4 : "换班异常，请重新选择";
//            tip5 : "换班人和还班人相同时，还班日期不能为空";
			doInit : function(){
				 this.init_urlInfo();
                 var status = this.methodStatus;
                 this.init_common(status);
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
			init_common : function(status){
			   _self = this;
			   tip2 = "换班时间有重复,请重新选择";
               tip3 = "还班时间有重复,请重新选择";
               this.init_ff();
               if(status == "view" || status == "edit")
               this.mySetTimeout(this.show_tipDivContent,1000);
            },
            init_view : function(status){


            },

            init_edit : function(status){
                 this.listenValueChange();
                 validatorUtil.addBatchChangeValidation('validateBatchChange',"");
                 // 这里异步原因：线上加载属性不同
                 if(status=="add"){
                     this.mySetTimeout(function(){
                        window.detail_fd_change_table_delRow(document.querySelector("tr[kmss_iscontentrow='1']"))
                        window.detail_fd_change_table_addRow();
                     },50)

                 }
            },
            init_ff: function(){// 初始化方法
            	window.getRequest=this.getRequest;
            	window.mySetTimeout=this.mySetTimeout;
            	window.getTrInputValue=this.getTrInputValue;

                window.validateElement = this.validateElement;
                window.clear_trError = this.clear_trError;
                window.clear_trError_xformflagId = this.clear_trError_xformflagId;
                window.validate_tr = this.validate_tr;
                window.validate_1 = this.validate_1;
                window.validate_2 = this.validate_2;
                window.validate_3 = this.validate_3;
                window.validate_4 = this.validate_4;
                window.save_hideInput_xxx = this.save_hideInput_xxx;
                window.clear_hideInput_xxx = this.clear_hideInput_xxx;
                window.add_tipDiv = this.add_tipDiv;
                window.clear_tipDivContent = this.clear_tipDivContent;
                window.show_tipDivContent = this.show_tipDivContent;
                window.excute_validate = this.excute_validate;
                window.getValueByName=this.getValueByName;
                window.get_trRequireDatas=this.get_trRequireDatas;
                window.validateAndexcuteValidateAsAllTr = this.validateAndexcuteValidateAsAllTr;
                window.netWork_thirdDingAttendance_canRelieveCheck=this.netWork_thirdDingAttendance_canRelieveCheck;
                window.netWork_canRelieveCheck_params = this.netWork_canRelieveCheck_params;
                window.thirdDingAttendance_successCallback = this.thirdDingAttendance_successCallback;
                window.thirdDingAttendance_failCallback = this.thirdDingAttendance_failCallback;
                window.thirdDingAttendance_completeCallback = this.thirdDingAttendance_completeCallback;
            },
            listenValueChange : function(){
                var self = this;
                // 地址本
                topic.subscribe(this.ADDRESS_CALLBACK, function(triggerWidget,obj){
				       // change_extend_value 换班人 ，  fd_change_swap_user.id 替班人
				       window.mySetTimeout(function(){
                               if(triggerWidget){
                                    var value = triggerWidget.value;
                                    var inputId = triggerWidget.idField;
                                    var xformflagId = triggerWidget.id;// 组件id
                                    var valueField = triggerWidget.valueField
                                    if(xformflagId && inputId && inputId.indexOf("fd_change_apply_user") > 0){
                                        // 换班人
                                        //var window.getTrInputValue(41,change_extend_value);
                                        var tr_table = $("[name='"+inputId+"']").closest("table");
                                        var myObj = window.get_trRequireDatas(tr_table,inputId.substring(41,42));
                                        myObj.tr_table = tr_table;
                                        myObj.xformflagId = xformflagId;
                                        window.clear_trError(tr_table);// 清除 error数据
                                        window.excute_validate(tr_table);// 去除该行已经显示的error
                                        window.clear_hideInput_xxx(tr_table);//
                                        window.clear_tipDivContent(tr_table);//
                                        var result = window.validate_tr(xformflagId,tr_table); // 非接口的数据校验
                                        if(result && myObj.exist){
                                            window.netWork_thirdDingAttendance_canRelieveCheck(myObj);
                                        }else {
                                             window.excute_validate(tr_table);// 执行校验
                                             window.validateAndexcuteValidateAsAllTr();
                                        }
                                    }
                                    if(inputId && inputId.indexOf("fd_change_swap_user") > 0){
                                        // 替班人
                                        var tr_table = $("[name='"+inputId+"']").closest("table");
                                        var myObj = window.get_trRequireDatas(tr_table,inputId.substring(41,42));
                                        myObj.tr_table = tr_table;
                                        myObj.xformflagId = xformflagId;
                                        window.clear_trError(tr_table);// 清除 error数据
                                        window.excute_validate(tr_table);// 去除该行已经显示的error
                                        window.clear_hideInput_xxx(tr_table);//
                                        window.clear_tipDivContent(tr_table);//
                                        var result = window.validate_tr(myObj.xformflagId,tr_table);
                                        if(result && myObj.exist){
                                            window.netWork_thirdDingAttendance_canRelieveCheck(myObj);
                                        }else {
                                            window.excute_validate(tr_table);// 执行校验
                                            window.validateAndexcuteValidateAsAllTr();
                                        }
                                    }

                               }
				       },100);
				       if(obj.eventType && obj.eventType == "detailsTable-addRow"){//新增行
				             window.add_tipDiv($(obj.row));
                       		 self.addRow_ObjValidate($(obj.row));
                       }
                       if(obj.eventType && obj.eventType == "detailsTable-delRow"){//删除行
                       		 window.validateAndexcuteValidateAsAllTr();
                       }

                });
                topic.subscribe(this.DATE_VALUE_CHANGE, function(triggerWidget){
                    window.mySetTimeout(function(){
                            if(triggerWidget){
                                var value = triggerWidget.value;
                                var idField = triggerWidget.idField;
                                var xformflagId = triggerWidget.id;// 组件id
                                var valueField = triggerWidget.valueField;
                                if(valueField && valueField.indexOf("fd_change_date") > 0){
                                    var tr_table = $("[name='"+valueField+"']").closest("table");
                                    var myObj = window.get_trRequireDatas(tr_table,valueField.substring(41,42));
                                    myObj.tr_table = tr_table;
                                    myObj.xformflagId = xformflagId;
                                    window.clear_trError(tr_table);// 清除 error数据
                                    window.excute_validate(tr_table);// 去除该行已经显示的error
                                    window.clear_hideInput_xxx(tr_table);
                                    window.clear_tipDivContent(tr_table);
                                    var result = window.validate_tr(myObj.xformflagId,tr_table);
                                    if(result && myObj.exist){
                                        window.netWork_thirdDingAttendance_canRelieveCheck(myObj);
                                    }else {
                                        window.excute_validate(tr_table);// 执行校验
                                        window.validateAndexcuteValidateAsAllTr();
                                    }
                                }
                                if(valueField && valueField.indexOf("fd_return_date") > 0){
                                    var tr_table = $("[name='"+valueField+"']").closest("table");
                                    var myObj = window.get_trRequireDatas(tr_table,valueField.substring(41,42));
                                    myObj.tr_table = tr_table;
                                    myObj.xformflagId = xformflagId;
                                    window.clear_trError(tr_table);// 清除 error数据
                                    window.excute_validate(tr_table);// 去除该行已经显示的error
                                    window.clear_hideInput_xxx(tr_table);
                                    window.clear_tipDivContent(tr_table);
                                    var result = window.validate_tr(myObj.xformflagId,tr_table);
                                    if(result && myObj.exist){
                                        window.netWork_thirdDingAttendance_canRelieveCheck(myObj);
                                    }else {
                                        window.excute_validate(tr_table);// 执行校验
                                        window.validateAndexcuteValidateAsAllTr();
                                    }
                                }
                            }
                    },100);

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
            get_trRequireDatas(tr_table,number){
                 var fd_change_apply_user_id = tr_table.find("[name='extendDataFormInfo.value(fd_change_table."+number+".fd_change_apply_user.id)']");// 换班人
                 var fd_change_swap_user_id = tr_table.find("[name='extendDataFormInfo.value(fd_change_table."+number+".fd_change_swap_user.id)']");// 替班人
                 var fd_change_date = tr_table.find("[name='extendDataFormInfo.value(fd_change_table."+number+".fd_change_date)']");// 换班时间
                 var fd_return_date = tr_table.find("[name='extendDataFormInfo.value(fd_return_date."+number+".fd_return_date)']");// 换班时间
                 var myObj = {"fd_change_apply_user_id":fd_change_apply_user_id.val(),"fd_change_swap_user_id":fd_change_swap_user_id.val(),"fd_change_date":fd_change_date.val(),"fd_return_date":fd_return_date.val()};
                 if(fd_change_apply_user_id && fd_change_swap_user_id && fd_change_date
                 && fd_change_apply_user_id.length > 0 && fd_change_swap_user_id.length > 0 && fd_change_date.length > 0
                 && fd_change_apply_user_id.val().length > 0 && fd_change_swap_user_id.val().length > 0 && fd_change_date.val().length > 0){
                     myObj.exist = true;
                 }else {
                     myObj.exist = false;
                 }
                 return myObj;
            },
            netWork_thirdDingAttendance_canRelieveCheck :function(myObj){
                 var urlStr = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=canRelieveCheck";
                 var parms = window.netWork_canRelieveCheck_params(myObj);
                 window.getRequest(urlStr,parms,myObj,window.thirdDingAttendance_successCallback,window.thirdDingAttendance_failCallback,window.thirdDingAttendance_completeCallback);

            },
            thirdDingAttendance_successCallback : function(result,myObj){
                   // {"errcode":-1,"errmsg":"换班仅支持排班制考勤，你所在考勤组为非排班制考勤组，无法换班","canRelieve":false}
                   if(result){
                     if(result.errcode != '0'){
                        $("[id='"+myObj.xformflagId+"']")[0].setAttribute("error",result.errmsg);// 请假提示数据
                     }else{
                          var tr_table = $("[id='"+myObj.xformflagId+"']").closest("table");
                          window.save_hideInput_xxx(tr_table,result)
                     }
                   }else {
                     Tip.tip({text:"批量换班接口异常result："+result});
                   }

            },
            thirdDingAttendance_completeCallback : function (myObj){
                   window.excute_validate(myObj.tr_table);
                   window.validateAndexcuteValidateAsAllTr();
            },
            clear_trError : function (tr_table){
                 // 清除所有error
                 tr_table.find("[id*='fd_change_apply_user']").children("div [data-dojo-type='mui/form/Address']")[0].setAttribute("error","");
                 tr_table.find("[id*='fd_change_swap_user']").children("div [data-dojo-type='mui/form/Address']")[0].setAttribute("error","");
                 tr_table.find("[id*='fd_change_date']").children("div")[0].setAttribute("error","");
                 tr_table.find("[id*='fd_return_date']").children("div")[0].setAttribute("error","");
            },
            clear_trError_xformflagId : function (tr_table,xformflagId){
                 // 清除xformflagIderror
                 tr_table.find("[id*='"+xformflagId+"']")[0].setAttribute("error","");
            },
            excute_validate : function (tr_table){
                   if(tr_table){
                        window.validateElement(tr_table,"fd_change_apply_user","[data-dojo-type='mui/form/Address']");
                        window.validateElement(tr_table,"fd_change_swap_user","[data-dojo-type='mui/form/Address']");
                        window.validateElement(tr_table,"fd_change_date","");
                        window.validateElement(tr_table,"fd_return_date","");
                   }

            },
             // 针对所有行做一次校验，去除可以去除的 加班重复的提示
             validateAndexcuteValidateAsAllTr : function (){
                  // 获取所有 error = "加班记录重复，请重新选择"  的行
                  $("[error='"+tip2+"']").forEach(function(obj,index){
                       var tr_table = $(obj).closest("table");
                       var pass = window.validate_3(obj.id,tr_table);
                       if(pass){
                           window.clear_trError_xformflagId(tr_table,obj.id);
                           window.excute_validate(tr_table);
                       }
                  })

                   $("[error='"+tip3+"']").forEach(function(obj,index){
                        var tr_table = $(obj).closest("table");
                        var pass = window.validate_4(obj.id,tr_table);
                        if(pass){
                            window.clear_trError_xformflagId(tr_table,obj.id);
                            window.excute_validate(tr_table);
                        }
                   })
                  // 循环，然后 validate_tr，然后判断这些行哪些没有 error ="true"  再执行 excute_validate

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
            validate_tr : function(xformflagId,tr_table){
	                var v1 = validate_1(xformflagId,tr_table);
	                var v2 = validate_1(xformflagId,tr_table);
	                if(v1 && v2){
		                var v3 = validate_3(xformflagId,tr_table);
		                var v4 = validate_4(xformflagId,tr_table);
		                if(v3 && v4){
			                 return true;
		                }
	                }
                    return false;
                        
            },

            validate_1 : function(xformflagId,tr_table){
                //换班人和还班人相同时，还班日期不能为空
                var fd_change_date_div = tr_table.find("[name*='fd_change_date']").parent();// 换班时间
                var fd_return_date_div = tr_table.find("[name*='fd_return_date']").parent();// 还班时间
                var fd_change_apply_user_id = window.getValueByName(tr_table,"fd_change_apply_user.id");//换班人id
                var fd_change_swap_user_id = window.getValueByName(tr_table,"fd_change_swap_user.id");//替班人id
                var fd_change_date = window.getValueByName(tr_table,"fd_change_date");//换班日期-yyyy-mm-dd
                var fd_return_date = window.getValueByName(tr_table,"fd_return_date");//还班日期
                if(fd_change_apply_user_id && fd_change_swap_user_id
                   && fd_change_apply_user_id == fd_change_swap_user_id
                   && fd_return_date.length == 0) {
                      fd_return_date_div[0].setAttribute("error","换班人和还班人相同时，还班日期不能为空");
                      return false;
                }
                return true;
            },
            validate_2 : function(xformflagId,tr_table){
                //还班日期必须晚于换班日期
                var fd_change_date_div = tr_table.find("[name*='fd_change_date']").parent();// 换班时间
                var fd_return_date_div = tr_table.find("[name*='fd_return_date']").parent();// 还班时间
                var fd_change_date = tr_table.find("[name*='fd_change_date']").val();// 换班时间
                var fd_return_date = tr_table.find("[name*='fd_return_date']").val();// 还班时间
                if(fd_change_date && fd_return_date && fd_change_date.length>0 && fd_return_date.length>0){
                     if(new Date(fd_change_date).getTime() >= new Date(fd_return_date).getTime())  {
                         var nowDivId = $("[id='"+xformflagId+"']")[0].id;
                         if(fd_change_date_div[0].id == xformflagId || fd_return_date_div[0].id == xformflagId ){
	                          // 如果此时选中的控件时换班时间或者还班时间则，直接把error 添加再上面
                              $("[id='"+xformflagId+"']")[0].setAttribute("error","还班日期必须晚于换班日期");
                         }else {
	                         fd_change_date_div[0].setAttribute("error","还班日期必须晚于换班日期");
                         }
                       
                         return false;
                     }
                }
                return true;
            },
            validate_3 : function(xformflagId,tr_table){
                 // 换班时间重复
                 var fd_change_date_div = tr_table.find("[name*='fd_change_date']").parent();// 换班时间
                var fd_return_date_div = tr_table.find("[name*='fd_return_date']").parent();// 还班时间
                var fd_change_apply_user_id = window.getValueByName(tr_table,"fd_change_apply_user.id");//换班人id
                var fd_change_swap_user_id = window.getValueByName(tr_table,"fd_change_swap_user.id");//替班人id
                var fd_change_date = window.getValueByName(tr_table,"fd_change_date");//换班日期-yyyy-mm-dd

                var combination_1 = fd_change_apply_user_id + "_" + fd_change_date;// 换班人 + 换班日期
                var combination_2 = fd_change_swap_user_id + "_" + fd_change_date;// 还班人 + 换班日期
                var existErrorNumber_1 = 0;
                var existErrorNumber_2 = 0;
                var existError_1 = "";
                var existError_2 = "";
                if( fd_change_apply_user_id && fd_change_date ){
                    $("tr[kmss_iscontentrow='1']").forEach(function (obj,index){
                        var temp_fd_change_apply_user_id = window.getValueByName($(obj),"fd_change_apply_user.id");//换班人id
                        var temp_fd_change_date = window.getValueByName($(obj),"fd_change_date");//换班日期-yyyy-mm-dd
                        var temp_combination_1 = temp_fd_change_apply_user_id + "_" + temp_fd_change_date;
                        if(combination_1 == temp_combination_1)  {
                            existErrorNumber_1++;
                            if(existErrorNumber_1 >=2 ){
                                existError_1 = "换班时间重复";
                            }
                        }
                    });
                }
                if( fd_change_swap_user_id && fd_change_date ){
                    $("tr[kmss_iscontentrow='1']").forEach(function (obj,index){
                       var temp_fd_change_swap_user_id = window.getValueByName($(obj),"fd_change_swap_user.id");//还班人id
                       var temp_fd_change_date = window.getValueByName($(obj),"fd_change_date");//还班日期
                        var temp_combination_2 = temp_fd_change_swap_user_id + "_" + temp_fd_change_date;
                        if(combination_2 == temp_combination_2)  {
                            existErrorNumber_2++;
                            if(existErrorNumber_2 >=2 ){
                                existError_2 = "换班时间重复";
                            }
                        }
                    });
                }
                if(existError_1.length>0 || existError_2.length>0){
                   fd_change_date_div[0].setAttribute("error","换班时间有重复,请重新选择");
                    return false;
                }
                return true;

            },
            validate_4 : function(xformflagId,tr_table){
                 // 还班时间重复
                var fd_change_date_div = tr_table.find("[name*='fd_change_date']").parent();// 换班时间
                var fd_return_date_div = tr_table.find("[name*='fd_return_date']").parent();// 还班时间
                var fd_change_apply_user_id = window.getValueByName(tr_table,"fd_change_apply_user.id");//换班人id
                var fd_change_swap_user_id = window.getValueByName(tr_table,"fd_change_swap_user.id");//替班人id
                var fd_return_date = window.getValueByName(tr_table,"fd_return_date");//还班日期

                var combination_1 = fd_change_apply_user_id + "_" + fd_return_date;// 换班人 + 换班日期
                var combination_2 = fd_change_swap_user_id + "_" + fd_return_date;// 还班人 + 换班日期
                var existErrorNumber_1 = 0;
                var existErrorNumber_2 = 0;
                var existError_1 = "";
                var existError_2 = "";
                if( fd_change_apply_user_id && fd_return_date ){
                    // 还班时间重复
                    $("tr[kmss_iscontentrow='1']").forEach(function (obj,index){
                        var temp_fd_change_apply_user_id = window.getValueByName($(obj),"fd_change_apply_user.id");//换班人id
                        var temp_fd_return_date = window.getValueByName($(obj),"fd_return_date");//换班日期-yyyy-mm-dd
                        var temp_combination_1 = temp_fd_change_apply_user_id + "_" + temp_fd_return_date;
                        if(combination_1 == temp_combination_1)  {
                            existErrorNumber_1++;
                            if(existErrorNumber_1 >=2 ){
                                existError_1 = "还班时间重复";
                            }
                        }
                    });
                }

                if( fd_change_swap_user_id && fd_return_date ){

                       // 还班时间重复
                    $("tr[kmss_iscontentrow='1']").forEach(function (obj,index){
                       var temp_fd_change_swap_user_id = window.getValueByName($(obj),"fd_change_swap_user.id");//还班人id
                       var temp_fd_return_date = window.getValueByName($(obj),"fd_return_date");//还班日期
                        var temp_combination_2 = temp_fd_change_swap_user_id + "_" + temp_fd_return_date;
                        if(combination_2 == temp_combination_2)  {
                            existErrorNumber_2++;
                            if(existErrorNumber_2 >=2 ){
                                existError_2 = "还班时间重复";
                            }
                        }
                    });
                }
                if(existError_1.length>0 || existError_2.length>0){
                      fd_return_date_div[0].setAttribute("error","还班时间有重复,请重新选择");
                      return false;
                }
                return true;

            },


            save_hideInput_xxx : function (tr_table,result){
                   var formList = result.result.form_data_list;
            	   if(null != formList && formList.length>0  && null != formList[0]["value"]){
                       tr_table.find("[name*='change_extend_value']").val(formList[0]["extend_value"]);
                       tr_table.find("[name*='change_value']").val(formList[0]["value"]);

                       if(formList[0].value.length > 0){
                           var json1 = JSON.parse(formList[0].value);
                           var relieveInfo = json1.relieveInfo;
                           var backInfo = json1.backInfo;
                           $(tr_table).find("[name*='fd_change_date_div']").html(relieveInfo);
                           $(tr_table).find("[name*='fd_return_date_div']").html(backInfo);
                       }

            	   }
            },
            clear_hideInput_xxx : function (tr_table){
                  tr_table.find("[name*='change_extend_value']").val("");
                  tr_table.find("[name*='change_value']").val("");
            },
            clear_tipDivContent(tr_table){
                $(tr_table).find("[name*='fd_change_date_div']").html("");
                $(tr_table).find("[name*='fd_return_date_div']").html("");
            },
            show_tipDivContent(){
                $("[name*='change_value']").each(function() {
                      var tr_table = $(this).closest("table");
                      window.add_tipDiv(tr_table);
                      if($(this).val()){
                           var json = JSON.parse($(this).val());
                           var relieveInfo = json.relieveInfo;
                           var backInfo = json.backInfo;
                           $(tr_table).find("[name*='fd_change_date_div']").html(relieveInfo);
                           $(tr_table).find("[name*='fd_return_date_div']").html(backInfo);
                      }
                });
            },
            add_tipDiv(tr_table){
                  var fd_change_date = tr_table.find("[id*='fd_change_date']");
                  var fd_return_date = tr_table.find("[id*='fd_return_date']");
                  var number = fd_change_date[0].id.substring(48,49);
                  var fd_change_date_div = fd_change_date.children();// 换班时间
                  var fd_return_date_div = fd_return_date.children();// 还班时间
                  $("<div style='float:right;font-size:8px;color:#b3b1bc' name='fd_change_date_div_"+ number +"'>").insertAfter(fd_change_date_div);
                  $("<div style='float:right;font-size:8px;color:#b3b1bc' name='fd_return_date_div_"+ number +"'>").insertAfter(fd_return_date_div);
            },
            thirdDingAttendance_failCallback : function(result){

            },
            netWork_canRelieveCheck_params(myObj){
                var params = {
                              "applicantStaff_ekpid":window.getValueByName(myObj.tr_table,"fd_change_apply_user.id"),//换班人id
                              "reliefStaff_ekpid":window.getValueByName(myObj.tr_table,"fd_change_swap_user.id"),//替班人id
                              "relieveDatetime":window.getValueByName(myObj.tr_table,"fd_change_date"),//换班日期-yyyy-mm-dd
                              "backDatetime":window.getValueByName(myObj.tr_table,"fd_return_date")//还班日期
                };
                return params;
            },

            // 校验器添加
            addRow_ObjValidate : function(tr_table){
                // 获取input 对象
//                 tr_table.find("[id*='fd_change_apply_user']").children("div [data-dojo-type='mui/form/Address']")[0].id;
//                 tr_table.find("[id*='fd_change_swap_user']").children("div [data-dojo-type='mui/form/Address']")[0].id;
//                 tr_table.find("[id*='fd_change_date']").children("div")[0].id;
//                 tr_table.find("[id*='fd_return_date']").children("div")[0].id;
                var fd_change_apply_user_id = tr_table.find("[name*='fd_change_apply_user.id']");// 换班人
                var fd_change_swap_user_id = tr_table.find("[name*='fd_change_swap_user.id)']");// 替班人
                var fd_change_date = tr_table.find("[name*='fd_change_date']");// 换班时间
                var fd_return_date = tr_table.find("[name*='fd_return_date']");// 还班时间
                // 获取input名称
                var fd_change_apply_user_id_name =fd_change_apply_user_id[0].name;
                var fd_change_swap_user_id_name = fd_change_swap_user_id[0].name;
                var fd_change_date_name = fd_change_date[0].name;
                var fd_return_date_name = fd_return_date[0].name;
                // 获取组件对象 widget
                fd_change_apply_user_id_widget = commonUtil.getWidgetFrom$Form($form(fd_change_apply_user_id_name));
                fd_change_swap_user_id_widget = commonUtil.getWidgetFrom$Form($form(fd_change_swap_user_id_name));
                fd_change_date_widget = commonUtil.getWidgetFrom$Form($form(fd_change_date_name));
                fd_return_date_widget = commonUtil.getWidgetFrom$Form($form(fd_return_date_name));
                // 设置 validate
                fd_change_apply_user_id_widget._set("validate", "validateBatchChange required");
                fd_change_swap_user_id_widget._set("validate", "validateBatchChange required");
                fd_change_date_widget._set("validate", "validateBatchChange required");
                fd_return_date_widget._set("validate", "validateBatchChange");
                // 设置 _validate
                fd_change_apply_user_id_widget._set("_validate", "validateBatchChange required");
                fd_change_swap_user_id_widget._set("_validate", "validateBatchChange required");
                fd_change_date_widget._set("_validate", "validateBatchChange required");
                fd_return_date_widget._set("_validate", "validateBatchChange");


            },



		});
	return Service;
})