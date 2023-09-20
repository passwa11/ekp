<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrgMatrix" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 排序号 -->
		<list:data-column headerClass="width80"  property="fdOrder" title="${ lfn:message('sys-organization:sysOrgMatrix.fdOrder') }">
		</list:data-column>
		<!-- 矩阵名称 -->
		<list:data-column headerClass="width200" property="fdName" title="${ lfn:message('sys-organization:sysOrgMatrix.fdName') }">
		</list:data-column>
		<!-- 矩阵类别 -->
		<list:data-column headerClass="width200" col="fdGroupCate" title="${ lfn:message('sys-organization:sysOrgMatrix.fdCategory') }">
			${sysOrgMatrix.fdCategory.fdName}
		</list:data-column>
		<!-- 是否内置还是人工创建 -->
		<list:data-column col="matrixType">
			<c:if test="${sysOrgMatrix.matrixType=='1'}">
				(${ lfn:message('sys-organization:sysOrgMatrix.matrixType.sysCreate.message') })
			</c:if>
			<c:if test="${sysOrgMatrix.matrixType!='1'}">
				(${ lfn:message('sys-organization:sysOrgMatrix.matrixType.peopleCreate.message') })
			</c:if>
		</list:data-column>
		<list:data-column col="_matrixType">
			${sysOrgMatrix.matrixType}
		</list:data-column>
		<!-- 状态 -->
		<list:data-column escape="false" headerClass="width100" col="fdIsAvailable" title="${ lfn:message('sys-organization:sysOrgMatrix.fdIsAvailable') }">
			
        <c:if test="${sysOrgMatrix.fdIsAvailable!='1'}">
          <span class="orgMatrixAvailableStatus_able">
          </span>
        </c:if>
        <c:if test="${sysOrgMatrix.fdIsAvailable=='1'}">
          <span class="orgMatrixAvailableStatus_disable ">
          </span>
        </c:if>

			<sunbor:enumsShow value="${sysOrgMatrix.fdIsAvailable}" enumsType="sys_org_available" />

		</list:data-column>
		<!-- 原始状态 -->
		<list:data-column col="isAvailable">
			${sysOrgMatrix.fdIsAvailable}
		</list:data-column>
		<!-- 描述 -->
		<list:data-column  headerClass="width200" property="fdDesc" title="${ lfn:message('sys-organization:sysOrgMatrix.fdDesc') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<p class="card_tip">
				<c:set var="showCate" value="true"></c:set>
				<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=edit&fdId=${sysOrgMatrix.fdId}" requestMethod="GET">
				<c:set var="showCate" value="false"></c:set>
				<span class="card_tip_item" onclick="edit('${sysOrgMatrix.fdId}', 0);">
					<i class="card_icon icon_baseinfo"></i>
					<span class="card_tip_txt"><i class="card_tip_arrow"></i>
					<i class="card_tip_txt_inner">${lfn:message('sys-organization:sysOrgMatrix.base')}</i></span>
				</span>
				<span class="card_tip_item" onclick="edit('${sysOrgMatrix.fdId}', 1);">
					<i class="card_icon icon_field"></i>
					<span class="card_tip_txt"><i class="card_tip_arrow"></i>
					<i class="card_tip_txt_inner">${lfn:message('sys-organization:sysOrgMatrix.field')}</i></span>
				</span>
				<span class="card_tip_item" onclick="divideEdit('${sysOrgMatrix.fdId}');">
					<i class="card_icon icon_data"></i>
					<span class="card_tip_txt"><i class="card_tip_arrow"></i>
					<i class="card_tip_txt_inner">${lfn:message('sys-organization:sysOrgMatrix.fdContent')}</i></span>
				</span>
				</kmss:auth>
				<c:if test="${showCate eq 'true'}">
				<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=dataCate&fdId=${sysOrgMatrix.fdId}" requestMethod="GET">
				<span class="card_tip_item" onclick="dataCate('${sysOrgMatrix.fdId}');">
					<i class="card_icon icon_data"></i>
					<span class="card_tip_txt"><i class="card_tip_arrow"></i>
					<i class="card_tip_txt_inner">${lfn:message('sys-organization:sysOrgMatrix.fdContent')}</i></span>
				</span>
				</kmss:auth>
				</c:if>
				<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=invalidated&fdId=${sysOrgMatrix.fdId}" requestMethod="GET">
				<c:if test="${sysOrgMatrix.fdIsAvailable}">
				   <span class="card_tip_item" onclick="invalidated('${sysOrgMatrix.fdId}', false);">
						<i class="card_icon icon_disable"></i>
						<span class="card_tip_txt"><i class="card_tip_arrow"></i>
						<i class="card_tip_txt_inner">${lfn:message('sys-organization:sys.org.available.false')}</i></span>
					</span>
				</c:if>
				<c:if test="${!sysOrgMatrix.fdIsAvailable}">
				   <span class="card_tip_item" onclick="invalidated('${sysOrgMatrix.fdId}', true);">
						<i class="card_icon icon_start"></i>
						<span class="card_tip_txt"><i class="card_tip_arrow"></i>
						<i class="card_tip_txt_inner">${lfn:message('sys-organization:sys.org.available.true')}</i></span>
					</span>
				</c:if>
				</kmss:auth>
			</p>
			<!--操作按钮 结束-->
		</list:data-column>
		<!-- 编辑权限 -->
		<list:data-column col="edit_auth">
			<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=edit&fdId=${sysOrgMatrix.fdId}" requestMethod="GET">
				true
			</kmss:auth>
		</list:data-column>
		<!-- 分组维护者 -->
		<list:data-column col="data_cate_auth">
			<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=dataCate&fdId=${sysOrgMatrix.fdId}" requestMethod="GET">
				true
			</kmss:auth>
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>