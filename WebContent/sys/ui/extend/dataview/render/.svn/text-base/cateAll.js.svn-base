var ___data = data;

var ___href = render.vars.href, ___target = render.vars.target;

var element = render.parent.element;
var __render = render;

var criProps = __render.cfg.criProps;
var isKeepCriParameter = "false" == __render.cfg.isKeepCriParameter ? false : true;

//当前选中id
var __selectedId = "";

function createItem_l() {

	var item__l = $("<div class='lui_dataview_cate_item_left' />");
	return item__l;
}

function createItem_r() {

	var item__r = $("<div class='lui_dataview_cate_item_right' />");
	return item__r;
}

function createItem_c() {

	var item__c = $("<div class='lui_dataview_cate_item_content'/>");
	return item__c;
}

function createItem_s() {

	var item__s = $("<div class='lui_dataview_cate_item_sign'>"
			+ "<i class='lui_dataview_cate_item_sign_mtr1'></i>"
			+ "<i class='lui_dataview_cate_item_sign_mtr2'></i>" + "</div>");
	return item__s;
}

function createItem_t() {

	var item__t = $("<div class='lui_dataview_cate_item_txt' />");
	return item__t;
}

function createFrame() {

	var frame = $("<div class='lui_dataview_cate'/>");
	return frame;
}

function createBox() {

	var box = $('<ul class="lui_dataview_cate_all_box" />');
	return box;
}
var __frame = createFrame();
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

					}

					var __box = createBox();
					__frame.append(__box);

					var modelName = __render.cfg.modelName;
					var currentId = Spa.spa.getValue('docCategory');
					if (currentId) {
						$.ajax({
									url : env.fn.formatUrl('/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=path&modelName='
													+ modelName
													+ '&categoryId='
													+ currentId + '&authType=2'),
									dataType : 'json',
									success : function(datas) {
										
										buildPopup(strutil, popup, topic,
												spaConst, cate, __box,
												modelName, router, currentId,
												datas, router);
									}
								});
					} else {
						buildPopup(strutil, popup, topic, spaConst, cate,__box, modelName, router);
					}
	
				});

seajs
.use(
		[
		 		'lui/util/str',
				'lui/jquery',
				'lui/popup',
				'lui/topic',
				'lui/spa/const',
				'sys/ui/extend/dataview/render/cateAll/cateSearch',
				'sys/ui/extend/dataview/render/cateAll/triggerSearch'],
		function(
				strutil,
				$,
				popup,
				topic,
				spaConst,
				cate,trigger) {
			
			//搜索
			var __box = createBox();
			__frame.append(__box);
			var modelName = __render.cfg.modelName;
			topic.subscribe('navSearchCategory', function() {
				var name=$("#nav_search").val();
				if(name != ""){
					$.ajax({
						url : env.fn.formatUrl('/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=select&modelName='
										+ modelName
										+ '&searchText='
										+ encodeURIComponent(name) + '&authType=2&type=03&qSearch=true'),
						dataType : 'json',
						success : function(datas) {
							buildSearchPopup(strutil,topic,popup,spaConst,__box,cate,datas[0],trigger);
						}
					});
				}
				
				
			});
			
			topic.subscribe(spaConst.SPA_CHANGE_VALUES, function(evt) {
				var currentId=evt.value.docCategory;
				var modelName = __render.cfg.modelName;
				selectCurrent(currentId,strutil);
			});
		});


function buildSearchPopup(strutil,topic,popup,spaConst,__box,cate,datas,trigger) {
	var modelName = __render.cfg.modelName;
	var configs = {
			datas : datas || [],
			modelName:modelName,
			isKeepCriParameter:isKeepCriParameter
		}
	var search_div=$('.lui_dataview_search_wrap');
	
	//criProps = criProps ? strutil.toJSON(criProps) : '';
	if (criProps) {
		$.extend(configs, criProps);
	}
	
	var _cate = new cate.SearchCate(configs);
	
	$(".lui_dataview_cate_popup_search").remove();
	
	var popDiv = $('<div>').attr('class', 'lui_dataview_cate_popup');
	popDiv.addClass('lui_dataview_cate_popup_search');
	var _____height = $(window).height();
	popDiv.css('max-height', _____height);
	popDiv.append(_cate.element);
	var cfg = {
		"align" : "down-left"
	};
	var icon_search=$('.lui_icon_search');
	
	cfg.builder={"trigger":new trigger.HoverTriggerSearch({"element":icon_search,"position":popDiv})};

	if (__render.parent.parent)
		cfg.parent = __render.parent.parent;
	
	
	var pp = popup.build(search_div, popDiv, cfg);
	_cate.popup = pp;
	pp.addChild(_cate);
	outerWheelDisable(pp.element);
	//if (datas.length > 0) {
		popDiv.css('width', 600);
		popDiv.css('overflow-y', 'auto');

	//} else {
	//	popDiv.css('width', 0);
	//	popDiv.css('overflow', 'hidden');
	//	redrawPopItem({
	//		___ : li
	//	});
	//}
	
	pp.overlay.show();
	pp.overlay.trigger.show=true;
	
	(function(pp) {
		return function() {
			topic.subscribe(spaConst.SPA_CHANGE_ADD, function() {
				pp.overlay.trigger.emit('mouseout', {
					timer : 1
				});
			});
		}();
	}(pp));
	
}
function buildPopup(
		strutil,
		popup,
		topic,
		spaConst,
		cate,
		__box,
		modelName,
		router,
		currentId,
		path) {
	for (var i = 0; i < ___data.length; i++) {

		// 文本
		var __t = createItem_t();
		__t.html('<i class="iconfont lui_iconfont_dotted"></i>'
				+ env.fn.formatText(___data[i].text));
		__t.attr('title', strutil.decodeHTML(___data[i].text));
		var __l = createItem_l();
		var __r = createItem_r();
		var __c = createItem_c();
		var __s = createItem_s();
		__l.append(__r.append(__c.append(__t).append(__s)));

		var li = $('<li></li>');
		
		var div = $('<div data-lui-nodeid="'+___data[i].value+'" data-lui-switch-class="lui_icon_on" class="lui_dataview_cate_item"/>');
		
		~~function(i) {

			return function() {

				div.click(function(evt) {
					

					if (router) {
						
						var cri ={
							'j_path':'/docCategory',
							'docCategory' : data[i]['value']
						};

						if (criProps) {
							$.extend(cri, criProps);
						}
							
						router.push('/docCategory', cri)

					} else {

						topic.publish(spaConst.SPA_CHANGE_ADD, {
							value : {
								'docCategory' : data[i]['value']
							}
						});
					}

				});
			}();
		}(i);
		li.append(div.append(__l));
		__box.append(li);

		var datas = ___data[i].children;

		var configs = {
			datas : datas || [],
			modelName : modelName,
			currentId : currentId,
			criProps : criProps
		}

		if (path && path.length > 1) {

			if (___data[i].value == path[0].value) {
				configs.path = path;
			}
		}

		var _cate = new cate.Cate(configs);
		
		var popDiv = $('<div>').attr('class', 'lui_dataview_cate_popup');
		var _____height = $(window).height();
		popDiv.css('max-height', _____height);
		popDiv.append(_cate.element);
		var cfg = {
			"align" : "right-top"
		};

		if (__render.parent.parent)
			cfg.parent = __render.parent.parent;

		var pp = popup.build(div, popDiv, cfg);
		_cate.popup = pp;
		
		pp.addChild(_cate);
		outerWheelDisable(pp.element);
		if (datas.length > 0) {

			popDiv.css('width', 600);
			popDiv.css('overflow-y', 'auto');

		} else {
			popDiv.css('width', 0);
			popDiv.css('overflow', 'hidden');
			redrawPopItem({
				___ : li
			});
		}

		(function(pp) {
			return function() {

				topic.subscribe(spaConst.SPA_CHANGE_ADD, function() {

					pp.overlay.trigger.emit('mouseout', {
						timer : 1
					});
				});
			}();
		}(pp));
		
	}
	
	topic.subscribe(spaConst.SPA_CHANGE_RESET, function() {
		$("div").removeClass("lui_icon_on_select");
		$(".lui_dataview_cate_sub_item").parent().remove();
		
	});
	
	if(typeof(currentId) != 'undefined'&&currentId!=''){
		selectCurrent(currentId,strutil);
	}
}


function selectCurrent(currentId, strutil) {
	if(typeof(currentId) == 'undefined'||currentId=='') {
		__selectedId = "";
		return;
	}
	if(__selectedId == currentId) {
		return;
	}
	__selectedId = currentId;
	
	var modelName = __render.cfg.modelName;
	$("div").removeClass("lui_icon_on_select");
	$.ajax({
		url : env.fn.formatUrl("/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=path&modelName="+modelName+"&categoryId="+currentId+"&authType=2"),
		dataType : 'json',
		success : function(pathDatas) {
			if(pathDatas.length==0)
				return;
			var currentParentId=pathDatas[0].value;
			
			var lui_nodeid=$(".lui_dataview_cate_all_box").find('[data-lui-nodeid="'+currentParentId+'"]');
			lui_nodeid.addClass("lui_icon_on_select");
			
			$(".lui_dataview_cate_sub_item").parent().remove();
			$.ajax({
				url : env.fn.formatUrl("/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=currentCate&modelName="+modelName+"&level=1&authType=2&currId="+currentId),
				dataType : 'json',
				success : function(indexDatas) {
					var currentText=indexDatas[0].text;
					var selectId=indexDatas[0].value;
					var hierarchyId=indexDatas[0].hid;
					if(currentParentId==selectId)
						return;
					var __t = createItem_t();
					__t.html('<i class="iconfont lui_iconfont_dotted"></i><i class="lui_icon_s lui_icon_s_subLine"></i>'
							+ env.fn.formatText(indexDatas[0].text));
					__t.attr('title', strutil.decodeHTML(indexDatas[0].text));
					var __l = $("<div class='lui_dataview_cate_item_left' />");
					var __r = $("<div class='lui_dataview_cate_item_right' />");
					var __c = $("<div class='lui_dataview_cate_item_content'/>");
					var __s = $("<div class='lui_dataview_cate_item_sign'></div>");
					__l.append(__r.append(__c.append(__t).append(__s)));
					
					var li = $('<li></li>');
					var div = $('<div data-lui-switch-class="lui_icon_on" class="lui_dataview_cate_item lui_icon_on lui_dataview_cate_sub_item lui_icon_on_select"/>');
					
					li.append(div.append(__l));
					
					lui_nodeid.parent().after(li);
					
					// #131554 当前选中菜单也需要弹层提示
					seajs.use(['lui/jquery','lui/popup','lui/topic','lui/spa/const', 'sys/ui/extend/dataview/render/cateAll/cate'],
							function($,popup,topic,spaConst,cate) {
						var __obj__ = getAllData(hierarchyId);
						var configs = {
							datas : indexDatas,
							modelName : modelName,
							currentId : selectId,
							criProps : criProps,
							buildCurr: true,
							current: __obj__.self,
							path: __obj__.path,
							childs: __obj__.children
						}
						var _cate = new cate.Cate(configs);
						var popDiv = $('<div>').attr('class', 'lui_dataview_cate_popup');
						var _____height = $(window).height();
						popDiv.css('max-height', _____height);
						popDiv.append(_cate.element);
						var cfg = {
							"align" : "right-top"
						};
						if (__render.parent.parent) {
							cfg.parent = __render.parent.parent;
						}
						var pp = popup.build(div, popDiv, cfg);
						_cate.popup = pp;
						pp.addChild(_cate);
						outerWheelDisable(pp.element);
						if (indexDatas.length > 0) {
							popDiv.css('width', 600);
							popDiv.css('overflow-y', 'auto');
						} else {
							popDiv.css('width', 0);
							popDiv.css('overflow', 'hidden');
							redrawPopItem({
								___ : li
							});
						}
				
						(function(pp) {
							return function() {
								topic.subscribe(spaConst.SPA_CHANGE_ADD, function() {
									pp.overlay.trigger.emit('mouseout', {
										timer : 1
									});
								});
							}();
						}(pp));
					});
		
				}
			});
		}
	});
}

// 根据当前菜单获取该菜单的层级，子菜单
function getAllData(hid) {
	var self = {};
	var path = [{}];
	var children = [];
	var hids = hid.split("x");
	var ids = [];
	// 过滤空字符
	for(var i=0; i<hids.length; i++) {
		var id = hids[i];
		if(id.length > 0) {
			ids.push(id);
		}
	}
	var _datas = [];
	// 操作对象深度拷贝
	$.extend(true, _datas, ___data);
	for(var i=0; i<ids.length; i++) {
		var id = ids[i];
		var obj = getData(_datas, id);
		if(obj != null) {
			if(i == ids.length - 1) {
				// 最后一个，判断是否还有子类
				if(obj.children && obj.children.length > 0) {
					path.push(obj);
					children = obj.children;
				} else {
					children = path[path.length - 1].children;
				}
				self = obj;
			} else {
				path.push(obj);
			}
			_datas = obj.children;
		} else {
			break;
		}
	}
	return {"path": path, "children": children, "self": self};
}

function getData(list, value) {
	if(list && list.length > 0) {
		for(var i=0; i<list.length; i++) {
			var obj = list[i];
			if(obj["value"] === value) {
				return obj;
			}
		}
	}
	return null;
}

// 移除下级数据箭头标志
function redrawPopItem(__) {

	if (!__.___)
		return;
	__.___.find(".lui_dataview_cate_item_sign").remove();
}
/**
 * 当弹窗滑轮滑到底部禁用掉最外层文档滑轮事件
 * @returns
 */
function outerWheelDisable(popDivObj){
	if (navigator.userAgent.indexOf("Firefox") > -1){
		popDivObj.get(0).addEventListener('DOMMouseScroll',function(ev){
			if(ev.detail>0){
				var scrollValue = popDivObj.get(0).scrollHeight-popDivObj.height();
				if(scrollValue<=(popDivObj.scrollTop()+6)){
					ev.preventDefault()
				}
			}else{
				if(popDivObj.scrollTop()<1){
					ev.preventDefault()
				}
			}
		},false);
	}else{
		var cc = 0;
			popDivObj.on("mousewheel",function(e){
				var scrollValue = popDivObj.get(0).scrollHeight-popDivObj.outerHeight();
				if(e.originalEvent.wheelDelta<0){
					if(scrollValue-cc>=4){
						cc+=40;
					}
				}else{
					if(cc<10){
						cc = 0;
					}else{
						cc=cc - 40;
					}
				}
				popDivObj.scrollTop(cc);
				return false;
			});	
	}
}


done(__frame);