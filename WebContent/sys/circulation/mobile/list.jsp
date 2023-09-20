<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ include file="/resource/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }" mobile="true">
		<list:data-column col="content" title="">
			<c:out value="${item.fdContent}" />
		</list:data-column>
		<list:data-column col="label" title="">
			<c:out value="${item.fdCirculator.fdName}" />
		</list:data-column>
		<list:data-column col="created" title="">
			<kmss:showDate value="${item.fdCirculationTime}" isInterval="true"></kmss:showDate>
		</list:data-column>
		<list:data-column col="icon" escape="false">
			<person:headimageUrl personId="${item.fdCirculator.fdId}" size="m" />
		</list:data-column>	
		<%--链接--%>
		<list:data-column col="href" escape="false">
		    /sys/circulation/sys_circulation_main/sysCirculationMain.do?method=view&fdId=${item.fdId}
		</list:data-column>	
		<list:data-column col="remark" title="">
			${item.fdRemark}
		</list:data-column>
		<list:data-column col="receivedCirCulatorNames" title="">
			<c:out value="${item.receivedCirCulatorNames}" />
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
