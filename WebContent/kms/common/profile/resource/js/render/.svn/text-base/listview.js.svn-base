seajs.use(['lui/topic','lui/fixed','lui/jquery','lui/dialog','lang!sys-profile'],function(topic,fixed,$,dialog,lang){
	_lang=lang;
	//辣鸡IE8
	Array.prototype.sort = Array.prototype.sort || function(fn){
		var array = this;
		var fn = fn || function(a,b){ return b > a; };
		for(var i = 1;i < array.length;i++){
			var key = array[i];
			var j = i - 1;
			while(j >= 0 && fn(array[i],key)){
				array[j + 1] = array[j];
				j--;
			}
			array[j + 1] = key;
		}
		return array;
	};
	String.prototype.trim = String.prototype.trim = function(){
		return $.trim(this);
	};
	
	var dataPy = data.slice(0); //按拼音排序数据集合
	//结果集按拼音首字母排序
	dataPy.sort(function(a,b){
		var aPinYin = a['pinYin'].trim(),
			bPinYin = b['pinYin'].trim();
		if(aPinYin > bPinYin){
			return 1;
		}else{
			return -1;
		}
	});
	
	//结果集按拼音首字母排序
	data.sort(function(a,b){
		var aOrder = a['order'],
			bOrder = b['order'];
		if(aOrder > bOrder){
			return 1;
		}else if(aOrder < bOrder){
			return -1;
		}else{
			var aPinYin = a['pinYin'].trim(),
				bPinYin = b['pinYin'].trim();
			if(aPinYin > bPinYin){
				return 1;
			}else{
				return -1;
			}
		}
	});
	var app = data,
		viewType  = 'grid',
		isGridInit , isListInit;
	
	var container,searchBar,appMenu,GridView,ListView;
	
	//构建根节点
	function createContainer(){
		return $('<div class="lui_profile_listview_container" />');
	}
	function createAppMeanu(){
		return $('<div class="lui_profile_listview_appMenu" />');
	}
	function showTipeNull(flag){
		
		var tipsNull=$(".tipsNull");
		if(flag!=true){
			tipsNull.css({
					'display':'none',
			});
		}else{
				tipsNull.css({
					'display':'block',
				});
		}
	}
	var count=0;
	var flag=false;
	var nullFlag=false;
	var timer=0;
	//构建搜索区域
	function createSearchBar(){
		var searchContainer = $('<div class="lui_profile_listview_search" />'),
			searchContent = $('<div class="lui_profile_listview_content searchContent" />'),
			switchView = $('<div class="lui_profile_listview_switchView" />').appendTo(searchContent),
			searchWrap = $('<div class="lui_profile_listview_searchWrap" />').appendTo(searchContent);
		//切换区域	
		var switchToGrid = $('<a class="lui_profile_listview_switchTo" />').appendTo(switchView),
			switchToList =  $('<a class="lui_profile_listview_switchTo" />').appendTo(switchView);
		
		switchToGrid.on('click',function(){
			if(!isGridInit){
				GridView = createGridView(appMenu);
			}
			switchToList.removeClass('lui_icon_on');
			switchToGrid.addClass('lui_icon_on');
			GridView.show();
			if(ListView){
				ListView.hide();
			}
			viewType = 'grid';
		}).append($('<i class="lui_profile_listview_icon lui_icon_s_more_default" />')).attr('title','格子视图');
		
		switchToList.on('click',function(){
			if(!isListInit){
				ListView = createListView(appMenu);
			}
			switchToGrid.removeClass('lui_icon_on');
			switchToList.addClass('lui_icon_on');
			ListView.show();
			if(GridView){
				GridView.hide();
			}
			viewType = 'list';
		}).append($('<i class="lui_profile_listview_icon lui_icon_s_list" />')).attr('title','列表视图');
		if(viewType == 'grid'){
			switchToGrid.addClass('lui_icon_on')
		}else{
			switchToList.addClass('lui_icon_on');
		}
		
		//搜索区域
		var icon = $('<i class="lui_profile_listview_icon lui_icon_s_icon_search" />').appendTo(searchWrap),
			input = $('<input type="text" class="lui_profile_search_input" /> ').prop("placeholder",_lang['sys.profile.render.search.byKeyword']);
			input.appendTo(searchWrap);
		var closeIcon = $('<i class="close" />');
			closeIcon.css({
					'display':'none',
				});
			var tips=$('<div id="tips" class="tips"><span>请输入查找模块名</span></div>');
			tips.css({
				'display':'none',
			});
			var tipsNull=$('<div id="tipsNull" class="tipsNull"><span>没有找到要查找的模块</span></div>');
			tipsNull.css({
				'display':'none',
			});
		
		input.after(tips);
		input.after(tipsNull);
		input.after(closeIcon);
		closeIcon.on('click',cleanInput);
		input.on('keypress',showTipe);
		function serach(){
			var value = input.val();
			if(value!=''){
				closeIcon.css({
					'display':'block',
				});
			}else{
				closeIcon.css({
					'display':'none',
				});
			}
			resetGridView(value);
			highlightListView(value);
		}
		icon.on('click',serach);
		input.on('input',serach);
		function showTipe(event){
			 if (event.keyCode == "13") {
				 var tips=$("#tips");
					var value = input.val();
					if(value!=''){
						tips.css({
							'display':'none',
						});
						
						if(nullFlag==true && !clearNullFlag){
							var clearNullFlag = setInterval(function(){
								timer+=1;
								if(timer==3){
									showTipeNull(false);
									timer=0;
									window.clearInterval(clearNullFlag);
								}else{
									showTipeNull(true);
								}
							},1000);
					}
						
					}else{
						if(!flag){
						var interval = setInterval(function(){
						count += 1;
						if(count === 3){
							tips.css({
								'display':'none',
							});
							count=0;
							flag=false;
							window.clearInterval(interval);
						}else{
							flag=true;
							tips.css({
								'display':'block',
							});
						}
						}, 1000);
					}
					}
	            }

		}	
		
		function cleanInput(){
			var value = input.val();
			if(value!=''){
				input.val(null);
				resetGridView(null);
				tipsNull.css({
					'display':'none',
				});
				tips.css({
					'display':'none',
				});
				closeIcon.css({
					'display':'none',
				});
			}
		}	
		
		searchContainer.append(searchContent)
		var f = new fixed.Fixed({
			elem : searchContainer
		});
		f.startup();
		f.draw();
		return searchContainer;
	}
	
	var pageTemp=0;
	//构建格子列表区域
	function createGridView(parent){
		function doMouseWheel(e){
			if(pageTemp>1){
			e=e||window.event;
			if(isAnimating)	return;
			isAnimating = true;
			var position = null;
			if(e.wheelDelta){
				position = e.wheelDelta > 0 ? 'up' : 'down';
			}else{
				position = e.detail > 0 ? 'down' : 'up';
			}
			var count = $('.lui_profile_listview_grid_hdItem').length,
				currentIndex = $('.lui_profile_listview_grid_hdItem.active').index(),
				index = position == 'up' ? currentIndex -1 : currentIndex + 1;
			if(index < 0) 
				index = 0;
			if(index >= count)
				index = count -1;
			var bd = $('.lui_profile_listview_grid_bd');
			$('.lui_profile_listview_grid_hdItem').removeClass('active');
			$('.lui_profile_listview_grid_hdItem').eq(index).addClass('active');
			bd.animate({
				left : 0 - index * pageWidth
			},750,'swing',function(){
				setTimeout(function(){isAnimating = false;},250);
			});
		}
		}
		var gridContainer = $('<div class="lui_profile_listview_content gridContent" />').appendTo(parent),
			grid = $('<div class="lui_profile_listview_grid" />').appendTo(gridContainer),
			gridWidth = $('body').width() - 40,
			geometry = getItemGeometry();
		console.log("geometry",geometry);
		// 估算能显示多少行 因为代码渲染退后所以这里的计算不精准，css调整后这块的高度需要重新计算
		// 搜索框的高度为58px 分页空间的高度为30px 单元格的高度为200px
		// 最少显示一行
		var sizePerRow=Math.floor(($('body').height()-58-30)/200);
		sizePerRow=sizePerRow<1?1:sizePerRow;

		//放置应用个数计算
		var sizePerLine = parseInt(gridWidth / geometry.ow),
			// sizePerPage = sizePerLine * 2,//暂时固定每页显示两行
			sizePerPage = sizePerLine * sizePerRow,//暂时固定每页显示两行
			pageNumber = Math.ceil(app.length / sizePerPage), //多少页
			pageWidth = sizePerLine * geometry.ow;
			
		grid.width(pageWidth);
		pageTemp=pageNumber;
		if(pageNumber<=1){
			if(document.addEventListener){
				document.removeEventListener('DOMMouseScroll',doMouseWheel,false);
				window.onmousewheel = document.onmousewheel =null;
		}
	}
		
		var bd = $('<div class="lui_profile_listview_grid_bd" />').width(sizePerLine * geometry.ow * pageNumber).appendTo(grid),
			hd = $('<div class="lui_profile_listview_grid_hd" />').appendTo(grid);
		for(var i = 0;i < pageNumber;i++){
			// var beginIndex = 2 * i * sizePerLine,
			// 	endIndex = Math.min ( 2 * ( i * sizePerLine + sizePerLine ), app.length ),
			// 	ul = createGridPage(beginIndex, endIndex);

			// 之前是固定两行，现在改为动态计算
			var beginIndex = sizePerRow * i * sizePerLine,
				endIndex = Math.min ( sizePerRow * ( i * sizePerLine + sizePerLine ), app.length ),
				ul = createGridPage(beginIndex, endIndex);
			ul.width(sizePerLine * geometry.ow);
			bd.append(ul);
		}
		
		var isAnimating = false;
		if(pageNumber > 1){
			//绑定click事件
			var ul = $('<ul/>').appendTo(hd);
			for(var i = 0;i < pageNumber;i++){
				var hdItem = $('<li class="lui_profile_listview_grid_hdItem"><span /></li>').appendTo(ul);
				if(i == 0){
					hdItem.addClass('active');
				}
				hdItem.on('mouseenter',(function(index){
					return function(){
						if(isAnimating)	return;
						isAnimating = true;
						$('.lui_profile_listview_grid_hdItem').removeClass('active');
						$(this).addClass('active');
						bd.animate({
							left : 0 - index * pageWidth
						},750,'swing',function(){	
							setTimeout(function(){isAnimating = false;},250);	
						});
					}
				})(i));
			}
			//绑定滚轮事件
			if(document.addEventListener){
				document.addEventListener('DOMMouseScroll',doMouseWheel,false);
			}
			document.onmousewheel=doMouseWheel;
		}
		
		isGridInit = true;
		return gridContainer;
	}
	
	//构建格子单页
	function createGridPage(beginIndex,endIndex){
		var ul = $('<ul class="lui_profile_listview_card_page" />');
		for(var i = beginIndex,j = 0 ;i < endIndex;i++,j++){
			var gridItem = createGridItem(app[i]);
			gridItem.addClass('itemStyle_'+ (j%6+1));
			ul.append(gridItem);
		}
		return ul;
	}
	
	function createGridItem(_data){
		var gridItem = $('<li class="lui_profile_block_grid_item" />');
		var	appMenuItemBlock = $('<div class="appMenu_item_block" />').appendTo(gridItem);
			
		var	appMenuIconBar = $('<div class="appMenu_iconBar" />').appendTo(appMenuItemBlock);
		var	gridIcon = $('<i />').appendTo(appMenuIconBar);
	
/**
 * 移除旧图标兼容
 */
//		if(_data.icon.indexOf('lui_icon_') > -1) {
//			gridIcon.addClass('lui_profile_listview_l_icon');
//			gridIcon.addClass(_data.icon);
//		} else {
//			appMenuIconBar.addClass('appMenu_iconBar_iconfont');
//			gridIcon.addClass('iconfont_nav');
//			gridIcon.addClass('lui_iconfont_nav_' + _data.icon);			
//		}
		appMenuIconBar.addClass('appMenu_iconBar_iconfont');
		gridIcon.addClass('iconfont_nav');
		gridIcon.addClass('lui_iconfont_nav_' + _data.icon);			
		
		
		var dataDescription = _data.description ? _data.description : _data.messageKey;
		var	gridDescription = $('<p class="appMenu_brief" />').text(dataDescription).attr("title",dataDescription).appendTo(appMenuItemBlock);
		var gridTitleWrapper = $('<div class="appMenu_title" />').appendTo(appMenuItemBlock);
		var trigIcon = $('<i class="trig" />').appendTo(gridTitleWrapper);
		var gridTitle = $('<span class="textEllipsis" />').text(_data.messageKey).appendTo(gridTitleWrapper);
		
		var title1 = _lang['sys.profile.app.homePage']

		var isModuleExist = true;
		if(_data.fdIsModuleExist == 'false' || _data.fdIsModuleExist == false){
			isModuleExist = false;
		}

		if(_data.homePageUrl){
			var gridHomeIcon = $('<i class="home" title ='+title1+'/>').appendTo(gridTitleWrapper);
			if(!isModuleExist){
				gridHomeIcon.on('click', function(event){
					showNotExistModuleAlert(_data);
				});
			}else{
				gridHomeIcon.on('click', function(event){
					event.stopPropagation();
					window.open(env.fn.formatUrl(_data.homePageUrl),"_blank");
				});
			}
		}
		
		var	appMenuMask = $('<div class="appMenu_mask" style="display: none;" />').appendTo(gridItem);
		var	appMenuBtnGroup = $('<div class="appMenu_btnGroup" />').appendTo(appMenuMask);
		if(_data.url){
			appMenuItemBlock.on('click',function(){
				if(!isModuleExist){
					showNotExistModuleAlert(_data);
					return;
				}
				var _target = _data.target || '_self';
				if(render.handleItemCallback)
					render.handleItemCallback({ key : _data.key , url : _data.url , target : _data.target  });
				//window.open(env.fn.formatUrl(_data.url),target);
			});	
			
		}else{
			appMenuItemBlock.css('cursor','auto');
		}	
		
		return gridItem;
	}
	
	function resetGridView(value){
		if(!value){
			app = data;
		}else{
			app = [];
			for(var i = 0;i < data.length;i++){
				if(data[i].messageKey.toLowerCase().indexOf(value.toLowerCase()) > -1
					|| data[i].pinYin.toLowerCase().indexOf(value.toLowerCase()) > -1)	
					app.push(data[i]);
			}
		}
				if(app.length==0){
					nullFlag=true;
				}else{
					nullFlag=false;
				}	
		GridView.remove();
		GridView = createGridView(appMenu);
		if(viewType == 'list'){
			GridView.hide();
		}
		
	}
	
	function getItemGeometry(){
		var div = $('<div class="lui_profile_listview_card_page"><div class="lui_profile_block_grid_item"></div></div>').appendTo($('body'));
		var itemDiv = $('.lui_profile_block_grid_item');
		result = {
			w: Math.ceil(itemDiv.width()),
			h: Math.ceil(itemDiv.height()),
			ow: Math.ceil(itemDiv.outerWidth(true)),
			oh: Math.ceil(itemDiv.outerHeight(true))
		};
		//火狐浏览器下 宽度识别1px -- 0.6667px hack
		if(result.w != itemDiv.width()) {
			result.ow++
		}
		div.remove();
		return result;
	}
	
	var charArray = [];
	
	//构建经典列表区域
	function createListView(parent){
		var listContainer = $('<div class="lui_profile_listview_content listContent" />').appendTo(parent),
			main = createListMain().appendTo(listContainer),
			wordSort = createWordSort().prependTo(listContainer);
		isListInit = true;
		return listContainer;
	}
	
	function createListMain(){
		charArray = [];
		var ulMap = {},
			main = $('<div class="lui_profile_block_list_main"/>');
		for(var i = 0;i < dataPy.length;i++){
			var pinYin = dataPy[i]['pinYin'].trim(),
				char = charAttr = pinYin.charAt(0).toUpperCase();
			if(!contains(charArray,char)){
				if(char < 'A' || char > 'Z'){
					char = '#';
					charAttr = 'other';
				}
				var listItem = $('<div class="lui_profile_block_list_item"/>').appendTo(main),
					listItemTitle = $('<div class="lui_profile_block_list_itemTitle"/>').text(char).appendTo(listItem),
					listItemContent = $('<div class="lui_profile_listview_list_itemContent">').appendTo(listItem);
				listItem.attr('id','block_list_' + charAttr);
				var _ul = $('<ul/>').appendTo(listItemContent);
				ulMap[char] = _ul;
				charArray.push(char);
			}
			var ul = ulMap[char],
				li = $('<li/>'), link = $('<a/>').appendTo(li),
				h3 = $('<h3 class="lui_profile_listview_list_itemh"/>').text(dataPy[i].messageKey).appendTo(link),
				description = $('<p class="lui_profile_listview_list_itemDes"/>').text(dataPy[i].description ? dataPy[i].description : '暂无简介').appendTo(link);
			li.on('click',(function(_data){
				return function(){
					var isModuleExist = true;
					if(_data.fdIsModuleExist == 'false' || _data.fdIsModuleExist == false){
						isModuleExist = false;
					}
					if(!isModuleExist){
						showNotExistModuleAlert(_data);
						return;
					}
					var target = _data.target || '_self';
					//window.open(env.fn.formatUrl(_data.url),target);
					if(render.handleItemCallback)
						render.handleItemCallback({ key : _data.key , url : _data.url , target : _data.target  });
				}
			})(dataPy[i]));
			ul.append(li);
		}
		return main;
	}
	
	function createWordSort(){
		var wordSort = $('<div class="lui_profile_block_list_wordSort" />'),
			ul = $('<ul/>').appendTo(wordSort);
		for(var i = 65 ; i < 91 ; i++ ){
			var code = 	String.fromCharCode(i),
				sortItem = $('<li class="lui_profile_block_list_wordSortItem"/>'),
				sortLink = $('<a />').text(code).appendTo(sortItem);
			sortItem.attr('data-sortType','sortType'+code);
			if(!contains(charArray,code)){
				sortItem.addClass('disable');
			}else{
				sortLink.attr('href','#block_list_' + code);
			}
			ul.append(sortItem);
		}
		//添加#
		var sortItem = $('<li class="lui_profile_block_list_wordSortItem"/>'),
			sortLink = $('<a />').text('#').appendTo(sortItem);
		if(!contains(charArray,'#')){
			sortItem.addClass('disable');
		}else{
			sortLink.attr('href','#block_list_' + 'other');
		}
		ul.append(sortItem);
		return wordSort;
	}
	
	var keydownEventInit = false,
		highlightIndex = 0;
	function highlightListView(value){
		bindKeyDownEvent();
		clearHighlight();
		highlightIndex = 0;
		var regExp = new RegExp(value, 'g');
		var listItem = $('.lui_profile_listview_list_itemh');
		listItem.each(function(){
			var html = $(this).html();
			$(this).html(html.replace(regExp,'<span class="highlight">'+ value +'</span>') );
		});
	}
	
	function bindKeyDownEvent(){
		if(!keydownEventInit){
			$(document).keydown(function(e){
				if(e.keyCode != 13) return;
				if(viewType != 'list') return;
				var highlights = $('.lui_profile_listview_list_itemh > .highlight');
				if(highlights.size() > 0){
					highlights.removeClass('selected');
					highlightIndex = highlightIndex >=  highlights.size() ? 0 : highlightIndex;
					var highlight = highlights[highlightIndex],
						top = getOffsetTop(highlight);
					$(highlight).addClass('selected');
					$(window).scrollTop(top - 100);
					highlightIndex++;
				}
			});
			keydownEventInit = true;
		}
	}
	
	function getOffsetTop(node){
		 var offsetParent = node;
		 var nTp = 0;
		 while (offsetParent!=null && offsetParent!=document.body) { 
			 nTp += offsetParent.offsetTop; 
			 offsetParent = offsetParent.offsetParent; 
		 } 
		 return nTp;
	}
	
	function clearHighlight(){
		$('.lui_profile_listview_list_itemh').each(function() {
			$(this).find('.highlight').each(function() {
				$(this).replaceWith($(this).html()); //将他们的属性去掉；
			});
		});
	}
	
	function contains(container,contained){
		var i = container.length;
		while (i--) {
			if (container[i] === contained) {
				return true;
			}
		}
		return false;
	}

	function showNotExistModuleAlert(data){
		console.log("不存在模块信息",data);
		dialog.alert("该模块暂无法使用，可联系相关项目人员购买开通使用");
	}

	container = createContainer();
	searchBar = createSearchBar().appendTo(container);
	if(render && render.parent && render.parent.showTip && render.parent.buildBusinessModuleTipContainer){
		render.parent.buildBusinessModuleTipContainer().appendTo(container);
	}
	appMenu = createAppMeanu().appendTo(container);
	
	if(viewType = 'grid'){
		GridView = createGridView(appMenu);
	}else{
		ListView = createListView(appMenu);
	}
	console.log("render",render)
	done(container);
});