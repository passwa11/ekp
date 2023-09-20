<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.print" sidebar="no">
	<template:replace name="head">
		<style>
			.print_txttitle{ 
				font-size: 20px; 
				font-weight: normal; 
				color:#000;
			}
			.tr_label_title{
				margin: 28px 0px 10px 0px;
				border-left: 3px solid #46b1fc
			}
			
			.tr_label_title .title{
				font-weight: 900;
				font-size: 16px;
				color: #000;
				text-align:left;
				margin-left: 8px;
			}
		</style>
	</template:replace>
	
	<template:replace name="title">
		<c:out value="${kmImeetingSummaryForm.fdName} - ${ lfn:message('km-imeeting:module.km.imeeting') }"></c:out>
	</template:replace>
	
	<template:replace name="toolbar">
	</template:replace>
	
	<template:replace name="content">
		<script>
			seajs.use(['lui/jquery','km/imeeting/resource/js/dateUtil'],function($,dateUtil){
				//初始化会议历时
				if('${kmImeetingSummaryForm.fdHoldDuration}'){
					//将小时分解成时分
					var timeObj=dateUtil.splitTime({"ms":"${kmImeetingSummaryForm.fdHoldDuration}"});
					$('#fdHoldDurationHour').html(timeObj.hour);
					$('#fdHoldDurationMin').html(timeObj.minute);
					if(timeObj.minute){
						$('#fdHoldDurationMinSpan').show();
					}else{
						$('#fdHoldDurationMinSpan').hide();
					}
				}
			});
		</script>
		<html:form action="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do" > 
			<center>
			<div class="print_title_header">
				<p class="print_txttitle"><bean:write name="kmImeetingSummaryForm" property="fdName" /></p>
			</div>
			<div id="printTable" class="tb_normal" style="border: none;font-size: 12px;max-width:1000px;">
				<div class="lui_form_content_frame">
					<div class="tr_label_title">
					    <div class="title">
					       <bean:message bundle="km-imeeting" key="kmImeetingSummary.info.base" />
					    </div>
				    </div>
					<table class="tb_normal" width=100% id="Table_Main">
						<tr>
							<%-- 会议名称--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdName"/>
							</td>
							<td width="35%">
								<xform:text property="fdName" style="width:80%" />
							</td>
							<%-- 会议类型--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdTemplate"/>
							</td>
							<td width="35%">
								<c:out value="${kmImeetingSummaryForm.fdTemplateName}"></c:out>
							</td>
						</tr>
						<%-- 所属场所 --%>
							<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
			                     <c:param name="id" value="${kmImeetingSummaryForm.authAreaId}"/>
			                </c:import>
						<tr>
							<%-- 主持人--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdHost"/>
							</td>
							<td width="35%">
								<c:out value="${kmImeetingSummaryForm.fdHostName} ${kmImeetingSummaryForm.fdOtherHostPerson}"></c:out>
							</td>
							<%-- 会议地点--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdPlace"/>
							</td>
							<td width="35%">
								<c:out value="${kmImeetingSummaryForm.fdPlaceName} ${kmImeetingSummaryForm.fdOtherPlace}"></c:out>
							</td>
						</tr>
						<tr>
							<%-- 会议时间--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate"/>
							</td>
							<td width="35%">
								<xform:datetime property="fdHoldDate" dateTimeType="datetime" style="width:36%" ></xform:datetime>~
								<xform:datetime property="fdFinishDate" dateTimeType="datetime" style="width:36%" ></xform:datetime>
							</td>
							<%--会议历时--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration"/>
							</td>			
							<td width="35%" >
								<span id ="fdHoldDurationHour" ></span><bean:message key="date.interval.hour"/>
								<span id="fdHoldDurationMinSpan"><span id ="fdHoldDurationMin" ></span><bean:message key="date.interval.minute"/></span>
							</td>
						</tr>
						<tr>
					 		<%--选择会议室--%>
					 		<td class="td_normal_title" width=15%>
					 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdPlace"/>
					 		</td>
					 		<td width="85%" colspan="3" >
					 			<c:choose>
									<c:when test="${not empty kmImeetingSummaryForm.fdVicePlaceNames or not empty kmImeetingSummaryForm.fdOtherVicePlace }">
										<!-- 主会场 -->
										<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMainPlace"/>：
										<c:out value="${kmImeetingSummaryForm.fdPlaceName}" />
										&nbsp;
										<!-- 外部主会场 -->
							 			<c:set var="hasSysAttend" value="false"></c:set>
										<kmss:ifModuleExist path="/sys/attend">
											<c:set var="hasSysAttend" value="true"></c:set>
										</kmss:ifModuleExist>
										<c:if test="${hasSysAttend == true }">
											<c:import url="/sys/attend/import/map_tag.jsp" charEncoding="UTF-8">
												<c:param name="propertyName" value="fdOtherPlace"></c:param>
												<c:param name="propertyCoordinate" value="fdOtherPlaceCoordinate"></c:param>
												<c:param name="validators" value="validateplace"></c:param>
												<c:param name="subject" value="${ lfn:message('km-imeeting:kmImeetingMain.fdPlace')}"></c:param>
												<c:param name="style" value="width:40%;"></c:param>
												<c:param name="showStatus" value="view"></c:param>
											</c:import>
										</c:if>
										<c:if test="${hasSysAttend == false }">
											<xform:text property="fdOtherPlace" style="width:40%;"></xform:text>
										</c:if>
										<br/><br/>
										<!-- 分会场 -->
										<bean:message bundle="km-imeeting" key="kmImeetingMain.fdVicePlaces"/>：
										<c:out value="${kmImeetingSummaryForm.fdVicePlaceNames}" />
										&nbsp;
										<!-- 外部分会场 -->
										<c:if test="${hasSysAttend == true }">
											<c:import url="/sys/attend/import/map_tag.jsp" charEncoding="UTF-8">
												<c:param name="propertyName" value="fdOtherVicePlace"></c:param>
												<c:param name="propertyCoordinate" value="fdOtherVicePlaceCoord"></c:param>
												<c:param name="subject" value="${ lfn:message('km-imeeting:kmImeetingMain.fdOtherMainPlace')}"></c:param>
												<c:param name="style" value="width:40%;"></c:param>
												<c:param name="showStatus" value="view"></c:param>
											</c:import>
										</c:if>
										<c:if test="${hasSysAttend == false }">
											<xform:text property="fdOtherVicePlace" style="width:40%;"></xform:text>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:out value="${kmImeetingSummaryForm.fdPlaceName}" />
										&nbsp;
										<!-- 其他会议地点 -->
							 			<c:set var="hasSysAttend" value="false"></c:set>
										<kmss:ifModuleExist path="/sys/attend">
											<c:set var="hasSysAttend" value="true"></c:set>
										</kmss:ifModuleExist>
										<c:if test="${hasSysAttend == true }">
											<c:import url="/sys/attend/import/map_tag.jsp" charEncoding="UTF-8">
												<c:param name="propertyName" value="fdOtherPlace"></c:param>
												<c:param name="propertyCoordinate" value="fdOtherPlaceCoordinate"></c:param>
												<c:param name="validators" value="validateplace"></c:param>
												<c:param name="subject" value="${ lfn:message('km-imeeting:kmImeetingMain.fdOtherPlace')}"></c:param>
												<c:param name="style" value="width:40%;"></c:param>
												<c:param name="showStatus" value="view"></c:param>
											</c:import>
										</c:if>
										<c:if test="${hasSysAttend == false }">
											<xform:text property="fdOtherPlace" style="width:40%;"></xform:text>
										</c:if>
									</c:otherwise>
								</c:choose>
							</td>
					 	</tr>
						<tr>
							<%-- 计划参加人员--%>
							<td class="td_normal_title" width=15%>
						   		<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdPlanAttendPersons" />
							</td>
							<td width="85%"  colspan="3" style="word-break:break-all">
								<c:if test="${ not empty kmImeetingSummaryForm.fdPlanAttendPersonNames }">
									<div>
										<img src="${LUI_ContextPath}/km/imeeting/resource/images/inner_person.png" />
										<span style="vertical-align: top;">
											<bean:message bundle="km-imeeting" key="kmImeetingMain.fdInnerPerson"/>：<c:out value="${kmImeetingSummaryForm.fdPlanAttendPersonNames }"></c:out>
										</span>
									</div>
								</c:if>
								<%--外部计划参与人员--%>
								<c:if test="${ not empty kmImeetingSummaryForm.fdPlanOtherAttendPerson }">
									<div>
										<img src="${LUI_ContextPath}/km/imeeting/resource/images/other_person.png" />
										<span style="vertical-align: top;">
											<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>：<c:out value="${kmImeetingSummaryForm.fdPlanOtherAttendPerson }"></c:out>
										</span>
									</div>
								</c:if>
							</td>
						</tr>
						<tr>
							<%-- 计划列席人员--%>
							<td class="td_normal_title" width=15%>
						   		<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdPlanParticipantPersons" />
							</td>
							<td width="85%"  colspan="3" style="word-break:break-all">
								<c:if test="${ not empty kmImeetingSummaryForm.fdPlanParticipantPersonNames }">
									<div>
										<img src="${LUI_ContextPath}/km/imeeting/resource/images/inner_person.png" />
										<span style="vertical-align: top;">
											<bean:message bundle="km-imeeting" key="kmImeetingMain.fdInnerPerson"/>：<c:out value="${kmImeetingSummaryForm.fdPlanParticipantPersonNames }"></c:out>
										</span>
									</div>
								</c:if>
								<%--外部参加人员--%>
								<c:if test="${ not empty kmImeetingSummaryForm.fdPlanOtherParticipantPersons }">
									<div>
										<img src="${LUI_ContextPath}/km/imeeting/resource/images/other_person.png" />
										<span style="vertical-align: top;">
											<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>：<c:out value="${kmImeetingSummaryForm.fdPlanOtherParticipantPersons }"></c:out>
										</span>
									</div>
								</c:if>
							</td>
						</tr>
						<tr>
							<!-- 实际与会人员 -->
							<td class="td_normal_title" width=15%>
							   <bean:message bundle="km-imeeting" key="kmImeetingSummary.fdActualAttendPersons" />
							</td>
							<td width="85%"  colspan="3" style="word-break:break-all">
								<c:if test="${ not empty kmImeetingSummaryForm.fdActualAttendPersonNames }">
									<div>
										<img src="${LUI_ContextPath}/km/imeeting/resource/images/inner_person.png" />
										<span style="vertical-align: top;">
											<bean:message bundle="km-imeeting" key="kmImeetingMain.fdInnerPerson"/>：<c:out value="${kmImeetingSummaryForm.fdActualAttendPersonNames }"></c:out>
										</span>
									</div>
								</c:if>
								<%--外部与会人员--%>
								<c:if test="${ not empty kmImeetingSummaryForm.fdActualOtherAttendPersons }">
									<div>
										<img src="${LUI_ContextPath}/km/imeeting/resource/images/other_person.png" />
										<span style="vertical-align: top;">
											<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>：<c:out value="${kmImeetingSummaryForm.fdActualOtherAttendPersons }"></c:out>
										</span>
									</div>
								</c:if>
							</td>
						</tr>
						<tr>
							<%-- 抄送人员--%>
							<td class="td_normal_title" width=15%>
						   		<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdCopyToPersons" />
							</td>
							<td colspan="3">
								<xform:address propertyName="fdCopyToPersonNames" propertyId="fdCopyToPersonIds" style="width:92%;" textarea="true"></xform:address>
							</td>
						</tr>
						<tr>
							<%-- 编辑内容--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingSummary.docContent" />
							</td>
							<td width=85% colspan="3" id="contentDiv">
								<c:if test="${kmImeetingSummaryForm.fdContentType=='rtf'}">
									<xform:rtf property="docContent"></xform:rtf>
								</c:if>
							</td>
						</tr>
						<tr>
					 		<%--会议组织人--%>
					 		<td class="td_normal_title" width=15%>
					 			<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdEmcee"/>
					 		</td>
					 		<td width="35%" >
					 			<c:out value="${kmImeetingSummaryForm.fdEmceeName}"></c:out>
							</td>
							<%--组织部门--%>
					 		<td class="td_normal_title" width=15%>
					 			<bean:message bundle="km-imeeting" key="kmImeetingSummary.docDept"/>
					 		</td>
					 		<td width="35%" >
					 			<c:out value="${kmImeetingSummaryForm.docDeptName}"></c:out>
							</td>
					 	</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdNotifyType" />
							</td>
							<td colspan="3">
									<kmss:showNotifyType value="${kmImeetingSummaryForm.fdNotifyType}"></kmss:showNotifyType>
							</td>
						</tr>
						<tr>
							<%-- 纪要录入人--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingSummary.docCreator"/>
							</td>
							<td width="35%">
								<html:hidden property="docCreatorId"/><html:hidden property="docCreatorName"/>
								<c:out value="${kmImeetingSummaryForm.docCreatorName }"></c:out>
							</td>
							<%-- 录入时间--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingSummary.docCreateTime"/>
							</td>
							<td width="35%">
								<html:hidden property="docCreateTime"/>
								<c:out value="${kmImeetingSummaryForm.docCreateTime }"></c:out>
							</td>
						</tr>
					</table>
					
				</div>
			
				<%-- 审批记录 --%>
				<c:if test="${saveApproval }">
					<div>
					    <div class="tr_label_title">
						    <div class="title">
						       <bean:message bundle="km-imeeting" key="kmImeeting.flow.trail" />
						    </div>
					    </div>
						<table width=100%>
							<!-- 审批记录 -->
							<tr>
								<td colspan=4>
									<c:import url="/sys/workflow/include/sysWfProcess_log.jsp" charEncoding="UTF-8">
										<c:param name="formName" value="kmImeetingSummaryForm" />
									</c:import>
								</td>
							</tr>
						</table>
					</div>
				</c:if>
			</div>
			</center>
		</html:form>
	</template:replace>
		
</template:include>

