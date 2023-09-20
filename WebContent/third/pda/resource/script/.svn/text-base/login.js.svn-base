/**
* 获取Cookie值
* @param name cookie对应的key名称 
* @return
*/
function GetCookie(name) {
  var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
  if (arr != null) return decodeURIComponent(arr[2]);
  return null;
}

/**
* 移除Cookie值
* @param name cookie对应的key名称 
* @return
*/
function deleteCookie(name) {
  var expdate = new Date();
  expdate.setTime(expdate.getTime() - 86400 * 1000 * 1);
  document.cookie = name + "=;expires=" + expdate.toGMTString();
}

// 读取登录用户Cookie值设置到输入框中
var j_username = $('input[name="j_username"]')[0],
  j_password = $('input[name="j_password"]')[0];
var username = GetCookie("username");
if (username != null && username != "") {
  j_username.value = username;
}

// 提交按钮是否可用
window.checkSubmitButton = function() {
  var disable = true; //不可用标识
  var j_username = $('input[name="j_username"]')[0],
      j_password = $('input[name="j_password"]')[0];
  if ($.trim(j_username.value)!="" && $.trim(j_password.value)!="") {
    var j_validation_code = $('input[type="text"][name="j_validation_code"]');
    if (j_validation_code.length == 0) {
      disable = false;
    } else {
      j_validation_code = j_validation_code[0];
      if (j_validation_code.value) {
        disable = false;
      }
    }
  }
  disableSubmitBtn(disable);
};

function disableSubmitBtn(disable) {
  if (disable) {
	  $("#login_submit_btn").removeClass("clickable"); // 不允许点击提交按钮
  } else {
	  $("#login_submit_btn").addClass("clickable");  // 允许点击提交按钮
  }
}


// 设置登录成功后需要跳转的目标URL
var urlObj = $('input[type="hidden"][name="j_redirectto"]');
var hash = location.hash;
if (hash) urlObj[0].value = urlObj[0].value + hash;

//提交登陆
window.kmss_onsubmit = function() {
  var loginInput = "请输入用户名和密码";
  var j_username = $('[name="j_username"]')[0],j_password = $('[name="j_password"]')[0];
  if (j_username.value == "" || j_password.value == "") {
    return false;
  }
  var j_code = $('[name="j_validation_code"]');
  if (j_code.length > 0 && j_code[0].value == "") {
    return false;
  }
  var password = j_password.value;
  try {
    j_password.value = desEncrypt(j_password.value);
  } catch (e) {}
  var expdate = new Date();
  expdate.setTime(expdate.getTime() + 86400 * 1000 * 1);
  document.cookie = "saveinfo=1" + ";expires=" + expdate.toGMTString();
  document.cookie = "username=" + encodeURIComponent(j_username.value) + ";expires=" + expdate.toGMTString();
  return true;
};

// 跳转至PC端登录页面
window.toPCLoginPage = function() {
  var url = Com_Parameter.ContextPath + "login.jsp";
  $.post(Com_Parameter.ContextPath + "third/pda/access.jsp?access=0",{},
    function() {
      location.href = url;
    }
  )
};

/**
* 跳转至“忘记密码”页面
* @return
*/
window.forgetPwd = function() {
  event.stopPropagation();
  event.preventDefault();
  location.href = Com_Parameter.ContextPath + "sys/mobile/sys_mobile_retrieve_password/validateUser.jsp";
};


window.preLoading = function(_url) {
  if (_url == null || _url == "") return;
  try {
    var xmlhttp = {};
    if (window.XMLHttpRequest) {
      xmlhttp = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
      xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if (!Com_GetUrlParameter(_url, "s_cache")) {
      _url = Com_SetUrlParameter(_url, "s_cache", Com_Parameter.Cache);
    }
    xmlhttp.open("GET", _url, false);
    xmlhttp.setRequestHeader("Accept", "text/plain");
    xmlhttp.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
    xmlhttp.send(null);
  } catch (e) {}
};

var oriWindowHeight = $(window).height(); // 初始化页面时window的窗口高度
// 监听屏幕touchstart事件，根据滑动方向移动登录框
$('body').on('touchstart', function (touchStartEvent) {
    var touchStartEvent = touchStartEvent.originalEvent||touchStartEvent;
    var startX = touchStartEvent.changedTouches[0].pageX;
    var startY = touchStartEvent.changedTouches[0].pageY;
    $('body').on('touchmove', function (touchMoveEvent) {
    	if(oriWindowHeight!=$(window).height()){
    		$('.muiLoginFooter').hide(); // 滑动过程中隐藏页脚
    	}
    	var touchMoveEvent = touchMoveEvent.originalEvent||touchMoveEvent;
        var touch = touchMoveEvent.touches[0] || touchMoveEvent.changedTouches[0];
        if (touch.pageY - startY > 10) {
            $('.mui_ekp_portal_login_content').removeClass('edit');
            $('body').off('touchmove');
        } else if (startY - touch.pageY > 10) {
            $('.mui_ekp_portal_login_content').addClass('edit');
            $('body').off('touchmove');
        }
        setTimeout(function(){
        	resetFooterDisplay(); // 滑动样式动画执行完成后调用重置页脚显示
        }, 450); 
    });
}).on('touchend', function () {
    $('body').off('touchmove');
});


/**
* 绑定登录表单种各元素的事件
* @return
*/
function bindFormElementEvent(){
	//  密码输入框获取焦点时变更input类型
	$("input[name='j_password']").bind("focus",function(){
		this.type="password";
	});
	
	// 输入框获取焦点，上移登录表单至全屏，并显示输入框后面的删除图标
	$(".mui_ekp_portal_login_input").bind("focus",function(){
	    $(".mui_ekp_portal_login_content").addClass("edit");
	    if ($(this).val() != '') {
	        $(this).next(".mui_ekp_portal_login_btn_delete").addClass("btn_show");
	    }
	});
	
	// 输入框失去焦点，并隐藏输入框后面的删除图标
	$(".mui_ekp_portal_login_input").bind("blur",function(){
		$(this).next(".mui_ekp_portal_login_btn_delete").removeClass("btn_show");
	});

	// 输入框编辑状态(当用户输入字符后显示输入框后面的删除图标)
	$('.mui_ekp_portal_login_input').on('input propertychange', function () {
	    if ($(this).val() == '') {
	        $(this).next('.mui_ekp_portal_login_btn_delete').removeClass('btn_show');
	    } else {
	        $(this).next('.mui_ekp_portal_login_btn_delete').addClass('btn_show');
	    }
	    // 移除输入错误红色下边框线提醒样式
	    $('.mui_ekp_portal_login_input').removeClass('error');
	    window.checkSubmitButton();
	});

	// 输入框删除按钮
	$(".mui_ekp_portal_login_btn_delete").bind("click",function () {
	    $(this).prev().val('');  // 清空输入框的值
	    $(this).removeClass("btn_show"); // 隐藏删除按钮
	    window.checkSubmitButton();
	});
}



/**
* 重置页脚“电脑登录”显示或隐藏状态
* @return
*/
function resetFooterDisplay(){
	var $submitBtn = $(".mui_ekp_portal_login_content_btn_submit");
	var $footer = $('.muiLoginFooter');
	$footer.css("visibility","visible");
    if( $footer.offset().top < ($submitBtn.offset().top+$submitBtn.height()) ){
    	$footer.css("visibility","hidden");
    }
}

/** 绑定window resize事件（软键盘被弹起时会触发）
  * 页脚“电脑登录”是浮动在底部的，为防止软键盘弹起时浮动的页脚遮盖住输入框或登录按钮区域，当页脚浮动位置top值小于登录按钮的底部位置时隐藏页脚
 **/
var windowResize = function(){
	resetFooterDisplay();
};
window.addEventListener("resize",windowResize);



$(document).ready(function(){
	window.bindFormElementEvent();
	window.checkSubmitButton();
});