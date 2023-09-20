﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>	
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}third/im/kk/resource/css/main.css"/>
<script type="text/javascript" src="${KMSS_Parameter_ContextPath}resource/js/jquery.js"></script>
<script type="text/javascript">
$(function(){ 
	$.ajax({
        type: "post",
        url: "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=checkKKConfig",
        async : false,
        dataType: "json",
        success: function (data ,textStatus, jqXHR)
        {
            if(true == data.kkConfigFlag ){
               	window.location.href = "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=configView";
            }
        }
     });
})
</script>
</head>
<body id="body">
    <div class="right-modeleMain">
        <div class="integra-head">
            <h3></h3>
        </div>
        <div class="modelMain-box">
            <div class="left">
                <h3>${lfn:message('third-im-kk:kk.config.index.title')}</h3>
                <div class="logo-left"></div>
            </div>
            <div class="right">
                <p>${lfn:message('third-im-kk:kk.config.index.check')}：</p>
                <ul>
                    <li><span class="round-icon"></span>${lfn:message('third-im-kk:kk.config.index.zuzhi')}<span style="float: right;"><a href="${LUI_ContextPath}/sys/profile/index.jsp#org/organizational/" target="_bank">${lfn:message('third-im-kk:kk.config.index.config')}</a></span></li>
                    <li><span class="round-icon"></span>${lfn:message('third-im-kk:kk.config.index.fuwu')}<span style="float: right;"><a href="${LUI_ContextPath}/admin.do?method=config" target="_bank">${lfn:message('third-im-kk:kk.config.index.config')}</a></span></li>
                    <li><span class="round-icon"></span>${lfn:message('third-im-kk:kk.config.index.zuzhikejian')}<span style="float: right;"><a href="${LUI_ContextPath}/sys/profile/index.jsp#org/baseSetting/" target="_bank">${lfn:message('third-im-kk:kk.config.index.config')}</a></span></li>
                    <li><span class="round-icon"></span>${lfn:message('third-im-kk:kk.config.index.SSO')}<span style="float: right;"><a href="${LUI_ContextPath}/admin.do?method=config" target="_bank">${lfn:message('third-im-kk:kk.config.index.config')}</a></span></li>
                    <li><span class="round-icon"></span>${lfn:message('third-im-kk:kk.config.index.yidong')}<span style="float: right;"><a href="${LUI_ContextPath}/sys/profile/index.jsp#mobile/component/" target="_bank">${lfn:message('third-im-kk:kk.config.index.config')}</a></span></li>
                </ul>
                <div class="mean-deal">
                    <input id="confirm" type="checkbox" onclick="check();"/>&nbsp;<span><label for="confirm">${lfn:message('third-im-kk:kk.config.index.confirm')}</label></span>
                </div>
                <button id="openBtn" disabled="disabled" onclick="submit();">${lfn:message('third-im-kk:kk.config.index.connKK')}</button>
            </div>
        </div>
    </div>
 	<script type="text/javascript">
		function check(){
			if ($('#confirm').is(':checked')) {
				$('#openBtn').attr("disabled",false); 
			} else{
				$('#openBtn').attr("disabled","true"); 
			}
		}
		
		function submit(){
			$.ajax({
		        type: "post",
		        url: "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=checkUrl",
		        async : false,
		        dataType: "json",
		        success: function (data ,textStatus, jqXHR)
		        {
		            if(true == data.checkFlag ){
		               	window.location.href = "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=simpleConnectPage";
		            }else{
		                alert("请到admin.do中检查ekp内网、外网地址是否配置！");
		                return false;
		            }
		        },
		        error:function (XMLHttpRequest, textStatus, errorThrown) {      
		            alert("请求失败！");
		        }
		     });
		}
	</script>
</body>
</html>