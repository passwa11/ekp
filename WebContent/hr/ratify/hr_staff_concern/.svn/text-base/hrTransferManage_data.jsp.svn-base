<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="personInfo" list="${queryPage.list }" varIndex="status">
		<list:data-column col="index">
			${status+1}
		</list:data-column>
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="personId" >
		</list:data-column>
		<!-- 姓名-->
		<list:data-column col="fdName" title="${ lfn:message('hr-ratify:hrRatify.concern.person.info') }" escape="false" styleClass="hr_tb_align_left" headerClass="width200 hr_th_align_left">
			<%-- <span class="com_subject"><c:out value="${personInfo.fdName}" /></span> --%>
			<div class="lui_hr_list_info">
				<p class="lui_hr_list_info_main">
					<span>${lfn:escapeHtml(personInfo.fdName)}</span>
					<span class="mtr15 lui_hr_assist_txt">
						工号：
						<c:choose>
							<c:when  test="${not empty personInfo.fdStaffNo}">
								${personInfo.fdStaffNo}
							</c:when>
							<c:otherwise>
								暂无工号
							</c:otherwise>
						</c:choose>
					</span>
				</p>
				<p class="lui_hr_list_info_desc">${personInfo.fdTransferAfter}</p>
			</div>
		</list:data-column>
		<list:data-column col="fdArrow" escape="false" headerClass="width30">
				<span class="lui_hr_arrow_right"></span>
		</list:data-column>
		<!-- 异动内容 -->
		<list:data-column col="fdTransferInfo" title="${ lfn:message('hr-ratify:hrRatifyEntry.changeContent') }" escape="false" headerClass="hr_th_align_left">
				<div class="lui_hr_transfer">
				<div class="lui_hr_transfer_title">
					<c:choose>
						<c:when test="${personInfo.fdTransferType eq '1'}">
							${ lfn:message('hr-ratify:hrRatify.concern.salary') }
						</c:when>
						<c:otherwise>
							${ lfn:message('hr-ratify:hrRatify.concern.transfer') }
						</c:otherwise>
					</c:choose>
				</div>
				<div class="lui_hr_transfer_info">
					<div class="lui_hr_transfer_detailed lui_text_primary">${personInfo.fdTransferBefore}</div>
					<div class="lui_hr_transfer_annual_salary">${personInfo.fdSalaryAfter}</div>
				</div>
			</div>
		</list:data-column>

		<!--工号-->
		<list:data-column property="fdStaffNo" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffNo') }">
		</list:data-column>
		<!--部门-->
		<list:data-column col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			${personInfo.fdDeptName}
		</list:data-column>
		<!--岗位-->
		<list:data-column col="fdOrgPostNames" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }">
			${personInfo.fdOrgPostNames}
		</list:data-column>
		<!--异动类型-->
		<list:data-column col="fdTransferType" title="${ lfn:message('hr-ratify:hrRatify.concern.transfer.fdTransferType') }">
			${personInfo.fdTransferType}
		</list:data-column>
		<!--生效日期-->
		<list:data-column col="fdEffectTime" title="${ lfn:message('hr-ratify:hrRatify.concern.transfer.fdEffectTime') }">
			<c:out value="${personInfo.fdEffectTime}"/>
		</list:data-column>
		<!--异动决策状态-->
		<list:data-column col="docStatus" title="${ lfn:message('hr-ratify:hrRatify.concern.transfer.flow.docStatus') }">

			<c:choose>
				<c:when test="${personInfo.docStatus >= '30'}">
					${ lfn:message('hr-staff:hrStaff.transfer.flow.status.over') }
				</c:when>
				<c:when test="${personInfo.docStatus >= '10' && personInfo.docStatus < '30'}">
					${ lfn:message('hr-staff:hrStaff.transfer.flow.status.ing') }
				</c:when>
				<c:otherwise>
					${ lfn:message('hr-staff:hrStaff.transfer.flow.status.reset') }
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!--异动状态-->
		<list:data-column col="fdIsEffective" title="${ lfn:message('hr-ratify:hrRatify.concern.transfer.docStatus') }">
			<c:choose>
				<c:when test="${not empty personInfo.fdIsEffective}">
					<c:choose>
						<c:when test="${personInfo.fdIsEffective =='false'}">
							${ lfn:message('hr-staff:hrStaff.transfer.fdIsEffective.false') }
						</c:when>
						<c:otherwise>
							${ lfn:message('hr-staff:hrStaff.transfer.fdIsEffective.true') }
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="${ personInfo.docStatus < '30'}">
					${ lfn:message('hr-staff:hrStaff.transfer.fdIsEffective.false') }
				</c:when>
				<c:otherwise>
					${ lfn:message('hr-staff:hrStaff.transfer.fdIsEffective.true') }
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="docNumber" title="${ lfn:message('hr-ratify:hrRatify.concern.transfer.docNumber') }" escape="false">
			<c:choose>
				<c:when test="${not empty personInfo.docNumber}">
					<c:out value="${personInfo.docNumber}"></c:out>
				</c:when>
				<c:otherwise>
					<无>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="handlerName" title="${ lfn:message('hr-ratify:hrRatify.concern.transfer.handlerName') }" escape="false">
			<c:choose>
				<c:when test="${empty personInfo.fdCreator}">
					<kmss:showWfPropertyValues  var="handlerValue" idValue="${personInfo.fdId}" propertyName="handlerName" />
					<div class="textEllipsis width100" style="font-weight:bold;" title="${handlerValue}">
						<c:out value="${handlerValue}"></c:out>
					</div>
				</c:when>
				<c:otherwise>
					<c:out value="${personInfo.fdCreator}"></c:out>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<div class="lui_hr_link_group">
				<c:choose>
					<c:when test="${personInfo.delSalarykMark == 'true'}">
						<c:if test="${personInfo.fdIsEffective =='false' }">
							<span class="lui_hr_link_item ">
								<a class="lui_text_primary" href="javascript:delSalary('${personInfo.personId}','${personInfo.fdId}')">${ lfn:message('hr-staff:hr.staff.btn.revoke') }</a>
							</span>
						</c:if>
						<kmss:auth requestURL="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=${personInfo.personId}">
							<span class="lui_hr_link_item ">
								<a class="lui_text_primary" href="javascript:openPersonInfo('${personInfo.personId}')">${ lfn:message('hr-staff:hrStaffPersonInfo.viewProfile') }</a>
							</span>
						</kmss:auth>
						<c:if test="${personInfo.fdIsEffective =='false' }">
							<span class="lui_hr_link_item ">
								<a class="lui_text_primary" href="javascript:editSalary('${personInfo.fdId}','${personInfo.personId}')">${ lfn:message('hr-staff:hr.staff.btn.edit') }</a>
							</span>
						</c:if>
					</c:when>
					<c:when test="${personInfo.delTrackMark == 'true'}">

						<c:if test="${personInfo.fdIsEffective =='false' }">
							<span class="lui_hr_link_item ">
								<a class="lui_text_primary" href="javascript:delTrack('${personInfo.personId}','${personInfo.fdId}')">${ lfn:message('hr-staff:hr.staff.btn.revoke') }</a>
							</span>
						</c:if>
						<kmss:auth requestURL="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=${personInfo.personId}">
							<span class="lui_hr_link_item ">
								<a class="lui_text_primary" href="javascript:openPersonInfo('${personInfo.personId}')">${ lfn:message('hr-staff:hrStaffPersonInfo.viewProfile') }</a>
							</span>
						</kmss:auth>
						<c:if test="${personInfo.fdIsEffective =='false' }">
							<span class="lui_hr_link_item ">
								<a class="lui_text_primary" href="javascript:editHrStaffTrackRecord('${personInfo.fdId}','${personInfo.personId}')">${ lfn:message('hr-staff:hr.staff.btn.edit') }</a>
							</span>
						</c:if>
					</c:when>
					<c:otherwise>
						<kmss:auth requestURL="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=${personInfo.personId}">
							<span class="lui_hr_link_item ">
								<a class="lui_text_primary" href="javascript:openPersonInfo('${personInfo.personId}')">${ lfn:message('hr-staff:hrStaffPersonInfo.viewProfile') }</a>
							</span>
						</kmss:auth>
						<c:choose>
							<c:when test="${personInfo.fdTransferType == '1'}">
								<kmss:auth requestURL="/hr/ratify/hr_ratify_salary/hrRatifySalary.do?method=view&fdId=${personInfo.fdId}">
									<span class="lui_hr_link_item ">
										<a class="lui_text_primary" href="javascript:findFlow('${personInfo.fdId}', '${personInfo.fdTransferType}')">${ lfn:message('hr-staff:hrStaffPersonInfo.viewProcessInfo') }</a>
									</span>
								</kmss:auth>
							</c:when>
							<c:otherwise>
								<kmss:auth requestURL="/hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=view&fdId=${personInfo.fdId}">
									<span class="lui_hr_link_item ">
										<a class="lui_text_primary" href="javascript:findFlow('${personInfo.fdId}', '${personInfo.fdTransferType}')">${ lfn:message('hr-staff:hrStaffPersonInfo.viewProcessInfo') }</a>
									</span>
								</kmss:auth>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</div>
		</list:data-column>


	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>