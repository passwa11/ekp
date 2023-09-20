<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>


<c:choose>
	<c:when test="${'true' eq isCancelMeeting}">
		<c:if test="${kmImeetingMainFormOri != null}">
			<c:choose>
				<c:when test="${param.approveModel eq 'right'}">
					<c:choose>
						<c:when test="${kmImeetingMainForm.docStatus>='30' || kmImeetingMainForm.docStatus=='00'}">
							<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmImeetingMainFormOri" />
								<c:param name="fdKey" value="ImeetingMain" />
								<c:param name="showHistoryOpers" value="true" />
								<c:param name="isExpand" value="true" />
								<c:param name="approveType" value="right" />
								<c:param name="needInitLbpm" value="true" />
								<c:param name="order" value="6"/>
							</c:import>
						</c:when>
						<c:otherwise>
							<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmImeetingMainFormOri" />
								<c:param name="fdKey" value="ImeetingMain" />
								<c:param name="showHistoryOpers" value="true" /> 
								<c:param name="isExpand" value="true" />
								<c:param name="approveType" value="right" />
								<c:param name="order" value="6"/>
							</c:import>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmImeetingMainFormOri" />
						<c:param name="fdKey" value="ImeetingMain" />
					</c:import>
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${param.approveModel eq 'right'}">
				<c:choose>
					<c:when test="${kmImeetingMainForm.docStatus>='30' || kmImeetingMainForm.docStatus=='00'}">
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmImeetingMainForm" />
							<c:param name="fdKey" value="ImeetingMain" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
							<c:param name="needInitLbpm" value="true" />
							<c:param name="order" value="6"/>
						</c:import>
					</c:when>
					<c:otherwise>
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmImeetingMainForm" />
							<c:param name="fdKey" value="ImeetingMain" />
							<c:param name="showHistoryOpers" value="true" /> 
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
							<c:param name="order" value="6"/>
						</c:import>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingMainForm" />
					<c:param name="fdKey" value="ImeetingMain" />
					<c:param name="onClickSubmitButton" value="gun();" />
				</c:import>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

		
