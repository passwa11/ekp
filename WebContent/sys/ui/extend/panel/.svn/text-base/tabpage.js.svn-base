layout.contents = [];
layout.navs = [];
var dom = $("<div class='lui_tabpage_frame'></div>");
var _lang ={};
seajs.use('lang!sys-ui',function(lang){
	_lang = lang;
	$(".lui_tabpage_float_header_close",dom).each(function(){
		$(this).prop("title",_lang['ui.tabpage.minimize']);
	});
});
function createContentNav(content){
	var ndom = $("<div class='lui_tabpage_float_nav_item'></div>");
	var nitem = $("<div class='lui_tabpage_float_nav_item_l'><div class='lui_tabpage_float_nav_item_r'><div class='lui_tabpage_float_nav_item_c'></div></div></div>");
	nitem.find(".lui_tabpage_float_nav_item_c").append(content.title);
	return ndom.append(nitem);
}
function createContent(content){
	var cdom = $("<div class='lui_tabpage_float_content'></div>");
	var ch = $("<div class='lui_tabpage_float_header_l'><div class='lui_tabpage_float_header_r'><div class='lui_tabpage_float_header_c'></div></div></div>");
	var cc = $("<div class='lui_tabpage_float_content_l'><div class='lui_tabpage_float_content_r'><div class='lui_tabpage_float_content_c'></div></div></div>");
	var cf = $("<div class='lui_tabpage_float_footer_l'><div class='lui_tabpage_float_footer_r'><div class='lui_tabpage_float_footer_c'></div></div></div>");
	var ct = $("<div class='lui_tabpage_float_header_text'></div>").append(content.title);
	var cx = $("<div class='lui_tabpage_float_header_close' title='"+_lang['ui.tabpage.minimize']+"'></div>");
	var cht = $("<div class='lui_tabpage_float_header_title'></div>");
	cht.append(ct);
	if(content.getToggle()){
		cx.click((function(index){
			return function(){
				layout.tabpage.onToggle(index);
			};
		})(i));
		cht.append(cx);
	}
	ch.find(".lui_tabpage_float_header_c").append(cht);
	cc.find(".lui_tabpage_float_content_c").append(content.element);
	if (navigator.userAgent.indexOf("Firefox") >= 0 || navigator.userAgent.indexOf("Chrome") >= 0){
		cdom.append(ch).append(cc).append(cf);
	} else {
		cdom.append(ch).append(cc).append(cf).hide();
	}

	return cdom;
}

//layout.tabpage.on('toggleAfter', function(evt) {
//	var allCollapse=true;
//	var contents = layout.tabpage.contents;
//	for ( var j = 0; j < contents.length; j++) {
//		if(contents[j].isShow){
//			allCollapse = false;
//		}
//	}
//	if(allCollapse){
//		if(layout.tabpage.element.find(".lui_tabpage_float_navs").is(':hidden')){
//		//	_self.element.find(".lui_tabpage_float_navs").fadeIn();
//			layout.tabpage.element.find(".lui_tabpage_float_navs").show();
//			layout.tabpage.element.find(".lui_tabpage_float_navs").addClass('fadeInRight animated');
//			setTimeout(function(){
//				layout.tabpage.element.find(".lui_tabpage_float_navs").removeClass('fadeInRight animated');
//			},500);
//			LUI('collapseBtn').element.addClass("collapsed");
//		}
//	}
//});


//内容
var csNode = $("<div class='lui_tabpage_float_contents'></div>");
for(var i=0;i<layout.tabpage.contents.length;i++){
	var ni = createContent(layout.tabpage.contents[i]);
	layout.contents.push(ni);
	csNode.append(ni);
}
dom.append(csNode);
//标题
var ns = $("<div class='lui_tabpage_float_navs'></div>");

//ns.append($("<div class='lui_tabpage_float_nav_item_before'></div>"));
//ns.append($("<div class='lui_tabpage_float_nav_item_after'></div>"));
var nb = $("<div class='lui_tabpage_float_navs_l'><div class='lui_tabpage_float_navs_r'><div class='lui_tabpage_float_navs_c'></div></div></div>");
var nsNode = nb.find(".lui_tabpage_float_navs_c");
// #37032
// if(layout.tabpage.vars.navwidth != null){
// 	nsNode.width(layout.tabpage.vars.navwidth);
// }
nsNode.css({"max-width":env.config.pageMaxWidth});

for(var i=0;i<layout.tabpage.contents.length;i++){
	if(layout.tabpage.contents[i].getToggle()){
		var ni = createContentNav(layout.tabpage.contents[i]);
		ni.click((function(index){
			return function(){
				layout.tabpage.onToggle(index);
			};
		})(i));
		layout.navs.push(ni);
		nsNode.append(ni);
	}else{
		var ni = "";
		layout.navs.push(ni);
		nsNode.append(ni);
	}
}

ns.append(nb);
var nsMark = $("<iframe frameborder='0' class='lui_tabpage_float_navs_mark' scrolling='no'></iframe>");
dom.append(nsMark);
dom.append(ns);

var collapseNode = $("<div class='lui_tabpage_float_collapse  lui_tabpage_uncollapse'><a class='txt'></a></div>");
dom.append(collapseNode);

layout.tabpage.on("toggleBefore",function(data){
	var i = data.index;
	var vcontent = layout.contents[i];
	if(layout.tabpage.contents[i].getToggle()){
		if(data.visible){
			if (navigator.userAgent.indexOf("Firefox") >= 0 || navigator.userAgent.indexOf("Chrome") >= 0){
				vcontent.css({'position': 'absolute','top':'-999em'});
				//谷歌下金格控件隐藏显示问题
				try {
					if (navigator.userAgent.indexOf("Chrome") >= 0) {
						// #114532 iwebPDF2018控件的优先级过高，会将弹出框和后面的按钮遮挡住value=1显示 value=0 隐藏
						var pdfFrame = document.getElementById('pdfFrame');
						if(pdfFrame != null){
							pdfFrame.contentWindow.document.getElementById('JGWebPdf_mainonline').HidePlugin(0);
						}
						if (null != jgBigVersionParam && jgBigVersionParam == "2015") {
							vcontent.find("object[id*='JGWebOffice_']").each(function(i,_obj){
								_obj.value = "0";
								_obj.HidePlugin(0);
							});
						}
					}
				} catch (e) {}
			} else {
				if(data.init){
					vcontent.hide();
				}else{
					vcontent.slideUp(200);
				}
			}
			layout.navs[i].removeClass("selected");
		}else{
			layout.navs[i].addClass("selected");
			if(data.init){
				vcontent.show();
			}else{
				//谷歌下金格控件隐藏显示问题
				if (navigator.userAgent.indexOf("Firefox") >= 0 || navigator.userAgent.indexOf("Chrome") >= 0){
					vcontent.removeAttr("style");
					try {
						if (navigator.userAgent.indexOf("Chrome") >= 0) {
							// #114532 iwebPDF2018控件的优先级过高，会将弹出框和后面的按钮遮挡住value=1显示 value=0 隐藏
							var pdfFrame = document.getElementById('pdfFrame');
							if(pdfFrame != null){
								pdfFrame.contentWindow.document.getElementById('JGWebPdf_mainonline').HidePlugin(1);
							}
							if (null != jgBigVersionParam && jgBigVersionParam == "2015") {
								vcontent.find("object[id*='JGWebOffice_']").each(function(i,_obj){
									_obj.value = "1";
									_obj.HidePlugin(1);
								});
							}
						}
					} catch (e) {}
				}
				//slideDown(200,fn)bug:IE10下对隐藏元素做slideDown(200,fn)，会导致元素内的css样式无法加载;改成slideDown(0,fn)或者升级到JQ1.11.0
				vcontent.slideDown(0, function(){
					var mtop =   parseInt($(document.body).css("margin-top"));
					var scro = vcontent.offset().top - mtop;
					$("html,body").animate({scrollTop: scro}, 200);
				});
			}
			layout.tabpage.contents[i].load();
		}
	}else{
		vcontent.show();
		layout.tabpage.contents[i].load();
	}
	data.cancel = true;
});
layout.on("addContent",function(content){
	var nic = createContent(content);
	layout.contents.push(nic);
	csNode.append(nic);

	var nin = createContentNav(content);
	if(content.getToggle()){
		nin.click((function(index){
			return function(){
				layout.tabpage.onToggle(index);
			};
		})(layout.navs.length));
	}
	layout.navs.push(nin);
	nsNode.append(nin);
});
done(dom);
$(document.body).css("margin-bottom",$(".lui_tabpage_float_navs").height());