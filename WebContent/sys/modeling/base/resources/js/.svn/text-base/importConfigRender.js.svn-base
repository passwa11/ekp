/**
 <div class="model-record-item">
 <label><i></i><input type="radio" name="record">员工每日打卡状态</label> </div>
 */

var nodes = data.data;
var element = render.parent.element;
if(data == null || nodes.length == 0){
	$("<div class=\"model-record-item\" >"+langs['return.noRecord']+"</div>")
		.appendTo(element);
	done();
}else{
	/*
	<ul class="flow_ul"><li data-config-id="1715dfd379861d74f5d0a9a48c280ffa"><a><span>物资分类01（仅新增）</span></a></li></ul>
	 */
	//var ul = $('<ul>').attr('class', 'flow_ul').appendTo(element);
	for(var i = 0; i < nodes.length; i++){
		var node = buildNode(nodes[i]);
		node.appendTo(element);
	}
}

function buildNode(data){
	var node = $('<div/>').attr('class', 'model-record-item');
	var label = $('<label><i></i><input type="radio" name="record" value="'+data.id+'"></label> ')
	var sapn = $('<span></span>');
	sapn.text(data.name+"("+data.type+")");
	label.append(sapn);
	label.appendTo(node)
	// link = $('<a/>').appendTo(node);
	//
	// link.append($('<span/>').text(data.name+"（"+data.type+"）"));
	//
	 node.attr("data-config-id",data.id);

	node.on('dblclick',function(){
		ok();
	});
	//
	 node.on('click',function(){

	// 	//移除导航选中状态
		LUI.$(".model-record-item").removeClass("active");
		$(this).addClass('active');
	});
	if (selectId ===data.id ) {
		node.trigger($.Event("click"))
	}
	return node;
}