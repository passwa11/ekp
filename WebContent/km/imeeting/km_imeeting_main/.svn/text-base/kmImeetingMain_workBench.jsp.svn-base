<%@page import="com.landray.kmss.common.model.PeriodModel"%>
<%@page import="com.landray.kmss.common.model.PeriodTypeModel"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@page import="java.util.Date"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String userId = UserUtil.getUser().getFdId();
	pageContext.setAttribute("userId", userId);
	Date yearFirst = DateUtil.getBeginDayOfYear();
	Date yearLast = DateUtil.getEndDayOfYear();
	Date monthFirst = DateUtil.getBeginDayOfMonth();
	Date monthLast = DateUtil.getEndDayOfMonth();
	pageContext.setAttribute("yearFirst", DateUtil.convertDateToString(yearFirst, DateUtil.TYPE_DATE, request.getLocale()));
	pageContext.setAttribute("yearLast", DateUtil.convertDateToString(yearLast, DateUtil.TYPE_DATE, request.getLocale()));
	pageContext.setAttribute("monthFirst", DateUtil.convertDateToString(monthFirst, DateUtil.TYPE_DATE, request.getLocale()));
	pageContext.setAttribute("monthLast", DateUtil.convertDateToString(monthLast, DateUtil.TYPE_DATE, request.getLocale()));
	// 本月我即将参加
	//Integer myAttend = service.getAttendStatCount("myAttend",null, monthFirst,monthLast);
	// 本月我已参加
	//Integer myHaveAttend = service.getAttendStatCount("myHaveAttend",null, monthFirst,monthLast);
	// 本月我主持的
	//Integer myHost = service.getAttendStatCount("myHaveAttend","myHost", monthFirst,monthLast);
	// 本月我汇报的
	//Integer myReport = service.getAttendStatCount("myHaveAttend","myReport", monthFirst,monthLast);
	// 本月我组织的
	//Integer myEmcc = service.getAttendStatCount("myHaveAttend","myEmcc", monthFirst,monthLast);
	// 本月我参与的
	//Integer monthMyPart = service.getAttendStatCount("myHaveAttend","myPart", monthFirst,monthLast);
	// 本年我已参加，由于一年数据太多，导致页面加载慢，所以做成异步的
	//Integer yearMyPart = service.getAttendStatCount("myHaveAttend",null, yearFirst, yearLast);
	//pageContext.setAttribute("myAttend", myAttend);
	//pageContext.setAttribute("myHaveAttend", myHaveAttend);
	//pageContext.setAttribute("myHost", myHost);
	//pageContext.setAttribute("myReport", myReport);
	//pageContext.setAttribute("monthMyPart", monthMyPart);
	//pageContext.setAttribute("myEmcc", myEmcc);
	//pageContext.setAttribute("yearMyPart", yearMyPart);
	
	// 会议开始时间和结束时间
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.MINUTE, 0);
	cal.add(Calendar.HOUR_OF_DAY, 1);
	String startDate = DateUtil.convertDateToString(cal.getTime(), "datetime", request.getLocale());
	cal.add(Calendar.HOUR_OF_DAY, 1);
	String finishDate = DateUtil.convertDateToString(cal.getTime(), "datetime", request.getLocale());
	pageContext.setAttribute("startDate", startDate);
	pageContext.setAttribute("finishDate", finishDate);
	
	PeriodTypeModel intervalType = PeriodTypeModel.createInstance(
			PeriodTypeModel.PERIOD_TYPE_MONTH, request.getLocale());
	Calendar calendar = Calendar.getInstance();
	calendar.add(Calendar.YEAR, -1);
	Long yearFirstPeriod = ((PeriodModel)intervalType.getPeriods(calendar.getTime(), 1, request.getLocale()).get(0)).getFdId();
	Long yearLastPeriod = ((PeriodModel)intervalType.getPeriods(new Date(), 1, request.getLocale()).get(0)).getFdId();
	pageContext.setAttribute("yearFirstPeriod", yearFirstPeriod);
	pageContext.setAttribute("yearLastPeriod", yearLastPeriod);
%>
<template:include  ref="default.simple" sidebar="no" spa="true">
	<template:replace name="body">
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/common.css?s_cache=${LUI_Cache}"/>
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/index.css?s_cache=${LUI_Cache}"/>
	<script type="text/javascript">
		seajs.use(['theme!module']);
		Com_IncludeFile("jquery.js");
	</script>
	<div class="lui_meeting">
      <div class="lui_meeting_content">
        <div class="lui_meeting_content_wrap">
          <!-- 会议布局左侧 starts -->
          <div id="lui_meeting_content_left" class="lui_meeting_content_left">
            <div>
              <!-- 会议布局左侧-点击卡 starts -->
              <div class="lui_meeting_content_left_panel">
                <div>
                  <ul class="lui_meeting_panel_click">
                  	<kmss:authShow roles="ROLE_KMIMEETING_BOOKCREATE">
                    <li>
                      <div class="lui_meeting_panel_click_item">
                        <p class="lui_mpci_desc lui_mpci_subscribe" onclick="addImeetingBook();"><bean:message bundle="km-imeeting" key="table.kmImeetingBook"/></p>
                      </div>
                    </li>
                    </kmss:authShow>
                    <kmss:authShow roles="ROLE_KMIMEETING_CREATE">
                    <li>
                      <div class="lui_meeting_panel_click_item">
                        <p class="lui_mpci_desc lui_mpci_arrange" onclick="addImeetingMain();"><bean:message bundle="km-imeeting" key="table.kmImeetingMain"/></p>
                      </div>
                    </li>
                    <% if(KmImeetingConfigUtil.isVideoMeetingEnable()){ %>
	                    <li>
	                      <div class="lui_meeting_panel_click_item">
	                        <p class="lui_mpci_desc lui_mpci_video" onclick="addImeetingVideo();"><bean:message bundle="km-imeeting" key="table.kmImeetingVideo"/></p>
	                      </div>
	                    </li>
                    <% }%>
                    </kmss:authShow>
                  </ul>
                </div>
              </div>
              <!-- 会议布局左侧-点击卡 ends -->

              <!-- 会议布局左侧-多面板展示 starts -->
              <div class="lui_meeting_content_left_panel">
                <!-- 会议布局左侧-多面板展示-单位面板 starts -->
                <div class="lui_meeting_panel_multiple">
                  <div>
                    <h3 class="lui_meeting_panel_multiple_tit">${lfn:message('km-imeeting:workbench.imeeting.this.month') }</h3>
                    <div class="lui_meeting_panel_multiple_sum">
                    	<ui:dataview>
	                      	<ui:source type="AjaxJson">
	                      		{"url":"/km/imeeting/km_imeeting_main/kmImeetingMainStat.do?method=attendStatCount&myType=myAttend&date=month"}
	                      	</ui:source>
	                      	<ui:render type="Template">
	                      		{$<div class="lui_meeting_panel_sepration">
			                        <div class="lui_meeting_panel_wrap">
			                          <div class="lui_meeting_panel_content">
			                            <div class="lui_mpms_wrap">
			                              <p class="lui_mpms_desc">${lfn:message('km-imeeting:workbench.imeeting.ready') }</p>
			                              <a href="javascript:newPage('/myAttend',{cri:{'cri.q':'mymeeting:myAttend;fdHoldDate:${monthFirst };fdHoldDate:${monthLast }'}})" class="lui_mpms_num">{%data.count%}</a>
			                            </div>
			                          </div>
			                        </div>
			                      </div>$}
	                      	</ui:render>
	                    </ui:dataview>
                    </div>
                    <div class="lui_meeting_panel_multiple_item">
                      <div class="lui_meeting_panel_sepration">
                        <div class="lui_meeting_panel_wrap">
                          <div class="lui_meeting_panel_content">
                            <div>
                            	<ui:dataview>
			                      	<ui:source type="AjaxJson">
			                      		{"url":"/km/imeeting/km_imeeting_main/kmImeetingMainStat.do?method=attendStatCount&myType=myHaveAttend&date=month"}
			                      	</ui:source>
			                      	<ui:render type="Template">
			                      		{$<div class="lui_mpmi_tit">
			                                <div>
			                                  <p class="lui_mpmi_tit_desc">${lfn:message('km-imeeting:workbench.imeeting.already') }</p>
			                                  <a href="javascript:newPage('/myAttend',{cri:{'cri.q':'mymeeting:myHaveAttend;fdHoldDate:${monthFirst };fdHoldDate:${monthLast }'}})" class="lui_mpmi_tit_num">{%data.count%}</a>
			                                </div>
			                              </div>$}
			                      	</ui:render>
			                    </ui:dataview>
                              <ul class="lui_mpmi_content">
                                <li>
                                  <div class="lui_mpmi_content_item">
                                    <p class="lui_mpmi_content_item_desc"><bean:message bundle="km-imeeting" key="kmImeeting.Host.my"/></p>
                                    <ui:dataview>
				                      	<ui:source type="AjaxJson">
				                      		{"url":"/km/imeeting/km_imeeting_main/kmImeetingMainStat.do?method=attendStatCount&myType=myHaveAttend&date=month&mydoc=myHost"}
				                      	</ui:source>
				                      	<ui:render type="Template">
				                      		{$<p class="lui_mpmi_content_item_num">{%data.count%}</p>$}
				                      	</ui:render>
				                    </ui:dataview>
                                  </div>
                                </li>
                                <li>
                                  <div class="lui_mpmi_content_item">
                                    <p class="lui_mpmi_content_item_desc"><bean:message bundle="km-imeeting" key="kmImeeting.Report.my"/></p>
                                    <ui:dataview>
				                      	<ui:source type="AjaxJson">
				                      		{"url":"/km/imeeting/km_imeeting_main/kmImeetingMainStat.do?method=attendStatCount&myType=myHaveAttend&date=month&mydoc=myReport"}
				                      	</ui:source>
				                      	<ui:render type="Template">
				                      		{$<p class="lui_mpmi_content_item_num">{%data.count%}</p>$}
				                      	</ui:render>
				                    </ui:dataview>
                                  </div>
                                </li>
                                <li>
                                  <div class="lui_mpmi_content_item">
                                    <p class="lui_mpmi_content_item_desc"><bean:message bundle="km-imeeting" key="kmImeeting.Participate.my"/></p>
                                    <ui:dataview>
				                      	<ui:source type="AjaxJson">
				                      		{"url":"/km/imeeting/km_imeeting_main/kmImeetingMainStat.do?method=attendStatCount&myType=myHaveAttend&date=month&mydoc=myPart"}
				                      	</ui:source>
				                      	<ui:render type="Template">
				                      		{$<p class="lui_mpmi_content_item_num">{%data.count%}</p>$}
				                      	</ui:render>
				                    </ui:dataview>
                                  </div>
                                </li>
                                <li>
                                  <div class="lui_mpmi_content_item">
                                    <p class="lui_mpmi_content_item_desc"><bean:message bundle="km-imeeting" key="kmImeeting.Emcc.my"/></p>
                                    <ui:dataview>
				                      	<ui:source type="AjaxJson">
				                      		{"url":"/km/imeeting/km_imeeting_main/kmImeetingMainStat.do?method=attendStatCount&date=month&mydoc=myEmcc"}
				                      	</ui:source>
				                      	<ui:render type="Template">
				                      		{$<p class="lui_mpmi_content_item_num">{%data.count%}</p>$}
				                      	</ui:render>
				                    </ui:dataview>
                                  </div>
                                </li>
                              </ul>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- 会议布局左侧-多面板展示-单位面板 ends -->
                <!-- 会议布局左侧-多面板展示-单位面板 starts -->
                <div class="lui_meeting_panel_multiple">
                  <div>
                    <h3 class="lui_meeting_panel_multiple_tit"><bean:message bundle="km-imeeting" key="workbench.imeeting.this.year"/></h3>
                    <div class="lui_meeting_panel_multiple_sum">
                      <ui:dataview>
                      	<ui:source type="AjaxJson">
                      		{"url":"/km/imeeting/km_imeeting_main/kmImeetingMainStat.do?method=attendStatCount&myType=myHaveAttend&date=year"}
                      	</ui:source>
                      	<ui:render type="Template">
                      		{$<div class="lui_meeting_panel_sepration">
		                        <div class="lui_meeting_panel_wrap">
		                          <div class="lui_meeting_panel_content">
		                            <div class="lui_mpms_wrap">
		                              <p class="lui_mpms_desc"><bean:message bundle="km-imeeting" key="workbench.imeeting.already"/></p>
		                              <a href="javascript:newPage('/myAttend',{cri:{'cri.q':'mymeeting:myHaveAttend;fdHoldDate:${yearFirst };fdHoldDate:${yearLast }'}})" class="lui_mpms_num">{%data.count%}</a>
		                            </div>
		                          </div>
		                        </div>
		                      </div>$}
                      	</ui:render>
                      </ui:dataview>
                    </div>
                    <div class="lui_meeting_panel_multiple_item">
                      <div class="lui_meeting_panel_sepration">
                        <div class="lui_meeting_panel_wrap">
                          <div class="lui_meeting_panel_tit">
                            <p><bean:message bundle="km-imeeting" key="workbench.imeeting.attend.stat"/></p>
                          </div>
                          <div class="lui_meeting_panel_content">
                            <div>
                              <ui:chart var-themeName="shine" var-noRecordStyle="simple" id="main_chart_part" width="300" height="300" cfg-lineSize="10">
								<ui:source type="AjaxJson">
									{url:'/km/imeeting/km_imeeting_main/kmImeetingMainStat.do?method=attendStat'}
								</ui:source>
							</ui:chart>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="lui_meeting_panel_multiple_item">
                      <div class="lui_meeting_panel_sepration">
                        <div class="lui_meeting_panel_wrap">
                          <div class="lui_meeting_panel_tit">
                            <p><bean:message bundle="km-imeeting" key="workbench.imeeting.cate.stat"/></p>
                          </div>
                          <div class="lui_meeting_panel_content">
                            <div>
                              <ui:chart var-themeName="shine" var-noRecordStyle="simple" id="main_chart_cate" width="300" height="300" cfg-lineSize="10">
								<ui:source type="AjaxJson">
									{url:'/km/imeeting/km_imeeting_main/kmImeetingMainStat.do?method=cateStat'}
								</ui:source>
							</ui:chart>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- 会议布局左侧-多面板展示-单位面板 ends -->
              </div>
            </div>
            <!-- 会议布局左侧-多面板展示 ends -->

          </div>
          <!-- 会议布局左侧 ends -->

          <!-- 会议布局右侧 starts -->
          <div class="lui_meeting_content_right">
            <div class="lui_meeting_content_right_wrap">
              <!-- 会议布局右侧-单位面板 starts -->
              <div class="lui_meeting_content_right_panel lui_meeting_right_panel_calendar">
                <div class="lui_meeting_panel_sepration">
                  <div class="lui_meeting_panel_wrap">
                    <div class="lui_meeting_panel_content" style="padding:0;">
                      <div>
                      	<ui:iframe id="calendarIframe" src="${LUI_ContextPath }/km/imeeting/km_imeeting_calendar/index_content_mycalendar.jsp?isWorkbench=1"></ui:iframe>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- 会议布局右侧-单位面板 ends -->
              <!-- 会议布局右侧-单位面板 starts -->
              <div class="lui_meeting_content_right_panel">
                  <div class="lui_meeting_panel_sepration">
                    <div class="lui_meeting_panel_wrap">
                      <div class="lui_meeting_panel_tit">
                        <p><bean:message bundle="km-imeeting" key="workbench.imeeting.stat.chart"/></p>
                      </div>
                      <div id="mainChartContent" class="lui_meeting_panel_content" style="padding:0;padding-bottom:10px;">
                        <div align="center"> <!-- width="1000" height="334" -->
                          <ui:chart var-themeName="shine" id="main_chart" >
							<ui:source type="AjaxJson">
								{url:'/km/imeeting/km_imeeting_stat/kmImeetingStat.do?method=statChart&fdType=person.statMon&fdDateType=1&fdStartDate=${yearFirstPeriod }&fdEndDate=${yearLastPeriod }&queryCondIds=${userId }&isWorkbench=1'}
							</ui:source>
						</ui:chart>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- 会议布局右侧-单位面板 ends -->
            </div>
          </div>
          <!-- 会议布局右侧 ends -->
        </div>
      </div>
  </div>
  <script>
  	LUI.ready(function() {
  		var mainChart = LUI("main_chart");
  		mainChart.on("load",function() {
  			//resizeMainChart();
  		});
  	});
  	// 调用resize多次某些电脑上会出现显示不全的问题，这里控制只resize一次
  	var isResized = false;
  	function resizeMainChart() {
  		if(!isResized) {
  			var mainChart = LUI("main_chart");
  			var width = $("#mainChartContent").width()-50;
  			var h = $("#lui_meeting_content_left").height() - $(".lui_meeting_content_right_wrap").height();
  			var can = $("#main_chart canvas");
  			var canHeight = can.height() + h;
  			var style = {"width":width,"height":Math.min(Math.max(canHeight,258),500)};
  			can.attr(style).css(style).parent().css(style).parent().css(style).parent().css("width",width+50);
  			$("#main_chart .div_listSection").css(style);
  			var echartsInstance = mainChart.getEchart();
  			echartsInstance.resize(style);
  			isResized = true;
  		}
  	}
  	
  	seajs.use(['lui/dialog','lui/jquery','lui/framework/router/router-utils'],function(dialog,$,utils) {
  		window.addImeetingBook = function() {
			var fdHoldDate='${startDate}';
			var fdFinishDate='${finishDate}';
			var url="/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=add&startDate="+fdHoldDate+"&endDate="+fdFinishDate;
			dialog.iframe(url,"${lfn:message('km-imeeting:kmImeetingBook.opt.create')}",function(result){
				if(result) {
					$("#calendarIframe iframe")[0].contentWindow.location.reload(true);
				}
			},{width:700,height:500});
  		};
  		
  		window.addImeetingMain = function() {
  			dialog.categoryForNewFile(
  					'com.landray.kmss.km.imeeting.model.KmImeetingTemplate',
  					'/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId=!{id}',false);
  		};
  		
  		window.addImeetingVideo = function() {
  			window.open('${LUI_ContextPath }/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&noTemplate=true')
  		};
  		
  		window.newPage = function(href,param) {
  			window.parent.newPageWithParam(href,param);
  		};
  	});
  </script>
	</template:replace>
</template:include>
