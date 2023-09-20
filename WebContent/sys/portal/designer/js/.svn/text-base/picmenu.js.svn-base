var element = render.parent.element;
if(data==null || data.length==0){
	done();
	return;
}

var cssExtend = "";			//CSS后缀
var iconChange = false;		//图标无切换效果
var showMore = false;		//不显示更多菜单
var target = "_blank";		//目标帧
var width  = element.parent().width();	//容器宽度
var height = element.parent().height();	//容器高度
var cellWidth = 0;			//单个格子宽度，含外框
var cellHeight = 0;			//单个格子高度，含外框
var space = 0;				//格子间隔/2
var endSpace = 0;			//右边空余的像素
var showCount = 0;			//能显示的格子个数
var intervalWidth = 0;
var intervalHeight = 0;
if(param.extend!=null){
	cssExtend = "_" + param.extend;
}
iconChange = (param.iconChange==true || param.iconChange=='true');
showMore = (param.showMore==true || param.showMore=='true');
if(render.vars!=null){
	if(render.vars.showMore!=null){
		showMore = (render.vars.showMore=="true");
	}
	if(render.vars.target!=null){
		target = render.vars.target=='auto'?null:render.vars.target;
	}
}
element.html('');
var picMenuListDiv = $('<div>').attr('class', 'lui_dataview_picmenulist'+cssExtend).appendTo(element);
buildFirstMenu();
buildNextMenu();
buildMoreMenu();
done();

//构造一个菜单
function buildMenu(oneData, isMore, isFirst){
	var text = env.fn.formatText(oneData.text);
	var topDivClass = 'lui_dataview_picmenu' + (oneData.selected?'_selected_table':(isMore?'_more_table':'_table'));
	var topDiv = $("<div/>").attr({'class':topDivClass, 'title':oneData.text});
	if(isMore)
		topDiv.attr('data-lui-switch-class',"lui_dataview_picmenu_more_on_table");
	if(!isFirst){
		topDiv.css({'border':'1px solid #e5e5e5'});
		topDiv.css({'padding':'0px'});
		topDiv.css({'margin-right':'-1px'});
		topDiv.css({'margin-bottom':'-1px'});
	}
	var bodyDiv = $("<div/>").attr({'class':'lui_dataview_picmenu_left_table'}).appendTo(topDiv);
	var leftDiv=bodyDiv;
	bodyDiv = $("<div/>").attr({'class':'lui_dataview_picmenu_right_table'}).appendTo(bodyDiv);
	bodyDiv = $("<div/>").attr({'class':'lui_dataview_picmenu_content_table'}).appendTo(bodyDiv);

	var iconDiv = $('<div>').attr('class','lui_icon_l')
			.appendTo(bodyDiv);
	console.log(oneData);
	if(oneData.icon) {
		$('<div>').attr('class', 'lui_icon_l ' + oneData.icon)
			.appendTo(iconDiv);
	}else if(oneData.img){
		var tUrl = oneData.img;
		if(tUrl.indexOf("/") == 0){
			tUrl = tUrl.substring(1);
		}
		var url = Com_Parameter.ContextPath+tUrl;
		var imgDiv = $('<div>').attr('class', 'lui_icon_l ')
			.appendTo(iconDiv);
		$('<img>').attr('src', url).attr('width','100%;')
			.appendTo(imgDiv);

	}

	var textDiv = $('<div>').attr('class', 'textEllipsis lui_dataview_picmenu_txt_table')
			.appendTo(bodyDiv);
	
	seajs.use(['lui/util/str'],function(strutil){
		textDiv.text(strutil.decodeHTML(oneData.text));
		topDiv.attr('title',strutil.decodeHTML(oneData.text));
	});
	topDiv.mouseover(function(){
		if(iconChange){
			iconDiv.addClass('lui_icon_on');
		}
		if(!isMore)
			topDiv.attr('class', topDivClass + '_on');
	});
	topDiv.mouseout(function(){
		if(iconChange){
			iconDiv.removeClass('lui_icon_on');
		}
		if(!isMore)
			topDiv.attr('class',topDivClass);
	});
	if(oneData.href!=null){
		topDiv.click(function(){
			if(oneData.href.toLowerCase().indexOf("javascript:")>-1){
				location.href = oneData.href;
			}else{
				window.open(env.fn.formatUrl(oneData.href), target || oneData.target || '_blank');
			}
			
		});
	}
	
	bodyDiv.css({'paddingTop':'12px'});
	return topDiv;
}

//构造第一个菜单，同时进行计算
function buildFirstMenu(){
	var firstMenu = buildMenu(data[0],false, true).appendTo(picMenuListDiv);
	firstMenu.css({'border':'1px solid #e5e5e5'});
	firstMenu.css({'padding':'0px'});
	firstMenu.css({'margin-right':'-1px'});
	firstMenu.css({'margin-bottom':'-1px'});
	cellWidth = firstMenu.outerWidth(true);
	cellHeight = firstMenu.outerHeight(true);
	var cols = Math.floor(width/cellWidth);
	if(cols <= 1){
		cols = 2;	//最少显示两个
	}
	space = Math.floor((width - firstMenu.width()*cols)/(cols*2));
	if(space<2){
		space = 2;
	}
	cellWidth = firstMenu.width() + space * 2;
	endSpace = width - cellWidth * cols;
	if(endSpace<0){
		endSpace = 0;
	}
	if(showMore){
		var rows = Math.floor(height/cellHeight);
		if(rows <= 0){
			rows = 1;
		}
		showCount = cols * rows;
		if(showCount >= data.length){
			showCount = data.length;
			showMore = false;
		}else{
			showCount--;
		}
	}else{
		showCount = data.length;
	}
	
	cellWidth = firstMenu.outerWidth(true);
	cellHeight = firstMenu.outerHeight(true);
	cols = Math.floor(width/cellWidth);
	rows = Math.floor(height/cellHeight);
	intervalWidth=Math.floor((width-cellWidth*cols)/2);
	intervalHeight=Math.floor((height-cellHeight*rows)/2);
	picMenuListDiv.css({'paddingLeft':intervalWidth,'paddingRight':intervalWidth});
	picMenuListDiv.css({'paddingTop':intervalHeight,'paddingBottom':intervalHeight});
	return firstMenu;
}

//构造其它菜单
function buildNextMenu(){
	for(var i=1; i<showCount; i++){
		buildMenu(data[i], false, false)
				.appendTo(picMenuListDiv);
	}
}

//构造更多操作
function buildMoreMenu(){
	if(!showMore)
		return;
	var bgcolor = "white";
	var popDiv = $('<div>').attr('class', 'lui_dataview_picmenu_popup'+cssExtend+"_table");
	var menusDiv = $('<div>').attr('class', 'lui_dataview_picmenu_menus_table');
	popDiv.css({"width": width,"background":bgcolor});
	var cellWidth=0;
	for(var i=showCount; i<data.length; i++){
		buildMenu(data[i],false,false).appendTo(menusDiv);
	}
	
	menusDiv.css({'paddingLeft':intervalWidth,'paddingRight':intervalWidth});
	seajs.use(['lang!','lang!sys-portal','sys/ui/js/popup'],function(lang,portalLang,popup){
		var moreMenu = buildMenu({"icon":" lui_dataview_picmenu_icon","text":lang['operation.more']}, true, false);
		popDiv.append(menusDiv);
		picMenuListDiv.append(moreMenu);
		picMenuListDiv.append(popDiv);
		var pp = popup.build(moreMenu,popDiv,{"align":"down-right"});
		render.parent.onErase(function(){pp.destroy();});
	});
}
