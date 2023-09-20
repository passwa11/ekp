<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.third.pda.service.spring.PdaModuleSelectDialog"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.notify.util.SysNotifyConfigUtil"%>
<%@page import="com.landray.kmss.util.*"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.third.pda.util.PdaFlagUtil"%>
<%@page import="com.landray.kmss.third.pda.service.IPdaDataShowService"%>
<%@page import="com.landray.kmss.third.pda.model.PdaRowsPerPageConfig"%>
<%
	PdaModuleSelectDialog pdaModuleSelectDialog = ((PdaModuleSelectDialog)SpringBeanUtil.getBean("pdaModuleSelectDialog"));
	String allowModules = pdaModuleSelectDialog.getAllowModules();
	Boolean allowAllModuleFlag = pdaModuleSelectDialog.isAllowAllModuleFlag();
	
	List<String> enabledModuleList = new ArrayList<String>();
	if (!allowAllModuleFlag) {
		if (StringUtil.isNotNull(allowModules)) {
			String[] enabledModuleArray = allowModules
					.split("\\s*[,;]\\s*");
			if (enabledModuleArray != null)
				enabledModuleList = Arrays.asList(enabledModuleArray);
		}
	}
	
  	String[] extendArr=(new PdaRowsPerPageConfig()).getFdExtendsUrl();
  	String prefix = PdaFlagUtil.getUrlPrefix(request);
  	//类型(doing,done)
  	String oprType = request.getParameter("oprType")==null ? "" : request.getParameter("oprType");
  	//todo,toview,done,tododone,toviewdone
  	String dataTpe = request.getParameter("dataType")==null ? "" : request.getParameter("dataType");
  	boolean isDone = false;
  	//判断待办是否已处理
  	if(oprType.indexOf("done")!=-1 || dataTpe.indexOf("done")!=-1){
  		isDone = true;
  	}
  	request.setAttribute("_oprType",oprType);
  	request.setAttribute("isShowAppHome",SysNotifyConfigUtil.getFdDisplayAppNameHome()==1);
%>
<list:data>
	<list:data-columns var="model" list="${queryPage.list}" varIndex="status" mobile="true">
	    <%-- 主键ID--%>	
	    <list:data-column property="fdId" />
	    <%-- 主题--%>	
		<list:data-column col="label" escape="false" title="${ lfn:message('sys-notify:sysNotifyTodo.subject4View') }">
			<c:if test="${model.fdType==3}"><span class="lui_notify_pending"></span></c:if>
			<c:if test="${isShowAppHome==true && not empty model.fdAppName}">
				${lfn:message('sys-notify:sysNotifyTodo.todo.appTitle.left')}
				<c:out value="${model.fdAppName}"/>
				${lfn:message('sys-notify:sysNotifyTodo.todo.appTitle.right')}
			</c:if>
			<c:if test="${fn:contains(model.fdExtendContent,'lbpmPress')}"> 
            	<span class="warn">[${lfn:message('sys-notify:sysNotifyTodo.process.press')}]</span>
            </c:if>
			<c:choose>
				<c:when test="${model.fdLevel==1 }">
		      			<span class="warn">[${lfn:message('sys-notify:sysNotifyTodo.level.title.1')}]</span>
				</c:when>
				<c:when test="${model.fdLevel==2 }">
		      			<span class="warn">[${lfn:message('sys-notify:sysNotifyTodo.level.title.2')}]</span>
				</c:when>
			</c:choose>
			<c:out value="${model.subject4View}"/>
		</list:data-column>
		 <%-- 创建者--%>
		<list:data-column col="creator" title="${ lfn:message('sys-notify:sysNotifyTodo.docCreator.fdName') }" >
		         <c:out value="${model.docCreator.fdName}"/>
		</list:data-column>
		 <%-- 创建者头像--%>
		<list:data-column col="icon" escape="false">
				<c:if test="${not empty model.docCreator}">
					<person:headimageUrl personId="${model.docCreator.fdId}" size="m" />
				</c:if>
			    <c:if test="${empty model.docCreator}">
					/sys/notify/resource/images/gear.png
				</c:if>
		</list:data-column>
		 <%-- 创建时间--%>
	 	<list:data-column col="created" title="${ lfn:message('sys-notify:sysNotifyTodo.fdCreateDate') }">
	        	<kmss:showDate value="${model.fdCreateTime}" type="datetime"></kmss:showDate>
      	</list:data-column>
		<%--链接--%>
		<list:data-column col="href" escape="false">
			<c:set var="_fdLink" value="${model.fdLink }" />
			<c:set var="_fdModelName" value="${model.fdModelName }" />
			<c:set var="_finishTime" value="${model.finishTime }" />
			<c:set var="url" value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId=${model.fdId}"/>
				<%	
					String url = (String)pageContext.getAttribute("url");
					String fdLink = (String)pageContext.getAttribute("_fdLink");
					String fdModelName = (String)pageContext.getAttribute("_fdModelName");
					java.util.Date finishTime = (java.util.Date)pageContext.getAttribute("_finishTime");

					boolean isDisplay=false;
					//异系统解锁匹配
					if(StringUtil.isNotNull(fdLink) && extendArr!=null){
						for(int i=0;i<extendArr.length;i++){
							if(StringUtil.isNotNull(extendArr[i]) && fdLink.toLowerCase().indexOf(extendArr[i].toLowerCase())>-1){
								isDisplay=true;
								break;
							}
						}
					}
					//模块前缀匹配
					if(!isDisplay && StringUtil.isNotNull(fdLink) && !enabledModuleList.isEmpty() && fdLink.startsWith("/")){
						Iterator<String> ite = enabledModuleList.iterator();
						while(ite.hasNext()){
							String urlPrefix = ite.next();
							if(allowAllModuleFlag || StringUtil.isNotNull(urlPrefix) && fdLink.toLowerCase().indexOf(urlPrefix.toLowerCase())>-1){
								isDisplay=true;
								break;
							}
						}
					}
					
					if(isDisplay==true){
						if(url.startsWith("/"))
							url = prefix + url.substring(1);
					}else{
						//加锁
						url = "";
					}
					pageContext.setAttribute("url",url);
				%>
		       <c:out value="${url}" escapeXml="false"/>
		</list:data-column>
      	<list:data-column col="modelNameText" title="${ lfn:message('sys-notify:sysNotifyTodo.moduleName')}" escape="false">
			<c:out value="${model.modelNameText}"/>
		</list:data-column>
      	<%-- 扩展属性 --%>
      	<c:forEach var="map" items="${model.extendContents}">
			<list:data-column col="${map['key'] }Value" title="${map['titleMsgKey']}"  escape="false">
				<c:out value="${map['titleMsgValue']}"/>
			</list:data-column>
		</c:forEach>
		<list:data-column col="fdType" title="" escape="false">
			<c:out value="${model.fdType}"/>
		</list:data-column>
		<list:data-column col="isDone" title="" escape="false">
			<c:set var="_finishTime" value="${model.finishTime }" />
			<c:set var="_isDone" value="false" />
			<%
			java.util.Date finishTime = (java.util.Date)pageContext.getAttribute("_finishTime");
			//已办
			if(isDone || finishTime!=null){
				pageContext.setAttribute("_isDone", "true");
			}
			%>
			${_isDone}
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>