
function createItem(data,i,k){
	var colorIdx = 4*i + (k+1);
	colorIdx = colorIdx % 8;
	if(colorIdx==0){
		colorIdx = 8;
	}
	var container = $('<li />').addClass('bgColor_' + colorIdx);
	var a = $('<a target="_blank" />').attr('href',env.fn.formatUrl(data.viewUrl)).appendTo(container);
	$('<span class="lui_personal_iconBox"><i class="iconfont_nav ' + (data.icon || '') + '"></i></span>').appendTo(a);
	$('<h4 />').text('').appendTo(a);
	$('<h5 />').text('').appendTo(a);
	return container;
}

function countStat(element,url){
	$.ajax({
		url: env.fn.formatUrl(url),
		type: 'GET',
		dataType: 'json',
		error: function(err){
			element.hide();
			if(window.console)
				window.console.log(err);
		},
		success: function(data){
			if(data){
				if(data.length>0){
					var record = data[0];
					element.find('a h4').text(record.text);
					element.find('a h5').text(record.count);
				}
			}
		}
   });
}

function splitArray(arrays){
	var size = arrays.length;
	var pageSize = 4;
	var page = parseInt((size + (pageSize - 1)) / pageSize);
	var result = [];
	for (var i = 0; i < page; i++) {
		var temp = [];
		for (var j = i * pageSize; j <= pageSize * (i + 1)-1; j++) {
			if (j <= size - 1) {
				temp.push(arrays[j]);
			} else {
				break;
			}
		}
		result.push(temp);
	}
	return result;
}

var html = (function(){
	if(data && data.length>0){
		var box = $('<div class="lui_personal_statisCard_box" />');
		var arrays = splitArray(data);
		for(var i = 0 ; i < arrays.length;i++){
			var ulNode = $('<ul />');
			var records = arrays[i];
			for(var k = 0; k < records.length;k++){
				var item = createItem(records[k],i,k);
				countStat(item,records[k].url);
				item.appendTo(ulNode);
			}
			ulNode.appendTo(box);
		}
		
	}
	return box;
})();

done(html);
