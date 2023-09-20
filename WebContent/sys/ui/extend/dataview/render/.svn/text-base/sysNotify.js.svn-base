
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
var newDay = parseInt(render.vars.newDay);
var jspHtml='<script type="text/javascript" src="../../sys/notify/sys_notify_todo_ui/sysNotifyTodo_print.jsp">';
$(jspHtml).appendTo(dataView.element);

buildCount(dataView,data[0]);

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
var contentDiv = $("<div/>").attr('class', 'lui_dataview_classic'+extend).appendTo(dataView.element);
if(data==null || data.length==0){
	done();
	return;
}

buildSysNotify(contentDiv,data,dataView);
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
	if(buildLinkCell(textBox,oneData,cssName,target)!=false){
		textBox=buildLinkCell(textBox,oneData,cssName,target);
	}else{
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
	}

	textBox.attr('title', oneData[text]);
	textBox.appendTo(rowDiv);
	return textBox;
};

//添加一行
function addRow(oneData, isFirst){
	var rowDiv =null;
	if(isFirst && firstRowScroll){  //首行 并且首行左右滚动
		var rowParentDiv = $('<div/>').appendTo(contentDiv);
		rowParentDiv.css({'overflow':'hidden',
			'height':26+'px',
			'line-height':19+'px',
			'width':render.parent.element.width()});
		rowDiv = $('<div />').attr('class', cssName('row')+' clearfloat').appendTo(rowParentDiv);
		rowDiv.addClass(highlight);
		scrollText(rowParentDiv,rowDiv,'left',1,20);//滚动字幕
	} else if(isFirst && !firstRowScroll){  //首行 并且首行左右不滚动
		rowDiv = $('<div/>').attr('class', cssName('row')+' clearfloat').appendTo(contentDiv);
		rowDiv.addClass(highlight);
	}else{
		rowDiv = $('<div/>').attr('class', cssName('row')+' clearfloat').appendTo(contentDiv);
	}
	
	if(!buildIconSpan(oneData,rowDiv,cssName)){
		//图标
		var iconSpan = $('<span/>').appendTo(rowDiv);
		if(oneData.icon && oneData.icon.indexOf('/')==-1){
			iconSpan.attr('class', cssName('customicon'));
			$('<span/>').attr('class', 'lui_icon_s '+oneData.icon).appendTo(iconSpan);
		}else{
			iconSpan.attr('class', cssName('icon'));
		}
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


function buildSysNotify(contentDiv,data,dataView){
	if(typeof(data[0])!='undefined'){
		var infoTip;
		var isShowEmail;
		var crqPrefix;
		if(data[0]['sysnotifysize']==0 || data[0]['sysnotifysize']=='0'){
			var dataType=data[0]['datatype'];
			var finish=data[0]['finish'];
			typeof(dataType)=='undefined'?"":dataType;
			totalrows=0;
			buildHtml(contentDiv,totalrows,dataType,finish);
		}else{
		if(typeof(data[0]['href'])!='undefined' && data[0]['href'].indexOf("sys/notify")>-1){
		var totalrows=data[0]['count'];
		var fdType=data[0]['type'];
		var finish=data[0]['finish'];
		var dataType=data[0]['datatype'];
		typeof(dataType)=='undefined'?"":dataType;
			buildHtml(contentDiv,totalrows,dataType,finish);
		}else{
			return false;
		}
		}
	}else{
		return false;
	}
}

function buildCount(dataView,data0){
	if(typeof(data0)!='undefined'){
		if(typeof(data0['href'])!='undefined' && data0['href'].indexOf("sys/notify")>-1){
			dataView.parent.emit("count", {count:data0['count'],more:false});
		}
	}else{
		return false;
	}
}

function buildIconSpan(oneData,rowDiv,cssName){
	if(typeof(oneData)!='undefined'){
	if(typeof(oneData['href'])!='undefined' && oneData['href'].indexOf("sys/notify")>-1){
	var iconSpan = $('<span/>').appendTo(rowDiv);
	if(oneData.icon && oneData.icon.indexOf('/')==-1){
		iconSpan.attr('class', cssName('customicon'));
		$('<span/>').attr('class', 'lui_icon_s '+oneData.icon+' lui_notify_content_'+oneData['type']).appendTo(iconSpan);
	}else{
		iconSpan.attr('class', cssName('icon')+' lui_notify_content_'+oneData['type']);
	}
	return true;
	}else{
		return false;
	}
}
}
function buildLinkCell(textBox,oneData,cssName,target){
	if(typeof(oneData)!='undefined'){
	if(typeof(oneData['href'])!='undefined' && oneData['href'].indexOf("sys/notify")>-1){
		if(oneData['href']&& typeof($('<a/>'))!='undefined'){
			textBox = $('<a/>').attr({
					'class':cssName('link'),
					'target':target,
					'onclick':'test("'+SYSNOTIFY.LUI_ContextPath+oneData['href']+'","'+oneData['type']+'")',
					'style':'cursor:pointer;'
				});
		}else{
			if(typeof($('<span/>'))!='undefined'){
				textBox = $('<span/>').attr('class', cssName('nolink'));
			}
			
		}
		return textBox;
	}else{
		return false;
	}
}else{
	return false;
}
}

function buildHtml(contentDiv,totalrows,dataType,finish){
	var html="var head = document.getElementsByTagName('HEAD').item(0);"+
	"var style = document.createElement('link');"+
	"style.href = '../../sys/notify/resource/css/notify.css?v=1.9';"+
	"style.rel = 'stylesheet';"+
	"style.type = 'text/css';"+
	"head.appendChild(style);"+
"function test(url,notifyType){window.open(url);if(notifyType=='2'){window.location.reload()};}	" +
"seajs.use(['lui/jquery','lui/topic'], function($ , topic) {"+
//审批等操作完成后，自动刷新列表
"topic.subscribe('successReloadPage', function() {"+
"	window.location.reload()"+
"	});});";
var script =  $("<script/>").attr({'type': 'text/javascript'});
script.appendTo(contentDiv);
$($.parseHTML(html)).appendTo(script);

var jspHtml='<div class="lui_dataview_classic_row clearfloat" style="padding-left:8px;line-height: 35px;font-weight:normal;color:#333;">'+
'<span id="lui_notify_todo_tip" class="lui_dataview_classic_icon" style=""></span>'+
'<div class="lui_dataview_classic_textArea clearfloat" style="padding-left: 44px;font-size:14px;">';

var jspHtml2='<span style="" class="lui_dataview_classic_link">';
			if(totalrows==0){
			jspHtml2=jspHtml2+'<span style="float:left;">'+SYSNOTIFY.homeYou+'<b style="color:#FF6600;margin-right:4px;">'+SYSNOTIFY.notHave+'</b></span>';
			}
			if(totalrows>0){
				jspHtml2=jspHtml2+'<span style="float:left;">'+SYSNOTIFY.youHave+'<b style="color:#FF6600" id="toViewCount">'+totalrows+'</b>';
				if(totalrows>1){
					jspHtml2=jspHtml2+SYSNOTIFY.homeCounts;
			}
				if(totalrows==11){
					jspHtml2=jspHtml2+SYSNOTIFY.homeCount;
				}
			jspHtml2=jspHtml2+'</span>';
			}
			
            var aLinkHref = "";
            aLinkHref+="javascript:(function(){";
            aLinkHref+="  seajs.use(['sys/notify/sys_notify_todo_ui/js/notifyLink.js'],function(notifyLink){";
            aLinkHref+="     var jpath = notifyLink.getJPath('"+dataType+"');";
            aLinkHref+="     var url = '"+SYSNOTIFY.LUI_ContextPath+"/sys/notify/index.jsp#j_path='+jpath;";
            aLinkHref+="     window.open(url);";
            aLinkHref+="  });";
            aLinkHref+="})();";
            
			jspHtml2=jspHtml2+'<a href="'+aLinkHref+'" target="_self" style="text-decoration: underline;font-size:14px;">';
			isShowEmail='true';
	if(finish==0 || finish==''){
		jspHtml2=jspHtml2+SYSNOTIFY.toDoInfo;
		infoTip='toDoInfo';
	}
	if(finish==1){
		jspHtml2=jspHtml2+SYSNOTIFY.label2;
		isShowEmail='false';
		infoTip='label2';				
	}
	jspHtml2=jspHtml2+'</a></span>';
	if(isShowEmail=='true'){
		
	}
	jspHtml2=jspHtml2+'<span style="float:left;cursor: pointer;"><a onclick="location.reload()"><img src="../../sys/notify/resource/images/refresh.png" alt=\''+SYSNOTIFY.refresh+'\'/></a></span></div>';
	if(totalrows==0){
		jspHtml2=jspHtml2+'<div class="lui-nodata-tips lui-nodata-tips-todo"><div class="imgbox"></div><div class="txt"><p>'+
		SYSNOTIFY.homeYou+'&nbsp;<font style="color:#FF6600;"><b>'+SYSNOTIFY.notHave+'</b></font>&nbsp;';
		if('toDoInfo'==infoTip){
			jspHtml2=jspHtml2+SYSNOTIFY.toDoInfo;
		}
		if('label2'==infoTip){
			jspHtml2=jspHtml2+SYSNOTIFY.label2;
		}
		
		jspHtml2=jspHtml2+'</p><p>'+SYSNOTIFY.noDataInfo+'</p></div></div>';
	}
	jspHtml2=jspHtml2+'</div>';
	jspHtml=jspHtml+jspHtml2;
$(jspHtml).appendTo(contentDiv);
return true;
}


done();