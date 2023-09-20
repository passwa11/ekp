$("body").prepend('<a id="fullScreen"  href="javascript:;"'
				+'style="width:40px;height:40px;display:block;position:fixed;z-index:5;cursor:pointer;'
				+'bottom:90px;right:10px;background:#000;opacity:0.5;" onClick="testFullScreen()">'
       				+'<img width="40" height="40" src="'+basePath+'/fullScreen.png">'
      +'</a>');

var sUserAgent = navigator.userAgent.toLowerCase();
   var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
   var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
   var bIsMidp = sUserAgent.match(/midp/i) == "midp";
   var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
   var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
   var bIsAndroid = sUserAgent.match(/android/i) == "android";
   var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
   var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
   if ((bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) ){
   		$("#fullScreen").css("display","none");
   }
if (document.addEventListener) {
document.addEventListener("fullscreenchange", function() {
    bindFullScreenChange(document.fullscreen);
});
document.addEventListener("mozfullscreenchange", function() {
    bindFullScreenChange(document.mozFullScreen);
});
document.addEventListener("webkitfullscreenchange", function() {
    bindFullScreenChange(document.webkitIsFullScreen);
});
document.addEventListener("MSFullscreenChange", function() {
    bindFullScreenChange(document.msFullscreenElement);
});
}
function bindFullScreenChange(){
	if(document.fullscreen || document.webkitIsFullScreen || document.mozFullScreen){
		$("#fullScreen").hide()
	}else{
		$("#fullScreen").show()
	}
}
function testFullScreen(){
	var docElm = document.documentElement;
        if (docElm.requestFullscreen) {
            docElm.requestFullscreen();
        }
        else if (docElm.mozRequestFullScreen) {
            docElm.mozRequestFullScreen();
       }
        else if (docElm.webkitRequestFullScreen) {
            docElm.webkitRequestFullScreen();
        }
        else if (docElm.msRequestFullscreen) {
           docElm.msRequestFullscreen();
       }
}