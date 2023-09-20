<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="lui_maxtrix_relation">
	<div class="lui_maxtrix_relation_head">
		<label>
			<input type="checkbox" name="maxtrix_relation_cb"> ${lfn:message('sys-organization:sysOrgMatrix.relation.text')}
		</label>
	</div>
	
	<div id="lui_maxtrix_relation_body" class="lui_maxtrix_relation_body">
		<!-- 内容列表 -->
		<list:listview channel="maxtrix_relation">
			<ui:source type="AjaxJson">
				{'url':'/sys/organization/sys_org_matrix/sysOrgMatrixTemplate.do?method=list&matrixId=${ HtmlParam.matrixId}&type=${ HtmlParam.type}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" channel="maxtrix_relation">
				<list:col-serial/>
				<list:col-auto/>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging channel="maxtrix_relation"/>
	</div>
	
	<script language="JavaScript">
	seajs.use(['lui/jquery','lui/dialog', 'lui/topic'], function($, dialog, topic) {
		<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrixTemplate.do?method=updateTemplateVersion&fdId=${JsParam.matrixId}">
		// 同步
		window.sync = function(elem, id, cur) {
			dialog.iframe("/sys/organization/sys_org_matrix/sysOrgMatrix_template_relation_version.jsp?matrixId=${JsParam.matrixId}&curVer=" + cur, "${lfn:message('sys-organization:sysOrgMatrix.relation.tip')}", function(version) {
				if(version) {
					// 设置版本号
					$.post("${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrixTemplate.do?method=updateTemplateVersion",
							{'ids': id, 'version': version, 'fdId': '${JsParam.matrixId}'}, function(res) {
						if(res.success) {
							dialog.success('<bean:message key="return.optSuccess"/>');
							topic.channel("maxtrix_relation").publish("list.refresh");
						} else {
							dialog.failure(res.msg);
						}
					}, "json");
				}
			}, {
				width : 300,
				height : 400,
				buttons : [{
					name : Msg_Info.button_ok,
					focus : true,
					fn : function(value, dialog) {
						if(dialog.frame && dialog.frame.length > 0) {
							var frame = dialog.frame[0];
							var contentDoc = $(frame).find("iframe")[0].contentDocument;
							var newVer = $(contentDoc).find("[name=version]:checked").val();
							value = newVer;
							dialog.hide(value);
						} else {
							dialog.hide(value);
						}
					}
				}, {
					name : Msg_Info.button_cancel,
					styleClass : 'lui_toolbar_btn_gray',
					fn : function(value, dialog) {
						dialog.hide();
					}
				}]
			});
		}
		</kmss:auth>
		// 多选框
		$("[name=maxtrix_relation_cb]").change(function() {
			if(this.checked) {
				$("#lui_maxtrix_relation_body").show();
			} else {
				$("#lui_maxtrix_relation_body").hide();
			}
		});
	});
	</script>
</div>