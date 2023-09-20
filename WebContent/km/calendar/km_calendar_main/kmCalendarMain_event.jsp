<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/km/calendar/import/calendarMinuteStep.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%
	request.setAttribute("formatter", ResourceUtil.getString("date.format.date"));
%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<template:include ref="default.dialog">
	<template:replace name="content">
	<%--新增日程(详细设置)--%>
	<html:form action="/km/calendar/km_calendar_main/kmCalendarMain.do" styleId="eventform">
		<html:hidden property="fdId" />
		<html:hidden property="docCreateTime" />
		<html:hidden property="docCreatorId" />
		<html:hidden property="fdRecurrenceStr" />
		<html:hidden property="fdType"  value="event"/>
		<html:hidden property="fdIsGroup" value="false"/>
		<br/>
		<table id="event_base_tb" class="tb_simple" width="100%">
			<tr>
	     		<%--日历--%>
	              <td width="15%" class="td_normal_title" >
	              		<bean:message bundle="km-calendar" key="kmCalendarMain.docLabel" />
	              </td>
	              <td width="85%" colspan="3" >
	              		<div style="float:left">
		              		<ui:dataview id="labelId" name="labelId">
	                	  		<ui:source type="AjaxJson">
									{url:'/km/calendar/km_calendar_label/kmCalendarLabel.do?method=listJson&userId=${kmCalendarMainForm.docOwnerId }'}
								</ui:source>
								<ui:render type="Template">
									<c:set var="url" value="/km/calendar/tmpl/label_select.jsp"></c:set>
									<c:if test="${param.method =='edit' }">
										<c:set var="url" value="/km/calendar/tmpl/add_event_label_select.jsp"></c:set>
									</c:if>
									<c:if test="${param.method =='addEvent' }">
										<c:set var="url" value="/km/calendar/tmpl/add_event_label_select.jsp"></c:set>
									</c:if>
									<c:import url="${url }" charEncoding="UTF-8"></c:import>
								</ui:render>
								<ui:event event="load">
									setLabelSelected();
								</ui:event>
							</ui:dataview>
						</div>
	              		<%--所有者--%>
	              		<bean:message bundle="km-calendar" key="kmCalendarMain.docOwner" />	
	              		<c:choose>
	              			<c:when test="${not empty kmCalendarMainForm.docOwnerId and kmCalendarMainForm.docOwnerId ne KMSS_Parameter_CurrentUserId and kmCalendarMainForm.docOwnerId ne 'multiCreate'}">
	              				<input type="hidden" name="docOwnerId" value="${kmCalendarMainForm.docOwnerId }" />
	              				${kmCalendarMainForm.docOwnerName }
	              			</c:when>
	              			<c:otherwise>
	              				<select id="docOwnerId" name="docOwnerId" onchange="changeOwner();">
				              		<c:forEach items="${owners}" var="owner" begin="0">
				              			<option value="${owner[0]}" <c:if test="${owner[0] == kmCalendarMainForm.docOwnerId }">selected</c:if>
										>${owner[1]}
										</option>
									</c:forEach>
									<c:if test="${kmCalendarMainForm.method_GET == 'addEvent' }">
									<kmss:authShow roles="ROLE_KMCALENDAR_MULTI_CREATE">
										 <%-- 是否发起多人日程--%>
										<option value="multiCreate">
											<bean:message bundle="km-calendar" key="kmCalendarShareGroup.fdGroup"/>
										</option>
									</kmss:authShow>
									</c:if>
								</select>
	              			</c:otherwise>
	              		</c:choose>
						<span id="ownerTip" style="color: red;display: none;">
							<bean:message bundle="km-calendar" key="kmCalendarMain.docOwner.tip" />
						</span>
						<c:if test="${creatorName!=null}">
							&nbsp;&nbsp;&nbsp;<bean:message bundle="km-calendar" key="kmCalendarMain.docCreator" />：<span style="font-weight:bold;">${creatorName}</span>
						</c:if>
	              </td>
	         </tr>
	         <kmss:authShow roles="ROLE_KMCALENDAR_MULTI_CREATE">
	         <%--所有者(发起多人日程时以地址本选择)--%>
	         <tr id="multiOwner" style="display: none;">
	              <td width="15%" class="td_normal_title" >
	              		<bean:message bundle="km-calendar" key="kmCalendarShareGroup.fdGroupMemberIds" />
	              </td>
	              <td width="85%" colspan="3" >
	              		<xform:dialog subject="${lfn:message('km-calendar:kmCalendarShareGroup.fdGroupMemberIds') }" style="width:90%;" propertyId="docOwnerIds" propertyName="docOwnerNames" showStatus="edit">
								Dialog_AddressList(true,'docOwnerIds','docOwnerNames',';','kmCalendarAuthService',null,'kmCalendarAuthService&fdName=!{keyword}',null,null,'${ lfn:message('km-calendar:kmCalendarShareGroup.fdGroupMemberIds') }');
						</xform:dialog>	
	              		<span style="color: #FF0000;">*</span>
	              </td>
	         </tr>
	         </kmss:authShow>
			<tr>
	     		<%--内容--%>
	             <td width="15%" class="td_normal_title" valign="top">
	             	<bean:message bundle="km-calendar" key="kmCalendarMain.docContent" />
	              </td>
	               <td width="85%" colspan="3">
	                 <xform:textarea property="docSubject"  showStatus="edit" subject="${lfn:message('km-calendar:kmCalendarMain.docContent')}" style="width:90%"/>
	              </td>
	         </tr>
	         <c:if test="${not empty kmCalendarMainForm.docContent }">
	         <tr>
	         	<%--详情（EKP系统的日程不存在详情，此字段存放来自exchange的内容）--%>
	             <td width="15%" class="td_normal_title" valign="top">
	             	<bean:message bundle="km-calendar" key="kmCalendarMain.detailDocContent" />
	              </td>
	         	 <td width="85%" colspan="3">
	         	 	<xform:rtf property="docContent" showStatus="edit"   toolbarCanCollapse="all" height="150"  width="90%" ></xform:rtf>
	              </td>
	         </tr>
	         </c:if>
	         <tr>
	        	<%--时间--%>
	            <td width="15%" class="td_normal_title" valign="top">
	               	<bean:message bundle="km-calendar" key="kmCalendarMain.docTime" />
	            </td>
	            <td width="85%" colspan="3">
	     			<div class="div_1">
	               		<%--全天--%>
	               		<input type="checkbox" id="fdIsAlldayevent" name="fdIsAlldayevent" value="true" onClick="changeAllDayValue();"
	                   		<c:if test="${kmCalendarMainForm.fdIsAlldayevent =='true' }">checked="checked"</c:if>/>
	                     	<label>
	                     		<bean:message bundle="km-calendar" key="kmCalendarMain.allDay" />
	                     	</label>
	                  	<%--农历--%>
	                   	<input type="checkbox" id="fdIsLunar" name="fdIsLunar" class="ck_lunar" value="true" onClick="changeLunarValue();"
	     					<c:if test="${kmCalendarMainForm.fdIsLunar =='true' }">checked="checked"</c:if>/> 
	     					<label>
	     						<bean:message bundle="km-calendar" key="lunar" />
	     					</label>
					</div>
					<%--公历时间选择器--%>
	             	<div id="div_solar" class="div_2" style="display:none;">
	                	<xform:datetime showStatus="edit"  property="docStartTime" onValueChange="docStartTimeChange" style="width:20%;height:23px;" dateTimeType="date"  required="true" />
                       <div id="startTimeDiv" class="startTime" style="top: -5px;position: relative; display: inline;" >
	                        <select id="startHour" name="startHour" onchange="startMinuteSelect()" >
	                        	<c:forEach  begin="0" end="23" varStatus="status" >
	                            	<option value="${status.index}">${status.index}</option>
	                            </c:forEach>
	                        </select>
	                        <bean:message bundle="km-calendar" key="hour" />
	                        
	                        <select id="startMinute" name="startMinute" onchange="startMinuteSelect()">
	                        	<c:forEach  items="${calendarMinuteList}" var="minute" >
	                            	<option value="${minute}">${minute}</option>
	                            </c:forEach>
	                        </select>
	                        <bean:message bundle="km-calendar" key="minute" />
                       </div>
                       
	                  	<span style="top: -5px;position: relative;">-</span>
                        <xform:datetime showStatus="edit"  property="docFinishTime" style="width:20%;height:23px;" dateTimeType="date" required="true" />
                   		<div id="endTimeDiv" class="endTime" style="top: -5px;position: relative; display: inline;" >
	                         <select id="endHour" name="endHour">
	                         	<c:forEach  begin="0" end="23" varStatus="status" >
	                              	<option value="${status.index}">${status.index}</option>
	                            </c:forEach>
							</select><bean:message bundle="km-calendar" key="hour" />
                        	<select id="endMinute" name="endMinute">
								<c:forEach items="${calendarMinuteList}" var="minute" >
									<option value="${minute}">${minute}</option>
								</c:forEach>
                         </select><bean:message bundle="km-calendar" key="minute" />
                       </div>
	          		</div>
	          		<%--农历时间选择器--%>
	                <div id="div_lunar" class="div_2" style="display: none;">
	                	<div style="position: relative; display: inline;">
	                	    <select name="lunarStartYear" id="lunarStartYear">
                 	        	<option value="2000">2000年</option><option value="2001">2001年</option><option value="2002">2002年</option><option value="2003">2003年</option><option value="2004">2004年</option><option value="2005">2005年</option><option value="2006">2006年</option><option value="2007">2007年</option><option value="2008">2008年</option><option value="2009">2009年</option><option value="2010">2010年</option><option value="2011">2011年</option><option value="2012">2012年</option><option value="2013" selected="selected">2013年</option><option value="2014">2014年</option><option value="2015">2015年</option><option value="2016">2016年</option><option value="2017">2017年</option><option value="2018">2018年</option><option value="2019">2019年</option><option value="2020">2020年</option><option value="2021">2021年</option><option value="2022">2022年</option><option value="2023">2023年</option><option value="2024">2024年</option><option value="2025">2025年</option>
                 	        </select>
                 	        <select id="lunarStartMonth" name="lunarStartMonth">
	                 	        <option idx="0" value="1">正月</option><option idx="1" value="2">二月</option><option idx="2" value="3">三月</option><option idx="3" value="4">四月</option><option idx="4" value="5">五月</option><option idx="5" value="6">六月</option><option idx="6" value="7">七月</option><option idx="7" value="8">八月</option><option idx="8" value="9">九月</option><option idx="9" value="10" selected="selected">十月</option><option idx="10" value="11">十一月</option><option idx="11" value="12">十二月</option>
                 	        </select>
                 	        <select id="lunarStartDay" name="lunarStartDay">
	                 	        <option>初一</option>
                 	        </select>
	                	</div>
	                	<div id="startTimeDivLunar"  class="startTime" style="position: relative; display: inline;">
                           <select id="lunarStartHour" name="lunarStartHour" onclick="lunarTimeSelect()">
                           		<c:forEach  begin="0" end="23" varStatus="status" >
	                               	<option value="${status.index}">${status.index}</option>
	                        	</c:forEach>
                           	</select>
                           	<bean:message bundle="km-calendar" key="hour" />
                           	
                           	<select id="lunarStartMinute" name="lunarStartMinute" onclick="lunarTimeSelect()">
								<c:forEach  items="${calendarMinuteList}" var="minute" >
									<option value="${minute}">${minute}</option>
								</c:forEach>
                           	</select>
                           	<bean:message bundle="km-calendar" key="minute" />
                           	
	               		</div>
	                   	<span>-</span>
	                    <div style="position: relative; display: inline;">
	                	    <select id="lunarEndYear" name="lunarEndYear">
                 	        	<option value="2000">2000年</option><option value="2001">2001年</option><option value="2002">2002年</option><option value="2003">2003年</option><option value="2004">2004年</option><option value="2005">2005年</option><option value="2006">2006年</option><option value="2007">2007年</option><option value="2008">2008年</option><option value="2009">2009年</option><option value="2010">2010年</option><option value="2011">2011年</option><option value="2012">2012年</option><option value="2013" selected="selected">2013年</option><option value="2014">2014年</option><option value="2015">2015年</option><option value="2016">2016年</option><option value="2017">2017年</option><option value="2018">2018年</option><option value="2019">2019年</option><option value="2020">2020年</option><option value="2021">2021年</option><option value="2022">2022年</option><option value="2023">2023年</option><option value="2024">2024年</option><option value="2025">2025年</option>
                 	        </select>
                 	        <select id="lunarEndMonth" name="lunarEndMonth">
	                 	       <option idx="0" value="1">正月</option><option idx="1" value="2">二月</option><option idx="2" value="3">三月</option><option idx="3" value="4">四月</option><option idx="4" value="5">五月</option><option idx="5" value="6">六月</option><option idx="6" value="7">七月</option><option idx="7" value="8">八月</option><option idx="8" value="9">九月</option><option idx="9" value="10" selected="selected">十月</option><option idx="10" value="11">十一月</option><option idx="11" value="12">十二月</option>
                 	        </select>
                 	        <select id="lunarEndDay" name="lunarEndDay">
	                 	        <option>初一</option>
                 	        </select>
	                	</div>
	                	<div id="endTimeDivLunar" class="endTime" style="position: relative; display: inline;">
                           <select id="lunarEndHour" name="lunarEndHour">
                           		<c:forEach  begin="0" end="23" varStatus="status" >
	                               	<option value="${status.index}">${status.index}</option>
	                        	</c:forEach>
                           </select>
                           <bean:message bundle="km-calendar" key="hour" />
                           <select id="lunarEndMinute" name="lunarEndMinute">
							   <c:forEach items="${calendarMinuteList}" var="minute" >
								   <option value="${minute}">${minute}</option>
							   </c:forEach>
                           </select>
                           <bean:message bundle="km-calendar" key="minute" />
	                 	</div>
	                </div>
	               </td>
	            </tr>
				<%--日程相关人--%>
				<tr>
					<td width="15%" class="td_normal_title" valign="top">
						<bean:message bundle="km-calendar" key="kmCalendarMain.fdRelatedPersons" />
					</td>
					<td width="80%" colspan="3">
					<xform:address propertyId="fdRelatedPersonIds" propertyName="fdRelatedPersonNames" showStatus="edit"
						   orgType="ORG_TYPE_PERSON" mulSelect="true" style="width:90%" onValueChange="addAuthReaders"></xform:address>
					</td>
				</tr>

	            <tr style="border-bottom: 1px dashed #ccc;"><td colspan="4"></td></tr>
	            <%--公历重复设置（开始）--%>
	            <tr id="tr_recurrence" style="display: none;">
	            	<%--重复类型--%>
	            	<td width="15%" class="td_normal_title" >
	            		<bean:message bundle="km-calendar" key="kmCalendarMain.fdRecurrenceType" />
	            	</td>
	            	<td>
	            		<xform:select property="RECURRENCE_FREQ" showPleaseSelect="false" showStatus="edit" htmlElementProperties="id='recurrence_freq'">
							<xform:enumsDataSource enumsType="km_calendar_recurrence_freq" />
						</xform:select>
	            	</td>
	            </tr>
	            <tr id="moreset" style="display: none;">
		            <td colspan="4">
		            	<table class="tb_simple" width="100%">
		            		<%--重复频率--%>
		            		<tr>
		            			<td width="15%" class="td_normal_title" >
		            				<bean:message bundle="km-calendar" key="kmCalendarMain.recurrenceInterval" />
		            			</td>
				             	<td colspan="3">
				             		<bean:message bundle="km-calendar" key="each" />
				             		<select name="RECURRENCE_INTERVAL" id="interval" class="interval">
			                              <c:forEach  begin="1" end="50" varStatus="index" >
			                              		<option value="${index.count}" <c:if test="${kmCalendarMainForm.RECURRENCE_INTERVAL == index.count}">selected</c:if> >${index.count}</option>
			                              </c:forEach>
			                            </select><span id="fdRecurrenceUnit" class="unit"></span>
				             	</td>
		            		</tr>
		            		<%--重复时间：星期一、星期二…………--%>
		            		<tr class="recurrence_time_type">
		            			<td width="15%" class="td_normal_title" >
		            				<bean:message bundle="km-calendar" key="kmCalendarMain.recurrenceTimeType" />
		            			</td>
				             	<td colspan="3">
				             		<xform:checkbox  property="RECURRENCE_WEEKS" showStatus="edit" required="true">
										<xform:enumsDataSource enumsType="km_calendar_recurrence_week" />
									</xform:checkbox >
				             	</td>
		            		</tr>
		            		<%--重复时间：每周的某天、每月的某天--%>
		            		<tr class="recurrence_time_type">
		            			<td width="15%" class="td_normal_title" >
		            				<bean:message bundle="km-calendar" key="kmCalendarMain.recurrenceTimeType" />
		            			</td>
				             	<td colspan="3">
				             		<xform:radio  property="RECURRENCE_MONTH_TYPE" showStatus="edit">
										<xform:enumsDataSource enumsType="km_calendar_recurrence_month_type" />	
									</xform:radio>
				             	</td>
		            		</tr>
		            		<%--结束条件--%>
		            		<tr class="recurrence_end_type">
		            			<td width="15%" class="td_normal_title"  valign="top">
		            				<bean:message bundle="km-calendar" key="kmCalendarMain.recurrenceEndType" />
		            			</td>
			             		<td colspan="3">
			             			<%--从不结束--%>
			             			<div>
			             				<input type="radio" name="RECURRENCE_END_TYPE" value="NEVER" <c:if test="${kmCalendarMainForm.RECURRENCE_END_TYPE==null || kmCalendarMainForm.RECURRENCE_END_TYPE=='NEVER'}"> checked="checked"</c:if> class="never">
			             				<span><bean:message bundle="km-calendar"  key="recurrence.end.type.never" /></span>
			             			</div>
			             			<%--发生X次后结束--%>
			             			<div>
			             				<input type="radio" name="RECURRENCE_END_TYPE" value="COUNT" <c:if test="${kmCalendarMainForm.RECURRENCE_END_TYPE=='COUNT'}"> checked="checked"</c:if>>
			             				<bean:message bundle="km-calendar"  key="recurrence.end.type.count" />
			             				<xform:text  showStatus="edit"  validators="count" property="RECURRENCE_COUNT"  style="width:20%;height:20px;margin:5px;"  className="re_count"/>
			             				<bean:message bundle="km-calendar" key="times" />
			             			</div>
			             			<%--直到某天结束--%>
			             			<div>
			             				<span style="position: relative;top:-5px;"><input type="radio" name="RECURRENCE_END_TYPE" value="UNTIL" <c:if test="${kmCalendarMainForm.RECURRENCE_END_TYPE=='UNTIL'}"> checked="checked"</c:if>>
			             					<bean:message bundle="km-calendar"  key="recurrence.end.type.until" />
			             				</span>
			             				<xform:datetime showStatus="edit"   property="RECURRENCE_UNTIL" onValueChange="untilChange"  style="width:20%;height:23px;margin:5px;" dateTimeType="date"  />
			             			</div>
			             		</td>
		            		</tr>
		            		<%--摘要--%>
		            		<tr>
			            		<td width="15%" class="td_normal_title" >
			            			<bean:message bundle="km-calendar" key="kmCalendarMain.summary" />
			            		</td>
				             	<td colspan="3">
				             		<input type="hidden" name="RECURRENCE_SUMMARY" />
				             		<span id="summary" class="summary"></span>
				             	</td>
		            		</tr>
		            	</table>
		            </td>
	            </tr>
	            <%--重复设置（结束）--%>
	            <%--农历重复设置（开始）--%>
	            <tr id="tr_recurrence_lunar" style="display: none;">
	            	<td width="15%" class="td_normal_title" >
	            		<bean:message bundle="km-calendar" key="kmCalendarMain.fdRecurrenceType" />
	            	</td>
	            	<td>
	            		<xform:select property="RECURRENCE_FREQ_LUNAR" showPleaseSelect="false" showStatus="edit" htmlElementProperties="id='recurrence_freq_lunar'">
							<xform:enumsDataSource enumsType="km_calendar_recurrence_freq_lunar" />
						</xform:select>
	            	</td>
	            </tr>
	            <tr id="moreset_lunar" style="display:none; " >
	            	<td colspan="4">
		            	<table class="tb_simple" width="100%">
		            		<%--重复频率--%>
		            		<tr>
		            			<td width="15%" class="td_normal_title" >
		            				<bean:message bundle="km-calendar" key="kmCalendarMain.recurrenceInterval" />
		            			</td>
			             		<td colspan="3">
			             		<bean:message bundle="km-calendar" key="each" />
			             		<select name="RECURRENCE_INTERVAL_LUNAR" id="interval" class="interval">
		                              <c:forEach  begin="1" end="50" varStatus="index" >
		                              		<option value="${index.count}" <c:if test="${kmCalendarMainForm.RECURRENCE_INTERVAL_LUNAR == index.count}">selected</c:if> >${index.count}</option>
		                              </c:forEach>
		                            </select><span id="fdRecurrenceUnitLunar" class="unit"></span>
			             	</td>
		            		</tr>
		            		<%--结束条件--%>
		            		<tr class="recurrence_end_type">
		            			<td width="15%" class="td_normal_title"  valign="top">
		            				<bean:message bundle="km-calendar" key="kmCalendarMain.recurrenceEndType" />
		            			</td>
			             		<td colspan="3">
			             			<%--从不结束--%>
			             			<div>
			             				<input type="radio" name="RECURRENCE_END_TYPE_LUNAR" value="NEVER" <c:if test="${kmCalendarMainForm.RECURRENCE_END_TYPE_LUNAR==null || kmCalendarMainForm.RECURRENCE_END_TYPE_LUNAR=='NEVER'}"> checked="checked"</c:if> class="never">
			             				<span><bean:message bundle="km-calendar"  key="recurrence.end.type.never" /></span>
			             			</div>
			             			<%--发生X次后结束--%>
			             			<div>
			             				<input type="radio" name="RECURRENCE_END_TYPE_LUNAR" value="COUNT" <c:if test="${kmCalendarMainForm.RECURRENCE_END_TYPE_LUNAR=='COUNT'}"> checked="checked"</c:if>>
			             				<bean:message bundle="km-calendar"  key="recurrence.end.type.count" />
			             				<xform:text showStatus="edit" validators="count" property="RECURRENCE_COUNT_LUNAR" style="width:20%;height:20px;margin:5px;"  className="re_count"/>
			             				<bean:message bundle="km-calendar" key="times" />
			             			</div>
			             			<%--直到某天结束--%>
			             			<div>
			             				<span style="position: relative;top:-5px;"><input type="radio" name="RECURRENCE_END_TYPE_LUNAR" value="UNTIL" <c:if test="${kmCalendarMainForm.RECURRENCE_END_TYPE_LUNAR=='UNTIL'}"> checked="checked"</c:if>>
			             					<bean:message bundle="km-calendar"  key="recurrence.end.type.until" />
			             				</span>
			             				<xform:datetime showStatus="edit"  property="RECURRENCE_UNTIL_LUNAR"  onValueChange="untilChange" style="width:20%;height:23px;margin:5px;" dateTimeType="date"  />
			             			</div>
			             		</td>
		            		</tr>
		            		<tr>
		            			<td width="15%" class="td_normal_title" >
		            				<bean:message bundle="km-calendar" key="kmCalendarMain.summary" />
		            			</td>
			             		<td colspan="3">
				             		<input type="hidden" name="RECURRENCE_SUMMARY_LUNAR" />
				             		<span id="summaryLunar" class="summary"></span>
			             		</td>
		            		</tr>
		            	</table>
	            	</td>
	            </tr>
	           	<%--农历重复设置（结束）--%>
	           	<tr>
	           		<%--提醒设置--%>
	           	   <td width="15%" class="td_normal_title"  valign="top">
	           	   		<bean:message bundle="km-calendar" key="kmCalendarMain.fdNotifySet" />
	           	   </td>
		           <td colspan="3"  style="padding: 0px;">
						<c:import url="/km/calendar/km_calendar_main/kmCalendarNotifyRemindMain_edit.jsp" charEncoding="UTF-8">
						    <c:param name="formName" value="kmCalendarMainForm" />
					         <c:param name="fdKey" value="kmCalenarMainDoc" />
					         <c:param name="fdPrefix" value="event" />
					         <c:param name="fdModelName" value="com.landray.kmss.km.calendar.model.KmCalendarMain" />
					    </c:import>
					</td>
	           </tr>
	            <tr>
	            	<%--关联URL--%>
	            	<td width="15%" class="td_normal_title">
	            		<bean:message bundle="km-calendar" key="kmCalendarMain.fdRelationUrl" />
	            	</td>
	            	<td>
	            		<xform:text property="fdRelationUrl" showStatus="edit" style="width:90%" validators="testURL" htmlElementProperties="placeholder='${lfn:message('km-calendar:kmCalendarMain.url.placeholder.message')}'"/>
	            	</td>
	            </tr>
	            <tr>
	            	<%--地点--%>
	            	<td width="15%" class="td_normal_title" >
	            		<bean:message bundle="km-calendar" key="kmCalendarMain.fdLocation" />
	            	</td>
	            	<td>
	            		<xform:text property="fdLocation" showStatus="edit" style="width:90%"/>
	            	</td>
	            </tr>
				<%--描述--%>
				<tr>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-calendar" key="kmCalendarMain.fdDesc" />
					</td>
					<td>
					<xform:textarea property="fdDesc" showStatus="edit" style="width:90%" validators="maxLength(200)"/>
					</td>
				</tr>
	            <tr style="border-bottom: 1px dashed #ccc;"><td colspan="4"></td></tr>
	            <tr>
	            	<%--活动性质--%>
	            	<td width="15%" class="td_normal_title" >
	            		<bean:message bundle="km-calendar" key="kmCalendarMain.fdAuthorityType" />
	            	</td>
	            	<td>
	            		<xform:radio property="fdAuthorityType" showStatus="edit"  onValueChange="authorityTypeSelect">
							<xform:enumsDataSource enumsType="km_calendar_fd_authority_type" />
						</xform:radio>
	            	</td>
	            </tr>
	            <tr class="auth_type_desc" style="display: none;">
	            	<td width="15%" class="td_normal_title" ></td>
	            	<td style="padding-top: 0px;color: red;">
	            		<p style="width:92%"><bean:message bundle="km-calendar" key="kmCalendarMain.fdAuthorityType.private.desc"/></p>
	            	</td>
	            </tr>
	        </table>
	        <table id="event_auth_tb" class="tb_simple" width="100%" style="display: none;">
	            <tr>
	            	<%--可阅读者--%>
					<td width="15%" class="td_normal_title">
						<bean:message bundle="km-calendar" key="kmCalendarMain.authReader" />
					</td>
					<td width="85%" colspan="3">
						<div _xform_type="address">
							<xform:address showStatus="edit" propertyId="authReaderIds" propertyName="authReaderNames" orgType="ORG_TYPE_ALL" mulSelect="true"  textarea="true"  style="width:90%" ></xform:address>
						</div>
						<br>
					</td>
				</tr>
				 <tr>
				 	<%--可编辑者--%>
				 	<td width="15%" class="td_normal_title">
						<bean:message bundle="km-calendar" key="kmCalendarMain.authEditor" />
					</td>
					<td width="85%" colspan="3">
						<div _xform_type="address">
							<xform:address showStatus="edit" propertyId="authEditorIds" propertyName="authEditorNames" orgType="ORG_TYPE_ALL" mulSelect="true"  textarea="true"  style="width:90%" ></xform:address>
						</div>
						<br>
					</td>
				 </tr>
			</table>
			<div style="margin: 0 auto;width: 200px;" class="button_div">
				<center>
					<ui:button text="${lfn:message('button.save')}"  onclick="save
					();"/>&nbsp;
					<c:if test="${kmCalendarMainForm.method_GET=='edit' }">
						<ui:button text="${lfn:message('button.delete')}"  styleClass="lui_toolbar_btn_gray" onclick="deleteDoc();"/>&nbsp;
					</c:if>
			        <ui:button text="${lfn:message('button.close')}"  styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();"/> 
		        </center>
			</div>
	</html:form>
	</template:replace>
</template:include>
<script>Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|eventbus.js|xform.js|calendar.js|form.js",null,"js");</script>
<script type="text/javascript" src="${LUI_ContextPath}/km/calendar/resource/js/solarAndLunar.js"></script>
<script type="text/javascript" src="${LUI_ContextPath}/km/calendar/resource/js/jquery.addSelectOption.js"></script>
<script language="JavaScript">
  var _validator = $KMSSValidation(document.forms['kmCalendarMainForm']);
  var NameValidators = {
	  'testURL' : {
		  error : "<bean:message bundle="km-calendar" key="kmCalendarMain.fdRelationUrl" />",
		  test : function (value) {
		  	//如果没有值 则说明不需要进行判断
		  	if (!value){
		  		return true;
			}
			if (value.indexOf('https')>-1 || value.indexOf('http')>-1 || value.indexOf('dingtalk')>-1){
				return true;
			}else {
				// 验证模块是否存在
				return checkModule(value);
			}
		  	return true;
		  }
	  }
  };
  _validator.addValidators(NameValidators);
  //检查模块是否存在
  function checkModule(url){
	  var checkResult = false;
	  var reqUrl = '<c:url value="/km/calendar/km_calendar_main/kmCalendarMain.do?method=checkModule"/>'+"&innerUrl="+url;
	  $.ajax({
		  url: reqUrl,
		  type: "GET",
		  dataType:"json",
		  //设置为同步
		  async: false,
		  error: function(){
			  checkResult = false;
		  },
		  success: function(re){
			  checkResult = re.result;
		  }
	  });
	  return checkResult;
  }


</script>
<script>
	seajs.use(['theme!form']);
	seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar','lui/dateUtil'], function($, dialog,topic,toolbar,dateutil) {
		
		var eventValidation=null;
		var label_html_all = null;
		var label_html_myEvent = "<option value=\"\" >${lfn:message('km-calendar:module.km.calendar.tree.my.calendar')}</option>";

		//常用字段
		var unitValue="YEARLY MONTHLY WEEKLY DAILY";//年月周日
		var weekValue=(function(){
			var shortNameValue="${lfn:message('calendar.week.shortNames')}".split(',');//周缩写
			var shortNameKey=["SU","MO","TU","WE","TH","FR","SA"];
			var tmp={};
			for(var i=0;i<shortNameKey.length;i++){
				tmp[shortNameKey[i]]=shortNameValue[i];
			}
			return tmp;
		})();//格式:{"SU":"日","MO":"一","TU":"二","WE":"三","TH":"四","FR":"五","SA":"六"};
		
		//修改摘要
		var summary=function(){
			var summaryText=new Array();
			var isLunar=$("#fdIsLunar").is(':checked');
			summaryText.push(isLunar ? "${lfn:message('km-calendar:lunar')}" : "${lfn:message('km-calendar:solar')}");//日历类型
			var set=isLunar?$("#moreset_lunar"):$("#moreset");
			var intervalStr="${lfn:message('km-calendar:kmCalendarMain.summary.interval')}"
				.replace("%interval%",set.find(".interval").val())
				.replace("%unit%",set.find(".unit").text());
			summaryText.push(intervalStr);//每隔xx(天、周、月、年)
			//公历下周、月特殊处理
			if(!isLunar){
				var unit=set.find(".unit").text();
				//如果是周重复
				if(unit=="${lfn:message('km-calendar:week')}"){
					var weekDays=$(".recurrence_time_type").eq(0).find(":checkbox").filter(':checked').map(function(){
						return weekValue[$(this).val()];
					}).get();
					if(weekDays.length){
						if(Com_Parameter['Lang']!=null && (Com_Parameter['Lang']=='zh-cn' || Com_Parameter['Lang']=='zh-hk')){
							summaryText.push("${lfn:message('km-calendar:week')}"+weekDays.join('、'));
						}else{
							summaryText.push(weekDays.join('、'));//周一、二、三……
						}
					}
				//如果是月重复
				}else if(unit=="${lfn:message('km-calendar:month')}"){
					var type=$(".recurrence_time_type").eq(1).find(":radio").filter(':checked').val();
					var d=$(":input[name=docStartTime]").eq(0).val();
					var date=new Date();
					if(d!=""){
						date=formatDate(d,"${formatter}");
					}
					if(type=="month"){
						var eachMonthStr="${lfn:message('km-calendar:kmCalendarMain.summary.eachMonth')}"
							.replace("%day%",date.getDate());
						summaryText.push(eachMonthStr);//每月第N天
					}else{
						var eachWeekStr="${lfn:message('km-calendar:kmCalendarMain.summary.eachWeek')}"
							.replace("%order%",Math.ceil(date.getDate() / 7)).replace("%week%","${lfn:message('calendar.week.names')}".split(',')[date.getDay()]);
						summaryText.push(eachWeekStr);//第N个周日、一、二……
					}
				}
			}
			var endType=set.find(".recurrence_end_type").find(":radio").filter(':checked').val();
			if(endType=="NEVER"){
				summaryText.push("${lfn:message('km-calendar:recurrence.end.type.never')}");//从不结束
			}else if(endType=="COUNT"){
				var countStr="${lfn:message('km-calendar:kmCalendarMain.summary.freqEnd')}"
					.replace('%count%',set.find(".re_count").val());
				summaryText.push(countStr);//重复N次结束
			}else{
				var endDate=isLunar?$("[name=RECURRENCE_UNTIL_LUNAR]").val():$("[name=RECURRENCE_UNTIL]").val();
				summaryText.push("${lfn:message('km-calendar:recurrence.end.type.until')}" + endDate);//直到yyyy-MM-dd结束
			}
			set.find(".summary").html($.map(summaryText, function(t){ return ['<span style="color:blue;margin-right:10px;">', t, '</span>'].join(''); }).join(''));
		};

		window.setLabelSelected=function(){
			if(typeof "${kmCalendarMainForm.labelId}"!="undefined"){
				if("${kmCalendarMainForm.fdIsGroup}"=="true"){
					$("#event_base_tb :input[name='labelId']").val("myEvent");
				}else{
					$("#event_base_tb :input[name='labelId']").val("${kmCalendarMainForm.labelId}");
				}
			}													
		};
		//是否显示公历重复信息
		window.displayMoreSet=function(self){
			var moreset=$("#moreset");
			var option=$("option:selected",self);
			var value=option.attr("value"),text=option.text();
			if (unitValue.indexOf(value) > -1) {
				$(".recurrence_time_type").hide();
				//如果是天重复
				if("DAILY"==value){
					moreset.find(".unit").text("${lfn:message('km-calendar:daily')}");
				}
				//如果是年重复
				if("YEARLY"==value){
					moreset.find(".unit").text("${lfn:message('km-calendar:year')}");
				}
				//如果是周重复
				if("WEEKLY"==value){
					moreset.find(".unit").text("${lfn:message('km-calendar:week')}");
					$(".recurrence_time_type").eq(0).show();
				}
				//如果是月重复
				if("MONTHLY"==value){
					moreset.find(".unit").text("${lfn:message('km-calendar:month')}");
					$(".recurrence_time_type").eq(1).show();
				}
				moreset.show();
			}
			else{
				moreset.hide();
			}
			summary();
		};
		
		//是否显示农历历重复信息
		window.displayMoreSetLunar=function(self){
			var option=$("option:selected",self);
			var value=option.attr("value"),text=option.text();
			//如果农历重复
			if ("MONTHLY YEARLY".indexOf(value) > -1) {
				//如果是农历月重复
				if(value=="MONTHLY"){
					$("#fdRecurrenceUnitLunar").text("${lfn:message('km-calendar:month')}");
				}
				if(value=="YEARLY"){
					$("#fdRecurrenceUnitLunar").text("${lfn:message('km-calendar:year')}");
				}
				$("#moreset_lunar").show();
			}
			else{
				$("#moreset_lunar").hide();
			}
			summary();
		};

		//公历重复类型修改时触发
		$("#recurrence_freq").change(function(){
			displayMoreSet(this);
		});
		//农历重复类型修改时触发
		$("#recurrence_freq_lunar").change(function(){
			displayMoreSetLunar(this);
		});
		//重复频率变化时修改摘要
		$(".interval").change(function(){
			summary();
		});
		//重复时间变化时修改摘要
		$(":checkbox[name=_RECURRENCE_WEEKS],:radio[name=RECURRENCE_MONTH_TYPE]").click(function(){
			summary();
		});
		//结束条件变化时修改摘要
		$(":radio[name=RECURRENCE_END_TYPE],:radio[name=RECURRENCE_END_TYPE_LUNAR]").click(function(){
			summary();
		});
		//结束次数变化时修改摘要
		$(".re_count").blur(function(){
			summary();
		});
		
		//结束时间变化时修改摘要
		window.untilChange=function(){
			summary();
		};

		//开始时间改变时，校验结束时间是否小于开始时间，如果小于将结束时间=开始时间
		window.docStartTimeChange=function(obj){
			if($("[name='docFinishTime']").val()==null || $("[name='docFinishTime']").val()==""){
				return;
			}
			var docStartTime=formatDate($("[name='docStartTime']").val(),"${formatter}");
			var docFinishTime=formatDate($("[name='docFinishTime']").val(),"${formatter}");
			if(docStartTime.getTime()>docFinishTime.getTime()){
				$("[name='docFinishTime']").val($("[name='docStartTime']").val());	
			}
		};
		
		var data_start = [];
		var data_end = [];

		
		//初始化农历级联下拉框
		window.initLunar=function(startDate,endDate){
			lunarDate = lunar(startDate);
			$startYear = $('#lunarStartYear');//农历开始年
			var year = lunarDate.sYear + 1984;
			$startYear.val(year);
			
			$startYear.change(function(evt){
				_calYearData("start");
				_fillMonth(evt,"start");
				_fillDate(evt,"start");
			}).change();//切换农历年时触发
			
			$('#lunarStartMonth').off('change').change(function(evt){
				_fillDate(evt,"start");
			}).change();

			if(endDate){
				lunarDate = lunar(endDate);
				$endYear = $('#lunarEndYear');
				year = lunarDate.sYear + 1984;
				$endYear.val(year);
				$endYear.change(function(evt){
					_calYearData("end");
					_fillMonth(evt,"end");
					_fillDate(evt,"end");
				}).change();
				$('#lunarEndMonth').off('change').change(function(evt){
					_fillDate(evt,"end");
				}).change();
			}
		};

		window._fillMonth=function(evt, preName){
			if(preName == "start"){
				$month = $('#lunarStartMonth');
				$date = $('#lunarStartDay');
				data = data_start;
			}
			if(preName == "end"){
				$month = $('#lunarEndMonth');
				$date = $('#lunarEndDay');
				data = data_end;
			}
			var month = evt.isTrigger ? ((lunarDate.isLeap ? 'r' : '') + (lunarDate.monthIndex + 1)) : $month.val().slice(-($month.val() - 1));
			var m = "";
			for(var i = 0;i < data.length;i++){
				if(data[i].text.indexOf(lunarDate.lMonth) > -1){
					m += '<option idx="'+i +'" value="'+data[i].value+'"  selected="selected">'+data[i].text+'</option>';
				}else{
					m += '<option idx="'+i +'" value="'+data[i].value+'">'+data[i].text+'</option>';
				}
			}
			$month.empty().append(m);
			$month.change();
		};
		window._fillDate=function(evt, preName){
			var $month;
			var $date;
			var data;
			if(preName == "start"){
				$month = $('#lunarStartMonth');
				$date = $('#lunarStartDay');
				data = data_start;
			}
			if(preName == "end"){
				$month = $('#lunarEndMonth');
				$date = $('#lunarEndDay');
				data = data_end;
			}
			var idx = $month.find('option:selected').attr('idx');
			var date = evt.isTrigger ? lunarDate.dateIndex + 1 : $date.val();
			var dates = data[idx].days;
			var d = "";
			for(i=0;i<dates.length;i++){
				if(date-1==i){
					d += '<option date="'+i +'" value="'+dates[i].value+'"  selected="selected">'+dates[i].text+'</option>';
				}else{
				d += '<option date="'+i +'" value="'+dates[i].value+'">'+dates[i].text+'</option>';
				}
			}
			$date.empty().append(d);
			$date.change();
		};
		window._calYearData=function(preName){
			var $year;
			var data;
			if(preName == "start"){
				$year = $('#lunarStartYear');
				data = data_start;
			}
			if(preName == "end"){
				$year = $('#lunarEndYear');
				data = data_end;
			}
			var ar = lunar(new Date($year.val(), 2, 1)).getMonthInfo(); 
			var date = ar.solarSpringDay;
			var obj;
				$.each(ar, function(index, o){
					obj = data[index] = {
						value: (o.isLeap ? 'r' : '') + ((o.index - 2) % 12 + 1),
						text: o.name + '月',
						days: []
					};
					$.each(new Array(o.days), function(dayIndex){
						obj.days.push({
							value: dayIndex + 1,
							text: Lunar.DB.dateCn[dayIndex],
							date: [date.getFullYear(), date.getMonth() + 1, date.getDate()].join('-')
						});
						date.setDate(date.getDate() + 1);
					});
				}
			);
		};

		//公历开始时间改变
		window.startMinuteSelect=function(){
			var orgEndV = $("#endMinute").val();
			if(parseInt($("#startHour").val())>=22){
				$("#endHour").val(23);
				$("#endMinute").val(59);	
			}else{
				$("#endHour").val(parseInt($("#startHour").val())+1);
				$("#endMinute").val($("#startMinute").val());
			}
			//赋值不成功（因为可能后台设置刻度变化，某些分钟刻度已不可见），填充原值
			if(!$("#endMinute").val() && !!orgEndV){
				$("#endMinute").val(orgEndV);
			}
		};

		//农历开始时间改变
		window.lunarTimeSelect=function(){
			if(parseInt($("#lunarStartHour").val())>=22){
				$("#lunarEndHour").val(23);
				$("#lunarEndMinute").val(59);	
			}else{
				$("#lunarEndHour").val(parseInt($("#lunarStartHour").val())+2);
				$("#lunarEndMinute").val($("#lunarStartMinute").val());
			}	
		};
		
		$(document).ready(function(){
			var isAllday=$("#fdIsAlldayevent").prop('checked');
			if(isAllday)
				$("#event_kmCalenarMainDoc_false").hide();
			else
				$("#event_kmCalenarMainDoc_true").hide();
			
			var docOwner = $('select[id="docOwnerId"]');
			if(docOwner && docOwner.length>0){
				docOwner.css('width','75px');
			}
			//隐藏公开属性
			var publicNode = $('input[name="fdAuthorityType"][value="PUBLIC"]');
			if(publicNode && publicNode.length>0){
				publicNode.parent().hide();
			}
		});
		
		window.clearTr=function(){
			$("#event_kmCalenarMainDoc_true tbody td span a").each(function(){
				$(this).trigger('click');
			});
			$("#event_kmCalenarMainDoc_false tbody td span a").each(function(){
				$(this).trigger('click');
			});
		};
		
		//是否全天
		window.changeAllDayValue=function(){
			clearTr();
			var isAllday=$("#fdIsAlldayevent").prop('checked');
			if(isAllday){
				$("#event_kmCalenarMainDoc_false").hide();
				$("#event_kmCalenarMainDoc_true").show();
				$("#startTimeDiv,#endTimeDiv").css("display","none");
				$("#startTimeDivLunar,#endTimeDivLunar").css("display","none");
			}else{
				$("#event_kmCalenarMainDoc_false").show();
				$("#event_kmCalenarMainDoc_true").hide();
				var date = new Date();
				var hours = date.getHours();
				var minutes = date.getMinutes();
				$("#startHour").val(date.getHours()+1);
				//$("#startMinute").val(date.getMinutes());
				if(date.getHours()>=22){
					$("#endHour").val(23);
					$("#endMinute").val(59);
				}else{
					date.setHours(date.getHours()+2);
					$("#endHour").val(date.getHours());
					//$("#endMinute").val(date.getMinutes());	
				}
				
				$("#startTimeDiv,#endTimeDiv").css("display","inline");
				$("#startTimeDivLunar,#endTimeDivLunar").css("display","inline");
			}
		};
		
		//是否农历
		window.changeLunarValue=function(){
			var isLunar=$("#fdIsLunar").prop('checked');
			if(isLunar){
				var date = new Date();
				var hours = date.getHours();
				var minutes = date.getMinutes();
				$("#lunarStartHour").val(date.getHours()+1);
				//$("#lunarStartMinute").val(date.getMinutes());
				if(date.getHours()>=22){
					$("#lunarEndHour").val(23);
					$("#lunarEndMinute").val(59);
				}else{
					date.setHours(date.getHours()+2);
					$("#lunarEndHour").val(date.getHours());
					//$("#lunarEndMinute").val(date.getMinutes());	
				}
				
				$("#tr_recurrence_lunar").show();
				displayMoreSetLunar($("#recurrence_freq_lunar")[0]);//是否显示农历重复信息
				$("#tr_recurrence,#moreset").hide();
				$("#div_lunar").css("display","inline");
				$("#div_solar").css("display","none");
				var docStartTime = $("[name='docStartTime']").val();
				var docFinishTime = $("[name='docFinishTime']").val();
				initLunar(formatDate(docStartTime,"${formatter}"),formatDate(docFinishTime,"${formatter}"));
			}else{
				$("#tr_recurrence").show();
				displayMoreSet($("#recurrence_freq")[0]);//是否显示公历重复信息
				$("#tr_recurrence_lunar,#moreset_lunar").hide();
				$("#div_lunar").css("display","none");
				$("#div_solar").css("display","inline");
			}
			changeAllDayValue();
		};
		
		//保存日程
		var __isSubmit = false;
		window.save=function(){
			if(__isSubmit){
				return;
			}
			var docSubject = $("[name='docSubject']").val();
			if($("#event_auth_tb").is(":visible") && (docSubject == '' || docSubject.length > 500))
				window.parent.$("#event_base_label").trigger("click");
			var method = "saveEvent";
			var last_method = Com_GetUrlParameter(window.location.href, "method");
			if("edit"==last_method){
				method = "updateEvent";
			}
			var isLunar=$("#fdIsLunar").prop('checked');
			if(isLunar){
				var lunarStartYear = $('#lunarStartYear').val();
				var lunarStartMonth = $('#lunarStartMonth').val();
				var lunarStartDay = $('#lunarStartDay').val();
				var isLeap = false;
				if(lunarStartMonth.indexOf("r")>-1){
					lunarStartMonth = lunarStartMonth.substring(1);
					isLeap = true;
				}
				var solarStartDate = getSolarDate(lunarStartYear,lunarStartMonth,lunarStartDay,isLeap);
				var solarMonth = solarStartDate.getMonth()+1;
				var solarStartDateStr = solarStartDate.format("${formatter}");
				$("[name='docStartTime']").val(solarStartDateStr);

				var lunarEndYear = $('#lunarEndYear').val();
				var lunarEndMonth = $('#lunarEndMonth').val();
				var lunarEndDay = $('#lunarEndDay').val();
				isLeap = false;
				if(lunarEndMonth.indexOf("r")>-1){
					lunarEndMonth = lunarEndMonth.substring(1);
					isLeap = true;
				}
				var solarEndDate = getSolarDate(lunarEndYear,lunarEndMonth,lunarEndDay,isLeap);
				solarMonth = solarStartDate.getMonth()+1;
				var solarEndDateStr = solarEndDate.format("${formatter}");
				$("[name='docFinishTime']").val(solarEndDateStr);
				
			}
			//校验
			if(eventValidation.validate()==false){
				return false;
			}
			//校验开始时间不能晚于结束时间
			var _startTime=$("[name='docStartTime']").val();
			var _finishTime=$("[name='docFinishTime']").val();
			//非全天.加上时、分
			var isAllDay = $("#fdIsAlldayevent").prop('checked');
			if(!isAllDay){
				//农历的时分
				if($("#fdIsLunar").prop('checked')){
					var lunarStartHour=$("#lunarStartHour option:selected").val();
					if(lunarStartHour.length==1){
						lunarStartHour="0"+lunarStartHour;
					}
					_startTime+=" "+lunarStartHour+":"+$("#lunarStartMinute option:selected").val()+":00";
					_finishTime+=" "+$("#lunarEndHour option:selected").val()+":"+$("#lunarEndMinute option:selected").val()+":00";
				//公历的时分
				}else{
					var startHour=$("#startHour option:selected").val();
					var finishHour=$("#endHour option:selected").val();
					if(startHour.length==1){
						startHour="0"+startHour;
					}
					if(finishHour.length==1){
						finishHour="0"+finishHour;
					}
					_startTime+=" "+startHour+":"+$("#startMinute option:selected").val()+":00";
					_finishTime+=" "+finishHour+":"+$("#endMinute option:selected").val()+":00";
				}
			}
			 if(Date.parse(Com_GetDate(_finishTime))<Date.parse(Com_GetDate(_startTime))){
				 //开始时间不能晚于结束时间
				 dialog.alert("${lfn:message('km-calendar:kmCalendarMain.tip.validateDate.errorDate')}");
		    	return false;
			}
			//校验发生次数
			for(var i=0;i<$(".re_count").size();i++){
				var item=$(".re_count").eq(i);
				//选中了才校验
				if(item.is(":visible")&&item.prev().prop("checked")==true){
					if(!/^[1-9]\d*$/.test(item.val())||item.val().length<=0){
						//发生次数不能为空且必须为数字
						dialog.alert("${lfn:message('km-calendar:kmCalendarMain.tip.validateCount.errorCount')}");
						return false;
					}
				}
			}
			//校验结束条件的时间
			if(validateUntilTime($("[name=RECURRENCE_UNTIL]"),$("[name='RECURRENCE_END_TYPE'][value='UNTIL']"))==false){
				return false;
			}
			if(validateUntilTime($("[name=RECURRENCE_UNTIL_LUNAR]"),$("[name='RECURRENCE_END_TYPE_LUNAR'][value='UNTIL']"))==false){
				return false;
			}
			//校验提醒
			var notifyDate =  Com_GetDate(_startTime);
			if(sysNotifyRemind_validate && !sysNotifyRemind_validate(notifyDate,isAllDay) ){
				return false;
			}
			//beforeSubmit(document.kmCalendarMainForm);
			var url='${LUI_ContextPath}/km/calendar/km_calendar_main/kmCalendarMain.do?method='+method;

			<c:if test="${not empty kmCalendarMainForm.docContent }">
				var oEditor = eval("CKEDITOR.instances.docContent");
				$("[name='docContent']").val(oEditor.getData());
			</c:if>
			__isSubmit = true;
			$.ajax({
				url: url,
				type: 'POST',
				dataType: 'json',
				async: false,
				data: $("#eventform").serialize(),
				beforeSend:function(){
					//window.loading = dialog.loading();
				},
				success: function(data, textStatus, xhr) {//操作成功
					if (data && data['status'] == true) {
						if(data['changeGroup'] == true){
							dialog.failure('<bean:message bundle="km-calendar" key="kmCalendarMain.event.not.exchangeGroupEvent" />');
						}else if(data['isSelf']==false)
							dialog.success('<bean:message key="return.optSuccess" />');
						//	window.loading.hide();
						if(window.$dialog!=null){
							$dialog.hide({"schedule":data['schedule'],"isRecurrence":data['isRecurrence'],"isSelf":data['isSelf'],"method":method});
							//时间相等 结束时间减去一分钟
							var end = handler4equal(data['schedule'].start,data['schedule'].end);
						    data['schedule'].end=end;
						}else{
							window.close();
						}
					}else{
						dialog.failure('<bean:message key="return.optFailure" />');
						if(window.$dialog!=null){
							$dialog.hide(null);
						}else{
							window.close();
						}
					}
				},
				error:function(xhr, textStatus, errorThrown){//操作失败
					dialog.failure('<bean:message key="return.optFailure" />');
					if(window.$dialog!=null){
						$dialog.hide(null);
					}else{
						window.close();
					}
				}
			});
		}; 
		
		//判断起始时间和结束时间是否相等，相等的话结束时间+一分钟
		function handler4equal(start,end){
			if(dateutil.parseDate(start).getTime()==dateutil.parseDate(end).getTime()){
				var datetime =dateutil.parseDate(end).getTime();
				datetime = datetime + (60*1000);
				var date = new Date(datetime);
				end = dateutil.formatDate(date,"yyyy-MM-dd HH:mm:ss");
			}
			return end;
		};

		window.beforeSubmit=function(formObj){
			if(formObj.onsubmit!=null && !formObj.onsubmit()){
				return false;
			}
			//提交表单消息确认
			for(var i=0; i<Com_Parameter.event["confirm"].length; i++){
				if(!Com_Parameter.event["confirm"][i]()){
					return false;
				}
			}
		};

		//校验结束条件时间(radio:结束条件的单选框)
		var validateUntilTime=function(validator,radio){
			//选中了才校验
			if(validator.is(":visible")&&radio.prop("checked")==true){
				//校验结束条件日期不能为空
				if(validator.val().length<=0){
					dialog.alert("${lfn:message('km-calendar:kmCalendarMain.tip.validateUntilTime.notNull')}");
					return false;
				}
				//校验结束条件日期格式
				if(chkDateFormat(validator.val())==false){
					dialog.alert("${lfn:message('km-calendar:kmCalendarMain.tip.validateUntilTime.errorFormat')}");
					return false;
				}
				//校验日期不能早于日程开始时间
				if(compareDate(validator.val(),$("[name='docStartTime']").val())<0){
					dialog.alert("${lfn:message('km-calendar:kmCalendarMain.tip.validateUntilTime.errorDate')}");
					return false;
				}
			}
			return true;
		};
		
		//修改日程所有者
		window.changeOwner = function(){
			var url = '/km/calendar/km_calendar_label/kmCalendarLabel.do?method=listJson';
			var docOwner=$("#docOwnerId"),
				selectedIndex = docOwner.get(0).selectedIndex,
				selectedValue = docOwner.get(0).value;
			if(selectedIndex == 0){
				//日程所有者为自己时可选标签
				LUI("labelId").source.url = url;
				LUI("labelId").source.get();							
			}else if(docOwner.val()=="multiCreate"){
				//日程所有者为群组时只可以选默认标签(即"我的日历")
				$(".fixedSelect").html(label_html_myEvent);
			}else{
				//日程所有者为别人时只可以选系统标签
				LUI("labelId").source.url = url + "&userId=" + docOwner.val();
				LUI("labelId").source.get();				
			}
			//创建群组日程
			if(docOwner.val()=="multiCreate"){
				//显示群组地址本
				$("#multiOwner").show();
				_validator.addElements($('#multiOwner')[0],'required');
				$form("authReaderIds").val("");
				$form("authReaderNames").val("");
				$form("authEditorIds").val("");
				$form("authEditorNames").val("");
			}else{
				//隐藏群组地址本
				$("#multiOwner").hide();
				_validator.removeElements($('#multiOwner')[0],'required');
				//非群组日历,修改权限
				$.get('${LUI_ContextPath}/km/calendar/km_calendar_main/kmCalendarMain.do?method=loadAuthData&personId='+selectedValue,function(data){
					if(data){
						$form("authReaderIds").val(data.readersIdStr);
						$form("authReaderNames").val(data.readersNameStr);
						$form("authEditorIds").val(data.modifiersIdStr);
						$form("authEditorNames").val(data.modifiersNameStr);
					}
				});
			}
			showAuthTypeDesc();
		};

		//删除文档
		window.deleteDoc=function(){
			var fdId="${kmCalendarMainForm.fdId}";
			var url="${LUI_ContextPath}/km/calendar/km_calendar_main/kmCalendarMain.do?method=delete&fdId="+fdId;
			var config = {url:url,type:'GET'};
			var fdIsGroup = "${kmCalendarMainForm.fdIsGroup}";
			if(fdIsGroup == "true")
				Com_Parameter.ComfirmDelete = "<bean:message bundle='km-calendar' key='kmCalendarMain.groupEvent.delete.msg2' />";
			Com_Delete(config,function(data){
				if(data!=null && data.status==true){
					$dialog.hide({"method":"delete"});
					//LUI('calendar').removeSchedule(fdId);//删除日程
				}
				else{
					dialog.failure('<bean:message key="return.optFailure" />');
				}
			});
		};

		//切换到日程基础信息标签(时间管理模块下打开时用)
		window.parent.$("#event_base_label").mousedown(function(){
			window.parent.$("#event_base_label").addClass("current");
			window.parent.$("#event_auth_label").removeClass("current");
			$("#event_auth_tb").hide();
			$("#event_base_tb").show();
			$(".button_div").show();
		});

		//切换到日程权限标签(时间管理模块下打开时用)
		window.parent.$("#event_auth_label").mousedown(function(){
			window.parent.$("#event_base_label").removeClass("current");
			window.parent.$("#event_auth_label").addClass("current");
			$("#event_auth_tb").show();
			$("#event_base_tb").hide();
			$(".button_div").hide();
		});

		//加载校验框架
		eventValidation=$KMSSValidation();
		
		//初始化
		var _solar=$("#div_solar"),_lunar=$("#div_lunar");
		if("${kmCalendarMainForm.fdIsLunar}"=="true"){//农历
			$("#tr_recurrence_lunar").show();
			_lunar.show();
			_solar.find(".startTime,.endTime").css("display","none");
			_lunar.find(".startTime,.endTime").css("display","inline");
			var docStartTime = $("[name='docStartTime']").val();
			var docFinishTime = $("[name='docFinishTime']").val();
			initLunar(formatDate(docStartTime,"${formatter}"),formatDate(docFinishTime,"${formatter}"));
		}else{//公历
			$("#tr_recurrence").show();
			_solar.show();
			_solar.find(".startTime,.endTime").css("display","inline");
			_lunar.find(".startTime,.endTime").css("display","none");
		}
		if("${kmCalendarMainForm.fdIsAlldayevent}"=="true"){//全天
			$(".startTime,.endTime").css("display","none");
		}else{//非全天
			$("#startHour").val(${kmCalendarMainForm.startHour});
			$("#endHour").val(${kmCalendarMainForm.endHour});
			$("#lunarStartHour").val(${kmCalendarMainForm.lunarStartHour});
			$("#lunarEndHour").val(${kmCalendarMainForm.lunarEndHour});

			//初始化分钟-刻度列表后，没有对应选项则新增选项值option #170616
			//console.log("event...");
			var startM = '${kmCalendarMainForm.startMinute}',
					endM = '${kmCalendarMainForm.endMinute}',
					lStartM = '${kmCalendarMainForm.lunarStartMinute}',
					lEndM = '${kmCalendarMainForm.lunarEndMinute}';
			if(!!startM){
				//原值为单数值
				var intsV = parseInt(startM);
				addSelectOption($("#startMinute"),[{"value":intsV ,"name":intsV}],function (){
					$("#startMinute").val(intsV);
				});
			}
			if(!!endM) {
				var inteV = parseInt(endM);
				addSelectOption($("#endMinute"),[{"value":inteV,"name":inteV}],function (){
					$("#endMinute").val(inteV);
				});
			}

			if(!!lStartM) {
				var intlsV = parseInt(lStartM);
				addSelectOption($("#lunarStartMinute"),[{"value":intlsV,"name":intlsV}],function (){
					$("#lunarStartMinute").val(intlsV);
				});
			}
			if(!!lEndM) {
				var intleV = parseInt(lEndM);
				addSelectOption($("#lunarEndMinute"),[{"value":intleV,"name":intleV}],function (){
					$("#lunarEndMinute").val(intleV);
				});
			}
		}
		//是否显示重复信息
		if('${kmCalendarMainForm.RECURRENCE_FREQ}'!=null && '${kmCalendarMainForm.RECURRENCE_FREQ}' != 'NO'){
			displayMoreSet($("#recurrence_freq"));
		}
		//是否显示农历重复信息
		if('${kmCalendarMainForm.RECURRENCE_FREQ_LUNAR}'!=null && '${kmCalendarMainForm.RECURRENCE_FREQ_LUNAR}' != 'NO'){
			displayMoreSetLunar($("#recurrence_freq_lunar"));
		}
		label_html_all = $("#labelId").html();

		//提示：以下人员授权您为他/她创建日程
		/* $("#docOwnerId").click(function(){
			if($("#docOwnerId option").length>1){
				$("#ownerTip").toggle();
			}
		}); */

		//非中文环境下无显示农历选项
		if(Com_Parameter['Lang']!=null && Com_Parameter['Lang']!='zh-cn'&& Com_Parameter['Lang']!='zh-hk'){
			$("#fdIsLunar").hide().next().hide();
		}

		Com_AddEventListener(window, "load", function(){
			window.personIds=document.getElementsByName("fdRelatedPersonIds")[0].value;
		});

		//相关人与可阅读者联动
		window.addAuthReaders = function(value){
			var names = value[1];
			var ids = value[0];
			var result = [];
			var idResult = "";
			var nameResult = "";
			var address = Address_GetAddressObj("authReaderNames");
			var authIds = "";
			var authNames = "";
			if(address) {
				authIds = document.getElementsByName("authReaderIds")[0].value;
				authNames = document.getElementsByName("authReaderNames")[0].value;
			}
			var authIdArr = authIds.split(";");
			var authNameArr = authNames.split(";");
			//删除之前的，添加现在的
			if(window.personIds && window.personIds != ids){
				//取阅读者数据[]，如果在之前存在，且新的不存在，则删除，如果前面存在，后边存在，忽略。如果前面不存在，后面存在，则保留
				for(var i=0; i<authIdArr.length; i++){
					//1、如果在之前存在，且新的不存在，则删除
					if(window.personIds.indexOf(authIdArr[i]) != -1 && ids.indexOf(authIdArr[i]) ==-1){
						continue;
					}else{
						if(authIdArr[i]) {
							var obj = {id: authIdArr[i], name: authNameArr[i]};
							result.push(obj);
							idResult += authIdArr[i] + ";"
							nameResult += authNameArr[i] + ";";
						}
					}
				}
			}else{
				for(var i=0; i<authIdArr.length; i++){
					if(authIdArr[i]) {
						var obj = {id: authIdArr[i], name: authNameArr[i]};
						result.push(obj);
						idResult += authIdArr[i] + ";"
						nameResult += authNameArr[i] + ";";
					}
				}
			}
			var idArr = ids.split(";");
			var nameArr =names.split(";");
			for(var i=0; i<idArr.length; i++){
				if(idResult.indexOf(idArr[i]) != -1){
					continue;
				}
				var obj={id:idArr[i],name:nameArr[i]};
				result.push(obj);
				idResult += idArr[i]+";"
				nameResult += nameArr[i]+";";
			}
			//修改之前的
			window.personIds = ids;
			//清空地址本，调用添加
			address.emptyAddress();
			address.idField.value = idResult != "" ? idResult.substring(0,idResult.length-1) : idResult;
			address.nameField.value = nameResult != "" ? nameResult.substring(0,nameResult.length-1) : nameResult;
			address.addData(result);

		}
		
		window.authorityTypeSelect=function(value){
		    if(value=="PUBLIC"||value=="PRIVATE"){
		    	window.parent.$("#event_auth_label").hide().prev().hide();
			}else{
				window.parent.$("#event_auth_label").show().prev().show();
			}
		    showAuthTypeDesc();
		};
		
		window.showAuthTypeDesc=function(){
			var authType = $("input[name='fdAuthorityType']:checked").val(),
				docOwner = $("#docOwnerId").val(),
				typeDesc = $(".auth_type_desc");
			if (authType == 'PRIVATE' && docOwner == 'multiCreate') {
				typeDesc.show();
			} else {
				typeDesc.hide();
			}
		};
		
		var value="${kmCalendarMainForm.fdAuthorityType}";
	    if(value=="PUBLIC"||value=="PRIVATE"){
	    	window.parent.$("#event_auth_label").hide().prev().hide();
		}else{
			window.parent.$("#event_auth_label").show().prev().show();
		}
	});
</script>