'use strict';

var dataView = render.parent;
var columnNum = isNaN(render.vars.columnNum) ? 1 : parseInt(render.vars.columnNum);

// if (render.preDrawing) { // 预加载执行
//     sendMessage();
//     return;
// }
if (data == null || data.length == 0) {
	done();
	return;
}

//专家信息starts

function getUrl(url) {
	return render.env.fn.formatUrl(url);
}

var element = render.parent.element;
var frag = $(document.createDocumentFragment());

// ===== 变量处理 starts =====
var _vars = render.vars;
var _style = _vars.style || '';

// 单行下间距
var _marginL = (_vars.marginL || 45) + 'px';

// 单行下间距
var _marginB = (_vars.marginB || 20) + 'px';

var _css = '<style>\n#' + render.dataview.cid + '{\n    ' + _style + '\n}\n\n#' + render.dataview.cid + ' .lui_portal_person_roundAvatar-list-3{\n\tmargin-left:-' + _marginL + ';\n}\n#' + render.dataview.cid + ' .lui_portal_person_roundAvatar-list-3 .lui_portal_person_roundAvatar_list_item{\n\tmargin-bottom:' + _marginB + ';\n\tmargin-left:' + _marginL + ';\n}\n</style>';

$(element).html(_css);
// ===== 变量处理 ends =====

var box = $('<div class="lui_portal_person_roundAvatar_list">');
var container = $('<ul>');
for (var i = 0; i < data.length; i++) {
	var item = data[i];
	var $li = $('<li class="lui_portal_person_roundAvatar_list_item">');
	$li.attr('onclick', "gotoPersonPage('" + data[i]['fdcontentlink'] + "',event)");

	var _image = item.imgurl || '';
	//专家名字
	var _name = item.name || '';

	//岗位
	var _postinfo = item.postinfo || '';

	//精通领域
	var _taginfo = item.taginfo || '';

	// ES6在线转换 https://babeljs.io/repl#?browsers=&build=&builtIns=false&spec=false&loose=false&code_lz=AYHghgBAFgTgpgMwLwCIAkBvA5nALgVRgBsAKAS1zgFsA6WRASgF8UJcwYddUB9AIyJgAdgGtWuCkTioK1GpQAeuFAD4QAEzIA3CAGNBAZwOoiAVwUBaMlTA4L6uAd32wlVZlm1d8V3HVMQAHpNLTUQvUNjFDNLa1s4e0dnKDgwdVUQKABmCLAjE3MrGzsHJwsJXCl3DE95OCUAwOy1AAdc_OjCuJKkqyEEAHtq2tLvMhaJAaFGlrVg7TDtdqiYovjEsu64DOssCAMYXVRMLkJSWq3mVjAibhQM-dCgsBVgIA&debug=false&forceAllTransforms=false&shippedProposals=false&circleciRepo=&evaluate=true&fileSize=false&timeTravel=false&sourceType=module&lineWrap=false&presets=es2015%2Creact%2Cstage-2&prettier=false&targets=&version=7.10.5&externalPlugins=
	// https://tool.oschina.net/jscompress/ 在线代码压缩
	// https://babeljs.io/repl
	var _html = '<a>\n\t\t<div class="lui_image_expert_img">\n\t\t\t<img src="' + _image + '" alt="">\n\t\t</div>\n\t\t<div class="lui_image_expert_content">\n\t\t\t<h3 class="lui_person_name">' + _name + '</h3>\n\t\t\t<p class="lui_person_postinfo">' + _postinfo + '</p>\n\t\t</div>\n\t</a>';
	$li.html(_html);
	$li.appendTo(container);
}

container.appendTo(box);
box.appendTo(frag);
frag.appendTo(element);
//专家信息ends

done();

// 打开个人页面
window.gotoPersonPage = function (fdLink, event) {
	if (!fdLink || fdLink == null || fdLink.trim() == '') {
		return;
	}
	Com_OpenWindow(fdLink, '_blank');
};