<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmProcess" list="${queryPage.list }" varIndex="status">
	    <list:data-column property="fdId">
		</list:data-column >
		<list:data-column col="url" escape="false">
		    <c:if test="${not empty urltMap[lbpmProcess.fdId]}">
	             ${urltMap[lbpmProcess.fdId]}
	        </c:if>
		</list:data-column >
		<list:data-column col="index">
		     ${status+1}
		</list:data-column >
	    <!--标题-->
	    <list:data-column col="subject" title="${ lfn:message('sys-lbpmperson:lbpmperson.person.docSubject') }" escape="false" style="text-align:left">
		     <c:if test="${not empty subjectMap[lbpmProcess.fdId]}">
		          <span class="com_subject">${subjectMap[lbpmProcess.fdId]}</span>
	        </c:if>
		</list:data-column>
		<!--所属模块-->
		<c:if test="${showModule==true}">
		    <list:data-column headerStyle="width:10%" col="subject" title="${ lfn:message('sys-lbpmperson:lbpmperson.person.module') }" escape="false">
		         ${moduleMap[lbpmProcess.fdModelName]}
			</list:data-column>
		</c:if>
	   
		<!--创建时间-->
		<list:data-column col="fdCreateTime" headerClass="width100" styleClass="width100" title="${ lfn:message('sys-lbpmperson:lbpmperson.person.creatorTime') }">
				<kmss:showDate value="${lbpmProcess.fdCreateTime}" type="date"/>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>