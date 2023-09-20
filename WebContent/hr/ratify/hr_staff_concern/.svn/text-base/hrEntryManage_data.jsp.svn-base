<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.model.HrStaffEntry,com.landray.kmss.sys.organization.model.SysOrgPost,com.landray.kmss.util.ArrayUtil,java.util.*"%>
<%@page import="com.landray.kmss.hr.staff.model.HrStaffPersonInfo"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService"%>
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
		<list:data-column col="fdPlanEntryTime" title="${lfn:message('hr-ratify:mobile.hrStaffEntry.fdPlanEntryTime') }">
			<kmss:showDate value="${hrStaffEntry.fdPlanEntryTime }" type="date"></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdAbandonEntryTime" title="原定入职日期">
			<kmss:showDate value="${hrStaffEntry.fdPlanEntryTime }" type="date"></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdPlanEntryDept" title="${lfn:message('hr-ratify:hrRatifyEntry.fdEntryDept') }">
			<c:out value="${hrStaffEntry.fdPlanEntryDept.fdName }"></c:out>
		</list:data-column>
		<list:data-column col="fdOrgPosts" title="${lfn:message('hr-ratify:hrRatifyEntry.fdEntryPosts') }">
			<%
				List<SysOrgPost> fdOrgPosts = ((HrStaffEntry)pageContext.getAttribute("hrStaffEntry")).getFdOrgPosts();
				String[] str = ArrayUtil.joinProperty(fdOrgPosts, "fdName", ";");
				out.println(str[0]);
			%>
		</list:data-column>
		<list:data-column col="fdQRStatus" title="${lfn:message('hr-ratify:hrRatifyEntry.entry.codeScanning') }">
			<c:choose>
				<c:when test="${hrStaffEntry.fdQRStatus }">
					<bean:message key="message.yes"/>
				</c:when>
				<c:otherwise>
					<bean:message key="message.no"/>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="creator" title="${lfn:message('hr-ratify:hrRatifyEntry.addPerson') }">
			<c:out value="${hrStaffEntry.docCreator.fdName }"></c:out>
		</list:data-column>
		<list:data-column property="fdStaffNo" title="${lfn:message('hr-staff:hrStaffEntry.fdStaffNo') }">
		</list:data-column>
		<list:data-column property="fdAbandonReason" title="${lfn:message('hr-staff:hrStaffEntry.fdAbandonReason') }">
		</list:data-column>
		<list:data-column property="fdAbandonRemark" title="${lfn:message('hr-staff:hrStaffEntry.fdAbandonRemark') }">
		</list:data-column>
		<list:data-column col="docCreateTime" title="添加时间">
			<kmss:showDate value="${hrStaffEntry.docCreateTime }" type="datetime"></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdPersonStatus" title="岗位状态">
			<%
				IHrStaffPersonInfoService service = (IHrStaffPersonInfoService)SpringBeanUtil.getBean("hrStaffPersonInfoService");
				HrStaffEntry entry = (HrStaffEntry)pageContext.getAttribute("hrStaffEntry");
				String fdStaffEntryId = entry.getFdId();
				HrStaffPersonInfo info = (HrStaffPersonInfo)service.findByStaffEntryId(fdStaffEntryId);
				if(info != null){
					request.setAttribute("fdStatus", info.getFdStatus());
					request.setAttribute("fdEntryTime", info.getFdEntryTime());
					request.setAttribute("fdPersonId", info.getFdId());
				}else{
					request.setAttribute("fdStatus", null);
					request.setAttribute("fdEntryTime", null);
					request.setAttribute("fdPersonId", null);
				}
			%>
			<sunbor:enumsShow enumsType="hrStaffPersonInfo_fdStatus" value="${fdStatus }"></sunbor:enumsShow>
		</list:data-column>
		<list:data-column col="fdEntryTime" title="${lfn:message('hr-staff:hrStaffPersonInfo.fdEntryTime') }">
			<kmss:showDate value="${fdEntryTime }" type="date"></kmss:showDate>
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
		<list:data-column property="fdEmail" title="${lfn:message('hr-staff:hrStaffPersonInfo.fdEmail') }" headerClass="width160">
		</list:data-column>
		<list:data-column col="rowHref">
			<c:choose>
				<c:when test="${hrStaffEntry.fdStatus eq 2 and not empty fdPersonId}">
					<c:out value="${fdPersonId }"></c:out>
				</c:when>
				<c:otherwise>
					<c:out value="${hrStaffEntry.fdId }"></c:out>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<c:if test="${param.fdStatus eq 1 }">
		<!-- 其它操作 -->
		<list:data-column col="operations" title="${ lfn:message('list.operation') }" escape="false" headerClass="width160">
			<!--操作按钮 开始-->
			<div class="lui_hr_link_group">
				<div class="conf_btn_edit">
					<kmss:authShow roles="ROLE_HRSTAFF_ENTRYCHECK">
						<!-- 确认 -->
						<span class="lui_hr_link_item ">
							<a class="lui_text_primary" href="javascript:check('${hrStaffEntry.fdId}')">${lfn:message('hr-staff:hrStaffEntry.check')}</a>
						</span>
					</kmss:authShow>
					<kmss:authShow roles="ROLE_HRRATIFY_CREATE">
						<!-- 发起入职审批 -->
						<span class="lui_hr_link_item ">
							<a class="lui_text_primary" href="javascript:addRatifyEntry('${hrStaffEntry.fdId}')">${lfn:message('hr-staff:hrStaffEntry.initiate.employmentApproval')}</a>
						</span>
			    	</kmss:authShow>
			    	<span class="lui_hr_link_item ">
			    		<a class="lui_text_primary" href="javascript:abandonEntry('${hrStaffEntry.fdId}');">${lfn:message('hr-staff:hrStaffEntry.abandon.employment')}</a>
			    	</span>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
		</c:if>
		<c:if test="${param.fdStatus eq 3 }">
				<!-- 其它操作 -->
		<list:data-column col="operations" title="${ lfn:message('list.operation') }" escape="false" headerClass="width100">
			<!--操作按钮 开始-->
			<div class="lui_hr_link_group">
				<div class="conf_btn_edit">
						<!-- 删除 -->
						<span class="lui_hr_link_item ">
							<a class="lui_text_primary" href="javascript:del('${hrStaffEntry.fdId}')">删除</a>
						</span>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
		</c:if>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>