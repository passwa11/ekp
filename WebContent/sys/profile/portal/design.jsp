<%@page import="com.landray.kmss.sys.language.utils.SysLangUtil"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- <%@ include file="/loginConfig.jsp"%> --%>
<%if(request.getAttribute("config") == null) {%>
<c:import url="/${templateName}_config.jsp" charEncoding="UTF-8"></c:import>
<%}
	LinkedHashMap<String,String> langs = LoginTemplateUtil.getDesignLangs();
%>
<template:include ref="config.view">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${LUI_ContextPath }/resource/customization/css/app.css">
		<link rel="stylesheet" type="text/css" href="${LUI_ContextPath }/resource/customization/js/bootstrap-colorpicker/css/bootstrap-colorpicker.css">
		<style>
			/* 颜色选选择器箭头方向 Starts */
			body .colorpicker:after {
				border-right-color: transparent;
				border-left: 7px solid #fff;
				left: auto;
				right: -13px;
			}

			body .colorpicker:before {
				border-right-color: transparent;
				border-left: 7px solid #ccc;
				left: auto;
				right: -15px;
			}
			/* 颜色选选择器箭头方向 Ends */
		</style>
	</template:replace>
	<template:replace name="title">
		<bean:message bundle="sys-profile" key="sys.profile.design.login"/>
	</template:replace>
	<template:replace name="content">
		<script>
			Com_IncludeFile('jquery.js');
		</script>
		<script type="text/javascript" src="${LUI_ContextPath }/resource/customization/js/jquery.nicescroll.js"></script>
		<script type="text/javascript" src="${LUI_ContextPath }/resource/customization/js/vue.min.js"></script>
		<script type="text/javascript" src="${LUI_ContextPath }/resource/customization/js/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
		<div id="head" class="head">
			<div class="title" onclick="window.open('${LUI_ContextPath}/','_self')" title="${lfn:message('home.logoTitle') }">
				<bean:message bundle="sys-profile" key="sys.profile.design.login"/>
			</div>
		</div>
		<div id="container" class="container">
			<div class="left">
				<div class="options">
					<div id="option-container">
						<c:if test="${not empty template}">
							<c:import url="${template.designJsp}" charEncoding="UTF-8">
								<c:param name="templateName" value="${templateName}"></c:param>
								<c:param name="key" value="${param.key}"></c:param>
							</c:import>
						</c:if>
					</div>
					<iframe name="file_frame" style="display:none;"></iframe>
				</div>
				<div class="operation">
					<button class="save" onclick="save();"><bean:message bundle="sys-profile" key="sys.profile.design.replace"/></button>
					<button class="reset" onclick="download();"><bean:message bundle="sys-profile" key="sys.profile.design.download"/></button>
					<button class="reset" onclick="resetConfig();"><bean:message key="button.reset"/></button>
					<button style="background:#f3b521" class="reset" onclick="closeWin();"><bean:message key="home.logout"/></button>
				</div>
				<!-- 打包下载 -->
				<form target="_blank" name="loginDesignForm" action="<%=request.getContextPath() %>/sys/profile/sysProfileCuxTemplateAction.do" method="post">
					<input type="hidden" name="method" value="download">
					<input type="hidden" name="config" id="config" />
					<input type="hidden" name="key" value="${param.key}"/>
				</form>
			</div>
			<div id="right" class="right">
				<c:if test="${not empty template }">
					<iframe id="preview" src="${LUI_ContextPath }${template.cuxJsp}" style="width:100%;height:100%;border:0;"></iframe>
				</c:if>
			</div>
		</div>
		<c:import url="/sys/profile/portal/${templateName}_configJson.jsp" charEncoding="UTF-8"></c:import>
		<script type="text/javascript">
			// 改变语言环境（子页面调用），同时将配置传递过去
			function changeFrameLang(lang) {
				var url = '<c:url value="/sys/profile/sysProfileCuxTemplateAction.do?method=setNowConfig&key=${param.key}"/>';
				$.post(url,{config:getConfigStr()},function() {
					var href = window.location.href;
					href = Com_SetUrlParameter(href, "j_lang", lang);
					href = Com_SetUrlParameter(href, "isDesign", 1);
					window.location.href = href;
				});
			}
			//设置一些元素的高度
			function setContainerSize() {
				var h = $(window).height()-$("#head").height()-10;
				$("#container").css({'height':h});
				$(".options").css({'height':h-$(".operation").height()});
				$("#right").css({'width':$(window).width()-$('.left').width()-40});
			}
			//设置属性，保存时会删除
			function setClassControl(config) {
				if(templateKey == '1') {
					/*********单屏模式 login_single*********/
					config.isLogoActive = false;
					config.isCopyrightActive = false;
					config.isLoginButtonActive = false;
					config.isBgImageActive = false;
					config.isProductActive = false;
				} else if(templateKey == '2') {
					/*********随机背景 login_single_random*********/
					config.isLogoActive = false;
					config.isCopyrightActive = false;
					config.isLoginButtonActive = false;
					config.isHeadLinksActive = false;
					var linksLength = config.single_random_head_links.length;
					for (var i = 0; i < linksLength; i++) {
						config.single_random_head_links[i].id = i+1;
					}
					config.nextLinkItemId = linksLength+1;
				} else if(templateKey == '3') {
					/*********多屏滚动 login_multi*********/
					config.isLogoActive = false;
					config.isCopyrightActive = false;
					config.isLoginButtonActive = false;
					config.isLoginTitleActive = false;
					config.isBgImageActive = false;
				} else if(templateKey == '4') {
					/*********单屏（横向登录）模式  login_single_horizontal*********/
					config.isLogoActive = false;
					config.isCopyrightActive = false;
					config.isLoginBoxActive = false;
					config.isLoginButtonActive = false;
					config.isLoginTitleActive = false;
					config.isBgImageActive = false;
				} else if(templateKey == '5') {
					/*********全屏登录（随机背景）模式 login_single_full_screen*********/
					config.isLogoActive = false;
					config.isCopyrightActive = false;
					config.isLoginBoxActive = false;
					config.isLoginButtonActive = false;
				} else if(templateKey == '6') {
					/*********单屏（圆角登录） login_simplicity*********/
					config.isLogoActive = false;
					config.isCopyrightActive = false;
					config.isLoginButtonActive = false;
					config.isHeadLinksActive = false;
					var linksLength = config.simplicity_head_links.length;
					for (var i = 0; i < linksLength; i++) {
						config.simplicity_head_links[i].id = i+1;
					}
					config.nextLinkItemId = linksLength+1;
				}

			}
			//删除属性，同时返回配置的JSON字符串
			function getConfigStr() {
				if(templateKey == '1') {
					/*********单屏模式 login_single*********/
					delete config.isLogoActive;
					delete config.isCopyrightActive;
					delete config.isLoginButtonActive;
					delete config.isBgImageActive;
					delete config.isProductActive;
				} else if(templateKey == '2') {
					/*********随机背景 login_single_random*********/
					delete config.isLogoActive;
					delete config.isCopyrightActive;
					delete config.isLoginButtonActive;
					delete config.isHeadLinksActive;
					delete config.nextLinkItemId;
					var linksLength = config.single_random_head_links.length;
					for (var i = 0; i < linksLength; i++) {
						delete config.single_random_head_links[i].id;
					}
				} else if(templateKey == '3') {
					/*********多屏滚动 login_multi*********/
					delete config.isLogoActive;
					delete config.isCopyrightActive;
					delete config.isLoginButtonActive;
					delete config.isLoginTitleActive;
					delete config.isBgImageActive;
				} else if(templateKey == '4') {
					/*********单屏（横向登录）模式  login_single_horizontal*********/
					delete config.isLogoActive;
					delete config.isCopyrightActive;
					delete config.isLoginBoxActive;
					delete config.isLoginButtonActive;
					delete config.isLoginTitleActive;
					delete config.isBgImageActive;
				} else if(templateKey == '5') {
					/*********全屏登录（随机背景）模式 login_single_full_screen*********/
					delete config.isLogoActive;
					delete config.isCopyrightActive;
					delete config.isLoginBoxActive;
					delete config.isLoginButtonActive;
				} else if(templateKey == '6') {
					/*********单屏（圆角登录） login_simplicity*********/
					delete config.isLogoActive;
					delete config.isCopyrightActive;
					delete config.isLoginButtonActive;
					delete config.isHeadLinksActive;
					delete config.nextLinkItemId;
					var linksLength = config.simplicity_head_links.length;
					for (var i = 0; i < linksLength; i++) {
						delete config.simplicity_head_links[i].id;
					}
				}
				return JSON.stringify(config);
			}
			//原来的配置JSON字符串
			var oriConfigStr = null;
			function getOriginalConfig() {
				if(oriConfigStr) {
					configCopy = JSON.parse(oriConfigStr);
					return;
				}
				var url = '<c:url value="/sys/profile/sysProfileCuxTemplateAction.do?method=getOriginalConfig&key=${param.key}"/>';
				$.get(url,function(data) {
					configCopy = JSON.parse(data);
					oriConfigStr = data;
				});
			}
			//配置对象副本（未保存过的）
			var configCopy;
			//保存颜色选择器实例对象
			var colorPickers = new Object();
			var templateKey = "${template.key}";
			$(document).ready(function() {
				setContainerSize();
				setClassControl(config);
				getOriginalConfig();
				// 滚动条
				$(".options").niceScroll({
					cursorwidth:8,
					autohidemode:false
				});
				$(".options").hover(function() {
					$(".options").getNiceScroll().show();
				},function() {
					$(".options").getNiceScroll().hide();
				});
				// config对象与dom元素绑定
				var parentApp = new Vue({
					el:"#option-container",
					data:config,
					methods:{
						enter:function(event) {
							var hoverProp = $(event.target).data('hover');
							config[hoverProp] = true;
						},
						leave:function(event) {
							var hoverProp = $(event.target).data('hover');
							config[hoverProp] = false;
						},
						showColorPicker:function(evt) {
							// debugger
							var $ele = $(evt.target);
							var pick_prop = $ele.parents('div.colorpicker-component').data('pick');
							var $target = colorPickers[pick_prop];
							var pos = $ele.offset();
							pos.top = pos.top - $ele.height()/2;
							pos.left = pos.left + $ele.width()+10-200;
							$target.colorpicker('show',pos);
							console.log(pos)
						},
						removeLinkItem:function(index,evt) {
							if(this.single_random_head_links) {
								this.single_random_head_links.splice(index,1);
							}
							if(this.simplicity_head_links) {
								this.simplicity_head_links.splice(index,1);
							}
							changeVueView();
						},
						addLinkItem:function(evt) {
							if(this.single_random_head_links) {
								var item = {
									id:this.nextLinkItemId++,
									<% for(Entry<String,String> entry : langs.entrySet()) {%>
									single_random_head_link_<%=entry.getKey()%>:'',
									<%}%>
									single_random_head_link_href:'',
								};
								this.single_random_head_links.push(item);
							}
							if(this.simplicity_head_links) {
								var item = {
									id:this.nextLinkItemId++,
									<% for(Entry<String,String> entry : langs.entrySet()) {%>
									simplicity_head_link_<%=entry.getKey()%>:'',
									<%}%>
									simplicity_head_link_href:'',
								};
								this.simplicity_head_links.push(item);
							}
							//this.$nextTick();
							changeVueView();
						}
					}
				});
				//手动触发视图更新
				var changeVueView = function() {
					setTimeout(function() {
						var temp = parentApp.loginBtn_text_CN;
						parentApp.loginBtn_text_CN = "*";
						parentApp.loginBtn_text_CN = temp;
					},10);
				};
			});
			window.onresize = setContainerSize;
			// 直接替换
			function save() {
				seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
					<%if(LoginTemplateUtil.isMultiServer()) {%>
					var confirmMsg = '<bean:message bundle="sys-profile" key="sys.profile.design.replace.multiserver.confirm"/>';
					<%} else {%>
					var confirmMsg = '<bean:message bundle="sys-profile" key="sys.profile.design.replace.singleserver.confirm"/>';
					<%}%>
					dialog.confirm(confirmMsg,function(rtnVal) {
						if(rtnVal) {
							var url = '<c:url value="/sys/profile/sysProfileCuxTemplateAction.do?method=saveConfig&key=${param.key}"/>';
							$.ajax({
								url: url,
								type: 'POST',
								data:{
									config:getConfigStr()
								},
								dataType: 'json',
								error: function(data){
									dialog.result(data.responseJSON);
								},
								success: function(data) {
									if(data.status) {
										dialog.alert('<bean:message key="return.optSuccess"/>',function(data) {
											var url = '<c:url value="/sys/profile/sysProfileCuxTemplateAction.do?method=design&key=${template.key}"/>';
											window.location.href = url;
										});
									}else {
										dialog.result(data);
									}
								}
							});
						}
					});
				});
			}
			//打包下载
			function download() {
				<%if(LoginTemplateUtil.isMultiServer()) {%>
				seajs.use(['lui/dialog'],function(dialog) {
					dialog.confirm('<bean:message bundle="sys-profile" key="sys.profile.design.download.confirm"/>',function(rtnVal) {
						if(rtnVal) {
							document.getElementById('config').value = oriConfigStr = getConfigStr();
							configCopy = JSON.parse(oriConfigStr);
							document.loginDesignForm.submit();
						}
					});
				});
				<%} else {%>
				document.getElementById('config').value = oriConfigStr = getConfigStr();
				configCopy = JSON.parse(oriConfigStr);
				document.loginDesignForm.submit();
				<%}%>
			}
			//重置调用的方法
			var resetFuncs = new Array();
			//重置
			function resetConfig() {
				seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
					dialog.confirm('<bean:message bundle="sys-profile" key="sys.profile.design.reset.confirm"/>',function(rtnVal) {
						if(rtnVal) {
							var url = '<c:url value="/sys/profile/sysProfileCuxTemplateAction.do?method=resetConfig&key=${param.key}"/>';
							$.ajax({
								url: url,
								type: 'GET',
								dataType: 'json',
								error: function(data){
									dialog.result(data.responseJSON);
								},
								success: function(data) {
									$.extend(config,configCopy);
									getOriginalConfig();
									for (var i = 0; i < resetFuncs.length; i++) {
										resetFuncs[i].call(window);
									}
									dialog.result(data);
								}
							});
						}
					});
				});
			}

			//退出
			function closeWin() {
				seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
					dialog.confirm('<bean:message key="message.closeWindow"/>',function(rtnVal) {
						if(rtnVal) {
							var url = '<c:url value="/sys/profile/sysProfileCuxTemplateAction.do?method=release&key=${param.key}"/>';
							$.ajax({
								url: url,
								type: 'POST',
								error: function(data){
									cuxCloseWindow();
								},
								success: function(data) {
									cuxCloseWindow();
								}
							});
						}
					});
				});
			}

			function cuxCloseWindow() {
				var userAgent = navigator.userAgent;
				if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") !=-1) {
					window.location.href="about:blank";
				} else {
					window.opener = null;
					window.open("", "_self");
				}
				window.close();
			}
		</script>
	</template:replace>
</template:include>