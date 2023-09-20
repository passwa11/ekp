//变量赋值
var dataView = render.parent;
var extend = param.extend?('_'+param.extend):'';
var ellipsis = param.ellipsis == 'true' || render.vars.ellipsis=='true';
var target = render.vars.target?render.vars.target:'_blank';
var highlight = render.vars.highlight == 'true'?cssName('highlight'):'';
var showCate = render.vars.showCate == null || render.vars.showCate == 'true'; 
var showCreator = render.vars.showCreator == null || render.vars.showCreator == 'true';
var showCreated = render.vars.showCreated == null || render.vars.showCreated == 'true';

var showEvaluateCount = render.vars.showEvaluateCount == null || render.vars.showEvaluateCount == 'true';
var showRank = render.vars.showRank;
var showReadCount = render.vars.showReadCount == null || render.vars.showReadCount == 'true';
var showDocscore = render.vars.showDocscore == null || render.vars.showDocscore == 'true';


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

var contentDiv = $("<div/>").attr('class', 'lui_dataview_classicrich'+extend).appendTo(dataView.element);
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
	if ("fdTotalCount" == name){
		name = "readcount";
	}
	return 'lui_dataview_classicrich'+extend+'_'+name;
}


//添加一个格子
function addCell(rowDiv, oneData, key, isOccupy) {
	if(oneData[key] || typeof(oneData[key]) == "number") {
		$('<span/>').attr('class', cssName(key)).text(oneData[key])
				.appendTo(rowDiv).attr("title", oneData[key]);
	}  else {
		if(isOccupy) {
			$('<span/>').attr('class', cssName(key)).html("&nbsp")
			.appendTo(rowDiv);
		}
	}
};

//添加一个带链接的格子
function addLinkCell(rowDiv, oneData, text, href, css) {
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
		scrollText(rowParentDiv,rowDiv,'left',1,10);//滚动字幕
	} else{
		rowDiv = $('<div/>').attr('class', cssName('row')+' clearfloat').appendTo(contentDiv);
	}
	
	
	var isThisRowRank = false;
	//排名
	if(showRank != "no") {
		var rank = i + 1;
		if((showRank == "three" && rank <= 3)
		   || showRank == "all") {
			var rankSpan = $("<span>" + rank + "</span>").appendTo(rowDiv);
			rankSpan.addClass(cssName('rank'));
			
			if(rank <= 3) {
				rankSpan.addClass(cssName('rank' + rank))
			}
			
			isThisRowRank = true;
		}
	}
	
	
	//文本区域
	var textArea = $("<div/>").attr('class',  cssName('textArea')+' clearfloat')
			.appendTo(rowDiv);
	if (isThisRowRank) {
		textArea.css("padding-left","25px");
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
	linkBox = addLinkCell(textArea, oneData, 'text', 'href', '');
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
	
	//New图标
	if(showNewIcon(oneData)){
		$('<span/>').attr('class', cssName('new'))
				.appendTo(textArea);
	}
	
	
	if(showDocscore) {
		if(typeof(oneData['docscore']) === "undefined") {
			oneData['docscore'] = 0;
		}
		addCell(textArea, oneData, 'docscore');
	}
	
	if(showEvaluateCount) {
		if(typeof(oneData['evalcount']) === "undefined") {
			oneData['evalcount'] = 0;
		}
		addCell(textArea, oneData, 'evalcount');
	}
	
	if(showReadCount) {
		if ('fdTotalCount' == oneData['fdReadcountType']){
			if(typeof(oneData['fdTotalCount']) === "undefined") {
				oneData['fdTotalCount'] = 0;
			}
			addCell(textArea, oneData, 'fdTotalCount');
		} else {
			if(typeof(oneData['readcount']) === "undefined") {
				oneData['readcount'] = 0;
			}
			addCell(textArea, oneData, 'readcount');
		}
	}
	
	//创建时间
	if(showCreated){
		addCell(textArea, oneData, 'created', true);
	}
	
	//创建者
	if(showCreator){
		addCell(textArea, oneData, 'creator');
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