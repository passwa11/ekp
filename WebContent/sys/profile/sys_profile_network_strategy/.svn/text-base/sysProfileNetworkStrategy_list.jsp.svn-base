<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysProfileNetworkStrategy" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!-- IP网段-->
		<list:data-column col="network" escape="false" title="${ lfn:message('sys-profile:sys.profile.networkStrategy.network') }" style="text-align:left">
			${sysProfileNetworkStrategy.fdStartIp} ~~ ${sysProfileNetworkStrategy.fdEndIp}
		</list:data-column>
		<!-- 网段说明-->
		<list:data-column property="fdMark" title="${ lfn:message('sys-profile:sys.profile.networkStrategy.fdMark') }" style="text-align:left">
		</list:data-column>
		<!--创建者-->
		<list:data-column headerStyle="width:60px" col="docCreator" title="${ lfn:message('model.fdCreator') }" escape="false"> 
		    ${sysProfileNetworkStrategy.docCreator.fdName}
		</list:data-column>
		<!--创建时间-->
		<list:data-column headerStyle="width:80px" col="docCreateTime" title="${ lfn:message('model.fdCreateTime') }" escape="false">
		    <kmss:showDate value="${sysProfileNetworkStrategy.docCreateTime}" type="date" /> 
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑-->
					<a class="btn_txt" href="javascript:addOrEdit('${sysProfileNetworkStrategy.fdId}')">${ lfn:message('button.edit') }</a>
					<!-- 删除-->
					<a class="btn_txt" href="javascript:del('${sysProfileNetworkStrategy.fdId}')">${ lfn:message('button.delete') }</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>