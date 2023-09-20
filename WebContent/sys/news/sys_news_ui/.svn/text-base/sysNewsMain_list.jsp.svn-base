<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.news.model.SysNewsMain"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%
	ISysAttMainCoreInnerService sysAttMainCoreInnerService=(ISysAttMainCoreInnerService)
			SpringBeanUtil.getBean("sysAttMainService");
%>
<list:data>
	<list:data-columns var="sysNewsMain" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column >
		<list:data-column col="url" escape="false">
			"/sys/news/sys_news_main/sysNewsMain.do?method=view&fdId=${sysNewsMain.fdId}"
		</list:data-column >
		<list:data-column col="index">
		      ${status+1}
		</list:data-column >
	    <!-- 主题-->	
	    <list:data-column  col="docSubject" title="${ lfn:message('sys-news:sysNewsMain.docSubject') }" escape="false" style="text-align:left">
		           <c:if test="${sysNewsMain.fdIsTop==true}">
		           		 <i class="lui_article_status_top_border lui_article_status_top"><bean:message key="news.fdIsTop.true" bundle="sys-news"/></i>
		             </c:if>    
		            <span title="<c:out value="${sysNewsMain.docSubject}"/>">
		             <c:out value="${sysNewsMain.docSubject}"/>
		            </span>
		</list:data-column>
		
		  <!-- 主题--摘要视图-->	
		<list:data-column col="docSubject_row" title="${ lfn:message('sys-news:sysNewsMain.docSubject') }" escape="false" style="text-align:left">
		             <c:if test="${sysNewsMain.fdIsTop==true}">
		             	<i class="lui_article_status_top_border lui_article_status_top"><bean:message key="news.fdIsTop.true" bundle="sys-news"/></i>
		             </c:if>    
		            <span title="<c:out value="${sysNewsMain.docSubject}"/>">  
		            <c:out value="${sysNewsMain.docSubject}"/>
		            </span>
		</list:data-column>
		 <!-- 类型-->
		<list:data-column headerStyle="width:80px" property="fdTemplate.fdName"  title="${ lfn:message('sys-news:sysNewsMain.fdTemplate') }">
		</list:data-column>
	     <!-- 重要度-->
		<list:data-column headerStyle="width:100px" col="fdImportance"  title="${ lfn:message('sys-news:sysNewsMain.fdImportance') }">
		     <sunbor:enumsShow value="${sysNewsMain.fdImportance}" enumsType="sysNewsMain_fdImportance" />
		</list:data-column>
	     <!-- 文档重要度-->
		<list:data-column headerStyle="width:100px" col="fdImportance_doc"  title="${ lfn:message('sys-news:sysNewsMain.fdImportance') }">
		     <sunbor:enumsShow value="${sysNewsMain.fdImportance}" enumsType="sysNewsMain_fdImportance" />
		</list:data-column>
		 <!-- 创建者-->
		<list:data-column headerStyle="width:90px" col="docCreator.fdName" title="${ lfn:message('sys-news:sysNewsMain.docCreatorId') }" escape="false">
		        <ui:person personId="${sysNewsMain.docCreator.fdId}" personName="${sysNewsMain.docCreator.fdName}"></ui:person>
		</list:data-column>
		 <!-- 创建时间-->
		 <list:data-column headerStyle="width:100px" col="docCreateTime" title="${ lfn:message('sys-news:sysNewsMain.docCreateTime') }">
		        <kmss:showDate value="${sysNewsMain.docCreateTime}" type="date"></kmss:showDate>
	      </list:data-column>
	       <%if(ISysAuthConstant.IS_AREA_ENABLED) { %> 
		<!-- 所属场所-->
		<list:data-column headerStyle="width:80px" property="authArea.fdName" title="${ lfn:message('sys-authorization:sysAuthArea.authArea') }">
		</list:data-column>
		<% } %>
		  <!-- 发布时间-->   	
		<list:data-column headerStyle="width:100px"  col="docPublishTime" title="${ lfn:message('sys-news:sysNewsMain.docPublishTime') }">
		<c:if test="${not empty sysNewsMain.docPublishTime }">
		        <kmss:showDate value="${sysNewsMain.docPublishTime}" type="date"></kmss:showDate>
		  </c:if>
		</list:data-column>
			  <!-- 发布时间row-->   	
		<list:data-column headerStyle="width:80px"  col="docPublishTime_row" title="${ lfn:message('sys-news:sysNewsMain.docPublishTime') }">
		<c:if test="${not empty sysNewsMain.docPublishTime }">
		         ${ lfn:message('sys-news:sysNewsMain.docPublishTime') }：<kmss:showDate value="${sysNewsMain.docPublishTime}" type="date"></kmss:showDate>
		</c:if>
		</list:data-column>
		
			  <!--修改时间-->   	
		<list:data-column headerStyle="width:100px"  col="docAlterTime" title="${ lfn:message('sys-news:sysNewsMain.docAlterTime') }">
		<c:if test="${not empty sysNewsMain.docAlterTime }">
		       <kmss:showDate value="${sysNewsMain.docAlterTime}" type="date"></kmss:showDate>
		</c:if>
		</list:data-column>
			  <!--修改时间-->   	
		<list:data-column headerStyle="width:120px"  col="docAlterTime_time" title="${ lfn:message('sys-news:sysNewsMain.docAlterTime') }">
		<c:if test="${not empty sysNewsMain.docAlterTime }">
		       <kmss:showDate value="${sysNewsMain.docAlterTime}" type="datetime"></kmss:showDate>
		</c:if>
		</list:data-column>
	     <!-- 点击率-->	
		<list:data-column headerStyle="width:60px"  col="docReadCount" title="${ lfn:message('sys-news:sysNewsMain.docHits')}" escape="false">
		             <font class="com_number"> <c:out value="${sysNewsMain.docReadCount}"/></font>
		</list:data-column>
	       <!-- 是否置顶-->		
	    <list:data-column headerStyle="width:60px" col="fdTopDays" escape="false" title="${ lfn:message('sys-news:sysNewsMain.fdTopDays') }">       
		       <c:if test="${sysNewsMain.fdIsTop==true}">
		          <kmss:showDate value="${sysNewsMain.fdTopEndTime}" type="date"></kmss:showDate>
		       </c:if>
		</list:data-column>		
	
	<!-- 摘要-->
	<list:data-column headerStyle="width:80px"  col="fdDescription_row" title="${ lfn:message('sys-news:sysNewsMain.fdDescription')}" escape="false">
	                <c:out value="${sysNewsMain.fdDescription}"/>
	</list:data-column>
	<list:data-column headerStyle="width:80px"  col="fdDescription" title="${ lfn:message('sys-news:sysNewsMain.fdDescription')}" escape="false">
	         <c:choose>
				<c:when test="${fn:length(sysNewsMain.fdDescription)>30 }"><c:out value="${fn:substring(sysNewsMain.fdDescription,0,29)}..." /></c:when>
				<c:otherwise><c:out value="${sysNewsMain.fdDescription}"/></c:otherwise>
			</c:choose>
	                
	</list:data-column>
		
    <!-- 部门-->
	<list:data-column headerStyle="width:80px"  col="fdDepartment.fdName" escape="false" title="${ lfn:message('sys-news:sysNewsMain.publishUnit')}">
		<c:if test="${sysNewsMain.fdAuthor!=null}">
			<c:out value="${sysNewsMain.fdDepartment.fdName}"></c:out>
		</c:if>
	</list:data-column>
	<!-- 作者-->	
	<list:data-column headerStyle="width:80px" col="fdWriterName_row"  escape="false" title="${ lfn:message('sys-news:sysNewsMain.publisher')}">
	           <c:if test="${sysNewsMain.fdAuthor==null}">
	                 <c:out value="${sysNewsMain.fdWriter}"/>
	           </c:if>
	            <c:if test="${sysNewsMain.fdAuthor!=null}">
	                 <ui:person personId="${sysNewsMain.fdAuthor.fdId}" personName="${sysNewsMain.fdAuthor.fdName}"></ui:person>
	           </c:if>
	</list:data-column>
	
	
		
     <!-- 点击率row-->	
		<list:data-column headerStyle="width:50px"  col="docHits_row" title="${ lfn:message('sys-news:sysNewsMain.docHits')}" escape="false">
		        <c:if test="${not empty sysNewsMain.docReadCount}">
		                 ${lfn:message('sys-news:sysNewsMain.docHits') }：<font class="com_number"> <c:out value="${sysNewsMain.docReadCount}"/></font>
		         </c:if>
		</list:data-column>
	<!-- 标签-->	
	<list:data-column headerStyle="width:80px"  col="sysTagMain_row" title="${ lfn:message('sys-news:sysNewsMain.label')}" escape="false">
	            <c:if test="${not empty tagJson[sysNewsMain.fdId]}">
	              ${lfn:message('sys-news:sysNewsMain.label') }：${tagJson[sysNewsMain.fdId]}
	             </c:if>
	</list:data-column>

	<list:data-column headerStyle="width:65px" col="docStatus" title="${ lfn:message('sys-news:sysNewsMain.docStatus') }    ">
						<c:if test="${sysNewsMain.docStatus=='10'}">
							${ lfn:message('sys-news:status.draft') } 
						</c:if>
						<c:if test="${sysNewsMain.docStatus=='20'}">
							${ lfn:message('sys-news:status.examine')}
						</c:if>
						<c:if test="${sysNewsMain.docStatus=='11'}">
							${ lfn:message('sys-news:status.refuse') }
						</c:if>
						<c:if test="${sysNewsMain.docStatus=='00'}">
							${ lfn:message('sys-news:status.discard') }
						</c:if>
						<c:if test="${sysNewsMain.docStatus=='30'}">
							${ lfn:message('sys-news:status.publish') }
						</c:if>
						<c:if test="${sysNewsMain.docStatus=='40'}">
							${ lfn:message('sys-news:status.cancle') }
						</c:if>
	</list:data-column>
	
	<c:if test="${!empty param.ai }">
		<list:data-column col="sysTagMain_url" escape="false" >
			<c:if test="${sysNewsMain.fdIsPicNews == true}">
				     <%
				        SysNewsMain sysNews=(SysNewsMain)pageContext.getAttribute("sysNewsMain");
						List list=sysAttMainCoreInnerService.findByModelKey(ModelUtil.getModelClassName(sysNews),sysNews.getFdId(),"Attachment");
						if(list!=null && list.size()>0){
							SysAttMain att=(SysAttMain)list.get(0);
							out.print("/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId="+att.getFdId());
						}
					%>
			</c:if>
		</list:data-column>
	</c:if>

	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>