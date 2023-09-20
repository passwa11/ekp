<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImeetingEquipment" list="${list}" varIndex="status">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--序号--%>
		<list:data-column col="index">${status+1 }</list:data-column>
		<%--排序号--%>
		<list:data-column property="fdOrder" headerClass="width30" styleClass="width30" title="${ lfn:message('km-imeeting:kmImeetingEquipment.fdOrder') }">
		</list:data-column>
		<%--会议室名字--%>
		<list:data-column  property="fdName" headerClass="width140" styleClass="width140"  title="${ lfn:message('km-imeeting:kmImeetingEquipment.fdName') }" >
		</list:data-column>
		
	</list:data-columns>
	
</list:data>