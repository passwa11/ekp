var target = render.vars.target ? render.vars.target : '_blank';
var parent = render.parent;

/**
* 绘制树菜单内容
* @return
*/
function drawTreeContent(){
	
	// 构建树菜单框架载体
	var treeContainer = createTreeContainer();
	
	// 创建左侧门户页面纵向Tab内容载体
	var pageTabContainer = createPageTabContainer().appendTo(treeContainer);
	
	// 循环构建页面Tab明细项
	for(var i = 0; i < data.length; i++){
		var pageTabItemData = data[i];
		var pageItem = createPageItem(pageTabItemData).appendTo(pageTabContainer);
	}	

	return treeContainer;
	
}


/**
* 创建树菜单浮动窗口展示框架载体
* @return
*/
function createTreeContainer() {
	var treeContainer = $('<div class="lui_dataview_treemenu2_portal3" />');
	return treeContainer;
}


/**
* 创建左侧门户页面纵向Tab内容载体
* @return
*/
function createPageTabContainer(){
	var pageTabContainer = $('<div class="page_tab_container" />');
	return pageTabContainer;
}


/**
* 创建左侧门户页面纵向Tab单个明细项
* @return
*/
function createPageItem(pageTabItemData){
	var pageItem = $('<div class="page_tab_item" />');
	
	// 显示标题文字
	pageItem.text(pageTabItemData.text); 
	pageItem.attr("title", pageTabItemData.text);
		
	// 鼠标移入
	pageItem.mouseenter(pageTabItemData,function(event){
		var rowData = event.data;
        var currentItem = this;
        
        $(".lui_dataview_treemenu2_portal3 .page_tab_container .page_tab_item").each(function(){
        	if(currentItem==this){
        		// 添加选中样式
        		$(this).addClass("page_tab_item_selected");
        		$(this).addClass("lui_text_primary");
        	}else{
        		// 移除选中样式
        		$(this).removeClass("page_tab_item_selected");
        		$(this).removeClass("lui_text_primary");
        	}
        });
		
        // 查找相应页面配置的系统导航子菜单数据
        var categoryArray = [];
        for(var i=0; i<submenuDataArray.length; i++){
        	var submenuData = submenuDataArray[i];
        	if(rowData.fdId == submenuData.portalPageId){
        		categoryArray = $.parseJSON(submenuData.fdContent);
        	}
        }
        
        // 绘制右侧分类子菜单内容
        drawRightContent(categoryArray);
	});
	
	// 鼠标移出
	pageItem.mouseleave(pageTabItemData,function(event){
        var rowData = event.data;
        if($(this).hasClass("page_tab_item_selected")==false){
            $(this).removeClass("lui_text_primary");
        }
	});
	
	// 鼠标点击
	pageItem.click(pageTabItemData,function(event){
        var rowData = event.data;
        // 打开点击的页面
        doOpenPage(rowData,this);
	});
	
	return pageItem;
}


/**
* 绘制右侧分类子菜单内容
* @return
*/
function drawRightContent(categoryArray){
	
	// 删除上一次渲染的右侧子菜单容器
	$tree.children("div.tab_content_container").remove();
	
	if(categoryArray.length>0){
		
			// 构建右侧子菜单容器
			var tabContentContainer = createTabContentContainer().appendTo($tree);
			var tabContentContainerWidth = parseInt( ( $(window).width() - parent.element.offset().left) * 0.8 ) - $tree.children("div.page_tab_container").width();
			tabContentContainer.css("width",tabContentContainerWidth);
			
			// 判断是否存在两级数据（只包含一级数据时:将数据作为导航渲染，当存在两级数据时：第一级数据作为分类渲染，第二级数据作为导航渲染）
			var hasTwoLevelData = false;
			for(var i=0;i<categoryArray.length;i++){
				var categoryItemData = categoryArray[i];
				if(categoryItemData.children && categoryItemData.children.length>0){
					hasTwoLevelData = true;
					break;
				}
			}
			
			var navigationCategoryContainer = createNavigationCategoryContainer().appendTo(tabContentContainer);
			
			if(hasTwoLevelData){
				
				for(var i=0;i<categoryArray.length;i++){
					var categoryItemData = categoryArray[i];
					
					// 构建分类容器
					var categoryItem = createCategoryItem(categoryItemData).appendTo(navigationCategoryContainer);
					
					// 构建分类标题
					var categoryTitle = createCategoryTitle(categoryItemData).appendTo(categoryItem);
					
					// 构建导航容器
					var navigationContainer = createNavigationContainer().appendTo(categoryItem);
					
					// 循环构建导航明细项
					var navigationItemDataArray = categoryItemData.children||[];
					if(navigationItemDataArray.length>0){
						for(var k=0;k<navigationItemDataArray.length;k++){
							createNavigationItem(navigationItemDataArray[k]).appendTo(navigationContainer);
						}
					}else{
						// 没有子导航时创建一个空的明细项
						createNavigationItem(null).appendTo(navigationContainer);
					}

				}	
				
			}else{
				
				// 构建导航容器
				var navigationContainer = createNavigationContainer().appendTo(navigationCategoryContainer);
				
				// 只存在一级的情况下，添加样式强制向左偏移指定单位像素（目的是为了与有两级的菜单左侧起始位置对齐） 
				navigationContainer.addClass("navigation_container_just_one_level");
				
				// 循环构建导航明细项
				for(var i=0;i<categoryArray.length;i++){
				    createNavigationItem(categoryArray[i]).appendTo(navigationContainer);
			    }
				
				// 重置树的相关样式 
				resetTreeStyle(); 
				
			}

	}

}


/**
* 创建右侧子菜单载体
* @return
*/
function createTabContentContainer(){
	var tabContentContainer = $('<div class="tab_content_container" />');
	return tabContentContainer;
}

/**
* 创建导航分类行载体
* @return
*/
function createNavigationCategoryContainer(){
	var navigationCategoryContainer = $('<div class="navigation_category_container" />');
	return navigationCategoryContainer;
}


/**
* 创建导航分类基本信息(子菜单中的一级菜单)载体
* @return
*/
function createCategoryItem(){
	var categoryItem = $('<div class="category_item" />');
	return categoryItem;
}


/**
* 创建导航分类标题载体
* @return
*/
function createCategoryTitle(categoryItemData){
	var categoryTitle = $('<div class="category_title" />');
	
	// 显示标题文字
	var titleText = $('<div class="category_title_text" />').appendTo(categoryTitle);
	titleText.text(categoryItemData.text);
	titleText.attr("title", categoryItemData.text);
	
	// 鼠标移入
	titleText.mouseenter(categoryItemData,function(event){
		var rowData = event.data;
		$(this).addClass("lui_text_primary");
	});
	
	// 鼠标移出
	titleText.mouseleave(categoryItemData,function(event){
        var rowData = event.data;
        $(this).removeClass("lui_text_primary");
	});	
	
	// 鼠标点击
	titleText.click(categoryItemData,function(event){
        var rowData = event.data;
        // 打开点击的页面
        doOpenPage(rowData,this);
	});
	
	// 展开图标
	$('<div class="category_title_dis" />').appendTo(categoryTitle);

	return categoryTitle;
}


/**
* 创建右侧导航(子菜单中的二级菜单)明细项载体
* @return
*/
function createNavigationContainer(){
	var navigationContainer = $('<div class="navigation_container" />');
	return navigationContainer;
}


/**
* 创建右侧导航单个明细项
* @return
*/
function createNavigationItem(navigationItemData){
	var navigationItem = $('<div class="navigation_item" />');
	if(navigationItemData!=null){
		// 显示标题文字
		navigationItem.text(navigationItemData.text); 
		navigationItem.attr("title", navigationItemData.text);
		
		// 鼠标移入
		navigationItem.mouseenter(navigationItemData,function(event){
			var rowData = event.data;
			$(this).addClass("lui_text_primary");
		});
		
		// 鼠标移出
		navigationItem.mouseleave(navigationItemData,function(event){
	        var rowData = event.data;
	        $(this).removeClass("lui_text_primary");
		});	
		
		// 鼠标点击
		navigationItem.click(navigationItemData,function(event){
	        var rowData = event.data;
	        // 打开点击的页面
	        doOpenPage(rowData,this);
		});		
	}
	return navigationItem;
}


/**
* 打开选中页面
* @param configData 
* @param elementDom 点击的DOM (HTML DOM Element对象) 
* @return
*/
function doOpenPage(configData,elementDom){
	var text = env.fn.formatText(configData.text);
	var href = env.fn.formatUrl(configData.href);
	var target = $.trim(configData.target) != '' ? configData.target : target;  // 目标页面打开方式( _top:本页面、   _blank：新页面、   _content:内容区 )
	var pageType = configData.pageType; // 页面类型：1、页面   2、URL
	var usefor = render.config['for'];
	var mode = LUI.pageMode();
	
	if(usefor == 'switchPage' && mode == 'quick' && target != '_blank'){ //极速模式且二级树用于切换页面时调用LUI.pageOpen....bad hack
		if(pageType){ // 当前点击的是门户页面菜单
			if(pageType == '1'){ 
				target = '_content'; // 页面以 _content 方式打开
			}else{ 
				target = '_iframe';  // URL以 _iframe 方式打开
			}
		}else{ // 当点击的是系统导航配置的子菜单
		    target = '_iframe';
		}
		LUI.pageOpen(href, target);
		// 事件在跳转选中门户路径之后触发
		parent.emit('treeMenuChanged',{
			channel : parent.config.channel ?  parent.config.channel : null,
			text : text,
			target: target,
			element: elementDom
		});				
	}else{
		if(target=="_content"){
			target="_top";
		}
		// 事件在弹出门户新窗口之前触发
		parent.emit('treeMenuChanged',{
			channel : parent.config.channel ?  parent.config.channel : null,
			text : text,
			target: target,
			element: elementDom
		});	
		setTimeout(function(){
			window.open(href, target); // 注：当点击的门户配置为弹出新窗口时，不修改当前门户的显示名称（即不触发treeMenuChanged事件）				
		},100);
	}
	
}


/**
 * 根据门户与页面中间表的fdId获取子级树形菜单数据（从门户公共部件-系统导航中获取）
 * @return
 */
function getSubmenuDataArray(){	
	var portalPageIds = "";
	for(var i=0; i<data.length; i++){
		var rowData = data[i];
		portalPageIds+=(i==0?"":",")+rowData.fdId;
	}
	var submenuDataArray = [];
	var params = {"portalPageIds":portalPageIds}
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/portal/sys_portal_nav/sysPortalNav.do?method=getPortletNavByPortalPageIdArray",
		async: false,
		data: params,
		type: "POST",
		dataType: 'json',
		success: function (result) {
          if(result && result.length>0){
        	  submenuDataArray = result;
          }
		}
	});
	return submenuDataArray;
}


/**
 * 重置树的相关样式
 * @param $tree 树载体
 * @return
 */
function resetTreeStyle(){
	var $navigationItemArray = $tree.find(".navigation_item");
	if($navigationItemArray.length>0){
		var firstDomOffsetLeft = $navigationItemArray.eq(0).offset().left;
		$navigationItemArray.each(function(index,dom){
			if( $(dom).offset().left == firstDomOffsetLeft ){
				$(dom).attr("navigation_item_row_type","first_row");
			}
		});		
		var $firstRowNavigationItemArray = $tree.find(".navigation_item[navigation_item_row_type='first_row']");
		$firstRowNavigationItemArray.each(function(index,dom){
			$(dom).addClass("row_first_navigation_item");
		});
	}
}


var submenuDataArray = getSubmenuDataArray();// 查询子菜单配置数据（从门户公共部件-系统导航中获取）

var $tree = drawTreeContent();
done($tree); // 完成构建树形菜单DOM


//发送菜单构建完成后的事件
parent.emit('treeMenuLoaded',{
	channel : parent.config.channel ?  parent.config.channel : null,
	treeItems: $tree.find(".page_tab_item")
});

//$tree.find(".page_tab_item").eq(0).mouseenter(); // 打开切换门户悬浮窗时默认选中第一项


