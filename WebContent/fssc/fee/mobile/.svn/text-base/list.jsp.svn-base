<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="fsscFeeMain" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
		<%--状态--%>
		<list:data-column col="status" title="${ lfn:message('fssc-fee:fsscFeeMain.docStatus') }" escape="false">
			<sunbor:enumsShow enumsType="common_status" value="${fsscFeeMain.docStatus }"/>
		</list:data-column>
		<list:data-column col="statusIdx">
            <c:out value="${fsscFeeMain.docStatus}" />
        </list:data-column>
	    <%-- 主题--%>	
		<list:data-column col="label" title="${ lfn:message('fssc-fee:fsscFeeMain.docSubject') }" escape="false" property="docSubject" >
		         <c:out value="${fsscFeeMain.docSubject}"/>
		</list:data-column>
		 <%-- 创建者--%>
		<list:data-column col="creator" title="${ lfn:message('fssc-fee:fsscFeeMain.docCreator') }" >
		         <c:out value="${fsscFeeMain.docCreator.fdName}"/>
		</list:data-column>
		 <%-- 创建者头像--%>
		<list:data-column col="icon" escape="false">
			    <person:headimageUrl personId="${fsscFeeMain.docCreator.fdId}" size="90" />
		</list:data-column>
		 <%-- 创建时间--%>
	 	<list:data-column col="created" title="${ lfn:message('fssc-fee:fsscFeeMain.docCreateTime') }">
	        <kmss:showDate value="${fsscFeeMain.docCreateTime}" type="date"></kmss:showDate>
      	</list:data-column>
		<%--链接--%>
		<list:data-column col="href" escape="false">
		        /fssc/fee/fssc_fee_main/fsscFeeMain.do?method=view&fdId=${fsscFeeMain.fdId}
		</list:data-column>
		 <%-- 创建时间--%>
	 	<list:data-column col="summary" title="${ lfn:message('fssc-fee:sysWfNode.processingNode.currentProcessor') }" escape="false">
	        <kmss:showWfPropertyValues idValue="${fsscFeeMain.fdId}" propertyName="handlerName" />
      	</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
