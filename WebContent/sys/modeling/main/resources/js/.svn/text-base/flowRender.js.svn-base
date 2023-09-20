/**
 * 
 */
var nodes = data.data;
var element = render.parent.element;
if(data && data.land){
	var flowName = data.land.flowName;
	var noRecord = data.land.noRecord;
}
if(data == null || data.length == 0){
	done();
}else{
	if(nodes.length > 0) {
		//有数据
		var head = $('<div>'+flowName+'</div>');
		head.css('width', '97%').css('font-weight', 'bold').css('padding', '8px 10px').css('border-bottom', '1px solid #ebdddd');
		head.appendTo(element);
		var ul = $('<ul>').attr('class', 'flow_ul').appendTo(element);
		for(var i = 0; i < nodes.length; i++){
			var node = buildNode(nodes[i]);
			node.appendTo(ul);
		}
	} else {
		//无数据
		$('<div>'+noRecord+'</div>').css('padding', '8px 10px').appendTo(element);
	}
}

function buildNode(data){
	var node = $('<li/>'),
	link = $('<a/>').appendTo(node);

	link.append($('<span/>').text(data.text));
	
	node.attr("data-flow-id",data.value);
	
	node.on('dblclick',function(){
		ok();
	});
	
	node.on('click',function(){
		//移除导航选中状态
		LUI.$(".flow_ul li").removeClass("flow_li_selected");
		$(this).addClass('flow_li_selected');
	});
	return node;
}