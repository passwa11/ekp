<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.landray.kmss.sys.time.util.SysTimeUtil,com.landray.kmss.util.StringUtil,com.landray.kmss.sys.time.forms.SysTimeLeaveDetailForm,java.lang.Float" %>
<%
	SysTimeLeaveDetailForm sysTimeLeaveDetailForm = (SysTimeLeaveDetailForm) request.getAttribute("sysTimeLeaveDetailForm");
	Float fdLeaveTime = Float.valueOf(sysTimeLeaveDetailForm.getFdLeaveTime());
//	Float convertTime = SysTimeUtil.getConvertTime();
//	if("3".equals(sysTimeLeaveDetailForm.getFdStatType())){
//		fdTotalTime = fdTotalTime/60;
//	}else{
//		fdTotalTime = convertTime * fdTotalTime/(60*24);
//	}
	pageContext.setAttribute("fdLeaveTime", fdLeaveTime);
//	pageContext.setAttribute("fdTotalTimeTxt", SysTimeUtil.formatLeaveTimeStr(0f, fdTotalTime));
%>

<template:include ref="default.view" sidebar="auto">
	<template:replace name="title">
		${ lfn:message('sys-time:table.sysTimeLeaveAmount') }
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:if test="${sysTimeLeaveDetailForm.fdOprStatus eq '2'}">
				<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=deduct">
					<ui:button text="${lfn:message('sys-time:sysTimeLeaveDetail.rededuct')}" onclick="rededuct('${sysTimeLeaveDetailForm.fdId }');" order="2">
					</ui:button>
				</kmss:auth>
			</c:if>
			<c:if test="${sysTimeLeaveDetailForm.fdOprStatus eq '1' || sysTimeLeaveDetailForm.fdCanUpdateAttend}">
				<kmss:ifModuleExist path="/sys/attend">
				<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=updateAttend">
					<ui:button text="${lfn:message('sys-time:sysTimeLeaveDetail.updateAttend')}" onclick="updateAttend('${sysTimeLeaveDetailForm.fdId }');" order="2">
					</ui:button>
				</kmss:auth>
				</kmss:ifModuleExist>
				<c:if test="${fdLeaveTime > 0}">
					<kmss:auth requestURL="/sys/time/sys_time_leave_resume/sysTimeLeaveResume.do?method=add">
						<ui:button text="${lfn:message('sys-time:sysTimeLeaveResume.resume')}" onclick="addResume('${sysTimeLeaveDetailForm.fdId }');" order="3">
						</ui:button>
					</kmss:auth>
				</c:if>
			</c:if>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content"> 
		<div class='lui_form_title_frame' style="padding-top: 10px;">
			<div class='lui_form_subject'>
				${ lfn:message('sys-time:table.sysTimeLeaveDetail') }
			</div>
			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class="tb_normal" width=100% style="margin-bottom: 10px">
					<tr>
						<%-- 人员 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdPerson') }
						</td>
						<td width="35%">
							<c:out value="${sysTimeLeaveDetailForm.fdPersonName }"/>
						</td>
						<%-- 请假类型 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveName') }
						</td>
						<td width="35%">
							<c:out value="${sysTimeLeaveDetailForm.fdLeaveName }"/>
						</td>
					</tr>
					<tr>
						<%-- 请假开始时间 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdStartTime') }
						</td>
						<td>
							<c:choose>
								<c:when test="${sysTimeLeaveDetailForm.fdStatType eq '1'}">
									<xform:datetime property="fdStartTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveDetailForm.fdStartTime }">
									</xform:datetime>
								</c:when>
								<c:when test="${sysTimeLeaveDetailForm.fdStatType eq '2'}">
									<xform:datetime property="fdStartTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveDetailForm.fdStartTime }">
									</xform:datetime>
									<c:if test="${sysTimeLeaveDetailForm.fdStartNoon eq '1' }">
										${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }
									</c:if>
									<c:if test="${sysTimeLeaveDetailForm.fdStartNoon eq '2' }">
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
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdEndTime') }
						</td>
						<td>
							<c:choose>
								<c:when test="${sysTimeLeaveDetailForm.fdStatType eq '1'}">
									<xform:datetime property="fdEndTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveDetailForm.fdEndTime }">
									</xform:datetime>
								</c:when>
								<c:when test="${sysTimeLeaveDetailForm.fdStatType eq '2'}">
									<xform:datetime property="fdEndTime" dateTimeType="date" showStatus="view" value="${sysTimeLeaveDetailForm.fdEndTime }">
									</xform:datetime>
									<c:if test="${sysTimeLeaveDetailForm.fdEndNoon eq '1' }">
										${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }
									</c:if>
									<c:if test="${sysTimeLeaveDetailForm.fdEndNoon eq '2' }">
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
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveTime') }
						</td>
						<td colspan="3">
							<fmt:formatNumber value="${sysTimeLeaveDetailForm.fdLeaveTime }" pattern="#.###"/>
							${lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveTime.day')}
						</td>
					</tr>
					<tr>
						<%-- 扣减方式 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType') }
						</td>
						<td>
							<c:if test="${sysTimeLeaveDetailForm.fdOprType eq '1' }">
								${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.review') }
							</c:if>
							<c:if test="${sysTimeLeaveDetailForm.fdOprType eq '2' }">
								${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.manual') }
							</c:if>
							<c:if test="${sysTimeLeaveDetailForm.fdOprType eq '3' }">
								${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.batch') }
							</c:if>
						</td>
						<%-- 扣减情况 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprDesc') }
						</td>
						<td>
							<c:if test="${sysTimeLeaveDetailForm.fdOprStatus eq '0'  || sysTimeLeaveDetailForm.fdOprStatus eq null}">
								${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.no') }
							</c:if>
							<c:if test="${sysTimeLeaveDetailForm.fdOprStatus eq '1'}">
								${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.success') }
							</c:if>
							<c:if test="${sysTimeLeaveDetailForm.fdOprStatus eq '2'}">
								<c:if test="${sysTimeLeaveDetailForm.fdCanUpdateAttend}">
									${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.no') }。
									${sysTimeLeaveDetailForm.fdOprDesc }
								</c:if>
								<c:if test="${sysTimeLeaveDetailForm.fdCanUpdateAttend eq null || !sysTimeLeaveDetailForm.fdCanUpdateAttend}">
									<span style="color: red;">
										${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.fail') }。
										${ lfn:message('sys-time:sysTimeLeaveDetail.reason') }：
										${sysTimeLeaveDetailForm.fdOprDesc }
									</span>
								</c:if>
							</c:if>
						</td>
					</tr>
					<kmss:ifModuleExist path="/sys/attend">
					<tr>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdIsUpdateAttend') }
						</td>
						<td colspan="3">
							<c:if test="${sysTimeLeaveDetailForm.fdIsUpdateAttend }">
								${ lfn:message('sys-time:sysTimeLeaveDetail.fdIsUpdateAttend.yes') }
							</c:if>
							<c:if test="${sysTimeLeaveDetailForm.fdIsUpdateAttend eq null || !sysTimeLeaveDetailForm.fdIsUpdateAttend }">
								${ lfn:message('sys-time:sysTimeLeaveDetail.fdIsUpdateAttend.no') }
							</c:if>
						</td>
					</tr>
					</kmss:ifModuleExist>
					<tr>
						<%-- 关联流程 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.fdReview') }
						</td>
						<td colspan="3">
							<c:if test="${not empty sysTimeLeaveDetailForm.fdReviewId }">
								<a href="${LUI_ContextPath }/km/review/km_review_main/kmReviewMain.do?method=view&fdId=${sysTimeLeaveDetailForm.fdReviewId }" target="_blank">
									${sysTimeLeaveDetailForm.fdReviewName }
								</a>
							</c:if>
						</td>
					</tr>
					<%-- 所属场所 --%>
				    <%if(ISysAuthConstant.IS_AREA_ENABLED){ %>
						<tr>
							<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field_single.jsp" charEncoding="UTF-8">
				                <c:param name="id" value="${sysTimeLeaveDetailForm.authAreaId}"/>
				            </c:import>
						</tr>
					<%} %>
					<tr>
						<%-- 录入人员 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.docCreator') }
						</td>
						<td>
							${sysTimeLeaveDetailForm.docCreatorName }
						</td>
						<%-- 录入时间 --%>
						<td width="15%" class="td_normal_title">
							${ lfn:message('sys-time:sysTimeLeaveDetail.docCreateTime') }
						</td>
						<td>
							${sysTimeLeaveDetailForm.docCreateTime }
						</td>
					</tr>
				</table>
				
				<ui:tabpage expand="false">
					<ui:content expand="true" title="${ lfn:message('sys-time:table.sysTimeLeaveResume') }">
						<list:listview id="listview">
							<ui:source type="AjaxJson">
								{url:'/sys/time/sys_time_leave_resume/sysTimeLeaveResume.do?method=list&detailId=${sysTimeLeaveDetailForm.fdId}&orderby=sysTimeLeaveResume.docCreateTime&ordertype=down'}
							</ui:source>
							<list:colTable isDefault="false" layout="sys.ui.listview.columntable"
								rowHref="/sys/time/sys_time_leave_resume/sysTimeLeaveResume.do?method=view&fdId=!{fdId}"  name="columntable">
								<list:col-serial></list:col-serial> 
								<list:col-auto props="fdPerson.fdName;fdStartTime;fdEndTime;fdLeaveTime;fdOprType;fdOprStatus;fdReview;operation;"></list:col-auto>
							</list:colTable>
						</list:listview> 
						<list:paging></list:paging>
					</ui:content>
				</ui:tabpage>
			</div>
		</div>
		<script type="text/javascript">
		seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
			topic.subscribe('successReloadPage', function() {
				topic.publish('list.refresh');
			});
			
			// 重新扣减
			window.rededuct = function(id){
				window.loading = dialog.loading();
				$.post('<c:url value="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=deduct"/>&fdId=' + id, 
						null,function(data){
					if(window.loading!=null)
						window.loading.hide();
					if(data) {
						if(data.status != 1) {
							var errMsg = "${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.fail') }";
							if(data.reason){
								errMsg += '。' + "${ lfn:message('sys-time:sysTimeLeaveDetail.reason') }" + '：' + data.reason;
							}
							dialog.failure(errMsg, null, function(){
								location.reload();
							});
						} else {
							dialog.success('<bean:message key="return.optSuccess" />', null, function(){
								location.reload();
							});
						}
					} else {
						dialog.failure('<bean:message key="return.optFailure" />', null, function(){
							location.reload();
						});
					}
				},'json');
			};
			
			window.updateAttend = function(id){
				window.loading = dialog.loading();
				$.post('<c:url value="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=updateAttend"/>&fdId=' + id, 
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
			
			window.updateResumeAttend = function(id){
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
			
			window.addResume = function(id){
				Com_OpenWindow('<c:url value="/sys/time/sys_time_leave_resume/sysTimeLeaveResume.do" />?method=add&leaveDetailId=' + id);
			}
		});
		</script>
	</template:replace>
</template:include>