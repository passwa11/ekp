var element = render.parent.element;
if(data == null || data.length == 0) {
	done();
}else{ 
	seajs.use(['lang!sys.ui:ui.tabPage'],function(lang){
		var conta
		
		var outer = $("<div/>").addClass("lui_nav_no_Expand_sideBar").appendTo(element),
		    inner = $("<div class='lui_navExpand_sideBar_inner'></div>").appendTo(outer);
		
		
		var ele = $("[data-lui-body-mark='lui_expand_body_content']");
		outer.removeClass("lui_exapandNav_collapse");
		
		var ul = $("<ul/>");
		ul.attr("style","margin-left: 32px");
		
		for(var i = 0; i < data.length; i ++) {
			var li  = $("<li/>"), item = data[i];
			
			$("<span class='nav_tips'>" + item.text + "</span>").appendTo(li);
			
			var anode = $("<a href='javascript:void(0)'></a>");
			li.append(anode);
			
			li.attr("data-href", item.href);
			li.attr("style","margin-bottom:5px");
			
			if(item.isClickSelected === false) {
				li.attr("data-isClickSelected", false);
			}
			
			if(item.selected === true) {
				li.addClass("lui_item_on com_bgcolor_d");
				li.attr("data-isClickSelected", true);
			}else{
				li.attr("data-isClickSelected", false);
			}
			
			anode.append("<span class='nav_iconBox'><i class=' "
								+ (item.icon ? item.icon : "lui_iconfont_navleft_learn_self iconfont_nav")
								+ "'></i></span><p class='nav_link'>" 
								+ item.text 
								+ "</p>");
			
			ul.append(li);
		}
		
		inner.append(ul);
		
		done();
		
		
		ul.on( "click", "li" , function(evt) {
			var $t = $(evt.currentTarget), href = $t.attr("data-href"), 
				isClickSelected = $t.attr("data-isClickSelected");
			if(href) {
				if(isClickSelected !== "false") {
					ul.find("li").removeClass("lui_item_on com_bgcolor_d");
					$t.addClass("lui_item_on com_bgcolor_d");
				}
				if(href.toLowerCase().indexOf("javascript:") > -1){
					location.href = href;
				}else{
					LUI.pageOpen(href, '_blank');
				}
			}
		});
		ul.on( "mouseover", "li" , function(evt) {
			var $t = $(evt.currentTarget);
			var isClickSelected = $t.attr("data-isClickSelected");
			if(isClickSelected !== "true") {
				$t.addClass("com_bgcolor_d");
			}
		});
		ul.on( "mouseout", "li" , function(evt) {
			var $t = $(evt.currentTarget);
			var isClickSelected = $t.attr("data-isClickSelected");
			if(isClickSelected !== "true") {
				$t.removeClass("com_bgcolor_d");
			}
		});
	})
}