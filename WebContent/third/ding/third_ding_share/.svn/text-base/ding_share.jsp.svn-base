<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page import="com.landray.kmss.third.ding.util.DingUtil"%>
<%@ page import="java.util.*,
	com.landray.kmss.util.*" %>
	
<%
	String domainName = DingConfig.newInstance().getDingDomain();
    String dingEnable = DingConfig.newInstance().getDingEnabled();
	if (StringUtil.isNull(domainName)) {
		domainName = ResourceUtil
				.getKmssConfigString("kmss.urlPrefix");
	}
	if(domainName.trim().endsWith("/"))
		domainName = domainName.trim().substring(0, domainName.length()-1);
	 String reqUrl=request.getParameter("reqUrl");
	 reqUrl =  SecureUtil.BASE64Encoder(reqUrl);	 
	 String subject=request.getParameter("fdSubject");
%>

<%
   if(StringUtil.isNotNull(dingEnable)&& "true".equals(dingEnable)){
%>	
	<div data-dojo-type="mui/tabbar/TabBarButton"
	     data-dojo-props='label:"分享",align:"${param.align}",modelId:"${param.fdModelId}",subject:"${lfn:escapeJs(param.fdSubject)}",modelName:"${param.fdModelName }"'
	     onclick="dingShare()">
	</div>
	<script>
	require([ 'dojo/ready', 'mui/device/adapter', 'dojo/query', "dojo/request","mui/dialog/Tip"],
			function(ready,adapter,query, request, Tip){

				window.dingShare = function(){
					var option={};
					option['corpId']='<%=DingUtil.getCorpId()%>';
					option['reqUrl']='<%=reqUrl%>';
					option['fdSubject']='<%=subject%>';
					option['fdContent']='${param.fdContent}';
					option['fdContentPro']='${param.fdContentPro}';
					option['fdModelName']='${param.fdModelName}';
					option['fdModelId']='${param.fdModelId}';
					adapter.share2ding(option);
				}

	})
	</script>
<%   
   }
%>