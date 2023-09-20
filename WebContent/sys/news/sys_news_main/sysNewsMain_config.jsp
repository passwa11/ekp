<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<kmss:windowTitle subjectKey="sys-news:sysNewsMain.param.config" moduleKey="sys-news:news.moduleName" />
<html:form action="/sys/news/sys_news_main/sysNewsConfig.do" onsubmit="return validateAppConfigForm(this);">
<div style="margin-top:25px">
    <p class="configtitle">
		<bean:message bundle="sys-news" key="sysNewsMain.param.config" />
	</p>
	<center>
		<table  class="tb_normal" width=95%>
		<tr>
				<td class="td_normal_title" width="20%">
					<bean:message bundle="sys-news" key="sysNewsMain.config.imageWH"/>
				</td>
				<td>
				<table>
					<tr>
						<td>
							<bean:message bundle="sys-news" key="sysNewsMain.config.imageW"/>: 
							<xform:text style="width:50px" property="value(fdImageW)" validators="digits min(0)"></xform:text>px
						</td>
					</tr>
					<tr>
						<td>
							<bean:message bundle="sys-news" key="sysNewsMain.config.imageH"/>:
							<xform:text style="width:50px" property="value(fdImageH)" validators="digits min(0)"></xform:text>px
						</td>
				    </tr>
				</table>
				<div>
					<bean:message bundle="sys-news" key="sysNewsMain.config.recommended"/>： <font color="red">312px(<bean:message bundle="sys-news" key="sysNewsMain.config.width"/>)*234px(<bean:message bundle="sys-news" key="sysNewsMain.config.height"/>)</font>，<bean:message bundle="sys-news" key="sysNewsMain.config.imageWH.desc"/>
				</div>			
			</td>
		</tr>
	</table>
	<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');" order="1" ></ui:button>
	</div>
	</center>
</div>
	<html:hidden property="method_GET" />
	<input type="hidden" name="modelName" value="com.landray.kmss.sys.news.model.SysNewsConfig" />
</html:form>
<script>
	$KMSSValidation();
	function validateAppConfigForm(thisObj){
		var fdNewsExpireDate=document.getElementsByName("value(fdNewsExpireDate)")[0];
		if(fdNewsExpireDate!=null && fdNewsExpireDate.value!=""){
			if(isNaN(fdNewsExpireDate.value)){
				alert('<bean:message bundle="sys-news" key="sysNewsMain.config.hompage.validate"/>');
				fdNewsExpireDate.value="";
				fdNewsExpireDate.focus();
				return false;
			}
		}
		return true;
	}
</script>
</template:replace>
</template:include>