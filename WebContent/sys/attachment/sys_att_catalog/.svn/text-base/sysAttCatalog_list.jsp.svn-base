<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysAttCatalog" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  headerClass="width200" property="fdName" title="${ lfn:message('sys-attachment:sysAttCatalog.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column  property="fdPath" title="${ lfn:message('sys-attachment:sysAttCatalog.fdPath') }">
		</list:data-column>
		<list:data-column headerClass="width80" col="fdIsCurrent" title="${ lfn:message('sys-attachment:sysAttCatalog.fdIsCurrent') }">
		    <c:if test="${sysAttCatalog.fdIsCurrent==true}">
				<bean:message key="message.yes"/>
			</c:if>
			<c:if test="${sysAttCatalog.fdIsCurrent!=true}">
				<bean:message key="message.no"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/attachment/sys_att_catalog/sysAttCatalog.do?method=edit&fdId=${sysAttCatalog.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysAttCatalog.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/attachment/sys_att_catalog/sysAttCatalog.do?method=delete&fdId=${sysAttCatalog.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteAll('${sysAttCatalog.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>