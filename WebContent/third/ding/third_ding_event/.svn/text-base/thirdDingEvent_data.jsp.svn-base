<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingEvent" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingEvent.fdName')}" />
        <list:data-column property="fdTag" title="${lfn:message('third-ding:thirdDingEvent.fdTag')}" />
        <list:data-column property="fdCallbackUrl" title="${lfn:message('third-ding:thirdDingEvent.fdCallbackUrl')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('third-ding:thirdDingEvent.fdIsAvailable')}">
            <sunbor:enumsShow value="${thirdDingEvent.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
       <%--  <list:data-column col="fdIsAvailable">
            <c:out value="${thirdDingEvent.fdIsAvailable}" />
        </list:data-column> --%>
        <list:data-column col="fdIsStatus.name" title="${lfn:message('third-ding:thirdDingEvent.fdIsStatus')}">
            <sunbor:enumsShow value="${thirdDingEvent.fdIsStatus}" enumsType="third_ding_event_start" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('third-ding:thirdDingEvent.docCreator')}" escape="false">
            <c:out value="${thirdDingEvent.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${thirdDingEvent.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingEvent.docCreateTime')}">
            <kmss:showDate value="${thirdDingEvent.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:if test="${thirdDingEvent.fdIsStatus == 'true'}">
						<!-- 启用 -->
						<a class="btn_txt" href="javascript:updateStatus('${thirdDingEvent.fdId}', 'false')">禁用 </a>
					</c:if>
					<c:if test="${thirdDingEvent.fdIsStatus  == 'false'}">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:updateStatus('${thirdDingEvent.fdId}', 'true')">启用</a>
					</c:if>
				    <%-- <kmss:auth requestURL="/third/ding/third_ding_event/thirdDingEvent.do?method=deleteall">
                        <c:set var="canDelete" value="true" />
                        <a class="btn_txt" href="javascript:deleteDoc('${thirdDingEvent.fdId}')">${lfn:message('button.delete')}</a>
                    </kmss:auth> --%>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
