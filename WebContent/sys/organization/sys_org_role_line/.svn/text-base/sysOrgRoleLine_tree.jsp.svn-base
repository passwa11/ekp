<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ KMSS_Parameter_ContextPath}sys/organization/resource/css/roleline.css">
		<script type="text/javascript">
			Com_IncludeFile("dialog.js|optbar.js|list.js|jquery.js");
			Com_IncludeFile("sysOrgRoleLine_tree.script.js", "${LUI_ContextPath}/sys/organization/resource/js/", 'js', true);
		</script>
		<script type="text/javascript">
		var LKSTree;
		Tree_IncludeCSSFile();
		//Com_Parameter.XMLDebug = true;
		var confId = "${fdConfId}";
		
		var $data = {
				'fdConfName': '<c:out value="${fdConfName}" />',
				'orgId': '<c:out value="${param.orgId}"/>',
				'quickAdd.noexist': '<bean:message bundle="sys-organization" key="sysOrgRoleLine.msg.dept.noexist.role"/>',
				'delete.child.confirm': '<bean:message bundle="sys-organization" key="sysOrgRoleLine.msg.delete.child.confirm"/>',
				'delete.confirm': '<bean:message bundle="sys-organization" key="sysOrgRoleLine.msg.delete.confirm"/>',
				'pleaseSelect': '<bean:message bundle="sys-organization" key="sysOrgRoleLine.msg.pleaseSelect"/>',
				'rootForbit': '<bean:message bundle="sys-organization" key="sysOrgRoleLine.msg.rootForbit"/>',
				'noData': '<bean:message bundle="sys-ui" key="address.noData" />',
				'sysOrgRole_simulator_url': '<c:url value="/sys/organization/sys_org_role/sysOrgRole_simulator.jsp"/>',
				'checkRepeatRoleUrl': '<c:url value="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=checkRepeatRole&fdId=${fdConfId}"/>'
		}
		</script>
	</template:replace>
	<template:replace name="toolbar">
		<div class="lui_tree_operation">
  			<ui:toolbar id="toolbar" style="float:right;margin-right:15px" count="9">
			   <ui:button text="${lfn:message('sys-organization:sysOrgRoleLine.opt.quickCreate')}" onclick="opt_quickAdd();" order="1"></ui:button>
			   <ui:button text="${lfn:message('sys-organization:sysOrgRoleLine.opt.create')}" onclick="opt_add();" order="2"></ui:button>
			   <ui:button text="${lfn:message('sys-organization:sysOrgRoleLine.opt.move')}" onclick="opt_move();" order="3"></ui:button>
			   <ui:button text="${lfn:message('button.edit')}" onclick="opt_edit();" order="4"></ui:button>
			   <ui:button text="${lfn:message('button.delete')}" onclick="opt_delete();" order="5"></ui:button>
			   <ui:button text="${lfn:message('sys-organization:sysOrgRoleLine.opt.check')}" onclick="opt_check();" order="6"></ui:button>
			   <ui:button text="${lfn:message('sys-organization:sysOrgRoleConf.simulator')}" onclick="opt_simulator();" order="7"></ui:button>
		   </ui:toolbar>
		</div>
	</template:replace>
	<template:replace name="content">
		<!-- 角色线 head Starts -->
		<div class="lui_role_setting_head">
			<!-- 展开收起按钮 Starts -->
			<div class="lui_role_btnWrap">
				<ui:button text="${lfn:message('sys-organization:sysOrgRoleLine.allExpand')}" id="expand_all" onclick="expand_all();"></ui:button>
				<ui:button text="${lfn:message('sys-organization:sysOrgRoleLine.allReduce')}" id="reduce_all" onclick="reduce_all();" style="display:none;"></ui:button>
			</div>
			<!-- 展开收起按钮 Ends -->
			<!-- 快速定位按钮 Starts -->
			<div class="quciklocateDiv">
				<input class="quciklocateInput" type="text" placeholder="${lfn:message('sys-organization:sysOrg.address.search.inputkeyword')}">
				<button class="quciklocateBtn"></button>
			</div>
			<!-- 快速定位按钮 Ends -->
		</div>
		<!-- 角色线 head Ends -->
		<div class="quciklocateDialog"></div>
		<div id=treeDiv class="treediv"></div>
		<script>generateTree();</script>
		<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
		<c:if test="${frameShowTop=='yes' }">
		<ui:top id="top"></ui:top>
			<kmss:ifModuleExist path="/sys/help">
				<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
			</kmss:ifModuleExist>
		</c:if>
	</template:replace>
</template:include>
