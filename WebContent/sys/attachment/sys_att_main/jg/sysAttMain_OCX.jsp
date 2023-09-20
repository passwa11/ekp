<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.webservice2.util.SysWsUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>

<div id="noSupport_${HtmlParam.fdKey}"  style="display:none;" >
	<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_prompt_noInstall.jsp" charEncoding="UTF-8">
	</c:import>
</div>

<%
	String clsid = ResourceUtil.getKmssConfigString("sys.att.jg.clsid");
	//控件类型
	String plugintype = ResourceUtil.getKmssConfigString("sys.att.jg.plugintype");
	
	if(StringUtil.isNull(clsid)){
		clsid = "8B23EA28-2009-402F-92C4-59BE0E063499";
		if ("2003".equals(plugintype)) {
			clsid = "23739A7E-5741-4D1C-88D5-D50B18F7C347";
		}
	}
	request.setAttribute("clsid",clsid);
	
	String JGBigVersion = JgWebOffice.getJGBigVersion();
	if("windows".equals(JgWebOffice.getOSType(request))){
		if (null != JGBigVersion && JGBigVersion.equals(JgWebOffice.JG_OCX_BIG_VERSION_2015)){
			//2015
			request.setAttribute("jgOcxUrl",JgWebOffice.getJGOcxURL2015());
			request.setAttribute("jgOcxVersion",JgWebOffice.getJGOcxVersion2015());
			request.setAttribute("jgOcxCopyright",JgWebOffice.getJGOcxCopyRight2015());
			%>
				<script>
					var userAgent = navigator.userAgent, 
					rMsie = /(msie\s|trident.*rv:)([\w.]+)/, 
					rFirefox = /(firefox)\/([\w.]+)/, 
					rOpera = /(opera).+version\/([\w.]+)/, 
					rChrome = /(chrome)\/([\w.]+)/, 
					rSafari = /version\/([\w.]+).*(safari)/;
					var browser;
					var version;
					var ua = userAgent.toLowerCase();
					function uaMatch(ua) {
						var match = rMsie.exec(ua);
						if (match != null) {
							return { browser : "IE", version : match[2] || "0" };
						}
						var match = rFirefox.exec(ua);
						if (match != null) {
							return { browser : match[1] || "", version : match[2] || "0" };
						}
						var match = rOpera.exec(ua);
						if (match != null) {
							return { browser : match[1] || "", version : match[2] || "0" };
						}
						var match = rChrome.exec(ua);
						if (match != null) {
							return { browser : match[1] || "", version : match[2] || "0" };
						}
						var match = rSafari.exec(ua);
						if (match != null) {
							return { browser : match[2] || "", version : match[1] || "0" };
						}
						if (match != null) {
							return { browser : "", version : "0" };
						}
					}
					var browserMatch = uaMatch(userAgent.toLowerCase());
					if (browserMatch.browser) {
						browser = browserMatch.browser;
						version = browserMatch.version;
					}
					
					var chromeVersion = version.substring(0,2);
					
					var str = '';
					
					var ocxObj = 'JGWebOffice_' + '${HtmlParam.fdKey}';
					
					var copyright='${jgOcxCopyright}';
					
					str += '<object id="'+ocxObj+'"';

					var attWidthFlag = '${param.attWidth}';
					if (attWidthFlag == null || (null != attWidthFlag && attWidthFlag == "")) {
						str += ' width="100%"';
					} else {
						str += ' width="'+attWidthFlag+'"';
					}
					
					var fdAttTypeFlag = '${HtmlParam.fdAttType}';
					if (fdAttTypeFlag != 'office') {
						str += ' style="display:none"';
					}
					
					var attHeightFlag = '${JsParam.attHeight}';
					if (attHeightFlag == null || (null != attHeightFlag && attHeightFlag == "")) {
						str += ' height="95%"';
					} else {
						str += ' height="'+attHeightFlag+'"';
					}
					
					if ((window.ActiveXObject!=undefined) || (window.ActiveXObject!=null) ||"ActiveXObject" in window)
					{
						var iweboffice2015_url = '${jgOcxUrl}';
						if (null != iweboffice2015_url && iweboffice2015_url == '/sys/attachment/plusin/iWebOffice2015.cab') {
							var urlPrefix = Com_Parameter.ContextPath;
							if(urlPrefix.substring(0,4) != 'http')
								urlPrefix = Com_GetCurDnsHost() + urlPrefix;
							iweboffice2015_url = urlPrefix + "sys/attachment/plusin/iWebOffice2015.cab";
						}
						if(window.navigator.platform == "Win32")
							str += ' CLASSID="CLSID:D89F482C-5045-4DB5-8C53-D2C9EE71D025"  codebase="<c:url value="' + iweboffice2015_url+ '"/>#version=${jgOcxVersion}"';
						if(window.navigator.platform == "Win64")
							str += ' CLASSID="CLSID:D89F482C-5045-4DB5-8C53-D2C9EE71D024"  codebase="<c:url value="' + iweboffice2015_url+ '"/>#version=${jgOcxVersion}"';
							str += '>';
							str += '<param name="Copyright" value="' + copyright + '">';
					}
					else if (browser == "chrome") 
					{
						str += ' clsid="CLSID:D89F482C-5045-4DB5-8C53-D2C9EE71D025"';   
						str += ' type="application/kg-plugin"';	
						str += ' OnCommand="OnCommand"';
						str += ' OnRightClickedWhenAnnotate="OnRightClickedWhenAnnotate"';
						str += ' OnSending="OnSending"';
						str += ' OnSendEnd="OnSendEnd"';
						str += ' OnRecvStart="OnRecvStart"';
						str += ' OnRecving="OnRecving"';
						str += ' OnReady="OnReady"';
						str += ' OnRecvEnd="OnRecvEnd"';
						str += ' OnFullSizeBefore="OnFullSizeBefore"';
						str += ' OnFullSizeAfter="OnFullSizeAfter"';	
						str += ' Copyright="' + copyright + '"';
						str += '>';
					}
					else if (browser == "firefox") 
					{
						str += ' clsid="CLSID:D89F482C-5045-4DB5-8C53-D2C9EE71D025"';
						str += ' type="application/kg-activex"';
						str += ' OnCommand="OnCommand"';
						str += ' OnReady="OnReady"';
						str += ' OnOLECommand="OnOLECommand"';
						str += ' OnExecuteScripted="OnExecuteScripted"';
						str += ' OnQuit="OnQuit"';
						str += ' OnSendStart="OnSendStart"';
						str += ' OnSending="OnSending"';
						str += ' OnSendEnd="OnSendEnd"';
						str += ' OnRecvStart="OnRecvStart"';
						str += ' OnRecving="OnRecving"';
						str += ' OnRecvEnd="OnRecvEnd"';
						str += ' OnRightClickedWhenAnnotate="OnRightClickedWhenAnnotate"';
						str += ' OnFullSizeBefore="OnFullSizeBefore"';
						str += ' OnFullSizeAfter="OnFullSizeAfter"';	
						str += ' Copyright="' + copyright + '"';
						str += '>';
					}
					str += '</object>';
					document.write(str);
				</script>
			<%
		} else {
			//2009
			if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE")>-1){
				request.setAttribute("isIE",true);
			}else if(request.getHeader("User-Agent").toUpperCase().indexOf("TRIDENT")>-1){
				request.setAttribute("isIE",true);
			}else{
				request.setAttribute("isIE",false);
			}
			String plugOcxUrl = "";
			String plugOcxVersion = "";
			String plugMulVersion = "";
			if ("2003".equals(plugintype)) {
				//2003金格控件
				plugOcxUrl = JgWebOffice.getJGOcxURL2003();
				if (null != plugOcxUrl && ("/sys/attachment/plusin/iWebOffice2003.ocx").equals(plugOcxUrl)) {
					plugOcxUrl = SysWsUtil.getUrlPrefix(request) + plugOcxUrl;
				}
				plugOcxVersion = JgWebOffice.getJGOcxVersion2003();
				plugMulVersion = JgWebOffice.getJGVersion("mul");
			} else {
				//2009金格控件
				plugOcxUrl = JgWebOffice.getJGDownLoadUrl("ocx");
				if (null != plugOcxUrl && ("/sys/attachment/plusin/iWebOffice2009.cab").equals(plugOcxUrl)) {
					plugOcxUrl = SysWsUtil.getUrlPrefix(request) + plugOcxUrl;
				}
				plugOcxVersion = JgWebOffice.getJGVersion("ocx");
				plugMulVersion = JgWebOffice.getJGVersion("mul");
			}

			request.setAttribute("jgOcxUrl",plugOcxUrl);
			request.setAttribute("jgOcxVersion",plugOcxVersion);
			request.setAttribute("jgMulVersion",plugMulVersion);
			%>
				<c:if test="${isIE}">
					<object 
						id="JGWebOffice_${HtmlParam.fdKey}" 
						<c:if test="${(param.attWidth==null) || (null != param.attWidth && param.attWidth == '')}">width="100%"</c:if> 
						<c:if test="${param.attWidth!=null}">width="${HtmlParam.attWidth}"</c:if> 
						 classid="clsid:${clsid}"
						codebase="<c:url value='${jgOcxUrl}'/>#version=${jgOcxVersion}"
						<c:if test="${HtmlParam.attHeight==null}">height="95%"</c:if>
						<c:if test="${HtmlParam.attHeight!=null}">height="${HtmlParam.attHeight}"</c:if>
						<c:if test="${param.fdAttType != 'office'}"> style="display:none"</c:if>>
					</object>
				</c:if>
				<c:if test="${!isIE}">
					<object 
						id="JGWebOffice_${HtmlParam.fdKey}" 
						type="application/kg-activex" 
						<c:if test="${(param.attWidth==null) || (null != param.attWidth && param.attWidth == '')}">width="100%"</c:if> 
						<c:if test="${param.attWidth!=null}">width="${HtmlParam.attWidth}"</c:if> 
						 clsid="{${clsid}}" 
						copyright="${jgMulVersion}"
						<c:if test="${HtmlParam.attHeight==null}">height="95%"</c:if>
						<c:if test="${HtmlParam.attHeight!=null}">height="${HtmlParam.attHeight}"</c:if>
						<c:if test="${param.fdAttType != 'office'}"> style="display:none"</c:if>>
					</object>
				</c:if>
			<%
		}
	}else{
		if (null != JgWebOffice.getIsJGHandZzkkEnabled() && "true".equals(JgWebOffice.getIsJGHandZzkkEnabled())){
			//ZZKK
			request.setAttribute("jgOcxCopyright",JgWebOffice.getJGOcxCopyRightZZKK());
			%>
				<script>
					var str = '';
					
					var ocxObj = 'JGWebOffice_' + '${HtmlParam.fdKey}';
					
					var copyright='${jgOcxCopyright}';
					
					str += '<object id="'+ocxObj+'" type="application/iweboffice"';

					var attWidthFlag = '${param.attWidth}';
					if (attWidthFlag == null || (null != attWidthFlag && attWidthFlag == "")) {
						str += ' width="100%"';
					} else {
						str += ' width="'+attWidthFlag+'"';
					}
					
					var fdAttTypeFlag = '${HtmlParam.fdAttType}';
					if (fdAttTypeFlag != 'office') {
						str += ' style="display:none"';
					}
					
					var attHeightFlag = '${param.attHeight}';
					if (attHeightFlag == null || 
							(null != attHeightFlag && 
									(attHeightFlag == "" || attHeightFlag == "100%"))) {
						str += ' height="550px"';
					} else {
						str += ' height="'+attHeightFlag+'"';
					}
					str += '>';
					str += '<param name="Copyright" value="' + copyright + '">';
					str += '</object>';
					document.write(str);
				</script>
			<%
		}
	}
	

%>
