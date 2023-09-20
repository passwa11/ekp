<%@page import="java.util.Locale"%>
<%@page import="com.landray.kmss.sys.language.utils.SysLangUtil"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmUsageContent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTag"%>
<%
	pageContext.setAttribute("isLangSuportEnabled", MultiLangTextareaGroupTag.isLangSuportEnabled());
	request.setAttribute("vEnter", "\r\n");
%>

<style type="text/css">
.calcBtn {
	display: block;
	margin: 0;
	padding: 5px 0;
	width: 100%;
	color: #333;
	font-size: 18px;
	font-weight: 400;
	line-height: 1.42857143;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	text-transform: capitalize;
	-ms-touch-action: manipulation;
			touch-action: manipulation;
	cursor: pointer;
	-webkit-user-select: none;
		 -moz-user-select: none;
			-ms-user-select: none;
					user-select: none;
  background-image: none;
  background-color: #fff;
	border: 1px solid transparent;
	border-radius: 0;
	outline: 0;
	transition-duration: .3s;
	-wekbit-box-sizing: content-box;
	        box-sizing: content-box;
}
.calcBtn:hover,
.calcBtn:focus{
  text-decoration: none;
	color: #fff;
  background-color: #4285f4;
  border-color: #4285f4;
}
.calcBtn:active {
  background-image: none;
  outline: 0;
  -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, .15);
          box-shadow: inset 0 3px 5px rgba(0, 0, 0, .15);
}

.resultBtn{
	font-size: 14px;
	padding: 2px 10px;
	border-radius: 4px;
	border-color: #d2d2d2;
	margin: 0 5px;
	width: auto;
	display: inline-block;
}

</style>


<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-lbpmservice-support" key="table.lbpmUsageContent"/></span>
		</h2>
		<html:form action="/sys/lbpmservice/support/lbpm_usage/lbpmCommunicateUsageAction.do">
		<center>
		<div style="margin:auto auto 60px;">
		<table class="tb_normal" width=95%>
			
			<!-- 默认签字意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.sign"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=90%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
						</tr>
						<tr>
							<td width=90%>
							
							<c:if test="${!isLangSuportEnabled }">
								 
										<xform:textarea property="customizeUsageContentSign" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
									
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentSign" alias="customizeUsageContentSign" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentSign_lang" />

							</td>
							<td align="center">
								
								<ui:switch property="isSignContentRequired" id="isSignContentRequired"></ui:switch>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<!-- 默认审批节点暂停意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.nodeSuspend"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=90%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
						</tr>
						<tr>
							<td width=90%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentNodeSuspend" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit" />
							
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentNodeSuspend" alias="customizeUsageContentNodeSuspend" validators="maxLength(4000)" style="width:100%;height:80px"/>
							</c:if>
							<html:hidden property="customizeUsageContentNodeSuspend_lang" />
							</td>
							<td align="center">
								<ui:switch property="isNodeSuspendContentRequired" id="isNodeSuspendContentRequired"></ui:switch>
							</td>
						</tr>
					</table>
				
				</td>
			</tr>
			
			
			<!-- 默认审批节点唤醒意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.handler.nodeResume"/>
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" align="center" width=90%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.content"/>
							</td>
							<td class="td_normal_title" align="center" width=10%>
								<bean:message bundle="sys-lbpmservice-support" key="lbpmUsageContent.isRequired"/>
							</td>
						</tr>
						<tr>
							<td width=90%>
							
							<c:if test="${!isLangSuportEnabled }">
										<xform:textarea property="customizeUsageContentNodeResume" style="width:100%;height:80px" validators="maxLength(4000)" showStatus="edit"/>
								
							</c:if>
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="customizeUsageContentNodeResume" alias="customizeUsageContentNodeResume" validators="maxLength(4000)" style="width:100%;height:80px" />
							</c:if>
							<html:hidden property="customizeUsageContentNodeResume_lang" />
							</td>
							<td align="center">
								<ui:switch property="isNodeResumeContentRequired" id="isNodeResumeContentRequired"></ui:switch>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<!-- 默认节点处理人身份重复跳过意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					默认节点处理人身份重复跳过意见
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td width=20% class="td_normal_title">使用下方设置的意见</td>
							<td width=10%><ui:switch property="customizeRepeatJumpValue" id="customizeRepeatJumpValue" onValueChange="switchDefaultRepeatJump('customizeRepeatJumpValue','customizeBeforeRepeatJumpValue','customizeSystemRepeatJumpValue')"></ui:switch></td>
							<td width=30% class="td_normal_title">使用该处理人在之前节点的审核意见</td>
							<td width=10%><ui:switch property="customizeBeforeRepeatJumpValue" id="customizeBeforeRepeatJumpValue" onValueChange="switchDefaultRepeatJump('customizeBeforeRepeatJumpValue','customizeRepeatJumpValue','customizeSystemRepeatJumpValue')"></ui:switch></td>
							<td width=20% class="td_normal_title">使用系统自动生成的意见</td>
							<td width=10%><ui:switch property="customizeSystemRepeatJumpValue" id="customizeSystemRepeatJumpValue" onValueChange="switchDefaultRepeatJump('customizeSystemRepeatJumpValue','customizeBeforeRepeatJumpValue','customizeRepeatJumpValue')"></ui:switch></td>
						</tr>
						
						<tr>
							<td width=100% colspan="6">
								<c:if test="${!isLangSuportEnabled }">
									 <textarea name="customizeUsageContent_repeatJump" validate="maxLength(4000)" style="width:100%;height:80px" showStatus="edit"></textarea>
								</c:if>
								<c:if test="${isLangSuportEnabled }">
									<xlang:lbpmlangAreaNew property="customizeUsageContent_repeatJump" alias="customizeUsageContent_repeatJump" validators="maxLength(4000)" style="width:100%;height:80px" />
								</c:if>
								<html:hidden property="customizeUsageContent_repeatJump_lang" />
							</td>
						</tr>
						
					</table>
				</td>
			</tr>
			
			<!-- 默认节点超时跳过意见 -->
			<tr>
				<td class="td_normal_title" width=15%>
					默认节点超时跳过意见
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
					     <tr>
							<td width=30% class="td_normal_title">使用下方设置的意见</td>
							<td width=20%><ui:switch property="customizeTimeOutValue" id="customizeTimeOutValue" onValueChange="switchTimeOut('customizeTimeOutValue','customizeSystemTimeOutValue')"></ui:switch></td>
							<td width=30% class="td_normal_title">使用系统自动生成的意见</td>
							<td width=20%><ui:switch property="customizeSystemTimeOutValue" id="customizeSystemTimeOutValue" onValueChange="switchTimeOut('customizeSystemTimeOutValue','customizeTimeOutValue')"></ui:switch></td>
						</tr>
						
						<tr>
							<td width=100% colspan="4">
								<c:if test="${!isLangSuportEnabled }">
									 <textarea name="customizeUsageContent_timeOut" validate="maxLength(4000)" style="width:100%;height:80px" showStatus="edit"></textarea>
								</c:if>
								<c:if test="${isLangSuportEnabled }">
									<xlang:lbpmlangAreaNew property="customizeUsageContent_timeOut" alias="customizeUsageContent_timeOut" validators="maxLength(4000)" style="width:100%;height:80px" />
								</c:if>
								<html:hidden property="customizeUsageContent_timeOut_lang" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmUsage.fdDescription"/>
				</td><td width="85%">
					<bean:message  bundle="sys-lbpmservice-support" key="lbpmUsageContent.fdDescription.details.1"/><br>
					<bean:message  bundle="sys-lbpmservice-support" key="lbpmUsageContent.fdDescription.details.2"/>
				</td>
			</tr>
			<tr>
				<td  colspan="2" align="center" >
					<!-- 确定 -->
					<input class="calcBtn resultBtn" type=button value="<bean:message key="button.ok"/>" onclick="setCustomizeUsageContent();">
					<input class="calcBtn resultBtn" type="button" value="<bean:message key="button.cancel"/>" onClick="window.close();">
				</td>
			</tr>
		</table>
		</div>
		</center>

		</html:form>
		
		<%
			LbpmUsageContent lbpmUsageContent = new LbpmUsageContent();
			pageContext.setAttribute("lbpmUsageContent", lbpmUsageContent);
			
			
		%>
		
		<script type="text/javascript">
		var dialogObject;
		if(window.showModalDialog){
			dialogObject = window.dialogArguments;
		}else{
			if(opener){
				dialogObject = opener.Com_Parameter.Dialog;
			}
		}
		
		<%
		Locale locale =Locale.getDefault();
		%>
		var currentLang ='<%=MultiLangTextareaGroupTag.getUserLangKey()%>';
		
		var langJson = <%=MultiLangTextareaGroupTag.getLangsJsonStr()%>;
		var isLangSuportEnabled = <%=MultiLangTextareaGroupTag.isLangSuportEnabled()%>;
		
		
		function switchDefaultRepeatJump(op,op1,op2){
			if(LUI(op).checkbox.prop('checked')){
				LUI(op1).checkbox.prop('checked',false);
				$("input[name='"+op1+"']").val("false");
				LUI(op2).checkbox.prop('checked',false);
				$("input[name='"+op2+"']").val("false");
			}
		}

		function switchTimeOut(op,op1){
			if(LUI(op).checkbox.prop('checked')){
				LUI(op1).checkbox.prop('checked',false);
				$("input[name='"+op1+"']").val("false");
			}
		}
		
		
		
		LUI.ready(function(){
			
			function getCustomizeJsonValue(pJson,langType){
				console.log("###pJson");
				console.log(pJson);
				if(!pJson){
					return "";
				}
				
				var customizeJson=JSON.parse(pJson);
				for(var z=0;z<customizeJson.length;z++){
					console.log("hah2:"+customizeJson[z]["lang"]);
					if(customizeJson[z]["lang"]==langType){
						if(customizeJson[z]["value"]=="&nbsp;"){
							return "";
						}else{
							return customizeJson[z]["value"];	
						}
						
					}
				}
			}
			
			function initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,fileName,isContentRequired){
				
				var usageContentJson=lbpmCustomizeContentJson[fileName+"_lang"];
				console.log("###"+isContentRequired);
				console.log(11);
				for(var i=0;i<langJson["support"].length;i++){
					var supportValue =langJson["support"][i]["value"];
					if(getCustomizeJsonValue(usageContentJson,supportValue)){
						document.getElementsByName(fileName+"_"+supportValue)[0].value=getCustomizeJsonValue(usageContentJson,supportValue);	
					}else{
						document.getElementsByName(fileName+"_"+supportValue)[0].value="";
					}
				}	
				console.log(22);
				document.getElementsByName(fileName+"_lang")[0].value=lbpmCustomizeContentJson[fileName+"_lang"];
				console.log(document.getElementsByName(isContentRequired)[0]);
				
				var isPassContentRequired=lbpmCustomizeContentJson[isContentRequired];
				$("input[name='"+isContentRequired+"']").val(isPassContentRequired);
				LUI(isContentRequired).checkbox.prop('checked',isPassContentRequired=="true");
				document.getElementsByName(fileName)[0].value=lbpmCustomizeContentJson[fileName];
			}
			
			function initNoValueCustomizeLangEnabled(lbpmCustomizeContentJson,filedName,isContentRequiredFiled,isContentRequired,defaultUsageContent4Lang,defaultUsageContent){
				var officialLang=currentLang;
				console.log("###officialLang"+officialLang);
				console.log("###"+isContentRequiredFiled);
				var usageContentJson=lbpmCustomizeContentJson;
				console.log("usageContentJson");
				console.log(lbpmCustomizeContentJson);
				var defaultUsageContentOfficialLang='';
				for(var i=0;i<langJson["support"].length;i++){
					var supportValue =langJson["support"][i]["value"];
					if(officialLang==supportValue){
						defaultUsageContentOfficialLang=getCustomizeJsonValue(usageContentJson,supportValue);
					}
					
					document.getElementsByName(filedName+"_"+supportValue)[0].value=getCustomizeJsonValue(usageContentJson,supportValue);
				}	
				var isPassContentRequired=isContentRequired;
				$("input[name='"+isContentRequiredFiled+"']").val(isPassContentRequired);
				LUI(isContentRequiredFiled).checkbox.prop('checked',isPassContentRequired=="true");
				document.getElementsByName(filedName+"_lang")[0].value=defaultUsageContent4Lang;
				document.getElementsByName(filedName)[0].value=defaultUsageContentOfficialLang;
			}
			
			
			//支持多语言
			if(isLangSuportEnabled){
				//扩展属性值
				var lbpmCustomizeContentJsonStr;
				if(dialogObject && dialogObject.lbpmCustomizeParameter){
					lbpmCustomizeContentJsonStr=dialogObject.lbpmCustomizeParameter.dataInfo;
				}
				if(lbpmCustomizeContentJsonStr){
					var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
					//有扩展属性值
					if(lbpmCustomizeContentJson){
						/*var usageContentJson=lbpmCustomizeContentJson["customizeUsageContent_lang"];
						
						console.log(11);
						for(var i=0;i<langJson["support"].length;i++){
							var supportValue =langJson["support"][i]["value"];
							document.getElementsByName("customizeUsageContent_"+supportValue)[0].value=getCustomizeJsonValue(usageContentJson,supportValue);
						}	
						console.log(22);
						document.getElementsByName("customizeUsageContent_lang")[0].value=lbpmCustomizeContentJson["customizeUsageContent_lang"];
						console.log(document.getElementsByName("isPassContentRequired")[0]);
						
						var isPassContentRequired=lbpmCustomizeContentJson["isPassContentRequired"];
						LUI('isPassContentRequired').checkbox.prop('checked',isPassContentRequired=="true");
						document.getElementsByName("customizeUsageContent")[0].value=lbpmCustomizeContentJson["customizeUsageContent"];*/
						
						
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentSign','isSignContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentNodeSuspend','isNodeSuspendContentRequired');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContentNodeResume','isNodeResumeContentRequired');
						
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContent_repeatJump','customizeRepeatJumpValue');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContent_repeatJump','customizeBeforeRepeatJumpValue');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContent_repeatJump','customizeSystemRepeatJumpValue');
						
						
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContent_timeOut','customizeTimeOutValue');
						initHasValueCustomizeLangEnabled(lbpmCustomizeContentJson,'customizeUsageContent_timeOut','customizeSystemTimeOutValue');
						
						
					}
				}else{
					/*console.log("###");
					var usageContentJson='${lbpmUsageContent.defaultUsageContent4Lang}';
					console.log("usageContentJson");
					console.log(usageContentJson);
					for(var i=0;i<langJson["support"].length;i++){
						var supportValue =langJson["support"][i]["value"];
						document.getElementsByName("customizeUsageContent_"+supportValue)[0].value=getCustomizeJsonValue(usageContentJson,supportValue);
					}	
					var isPassContentRequired='${lbpmUsageContent.isPassContentRequired}';
					LUI('isPassContentRequired').checkbox.prop('checked',isPassContentRequired=="true");
					document.getElementsByName("customizeUsageContent_lang")[0].value='${lbpmUsageContent.defaultUsageContent4Lang}';
					document.getElementsByName("customizeUsageContent")[0].value='${lbpmUsageContent.defaultUsageContent}';*/
					//lbpmCustomizeContentJson,filedName,isContentRequiredFiled,isContentRequired,defaultUsageContent4Lang,defaultUsageContent
					
					initNoValueCustomizeLangEnabled('${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_sign4Lang)}','customizeUsageContentSign','isSignContentRequired','${lbpmUsageContent.isSignContentRequired}','${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_sign4Lang)}','${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_sign)}');
					initNoValueCustomizeLangEnabled('${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend4Lang)}','customizeUsageContentNodeSuspend','isNodeSuspendContentRequired','${lbpmUsageContent.isNodeSuspendContentRequired}','${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend4Lang)}','${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend)}');
					initNoValueCustomizeLangEnabled('${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume4Lang)}','customizeUsageContentNodeResume','isNodeResumeContentRequired','${lbpmUsageContent.isNodeResumeContentRequired}','${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume4Lang)}','${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume)}');
					
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump4Lang)}","customizeUsageContent_repeatJump","customizeRepeatJumpValue","${lbpmUsageContent.defaultRepeatJumpValue}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump4Lang)}","customizeUsageContent_repeatJump","customizeBeforeRepeatJumpValue","${lbpmUsageContent.beforeRepeatJumpValue}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump4Lang)}","customizeUsageContent_repeatJump","customizeSystemRepeatJumpValue","${lbpmUsageContent.systemRepeatJumpValue}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump)}");
					
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut4Lang)}","customizeUsageContent_timeOut","customizeTimeOutValue","${lbpmUsageContent.defaultTimeOutValue}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut)}");
					initNoValueCustomizeLangEnabled("${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut4Lang)}","customizeUsageContent_timeOut","customizeSystemTimeOutValue","${lbpmUsageContent.systemTimeOutValue}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut4Lang)}","${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut)}");
					
					
				}
			}else{ 
				var lbpmCustomizeContentJsonStr;
				if(dialogObject && dialogObject.lbpmCustomizeParameter){
					lbpmCustomizeContentJsonStr=dialogObject.lbpmCustomizeParameter.dataInfo;
				}
				if(lbpmCustomizeContentJsonStr){
					var lbpmCustomizeContentJson=JSON.parse(lbpmCustomizeContentJsonStr);
					//有扩展属性值
					if(lbpmCustomizeContentJson){
						var isSignContentRequiredValue=lbpmCustomizeContentJson["isSignContentRequired"];
						$("input[name='isSignContentRequired']").val(isSignContentRequiredValue);
						LUI('isSignContentRequired').checkbox.prop('checked',isSignContentRequiredValue=="true");
						document.getElementsByName("customizeUsageContentSign")[0].value=lbpmCustomizeContentJson["customizeUsageContentSign"];
						
						var isNodeSuspendContentValue=lbpmCustomizeContentJson["isNodeSuspendContentRequired"];
						$("input[name='isNodeSuspendContentRequired']").val(isNodeSuspendContentValue);
						LUI('isNodeSuspendContentRequired').checkbox.prop('checked',isNodeSuspendContentValue=="true");
						document.getElementsByName("customizeUsageContentNodeSuspend")[0].value=lbpmCustomizeContentJson["customizeUsageContentNodeSuspend"];
						
						var isNodeResumeContentValue=lbpmCustomizeContentJson["isNodeResumeContentRequired"];
						$("input[name='isNodeResumeContentRequired']").val(isNodeResumeContentValue);
						LUI('isNodeResumeContentRequired').checkbox.prop('checked',isNodeResumeContentValue=="true");
						document.getElementsByName("customizeUsageContentNodeResume")[0].value=lbpmCustomizeContentJson["customizeUsageContentNodeResume"];
						
						
						var customizeRepeatJumpValue=lbpmCustomizeContentJson["customizeRepeatJumpValue"];
						$("input[name='customizeRepeatJumpValue']").val(customizeRepeatJumpValue);
						LUI('customizeRepeatJumpValue').checkbox.prop('checked',customizeRepeatJumpValue=="true");
						document.getElementsByName("customizeUsageContent_repeatJump")[0].value=lbpmCustomizeContentJson["customizeUsageContent_repeatJump"];
						
						var customizeBeforeRepeatJumpValue=lbpmCustomizeContentJson["customizeBeforeRepeatJumpValue"];
						$("input[name='customizeBeforeRepeatJumpValue']").val(customizeRepeatJumpValue);
						LUI('customizeBeforeRepeatJumpValue').checkbox.prop('checked',customizeRepeatJumpValue=="true");
						document.getElementsByName("customizeUsageContent_repeatJump")[0].value=lbpmCustomizeContentJson["customizeUsageContent_repeatJump"];
						
						var customizeRepeatJumpValue=lbpmCustomizeContentJson["customizeSystemRepeatJumpValue"];
						$("input[name='customizeSystemRepeatJumpValue']").val(customizeRepeatJumpValue);
						LUI('customizeSystemRepeatJumpValue').checkbox.prop('checked',customizeRepeatJumpValue=="true");
						document.getElementsByName("customizeUsageContent_repeatJump")[0].value=lbpmCustomizeContentJson["customizeUsageContent_repeatJump"];
						
						
						var customizeTimeOutValue=lbpmCustomizeContentJson["customizeTimeOutValue"];
						$("input[name='customizeTimeOutValue']").val(customizeRepeatJumpValue);
						LUI('customizeTimeOutValue').checkbox.prop('checked',customizeRepeatJumpValue=="true");
						document.getElementsByName("customizeUsageContent_timeOut")[0].value=lbpmCustomizeContentJson["customizeUsageContent_timeOut"];
						
						var customizeSystemTimeOutValue=lbpmCustomizeContentJson["customizeSystemTimeOutValue"];
						$("input[name='customizeSystemTimeOutValue']").val(customizeRepeatJumpValue);
						LUI('customizeSystemTimeOutValue').checkbox.prop('checked',customizeRepeatJumpValue=="true");
						document.getElementsByName("customizeUsageContent_timeOut")[0].value=lbpmCustomizeContentJson["customizeUsageContent_timeOut"];
						
					}
				}else{
						var isPassContentValue='${lbpmUsageContent.isSignContentRequired}';
						$("input[name='isSignContentRequired']").val(isPassContentValue);
						LUI('isSignContentRequired').checkbox.prop('checked',isPassContentValue=="true");
						document.getElementsByName("customizeUsageContentSign")[0].value='${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_sign)}';
						
						var isNodeSuspendContentValue='${lbpmUsageContent.isNodeSuspendContentRequired}';
						$("input[name='isNodeSuspendContentRequired']").val(isNodeSuspendContentValue);
						LUI('isNodeSuspendContentRequired').checkbox.prop('checked',isNodeSuspendContentValue=="true");
						document.getElementsByName("customizeUsageContentNodeSuspend")[0].value='${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeSuspend)}';
						
						var isNodeResumeContentValue='${lbpmUsageContent.isNodeResumeContentRequired}';
						$("input[name='isNodeResumeContentRequired']").val(isNodeResumeContentValue);
						LUI('isNodeResumeContentRequired').checkbox.prop('checked',isNodeResumeContentValue=="true");
						document.getElementsByName("customizeUsageContentNodeResume")[0].value='${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_nodeResume)}';
					
						
						var customizeRepeatJumpValue='${lbpmUsageContent.defaultRepeatJumpValue}';
						$("input[name='customizeRepeatJumpValue']").val(customizeRepeatJumpValue);
						LUI('customizeRepeatJumpValue').checkbox.prop('checked',customizeRepeatJumpValue=="true");
						document.getElementsByName("customizeUsageContent_repeatJump")[0].value='${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump)}';
						
						var customizeBeforeRepeatJumpValue='${lbpmUsageContent.beforeRepeatJumpValue}';
						$("input[name='customizeBeforeRepeatJumpValue']").val(customizeRepeatJumpValue);
						LUI('customizeBeforeRepeatJumpValue').checkbox.prop('checked',customizeRepeatJumpValue=="true");
						document.getElementsByName("customizeUsageContent_repeatJump")[0].value='${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump)}';
						
						var customizeRepeatJumpValue='${lbpmUsageContent.systemRepeatJumpValue}';
						$("input[name='customizeSystemRepeatJumpValue']").val(customizeRepeatJumpValue);
						LUI('customizeSystemRepeatJumpValue').checkbox.prop('checked',customizeRepeatJumpValue=="true");
						document.getElementsByName("customizeUsageContent_repeatJump")[0].value='${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_repeatJump)}';
						
						
						var customizeTimeOutValue='${lbpmUsageContent.defaultTimeOutValue}';
						$("input[name='customizeTimeOutValue']").val(customizeRepeatJumpValue);
						LUI('customizeTimeOutValue').checkbox.prop('checked',customizeRepeatJumpValue=="true");
						document.getElementsByName("customizeUsageContent_timeOut")[0].value='${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut)}';
						
						var customizeSystemTimeOutValue='${lbpmUsageContent.systemTimeOutValue}';
						$("input[name='customizeSystemTimeOutValue']").val(customizeRepeatJumpValue);
						LUI('customizeSystemTimeOutValue').checkbox.prop('checked',customizeRepeatJumpValue=="true");
						document.getElementsByName("customizeUsageContent_timeOut")[0].value='${lfn:escapeJs(lbpmUsageContent.defaultUsageContent_timeOut)}';
						
						
				}
			}
		
		});
		
		//如果是单语言环境下去同步多语言环境下官方语言的值
		function handlerOfficialLangValue(eleName){
			console.log("handlerOfficialLangValue");
			console.log("eleName:"+eleName);
			var fdValue = document.getElementsByName(eleName)[0].value;
			
			console.log("officialElName:"+currentLang);
			var eleNameLangJsonStr=$(eleName+"_lang").val();
			if(eleNameLangJsonStr){
				var eleNameLangJson=JSON.parse(eleNameLangJsonStr);
				console.log("eleNameLangJson");
				console.log(eleNameLangJson);
				for(var i=0;i<eleNameLangJson.length;i++){
					if(eleNameLangJson[i]["lang"]==currentLang){
						eleNameLangJson[i]["value"]=fdValue;
					}
				}
				
				$("input[name="+eleName+"_lang]").val(JSON.stringify(eleNameLangJsonStr));
			}else{//即使没有多语言，也需要更新多语言环境下的官方语言值 [{"lang":"zh-CN","value":"&nbsp;"},{"lang":"en-US","value":"&nbsp;"}]
				$("input[name="+eleName+"_lang]").val('[{"lang":"'+currentLang+'","value":'+'"'+fdValue+'"}]');
			}
		}
		
		function handleLangByElName(eleName,elJsonName){
			//[{lang:"zh-CN","value":""},{lang:"en-US","value":""}]
			var elLang=[];
			if(!isLangSuportEnabled){
				return elLang;
			}
			var fdValue = document.getElementsByName(eleName)[0].value;
			
			var officialElName=eleName+"_"+langJson["official"]["value"];
			document.getElementsByName(officialElName)[0].value=fdValue;
			var lang={};
			lang["lang"]=langJson["official"]["value"];
			lang["value"]=fdValue;
			elLang.push(lang);
			for(var i=0;i<langJson["support"].length;i++){
				console.log(eleName);
				console.log(langJson["support"]);
				console.log(langJson["support"][i]);
				var elName = eleName+"_"+langJson["support"][i]["value"];
				if(elName==officialElName){
					continue;
				}
				lang={};
				lang["lang"]=langJson["support"][i]["value"];
				lang["value"]=document.getElementsByName(elName)[0].value;
				elLang.push(lang);
			}
			document.getElementsByName(elJsonName)[0].value=JSON.stringify(elLang);
		}
		
		
		function setCustomizeUsageContent(){
			
			handleLangByElName("customizeUsageContentSign","customizeUsageContentSign_lang");
			handleLangByElName("customizeUsageContentNodeSuspend","customizeUsageContentNodeSuspend_lang");
			handleLangByElName("customizeUsageContentNodeResume","customizeUsageContentNodeResume_lang");
		
			handleLangByElName("customizeUsageContent_repeatJump","customizeUsageContent_repeatJump_lang");
			handleLangByElName("customizeUsageContent_timeOut","customizeUsageContent_timeOut_lang");
			
			
			if(!isLangSuportEnabled){
				
				handlerOfficialLangValue("customizeUsageContentSign");
				handlerOfficialLangValue("customizeUsageContentNodeSuspend");
				handlerOfficialLangValue("customizeUsageContentNodeResume");
				
				handlerOfficialLangValue("customizeUsageContent_repeatJump");
				handlerOfficialLangValue("customizeUsageContent_timeOut");
				
			}
			
			var jsonCustomize={};
			
			jsonCustomize.customizeUsageContentSign=$("textarea[name='customizeUsageContentSign']").val();
			jsonCustomize.customizeUsageContentSign_lang=$("input[name='customizeUsageContentSign_lang']").val();
			jsonCustomize.isSignContentRequired=$("input[name='isSignContentRequired']").val();
			
			
			jsonCustomize.customizeUsageContentNodeSuspend=$("textarea[name='customizeUsageContentNodeSuspend']").val();
			jsonCustomize.customizeUsageContentNodeSuspend_lang=$("input[name='customizeUsageContentNodeSuspend_lang']").val();
			jsonCustomize.isNodeSuspendContentRequired=$("input[name='isNodeSuspendContentRequired']").val();
			
			
			jsonCustomize.customizeUsageContentNodeResume=$("textarea[name='customizeUsageContentNodeResume']").val();
			jsonCustomize.customizeUsageContentNodeResume_lang=$("input[name='customizeUsageContentNodeResume_lang']").val();
			jsonCustomize.isNodeResumeContentRequired=$("input[name='isNodeResumeContentRequired']").val();
			
			jsonCustomize.customizeUsageContent_repeatJump=$("textarea[name='customizeUsageContent_repeatJump']").val();
			jsonCustomize.customizeUsageContent_repeatJump_lang=$("input[name='customizeUsageContent_repeatJump_lang']").val();
			jsonCustomize.customizeRepeatJumpValue=$("input[name='customizeRepeatJumpValue']").val();
			jsonCustomize.customizeBeforeRepeatJumpValue=$("input[name='customizeBeforeRepeatJumpValue']").val();
			jsonCustomize.customizeSystemRepeatJumpValue=$("input[name='customizeSystemRepeatJumpValue']").val();
			
			
			jsonCustomize.customizeUsageContent_timeOut=$("textarea[name='customizeUsageContent_timeOut']").val();
			jsonCustomize.customizeUsageContent_timeOut_lang=$("input[name='customizeUsageContent_timeOut_lang']").val();
			jsonCustomize.customizeTimeOutValue=$("input[name='customizeTimeOutValue']").val();
			jsonCustomize.customizeSystemTimeOutValue=$("input[name='customizeSystemTimeOutValue']").val();
			
			
			var jsonCustomizeText=JSON.stringify(jsonCustomize);
			if(dialogObject){
				
				dialogObject.rtnData=[{'jsonCustomize':jsonCustomizeText}];
			}
			
			close();
		}

		//添加关闭事件
		Com_AddEventListener(window, "beforeunload", function(){
			if(dialogObject){
				
				dialogObject.AfterShow();
			}
			});
		

	 	</script>
	</template:replace>
</template:include>
