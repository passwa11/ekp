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
// 图片宽度
var _imgWidth = (_vars.imgWidth || 110) + 'px';
// 分类颜色
var _classColor = _vars.classColor || "#FAA83B";
// 单元格下间距
var _marginB = (_vars.marginB || 8) + 'px';
// 图片是否拉伸 0 -- 拉伸；1 -- 不拉伸 浅灰色底；2 -- 不拉伸，模糊背景；
var _isStretch = _vars.isStretch || "0";

// 封面
var _coverImg = _vars.coverImg ? "background-image:url(" + getUrl(_vars.coverImg) + ");" : '';

// 外部容器style
var _style = _vars.style || '';

// 图片类名
var _imgClass = '';
if (_isStretch === "0") _imgClass = 'lui-image-desc-image-stretch';
if (_isStretch === "2") _imgClass = 'lui-image-desc-image-blur';

var _css = '<style>\n#' + render.dataview.cid + '{\n    ' + _style + '\n}\n\n#' + render.dataview.cid + ' .lui-image-desc-list-head-content {\n  margin-left:' + _imgWidth + '\n}\n\n#' + render.dataview.cid + ' .lui-image-desc-list-item {\n  margin-top:' + _marginB + '\n}\n\n#' + render.dataview.cid + ' .lui-image-desc-list-item:first-child {\n  margin-top:0;\n}\n\n#' + render.dataview.cid + ' .lui-image-desc-list-item .lui-image-desc-category a {\n\tcolor: ' + _classColor + '\n}\n\n#' + render.dataview.cid + ' .lui-image-desc-list-head-img {\n  width:' + _imgWidth + '\n}\n</style>';
$(element).html(_css);
// ===== 变量处理 ends =====

var container = $('<ul class="lui-image-desc-list-8">');
var _headhtml = "";

var _firstData = data[0];
_headhtml = '<div class="lui-image-desc-list-head">\n      <a href="' + getUrl(_firstData.href) + '" target="_blank" class="lui-image-desc-list-head-content">\n          <h3 class="lui-image-desc-list-head-title" title="' + _firstData.text + '">' + _firstData.text + '</h3>\n          <p class="lui-image-desc-list-head-time" title="' + (_firstData.publishtime || '') + '">' + (_firstData.publishtime || '') + '</p>\n      </a>\n      <div class="lui-image-desc-list-head-img ' + _imgClass + '">\n            <div class="lui-image-desc-image-bg" style="' + _coverImg + '">\n            </div>\n            <div class="lui-image-desc-image-img" style="' + _coverImg + '">\n            </div>\n          </div>\n  </div>';
// _headhtml =  "<div class=\"lui-image-desc-list-head\"><a href=\"" + getUrl(_firstData.href) + "\" target=\"_blank\" class=\"lui-image-desc-list-head-content\"><h3 class=\"lui-image-desc-list-head-title\" title=\"" + _firstData.text + "\">" + _firstData.text + "</h3><p class=\"lui-image-desc-list-head-desc\" title=\"" + _firstData.description + "\">" + _firstData.description + "</p><span class=\"lui-image-desc-list-head-more\"></span></a><div class=\"lui-image-desc-list-head-img\"><img src=\"" + _coverImg + "\" alt=\"\"/></div></div>";

container.html(_headhtml);
for (var i = 1; i < data.length; i++) {
    var item = data[i];
    var $li = $('<li class="lui-image-desc-list-item">');

    // https://tool.oschina.net/jscompress/ 在线代码压缩
    // https://babeljs.io/repl
    // http://google.github.io/traceur-compiler/demo/repl.html#
    //   `<a href="${getUrl(item.href)}" target="_blank" title="${item.text}">
    //      <h3 class="lui-image-desc-title" title="${item.text}">${item.text}</h3>
    //      <span class="lui-image-desc-date">${item.created}</span>
    // </a>`
    var _html = '<div class="lui-image-desc-content_box">\n    <div class="lui-image-desc-content">\n      <div class="lui-image-desc-category"><span><a href="' + getUrl(item.catehref) + '" target="_blank" title="' + item.catename + '">' + item.catename + '</a></span></div>\n      <h3 class="lui-image-desc-title"><span><a href="' + getUrl(item.href) + '" target="_blank" title="' + item.text + '">' + item.text + '</a></span></h3>\n    </div>\n  </div>';
    // var _html = "<a href=\"" + getUrl(item.href) + "\" target=\"_blank\" title=\"" + item.text + "\"><h3 class=\"lui-image-desc-title\" title=\"" + item.text + "\">" + item.text + "</h3><span class=\"lui-image-desc-date\">" + item.created + "</span></a>";
    $li.html(_html);
    $li.appendTo(container);
}

container.appendTo(frag);
frag.appendTo(element);

// 超出隐藏处理
var cateCont = $('.lui-image-desc-category span');
for (var j = 0; j < cateCont.length; j++) {
    var cateW = $(cateCont[j]).width();
    $(cateCont[j]).parent('.lui-image-desc-category').css('width', cateW + 'px');
}

done();