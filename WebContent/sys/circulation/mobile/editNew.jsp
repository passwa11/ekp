<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-circulation:sysCirculationMain.button.circulation') }"></c:out>
	</template:replace>
	<template:replace name="head">
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=save">
			<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView">
				<div data-dojo-type="mui/panel/AccordionPanel" class="editPanel">
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'继续传阅',icon:'mui-ul'">
						<div class="muiFormContent">
							<html:hidden property="fdId" />
							<html:hidden property="fdFromParentId" />
							<html:hidden property="fdFromOpinionId" />
							<html:hidden property="method_GET" />
							<html:hidden property="fdKey" />
							<html:hidden property="fdModelId" />
							<html:hidden property="fdModelName" />
						</div>
						<table class="muiSimple headTb" cellpadding="0" cellspacing="0" >
							<tr>
								<td class="muiTitle">
									<bean:message bundle="sys-circulation" key="sysCirculationMain.fdCirculatorId" />
								</td>
								<td>
									<xform:text property="fdCirculatorName" mobile="true" showStatus="readOnly"></xform:text>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									<bean:message bundle="sys-circulation" key="sysCirculationMain.fdCirculationTime" />
								</td>
								<td>
									<xform:text property="fdCirculationTime" showStatus="readOnly" mobile="true"></xform:text>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									<bean:message bundle="sys-circulation" key="table.sysCirculationCirculors" />
								</td>
								<td>
									<c:choose>
										<c:when test="${latestForm.fdSpreadScope eq '1'}">
											<div>
												<xform:address mobile="true"  showStatus="edit" otherProperties="scope:'22'" propertyId="receivedCirCulatorIds"  propertyName="receivedCirCulatorNames"  mulSelect="true" textarea="true"
													orgType="ORG_TYPE_PERSON" required="true" subject="${ lfn:message('sys-circulation:table.sysCirculationCirculors') }" 
													style="width:95%" >
												</xform:address>
											</div>
										</c:when>
										<c:otherwise>
											<div>
												<xform:address   mobile="true" showStatus="edit" otherProperties="scope:'33'"  propertyId="receivedCirCulatorIds"  propertyName="receivedCirCulatorNames"  mulSelect="true" textarea="true"
													orgType="ORG_TYPE_PERSON" required="true" subject="${ lfn:message('sys-circulation:table.sysCirculationCirculors') }" 
													style="width:95%" >
												</xform:address>
											</div>
										</c:otherwise>
									</c:choose>
									<br/>
									<xform:checkbox property="fdOpinionRequired"  showStatus="edit" mobile="true" >	
						    			<xform:simpleDataSource value="1">传阅意见必填</xform:simpleDataSource>
						    		</xform:checkbox>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									<bean:message bundle="sys-circulation" key="sysCirculationMain.fdNotifyType" />
								</td>
								<td>
									<kmss:editNotifyType property="fdNotifyType" mobile="true"/>
								</td>
							</tr>
							<tr>
							   <td class="muiTitle">
									<bean:message bundle="sys-circulation" key="sysCirculationMain.fdRemark" />
								</td>
								<td>
									<xform:textarea property="fdRemark" showStatus="edit"  validators="maxLength(500)" mobile="true" style="width:94%"/>
								</td>
							</tr>
						</table>	
					</div>
				</div>
				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
				  	<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " 
				  		data-dojo-props='colSize:2,href:"javascript:submitForm();",transition:"slide"'>
				  		提交
				  	</li>
				</ul>
			</div>
			<script type="text/javascript">
			
			require(["mui/form/ajax-form!sysCirculationMainForm"]);
			require(['dojo/ready','dijit/registry','dojo/topic','dojo/query','dojo/dom-style','dojo/dom-class',"dojo/_base/lang","mui/dialog/Tip","dojo/request","mui/device/adapter","mui/util","mui/device/device" ,'dojo/date/locale'],
					function(ready,registry,topic,query,domStyle,domClass,lang,Tip,req,adapter,util,device,locale){
				
				window.onCirCulatorChange = function(value, element){
					document.getElementsByName("receivedCirCulatorIds")[0].value = value;
				};
				
				window.submitForm = function (){
					var validorObj = registry.byId('scrollView');
					if(!validorObj.validate()){
						return;
					}
					Com_Submit(document.forms[0], 'saveCirculate');
				}
			});
			</script>
		</html:form>
	</template:replace>
</template:include>
