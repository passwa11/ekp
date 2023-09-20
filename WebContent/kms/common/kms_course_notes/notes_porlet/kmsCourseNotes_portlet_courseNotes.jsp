<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 分开页面原因为防止channel干扰 -->

<c:choose>
	 <c:when  test="${param.mode eq 'hotNotes'}">
        <c:if test="${param.displayType eq '1'}">
        	<c:import url="/kms/common/kms_course_notes/notes_porlet/kmsCourseNotes_portlet_hotNotes_slide.jsp" charEncoding="UTF-8">
        		
        	</c:import>
        </c:if>
        <c:if test="${param.displayType eq '0'}">
        	 <c:import url="/kms/common/kms_course_notes/notes_porlet/kmsCourseNotes_portlet_hotNotes_tile.jsp" charEncoding="UTF-8">
        		
        	</c:import>
        </c:if>
	</c:when>
	<c:otherwise>
		<c:if test="${param.displayType eq '1'}">
        	<c:import url="/kms/common/kms_course_notes/notes_porlet/kmsCourseNotes_portlet_myNotes_slide.jsp" charEncoding="UTF-8">
        		
        	</c:import>
        </c:if>
        <c:if test="${param.displayType eq '0'}">
        	 <c:import url="/kms/common/kms_course_notes/notes_porlet/kmsCourseNotes_portlet_myNotes_tile.jsp" charEncoding="UTF-8">
        		
        	</c:import>
        </c:if>
	
	</c:otherwise>
</c:choose>
