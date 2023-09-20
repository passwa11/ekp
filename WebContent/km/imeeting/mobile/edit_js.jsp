<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	require(["mui/form/ajax-form!kmImeetingMainForm"]);
	require(['dojo/ready','dijit/registry','dojo/query','dojo/dom-style','dojo/dom-class','dojo/_base/lang',
	         'mui/dialog/Tip','dojo/request','mui/util','mui/i18n/i18n!km-imeeting:mobile','dojo/dom-attr',
	         'dojo/ready','dojo/topic'
	         ],
			function(ready,registry,query,domStyle,domClass,lang,Tip,req,util,msg,domAttr,ready,topic){
		
		function setNeedFeedback(value){
			var feedBackDeadlineTr = query(".feedBackDeadlineTr")[0];
			var fdFeedBackDeadline = registry.byId('fdFeedBackDeadline');
			if(fdFeedBackDeadline){
				if(value){
					domStyle.set(feedBackDeadlineTr,'display','');
					fdFeedBackDeadline._set('validate', 'required after valDeadline');
				}else{
					domStyle.set(feedBackDeadlineTr,'display','none');
					fdFeedBackDeadline._set('validate', '');
				}
			}
		}
		
		function setNeedPlace(value){
			var placeTr = query(".placeResTr");
			var otherPlace = query("#otherPlace");
			var isVideo = query('[name="fdIsVideo"]')[0].value;
			var hasTemplate = "${kmImeetingMainForm.fdTemplateId}";
			var placeComponent = registry.byId('placeComponent');
			var equipmentComponent = registry.byId('equipmentComponent');
			var vicePlaceComponent = registry.byId('vicePlaceComponent');
			var otherPlaceComponent=query('[name="fdOtherPlace"]')[0];
			var kmImeetingDeviceIds=query('[name="kmImeetingDeviceIds"]')[0];
			var fdAssistPersonIds=query('[name="fdAssistPersonIds"]')[0];
			var fdAssistPersonNames=query('[name="fdAssistPersonNames"]')[0];
			var fdOtherAssistPersons=query('[name="fdOtherAssistPersons"]')[0];
			var fdArrange=query('[name="fdArrange"]')[0];
			var viceOtherPlaceComponent=query('[name="fdOtherVicePlace"]')[0];
			if(value){
				placeTr.forEach(function(node,index){
					domStyle.set(placeTr[index],'display','');
				});
				placeComponent._set('validate', 'validateplace validateUserTime');
				
				if(hasTemplate == ""){
					if(isVideo == 'true'){
						otherPlace[0].style.display = 'none';
					}else{
						validorObj._validation.addElements(query('[name="fdOtherPlace"]')[0],'validateplace');
					}
				}
			}else{
				placeTr.forEach(function(node,index){
					domStyle.set(placeTr[index],'display','none');
				});
				placeComponent._set('validate', '');
				placeComponent.curIds = "";
				placeComponent.curNames = "";
				placeComponent.set("value","");
				placeComponent.buildValue(placeComponent.cateFieldShow);
				
				equipmentComponent.curIds = "";
				equipmentComponent.curNames = "";
				equipmentComponent.set("value","");
				equipmentComponent.buildValue(equipmentComponent.cateFieldShow);
				
				otherPlaceComponent.value="";
				
				kmImeetingDeviceIds.value="";
				fdAssistPersonIds.value="";
				fdAssistPersonNames.value="";
				fdArrange.value="";
				fdOtherAssistPersons.value="";
				if(viceOtherPlaceComponent){
					viceOtherPlaceComponent.value="";
				}
				if(vicePlaceComponent){
					vicePlaceComponent._set('validate', '');
					vicePlaceComponent.curIds = "";
					vicePlaceComponent.curNames = "";
					vicePlaceComponent.set("value","");
					vicePlaceComponent.buildValue(vicePlaceComponent.cateFieldShow);
				}
			}
		}
		
		//校验对象
		var validorObj=null;
		
		//选择框控件
		topic.subscribe('mui/form/select/callback',function(widget){
			var valueField = widget.valueField;
			var value = widget.value;
			var freq="";
			if (query("[name='RECURRENCE_FREQ']")[0]) 
				freq=query("[name='RECURRENCE_FREQ']")[0].value;
			var interval="";
			if (query("[name='RECURRENCE_INTERVAL']")[0]) 
				interval=query("[name='RECURRENCE_INTERVAL']")[0].value
			var count="";
			if (query("[name='RECURRENCE_COUNT']")[0]) 
				count=query("[name='RECURRENCE_COUNT']")[0].value;
			var endType="";
			if (query("[name='RECURRENCE_END_TYPE']")[0]) 
				endType=query("[name='RECURRENCE_END_TYPE']")[0].value
			var weeks="";
			if (query("[name='RECURRENCE_WEEKS']")[0]) 
				weeks=query("[name='RECURRENCE_WEEKS']")[0].value;
			var monthType="";
			if (query("[name='RECURRENCE_MONTH_TYPE']")[0]) 
				monthType=query("[name='RECURRENCE_MONTH_TYPE']")[0].value;
			if(valueField=='RECURRENCE_FREQ'){
				var moreset = query(".moreset");
				moreset.forEach(function(node,index){
					if(value!='NO'){
						if(index>5){
							domStyle.set(moreset[index],'display','block');
						}else{
							switch(value){
								case "DAILY":
									if(index==0){
										domStyle.set(moreset[index],'display','block');
									}else{
										domStyle.set(moreset[index],'display','none');
									}
									freq = "DAILY";
									break;
								case "WEEKLY":
									if(index==1||index==4){
										domStyle.set(moreset[index],'display','block');
									}else{
										domStyle.set(moreset[index],'display','none');
									}
									freq = "WEEKLY";
									break;
								case "MONTHLY":
									if(index==2||index==5){
										domStyle.set(moreset[index],'display','block');
									}else{
										domStyle.set(moreset[index],'display','none');
									}
									freq = "MONTHLY";
									break;
								case "YEARLY":
									if(index==3){
										domStyle.set(moreset[index],'display','block');
									}else{
										domStyle.set(moreset[index],'display','none');
									}
									freq = "YEARLY";
									break;
							}
						}
					}else{
						domStyle.set(node,'display','none');
						freq = "NO";
					}
				});
			}
			if(valueField=='RECURRENCE_INTERVAL'){
				interval = value;
			}
			if(valueField=='RECURRENCE_WEEKS'){
				weeks = value;
			}
			if(valueField=='RECURRENCE_MONTH_TYPE'){
				monthType = value;
			}
			if(valueField=='RECURRENCE_END_TYPE'){
				endType = value;
				var endTypeDom = query(".endType");
				if(value=='NEVER'){
					endTypeDom.forEach(function(node){
						domStyle.set(node,'display','none');
					});
				}
				if(value=='COUNT'){
					domStyle.set(endTypeDom[0],'display','block');
					domStyle.set(endTypeDom[1],'display','none');
				}
				if(value=='UNTIL'){
					domStyle.set(endTypeDom[0],'display','none');
					domStyle.set(endTypeDom[1],'display','block');
				}
			}
			summary(freq,interval,count,endType,weeks,monthType);
		});
		var values = {"SU":"日","MO":"一","TU":"二","WE":"三","TH":"四","FR":"五","SA":"六"};
		var en_values = {"SU":"Sun","MO":"Mon","TU":"Tue","WE":"Wed","TH":"Thu","FR":"Fri","SA":"Sat"}; 
		function summary(freq,interval,count,endType,weeks,monthType){
			var unit='';
			var solar = '<span style="margin-right:10px;">${lfn:message("km-imeeting:solar")}</span>';//日历类型
			var intervalMsg="${lfn:message('km-imeeting:kmImeetingMain.summary.interval')}";
			var recurrenceTime='';
			if(freq=='DAILY'){
				unit='${lfn:message("km-imeeting:daily")}';
			}
			if(freq=='WEEKLY'){
				unit='${lfn:message("km-imeeting:week")}';
				recurrenceTime+=unit;
				if(weeks!=''){
					var _weeks=weeks.split(";");
					if(Com_Parameter.Lang.indexOf("en")!=-1){
						recurrenceTime+=" ";
						for(var i=0;i<_weeks.length;i++){
							recurrenceTime+=en_values[_weeks[i]]+"、";
						}
					}else{
						for(var i=0;i<_weeks.length;i++){
							recurrenceTime+=values[_weeks[i]]+"、";
						}
					}
					recurrenceTime=recurrenceTime.substring(0,recurrenceTime.length-1);
				}
			}
			if(freq=='MONTHLY'){
				unit='${lfn:message("km-imeeting:month")}';
				var d = query("input[name='fdHoldDate']")[0].value;
				var date = new Date();
				if(d!=''){
					date = Com_GetDate(d,'date','${formatter}');
				}
				if(monthType=='month'){
					recurrenceTime+="${lfn:message('km-imeeting:kmImeetingMain.summary.eachMonth')}".replace("%day%",date.getDate());
				}
				if(monthType=='week'){
					recurrenceTime+="${lfn:message('km-imeeting:kmImeetingMain.summary.eachWeek')}".replace("%order%",Math.ceil(date.getDate() / 7)).replace("%week%","${lfn:message('calendar.week.names')}".split(',')[date.getDay()]);
				}
			}
			if(freq=='YEARLY'){
				unit='${lfn:message("km-imeeting:year")}';
			}
			intervalMsg=intervalMsg.replace("%interval%",interval).replace("%unit%",unit)+"</span>";
			var intervalStr="<span style='margin-right:10px;'>"+intervalMsg;
			if(recurrenceTime!=''&&weeks!=''){
				intervalStr+="<span style='margin-right:10px'>"+recurrenceTime+"</span>";
			}
			var endTypeStr = '<span style="margin-right:10px;">';
			switch(endType){
				case "NEVER":
					endTypeStr+="<bean:message bundle='km-imeeting' key='recurrence.end.type.never'/>";
					break;
				case "COUNT":
					endTypeStr+="${lfn:message('km-imeeting:kmImeetingMain.summary.freqEnd')}".replace("%count%",count);
					break;
				case "UNTIL":
					endTypeStr+="<bean:message bundle='km-imeeting' key='recurrence.end.type.until'/><span id='untilTime'></span>";
					break;
			}
			endTypeStr+="</span>";
			var summary = query("#summary")[0];
			if (summary) {
				summary.innerHTML = solar+intervalStr+endTypeStr;
			}
		}
		
		window.reCount = function(value){
			var _freq = query("[name='RECURRENCE_FREQ']")[0].value;
			var _interval = query("[name='RECURRENCE_INTERVAL']")[0].value;
			var _count = value;
			var _weeks = query("[name='RECURRENCE_WEEKS']")[0].value;
			var _monthType = query("[name='RECURRENCE_MONTH_TYPE']")[0].value;
			summary(_freq,_interval,_count,'COUNT',_weeks,_monthType);
		};
		
		window.untilChange=function(){
			var until = query("[name='RECURRENCE_UNTIL']")[0].value;
			query("#untilTime")[0].innerHTML=until;
		};
		
		ready(function(){
			validorObj=registry.byId('scrollView');
			
			topic.subscribe('km/imeeting/statChanged',function(widget,value){
				if(widget.property == "fdNeedPlace"){
					setNeedPlace(value);
				}
				
				if(widget.property == "fdNeedFeedback"){
					setNeedFeedback(value);
				}
				
			});
			
			var fdNeedPlace = "${kmImeetingMainForm.fdNeedPlace}"=="true"?true:false;
			if ("${isVideoMeetingEnable}"=="false") {
				fdNeedPlace=true;
			}
			setNeedPlace(fdNeedPlace);
			
			var fdNeedFeedback = "${kmImeetingMainForm.fdNeedFeedback}"=="true"?true:false;
			setNeedFeedback(fdNeedFeedback);
			
			//完善更多信息点击事件
			if(query('.meetingMoreOpt')[0]){
			 	var moreInfoOpt = query('.meetingMoreOpt')[0],
					moreInfo = query('.meetingMoreInfo')[0],
					moreInfoIcon = query('.meetingMoreIcon')[0];
			 		moreInfoOpt.dojoClick = true;
					moreInfoOpt.onclick = function(){
					var display = domStyle.get(moreInfo,'display');
					if(display != 'none'){
						domStyle.set(moreInfo,'display','none');
						domClass.add(moreInfoIcon,'mui-down-n');
						domClass.remove(moreInfoIcon,'mui-up-n');
					}else{
						domStyle.set(moreInfo,'display','block');
						domClass.add(moreInfoIcon,'mui-up-n');
						domClass.remove(moreInfoIcon,'mui-down-n');
						var view=registry.byId("scrollView");
						view.handleToTopTopic(null,{
								y: 0 - (query(".meetingMoreInfo")[0].offsetTop)
						});
					}
				};	
			}
			
			//
			var hostId = '${kmImeetingMainForm.fdHostId}';
			var hostName = '${kmImeetingMainForm.fdHostName}';
			var presidtor = query("#presidtor")[0].children[0];
			
			var digitAddress = registry.byNode(presidtor);
			digitAddress._setCurIdsAttr(hostId);
			digitAddress._setCurNamesAttr(hostName);
			if('${kmImeetingMainForm.fdRecurrenceStr}' != ''){
				var _freq='${kmImeetingMainForm.RECURRENCE_FREQ}';
				var moreset = query(".moreset");
				moreset.forEach(function(node,index){
					if(_freq!='NO'){
						if(index>5){
							domStyle.set(moreset[index],'display','block');
						}else{
							switch(_freq){
								case "DAILY":
									if(index==0){
										domStyle.set(moreset[index],'display','block');
									}
									break;
								case "WEEKLY":
									if(index==1||index==4){
										domStyle.set(moreset[index],'display','block');
									}
									break;
								case "MONTHLY":
									if(index==2||index==5){
										domStyle.set(moreset[index],'display','block');
									}
									break;
								case "YEARLY":
									if(index==3){
										domStyle.set(moreset[index],'display','block');
									}
									break;
							}
						}
						var _interval = '${kmImeetingMainForm.RECURRENCE_INTERVAL}';
						var _count = '${kmImeetingMainForm.RECURRENCE_COUNT}';
						var _endType = '${kmImeetingMainForm.RECURRENCE_END_TYPE}';
						var _weeks = '${kmImeetingMainForm.RECURRENCE_WEEKS}';
						var _monthType = '${kmImeetingMainForm.RECURRENCE_MONTH_TYPE}';
						summary(_freq,_interval,_count,_endType,_weeks,_monthType);
						var endTypeDom = query('.endType');
						if(_endType=='COUNT'){
							domStyle.set(endTypeDom[0],'display','block');
						}
						if(_endType=='UNTIL'){
							domStyle.set(endTypeDom[1],'display','block');
							untilChange();
						}
					}
				});
			}
			
			
			//自定义校验器
			validorObj._validation.addValidator("validateUserTime",'${lfn:message("km-imeeting:kmImeetingRes.fdUserTime.error.tip")}',function(v, e, o){
				var result = _validateUserTime();
				if(result == false){
					validator = this.getValidator('validateUserTime');
					var error = '${lfn:message("km-imeeting:kmImeetingRes.fdUserTime.error.tip")}';
					error = error.replace('%fdPlaceName%', query('[name="fdPlaceName"]')[0].value ).replace('%fdUserTime%', query('[name="fdPlaceUserTime"]')[0].value );
					validator.error = error;
				}
				return result;
			});
			
			//自定义校验器:会议地点不能全为空
			validorObj._validation.addValidator("validateplace","{name} ${lfn:message('km-imeeting:kmImeetingMain.tip.notNull')}",function(v, e, o) {
				if(e.id != "placeComponent"){
					var placeComponent = registry.byId('placeComponent');
					 validorObj._validation.validateElement(placeComponent);
					return true;
				}else{
					var result= _validatePlace();
					return result;
				}
			});
			
			validorObj._validation.addValidator("validateViceUserTimes",'${lfn:message("km-imeeting:kmImeetingRes.fdUserTime.error.tip")}',function(v, e, o){
				var result = _validateViceUserTimes();
				if(result !=null){//修复移动端校验会议室使用时长#102890
					validator = this.getValidator('validateViceUserTimes');
					var error = '${lfn:message("km-imeeting:kmImeetingRes.fdUserTime.error.tip")}';
					error = error.replace('%fdPlaceName%', result.placeName ).replace('%fdUserTime%', result.userTime );
					validator.error = error;
					return false;
				}
				return true;
			});
			
			//调用结束时间验证
			validorObj._validation.addValidator("beforeFinishDate",'${lfn:message("km-imeeting:kmImeetingMain.fdDate.beforetip")}',function(v, e, o){
				var fdFinishDate = registry.byId('fdFinishDate');
				 validorObj._validation.validateElement(fdFinishDate);
				 return true;
				
			});
			validorObj._validation.addValidator("afterHoldDate",'${lfn:message("km-imeeting:kmImeetingMain.fdDate.tip")}',function(v, e, o){
				return _validateAfterHoldDate();
			});
            //纪要完成时间不能早于会议召开时间
            validorObj._validation.addValidator("validateSummaryTime",'${lfn:message("km-imeeting:kmImeeting.validateSummaryTime.tip")}',function(v, e, o){
                var result = true;
                //召开时间
                var fdHoldDate = Com_GetDate(query('[name="fdHoldDate"]')[0].value);
                //纪要时间
                var fdSummaryCompleteTime = Com_GetDate(query('[name="fdSummaryCompleteTime"]')[0].value);
                var fdSummaryCompleteTime_value = $("input[name='fdSummaryCompleteTime']")[0].value;
                if(fdSummaryCompleteTime_value) {
                    if (fdHoldDate.getTime() > fdSummaryCompleteTime.getTime()) {
                        result = false;
                    }
                }
                return result;
            });
			
			validorObj._validation.addValidator("valExpectTime",'该时间必须介于开始时间和结束时间之间',function(v, e, o){
				var result = true
				var feedBackDeadVal = Com_GetDate(v);
				var fdHoldDate = Com_GetDate(query('[name="fdHoldDate"]')[0].value);
				var fdFinishDate = Com_GetDate(query('[name="fdFinishDate"]')[0].value);
				if( fdHoldDate.getTime()>feedBackDeadVal.getTime() ){
					result=false;
				}
				if( feedBackDeadVal.getTime()>fdFinishDate.getTime() ){
					result=false;
				}
				return result;
			});
			
			validorObj._validation.addValidator("valDeadline",'回执截止时间不能晚于会议开始时间',function(v, e, o){
				var result = true
				var feedBackDeadVal = Com_GetDate(v);
				var fdHoldDate = Com_GetDate(query('[name="fdHoldDate"]')[0].value);
				var fdFinishDate = Com_GetDate(query('[name="fdFinishDate"]')[0].value);
				if( fdHoldDate.getTime() < feedBackDeadVal.getTime() ){
					result=false;
				}
				
				return result;
			});

            //自定义校验器:参加人员和外部参加人员不能全为空
            validorObj._validation.addValidator('validateattend','{name} ${lfn:message("km-imeeting:kmImeetingMain.tip.notNull")}',function(v, e, o){
                var result=true;
                if(e.name=="fdOtherAttendPerson"){
                    var fdAttendPersonIds = registry.byId('fdAttendPersonIds');
                    validorObj._validation.validateElement(fdAttendPersonIds);
                }else{
                    var otherAttendPerson=query('[name="fdOtherAttendPerson"]').val();//外部与会人员
                    if( v || otherAttendPerson){
                        result=true;
                    }else{
                        result=false;
                    }
                }
                return result;
            });

			validorObj._validation.addValidator("valuesIsEmpty",'${lfn:message("km-imeeting:kmImeetingMain.agendaNotNull")}',function(v, e, o){
				validator = this.getValidator('valuesIsEmpty');
				var oUl = e.UlNode;
				var Input = oUl.querySelectorAll(".inputNodeClass");
				for(var i = 0;i<(Input?Input.length:0);i++){
					if(Input[i].value==''){
						return false;
					}
				}
				return true; 
			});
			//var duration = parseFloat( query('[name="fdHoldDurationHour"]')[0].value );
			var fdHoldDate = Com_GetDate(query('[name="fdHoldDate"]')[0].value);
			var fdFinishDate = Com_GetDate(query('[name="fdFinishDate"]')[0].value);
			var duration = (fdFinishDate.getTime() - fdHoldDate.getTime()) / 3600000;
			
			onHoldDurationChange(duration);
		});
		
		function _validateBeforeFinishDate() {
			var fdHoldDate = Com_GetDate(query('[name="fdHoldDate"]')[0].value);
			var fdFinishDate = Com_GetDate(query('[name="fdFinishDate"]')[0].value);
			return fdHoldDate.getTime() < fdFinishDate.getTime();
		}
		
		function _validateAfterHoldDate() {
			var fdHoldDate = Com_GetDate(query('[name="fdHoldDate"]')[0].value);
			var fdFinishDate = Com_GetDate(query('[name="fdFinishDate"]')[0].value);
			return fdFinishDate.getTime() > fdHoldDate.getTime();
		}

		
		window.handleDurationChange = function() {
			var fdHoldDateStr = query('[name="fdHoldDate"]')[0].value;
			var fdFinishDateStr= query('[name="fdFinishDate"]')[0].value
			
			//存在兼容问题，2018-9-3 14:00需变成2018/9/3 14:00格式
 			//fdHoldDateStr = fdHoldDateStr.replace(/-/g,'/');
			//fdFinishDateStr = fdFinishDateStr.replace(/-/g,'/'); 
			
			var fdHoldDate = Com_GetDate(fdHoldDateStr);
			var fdFinishDate = Com_GetDate(fdFinishDateStr);
			
			var fdHoldDurationHour = query('[name="fdHoldDurationHour"]')[0];
			
			
			if(fdHoldDurationHour) {
				if((fdFinishDate.getTime() - fdHoldDate.getTime())>0){
					domAttr.set(fdHoldDurationHour, 'value', ((fdFinishDate.getTime() - fdHoldDate.getTime()) / 3600000).toFixed(1));
					onHoldDurationChange((fdFinishDate.getTime() - fdHoldDate.getTime()) / 3600000);
				}else{
					domAttr.set(fdHoldDurationHour, 'value', '');
				}
				
			}
			
		}
		//校验最大使用时长
		function _validateUserTime(){
			var userTime = query('[name="fdPlaceUserTime"]'),
				fdPlaceId = query('[name="fdPlaceId"]');
			if(fdPlaceId[0].value && userTime[0].value){
				//var duration = parseFloat( query('[name="fdHoldDurationHour"]')[0].value ) * 3600 * 1000;
				var fdHoldDate = Com_GetDate(query('[name="fdHoldDate"]')[0].value);
				var fdFinishDate = Com_GetDate(query('[name="fdFinishDate"]')[0].value);
				var duration = fdFinishDate.getTime() - fdHoldDate.getTime();
				
				if( parseFloat(userTime[0].value) == 0 ||  parseFloat(userTime[0].value) == 0.0){
					return true;
				} else if( duration > userTime[0].value * 3600 * 1000 ){
					return false;
				}else{
					return true;
				}
			}
			return true;
		}
		
		function _validatePlace(){
			var fdPlaceId=query('[name="fdPlaceId"]');//地点
			var fdOtherPlace=query('[name="fdOtherPlace"]');//外部地点
			if(fdPlaceId[0].value || fdOtherPlace[0].value){
				return true;
			}else{
				return false;
			}
		};
		
		function _validateViceUserTimes(){
			var userTime = query('[name="fdVicePlaceUserTimes"]'),
				fdPlaceNames = query('[name="fdVicePlaceNames"]');
			if(fdPlaceNames[0].value && userTime[0].value){//修复移动端校验会议室使用时长#102890
				//var duration = parseFloat( query('[name="fdHoldDurationHour"]')[0].value ) * 3600 * 1000;
				var fdHoldDate = Com_GetDate(query('[name="fdHoldDate"]')[0].value);
				var fdFinishDate = Com_GetDate(query('[name="fdFinishDate"]')[0].value);
				var duration = fdFinishDate.getTime() - fdHoldDate.getTime();
				var placeNameArray=fdPlaceNames[0].value.split(';');
				var userTimeArray=userTime[0].value.split(';');
				if(placeNameArray.length>0&&placeNameArray.length==userTimeArray.length){
					for(var i=0;i<placeNameArray.length;i++){
						if(userTimeArray[i]=='' || parseFloat(userTimeArray[i]) == 0 ||  parseFloat(userTimeArray[i]) == 0.0){
							continue;
						} 
						else if( duration > userTimeArray[i] * 3600 * 1000 ){
							return {
								placeName:placeNameArray[i],
								userTime:userTimeArray[i]
							}
						}
					}
				}
			}
			return null;
		}
		
		function valuesIsEmpty(){
			var inputNode = query(".AgendaList")[0].getElementsByTagName('input');
			for(var i = 0;i<inputNode.length;i++){
				if(inputNode[i].value=""){
					return false;
				}
			}
			return true;
		}
		
		window.validateCount = function(){
			var endType = query("[name='RECURRENCE_END_TYPE']")[0].value;
			var count = query("[name='RECURRENCE_COUNT']")[0].value;
			if(endType=="COUNT"){
				if(!/^[1-9]\d*$/.test(count)||count.length<=0){
					Tip.fail({
						text:'<bean:message key="kmImeetingMain.tip.validateCount.errorCount" bundle="km-imeeting" />' 
					});
					return false;
				}
			}
			return true;
		}
		
		window.validateUntilTime = function(){
			var endType = query("[name='RECURRENCE_END_TYPE']")[0].value;
			var until = query("[name='RECURRENCE_UNTIL']")[0].value;
			if(endType=='UNTIL'){
				if(until==''){
					Tip.fail({
						text:'<bean:message key="kmImeetingMain.tip.validateUntilTime.notNull" bundle="km-imeeting" />' 
					});
					return false;
				}else{
					var untilDate = Com_GetDate(until,'date','${formatter}');
					var startDate = Com_GetDate(query('[name="fdHoldDate"]')[0].value,'date','${formatter}');
					if(untilDate.getTime()<startDate.getTime()){
						Tip.fail({
							text:'<bean:message key="kmImeetingMain.tip.validateUntilTime.errorDate" bundle="km-imeeting" />' 
						});
						return false;
					}
				}
			}
			return true;
		}
		
		
		window.onHoldDurationChange = function(value){
			var placeComponent = registry.byId('placeComponent');
			validorObj._validation.validateElement(placeComponent);
			
			if('${fdNeedMultiRes}' == 'true') {
				var vicePlaceComponent = registry.byId('vicePlaceComponent');
				validorObj._validation.validateElement(vicePlaceComponent);
			}
			
			var fdHoldDurationHour = query('[name="fdHoldDurationHour"]')[0];
			
			
			if(fdHoldDurationHour) {
				if(value>0){
					domAttr.set(fdHoldDurationHour, 'value', value.toFixed(1));
				}else{
					domAttr.set(fdHoldDurationHour, 'value', '');
				}
			}
		};
		
		window.getFormatDate = function(date){
			var seperator1 = "-";
		    var seperator2 = ":";
		    var month = date.getMonth() + 1;
		    var strDate = date.getDate();
		    if (month >= 1 && month <= 9) {
		        month = "0" + month;
		    }
		    if (strDate >= 0 && strDate <= 9) {
		        strDate = "0" + strDate;
		    }
		    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
		            + " " + date.getHours() + seperator2 + date.getMinutes();
		    return currentdate;
		};
		
		//会议提交
		window.commitMethod = function(commitType,dStatus){
			var fdIsVideo = document.getElementsByName("fdIsVideo")[0].value;
			var fdNeedPlace = document.getElementsByName("fdNeedPlace")[0].value;
			
			if(fdIsVideo =="false" && fdNeedPlace=="false"){
				Tip.tip({icon:'mui mui-warn', text:msg['mobile.kmImeetingMain.videoAndPlace'],width:'260'});
				return;
			}
			
			var hasTemplate = "${kmImeetingMainForm.fdTemplateId}";
			var isCycle = "${kmImeetingMainForm.isCycle}";
			if(hasTemplate != "" && isCycle == "true"){
				//结束条件为次数才校验
				if(validateCount()==false){
					return false;
				}
				//结束条件为日期才校验
				if(validateUntilTime()==false){
					return false;
				}
			}
			var validorObj=registry.byId('scrollView');

			setTimeout(function(){
				if(validorObj.validate()){
					var formObj = document.kmImeetingMainForm;
					var docStatus = document.getElementsByName("docStatus")[0];
					//docStatus.value = '30';
				
					if(dStatus=='true'){
						docStatus.value = '20';
					}else{
						docStatus.value = '30';
					}
					var placeArr = [], isVicePlace = false;
					if($('[name="fdPlaceId"]').val()){
						placeArr.push($('[name="fdPlaceId"]').val());
					}
					if($('[name="fdVicePlaceIds"]').val()){
						placeArr.push($('[name="fdVicePlaceIds"]').val());
						isVicePlace = true;
					}
					var fdHoldDate = query('[name="fdHoldDate"]')[0].value;
					// var duration = query('[name="fdHoldDurationHour"]')[0].value;
					//var fdFinishDate = getFormatDate(new Date(Com_GetDate(fdHoldDate,'datetime').valueOf()+duration*3600*1000));
					var fdFinishDate = query('[name="fdFinishDate"]')[0].value;
					req(util.formatUrl("/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=checkConflict"), {
						handleAs : 'json',
						method : 'post',
						data : {
							bookId : $('[name="bookId"]').val(),
							fdHoldDate:fdHoldDate,
							fdFinishDate : fdFinishDate,
							RECURRENCE_FREQ : $('[name="RECURRENCE_FREQ"]').val() || '',
							RECURRENCE_INTERVAL : $('[name="RECURRENCE_INTERVAL"]').val() || '',
							RECURRENCE_COUNT : $('[name="RECURRENCE_COUNT"]').val() || '',
							RECURRENCE_UNTIL : $('[name="RECURRENCE_UNTIL"]').val() || '',
							RECURRENCE_BYDAY : $('[name="RECURRENCE_BYDAY"]').val() || '',
							RECURRENCE_SUMMARY : $('[name="RECURRENCE_SUMMARY"]').val() || '',
							RECURRENCE_END_TYPE : $('[name="RECURRENCE_END_TYPE"]').val() || '',
							RECURRENCE_WEEKS : $('[name="RECURRENCE_WEEKS"]').val() || '',
							RECURRENCE_MONTH_TYPE : $('[name="RECURRENCE_MONTH_TYPE"]').val() || '',
							RECURRENCE_START : $('[name="RECURRENCE_START"]').val() || '',
							RECURRENCE_WEEKS : $('[name="RECURRENCE_WEEKS"]').val() || '',
							recurrenceStr : $('[name="fdRecurrenceStr"]').val() || '',
							fdPlaceId : placeArr.join(';'),
							meetingId : '${JsParam.fdOriginalId}' || '${JsParam.fdId}'
						}
					}).then(lang.hitch(this, function(data) {
						if(data && !data.result){
							if('save'==commitType){
								Com_Submit(formObj, commitType);
						    }else{
						    	Com_Submit(formObj, commitType); 
						    }
						}else{
							if(isVicePlace) {
								var conflictName = "";
								if(data.conflictArr){
									conflictArr = data.conflictArr;
									for(var i = 0; i<conflictArr.length;i++){
										var conflict = conflictArr[i];
										conflictName += conflict.conflictName;
										if(i < conflictArr.length - 1) {
											conflictName += ";";
										}
									}
								}
								Tip.tip({icon:'mui mui-warn', text:msg['mobile.kmImeetingMain.conflict.tip2'].replace('%Place%',conflictName),width:'260'});
							} else {
								Tip.tip({icon:'mui mui-warn', text:msg['mobile.kmImeetingMain.conflict.tip'],width:'260'});
							}
							return;
						}
					}));
				}
			}, 100);
		};
	});
</script>