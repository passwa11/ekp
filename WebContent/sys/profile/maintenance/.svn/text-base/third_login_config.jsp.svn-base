<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ page import="com.landray.kmss.sys.profile.interfaces.IThirdLogin"%>
<%@ page import="com.landray.kmss.framework.service.plugin.Plugin"%>
<%@ page import="com.landray.kmss.sys.profile.model.PasswordSecurityConfig"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page import="com.landray.kmss.sys.profile.util.ThirdLoginPluginUtil"%>
<%@ page import="com.landray.kmss.framework.plugin.core.config.IExtension"%>
<%
	IExtension[] extensions = ThirdLoginPluginUtil.getExtensions();
	List<Map<String,String>> collection = new ArrayList<Map<String,String>>();
	PasswordSecurityConfig passwordSecurityConfig = PasswordSecurityConfig.newInstance();
	String thirdLoginType = passwordSecurityConfig.getThirdLoginType();
	for(IExtension extension : extensions){
		IThirdLogin bean = (IThirdLogin)Plugin.getParamValue(extension, "bean");
		String[] key = bean.key().split("\\|");
		//兼容多企业微信
		for(int i=0; i<key.length; i++){
			if(bean.loginEnable() && bean.cfgEnable(key[i]) && !bean.isDefault()){
				Map<String,String> map = new HashMap<String,String>();
				map.put("key",key[i]);
				map.put("name", bean.name(key[i]));
				map.put("icon", bean.iconUrl());
				map.put("checked", thirdLoginType.indexOf(key[i]) > -1 ? "true" : "false");
				collection.add(map);
			}
		}
	}
	request.setAttribute("thirdLoginType", thirdLoginType);
	request.setAttribute("thirdLoginCollection", collection);
%>
<c:if test="${!empty thirdLoginCollection}">
<tr>
	<td class="td_normal_title" width=35%>
		${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.other.loginType')} 
  	</td>
	<td colspan="3">
		<div class="third_login_config">
			<input name="value(thirdLoginType)" type="hidden" value="${thirdLoginType}"/>
			<table>
				<tr>
					<c:forEach items="${thirdLoginCollection}" var="thirdLogin">
						<c:set var="__checked__third__login__" value=""></c:set>
						<c:if test="${thirdLogin.checked == 'true' }">
							<c:set var="__checked__third__login__" value="checked"></c:set>
						</c:if>
						<div class="third_login_citem">
							<td>
								<table style="margin-left: 15px;">
									<tr>
										<td align="center"><img class="third_login_item_icon" width="46" src="${ thirdLogin.icon}"></td>
									</tr>
									<tr>
										<td align="center"><input name="_thirdLoginType" type="checkbox" ${__checked__third__login__} value="${ thirdLogin.key}"/>${thirdLogin.name}</td>
									</tr>
								</table>
							</td>
						</div>
					</c:forEach>
				</tr>
			</table>
		</div>
	</td>
</tr>
</c:if>
<script type="text/javascript">
	$(function(){
		Com_Parameter.event["submit"].push(function(){
			var thirdLoginType = [];
			var _thirdLoginTypes = $("[name='_thirdLoginType']");
			var _defaultLoginType = $("[name='_defaultLoginType']");
			if(_thirdLoginTypes && _thirdLoginTypes.length > 0){
				for(var i = 0; i < _thirdLoginTypes.length;i++){
					var _thirdLoginType = _thirdLoginTypes.eq(i);
					if(_thirdLoginType.prop('checked')){
						thirdLoginType.push(_thirdLoginType.val());
					}
				}
			}
			if(_defaultLoginType && _defaultLoginType.val() == 'true'){
				thirdLoginType.push( $("[name='_defaultLoginKey']").val() );  
			}
			$('[name="value(thirdLoginType)"]').val(thirdLoginType.join(';'));
			return true;
		});
	});
</script>