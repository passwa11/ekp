<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@page import="com.landray.kmss.km.imeeting.util.KKUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.Map"%>
<%--内容区--%>
<template:replace name="content"> 
	<c:if test="${kmImeetingMainForm.method_GET=='add'}">
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
			if('${JsParam.fdTemplateId}'==''){
				var createUrl = "/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId=!{id}";
				if ('${param.fdWorkId}'!=null && '${param.fdWorkId}'!="") {
					createUrl += "&fdWorkId=${param.fdWorkId}";
				}
				if ('${param.fdTime}'!=null && '${param.fdTime}'!="") {
					createUrl += "&fdTime=${param.fdTime}";
				}
				if ('${param.fdStartTime}'!=null && '${param.fdStartTime}'!="") {
					createUrl += "&fdStartTime=${param.fdStartTime}";
				}
				if ('${param.fdFinishTime}'!=null && '${param.fdFinishTime}'!="") {
					createUrl += "&fdFinishTime=${param.fdFinishTime}";
				}
				if ('${param.resId}'!=null && '${param.resId}'!="") {
					createUrl += "&resId=${param.resId}";
				}
				window.changeDocTemp('com.landray.kmss.km.imeeting.model.KmImeetingTemplate',createUrl,true);
			}
			
			function changeEmcee(rtn,obj){
				if(rtn[0]!=""){
					var fdOrgId = rtn[0];
					 var url="${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_main/kmImeetingMain.do?method=getDept"; 
					 $.ajax({  
					     url:url,  
					     data:{fdOrgId:fdOrgId},   
					     async:false,    //用同步方式 
					     success:function(data){
					    	 var results =  eval("("+data+")");
					    	var values=[];
						    if(results['deptId'] && results['deptName']){
						    	var obj={
						    		id:	results['deptId'],
						    		name:results['deptName']
						    	};
						    	values.push(obj);
							}
							var address = Address_GetAddressObj("docDeptName");
							address.reset(";",ORG_TYPE_ORGORDEPT,false,values);
						}    
				    });
				}
			}
		</script>
	</c:if>
	<c:if test="${param.approveModel ne 'right'}">
		<form name="kmImeetingMainForm" method="post" action ="${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_main/kmImeetingMain.do">
	</c:if>
		<html:hidden property="fdId" />
		<html:hidden property="docStatus" />
		<html:hidden property="docCreatorId" />
		<html:hidden property="docCreateTime" />
		<html:hidden property="fdNotifyerId" />
		<html:hidden property="fdChangeMeetingFlag" />
		<html:hidden property="fdSummaryFlag" />
		<html:hidden property="method_GET" />
		<html:hidden property="fdModelId" value = "${lfn:escapeHtml(kmImeetingMainForm.fdModelId)}" />
		<html:hidden property="fdModelName" value = "${lfn:escapeHtml(kmImeetingMainForm.fdModelName)}" />
		<html:hidden property="fdPhaseId" value = "${lfn:escapeHtml(kmImeetingMainForm.fdPhaseId)}" />
		<html:hidden property="fdWorkId" value = "${lfn:escapeHtml(kmImeetingMainForm.fdWorkId)}" />
		<html:hidden property="fdTemplateId"/>
		<html:hidden property="bookId" value="${HtmlParam.bookId }"/>
		
		<c:if test="${param.approveModel eq 'right'}">
			<c:set var="layout" value="sys.ui.step.fixed"></c:set>
		</c:if>
		<ui:step id="__step"  onSubmit="commitMethod('save','false');" layout="${layout}">
			<%--会议信息填写--%>
			<ui:content title="${lfn:message('km-imeeting:kmImeetingMain.createStep.base') }" toggle="false" id="validate_ele0">
				<div class="lui_form_content_frame">
				 <table class="tb_normal" width=100% id="Table_Main"> 
				 	<tr>
				 		<td colspan="4" class="com_subject" style="font-size: 110%;font-weight: bold;">
				 			<bean:message bundle="km-imeeting" key="kmImeetingMain.createStep.base"/>
				 		</td>
				 	</tr>
				 	<tr>
				 		<%--会议名称--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdName"/>
						</td>			
						<td width="85%" colspan="3">
							<xform:text property="fdName" style="width:95%" />		 	
						</td>
				 	</tr>
				 	<tr>
				 		<%--召开时间--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate"/>
						</td>			   
						<td width="35%" >
							<xform:datetime property="fdHoldDate" dateTimeType="datetime" showStatus="edit" 
								onValueChange="changeDateTime"  required="true" validators="after compareTime"></xform:datetime>
							<span style="position: relative;top:-5px;">~</span>
							<xform:datetime property="fdFinishDate" dateTimeType="datetime" showStatus="edit" 
								onValueChange="changeDateTime" required="true" validators="after compareTime"></xform:datetime>
							<%--隐藏域,保存改变前的时间，用于回退--%>
							<input type="hidden" name="fdHoldDateTmp" value="${HtmlParam.kmImeetingMainForm.fdHoldDate}">
							<input type="hidden" name="fdFinishDateTmp" value="${HtmlParam.kmImeetingMainForm.fdFinishDate}">
							
						</td>
						<%--会议历时--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration"/>
						</td>
						<td width="35%">
							<input type="text" name="fdHoldDurationHour" validate="digits maxLength(4) min(0) validateDuration" class="inputsgl" 
								style="width:50px;text-align: center;"  onchange="changeDuration();" subject="${lfn:message('km-imeeting:kmImeetingMain.fdHoldDuration')}"/>
							<bean:message key="date.interval.hour"/>
							<input type="text" name="fdHoldDurationMin" validate="digits maxLength(4) min(0) validateDuration" class="inputsgl" 
								style="width:50px;text-align: center;"  onchange="changeDuration();"  subject="${lfn:message('km-imeeting:kmImeetingMain.fdHoldDuration')}"/>
							<bean:message key="date.interval.minute"/>
							<xform:text property="fdHoldDuration" showStatus="noShow" validators="validateDuration" subject="${lfn:message('km-imeeting:kmImeetingMain.fdHoldDuration')}"/>
						</td>
				 	</tr>
				 	<%
					 	if(KmImeetingConfigUtil.isBoenEnable() || KKUtil.isKkVideoMeetingEnable()){
					 %>
					 <tr>
				 		<%--回执结束时间--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdFeedBackDeadline"/>
						</td>			   
						<td width="85%"  colspan="3">
							<xform:datetime property="fdFeedBackDeadline" dateTimeType="datetime" showStatus="edit" subject="${lfn:message('km-imeeting:kmImeetingMain.fdFeedBackDeadline')}" required="true" validators="after valDeadline">
							</xform:datetime><font color="red"><bean:message bundle="km-imeeting" key="kmImeetingMain.feedbackDeadline" /></font>
						</td>
					</tr>	
					 <%} %>
				 	<c:if test="${ kmImeetingMainForm.isCycle eq 'true'}">
					 	<c:if test="${kmImeetingConfig.useCyclicity eq '2' || kmImeetingConfig.useCyclicity eq '3' && fn:contains(kmImeetingConfig.useCyclicityPersonId,userId) == true}">
					 	<!-- 周期性会议设置 -->
					 	<tr>
					 		<td class="td_normal_title" width=15%>
					 			<bean:message bundle="km-imeeting" key="kmImeetingMain.cyclicityConfig"/>
					 		</td>
					 		<td width="85%" colspan="3" class="customContainerTD">
								<div>
									<ui:recurrence id="fdRecurrence" property="fdRecurrenceStr" customContainer="#customContainer" isOn="true" cfg-finishDate="fdFinishDate"></ui:recurrence>
								</div>
								<div id="customContainer"></div>
					 		</td>
				 		</tr>
				 		</c:if>
			 		</c:if>
				 	<tr>
				 		<%--会议目的--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMeetingAim"/>
						</td>			
						<td width="85%" colspan="3" >
							<xform:textarea htmlElementProperties="data-actor-expand='true'" property="fdMeetingAim" style="width:95%;" />
						</td>
				 	</tr>
				 	<tr>
				 		<%--会议组织人--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingTemplate.fdEmcee"/>
						</td>			
						<td width="35%" >
							<xform:address propertyName="fdEmceeName" propertyId="fdEmceeId" orgType="ORG_TYPE_PERSON" style="width:95%;"  onValueChange="changeEmcee"></xform:address>
						</td>
						<%--组织部门--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingMain.docDept"/>
						</td>			
						<td width="35%" >
							<xform:address propertyName="docDeptName" propertyId="docDeptId" subject="${lfn:message('km-imeeting:kmImeetingMain.docDept') }"
								orgType="ORG_TYPE_ORG|ORG_TYPE_DEPT" style="width:90%;" required="true"></xform:address>
						</td>
				 	</tr>
				 	<tr>
				 		<%--会议发起人--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingMain.docCreator"/>
						</td>
						<%if(!ISysAuthConstant.IS_AREA_ENABLED) { %> 
							<td width="85%" colspan="3">
								<html:hidden property="docCreatorId"/>
								<c:out value="${kmImeetingMainForm.docCreatorName }"></c:out>
							</td>
						<%}else{ %>			
							<td width="35%" >
								<html:hidden property="docCreatorId"/>
								<c:out value="${kmImeetingMainForm.docCreatorName }"></c:out>
							</td>
							<%-- 所属场所 --%>
							<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field_single.jsp" charEncoding="UTF-8">
			                     <c:param name="id" value="${kmImeetingMainForm.authAreaId}"/>
			                </c:import>
		                <%} %>
				 	</tr>
				 	</table>
				 </div>
				 <div class="lui_form_content_frame">
				 	<div class="swichDiv"> 
		 			<% if(KmImeetingConfigUtil.isVideoMeetingEnable()){%>
		 				<div class="videoDiv"><bean:message bundle="km-imeeting" key="kmImeetingMain.fdIsVideo" /><ui:switch property="fdIsVideo" showType="edit" checked="${ kmImeetingMainForm.fdIsVideo}"/></div>
		 				<div class="placeDiv"><bean:message bundle="km-imeeting" key="kmImeetingMain.fdNeedPlace" /><ui:switch property="fdNeedPlace" showType="edit" checked="${ kmImeetingMainForm.fdNeedPlace}" onValueChange="changeNeedPlace(false)"/></div>
		 			<%}else{%>
		 				<html:hidden property="fdIsVideo" value="false"/>
		 				<html:hidden property="fdNeedPlace" value="true"/>
		 			<%} %>	
		 			</div>
					 <table class="tb_normal" id="resTb" width=100% > 
					 	<tr onclick="toggleRes(this);">
					 		<td colspan="4" class="com_subject"  style="font-size: 110%;font-weight: bold;">
					 			<bean:message bundle="km-imeeting" key="kmImeetingMain.createStep.resourceService"/>
					 			<div class="expandDiv">
					 				<a onclick="javascript:void(0);" class="expandTxt com_btn_link"> 
					 					<bean:message bundle="km-imeeting" key="kmImeetingMain.expandFields" />
					 				</a>
					 			</div>
					 		</td>
					 	</tr>
					 	<tr>
					 		<%--选择会议室--%>
					 		<td class="td_normal_title" width=15%>
					 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdPlace"/>
					 		</td>
					 		<td width="85%" colspan="3">	
								<c:choose>
									<c:when test="${fdNeedMultiRes}">
							 			<xform:dialog propertyId="fdPlaceId" propertyName="fdPlaceName" showStatus="edit" validators="validateplace validateUserTime" className="inputsgl" style="width:46%;" 
							 				subject="${lfn:message('km-imeeting:kmImeetingMain.fdMainPlace') }"  htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdMainPlace') }'">
									  	 	selectHoldPlace();
										</xform:dialog>
										<input type="hidden" name="fdPlaceUserTime" value="${ kmImeetingMainForm.fdUserTime}"
												subject="${lfn:message('km-imeeting:kmImeetingMain.fdMainPlace') }"/>
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
											<xform:text property="fdOtherPlace" style="width:46%;" validators="validateplace" subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherMainPlace') }'"></xform:text>
										</c:if>
										<span class="txtstrong">*</span>
										<div class="roomDetail" style="display: none;margin: 5px 0;padding: 5px;background-color: #fff5d8;border: 1px solid #e0a385;"></div>
										<br/><br/>
										
							 			<xform:dialog propertyId="fdVicePlaceIds" propertyName="fdVicePlaceNames" showStatus="edit" validators="validateViceUserTime"
							 				className="inputsgl" style="width:46%;"  htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdVicePlaces') }'"
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
										<input type="hidden" name="fdPlaceUserTime" value="${ kmImeetingMainForm.fdUserTime}"
												subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }"/>
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
												<c:param name="placeholder" value="km-imeeting:kmImeetingMain.fdOtherPlace"></c:param>
												<c:param name="subject" value="${ lfn:message('km-imeeting:kmImeetingMain.fdPlace')}"></c:param>
												<c:param name="style" value="width:46%;"></c:param>
											</c:import>
										</c:if>
										<c:if test="${hasSysAttend == false }">
											<xform:text property="fdOtherPlace" style="width:46%;" validators="validateplace" subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherPlace') }'"></xform:text>
										</c:if>
										<span class="txtstrong">*</span>
										<div class="roomDetail" style="display: none;margin: 5px 0;padding: 5px;background-color: #fff5d8;border: 1px solid #e0a385;"></div>
									</c:otherwise>
								</c:choose>
							</td>
					 	</tr>
					 	<tr class="toggleRes" style="display: none">
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
					 	<tr class="toggleRes" style="display: none">
					 		<%--会议室辅助服务--%>
					 		<td class="td_normal_title" width=15% title="${lfn:message('km-imeeting:kmImeetingMain.kmImeetingDevices.tip') }">
					 			<bean:message bundle="km-imeeting" key="kmImeetingMain.kmImeetingDevices"/>
					 		</td>
					 		<td width="85%" colspan="3" >
								<ui:dataview>
									<ui:source type="AjaxJson">
										{url:'/km/imeeting/km_imeeting_device/kmImeetingDevice.do?method=listDevices&orderby=fdOrder&ordertype=up'}
									</ui:source>
									<ui:render type="Template">
										<c:import url="/km/imeeting/resource/tmpl/devices.jsp" charEncoding="UTF-8"></c:import>
									</ui:render>
								</ui:dataview>
							</td>
					 	</tr>
					 	<tr class="toggleRes" style="display: none">
					 		<%--会场布置要求--%>
					 		<td class="td_normal_title" width=15%>
					 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdArrange"/>
					 		</td>
					 		<td width="85%" colspan="3" >
								<xform:textarea property="fdArrange" style="width:93%;" htmlElementProperties="data-actor-expand='true'"></xform:textarea>
							</td>
					 	</tr>
					 	 <tr class="toggleRes" style="display: none">
					 		<%--会议协助人--%>
					 		<td class="td_normal_title" width=15%>
					 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAssistPersons"/>
					 		</td>
					 		<td width="85%" colspan="3" >
					 			<xform:address style="width:45%;height:80px" textarea="true" showStatus="edit"  propertyId="fdAssistPersonIds" propertyName="fdAssistPersonNames" orgType="ORG_TYPE_ALL" mulSelect="true"></xform:address>
						  		&nbsp;&nbsp;
						  		<xform:textarea style="width:45%;border:1px solid #b4b4b4" property="fdOtherAssistPersons" validators="maxLength(1500)"
						  			htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherAssistPersons') }' data-actor-expand='true'"/>
							</td>
					 	</tr>
					 	</table>
				 	</div>
				 	<div class="lui_form_content_frame">
						 <table class="tb_normal" width=100%> 
						 	<tr >
						 		<td colspan="4" class="com_subject"  style="font-size: 110%;font-weight: bold;">
						 			<bean:message bundle="km-imeeting" key="kmImeetingMain.createStep.base.fdAttendPersons"/>
						 			<div class="expandDiv" onclick="toggleOrg(this);">
						 				<a onclick="javascript:void(0);" class="expandTxt com_btn_link">
						 					<bean:message bundle="km-imeeting" key="kmImeetingMain.expandFields" />
						 				</a>
						 			</div>
						 			<div style="float: right;margin-right: 30px;border: 0px;color: #333;">
						 				<ui:button text="${lfn:message('km-imeeting:kmImeetingMain.checkFree.text') }"  onclick="checkFree();" href="javascript:void(0);"
						 					title="${lfn:message('km-imeeting:kmImeetingMain.checkFree.title') }"/>
						 					<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAttendNum.tip1"/>
						 					<xform:text property="fdAttendNum" validators="min(0)  max(2147483647)" style="width:40px;text-align:center;"/>
						 					<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAttendNum.tip2"/>
						 			</div>
						 			
						 		</td>
						 	</tr>
						 	<tr>
						 		<%--主持人--%>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHost"/>
								</td>			
								<td width="85%" colspan="3" >
									<xform:address propertyName="fdHostName" propertyId="fdHostId" orgType="ORG_TYPE_PERSON" style="width:47%;" onValueChange="caculateAttendNum"></xform:address>&nbsp;
									&nbsp;&nbsp;
									<xform:text property="fdOtherHostPerson" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherHostPerson') }'" style="width:47%;position: relative;top:-4px;" />
								</td>
						 	</tr>
						 	<%
							 	if(KmImeetingConfigUtil.isBoenEnable()){
							 %>
						 	<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdControlPerson"/>
								</td>			
								<td width="85%" colspan="3" >
									<xform:address style="width:95%;height:80px;" required="true" textarea="false" showStatus="edit" propertyName="fdControlPersonName" propertyId="fdControlPersonId" 
										orgType="ORG_TYPE_PERSON" mulSelect="false"  subject="${lfn:message('km-imeeting:kmImeetingMain.fdControlPerson') }" >
									</xform:address>
								</td>
						 	</tr>
						 	<tr>
						 		<%--监票人--%>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdBallotPerson"/>
								</td>			
								<td width="85%" colspan="3" >
								<xform:address  style="width:95%;height:80px;"  textarea="false" showStatus="edit"  propertyId="fdBallotPersonIds" propertyName="fdBallotPersonNames" 
										orgType="ORG_TYPE_PERSON" mulSelect="false"  subject="${lfn:message('km-imeeting:kmImeetingMain.fdBallotPerson') }"></xform:address>
								</td>
						 	</tr>
						 	<%
							 	}
							 %>
						 	<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAttendPersons"/>
								</td>			
								<td width="85%" colspan="3" >
									<%--参加人员--%>
									<xform:address  style="width:46%;height:80px;" textarea="true" showStatus="edit"  propertyId="fdAttendPersonIds" propertyName="fdAttendPersonNames" 
										orgType="ORG_TYPE_ALL" mulSelect="true" onValueChange="caculateAttendNum" validators="validateattend"
										subject="${lfn:message('km-imeeting:kmImeetingMain.fdAttendPersons') }"></xform:address>
							  		&nbsp;&nbsp;
							  		<%--外部参加人员--%>
							  		<xform:textarea style="width:46%;border:1px solid #b4b4b4"  property="fdOtherAttendPerson" showStatus="edit"
							  			htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherAttendPerson') }' data-actor-expand='true'"  validators="validateattend maxLength(1500)" 
							  			subject="${lfn:message('km-imeeting:kmImeetingMain.fdAttendPersons') }"/>
							  		<span class="txtstrong">*</span>
								</td>
						 	</tr>
						 	<tr>
						 		<%--纪要录入人--%>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdSummaryInputPerson"/>
								</td>
								<td width="35%" >
									<xform:address style="width:95%;"   propertyId="fdSummaryInputPersonId" propertyName="fdSummaryInputPersonName" 
										orgType="ORG_TYPE_PERSON" onValueChange="caculateAttendNum"  validators="validateSummaryInputPerson"></xform:address>
								</td>
								<%--纪要完成时间--%>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdSummaryCompleteTime"/>
								</td>			
								<td width="35%" >
									<xform:datetime property="fdSummaryCompleteTime" style="width:55%" showStatus="edit" dateTimeType="date"  validators="validateSummaryCompleteTime validateWithHoldDate"></xform:datetime>
									<%--是否催办纪要--%>
									<span>
							 			<input type="checkbox" style="margin-left:10px" name="fdIsHurrySummary" value="true" onclick="showHurryDayDiv();" 
											<c:if test="${kmImeetingMainForm.fdIsHurrySummary == 'true'}">checked</c:if>> 
											<bean:message bundle="km-imeeting" key="kmImeetingMain.fdIsHurrySummary" />
									</span>
									<span id="HurryDayDiv" style="display:none">
										&nbsp;<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHurrySummaryDay.0" />
										<xform:text validators="validateHurrySummaryDay" property="fdHurryDate" style="width:30px" showStatus="edit"/> 
										<bean:message	bundle="km-imeeting" key="kmImeetingMain.fdHurrySummaryDay.1" /> 
									</span> 
								</td>
						 	</tr>
						 	<tr class="toggleOrg" style="display: none">
						 		<%--列席人员--%>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdParticipantPersons"/>
								</td>			
								<td width="85%" colspan="3" >
									<xform:address style="width:46%;height:80px" textarea="true" showStatus="edit"  propertyId="fdParticipantPersonIds" propertyName="fdParticipantPersonNames" orgType="ORG_TYPE_ALL" mulSelect="true" onValueChange="caculateAttendNum"></xform:address>
							  		&nbsp;&nbsp;
							  		<xform:textarea style="width:46%;border:1px solid #b4b4b4" property="fdOtherParticipantPerson" showStatus="edit"  validators="maxLength(1500)"
							  			htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherParticipantPerson') }' data-actor-expand='true'"/>
								</td>
						 	</tr>
						 	<tr class="toggleOrg" style="display: none">
						 		<%--抄送人员--%>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdCopyToPersons"/>
								</td>			
								<td width="85%" colspan="3" >
									<xform:address style="width:46%;height:80px" textarea="true" showStatus="edit"  propertyId="fdCopyToPersonIds" propertyName="fdCopyToPersonNames" orgType="ORG_TYPE_ALL" mulSelect="true"></xform:address>
							  		&nbsp;&nbsp;
							  		<xform:textarea style="width:46%;border:1px solid #b4b4b4" property="fdOtherCopyToPerson" showStatus="edit"  validators="maxLength(1500)"
							  			htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherCopyToPerson') }' data-actor-expand='true'"/>
								</td>
						 	</tr>
						 	
					 	</table>
				 	</div>
				 	<div class="lui_form_content_frame">
						 <table class="tb_normal" width=100% id="Table_Main"> 
						 	 <tr>
						 		<td colspan="4" class="com_subject" style="font-size: 110%;font-weight: bold;">
						 			<bean:message bundle="km-imeeting" key="kmImeetingMain.createStep.base.agenda"/>
						 			<%
							 			if("true".equals(KmImeetingConfigUtil.isTopicMng())){
							 		%>
						 				&nbsp;&nbsp;&nbsp;<input type="button" class="lui_form_button"
						 				value="<bean:message key="kmImeetingAgenda.operation.addDetailTopic.mobile" bundle="km-imeeting"  />"
						 				onclick="selectTopicList();"/>
						 			<%} %>
						 		</td>
						 	</tr>
						 	<tr>
						 		<%--会议议程信息--%>
						 		<td colspan="4">
						 			<%
							 			if("true".equals(KmImeetingConfigUtil.isTopicMng())){
							 		%>
							 			<html:hidden property="fdIsTopic" value="1"/>
							 			<%@include file="/km/imeeting/km_imeeting_agenda/kmImeetingAgenda_editTopic.jsp"%>
							 		<%}else{ %>
							 			<html:hidden property="fdIsTopic" value="0"/>
							 			<%@include file="/km/imeeting/km_imeeting_agenda/kmImeetingAgenda_edit.jsp"%>
							 		<%} %>
						 		</td>
						 	</tr>
						 	<%
						 		if(!KmImeetingConfigUtil.isBoenEnable()){
					 		%>
						 		<tr>
							 		<%--相关资料--%>
							 		<td class="td_normal_title" width=15%>
							 			<bean:message bundle="km-imeeting" key="kmImeetingMain.attachment"/>
							 		</td>
							 		<td width="85%" colspan="3" >
										<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
											<c:param name="fdKey" value="attachment" />
											<c:param name="uploadAfterSelect" value="true" />
											<c:param name="fdModelId" value="${JsParam.fdId }" />
											<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
										</c:import>
									</td>
							 	</tr>
						 	<%} %>
						 	<%
							 	if(KmImeetingConfigUtil.isBoenEnable()){
							 %>
						 	<tr>
						 		<td class="td_normal_title" width=15%>
						 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdVoteEnable"/>
						 		</td>
						 		<td width="35%" >
									<ui:switch property="fdVoteEnable" showType="edit" checked="${kmImeetingMainForm.fdVoteEnable}"  checkVal="true" unCheckVal="false" onValueChange="voteEnableChange()"/>
									
									<span id="voteConfig" onclick="voteConfig();" style="display:none;cursor: pointer;" class="lui_text_primary" >投票配置</span>
								</td>
								<td class="td_normal_title" width=15%>
						 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdBallotEnable"/>
						 		</td>
						 		<td width="35%" >
									<ui:switch property="fdBallotEnable" showType="edit" checked="${kmImeetingMainForm.fdBallotEnable}"  checkVal="true" unCheckVal="false" />
								</td>
							</tr>
							<%
							 	}
							%>
						 	<tr>
						 		<%--备注--%>
						 		<td class="td_normal_title" width=15%>
						 			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdRemark"/>
						 		</td>
						 		<td width="85%" colspan="3" >
									<xform:textarea property="fdRemark" style="width:95%;" htmlElementProperties="data-actor-expand='true'"></xform:textarea>
								</td>
						 	</tr>
					 	</table>
				 	</div>
			</ui:content>
			<%-- 权限及流程处理 --%>
			<ui:content title="${lfn:message('km-imeeting:kmImeetingMain.createStep.rightAndWorkflow') }" toggle="false">
				<c:import url="/km/imeeting/km_imeeting_main/kmImeetingMain_rightAndWorkflow_add.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingMainForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
					<c:param name="fdKey" value="ImeetingMain" />
					<c:param name="approveModel" value="${param.approveModel}"></c:param>
				</c:import>
			</ui:content>
			<%-- 发送会议通知单 --%>
			<ui:content title="${lfn:message('km-imeeting:kmImeetingMain.createStep.sendNotify') }" toggle="false">
				<%-- 会议通知 --%>
				<div>
					<div class="com_subject lui_imeeting_title">
						${lfn:message('km-imeeting:kmImeetingMain.createStep.sendNotify') }
					</div>
				</div>
				<div>
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
		</ui:step>
		<%-- 发布机制隐藏页面 --%>
		<c:import url="/sys/news/import/sysNewsPublishMain_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingMainForm" />
			<c:param name="fdKey" value="ImeetingMain" />
			<c:param name="isShow" value="false" />
		</c:import>
	<c:if test="${param.approveModel ne 'right'}">
	 </form>
	</c:if>
	<%@include file="/km/imeeting/km_imeeting_main/kmImeetingMain_add_js.jsp"%>
</template:replace>
<c:if test="${param.approveModel eq 'right'}">
	<template:replace name="barRight">
		<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
			<%--流程--%>
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmImeetingMainForm" />
				<c:param name="fdKey" value="ImeetingMain" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
				<c:param name="approveType" value="right" />
				<c:param name="approvePosition" value="right" />
			</c:import>
		</ui:tabpanel>
	</template:replace>
</c:if>