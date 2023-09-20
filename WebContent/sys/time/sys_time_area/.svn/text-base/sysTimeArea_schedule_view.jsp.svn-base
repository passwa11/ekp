<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="title">
		<bean:message  bundle="sys-time" key="sysTimeArea.button.workView"/>
	</template:replace>
	<template:replace name="head">
		<template:super/>
		<link href="resource/css/maincss.css" rel="stylesheet">
		<link href="resource/css/css.css" rel="stylesheet">
		<script type="text/javascript">
			Com_IncludeFile("dialog.js");
		</script>
	</template:replace>
	<template:replace name="body">
		<center>
			<c:if test="${sysTimeAreaForm.fdIsBatchSchedule == false }">
			<div class="lui_workforce_management_container">
				<div class="lui_workforce_management_tab">
			      <div class="lui_workforce_management_tab-wrap">
			        <div class="lui_workforce_management_tab-heading" style="display: none;">
			          <span><bean:message  bundle="sys-time" key="calendar.data.list"/></span>
			        </div>
			        <div class="lui_workforce_management_tab-list-wrap">
			          <ul class="lui_workforce_management_tab-list">
			            <li class="active">
			              	<bean:message  bundle="sys-time" key="table.sysTimeWorkTime"/><bean:message  bundle="sys-time" key="calendar.data.list1"/>
			            </li>
			            <li>
			              	<bean:message  bundle="sys-time" key="table.sysTimeWorkTime"/><bean:message  bundle="sys-time" key="calendar.data.list2"/>
			            </li>
			          </ul>
			        </div>
			        <div id="btnEditCalendar" class="hidden" data-active="false">
						<bean:message key="button.edit"/>
			        </div>
			      </div>
			      <div class="lui_workforce_management_tab-content">
			        <ul class="lui_workforce_management_tab-content-list">
			          <li class="active">
			            <div class="lui_workforce_management-inner-tab">
			              <div class="lui_workforce_management-inner-tab-wrap">
			                <ul class="lui_workforce_management-inner-tab-list">
			                  <li class="active">
			                   	 <bean:message  bundle="sys-time" key="calendar.data.list.worktime"/>
			                  </li>
			                  <li>
			                  	<bean:message  bundle="sys-time" key="calendar.data.list.holiday"/>
			                  </li>
			                  <li>
			                  	<bean:message  bundle="sys-time" key="calendar.data.list.pach"/>
			                  </li>
			                </ul>
			              </div>
			              <div class="lui_workforce_management-inner-tab-content-wrap">
			                <ul class="lui_workforce_management-inner-tab-content-list">
			                  <li class="active">
			                      <iframe id="workTimeContent" style="min-height: 300px;" src='<c:url value="/sys/time/sys_time_work/sysTimeWork.do?method=list&sysTimeAreaId="/>${sysTimeAreaForm.fdId}' width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
			                      <div class="lui_workforce_management-table-remark">
			                        	${lfn:message('sys-time:sysTimeArea.descriptption')}<br>
									    ${lfn:message('sys-time:sysTimeArea.descriptption.area')}<br>
									    ${lfn:message('sys-time:sysTimeArea.descriptption.time')}<br>
									    ${lfn:message('sys-time:sysTimeArea.descriptption.voa')}<br>
									    ${lfn:message('sys-time:sysTimeArea.descriptption.patch')}<br>
									    ${lfn:message('sys-time:sysTimeArea.descriptption.patch5')}<br>
									    ${lfn:message('sys-time:sysTimeArea.descriptption.patch6')}<br>
									    
									    ${lfn:message('sys-time:sysTimeArea.notice')} <br>
									    ${lfn:message('sys-time:sysTimeArea.notice1')}<br>
									    ${lfn:message('sys-time:sysTimeArea.notice2')}<br>
									    
									    ${lfn:message('sys-time:sysTimeArea.notice.example')}<br>
									    ${lfn:message('sys-time:sysTimeArea.notice.example1')}<br>
									    ${lfn:message('sys-time:sysTimeArea.notice.example2')}<br>
			                      </div>
			                  </li>
			                  <li>
			                  	<div class="lui_page_panel_accordion">
							      <div class="heading">
							        <i class="accordion_arrow"></i>
							        <h4 class="heading_title" align="left" style="font-size: 12px">${lfn:message('sys-time:sysTimeHolidayDetail.custom.vacation')}</h4>
							      </div>
							      <div class="content">
					                    <iframe style="min-height: 300px;" id="vacationContent" src='' width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
							      </div>
							    </div>
			                  </li>
			                  <li>
			                  	<div class="lui_page_panel_accordion">
							      <div class="heading">
							        <i class="accordion_arrow"></i>
							        <h4 class="heading_title" align="left" style="font-size: 12px">${lfn:message('sys-time:sysTimeHolidayDetail.custom.pachwork')}</h4>
							      </div>
							      <div class="content">
					                    <iframe style="min-height: 300px;" id="patchWorkContent" src='' width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
							      </div>
							    </div>
			                  </li>
			                </ul>
			              </div>
			            </div>
			          </li>
			          <li>
						<iframe id="calendarContent" src="" width="100%" height="1300px" frameborder="0" scrolling="no"></iframe>
			          </li>
			        </ul>
			      </div>
			    </div>
		    </div>
			<script>
			var count = 0;
			seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		    	//打开日历编辑页面
			    $('#btnEditCalendar').click(function() {
			    	var ieVersion = checkIEBrowser();
			    	if(ieVersion > 0 && ieVersion < 9) {
			    		alert('为了获得最佳操作体验，建议您使用最新版本的浏览器（IE9及以上）');
			    	} else {
			    		//选择排班方式
			    		var isScheduled = "true";
			    		<c:if test="${empty sysTimeAreaForm.sysTimeWorkList}">
			    			isScheduled = "false";
			    		</c:if>
			    		var id = "${sysTimeAreaForm.fdId}";
			    		var url = "/sys/time/sys_time_area/sysTimeArea_selectCalendar.jsp?fdId=" + id;
			    		var pUrl = '<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=editMCalendar&fdId=' + id;
			 			var bUrl = '<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=editCalendar&fdId=' + id;
			 			if(isScheduled=='true'){
			 				url = bUrl;
			 				Com_OpenWindow(url);
			 				return;
			 			}
			    		 dialog.iframe(url,
			 			            '选择排班方式',
			 			            function(result) {
			 			                if (!result) { return; }
			 			               	if(result.value==1){
			 			               		url = bUrl;
			 			               	}else{
			 			               		url = pUrl;
			 			               	}
			 			               Com_OpenWindow(url);
			 			            },
			 			            {
			 			                width: 650,
			 			                height: 350,
			 			                params: {
			 			                    data: null,
			 			                    method: 'add'
			 			                }
			 			            }
			 			        );
			    	}
			    });
		    });
			seajs.use(['lui/jquery'],function($){
				/*外选项卡*/
				$('.lui_workforce_management_tab-list>li').click(function() {
				  $(this).parent().find('li').removeClass('active');
				  $(this).addClass('active');
				  
				  $('#btnEditCalendar').attr('data-active', 'false');
				  
				  var idx = $(this).index();
				  if(idx==1){
					  $("#calendarContent").attr("src",'<c:url value="/sys/time/sys_time_area/sysTimeArea.do?method=viewCalendar&fdId="/>${sysTimeAreaForm.fdId}');
					  $('#btnEditCalendar').attr('data-active', 'true');
				  }
				  var showContent = '.lui_workforce_management_tab-content-list>li:eq(' + idx + ')';
				  $(this).parents('.lui_workforce_management_tab').find('.lui_workforce_management_tab-content-list>li').css({'display': 'none'});
				  $(this).parents('.lui_workforce_management_tab').find(showContent).fadeIn()
				})
				
				/*内选项卡*/
				$('.lui_workforce_management-inner-tab-list>li').click(function() {
				  $(this).parent().find('li').removeClass('active');
				  $(this).addClass('active');
				  var idx = $(this).index();
				  if(idx==0){
					  //iFrameHeight("workTimeContent");
					  window.clearInterval(tid);
					  count=0;
					  tid = window.setInterval("iFrameHeight('workTimeContent')",1000);
				  }
				  if(idx==1){
					  $("#vacationContent").attr("src",'<c:url value="/sys/time/sys_time_vacation/sysTimeVacation.do?method=list&sysTimeAreaId="/>${sysTimeAreaForm.fdId}&fdHolidayId=${sysTimeAreaForm.fdHolidayId}&type=vacation');
					  //iFrameHeight("vacationContent");
					  window.clearInterval(tid);
					  count=0;
					  tid = window.setInterval("iFrameHeight('vacationContent')",1000);
				  }else if(idx==2){
					  $("#patchWorkContent").attr("src",'<c:url value="/sys/time/sys_time_patchwork/sysTimePatchwork.do?method=list&sysTimeAreaId="/>${sysTimeAreaForm.fdId}&fdHolidayId=${sysTimeAreaForm.fdHolidayId}&type=pachwork');
					  //iFrameHeight("patchWorkContent");
					  window.clearInterval(tid);
					  count=0;
					  tid = window.setInterval("iFrameHeight('patchWorkContent')",1000);
				  }
				  var showContent = '.lui_workforce_management-inner-tab-content-list>li:eq(' + idx + ')';
				  $(this).parents('.lui_workforce_management-inner-tab').find('.lui_workforce_management-inner-tab-content-list>li').css({'display': 'none'});
				  $(this).parents('.lui_workforce_management-inner-tab').find(showContent).fadeIn()
				})
				var tid,ifh,count=0;
				$(function(){
					tid = window.setInterval("iFrameHeight('workTimeContent')",1000);
				});
			    $(function(){
			      $(".lui_page_panel_accordion .heading").bind('click', function() {
			        var tc2 = $(this).toggleClass('accordion').next(".content").find("#vacationContent").contents().find("#c2").attr("style");
			        if(tc2&&tc2!=""&&tc2.indexOf("display: block;")>-1){
			      	  $(this).toggleClass('accordion');
			        }else if(tc2&&tc2!=""){
			      	  $(this).toggleClass('accordion');
			        }
			        $(this).toggleClass('accordion').next(".content").find("#vacationContent").contents().find("#c2").slideToggle(300);
			        
			        var tc1 = $(this).toggleClass('accordion').next(".content").find("#patchWorkContent").contents().find("#c1").attr("style");
			        if(tc1&&tc1!=""&&tc1.indexOf("display: block;")>-1){
			      	  $(this).toggleClass('accordion');
			        }else if(tc1&&tc1!=""){
			      	  $(this).toggleClass('accordion');
			        }
			        $(this).toggleClass('accordion').next(".content").find("#patchWorkContent").contents().find("#c1").slideToggle(300);
			      });
				    
			    });
			});
			/* ===============iframe高度自适应=============*/
			function iFrameHeight(id) {
				try {
				 var browserVersion = window.navigator.userAgent.toUpperCase();
				 var isOpera = false, isFireFox = false, isChrome = false, isSafari = false, isIE = false;
			    isOpera = browserVersion.indexOf("OPERA") > -1 ? true : false;
		        isFireFox = browserVersion.indexOf("FIREFOX") > -1 ? true : false;
		        isChrome = browserVersion.indexOf("CHROME") > -1 ? true : false;
		        isSafari = browserVersion.indexOf("SAFARI") > -1 ? true : false;
		        if (!!window.ActiveXObject || "ActiveXObject" in window)
		            isIE = true;
		        var iframe = document.getElementById(id);
		        var bHeight = 0;
		        if (isChrome == false && isSafari == false){
		        	if(iframe.contentWindow.document.getElementsByTagName("table").length>1){
		        		bHeight = iframe.contentWindow.document.getElementsByTagName("table")[1].scrollHeight+105;
		        	}else{
			            bHeight = iframe.contentWindow.document.getElementsByTagName("table")[0].scrollHeight;
		        	}
		        }
		        var dHeight = 0;
		        if (isFireFox){
		        	if(iframe.contentWindow.document.getElementsByTagName("table").length>1){
		        		dHeight = iframe.contentWindow.document.getElementsByTagName("table")[1].offsetHeight + 105;
		        	}else{
			            dHeight = iframe.contentWindow.document.getElementsByTagName("table")[0].offsetHeight + 2;
		        	}
		        }else if (!isIE && !isOpera){
		        	if(iframe.contentWindow.document.getElementsByTagName("table").length>1){
		        		dHeight = iframe.contentWindow.document.getElementsByTagName("table")[1].scrollHeight+105;
		        	}else{
		        		dHeight = iframe.contentWindow.document.getElementsByTagName("table")[0].scrollHeight;
		        	}
		            
		        }else if (isIE && -[1,] ) {
		        } else{//ie9+
		            bHeight += 3;
		        }
		        var height = Math.max(bHeight, dHeight);
		        if("vacationContent"==id){
		        	if(height<=40)
				        height += 350;
		        	var if_h = $("#vacationContent").contents().find("#hv").contents().find("#List_ViewTable").height();
			        height += if_h+300;
		        }else if("patchWorkContent"==id){
		        	if(height<=40)
				        height += 350;
		        	var if_h = $("#patchWorkContent").contents().find("#hp").contents().find("#List_ViewTable").height();
		        	height += if_h+300;
		        }
		        iframe.style.height = height + "px";
		        count+=1;
		        if(count>5){
		        	window.clearInterval(tid);
		        	count=0;
		        }
				} catch (ex) {
		        }
			}
			
			//检查ie浏览器
			function checkIEBrowser() {
		        var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
		        var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
		        var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
		        var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
		        if (isIE) {
		            var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
		            reIE.test(userAgent);
		            var fIEVersion = parseFloat(RegExp["$1"]);
		            if (fIEVersion == 7) {
		                return 7;
		            } else if (fIEVersion == 8) {
		                return 8;
		            } else if (fIEVersion == 9) {
		                return 9;
		            } else if (fIEVersion == 10) {
		                return 10;
		            } else {
		                return 6; //IE版本<=7
		            }
		        } else if (isEdge) {
		            return 12; //edge
		        } else if (isIE11) {
		            return 11; //IE11  
		        } else {
		            return -1; //不是ie浏览器
		        }
		    }
			</script>
				
			</c:if>
			<c:if test="${sysTimeAreaForm.fdIsBatchSchedule == true }">
				<div class="lui_workforce_management_container">
					<div class="lui_workforce_management_tab">
				      <div class="lui_workforce_management_tab-wrap">
				        <div class="lui_workforce_management_tab-list-wrap">
				          <ul class="lui_workforce_management_tab-list">
				            <li class="active">
				            	排班日历
				            </li>
				          </ul>
				        </div>
				        <div id="btnEditCalendar" data-active="true">
							<bean:message key="button.edit"/>
				        </div>
				      </div>
				      <div class="lui_workforce_management_tab-content">
				        <ul class="lui_workforce_management_tab-content-list">
				          <li class="active">
							<iframe id="calendarContent" src='<c:url value="/sys/time/sys_time_area/sysTimeArea.do?method=viewMCalendar&fdId=${sysTimeAreaForm.fdId}"/>' width="100%" height="1300px" frameborder="0" scrolling="no"></iframe>
				          </li>
				        </ul>
				      </div>
				    </div>
				  </div>
				  
				  <script>
				  	seajs.use(['lui/jquery'],function($){
				  	//打开日历编辑页面
					    $('#btnEditCalendar').click(function() {
							Com_OpenWindow('<c:url value="/sys/time/sys_time_area/sysTimeArea.do?method=editMCalendar&fdId=${sysTimeAreaForm.fdId}"/>');
					    });
				  	})
				  </script>
			
			</c:if>
		</center>
	</template:replace>
</template:include>

