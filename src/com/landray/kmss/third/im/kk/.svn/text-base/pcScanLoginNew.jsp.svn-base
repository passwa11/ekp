<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KK扫码登录</title>
<script type="text/javascript">
	Com_IncludeFile("jquery.js");
</script>
<style>
.login_content {
	width: 320px;
	height: 350px;
	border: 1px solid #e8e8e8;
	border-radius: 5px;
	background: #f9f9f9;
	margin: 80px auto;
	overflow: hidden;
	position: relative;
}

.login_qrcode {
	text-align: center;
	margin: auto;
	margin-top: 15px;
}

.login_qrcode_text {
	margin-top: 5px;
	text-align: center;
	color: #898d90;
	font-size: 14px;
}

.login_qrcode_refresh {
	position: absolute;
	top: 0px;
	left: 50%;
	width: 210px;
	height: 210px;
	background: rgba(255, 255, 255, 0.9);
	z-index: 100;
	text-align: center;
	padding-top: 90px;
	color: #fa5b5b;
	font-size: 14px;
	margin-left: -105px;
	display: block;
}
</style>
</head>
<body>
	<input id="token" type="hidden" value="" />
	<div class="login_content">
		<div class="login_qrcode">
			<img src="" alt="" id="qrcode" width="280" height="280">
		</div>
		<div class="login_qrcode_text"
			data-spm-anchor-id="0.0.0.i0.2b7aa428HS2b3B">
			请使用KK扫描二维码登录 <img src="" alt="" width="10px" height="10px"> <span
				id="flesh" style="color: #38adff; cursor: pointer">刷新</span>
		</div>
		<div class="login_qrcode_refresh" style="display: none;">
			您的二维码已失效，<br>请点击下方刷新按钮
		</div>
	</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {	  	
    	qrcode();
    });
    
    //生成二维码
    function qrcode(){
    	$.get('<c:url value="/third/im/kk/user.do?method=getNewSign"/>', function (data) {
            if(data.result==0){
                $("#qrcode").attr("src", "data:image/png;base64," + data.qrcode.base64Qrcode);
                console.log(data.qrcode.expireTime)
                startLongPolling(data.qrcode.uuid,data.qrcode.expireTime);
            }else{
            	alert(data.errorMsg);
            }
        },'json');
    }
    
  //定时请求 每3s请求一次
   // timer = setInterval(startLongPolling(uuid,expireTime), 3000);

    //轮询 二维码登录 请求接口 应该是判断这个二维码是否被扫
    function startLongPolling(uuid,expireTime) {
        console.log("定时任务执行中!"); 
        if(expireTime<new Date().valueOf()){
            $('.login_qrcode_refresh').css('display','block');
            return;
        }
        $.get('<c:url value="/third/im/kk/user.do?method=longpolling&uuid="/>' + uuid, function (data) {
            if (data.code == 0) {         
                alert("二维码扫描成功");
                window.location.href= '<c:url value="/third/im/kk/user.do?method=ekpLogin&token="/>' + data.token
            }else if (data.code == 408) {
            	//继续轮询
                console.log("未扫码....");
                startLongPolling(uuid,expireTime);
            } else if (data.code == 102) {
                console.log(data.errorMsg);
                return;
            }else {
                alert("服务器异常!");
                return;
            }
        },'json');
    }
    
    //刷新扫码
    $("#flesh").click(function () {
    	qrcode();
    	 $('.login_qrcode_refresh').css('display','none');
    })


</script>
</html>