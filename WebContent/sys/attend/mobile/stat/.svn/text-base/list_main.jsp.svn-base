<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.DateUtil,java.util.Date,com.landray.kmss.sys.attend.model.SysAttendMain,com.landray.kmss.util.AutoHashMap" %>
<%@ page import="com.landray.kmss.sys.attend.model.SysAttendMain, com.landray.kmss.sys.attachment.model.SysAttMain" %>
<%@ page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil,java.util.List,net.sf.json.JSONArray"%>
<%@ page import="java.util.Set"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="model" list="${queryPage}" varIndex="status" mobile="true">
		<%
			SysAttendMain main = (SysAttendMain)pageContext.getAttribute("model");
		%>
		<list:data-column property="fdId">
		</list:data-column >
		<list:data-column col="categoryId">
			${model.fdCategory.fdId}
		</list:data-column >
		<list:data-column col="fdCateName">
			${model.fdCategory.fdName}
		</list:data-column >
		<list:data-column col="fdSignedDate" escape="false" title="">
			<%
				pageContext.setAttribute("__fdSignedDate", DateUtil.convertDateToString(main.getDocCreateTime(),DateUtil.TYPE_DATE,null));
			%>
			${__fdSignedDate}
		</list:data-column>
      	<list:data-column col="fdSignTime" escape="false" title="">
			<%
				pageContext.setAttribute("__fdSignTime", DateUtil.convertDateToString(main.getDocCreateTime(),DateUtil.TYPE_TIME,null));
			%>
			${__fdSignTime}
		</list:data-column>
		<list:data-column col="fdType" escape="false" title="">
			${model.fdCategory.fdType}
		</list:data-column>
		<list:data-column col="fdStatus" escape="false" title="">
			${model.fdStatus}
		</list:data-column>
		<list:data-column col="fdStatusText" escape="false" title="">
			<sunbor:enumsShow value="${model.fdStatus}" enumsType="sysAttendMain_fdStatus" />
		</list:data-column>
		<list:data-column col="fdState" escape="false" title="">
			${model.fdState}
		</list:data-column>
		<list:data-column col="fdOutside" escape="false" title="">
			${model.fdOutside}
		</list:data-column>

		<list:data-column col="fdLocation" escape="false" title="">
			${model.fdLocation}
		</list:data-column>
		<list:data-column col="fdAddress" escape="false" title="">
			${model.fdAddress}
		</list:data-column>
		<list:data-column col="docCreatorName" title="">
			<c:if test="${model.fdOutPerson==null }">
				<c:out value="${model.docCreator.fdName}" />
			</c:if>
			<c:if test="${model.fdOutPerson!=null }">
				${model.fdOutPerson.fdName}(${model.fdOutPerson.fdPhoneNum})
			</c:if>
		</list:data-column>
		<list:data-column col="docCreatorImg" title="" escape="false">
			<person:headimageUrl contextPath="true" personId="${model.docCreator.fdId}" size="m" />
		</list:data-column>
		<list:data-column col="dept" title="">
			<c:out value="${model.docCreator.fdParent.fdName}" />
		</list:data-column>
		<list:data-column col="fdDesc" escape="false" title="">
			${model.fdDesc}
		</list:data-column>
		<list:data-column col="fdLng" escape="false" title="">
			${model.fdLng}
		</list:data-column>
		<list:data-column col="fdLat" escape="false" title="">
			${model.fdLat}
		</list:data-column>
		<list:data-column col="fdLatLng" escape="false" title="">
			${model.fdLatLng}
		</list:data-column>
		<list:data-column col="fdSignCount" title="">
			<c:out value="${fdSignCount}" />
		</list:data-column>
		<list:data-column col="fdAttrs" escape="false" title="">
			<%
			ISysAttMainCoreInnerService sysAttMainCoreInnerService=(ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
			List<SysAttMain> list=sysAttMainCoreInnerService.findByModelKey("com.landray.kmss.sys.attend.model.SysAttendMain",
					main.getFdId(),"attachment");
			JSONArray array = new JSONArray();
			for(SysAttMain sysAttMain : list) {
				String attHref = request.getContextPath()
						+ "/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId="
						+ sysAttMain.getFdId();
				array.add(attHref);
			}
			pageContext.setAttribute("__fdAttrs", array.toString());
			%>
			${__fdAttrs}
		</list:data-column>
		
		<list:data-column col="fdAppId" escape="false" title="">
			${model.fdCategory.fdAppId}
		</list:data-column>
		<list:data-column col="fdAppName" escape="false" title="">
			${model.fdCategory.fdAppName}
		</list:data-column>
		<list:data-column col="fdAppUrl" escape="false" title="">
			${model.fdCategory.fdAppUrl}
		</list:data-column>
		</list:data-columns>
		
</list:data>