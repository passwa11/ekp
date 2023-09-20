<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
		<link charset="utf-8" rel="stylesheet" href="<c:url value="/sys/ui/extend/theme/default/style/profile.css"/>">
	</template:replace>
	<template:replace name="content">

		<div class="lui-component">
			<div class="lui_profile_listview_container lui_profile_io_listview">
				<div class="lui_profile_listview_appMenu">
					<div class="lui_profile_listview_content gridContent">
						<div class="lui_profile_listview_grid">
							<div class="lui_profile_listview_grid_bd">
								<ul class="lui_profile_listview_card_page">
									<li class="lui_profile_block_grid_item itemStyle_1" onclick="_import();">
										<div class="appMenu_item_block">
											<div class="appMenu_iconBar">
												<i class="lui_profile_listview_l_icon lui_icon_l_profile_orgio_dept_import"></i>
											</div>
											<p class="appMenu_brief">
												<bean:message bundle="sys-profile" key="sys.profile.orgImport.title.desc" />
											</p>
											<div class="appMenu_title">
												<i class="trig"></i><span class="textEllipsis"><bean:message bundle="sys-profile" key="sys.profile.orgImport.title" /></span>
											</div>
										</div>
										<div class="appMenu_mask" style="display: none;">
											<div class="appMenu_btnGroup">
											</div>
										</div>
									</li>
									<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN || TripartiteAdminUtil.isSecurity()) { %>
									<li class="lui_profile_block_grid_item itemStyle_3" onclick="_export();">
										<div class="appMenu_item_block">
											<div class="appMenu_iconBar">
												<i class="lui_profile_listview_l_icon lui_icon_l_profile_orgio_dept_export"></i>
											</div>
											<p class="appMenu_brief">
												<bean:message bundle="sys-profile" key="sys.profile.orgExport.title.desc" />
											</p>
											<div class="appMenu_title">
												<i class="trig"></i><span class="textEllipsis"><bean:message bundle="sys-profile" key="sys.profile.orgExport.title" /></span>
											</div>
										</div>
										<div class="appMenu_mask" style="display: none;">
											<div class="appMenu_btnGroup">
											</div>
										</div>
									</li>
									<% } %>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<form id="exportForm" action="${LUI_ContextPath}/sys/profile/org/orgExport.do?method=exportData" method="post">
			<input type="hidden" name="type">
			<input type="hidden" name="exportIds">
			<input type="hidden" name="exportNames">
			<input type="hidden" name="password">
		</form>

		<script type="text/javascript">
			function _export() {
				seajs.use(['lui/dialog'], function(dialog) {
					dialog.iframe("/sys/profile/org/io/export_data.jsp", "${lfn:message('sys-profile:sys.profile.orgExport.title')}", 
						function(data) {
							if(data) {
								$.each(data, function(i ,n){
									$("#exportForm input[name='" + n.name + "']").val(n.value);
								});
								$("#exportForm").submit();
							}
						}, {
						width : 600,
						height : 300
					});
				});
			}

			function _import() {
				window.open("<c:url value="/sys/profile/org/io/import_data.jsp"/>", "_self");
			}
		</script>
	</template:replace>
</template:include>
