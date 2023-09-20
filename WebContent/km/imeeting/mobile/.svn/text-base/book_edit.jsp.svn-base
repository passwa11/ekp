<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));%>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="title">
			<bean:message bundle="km-imeeting" key="mobile.kmImeetingBook.create"/>
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/meetingBook.css?s_cache=${MUI_Cache}" />
	</template:replace>
	<template:replace name="content">
	<xform:config  orient="vertical">
		<html:form action="/km/imeeting/km_imeeting_book/kmImeetingBook.do"  styleId="bookform">
			<html:hidden property="fdId" />
			<html:hidden property="authAreaId" />
			<html:hidden property="docCreatorId" />
			<div id="scrollView"  data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
				<div class="meetingBookInfo" data-dojo-type="mui/panel/Content" >
					<div class="muiFormContent">
						
						<%-- 
						<table class="muiSimple" cellpadding="0" cellspacing="0" >
							<tr>
								<td width="50%" >
									<div class="holdDateTimeDiv">
									  <div>
									  	<div class="startDate" name="startDate">
									  	</div>
									  	<div class="startTime" name="startTime">
									  	</div>
									  </div>
									  <xform:datetime property="fdHoldDate" dateTimeType="datetime" mobile="true"  required="true" validators="after" onValueChange="onHoldDurationChange"></xform:datetime>
									</div>
								</td>
								<td width="50%" >
									<div class="finishDateTimeDiv">
										<div>
										  	<div class="endDate" name="endDate">
										  	</div>
										  	<div class="endTime" name="endTime">
										  	</div>
										  </div>
									  <xform:datetime property="fdFinishDate" dateTimeType="datetime" mobile="true"  required="true" validators="after" onValueChange="onHoldDurationChange"></xform:datetime>
									</div>
									<input type="hidden" name="fdHoldDuration" />
								</td>
							</tr>
						</table>
						
						<table style="width:100%" cellpadding="0" cellspacing="0">
				             <tr>
				             	<td>
									<input type="text" id="timeValidate"  name="timeValidate"  validate="compareTime" readonly="readonly" style="border:0"/>
								</td>
				            </tr>
				        </table>
						--%>
						
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr style="display: none;">
								<td>
									<input type="text" id="timeValidate"  name="timeValidate"  validate="compareTime" readonly="readonly" style="border:0"/>
								</td>
							</tr>
				             <tr>
				              	<td>
				              		<xform:text 
				              			subject="${lfn:message('km-imeeting:kmImeetingBook.fdName') }" 
				              			property="fdName" style="width:90%" mobile="true" >
				              		</xform:text>
				              	</td>
				            </tr>
				            <tr>
				            	<td>
									<xform:datetime property="fdHoldDate" dateTimeType="datetime" mobile="true" required="true" validators="after compareTime" onValueChange="onHoldDurationChange"></xform:datetime>
				            	</td>
				            </tr>
							<tr>
								<td>
									<xform:datetime property="fdFinishDate" dateTimeType="datetime" mobile="true"  required="true" validators="after compareTime" onValueChange="onHoldDurationChange"></xform:datetime>
								</td>
							</tr>
				            <tr>
								<td>
									<!-- 重复规则 -->
									<div id="recurrenceView" data-dojo-type="dojox/mobile/View">
										<div>
											<xform:select
												subject="${lfn:message('km-imeeting:kmImeetingMain.fdRecurrenceType') }"
												property="RECURRENCE_FREQ" showPleaseSelect="false"
												showStatus="edit" htmlElementProperties="id='recurrence_freq'"
												mobile="true">
												<xform:enumsDataSource enumsType="km_imeeting_recurrence_freq" />
											</xform:select>
										</div>
				
										<div class="moreset">
											<div data-dojo-type="mui/form/Select"
												data-dojo-props="name:'RECURRENCE_INTERVAL',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.recurrenceInterval"/>',value:'${kmImeetingBookForm.RECURRENCE_INTERVAL }',showPleaseSelect:false,orient:'vertical',
												         store:[<c:forEach begin="1" end="50" varStatus="index">
								                            {text:'<bean:message bundle="km-imeeting" key="each"/>${index.count}<bean:message bundle="km-imeeting" key="daily"/>',value:'${index.count}'}
								                            <c:if test="${ index.count!=50 }">,</c:if>
								                        </c:forEach>]">
											</div>
										</div>
										<div class="moreset">
											<div data-dojo-type="mui/form/Select"
												data-dojo-props="name:'RECURRENCE_INTERVAL',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.recurrenceInterval"/>',value:'${kmImeetingBookForm.RECURRENCE_INTERVAL }',showPleaseSelect:false,orient:'vertical',
												         store:[<c:forEach begin="1" end="50" varStatus="index">
								                            {text:'<bean:message bundle="km-imeeting" key="each"/>${index.count}<bean:message bundle="km-imeeting" key="week"/>',value:'${index.count}'}
								                            <c:if test="${ index.count!=50 }">,</c:if>
								                        </c:forEach>]">
											</div>
										</div>
										<div class="moreset">
											<div data-dojo-type="mui/form/Select"
												data-dojo-props="name:'RECURRENCE_INTERVAL',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.recurrenceInterval"/>',value:'${kmImeetingBookForm.RECURRENCE_INTERVAL }',showPleaseSelect:false,orient:'vertical',
												         store:[<c:forEach begin="1" end="50" varStatus="index">
								                            {text:'<bean:message bundle="km-imeeting" key="each"/>${index.count}<bean:message bundle="km-imeeting" key="month"/>',value:'${index.count}'}
								                            <c:if test="${ index.count!=50 }">,</c:if>
								                        </c:forEach>]">
											</div>
										</div>
										<div class="moreset">
											<div data-dojo-type="mui/form/Select"
												data-dojo-props="name:'RECURRENCE_INTERVAL',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.recurrenceInterval"/>',value:'${kmImeetingBookForm.RECURRENCE_INTERVAL }',showPleaseSelect:false,orient:'vertical',
												         store:[<c:forEach begin="1" end="50" varStatus="index">
								                            {text:'<bean:message bundle="km-imeeting" key="each"/>${index.count}<bean:message bundle="km-imeeting" key="year"/>',value:'${index.count}'}
								                            <c:if test="${ index.count!=50 }">,</c:if>
								                        </c:forEach>]">
											</div>
										</div>
										<div class="moreset">
											<div data-dojo-type="mui/form/Select"
												data-dojo-props="name:'RECURRENCE_WEEKS',showStatus:'edit',subject:'<bean:message bundle="km-imeeting" key="kmImeetingMain.recurrenceTimeType" />',value:'${kmImeetingBookForm.RECURRENCE_WEEKS }',showPleaseSelect:false,orient:'vertical',
														 store:[{text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Sunday"/>',value:'SU'},
														 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Monday"/>',value:'MO'},
														 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Tuesday"/>',value:'TU'},
														 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Wednesday"/>',value:'WE'},
														 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Thursday"/>',value:'TH'},
														 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Friday"/>',value:'FR'},
														 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Saturday"/>',value:'SA'}]"></div>
										</div>
										<div class="moreset">
											<div data-dojo-type="mui/form/Select"
												data-dojo-props="name:'RECURRENCE_MONTH_TYPE',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-imeeting" key="kmImeetingMain.recurrenceTimeType" />',value:'${kmImeetingBookForm.RECURRENCE_MONTH_TYPE }',showPleaseSelect:false,orient:'vertical',
														 store:[{text:'<bean:message bundle="km-imeeting" key="recurrence.month.type.month"/>',value:'month'},
														 {text:'<bean:message bundle="km-imeeting" key="recurrence.month.type.week"/>',value:'week'}]">
											</div>
										</div>
										<div class="moreset">
											<div data-dojo-type="mui/form/Select"
												data-dojo-props="name:'RECURRENCE_END_TYPE',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-imeeting" key="kmImeetingMain.recurrenceEndType" />',value:'${kmImeetingBookForm.RECURRENCE_END_TYPE }',showPleaseSelect:false,orient:'vertical',
														 store:[{text:'<bean:message bundle="km-imeeting" key="recurrence.end.type.never"/>',value:'NEVER'},
														 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.end.type.count" />',value:'COUNT'},
														 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.end.type.until" />',value:'UNTIL'}]">
											</div>
											<div class="endType">
												<xform:text showStatus="edit" validators="count"
													property="RECURRENCE_COUNT"
													onValueChange="reCount(this.value);" mobile="true" />
											</div>
											<div class="endType">
												<xform:datetime showStatus="edit" property="RECURRENCE_UNTIL"
													onValueChange="untilChange" dateTimeType="date" mobile="true" />
											</div>
										</div>
										<div class="moreset">
											<span style="padding-left: 1rem;"><bean:message
													bundle="km-imeeting" key="kmImeetingMain.summary" />:</span> <input
												type="hidden" name="RECURRENCE_SUMMARY" />
											<div id="summary" style="margin-left: 1rem"></div>
										</div>
									</div>
								</td>
							</tr>
				            <tr>
								<td>
									<div id="placeComponent"
										data-dojo-type="km/imeeting/mobile/resource/js/PlaceComponent"
										data-dojo-props='
											"subject":"${lfn:message("km-imeeting:kmImeetingBook.fdPlace") }",
											"idField":"fdPlaceId",
											"nameField":"fdPlaceName",
											"showStatus":"edit",
											"curIds":"${kmImeetingBookForm.fdPlaceId}",
											"curNames":"${kmImeetingBookForm.fdPlaceName}",
											"validate":"validateSelectTime validateUserTime",
											orient:"vertical",
											required:true'></div>
									<input type="hidden" name="fdPlaceUserTime" value="${kmImeetingBookForm.fdUserTime}"/>
								</td>
							</tr>
				             <tr>
				              	<td>
				              		<xform:textarea property="fdRemark"  mobile="true" subject="${ lfn:message('km-imeeting:mobile.imeeting.desc') }"/>
				              	</td>
				            </tr>
						</table>
					</div>
				</div>
				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
					
					<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " 
					  	data-dojo-props='colSize:2,href:"javascript:save();"'>
						  <bean:message bundle="km-imeeting" key="mobile.kmImeetingBook.book" />
					 </li>
					
				</ul>
			</div>
		</html:form>
		</xform:config>
	</template:replace>
</template:include>
<script>
require(["mui/form/ajax-form!kmImeetingBookForm"]);
require(['dojo/ready','dojo/date/locale','dojo/date','dijit/registry','dojo/topic','dojo/query','dojo/dom-style','dojo/dom-class',"dojo/_base/lang","mui/dialog/Tip","dojo/request","mui/util","mui/i18n/i18n!km-imeeting:mobile"],
		function(ready,locale,dateClz,registry,topic,query,domStyle,domClass,lang,Tip,req,util,msg){

	//选择框控件
	topic.subscribe('mui/form/select/callback',function(widget){
		var valueField = widget.valueField;
		var value = widget.value;
		var freq=query("[name='RECURRENCE_FREQ']")[0].value;
		var interval=query("[name='RECURRENCE_INTERVAL']")[0].value;
		var count=query("[name='RECURRENCE_COUNT']")[0].value;
		var endType=query("[name='RECURRENCE_END_TYPE']")[0].value;
		var weeks=query("[name='RECURRENCE_WEEKS']")[0].value;
		var monthType=query("[name='RECURRENCE_MONTH_TYPE']")[0].value;
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
		summary.innerHTML = solar+intervalStr+endTypeStr;
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
	
	//校验对象
	var validorObj=null;
	
	ready(function(){
		validorObj=registry.byId('scrollView');
		
		if('${kmImeetingBookForm.method_GET}'=='edit'){
			var _freq='${kmImeetingBookForm.RECURRENCE_FREQ}';
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
					var _interval = '${kmImeetingBookForm.RECURRENCE_INTERVAL}';
					var _count = '${kmImeetingBookForm.RECURRENCE_COUNT}';
					var _endType = '${kmImeetingBookForm.RECURRENCE_END_TYPE}';
					var _weeks = '${kmImeetingBookForm.RECURRENCE_WEEKS}';
					var _monthType = '${kmImeetingBookForm.RECURRENCE_MONTH_TYPE}';
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
		//校验召开时间不能晚于结束时间
		validorObj._validation.addValidator("compareTime",'${lfn:message("km-imeeting:kmImeetingMain.fdDate.tip")}',function(v, e, o){
			var result = _compareTime();
			if(result){
				if(query(".muiFormFlexEle .muiValidate").length > 1){
					domStyle.set(query(".muiFormFlexEle .muiValidate")[0],'display','none');
					domStyle.set(query(".muiFormFlexEle .muiValidate")[1],'display','none');
				}else if(query(".muiFormFlexEle .muiValidate").length > 0){
					//#102883 修复
					var holeDateStr = query('[name="fdHoldDate"]')[0].value;
					var startDate=locale.parse(holeDateStr,
							{
								selector : 'time',
								timePattern : "${ lfn:message('date.format.datetime') }"
							});
					if(dateClz.compare(startDate,new Date())>=0){
						domStyle.set(query(".muiFormFlexEle .muiValidate")[0],'display','none');
					}
				}
				
			}
			return result;
		});
		validorObj._validation.addValidator("validateSelectTime",'${lfn:message("km-imeeting:kmImeetingRes.makeDate")}',function(v, e, o){
			var result = _validateSelectTime();
			return result;
		});
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
		
	});
	
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
	
	window.onHoldDurationChange = function(){
		var placeComponent = registry.byId('placeComponent');
		validorObj._validation.validateElement(placeComponent);
		var timevalidate = query('[name="timeValidate"]')[0];
		validorObj._validation.validateElement(timevalidate);
	};
	//保存会议室预约
	window.save=function(){
		//结束条件为次数才校验
		if(validateCount()==false){
			return false;
		}
		//结束条件为日期才校验
		if(validateUntilTime()==false){
			return false;
		}
		if(validorObj.validate()){
			var formObj = document.kmImeetingBookForm;
			req(util.formatUrl("/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=checkConflict"), {
				handleAs : 'json',
				method : 'post',
				data : {
					bookId : query('[name="fdId"]')[0].value,
					fdHoldDate:query('[name="fdHoldDate"]')[0].value,
					fdFinishDate : query('[name="fdFinishDate"]')[0].value,
					fdPlaceId : query('[name="fdPlaceId"]')[0].value,
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
					recurrenceStr : $('[name="fdRecurrenceStr"]').val() || ''
				}
			}).then(lang.hitch(this, function(data) {
				if (data && !data.result ){
					/*var holeDateStr = query('[name="fdHoldDate"]')[0].value;
					var finishDateStr = query('[name="fdFinishDate"]')[0].value;
					if( holeDateStr && finishDateStr){
						var startDate=locale.parse(holeDateStr,
							{
								selector : 'time',
								timePattern : "${ lfn:message('date.format.datetime') }"
							});
						var finishDate=locale.parse(finishDateStr,
							{
								selector : 'time',
								timePattern : "${ lfn:message('date.format.datetime') }"
							});
						query('[name="fdHoldDuration"]')[0].value = ((finishDate.getTime()-startDate.getTime())/ 3600000).toFixed(1);//记录会议历时
					}*/
					var method = "save";
					var last_method = Com_GetUrlParameter(window.location.href, "method");
					if("edit"==last_method){
						method = "update";
					}
					Com_Submit(formObj, method);
					setTimeout(function(){
					//	location.href=util.formatUrl('/km/imeeting/mobile/index_book.jsp');
					},1000);
					
				}else{
					Tip.tip({icon:'mui mui-warn', text:msg['mobile.kmImeetingBook.conflict.tip'],width:'260'});
				}
			}));
		}
	};
	
	function setDateTimeValue(noValid){
		var holeDateStr = query('[name="fdHoldDate"]')[0].value;
		var finishDateStr = query('[name="fdFinishDate"]')[0].value;
		if(holeDateStr){
			//设置开始时间
			var holeDateArr = holeDateStr.split(" ");
			query('[name="startDate"]')[0].innerHTML = holeDateArr[0];
			query('[name="startTime"]')[0].innerHTML = holeDateArr[1];
		}
		if(finishDateStr){
			//设置结束时间
			var finishDateArr = finishDateStr.split(" ");
			query('[name="endDate"]')[0].innerHTML = finishDateArr[0];
			query('[name="endTime"]')[0].innerHTML = finishDateArr[1];
		}
		
		if(noValid === true) {
			return;
		}
		
		var placeComponent = registry.byId('placeComponent');
		validorObj._validation.validateElement(placeComponent);
	}
	
	
	function _compareTime(){
		var holeDateStr = query('[name="fdHoldDate"]')[0].value;
		var finishDateStr = query('[name="fdFinishDate"]')[0].value;
		var result=true;
		if( holeDateStr && finishDateStr){
			var startDate=locale.parse(holeDateStr,
				{
					selector : 'time',
					timePattern : "${ lfn:message('date.format.datetime') }"
				});
			var finishDate=locale.parse(finishDateStr,
				{
					selector : 'time',
					timePattern : "${ lfn:message('date.format.datetime') }"
				});
			if(dateClz.compare(finishDate,startDate) <= 0){
				result =  false;
			}
		}
		return result;
	}
	
	
	function _validateSelectTime(){
		var holeDateStr = query('[name="fdHoldDate"]')[0].value;
		var finishDateStr = query('[name="fdFinishDate"]')[0].value;
		if( holeDateStr && finishDateStr ){
			return true;
		}else{
			return false;
		}
		return false;
	}
	//校验最大使用时长
	function _validateUserTime(){
		var userTime = query('[name="fdPlaceUserTime"]'),
			fdPlaceId = query('[name="fdPlaceId"]');
		if(fdPlaceId[0].value && userTime[0].value){
			var holeDateStr = query('[name="fdHoldDate"]')[0].value;
			var finishDateStr = query('[name="fdFinishDate"]')[0].value;
			if( holeDateStr && finishDateStr){
				var startDate=locale.parse(holeDateStr,
					{
						selector : 'time',
						timePattern : "${ lfn:message('date.format.datetime') }"
					});
				var finishDate=locale.parse(finishDateStr,
					{
						selector : 'time',
						timePattern : "${ lfn:message('date.format.datetime') }"
					});
				if( parseFloat(userTime[0].value) == 0 ||  parseFloat(userTime[0].value) == 0.0){
					return true;
				} else if(dateClz.difference(startDate,finishDate,"millisecond") <= userTime[0].value * 3600 * 1000){
					return true;
				}else{
					return false;
				}
			}
		}
		return true;
	}
});
</script>