<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.Map"%>	
<c:set var="isVideoEnable" value="<%=KmImeetingConfigUtil.isVideoMeetingEnable()%>" />
   
<%-- 会议资源预定 --%>
<ui:content title="${lfn:message('km-imeeting:kmImeetingMain.createStep.resource') }" expand="true">
	<div class="swichDiv"> 
		<% if(KmImeetingConfigUtil.isVideoMeetingEnable()){%>
			<div class="videoDiv"><bean:message bundle="km-imeeting" key="kmImeetingMain.fdPlace" /><ui:switch property="fdNeedPlace" showType="edit" checked="${ kmImeetingMainForm.fdNeedPlace}" onValueChange="changeNeedPlace(false);"/></div>
			<div class="placeDiv"><bean:message bundle="km-imeeting" key="table.kmImeetingVideo" /><ui:switch property="fdIsVideo" showType="edit" checked="${ kmImeetingMainForm.fdIsVideo}"/></div>
		<%}else{%>
			<html:hidden property="fdIsVideo" value="false"/>
			<html:hidden property="fdNeedPlace" value="true"/>
		<%} %>	
	</div>
	 <table class="tb_normal" width=100%  id="resTb">
	 	<tr>
	 		<%--选择会议室--%>
	 		<td class="td_normal_title" width=15%>
	 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdPlace"/>
	 		</td>
	 		<td width="85%" colspan="3" >	
				<c:choose>
					<c:when test="${not empty kmImeetingMainForm.fdVicePlaceIds or not empty kmImeetingMainForm.fdOtherVicePlace or fdNeedMultiRes}">
			 			<xform:dialog propertyId="fdPlaceId" propertyName="fdPlaceName" showStatus="edit" validators="validateplace validateUserTime"
			 				className="inputsgl" style="width:46%;" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdMainPlace') }'"
			 				subject="${lfn:message('km-imeeting:kmImeetingMain.fdMainPlace') }">
					  	 	selectHoldPlace();
						</xform:dialog>
						<input type="hidden" name="fdPlaceUserTime" value="${ kmImeetingMainForm.fdUserTime}"/>
						&nbsp;	&nbsp;
			 			<c:set var="hasSysAttend" value="false"></c:set>
						<kmss:ifModuleExist path="/sys/attend">
							<c:set var="hasSysAttend" value="true"></c:set>
						</kmss:ifModuleExist>
						<c:if test="${hasSysAttend == true }">
							<c:import url="/sys/attend/import/map_tag.jsp" charEncoding="UTF-8">
								<c:param name="propertyName" value="fdOtherPlace"></c:param>
								<c:param name="propertyCoordinate" value="fdOtherPlaceCoordinate"></c:param>
								<c:param name="validators" value="validateplace"></c:param>
								<c:param name="placeholder" value="km-imeeting:kmImeetingMain.fdOtherMainPlace"></c:param>
								<c:param name="subject" value="${ lfn:message('km-imeeting:kmImeetingMain.fdPlace')}"></c:param>
								<c:param name="style" value="width:46%;"></c:param>
							</c:import>
						</c:if>
						<c:if test="${hasSysAttend == false }">
							<xform:text property="fdOtherPlace" style="width:40%;" validators="validateplace" subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherMainPlace') }'"></xform:text>
						</c:if>
						<span class="txtstrong">*</span>
						<br/><br/>
			 			<xform:dialog propertyId="fdVicePlaceIds" propertyName="fdVicePlaceNames" showStatus="edit" validators="validateViceUserTime"
			 				className="inputsgl" style="width:46%;"   htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdVicePlaces') }'"
			 				subject="${lfn:message('km-imeeting:kmImeetingMain.fdOtherVicePlace') }">
					  	 	selectHoldVicePlace();
						</xform:dialog>
						&nbsp;	&nbsp;
						<c:if test="${hasSysAttend == true }">
							<c:import url="/sys/attend/import/map_tag.jsp" charEncoding="UTF-8">
								<c:param name="propertyName" value="fdOtherVicePlace"></c:param>
								<c:param name="propertyCoordinate" value="fdOtherVicePlaceCoord"></c:param>
								<c:param name="placeholder" value="km-imeeting:kmImeetingMain.fdOtherVicePlace"></c:param>
								<c:param name="subject" value="${ lfn:message('km-imeeting:kmImeetingMain.fdOtherMainPlace')}"></c:param>
								<c:param name="style" value="width:46%;"></c:param>
							</c:import>
						</c:if>
						<c:if test="${hasSysAttend == false }">
							<xform:text property="fdOtherVicePlace" style="width:46%;" subject="${lfn:message('km-imeeting:kmImeetingMain.fdOtherVicePlace') }"  htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherVicePlace') }'"></xform:text>
						</c:if>
						<input type="hidden" name="fdVicePlaceUserTimes" value="${ kmImeetingMainForm.fdVicePlaceUserTimes}"/>
					</c:when>
					<c:otherwise>
						<xform:dialog propertyId="fdPlaceId" propertyName="fdPlaceName" showStatus="edit" validators="validateplace validateUserTime"
			 				className="inputsgl" style="width:46%;"
			 				subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }">
					  	 	selectHoldPlace();
						</xform:dialog>
						<input type="hidden" name="fdPlaceUserTime" value="${ kmImeetingMainForm.fdUserTime}"/>
						&nbsp;	&nbsp;
			 			<!-- 其他会议地点 -->
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPlace"/>
			 			&nbsp;	&nbsp;
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
								<c:param name="style" value="width:46%;"></c:param>
							</c:import>
						</c:if>
						<c:if test="${hasSysAttend == false }">
							<xform:text property="fdOtherPlace" style="width:46%;" validators="validateplace" subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }"></xform:text>
						</c:if>
						<span class="txtstrong">*</span>
					</c:otherwise>
				</c:choose>
			</td>
	 	</tr>
	 	<tr>
	 		<%--会议室辅助设备--%>
	 		<td class="td_normal_title" width=15% title="${lfn:message('km-imeeting:kmImeetingMain.kmImeetingEquipment.tip') }">
	 			<bean:message bundle="km-imeeting" key="kmImeetingMain.kmImeetingEquipment"/>
	 		</td>
	 		<td width="85%" colspan="3" >
				<xform:dialog propertyId="kmImeetingEquipmentIds" propertyName="kmImeetingEquipmentNames" showStatus="edit" 
	 				className="inputsgl" style="width:98%;float:left">
			  	 	selectEquipment();
				</xform:dialog>
			</td>
	 	</tr>
	 	<tr>
	 		<%--会议室辅助设备--%>
	 		<td class="td_normal_title" width=15%>
	 			<bean:message bundle="km-imeeting" key="kmImeetingMain.kmImeetingDevices"/>
	 		</td>
	 		<td width="85%" colspan="3" >
				<ui:dataview>
					<ui:source type="AjaxJson">
						{url:'/km/imeeting/km_imeeting_device/kmImeetingDevice.do?method=listDevices'}
					</ui:source>
					<ui:render type="Template">
						<c:import url="/km/imeeting/resource/tmpl/devices.jsp" charEncoding="UTF-8"></c:import>
					</ui:render>
				</ui:dataview>
			</td>
	 	</tr>
	 	<tr>
	 		<%--会场布置要求--%>
	 		<td class="td_normal_title" width=15%>
	 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdArrange"/>
	 		</td>
	 		<td width="85%" colspan="3" >
				<xform:textarea property="fdArrange" style="width:93%;" showStatus="edit" htmlElementProperties="data-actor-expand='true'"></xform:textarea>
			</td>
	 	</tr>
	 	 <tr>
	 		<%--会议协助人--%>
	 		<td class="td_normal_title" width=15%>
	 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAssistPersons"/>
	 		</td>
	 		<td width="85%" colspan="3" >
	 			<xform:address style="width:45%;height:80px" textarea="true" showStatus="edit"  propertyId="fdAssistPersonIds" propertyName="fdAssistPersonNames" orgType="ORG_TYPE_ALL" mulSelect="true"></xform:address>
		  		&nbsp;&nbsp;&nbsp;&nbsp;
		  		<xform:textarea style="width:45%;border:1px solid #b4b4b4" property="fdOtherAssistPersons" validators="maxLength(1500)"
		  			htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherAssistPersons') }' data-actor-expand='true'" showStatus="edit"/>
			</td>
	 	</tr>
	</table>
</ui:content>

<%-- 权限及流程处理 --%>
<ui:content title="${lfn:message('km-imeeting:kmImeetingMain.createStep.rightAndWorkflow') }" >
	<c:import url="/km/imeeting/km_imeeting_main/kmImeetingMain_rightAndWorkflow_add.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmImeetingMainForm" />
		<c:param name="moduleModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
		<c:param name="fdKey" value="ImeetingMain" />
	</c:import>
</ui:content>

<%-- 发送会议通知单 --%>
<ui:content title="${lfn:message('km-imeeting:kmImeetingMain.createStep.sendNotify') }" >
	<div id="notifyDiv">
		<table class="tb_normal" width=100%>
			<%-- 会议通知选项 --%>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-imeeting" key="kmImeetingMain.fdNotifyType"/>
				</td>
				<td width="85%" colspan="3">
					<xform:radio property="fdNotifyType" showStatus="edit">
     							<xform:enumsDataSource enumsType="km_imeeting_main_fd_notify_type" />
					</xform:radio>
				</td>
			</tr>
			<%-- 会议通知方式 --%>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="km-imeeting" key="kmImeetingMain.fdNotifyWay"/>
				</td>
				<td width="85%" colspan="3">
					 <kmss:editNotifyType property="fdNotifyWay" />
				</td>
			</tr>
		</table>
	</div>
</ui:content>
		
