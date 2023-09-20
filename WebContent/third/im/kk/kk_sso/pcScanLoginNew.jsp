<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/htmlhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KK扫码登录</title>
<script type="text/javascript">
	Com_IncludeFile("jquery.js");
</script>
<style>
#qr {
	width: 100%;
	height: 146px;
	text-align: center;
	position: relative;
}

#qrcodeImg {
	width: 146px;
	height: 146px;
	display: inline-block;
}

#qr .qrcodeTips {
	font-size: 12px;
	color: #333333;
	position: absolute;
	height: 18px;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	margin: auto;
}

.qr-reload {
	color: #38acff;
	cursor: pointer;
	text-decoration: none;
}
</style>
</head>
<body>
	<input id="token" type="hidden" value="" />
	<div id="qr">
		<div id="qrcodeImg" class="mask">
			<img src="" alt="" id="qrcode" width="150" height="150">
		</div>
		<p class="qrcodeTips" style="display: none;">
			已过期, <a class="qr-reload" href="javascript:;">点击刷新</a>
		</p>
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
        
        if(expireTime<new Date().valueOf()){
            $('.qrcodeTips').css('display','block');
            $('#qrcodeImg.mask').css('opacity','0.1');
            return;
        }
        
        $.get('<c:url value="/third/im/kk/user.do?method=longpolling&uuid="/>' + uuid, function (data) {
        	
            if (data.code == 0) {         
            	
            	window.top.location.href= '<c:url value="/third/im/kk/user.do?method=ekpLogin&token="/>' + encodeURIComponent(data.token)

            }else if (data.code == 408) {
            	//继续轮询
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
    $(".qr-reload").click(function () {
    	qrcode();
        $('.qrcodeTips').css('display','none');
        $('#qrcodeImg.mask').attr("style","");

    })


</script>
</html>