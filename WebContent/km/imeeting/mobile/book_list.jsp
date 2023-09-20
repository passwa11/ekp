<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.km.imeeting.model.KmImeetingBook"%>
<%
	ISysAttMainCoreInnerService sysAttMainCoreInnerService = 
		(ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
%>

<list:data>
	<list:data-columns var="kmImeetingBook" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId"></list:data-column>
		<list:data-column property="fdName"></list:data-column>
		<list:data-column property="fdHoldDate"></list:data-column>
		<list:data-column property="fdFinishDate"></list:data-column>
		<list:data-column property="fdRemark"></list:data-column>
		<list:data-column property="fdHasExam"></list:data-column>
		
		<list:data-column col="fdPlace" escape="false">
		    <c:out value="${kmImeetingBook.fdPlace.fdName}"/>
		</list:data-column>
		
		<list:data-column col="docCreator" escape="false">
		    <c:out value="${kmImeetingBook.docCreator.fdName}"/>
		</list:data-column>
		
		<list:data-column col="placeImg" escape="false">
			<%
				KmImeetingBook kmImeetingBook=(KmImeetingBook)pageContext.getAttribute("kmImeetingBook");
				List list=sysAttMainCoreInnerService.findByModelKey(ModelUtil.getModelClassName(kmImeetingBook.getFdPlace()),
						kmImeetingBook.getFdPlace().getFdId(),"Attachment");
				if(list!=null && list.size()>0){
					SysAttMain att=(SysAttMain)list.get(0);
					out.print("sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId="+att.getFdId());
				}
				request.setAttribute("modelName",  ModelUtil.getModelClassName(kmImeetingBook));
			%>
		</list:data-column>
      	<list:data-column col="modelName" escape="false">
			<c:out value="${modelName}"/>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>