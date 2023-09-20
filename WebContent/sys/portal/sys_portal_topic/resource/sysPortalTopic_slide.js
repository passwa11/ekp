var element = render.parent.element;
var width = Math.max(element.parent().width(), 160);
var height = '';//element.parent().height();
var target = render.vars.target ? render.vars.target : '_blank';
var stretch = render.vars.stretch == null || render.vars.stretch == 'true';
var showTitle = render.vars.showTitle == null || render.vars.showTitle == 'true';
var SwitchingFrequency = 5000;
if(render.vars.SwitchingFrequency==null||render.vars.SwitchingFrequency=='0'||render.vars.SwitchingFrequency==''){
	SwitchingFrequency=0;
}else{
	SwitchingFrequency=parseInt(render.vars.SwitchingFrequency);
}
var htmlCode = "<div id='";
htmlCode += render.cid;
htmlCode += "-slide'><div class='lui_dataview_slide_content' style='";
if (width) {
	htmlCode += ["width:", width, "px;"].join('');
}
if (height) {
	htmlCode += ["height:", height, "px;"].join('');
}
htmlCode += "'>";


var content = data;
var size = 0;
if(data && data.length) {
	size = data.length;
}

if(size > 1) {
	htmlCode += "<div class='lui_dataview_slide_change lui_dataview_slide_pre'><i></i></div> " + 
				"<div class='lui_dataview_slide_change lui_dataview_slide_next'><i></i></div>";
}

var imgCode = "";
// 右下角按钮
var buttonCode = "";
if(showTitle) {
	buttonCode = "<ul>";
} else {
	buttonCode = "<ul class='lui_dataview_slide_btbar'>";
}
var context = "";
// 标题
var subjectCode = "";
var bgCode = "<div class='lui_dataview_slide_bg'></div>";
var shadow = "";
for (var i = 0; i < content.length; i++) {
	context += "<div class='lui_dataview_slide_imgDiv' style='height:100%;width:"
			+ width + "px'>";
	imgCode = [ '<a data-href = "', env.fn.formatUrl(content[i].href), '"',
			'target="', target, '" onclick="Com_OpenNewWindow(this)">', '<img src="',
			env.fn.formatUrl(content[i].image), '" alt="" ',
			(stretch ? 'style="width:100%; height:100%"' : ''), '/>', '</a>' ]
			.join('');
	context += imgCode;
	if (showTitle) {
		
		if(i==0){
			subjectCode +=  [ '<h3 data-index="' + i +'" style="display:inline-block">', env.fn.formatText(content[i].text), '</h3>' ]
			.join('');
		}else{
			subjectCode +=  [ '<h3 data-index="' + i +'" style="display:none">', env.fn.formatText(content[i].text), '</h3>' ]
			.join('');
		}
	}
	context += "</div>";
	if(i == 0) {
		shadow = context;
	}
	buttonCode += '<li></li>';
}
buttonCode += "</ul>";
if(context) {
	var containerWidth = (content.length + 1) * width || 0;
	htmlCode += "<div  class='lui_dataview_slide_container' style='height:100%;width:" 
			 +  containerWidth  +"px;'>" + context + shadow + "</div>";
}
htmlCode += subjectCode;
if(showTitle) {
	htmlCode += bgCode;
}
if(size > 1) {
	htmlCode += buttonCode;
}
htmlCode += "</div></div>";
done(htmlCode);

// 渲染完后绑定事件
~~function() {
	setTimeout(function(){
		var _height = element.parent().height();
		$("#" + render.cid + "-slide .lui_dataview_slide_content").css('height',_height+'px');
	},300);
	if(!size) {
		return;
	}
	var slideCache = [];
	$("#" + render.cid + "-slide ul li").each(function(c) {
				var slide = {};
				slide["cursor"] = $(this);
				this["pos"] = c;
				slideCache[c] = slide;
	});
	$("#" + render.cid + "-slide h3").each(function(c) {
		if(slideCache[c]) {
			slideCache[c]["text"] = $(this);
		}
	});
	
	var itemWidth = $("#" + render.cid + "-slide .lui_dataview_slide_content").width();
	var slideContainer = $("#" + render.cid + "-slide .lui_dataview_slide_container");

	var curpos = 0, timer;
	var slen = slideCache.length, curSlide = null, oSlide = null;
	
	function slideTo(init, auto, dir) {
		for (var c = 0; c < slen; c++) {
			if (c == curpos) {
				curSlide = slideCache[c];
				curSlide.cursor.addClass("lui_dataview_slide_on");
				if(curSlide.text)
					curSlide.text.css('display', 'inline-block');
			} else {
				oSlide = slideCache[c];
				oSlide.cursor.removeClass("lui_dataview_slide_on");
				if(oSlide.text)
					oSlide.text.hide();
			}
		}
		if(!init) {
			var sleft = "";
			if(auto) {
				if(dir == 'right') {
					//点击右箭头和自动向右滑动的时候
					if(curpos == 0) {
						sleft = -(slen  * itemWidth);
					} else if(curpos == 1) {
						slideContainer.css({ left : "0px" });
						sleft = -itemWidth;
					} else {
						sleft = -(itemWidth * curpos);
					}
					
				} else if(dir == "left"){
					//点击左箭头的时候
					if(curpos == slen - 1) {
						slideContainer.css({ left : (-(itemWidth * slen)) + "px" });
					}
					sleft = -(itemWidth * curpos);
				}
			} else {
				//点击小按钮的时候
				sleft = -(itemWidth * curpos);
			}
			slideContainer.animate({left: sleft + "px"}, 700);
		}
	}
	
	function autoSlide(init, auto, dir) {
		if(SwitchingFrequency!=0){
		if(!init) {
			curpos++;
		}
		if (curpos == slen)
			curpos = 0;
		slideTo(init, auto, dir);
		}
	}
	if(size > 1 ) {
		autoSlide(true, true, "right");
		timer = setInterval( function() {
				autoSlide(false, true, "right");
		}, SwitchingFrequency);
		
		render.parent.onErase(function() {
			clearInterval(timer);
		});
		
		$("#" + render.cid + "-slide li").bind("click", function() {
				if(slideContainer.is(":animated")) {
					return;
				}
				clearInterval(timer);
				timer = setInterval( function() {
					autoSlide(false, true, "right");
				}, SwitchingFrequency);
				curpos = this["pos"];
				slideTo(false, false, "right");
		});
		
		//点击左右切换
		$("#" + render.cid + "-slide .lui_dataview_slide_change").bind("click", function() {
			if(slideContainer.is(":animated")) {
				return;
			}
			clearInterval(timer);
			var dir = "";
			if($(this).hasClass("lui_dataview_slide_next")) {
				curpos ++;
				dir = "right";
				if (curpos == slen) {
					curpos = 0;
				}
			} else {
				curpos --;
				dir = "left";
				if (curpos < 0)
					curpos = slen - 1;
			}
			slideTo(false, true, dir);
			timer = setInterval( function() {
						autoSlide(false, true, "right");
					}, SwitchingFrequency);
		});
	}
	if (!stretch) {
		// 高度垂直居中
		$("#" + render.cid + "-slide img").bind('load', function() {
			var $outerObj = $(this.parentNode), obj = $(this), iheight = $outerObj
					.height();
			if (this.height > 0 && iheight > this.height) {
				obj.css('margin-top', [(iheight - this.height) / 2, 'px']
								.join(''));
			}
		});
	}
	
	//无标题情况下,让中间的小点居中
	if(!showTitle) {
		var btnToolBar = $("#" + render.cid + '-slide .lui_dataview_slide_btbar');
		var btbarWidth = btnToolBar.width();
		if(btbarWidth) {
			btnToolBar.css({'margin-left' : - (btbarWidth /2)});
		}
	}
	
	// 减去边框
	var content = $("#" + render.cid + '-slide .lui_dataview_slide_content'), b_w = (content[0].offsetWidth - parseInt(content
			.width()))
			/ 2, b_h = (content[0].offsetHeight - parseInt(content.height()))
			/ 2;
	if (b_w == 0)
		return;
	var w = content.width(), h = content.height()
	content.css({
				'width' : w - 2 * b_w,
				'height' : h - 2 * b_h
			});
	
}();
