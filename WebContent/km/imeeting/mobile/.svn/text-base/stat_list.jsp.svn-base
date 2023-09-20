<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="model" list="${queryPage.list}" varIndex="status" mobile="true">
		<list:data-column property="fdId"></list:data-column>
		 <%-- 主题--%>	
		<list:data-column col="label" escape="false" title="${ lfn:message('km-imeeting:kmImeetingStat.fdName') }">
			<c:out value="${model.fdName}"/>
		</list:data-column>
		 <%-- 创建者--%>
		<list:data-column col="host" title="${ lfn:message('km-imeeting:kmImeetingStat.docCreator') }" >
		         <c:out value="${model.docCreator.fdName}"/>
		</list:data-column>
		 <%-- 创建时间--%>
	 	<list:data-column col="created" title="${ lfn:message('km-imeeting:kmImeetingStat.docCreateTime') }">
	        <kmss:showDate value="${model.docCreateTime}" type="date"></kmss:showDate>
      	</list:data-column>
      	<%-- 统计分类 --%>
      	<list:data-column col="type" title="${ lfn:message('km-imeeting:kmImeetingStat.fdType') }" escape="false">
      		<c:if test="${model.fdType == 'dept.stat' }">
      			<bean:message bundle="km-imeeting"  key="kmImeetingStat.dept.stat"/>
      		</c:if>
      		<c:if test="${model.fdType == 'dept.statMon' }">
      			<bean:message bundle="km-imeeting"  key="kmImeetingStat.dept.statMon"/>
      		</c:if>
      		<c:if test="${model.fdType == 'person.stat' }">
      			<bean:message bundle="km-imeeting"  key="kmImeetingStat.person.stat"/>
      		</c:if>
      		<c:if test="${model.fdType == 'person.statMon' }">
      			<bean:message bundle="km-imeeting"  key="kmImeetingStat.person.statMon"/>
      		</c:if>
      		<c:if test="${model.fdType == 'resource.stat' }">
      			<bean:message bundle="km-imeeting"  key="kmImeetingStat.resource.stat"/>
      		</c:if>
      		<c:if test="${model.fdType == 'resource.statMon' }">
      			<bean:message bundle="km-imeeting"  key="kmImeetingStat.resource.statMon"/>
      		</c:if>
      	</list:data-column>
      	<list:data-column col="href" escape="false">
	       /km/imeeting/km_imeeting_stat/kmImeetingStat.do?method=view&fdId=${model.fdId}
      	</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>

