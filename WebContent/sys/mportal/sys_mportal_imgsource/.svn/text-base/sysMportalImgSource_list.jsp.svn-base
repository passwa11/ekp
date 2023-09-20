<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysMportalImgSource" list="${queryPage.list}">
		<list:data-column property="fdId"></list:data-column>
		<list:data-column property="fdName" title="${lfn:message('sys-mportal:sysMportalImgSource.fdName')}" style="text-align:left;min-width:180px"/>
		<list:data-column headerClass="width120" property="docCreator.fdName" title="${lfn:message('sys-mportal:sysMportalImgSource.docCreator')}" />
		<list:data-column headerClass="width160" property="docCreateTime" title="${lfn:message('sys-mportal:sysMportalImgSource.docCreateTime')}" />
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do?method=edit&fdId=${sysMportalImgSource.fdId}" requestMethod="POST">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysMportalImgSource.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do?method=delete&fdId=${sysMportalImgSource.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysMportalImgSource.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>