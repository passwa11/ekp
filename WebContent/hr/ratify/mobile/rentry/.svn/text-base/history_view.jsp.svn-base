<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div data-dojo-type="mui/table/ScrollableHContainer">
	<div data-dojo-type="mui/table/ScrollableHView">
		<table cellspacing="0" cellpadding="0" class="detailTableNormal">
			<tr>
				<td class="detailTableNormalTd">
					<table class="muiAgendaNormal muiNormal" width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
				  			<%--序号--%>
							<td>
								<bean:message key="page.serial"/>
							</td>        	
							<%--公司名称--%>		
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdName"/>
							</td>	
							<%--职务--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdPost"/>
							</td>	
							<%--开始时间--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdStartDate"/>
							</td>
							<%--结束时间--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdEndDate"/>
							</td>
							<%--描述--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdDesc"/>
							</td>
							<%--离职原因--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyHistory.fdLeaveReason"/>
							</td>		
				       	</tr>
				       	<c:forEach items="${hrRatifyEntryForm.fdHistory_Form}" var="fdHistory_FormItem" varStatus="vstatus"> 
					 		<tr KMSS_IsContentRow="1" data-celltr="true">
								<td KMSS_IsRowIndex=1 class="detailTableIndex">
									<span>${vstatus.index+1}</span>
									<input name="fdHistory_Form[${vstatus.index}].fdId" type="hidden" value="${fdHistory_FormItem.fdId}" />
								</td>
								<td>
									<div class="td1"><c:out value="${fdHistory_FormItem.fdName}"></c:out></div>
								</td> 
								<td>
									<div class="td2"><c:out value="${fdHistory_FormItem.fdPost}"></c:out></div>
								</td> 
								<td>
									<div class="td3"><c:out value="${fdHistory_FormItem.fdStartDate}"></c:out></div>
								</td>     
								<td>
									<div class="td4"><c:out value="${fdHistory_FormItem.fdEndDate}"></c:out></div>
								</td>
								<td>
									<div class="td5"><c:out value="${fdHistory_FormItem.fdDesc}"></c:out></div>
								</td>
								<td>
									<div class="td6"><c:out value="${fdHistory_FormItem.fdLeaveReason}"></c:out></div>
								</td> 							
					 		</tr>
				       	</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>