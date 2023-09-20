'use strict';

if (data == null || data.length == 0) {
	done();
	return;
}

function getUrl(url) {
	return render.env.fn.formatUrl(url);
}

function _fn(str) {
	return str.replace(/\$\{(.*?)\}/g, function (m, k, index, str) {
		return eval(k);
	});
}

var element = render.parent.element;
var frag = $(document.createDocumentFragment());
var _vars = render.vars;

// 下间距
var _marginB = (_vars.marginB || 12) + 'px';
var _isShowBorder = _vars.isShowBorder || 'hidden';

var _primary = render.env.fn.formatText(_vars.primary) || '#4285f4';

// 外部容器style
var _style = Com_HtmlEscape(_vars.style) || '';
var _css = '<style>\n' +
	'#' + render.dataview.cid + '{\n    ' + _style + '\n}\n' +
	'#' + render.dataview.cid + ' .lui-classic-list-13 .lui-classic-list-item{\n' +
	'  padding-top:' + _marginB + ';\n' +
	'  margin-bottom:' + _marginB + ';\n}\n' +
	'#' + render.dataview.cid + ' .lui-classic-list-13 .lui-classic-last-item{\n margin-bottom:12;\n}\n' +
	'#' + render.dataview.cid + ' .lui-classic-list-13 .lui-classic-list-item:first-child{\n  padding-top:0;\n}\n' +
	'#' + render.dataview.cid + ' .lui-classic-list-13 .lui-classic-list-item.lui-classic-list-item-hidden{\n  border-top:none;\n  margin-bottom:0;\n}\n' +
	'// #' + render.dataview.cid + '  .lui-classic-list-13 .lui-classic-date{\n//   background:' + _primary + ';\n// }\n</style>\n';
element.html(_css);

var container = $('<ul class="lui-classic-list-13">');
for (var i = 0; i < data.length; i++) {
	var item = data[i];
	var _itemClass = i + 1 === data.length ? 'lui-classic-last-item' : '';
	var $li = $('<li class="lui-classic-list-item ' + _itemClass + ' lui-classic-list-item-' + _isShowBorder + '">');
	var _year;
	var _month;
	var _date;
	// 月份提取
	if(item.created.indexOf('/')!=-1){//判断时间是否为英文格式
		var _month = parseInt(item.created.split('/')[0] || 1) > 10 ? parseInt(item.created.split('/')[0] || 1) : '0' + parseInt(item.created.split('/')[0] || 1);
		var _date = parseInt(item.created.split('/')[1] || 1);
		var _year = parseInt(item.created.split('/')[2] || 1);

	}else{
		var _year = parseInt(item.created.split('-')[0] || 1);
		var _month = parseInt(item.created.split('-')[1] || 1) > 10 ? parseInt(item.created.split('-')[1] || 1) : '0' + parseInt(item.created.split('-')[1] || 1);
		var _date= parseInt(item.created.split('-')[2] || 1);
	}

	// 判断摘要是否存在
	var _desc = item.description || '';
	// 判断摘要不存在，增加类型 lui-classic-no-desc
	var _class = '';
	// 创建者
	var _creator = item.creator || '';

	// 分类
	var _catename = item.catename || '';

	if (item.description == undefined) {
		_class = 'lui-classic-no-desc ';
	}
	// https://tool.oschina.net/jscompress/ 在线代码压缩
	// https://babeljs.io/repl
	var _html = '';
	_html = '<a href="' + getUrl(item.href) + '" class="' + _class
		+ '" target="_blank" title="' + render.env.fn.formatText(item.text) + '">\n    <div class="lui-classic-date">\n        <h4>'
		+ _date + '</h4>\n        <h5>' + _year + '-' + _month
		+ '</h5>\n    </div>\n    <div class="lui-classic-content">\n        <h3 class="lui-classic-title" title="'+ render.env.fn.formatText(item.text) +'">'
		+ render.env.fn.formatText(item.text) + '</h3>\n        <p class="lui-classic-desc">\n        <span>'
		+ _creator + '</span>\n        <span>'
		+ _catename + '</span>\n        </p>\n    </div>\n  </a>';

	$li.html(_html);
	$li.appendTo(container);
}
container.appendTo(frag);
frag.appendTo(element);

done();