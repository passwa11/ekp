<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.hr.ratify.model.HrRatifyAgendaConfig"%>
<%
	HrRatifyAgendaConfig agendaConfig = new HrRatifyAgendaConfig();
	boolean fdLeaveManage = "true".equals(agendaConfig.getFdLeaveManage());
	pageContext.setAttribute("fdLeaveManage", fdLeaveManage);
%>
<list:data>
    <list:data-columns var="hrRatifyLeave" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column property="fdOrgPersonId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column col="message" title="人员信息" escape="false" styleClass="hr_tb_align_left" headerClass="width200 hr_th_align_left">
        	<div class="lui_hr_list_info">
        		<p class="lui_hr_list_info_main">
        			<span>
        				<c:out value="${hrRatifyLeave.fdStaffName }" />
        			</span>
        			<span class="mtr15 lui_hr_assist_txt">
        				<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdStaffNo"/>：
        				<c:choose>
							<c:when  test="${not empty hrRatifyLeave.fdStaffNo}">
								${hrRatifyLeave.fdStaffNo}
							</c:when>
							<c:otherwise>
								暂无工号
							</c:otherwise>
						</c:choose>
                    </span>
                </p>
                <c:if test="${not empty hrRatifyLeave.fdDeptName }">
	                <p class="lui_hr_list_info_desc">
	                	<c:out value="${hrRatifyLeave.fdDeptName }" />
	                </p>
                </c:if>
            </div>
        </list:data-column>
        <list:data-column col="fdStaffStatus" title="员工状态" escape="false" styleClass="hr_tb_align_left" headerClass="width100 hr_th_align_left">
        	 <p class="lui_hr_list_info_main">
        	 	<span class="lui_hr_status_${hrRatifyLeave.fdStaffStatus}">
        	 		<sunbor:enumsShow enumsType="hrStaffPersonInfo_fdStatus" value="${hrRatifyLeave.fdStaffStatus }"></sunbor:enumsShow>
        	 	</span>
        	 </p>
        	 <c:if test="${not empty hrRatifyLeave.fdEntryTime }">
	             <p class="lui_hr_list_info_desc">
	             	<span class="lui_hr_assist_txt">
	             		<c:out value="${hrRatifyLeave.fdEntryTime }" />入职
	             	</span>
	             </p>
             </c:if>
        </list:data-column>
        <list:data-column col="fdStaffName" title="姓名">
        	<c:out value="${hrRatifyLeave.fdStaffName }" />
        </list:data-column>
        <list:data-column col="fdStaffNo" title="工号">
        	<c:out value="${hrRatifyLeave.fdStaffNo }" />
        </list:data-column>
        <list:data-column col="fdDeptName" title="部门">
        	<c:out value="${hrRatifyLeave.fdDeptName }" />
        </list:data-column>
        <list:data-column col="fdPosts" title="岗位">
        	<c:out value="${hrRatifyLeave.fdPosts }" />
        </list:data-column>
        <list:data-column col="fdLeaveStatus" title="${lfn:message('hr-ratify:hrRatifyLeave.fdLeaveStatus') }" headerClass="width80">
        	<sunbor:enumsShow enumsType="hr_ratify_leave_status" value="${hrRatifyLeave.fdLeaveStatus }"></sunbor:enumsShow>
        </list:data-column>
        <list:data-column col="fdEntryTime" title="入职日期">
        	<c:out value="${hrRatifyLeave.fdEntryTime }" />
        </list:data-column>
        <list:data-column col="fdLeaveRealDate" title="实际离职日期" headerClass="width100">
        	<c:out value="${hrRatifyLeave.fdLeaveRealDate }" />
        </list:data-column>
        <list:data-column col="docNumber" title="离职审批号" escape="false">
        	<c:if test="${not empty hrRatifyLeave.docNumber }">
        		<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/hr/ratify/hr_ratify_leave/hrRatifyLeave.do?method=view&fdId=${hrRatifyLeave.fdId}" target="_blank"><c:out value="${hrRatifyLeave.docNumber }" /></a>
        	</c:if>
        </list:data-column>
        <list:data-column col="fdCompanyAge" title="司龄" headerClass="width80">
        	<c:out value="${hrRatifyLeave.fdCompanyAge }" />
        </list:data-column>
        <list:data-column col="fdLeaveDate" title="原定离职日期" headerClass="width100">
        	<c:out value="${hrRatifyLeave.fdLeaveDate }" />
        </list:data-column>
        <list:data-column col="fdLeaveType" title="离职原因" headerClass="width80">
        	<c:out value="${hrRatifyLeave.fdLeaveType }" />
        </list:data-column>
        <list:data-column col="fdNextCompany" title="离职去向">
        	<c:out value="${hrRatifyLeave.fdNextCompany }" />
        </list:data-column>
        <!-- 其它操作 -->
		<list:data-column headerClass="width200" col="wait-operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="lui_hr_link_group">
                <!-- 确认离职 -->
				<%-- <c:if test="${fdLeaveManage }">
	                <span class="lui_hr_link_item ">
	                    <a class="lui_text_primary" onclick="Com_OpenNewWindow(this)" data-href="javascript:checkLeave('${hrRatifyLeave.fdStaffId}','${hrRatifyLeave.fdId}')">确认离职</a>
	                </span>
	            </c:if> --%>
	            <span class="lui_hr_link_item">
                    <a class="lui_text_primary" href="javascript:openPersonInfo('${hrRatifyLeave.fdOrgPersonId}')">查看档案</a>
                </span>
                <c:if test="${not empty hrRatifyLeave.notProcess}">
	                <span class="lui_hr_link_item ">
	                    <a class="lui_text_primary" href="javascript:editLeaveInfo('${hrRatifyLeave.fdOrgPersonId}')">编辑</a>
	                </span>
	                <span class="lui_hr_link_item">
                    	<a class="lui_text_primary" href="javascript:abandonLeave('${hrRatifyLeave.fdStaffId}','${hrRatifyLeave.fdId}')">放弃离职</a>
                	</span>
	            </c:if>
            </div>
			<!--操作按钮 结束-->
		</list:data-column>
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="lui_hr_link_group">
				<div class="conf_btn_edit">
					<!-- 撤销离职 -->
					<span class="lui_hr_link_item ">
						<a class="lui_text_primary" href="javascript:cancelLeave('${hrRatifyLeave.fdOrgPersonId}','${hrRatifyLeave .fdStaffName}')">撤销离职</a>
					</span>
					<!-- 发起返聘流程 -->
					<span class="lui_hr_link_item ">
						<a class="lui_text_primary" href="javascript:addRatifyRehire('${hrRatifyLeave.fdStaffId}')">发起返聘流程</a>
					</span>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
		<list:data-column col="viewUrl">
			<c:if test="${hrRatifyLeave.modelName eq 'HrRatifyLeave'}">
				<c:out value="/hr/ratify/hr_ratify_leave/hrRatifyLeave.do?method=view&fdId=${hrRatifyLeave.fdId }"></c:out>
			</c:if>
			<c:if test="${hrRatifyLeave.modelName eq 'HrStaffPersonInfo' }">
				<c:out value="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=${hrRatifyLeave.fdId }"></c:out>
			</c:if>
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
