<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.category.actions.SysCategoryConfigAction" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%> 
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.design.SysConfigs"%>
<%@page import="com.landray.kmss.sys.config.design.SysCfgModule"%>
<%@ page import="com.landray.kmss.sys.config.design.SysCfgCategoryMng"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<html:form action="/sys/category/sys_category_config/sysCategoryConfig.do?method=saveConfig">
<div style="margin-top:25px;margin-bottom:50px">
<p class="configtitle"><bean:message  bundle="sys-category" key="table.SysCategoryConfig"/></p>
<div  style="text-align: center;color:red"><bean:message  bundle="sys-category" key="table.SysCategoryConfig.summary"/></div>
<br/>
<center>
<table class="tb_normal" width="95%">
       <tr>
	       <td align="center" style="font-size:14px;font-weight:bold;"><bean:message  bundle="sys-category" key="sysCategoryConfig.fdName"/></td>
	       <td align="center" style="font-size:14px;font-weight:bold;"><bean:message  bundle="sys-category" key="sysCategoryConfig.path"/></td>
	       <td align="center" style="font-size:14px;font-weight:bold;"><bean:message  bundle="sys-category" key="sysCategoryConfig.status"/></td>
       </tr>
	    <%
		  SysConfigs a = SysConfigs.getInstance();
	    %>
       <c:forEach items="${modelNameList}" var="modelName" varStatus="vstatus">
            <%  Object basedocObj = pageContext.getAttribute("modelName");
				    String Url="";
				    SysDictModel sysDictModel  = null;
				   if(basedocObj != null) { 
					    String modelName = (String)basedocObj;
					    if(!"com.landray.kmss.km.signature.model.KmSignatureCategory".equals(modelName)&&!"com.landray.kmss.km.coproject.model.KmCoprojectCategory".equals(modelName)){
						    sysDictModel = SysDataDict.getInstance().getModel(modelName);
						    if(sysDictModel!=null&&StringUtil.isNotNull(sysDictModel.getUrl())){
						       Url =  sysDictModel.getUrl();
						    }
					    } 
					}
		     %>
		     <%
		     if(sysDictModel!=null&&StringUtil.isNotNull(Url)){
				    String[] temp = Url.split("/");
				    SysCfgModule sysCfgModule = null;
				    String moduleKey = "";
				    String Messagekey  = "";
				    sysCfgModule = a.getModule("/"+temp[1]+"/"+temp[2]+"/");
				    if(sysCfgModule==null){
				    	sysCfgModule = a.getModule("/"+temp[1]+"/"+temp[2]+"/"+temp[3]+"/");
				    }
				    if(sysCfgModule!=null){
				        moduleKey = sysCfgModule.getMessageKey();
				    }
				    if(sysDictModel!=null){ 
				    	Messagekey= sysDictModel.getMessageKey();
				    }
		     %>
			<tr>
				<td>
				   <% out.print("【"+ResourceUtil.getString(moduleKey)+"】"+ResourceUtil.getString(Messagekey)); %>
				</td>
				<td><% out.print(sysCfgModule.getUrlPrefix()); %></td>
				<td>
				    <ui:switch property="value(${modelName})" checkVal="1" unCheckVal="0" enabledText="${lfn:message('sys-category:sysCategory.open')}" disabledText="${lfn:message('sys-category:sysCategory.close')}"></ui:switch>
				</td>
			</tr>
			<%}%>
		</c:forEach>
</table>
<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button style="position: fixed;bottom:0px;left: 0px;width:100%;background:#fff" text="${lfn:message('button.save')}" height="35" width="120" onclick="config_submitForm();" order="1" ></ui:button>
</div>
</center>
</div>
<script>
function config_submitForm(){

	Com_Submit(document.forms[0], 'saveConfig');
}
</script>
<script>
Com_IncludeFile("jquery.js");
</script>
<script>
$(document).ready(function (){
	init();
});
/****
 * 初始化
*/
function init(){
	
}
</script>
</html:form>
</template:replace>
</template:include>