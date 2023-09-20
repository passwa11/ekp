<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/list.tld" prefix="list" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<list:data>
	<list:data-columns var="hrRatifyFeedback" list="${queryPage.list}" varIndex="index">
	    <list:data-column property="fdId">
		</list:data-column>
		<list:data-column style="width:35px;" col="fdSerial" title="${ lfn:message('page.serial') }">
			${index+1}
		</list:data-column>
		<list:data-column style="width:35px;" col="fdHasAttachment" escape="false">
			<c:if test="${hrRatifyFeedback.fdHasAttachment }">
				<img src="../img/fj.png" title="${ lfn:message('hr-staff:hrRatifyFeedback.hasAttachment') }">
			</c:if>
		</list:data-column>
		<list:data-column property="fdSummary" title="${ lfn:message('hr-ratify:hrRatifyFeedback.fdSummary') }">
		</list:data-column>
		<list:data-column style="width:100px;" property="docCreator.fdName" title="${ lfn:message('hr-ratify:hrRatifyFeedback.docCreator') }">
		</list:data-column>
		<list:data-column style="width:120px; white-space:nowrap;" property="docCreateTime" title="${ lfn:message('hr-ratify:hrRatifyFeedback.docCreateTime') }">
		</list:data-column>
		
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }"></list:data-paging>
	
</list:data>