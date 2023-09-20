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
var _listStyle = _vars.listStyle || 'gray-disc';
var _primary = render.env.fn.formatText(_vars.primary);
// 是否显示创建者
var _showCreator = _vars.showCreator || true;
// 是否显示创建时间
var _showCreated = _vars.showCreated || true;
// 是否显示显示分类
var _showCate = _vars.showCate || false;

// 外部容器style
var _style = Com_HtmlEscape(_vars.style) || '';
var _css = '<style>\n#' + render.dataview.cid + '{\n    ' + _style + '\n}\n\n#' + render.dataview.cid + ' .lui-classic-list-2 .lui-classic-list-item-primary-square:before{\n  background: ' + _primary + ';\n}\n\n</style>';
$(element).html(_css);

function isExitsFunction(funcName) {
    try {
        if (typeof eval(funcName) == "function") {
            return true;
        }
    } catch (e) {}
    return false;
}

var container = $('<ul class="lui-classic-list-2">');
for (var i = 0; i < data.length; i++) {
    var item = data[i];
    var $li = $('<li class="lui-classic-list-item lui-classic-list-item-' + _listStyle + '">');

    //管理员摘取
    var _admin = item.creator || "";

    // 月份提取
    var _month = parseInt(item.created.split('-')[1] || 1);
    var _date = parseInt(item.created.split('-')[2] || 1);
    _month = _month < 10 ? "0" + _month : _month;
    var _time = parseInt(item.created || 1);

    // 判断摘要是否存在
    var _desc = render.env.fn.formatText(item.description) || '';
    // 判断摘要不存在，增加类型 lui-classic-no-desc
    var _class = !item.description ? 'lui-classic-no-desc' : '';

    //title
    var _title = render.env.fn.formatText(item.text) || '';
    // 分类信息
    var _catename = item.catename;

    // https://tool.oschina.net/jscompress/ 在线代码压缩
    // https://babeljs.io/repl
    // 新方式打开
    if (isExitsFunction('Com_OpenNewWindow')) {
        var _html = '<a onclick="Com_OpenNewWindow(this)" data-href="' + getUrl(item.href) + '" class="' + _class + '" target="_blank" title="' + _title + '">\n  \n  <div class="lui-classic-content">\n      <h3 class="lui-classic-title">' + _title + '</h3>\n      <p class="lui-classic-desc">' + _desc + '</p>\n  </div>\n  <div class="lui-classic-infomation">\n     <span>' + _admin + '</span>\n     <span>' + _time + '-' + _month + '-' + _date + '</span>\n  </div>\n</a>';
    } else {
        var _html = '<a href="' + getUrl(item.href) + '" class="' + _class + '" target="_blank" title="' + _title + '">\n  \n  <div class="lui-classic-content">\n      <h3 class="lui-classic-title">' + item.text + '</h3>\n      <p class="lui-classic-desc">' + _desc + '</p>\n  </div>\n  <div class="lui-classic-infomation">\n     <span>' + _admin + '</span>\n     <span>' + _time + '-' + _month + '-' + _date + '</span>\n  </div>\n</a>';
    }
    // var _html = "<a href=\"" + getUrl(item.href) + "\" class=\"" + _class + "\" target=\"_blank\" title=\""+ _title +"\"><div class=\"lui-classic-infomation\"><span>" +_wahaha + "</span><span>" + _time +"-"+ _month + "-" + _date + "</span></div><div class=\"lui-classic-content\"><h3 class=\"lui-classic-title\">" + item.text + "</h3><p class=\"lui-classic-desc\">" + _desc + "</p></div></a>";
    $li.html(_html);
    $li.appendTo(container);
}
container.appendTo(frag);
frag.appendTo(element);
done();