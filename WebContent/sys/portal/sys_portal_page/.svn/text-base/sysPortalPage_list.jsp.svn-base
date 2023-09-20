<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.ui.xml.model.SysUiTheme"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysPortalPage" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  col="fdName" title="${ lfn:message('sys-portal:sysPortalPage.fdName') }" style="text-align:left;min-width:180px" escape="false">
			<div style="text-align: left;">
			<a target="_blank" href="<c:url value="/sys/portal/sys_portal_page/sysPortalPage.do" />?method=view&fdId=${sysPortalPage.fdId}"><c:out value="${sysPortalPage.fdName}" /></a>
			&nbsp;&nbsp;:&nbsp;&nbsp;<c:out value="${sysPortalPage.fdPortalNames}" />
			</div>
		</list:data-column>
		<list:data-column headerClass="width140" col="fdTheme" title="${ lfn:message('sys-portal:sysPortalPage.fdTheme') }">
		    <%
				Object syspage = pageContext.getAttribute("sysPortalPage");
				if(syspage!=null){
					String theme = (String)PropertyUtils.getProperty(syspage,"fdTheme");
					if(theme != null){
						SysUiTheme uit = SysUiPluginUtil.getThemeById(theme);
						if(uit != null)
							out.append(uit.getFdName());
					}
				}
			%>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdType" title="${ lfn:message('sys-portal:sysPortalPage.fdType') }" escape="false">
			<xform:select property="connState" value="${sysPortalPage.fdType}">
				<xform:enumsDataSource enumsType="sys_portal_page_type" />
			</xform:select> 
		</list:data-column>
		<!-- 《匿名》列 @author 吴进 by 20191107 -->
		<list:data-column col="fdAnonymous" title="${ lfn:message('sys-portal:sysportal.anonymous') }">
			<c:choose>
				<c:when test="${sysPortalPage.fdAnonymous==true }">
					${ lfn:message('sys-portal:sys_portal_anonymous') }
				</c:when>
				<c:otherwise>
					${ lfn:message('sys-portal:sys_portal_general') }
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/portal/sys_portal_page/sysPortalPage.do?method=edit&fdId=${sysPortalPage.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<c:if test="${sysPortalPage.fdAnonymous==false }">
							<a class="btn_txt" href="javascript:edit('${sysPortalPage.fdId}')">${lfn:message('button.edit')}</a>
						</c:if>
						<!-- 编辑匿名页面 -->
						<c:if test="${sysPortalPage.fdAnonymous==true }">
							<a class="btn_txt" href="javascript:editAnonymous('${sysPortalPage.fdId}')">${lfn:message('button.edit')}</a>
						</c:if>
					</kmss:auth>
					<kmss:auth requestURL="/sys/portal/sys_portal_page/sysPortalPage.do?method=delete&fdId=${sysPortalPage.fdId}" requestMethod="GET">
					    <!-- 删除-->
						<a class="btn_txt" href="javascript:deleteById('${sysPortalPage.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth> 
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>