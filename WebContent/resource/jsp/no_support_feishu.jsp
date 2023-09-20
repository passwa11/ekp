<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ page import="com.landray.kmss.web.filter.ResourceCacheFilter"%>
<%@ page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/list.tld" prefix="list"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/kmss-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/kmss-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/sunbor.tld" prefix="sunbor"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
if(request.getAttribute("LUI_ContextPath")==null){
	String LUI_ContextPath = request.getContextPath();
	request.setAttribute("LUI_ContextPath", LUI_ContextPath);
	request.setAttribute("LUI_CurrentTheme",SysUiPluginUtil.getThemeById("default"));
	request.setAttribute("LUI_Cache",ResourceCacheFilter.cache);
	request.setAttribute("KMSS_Parameter_Style", "default");
	request.setAttribute("KMSS_Parameter_ContextPath", LUI_ContextPath+"/");
	request.setAttribute("KMSS_Parameter_ResPath", LUI_ContextPath+"/resource/");
	request.setAttribute("KMSS_Parameter_StylePath", LUI_ContextPath+"/resource/style/default/");
	request.setAttribute("KMSS_Parameter_CurrentUserId", UserUtil.getKMSSUser(request).getUserId());
	request.setAttribute("KMSS_Parameter_Lang", UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-'));
}
%>
<%@ page import="
	com.landray.kmss.util.KmssMessages,
	com.landray.kmss.util.KmssMessage,
	com.landray.kmss.util.KmssMessageWriter,
	com.landray.kmss.util.KmssReturnPage" %>
<%
try{
%>
<!doctype html>
<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath }/<%=(JSONObject.fromObject(SysUiPluginUtil.getThemes(request))).getJSONArray("prompt").get(0)%>"/>
		<script>
			seajs.use( [ 'lui/jquery'], function($) {
				$( function() {
					try {
						var arguObj = document.getElementsByTagName("div")[0];
						if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
							window.frameElement.style.height = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight) + "px";
						}
					} catch(e) {
					}
				});
			});
		</script>
		<script type="text/javascript" src="${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/scripts/shBrushBash.js"></script>
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/styles/shCore.css"/>
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/styles/shThemeDefault.css"/>
		<script type="text/javascript">
			SyntaxHighlighter.config.clipboardSwf = '${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/scripts/clipboard.swf';
			SyntaxHighlighter.all();
		</script>
		<script>
			function openInBrowser(){
				var url = window.localStorage.getItem("feishuCurrentUrl");
				// alert("openInBrowser=>" + window.localStorage.getItem("dingCurrentUrl"));
				window.open(url);
			}
		</script>
		<style>
			.model { 
				width:618px; 
				height:375px; 
				box-shadow:0 0 10px rgba(0,0,0,.4); 
				border-radius:2px; 
				padding:1em; 
				padding-top:0; 
				position:fixed; 
				z-index:100;
			 	background-color:#fff; 
			 	display:none;
			 }
			.model-header {
				border-bottom:1px solid #eaeaea; 
				height:35px; 
				line-height:35px; 
				text-align:center;
			}
			.close{ 
				position:absolute; 
				top:0; 
				right:15px; 
				height:35px; 
				line-height:35px; 
				text-align:center; 
				display:block; 
				color:#666; 
				cursor:pointer;
			}
			.close:hover{ 
				color:#A30D10;
			}
			.mask{ 
			    background: #000;
				width:100%; 
				height:100%; 
				opacity:.3; 
				position:absolute; 
				left:0; 
				top:0; 
				z-index:0; 
				display:none;
			}
			.no-support-msg{
				text-align: center;
			}
			.no-support-msg .pic{
				margin-top: 10px;
			}
			
			.no-support-msg .msg{
				margin: 20px;
			}
			
			.no-support-msg .btn{
			    background: #4285f4;
			    width: 150px;
			    margin: 10px auto;
			    color: #fff;
			    height: 25px;
			    font-size: 14px;
			    padding-top: 12px;
			    border-radius: 5px;
		        cursor: pointer;
			}
   		</style>
		<title>${lfn:message('return.systemInfo') }</title>
	</head>
	<body>
		<div class="model-body no-support-msg">	
			<div class="pic"><img src="${LUI_ContextPath }/resource/images/no_support_ding_big.png?s_cache=${ LUI_Cache }"></div>	
			<div class="msg">暂不支持在飞书PC端内操作，如有需要，请打开到外部浏览器</div>	
			<div class="btn"  onclick="openInBrowser()">打开到外部浏览器</div>
			
		</div>
	</body>
</html>
<%
}catch(Exception e){
	try{
		if(exception != null){
			e.printStackTrace();
			out.println("<br><br>"+exception);
			out.println("<pre>");
			exception.printStackTrace( new java.io.PrintWriter( out ) );
			out.println("</pre>");
		}
	}catch(Exception ex){
		ex.printStackTrace();
	}
}
%>