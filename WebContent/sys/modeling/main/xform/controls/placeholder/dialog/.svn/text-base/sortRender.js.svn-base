/**
 * 
 */
var element = render.parent.element;
$(element).html("");
if(data.list == null || data.list.length == 0){
	done();
}else{
	if(data.list && data.list.length && data.list.length > 0){
		var $lui_list_operation_order_btn = $("<div class='lui_list_operation' />").appendTo(element);

		var $lui_list_operation_order_text = $("<div class= 'lui_list_operation_order_text' style='float: left;margin-right: 10px' >"+data.land.order+"</div>");
		$lui_list_operation_order_btn.append($lui_list_operation_order_text);
		var $lui_toolbar = $("<div class='lui_toolbar_btn_r' style='float: left'/>");
		$lui_list_operation_order_btn.append($lui_toolbar);
		var isHide = data.list.length > 5;
		if(isHide){
			//更多排序
			var sortInfo = data.list[0];
			$lui_toolbar.append(buildSortNode(sortInfo,true,true));
			$lui_toolbar.append(buildMoreSortButton());
			var $moreSortSelect =buildMoreSortSelect(data.list);
			$moreSortSelect.css("display","none");
			$lui_toolbar.append($moreSortSelect);
		}else{
			for(var i = 0;i < data.list.length;i++){
				var sortInfo = data.list[i];
				$lui_toolbar.append(buildSortNode(sortInfo,true,false));
			}
		}

	}
}

function buildSortNode(sortInfo,isShow,isMore){
	var expression = sortInfo.expression;
	var nameInfo = sortInfo.name;
	var orderType = expression.value;
	var text = nameInfo.text;
	var value = nameInfo.value;
	if(isShow){
		var $nodeDiv = $("<div class='lui_toolbar_btn_c isShow' style='padding: 0 5px;float:left;display: inline-block'/>");
	}else{
		if(isMore){
			var $nodeDiv = $("<div class='lui_toolbar_btn_c' style='padding: 0 5px;display: inline-block'/>");
		}else{
			var $nodeDiv = $("<div class='lui_toolbar_btn_c' style='padding: 0 5px;float:left;display: inline-block'/>");
		}
	}

	var $nameDiv =  $("<div class='lui-component lui_widget_btn_txt' orderby='"+value+"' title='"+text+"'>"+text+"</div>");
	if(isShow){
		var $iconDiv =  $("<div class='lui_widget_btn_icon' style='display: inline-block;'/>");
	}else{
		var $iconDiv =  $("<div class='lui_widget_btn_icon' style='display: none'/>");
	}

	var $icon =  $("<div class='lui_icon_s lui_icon_s_default_filter' style='width: 16px;height: 16px;position: relative;'/>");
	if(orderType == "asc"){
		$icon.removeClass("lui_icon_s_default_filter");
		$icon.addClass("lui_icon_s_up_filter");
	}else if(orderType == "desc"){
		$icon.removeClass("lui_icon_s_default_filter");
		$icon.addClass("lui_icon_s_on_filter");
	}
	$iconDiv.append($icon);
	$nodeDiv.append($nameDiv);
	$nodeDiv.append($iconDiv);
	$nodeDiv.on("click", function(){
		if(isMore && !$(this).hasClass("isShow")){
			var $isShow = element.find(".lui_toolbar_btn_r").find(".isShow");
			$isShow.removeClass("isShow");
			$isShow.find(".lui_widget_btn_icon").css("display","none");
			var $selectDivItem = $("<div class='selectDivItem'/>");
			$isShow.css("float","none");
			$selectDivItem.append($isShow);
			element.find(".moreSortSelect").append($selectDivItem);

			$(this).addClass("isShow");
			$(this).css("float","left");
			$(this).find(".lui_widget_btn_icon").css("display","inline-block")
			var $parentSelectDivItem = $(this).parent(".selectDivItem");
			element.find(".lui_toolbar_btn_r").prepend($(this));
			$parentSelectDivItem.remove();
			hideMoreSortSelect();
		}
		var icon = $(this).find(".lui_icon_s");
		if(icon.hasClass("lui_icon_s_default_filter")){
			element.find(".lui_icon_s").removeClass("lui_icon_s_up_filter");
			element.find(".lui_icon_s").removeClass("lui_icon_s_on_filter");
			element.find(".lui_icon_s").addClass("lui_icon_s_default_filter");
			icon.removeClass("lui_icon_s_default_filter");
			icon.addClass("lui_icon_s_up_filter");
		}else if(icon.hasClass("lui_icon_s_up_filter")){
			element.find(".lui_icon_s").removeClass("lui_icon_s_up_filter");
			element.find(".lui_icon_s").removeClass("lui_icon_s_on_filter");
			element.find(".lui_icon_s").addClass("lui_icon_s_default_filter");
			icon.removeClass("lui_icon_s_default_filter");
			icon.addClass("lui_icon_s_on_filter");
		}else if(icon.hasClass("lui_icon_s_on_filter")){
			element.find(".lui_icon_s").removeClass("lui_icon_s_up_filter");
			element.find(".lui_icon_s").removeClass("lui_icon_s_on_filter");
			element.find(".lui_icon_s").addClass("lui_icon_s_default_filter");
		}

		doSort();
	});
	return $nodeDiv;
}

function buildMoreSortButton(){
	var $lui_MoreSort_right =  $(" <div class= 'lui_MoreSort_right lui_MoreSort_right_up' style='float:left;cursor:pointer;line-height:40px;padding-right:20px;'><span>"+data.land.moreOrder+"</span></div>");
	$lui_MoreSort_right.on("mouseover",function(){
		$(this).removeClass("lui_MoreSort_right_up");
		$(this).addClass("lui_MoreSort_right_down");
		showMoreSortSelect($(this));
	});
	$lui_MoreSort_right.on("mouseout",function(){
		$(this).removeClass("lui_MoreSort_right_down");
		$(this).addClass("lui_MoreSort_right_up");
		hideMoreSortSelect($(this));
	});
	$lui_MoreSort_right.on("mouseleave",function(){
		$(this).removeClass("lui_MoreSort_right_down");
		$(this).addClass("lui_MoreSort_right_up");
		hideMoreSortSelect($(this));
	});

	return $lui_MoreSort_right;
}

function buildMoreSortSelect(listInfo){
	var $selectDiv = $("<div class='moreSortSelect'/>")
	for (var i = 1; i < listInfo.length; i++) {
		var sortInfo = listInfo[i];
		var $selectDivItem = $("<div class='selectDivItem'/>")
		var $sortNode = buildSortNode(sortInfo,false,true);
		$selectDivItem.append($sortNode);
		$selectDiv.append($selectDivItem);
	}
	$selectDiv.css({
		"border": "1px solid #d5d5d5",
		"border-bottom-left-radius": "4px",
		"border-bottom-right-radius": "4px",
		"position": "absolute",
		"background":"#FFFFFF",
		"width":"150px"
	})

	$selectDiv.on("mouseover",function(){
		element.find(".lui_MoreSort_right").removeClass("lui_MoreSort_right_up");
		element.find(".lui_MoreSort_right").addClass("lui_MoreSort_right_down");
		element.find(".moreSortSelect").css({"display":"block"});
	});
	$selectDiv.on("mouseout",function(){
		element.find(".lui_MoreSort_right").removeClass("lui_MoreSort_right_down");
		element.find(".lui_MoreSort_right").addClass("lui_MoreSort_right_up");
		hideMoreSortSelect();
	});
	$selectDiv.on("mouseleave",function(){
		element.find(".lui_MoreSort_right").removeClass("lui_MoreSort_right_down");
		element.find(".lui_MoreSort_right").addClass("lui_MoreSort_right_up");
		hideMoreSortSelect();
	});
	return $selectDiv;
}

function showMoreSortSelect($moreSortButton) {
	var top = $moreSortButton.offset().top;
	var left =$moreSortButton.offset().left;
	element.find(".moreSortSelect").css({"display":"block","top":top+40,"left":left,"padding-top":"1px"});
}

function hideMoreSortSelect() {
	element.find(".moreSortSelect").css({"display":"none"});
}

function doSort(){
	render.parent.doSort();
}
