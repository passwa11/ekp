<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.model.HrStaffEntry,com.landray.kmss.sys.organization.model.SysOrgPost,com.landray.kmss.util.ArrayUtil,java.util.*"%>
<list:data>
	<list:data-columns list="${queryPage.list }" var="hrStaffEntry" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index" title="${lfn:message('page.serial') }">
			${status+1}
		</list:data-column>
		<list:data-column col="fdName" title="${lfn:message('hr-staff:hrStaffEntry.fdName') }" escape="false">
			<span class="com_subject"><c:out value="${hrStaffEntry.fdName}"/></span>
		</list:data-column>
		<list:data-column col="entryName" title="${lfn:message('hr-staff:hrStaffEntry.fdName') }">
			<c:out value="${hrStaffEntry.fdName}"/>
		</list:data-column>
		<list:data-column property="fdIdCard" title="${lfn:message('hr-staff:hrStaffEntry.fdIdCard') }">
		</list:data-column>
		<list:data-column property="fdMobileNo" title="${lfn:message('hr-staff:hrStaffEntry.fdMobileNo') }">
		</list:data-column>
		<list:data-column col="fdPlanEntryTime" title="${lfn:message('hr-staff:hrStaffEntry.fdPlanEntryTime') }">
			<kmss:showDate value="${hrStaffEntry.fdPlanEntryTime }" type="date"></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdPlanEntryDept" title="${lfn:message('hr-staff:hrStaffEntry.fdPlanEntryDept') }">
			<c:out value="${hrStaffEntry.fdPlanEntryDept.fdName }"></c:out>
		</list:data-column>
		<list:data-column col="fdOrgPosts" title="${lfn:message('hr-staff:hrStaffEntry.fdOrgPosts') }">
			<%
				List<SysOrgPost> fdOrgPosts = ((HrStaffEntry)pageContext.getAttribute("hrStaffEntry")).getFdOrgPosts();
				String[] str = ArrayUtil.joinProperty(fdOrgPosts, "fdName", ";");
				out.println(str[0]);
			%>
		</list:data-column>
		<list:data-column col="fdDataFrom" title="${lfn:message('hr-staff:hrStaffEntry.fdDataFrom') }">
			<sunbor:enumsShow enumsType="hrStaffEntry_fdDataFrom" value="${hrStaffEntry.fdDataFrom }"></sunbor:enumsShow>
		</list:data-column>
		<list:data-column col="fdLastModifiedTime" title="${lfn:message('hr-staff:hrStaffEntry.fdLastModifiedTime') }">
			<kmss:showDate value="${hrStaffEntry.fdLastModifiedTime }" type="datetime"></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdAlteror" title="${lfn:message('hr-staff:hrStaffEntry.fdAlteror') }">
			<c:out value="${hrStaffEntry.fdAlteror.fdName }"></c:out>
		</list:data-column>
		<list:data-column col="fdStatus" title="${lfn:message('hr-staff:hrStaffEntry.fdStatus') }">
			<sunbor:enumsShow enumsType="hrStaffEntry_fdStatus" value="${hrStaffEntry.fdStatus }"></sunbor:enumsShow>
		</list:data-column>
		<list:data-column col="fdCheckDate" title="${lfn:message('hr-staff:hrStaffEntry.fdCheckDate') }">
			<kmss:showDate value="${hrStaffEntry.fdCheckDate }" type="datetime"></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdChecker" title="${lfn:message('hr-staff:hrStaffEntry.fdChecker') }">
			<c:out value="${hrStaffEntry.fdChecker.fdName }"></c:out>
		</list:data-column>
		<list:data-column col="fdQRStatus" title="是否扫码">
			<c:choose>
				<c:when test="${hrStaffEntry.fdQRStatus }">
					<bean:message bundle="hr-staff" key="hrStaffEntry.fdQRStatus.yes"/>
				</c:when>
				<c:otherwise>
					<bean:message bundle="hr-staff" key="hrStaffEntry.fdQRStatus.no"/>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdSex" title="${ lfn:message('hr-ratify:hrRatifyEntry.fdSex') }">
	         <c:out value="${hrStaffEntry.fdSex}"/>
      	</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width130" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:authShow roles="ROLE_HRSTAFF_ENTRYCHECK">
					<!-- 确认 -->
					<a class="btn_txt" href="javascript:check('${hrStaffEntry.fdId}')">${lfn:message('hr-staff:hrStaffEntry.check')}</a>
					</kmss:authShow>
					<kmss:authShow roles="ROLE_HRSTAFF_EDIT">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${hrStaffEntry.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:authShow>
					<kmss:authShow roles="ROLE_HRSTAFF_DELETE">
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:del('${hrStaffEntry.fdId}')">${ lfn:message('button.delete') }</a>
			    	</kmss:authShow>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>