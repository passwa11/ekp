<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>

<script src="${LUI_ContextPath}/sys/time/resource/js/jquery.colorpicker.js" type="text/javascript"></script>
<link rel="stylesheet" href="${LUI_ContextPath}/sys/time/resource/css/colpick.css?s_cache=${MUI_Cache}" type="text/css"/>

<style>
	#colorpanel table {
		width: 100%;
	}
</style>


<script type="text/javascript">

	if(Com_Parameter){
		Com_Parameter.CalendarMinuteStep =1;
	}
Com_IncludeFile("doclist.js|jquery.js");

function delete_WorkTime(){
	DocList_DeleteRow();
	checkRestRow();
	calcWorkHour();
}

function add_WorkTime() {
	DocList_AddRow('TABLE_DocList');
	checkRestRow();
	calcWorkHour();
}

function _submited(method){
	var startTime = $('[name="fdStartTime"]').val() || '';
	var endTime = $('[name="fdEndTime"]').val() || '';

	if(startTime && endTime){
		startTime=Com_GetDate(startTime,'date',Com_Parameter.Date_format);
		endTime=Com_GetDate(endTime,'date',Com_Parameter.Date_format);
		var date = endTime.getTime() - startTime.getTime();
		if( date < 0){
			alert('<bean:message  bundle="sys-time" key="sysTimeWork.validate"/>');
			return false;
		}
	}else if(!startTime){
		alert('请填写开始时间');
		return false;
	}
    // 获取班次数据
    var ajaxGetAll = $.get('${LUI_ContextPath}/sys/time/sys_time_area/sysTimeArea.do?method=getDefine&fdId=${JsParam.sysTimeAreaId}');
    //var ajaxGetCommon = $.get('${LUI_ContextPath}/sys/time/sys_time_common_time/sysTimeCommonTime.do?method=commonTimeById');
    $.when(ajaxGetAll).then(function(all){
    	all = all[0] || [];
    	var colors = [];
    	if(all && all.length > 0){
			$.each(all, function(_, c) {
				colors.push(c.clazz.color);
			});
		}
		var startWeek = $('[name="fdWeekStartTime"]').val() || '';
		var endWeek = $('[name="fdWeekEndTime"]').val() || '';
		if(startWeek && endWeek){
			if(endWeek < startWeek){
				alert('<bean:message  bundle="sys-time" key="sysTimeWork.week.explanation"/>');
				return false;
			}
		}
		if($('input[name="timeType"]:checked').val() == '1') {
			if(!$('select[name="sysTimeCommonId"]').val()) {
				alert('请选择通用班次!');
				return false;
			}
			Com_Submit(document.sysTimeWorkForm, method);
		} else {
			var times = [];
			for(var i=0; true; i++){
				var workStartTime = document.getElementsByName("sysTimeWorkTimeFormList[" + i + "].fdWorkStartTime");
				var workEndTime = document.getElementsByName("sysTimeWorkTimeFormList[" + i + "].fdWorkEndTime");
				var workOverType = document.getElementsByName("sysTimeWorkTimeFormList[" + i + "].fdOverTimeType");

				var fdStartTime = document.getElementsByName("sysTimeWorkTimeFormList[" + i + "].fdStartTime");
				var fdEndOverTimeType = document.getElementsByName("sysTimeWorkTimeFormList[" + i + "].fdEndOverTimeType");
				var fdOverTime = document.getElementsByName("sysTimeWorkTimeFormList[" + i + "].fdOverTime");

				if(workStartTime.length==0){
					if(i==0){
						alert('<bean:message  bundle="sys-time" key="sysTimeWorkTime.time.null"/>');
						return false;
					}
					break;
				}
				var startTime = workStartTime[0].value;
				var endTime = workEndTime[0].value;
				var overType = workOverType[0].value;


				var beginTime = fdStartTime[0].value;
				var overTime = fdOverTime[0].value;
				var endOverType = fdEndOverTimeType[0].value;

				if(startTime == "" ){
					alert('<bean:message  bundle="sys-time" key="sysTimeWorkTime.fdWorkStartTime.validate"/>');
					return false;
				}
				if(endTime == "" ){
					alert('<bean:message  bundle="sys-time" key="sysTimeWorkTime.fdWorkEndTime.validate"/>');
					return false;
				}
				if(beginTime == "" ){
					alert('请选择最早打卡时间');
					return false;
				}
				if(overTime == "" ){
					alert('请选择最晚打卡时间');
					return false;
				}

				startTime = new Date('2018/01/01 ' + startTime);
				endTime = new Date('2018/01/01 ' + endTime);
				beginTime = new Date('2018/01/01 ' + beginTime);
				overTime = new Date('2018/01/01 ' + overTime);
				var isOverTime = false;
				if(overType == 2){
					endTime = endTime.setDate(endTime.getDate()+1);
					endTime = new Date(endTime);
					isOverTime = true;
				}
				if(endOverType == 2){
					//如果最晚时间是次日。则判断。最晚打卡时间，应该在最早打卡时间之前
					if(overTime >= beginTime){
						alert('最晚打卡时间应该在最早打卡时间之前');
						return false;
					}
					overTime = endTime.setDate(overTime.getDate()+1);
					overTime = new Date(overTime);
				}
				if(beginTime > startTime){
					//最早打卡时间不能大于开始打卡时间
					alert('最早打卡时间不能大于上班时间');
					return false;
				}else if(endTime > overTime){
					//最晚打卡时间小于结束时间
					alert('最晚打卡时间不能小于下班时间');
					return false;
				}

				time = endTime - startTime;
				if(time <= 0){
					alert('<bean:message  bundle="sys-time" key="sysTimeWorkTime.validate"/>');
					return false;
				}
				times.push({
					start: startTime,
					end: endTime,
					isOverTime: isOverTime
				});
			}
			//班次交错比较
			var i = 0, j = 0;
    		for(i; i < times.length - 1; i++) {
				var t1 = times[i];
    			for(j = i + 1; j < times.length; j++) {
    				var t2 = times[j];
    				if((t1.start > t2.start && t1.start < t2.end)
    						|| (t2.start > t1.start && t2.start < t1.end)) {
    					alert('<bean:message bundle="sys-time" key="sysTimePatchworkTime.time.compare"/>');
    					return false;
    				}
    			}
    		}
    		var onStart,offEnd;
    		var endOver = false;
    		for(var k = 0; k < times.length; k++){
    			var t1 = times[k];
    			onStart = onStart && t1.start > onStart ? onStart : t1.start;
				if(!(offEnd && t1.end < offEnd )){
					offEnd = t1.end;
					endOver = t1.isOverTime;
				}
    		}
    		if(endOver && onStart && offEnd){
    			offEnd = offEnd.setDate(offEnd.getDate()-1);
    			offEnd = new Date(offEnd);
    			if(offEnd >= onStart){
    				alert('<bean:message  bundle="sys-time" key="sysTimeWorkTime.validate"/>');
					return false;
    			}
    		}
			
			//自定义班次校验颜色重复
			var color = $('[name="fdTimeWorkColor"]').val();
			if('${JsParam.method}' == 'edit' && color == '${sysTimeWorkForm.fdTimeWorkColor}') {
				Com_Submit(document.sysTimeWorkForm, method);
				return true;
			} else if($.inArray(color, colors) > -1){
				alert('班次颜色已被使用！');
				return false;									
			}
			
			Com_Submit(document.sysTimeWorkForm, method);
		}

    });
}

function checkWeekCue(){
	var startWeek = document.getElementsByName("fdWeekStartTime")[0];
	var endWeek = document.getElementsByName("fdWeekEndTime")[0];
	if(startWeek.value !="" && endWeek.value !=""){
		var weekCue = document.getElementById("weekCue");
		if(endWeek.value < startWeek.value){
			//weekCue.innerText = "<bean:message  bundle="sys-time" key="sysTimeWork.week.explanation"/>";
			$(weekCue).text("<bean:message  bundle="sys-time" key="sysTimeWork.week.explanation"/>");
		}else{
			//weekCue.innerText = "您选择的日期范围为：";
			$(weekCue).text("<bean:message bundle="sys-time" key="sysTimeWork.week.text"/>：");
			if("textContent" in weekCue){
				for(var i=startWeek.value;i<=endWeek.value;i++){
					if(i=='1'){
						weekCue.textContent += "<bean:message key="date.weekDay0"/>";
					}else if(i=='2'){
						weekCue.textContent += "<bean:message key="date.weekDay1"/>";
					}else if(i=='3'){
						weekCue.textContent += "<bean:message key="date.weekDay2"/>";
					}else if(i=='4'){
						weekCue.textContent += "<bean:message key="date.weekDay3"/>";
					}else if(i=='5'){
						weekCue.textContent += "<bean:message key="date.weekDay4"/>";
					}else if(i=='6'){
						weekCue.textContent += "<bean:message key="date.weekDay5"/>";
					}else if(i=='7'){
						weekCue.textContent += "<bean:message key="date.weekDay6"/>";
					}
					if(endWeek.value - startWeek.value>=1&&i!=endWeek.value){
						weekCue.textContent += "、";
					}
				}
			}else{
				for(var i=startWeek.value;i<=endWeek.value;i++){
					if(i=='1'){
						weekCue.innerText += "<bean:message key="date.weekDay0"/>";
					}else if(i=='2'){
						weekCue.innerText += "<bean:message key="date.weekDay1"/>";
					}else if(i=='3'){
						weekCue.innerText += "<bean:message key="date.weekDay2"/>";
					}else if(i=='4'){
						weekCue.innerText += "<bean:message key="date.weekDay3"/>";
					}else if(i=='5'){
						weekCue.innerText += "<bean:message key="date.weekDay4"/>";
					}else if(i=='6'){
						weekCue.innerText += "<bean:message key="date.weekDay5"/>";
					}else if(i=='7'){
						weekCue.innerText += "<bean:message key="date.weekDay6"/>";
					}
					if(endWeek.value - startWeek.value>=1&&i!=endWeek.value){
						weekCue.innerText += "、";
					}
				}
			}
		}
	}
}

function timeComparer(t1, t2){
	return compareTime(t1.start,t2.start);
}
</script>

<html:form action="/sys/time/sys_time_work/sysTimeWork.do" onsubmit="return validateSysTimeWorkForm(this);">
<div id="optBarDiv">
	<c:if test="${sysTimeWorkForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="_submited('update')">
	</c:if>
	<c:if test="${sysTimeWorkForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="_submited('save')">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="_submited('saveadd')">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="top.close();">
</div>

<p class="txttitle"><bean:message  bundle="sys-time" key="table.sysTimeWork"/></p>

<center>
<table class="tb_normal" width=95%>
	<html:hidden property="fdId"/>
	<html:hidden property="sysTimeAreaId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-time" key="sysTimeWork.validTime"/>
		</td>
		<td width=85% colspan=3>
			<bean:message  bundle="sys-time" key="sysTimeWork.validTime.start"/>
			<xform:datetime property="fdStartTime" dateTimeType="date" required="true" htmlElementProperties="readonly='true'"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<bean:message  bundle="sys-time" key="sysTimeWork.validTime.end"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<xform:datetime property="fdEndTime" dateTimeType="date" htmlElementProperties="readonly='true'"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="txtstrong"><bean:message  bundle="sys-time" key="sysTimeWork.validTime.explanation"/></span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-time" key="sysTimeWork.week"/>
		</td>
		<td width="85%" colspan="3">
			<bean:message  bundle="sys-time" key="sysTimeWork.week.start"/>
			<sunbor:enums property="fdWeekStartTime" enumsType="common_week_type" elementType="select" htmlElementProperties="onchange=checkWeekCue();"/>&nbsp;&nbsp;
			<bean:message  bundle="sys-time" key="sysTimeWork.week.end"/>
			<sunbor:enums property="fdWeekEndTime" enumsType="common_week_type" elementType="select" htmlElementProperties="onchange=checkWeekCue();"/>&nbsp;&nbsp;
			<span id="weekCue" class="txtstrong"></span>
		</td>
	</tr>
	
	<tr>
	
		<td class="td_normal_title" width="15%">
			班次类型
		</td>
		<td width="85%" colspan="3">
			<c:choose>
				<c:when test="${sysTimeWorkForm.timeType != null && sysTimeWorkForm.timeType != ''}">
					<xform:radio property="timeType" showStatus="edit" onValueChange="handleChangeType" value="${sysTimeWorkForm.timeType }" >
						<xform:simpleDataSource value="1">通用</xform:simpleDataSource>
						<xform:simpleDataSource value="2">自定义</xform:simpleDataSource>
					</xform:radio>
				</c:when>
				<c:otherwise>
					<xform:radio property="timeType" showStatus="edit" onValueChange="handleChangeType" value="1" >
						<xform:simpleDataSource value="1">通用</xform:simpleDataSource>
						<xform:simpleDataSource value="2">自定义</xform:simpleDataSource>
					</xform:radio>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr id="commonClass">
		<td class="td_normal_title" width=15%>
			通用班次
		</td>
		<td width="85%" colspan="3">
			<xform:select property="sysTimeCommonId" showStatus="edit" onValueChange="handleChangeCommon" value="">
				<xform:beanDataSource serviceBean="sysTimeCommonTimeService" whereBlock="status='true' and type!='2' " />
			</xform:select>
		</td>
	</tr>
	<tr class="customClass" style="display: none;">
		<td class="td_normal_title" width=15%>
			班次名称
		</td>
		<td width="35%">
			<xform:text property="fdName" style="width:85%"/>
		</td>
		<td class="td_normal_title" width=15%>
			班次颜色
		</td>
		<td width="35%">
			<c:choose>
				<c:when test="${sysTimeWorkForm.fdTimeWorkColor != null and sysTimeWorkForm.fdTimeWorkColor != '' }">
					<input name="fdTimeWorkColor" value="${sysTimeWorkForm.fdTimeWorkColor}" type="hidden"/>
				</c:when>
				<c:otherwise>
					<input name="fdTimeWorkColor" value="#5484ed" type="hidden"/>
				</c:otherwise>
			</c:choose>
			<ul class="clrfix color_ul">
                 <li class="select">
                 	<c:choose>
                 		<c:when test="${sysTimeWorkForm.fdTimeWorkColor != null and sysTimeWorkForm.fdTimeWorkColor != '' }">
                 			<a style="background-color: ${sysTimeWorkForm.fdTimeWorkColor};"></a>
                 		</c:when>
                 		<c:otherwise>
		                 	<a style="background-color: #5484ed;"></a>
                 		</c:otherwise>
                 	</c:choose>
                 </li>
                 <li class="line"></li>
                 <li class="color_1"></li>
                 <li class="color_2"></li>
                 <li class="color_3"></li>
                 <li class="color_4"></li>
                 <li class="color_5"></li>
                 <li class="color_6"></li>
                 <li class="color_7"></li>
                 <li class="color_8"></li>
                 <li class="color_9"></li>
                 <li class="color_10"></li>
                 <li class="color_11"></li>
			</ul>
			<div class="selfdef"><a id="selfdef">自定义</a></div>			
			<script type="text/javascript">
			    $(function () {
			        $(".color_ul li").each(function () {
			        	if($(this).prop("className") != 'select' && $(this).prop("className") != 'line'){
			        		 $(this).click(function () {
				                var color = colorRGB2Hex($(this).css("background-color"));
				                $(".color_ul li.select a").css("background-color", color);
				                $("input[name='fdTimeWorkColor']").val(color);
				            });
			        	}
			           
			        });
			        $('#selfdef').colorpicker({
			            ishex: true, //是否使用16进制颜色值
			            fillcolor: false,  //是否将颜色值填充至对象的val中
			            target: null, //目标对象
			            event: 'click', //颜色框显示的事件
			            success: function (o, color) {
			            	
			                $(".color_ul li.select a").css("background-color", color);
			                if(color == '#FFFFFF' || color == '#FFF' || color == '#ffffff' ||color == '#fff'){
			                	 $("input[name='fdTimeWorkColor']").val("");
			                }else{
			                	$("input[name='fdTimeWorkColor']").val(color);
			                }
			            },
			        	reset:function(o){
			        		 $(".color_ul li.select a").css("background-color", "#ffffff");
				             $("input[name='fdTimeWorkColor']").val("");
			            }
			        });
			    });
			    function colorRGB2Hex(color) {
			        var rgb = color.split(',');
			        var r = parseInt(rgb[0].split('(')[1]);
			        var g = parseInt(rgb[1]);
			        var b = parseInt(rgb[2].split(')')[0]);
			     
			        var hex = "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
			        return hex;
			     }
			</script>
		</td>
	</tr>

	

	<tr>
		<td colspan=4>
			
			<table class="tb_normal" width=100% id="commonDetail" width="100%">
				<tr style="text-align: center;"><td><span class="txtstrong">请选择通用班次</span></td></tr>
			</table>
		
			<table class="tb_normal" width=100% id="TABLE_DocList" style="display: none;">
				<tr>
					<td class="td_normal_title" align="center" width=5%>
						<img src="${KMSS_Parameter_StylePath}icons/add.gif" onclick="add_WorkTime();" style="cursor:pointer">
					</td>
					<td class="td_normal_title" align="center" width=20%>
						<bean:message  bundle="sys-time" key="sysTimeWorkTime.fdWorkStartTime"/>
					</td>
					<td class="td_normal_title" align="center" width=26%>
						<bean:message  bundle="sys-time" key="sysTimeWorkTime.fdWorkEndTime"/>
					</td>
						<%-- 最早打卡 --%>
					<td  class="td_normal_title" align="center" width=20%>
							${ lfn:message('sys-time:sysTimeCommonTime.earliest.startTime') }
					</td>
					<td  class="td_normal_title" align="center" width=26%>
							${ lfn:message('sys-time:sysTimeCommonTime.latest.endTime') }
					</td>
				</tr>
				<tr KMSS_IsReferRow="1" style="display:none" class="time-row">
					<td>
						<center>
						<img src="${KMSS_Parameter_StylePath}icons/delete.gif"
									onclick="delete_WorkTime();" style="cursor:pointer">
						</center>
					</td>
					<td>	
					     <input type="hidden" class="inputSgl" name="sysTimeWorkTimeFormList[!{index}].fdWorkId" value = "${sysTimeWorkForm.fdId}"/>
					     <input type="hidden" class="inputSgl" name="sysTimeWorkTimeFormList[!{index}].fdId" value = ""/>
					     <input type="text" onchange="window.calcWorkHour();" class="inputSgl start-time" style="width:70%" name="sysTimeWorkTimeFormList[!{index}].fdWorkStartTime" value = "" readonly/>
					     <a href="#" onclick="selectTime('sysTimeWorkTimeFormList[!{index}].fdWorkStartTime');" styleClass="inputsgl" >
						 <bean:message key="dialog.selectTime" /></a>
						 <span class="txtstrong">*</span>
					</td>
					<td>
					    <xform:select
									property="sysTimeWorkTimeFormList[!{index}].fdOverTimeType"
									showPleaseSelect="false"
									title="${ lfn:message('sys-time:sysTimeWork.hbmEndTime') }"
									onValueChange="window.calcWorkHour();"
									style="width:30%;height:32px;margin-right:7px;">
									<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
									<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
						 </xform:select>
						 <input type="text" onchange="window.calcWorkHour();" class="inputSgl end-time" style="width:40%" name="sysTimeWorkTimeFormList[!{index}].fdWorkEndTime" value = "" readonly/>
					     <a href="#" onclick="selectTime('sysTimeWorkTimeFormList[!{index}].fdWorkEndTime');" styleClass="inputsgl" >
						 <bean:message key="dialog.selectTime" /></a>
						 <span class="txtstrong">*</span>
					</td>
						<%-- 最早打卡 --%>
					<td style="width: 120px">
						<input type="text"  class="inputSgl begin-start-time" style="width:70%"
							   name="sysTimeWorkTimeFormList[!{index}].fdStartTime" value = "" readonly/>
						<a href="#" onclick="selectTime('sysTimeWorkTimeFormList[!{index}].fdStartTime');" styleClass="inputsgl" >
							<bean:message key="dialog.selectTime" /></a>
						<span class="txtstrong">*</span>
					</td>
						<%-- 最晚打卡 --%>
					<td style="">
						<xform:select
								property="sysTimeWorkTimeFormList[!{index}].fdEndOverTimeType"
								showPleaseSelect="false"
								title="${ lfn:message('sys-time:sysTimeWork.hbmEndTime') }"
								onValueChange="window.calcWorkHour();"
								style="width:30%;height:32px;margin-right:7px;"
						>
							<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
							<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
						</xform:select>
						<input type="text"  class="inputSgl end-over-time" style="width:40%"
							   name="sysTimeWorkTimeFormList[!{index}].fdOverTime" value = "" readonly/>
						<a href="#" onclick="selectTime('sysTimeWorkTimeFormList[!{index}].fdOverTime');" styleClass="inputsgl" >
							<bean:message key="dialog.selectTime" /></a>
						<span class="txtstrong">*</span>
					</td>

				</tr>
				<c:forEach items="${sysTimeWorkForm.sysTimeWorkTimeFormList}" var="sysTimeWorkTimeForm" varStatus="vstatus">
				<tr KMSS_IsContentRow="1" class="time-row">
					<td>
						<center>
						<img src="${KMSS_Parameter_StylePath}icons/delete.gif"
									onclick="delete_WorkTime();" style="cursor:pointer">
						</center>
					</td>
					<td>
					    <input type="hidden" name="sysTimeWorkTimeFormList[${vstatus.index}].fdWorkId" value = "${sysTimeWorkForm.fdId}"/>
					    <input type="hidden" name="sysTimeWorkTimeFormList[${vstatus.index}].fdId" value = "${sysTimeWorkTimeForm.fdId}"/>
						<input type="text" onchange="window.calcWorkHour();" class="inputSgl start-time" style="width:70%" name="sysTimeWorkTimeFormList[${vstatus.index}].fdWorkStartTime" value = "${sysTimeWorkTimeForm.fdWorkStartTime}" readonly/>
					    <a href="#" onclick="selectTime('sysTimeWorkTimeFormList[${vstatus.index}].fdWorkStartTime');" styleClass="inputsgl" >
						<bean:message key="dialog.selectTime" /></a>
						<span class="txtstrong">*</span>
					</td>	
					<td>
					    <xform:select
						    property="sysTimeWorkTimeFormList[${vstatus.index}].fdOverTimeType"
						    showPleaseSelect="false"
						    title="${ lfn:message('sys-time:sysTimeWork.hbmEndTime') }"
						    onValueChange="window.calcWorkHour();"
						    style="width:30%;height:32px;margin-right:7px;">
						    <xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
						    <xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
						 </xform:select> 
						 <input type="text" onchange="window.calcWorkHour();" class="inputSgl end-time" style="width:40%" name="sysTimeWorkTimeFormList[${vstatus.index}].fdWorkEndTime" value = "${sysTimeWorkTimeForm.fdWorkEndTime}" readonly/>
					     <a href="#" onclick="selectTime('sysTimeWorkTimeFormList[${vstatus.index}].fdWorkEndTime');" styleClass="inputsgl" >
						 <bean:message key="dialog.selectTime" /></a>
						 <span class="txtstrong">*</span>
					</td>

						<%-- 最早打卡 --%>
					<td >
						<input onchange="window.calcWorkHour();" type="text" class="inputSgl begin-start-time" style="width:70%"
							   name="sysTimeWorkTimeFormList[${vstatus.index}].fdStartTime"
							   value = "${sysTimeWorkTimeForm.fdStartTime}" readonly/>
						<a href="#" onclick="selectTime('sysTimeWorkTimeFormList[${vstatus.index}].fdStartTime');" styleClass="inputsgl" >
							<bean:message key="dialog.selectTime" /></a>
						<span class="txtstrong">*</span>
					</td>
						<%-- 最晚打卡 --%>
					<td >
						<xform:select
								property="sysTimeWorkTimeFormList[${vstatus.index}].fdEndOverTimeType"
								showPleaseSelect="false"
								title="${ lfn:message('sys-time:sysTimeWork.hbmEndTime') }"
								onValueChange="window.calcWorkHour();"
								style="width:30%;height:32px;margin-right:7px;"
						>
							<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
							<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
						</xform:select>
						<input type="text"  class="inputSgl end-over-time" style="width:40%"
							   value = "${sysTimeWorkTimeForm.fdOverTime}"
							   name="sysTimeWorkTimeFormList[${vstatus.index}].fdOverTime" value = "" readonly/>
						<a href="#" onclick="selectTime('sysTimeWorkTimeFormList[${vstatus.index}].fdOverTime');" styleClass="inputsgl" >
							<bean:message key="dialog.selectTime" /></a>
						<span class="txtstrong">*</span>
					</td>

				</tr>	
				</c:forEach>
			</table>
		</td>
	</tr>
	<!-- 休息时间 -->
	<tr class="customClass" style="display: none;" id="restRow">
		<td class="td_normal_title" width="15%">
			休息开始时间
		</td>
		<td width="35%">
			<div id="_xform_fdRestStartTime" _xform_type="datetime">
				<xform:select
						property="sysTimeCommonTimeForm.fdRestStartType"
						showPleaseSelect="false"
						title="休息开始时间"
						style="width:30%;margin-right:7px;"
						showStatus="edit"
						onValueChange="window.calcWorkHour"
				>
					<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
					<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
				</xform:select>
				<xform:datetime onValueChange="window.calcWorkHour" property="sysTimeCommonTimeForm.fdRestStartTime" subject="休息开始时间"
								validators="checkStartEnd checkWithWorkTime" showStatus="edit" dateTimeType="time" style="width:60%;" />
			</div>
		</td>
		<td class="td_normal_title" width="15%">
			休息结束时间
		</td>
		<td width="35%">
			<div id="_xform_fdRestEndTime" _xform_type="datetime">
				<xform:select
						property="sysTimeCommonTimeForm.fdRestEndType"
						showPleaseSelect="false"
						title="休息结束时间"
						style="width:30%;margin-right:7px;"
						showStatus="edit"
						onValueChange="window.calcWorkHour"
				>
					<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
					<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
				</xform:select>
				<xform:datetime onValueChange="window.calcWorkHour" property="sysTimeCommonTimeForm.fdRestEndTime" subject="休息结束时间"
								validators="checkStartEnd checkWithWorkTime" showStatus="edit" dateTimeType="time" style="width:60%;" />
			</div>
		</td>
	</tr>

	<!-- 总工时 -->
	<tr class="customClass" style="display: none;">
		<td class="td_normal_title" width="15%">
			总工时（小时）
		</td>
		<td width="35%">
			<xform:text property="fdWorkHour" showStatus="readOnly" style="width:45%;"/>

				<%--统计时按多少天算--%>
			<xform:select showStatus="edit" property="fdTotalDay" showPleaseSelect="false"
						  title="${ lfn:message('sys-time:sysTimeCommonTime.total.day.one') }"  style="width:35%;height:30px;margin-right:7px;"
						  value="${sysTimeCommonTimeForm.fdTotalDay }">
				<xform:simpleDataSource value='1.0'>${ lfn:message('sys-time:sysTimeCommonTime.total.day.one') }</xform:simpleDataSource>
				<xform:simpleDataSource value='0.5'>${ lfn:message('sys-time:sysTimeCommonTime.total.day.half') }</xform:simpleDataSource>
			</xform:select>

		</td>
		<td class="td_normal_title" width="15%">
		</td>
		<td  >
		</td>

	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-time" key="sysTimeWork.docCreatorId"/>
		</td><td width=35%>
			${sysTimeWorkForm.docCreatorName}
	</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-time" key="sysTimeWork.docCreateTime"/>
		</td><td width=35%>
			${sysTimeWorkForm.docCreateTime}
	</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
</html:form>

<script>

	Com_IncludeFile("calendar.js");
	checkWeekCue();

	//计算工时
	window.calcWorkHour = function() {
		
		var workTime = 0;
		
		$('.time-row').each(function(index) {
			var startTime = $(this).find('.start-time').val();
			var endTime = $(this).find('.end-time').val();
			var overTime = $('select[name="sysTimeWorkTimeFormList['+index+'].fdOverTimeType"]').val();
			
			if(startTime && /\d\d:\d\d/.test(startTime) && endTime && /\d\d:\d\d/.test(endTime)) {
				
				startTime = new Date('2018/01/01 ' + startTime);
				endTime = new Date('2018/01/01 ' + endTime);
				if(overTime == 2){
					endTime = endTime.setDate(endTime.getDate()+1);
					endTime = new Date(endTime);
				}
				
				if(endTime > startTime) {
					workTime += (endTime - startTime);
				}
				
			} 
			
		});
		
		var restTime = 0;
		var restStartTime = $('input[name="sysTimeCommonTimeForm.fdRestStartTime"]').val();
		var restEndTime = $('input[name="sysTimeCommonTimeForm.fdRestEndTime"]').val();
		var fdRestStartType = $('select[name="sysTimeCommonTimeForm.fdRestStartType"]:enabled').val();
		var fdRestEndType = $('select[name="sysTimeCommonTimeForm.fdRestEndType"]:enabled').val();
		if(restStartTime && /\d\d:\d\d/.test(restStartTime) && restEndTime && /\d\d:\d\d/.test(restEndTime)) {
			restStartTime = new Date('2018/01/01 ' + restStartTime);
			restEndTime = new Date('2018/01/01 ' + restEndTime);

			if(fdRestStartType ==2){
				restStartTime = restStartTime.setDate(restStartTime.getDate()+1);
				restStartTime = new Date(restStartTime);
			}
			if(fdRestEndType ==2){
				restEndTime = restEndTime.setDate(restEndTime.getDate()+1);
				restEndTime = new Date(restEndTime);
			}
			if(restEndTime > restStartTime) {
				restTime += (restEndTime - restStartTime);
			}
		} 
		
		if(window.checkRestRow()) {
    		var t = workTime - restTime < 0 ? 0 : (workTime - restTime);
			$('input[name="fdWorkHour"]').val((t / 3600000).toFixed(1));
		} else {
			$('input[name="fdWorkHour"]').val((workTime / 3600000).toFixed(1));
		}
	}
	
	
	//校验休息开始结束时间是否显示
	window.checkRestRow = function() {
		
		if($('[name="timeType"]:checked').val() == '1') {
			hideAndDisabled('restRow');
			return false;
		}

		if($('.time-row').get().length > 1) {
			hideAndDisabled('restRow');
			return false;	
		}
		
		showAndAbled('restRow');
		return true;
	}
	
	var showAndAbled = function(id) {
		var parentDom = $('#' + id);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		if(childInputs)
			childInputs.removeAttr('disabled');
		parentDom.show();
	};
	
	var hideAndDisabled= function(id) {
		var parentDom = $('#' + id);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		if(childInputs)
			childInputs.prop('disabled', 'disabled');
		parentDom.hide();
	};
	
	var validation = $KMSSValidation();
	
	//自定义必填校验器:校验休息开始结束时间
	validation.addValidator('checkStartEnd','休息开始时间不能晚于休息结束时间',function(v, e, o){

		if(window.checkRestRow()) {

			var restStartTime = $('input[name="sysTimeCommonTimeForm.fdRestStartTime"]').val();
			var restEndTime = $('input[name="sysTimeCommonTimeForm.fdRestEndTime"]').val();
			var fdRestStartType = $('select[name="sysTimeCommonTimeForm.fdRestStartType"]:enabled').val();
			var fdRestEndType = $('select[name="sysTimeCommonTimeForm.fdRestEndType"]:enabled').val();
			if(restStartTime && restEndTime) {

				restStartTime = new Date('2018/01/01 ' + restStartTime);
				if(fdRestStartType ==2){
					restStartTime.setDate(restStartTime.getDate()+1);
				}

				restEndTime = new Date('2018/01/01 ' + restEndTime);
				if(fdRestEndType ==2){
					restEndTime.setDate(restEndTime.getDate()+1);
				}
				return !(restStartTime >= restEndTime);
			}
		}
		return true;
	});
	
	//自定义必填校验器:校验休息开始结束时间是否在工作时间范围内
	validation.addValidator('checkWithWorkTime','休息时间不能在工作时间范围外',function(v, e, o){
		if(window.checkRestRow()) {
			
			var restStartTime = $('input[name="sysTimeCommonTimeForm.fdRestStartTime"]').val();
			var restEndTime = $('input[name="sysTimeCommonTimeForm.fdRestEndTime"]').val();
			var workStartTime = $('input[name="sysTimeWorkTimeFormList[0].fdWorkStartTime"]').val();
			var workEndTime = $('input[name="sysTimeWorkTimeFormList[0].fdWorkEndTime"]').val();
			var overTimeType = $('select[name="sysTimeWorkTimeFormList[0].fdOverTimeType"]').val();
			var fdRestStartType = $('select[name="sysTimeCommonTimeForm.fdRestStartType"]:enabled').val();
			var fdRestEndType = $('select[name="sysTimeCommonTimeForm.fdRestEndType"]:enabled').val();

			if(restStartTime && restEndTime && workStartTime && workEndTime) {
				restStartTime = new Date('2018/01/01 ' + restStartTime);
				restEndTime = new Date('2018/01/01 ' + restEndTime);
				workStartTime = new Date('2018/01/01 ' + workStartTime);
				workEndTime = new Date('2018/01/01 ' + workEndTime);
				if(overTimeType == 2){
					workEndTime = workEndTime.setDate(workEndTime.getDate()+1);
					workEndTime = new Date(workEndTime);
				}
				if(fdRestStartType == 2){
					restStartTime = restStartTime.setDate(restStartTime.getDate()+1);
					restStartTime =new Date(restStartTime);
				}
				if(fdRestEndType == 2){
					restEndTime = restEndTime.setDate(restEndTime.getDate()+1);
					restEndTime =new Date(restEndTime);
				}

				return (restStartTime >= workStartTime && restStartTime <= workEndTime
						&& restEndTime >= workStartTime && restEndTime <= workEndTime);
			
			}
			
		}
		
		return true;
	});
	
	
	var commonClass = $('#commonClass');
	var commonDetail = $('#commonDetail');
	
	var customClass = $('.customClass');
	var customDetail = $('#TABLE_DocList');
	
	window.handleChangeType = function(value) {
		if(value == '1') {
			commonClass.show();
			commonDetail.show();
			
			customClass.hide();
			customDetail.hide();
		} else {
			commonClass.hide();
			commonDetail.hide();
			
			customClass.show();
			customDetail.show();	
		}
		
		checkRestRow();
		calcWorkHour();
	}
	
	window.handleChangeCommon = function(value) {
		commonDetail.empty();
		if(!value) {
			commonDetail.html('<tr style="text-align: center;"><td><span class="txtstrong">请选择通用班次</span></td></tr>');
		} else {

			$('<tr/>')
				.append(
					$('<td class="td_normal_title" align="center" width="25%">上班时间</td>')
				)
				.append(
					$('<td class="td_normal_title" align="center" width="25%">下班时间</td>')
				).append(
					$('<td class="td_normal_title" align="center" width="25%">最早打卡时间</td>')
			)
					.append(
							$('<td class="td_normal_title" align="center" width="25%">最晚打卡时间</td>')
					).appendTo(commonDetail);
			
			$.getJSON('${LUI_ContextPath}/sys/time/sys_time_common_time/sysTimeCommonTime.do?method=commonTimeById&fdId=' + value).then(function(res) {
				
				var detail = res[0];
				if(detail) {
					
					$.each(detail.times || [], function(_, d) {
						var str = '(${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type1") })';
						if(d.overTimeType == 2){
							str = '(${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type2") })';
						}

						var str2 = '(${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type1") })';
						if(d.endOverTimeType == 2){
							str2 = '(${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type2") })';
						}

						$('<tr/>').append($('<td align="center">' + d.start + '</td>'))
								.append($('<td align="center">' + d.end + ' ' + str + '</td>'))
								.append($('<td align="center">' + (d.beginTime ? d.beginTime:' ') +'</td>'))
								.append($('<td align="center">' + (d.overTime ? (d.overTime + ' ' + str2) : ' ') + '</td>'))
								.appendTo(commonDetail);
						
					});
					
				}

			});
			
		}
	}
	
	
	// 编辑状态下初始化
	if('${JsParam.method}' == 'edit') {
		if('${sysTimeWorkForm.timeType}' == '1') {
			handleChangeType('1');
			if('${sysTimeWorkForm.sysTimeCommonId}') {
				handleChangeCommon('${sysTimeWorkForm.sysTimeCommonId}');
			}
		} else {
			handleChangeType('2');
		}
	} else if("${JsParam.method}" == "add") {
		Com_AddEventListener(window, 'load', function(){ setTimeout(function() {DocList_AddRow('TABLE_DocList');}, 500);});
	}
	
	
</script>
<html:javascript formName="sysTimeWorkForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>