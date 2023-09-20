<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list}">
		<list:data-column property="fdId"></list:data-column>
		<list:data-column property="docSubject" title="${lfn:message('sys-mportal:sysMportalMenu.docSubject')}" style="text-align:left;min-width:180px"/>
		<list:data-column headerClass="width120" property="docCreator.fdName" title="${lfn:message('sys-mportal:sysMportalMenu.docCreator')}" />
		<list:data-column headerClass="width160" property="docCreateTime" title="${lfn:message('sys-mportal:sysMportalMenu.docCreateTime')}" />
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=edit&fdId=${item.fdId }" requestMethod="POST">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${item.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=delete&fdId=${item.fdId }" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${item.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>