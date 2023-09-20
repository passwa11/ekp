<!-- 渲染   图文类型  列表  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:forEach var="model" items="${queryPage.list}">
	<c:set var="_readStyle" value="style=''" /> 
	<c:if test="${model.read == true}">
		<c:set var="_readStyle" value="style='color:#999;'" /> 
	</c:if>
 	<div class="lui_notify_graphic">
		<div class="lui_notify_graphic_person">
			<c:set var="doc_creator_id" value="<%=new java.util.Date().getTime() %>"></c:set>
			<c:set var="personImgSrc" value="${KMSS_Parameter_ContextPath}sys/notify/resource/images/gear.png"></c:set>
			<c:if test="${not empty model.creatorHeadUrl}">
				<c:set var="doc_creator_id" value="${model.docCreator.fdId}"></c:set>
				<c:set var="personImgSrc" value="${model.creatorHeadUrl}"></c:set>
			</c:if>
			<img style="border-radius: 50%;border: 1px solid #e2e2e2;height: 40px;width: 40px;" src="${ personImgSrc}"/>
		 </div>
          <div id="lui_notify_title" style="padding-top: 2px;padding-left:50px;">
                <a title='<c:out value="${model.subject4View}"/>' target="_blank" class="lui_notify_graphic_todo_title textEllipsis"  ${_readStyle}
                	<c:if test="${_isGraphicDone==true && not empty model.fdLink}">
                		href="<c:url value="${model.fdLink}" />"
                	</c:if>
                	<c:if test="${_isGraphicDone!=true}">
                		onclick="onNotifyClick(this,'${model.fdType}')"
                		<c:if test="${not empty model.fdLink}"> 
							data-href="<c:url value="/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=view&fdId=${model.fdId}&r=${xxx }" />"
						</c:if>
                	</c:if>
					>
					<c:if test="${_isGraphicDone==true && not empty model.fdLink}">
						<span class="lui_notify_done_flag">${lfn:message('sys-notify:sysNotifyTodo.subject.done')}</span>
					</c:if>
					<c:if test="${fn:contains(model.fdExtendContent,'lbpmPress')}"> 
                 		<span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.process.press')}</span>
                 	</c:if>
					<c:if test="${model.fdType==3}"><span class="lui_notify_pending">${lfn:message('sys-notify:sysNotifyTodo.type.suspend')} </span></c:if>
               		<c:if test="${model.fdLevel==1}"><span class="lui_notify_level lui_notify_level1">${lfn:message('sys-notify:sysNotifyTodo.level.title.1')}</span></c:if>
				    <c:if test="${model.fdLevel==2}"><span class="lui_notify_level lui_notify_level2">${lfn:message('sys-notify:sysNotifyTodo.level.title.2')}</span></c:if>
				    <c:out value="${model.subject4View}"/> 
                 </a>
              
              <div class="lui_notify_graphic_todo_subhead" ${_readStyle}>
              	 <c:if test="${not empty model.docCreator.fdName}">
              		<span>${model.docCreator.fdName}</span>
              	 </c:if>
              	 <span> <kmss:showDate value="${model.fdCreateTime}" type="datetime"></kmss:showDate></span>
                <span class="lui_notify_modelname">${model.modelNameText }</span>
                
               	<c:forEach var="map" items="${model.extendContents }">
               		<c:if test="${map['key']=='lbpmCurrNode' }">
               			<span> ${map['titleMsgKey']}：${map['titleMsgValue'] }</span>
               		</c:if>
               		<c:if test="${map['key']=='docFinishedTime' }">
                			<span> ${lfn:message('sys-notify:sysNotifyTodo.kmPindagateMain.docEndTime')}：${map['titleMsgValue'] }</span>
                	</c:if>
               	</c:forEach>
              </div>
          </div>
       <div style="clear: both;"></div>
 	</div>
</c:forEach>