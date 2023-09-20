<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil" %>
<%@ page import="com.landray.kmss.sys.attachment.util.JgWebOffice" %>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil" %>
<template:replace name="content">
	<c:set var="wpsoaassistEmbed" value="<%=SysAttWpsoaassistUtil.isWPSOAassistEmbed()%>"/>
<%
	pageContext.setAttribute("_isWpsWebOfficeEnable", new Boolean(SysAttWpsWebOfficeUtil.isEnable()));
	pageContext.setAttribute("_isWpsWebCloudEnable", new Boolean(SysAttWpsCloudUtil.isEnable()));
	//WPS加载项
    pageContext.setAttribute("_isWpsWebOffice", new Boolean(SysAttWpsCloudUtil.isEnableWpsWebOffice()));
	//WPS中台
	pageContext.setAttribute("_isWpsCenterEnable", new Boolean(SysAttWpsCenterUtil.isEnable()));
	Boolean isWindows = Boolean.FALSE;
	if("windows".equals(JgWebOffice.getOSType(request))){
		isWindows = Boolean.TRUE;
	}
	pageContext.setAttribute("isWindowsInOAassist", isWindows);
%>
	<c:if test="${kmImeetingSummaryForm.method_GET=='add'}">
		<script type="text/javascript">
			window.changeDocTemp = function(modelName,url,canClose){
				if(modelName==null || modelName=='' || url==null || url=='')
					return;
		 		seajs.use(['sys/ui/js/dialog'],function(dialog) {
				 	dialog.categoryForNewFile(modelName,url,false,null,
					function(rtn) {
						// 无分类状态下（一般于门户快捷操作）创建文档，取消操作同时关闭当前窗口
						if (!rtn){
							if(navigator.userAgent.indexOf("Edge") != -1){
								window.opener = null;
							    window.open("", "_self");
							    window.close();
							}
							if(navigator.userAgent.indexOf("Firefox") != -1 || navigator.userAgent.indexOf("Chrome") != -1){
								if(window.opener == null){
									window.location.href = "about:blank";
							        window.close();
								}else{
									window.close();
								}
						    }else{
						        window.opener = null;
						        window.open("", "_self");
						        window.close();
						    }
						}
					},'${JsParam.categoryId}','_self',canClose);
			 	});
		 	};
			if('${JsParam.meetingId}'==''&&'${JsParam.fdTemplateId}'==''){
				window.changeDocTemp('com.landray.kmss.km.imeeting.model.KmImeetingTemplate','/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=add&fdTemplateId=!{id}',true);
			}
			
		</script>
	</c:if>
	<c:if test="${param.approveModel ne 'right'}">
		<form name="kmImeetingSummaryForm" method="post" action ="${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_summary/kmImeetingSummary.do" onsubmit="return checkForm();">
	</c:if>
		<style>
			.tips {
				background: #fff7c6;
				margin-top: 10px;
				line-height: 27px;
				border: 1px #e8e8e8 solid;
				padding-left: 8px;
				width: 460px;
			}
			.tips img {
				position: relative;
				top: 3px;
			}
		</style>
		<div class="lui_form_content_frame" style="padding-top:20px">
			<html:hidden property="fdId" />
			<html:hidden property="docStatus" />
			<html:hidden property="fdMeetingId" />
			<html:hidden property="fdSignEnable" />
			<p class="txttitle">
				<bean:message bundle="km-imeeting" key="table.kmImeetingSummary" />
			</p>
			<table class="tb_normal" width=100% id="Table_Main">
				<tr>
					<%-- 会议名称--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdName"/>
					</td>
					<td width="35%">
						<xform:text property="fdName" style="width:95%" />
					</td>
					<%-- 会议类型--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdTemplate"/>
					</td>
					<td width="35%">
						<html:hidden property="fdTemplateId"/>
						<html:hidden property="fdTemplateName"/>
						<c:out value="${kmImeetingSummaryForm.fdTemplateName}"></c:out>
					</td>
				</tr>
				<%-- 所属场所 --%>
				<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
                     <c:param name="id" value="${kmImeetingSummaryForm.authAreaId}"/>
                </c:import>
				<tr>
					<%-- 主持人--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdHost"/>
					</td>
					<td width="85%" colspan="3">
						<xform:address style="width:22%;float:left" propertyId="fdHostId" propertyName="fdHostName" orgType="ORG_TYPE_PERSON"></xform:address>
				    	&nbsp;
						<xform:text style="width:22%" property="fdOtherHostPerson" className="inputsgl" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingSummary.fdOtherHostPerson') }'"/>
					</td>
				</tr>
				<tr>
					<%-- 会议时间--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate"/>
					</td>
					<td width="40%">
						<xform:datetime property="fdHoldDate" dateTimeType="datetime" onValueChange="changeDateTime"
							style="width:45%" required="true" validators="compareTime"></xform:datetime>
						<span style="position: relative;top:-5px;">~</span>
						<xform:datetime property="fdFinishDate" dateTimeType="datetime" onValueChange="changeDateTime"
							style="width:45%" required="true" validators="compareTime"></xform:datetime>
					</td>
					<%--会议历时--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration"/>
					</td>			
					<td width="35%" >
						<input type="text" name="fdHoldDurationHour" validate="digits maxLength(4)" class="inputsgl" style="width:50px;text-align: center;"  onchange="changeDuration();"/><bean:message key="date.interval.hour"/>
						<input type="text" name="fdHoldDurationMin" validate="digits maxLength(4)" class="inputsgl" style="width:50px;text-align: center;"  onchange="changeDuration();"/><bean:message key="date.interval.minute"/>
						<xform:text property="fdHoldDuration" showStatus="noShow"/>
					</td>
				</tr>
				<tr>
			 		<%--选择会议室--%>
			 		<td class="td_normal_title" width=15%>
			 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdPlace"/>
			 		</td>
			 		<td width="85%" colspan="3" >
			 			<c:choose>
			 				<c:when test="${fdNeedMultiRes or (kmImeetingSummaryForm.method_GET eq 'edit' and not empty kmImeetingSummaryForm.fdVicePlaceIds)}">
					 			<xform:dialog propertyId="fdPlaceId" propertyName="fdPlaceName" showStatus="edit" validators="placenotnull"
					 				className="inputsgl" style="width:46%;"   htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdMainPlace') }'"
					 				subject="${lfn:message('km-imeeting:kmImeetingMain.fdMainPlace') }">
							  	 	selectHoldPlace();
								</xform:dialog>
					 			&nbsp;	&nbsp;
					 			<c:set var="hasSysAttend" value="false"></c:set>
								<kmss:ifModuleExist path="/sys/attend">
									<c:set var="hasSysAttend" value="true"></c:set>
								</kmss:ifModuleExist>
								<c:if test="${hasSysAttend == true }">
									<c:import url="/sys/attend/import/map_tag.jsp" charEncoding="UTF-8">
										<c:param name="propertyName" value="fdOtherPlace"></c:param>
										<c:param name="propertyCoordinate" value="fdOtherPlaceCoordinate"></c:param>
										<c:param name="validators" value="placenotnull"></c:param>
										<c:param name="placeholder" value="km-imeeting:kmImeetingMain.fdOtherMainPlace"></c:param>
										<c:param name="subject" value="${ lfn:message('km-imeeting:kmImeetingMain.fdPlace')}"></c:param>
										<c:param name="style" value="width:46%;"></c:param>
									</c:import>
								</c:if>
								<c:if test="${hasSysAttend == false }">
									<xform:text property="fdOtherPlace" style="width:46%;" validators="validateplace" subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }"  htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherMainPlace') }'"></xform:text>
								</c:if>
								<span class="txtstrong">*</span>
								<br/><br/>
					 			<xform:dialog propertyId="fdVicePlaceIds" propertyName="fdVicePlaceNames" showStatus="edit"
					 				className="inputsgl" style="width:40%;"  htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdVicePlaces') }'"
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
									<xform:text property="fdOtherVicePlace" style="width:40%;" subject="${lfn:message('km-imeeting:kmImeetingMain.fdOtherMainPlace') }" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherVicePlace') }'"></xform:text>
								</c:if>
			 				</c:when>
			 				<c:otherwise>
	 							<xform:dialog propertyId="fdPlaceId" propertyName="fdPlaceName" showStatus="edit" validators="placenotnull"
			 						className="inputsgl" style="width:46%;" 
			 						subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }">
					  	 			selectHoldPlace();
								</xform:dialog>
								&nbsp;	&nbsp;
					 			<c:set var="hasSysAttend" value="false"></c:set>
								<kmss:ifModuleExist path="/sys/attend">
									<c:set var="hasSysAttend" value="true"></c:set>
								</kmss:ifModuleExist>
								<c:if test="${hasSysAttend == true }">
									<c:import url="/sys/attend/import/map_tag.jsp" charEncoding="UTF-8">
										<c:param name="propertyName" value="fdOtherPlace"></c:param>
										<c:param name="propertyCoordinate" value="fdOtherPlaceCoordinate"></c:param>
										<c:param name="validators" value="placenotnull"></c:param>
										<c:param name="placeholder" value="km-imeeting:kmImeetingMain.fdOtherPlace"></c:param>
										<c:param name="subject" value="${ lfn:message('km-imeeting:kmImeetingMain.fdPlace')}"></c:param>
										<c:param name="style" value="width:46%;"></c:param>
									</c:import>
								</c:if>
								<c:if test="${hasSysAttend == false }">
									<xform:text property="fdOtherPlace" style="width:46%;" validators="validateplace" subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherPlace') }'"></xform:text>
								</c:if>
								<span class="txtstrong">*</span>
			 				</c:otherwise>
			 			</c:choose>
					</td>
			 	</tr>
				<tr>
					<%-- 计划参加人员--%>
					<td class="td_normal_title" width=15%>
				   		<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdPlanAttendPersons" />
					</td>
					<td width=85% colspan="3">
						<c:if test="${kmImeetingSummaryForm.fdMeetingId == null}">
							 <xform:address mulSelect="true" isLoadDataDict="false" required="false" style="width:48%;height:80px" textarea="true"
							 propertyId="fdPlanAttendPersonIds" subject="${lfn:message('km-imeeting:kmImeetingSummary.fdPlanAttendPersons')}" 
							 propertyName="fdPlanAttendPersonNames" orgType='ORG_TYPE_ALL' onValueChange="changeActualAttendPerson"></xform:address>
							<%-- <xform:dialog icon="orgelement" propertyId="fdPlanAttendPersonIds"  propertyName="fdPlanAttendPersonNames" 
								 style="width:48%;height:80px" textarea="true">
					  			Dialog_Address(true, 'fdPlanAttendPersonIds','fdPlanAttendPersonNames',';',ORG_TYPE_ALL,changeActualAttendPerson);
							</xform:dialog>  --%>
							<xform:textarea property="fdPlanOtherAttendPerson" style="width:48%;height:80px;border:1px solid #b4b4b4" validators="maxLength(1500)"
								htmlElementProperties="onkeyup='changeOtherActualAttend()' placeholder='${lfn:message('km-imeeting:kmImeetingSummary.fdPlanOtherAttendPerson') }'" />
						</c:if>
						<c:if test="${kmImeetingSummaryForm.fdMeetingId != null}">
							<html:hidden property="fdPlanAttendPersonIds" />
							<html:hidden property="fdPlanAttendPersonNames" />
							<html:hidden property="fdPlanOtherAttendPerson" />
							
							<c:if test="${ not empty kmImeetingSummaryForm.fdPlanAttendPersonNames }">
								<img src="${LUI_ContextPath}/km/imeeting/resource/images/inner_person.png" />
								<span style="vertical-align: top;">
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdInnerPerson"/>：<c:out value="${kmImeetingSummaryForm.fdPlanAttendPersonNames }"></c:out>
								</span>
							</c:if>
							<%--外部计划参与人员--%>
							<c:if test="${ not empty kmImeetingSummaryForm.fdPlanOtherAttendPerson }">
								<br/><br/>
								<img src="${LUI_ContextPath}/km/imeeting/resource/images/other_person.png" />
								<span style="vertical-align: top;">
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>：<c:out value="${kmImeetingSummaryForm.fdPlanOtherAttendPerson }"></c:out>
								</span>
							</c:if>
							
						</c:if>
					</td>
				</tr>
				<tr>
					<%-- 计划列席人员--%>
					<td class="td_normal_title" width=15%>
				   		<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdPlanParticipantPersons" />
					</td>
					<td width=85% colspan="3">
						<c:if test="${kmImeetingSummaryForm.fdMeetingId == null}">
							<xform:address mulSelect="true" isLoadDataDict="false" required="false" style="width:48%;height:80px" textarea="true"
							 propertyId="fdPlanParticipantPersonIds" subject="${lfn:message('km-imeeting:kmImeetingSummary.fdPlanParticipantPersons')}" 
							 propertyName="fdPlanParticipantPersonNames" orgType='ORG_TYPE_ALL' onValueChange="changeActualAttendPerson"></xform:address>
							<%-- <xform:dialog icon="orgelement" propertyId="fdPlanParticipantPersonIds"  propertyName="fdPlanParticipantPersonNames" 
								style="width:48%;height:80px" textarea="true">
					  			Dialog_Address(true, 'fdPlanParticipantPersonIds','fdPlanParticipantPersonNames',';',ORG_TYPE_ALL,changeActualAttendPerson);
							</xform:dialog>  --%>
							<xform:textarea property="fdPlanOtherParticipantPersons" style="width:48%;height:80px;border:1px solid #b4b4b4" validators="maxLength(1500)"
								htmlElementProperties="onkeyup='changeOtherActualAttend()' placeholder='${lfn:message('km-imeeting:kmImeetingSummary.fdPlanOtherParticipantPersons') }'" />
						</c:if>
						<c:if test="${kmImeetingSummaryForm.fdMeetingId != null}">
							<html:hidden property="fdPlanParticipantPersonIds" />
							<html:hidden property="fdPlanParticipantPersonNames" />
							<html:hidden property="fdPlanOtherParticipantPersons" />
							
							<c:if test="${ not empty kmImeetingSummaryForm.fdPlanParticipantPersonNames }">
								<img src="${LUI_ContextPath}/km/imeeting/resource/images/inner_person.png" />
								<span style="vertical-align: top;">
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdInnerPerson"/>：<c:out value="${kmImeetingSummaryForm.fdPlanParticipantPersonNames }"></c:out>
								</span>
							</c:if>
							<%--外部参加人员--%>
							<c:if test="${ not empty kmImeetingSummaryForm.fdPlanOtherParticipantPersons }">
								<br/><br/>
								<img src="${LUI_ContextPath}/km/imeeting/resource/images/other_person.png" />
								<span style="vertical-align: top;">
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>：<c:out value="${kmImeetingSummaryForm.fdPlanOtherParticipantPersons }"></c:out>
								</span>
							</c:if>
							
						</c:if>
					</td>
				</tr>
				<tr>
					<!-- 实际与会人员 -->
					<td class="td_normal_title" width=15%>
					   <bean:message bundle="km-imeeting" key="kmImeetingSummary.fdActualAttendPersons" />
					</td>
					<td colspan="3">
						<xform:address mulSelect="true" isLoadDataDict="false" required="false" style="width:48%;height:80px" textarea="true"
							 propertyId="fdActualAttendPersonIds" subject="${lfn:message('km-imeeting:kmImeetingSummary.fdActualAttendPersons')}" 
							 propertyName="fdActualAttendPersonNames" orgType='ORG_TYPE_ALL'></xform:address>
					    <%-- <xform:dialog icon="orgelement" propertyId="fdActualAttendPersonIds" propertyName="fdActualAttendPersonNames" 
					   		style="width:48%;height:80px" textarea="true"  validators="attendpersonnotnull">
						   Dialog_Address(true, 'fdActualAttendPersonIds','fdActualAttendPersonNames',';',ORG_TYPE_ALL);
						</xform:dialog>  --%>
						<xform:textarea property="fdActualOtherAttendPersons" validators="attendpersonnotnull maxLength(1500)"
							style="width:48%;height:80px;border:1px solid #b4b4b4" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingSummary.fdActualOtherAttendPersons') }'"/> 
						<span class="txtstrong">*</span>
					</td>
				</tr>
				<tr>
					<%-- 抄送人员--%>
					<td class="td_normal_title" width=15%>
				   		<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdCopyToPersons" />
					</td>
					<td colspan="3">
						<xform:address propertyName="fdCopyToPersonNames" propertyId="fdCopyToPersonIds" style="width:97%;" textarea="true" mulSelect="true"></xform:address>
					</td>
				</tr>
				<tr>
					<%-- 编辑方式--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdContentType" />
					</td>
					<td width=85% colspan="3">
						<xform:radio property="fdContentType" showStatus="edit"   onValueChange="checkEditType">
							<xform:enumsDataSource enumsType="kmImeetingSummary_fdContentType" />
						</xform:radio>	
					</td>
				</tr>
				<tr>
					<%-- 编辑内容--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingSummary.docContent" />
					</td>
					<td width=85% colspan="3">
						<html:hidden property="fdHtmlContent" />
						<div id="rtfEdit"  <c:if test="${kmImeetingSummaryForm.fdContentType!='rtf'}">style="display:none"</c:if>>
							<kmss:editor property="docContent" toolbarSet="Default" width="97%"/>
						</div>
						
						<!-- display的值none时，会影响金格加载，启用WPS时需要将display设置为none -->
						<c:choose>
							<c:when test="${pageScope._isWpsWebCloudEnable == 'true' or pageScope._isWpsWebOfficeEnable == 'true' or pageScope._isWpsCenterEnable == 'true'}">
								<div id="wordEdit" <c:if test="${kmImeetingSummaryForm.fdContentType=='rtf'}">style="width: 0px; height: 0px; filter: alpha(opacity = 0); opacity: 0;"</c:if>>
							</c:when>
							<c:otherwise>
								<div id="wordEdit" style="width:0px;height:0px;">
							</c:otherwise>
						</c:choose>
						
							<c:choose>
								<c:when test="${pageScope._isWpsWebCloudEnable == 'true'}">
									<c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_edit.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="editonline" />
										<c:param name="load" value="true" />
										<c:param name="bindSubmit" value="false"/>	
										<c:param name="fdModelId" value="${kmImeetingSummaryForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
										<c:param name="formBeanName" value="kmDocKnowledgeForm" />
										<c:param name="fdTemplateModelId" value="" />
										<c:param name="fdTemplateModelName" value="" />
										<c:param name="fdTemplateKey" value="" />
										<c:param name="fdTempKey" value="${kmImeetingSummaryForm.fdTemplateId}" />
									</c:import>
								</c:when>
								<c:when test="${pageScope._isWpsCenterEnable == 'true'}">
									<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_edit.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="editonline" />
										<c:param name="load" value="true" />
										<c:param name="bindSubmit" value="false"/>	
										<c:param name="fdModelId" value="${kmImeetingSummaryForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
										<c:param name="formBeanName" value="kmDocKnowledgeForm" />
										<c:param name="fdTemplateModelId" value="" />
										<c:param name="fdTemplateModelName" value="" />
										<c:param name="fdTemplateKey" value="" />
										<c:param name="fdTempKey" value="${kmImeetingSummaryForm.fdTemplateId}" />
									</c:import>
								</c:when>
								<c:when test="${pageScope._isWpsWebOfficeEnable == 'true'}">
									<c:import url="/sys/attachment/sys_att_main/wps/sysAttMain_edit.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="editonline" />
										<c:param name="load" value="true" />
										<c:param name="bindSubmit" value="false"/>	
										<c:param name="fdModelId" value="${kmImeetingSummaryForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
										<c:param name="formBeanName" value="kmImeetingSummaryForm" />
										<c:param name="fdTemplateModelId" value="" />
										<c:param name="fdTemplateModelName" value="" />
										<c:param name="fdTemplateKey" value="" />
										<c:param name="fdTempKey" value="${kmImeetingSummaryForm.fdTemplateId}" />
									</c:import>
								</c:when>
								<c:when test="${pageScope._isWpsWebOffice == 'true'}">
									<c:import url="/sys/attachment/sys_att_main/wps/oaassist/sysAttMain_edit.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="editonline" />
										<c:param name="fdMulti" value="false" />
										<c:param name="fdModelId" value="${kmImeetingSummaryForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
										<c:param name="formBeanName" value="kmImeetingSummaryForm" />
										<c:param name="fdTemplateModelId" value="${kmImeetingSummaryForm.fdTemplateId}" />
										<c:param name="fdTemplateModelName" value="" />
										<c:param name="fdTemplateKey" value="editonline" />
										<c:param name="templateBeanName" value="kmImeetingSummaryForm" />
										<c:param name="showDelete" value="false" />
										<c:param name="wpsExtAppModel" value="imeetingSummary" />
										<c:param name="canRead" value="false" />
										<c:param name="addToPreview" value="false" />
										<c:param  name="hideTips"  value="true"/>
										<c:param  name="hideReplace"  value="true"/>
										<c:param  name="canChangeName"  value="true"/>
										<c:param name="canEdit" value="true" />
										<c:param name="canPrint" value="false" />
										<c:param  name="filenameWidth"  value="300"/>
										<c:param name="load" value="false" />
									</c:import>
								</c:when>
								<c:otherwise>
									<div id="wordEditWrapper"></div>
									<div id="missiveButtonDiv" style="text-align:right; display: none;"></div>
									<div id="wordEditFloat" style="width: 1px; height: 1px; filter: alpha(opacity = 0); opacity: 0;">
										<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_edit.jsp" charEncoding="UTF-8">
											<c:param name="fdKey" value="editonline" />
											<c:param name="fdAttType" value="office" />
											<c:param name="fdModelId" value="${kmImeetingSummaryForm.fdId}" />
											<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
											<c:param name="bindSubmit" value="false"/>
											<c:param name="isTemplate" value="true"/>
											<c:param  name="attHeight" value="550"/>
											<c:param name="buttonDiv" value="missiveButtonDiv" />
											<c:param name="load" value="false" />
										</c:import>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
				<tr>
			 		<%--相关资料--%>
			 		<td class="td_normal_title" width=15%>
			 			<bean:message bundle="sys-attachment" key="table.sysAttMain"/>
			 		</td>
			 		<td width="85%" colspan="3" >
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
							<c:param name="fdKey" value="attachment" />
							<c:param name="fdModelId" value="${JsParam.fdId }" />
							<c:param name="uploadAfterSelect" value="true" />
							<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
						</c:import>
					</td>
			 	</tr>
			 	
			 	<!-- 盖章文件 -->
			 	<c:if test="${kmImeetingSummaryForm.fdSignEnable == 'true' }">
			 	<tr>
			 		<td class="td_normal_title" width=15%>
			 			<bean:message bundle="km-imeeting" key="KmImeetingSummary.fdSignFile"/>
			 		</td>
			 		<td width="85%" colspan="3" >
			 			<div style="padding:10px 0"><font color="red"><bean:message bundle="km-imeeting" key="kmImeetingSummary.file.big"/></font></div>
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
							<c:param name="fdKey" value="fdSignFile" />
							<c:param name="fdModelId" value="${JsParam.fdId }" />
							<c:param name="enabledFileType" value=".pdf;.doc;.xls;.ppt;.docx;.xlsx;.pptx;.jpg;.jpeg;.png;" />
							<c:param name="uploadAfterSelect" value="true" />
							<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
							<c:param name="fdRequired" value="true" />
						</c:import>
					</td>
			 	</tr>
			 	</c:if>
			 	
		 		<tr>
			 		<%--会议组织人--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdEmcee"/>
					</td>			
					<td width="35%" >
						<xform:address propertyName="fdEmceeName" propertyId="fdEmceeId" orgType="ORG_TYPE_PERSON" style="width:95%;"></xform:address>
					</td>
					<%--组织部门--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingSummary.docDept"/>
					</td>			
					<td width="35%" >
						<xform:address propertyName="docDeptName" propertyId="docDeptId" orgType="ORG_TYPE_DEPT" style="width:95%;"></xform:address>
					</td>
			 	</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdNotifyType" />
					</td>
					<td colspan="3">
						<kmss:editNotifyType property="fdNotifyType" value="${kmImeetingSummaryForm.fdNotifyType}"/>
						<div class="notNull" id="fdNotifyType" style="display:none;padding-left:10px;border:solid #DFA387 1px;padding-top:8px;padding-bottom:8px;background:#FFF6D9;color:#C30409;margin-top:3px">×&nbsp;&nbsp;&nbsp;<bean:message bundle="km-imeeting" key="kmImeetingSummary.choose.notifytype"/></div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdNotifyPerson" />
					</td>
					<td colspan="3">
						<sunbor:multiSelectCheckbox formName="kmImeetingSummaryForm"  enumsType="kmImeetingSummary_fdNotifyPerson" property="fdNotifyPersonList"></sunbor:multiSelectCheckbox>
					</td>
				</tr>
				<tr>
					<%-- 纪要录入人--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingSummary.docCreator"/>
					</td>
					<td width="35%">
						<html:hidden property="docCreatorId"/><html:hidden property="docCreatorName"/>
						<c:out value="${kmImeetingSummaryForm.docCreatorName }"></c:out>
					</td>
					<%-- 录入时间--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-imeeting" key="kmImeetingSummary.docCreateTime"/>
					</td>
					<td width="35%">
						<html:hidden property="docCreateTime"/>
						<c:out value="${kmImeetingSummaryForm.docCreateTime }"></c:out>
					</td>
				</tr>
			</table>
		</div>
		<c:choose>
			<c:when test="${param.approveModel eq 'right'}">
				<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'
					var-supportExpand="true" var-expand="true">
					<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmImeetingSummaryForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
					</c:import>
					<c:if test="${kmImeetingSummaryForm.docStatus != '30'}">
						<%--流程--%>
						<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmImeetingSummaryForm" />
							<c:param name="fdKey" value="ImeetingSummary" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
						</c:import>
					</c:if>
				</ui:tabpanel>
			</c:when>
			<c:otherwise>
				<ui:tabpage expand="false" > 
					<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmImeetingSummaryForm" />
						<c:param name="fdKey" value="ImeetingSummary" />
					</c:import>
					<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmImeetingSummaryForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
					</c:import>
				</ui:tabpage>
			</c:otherwise>
		</c:choose>
	<c:if test="${param.approveModel ne 'right'}">
		</form>
	</c:if>	
<script language="JavaScript">
	var validation=$KMSSValidation(document.forms['kmImeetingSummaryForm']);
</script>
<script>
	seajs.use(['lui/jquery','lui/dialog','lui/topic','km/imeeting/resource/js/dateUtil'], function($,dialog,topic,dateUtil){
		
		$(document).ready(function(){
			$("#fdNotifyType").hide();
			$("input[name^='__notify_type_']").click(function() {
				checkNotifyType();
			});
		});
		
		window.relateToMeeting = function() {
			var url=Com_GetCurDnsHost()+Com_Parameter.ContextPath + 'km/imeeting/km_imeeting_main/kmImeetingMain.do?method=listMySummary';
		 	dialog.iframe(url,"${lfn:message('km-imeeting:kmImeeting.Notice.Choice')}",function(id){
		 		if (id!=""&&id!=null){
				    var url = "${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=add&meetingId="+ id;
				    window.open(url,"_self");
				}
			},{width:950,height:550});
		};
		
		//通知方式
		window.checkNotifyType = function() {
			var fdNotifyType = document.getElementsByName("fdNotifyType")[0].value;
			//提示【通知方式不能为空】
			if(null == fdNotifyType || fdNotifyType==""){
				$("#fdNotifyType").show();
				$("input[type='checkbox']").focus();
				return false;
			}else{
				$("#fdNotifyType").hide();
				return true;
			}
		};
		
		//自动带出实际参会人员
		window.changeActualAttendPerson=function(){
			var values=[];
			var oldIds = $('[name="fdActualAttendPersonIds"]').val();
			var oldNames = $('[name="fdActualAttendPersonNames"]').val();
			if(oldIds && oldIds !=""){ 
				var oldIdArr = oldIds.split(";");
				var oldNameArr = oldNames.split(";");
				for(var i = 0;i<oldIdArr.length;i++){
					var obj = {
						id:	oldIdArr[i],
						name:oldNameArr[i]
					}
					values.push(obj);
				}
			}
			var attendIds=$('[name="fdPlanAttendPersonIds"]').val(),
			attendNames=$('[name="fdPlanAttendPersonNames"]').val(),
			participantIds=$('[name="fdPlanParticipantPersonIds"]').val(),
			participantNames=$('[name="fdPlanParticipantPersonNames"]').val();
			var actualAttendIds=joinPerson(attendIds,participantIds),
				actualAttendNames=joinPerson(attendNames,participantNames);
			$('[name="fdActualAttendPersonIds"]').val(actualAttendIds);
			$('[name="fdActualAttendPersonNames"]').val(actualAttendNames);	
			
			var idArr = actualAttendIds.split(";");
			var nameArr = actualAttendNames.split(";");
			for(var i = 0;i<idArr.length;i++){
				if(oldIds.indexOf(idArr[i]) == -1){
					var obj = {
						id:	idArr[i],
						name:nameArr[i]
					}
					values.push(obj);
				}
			}
			var address = Address_GetAddressObj("fdActualAttendPersonNames");
			address.reset(";",ORG_TYPE_ALL,true,values);
		};
		
		//自动带出实际参会外部人员
		window.changeOtherActualAttend=function(){
			var otherAttend=$('[name="fdPlanOtherAttendPerson"]').val(),
				otherParticipant=$('[name="fdPlanOtherParticipantPersons"]').val();
			$('[name="fdActualOtherAttendPersons"]').val(joinPerson(otherAttend,otherParticipant));
		};
		
		function joinPerson(){
			var slice=Array.prototype.slice,
				args=slice.call(arguments,0),
				arr=[];
			for(var i=0;i<args.length;i++){
				if(args[i]){
					arr.push(args[i]);
				}
			}
			return arr.join(';');
		}

		//选择会议地点
		window.selectHoldPlace=function(){
			var resId=$('[name="fdPlaceId"]').val();//地点ID
			var resName=$('[name="fdPlaceName"]').val();//地点Name
			var url="/km/imeeting/km_imeeting_res/kmImeetingRes_showAllResDialog.jsp?"+"&resId="+resId+"&resName="+encodeURIComponent(resName);
			dialog.iframe(url,"${lfn:message('km-imeeting:kmImeeting.ConferenceRoom.Choice')}",function(arg){
				if(arg){
					$('[name="fdPlaceId"]').val(arg.resId);
					$('[name="fdPlaceName"]').val(arg.resName);
				}
				validation.validateElement($('[name="fdPlaceName"]')[0]);
			},{width:800,height:500});
		};
		
		window.selectHoldVicePlace = function(){
			var resId = $('[name="fdVicePlaceIds"]').val();//地点ID
			var url = "/km/imeeting/km_imeeting_res/kmImeetingRes_showAllResDialog.jsp?multiSelect=true";
			url = Com_SetUrlParameter(url,'resId',resId);
			dialog.iframe(url,'会议室选择',function(arg){
				if(arg){
					$('[name="fdVicePlaceIds"]').val(arg.resId);
					$('[name="fdVicePlaceNames"]').val(arg.resName);
				}
			},{width:800,height:500});
		};
		
		//计算会议历时时间,返回数组,依次为:总时差、小时时差、分钟时差、……
		var _caculateDuration=function(start,end){
			if( start && end ){
				start=Com_GetDate(start,'datetime');
				end=Com_GetDate(end,'datetime');
				if(start.getTime()<end.getTime()){
					var total=end.getTime()-start.getTime();
					var hour=parseInt((end.getTime()-start.getTime() )/(1000*60*60));
					var minute=parseInt((end.getTime()-start.getTime() )%(1000*60*60)/(1000*60));
					return [total,hour,minute];
				}else{
					return [0.0,0,0];
				}
			}
		};
		
		//修改会议时间时，联动修改会议历时
		window.changeDateTime = function(___value,___element){
			var fdHoldDate=$('[name="fdHoldDate"]').val();//召开时间
			var fdFinishDate=$('[name="fdFinishDate"]').val();//结束时间
			
			//选择了开始时间后，结束时间默认带出
			if( fdHoldDate && !fdFinishDate && ___element && ___element.name =='fdHoldDate' ){
				var ___fdHoldDate = Com_GetDate(fdHoldDate,'datetime');
				___fdHoldDate.setHours(___fdHoldDate.getHours()+1);
				var ___finishDate = dateUtil.formatDate(___fdHoldDate,'${dateTimeFormatter}');
				$('[name="fdFinishDate"]').val(___finishDate);
				fdFinishDate=___finishDate;
			}
			
			if(fdHoldDate && fdFinishDate){
				//如果结束日期早于召开日期，自动调整结束日期为开始日期
				if(Com_GetDate(fdHoldDate,'datetime').getTime()>Com_GetDate(fdFinishDate,'datetime').getTime()){
					var ___fdHoldDate = Com_GetDate(fdHoldDate,'datetime');
					___fdHoldDate.setHours(___fdHoldDate.getHours()+1);
					var ___finishDate = dateUtil.formatDate(___fdHoldDate,'${dateTimeFormatter}');
					$('[name="fdFinishDate"]').val(___finishDate);
					fdFinishDate=___finishDate;
				}
				var duration=_caculateDuration(fdHoldDate,fdFinishDate);
				//设置会议历时
				$('[name="fdHoldDuration"]').val(duration[0]);
				$('[name="fdHoldDurationHour"]').val(duration[1]);
				$('[name="fdHoldDurationMin"]').val(duration[2]);
			}
		};
		
		//修改会议历时时触发
		window.changeDuration=function(){
			var fdHoldDurationHour=$('[name="fdHoldDurationHour"]').val();
			var fdHoldDurationMin=	$('[name="fdHoldDurationMin"]').val();
			var totalHour=dateUtil.mergeTime({"hour":fdHoldDurationHour, "minute":fdHoldDurationMin},"ms" );
			$('[name="fdHoldDuration"]').val(totalHour);
		};
		
		//初始化会议历时
		if('${kmImeetingSummaryForm.fdHoldDuration}'){
			//将小时分解成时分
			var timeObj=dateUtil.splitTime({"ms":"${kmImeetingSummaryForm.fdHoldDuration}"});
			$('[name="fdHoldDurationHour"]').val(timeObj.hour);
			$('[name="fdHoldDurationMin"]').val(timeObj.minute);
		}
		
		//校验召开时间不能晚于结束时间
		var _compareTime=function(){
			var fdHoldDate=$('[name="fdHoldDate"]');
			var fdFinishedDate=$('[name="fdFinishDate"]');
			var result=true;
			if( fdHoldDate.val() && fdFinishedDate.val() ){
				var start=dateUtil.parseDate(fdHoldDate.val());
				var end=dateUtil.parseDate(fdFinishedDate.val());
				if( start.getTime()>=end.getTime() ){
					result=false;
				}
			}
			return result;
		};
		//自定义校验器:校验召开时间不能晚于结束时间
		validation.addValidator('compareTime','${lfn:message("km-imeeting:kmImeetingMain.fdDate.tip")}',function(v, e, o){
			 var fdHoldDate=document.getElementsByName('fdHoldDate')[0];
			 var result=true;
			 if(e.name=="fdFinishDate"){//fdFinishDate的这个校验与fdHoldDate的相同，直接执行fdHoldDate的
				 validation.validateElement(fdHoldDate);
			 }else{
				 result= _compareTime();
			 }
			return result;
			
		});
		
		//自定义校验器:校验“实际与会人员”和“实际其他与会人员”不能全为空
		var attendPersonNotNullStr="${lfn:message('errors.required')}".replace('{0}',"${lfn:message('km-imeeting:kmImeetingSummary.fdActualAttendPersons')}");
		validation.addValidator('attendpersonnotnull',attendPersonNotNullStr,function(v, e, o){
			 var fdActualAttendPersonNames=document.getElementsByName('fdActualAttendPersonNames')[0];
			 var fdActualOtherAttendPersons=document.getElementsByName('fdActualOtherAttendPersons')[0];
			 var result= true;
			 if(!fdActualAttendPersonNames.value
					 && !fdActualOtherAttendPersons.value){
				 result=false;
			 }
			 if(result==false){
				KMSSValidation_HideWarnHint(fdActualAttendPersonNames);
				KMSSValidation_HideWarnHint(fdActualOtherAttendPersons);
			}
			return result;
		});
		
		//自定义校验器:校验“地点”和“其他地点”不能全为空
		var placeNotNullStr="${lfn:message('errors.required')}".replace('{0}',"${lfn:message('km-imeeting:kmImeetingMain.fdPlace')}");
		validation.addValidator('placenotnull',placeNotNullStr,function(v, e, o){
			 var fdPlaceName=document.getElementsByName('fdPlaceName')[0];
			 var fdOtherPlace=document.getElementsByName('fdOtherPlace')[0];
			 var result= true;
			 if(!fdPlaceName.value
					 && !fdOtherPlace.value){
				 result=false;
			 }
			 if(result==false){
				KMSSValidation_HideWarnHint(fdPlaceName);
				KMSSValidation_HideWarnHint(fdOtherPlace);
			}
			return result;
		});

		//切换编辑方式
		 window.checkEditType=function(value, obj){
			var _wordEdit = $('#wordEdit');
			var _rtfEdit = $('#rtfEdit');
			var wordFloat = $("#wordEditFloat");
			if (_rtfEdit == null || _wordEdit == null) {
				return ;
			}
			if("word" == value){
				_wordEdit.css({'width':'auto','height':'auto','filter':'alpha(opacity=100)','opacity':'1'});
				wordFloat.css({'width':'auto','height':'auto','filter':'alpha(opacity=100)','opacity':'1'});
				_rtfEdit.css({'display':"none"});
				if ("${pageScope._isWpsWebOfficeEnable}" == "true" ){
					wps_editonline.load();
				}else if("${pageScope._isWpsWebOffice}" == "true" && "${pageScope.wpsoaassistEmbed}" == "false"){
					$('#uploader_editonline').show();
					_wordEdit.css({'display':"block",'width':"98%",'height':"150px"});
				}else if("${pageScope._isWpsWebOffice}" == "true" && "${pageScope.wpsoaassistEmbed}" == "true" && "${pageScope.isWindowsInOAassist}" == "false"){
					_wordEdit.css({'display':"block",'width':"98%",'height':"700px"});
					wps_linux_editonline.load();
				} else if("${pageScope._isWpsCenterEnable}" == "true" ) {
					wps_center_editonline.load();
				} else {
					var obj = document.getElementById("JGWebOffice_editonline");
					setTimeout(function(){
						if(obj && !jg_attachmentObject_editonline.getOcxObj()){
							dialog.alert('<bean:message bundle="km-imeeting" key="tip.kmImeeting.noJg" />');
						}else{
							if(obj&&Attachment_ObjectInfo['editonline'] && !jg_attachmentObject_editonline.hasLoad){
								jg_attachmentObject_editonline.load();
								jg_attachmentObject_editonline.show();
								if(jg_attachmentObject_editonline.ocxObj){
									jg_attachmentObject_editonline.ocxObj.Active(true);
								}
							 }
						}
					},1000);
					$("#JGWebOffice_editonline").height("550px");
					chromeHideJG_2015(1);
				}
				seajs.use(['lui/topic'],function(topic){
					topic.subscribe("Sidebar", function(data){
						wordFloat.css({'width':'auto','height':'auto','filter':'alpha(opacity=100)','opacity':'1'});
					});
				});
			} else {
				if("${pageScope._isWpsWebOffice}" == "true")
			     {
					 $('#uploader_editonline').hide();
					}
				_rtfEdit.css({'display':"block"}); 
				if ("${pageScope._isWpsWebOfficeEnable}" == "true" || "${pageScope._isWpsWebCloudEnable}" == "true" || "${pageScope._isWpsCenterEnable}" == "true") {
					_wordEdit.css({'width':'0','height':'0','filter':'alpha(opacity=0)','opacity':'0'});
				}
				
				wordFloat.css({'width':'0px','height':'0px','filter':'alpha(opacity=0)','opacity':'0'});
				$("#JGWebOffice_editonline").height("0px");
				chromeHideJG_2015(0);
			}
		};

		//提交纪要
		window.commitMethod=function(commitType, saveDraft){
			var formObj = document.kmImeetingSummaryForm;
			var docStatus = document.getElementsByName("docStatus")[0];
			if(saveDraft=="true"){
				_removeRequireValidate();
				docStatus.value="10";
				Com_Submit(formObj, commitType);
			}else{
				validation.resetElementsValidate(formObj);
				docStatus.value="20";
				var fdSignEnable = document.getElementsByName("fdSignEnable")[0];
				var signEnable = fdSignEnable.value;
				if("true" == signEnable){
					var attendPersonIds = $('[name="fdActualAttendPersonIds"]').val();
					$.ajax({
						url: "${LUI_ContextPath}/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=checkPhoneNo",
						type: 'POST',
						dataType: 'json',
						data: { 
							attendPersonIds : attendPersonIds
						},
						success: function(data, textStatus, xhr) {//操作成功
							if(data && data.names){
								dialog.alert("${lfn:message('km-imeeting:kmImeetingSummary.no.phone')}".replace('%names%',data.names));
							}else{
								Com_Submit(formObj, commitType);
							}
						}
					});
				}else{
					Com_Submit(formObj, commitType);
				}
			}
			
		};
		
		//移除必填校验
		function _removeRequireValidate(){
			var formObj = document.kmImeetingSummaryForm;
			validation.removeElements(formObj,'required');//不校验单字段必填
			validation.removeElements(formObj,'attendpersonnotnull');//不校验参加人员不全为空
			validation.removeElements(formObj,'placenotnull');//不校验地点不全为空
			validation.addElements($('[name="fdName"]')[0],'required');//标题还是要必填
		}

		//文档提交时调用该方法进行验证
		window.checkForm=function(){
			return true;
		};

		//*******word编辑方式相关JS（开始）****************//
		
		 Com_AddEventListener(window, "load", function() {
				<c:if test="${kmImeetingSummaryForm.fdContentType=='rtf'}">
						checkEditType("${kmImeetingSummaryForm.fdContentType}", null);
				</c:if>
				<c:if test="${kmImeetingSummaryForm.fdContentType=='word'}">
					checkEditType("${kmImeetingSummaryForm.fdContentType}", null);
				</c:if>
		});
		
		
		var hasSetHtmlToContent = false;
		var htmlContent = "";

		Com_Parameter.event["submit"].push(function() {
			var type=$('[name="fdContentType"]:checked').val();
			if ("word" == type) {
				if ("${pageScope._isWpsWebOfficeEnable}" != "true") {
					var obj = document.getElementById("JGWebOffice_editonline");
					// 保存附件
					if(obj&&Attachment_ObjectInfo['editonline']&&jg_attachmentObject_editonline.hasLoad){
						jg_attachmentObject_editonline._submit();
					}
					// 保存附件为html
					//if(!JG_WebSaveAsHtml()){return false;}
				} else {
					if ("${pageScope._isWpsWebOfficeEnable}" == "true" ){
						wps_editonline.submit();
					}
				}
			}
			return true;
		});
		Com_Parameter.event["confirm"].push(function() {
			if (hasSetHtmlToContent) {
				document.kmImeetingSummaryForm.fdHtmlContent.value = htmlContent;
			}
			return checkNotifyType();
		});
		
		//*******word编辑方式相关JS（结束）****************//
		
		$('#wordEditFloat .jg_tip_box').hide();
		$('#wordEditFloat .jg_tip_container').hide();
	});
</script>
</template:replace>
<c:if test="${param.approveModel eq 'right'}">
	<template:replace name="barRight">
		<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
			<c:choose>
				<c:when test="${kmImeetingSummaryForm.docStatus>='30' || kmImeetingSummaryForm.docStatus=='00'}">
					<!-- 基本信息-->
					<c:import url="/km/imeeting/km_imeeting_summary/kmImeetingSummary_viewBaseInfo.jsp" charEncoding="UTF-8">
					</c:import>
				</c:when>
				<c:otherwise>
					<%--流程--%>
					<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmImeetingSummaryForm" />
						<c:param name="fdKey" value="ImeetingSummary" />
						<c:param name="showHistoryOpers" value="true" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
						<c:param name="approvePosition" value="right" />
					</c:import>
				</c:otherwise>
			</c:choose>
		</ui:tabpanel>
	</template:replace>
</c:if>