//变量赋值
var element = render.parent.element;
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
if(isNaN(rate)){
	rate = 55;
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
var contentPanel = $("<div/>").attr('class', 'lui-inform-panel').appendTo(dataView.element);
if(position == '0'){
	var leftArea = $("<div/>").attr('class', 'section-left').attr('style','width:'+rate+'%').appendTo(contentPanel);
	var rightArea = $("<div/>").attr('class', 'list-inform-wrap').appendTo(contentPanel);
	var prevDiv = $("<div/>").attr('class', 'lui_portlet_switch_prev').appendTo(rightArea);
	$("<em/>").appendTo(prevDiv);
	var nextDiv = $("<div/>").attr('class', 'lui_portlet_switch_next').appendTo(rightArea);
	$("<em/>").appendTo(nextDiv);
	var rightUl = $("<ul/>").attr('class', 'list-inform').appendTo(rightArea);
}else if(position == '1'){
	var rightArea = $("<div/>").attr('class', 'list-inform-wrap').appendTo(contentPanel);
	var prevDiv = $("<div/>").attr('class', 'lui_portlet_switch_prev').appendTo(rightArea);
	$("<em/>").appendTo(prevDiv);
	var nextDiv = $("<div/>").attr('class', 'lui_portlet_switch_next').appendTo(rightArea);
	$("<em/>").appendTo(nextDiv);
	var rightUl = $("<ul/>").attr('class', 'list-inform').appendTo(rightArea);
	var leftArea = $("<div/>").attr('class', 'section-right').attr('style','width:'+rate+'%').appendTo(contentPanel);
}
for(var i=0; i<data.length; i++){
	addRow(data[i], i==0);
}
if(ellipsis){
	for(var i=0; i<data.length; i++){
		ellipsisText(data[i]);
	}
}

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
		LUI.$("#" + this.id + " .list-inform-item").each(
				function(c) { 
					var slide = {};
					slide["title"] = LUI.$(this);
					slide["text"] = LUI.$(this).find('.list-inform-link');
					this['pos'] = c;
					that.slideCache[c] = slide;
					that.slideNum ++ ; 
				}
		);
	}
	this.bindEnter = function() {
		var self = this;
		LUI.$("#" + self.id + " .list-inform-item")
		          .on("mouseenter", 
		      (function(){
				var  _bindFn = function(e) {
			          	if(LUI.$("#" + self.id + " .list-inform").is(":animated"))
			          		return;
					}
					if(self.slideNum > 3) {
						return function(e) {
							_bindFn(e);
							var contentHeight = $(LUI.$("#" + self.id + " .list-inform-wrap")[0]).height();
							var itemsHeight = 0;
							var items = LUI.$("#" + self.id + " .list-inform-item");
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
		LUI.$("#" + self.id + " .lui-inform-panel").on("mouseleave ", function() {
			LUI.$("#" + self.id + " .lui_portlet_switch_prev").hide();
			LUI.$("#" + self.id + " .lui_portlet_switch_next").hide();
		});
		LUI.$("#" + self.id + " .lui_portlet_switch_next").on("click", function() {
				self.titleScroll(self.firstIndex + 3);
		});
		LUI.$("#" + self.id + " .lui_portlet_switch_prev").on("click", function() {
				self.titleScroll(self.firstIndex - 3);
		});
	}
	this.titleScroll = function(index) {
		if(this.slideNum <= 0)
			return;
	    var _firstIndex = this.firstIndex;
		this.firstIndex = index;
		if(index <= 0)
			this.firstIndex = 0;
		else if(this.slideNum - index < 3)
			this.firstIndex = this.slideNum - 3 ;
	    if(_firstIndex != this.firstIndex){
	    	var height = 0;
	    	var items = LUI.$("#" + this.id + " .list-inform-item");
	    	for(var i = 0 ; i < this.firstIndex; i++){
	    		height += $(items[i]).outerHeight();
	    	}
	    	LUI.$("#" + this.id + " .list-inform").animate({"top":-height}, 500);
	    }
			
	}
}

//添加一行
function addRow(oneData, isFirst){
	var rowDiv =null;
	//日期
	var vdate = oneData.created;
	if(oneData.ocreated) {
		vdate = oneData.ocreated;
	} 
	var createDate = env.fn.parseDate(vdate);
	if(isFirst){ 
		var contentArea = $("<div/>").attr('class','inform-board').appendTo(leftArea);
		
		var creatorAndDateArea = $("<div/>").attr('class', 'list-inform-assist').appendTo(contentArea);
		//创建者
		if(showCreator){
			$('<div/>').attr('class', 'lui_dataview_classic_creator').text(oneData['creator'] || '').appendTo(creatorAndDateArea);
		}
		// 时间
		if(showCreated){
			var dateArea = $("<div/>").attr('class','list-inform-date').appendTo(creatorAndDateArea);
			dateArea.text(vdate);
//			dateArea.text(getDateRender(createDate,'/'));
//			$("<span/>").text(createDate.getFullYear()).appendTo(dateArea);
		}
		
		var textArea = $("<h4/>").attr('class', 'list-inform-title').appendTo(contentArea);
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
		
		var descArea = $("<p/>").attr('class', 'list-inform-depict').text(oneData['description'] || '').appendTo(contentArea);
		var imgArea = $("<div/>").attr('class', 'list-inform-imgbox').appendTo(contentArea);
		var aDiv = $('<a/>').attr('data-href',env.fn.formatUrl(oneData['href'])).attr('target',target).attr('onclick', "Com_OpenNewWindow(this);").appendTo(imgArea);
		
		var imageFormatUrl=env.fn.formatUrl(oneData['image']);
		if(oneData.imagefrom&&oneData.imagefrom=='rtf'){
			imageFormatUrl=oneData['image'];
		}
		
		var imgNode = $("<img/>").attr('src', imageFormatUrl).appendTo(aDiv);
		imgNode.load(function(){
			if(stretch){
				autoSize($(this));
			}else{
				var marginLeft = $(this).width()/2;
				if(marginLeft){
					$(this).css('margin-left',-marginLeft+'px');
				}
			}
		});
	}else{
		var contentArea = $("<li/>").attr('class','list-inform-item').appendTo(rightUl);
		
		//标题
		var textArea = $("<h4/>").attr('class', 'list-inform-title').appendTo(contentArea);
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
			var dateArea = $("<div/>").attr('class', 'lui_dataview_classic_created').css({"width":"75px"}).appendTo(textArea);
			$("<span/>").text(vdate).appendTo(dateArea);
		}
		//创建者
		if(showCreator){
			var creatorArea = $('<div/>').attr('class', 'lui_dataview_classic_creator').text(oneData['creator'] || '').appendTo(textArea);
		}
		
		var contentDiv = $("<div/>").attr('class', 'list-inform-item-content').appendTo(contentArea);
		var descArea = $("<p/>").attr('class', 'list-inform-depict').text(oneData['description'] || '').appendTo(contentDiv);
		var footArea = $("<div/>").attr('class', 'list-inform-btn-group').appendTo(contentDiv);
		var linkNode = $('<a/>').attr('class','inform-btn').text('查看详情').appendTo(footArea);
		if(oneData['href']){
			linkNode.attr({
				'data-href':env.fn.formatUrl(oneData['href']),
				'title' : '查看详情',
				'target':target
			});
			linkNode.click(function() {
				Com_OpenNewWindow(this);
			});
		}else{
			linkNode.attr({
				'href':'javascript:void(0)'
			});
		}
	}

}

function autoSize($img) {
    // Set bg size
    var ratio = $img.height() / $img.width();
    var $parent = $img.parent().parent();
    // Get browser parent size
    var containerWidth = $parent.width();
    var containerHeight = $parent.height();
    // Scale the image
    if ((containerHeight / containerWidth) > ratio) {
        $img.height(containerHeight);
        $img.width(containerHeight / ratio);
    } else {
        $img.width(containerWidth);
        $img.height(containerWidth * ratio);
    }
    // Center the image
    $img.css('position','absolute');
    $img.css('left', (containerWidth - $img.width()) / 2);
    $img.css('top', (containerHeight - $img.height()) / 2);
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
				'class':'list-inform-link',
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

function getDateRender(date,split){
	var template ="MM-DD" + split;
	template = template.replace("DD",date.getDate())
					   .replace("MM",date.getMonth()+1);
	return template;
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
		$("#" + dataView.cid + " .section-left").css('height',_height+'px');
		$("#" + dataView.cid + " .list-inform-wrap").css('height',_height+'px');
		$("#" + dataView.cid + " .list-inform-imgbox").css('height',(_height-50)+'px');
		if(stretch){
			$("#" + dataView.cid + " .list-inform-imgbox img").each(function(){
				autoSize($(this));
			});
		}
		$("#" + dataView.cid + " .lui-inform-panel").addClass("lui_portlet_show");
	},300);
}();