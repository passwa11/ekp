<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	//主题固定为蓝天凌云
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
var prefix = Com_Parameter.ContextPath.substring(0,Com_Parameter.ContextPath.length-1);
if(grid['rowIndex'] == 0){
{$
<div class="profile_loginSetting_uploadBtn profile_loginSetting_portlet_uploadBtn">
	<div class="profile_loginSetting_content"
		onclick="uploadComponent('${JsParam.type}');">
		<div class="thumb_img_bg">
			<span class="btn-upload"><i></i></span> <span class="uploadBtn_title">${lfn:message('sys-ui:mall.component.add') }</span>
		</div>
	</div>
</div>
$} 
}else{ 
{$
<div class="profile_loginSetting_item profile_loginSetting_portlet_item">
	$} 
	if(!grid['path']){
		if(!grid['fdThumb']){
			{$
			<div class="profile_loginSetting_content profile_loginSetting_content_no_data">
				<div class="thumb_img_bg" style="">暂无缩略图</div>
			</div>
			$}
		}else{
			{$
			<div class="profile_loginSetting_content">
				<div class="thumb_img_bg"
					 style="background-image: url({% prefix + grid [ 'fdThumb' ]%});">
					<img class="thumb_img" src="{%prefix+grid['fdThumb']%}"
						 alt="{%grid['fdName']%}" style="width:100%;height:100%" onerror="this.src='${LUI_ContextPath}/sys/profile/resource/images/image.png'"/>
				</div>
				<div class="profile_loginSetting_bg">
					<a href="javascript:_review('{%prefix+grid['fdThumb']%}');">
						<span class="profile_icon_view"></span>
					<bean:message bundle="sys-profile" key="sys.profile.portal.login.preview" /></a>
				</div>
			</div>
			$}
		}
			{$
				<input type="hidden" value="{%grid['fdId']%}">
				<p id="login_model_default_0" class="loginTemplete_title"  title="{%grid['fdName']%}">{%grid['fdName']%}</p></div>
			$}
	}else if(grid['path']){
		if(!grid['fdThumb']){
			{$
				<div class="profile_loginSetting_content profile_loginSetting_content_no_data">
					<span class="profile_portlet_ext_flag">扩展</span>
					<div class="thumb_img_bg" style="">暂无缩略图</div>
                    <span class="profile_custom_del"
                          onclick="_delete('{%grid['fdId']%}','{%grid['uiType']%}');"><i></i></span>
					<div class="profile_loginSetting_bg">
                        <a href="javascript:_review('{%grid['fdThumb']%}'); " style="width:50%;">
                            <span class="profile_icon_view"></span>
                            <bean:message bundle="sys-profile" key="sys.profile.portal.login.preview" /></a>
                        <span class="line"></span>
						<a href="javascript:_download('{%grid['fdId']%}');" style="width:50%;">
							<i class="profile_icon_download"></i><bean:message bundle="sys-profile" key="sys.loginTemplate.download" /></a>
					</div>
				</div>
			$}
		}else{
			{$
				<div class="profile_loginSetting_content ">
					<span class="profile_portlet_ext_flag">扩展</span>
					<div class="thumb_img_bg"
						 style="background-image: url({% prefix + grid [ 'thumbPath' ]%});">
							<span class="profile_custom_del"
								  onclick="_delete('{%grid['fdId']%}','{%grid['uiType']%}');"><i></i></span>
							<img class="thumb_img" src="{%prefix+grid['fdThumb']%}"
							 alt="{%grid['fdName']%}" style="width:100%;height:100%" onerror="this.src='${LUI_ContextPath}/sys/profile/resource/images/image.png'"/>
					</div>
					<div class="profile_loginSetting_bg">
						<a href="javascript:_review('{%prefix+grid['fdThumb']%}'); " style="width:50%;">
							<span class="profile_icon_view"></span>
							<bean:message bundle="sys-profile" key="sys.profile.portal.login.preview" /></a>
						<span class="line"></span>
						<a href="javascript:_download('{%grid['fdId']%}');" style="width:50%;">
							<i class="profile_icon_download"></i><bean:message bundle="sys-profile" key="sys.loginTemplate.download" /></a>
					</div>
				</div>
			$}
		}
		{$
		<input type="hidden" value="{%grid['fdId']%}">
		<p id="login_model_default_0" class="loginTemplete_title"  title="{%grid['fdName']%}">{%grid['fdName']%}</p></div>
		$}
	$}
}