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
// 单行下间距
var _marginB = (_vars.marginB || 8) + 'px';

var _style = _vars.style || '';

// 序号是否显示 notshow -- 不显示；showthree -- 显示前三；showall -- 显示全部；
var _numShow = _vars.numShow || 'showall';
var _numClass = '';
if (_numShow === 'notshow') _numClass = 'lui-classic-num-notshow';
if (_numShow === 'showthree') _numClass = 'lux-image-desc-image-showthree';
if (_numShow === 'showall') _numClass = 'lux-image-desc-image-showall';

var _css = '<style>\n  ' +
	'#' + render.dataview.cid + '{\n\t  ' + _style + '\n  }\n  ' +
	'#' + render.dataview.cid + ' .lui-classic-list-6 .lui-classic-list-item{\n\tmargin-bottom:' + _marginB + ';\n  }\n}\n' +
	'  </style>';

$(element).html(_css);
// ===== 变量处理 ends =====

var container = $('<ul class="lui-classic-list-6">');
for (var i = 0; i < data.length; i++) {
	var index = i + 1;
	var item = data[i];

	// 作者
	var _created = item.created || '';
	//回复数
	var _otherinfo = item.otherinfo || '';

	var $li = $('<li class="lui-classic-list-item">');

	// 判断摘要是否存在
	//var _desc = item.description || '';
	// 判断摘要不存在，增加类型 lui-classic-no-desc
	//var _class = !item.description ? 'lui-classic-no-desc' : '';

	// ES6在线转换 https://babeljs.io/repl#?browsers=&build=&builtIns=false&spec=false&loose=false&code_lz=AYHghgBAFgTgpgMwLwCIAkBvA5nALgVRgBsAKAS1zgFsA6WRASgF8UJcwYddUB9AIyJgAdgGtWuCkTioK1GpQAeuFAD4QAEzIA3CAGNBAZwOoiAVwUBaMlTA4L6uAd32wlVZlm1d8V3HVMQAHpNLTUQvUNjFDNLa1s4e0dnKDgwdVUQKABmCLAjE3MrGzsHJwsJXCl3DE95OCUAwOy1AAdc_OjCuJKkqyEEAHtq2tLvMhaJAaFGlrVg7TDtdqiYovjEsu64DOssCAMYXVRMLkJSWq3mVjAibhQM-dCgsBVgIA&debug=false&forceAllTransforms=false&shippedProposals=false&circleciRepo=&evaluate=true&fileSize=false&timeTravel=false&sourceType=module&lineWrap=false&presets=es2015%2Creact%2Cstage-2&prettier=false&targets=&version=7.10.5&externalPlugins=
	// https://tool.oschina.net/jscompress/ 在线代码压缩
	// https://babeljs.io/repl

	//标题
	var _href = item.href || '';
	var _text = item.text || '';

	var _html = '<a data-href="' + getUrl(_href) + '"  target="_blank">\n' +
		'    <div class="lui-classic-content">   \n' +
		'\t\t<span class="lui-classic-num ' + _numClass + '">' + (i + 1) + '</span>\n\t\t<h3 class="lui-classic-title" title="' + _text + '">' + _text + '</h3>\n    </div>\n  </a>\n  <div class="lui-classic-replies">' + _otherinfo + '</div>\n  <div class="lui-classic-date">' + _created + '</div>';
	$li.html(_html);
	$li.appendTo(container);
}
container.appendTo(frag);
frag.appendTo(element);

done();