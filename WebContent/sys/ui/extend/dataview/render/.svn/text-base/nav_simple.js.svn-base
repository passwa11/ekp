var element = render.parent.element;
if(data == null || data.length == 0){
	done();
}else{
	var ul = $('<ul>').attr('class', 'lui_list_nav_list').appendTo(element);
	for(var i = 0; i < data.length; i++){
		var node = buildNode(data[i]);
		node.appendTo(ul);
	}
	//监听清除组件内部选中项事件
	seajs.use(['lui/topic'],function(topic){
		topic.subscribe('nav.operation.clearStatus', function(evt) {
			$(".lui_list_nav_list > li",render.parent.element).removeClass("lui_list_nav_selected");
		});
	});
}
function buildNode(data){
	var node = $('<li/>'),
		link = $('<a/>').appendTo(node);
	if(data.icon){
		link.append($('<i/>').addClass('iconfont').addClass(data.icon));
	}
	link.append($('<span/>').text(data.text).attr("title", data.text));
	
	if(data.router){
		seajs.use(['lui/framework/router/router-utils'],function(routerUtils){
			if(routerUtils.equalPath(data.href) 
					|| routerUtils.isParentPath(data.href)){
				$(node).addClass('lui_list_nav_selected');
			}
		});
	}
	
	node.attr("data-path",data.href);
	
	node.on('click',function(){
		//移除导航选中状态
		LUI.$("[data-lui-type*=AccordionPanel] li").removeClass("lui_list_nav_selected");
		//移除导航头部选中状态
		seajs.use(['lui/topic'],function(topic){
			topic.publish("nav.operation.clearStatus", null);
		});
		$(this).addClass('lui_list_nav_selected');
		if(data.router){
			seajs.use(['lui/framework/router/router-utils'],
					function(routerUtils){
				var $router = routerUtils.getRouter();
				$router.push(data.href, data.params || {});
			});
		}else{
			if(data.href.toLowerCase().indexOf("javascript:") > -1){
				location.href = data.href;
			}else{
				LUI.pageOpen(data.href, data.target ||'_blank');
			}
		}
	});
	return node;
}
