<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysAdminTransferTask" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 任务类型 -->
		<list:data-column headerClass="width80" col="fdType" title="${ lfn:message('sys-admin-transfer:sysAdminTransferTask.fdType') }">
			<c:choose>
				<c:when test="${sysAdminTransferTask.fdStatus == '0' || sysAdminTransferTask.fdStatus == '1'}">
					<bean:message bundle="sys-admin-transfer" key="sysAdminTransferTask.fdType.transfer"/>
				</c:when>
				<c:otherwise>
					<bean:message bundle="sys-admin-transfer" key="sysAdminTransferTask.fdType.notify"/>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!-- 名称 -->
		<list:data-column headerClass="width100" property="fdName" title="${ lfn:message('sys-admin-transfer:sysAdminTransferTask.fdName') }">
		</list:data-column>
		<!-- 任务说明 -->
		<list:data-column headerClass="width300" property="fdDescription" title="${ lfn:message('sys-admin-transfer:sysAdminTransferTask.fdDescription') }" style="text-align:left">
		</list:data-column>
		<!-- 处理状态 -->
		<list:data-column headerClass="width80" col="fdStatus" title="${ lfn:message('sys-admin-transfer:sysAdminTransferTask.fdStatus') }" escape="false">
			<div id="status_${sysAdminTransferTask.fdId}">
				<xform:select property="fdStatus" showStatus="view" value="${sysAdminTransferTask.fdStatus}">
					<xform:enumsDataSource enumsType="sysAdminTransferTask.fdStatus" />
				</xform:select>
			</div>
		</list:data-column>
		<!-- 处理结果 -->
		<list:data-column headerClass="width80" col="fdResult" title="${ lfn:message('sys-admin-transfer:sysAdminTransferTask.fdResult') }" escape="false">
			<div id="result_${sysAdminTransferTask.fdId}">
				<xform:select property="fdResult" showStatus="view" value="${sysAdminTransferTask.fdResult}">
					<xform:enumsDataSource enumsType="sysAdminTransferTask.fdResult" />
				</xform:select>
			</div>
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width100" col="docCreateTime" title="${ lfn:message('model.fdCreateTime') }">
		    <kmss:showDate value="${sysAdminTransferTask.docCreateTime}" type="date"/>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width60" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<c:if test="${sysAdminTransferTask.fdStatus == '0' || sysAdminTransferTask.fdStatus == '2'}">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 处理 -->
					<a class="btn_txt" href="javascript:transferAll('${sysAdminTransferTask.fdId}')">${lfn:message('sys-admin-transfer:button.transfer')}</a>
				</div>
			</div>
			<!--操作按钮 结束-->
			</c:if>
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>