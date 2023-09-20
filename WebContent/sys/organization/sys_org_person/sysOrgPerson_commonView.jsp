<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page
	import="com.landray.kmss.sys.organization.forms.SysOrgPersonForm"%>
<tr>
    <!-- 姓名  -->
	<td width="15%" class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdName" /></td>
	<td width="35%"><pre><bean:write name="sysOrgPersonForm" property="fdName" /></pre></td>
	<!-- 昵称  -->
	<td width="15%" class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdNickName" /></td>
	<td width="85%" colspan="3"><pre><bean:write name="sysOrgPersonForm" property="fdNickName" /></pre></td>
</tr>
<tr>
    <!-- 编号  -->
    <td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdNo" /></td>
	<td width=85% colspan="3"><pre><bean:write name="sysOrgPersonForm" property="fdNo" /></pre></td>
</tr>
<tr>
    <!-- 所在部门  -->
	<c:if test="${'false' eq sysOrgPersonForm.fdIsAvailable}">
		<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.original.dept" /></td>
		<td width=35% style="display:table-cell; vertical-align:middle">
			<c:choose>
				<c:when test="${orgType == 'dept'}">
					<a href="${LUI_ContextPath}/sys/organization/sys_org_dept/sysOrgDept.do?method=view&fdId=${fdPreDeptId}"
					   target="_blank" style="color: #0099FF;text-decoration:underline;">
						<%=com.landray.kmss.sys.organization.util.SysOrgUtil.getLeavePersonParentNameByForm((com.landray.kmss.sys.organization.forms.SysOrgPersonForm)request.getAttribute("sysOrgPersonForm"))%>
					</a>
				</c:when>
				<c:when test="${orgType == 'org'}">
					<a href="${LUI_ContextPath}/sys/organization/sys_org_org/sysOrgOrg.do?method=view&fdId=${fdPreDeptId}"
					   target="_blank" style="color: #0099FF;text-decoration:underline;">
						<%=com.landray.kmss.sys.organization.util.SysOrgUtil.getLeavePersonParentNameByForm((com.landray.kmss.sys.organization.forms.SysOrgPersonForm)request.getAttribute("sysOrgPersonForm"))%>
					</a>
				</c:when>

			</c:choose>
		</td>
	</c:if>
	<c:if test="${'true' eq sysOrgPersonForm.fdIsAvailable}">
		<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdParent" /></td>
		<td width=35% style="display:table-cell; vertical-align:middle">
			<c:choose>
				<c:when test="${orgType == 'dept'}">
					<a href="${LUI_ContextPath}/sys/organization/sys_org_dept/sysOrgDept.do?method=view&fdId=${sysOrgPersonForm.fdParentId}"
					   target="_blank" style="color: #0099FF;text-decoration:underline;">
						<%=com.landray.kmss.sys.organization.util.SysOrgUtil.getFdParentsNameByForm((com.landray.kmss.sys.organization.forms.SysOrgPersonForm)request.getAttribute("sysOrgPersonForm"))%>

					</a>
				</c:when>
				<c:when test="${orgType == 'org'}">
					<a href="${LUI_ContextPath}/sys/organization/sys_org_org/sysOrgOrg.do?method=view&fdId=${sysOrgPersonForm.fdParentId}"
					   target="_blank" style="color: #0099FF;text-decoration:underline;">
						<%=com.landray.kmss.sys.organization.util.SysOrgUtil.getFdParentsNameByForm((com.landray.kmss.sys.organization.forms.SysOrgPersonForm)request.getAttribute("sysOrgPersonForm"))%>
					</a>
				</c:when>

			</c:choose>
		</td>
	</c:if>
	<!-- 邮件地址  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdEmail" /></td>
	<td width=35%><pre><bean:write name="sysOrgPersonForm" property="fdEmail" /></pre></td>
</tr>
<tr>
    <!-- 手机号码  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdMobileNo" /></td>
	<td width=35%><pre><bean:write name="sysOrgPersonForm" property="fdMobileNo" /></pre></td>
	<!-- 办公电话  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdWorkPhone" /></td>
	<td width=35%><pre><bean:write name="sysOrgPersonForm" property="fdWorkPhone" /></pre></td>
</tr>
<tr>
    <!-- 登录名  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdLoginName" /></td>
	<td width=35%><bean:write name="sysOrgPersonForm" property="fdLoginName" /></td>
	<!-- 默认语言  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdDefaultLang" /></td>
	<td width=35%>
	<%
		SysOrgPersonForm sysOrgPersonForm = (SysOrgPersonForm) request.getAttribute("sysOrgPersonForm");
		out.write(sysOrgPersonForm.getLangDisplayName(request,sysOrgPersonForm.getFdDefaultLang()));
	%>
	</td>
</tr>
<tr>
    <!-- 关键字  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdKeyword" /></td>
	<td width=35%><pre><bean:write name="sysOrgPersonForm" property="fdKeyword" /></pre></td>
	<!-- 排序号  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdOrder" /></td>
	<td width=35%><bean:write name="sysOrgPersonForm" property="fdOrder" /></td>
</tr>
<tr>
    <!-- 职务  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdStaffingLevel" /></td>
	<td width="35%"><bean:write name="sysOrgPersonForm" property="fdStaffingLevelName" /></td>
	<!-- 所属岗位  -->
	<c:if test="${'false' eq sysOrgPersonForm.fdIsAvailable}">
		<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.original.post" /></td>
		<td width=35%>
			<c:forEach items="${postList}" var="fdPost" varStatus="index">
				<c:choose>
					<c:when test="${'true' eq fdPost.fdIsExternal}">
						<a href="${LUI_ContextPath}/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=view&fdId=${fdPost.fdId}" target="_blank" style="color: #0099FF;text-decoration:underline;">${fdPost.fdName}</a>
					</c:when>
					<c:otherwise>
						<a href="${LUI_ContextPath}/sys/organization/sys_org_post/sysOrgPost.do?method=view&fdId=${fdPost.fdId}" target="_blank" style="color: #0099FF;text-decoration:underline;">${fdPost.fdName}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
				<%-- <pre><bean:write name="sysOrgPersonForm" property="fdPostNames" /></pre> --%>
		</td>
	</c:if>
	<c:if test="${'true' eq sysOrgPersonForm.fdIsAvailable}">
		<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdPosts" /></td>
		<td width=35%>
			<c:forEach items="${postList}" var="fdPost" varStatus="index">
				<c:choose>
					<c:when test="${'true' eq fdPost.fdIsExternal}">
						<a href="${LUI_ContextPath}/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=view&fdId=${fdPost.fdId}" target="_blank" style="color: #0099FF;text-decoration:underline;">${fdPost.fdName}</a>
					</c:when>
					<c:otherwise>
						<a href="${LUI_ContextPath}/sys/organization/sys_org_post/sysOrgPost.do?method=view&fdId=${fdPost.fdId}" target="_blank" style="color: #0099FF;text-decoration:underline;">${fdPost.fdName}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
				<%-- <pre><bean:write name="sysOrgPersonForm" property="fdPostNames" /></pre> --%>
		</td>
	</c:if>
</tr>
<tr>
    <!-- RTX帐号  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdRtxNo" /></td>
	<td width=35%><bean:write name="sysOrgPersonForm" property="fdRtxNo" /></td>
	<!-- 动态密码卡  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdCardNo" /></td>
	<td width=35%><bean:write name="sysOrgPersonForm" property="fdCardNo" /></td>
</tr>
<tr>
    <!-- 性别  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdSex" /></td>
	<td width="35%"><sunbor:enumsShow value="${sysOrgPersonForm.fdSex}" enumsType="sys_org_person_sex" /></td>
	<!-- 微信号  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdWechatNo" /></td>
	<td width=35%><bean:write name="sysOrgPersonForm" property="fdWechatNo" /></td>
</tr>
<tr>
    <!-- 短号  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdShortNo" /></td>
	<td width=35%><bean:write name="sysOrgPersonForm" property="fdShortNo" /></td>
	<!-- 双因子验证  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdDoubleValidation" /></td>
	<td width=35%>
		<%
			if("true".equals(com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getDoubleAuthEnable())) {
				// 后台总开关开启时，可以按状态显示
		%>
			<sunbor:enumsShow value="${sysOrgPersonForm.fdDoubleValidation}" enumsType="sys_org_double_validation" />
		<%
		} else {
				// 后台总开关关闭时，一律显示"禁用"
		%>
			<bean:message bundle="sys-organization" key="sysOrgPerson.fdDoubleValidation.disable" />
		<%
			}
		%>
	</td>
</tr>
<tr>
    <!-- 是否有效  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdIsAvailable" /></td>
	<td width="35%"><sunbor:enumsShow value="${sysOrgPersonForm.fdIsAvailable}" enumsType="sys_org_available_result" /></td>
	<!-- 是否业务相关  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdIsBusiness" /></td>
	<td width=35%><sunbor:enumsShow value="${sysOrgPersonForm.fdIsBusiness}" enumsType="common_yesno" />
	</td>
</tr>
<tr>
	<!-- 是否登录系统 -->
	<td width=15% class="td_normal_title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.fdCanLogin" />
	</td>
	<td width=85% colspan="3">
		<sunbor:enumsShow value="${sysOrgPersonForm.fdCanLogin}" enumsType="common_yesno" />
	</td>
</tr>
<c:if test="${personImportType=='inner'}">
	<c:import url="${personExtendFormUrl}" charEncoding="UTF-8" />
</c:if>
<tr>
    <!-- 备注  -->
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.fdMemo" /></td>
	<td colspan=3><pre><kmss:showText value="${sysOrgPersonForm.fdMemo}" /></pre></td>
</tr>
<c:if test="${'true' eq unlock && 'true' eq sysOrgPersonForm.fdIsAvailable}">
<tr>
	<td width=15% class="td_normal_title"><bean:message bundle="sys-organization" key="sysOrgPerson.unLock.prompt" /></td>
	<td colspan=3>
		<bean:message bundle="sys-organization" key="sysOrgPerson.unLock.message" arg0="${autoUnlockTime}"/>
	</td>
</tr>
</c:if>

<%-- 引入动态属性 --%>
<c:import url="/sys/property/custom_field/custom_fieldView.jsp" charEncoding="UTF-8" />
