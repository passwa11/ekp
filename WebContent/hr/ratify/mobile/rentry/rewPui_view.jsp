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
							<%--名称--%>		
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyRewPuni.fdName"/>
							</td>	
							<%--日期--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyRewPuni.fdDate"/>
							</td>	
							<%--备注--%>
							<td>
								<bean:message bundle="hr-ratify" key="hrRatifyRewPuni.fdRemark"/>
							</td>		
				       	</tr>
				       	<c:forEach items="${hrRatifyEntryForm.fdRewardsPunishments_Form}" var="fdRewardsPunishments_FormItem" varStatus="vstatus"> 
					 		<tr KMSS_IsContentRow="1" data-celltr="true">
								<td KMSS_IsRowIndex=1 class="detailTableIndex">
									<span>${vstatus.index+1}</span>
									<input name="fdRewardsPunishments_Form[${vstatus.index}].fdId" type="hidden" value="${fdRewardsPunishments_FormItem.fdId}" />
								</td>
								<td>
									<div class="td1"><c:out value="${fdRewardsPunishments_FormItem.fdName}"></c:out></div>
								</td> 
								<td>
									<div class="td2"><c:out value="${fdRewardsPunishments_FormItem.fdDate}"></c:out></div>
								</td> 
								<td>
									<div class="td3"><c:out value="${fdRewardsPunishments_FormItem.fdRemark}"></c:out></div>
								</td>      							
					 		</tr>
				       	</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>