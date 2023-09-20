<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.simple">
	<template:replace name="title">
		扫码签到
	</template:replace>
	<template:replace name="head">
		<style>
		.qrcodeContainer{position: absolute;left: 50%;top: 50%;}
		.qrcodeTitle,.qrcodeText{text-align: center;padding-top: 10px;}
		.qrcodeTitle{font-size: 1.8rem;color: #4297ff;}
		</style>
	</template:replace>
	<template:replace name="content">
		<div id='qrcodeContainer' class='qrcodeContainer'>
			<div id='qrcodeMain' class='qrcodeMain'></div>
			<p class='qrcodeTitle'>${ lfn:message('sys-attend:sysAttend.sign.qrcode.title') }</p>
			<div class='qrcodeText'>${ lfn:message('sys-attend:sysAttend.sign.qrcode.text') }</div>
		</div>
		<script type="text/javascript">
			require(['dojo/dom-construct','dojo/dom-style','dojo/dom-geometry','dojo/query','mui/qrcode/QRcode','mui/util','dojo/request'],
					function(domConstruct,domStyle,domGeometry,query,qrcode,util,request){
				window.genQRCode = function(url){
					// JsParam默认解码了一次，不用decodeURIComponent
					var t = new Date().getTime();
					var postUrl = "/sys/attend/sys_attend_main/sysAttendMain.do?method=getDbTimeMillis&forward=lui-source";
					request(util.formatUrl(postUrl), {
						handleAs : 'json',
						method : 'post',
						data : '',
						async : false
					}).then(function(result){
						if (result.nowTime) {
							t = result.nowTime;
						}
					},function(e){
						window.console.log("获取数据失败,error:" + e);
					});
					
					var scanUrl = url + "&t=" + t;
					var qrCodeUpdateTime = parseInt(util.getUrlParameter(url, 'qrCodeTime')) * 1000;
					domConstruct.empty("qrcodeMain");
					
					var obj = qrcode.make({
						url : scanUrl
					});
					var qrcodeMain = query('#qrcodeMain');
					domConstruct.place(obj.domNode,qrcodeMain[0],'first'); 
					var qrcodeContainer = query('#qrcodeContainer')[0];
				
					domStyle.set(qrcodeContainer,'marginLeft', (0 - domGeometry.getContentBox(obj.domNode).w /2 ) + 'px');
					domStyle.set(qrcodeContainer,'marginTop', (0 - domGeometry.getContentBox(obj.domNode).h ) + 'px');
				
					window.setTimeout(function(){
						genQRCode(url);
			 		},qrCodeUpdateTime || 60000);
				}
				
				var url = '${JsParam.qrcodeurl}';
				if(url){
					genQRCode(url);
				}
				
			});
		</script>
	</template:replace>
</template:include>