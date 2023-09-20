<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgDept"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrgDept" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 排序号 -->
		<list:data-column headerClass="width80"  col="fdOrder" title="${ lfn:message('sys-organization:sysOrgDept.fdOrder') }">
			${sysOrgDept.fdOrder}
		</list:data-column>
		<list:data-column headerClass="width200" col="fdName" title="${ lfn:message('sys-organization:sysOrgElementExternal.fdName') }" escape="false">
		    ${sysOrgDept.fdName}
		</list:data-column>
		<!-- 编号 -->
		<list:data-column headerClass="width100" col="fdNo" title="${ lfn:message('sys-organization:sysOrgDept.fdNo') }">
			${sysOrgDept.fdNo}
		</list:data-column>
		<!-- 父组织名称 -->
		<list:data-column headerClass="width100" col="parentsName">
			${sysOrgDept.fdParentsName}
		</list:data-column>
		<!-- 负责人 -->
		<list:data-column col="adminsName">
			<%
				SysOrgDept sysOrgDept = (SysOrgDept)pageContext.getAttribute("sysOrgDept");
				if (sysOrgDept != null) {
					List<SysOrgElement> list = sysOrgDept.getAuthElementAdmins();
					if(!list.isEmpty()) {
						String adminStr = "";
						for(int i = 0; i < list.size(); i++) {
							SysOrgElement sysOrgElement = list.get(i);
							adminStr += sysOrgElement.getFdName();
							if(i < list.size() - 1)
								adminStr += ";";
						}
						out.print(adminStr);
					}
				}
			%>
		</list:data-column>
		
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>