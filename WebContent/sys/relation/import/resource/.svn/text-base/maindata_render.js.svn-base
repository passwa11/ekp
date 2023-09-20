//变量赋值
var dataView = render.parent;
var target = render.vars.target?render.vars.target:'_blank';

var containerDiv = $("<div/>").attr('class','lui_dataview_text').appendTo(dataView.element);
var contentDiv = $("<ul/>").appendTo(containerDiv);
for(var i=0; i<data.length; i++){
	addLiRow(data[i]);
}

if(data==null || data.length==0){
	done();
	return;
}

//添加一行
function addLiRow(oneData) {
	var rowDiv = $('<li/>').appendTo(contentDiv);
	// 分类, catename
	rowDiv.text(oneData['catename'] + ": ");
	
	var linkBox, text;
	//标题
	linkBox = addLinkCell(oneData, 'text', 'href', 'com_btn_link');
	linkBox.appendTo(rowDiv);
	text = oneData.text || '';
	linkBox.text(text);
}


//添加一个带链接的格子
function addLinkCell(oneData, text, href, css){
	var textBox;
	if(oneData[href]){
//		textBox = $('<a/>').attr({
//				'class': css,
//				'href':env.fn.formatUrl(oneData[href]),
//				'target':target
//			});
		textBox = $('<a/>').attr({
			'class': css,
			'href':'javascript:void(0)',
			'ajax-href':env.fn.formatUrl(oneData[href]).replace('method=show','method=cardInfo'),
			'onmouseover':'if(window.LUI && window.LUI.maindata) window.LUI.maindata(event,this);',
			
		});
	}
	textBox.attr('title', oneData[text]);
	return textBox;
};

done();