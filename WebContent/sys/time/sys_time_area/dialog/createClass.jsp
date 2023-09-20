<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">

	<%-- 样式 --%>
	<template:replace name="head">

		<style>
			.class-container {
				position: absolute;
				left: 0;
				top: 0;
				right: 0;
				bottom: 0;
			}
			
			.class-main {
				padding-top: 8px;
				position: absolute;
				left: 0;
				top: 0;
				right: 0;				
				bottom: 44px;
				overflow: auto;
			}
			
			.class-bottom {
				position: absolute;
				background-color: white;
				left: 0;
				right: 0;
				bottom: 0;
				padding: 8px;
				text-align: center;
			}
		</style>

		<script type="text/javascript">
			Com_IncludeFile("doclist.js|jquery.js");
		</script>
		<script src="${LUI_ContextPath}/sys/time/resource/js/jquery.colorpicker.js" type="text/javascript"></script>
		<link rel="stylesheet" href="${LUI_ContextPath}/sys/time/resource/css/colpick.css?s_cache=${MUI_Cache}" type="text/css"/>
		
	</template:replace>

	<%--内容区--%>
	<template:replace name="content">
		<div class="class-container">
			<div class="class-main">

				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width="15%">
							班次名称
						</td>
						<td width="85%" colspan="3">
							<input class="inputsgl" type="text" name="name" />
							<span class="txtstrong">*</span>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">
							班次颜色
						</td>
						<td width="85%" colspan="3">
							<input name="color" value="#5484ed" type="hidden"/>
							<ul class="clrfix color_ul">
				                 <li class="select">
									<a style="background-color: #5484ed;"></a>
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
							<div class="selfdef"><a id="selfdef">自定义</a><span class="txtstrong">*</span></div>			
							<script type="text/javascript">
							    $(function () {
							        $(".color_ul li").each(function () {
							        	if($(this).prop("className") != 'select' && $(this).prop("className") != 'line'){
							        		 $(this).click(function () {
								                var color = colorRGB2Hex($(this).css("background-color"));
								                $(".color_ul li.select a").css("background-color", color);
								                $("input[name='color']").val(color);
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
							                	 $("input[name='color']").val("");
							                }else{
							                	$("input[name='color']").val(color);
							                }
							            },
							        	reset:function(o){
							        		 $(".color_ul li.select a").css("background-color", "#ffffff");
								             $("input[name='color']").val("");
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
							<table class="tb_normal" width=100% id="TABLE_DocList">
								<tr >
									<td class="td_normal_title" align="center" width=5%>
										<img src="${KMSS_Parameter_StylePath}icons/add.gif" onclick="handleAddTimeRow();" style="cursor:pointer">
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
								<tr KMSS_IsReferRow="1" style="display:none" class="time time-row">
									<td>
										<center>
											<img src="${KMSS_Parameter_StylePath}icons/delete.gif" onclick="deleteItem();" style="cursor:pointer">
										</center>
									</td>
									<td>	
									     <input type="text" class="inputSgl start" style="width:40%" name="times[!{index}].start" value = "" readonly/>
									     <a href="#" onclick="selectTime('times[!{index}].start');" styleClass="inputsgl" >
										 <bean:message key="dialog.selectTime" /></a>
										 <span class="txtstrong">*</span>
									</td>
									<td>
									     <select name="times[!{index}].overTimeType" title="结束时间" style="width:30%;height:32px;margin-right:7px;" class="inputsgl overType">
									     <option value="1">${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type1") }</option>
									     <option value="2">${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type2") }</option>
									     </select>
									     <input type="text"   class="inputSgl end" style="width:35%"   name="times[!{index}].end" value = "" readonly/>
									     <a href="#" onclick="selectTime('times[!{index}].end');" styleClass="inputsgl" >
										 <bean:message key="dialog.selectTime" /></a>
										 <span class="txtstrong">*</span>
									</td>
										<%-- 最早打卡 --%>
									<td >
										<input type="text" class="inputSgl begin-start-time" style="width:40%" name="times[!{index}].fdStartTime" value = "" readonly/>
										<a href="#" onclick="selectTime('times[!{index}].fdStartTime');" styleClass="inputsgl" >
											<bean:message key="dialog.selectTime" /></a>
										<span class="txtstrong">*</span>
									</td>
										<%-- 最晚打卡 --%>
									<td >
										<select name="times[!{index}].fdEndOverTimeType" title="结束时间" style="width:30%;height:32px;margin-right:7px;" class="inputsgl endOverType">
											<option value="1">${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type1") }</option>
											<option value="2">${ lfn:message("sys-time:sysTimeWork.fdOverTimeType.type2") }</option>
										</select>

										<input type="text" class="inputSgl end-over-time" style="width:35%" name="times[!{index}].fdOverTime" value = "" readonly/>
										<a href="#" onclick="selectTime('times[!{index}].fdOverTime');" styleClass="inputsgl" >
											<bean:message key="dialog.selectTime" /></a>
										<span class="txtstrong">*</span>
									</td>
								</tr>
								
								<!--  
								<tr KMSS_IsContentRow="1" class="time">
									<td>
										<center>
											<img src="${KMSS_Parameter_StylePath}icons/delete.gif" onclick="deleteItem();" style="cursor:pointer">
										</center>
									</td>
									<td>	
									     <input type="text" class="inputSgl start" style="width:70%" name="times[0].start" value = "" readonly/>
									     <a href="#" onclick="selectTime('times[0].start');" styleClass="inputsgl" >
										 <bean:message key="dialog.selectTime" /></a>
										 <span class="txtstrong">*</span>
									</td>
									<td>
									     <input type="text"   class="inputSgl end" style="width:70%"   name="times[0].end" value = "" readonly/>
									     <a href="#" onclick="selectTime('times[0].end');" styleClass="inputsgl" >
										 <bean:message key="dialog.selectTime" /></a>
										 <span class="txtstrong">*</span>
									</td>					
								</tr>
								-->
							</table>
						</td>
					</tr>
					<!-- 休息时间 -->
					<tr id="restRow">
						<td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:sysTimeCommonTime.start.time.lunch.break') }
						</td>
						<td width="35%">
							<div id="_xform_fdRestStartTime" _xform_type="datetime">
								<xform:select
										property="fdRestStartType"
										showPleaseSelect="false"
										title="休息开始时间"
										style="width:30%;margin-right:7px;"
										showStatus="edit"
								>
									<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
									<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
								</xform:select>
								<xform:datetime  property="fdRestStartTime" subject="休息开始时间"
												 showStatus="edit"
												 dateTimeType="time" style="width:60%;" />
							</div>
						</td>
						<td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:sysTimeCommonTime.end.lunch.break') }
						</td>
						<td width="35%">
							<div id="_xform_fdRestEndTime" _xform_type="datetime">
								<xform:select
										property="fdRestEndType"
										showPleaseSelect="false"
										title="休息开始时间"
										style="width:30%;margin-right:7px;"
										showStatus="edit"
								>
									<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
									<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
								</xform:select>
								<xform:datetime  property="fdRestEndTime" subject="休息结束时间"
												 showStatus="edit"
												 dateTimeType="time" style="width:60%;" />
							</div>
						</td>
					</tr>

					<tr>
						<td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:sysTimeCommonTime.total.day') }
						</td>
						<td colspan="3" width="85%">
								<%--统计时按多少天算--%>
							<xform:select showStatus="edit" property="fdTotalDay" showPleaseSelect="false"
										  title="${ lfn:message('sys-time:sysTimeCommonTime.total.day.one') }"
										  style="width:35%;height:30px;margin-right:7px;">
								<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeCommonTime.total.day.one') }</xform:simpleDataSource>
								<xform:simpleDataSource value='0.5'>${ lfn:message('sys-time:sysTimeCommonTime.total.day.half') }</xform:simpleDataSource>
							</xform:select>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="class-bottom lui_dialog_buttons_container">
				<div onclick="window.handleSubmit();" class="lui-component lui_widget_btn lui_toolbar_btn_def" style="display: inline-block; margin: 0px 10px;"><div class="lui_toolbar_btn_l lui_toolbar_m" data-lui-mark="toolbar_button_inner" style="text-align: center;"><div class="lui_toolbar_btn_r"><div class="lui_toolbar_btn_c" data-lui-mark="toolbar_button_content"><div id="lui-id-27" class="lui-component lui_widget_btn_txt" >确定</div></div></div></div></div>
				<div onclick="window.handleCancel();" class="lui-component lui_widget_btn lui_toolbar_btn_gray" style="display: inline-block; margin: 0px 10px;"><div class="lui_toolbar_btn_l lui_toolbar_m" data-lui-mark="toolbar_button_inner" style="text-align: center;"><div class="lui_toolbar_btn_r"><div class="lui_toolbar_btn_c" data-lui-mark="toolbar_button_content"><div id="lui-id-29" class="lui-component lui_widget_btn_txt" >取消</div></div></div></div></div>
			</div>
		</div>
		
		<script>
			Com_IncludeFile("calendar.js");
					
			seajs.use(['lui/dialog'], function(dialog) {
				
				window.deleteItem = function() {
					DocList_DeleteRow();
					window._checkRestRow();
				}
				window.handleAddTimeRow = function() {
					DocList_AddRow('TABLE_DocList');
					window._checkRestRow();
				}
				window.addItem = function(index, time) {
					DocList_AddRow('TABLE_DocList');
					if(time && time.start) {
						$('input[name="times['+index+'].start"]').val(time.start);
					}
					if(time && time.overTimeType) {
						$('select[name="times['+index+'].overTimeType"]').val(time.overTimeType);
						// selectOverType.val(time.overTimeType);
					}
					<%--//结束时间--%>
					if(time && time.end) {
						$('input[name="times['+index+'].end"]').val(time.end);
					}
					<%--//最早打卡时间--%>
					if(time && time.fdStartTime) {
						$('input[name="times['+index+'].fdStartTime"]').val(time.fdStartTime);
					}
					<%--//最晚打卡时间--%>
				 	if(time && time.fdEndOverTimeType) {
						$('select[name="times['+index+'].fdEndOverTimeType"]').val(time.fdEndOverTimeType);
				 	}
				 	if(time && time.fdOverTime) {
						$('input[name="times['+index+'].fdOverTime"]').val(time.fdOverTime);
 					}
					window._checkRestRow();
				}
				
				// 校验数据
				window.checkData = function(data) {
					if(!data.name) {
						dialog.alert('请填写班次名称！');
						return false;
					}
					if(!data.color) {
						dialog.alert('请选择班次颜色！');
						return false;
					}
					if(!data.times || data.times.length < 1) {
						dialog.alert('请设置班次时间！');
						return false;
					}
					var t1, t2, i, j, l = data.times.length, times = data.times;
					
					for(i = 0; i < l; i++) {
						t1 = times[i];
						if(!t1.start || !t1.end|| !t1.fdStartTime|| !t1.fdOverTime) {
							dialog.alert('班次时间设置有误！');
							return;
						}
						var tStart = new Date('2018/01/01 ' + t1.start);
						var tEnd = new Date('2018/01/01 ' + t1.end);
						//最早最晚打卡时间
						var fdStartTime = new Date('2018/01/01 ' + t1.fdStartTime);
						var fdOverTime = new Date('2018/01/01 ' + t1.fdOverTime);
						var check =true;
						if(t1.overTimeType == 2){
							tEnd = tEnd.setDate(tEnd.getDate()+1);
							tEnd = new Date(tEnd);
						}
						if(tStart>=tEnd) {
							check =false;
						}
						if(t1.fdEndOverTimeType == 2){
							//如果最晚时间是次日。则判断。最晚打卡时间，应该在最早打卡时间之前
							if(fdOverTime >= fdStartTime){
								check = false;
							}
							fdOverTime = tEnd.setDate(fdOverTime.getDate()+1);
							fdOverTime = new Date(fdOverTime);
						}
						if(fdStartTime > tStart){
							//最早打卡时间大于开始时间
							check = false;
						}else if(tEnd > fdOverTime){
							//最晚打卡时间小于结束时间
							check = false;
						}
						if(!check){
							dialog.alert('班次时间设置有误！');
							return;
						}
					}
					if(check) {
						for (i = 0; i < l - 1; i++) {
							t1 = times[i];
							for (j = i + 1; j < l; j++) {
								t2 = times[j];

								var start1 = new Date('2018/01/01 ' + t1.start);
								var end1 = new Date('2018/01/01 ' + t1.end);
								if (t1.overTimeType == 2) {
									end1 = end1.setDate(end1.getDate() + 1);
									end1 = new Date(end1);
								}
								var start2 = new Date('2018/01/01 ' + t2.start);
								var end2 = new Date('2018/01/01 ' + t2.end);
								if (t2.overTimeType == 2) {
									end2 = end2.setDate(end2.getDate() + 1);
									end2 = new Date(end2);
								}
								if ((start1 > start2 && start1 < end2) || (start2 > start1 && start2 < end1)) {
									dialog.alert('班次时间设置有误！');
									return;
								}

							}
						}
						var onStart, offEnd;
						var endOver = false;
						for (i = 0; i < l; i++) {
							t1 = times[i];
							var start = new Date('2018/01/01 ' + t1.start);
							var end = new Date('2018/01/01 ' + t1.end);
							if (t1.overTimeType == 2) {
								end = end.setDate(end.getDate() + 1);
								end = new Date(end);
							}
							onStart = onStart && start > onStart ? onStart : start;
							if (!(offEnd && end < offEnd)) {
								offEnd = end;
								endOver = t1.overTimeType == 2;
							}
						}

						if (endOver && onStart && offEnd) {
							offEnd = offEnd.setDate(offEnd.getDate() - 1);
							offEnd = new Date(offEnd);
							if (offEnd >= onStart) {
								dialog.alert('班次时间设置有误！');
								return;
							}
						}

						return true;
					}
					return false;
				}
	
				// 获取数据
				window.getData = function() {
					
					var clazz = {};
					clazz['name'] = $('input[name="name"]').val() || '';
					clazz['color'] = $('input[name="color"]').val() || '';
					
					var times = [];
					
					$('tr.time').each(function() {
						
						var t = {};
						
						var start = $(this).find('input.start').val();
						start && (t.start = start);
						
						var end = $(this).find('input.end').val();
						end && (t.end = end);
						
						var overTimeType = $(this).find('select.overType').val();
						end && (t.overTimeType = overTimeType);


						var fdStartTime = $(this).find('input.begin-start-time').val();
						fdStartTime && (t.fdStartTime = fdStartTime);

						var fdOverTime = $(this).find('input.end-over-time').val();
						fdOverTime && (t.fdOverTime = fdOverTime);

						var fdEndOverTimeType = $(this).find('select.endOverType').val();
						end && (t.fdEndOverTimeType = fdEndOverTimeType);
						times.push(t);
					});
					clazz['times'] = times;
					if(window._checkRestRow){
						var restStartTime =$('input[name="fdRestStartTime"]').val();
						if(restStartTime){
							// var restStartTime2 = new Date('2018/01/01 ' + restStartTime);
							clazz['restStart'] = restStartTime;
						}
						var fdRestEndTime =$('input[name="fdRestEndTime"]').val();
						if(fdRestEndTime){
							// var fdRestEndTime2 = new Date('2018/01/01 ' + fdRestEndTime);
							clazz['restEnd'] =fdRestEndTime;
						}
						clazz['fdTotalDay'] = $('select[name="fdTotalDay"]').val() || '1';
						clazz['fdRestEndType'] =$('select[name="fdRestEndType"]').val() || '1';
						clazz['fdRestStartType'] =$('select[name="fdRestStartType"]').val() || '1';
					}
					return clazz;
				};
				// 取消操作
				window.handleCancel = function() {
					window.$dialog.hide();
				}

				// 一行工作时间下包含休息时间，两行以上隐藏休息时间
				window._checkRestRow = function() {

					if($('.time-row').get().length >= 2) {
						$('#restRow').hide();
						return false;
					}
					$('#restRow').show();
					return true
				}
				// 延迟执行，避免无法获取$dialog对象
				var timer = setInterval(function() {
					
					if(!window.$dialog) { return; }
					clearInterval(timer);
					
					// 元数据
					var srcData = {};
					
					// 获取传过来的数据
					var params =  window.$dialog.config.content.params;
					
					var clazz = params.data;
					if(clazz) {
						srcData = clazz;
						$('input[name="name"]').val(clazz.name);

						$(".color_ul li.select a").css("background-color", clazz.color);
						$("input[name='color']").val(clazz.color);
						$.each(clazz.times || [], function(index, time) {
							window.addItem(index, time);
						});
						if(clazz.restStart !=null){
							$('input[name="fdRestStartTime"]').val(clazz.restStart);
						}
						if(clazz.restStart !=null){
							$('input[name="fdRestEndTime"]').val(clazz.restEnd);
						}
						if(clazz.fdTotalDay){
							$('select[name="fdTotalDay"]').val(clazz.fdTotalDay);
						}
						if(clazz.fdRestEndType !=null){
							$('select[name="fdRestEndType"]').val(clazz.fdRestEndType);
						}
						if(clazz.fdRestStartType !=null){
							$('select[name="fdRestStartType"]').val(clazz.fdRestStartType);
						}
						if(window.console){
							console.info('设置时间：',clazz);
						}
					} else {
						DocList_AddRow('TABLE_DocList');
					}
					
					// 提交操作
					window.handleSubmit = function() {
						var data = window.getData();
						if(checkData(data)) {
							console.info(data);
							if(!window.checkStartEnd()){
								dialog.alert('休息开始时间不能晚于休息结束时间');
								return;
							}
							if(!window.checkWithWorkTime()){
								dialog.alert('休息时间不能在工作时间范围外');
								return;
							}
							// 校验颜色是否重复
							var colors = params.colors || [];
							var method = params.method;
							
							if(method == 'add') {
								if($.inArray(data.color, colors) > -1) {
									dialog.alert('班次颜色已被使用！');
									return;
								}
								window.$dialog.hide($.extend(srcData, data));
							} else {
								if(data.color != clazz.color && $.inArray(data.color, colors) > -1) {
									dialog.alert('班次颜色已被使用！');
									return;
								}
								window.$dialog.hide($.extend(srcData, data));
							}
						}
					}
				}, 300);

				//自定义必填校验器:校验休息开始结束时间
				window.checkStartEnd = function(){
					//休息开始时间不能晚于休息结束时间
					if(window._checkRestRow()) {
						var restStartTime = $('input[name="fdRestStartTime"]').val();
						var restEndTime = $('input[name="fdRestEndTime"]').val();
						var fdRestStartType = $('select[name="fdRestStartType"]:enabled').val();
						var fdRestEndType = $('select[name="fdRestEndType"]:enabled').val();
						if(restStartTime && restEndTime) {
							restStartTime = new Date('2018/01/01 ' + restStartTime);
							restEndTime = new Date('2018/01/01 ' + restEndTime);
							if(fdRestStartType ==2){
								restStartTime.setDate(restStartTime.getDate()+1);
							}
							if(fdRestEndType ==2){
								restEndTime.setDate(restEndTime.getDate()+1);
							}
							return !(restStartTime >= restEndTime);
						}
					}
					return true;
				}

				//自定义必填校验器:校验休息开始结束时间是否在工作时间范围内
				//休息时间不能在工作时间范围外
				window.checkWithWorkTime =function(){
					if(window._checkRestRow()) {
						var restStartTime = $('input[name="fdRestStartTime"]').val();
						var restEndTime = $('input[name="fdRestEndTime"]').val();
						var workStartTime = $('input[name="times[0].start"]').val();
						var workEndTime = $('input[name="times[0].end"]').val();
						var overTimeType = $('select[name="times[0].overTimeType"]').val();
						var fdRestStartType = $('select[name="fdRestStartType"]:enabled').val();
						var fdRestEndType = $('select[name="fdRestEndType"]:enabled').val();
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
				}

			});
			
			
		</script>
		
	</template:replace>
</template:include>