var target = render.vars.target?render.vars.target:'_self';

/**
 * 创建菜单载体区域（DIV）
 * @param root  菜单载体区域最顶层DIV  
 * @return 返回  菜单载体区域最底层DIV
 */
function createHeader(root){
	var domR = $("<div class='lui_portal_header_menu_r'></div>");
	domR.appendTo(root);
	var domC = $("<div class='lui_portal_header_menu_c'></div>");
	domC.appendTo(domR);
	var div = $("<div class='lui_portal_header_menu_item_div'></div>");
	div.appendTo(domC);
	return div;
} 

/**
 * 创建菜单明细项（并同时绑定鼠标悬停和单击事件）
 * @param parent  菜单明细项父DIV（jQuery DOM） 
 * @param data    菜单明细JSON对象
 * @param createLine 是否需要创建分隔竖线(true or false)
 * @return
 */
function createItem(parent,data,createLine){
	var item = $("<div style='display: inline-block;vertical-align: top;'></div>");
	item.attr('data-portal-id',data.fdId);
	item.addClass('lui_portal_header_menu_item');
	item.appendTo(parent);
	var selected = data.selected == null ? false : data.selected;
	if(selected){
		item.attr("class","lui_portal_header_menu_item_current");
	}else{
		item.attr("class","lui_portal_header_menu_item_div");
	}
	item.hover(
			function (e) {
				// 添加悬停样式
				$(this).addClass("lui_portal_header_menu_item_hover");
			    // 创建下拉菜单
				var submenuData = getSubmenuData(data.fdId);
				if(submenuData && submenuData.length>0){
				    var fdId = data.fdId;
				    var $menuItem = $(this).find(".lui_portal_header_menu_item_r");
				    var dropDownMenu = $("div.lui_portal_header_dropdown_menu[data-portal-id='"+data.fdId+"']");
					if(dropDownMenu.length>0){ // 已经创建过下拉菜单的情况下，显示菜单DIV
						dropDownMenu.show();  
					}else{ // 未创建过下拉菜单的情况下，新建菜单DIV
						dropDownMenu = createDropDownMenu($menuItem,data,submenuData);
					}
					
					// 设置下拉菜单显示的位置CSS样式
					var menuWidth = $menuItem.width();     // 获取当前菜单项的宽度
					var menuHeight = $menuItem.height();   // 获取当前菜单项的高度
					var menuOffset = $menuItem.offset();   // 获取当前菜单项的偏移坐标
					var dropDownMenuTop = menuOffset.top + menuHeight;
					var dropDownMenuLeft = menuOffset.left;
					dropDownMenu.css({
						"top": dropDownMenuTop,
						"left": dropDownMenuLeft,
						"width": menuWidth
					});
					
				}
			},
			function (e) {
				// 移除悬停样式
				$(this).removeClass("lui_portal_header_menu_item_hover");
				// 移除下拉菜单
				var toTarget = e.toElement||e.relatedTarget;
				var $toTarget = $(toTarget);
				if($toTarget.hasClass("lui_portal_header_dropdown_menu")==false&&$toTarget.closest(".lui_portal_header_dropdown_menu").length==0){
					$("div.lui_portal_header_dropdown_menu[data-portal-id='"+data.fdId+"']").hide();
				}
			}
	);
	item.click(data,function(event){
		var configData = event.data;
		var preItem = $('.lui_portal_header_menu_item_current',parent);
		preItem.removeClass('lui_portal_header_menu_item_current').addClass('lui_portal_header_menu_item_div');
		$(item).addClass('lui_portal_header_menu_item_current');
		var target = data.target, href = data.href;
		var mode = LUI.pageMode();
		if(mode=='quick' && target != '_blank'){
			target = '_content';
			if(data.pageType == '2'){
				target = '_iframe';
			}
		}
		LUI.pageOpen(env.fn.formatUrl(href),target,{
			portalId : data.fdId
		});
	});
	var domL = $("<div class='lui_portal_header_menu_item_l'></div>");
	domL.appendTo(item);
	var domR = $("<div class='lui_portal_header_menu_item_r'></div>");
	domR.appendTo(domL);
	var domC = $("<div class='lui_portal_header_menu_item_c'></div>");
	domC.appendTo(domR);
	domC.append(env.fn.formatText(data.text));

	if(createLine){
		parent.append($("<div class='lui_portal_header_menu_item_line' style='display: inline-block;vertical-align: top;'></div>"));
	}
}


/**
 * 创建下拉垂直菜单
 * @param $menuItem 悬停选中的菜单元素
 * @param data      菜单明细JSON对象
 * @param submenuData 子级树形菜单数据
 * @return
 */
function createDropDownMenu($menuItem,data,submenuData){
	var dropDownMenu = $("<div root='true' class='lui_portal_header_dropdown_menu'></div>");
	dropDownMenu.attr('data-portal-id',data.fdId);
	dropDownMenu.hover(
		function (e) {},
		function (e) {
           $(this).hide();
		}
	);
	$("body").append(dropDownMenu);
	createDropDownMenuItem(dropDownMenu,submenuData);
	return dropDownMenu;
}

/**
 * 创建下拉垂直菜单明细项
 * @param $parent 父级元素
 * @param submenuData 子级树形菜单数据
 * @return
 */
function createDropDownMenuItem($parent,submenuData){
		var root = $parent.attr("root");
		var hasChild = false;
	    for(var i=0;i<submenuData.length;i++){
			var menuObj = submenuData[i];
			
			// 创建菜单明细项DIV
			var dropDownItem = $("<div class='lui_portal_header_dropdown_menu_item'></div>");

			// 创建菜单明细项标题文字DIV
			var dropDownItemText = $("<div class='lui_portal_header_dropdown_menu_item_text'></div>");
			dropDownItemText.text(menuObj.text);
			dropDownItem.append(dropDownItemText);
			
			if(menuObj.children){
				hasChild = true;
				// 创建菜单明细项展开下级图标DIV
				var dropDownItemDis = $("<div class='lui_portal_header_dropdown_menu_item_dis'></div>");
				dropDownItem.append(dropDownItemDis);
			}

			// 鼠标移入悬停
			dropDownItem.mouseenter(menuObj,function(event){
		        var itemData = event.data;
				$(this).addClass("lui_portal_header_dropdown_menu_item_hover");
				$(this).children(".lui_portal_header_dropdown_menu_item_text").addClass("lui_text_primary");
				if(itemData.children){
					var dropDownMenu = $(this).children("div.lui_portal_header_dropdown_menu");
					if(dropDownMenu.length>0){
						dropDownMenu.show();
					}else{
						var subMenu = $("<div class='lui_portal_header_dropdown_menu'></div>");
						subMenu.css({
							"left": "100%",
							"min-width": "100%"
						});	
						$(this).append(subMenu);
						createDropDownMenuItem(subMenu,itemData.children);						
					}

				}
			});
			
			// 鼠标移出
			dropDownItem.mouseleave(menuObj,function(event){
		        var itemData = event.data;
				// 清除悬停样式
				$(this).removeClass("lui_portal_header_dropdown_menu_item_hover");
				$(this).children(".lui_portal_header_dropdown_menu_item_text").removeClass("lui_text_primary");
				// 移除子菜单
				$(this).children(".lui_portal_header_dropdown_menu").hide();
			});
			
			// 鼠标单击
			dropDownItem.click(menuObj,function(event){
				var itemData = event.data;
				// 隐藏菜单项
				$(this).closest("div.lui_portal_header_dropdown_menu").hide();
		        // 打开点击的页面
				doOpenDownItemPage(itemData);
		        // 阻止事件冒泡
				event.stopPropagation(); 
			});
			
			$parent.append(dropDownItem);
		 }	
	    
	    if(('true'==root)&&!hasChild){
	    	$parent.css("overflow-x","hidden");
	    	$parent.css("overflow-y","auto");
	    	$parent.css("max-height","500px");
	    }
	
}


/**
* 打开选中的垂直菜单明细项相应页面
* @param configData
* @return
*/
function doOpenDownItemPage(configData){
	var href = env.fn.formatUrl(configData.href);
	var target = $.trim(configData.target) != '' ? configData.target : target;  // 目标页面打开方式( _top:本页面、   _blank：新页面、   _content:内容区 )
	var mode = LUI.pageMode(); // 当前模式：quick（极速模式）
	
	if(mode == 'quick' && target != '_blank'){ //极速模式且二级树用于切换页面时调用LUI.pageOpen....bad hack
		target = '_iframe';
		LUI.pageOpen(href, target);		
	}else{
		if(target=="_content"){
			target="_top";
		}
		setTimeout(function(){
			window.open(href, target); 			
		},100);
	}
}



/**
 * 根据页面fdId获取子级树形菜单数据（从门户公共部件-多级菜单树中获取）
 * @param portalPageId 门户与页面中间表的fdId
 * @return
 */
function getSubmenuData(portalPageId){	
	
	var submenuCacheId = "dropdown_menu_submenu_"+portalPageId;
	if(window[submenuCacheId]){
		return window[submenuCacheId];
	}
	
	var submenuData = null;
	var params = {"portalPageId":portalPageId}
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/portal/sys_portal_nav/sysPortalNav.do?method=getPortletNavByPortalPageId",
		async: false,
		data: params,
		type: "POST", 
		dataType: 'json',
		success: function (result) {
          if(result && result.fdContent){
        	  submenuData = $.parseJSON(result.fdContent);
        	  window[submenuCacheId] = submenuData;
          }else{
        	  window[submenuCacheId] = [];
          }
		}
	});

	return submenuData;
}


var root = $("<div class='lui_portal_header_menu_l'></div>");
var xdiv = createHeader(root);
var left = $("<div class='lui_portal_header_menu_item_left'></div>").hide().appendTo(xdiv);
var frame = $("<div class='lui_portal_header_menu_item_frame'></div>").appendTo(xdiv);
var right = $("<div class='lui_portal_header_menu_item_right'></div>").hide().appendTo(xdiv);
var body = $("<div class='lui_portal_header_menu_item_body'></div>").appendTo(frame);
frame.css("width","100%");
body.css("white-space","nowrap");
for(var i=0; i<data.length; i++){
	createItem(body,data[i],(i<data.length-1));
} 
done(root);

// 如果后台有配置经典页眉导航设置（navigationSettingConfig 定义在 sys\portal\template\menu\header.jsp）
if(typeof navigationSettingConfig!=="undefined" && navigationSettingConfig!=null){
	
	if(navigationSettingConfig.widthType=="width_fixed"){ // 固定宽度
		var fixedWidth = navigationSettingConfig.fixedWidth;
		for(var i=0; i<data.length; i++){
			var menuItem = body.children("div[data-portal-id='"+data[i].fdId+"']");
			menuItem.find(".lui_portal_header_menu_item_r").css("width",fixedWidth);
			var fontCss = {"overflow":"hidden","text-align":"center","white-space":"nowrap","-o-text-overflow":"-o-text-overflow","text-overflow":"ellipsis"};
			menuItem.find(".lui_portal_header_menu_item_c").css(fontCss);
		}	
	}else if(navigationSettingConfig.widthType=="both_sides_align"){  // 两端对齐(宽度平分)
		// 菜单区域可展现的总宽度
		var frameWidth = frame.width();
		// 菜单内容实际占用的总宽度
		var bodyWidth = body.width();
		// 当实际占用宽度小于菜单区域可展现的宽度时，才进行宽度平分，否则依然保持宽度自适应
		if(bodyWidth<=frameWidth){
			// 获取分割线DIV宽度
			var dividingLineWidth = $(".lui_portal_header_menu_item_line:first").width();
	        // 计算出所有分割线的总宽度
			var dividingLineTotalWidth = dividingLineWidth * (data.length-1);
			// 计算出可平均分配给各个菜单项的宽度
			var menuItemWidth = parseInt( (frameWidth - dividingLineTotalWidth)/data.length );
			for(var i=0; i<data.length; i++){
				var menuItem = body.children("div[data-portal-id='"+data[i].fdId+"']");
				menuItem.find(".lui_portal_header_menu_item_r").css("width",menuItemWidth);
				var fontCss = {"overflow":"hidden","text-align":"center","white-space":"nowrap","-o-text-overflow":"-o-text-overflow","text-overflow":"ellipsis"};
				menuItem.find(".lui_portal_header_menu_item_c").css(fontCss);
			}			
		}
	}
	
}
var w1 = xdiv.width();
var w2 = body.width();
if(w2>w1){
	left.show();
	right.show();
	//ie下宽度太小显示不全
	body.css("width",body.width()+2);
	frame.css("left",left.width());
	frame.css("right",right.width());
	frame.css("width","");
	left.click(function(){
		var xl = frame.scrollLeft();
		var scro = xl - 300;
		frame.animate({scrollLeft: scro}, 200);
	});
	right.click(function(){
		var xl = frame.scrollLeft();
		var scro = xl + 300;
		frame.animate({scrollLeft: scro}, 200);
	});
	var currentNode = $(".lui_portal_header_menu_item_current");
	if(currentNode.length>0){
		var w =currentNode[0].offsetLeft;
		var xl = frame.scrollLeft();
		var scro = xl+ w;
		frame.animate({scrollLeft: scro}, 200);
	}
}
seajs.use(['lui/topic'],function(topic){
	topic.subscribe('lui.page.open',function(evt){
		if(evt && evt.features){
			var portalId = evt.features.portalId;
			if(portalId){
				var item = $('[data-portal-id="'+ portalId +'"]',root);
				if(item.length > 0){
					$('[data-portal-id]',root).removeClass('lui_portal_header_menu_item_current').addClass('lui_portal_header_menu_item_div');
					$(item).addClass('lui_portal_header_menu_item_current');
				}
			}
		}
	});
});
