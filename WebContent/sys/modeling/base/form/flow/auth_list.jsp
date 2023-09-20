<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="modelingAppFlow" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  col="fdName" property="fdName" title="${ lfn:message('sys-modeling-base:modeling.flow.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width80" col="fdValid" title="${ lfn:message('sys-modeling-base:modeling.flow.fdValid') }" escape="false">
		    <c:if test="${modelingAppFlow.fdValid}">
				${ lfn:message('sys-modeling-base:modeling.flow.fdValid.true') }
			</c:if>
			<c:if test="${!modelingAppFlow.fdValid}">
				${ lfn:message('sys-modeling-base:modeling.flow.fdValid.false') }
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width80" col="docCreator.fdName" property="docCreator.fdName" title="${ lfn:message('model.fdCreator') }">
		</list:data-column>
		<list:data-column headerClass="width120" col="docCreateTime"  property="docCreateTime" title="${ lfn:message('model.fdCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/modeling/base/modelingAppFlow.do?method=edit&fdId=${modelingAppFlow.fdId}" requestMethod="GET">
						<!-- 设置 -->
						<a class="btn_txt" href="javascript:doSetting('${modelingAppFlow.fdId}')">${lfn:message('sys-modeling-base:modeling.form.Set')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>