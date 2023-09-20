<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="project" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!--项目名称-->
		<list:data-column headerClass="width80" property="fdName" title="${ lfn:message('hr-staff:hrStaffPersonExperience.project.fdCompany') }"> 
		</list:data-column>
		<!--所属角色-->
		<list:data-column headerClass="width80" property="fdRole" title="${ lfn:message('hr-staff:hrStaffPersonExperience.project.fdPosition') }"> 
		</list:data-column>
		<!--开始时间-->
		<list:data-column headerClass="width80" col="fdBeginDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.fdBeginDate') }">
		    <kmss:showDate value="${project.fdBeginDate}" type="date" /> 
		</list:data-column>
		<!--结束日期-->
		<list:data-column headerClass="width80" col="fdEndDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.fdEndDate') }">
			<kmss:showDate value="${project.fdEndDate}" type="date" /> 
		</list:data-column> 
		<!--工作描述-->
		<list:data-column headerClass="width160" col="fdMemo" title="${ lfn:message('hr-staff:hrStaffPersonExperience.project.fdMemo') }" escape="false">
			<span style="width:180px" class="textEllipsis" title="${work.fdDescription}">${project.fdMemo}</span>
		</list:data-column> 
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:_delete('${LUI_ContextPath}/hr/staff/hr_staff_person_experience/project/hrStaffPersonExperienceProject.do?method=deleteall', '${project.fdId}')">${ lfn:message('button.delete') }</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>