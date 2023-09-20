<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="modelingAppFlow" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  col="fdName" property="fdName" title="${ lfn:message('sys-modeling-base:modeling.flow.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
<%/*
		<list:data-column col="authEditor" title="可维护者" escape="false">
			<c:forEach items="${modelingAppFlow.authEditors}" var="authEditor" varStatus="idx">
				<c:if test="${ idx.index > 0 }">;</c:if>
					${ authEditor.fdName }
			</c:forEach>
		</list:data-column>
 */%>
		<list:data-column col="authReader" title="${lfn:message('sys-modeling-base:modeling.app.userAvailable')}" escape="false">
			<c:if test ="${modelingAppFlow.authNotReaderFlag == true}">
				${lfn:message('sys-modeling-base:modeling.app.notAvailableToAll')}
			</c:if>
			<c:if test ="${modelingAppFlow.authNotReaderFlag == false}">
				<c:set var="isnotdata" value="true" />
				<c:forEach items="${modelingAppFlow.authReaders}" var="authReader" varStatus="idx">
					<c:set var="isnotdata" value="false" />
					<c:if test="${ idx.index > 0 }">;</c:if>
						${ authReader.fdName }
				</c:forEach>
				<c:if test ="${isnotdata eq 'true'}">
					${lfn:message('sys-modeling-base:modeling.app.AvailableToAll')}
				</c:if>
			</c:if>
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