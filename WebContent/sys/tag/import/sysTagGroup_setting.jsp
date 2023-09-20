<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="java.util.List"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page language="java" import="com.landray.kmss.sys.tag.service.ISysTagGroupService"%>
<%@ page language="java" import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" import="java.util.Map"%>
<%
	String modelName = request.getParameter("modelName");
	List<Map<String , Object>> tagList = null;
	if(StringUtil.isNotNull(modelName)) {
		ISysTagGroupService service = (ISysTagGroupService)SpringBeanUtil.getBean("sysTagGroupService");
		tagList = service.getMixDataTagGroupByModelName(modelName);
	}
	pageContext.setAttribute("tagList", tagList);
	pageContext.setAttribute("length", tagList != null ? tagList.size() : 0);
%>
 <%--筛选器  --%>
		 <c:if test="${length > 0}">
			 <list:criteria>
				 <c:forEach var="tags" items="${tagList}"  varStatus="status">
				 	<list:cri-criterion title="${tags.categoryName}"
				 		key="tags${status.index}"  multi="true" expand="${status.index > 0 ? false : true }"> 
						<list:box-select >
							<list:item-select>
								<ui:source type="Static">
								[
								 <ui:trim>
									 <c:forEach items="${tags.tags}" var="tag" varStatus="vstatus">
									 	{"text" : "${lfn:escapeJs(tag)}",
									 	 "value" : "${lfn:escapeJs(tag)}"},
									 </c:forEach>
								 </ui:trim>
								]
								</ui:source> 
							</list:item-select>
						</list:box-select>
				 	 </list:cri-criterion>
				 </c:forEach>
			 </list:criteria>
		 </c:if>