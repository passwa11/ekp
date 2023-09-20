<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.landray.kmss.km.imeeting.model.KmImeetingUse"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImeetingResUse" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 链接 -->
		<list:data-column col="link" escape="false">
			<c:if test="${kmImeetingResUse.isMeeting==true }">
				/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId=${kmImeetingResUse.fdId}
			</c:if>                                                                                   
		</list:data-column>
		<!-- 会议室 -->	
		<list:data-column  headerClass="width150" property="fdPlace" title="${ lfn:message('km-imeeting:kmImeetingResUse.fdPlace') }" />
		<!-- 会议名称 -->
		<list:data-column col="fdName" title="${ lfn:message('km-imeeting:kmImeetingResUse.fdName') }" escape="false" >
			<c:if test="${ kmImeetingResUse.isMeeting==true}">
				<c:out value="${kmImeetingResUse.fdName}" />
			</c:if>
			<c:if test="${ kmImeetingResUse.isMeeting==false}">
				(<bean:message bundle="km-imeeting" key="kmImeetingBook.book" />)<c:out value="${kmImeetingResUse.fdName}" />
			</c:if>
		</list:data-column>
		<!-- 会议开始时间 -->
		<list:data-column headerClass="width120" property="fdHoldDate" title="${ lfn:message('km-imeeting:kmImeetingResUse.fdHoldDate') }" />
		<!-- 会议结束时间 -->
		<list:data-column headerClass="width120" property="fdFinishDate" title="${ lfn:message('km-imeeting:kmImeetingResUse.fdFinishDate') }" />
		<!-- 会议登记人 -->
		<list:data-column   headerClass="width80" property="personName" title="${ lfn:message('km-imeeting:kmImeetingResUse.personName') }" />
		<!-- 状态 -->
		<list:data-column  headerClass="width60" col="docStatus" title="${ lfn:message('km-imeeting:kmImeetingResUse.docStatus') }" >
			<%
				Date now=new Date();
				Boolean isBegin=false,isEnd=false;
				KmImeetingUse kmImeetingResUse=(KmImeetingUse)pageContext.findAttribute("kmImeetingResUse");
				if (kmImeetingResUse.getFdHoldDate().getTime() < now.getTime()) {
					isBegin = true;
				}
				// 会议已结束
				if (kmImeetingResUse.getFdFinishDate().getTime() < now.getTime()) {
					isEnd = true;
				}
				request.setAttribute("isBegin", isBegin);
				request.setAttribute("isEnd", isEnd);
			%>
			<c:if test="${kmImeetingResUse.isMeeting==true }">
				<c:if test="${kmImeetingResUse.docStatus!='30' && kmImeetingResUse.docStatus!='41' }">
					<sunbor:enumsShow value="${kmImeetingResUse.docStatus}" enumsType="common_status" />
				</c:if>
				<%--未召开--%>
				<c:if test="${kmImeetingResUse.docStatus=='30' && isBegin==false }">
					<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.unHold"/>
				</c:if>
				<%--正在召开--%>
				<c:if test="${kmImeetingResUse.docStatus=='30' && isBegin==true && isEnd==false }">
					<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.holding"/>
				</c:if>
				<%--已召开--%>
				<c:if test="${kmImeetingResUse.docStatus=='30' && isEnd==true }">
					<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.hold"/>
				</c:if>
				<%--已取消--%>
				<c:if test="${kmImeetingResUse.docStatus=='41' }">
					<bean:message bundle="km-imeeting" key="kmImeeting.status.cancel" />
				</c:if>
			</c:if>
			<c:if test="${kmImeetingResUse.isMeeting==false }">
				<%
					if (kmImeetingResUse.getFdHasExam() != null) {
						if (kmImeetingResUse.getFdHasExam() == true) {
							request.setAttribute("fdHasExam", "true");
						} else {
							request.setAttribute("fdHasExam", "false");
						}
					} else {
						request.setAttribute("fdHasExam", "wait");
					}
				
				%>
				<c:choose>
					<c:when test="${fdHasExam == 'wait'}">
						<bean:message key="kmImeetingCalendar.res.wait" bundle="km-imeeting"/>
					</c:when>
					<c:when test="${fdHasExam == 'false'}">
						<bean:message key="kmImeetingCalendar.res.false" bundle="km-imeeting"/>
					</c:when>
					<c:otherwise>
						<%--未召开--%>
						<c:if test="${isBegin==false }">
							<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.unHold"/>
						</c:if>
						<%--正在召开--%>
						<c:if test="${isBegin==true && isEnd==false }">
							<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.holding"/>
						</c:if>
						<%--已召开--%>
						<c:if test="${isEnd==true }">
							<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.hold"/>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:if>
		</list:data-column>
		
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>