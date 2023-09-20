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
							<%--学校名称--%>		
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdName"/>
							</td>	
							<%--专业--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdMajor"/>
							</td>	
							<%--学历--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdAcademic"/>
							</td>
							<%--入学时间--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdEntranceDate"/>
							</td>
							<%--毕业时间--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdGraduationDate"/>
							</td>
							<%--备注--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyEduExp.fdRemark"/>
							</td>		
				       	</tr>
				       	<c:forEach items="${hrRatifyEntryForm.fdEducations_Form}" var="fdEducations_FormItem" varStatus="vstatus"> 
					 		<tr KMSS_IsContentRow="1" data-celltr="true">
								<td KMSS_IsRowIndex=1 class="detailTableIndex">
									<span>${vstatus.index+1}</span>
									<input name="fdEducations_Form[${vstatus.index}].fdId" type="hidden" value="${fdEducations_FormItem.fdId}" />
								</td>
								<td>
									<div class="td1"><c:out value="${fdEducations_FormItem.fdName}"></c:out></div>
								</td> 
								<td>
									<div class="td2"><c:out value="${fdEducations_FormItem.fdMajor}"></c:out></div>
								</td> 
								<td>
									<div class="td3"><c:out value="${fdEducations_FormItem.fdAcadeRecordName}"></c:out></div>
								</td>     
								<td>
									<div class="td4"><c:out value="${fdEducations_FormItem.fdEntranceDate}"></c:out></div>
								</td>
								<td>
									<div class="td5"><c:out value="${fdEducations_FormItem.fdGraduationDate}"></c:out></div>
								</td>
								<td>
									<div class="td6"><c:out value="${fdEducations_FormItem.fdRemark}"></c:out></div>
								</td> 							
					 		</tr>
				       	</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>