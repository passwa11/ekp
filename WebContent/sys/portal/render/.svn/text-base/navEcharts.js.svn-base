//js  兼容各浏览器



var target = render.vars.target?render.vars.target:'_top';
//var parent = render.parent;




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
	var frame = $('<div class="lui_dataview_navEcharts_app" />');
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
* @param rowData  应用行数据
* @return
*/
function createLv1(  rowData ){
    
	var lv1Left = $('<div class="lui_dataview_navtree_app_lv1_l"/>'),
		lv1Right = $('<div class="lui_dataview_navtree_app_lv1_r"/>').appendTo(lv1Left),
		lv1Center = $('<div class="lui_dataview_navtree_app_lv1_c"/>').appendTo(lv1Right);
	
	// 标题分隔图标DIV
	var separateIcon = $('<div class="lui_dataview_navtree_app_1v1_separate" />').appendTo(lv1Center);
	
    // 标题内容DIV
	var titleContent = $('<div class="lui_dataview_navtree_app_1v1_title" />').appendTo(lv1Center);
	
	// 渲染标题
	var a = $('<a />');
	a.attr('title', rowData.fdName);
	a.attr('href',  'javascript:void(0)' );

	$('<span class="icon" />').appendTo(a);
	$('<span class="txt" />').text(rowData.fdName).appendTo(a);
	a.appendTo(titleContent);

	// 一级类别图表
	var ul = $('<ul class="lui_dataview_navtree_app_lv2_all" />');
	if(rowData.dbEchartsTotalForms && rowData.dbEchartsTotalForms.length > 0){
		for(var z = 0;z <  rowData.dbEchartsTotalForms.length ;z++){
			var colData =  rowData.dbEchartsTotalForms[z];
			createLv2(  z, rowData, colData).appendTo(ul);
		}
	}
	rowData.contentDom = lv1Left;
	rowData.childDom = ul;
	
	return rowData;
}


/**
* 创建单个应用数据
* @param colIndex 列索引
* @param rowData  应用行数据
* @param colData  应用列数据
* @return
*/
function createLv2(  colIndex, rowData, colData ){
	var li = $('<li class="lui_dataview_navtree_app_lv2" />');
	
	// 添加title属性，用于使用搜索功能的时候获取进行字符串匹配
	li.attr('title', colData.docSubject);
	li.attr('parent_title', rowData.fdName);
    li.attr("echart_type",colData.echartType)

	var	a = $('<a />').attr('data-portal-id',colData.fdId).appendTo(li);
	
	// 创建 应用图标 元素
	var iconSpan = $('<span class="icon" />').appendTo(a);
	// 创建 应用名称 文本元素
	$('<span class="txt" />').text(colData.docSubject).appendTo(a);
	
	// 绑定应用点击事件
	/*a.click(function(){
		doOpenPage(li);
	});*/

	return li;
}


/**
* 打开选中的应用页面
* @param appDom 应用对应的DOM (HTML DOM Element对象) 
* @return
*/
function doOpenPage(appDom){
	var dialogH = parent.document.documentElement.clientHeight;
	var dialogW = parent.document.documentElement.clientWidth;
	console.log("dialogH:"+dialogH);
	console.log("dialogW:"+dialogW);
	dialogW=dialogW-dialogW*0.1*2;
	dialogH=dialogH-dialogH*0.05*2;
	console.log("dialogH:"+dialogH);
	console.log("dialogW:"+dialogW);
	
	$('.lui_dataview_navtree_app_lv2 a.app_selected').removeClass('app_selected'); // 移除上一个应用选中样式
	$(appDom).children('a').addClass('app_selected');// 添加选中样式
    var fdId=$($(appDom).children('a')[0]).attr("data-portal-id");
    var echart_type=$(appDom).attr("echart_type");

	/*
    var echart_type=$(appDom).attr("echart_type");
    var child_a=$(appDom).children('a');
    var fdId="";
    for(var i=0;i<child_a.length;i++){
        fdId=child_a[i].attr('data-portal-id');
        if(fdId!=""){
        	break;
		}
	}*/

    var customUrl="/dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do?method=view&fdId="+fdId;
    var chartUrl="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=view&fdId="+fdId;
    var tableUrl="/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=view&fdId="+fdId;
    var setUrl="/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=view&fdId="+fdId;

	if(echart_type=="1"){
		urlParm=customUrl;
	}else if(echart_type=="2"){
		urlParm=chartUrl;
	}else if(echart_type=="3"){
		urlParm=tableUrl;
	}else if(echart_type=="4"){
		urlParm=setUrl;
	}else{
        urlParm="";
	}
	
    seajs.use(['lui/dialog','lui/jquery'], function(dialog, $){
        dialog.build({
            config : {
                width : dialogW,
                height : dialogH,
                title : Data_GetResourceString("dbcenter-echarts:module.dbcenter.piccenter"),
                content : {
                    type : "iframe",
                    url : urlParm
                }
            },
            callback :  function(value, dialog) {}
        }).show();
    });


	/*var	href = configData.href ? env.fn.formatUrl(configData.href):'javascript:void(0)';
	var	target = configData.target;
	
	var usefor = render.config['for'];
	var mode = LUI.pageMode();
	if(usefor == 'switchAppNav' && mode == 'quick' && target != '_blank'){
		if(target=='_top'){
			LUI.pageOpen(href,'_iframe');
		}else{
			target = '_iframe';
			LUI.pageQuickOpen( href, target, { portalId:configData.fdId } );
		}
	}else{
		target = target != '_blank' ? '_top' : target; //修正target
		if(mode!='quick'){
			href = addAppNameParamToURL(href,configData.text);
		}
		LUI.pageOpen(href,target);
	}*/


}

/**
* 往URL中追加“应用”名称参数 j_app_title（使得非极速模式下切换应用时，可以获取该参数并保持显示选择的应用名称）
* @param href      URL地址
* @param appTitle  应用名称
* @return 返回追加过j_app_title参数的URL地址
*/
function addAppNameParamToURL(href,appTitle){
    if(href.startsWith("/")==false || !appTitle){
 	   return href;
 	}
 	var appHref="";
 	var encodeAppTitle = encodeURIComponent(appTitle);
 	var paramStartIndex = href.indexOf("?");
 	if(paramStartIndex!=-1){
 		paramStartIndex = paramStartIndex+1;
 		var startHref = href.substring(0,paramStartIndex);
 		var endHref = href.substring(paramStartIndex);
 		appHref = startHref+"j_app_title="+encodeAppTitle+(endHref?("&"+endHref):"");
 	}else{
 		var hashParamStartIndex = href.indexOf("#");
 		if(hashParamStartIndex!=-1){
 			var startHref = href.substring(0,hashParamStartIndex);
 			var endHref = href.substring(hashParamStartIndex);
 			appHref = startHref+"?j_app_title="+encodeAppTitle+endHref;
 		}else{
 			appHref = href+"?j_app_title="+encodeAppTitle
 		}
 	}	
    return appHref;
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
		
		// 循环DOM进行比较
		$(".lui_dataview_navtree_app_lv2").each(function(){
			var title = $(this).attr('title') || '';
			var parentTitle = $(this).attr('parent_title') || '';
			if( title.indexOf(searchKeyStr)>-1 || parentTitle.indexOf(searchKeyStr)>-1 ){
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
		searchBar.attr('placeholder',lang['header.navEchart.search.tip']); 
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
	var resultData=[];
	if(data.resultCode=="1"){
        resultData=data.data;//接口正常调用返回
	}

	var _datas = [];
	for(var i = 0;i < resultData.length;i++){

		var _data = createLv1(resultData[i]);
		_datas.push(_data);

		if(resultData[i].echartsTemplateFormChildrens&&resultData[i].echartsTemplateFormChildrens.length>0){
           	  for(var j=0;j<resultData[i].echartsTemplateFormChildrens.length;j++){
                  var _data1 = createLv1(resultData[i].echartsTemplateFormChildrens[j]);
                  _datas.push(_data1);
			  }
		}
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

$(".lui_dataview_navtree_app_content").hide();



