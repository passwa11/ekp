<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysNotifyTodo" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdModelName">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column col="star" headerStyle="width:30px" escape="false">
			<c:choose>
				<c:when test="${sysNotifyTodo.star==true}">
					<img onclick="doSingleStar('0','${sysNotifyTodo.fdId}')" style="cursor:pointer" title="${ lfn:message('sys-notify:sysNotifyTodo.button.todo.star.unset') }" src="${LUI_ContextPath}/sys/notify/resource/images/flag_red.png">
				</c:when>
				<c:otherwise>     
					<img onclick="doSingleStar('1','${sysNotifyTodo.fdId}')" style="cursor:pointer" title="${ lfn:message('sys-notify:sysNotifyTodo.button.todo.star.set') }" src="${LUI_ContextPath}/sys/notify/resource/images/flag_grey.png">
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="read" escape="false">
			<c:choose>
				<c:when test="${sysNotifyTodo.read==true}">
					style='color:#999;'
				</c:when>
				<c:otherwise>     
					style='color:#000;'
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="todo.subject4View"    escape="false" title="${ lfn:message('sys-notify:sysNotifyTodo.fdSubject') }" style="text-align:left">
			
			<span style="padding-left:16px;vertical-align: middle;" class="lui_notify_content_${sysNotifyTodo.fdType}"></span>
			<c:if test="${not empty sysNotifyTodo.finishTime }">
				<span class="lui_notify_done_flag ">${lfn:message('sys-notify:sysNotifyTodo.subject.done')}</span>
			</c:if>
			
			<c:if test="${sysNotifyTodo.fdType==3 }">
				<span title="<c:out value='${sysNotifyTodo.fdSubject}' />">
		      			<span class="lui_notify_level " style="margin-bottom:2px;">[${lfn:message('sys-notify:sysNotifyTodo.type.suspend')}]</span>
		    	</span>
			</c:if>
			
			<c:if test="${fn:contains(sysNotifyTodo.fdExtendContent,'lbpmPress')}"> 
            	<span title="<c:out value='${sysNotifyTodo.fdSubject}' />">
            		<span class="lui_notify_level" style="margin-bottom:2px;">[${lfn:message('sys-notify:sysNotifyTodo.process.press')}]</span>
            	</span>
            </c:if>
			
			<c:choose>
				<c:when test="${sysNotifyTodo.fdLevel==1 }">
				
					<span class="subjecthead" title="<c:out value='${sysNotifyTodo.subject4View}' />">
		      			<span class="lui_notify_level">[${lfn:message('sys-notify:sysNotifyTodo.level.title.1')}]</span><c:out value="${sysNotifyTodo.subject4View}"/>
		    		</span>
		    	
				</c:when>
				<c:when test="${sysNotifyTodo.fdLevel==2}">
				
					<span class="subjecthead" title="<c:out value='${sysNotifyTodo.subject4View}' />">
		      			<span class="lui_notify_level">[${lfn:message('sys-notify:sysNotifyTodo.level.title.2')}]</span><c:out value="${sysNotifyTodo.subject4View}"/>
		    		</span>
		    		
				</c:when>
				<c:otherwise>
			
					<span class="subjecthead" title="<c:out value='${sysNotifyTodo.subject4View}' />">
		      			<c:out value="${sysNotifyTodo.subject4View}"/>
		    		</span>  
		    			
				</c:otherwise>
			</c:choose>
			
		</list:data-column>
		<list:data-column property="fdLevel" title="${ lfn:message('sys-notify:sysNotifyTodo.fdLevel') }" style="text-align:left">
		</list:data-column>
		<list:data-column headerStyle="width:60px" col="fdType" title="${ lfn:message('sys-notify:sysNotifyTodo.fdType.1') }">
		   <sunbor:enumsShow value="${sysNotifyTodo.fdType}" enumsType="sys_todo_cate" bundle="sys-notify"/>
		</list:data-column> 
		<list:data-column headerStyle="width:120px" property="fdType4View" title="${ lfn:message('sys-notify:sysNotifyTodo.fdType.1') }">
		</list:data-column> 
		
		<list:data-column col="_fdType" title="${ lfn:message('sys-notify:sysNotifyTodo.fdType.1') }">
		   ${sysNotifyTodo.fdType}
		</list:data-column> 
		
		<list:data-column col="docCreatorNameTitle">
		   ${ lfn:langMessage('sys-notify:sysNotifyTodo.docCreatorName',sysNotifyTodo.fdLang4View) }
		</list:data-column> 
		
		<list:data-column col="docCreateTimeTitle">
		   ${ lfn:langMessage('sys-notify:sysNotifyTodo.docCreateTime.1',sysNotifyTodo.fdLang4View) }
		</list:data-column>
		
		<list:data-column col="moduleNameTitle">
		   ${ lfn:langMessage('sys-notify:sysNotifyTodo.moduleName',sysNotifyTodo.fdLang4View) }
		</list:data-column>
		
		
		
		<list:data-column headerStyle="width:60px" col="appName" title="${ lfn:message('sys-notify:sysNotifyTodo.fdAppName') }">
		    <c:set var="appName" value="${sysNotifyTodo.fdAppName}"/>
			<c:choose>
				<c:when test="${appName==null || appName=='' }">
					EKP
				</c:when>
				<c:otherwise>
					<c:out value="${appName}"/>
				</c:otherwise>
			</c:choose>
		</list:data-column> 
		<list:data-column headerStyle="width:120px" col="fdCreateTime" title="${ lfn:message('sys-notify:sysNotifyTodo.fdCreateDate') }">
		    <kmss:showDate value="${sysNotifyTodo.fdCreateTime}" type="datetime" />
		</list:data-column>
		<list:data-column headerStyle="width:120px" property="fdCreateTime4View" title="${ lfn:message('sys-notify:sysNotifyTodo.fdCreateDate') }">
		</list:data-column>
		<list:data-column headerStyle="width:120px" col="finishTime" title="${ lfn:message('sys-notify:sysNotifyTodo.finishDate') }">
		    <kmss:showDate value="${sysNotifyTodo.finishTime}" type="datetime" />
		</list:data-column>
		<list:data-column headerStyle="width:120px" property="finishTime4View" title="${ lfn:message('sys-notify:sysNotifyTodo.finishDate') }">
		</list:data-column>
		<list:data-column headerStyle="width:100px" col="docCreator.fdName" title="${ lfn:message('sys-notify:sysNotifyTodo.docCreatorName')}" style="width:10%"  escape="false">
			<c:out value="${sysNotifyTodo.docCreator.fdName}"/>
		</list:data-column>
		<list:data-column headerStyle="width:100px" col="docCreatorName" title="${ lfn:message('sys-notify:sysNotifyTodo.docCreatorName')}" style="width:10%"  escape="false">
			<c:out value="${sysNotifyTodo.docCreatorName}"/>
		</list:data-column>
		<list:data-column headerStyle="width:150px" col="modelNameText" title="${ lfn:message('sys-notify:sysNotifyTodo.moduleName')}" style="width:10%"  escape="false">
			<c:out value="${sysNotifyTodo.modelNameText}"/>
		</list:data-column>
		<list:data-column headerStyle="width:80px" col="tr_href" title="" escape="false">
			<c:set var="_fdLink" value="${sysNotifyTodo.fdLink }" />
			<c:set var="_contextPath" value="${KMSS_Parameter_ContextPath}" />
			<%
				String fdLink = (String)pageContext.getAttribute("_fdLink");
				//如：/ekp/或/
				String contextPath = (String)pageContext.getAttribute("_contextPath");
				if(fdLink!=null && fdLink.startsWith("/")){
					fdLink = contextPath+fdLink.substring(1);
				}
				pageContext.setAttribute("_tr_href",fdLink);
			%>
		    <c:out value="${_tr_href}" escapeXml="false"/>
		</list:data-column>
		<list:data-column headerStyle="width:80px" col="_tr_href" title="" escape="false">
		    <c:out value="${sysNotifyTodo.fdLink}" escapeXml="false"/>
		</list:data-column>
		<c:forEach var="map" items="${sysNotifyTodo.extendContents}">
			<list:data-column headerStyle="100px" col="${map['key'] }.value" title="${map['titleMsgKey']}" style="width:10%"  escape="false">
				<c:out value="${map['titleMsgValue']}"/>
			</list:data-column>
			<list:data-column headerStyle="100px" col="${map['key'] }.key" title="${map['titleMsgKey']}" style="width:10%"  escape="false">
				<c:out value="${map['titleMsgKey']}"/>
			</list:data-column>
		</c:forEach>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>


