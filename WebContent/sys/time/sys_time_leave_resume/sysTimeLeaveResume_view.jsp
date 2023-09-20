<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.landray.kmss.util.StringUtil,com.landray.kmss.sys.time.forms.SysTimeLeaveResumeForm,com.landray.kmss.sys.time.util.SysTimeUtil" %>

<template:include ref="default.view" sidebar="auto">
	<template:replace name="title">
		${ lfn:message('sys-time:table.sysTimeLeaveResume') }
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:if test="${sysTimeLeaveResumeForm.fdOprStatus eq '1'}">
				<kmss:ifModuleExist path="/sys/attend">
					<kmss:auth requestURL="/sys/time/sys_time_leave_resume/sysTimeLeaveResume.do?method=updateAttend">
						<ui:button text="${lfn:message('sys-time:sysTimeLeaveDetail.updateAttend')}" onclick="updateAttend('${sysTimeLeaveResumeForm.fdId }');" order="2">
						</ui:button>
					</kmss:auth>
				</kmss:ifModuleExist>
			</c:if>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content"> 
		<div class='lui_form_title_frame' style="padding-top: 10px;">
			<div class='lui_form_subject'>
				${ lfn:message('sys-time:table.sysTimeLeaveResume') }
			</div>
			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class="tb_normal" width=100% style="margin-bottom: 10px">
					<tr>
						<%-- 人员 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdPerson') }
						</td>
						<td colspan="3">
							<c:out value="${sysTimeLeaveResumeForm.fdPersonName }"/>
						</td>
					</tr>
					<tr>
						<%-- 请假开始时间 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdStartTime') }
						</td>
						<td>
							<c:choose>
								<c:when test="${sysTimeLeaveResumeForm.fdDetailStatType eq '1'}">
									<xform:datetime property="fdStartTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveDetailForm.fdStartTime }">
									</xform:datetime>
								</c:when>
								<c:when test="${sysTimeLeaveResumeForm.fdDetailStatType eq '2'}">
									<xform:datetime property="fdStartTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveDetailForm.fdStartTime }">
									</xform:datetime>
									<c:if test="${sysTimeLeaveResumeForm.fdStartNoon eq '1' }">
										${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }
									</c:if>
									<c:if test="${sysTimeLeaveResumeForm.fdStartNoon eq '2' }">
										${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }
									</c:if>
								</c:when>
								<c:otherwise>
									<xform:datetime property="fdStartTime" dateTimeType="datetime" showStatus="view" value="${sysTimeLeaveDetailForm.fdStartTime }">
									</xform:datetime>
								</c:otherwise>
							</c:choose>
						</td>
						<%-- 请假结束时间 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdEndTime') }
						</td>
						<td>
							<c:choose>
								<c:when test="${sysTimeLeaveResumeForm.fdDetailStatType eq '1'}">
									<xform:datetime property="fdEndTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveDetailForm.fdEndTime }">
									</xform:datetime>
								</c:when>
								<c:when test="${sysTimeLeaveResumeForm.fdDetailStatType eq '2'}">
									<xform:datetime property="fdEndTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveDetailForm.fdEndTime }">
									</xform:datetime>
									<c:if test="${sysTimeLeaveResumeForm.fdEndNoon eq '1' }">
										${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }
									</c:if>
									<c:if test="${sysTimeLeaveResumeForm.fdEndNoon eq '2' }">
										${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }
									</c:if>
								</c:when>
								<c:otherwise>
									<xform:datetime property="fdEndTime" dateTimeType="datetime" showStatus="view" value="${sysTimeLeaveDetailForm.fdEndTime }">
									</xform:datetime>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<%-- 时长--%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdLeaveTime') }
						</td>
						<td colspan="3">
							<fmt:formatNumber value="${sysTimeLeaveResumeForm.fdLeaveTime}" pattern="#.###"/>${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveTime.day') }
						</td>
					</tr>
					<tr>
						<%-- 扣减方式 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdOprType') }
						</td>
						<td>
							<c:if test="${sysTimeLeaveResumeForm.fdOprType eq '1' }">
								${ lfn:message('sys-time:sysTimeLeaveResume.fdOprType.review') }
							</c:if>
							<c:if test="${sysTimeLeaveResumeForm.fdOprType eq '2' }">
								${ lfn:message('sys-time:sysTimeLeaveResume.fdOprType.manual') }
							</c:if>
						</td>
						<%-- 扣减情况 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdOprStatus') }
						</td>
						<td>
							<c:if test="${sysTimeLeaveResumeForm.fdOprStatus eq '0'  || sysTimeLeaveResumeForm.fdOprStatus eq null}">
								${ lfn:message('sys-time:sysTimeLeaveResume.fdOprStatus.no') }
							</c:if>
							<c:if test="${sysTimeLeaveResumeForm.fdOprStatus eq '1'}">
								${ lfn:message('sys-time:sysTimeLeaveResume.fdOprStatus.success') }
							</c:if>
							<c:if test="${sysTimeLeaveResumeForm.fdOprStatus eq '2'}">
								<span style="color: red;">
									${ lfn:message('sys-time:sysTimeLeaveResume.fdOprStatus.fail') }。
									${ lfn:message('sys-time:sysTimeLeaveDetail.reason') }：
									${sysTimeLeaveResumeForm.fdOprDesc }
								</span>
							</c:if>
						</td>
					</tr>
					<tr>
						<%-- 关联请假明细 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveResume.fdLeaveDetail') }
						</td>
						<td colspan="3">
							<table class="tb_normal" width=100%>
								<tr>
									<td class="td_normal_title">
										${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveName') }
									</td>
									<td class="td_normal_title">
										${ lfn:message('sys-time:sysTimeLeaveDetail.fdStartTime') }
									</td>
									<td class="td_normal_title">
										${ lfn:message('sys-time:sysTimeLeaveDetail.fdEndTime') }
									</td>
								</tr>
								<tr>
									<td>
										<c:out value="${sysTimeLeaveResumeForm.fdDetailName }"/>
									</td>
									<td>
										<c:choose>
											<c:when test="${sysTimeLeaveResumeForm.fdDetailStatType eq '1'}">
												<xform:datetime property="fdStartTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveResumeForm.fdDetailStartTime }">
												</xform:datetime>
											</c:when>
											<c:when test="${sysTimeLeaveResumeForm.fdDetailStatType eq '2'}">
												<xform:datetime property="fdStartTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveResumeForm.fdDetailStartTime }">
												</xform:datetime>
												<c:if test="${sysTimeLeaveResumeForm.fdDetailStartNoon eq '1' }">
													${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }
												</c:if>
												<c:if test="${sysTimeLeaveResumeForm.fdDetailStartNoon eq '2' }">
													${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }
												</c:if>
											</c:when>
											<c:otherwise>
												<xform:datetime property="fdStartTime" dateTimeType="datetime" showStatus="view" value="${sysTimeLeaveResumeForm.fdDetailStartTime }">
												</xform:datetime>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${sysTimeLeaveResumeForm.fdDetailStatType eq '1'}">
												<xform:datetime property="fdEndTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveResumeForm.fdDetailEndTime }">
												</xform:datetime>
											</c:when>
											<c:when test="${sysTimeLeaveResumeForm.fdDetailStatType eq '2'}">
												<xform:datetime property="fdEndTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveResumeForm.fdDetailEndTime }">
												</xform:datetime>
												<c:if test="${sysTimeLeaveResumeForm.fdDetailEndNoon eq '1' }">
													${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }
												</c:if>
												<c:if test="${sysTimeLeaveResumeForm.fdDetailEndNoon eq '2' }">
													${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }
												</c:if>
											</c:when>
											<c:otherwise>
												<xform:datetime property="fdEndTime" dateTimeType="datetime" showStatus="view" value="${sysTimeLeaveResumeForm.fdDetailEndTime }">
												</xform:datetime>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<%-- 关联流程 1--%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdReview') }
						</td>
						<td colspan="3">
							<c:if test="${not empty sysTimeLeaveResumeForm.fdReviewId }">
								<a href="${LUI_ContextPath }/km/review/km_review_main/kmReviewMain.do?method=view&fdId=${sysTimeLeaveResumeForm.fdReviewId }" target="_blank">
									${sysTimeLeaveResumeForm.fdReviewName }
								</a>
							</c:if>
						</td>
					</tr>
					<kmss:ifModuleExist path="/sys/attend">
					<tr>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdIsUpdateAttend') }
						</td>
						<td colspan="3">
							<c:if test="${sysTimeLeaveResumeForm.fdIsUpdateAttend }">
								${ lfn:message('sys-time:sysTimeLeaveDetail.fdIsUpdateAttend.yes') }
							</c:if>
							<c:if test="${sysTimeLeaveResumeForm.fdIsUpdateAttend eq null || !sysTimeLeaveResumeForm.fdIsUpdateAttend }">
								${ lfn:message('sys-time:sysTimeLeaveDetail.fdIsUpdateAttend.no') }
							</c:if>
						</td>
					</tr>
					</kmss:ifModuleExist>
					<tr>
						<%-- 录入人员 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.docCreator') }
						</td>
						<td>
							${sysTimeLeaveResumeForm.docCreatorName }
						</td>
						<%-- 录入时间 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.docCreateTime') }
						</td>
						<td>
							${sysTimeLeaveResumeForm.docCreateTime }
						</td>
					</tr>
				</table>
			</div>
		</div>
		<script type="text/javascript">
		seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
			window.updateAttend = function(id){
				window.loading = dialog.loading();
				$.post('<c:url value="/sys/time/sys_time_leave_resume/sysTimeLeaveResume.do?method=updateAttend"/>&fdId=' + id, 
						null,function(data){
					if(window.loading!=null)
						window.loading.hide();
					if(data) {
						dialog.success('<bean:message key="return.optSuccess" />', null, function(){
							topic.publish("list.refresh");
						});
					} else {
						dialog.failure('<bean:message key="return.optFailure" />', null, function(){
							topic.publish("list.refresh");
						});
					}
				},'json');
			};
		});
		</script>
	</template:replace>
</template:include>