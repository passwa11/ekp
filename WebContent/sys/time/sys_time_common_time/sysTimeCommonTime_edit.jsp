<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.StringUtil" %>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<template:include ref="default.edit">
    <template:replace name="head">
        <style type="text/css">
            
            		.lui_paragraph_title{
            			font-size: 15px;
            			color: #15a4fa;
            	    	padding: 15px 0px 5px 0px;
            		}
            		.lui_paragraph_title span{
            			display: inline-block;
            			margin: -2px 5px 0px 0px;
            		}
            		.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
            		    border: 0px;
            		    color: #868686
            		}
            		
        </style>
        <script type="text/javascript">
            var formInitData = {

            };
            var messageInfo = {

            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/sys/time/sys_time_common_time/resource/js/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/sys/time/sys_time_common_time/", 'js', true);
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            
            Com_IncludeFile("jquery.js");
            Com_IncludeFile("doclist.js");
        </script>

		<script src="${LUI_ContextPath}/sys/time/resource/js/jquery.colorpicker.js" type="text/javascript"></script>
		<link rel="stylesheet" href="${LUI_ContextPath}/sys/time/resource/css/colpick.css?s_cache=${MUI_Cache}" type="text/css"/>

    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${sysTimeCommonTimeForm.method_GET == 'add' }">
                Create - <c:out value="${ lfn:message('sys-time:sysTimeWork.common') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${sysTimeCommonTimeForm.fdName}" /> - ${ lfn:message('sys-time:sysTimeWork.common') }
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ sysTimeCommonTimeForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="window.handleUpdate();" />
                </c:when>
                <c:when test="${ sysTimeCommonTimeForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="window.handleSave();" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('sys-time:sysTimeCommonTime.general.shift.settings') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/time/sys_time_common_time/sysTimeCommonTime.do">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('sys-time:sysTimeCommonTime.essential.information') }" expand="true">
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:sysTimeWork.timeName') }
                            </td>
                            <td width="35%">
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" subject="${ lfn:message('sys-time:sysTimeWork.timeName') }" showStatus="edit" style="width:95%;" required="true" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:sysTimeCommonTime.abbreviation') }
                            </td>
                            <td width="35%">
                                <div id="_xform_fdNameShort" _xform_type="text">
                                    <xform:text property="simpleName" subject="${ lfn:message('sys-time:sysTimeCommonTime.Abbreviation') }"  showStatus="edit" style="width:95%;" required="true"/>
                                </div>
                            </td>
						</tr>
						<tr>
                            <td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:sysTimeCommonTime.color.mark') }
                            </td>
                            <td width="35%" colspan="1">
	                            <c:choose>
				               		<c:when test="${sysTimeCommonTimeForm.fdWorkTimeColor != null and sysTimeCommonTimeForm.fdWorkTimeColor != '' }">
										<input name="fdWorkTimeColor" value="${sysTimeCommonTimeForm.fdWorkTimeColor}" type="hidden"/>
				               		</c:when>
				               		<c:otherwise>
				               			<input name="fdWorkTimeColor" value="#5484ed" type="hidden"/>
				               		</c:otherwise>
				               	</c:choose>
								<ul class="clrfix color_ul">
					                 <li class="select">
					                 	<c:choose>
					                 		<c:when test="${sysTimeCommonTimeForm.fdWorkTimeColor != null and sysTimeCommonTimeForm.fdWorkTimeColor != '' }">
					                 			<a style="background-color: ${sysTimeCommonTimeForm.fdWorkTimeColor};"></a>
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
								<div class="selfdef"><a id="selfdef">${ lfn:message('sys-time:sysTimeCommonTime.custom') }</a></div>			
								<script type="text/javascript">
								    $(function () {
								        $(".color_ul li").each(function () {
								        	if($(this).prop("className") != 'select' && $(this).prop("className") != 'line'){
								        		 $(this).click(function () {
								        			var color = $(this).css("background-color");
								        			if(color.indexOf("#")==-1){
								        				color=colorRGB2Hex(color);
								        			}								        			 
									                $(".color_ul li.select a").css("background-color", color);
									                $("input[name='fdWorkTimeColor']").val(color);
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
								                	 $("input[name='fdWorkTimeColor']").val("");
								                }else{
								                	$("input[name='fdWorkTimeColor']").val(color);
								                }
								            },
								        	reset:function(o){
								        		 $(".color_ul li.select a").css("background-color", "#ffffff");
									             $("input[name='fdWorkTimeColor']").val("");
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
                            <td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:sysTimeCommonTime.isSchedule') }
                            </td>
                            <td width="35%">
                                <div id="_xform_status" _xform_type="radio">
									<c:choose>
									
										<c:when test="${sysTimeCommonTimeForm.isSchedule eq null or sysTimeCommonTimeForm.status eq '' }">
											<xform:radio property="isSchedule" subject="${ lfn:message('sys-time:sysTimeLeaveRule.status') }" htmlElementProperties="id='isSchedule'" showStatus="edit" required="true" value="false">
		                                        <xform:enumsDataSource enumsType="common_yesno" />
		                                    </xform:radio>
										</c:when>
										<c:otherwise>
		                                    <xform:radio property="isSchedule" subject="${ lfn:message('sys-time:sysTimeLeaveRule.status') }" htmlElementProperties="id='isSchedule'" showStatus="edit" required="true">
		                                        <xform:enumsDataSource enumsType="common_yesno" />
		                                    </xform:radio>
										</c:otherwise>
									</c:choose>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:sysTimeCommonTime.status') }
                            </td>
                            <td width="35%">
                                <div id="_xform_status" _xform_type="radio">
									<c:choose>
									
										<c:when test="${sysTimeCommonTimeForm.status eq null or sysTimeCommonTimeForm.status eq '' }">
											<xform:radio property="status" subject="${ lfn:message('sys-time:sysTimeLeaveRule.status') }" htmlElementProperties="id='status'" showStatus="edit" required="true" value="true">
		                                        <xform:enumsDataSource enumsType="common_yesno" />
		                                    </xform:radio>
										</c:when>
										<c:otherwise>
		                                    <xform:radio property="status" subject="${ lfn:message('sys-time:sysTimeLeaveRule.status') }" htmlElementProperties="id='status'" showStatus="edit" required="true">
		                                        <xform:enumsDataSource enumsType="common_yesno" />
		                                    </xform:radio>
										</c:otherwise>
									</c:choose>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:sysTime.import.sheet2.serial') }
                            </td>
                            <td width="35%">
                                <div id="_xform_fdOrder" _xform_type="text">
                                    <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        
                        <!-- 工作时间 -->
                        <tr>
                        	<td class="td_normal_title" width="15%">
								${ lfn:message('sys-time:calendar.worktime') }
                            </td>
                            <td colspan="3" width="85%">
								<table class="tb_normal" width=100% id="TABLE_DocList">
									<tr>
										<td class="td_normal_title" align="center" width=5%>
											<img src="${KMSS_Parameter_StylePath}icons/add.gif" onclick="handleAddTimeRow();" style="cursor:pointer">
										</td>
										<td class="td_normal_title" align="center" width=20%>
											${ lfn:message('sys-time:sysTimeWork.hbmStartTime') }
										</td>
										<td class="td_normal_title" align="center" width=26%>
											${ lfn:message('sys-time:sysTimeWork.hbmEndTime') }
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
												<img src="${KMSS_Parameter_StylePath}icons/delete.gif" onclick="handleDelTimeRow();" style="cursor:pointer" />
											     <input type="hidden" class="inputSgl" name="sysTimeWorkDetails[!{index}].fdId" value = ""/>
											</center>
										</td>
										<td>	
										     <input type="text" onchange="window._calcWorkHour();" class="inputSgl start-time" style="width:70%" name="sysTimeWorkDetails[!{index}].fdWorkStartTime" value = "" readonly/>
										     <a href="#" onclick="selectTime('sysTimeWorkDetails[!{index}].fdWorkStartTime');" styleClass="inputsgl" >
											 <bean:message key="dialog.selectTime" /></a>
											 <span class="txtstrong">*</span>
										</td>
										<td>
										    <xform:select
												property="sysTimeWorkDetails[!{index}].fdOverTimeType"
												showPleaseSelect="false"
												title="${ lfn:message('sys-time:sysTimeWork.hbmEndTime') }"
												onValueChange="window._calcWorkHour();"
												style="width:30%;height:32px;margin-right:7px;">
												<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
												<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
											</xform:select>
											<input type="text" onchange="window._calcWorkHour();" class="inputSgl end-time" style="width:40%" name="sysTimeWorkDetails[!{index}].fdWorkEndTime" value = "" readonly/>
										     <a href="#" onclick="selectTime('sysTimeWorkDetails[!{index}].fdWorkEndTime');" styleClass="inputsgl" >
											 <bean:message key="dialog.selectTime" /></a>
											 <span class="txtstrong">*</span>
										</td>

										<%-- 最早打卡 --%>
										<td style="width: 120px">
											<input type="text" onchange="window._calcWorkHour();" class="inputSgl begin-start-time" style="width:70%" name="sysTimeWorkDetails[!{index}].fdStartTime" value = "" readonly/>
											<a href="#" onclick="selectTime('sysTimeWorkDetails[!{index}].fdStartTime');" styleClass="inputsgl" >
												<bean:message key="dialog.selectTime" /></a>
											<span class="txtstrong">*</span>
										</td>
										<%-- 最晚打卡 --%>
										<td style="">
											<xform:select
													property="sysTimeWorkDetails[!{index}].fdEndOverTimeType"
													showPleaseSelect="false"
													title="${ lfn:message('sys-time:sysTimeWork.hbmEndTime') }"
													onValueChange="window._calcWorkHour();"
													style="width:30%;height:32px;margin-right:7px;"
											>
												<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
												<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
											</xform:select>
											<input type="text" onchange="window._calcWorkHour();" class="inputSgl end-over-time" style="width:40%" name="sysTimeWorkDetails[!{index}].fdOverTime" value = "" readonly/>
											<a href="#" onclick="selectTime('sysTimeWorkDetails[!{index}].fdOverTime');" styleClass="inputsgl" >
												<bean:message key="dialog.selectTime" /></a>
											<span class="txtstrong">*</span>
										</td>

									</tr>
									
									<c:forEach items="${sysTimeCommonTimeForm.sysTimeWorkDetails}" var="sysTimeWorkDetail" varStatus="vstatus">
									<tr KMSS_IsContentRow="1" class="time-row">
										<td>
											<center>
												<img src="${KMSS_Parameter_StylePath}icons/delete.gif" onclick="handleDelTimeRow();" style="cursor:pointer" />
											    <input type="hidden" name="sysTimeWorkDetails[${vstatus.index}].fdId" value = "${sysTimeWorkDetail.fdId}"/>
											</center>
										</td>
										<td>
											<input onchange="window._calcWorkHour();" type="text" class="inputSgl start-time"
												   style="width:40%" name="sysTimeWorkDetails[${vstatus.index}].fdWorkStartTime" value = "${sysTimeWorkDetail.fdWorkStartTime}" readonly/>
										    <a href="#" onclick="selectTime('sysTimeWorkDetails[${vstatus.index}].fdWorkStartTime');" styleClass="inputsgl" >
											<bean:message key="dialog.selectTime" /></a>
											<span class="txtstrong">*</span>
										</td>	
										<td>
										     <xform:select
													property="sysTimeWorkDetails[${vstatus.index}].fdOverTimeType"
													showPleaseSelect="false"
													title="${ lfn:message('sys-time:sysTimeWork.hbmEndTime') }"
													onValueChange="window._calcWorkHour();"
													style="width:30%;height:32px;margin-right:7px;"
													showStatus="edit"
													>
													<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
													<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
												</xform:select>
											 <input onchange="window._calcWorkHour();" type="text" class="inputSgl end-time"
													style="width:40%" name="sysTimeWorkDetails[${vstatus.index}].fdWorkEndTime"
													value = "${sysTimeWorkDetail.fdWorkEndTime}" readonly/>
										     <a href="#" onclick="selectTime('sysTimeWorkDetails[${vstatus.index}].fdWorkEndTime');" styleClass="inputsgl" >
											 <bean:message key="dialog.selectTime" /></a>
											 <span class="txtstrong">*</span>
										</td>

											<%-- 最早打卡 --%>
										<td >
											<input  type="text" class="inputSgl begin-start-time" style="width:70%"
												   name="sysTimeWorkDetails[${vstatus.index}].fdStartTime"
												   value = "${sysTimeWorkDetail.fdStartTime}" readonly/>
											<a href="#" onclick="selectTime('sysTimeWorkDetails[${vstatus.index}].fdStartTime');" styleClass="inputsgl" >
												<bean:message key="dialog.selectTime" /></a>
											<span class="txtstrong">*</span>
										</td>
											<%-- 最晚打卡 --%>
										<td >
											<xform:select
													property="sysTimeWorkDetails[${vstatus.index}].fdEndOverTimeType"
													showPleaseSelect="false"
													title="${ lfn:message('sys-time:sysTimeWork.hbmEndTime') }"
													onValueChange="window._calcWorkHour();"
													style="width:30%;height:32px;margin-right:7px;"
													showStatus="edit"
											>
												<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
												<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
											</xform:select>
											<input type="text"  class="inputSgl end-over-time" style="width:40%"
												   value = "${sysTimeWorkDetail.fdOverTime}"
												   name="sysTimeWorkDetails[${vstatus.index}].fdOverTime" value = "" readonly/>
											<a href="#" onclick="selectTime('sysTimeWorkDetails[${vstatus.index}].fdOverTime');" styleClass="inputsgl" >
												<bean:message key="dialog.selectTime" /></a>
											<span class="txtstrong">*</span>
										</td>

									</tr>	
									</c:forEach>
									
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
											onValueChange="window._calcWorkHour();"
											style="width:30%;margin-right:7px;"
											showStatus="edit"
									>
										<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
										<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
									</xform:select>
                                    <xform:datetime onValueChange="window._calcWorkHour" property="fdRestStartTime" subject="休息开始时间" 
                                    	validators="checkStartEnd checkWithWorkTime" showStatus="edit" dateTimeType="time" style="width:65%;" />
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
											title="休息结束时间"
											onValueChange="window._calcWorkHour();"
											style="width:30%;margin-right:7px;"
											showStatus="edit"
									>
										<xform:simpleDataSource value='1'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type1') }</xform:simpleDataSource>
										<xform:simpleDataSource value='2'>${ lfn:message('sys-time:sysTimeWork.fdOverTimeType.type2') }</xform:simpleDataSource>
									</xform:select>
                                    <xform:datetime onValueChange="window._calcWorkHour" property="fdRestEndTime" subject="休息结束时间" 
                                    	validators="checkStartEnd checkWithWorkTime" showStatus="edit" dateTimeType="time" style="width:65%;" />
                                </div>
                            </td>
                        </tr>
                        
						<!-- 总工时 -->
						<tr>
						  	<td class="td_normal_title" width="15%">
						    	${ lfn:message('sys-time:sysTimeCommonTime.total.working.hours') }
						  	</td>
						  	<td colspan="3" width="85%">
						  		<c:set var="_fdWorkHour" value="${sysTimeCommonTimeForm.fdWorkHour}"></c:set>
						  		<%
						  			String _fdWorkHour = (String)pageContext.getAttribute("_fdWorkHour");
						  			_fdWorkHour = StringUtil.isNotNull(_fdWorkHour)?_fdWorkHour:"0";
						  			int workHour = (int)(Float.valueOf(_fdWorkHour) * 60);
						  			int hour = workHour/60;
									int mins = workHour%60;
									String hourTxt = "";
									String hTxt = ResourceUtil.getString("date.interval.hour");
									String mTxt = ResourceUtil.getString("date.interval.minute");
									if(hour>0){
										hourTxt+=hour+hTxt;
									}
									if(mins>0){
										hourTxt+=mins+mTxt;
									}
									pageContext.setAttribute("work_hour", hourTxt);
									
						  		%>
						  		<input type="hidden" name="fdWorkHour" value="${sysTimeCommonTimeForm.fdWorkHour}">
						  		<span class="lui_workhour">${work_hour }</span>

								<%--统计时按多少天算--%>
								<xform:select showStatus="edit" property="fdTotalDay" showPleaseSelect="false"
											  title="${ lfn:message('sys-time:sysTimeCommonTime.total.day.one') }"  style="width:35%;height:30px;margin-right:7px;"
											  value="${sysTimeCommonTimeForm.fdTotalDay }">
									<xform:simpleDataSource value='1.0'>${ lfn:message('sys-time:sysTimeCommonTime.total.day.one') }</xform:simpleDataSource>
									<xform:simpleDataSource value='0.5'>${ lfn:message('sys-time:sysTimeCommonTime.total.day.half') }</xform:simpleDataSource>
								</xform:select>
						  	</td>
						</tr>
                        
                    </table>
                </ui:content>
            </ui:tabpage>
            <html:hidden property="fdId" />

            <html:hidden property="method_GET" />
        </html:form>
        
        <script>

        	seajs.use(['lui/dialog'], function(dialog) {
        		
	        	var validation=$KMSSValidation();
	        	
	    		//自定义必填校验器:校验休息开始结束时间
	    		validation.addValidator('checkStartEnd','休息开始时间不能晚于休息结束时间',function(v, e, o){

					if(window._checkRestRow()) {
						
						var restStartTime = $('input[name="fdRestStartTime"]').val();
						var restEndTime = $('input[name="fdRestEndTime"]').val();
						var fdRestStartType = $('select[name="fdRestStartType"]:enabled').val();
						var fdRestEndType = $('select[name="fdRestEndType"]:enabled').val();
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
					if(window._checkRestRow()) {
						
						var restStartTime = $('input[name="fdRestStartTime"]').val();
						var restEndTime = $('input[name="fdRestEndTime"]').val();
						var workStartTime = $('input[name="sysTimeWorkDetails[0].fdWorkStartTime"]').val();
						var workEndTime = $('input[name="sysTimeWorkDetails[0].fdWorkEndTime"]').val();
						var overTimeType = $('select[name="sysTimeWorkDetails[0].fdOverTimeType"]').val();
						var fdRestStartType = $('select[name="fdRestStartType"]:enabled').val();
						var fdRestEndType = $('select[name="fdRestEndType"]:enabled').val();
						if(restStartTime && restEndTime && workStartTime && workEndTime) {
							restStartTime = new Date('2018/01/01 ' + restStartTime);
							restEndTime = new Date('2018/01/01 ' + restEndTime);
							workStartTime = new Date('2018/01/01 ' + workStartTime);
							workEndTime = new Date('2018/01/01 ' + workEndTime);
							if(overTimeType == 2){
								workEndTime = workEndTime.setDate(workEndTime.getDate()+1);
								workEndTime =new Date(workEndTime);
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
	        	
	        	
	        	window.handleAddTimeRow = function() {
	        		DocList_AddRow('TABLE_DocList');
	        		window._checkRestRow();
	        		window._calcWorkHour();
	        	}
	        	
	        	window.handleDelTimeRow = function() {
	        		DocList_DeleteRow();
	        		window._checkRestRow();
	        		window._calcWorkHour();
	        	}
	        	
	        	window._checkWorkTimes = function() {
	        		var timeRows = $('#TABLE_DocList').find('.time-row');
	        		if(timeRows.get().length <= 0) {
	        			return false;
	        		}
	        		
	        		var check = true;
	        		var times = [];
	        		timeRows.each(function(index) {
	        			var start = $(this).find('.start-time').val();
	        			var end = $(this).find('.end-time').val();
						var overTime = $('select[name="sysTimeWorkDetails['+index+'].fdOverTimeType"]').val();

						var beginTime = $(this).find('.begin-start-time').val();
						var endOverTime = $(this).find('.end-over-time').val();
						var endOverTimeType = $('select[name="sysTimeWorkDetails['+index+'].fdEndOverTimeType"]').val();

	        			
						if(!start || !end || !beginTime || !endOverTime) {
							check = false;
						} else {
							start = new Date('2018/01/01 ' + start);
							end = new Date('2018/01/01 ' + end);
							var isOverTime = false;
							if(overTime == 2){
								end = end.setDate(end.getDate()+1);
								end = new Date(end);
								isOverTime = true;
							}

							//最早最晚打卡时间
							beginTime = new Date('2018/01/01 ' + beginTime);
							endOverTime = new Date('2018/01/01 ' + endOverTime);
							if(endOverTimeType ==2){
								//如果最晚时间是次日。则判断。最晚打卡时间，应该在最早打卡时间之前
								if(endOverTime >= beginTime){
									check = false;
								}
								endOverTime = endOverTime.setDate(endOverTime.getDate()+1);
								endOverTime = new Date(endOverTime);
							}
							if(beginTime > start){
								//最早打卡时间大于开始时间
								check = false;
							}else if(end > endOverTime){
								//最晚打卡时间小于结束时间
								check = false;
							}else  if(end <= start) {
								//开始时间大于结束时间
								check = false;
							} else {
								times.push({
									start: start,
									end: end,
									isOverTime: isOverTime,
									beginTime: beginTime,
									overTime: endOverTime,
									endOverTime: endOverTimeType==2?true:false
								});
							}
							if(!check){
								return false;
							}
						}	        		
	        		});
					if(check){
						var i = 0, j = 0;
						for(i; i < times.length - 1; i++) {
							var t1 = times[i];
							for(j = i + 1; j < times.length; j++) {
								var t2 = times[j];
								if((t1.start > t2.start && t1.start < t2.end)
										|| (t2.start > t1.start && t2.start < t1.end)) {
									check = false;
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
								check = false;
							}
						}
					}
	        		return check;
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
	        	
	        	// 计算工作时间
	        	window._calcWorkHour = function() {
	        		var workTime = 0;
	        		
	        		$('.time-row').each(function(index) {
	        			var startTime = $(this).find('.start-time').val();
	        			var endTime = $(this).find('.end-time').val();
	        			var overTime = $('select[name="sysTimeWorkDetails['+index+'].fdOverTimeType"]').val();
	        			
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
	        		var restStartTime = $('input[name="fdRestStartTime"]').val();
					var fdRestStartType = $('select[name="fdRestStartType"]:enabled').val();
	        		var restEndTime = $('input[name="fdRestEndTime"]').val();
					var fdRestEndType = $('select[name="fdRestEndType"]:enabled').val();
	        		if(restStartTime && /\d\d:\d\d/.test(restStartTime) && restEndTime && /\d\d:\d\d/.test(restEndTime)) {
	        			restStartTime = new Date('2018/01/01 ' + restStartTime);
						if(fdRestStartType ==2){
							restStartTime = restStartTime.setDate(restStartTime.getDate()+1);
							restStartTime = new Date(restStartTime);
						}
	        			restEndTime = new Date('2018/01/01 ' + restEndTime);
	    				if(fdRestEndType ==2){
							restEndTime = restEndTime.setDate(restEndTime.getDate()+1);
							restEndTime = new Date(restEndTime);
						}
	    				if(restEndTime > restStartTime) {
	    					restTime += (restEndTime - restStartTime);
	    				}
	    			} 
	        		
	        		if(window._checkRestRow()) {
	        			// TODO 这里计算时间还未考虑交集或工作时间范围外的情况，后续优化
		        		var t = workTime - restTime < 0 ? 0 : (workTime - restTime);
						$('.lui_workhour').html(formatWorkHour(t));
						$('input[name="fdWorkHour"]').val((t / 3600000).toFixed(5));
	        		} else {
	        			$('.lui_workhour').html(formatWorkHour(workTime));
	        			$('input[name="fdWorkHour"]').val((workTime / 3600000).toFixed(5));
	        		}
	        	}
	        	window.formatWorkHour = function(value){
	        		var hour = parseInt(value/1000/60/60);
	        		var min = (value/1000/60)%60;
	        		var hTxt = "${ lfn:message('date.interval.hour') }";
	        		var mTxt = "${ lfn:message('date.interval.minute') }";
	        		var hourTxt = "";
	        		if(hour>0){
	        			hourTxt+=hour+hTxt;
	        		}
	        		if(min>0){
	        			hourTxt+=min+mTxt;
	        		}
	        		return hourTxt;
	        	}
	        	
	        	window.handleSave = function() {
	        		if(_checkWorkTimes()) {
		        		Com_Submit(document.sysTimeCommonTimeForm, 'save');
	        		} else {
	        			dialog.alert('工作时间设置有误！');
	        		}
	        		
	        	}
	        	
	        	window.handleUpdate = function() {
	        		if(_checkWorkTimes()) {
		        		Com_Submit(document.sysTimeCommonTimeForm, 'update');
	        		} else {
	        			dialog.alert('工作时间设置有误！');
	        		}
	        	}
	        
	        	// 添加状态下新增一行，编辑状态下校验休息开始结束时间
		        if("${JsParam.method}" == "add"){
		    		Com_AddEventListener(window, 'load', function(){ setTimeout(function() {DocList_AddRow('TABLE_DocList');}, 500);});
		    		window._calcWorkHour();
		    	} else {
	    			Com_AddEventListener(window, 'load', function(){ 
	    				setTimeout(function() {
	    					window._checkRestRow();
	    					window._calcWorkHour();
	    				});
	    			});
		    	}
        	});

        </script>
        
        
    </template:replace>

</template:include>