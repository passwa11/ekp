<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="modelingAppNav" list="${queryPage.list}">
		<list:data-column property="fdId" />
		<list:data-column headerClass="width50" property="fdOrder" title="${ lfn:message('sys-modeling-base:modelingAppNav.fdOrder') }">
		</list:data-column>
		<!-- 名称 -->
		<list:data-column col="docSubject" title="${ lfn:message('sys-modeling-base:modelingAppNav.docSubject')}" style="min-width:180px">
			<%--@elvariable id="ModelingProfileConstant" type="com.landray.kmss.sys.modeling.base.profile.constant.ModelingProfileConstant"--%>

			<c:if test="${modelingAppNav.docSubject == null}">
				默认导航
				<c:if test="${modelingAppNav.fdNavVersion == null || modelingAppNav.fdNavVersion != '1'}">
					(旧版)
				</c:if>
			</c:if>
			<c:if test="${modelingAppNav.docSubject != null}">
				<c:out value="${modelingAppNav.docSubject}" />
				<c:if test="${modelingAppNav.fdNavVersion == null || modelingAppNav.fdNavVersion != '1'}">
					(旧版)
				</c:if>
			</c:if>
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column col="docCreator" title="${ lfn:message('sys-modeling-base:modelingAppNav.docCreator') }" escape="false">
<%--		 	<ui:person personId="${modelingAppNav.docCreator.fdId}" personName="${modelingAppNav.docCreator.fdName}"></ui:person>--%>
			<c:if test="${modelingAppNav.docCreator == null}">
				管理员
			</c:if>
			<c:if test="${modelingAppNav.docCreator != null}">
				<c:out value="${modelingAppNav.docCreator.fdName}" />
			</c:if>
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column col="docCreateTime" title="${ lfn:message('sys-modeling-base:modelingAppNav.docCreateTime') }">
		    <kmss:showDate value="${modelingAppNav.docCreateTime}" type="dateTime"/>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${modelingAppNav.fdId}')">${lfn:message('button.edit')}</a>
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:del('${modelingAppNav.fdId}')">${lfn:message('button.delete')}</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>