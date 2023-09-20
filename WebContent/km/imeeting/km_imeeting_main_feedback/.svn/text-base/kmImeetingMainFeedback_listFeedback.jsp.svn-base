<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.km.imeeting.model.KmImeetingAgenda,
				com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback,
				com.landray.kmss.util.*,
				java.lang.String,
				com.landray.kmss.util.ResourceUtil,
				com.landray.kmss.km.imeeting.service.IKmImeetingAgendaService"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImeetingMainFeedback" list="${queryList}">
		<%--ID--%> 
		<list:data-column property="fdId"></list:data-column>
		<%--名称--%>
		<list:data-column  headerClass="width200" col="fdMeeting.docSubject" title="${lfn:message('km-imeeting:kmImeetingMain.fdName') }" style="text-align:left" escape="false">
			<span class="com_subject"><c:out value="${kmImeetingMainFeedback.fdMeeting.docSubject}" /></span>
		</list:data-column>
		
		<list:data-column  headerClass="width200" col="fdAgendaName" title="${lfn:message('km-imeeting:kmImeetingFeedback.topic') }" escape="false">
			<%
				if(pageContext.getAttribute("kmImeetingMainFeedback")!=null){
					KmImeetingMainFeedback kmImeetingMainFeedback = (KmImeetingMainFeedback)pageContext.getAttribute("kmImeetingMainFeedback");
					String fdAgendaId = kmImeetingMainFeedback.getFdAgendaId();
					if(StringUtil.isNotNull(fdAgendaId)){
						IKmImeetingAgendaService kmImeetingAgendaService = (IKmImeetingAgendaService)SpringBeanUtil.getBean("kmImeetingAgendaService");
						KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda)kmImeetingAgendaService.findByPrimaryKey(fdAgendaId, KmImeetingAgenda.class, true);
						if(kmImeetingAgenda!=null){
							request.setAttribute("fdAgendaName",kmImeetingAgenda.getDocSubject());
						}
					}else{
						request.setAttribute("fdAgendaName", ResourceUtil
								.getString("kmImeetingFeedback.non-issueReceipt", "km-imeeting", ResourceUtil.getLocaleByUser()));
					}
				}
			%>
			<c:out value="${fdAgendaName}" />
		</list:data-column>
		<list:data-column  headerClass="width150" col="fdUnitName" title="${lfn:message('km-imeeting:kmImeetingFeedback.unit') }">
			<c:out value="${kmImeetingMainFeedback.fdUnitName}" />
		</list:data-column>
		<list:data-column headerClass="width150" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:choose>
				    	<c:when test="${kmImeetingMainFeedback.fdOperateType eq '01' or kmImeetingMainFeedback.fdOperateType eq '02'}">
				    		<a class="btn_txt" href="javascript:editFeedBack('${kmImeetingMainFeedback.fdId}')">${ lfn:message('km-imeeting:kmImeetingFeedback.modifyReceipt') }</a>
				    	</c:when>
				    	<c:otherwise>	
				    		<c:if test="${kmImeetingMainFeedback.fdOperateType ne '03' && kmImeetingMainFeedback.fdOperateType ne '05'}">
				    			<a class="btn_txt" style="color: blue;text-decoration: underline" href="javascript:addFeedBack('${kmImeetingMainFeedback.fdId}')">${ lfn:message('km-imeeting:kmImeetingFeedback.do')}</a>
				    		</c:if>
				    	</c:otherwise>
				    </c:choose>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>	
	</list:data-columns>
</list:data>