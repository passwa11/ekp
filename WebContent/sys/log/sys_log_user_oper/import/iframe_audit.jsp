<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>

<template:include ref="default.dialog">
	<template:replace name="content">
		<center>
			<script type="text/javascript">
				seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
					//确认
					window.clickOK = function() {
						var data = $dialog.content.params.data;
						var url = $dialog.content.params.url;
						if(!url) {
							dialog.alert('<bean:message key="errors.required" arg0="URL"/>');
							return false;
						}

						var desc = $("textarea[name='desc']").val();
						if(!desc){
							desc = "";
							dialog.alert("<bean:message key="errors.required" arg0="${lfn:message('sys-log:page.audit.fdDesc')}"/>");
							return;
						}

						if(desc.replace(/[^\x00-\xff]/g,"***").length > 1500){
							dialog.alert("<bean:message key="errors.maxLength" arg0="${lfn:message('sys-log:page.audit.fdDesc')}" arg1="1500"/>");
							return;
						}
						
						data += "&fdDesc=" + desc;
						
						$.ajax({
							url : url,
							type : 'POST',
							data : data,
							dataType : 'json',
							error : function(data) {
								dialog.result(data.responseJSON);
								callback(data);
							},
							success: function(data) {
								dialog.result(data);
								callback(data);
							}
					   });
						
						LUI('okBtn').setDisabled(true);
						LUI('closeBtn').setDisabled(true);
					};
					
					window.callback = function(data) {
						$dialog.hide(data);
					}
				});
				
			</script>
			<!-- 提示框 Starts -->
			<div>
				<br />
				<xform:textarea property="desc"
					htmlElementProperties="placeholder='${lfn:message('sys-log:page.audit.fdDesc')}'"
					style="width:95%;height:70%" showStatus="edit" />
				<br />
			</div>
			<!-- 提示框 Ends -->
			<div data-lui-type="lui/toolbar!ToolBar" style="" id="lui-id-2"
				class="lui-component" data-lui-cid="lui-id-2"
				data-lui-parse-init="1">
				<div class="lui_toolbar_frame">
					<div class="lui_toolbar_left">
						<div class="lui_toolbar_right">
							<div class="lui_toolbar_content">
								<table lui_toolbar_mark="1">
									<tbody>
										<tr>
											<td lui-button-container="1"><div
													class="lui_toolbar_btn"
													data-lui-on-class="lui_toolbar_btn_on"
													data-lui-status-class="lui_toolbar_btn_toggle_on">
													<div id="okBtn" data-lui-type="lui/toolbar!Button" style=""
														class="lui-component lui_widget_btn lui_toolbar_btn_def"
														data-lui-cid="okBtn" data-lui-parse-init="3"
														tabindex="0">

														<div class="lui_toolbar_btn_l"
															data-lui-mark="toolbar_button_inner"
															style="text-align: center;">
															<div class="lui_toolbar_btn_r">
																<div class="lui_toolbar_btn_c"
																	data-lui-mark="toolbar_button_content">
																	<div id="lui-id-4"
																		class="lui-component lui_widget_btn_txt "
																		data-lui-cid="lui-id-4"  onclick="clickOK();">${lfn:message('button.ok')}</div>
																</div>
															</div>
														</div>
													</div>
												</div></td>
											<td lui-button-container="1"><div
													class="lui_toolbar_btn"
													data-lui-on-class="lui_toolbar_btn_on"
													data-lui-status-class="lui_toolbar_btn_toggle_on">
													<div id="closeBtn" data-lui-type="lui/toolbar!Button"
														style=""
														class="lui-component lui_widget_btn lui_toolbar_btn_def"
														data-lui-cid="closeBtn" data-lui-parse-init="4" 
														tabindex="0">

														<div class="lui_toolbar_btn_l"
															data-lui-mark="toolbar_button_inner"
															style="text-align: center;">
															<div class="lui_toolbar_btn_r">
																<div class="lui_toolbar_btn_c"
																	data-lui-mark="toolbar_button_content">
																	<div id="lui-id-5"
																		class="lui-component lui_widget_btn_txt "
																		data-lui-cid="lui-id-5" onclick="Com_CloseWindow();">${lfn:message('button.close')}</div>
																</div>
															</div>
														</div>
													</div>
												</div></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</center>
		<style>
span {
	padding: 8px;
	word-break: break-word;
}
.lui_toolbar_frame .lui_toolbar_btn .lui_widget_btn_txt {
/* color:rgb(255,255,255); */
}

</style>
	</template:replace>
</template:include>
