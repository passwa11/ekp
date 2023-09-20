<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.third.mall.util.MallUtil" %>
<%@ page import="com.landray.kmss.third.mall.util.ThirdMallPluginUtil" %>
<%@ include file="./../ThirdMallConfig_common.jsp"%>
<%
	String reachable = MallUtil.reachable();
	request.setAttribute("reachable", reachable);
	request.setAttribute("extensions",ThirdMallPluginUtil.getConfigExtensions());
	String keyWord = request.getParameter("keyWord");
	if(com.landray.kmss.util.StringUtil.isNotNull(keyWord)){
		request.setAttribute("__keyWord", java.net.URLEncoder.encode(keyWord,"UTF-8"));
	}
	//主题固定为蓝天凌云
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="config.edit" sidebar="no">
	<c:set var="isPortalRight" value="false"/>
	 <template:replace name="head">
		<script type="text/javascript">
			seajs.use(['theme!profile']);
			seajs.use(['theme!iconfont']);
			Com_IncludeFile("common.css","${LUI_ContextPath}/third/mall/resource/css/","css",true);
			Com_IncludeFile("templateList.css","${LUI_ContextPath}/third/mall/resource/css/","css",true);
			var subType;
			function chooseCurrent(type){
				var title =$(".lui_mall_content_aside_title");
				for(var i=0;i<title.length;i++){
					$(title[i]).removeClass("active");
				}
				$("#"+type).addClass("active");
				subType =type;
			}
			LUI.ready(function(){
				var keyWord ='${keyWord}';
				if(keyWord !=''){
					$('#fySearchInput').val(decodeURI(keyWord));
				}
				var mallTabPanel =LUI("mallTabPanel");
				if(mallTabPanel){
					mallTabPanel.currentIndex=3
					mallTabPanel.on("indexChanged", function (evt) {
						var cur = evt.panel.contents[evt.index.after];
						if(evt.index.after != evt.index.before){
							var curentTarget =$("#goods_listview_"+cur.id);
							if(curentTarget[0].children[0]){
								curentTarget[0].children[0].src =curentTarget[0].children[0].src+1;
							}
						}
					});
				}
			})

			function showProfile(type,keyWord){
				if(!keyWord || keyWord ==undefined){
					keyWord='';
					$("#fySearchInput").val("");
				}
				// 打开iframe子页面
				var url = '${LUI_ContextPath}/third/mall/public/thirdMall_content_index.jsp?type=${type}&fdType='+type+'&keyWord='+keyWord+'&n='+Math.random();
				if(subType=='main' && keyWord){
					url+="&selectedIndex=3";
				}else{
					url+="&selectedIndex=0";
				}
				var iframeObj = document.getElementById("contentIframe");
				iframeObj.src = url;
				chooseCurrent(type);
			}

			function setIframeHeight(height){
				try{
					$("#contentIframe").css({height:height+50});
					if(parent && parent.document.getElementById("main_show_iframe")){
						//因为用的iframe。为了美观一点只能手动设置高度。
						parent.document.getElementById("main_show_iframe").children[0].style.height = $("#lui_mall_content_mall_info")[0].scrollHeight +"px";
					}
				}catch(e){
					throw new Error('setIframeHeight Error',e);
				}
			}

			function _search__full(v){
				/**关键字搜索*/
				var searchKeyWord =$("#fySearchInput").val();
				var keyWord="";
				if(searchKeyWord){
					keyWord =encodeURI($("#fySearchInput").val());
				}
				showProfile(subType,keyWord);
			}
		</script>
	 </template:replace>
	 <template:replace name="content">
	 	<div  >

			<div class="lui_mall_content lui_mall_content_app " id="lui_mall_content_mall_info">
				<c:set var="isExist" value="false"/>
				<c:choose>
					<c:when test="${__fdMallEnable=='1' && param.type=='sysApplication' && fn:contains(__fdBusKeys,'sys_application')}">
					 	<!-- 当前是应用，应用是否开启了 授权-->
						<c:set var="isExist" value="true"/>
					</c:when>
					<c:when test="${__fdMallEnable=='1' && param.type=='sysMain' && fn:contains(__fdBusKeys,'sys_portal')}">
						<!-- 当前是应用，应用是否开启了 授权-->
						<c:set var="isExist" value="true"/>
					</c:when>
					<c:otherwise>
						<c:set var="isExist" value="false"/>
					</c:otherwise>
				</c:choose>
				<div class="lui_mall_content_main aside_main"style="height:100%">
					<c:set var="showTabs" value=""/>
					<c:if test="${reachable eq 'true'}">
						<!--当前模块已授权-->
						<div class="lui_mall_content_aside">
							<div class="lui_mall_content_aside_title max">
								<bean:message  bundle="third-mall" key="thirdMall.tab.sysMain"/>
							</div>

							<c:if test="${type =='sysMain'}">
								<kmss:auth requestURL="/sys/portal/sys_portal_main/sysPortalMain.do?method=add">
									<!-- 切换回门户 -->
									<div  id="main" class="lui_mall_content_aside_title" onclick="showProfile('main')">
										<span class="aside_title">
											<bean:message key="third-mall:kmReuseCommon.portal"/>
										</span>
									</div>
									<c:set var="isPortalRight" value="true"/>
									<c:set var="showTabs" value="${showTabs};main"/>
								</kmss:auth>
								<kmss:authShow roles="ROLE_SYSPORTAL_BASE_SETTING">
									<div id="login" class="lui_mall_content_aside_title" onclick="showProfile('login')">
										<span class="aside_title">
											<bean:message key="third-mall:thirdMall.preview.title.login"/>

										</span>
									</div>
									<c:set var="isPortalRight" value="true"/>
									<c:set var="showTabs" value="${showTabs};login"/>
								</kmss:authShow>
								<kmss:authShow roles="ROLE_SYSPORTAL_EXT_SETTING">
									<div id="theme" class="lui_mall_content_aside_title" onclick="showProfile('theme')">
										<span class="aside_title">
										<bean:message key="third-mall:thirdMall.preview.title.theme"/>
										</span>
									</div>
									<c:set var="isPortalRight" value="true"/>
									<c:set var="showTabs" value="${showTabs};theme"/>
								</kmss:authShow>
								<kmss:authShow roles="ROLE_SYSPORTAL_BASE_SETTING">
									<div id="component" class="lui_mall_content_aside_title" onclick="showProfile('component')">
										<span class="aside_title">
											<bean:message key="third-mall:thirdMall.preview.title.component"/>
										</span>
									</div>
									<c:set var="isPortalRight" value="true"/>
									<c:set var="showTabs" value="${showTabs};component"/>
								</kmss:authShow>
							</c:if>
							<c:if test="${type =='sysApplication'}">
								<!-- 应用中心 -->
								<kmss:authShow roles="ROLE_MODELING_SETTING;">
									<kmss:authShow roles="ROLE_MODELING_APP_CREATE">
										<c:set var="isPortalRight" value="true"/>
									</kmss:authShow>
								</kmss:authShow>
							</c:if>
							<c:if test="${isPortalRight =='false'}">
								<script>
									window.parent.location.href="${LUI_ContextPath }/resource/jsp/e403.jsp";
								</script>
							</c:if>
							<c:if test="${isPortalRight =='true'}">
								<script>
									LUI.ready(function() {
										var tempType ="${showTabs}";
										if(tempType) {
											var tempArr = tempType.split(";");
											var val;
											for(var temp in tempArr){
												var v =tempArr[temp];
												if(v && v.length > 0 && !subType){
													subType =v;
												}
											}
										}else{
											var topUrl = Com_GetUrlParameter(location.href, 'fdType');
											subType = topUrl ? topUrl : 'main';
										}
										showProfile(subType);
										chooseCurrent(subType)
									});
								</script>
							</c:if>

							<!--判断有没有授权-->
							<div class="fy-search-wrap">
								<input placeholder="${lfn:message('third-mall:kmReuseCommon.nameInAlert')}|${lfn:message('third-mall:kmReuseCommon.keywordSearch')}" id="fySearchInput" name="SEARCH_KEYWORD"
									   onkeydown="if (event.keyCode == 13 ) _search__full();"
								>
								<span class="search_" onclick="_search__full();"></span>
							</div>
						</div>
						<div style="margin: 10px 20px;">
							<c:if test="${ isExist =='false'}">
								<span class="no-open-tip">
									<bean:message key="third-mall:thirdMall.tip.${type}.name.noOpen"/>
								</span>
								<span class="go-open-mall-service">
									<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath }/sys/profile/index.jsp#integrate/saas/mall">
										<bean:message key="third-mall:kmReuseCommon.goopen"/>
									</a>
								</span>
							</c:if>
						</div>
						<iframe id='contentIframe'
								frameborder="0"
								scrolling="no"
								marginheight="no">
						</iframe>
					</c:if>
					<!-- 网络连接异常 -->
					<c:if test="${reachable eq 'false'}">

						<div class="cm-interlink">
							<div class="interlink-main">
								<c:if test="${reachable eq 'false' }">
									<img src="${LUI_ContextPath }/third/mall/resource/images/img_2_big@2x.png" alt="">
									<span><bean:message key="third-mall:thirdMall.no_network_tip"/></span>
								</c:if>
								<p onclick="reload();"><bean:message key="third-mall:thirdMall.reload"/></p>
							</div>
						</div>
						<script type="text/javascript">
							function reload() {
								window.location.reload();
							}
						</script>
					</c:if>

				</div>
			</div>
		</div>

	</template:replace>
</template:include>
<%
	pageContext.removeAttribute("reachable");
%>