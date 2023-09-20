<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="content">
		<html:form action="/third/wechat/wechatMainConfig.do">
		<div class="lui_form_content_frame" style="padding:50px"> 
			<p class="txttitle">${lfn:message('third-wechat:wechatMainConfig.config')}</p>
			<div style="margin: 20px 0; text-align: right; color: #d02300">
				${lfn:message('third-wechat:wechatMainConfig.config.tip')}
			</div>
			<table class="tb_normal" width=100% style="margin-top: 15px;">
				<tr>
					<td class="td_normal_title" width=25%>${lfn:message('third-wechat:wechatMainConfig.config.url')}</td>
					<td>
						<xform:text property="lwechat_wyUrl" style="width:100%" showStatus="readOnly"></xform:text>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=25%>${lfn:message('third-wechat:wechatMainConfig.config.notify')}</td>
					<td>
						<ui:switch property="lwechat_wyEnable" onValueChange="wy_display_change();" 
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					</td>
				</tr>
				<tr id="wy_todo_type">
					<td class="td_normal_title" width=25%>${lfn:message('third-wechat:wechatMainConfig.config.notify.type')}</td>
					<td>
						<xform:checkbox property="lwechat_wyisSendTodo">
						   <xform:simpleDataSource value="true">${lfn:message('third-wechat:wechatMainConfig.config.notify.type1')}</xform:simpleDataSource>
						</xform:checkbox>
						<xform:checkbox property="lwechat_wyisSendView">
						   <xform:simpleDataSource value="true">${lfn:message('third-wechat:wechatMainConfig.config.notify.type2')}</xform:simpleDataSource>
						</xform:checkbox>
						
				       
					</td>
				</tr>
			</table>
			<div style="width: 75%;margin: 20px auto;text-align: center;">
				<ui:button text="${lfn:message('button.update')}"  onclick="Com_Submit(document.wechatMainConfigForm, 'update');" order="1"  width="120" height="35"></ui:button>
			</div>
		</div>
		<div style="display: none">
			<xform:text property="lwechat_license" style="width:100%"></xform:text>
			<xform:text property="lwechat_qyUrl" style="width:100%"></xform:text>
			<xform:radio property="lwechat_qyEnable" showStatus="edit" ></xform:radio>
			<div id="_qy" style="display: none">
				 <input type="checkbox" name="lwechat_qyisSendTodo" value="true" id="_lwechat_qyisSendTodo">${lfn:message('third-wechat:wechatMainConfig.config.notify.type1')}
				 <input type="checkbox" name="lwechat_qyisSendView" value="true" id="_lwechat_qyisSendView">${lfn:message('third-wechat:wechatMainConfig.config.notify.type2')}
			</div>
			<xform:text property="lwechat_qyNotifyUrl" style="width:100%"></xform:text>
			<xform:text property="lwechat_wyNotifyUrl" style="width:100%"></xform:text>
		</div>
		</html:form>
		<script type="text/javascript">
			seajs.use(['lui/jquery'],function($){

				function wy_display_change() {
					var lwechat_wyEnable = $('input[name="lwechat_wyEnable"]').val();
					if(lwechat_wyEnable == 'true') {
						$('#wy_todo_type').show();
					} else {
						$('#wy_todo_type').hide();
					}
					
				}
				
				LUI.ready(function(){
					wy_display_change();
				});

				window.wy_display_change = wy_display_change;
			});
		</script>
	</template:replace>
</template:include>