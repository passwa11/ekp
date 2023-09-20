/**
 * 行业导航
 */
seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
	window.buildNode = function(data){
		var node = $('<li/>').attr("class","lui_block_grid_item itemStyle_1");
		node.attr("data-industryId",data.value);
		$head = $("<div class='nav_item_head'/>").appendTo(node);
		//更新样式
		var $block = $("<div class='nav_item_block'>").appendTo(node);
		var pageSize =data.pageSize?data.pageSize:"8";
		$block.append('<div class="nav_main"><div class="nav_main_title" title="' + data.name + '">'+data.name+'</div></div>');
		node.on('click',function(){
			$(element).find("li").removeClass("nav_main_selected");
			$(this).addClass("nav_main_selected");
			var url = data.url;
			url = url.replace("method=getDataList","method=getList");
			url += "&rowsize="+pageSize;
			url += "&version=" + window.parent.version;
			url += "&_=" + new Date().getTime();
			var modeListObj = LUI("modeList");
			var source = modeListObj.source;
			source.url = url;
			modeListObj.load();
		});
		return node;
	}
});
var element = render.parent.element;
$(element).html("");
if(data == null || data.length == 0){
	done();
}else{
	var ul = $('<ul>').attr('class', 'nav_operation_ul lui_profile_listview_card_page nofloat').appendTo(element);
	for(var i = 0; i < data.length; i++){
		//只展示八个！
		if (i == 7) {
			break;
		}
		var industryObj = data[i];
		var modeInfo = {"name":industryObj.text,"value":industryObj.fdId,"url":industryObj.url,"pageSize":industryObj.pageSize};
		var node = buildNode(modeInfo);
		//默认选中第一个
		if (i == 0) {
			$(node).addClass("nav_main_selected");
		}
		node.appendTo(ul);
	}
}