<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Date,com.landray.kmss.km.imeeting.model.KmImeetingMain"%>
<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.landray.kmss.km.imeeting.model.KmImeetingRes"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="kmImeetingMain" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
	    <%-- 主题--%>	
		<list:data-column col="label" title="${ lfn:message('km-imeeting:kmImeetingMain.fdName') }" escape="false">
			<c:out value="${kmImeetingMain.fdName}"/>
		</list:data-column>
		<%-- 召开时间~结束时间 --%>
		<list:data-column col="created" escape="false">
			<c:if test="${not empty kmImeetingMain.fdHoldDate or not empty kmImeetingMain.fdFinishDate }">
				<kmss:showDate value="${kmImeetingMain.fdHoldDate}" type="datetime"></kmss:showDate>
				 ~
				<kmss:showDate value="${kmImeetingMain.fdFinishDate}" type="datetime"></kmss:showDate>
			</c:if>
		</list:data-column>
		<list:data-column col="yearMonth" escape="false">
			<c:if test="${not empty kmImeetingMain.fdHoldDate or not empty kmImeetingMain.fdFinishDate }">
				<kmss:showDate value="${kmImeetingMain.fdHoldDate}" type="yearMonth"></kmss:showDate>
			</c:if>
		</list:data-column>
		 <%-- 主持人头像--%>
		<list:data-column col="icon" escape="false">
			<c:if test="${not empty kmImeetingMain.fdHost }" >
				<person:headimageUrl personId="${kmImeetingMain.fdHost.fdId}" size="m" />
			</c:if>
			<c:if test="${empty kmImeetingMain.fdHost }">
				<person:headimageUrl   personId="" size="m" />
			</c:if>
		</list:data-column>
		 <%-- 主持人--%>
		<list:data-column col="host" title="${ lfn:message('km-imeeting:kmImeetingMain.fdHost') }" escape="false">
			<c:if test="${not empty kmImeetingMain.fdHost or not empty kmImeetingMain.fdOtherHostPerson }">
				<c:if test="${not empty kmImeetingMain.fdHost}">
					<c:out value="${kmImeetingMain.fdHost.fdName}"/>
				</c:if>
			    <c:if test="${not empty kmImeetingMain.fdOtherHostPerson }">
			    	<c:out value="${ kmImeetingMain.fdOtherHostPerson }"></c:out>
			    </c:if>     
	    	</c:if>
		</list:data-column>
		 <%-- 发布时间
	 	<list:data-column col="created" title="${ lfn:message('km-imeeting:kmImeetingMain.fdHoldDate') }">
	        <kmss:showDate value="${kmImeetingMain.fdHoldDate}" type="datetime"></kmss:showDate>
      	</list:data-column>--%>
      	 <%-- 地点--%>
	 	<list:data-column col="place" title="${ lfn:message('km-imeeting:kmImeetingMain.fdPlace') }" escape="false">
	       <c:if test="${not empty kmImeetingMain.fdPlace }">
	       		<c:out value="${kmImeetingMain.fdPlace.fdName }"></c:out>
	       </c:if>
	       <c:if test="${not empty kmImeetingMain.fdOtherPlace }">
	       		<c:out value="${kmImeetingMain.fdOtherPlace }"></c:out>
	       </c:if>
      	</list:data-column>
      	
      	<%-- 分会场 --%>
      	<list:data-column col="vicePlace" title="${ lfn:message('km-imeeting:kmImeetingMain.fdVicePlace') }" escape="false">
      		<%
      		 	KmImeetingMain kmImeetingMain = (KmImeetingMain)pageContext.getAttribute("kmImeetingMain");
      			List<KmImeetingRes> vicePlaces = kmImeetingMain.getFdVicePlaces();
				List<String> vicePlaceNames = new ArrayList();
      			if(vicePlaces != null) {
      				for(KmImeetingRes res : vicePlaces) {
      					vicePlaceNames.add(res.getFdName());
      				}
      			}
      			String otherVicePlace = kmImeetingMain.getFdOtherVicePlace();
      			if(StringUtil.isNotNull(otherVicePlace)) {
	      			out.print(StringUtil.join(vicePlaceNames, ";") + " " + otherVicePlace);
      			} else {
      				out.print(StringUtil.join(vicePlaceNames, ";"));
      			}
      		%>
      	</list:data-column>
      	
      	<%--状态 --%>
      	<list:data-column col="status" title="${ lfn:message('km-imeeting:kmImeetingMain.docStatus') }" escape="false">
	       <%
	       	   Boolean isBegin=false,isEnd=false;
		       if(pageContext.getAttribute("kmImeetingMain")!=null){
		    	   Date now=new Date();
		    	   KmImeetingMain kmImeetingMain=(KmImeetingMain)pageContext.getAttribute("kmImeetingMain");
		    	   if(kmImeetingMain.getFdHoldDate()!=null && kmImeetingMain.getFdFinishDate()!=null){
		    	   		//会议提前结束时间
					   Date earlyDate = kmImeetingMain.getFdEarlyFinishDate();
					   //会议已开始
		    		   if(kmImeetingMain.getFdHoldDate().getTime() < now.getTime()){
		    			   isBegin=true;
		    		   }
		    		   if(kmImeetingMain.getFdFinishDate().getTime() < now.getTime()){
		    			   isEnd=true;
		    		   }
					   //提前结束的会议 显示已结束
					   if(earlyDate!=null  && earlyDate.getTime() < now.getTime()) {
						   isEnd = true;
					   }
		    	   }
		    	   String docStatus = kmImeetingMain.getDocStatus();
		    	   if("00".equals(docStatus )){
		    		   request.setAttribute("statusText", ResourceUtil.getString("status.discard"));
		    	   }
		    	   if("10".equals(docStatus )){
		    		   request.setAttribute("statusText", ResourceUtil.getString("status.draft"));
		    	   }
		    	   if("11".equals(docStatus )){
		    		   request.setAttribute("statusText", ResourceUtil.getString("status.refuse"));
		    	   }
		    	   if("20".equals(docStatus )){
		    		   request.setAttribute("statusText", ResourceUtil.getString("status.examine"));
		    	   }
		    	   if("30".equals(docStatus) && !isBegin){
		    		   request.setAttribute("statusText", ResourceUtil.getString("km-imeeting:kmImeeting.status.publish.unHold"));
		    	   }
		    	   if("30".equals(docStatus) && isBegin && !isEnd){
		    		   request.setAttribute("statusText", ResourceUtil.getString("km-imeeting:kmImeeting.status.publish.holding"));
		    	   }
		    	   if("30".equals(docStatus) && isBegin && isEnd){
		    		   request.setAttribute("statusText", ResourceUtil.getString("km-imeeting:kmImeeting.status.publish.hold"));
		    	   }
		    	   if("41".equals(docStatus )){
		    		   request.setAttribute("statusText", ResourceUtil.getString("km-imeeting:kmImeeting.status.cancel"));
		    	   }
		       }
		       
		       if(isBegin && isEnd){
		    	   request.setAttribute("statusNew", "hold");
		       }
		       if(isBegin && !isEnd){
		    	   request.setAttribute("statusNew", "holding");
		       }
		       if(!isBegin){
		    	   request.setAttribute("statusNew", "unHold");
		       }
		       
		       
		       request.setAttribute("isBegin", isBegin);
	    	   request.setAttribute("isEnd", isEnd);
			%>
			<%--状态 --%>
			<c:import url="/km/imeeting/mobile/import/status.jsp"  charEncoding="UTF-8">
				<c:param name="status" value="${kmImeetingMain.docStatus }"></c:param>
				<c:param name="isBegin" value="${isBegin }"></c:param>
				<c:param name="isEnd" value="${isEnd }"></c:param>
			</c:import>
      	</list:data-column>
		<%--链接--%>
		<list:data-column col="href" escape="false">
			/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId=${kmImeetingMain.fdId}
		</list:data-column>
		<%
		       if(pageContext.getAttribute("kmImeetingMain")!=null){
		    	   KmImeetingMain kmImeetingMain=(KmImeetingMain)pageContext.getAttribute("kmImeetingMain");
		    	   request.setAttribute("modelName",  ModelUtil.getModelClassName(kmImeetingMain));
		       }
		%>
		<list:data-column col="modelName" escape="false">
			<c:out value="${modelName}"/>
		</list:data-column>
		
		<list:data-column col="statusNew" escape="false">
			<c:out value="${statusNew}"/>
		</list:data-column>
		<list:data-column col="statusText" escape="false">
			<c:out value="${statusText}"/>
		</list:data-column>
		
		<%-- 文档状态 --%>
        <list:data-column col="docStatus" title="${lfn:message('km-imeeting:kmImeetingMain.docStatus')}">
			<c:choose>
				<c:when test="${kmImeetingMain.docStatus=='10' }">
					<bean:message key="status.draft"></bean:message>
				</c:when>
				<c:when test="${kmImeetingMain.docStatus=='20' }">
					<bean:message key="kmImeeting.status.append" bundle="km-imeeting"/>
				</c:when>
				<c:when test="${kmImeetingMain.docStatus=='11' }">
					<bean:message key="status.refuse"></bean:message>
				</c:when>
				<c:when test="${kmImeetingMain.docStatus=='30' || kmImeetingMain.docStatus=='40' }">
					<bean:message key="status.publish"></bean:message>
				</c:when>
				<c:when test="${kmImeetingMain.docStatus=='41' }">
					<bean:message bundle="km-imeeting" key="kmImeeting.status.cancel"/>
				</c:when>
				<c:when test="${kmImeetingMain.docStatus=='00' }">
					<bean:message key="status.discard"></bean:message>
				</c:when>
			</c:choose>
		</list:data-column>
		
		<list:data-column col="docStatusNew" title="${lfn:message('km-imeeting:kmImeetingMain.docStatus')}">
			<c:out value="${kmImeetingMain.docStatus}"/>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>