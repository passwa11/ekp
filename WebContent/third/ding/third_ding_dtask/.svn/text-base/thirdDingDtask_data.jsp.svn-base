<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingDtask" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingDtask.fdName')}" />
        <list:data-column col="fdInstance.name" title="${lfn:message('third-ding:thirdDingDtask.fdInstance')}" escape="false">
            <c:out value="${thirdDingDtask.fdInstance.fdName}" />
        </list:data-column>
        <list:data-column col="fdInstance.id" escape="false">
            <c:out value="${thirdDingDtask.fdInstance.fdId}" />
        </list:data-column>
        <list:data-column property="fdDingUserId" title="${lfn:message('third-ding:thirdDingDtask.fdDingUserId')}" />
        <list:data-column col="fdEkpUser.name" title="${lfn:message('third-ding:thirdDingDtask.fdEkpUser')}" escape="false">
            <c:out value="${thirdDingDtask.fdEkpUser.fdName}" />
        </list:data-column>
        <list:data-column col="fdEkpUser.id" escape="false">
            <c:out value="${thirdDingDtask.fdEkpUser.fdId}" />
        </list:data-column>
        <list:data-column property="fdTaskId" title="${lfn:message('third-ding:thirdDingDtask.fdTaskId')}" />
        <list:data-column property="fdEkpTaskId" title="${lfn:message('third-ding:thirdDingDtask.fdEkpTaskId')}" />
        <list:data-column col="fdStatus.name" title="${lfn:message('third-ding:thirdDingDtask.fdStatus')}">
            <c:if test="${thirdDingDtask.fdStatus=='10' }">
            	${lfn:message('third-ding:enums.status.10')}
            </c:if>
            <c:if test="${thirdDingDtask.fdStatus=='11' }">
            	${lfn:message('third-ding:enums.status.11')}
            </c:if>
            <c:if test="${thirdDingDtask.fdStatus=='12' }">
            	${lfn:message('third-ding:enums.status.12')}
            </c:if>
            <c:if test="${thirdDingDtask.fdStatus=='20' }">
            	${lfn:message('third-ding:enums.status.20')}
            </c:if>
            <c:if test="${thirdDingDtask.fdStatus=='21' }">
            	${lfn:message('third-ding:enums.status.21')}
            </c:if>
            <c:if test="${thirdDingDtask.fdStatus=='22' }">
            	${lfn:message('third-ding:enums.status.22')}
            </c:if>
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingDtask.docCreateTime')}">
            <kmss:showDate value="${thirdDingDtask.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('third-ding:thirdDingOmsInit.handle') }" escape="false">
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:if test="${thirdDingDtask.fdStatus=='10' or thirdDingDtask.fdStatus=='11' or thirdDingDtask.fdStatus=='20' or thirdDingDtask.fdStatus=='21'}">
						<kmss:auth requestURL="/third/ding/third_ding_dtask/thirdDingDtask.do?method=edit&fdId=${thirdDingDtask.fdId}" requestMethod="GET">
							<a class="btn_txt" href="javascript:sendOpr('${thirdDingDtask.fdId}')">${lfn:message('third-ding:enums.status.opr')}</a>
						</kmss:auth>
					</c:if>
				</div>
			</div>
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
