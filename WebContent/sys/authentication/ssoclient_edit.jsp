<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/config/resource/edit_top.jsp"%>
<%@page import="com.landray.kmss.sys.config.action.SysConfigAdminUtil"%>
<%
	if (!SysConfigAdminUtil.validateUser(request)) {
		//request.getSession().setAttribute("VALIDATION_CODE", IDGenerator.generateID());
		//request.getRequestDispatcher("/sys/config/login.jsp").forward(request,response);
		response.sendRedirect(request.getContextPath()+"/admin.do");
		return;
	}
%>
<script>
	Com_IncludeFile("dialog.js");
	var saving = false;
	<%-- 提交表单 --%>
	function submitForm(){
		if(saving)
			return;

		var type = getFieldValue("instance.class"); // 获取 “ 密钥类型 ” 单选按钮选中值
		var fdConfig = "";
		var fdTokenKey = "instance.class="+type;
		if(type=="BAMTokenGenerator"){ // 竹云BAM系统的Oauth Token

			var bam_oauth_config = getTBTokenKeyInfo("TB_BAMTokenGenerator");
			fdConfig = "log.level = WARN\r\nfilter.chain = BamOauthFilter";
			fdConfig = fdConfig + bam_oauth_config;

		}else if(type=="LandingGenerator"){ // 竹云BAM系统的Oauth Token

			var landing_oauth_config = getTBTokenKeyInfo("TB_LANDING");
			fdConfig = "log.level = WARN\r\nfilter.chain = LandingOauthFilter\r\nTokenFilter.keyFilePath = /LRToken";
			fdConfig = fdConfig + landing_oauth_config;

		} else {

			var value = parseInt(getFieldValue("cookie.maxAge"));
			if(isNaN(value) || value<1){
				alert("有效期必填，并且必须为正整数！");
				return;
			}

			var value = parseInt(getFieldValue("cookie.maxAge.redis"));
			if( value && (isNaN(value) || value<1)){
				alert("使用redis存储有效期  并且必须为正整数！");
				return;
			}

			var commonConfig = getTBTokenKeyInfo("TB_Common");
			if(commonConfig==null)
				return;
			fdTokenKey += commonConfig;

			generatorConfig = getTBTokenKeyInfo("TB_"+type);
			if(generatorConfig==null)
				return;
			fdTokenKey += generatorConfig;
			fdConfig = "log.level = WARN\r\nfilter.chain = TokenFilter\r\nTokenFilter.keyFilePath = /LRToken\r\nTokenFilter.logoutURL = /logout.jsp\r\n";
		}


		saving = true;

		var data = new KMSSData();
		data.AddHashMap({fdConfig:fdConfig, fdTokenKey:fdTokenKey});
		data.SendToUrl(Com_Parameter.ContextPath+"sys/authentication/ssoclient.do?method=save", function(http){
			var result = eval("("+http.responseText+")");
			if(result.error!=null){
				alert(result.error);
				saving = false;
				return;
			}
			//top.returnValue = true;
			myReturnValue(true);
			window.close();
		});
	}


	function myReturnValue(value) {
		if (navigator.userAgent.indexOf("Chrome") > 0 || navigator.userAgent.indexOf("Firefox")>0) {
			window.opener.loadMessage(window.opener, value);
		}
		else {
			top.returnValue = value;
		}
	}


	<%-- 获取一个tbody中的配置信息 --%>
	function getTBTokenKeyInfo(id){
		var obj = document.getElementById(id);
		var fields = obj.getElementsByTagName("INPUT");
		var rtnVal = "";
		var value;
		for(var i=0; i<fields.length; i++){
			if(fields[i].type!="text" && fields[i].type!="password")
				continue;
			value = Com_Trim(fields[i].value);
			if(value=="" && fields[i].name!="domino.user.key" && fields[i].name!="cookie.maxAge.redis"){
				alert("请完整填写所有必填项，再执行提交操作！");
				return null;
			}
			rtnVal += "\r\n"+fields[i].name + "=" + value;
		}
		if(id=="TB_Common"){
			rtnVal += "\r\n"+"cookie.delete.only.my" + "=" + getFieldValue("cookie.delete.only.my");
		}
		return rtnVal;
	}

	<%-- window onload事件，初始化所有值，并且更新页面显示 --%>
	window.onload = function(){
		var values = getFieldValue("fdTokenKey").split("\n");;
		for(var i=0; i<values.length; i++){
			var index = values[i].indexOf("=");
			if(index==-1)
				continue;
			setFieldValue(Com_Trim(values[i].substring(0, index)), Com_Trim(values[i].substring(index+1)));
		}
		values = getFieldValue("fdConfig").split("\n");;
		for(var i=0; i<values.length; i++){
			var index = values[i].indexOf("=");
			if(index==-1)
				continue;
			setFieldValue(Com_Trim(values[i].substring(0, index)), Com_Trim(values[i].substring(index+1)));
		}
		changeDisplay();
	};

	<%-- 获取某个字段的值 --%>
	function getFieldValue(fieldName){
		var fields = document.getElementsByName(fieldName);
		if(fields.length==0)
			return null;
		if(fields[0].type=="radio"){
			for(var i=0; i<fields.length; i++){
				if(fields[i].checked)
					return fields[i].value;
			}
		}else{
			return Com_Trim(fields[0].value);
		}
	}

	<%-- 设置某个字段的值 --%>
	function setFieldValue(fieldName, fieldValue){
		var fields = document.getElementsByName(fieldName);
		if(fields.length==0)
			return;
		if(fields[0].type=="radio"){
			for(var i=0; i<fields.length; i++){
				if(fields[i].value==fieldValue){
					fields[i].checked = true;
					break;
				}
			}
		}else{
			fields[0].value = fieldValue;
		}
	}

	<%-- 更新页面显示 --%>
	function changeDisplay(){
		var value = getFieldValue("instance.class");  // 获取 “ 密钥类型 ” 单选按钮选中值
		var TB_LtpaTokenGenerator = document.getElementById("TB_LtpaTokenGenerator"); // 获取 “ Domino系统的LtpaToken ” 对应tbody的Dom对象
		var TB_LRTokenGenerator = document.getElementById("TB_LRTokenGenerator");     // 获取 “ EKP系统的LRToken ” 对应tbody的Dom对象
		var TB_BAMTokenGenerator = document.getElementById("TB_BAMTokenGenerator");   // 获取 “ 竹云BAM系统的Oauth Token ” 对应tbody的Dom对象
		var TB_LANDING = document.getElementById("TB_LANDING");
		var TB_Common = document.getElementById("TB_Common");   // 获取公用配置（作用域、有效期）对应tbody的Dom对象
		//var BTN_Domain = document.getElementById("BTN_Domain"); // 获取作用域 “ 自动获取  ” 对应button按钮的Dom对象
		//var SEL_MaxAge = document.getElementById("SEL_MaxAge"); // 获取有效期 “ 快速选中  ” 对应select下拉框的Dom对象
		var BTN_GenKeyDomino = document.getElementById("BTN_GenKeyDomino"); // 获取Domino配置对应导入button按钮的Dom对象

		if(value=="LRTokenGenerator"){ // EKP系统的LRToken
			TB_LRTokenGenerator.style.display = "";
			TB_LtpaTokenGenerator.style.display = "none";
			TB_BAMTokenGenerator.style.display = "none";
			TB_Common.style.display = "";
			//BTN_Domain.style.display = "";
			//SEL_MaxAge.style.display = "";
			BTN_GenKeyDomino.style.display = "none";
			TB_LANDING.style.display = "none";
		}else if(value=="LtpaTokenGenerator"){ // Domino系统的LtpaToken
			TB_LtpaTokenGenerator.style.display = "";
			TB_LRTokenGenerator.style.display = "none";
			TB_BAMTokenGenerator.style.display = "none";
			TB_Common.style.display = "";
			//BTN_Domain.style.display = "none";
			//SEL_MaxAge.style.display = "none";
			BTN_GenKeyDomino.style.display = "";
			TB_LANDING.style.display = "none";
		}else if(value=="BAMTokenGenerator"){ // 竹云BAM系统的OauthToken
			TB_BAMTokenGenerator.style.display = "";
			TB_LtpaTokenGenerator.style.display = "none";
			TB_LRTokenGenerator.style.display = "none";
			BTN_GenKeyDomino.style.display = "none";
			TB_Common.style.display = "none";
			TB_LANDING.style.display = "none";
		}else if(value=="LandingGenerator"){ // 蓝桥
			TB_BAMTokenGenerator.style.display = "none";
			TB_LtpaTokenGenerator.style.display = "none";
			TB_LRTokenGenerator.style.display = "none";
			BTN_GenKeyDomino.style.display = "none";
			TB_Common.style.display = "none";
			TB_LANDING.style.display = "";
		}
	}

	<%-- 自动获取作用域 --%>
	function getDomain(){
		<%-- 获取DNS --%>
		var url = window.dialogArguments.localUrl;
		var index = url.indexOf("//");
		if(index==-1){
			url = location.href;
			index = url.indexOf("//");
		}
		url = url.substring(index+2);
		index = url.indexOf("/");
		if(index>-1){
			url = url.substring(0, index);
		}
		index = url.indexOf(":");
		if(index>-1){
			url = url.substring(0, index);
		}
		<%-- 判断是否为DNS --%>
		var s = url.split(".");
		if(s.length==1){
			alert("无法自动获取作用域，请在“服务器DNS”配置选项中设置为域名访问方式！");
			return;
		}
		var isIP = s.length==4;
		if(isIP){
			for(var i = 0; i<4; i++){
				var n = parseInt(s[i]);
				if(isNaN(n) || n<0 || n>255){
					isIP = false;
					break;
				}
			}
		}
		if(isIP){
			alert("无法自动获取作用域，请在“服务器DNS”配置选项中设置为域名访问方式！");
			return;
		}
		<%-- 写值 --%>
		setFieldValue("cookie.domain", url.substring(url.indexOf(".")+1));
	}

	<%-- 生成公钥和私钥 --%>
	function genKeyPair(){
		var data = new KMSSData();
		data.SendToUrl(Com_Parameter.ContextPath+"sys/authentication/ssoclient.do?method=genKeyPair", function(http){
			var result = eval("("+http.responseText+")");
			if(result.error!=null){
				alert(result.error);
				return;
			}
			for(var item in result){
				setFieldValue(item, result[item]);
			}
		});
	}


	<%-- 生成LTPAToken秘钥 --%>
	function genLTPATokenKey(){
		var data = new KMSSData();
		data.SendToUrl(Com_Parameter.ContextPath+"sys/authentication/ssoclient.do?method=genLTPATokenKey", function(http){
			var result = eval("("+http.responseText+")");
			if(result.error!=null){
				alert(result.error);
				return;
			}
			for(var item in result){
				setFieldValue(item, result[item]);
			}
		});
	}

	<%-- 导入Domino的信息 --%>
	function genKeyDomino(){
		var url = Com_Parameter.ContextPath+"sys/authentication/ssoclient_domino.jsp";
		var result = Dialog_PopupWindow(url, 600, 300, dialogArguments);
		if(result==null){
			return;
		}
		for(var item in result){
			setFieldValue(item, result[item]);
		}
	}
</script>
<center>
	<table class="tb_normal" width=95%>
		<tr>
			<td class="td_normal_title" width=15%>
				密钥类型
			</td><td width=85%>
			<label><input type="radio" name="instance.class" onclick="changeDisplay();" value="LtpaTokenGenerator" checked>Domino系统的LtpaToken</label>
			<label><input type="radio" name="instance.class" onclick="changeDisplay();" value="LRTokenGenerator">EKP系统的LRToken</label>
			<label><input type="radio" name="instance.class" onclick="changeDisplay();" value="BAMTokenGenerator">竹云BAM系统的Oauth Token</label>
			<label><input type="radio" name="instance.class" onclick="changeDisplay();" value="LandingGenerator">蓝桥系统</label>
		</td>
		</tr>
		<tbody id="TB_Common">
		<tr>
			<td class="td_normal_title" width=15%>
				作用域
			</td><td width=85%>
			<input name="cookie.domain" value="" class="inputsgl" style="width: 320px;">
			<span class="txtstrong">*</span>
			<input id="BTN_Domain" type="button" value="自动获取" class="btnopt" onclick="getDomain();"><br>
			<br style="font-size:5px;">
			如：landray.com.cn表示生成的令牌环对所有DNS为*.landray.com.cn的服务器都有效。
		</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				有效期
			</td><td width=85%>
			<input name="cookie.maxAge" value="" class="inputsgl">秒
			<span class="txtstrong">*</span>
			<select id="SEL_MaxAge" onchange="if(value!='') setFieldValue('cookie.maxAge', parseInt(value)*3600);">
				<option value="">快速选择</option>
				<option value="1">1小时</option>
				<option value="2">2小时</option>
				<option value="3">3小时</option>
				<option value="4">4小时</option>
				<option value="6">6小时</option>
				<option value="12">12小时</option>
			</select><br>
			<br style="font-size:5px;">
			令牌环有效期，一旦登录后，超过此时间的所有令牌将失效，需重新登录。
		</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				使用redis存储有效期
			</td><td width=85%>
			<input name="cookie.maxAge.redis" value="" class="inputsgl">秒
			<select id="SEL_MaxAge_redis" onchange="if(value!='') setFieldValue('cookie.maxAge.redis', parseInt(value)*60);">
				<option value="">快速选择</option>
				<option value="10">10分钟</option>
				<option value="20">20分钟</option>
				<option value="30">30分钟</option>
				<option value="60">60分钟</option>
				<option value="120">120分钟</option>
				<option value="240">240分钟</option>
				<option value="480">480分钟</option>
			</select><br>
			<br style="font-size:5px;">
			使用该功能，必须先启用启用Redis缓存，不配置的话表示不使用该功能。用户每次访问时，会延长该有效期，类似于session的有效期。当从redis取到有效期时，以该有效期为准，否则以上面的令牌环有效期为准。
		</td>

		<tr>
			<td class="td_normal_title" width=15%>
				注销时只删除本系统生成的token
			</td><td width=85%>
			<label><input type="radio" name="cookie.delete.only.my" value="false">否</label>
			<label><input type="radio" name="cookie.delete.only.my" value="true">是</label>
			<br style="font-size:5px;">
			如：启用该项后，在系统A登录后，系统A生成token到cookie中，并单点到ekp，ekp注销的时候不删除该cookie。（默认不启用该功能）
		</td>
		</tr>

		</tr>
		</tbody>
		<tbody id="TB_LRTokenGenerator">
		<tr>
			<td class="td_normal_title" width=15%>
				令牌环名称
			</td><td width=85%>
			<input name="cookie.name" value="" class="inputsgl" style="width: 320px;">
			<span class="txtstrong">*</span>
			<input type="button" value="默认值" class="btnopt" onclick="setFieldValue('cookie.name', 'LRToken');"><br>
			<br style="font-size:5px;">
			若您需要将同一作用域下的不同的SSO服务器组进行区分，请设置不同的令牌环名称。
		</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				密钥
			</td><td width=85%>
			公钥：<input name="security.key.public" value="" class="inputsgl" style="width: 284px;">
			<span class="txtstrong">*</span><br>
			私钥：<input name="security.key.private" value="" class="inputsgl" style="width: 284px;">
			<span class="txtstrong">*</span>
			<input type="button" value="生成密钥" class="btnopt" onclick="genKeyPair();"><br>
			<br style="font-size:5px;">
			一旦公钥和私钥被泄露，其它系统很容易伪造令牌环，因此请妥善保管好。
		</td>
		</tr>
		</tbody>
		<tbody id="TB_LtpaTokenGenerator">
		<tr>
			<td class="td_normal_title" width=15%>
				令牌环名称
			</td><td width=85%>
			<input name="domino.cookie.name" value="" class="inputsgl" style="width: 320px;">
			<span class="txtstrong">*</span>
			<input type="button" value="默认值" class="btnopt" onclick="setFieldValue('domino.cookie.name', 'LtpaToken');">
			<br style="font-size:5px;">
			若与Domino系统集成，请将该值设置为LtpaToken。
		</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				密钥
			</td><td width=85%>
			<input name="domino.security.key" value="" class="inputsgl" style="width: 320px;">
			<span class="txtstrong">*</span>
			<input type="button" value="生成密钥" class="btnopt" onclick="genLTPATokenKey();">
			<br style="font-size:5px;">
			一旦公钥和私钥被泄露，其它系统很容易伪造令牌环，因此请妥善保管好。
		</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				登录名关键字
			</td><td width=85%>
			<input name="domino.user.key" value="" class="inputsgl" style="width: 320px;"><br>
			<br style="font-size:5px;">
			一般的，Domino会使用形如“CN=yezq/O=Landray”的格式表示当前用户信息，而最容易被各个系统识别的用户信息为登录名。
			因此，需要从Domino的用户名表达式中获取登录名的信息，您可以在上面填写“CN”这个参数，说明从CN对应的值就是登录名。
			您也可以不设置该参数，此时，系统自动会获取到Domino表达式中的第一项值作为用户登录名。也可以设置多个关键字，以;分隔。
		</td>
		</tr>
		</tbody>
		<tbody id="TB_BAMTokenGenerator">
		<tr>
			<td class="td_normal_title" width=15%>
				认证系统URL
			</td><td width=85%>
			<input name="BamOauthFilter.oauth.url" value="" class="inputsgl" style="width: 320px;">
			<span class="txtstrong">*</span>
			<br style="font-size:5px;">
			竹云身份认证系统的URL，例如：http://bam.boocloud.com/idp
		</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				Client Id
			</td><td width=85%>
			<input name="BamOauthFilter.oauth.clientId" value="" class="inputsgl" style="width: 320px;">
			<span class="txtstrong">*</span>
			<br style="font-size:5px;">
			Oauth协议标准要求的客户端应用ID，例如：landray
		</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				Client Secret
			</td><td width=85%>
			<input name="BamOauthFilter.oauth.clientSecret" value="" class="inputsgl" style="width: 320px;">
			<span class="txtstrong">*</span>
			<br style="font-size:5px;">
			Oauth协议标准要求的客户端应用秘钥，例如：5980dd276cfe4d7fba73ae6f50ca7a26
		</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				注意事项
			</td><td width=85% >
			<span style="color:coral;">要启用竹云BAM单点登录，必须在配置完此页的各必填项之后同时勾选《 未登录时跳转 》,并配置《 未登录时跳转地址 》和《 用户注销时跳转地址 》，配置时分别复制下列配置示例，将其中的[]参数表达式根据环境的实际值进行替换</span>
			<br/>
			<br/><strong>未登录时跳转地址 配置示例:</strong><br/>
			http://<span style="color:#1b83d8;">[竹云BAM系统地址]</span>/oauth2/authorize?redirect_uri=\${URL}</span>&state=login&client_id=<span style="color:#1b83d8;">\[Client Id]</span>&response_type=code
			<br/>
			<br/><strong>用户注销时跳转地址 配置示例：</strong><br/>
			http://<span style="color:#1b83d8;">\[竹云BAM系统地址]</span>/profile/OAUTH2/Redirect/GLO?redirctToUrl=<span style="color:#1b83d8;">[EKP系统地址]</span>&redirectToLogin=true&entityId=<span style="color:#1b83d8;">[Client Id]</span>
		</td>
		</tr>
		</tbody>


		<tbody id="TB_LANDING">
		<tr>
			<td class="td_normal_title" width=15%>
				蓝桥系统URL
			</td><td width=85%>
			<input name="landing.oauth.url" value="" class="inputsgl" style="width: 320px;">
			<span class="txtstrong">*</span>
			<br style="font-size:5px;">
			蓝桥系统的URL，例如：http://landing.test.com
		</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				认证账号
			</td><td width=85%>
			<input name="landing.oauth.account" value="" class="inputsgl" style="width: 320px;">
			<br style="font-size:5px;">
			蓝桥中“获取SSO用户信息服务”接口的认证账号
		</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				认证密码
			</td><td width=85%>
			<input type="password" name="landing.oauth.password" value="" class="inputsgl" style="width: 320px;">
			<br style="font-size:5px;">
			蓝桥中“获取SSO用户信息服务”接口的认证密码
		</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				注意事项
			</td><td width=85% >
			<span style="color:coral;">要启用蓝桥单点登录，必须在配置完此页的各必填项之后同时勾选《 未登录时跳转 》,并配置《 未登录时跳转地址 》，配置时分别复制下列配置示例，将其中的【】参数表达式根据环境的实际值进行替换</span>
			<br/>
			<br/><strong>未登录时跳转地址 配置示例:</strong><br/>
			<span style="color:#1b83d8;">【蓝桥系统地址】</span>/lding/oapi/lding_oapi_sso/ldingOapiSSO.do?method=auth&sourceUrl=\${URL}
			<br/>

			<strong>用户注销时跳转地址 配置示例:</strong><br/>
			<span style="color:#1b83d8;">【蓝桥系统地址】</span>/lding/oapi/lding_oapi_sso/ldingOapiSSO.do?method=loginOut&sourceUrl=<span style="color:#1b83d8;">【EKP系统地址】</span>
			<br/>

		</td>
		</tr>
		</tbody>

	</table>
	<br>
	<input id="BTN_GenKeyDomino" type="button" value="导入" class="btnopt" onclick="genKeyDomino();">&nbsp;&nbsp;
	<input type="button" value="提交" class="btnopt" onclick="submitForm();">&nbsp;&nbsp;
	<input type="button" value="关闭" class="btnopt" onclick="window.close();">
	<textarea name="fdTokenKey" style="display:none;"><c:out value="${fdTokenKey}" /></textarea>
	<textarea name="fdConfig" style="display:none;"><c:out value="${fdConfig}" /></textarea>
</center>
<br>
</body>
</html>