//变量赋值
var dataView = render.parent;
var extend = param.extend?('_'+param.extend):'';
var ellipsis = param.ellipsis == 'true' || render.vars.ellipsis=='true';
var target = render.vars.target?render.vars.target:'_blank';
var highlight = render.vars.highlight == 'true'?cssName('highlight'):'';
var showCate = render.vars.showCate == null || render.vars.showCate == 'true'; 
var showCreator = render.vars.showCreator == null || render.vars.showCreator == 'true';
var showCreated = render.vars.showCreated == null || render.vars.showCreated == 'true';
var firstRowScroll = render.vars.firstRowScroll == 'true'?true:false;
var cateSize = parseInt(render.vars.cateSize);
var width = parseInt(render.vars.width);
var height = parseInt(render.vars.height);
var sumSize = parseInt(render.vars.sumSize);
var newDay = parseInt(render.vars.newDay);
if(isNaN(sumSize)){
	sumSize = 0;
}
if(isNaN(cateSize)){
	cateSize = 0;
}
if(isNaN(width)){
	width = 200;
}
if(isNaN(height)){
	height = 140;
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
var contentDiv = $("<div/>").attr('class', 'lui_dataview_classic'+extend).appendTo(dataView.element);
for(var i=0; i<data.length; i++){
	addRow(data[i], i==0);
}
if(ellipsis){
	for(var i=0; i<data.length; i++){
		ellipsisText(data[i]);
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

//样式简化调用
function cssName(name){
	return 'lui_dataview_classic'+extend+'_'+name;
}

//添加一个格子
function addCell(rowDiv, oneData, key){
	if(oneData[key]){
		$('<span/>').attr('class', cssName(key)).text(oneData[key])
				.appendTo(rowDiv);
	}
};

//添加一个带链接的格子
function addLinkCell(rowDiv, oneData, text, href, css){
	var textBox;
	if(oneData[href]){
		textBox = $('<a/>').attr({
				'class':cssName(css+'link'),
				'data-href':env.fn.formatUrl(oneData[href]),
				'target':target
			});
		textBox.click(function() {
			Com_OpenNewWindow(this);
		});
	}else{
		textBox = $('<span/>').attr('class', cssName(css+'nolink'));
	}
	textBox.attr('title', oneData[text]);
	textBox.appendTo(rowDiv);
	return textBox;
};

//添加一行
function addRow(oneData, isFirst){
	if(isFirst){  //首行 并且首行左右滚动
	   	var summary = oneData['description'];
		if(sumSize>0 && summary.length>sumSize){
			summary = summary.substring(0, sumSize)+'..';
		}else if(sumSize == 0){
			summary = summary;
		}else{
			summary = '';
		}
		
		
		// 首行DOM元素
        var $firstRowDom = $("<div class='"+cssName('first_row')+"' ></div>").appendTo(contentDiv);

		// 左侧图片展示区DOM元素
        var $leftDom = $("<div class='"+cssName('first_row_img')+"' ></div>").appendTo($firstRowDom);
        $leftDom.css({"height":height,"width":width});
        var $leftLinkDom = $("<a data-href='"+env.fn.formatUrl(oneData['href'])+"' target='"+target+"' ></a>").attr('onclick', "Com_OpenNewWindow(this);").appendTo($leftDom);
        var $leftImgDom = $("<img src='"+env.fn.formatUrl(oneData['image'])+"' />").appendTo($leftLinkDom);

        // 右侧标题、详细描述、查看详情、创建者信息展示区DOM元素
        var $rightDom = $("<div class='"+cssName('first_row_text')+"' ></div>").appendTo($firstRowDom);
        // 1、标题
        var $rightTitleDom = $("<div class='"+cssName('first_row_title')+"' ></div>").appendTo($rightDom);
        var $rightTitleLinkDom = $("<a data-href='"+env.fn.formatUrl(oneData['href'])+"' target='"+target+"' ></a>").attr('onclick', "Com_OpenNewWindow(this);").appendTo($rightTitleDom);
        $rightTitleLinkDom.text(oneData["text"]);
        $rightTitleLinkDom.attr("title",oneData["text"]);
        // 2、详细描述
        var $rightDescDom = $("<div class='"+cssName('first_row_desc')+"' ></div>").appendTo($rightDom);
        var $p = $("<p></p>").appendTo($rightDescDom);
        $p.text(summary);
        // 3、查看详情
        var $rightDetailDom = $("<div class='"+cssName('first_row_detail')+"' ></div>").appendTo($rightDom);
        var $rightDetailLinkDom = $("<a data-href='"+env.fn.formatUrl(oneData['href'])+"' target='"+target+"' ></a>").attr('onclick', "Com_OpenNewWindow(this);").appendTo($rightDetailDom);
        $rightDetailLinkDom.text("["+oneData['detail']+"]");
        // 4、创建人、创建时间
        var $rightCreateInfoDom = $("<div class='"+cssName('first_row_creator')+"' ></div>").appendTo($rightDom);
        var creator = ""; // 创建人
        if(showCreator){
        	creator = oneData['fdAuthor'] ? oneData['fdAuthor'] : oneData['creator'];
        }
        if(creator!=""){
        	var $span = $("<span class='creator' ></span>").appendTo($rightCreateInfoDom);
        	$span.text(creator);
        }
        var createdDate = "" // 创建时间
		if(showCreated){
			createdDate = oneData['created'];
		}
        if(createdDate!=""){
        	if(creator!=""){
        		$("<span>&nbsp;|&nbsp;</span>").appendTo($rightCreateInfoDom);
        	}
        	var $span = $("<span class='createdDate' ></span>").appendTo($rightCreateInfoDom);
        	$span.text(createdDate);
        }
       
       // 计算详细描述区域的可展示高度
       var descHeight = contentDiv.height() - ( $rightTitleDom.outerHeight(true) + $rightDetailDom.outerHeight(true) + $rightCreateInfoDom.outerHeight(true) );
       descHeight = descHeight - 2;
       $rightDescDom.height(descHeight);
       
	}else{
		var rowDiv = $('<div/>').attr('class', cssName('row')+' clearfloat').appendTo(contentDiv);
		//图标
		var iconSpan = $('<span/>').appendTo(rowDiv);
		if(oneData.icon && oneData.icon.indexOf('/')==-1){
			iconSpan.attr('class', cssName('customicon'));
			$('<span/>').attr('class', 'lui_icon_s '+oneData.icon).appendTo(iconSpan);
		}else{
			iconSpan.attr('class', cssName('icon'));
		}
		
		//文本区域
		var textArea = $("<div/>").attr('class',  cssName('textArea')+' clearfloat')
				.appendTo(rowDiv);
		var linkBox, text;
		
		//分类
		if(showCate && oneData.catename && oneData.catename!=''){
			linkBox = addLinkCell(textArea, oneData, 'catename', 'catehref', 'cate_');
			text = oneData.catename;
			if(cateSize>0 && text.length>cateSize){
				text = text.substring(0, cateSize)+'...';
			}
			linkBox.text('['+text+']');
		}
		
		//标题
		linkBox = addLinkCell(textArea, oneData, 'text', 'href', '');
		text = oneData.text || '';
		if(ellipsis && text.length>3){
			//等全部显示完毕后在调整宽度，避免出现滚动条导致页面变窄
			linkBox.text('.');
			oneData.textBox = linkBox;
			oneData.textArea = textArea;
		}else{
			linkBox.text(text);
		}
		
		if(oneData.isintroduced=='true'){
			$('<span/>').attr('class', cssName('introduced'))
			.appendTo(textArea);
		}
		
		//其它信息
		addCell(textArea, oneData, 'otherinfo');
		
		//New图标
		if(showNewIcon(oneData)){
			$('<span/>').attr('class', cssName('new'))
					.appendTo(textArea);
		}
		
		//创建时间
		if(showCreated){
			addCell(textArea, oneData, 'created');
		}
		
		//创建者
		if(showCreator){
			addCell(textArea, oneData, 'creator');
		}
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
	// textArea.height()-3预留一定空间做浏览器兼容。
	for(var i=text.length-2; height>5 && i>0 && textArea.height()-3>height; i--){
		textBox.text(text.substring(0, i)+'...');
	}
	oneData.textBox = null;
	oneData.textArea = null;
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
