<%@page import="com.landray.kmss.sys.webservice2.util.SysWsUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>

<%

	String JGBigVersion = JgWebOffice.getPDFBigVersion();
	if (null != JGBigVersion && JGBigVersion.equals(JgWebOffice.PDF_OCX_BIG_VERSION_2018)){
		//iWebPDF2018
		request.setAttribute("jgOcxCopyright",JgWebOffice.getPDF2018CopyRight());	
		request.setAttribute("jgPDF2018Url",JgWebOffice.getJGPdf2018URL());
		request.setAttribute("jgPDF2018Version",JgWebOffice.getJGPdf2018Version());
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
			var str = '';			
			var pdfObj = 'JGWebPdf_' + '${HtmlParam.fdKey}';
			str += '<object id="'+pdfObj+'"';
			var copyright='${jgOcxCopyright}';

			str += ' width="100%"';
			
			var attHeightFlag = '${JsParam.attHeight}';
			if (attHeightFlag==null || (attHeightFlag!=null && (attHeightFlag == '100%' || attHeightFlag == ''))) {
				str += ' height="570px"';
			} else {
				str += ' height="'+attHeightFlag+'"';
			}
			if ((window.ActiveXObject!=undefined) || (window.ActiveXObject!=null) ||"ActiveXObject" in window)
			{	
				var pdf2018url = '${jgPDF2018Url}';
				if (null != pdf2018url && pdf2018url == '/sys/attachment/plusin/iWebPDF2018.cab') {
					var urlPrefix = Com_Parameter.ContextPath;
					if(urlPrefix.substring(0,4) != 'http')
						urlPrefix = Com_GetCurDnsHost() + urlPrefix;
					pdf2018url = urlPrefix + "sys/attachment/plusin/iWebPDF2018.cab";
				}
				str += ' CLASSID="CLSID:7017318C-BC50-4DAF-9E4A-10AC8364C315" codebase="'+pdf2018url+'#version=${jgPDF2018Version}"';			
				str += '>';			
			}
			else if (browser == "chrome") 
			{
				str += ' clsid="CLSID:7017318C-BC50-4DAF-9E4A-10AC8364C315"';         
				str += ' type="application/kg-plugin"';                                   //KGChromePlugin插件type
				//str += ' type="application/kg-activex"';                                //iWebPlugin插件type
				str += ' OnWebOpenAfter="OnWebOpenAfter"';
				str += ' OnDelSignature="OnDelSignature"';
				str += ' OnAddSignature="OnAddSignature"';
				str += ' Copyright="' + copyright + '"';
				str += '>';			
			}
			else if (browser == "firefox") 
			{
				str += ' clsid="CLSID:7017318C-BC50-4DAF-9E4A-10AC8364C315"';         
				str += ' type="application/kg-activex"';                                //iWebPlugin插件type
				str += ' OnWebOpenAfter="OnWebOpenAfter"';
				str += ' OnDelSignature="OnDelSignature"';
				str += ' OnAddSignature="OnAddSignature"';
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
		String jgPDFUrl = JgWebOffice.getJGDownLoadUrl("pdf");
		if (null != jgPDFUrl && ("/sys/attachment/plusin/iWebPDF.cab").equals(jgPDFUrl)) {
			jgPDFUrl = SysWsUtil.getUrlPrefix(request) + jgPDFUrl;
		}
		request.setAttribute("jgPDFUrl",jgPDFUrl);
		request.setAttribute("jgPDFVersion",JgWebOffice.getJGVersion("pdf"));
		request.setAttribute("jgMulVersion",JgWebOffice.getJGVersion("mul"));
		%>
		<c:if test="${isIE}">
			<object id="JGWebPdf_${HtmlParam.fdKey}" width="100%"
				classid="clsid:39E08D82-C8AC-4934-BE07-F6E816FD47A1"
				codebase="<c:url value='${jgPDFUrl}'/>#version=<c:url value='${jgPDFVersion}'/>" 
				<c:if test="${HtmlParam.attHeight==null || (HtmlParam.attHeight!=null && HtmlParam.attHeight == '100%')}">height="570px"</c:if>
				<c:if test="${HtmlParam.attHeight!=null}">height="${HtmlParam.attHeight}"</c:if>
				VIEWASTEXT>
			</object>
		</c:if>
		<c:if test="${!isIE}">
			<object id="JGWebPdf_${HtmlParam.fdKey}" width="100%" 
				type="application/kg-activex" 
				clsid="{39E08D82-C8AC-4934-BE07-F6E816FD47A1}" 
				copyright="${jgMulVersion}" 
				<c:if test="${HtmlParam.attHeight==null || (HtmlParam.attHeight!=null && HtmlParam.attHeight == '100%')}">height="570px"</c:if>
				<c:if test="${HtmlParam.attHeight!=null}">height="${HtmlParam.attHeight}"</c:if>
				VIEWASTEXT>
			</object>
		</c:if>
		<%
	}

%>
