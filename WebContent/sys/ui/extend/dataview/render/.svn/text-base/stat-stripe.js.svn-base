 // 计算数据统计卡片的容器宽度，并决定一行显示几个卡片
  var cacuWidth = function(){
    // 数据统计卡片自适应初始化（行）
    var sWidth = $(".nw-statistic").width();
    var sElement = $(".nw-statistic").find(".n-work-box");
    // 宽度以100,800,600为界限，对应一行显示不同数量的卡片
    if(sWidth > 1000){
      sElement.css("width","20%");
    }else if(sWidth < 1000 && sWidth>800){
      sElement.css("width","25%");
    }else if(sWidth>600 && sWidth<800){
      sElement.css("width","33.3%");
    }else if(sWidth < 600){
      sElement.css("width","50%");
    }
  }

  // 动态监听容器宽度
  $(window).resize(function(){
    cacuWidth();
  })

function createItem(data,i,k){

	var container = $('<div />').attr('class','n-work-box');
	
	var aworkitem = $('<a class="n-work-item" target="_blank" />').attr('href',env.fn.formatUrl(data.viewUrl)).appendTo(container);
	
	var divWorkItemWrap= $('<div class="n-work-item-wrap" />');
	divWorkItemWrap.appendTo(aworkitem);
	
	var divWorkItemLeft= $('<div class="n-work-item-left" />');
	divWorkItemLeft.appendTo(divWorkItemWrap);
	
	$('<i class="iconfont iconfont_nav ' + (data.icon || '') + '"></i>').appendTo(divWorkItemLeft);
	
	var divWorkItemRight= $('<div class="n-work-item-right" />');
	divWorkItemRight.appendTo(divWorkItemWrap);
	
	$('<p class="nwir-num" />').appendTo(divWorkItemRight);
	$('<p class="snwir-desc" />').appendTo(divWorkItemRight);
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
					element.find('.n-work-item-right .snwir-desc').html((typeof(record.text)=="undefined")? "":record.text);
			        element.find('.n-work-item-right .nwir-num').html((typeof(record.count)=="undefined")? "0":record.count);
				}
			}
		}
   });
}

function splitArray(arrays){
	var size = arrays.length;
	var pageSize = 50;
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
		var work = $('<div class="n-work nw-statistic" />');
		var box = $('<div class="n-work-wrap" />');
		box.appendTo(work);
		
		
		var arrays = splitArray(data);
		for(var i = 0 ; i < arrays.length;i++){
			
			var records = arrays[i];
			for(var k = 0; k < records.length;k++){
				var item = createItem(records[k],i,k);
				countStat(item,records[k].url);
				item.appendTo(box);
			}
		}
		return work;
	}
	
})();



done(html);
cacuWidth();