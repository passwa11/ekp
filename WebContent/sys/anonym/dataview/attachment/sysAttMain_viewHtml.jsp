<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="java.util.List,com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="java.util.regex.Matcher"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%
	pageContext.setAttribute("_isJGEnabled", new Boolean(JgWebOffice.isJGEnabled()));
%>
<%
	//以下代码用于附件不通过form读取的方式
	List sysAttMains = (List)pageContext.getAttribute("sysAttMains");
	boolean isWordFileFlag = false;
	if(sysAttMains==null || sysAttMains.isEmpty()){
		try{
			String _modelName = request.getParameter("fdModelName");
			String _modelId = request.getParameter("fdModelId");
			String _key = request.getParameter("fdKey");
			if(StringUtil.isNotNull(_modelName) 
					&& StringUtil.isNotNull(_modelId) 
					&& StringUtil.isNotNull(_key)){
				ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
				sysAttMains = sysAttMainService.findByModelKey(_modelName,_modelId,_key);
				pageContext.setAttribute("anonymModelId", _modelId);
				pageContext.setAttribute("anonymModelName", _modelName);
				pageContext.setAttribute("anonymKey", _key);
			}
			if(sysAttMains!=null && !sysAttMains.isEmpty()){
				for (int i = 0; i < sysAttMains.size(); i++) {
					SysAttMain sysAttMain = (SysAttMain)sysAttMains.get(i);
					// 判断文件类型
					if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document"
								.equals(sysAttMain.getFdContentType())
										|| 
								"application/msword".equals(sysAttMain.getFdContentType())) {
						
						isWordFileFlag = true;
						pageContext.setAttribute("_isWordFile", true);
						
					}
					
				}
				pageContext.setAttribute("sysAttMains",sysAttMains);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
%>
<c:forEach items="${sysAttMains}" var="sysAttMain"	varStatus="vstatus">
	<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
	<c:if test="${empty _fileName}">
	  <c:set var="fdFileName" value="${sysAttMain.fdFileName}"/>
	</c:if>
	<c:set var="fdAttMainId" value="${sysAttMain.fdId}" scope="request"/>
</c:forEach>
   <%
   //取fdAttMainId的值判断附件是否已经转换
   if(JgWebOffice.isExistViewPath(request)){ 
  %>
	    <%
		   boolean isExpand = "true".equals(request.getParameter("isExpand"));
		   if(isExpand){
	    %>
			   <c:choose>
                  <c:when test="${empty param.showAllPage or param.showAllPage}">
					   <iframe width="100%" height="100%" src='<c:url value="/sys/anonym/sysAnonymData.do?method=view&fdId=${attachmentId}&viewer=htmlviewer&showAllPage=true"/>'
								frameborder="0" scrolling="no">
					   </iframe>
				  </c:when>
				  <c:otherwise>
					  <iframe width="100%" height="566px" src='<c:url value="/sys/anonym/sysAnonymData.do?method=view&fdId=${attachmentId}&viewer=htmlviewer&toolPosition=top&newOpen=true&inner=yes"/>'
								frameborder="0" scrolling="auto">
					   </iframe>
			      </c:otherwise>
				</c:choose>
		 <%}else{ %>
		       <c:choose>
                  <c:when test="${empty param.showAllPage or param.showAllPage}">
				     <ui:event event="show">
				  	  	document.getElementById('IFrame_Content').src = ("<c:url value="/sys/anonym/sysAnonymData.do?method=view&fdId=${attachmentId}&viewer=htmlviewer&showAllPage=true"/>");
				     </ui:event>
				     <iframe id="IFrame_Content" width="100%" height="100%"
						frameborder="0" scrolling="no"> 
				     </iframe>
			     </c:when>
				 <c:otherwise> 
				     <ui:event event="show">
				  	  document.getElementById('IFrame_Content').src = ("<c:url value="/sys/anonym/sysAnonymData.do?method=view&fdId=${attachmentId}&viewer=htmlviewer&toolPosition=top&newOpen=true&inner=yes"/>");
				     </ui:event>
				     <iframe id="IFrame_Content" width="100%" height="600px"
						frameborder="0" scrolling="auto"> 
				     </iframe>
			     </c:otherwise>
				</c:choose>
		<%} %>	
    <%} else if (JgWebOffice.isJGEnabled() && isWordFileFlag) {%> 
		<c:import url="/sys/anonym/dataview/attachment/sysAttMain_view_jg.jsp" charEncoding="UTF-8">
			<c:param name="fdKey" value="${param.fdKey}" />
			<c:param name="fdAttType" value="office" />
			<c:param name="load" value="false" />
			<c:param name="bindSubmit" value="false"/>													
			<c:param name="fdModelId" value="${anonymModelId}" />
			<c:param name="fdModelName" value="${anonymModelName}" />
			<c:param name="formBeanName" value="${param.formBeanName}" />
			<c:param name="buttonDiv" value="missiveButtonDiv" />
			<c:param  name="attHeight" value="600"/>
			<c:param name="isToImg" value="false"/>
		</c:import>
	<% } else { %>
		<%@ include file="/sys/anonym/dataview/attachment/sysAttMain_view.jsp"%>
	<% } %>
