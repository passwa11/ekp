<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysMportalPage" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column headerClass="width30" property="fdOrder" title="${lfn:message('sys-mportal:sysMportalPage.fdOrder') }" />
		<!-- 名称 -->
		<list:data-column  property="fdName" title="${ lfn:message('sys-mportal:sysMportalPage.fdName') }" style="text-align:left;min-width:180px" />
		<!-- 是否有效 -->
		<list:data-column  col="fdEnabled" title="${ lfn:message('sys-mportal:sysMportalPage.fdEnabled') }" >
			<sunbor:enumsShow enumsType="common_yesno"  value="${sysMportalPage.fdEnabled}"></sunbor:enumsShow>
		</list:data-column>
		<!-- 创建者 -->
		<list:data-column headerClass="width120" property="docCreator.fdName" title="${ lfn:message('sys-mportal:sysMportalPage.docCreator') }" />
		<!-- 创建时间 -->
		<list:data-column headerClass="width160" property="docCreateTime" title="${ lfn:message('sys-mportal:sysMportalPage.docCreateTime') }" />
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=edit&fdId=${sysMportalPage.fdId }" requestMethod="POST">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysMportalPage.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=delete&fdId=${sysMportalPage.fdId }" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysMportalPage.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=edit&fdId=${sysMportalPage.fdId }" requestMethod="POST">
					<c:if test="${sysMportalPage.fdEnabled == false}">
						<a class="btn_txt" href="javascript:enableAll('${sysMportalPage.fdId}')">${lfn:message('sys-mportal:btn.fdIsAvailable.on')}</a>
					</c:if>
					<c:if test="${sysMportalPage.fdEnabled == true}">
						<a class="btn_txt" href="javascript:disableAll('${sysMportalPage.fdId}')">${lfn:message('sys-mportal:btn.fdIsAvailable.off')}</a>
					</c:if>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>