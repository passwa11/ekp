<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<template:include ref="mobile.edit" compatibleMode="true">
    <template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/lbpmext/authorize/mobile/resource/css/lbpmext.css" />
	</template:replace>
	<template:replace name="title">
		<c:if test="${lbpmAuthorizeForm.method_GET == 'add'}">
			<c:out value="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.mobile.add.title')}"></c:out>
		</c:if>
		<c:if test="${lbpmAuthorizeForm.method_GET == 'edit'}">
			<c:out value="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.mobile.edit.title')}"></c:out>
		</c:if>
	</template:replace>
	<template:replace name="content">
	<xform:config orient="vertical">
		<html:form action="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do">
			<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView">
				<%
					String currentUserId = UserUtil.getUser().getFdId();
					pageContext.setAttribute("currentUserId", currentUserId);
				%>
			<div data-dojo-type="mui/panel/AccordionPanel">
				<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.mobile.info" />',icon:'mui-ul'">
				<div class="muiFormContent">
					<html:hidden property="fdId"/>
					<html:hidden property="fdAuthorizeCategory" value="0"/>
					<html:hidden property="currentUserRoleIds"/>	
					<html:hidden property="currentUserRoleNames"/>
					<html:hidden property="fdLbpmAuthorizeItemIds"/>	
					<html:hidden property="fdLbpmAuthorizeItemNames"/>
					<table class="muiSimple" cellpadding="0" cellspacing="0">
						<tr>
							<%-- <td class="muiTitle">
							  <bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizeType"/>
							</td> --%>
							<td>
								<c:if test="${lbpmAuthorizeForm.method_GET=='edit'}">
									<html:hidden property="fdAuthorizeType"/>
									<sunbor:enumsShow value="${lbpmAuthorizeForm.fdAuthorizeType}" enumsType="lbpmAuthorize_authorizeType" />
								</c:if>
								<c:if test="${lbpmAuthorizeForm.method_GET != 'edit'}">
									<xform:radio property="fdAuthorizeType" mobile="true">
										<xform:enumsDataSource enumsType="lbpmAuthorize_authorizeType"/>
									</xform:radio>
									
									<script type="text/javascript">
										
												require(['dojo/ready','dojo/query','dojo/dom-style'],
													function(ready,query,domStyle){
												
													
														ready(function(){
															
															
															query('div.muiFormItem.muiRadioGroupWrap.muiRadioGroupNormalWrap .muiRadioItem').forEach(function(item, index){
																
																var SettingInfo = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0];
																//if(SettingInfo.businessauth!="true"||SettingInfo.personBusinessauth!="true"){
																	if(query('input', item).val()=="4"){
																		domStyle.set(item,"display","none");
																	}
																//}
																if(SettingInfo.advanceApprovalAuth!="true"){
																	if(query('input', item).val()=="3"){
																		domStyle.set(item,"display","none");
																	}
																}
															});
															});
													});
										</script>
										
								</c:if>
								<div class="description"><i class="mui mui-Bulb"></i><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorize.description0"/></div>
							</td>
						</tr>
						<tr>
						<%-- 	<td class="muiTitle">
							   <bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizer"/>
							</td> --%>
							<td>
								<c:if test="${lbpmAuthorizeForm.canChangeAuthorizerAuth != 'true'}">
									<html:hidden property="fdAuthorizerId"/>
									<xform:text property="fdAuthorizerName" showStatus="view" mobile="true" subject="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizer') }"/>
								</c:if>
								<c:if test="${lbpmAuthorizeForm.canChangeAuthorizerAuth == 'true'}">
									<xform:address propertyId="fdAuthorizerId" propertyName="fdAuthorizerName" subject="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizer') }" orgType="ORG_TYPE_PERSON" htmlElementProperties="id='authorizer'"
										showStatus="edit"  mobile="true" style="width:100%">
									</xform:address>
								</c:if>
							</td>
						</tr>
						<tr>
						<%-- 	<td class="muiTitle">
						    	<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorizeItem.fdAuthorizeOrgId"/>
							</td> --%>
							<td>
								<c:set var="userId" value="${lbpmAuthorizeForm.fdAuthorizerId}" scope="request"></c:set>
							    <xform:checkbox property="fdLbpmAuthorizeItem" value="${fdLbpmAuthorizeItem}" mobile="true" required="true" subject="${lfn:message('sys-lbpmext-authorize:lbpmAuthorizeItem.fdAuthorizeOrgId')}" htmlElementProperties="id='aaaa'">
							       <xform:customizeDataSource className="com.landray.kmss.sys.lbpmext.authorize.service.spring.LbpmAuthorizeScopeDataSource"></xform:customizeDataSource>
							    </xform:checkbox>
							</td>
						</tr>
						<tr>
							<%-- <td class="muiTitle">
							   <bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizedPerson"/>
							</td> --%>
							<td>
								<div name="lbpm_authorize_other_row">
									<xform:address propertyId="fdAuthorizedPersonId" propertyName="fdAuthorizedPersonName" orgType="ORG_TYPE_PERSON" subject="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizedPerson') }" 
										showStatus="edit" required="true" mobile="true" htmlElementProperties="id=otherPerson">
									</xform:address>
								</div>
								<div name="lbpm_authorize_read_row" style="display: none">
									<xform:address propertyId="fdAuthorizedReaderIds" propertyName="fdAuthorizedReaderNames" orgType="ORG_TYPE_PERSON" subject="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizedPerson') }" 
										showStatus="edit" mulSelect="true" required="true" mobile="true" htmlElementProperties="id=readPerson">
									</xform:address>
								</div>
							</td>
						</tr>

						<tr>
							<td>
								<xform:address propertyId="fdDrafterDeptConstraintIds" propertyName="fdDrafterDeptConstraintNames" orgType="ORG_TYPE_ORGORDEPT|ORG_TYPE_POSTORPERSON"
									showStatus="edit"  mulSelect="true" required="false" subject="${lfn:message('sys-lbpmext-authorize:mui.authorize.fdDrafterDeptConstraints') }" mobile="true">
								</xform:address>
							</td>
						</tr>

						<tr>
							<td>
								<html:hidden property="fdScopeFormAuthorizeCateIds"/>
								<html:hidden property="fdScopeFormAuthorizeCateNames"/>
								<html:hidden property="fdScopeFormModelNames"/>
								<html:hidden property="fdScopeFormModuleNames"/>
								<html:hidden property="fdScopeFormTemplateIds"/>
								<html:hidden property="fdScopeFormTemplateNames"/>
								<%-- <input type="hidden" name="scopeTempValues">
								<html:hidden property="fdScopeFormAuthorizeCateShowtexts"/> --%>
								<div data-dojo-type="sys/lbpmext/authorize/mobile/js/authorizescope/LbpmAuthorizeScope" 
							  		data-dojo-props="idField:'scopeTempValues',nameField:'fdScopeFormAuthorizeCateShowtexts',curIds:'${lbpmAuthorizeForm.scopeTempValues}',curNames:'${lbpmAuthorizeForm.fdScopeFormAuthorizeCateShowtexts}'">
							  	</div>
							</td>
						</tr>
						<tr>
					<!-- 		<td class="muiTitle"> -->
							   <div name="aaa">
							  <%--    <bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorizationTitle"/> --%>
							   </div>
						<!-- 	</td> -->
							<td>
								 <div name="bbb">
								   <div class="timeWrap">
								    <xform:datetime htmlElementProperties="id='_fdStartTime'" property="fdStartTime" dateTimeType="datetime" placeholder="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdStartTime')}" required="true" validators="after" subject="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdStartTime')}" mobile="true"></xform:datetime>
								   </div>
								    <div class="timeWrap">
									<xform:datetime htmlElementProperties="id='_fdEndTime'" property="fdEndTime" dateTimeType="datetime" placeholder="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdEndTime')}" required="true" validators="after" subject="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdEndTime')}" mobile="true"></xform:datetime>
									</div>
								 </div>
							</td>
						</tr>
						<tr id="expireRecoverTR" style="display:none">
							<td>
								<div style="color:#999"><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdExpireRecover"/></div>
								<c:if test="${lbpmAuthorizeForm.fdExpireRecover=='true'}">
									<div class="muiLbpmextSwitch" data-dojo-type="mui/form/switch/NewSwitch"
									     data-dojo-mixins="sys/lbpmext/authorize/mobile/js/SwitchMixin" 
									     data-dojo-props="leftLabel:'',rightLabel:'',value:'on',property:'fdExpireRecover'">
									</div>
								</c:if>
								<c:if test="${lbpmAuthorizeForm.fdExpireRecover!='true'}">
									<div class="muiLbpmextSwitch" data-dojo-type="mui/form/switch/NewSwitch"
										 data-dojo-mixins="sys/lbpmext/authorize/mobile/js/SwitchMixin" 
										 data-dojo-props="leftLabel:'',rightLabel:'',value:'off',property:'fdExpireRecover'">
									</div>									
								</c:if>
						</td>
						</tr>
					</table>
					 <br><br>
				</div>
			</div>
			</div>
			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
			  	<c:if test="${ lbpmAuthorizeForm.method_GET == 'add'  }">	
			  	<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " 
			  		data-dojo-props='colSize:2,href:"javascript:submitMethod(\"save\");",transition:"slide"'>
			  		<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.mobile.btn.submit" />
			  	</li>
			  	</c:if>
			  	<c:if test="${ lbpmAuthorizeForm.method_GET == 'edit'  }">	
				 <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit "
				  	data-dojo-props='colSize:2,href:"javascript:submitMethod(\"update\");",transition:"slide"'>
				  		</i><bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.mobile.btn.update" />
				 </li>
				</c:if>
			</ul>
		 </div>
<script type="text/javascript">
require(["mui/form/ajax-form!lbpmAuthorizeForm"]);
require(['dojo/ready','dojo/query','dojo/dom-attr','dojo/dom-style','dojo/topic','dijit/registry','dojo/store/Memory','mui/dialog/Tip','mui/calendar/CalendarUtil',"dojo/dom-construct"],
		function(ready,query,domAttr,domStyle,topic,registry,Memory,Tip,cutil,domConstruct){
	
	 //校验对象
		var validorObj=null;
			ready(function(){
				if("${lbpmAuthorizeForm.method_GET=='edit'}" === "true"){
					initAuthorizeType("${lbpmAuthorizeForm.fdAuthorizeType}");
				}else if("${lbpmAuthorizeForm.method_GET=='add'}" === "true"){
					// 因默认选中是2，则在新建时初始化传2
					initAuthorizeType(2);
				}
				validorObj=registry.byId('scrollView');
				topic.subscribe('/mui/form/valueChanged',function(widget,args){
					if(widget.name=="fdAuthorizeType"){
						if(args.value == 0){
							query(".description")[0].innerHTML='<i class="mui mui-Bulb"></i><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorize.description0"/>';
							document.getElementById('expireRecoverTR').style.display="none";
						}
						if(args.value == 1){
							query(".description")[0].innerHTML='<i class="mui mui-Bulb"></i><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorize.description1"/>';
							document.getElementById('expireRecoverTR').style.display="none";
						}
						if(args.value == 2){
							query(".description")[0].innerHTML='<i class="mui mui-Bulb"></i><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorize.description2"/>';
							document.getElementById('expireRecoverTR').style.display="";
						}
						if(args.value == 3){
							query(".description")[0].innerHTML='<i class="mui mui-Bulb"></i><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorize.description4"/>';
							document.getElementById('expireRecoverTR').style.display="none";
						}
						var aaa = document.getElementsByName("aaa")[0];
						var bbb = document.getElementsByName("bbb")[0];
						if(args.value == 0 || args.value==2 || args.value==3){
							aaa.style.display = "block";
							bbb.style.display = "block";
						}else{
							aaa.style.display = "none";
							bbb.style.display = "none";
						}
						var readRow = document.getElementsByName("lbpm_authorize_read_row")[0];
						var otherRow = document.getElementsByName("lbpm_authorize_other_row")[0];
						var scrollView = registry.byId("scrollView");
						var readPerson = registry.byId("readPerson");
						var otherPerson = registry.byId("otherPerson");
						if(args.value == 1){
							readRow.style.display = "block";
							otherRow.style.display = "none";
							var removeElement = [];
							var resetElement = [];
							removeElement.push(otherPerson);
							resetElement.push(readPerson);
							scrollView.removeElementValidate(removeElement,"required");
							scrollView.resetElementValidate(resetElement);
						}else{
							readRow.style.display = "none";
							otherRow.style.display = "block";
							var removeElement = [];
							var resetElement = [];
							removeElement.push(readPerson);
							resetElement.push(otherPerson);
							scrollView.removeElementValidate(removeElement,"required");
							scrollView.resetElementValidate(resetElement);
						}
					}
					if(widget.id=="authorizer"){
						var fdAuthorizerId = args.value;
						if(fdAuthorizerId!=""){
							$.get(Com_Parameter.ContextPath+'sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=getAuthInfo4pda&authId='+fdAuthorizerId,function(data) {
								var results =  eval("("+data+")");
								var aaaa = registry.byId('aaaa');
					     		if(aaaa){
						     		aaaa.value="";
						     		aaaa.setStore(new Memory({data: results}));
						     		document.getElementsByName("fdLbpmAuthorizeItem")[0].value="";
						     	}
							}, 'text');
						}else{
							var aaaa = registry.byId('aaaa');
				     		if(aaaa){
				     			//清空子，否则报错
				     			domConstruct.empty(aaaa.valueNode);
					     		aaaa.setStore(new Memory({data: ""}));
					     		document.getElementsByName("fdLbpmAuthorizeItem")[0].value="";
					     	}
						}
					}
					if(widget.name=="fdLbpmAuthorizeItem"){
						var itemIds = "", itemNames = "";
						if(args.value!=""){
							var itemArray = args.value.split(";");
							for(var i = 0; i < itemArray.length; i++){
								var obj = itemArray[i].split(":");
								itemIds += obj[0] + ";";
								itemNames += obj[1] + ";";
							}
						}
						if(itemIds != ""){
							itemIds = itemIds.substring(0, itemIds.length - 1);
							itemNames = itemNames.substring(0, itemNames.length - 1);
						}
						var fdSysAuthorizeItemIds = document.getElementsByName("fdLbpmAuthorizeItemIds")[0];
						var fdSysAuthorizeItemNames = document.getElementsByName("fdLbpmAuthorizeItemNames")[0];
						fdSysAuthorizeItemIds.value = itemIds;
						fdSysAuthorizeItemNames.value = itemNames;
					}
					if(widget.id=="scope"){
						var fdScopeFormAuthorizeCateIdsObj = document.getElementsByName("fdScopeFormAuthorizeCateIds")[0];
						var fdScopeFormAuthorizeCateNamesObj = document.getElementsByName("fdScopeFormAuthorizeCateNames")[0];
						var fdScopeFormModelNamesObj = document.getElementsByName("fdScopeFormModelNames")[0];
						var fdScopeFormModuleNamesObj = document.getElementsByName("fdScopeFormModuleNames")[0];
						var fdScopeFormTemplateIdsObj = document.getElementsByName("fdScopeFormTemplateIds")[0];
						var fdScopeFormTemplateNamesObj = document.getElementsByName("fdScopeFormTemplateNames")[0];
						var fdScopeFormAuthorizeCateShowtextsObj = document.getElementsByName("fdScopeFormAuthorizeCateShowtexts")[0];
						fdScopeFormAuthorizeCateIdsObj.value = "";
						fdScopeFormAuthorizeCateNamesObj.value = "";
						fdScopeFormModelNamesObj.value = "";
						fdScopeFormModuleNamesObj.value = "";
						fdScopeFormTemplateIdsObj.value = "";
						fdScopeFormTemplateNamesObj.value = "";
						fdScopeFormAuthorizeCateShowtextsObj.value = "";
						if(args.value!=""){
							var fdScopeFormAuthorizeCateIds = "";
							var fdScopeFormAuthorizeCateNames = "";
							var fdScopeFormModelNames = "";
							var fdScopeFormModuleNames = "";
							var fdScopeFormTemplateIds = "";
							var fdScopeFormTemplateNames = "";
							var fdScopeFormAuthorizeCateShowtexts = "";
							var array = args.value.split(";");
							for(var i = 0; i < array.length; i++){
								var urlValue = unescape(array[i]);
								var showText = Com_GetUrlParameter(urlValue, "showText");
								var categoryId = Com_GetUrlParameter(urlValue, "categoryId");
								var categoryName = Com_GetUrlParameter(urlValue, "categoryName");
								var modelName = Com_GetUrlParameter(urlValue, "modelName");
								var moduleName = Com_GetUrlParameter(urlValue, "moduleName");
								var templateId = Com_GetUrlParameter(urlValue, "templateId");
								var templateName = Com_GetUrlParameter(urlValue, "templateName");
								fdScopeFormAuthorizeCateIds += (categoryId == null?" ":categoryId) + ";";
								fdScopeFormAuthorizeCateNames += (categoryName == null?" ":categoryName) + ";";
								fdScopeFormModelNames += (modelName == null?" ":modelName) + ";";
								fdScopeFormModuleNames += (moduleName == null?" ":moduleName) + ";";
								fdScopeFormTemplateIds += (templateId == null?" ":templateId) + ";";
								fdScopeFormTemplateNames += (templateName == null?" ":templateName) + ";";
								fdScopeFormAuthorizeCateShowtexts += (showText == null?" ":showText) + ";";
							}
							fdScopeFormAuthorizeCateIdsObj.value = fdScopeFormAuthorizeCateIds;
							fdScopeFormAuthorizeCateNamesObj.value = fdScopeFormAuthorizeCateNames;
							fdScopeFormModelNamesObj.value = fdScopeFormModelNames;
							fdScopeFormModuleNamesObj.value = fdScopeFormModuleNames;
							fdScopeFormTemplateIdsObj.value = fdScopeFormTemplateIds;
							fdScopeFormTemplateNamesObj.value = fdScopeFormTemplateNames;
							fdScopeFormAuthorizeCateShowtextsObj.value = fdScopeFormAuthorizeCateShowtexts;
						}
					}
				});
			});
			window.initAuthorizeType = function(value){
				if(value == "0"){
					query(".description")[0].innerHTML='<i class="mui mui-Bulb"></i><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorize.description0"/>';
					document.getElementById('expireRecoverTR').style.display="none";
				}
				if(value == 1){
					query(".description")[0].innerHTML='<i class="mui mui-Bulb"></i><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorize.description1"/>';
					document.getElementById('expireRecoverTR').style.display="none";
				}
				if(value == 2){
					query(".description")[0].innerHTML='<i class="mui mui-Bulb"></i><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorize.description2"/>';
					document.getElementById('expireRecoverTR').style.display="";
				}
				if(value == 3){
					query(".description")[0].innerHTML='<i class="mui mui-Bulb"></i><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorize.description4"/>';
					document.getElementById('expireRecoverTR').style.display="none";
				}
				var aaa = document.getElementsByName("aaa")[0];
				var bbb = document.getElementsByName("bbb")[0];
				if(value == 0 || value==2 || value==3){
					aaa.style.display = "block";
					bbb.style.display = "block";
				}else{
					aaa.style.display = "none";
					bbb.style.display = "none";
				}
				var readRow = document.getElementsByName("lbpm_authorize_read_row")[0];
				var otherRow = document.getElementsByName("lbpm_authorize_other_row")[0];
				var scrollView = registry.byId("scrollView");
				var readPerson = registry.byId("readPerson");
				var otherPerson = registry.byId("otherPerson");
				if(value == 1){
					readRow.style.display = "block";
					otherRow.style.display = "none";
					var removeElement = [];
					var resetElement = [];
					removeElement.push(otherPerson);
					resetElement.push(readPerson);
					scrollView.removeElementValidate(removeElement,"required");
					scrollView.resetElementValidate(resetElement);
				}else{
					readRow.style.display = "none";
					otherRow.style.display = "block";
					var removeElement = [];
					var resetElement = [];
					removeElement.push(readPerson);
					resetElement.push(otherPerson);
					scrollView.removeElementValidate(removeElement,"required");
					scrollView.resetElementValidate(resetElement);
				}
			};
			window.submitMethod = function(method){
				var aaa = document.getElementsByName("aaa")[0];
				var bbb = document.getElementsByName("bbb")[0];
				var isShowDate = false;
			    if(aaa.style.display != "none" && bbb.style.display != "none"){
			    	isShowDate = true;
			    }else{
			    	var _fdStartTime = registry.byId('_fdStartTime');
			    	var _fdEndTime = registry.byId('_fdEndTime');
			    	_fdStartTime.edit=false;
			    	_fdEndTime.edit=false;
			    }
			    if(!validorObj.validate()){
					return;
				}
			    if(isShowDate && !___validate()){
			    	return;
			    }
			    Com_Submit(document.lbpmAuthorizeForm, method);
			};
			function ___validate(){
				var startTime=query('[name="fdStartTime"]')[0].value,
					endTime=query('[name="fdEndTime"]')[0].value;
				startTime=cutil.parseDate(startTime);
				endTime=cutil.parseDate(endTime);
				if(endTime.getTime() < startTime.getTime()){
					Tip.fail({
						text:'<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorizeForm.endTimeSmallerThanCurrentTime"/>' 
					});
					return false;
				}
				return true;
			}
		});
</script>
		</html:form>
		</xform:config>
	</template:replace>
</template:include>
