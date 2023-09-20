<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	
	<title><template:block name="title"></template:block></title>
	
	<script type="text/javascript">
	var Com_Parameter = {
		ContextPath:"${LUI_ContextPath}"
	};
	var i18n = {
		'sys.profile.behavior.prepage':'<bean:message key="sys.profile.behavior.prepage" bundle="sys-profile-behavior" />',
		'sys.profile.behavior.nxtpage':'<bean:message key="sys.profile.behavior.nxtpage" bundle="sys-profile-behavior" />',
		'sys.profile.behavior.page':'<bean:message key="sys.profile.behavior.page" bundle="sys-profile-behavior" />',
		'sys.profile.behavior.no':'<bean:message key="sys.profile.behavior.no" bundle="sys-profile-behavior" />'
	};
	</script>
	
	<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/profile/maintenance/behavior/css/beh.css">
	<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/profile/maintenance/behavior/fonts/iconfont.css">
	
	<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/profile/maintenance/behavior/css/common.css">
	<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/profile/maintenance/behavior/css/sui.css">
	
	<template:block name="head"></template:block>
</head>
<body class="beh-bg-gray">
    <!--侧边导航栏 Starts-->
  <div class="beh-aside">
    <!--折叠面板 Starts-->
    
    <!-- 统计分析---菜单 -->
    <div style="display: none;">
	    <div class="beh-aside-title"><bean:message key="sys.profile.behavior.statistical" bundle="sys-profile-behavior" /></div>
	    <dl class="beh-aside-accordion">
	      <dt class="beh-aside-accordion-heading">
	        <a class="beh-collapsed" href="javascript:void(0);">
	          <span class="heading-icon">
	            <i class="fontmui mui-dashboard_line"></i>
	          </span>
	          <bean:message key="sys.profile.behavior.flow" bundle="sys-profile-behavior" />
	          <span class="accordion-toggle-arrow">
	            <i class="fontmui mui-jiantouxiangxia"></i>
	          </span>
	        </a>
	      </dt>
	      <dd class="beh-aside-accordion-body">
	        <a id="0__online" href="online.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.online') }"><bean:message key="sys.profile.behavior.online" bundle="sys-profile-behavior" /></a>
	        <a id="0__trend" href="trend.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.trend') }"><bean:message key="sys.profile.behavior.trend" bundle="sys-profile-behavior" /></a>
	      </dd>
	      <dt class="beh-aside-accordion-heading">
	        <a class="beh-collapsed" href="javascript:void(0);">
	          <span class="heading-icon">
	            <i class="fontmui mui-yonghurenyuan"></i>
	          </span>
	          <bean:message key="sys.profile.behavior.user" bundle="sys-profile-behavior" />
	          <span class="accordion-toggle-arrow">
	            <i class="fontmui mui-jiantouxiangxia"></i>
	          </span>
	        </a>
	      </dt>
	      <dd class="beh-aside-accordion-body">
	        <a id="0__client" href="client.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.terminal') }"><bean:message key="sys.profile.behavior.terminal" bundle="sys-profile-behavior" /></a>
	        <a id="0__region" href="region.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.region') }"><bean:message key="sys.profile.behavior.region" bundle="sys-profile-behavior" /></a>
	      </dd>
	      <dt class="beh-aside-accordion-heading">
	        <a class="beh-collapsed" href="javascript:void(0);">
	          <span class="heading-icon">
	            <i class="fontmui mui-renwujishi"></i>
	          </span>
	         <bean:message key="sys.profile.behavior.usage" bundle="sys-profile-behavior" />
	          <span class="accordion-toggle-arrow">
	            <i class="fontmui mui-jiantouxiangxia"></i>
	          </span>
	        </a>
	      </dt>
	      <dd class="beh-aside-accordion-body">
	        <a id="0__portal" href="portal.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.portal') }"><bean:message key="sys.profile.behavior.portal" bundle="sys-profile-behavior" /></a>
	        <a id="0__search" href="search.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.search') }"><bean:message key="sys.profile.behavior.search" bundle="sys-profile-behavior" /></a>
	        <a id="0__module" href="module.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.module') }"><bean:message key="sys.profile.behavior.module" bundle="sys-profile-behavior" /></a>
	      </dd>
	      <dt class="beh-aside-accordion-heading">
	        <a class="beh-collapsed" href="javascript:void(0);">
	          <span class="heading-icon">
	            <i class="fontmui mui-tuwenshitu"></i>
	          </span>
	          <bean:message key="sys.profile.behavior.performance" bundle="sys-profile-behavior" />
	          <span class="accordion-toggle-arrow">
	            <i class="fontmui mui-jiantouxiangxia"></i>
	          </span>
	        </a>
	      </dt>
	      <dd class="beh-aside-accordion-body">
	        <a id="0__system" href="system.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.system.load') }"><bean:message key="sys.profile.behavior.system.load" bundle="sys-profile-behavior" /></a>
	        <a id="0__time" href="time.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.response.speed') }"><bean:message key="sys.profile.behavior.response.speed" bundle="sys-profile-behavior" /></a>
	      </dd>
	    </dl>
    </div>
    
    <!-- 系统设置---菜单 -->
    <div style="display: none;">
	    <div class="beh-aside-title"><bean:message key="sys.profile.behavior.system" bundle="sys-profile-behavior" /></div>
	    <dl class="beh-aside-accordion">
	      <dt class="beh-aside-accordion-heading">
	        <a class="beh-collapsed" href="javascript:void(0);">
	          <span class="heading-icon">
	            <i class="fontmui mui-shezhichilun"></i>
	          </span>
	          <bean:message key="sys.profile.behavior.system" bundle="sys-profile-behavior" />
	          <span class="accordion-toggle-arrow">
	            <i class="fontmui mui-jiantouxiangxia"></i>
	          </span>
	        </a>
	      </dt>
	      <dd class="beh-aside-accordion-body">
	        <a id="1__db" href="${LUI_ContextPath}/sys/profile/maintenance/behavior/behaviorSetting.do?method=db" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.database') }"><bean:message key="sys.profile.behavior.database" bundle="sys-profile-behavior" /></a>
	        <a id="1__name" href="name.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.named') }"><bean:message key="sys.profile.behavior.named" bundle="sys-profile-behavior" /></a>
	        <a id="1__job" href="${LUI_ContextPath}/sys/profile/maintenance/behavior/behaviorSetting.do?method=job" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.job') }"><bean:message key="sys.profile.behavior.job" bundle="sys-profile-behavior" /></a>
	        <a id="1__logfile" href="logfile.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.logfile') }"><bean:message key="sys.profile.behavior.logfile" bundle="sys-profile-behavior" /></a>
	        <a id="1__design" href="design.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.design') }"><bean:message key="sys.profile.behavior.design" bundle="sys-profile-behavior" /></a>
	        <a id="1__server" href="${LUI_ContextPath}/sys/profile/maintenance/behavior/behaviorSetting.do?method=server" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.server') }"><bean:message key="sys.profile.behavior.server" bundle="sys-profile-behavior" /></a>
	        <a id="1__password" href="password.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.password') }"><bean:message key="sys.profile.behavior.password" bundle="sys-profile-behavior" /></a>
	      </dd>
	    </dl>
    </div>
    <!--折叠面板 Ends-->
  </div>
  <!--侧边导航栏 Ends-->

  <!--页面头部 Starts-->
  <div class="beh-header">
    <!--Logo Starts-->
    <div class="beh-brand">
      <a href="javascript:void(0);"><img src="images/logo.png" alt="Landray"></a>
    </div>
    <!--Logo Ends-->
    <div class="beh-container beh-header-container">
      <!--导航栏 Starts-->
      <ul class="beh-navbar">
        <li>
          <a href="online.jsp" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.statistical') }">
            <span class="fontmui mui-tongjibaobiao"></span>
            <bean:message key="sys.profile.behavior.statistical" bundle="sys-profile-behavior" />
          </a>
        </li>
        <li>
          <a href="${LUI_ContextPath}/sys/profile/maintenance/behavior/behaviorSetting.do?method=db" title="${ lfn:message('sys-profile-behavior:sys.profile.behavior.system') }">
            <span class="fontmui mui-shezhichilun"></span>
           <bean:message key="sys.profile.behavior.system" bundle="sys-profile-behavior" />
          </a>
        </li>
      </ul>
      <!--导航栏 Ends-->
    </div>
    <!-- 头部右侧区域 Starts
    <div class="beh-header-right-wrap">
      <div class="beh-header-info-wrap">
        <a href="javascript:void(0);">
          <span class="fontmui mui-lingdang"></span>
          <div class="beh-header-info-tip">99</div>
        </a>
      </div>
      <div class="beh-avatar-container">
        <a href="javascript:void(0);">
          <div class="beh-avatar-wrap">
            <img src="./images/avatar1.png">
          </div>
          <p class="beh-user-name">用户名称</p>
        </a>
      </div>
    </div> -->
    <!-- 头部右侧区域 Ends -->
  </div>
  <!--页面头部 Ends-->

  <!--主体内容区域 Starts-->
  <div class="beh-article">
    <!--右侧内容 Starts-->
    <div class="beh-container">
      <!-- 筛选器 -->
      <template:block name="filter"></template:block>

      <div class="beh-container-body">
        <!-- 主体内容 -->
        <template:block name="body"></template:block>
      </div>
    </div>
    <!--右侧内容 Ends-->
  </div>
  <!--主体内容区域 Ends -->

  <script src="${LUI_ContextPath}/sys/profile/maintenance/behavior/js/jquery.js"></script>
  <script src="${LUI_ContextPath}/sys/profile/maintenance/behavior/js/beh-common.js"></script>
  <script src="${LUI_ContextPath}/sys/profile/maintenance/behavior/js/sui.js"></script>
  <script type="text/javascript">
    // 菜单聚焦，文档加载完成后，传入菜单ID
	function menu_focus(menuId) {
    	var index = menuId.substr(0, 1);
    	// 菜单区域
    	$(".beh-aside div").hide();
		$(".beh-aside div").children("div").eq(index).parent().show();
    	
		// 横向导航
		$(".beh-navbar li").removeClass('active');
		$(".beh-navbar li").eq(index).addClass('active');
		
    	// 纵向菜单
		var menu = $("#" + menuId);
		var parent = menu.parent().prev();
		parent.find("a").click();
		menu.click();
	}
    
    $(function() {
		$(document).on('sui.done', function() {
    		// 如果没有筛选器时，内容区域往上移动一些
			var filter = $(".beh-container-heading");
	    	var bodyHeight = 0;
			if(filter.length > 0) {
				bodyHeight = filter.height() + 20;
			}
			$(".beh-container-body").css("margin-top", bodyHeight + "px");
		});
    });
  </script>
  <template:block name="script"></template:block>
</body>
</html>