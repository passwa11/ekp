<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImeetingTemplate" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 模板名称 -->	
		<list:data-column  property="fdName" title="${ lfn:message('km-imeeting:kmImeetingTemplate.fdName') }" />
		<!-- 所属分类 -->
		<list:data-column property="docCategory.fdName" title="${ lfn:message('km-imeeting:kmImeetingTemplate.docCategoryId') }" />
		<!-- 排序号 -->
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('km-imeeting:kmImeetingTemplate.fdOrder') }" />
		<!-- 模板状态 -->
		<list:data-column col="fdIsAvailable" title="${lfn:message('km-imeeting:kmImeetingTemplate.fdStatus') }" escape="false">
			<c:choose>
				<c:when test="${kmImeetingTemplate.fdIsAvailable}">
					<bean:message bundle='km-imeeting' key='kmImeetingTemplate.fdIsAvailable.true' />
				</c:when>
				<c:otherwise>
					<bean:message bundle='km-imeeting' key='kmImeetingTemplate.fdIsAvailable.false' />
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!-- 创建者 -->
		<list:data-column property="docCreator.fdName" title="${ lfn:message('km-imeeting:kmImeetingTemplate.docCreatorId') }" />
		<!-- 创建时间 -->
		<list:data-column property="docCreateTime" title="${ lfn:message('km-imeeting:kmImeetingTemplate.docCreateTime') }" />
		
		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:if test="${kmImeetingTemplate.fdIsAvailable}">
						<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId=${kmImeetingTemplate.fdId}" requestMethod="GET">
							<!-- 会议安排 -->
							<a class="btn_txt" href="javascript:addDoc('${kmImeetingTemplate.fdId}')">${lfn:message('km-imeeting:kmImeeting.btn.add.meeting')}</a>
						</kmss:auth>
					</c:if>
					<kmss:auth requestURL="/km/imeeting/km_imeeting_template/kmImeetingTemplate.do?method=edit&fdId=${kmImeetingTemplate.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmImeetingTemplate.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/km/imeeting/km_imeeting_template/kmImeetingTemplate.do?method=delete&fdId=${kmImeetingTemplate.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteTepl('${kmImeetingTemplate.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>	
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>