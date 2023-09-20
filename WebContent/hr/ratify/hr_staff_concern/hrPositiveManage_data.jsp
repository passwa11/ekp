<%@page import="com.landray.kmss.hr.ratify.model.HrRatifyPositive"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.hr.staff.model.HrStaffPersonInfo"%>
<%@page import="com.landray.kmss.common.dao.HQLInfo"%>
<%@page import="com.landray.kmss.hr.ratify.service.IHrRatifyPositiveService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
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
		<list:data-column col="fdName" title="${ lfn:message('hr-ratify:hrRatify.concern.person.info') }" escape="false" styleClass="hr_tb_align_left" headerClass="width250">
			<%-- <span class="com_subject"><c:out value="${hrStaffPersonInfo.fdName}" /></span> --%>
			 <div class="lui_hr_list_info">
                <p class="lui_hr_list_info_main">
                	<span>${hrStaffPersonInfo.fdName}</span> 
                	<span class="mtr15 lui_hr_assist_txt">
                		工号：
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
                <p class="lui_hr_list_info_desc">${hrStaffPersonInfo.fdOrgParentsName}</p>
            </div>
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
		<list:data-column col="fdStatus" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }" escape="false" styleClass="hr_tb_align_left" headerClass="width80 hr_th_align_left">
			<%-- <sunbor:enumsShow value="${ hrStaffPersonInfo.fdStatus }" enumsType="hrStaffPersonInfo_fdStatus" /> --%>
			<div class="lui_hr_entry_status lui_hr_status_${hrStaffPersonInfo.fdStatus}">
				<sunbor:enumsShow value="${ hrStaffPersonInfo.fdStatus }" enumsType="hrStaffPersonInfo_fdStatus" />
			</div>
			<div class="lui_hr_entry_time lui_hr_assist_txt" style="width: 95px;">
				<c:choose>
					<c:when  test="${not empty hrStaffPersonInfo.fdEntryTime}">
						<kmss:showDate value="${hrStaffPersonInfo.fdEntryTime}" type="date"></kmss:showDate> 入职
					</c:when>
					<c:otherwise>
						${ lfn:message('hr-ratify:hrRatify.concern.zanwuruzhiriqi') }
					</c:otherwise>
				</c:choose>
			</div>
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
		<!--转正时间-->
		<list:data-column col="fdPositiveTime" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdPositiveTime') }">
		    <kmss:showDate value="${hrStaffPersonInfo.fdPositiveTime}" type="date" /> 
		</list:data-column>
		<%
			String fdId = null;
			HrStaffPersonInfo hrStaffPersonInfo = ((HrStaffPersonInfo)pageContext.getAttribute("hrStaffPersonInfo"));
			if(null != hrStaffPersonInfo.getFdOrgPerson()){
				fdId = hrStaffPersonInfo.getFdOrgPerson().getFdId();
			}else{
				fdId = hrStaffPersonInfo.getFdId();
			}
		    IHrRatifyPositiveService hrRatifyPositiveService = (IHrRatifyPositiveService)SpringBeanUtil.getBean("hrRatifyPositiveService");
		    HQLInfo hqlInfo = new HQLInfo();
		    hqlInfo.setWhereBlock("hrRatifyPositive.fdPositiveStaff.fdId =:fdId");
		    hqlInfo.setParameter("fdId", fdId);
			HrRatifyPositive hrRatifyPositive = (HrRatifyPositive)hrRatifyPositiveService.findFirstOne(hqlInfo);
       		if(null != hrRatifyPositive){
				request.setAttribute("positiveId", hrRatifyPositive.getFdId());
				request.setAttribute("positiveDocNumber", hrRatifyPositive.getDocNumber());
       		}else{
       			request.setAttribute("positiveId", "");
       		}
       	%>
       	<c:choose>
       		<c:when test="${not empty positiveId }">
       			 <list:data-column col="positiveDocNumber" title="${ lfn:message('hr-staff:hrStaffEntry.regular.worker.no') }" escape="false">
		            <kmss:showWfPropertyValues var="handlerValue" idValue="${positiveId}" propertyName="handlerName" />
		            <c:out value="${positiveDocNumber}"></c:out>
		        </list:data-column>
		        <list:data-column col="lbpm_main_listcolumn_node" title="${ lfn:message('hr-staff:hrStaffEntry.auditStatus') }" escape="false">
		            <kmss:showWfPropertyValues var="nodevalue" idValue="${positiveId}" propertyName="nodeName" />
		             <c:out value="${nodevalue}"></c:out>
		        </list:data-column>
       		</c:when>
       		<c:otherwise>
		        <list:data-column col="positiveDocNumber" title="${ lfn:message('hr-staff:hrStaffEntry.regular.worker.no') }" escape="false">
		          	<c:out value="${ lfn:message('hr-staff:hrStaff.employee.resume.no') }"></c:out>
		        </list:data-column>
		        <list:data-column col="lbpm_main_listcolumn_node" title="${ lfn:message('hr-staff:hrStaffEntry.auditStatus') }" escape="false">
		            <c:out value="${ lfn:message('hr-staff:hrStaff.employee.resume.no') }"></c:out>
		        </list:data-column>
       		</c:otherwise>
       	</c:choose>
       		<!-- 其它操作 -->
		<list:data-column col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="lui_hr_link_group">
					<c:choose>
						<c:when test="${not empty positiveId }">
							<span class="lui_hr_link_item">
			                    <a class="lui_text_primary" href="javascript:openPersonInfo('${hrStaffPersonInfo.fdId}')">${ lfn:message('hr-staff:hrStaffPersonInfo.viewProfile') }</a>
			                </span>
							<!-- 查看流程详情 -->
							<span class="lui_hr_link_item ">
			                     <a class="lui_text_primary" href="javascript:findFlow('${positiveId}')">${ lfn:message('hr-staff:hrStaffPersonInfo.viewProcessDetails') }</a>
			                </span>
						</c:when>
						<c:otherwise>
							<span class="lui_hr_link_item ">
			                     <a class="lui_text_primary" href="javascript:openPersonInfo('${hrStaffPersonInfo.fdId}')">${ lfn:message('hr-staff:hrStaffPersonInfo.viewProfile') }</a>
			                </span>
							<!-- 办理转正 -->
							<span class="lui_hr_link_item ">
			                     <a class="lui_text_primary" href="javascript:positive('${hrStaffPersonInfo.fdId}')">${ lfn:message('hr-staff:hrStaffPersonInfo.applyConfirmation') }</a>
			                </span>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
       	
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>