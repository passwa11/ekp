<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page
	import="com.landray.kmss.sys.zone.model.SysZonePersonInfo"%>
<%@page import="com.sunbor.web.tag.Page"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.common.dao.IBaseDao"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%> 
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" 
	prefix="person"%>
<list:data>
	<list:data-columns var="json" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column property="fdName" title="${ lfn:message('sys-zone:sysZonePerson.fdSignature') }">
		</list:data-column>
		<list:data-column col="tags" escape="false">
				<%
					Object basedocObj = pageContext.getAttribute("json");
					if(basedocObj != null) {
						JSONObject json = (JSONObject)basedocObj;
						String personTags= json.get("personTags").toString();
						if(StringUtil.isNotNull(personTags)) {
							out.print(personTags.trim().replaceAll("\\s+", ";"));
						}
					}
				%>
		</list:data-column>	
		<list:data-column property="isLoad">
		</list:data-column>		
		<list:data-column property="fdAttentionNum" title="${ lfn:message('sys-zone:sysZonePerson.fdAttentionNum') }">
		</list:data-column>
		<list:data-column property="fdFansNum" title="${ lfn:message('sys-zone:sysZonePerson.fdFansNum') }">
		</list:data-column>
		<list:data-column col="imgUrl" escape="false"> 
			<person:headimageUrl personId="${json.fdId}" size="m" contextPath="true"/> 
		</list:data-column> 
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>