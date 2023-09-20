<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:content title="基本信息" titleicon="lui-fm-icon-2">
	<!-- 草稿状态的文档默认选中基本信息页签 -->
	<c:if test="${kmImeetingMainForm.docStatus=='10'}">
		<script>
			LUI.ready(function(){
				setTimeout(function(){
					$("i.lui-fm-icon-2").closest(".lui_tabpanel_vertical_icon_navs_item_l").click();
				},200);
			});
		</script>
	</c:if>
	<table class="tb_normal lui-fm-noneBorderTable" width=100%>
		<!--主题-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdName"/>
			</td>
			<td>
				<c:out value="${kmImeetingMainForm.fdName }"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMeetingNum"/>
			</td>
			<td>
				<c:out value="${kmImeetingMainForm.fdMeetingNum}"></c:out>
			</td>
		</tr>
		<!--状态-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.docStatus"/>
			</td>
			<td>
				<c:if test="${kmImeetingMainForm.docStatus!='30' && kmImeetingMainForm.docStatus!='41' }">
					<sunbor:enumsShow value="${kmImeetingMainForm.docStatus}" enumsType="common_status" />
				</c:if>
				<%--未召开--%>
				<c:if test="${kmImeetingMainForm.docStatus=='30' && isBegin==false }">
					<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.unHold"/>
				</c:if>
				<%--正在召开--%>
				<c:if test="${kmImeetingMainForm.docStatus=='30' && isBegin==true && isEnd==false }">
					<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.holding"/>
				</c:if>
				<%--已召开--%>
				<c:if test="${kmImeetingMainForm.docStatus=='30' && isEnd==true }">
					<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.hold"/>
				</c:if>
				<%--已取消--%>
				<c:if test="${kmImeetingMainForm.docStatus=='41' }">
					<bean:message bundle="km-imeeting" key="kmImeeting.status.cancel"/>
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdTemplate"/>
			</td>			
			<td>
				<c:out value="${kmImeetingMainForm.fdTemplateName }"></c:out>
			</td>
		</tr>
		
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHost"/>
			</td>			
			<td>
				<c:out value="${kmImeetingMainForm.fdHostName }"></c:out>
				<c:if test="${not empty kmImeetingMainForm.fdOtherHostPerson }">
					&nbsp;${kmImeetingMainForm.fdOtherHostPerson }
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate"/>
			</td>			
			<td>
				 <input type="hidden" name="fdHoldDate" value="${kmImeetingMainForm.fdHoldDate }"/>
				 <input type="hidden" name="fdFinishDate" value="${kmImeetingMainForm.fdFinishDate }"/>
				<c:out value="${kmImeetingMainForm.fdHoldDate }"></c:out>&nbsp;~&nbsp;<c:out value="${kmImeetingMainForm.fdFinishDate }"></c:out>
			</td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration"/>
			</td>			
			<td width="35%" >
				<span id ="fdHoldDurationHour2" ></span><bean:message key="date.interval.hour"/>
				<span id="fdHoldDurationMinSpan2"><span id ="fdHoldDurationMin2" ></span><bean:message key="date.interval.minute"/></span>
				<script type="text/javascript">
				seajs.use([ 'km/imeeting/resource/js/dateUtil'], function(dateUtil) {
					//初始化会议历时
					if( "${kmImeetingMainForm.fdHoldDuration}" ){
						//将小时分解成时分
						var timeObj=dateUtil.splitTime({"ms":"${kmImeetingMainForm.fdHoldDuration}"});
						$('#fdHoldDurationHour2').html(timeObj.hour);
						$('#fdHoldDurationMin2').html(timeObj.minute);
						if(timeObj.minute){
							$('#fdHoldDurationMinSpan2').show();
						}else{
							$('#fdHoldDurationMinSpan2').hide();
						}		
					}
				});
				</script>
			</td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdPlace"/>
			</td>
			<td>
				<c:choose>
					<c:when test="${not empty kmImeetingMainForm.fdVicePlaceNames or not empty kmImeetingMainForm.fdOtherVicePlace }">
						<!-- 主会场 -->
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMainPlace"/>：
						<c:out value="${kmImeetingMainForm.fdPlaceName}" />
						<input type="hidden" name="fdPlaceUserTime" value="${ kmImeetingMainForm.fdUserTime}"
								subject="${lfn:message('km-imeeting:kmImeetingMain.fdMainPlace') }"/>
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
						<c:out value="${kmImeetingMainForm.fdVicePlaceNames}" />
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
						<input type="hidden" name="fdVicePlaceUserTimes" value="${ kmImeetingMainForm.fdVicePlaceUserTimes}"/>
					</c:when>
					<c:otherwise>
						<c:out value="${kmImeetingMainForm.fdPlaceName}" />
						<input type="hidden" name="fdPlaceUserTime" value="${ kmImeetingMainForm.fdUserTime}"
								subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }"/>
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
			<td class="tr_normal_title" width=30%>
	 			<bean:message bundle="km-imeeting" key="kmImeetingMain.docCreator"/>
	 		</td>
	 		<td>
	 			<c:out value="${kmImeetingMainForm.docCreatorName}"></c:out>
			</td>
		</tr>
		<tr>
			<%--创建时间--%>
	 		<td class="tr_normal_title" width=30%>
	 			<bean:message bundle="km-imeeting" key="kmImeetingMain.docCreateTime"/>
	 		</td>
	 		<td>
	 			<c:out value="${kmImeetingMainForm.docCreateTime}"></c:out>
			</td>
		</tr>
		<%-- 所属场所 --%>
		<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
               <c:param name="id" value="${kmImeetingMainForm.authAreaId}"/>
               <c:param name="rightModel" value="true"/>
         </c:import> 
	</table>
</ui:content>