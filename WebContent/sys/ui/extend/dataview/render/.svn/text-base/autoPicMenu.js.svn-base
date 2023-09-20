

// 计算多行快捷方式每行的高度
var cacuHeight = function(){
	
  // 父容器高度获取
  var element = render.parent.element;
  var rowHeight = element.parent().height();
  
  
  
  // 快捷方式数量获取
  var rowAmount = $(".nw-row .n-work-box");
  // 将获取到的数组转化为数字
  var rowAmountNum = $(".nw-row .n-work-box").length;
  // 高度除以快捷方式数量，得到每个快捷方式的高度百分比
  var rowItemHeight = rowHeight/rowAmountNum;
  // 设置最小高度80
  if(rowItemHeight < 80){
    rowItemHeight = 80
  }
  rowAmount.height(rowItemHeight);
}


//构造一个菜单
var buildMenu= (function (){
	if(data && data.length>0){
		
		var target = "_blank";	//目标帧

		if(render.vars!=null){
			if(render.vars.target!=null){
				target = render.vars.target=='auto'?null:render.vars.target;
			}
		}
		
		var picMenuListDiv =$('<div>').attr('class', 'n-work'+ ' nw-row');
		var workWrapDiv= $('<div/>').attr('class', 'n-work-wrap');
		workWrapDiv.appendTo(picMenuListDiv);
		
		 
		 for(var i=0;i<data.length;i++){

			 var workBoxItem= $('<div/>').attr('class', 'n-work-box');
			 workBoxItem.appendTo(workWrapDiv);
			 var liItem;
			 if(data[i].img){
				 var url = data[i].img;
				 if(url.indexOf("/") == 0){
					 url = url.substring(1);
				 }
				 liItem = $("<a class='n-work-item' href='" + env.fn.formatUrl(data[i].href) + "' target='" + (target || data[i].target || "_blank") + "'> " +
					 "<div class='n-work-item-wrap'> " +
					 "<div class='n-work-item-left'><i class='lui_icon_l' style='background:url("+Com_Parameter.ContextPath+url+") no-repeat center;background-size: contain'></i></div> " +
					 "<div class='n-work-item-right'> " +
					 "<p class='nwir-desc'>" + data[i].text + "</p>" +
					 "</div>" +
					 "</div>" +
					 "</a>");
			 }else {
				  liItem = $("<a class='n-work-item' href='" + env.fn.formatUrl(data[i].href) + "' target='" + (target || data[i].target || "_blank") + "'> " +
					 "<div class='n-work-item-wrap'> " +
					 "<div class='n-work-item-left'><i class='lui_icon_l " + data[i].icon + "'></i></div> " +
					 "<div class='n-work-item-right'> " +
					 "<p class='nwir-desc'>" + data[i].text + "</p>" +
					 "</div>" +
					 "</div>" +
					 "</a>");
			 }
			 
			 liItem.appendTo(workBoxItem);
		 }
		 
		 return picMenuListDiv;
	}
	
})();

done(buildMenu);
cacuHeight();
