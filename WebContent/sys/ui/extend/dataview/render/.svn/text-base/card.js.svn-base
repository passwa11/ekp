//变量赋值
var dataView = render.parent;
var extend = param.extend?('_'+param.extend):'';
var ellipsis = param.ellipsis == 'true' || render.vars.ellipsis=='true';
var target = render.vars.target?render.vars.target:'_blank';
var lang = Com_Parameter.Lang;
var dateFormat = Com_Parameter.Date_format;
if(render.preDrawing){            //预加载执行
	return;
}
if(data==null || data.length==0){
	done();
	return;
}

var contentDiv = $("<ul/>").attr('class', 'lui-timebase-list').appendTo(dataView.element);
for(var i=0; i<data.length; i++){
	addRow(data[i], i==0);
}

//添加一行
function addRow(oneData, isFirst){
	var rowDiv = $('<li/>').appendTo(contentDiv);
	//日期
	var vdate = oneData.created;
	if(oneData.ocreated) {
		vdate = oneData.ocreated;
	} 
	var createDate = env.fn.parseDate(vdate);
	var dateArea = $('<div/>').attr('class','list-date').appendTo(rowDiv);
	$('<div/>').attr('class','day').text(createDate.getDate()).appendTo(dateArea);
	$('<div/>').attr('class','date').text(getDateRender(createDate)).appendTo(dateArea);
	//内容
	var contentArea = $('<h2/>').attr('class','list-title').appendTo(rowDiv);
	
	var linkNode = $('<a/>').attr('class','date').appendTo(contentArea);
	if(oneData['href']){
		linkNode.attr({
			'data-href':env.fn.formatUrl(oneData['href']),
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
	var text = oneData.text || '';
	linkNode.attr('title', text).text(text);
	var descArea = $('<p/>').attr('class','list-depict').appendTo(rowDiv);
	descArea.text(oneData['description'] || '');
	
	var footArea = $('<div/>').attr('class','list-assist').appendTo(rowDiv);
	$('<span/>').text(oneData['creator'] || '').appendTo(footArea);
	if ('fdTotalCount' == oneData['fdReadcountType']){
		$('<span/>').text((oneData['fdTotalCount'] ? oneData['fdTotalCount']:'0')).attr('class','lui_dataview_classicrich_readcount').appendTo(footArea);
	} else {
		$('<span/>').text((oneData['docreadcount'] ? oneData['docreadcount']:'0')).attr('class','lui_dataview_classicrich_readcount').appendTo(footArea);
	}
}
function getDateRender(date){
	if(lang == 'zh-cn'){
		var template ="yyyy年MM月";
		template = template.replace("yyyy",date.getFullYear())
						   .replace("MM",date.getMonth()+1);
		return template;
	}else {
		return env.fn.formatDate(date,dateFormat);
	}	
}
done();