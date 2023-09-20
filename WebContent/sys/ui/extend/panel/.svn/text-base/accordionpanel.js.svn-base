var extend = (param!=null && param.extend!=null)?('_'+param.extend):'';
console.log("默认垂直多标签",layout.accordionpanel)
var _vars = layout.accordionpanel.config.vars;
var _bgColor = '';
var _bg = '';
var _padding = '';
var _borderRadius = '';
var _top = 0;
var _top_style = '';

var _customStyle = '';
var _style = '';
if(_vars){
	_customStyle = _vars.style || '';
	_bgColor = _vars.bgColor === '' ? '' : (_vars.bgColor!=null) ? 'background-color:'+_vars.bgColor+';': '';
	_bg = _vars.bg === '' ? '' : (_vars.bg!=null) ? 'background-image:url('+layout.env.fn.formatUrl(_vars.bg)+');': '';
	_padding = _vars.padding === '' ? '' : (_vars.padding!=null) ? 'padding:'+_vars.padding+';': '';
	_borderRadius = _vars.borderRadius == '' ? '' : _vars.borderRadius!=null ? 'border-radius:'+_vars.borderRadius+';': '';
	_top = _vars.top || 0;
	_top_style = _top != 0 ? 'position:relative;top:-'+_top+'px;margin-bottom:-'+_top+'px;': '';
	_style = [_bgColor,_bg,_borderRadius,_top_style,_customStyle].join(" ");
}
var _style_link= '<style>';
if(_bgColor || _bg){
	_style_link += '#'+ layout.accordionpanel.cid +' .lui_accordionpanel_content_l{background:transparent;}'
		+ '#'+ layout.accordionpanel.cid +' .lui_accordionpanel_content_c{background:transparent;}';
}
if(_borderRadius){
	_style_link += '#'+ layout.accordionpanel.cid +' .lui_accordionpanel_frame{overflow:hidden;}';
}
if(_padding){
	_style_link += '#'+ layout.accordionpanel.cid +' [data-lui-type="lui/panel!Content"]{'+_padding+'}';
}
_style_link += '</style>'

function createContent(content){
	var b = $("<div class='lui_accordionpanel_content_frame'>"+_style_link+"</div>");
	var h = $("<div class='lui_accordionpanel"+extend+"_header_l'><div class='lui_accordionpanel"+extend+"_header_r'><div class='lui_accordionpanel"+extend+"_header_c'></div></div></div>");
	var c = $("<div class='lui_accordionpanel"+extend+"_content_l'><div class='lui_accordionpanel"+extend+"_content_r'><div class='lui_accordionpanel"+extend+"_content_c' data-lui-mark='panel.content'></div></div></div>");
	var f = $("<div class='lui_accordionpanel"+extend+"_footer_l'><div class='lui_accordionpanel"+extend+"_footer_r'><div class='lui_accordionpanel"+extend+"_footer_c'></div></div></div>");

	var n = $("<div class='lui_accordionpanel"+extend+"_nav_l' data-lui-mark='panel.nav.head'><div class='lui_accordionpanel"+extend+"_nav_r'><div class='lui_accordionpanel"+extend+"_nav_c'></div></div></div>");

	var nav = n.find("div:last");
	var header = h.find("div:last");
	var body = c.find("div:last");

	header.append(n);
	b.append(h).append(c).append(f);


	//添加标题
	var title= env.fn.formatText(content.title);
	var titleNode = $("<span class='lui_accordionpanel_nav_text' title = '"+title+"' data-lui-mark='panel.nav.title'>"+title+"</span>");
	nav.append(titleNode);
	if(content&&content.config&&content.config.isNeedBtn){
		var config = content.config;
		var aNode = $("<a href='javascript:void(0)'>"+config.btnText+"</a>");
		titleNode.append(aNode);
		aNode.bind("click",{"id":config.id}, config.btnFun);
	}
	var toggle = $("<span data-lui-mark='panel.nav.toggle' class='lui_accordionpanel_toggle_up' data-lui-switch-class='lui_accordionpanel_toggle_down'>&nbsp;</span>");
	nav.append(toggle);
	content.nav = nav;
	//添加内容
	body.append(content.element);
	return b;
}
var dom = $("<div class='lui_accordionpanel"+extend+"_frame' style='"+_style+"'></div>");

//内容
for(var i=0;i<layout.accordionpanel.contents.length;i++){
	var ni = createContent(layout.accordionpanel.contents[i]);
	dom.append(ni);
}
layout.on("addContent",function(content){
	var nic = createContent(content);
	//获取标题的mark;
	layout.accordionpanel.parseTextMark(nic, true);
	//解析toggle的mark
	layout.accordionpanel.parseToggleMark(nic);
	//解析content的mark
	layout.accordionpanel.parseContentMark(nic);
	dom.append(nic);
});
layout.on("removeContent",function(content){
	content.element.closest("div.lui_accordionpanel_content_frame").remove();
});
done(dom);