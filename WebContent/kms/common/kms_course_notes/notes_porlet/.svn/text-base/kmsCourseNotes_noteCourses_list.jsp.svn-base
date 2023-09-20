<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page
	import="com.landray.kmss.kms.common.model.KmsCourseNotes"%>
<%@page
	import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.common.service.IBaseService"%>
<%@page import="com.landray.kmss.common.model.IBaseModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictCommonProperty"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="java.util.Map"%>
<%@page import="com.sunbor.web.tag.Page"%>

<%@page import="com.landray.kmss.util.StringUtil"%>
<%
	Page queryPage = (Page)request.getAttribute("queryPage");
	int pageno = queryPage.getPageno();
	int total = queryPage.getTotal();
	request.setAttribute("pageno",pageno);
	request.setAttribute("total",total);
%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
	
		<list:data-column col="fdId">
			<c:out value="${item[0]}"/>
		</list:data-column>
		
		<list:data-column col="fdModelName">
			<c:out value="${item[1]}"/>
		</list:data-column>
		
		<list:data-column col="noteNum">
			<c:out value="${item[3]}"/>
		</list:data-column>
		
		<list:data-column col="courseName"
			title="${lfn:message('kms-common:kmsCourseNotes.sourse')}" escape="false">
			<%
				String[] fields = { "fdName", "fdSubject","docSubject" };
				Object[] itemObject = (Object[])pageContext.getAttribute("item");
				String fdModelId = (String)itemObject[0];
				String fdModelName =  (String)itemObject[1];
				SysDictModel docModel = SysDataDict.getInstance().getModel(fdModelName);
				if(docModel!=null){
					IBaseService docService = (IBaseService) SpringBeanUtil.getBean(docModel.getServiceBean());
					
					IBaseModel baseModel = docService.findByPrimaryKey(fdModelId,null,true);
					if(baseModel!=null){
						Map<String, SysDictCommonProperty> propertyMap = docModel.getPropertyMap();
						for (String field : fields) {
							if (propertyMap.get(field) != null) {
								String fdCourse=(String) PropertyUtils.getSimpleProperty(baseModel, field);
								/*if(fdCourse.length()>15){
									fdCourse=fdCourse.substring(0,14);
									fdCourse+="……";
								}*/
								request.setAttribute("fdCourse",fdCourse);
							}
						} 
					}else{
						request.setAttribute("fdCourse","");
					}
				}


			%>
			<c:choose>
				<c:when test="${empty fdCourse }">
					${lfn:message('kms-common:kmsCourseNotes.deleteSourse')}
				</c:when>
				<c:otherwise>
					${fdCourse}
				</c:otherwise>
			</c:choose>
		</list:data-column>
		
		<list:data-column col="isDelete">
			<c:choose>
				<c:when test="${empty fdCourse }">
					1
				</c:when>
				<c:otherwise>
					0
				</c:otherwise>
			</c:choose>
		</list:data-column>
		
		<list:data-column col="docCreateTime" title="${lfn:message('kms-common:kmsCourseNotes.docCreateTime') }">
			 <kmss:showDate value="${item[2]}" type="date" />
		</list:data-column>
		
	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>
