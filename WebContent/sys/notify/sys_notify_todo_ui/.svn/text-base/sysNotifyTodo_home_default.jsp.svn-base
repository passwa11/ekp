<!-- 渲染   默认类型  列表  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:forEach var="model" items="${queryPage.list}">
	<div class="lui_dataview_classic_row clearfloat lui_notify_todo">
		<span class="lui_notify_title_icon lui_notify_content_<c:choose><c:when test="${param.finish == 1 }">${model.fdType}</c:when><c:otherwise>${model.fdType}</c:otherwise></c:choose>"></span>
			<div class="lui_dataview_classic_textArea clearfloat">
		<c:choose>
			<c:when test="${param.finish == 1}">
				<a title='<c:out value="${model.subject4View}"/>' class="lui_dataview_classic_link"  target="_blank"<c:if test="${not empty model.fdLink}"> href="<c:url value="${model.fdLink}"/>"</c:if>>
					<span class="lui_notify_done_flag">${lfn:message('sys-notify:sysNotifyTodo.subject.done')}</span>
					<c:if test="${showAppHome==1}">
						<c:set var="appName" value="${model.fdAppName}"/>
						<c:if test="${appName!=null && appName!='' }">
							<bean:message bundle="sys-notify" key="sysNotifyTodo.todo.appTitle.left" /><c:out value="${appName}"/><bean:message bundle="sys-notify" key="sysNotifyTodo.todo.appTitle.right" />
						</c:if>
					</c:if>
					<c:if test="${fn:contains(model.fdExtendContent,'lbpmPress')}">
						<span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.process.press')}</span>
					</c:if>
					<c:if test="${model.fdType==3}"><span class="lui_notify_pending">${lfn:message('sys-notify:sysNotifyTodo.type.suspend')} </span></c:if>
					<c:if test="${model.fdLevel==1}"><span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.level.title.1')}</span></c:if>
					<c:if test="${model.fdLevel==2}"><span class="lui_notify_level lui_notify_level2">${lfn:message('sys-notify:sysNotifyTodo.level.title.2')}</span></c:if>
					<c:out value="${model.subject4View}"/>

				</a>
			</c:when>
			<c:otherwise>
				<c:set var="_readStyle" value="style=''" /> 
				<c:if test="${model.read == true}">
					<c:set var="_readStyle" value="style='color:#999;'" /> 
				</c:if>
				<c:choose>
					<c:when test="${isHeader == 1}">
				 		<a title='<c:out value="${model.subject4View}"/>' class="lui_dataview_classic_link lui_dataview_classic_header" ${_readStyle} target="_blank" onclick="onNotifyClick(this,'${model.fdType}')"
							<c:if test="${not empty model.fdLink}"> data-href="<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId=${model.fdId}&r=${xxx }" />"</c:if>>
		                 	<c:if test="${fn:contains(model.fdExtendContent,'lbpmPress')}"> 
		                 		<span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.process.press')}</span>
		                 	</c:if>
							<c:if test="${model.fdType==3}"><span class="lui_notify_pending">${lfn:message('sys-notify:sysNotifyTodo.type.suspend') }</span></c:if>
							<c:if test="${model.fdLevel==1}"><span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.level.title.1')}</span></c:if>
							<c:if test="${model.fdLevel==2}"><span class="lui_notify_level lui_notify_level2">${lfn:message('sys-notify:sysNotifyTodo.level.title.2')}</span></c:if>
							<c:if test="${showAppHome==1}">
								<c:set var="appName" value="${model.fdAppName}"/>
								<c:if test="${appName!=null && appName!='' }">
									<bean:message bundle="sys-notify" key="sysNotifyTodo.todo.appTitle.left" /><c:out value="${appName}"/><bean:message bundle="sys-notify" key="sysNotifyTodo.todo.appTitle.right" />
								</c:if>
							</c:if>
							<c:out value="${model.subject4View}"/> 
		
					 	</a>
					</c:when>
					<c:otherwise>
						<a title='<c:out value="${model.subject4View}"/>' class="lui_dataview_classic_link " ${_readStyle} target="_blank" onclick="onNotifyClick(this,'${model.fdType}')"
							<c:if test="${not empty model.fdLink}"> data-href="<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId=${model.fdId}&r=${xxx }" />"</c:if>>
		                 	<c:if test="${fn:contains(model.fdExtendContent,'lbpmPress')}"> 
		                 		<span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.process.press')}</span>
		                 	</c:if>
							<c:if test="${model.fdType==3}"><span class="lui_notify_pending">${lfn:message('sys-notify:sysNotifyTodo.type.suspend') }</span></c:if>
							<c:if test="${model.fdLevel==1}"><span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.level.title.1')}</span></c:if>
							<c:if test="${model.fdLevel==2}"><span class="lui_notify_level lui_notify_level2">${lfn:message('sys-notify:sysNotifyTodo.level.title.2')}</span></c:if>
							<c:if test="${showAppHome==1}">
								<c:set var="appName" value="${model.fdAppName}"/>
								<c:if test="${appName!=null && appName!='' }">
									<bean:message bundle="sys-notify" key="sysNotifyTodo.todo.appTitle.left" /><c:out value="${appName}"/><bean:message bundle="sys-notify" key="sysNotifyTodo.todo.appTitle.right" />
								</c:if>
							</c:if>
							<c:out value="${model.subject4View}"/> 
		
					 	</a>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		</div>
	</div>
</c:forEach>