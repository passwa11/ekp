<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysAnonymMain" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('sys-anonym:sysAnonymMain.fdName')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('sys-anonym:sysAnonymMain.docSubmitTime')}">
            <kmss:showDate value="${sysAnonymMain.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docPublishTime" title="${lfn:message('sys-anonym:sysAnonymMain.newestdocPublishTime')}">
            <kmss:showDate value="${sysAnonymMain.docPublishTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('sys-anonym:sysAnonymMain.docAlterTime')}">
            <kmss:showDate value="${sysAnonymMain.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdCategory.fdName" title="${lfn:message('sys-anonym:sysAnonymCate.fdName')}" />
		<list:data-column col="fdStatus" title="${lfn:message('sys-anonym:sysAnonymMain.fdStatus')}">
            <sunbor:enumsShow value="${empty sysAnonymMain.fdStatus?0:sysAnonymMain.fdStatus}"
									enumsType="anonym_fdStatus" />
        </list:data-column>
		<!-- 操作 -->
		<list:data-column col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<c:if test="${sysAnonymMain.fdStatus == 1 }" >
				<a class="com_btn_link"  href="javascript:anonymCancel('${sysAnonymMain.fdId}','${sysAnonymMain.fdModelName}')">${lfn:message('sys-anonym:sysAnonymMain.btn.cancelpub')}</a>
			</c:if>
			<c:if test="${sysAnonymMain.fdStatus == 0 }" >
				<a class="com_btn_link"  href="javascript:anonymPublish('${sysAnonymMain.fdId}','${sysAnonymMain.fdModelName}')">${lfn:message('sys-anonym:sysAnonymMain.btn.repub')}</a>
			</c:if>
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
