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
<%@page import="com.landray.kmss.util.StringUtil"%>

<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdNotesContent"
			title="${lfn:message('kms-common:kmsCourseNotes.docSubject')}">

		</list:data-column>
		<list:data-column col="fdCourse"
			title="${lfn:message('kms-common:kmsCourseNotes.sourse')}" escape="false">
			<%
				String[] fields = { "fdName", "fdSubject","docSubject" };
				KmsCourseNotes basedocObj = (KmsCourseNotes)pageContext.getAttribute("item");
				String fdModelName = basedocObj.getFdModelName();
				String fdModelId = basedocObj.getFdModelId();
				SysDictModel docModel = SysDataDict.getInstance().getModel(fdModelName);
				if(docModel!=null){
					IBaseService docService = (IBaseService) SpringBeanUtil.getBean(docModel.getServiceBean());
					
					IBaseModel baseModel = docService.findByPrimaryKey(fdModelId,null,true);
					if(baseModel!=null){
						Map<String, SysDictCommonProperty> propertyMap = docModel.getPropertyMap();
						for (String field : fields) {
							if (propertyMap.get(field) != null) {
								String fdCourse=(String) PropertyUtils.getSimpleProperty(baseModel, field);

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
			<font style="color:red;">${lfn:message('kms-common:kmsCourseNotes.deleteSourse')}</font>
			</c:when>
			<c:otherwise>
			 <c:out value="	${fdCourse}"></c:out>
			</c:otherwise>
		</c:choose>
		</list:data-column>
		<list:data-column col="fdType"
			title="${lfn:message('kms-common:kmsCourseNotes.isType')}">
			<%
				KmsCourseNotes basedocObj = (KmsCourseNotes)pageContext.getAttribute("item");
				Boolean isShare = basedocObj.getIsShare();
				String fdType = new String();
				if(isShare){
					fdType = ResourceUtil.getString("kms-common:kmsCommon.share_Notes");
				}else{
					fdType = ResourceUtil.getString("kms-common:kmsCommon.private_Notes");
				}
				request.setAttribute("fdType",fdType);

			%>
			${fdType}
		</list:data-column>
		
		<list:data-column property="isShare">
		</list:data-column>
		
		<list:data-column col="docCreateTime"
			title="${lfn:message('kms-common:kmsCourseNotes.docCreateTime') }">
			 <kmss:showDate value="${item.docCreateTime}" type="date" />
		</list:data-column>
		<list:data-column col="docCreateTime2"
			title="${lfn:message('kms-common:kmsCourseNotes.docCreateTime') }">
			 <kmss:showDate value="${item.docCreateTime}" type="datetime" />
		</list:data-column>
		<list:data-column col="docEvalCount"
			title="${lfn:message('kms-common:kmsCourseNotes.evalCount') }" escape="false">
			<span class="com_number" title="${not empty item.docEvalCount ? item.docEvalCount : 0}">${not empty item.docEvalCount ? item.docEvalCount : 0}</span>
		</list:data-column>
		<list:data-column col="docPraiseCount"
			title="${lfn:message('kms-common:kmsCourseNotes.praiseCount') }" escape="false">
			<span class="com_number" title="${item.docPraiseCount}">${item.docPraiseCount}</span>
		</list:data-column>
		<list:data-column col="evalCount"
			title="${lfn:message('kms-common:kmsCourseNotes.evalCount') }" escape="false">
			${not empty item.docEvalCount ? item.docEvalCount : 0}
		</list:data-column>
		<list:data-column col="praiseCount"
			title="${lfn:message('kms-common:kmsCourseNotes.praiseCount') }" escape="false">
			${item.docPraiseCount}
		</list:data-column>
		<list:data-column property="docCreator.fdName" title="${lfn:message('kms-common:kmsCourseNotes.fdRecorder')}">
		</list:data-column>
		<list:data-column property="docCreator.fdId">
		</list:data-column>
		
	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>



