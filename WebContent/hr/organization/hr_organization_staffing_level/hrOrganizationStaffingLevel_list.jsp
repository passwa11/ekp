<%@page import="com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService"%>
<%@page import="com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel"%>
<%@page import="com.landray.kmss.util.ArrayUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.common.dao.HQLInfo"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrganizationStaffingLevel" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 职务 -->
		<list:data-column headerClass="width300"  property="fdName" title="${ lfn:message('sys-organization:sysOrganizationStaffingLevel.fdName') }">
		</list:data-column>
		<!-- 职务编码 -->
		<list:data-column headerClass="width200" property="fdLevel" title="${ lfn:message('hr-organization:hrOrganizationDuty.fdCode') }">
		</list:data-column>
		<!-- 在职人数 -->
		<list:data-column headerClass="width200" col="fdNum" title="${ lfn:message('hr-organization:hrOrganizationPost.job.num') }" escape="false">
			<%
				SysOrganizationStaffingLevel staffingLevel = (SysOrganizationStaffingLevel)pageContext.getAttribute("sysOrganizationStaffingLevel");
				IHrStaffPersonInfoService hrStaffPersonInfoService = (IHrStaffPersonInfoService)SpringBeanUtil.getBean("hrStaffPersonInfoService");
				IHrStaffTrackRecordService hrStaffTrackRecordService = (IHrStaffTrackRecordService)SpringBeanUtil.getBean("hrStaffTrackRecordService");
				HQLInfo info = new HQLInfo();
				info.setWhereBlock("fdStaffingLevel.fdId=:fdStaffingLevelId and fdIsAvailable = true");
				info.setParameter("fdStaffingLevelId", staffingLevel.getFdId());
				info.setSelectBlock("count(fdId)");
				List list = hrStaffPersonInfoService.findList(info);
				
				info = new HQLInfo();
				info.setWhereBlock("fdStaffingLevel.fdId=:fdStaffingLevelId and fdStatus = '1' and fdType = '2'");
				info.setParameter("fdStaffingLevelId", staffingLevel.getFdId());
				info.setSelectBlock("count(fdId)");
				List trackRecordlist = hrStaffTrackRecordService.findList(info);
				int fdNum = ArrayUtil.isEmpty(list) ? 0 : Integer.parseInt(list.get(0).toString());
				int fdTrackNum = ArrayUtil.isEmpty(trackRecordlist) ? 0 : Integer.parseInt(trackRecordlist.get(0).toString());
				int total = fdNum + fdTrackNum;
				request.setAttribute("fdNum", total);
			%>
			${fdNum }
		</list:data-column>
		<list:data-column col="docCreator.name" escape="false" title="${lfn:message('hr-organization:hrOrganizationDuty.docCreator')}">
            <c:out value="${sysOrganizationStaffingLevel.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('hr-organization:hrOrganizationDuty.docCreateTime')}">
            <kmss:showDate value="${sysOrganizationStaffingLevel.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
		<!-- 其它操作 -->
		<c:if test="${hrToEkpEnable }">
				<kmss:auth requestURL="/hr/organization/hr_organization_staffing_level/hrOrganizationStaffingLevel.do?method=add">
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="lui_hr_link_group">
					<!-- 编辑 -->
					<span class="lui_hr_link_item ">
						<a class="lui_text_primary" href="javascript:editStaffingLevel('${sysOrganizationStaffingLevel.fdId}')">${lfn:message('button.edit')}</a>
					</span>
					<!-- 删除 -->
					<c:if test="${fdNum < 1}">
						<span class="lui_hr_link_item ">
							<a class="lui_text_primary" href="javascript:delStaffingLevel('${sysOrganizationStaffingLevel.fdId}')">${lfn:message('button.delete')}</a>
		                </span>
	                </c:if>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
				</kmss:auth>
		</c:if>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>