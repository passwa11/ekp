<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.landray.kmss.sys.organization.eco.AuthOrgRange"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@page import="com.landray.kmss.sys.authentication.user.UserAuthInfo"%>
<%@page import="com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="java.util.*"%>
<%@page import="net.sf.json.*"%>
<%@page import="com.landray.kmss.sys.authentication.filter.HQLFragment"%>
<%@page import="com.landray.kmss.common.dao.HQLParameter"%>
<%@page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrgElement" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.sys.organization.eco.SysOrgShowRange" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>当前用户信息</title>
	<style>
		table { border-collapse: collapse;border-spacing: 0;}
		td {padding:2px 8px;border: 1px #d2d2d2 solid;word-break:break-all;}
	</style>
</head>
<body style="margin:20px; line-height:24px; font-size:12px;">
<%
	KMSSUser user = UserUtil.getKMSSUser(request);
	pageContext.setAttribute("user", user);
	UserAuthInfo auth = user.getUserAuthInfo();
	pageContext.setAttribute("auth", auth);
	if(!user.isAnonymous()){
		ISysOrgCoreService service = (ISysOrgCoreService)SpringBeanUtil.getBean("sysOrgCoreService");
		List<Object[]> results = service.getNamesByIds(auth.getAuthOrgIds());
		List<Object> names = new ArrayList<Object>();
		for(Object[] result:results){
			names.add(result[0]);
		}
		pageContext.setAttribute("authNames", names);
		SysOrgElement person = service.findByPrimaryKey(user.getUserId());
		List<SysOrgElement> posts = person.getFdPosts();
		if(posts != null) {
			String postNames = "";
			for(SysOrgElement post : posts) {
				if(postNames.length() > 0) {
					postNames += "，";
				}
				postNames +=  post.getFdName();
			}
			pageContext.setAttribute("postNames", postNames);
		}
	}
	List hqlInfos = new ArrayList();
	for(Object o: auth.getHqlInfoModelMap().entrySet()){
		Map.Entry entry = (Map.Entry) o;
		HQLFragment fragment = (HQLFragment) entry.getValue();
		Map item = new HashMap();
		item.put("key", entry.getKey());
		item.put("join", fragment.getJoinBlock());
		item.put("where", fragment.getWhereBlock());
		JSONArray array = new JSONArray();
		for(HQLParameter param : fragment.getParameterList()){
			JSONObject json = new JSONObject();
			json.put("name", param.getName());
			json.put("value", param.getValue());
			array.add(json);
		}
		item.put("param", array);
		hqlInfos.add(item);
	}
	pageContext.setAttribute("hqlInfos", hqlInfos);

	AuthOrgRange range = auth.getAuthOrgRange();
	if(range != null) {
		pageContext.setAttribute("utype", range.isExternal() ? "生态人员" : "内部人员");
		// 查看范围
		pageContext.setAttribute("authRanges", range.getAuthRanges());
		// 隐藏组织
		pageContext.setAttribute("hideRanges", range.getHideRanges());
		// 所属部门
		pageContext.setAttribute("myDepts", range.getMyDepts());
		// 生态管理员
		pageContext.setAttribute("adminRanges", range.getAdminRanges());
		// 其它可查看的内部组织
		pageContext.setAttribute("authOtherRanges", range.getAuthOtherRanges());
		// 有权限查看的生态
		pageContext.setAttribute("authOuterRanges", range.getAuthOuterRanges());
		// 是否仅查看自己
		pageContext.setAttribute("isSelf", range.isSelf());
		List<SysOrgShowRange> rootRanges = new ArrayList<>();
		if(range.getRootDeptIds() != null && range.getRootDeptIds().size() > 0) {
			for(SysOrgShowRange showRange : range.getAuthRanges()) {
				if(range.getRootDeptIds().contains(showRange.getFdId())) {
					rootRanges.add(showRange);
				}
			}
		}
		if(range.getRootPersonIds() != null && range.getRootPersonIds().size() > 0) {
			for(SysOrgShowRange showRange : range.getAuthRanges()) {
				if(range.getRootPersonIds().contains(showRange.getFdId())) {
					rootRanges.add(showRange);
				}
			}
		}
		pageContext.setAttribute("rootRanges", rootRanges);
	} else {
		pageContext.setAttribute("AuthOrgRange_isNull", "当前用户未能获取到组织查看范围权限！");
	}
	// 其它组织角色权限
	List<String> otherRoles = new ArrayList<>();
	if(UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN")) {
		otherRoles.add("【内部】机构管理员：可维护整个组织架构（机构、部门、岗位、个人，不包括群组和角色线等）");
	}
	if(UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
		otherRoles.add("【内部】使用所有组织：可在地址本（或后台管理）中选择到所有的人员组织，不受可见性和职级的限制");
	}
	if(UserUtil.checkRole("ROLE_SYSORG_ECO_ADMIN")) {
		otherRoles.add("【生态】管理员：可对生态组织所有数据进行维护");
	}
	if(UserUtil.checkRole("ROLE_SYSORG_ECO_ORG_ADMIN")) {
		otherRoles.add("【生态】类型管理员：可以管理维护所有组织类型和组织类型下的组织和人员信息，包括新建、编辑、禁用组织类型和组织/人员等操作");
	}
	if(UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_ADMIN")) {
		otherRoles.add("【生态】组织管理员：可以管理维护所有组织和组织下的人员信息，包括新建、编辑、禁用组织/人员等操作");
	}
	if(UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
		otherRoles.add("【生态】使用所有组织：可以在地址本（或后台管理）查看所有生态组织信息和组织下的人员信息");
	}
	pageContext.setAttribute("otherRoles", otherRoles);
%>
<b>基本信息</b><br>
当前用户：${user.userName}（${user.userId}）<br>
登录名：${user.username}<br>
语言：${user.locale}<br>
<br>
<b>权限信息<c:if test="${user.admin}">：超级管理员</c:if></b><br>
是否特权用户：<c:if test="${user.privilege}"><span style="color:red;">是</span></c:if><c:if test="${!user.privilege}"><span>否</span></c:if><br>
角色：${auth.authRoleAliases}<br>
身份：${authNames}（${auth.authOrgIds}）<br>
<c:if test="${not empty postNames}">
	所属岗位：${postNames}<br>
</c:if>

<%
	if (TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
		int type = TripartiteAdminUtil.getUserType(null);
		String typeName = null;
		switch(type) {
			case 2:{
				typeName = "系统管理员";
				break;
			}
			case 4:{
				typeName = "安全保密管理员";
				break;
			}
			case 8:{
				typeName = "安全审计管理员";
				break;
			}
			default:{
				typeName = "普通用户";
			}
		}
%>
三员管理用户类型：<%= typeName %>
<% } %>
<br>
<b>查询缓存</b>
<table>
	<tr style="text-align: center;">
		<td>Key</td>
		<td>Join</td>
		<td>Where</td>
		<td>Param</td>
	</tr>
	<c:forEach items="${hqlInfos}" var="hqlInfo">
		<tr>
			<td><c:out value="${hqlInfo.key}" /></td>
			<td><c:out value="${hqlInfo.join}" /></td>
			<td><c:out value="${hqlInfo.where}" /></td>
			<td><c:out value="${hqlInfo.param}" /></td>
		</tr>
	</c:forEach>
</table>

<br>
<c:if test="${!empty AuthOrgRange_isNull}">
	<span style="color: red;">${AuthOrgRange_isNull}</span>
	<br>
</c:if>
<b>组织地址本权限</b>
<br>
组织类型：${utype}<br>
<br>
<c:if test="${isSelf}">
<span style="color: red;">仅查看自己</span>
<br>
</c:if>
<c:if test="${!empty otherRoles}">
	其它组织角色权限：
	<br>
	<c:forEach items="${otherRoles}" var="otherRole" varStatus="status">
		<c:out value="${status.index + 1}" />、<c:out value="${otherRole}" /><br>
	</c:forEach>
</c:if>
<br>
<c:if test="${not empty authRanges}">
<br>
<b>组织可见范围</b>
<table width="80%">
	<tr style="text-align: center;">
		<td width="10%">序号</td>
		<td width="30%">组织名称</td>
		<td width="10%">组织类型</td>
		<td width="10%">外部组织</td>
		<td width="10%">仅自己</td>
	</tr>
	<c:forEach items="${authRanges}" var="range" varStatus="status">
		<tr style="text-align: center;">
			<td><c:out value="${status.index + 1}" /></td>
			<td><c:out value="${range.fdName}" /></td>
			<td <c:if test="${range.fdOrgType == '4' || range.fdOrgType == '8'}">style="background-color: yellow;"</c:if>>
				<c:out value="${range.fdOrgType == '1' ? '机构' : range.fdOrgType == '2' ? '部门' : range.fdOrgType == '4' ? '岗位' : range.fdOrgType == '8' ? '人员' : ''}" />
			</td>
			<td><span <c:if test="${range.external}">style="color:red;"</c:if>><c:out value="${range.external}" /></span></td>
			<td><span <c:if test="${range.self}">style="color:red;"</c:if>><c:out value="${range.self}" /></span></td>
		</tr>
	</c:forEach>
</table>
</c:if>
<c:if test="${not empty authOuterRanges}">
<br>
<b>未隐藏的生态组织</b>
<table width="80%">
	<tr style="text-align: center;">
		<td width="10%">序号</td>
		<td width="30%">组织名称</td>
		<td width="10%">组织类型</td>
	</tr>
	<c:forEach items="${authOuterRanges}" var="range" varStatus="status">
		<tr style="text-align: center;">
			<td><c:out value="${status.index + 1}" /></td>
			<td><c:out value="${range.fdName}" /></td>
			<td <c:if test="${range.fdOrgType == '4' || range.fdOrgType == '8'}">style="background-color: yellow;"</c:if>>
				<c:out value="${range.fdOrgType == '1' ? '机构' : range.fdOrgType == '2' ? '部门' : range.fdOrgType == '4' ? '岗位' : range.fdOrgType == '8' ? '人员' : ''}" />
			</td>
		</tr>
	</c:forEach>
</table>
</c:if>
<br>

<c:if test="${not empty authOtherRanges}">
	<b>其它可查看组织（内部）</b>
	<table width="80%">
		<tr style="text-align: center;">
			<td width="10%">序号</td>
			<td width="30%">组织名称</td>
			<td width="10%">组织类型</td>
		</tr>
		<c:forEach items="${authOtherRanges}" var="range" varStatus="status">
			<tr style="text-align: center;">
				<td><c:out value="${status.index + 1}" /></td>
				<td><c:out value="${range.fdName}" /></td>
				<td><c:out value="${range.fdOrgType == '1' ? '机构' : range.fdOrgType == '2' ? '部门' : ''}" /></td>
			</tr>
		</c:forEach>
	</table>
</c:if>

<c:if test="${not empty hideRanges}">
<b>隐藏组织</b>
<table width="80%">
	<tr style="text-align: center;">
		<td width="10%">序号</td>
		<td width="30%">组织名称</td>
		<td width="10%">组织类型</td>
		<td width="10%">外部组织</td>
	</tr>
	<c:forEach items="${hideRanges}" var="range" varStatus="status">
		<tr style="text-align: center;">
			<td><c:out value="${status.index + 1}" /></td>
			<td><c:out value="${range.fdName}" /></td>
			<td><c:out value="${range.fdOrgType == '1' ? '机构' : range.fdOrgType == '2' ? '部门' : ''}" /></td>
			<td><span <c:if test="${range.external}">style="color:red;"</c:if>><c:out value="${range.external}" /></span></td>
		</tr>
	</c:forEach>
</table>
</c:if>
<br>
<c:if test="${not empty myDepts}">
<br>
<b>我所属组织</b>
<table width="80%">
	<tr style="text-align: center;">
		<td width="10%">序号</td>
		<td width="30%">组织名称</td>
		<td width="10%">组织类型</td>
		<td width="10%">外部组织</td>
		<td width="10%">当前身份类型</td>
		<td width="10%">当前身份名称</td>
	</tr>
	<c:forEach items="${myDepts}" var="myDept" varStatus="status">
		<tr style="text-align: center;">
			<td><c:out value="${status.index + 1}" /></td>
			<td><c:out value="${myDept.fdName}" /></td>
			<td><c:out value="${myDept.fdOrgType == '1' ? '机构' : myDept.fdOrgType == '2' ? '部门' : ''}" /></td>
			<td><span <c:if test="${myDept.external}">style="color:red;"</c:if>><c:out value="${myDept.external}" /></span></td>
			<td><span <c:if test="${myDept.type == 4}">style="color:red;"</c:if>><c:out value="${myDept.type == 4 ? '岗位' : '人员'}" /></span></td>
			<td><c:out value="${myDept.name}" /></td>
		</tr>
	</c:forEach>
</table>
</c:if>
<br>
<c:if test="${not empty adminRanges}">
	<b>生态组织管理员</b>
	<table width="80%">
		<tr style="text-align: center;">
			<td width="10%">序号</td>
			<td width="30%">组织名称</td>
			<td width="10%">管理员类型</td>
		</tr>
		<c:forEach items="${adminRanges}" var="range" varStatus="status">
			<tr style="text-align: center;">
				<td><c:out value="${status.index + 1}" /></td>
				<td><c:out value="${range.fdName}" /></td>
				<td><c:out value="${range.adminType == '1' ? '组织类型管理员' : range.adminType == '2' ? '组织管理员' : range.adminType == '3' ? '组织负责人' : ''}" /></td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<br>
<c:if test="${not empty rootRanges}">
	<b>地址本根节点（人员不会直接显示，需要点击"组织架构"才会显示）</b>
	<table width="80%">
		<tr style="text-align: center;">
			<td width="10%">序号</td>
			<td width="30%">组织名称</td>
			<td width="10%">组织类型</td>
		</tr>
		<c:forEach items="${rootRanges}" var="range" varStatus="status">
			<tr style="text-align: center;">
				<td><c:out value="${status.index + 1}" /></td>
				<td><c:out value="${range.fdName}" /></td>
				<td <c:if test="${range.fdOrgType == '4' || range.fdOrgType == '8'}">style="background-color: yellow;"</c:if>>
					<c:out value="${range.fdOrgType == '1' ? '机构' : range.fdOrgType == '2' ? '部门' : range.fdOrgType == '4' ? '岗位' : range.fdOrgType == '8' ? '人员' : ''}" />
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
</body>
</html>