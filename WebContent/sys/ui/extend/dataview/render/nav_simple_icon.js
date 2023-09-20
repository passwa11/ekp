var element = render.parent.element;
if(data == null || data.length == 0){
	done();
}else{
	for(var i = 0; i < data.length; i++){
		var node = buildNode(data[i]);
		node.appendTo(element);
	}
	
	//监听清除组件内部选中项事件
//	seajs.use(['lui/topic'],function(topic){
//		topic.subscribe('nav.operation.clearStatus', function(evt) {
//			$(".lui_list_nav_list > li",render.parent.element).removeClass("lui_list_nav_selected");
//		});
//	});
}
function buildNode(data){
	//默认选择首页
	if(data.isHome){
		var node = $('<li />').addClass('lui_loperation_nav_item lui_loperation_nav_item_selected');
	}else{
		var node = $('<li />').addClass('lui_loperation_nav_item');
	}
	var	link = $('<a/>').appendTo(node);
		span = $('<span/>').addClass('nav_icon_box').appendTo(link);
		
	if(data.icon){
		span.append($('<i/>').addClass('iconfont_nav').addClass(data.icon));
	}
	if(data.text){
		span.append($('<span class="nav_tips" />').text(data.text));
		link.append($('<p class="nav_link" />').text(data.text));
	}
	
	node.click(function(){
		if(node.hasClass('lui_loperation_nav_item_selected')){ //已经被选中，不做处理

		}else{ //被选中
			$('.lui_loperation_nav_item_selected').removeClass('lui_loperation_nav_item_selected');//删除之前的选中
			var href = data.href;
			if(href){
				var symbol = "?";
				if (href.indexOf('?') > 0) {
					symbol = "&";
				}
				LUI.pageOpen( href+symbol+'j_iframe=true&j_aside=false&j_notop=true',
				'_rIframe');  //j_iframe=true&j_aside=false&j_notop=true 为不要页眉不要左侧导航栏,也不要右下端赞赏等按钮入口
			}
			node.addClass('lui_loperation_nav_item_selected');
		}
		
		
		
	})
	
	
	


	
	


	return node;
}
