//变量赋值
var element = render.parent.element;
var dataView = render.parent;
var extend = param.extend?('_'+param.extend):'';
var target = render.vars.target?render.vars.target:'_blank';
var ellipsis = param.ellipsis == 'true' || render.vars.ellipsis=='true';
var position = render.vars.position?render.vars.position:'1';
var rate = parseInt(render.vars.rate);
var stretch = render.vars.stretch == null || render.vars.stretch == 'true';
var showCate = render.vars.showCate == null || render.vars.showCate == 'true'; 
var showCreator = render.vars.showCreator == null || render.vars.showCreator == 'true';
var showCreated = render.vars.showCreated == null || render.vars.showCreated == 'true';
var cateSize = parseInt(render.vars.cateSize);
var newDay = parseInt(render.vars.newDay);
var dateFormat = Com_Parameter.Date_format;

if(isNaN(rate)){
	rate = 45;
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

var contentPanel = $("<div/>").attr('class', 'lui_portlet_imgTxt_silder_wrap lui_portlet_imgTxt_hasSummary lui_portlet_imgTxt_knMap').appendTo(dataView.element);
if(stretch){
	contentPanel.attr('class','lui_portlet_imgTxt_silder_wrap lui_portlet_imgTxt_hasSummary lui_portlet_imgTxt_knMap lui_portlet_imgTxt_silder_imgCover_wrap');
}else {
	contentPanel.attr('class','lui_portlet_imgTxt_silder_wrap lui_portlet_imgTxt_hasSummary lui_portlet_imgTxt_knMap');
}
if(position == '1'){
	var leftArea = $("<div/>").attr('class', 'lui_portlet_txt_content lui_dataview_classic').appendTo(contentPanel);
	var prevDiv = $("<div/>").attr('class', 'lui_portlet_switch_prev').appendTo(leftArea);
	$("<em/>").appendTo(prevDiv);
	var nextDiv = $("<div/>").attr('class', 'lui_portlet_switch_next').appendTo(leftArea);
	$("<em/>").appendTo(nextDiv);
	var contentDiv = $("<div/>").attr('class', 'lui_portlet_txt_item_wrap').appendTo(leftArea);
	var rightArea = $("<div/>").attr('class', 'lui_portlet_thumb_content').attr('style','width:'+rate+'%').appendTo(contentPanel);
}else if(position == '0'){
	var rightArea = $("<div/>").attr('class', 'lui_portlet_thumb_content').attr('style','width:'+rate+'%').appendTo(contentPanel);
	var leftArea = $("<div/>").attr('class', 'lui_portlet_txt_content lui_dataview_classic').appendTo(contentPanel);
	var prevDiv = $("<div/>").attr('class', 'lui_portlet_switch_prev').appendTo(leftArea);
	$("<em/>").appendTo(prevDiv);
	var nextDiv = $("<div/>").attr('class', 'lui_portlet_switch_next').appendTo(leftArea);
	$("<em/>").appendTo(nextDiv);
	var contentDiv = $("<div/>").attr('class', 'lui_portlet_txt_item_wrap').appendTo(leftArea);
}
for(var i=0; i<data.length; i++){
	addRow(data[i], i==0);
}
if(ellipsis){
	for(var i=0; i<data.length; i++){
		ellipsisText(data[i]);
	}
}

//添加事件
LUI.$("#" + dataView.cid + " .lui_portlet_txt_item").each(function(){
	$(this).on("mouseenter",function(){
		var $this = $(this);
	    var $parent = $this.parents('.lui_portlet_imgTxt_silder_wrap').eq(0);
	    var index = $this.index();
	    $this.addClass('lui_dataview_current').siblings().removeClass('lui_dataview_current');
	    $parent.find('.lui_portlet_thumb_item').eq(index)
	        .addClass('lui_dataview_current').siblings().removeClass('lui_dataview_current');
	    if(stretch){
	    	var imgNode = $parent.find('.lui_portlet_thumb_item img').eq(index);
	    	autoSize(imgNode);
	    }
	})
});

var slideTuwen = new SlideTuwen({id:dataView.cid});

slideTuwen.init();
slideTuwen.bindEnter();
function SlideTuwen(config) {
	this.id = config.id;
	this.slideCache = [];
	this.slideNum = 0;
	this.cur = 0;
	this.timer = null;
	this.firstIndex = 0;
	this.init = function() {
	    var that = this;
		LUI.$("#" + this.id + " .lui_portlet_thumb_item").each(
			function(c) {
				var slide = {};
				slide["pic"] = $(this);
				this['pos'] = c;
				that.slideCache[c] = slide;
				that.slideNum ++ ; 
			}
		);
		LUI.$("#" + this.id + " .lui_portlet_txt_item").each(
				function(c) { 
					that.slideCache[c]["title"] = LUI.$(this);
					this['pos'] = c;
					that.slideCache[c]["text"] = LUI.$(this).find('.lui_portlet_txt_link');
				}
		);
	}
	this.bindEnter = function() {
		var self = this;
		LUI.$("#" + self.id + " .lui_portlet_txt_item, " + 
		       "#" + self.id + " .lui_portlet_thumb_item")
		          .on("mouseenter", 
		      (function(){
				var  _bindFn = function(e) {
			          	if(LUI.$("#" + self.id + " .lui_portlet_txt_item_wrap").is(":animated"))
			          		return;
					}
					if(self.slideNum > 4) {
						return function(e) {
							_bindFn(e);
							var contentHeight = $(LUI.$("#" + self.id + " .lui_portlet_txt_content")[0]).height();
							var itemsHeight = 0;
							var items = LUI.$("#" + self.id + " .lui_portlet_txt_item");
					    	for(var i = self.firstIndex ; i < items.length ; i++){
					    		itemsHeight += $(items[i]).outerHeight();
					    	}
					    	if(itemsHeight > contentHeight){
					    		LUI.$("#" + self.id + " .lui_portlet_switch_next").show();
					    		if(self.firstIndex == 0){
					    			LUI.$("#" + self.id + " .lui_portlet_switch_prev").hide();
					    		}else{
					    			LUI.$("#" + self.id + " .lui_portlet_switch_prev").show();
					    		}
					    	}else{
								LUI.$("#" + self.id + " .lui_portlet_switch_next").hide();
								if(self.firstIndex == 0){
					    			LUI.$("#" + self.id + " .lui_portlet_switch_prev").hide();
					    		}else{
					    			LUI.$("#" + self.id + " .lui_portlet_switch_prev").show();
					    		}
							}
						};
					} else {
						return function(e) {
							_bindFn(e);
							LUI.$("#" + self.id + " .lui_portlet_switch_prev").hide();
							LUI.$("#" + self.id + " .lui_portlet_switch_next").hide();
						};
					}
			})());
		
		LUI.$("#" + self.id + " .lui_portlet_imgTxt_silder_wrap").on("mouseleave ", function() {
			LUI.$("#" + self.id + " .lui_portlet_switch_prev").hide();
			LUI.$("#" + self.id + " .lui_portlet_switch_next").hide();
		});
		LUI.$("#" + self.id + " .lui_portlet_switch_next").on("click", function() {
			self.titleScroll(self.firstIndex + 4);
		});
		LUI.$("#" + self.id + " .lui_portlet_switch_prev").on("click", function() {
			self.titleScroll(self.firstIndex - 4);
		});
	}
	this.titleScroll = function(index) {
		if(this.slideNum <= 0)
			return;
	    var _firstIndex = this.firstIndex;
		this.firstIndex = index;
		if(index <= 0)
			this.firstIndex = 0;
		else if(this.slideNum - index < 4)
			this.firstIndex = this.slideNum - 4 ;
	    if(_firstIndex != this.firstIndex){
	    	var height = 0;
	    	var items = LUI.$("#" + this.id + " .lui_portlet_txt_item");
	    	for(var i = 0 ; i < this.firstIndex; i++){
	    		height += $(items[i]).outerHeight();
	    	}
	    	LUI.$("#" + this.id + " .lui_portlet_txt_item_wrap").animate({"top":-height}, 500);
	    }
	}
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
function addRow(oneData, isFirst){
	//日期
	var vdate = oneData.created;
	if(oneData.ocreated) {
		vdate = oneData.ocreated;
	} 
	var createDate = env.fn.parseDate(vdate);
	
	var contentArea = $("<div/>").appendTo(contentDiv);
	if(isFirst){ 
		contentArea.attr('class', 'lui_portlet_txt_item clearfloat lui_dataview_current');
	}else{
		contentArea.attr('class', 'lui_portlet_txt_item clearfloat');
	}
	var textArea = $("<div/>").attr('class','lui_portlet_txt_textArea clearfloat').appendTo(contentArea);
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
	var descArea = $("<div/>").attr('class','lui_portlet_txt_summary').text(oneData['description'] || '').appendTo(contentArea);
	
	//图片
	var imgArea = $("<div/>").appendTo(rightArea);
	if(isFirst){
		imgArea.attr('class', 'lui_portlet_thumb_item lui_dataview_current');
	}else{
		imgArea.attr('class', 'lui_portlet_thumb_item');
	}
	var imageLink = $("<a/>").attr('data-href',env.fn.formatUrl(oneData['href'])).attr('target',target).attr('onclick', "Com_OpenNewWindow(this);").appendTo(imgArea);
	
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
}
//设置图片自适应
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
    $img.css('left', (containerWidth - $img.width()) / 2);
    $img.css('top', (containerHeight - $img.height()) / 2);
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

~~function() {
	setTimeout(function(){
		var _height = element.parent().height();
		$("#" + dataView.cid + " .lui_portlet_txt_content").css('height',_height+'px');
		$("#" + dataView.cid + " .lui_portlet_thumb_content").css('height',_height+'px');
		$("#" + dataView.cid + " .lui_portlet_thumb_content a").css('height',_height+'px');
		if(stretch){
			$("#" + dataView.cid + " .lui_portlet_thumb_item img").each(function(){
				autoSize($(this));
			});
		}
		$("#" + dataView.cid + " .lui_portlet_imgTxt_silder_wrap").addClass("lui_portlet_show");
	},300);
}();