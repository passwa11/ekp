<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.lbpmext.authorize.model.LbpmAuthorize,com.landray.kmss.util.*,java.util.*" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmAuthorize" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdStartTime" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdStartTime') }"  escape="false">
			<kmss:showDate value="${lbpmAuthorize.fdStartTime}" type="datetime" />
		</list:data-column>
	
		<list:data-column col="fdEndTime" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdEndTime') }"  escape="false">
			<c:if test="${lbpmAuthorize.fdAuthorizeType == 0 || lbpmAuthorize.fdAuthorizeType == 2 || lbpmAuthorize.fdAuthorizeType == 3 || lbpmAuthorize.fdAuthorizeType == 4}">
				<kmss:showDate value="${lbpmAuthorize.fdEndTime}" type="datetime" />
			</c:if>
			<c:if test="${lbpmAuthorize.fdAuthorizeType == 1}">
				<c:out value="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdEndTime.infinitive') }"/>
			</c:if>
		</list:data-column>
		
		<list:data-column property="fdAuthorizer.fdName" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizer') }">
		</list:data-column>
		
		<list:data-column col="fdAuthorizedPerson.fdName" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizedPerson') }">
			<c:choose>
				<c:when test="${lbpmAuthorize.fdAuthorizeType == 1}">
				
					<c:if test="${lbpmAuthorize.fdAuthorizedReaders.size()==0}">
						<c:out value="${requestScope['fdAuthorizedPersons'][lbpmAuthorize.fdId]}" />
					</c:if>
					<c:if test="${lbpmAuthorize.fdAuthorizedReaders.size()>0}">
						<kmss:joinListProperty properties="fdName" value="${lbpmAuthorize.fdAuthorizedReaders}" />
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${lbpmAuthorize.fdAuthorizedPerson == null  }">
						<c:out value="${requestScope['fdAuthorizedPersons'][lbpmAuthorize.fdId]}" />
					</c:if>
					<c:if test="${lbpmAuthorize.fdAuthorizedPerson != null}">
						<c:out value="${lbpmAuthorize.fdAuthorizedPerson.fdName}" />
					</c:if>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		
			<list:data-column col="fdAuthorizeStatus" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizeStatus') }" escape="false">
				<%
						LbpmAuthorize authoirze = (LbpmAuthorize) pageContext.getAttribute("lbpmAuthorize");
						if("1".equals(authoirze.getFdStoppedFlag())){//提前终止
					%>
						${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizeStatus.stop') }
					<%
						}else if(authoirze.getFdStartTime().getTime()<new Date().getTime() && authoirze.getFdEndTime().getTime()>new Date().getTime()){//有效
					%>
					${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizeStatus.valid') }
					<%	
						}else if(authoirze.getFdStartTime().getTime()>new Date().getTime()){//未开始
					%>
					${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizeStatus.nostart') }
					<%
						}else if(authoirze.getFdEndTime().getTime()<new Date().getTime()){//过期
					%>
					${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizeStatus.overdue') }
					<%}%>
		</list:data-column>

		<list:data-column col="fdAuthorizeType" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizeType') }" escape="false">
			<sunbor:enumsShow  enumsType="lbpmAuthorize_authorizeType" value="${lbpmAuthorize.fdAuthorizeType}"/>
		</list:data-column>
		
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=edit&fdId=${lbpmAuthorize.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${lbpmAuthorize.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=delete&fdId=${lbpmAuthorize.fdId}" requestMethod="GET">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${lbpmAuthorize.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					<c:if test="${lbpmAuthorize.fdAuthorizeType ne '1' && lbpmAuthorize.fdAuthorizeType ne '4' && lbpmAuthorize.fdEndTime.time > _now}">
					<kmss:auth requestURL="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=stop&fdId=${lbpmAuthorize.fdId}" requestMethod="GET">
						<!-- 终止 -->
						<a class="btn_txt" href="javascript:stop('${lbpmAuthorize.fdId}')">${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.operations.stop')}</a>
					</kmss:auth>
					</c:if>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>