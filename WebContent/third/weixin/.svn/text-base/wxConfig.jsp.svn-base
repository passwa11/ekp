<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
function config_wx_chgDisplay(){
	var tbObj = document.getElementById("kmss.third.wx");
	var field = tbObj.rows[0].getElementsByTagName("INPUT")[0];
	var  todo= document.getElementsByName("value(kmss.third.wx.todo.enabled)")[0];
	var omsIn = document.getElementsByName("value(kmss.third.wx.oms.out.enabled)")[0];
	if(!field.checked){
		todo.checked = false;
		omsIn.checked = false;
	}
	for(var i=1; i<tbObj.rows.length; i++){
		tbObj.rows[i].style.display = field.checked?"":"none";
	}

	 config_wx_callbackurl();
	 config_wx_notify_type();
}

function  config_wx_notify_type(){
	var type = document.getElementsByName("value(kmss.third.wx.notify.type)"),
		 checked = false;
	for(var i = 0; i < type.length; i++) {
		if(type[i].checked) {
			checked = true;
			break;
		}
	}
	if(!checked){
		type[0].checked = true;
	}
}

function config_wx_callbackurl(){
	document.getElementsByName("value(kmss.third.wx.callbackurl)")[0].value=
		document.getElementsByName("value(kmss.third.wx.domain)")[0].value+
		document.getElementsByName("wx_url_suffix")[0].value;
}

function config_wx_dns_getUrl(){
	var url = location.href;
	var i = url.indexOf("admin.do");
	var rtnUrl = document.getElementsByName("value(kmss.third.wx.domain)")[0];
	rtnUrl.value = url.substring(0, i-1);
}

function config_randomAlphanumeric(charsLength,chars) { 
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

//config_addOnloadFuncList(config_wx_chgDisplay);
</script>
<table class="tb_normal" width=100% id="kmss.third.wx" style="display: none;">
	<tr>
		<td class="td_normal_title" colspan=2>
			<b>
				<label>
					<html:checkbox property="value(kmss.third.wx.enabled)" value="true" onclick="config_wx_chgDisplay()"/>集成微信企业号
				</label>
			</b>
		</td>
	</tr>

	<tr>
		<td class="td_normal_title" width="15%">微信企业号CorpID</td>
		<td>
			<xform:text property="value(kmss.third.wx.corpid)" subject="微信企业号微应用中指定的企业ID" 
				required="false" style="width:85%" showStatus="edit"/><span class="txtstrong">*</span><br>
			<span class="message">从微信企业号后台-->设置-->权限管理-->选择某个分组后会出现CorpID，将此CorpID拷贝粘贴至此</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">微信企业号Secret</td>
		<td>
			<xform:text property="value(kmss.third.wx.corpsecret)" subject="微信企业号微应用中指定的企业secret" 
				required="false" style="width:85%" showStatus="edit" /><span class="txtstrong">*</span><br>
			<span class="message">从微信企业号后台-->设置-->权限管理-->选择某个分组后会出现Secret，将此Secret拷贝粘贴至此</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">回调URL</td>
		<td>
			<input type="hidden" name="wx_url_suffix" value="/resource/third/wx/cpEndpoint.do?method=service">
			<xform:text property="value(kmss.third.wx.callbackurl)" subject="微信企业号微应用中回调URL" 
				required="false" style="width:85%" showStatus="readOnly" /><br>
			<span class="message">从微信企业号后台-->应用中心-->编辑所选应用-->回调模式-->修改，将此处生成的回调URL拷贝后，粘贴到对应的URL中</span>
		</td>
	</tr>

	<tr>
		<td class="td_normal_title" width="15%">回调Token</td>
		<td>
			<xform:text property="value(kmss.third.wx.token)" subject="微信企业号微应用中指定的企业token" 
				required="false" style="width:85%" showStatus="edit" /><span class="txtstrong">*</span>
				<input type="button" class="btnopt" value="随机生成" onclick="document.getElementsByName('value(kmss.third.wx.token)')[0].value=config_randomAlphanumeric(16)"/><br>
			<span class="message">从微信企业号后台-->应用中心-->编辑所选应用-->回调模式-->修改，将此处生成的企业Token拷贝后，粘贴到对应的Token中</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">回调EncodingAESKey</td>
		<td>
			<xform:text property="value(kmss.third.wx.aeskey)" subject="微信企业号微应用中指定的企业aeskey" 
				required="false" style="width:85%" showStatus="edit" /><span class="txtstrong">*</span>
			<input type="button" class="btnopt" value="随机生成" onclick="document.getElementsByName('value(kmss.third.wx.aeskey)')[0].value=config_randomAlphanumeric(43)"/><br>
			<span class="message">从微信企业号后台-->应用中心-->编辑所选应用-->回调模式-->修改，将此处生成的回调EncodingAESKey拷贝后，粘贴到对应的EncodingAESKey中</span>
			<br>
			<span class="txtstrong">注意： 如果是配置了回调Token，和EncodingAESKey保存，重起应用，再进行微信企业号后台回调模式的配置。多个应用需要使用相同的Token，和EncodingAESKey</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">待办消息在微信企业号微应用agentid</td>
		<td>
			<xform:text property="value(kmss.third.wx.agentid)" subject="待办消息在微信企业号微应用agentid" 
				required="false" style="width:85%" showStatus="edit" />
				<br>
			<span class="message">微信企业号微应用agentid，微信企业号消息发送时，以及发布菜单时必选</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">待办消息类型</td>
		<td>
			<xform:radio property="value(kmss.third.wx.notify.type)" subject="待办消息类型" showStatus="edit" >
				<xform:simpleDataSource value="news">图文型</xform:simpleDataSource>
				<xform:simpleDataSource value="text">文本型</xform:simpleDataSource>
			</xform:radio>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">EKP使用微信企业号开放授权</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.third.wx.oauth2.enabled)" value="true"/>启用
			</label>
			<br>
			<span class="message">EKP使用微信企业号开放授权，免登录使用EKP移动应用</span>
		</td>
	</tr>

	<tr>
		<td class="td_normal_title" width="15%">微信企业号通知:</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.third.wx.todo.enabled)" value="true"/>启用
			</label>
		</td>
	</tr>
	
	<tr>
		<td class="td_normal_title" width="15%">微信企业号待阅推送:</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.third.wx.todo.type2.enabled)" value="true"/>启用
			</label>
			<br>
			<span class="message">默认EKP待阅类消息不会推送到微信企业号，可选</span>
		</td>
	</tr>

	<tr>
		<td class="td_normal_title" width="15%">微信企业号微应用首页地址中设置的域名</td>
		<td>
			<xform:text property="value(kmss.third.wx.domain)" subject="微信企业号微应用首页地址中设置的域名" 
				required="false" style="width:85%" showStatus="edit" onValueChange="config_wx_callbackurl()"/>
				<input type="button" class="btnopt" value="自动获取" onclick="config_wx_dns_getUrl();config_wx_callbackurl()"/><br>
			<span class="message">可选，不设置为EKP应用所在域名</span>
			<br>
			<span class="txtstrong">注意：域名变动后 ，微信企业号后台回调模式URL也需要修改</span>
		</td>
	</tr>

	<tr>
		<td class="td_normal_title" width="15%">组织架构数据同步（接出到微信企业号）</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.third.wx.oms.out.enabled)" value="true"/>启用
			</label>
			<br>
			<span class="message">EKP组织机构同步到微信企业号配置</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">EKP中根机构ID</td>
		<td>
			<xform:text property="value(kmss.third.wx.org.id)" subject="EKP中根机构ID" 
				required="false" style="width:85%" showStatus="edit" /><span class="txtstrong">*</span><br>
			<span class="message">将EKP中的组织机构同步到微信企业号时，指定只同步EKP中某个机构或部门下的所有成员到微信企业号，多个机构ID可以用;分隔</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">是否同步根机构到微信企业号</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.third.wx.oms.root.flag)" value="true"/>同步根机构
			</label>
			<span class="message">指定是否同步根机构到微信企业号，需要和EKP中根机构ID中指定的值一同使用，可选，默认不同步</span>
		</td>
	</tr>

</table>
