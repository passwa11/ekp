<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.DateUtil,java.util.Date,com.landray.kmss.sys.attend.util.AttendUtil" %>
<%@ page import="com.landray.kmss.util.DbUtils" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="model" list="${queryPage.list}" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
		<list:data-column col="categoryId">
			${model.categoryId}
		</list:data-column >
		<c:set var="__fdOverTimeType" value="${model.overTimeType}"></c:set>
		<c:set var="__isAcrossDay" value="${model.isAcrossDay}"></c:set>
		<c:set var="__nextOverTimeType" value="${model.nextOverTimeType}"></c:set>
	    <%-- 主题--%>	
		<list:data-column col="fdSigned" escape="false" title="">
			${model.fdSigned}
		</list:data-column>
      	<list:data-column col="fdSignedTime" escape="false" title="">
			<c:set var="_fdSignedTime" value="${model.fdSignedTime}"></c:set>
			<%
				Date _fdSignedTime = (Date)pageContext.getAttribute("_fdSignedTime");
				pageContext.setAttribute("__fdSignedTime", DateUtil.convertDateToString(_fdSignedTime, "HH:mm"));
			%>
			${__fdSignedTime}
		</list:data-column>
		<list:data-column col="fdSignedStatus" escape="false" title="">
			${model.fdSignedStatus}
		</list:data-column>
		<list:data-column col="fdState" escape="false" title="">
			${model.fdState}
		</list:data-column>
		<list:data-column col="fdSignedStatusTxt" escape="false" title="">
			<sunbor:enumsShow value="${model.fdSignedStatus}" enumsType="sysAttendMain_fdStatus" />
		</list:data-column>
		<list:data-column col="fdSignedLocation" escape="false" title="">
			${model.fdSignedLocation}
		</list:data-column>
		<list:data-column col="fdSignedWifi" escape="false" title="">
			${model.fdSignedWifi}
		</list:data-column>
		<list:data-column col="fdSignedOutside" escape="false" title="">
			${model.fdSignedOutside}
		</list:data-column>
		<list:data-column col="fdSignedAcross" escape="false" title="">
			${model.fdSignedAcross}
		</list:data-column>
		<list:data-column col="fdStartTime" escape="false" title="">
			${model.fdStartTime}
		</list:data-column>
		<list:data-column col="_fdStartTime" escape="false" title="">
			<c:set var="__fdStartTime" value="${model.fdStartTime}"></c:set>
			<%
				Date _fdStartTime = (Date)pageContext.getAttribute("__fdStartTime");
				if(_fdStartTime != null){
					long mins = _fdStartTime.getHours() * 60 +_fdStartTime.getMinutes();
					pageContext.setAttribute("_fdStartTime_min", mins);
				}
				
			%>
			${_fdStartTime_min}
		</list:data-column>
		<list:data-column col="fdEndTime" escape="false" title="">
			${model.fdEndTime}
		</list:data-column>
		<list:data-column col="_fdEndTime" escape="false" title="">
			<c:set var="__fdEndTime" value="${model.fdEndTime}"></c:set>
			<%
				Date _fdEndTime = (Date)pageContext.getAttribute("__fdEndTime");
				if(_fdEndTime !=null){
					long mins = _fdEndTime.getHours() * 60 +_fdEndTime.getMinutes();
					Boolean _isAcrossDay = (Boolean)pageContext.getAttribute("__isAcrossDay");
					Integer _fdOverTimeType = (Integer)pageContext.getAttribute("__fdOverTimeType");
					Integer _nextOverTimeType = (Integer)pageContext.getAttribute("__nextOverTimeType");
					if(Boolean.TRUE.equals(_isAcrossDay)&&(Integer.valueOf(2).equals(_fdOverTimeType) || Integer.valueOf(2).equals(_nextOverTimeType))){
						mins+=1440;
					}
					pageContext.setAttribute("_fdEndTime_min", mins);
				}
				
			%>
			${_fdEndTime_min}
		</list:data-column>
		<list:data-column col="fdType" escape="false" title="">
			${model.fdType}
		</list:data-column>
		<list:data-column col="fdOutside" escape="false" title="">
			${model.fdOutside}
		</list:data-column>
		<list:data-column col="fdCanMobile" escape="false" title="">
            ${model.fdCanMobile}
        </list:data-column>
		<list:data-column col="fdLateTime" escape="false" title="">
			${model.fdLateTime}
		</list:data-column>
		<list:data-column col="fdLeftTime" escape="false" title="">
			${model.fdLeftTime}
		</list:data-column>
		<list:data-column col="fdLimit" escape="false" title="">
			${model.fdLimit}
		</list:data-column>
		<list:data-column col="fdWorkTimeId" escape="false" title="">
			${model.fdWorkTimeId}
		</list:data-column>
		<list:data-column col="signTime" escape="false" title="">
			<c:set var="_signTime" value="${model.signTime}"></c:set>
			<%
				Date _signTime = (Date)pageContext.getAttribute("_signTime");
				if(_signTime !=null){
					pageContext.setAttribute("__signTime", DateUtil.convertDateToString(_signTime, "HH:mm"));
				}
				
			%>
			${__signTime}
		</list:data-column>
		<list:data-column col="_signTime" escape="false" title="">
			<c:set var="__signTime" value="${model.signTime}"></c:set>
			<%
				Date _signTime = (Date)pageContext.getAttribute("__signTime");
				if(_signTime!=null){
					long mins = _signTime.getHours() * 60 +_signTime.getMinutes();
					Integer _fdOverTimeType = (Integer)pageContext.getAttribute("__fdOverTimeType");
					if(Integer.valueOf(2).equals(_fdOverTimeType)){
						mins+=1440;
					}
					pageContext.setAttribute("_signTime_min", mins);
				}
				
			%>
			${_signTime_min}
		</list:data-column>
		<list:data-column col="lastSignedTime" escape="false" title="">
			<c:set var="_lastSignedTime" value="${model.lastSignedTime}"></c:set>
			<%
				Date _lastSignedTime = (Date) pageContext.getAttribute("_lastSignedTime");
				if(_lastSignedTime != null){
					long mins = _lastSignedTime.getHours() * 60 + _lastSignedTime.getMinutes();
					pageContext.setAttribute("__lastSignedTime", mins);
				}
			%>
			${__lastSignedTime}
		</list:data-column>

		<list:data-column col="goWorkTimeMins" escape="false" title="">
			${model.goWorkTimeMins}
		</list:data-column>

		<list:data-column col="lastSignedStatus" escape="false" title="">
			${model.lastSignedStatus}
		</list:data-column>
		<list:data-column col="lastSignedState" escape="false" title="">
			${model.lastSignedState}
		</list:data-column>
		
		<list:data-column col="nextSignTime" escape="false" title="">
			<c:if test="${not empty model.nextSignTime}">
				<c:set var="__nextSignTime" value="${model.nextSignTime}"></c:set>
				<%
					Date __nextSignTime = (Date)pageContext.getAttribute("__nextSignTime");
					if(__nextSignTime!=null){
						long mins = __nextSignTime.getHours() * 60 +__nextSignTime.getMinutes();
						Integer _nextOverTimeType = (Integer)pageContext.getAttribute("__nextOverTimeType");
						if(Integer.valueOf(2).equals(_nextOverTimeType)){
							mins+=1440;
						}
						pageContext.setAttribute("__nextSignTime_min", mins);
					}
					
				%>
				${__nextSignTime_min}
			</c:if>
			
		</list:data-column>
		<list:data-column col="fdWorkType" escape="false" title="">
			${model.fdWorkType}
		</list:data-column>
		<list:data-column col="fdLocations" escape="false" title="">
			<c:set scope="page" var="_fdLocations">
	    		[<ui:trim>
		    		<c:forEach items="${model.fdLocations}" var="item">
		    			{	
			    			coord : "${item.fdLatLng}",
			    			address:"${item.fdLocation}",
			    			distance:${empty item.fdLimit ? "null" : item.fdLimit}
			    		},
		    		</c:forEach>
	    		 </ui:trim>]
	    	</c:set>
	    	${_fdLocations}
		</list:data-column>
		<list:data-column col="fdWifiConfigs" escape="false" title="">
			<c:set scope="page" var="_fdWifiConfigs">
	    		[<ui:trim>
		    		<c:forEach items="${model.fdWifiConfigs}" var="item">
		    			{	
			    			"fdName" : "${item.fdName}",
			    			"fdMacIp":"${item.fdMacIp}"
			    		},
		    		</c:forEach>
	    		 </ui:trim>]
	    	</c:set>
	    	${_fdWifiConfigs}
		</list:data-column>
		<list:data-column col="fdBusUrl" escape="false" title="">
			${model.fdBusUrl}
		</list:data-column>
		<list:data-column col="nowTime" escape="false" title="">
			<%
				request.setAttribute("nowTime", DbUtils.getDbTimeMillis());
			%>
			${nowTime}
		</list:data-column>
		<list:data-column col="fdBusSetting" escape="false" title="">
			<c:set scope="page" var="_fdBusSetting">
	    		[<ui:trim>
		    		<c:forEach items="${model.fdBusSetting}" var="item">
		    			{	
			    			"fdBusName"  : "${item.fdBusName}",
			    			"fdBusType"  : "${item.fdBusType}",
			    			"fdReviewUrl": "/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId=${item.fdTemplateId}" 
			    		},
		    		</c:forEach>
	    		 </ui:trim>]
	    	</c:set>
	    	${_fdBusSetting}
		</list:data-column>
		<list:data-column col="records" escape="false" title="">
			<c:set scope="page" var="_records">
	    		[<ui:trim>
		    		<c:forEach items="${model.records}" var="item">
		    			{	
			    			"fdSignedTime"  : "${item.fdSignedTime}",
			    			"fdSignedStatus"  : "${item.fdSignedStatus}",
			    			"fdSignedLocation": "${item.fdSignedLocation}",
			    			"fdSignedOutside": "${item.fdSignedOutside}"
			    		},
		    		</c:forEach>
	    		 </ui:trim>]
	    	</c:set>
	    	${_records}
		</list:data-column>
		<list:data-column col="isAcrossDay" escape="false" title="">
			${model.isAcrossDay}
		</list:data-column>
		<list:data-column col="fdMainExcId" escape="false" title="">
			${model.fdMainExcId}
		</list:data-column>
		<list:data-column col="fdIsFlex" escape="false" title="">
			${model.fdIsFlex}
		</list:data-column>
		<list:data-column col="fdFlexTime" escape="false" title="">
			${model.fdFlexTime}
		</list:data-column>
		<list:data-column col="workTimeMins" escape="false" title="">
			${model.workTimeMins}
		</list:data-column>
		<list:data-column col="fdOsdReviewType" escape="false" title="">
			${model.fdOsdReviewType}
		</list:data-column>
		<list:data-column col="fdStartTime2" escape="false" title="">
			${model.fdStartTime2}
		</list:data-column>
		<list:data-column col="_fdStartTime2" escape="false" title="">
			<c:set var="__fdStartTime2" value="${model.fdStartTime2}"></c:set>
			<%
				Date __fdStartTime2 = (Date) pageContext.getAttribute("__fdStartTime2");
				if(__fdStartTime2 != null){
					long mins = __fdStartTime2.getHours() * 60 +__fdStartTime2.getMinutes();
					pageContext.setAttribute("__fdStartTime2_min", mins);
				}
				
			%>
			${__fdStartTime2_min}
		</list:data-column>
		<list:data-column col="fdEndTime1" escape="false" title="">
			${model.fdEndTime1}
		</list:data-column>
		<list:data-column col="_fdEndTime1" escape="false" title="">
			<c:set var="__fdEndTime1" value="${model.fdEndTime1}"></c:set>
			<%
				Date __fdEndTime1 = (Date) pageContext.getAttribute("__fdEndTime1");
				if(__fdEndTime1 != null){
					long mins = __fdEndTime1.getHours() * 60 + __fdEndTime1.getMinutes();
					Integer _fdOverTimeType = (Integer)pageContext.getAttribute("__fdOverTimeType");
					if(Integer.valueOf(2).equals(_fdOverTimeType)){
						mins+=1440;
					}
					pageContext.setAttribute("__fdEndTime1_min", mins);
				}
				
			%>
			${__fdEndTime1_min}
		</list:data-column>
		<list:data-column col="fdRestStartTime" escape="false" title="">
			<c:set var="__fdRestStartTime" value="${model.fdRestStartTime}"></c:set>
			<%
				Date __fdRestStartTime = (Date) pageContext.getAttribute("__fdRestStartTime");
				if(__fdRestStartTime != null){
					long mins = __fdRestStartTime.getHours() * 60 + __fdRestStartTime.getMinutes();
					pageContext.setAttribute("__fdRestStartTime_min", mins);
				}
				
			%>
			${__fdRestStartTime_min}
		</list:data-column>
		<list:data-column col="fdRestEndTime" escape="false" title="">
			<c:set var="__fdRestEndTime" value="${model.fdRestEndTime}"></c:set>
			<%
				Date __fdRestEndTime = (Date) pageContext.getAttribute("__fdRestEndTime");
				if(__fdRestEndTime != null){
					long mins = __fdRestEndTime.getHours() * 60 + __fdRestEndTime.getMinutes();
					pageContext.setAttribute("__fdRestEndTime_min", mins);
				}
				
			%>
			${__fdRestEndTime_min}
		</list:data-column>
		<list:data-column col="fdWorkNum" escape="false" title="">
			${model.fdWorkNum}
		</list:data-column>
		<list:data-column col="fdWorkTimeSize" escape="false" title="">
			${model.fdWorkTimeSize}
		</list:data-column>
		<list:data-column col="isLastSigned" escape="false" title="">
			${model.isLastSigned}
		</list:data-column>
		<list:data-column col="attendCfgJson" escape="false" title="">
			${model.attendCfgJson}
		</list:data-column>
		<list:data-column col="fdShiftType" escape="false" title="">
			${model.fdShiftType}
		</list:data-column>
		<list:data-column col="fdSecurityMode" escape="false" title="">
			${model.fdSecurityMode}
		</list:data-column>
		<list:data-column col="fdWorkDate" escape="false" title="">
			${model.fdWorkDate}
		</list:data-column>
		<list:data-column col="pSignTime" escape="false" title="">
			<c:if test="${not empty model.pSignTime}">
				<c:set var="__pSignTime" value="${model.pSignTime}"></c:set>
				<%
					Date __pSignTime = (Date)pageContext.getAttribute("__pSignTime");
					if(__pSignTime!=null){
						pageContext.setAttribute("__pSignTime_min", AttendUtil.getHMinutes(__pSignTime));
					}
				%>
				${__pSignTime_min}
			</c:if>
		</list:data-column>
		<list:data-column col="fdOverTimeType" escape="false" title="">
			${model.overTimeType}
		</list:data-column>
		<list:data-column col="nextOverTimeType" escape="false" title="">
			${model.nextOverTimeType}
		</list:data-column>
		</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>