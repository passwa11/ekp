<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="auto">
	<template:replace name="body"> 
		<script language="JavaScript">
			seajs.use(['theme!form']);
		</script>
		<script type="text/javascript">
			function getReturnValue()
			{
				 var checks=document.getElementsByName("List_Selected");
				 var checksValue="";
				 for(var i=0;i<checks.length;i++)
				 {
					 if(checks[i].checked)
					 {
						checksValue = checks[i].value;
					 }
				 }
				 $dialog.hide(checksValue);
			}
			
			function selectMeeting(fdId){
				$('[name="List_Selected"][value="'+fdId+'"]').prop('checked',true);
				getReturnValue();
			};
		</script>
		<p class="txttitle"><bean:message bundle="km-imeeting" key="kmImeetingMain.fdNotifyView" /></p>
		<c:if test="${queryPage.totalrows==0}">
			<%@ include file="/resource/jsp/list_norecord.jsp"%>
		</c:if>
		<c:if test="${queryPage.totalrows>0}">
			<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
			<table id="List_ViewTable">
				<tr>
					<td width="25px">
						
					</td>
					<td width="40px">
						<bean:message key="page.serial"/>
					</td>
					<td>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdName"/>
					</td>
					<td>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHost"/>
					</td>
					<td>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdPlace"/>
					</td>
					<td>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDate"/>
					</td>
					<td>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdFinishDate"/>
					</td>
				</tr>
				<c:forEach items="${queryPage.list}" var="kmImeetingMain" varStatus="vstatus">
					<tr style="cursor:pointer"  onclick="selectMeeting('${kmImeetingMain.fdId}')">
						<td>
							<input type="radio" name="List_Selected" value="${kmImeetingMain.fdId}" onclick="getReturnValue();">
						</td>
						<td>
							${vstatus.index+1}
						</td>
						<td>
							<c:out value="${kmImeetingMain.fdName}" />
						</td>
						<td>
							<c:out value="${kmImeetingMain.fdHost.fdName}" />
						</td>
						<td>
							<c:if test="${kmImeetingMain.isCloud }">
								<img src="${LUI_ContextPath}/km/imeeting/resource/images/icon_video.png" />		
							</c:if>
						  <c:out value="${kmImeetingMain.fdPlace.fdName}"/> <c:out value="${kmImeetingMain.fdOtherPlace}"/>
						</td>
						<td>
							 <kmss:showDate value="${kmImeetingMain.fdHoldDate}" type="datetime" />
						</td>
						<td>
							 <kmss:showDate value="${kmImeetingMain.fdFinishDate}" type="datetime" />
						</td>
					</tr>
				</c:forEach>
			</table>
			<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
		</c:if>
		<%@ include file="/resource/jsp/list_down.jsp"%>
	</template:replace> 
</template:include>