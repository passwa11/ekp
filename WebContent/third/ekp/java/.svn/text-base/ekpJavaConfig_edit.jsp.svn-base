<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">${lfn:message('third-ekp-java:ekp.java.setting')}</template:replace>
	<template:replace name="head">
		<script type="text/javascript"
			src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
	Com_IncludeFile(
			"validator.jsp|validation.js|plugin.js|validation.jsp|xform.js|doclist.js|dialog.js",
			null, "js");
</script>
		<style type="text/css">
.tb_normal td {
	//padding: 5px; //
	border: 1px #d2d2d2 solid; //
	word-break: break-all;
}
</style>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0"><span
			style="color: #35a1d0;">${lfn:message('third-ekp-java:ekp.java.setting')}</span></h2>

		<html:form action="/third/ekp/java/oms/in/config.do" onsubmit="return validate();">
			<center>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>${lfn:message('third-ekp-java:ekp.java.integrate.enable')}</td>
					<td><ui:switch property="value(kmss.integrate.java.enabled)"
						onValueChange="config_chgEnabled();"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					</td>
				</tr>
			</table>
			<table class="tb_normal" id='lab_detail' width=95% cellpadding="20"
				cellspacing="20"
				style="display: none; width: 95%;">
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.local.name')}</td>
					<td><label> <xform:text
						property="value(kmss.java.system.name)" subject="${lfn:message('third-ekp-java:ekp.java.local.name')}"
						style="width:85%" showStatus="edit"
						htmlElementProperties="disabled='true'" /><br>
					</label> <span class="message">${lfn:message('third-ekp-java:ekp.java.local.name.tip')}</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.synchro.enable')}</td>
					<td><label>
					<ui:switch property="value(kmss.oms.in.java.enabled)"
						onValueChange="updateOmsIn();"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</label> <span class="message">${lfn:message('third-ekp-java:ekp.java.synchro.tip')}</span>
					</td>
				</tr>
				<tr id="tr_business">
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.synchro.business.no')}</td>
					<td><label> 
					<ui:switch property="value(kmss.oms.in.java.synchro.business.no)"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					
					</label> <span class="message">${lfn:message('third-ekp-java:ekp.java.synchro.business.no.tip')}</span></td>
				</tr>
				<!-- 是否覆盖本系统所属岗位 -->
				<tr id="tr_post">
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.synchro.post.cover')}</td>
					<td><label> 
					<ui:switch property="value(kmss.oms.in.java.synchro.post.cover)"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					
					</label> <span class="message">${lfn:message('third-ekp-java:ekp.java.synchro.post.cover.tip')}</span></td>
				</tr>
				<tr id="tr_roleLine">
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.synchro.role.enable')}</td>
					<td><label> 
					<ui:switch property="value(kmss.oms.in.java.synchro.roleLine)"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					
					</label> <span class="message">${lfn:message('third-ekp-java:ekp.java.synchro.role.tip')}</span></td>
				</tr>
				<tr id="tr_roleLine_cate">
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.synchro.role.cate.enable')}</td>
					<td><label> 
					<ui:switch property="value(kmss.oms.in.java.synchro.roleConfCate)"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					 <span class="message">${lfn:message('third-ekp-java:ekp.java.synchro.role.cate.tip')}</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.notify.enable')}</td>
					<td><label> 
					<ui:switch property="value(kmss.notify.todoExtend.java.enabled)" onValueChange="notifyEnableChange()"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					</label> <span class="message">${lfn:message('third-ekp-java:ekp.java.notify.tip')}</span>
					</td>
				</tr>
				
				<tr id="tr_notifyApiType">
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.notify.api.type')}</td>
					<td>
						
						<input type="text" style="display:none;" class="inputsgl" id="apiTypeOld" value="${sysAppConfigForm.map['kmss.notify.todoExtend.api.type']}" disabled >
		
						<xform:select htmlElementProperties="id='apiTypeNew'" property="value(kmss.notify.todoExtend.api.type)">
							<xform:simpleDataSource value="simple">${lfn:message('third-ekp-java:ekp.java.notify.api.type.simple')}</xform:simpleDataSource>
							<xform:simpleDataSource value="ekpj">${lfn:message('third-ekp-java:ekp.java.notify.api.type.ekpj')}</xform:simpleDataSource>
						</xform:select>
						<br>
					    <span class="txtstrong">
					    	${lfn:message('third-ekp-java:ekp.java.notify.api.type.tip')}
					    </span>
					</td>
				</tr>
				
				
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.tag.enable')}</td>
					<td><label> 
					<ui:switch property="value(kmss.tag.java.enabled)"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					</label> <span class="message">${lfn:message('third-ekp-java:ekp.java.tag.tip')}</span>
					</td>
				</tr>
				<tr>
				
				<%-- 
			<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.circuitBreaker.enable')}</td>
				<td colspan="3">
				<label>
					<ui:switch property="value(ekp.java.CircuitBreaker.enable)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
				</label>
				<span class="txtstrong">${lfn:message('third-ekp-java:ekp.java.circuitBreaker.tip')}</span>
				</td>
			</tr>
			--%>
			
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.webservice.urlPrefix')}</td>
					<td><xform:text
						property="value(kmss.java.webservice.urlPrefix)"
						subject="${lfn:message('third-ekp-java:ekp.java.webservice.urlPrefix')}" required="true" style="width:85%"
						showStatus="edit" htmlElementProperties="disabled='true'" /><br>
					<span class="message">${lfn:message('third-ekp-java:ekp.java.webservice.urlPrefix.tip')}</span></td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.webservice.userName')}</td>
					<td><xform:text
						property="value(kmss.java.webservice.userName)"
						subject="${lfn:message('third-ekp-java:ekp.java.webservice.userName')}" required="false" style="width:85%"
						showStatus="edit" htmlElementProperties="disabled='true'" /></td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.webservice.password')}</td>
					<td>
					
					<xform:text
						property="value(kmss.java.webservice.password)"
						subject="${lfn:message('third-ekp-java:ekp.java.webservice.password')}" required="false" style="width:85%"
						showStatus="edit" htmlElementProperties="disabled='true' type='password' class='inputsgl'" />
					
					</td>
						
				</tr>
				<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.restservice.userName')}</td>
					<td><xform:text
						property="value(kmss.java.restservice.userName)"
						subject="${lfn:message('third-ekp-java:ekp.java.restservice.userName')}" required="false" style="width:85%"
						showStatus="edit" htmlElementProperties="disabled='true'" /><br>
						<span class="message">${lfn:message('third-ekp-java:ekp.java.restservice.tips')}</span>
						</td>
						
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.restservice.password')}</td>
					<td>
					
					<xform:text
						property="value(kmss.java.restservice.password)"
						subject="${lfn:message('third-ekp-java:ekp.java.restservice.password')}" required="false" style="width:85%"
						showStatus="edit" htmlElementProperties="disabled='true' type='password' class='inputsgl'" /><br>
						<span class="message">${lfn:message('third-ekp-java:ekp.java.restservice.tips')}</span>
					</td>
						
				</tr>
				<% } %>
				<tr style="display:none;">
					<td style="display:none;" class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.notify.mq.send.enabled')}</td>
					<td style="display:none;"><label> 
					<ui:switch property="value(kmss.notify.mq.send.enabled)"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					</label> <span
						class="message">${lfn:message('third-ekp-java:ekp.java.notify.mq.send.tip')}</span></td>
				</tr>
				<tr style="display:none;">
					<td style="display:none;" class="td_normal_title" width="15%">${lfn:message('third-ekp-java:ekp.java.notify.mq.receive.enable')}</td>
					<td style="display:none;"><label> 
					<ui:switch property="value(kmss.notify.mq.receive.enabled)"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					</label> <span class="message">${lfn:message('third-ekp-java:ekp.java.notify.mq.receive.tip.pre')}<span
						class="txtstrong">${lfn:message('third-ekp-java:ekp.java.notify.mq.receive.tip.mid')}</span>${lfn:message('third-ekp-java:ekp.java.notify.mq.receive.tip.suf')} </span></td>
				</tr>


			</table>
			<br />
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName"
				value="com.landray.kmss.third.ekp.java.EkpJavaConfig" />
			<input type="hidden" name="autoclose" value="false" />
			<center style="margin-top: 10px;"><!-- 保存 --> <ui:button
				text="${lfn:message('button.save')}" height="35" width="120"
				onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
				<ui:button
				text="${lfn:message('third-ekp-java:button.deleteTimeStamp')}" height="35" width="120" title="${lfn:message('third-ekp-java:button.deleteTimeStamp.tip')}" 
				onclick="deleteTimeStamp()"></ui:button>
			</center>
		</html:form>

		<script type="text/javascript">
	$KMSSValidation();
	function validateAppConfigForm(thisObj) {
		return true;
	}
	
	function config_chgEnabled() {
		var cfgDetail = $("#lab_detail");
		var isChecked = "true" == $(
				"input[name='value\\\(kmss.integrate.java.enabled\\\)']")
				.val();
		if (isChecked) {
			cfgDetail.show();
		} else {
			cfgDetail.hide();
			$("input[name='value\\\(kmss.oms.in.java.enabled\\\)']").val("false");
			$("input[name='value\\\(kmss.notify.todoExtend.java.enabled\\\)']").val("false");
			$("input[name='value\\\(kmss.oms.in.java.synchro.roleLine\\\)']").val("false");
			$("input[name='value\\\(kmss.oms.in.java.synchro.roleConfCate\\\)']").val("false");
			$("input[name='value\\\(kmss.notify.mq.send.enabled\\\)']").val("false");
			$("input[name='value\\\(kmss.notify.mq.receive.enabled\\\)']").val("false");
			$("input[name='value\\\(kmss.tag.java.enabled\\\)']").val("false");
		}
		cfgDetail.find("input").each( function() {
				if($(this).attr("id")=='apiTypeOld'){
					return;
				}
				if(isChecked){
					$(this).attr("disabled", false);
				}else{
					if($(this).val()==''){
						$(this).attr("disabled", true);
					}
				}
		});
		
		notifyEnableChange();
	}

	function cnofig_java_init(){
		config_chgEnabled();
		var systemName = $(
				"input[name='value\\\(kmss.java.system.name\\\)']");
		if(systemName.val()==''){
			systemName.val('EKP');
		}
	}
	function updateOmsIn(){
		var isChecked = "true" == $(
				"input[name='value\\\(kmss.oms.in.java.enabled\\\)']").val();
		var tr_roleLine = $("#tr_roleLine");
		var tr_roleLine_cate = $("#tr_roleLine_cate");
		var tr_business = $("#tr_business");
		var tr_post = $("#tr_post");
		if(isChecked){
			tr_roleLine.show();
			tr_roleLine_cate.show();
			tr_business.show();
			tr_post.show();
		}else{
			tr_roleLine.hide();
			tr_roleLine_cate.hide();
			tr_business.hide();
			tr_post.hide();
		}
		tr_roleLine.find("input").each( function() {
			$(this).attr("disabled", !isChecked);
		});
		tr_roleLine_cate.find("input").each( function() {
			$(this).attr("disabled", !isChecked);
		});
	}
	
	function deleteTimeStamp(){
		window.open(Com_Parameter.ContextPath+"third/ekp/java/config.do?method=deleteTimeStamp",'_blank');
	}

	LUI.ready( function() {
		cnofig_java_init();
		config_chgEnabled();
		updateOmsIn();
	});
	
	function validate(){
		var apiTypeOld = $('#apiTypeOld').val();
		if(apiTypeOld==''){
			return true;
		}
		var apiTypeNew = $('#apiTypeNew').val();
		if(apiTypeNew==apiTypeOld){
			return true;
		}else{
			if(confirm("修改待办接口类型可能到导致已经同步过去的旧待办无法自动消失，确定要修改吗？"))
			{
			 	return true;
			}else{
				return false;
			}
		}
	}
	
	function notifyEnableChange(){
		var isChecked = "true" == $(
			"input[name='value\\\(kmss.notify.todoExtend.java.enabled\\\)']").val();
		var tr_notifyApiType = $("#tr_notifyApiType");
		if(isChecked){
			tr_notifyApiType.show();
		}else{
			tr_notifyApiType.hide();
		}
	}
</script>
	</template:replace>
</template:include>
