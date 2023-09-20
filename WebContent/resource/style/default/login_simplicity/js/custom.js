// JavaScript custom code

$(function () {
	var screenWidth = document.body.scrollWidth;
	var screenHeight = document.body.scrollHeight;
	var lay_background_img = $(".lay_background_img");
	var login_iframe = $(".login_iframe");
	var iframe_height = login_iframe.height();

	//随机背景图
	var bg = Com_Parameter.ContextPath + 'resource/customization/images/login_simplicity/' + get_random_bg();
	$(".login-background-img > img").attr("src", bg);

	// 获取图片的真实宽高
	var img = new Image();
	img.src = bg;
	img.onload = function () {
		// debugger
		bgWidth = img.width;
		bgHeight = img.height;
		// 您必须指定你的背景图片的大小
		var FullscreenrOptions = {
			width: bgWidth || 1920,
			height: bgHeight || 1080,
			bgID: '#login-bgImg'
		};
		// 此处将会激活全屏背景
		jQuery.fn.fullscreenr(FullscreenrOptions);
	}

	//错误信息是否为空的判断
	var lui_login_message_div = $(".lui_login_message_div");
	var txt = lui_login_message_div.html();
	txt = $.trim(txt);
	if (txt == null || txt == "") {} else {
		lui_login_message_div.addClass("tip_message");
	}

	/*** 浏览器兼容性处理 ***/
	var ie = document.documentMode; /*** 文档模式 ***/
	if (/msie/.test(navigator.userAgent.toLowerCase()) && ie <= 8) {
		// $("").attr("placeholder")
		$('body').addClass('ltie8');
	}

	//增加删除小图标
	initFunction();

	$("body").on("click", ".icon_del", function (e) {
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
	window.onresize = function () {
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
	var detectCapsLock = function (event) {
		if (capital) return;
		var e = event || window.event;
		var keyCode = e.keyCode || e.which; // 按键的keyCode
		var isShift = e.shiftKey || (keyCode == 16) || false; // shift键是否按住
		if (((keyCode >= 65 && keyCode <= 90) && !isShift) // Caps Lock 打开，且没有按住shift键
			||
			((keyCode >= 97 && keyCode <= 122) && isShift) // Caps Lock 打开，且按住shift键
		) {
			capitalTip.show();
			capital = true;
		} else {
			capitalTip.hide();
		}
	};
	inputPWD.onkeypress = detectCapsLock;
	inputPWD.onkeyup = function (event) {
		var e = event || window.event;
		if (e.keyCode == 20 && capital) {
			capitalTip.toggle();
			return false;
		}
	};

	// 失去焦点
	$('.lui_login_input_password').blur(function () {
		capitalTip.hide();
		capital = false;
	});
}

function initFunction() {
	//添加删除按钮
	var input_txt;
	$("<i class='icon_del third_login_item_icon third_login_icon_del'></i>").insertAfter($(".lui_login_input_username")[0]);
	$("<i class='icon_del third_login_item_icon third_login_icon_del'></i>").insertAfter($(".lui_login_input_password")[0]);
	$("<i class='icon_user third_login_item_icon third_login_icon_name'></i>").insertBefore('.lui_login_input_username')[0];
	$("<i class='icon_pwd third_login_item_icon third_login_icon_pwd'></i>").insertBefore('.lui_login_input_password')[0];
	$("<i class='icon_verify third_login_item_icon third_login_icon_verify'></i>").insertBefore(".lui_login_input_vcode")[0]

	$(".lui_login_input_div input").each(function () {
		input_txt = $(this).val();
		input_txt = $.trim(input_txt);
		if (input_txt != "") {
			$(this).parent(".lui_login_input_div").addClass('show');
		}
	});

	$(".lui_login_input_div input").keyup(function () {
		input_txt = $(this).val();
		input_txt = $.trim(input_txt);
		if (input_txt != "") {
			$(this).parent(".lui_login_input_div").addClass('show');
		}
	});

	$(".lui_login_input_div input").change(function () {
		input_txt = $(this).val();
		input_txt = $.trim(input_txt);
		if (input_txt != "") {
			$(this).parent(".lui_login_input_div").addClass('show');
		}
	});

	$(".lui_login_input_div input").focus(function () {
		input_txt = $(this).val();
		input_txt = $.trim(input_txt);

		$(this).parent(".lui_login_input_div").addClass('input_focus');

		if (input_txt != "") {
			$(this).parent(".lui_login_input_div").addClass('show');
		}
	});

	$(".lui_login_input_div input").blur(function () {
		input_txt = $(this).val();
		input_txt = $.trim(input_txt);

		$(this).parent(".lui_login_input_div").removeClass('input_focus');
		if (input_txt == "") {
			$(this).parent(".lui_login_input_div").removeClass('show');
		}
	});

}

//多语言
function mulit_lang() {
	var select = $(".lui_login_input_div select");
	var radio = $(".lui_login_input_div input[type='radio']");
	if (select != null && select.length > 0) {
		mulit_select();
	}
	if (radio != null && radio.length > 0) {
		mulit_radio();
	}
	// 如果有定制的链接，需要显示顶部菜单
	var links = $(".login_top_bar .login_head_link");
	if (links != null && links.length > 0) {
		$(".login_header .login_top_bar").css("display", "block");
	}
}

//多语言 为下拉框时
function mulit_select() {
	var select = $(".lui_login_input_div select");
	$(".login_header .login_top_bar").css("display", "block");
	var options = select.find("option");
	var uli = "",
		val = "";
	var ul = $("<ul class='header_lan'>");
	options.each(function (i) {
		val = $(this).val();
		txt = $(this).text();
		uli += "<li><a onclick=changeLang('" + val + "') data-value='" + val + "' href='javascript:void(0);'>" + txt + "</a></li>";
	});
	ul.html(uli);
	$(".login_header .login_top_bar").prepend(ul);
}

//多语言 为单选框时
function mulit_radio() {
	var radio = [];
	radio = $(".lui_login_input_div input[type='radio']");
	$(".login_header .login_top_bar").css("display", "block");
	var uli = "",
		val = "";
	var ul = $("<ul class='header_lan'>");
	radio.each(function (i) {
		val = $(this).val();
		txt = $(this).next("label").text();
		uli += "<li><a onclick=changeLang('" + val + "') data-value='" + val + "' href='javascript:void(0);'>" + txt + "</a></li>";
	});
	ul.html(uli);
	$(".login_header .login_top_bar").prepend(ul);
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
	if (ul.length > 0) {
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

		options.each(function (i) {
			val = $(this).val();
			txt = $(this).text();

			if ($(".lui_login_input_div select option:selected").val() != val)
				uli += "<li><a data-value='" + val + "' href='javascript:void(0);'>" + txt + "</a></li>";
		});
		obj.addClass("current");
		$(ul).html(uli);
		$("body").append(ul);
	}

	$(document).click(function () {
		$(ul).remove();
		obj.removeClass("current");
	});
}