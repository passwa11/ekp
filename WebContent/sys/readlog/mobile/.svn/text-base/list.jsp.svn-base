<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="com.landray.kmss.util.DateUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="readLog" list="${queryPage.list }" varIndex="status" mobile="true">
		 <%-- 阅读者名称--%>
		<list:data-column col="fdName">
		     <c:out value="${readLog[1]}"/>
		</list:data-column>
		 <%-- 阅读者头像--%>
		<list:data-column col="fdIcon" escape="false">
			<person:headimageUrl personId="${readLog[0]}" size="m" />
		</list:data-column>
		<list:data-column col="fdCreateTime">
			<%
				Object data = ((Object[])pageContext.getAttribute("readLog"))[3];
				Date readTime = null;
				if(data instanceof Timestamp){
					readTime = new Date(((Timestamp)data).getTime());
				}else if(data instanceof String){
					readTime = DateUtil.convertStringToDate((String)data,null);
				}else{
					readTime = (Date)data;
				}
				pageContext.setAttribute("readTime",readTime);
			%>
			 <kmss:showDate value="${readTime}" type="datetime"/>
		</list:data-column>
		<list:data-column col="fdDeptName">
			${readLog[2]==null?"":readLog[2]}
		</list:data-column> 
		<%--状态--%>
		<list:data-column col="fdReadTypeValue">
			${readLog[4]}
		</list:data-column> 
		<list:data-column col="fdReadType">
				<c:if test="${readLog[4]==1}">					
					<bean:message key="sysReadLog.fdReadType.process" bundle="sys-readlog" />
				</c:if>
				<c:if test="${readLog[4]!=1}">
					<bean:message key="sysReadLog.fdReadType.publish" bundle="sys-readlog" />
				</c:if>
		</list:data-column>
		<list:data-column col="dataType">
			<c:out value="readLog"></c:out>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>