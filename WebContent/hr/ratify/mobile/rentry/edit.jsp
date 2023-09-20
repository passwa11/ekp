<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<%@page import="com.landray.kmss.sys.organization.model.SysOrganizationConfig" %>
<%
	SysOrganizationConfig sysOrganizationConfig = new SysOrganizationConfig();
	String isLoginSpecialChar = sysOrganizationConfig.getIsLoginSpecialChar();
%>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="title">
		<c:choose>
	        <c:when test="${hrRatifyEntryForm.method_GET == 'add' }">
	            <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('hr-ratify:table.hrRatifyEntry') }" />
	        </c:when>
	        <c:otherwise>
	            <c:out value="${hrRatifyEntryForm.docSubject} - " />
	            <c:out value="${ lfn:message('hr-ratify:table.hrRatifyEntry') }" />
	        </c:otherwise>
	    </c:choose>
	</template:replace>
	<template:replace name="head">
	<!-- 块状样式，因为使用全局的块状样式会导致其他样式影响。所有只引用单样式 -->
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/form-tiny.css?s_cache=${MUI_Cache}"></link>
				<!-- 非块状多选样式变化 查看页面不需要 -->
		<style>
			.oldMui.muiFormEleWrap .muiCheckBoxWrap.muiCheckBoxNormalWrap {
			    margin-top: 0;
			}
			.muiCheckItem.hasText .muiCheckBlock {
				    top: 0.9rem;
			}
		</style>
		<!--块状end--> 
		<style>
			.muiSimple .muiTitle{
			    width: 38%;
			    white-space: nowrap;
			    overflow: hidden;
			    text-overflow: ellipsis;
			}
	       .muiSelInputRight{
				    right: 1.5rem;
			}
		</style>
		<script type="text/javascript">
		   	require(["dojo/store/Memory","dojo/topic","dijit/registry"],function(Memory,topic,registry){
		   		var navData = [{'text':'01  /  审批内容',
		   			'moveTo':'scrollView','selected':true},{'text':'02  /  流程操作',
			   		'moveTo':'lbpmView'}];
		   		window._narStore = new Memory({data:navData});
		   		var changeNav = function(view){
		   			var wgt = registry.byId("_flowNav");
		   			for(var i=0;i<wgt.getChildren().length;i++){
		   				var tmpChild = wgt.getChildren()[i];
		   				if(view.id == tmpChild.moveTo){
		   					tmpChild.beingSelected(tmpChild.domNode);
		   					return;
		   				}
		   			}
		   		}
		   		topic.subscribe("mui/form/validateFail",function(view){
		   			changeNav(view);
		   		});
				topic.subscribe("mui/view/currentView",function(view){
					changeNav(view);
		   		});
		   	});
	   </script>
	  
	</template:replace>
	<template:replace name="content">
		<html:form action="/hr/ratify/hr_ratify_entry/hrRatifyEntry.do?method=save">
			<div>
				<div data-dojo-type="mui/fixed/Fixed" class="muiFlowEditFixed">
					<div data-dojo-type="mui/fixed/FixedItem" class="muiFlowEditFixedItem">
						<div data-dojo-type="mui/nav/NavBarStore" id="_flowNav" data-dojo-props="store:_narStore">
						</div>
					</div>
				</div>
				<div data-dojo-type="mui/view/DocScrollableView" 
					data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView">
					<div class="muiFlowInfoW muiFormContent">
						<html:hidden property="fdId" />
						<html:hidden property="docStatus" />
						<html:hidden property="method_GET"/>
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td class="muiTitle">
									${lfn:message('hr-ratify:hrRatifyMain.docSubject') }
								</td>
								<td>
									<c:if test="${hrRatifyEntryForm.titleRegulation==null || hrRatifyEntryForm.titleRegulation=='' }">
										<xform:text property="docSubject" mobile="true" subject="${lfn:message('hr-ratify:hrRatifyMain.docSubject') }" htmlElementProperties="id='docSubject'"/>
									</c:if>
									<c:if test="${hrRatifyEntryForm.titleRegulation!=null && hrRatifyEntryForm.titleRegulation!='' }">
										<xform:text property="docSubject" mobile="true" showStatus="readOnly" value="${lfn:message('hr-ratify:hrRatifyMain.docSubject.info') }" htmlElementProperties="id='docSubject'"/>
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									${lfn:message('hr-ratify:hrRatifyTemplate.fdName') }
								</td>
								<td>
									<html:hidden property="docTemplateId" /> 
									<xform:text property="docTemplateName" mobile="true" showStatus="view" subject="${lfn:message('hr-ratify:hrRatifyTemplate.fdName') }"/>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									${lfn:message('hr-ratify:hrRatifyMain.docCreator') }
								</td>
								<td>
									<xform:text property="docCreatorName" mobile="true" showStatus="view" subject="${lfn:message('hr-ratify:hrRatifyMain.docCreator') }"/>
								</td>
							</tr>
							<tr>
								<td style="width:50%;" class="muiTitle" >
									引入待确认员工档案
								</td>
								<td>
									<div data-dojo-type="mui/form/Switch"
										 data-dojo-mixins="hr/ratify/mobile/resource/js/SwitchMixin"
										 data-dojo-props="orient:'vertical',leftLabel:'',rightLabel:'',value:'${hrRatifyEntryForm.fdIsRecruit == null ? 'on' : hrRatifyEntryForm.fdIsRecruit eq 'true' ? 'on' : 'off' }',property:'fdIsRecruit'">
									</div>
								</td>
							</tr>
						</table>
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
					</div>
					<div class="muiFlowInfoW muiFormContent">
						<c:if test="${hrRatifyEntryForm.docUseXform == 'false'}">
							<table class="muiSimple" cellpadding="0" cellspacing="0">
								<tr>
									<td colspan="2">
										<kmss:editor property="docXform" width="95%" />
									</td>
								</tr>
							</table>
						</c:if>
						<c:if test="${hrRatifyEntryForm.docUseXform == 'true' || empty hrRatifyEntryForm.docUseXform}">
							<c:import url="/sys/xform/mobile/import/sysForm_mobile.jsp"
								charEncoding="UTF-8">
								<c:param name="formName" value="hrRatifyEntryForm" />
								<c:param name="fdKey" value="${fdTempKey }" />
								<c:param name="backTo" value="scrollView" />
							</c:import>
						</c:if>
						<div data-dojo-type="mui/panel/AccordionPanel">
							<div data-dojo-type="mui/panel/Content"
								data-dojo-props="title:'<bean:message bundle="hr-ratify" key="py.JiBenXinXi" />',icon:'mui-ul'">
								
								<table class="muiSimple" cellpadding="0" cellspacing="0">
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
												<xform:enumsDataSource enumsType="sys_org_person_sex"></xform:enumsDataSource>
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
											<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
				                            	<c:param name="formName" value="hrRatifyEntryForm" />
				                            	<c:param name="fdKey" value="attRatifyEntry" />
				                            	<c:param name="fdRequired" value="false" />
				                        	</c:import>
										</td>
									</tr>
								</table>
							</div>
							<div data-dojo-type="mui/panel/Content"
								data-dojo-props="title:'<bean:message bundle="hr-ratify" key="py.LianXiXinXi" />',icon:'mui-ul'">
								<table class="muiSimple" cellpadding="0" cellspacing="0">
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
								</table>
							</div>
							<div data-dojo-type="mui/panel/Content"
								data-dojo-props="title:'<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdHistory" />',icon:'mui-ul'">
								<c:import url="/hr/ratify/mobile/rentry/history.jsp" charEncoding="UTF-8"></c:import>
							</div>
							<div data-dojo-type="mui/panel/Content"
								data-dojo-props="title:'<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdEducations" />',icon:'mui-ul'">
								<c:import url="/hr/ratify/mobile/rentry/education.jsp" charEncoding="UTF-8"></c:import>
							</div>
							<div data-dojo-type="mui/panel/Content"
								data-dojo-props="title:'<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdCertificate" />',icon:'mui-ul'">
								<c:import url="/hr/ratify/mobile/rentry/certificate.jsp" charEncoding="UTF-8"></c:import>
							</div>
							<div data-dojo-type="mui/panel/Content"
								data-dojo-props="title:'<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdRewardsPunishments" />',icon:'mui-ul'">
								<c:import url="/hr/ratify/mobile/rentry/rewPui.jsp" charEncoding="UTF-8"></c:import>
							</div>
						</div>
					</div>
					<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
					  	<li data-dojo-type="hr/ratify/mobile/resource/js/SaveDraftButton" 
					  		data-dojo-props='validateDomId:"scrollView",validateElementId:"docSubject"'>
							<bean:message key="button.savedraft" /></li>
					  	<li data-dojo-type="mui/tabbar/TabBarButton" class="mainTabBarButton"
					  		data-dojo-props='colSize:2,moveTo:"lbpmView",transition:"slide"'>
					  		下一步</li>
					</ul>
				</div>
				<c:import url="/sys/lbpmservice/mobile/import/edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifyEntryForm" />
					<c:param name="fdKey" value="${fdTempKey }" />
					<c:param name="viewName" value="lbpmView" />
					<c:param name="backTo" value="scrollView" />
					<c:param name="onClickSubmitButton" value="commitMethod();" />
				</c:import>
				<script type="text/javascript">
					require(["mui/form/ajax-form!hrRatifyEntryForm"]);
					require(['dojo/query','mui/util','dojo/topic','dijit/registry','dojo/ready','dojo/dom-style','dojo/dom-geometry','mui/dialog/Tip','mui/calendar/CalendarUtil','dojo/date/locale','mui/device/adapter','dojo/request','dojo/dom-attr','mui/form/ajax-form','dojo/parser'],function(query,util,topic,registry,ready,domStyle,domGeometry,Tip,cutil,locale,adapter,request,domAttr,ajaxForm,parser){
						//切换全天时隐藏type='time'的时间控件
						topic.subscribe('hr/ratify/statChanged',function(widget,value){
							if(value){
								showSelect();
							}else{
								showInput();
							}
						});
						function showSelect(){
							//招聘模块
							var isRecruitSpan = query("#isRecruitSpan")[0];	
							var notRecruitSpan = query("#notRecruitSpan")[0];
							domStyle.set(isRecruitSpan,'display','block');
							domStyle.set(notRecruitSpan,'display','none');
							var isRecruitInput = registry.byId('fdEntryName');
							var isRecruitSelect = registry.byId('fdRecruitEntryId');
							domStyle.set(isRecruitInput.domNode,'display','none');
							domStyle.set(isRecruitSelect.domNode,'display','block');
							//添加效验
				    		isRecruitSelect.validate = 'required';
						}
						function showInput(){
							//招聘模块
							var isRecruitSpan = query("#isRecruitSpan")[0];	
							var notRecruitSpan = query("#notRecruitSpan")[0];
							domStyle.set(isRecruitSpan,'display','none');
							domStyle.set(notRecruitSpan,'display','block');
							var isRecruitInput = registry.byId('fdEntryName');
							var isRecruitSelect = registry.byId('fdRecruitEntryId');
							domStyle.set(isRecruitInput.domNode,'display','block');
							domStyle.set(isRecruitSelect.domNode,'display','none');
							
							//删除效验
				    		isRecruitSelect.validate = 'skip';
						}
						//校验对象
						var validorObj = null;
						ready(function(){
							validorObj = registry.byId('scrollView');
							validorObj._validation.addValidator('compareFdStartDateHistory','结束时间不能早于开始时间',function(v, e, o){
				    			var result = true; 
				    			var fdStartDateHistory=query('[name="fdStartDateHistory"]')[0];
				    			var fdEndDateHistory=query('[name="fdEndDateHistory"]')[0];
				    			if( fdStartDateHistory && fdEndDateHistory ){
				    				var start=Com_GetDate(fdStartDateHistory.value);
				    				var end=Com_GetDate(fdEndDateHistory.value);
				    				if(end.getTime() < start.getTime()){
				    					result = false;
				    				}
				    			}
				    			return result;
				    		});
							validorObj._validation.addValidator('compareFdEntranceDate','毕业日期不能早于入学日期',function(v, e, o){
				    			var result = true; 
				    			var fdEntranceDate=query('[name="fdEntranceDate"]')[0];
				    			var fdGraduationDate=query('[name="fdGraduationDate"]')[0];
				    			if( fdEntranceDate && fdGraduationDate ){
				    				var start=Com_GetDate(fdEntranceDate.value);
				    				var end=Com_GetDate(fdGraduationDate.value);
				    				if(end.getTime() < start.getTime()){
				    					result = false;
				    				}
				    			}
				    			return result;
				    		});
							validorObj._validation.addValidator('compareFdStartDateTrain','结束时间不能早于开始时间',function(v, e, o){
				    			var result = true; 
				    			var fdStartDateTrain=query('[name="fdStartDateTrain"]')[0];
				    			var fdEndDateTrain=query('[name="fdEndDateTrain"]')[0];
				    			if( fdStartDateTrain && fdEndDateTrain ){
				    				var start=Com_GetDate(fdStartDateTrain.value);
				    				var end=Com_GetDate(fdEndDateTrain.value);
				    				if(end.getTime() < start.getTime()){
				    					result = false;
				    				}
				    			}
				    			return result;
				    		});
							validorObj._validation.addValidator('compareFdIssueDate','失效日期不能早于颁发日期',function(v, e, o){
				    			var result = true; 
				    			var fdIssueDate=query('[name="fdIssueDate"]')[0];
				    			var fdInvalidDate=query('[name="fdInvalidDate"]')[0];
				    			if( fdIssueDate && fdInvalidDate ){
				    				var start=Com_GetDate(fdIssueDate.value);
				    				var end=Com_GetDate(fdInvalidDate.value);
				    				if(end.getTime() < start.getTime()){
				    					result = false;
				    				}
				    			}
				    			return result;
				    		});
							validorObj._validation.addValidator('uniqueMobileNo','手机号已被注册',function(value, e, o){
				    			if(startsWith(value, "+86")) {
				    				// 如果是+86开头的手机号，保存到数据库时强制去掉+86前缀
				    				value = value.slice(3, value.length)
				    			}
				    			if(startsWith(value, "+")) {
				    				value = value.replace("+", "x")
				    			}
				    			var fdId = query('[name="fdId"]')[0].value;
				    			var url = Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrRatifyEntryService&mobileNo=" + value + "&fdId="+fdId+"&checkType=unique";
								var fdRecruitEntryId = query('[name="fdRecruitEntryId"]')[0];
				    			if (fdRecruitEntryId){
									url = url + "&fdRecruitEntryId="+fdRecruitEntryId.value;
								}
								url = encodeURI(url + "&date=" + new Date());
								var result = _CheckUnique(url);
				    			if (!result) 
				    				return false;
				    			return true;
				    		});
							var LoginNameValidators = {};
							var isLoginSpecialChar = <%=isLoginSpecialChar%>;
					    	var errorMsg ="<bean:message key='sysOrgPerson.error.loginName.abnormal' bundle='sys-organization'/>";
					    	<%  if("true".equals(isLoginSpecialChar)){%>
					    		errorMsg = "只能包含部分特殊字符 @ # $ % ^ & ( ) - + = { } : ; \ ' ? / < > , . \" [ ] | _ 空格";
					    	<% } %>
					    	validorObj._validation.addValidator("uniqueLoginName","<bean:message key='sysOrgPerson.error.loginName.mustUnique' bundle='sys-organization' />",function (value) {
			     				var fdId = query('[name="fdId"]')[0].value;
			     				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrRatifyEntryService&loginName=" + value+"&fdId="+fdId+"&checkType=unique&date="+new Date());
			     				var result = _CheckUnique(url);
			     				if (!result) 
			     					return false;
			     				return true;
			     			});
					    	validorObj._validation.addValidator("invalidLoginName","<bean:message key='sysOrgPerson.error.newLoginNameSameOldName' bundle='sys-organization' />",function(value) {
			     				if (LoginNameValidators["lgName"] && (LoginNameValidators["lgName"]==value)){
			     					return true;
			     				}
								LoginNameValidators["lgName"]=null;
								var fdId = query('[name="fdId"]')[0].value;
								var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrRatifyEntryService&loginName=" + value+"&fdId="+fdId+"&checkType=invalid&date="+new Date());
								var result = _CheckUnique(url);
								if (!result){ 
									if(window.confirm("<bean:message key='sysOrgPerson.newLoginName.ConfirmMsg' bundle='sys-organization' />")){
										LoginNameValidators["lgName"]=value;
										return true;
									}else{
										return false;
									}
								}
								return true;	
			     			});
					    	validorObj._validation.addValidator("normalLoginName",errorMsg,function(value){
			     				var pattern;
		     					
			   					<% if("true".equals(isLoginSpecialChar)){%>
			   						pattern=new RegExp("^[A-Za-z0-9_@#$%^&()={}:;\'?/<>,.\"\\[\\]|\\-\\+ ]+$");
			   					<% }else{ %>
			   						pattern=new RegExp("^[A-Za-z0-9_]+$");
			   					<% }%>
			     					
			     				if(pattern.test(value)){
			     					return true;
			     				}else{
			     					return false;
			     				}
			     			});
					    	validorObj._validation.addValidator("uniqueStaffNo","<bean:message key='hrStaffPersonInfo.staffNo.unique.err' bundle='hr-staff' />",function (value) {
			     				var fdId = query('[name="fdId"]')[0].value;
			     				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrRatifyEntryService&staffNo="
			     					+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date());
			     				var result = _CheckUnique(url);
			     				if (!result) 
			     					return false;
			     				return true;
			     			});
					    	validorObj._validation.addValidator("uniqueNo","<bean:message key='organization.error.fdNo.mustUnique' bundle='sys-organization' />",function(v, e, o) {
			      				if (v.length < 1)
			      					return true;
			      				var fdId = query('[name="fdId"]')[0].value,
			      					fdNo = query('[name="fdNo"]')[0].value;;
			      				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=sysOrgElementService&fdOrgType=8"
			      						+ "&fdId=" + fdId + "&fdNo=" + fdNo + "&_=" + new Date());
			      				return _CheckUnique(url);
			      			});
							var fdIsRecruit = document.getElementsByName('fdIsRecruit')[0];
							if(fdIsRecruit.value == 'true'){
								showSelect();
							}else{
								showInput();
							}
							if(DocList_TableInfo['TABLE_DocList_fdHistory_Form']==null){
			        			DocListFunc_Init();
			        		}
						});
						// 验证手机号是否已被注册
			    		window.startsWith = function(value, prefix) {
							return value.slice(0, prefix.length) === prefix;
						}
						//校验登录名是否与系统中失效的登录名一致
				     	window._CheckUnique = function(url) {
				     		var xmlHttpRequest;
				     		if (window.XMLHttpRequest) { // Non-IE browsers
				     			xmlHttpRequest = new XMLHttpRequest();
				     		} else if (window.ActiveXObject) { // IE
				     			try {
				     				xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
				     			} catch (othermicrosoft) {
				     				try {
				     					xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				     				} catch (failed) {
				     					xmlHttpRequest = false;
				     				}
				     			}
				     		}
				     		if (xmlHttpRequest) {
				     			xmlHttpRequest.open("GET", url, false);
				     			xmlHttpRequest.send();
				     			var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
				     			if (result != "") {
				     				return false;
				     			}
				     		}
				     		return true;
				     	}
						window.validate = function() {
							validatorObj = registry.byId("scrollView");
							var rs = validatorObj.validate();
							return rs;
						};
						
						window.commitMethod = function(){
							if(!validorObj.validate()){
								return;
							}
							var status = document.getElementsByName("docStatus")[0];
							var method = Com_GetUrlParameter(location.href,'method');
							var result=true;
							if(method=='add'){
								status.value="20"
									result =Com_Submit(document.forms[0],'save');
							}else{
								status.value="20"
									result =Com_Submit(document.forms[0],'update');
							}
							if(result == true){
							setInterval(() => {
								window.location='${LUI_ContextPath}/hr/ratify/mobile/list.jsp?modulekey=entry';
							}, 2000);
							}
						};
						
						window.reloadForm = function(value){
			        		document.hrRatifyEntryForm.action = Com_SetUrlParameter(location.href, "fdEntryId", value);
			    			document.hrRatifyEntryForm.submit();
			        	};
			        	//添加行
			        	window.add = function(id) {
			        		event = event || window.event;
			        		if (event.stopPropagation)
			        			event.stopPropagation();
			        		else
			        			event.cancelBubble = true;
			        		var newRow = DocList_AddRow(id);
			        		parser.parse(newRow);
			        		resize(id);
			        		window['fixNo'](id);
			        	};
			        	//删除行
			        	window.del = function(domNode,id) {
			        		var TR = query(domNode).parents('.muiAgendaNormalTr')[0];
			        		DocList_DeleteRow(TR)
			        		resize(id);
			        		window['fixNo'](id);
			        	};
			        	
			        	//调整行号
			        	window.fixNo = function(id){
			        		var tb = query('#'+id)[0];
			        		var nodes = query('.muiDetailTableNoTxt',tb);
			        		var visibleIndex = 0;
			        		nodes.forEach(function(dom){
			        			var TR = query(dom).parents('.muiAgendaNormalTr')[0];
			        			var visible = (domStyle.get(TR,'display') != 'none');
			        			if(visible){
			        				dom.innerHTML = (visibleIndex + 1);
			        				visibleIndex++;
			        			}
			        		});
			        	};
			        	
			        	//重新调整位置
			        	function resize(id){
			        		var table = query('#'+id)[0];
			        		var	tableOffsetTop=_getDomOffsetTop(table),
			        			sliceHeight= tableOffsetTop+table.offsetHeight;
			        	}
			        	
			        	function _getDomOffsetTop(node){
			        		 var offsetParent = node;
			        		 var nTp = 0;
			        		 while (offsetParent!=null && offsetParent!=document.body) {
			        			 nTp += offsetParent.offsetTop; 
			        			 offsetParent = offsetParent.offsetParent; 
			        		 }
			        		 return nTp;
			        	};
					});
				</script>
			</div>
		</html:form>
	</template:replace>
</template:include>
