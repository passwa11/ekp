<%@page import="com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ thirdWeixinWorkForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.thirdWeixinWorkForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ thirdWeixinWorkForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.thirdWeixinWorkForm, 'save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.thirdWeixinWorkForm, 'saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<html:form action="/third/weixin/mutil/third_weixin_work/thirdWeixinWork.do">
 
<p class="txttitle"><bean:message bundle="third-weixin-work" key="table.thirdWeixinWork"/></p>
<%
	String wxdomain= WeixinMutilConfig.newInstance(null).getWxDomain();
	request.setAttribute("wxdomain", wxdomain);

	String fdWxKey = (String)request.getAttribute("fdWxKey");
	request.setAttribute("fdWxKey", fdWxKey);

%>

<center>
			<table class="tb_normal" width=95%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdName"/>
		</td><td width="35%">
			<xform:text property="fdName" style="width:65%" required="true"/>
			<%-- <div class="message">在管理后台企业应用-->自建应用，将对应的应用名称拷贝粘贴至此（先创建应用）</div> --%>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdModelName"/>
		</td>
		<td width="35%">
			<xform:text property="fdModelName" style="width:50%"/>
			<xform:text property="fdUrlPrefix" style="display: none;"/>
			<a onclick="selectModule();return false;" href=""><bean:message key="dialog.selectOther" /></a>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdAgentid"/>
		</td><td width="35%">
			<xform:text property="fdAgentid" style="width:85%" required="true" onValueChange="config_wx_callbackurl();"/>
			<%-- <div class="message">在管理后台企业应用-->自建应用，点击对应的应用，把对应的AgentId拷贝粘贴至此（先创建应用）</div> --%>
		</td>
		<!-- 所属企业微信 -->
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWxWorkConfig.fdKey"/>
		</td><td width="35%">
			<xform:text property="fdWxKey" style="width:85%" required="true" value="${fdWxKey}"  showStatus="readOnly" onValueChange="config_wx_callbackurl();"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdSecret"/>
		</td><td width="85%"  colspan="3">
			<xform:text property="fdSecret" style="width:85%" required="true"/>
			<%-- <div class="message">在管理后台企业应用-->自建应用，点击对应的应用，把对应的Secret拷贝粘贴至此（先创建应用）</div> --%>
		</td>
	</tr>
	<%-- <tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdType"/>
		</td><td width="85%">
			<xform:radio property="fdType" required="true">
	       		<xform:enumsDataSource enumsType="third_weixin_work_type" />
	      	</xform:radio>
	      	<div class="message">在管理后台企业应用-->自建应用，点击对应的应用，如果设置工作台应用主页则是主页型，如果设置接收消息则是消息型</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdSystemUrl"/>
		</td><td width="85%">
			<xform:text property="fdSystemUrl" subject="微应用首页地址中设置的域名" required="true" style="width:85%" showStatus="edit" onValueChange="config_wx_callbackurl();"/>&nbsp;&nbsp;
			<ui:button text="自动获取" onclick="config_wx_dns_getUrl();config_wx_callbackurl();" style="vertical-align: top;"></ui:button>	
			<div class="message">在管理后台企业应用-->自建应用，点击对应的应用，点击自动获取将此处生成系统地址，粘贴到网页授权及JS-SDK的配置中（去掉协议）</div>
		</td>
	</tr> --%>
	<tr style="display:none;">
		<td class="td_normal_title" width=15% colspan="4" align="center">
			<b><bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.receivedmessage.config"/></b>
			<div style="font-size: 10xp"><font color="red"><bean:message bundle="third-weixin-mutil" key="third.weixin.work.note"/></font></div>
		</td>
	</tr>
	<tr style="display:none;">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdCallbackUrl"/>
		</td><td width="85%"  colspan="3">
			<input type="hidden" name="wxUrlSuffix" value="/resource/third/wxwork/mutil/cpEndpoint.do?method=service&agentId=">
			<xform:text property="fdCallbackUrl" subject="URL" required="false" style="width:85%" showStatus="readOnly" />
			<%-- <div class="message">在管理后台企业应用-->自建应用，点击对应的应用，在应用配置下面的接收消息下点击设置，将此处生成的回调URL拷贝后，粘贴到对应的URL中</div> --%>
		</td>
	</tr>
	<tr style="display:none;">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdToken"/>
		</td><td width="85%"  colspan="3">
			<xform:text property="fdToken" subject="Token" required="false" style="width:85%" showStatus="edit"/>
			<ui:button text='${lfn:message("third-weixin-mutil:third.weixin.work.config.wxTokenBtn") }' onclick="window.random_token();" style="vertical-align: top;"></ui:button>
			<%-- <div class="message">在管理后台企业应用-->自建应用，点击对应的应用，在应用配置下面的接收消息下点击设置，将此处生成的回调Token拷贝后，粘贴到对应的Token中(主页型可不用设置)</div> --%>
		</td>
	</tr>
	<tr style="display:none;">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdAeskey"/>
		</td><td width="85%"  colspan="3">
			<xform:text property="fdAeskey" subject='${lfn:message("third-weixin-mutil:third.weixin.work.config.wxAeskeySubject") }' required="false" style="width:85%" showStatus="edit"/>
			<ui:button text='${lfn:message("third-weixin-mutil:third.weixin.work.config.wxTokenBtn") }' onclick="window.random_AESKey();" style="vertical-align: top;"></ui:button>
			<%-- <div class="message">在管理后台企业应用-->自建应用，点击对应的应用，在应用配置下面的接收消息下点击设置，将此处生成的回调EncodingAESKey拷贝后，粘贴到对应的EncodingAESKey中(主页型可不用设置)</div>
			<div class="txtstrong">注意：多个应用需要使用相同的Token，和EncodingAESKey</div> --%>
		</td>
	</tr>
</table>  
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />

<script>
	var validation = $KMSSValidation();
	//生成随机Token
	function random_token(){
		$("[name='fdToken']").val( config_randomAlphanumeric(16) ); 
	}
	//生成随机AESKey
	function random_AESKey(){
		$("[name='fdAeskey']").val( config_randomAlphanumeric(43) ); 
	}
	//生成随机码
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
	//设置微信回调URL
	function config_wx_callbackurl(){
		var fdAgentid = $("[name='fdAgentid']").val();
		var fdWxKey = $("[name='fdWxKey']").val();
		if(fdAgentid!=""){
			var wxDomain = '${wxdomain}';
			var wxUrlSuffix = $("[name='wxUrlSuffix']").val();
			$('[name="fdCallbackUrl"]').val(wxDomain + wxUrlSuffix + fdAgentid + "&key=" + fdWxKey);
		}
	}
	function config_wx_dns_getUrl(){
		var protocol = location.protocol,
			host = location.host,
			contextPath = seajs.data.env.contextPath;
		$('[name="fdSystemUrl"]').val(protocol + '//' + host + contextPath );
	}
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = subCheck;
	function subCheck(){
		var flag = true;
		$('[name="fdAgentid"]').val($.trim($('[name="fdAgentid"]').val()));
		$('[name="fdSecret"]').val($.trim($('[name="fdSecret"]').val()));
		$('[name="fdToken"]').val($.trim($('[name="fdToken"]').val()));
		$('[name="fdAeskey"]').val($.trim($('[name="fdAeskey"]').val()));
		return flag;
	} 
</script>
<script>
	Com_IncludeFile("calendar.js|dialog.js|doclist.js|jquery.js|json2.js");
	//选择模块
	function selectModule(){
		Dialog_List(true, "fdUrlPrefix", "fdModelName", null, "pdaModuleSelectDialog",afterModuleSelect,null,null,null,
				"<bean:message bundle='third-pda' key='pdaModuleConfigMain.moduleSelectDilog'/>");
	}
	function afterModuleSelect(dataObj){
		if(dataObj==null)
			return ;
		var rtnData = dataObj.GetHashMapArray();
		if(rtnData[0]==null)
			return;
		if(rtnData[0]["countUrl"]!=null)
			$('input[name="fdCountUrl"]').val(rtnData[0]["countUrl"]);
		else
			$('input[name="fdCountUrl"]').val('');
		for(var key in rtnData[0]){
			if(key.indexOf('dynamicMap_') > -1){
				var name = key.replace('_','(').replace('_',')'),
					element = $('[name="'+ name +'"]');
				if(element && element.length > 0){
					element.val( rtnData[0][key]);
				}
			}
		}
	}
</script>
</html:form>

	</template:replace>
</template:include>