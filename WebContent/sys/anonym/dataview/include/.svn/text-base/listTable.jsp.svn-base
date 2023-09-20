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
<list:data>
	<list:data-columns var="sysAnonymCommon" list="${queryPage.list}">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdAnonymId">
		</list:data-column>
		<list:data-column headerStyle="width:200px" property="fdName" title="名称">
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
		<list:data-column headerStyle="width:200px" property="fdSummary" title="简介">
		</list:data-column>
		<list:data-column property="docContent" title="内容">
		</list:data-column>
		<list:data-column property="fdIsAvailable" title="是否可用">
		</list:data-column>
		<list:data-column headerStyle="width:145px" property="docCreateTime" title="创建时间">
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
    <list:data-paging page="${queryPage}" />
</list:data>