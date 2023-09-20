var target = render.vars.target?render.vars.target:'_top';
var dataView = render.parent;
var default_app_title = null; // 默认选中的应用名称
var default_app_url = null;   // 默认选择的应用的URL
if(dataView._default_app_info){
	var appInfo = dataView._default_app_info;
	if(appInfo.default_app_title){
		default_app_title = appInfo.default_app_title;
	}
	if(appInfo.default_app_url){
		default_app_url = appInfo.default_app_url; 
	}
}

// 兼容IE8，给字符串对象添加startsWith方法
if (typeof String.prototype.startsWith !== 'function') {
    String.prototype.startsWith = function(prefix) {
        return this.slice(0, prefix.length) === prefix;
    };
}
// 兼容IE8，给字符串对象添加endsWith方法
if (typeof String.prototype.endsWith !== 'function') {
    String.prototype.endsWith = function(suffix) {
        return this.indexOf(suffix, this.length - suffix.length) !== -1;
    };
}

/**
* 创建浮动窗口展示框架载体
* @return
*/
function createFrame() {
	var frame = $('<div class="lui_dataview_navtree_app" />');
	return frame;
}


/**
* 创建数据展示分类单行容器
* @return
*/
function createContainer(){
	var container = $('<li class="lui_dataview_navtree_app_lv1_all"/>');
	return container;
}


/**
* 创建单行应用列表数据
* @param rowIndex 行索引 
* @param rowData  应用行数据
* @return
*/
function createLv1( rowIndex, rowData ){

	var lv1Left = $('<div class="lui_dataview_navtree_app_lv1_l"/>'),
		lv1Right = $('<div class="lui_dataview_navtree_app_lv1_r"/>').appendTo(lv1Left),
		lv1Center = $('<div class="lui_dataview_navtree_app_lv1_c"/>').appendTo(lv1Right);
	
	// 标题分隔图标DIV
	var separateIcon = $('<div class="lui_dataview_navtree_app_1v1_separate" />').appendTo(lv1Center);
	
    // 标题内容DIV
	var titleContent = $('<div class="lui_dataview_navtree_app_1v1_title" />').appendTo(lv1Center);
	
	// 渲染标题
	var a = $('<a />');
	a.attr('title', rowData.text);
	a.attr('href', rowData.href ? env.fn.formatUrl(rowData.href) : 'javascript:void(0)' );
	a.attr('target',rowData.target);
	$('<span class="icon" />').appendTo(a);
	$('<span class="txt" />').text(rowData.text).appendTo(a);
	a.appendTo(titleContent);

	// 创建单行应用列表ul容器，并循环子数据列表创建应用内容添加至其中
	var ul = $('<ul class="lui_dataview_navtree_app_lv2_all" />');
	if(rowData.children && rowData.children.length > 0){
		for(var j = 0;j < rowData.children.length ;j++){
			var colData = rowData.children[j];
			createLv2( rowIndex, j, rowData, colData).appendTo(ul);
		}
	}
	rowData.contentDom = lv1Left;
	rowData.childDom = ul;
	
	return rowData;
}


/**
* 创建单个应用数据
* @param rowIndex 行索引 
* @param colIndex 列索引
* @param rowData  应用行数据
* @param colData  应用列数据
* @return
*/
function createLv2( rowIndex, colIndex, rowData, colData ){
	var li = $('<li class="lui_dataview_navtree_app_lv2" />');
	
	// 添加title属性，用于使用搜索功能的时候获取进行字符串匹配
	li.attr('title', colData.text);
	li.attr('parent_title', rowData.text);
	// 添加拼音至特殊属性，便于支持拼音搜索
	(function(elm){
		seajs.use(['lui/pinyin'], function (Pinyin) {
			elm.attr('title_pinyin', Pinyin.convertToPinyin(elm.attr('title')));
		});
	})(li);
	// 将应用配置数据写入特殊属性中，便于读取
	li.attr('app_config_data', JSON.stringify(colData));

	var	a = $('<a />').attr('data-portal-id',colData.fdId).appendTo(li);

	// 计算获取图标的背景颜色样式名（五组标准颜色依次循环）
	var backgroundClass ="icon_bg_"+( (rowIndex % 5) + 1 );
	if(colData.img){
		// 创建 应用图标 元素
		var imgSrc = env.fn.formatUrl(colData.img);
		var imgSpan = $('<span class="icon img"><img alt="" alt="" src="' + imgSrc + '" /></span>').addClass(backgroundClass).appendTo(a);
	}else{
// 创建 应用图标 元素
		var iconSpan = $('<span class="icon" />').addClass(backgroundClass).appendTo(a);
		var icon = colData.icon || 'iconfont_nav';
		if(icon.indexOf('&amp;') > -1){
			iconSpan.addClass("fontmui");
			iconSpan.html(icon.replace('&amp;','&'));
		}else{
			iconSpan.addClass(icon);
		}
	}


	// 创建 应用名称 文本元素
	$('<span class="txt" />').text(colData.text).appendTo(a);

	// 绑定应用点击事件
	a.click(function(){
		doOpenPage(li);
	});

	// 当前应用添加默认选中样式
	if( default_app_title && default_app_url ){
		if( colData.text==default_app_title && colData.href==default_app_url ){
			a.addClass('app_selected');// 添加选中样式
		}
	}
	return li;
}


/**
 * 打开选中的应用页面
 * @param appDom 应用对应的DOM (HTML DOM Element对象)
 * @return
 */
function doOpenPage(appDom){
	$('.lui_dataview_navtree_app_lv2 a.app_selected').removeClass('app_selected'); // 移除上一个应用选中样式
	$(appDom).children('a').addClass('app_selected');// 添加选中样式

	var configData = $.parseJSON( $(appDom).attr('app_config_data') );
	var	href = configData.href ? env.fn.formatUrl(configData.href):'javascript:void(0)';
	var	target = configData.target;

	var usefor = render.config['for'];
	var mode = LUI.pageMode();
	var customHashParams = {"c_app_title":encodeURIComponent(configData.text),"c_app_url":encodeURIComponent(configData.href)};
	if(usefor == 'switchAppNav' && mode == 'quick' && target != '_blank'){
		if(target=='_top'){
			LUI.pageOpen(href,'_iframe', {}, customHashParams);
		}else{
			target = '_iframe';
			LUI.pageQuickOpen( href, target, {}, customHashParams);
		}
	}else{
		target = target != '_blank' ? '_top' : target; //修正target

		LUI.pageOpen( href, target, {}, customHashParams);
	}
	dataView.emit('appNavChanged',{
		channel : dataView.config.channel ?  dataView.config.channel : null,
		// text : configData.text,
		target : target,
		element: render.dataview.element
	});	
}



/**
* 响应处理搜索,展示搜索结果（根据关键字或拼音匹配，在当前浮动窗口内进行搜索）
* @param value 搜索关键字
* @return
*/
function handleNavTreeSearch(value){
	// 匹配搜索的数据对象数组
	var matchedDataArray = []; 
	
	// 搜索联想结果展示DIV
	var associationArea = $('.lui_dataview_navtree_app_searchbar_association');
	
	// 清空上一次的联想内容
	associationArea.children("div.association_item").remove();
	
	// 循环在DOM对象上获取title属性进行字符串匹配比较，并将匹配出的结果添加至matchedDataArray数组后进行显示
	if( typeof(value)!="undefined" && value!=null && $.trim(value)!="" ){
		value = $.trim(value);
		var searchKeyStr = value.toLowerCase();
		
		// 循环DOM进行比较（依次比较应用标题、应用父级分类标题、汉字拼音）
		$(".lui_dataview_navtree_app_lv2").each(function(){
			var title = $(this).attr('title') || '';
			var parentTitle = $(this).attr('parent_title') || '';
			var titlePinyin = $(this).attr('title_pinyin') || '';
			if( title.indexOf(searchKeyStr)>-1 || parentTitle.indexOf(searchKeyStr)>-1 || titlePinyin.indexOf(searchKeyStr.toLowerCase()) > -1){
				var showText = parentTitle+" - "+title;
				var appDom = this;
				matchedDataArray.push({"showText":showText,"appDom":appDom});
			}
		});		
		
		// 显示匹配出的联想内容，并设置匹配的搜索关键字高亮显示
		if(matchedDataArray.length>0){
			$('.lui_dataview_navtree_app_tip').hide();
			for(var i = 0; i < matchedDataArray.length; i++){
				var matchedData = matchedDataArray[i];
				var itemText = matchedData.showText;
				
				// 构建高亮的关键字HTML DOM
				var keywordSpan = $('<span class="association_item_keyword"></span>').text(value);
				
				// 替换完整字符串中的匹配文字，修改为高亮的关键字HTML
				itemText = itemText.replace( new RegExp(value,'g'), keywordSpan.prop("outerHTML"));
				
				// 构建应用联想结果明细行DIV
				var $item = $('<div class="association_item" ></div>').html(itemText);
				
				// 绑定应用点击事件
				$item.click(matchedData,function(event){
					var appDom = event.data.appDom;
					doOpenPage(appDom);
				});
				
				$item.appendTo(associationArea);
			}
		}else{
			var txtValue = value;
			if(value.length>20){
				txtValue = value.substring(0,19)+"..."; // tip提示时，超过20个字符长度的部分显示省略号
			}
			$('.lui_dataview_navtree_app_tip .txtValue').text(txtValue);
			$('.lui_dataview_navtree_app_tip').show();
		}
		// 显示联想内容
		associationArea.show();
	}else{
		// 隐藏联想内容
		associationArea.hide();
	}

}

/**
* 创建搜索框，并绑定相应事件
* @return
*/
function createSearchBar(){
	var container = $('<div class="lui_dataview_navtree_app_searchbar"/>');
	var containerWrap = $('<div class="lui_dataview_navtree_app_searchbar_wrap"/>').appendTo(container);
	var searchBar = $('<input maxlength="100" />').appendTo(containerWrap);
	seajs.use('lang!sys-portal',function(lang){
		// 添加placeholder提示：请输入应用名称或关键字
		searchBar.attr('placeholder',lang['header.navApp.search.tip']); 
	});
	var searchIcon = $('<span/>').appendTo(containerWrap);
	var deleteIcon = $('<i class="deleteIcon"/>').appendTo(containerWrap);
	
	// 搜索联想结果展示区域
	var associationArea = $('<div class="lui_dataview_navtree_app_searchbar_association"/>').appendTo(container);
	// 创建查询无记录时的tip提醒
	createTip().appendTo(associationArea);

	$('.lui_tlayout_header_app').click(function(){
		searchBar.focus();
		searchBar.val('');
		deleteIcon.hide();
		handleNavTreeSearch('');
	});
	
	searchBar.on('input propertychange', function(){
		var value = $(this).val();
		if (value) {
			deleteIcon.show();
		} else{
			deleteIcon.hide();
		}
		handleNavTreeSearch(value);
	});
	
	searchBar.keypress(function(){
		var value = $(this).val();
		handleNavTreeSearch(value);
	});
	
	searchIcon.on('click', function(){
		var value = searchBar.val();
		handleNavTreeSearch(value);
	});
	
	deleteIcon.on('click', function(){
		searchBar.focus();
		searchBar.val('');
		handleNavTreeSearch('');
		deleteIcon.hide();
	});
	
	return container;
}

/**
*  创建查询无记录时的tip提醒DIV
*  提醒内容： 未找到与 “XXX” 相关的应用
* @return
*/
function createTip() {
	var container = $('<div class="lui_dataview_navtree_app_tip" style="display: none"/>');
	var containerContent = $('<div class="lui_dataview_navtree_app_tip_content"/>').appendTo(container);
	var beforeText = $('<span/>').appendTo(containerContent);
	var txtValue = $('<span class="txtValue" />').appendTo(containerContent);
	var afterText = $('<span/>').appendTo(containerContent);
	
	seajs.use('lang!sys-portal',function(lang){
		beforeText.text(lang['header.navApp.search.tip.beforeText']);
		afterText.text(lang['header.navApp.search.tip.afterText']);
	});
	
	return container;
}


var frame = (function(){
	var _datas = [];
	for(var i = 0;i < data.length;i++){
		var _data = createLv1(i,data[i]);
		_datas.push(_data);
	}
	
	// 创建浮动窗口展示框架载体
	var frame = createFrame();
	
	// 新增搜索框
	createSearchBar().appendTo(frame);

	// 鼠标移出浮动窗口时清空搜索结果
	frame.hover(function(){
		if(frame.searchbarTimer){
			clearTimeout(frame.searchbarTimer);
			frame.searchbarTimer = null;
		}
	}, function(){
		frame.searchbarTimer = setTimeout(function(){
			$('.lui_dataview_navtree_app_searchbar input').val('');
			$('.lui_dataview_navtree_app_searchbar .deleteIcon').hide();
			handleNavTreeSearch('');
		}, 1000);
	});
	
	var appContent = $('<div class="lui_dataview_navtree_app_content" />').appendTo(frame);
	
	// 循环创建“应用”数据展示行
	for(var i = 0;i < _datas.length;i++){
		var _data = _datas[i],
		container = createContainer().appendTo(appContent);
		container.append(_data.contentDom);
		container.append(_data.childDom);
		_data.contentDom = null;
		_data.childDom = null;
	}
	return frame;
})();

done(frame);
