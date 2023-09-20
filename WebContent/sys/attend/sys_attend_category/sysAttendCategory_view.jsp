<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attend.forms.SysAttendCategoryForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.attend.forms.SysAttendCategoryTimeForm,com.landray.kmss.util.DateUtil,java.util.Date" %>
<%@ page import="java.util.List,com.sunbor.web.tag.enums.ValueLabel" %>
<%@ page import="com.landray.kmss.sys.attend.forms.SysAttendCategoryExctimeForm,com.landray.kmss.util.EnumerationTypeUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" showQrcode="false">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/resource/css/attend.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<kmss:auth requestURL="/sys/attend/sys_attend_category/sysAttendCategory.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<c:if test="${sysAttendCategoryForm.fdStatus != '2' }">
					<ui:button text="${ lfn:message('sys-attend:sysAttendCategory.edit') }"
								onclick="Com_OpenWindow('sysAttendCategory.do?method=edit&fdId=${param.fdId}','_self');" order="2">
					</ui:button>
				</c:if>
				<c:if test="${sysAttendCategoryForm.fdStatus == '2' }">
					<ui:button text="${ lfn:message('sys-attend:sysAttendCategory.close.edit') }"
							onclick="Com_OpenWindow('sysAttendCategory.do?method=edit&fdId=${param.fdId}','_self');" order="2">
					</ui:button>
				</c:if>
			</kmss:auth>
			<kmss:auth requestURL="/sys/attend/sys_attend_category/sysAttendCategory.do?method=updateStatus&fdId=${param.fdId}" requestMethod="GET">
				<c:if test="${sysAttendCategoryForm.fdStatus != '2' }">
					<ui:button text="${ lfn:message('sys-attend:sysAttendCategory.close') }" order="4"
							onclick="closeDoc('sysAttendCategory.do?method=updateStatus&fdId=${param.fdId}');">
					</ui:button>
				</c:if>
			</kmss:auth>
			<kmss:auth requestURL="/sys/attend/sys_attend_category/sysAttendCategory.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<c:if test="${sysAttendCategoryForm.fdStatus != '1' }">
					<ui:button text="${lfn:message('button.delete')}" order="4"
							onclick="deleteDoc('sysAttendCategory.do?method=delete&fdId=${param.fdId}');">
					</ui:button>
				</c:if>
			</kmss:auth>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-attend:module.sys.attend') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="content">
		<div class="lui-singin-detailPage">
			<div class="lui-singin-detailPage-heading">
				<c:set var="_status" value="underway"></c:set>
				<c:set var="_statusText" value="${ lfn:message('sys-attend:sysAttendCategory.fdStatus.doing') }"></c:set>
				<c:if test="${sysAttendCategoryForm.fdStatus=='0' }">
					<c:set var="_status" value="waiting"></c:set>
					<c:set var="_statusText" value="${ lfn:message('sys-attend:sysAttendCategory.fdStatus.unStart') }"></c:set>
				</c:if>
				<c:if test="${sysAttendCategoryForm.fdStatus=='2' }">
					<c:set var="_status" value="finished"></c:set>
					<c:set var="_statusText" value="${ lfn:message('sys-attend:sysAttendCategory.fdStatus.finish') }"></c:set>
				</c:if>

		      <h1 class="lui-singin-detailPage-title ${_status }"><span class="status">[${_statusText }]</span><bean:write name="sysAttendCategoryForm" property="fdName" /></h1>
		      <ul class="lui-singin-detailPage-tips">
		        <li>
		          <span>${ lfn:message('sys-attend:sysAttendCategory.docCreatorName') }：</span>
		          <span><c:out value="${sysAttendCategoryForm.docCreatorName}" /></span>
		        </li>
		        <li>
		          <span>${ lfn:message('sys-attend:sysAttendCategory.type') }：</span>
		          <span>
		          	<c:if test="${sysAttendCategoryForm.fdType=='1' }">
		          		${ lfn:message('sys-attend:sysAttendCategory.attend') }
		          	</c:if>
		          	<c:if test="${sysAttendCategoryForm.fdType=='2' }">
		          		${ lfn:message('sys-attend:sysAttendCategory.custom') }
		          	</c:if>
		          </span>
		        </li>
		        <c:if test="${sysAttendCategoryForm.fdType=='2' && not empty sysAttendCategoryForm.fdAppName}">
		        	<li>
			          <span>
			          	<bean:message bundle="sys-attend" key="sysAttendCategory.fdAppName"/> ：
			          </span>
			          <span>
			          	<c:out value="${sysAttendCategoryForm.fdAppName }"></c:out>
			          </span>
			        </li>
		        </c:if>
		        <c:if test="${sysAttendCategoryForm.fdType=='2' && not empty sysAttendCategoryForm.fdTemplateName}">
		        	<li>
			          <span>
			          	<bean:message bundle="sys-attend" key="sysAttendCategoryTemplate.fdName"/> ：
			          </span>
			          <span>
			          	<c:out value="${sysAttendCategoryForm.fdTemplateName }"></c:out>
			          </span>
			        </li>
		        </c:if>
		        <c:if test="${sysAttendCategoryForm.fdType=='1' && not empty sysAttendCategoryForm.fdATemplateName}">
		        	<li>
			          <span>
			          	<bean:message bundle="sys-attend" key="sysAttendCategoryTemplate.fdName"/> ：
			          </span>
			          <span>
			          	<c:out value="${sysAttendCategoryForm.fdATemplateName }"></c:out>
			          </span>
			        </li>
		        </c:if>

		        <li>
		          <span>${ lfn:message('sys-attend:sysAttendCategory.docCreateTime1') }：</span>
		          <span><xform:datetime showStatus="view" property="docCreateTime"  /></span>
		        </li>
		        <c:if test="${not empty sysAttendCategoryForm.docAlterTime }">
		        <li>
		          <span>${ lfn:message('sys-attend:sysAttendCategory.docAlterTime') }：</span>
		          <span><xform:datetime showStatus="view" property="docAlterTime"  /></span>
		        </li>
		        </c:if>
		      </ul>
		    </div>

		    <%if(ISysAuthConstant.IS_AREA_ENABLED){ %>
		    <%-- 所属场所 --%>
           	<div class="lui-singin-detailPage-panel">
		      <h2 class="lui-singin-detailPage-panel-title">${ lfn:message('sys-attend:sysAttend.place') }</h2>
		      <div class="lui-singin-detailPage-panel-body">
		        <dl>
		          <dt>${ lfn:message('sys-attend:sysAttend.premises') }：</dt>
		          <dd>
		          	<c:out value="${sysAttendCategoryForm.authAreaName}" />
		          </dd>
		        </dl>
		      </div>
		    </div>
		    <%} %>

		    <div class="lui-singin-detailPage-panel">
		      <h2 class="lui-singin-detailPage-panel-title">${ lfn:message('sys-attend:sysAttendCategory.fdManager') }</h2>
		      <div class="lui-singin-detailPage-panel-body">
		        <dl>
		          <dt>${ lfn:message('sys-attend:sysAttendCategory.fdManager') }：</dt>
		          <dd>
		          	<c:out value="${sysAttendCategoryForm.fdManagerName}" />
		          </dd>
		        </dl>
		      </div>
		    </div>

		    <div class="lui-singin-detailPage-panel">
		      <h2 class="lui-singin-detailPage-panel-title">${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit') }</h2>
		      <div class="lui-singin-detailPage-panel-body">
		        <dl>
		          <dt>${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit') }：</dt>
		          <dd class="pl-70" style="word-break: break-all;">
		          		<span><c:out value="${sysAttendCategoryForm.fdTargetNames}" /> </span>
		          </dd>
		        </dl>
		        <c:if test="${not empty sysAttendCategoryForm.fdExcTargetNames}">
			        <dl>
			          <dt>${ lfn:message('sys-attend:sysAttendCategory.fdExcTargets') }：</dt>
			          <dd class="pl-70" style="word-break: break-all;">
			          		<span><c:out value="${sysAttendCategoryForm.fdExcTargetNames}" /></span>
			          </dd>
			        </dl>
		        </c:if>
		      </div>
		    </div>

		    <div class="lui-singin-detailPage-panel">
		      <h2 class="lui-singin-detailPage-panel-title">${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }</h2>
		      <div class="lui-singin-detailPage-panel-body">
		      	<c:if test="${sysAttendCategoryForm.fdType eq '1' }">
		      	<dl>
		      		<dt>
		      			${ lfn:message('sys-attend:sysAttendCategory.fdShiftType') }：
		      		</dt>
		      		<dd class="pl-70" style="padding-left: 70px;">
		      			<xform:radio property="fdShiftType" alignment="V" value="${sysAttendCategoryForm.fdShiftType eq null ? 0 : sysAttendCategoryForm.fdShiftType}">
		      				<xform:simpleDataSource value="0">
		      					${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.fixed') }
		      					<c:if test="${sysAttendCategoryForm.fdSameWorkTime == '0' }">
		      						（${ lfn:message('sys-attend:sysAttendCategory.fdSameWorkTime.yes') }）
		      					</c:if>
		      					<c:if test="${sysAttendCategoryForm.fdSameWorkTime == '1' }">
		      						（${ lfn:message('sys-attend:sysAttendCategory.fdSameWorkTime.no') }）
		      					</c:if>
		      				</xform:simpleDataSource>
		      				<xform:simpleDataSource value="1">
		      					${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.schedule') }
		      				</xform:simpleDataSource>
		      				<xform:simpleDataSource value="2">
		      					${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.custom') }
		      				</xform:simpleDataSource>
		      				<xform:simpleDataSource value="3">
		      					${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.comprehensive') }
		      				</xform:simpleDataSource>
							<%-- 不定时工作制  --%>
							<xform:simpleDataSource value="4">
								${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.unfixed') }
							</xform:simpleDataSource>

		      			</xform:radio>
		      		</dd>
		      	</dl>
		      	</c:if>
		          <%-- 固定班制 --%>
		          <c:if test="${sysAttendCategoryForm.fdShiftType == '0' }">
		          	<dl>
		          	<%--一周相同时间 --%>
		          	<c:if test="${sysAttendCategoryForm.fdSameWorkTime == '0'}">
		         		<dt>${ lfn:message('sys-attend:sysAttendCategory.attendDate') }：</dt>
		          		<dd>&nbsp;</dd>
			        	<dd class="pl-70">${ lfn:message('sys-attend:sysAttendCategory.fdWeek') }：
			         		${sysAttendCategoryForm.fdWeekNames }
						</dd>
		          	</c:if>
		          	<%--一周不同时间 --%>
		          	<c:if test="${sysAttendCategoryForm.fdSameWorkTime == '1'}">
	          			<dt>
	          				${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }：
	          			</dt>
						<dd>&nbsp;</dd>
	          			<c:forEach items="${sysAttendCategoryForm.fdTimeSheets }" var="tSheet">
		          			<dd class="pl-70">
								${ lfn:message('sys-attend:sysAttendCategory.fdWeek') }：${tSheet.fdWeekNames }
							</dd>
							<dd class="pl-70">${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }： ${tSheet.fdWorkTimeText } </dd>
							<%--一周不同时间 最早最晚打卡时间--%>
							<c:set var="_fdStartTime" value="${tSheet.fdStartTime1}"></c:set>
							<c:set var="_fdStartTime2" value="${tSheet.fdStartTime2}"></c:set>
							<%
								String _fdStartTime = (String)pageContext.getAttribute("_fdStartTime");
								pageContext.setAttribute("__fdStartTime", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdStartTime, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));

								String _fdStartTime2 = (String)pageContext.getAttribute("_fdStartTime2");
								pageContext.setAttribute("_fdStartTime2", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdStartTime2, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
							%>
							<c:set var="_fdEndTime" value="${tSheet.fdEndTime1}"></c:set>
							<c:set var="_fdEndTime2" value="${tSheet.fdEndTime2}"></c:set>
							<%
								String _fdEndTime = (String)pageContext.getAttribute("_fdEndTime");
								pageContext.setAttribute("__fdEndTime", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdEndTime, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));

								String _fdEndTime2 = (String)pageContext.getAttribute("_fdEndTime2");
								pageContext.setAttribute("__fdEndTime2", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdEndTime2, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
							%>
							<dd class="pl-70">
									${ lfn:message('sys-attend:sysAttendCategory.sign.first.end.time') }：${__fdStartTime }
								<c:choose>
									<c:when test="${tSheet.fdWork==2}">
										- ${__fdEndTime }
										; ${_fdStartTime2 } - ${__fdEndTime2}
									</c:when>
									<c:otherwise>
										- ${__fdEndTime2 }
									</c:otherwise>
								</c:choose>
								<c:if test="${empty tSheet.fdEndDay || tSheet.fdEndDay eq '1'}">
									（${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }）
								</c:if>
								<c:if test="${tSheet.fdEndDay eq '2'}">
									（${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }）
								</c:if>
							</dd>

							<c:if test="${not empty tSheet.fdRestStartTime && not empty tSheet.fdRestEndTime}">
								<c:set var="_fdRestStartTime" value="${tSheet.fdRestStartTime}"></c:set>
								<%
									String _fdRestStartTime = (String)pageContext.getAttribute("_fdRestStartTime");
									pageContext.setAttribute("__fdRestStartTime", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdRestStartTime, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
								%>
								<c:set var="_fdRestEndTime" value="${tSheet.fdRestEndTime}"></c:set>
								<%
									String _fdRestEndTime = (String)pageContext.getAttribute("_fdRestEndTime");
									pageContext.setAttribute("__fdRestEndTime", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdRestEndTime, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
								%>
								<c:if test="${sysAttendCategoryForm.fdWork!=2}">
									<dd class="pl-70">
											${ lfn:message('sys-attend:sysAttendCategory.noon.restTime') }：
											${__fdRestStartTime }
										<c:if test="${empty tSheet.fdRestStartType || tSheet.fdRestStartType eq '1'}">
											（${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }）
										</c:if>
										<c:if test="${tSheet.fdRestStartType eq '2'}">
											（${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }）
										</c:if>

										- ${__fdRestEndTime}
										<c:if test="${empty tSheet.fdRestEndType || tSheet.fdRestEndType eq '1'}">
											（${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }）
										</c:if>
										<c:if test="${tSheet.fdRestEndType eq '2'}">
											（${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }）
										</c:if>
									</dd>
								</c:if>
							</c:if>
		          			</dd>
							<dd style="border:#f7f6f6 solid 1px;"></dd>
	          			</c:forEach>
		          	</c:if>
	          		<c:if test="${not empty sysAttendCategoryForm.fdHolidayId }">
				  		<dd class="pl-70">${ lfn:message('sys-attend:sysAttendMain.fdDateType.holiday') }：${sysAttendCategoryForm.fdHolidayName }</dd>
					</c:if>
					<c:if test="${fn:length(sysAttendCategoryForm.fdTimes)>0 }">
						  <dd class="pl-70">${ lfn:message('sys-attend:sysAttendCategory.fdTimes') }：
			          			<c:forEach var="_item" items="${sysAttendCategoryForm.fdTimes }">
									<%
									SysAttendCategoryTimeForm cateTimeForm = (SysAttendCategoryTimeForm)pageContext.getAttribute("_item");
									Date _fdTimes = DateUtil.convertStringToDate(cateTimeForm.getFdTime(), DateUtil.TYPE_DATETIME,request.getLocale());
									pageContext.setAttribute("__fdTime", DateUtil.convertDateToString(_fdTimes, DateUtil.TYPE_DATE, request.getLocale()));
									%>
										<span>${__fdTime}</span>
								</c:forEach>
				          </dd>
			         </c:if>
			         <c:if test="${fn:length(sysAttendCategoryForm.fdExcTimes)>0 }">
				          <dd class="pl-70">${ lfn:message('sys-attend:sysAttendCategory.fdExcTimes') }：
				          		<c:forEach var="_item" items="${sysAttendCategoryForm.fdExcTimes }">
									<%
										SysAttendCategoryExctimeForm cateExcTimeForm = (SysAttendCategoryExctimeForm)pageContext.getAttribute("_item");
										Date _fdTimes = DateUtil.convertStringToDate(cateExcTimeForm.getFdExcTime(), DateUtil.TYPE_DATETIME,request.getLocale());
										pageContext.setAttribute("__fdExcTime", DateUtil.convertDateToString(_fdTimes, DateUtil.TYPE_DATE, request.getLocale()));
									%>
									<span>${__fdExcTime}</span>
								</c:forEach>
				          </dd>
			        </c:if>
			        </dl>
		          </c:if>
		          <%-- 排班 --%>
		          <c:if test="${sysAttendCategoryForm.fdShiftType == '1' }">

		          </c:if>
		          <%-- 自定义 --%>
		          <c:if test="${sysAttendCategoryForm.fdShiftType == '2' }">
		          	<c:if test="${fn:length(sysAttendCategoryForm.fdTimes)>0 }">
	          			<dl>
	          			 <dt>${ lfn:message('sys-attend:sysAttendCategory.attendDate') }：</dt>
			         	 <dd class="pl-70">${ lfn:message('sys-attend:sysAttendCategoryTime.fdTime') }：
			          	 	<div>
			          			<c:forEach var="_item" items="${sysAttendCategoryForm.fdTimes }">
									<%
									SysAttendCategoryTimeForm cateTimeForm = (SysAttendCategoryTimeForm)pageContext.getAttribute("_item");
									Date _fdTimes = DateUtil.convertStringToDate(cateTimeForm.getFdTime(), DateUtil.TYPE_DATETIME,request.getLocale());
									pageContext.setAttribute("__fdTime", DateUtil.convertDateToString(_fdTimes, "yyyy-MM-dd"));
									%>
										<span>${__fdTime}</span>
								</c:forEach>
			          		</div>
				          </dd>
			          	</dl>
			          </c:if>
		          </c:if>
				  <%-- 综合工时 --%>
				  <c:if test="${sysAttendCategoryForm.fdShiftType == '3' }">
						  <dd class="pl-70">${ lfn:message('sys-attend:sysAttendCategory.fdWeek') }：
								  ${sysAttendCategoryForm.fdWeekNames }
						  </dd>
				  </c:if>
				  <%-- 不定时工作制  --%>
				  <c:if test="${sysAttendCategoryForm.fdShiftType == '4' }">
					  <dd class="pl-70">${ lfn:message('sys-attend:sysAttendCategory.fdShiftType.unfixed') }：
							  ${sysAttendCategoryForm.fdWeekNames }
					  </dd>
				  </c:if>

		        </dl>
		        <c:if test="${sysAttendCategoryForm.fdType=='1' }">
		        	<%-- 签到时间 --%>
		        	<c:if test="${(sysAttendCategoryForm.fdShiftType == '0' && sysAttendCategoryForm.fdSameWorkTime == '0') || sysAttendCategoryForm.fdShiftType == '2'}">
	          			<dl>
				          <dt>${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }：</dt>
				          <dd>&nbsp;</dd>
				          <dd class="pl-70">${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }：
				          	<c:forEach var="item" items="${sysAttendCategoryForm.fdWorkTime }">
				          		<c:if test="${item.fdIsAvailable == 'true' }">
				          			<c:set var="_fdStartTime" value="${item.fdStartTime}"></c:set>
									<c:set var="_fdEndTime" value="${item.fdEndTime}"></c:set>
									<%
										String _fdStartTime = (String)pageContext.getAttribute("_fdStartTime");
										String _fdEndTime = (String)pageContext.getAttribute("_fdEndTime");
										pageContext.setAttribute("__fdStartTime", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdStartTime, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
										pageContext.setAttribute("__fdEndTime", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdEndTime, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
									%>
									${__fdStartTime }-${__fdEndTime}
									<c:if test="${item.fdOverTimeType eq '2'}">
						          		（${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }）
						          	</c:if>
				          		</c:if>
							</c:forEach>
				          </dd>
							<c:set var="_fdStartTime" value="${sysAttendCategoryForm.fdStartTime}"></c:set>
							<c:set var="_fdStartTime2" value="${sysAttendCategoryForm.fdStartTime2}"></c:set>
							<%
								String _fdStartTime = (String)pageContext.getAttribute("_fdStartTime");
								pageContext.setAttribute("__fdStartTime", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdStartTime, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));

								String _fdStartTime2 = (String)pageContext.getAttribute("_fdStartTime2");
								pageContext.setAttribute("_fdStartTime2", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdStartTime2, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
							%>
							<c:set var="_fdEndTime" value="${sysAttendCategoryForm.fdEndTime1}"></c:set>
							<c:set var="_fdEndTime2" value="${sysAttendCategoryForm.fdEndTime}"></c:set>
							<%
								String _fdEndTime = (String)pageContext.getAttribute("_fdEndTime");
								pageContext.setAttribute("__fdEndTime", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdEndTime, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));

								String _fdEndTime2 = (String)pageContext.getAttribute("_fdEndTime2");
								pageContext.setAttribute("__fdEndTime2", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdEndTime2, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
							%>
							<dd class="pl-70">
								 ${ lfn:message('sys-attend:sysAttendCategory.sign.first.end.time') }：
								 ${__fdStartTime }
								<c:choose>
									<c:when test="${sysAttendCategoryForm.fdWork==2}">
										- ${__fdEndTime }
										; ${_fdStartTime2 } - ${__fdEndTime2}
									</c:when>
									<c:otherwise>
										- ${__fdEndTime2 }
									</c:otherwise>
								</c:choose>
								<c:if test="${empty sysAttendCategoryForm.fdEndDay || sysAttendCategoryForm.fdEndDay eq '1'}">
									（${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }）
								</c:if>
								<c:if test="${sysAttendCategoryForm.fdEndDay eq '2'}">
									（${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }）
								</c:if>
							</dd>
				          <c:if test="${not empty sysAttendCategoryForm.fdRestStartTime && not empty sysAttendCategoryForm.fdRestEndTime}">
				          		<c:set var="_fdRestStartTime" value="${sysAttendCategoryForm.fdRestStartTime}"></c:set>
								<%
									String _fdRestStartTime = (String)pageContext.getAttribute("_fdRestStartTime");
									pageContext.setAttribute("__fdRestStartTime", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdRestStartTime, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
								%>
								<c:set var="_fdRestEndTime" value="${sysAttendCategoryForm.fdRestEndTime}"></c:set>
								<%
									String _fdRestEndTime = (String)pageContext.getAttribute("_fdRestEndTime");
									pageContext.setAttribute("__fdRestEndTime", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdRestEndTime, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
								%>
								<c:if test="${sysAttendCategoryForm.fdWork!=2}">
									<dd class="pl-70">
							         	${ lfn:message('sys-attend:sysAttendCategory.noon.restTime') }：
							         	${__fdRestStartTime }
											<c:if test="${empty sysAttendCategoryForm.fdRestStartType || sysAttendCategoryForm.fdRestStartType eq '1'}">
												（${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }）
											</c:if>
											<c:if test="${sysAttendCategoryForm.fdRestStartType eq '2'}">
												（${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }）
											</c:if>

											- ${__fdRestEndTime}
											<c:if test="${empty sysAttendCategoryForm.fdRestEndType || sysAttendCategoryForm.fdRestEndType eq '1'}">
												（${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.firstDay') }）
											</c:if>
											<c:if test="${sysAttendCategoryForm.fdRestEndType eq '2'}">
												（${ lfn:message('sys-attend:sysAttendCategory.fdEndDay.secondDay') }）
											</c:if>
							         </dd>
						         </c:if>
				          </c:if>
				        </dl>

				        <c:if test="${not empty sysAttendCategoryForm.fdTotalTime }">
				        <dl>
				        	<dt>
				        		${ lfn:message('sys-attend:sysAttendStat.fdTotalTime') }：
				        	</dt>
				        	<dd class="pl-70">
				        		${sysAttendCategoryForm.fdTotalTime }${ lfn:message('sys-attend:sysAttendCategory.hour') }
				        	</dd>
				        </dl>
				        </c:if>
		          	</c:if>


		        </c:if>
		        <c:if test="${sysAttendCategoryForm.fdType=='2' }">
		        	<dl>
			          <dt>${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }：</dt>
			          <dd>&nbsp;</dd>
			          <c:set var="_fdStartTime" value="${sysAttendCategoryForm.fdStartTime}"></c:set>
						<%
							String _fdStartTime = (String)pageContext.getAttribute("_fdStartTime");
							pageContext.setAttribute("__fdStartTime", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdStartTime, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
						%>
						<c:set var="_fdEndTime" value="${sysAttendCategoryForm.fdEndTime}"></c:set>
						<%
							String _fdEndTime = (String)pageContext.getAttribute("_fdEndTime");
							pageContext.setAttribute("__fdEndTime", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdEndTime, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
						%>
			          <dd class="pl-70">${ lfn:message('sys-attend:sysAttendCategory.fdStartTime') }：${__fdStartTime }</dd>
			          <dd class="pl-70">${ lfn:message('sys-attend:sysAttendCategory.fdEndTime') }：${__fdEndTime }</dd>

			        </dl>
		        </c:if>
		      </div>
		    </div>
		    <%-- 考勤规则 --%>
		    <c:if test="${sysAttendCategoryForm.fdType=='1'}">
		    	<div class="lui-singin-detailPage-panel">
		    		<h2 class="lui-singin-detailPage-panel-title">${ lfn:message('sys-attend:sysAttendCategory.rule.setting.title') }</h2>
		    		<div class="lui-singin-detailPage-panel-body">
			    		<dl>
			    			<%-- 外勤 --%>
			    			<dt>
			    				${ lfn:message('sys-attend:sysAttendMain.fdOutside') }：
			    			</dt>
			    			<dd class="pl-70" style="padding-left: 45px;">
				          		<c:if test="${sysAttendCategoryForm.fdRule[0].fdOutside=='true'}">
				          			${ lfn:message('sys-attend:sysAttendCategoryRule.fdOutside.allowOrNot') }
				          		</c:if>
				          		<c:if test="${sysAttendCategoryForm.fdRule[0].fdOutside!='true'}">
				          			${ lfn:message('sys-attend:sysAttendCategoryRule.fdOutside.notAllow') }
				          		</c:if>
			    			</dd>
			    			<c:if test="${sysAttendCategoryForm.fdRule[0].fdOutside=='true'}">
			    			<dd class="pl-70" style="padding-left: 45px;">
			    				<xform:radio property="fdOsdReviewType" alignment="V" showStatus="view">
			    					<xform:simpleDataSource value="0">${ lfn:message('sys-attend:sysAttendCategory.fdOsdReviewType.noReview') }</xform:simpleDataSource>
			    					<xform:simpleDataSource value="1">${ lfn:message('sys-attend:sysAttendCategory.fdOsdReviewType.review') }</xform:simpleDataSource>
			    				</xform:radio>
			    				<%-- 外勤打卡上传照片 --%>
			    				<br/>
			    				<xform:checkbox property="fdOsdReviewIsUpload">
									<xform:simpleDataSource value="1">
										${ lfn:message('sys-attend:sysAttendCategory.fdOsdReviewType.addphoto') }
									</xform:simpleDataSource>
								</xform:checkbox>
		    				</dd>
		    				</c:if>

							<dd class="pl">
								${ lfn:message('sys-attend:sysAttendCategory.fdIsFlex') }：
									<c:if test="${not sysAttendCategoryForm.fdIsFlex || empty sysAttendCategoryForm.fdFlexTime}">
										${ lfn:message('sys-attend:sysAttendCategory.fdIsFlex.close') }${ lfn:message('sys-attend:sysAttendCategory.fdIsFlex') }
									</c:if>
									<c:if test="${sysAttendCategoryForm.fdIsFlex && not empty sysAttendCategoryForm.fdFlexTime}">
										${ lfn:message('sys-attend:sysAttendCategory.fdFlexTime.tips') }${sysAttendCategoryForm.fdFlexTime}${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }
									</c:if>
							</dd>

			    			<%-- 迟到早退 --%>
			    			<c:if test="${not sysAttendCategoryForm.fdIsFlex }">
			    				<c:if test="${sysAttendCategoryForm.fdRule[0].fdLateTime!=null && sysAttendCategoryForm.fdRule[0].fdLateTime!=0}">
			    					<dd class="pl">${ lfn:message('sys-attend:sysAttendCategoryRule.fdLateTime') }：${sysAttendCategoryForm.fdRule[0].fdLateTime} ${ lfn:message('sys-attend:sysAttendCategoryRule.fdLateTime.setting') }</dd>
			    				</c:if>
					          	<c:if test="${sysAttendCategoryForm.fdRule[0].fdLeftTime!=null && sysAttendCategoryForm.fdRule[0].fdLeftTime!=0}">
			    					<dd class="pl">${ lfn:message('sys-attend:sysAttendCategoryRule.fdLeftTime') }：${sysAttendCategoryForm.fdRule[0].fdLeftTime} ${ lfn:message('sys-attend:sysAttendCategoryRule.fdLeftTime.setting') }</dd>
			    				</c:if>
				          	</c:if>
				          	<%-- 事假 --%>
				          	<c:if test="${sysAttendCategoryForm.fdLateToAbsentTime!=null && sysAttendCategoryForm.fdLateToAbsentTime!=0 || sysAttendCategoryForm.fdLeftToAbsentTime!=null && sysAttendCategoryForm.fdLeftToAbsentTime!=0 || sysAttendCategoryForm.fdLateToFullAbsTime!=null && sysAttendCategoryForm.fdLateToFullAbsTime!=0 || sysAttendCategoryForm.fdLeftToFullAbsTime!=null && sysAttendCategoryForm.fdLeftToFullAbsTime!=0}">
				          	<dt>

				          	</dt>
					    		<dt>${ lfn:message('sys-attend:sysAttendCategory.personalLeave') }：</dt>
					    		<c:if test="${sysAttendCategoryForm.fdLateTotalTime!=null && sysAttendCategoryForm.fdLateTotalTime!=0}">
					    		<dd>
					    		迟到累计${sysAttendCategoryForm.fdLateTotalTime }分钟开始算事假
					    		</dd>
					    		</c:if>
					    		<c:if test="${sysAttendCategoryForm.fdLateNumTotalTime!=null && sysAttendCategoryForm.fdLateNumTotalTime!=0}">
					    		<dd>
					    		或者迟到次数达到${sysAttendCategoryForm.fdLateNumTotalTime }次开始算事假
					    		</dd>
					    		</c:if>


					    			<c:if test="${sysAttendCategoryForm.fdLateToAbsentTime!=null && sysAttendCategoryForm.fdLateToAbsentTime!=0}">
					    				<dd class="pl-70" style="padding-left: 45px;">
					    					${ lfn:message('sys-attend:sysAttendCategory.fdLateToAbsentTime.over') } ${sysAttendCategoryForm.fdLateToAbsentTime } ${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }${ lfn:message('sys-attend:sysAttendCategory.half.personalLeave') }
					    				</dd>
					    			</c:if>
					    			<c:if test="${sysAttendCategoryForm.fdLeftToAbsentTime!=null && sysAttendCategoryForm.fdLeftToAbsentTime!=0}">
					    				<dd class="pl-70" style="padding-left: 45px;">
					    					${ lfn:message('sys-attend:sysAttendCategory.fdLeftToAbsentTime.over') } ${sysAttendCategoryForm.fdLeftToAbsentTime } ${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }${ lfn:message('sys-attend:sysAttendCategory.half.personalLeave') }
					    				</dd>
					    			</c:if>
					    			<c:if test="${sysAttendCategoryForm.fdWork!=2}">
					    			<c:if test="${sysAttendCategoryForm.fdLateToFullAbsTime!=null && sysAttendCategoryForm.fdLateToFullAbsTime!=0}">
					    				<dd class="pl-70" style="padding-left: 45px;">
					    					${ lfn:message('sys-attend:sysAttendCategory.fdLateToAbsentTime.over') } ${sysAttendCategoryForm.fdLateToFullAbsTime } ${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }${ lfn:message('sys-attend:sysAttendCategory.full.personalLeave') }
					    				</dd>
					    			</c:if>
					    			<c:if test="${sysAttendCategoryForm.fdLeftToFullAbsTime!=null && sysAttendCategoryForm.fdLeftToFullAbsTime!=0}">
					    				<dd class="pl-70" style="padding-left: 45px;">
					    					${ lfn:message('sys-attend:sysAttendCategory.fdLeftToAbsentTime.over') } ${sysAttendCategoryForm.fdLeftToFullAbsTime } ${ lfn:message('sys-attend:sysAttendCategoryRule.minute') }${ lfn:message('sys-attend:sysAttendCategory.full.personalLeave') }
					    				</dd>
					    			</c:if>
					    			</c:if>
				          	</c:if>
							<%-- 旷工 --%>
								<dt>${ lfn:message('sys-attend:sysAttendCategory.absence') }：</dt>
								<dd class="pl-70" style="padding-left: 45px;">
										<span class="comment-text">
												${ lfn:message('sys-attend:sysAttendCategory.absence.tips') }
										</span>
								</dd>
				          	<%-- 加班 --%>
				    		<c:if test="${not sysAttendCategoryForm.fdIsOvertime }">
			    				<dd class="pl">${ lfn:message('sys-attend:sysAttendCategory.overTime.title') }：${ lfn:message('sys-attend:sysAttendCategory.fdIsOvertime.not.view') }</dd>
				    		</c:if>
				    		<c:if test="${sysAttendCategoryForm.fdIsOvertime }">
			    				<dt>${ lfn:message('sys-attend:sysAttendCategory.overTime.title') }：</dt>
			    				<dd class="pl" style="padding-left: 45px;">${ lfn:message('sys-attend:sysAttendCategory.fdIsOvertime') }</dd>
				    			<dd class="pl-70" style="padding-left: 45px;">${ lfn:message('sys-attend:sysAttendCategory.overtime.atLeast') }：${sysAttendCategoryForm.fdMinOverTime==null?0:sysAttendCategoryForm.fdMinOverTime}${ lfn:message('sys-attend:sysAttendCategory.overtime.hour') }
			    				</dd>

			    				<dd class="pl-70" style="padding-left: 45px;">
			    				<xform:radio property="fdOvtReviewType" alignment="V">
			    					<xform:enumsDataSource enumsType="sysAttendCategory_fdOvtReviewType" />
			    				</xform:radio>
			    				</dd>
			    				<c:if test="${sysAttendCategoryForm.fdIsOvertimeDeduct}">
									<dd class="pl-70" style="padding-left: 45px;">
										<ui:switch property="fdBeforeWorkOverTime"
												   showType="show"
												   enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdBeforeWorkOverTime.yes') }"
												   disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdBeforeWorkOverTime.no') }" ></ui:switch>

									</dd>
									<dd class="pl-70" style="padding-left: 45px;">
										<ui:switch property="fdIsCalculateOvertime"
												   showType="show"
												   enabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsCalculateOvertime.yes') }"
												   disabledText="${ lfn:message('sys-attend:sysAttendCategory.fdIsCalculateOvertime.no') }" ></ui:switch>

									</dd>
			    					<dt class="pl-70" style="padding-left: 45px;">${ lfn:message('sys-attend:sysAttendCategory.fdIsOvertimeDeduct.tittle') }</dt>
			    					<dd class="pl-70" style="padding-left: 45px;">
					    				<xform:radio property="fdOvtDeductType" alignment="H">
					    					<xform:enumsDataSource enumsType="sysAttendCategory_fdOvtDeductType" />
					    				</xform:radio>
		    						</dd>
		    						<c:if test="${sysAttendCategoryForm.fdOvtDeductType!=null && sysAttendCategoryForm.fdOvtDeductType==0}">
		    							<c:if test="${fn:length(sysAttendCategoryForm.overtimeDeducts)>0 }">
								         		<c:forEach var="deduct" items="${sysAttendCategoryForm.overtimeDeducts }">
									         		<c:set var="_fdStartDeduct" value="${deduct.fdStartTime}"></c:set>
									         		<%
														String _fdStartDeduct = (String)pageContext.getAttribute("_fdStartDeduct");
														pageContext.setAttribute("_fdStartDeduct", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdStartDeduct, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
													%>
													<c:set var="_fdEndDeduct" value="${deduct.fdEndTime}"></c:set>
									         		<%
														String _fdEndDeduct = (String)pageContext.getAttribute("_fdEndDeduct");
														pageContext.setAttribute("_fdEndDeduct", DateUtil.convertDateToString(DateUtil.convertStringToDate(_fdEndDeduct, DateUtil.TYPE_DATETIME,request.getLocale()), "HH:mm"));
													%>
								             		<dd class="pl-70"  style="padding-left: 142px;">
								             			${_fdStartDeduct}&nbsp;-&nbsp;${_fdEndDeduct}
								             		</dd>
								         		</c:forEach>
				             			</c:if>

		    						</c:if>
		    						<c:if test="${sysAttendCategoryForm.fdOvtDeductType!=null && sysAttendCategoryForm.fdOvtDeductType==1}">
		    							<c:if test="${fn:length(sysAttendCategoryForm.overtimeDeducts)>0 }">
								         		<c:forEach var="deduct" items="${sysAttendCategoryForm.overtimeDeducts }">
								             		<dd class="pl-70" style="padding-left: 142px;">
								             			${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.everyDayThreshold') }${deduct.fdThreshold}${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.deductTips') }${deduct.fdDeductHours}${ lfn:message('sys-attend:sysAttendCategory.fdOvtDeductType.deductHours') }
								             		</dd>
								         		</c:forEach>
				             			</c:if>
		    						</c:if>

			    				</c:if>
			    				<dd class="pl-70" style="padding-left: 45px;">
			    					<span>
				    					${ lfn:message('sys-attend:sysAttendCategory.fdRoundingType') }：
				    					<c:if test="${sysAttendCategoryForm.fdRoundingType == '0' }">
			      							${ lfn:message('sys-attend:sysAttendCategory.fdRoundingType.no') }
				      					</c:if>
				      					<c:if test="${sysAttendCategoryForm.fdRoundingType == '1' }">
				      						${ lfn:message('sys-attend:sysAttendCategory.fdRoundingType.upper') }
				      					</c:if>
				      					<c:if test="${sysAttendCategoryForm.fdRoundingType == '2' }">
				      						${ lfn:message('sys-attend:sysAttendCategory.fdRoundingType.lower') }
				      					</c:if>
			      					</span>
			      					<span style="padding-left: 35px;">
			      						<c:if test="${sysAttendCategoryForm.fdRoundingType != '0' }">
					      					${ lfn:message('sys-attend:sysAttendCategory.overtime.unit') }：
					      					${sysAttendCategoryForm.fdMinUnitHour==null?0:sysAttendCategoryForm.fdMinUnitHour}${ lfn:message('sys-attend:sysAttendCategory.overtime.unit.hour') }
				      					</c:if>
			      					</span>
			    				</dd>
				    		</c:if>
				    		<c:if test="${sysAttendCategoryForm.fdIsPatch eq null || sysAttendCategoryForm.fdIsPatch}">
				    			<dd class="pl">
				    				${ lfn:message('sys-attend:sysAttendCategory.fdIsPatch') }：${ lfn:message('sys-attend:sysAttendCategory.fdIsPatch.yes') }
				    			</dd>
				    			<c:if test="${not empty sysAttendCategoryForm.fdPatchTimes}">
				    			<dd class="pl-70" style="padding-left: 45px;">
				    				${ lfn:message('sys-attend:sysAttendCategory.fdPatchTimes.text1') }
				    				${sysAttendCategoryForm.fdPatchTimes }
				    				${ lfn:message('sys-attend:sysAttendCategory.fdPatchTimes.text2') }
				    			</dd>
				    			</c:if>
				    			<c:if test="${not empty sysAttendCategoryForm.fdPatchDay}">
				    			<dd class="pl-70" style="padding-left: 45px;">
				    				${ lfn:message('sys-attend:sysAttendCategory.fdPatchDay.text1') }
				    				${sysAttendCategoryForm.fdPatchDay }
									${ lfn:message('sys-attend:sysAttendCategory.fdPatchDay.text2') }
				    			</dd>
				    			</c:if>
				          	</c:if>
				          	<c:if test="${not sysAttendCategoryForm.fdIsPatch}">
				          		<dd class="pl">
				          			${ lfn:message('sys-attend:sysAttendCategory.fdIsPatch') }：${ lfn:message('sys-attend:sysAttendCategory.fdIsPatch.no') }
				          		</dd>
				          	</c:if>
			    		</dl>
		    		</div>
		    	</div>
		    </c:if>
			 <c:if test="${sysAttendCategoryForm.fdType=='1' || (sysAttendCategoryForm.fdType=='2' && not empty sysAttendCategoryForm.fdAppName)}">
			     <div class="lui-singin-detailPage-panel">
			       <h2 class="lui-singin-detailPage-panel-title">${ lfn:message('sys-attend:sysAttendCategoryRule.fdMode') }</h2>
			       <div class="lui-singin-detailPage-panel-body">
			         <dl>
					    <c:if test="${sysAttendCategoryForm.fdDingClock == 'true'}">
						    <%-- 考勤机 --%>
						    <dt>
						        ${ lfn:message('sys-attend:sysAttendCategory.fdDingClock') }：
						    </dt>
						    <dd class="pl-70" style="padding-left: 45px;">
						        ${ synConfigType=='qywx'?lfn:message('sys-attend:sysAttendCategory.fdWxClock.enable'):lfn:message('sys-attend:sysAttendCategory.fdDingClock.enable') }
						    </dd>
					    </c:if>
					    <c:if test="${sysAttendCategoryForm.fdCanMap != 'false'}">
					       <dt>${ lfn:message('sys-attend:sysAttendCategory.fdMode.map') }：</dt>
				           <c:if test="${sysAttendCategoryForm.fdType=='1'}">
				            <%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
				         	<c:set var="limitstr" value="-1"></c:set>
				         	<dd>&nbsp;</dd>
				         	<c:forEach var="item" items="${sysAttendCategoryForm.fdLocations }" varStatus="varStatus">
					         	<dd class="pl-70">
						         	<c:choose>
										<c:when test="${sysAttendCategoryForm.fdType=='1' && item.fdDataType=='newdata' && limitstr!=item.fdLimitIndex }">
											 <c:set var="limitstr" value="${ item.fdLimitIndex}"></c:set>
						           			 ${ item.fdLimit} ${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit.setting') }，
										</c:when>
										<c:otherwise>
											<c:if test="${oldLoad !='1' && sysAttendCategoryForm.fdRule[0].fdLimit!=null && sysAttendCategoryForm.fdRule[0].fdLimit!='' }">
												<c:set var="oldLoad" value="1"></c:set>
							          			${sysAttendCategoryForm.fdRule[0].fdLimit } ${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit.setting') }，
							          		</c:if>
										</c:otherwise>
									</c:choose>
					           	</dd>

				 				<dd class="pl-70">
				 					<map:location propertyName="fdLocations[${varStatus.index}].fdLocation" propertyCoordinate="fdLocations[${varStatus.index}].fdLocationCoordinate"
				 						subject="${ lfn:message('sys-attend:sysAttendCategory.fdLocations') }"
				 						style="width:95%;display:block;" showStatus="view" ></map:location>
				 				</dd>
				 			</c:forEach>
					       </c:if>
					    </c:if>
					    <c:if test="${sysAttendCategoryForm.fdCanWifi != 'false'}">
				             <c:if test="${fn:length(sysAttendCategoryForm.fdWifiConfigs)>0 }">
				             <dt>${ lfn:message('sys-attend:sysAttendCategory.singin.wifi') }：</dt>
				         		<c:forEach var="wifiConfig" items="${sysAttendCategoryForm.fdWifiConfigs }">
				             		<dd class="pl-70">
				             			${wifiConfig.fdName }&nbsp;${wifiConfig.fdMacIp }
				             		</dd>
				         		</c:forEach>
				             </c:if>
					    </c:if>

			      	  <c:if test="${sysAttendCategoryForm.fdType=='2' && not empty sysAttendCategoryForm.fdAppName}">
			      	  	<%
			      	  		SysAttendCategoryForm _form = (SysAttendCategoryForm)request.getAttribute("sysAttendCategoryForm");
			      	  		String url = "/sys/attend/sys_attend_main/sysAttendMain.do?method=updateByScan&categoryId=" + _form.getFdId();
			      	  		request.setAttribute("fdQRCodeUrl", StringUtil.formatUrl(url,true));
			      	  	%>
			           	<dd>${ lfn:message('sys-attend:sysAttendCategory.qrcode') }</dd>
			           	<dd class="pl-70">
			 	            <div class="lui-singin-QRcode">
			 	              <div id="luiQRCode"></div>
			 	              <span class="txt"><a href="#" onclick="window.downloadQRCode();" title="[${ lfn:message('sys-attend:sysAttendCategory.importView.download') }]">[${ lfn:message('sys-attend:sysAttendCategory.importView.download') }]</a></span>
			 	              <script type="text/javascript">
			 	               		seajs.use(['lui/qrcode','lui/jquery'],function(QRCode,$){
			 	               			var obj = QRCode.Qrcode({
			 	               				text : '${fdQRCodeUrl}',
			 	               				element : $('#luiQRCode'),
			 	               				render : 'canvas'
			 	               			});
			 	               			function _fixType(type) {
			 	               				var r = type.match(/png|jpeg|bmp|gif/)[0];
			 	               				return 'image/' + r;
			 	               			}
			 	               			window.downloadQRCode = function(){
			 	               				var type = "png", name = "${ lfn:message('sys-attend:sysAttendCategory.qrcode') }." + type,
			 	               					canvas = $('#luiQRCode canvas');
			 	               				if (window.navigator.msSaveBlob) {
			 	               					window.navigator.msSaveBlob(canvas[0].msToBlob(), name);
			 	               				} else {
			 	               					var imageData = canvas[0].toDataURL(type);
			 	               					imageData = imageData.replace(_fixType(type),
			 	               							'image/octet-stream');
			 	               					var save_link = document.createElementNS(
			 	               							"http://www.w3.org/1999/xhtml", "a");
			 	               					save_link.href = imageData;
			 	               					save_link.download = name;
			 	               					var ev = document.createEvent("MouseEvents");
			 	               					ev.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0,
			 	               							false, false, false, false, 0, null);
			 	               					save_link.dispatchEvent(ev);
			 	               					ev = null;
			 	               					delete save_link;
			 	               				}
			 	               			};
			 	               		});
			 	               </script>
			 	            </div>
			           	</dd>
			           </c:if>

			         </dl>
			       </div>
			     </div>
			 </c:if>
			<c:if test="${sysAttendCategoryForm.fdType=='2' && empty  sysAttendCategoryForm.fdAppName && fn:length(sysAttendCategoryForm.fdLocations)>0}">
				<div class="lui-singin-detailPage-panel">
			    	<h2 class="lui-singin-detailPage-panel-title">${ lfn:message('sys-attend:sysAttendCategoryRule.fdMode') }</h2>
			    	<div class="lui-singin-detailPage-panel-body">
			    		<dl>
			          		<dt>${ lfn:message('sys-attend:sysAttendCategory.fdMode.map') }：</dt>
			          		<dd>
			          		<c:if test="${sysAttendCategoryForm.fdRule[0].fdLimit!=null && sysAttendCategoryForm.fdRule[0].fdLimit!='' }">
			          			${sysAttendCategoryForm.fdRule[0].fdLimit }${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit.setting') }
			          		</c:if>
			          		</dd>
			          		<%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
			            	<c:forEach var="item" items="${sysAttendCategoryForm.fdLocations }" varStatus="varStatus">
								<dd class="pl-70">
									<map:location propertyName="fdLocations[${varStatus.index}].fdLocation" propertyCoordinate="fdLocations[${varStatus.index}].fdLocationCoordinate"
										subject="${ lfn:message('sys-attend:sysAttendCategory.fdLocations') }"
										style="width:95%;display:block;" showStatus="view" ></map:location>
								</dd>
							</c:forEach>
			          	</dl>
			    	</div>
		      	</div>
		    </c:if>
		    <c:if test="${sysAttendCategoryForm.fdType=='1' && fn:length(sysAttendCategoryForm.busSettingForms)>0}">
		    	<div class="lui-singin-detailPage-panel">
		    		<h2 class="lui-singin-detailPage-panel-title">${ lfn:message('sys-attend:sysAttendCategory.business.title') }</h2>
		    		<div class="lui-singin-detailPage-panel-body">
		    			<dl>
		    				<dt>${ lfn:message('sys-attend:sysAttendCategory.lbpm.template.business') }：</dt>
		    				<c:set scope="page" var="isNone1" value="true" />
		    				<c:forEach var="busItem" items="${sysAttendCategoryForm.busSettingForms }">
				    			<c:if test="${busItem.fdBusType == '4' }">
				    			<c:set scope="page" var="isNone1" value="false" />
				    				<dd class="pl-70">
				    					<a target="_blank" title="${busItem.fdBusName }"  href="${LUI_ContextPath }/km/review/km_review_template/kmReviewTemplate.do?method=view&fdId=${busItem.fdTemplateId}">
				    						${busItem.fdTemplateName }
				    					</a>
				    				</dd>
				    			</c:if>
				    		</c:forEach>
				    		<c:if test="${isNone1 }">
				    			<dd>${ lfn:message('sys-attend:sysAttendCategory.not.exist') }</dd>
				    		</c:if>
				    	</dl>
				    	<dl>
				    		<dt>${ lfn:message('sys-attend:sysAttendCategory.lbpm.template.askforleave') }：</dt>
				    		<c:set scope="page" var="isNone2" value="true" />
		    				<c:forEach var="busItem" items="${sysAttendCategoryForm.busSettingForms }">
				    			<c:if test="${busItem.fdBusType == '5' }">
				    			<c:set scope="page" var="isNone2" value="false" />
				    				<dd class="pl-70">
				    					<a target="_blank" title="${busItem.fdBusName }"  href="${LUI_ContextPath }/km/review/km_review_template/kmReviewTemplate.do?method=view&fdId=${busItem.fdTemplateId}">
				    						${busItem.fdTemplateName }
				    					</a>
				    				</dd>
				    			</c:if>
				    		</c:forEach>
				    		<c:if test="${isNone2 }">
				    			<dd>${ lfn:message('sys-attend:sysAttendCategory.not.exist') }</dd>
				    		</c:if>
		    			</dl>
		    			<dl>
		    				<dt>${ lfn:message('sys-attend:sysAttendCategory.lbpm.template.overtime') }：</dt>
		    				<c:set scope="page" var="isNone3" value="true" />
		    				<c:forEach var="busItem" items="${sysAttendCategoryForm.busSettingForms }">
				    			<c:if test="${busItem.fdBusType == '6' }">
				    			<c:set scope="page" var="isNone3" value="false" />
				    				<dd class="pl-70">
				    					<a target="_blank" title="${busItem.fdBusName }"  href="${LUI_ContextPath }/km/review/km_review_template/kmReviewTemplate.do?method=view&fdId=${busItem.fdTemplateId}">
				    						${busItem.fdTemplateName }
				    					</a>
				    				</dd>
				    			</c:if>
				    		</c:forEach>
				    		<c:if test="${isNone3 }">
				    			<dd>${ lfn:message('sys-attend:sysAttendCategory.not.exist') }</dd>
				    		</c:if>
				    	</dl>
				    	<dl>
		    				<dt>${ lfn:message('sys-attend:sysAttendCategory.lbpm.template.outgoing') }：</dt>
		    				<c:set scope="page" var="isNone4" value="true" />
		    				<c:forEach var="busItem" items="${sysAttendCategoryForm.busSettingForms }">
				    			<c:if test="${busItem.fdBusType == '7' }">
				    			<c:set scope="page" var="isNone4" value="false" />
				    				<dd class="pl-70">
				    					<a target="_blank" title="${busItem.fdBusName }"  href="${LUI_ContextPath }/km/review/km_review_template/kmReviewTemplate.do?method=view&fdId=${busItem.fdTemplateId}">
				    						${busItem.fdTemplateName }
				    					</a>
				    				</dd>
				    			</c:if>
				    		</c:forEach>
				    		<c:if test="${isNone4 }">
				    			<dd>${ lfn:message('sys-attend:sysAttendCategory.not.exist') }</dd>
				    		</c:if>
				    	</dl>
		    		</div>
		    	</div>
		    </c:if>

		    <c:if test="${sysAttendCategoryForm.fdType=='1'}">
		    	<div class="lui-singin-detailPage-panel">
		    		<h2 class="lui-singin-detailPage-panel-title">${ lfn:message('sys-attend:sysAttendCategory.notify.title') }</h2>
		    		<div class="lui-singin-detailPage-panel-body">
		    			<dl>
		    				<dt>${ lfn:message('sys-attend:sysAttendCategory.notify.work.title') }：</dt>
		    				<dd>&nbsp;</dd>
		    				<dd class="pl-70">
		    					${ lfn:message('sys-attend:sysAttendCategory.notify.on.title') }：
		    					<c:if test="${sysAttendCategoryForm.fdNotifyOnTime !=null && sysAttendCategoryForm.fdNotifyOnTime!=0}">
		    						${sysAttendCategoryForm.fdNotifyOnTime }${ lfn:message('sys-attend:sysAttendCategory.notify.on.tips') }
		    					</c:if>
		    					<c:if test="${sysAttendCategoryForm.fdNotifyOnTime==null || sysAttendCategoryForm.fdNotifyOnTime==0}">
		    						${ lfn:message('sys-attend:sysAttendCategory.not.exist') }
		    					</c:if>
		    				</dd>
		    				<dd class="pl-70">
		    					${ lfn:message('sys-attend:sysAttendCategory.notify.off.title') }：
		    					<c:if test="${sysAttendCategoryForm.fdNotifyOffTime !=null && sysAttendCategoryForm.fdNotifyOffTime!=0}">
		    						${sysAttendCategoryForm.fdNotifyOffTime }${ lfn:message('sys-attend:sysAttendCategory.notify.off.tips') }
		    					</c:if>
		    					<c:if test="${sysAttendCategoryForm.fdNotifyOffTime==null || sysAttendCategoryForm.fdNotifyOffTime==0}">
		    						${ lfn:message('sys-attend:sysAttendCategory.not.exist') }
		    					</c:if>
		    				</dd>
		    			</dl>
		    			<dl>
		    				<dt>${ lfn:message('sys-attend:sysAttendCategory.notify.result') }：</dt>
		    				<dd>&nbsp;</dd>
		    				<dd class="pl-70">
		    					<c:if test="${sysAttendCategoryForm.fdNotifyResult=='true' }">
		    						${ lfn:message('sys-attend:sysAttendCategory.notify.result.tips') }
		    					</c:if>
		    					<c:if test="${sysAttendCategoryForm.fdNotifyResult!='true' }">
		    						${ lfn:message('sys-attend:sysAttendCategory.notify.result.tips.not') }
		    					</c:if>
		    				</dd>
		    			</dl>
		    			<dl>
		    				<dt>${ lfn:message('sys-attend:sysAttendCategory.fdNotifyAttend') }：</dt>
		    				<dd>&nbsp;</dd>
		    				<dd class="pl-70">
		    					<c:if test="${sysAttendCategoryForm.fdNotifyAttend=='true' }">
		    						${ lfn:message('sys-attend:sysAttendCategory.fdNotifyAttend.tips') }
		    					</c:if>
		    					<c:if test="${sysAttendCategoryForm.fdNotifyAttend!='true' }">
		    						${ lfn:message('sys-attend:sysAttendCategory.fdNotifyAttend.tips.not') }
		    					</c:if>
		    				</dd>
		    			</dl>
		    		</div>
		    	</div>
		    </c:if>
		     <c:if test="${sysAttendCategoryForm.fdType=='2'}">
		     	<div class="lui-singin-detailPage-panel">
		     		<h2 class="lui-singin-detailPage-panel-title">${ lfn:message('sys-attend:sysAttendCategory.notify.title') }</h2>
		     		<div class="lui-singin-detailPage-panel-body">
		     			<dl>
		     				<dt>${ lfn:message('sys-attend:sysAttendCategory.notify.work.title') }：</dt>
		    				<dd>&nbsp;</dd>
		    				<dd class="pl-70">
		    					${ lfn:message('sys-attend:sysAttendCategory.notify.custom.on') }：
		    					<c:if test="${sysAttendCategoryForm.fdNotifyOnTime !=null && sysAttendCategoryForm.fdNotifyOnTime!=0}">
		    						${sysAttendCategoryForm.fdNotifyOnTime }${ lfn:message('sys-attend:sysAttendCategory.notify.on.tips') }
		    					</c:if>
		    					<c:if test="${sysAttendCategoryForm.fdNotifyOnTime==null || sysAttendCategoryForm.fdNotifyOnTime==0}">
		    						${ lfn:message('sys-attend:sysAttendCategory.not.exist') }
		    					</c:if>
		    				</dd>
		     			</dl>
		     			<dl>
		    				<dt>${ lfn:message('sys-attend:sysAttendCategory.notify.result') }：</dt>
		    				<dd>&nbsp;</dd>
		    				<dd class="pl-70">
		    					<c:if test="${sysAttendCategoryForm.fdNotifyResult=='true' }">
		    						${ lfn:message('sys-attend:sysAttendCategory.notify.custom.result.tips') }
		    					</c:if>
		    					<c:if test="${sysAttendCategoryForm.fdNotifyResult!='true' }">
		    						${ lfn:message('sys-attend:sysAttendCategory.notify.custom.result.tips.not') }
		    					</c:if>
		    				</dd>
		    			</dl>
		     		</div>
		     	</div>
		     </c:if>
		</div>
		<ui:tabpage expand="false">
			<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="sysAttendCategoryForm" />
				<c:param name="moduleModelName" value="com.landray.kmss.sys.attend.model.SysAttendCategory" />
			</c:import>
		</ui:tabpage>
		<script>
		seajs.use(['lui/dialog'],function(dialog){
			window.deleteDoc = function(delUrl) {
				var tip = "${ lfn:message('sys-attend:sysAttendCategory.delete.attend.tips') }";
				var fdType = "${sysAttendCategoryForm.fdType}";
				if(fdType=='2'){
					tip = "${ lfn:message('sys-attend:sysAttendCategory.delete.custom.tips') }";
				}
				var fdStatus = '${sysAttendCategoryForm.fdStatus}';
				if(fdStatus == '1'){
					if (fdType=='1') {
						dialog.alert("${ lfn:message('sys-attend:sysAttendCategory.doing.warn') }");
						return;
					} else if (fdType=='2') {
						dialog.alert("${ lfn:message('sys-attend:sysAttendCategory.doing.custom.warn') }");
						return;
					}
				}
				dialog.confirm(tip,function(isOk){
					if(isOk){
						Com_OpenWindow(delUrl,'_self');
					}
				});
			}
			window.closeDoc = function(url) {
				var tip = "${ lfn:message('sys-attend:sysAttendCategory.close.attend.tips') }";
				var fdType = "${sysAttendCategoryForm.fdType}";
				if(fdType=='2'){
					tip = "${ lfn:message('sys-attend:sysAttendCategory.close.custom.tips') }";
					dialog.confirm(tip,function(isOk){
						if(isOk){
							Com_OpenWindow(url,'_self');
						}
					});
				}else{
					//立即生效、明日生效
					dialog.confirm(tip, function (flag, d) {

					}, null, [{
						name: '${ lfn:message("sys-attend:sysAttendHisCategory.tip4") }',
						value: true,
						focus: true,
						fn: function (value, dialog) {
							url+="&fdStatusFlag=1";
							Com_OpenWindow(url,'_self');
						}
					}, {
						name: '${ lfn:message("sys-attend:sysAttendHisCategory.tip5") }',
						value: false,
						fn: function (value, dialog) {
							url+="&fdStatusFlag=0";
							Com_OpenWindow(url,'_self');
						}
					}]);
				}
			}
		});
		</script>
	</template:replace>
</template:include>