var target = render.vars.target?render.vars.target:'_top';
function createFrame() {
	var frame = $('<div class="lui_dataview_treemenu2_portal2" />');
	return frame;
}

function createContainer(){
	var container = $('<div class="lui_dataview_treemenu2_portal2_lv1_all"/>');
	return container;
}

function createLv1(__data){
	var lv1Left = $('<div class="lui_dataview_treemenu2_portal2_lv1_l"/>'),
		lv1Right = $('<div class="lui_dataview_treemenu2_portal2_lv1_r"/>').appendTo(lv1Left),
		lv1Center = $('<div class="lui_dataview_treemenu2_portal2_lv1_c"/>').appendTo(lv1Right);
	//渲染标题
	var href = env.fn.formatUrl(__data.href),
		a = $('<a />').text(__data.text).attr('title',__data.text).attr('href',href).attr('target',__data.target).appendTo(lv1Center);
	
	var ul = $('<ul class="lui_dataview_treemenu2_portal2_lv2_all" />');
	var selectedPortal = false,
		hasLv2Portal = false;
	if(__data.children && __data.children.length > 0){
		for(var j = 0;j < __data.children.length ;j++){
			var _c = __data.children[j];
			_c.$parent = __data;
			createLv2(_c).appendTo(ul);
			selectedPortal = selectedPortal || _c.selected;
			if(_c.fdType && _c.fdType == 'portal'){
				hasLv2Portal = true;
			}
		}
	}
	__data.selectedPortal = selectedPortal;
	__data.hasLv2Portal = hasLv2Portal;
	__data.contentdom = lv1Left;
	__data.childdom = ul;
	return __data;
}

function createLv2(__data){
	var li = $('<li class="lui_dataview_treemenu2_portal2_lv2" />'),
		href = env.fn.formatUrl(__data.href),
		target = __data.target,
		a = $('<a />').text(__data.text).attr('title',__data.text).attr('data-portal-id',__data.fdId).appendTo(li);
	a.click(function(){
		var links = $('.lui_dataview_treemenu2_portal2_lv2 a');
		links.removeClass('selected');
		$(this).addClass('selected');
		if(__data.$parent.selectedPortal){
			if(LUI.pageMode()  == 'quick'
					&& target != '_blank'){ // 极速模式
				target = '_content';
			}
			LUI.pageQuickOpen(href,target,{ portalId : __data.fdId });
		}else{
			window.open(href,target);
		}
	});
	if(__data.selected){
		a.addClass('selected');
	}
	if(__data.fdType && __data.fdType == 'portal'){
		li.addClass('lui_dataview_treemenu2_portal2_main');
	}else{
		li.addClass('lui_dataview_treemenu2_portal2_page');
	}
	return li;
}

var frame = (function(){
	var _datas = [];
	for(var i = 0;i < data.length;i++){
		var _data = createLv1(data[i]);
		if(_data.selectedPortal){
			_datas.unshift(_data);
		}else{
			_datas.push(_data);
		}
	}
	var frame = createFrame();
	var	otherContainer = "";
	for(var i = 0;i < _datas.length;i++){
		var _data = _datas[i],
			flag = _data.selectedPortal || _data.hasLv2Portal;
		if(flag){
			container = $('<div class="lui_dataview_treemenu2_portal2_lv1_all"/>').appendTo(frame);
		}else{
			if(!otherContainer){
				otherContainer = createContainer().appendTo(frame);
				container = otherContainer;
			}
		}
		container.append(_data.contentdom);
		if(_data.selectedPortal){
			container.addClass('selectedPortal');
			container.prependTo(frame);
		}else{
			container.addClass('otherPortal');
		}
		if(_data.hasLv2Portal){
			container.addClass('hasLv2Portal');
		}
		if(flag){
			container.append(_data.childdom);
		}
		_data.contentdom = null;
		_data.childdom = null;
	}
	return frame;
})();

done(frame);

seajs.use(['lui/topic'],function(topic){
	topic.subscribe('lui.page.open',function(evt){
		if(evt && evt.features){
			var portalId = evt.features.portalId;
			if(portalId){
				var item = $('[data-portal-id="'+ portalId +'"]',frame);
				if(item.length > 0){
					$('[data-portal-id]',frame)
						.removeClass('selected');
					$(item).addClass('selected');
				}
			}
		}
	});
});
