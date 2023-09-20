<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.alibaba.fastjson.JSONArray"%>
<%@ page import="com.alibaba.fastjson.JSONObject"%>
<%@ page import="com.landray.kmss.sys.anonym.context.AnonymContext"%>
<%@ page import="com.landray.kmss.sys.anonym.constants.SysAnonymConstant"%>
<%@ page import="com.landray.kmss.common.forms.IExtendForm"%>
<%@ page import="com.landray.kmss.sys.anonym.forms.SysAnonymCommonForm"%>
<%@ page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@ page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ page import="com.landray.kmss.sys.ui.xml.model.*"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>

<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", -1);

AnonymContext anonymContext = (AnonymContext)request.getAttribute("anonymContext");

List<IExtendForm> forms = anonymContext.getList();


JSONArray json = new JSONArray();

if(SysAnonymConstant.SYS_UI_CLASSIC.equals(anonymContext.getFormatKey())){
	
	for(IExtendForm form:forms){
		SysAnonymCommonForm sysAnonymCommon = (SysAnonymCommonForm)form;
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("text", sysAnonymCommon.getFdName());
		
		jsonObj.put("created", sysAnonymCommon.getDocCreateTime());
		
		jsonObj.put("href", sysAnonymCommon.getDocField6());
		
		json.add(jsonObj);
	}
	
	out.print(json.toJSONString());
	
}else if(SysAnonymConstant.SYS_UI_CLASSIC_RICH.equals(anonymContext.getFormatKey())){
	
	for(IExtendForm form:forms){
		SysAnonymCommonForm sysAnonymCommon = (SysAnonymCommonForm)form;
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("text", sysAnonymCommon.getFdName());
		
		jsonObj.put("personName",sysAnonymCommon.getDocAuthorName());
		
		jsonObj.put("created", sysAnonymCommon.getDocCreateTime());
		
		jsonObj.put("catename", sysAnonymCommon.getAnonymCateName());
		
		jsonObj.put("href", sysAnonymCommon.getDocField6());
		
		jsonObj.put("catehref", sysAnonymCommon.getDocField7());
		
		json.add(jsonObj);
	}
	
	out.print(json.toJSONString());
	
}else if(SysAnonymConstant.SYS_UI_IMAGE.equals(anonymContext.getFormatKey())){
	
	for(IExtendForm form:forms){
		SysAnonymCommonForm sysAnonymCommon = (SysAnonymCommonForm)form;
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("text",  sysAnonymCommon.getFdName());
		
		jsonObj.put("href",  sysAnonymCommon.getDocField6());
		
		jsonObj.put("image", sysAnonymCommon.getDocField7());
		
		json.add(jsonObj);
	}
	
	out.print(json.toJSONString());
}else if(SysAnonymConstant.SYS_UI_IMAGE_RICH.equals(anonymContext.getFormatKey())){
	
	for(IExtendForm form:forms){
		SysAnonymCommonForm sysAnonymCommon = (SysAnonymCommonForm)form;
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("fdId",  sysAnonymCommon.getFdId());
		
		jsonObj.put("catename",  sysAnonymCommon.getAnonymCateName());
		
		jsonObj.put("text",  sysAnonymCommon.getFdName());
		
		jsonObj.put("created",  sysAnonymCommon.getDocCreateTime());
		
		jsonObj.put("personName",  sysAnonymCommon.getDocAuthorName());
		
		jsonObj.put("href",  sysAnonymCommon.getDocField6());
		
		jsonObj.put("image", sysAnonymCommon.getDocField7());
		
		
		json.add(jsonObj);
	}
	
	out.print(json.toJSONString());
}else if(SysAnonymConstant.SYS_UI_LISTTABLE.equals(anonymContext.getFormatKey())){
	
%>
<list:data>
	<list:data-columns var="sysAnonymData" list="${anonymContext.list}">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdAnonymId">
		</list:data-column>
		<list:data-column property="fdName" title="名称">
		</list:data-column>
		<list:data-column property="docAuthorName" title="作者">
		</list:data-column>
		<list:data-column property="docAlterorName" title="修改者">
		</list:data-column>
		<list:data-column property="docCreatorName" title="创建者">
		</list:data-column>
		<list:data-column property="fdDeptName" title="部门名称">
		</list:data-column>
		<list:data-column property="fdNumber" title="编号">
		</list:data-column>
		<list:data-column property="fdOrder" title="排序">
		</list:data-column>
		<list:data-column property="fdSummary" title="简介">
		</list:data-column>
		<list:data-column property="docContent" title="内容">
		</list:data-column>
		<list:data-column property="fdIsAvailable" title="是否可用">
		</list:data-column>
		<list:data-column property="docCreateTime" title="创建时间">
		</list:data-column>
		<list:data-column property="docAlterTime" title="修改时间">
		</list:data-column>
		<list:data-column property="docPublishTime" title="发布时间">
		</list:data-column>
		<list:data-column property="docField1" title="匿名字段1">
		</list:data-column>
		<list:data-column property="docField2" title="匿名字段2">
		</list:data-column>
		<list:data-column property="docField3" title="匿名字段3">
		</list:data-column>
		<list:data-column property="docField4" title="匿名字段4">
		</list:data-column>
		<list:data-column property="docField5" title="匿名字段5">
		</list:data-column>
	</list:data-columns>
	<%-- 分页信息生成 --%>
    <list:data-paging page="${anonymContext.page}" />
</list:data>
<%

}else if(SysAnonymConstant.SYS_UI_TEXTMENU.equals(anonymContext.getFormatKey())){
	
	for(IExtendForm form:forms){
		SysAnonymCommonForm sysAnonymCommon = (SysAnonymCommonForm)form;
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("text",  sysAnonymCommon.getFdName());
		
		jsonObj.put("href",  sysAnonymCommon.getDocField6());
		
		json.add(jsonObj);
	}
	out.print(json.toJSONString());
}




%>



