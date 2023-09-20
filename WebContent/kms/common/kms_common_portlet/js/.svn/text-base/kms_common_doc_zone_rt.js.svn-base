// 文档专区组件
//console.log("配置参数",render.parent.source.vars)
//标题转义
window.docZoneEncodeHTML = function(str){ 
	return str.replace(/&/g,"&amp;")
		.replace(/</g,"&lt;")
		.replace(/>/g,"&gt;")
		.replace(/\"/g,"&quot;")
		.replace(/¹/g, "&sup1;")
		.replace(/²/g, "&sup2;")
		.replace(/³/g, "&sup3;");
};

if (data == null || data.length == 0) {
	done();
	return;
}

// 变量赋值
var zonetitle = render.parent.source.vars.zoneTitle;
var type = render.parent.source.vars.type;
var cateId = render.parent.source.vars.cateid;
var scope = render.parent.source.vars.scope;

var headTitleText = zonetitle ? zonetitle : '';
//背景图
var backgroundImg = render.env.fn.formatUrl("/kms/common/kms_common_portlet/img/kms_common_doc_zone_bg.png");
if(render.parent.source.vars.backgroundImg){
	backgroundImg = render.env.fn.formatUrl(render.parent.source.vars.backgroundImg);
}

//部件容器
var container = $('<div class="kms_common_doc_zone">');

//部件头部
var $head = $('<div class="kms_common_doc_zone_head"><img src="'+backgroundImg+'"></div>');
var $headTitle = $('<div class="kms_common_doc_zone_zonetitle"></div>');
$headTitle.append('<div>'+headTitleText+'</div>');
$headTitle.appendTo($head);
$head.appendTo(container);

/* 列表数据处理
 * data json格式
 * text 文档标题
 * href 文档跳转链接
 */
var $ul = $('<div class="kms_common_doc_zone_body_ul">');
for (var i = 0; i < data.length; i++) {
	var item = data[i];
	var $li = $('<div class="kms_common_doc_zone_body_li">');
	var $li_a = $('<a target="_blank"><div class="kms_common_doc_zone_body_list_name"><i></i><div>'+docZoneEncodeHTML(item.text)+'</div></div></a>').attr('data-href', render.env.fn.formatUrl(item.href));
	$li_a.attr("onclick","Com_OpenNewWindow(this)");
	$li_a.appendTo($li);
	$li.appendTo($ul);
}
$ul.appendTo(container);

seajs.use([ 'lang!kms-common' ], function(lang) {
	var recentTest = lang['kmsKnowledge.porlet.kms.more']; // 更多
//设置更多点击事件
if(render.parent.source.ajaxConfig.goToMoreView){
	//部件底部
	var $footer = $('<div class="kms_common_doc_zone_foot">'+recentTest+'</div>');
	$footer.appendTo(container);
	
	$footer.click(function () {
		seajs.use([render.parent.source.ajaxConfig.goToMoreView], function(goToMoreView) {
			goToMoreView.goToZonePortletView(cateId,type,scope);
	    });
	});
}
});
//渲染dom
done(container);