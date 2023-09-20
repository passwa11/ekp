<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
function config_notify_checkFunc(){
	var todoCheckbox  = document.getElementById("value(kmss.notify.type.todo.enabled)");
	var emailCheckbox  = document.getElementById("value(kmss.notify.type.email.enabled)");
	var mobileCheckbox  = document.getElementById("value(kmss.notify.type.mobile.enabled)");
	//var dingCheckbox  = document.getElementById("value(kmss.notify.type.ding.enabled)");
	
	if(!todoCheckbox.checked && !emailCheckbox.checked && !mobileCheckbox.checked){
		alert('【基础配置】消息机制配置:启用通知方式必须选择一种');
		return false;
	}		
	return true;
}

function config_notify_onloadFunc(){
	var emailCheckbox  = document.getElementById("value(kmss.notify.type.email.enabled)");
	if(emailCheckbox.checked){
		config_notify_checkMailConfig(true);
	}else{
		config_notify_checkMailConfig(false);
	}
}

function config_notify_configNotify(){
	var todoCheckbox  = document.getElementById("value(kmss.notify.type.todo.enabled)");
	var defaultTodoCheckbox = document.getElementById("value(kmss.notify.type.todo.default)");
	var emailCheckbox  = document.getElementById("value(kmss.notify.type.email.enabled)");
	var defaultEmailCheckbox = document.getElementById("value(kmss.notify.type.email.default)");
	var mobileCheckbox  = document.getElementById("value(kmss.notify.type.mobile.enabled)");
	var defaultMobileCheckbox = document.getElementById("value(kmss.notify.type.mobile.default)");
	var defaultEmailCheckboxHidden = document.getElementsByName("value(kmss.notify.type.email.default)")[1];
	var defaultMobileCheckboxHidden = document.getElementsByName("value(kmss.notify.type.mobile.default)")[1];
	var dingCheckbox  = document.getElementById("value(kmss.notify.type.ding.enabled)");
	var defaultDingCheckbox = document.getElementById("value(kmss.notify.type.ding.default)");
	var govdingCheckbox  = document.getElementById("value(kmss.notify.type.govding.enabled)");
	var defaultGovDingCheckbox = document.getElementById("value(kmss.notify.type.govding.default)");
	//默认通知方式
	if(!todoCheckbox.checked){
		if(defaultTodoCheckbox.checked){
			alert("请在启用通知方式中选择待办后再选待办为默认通知方式！");
			defaultTodoCheckbox.checked = false;
		}
	}
	if(!emailCheckbox.checked){
		if(defaultEmailCheckbox.checked){
			alert("请在启用通知方式中选择邮件后再选邮件为默认通知方式！");
			defaultEmailCheckbox.checked = false;
			defaultEmailCheckboxHidden.value = "false";
		}
	}
	
	if(!mobileCheckbox.checked){
		if(defaultMobileCheckbox.checked){
			alert("请在启用通知方式中选择短消息后再选短消息为默认通知方式！");
			defaultMobileCheckbox.checked = false;
			defaultMobileCheckboxHidden.value = "false";
		}
	}
	
	if(dingCheckbox !=null && !dingCheckbox.checked){
		
		if(defaultDingCheckbox !=null && defaultDingCheckbox.checked){
			alert("请在启用通知方式中选择DING后再选DING为默认通知方式！");
			defaultDingCheckbox.checked = false;
		}
	}
	if(govdingCheckbox !=null && !govdingCheckbox.checked){
		if(defaultGovDingCheckbox != null && defaultGovDingCheckbox.checked){
			alert("请在启用通知方式中选择政DING后再选政DING为默认通知方式！");
			defaultGovDingCheckbox.checked = false;
		}
	}
	
	if(govdingCheckbox !=null && !govdingCheckbox.checked){
		if(defaultGovDingCheckbox != null && defaultGovDingCheckbox.checked){
			alert("请在启用通知方式中选择政DING后再选政DING为默认通知方式！");
			defaultGovDingCheckbox.checked = false;
		}
	}
	
	//启用通知方式
	if(emailCheckbox.checked){
		config_notify_checkMailConfig(true);
	}else{
		config_notify_checkMailConfig(false);
	}	
}

function config_notify_checkMailConfig(value){
	var mailSetting = document.getElementById("kmss.notify.mailSetting");
	if(value){
		mailSetting.style.display = 'table-row';
	}else{
		mailSetting.style.display = 'none';
	}
}


function config_notify_mail_host_agreement() {
	var mailHost = document.getElementsByName("value(kmss.notify.mailHost)")[0].value;
	var agreement = document.getElementsByName("value(kmss.notify.mailHost.agreement)")[0].value;
	var sslTip = document.getElementById("sslTip");
	if (agreement == "SSL" || agreement == "TLS") {
		if (mailHost.indexOf(":") != -1) {
			sslTip.style.display = 'none';
		} else {
			sslTip.style.display = 'inline';
		}
	} else {
		sslTip.style.display = 'none';
	}
}

	config_addCheckFuncList(config_notify_checkFunc);
	config_addOnloadFuncList(config_notify_onloadFunc);
</script>
<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" colspan=2><b>消息机制配置</b></td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">启用通知方式</td>
		<td>
			<xform:checkbox  property="value(kmss.notify.type.todo.enabled)" dataType="boolean" value="true" subject="待办"  htmlElementProperties="id=\"value(kmss.notify.type.todo.enabled)\"" onValueChange="config_notify_configNotify()"  showStatus="edit">
				<xform:simpleDataSource value="true">待办</xform:simpleDataSource>
			</xform:checkbox>
			<xform:checkbox property="value(kmss.notify.type.email.enabled)" subject="Email" dataType="boolean"  htmlElementProperties="id=\"value(kmss.notify.type.email.enabled)\"" onValueChange="config_notify_configNotify()" showStatus="edit">
				<xform:simpleDataSource value="true">Email</xform:simpleDataSource>
			</xform:checkbox>
			<xform:checkbox property="value(kmss.notify.type.mobile.enabled)" subject="短消息" dataType="boolean" htmlElementProperties="id=\"value(kmss.notify.type.mobile.enabled)\"" onValueChange="config_notify_configNotify()" showStatus="edit">
				<xform:simpleDataSource value="true">短消息</xform:simpleDataSource>
			</xform:checkbox>
			<kmss:ifModuleExist path="/third/ding/">
				<xform:checkbox property="value(kmss.notify.type.ding.enabled)" subject="DING" dataType="boolean" htmlElementProperties="id=\"value(kmss.notify.type.ding.enabled)\"" onValueChange="config_notify_configNotify()" showStatus="edit">
					<xform:simpleDataSource value="true">DING</xform:simpleDataSource>
				</xform:checkbox>
			</kmss:ifModuleExist>
			<kmss:ifModuleExist path="/third/govding/">
				<xform:checkbox property="value(kmss.notify.type.govding.enabled)" subject="政DING" dataType="boolean" htmlElementProperties="id=\"value(kmss.notify.type.govding.enabled)\"" onValueChange="config_notify_configNotify()" showStatus="edit">
					<xform:simpleDataSource value="true">政DING</xform:simpleDataSource>
				</xform:checkbox>
			</kmss:ifModuleExist>
			<span class="txtstrong">*</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">默认通知方式</td>
		<td>
			<xform:checkbox property="value(kmss.notify.type.todo.default)" subject="待办" value="true" dataType="boolean" onValueChange="config_notify_configNotify()" htmlElementProperties="id=\"value(kmss.notify.type.todo.default)\"" showStatus="edit">
				<xform:simpleDataSource value="true">待办</xform:simpleDataSource>
			</xform:checkbox>
			<xform:checkbox property="value(kmss.notify.type.email.default)" subject="Email" dataType="boolean"  onValueChange="config_notify_configNotify()" htmlElementProperties="id=\"value(kmss.notify.type.email.default)\"" showStatus="edit">
				<xform:simpleDataSource value="true">Email</xform:simpleDataSource>
			</xform:checkbox>
			<xform:checkbox property="value(kmss.notify.type.mobile.default)" subject="短消息"  dataType="boolean"  onValueChange="config_notify_configNotify()" htmlElementProperties="id=\"value(kmss.notify.type.mobile.default)\"" showStatus="edit">
				<xform:simpleDataSource value="true">短消息</xform:simpleDataSource>
			</xform:checkbox>
			<kmss:ifModuleExist path="/third/ding/">
				<xform:checkbox property="value(kmss.notify.type.ding.default)" subject="DING"  dataType="boolean"  onValueChange="config_notify_configNotify()" htmlElementProperties="id=\"value(kmss.notify.type.ding.default)\"" showStatus="edit">
					<xform:simpleDataSource value="true">DING</xform:simpleDataSource>
				</xform:checkbox>
			</kmss:ifModuleExist>
			<kmss:ifModuleExist path="/third/govding/">
				<xform:checkbox property="value(kmss.notify.type.govding.default)" subject="政DING"  dataType="boolean"  onValueChange="config_notify_configNotify()" htmlElementProperties="id=\"value(kmss.notify.type.govding.default)\"" showStatus="edit">
					<xform:simpleDataSource value="true">政DING</xform:simpleDataSource>
				</xform:checkbox>
			</kmss:ifModuleExist>
			<span class="txtstrong">*</span>
		</td>
	</tr>
	<%-- 
	<tr style="display:none" id="kmss.notify.mailFrom">
		<td class="td_normal_title" width="15%">缺省邮箱</td>
		<td>
			<xform:text property="value(kmss.notify.mailFrom)" style="width:300px"
				required="true" showStatus="edit" htmlElementProperties="disabled='true'" subject="缺省邮箱"/>
			<br>
			<span class="message">例：admin@demo.landray.com.cn 说明：用来作为系统发送邮件的缺省邮箱</span>
		</td>
	</tr>
	<tr style="display:none" id="kmss.notify.mailHost">
		<td class="td_normal_title" width="15%">邮件服务器地址</td>
		<td>
			<xform:text property="value(kmss.notify.mailHost)" style="width:300px"
				required="true" showStatus="edit" subject="邮件服务器地址" onValueChange="config_notify_mail_host_agreement"/>
			&nbsp;&nbsp;&nbsp;
			请选择加密协议：
			<xform:select property="value(kmss.notify.mailHost.agreement)" showStatus="edit" onValueChange="config_notify_mail_host_agreement();" showPleaseSelect="false">
				<xform:simpleDataSource value="">无</xform:simpleDataSource>
				<xform:simpleDataSource value="SSL">SSL</xform:simpleDataSource>
				<xform:simpleDataSource value="TLS">TLS</xform:simpleDataSource>
			</xform:select>
			&nbsp;&nbsp;&nbsp;
			<span id="sslTip" style="color: red;display: none;"> 请按以下格式配置端口号，如：mail.demo.landray.com.cn:465（SSL协议默认端口：465，TLS协议默认端口：25）</span>
			<br>
			<span class="message">例：mail.demo.landray.com.cn，则默认端口为25，如果使用其他端口（例如511），请按以下格式配置：mail.demo.landray.com.cn:511</span>
		</td>
	</tr>
	<tr style="display:none" id="kmss.notify.mailFromDefault">
		<td class="td_normal_title" width="15%">始终以系统发送邮件</td>
		<td>
			<xform:checkbox property="value(kmss.notify.mailFromDefault)" subject="始终以系统发送邮件"  htmlElementProperties="id=\"value(kmss.notify.mailFromDefault)\"" showStatus="edit">
				<xform:simpleDataSource value="true">启用</xform:simpleDataSource>
			</xform:checkbox>&nbsp;		
			<span class="message">(说明：勾中则以系统缺省邮件发送邮件，否则以个人邮件发送邮件)</span>
		</td>
	</tr>
	<tr style="display:none" id="kmss.notify.mailDefaultEncoding">
		<td class="td_normal_title" width="15%">邮件默认编码格式</td>
		<td>
			<xform:text property="value(kmss.notify.mailDefaultEncoding)" style="width:150px"
				required="true" showStatus="edit" htmlElementProperties="disabled='true'" subject="邮件默认编码格式"/>
			<br>
			<span class="message">例：GB2312</span>
		</td>
	</tr>
	<tr style="display:none" id="kmss.notify.mailSmtpAuth">
		<td class="td_normal_title" width="15%">是否需要认证</td>
		<td>
			<label>
				<xform:checkbox property="value(kmss.notify.mailSmtpAuth)" subject="是否需要认证" onValueChange="config_notify_configMail()"  htmlElementProperties="id=\"value(kmss.notify.mailSmtpAuth)\"" showStatus="edit">
					<xform:simpleDataSource value="true">启用</xform:simpleDataSource>
				</xform:checkbox>				
			</label>
		</td>
	</tr>
	<tr style="display:none" id="kmss.notify.mailUsername">
		<td class="td_normal_title" width="15%">认证帐号</td>
		<td>
			<xform:text property="value(kmss.notify.mailUsername)" style="width:150px"
				required="true" showStatus="edit" htmlElementProperties="disabled='true'" subject="认证帐号"/>
			<br>
		</td>
	</tr>
	<tr style="display:none" id="kmss.notify.mailPassword">
		<td class="td_normal_title" width="15%">认证密码</td>
		<td>
			<xform:text property="value(kmss.notify.mailPassword)" style="width:150px" 
				required="true" showStatus="edit" htmlElementProperties="type='password' disabled='true'" subject="认证密码"/>
			<br>
		</td>
	</tr>
	<tr style="display:none" id="kmss.notify.mailTimeout">
		<td class="td_normal_title" width="15%">链接超时</td>
		<td>
			<xform:text property="value(kmss.notify.mailTimeout)" style="width:150px"
				required="true" showStatus="edit" htmlElementProperties="disabled='true'" 
				validators="required number scaleLength(0)" subject="链接超时"/>毫秒
				<div>
				<span class="message">(说明：如果超出链接超时时间邮件服务器还没有应答，将导致邮件发送失败。)</span>
				</div>
			<br>
		</td>
	</tr>
	<tr style="display:none" id="kmss.notify.mailSendtoCount">
		<td class="td_normal_title" width="15%">最大收件人个数</td>
		<td>
			<xform:text property="value(kmss.notify.mailSendtoCount)" style="width:150px"
				required="true" showStatus="edit" htmlElementProperties="disabled='true'" 
				validators="required number scaleLength(0)" subject="最大收件人个数"/>个
				<div>
				<span class="message">(说明：由于每种邮件服务器对一封邮件中的收件人个数限制不同，超出大小则会导致邮件发送失败，可根据邮件服务器进行配置，如同时发送300人的邮件，配置最大个数为100的，则会拆分为3封进行发送。)</span>
				</div>
			<br>
		</td>
	</tr>
	--%>
	<tr style="display:none" id="kmss.notify.mailSetting">
		<td class="td_normal_title" colspan="2"><a  target="_blank"
					href='<c:url value="/sys/profile/index.jsp#notify/mail/"/>'><font color="red">请进入后台配置，“统一消息中心”—“邮件”—“邮箱设置”中配置详细信息（注意，该操作是必须完成的）</font></a></td>
	</tr>
	<tr style="" id="kmss.notify.todoSetting">
		<td class="td_normal_title" colspan="2"><a  target="_blank"
					href='<c:url value="/sys/profile/index.jsp#notify/mail/"/>'><font color="red">1.待办为默认设置不能取消(保存不生效)</font></a></td>
	</tr>
	<tr style="display:none">
	<td>
	待办保存多种语言（true或false）
	</td>
		<td>
	<xform:text property="value(sys.notify.lang.store)" subject="待办保存多种语言" required="false" style="width:150px" showStatus="edit"/><br>
	</td>
	</tr>		
	
	<tr style="display:none">
	<td>
	发邮件收件人排序（asc或者desc）
	</td>
		<td>
	<xform:text property="value(sys.notify.mail.order)" subject="发邮件收件人排序" required="false" style="width:150px" showStatus="edit"/><br>
	</td>
	</tr>		
	
</table>