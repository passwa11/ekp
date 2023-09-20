//变量赋值
var dataView = render.parent;
var extend = param.extend?('_'+param.extend):'';
var ellipsis = param.ellipsis == 'true' || render.vars.ellipsis=='true';
var target = render.vars.target?render.vars.target:'_blank';
var highlight = render.vars.highlight == 'true'?cssName('highlight'):'';
var showCate = render.vars.showCate == null || render.vars.showCate == 'true'; 
var showCreator = render.vars.showCreator == null || render.vars.showCreator == 'true';
var showCreated = render.vars.showCreated == null || render.vars.showCreated == 'true';
var showRank = !render.vars.showRank  ? false : render.vars.showRank;
var icon = render.vars.icon?render.vars.icon:"iconfont lui_iconfont_navleft_com_notice";
		
ellipsis=true;

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
var swiperWrapper = $("<div/>").attr('class', 'lui_portlet_notice_swiper_wrapper').appendTo(dataView.element);
var contentDiv = $("<div/>").attr('class', 'lui_dataview_classic'+extend).appendTo(swiperWrapper);

for(var i=0; i<data.length; i++){
	addRow(data[i], i==0, i);
}

var billboard=$(swiperWrapper);

//公告牌轮播逻辑
billboard.find(".lui_dataview_classic_billboard_row:eq(0)").clone(true).appendTo(billboard.find(".lui_dataview_classic_billboard")); //克隆第一个放到最后(实现无缝滚动)
var height = billboard.height(); //一个li的高度
var totalHeight = (billboard.find(".lui_dataview_classic_billboard .lui_dataview_classic_billboard_row").length * billboard.find(".lui_dataview_classic_billboard .lui_dataview_classic_billboard_row").eq(0).height()) - height;//获取li的总高度再减去一个li的高度(再减一个Li是因为克隆了多出了一个Li的高度)
billboard.find(".lui_dataview_classic_billboard").height(totalHeight);//给ul赋值高度
var index = 0;
var autoTimer = 0; //全局变量目的实现左右点击同步
var clickEndFlag = true; //设置每张走完才能再点击
function tab() {
	billboard.find(".lui_dataview_classic_billboard").stop().animate({
        top: -index * height
    }, 400, function () {
        clickEndFlag = true; //图片走完才会true
        if (index == billboard.find(".lui_dataview_classic_billboard .lui_dataview_classic_billboard_row").length - 1) {
        	billboard.find(".lui_dataview_classic_billboard").css({
                top: 0
            });
            index = 0;
        }
    })
}

function next() {
    index++;
    if (index > billboard.find(".lui_dataview_classic_billboard .lui_dataview_classic_billboard_row").length - 1) { //判断index为最后一个Li时index为0
        index = 0;
    }
    tab();
}

function prev() {
    index--;
    if (index < 0) {
        index = billboard.find(".lui_dataview_classic_billboard .lui_dataview_classic_billboard_row").size() -
            2; //因为index的0 == 第一个Li，减二是因为一开始就克隆了一个LI在尾部也就是多出了一个Li，减二也就是_index = .lui_dataview_classic_row的长度减二
        billboard.find(".lui_dataview_classic_billboard").css("top", -(billboard.find(".lui_dataview_classic_billboard .lui_dataview_classic_billboard_row").size() - 1) *
            height); //当_index为-1时执行这条，也就是走到li的最后一个
    }
    tab();
}

//切换到下一张
billboard.find(".swiper_wrap .gt").on("click", function () {
    if (clickEndFlag) {
        next();
        clickEndFlag = false;
    }
});

//切换到上一张
billboard.find(".swiper_wrap .lt").on("click", function () {
    if (clickEndFlag) {
        prev();
        clickEndFlag = false;
    }
});

//自动轮播
autoTimer = setInterval(next, 3000);
billboard.find(".lui_dataview_classic_billboard a").hover(function () {
    clearInterval(autoTimer);
}, function () {
    autoTimer = setInterval(next, 3000);
})

//鼠标放到左右方向时关闭定时器
billboard.find(".swiper_wrap .lt,.swiper_wrap .gt").hover(function () {
    clearInterval(autoTimer);
}, function () {
    autoTimer = setInterval(next, 3000);
})
//1文字轮播(2-5页中间)结束

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
	var rowDiv = $('<div/>').attr('class', cssName('row')+' clearfloat').appendTo(contentDiv);
	
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
			iconSpan.attr('class', cssName('icon')+" "+icon);
		}
	}
	
	//文本区域
	var textArea = $("<div/>").attr('class',  cssName('textArea')+' clearfloat')
			.appendTo(rowDiv);
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

	//创建者
	if(showCreator){
		addCell(textArea, oneData, 'created');
	}
	
	//创建时间
	if(showCreated){
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
	console.log(textBox)
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