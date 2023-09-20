<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
			<kmss:auth requestURL="/third/weixin/mutil/third_weixin_work/thirdWeixinWork.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('thirdWeixinWork.do?method=edit&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/third/weixin/mutil/third_weixin_work/thirdWeixinWork.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.delete') }" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdWeixinWork.do?method=delete&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<kmss:authShow roles="ROLE_THIRDWXWORK_ADMIN">
				<c:if test="${empty fdMenuId }">
					<ui:button text="${ lfn:message('third-weixin-mutil:third.wx.menu.btn.wxwork.add') }" onclick="Com_OpenWindow('${LUI_ContextPath}/third/wxwork/mutil/menu/wxworkMenuDefine.do?method=add&fdAppId=${param.fdId}&fdWxKey=${thirdWeixinWorkForm.fdWxKey }');"></ui:button>
				</c:if>
				<c:if test="${!empty fdMenuId }">
					<c:if test="${fdMenuPuslished!='1' }">
						<ui:button text="${ lfn:message('third-weixin-mutil:third.wx.menu.btn.wxwork.publish') }" onclick="Com_OpenWindow('${LUI_ContextPath}/third/wxwork/mutil/menu/wxworkMenuDefine.do?method=publish&fdId=${fdMenuId}&fdWxKey=${thirdWeixinWorkForm.fdWxKey }','_self');"></ui:button>
					</c:if>
					<ui:button text="${ lfn:message('third-weixin-mutil:third.wx.menu.btn.wxwork.edit') }" onclick="Com_OpenWindow('${LUI_ContextPath}/third/wxwork/mutil/menu/wxworkMenuDefine.do?method=edit&fdId=${fdMenuId}','_self');"></ui:button>
					<ui:button text="${ lfn:message('third-weixin-mutil:third.wx.menu.btn.wxwork.del') }" onclick="if(!confirmDelete())return;Com_OpenWindow('${LUI_ContextPath}/third/wxwork/mutil/menu/wxworkMenuDefine.do?method=delete&fdId=${fdMenuId}','_self');"></ui:button>
				</c:if>
			</kmss:authShow>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<p class="txttitle"><bean:message bundle="third-weixin-mutil" key="table.thirdWeixinWork"/></p>

<center>
<table class="tb_normal" id="Label_Tabel" width=95%>
	<tr LKS_LabelName='${ lfn:message('config.baseinfo') }'>
		<td>
			<table class="tb_normal" width=100%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdName"/>
		</td><td width="35%">
			<xform:text property="fdName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdModelName"/>
		</td>
		<td width="35%">
			<xform:text property="fdModelName" style="width:50%"/>
		</td>
	</tr>
	<%-- <tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdType"/>
		</td><td width="85%">
			<xform:radio property="fdType" value="${thirdWeixinWorkForm.fdType}">
	       		<xform:enumsDataSource enumsType="third_weixin_work_type" />
	      	</xform:radio>
		</td>
	</tr> --%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdAgentid"/>
		</td><td width="35%">
			<xform:text property="fdAgentid" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWxWorkConfig.fdKey"/>
		</td><td width="35%">
			<xform:text property="fdWxKey" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdSecret"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdSecret" style="width:85%" />
		</td>
	</tr>
	<%-- <tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdSystemUrl"/>
		</td><td width="85%">
			<xform:text property="fdSystemUrl" style="width:85%" />
		</td>
	</tr> --%>
	<%-- <tr>
		<td class="td_normal_title" width=15% colspan="4" align="center">
			<b><bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.receivedmessage.config"/></b>
			<div style="font-size: 10xp"><font color="red">注：登录企业管理后台>>企业应用，打开应用点击“接收消息”，复制下面三个参数到对应文本框中</font></div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdCallbackUrl"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdCallbackUrl" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdToken"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdToken" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-weixin-mutil" key="thirdWeixinWork.fdAeskey"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdAeskey" style="width:85%" />
		</td>
	</tr> --%>
			</table>
		</td>
	</tr>
	<c:if test="${!empty fdMenuId }">
		<tr LKS_LabelName='${ lfn:message('third-weixin-work:third.wx.menu.btn.wxwork.menu.info') }'>
			<td style="padding: 0px;">
				<table class="tb_normal" width=100%> 
					<tr valign="top">
						<td>
							<iframe id="mainFrame" name="mainFrame" style="padding: 0px;" width="100%" marginheight=0 marginwidth=0 frameborder=0 src="<c:url value="/third/wxwork/mutil/menu/wxworkMenuDefine.do" />?method=view&wxwork=1&fdId=${fdMenuId}">"></iframe>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</c:if>
</table>
</center>
<script>
	function wxcopy(name){
		var name = $("input[name='"+name+"']").val();
		window.clipboardData.setData("Text",name);
	}
	var browserVersion = window.navigator.userAgent.toUpperCase();
	var isOpera = browserVersion.indexOf("OPERA") > -1 ? true : false;
	var isFireFox = browserVersion.indexOf("FIREFOX") > -1 ? true : false;
	var isChrome = browserVersion.indexOf("CHROME") > -1 ? true : false;
	var isSafari = browserVersion.indexOf("SAFARI") > -1 ? true : false;
	var isIE = (!!window.ActiveXObject || "ActiveXObject" in window);
	var isIE9More = (! -[1, ] == false);
	function reinitIframe(iframeId, minHeight) {
	    try {
	        var iframe = document.getElementById(iframeId);
	        var bHeight = 0;
	        if (isChrome == false && isSafari == false)
	            bHeight = iframe.contentWindow.document.body.scrollHeight;

	        var dHeight = 0;
	        if (isFireFox == true)
	            dHeight = iframe.contentWindow.document.documentElement.offsetHeight + 2;
	        else if (isIE == false && isOpera == false)
	            dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
	        else if (isIE == true && isIE9More) {//ie9+
	            var heightDeviation = bHeight - eval("window.IE9MoreRealHeight" + iframeId);
	            if (heightDeviation == 0) {
	                bHeight += 3;
	            } else if (heightDeviation != 3) {
	                eval("window.IE9MoreRealHeight" + iframeId + "=" + bHeight);
	                bHeight += 3;
	            }
	        }
	        else//ie[6-8]、OPERA
	            bHeight += 3;

	        var height = Math.max(bHeight, dHeight);
	        if (height < minHeight) height = minHeight;
	        iframe.style.height = height + "px";
	    } catch (ex) { }
	}
	function startInit(iframeId, minHeight) {
	    eval("window.IE9MoreRealHeight" + iframeId + "=0");
	    window.setInterval("reinitIframe('" + iframeId + "'," + minHeight + ")", 100);
	}
	startInit('mainFrame', 300);
</script>
	</template:replace>
</template:include>