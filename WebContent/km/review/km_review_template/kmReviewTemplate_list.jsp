<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmReviewTemplate" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('km-review:kmReviewTemplate.fdName') }" style="text-align:left;min-width:130px">
		</list:data-column>
		<list:data-column headerClass="width50" col="fdIsAvailable" title="${ lfn:message('km-review:kmReviewTemplate.fdStatus') }" escape="false">
		    <c:if test="${kmReviewTemplate.fdIsAvailable}">
				<bean:message bundle="km-review" key="kmReviewTemplate.fdIsAvailable.true" />
			</c:if>
			<c:if test="${!kmReviewTemplate.fdIsAvailable}">
				<bean:message bundle="km-review" key="kmReviewTemplate.fdIsAvailable.false" />
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('km-review:kmReviewMain.fdOrder') }">
		</list:data-column>
		<list:data-column headerClass="width80" property="docCreator.fdName" title="${ lfn:message('km-review:kmReviewTemplate.docCreatorId') }">
		</list:data-column>
		<list:data-column style="white-space:nowrap" headerClass="width120" property="docCreateTime" title="${ lfn:message('km-review:kmReviewTemplate.docCreateTime') }">
		</list:data-column>
		<list:data-column style="white-space:nowrap" headerClass="width120" property="docAlterTime" title="${ lfn:message('km-review:kmReviewTemplate.docAlterTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
				 	<c:if test="${kmReviewTemplate.fdIsAvailable}">
				 		<kmss:auth requestURL="/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId=${kmReviewTemplate.fdId}" requestMethod="GET">
							<!-- 新建流程 -->
							<a class="btn_txt" href="javascript:addDoc('${kmReviewTemplate.fdId}')">${lfn:message('km-review:kmReviewMain.opt.create')}</a>
						</kmss:auth>
				  	</c:if>
					<kmss:auth requestURL="/km/review/km_review_template/kmReviewTemplate.do?method=edit&fdId=${kmReviewTemplate.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmReviewTemplate.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:authShow roles="ROLE_KMREVIEW_SETTING">
						<!-- 搜索设置 -->
						<a class="btn_txt" href="javascript:setSearch('${kmReviewTemplate.fdId}','${kmReviewTemplate.fdName}')">${lfn:message('km-review:button.searchSet')}</a>
					</kmss:authShow>
					<kmss:auth requestURL="/km/review/km_review_template/kmReviewTemplate.do?method=delete&fdId=${kmReviewTemplate.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteDoc('${kmReviewTemplate.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>