<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="hrStaffPersonInfo" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdIsAvailable">
			${hrStaffPersonInfo.fdOrgPerson.fdIsAvailable}
		</list:data-column>
		<list:data-column col="imgUrl">
			${urlJson[hrStaffPersonInfo.fdId]}
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!-- 姓名-->
		<list:data-column headerClass="width100" property="fdName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdName') }">
		</list:data-column>
		<!--账号-->
		<list:data-column headerClass="width80" col="fdLoginName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdLoginName') }"> 
		     <c:if test="${hrStaffPersonInfo.fdOrgPerson != null}">
	    		${hrStaffPersonInfo.fdOrgPerson.fdLoginName}
	    	</c:if>
		</list:data-column>
		<!--到本单位时间-->
		<list:data-column headerClass="width100" col="fdTimeOfEnterprise" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise') }">
		    <kmss:showDate value="${hrStaffPersonInfo.fdTimeOfEnterprise}" type="date" /> 
		</list:data-column>
		<!--部门-->
		<list:data-column headerClass="width200" col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			${hrStaffPersonInfo.fdOrgParentsName}
		</list:data-column>
		<!--工号-->
		<list:data-column headerClass="width100" property="fdStaffNo" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffNo') }">
		</list:data-column>
		<!--员工状态-->
		<list:data-column headerClass="width100" col="fdStatus" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }">
			<sunbor:enumsShow value="${ hrStaffPersonInfo.fdStatus }" enumsType="hrStaffPersonInfo_fdStatus" />
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:authShow roles="ROLE_HRSTAFF_EDIT">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${hrStaffPersonInfo.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:authShow>
					<kmss:authShow roles="ROLE_HRSTAFF_DELETE">
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:del('${hrStaffPersonInfo.fdId}')">${ lfn:message('button.delete') }</a>
			    	</kmss:authShow>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>