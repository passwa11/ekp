<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="hrStaffPersonReport" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!-- 统计名称-->
		<list:data-column property="fdName" title="${ lfn:message('hr-staff:hrStaffPersonReport.fdName') }">
		</list:data-column>
		<!-- 组织部门-->
		<list:data-column headerClass="width200" col="fdDept" title="${ lfn:message('hr-staff:hrStaffPersonReport.fdDept') }">
			${hrStaffPersonReport.fdQueryNames}
		</list:data-column>
		<!-- 入职期间-->
		<list:data-column headerClass="width100" col="fdPeriod" title="${ lfn:message('hr-staff:hrStaffPersonReport.fdPeriod') }">
			<kmss:showDate value="${hrStaffPersonReport.fdBeginPeriod}" type="date" /> - <kmss:showDate value="${hrStaffPersonReport.fdEndPeriod}" type="date" /> 
		</list:data-column>
		<!--创建人-->
		<list:data-column headerClass="width100" col="docCreator" title="${ lfn:message('model.fdCreator') }">
		    ${hrStaffPersonReport.docCreator.fdName}
		</list:data-column>
		<!--创建时间-->
		<list:data-column headerClass="width100" col="docCreateTime" title="${ lfn:message('model.fdCreateTime') }">
		    <kmss:showDate value="${hrStaffPersonReport.docCreateTime}" type="datetime" /> 
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:authShow roles="ROLE_HRSTAFF_REPORT">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${hrStaffPersonReport.fdId}')">${lfn:message('button.edit')}</a>
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:del('${hrStaffPersonReport.fdId}')">${ lfn:message('button.delete') }</a>
			    	</kmss:authShow>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>