var target = render.vars.target ? render.vars.target : '_blank';
var extend = param.extend == null ? '' : '_' + param.extend;
var parent = render.parent;
var element = parent.element;
var width = parent.element.width();

//绘制标题
var drawTitle = function(prefix, oneData ,flag){
	var $domL = $('<div />').addClass('lui_dataview_' + prefix + '_l'),
		$domR = $('<div/>').addClass('lui_dataview_' + prefix + '_r').appendTo($domL),
		$dom = $('<div/>').addClass('lui_dataview_' + prefix + '_c').appendTo($domR);
	if(flag){
		$dom.addClass('$lui_dataview_' + prefix + '_first');
	}
	$dom.append(drawHref(oneData));
	return $dom;
};

//绘制HREF
var drawHref = function(oneData){
	var $dom = null,
		text = env.fn.formatText(oneData.text),
		href = env.fn.formatUrl(oneData.href),
		target = $.trim(oneData.target) != '' ? oneData.target : target,
		pageType = oneData.pageType;
	if(oneData.href != null && $.trim(oneData.href) != ""){
		var clz = ($.trim(oneData.selected + '') != '' && $.trim('' + oneData.selected) == 'true' ) ?  'selected' : '';
		$dom = $('<a/>').attr('title', text).html(text).addClass(clz);
		$dom.on('click',function(){
			var usefor = render.config['for'],
				mode = LUI.pageMode();
			if(usefor == 'switchPage' 
					&& mode == 'quick' 
					&& target != '_blank'){ //极速模式且二级树用于切换页面时调用LUI.pageOpen....bad hack
				if(pageType == '1'){
					target = '_content';
				}else{
					target = '_iframe';
				}
				LUI.pageOpen(href, target);
				// 事件在跳转选中门户路径之后触发
				parent.emit('treeMenuChanged',{
					channel : parent.config.channel ?  parent.config.channel : null,
					text : text,
					target: target,
					element:this
				});				
			}else{
				// 事件在弹出门户新窗口之前触发
				parent.emit('treeMenuChanged',{
					channel : parent.config.channel ?  parent.config.channel : null,
					text : text,
					target: target,
					element:this
				});	
				setTimeout(function(){
					window.open(href, target); // 注：当点击的门户配置为弹出新窗口时，不修改当前门户的显示名称（即不触发treeMenuChanged事件）				
				},100);

			}
		});
	}else{
		$dom = $('<span/>').attr('title', text).html(text);
	}
	return $dom
};

var drawFrame = function(){
	var clz = 'lui_dataview_treemenu2' + extend;
	var $dom = $('<div/>').addClass(clz);
	for(var i = 0; i < data.length; i++){
		var lv1 = data[i];
		var $lv1 = null;
		if(i < 1){
			$lv1 = $('<div/>')
					.addClass('lui_dataview_treemenu2' + extend + '_lv1_all')
					.appendTo($dom);
		}
		var hasChildren = lv1.children ? true : false;
		if(i >= 1){
			var __hasChildren = data[i - 1].children ? true : false;
			if((__hasChildren && data[i - 1].children.length > 0) || (hasChildren && lv1.children.length > 0)){
				$lv1  = $('<div/>')
						.addClass('lui_dataview_treemenu2' + extend + '_lv1_all')
						.appendTo($dom);
			}
		}
		var innerNode = $lv1 ? $lv1 : $dom;
		
		if(!hasChildren || lv1.children.length==0){
			var $title = drawTitle("treemenu2" + extend + "_lv1", lv1).appendTo(innerNode);
		}
			
		if(hasChildren && lv1.children.length > 0){
			var $title = drawTitle("treemenu2" + extend + "_lv1", lv1, true).appendTo(innerNode),
				$ul = $('<ul />').addClass('lui_dataview_treemenu2' + extend + '_lv2_all');
			$ul.appendTo(innerNode);
			if(width > 0){
				$ul.css({ width : (width -120) + 'px' });
			}
			for(var j=0; j < lv1.children.length; j++){
				var lv2 = lv1.children[j],
					$li = $('<li/>').append(drawHref(lv2));
					$li.appendTo($ul);
			}
		}
	}
	return $dom;
};

done(drawFrame());