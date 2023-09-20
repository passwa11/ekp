<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.landray.kmss.sys.attachment.jg.JGWebRevision"%>
<%
	JGWebRevision iWebServer = new JGWebRevision();
	String lbpmNote_Id = request.getParameter("auditNoteFdId")==null?"":request.getParameter("auditNoteFdId");
	String lbpmHandler_Id = request.getParameter("curHanderId")==null?"":request.getParameter("curHanderId");
	boolean revisionFlag = iWebServer.webRevisionIsExits(lbpmNote_Id, lbpmHandler_Id);
	if(revisionFlag){
%>					
<c:import url="/sys/lbpmservice/signature/handSignture/iWebRevision_view.jsp" charEncoding="UTF-8">
	<c:param name="recordID" value="${param.auditNoteFdId}"/>
	<c:param name="fieldName" value="LBPMProcess_${param.auditNoteFdId}"/>
	<c:param name="userName" value="${param.curHanderId}"/>
</c:import>
<script type="text/javascript">
	Com_AddEventListener(window,'load',function(){
		var revisionObjList = document.getElementsByName("iWebRevisionObject");
		try{
			for(var i=0;i<revisionObjList.length;i++){
				// 不可编辑
				revisionObjList[i].Enabled = '0';
				// 隐藏不必要的菜单
				revisionObjList[i].InvisibleMenus("-2,-3,-4,-5,-6,-7,");
				revisionObjList[i].LoadSignature();
			}				
		}catch(e){
			
		}
	});
</script>
<% }%>
