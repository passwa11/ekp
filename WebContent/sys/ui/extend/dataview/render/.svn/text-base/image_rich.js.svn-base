
var personImgUrl = '/sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId=';
var personZoneUrl = '/sys/zone/index.do?userid=';
var columnNum = render.vars.columnNum ? render.vars.columnNum : '4';

function initDom(data) {
	var table = buildTab(data);
	return table;
}

function buildTab(data) {
	var table = $('<table class="image_rich_portlet_table">');
	var width = 100/columnNum;
	var rowNum ;
	if(data.length > columnNum){
		rowNum = parseInt(data.length/columnNum) +1;
	}else{
		rowNum = 1;
	}
	
	width += '%"';
	
	for(var k = 1; k < rowNum + 1 ; k++){
		var tr = $('<tr rowNum="' + k + '">');
		var blankTdNum = 0;
		var blankTdFlag = false;
		if(data.length < columnNum){
			blankTdNum = columnNum - data.length;
		}else if(data.length > columnNum){
			blankTdNum = data.length - columnNum ;
		}
		for (var i = 0; i < columnNum ; i++) {
			var tempNum = k*columnNum-columnNum + i;
			if(tempNum == data.length ){
				blankTdFlag = true;
				break;
			}
			var td = $('<td data-lui-mark-id="' + data[tempNum].fdId + '" width="' + width + '>');
			
			var div = $('<div class="image_rich_portlet_div">');
			
			var first = $('<div class="first"></div>');
			var first_a = $('<a class="image_rich_portlet_img_url" href="' + env.fn.formatUrl(data[tempNum].href) + '" target="view_window" onclick="Com_OpenNewWindow(this);"></a>');
			var first_img = $('<img src="' + env.fn.formatUrl(data[tempNum].image) +'">');
			
			var second = $('<div class="second">');
			var second_a = $('<a class="image_rich_portlet_subject_url" href="' + env.fn.formatUrl(data[tempNum].href) + '" target="view_window" onclick="Com_OpenNewWindow(this);" title="' + data[tempNum].text +'">' + data[tempNum].text +'</a>');
			
			var third  = $('<div class="image_rich_third">');
			var third_left = $('<div class="image_rich_portlet_left">');
			var third_right = $('<div class="image_rich_portlet_right">');
			var third_left_span1 = $('<span class="image_rich_portlet_avator_img">');
			var third_left_span1_img = $('<img src="' + env.fn.formatUrl(personImgUrl+data[tempNum].personId) + '">');
			var third_left_span2 = $('<span class="image_rich_portlet_avator_name">');
			
			var third_left_span2_a;
			if(data[tempNum].personId){
				third_left_span2_a = $('<a class="com_author" target="_blank" href="' + env.fn.formatUrl(personZoneUrl+data[tempNum].personId) + '" onclick="Com_OpenNewWindow(this);">'+ data[tempNum].personName + '</a>');
			}else{
				third_left_span2_a = $('<a class="com_author" target="_blank">'+ data[tempNum].personName + '</a>');
			}
			
			var third_right_div = $('<div class="image_rich_portlet_category" title="' + data[tempNum].catename + '">' + data[tempNum].catename +'</div>');
			
			var fourth = $('<div class="fourth">' + data[tempNum].created + '</div>');
			
			first_a.append(first_img);
			first.append(first_a);
			
			second.append(second_a);
			
			third_left_span1.append(third_left_span1_img);
			third_left_span2.append(third_left_span2_a);
			third_left.append(third_left_span1);
			third_left.append(third_left_span2);
			third_right.append(third_right_div);
			third.append(third_left);
			third.append(third_right);
			
			div.append(second);
			div.append(third);
			div.append(fourth);
			
			td.append(first);
			td.append(div);
			tr.append(td);
			
		}
		
		table.append(tr);
	}
	
	
	return table;
}

var html = (function(e) {
	if (data && data.length > 0) {
		return initDom(data);
	}
})();

done(html);