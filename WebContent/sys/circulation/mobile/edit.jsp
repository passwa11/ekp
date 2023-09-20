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
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'传阅',icon:'mui-ul'">
						<div class="muiFormContent">
							<html:hidden property="fdId" />
							<html:hidden property="fdFromParentId" />
							<html:hidden property="fdFromOpinionId" />
						</div>
						<table class="muiSimple headTb" cellpadding="0" cellspacing="0" >
							<tr>
								<td class="muiTitle">
									<bean:message bundle="sys-circulation" key="sysCirculationMain.fdCirculatorId" />
								</td>
								<td>
									<xform:text property="fdCirculatorName" showStatus="readOnly" mobile="true"></xform:text>
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
									<bean:message bundle="sys-circulation" key="sysCirculationMain.fdExpireTime" />
								</td>
								<td>
									<xform:datetime property="fdExpireTime" mobile="true" dateTimeType="datetime" style="width:50%" validators="after" subject="${ lfn:message('sys-circulation:sysCirculationMain.fdExpireTime') }"></xform:datetime>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									<bean:message bundle="sys-circulation" key="sysCirculationMain.fdRegular" />
								</td>
								<td>
									<xform:radio property="fdRegular" mobile="true"  showStatus="edit" >
										<xform:enumsDataSource enumsType="sysCirculationMain_fdRegular"></xform:enumsDataSource>
									</xform:radio>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									<bean:message bundle="sys-circulation" key="table.sysCirculationCirculors" />
								</td>
								<td>
								<input type="hidden" name="receivedCirCulatorIds" value="">
								<div id="main_m" style="display:block">
									<xform:address htmlElementProperties="id='receivedCirCulator_m'" onValueChange="onCirCulatorChange" mobile="true" propertyId="receivedCirCulatorIds_m"  propertyName="receivedCirCulatorNames_m"  mulSelect="true" textarea="true"
										orgType="ORG_TYPE_ALLORG" required="true" subject="${ lfn:message('sys-circulation:table.sysCirculationCirculors') }" 
										style="width:95%" >
									</xform:address>
								</div>
								<div id="main_s"  style="display:none">
									<xform:address htmlElementProperties="id='receivedCirCulator_s'" onValueChange="onCirCulatorChange" mobile="true" propertyId="receivedCirCulatorIds_s"  propertyName="receivedCirCulatorNames_s"  mulSelect="true" textarea="true"
										orgType="ORG_TYPE_PERSON" required="true" subject="${ lfn:message('sys-circulation:table.sysCirculationCirculors') }" 
										style="width:95%" >
									</xform:address>
								</div>
								<br/>
								<xform:checkbox property="fdOpinionRequired"  showStatus="edit" mobile="true" >	
					    			<xform:simpleDataSource value="1"><bean:message bundle="sys-circulation" key="sysCirculationMain.fdOpinionRequired" /></xform:simpleDataSource>
					    		</xform:checkbox>
							</td>
							</tr>
							<tr id="spreadScopeTr">
								<td class="muiTitle">
									<bean:message bundle="sys-circulation" key="sysCirculationMain.fdSpreadScope" />
								</td>
								<td>
									<xform:checkbox property="fdAllowSpread"  showStatus="edit" mobile="true" >	
						    			<xform:simpleDataSource value="1"><bean:message bundle="sys-circulation" key="sysCirculationMain.fdAllowSpread" /></xform:simpleDataSource>
						    		</xform:checkbox>
									<div id="allowSpreadScope" style="display:none;margin-top:6px">
										<xform:radio property="fdSpreadScope" mobile="true" showStatus="edit">
											<xform:enumsDataSource enumsType="sysCirculationMain_fdSpreadScope"></xform:enumsDataSource>
										</xform:radio><br/>
										<div id="OtherSpreadScope" style="display:none;margin-top:6px">
											<xform:address htmlElementProperties="id=spreadScope" mobile="true" required="true"  textarea="true" propertyId="fdOtherSpreadScopeIds" style="width:95%" propertyName="fdOtherSpreadScopeNames"  mulSelect="true"  orgType="ORG_TYPE_ALLORG"  >
											</xform:address>
											<font color="red">（移动端地址本不支持自定义选择范围，该自定义范围只在pc端起作用）</font>
										</div>
									</div>
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
									<xform:textarea property="fdRemark" validators="maxLength(500)" mobile="true" style="width:94%"/>
								</td>
							</tr>
						</table>	
					</div>
				</div>
				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
				  	<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " 
				  		data-dojo-props='colSize:2,href:"javascript:submitForm();",transition:"slide"'>
				  		<bean:message  key="button.submit" />
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
				
				topic.subscribe('/mui/form/valueChanged',function(widget,args){
					if(widget && widget.name=="fdRegular"){
						 var main_s = document.getElementById("main_s");
						 var main_m = document.getElementById("main_m");
						 var dialog_main_s = registry.byId("receivedCirCulator_s");
						 var dialog_main_m = registry.byId("receivedCirCulator_m");
						if(args.value == '1'){
							main_m.style.display = "none";
							main_s.style.display = "block";
							dialog_main_m._set('validate', '');
							dialog_main_s._set('validate', 'required');
							document.getElementById("spreadScopeTr").style.display="none";
						}else{
							main_m.style.display = "block";
							main_s.style.display = "none";
							dialog_main_s._set('validate', '');
							dialog_main_m._set('validate', 'required');
							document.getElementById("spreadScopeTr").style.display="table-row";
						}
					}
					
					if(widget && widget.name=="fdAllowSpread"){
						if(args.value == '1'){
							document.getElementById("allowSpreadScope").style.display="block";
						}else{
							document.getElementById("allowSpreadScope").style.display="none";
						}
					}
					if(widget && widget.name=="fdSpreadScope"){
						var spreadScope = registry.byId("spreadScope");
						if(args.value == '2'){
							spreadScope._set('validate', 'required');
							//document.getElementById("spreadScope").setAttribute("validate", "required");
							document.getElementById("OtherSpreadScope").style.display="block";
						}else{
							spreadScope._set('validate', '');
							//document.getElementById("spreadScope").setAttribute("validate", "");
							document.getElementById("OtherSpreadScope").style.display="none";
						}
					}
				});
				
				window.submitForm = function (){
					var validorObj = registry.byId('scrollView');
					if(!validorObj.validate()){
						return;
					}
					Com_Submit(document.forms[0], 'save');
				}
			});
			</script>
		</html:form>
	</template:replace>
</template:include>
