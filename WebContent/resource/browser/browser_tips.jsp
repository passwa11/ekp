<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.landray.kmss.sys.profile.model.PasswordSecurityConfig" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>

<%
	List<String> allBrowserList = PasswordSecurityConfig.newInstance().getAllBrowserCompatible();
	StringBuffer allBrowser = new StringBuffer();
	for(String browser : allBrowserList) {
		allBrowser.append(browser).append(",");
	}
	if(allBrowser.length() > 0) {
		allBrowser.deleteCharAt(allBrowser.length() - 1);
	}
%>

<!DOCTYPE html>
<html class="browserTips_html">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="renderer" content="webkit">
		<title><bean:message  bundle="sys-profile" key="sys.profile.browserCheck.title" /></title>
		<link rel="stylesheet" href="css/browser_tips.css" />
		<link rel="shortcut icon" href="${LUI_ContextPath}/favicon.ico">
		<script type="text/javascript">
			function showAllBrowser() {
				seajs.use([ 'lui/jquery' ], function($) {
					var allBrowser = "<%=allBrowser%>".split(",");
					var showBrowser = [];
					$.each(allBrowser, function(i, n) {
						if(n.replace(/(^\s*)|(\s*$)/g, "").length < 1) {
							return true;
						}
						var browser;
						switch(n) {
						case 'Chrome':
							browser = '<li><i class="browser_logo logo_chrome"></i><p class="text_chrome">Chrome</p></li>';
							break;
						case 'Opera':
							browser = '<li><i class="browser_logo logo_opera"></i><p class="text_opera">Opera</p></li>';
							break;
						case 'IE':
							browser = '<li><i class="browser_logo logo_ie"></i><p class="text_ie">IE 8+</p></li>';
							break;
						case 'IE10':
							browser = '<li><i class="browser_logo logo_ie10"></i><p class="text_ie">IE 10+</p></li>';
							break;
						case 'Edge':
							browser = '<li><i class="browser_logo logo_edge"></i><p class="text_ie">Edge</p></li>';
							break;
						case 'Firefox':
							browser = '<li><i class="browser_logo logo_firefox"></i><p class="text_firefox">Firefox</p></li>';
							break;
						case 'Safari':
							browser = '<li><i class="browser_logo logo_safari"></i><p class="text_safari">Safari</p></li>';
							break;
						default:
							browser = '<li><i class="browser_logo logo_other"></i><p class="text_other">' + n + '</p></li>';
							break;
						}
						showBrowser.push(browser);
					});
					if(showBrowser.length > 0) {
						$(".browserTips_list>ul").empty().append(showBrowser.join(""));
					} else {
						$(".browserTips_header").remove();
						$(".browserTips_content").empty().append('<div class="browserTips_content browserTips_forbid_wrap"><p class="browserTips_forbid"><i class="icon_forbid"></i><span><bean:message  bundle="sys-profile" key="sys.profile.browserCheck.forbidden" /></span></p></div>');
					}
					$(".browserTips_containter").show();
				});
			}
		</script>
	</head>

	<body class="browserTips_body" onload="showAllBrowser();">
		<div class="browserTips_containter" style="display: none;">
			<div class="browserTips_header"><span class="browserTips_icon"><i class="icon_warning"></i></span>
				<p><bean:message  bundle="sys-profile" key="sys.profile.browserCheck.tips1" /></p>
			</div>
			<div class="browserTips_content">
				<h4 class="browserTips_title">
					<bean:message  bundle="sys-profile" key="sys.profile.browserCheck.tips2" />
				</h4>
				<div class="browserTips_list">
					<ul>
					</ul>
				</div>
			</div>
		</div>
	</body>

</html>