<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page import="com.landray.kmss.sys.organization.util.SysOrgEcoUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrgPerson" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 排序号 -->
		<list:data-column headerClass="width80"  property="fdOrder" title="${ lfn:message('sys-organization:sysOrgPerson.fdOrder') }">
		</list:data-column>
		<!-- 所在部门 -->
		<list:data-column headerClass="width200" col="fdParent" title="${ lfn:message('sys-organization:sysOrgPerson.fdParent') }" escape="false">
			<pre>${sysOrgPerson.fdParent.fdName}</pre>
		</list:data-column>
		<!-- 原所在部门 -->
		<list:data-column headerClass="width200" col="fdPreDept" title="${ lfn:message('sys-organization:sysOrgPerson.original.dept') }" escape="false">
			<pre>${sysOrgPerson.fdParent.fdName}</pre>
		</list:data-column>
		<!-- 编号 -->
		<list:data-column headerClass="width100" property="fdNo" title="${ lfn:message('sys-organization:sysOrgPerson.fdNo') }">
		</list:data-column>
		<!-- 姓名 -->
		<list:data-column headerClass="width200" col="fdName" title="${ lfn:message('sys-organization:sysOrgPerson.fdName') }" escape="false">
		    <pre>${fn:escapeXml(sysOrgPerson.fdName)}</pre>
		</list:data-column>
		<!-- 登录名 -->
		<list:data-column headerClass="width200" col="fdLoginName" title="${ lfn:message('sys-organization:sysOrgPerson.fdLoginName') }" escape="false">
		    <pre>${lfn:escapeHtml(sysOrgPerson.fdLoginName)}</pre>
		</list:data-column>
		<!-- 是否登录系统 -->
		<list:data-column headerClass="width100" col="fdCanLogin" title="${ lfn:message('sys-organization:sysOrgPerson.fdCanLogin') }">
			<sunbor:enumsShow value="${sysOrgPerson.fdCanLogin}" enumsType="common_yesno" />
		</list:data-column>
		<!-- 所属岗位 -->
		<list:data-column headerClass="width200" col="fdPosts" title="${ lfn:message('sys-organization:sysOrgPerson.fdPosts') }" escape="false">
			<pre><c:forEach items="${sysOrgPerson.fdPosts}" var="post" varStatus="idx"><c:if test="${ idx.index > 0 }">,</c:if>${post.fdName}</c:forEach></pre>
		</list:data-column>
		<!-- 原所属岗位 -->
		<list:data-column headerClass="width200" col="fdPrePosts" title="${ lfn:message('sys-organization:sysOrgPerson.original.post') }" escape="false">
			<pre><c:forEach items="${sysOrgPerson.fdPosts}" var="post" varStatus="idx"><c:if test="${ idx.index > 0 }">,</c:if>${post.fdName}</c:forEach></pre>
		</list:data-column>
		<!-- 邮件地址 -->
		<list:data-column headerClass="width100" col="fdEmail" title="${ lfn:message('sys-organization:sysOrgPerson.fdEmail') }" escape="false">
		    <pre>${sysOrgPerson.fdEmail}</pre>
		</list:data-column>
		<!-- 电话号码 -->
		<list:data-column headerClass="width100" col="fdMobileNo" title="${ lfn:message('sys-organization:sysOrgPerson.fdMobileNo') }" escape="false">
		    <pre>${sysOrgPerson.fdMobileNo}</pre>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:choose>
						<c:when test="${ 'true' eq actiList }">
							<% if(com.landray.kmss.sys.authorization.util.TripartiteAdminUtil.isSecurity()) { %>
							<!-- 激活 -->
							<a class="btn_txt" href="javascript:doActivate('${sysOrgPerson.fdId}')" title="${ lfn:message('sys-organization:org.personnel.activation.one') }">${ lfn:message('sys-organization:org.personnel.activation.one') }</a>
							<% } %>
						</c:when>
						<c:otherwise>
							<%-- 匿名和everyone用户不显示任何操作 --%>
							<% if(!com.landray.kmss.sys.organization.util.SysOrgUtil.isAnonymousOrEveryOne((com.landray.kmss.sys.organization.model.SysOrgPerson)pageContext.getAttribute("sysOrgPerson"))) { %>
							<kmss:auth requestURL="/sys/organization/sys_org_person/sysOrgPerson.do?method=edit&fdId=${sysOrgPerson.fdId}" requestMethod="GET">
								<!-- 编辑 -->
								<a class="btn_txt" href="javascript:edit('${sysOrgPerson.fdId}')" title="${lfn:message('button.edit')}">${lfn:message('button.edit')}</a>
							</kmss:auth>
							<c:if test="${param.available != '0'}">
							<kmss:auth requestURL="/sys/organization/sys_org_person/sysOrgPerson.do?method=invalidatedAll" requestMethod="POST">
								<!-- 禁用 -->
								<a class="btn_txt" href="javascript:invalidated('${sysOrgPerson.fdId}')" title="${ lfn:message('sys-organization:sys.org.available.false') }">${ lfn:message('sys-organization:sys.org.available.false') }</a>
							</kmss:auth>
							</c:if>
							<% if (!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
							<!-- 日志 -->
							<a class="btn_txt" href="javascript:viewLog('${sysOrgPerson.fdId}', '${sysOrgPerson.fdName}')" title="${ lfn:message('sys-organization:sys.org.operations.log') }">${ lfn:message('sys-organization:sys.org.operations.log') }</a>
							<% } %>
							<% if (SysOrgEcoUtil.IS_ENABLED_ECO) { %>
							<kmss:auth requestURL="/sys/organization/sys_org_person/sysOrgPerson.do?method=edit&fdId=${sysOrgPerson.fdId}" requestMethod="GET">
								<!-- 转外部人员 -->
								<a class="btn_txt" title="${ lfn:message('sys-organization:sys.org.operations.toOutPerson') }" href="javascript:transformOut('${sysOrgPerson.fdId}', '${sysOrgPerson.fdName}')">${ lfn:message('sys-organization:sys.org.operations.toOutPerson') }</a>
							</kmss:auth>
							<% } %>
							<% } %>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
		<!-- 锁定人员解锁 -->
		<list:data-column headerClass="width100" col="operations_locked" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/organization/sys_org_person/chgPersonInfo.do?method=chgPwd&fdId=${sysOrgPerson.fdId}" requestMethod="GET">
						<!-- 解锁 -->
						<a class="btn_txt" href="javascript:unLock('${sysOrgPerson.fdId}')">${lfn:message('sys-organization:sysOrgPerson.unLock')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>