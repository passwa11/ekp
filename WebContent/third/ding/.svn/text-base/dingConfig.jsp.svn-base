<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
function config_ding_chgDisplay(){
	var tbObj = document.getElementById("kmss.third.ding");
	var field = tbObj.rows[0].getElementsByTagName("INPUT")[0];
	var  todo= document.getElementsByName("value(kmss.third.ding.todo.enabled)")[0];
	var omsOut = document.getElementsByName("value(kmss.third.ding.oms.out.enabled)")[0];
	var omsIn = document.getElementsByName("value(kmss.third.ding.oms.in.enabled)")[0];
	if(!field.checked){
		todo.checked = false;
		omsOut.checked = false;
		omsIn.checked = false;
	}
	for(var i=1; i<tbObj.rows.length; i++){
		tbObj.rows[i].style.display = field.checked?"":"none";
	}
	if(field.checked){
		config_ding_todo_chgDisplay();
		config_ding_oms_out_chgDisplay();
		config_ding_oms_in_chgDisplay();
	}
	config_ding_callbackurl();
}

function config_ding_callbackurl(){
	document.getElementsByName("value(kmss.third.ding.callbackurl)")[0].value=
		document.getElementsByName("value(kmss.third.ding.domain)")[0].value+
		document.getElementsByName("ding_url_suffix")[0].value;
}

function config_ding_todo_chgDisplay(){
	var  todo= document.getElementsByName("value(kmss.third.ding.todo.enabled)")[0];
	if(todo.checked){
		$('.ding_conifg_todo').show();
	}else{
		$('.ding_conifg_todo').hide();
	}
}

function config_ding_oms_out_chgDisplay(){
	var  out= document.getElementsByName("value(kmss.third.ding.oms.out.enabled)")[0],
		 _in= document.getElementsByName("value(kmss.third.ding.oms.in.enabled)")[0];
	if(out.checked){
		_in.checked = false;
		$('.ding_conifg_oms_out').show();
		$('.ding_conifg_oms_in').hide();
	}else{
		$('.ding_conifg_oms_out').hide();
	}
}

function config_ding_oms_in_chgDisplay(){
	var  _in= document.getElementsByName("value(kmss.third.ding.oms.in.enabled)")[0],
		  out= document.getElementsByName("value(kmss.third.ding.oms.out.enabled)")[0];
	if(_in.checked){
		out.checked = false;
		$('.ding_conifg_oms_in').show();
		$('.ding_conifg_oms_out').hide();
	}else{
		$('.ding_conifg_oms_in').hide();
	}
}

function config_ding_dns_getUrl(){
	var url = location.href;
	var i = url.indexOf("admin.do");
	var rtnUrl = document.getElementsByName("value(kmss.third.ding.domain)")[0];
	rtnUrl.value = url.substring(0, i-1);
}

function config_ding_randomAlphanumeric(charsLength,chars){
	var length = charsLength;
	if (!chars){
		var chars = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
	}
	var randomChars = ""; 
	for(x=0; x<length; x++) {
		var i = Math.floor(Math.random() * chars.length); 
		randomChars += chars.charAt(i); 
	}
	return randomChars; 
}
//config_addOnloadFuncList(config_ding_chgDisplay);

</script>
<table class="tb_normal" width=100% id="kmss.third.ding" style="display: none;">
	<tr>
		<td class="td_normal_title" colspan=2>
			<b>
				<label>
					<html:checkbox property="value(kmss.third.ding.enabled)" value="true" onclick="config_ding_chgDisplay()"/>集成钉钉
				</label>
			</b>
		</td>
	</tr>
	
	<tr>
		<td colspan=2>
			<b><label>钉钉基础配置</label></b>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">钉钉微应用中指定的企业ID</td>
		<td>
			<xform:text property="value(kmss.third.ding.corpid)" subject="钉钉微应用中指定的企业ID" 
				required="false" style="width:85%" showStatus="edit"/><span class="txtstrong">*</span><br>
			<span class="message">从钉钉后台管理平台中获取</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">钉钉微应用中指定的企业secret</td>
		<td>
			<xform:text property="value(kmss.third.ding.corpsecret)" subject="钉钉微应用中指定的企业secret" 
				required="false" style="width:85%" showStatus="edit" /><span class="txtstrong">*</span><br>
			<span class="message">从钉钉后台管理平台中获取</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">钉钉微应用首页地址中设置的域名</td>
		<td>
			<xform:text property="value(kmss.third.ding.domain)" subject="钉钉微应用首页地址中设置的域名" 
				required="false" style="width:85%" showStatus="edit"  onValueChange="config_ding_callbackurl()"/>
				<input type="button" class="btnopt" value="自动获取" onclick="config_ding_dns_getUrl()"/><br>
			<span class="message">可选，不设置为EKP应用所在域名</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">回调URL</td>
		<td>
			<input type="hidden" name="ding_url_suffix" value="/resource/third/ding/endpoint.do?method=service">
			<xform:text property="value(kmss.third.ding.callbackurl)" subject="钉钉微应用中回调URL" 
				required="false" style="width:85%" showStatus="readOnly" /><br>
			<span class="message">接收钉钉事件推送的地址</span>
		</td>
	</tr>
		<tr>
		<td class="td_normal_title" width="15%">回调Token</td>
		<td>
			<xform:text property="value(kmss.third.ding.token)" subject="钉钉Token" 
				required="false" style="width:85%" showStatus="edit" /><span class="txtstrong">*</span>
				<input type="button" class="btnopt" value="随机生成" onclick="document.getElementsByName('value(kmss.third.ding.token)')[0].value=config_ding_randomAlphanumeric(16)"/><br>
			<span class="message">钉钉回调Token，用来生成signature，用来和回调参数中的signature比对，校验消息的合法性</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">回调EncodingAESKey</td>
		<td>
			<xform:text property="value(kmss.third.ding.aeskey)" subject="钉钉aeskey" 
				required="false" style="width:85%" showStatus="edit" /><span class="txtstrong">*</span>
			<input type="button" class="btnopt" value="随机生成" onclick="document.getElementsByName('value(kmss.third.ding.aeskey)')[0].value=config_ding_randomAlphanumeric(43)"/><br>
			<span class="message">用于回调数据的加解密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成。</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">EKP使用钉钉开放授权</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.third.ding.oauth2.enabled)" value="true"/>&nbsp;启用
			</label>
			<span class="message">EKP使用钉钉开放授权，免登录使用EKP移动应用</span>
		</td>
	</tr>
	<tr>
		<td colspan=2>
			<b><label>钉钉消息推送配置</label></b>
			<label>
				<html:checkbox property="value(kmss.third.ding.todo.enabled)" value="true" onclick="config_ding_todo_chgDisplay()" />&nbsp;启用
			</label>
		</td>
	</tr>
	<tr class="ding_conifg_todo">
		<td class="td_normal_title" width="15%">待办消息在钉钉微应用ID</td>
		<td>
			<xform:text property="value(kmss.third.ding.agentid)" subject="待办消息在钉钉微应用ID" 
				required="false" style="width:85%" showStatus="edit" />
				<br>
			<span class="message">钉钉微应用ID，钉钉消息发送时必选，在此用于EKP中待办发送到钉钉时所在的应用ID</span>
		</td>
	</tr>
	<tr class="ding_conifg_todo">
		<td class="td_normal_title" width="15%">钉钉消息标题的颜色</td>
		<td>
			<xform:text property="value(kmss.third.ding.title.color)" subject="钉钉消息标题的颜色" 
				required="false" style="width:85%" showStatus="edit" />
				<br>
			<span class="message">在此用于EKP中待办发送到钉钉时，用于设置消息标题的颜色，可选</span>
		</td>
	</tr>
	<tr class="ding_conifg_todo">
		<td class="td_normal_title" width="15%">钉钉待阅推送:</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.third.ding.todo.type2.enabled)" value="true"/>&nbsp;启用
			</label>
			<br>
			<span class="message">默认EKP待阅类消息不会推送到钉钉，可选</span>
		</td>
	</tr>
	<tr title="基于组织架构维护的单向性,接入、接出配置只能二选一">
		<td colspan=2>
			<b><label >组织架构接出配置（EKP到钉钉）</label></b>
			<label>
				<html:checkbox property="value(kmss.third.ding.oms.out.enabled)" value="true" onclick="config_ding_oms_out_chgDisplay()"/>启用
			</label>
		</td>
	</tr>
	<tr class="ding_conifg_oms_out">
		<td class="td_normal_title" width="15%">EKP中根机构ID</td>
		<td>
			<xform:text property="value(kmss.third.ding.org.id)" subject="EKP中根机构ID" 
				required="false" style="width:85%" showStatus="edit" /><span class="txtstrong">*</span><br>
			<span class="message">将EKP中的组织机构同步到钉钉时，指定只同步EKP中某个机构或部门下的所有成员到钉钉，多个机构ID可以用;分隔</span>
		</td>
	</tr>
	<tr class="ding_conifg_oms_out">
		<td class="td_normal_title" width="15%">是否同步根机构到钉钉</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.third.ding.oms.root.flag)" value="true"/>同步根机构
			</label>
			<span class="message">指定是否同步根机构到钉钉，需要和EKP中根机构ID中指定的值一同使用，可选，默认不同步</span>
		</td>
	</tr>
	<tr  class="ding_conifg_oms_out">
		<td class="td_normal_title" width="15%">钉钉中根部门ID</td>
		<td>
			<xform:text property="value(kmss.third.ding.ding.deptid)" subject="钉钉中根部门ID" 
				required="false" style="width:85%" showStatus="edit" /><br>
			<span class="message">将EKP中的组织机构同步到钉钉时，指定将EKP中的所有部门放在钉钉中根部门下，不设置该值，默认为钉钉的主根下，如果要设置，请在钉钉的管理平台的地址栏查看</span>
		</td>
	</tr>
	<tr class="ding_conifg_oms_out">
		<td class="td_normal_title" width="15%">钉钉中是否创建一个关联此部门的企业群</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.third.ding.oms.createDeptGroup)" value="true"/>关联创建企业部门
			</label>
		</td>
	</tr>
	<tr  title="基于组织架构维护的单向性,接入、接出配置只能二选一">
		<td colspan=2>
			<b><label>组织架构接入配置（钉钉到EKP）</label></b>
			<label>
				<html:checkbox property="value(kmss.third.ding.oms.in.enabled)" value="true"  onclick="config_ding_oms_in_chgDisplay()"/>启用
			</label>
		</td>
	</tr>
	<tr class="ding_conifg_oms_in">
		<td class="td_normal_title" width="15%">是否同步钉钉部门</td>
		<td>
			<html:checkbox property="value(kmss.third.ding.oms.in.dept.enabled)" value="true"  />
			<span class="message">开启</span>
		</td>
	</tr>
	<tr class="ding_conifg_oms_in">
		<td class="td_normal_title" width="15%">EKP中根机构ID</td>
		<td>
			<xform:text property="value(kmss.third.ding.in.org.id)" subject="EKP中根机构ID" 
				required="false" style="width:85%" showStatus="edit" /><span class="txtstrong">*</span><br>
			<span class="message">将钉钉部门同步到EKP时，指定钉钉根部门同步到EKP哪个根机构之下，为空则同步到最顶层</span>
		</td>
	</tr>

</table>
