<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.landray.kmss.framework.service.plugin.Plugin"%>
<%@ page import="com.landray.kmss.framework.plugin.core.config.IExtension"%>
<%@ page import="com.landray.kmss.sys.profile.model.PasswordSecurityConfig"%>
<%@ page import="com.landray.kmss.sys.profile.interfaces.IThirdLogin"%>
<%@ page import="com.landray.kmss.sys.profile.util.ThirdLoginPluginUtil"%>
<% 
	IExtension[] extensions = ThirdLoginPluginUtil.getExtensions();
	PasswordSecurityConfig passwordSecurityConfig = PasswordSecurityConfig.newInstance();
	String thirdLoginType = passwordSecurityConfig.getThirdLoginType();
	for(IExtension extension : extensions){
	IThirdLogin bean = (IThirdLogin)Plugin.getParamValue(extension, "bean");
	if(bean.cfgEnable() && bean.isDefault()){
		Map<String,String> map = new HashMap<String,String>();
		map.put("key",bean.key());
		map.put("name", bean.name());
		map.put("checked", thirdLoginType.indexOf(bean.key()) > -1 ? "true" : "false");
		request.setAttribute("defaultLoginConfig", map);
		break;
	}
}
%>
<c:if test="${not empty defaultLoginConfig}">
<tr id="defaultPcScanLogin">
	<input name="value(thirdLoginType)" type="hidden" value="${thirdLoginType}"/>
	<td class="td_normal_title" width="35%">
		${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.loginPcScanType')}
	</td>
	<td colspan="3">
		<div class="third_defaultlogin_config">
			<input type="hidden" name="_defaultLoginKey" value="${defaultLoginConfig.key}">
			<ui:switch property="_defaultLoginType" 
				checked="${defaultLoginConfig.checked}"
				enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
				disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			<xform:text property="thirdLoginName_${defaultLoginConfig.key}" value="${defaultLoginConfig.name}" required="true"
				showStatus="edit" style="width:100px;"></xform:text>	
			<span class="message">
				<bean:message bundle="sys-profile" key="sys.profile.org.passwordSecurityConfig.loginPcScanType.tip1" />${defaultLoginConfig.name}<bean:message bundle="sys-profile" key="sys.profile.org.passwordSecurityConfig.loginPcScanType.tip2" />
			</span>
		</div>
	</td>
</tr>	
</c:if>
