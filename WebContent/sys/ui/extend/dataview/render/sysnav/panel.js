define(function (require, exports, module) {
	var $ = require('lui/jquery');
	var layout = require('lui/view/layout');
	var panel = require('lui/panel');
	var env = require('lui/util/env');
	var str = require('lui/util/str');

	//构建分类
	function ___createContent(data, evt, isFirst) {
		var parent = evt.parent;
		var content = new panel.Content({
			title: data.text,
			expand: data.expand == 'false' ? false : true    //兼容旧数据默认展开
		});
		content.setParent(parent);
		parent.addChild(content);
		content.startup();
		content.draw();
		var $ul = $('<ul/>');
		if (data.children && data.children.length > 0) {
			$ul = $ul.addClass('lui_list_nav_list').appendTo(content.element);
			for (var i = 0; i < data.children.length; i++) {
				___createItem(data.children[i], {
					parentNode: $ul,
					ancestor: parent
				});
			}
		}
		return content;
	}

	//构建导航项
	function ___createItem(data, evt) {
		var parentNode = evt.parentNode,
			ancestorNode = evt.ancestor.element;


		if(data.img){
			var $li = $('<li class="lui_list_nav_list_item_img"/>'),
				$a = $('<a/>').attr('title', data.text).appendTo($li);
			var imgSrc = env.fn.formatUrl(data.img);
			var imgNode = $('<i class="img icon"><img alt="" src="' + imgSrc + '" /></i>');
			$a.append(imgNode).append($('<span>').text(data.text).attr("title", data.text));
		}
		else {
			var $li = $('<li class="lui_list_nav_list_item"/>'),
				$a = $('<a/>').attr('title', data.text).appendTo($li);
			var iconNode = $('<i class="icon"/>');
			var icon = data.icon || 'iconfont';
			if (icon.indexOf('&amp;') > -1) {
				iconNode.addClass("fontmui").html(str.decodeHTML(icon));
			} else {
				iconNode.addClass(icon);
			}
			$a.append(iconNode).append($('<span>').text(data.text).attr("title", data.text));
		}


		$a.on('click', function () {
			$('li', ancestorNode).removeClass('lui_list_nav_selected');
			$(this).parent().addClass('lui_list_nav_selected');
			var target = data.target,
				href = data.href ? env.fn.formatUrl(data.href) : 'javascript:void(0)';
			//修正target
			switch (target) {
				case '_self' :
					target = '_top';
					break;
				case '_content' :
					target = LUI.pageMode() == 'quick' ? '_rIframe' : '_top';
					break;
				default :
					break;
			}
			if (LUI.pageMode() == 'quick' && target == '_rIframe') {
				//非站点内url不附加额外参数
				if (env.fn.isInternalLink(href)) {
					var jAside = Com_GetUrlParameter(href, 'j_aside');
					var jrIframe = Com_GetUrlParameter(href, 'j_rIframe');
					var jiframe = Com_GetUrlParameter(href, 'j_iframe');


					//#151830 如果是在极速和左页面模式下，需要进行转换 如：
					// 将 /sys/portal/page.jsp?mainPageId=162712861e187099dc4b94e439b81e2d
					//转化为
					//   /sys/portal/sys_portal_page/sysPortalPage.do?method=view&fdId=162712861e187099dc4b94e439b81e2d
					var mainPageId = Com_GetUrlParameter(href, 'mainPageId');
					if (mainPageId != null){
						href = href.substring(0, href.indexOf("/sys/portal/") + 12) + "sys_portal_page/sysPortalPage.do?method=view&fdId=" + mainPageId;
					}
					//左边栏
					if (jAside == null) {
						href = Com_SetUrlParameter(href, 'j_aside', 'false');
					}else{
						href = Com_SetUrlParameter(href, 'j_aside', jAside);
					}
					// 页眉
					if (jrIframe == null){
						href = Com_SetUrlParameter(href, 'j_rIframe', 'true');
					}else{
						href = Com_SetUrlParameter(href, 'j_rIframe', jrIframe);
					}
					if (jiframe == null){
						href = Com_SetUrlParameter(href, 'j_iframe', 'true');
					}else{
						href = Com_SetUrlParameter(href, 'j_iframe', jiframe);
					}

				}
				LUI.pageOpen(href, target, {
					subscribeIframe: false
				});
			} else {
				window.open(href, target);
			}
		});
		parentNode.append($li);
	}

	var buildAccordionPanel = function (___datas) {

		var accordionPanel = new panel.AccordionPanel({
			expand: true, toggle: true
		});
		accordionPanel.layout = new layout.Javascript({
			kind: 'accordionpanel',
			src: '/sys/ui/extend/panel/accordionpanel.js',
			parent: accordionPanel
		});
		accordionPanel.layout.startup();
		accordionPanel.startup();

		for (var i = 0; i < ___datas.length; i++) {
			var c = ___createContent(___datas[i], {
				parent: accordionPanel
			}, i == 0);
		}
		accordionPanel.draw();
		return accordionPanel;
	};

	exports.buildAccordionPanel = buildAccordionPanel;

});