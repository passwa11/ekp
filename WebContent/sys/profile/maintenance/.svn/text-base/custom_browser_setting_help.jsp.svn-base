<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="renderer" content="webkit">
		<link rel="stylesheet" href="css/custom_setting.css" />
		<link rel="shortcut icon" href="${LUI_ContextPath}/favicon.ico">
		<title>自定义兼容浏览器配置说明</title>
	</head>

	<body class="custom_body">
		<div class="custom_main_content">
			<h2 class="custom_title">自定义兼容浏览器配置说明</h2>
			<div class="custom_item_wrap">
				<p>Q：您要明白后台服务器根据什么信息才能得到用户使用的浏览器信息？</p>
				<p>A：后台服务器是根据UA(user-agent)信息判断用户使用的浏览器。UA信息是浏览器在请求页面时，会自动将该信息提交到服务器，服务器通过UA信息就能分析出用户使用的浏览器和版本号了。</p>
			</div>

			<div class="custom_item_wrap">
				<p>自定义兼容浏览器配置解答：</p>
				<p>我们系统已经内置了几款常见的浏览器，如果不能满足您的需求，您可以额外扩充其它浏览器，只需要输入浏览器名称（英文）和UA中的关键字即可。</p>
			</div>

			<div class="custom_item_wrap">
				<p>如何获取浏览器的UA信息及关键字：</p>
				<ul class="custom_list">
					<li>1、打开浏览器，按F12（或者找到“开发者工具”），在“控制台”输入：navigator.userAgent 或 <a href="${LUI_ContextPath}/resource/browser/show_browser_ua.html" target="_blank" class="com_btn_link">点击查看当前浏览器UA信息</a></li>
					<li>2、找到与您浏览器有关的关键字</li>
					<li><img src="images/customBrowser.png"></li>
				</ul>
			</div>

			<div class="custom_item_wrap custom_item_warnning">
				<p>注意：由于国产浏览器都是使用4大内核中的某几种，所以UA信息都是包含了代表产品中的信息，如果没有额外提供特定的信息，是无法判断其使用的浏览器。</p>
				<p>如：</p>
				<p>360浏览器使用了“Trident内核”和“WebKit内核”，而UA信息中并没有找到与360有关的关键字，所以无法判断为360浏览器。</p>
				<p>QQ浏览器虽然使用了“WebKit内核”，但其UA信息中包含了“QQBrowser”关键字（见上图），因此可以判断为QQ浏览器。</p>
			</div>

			<div class="custom_item_wrap">
				<h5 class="custom_sub_title">四大内核：</h5>
				<ul class="custom_list">
					<li>1、Trident内核，代表产品Internet Explorer</li>
					<li>2、Gecko内核，代表作品Mozilla FirefoxGecko</li>
					<li>3、WebKit内核，代表作品Safari、Chrome</li>
					<li>4、Presto内核，代表作品Opera</li>
				</ul>
			</div>
		</div>
	</body>
</html>