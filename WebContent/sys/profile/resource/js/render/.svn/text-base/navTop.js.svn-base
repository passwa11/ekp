seajs.use(['lui/topic','lui/popup'],function(topic,Popup){
	var root,rootInner,currentItem,
		navTop = render.parent;

	// 获取展示顶部导航菜单项的容器对象（jQuery DIV）
	root = render.parent.element;
	root.addClass('lui_profile_header_navTop_root');

	// 在容器内构建一个子DIV容器（用来包纳顶部可视区域菜单项，并预留给主题包做CSS定位或样式渲染）
	rootInner = $('<div class="lui_profile_header_navTop_rootInner"/>').appendTo(root);


	/**
	 * 构建顶部导航菜单的内容
	 * @return
	 */
	function buildTopNavMenuContent(){
		// 在容器内构建第一个菜单项
		var firstMenuItemData = data[0];
		var firstMenuItemElement = createItem(rootInner,firstMenuItemData);

		// 获取第一个菜单项的宽度 ( 用来计算当前屏幕分辨率的容器下一共可以渲染多少个菜单项 )
		var firstMenuItemWidth = firstMenuItemElement.outerWidth(true);

		// 计算出顶部菜单容器可视区域可以展示出多少个菜单项
		var	maxShowLength = parseInt(root.width() / firstMenuItemWidth);
		maxShowLength = maxShowLength==0?1:maxShowLength;

		// 菜单项总个数
		var dataLength = data.length;

		var lenOut = 0; // 直接显示在顶部菜单容器可视区域的菜单项个数
		var lenIn = 0;  // 初始化需要隐藏,待鼠标移入时显示在下拉容器中的菜单项个数
		if(dataLength <= maxShowLength) {
			lenOut = dataLength;
			lenIn = 0;
		} else if(dataLength >= maxShowLength + 1) {
			lenOut = maxShowLength - 1;
			lenIn = dataLength - maxShowLength + 1;
		}

		// 构建直接显示在顶部菜单容器可视区域的菜单项元素(从第二个菜单元素开始构建，第一个元素前面已经直接构建好了)
		for(var i = 1; i < lenOut; i ++) {
			createItem(rootInner,data[i]);
		}

		// 构建“更多”图标容器以及需要展示在下拉容器中的菜单项
		if(lenIn > 0) {
			var moreItem = createMoreItem(rootInner);
			var popup = $('<div class="lui_profile_header_navTop_popup" />');
			popup.append($('<div  class="lui_profile_header_triangle_border"></div>'));
			popup.append($('<div  class="lui_profile_header_triangle_bg"></div>'));
			if(lenOut==0){
				firstMenuItemElement.remove();
			}
			for(var j = 0; j <  lenIn;j++){
				createItem(popup,data[lenOut + j]);
			}
			var config = {
				"element":popup,
				"positionObject":moreItem,
				"popupObject":popup,
				"borderWidth" : -1
			};
			var pp = new Popup.Popup(config);
			// 监听弹出窗口显示事件，设置下拉弹出层的最大高度
			pp.on("show",function(){
				var setPopupHeightInterval = setInterval(function(){
					if(popup.is(":visible")){
						clearInterval(setPopupHeightInterval);
						var popupOffsetTop = popup.offset().top;
						var innerHeight = window.innerHeight || document.documentElement.clientHeight // 页面可视高度
						var popupHeight = innerHeight - popupOffsetTop - 20;
						popup.css("max-height",popupHeight+"px");
					}
				},10);
			});
			pp.startup();
			pp.draw();
		}
	}



	/**
	 * 创建单个菜单项元素，并绑定相应的click事件
	 * @param parent 父级jQuery DOM对象
	 * @param _data  菜单项数据
	 * @return
	 */
	function createItem( parent, _data ){
		var item = $('<a class="lui_profile_header_navTop_item"></a>').attr("title",_data.title),
			itemIcon = $('<div class="lui_profile_header_navTop_itemIcon" />').addClass(_data.icon).appendTo(item);
		itemText = $('<div class="lui_profile_header_navTop_itemText" />').text(_data.title).appendTo(item);
		item.appendTo(parent);

		if(_data.key == navTop.selectedItem){
			item.addClass('current');
			currentItem = item;
		}
		item.attr('data-type',_data.key);
		item.on('click',function(){
			currentItem = $('.lui_profile_header_navTop_item[data-type="'+ _data.key +'"]');
			$('.lui_profile_header_navTop_item').removeClass('current');
			currentItem.addClass('current');
			exchangeItem();
			topic.publish('sys.profile.navTop.change',{
				key : _data.key
			});
		});
		return item;
	}


	/**
	 * 交换菜单项的位置
	 * @return
	 */
	function exchangeItem(){
		// 如果当前菜单项不在顶部可视区域（即点击的菜单在下拉展示区），则将可视区域的第一个菜单项与当前点击的菜单项交换位置
		if(!$.contains(rootInner[0],currentItem[0])){
			var firstNode = rootInner.children().eq(0);
			currentItem.after(firstNode);
			rootInner.prepend(currentItem);
		}
	}

	/**
	 * 创建“更多”图标元素
	 * @param parent 父级jQuery DOM对象
	 * @return
	 */
	function createMoreItem(parent){
		var item = $("<div data-lui-switch-class='lui_profile_navTop_over' class='lui_profile_header_navTop_item more'></div>");
		item.appendTo(parent);
		item.text('更多');
		return item;
	}


	// 调用构建顶部导航菜单的内容
	buildTopNavMenuContent();

	/**
	 * 监听浏览器窗口改变事件(控制根据浏览器窗口大小的变化动态移动菜单项显示的位置，即:在顶部可视区和下拉展示区之间移动)
	 * @return
	 */
	$(window).resize(function() {
		// 当前已经显示在顶部可视区的菜单项个数
		var currentShowLength = rootInner.children().length;

		// 第一个菜单项元素
		var firstMenuItemElement = rootInner.children().eq(0);

		// 获取第一个菜单项的宽度 ( 用来计算当前屏幕分辨率的容器下一共可以渲染多少个菜单项 )
		var firstMenuItemWidth = firstMenuItemElement.outerWidth(true);

		// 计算出顶部菜单容器可视区域可以展示出多少个菜单项
		var	maxShowLength = parseInt(root.width() / firstMenuItemWidth);
		maxShowLength = maxShowLength==0?1:maxShowLength;

		var navPopup = $(".lui_profile_header_navTop_popup");
		if(currentShowLength>maxShowLength){
			var needMoveNum = currentShowLength - maxShowLength;
			for(var i=0;i<needMoveNum;i++){
				var moveNode = rootInner.children().eq(-2);
				var navPopupFirstMenuItemElement = navPopup.children(".lui_profile_header_navTop_item").first();
				if(navPopupFirstMenuItemElement.length>0){
					navPopupFirstMenuItemElement.before(moveNode);
				}else{
					navPopup.append(moveNode);
				}
			}
		}else if(currentShowLength<maxShowLength){
			var needMoveNum = maxShowLength - currentShowLength;
			for(var i=0;i<needMoveNum;i++){
				var moveNode = navPopup.children(".lui_profile_header_navTop_item").first();
				rootInner.children().last().before(moveNode);
			}
		}

	});

	done();
});