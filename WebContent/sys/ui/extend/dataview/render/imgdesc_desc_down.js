/*专有类名前缀 imgdesc_updown*/
//变量赋值-及其初始化
var dataView = render.parent;
var extend = param.extend?('_'+param.extend):'';
var target = render.vars.target?render.vars.target:'_blank';
var ellipsis = param.ellipsis == 'true' || render.vars.ellipsis=='true';


var dateFormat = Com_Parameter.Date_format;
//图文顺序
var position = render.vars.position?render.vars.position:'0';
//图片高度
var imgHeight = parseInt(render.vars.imgHeight);
if(isNaN(imgHeight)){
	imgHeight = 0;
}
//拉伸
var stretch = render.vars.stretch == null || render.vars.stretch == 'true';
//分类
var showCate = render.vars.showCate == null || render.vars.showCate == 'true'; 
//作者
var showCreator = render.vars.showCreator == null || render.vars.showCreator == 'true';
//时间
var showCreated = render.vars.showCreated == null || render.vars.showCreated == 'true';
 //分类信息字符限制
var cateSize = parseInt(render.vars.cateSize);
if(isNaN(cateSize)){
	cateSize = 0;
}
//New图标	-----------------------------------------------
var newDay = parseInt(render.vars.newDay);
if(isNaN(newDay)){
	newDay = 0;
}
//计算new日期
var showNewDate;
if(newDay>0){
	showNewDate = env.fn.parseDate(env.config.now);
	showNewDate.setTime(showNewDate.getTime()-newDay*24*60*60*1000);
}
if(render.preDrawing){            //预加载执行
	sendMessage();
	return;
}
function sendMessage(){
	if(data!=null && data.length>0 && newDay>0){
		var more = false;
		for(var i=0; i<data.length; i++){
			if(showNewIcon(data[i])){
				more = true;
				break;
			}
		}
		if(more){
			if(dataView && dataView.parent){
				var countInfo={};
				countInfo.more = true;
				dataView.parent.emit("count", countInfo);
			}
		}
	}
}
if(data==null || data.length==0){
	done();
	return;
}
function showNewIcon(oneData){
	var vdate = oneData.created_temp || oneData.created;
	if(newDay<=0 || vdate==null){
		return false;
	}
	if(oneData.ocreated) {
		//ocreated 表示原始的时间格式，有一些时间格式是类似于“1天前”这种，用于判断显示new标志显示必须用原始时间
		vdate = oneData.ocreated;
	}
	return showNewDate < env.fn.parseDate(vdate);
};

//main-----------------------
var contentPanel = $("<ul/>").attr('class', 'lui_summary_news_updowbox lui_render_imgdesc_updown_box').appendTo(dataView.element);
for(var i=0; i<data.length; i++){
	addRow(data[i]);
}
if(ellipsis){
	for(var i=0; i<data.length; i++){
		ellipsisText(data[i]);
	}
}

function ellipsisText(oneData){
	//textArea然后获取高度，再填充其它字符，若高度改变说明折行
	var textArea = oneData.textArea;
	if(textArea==null)
		return;
	var textBox = oneData.textBox;
	var text = oneData.text;
	var height = textArea.height();
	height=2*height;
	textBox.text(text);
	
	// textArea.height()-4预留一定空间做浏览器兼容。
	for(var i=text.length-2; height>5 && i>0 && textArea.height()-4>height; i--){
		textBox.text(text.substring(0, i)+'...');
	}
	oneData.textBox = null;
	oneData.textArea = null;
}
//添加一个带链接的格子
function addLinkCell( oneData, text, href,isCate){
	var textBox;
	if(oneData[href]){
		if(!isCate){
			textBox = $('<a/>').attr({
				'class':'lui_portlet_txt_link',
				'data-href':env.fn.formatUrl(oneData[href]),
				'target':target
			});
			textBox.click(function() {
				Com_OpenNewWindow(this);
			});
		}else{
			textBox = $('<a/>').attr({
				'class':'lui_dataview_classic'+extend+'_'+'cate_link',
				'data-href':env.fn.formatUrl(oneData[href]),
				'target':target
			});
			textBox.click(function() {
				Com_OpenNewWindow(this);
			});
		}
	}
	if(!textBox){
		textBox=$('<span/>');
	}
	textBox.attr('title', oneData[text]);
	return textBox;
};

//添加一行
function addRow(oneData){
	//日期
	var vdate = oneData.created;
	if(oneData.ocreated) {
		vdate = oneData.ocreated;
	} 
	var createDate = env.fn.parseDate(vdate);
	var contentLi = $("<li/>").attr('class','lui_render_imgdesc_updown_item').appendTo(contentPanel)
	var contentArea = $("<div/>").attr('class','lui_summary_news_item').attr('href','javascript:;').appendTo(contentLi);
	
	var imageDiv = $("<div/>").attr('class','lui_summary_news_pic');
	
	
	var textDiv = $("<div/>").attr('class','lui_summary_news_infobox');
	if(position == '0'){
		imageDiv.appendTo(contentArea);
		textDiv.appendTo(contentArea);
	}else if(position == '1'){
		textDiv.appendTo(contentArea);
		imageDiv.appendTo(contentArea);
	}
	//图片
	var imageFormatUrl=env.fn.formatUrl(oneData['image']);
	if(oneData.imagefrom&&oneData.imagefrom=='rtf'){
		imageFormatUrl=oneData['image'];
	}
	var aNode= $("<a/>").attr('data-href',env.fn.formatUrl(oneData['href'])).attr("target", target).attr('onclick', "Com_OpenNewWindow(this);").appendTo(imageDiv);
	var imgNode = $("<img/>").attr('src',imageFormatUrl).appendTo(aNode);
	if(imgHeight>0){
		imageDiv.attr('class','lui_summary_news_pic lui_imgdesc_updown_restrict_height');
		imageDiv.attr("style","height:"+imgHeight+"px;"+"line-height:"+imgHeight+"px;");
		if(stretch){
			imgNode.attr("style","width:100%;height:auto");
		}else{
			imgNode.attr("style","max-height:100%;max-width:100%");
		}
	}else{
		imgNode.attr("style","width:100%;height:auto");
	}
	
	
	
	
	//text
	var title=$("<div/>").attr('class','lui_summary_news_title').appendTo(textDiv);
	
	
	//New图标
	if(showNewIcon(oneData)){
		$('<span/>').attr('class', 'lui_dataview_classic_new')
				.appendTo(title);
	}
	//分类
	var cate ;
	if(showCate && oneData.catename && oneData.catename!=''){
		cate = addLinkCell( oneData, 'catename', 'catehref', true);
		text = oneData.catename;
		if(cateSize>0 && text.length>cateSize){
			text = text.substring(0, cateSize)+'...';
		}
		cate.text('['+text+']');
		cate.appendTo(title);
	
	}
	var titlep = addLinkCell( oneData, 'text', 'href', false);
	titlep.appendTo(title);
	text = oneData.text || '';
	if(ellipsis && text.length>3){
		//等全部显示完毕后在调整宽度，避免出现滚动条导致页面变窄
		titlep.text('.'); //原来是用小数点来撑开div高度，但是小数点撑开的div高度为17，而一般汉字撑开为19，这样就造成一行字也会触动截取
		oneData.textBox = titlep;
		oneData.textArea = title;
		oneData.text=text;
	}else{
		titlep.text(text);
	}
	
	//--info
	var info=$("<div/>").attr('class','lui_summary_news_info').appendTo(textDiv);
	//创建者
	if(showCreator){
		var creatorArea = $('<span/>').attr('class', 'lui_summary_news_name').text(oneData['creator'] || '').appendTo(info);
	}
	// 时间
	if(showCreated){
		var dateArea = $("<span/>").attr('class', 'lui_summary_news_time').appendTo(info);
		dateArea.text(env.fn.formatDate(createDate,dateFormat));
	}

	
	//摘要
	var desc = $("<div/>").attr('class','lui_summary_news_con').appendTo(textDiv);
	var descArea = $("<span/>").attr('title',oneData['description'] || '').text(oneData['description'] || '').appendTo(desc);

}

done();