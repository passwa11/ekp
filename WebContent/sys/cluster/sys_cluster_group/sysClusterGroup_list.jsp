<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysClusterGroup" list="${queryPage.list}">
		<list:data-column property="fdId" />
		<list:data-column  property="fdOrder" title="${ lfn:message('sys-cluster:sysClusterGroup.fdName') }" >
		</list:data-column>
		<list:data-column  property="fdName" title="${ lfn:message('sys-cluster:sysClusterGroup.fdName') }" >
		</list:data-column>
		<list:data-column  property="fdKey" title="${ lfn:message('sys-cluster:sysClusterGroup.fdKey') }"  >
		</list:data-column>
		<list:data-column  property="fdUrl" title="${ lfn:message('sys-cluster:sysClusterGroup.fdUrl') }" >
		</list:data-column>
		<list:data-column  col="fdMaster" title="${ lfn:message('sys-cluster:sysClusterGroup.fdMaster') }"  escape="false"  >
		     <sunbor:enumsShow value="${sysClusterGroup.fdMaster}" enumsType="common_yesno" />
		</list:data-column>
		<list:data-column  col="fdLocal" title="${ lfn:message('sys-cluster:sysClusterGroup.fdLocal') }"  escape="false"  >
		      <sunbor:enumsShow value="${sysClusterGroup.fdLocal}" enumsType="common_yesno" />
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/cluster/sys_cluster_group/sysClusterGroup.do?method=delete&fdId=${sysClusterGroup.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteAll('${sysClusterGroup.fdId}')">删除</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>