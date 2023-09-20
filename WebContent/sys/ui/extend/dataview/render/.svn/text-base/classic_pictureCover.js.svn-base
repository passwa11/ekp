//变量赋值
var dataView = render.parent;
var extend = param.extend?('_'+param.extend):'';
var ellipsis = param.ellipsis == 'true' || render.vars.ellipsis=='true';
var target = render.vars.target?render.vars.target:'_blank';
var highlight = render.vars.highlight == 'true'?cssName('highlight'):'';
var showCate = render.vars.showCate == null || render.vars.showCate == 'true'; 
var showCreator = render.vars.showCreator == null || render.vars.showCreator == 'true';
var showCreated = render.vars.showCreated == null || render.vars.showCreated == 'true';
var img = render.vars.img;
//图片高度
var imgHeight = parseInt(render.vars.imgHeight);
if(isNaN(imgHeight)){
	imgHeight = 0;
}
//拉伸
var stretch = render.vars.stretch == null || render.vars.stretch == 'true';

var showRank = !render.vars.showRank  ? false : render.vars.showRank

var firstRowScroll = render.vars.firstRowScroll == 'true'?true:false;
var cateSize = parseInt(render.vars.cateSize);
var subjectSize = parseInt(render.vars.subjectSize);
var newDay = parseInt(render.vars.newDay);
if(isNaN(cateSize)){
	cateSize = 0;
}
if(isNaN(subjectSize)){
	subjectSize = 0;
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

var contentDiv = $("<div/>").attr('class', 'lui_dataview_pictrue_cover lui_dataview_classic'+extend).appendTo(dataView.element);
//封面
if(typeof(img)=='string' &&img.length>0){
	var pictrueCover = $("<div/>").attr('class', 'lui_dataview_pictrue_cover').appendTo(contentDiv);
	var pictrueCover_img=$("<img/>").attr('src',getImgUrl(img)).appendTo(pictrueCover)
	console.log(pictrueCover_img.attr("src"))
	if(imgHeight>0){
		pictrueCover.attr('class','lui_dataview_pictrue_cover lui_dataview_pictrue_cover_restrict_height');
		pictrueCover.attr("style","height:"+imgHeight+"px;"+"line-height:"+imgHeight+"px;");
		if(stretch){
			pictrueCover_img.attr("style","width:100%;height:auto");
		}else{
			pictrueCover_img.attr("style","max-height:100%;max-width:100%");
		}
	}else{
		pictrueCover_img.attr("style","width:100%;height:auto");
	}
}




for(var i=0; i<data.length; i++){
	addRow(data[i], i==0, i);
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
function addRow(oneData, isFirst, i){
	var rowDiv =null;
	if(isFirst && firstRowScroll){  //首行 并且首行左右滚动
		var rowParentDiv = $('<div/>').appendTo(contentDiv);
		rowParentDiv.css({'overflow':'hidden',
			'height':26+'px',
			'line-height':19+'px',
			'width':render.parent.element.width()});
		rowDiv = $('<div />').attr('class', cssName('row')+' clearfloat').appendTo(rowParentDiv);
		rowDiv.addClass(highlight);
		scrollText(rowParentDiv,rowDiv,'left',1,10);//滚动字幕
	} else if(isFirst && !firstRowScroll){  //首行 并且首行左右不滚动
		rowDiv = $('<div/>').attr('class', cssName('row')+' clearfloat').appendTo(contentDiv);
		rowDiv.addClass(highlight);
	}else{
		rowDiv = $('<div/>').attr('class', cssName('row')+' clearfloat').appendTo(contentDiv);
	}
	
	
	var isThisRowRank = false;
	//排名
	if(showRank != "no") {
		var rank = i + 1;
		if((showRank == "three" && rank <= 3) || showRank == "all") {
			var rankSpan = $("<span>" + rank + "</span>").appendTo(rowDiv);
			rankSpan.addClass(cssName('rank'));
			
			if(rank <= 3) {
				rankSpan.addClass(cssName('rank' + rank))
			}
			
			isThisRowRank = true;
		}
	}
	
	
	
	//图标
	var iconSpan = $('<span/>').appendTo(rowDiv);
	var showImg = false;
	if(oneData.icon && oneData.icon.indexOf('/')==-1){
		iconSpan.attr('class', cssName('customicon'));
		$('<span/>').attr('class', 'lui_icon_s '+oneData.icon).appendTo(iconSpan);
	} else if (oneData.icon && oneData.icon.indexOf('/')>=0) {
		//增加自定义图片,czk2019
		iconSpan.attr('class', cssName('customicon'));
		iconSpan.css("width","30px");
		var personImg = $("<img/>").appendTo(iconSpan);
		personImg.attr("width","30px");
		personImg.attr("height","30px");
		personImg.css("border-radius","50%");
		personImg.css("overflow","hidden");
		personImg.css("border", "none");
		personImg.attr("src", Com_Parameter.ContextPath+oneData.icon);
		showImg = true;
	} else{
		if(!isThisRowRank) {
			iconSpan.attr('class', cssName('icon'));
		}
	}
	
	//文本区域
	var textArea = $("<div/>").attr('class',  cssName('textArea')+' clearfloat')
			.appendTo(rowDiv);
	//New图标
	if(showNewIcon(oneData)){
		$('<span/>').attr('class', cssName('new'))
				.appendTo(textArea);
	}
	if (showImg) {
		rowDiv.css("line-height","35px");
		if(!isThisRowRank) {
			textArea.css("padding-left","35px");
		} else {
			textArea.css("padding-left","55px");
		}
	}
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
	linkBox = addLinkCell(textArea, oneData, 'text', 'href', 'title');
	text = oneData.text || '';
	if(subjectSize>0 && text.length>subjectSize){
		text = text.substring(0, subjectSize)+"...";
	}
	
	if(ellipsis && text.length>3){
		//等全部显示完毕后在调整宽度，避免出现滚动条导致页面变窄
		linkBox.text('.'); //原来是用小数点来撑开div高度，但是小数点撑开的div高度为17，而一般汉字撑开为19，这样就造成一行字也会触动截取
		oneData.textBox = linkBox;
		oneData.textArea = textArea;
		oneData.text=text;
	}else{
		linkBox.text(text);
	}
	if(oneData.isintroduced=='true'){
		$('<span/>').attr('class', cssName('introduced'))
		.appendTo(textArea);
	}
	
	//其它信息
	addCell(textArea, oneData, 'otherinfo');
	
	
	//info区域
	var infoArea = $("<div/>").attr('class',  cssName('infoArea')+' clearfloat')
			.appendTo(rowDiv);
	//创建者
	if(showCreator){
		addCell(infoArea, oneData, 'creator');
	}
	//创建时间
	if(showCreated){
		addCell(infoArea, oneData, 'created');
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

	//最多三行
	height=2*height;
	textBox.text(text);
	
	// textArea.height()-4预留一定空间做浏览器兼容。
	for(var i=text.length-2; height>5 && i>0 && textArea.height()-4>height; i--){
		textBox.text(text.substring(0, i)+'...');
	}
	oneData.textBox = null;
	oneData.textArea = null;
}

//--------------------------------------------左右滚动效果----------------------------------------------
/*
appendToObj：        显示位置（目标对象）
scrollObj :    滚动对象
scrollDirection：    滚动方向（值：left、right）
steper：        每次移动的间距（单位：px；数值越小，滚动越流畅，建议设置为1px）
interval:        每次执行运动的时间间隔（单位：毫秒；数值越小，运动越快）
*/
var scrollTime;
function scrollText(appendToObj,scrollObj,scrollDirection,steper,interval){
    var textWidth,posInit,posSteper;
    var showWidth = appendToObj.width();
    if (scrollDirection=='left'){
        posInit = showWidth;
        posSteper = steper;
    }else{
        posSteper = 0 - steper;
    }
    if(steper<1 || steper>showWidth){//每次移动间距超出限制(单位:px)
    	Steper = 1;
    }
    if(interval<1){//每次移动的时间间隔（单位：毫秒）
       interval = 10;
    }
    with(scrollObj){
      css('float','left');
      css('overflow','hidden');
      css('width',showWidth);
      
      textWidth = showWidth;
      if(isNaN(posInit)){
    	  posInit = 0 - textWidth;
      }
      css('margin-left',posInit);
      mouseover(function(){
          clearInterval(scrollTime);
      });
      mouseout(function(){
          scrollTime = setInterval(function(){
        	  scrollAutoPlay(scrollObj,scrollDirection,showWidth,textWidth,posSteper);
        	         },interval);
      });
    }
    scrollTime = setInterval(function(){
      scrollAutoPlay(scrollObj,scrollDirection,showWidth,textWidth,posSteper);
             },interval);
}
function scrollAutoPlay(scrollObj,scrolldir,showwidth,textwidth,steper){
    var posInit,currPos;
    with(scrollObj){
        currPos = parseInt(css('margin-left'));
        if(scrolldir=='left'){
            if(currPos<0 && Math.abs(currPos)>textwidth){
                css('margin-left',showwidth);
            }else{
                css('margin-left',currPos-steper);
            }
        }else{
            if(currPos>showwidth){
                css('margin-left',(0-textwidth));
            }else{
                css('margin-left',currPos-steper);
            }
        }
    }
}
function getImgUrl (imgSrc) {
    if(imgSrc.indexOf('/')==0){
    	imgSrc=imgSrc.substr(1,imgSrc.length);
    }
    return $('<div>').html(Com_Parameter.ContextPath+imgSrc).text() ;
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