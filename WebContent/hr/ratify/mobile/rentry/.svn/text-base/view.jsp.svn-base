<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<template:include ref="mobile.view" compatibleMode="true" >
	<template:replace name="loading">
		<c:import url="/hr/ratify/mobile/rentry/view_banner.jsp" charEncoding="UTF-8">
			<c:param name="formBeanName" value="hrRatifyEntryForm"></c:param>
			<c:param name="loading" value="true"></c:param>
		</c:import>
	</template:replace>
	<template:replace name="title">
		<c:out value="${hrRatifyEntryForm.docSubject}"></c:out>
	</template:replace>
	<template:replace name="head">
	<!-- 块状样式，因为使用全局的块状样式会导致其他样式影响。所有只引用单样式 -->
		<!-- 块状样式，因为使用全局的块状样式会导致其他样式影响。所有只引用单样式 -->
	    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/radio-tiny.css?s_cache=${MUI_Cache}"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/checkbox-tiny.css?s_cache=${MUI_Cache}"></link>		 
	   <mui:cache-file name="mui-review-view.css" cacheType="md5"/>
	   <style>
	   	.muiSimple .muiTitle{
	   		width:40%;
	   	}
	   </style>
	   <script type="text/javascript">
	   	require(["dojo/store/Memory","dojo/topic",'dijit/registry'],function(Memory, topic, registry){
	   		window._narStore = new Memory({data:[{'text':'审批内容',
	   			'moveTo':'_contentView','selected':true},{'text':'流程记录',
	   			'moveTo':'_noteView'}]});
	   		var previousY = null;
	   		topic.subscribe("/mui/navitem/_selected",function(evtObj){
	   			setTimeout(function(){
	   				if(evtObj && evtObj.tabIndex === 1){
	   					var scrollview = registry.byId('scrollView');
	   					previousY = scrollview.getPos().y;
	   				}
	   				topic.publish("/mui/list/resize");
	   				if(evtObj && evtObj.tabIndex === 0 && previousY){
	   					var scrollview = registry.byId('scrollView');
	   					scrollview.scrollTo({ y : previousY  });
	   					//通知Fixed组件
	   					topic.publish('/mui/list/_runSlideAnimation',scrollview,{
	   						from : { y : 0 },
	   						to : { y : previousY }
	   					})
	   				}
	   			},150);
	   		});
	   	});
	   </script>
	</template:replace>
	<template:replace name="content">
		<html:form action="/hr/ratify/hr_ratify_entry/hrRatifyEntry.do">		
		<div id="scrollView"
			data-dojo-type="mui/view/DocScrollableView"
			data-dojo-mixins="mui/form/_ValidateMixin" class="muiFlowBack">
			
			<c:import url="/hr/ratify/mobile/rentry/view_banner.jsp" charEncoding="UTF-8">
				<c:param name="formBeanName" value="hrRatifyEntryForm"></c:param>
			</c:import>
			<c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="hrRatifyEntryForm" />
				<c:param name="moduleModelName" value="com.landray.kmss.hr.ratify.model.HrRatifyEntry" />
			</c:import>
			<c:import url="/sys/relation/mobile/edit_hidden.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="hrRatifyEntryForm" />
			</c:import>
			<c:import url="/sys/authorization/mobile/edit_hidden.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="hrRatifyEntryForm" />
			</c:import>
			<div class="muiFlowInfoW">
				<div data-dojo-type="mui/fixed/Fixed" id="fixed">
					<div data-dojo-type="mui/fixed/FixedItem" class="muiFlowFixedItem">
						<div data-dojo-type="mui/nav/NavBarStore" data-dojo-props="store:_narStore">
						</div>
					</div>
				</div>
				<div data-dojo-type="dojox/mobile/View" id="_contentView">
					<c:if test="${hrRatifyEntryForm.docUseXform == 'true' || empty hrRatifyEntryForm.docUseXform}">
						<div data-dojo-type="mui/table/ScrollableHContainer">
							<div data-dojo-type="mui/table/ScrollableHView" class="muiFormContent">
								<c:import url="/sys/xform/mobile/import/sysForm_mobile.jsp"
									charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifyEntryForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="backTo" value="scrollView" />
								</c:import>
							</div>
						</div>
					</c:if>
					<table class="muiSimple" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="2">
								${lfn:message('hr-ratify:py.JiBenXinXi') }
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdNameUsedBefore"/>
							</td>
							<td>
								<xform:text property="fdNameUsedBefore" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdSex"/>
							</td>
							<td>
								<xform:radio property="fdSex" mobile="true" alignment="H">
									<xform:enumsDataSource enumsType="sys_org_person_sex" ></xform:enumsDataSource>
								</xform:radio>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdDateOfBirth"/>
							</td>
							<td>
								<xform:datetime property="fdDateOfBirth" dateTimeType="date" mobile="true"></xform:datetime>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdNativePlace"/>
							</td>
							<td>
								<xform:text property="fdNativePlace" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdMaritalStatus"/>
							</td>
							<td>
								<xform:select property="fdMaritalStatusId" mobile="true">
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdMaritalStatus'" orderBy="fdOrder"></xform:beanDataSource>
								</xform:select>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdNation"/>
							</td>
							<td>
								<xform:select property="fdNationId" mobile="true">
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdNation'" orderBy="fdOrder"></xform:beanDataSource>
								</xform:select>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdPoliticalLandscape"/>
							</td>
							<td>
								<xform:select property="fdPoliticalLandscapeId" mobile="true">
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdPoliticalLandscape'" orderBy="fdOrder"></xform:beanDataSource>
								</xform:select>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdHealth"/>
							</td>
							<td>
								<xform:select property="fdHealthId" mobile="true">
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdHealth'" orderBy="fdOrder"></xform:beanDataSource>
								</xform:select>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdLivingPlace"/>
							</td>
							<td>
								<xform:text property="fdLivingPlace" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdIdCard"/>
							</td>
							<td>
								<xform:text property="fdIdCard" validators="idCard" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdHighestDegree"/>
							</td>
							<td>
								<xform:select property="fdHighestDegreeId" mobile="true">
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdHighestDegree'" orderBy="fdOrder"></xform:beanDataSource>
								</xform:select>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdHighestEducation"/>
							</td>
							<td>
								<xform:select property="fdHighestEducationId" mobile="true">
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdHighestEducation'" orderBy="fdOrder"></xform:beanDataSource>
								</xform:select>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdProfession"/>
							</td>
							<td>
								<xform:text property="fdProfession" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdWorkTime"/>
							</td>
							<td>
								<xform:datetime property="fdWorkTime" dateTimeType="date" mobile="true"></xform:datetime>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdDateOfGroup"/>
							</td>
							<td>
								<xform:datetime property="fdDateOfGroup" dateTimeType="date" mobile="true"></xform:datetime>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdDateOfParty"/>
							</td>
							<td>
								<xform:datetime property="fdDateOfParty" dateTimeType="date" mobile="true"></xform:datetime>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdStature"/>
							</td>
							<td>
								<xform:text property="fdStature" validators="digits min(1)" className="inputsgl" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdWeight"/>
							</td>
							<td>
								<xform:text property="fdWeight" validators="digits min(1)" className="inputsgl" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdHomeplace"/>
							</td>
							<td>
								<xform:text property="fdHomeplace" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdAccountProperties"/>
							</td>
							<td>
								<xform:text property="fdAccountProperties" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdRegisteredResidence"/>
							</td>
							<td>
								<xform:text property="fdRegisteredResidence" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdResidencePoliceStation"/>
							</td>
							<td>
								<xform:text property="fdResidencePoliceStation" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.attRatifyEntry"/>
							</td>
							<td>
								<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
	                            	<c:param name="formName" value="hrRatifyEntryForm" />
	                            	<c:param name="fdKey" value="attRatifyEntry" />
	                        	</c:import>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								${lfn:message('hr-ratify:py.LianXiXinXi') }
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdEmail"/>
							</td>
							<td>
								<xform:text property="fdEmail" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdMobileNo"/>
							</td>
							<td>
								<xform:text property="fdMobileNo" validators="phoneNumber uniqueMobileNo" required="true" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdEmergencyContact"/>
							</td>
							<td>
								<xform:text property="fdEmergencyContact" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdEmergencyContactPhone"/>
							</td>
							<td>
								<xform:text property="fdEmergencyContactPhone" validators="phoneNumber" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdOtherContact"/>
							</td>
							<td>
								<xform:text property="fdOtherContact" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								${lfn:message('hr-ratify:hrRatifyEntry.fdHistory') }
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<c:import url="/hr/ratify/mobile/rentry/history_view.jsp" charEncoding="UTF-8"></c:import>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								${lfn:message('hr-ratify:hrRatifyEntry.fdEducations') }
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<c:import url="/hr/ratify/mobile/rentry/education_view.jsp" charEncoding="UTF-8"></c:import>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								${lfn:message('hr-ratify:hrRatifyEntry.fdCertificate') }
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<c:import url="/hr/ratify/mobile/rentry/certificate_view.jsp" charEncoding="UTF-8"></c:import>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								${lfn:message('hr-ratify:hrRatifyEntry.fdRewardsPunishments') }
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<c:import url="/hr/ratify/mobile/rentry/rewPui_view.jsp" charEncoding="UTF-8"></c:import>
							</td>
						</tr>
					</table>
				</div>
				<div data-dojo-type="hr/ratify/mobile/resource/js/DelayView" id="_noteView">
					<div class="muiFormContent">
						<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
							<c:param name="fdModelId" value="${hrRatifyEntryForm.fdId }"/>
							<c:param name="fdModelName" value="com.landray.kmss.hr.ratify.model.HrRatifyEntry"/>
							<c:param name="formBeanName" value="hrRatifyEntryForm"/>
						</c:import>
						<xform:isExistRelationProcesses relationType="parent">
							<xform:showParentProcesse mobile="true" />
						</xform:isExistRelationProcesses>
							
						<xform:isExistRelationProcesses relationType="subs">
							<xform:showSubProcesses mobile="true"/>
						</xform:isExistRelationProcesses>
					</div>
				</div>
			</div>
			
			<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" 
							editUrl="/hr/ratify/hr_ratify_entry/hrRatifyEntry.do?method=edit&fdId=${param.fdId }"
							formName="hrRatifyEntryForm"
							viewName="lbpmView"
							allowReview="true">
				<template:replace name="flowArea">
					<c:set var="addOrgPerson" value="false" scope="request"></c:set>
					<c:if test="${hrRatifyEntryForm.sysWfBusinessForm.fdNodeAdditionalInfo.addOrgPerson =='true'}">
						<c:set var="addOrgPerson" value="true" scope="request"></c:set>
					</c:if>
					<%--账号写入 --%>
					<c:if test="${addOrgPerson eq 'true' and empty hrRatifyEntryForm.fdHasWrite}">
						<div data-dojo-type="mui/tabbar/TabBarButton" onclick="addOrgPerson();">
							<bean:message bundle="hr-ratify" key="hrRatifyEntry.addOrgPerson"/>
						</div>
					</c:if>
					<%--传阅 --%>
					<c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="hrRatifyEntryForm"></c:param>
						<c:param name="showOption" value="label"></c:param>
				 	</c:import>
					<c:import url="/sys/relation/mobile/import/view.jsp" charEncoding="UTF-8">
				  		<c:param name="formName" value="hrRatifyEntryForm"/>
				  		<c:param name="showOption" value="label"></c:param>
				  	</c:import>
				</template:replace>
				<template:replace name="publishArea">
					<%--传阅 --%>
					<c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="hrRatifyEntryForm"></c:param>
						<c:param name="showOption" value="label"></c:param>
				 	</c:import>
					<c:import url="/sys/relation/mobile/import/view.jsp" charEncoding="UTF-8">
				  		<c:param name="formName" value="hrRatifyEntryForm"/>
				  		<c:param name="showOption" value="label"></c:param>
				  	</c:import>
				</template:replace>
			</template:include>
		</div>
		<!-- 钉钉图标 -->
		<kmss:ifModuleExist path="/third/ding">
			<c:import url="/third/ding/import/ding_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="hrRatifyEntryForm" />
			</c:import>
		</kmss:ifModuleExist>
		<kmss:ifModuleExist path="/third/lding">
			<c:import url="/third/lding/import/ding_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="hrRatifyEntryForm" />
			</c:import>
		</kmss:ifModuleExist>
		
		<!-- 钉钉图标 end -->
		<c:if test="${hrRatifyEntryForm.docStatus < '30' }">
			<c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="hrRatifyEntryForm" />
				<c:param name="fdKey" value="${fdTempKey }" />
				<c:param name="lbpmViewName" value="lbpmView" />
				<c:param name="onClickSubmitButton" value="Com_Submit(document.hrRatifyEntryForm, 'update');" />
			</c:import>
			<script type="text/javascript">
				require(["mui/form/ajax-form!hrRatifyEntryForm"]);
			</script>
		</c:if>
		<c:if test="${hrRatifyEntryForm.docStatus eq '30'}">
			<script type="text/javascript">
				require(["dojo/ready"], function(ready) {
					ready(function() {
						document.getElementById("processStatusDiv").className="muiProcessStatus stamp";
					});
				});
			</script>
		</c:if>
		<c:if test="${hrRatifyEntryForm.docStatus eq '00'}">
			<script type="text/javascript">
				require(["dojo/ready"], function(ready) {
					ready(function() {
						document.getElementById("discardStatusDiv").className="muiProcessStatus muiDiscardStatus stamp";
					});
				});
			</script>
		</c:if>
	</html:form>
	<!-- 分享机制  -->
	<kmss:ifModuleExist path="/third/ywork/">
		 <c:import url="/third/ywork/ywork_share/yworkDoc_mobile_share.jsp"
			charEncoding="UTF-8">
			<c:param name="modelId" value="${hrRatifyEntryForm.fdId}" />
			<c:param name="modelName" value="com.landray.kmss.hr.ratify.model.HrRatifyEntry" />
			<c:param name="templateId" value="${ hrRatifyEntryForm.docTemplateId}" />
			<c:param name="allPath" value="${ hrRatifyEntryForm.docTemplateName}" />
		</c:import>
	</kmss:ifModuleExist>
		
	</template:replace>
</template:include>
<script type="text/javascript">
	require(["dojo/parser", "dojo/dom", "dojo/dom-construct", "dijit/registry","dojo/topic","mui/rtf/RtfResize","dojo/_base/lang","mui/dialog/Dialog"], function(parser, dom, domConstruct, registry, topic,Rtf,lang,Dialog){
		var dialog;
		window.addOrgPerson = function(){
			var url = Com_GetCurDnsHost()+Com_Parameter.ContextPath+'hr/ratify/hr_ratify_entry_dr/hrRatifyEntryDR.do?method=addOrgPerson&fdEntryId=${param.fdId}';
			window.open(url,"_self");
		};
	});
</script>