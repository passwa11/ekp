$(function(){
	$('#containter').fullpage({ 
        continuousVertical: true,
        menu: '#menu',
        navigation: true,
        css3: true,
        touchSensitivity: 200,
        afterLoad: function (anchorLink, index) {
            if (index == 1) {
                $(".login_header .btn_login").css("display", "none");$(".multi_sel").css("display","block");
            }
            else { $(".login_header .btn_login").css("display", "block"); $(".multi_sel").css("display","none");}
        }
    });
    /*** 浏览器兼容性处理 ***/
    var ie = document.documentMode; /*** 文档模式 ***/
    if (/msie/.test(navigator.userAgent.toLowerCase()) && ie < 10) {
        $('body').addClass('ltie10');
    }
    if (/msie/.test(navigator.userAgent.toLowerCase()) && ie < 9) {
        $('body').addClass('ltie9');
        $(".section").css("background", "none");
        var h = $(".section").height();
        $(".section_Bg").css({ "height": h, "display": "inline-block" });
    }
    
    function setWidth(width) {
        if (/msie/.test(navigator.userAgent.toLowerCase()) && ie < 9) {
            $('body').addClass('ltie9');
            $(".section").css("background", "none");
            var h = $("body").height(); 
            $(".section_Bg").css({ "height": h, "display": "inline-block" });
        }
        if (width < 1200) {
            $('body').addClass('bodySmall');
            $(".main_content").css("width", "90%");
        }
        else {
            $('body').removeClass('bodySmall');
        }
    }
    var width = document.body.clientWidth;
    setWidth(width);
    $(window).resize(function () {
        width = document.body.clientWidth;
        setWidth(width);
    });

    $.fn.hoverDelay = function (options) {
        var defaults = {
            hoverDuring: 200,
            outDuring: 200,
            hoverEvent: function () {
                $.noop();
            },
            outEvent: function () {
                $.noop();
            }
        };
        var sets = $.extend(defaults, options || {});
        var hoverTimer, outTimer;
        return $(this).each(function () {
            $(this).hover(function () {
                clearTimeout(outTimer);
                hoverTimer = setTimeout(sets.hoverEvent, sets.hoverDuring);
            }, function () {
                clearTimeout(hoverTimer);
                outTimer = setTimeout(sets.outEvent, sets.outDuring);
            });
        });
    }
    
   //修复IE下滚动问题 #21182
   $(function(){
	   $('input').focus(function(){
		   $('#containter').fullpage.setKeyboardScrolling(false);
	    }).blur(function(){
	    	$('#containter').fullpage.setKeyboardScrolling(true);
	    });
   });
   
  //二维码框
  var oDiv = document.createElement("div");
    oDiv.id = "lui_common_divlayout";
    oDiv.className = "lui_common_divlayout"; //动态增加类名
    // 二维码呈现事件
    $(".login_code").hoverDelay({
        outDuring: 100,
        hoverEvent: function () {
    		removeDropdown(); // 隐藏多语言
            document.body.appendChild(oDiv);
            $(".login_code_wapper").css("display", "block").addClass("zoomIn");
        },
        outEvent: function () {
            try{document.body.removeChild(oDiv);}catch(e){}
            $(".login_code_wapper").css("display", "none").removeClass("zoomIn");
        }
    });
    
    // 多语言呈现事件
    $(".multi_sel").hoverDelay({
        outDuring: 100,
        hoverEvent: function () {
            $("a.dropdown").click();
        },
        outEvent: function () {
        	var select = $('.login_form_item_lang').find('select');
			var className = select.attr("class");

			var ul = document.getElementById("dropdownOptionsFor" + className);
			if(!ul) {
				return;
			}
			ul.onmouseout = function(event) {
				try {
					var x = event.pageX;
					var y = event.pageY;
					var ulx1 = ul.offsetLeft;
					var uly1 = ul.offsetTop;
					var ulx2 = ul.offsetLeft + ul.offsetWidth;
					var uly2 = ul.offsetTop + ul.offsetHeight;
					if (x < ulx1 || x > ulx2 || y < uly1 || y > uly2) {
						removeDropdown();
					}
				} catch (e) {}
			}
        }
    });

	// 切换语言
	if(isShowLanguage == "true") {
		var langContainer = $('.login_form_item_lang'),
			select = $('.login_form_item_lang:first').find('select');
		langContainer.hide();
	    jQuery.fn.extend({
	        dropdown: function () {
	            return this.each(function (i, obj) {
	                var obj = $(this);
	                var top = obj.offset().top;
	                var left = obj.offset().left;
	                var height = obj.height();
	                var width = obj.width();
	                //var select = obj.prev("select");
	                var options = select.find("option");
	                var nheight = options.height();
	                var className = select.attr("class");
	
	                var uli = "", val = "", txt = "";
	                var ul = $("#dropdownOptionsFor" + className);
	                $(".multi_sel .dropdown").addClass("sel");
	                if (ul.length > 0) {
	                	ul.remove();
	                	$(".multi_sel .dropdown").removeClass("sel");
	                } else {
	                	$(".dropdown-options").remove();
	                    ul = document.createElement("ul");
	                    $(ul).attr("id", "dropdownOptionsFor" + className).addClass("dropdown-options").css({ position: "absolute", left: left, top: top + height, width: width });

	                    options.each(function (i) {
	                        val = $(this).val();
	                        txt = $(this).text();
	                        uli += "<li><a data-value='" + val + "' href='javascript:void(0);'>" + txt + "</a></li>";
	                    });
	                    $(ul).html(uli);  
	                    $(".multi_sel .dropdown").addClass("sel");
	                    $("body").append(ul);
	                }
	                
	                $(document).on("click", "li a", function() {
	                	val = $(this).attr("data-value");
	                    txt = $(this).html();
	                    select.val(val);
	                    $("span.dropdown-input", obj).html(txt);
	                    $(ul).remove();
	                    $(".multi_sel .dropdown").removeClass("sel");
	                    changeLang(val);
	                   /* var url = document.location.href;
						url = Com_SetUrlParameter(url, "j_lang", val);
						url = Com_SetUrlParameter(url, "username", document.getElementsByName("j_username")[0].value);
						location.href = url;*/
					});
	
	                $(document).click(function () { $(ul).remove(); $(".multi_sel .dropdown").removeClass("sel");});
	            });
	        }
	    });
	
		// 下拉框
		$(document).on("click", "a.dropdown", function() {
			$(this).dropdown();
		});
	    // 设置默认语言
	    $("span.dropdown-input").html(select.find('option[value="'+lang+'"]').text() );
	    $('.btn_login').text( loginText );
	}
});

function removeDropdown() {
	var select = $('.login_form_item_lang').find('select');
	var className = select.attr("class");
	var ul = $("#dropdownOptionsFor" + className);
	$(ul).remove(); 
	$(".multi_sel .dropdown").removeClass("sel");
}

//登陆表单垂直居中
$(function(){
	var __formWapper = $('.login_form_wapper'),
		__formHeight = __formWapper.height();
	__formWapper.css('margin-top', 0 - __formHeight/2);
});
	
//显示遮罩层
function show_shade(id) {
	var __form = $('#login_form'),
		__dialogform = $('#login_dialog_form'); 
		html = __form.find('form[name="login_form"]').clone(true); // 获取登录表单
	if(window.Vue == null) {
		__form.empty(); // 清空原有表单
		__dialogform.html(html); // 创建新的表单
	}
    
	
	//遮罩层垂直居中
	var __formDialogWapper = $('.login_popup_wapper'),
		__formDialogHeight = __formDialogWapper.height();
	__formDialogWapper.css('margin-top',0 - __formDialogHeight/2 + 30 );
	
    if ($(".btn_login").attr("status") == "off") {
        var oDiv = document.createElement("div");
        oDiv.id = "lui_common_divlayout";
        oDiv.className = "lui_common_divlayout"; //动态增加类名
        $("." + id).addClass("zoomIn");
        document.body.appendChild(oDiv);
        $(".btn_login").attr("status", "on").css("cursor", "default");
    }
    
    // 打开弹窗登录时，光标定位到输入框
    setTimeout(inputFocus, 500);
}

//关闭遮罩层
function close_shade(id) {
	var __form = $('#login_form'),
		__dialogform = $('#login_dialog_form'),
		html = __dialogform.find('form[name="login_form"]').clone(true); // 获取登录表单
	if(window.Vue == null) {
		__dialogform.empty(); // 清空原有表单
		__form.html(html); // 创建新的表单
	}
	
    if ($(".btn_login").attr("status") == "on") {
        var divlayout = document.getElementById("lui_common_divlayout");
        if (divlayout) {
            document.body.removeChild(divlayout);
            $("." + id).removeClass("zoomIn");
        }
        $(".btn_login").attr("status", "off").css("cursor", "pointer");
    }
}
