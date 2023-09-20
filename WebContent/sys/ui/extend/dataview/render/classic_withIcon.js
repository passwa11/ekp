'use strict';

if (data == null || data.length == 0) {
	done();
	return;
}

function getUrl(url) {
	return render.env.fn.formatUrl(url);
}
var element = render.parent.element;

var frag = $(document.createDocumentFragment());

// ===== 变量处理 starts =====

var _vars = render.vars;

//时间显示
var _isTime = _vars.isTime || '0';

// 时间类名
var _timeClass = '';
if (_isTime === '1') _timeClass = 'lui-classic-date-ShowTime';

// 单行下间距
var _marginB = (_vars.marginB || 0) + 'px';

// 鼠标悬浮线框颜色
var _hoverBorderLeft = render.env.fn.formatText(_vars.hoverBorderLeft) || '#4285F4';

// 外部容器style
var _style = Com_HtmlEscape(_vars.style) || '';

var _css = '<style>\n  ' +
	'#' + render.dataview.cid + '{\n      ' + _style + '\n  }\n  ' +
	'#' + render.dataview.cid + ' .lui-classic-list-12 .lui-classic-list-item{\n    margin-bottom: ' + _marginB + ';\n  }\n  </style>';
$(element).html(_css);
// ===== 变量处理 ends =====

var showRank = !render.vars.showRank ? false : render.vars.showRank;
var firstRowScroll = render.vars.firstRowScroll == 'true' ? true : false;

var container = $('<ul class="lui-classic-list-12">');
for (var i = 0; i < data.length; i++) {
	var item = data[i];

	var _icon = i % 5 + 1;

	var $li = $('<li class="lui-classic-list-item lui-classic-list-item-' + _icon + '">');

	// 判断摘要不存在，增加类型 lui-classic-no-desc
	//var _class = !item.description ? 'lui-classic-no-desc' : '';

	// ES6在线转换 https://babeljs.io/repl#?browsers=&build=&builtIns=false&spec=false&loose=false&code_lz=AYHghgBAFgTgpgMwLwCIAkBvA5nALgVRgBsAKAS1zgFsA6WRASgF8UJcwYddUB9AIyJgAdgGtWuCkTioK1GpQAeuFAD4QAEzIA3CAGNBAZwOoiAVwUBaMlTA4L6uAd32wlVZlm1d8V3HVMQAHpNLTUQvUNjFDNLa1s4e0dnKDgwdVUQKABmCLAjE3MrGzsHJwsJXCl3DE95OCUAwOy1AAdc_OjCuJKkqyEEAHtq2tLvMhaJAaFGlrVg7TDtdqiYovjEsu64DOssCAMYXVRMLkJSWq3mVjAibhQM-dCgsBVgIA&debug=false&forceAllTransforms=false&shippedProposals=false&circleciRepo=&evaluate=true&fileSize=false&timeTravel=false&sourceType=module&lineWrap=false&presets=es2015%2Creact%2Cstage-2&prettier=false&targets=&version=7.10.5&externalPlugins=
	// https://tool.oschina.net/jscompress/ 在线代码压缩
	// https://babeljs.io/repl

	// 作者
	var _created = item.created || '';
	// 判断摘要是否存在
	var _desc = item.description || '';
	//标题
	var _href = item.href || '';
	var _text = render.env.fn.formatText(item.text) || '';

	//回复数
	var _otherinfo = item.otherinfo || '';

	//循环图标


	var _html = '\n    <div class="lui-classic-pic"></div>\n' +
		'  <div class="lui-classic-items">\n' +
		'  <div class="lui-classic-box10"> <a href="' + getUrl(_href) + '" target="_blank">    \n' +
		'       <div class="lui-classic-content">   \n' +
		'            <h3 class="lui-classic-title" title="' + _text + '">' + _text + '</h3>\n' +
		'    </div> </a>\n' +
		'  </div>\n' +
		'    <div>\n' +
		'        <span class="lui-classic-replies">' + _otherinfo + '</span>\n' +
		'        <span class="lui-classic-date">' + _created + '</span>\n' +
		'    </div>\n' +
		'  </div>\n\n  ';
	$li.html(_html);
	$li.appendTo(container);
}
container.appendTo(frag);
frag.appendTo(element);

done();

$('.lui-classic-list-12 .lui-classic-list-item').hover(function () {
	$(this).addClass('lui-classic-hover');
}, function () {
	$(this).removeClass('lui-classic-hover');
});