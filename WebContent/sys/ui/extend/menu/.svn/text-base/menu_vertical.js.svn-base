var topDom = null;
var contentDom = null;
var cssExtend = "";



var criProps;

//当前选中id
var __selectedId = "";

var isKeepCriParameter = true;


if(param!=null ){
	if(param.extend!=null){
		cssExtend = "_" + param.extend;
	}
}
function createMenuFrame(){
	topDom = $("<div class='lui_menu_frame_ver"+cssExtend+"'></div>");
	contentDom = $("<div class='lui_menu_left'></div>").appendTo(topDom);
	contentDom = $("<div class='lui_menu_right'></div>").appendTo(contentDom);
	contentDom = $("<div class='lui_menu_content'></div>").appendTo(contentDom);
}
function createLayout(html){
	var tr = $("<tr lui-button-container='1'/>");
	$("<td/>").append(html).appendTo(tr);
	return tr;
}
function getLayoutTable(){
	var tableObj = contentDom.find("table");
	if(tableObj.length<=0){
		tableObj = $("<table/>").appendTo(contentDom);
	}
	return tableObj;
}
seajs.use(['lui/popup','lui/menu'],function(popup,menu){

	window.createItem = function (item){
		var itemTop = $("<div class='lui_menu_item'></div>");
		item.element.appendTo(itemTop);
		var itemContent = $("<div class='lui_item_left' data-mid='"+item.config.value+"'></div>").appendTo(item.element);
		itemContent = $("<div class='lui_item_right'></div>").appendTo(itemContent);
		itemContent = $("<div class='lui_item_content'></div>").appendTo(itemContent);
		if(item.triggerObject!=null){
			itemContent.append(item.triggerObject);
		}else{
			if(item.icon){
				if(item.icon.indexOf('&#') == 0){
					itemContent.append($("<i class='fontmui'>"+item.icon+"</i>"));
				}
				else if(item.icon.indexOf('lui_iconfont') == 0){
					itemContent.append($("<div class='iconfont "+item.icon+"'></div>"));
				}
				else{
					itemContent.append($("<div class='lui_icon_s "+item.icon+"'></div>"));
				}
			}
			if(item.text){
				itemContent.append($("<div class='lui_item_txt' title='"+item.text+"'>"+item.text+"</div>"));
			}
			item.element.attr("data-lui-switch-class",'lui_icon_on');
		}


		if(item.children.length>0){
			if(item.href==""&&item.icon==undefined&&item.children[0].text==undefined){

			}
			else if(item.triggerObject == null){
				item.tiggerObj = $("<div class='lui_item_tigger'></div>");
				var signObj = $("<div class='lui_item_sign'></div>");
				item.tiggerObj.append(signObj);
				itemContent.append(item.tiggerObj);
			}
			if(item instanceof menu.MenuItem){
				var tmpmenu = menu.buildMenu(item.children,{
					"type": "Javascript",
					"param":{"extend":"pop"},
					"src":"/sys/ui/extend/menu/menu_vertical.js"
				});
				if(item.config.key) {
					tmpmenu.config['key'] = item.config.key;
				}
				var containerDiv = $('<div>').attr('class', 'lui_menu_popup_ver'+cssExtend);
				containerDiv.append(tmpmenu.element);
				var parentPop = layout.menu.popup;
				var cfg = null;
				if(parentPop==null){
					cfg = {"align":"right-top"};
				}else{
					var nAlign = parentPop.overlay.position.align;
					var oalign = parentPop.overlay.position.oalign;
					if(nAlign && nAlign!=oalign){
						cfg = {"parent":parentPop,"align":nAlign};
					}else{
						cfg = {"parent":parentPop,"align":"right-top"};
					}
				}
				if(item.tiggerObj){
					cfg["triggerObject"]= item.element;
				}
				var pp = popup.build(item.element , containerDiv, cfg);
				tmpmenu.popup = pp;
				pp.addChild(tmpmenu);
				item.onErase(function(){pp.destroy();tmpmenu.destroy();});
			}
		}
		return itemTop;
	}
});
function addItem(data){
	var newItemTr = createLayout(createItem(data.item));
	var layoutTab = getLayoutTable(); 
	if(data.posItem!=null){
		var trObj = data.posItem.element.parents('tr[lui-button-container="1"]');
		if(data.isBefore){
			trObj.before(newItemTr);
		}else{
			trObj.after(newItemTr);
		}
	}else{
		newItemTr.appendTo(layoutTab);
	}
	//设置滚动条
	//弹出层Dom
	
	var popDom = topDom.parents('.lui_popup_border_content');
	//弹出层位置
	var top = popDom.length>0 ? (popDom.position().top - $(document.body).scrollTop()): 0;
	var height = popDom.height() && contentDom.height()< popDom.height() ? popDom.height() : contentDom.height();
	//可视窗口高度
	var minHeight = 100;
	var _____height = getClientHeight();
	if (height >= _____height) {
		var dom = popDom.length>0 ? popDom : contentDom;
		var newHeight = (popDom.length > 0 && (_____height-top) > 0 ) ? (_____height-top) : _____height;
		// 当得到的高度比最小高度还要小时，高度设置为可视窗口高度
		newHeight = (height > minHeight && minHeight > newHeight) ? _____height : newHeight;
		dom.css('overflow-y', 'scroll');
		dom.css('overflow-x', 'hidden');
		dom.css('white-space', 'nowrap');
		dom.css('max-height', '400px');
		dom.css('height', newHeight);
	}
}
function getClientHeight() {
	if (window.innerHeight)
		return window.innerHeight;
	return $(window).height();
}
function redrawItem(data){
	var newItemTr = createLayout(createItem(data.item));
	if(data.posItem!=null){
		var trObj = data.posItem.element.parents('tr[lui-button-container="1"]');
		trObj.after(newItemTr);
		trObj.remove();
	}
}
function redrawPopItem(data){
	if(data.popupItem!=null){
		var itemEle = data.popupItem.element;
		if(itemEle!=null){
			var sign = itemEle.find(".lui_item_sign");
			sign.attr("class","lui_item_sign_dis");
		}
	}
}
function removeItem(data){
	if(data.item!=null){
		var trObj = data.item.element.parents('tr[lui-button-container="1"]');
		trObj.remove();
	}
}
var items = layout.menu.children;
if(items.length>0){
	createMenuFrame();
	for(var i=0;i<items.length;i++){
		if(items[i]!=layout)
			addItem({"item":items[i]});
	}
}
layout.menu.on("addItem",addItem);
layout.menu.on("redrawItem",redrawItem);
layout.menu.on("removeItem",removeItem);
layout.menu.on("popupItemHide",redrawPopItem);
layout.menu.onErase(function(){
	layout.menu.off('addItem', addItem);
	layout.menu.off('redrawItem', redrawItem);
	layout.menu.off('removeItem', removeItem);
	layout.menu.off("popupItemHide",redrawPopItem);
	
});


done(topDom);


seajs
	.use(
		[
			'lui/util/str',
			'lui/jquery',
			'lui/popup',
			'lui/topic',
			'lui/spa/const',
			'sys/ui/extend/dataview/render/cateAll/cate',
			'lui/spa/Spa',
			'lui/framework/router/router-utils' ],
		function(
			strutil,
			$,
			popup,
			topic,
			spaConst,
			cate,
			Spa,
			routerUtils) {

			criProps = criProps ? strutil.toJSON(criProps) : '';

			var router = routerUtils.getRouter();

			// 是否启用路由模式
			if (router) {
				router.addRoutes({

					path : '/docCategory',
					action : function(value) {
						if(isKeepCriParameter){
							topic.publish(spaConst.SPA_CHANGE_ADD, {
								value : value,
								target : this
							});
						}else{
							topic.publish(spaConst.SPA_CHANGE_RESET, {
								value : value,
								target : this
							});
						}

						topic.publish("nav.operation.clearStatus",null);
					}

				});

				var categoryId = Com_GetUrlParameter(location.href,'categoryId');
				if (categoryId) {

					var data = {
						'docCategory' : categoryId,
						'j_path' : '/docCategory'
					};

					if (criProps) {
						$.extend(data, criProps);
					}
					if (LUI.luihasReady) {
						router.push('/docCategory', data);
					} else {
						LUI.ready(function() {
							router.push('/docCategory', data);
						});
					}

				}

			}})

