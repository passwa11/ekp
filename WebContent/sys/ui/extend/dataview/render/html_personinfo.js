var instances;
var heig = render.parent.element.parent().height();
var showPersonSet=render.vars.showPersonSet == null || render.vars.showPersonSet == 'true'; 
var img = render.vars.img;
var imgParam = render.vars.imgParam ;
var imgFormat = render.vars.imgFormat || render.param.imgFormat
var dom = $("<div class='lui_dataview_html_div' style='min-height:"+heig+"px;overflow-y:hidden;'>"+data.html+"</div>");
dom.find("[data-lui-mark='panel.dataview.height']").css("min-height",heig);
if(showPersonSet){
	dom.children().find(".lui_person_info_setting").show();
}
if(img!=""&&img!=null){
	img=img.replace(/amp;/g,"");
	// 全图
	!imgFormat && dom.children().eq(0).css("background-image","url("+env.config.contextPath+img+")");
	// 1/3 & 2/3图
	imgFormat && dom.children().eq(0).find('.lui_person_info_usertitle_head').css("background-image","url("+env.config.contextPath+img+")");

}


setInterval(function(){
	var emailFrame=dom.children().find("#emailFrame");
	if($.trim(emailFrame.contents().find("#num_block").text())!=""){
		//console.log("如果有邮件 则显示邮件数");
		dom.children().find("#emailNum em").text(emailFrame.contents().find("#num_block").text());
		dom.children().find("#emailNum a").attr("href",emailFrame.contents().find("#div_num_nozero a").attr("href"));
	}else{
		if($.trim(emailFrame.contents().find("#div_num_err").text())!=""){
			//console.log("如果邮件没有配置正确，则显示错误信息");
			dom.children().find("#emailNum").html(emailFrame.contents().find("#div_num_err").text());
		}else{
			//默认显示收到零封邮件
			dom.children().find("#emailNum a").attr("href",emailFrame.contents().find("#div_num_nozero a").attr("href"));
		}
	}
},10000);

// 判断图片状态
switch (imgParam) {
	case "default":
		dom.children().eq(0).css("background-image","");
		dom.children().eq(0).find('.lui_person_info_usertitle_head').css("background-image","");
		break;
	case "no":
		dom.children().eq(0).css("background-image","url()");
		dom.children().eq(0).find('.lui_person_info_usertitle_head').css("background-image","url()");
		break;
	case "shallow":
		dom.children().eq(0).addClass("lui_person_info_usertitle_shallow");
		break;
	case "deep":
		dom.children().eq(0).addClass("lui_person_info_usertitle_deep");
		break;
	default:
		dom.children().eq(0).css("background-image","");
		dom.children().eq(0).find('.lui_person_info_usertitle_head').css("background-image","");
		break;
}
if(imgFormat){
	dom.children().eq(0).addClass("lui_person_info_usertitle_imgFormat"+imgFormat);
}

done(dom); 
seajs.use(['lui/parser'],function(parser){				
	parser.parse(render.parent.element,function(_instances){
		instances = _instances;
	});
});
render.parent.onErase(function(){
	for (var i in instances){
		instances[i].destroy();
	}		
});