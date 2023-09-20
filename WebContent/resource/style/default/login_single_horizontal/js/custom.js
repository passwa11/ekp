// JavaScript custom code

$(function() {
	var screenWidth = document.body.scrollWidth;
	var screenHeight = document.body.scrollHeight;
	var lay_background_img = $(".lay_background_img");
	var login_iframe = $(".login_iframe");
	var iframe_height = login_iframe.height();

	// 背景图片
	$(".login-background-img > img").attr("src", loginBackgroundImgPath);

	//二维码
	$(".login-dropbox").hover(function() {
		iframe_height = login_iframe.height();
		$(this).find(".login-dropbox-toggle > .iconbox").stop().animate({
			left: "-45px"
		}, 300).end().find(".login-dropbox-menu").css("height", iframe_height).stop(true, true).show(300);
	}, function() {
		$(this).find(".login-dropbox-toggle > .iconbox").stop().animate({
			left: "0"
		}, 300).end().find(".login-dropbox-menu").stop(true, true).hide(300);
	});

	//错误信息是否为空的判断
	var lui_login_message_div = $(".lui_login_message_div");
	var txt = lui_login_message_div.html();
	txt = $.trim(txt);
	if(txt == null || txt == "") {} else {
		lui_login_message_div.addClass("tip_message");
	}

	/*** 浏览器兼容性处理 ***/
	var ie = document.documentMode; /*** 文档模式 ***/
	if(/msie/.test(navigator.userAgent.toLowerCase()) && ie <= 8) {
		// $("").attr("placeholder")
		$('body').addClass('ltie8');
	}

	//增加删除小图标
	initFunction();

	$("body").on("click", ".icon_del", function(e) {
		$(this).siblings("input").val("").focus().parent(".lui_login_input_div").removeClass('show');
	});

	mulit_lang();
	
	captialTip();
	
});

//输入密码检测大写是否锁定
function captialTip() {
	var inputPWD = $('.lui_login_input_password');
	var left = inputPWD.offset().left;
	var top = inputPWD.offset().top;
	var h = inputPWD.height();
	top = top + h + 15;

	var tips = $(".tipsClass").css({
		left: left,
		top: top
	});
	window.onresize = function() {
		left = $('.lui_login_input_password').offset().left;
		top = $('.lui_login_input_password').offset().top;
		h = $('.lui_login_input_password').height();
		top = top + h + 18;

		tips = $(".tipsClass").css({
			left: left,
			top: top
		});
	};
	
	inputPWD = inputPWD[0];
	var capital = false;
	var capitalTip = $(".tipsClass");
	var detectCapsLock = function(event) {
		if (capital) return;
		var e = event || window.event;
		var keyCode = e.keyCode || e.which; // 按键的keyCode
		var isShift = e.shiftKey || (keyCode == 16) || false; // shift键是否按住
		if (((keyCode >= 65 && keyCode <= 90) && !isShift) // Caps Lock 打开，且没有按住shift键
				|| ((keyCode >= 97 && keyCode <= 122) && isShift)// Caps Lock 打开，且按住shift键
		) {
			capitalTip.show();
			capital = true;
		} else {
			capitalTip.hide();
		}
	};
	inputPWD.onkeypress = detectCapsLock;
	inputPWD.onkeyup = function(event) {
		var e = event || window.event;
		if (e.keyCode == 20 && capital) {
			capitalTip.toggle();
			return false;
		}
	};
	
	// 失去焦点
	$('.lui_login_input_password').blur(function() {
		capitalTip.hide();
		capital = false;
	});
}

function initFunction() {
	//添加删除按钮
	var input_txt;
	$("<i class='icon_del'></i>").insertAfter($(".lui_login_input_username")[0]);
	$("<i class='icon_del'></i>").insertAfter($(".lui_login_input_password")[0]);
	$("<i class='icon_user'></i>").insertBefore('.lui_login_input_username')[0];
	$("<i class='icon_pwd'></i>").insertBefore('.lui_login_input_password')[0];

	$(".lui_login_input_div input").each(function() {
		input_txt = $(this).val();
		input_txt = $.trim(input_txt);
		if(input_txt != "") {
			$(this).parent(".lui_login_input_div").addClass('show');
		}
	});

	$(".lui_login_input_div input").keyup(function() {
		input_txt = $(this).val();
		input_txt = $.trim(input_txt);
		if(input_txt != "") {
			$(this).parent(".lui_login_input_div").addClass('show');
		}
	});

	$(".lui_login_input_div input").change(function() {
		input_txt = $(this).val();
		input_txt = $.trim(input_txt);
		if(input_txt != "") {
			$(this).parent(".lui_login_input_div").addClass('show');
		}
	});

	$(".lui_login_input_div input").focus(function() {
		input_txt = $(this).val();
		input_txt = $.trim(input_txt);

		$(this).parent(".lui_login_input_div").addClass('input_focus');

		if(input_txt != "") {
			$(this).parent(".lui_login_input_div").addClass('show');
		}
	});

	$(".lui_login_input_div input").blur(function() {
		input_txt = $(this).val();
		input_txt = $.trim(input_txt);

		$(this).parent(".lui_login_input_div").removeClass('input_focus');
		if(input_txt == "") {
			$(this).parent(".lui_login_input_div").removeClass('show');
		}
	});

}

//多语言
function mulit_lang() {
	var select = $(".lui_login_input_div select");
	var radio = $(".lui_login_input_div input[type='radio']");
	if(select!=null && select.length > 0) {
		mulit_select();
	}
	if(radio!=null && radio.length > 0) {
		mulit_radio();
	}
}

//多语言 为下拉框时
function mulit_select() {
	var select = $(".lui_login_input_div select");
	if(select.length>0){
		$(".lui_change_lang_container").css("display", "block");
		var options = select.find("option");
		var uli = "",
			val = "";
		var ul = $("<ul class='header_lan'>");
		options.each(function(i) {
			val = $(this).val();
			txt = $(this).text();
			uli += "<li><a onclick=changeLang('" + val + "') data-value='" + val + "' href='javascript:void(0);'>" + txt + "</a></li>";
		});
		ul.html(uli);
		$(".lui_change_lang_container").prepend(ul);
	}
}

//多语言 为单选框时
function mulit_radio() {
	var radio = [];
	radio = $(".lui_login_input_div input[type='radio']");
	if(radio.length){
		$(".lui_change_lang_container").css("display", "block");
		var uli = "",
			val = "";
		var ul = $("<ul class='header_lan'>");
		radio.each(function(i) {
			val = $(this).val();
			txt = $(this).next("label").text();
			uli += "<li><a onclick=changeLang('" + val + "') data-value='" + val + "' href='javascript:void(0);'>" + txt + "</a></li>";
		});
		ul.html(uli);
		$(".lui_change_lang_container").prepend(ul);
	}
}

//美化的下拉框
function dropdown(obj) {
	var top = obj.offset().top;
	var left = obj.offset().left;
	var height = obj.height();
	var width = obj.width();
	var select = $(".lui_login_input_div select");

	var options = select.find("option");
	var nheight = options.height();
	var className = select.attr("class");

	var uli = "",
		val = "",
		txt = "";
	var ul = $("#dropdownOptionsFor" + className);
	if(ul.length > 0) {
		ul.remove();
		obj.removeClass("current");
	} else {
		$(".dropdown-options").remove();
		ul = document.createElement("ul");
		$(ul).attr("id", "dropdownOptionsFor" + className).addClass("dropdown-options").css({
			position: "absolute",
			left: left,
			top: top + height
		});

		options.each(function(i) {
			val = $(this).val();
			txt = $(this).text();

			if($(".lui_login_input_div select option:selected").val() != val)
				uli += "<li><a data-value='" + val + "' href='javascript:void(0);'>" + txt + "</a></li>";
		});
		obj.addClass("current");
		$(ul).html(uli);
		$("body").append(ul);
	}

	$(document).click(function() {
		$(ul).remove();
		obj.removeClass("current");
	});
}
