//变量赋值
var dataView = render.parent;
var extend = param.extend?('_'+param.extend):'';
var target = render.vars.target?render.vars.target:'_blank';
var ellipsis = param.ellipsis == 'true' || render.vars.ellipsis=='true';
var position = render.vars.position?render.vars.position:'0';
var rate = parseInt(render.vars.rate);
var stretch = render.vars.stretch == null || render.vars.stretch == 'true';
var showCate = render.vars.showCate == null || render.vars.showCate == 'true'; 
var showCreator = render.vars.showCreator == null || render.vars.showCreator == 'true';
var showCreated = render.vars.showCreated == null || render.vars.showCreated == 'true';
var cateSize = parseInt(render.vars.cateSize);
var newDay = parseInt(render.vars.newDay);
var dateFormat = Com_Parameter.Date_format;
if(isNaN(rate)){
	rate = 15;
}
if(isNaN(cateSize)){
	cateSize = 0;
}
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

var contentPanel = $("<div/>").attr('class', 'lui_portlet_imgTxt_wrap').appendTo(dataView.element);
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
	textBox.text(text);
	// textArea.height()-4预留一定空间做浏览器兼容。
	for(var i=text.length-2; height>5 && i>0 && textArea.height()-4>height; i--){
		textBox.text(text.substring(0, i)+'...');
	}
	oneData.textBox = null;
	oneData.textArea = null;
}
//添加一个带链接的格子
function addLinkCell(rowDiv, oneData, text, href,isCate){
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
	textBox.appendTo(rowDiv);
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
	
	var contentArea = $("<a/>").attr('class','lui_portlet_imgTxt_item').attr('href','javascript:;').appendTo(contentPanel);
	var imageDiv = $("<div/>").attr('class','lui_img_content_td').attr('style','width:'+rate+'%');
	var textDiv = $("<div/>").attr('class','lui_txt_content_td');
	if(position == '0'){
		imageDiv.appendTo(contentArea);
		textDiv.appendTo(contentArea);
	}else if(position == '1'){
		textDiv.appendTo(contentArea);
		imageDiv.appendTo(contentArea);
	}
	//图片
	var imgArea = $("<div/>").attr('class','lui_img_content').appendTo(imageDiv);
	var imageLink = $("<a/>").attr('data-href',env.fn.formatUrl(oneData['href'])).attr('style','display:block;').attr('target',target).attr('onclick', "Com_OpenNewWindow(this);").appendTo(imgArea);
	
	var imageFormatUrl=env.fn.formatUrl(oneData['image']);
	if(oneData.imagefrom&&oneData.imagefrom=='rtf'){
		imageFormatUrl=oneData['image'];
	}
	
	var imgNode = $("<img/>").attr('src', imageFormatUrl).appendTo(imageLink);
	// 如果后台有强制设置图片高宽时，这里不需要自动调整高宽
	if(oneData['width']) {
		imgNode.attr('data-width', oneData['width']);
	}
	if(oneData['height']) {
		imgNode.attr('data-height', oneData['height']);
	}
	if(stretch){
		autoSize($(imgNode));
	}
	var textArea = $("<div/>").attr('class','lui_portlet_txt_item').appendTo(textDiv);
	var linkBox, text;
	//分类
	if(showCate && oneData.catename && oneData.catename!=''){
		linkBox = addLinkCell(textArea, oneData, 'catename', 'catehref', true);
		text = oneData.catename;
		if(cateSize>0 && text.length>cateSize){
			text = text.substring(0, cateSize)+'...';
		}
		linkBox.text('['+text+']');
	}
	//标题
	linkBox = addLinkCell(textArea, oneData, 'text', 'href', false);
	text = oneData.text || '';
	if(ellipsis && text.length>3){
		//等全部显示完毕后在调整宽度，避免出现滚动条导致页面变窄
		linkBox.text('.'); //原来是用小数点来撑开div高度，但是小数点撑开的div高度为17，而一般汉字撑开为19，这样就造成一行字也会触动截取
		oneData.textBox = linkBox;
		oneData.textArea = textArea;
		oneData.text=text;
	}else{
		linkBox.text(text);
	}
	//New图标
	if(showNewIcon(oneData)){
		$('<span/>').attr('class', 'lui_dataview_classic_new')
				.appendTo(textArea);
	}
	// 时间
	if(showCreated){
		var dateArea = $("<div/>").attr('class', 'lui_portlet_txt_created').appendTo(textArea);
		dateArea.text(env.fn.formatDate(createDate,dateFormat));
	}
	//创建者
	if(showCreator){
		var creatorArea = $('<div/>').attr('class', 'lui_dataview_classic_creator').text(oneData['creator'] || '').appendTo(textArea);
	}
	// 摘要
	var descArea = $("<p/>").attr('class','lui_portlet_txt_summary').text(oneData['description'] || '').appendTo(textDiv);
	
}

function autoSize($img) {
    // Set bg size
    var ratio = $img.height() / $img.width();
    var $parent = $img.parent().parent();
    // Get browser parent size
    var containerWidth = $parent.width();
    var containerHeight = $parent.height();
	// 获取后台指定的图片高宽
	var imgWidth = $img.data('width');
	var imgHeight = $img.data('height');

	// Scale the image
	if ((containerHeight / containerWidth) > ratio) {
		imgWidth = imgWidth || containerHeight / ratio;
		imgHeight = imgHeight || containerHeight;
	} else {
		imgWidth = imgWidth || containerWidth;
		imgHeight = imgHeight || containerWidth * ratio;
	}
	$img.height(imgHeight);
	$img.width(imgWidth);
    // Center the image
    $img.css('position','absolute');
    if(!isIE()){
    	$img.css('left', (containerWidth - $img.width()) / 2);
    }
    $img.css('top', (containerHeight - $img.height()) / 2);
}

function isIE() {
	if (!!window.ActiveXObject || "ActiveXObject" in window)
		return true;
	else
		return false;
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
done();
