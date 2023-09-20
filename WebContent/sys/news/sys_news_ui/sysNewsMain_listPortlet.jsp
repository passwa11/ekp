<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysNewsMain" list="${queryPage.list }"  custom="false">
		<list:data-column property="fdId">
		</list:data-column >
	    <!-- 主题-->	
		<list:data-column headerClass="width100"  col="docSubject" title="${ lfn:message('sys-news:sysNewsMain.docSubject') }" escape="false" style="text-align:left;min-width:100px">
	          <a onclick="Com_OpenNewWindow(this)" class="com_subject textEllipsis" title="${sysNewsMain.docSubject}" 
	          data-href="${LUI_ContextPath}/sys/news/sys_news_main/sysNewsMain.do?method=view&fdId=${sysNewsMain.fdId}">
	            <c:out value="${sysNewsMain.docSubject}"/>
	          </a>  
		</list:data-column>
		 <!-- 类型-->
		<list:data-column headerClass="width80" styleClass="width80" property="fdTemplate.fdName"  title="${ lfn:message('sys-news:sysNewsMain.fdTemplate') }">
		</list:data-column>
		 <!-- 点击率-->
	    <list:data-column headerClass="width40" styleClass="width40"  col="docHits" title="${ lfn:message('sys-news:sysNewsMain.docHits')}" escape="false">
		             <font class="com_number"> <c:out value="${sysNewsMain.docReadCount}"/></font>
		</list:data-column>
		  <!-- 发布时间--> 
		<list:data-column headerClass="width80" styleClass="width80"  col="docPublishTime" title="${ lfn:message('sys-news:sysNewsMain.docPublishTime') }">
			    <kmss:showDate value="${sysNewsMain.docPublishTime}" type="date"></kmss:showDate>
		</list:data-column> 
  </list:data-columns>  
</list:data>