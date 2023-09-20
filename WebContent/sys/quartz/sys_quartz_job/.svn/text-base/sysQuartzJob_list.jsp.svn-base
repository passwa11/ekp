<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page import="com.landray.kmss.sys.log.util.ParseOperContentUtil" %>	
<%@ page import="org.apache.commons.beanutils.PropertyUtils" %>	
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysQuartzJob" list="${queryPage.list }">
		<%
			pageContext.setAttribute("list_error", false);
		%>
		<list:data-column property="fdId" />
		<!-- 任务名称 -->
		<list:data-column headerClass="width300"  col="fdSubject" title="${ lfn:message('sys-quartz:sysQuartzJob.fdSubject') }" style="text-align: left;" escape="false">
			<c:if test="${sysQuartzJob.fdIsSysJob}">
				<% try { %>
					<kmss:message key="${sysQuartzJob.fdSubject}"/>
				<% 
					} catch (com.landray.kmss.common.exception.KmssRuntimeException e) {
						pageContext.setAttribute("list_error", true);
					%>
						<span style="color: red;">${ lfn:message('sys-quartz:sysQuartzJob.list.error') }</span>
					<%
					}
				%>
			</c:if>
			<c:if test="${not sysQuartzJob.fdIsSysJob}">
				<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
				<bean:write name="sysQuartzJob" property="fdSubject"/> 
				<% } else {
					String fdSubject = (String)PropertyUtils.getProperty(pageContext.getAttribute("sysQuartzJob"), "fdSubject");
					out.append(ParseOperContentUtil.hideDisplayName(fdSubject));
				}%>
			</c:if>
		</list:data-column>
		<!-- 触发时间 -->
		<list:data-column headerClass="width100" col="fdCronExpression" title="${ lfn:message('sys-quartz:sysQuartzJob.fdCronExpression') }">
			<c:if test="${!list_error}">
				<c:import url="/sys/quartz/sys_quartz_job/sysQuartzJob_showCronExpression.jsp" charEncoding="UTF-8">
					<c:param name="value" value="${sysQuartzJob.fdCronExpression}" />
				</c:import>
			</c:if>
		</list:data-column>
		<!-- 下次运行 -->
		<list:data-column headerClass="width120" col="nextTime" title="${ lfn:message('sys-quartz:sysQuartzJob.nextTime') }">
			<c:if test="${sysQuartzJob.fdEnabled && !list_error}">
				<kmss:showDate value="${sysQuartzJob.fdRunTime}" type="datetime"/>
			</c:if>
		</list:data-column>
		<!-- 相关链接 -->
		<list:data-column headerClass="width200" col="fdLink" title="${ lfn:message('sys-quartz:sysQuartzJob.fdLink') }" escape="false">
			<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
			<c:if test="${sysQuartzJob.fdLink != null && sysQuartzJob.fdLink != '' && !list_error}">
				<a href="<c:url value="${sysQuartzJob.fdLink}" />" style="text-decoration:underline;" target="_blank">
					<bean:message bundle="sys-quartz" key="sysQuartzJob.fdLink"/>
				</a>
			</c:if>
			<% } %>
		</list:data-column>
		<!-- 运行类型 -->
		<list:data-column headerClass="width50" col="fdRunType" title="${ lfn:message('sys-quartz:sysQuartzJob.fdRunType') }">
			<c:if test="${!list_error}">
				<sunbor:enumsShow value="${sysQuartzJob.fdRunType}" enumsType="sysQuartzJob_fdRunType" />
				<c:if test="${not empty sysQuartzJob.fdRunServer}">
					(${sysQuartzJob.fdRunServer})
				</c:if>
			</c:if>
		</list:data-column>
		<!-- 是否启用 -->
		<list:data-column headerClass="width50" col="fdEnabled" title="${ lfn:message('sys-quartz:sysQuartzJob.fdEnabled') }">
			<c:if test="${!list_error}">
				<sunbor:enumsShow value="${sysQuartzJob.fdEnabled}" enumsType="common_yesno" />
			</c:if>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<c:if test="${!list_error}">
			<!--操作按钮 开始-->
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=edit&fdId=${sysQuartzJob.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysQuartzJob.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=edit&fdId=${sysQuartzJob.fdId}" requestMethod="GET">
						<c:choose>
							<c:when test="${sysQuartzJob.fdEnabled}">
								<!-- 禁用 -->
								<a class="btn_txt" href="javascript:changeJob(false, '${sysQuartzJob.fdId}')">${lfn:message('sys-quartz:sysQuartzJob.button.disable')}</a>
							</c:when>
							<c:otherwise>
								<!-- 启用 -->
								<a class="btn_txt" href="javascript:changeJob(true, '${sysQuartzJob.fdId}')">${lfn:message('sys-quartz:sysQuartzJob.button.enable')}</a>
							</c:otherwise>
						</c:choose>
					</kmss:auth>
				</div>
			<!--操作按钮 结束-->
			</c:if>
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>