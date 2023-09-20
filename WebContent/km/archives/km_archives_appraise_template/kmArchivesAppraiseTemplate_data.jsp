<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="kmArchivesAppraiseTemplate" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('km-archives:kmArchivesAppraiseTemplate.fdName')}" />
        <list:data-column property="docCreateTime" title="${lfn:message('km-archives:kmArchivesAppraiseTemplate.docCreateTime')}" />
       <%--  <list:data-column col="docCreator.fdName" title="${lfn:message('km-archives:kmArchivesAppraiseTemplate.docCreator')}" escape="false">
            <ui:person personId="${kmArchivesAppraiseTemplate.docCreator.fdId}" personName="${kmArchivesAppraiseTemplate.docCreator.fdName}" />
        </list:data-column> --%>
        <list:data-column headerClass="width100" property="docCreator.fdName" title="${lfn:message('km-archives:kmArchivesAppraiseTemplate.docCreator')}">
		</list:data-column>
		<list:data-column col="fdDefaultFlag" escape="false" title="${lfn:message('km-archives:list.isDefaultFlag')}">
        	<c:if test="${kmArchivesAppraiseTemplate.fdDefaultFlag=='1' }">
				<img src='<c:url value="/sys/profile/resource/images/profile_list_status_y.png"/>'>
			</c:if>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/km/archives/km_archives_appraise_template/kmArchivesAppraiseTemplate.do?method=edit&fdId=${kmArchivesAppraiseTemplate.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmArchivesAppraiseTemplate.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/km/archives/km_archives_appraise_template/kmArchivesAppraiseTemplate.do?method=delete&fdId=${kmArchivesAppraiseTemplate.fdId}" requestMethod="GET">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:del('${kmArchivesAppraiseTemplate.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
