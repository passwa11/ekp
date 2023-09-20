<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysPersonSysNavCategory" list="${list}">
		<list:data-column property="fdId" />
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('sys-person:sysPersonSysTabCategory.fdOrder') }">
		</list:data-column>
		<list:data-column  property="fdName" title="${ lfn:message('sys-person:sysPersonSysNavCategory.fdName') }" style="text-align:left">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdShortName" title="${ lfn:message('sys-person:sysPersonSysNavCategory.fdShortName') }">
		</list:data-column>
		<list:data-column  col="fdStatus" title="${ lfn:message('sys-person:sysPersonSysNavCategory.fdStatus')}" escape="false">
		   <sunbor:enumsShow value="${sysPersonSysNavCategory.fdStatus}"  enumsType="sysPerson_fdStatus" />
		</list:data-column>
		<list:data-column headerClass="width80" property="docCreator.fdName" title="${ lfn:message('sys-person:sysPersonSysNavCategory.docCreator') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="docCreateTime" title="${lfn:message('sys-person:sysPersonSysNavCategory.docCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/person/sys_person_sysnav_category/sysPersonSysNavCategory.do?method=edit&fdId=${sysPersonSysNavCategory.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysPersonSysNavCategory.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/person/sys_person_sysnav_category/sysPersonSysNavCategory.do?method=delete&fdId=${sysPersonSysNavCategory.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:PersonOnDeleteById('${sysPersonSysNavCategory.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					<c:choose>
						<c:when test="${sysPersonSysNavCategory.fdStatus == 1}">
							<kmss:auth requestURL="/sys/person/sys_person_sysnav_category/sysPersonSysNavCategory.do?method=edit&fdId=${sysPersonSysNavCategory.fdId}" requestMethod="GET">
								<!-- 启用 -->
								<a class="btn_txt" href="javascript:PersonOnUpdateStatus(2, '${sysPersonSysNavCategory.fdId}');">${lfn:message('sys-person:btn.start')}</a>
							</kmss:auth>
						</c:when>
						<c:otherwise>
							<kmss:auth requestURL="/sys/person/sys_person_sysnav_category/sysPersonSysNavCategory.do?method=edit&fdId=${sysPersonSysNavCategory.fdId}" requestMethod="GET">
								<!-- 停用 -->
								<a class="btn_txt" href="javascript:PersonOnUpdateStatus(1, '${sysPersonSysNavCategory.fdId}');">${lfn:message('sys-person:btn.stop')}</a>
							</kmss:auth>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>