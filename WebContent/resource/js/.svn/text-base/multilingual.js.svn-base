var x=0;     //鼠标x的值
var y=0;     //鼠标y的值
var curLangMul=null; //当前选中的div
var y1 = 0;  //div上面两个点的y值
var y2 = 0;  //div下面两个点的y值
var x1 = 0;  //div左边两个点的x值
var x2 = 0;  //div右边两个点的x值
$(function() {
	if (!Array.prototype.forEach) {
		Array.prototype.forEach = function(callback/* , thisArg */) {
			var T, k;
			if (this == null) {
				throw new TypeError('this is null or not defined');
			}
			var O = Object(this);
			var len = O.length >>> 0;
			if (typeof callback !== 'function') {
				throw new TypeError(callback + ' is not a function');
			}
			if (arguments.length > 1) {
				T = arguments[1];
			}
			k = 0;
			while (k < len) {
				var kValue;
				if (k in O) {
					kValue = O[k];
					callback.call(T, kValue, k, O);
				}
				k++;
			}
		};
	}
	$(".langMul").each(function(index,element) {
		element.mark=index;
		checkLangStatus(this);
	});

	$(document).click(function(e){ 
		 x = pointerX(e);
	     y = pointerY(e); 
	     if(curLangMul!=null){
	    	 y1 = $(curLangMul).offset().top-10;
			 y2 = y1 + $(curLangMul).height()+10;
			 x1 = $(curLangMul).offset().left-10;
			 x2 = x1 + $(curLangMul).width()+10;
	     }
	     if( x < x1 || x > x2 || y < y1 || y > y2){
	    	 hideLangMul(curLangMul);
	     }
	});
	
});

function showLangMul(event, langMul) {
	if (langMul != null) {
		var src = event.srcElement ? event.srcElement : event.target;
		if (src.lang != undefined && src.lang != "") {
			checkLangStatus(langMul);
			if (official === src.lang) {
				if($(src)[0].localName==='input'||src.nodeName==='INPUT'){
					langs.forEach(function(t) {
						$(langMul).children(".input_lang_item." + t).show();
						$(langMul).children()
								.find(".input_lang_icon." + t + ".def").hide();
					});
				}else if($(src)[0].localName==='div'||src.nodeName==='DIV'){
					langs.forEach(function(t) {
						$(langMul).children(".input_lang_item." + t).hide();
						$(langMul).children()
						.find(".input_lang_icon." + t + ".def").show();
					});
					$(langMul).children().find(
							".input_lang_icon." + official + ".def").css(
							"background-color", "#000099");
					$(langMul).children().find(
							".input_lang_icon." + official + ".def").css("color",
							"#FFFFFF");
					Toast('请在当前输入框输入官方语言!',2000,pointerX(event),pointerY(event));
				}
			} else {
				langs.forEach(function(t) {
					// 如果当前语言被选择则显示当前语言隐藏其他语言
					if (t === src.lang) {
						$(langMul).children(".input_lang_item." + t).show();
						$(langMul).children().find(
								".input_lang_icon." + t + ".def").css(
								"background-color", "#000099");
						$(langMul).children().find(
								".input_lang_icon." + t + ".def").css("color",
								"#FFFFFF");
					} else {
						$(langMul).children(".input_lang_item." + t).hide();
					}
				});
			}
		}
		if(curLangMul==null){
			curLangMul=langMul;
		}else{
			if(curLangMul.mark!=langMul.mark){
				hideLangMul(curLangMul);
				curLangMul=langMul;
			}
		}
		
	}
}

function hideLangMul(langMul) {
	if (langMul != null) {
		checkLangStatus(langMul);
		langs.forEach(function(t) {
			$(langMul).children(".input_lang_item." + t).hide();
			$(langMul).children().find(".input_lang_icon." + t + ".def").show();
		});
	}
}

function checkLangStatus(langMul) {
	($(langMul).children().find("input[lang='" + official + "']").val()) ? changeLangStatus(
			'.input_lang_icon.' + official, true, langMul)
			: changeLangStatus('.input_lang_icon.' + official, false, langMul);
	langs
			.forEach(function(t) {
				($(langMul).children().find("input[lang_pull='" + t + "']")
						.val()) ? changeLangStatus('.input_lang_icon.' + t,
						true, langMul) : changeLangStatus('.input_lang_icon.'
						+ t, false, langMul);
			});
}

function changeLangStatus(langClassName, flag, langMul) {
	if (flag) {
		$(langMul).children().find(langClassName).css("color", "#4285f4");
		$(langMul).children().find(langClassName).css("background-color",
				"#D9E7FD");
	} else {
		$(langMul).children().find(langClassName).css("color", "#999");
		$(langMul).children().find(langClassName).css("background-color",
				"#DDD");
	}
}

function Toast(msg,duration,pageX,pageY){
    duration=isNaN(duration)?3000:duration;
    var m = document.createElement('div');
    m.innerHTML = msg;
    m.setAttribute("style","max-width:60%;min-width: 150px;padding:0 14px;height: 30px;color: #FFFFFF;line-height: 30px;text-align: center;border-radius: 1px;position: fixed;top: "+(pageY-38)+"px;left: "+pageX+"px;transform: translate(-50%, -50%);z-index: 999999;background: #47b5e8;font-size: 12px;");
    document.body.appendChild(m);
    setTimeout(function() {
      var d = 0.5;
      m.style.webkitTransition = '-webkit-transform ' + d + 's ease-in, opacity ' + d + 's ease-in';
      m.style.opacity = '0';
      setTimeout(function() { document.body.removeChild(m) }, d * 1000);
    }, duration);
}

function pointerX(event) {
   var docElement = document.documentElement,body = document.body || { scrollLeft: 0 };
   return event.pageX || (event.clientX +
      (docElement.scrollLeft || body.scrollLeft) -
      (docElement.clientLeft || 0));
}
	 
function pointerY(event) {
	var docElement = document.documentElement,body = document.body || { scrollTop: 0 };
	return  event.pageY || (event.clientY +
	   (docElement.scrollTop || body.scrollTop) -
	   (docElement.clientTop || 0));
}



