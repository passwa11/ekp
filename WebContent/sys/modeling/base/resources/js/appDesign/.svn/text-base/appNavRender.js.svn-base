/**
 * 
 */
var element = render.parent.element;
if(data == null || data.length == 0){
	done();
}else{
	var ul = $('<ul>').attr('class', 'aside_ul').appendTo(element);
	for(var i = 0; i < data.length; i++){
		var node = buildNode(data[i]);
		node.appendTo(ul);
	}
}

function buildNode(data){
	var node = $('<li class="model-sidebar-item"/>');
	node.attr("title",data.text);
	var itemBox = $('<div class="model-sidebar-item-box" />');
	itemBox.append('<i class="icon iconfont_modeling '+data.icon+'"></i>')
	$('<span class="model-sidebar-desc"/>').text(data.text).appendTo(itemBox);
	itemBox.appendTo(node);
	/*seajs.use(['lui/framework/router/router-utils'],function(routerUtils){
		if(routerUtils.equalPath(data.href) 
				|| routerUtils.isParentPath(data.href)){
			$(node).addClass('aside_selected');
		}
	});*/
	// 原有的路由逻辑判断感觉有问题，自己处理
	seajs.use(['lui/framework/router/router-utils'],function(routerUtils){
		var router = routerUtils.getRouter(true);
		var curPath = router._getHashPath();
		if(curPath){
			if(curPath === data.href){
				$(node).addClass('aside_selected');
			}
		}else{
			if(data.href === "/form"){
				$(node).addClass('aside_selected');
			}
		}
	});
	
	node.on('click',function(){
		//移除导航选中状态
		LUI.$(".aside_ul li").removeClass("aside_selected");
		$(this).addClass('aside_selected');
		seajs.use(['lui/framework/router/router-utils'],function(routerUtils){
			var $router = routerUtils.getRouter();
			$router.push(data.href, data.params || {});
		});
	});
	return node;
}
