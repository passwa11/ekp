<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTag"%>
<%
	pageContext.setAttribute("isLangSuportEnabled", MultiLangTextareaGroupTag.isLangSuportEnabled());
%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-lbpmservice-support" key="table.lbpmUsage"/></span>
		</h2>
		
		<html:form action="/sys/lbpmservice/support/lbpm_usage/lbpmUsage.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpmservice-support" key="lbpmUsage.fdUsageContent"/>
						</td><td width="85%">
							<c:if test="${isLangSuportEnabled }">
								<xlang:lbpmlangAreaNew property="fdUsageContent" validators="maxLength(4000)" style="width:85%;height:200px" langs="${lbpmUsageForm.fdLangJson}"/>
							</c:if>
							<c:if test="${!isLangSuportEnabled }">
								<xform:textarea property="fdUsageContent" style="width:85%;height:200px" />
							</c:if>
							<%-- <xform:textarea property="fdUsageContent" style="width:85%;height:200px" />
							<xlang:lbpmlangArea property="fdUsageContent" style="width:85%;height:200px" langs="${lbpmUsageForm.fdLangJson}"/> --%>
							<html:hidden property="fdLangJson" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpmservice-support" key="lbpmUsage.fdDescription"/>
						</td><td width="85%">
							<bean:message  bundle="sys-lbpmservice-support" key="lbpmUsage.fdDescription.details.1"/><br>
							<bean:message  bundle="sys-lbpmservice-support" key="lbpmUsage.fdDescription.details.2"/>
						</td>
					</tr>
				</table>
			</center>
			<html:hidden property="fdId" />
			<html:hidden property="fdIsSysSetup" value="true"/>
			<html:hidden property="fdIsAppend" value="true"/>
			<html:hidden property="fdCreatorId"/>
			<html:hidden property="fdCreateTime"/>
			<html:hidden property="method_GET" />
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<c:if test="${lbpmUsageForm.method_GET=='edit'}">
				<ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="Com_Submit(document.lbpmUsageForm, 'update');"></ui:button>
			</c:if>
			<c:if test="${lbpmUsageForm.method_GET=='add'}">
				<ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="Com_Submit(document.lbpmUsageForm, 'save');"></ui:button>
			</c:if>
			</center>
		</html:form>
		<script type="text/javascript">
	 		$KMSSValidation();

//==============多语言增加==================//
Com_IncludeFile("json2.js");
var langJson = <%=MultiLangTextareaGroupTag.getLangsJsonStr()%>;
var isLangSuportEnabled = <%=MultiLangTextareaGroupTag.isLangSuportEnabled()%>;

Com_Parameter.event["submit"].push(function(){
	 handleLangByElName("fdUsageContent","fdLangJson");
	 return true;
});

function handleLangByElName(eleName,elJsonName){
	//[{lang:"zh-CN","value":""},{lang:"en-US","value":""}]
	var elLang=[];
	if(!isLangSuportEnabled){
		return elLang;
	}
	var fdValue = document.getElementsByName(eleName)[0].value.replace(/^\s+|\s+$/g, "");
	document.getElementsByName(eleName)[0].value = fdValue;
	var officialElName=eleName+"_"+langJson["official"]["value"];
	document.getElementsByName(officialElName)[0].value=fdValue;
	var lang={};
	lang["lang"]=langJson["official"]["value"];
	lang["value"]=fdValue;
	elLang.push(lang);
	for(var i=0;i<langJson["support"].length;i++){
		var elName = eleName+"_"+langJson["support"][i]["value"];
		if(elName==officialElName){
			continue;
		}
		lang={};
		lang["lang"]=langJson["support"][i]["value"];
		lang["value"]=document.getElementsByName(elName)[0].value.replace(/^\s+|\s+$/g, "");
		elLang.push(lang);
	}
	document.getElementsByName(elJsonName)[0].value=JSON.stringify(elLang);
}

	 	</script>
	</template:replace>
</template:include>
