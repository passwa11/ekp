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
		"dijit/registry", 
		"mui/dialog/Tip",
		"mui/dialog/Dialog"
],function(declare, query, xformUtil, commonUtil, domStyle, validatorUtil, msg, topic, util, request, array, domConstruct,right,registry ,Tip,Dialog){	
	// 业务逻辑
	var Service = declare("third.ding.xform.builtin.batchLeave.mobile.service", null , {
		// 下拉框的窗口关闭事件
		SELECT_CALLBACK : 'mui/form/select/callback',

		ADDRESS_CALLBACK : '/mui/form/valueChanged',
		// 日期控件的值改变事件
		DATE_VALUE_CHANGE: "/mui/form/datetime/change",
		
		ekpUserid : null,
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
		init_ff: function(){// 初始化方法
        	window.mySetTimeout=this.mySetTimeout;
		    window.addRowValidator=this.addRowValidator;
            window.loadScheduleByDaySuccess=this.loadScheduleByDaySuccess;
            window.saveItemDataToUll=this.saveItemDataToUll;
            window.showItemSchedueData_edit=this.showItemSchedueData_edit;
            window.saveItemDataToInput=this.saveItemDataToInput;
            window.replaceSupply=this.replaceSupply;
            window.chooseSchedule=this.chooseSchedule;// 在排班列表中选择要切换的具体的排班
            window.getItemScheduleData=this.getItemScheduleData;
        	window.getRequest=this.getRequest;
        	window.netWork_canSupply=this.netWork_canSupply;
        	window.canSupply_successCallback=this.canSupply_successCallback;
        	window.canSupply_failCallback=this.canSupply_failCallback;
        	window.canSupply_parms=this.canSupply_parms;
        	window.getDefaultSchedulePunchId=this.getDefaultSchedulePunchId;
        },
		doInit : function(){
			this.getUrlInfo();
			this.init_ff();
			this._export(this);
			var status = this.methodStatus;
			switch(status){
				case "add":
				case "edit":
					this.initInEdit(status);
					break;
				case "view":
					this.initInView();
					break;
			}
			
		},
		initInView : function(){
		var self = this;
		     window.showItemSchedueData_view=this.showItemSchedueData_view;
		    // 1 延迟查询所有的列表
		     setTimeout(function(){
		     var number = $("[kmss_iscontentrow='1']").length;
                  for(var i=0;i<number;i++){
                     var schedules_item_str =  $("[name*='extendDataFormInfo.value(fd_batch_replacement_table."+(i)+".singer_replacement_info)']").val();
                     var itemId = "_xform_extendDataFormInfo.value(fd_batch_replacement_table."+i+".fd_replacement_time)";
                     var divId = $("[id='"+itemId+"']").children()[0].id;
                     self.addRow_ul($("[id="+divId+"]"));
                     showItemSchedueData_view(JSON.parse(schedules_item_str),divId,itemId); //div   mui_datetime__DateTimeMixin_0
                  }
               $("[id='_xform_extendDataFormInfo.value(fd_replacement_count)']").find(".muiInput_View").css({"color":"#888","font-size":"0.1rem"});
		     },500);



			//$("[name*='extendDataFormInfo.value(fd_batch_replacement_table."+(number-1)+".singer_replacement_info)']").val(JSON.stringify(schedules_tr[punchId]));
		    // 获取 singer_replacement_info 数据 （自定义的数据）
		    //2 显示item此时的排班 showItemSchedueData_view(schedules_tr,divId,itemId,punchId);
		
		},
		initInEdit : function(status){		
			//注册事件
			var self = this;
     		self.initEditEvent(status);
			validatorUtil.addBatchReplaceValidation('validateBatchReplace',"当前时间没有考勤异常,无需补卡",this.getItemScheduleData);
			validatorUtil.addBatchCheckNotCanSupplyValidation('validateBatchCheckNotCanSupply',"超出补卡时间或剩余补卡次数无法申请",this.netWork_canSupply);

			Com_Parameter.event.submit[1]=function (){
			     var set1 = new Set();
			     var nowDataList = $("[name='nowData']");
                 if(nowDataList.length>1){
			          for(var i=0;i<nowDataList.length;i++){
				         var oldNumber =set1.size;
				         set1.add(nowDataList[i].innerText);
                          var newNumber =set1.size;
                          if(newNumber==oldNumber){
	                         Tip.tip({text:"补卡班次重复："+nowDataList[i].innerText});
                              return false;
                           }
			          }
	
                 }
			    
			     return true;
			};
			 Com_Parameter.event.submit[2]=function (){
			               var list = $("[name*='fd_replacement_time']");
			               var nowNumber = 0;
            			    list.each( (index,node) =>{
                                 if(new Date(node.value).getMonth() == new Date().getMonth()){
                                      nowNumber++;
                                 }
                            });
            			    if(nowNumber >Window.replacSurpluseNumber) {
            			        Tip.tip({text:"补卡次数不够"});
            			        return false;
            			    }else{
            			        return true;
            			    }

            			};
		},
		// 选择排班
		chooseSchedule : function (divId,itemId,punchId){
			var ull = $("#"+divId).siblings("ul[name*='fd_replacement_ul']")[0];
			var fd_schedule_info =ull.getAttribute("fd_schedule_info");
		    var schedules_tr = JSON.parse(fd_schedule_info);
            var fd_schedule = schedules_tr[punchId];
		    showItemSchedueData_edit(schedules_tr,divId,itemId,punchId);
		    saveItemDataToInput(schedules_tr,divId,itemId,punchId);
            Window.dialog.hide();
            window.netWork_canSupply(divId,itemId);
            //var idEle= registry.byId(divId);
            //validatorUtil.getValidation().validateElement(idEle);
		},
		// 更换补卡 
		replaceSupply : function(divId,itemId,punchId){
			  
				var fd_schedule_info =    $("#"+divId).siblings("[name*='fd_replacement_ul']")[0].getAttribute("fd_schedule_info");
				var fd_schedule_info_obj =  JSON.parse(fd_schedule_info );
				var tempDiv = "";
				Object.getOwnPropertyNames(fd_schedule_info_obj).forEach(function(key){
                         if(punchId && key && punchId != key)
                         tempDiv=tempDiv+"<div style='display: table-row;height:30px; line-height:30px;' onclick=chooseSchedule('"+divId+"','"+itemId+"','"+key+"')>"+fd_schedule_info_obj[key].text+"</div>"
                })
                if(tempDiv.length<1){
	               Tip.tip({text:"没有其他补卡班次"});
                   return false;
                }
				var contentNode = domConstruct.create('div', {
					className : 'muiBackDialogElement',
					innerHTML : tempDiv
				});
			var dialog=	Dialog.element({
					'title' : '请选择补卡班次',
					'showClass' : 'muiBackDialogShow',
					'element' : contentNode,
					'scrollable' : false,
					'parseable': false,
					'width':'100%',
					'buttons' : [ {
						title : "取消",
						fn : function(dialog) {
							dialog.hide();
						}
					}  ]
				});	
				Window.dialog=dialog;
				event.stopPropagation(); 
			   return false;
		    },
		initEditEvent :function(status){			
			_self = this;				
			this.listenBatchReplaceValueChange();
			setTimeout( function(){
			      _self.getCheckInfo();
			      if(status=="add"){
			
			          //删除行
		              window.detail_fd_batch_replacement_table_delRow(document.querySelector("tr[kmss_iscontentrow='1']"))
	                  //新增行
	                  window.detail_fd_batch_replacement_table_addRow();
	               }    
			},100);
			$(document).on('table-add-new','table[id="TABLE_DL_fd_batch_replacement_table"]',function(e,obj){
					     _self.addRowValidator(obj);
				});
		},
		_export:function(v){
			
			
		},
		addRowValidator :function(obj){
			        var curryNumber = query(".detailTableContent .muiDetailTableDel").length;// 当前的次数
					var ekpUserId=$("[name='extendDataFormInfo.value(fd_user.id)']").val();
					if(ekpUserId && Window.replacSurpluseNumber && curryNumber >Window.replacSurpluseNumber){
						//window.detail_fd_batch_replacement_table_delRow($("tr[kmss_iscontentrow='1']:last-child")[0]);
						//Tip.tip({text:"补卡次数不够"});
					}else{
						_self.addRow_ObjValidate(obj);
					}
		},
		ekpUserChange:function(obj){
			if(obj.value && obj.value != this.ekpUserid){
				//1、重新赋值给全局变量ekpUserid
				this.ekpUserId = obj.value;
				
			}
		},
		
		//初始化控件校验--obj为当前行的请假类型
		addRow_ObjValidate : function(obj){
		    window.mySetTimeout(function(){
		         var fdReplacementTimeObjName =$(obj.row).find("[name*='fd_replacement_time)']")[0].name;
                 var fdReplacementTimeObj = $form(fdReplacementTimeObjName);
                 var fdReplacementTimeWidget = commonUtil.getWidgetFrom$Form(fdReplacementTimeObj);
                 fdReplacementTimeWidget._set("validate", "required validateBatchReplace validateBatchCheckNotCanSupply");
                 fdReplacementTimeWidget._set("_validate", "required validateBatchReplace validateBatchCheckNotCanSupply");
                 _self._changeDateBaseAttr(fdReplacementTimeWidget, "dateTime");
                 _self.addRow_ul($(obj.row).find("[id*='mui_datetime__DateTimeMixin']"))
		    },100)

			
		},
		mySetTimeout : function(ff,time){
        			     setTimeout(
        			       ff
        			     ,time)
        },
		addRow_ul : function ($_div){
			var ull = document.createElement("ul"); 
		    ull.setAttribute("name", "fd_replacement_ul");
		    ull.setAttribute("hasdata", "none");
            
	        if($_div.length>0){
	           $_div.after(ull); 
	        }
		},
		
		// 监听
		listenBatchReplaceValueChange : function(){
			var _self = this;
			topic.subscribe(this.ADDRESS_CALLBACK, function(triggerWidget,obj){
				
				if(triggerWidget && "extendDataFormInfo.value(fd_user.id)"==triggerWidget.idField){
					if(obj.oldValue || !obj.value){//删除补卡人id
					    this.ekpUserId="";//删除补卡人
	                    $("[name='extendDataFormInfo.value(fd_replacement_count)']").val("");
						query(".detailTableContent .muiDetailTableDel").
						forEach(function(node, index, arr){
                             node.click();
                        });
					}else if(!obj.oldValue || obj.value){//新添补卡人
					    _self.ekpUserChange(obj);//设置补卡人id
						_self.getCheckInfo();
					}
				}
			});
			topic.subscribe(this.DATE_VALUE_CHANGE, function(triggerWidget){
				 var divId = triggerWidget.id;
				 var itemId = triggerWidget.valueField;
				 var value = triggerWidget.value;
			     // 情况数据
			     var nowDataObj = $("#"+divId).siblings("[name*='fd_replacement_ul']").find("[name*='nowData']");
			     if(nowDataObj.length>0){
			        nowDataObj[0].setAttribute("validatorresult","none");
			     }
                 $("#"+divId).siblings("[name*='fd_replacement_ul']")[0].setAttribute("hasdata","none");//问题1
                 $("#"+divId).siblings("[name*='fd_replacement_ul']")[0].removeAttribute("fd_schedule_info");
                 $("#"+divId).siblings("[name*='fd_replacement_ul']")[0].innerHTML="";
                 window.getItemScheduleData(value,divId,itemId)
             
                 							
			});
			
		},
		 // 保存所有的补班数据到  <ul> , 且设置 <ul> hasdata 为 true 
		 saveItemDataToUll : function(schedules_tr,divId,itemId,punchId){
			 if(schedules_tr){
				var ull=$("#"+divId).siblings("ul[name*='fd_replacement_ul']")[0];
				ull.setAttribute("hasdata", "true");
				var fd_schedule_info = JSON.stringify(schedules_tr);
				ull.setAttribute("fd_schedule_info",fd_schedule_info);//保存行内的组装弹窗所需信息-原全局schedules punchId

	         }
            
		},
		// 显示当前行的排班数据
		showItemSchedueData_edit :function (schedules_tr,divId,itemId,punchId){
		     var schedule = schedules_tr[punchId] ;
			 var ulStr= "<div><div punchId='"+itemId+"_"+punchId+"' name='nowData' style='float:left;font-size:8px;color:#b3b1bc'>"+schedule.text+"</div><div onclick='replaceSupply(\""+divId+"\",\""+itemId+"\",\""+punchId+"\")' style='margin-right:5px;float:right;font-size:8px;color:#1c79b8'>更换补卡</div></div>";
			 var ull=$("#"+divId).siblings("[name*='fd_replacement_ul']")[0];
			 ull.innerHTML=ulStr;   //保存当前行的班次信息
		},
		// 显示当前行的排班数据
		showItemSchedueData_view :function (schedule_item,divId,itemId){
			 var ulStr= "<div><div punchId='"+itemId+"_"+schedule_item.punchId+"' name='nowData' style='float:left;font-size:8px;color:#b3b1bc'>"+schedule_item.text+"</div></div>";
			 var ull=$("#"+divId).siblings("[name*='fd_replacement_ul']")[0];
			 ull.innerHTML=ulStr;   //保存当前行的班次信息
		},
	    saveItemDataToInput :function (schedules_tr,divId,itemId,punchId){
    		 var number = itemId.substring(52,53);
            $("[name*='extendDataFormInfo.value(fd_batch_replacement_table."+number+".punch_check_time)']").val(schedules_tr[punchId].check_date_time);
            $("[name*='extendDataFormInfo.value(fd_batch_replacement_table."+number+".work_date)']").val(schedules_tr[punchId].work_date);
            $("[name*='extendDataFormInfo.value(fd_batch_replacement_table."+number+".punchId)']").val(schedules_tr[punchId].punchId);
            $("[name*='extendDataFormInfo.value(fd_batch_replacement_table."+number+".singer_replacement_info)']").val(JSON.stringify(schedules_tr[punchId]));
    	},

    	 // init 加载排班数据
         loadScheduleByDaySuccess : function(data,divId,itemId) {
	         // 排班时间 punch_check_time   ，班次日期 work_date，班次ID punchId
	        if (data.errcode === 0 && data.success) {
	            var result = data.result;
	            if (result && result.form_data_list.length>0) {
	            	var fd_schedule_info = result.form_data_list[0].value;
                    var number = itemId.substring(52,53);
                    $("[name*='extendDataFormInfo.value(fd_batch_replacement_table."+number+".fd_schedule_info)']").val(fd_schedule_info);
                    var ull=$("#"+divId).siblings("[name*='fd_replacement_ul']")[0];
	            	var schedules_tr = {};
	            	var schedules_array = [];
	                var infoJson = JSON.parse(fd_schedule_info);
                    var tempPunchId = "";
	                for (var i = 0; i < infoJson.length; i++) {
	                	var work = infoJson[i];
	                    var schedule = {};
	                    if(!work.freeCheck){//非休息日
	                    	schedule.punchId = work.planId;
	                    	schedule.check_date_time = work.checkDateTime;
	                    	schedule.checkType = work.checkType;
	                    	schedule.checkStatus = work.checkStatus;
	                    	schedules_tr[schedule.punchId] = schedule;
	                    }else{
	                    	schedule.punchId = work.workDate+"_"+work.supplyDate;
	                    	schedule.check_date_time = work.supplyDate;
	                    }
	                    schedule.planText = work.planText;
	                    schedule.text = work.planTip;
	                    schedule.work_date = work.workDate;
	                    schedules_tr[schedule.punchId] = schedule;
	                    schedules_array.push(schedule);
                        //if(i==0)tempPunchId = schedule.punchId
	                }
	                   var defaultPunchId = window.getDefaultSchedulePunchId(itemId,schedules_array);
                       this.saveItemDataToUll(schedules_tr,divId,itemId,defaultPunchId);
                       this.showItemSchedueData_edit(schedules_tr,divId,itemId,defaultPunchId);
                       this.saveItemDataToInput(schedules_tr,divId,itemId,defaultPunchId);



	            }else{
	            	console.log("thirdDingAttendance.scheduleByDay ---> data.result is null..."+JSON.stringify(data));
	            	//没有需要补卡的
	            	clearTrTimeData(name,"noreplacement");           	
	            }
	        }else{
	        	console.log("thirdDingAttendance.scheduleByDay interface is exception..."+JSON.stringify(data));
	        	//接口请求失败，
	        	clearTrTimeData(name,"nullinfo");   
	        }
	    },
	    // 得到默认排班
	    getDefaultSchedulePunchId(itemId,schedules_array){
            var datetimeStr =  $("[name='"+itemId+"']").val();
            var return_punchId = "";
			var datetime  = (new Date(datetimeStr.replace(/-/g,'/'))).getTime()
            if(datetime && schedules_array && datetimeStr.length>0 && schedules_array.length>0 ){
                   var min = new Date(schedules_array[0].check_date_time).getTime();
                   var max = new Date(schedules_array[schedules_array.length-1].check_date_time).getTime();
                   if(datetime<min)return schedules_array[0].punchId;
                   else return schedules_array[schedules_array.length-1].punchId;
            }
	    },
		
		//根据时间和补卡人获取排班数据
		getItemScheduleData : function(datee,divId,itemId){
			var self =this;
			var url =Com_Parameter.ContextPath+ "third/ding/thirdDingAttendance.do?method=getSupplyDates" ;
            var params = {}; 
            var fd_user_id = $("[name='extendDataFormInfo.value(fd_user.id)']").val();//补卡人id
            if(!fd_user_id){
	            var idEle= registry.byId("mui_form_Address_0");// 这里可能存在风险
	            validatorUtil.getValidation().validateElement(idEle);
				setTimeout( function(){
					 //删除行
		            window.detail_fd_batch_replacement_table_delRow(document.querySelector("tr[kmss_iscontentrow='1']"))
	                //新增行
	                window.detail_fd_batch_replacement_table_addRow();
				},  1 );//延迟5000毫秒
               
                return;
            }
            params["ekpUserId"] = fd_user_id;  
        
            params["date"] = datee;     
            var requestUrl = util.urlResolver(url, params);		   
            $.ajax({
				url : requestUrl,
				dataType : "json",
				type : "GET",
				data:params,
				async:true, 
		    	jsonp:"jsonpcallback",
		    	success: function(data){
			        if(data.errcode!=0){
				        //Tip.tip({text:"补卡次数查询异常"});
			        }else if(null != data.result){		    			
					    var valueJsonStr = data.result.form_data_list[0].value;
		                var array = JSON.parse(valueJsonStr);
                        window.loadScheduleByDaySuccess(data,divId,itemId);
                        window.netWork_canSupply(divId,itemId);

                        return ;               
		    		}else{
		              	Tip.tip({text:"补卡次数查询异常"});
		            }
		    	},error : function(data,e) {
		    		Tip.tip({text:"补卡次数查询异常"});
		       	  	console.log("请假类型初始化异常。url:"+url);
		         }
			});
		},
		//获取补卡人补卡信息异常（剩余补卡次数）
		getCheckInfo : function(){
			var url =Com_Parameter.ContextPath+ "third/ding/thirdDingAttendance.do?method=getCheckInfo" ;
            var params = {};    
            params["ekpUserId"] = this.ekpUserId;        
            var requestUrl = util.urlResolver(url, params);		   
            $.ajax({
				url : requestUrl,
				dataType : "json",
				type : "GET",
				data:params,
		    	jsonp:"jsonpcallback",
		    	success: function(data){
		    		if(data && data.errcode!=undefined && data.result 
                            && data.result.form_data_list 
                            && data.result.form_data_list.length>0
                            && data.result.form_data_list[0].value.length>10 ){	
	                        var value = data.result.form_data_list[0].value;
                            var fd_replacement_count = $("[name='extendDataFormInfo.value(fd_replacement_count)']");
                            fd_replacement_count.val(value);
                            fd_replacement_count.css({"color":"#888","font-size":"0.1rem"});
	                        var number = value.substring(value.length-3,value.length-1);
	                        Window.replacSurpluseNumber=parseInt(number);
                            Tip.tip({text:value}); 
		    		}else{
			            Tip.tip({text:"批量补卡接口异常"}); 
                        
		            }
		    	},error : function(data,e) {
		    		    Tip.tip({text:"批量补卡e异常"}); 
		       	  	    console.error("批量补卡e异常"+e);
		         }
			});
		},
		netWork_canSupply : function(divId,itemId){
                        var params =window.canSupply_parms(divId,itemId);
                        var urlStr = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=canSupply";
                        var myObj = {"divId":divId,"itemId":itemId};
                         window.getRequest(urlStr,params,myObj, window.canSupply_successCallback, window.canSupply_failCallback)
        },
        canSupply_successCallback:function(result,myObj){
                   if(!result){
                      $("#"+myObj.divId).siblings("[name*='fd_replacement_ul']").find("[name*='nowData']")[0].setAttribute("validatorresult","undetermined");//未确定
                   }else if( result.errcode != 0){
                     $("#"+myObj.divId).siblings("[name*='fd_replacement_ul']").find("[name*='nowData']")[0].setAttribute("validatorresult","nopass");
                   }else {
                      $("#"+myObj.divId).siblings("[name*='fd_replacement_ul']").find("[name*='nowData']")[0].setAttribute("validatorresult","pass");
                   }
                   var idEle= registry.byId(myObj.divId);
                   validatorUtil.getValidation().validateElement(idEle);
        },
        canSupply_failCallback:function(){
              Tip.tip({text:"班次补卡信息异常"});
        },
        canSupply_parms:function(divId,itemId){
              var divObj= $("[id='"+divId+"']");
              var itemObj= $("[name='"+itemId+"']");
              var punchIdStr =   divObj.siblings("[name*='fd_replacement_ul']").find("[name*='nowData']")[0].getAttribute("punchid");
			  /*#156712-钉钉补卡套件，手机端上选择补卡日期提示补卡次数不足，无法申请，PC端上申请正常-开始*/
              /*var punchId = punchIdStr.substr(punchIdStr.lastIndexOf("_")+1,punchIdStr.length-1);*/
			  var punchId = punchIdStr.substr(punchIdStr.lastIndexOf(")")+2,punchIdStr.length-1);
			  /*#156712-钉钉补卡套件，手机端上选择补卡日期提示补卡次数不足，无法申请，PC端上申请正常-结束*/
              var number = itemId.substring(52,53);
              var fd_schedule_info = $("[name='extendDataFormInfo.value(fd_batch_replacement_table."+number+".fd_schedule_info)']").val();
              var extendValue = null;
              if(punchId && fd_schedule_info){
              	//休息日
              	var workDate = null ;
              	var supplyDate = null ;
              	var scheduleInfo = JSON.parse(fd_schedule_info);
              	//取extendValue
              	for (var i = 0; i < scheduleInfo.length; i++) {
              		if(punchId.indexOf("_")>0){//如果是休息日组装起来的punchId 则需重新取
              			workDate = punchId.split("_")[0];
              			supplyDate = punchId.split("_")[1];
              			if(workDate == scheduleInfo[i]["workDate"] && supplyDate == scheduleInfo[i]["supplyDate"]){
              				extendValue = scheduleInfo[i];
              			}
              		}else{
              			if(punchId == scheduleInfo[i]["planId"]){
              				extendValue = scheduleInfo[i];
              			}
              		}
              	}
              	var fd_replacement_time = itemObj.val();
              	var fd_user_id = $("[name*='extendDataFormInfo.value(fd_user.id)']").val();
              	if(fd_replacement_time && extendValue){
              	   var parm = {};
                   parm.extendValue = JSON.stringify(extendValue);
                   parm.date = fd_replacement_time;
                   parm.ekpUserId = fd_user_id;
                   return parm;
              	}else return;
             }
        },
		_changeDateBaseAttr : function(type, dateWidget){
			var info = dateWidgetInfo[type];
			for(var attrKey in info){
				dateWidget[attrKey] = info[attrKey];
			}
		},
		getRequest : function (urlStr, params,myObj ,successCallback, failCallback) {
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
                              return null;
                      	   }
                       });
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
