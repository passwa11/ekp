<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.imeeting.model.KmImeetingTopic"%>
<%@page import="com.landray.kmss.sys.unit.model.KmImissiveUnit"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement,java.util.*,com.landray.kmss.util.*"%>
<template:include ref="default.simple" sidebar="auto">
	<template:replace name="body"> 
		<html:form action="/km/imeeting/km_imeeting_topic/kmImeetingTopic.do">
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
						checksValue+=checks[i].value+";";
					}
				 }
				 if(checksValue==""){
					seajs.use(['lui/dialog'], function(dialog) {
						dialog.alert('请选择议题!');
					});
					return;
				 }
				 $dialog.hide(checksValue);
			}
			function selectStock(fdId){
				//多选的情况
				if($('[name="List_Selected"][value="'+fdId+'"]').prop('checked') ){
					$('[name="List_Selected"][value="'+fdId+'"]').prop('checked',false);
				}else{
					$('[name="List_Selected"][value="'+fdId+'"]').prop('checked',true);
				}
			};
			window.onload = function(){
				var keywords = Com_GetUrlParameter(location.href,'keywords');
				if(keywords != null &&  keywords != '')
					document.getElementsByName("keywords")[0].value = keywords;
			};
			function dosearch(){
				var keywords = document.getElementsByName("keywords")[0].value;
				//去除首尾空格
				keywords = keywords.replace(/(^\s*)|(\s*$)/g,"");
				keywords = encodeURI(keywords); //中文两次转码
				window.location.href ="${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=list&isDialog=0&keywords="+keywords;
			}
		</script>
		<p class="txttitle">会议议题</p>
		<div class="input_search" style="border:0">
		 	<input type="text"  name="keywords"  size="20" onkeydown="searchEnter();" />
		   <input type="button" class="btnopt" value="<bean:message key="button.search"/>" onclick="dosearch();">
		</div>
		<c:if test="${queryPage.totalrows==0}">
			<%@ include file="/resource/jsp/list_norecord.jsp"%>
		</c:if>
		<c:if test="${queryPage.totalrows>0}">
			<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
			<table id="List_ViewTable">
				<tr>
					<sunbor:columnHead htmlTag="td">
						<td width="10px">
							<input type="checkbox" name="List_Tongle">
						</td>
						<td width="40px">
							<bean:message key="page.serial"/>
						</td>
						<td style="min-width:200px">
							<bean:message bundle="km-imeeting" key="kmImeetingTopic.docSubject"/>
						</td>
						<td width="10%">
							<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdChargeUnit"/>
						</td>
						<td  width="8%">
							<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdReporter"/>
						</td>
						<td>
							<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdAttendUnit"/>
						</td>
						<td  width="16%">
							<bean:message bundle="km-imeeting" key="kmImeetingTopic.docCreateTime"/>
						</td>
						<td>
							<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdIsAccept"/>
						</td>
					</sunbor:columnHead>
				</tr>
				<c:forEach items="${queryPage.list}" var="kmImeetingTopic" varStatus="vstatus">
					<tr style="cursor:pointer"  onclick="selectStock('${kmImeetingTopic.fdId}')">
						<td>
							<input type="checkbox" name="List_Selected" value="${kmImeetingTopic.fdId}">
						</td>
						<td>
							${vstatus.index+1}
						</td>
						<td>
							<c:out value="${kmImeetingTopic.docSubject}" />
						</td>
						<td>
							<c:out value="${kmImeetingTopic.fdChargeUnit.fdName}" />
						</td>
						<td>
							<c:out value="${kmImeetingTopic.fdReporter.fdName}" />
						</td>
						<td>
							<%
								if(pageContext.getAttribute("kmImeetingTopic")!=null){
							    List fdAttendUnit=((KmImeetingTopic)pageContext.getAttribute("kmImeetingTopic")).getFdAttendUnit();
								String unitNames="";
									for(int i=0;i<fdAttendUnit.size();i++){
										if(i==fdAttendUnit.size()-1){
											unitNames+=((KmImissiveUnit)fdAttendUnit.get(i)).getFdName();	
										}else{
											unitNames+=((KmImissiveUnit)fdAttendUnit.get(i)).getFdName()+";";
										}
									 }
									request.setAttribute("unitNames",unitNames);
								}
							%>
							${unitNames}
						</td>
						<td>
							<kmss:showDate value="${kmImeetingTopic.docCreateTime}" type="datetime"></kmss:showDate>
							
						</td>
						<td>
							 <c:if test="${kmImeetingTopic.fdIsAccept}">
								是
							</c:if>
							<c:if test="${!kmImeetingTopic.fdIsAccept}">
								否
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
			<center>
			   <div style="padding-top:15px">
				<input name="btnOk" id="btnOk"  type="button" class="lui_form_button" style="width:50px"
				value="<bean:message key="button.ok"/>" onclick="getReturnValue();">&nbsp;
				<input name="btnCancel" id="btnCancel" type="button"  class="lui_form_button" style="width:50px"
				value="<bean:message key="button.cancel"/>" onclick="$dialog.hide();">
			    </div>
			</center>
			<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
		</c:if>
		</html:form>
		<%@ include file="/resource/jsp/list_down.jsp"%>
	</template:replace> 
</template:include>