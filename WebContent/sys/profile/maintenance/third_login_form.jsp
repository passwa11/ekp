<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ page import="com.landray.kmss.sys.profile.model.PasswordSecurityConfig"%>
<%@ page import="com.landray.kmss.sys.profile.util.ThirdLoginPluginUtil"%>
<%@ page import="com.landray.kmss.framework.plugin.core.config.IExtension"%>
<%@ page import="com.landray.kmss.framework.service.plugin.Plugin"%>
<%@ page import="com.landray.kmss.sys.profile.interfaces.IThirdLogin"%>
<%@ page import="com.landray.kmss.sys.profile.model.LoginConfig" %>
<%@ page import="com.landray.kmss.sys.profile.service.spring.SysLoginDefaultTypeDataSource" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	LoginConfig loginConfig = new LoginConfig();
	String defaultLoginType = SysLoginDefaultTypeDataSource.LOGIN_TYPE_PASSWORD;
	String cfgLoginType = loginConfig.getFdDefaultLoginType();

	IExtension[] extensions = ThirdLoginPluginUtil.getExtensions();
	List<Map<String,String>> collection = new ArrayList<Map<String,String>>();
	PasswordSecurityConfig passwordSecurityConfig = PasswordSecurityConfig.newInstance();
	String thirdLoginType = passwordSecurityConfig.getThirdLoginType();
	for(IExtension extension : extensions) {
		IThirdLogin bean = (IThirdLogin)Plugin.getParamValue(extension, "bean");
		String[] key = bean.key().split("\\|");
		//兼容多企业微信
		for(int i=0; i<key.length; i++){
			if((bean.loginEnable() && thirdLoginType.indexOf(key[i]) > -1 && bean.isDefault() == false) ||
					(bean.cfgEnable() && thirdLoginType.indexOf(key[i]) > -1 && bean.isDefault())){
				Map<String,String> map = new HashMap<String,String>();
				map.put("name", bean.name(key[i]));
				map.put("iconKey", key[i]);
				map.put("iconUrl", bean.iconUrl());
				map.put("qrCodeUrl", bean.qrCodeUrl()); 
				if(bean.isDefaultLang()){
					map.put("url", bean.loginUrl(key[i]));
				}else{
					map.put("url", bean.loginUrl(request));
				}
				collection.add(map);
				if(key[i].equalsIgnoreCase(cfgLoginType)) {
					defaultLoginType = cfgLoginType;
				}
			}
		}
	}
	request.setAttribute("thirdLoginCollection", collection);
	request.setAttribute("defaultLoginType", defaultLoginType);
%>
<script>	
	var loginType = "${defaultLoginType}";
	seajs.use("sys/profile/maintenance/css/third_login_form.css");
</script>
<c:if test="${fn:length(thirdLoginCollection) > 0 }">
	<script>	
		window.qrCodeGenerator = {
			selected : "",
			creators : [],
			container : "${JsParam.qrCodeElement}" || ".form_qrcode_content",
			backNode : null,
			registerCreator : function(creator)  {
				creator.inited = false;
				this.creators.push(creator);
			},
			createQrCode : function() {
				var self = this;
				seajs.use(["lui/jquery"], function($) {
					var element = $(self.container);
					if(self.creators.length > 0 && element.length > 0) {
						var innerEle = $("<div class='form_qrcode_content_inner'/>").appendTo(element);
						self.backNode = $("<div class='form_qrcode_content_back' data-login-key='password' >${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.loginName.login')}</div>").appendTo(element);
						for(var i = 0; i < self.creators.length; i ++) {
							var codeEle = $("<div id='qrcode_" + self.creators[i].key + "'/>").appendTo(innerEle);
							self.creators[i].element = codeEle;
							//self.creators[i].fn.call(self, ("qrcode_" + self.creators[i].key));
						}
					}
				});
			},
			show : function(key) {
				LUI.$(this.container).show();
				for(var i = 0; i < this.creators.length; i ++) {
					if(this.creators[i].key == key) {
						if(this.creators.inited) {
							this.creators[i].element.show();
						} else {
							this.creators[i].fn.call(this, ("qrcode_" + this.creators[i].key));
							this.creators[i].inited = true;
						}
					} else {
						this.creators[i].element.hide();
					}
				}
			},
			hide : function() {
				LUI.$(this.container).hide();
			}
		}
	</script>
	<div id="third_login_form" class="third_login_form">
		<div class="third_login_header">
		    <!-- 其他登录方式 -->
			<span>${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.other.login')}</span>
		</div>
		<ul class='third_login_list'>
		<c:forEach items="${thirdLoginCollection}" var="thirdLogin" >
			<li class="third_login_item">
				<a href="${empty thirdLogin.qrCodeUrl ? thirdLogin.url : 'javascript:;'}" 
				   title="${thirdLogin.name}"  
				   data-login-key="${thirdLogin.iconKey}">
				  <div class="third_login_item_icon third_login_icon_${thirdLogin.iconKey}"></div>
			      <div class="third_login_item_desc">${fn:substring(thirdLogin.name, 0, 3)}</div>
				</a>
				<%-- 提供生成二维码的方法 --%>
				<c:if test="${not empty thirdLogin.qrCodeUrl}">
					<c:import url="${thirdLogin.qrCodeUrl}" charEncoding="UTF-8">
					</c:import>
				</c:if>
			</li>
		</c:forEach>
		</ul>
	</div>
	<script>
		seajs.use(["lui/jquery", "lui/topic"], function($, topic) {
			$(function() {
				// 判断元素是否存在
				if(!($(".form_qrcode_content").length>0)){
					$(".form_content").before("<div class='form_qrcode_content' ></div>");
				}
				
				window.qrCodeGenerator.createQrCode();
				if(loginType != "password" && "${empty JsParam.login_error}" == "true") {
					window.qrCodeGenerator.show(loginType);
					$(".form_content").hide();
				}
				$(".form_bottom").append($("#third_login_form"));
				
				topic.subscribe("lui.login.type.change", function(evt) {
					if(!evt.key) return;
					if(evt.key == "password") {
						$(".form_content").show();
						this.hide();
					} else {
						$(".form_content").hide();
						this.show(evt.key);
					}
				}, window.qrCodeGenerator);
				
				$("[data-login-key]").on("click", function() {
					var key = $(this).attr("data-login-key");
					if(key) {
						topic.publish("lui.login.type.change", {
							key : key
						});
					}
				});
			})
		})
	</script>
</c:if>