$(function(){
	$(".lnk-file-title").html($(".excel-tab-title .active>a").html());
	$(".excel-tab-title li>a").click(function(){
		$(".navbar-fixed-top").css("height","");
		$(".lnk-file-title").html($(this).html());
		$(".nav-collapse.collapse").height("0px");
		$(".btn.btn-navbar").addClass("collapsed");
		$(".nav-collapse.in.collapse").removeClass("in");
	})
	$(".btn-navbar").click(function(e) {
		$(".navbar-fixed-top").height(document.documentElement.clientHeight);
		if($(".nav-collapse").hasClass("in")){
			$(".navbar-fixed-top").css("height","");
		}
	});
	
	 $(window).resize(function(e){
		var screenWidth = document.documentElement.clientWidth || window.innerWidth;
		var titleWdith = $("#btnPrint").length > 0 ? screenWidth-180 : screenWidth-130;
		$(".lnk-file-title").css("width",""+titleWdith+"px")
	 }).resize()
})
function move1(direction){
	if(direction == 0){
		for (var i = $(".excel-tab-title li").length - 1; i > -1; i--) {
			if ($(".excel-tab-title li:eq("+i+")").css("display") == "none") {
				$(".excel-tab-title li:eq("+i+")").css("display","block");
				break;
			}
		}
	}
	else{
		if($(".excel-tab-title")[0].scrollHeight > 42 ){
			for (var i = 0; i < $(".excel-tab-title li").length; i++) {
				if ($(".excel-tab-title li:eq("+i+")").css("display") != "none") {
					$(".excel-tab-title li:eq("+i+")").css("display","none");
					break;
				}
			}
		}
	}
}

if(window.addEventListener){
	window.addEventListener('message',function(e){
		if(e.data == "printDoc"){
			printDoc(); ;
		}
	},false);
}else{
	window.attachEvent('message',function(e){
		if(e.data == "printDoc"){
			printDoc(); ;
		}
	},false);
}

function printDoc()
{
	if(getExplorer() == "IE"){
            pagesetup_null();
        }
        $(".navbar").hide();
	  $("body").css("padding-top","0px");
        window.print(); 
	  $(".navbar").show();
	  $("body").css("padding-top","60px");
}

function pagesetup_null(){                
    var hkey_root,hkey_path,hkey_key;
    hkey_root="HKEY_CURRENT_USER";
    hkey_path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
    try{
        var RegWsh = new ActiveXObject("WScript.Shell");
        hkey_key="header";
        RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
        hkey_key="footer";
        RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
    }catch(e){}
}

function getExplorer() {
    var explorer = window.navigator.userAgent ;
    //ie
    if (explorer.indexOf("MSIE") >= 0) {
        return "IE";
    }
    //firefox
    else if (explorer.indexOf("Firefox") >= 0) {
        return "Firefox";
    }
    //Chrome
    else if(explorer.indexOf("Chrome") >= 0){
        return "Chrome";
    }
    //Opera
    else if(explorer.indexOf("Opera") >= 0){
        return "Opera";
    }
    //Safari
    else if(explorer.indexOf("Safari") >= 0){
        return "Safari";
	}
}

function getUrlParameter(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                sURLVariables = sPageURL.split('&'),
                sParameterName,
                i;

        for (i = 0; i < sURLVariables.length; i++) {
          sParameterName = sURLVariables[i].split('=');

          if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
          }
        }
      }
      var isMeeting=getUrlParameter('meeting')!=null;
      var isSpeaker=isMeeting && getUrlParameter('speaker');
      var serverhost=getUrlParameter('serverhost');
      var intervalMessage;
      var topValue;
      if(isMeeting &&!isSpeaker) {
        window.addEventListener('message',function(e){
          var msg=JSON.parse(e.data);
          switch(msg.type){
            case 'page':{
              var pos = document.body.clientHeight * msg.param;
              $('html,body').animate({scrollTop:pos},0);
              break;
            }
          }
        },false);
      }
      if(isSpeaker) {
        document.onscroll = function() {
          if(intervalMessage == null) {
            intervalMessage = self.setInterval("bindScroll()",20)
          }
        }
      }

      function bindScroll() {
        if(document.body.scrollTop == topValue) {
          clearInterval(intervalMessage)
          intervalMessage = null;
        }else{
          topValue = document.body.scrollTop;
          var msg={};
          var totalHeight = document.body.clientHeight;
          var scrollDistance = $(window).scrollTop();
          msg.type='page';
          msg.param = scrollDistance/totalHeight;
          parent.postMessage(JSON.stringify(msg),serverhost);
        }
      }