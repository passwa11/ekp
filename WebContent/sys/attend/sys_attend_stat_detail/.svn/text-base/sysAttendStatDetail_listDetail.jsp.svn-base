<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Date,com.landray.kmss.util.DateUtil,com.landray.kmss.sys.attend.util.AttendUtil,com.landray.kmss.sys.attend.util.DateTimeFormatUtil"%>
<%@ page import="java.util.Map,java.util.List,com.landray.kmss.sys.attend.model.SysAttendStat,com.landray.kmss.util.NumberUtil"%>
<%@ page import="net.sf.json.JSONObject,com.landray.kmss.sys.time.util.SysTimeUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<list:data>
	<list:data-columns var="sysAttendStatDetail" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>

		
		<list:data-column col="fdDate" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdDate') }" escape="false" headerStyle="min-width: 130px;">
			<c:set var="_fdDate" value="${sysAttendStatDetail.fdDate}"></c:set>
			<%
				Date _fdSignedTime = (Date)pageContext.getAttribute("_fdDate");
				String week = new DateTimeFormatUtil().getDateTimeNuber(_fdSignedTime);	
				pageContext.setAttribute("__fdDate", DateUtil.convertDateToString(_fdSignedTime, "yyyy-MM-dd"));
			%>
			${__fdDate}&nbsp;&nbsp;<sunbor:enumsShow value="<%=week %>" enumsType="common_week_type" />
		</list:data-column>
		
		<list:data-column col="fdDateType" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdDateType') }" escape="false">
			<sunbor:enumsShow
					value="${sysAttendStatDetail.fdDateType}"
					enumsType="sysAttendMain_fdDateType" />
		</list:data-column>
		
		<list:data-column col="fdCategoryName" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdCategoryName') }" escape="false" headerStyle="min-width: 200px;">
			${sysAttendStatDetail.fdCategoryName}
		</list:data-column>
		
		<list:data-column col="docCreator.fdName" title="${ lfn:message('sys-attend:sysAttendStatDetail.docCreator') }" escape="false" headerStyle="min-width: 65px;">
			<c:if test="${sysAttendStatDetail.docCreator.fdIsAvailable}">
				${sysAttendStatDetail.docCreator.fdName}
			</c:if>
			<c:if test="${!sysAttendStatDetail.docCreator.fdIsAvailable}">
				${sysAttendStatDetail.docCreator.fdName}${ lfn:message('sys-attend:sysAttendStatDetail.alreadyQuit') }
			</c:if>
		</list:data-column>
		
		<list:data-column col="docCreator.fdNo" title="工号" escape="false" headerStyle="min-width: 65px;">
			${sysAttendStatDetail.docCreator.fdNo}
		</list:data-column>
		
		<list:data-column col="fdFirstLevelDepartmentName" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdFirstLevelDepartmentName') }" escape="false" headerStyle="min-width: 200px;">
			${sysAttendStatDetail.fdFirstLevelDepartmentName}
		</list:data-column>
		<list:data-column col="fdSecondLevelDepartmentName" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdSecondLevelDepartmentName') }" escape="false" headerStyle="min-width: 200px;">
			${sysAttendStatDetail.fdSecondLevelDepartmentName}
		</list:data-column>
		<list:data-column col="fdThirdLevelDepartmentName" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdThirdLevelDepartmentName') }" escape="false" headerStyle="min-width: 200px;">
			${sysAttendStatDetail.fdThirdLevelDepartmentName}
		</list:data-column>
		<list:data-column col="fdStartTime" title="${ lfn:message('sys-attend:sysAttendStatDetail.startTime') }" escape="false" >
			${sysAttendStatDetail.startTime}
		</list:data-column>
		<list:data-column col="fdEndTime" title="${ lfn:message('sys-attend:sysAttendStatDetail.endTime') }" escape="false" >
			${sysAttendStatDetail.endTime}
		</list:data-column>
		<list:data-column col="fdRestTime" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdRestTime') }" escape="false" >
			${sysAttendStatDetail.fdRestTime}
		</list:data-column>
		
		<list:data-column col="fdStandWorkTime" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdStandWorkTime') }" escape="false" >
			${String.format("%.2f",sysAttendStatDetail.fdStandWorkTime)}
		</list:data-column>
		<list:data-column col="fdMonthLateNum" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdMonthLateNum') }" escape="false" >
			${sysAttendStatDetail.fdMonthLateNum}
		</list:data-column>
		<list:data-column col="fdMonthForgerNum" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdMonthForgerNum') }" escape="false" >
			${sysAttendStatDetail.fdMonthForgerNum}
		</list:data-column>
		<list:data-column col="fdMonthLateMinNum" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdMonthLateMinNum') }" escape="false" >
			${sysAttendStatDetail.fdMonthLateMinNum}
		</list:data-column>
		<list:data-column  col="fdAttendResult" title="考勤结果">
		<c:if test="${sysAttendStatDetail.fdAttendResult==1 }">
		异常
		</c:if>
		<c:if test="${sysAttendStatDetail.fdAttendResult!=1 }">
		正常
		</c:if>
		</list:data-column>
		<list:data-column col="fdDelayedTime" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdDelayedTime') }" escape="false" >
			${sysAttendStatDetail.fdDelayedTime}
		</list:data-column>
		
		
		<list:data-column col="docCreator.fdDept" title="${ lfn:message('sys-attend:sysAttendStatDetail.docCreator.fdDept') }" escape="false" headerStyle="min-width: 100px;">
			<c:out value="${sysAttendStatDetail.docCreator.fdParent.fdName}" />
		</list:data-column>
		
		<%-- 总工时 --%>
		<list:data-column col="fdTotalTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdTotalTime') }" escape="false">
			<c:set var="_fdTotalTime" value="${sysAttendStatDetail.fdTotalTime}"></c:set>
			<%
				Long _fdTotalTime = (Long)pageContext.getAttribute("_fdTotalTime");
				Double hour = 0.0;
				long minute = 0L;
				if(_fdTotalTime != null && _fdTotalTime.longValue() != 0) {
					hour = _fdTotalTime.doubleValue() / 60.0;
					minute = _fdTotalTime.longValue() % 60;
				}
				pageContext.setAttribute("totalHour", hour);
				pageContext.setAttribute("totalMinute", minute);
			%>
			${String.format("%.2f",totalHour)}
<%-- 			<c:if test="${totalHour == 0 && totalMinute == 0}"> --%>
<%-- 				${totalHour} --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${totalHour > 0 && totalMinute == 0}"> --%>
<%-- 				${totalHour}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.hour') } --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${totalHour == 0 && totalMinute > 0}"> --%>
<%-- 				${totalMinute}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.minute') } --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${totalHour > 0 && totalMinute > 0}"> --%>
<%-- 				${totalHour}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.hour') }${totalMinute}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.minute') } --%>
<%-- 			</c:if> --%>
		</list:data-column>
		
		<%-- 加班工时 --%>
		<list:data-column col="fdOverTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdOverTime') }" escape="false">
			<c:set var="_fdOverTime" value="${sysAttendStatDetail.fdOverTime}"></c:set>
			<%
				Long _fdOverTime = (Long) pageContext.getAttribute("_fdOverTime");
				double hour = 0.0;
				long minute = 0L;
				if(_fdOverTime != null && _fdOverTime.longValue() != 0) {
					hour = _fdOverTime.doubleValue() / 60.0;
					minute = _fdOverTime.longValue() % 60;
				}
				pageContext.setAttribute("overHour", hour);
				pageContext.setAttribute("overMinute", minute);
			%>
			${String.format("%.2f",overHour)}
<%-- 			<c:if test="${overHour==0 && overMinute == 0}"> --%>
<%-- 				${overHour} --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${overHour > 0 && overMinute == 0}"> --%>
<%-- 				${overHour}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.hour') } --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${overHour == 0 && overMinute > 0}"> --%>
<%-- 				${overMinute}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.minute') } --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${overHour > 0 && overMinute > 0}"> --%>
<%-- 				${overHour}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.hour') }${overMinute}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.minute') } --%>
<%-- 			</c:if> --%>
		</list:data-column>
		
		<c:set var="fdSignTimeTitle" value="${ lfn:message('sys-attend:sysAttendMain.fdWorkType.onwork') }" />
		<c:set var="startTime" value="${ lfn:message('sys-attend:sysAttendStatDetail.startTime') }" />
		
		<c:set var="fdSignTime2Title" value="${ lfn:message('sys-attend:sysAttendMain.fdWorkType.offwork') }" />
		<c:set var="isExc" value="false"></c:set>
		<%
			SysAttendStat stat = (SysAttendStat)pageContext.getAttribute("sysAttendStatDetail");
		%>
		<c:forEach var="index" begin="0" end="${workTimeCount-1}">
			<c:set var="fdSignTimeName_on" value="fdSignTime${2*index+1}"></c:set>
			<c:set var="fdStartTime" value="fdStartTime${2*index+1}"></c:set>
			<c:set var="docStatusName_on" value="docStatus${2*index+1}"></c:set>
			<%-- <c:set var="fdStateName_on" value="fdState${2*index+1}"></c:set> --%>
			<c:set var="fdSignTimeName_off" value="fdSignTime${2*index+2}"></c:set>
			<c:set var="docStatusName_off" value="docStatus${2*index+2}"></c:set>
			<%-- <c:set var="fdStateName_off" value="fdState${2*index+2}"></c:set> --%>
			<c:if test="${index ==0 }">
				<c:set var="fdSignTimeName_on" value="fdSignTime"></c:set>
				<c:set var="fdStartTime" value="fdStartTime"></c:set>
				<c:set var="docStatusName_on" value="docStatus"></c:set>
				<%-- <c:set var="fdStateName_on" value="fdState"></c:set> --%>
			</c:if>
			<c:set var="__index" value="${index }"></c:set>
			<%
				Integer index = (Integer)pageContext.getAttribute("__index");
				//当前展现班制数
				Map worksMap = (Map)request.getAttribute("worksMap");
				
				List _workTimeList = (List)worksMap.get(stat.getFdId());
				JSONObject work1 = new JSONObject(),work2=new JSONObject();
				if(_workTimeList!=null && !_workTimeList.isEmpty()){
					//用户班制数
					int userWorkCount = _workTimeList.size();
					if(index > userWorkCount-1){
					}else{
						List workTime = (List)_workTimeList.get(index);
						work1 = (JSONObject)workTime.get(0);
						if(workTime.size()>1){
							work2 =(JSONObject)workTime.get(1); 
						}
						if(work1!=null && !work1.isEmpty()){
							Integer workType = (Integer)work1.get("fdWorkType");
							if(workType==1){
								JSONObject tmp = work1;
								work1 = work2;
								work2 = tmp;
							}
						}
						if(work2!=null && !work2.isEmpty()){
							Integer workType = (Integer)work2.get("fdWorkType");
							if(workType==0){
								JSONObject tmp = work1;
								work1 = work2;
								work2 = tmp;
							}
						}
					}
				}
				pageContext.setAttribute("work1", work1);
				pageContext.setAttribute("work2", work2);
			%>
			<%-- 1 --%>
			<list:data-column col="${fdSignTimeName_on }" title="${fdSignTimeTitle}" escape="false">
				<c:if test="${not empty work1 && (work1.fdStatus!=0 || work1.fdState==2)}">
				<c:set var="_fdSignTime" value="${work1.signTime}"></c:set>
				<%
					Date _fdSignedTime = new Date((Long)pageContext.getAttribute("_fdSignTime"));
					pageContext.setAttribute("__fdSignedTime", DateUtil.convertDateToString(_fdSignedTime, "HH:mm"));
				%>
				${__fdSignedTime}<c:if test="${work1.fdIsAcross == 'true'}"> (${ lfn:message('sys-attend:sysAttendMain.fdIsAcross.nextday') })</c:if>
				</c:if>
			</list:data-column>
			
			
			<list:data-column col="${docStatusName_on }" title="${ lfn:message('sys-attend:sysAttendStatDetail.docStatus') }" escape="false">
				<c:if test="${not empty work1.fdId && sysAttendStatDetail.shiftType==4}">
					<c:choose>
						<c:when test="${not empty work1 && (work1.fdStatus==1 && work1.fdOutside)}">
							<a  target="_blank"  style="color: #2574ad;"  data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work1.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:when>
						<c:when test="${not empty work1 && (work1.fdStatus==1 || work1.fdStatus==0)}">
							<a class="sign_status"  style="color: #343434;" target="_blank" data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work1.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:when>
						<c:when test="${not empty work1 && (work1.fdState==2)}">
							<a class="sign_status"  style="color: #343434;" target="_blank" data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work1.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:when>
						<c:otherwise>
							<a class="sign_status" target="_blank" data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work1.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${(work1.fdStatus==0 || work1.fdStatus==2 || work1.fdStatus==3) && work1.fdState==2}">
							${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok')}
						</c:when>
						<c:when test="${(work2.fdStatus==0 || work2.fdStatus==2 || work2.fdStatus==3 || categoryMap[sysAttendStatDetail.fdCategoryId].fdOsdReviewType==1 && work2.fdStatus==1 && work2.fdOutside) && work2.fdState!=2 && (work1.fdStatus==0 || work1.fdStatus==2 || work1.fdStatus==3 || categoryMap[sysAttendStatDetail.fdCategoryId].fdOsdReviewType==1 && work1.fdStatus==1 && work1.fdOutside) && work1.fdState!=2}">
							<c:set var="isExc" value="true"></c:set>
							<span class="sign_status_exc">
								<c:if test="${work1.fdStatus!=1 }">
									<sunbor:enumsShow value="${work1.fdStatus}" enumsType="sysAttendMain_fdStatus" /><c:if test="${work1.fdOutside }">(${ lfn:message('sys-attend:sysAttendMain.outside')})</c:if>
								</c:if>
							</span>
							<c:if test="${work1.fdStatus==1 }">
									${ lfn:message('sys-attend:sysAttendMain.outside')}
							</c:if>
						</c:when>
						<c:when test="${work1.fdStatus==1 && work1.fdOutside}">
							${ lfn:message('sys-attend:sysAttendMain.outside')}
						</c:when>
						
						<c:when test="${work1.fdStatus==0 && !sysAttendStatDetail.fdMissed}">
							${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok')}
						</c:when>
						<c:otherwise>
						
<%-- 							${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok')} --%>
							<sunbor:enumsShow
 								value="${work1.fdStatus}" 
								enumsType="sysAttendMain_fdStatus" />
						</c:otherwise>
					</c:choose>
					<c:if test="${work1.fdStatus==5 && not empty work1.fdOffTypeText}">
						(${work1.fdOffTypeText})
					</c:if>
					</a>
				</c:if>
				<c:if test="${not empty work1.fdId && sysAttendStatDetail.shiftType!=4}">
					<c:choose>
						<c:when test="${not empty work1 && (work1.fdStatus==1 && work1.fdOutside)}">
							<a  target="_blank"  style="color: #2574ad;"  data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work1.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:when>
						<c:when test="${not empty work1 && (work1.fdStatus==1 || work1.fdStatus==0)}">
							<a class="sign_status"  style="color: #343434;" target="_blank" data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work1.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:when>
						<c:when test="${not empty work1 && (work1.fdState==2)}">
							<a class="sign_status"  style="color: #343434;" target="_blank" data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work1.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:when>
						<c:otherwise>
							<a class="sign_status" target="_blank" data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work1.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${(work1.fdStatus==0 || work1.fdStatus==2 || work1.fdStatus==3) && work1.fdState==2}">
							${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok')}
						</c:when>
						<c:when test="${(work2.fdStatus==0 || work2.fdStatus==2 || work2.fdStatus==3 || categoryMap[sysAttendStatDetail.fdCategoryId].fdOsdReviewType==1 && work2.fdStatus==1 && work2.fdOutside) && work2.fdState!=2 && (work1.fdStatus==0 || work1.fdStatus==2 || work1.fdStatus==3 || categoryMap[sysAttendStatDetail.fdCategoryId].fdOsdReviewType==1 && work1.fdStatus==1 && work1.fdOutside) && work1.fdState!=2}">
							<c:set var="isExc" value="true"></c:set>
							<span class="sign_status_exc">
								<c:if test="${work1.fdStatus!=1 }">
									<sunbor:enumsShow value="${work1.fdStatus}" enumsType="sysAttendMain_fdStatus" /><c:if test="${work1.fdOutside }">(${ lfn:message('sys-attend:sysAttendMain.outside')})</c:if>
								</c:if>
							</span>
							<c:if test="${work1.fdStatus==1 }">
									${ lfn:message('sys-attend:sysAttendMain.outside')}
							</c:if>
						</c:when>
						<c:when test="${work1.fdStatus==1 && work1.fdOutside}">
							${ lfn:message('sys-attend:sysAttendMain.outside')}
						</c:when>
						<c:otherwise>
						
							<sunbor:enumsShow
								value="${work1.fdStatus}"
								enumsType="sysAttendMain_fdStatus" /> 
						</c:otherwise>
					</c:choose>
					<c:if test="${work1.fdStatus==5 && not empty work1.fdOffTypeText}">
						(${work1.fdOffTypeText})
					</c:if>
					</a>
				</c:if>
			</list:data-column>
			<%-- <list:data-column col="${fdStateName_on }" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdState') }">
				<c:if test="${not empty work1.fdId}">
					<c:choose>
						<c:when test="${(work1.fdStatus==0 || work1.fdStatus==2 || work1.fdStatus==3) &&  work1.fdState==null}">
							${ lfn:message('sys-attend:sysAttendMain.fdState.undo')}
						</c:when>
						<c:when test="${work1.fdStatus==1 && !work1.fdOutside || work1.fdStatus==4 || work1.fdStatus==5 || work1.fdStatus==6 }">
						</c:when>
						<c:otherwise>
							<sunbor:enumsShow value="${work1.fdState}" enumsType="sysAttendMain_fdState" />
						</c:otherwise>
					</c:choose>
				</c:if>
			</list:data-column> --%>
			<%-- 2 --%>
			<list:data-column col="${fdSignTimeName_off }" title="${fdSignTime2Title }" escape="false">
				<c:if test="${work2.fdStatus!=0 || work2.fdState==2}">
				<c:set var="_fdSignTime2" value="${work2.signTime}"></c:set>
				<%
					String __fdSignedTime2 = "";
					Long _fdSignedTime2 = (Long)pageContext.getAttribute("_fdSignTime2");
					if(_fdSignedTime2!=null){
						__fdSignedTime2 = DateUtil.convertDateToString(new Date(_fdSignedTime2), "HH:mm");
					}
					pageContext.setAttribute("__fdSignedTime2",__fdSignedTime2);
				%>
				${__fdSignedTime2}<c:if test="${work2.fdIsAcross == 'true'}"> (${ lfn:message('sys-attend:sysAttendMain.fdIsAcross.nextday') })</c:if>
				</c:if>
			</list:data-column>
			<list:data-column col="${docStatusName_off}" title="${ lfn:message('sys-attend:sysAttendStatDetail.docStatus2') }" escape="false">
				<c:if test="${not empty work2.fdId && sysAttendStatDetail.shiftType==4}">
					<c:choose>
						<c:when test="${not empty work2 && (work2.fdStatus==1 && work2.fdOutside)}">
							<a  target="_blank"  style="color: #2574ad;"  data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work2.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:when>
						<c:when test="${not empty work2 && (work2.fdStatus==1 ||work2.fdStatus==0)}">
							<a class="sign_status" style="color: #343434;" target="_blank" data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work2.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:when>
						<c:when test="${(work2.fdStatus==0 || work2.fdStatus==2 || work2.fdStatus==3) && work2.fdState==2}">
							<a class="sign_status" style="color: #343434;" target="_blank" data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work2.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:when>
						<c:otherwise>
							<a class="sign_status" target="_blank" data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work2.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:otherwise>
					</c:choose>
						<c:choose>
							<c:when test="${(work2.fdStatus==0 || work2.fdStatus==2 || work2.fdStatus==3) && work2.fdState==2}">
								${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok')}
							</c:when>
							<c:when test="${(work2.fdStatus==0 || work2.fdStatus==2 || work2.fdStatus==3 || categoryMap[sysAttendStatDetail.fdCategoryId].fdOsdReviewType==1 && work2.fdStatus==1 && work2.fdOutside) && work2.fdState!=2 && (work1.fdStatus==0 || work1.fdStatus==2 || work1.fdStatus==3 || categoryMap[sysAttendStatDetail.fdCategoryId].fdOsdReviewType==1 && work1.fdStatus==1 && work1.fdOutside) && work1.fdState!=2}">
								<c:set var="isExc" value="true"></c:set>
								<span class="sign_status_exc">
									<c:if test="${work2.fdStatus!=1 }">
										<sunbor:enumsShow value="${work2.fdStatus}" enumsType="sysAttendMain_fdStatus" /><c:if test="${work2.fdOutside }">(${ lfn:message('sys-attend:sysAttendMain.outside')})</c:if>
									</c:if>
								</span>
								<c:if test="${work2.fdStatus==1 }">
										${ lfn:message('sys-attend:sysAttendMain.outside')}
								</c:if>
							</c:when>
							<c:when test="${work2.fdStatus==1 && work2.fdOutside}">
								${ lfn:message('sys-attend:sysAttendMain.outside')}
							</c:when>
						<c:when test="${work2.fdStatus==0&&!sysAttendStatDetail.fdMissed}">
							${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok')}
						</c:when>
							<c:otherwise>
<%-- 							${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok')} --%>
								<sunbor:enumsShow
								value="${work2.fdStatus}"
									enumsType="sysAttendMain_fdStatus" />
							</c:otherwise>
						</c:choose>
						<c:if test="${work2.fdStatus==5 && not empty work2.fdOffTypeText}">
							(${work2.fdOffTypeText})
						</c:if>
						</a>
				</c:if>
				<c:if test="${not empty work2.fdId && sysAttendStatDetail.shiftType!=4}">
					<c:choose>
						<c:when test="${not empty work2 && (work2.fdStatus==1 && work2.fdOutside)}">
							<a  target="_blank"  style="color: #2574ad;"  data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work2.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:when>
						<c:when test="${not empty work2 && (work2.fdStatus==1 ||work2.fdStatus==0)}">
							<a class="sign_status" style="color: #343434;" target="_blank" data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work2.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:when>
						<c:when test="${(work2.fdStatus==0 || work2.fdStatus==2 || work2.fdStatus==3) && work2.fdState==2}">
							<a class="sign_status" style="color: #343434;" target="_blank" data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work2.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:when>
						<c:otherwise>
							<a class="sign_status" target="_blank" data-href="${LUI_ContextPath }${'/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='}${work2.fdId}" onclick="Com_OpenNewWindow(this)">
						</c:otherwise>
					</c:choose>
						<c:choose>
							<c:when test="${(work2.fdStatus==0 || work2.fdStatus==2 || work2.fdStatus==3) && work2.fdState==2}">
								${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok')}
							</c:when>
							<c:when test="${(work2.fdStatus==0 || work2.fdStatus==2 || work2.fdStatus==3 || categoryMap[sysAttendStatDetail.fdCategoryId].fdOsdReviewType==1 && work2.fdStatus==1 && work2.fdOutside) && work2.fdState!=2}">
								<c:set var="isExc" value="true"></c:set>
								<span class="sign_status_exc">
									<c:if test="${work2.fdStatus!=1 }">
										<sunbor:enumsShow value="${work2.fdStatus}" enumsType="sysAttendMain_fdStatus" /><c:if test="${work2.fdOutside }">(${ lfn:message('sys-attend:sysAttendMain.outside')})</c:if>
									</c:if>
								</span>
								<c:if test="${work2.fdStatus==1 }">
										${ lfn:message('sys-attend:sysAttendMain.outside')}
								</c:if>
							</c:when>
							<c:when test="${work2.fdStatus==1 && work2.fdOutside}">
								${ lfn:message('sys-attend:sysAttendMain.outside')}
							</c:when>
							<c:otherwise>
<%-- 							${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok')} --%>
								<sunbor:enumsShow
 									value="${work2.fdStatus}" 
 									enumsType="sysAttendMain_fdStatus" /> 
							</c:otherwise>
						</c:choose>
						<c:if test="${work2.fdStatus==5 && not empty work2.fdOffTypeText}">
							(${work2.fdOffTypeText})
						</c:if>
						</a>
				</c:if>
			</list:data-column>
			<%-- <list:data-column col="${fdStateName_off }" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdState2') }">
				<c:if test="${not empty work2.fdId}">
					<c:choose>
						<c:when test="${(work2.fdStatus==0 || work2.fdStatus==2 || work2.fdStatus==3) &&  work2.fdState==null}">
							${ lfn:message('sys-attend:sysAttendMain.fdState.undo')}
						</c:when>
						<c:when test="${work2.fdStatus==1 && !work2.fdOutside || work2.fdStatus==4 || work2.fdStatus==5 || work2.fdStatus==6 }">
						</c:when>
						<c:otherwise>
							<sunbor:enumsShow value="${work2.fdState}" enumsType="sysAttendMain_fdState" />
						</c:otherwise>
					</c:choose>
				</c:if>
			</list:data-column> --%>
		</c:forEach>
		
		<list:data-column col="fdLateTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdLateTime') }">
			<%
				Integer lateTime = stat.getFdLateTime();
				Double hour = 0.0; int  minute = 0;
				if(lateTime != null && lateTime != 0) {
					hour = lateTime.doubleValue() / 60.0;
					minute = lateTime % 60;
				}
				pageContext.setAttribute("fdLateTimeHour", hour);
				pageContext.setAttribute("fdLateTimeMinute", minute);
			%>
			${String.format("%.2f",fdLateTimeHour)}
<%-- 			<c:if test="${fdLateTimeHour==0 && fdLateTimeMinute == 0}"> --%>
<%-- 				${fdLateTimeHour} --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${fdLateTimeHour>0 && fdLateTimeMinute == 0}"> --%>
<%-- 				${fdLateTimeHour}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.hour') } --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${fdLateTimeHour == 0 && fdLateTimeMinute > 0}"> --%>
<%-- 				${fdLateTimeMinute}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.minute') } --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${fdLateTimeHour > 0 && fdLateTimeMinute > 0}"> --%>
<%-- 				${fdLateTimeHour}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.hour') }${fdLateTimeMinute}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.minute') } --%>
<%-- 			</c:if> --%>
		</list:data-column>
		<list:data-column col="fdLeftTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdLeftTime') }">
			<%
				Integer leftTime = stat.getFdLeftTime();
				double hour = 0.0;int minute = 0;
				if(leftTime != null && leftTime != 0) {
					hour = leftTime.doubleValue() / 60.0;
					minute = leftTime % 60;
				}
				pageContext.setAttribute("fdLeftTimeHour", hour);
				pageContext.setAttribute("fdLeftTimeMinute", minute);
			%>
			${String.format("%.2f",fdLeftTimeHour)}
<%-- 			<c:if test="${fdLeftTimeHour==0 && fdLeftTimeMinute == 0}"> --%>
<%-- 				${fdLeftTimeHour} --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${fdLeftTimeHour>0 && fdLeftTimeMinute == 0}"> --%>
<%-- 				${fdLeftTimeHour}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.hour') } --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${fdLeftTimeHour == 0 && fdLeftTimeMinute > 0}"> --%>
<%-- 				${fdLeftTimeMinute}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.minute') } --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${fdLeftTimeHour > 0 && fdLeftTimeMinute > 0}"> --%>
<%-- 				${fdLeftTimeHour}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.hour') }${fdLeftTimeMinute}${ lfn:message('sys-attend:sysAttendStatDetail.totalTime.minute') } --%>
<%-- 			</c:if> --%>
		</list:data-column>
		<list:data-column col="fdAbsentDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdAbsentDays') }">
			<%
				Float absentDays = stat.getFdAbsentDays()==null ? 0:stat.getFdAbsentDays();
				pageContext.setAttribute("__fdAbsentDays",NumberUtil.roundDecimal(absentDays, 1));
			%>
			${__fdAbsentDays}
		</list:data-column>
		<%-- 事假 --%>
		<list:data-column col="fdPersonalLeaveDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdPersonalLeaveDays') }">
			<%
				Float personalLeaveDays = stat.getFdPersonalLeaveDays()==null ? 0:stat.getFdPersonalLeaveDays();
				pageContext.setAttribute("__fdPersonalLeaveDays",NumberUtil.roundDecimal(personalLeaveDays, 1));
			%>
			${__fdPersonalLeaveDays}
		</list:data-column>
		<list:data-column col="fdOffDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdOffDays') }">
<%--			请假工时--%>
			<%
// 				Float _fdWorkTime = stat.getFdWorkTime();
// 				Float __fdOffDays = stat.getFdOffDays()==null ? 0F:stat.getFdOffDays();
				Float __fdOffTime = stat.getFdOffTimeHour()==null ? 0F : stat.getFdOffTimeHour();
// 				if(_fdWorkTime !=null && _fdWorkTime > 0){
// 					__fdOffDays = 0F;
// 				}
// 				String _offText = SysTimeUtil.formatLeaveTimeStr(_fdWorkTime,__fdOffDays, __fdOffTime);
// 				__fdOffDays = __fdOffDays == null ? 0 : __fdOffDays;
// 				__fdOffTime = __fdOffTime == null ? 0f : __fdOffTime;
// 				if(_fdWorkTime==null){
// 					_fdWorkTime = SysTimeUtil.getConvertTime();
// 				}
// 				int _leaveDay = __fdOffDays.intValue();
// 				Float tmpLeaveDayHour = (__fdOffDays - _leaveDay) * _fdWorkTime;
// 				__fdOffTime = __fdOffTime + tmpLeaveDayHour;
// 				if(_fdWorkTime>0){
// 				Float leaveHourDay = __fdOffTime / _fdWorkTime;
// 				pageContext.setAttribute("_offText", __fdOffDays+leaveHourDay);
// 				}
// 				else
					pageContext.setAttribute("_offText", __fdOffTime);
			%>
<%-- 			${_offText} --%>
			${String.format("%.2f",_offText)}
		</list:data-column>
		<list:data-column col="fdTripDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdTripDays') }">
			<%
				Float tripDays = stat.getFdTripDays()==null ? 0:stat.getFdTripDays();
				pageContext.setAttribute("__fdTripDays",NumberUtil.roundDecimal(tripDays, 1));
			%>
			${__fdTripDays}
		</list:data-column>
		<%-- 外出工时 --%>
		<c:set var="_fdOutgoingTime" value="${sysAttendStatDetail.fdOutgoingTime }"></c:set>
		<%
			Float _fdOutgoingTime = stat.getFdOutgoingTime()==null ? 0F : stat.getFdOutgoingTime();
			pageContext.setAttribute("__fdOutgoingTime", _fdOutgoingTime);
// 			pageContext.setAttribute("__fdOutgoingTime", SysTimeUtil.formatHourTimeStr(_fdOutgoingTime));
		%>
		<list:data-column col="fdOutgoingTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdOutgoingTime') }" escape="false">
			${String.format("%.2f",__fdOutgoingTime) }
		</list:data-column>
		<list:data-column col="operation" title="${ lfn:message('sys-attend:sysAttendMain.operation') }" escape="false" headerStyle="min-width: 160px;">
			<a class="btn_txt" href="javascript:openMainList('${sysAttendStatDetail.docCreator.fdId}','${__fdDate}')">${ lfn:message('sys-attend:sysAttendMain.viewRecord') }</a>
			<c:if test="${(isAdmin || fn:contains(authCateIds, sysAttendStatDetail.fdCategoryId)) && isExc}">
				<a class="btn_txt" href="javascript:updateStatus('${sysAttendStatDetail.fdId}','1')">${ lfn:message('sys-attend:sysAttendStatDetail.updateToNormal') }</a>
			</c:if>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>