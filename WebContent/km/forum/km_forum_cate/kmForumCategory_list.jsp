<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmForumCategory" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('km-forum:kmForumCategory.fdName') }" style="text-align:center;min-width:180px">
		</list:data-column>
		<c:if test="${param.type=='forum'}">
		<list:data-column headerClass="width80" property="fdParent.fdName" title="${ lfn:message('km-forum:kmForumCategory.fdParentId') }">
		</list:data-column>
		<list:data-column headerClass="width100" col="authAllEditors" title="${ lfn:message('km-forum:kmForumCategory.forumManagers') }">
			<c:forEach items="${kmForumCategory.authAllEditors}" var="forumManager" varStatus="indx">
				<c:if test="${indx.index>0}">
						;
				</c:if>
				<c:out value="${forumManager.fdName}" />
			</c:forEach>
		</list:data-column>
		</c:if>
		<list:data-column headerClass="width100" property="docCreator.fdName" title="${ lfn:message('km-forum:kmForumCategory.docCreatorId') }">
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('km-forum:kmForumCategory.docCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:if test="${param.type=='directory'}">
						<kmss:auth requestURL="/km/forum/km_forum_cate/kmForumCategory.do?method=edit&fdId=${kmForumCategory.fdId}" requestMethod="GET">
							<a class="btn_txt" href="javascript:editDirectory('${kmForumCategory.fdId}')">${lfn:message('button.edit')}</a>
						</kmss:auth>
					</c:if>
					<c:if test="${param.type=='forum'}">
						<kmss:auth requestURL="/km/forum/km_forum_cate/kmForumCategory.do?method=edit&fdId=${kmForumCategory.fdId}" requestMethod="GET">
							<a class="btn_txt" href="javascript:edit('${kmForumCategory.fdId}')">${lfn:message('button.edit')}</a>
						</kmss:auth>
					</c:if>
					<kmss:auth requestURL="/km/forum/km_forum_cate/kmForumCategory.do?method=delete&fdId=${kmForumCategory.fdId}" requestMethod="GET">
						<a class="btn_txt" href="javascript:deleteAll('${kmForumCategory.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>