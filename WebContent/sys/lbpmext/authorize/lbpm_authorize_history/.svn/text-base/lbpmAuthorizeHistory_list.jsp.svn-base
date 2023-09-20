<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="history" list="${queryPage.list}" varIndex="status">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--序号--%>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column property="fdCreateTime" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.authorizationTitle') }" style="text-align:center;width:180px" />
		
		<list:data-column col="fdAuthorizeType" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizeType') }" style="text-align:center;width:100px" >
			<c:if test="${history.fdAuthorizeType=='handler'}">
				${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorize.process') }
			</c:if>
			<c:if test="${history.fdAuthorizeType=='proxy'}">
				${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorize.proxy') }
			</c:if>
			<c:if test="${history.fdAuthorizeType=='reader'}">
				${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorize.reading') }
			</c:if>
		</list:data-column>
		<!--授权项-->
		<list:data-column col="fdExpecter" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorizeItem.fdAuthorizeOrgId') }" style="text-align:center;width:180px" >
			<c:out value="${history.fdExpecter.fdName}" />
		</list:data-column>
		<!--授权人-->
		<list:data-column col="fdAuthorizer" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizer') }" style="text-align:center;width:100px" >
			<c:out value="${history.fdAuthorizer.fdName}" />
		</list:data-column>
		<!--被授权人-->
		<list:data-column col="fdAuthorizedPerson" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizedPerson') }" style="text-align:center;width:100px" >
			<c:choose>
				<c:when test="${history.fdAuthorizeType eq 'reader'}">
					<kmss:joinListProperty properties="fdName" value="${history.fdAuthorizedReaders}" />
				</c:when>
				<c:otherwise>
					<c:out value="${history.fdAuthorizedPerson.fdName}" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
		 <!--标题-->
	    <list:data-column col="subject" title="${ lfn:message('sys-lbpmservice:lbpmservice.taglib.docSubject') }" escape="false" style="text-align:center">
		     <c:if test="${not empty subjectMap[history.fdProcessId]}">
			          	<a class="com_subject textEllipsis" title="${subjectMap[history.fdProcessId]}" href="${LUI_ContextPath}${urltMap[history.fdProcessId]}" target="_blank">
			       			 ${subjectMap[history.fdProcessId]}
			       	    </a>
	        </c:if>
		</list:data-column>
		<c:if test="${history.fdAuthorizeType =='proxy'}">
			<list:data-column col="operations" title="${ lfn:message('list.operation') }" style="text-align:center;width:100px" escape="false">
				<c:if test="${canRecoverMap[history.fdTaskId] == 'true'}">
				<div class="conf_show_more_w">
				<div class="conf_btn_edit">
						<a class="btn_txt" href="javascript:recoverProxy('${history.fdId}')">
							<bean:message key="lbpmAuthorize.history.proxy.recover" bundle="sys-lbpmext-authorize"/>
						</a>
					</div>
					</div>
				 </c:if>
			</list:data-column>
		</c:if>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>