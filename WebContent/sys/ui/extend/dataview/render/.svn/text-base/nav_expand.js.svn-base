
var element = render.parent.element;
if(data == null || data.length == 0) {
	done();
}else{ 
	seajs.use(['lang!sys.ui:ui.tabPage'],function(lang){
		var conta
		
		var outer = $("<div/>").addClass("lui_navExpand_sideBar lui_exapandNav_collapse").appendTo(element),
		    inner = $("<div class='lui_navExpand_sideBar_inner'></div>").appendTo(outer)
		    opt = $("<div class='lui_sideBar_opt'><span class='nav_iconBox'><i class='statuc_icon'></i></span><p class='nav_link'>"+lang["ui.tabPage.collapsed"]+"</p></div>");
		
		inner.append(opt);
		
		var ul = $("<ul/>");
		
		for(var i = 0; i < data.length; i ++) {
			var li  = $("<li/>"), item = data[i];
			
			$("<span class='nav_tips'>" + item.text + "</span>").appendTo(li);
			
			var anode = $("<a href='javascript:void(0)'></a>");
			li.append(anode);
			
			li.attr("data-href", item.href);
			
			
			if(item.isClickSelected === false) {
				li.attr("data-isClickSelected", false);
			}
			
			if(item.selected === true) {
				li.addClass("lui_item_on");
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
		
		var isAnimation = false;
		opt.on("click", function() {
			if(isAnimation) {
				return;
			}
			isAnimation = true;
			var ele = $("[data-lui-body-mark='lui_expand_body_content']");
			if(ele.hasClass("status_spread")) {
				ele.removeClass("status_spread");
				ele.addClass("status_collapse");
				outer.addClass("lui_exapandNav_collapse");
				setTimeout(function() {
					isAnimation = false;
				}, 300);
			} else {
				ele.removeClass("status_collapse");
				ele.addClass("status_spread");
				setTimeout(function() {
					outer.removeClass("lui_exapandNav_collapse");
					isAnimation = false;
				}, 300);
			}
		});
		
		
		ul.on( "click", "li" , function(evt) {
			var $t = $(evt.currentTarget), href = $t.attr("data-href"), 
				isClickSelected = $t.attr("data-isClickSelected");
			if(href) {
				if(isClickSelected !== "false") {
					ul.find("li").removeClass("lui_item_on");
					$t.addClass("lui_item_on");
				}
				if(href.toLowerCase().indexOf("javascript:") > -1){
					location.href = href;
				}else{
					LUI.pageOpen(href, '_blank');
				}
			}
		})
	})
}