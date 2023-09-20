<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="muiAttendStatView" data-dojo-type="sys/attend/mobile/resource/js/stat/AttendStatView" data-dojo-props="isStatAllReader:'${isStatAllReader}'">
	<div class="muiSubHead muiStatType">
		<div class="muiItem muiFontSizeMS">
			 <input type="hidden" id="statType" value="1">
			 <span class="muiDate active">${ lfn:message('sys-attend:mui.stat.day') }</span>
			 <span class="muiMonth">${ lfn:message('sys-attend:mui.stat.month') }</span>
		</div>
	</div>
	<div class="muiStatCriterion">
		<div class="muiStatDate">
			<div id="_statDate" data-dojo-type="mui/form/DateTime"
				 data-dojo-mixins="mui/datetime/_DateMixin,sys/attend/mobile/resource/js/stat/MonthMixin"
				 data-dojo-props="optClass:'mui-down-n',valueField:'statDate',value:'',edit:false"></div>
		</div>
		<div class="muiItem">
			<span class="muiLeftArea">${ lfn:message('sys-attend:mui.default') }</span>
			<div class="muiRightArea">
				<span class="right">
					<span>${ lfn:message('sys-attend:mui.select.dept') }</span>
					<i class='fontmuis muis-to-right'></i>
				</span>
				<div class="muiAddressForm"></div>
			</div>
		</div>
	</div>

	<div class="muiStatView">
		<div class="muiEkpSubStatisticsPopulation">
            <p class="head">0/0</p>
            <span class="txt">${ lfn:message('sys-attend:mui.sign.and.should') }</span>
        </div>
		<section class="mui-infoboard-progress" style="display: none;">
			<div class="mui-progress-circle mui-progress-circle-lg">
		        <div class="mui-progress-circle-L">
		        	<div class="progress-o-L"></div>
		        </div>
		        <div class="mui-progress-circle-R">
		        	<div class="progress-o-R"></div>
		        </div>
		        <div class="mui-progress-circle-mask">
		          <div class="head">0/0</div>
		          <div class="txt">${ lfn:message('sys-attend:mui.sign.and.should') }</div>
		        </div>
		    </div>
		</section>
		<ul class="muiStatView-infoboard-list">
			<li class="normal muiStatus">
	       	 	<span class="num">0</span>
	        	<span class="txt">${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }</span>
	       </li>
	       <li class="warning muiLate">
	        	<span class="num">0</span>
	       	 <span class="txt">${ lfn:message('sys-attend:sysAttendMain.fdStatus.late') }</span>
	       </li>
	      <li class="muiLeft">
	        <span class="num">0</span>
	        <span class="txt">${ lfn:message('sys-attend:sysAttendMain.fdStatus.left') }</span>
	      </li>
	      <li class="warning muiMissed">
	        <span class="num">0</span>
	        <span class="txt">${ lfn:message('sys-attend:sysAttendMain.fdStatus.unSign') }</span>
	      </li>
	      <li class="warning muiAbsent">
	        <span class="num">0</span>
	        <span class="txt">${ lfn:message('sys-attend:sysAttendMain.fdStatus.missed') }</span>
	      </li>
	      <li class="muiOutside">
	        <span class="num">0</span>
	        <span class="txt">${ lfn:message('sys-attend:sysAttendMain.outside') }</span>
	      </li>
	      <li class="muiOff">
	        <span class="num">0</span>
	        <span class="txt">${ lfn:message('sys-attend:sysAttendMain.fdStatus.askforleave') }</span>
	      </li>
	      <li class="muiTrip">
	        <span class="num">0</span>
	        <span class="txt">${ lfn:message('sys-attend:sysAttendMain.fdStatus.business') }</span>
	      </li>
	      <li class="muiOvertime">
	        <span class="num">0</span>
	        <span class="txt">${ lfn:message('sys-attend:sysAttendMain.fdStatus.overtime') }</span>
	      </li>
	      <li class="muiOutgoing">
	        <span class="num">0</span>
	        <span class="txt">${ lfn:message('sys-attend:sysAttendMain.fdStatus.outgoing') }</span>
	      </li>
		</ul>
	</div>
	<div class="muiAttendExcListContainer">
		<span>${ lfn:message('sys-attend:mui.handle.exc') }</span>
		<div class="muiAttendExcListMore">
            <span class="excNum">0</span>
            <i class='fontmuis muis-to-right'></i>
        </div>
	</div>
	<div class="muiAttendWorkChartContainer">
		<span>${ lfn:message('sys-attend:mui.view.rank') }</span>
		<div class="muiAttendExcListMore">
			<i class='fontmuis muis-to-right'></i>
		</div>
	</div>
</div>