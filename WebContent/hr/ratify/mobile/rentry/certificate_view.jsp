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
							<%--证书名称--%>		
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdName"/>
							</td>	
							<%--颁发单位--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdIssuingUnit"/>
							</td>	
							<%--颁发时间--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdIssueDate"/>
							</td>
							<%--失效时间--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdInvalidDate"/>
							</td>
							<%--备注--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyCertifi.fdRemark"/>
							</td>		
				       	</tr>
				       	<c:forEach items="${hrRatifyEntryForm.fdCertificate_Form}" var="fdCertificate_FormItem" varStatus="vstatus"> 
					 		<tr KMSS_IsContentRow="1" data-celltr="true">
								<td KMSS_IsRowIndex=1 class="detailTableIndex">
									<span>${vstatus.index+1}</span>
									<input name="fdCertificate_Form[${vstatus.index}].fdId" type="hidden" value="${fdCertificate_FormItem.fdId}" />
								</td>
								<td>
									<div class="td1"><c:out value="${fdCertificate_FormItem.fdName}"></c:out></div>
								</td> 
								<td>
									<div class="td2"><c:out value="${fdCertificate_FormItem.fdIssuingUnit}"></c:out></div>
								</td> 
								<td>
									<div class="td3"><c:out value="${fdCertificate_FormItem.fdIssueDate}"></c:out></div>
								</td>     
								<td>
									<div class="td4"><c:out value="${fdCertificate_FormItem.fdInvalidDate}"></c:out></div>
								</td>
								<td>
									<div class="td5"><c:out value="${fdCertificate_FormItem.fdRemark}"></c:out></div>
								</td> 							
					 		</tr>
				       	</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>