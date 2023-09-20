if (data == null || data.length == 0) { 
	done();
	return;
}

// 变量赋值
var element = render.parent.element;
var size = render.vars.size ? render.vars.size : '100*100';
var columnNum = render.vars.columnNum ? render.vars.columnNum : '5';
var showTitle = render.vars.showTitle ? render.vars.showTitle : '0';
var slide = render.vars.slide ? render.vars.slide : '0';
var autoResizeImg = render.vars.autoResizeImg ? render.vars.autoResizeImg : '0';
var ____width = 100 / columnNum + '%';
var timer;


// 获取panel皮肤类型
var extend = '';
var container = $('<div class="lui_dataview_image' + extend + ' clearfloat"/>');
container.attr('id', render.cid + '____image____');
var content = $('<div class="lui_dataview_image' + extend + '_content"/>');
var table = $('<table data-lui-mark="dataview.image.table"/>');
var w2h = size.split('*'), w = w2h[0], h = w2h[1];

// 判断是否进行滑动
function isSlide() {
	if (slide && slide != '0'&& slide != '3')
		return true;
	return false;
}

function isHorizontSlide() {
	return isSlide() && slide == 1;
}

function isVerticalSlide() {
	return isSlide() && slide == 2;
}

____width = !isSlide() ? ____width : '';
function createImage(url, href) {

	if(autoResizeImg != '0'){
		var __div = $('<div></div>');
		__div.css({
			'width' : w,
			'height' : h,
			'line-height': (h -2) + 'px',
			'margin': 'auto',
			'text-align': 'center',
			'border': '1px solid #d9d9d9'
		});
		var __img = $('<img />');
		__img.css({
			'overflow':'hidden', 
	    	'vertical-align': 'middle',
			'display': 'inline-block',
			'max-width': '100%',
			'max-height': '100%'
		});
		//__img.attr('src', env.fn.formatUrl(url));
		__img.attr('data-src', env.fn.formatUrl(url));
		__div.append(__img);
		
		var ___a = $('<a target="_blank"/>');
		___a.attr('data-href', env.fn.formatUrl(href));
		___a.attr('class', 'lui_dataview_image' + extend + '_entity');
		___a.attr('onclick', "Com_OpenNewWindow(this);");
		___a.append(__div);
	}else{
		var __img = $('<img />');
		
		// __img.attr('src', env.fn.formatUrl(url));
		__img.css({
					'width' : w,
					'height' : h
				});
		__img.attr('data-src', env.fn.formatUrl(url));
		var ___a = $('<a target="_blank"/>');
		___a.attr('data-href', env.fn.formatUrl(href));
		___a.attr('class', 'lui_dataview_image' + extend + '_entity');
		___a.attr('onclick', "Com_OpenNewWindow(this);");
		___a.append(__img);
	}
	
	
	
	return ___a;
}

function createText(text, href, style) {
	var __text = $('<a />');
	__text.attr('data-href', env.fn.formatUrl(href));
	__text.attr('title', text);
	__text.attr('target', "_blank");
	__text.addClass('com_subject');
	__text.addClass('textEllipsis');
	__text.text(text);
	__text.css('width', w);
	__text.css(style);
	__text.attr('onclick', "Com_OpenNewWindow(this);");
	return __text;
}

function createSpanText(text, style) {
	var __text = $('<span />');
	__text.attr('title', text);
	__text.addClass('com_subject');
	__text.addClass('textEllipsis');
	__text.text(text);
	__text.css('width', w);
	__text.css(style);
	return __text;
}

var __tr = '<tr>', tr__ = '</tr>';
var __td = '<td width="' + ____width + '"'
		+ (!isSlide() ? 'style="padding-left:0;padding-right:0"' : '') + '>', td__ = '</td>';

// 创建td对象
function createTd(grid) {
	handleAutoResizeImg(grid);
	var grid_img = createImage(grid['image'], grid['href']);
	var style = {'text-align':'center'};
	
//小姐姐说不会有门户字体一般只有水平对齐字体居中的，我就不放开其他设置了
//	if(grid['isCenter']){
//		style = {'text-align':'center'};
//	}
	var grid_text = createText(grid['text'], grid['href'], style);
	var div = $('<div/>');

	if (showTitle && showTitle == '0') {
		div.append(grid_img).append(grid_text);
	} else {
		div.append(grid_img);
	}
	var learnC = createSpanText(grid['fdLearnCount'], {color: '#19B077',margin: '0 auto', 'text-align':'center'});
	var learnT = $('<span />');
	learnT.css({color: '#000'});
	learnT.text(grid['fdLearnTitle']);
	learnC.append(learnT);
	div.append(learnC);
	return __td + div.html() + td__;
}

// 普通视图,且图片文字保持水平
function __nomalBuildHorizontal(html){
	for (var i = 0; i < data.length; i++) {
		var grid = data[i];
		if (i % columnNum === 0)
			html += __tr;
		html += create2Td(grid);
		if (i % columnNum == columnNum - 1)
			html += tr__;
	}
	return html;
}

// 创建对象
function create2Td(grid) {
	var grid_img = createImage(grid['image'], grid['href']);
	var div = $('<div/>');
	div.append(grid_img);
	
	var div1 = $('<div/>');
	var grid_text = createText(grid['text'], grid['href'], {'text-align':'left'});
	if (showTitle && showTitle == '0') {
		div1.append(grid_text);
	}
	var learnC = createSpanText(grid['fdLearnCount'], {color: '#19B077',margin: '0 auto', 'text-align':'left'});
	var learnT = $('<span />');
	learnT.css({color: '#000'});
	learnT.text(grid['fdLearnTitle']);
	learnC.append(learnT);
	div1.append(learnC);
	return __td + div.html() + td__ + __td + div1.html() + td__;
}

// 构建普通图片视图
function ___nomalBuild(html) {
	for (var i = 0; i < data.length; i++) {
		var grid = data[i];
		if (i % columnNum === 0)
			html += __tr;
		html += createTd(grid);
		if (i % columnNum == columnNum - 1)
			html += tr__;
	}
	return html;
}

// 构建水平滚动视图
function ___horizontBuild(html) {
	html += __tr;
	for (var i = 0; i < data.length; i++) {
		var grid = data[i];
		html += createTd(grid);
	}
	html += tr__;
	return html;
}

// 构建垂直滚动视图
function ___verticalBuild(html) {
	for (var i = 0; i < data.length; i++) {
		html += __tr;
		var grid = data[i];
		html += createTd(grid);
		html += tr__;
	}
	return html;
}
var html = '';
if (!isSlide()) {
	if(slide == '3'){
		// TODO
		html = __nomalBuildHorizontal(html);
		container.append(content);
	}else{
		html = ___nomalBuild(html);
		container.append(content);
	}
}

if (isHorizontSlide()) {
	container.append($('<div class="lui_dataview_image' + extend + '_left"/>'));// 向左
	container.append(content);
	container
			.append($('<div class="lui_dataview_image' + extend + '_right"/>'));// 向右
	table.css('position', 'absolute');
	content.css('position', 'absolute');
	html += ___horizontBuild(html);
}

if (isVerticalSlide()) {
	container.append($('<div class="lui_dataview_image' + extend + '_top"/>'));// 向上
	container.append(content);
	content.addClass('vertical');
	container
			.append($('<div class="lui_dataview_image' + extend + '_bottom"/>'));// 向下
	html += ___verticalBuild(html);
	table.css('position', 'absolute');
	content.css('position', 'absolute');
}

table.append($(html));
content.append(table);
done(container);

var width = element.parent().width();
var height = element.parent().height();

if (isHorizontSlide()) {
	height = Math.max(height, 132);
	table.css('height', '100%');
	content.css('height', height);
	container.css('height', height);
	container.addClass('border');
	
	// 再次调整高度
	var theight = table.height();
	height = Math.max(height, theight);
	content.css('height', height);
	container.css('height', height);
}

if (isVerticalSlide()) {
	height = Math.max(height, 320);
	width = Math.max(width, 110);
	content.css('width', width);
	table.css('width', '100%');
	container.css('height', height);
	container.addClass('border');
}


/*******************************************************************************
 * 滑动需要变量赋值
 */
if (isSlide()) {
	var ____image____ = $('#' + render.cid + '____image____');
	var ____table____ = ____image____
			.find('[data-lui-mark="dataview.image.table"]');
	var tAttr;
	var c = ____image____.find('.lui_dataview_image_content');
	var cAttr; //横向滑动取宽度，纵向滑动取高度
	var tdAttr; //每一个图片，横向滑动取宽度，纵向滑动取高度
	var l,__1,__2;
	if (isHorizontSlide()) {
		cAttr = c.width();
		tdAttr = ____table____.find('td').outerWidth();
		l = parseInt(____table____.css('left'));
		__1 = ____image____.find('.lui_dataview_image' + extend + '_left');
		__2 = ____image____.find('.lui_dataview_image' + extend + '_right');
		tAttr = ____table____.width();
	}
	
	if (isVerticalSlide()) {
		cAttr = c.height();
		tdAttr = ____table____.find('td').outerHeight();

		l = parseInt(____table____.css('top'));
		__1= ____image____.find('.lui_dataview_image' + extend + '_top');
		__2 = ____image____.find('.lui_dataview_image' + extend + '_bottom');
		tAttr = ____table____.height();;

	}
}

var num = parseInt(cAttr / tdAttr);

var w = tdAttr * num;
/*******************************************************************************/




// var time = 2000;

// 刷新滑动按钮状态
function refreshStatus(__1, __2, l, tw, cw,reset) {
	if (cw > tw)// 宽度不够，不需要产生滑动效应
		return;
	var clas = 'hasNext';
	if(reset){ //轮播重置
		__1.css('cursor', 'auto');
		__1.removeClass(clas);
		__2.css('cursor', 'pointer');
		__2.addClass(clas);
		
	}else{
		if (l < 0) {
			__1.css('cursor', 'pointer');
			__1.addClass(clas);
		} else {
			__1.css('cursor', 'auto');
			__1.removeClass(clas);
		}
		if (tw - (cw - l) > 0) {
			__2.css('cursor', 'pointer');
			__2.addClass(clas);
		} else {
			__2.css('cursor', 'auto');
			__2.removeClass(clas);
		}
	}

	
	if (isHorizontSlide()) {
		l = parseInt(____table____.css('left'));

	}
	if (isVerticalSlide()) {
		l = parseInt(____table____.css('top'));

	}
}

/*******************************************************************************
 * 图片延迟加载简单处理
 */

// 获取位置信息
function getPosition(obj) {
	return {
		l : obj.offset().left,
		t : obj.offset().top,
		w : obj.width(),
		h : obj.height()
	};
}

// 是否交集
function intersect(o1, o2) {
	var lc1 = o1.l + o1.w / 2, lc2 = o2.l + o2.w / 2, tc1 = o1.t + o1.h / 2, tc2 = o2.t
			+ o2.h / 2, w1 = (o1.w + o2.w) / 2, h1 = (o1.h + o2.h) / 2;
	return Math.abs(lc1 - lc2) < w1 && Math.abs(tc1 - tc2) < h1;
}

function lazyload(__ctn) {
	var ctn = getPosition(__ctn);
	__ctn.find('img').each(function() {
				var img = getPosition($(this));
				if (intersect(ctn, img))
					if ($(this).attr('data-src')) {
						$(this).attr('src', $(this).attr('data-src'));
						$(this).removeAttr('data-src');
					}
			});
}

/** ***************************************************************************** */

//图像滑动函数
function slideToView(_l){
	
	if(container.is(":animated")) {
		return;
	}
	if (isHorizontSlide()) {
		l = parseInt(____table____.css('left'));
	}
	
	if (isVerticalSlide()) {
		l = parseInt(____table____.css('top'));
	}
	if(!_l&&_l!=0){

		var _l = (w - l) <= (tAttr - cAttr) ? (l - w) : cAttr - tAttr;
		
		if (tAttr - (cAttr - l) <= 0) {	
			if (isHorizontSlide()) {
				____table____.css({
					left : 0
				}, function() {
					lazyload(c);
				});
				
			}
			if (isVerticalSlide()) {
				____table____.css({
					top : 0
				}, function() {
					lazyload(c);
				});

			}
			refreshStatus(__1, __2, _l, tAttr, cAttr,true);
			return;
			
		}
			
	}
		if (isHorizontSlide()) {
			____table____.animate({
				left : _l
			}, function() {
				lazyload(c);
			});

		}
		if (isVerticalSlide()) {
			____table____.animate({
				top : _l
			}, function() {
				lazyload(c);
			});

		}

	refreshStatus(__1, __2, _l, tAttr, cAttr);
}




// 滑动
function startSlide() {

	timer = setInterval( function() {
		slideToView(null);
	}, 5000);
	
	refreshStatus(__1, __2, 0, tAttr, cAttr);
	lazyload(c);
	____image____.bind('click', function(evt) {
				var $parent = $(evt.target);
				while ($parent.length > 0) {
					
					if (isHorizontSlide()) {
						l = parseInt(____table____.css('left'));
					}
					
					if (isVerticalSlide()) {
						l = parseInt(____table____.css('top'));
					}
					
					if ($parent.hasClass('lui_dataview_image' + extend
							+ '_left') || $parent.hasClass('lui_dataview_image' + extend
									+ '_top')) {
						if(container.is(":animated")) {
							return;
						}
						clearInterval(timer);
						
						var _l = (l + w) <= 0 ? (l + w) : 0;
						 
						slideToView(_l);

						refreshStatus(__1, __2, _l, tAttr, cAttr);

						timer = setInterval( function() {
							slideToView(null);
						}, 5000);
						
						break;
					}
					if ($parent.hasClass('lui_dataview_image' + extend
							+ '_right') || $parent.hasClass('lui_dataview_image' + extend
									+ '_bottom')) {
						// 图片so little
						if (tAttr <= cAttr)
							return;
						if(container.is(":animated")) {
							return;
						}
						clearInterval(timer);
						var _l = (w - l) <= (tAttr - cAttr) ? (l - w) : cAttr - tAttr;
						
						slideToView(_l);

						
						timer = setInterval( function() {
							slideToView(null);
						}, 5000);
						
						refreshStatus(__1, __2, _l, tAttr, cAttr);
					
						
						break;
					}
					
					$parent = $parent.parent();
				}
			});
}

if (!isSlide()) {
	content.css('width', width);
	table.css('width', '100%');
	var c = $('#' + render.cid + '____image____ .lui_dataview_image_content');
	c.find('img').each(function() {
		if ($(this).attr('data-src')) {
			$(this).attr('src', $(this).attr('data-src'));
			$(this).removeAttr('data-src');
		}
	});
}else{
	startSlide();
}


/**
 * 判断是否需要自动比例缩放图片
 * @param data
 * @returns
 */
function handleAutoResizeImg(data){
	if(data && data['autoResizeImg']){
		autoResizeImg = 1;
	}
}


