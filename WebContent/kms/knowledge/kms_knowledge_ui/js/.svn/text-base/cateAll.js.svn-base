data[0].text="绩效合约导出";
data[0].children[0].value="/Ekp_H14_S";
data[0].children[0].text="销售和非销售绩效考核导出";
data[0].children[1].value="/Ekp_H14_M_performance";
data[0].children[1].text="M类负责人绩效面谈表导出";
data[0].children[2].value="/Ekp_H14_S1";
data[0].children[2].text="销售人员绩效面谈表导出";
data[0].children[3].value="/Ekp_H14_S2";
data[0].children[3].text="非销售人员绩效面谈表导出";
data[0].children.push({"value":"/ekp_H14_Intern","text":"实习生绩效考核申请导出"});
data[0].children.push({"value":"/performanceSearch","text":"M类负责人绩效考核导出"});
data[0].children[0].autoFetch=false;
var ___data = data;
console.log(data);
var ___href = render.vars.href, ___target = render.vars.target;

var element = render.parent.element;
var __render = render;
var _locationHref = window.location.href;
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

//	var item__s = $("<div class='lui_dataview_cate_item_sign'>"
//			+ "<i class='lui_dataview_cate_item_sign_mtr1'></i>"
//			+ "<i class='lui_dataview_cate_item_sign_mtr2'></i>" + "</div>");
//	return item__s;
	return "";
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
					
					var router = routerUtils.getRouter();
					// 是否启用路由模式
					if (router) {
						router.addRoutes({

							path : '/docCategory',
							action : function(value) {

								topic.publish(spaConst.SPA_CHANGE_RESET, {
									value : value,
									target : this
								});

								topic
										.publish("nav.operation.clearStatus",
												null);

							}

						});

						var categoryId = Com_GetUrlParameter(location.href,
								'categoryId');

						if (categoryId) {

							if (LUI.luihasReady) {
								router.push('/docCategory', {
									'docCategory' : categoryId
								});
							} else {
								LUI.ready(function() {

									router.push('/docCategory', {
										'docCategory' : categoryId
									});
								});
							}

						}

					}

					var __box = createBox();
					__frame.append(__box);

					var modelName = __render.cfg.modelName;

					var currentId = Spa.spa.getValue('docCategory');

					if (currentId) {

						$
								.ajax({
									url : env.fn
											.formatUrl('/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=path&modelName='
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

						buildPopup(strutil, popup, topic, spaConst, cate,
								__box, modelName, router);

					}
					//#60015 修改
					setTimeout(function(){
						if($(".lui_dataview_cate_popup").length==0){
							console.log('network slowly');
							$(document).bind('mousewheel', function(event, delta) { return false; });
						}else{
							$(".lui_dataview_cate_popup").on('mouseover',function(){
								$(document).bind('mousewheel', function(event, delta) { return false; });
							});
							$(".lui_dataview_cate_popup").on('mouseout',function(){
								$(document).unbind('mousewheel');
							});
						}
						
					},3000);

				});

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
	var docCategoryId = "";
	if ( _locationHref.indexOf("docCategory=") > -1 ) {
		docCategoryId = _locationHref.substring(_locationHref.indexOf("docCategory=")+12,_locationHref.indexOf("docCategory=")+12+32);
	}
	var isChildrenCategory = "";
	for (var i = 0; i < ___data.length; i++) {

		// 文本
		var __t = createItem_t();
		__t.html(''
				+ env.fn.formatText(___data[i].text));
		__t.attr('title', strutil.decodeHTML(___data[i].text));
		var __l = createItem_l();
		var __r = createItem_r();
		var __c = createItem_c();
		var __s = createItem_s();
		var toggle = $('<span class="knowledge_toggle_down">&nbsp;</span>');
		
		__l.append(__r.append(__c.append(__t).append(__s)));
		var li = $('<li class="lui_accordionpanel_frame li_check"></li>');
		var div = $('<div data-lui-switch-class="lui_icon_on" class="lui_dataview_cate_item"/>');
		if ( ___data[i]['value'] == docCategoryId ) {
			li.addClass("lui_list_nav_selected");
		}
		
		~~function(i) {

			return function() {

//				div.click(function(evt) {
//					$(".li_check").removeClass("lui_list_nav_selected");
//					$(evt.currentTarget.parentNode).addClass("lui_list_nav_selected");
//					if (router) {
//						router.push('/docCategory', {
//							'j_path' : data[i]['value'],
//						})
//					} else {
//						topic.publish(spaConst.SPA_CHANGE_ADD, {
//							value : {
//								'j_path' : data[i]['value']
//							}
//						});
//					}
//				});
				toggle.click(function(evt) {
					if ( $(evt.currentTarget).hasClass("knowledge_toggle_up") ) {
						$(evt.currentTarget).removeClass("knowledge_toggle_up");
						$(evt.currentTarget).addClass("knowledge_toggle_down");
						$(evt.currentTarget).next()[0].style.display="none";
					}else {
						$(evt.currentTarget).removeClass("knowledge_toggle_down");
						$(evt.currentTarget).addClass("knowledge_toggle_up");
						$(evt.currentTarget).next()[0].style.display="";
					}
				});
			}();
		}(i);
		var datas = ___data[i].children;
		var childrenDiv = $('<ul style="display:none"></ul>');
		for ( var j = 0; j < datas.length; j++ ) {
			// 文本
			var __ts = createItem_t();
			__ts.html('<i class="iconfont lui_iconfont_dotted"></i>'
					+ env.fn.formatText(datas[j].text));
			__ts.attr('title', strutil.decodeHTML(datas[j].text));
			var __ls = createItem_l();
			var __rs = createItem_r();
			var __cs = createItem_c();
			var __ss = createItem_s();
			__ls.append(__rs.append(__cs.append(__ts).append(__ss)));

			var lis = $('<li class="lui_accordionpanel_frame li_check"></li>');
			var divs = $('<div data-lui-switch-class="lui_icon_on" class="lui_dataview_cate_item lui_accordionpanel_content_l"/>');
			if ( datas[j]['value'] == docCategoryId ) {
				isChildrenCategory = "true";
				lis.addClass("lui_list_nav_selected");
			}
			
			~~function(i,j) {

				return function() {

					divs.click(function(evt) {
						$(".li_check").removeClass("lui_list_nav_selected");
						$(evt.currentTarget.parentNode).addClass("lui_list_nav_selected");
						if (router) {
							router.push('/docCategory', {
								'j_path' :data[i].children[j]['value']
							})

						} else {

							topic.publish(spaConst.SPA_CHANGE_ADD, {
								value : {
									'docCategory' : data[i].children[j]['value']
								}
							});
						}
						seajs.use(['lui/framework/router/router-utils'],
								function(routerUtils){
							var $router = routerUtils.getRouter();
						router.push(data[i].children[j]['value'], data.params || {});
						});
					});
				}();
			}(i,j);
			lis.append(divs.append(__ls));
			childrenDiv.append(lis);
			
			var configss = {
					datas : [],
					modelName : modelName,
					currentId : currentId
				}

			var _cates = new cate.Cate(configss);

			var popDivs = $('<div>').attr('class', 'lui_dataview_cate_popup');
			var _____heights = $(window).height() - 6;
			popDivs.css('max-height', _____heights);
			popDivs.append(_cates.element);
			var cfgs = {
				"align" : "right-top"
			};

			var pps = popup.build(divs, popDivs, cfgs);
			popDivs.css('width', 0);
			popDivs.css('overflow', 'hidden');
			redrawPopItem({
				___ : li
			});
			
		}
		li.append(div.append(__l));
		if ( isChildrenCategory == "true" ) {
			toggle.addClass("knowledge_toggle_up");
			toggle.removeClass("knowledge_toggle_down");
			childrenDiv.css("display","block");
		}
		if ( datas.length > 0 ) {
			li.append(childrenDiv);
		}
		
		__box.append(li);

		var configs = {
			datas : datas || [],
			modelName : modelName,
			currentId : currentId
		}

		if (path && path.length > 1) {

			if (___data[i].value == path[0].value) {
				configs.path = path;
			}
		}

		var _cate = new cate.Cate(configs);

		var popDiv = $('<div>').attr('class', 'lui_dataview_cate_popup');
		var _____height = $(window).height() - 6;
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

		if (datas.length > 0) {

			popDiv.css('width', 0);
			popDiv.css('overflow', 'hidden');
			redrawPopItem({
				___ : li
			});

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
}

// 移除下级数据箭头标志
function redrawPopItem(__) {

	if (!__.___)
		return;
	__.___.find(".lui_dataview_cate_item_sign").remove();
}
done(__frame);