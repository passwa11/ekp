'use strict';

if (data == null || data.length == 0) {
    done();
    return;
}

function getUrl(url) {
    return render.env.fn.formatUrl(url);
}
function getcateUrl(url) {
    return render.env.fn.formatUrl(url);
}

var element = render.parent.element;
var frag = $(document.createDocumentFragment());

// ===== 变量处理 starts =====
var _vars = render.vars;

// 图片高度

//var _Height = $('.lui-image-desc-list-9').height();
//var _imgheight = _Height + 'px';

// 图片宽度
var _imgWidth = (_vars.imgWidth || 300) + 'px';

// 图片圆角
var _radius = (_vars.radius || 4) + 'px';

// 鼠标悬浮线框颜色
var _hoverBorderLeft = _vars.hoverBorderLeft || '#4285F4';

// 单行下间距
var _marginB = (_vars.marginB || 8) + 'px';

// 外部容器style
var _style = _vars.style || '';

var _css = '<style>\n\t#' + render.dataview.cid + '{\n\t\t' + _style + '\n\t}\n\n\t#' + render.dataview.cid + ' .lui-image-desc-list-9 .lui-imgdesc-hover .lui-image-desc-content{\n\t\tborder-left-color: ' + _hoverBorderLeft + ';\n\t}\n\n\t#' + render.dataview.cid + ' .lui-image-desc-list-9 .lui-image-desc-list-item{\n\t\tmargin-left:' + _imgWidth + ';\n\t\tmargin-bottom:' + _marginB + ';\n\t}\n\t#' + render.dataview.cid + ' .lui-image-desc-list-9 .lui-image-desc-list-item.lui-image-desc-list-item-last{\n\t\tmargin-bottom:0;\n\t}\n\t#' + render.dataview.cid + ' .lui-image-desc-list-9 .lui-image-desc-img,\n\t#' + render.dataview.cid + ' .lui-image-desc-list-9 .lui-image-desc-img .lui-image-desc-image-bg,\n\t#' + render.dataview.cid + ' .lui-image-desc-list-9 .lui-image-desc-img.lui-image-desc-image-blur .lui-image-desc-image-bg{\n\t\twidth:' + _imgWidth + ';\n\t}\n\t#' + render.dataview.cid + ' .lui-image-desc-list-9 .lui-image-desc-img,\n\t#' + render.dataview.cid + ' .lui-image-desc-img.lui-image-desc-image-stretch img{\n\t\tborder-radius:' + _radius + ';\n\t}\n  </style>';
$(element).html(_css);
// ===== 变量处理 ends =====

var container = $('<ul class="lui-image-desc-list-9">');
for (var i = 0; i < data.length; i++) {
    var item = data[i];
    var _itemClass = i === data.length - 1 ? 'lui-image-desc-list-item-last' : '';
    var $li = $('<li class="lui-image-desc-list-item ' + _itemClass + '">');

    var _text = item.text || '';
    var _href = item.href || '';
    var _desc = item.description || '';
    var _creator = item.creator || '';
    var _created = item.created || '';
    var _image = item.image || '';

    // 防止XSS攻击
    _text = render.env.fn.formatText(_text);
    _desc = render.env.fn.formatText(_desc);

    // 图片是否拉伸 0 -- 拉伸；1 -- 不拉伸 浅灰色底；2 -- 不拉伸，模糊背景；
    var _isStretch = _vars.isStretch || '0';
    // 图片类名
    var _imgClass = '';
    if (_isStretch === '0') _imgClass = 'lui-image-desc-image-stretch';
    if (_isStretch === '2') _imgClass = 'lui-image-desc-image-blur';

    //分类
    var _catehref = item.catehref || '';
    var _catename = item.catename || '';

    // 是否显示分类 0--显示,默认显示；1--不显示
    var _iscate = _vars.iscate || '0';
    var _cateClass = '';
    if (_iscate === '1') _cateClass = 'lui-imgdesc-nocate';

    // https://tool.oschina.net/jscompress/ 在线代码压缩
    // https://babeljs.io/repl

    var _html = '<div class="lui-imgdesc-box">\n\t\t<a class="lui-imgdesc-pic-link" href="' + getUrl(_href) + '" target="_blank">\n\t\t\t<div class="lui-image-desc-img ' + _imgClass + '">\n\t\t\t\t<div class="lui-img">\n\t\t\t\t\t<div class="lui-image-desc-image-bg ' + _cateClass + '" style="background-image:url(' + getUrl(_image) + ');">\n\t\t\t\t\t</div>\n\t\t\t\t\t<img src="' + getUrl(_image) + '" alt="">\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</a>\n\t\t<div class="lui-image-desc-content">\n\t\t\t<div class="lui-image-desc-header">\n\t\t\t\t<a class="lui-imgdesc-cate-link ' + _cateClass + '" href="' + getcateUrl(_catehref) + '" target="_blank">\n\t\t\t\t\t<span class="lui-imgdesc-cate-sort">' + _catename + '</span>\n\t\t\t\t\t<span class="lui-imgdesc-cate-line">|</span>\n\t\t\t\t</a>\n\t\t\t\t<a class="lui-imgdesc-classic-link"  href="' + getUrl(_href) + '" target="_blank">\n\t\t\t\t\t<h3 class="lui-image-desc-title" title="' + _text + '">' + _text + '</h3>\n\t\t\t\t</a>\n\t\t\t</div>\n\t\t\t<p class="lui-image-desc-desc" title="' + _desc + '">' + _desc + '</p>\n\t\t\t<h4 class="lui-image-desc-date">\n\t\t\t\t<span>' + _creator + '</span>|<i>' + _created + '</i>\n\t\t\t</h4>\n\t\t</div>\n\t</div>';
    $li.html(_html);
    $li.appendTo(container);
}
container.appendTo(frag);
frag.appendTo(element);

done();

$('.lui-image-desc-list-9 .lui-image-desc-list-item:first').addClass('lui-imgdesc-hover');
$('.lui-image-desc-list-9 .lui-image-desc-list-item').hover(function () {
    $(this).addClass('lui-imgdesc-hover').siblings('.lui-image-desc-list-9 .lui-image-desc-list-item').removeClass('lui-imgdesc-hover');
});