<%@page import="java.net.URLEncoder"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.*" %>
<%@page import="net.sf.json.JSONObject" %>

<template:include ref="default.simple">
	<template:replace name="head">
		<template:super />
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/data.js?s_cache=${LUI_Cache}"></script>
		<script>
			Com_IncludeFile('echarts4.2.1.js','${LUI_ContextPath}/sys/ui/js/chart/echarts/','js',true);
		</script>

		<script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>?s_cache=${LUI_Cache}"></script>
		
											
		<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/transition.js"/>?s_cache=${LUI_Cache}"></script>
							
		<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/analysis_upcoming_process.js"/>?s_cache=${LUI_Cache}"></script>
		
		<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/analysis_timeOut_process.js"/>?s_cache=${LUI_Cache}"></script>
		
		<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/analysis_create_process.js"/>?s_cache=${LUI_Cache}"></script>
		
		<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/analysis_approvalTime_process.js"/>?s_cache=${LUI_Cache}"></script>
		
		<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/analysis_nodeApprovalTime_process.js"/>?s_cache=${LUI_Cache}"></script>
		
		<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/analysis_rejectNode_process.js"/>?s_cache=${LUI_Cache}"></script>
		
		<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/analysis_timeoutNode_process.js"/>?s_cache=${LUI_Cache}"></script>
		
		<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/analysis_upcomingNode_process.js"/>?s_cache=${LUI_Cache}"></script>
		
		<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/analysis_peopleApprovalTime_process.js"/>?s_cache=${LUI_Cache}"></script>
		
		<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/analysis_peopleUrgent_process.js"/>?s_cache=${LUI_Cache}"></script>
		
		<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/analysis_peopleTimeOutJump_process.js"/>?s_cache=${LUI_Cache}"></script>
		
		<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/analysis_peopleTimeOutTurnDo_process.js"/>?s_cache=${LUI_Cache}"></script>
		
		<link rel="stylesheet" type="text/css" href="<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/css/analysis.css"/>" media="screen" />
		
		
	</template:replace>
	<template:replace name="body">


		<% if(request.getParameter("s_path")!=null){ %>
		 <span class="txtlistpath"><div class="lui_icon_s lui_icon_s_home" style="float: left;"></div><div style="float: left;"><bean:message key="page.curPath"/>${fn:escapeXml(param.s_path)}</div></span>
		<% } %>
		 <!-- 选项卡头部 Starts -->
    <div class="lui-flowMonitor-tabHead">
      <ul class="lui-flowMonitor-tabs">
        <li class="active"><a href="#tab-content-1" id="tab-1" data-toggle="tab" aria-controls="tab-content-1" aria-expanded="true"><bean:message key="sysLbpmMonitor.analysis.process" bundle="sys-lbpmmonitor" /></a></li>
        <li ><a href="#tab-content-2" id="tab-2" data-toggle="tab" aria-controls="tab-content-2" aria-expanded="false"><bean:message key="sysLbpmMonitor.analysis.node" bundle="sys-lbpmmonitor" /></a></li>
        <li><a href="#tab-content-3" id="tab-3" data-toggle="tab" aria-controls="tab-content-3" aria-expanded="false"><bean:message key="sysLbpmMonitor.analysis.person" bundle="sys-lbpmmonitor" /></a></li>
      </ul>
    </div>
    <!-- 选项卡头部 Ends -->
    <!-- 选项卡内容 Starts -->
    <div class="lui-flowMonitor-tab-content">
      
      <div class="lui-flowMonitor-tab-pane fade active in" id="tab-content-1" aria-labelledby="tab-1">
        <ul class="lui-flowMonitor-statistics-board">
          <li>
            <div class="magnet" style="cursor: pointer " onclick="window.location.href=encodeURI('<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_detail.jsp?s_path=${param.s_path}"/>'); return false">
              <div class="magnet-icon icon-color-1 icon-1"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.UpcomingMaxProcess" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="upcoming_max_process"></p>
            </div>
          </li>
          <li>
            <div class="magnet" style="cursor: pointer " onclick="window.location.href=encodeURI('<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_excuteTimeout_detail.jsp?s_path=${param.s_path}"/>'); return false">
              <div class="magnet-icon icon-color-2 icon-2"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.dealMaxProcess" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="timeOut_max_process"></p>
            </div>
          </li>
          <li>
            <div class="magnet" style="cursor: pointer " onclick="window.location.href=encodeURI('<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_createProcess_detail.jsp?s_path=${param.s_path}"/>'); return false">
              <div class="magnet-icon icon-color-3 icon-3"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.createMaxProcess" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="create_max_process"></p>
            </div>
          </li>
          <li>
            <div class="magnet" style="cursor: pointer " onclick="window.location.href='<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_avg_approvalTime_detail.jsp?s_path=${param.s_path}"/>'; return false">
              <div class="magnet-icon icon-color-4 icon-4"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.avgApprovalTime" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="approvalTime_max_process"></p>
            </div>
          </li>
        </ul>
        <ul class="lui-flowMonitor-chartList">
          <li>
            	<div id="upcoming_process" class="chart-item" style="display: block; height: 420px; text-align: center; font-size: 36px;"></div>
          </li>
          <li>
            <div id="timeOut_process" class="chart-item" style="display: block; height: 420px; text-align: center; font-size: 36px;"></div>
          </li>
          <li>
            <div id="create_process" class="chart-item" style="display: block; height: 420px; text-align: center; font-size: 36px;"></div>
          </li>
          <li>
            	<div id="approvalTime_process" class="chart-item" style="display: block; height: 420px; text-align: center; font-size: 36px;"></div>
          </li>
        </ul>
      </div>
      
       <script type="text/javascript">
       
    	$(function(){
    		var DEFAULT_VERSION = 8.0;  
		    var ua = navigator.userAgent.toLowerCase();  
		    var isIE = ua.indexOf("msie")>-1;  
		    var safariVersion;  
		    if(isIE){  
		    	safariVersion =  ua.match(/msie ([\d.]+)/)[1];  
		    }   
			    
    		var url = '<c:url value="/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do" />';
    		
    		 // TAB CLASS DEFINITION
    		  // ====================

    		  var Tab = function (element) {
    		    // jscs:disable requireDollarBeforejQueryAssignment
    		    this.element = $(element)
    		    // jscs:enable requireDollarBeforejQueryAssignment
    		  }

    		  Tab.VERSION = '3.3.7'

    		  Tab.TRANSITION_DURATION = 150

    		  Tab.prototype.show = function () {
    		    var $this    = this.element

    		    var $ul      = $this.closest('ul:not(.dropdown-menu)')
    		    var selector = $this.data('target')

    		    if (!selector) {
    		      selector = $this.attr('href')
    		      selector = selector && selector.replace(/.*(?=#[^\s]*$)/, '') // strip for ie7
    		    }

    		    
    		    //if ($this.parent('li').hasClass('active')) return

    		    var $previous = $ul.find('.active:last a')
    		    var hideEvent = $.Event('hide.bs.tab', {
    		      relatedTarget: $this[0]
    		    })
    		    var showEvent = $.Event('show.bs.tab', {
    		      relatedTarget: $previous[0]
    		    })

    		    $previous.trigger(hideEvent)
    		    $this.trigger(showEvent)

    		    if (showEvent.isDefaultPrevented() || hideEvent.isDefaultPrevented()) return

    		    var $target = $(selector)

    		    this.activate($this.closest('li'), $ul)
    		    this.activate($target, $target.parent(), function () {
    		      $previous.trigger({
    		        type: 'hidden.bs.tab',
    		        relatedTarget: $this[0]
    		      })
    		      $this.trigger({
    		        type: 'shown.bs.tab',
    		        relatedTarget: $previous[0]
    		      })
    		    })
    		   
    		   console.log("returnTab"+getCookie("returnTab"));
			   if(selector=="#tab-content-1"){
				   
				    //待办最多的流程
		    		window.upComingProcess=initUpComingProcess(url);
		            
		    		 //审批超时最多的流程
		    		window.timeOutProcess=initTimeOutProcess(url);
		    		 
		    		//流程创建数量排名
		    		window.createProcess=initCreateProcess(url);
		    		
		    		//平均审批耗时最长的流程
		    		window.approvalTimeProcess=initApprovalTimeProcess(url);
    				
    				setTimeout(function(){ 
    					window.upComingProcess.resize();
    					window.timeOutProcess.resize();
    					window.createProcess.resize();
    					window.approvalTimeProcess.resize();
    				},200);
    				
    				
    			    if(safariVersion <= DEFAULT_VERSION ){  
    			    	window.attachEvent("resize", function(){ 
        					window.upComingProcess.resize();
        					window.timeOutProcess.resize();
        					window.createProcess.resize();
        					window.approvalTimeProcess.resize();
        				});
    			    }else{
    			    	window.addEventListener("resize", function(){ 
        					window.upComingProcess.resize();
        					window.timeOutProcess.resize();
        					window.createProcess.resize();
        					window.approvalTimeProcess.resize();
        				});
    			    }
    			    
    				
    		    }
    		    
    		    
    		    if(selector=="#tab-content-2"){
    		    	
    		    	//节点平均耗时蓝榜
    	    		window.nodeApprovalTimeProcess =initNodeApprovalTimeProcess(url); 
    	    		
    	    		//驳回次数最多的节点
    	    		window.rejectNodeProcess =initRejectNodeProcess(url); 
    	    		
    	    		//超时次数最多的节点
    	    		window.timeoutNodeProcess =initTimeoutNodeProcess(url);
    	    		
    	    		//待审流程最多的节点
    	    		window.upcomingNodeProcess =initUpcomingNodeProcess(url);
    				
    				setTimeout(function(){ 
    					window.nodeApprovalTimeProcess.resize();
    					window.rejectNodeProcess.resize();
    					window.timeoutNodeProcess.resize();
    					window.upcomingNodeProcess.resize();
    				},200);
    				
    				 if(safariVersion <= DEFAULT_VERSION ){  
    					 window.attachEvent("resize",function(){ 
    	    					window.nodeApprovalTimeProcess.resize();
    	    					window.rejectNodeProcess.resize();
    	    					window.timeoutNodeProcess.resize();
    	    					window.upcomingNodeProcess.resize();
    	    			});
     			    }else{
     			    	window.addEventListener("resize",function(){ 
        					window.nodeApprovalTimeProcess.resize();
        					window.rejectNodeProcess.resize();
        					window.timeoutNodeProcess.resize();
        					window.upcomingNodeProcess.resize();
        				});
     			    }
    				 
    				
    		    }
    		    
    		    
				if(selector=="#tab-content-3"){
					
					//人员平均耗时蓝榜
		    		window.peopleApprovalTimeProcess =initPeopleApprovalTimeProcess(url); 
		    		
		    		//被催办最多的员工
		    		window.peopleUrgentProcess =initPeopleUrgentProcess(url); 
		    		
		    		//超时跳过次数最多的员工
		    		window.peopleTimeOutJumpProcess =initPeopleTimeOutJumpProcess(url);
		    		
		    		//超时转办次数最多的员工排名
		    		window.peopleTimeOutTurnDoProcess =initPeopleTimeOutTurnDoProcess(url);
		    		
    				
    				setTimeout(function(){ 
    					window.peopleApprovalTimeProcess.resize();
    					window.peopleUrgentProcess.resize();
    					window.peopleTimeOutJumpProcess.resize();
    					window.peopleTimeOutTurnDoProcess.resize();
    				},200);
    				
    				if(safariVersion <= DEFAULT_VERSION ){  
    					window.attachEvent("resize",function(){
        					window.peopleApprovalTimeProcess.resize();
        					window.peopleUrgentProcess.resize();
        					window.peopleTimeOutJumpProcess.resize();
        					window.peopleTimeOutTurnDoProcess.resize();
        				});
    			    }else{
    			    	window.addEventListener("resize",function(){
        					window.peopleApprovalTimeProcess.resize();
        					window.peopleUrgentProcess.resize();
        					window.peopleTimeOutJumpProcess.resize();
        					window.peopleTimeOutTurnDoProcess.resize();
        				});
    			    }
    		    }
    		  }

    		  Tab.prototype.activate = function (element, container, callback) {
    		    var $active    = container.find('> .active')
    		    var transition = callback
    		      && $.support.transition
    		      && ($active.length && $active.hasClass('fade') || !!container.find('> .fade').length)

    		    function next() {
    		      $active
    		        .removeClass('active')
    		        .find('> .dropdown-menu > .active')
    		          .removeClass('active')
    		        .end()
    		        .find('[data-toggle="tab"]')
    		          .attr('aria-expanded', false)

    		      element
    		        .addClass('active')
    		        .find('[data-toggle="tab"]')
    		          .attr('aria-expanded', true)

    		      if (transition) {
    		        element[0].offsetWidth // reflow for transition
    		        element.addClass('in')
    		      } else {
    		        element.removeClass('fade')
    		      }

    		      if (element.parent('.dropdown-menu').length) {
    		        element
    		          .closest('li.dropdown')
    		            .addClass('active')
    		          .end()
    		          .find('[data-toggle="tab"]')
    		            .attr('aria-expanded', true)
    		      }

    		      callback && callback()
    		    }

    		    $active.length && transition ?
    		      $active
    		        .one('bsTransitionEnd', next)
    		        .emulateTransitionEnd(Tab.TRANSITION_DURATION) :
    		      next()

    		    $active.removeClass('in')
    		  }


    		  // TAB PLUGIN DEFINITION
    		  // =====================

    		  function Plugin(option) {
    		    return this.each(function () {
    		      var $this = $(this)
    		      var data  = $this.data('bs.tab')

    		      if (!data) $this.data('bs.tab', (data = new Tab(this)))
    		      if (typeof option == 'string') data[option]()
    		    })
    		  }

    		  var old = $.fn.tab

    		  $.fn.tab             = Plugin
    		  $.fn.tab.Constructor = Tab


    		  // TAB NO CONFLICT
    		  // ===============

    		  $.fn.tab.noConflict = function () {
    		    $.fn.tab = old
    		    return this
    		  }


    		  // TAB DATA-API
    		  // ============

    		  var clickHandler = function (e) {
    			  
    		    e.preventDefault()
    		    Plugin.call($(this), 'show')
    		  }

    		  $(document)
    		    .on('click.bs.tab.data-api', '[data-toggle="tab"]', clickHandler)
    		    .on('click.bs.tab.data-api', '[data-toggle="pill"]', clickHandler)
    		 
    		    var returnTab=getCookie("returnTab");
    		  
    		 if(returnTab!=null&&returnTab!=""){
    			 $('#'+returnTab).trigger("click.bs.tab.data-api");
    			 delCookie("returnTab");
    		 }else{
    			 $('#tab-1').trigger("click.bs.tab.data-api");
    		 }
    	});
			
		</script>
		
		
      <div class="lui-flowMonitor-tab-pane fade" id="tab-content-2" aria-labelledby="tab-2">
        <ul class="lui-flowMonitor-statistics-board">
          <li>
            <div class="magnet" style="cursor: pointer " onclick="window.location.href=encodeURI('<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_node_approvalTime_detail.jsp?s_path=${param.s_path}"/>'); return false" >
              <div class="magnet-icon icon-color-5 icon-5"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.avgNodeConsumTime" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="node_max_approvaltime"></p>
            </div>
          </li>
          <li>
            <div class="magnet" style="cursor: pointer " onclick="window.location.href=encodeURI('<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_node_reject_detail.jsp?s_path=${param.s_path}"/>'); return false">
              <div class="magnet-icon icon-color-6 icon-6"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.nodeRejectMax" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="node_max_rejectNode"></p>
            </div>
          </li>
          <li>
            <div class="magnet" style="cursor: pointer " onclick="window.location.href=encodeURI('<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_node_timeout_detail.jsp?s_path=${param.s_path}"/>'); return false">
              <div class="magnet-icon icon-color-7 icon-7"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.nodeTimeOutJumpMuchMax" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="node_max_timeoutNode"></p>
            </div>
          </li>
          <li>
            <div class="magnet" style="cursor: pointer " onclick="window.location.href=encodeURI('<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_node_upcoming_detail.jsp?s_path=${param.s_path}"/>'); return false">
              <div class="magnet-icon icon-color-8 icon-8"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.nodeUpcomingMaxProcess" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="node_max_upcomingNode"></p>
            </div>
          </li>
        </ul>
        <ul class="lui-flowMonitor-chartList">
          <li>
            <div  class="chart-item" id="nodeApprovalTime" style="display: block; height: 420px; text-align: center; font-size: 36px;"></div>
          </li>
          <li>
            <div class="chart-item" id="rejectNode" style="display: block; height: 420px; text-align: center; font-size: 36px;"></div>
          </li>
          <li>
            <div class="chart-item" id="timeoutNode" style="display: block; height: 420px; text-align: center; font-size: 36px;"></div>
          </li>
          <li>
            <div class="chart-item" id="upcomingNode" style="display: block; height: 420px; text-align: center; font-size: 36px;"></div>
          </li>
        </ul>
      </div>
      
		
      <div class="lui-flowMonitor-tab-pane fade" id="tab-content-3" aria-labelledby="tab-3">
        <ul class="lui-flowMonitor-statistics-board">
          <li>
            <div class="magnet" style="cursor: pointer " onclick="window.location.href=encodeURI('<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_people_upcoming_detail.jsp?s_path=${param.s_path}"/>'); return false" >
              <div class="magnet-icon icon-color-9 icon-9"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.upcomingMaxProcessPeople" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="people_max_approvalTime"></p>
            </div>
          </li>
          <li>
            <div class="magnet" style="cursor: pointer " onclick="window.location.href=encodeURI('<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_people_urgent_detail.jsp?s_path=${param.s_path}"/>'); return false"  >
              <div class="magnet-icon icon-color-10 icon-10"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.peopleUrgentMax" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="people_max_urgent"></p>
            </div>
          </li>
          <li>
            <div class="magnet" style="cursor: pointer " onclick="window.location.href=encodeURI('<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_people_timeOutJump_detail.jsp?s_path=${param.s_path}"/>'); return false">
              <div class="magnet-icon icon-color-11 icon-11"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.peopleTimeoutJumpMaxMuch" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="people_max_timeOutJump"></p>
            </div>
          </li>
          <li>
            <div class="magnet" style="cursor: pointer " onclick="window.location.href=encodeURI('<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_people_timeOutTurnDo_detail.jsp?s_path=${param.s_path}"/>'); return false" >
              <div class="magnet-icon icon-color-12 icon-12"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.peopleTimeoutTodoMaxMuch" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="people_max_timeOutTurnDo"></p>
            </div>
          </li>
        </ul>
        <ul class="lui-flowMonitor-chartList">
          <li>
            <div class="chart-item" id="peopleApprovalTime" style="display: block; height: 420px; text-align: center; font-size: 36px;">
            </div>
          </li>
          <li>
            <div class="chart-item" id="peopleUrgent" style="display: block; height: 420px; text-align: center; font-size: 36px;">
            </div>
          </li>
          <li>
            <div class="chart-item" id="peopleTimeOutJump" style="display: block; height: 420px; text-align: center; font-size: 36px;">
            </div>
          </li>
          <li>
            <div class="chart-item" id="peopleTimeOutTurnDo" style="display: block; height: 420px; text-align: center; font-size: 36px;">
            </div>
          </li>
        </ul>
      </div>
    </div>
    <!-- 选项卡内容 Ends -->
    
		
	</template:replace>
</template:include>