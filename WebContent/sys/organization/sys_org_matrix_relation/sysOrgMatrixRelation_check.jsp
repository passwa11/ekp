<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
	<template:replace name="title">
		<bean:message bundle="sys-organization" key="sysOrgMatrix.field.check"/>
	</template:replace>
	
	<template:replace name="head">
		<style>
			.lui_matrix_relation_content {
				width: 95%;
				margin: 20px auto;
				padding: 20px;
			}
		</style>
	</template:replace>
	
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
    	 	<ui:button text="${lfn:message('button.close') }" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	
	<template:replace name="content">
		<div class="lui_matrix_relation_content">
			<p class="txttitle">
				<bean:message bundle="sys-organization" key="sysOrgMatrix.field.check"/>: <c:out value="${sysOrgMatrix.fdName}"/>
			</p>
			
			<div style="padding-top: 20px;">
				<table id="relation_table" class="tb_normal" width=100%>
					<thead>
						<tr>
							<th>${lfn:message('sys-organization:sysOrgMatrixRelation.fdFieldName') }</th>
							<th>${lfn:message('sys-organization:sysOrgMatrixRelation.fdFieldName.alias') }</th>
							<th>${lfn:message('sys-organization:sysOrgMatrixRelation.fdType') }</th>
							<th>${lfn:message('sys-organization:sysOrgMatrix.field.count') }</th>
							<th>${lfn:message('title.option') }</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<div style="padding-top: 20px;">
				<p><bean:message bundle="sys-organization" key="sysOrgMatrix.field.check.node"/></p>
				<p style="padding-left: 10px;"><bean:message bundle="sys-organization" key="sysOrgMatrix.field.check.node1"/></p>
				<p style="padding-left: 10px;"><bean:message bundle="sys-organization" key="sysOrgMatrix.field.check.node2"/></p>
				<p style="padding-left: 10px;"><bean:message bundle="sys-organization" key="sysOrgMatrix.field.check.node3"/></p>
				<p style="padding-left: 10px;"><bean:message bundle="sys-organization" key="sysOrgMatrix.field.check.node4"/></p>
			</div>
		</div>

		<script type="text/javascript">
			seajs.use(['lui/dialog', 'lui/jquery'], function(dialog, $) {
				// 执行检测
				window.doCheck = function() {
					window.loading = dialog.loading();
					var relation_table = $("#relation_table tbody");
					relation_table.empty();
					$.ajax({
						'url': '<c:url value="/sys/organization/sys_org_matrix_relation/sysOrgMatrixRelation.do?method=doCheck"/>',
						'data': {'matrixId': '${sysOrgMatrix.fdId}'},
						'type': 'POST',
						'dataType': 'json',
						'success': function(res) {
							window.loading.hide();
							if(res.status) {
								// 排序处理
								res.data.sort(function(a, b) {
									var i1 = 3, i2 = 3;
									if(a.isResult == true) {
										i1 = 2;
									} else if(a.isResult == false) {
										i1 = 1;
									}
									if(b.isResult == true) {
										i2 = 2;
									} else if(b.isResult == false) {
										i2 = 1;
									}
									var i = i1 - i2;
									if(i == 0) {
										var o1 = a.order || 99999,
											o2 = b.order || 99999;
										return o1 - o2;
									}
									return i;
								});
								
								for(var i=0; i<res.data.length; i++) {
									var obj = res.data[i];
									if(obj.ignore) {
										continue;
									}
									var tr = $("<tr/>");
									if(obj.isDel) {
										// 可删除
										tr.append("<td>-</td>");
										tr.append("<td>" + obj.name + "</td>");
										tr.append("<td>-</td>");
										tr.append("<td>" + obj.count + "</td>");
										tr.append("<td><a href='javascript:deleteField(\"" + obj.name + "\", " + obj.count + ");'>${lfn:message('button.delete') }</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:depthCheck(\"" + obj.name + "\");'>${lfn:message('sys-organization:sysOrgMatrix.field.depthCheck') }</a></td>");
									} else {
										// 不可删除
										tr.append("<td>" + obj.label + "</td>");
										tr.append("<td>" + obj.name + "</td>");
										tr.append("<td>" + (obj.isResult ? "<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.result"/>" : "<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.conditional"/>") + "</td>");
										tr.append("<td>" + obj.count + "</td>");
										tr.append("<td>-</td>");
									}
									
									relation_table.append(tr);
								}
							} else {
								dialog.alert(res.message);
							}
						},
						'complete': function() {
							window.loading.hide();
						}
					});
				}
				
				window.deleteField = function(field, count) {
					if(count > 0) {
						var msg = '<bean:message bundle="sys-organization" key="sysOrgMatrix.field.delete.error"/>';
						msg = msg.replace("{0}", count).replace("{1}", "${sysOrgMatrix.fdSubTable}").replace("{2}", field);
						dialog.alert(msg);
					} else {
						dialog.confirm('<bean:message bundle="sys-organization" key="sysOrgMatrix.field.delete.confirm"/>', function(value) {
							if(value == true) {
								// 删除字段
								window.loading = dialog.loading();
								$.ajax({
									'url': '<c:url value="/sys/organization/sys_org_matrix_relation/sysOrgMatrixRelation.do?method=deleteField"/>',
									'data': {'matrixId': '${sysOrgMatrix.fdId}', 'field': field},
									'type': 'POST',
									'dataType': 'json',
									'success': function(res) {
										window.loading.hide();
										doCheck();
									},
									'complete': function() {
										window.loading.hide();
									}
								});
							}
						});
					}
				}
				
				// 深度检测数量
				window.depthCheck = function(field) {
					window.loading = dialog.loading();
					$.ajax({
						'url': '<c:url value="/sys/organization/sys_org_matrix_relation/sysOrgMatrixRelation.do?method=depthCheck"/>',
						'data': {'matrixId': '${sysOrgMatrix.fdId}', 'field': field},
						'type': 'POST',
						'dataType': 'json',
						'success': function(res) {
							window.loading.hide();
							if(res.status) {
								var msg = '<bean:message bundle="sys-organization" key="sysOrgMatrix.field.depthCheck.desc"/>';
								msg = msg.replace("{0}", res.count);
								if(res.count > 0) {
									msg += "<br>" + '<bean:message bundle="sys-organization" key="sysOrgMatrix.field.depthCheck.warn"/>';
								}
								dialog.alert(msg);
							} else {
								dialog.alert(res.message);
							}
						},
						'complete': function() {
							window.loading.hide();
						}
					});
				}
				
				$(function() {
					doCheck();
				});
			});
		</script>
	</template:replace>
</template:include>
