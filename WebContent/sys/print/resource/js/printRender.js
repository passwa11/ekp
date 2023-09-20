/**
 * 多个打印模板，选择弹窗的渲染
 */
var nodes = data;
var element = render.parent.element;
if(data == null || data.length == 0){
	done();
}else{
	if(nodes.length > 0) {
		//有数据
		var head = $('<div>打印模板名称</div>');
		head.css('width', '97%').css('font-weight', 'bold').css('padding', '8px 10px').css('border-bottom', '1px solid #ebdddd');
		head.appendTo(element);
		var ul = $('<ul>').attr('class', 'print_ul').appendTo(element);
		for(var i = 0; i < nodes.length; i++){
			var node = buildNode(nodes[i]);
			node.appendTo(ul);
		}
	} else {
		//无数据
		$('<div>很抱歉，未找到符合条件的打印模板！</div>').css('padding', '8px 10px').appendTo(element);
	}
}

function buildNode(data){
	var node = $('<li/>'),
	link = $('<a/>').appendTo(node);

	link.append($('<span/>').text(data.fdName));
	
	node.attr("data-print-id",data.fdId);
	
	node.on('dblclick',function(){
		ok();
	});
	
	node.on('click',function(){
		//移除导航选中状态
		LUI.$(".print_ul li").removeClass("print_li_selected");
		$(this).addClass('print_li_selected');
		window.selectId = $(this).attr("data-print-id");
	});
	return node;
}