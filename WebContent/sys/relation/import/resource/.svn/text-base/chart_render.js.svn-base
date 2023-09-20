//变量赋值
var dataView = render.parent;
var target = render.vars.target?render.vars.target:'_blank';

/**
 * 定时任务监控渲染图表的载体元素是否可见，可见的情况下触发渲染图表内容
 */
var loadChartInterval= setInterval(function(){
	var elementIsVisible = dataView.element.is(":visible"); // 图表 DataView DOM 元素是否可见(如果可见表示侧边栏处于展开状态)
    if(elementIsVisible){
    	clearInterval(loadChartInterval);
    	loadChartContent(); 
    }
},10);


/**
 * 渲染图表内容
 * @return
 */
function loadChartContent(){
	setTimeout(function(){ 
		var $containerDiv = $("<div class='relaChartContainer' />").appendTo(dataView.element);
		for(var i=0; i<data.length; i++){
			addLiRow(data[i]);
		}		
	},150);// 延迟150毫秒执行的目的是为了等待配置弹出窗口关闭之后再获取侧边栏宽度（配置弹出窗口打开时，隐藏了滚动条，屏幕内容可视区宽度会不一致）
	
}


/**
 * 添加一行图表
 * @param oneData 行数据   
 * @return
 */
function addLiRow(oneData) {
	var $containerDiv = dataView.element.find(".relaChartContainer");
	var url = Com_Parameter.ContextPath+oneData.chartUrl;
	var defaultWidth = $containerDiv.width(); // 宽度
	var defaultHeight = 400; // 默认高度
	url = Com_SetUrlParameter(url,"width",defaultWidth);
	url = Com_SetUrlParameter(url,"height",defaultHeight);
	var $chartIframe = $('<iframe frameborder="0" scrolling="no" width="100%" height="'+defaultHeight+'px" src="'+ url +'"></iframe>');
	// 绑定渲染图表的IFrame加载完成load事件
	$chartIframe.load(function(){
        var iframeDom = $chartIframe[0];
		var iframeWindow = iframeDom.contentWindow;
		var $iframeContentBody = $(iframeWindow.document).find("body");
		setInterval(function(){
			var iframeContentHeight = $iframeContentBody.outerHeight(true); // 获取图表内容页的原始高度
			if(iframeContentHeight==0){
				iframeContentHeight = defaultHeight;
			}
			$chartIframe.height(iframeContentHeight); // 设置IFrame的高度
		},10);
	});
	$containerDiv.append($chartIframe);
}

done();