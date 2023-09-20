//变量赋值
var dataView = render.parent;
var ellipsis = param.ellipsis == 'true' || render.vars.ellipsis=='true';
var target = render.vars.target?render.vars.target:'_blank';

var containerDiv = $("<div/>").attr('class','lui_dataview_imgtext').appendTo(dataView.element);
var contentDiv = $("<ul/>").appendTo(containerDiv);;
for(var i=0; i<data.length; i++){
	addLiRow(data[i], i);
}
if(ellipsis){
	for(var i=0; i<data.length; i++){
		ellipsisText(data[i]);
	}
}

if(data==null || data.length==0){
	done();
	return;
}

//添加一行
function addLiRow(oneData, no){
	if(no == 0){
		var rowDiv = $('<li/>').attr('class','first_order current').appendTo(contentDiv);
	}else if(no==1){
		var rowDiv = $('<li/>').attr('class','secend_order').appendTo(contentDiv);	
	}else if(no==2){
		var rowDiv = $('<li/>').attr('class','third_order').appendTo(contentDiv);	
	}else{
		var rowDiv = $('<li/>').appendTo(contentDiv);
	}	
	rowDiv.mouseover(function(){
		$(this).siblings().removeClass("current");
		$(this).addClass("current");
	});
	//序号
	var  liBox= $('<div/>').appendTo(rowDiv);
	var picBox = $('<div/>').attr('class','pic_box').appendTo(liBox);
	var linkPicBox = $('<a/>').attr({
		'data-href':env.fn.formatUrl(oneData['href']),
		'target':target,
		'title':oneData['text']
	});
	linkPicBox.click(function() {
		Com_OpenNewWindow(this);
	});
	var img = $('<img/>').attr({
		'src':env.fn.formatUrl(oneData['image']),
		'alt':oneData['text'],
		'width':'60px',
		'height':'80px'
	});
	img.appendTo(linkPicBox);
	linkPicBox.appendTo(picBox);
	
	var textBox = $('<div/>').attr('class','text_box textEllipsis').appendTo(liBox);
	var linkTextBox = $('<a/>').attr({
		'data-href':env.fn.formatUrl(oneData['href']),
		'target':target,
		'title':oneData['text']
	});	
	linkTextBox.click(function() {
		Com_OpenNewWindow(this);
	});
	linkTextBox.appendTo(textBox);
	var iTag = $('<i/>')
	iTag.text(no+1);
	iTag.appendTo(linkTextBox);
	var subject = $('<span/>')
	subject.text(oneData['text']);
	subject.appendTo(linkTextBox);

	seajs.use(['lang!sys-ui' ], function(lang){	
		//文本区域
		var creatorArea = $("<div/>").attr('class','creator').appendTo(textBox);
		creatorArea.text(lang['dataview.imgtext.author']+"："+oneData['creator']);
		var ceatedArea = $("<div/>").attr('class','created').appendTo(textBox);
		ceatedArea.text(lang['dataview.imgtext.publishTime']+"："+oneData['created']);
		var viewcount = $("<div/>").attr('class','view-count')
				.appendTo(textBox);
		viewcount.text(lang['dataview.imgtext.readCount']+"："+oneData['docReadCount']);
	});
}
//添加一个带链接的格子
function addLinkCell(rowDiv, oneData, text, href, css){
	var textBox;
	if(oneData[href]){
		textBox = $('<a/>').attr({
				'data-href':env.fn.formatUrl(oneData[href]),
				'target':target
			});
		textBox.click(function() {
			Com_OpenNewWindow(this);
		});
	}
	textBox.attr('title', oneData[text]);
	textBox.appendTo(rowDiv);
	return textBox;
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
	// textArea.height()-1是为了兼容部分浏览器英文字符跟中文字符获取高度相差1的问题。
	for(var i=text.length-2; height>5 && i>0 && textArea.height()-1>height; i--){
		textBox.text(text.substring(0, i)+'..');
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

done();