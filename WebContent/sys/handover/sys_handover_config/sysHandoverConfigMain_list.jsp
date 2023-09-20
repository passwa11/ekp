<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysHandoverConfigMain" list="${queryPage.list }">
	    <list:data-column property="fdId">
		</list:data-column >
		<!--交接人-->
		<list:data-column property="fdFromName" title="${ lfn:message('sys-handover:sysHandoverConfigMain.fdFromName') }">
		</list:data-column>
		<!--接收人-->
		<list:data-column headerClass="width100" property="fdToName" title="${ lfn:message('sys-handover:sysHandoverConfigMain.fdToName') }">
		</list:data-column>
		<!--交接类型-->
		<list:data-column headerClass="width100" col="type"  escape="false" title="${ lfn:message('sys-handover:sysHandoverConfigMain.handoverType') }">
			<c:choose>
				<c:when test="${sysHandoverConfigMain.handoverType == 2}">
					${ lfn:message('sys-handover:sysHandoverConfigMain.handoverType.doc') }
				</c:when>
				<c:when test="${sysHandoverConfigMain.handoverType == 3}">
					${ lfn:message('sys-handover:sysHandoverConfigMain.handoverType.auth') }
				</c:when>
				<c:otherwise>
					${ lfn:message('sys-handover:sysHandoverConfigMain.handoverType.config') }
				</c:otherwise>
			</c:choose>
		</list:data-column>
		
		<!--状态-->
		<list:data-column headerClass="width100" col="fdState" title="${ lfn:message('sys-handover:sysHandoverConfigMain.status') }">
			<sunbor:enumsShow
				value="${sysHandoverConfigMain.fdState}"
				enumsType="sys_handover_state" />
		</list:data-column>
		<!--创建人-->
		<list:data-column headerClass="width100" col="docCreatorName" title="${ lfn:message('sys-handover:sysHandoverConfigMain.docCreatorId') }">
			${sysHandoverConfigMain.docCreator.fdName}
		</list:data-column>
		<!--创建时间-->
		<list:data-column headerClass="width120" property="docCreateTime" title="${ lfn:message('sys-handover:sysHandoverConfigMain.docCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:authShow roles="ROLE_SYSHANDOVER_MAINTAIN">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:delDoc('${sysHandoverConfigMain.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:authShow>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>