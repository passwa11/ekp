<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Timestamp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/list.tld" prefix="list" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list}" varIndex="index">
		<list:data-column style="width:35px;" col="index" title="${ lfn:message('page.serial') }">
			${index+1}
		</list:data-column>
		<list:data-column headerClass="width80" col="fdReader.fdName" title="${ lfn:message('sys-readlog:sysReadLog.fdReaderId') }">
			${item[0]}
		</list:data-column>
		<list:data-column headerClass="width120"  col="fdReadTime" title="${ lfn:message('sys-readlog:sysReadLog.fdReadTime') }">
			<%
				Object data = ((Object[])pageContext.getAttribute("item"))[2];
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
		<list:data-column headerClass="width160" col="fdReader.fdParent.fdName" title="${ lfn:message('sys-organization:sysOrgElement.dept') }">
			${item[1]==null?"":item[1]}
		</list:data-column> 
		<list:data-column headerClass="width80" col="readType" title="${ lfn:message('sys-readlog:sysReadLog.fdReadType') }">
				<c:if test="${item[3]==1}">					
					<bean:message key="sysReadLog.fdReadType.process" bundle="sys-readlog" />
				</c:if>
				<c:if test="${item[3]!=1}">
					<bean:message key="sysReadLog.fdReadType.publish" bundle="sys-readlog" />
				</c:if>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
	
</list:data>