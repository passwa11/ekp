<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%request.setAttribute("formatter", ResourceUtil.getString("date.format.date"));%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
response.setHeader("Pragma","No-cache");    
response.setHeader("Cache-Control","no-cache");    
%>  
<ui:ajaxtext>
<% 
	String prefix=request.getParameter("LUIID").replace("-","");
	request.setAttribute("prefix",prefix);
	request.setAttribute("userPersonId",UserUtil.getUser().getFdId());
%>
<c:set var="jsPrefix" value="${lfn:escapeJs(prefix)}"/>
<c:set var="htmlPrefix" value="${lfn:escapeHtml(prefix)}"/>
<link rel="stylesheet" href="${LUI_ContextPath}/km/calendar/resource/css/calendar_portlet.css" type="text/css" />
<script type="text/javascript">
	seajs.use(['km/calendar/resource/js/calendar_portlet'], function(portlet) {
		 //初始化日历
	     var portletObj=new portlet.CalendarPortlet({'prefix':"${jsPrefix}"});
	     //打开日程对话框
	     window.openEvent=function(url,method,type){
	    	 portletObj.openEvent(url,method,type);
		 };
	});
</script>
   	<div id="${htmlPrefix}calendarDIV" class="calendarDIV">
       <div class="lui_modules_person_history">
		<div class="lui_modules_person_history_content">
           	<div class="lui_modules_person_calendar_person">
                	<div id="${htmlPrefix}calendar">
                   	<table class="table" >
                       	<tr style="height:26px;background: url(${LUI_ContextPath}/km/calendar/resource/images/title_bg.png) repeat-x">
                           	<td colspan="7" valign="middle">
                           		<%--日期翻页--%>
                           		<div style="float: left;margin-left: 3px;">
                           			<img class="prev" src="${LUI_ContextPath}/km/calendar/resource/images/pre.png" width="5" height="8" border="0"  style="cursor: pointer;"/>
                                    <span id="${htmlPrefix}YMBG" ></span>
                                    <img class="next"  src="${LUI_ContextPath}/km/calendar/resource/images/next.png" width="5" height="8" border="0"  style="cursor: pointer;"/>
                           		</div>
                           		<%--回到今天--%>
                           		<div style="right">
                           			<div class="current_left today" >
                                       	<div class="current_right">
                                           	<div class="current_center"> 
                                           		<bean:message key="calendar.today" />
                                           	</div>
                                        </div>
                               		</div>
                               		<div class="current_left refresh" >
                                       	<div class="current_right">
                                           	<div class="current_center"> 
                                           		<bean:message key="button.refresh" />
                                           	</div>
                                        </div>
                               		</div>
                           		</div>
                               </td>
                            </tr>
                            <%--星期标题.从周一开始--%>
                           <tr class="lui_calendar_portlet_weekTr" align="center" bgcolor="#e0e0e0">
                                <td data-lui-mark="calendar_weekName" align="center" bgcolor="#ffffff" width="14%">
                                </td>
                                <td data-lui-mark="calendar_weekName" align="center" bgcolor="#ffffff" width="14%"
                                </td>
                                <td data-lui-mark="calendar_weekName" align="center" bgcolor="#ffffff" width="14%">
                                </td>
                                <td data-lui-mark="calendar_weekName" align="center" bgcolor="#ffffff" width="14%">
                                </td>
                                <td data-lui-mark="calendar_weekName" align="center" bgcolor="#ffffff" width="14%">
                                </td>
                                <td data-lui-mark="calendar_weekName" align="center" bgcolor="#ffffff" width="14%">
                                </td>
                                <td data-lui-mark="calendar_weekName" align="center" bgcolor="#ffffff" width="14%">
                                </td>
                           </tr>
                           <c:forEach  begin="0" end="5" varStatus="i" >
                           		<tr align="center"  id="${htmlPrefix}TR${i.index}">
	                           		<c:forEach  begin="0" end="6" varStatus="j" >
	                           			<c:set value="${i.index*7+j.index}" var="gNum" />
										<td id="${htmlPrefix}GD${gNum}" style="cursor:pointer;height:30px;" class="no_events" bgcolor="white" >
											<div>
												<div align='center' id='${htmlPrefix}SD${gNum}' class='days' style='font-size:14px; height:20px; font-weight:bold; color:#64615a;background-color: #ffffff'  ></div>
											 </div>    
											 <div>
											 	<div align='center' id='${htmlPrefix}LD${gNum}' style='font-size:10px; color:#a7a6a4; height:20px;background-color: #ffffff'></div>
											 </div>                      			
	                           			</td>
	                           		</c:forEach>
                           		</tr>
                           </c:forEach>
						</table>
                     	</div>
                 	</div>
                   	<ul class="lui_modules_person_history_ul1" id="${htmlPrefix}calendarList"></ul>
              	</div>
       		</div>
   		</div>
   		<%--简单弹出框--%>
	   		<script type="text/javascript">
	   			seajs.use(['lui/topic','lui/jquery','km/calendar/resource/js/dateUtil'], function(topic,$,dateutil) {
	   				//获取位置
					var getPos=function(evt,showObj){
						var sWidth=showObj.width() || 350;var sHeight=showObj.height();
						x=evt.pageX;
						y=evt.pageY;
						if(y+sHeight>$(window).height()){
							y-=sHeight;
						}
						if(x+sWidth>$(document.body).outerWidth(true)){
							x-=sWidth;
						}
						return {"top":y,"left":x};
					};
	   				//打开日程简单弹出框
	   				topic.subscribe("kmCalendar_openSimpleDialog",function(args){
		    			var iframeObj = $("#${jsPrefix}calendarDialog");
		   				var selectedTime = dateutil.formatDate(args.date,"${formatter}");
		   				if(iframeObj.length==0){
		   					iframeObj = $('<iframe id="${jsPrefix}calendarDialog" name="${jsPrefix}calendarDialog" frameborder="no"></iframe>');
			   			}

	   					iframeObj.css({
	   						'position':'absolute',
	   						'border':'none',
	   						'z-index': '90',
	   						'display': 'none'
	   					}).attr('width','385')
	   						.attr('height','337')
	   							.attr('src','${LUI_ContextPath}/km/calendar/portlet/edit.jsp?prefix=${jsPrefix}');
	   					iframeObj.appendTo(document.body);
	   					iframeObj.load(function(){
	   						setCalendarInfo();
	   					});
	   					
		   				function setCalendarInfo(){
		   					var iframeDoc=${jsPrefix}calendarDialog.window.document,
		   						addDIV=$(iframeDoc).find("#calendar_add");
	   							   						
							/* LUI.$.ajax({
								url: '<c:url value="/km/calendar/km_calendar_main/kmCalendarMain.do?method=searchOwners"/>',
								type: 'GET',
								dataType: 'json',
								async: false,
								success: function(data, textStatus, xhr) {//操作成功
									$(iframeDoc).find("#multiOwner").hide();
									$(iframeDoc).find("#ownerTip").hide();
									$(iframeDoc).find('#docOwnerId > option').remove('option:not(.fdGroup)');
									for(var i=data.owners.length-1;i>=0;i--){
										var option=$('<option></option>');
										option.attr('value',data.owners[i][0]);
										option.html(data.owners[i][1]);
										if(data.owners[i][0]=="${userPersonId}"){
											//兼容IE浏览器
											setTimeout(function(){
												option.attr('selected','selected');//选中指定项
											},0);	
										}
										$(iframeDoc).find('#docOwnerId').prepend(option);
									}
								}
							}); */
													   						
		   					addDIV.find(['name="docSubject"']).val('');
			   				${jsPrefix}calendarDialog.window.document.getElementById("docStartTime").value = selectedTime;
			   				${jsPrefix}calendarDialog.window.document.getElementById("docFinishTime").value = selectedTime;
			   				${jsPrefix}calendarDialog.window.document.getElementById("startTimeDiv").style.display = "none";
			   				${jsPrefix}calendarDialog.window.document.getElementById("endTimeDiv").style.display = "none";
		   					$("#${jsPrefix}calendarDialog").css(getPos(args.evt,addDIV));
		   					$("#${jsPrefix}calendarDialog").fadeIn("fast");
		   				}
	   				});
	   			});
	   		</script>
  </ui:ajaxtext>