<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.hr.staff.model.HrStaffPersonInfo"%>
<%@page import="com.landray.kmss.common.dao.HQLInfo"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="hrStaffPersonInfo" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="message" title="人员信息" escape="false" styleClass="hr_tb_align_left" headerClass="width200 hr_th_align_left">
        	<div class="lui_hr_list_info">
        		<p class="lui_hr_list_info_main">
        			<span>
        				<c:out value="${hrStaffPersonInfo.fdName }" />
        			</span>
        			<span class="mtr15 lui_hr_assist_txt">
        				<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdStaffNo"/>：
        				<c:choose>
							<c:when  test="${not empty hrStaffPersonInfo.fdStaffNo}">
								${hrStaffPersonInfo.fdStaffNo}
							</c:when>
							<c:otherwise>
								暂无工号
							</c:otherwise>
						</c:choose>
                    </span>
                </p>
                <c:if test="${not empty hrStaffPersonInfo.fdLeavelOrgParentsName }">
	                <p class="lui_hr_list_info_desc">
	                	<c:out value="${hrStaffPersonInfo.fdLeavelOrgParentsName }" />
	                </p>
                </c:if>
            </div>
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
		<list:data-column col="fdName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdName') }" escape="false">
			<span class="com_subject"><c:out value="${hrStaffPersonInfo.fdName}" /></span>
		</list:data-column>
		<!--账号-->
		<list:data-column col="fdLoginName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdLoginName') }"> 
		     <c:if test="${hrStaffPersonInfo.fdOrgPerson != null}">
	    		${hrStaffPersonInfo.fdOrgPerson.fdLoginName}
	    	</c:if>
		</list:data-column>
		<!--到本单位时间-->
		<list:data-column col="fdTimeOfEnterprise" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise') }">
		    <kmss:showDate value="${hrStaffPersonInfo.fdTimeOfEnterprise}" type="date" /> 
		</list:data-column>
		<!--部门-->
		<list:data-column col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			${hrStaffPersonInfo.fdOrgParentsName}
		</list:data-column>
		<!--工号-->
		<list:data-column property="fdStaffNo" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffNo') }">
		</list:data-column>
		<!--员工状态-->
		<list:data-column col="fdStatus" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }" escape="false" styleClass="width80 hr_tb_align_left" headerClass="hr_th_align_left">
			<p class="lui_hr_list_info_main">
        	 	<span class="lui_hr_status_${hrStaffPersonInfo.fdStatus}">
        	 		<sunbor:enumsShow enumsType="hrStaffPersonInfo_fdStatus" value="${hrStaffPersonInfo.fdStatus }"></sunbor:enumsShow>
        	 	</span>
        	 </p>
        	 <c:if test="${not empty hrStaffPersonInfo.fdEntryTime }">
	             <p class="lui_hr_list_info_desc">
	             	<span class="lui_hr_assist_txt">
	             		<kmss:showDate value="${hrStaffPersonInfo.fdEntryTime }" type="date"/>入职
	             	</span>
	             </p>
             </c:if>
		</list:data-column>
		<list:data-column col="fdLeaveStatus" title="${lfn:message('hr-ratify:hrRatifyLeave.fdLeaveStatus') }">
        	<sunbor:enumsShow enumsType="hr_ratify_leave_status" value="${hrStaffPersonInfo.fdLeaveStatus }"></sunbor:enumsShow>
        </list:data-column>
		<!--岗位-->
		<list:data-column col="fdOrgPostNames" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }">
			<c:forEach items="${ hrStaffPersonInfo.fdOrgPosts }" varStatus="vstatus" var = "post">
			${post.fdName};
			</c:forEach>
		</list:data-column>
		<!--入职时间-->
		<list:data-column col="fdEntryTime" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdEntryTime') }">
		    <kmss:showDate value="${hrStaffPersonInfo.fdEntryTime}" type="date" /> 
		</list:data-column>
		<!--试用期限-->
		<list:data-column col="fdTrialOperationPeriod" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdTrialOperationPeriod') }">
		    ${hrStaffPersonInfo.fdTrialOperationPeriod}
		</list:data-column>
		<!-- 司龄 -->
		<list:data-column col="fdWorkingYears" title="司龄" headerClass="width80">
		    ${hrStaffPersonInfo.fdWorkingYears}
		</list:data-column>
		<!-- 离职日期 -->
		<list:data-column col="fdLeaveTime" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdLeaveTime') }">
		    <kmss:showDate value="${hrStaffPersonInfo.fdLeaveTime}" type="date" />
		</list:data-column>
		<!-- 离职原因 -->
		<list:data-column col="fdLeaveReason" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdLeaveReason') }">
		    ${hrStaffPersonInfo.fdLeaveReason}
		</list:data-column>
		<!-- 离职去向 -->
		<list:data-column col="fdNextCompany" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdNextCompany') }">
		    ${hrStaffPersonInfo.fdNextCompany}
		</list:data-column>
       	<!-- 其它操作 -->
		<list:data-column col="operations" title="${ lfn:message('list.operation') }" escape="false" headerClass="width200">
			<!--操作按钮 开始-->
			<div class="lui_hr_link_group">
				<div class="conf_btn_edit">
					<!-- 撤销离职 -->
					<span class="lui_hr_link_item ">
						<a class="lui_text_primary" href="javascript:cancelLeave('${hrStaffPersonInfo.fdId}','${hrStaffPersonInfo.fdName}')">撤销离职</a>
					</span>
					<!-- 发起返聘流程 -->
					<span class="lui_hr_link_item ">
						<a class="lui_text_primary" href="javascript:addRatifyRehire('${hrStaffPersonInfo.fdId}')">发起返聘流程</a>
					</span>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
       	
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>