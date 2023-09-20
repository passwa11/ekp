//变量赋值
var dataView = render.parent;
var ellipsis = param.ellipsis == 'true' || render.vars.ellipsis=='true';
var target = render.vars.target?render.vars.target:'_blank';
var newDay = parseInt(render.vars.newDay);
if(isNaN(newDay)){
	newDay = 0;
}
//计算new日期
var showNewDate;
if(newDay>0){
	showNewDate = env.fn.parseDate(env.config.now);
	showNewDate.setTime(showNewDate.getTime()-newDay*24*60*60*1000);
}

if(data==null || data.length==0){
	done();
	return;
}
var contentDiv = $("<div/>").attr('class','lui_dataview_listdesc').appendTo(dataView.element);
for(var i=0; i<data.length; i++){
	addLiRow(data[i], i);
}
if(ellipsis){
	for(var i=0; i<data.length; i++){
		ellipsisText(data[i]);
	}
}

//添加一行
function addLiRow(oneData, no){
	var rowDiv = "";
	if(no==0){
		rowDiv = $('<div/>').attr('class','lui_dataview_listdesc_row clearfloat current').appendTo(contentDiv);
	}else{
		rowDiv = $('<div/>').attr('class','lui_dataview_listdesc_row clearfloat').appendTo(contentDiv);
	}
	
	var titleDiv = $('<div/>').attr('class','lui_dataview_listdesc_title clearfloat').appendTo(rowDiv);
	var descDiv = $('<div/>').attr('class','lui_dataview_listdesc_desc clearfloat').appendTo(rowDiv);
	
	rowDiv.mouseover(function(){
		$(this).siblings().removeClass("current");
		$(this).addClass("current");
		var desc = $(this).find('.summary').text();
		if(!desc){
			//$(this).find('.lui_dataview_listdesc_desc').removeClass("null").addClass("null");
		}
	});
	
	var linkBox, text;
	//标题
	linkBox = addLinkCell(titleDiv, oneData, 'text', 'href', '');
	text = oneData.text || '';
	if(ellipsis && text.length>3){
		//等全部显示完毕后在调整宽度，避免出现滚动条导致页面变窄
		linkBox.text('.');
		oneData.textBox = linkBox;
		oneData.textArea = titleDiv;
	}else{
		linkBox.text(text);
	}
	//New图标
	if(showNewIcon(oneData)){
		$('<span/>').attr('class', 'lui_dataview_classic_new')
				.appendTo(titleDiv);
	}
	//摘要
	var textDesc = $("<p />").attr('class','summary').appendTo(descDiv);
	textDesc.text(oneData['description']);
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
}
//添加一个带链接的格子
function addLinkCell(rowDiv, oneData, text, href, css){
	var textBox;
	if(oneData[href]){
		textBox = $('<a class="lui_dataview_listdesc_link" />').attr({
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
		textBox.text(text.substring(0, i)+'...');
	}
	oneData.textBox = null;
	oneData.textArea = null;
}

done();