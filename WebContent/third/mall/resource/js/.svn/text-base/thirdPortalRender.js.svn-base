/**
 * 门户数据
 */
seajs.use(['lui/jquery', 'lui/dialog', 'lang!third-mall', 'third/mall/resource/js/thirdPortalUse'], function($, dialog, lang, thirdPortalUse) {
	window.buildNode = function(data) {
		var node = $('<div/>').attr("class", "cm-items");
		node.attr("data-formid", data.fdId);
		var innerNode = $('<div/>').attr("class", "cm-items-inner");
		node.append(innerNode);
		var createUrl = "third/mall/portal/thirdMallPortal_use.jsp";
		var url = "";
		//内容
		var $item = $("<div class='item-box'>").appendTo(innerNode);
		var auth = window.isAuth;
		if (data.fdId != "more") {
			if (data.pic && data.pic != "") {
				$item.append('<div class="item-img"><img src="' + data.pic + '" onerror=\"this.src=\'../resource/images/no-thumb.png\'\"></img></div>');
			} else {
				$item.append('<div class="item-img"><img src="" onerror=\"this.src=\'../resource/images/no-thumb.png\'\"></img></div>');;
			}
			var $content = $('<div class="content">');
			$item.append($content);
			var html = [];
			html.push("<p class='cm-form-box-item-desc'>" + data.fdName + "</p>");
			html.push("<div class='cm-form-box-item-title'>");
			// html.push("<p>" + (data.fdPrice == "0" ? lang["thirdMall.free"] : ("¥" + data.fdPrice)) + "</p>");
			html.push("<div class='cm-form-box-item-data'>");
			html.push("<div class='cm-form-box-item-read'><i class='icon-view'></i><span>" + data.readCount + "</span></div>")
			html.push("<div class='cm-form-box-item-download'><i class='icon-download'></i><span>" + data.downloadCount + "</span></div>");
			html.push("</div>");
			html.push("</div>");

			$content.append(html.join(""));

			//遮罩
			var $itemMask = $('<div class="item-mask">');
			innerNode.append($itemMask);
			var $maskContent = $('<div class="mask-content"></div>');
			$itemMask.append($maskContent);
			var $preview = $("<a href='javascript:void(0);'>" + lang["thirdMall.preview"] + "</a>");
			var  previewLabel = lang["thirdMall.preview"] ;
			var useLabel = lang["thirdMall.use.login"];

			if(window.type == 'theme') {
				useLabel = lang["thirdMall.use.theme"];
			}
			if(window.type == 'render' || window.type == 'panel' || window.type == 'header' || window.type == 'footer' || window.type == 'template'){
				useLabel = lang["thirdMall.use.component"];
			}
			var $use = $("<a href='javascript:void(0);'>" + useLabel + "</a>");
			$maskContent.append($preview);
			$maskContent.append($use);
			//预览
			$preview.on('click', function() {

				var previewUrl = "/third/mall/portal/thirdMallPortal_preview.jsp";
					var pcPreviewUrl = "../resource/images/pic_1@2x.png";
					var _createUrl = Com_Parameter.ContextPath + createUrl + "?sourceFrom=Reuse&sourceKey=Reuse&type=2&fdThirdTemplateId=" + data.fdId;
				if (data.pic && data.pic != "") {
					previewUrl = "/third/mall/portal/thirdMallPortal_preview.jsp";
					pcPreviewUrl = data.pic;
					_createUrl = Com_Parameter.ContextPath + createUrl + "?sourceFrom=Reuse&sourceKey=Reuse&type=2&fdThirdTemplateId=" + data.fdId;
				}
				dialog.iframe(previewUrl, previewLabel, function() {
				}, {
					"width": 920,
					"height": 680,
					params: {
						"pcPreviewUrl": pcPreviewUrl,
						"createUrl": _createUrl,
						"auth": auth,
						"parentDialog": window.parent.$dialog,
						"type": window.type,
						"fdId": data.fdId
					},
					buttons: [
						{
							name: lang["thirdMall.use"], value: true, focus: true,
							fn: function (value, dialogUse) {

									if (auth == "true") {

										var $thirdPortalUse = thirdPortalUse ;
										// 使用模板
										 $thirdPortalUse.useTpl(data.fdId, window.type,function(rt){
											 dialogUse.hide(rt);
										 });

										// useMallInfo(data.fdId, attId, vId, _dialog, fdSubType);

									} else {
										seajs.use(['lui/dialog'], function(dialog) {
											if(window.type == 'login') {
												dialog.alert(lang["thirdMall.login.noAuth"]);
											} else if(window.type == 'theme') {
												dialog.alert(lang["thirdMall.theme.noAuth"]);
											} else {
												dialog.alert(lang["thirdMall.noAuth"]);
											}
										});
									}

							}
						}, {
							name: lang["thirdMall.cancel"], value: false, styleClass: 'lui_toolbar_btn_gray',
							fn: function (value, dialog) {
								dialog.hide(value);
							}
						}
					]
				});
			});
			$use.on('click', function() {
				if (auth == "true") {
					// 使用模板
					thirdPortalUse.useTpl(data.fdId, window.type);
				} else {
					seajs.use(['lui/dialog'], function(dialog) {
						if(window.type == 'login') {
							dialog.alert(lang["thirdMall.login.noAuth"]);
						} else if(window.type == 'theme') {
							dialog.alert(lang["thirdMall.theme.noAuth"]);
						} else {
							dialog.alert(lang["thirdMall.noAuth"]);
						}
					});
				}
			});
		} else { // 更多，跳到云商城页面
			var moreHtml = [];
			moreHtml.push("<div class='more'><span>" + lang["thirdMall.templateMore"] + "</span><span class='icon-more'></span></div>");
			$item.append(moreHtml.join(""));
			node.on('click', function() {
				if (auth == "true") {
					//跳转到云商城页面
					var thirdMallCreateParam = "?type=" + window.type;
					createUrl = data.absPath + createUrl;
					url = data.url + "&type=" + window.type + "&sysVerId=" + data.versionId + "&version=" + window.version + "&product=" + window.product
						+ "&createUrl=" + encodeURIComponent(createUrl + thirdMallCreateParam);
					if (Com_Parameter.dingXForm === "true") {
						//因为钉钉审批高级版后台页面最外层是moduleindex，与\sys\profile\index.jsp不同，frameWin[funcName]能找到对应方法，会导致新建模板页面在viewFrame中打开
						Com_OpenWindow(url, "_blank");
					} else {
						Com_OpenWindow(url);
					}
					window.parent.$dialog.hide();
				} else {
					seajs.use(['lui/dialog'], function(dialog) {
						if(window.type == 'login') {
							dialog.alert(lang["thirdMall.login.noAuth"]);
						} else if(window.type == 'theme') {
							dialog.alert(lang["thirdMall.theme.noAuth"]);
						} else {
							dialog.alert(lang["thirdMall.noAuth"]);
						}
					});
				}
			});
		}
		return node;
	};
	var element = render.parent.element;
	$(element).html("");
	var formInfos = data;
	if (formInfos == null || formInfos.length == 0) {
		done();
		noRecode($(element));
	} else {
		var ul = $(element);
		for (var i = 0; i < formInfos.length; i++) {
			var formObj = formInfos[i];
			var node = buildNode(formObj);
			node.appendTo(ul);
		}
	}
});

function hide() {
	var context = window.top.document;
	seajs.use(['lui/jquery'], function($) {
		$("[data-lui-mark='dialog.nav.close']", context).trigger("click");
	});
}

function noRecode(context) {
	seajs.use(['lui/util/env', 'theme!listview'], function(env, listview) {
		var __url = '/resource/jsp/listview_norecord.jsp?_=' + new Date().getTime();
		$.ajax({
			url: env.fn.formatUrl(__url),
			dataType: 'text',
			success: function(data) {
				context.html(data);
			}
		});
	})
}

