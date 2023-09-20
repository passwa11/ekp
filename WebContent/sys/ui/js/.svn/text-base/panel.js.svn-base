// JavaScript Document
define(function (require, exports, module) {
	require("theme!panel");
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var env = require("lui/util/env");
	var strutil = require('lui/util/str');
	var layout = require("lui/view/layout");
	var topic = require("lui/topic");
	var toolbar = require('lui/toolbar');
	var lang = require('lang!sys-ui');
	var routerUtils = require('lui/framework/router/router-utils');

	var AbstractContent = base.Container.extend({
		initProps: function (_config) {
			this.isShow = false;
			this.config = _config;
			this.isLoad = false;
			this.title = _config.title;
			this.hasSetTitle = false;
			this.countUrl = _config.countUrl;
			this.titleicon = _config.titleicon;
			this.titleimg = _config.titleimg;
			this.subtitle = _config.subtitle;
			this.__title = _config.__title;
			if (!this.__title) {
				this.__title = _config.tagTitle;
			}
			this.element.show();
			this.vars = {};
			if (this.config.vars)
				this.vars = this.config.vars;
			if (this.config.toggle != null)
				this.toggle = this.config.toggle;
			if (this.config.expand != null)
				this.expand = this.config.expand;
			if (this.config.isShow != null)
				this.isShow = this.config.isShow;
			if (_config.style) {
				this.element.attr("style", (this.element.attr("style") ? this.element.attr("style") + ";" : "") + _config.style);
			}
		},
		startup: function ($super) {
			var self = this;
			this.on("count", function (countInfo) {
				if (self.parent && self.parent.setCount) {
					self.parent.setCount(self, countInfo);
				}
			});
		},
		getSize: function () {
			return this.parent.getSize();
		}
	});
	var AbstractPanel = base.Container.extend({
		initProps: function ($super, _config) {
			this.config = _config;
			this.contents = [];
			this.vars = {};
			this.navs = [];
			if (_config.style)
				this.element.attr("style", (this.element.attr("style") ? this.element.attr("style") + ";" : "") + _config.style);
			$.extend(this.vars, _config.vars);
		},
		getPanelSize: function () {
			return {width: this.frameWidth, height: this.frameHeight};
		},
		getSize: function ($super) {
			var size = $super();
			size.width = size.width - this.getPanelSize().width;
			size.height = size.height - this.getPanelSize().height;
			return size;
		},
		erase: function ($super, includeChild) {
			if (this.isDrawed) {
				if (includeChild == null || includeChild) {
					$super(includeChild);
				} else {
					this.tempFrame = $("<div data-lui-mark='temp'/>");
					this.element.append(this.tempFrame);
					for (var i = 0; i < this.contents.length; i++) {
						this.tempFrame.append(this.contents[i].getRootElement());
					}
					var cd = this.element.children();
					for (var i = cd.length - 1; i >= 0; i--) {
						if ((cd[i].getAttribute("data-lui-mark") || "") != "temp") {
							cd.remove();
						}
					}
					for (var i = 0; i < this.contents.length; i++) {
						this.element.append(this.contents[i].getRootElement());
					}
					this.tempFrame.remove();
				}
			}
			this.isDrawed = false;
		},
		parseToggleMark: function (dom) {
			//切换按钮
			var self = this;
			var headNodes = dom.find("[data-lui-mark='panel.nav.head']");
			headNodes.each(function (index, domEle) {
				var headNode = $(domEle);
				self.headersNode.push(headNode);
				var toggleNode = headNode.find('[data-lui-mark="panel.nav.toggle"]');
				var len = self.togglesNode.length;
				self.togglesNode.push(toggleNode);
				if (self.contents[len].getToggle()) {
					headNode.css('cursor', 'pointer');
					headNode.click(function (evt) {
						self.onToggle(len, null, null, evt);
					});
				} else {
					toggleNode.hide();
				}
			});
		},
		//图标 兼容字体和类名
		buildTitleIcon: function (titleIcon) {
			var defTitleIcon = "<i class='lui_panel_title_icon " + (titleIcon ? titleIcon : "") + "'></i>";
			try {
				if (titleIcon && titleIcon.indexOf('lui_iconfont') == 0) {
					defTitleIcon = "<i class='lui_panel_title_icon iconfont " + (titleIcon ? titleIcon : "") + "'></i>";
				}
			} catch (err) {
				console.log("[err]" + err.message);
			}
			return defTitleIcon
		},
		//素材库图标
		buildTitleImg: function (titleImg) {
			var _titleImg = "<img alt='' src="+env.fn.formatUrl(titleImg)+">";
			var defTitleImg = "<span class='lui_tabpanel_title_icon lui_panel_title_pc_img'>" +_titleImg
				+ "</span>";
			return defTitleImg
		},
		//副标题
		buildSubtitle: function (subtitle) {
			return "<span class='lui_panel_title_subtitle '>" + (subtitle ? subtitle : "")
				+ "</span>";
		},
		parseTextMarkForExpand: function (dom) {
			var self = this;
			var titleNodes = dom.find("[data-lui-mark='panel.nav.title']");
			var extendType;
			if (self.layout.config && self.layout.config.param) {
				extendType = self.layout.config.param.extend;
			}
			var idx = 0, moreIdx = 0;
			titleNodes.each(function (i, domEle) {
				//标题文字
				var titleNode = $(domEle);
				var index = idx;
				if (titleNode.hasClass("more_item")) {
					index = moreIdx;
				}
				self.titlesNode.push(titleNode);
				var title = strutil.decodeHTML(self.contents[index].title);
				//多标签面板
				if (extendType == "multiCollapse" && title.indexOf("/") > -1) {
					var titleArr = title.split("/");
					title = titleArr[1];
				}
				var titleiconHtml = self.contents[index].titleicon ? self.buildTitleIcon(self.contents[index].titleicon) : "";
				var subtitleHtml = self.contents[index].subtitle ? self.buildSubtitle(self.contents[index].subtitle) : "";
				var titleNodeHtml = titleiconHtml + '<span class="lui_panel_title_main lui_tabpanel_navs_item_title">' + decodeURIComponent(env.fn.formatText(title)) + '</span>' + subtitleHtml;
				if (extendType == "sucktop") {
					titleNodeHtml = titleiconHtml + '<span class="lui_panel_title_main lui_tabpanel_navs_item_title">' + decodeURIComponent(title) + '</span>' + subtitleHtml;
				}
				titleNode.html(titleNodeHtml);
				titleNode.attr('title', title);
				if (titleNode.hasClass("more_item")) {
					moreIdx++;
				} else {
					idx++;
				}
			});
		},
		parseTextMark: function (dom) {
			var self = this;
			var titleNodes = dom.find("[data-lui-mark='panel.nav.title']");
			var extendType;
			if (self.layout.config && self.layout.config.param) {
				extendType = self.layout.config.param.extend;
			}
			titleNodes.each(function (index, domEle) {

				//标题文字
				var titleNode = $(domEle);
				self.titlesNode.push(titleNode);
				if (self.contents[index].__title) {
					var __title = self.contents[index].__title
					//多标签面板
					if (extendType == "multiCollapse" && __title.indexOf("/") > -1) {
						var __titleArr = __title.split("/");
						__title = __titleArr[1];
					}
					var titleiconHtml = self.contents[index].titleicon ? self.buildTitleIcon(self.contents[index].titleicon) : "";
					var subtitleHtml = self.contents[index].titleicon ? self.buildSubtitle(self.contents[index].subtitle) : "";
					titleNode.html(titleiconHtml + '<span class="lui_panel_title_main lui_tabpanel_navs_item_title">' + __title + '</span>' + subtitleHtml);
					if (self.contents[index].title) {
						var title = self.contents[index].title;
						if (extendType == "multiCollapse" && title.indexOf("/") > -1) {
							var titleArr = title.split("/");
							title = titleArr[1];
						}
						titleNode.attr('title', title);
					}
				} else {
					var title = strutil.decodeHTML(self.contents[index].title);
					//多标签面板
					if (extendType == "multiCollapse" && title.indexOf("/") > -1) {
						var titleArr = title.split("/");
						title = titleArr[1];
					}
					var titleiconHtml = self.contents[index].titleicon ? self.buildTitleIcon(self.contents[index].titleicon) : "";
					//素材库图标
					var titleimgHtml = self.contents[index].titleimg ? self.buildTitleImg(self.contents[index].titleimg) : "";
					var subtitleHtml = self.contents[index].subtitle ? self.buildSubtitle(self.contents[index].subtitle) : "";
					if(titleimgHtml){
						titleiconHtml = titleimgHtml;
					}
					var titleNodeHtml = titleiconHtml + '<span class="lui_panel_title_main lui_tabpanel_navs_item_title">' + decodeURIComponent(env.fn.formatText(title)) + '</span>' + subtitleHtml;
					if (extendType == "sucktop") {
						titleNodeHtml = titleiconHtml + '<span class="lui_panel_title_main lui_tabpanel_navs_item_title">' + decodeURIComponent(title) + '</span>' + subtitleHtml;
					}
					titleNode.html(titleNodeHtml);
					titleNode.attr('title', title);
				}
			});
		},
		parseContentMark: function (dom) {
			var self = this;
			var contentNodes = dom.find("[data-lui-mark='panel.contents']");
			if (contentNodes.length > 0) {
				for (var i = 0; i < this.contents.length; i++) {
					contentNodes.append("<div data-lui-mark='panel.content'></div>");
				}
				dom.find("[data-lui-mark='panel.content']").map(function () {
					self.contentsNode.push($(this));
				});
			} else {
				dom.find("[data-lui-mark='panel.content']").map(function () {
					self.contentsNode.push($(this));
				});
			}
		},
		hideDisabledMark: function () {
			var self = this;
			if (this._disabled && this._disabled.length > 0) {
				for (var i = 0; i < this._disabled.length; i++) {
					this._disabled[i].getRootElement().remove();
				}
			}
		},
		doLayout: function ($super, obj) {
			var self = this;
			this.frame = $(obj);
			this.titlesNode = [];
			this.togglesNode = [];
			this.contentsNode = [];
			this.headersNode = [];
			this.titlesFrame = this.frame.find("[data-lui-mark='panel.nav.frame']");
			//获取标题的mark;
			if (this.vars.supportExpand == 'true') {
				// 更多
				this.parseTextMarkForExpand(this.frame);
			} else {
				this.parseTextMark(this.frame);
			}
			//解析toggle的mark
			this.parseToggleMark(this.frame);
			//解析content的mark
			this.parseContentMark(this.frame);
			//隐藏禁用的页签
			this.hideDisabledMark();

			this.element.append(this.frame);
			this.element.show();

			//处理高度
			//获取frame的高度
			this.frameHeight = this.frame.height();
			this.frameWidth = this.frame.width();

			if (this.contentsNode.length > 0) {
				//将所有子元素的Dom元素移动到指定位置
				for (var i = 0; i < this.contents.length; i++) {
					var obj = this.contents[i].getRootElement();
					this.contentsNode[i].hide().append(obj);
					this.markContent(obj, this.contents[i]);
					this.contents[i].preload();
				}
			}
			this.emit('panelLoaded');
			this.panelLoaded = true;
			//父标题填充
			this.parentTitleShow = false;
			if (this.vars.parentTitle && this.vars.parentTitle != '') {
				this.parentTitleShow = true;
				this.element.find(".lui_tabpanel_title_main").html(this.vars.parentTitle);
			}
			if (this.vars.icon && this.vars.icon != '') {
				this.parentTitleShow = true;
				// #146446 素材库应用多标签
				if(this.vars.icon.indexOf('/')>-1){
					var _titleImg = $("<img alt='' src="+env.fn.formatUrl(this.vars.icon)+">")
					_titleImg.appendTo(this.element.find(".lui_tabpanel_title_icon"));
					this.element.find(".lui_tabpanel_title_icon").attr("class", "lui_tabpanel_title_icon lui_tabpanel_title_img")
				}else{
					this.element.find(".lui_tabpanel_title_icon").attr("class", "lui_tabpanel_title_icon  " + this.vars.icon);
				}
			}
			if (this.vars.subtitle && this.vars.subtitle != '') {
				this.parentTitleShow = true;
				this.element.find(".lui_tabpanel_title_subtitle").html(this.vars.subtitle);
			}
			if (this.parentTitleShow) {
				var tabpanelTitleHeight = this.element.find(".lui_tabpanel_title").height();
				if (this.element.find(".lui_tabpanel_vertical_frame")) {
					//垂直多标签
					var navEle = this.element.find(".lui_tabpanel_vertical_navs_l");
					if (navEle.height()) {
						var verHeight = navEle.height() - tabpanelTitleHeight;
						navEle.height(verHeight)
						this.element.find(".lui_tabpanel_vertical_content_l").height(verHeight);
					}
				}
				if (this.element.find(".lui_tabpanel_vertical_light_frame")) {
					//浅色垂直多标签
					var navEle = this.element.find(".lui_tabpanel_vertical_light_navs_l");
					if (navEle.height()) {
						var verHeight = navEle.height() - tabpanelTitleHeight;
						navEle.height(verHeight)
						//高度设定
						if (this.config.scroll != 'true') {
							this.element.find(".lui_tabpanel_vertical_light_content_l").height("");
						} else {
							this.element.find(".lui_tabpanel_vertical_light_content_l").height(verHeight);
							this.element.find(".lui_tabpanel_vertical_light_navs_l").css("overflow-y", "scroll");
							this.element.find(".lui_tabpanel_vertical_light_navs_l").css("overflow-x", "hidden");
						}
					}
				}
				this.element.parent().find(".tabPanel_frame").addClass("lui_tabpanel_hasTitle_frame")
			} else {
				this.element.find(".lui_tabpanel_title").css({"display": "none", "height": 0});

				if (this.element.find(".lui_tabpanel_vertical_light_frame")) {
					//高度设定
					if (this.config.scroll != 'true') {
						var size = this.element.find(".lui_tabpanel_vertical_light_navs_item_l").size() * 41;
						this.element.find(".lui_tabpanel_vertical_light_frame").css("min-height", size + "px");
						this.element.find(".lui_tabpanel_vertical_light_navs_l").height("");
						this.element.find(".lui_tabpanel_vertical_light_content_l").height("auto");
					} else {
						this.element.find(".lui_tabpanel_vertical_light_navs_l").css("overflow-y", "scroll");
						this.element.find(".lui_tabpanel_vertical_light_navs_l").css("overflow-x", "hidden");
					}
				}
			}

			$super(obj);

		},
		markContent: function (dom, content) {
			if (content.config.extClass != null) {
				$(dom).addClass(content.config.extClass);
			}
		},
		addChild: function ($super, obj) {
			if (obj instanceof layout.AbstractLayout) {
				this.layout = obj;
			} else if (obj instanceof AbstractContent) {
				this.contents.push(obj);
			}
			$super(obj);
		},
		resize: function () {
			for (var i = 0; i < this.contents.length; i++) {
				this.contents[i].resize();
			}
		},
		//countInfo格式{count:2,more:true}属性皆可为空值
		setCount: function (content, countInfo) {
			if (countInfo) {
				if (!countInfo.count) {
					countInfo.count = 0;
				}
				if (!countInfo.more) {
					countInfo.more = false;
				}
				if (this.titlesNode.length > 0 && (countInfo.count >= 0 || countInfo.more == true)) {
					var self = this;
					var idx = $.inArray(content, this.contents);
					if (idx > -1) {
						if (this.titlesNode[idx] != null) {
							var titleNode = this.titlesNode[idx];
							var modify = false;
							var modifyClass = "";
							var html = "<span ";
							if (countInfo.count > 0) {
								modify = true;
								html = html + "class='com_prompt_num'>" + (countInfo.count > 99 ? "99+" : countInfo.count);
								modifyClass += " lui_panel_hasCount_frame ";
							}
							if (countInfo.count == 0) {
								modify = true;
							}
							if (countInfo.more == true && this.titlesNode.length > 1) {//单页签不显示红点,数字除外
								modify = true;
								modifyClass += " lui_panel_hasDot_frame ";
								html = html + "class='com_prompt_more'>";
							}
							html = html + "</span>";
							if (modify) {
								var title = self.contents[idx].title;
								var extendType;
								if (self.layout.config && self.layout.config.param) {
									extendType = self.layout.config.param.extend;
									if (extendType == "multiCollapse" && title.indexOf("/") > -1) {
										var titleArr = title.split("/");
										title = titleArr[1];
									}
								}
								var titleiconHtml = this.contents[idx].titleicon ? self.buildTitleIcon(this.contents[idx].titleicon) : "";
								var subtitleHtml = this.contents[idx].subtitle ? self.buildSubtitle(this.contents[idx].subtitle) : "";


								var scriptHtml = '<script>var h = $(".lui_panel_vertical_navs_item_c .lui_panel_hasCount_frame").html();</script>'
								var nodeHtml = titleiconHtml + '<span class="lui_panel_title_main lui_tabpanel_navs_item_title">' + title + '</span>' + html + subtitleHtml;

								titleNode.html("<div class='" + modifyClass + "'>" + nodeHtml + "</span>");
								//垂直单标签角标设置lui_panel_vertical_navs_item_c
								if (titleNode.context.className == "lui_panel_vertical_navs_item_c") {
									var domHeight = this.element.find(".lui_panel_hasCount_frame").height();
									if (domHeight > 0) {
										var textLength = this.element.find(".lui_tabpanel_navs_item_title").text().length * 18;
										var top = parseInt((domHeight - textLength) / 2) - 18;
										if (top <= 0) {
											top = -15;
										}
										this.element.find(".com_prompt_num").attr("style", "top:" + top + "px");
									}

								}

							}
						}
					}
				}
			}
		},
		props: function (index, options) { //{ visible,title,………… }
			options = options || {};
			if (index instanceof AbstractContent) {
				index = $.inArray(content, this.contents);
			}
			if (index >= this.contents.length || index < 0) {
				return;
			}
			for (var key in options) {
				var methodName = '__set' + key.substring(0, 1).toUpperCase() + key.substring(1);
				if (!this.panelLoaded) {
					this.on('panelLoaded', function () {
						this[methodName] && this[methodName](index, options);
					}, this);
				} else {
					this[methodName] && this[methodName](index, options);
				}
			}
		},
		//修改指定序号Tab的标题
		__setTitle: function (index, options) {
			var dom = this.frame;
			var titleNodes = dom.find("[data-lui-mark='panel.nav.title']"),
				titleNode = titleNodes.length > index ? titleNodes.eq(index) : null;
			var content = this.contents[index];
			if (titleNode) {
				if (options.force) {
					titleNode.html('<span class="lui_tabpanel_navs_item_title">' + options.title + '<span class="count">(' + options.count + ')</span></span>');
					content.hasSetTitle = true;
				} else {
					if (content.countUrl && !content.hasSetTitle) {
						titleNode.html('<span class="lui_tabpanel_navs_item_title">' + options.title + '<span class="count"></span></span>');
						$.getJSON(env.fn.formatUrl(content.countUrl), function (rtn) {
							if (rtn && rtn.count) {
								$(titleNode).find('.count').html("(" + rtn.count + ")");
							}
						});
						content.hasSetTitle = true;
					} else {
						if (!content.hasSetTitle) {
							titleNode.html('<span class="lui_tabpanel_navs_item_title">' + options.title + '</span>');
							content.hasSetTitle = true;
						} else {
							if (titleNode.children(":first").length > 0) {
								titleNode.children(":first").html(options.title);
							}
						}
					}
				}
				//低版本IE报错
				if (window.console) {
					console.log("前：", titleNode.attr('title'));
				}
				titleNode.attr('title', options.title);
				if (window.console) {
					console.log("后：", titleNode.attr('title'));
				}
			}
		},
		//修改指定序号Tab的可见性
		__setVisible: function (index, options) {
			var dom = this.frame;
			var titleNodes = dom.find("[data-lui-mark='panel.nav.title']"),
				titleNode = titleNodes.length > index ? titleNodes.eq(index) : null;
			if (titleNode) {
				if (options.visible) {
					$(titleNode).show();
				} else {
					$(titleNode).hide();
				}
			}
		}
	});


	/**
	 * 多标签面板的定义
	 */
	var TabPanel = AbstractPanel.extend({
		initProps: function ($super, _config) {
			$super(_config);
			this.router = _config.router;
			this.selectedIndex = 0;
			if (window.localStorage && _config.loadLocalStorage && _config.loadLocalStorage == 'true') {
				if (_config.loadLocalStorageKey) {
					var loadLocalStorageIndex = window.localStorage.getItem(_config.loadLocalStorageKey);
					if (loadLocalStorageIndex != null && !isNaN(loadLocalStorageIndex)) {
						this.selectedIndex = loadLocalStorageIndex;
					}
				}
			}
			if (_config.selectedIndex != null)
				this.selectedIndex = _config.selectedIndex;
			this.suckTop = _config.suckTop;
			this.isInitSelect = true;
		},
		startup: function ($super) {
			var self = this;
			$super();
			// contents排序
			this.contents.sort(function (a, b) {
				if (a.config.order > b.config.order) return 1;
				if (a.config.order < b.config.order) return -1;
				return 0;
			});
			// 排除禁用的页签
			this._disabled = [];
			for (var i = 0; i < this.contents.length; i++) {
				if (this.contents[i].config.disable) {
					this._disabled.push(this.contents[i]);
					this.contents.splice(i, 1);
				}
			}
		},
		_clearTimer: function () {
			for (var i = 0; i < this.timers.length; i++) {
				if (this.timers[i]) {
					clearInterval(this.timers[i]);
				}
			}
			this.timers = [];
		},
		/*支持伸缩模式下，处理普通页签*/
		supportExpandFrame: function () {
			var self = this;
			this.frame.find("[data-lui-mark='panel.nav.more.frame']").each(function (index, domEle) {
				var navFrame = $(domEle);
				var navTitle = null;
				var navSwitch = null;
				navFrame.click(function () {
					self.setSelectedIndex(index);
					self.element.find('.lui_tabpanel_multiCollapse_container').removeClass("lui_tabpanel_multiCollapse_selected");
					$(this).parent().parent().addClass("lui_tabpanel_multiCollapse_selected");
				});
				navSwitch = navFrame.attr("data-lui-switch-class");
				if (navFrame.find("[data-lui-mark='panel.nav.title']").length > 0) {
					navTitle = navFrame.find("[data-lui-mark='panel.nav.title']").get(0);
				}
				self.navs.push({"navFrame": navFrame, "navTitle": navTitle, "navSwitch": navSwitch});
			});
			// 是否显示更多
			var __width = self.frame.find(".lui_tabpanel_sucktop_item_iframe").width();
			var _total_width = 0;
			self.frame.find("[data-lui-mark='panel.nav.frame']").each(function (i, n) {
				_total_width += $(n).outerWidth(true);
			});
			if (_total_width < __width) {
				// 隐藏更多
				self.frame.find(".lui_tabpanel_collapse_navs_div").hide();
				self.frame.find("[data-lui-mark='panel.nav.morenode']").hide();
			} else {
				// 显示更多
				self.frame.find(".lui_tabpanel_collapse_navs_div").show();
				self.frame.find("[data-lui-mark='panel.nav.morenode']").show();
			}
			// 点击非更多区域时，隐藏更多菜单
			var moreNodes = self.frame.find("[data-lui-mark='panel.nav.morenode']");
			if (moreNodes.length > 0) {
				$(document).click(function (event) {
					var isHide = true;
					$(event.target).parents().each(function (i, n) {
						if ($(n).hasClass("lui_tabpanel_collapse_navs_div")) {
							isHide = false;
							return false;
						}
					});
					if (isHide) {
						self.element.find('.lui_tabpanel_collapse_navs_div').hide();
						self.element.find('.lui_tabpanel_collapse_navs_list').hide();
						moreNodes.removeClass(moreNodes.attr('data-lui-switch-class'));
					}
				});
			}
		},
		doLayout: function ($super, obj) {
			$super(obj);
			var self = this;
			//处理切换
			this.navs = [];
			this.groupTitle = [];
			this.timers = [];
			var extendType;
			if (self.layout.config && self.layout.config.param) {
				extendType = self.layout.config.param.extend;
			}
			if (this.vars.supportExpand == 'true') {
				this.supportExpandFrame();
				$(document).on("slideSpread", function () {
					setTimeout(function () {
						self.supportExpandFrame();
					}, 500);
				});
			}
			if (extendType == "multiCollapse") {
				if (this.contentsNode.length > 0) {
					for (var i = 0; i < this.contents.length; i++) {
						var obj = this.contents[i];
						var contentTitle = obj.title;
						if (contentTitle.indexOf("/") > -1) {
							var titleArr = contentTitle.split("/");
							if ($.inArray(titleArr[0], this.groupTitle) == -1) {
								this.groupTitle.push(titleArr[0]);
							}
						} else {
							if ($.inArray(contentTitle, this.groupTitle) == -1) {
								this.groupTitle.push(contentTitle);
							}
						}
					}
				}
				this.groupTitlesFrame = this.frame.find("[data-lui-mark='panel.nav.groupTitle']");
				this.groupTitlesFrame.each(function (index, domEle) {
					var groupNavFrame = $(domEle);
					var navCount = groupNavFrame.attr("data-count");
					var ulCount = groupNavFrame.parent().find("[data-lui-mark='panel.nav.frame']");
					//当没有分组时，点击直接切换
					if (ulCount.length == 1) {
						groupNavFrame.parent().find('.lui_tabpanel_multiCollapse_groupTitle').addClass('noArrow');
					} else {
						groupNavFrame.bind("mouseover", function (e) {
							self._clearTimer();
							self.element.find('.lui_tabpanel_multiCollapse_groupUl').hide();
							var groupUl = $(this).parent().find('.lui_tabpanel_multiCollapse_groupUl');
							groupUl.show();
							groupNavFrame.bind("mouseout", function () {
								self._clearTimer();
								if (groupUl.is(':visible')) {
									var timer = setInterval(function () {
										setTimeout(function () {
											groupUl.hide();
										}, 500);
										self._clearTimer();
									}, 50);
									self.timers.push(timer);
								}
								self.element.find(".lui_tabpanel_multiCollapse_content_l").unbind().bind("mouseover", function () {
									self._clearTimer();
									if (groupUl.is(':visible')) {
										var timer = setInterval(function () {
											groupUl.hide();
											self._clearTimer();
										}, 50);
										self.timers.push(timer);
									}
								});
							});
							groupUl.unbind().bind('mouseover', function (e) {
								e.stopPropagation();
								self._clearTimer();
							});
						});
					}
					//点击标题默认选中第一个
					groupNavFrame.click(function () {
						self.element.find('.lui_tabpanel_multiCollapse_container').removeClass("lui_tabpanel_multiCollapse_selected");
						$(this).parent().addClass("lui_tabpanel_multiCollapse_selected");
						self.setSelectedIndex(navCount);
						var thisOrder = $(this).attr("data-order");

						self.groupTitlesFrame.each(function (index, domElex) {
							var groupNavFramex = $(domElex);
							var navOrder = groupNavFramex.attr("data-order");
							groupNavFramex.find(".lui_tabpanel_multiCollapse_groupTitle").text(self.groupTitle[navOrder]);
						});

						var title = $(this).parent().find(".lui_tabpanel_navs_item_title");
						$(this).find(".lui_tabpanel_multiCollapse_groupTitle").text(title.html());
					});
				});
			}
			//左侧小图标垂直多标签
			if (extendType == "icon" && self.layout.config.src.indexOf("tabpanel_vertical_icon.tmpl") >= 0) {
				for (var i = 0; i < self.contents.length; i++) {
					var obj = self.contents[i];
					var $div = $("<div class='lui-fm-tab-body'></div>");
					obj.element.children().prependTo($div)
					obj.element.append($div);
					obj.element.prepend("<div class='lui-fm-tab-title'>" + obj.title + "</div>")
				}
				var _itemAction = self.element.find('.lui_tabpanel_vertical_icon_navs_c .lui_tabpanel_vertical_icon_navs_item_l');
				_itemAction.hover(function () {
					self.element.closest(".lui-fm-stickyR").css('z-index', '4');
				}, function () {
					self.element.closest(".lui-fm-stickyR").css('z-index', '1');
				});
			}
			this.titlesFrame.each(function (index, domEle) {
				var navFrame = $(domEle);
				navFrame.data("index", index);
				var navTitle = null;
				var navSwitch = null;
				navFrame.unbind('click').click(function () {
					//门户组件待办待阅数量刷新
					var index = $(this).data("index");
					//门户组件待办待阅数量刷新
					var flag = typeof ($(this).find(".com_prompt_num")[0]) == "undefined";
					var promptNum = $(this).find(".com_prompt_num").html();
					//#100811待办刷新问题
					if (promptNum == "99+")
						promptNum = 99
					if ((promptNum == "" || typeof (promptNum) == "undefined") && !flag)
						promptNum = 0;

					if (!isNaN(promptNum) && promptNum >= 0) {
						if (self.contents[index].frame) {
							setTimeout(function () {
								$(self.contents[index].frame).each(function () {
									$(this.getElementsByTagName("IFRAME")).each(function () {
										if (this.contentWindow.document.body != null) {
											this.contentWindow.location.reload(true);
										}
									});
								});
							}, 100)
						}
					}
					if (self.router) {
						var ___content = self.contents[index];
						if (___content.route) {
							self.setSelecedIndexByRouter(index);
							return;
						}
					}
					self.setSelectedIndex(index);
					//分组多页签
					this.groupTitlesFrame = self.frame.find("[data-lui-mark='panel.nav.groupTitle']");
					this.groupTitlesFrame.each(function (index, domEle) {
						var groupNavFrame = $(domEle);
						var navOrder = groupNavFrame.attr("data-order");
						groupNavFrame.find(".lui_tabpanel_multiCollapse_groupTitle").text(self.groupTitle[navOrder]);
					});
					var groupTitle = $(this).parent().parent().find("[data-lui-mark='panel.nav.groupTitle']");
					groupTitle.find(".lui_tabpanel_multiCollapse_groupTitle").text($(this).find(".lui_tabpanel_navs_item_title").html());
					self.element.find('.lui_tabpanel_multiCollapse_container').removeClass("lui_tabpanel_multiCollapse_selected");
					$(this).parent().parent().addClass("lui_tabpanel_multiCollapse_selected");
				});
				navSwitch = navFrame.attr("data-lui-switch-class");
				if (navFrame.find("[data-lui-mark='panel.nav.title']").length > 0) {
					navTitle = navFrame.find("[data-lui-mark='panel.nav.title']").get(0);
				}
				self.navs.push({"navFrame": navFrame, "navTitle": navTitle, "navSwitch": navSwitch});
			});

			var refreshsFrame = this.element.find('[data-lui-mark="panel.nav.refresh"]');
			if (refreshsFrame && refreshsFrame.length > 0) {
				refreshsFrame.each(function (index, _dom) {
					var dom = $(_dom);
					dom.click(function () {
						self.contents[index].refresh();
					});
				});
			}

			this.on("selectChange", function (data) {
				this.setSelectedIndex(data.index);
				var extendType;
				if (this.layout.config && this.layout.config.param) {
					extendType = this.layout.config.param.extend;
				}
				if (extendType == "multiCollapse") {
					var selectedNode = this.element.find('.lui_tabpanel_multiCollapse_container').get(0);
					$(selectedNode).addClass("lui_tabpanel_multiCollapse_selected");
					var title = $(selectedNode).find(".lui_tabpanel_navs_item_title").get(0);
					$(selectedNode).find(".lui_tabpanel_multiCollapse_groupTitle").text($(title).html());
				}
			});
			this.emit("layoutDone");
			if (this.isInitSelect) {
				if (this.router) {
					var ___content = this.contents[this.selectedIndex];
					if (___content.route) {
						this.element.hide();
						return;
					}
				}
				this.emit("selectChange", {"index": this.selectedIndex});
			}
			if (this.vars.supportExpand == 'true') {
				var _self = this;
				var moreNodes = this.element.find("[data-lui-mark='panel.nav.morenode']").get(0);
				if (moreNodes) {
					$(moreNodes).bind("mouseover", function (e) {
						_self._clearTimer();
						_self.element.find('.lui_tabpanel_collapse_navs_div').show();
						_self.element.find('.lui_tabpanel_collapse_navs_list').show();
						$(moreNodes).addClass($(moreNodes).attr('data-lui-switch-class'));
						$(moreNodes).bind("mouseout", function () {
							// 判断是否在更多菜单里面
							setTimeout(function () {
								_self.element.find('.lui_tabpanel_collapse_navs_list').mouseout(function () {
									_self._clearTimer();
									if (_self.element.find('.lui_tabpanel_collapse_navs_div').is(':visible')) {
										var timer = setInterval(function () {
											setTimeout(function () {
												_self.element.find('.lui_tabpanel_collapse_navs_div').hide();
												_self.element.find('.lui_tabpanel_collapse_navs_list').hide();
												$(moreNodes).removeClass($(moreNodes).attr('data-lui-switch-class'));
											}, 500);
											_self._clearTimer();
										}, 50);
										_self.timers.push(timer);
									}
								});
							}, 500);
						});
						_self.element.find('.lui_tabpanel_collapse_navs_div').unbind().bind('mouseover', function (e) {
							e.stopPropagation();
							_self._clearTimer();
						});
					});
				}
			} else
				//折叠多页签宽度计算
			if (this.vars.count && this.vars.count != '0') {
				//var containerWidth = this.element.width();
				var content_length = this.contents.length;
				var v_count = content_length;
				if (this.vars.count && content_length > parseInt(this.vars.count)) {
					v_count = parseInt(this.vars.count) + 1;
				}
				var _self = this;
				//var itemWidth = containerWidth/v_count;
				var itemWidth = (Math.floor(1000 / v_count) - 1) / 10.0;
				var sumWidth = 0;
				var moreNodes = this.element.find("[data-lui-mark='panel.nav.morenode']").get(0);
				if (moreNodes) {
					$(moreNodes).bind("mouseover", function (e) {
						_self._clearTimer();
						_self.element.find('.lui_tabpanel_collapse_navs_list').show();
						if (_self.vars.supportExpand == 'true') {
							_self.element.find('.lui_tabpanel_collapse_navs_div').show();
						}
						$(moreNodes).addClass($(moreNodes).attr('data-lui-switch-class'));
						$(moreNodes).bind("mouseout", function () {
							_self._clearTimer();
							if (_self.element.find('.lui_tabpanel_collapse_navs_list').is(':visible')) {
								var timer = setInterval(function () {
									setTimeout(function () {
										_self.element.find('.lui_tabpanel_collapse_navs_list').hide();
										if (_self.vars.supportExpand == 'true') {
											_self.element.find('.lui_tabpanel_collapse_navs_div').hide();
										}
										$(moreNodes).removeClass($(moreNodes).attr('data-lui-switch-class'));
									}, 500);
									_self._clearTimer();
								}, 50);
								_self.timers.push(timer);
							}
						});
						_self.element.find('.lui_tabpanel_collapse_navs_list').unbind().bind('mouseover', function (e) {
							e.stopPropagation();
							_self._clearTimer();
						});
					});
				}
				if (this.vars.average != 'false') {
					this.titlesFrame.each(function (index, domEle) {
						if (index != v_count - 1) {
							var navFrame = $(domEle);
							navFrame.css('width', itemWidth + '%');
							sumWidth += itemWidth;
						} else {
							if (moreNodes) {
								$(moreNodes).css('width', (100 - sumWidth) + '%');
							} else {
								var navFrame = $(domEle);
								navFrame.css('width', (100 - sumWidth) + '%');
							}
						}
					});
				}
			}

			//自动轮播
			if (this.vars.timeInterval && this.vars.timeInterval != '0') {
				var timeInterval = parseInt(this.vars.timeInterval);
				var _self = this;
				var doSetInterval = function () {
					_self.currentIndex = parseInt(_self.currentIndex) + 1;
					if (_self.currentIndex == _self.contents.length) {
						_self.currentIndex = 0;
					}
					_self.setSelectedIndex(_self.currentIndex);
				};
				this.intervalFn = setInterval(doSetInterval, timeInterval * 1000);
				this.element.bind('mouseover', function (e) {
					e.preventDefault();
					clearInterval(_self.intervalFn);
				});
				this.element.bind('mouseout', function (e) {
					e.preventDefault();
					_self.intervalFn = setInterval(doSetInterval, timeInterval * 1000);
				});
			}
			//只有sys.ui.tabpanel.sucktop布局，且搭配流程右侧审批模式的页面，吸顶方可生效
			if (this.suckTop) {
				var suckNacs = this.element.find(".lui_tabpanel_sucktop_navs_l");
				if (suckNacs.length > 0) {
					var suckNum = 0;
					var criticalTime = 0;
					var oldHeight = 0;
					var isViewRightModel = false;
					var $obj = $(".lui-fm-flexibleL-inner");
					if ($("body").hasClass("view_right_model")) {
						$obj = $(document);
						isViewRightModel = true;
					}
					$obj.scroll(function (event) {
						var pageTabHeight = self.element[0].offsetTop + 45;
						var scrollTop = $obj.scrollTop();
						var curTime = new Date();
						var $fillDom = self.element.find(".lui_tabpanel_sucktop_content_c");
						var isIE8 = navigator.userAgent.indexOf("MSIE") > -1 && document.documentMode == null || document.documentMode <= 8;
						if (suckNum) {
							if (curTime - criticalTime < 200) {
								$obj.scrollTop(pageTabHeight);
							}
						}
						if (!suckNum && scrollTop > pageTabHeight) {
							suckNum++;
							criticalTime = curTime;
							var h = document.documentElement.clientHeight || document.body.clientHeight;
							$fillDom.css("min-height", h - 70);
							$('.lui_form_path_frame_fixed_inner').append(suckNacs.prop("outerHTML"));
							$('.lui_form_path_frame_fixed_inner .lui_tabpanel_sucktop_navs_c').css("padding-left", "15px").width($(".lui_tabpanel_sucktop_frame .lui_tabpanel_sucktop_navs_c").width())
							var moreNodes = $(".lui_form_path_frame_fixed_inner [data-lui-mark='panel.nav.morenode']").get(0);
							if (moreNodes) {
								$(moreNodes).bind("mouseover", function (e) {
									_self._clearTimer();
									$('.lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_navs_list').show();
									if (_self.vars.supportExpand == 'true') {
										$('.lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_navs_div').show();
										$(moreNodes).addClass($(moreNodes).attr('data-lui-switch-class'));
									}
									$(moreNodes).bind("mouseout", function () {
										_self._clearTimer();
										if ($('.lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_navs_list').is(':visible')) {
											var timer = setInterval(function () {
												setTimeout(function () {
													$('.lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_navs_list').hide();
													if (_self.vars.supportExpand == 'true') {
														$('.lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_navs_div').hide();
													}
													$(moreNodes).removeClass($(moreNodes).attr('data-lui-switch-class'));
												}, 500);
												_self._clearTimer();
											}, 50);
											_self.timers.push(timer);
										}
									});
									$('.lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_navs_list').unbind().bind('mouseover', function (e) {
										e.stopPropagation();
										_self._clearTimer();
									});
								});
								// 选项卡吸顶后 点击非更多区域时隐藏更多菜单
								if (_self.vars.supportExpand == 'true') {
									var fixMoreNodes = $(".lui_form_path_frame_fixed_inner [data-lui-mark='panel.nav.morenode']");
									if (fixMoreNodes.length > 0) {
										$(document).click(function (event) {
											var isHide = true;
											$(event.target).parents().each(function (i, n) {
												if ($(n).hasClass("lui_tabpanel_collapse_navs_div")) {
													isHide = false;
													return false;
												}
											});
											if (isHide) {
												$('.lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_navs_div').hide();
												$('.lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_navs_list').hide();
												fixMoreNodes.removeClass(fixMoreNodes.attr('data-lui-switch-class'));
											}
										});
									}
								}
							}
							var titlesFrame = $(".lui_form_path_frame_fixed_inner [data-lui-mark='panel.nav.frame']");
							titlesFrame.each(function (index, domEle) {
								var navFrame = $(domEle);
								navFrame.click(function () {
									$(self.titlesFrame[index]).click();
									$obj.scrollTop(pageTabHeight);
									if ($(this).attr("data-more") == "true") {
										var text = $(this).find('.lui_tabpanel_navs_item_title').text();
										$(".lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_more_title").text(text);
										$(moreNodes).addClass($(moreNodes).attr('data-lui-switch-class'));
										$(".lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_navs_list").hide();
										if (self.vars.supportExpand == 'true') {
											$(".lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_navs_div").hide();
										}
									} else {
										$(moreNodes).removeClass($(moreNodes).attr('data-lui-switch-class'));
										$(".lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_more_title").text(lang['layout.tabpanel.collapse.more']);
									}
									titlesFrame.removeClass('lui_tabpanel_sucktop_navs_item_selected');
									navFrame.addClass('lui_tabpanel_sucktop_navs_item_selected');
								});
							});
							// supportExpand=true 文档底部页签展开折叠组件 吸顶
							if (_self.vars.supportExpand == 'true') {
								var supportMoreNodes = $(".lui_form_path_frame_fixed_inner [data-lui-mark='panel.nav.more.frame']")
								if (supportMoreNodes.length > 0) {
									supportMoreNodes.each(function (index, domEle) {
										var moreFrame = $(domEle);
										moreFrame.click(function () {
											$obj.scrollTop(pageTabHeight);
											supportMoreNodes.removeClass('lui_tabpanel_collapse_navs_item_selected');
											moreFrame.addClass('lui_tabpanel_collapse_navs_item_selected');

											$('.lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_navs_list').hide();
											$('.lui_form_path_frame_fixed_inner .lui_tabpanel_collapse_navs_div').hide();
											self.setSelectedIndex(index);
										});
									})
								}
							}

							$('.lui_toolbar_frame_float_mark').css('height', 74);
							setTimeout(function () {
								if (!isIE8) {
									$(".lui_form_body").css("margin-top", $(".lui_toolbar_frame_float_mark").outerHeight(true));
								}
								if (!isViewRightModel) {
									var h = document.documentElement.clientHeight || document.body.clientHeight;
									$(".lui-fm-flexibleL-inner").css("height", h - 75 - 32);
									$(".lui-fm-stickyR-inner").css("height", h - 75 - 32 + 30);
								}
							}, 100);
						}
						if (suckNum && scrollTop < pageTabHeight - 5) {
							suckNum--;
							$fillDom.css("min-height", "");
							$('.lui_form_path_frame_fixed .lui_form_path_frame_fixed_outer .lui_form_path_frame_fixed_inner .lui_tabpanel_sucktop_navs_l').remove();
							$('.lui_toolbar_frame_float_mark').css('height', 42);
							setTimeout(function () {
								if (!isIE8) {
									$(".lui_form_body").css("margin-top", $(".lui_toolbar_frame_float_mark").outerHeight(true));
								}
								if (!isViewRightModel) {
									var h = document.documentElement.clientHeight || document.body.clientHeight;
									$(".lui-fm-flexibleL-inner").css("height", h - 75);
									$(".lui-fm-stickyR-inner").css("height", h - 75 + 30);
								}
							}, 100);
						}
					});
				}
			}

			// 处理页签显示名称
			for (var i = 0; i < self.titlesNode.length; i++) {
				var titleNode = $(self.titlesNode[i]);
				var __text = titleNode.text();
				titleNode.attr('title', __text);
			}
			// 处理“展开/收起”
			var is_expand = self.element.parent().find(".tabPanel_frame").hasClass("tabPanel_expand_frame");
			self.element.find(".lui_tabpanel_collapse_extend_title").text(is_expand ? lang['ui.tabPage.collapsed'] : lang['ui.tabPage.uncollapsed']);
			this.frame.find("[data-lui-mark='panel.nav.extend']").click(function () {
				var _frame = self.element.parent().find(".tabPanel_frame");
				var is_expand = _frame.hasClass("tabPanel_expand_frame");
				if (is_expand) {
					_frame.removeClass("tabPanel_expand_frame");
					_frame.addClass("tabPanel_retract_frame");
					self.element.parent().find(".lui_tabpanel_collapse_extend_title").text(lang['ui.tabPage.uncollapsed']);
				} else {
					_frame.removeClass("tabPanel_retract_frame");
					_frame.addClass("tabPanel_expand_frame");
					self.element.parent().find(".lui_tabpanel_collapse_extend_title").text(lang['ui.tabPage.collapsed']);
					// 展开时，如果没有默认选中，需要选中第一个
					var def;
					self.frame.find("[data-lui-mark='panel.nav.frame']").each(function (i, n) {
						_total_width += $(n).outerWidth(true);
						def = $(n).hasClass("lui_tabpanel_sucktop_navs_item_selected");
						if (def) return false;
					});
					if (!def) {
						$(self.frame.find("[data-lui-mark='panel.nav.frame']")[0]).addClass("lui_tabpanel_sucktop_navs_item_selected");
					}
				}
				var morenode = self.frame.find("[data-lui-mark='panel.nav.morenode']");
				if (morenode && morenode.length > 0) {
					// 获取总宽度
					var total_width = self.frame.find(".lui_tabpanel_sucktop_item_iframe").width();
					var _total_width = 0;
					self.frame.find("[data-lui-mark='panel.nav.frame']").each(function (i, n) {
						_total_width += $(n).outerWidth(true);
					});
					if (_total_width < total_width) {
						// 删除更多
						self.frame.find(".lui_tabpanel_collapse_navs_div").remove();
						morenode.remove();
					}
				}
			});
			this.emit("layoutFinished");
		},

		setSelecedIndexByRouter: function (i) {
			function toJSON(str) {
				return (new Function("return (" + str + ");"))();
			}

			var content = this.contents[i],
				route = typeof (content.route) == 'string' ? toJSON(content.route) : content.route,
				$router = routerUtils.getRouter();
			$router.push(route.path, route.params);
			this.selectedIndex = i;
		},

		//设置选中哪个Tab
		setSelectedIndex: function (i) {
			var self = this;
			var hasJGWebOffice = false; // 页签中是否包含金格控件
			var hasJGSurread = false; // 页签中是否包含超阅控件 value=1显示 value=0 隐藏
			var beforeContentScrollTop = $(window).scrollTop(); // 切换Tab之前的浏览器滚动条位置
			this.element.show();
			var evt = {
				index: {before: this.selectedIndex, after: i},
				panel: this,
				cancel: false
			};
			this.emit("indexChanged", evt);
			if (!evt.cancel) {

				// 隐藏除当前选中之外其它Tab内容
				for (var j = 0; j < this.contents.length; j++) {
					if (this.contentsNode && this.navs && j != i) {

						// 隐藏非当前选中页签下金格控件
						//(注：切换页签时，包含了金格控件的页签不能使用display:none进行隐藏，否则会显示异常，详见#62887、#80630,下面的逻辑是判断在火狐和谷歌浏览器下通过给DIV加z-index层覆盖的方式来实现页签切换)
						if (navigator.userAgent.indexOf("Firefox") >= 0 || navigator.userAgent.indexOf("Chrome") >= 0) {
							var JGWebOffice_Obj_Array = this.contentsNode[j].find("object[id*='JGWebOffice_']");
							var JGSurread_Obj_Array = this.contentsNode[j].find("object[id*='surread']");
							if (JGWebOffice_Obj_Array.length > 0) {
								hasJGWebOffice = true;
								JGWebOffice_Obj_Array.each(function (idx, _obj) {
									if (_obj.HidePlugin) {
										_obj.HidePlugin(0);
									}
								});
								$(this.contentsNode[j]).addClass("lui_panel_JGWebOffice_content").css("z-index", -1);
							} else if (JGSurread_Obj_Array.length > 0) {
								hasJGSurread = true;
								JGSurread_Obj_Array.each(function (idx, _obj) {
									if (_obj.HidePlugin) {
										_obj.value = "0";
										_obj.HidePlugin(0);
									}
								});
								$(this.contentsNode[j]).addClass("lui_panel_JGWebOffice_content").css("z-index", -1);
							} else {
								var iframes = this.contentsNode[j].find('iframe');

								if (iframes.length > 0) {
									try {
										iframes.each(function () {
											if (this.id != "office-iframe") { //wps嵌入的iframe不处理
												var JGWebOffice_Obj_Array1 = $(this).contents().find("object[id*='JGWebOffice_']");
												if (JGWebOffice_Obj_Array1.length > 0) {
													hasJGWebOffice = true;
													JGWebOffice_Obj_Array1.each(function (idxx, _objx) {
														if (_objx.HidePlugin) {
															_objx.HidePlugin(0);
														}
													});
												}

												var JGSurread_Obj_Array1 = $(this).contents().find("object[id*='surread']");
												if (JGSurread_Obj_Array1.length > 0) {
													hasJGSurread = true;
													JGSurread_Obj_Array1.each(function (idxx, _objx) {
														if (_objx.HidePlugin) {
															_obj.value = "0";
															_objx.HidePlugin(0);
														}
													});
												}

											}
										});
									} catch (e) {
										if (window.console)
											console.log(e);
									}

									if (hasJGWebOffice || hasJGSurread) {
										$(this.contentsNode[j]).addClass("lui_panel_JGWebOffice_content").css("z-index", -1);
									} else {
										// 隐藏页签内容
										this.contentsNode[j].hide();
									}
								} else {
									// 隐藏页签内容
									this.contentsNode[j].hide();
								}
							}
						} else {
							// 隐藏页签内容
							this.contentsNode[j].hide();
						}

						// 移除标题选中样式
						if (this.navs[j].navFrame && this.navs[j].navSwitch) {
							this.navs[j].navFrame.removeClass(this.navs[j].navSwitch);
						}
					}
				}
				if (this.contentsNode && this.navs) {
					// 显示当前选中页签下的金格控件
					if (navigator.userAgent.indexOf("Firefox") >= 0 || navigator.userAgent.indexOf("Chrome") >= 0) {
						var JGWebOffice_Obj_Array = this.contentsNode[i].find("object[id*='JGWebOffice_']");
						var JGSurread_Obj_Array = this.contentsNode[i].find("object[id*='surread']");
						if (JGWebOffice_Obj_Array.length > 0) {
							hasJGWebOffice = true;
							JGWebOffice_Obj_Array.each(function (idx, _obj) {
								if (_obj.HidePlugin) {
									_obj.HidePlugin(1);
								}
							});
						}
						if (JGSurread_Obj_Array.length > 0) {
							hasJGSurread = true;
							JGSurread_Obj_Array.each(function (idx, _obj) {
								if (_obj.HidePlugin) {
									_obj.value = "1";
									_obj.HidePlugin(1);
								}
							});
						}
						var iframes = this.contentsNode[i].find('iframe');
						if (iframes.length > 0) {
							try {
								iframes.each(function () {
									if (this.id != "office-iframe") { //wps嵌入的iframe不处理
										var JGWebOffice_Obj_Array2 = $(this).contents().find("object[id*='JGWebOffice_']");
										if (JGWebOffice_Obj_Array2.length > 0) {
											hasJGWebOffice = true;
											JGWebOffice_Obj_Array2.each(function (idxa, _obja) {
												if (_obja.HidePlugin) {
													_obja.HidePlugin(1);
												}
											});
										}
										var JGSurread_Obj_Array2 = $(this).contents().find("object[id*='surread']");
										if (JGSurread_Obj_Array2.length > 0) {
											hasJGSurread = true;
											JGSurread_Obj_Array2.each(function (idxa, _obja) {
												if (_obja.HidePlugin) {
													_obj.value = "1";
													_obja.HidePlugin(1);
												}
											});
										}
									}
								});
							} catch (e) {
								if (window.console)
									console.log(e);
							}
						}
						if (hasJGWebOffice || hasJGSurread) {
							$(this.contentsNode[i]).addClass("lui_panel_JGWebOffice_content").css("z-index", 0);
						}
					}
					this.contentsNode[i].show();
					this.contents[i].load();
					if (this.navs[i].navFrame && this.navs[i].navSwitch) {
						// 添加标题选中样式
						this.navs[i].navFrame.addClass(this.navs[i].navSwitch);
						//折叠多页签
						var isMoreItem = this.navs[i].navFrame.attr("data-more");
						var moreNodes = this.element.find("[data-lui-mark='panel.nav.morenode']").get(0);

						if (isMoreItem && isMoreItem == 'true') {
							var text = this.navs[i].navFrame.find('.lui_tabpanel_navs_item_title').text();
							this.element.find(".lui_tabpanel_collapse_more_title").text(text);
							if (this.vars.supportExpand == 'true') {
								$(moreNodes).removeClass($(moreNodes).attr('data-lui-switch-class'));
							} else {
								$(moreNodes).addClass($(moreNodes).attr('data-lui-switch-class'));
							}
							$('.lui_tabpanel_collapse_navs_item_c').css('text-align', 'center');
							this.element.find('.lui_tabpanel_collapse_navs_list').hide();
							if (this.vars.supportExpand == 'true') {
								this.element.find('.lui_tabpanel_collapse_navs_div').hide();
							}
						} else {
							$(moreNodes).removeClass($(moreNodes).attr('data-lui-switch-class'));
							this.element.find(".lui_tabpanel_collapse_more_title").text(lang['layout.tabpanel.collapse.more']);
						}
					}
				}
				// 如果滚动条位置有被更改，强制重置回切换Tab之前的位置(#80630)
				if ($(window).scrollTop() < beforeContentScrollTop) {
					var frequency = 10; // 执行频率(10毫秒)
					var maxDuration = 1000; // 最大时长（1000毫秒，即1秒）
					var usedTime = 0;   // 已用时长（毫秒）
					// 在1秒限时范围内，每隔10毫秒重置滚动条位置
					var setScrollTopInterval = setInterval(function () {
						usedTime = usedTime + frequency;
						$(window).scrollTop(beforeContentScrollTop);
						if (usedTime >= maxDuration) {
							clearInterval(setScrollTopInterval);
						}
					}, frequency);
				}
				this.selectedIndex = i;
				this.currentIndex = i;
			}
			// 支持伸缩
			if (this.vars.supportExpand == 'true') {
				var expand_frame = this.element.find(".tabPanel_support_expand_frame");
				if (expand_frame.hasClass("tabPanel_retract_frame")) {
					return;
				}
				// 切换普通标题样式
				var navFrames = this.element.find("[data-lui-mark='panel.nav.frame']");
				navFrames.removeClass('lui_tabpanel_sucktop_navs_item_selected');
				$(navFrames[i]).addClass('lui_tabpanel_sucktop_navs_item_selected');
				var wrap = this.element.find(".lui_tabpanel_sucktop_item_wrap");
				// 获取当前元素
				var elem = $(wrap.find("[data-lui-mark='panel.nav.frame']")[i]);
				// 获取总宽度
				var total_width = this.frame.find(".lui_tabpanel_sucktop_item_iframe").width();
				// 当前元素的宽度
				var cur_width = elem.outerWidth(true);
				// 当前元素将到移动到的位置
				var target_position = total_width - cur_width;
				// 计算偏差 = 总宽度 - 目标位置 - 当前元素宽度 - 更多按钮的宽度
				var deviation = total_width - elem.position().left - cur_width - 30;
				if (deviation < 0) {
					wrap.animate({left: deviation + 'px'});
				} else if (wrap.position().left != 0) {
					wrap.animate({left: '0px'});
				}

				// 文档底部页签展开折叠组件 吸顶
				if (this.suckTop && this.vars.supportExpand == 'true') {
					var expand_nav = $('.lui_form_path_frame_fixed_inner .lui_tabpanel_sucktop_navs_supportExpand');
					if (expand_nav.length > 0) {
						var expand_more = $('.lui_form_path_frame_fixed_inner [data-lui-mark = "panel.nav.more.frame"]')
						var expand_switch_more = expand_more.eq(i).attr('data-lui-switch-class')
						// 更多 添加选中样式
						expand_more.removeClass(this.navs[i].navSwitch);
						expand_more.eq(i).addClass(this.navs[i].navSwitch);

						// 切换普通标题样式
						var fixNavFrames = expand_nav.find("[data-lui-mark='panel.nav.frame']");
						fixNavFrames.removeClass('lui_tabpanel_sucktop_navs_item_selected');
						$(fixNavFrames[i]).addClass('lui_tabpanel_sucktop_navs_item_selected');
						var fixWrap = expand_nav.find(".lui_tabpanel_sucktop_item_wrap");
						// 获取当前元素
						var elem = $(fixWrap.find("[data-lui-mark='panel.nav.frame']")[i]);
						// 获取总宽度
						var total_width = this.frame.find(".lui_tabpanel_sucktop_item_iframe").width();
						// 当前元素的宽度
						var cur_width = elem.outerWidth(true);
						// 当前元素将到移动到的位置
						var target_position = total_width - cur_width;
						// 计算偏差 = 总宽度 - 目标位置 - 当前元素宽度 - 更多按钮的宽度
						var deviation = total_width - elem.position().left - cur_width - 30;
						if (deviation < 0) {
							fixWrap.animate({left: deviation + 'px'});
						} else if (fixWrap.position().left != 0) {
							fixWrap.animate({left: '0px'});
						}
					}
				}
			}
		}

	});
	var Panel = AbstractPanel.extend({
		initProps: function ($super, cfg) {
			$super(cfg);
			this.memoryExpand = cfg.memoryExpand ? ("Panel.mExp-" + Com_Parameter.CurrentUserId + "-" + cfg.memoryExpand) : false;
			this._memoryExpandObj = [];
		},
		isMemoryExpand: function (index) {
			if (this._memoryExpandObj.length) {
				if (this._memoryExpandObj.length > index) {
					return this._memoryExpandObj[index];
				}
			} else if (this.memoryExpand && window.localStorage && window.JSON) {
				try {
					var mData = localStorage.getItem(this.memoryExpand);
					if (mData != null && mData.length > 0) {
						var mObj = JSON.parse(mData);
						this._memoryExpandObj = mObj;
						if (mObj.length) {
							if (mObj.length > index) {
								return mObj[index];
							}
						}
					}
				} catch (e) {
					if (window.console)
						console.log(e.name);
				}
			}
			return this.contents[index].getExpand();
		},
		writeMemoryExpand: function (index, expand) {
			if (this.memoryExpand && window.localStorage && window.JSON) {
				try {
					var mData = localStorage.getItem(this.memoryExpand);
					var mObj = (mData != null && mData.length > 0) ? JSON.parse(mData) : [];
					mObj[index] = expand;
					localStorage.setItem(this.memoryExpand, JSON.stringify(mObj));
					this._memoryExpandObj = mObj;
				} catch (e) {
					if (window.console)
						console.log(e.name);
				}
			}
		},
		writeMemoryExpands: function (expandArray) {
			if (this.memoryExpand && window.localStorage && window.JSON) {
				try {
					window.localStorage.setItem(this.memoryExpand, JSON.stringify(expandArray));
				} catch (e) {
					if (window.console)
						console.log(e.name);
				}
			}
		},
		doLayout: function ($super, obj) {
			$super(obj);
			var expandArray = [];
			for (var j = 0; j < this.contents.length; j++) {
				if (this.contents[j].getToggle()) {
					if (this.isMemoryExpand(j)) {
						expandArray.push(true);
						this.onToggle(j, true);
					} else {
						expandArray.push(false);
						this.onToggle(j, true, true);
					}
				} else {
					expandArray.push(true);
					this.onToggle(j, true);
				}
			}
			this.writeMemoryExpands(expandArray);
			this.emit("layoutDone");
		},
		onToggle: function (i, isInit, isShow, evt) {
			var self = this;
			var content = this.contentsNode[i];
			var toggle = this.togglesNode[i];
			var evt = {
				index: i,
				panel: this,
				cancel: false,
				init: isInit,
				content: content,
				visible: isShow || this.contents[i].isShow
			};
			self.emit("toggleBefore", evt);
			if (!evt.cancel) {
				if (evt.visible) {
					if (evt.init) {
						content.hide();
					} else {
						content.slideUp(300);
					}
					if (toggle) {
						toggle.addClass(toggle.attr("data-lui-switch-class"));
					}
				} else {

					if (evt.init) {
						content.show();
						this.contents[i].load();
					} else {
						content.slideDown(300, function () {
							self.contents[i].load();
						});
						//将其他content收起
						if (this.config.toggle == 'simple') {
							for (var m = 0; m < this.contents.length; m++) {
								if (m != i) {
									if (this.contents[m].isShow && this.contents[m].isDrawed && this.contents[m].getToggle()) {
										this.contentsNode[m].slideUp(300);
										this.contents[m].isShow = false;
										if (this.togglesNode[m]) {
											this.togglesNode[m].addClass(toggle.attr("data-lui-switch-class"));
										}
									}
								}
							}
						}
					}

					if (toggle) {
						toggle.removeClass(toggle.attr("data-lui-switch-class"));
					}
				}
			}
			if (evt.visible) {
				this.contents[i].isShow = false;
			} else {
				this.contents[i].isShow = true;
			}
			if (!evt.init) {
				this.writeMemoryExpand(i, !evt.visible);
			}
			self.emit("toggleAfter", evt);
		}
	});
	var NonePanel = Panel.extend({});
	var AccordionPanel = Panel.extend({
		startup: function ($super) {
			var self = this;
			$super();
			topic.group(this.config.channel).subscribe("addContent", function (evt) {
				self.addContent(evt.data);
			}, self);
			topic.group(this.config.channel).subscribe("removeContent", function (evt) {
				if (evt.data != null) {
					if (evt.data.target.id != null) {
						self.removeContentById(evt.data.target.id);
					} else if (evt.data.target.index != null) {
						self.removeContentByIndex(evt.data.target.index);
					}
				}
			}, self);
		},
		removeContentByIndex: function (i) {
			if (this.contents[i] != null) {
				this.layout.emit("removeContent", this.contents[i]);
				this.contents.splice(i, 1);
				this.titlesNode.splice(i, 1);
				this.togglesNode.splice(i, 1);
				this.contentsNode.splice(i, 1);
				this.headersNode.splice(i, 1);
				if (this.contents.length <= 0) {
					this.element.hide();
				}
				this.resetEvent();
			}
		},
		removeContentById: function (id) {
			var content = base.byId(id);
			this.layout.emit("removeContent", content);
			for (var i = 0; i < this.contents.length; i++) {
				if (this.contents[i] == content) {
					this.contents.splice(i, 1);
					this.titlesNode.splice(i, 1);
					this.togglesNode.splice(i, 1);
					this.contentsNode.splice(i, 1);
					this.headersNode.splice(i, 1);
				}
			}
			if (this.contents.length <= 0) {
				this.element.hide();
			}
			this.resetEvent();
		},
		addContent: function (config) {
			this.element.show();
			var obj = new Content(config);
			obj.setParent(this);
			obj.startup();
			obj.draw();
			var ni = this.contents.length;
			this.addChild(obj);
			this.layout.emit("addContent", obj);
			if (this.contents.length <= 0) {
				this.element.show();
			}
		},
		expandContent: function (arguContent) {
			var contentObj = null;
			var expandIndex = -1;
			if (typeof (arguContent) == "string") {
				contentObj = base.byId(arguContent);
			} else if (typeof (arguContent) == "number") {
				contentObj = this.contentsNode[arguContent]
				expandIndex = arguContent;
			} else if (typeof (arguContent) == "object") {
				contentObj = arguContent;
			}
			if (expandIndex == -1) {
				for (var i = 0; i < this.contents.length; i++) {
					if (this.contents[i] == contentObj) {
						expandIndex = i;
						break;
					}
				}
			}
			if (!contentObj.isShow) {
				this.onToggle(expandIndex);
			}
		},
		// 重置toggle绑定事件
		resetEvent: function () {
			var self = this;
			for (var jj = 0; jj < this.headersNode.length; jj++) {
				var headNode = this.headersNode[jj];
				var toggleNode = headNode
					.find('[data-lui-mark="panel.nav.toggle"]')
				if (self.contents[jj].getToggle()) {
					headNode.unbind('click');
					~~function (jj) {
						return function () {
							headNode.click(function (evt) {
								self.onToggle(jj, null, null, evt);
							});
						}();
					}(jj);
				}
			}
		},

		parseTextMark: function (dom, isContent) {
			var self = this;
			var titleNodes = dom.find("[data-lui-mark='panel.nav.title']");
			titleNodes.each(function (index, domEle) {
				//标题文字
				var titleNode = $(domEle);
				self.titlesNode.push(titleNode);
				if (!isContent) { // 解决accordionpanel通过触发"addContent"添加时候的标题出错
					var content = self.contents[index];
					var operations = content.config.operations;
					var $operation;

					var onclick = null;

					if (operations && operations.length >= 1) {
						// 将内容中的操作按钮置于标题旁
						if ('top' == operations[0].vertical) {
							var operation = operations[0];
							var href = env.fn.variableResolver(
								operation.href,
								content.vars);
							href = env.fn.formatUrl(href);
							onclick = operation.onclick;

							var target = operation.target ? operation.target
								: '_blank';


							$operation = $('<a class="lui_portlet_operation" href="javascript:void(0);" target="'
								+ target
								+ '" title="'
								+ operation.name
								+ '" onclick>'
								+ operation.name + '</a>');
						}
					}

					if (content.__title) {
						titleNode
							.html('<span class="lui_tabpanel_navs_item_title">'
								+ content.__title
								+ '</span>');
						if (self.contents[index].title) {
							titleNode.attr('title', self.contents[index].title);
						}
					} else {
						var title = strutil
							.decodeHTML(content.title);
						titleNode
							.html('<span class="lui_tabpanel_navs_item_title">'
								+ env.fn
									.formatText(title)
								+ '</span>');

						titleNode.attr('title', title);
					}
					if ($operation) {
						$operation.appendTo(titleNode);

						// 优先处理onclick事件
						if (onclick) {

							$operation.bind('click', function (evt) {
								(new Function(onclick)).call();
								Com_EventPreventDefault();
								Com_EventStopPropagation();
							});

						} else {

							$operation.bind('click', function (evt) {

								if (href.indexOf('javascript') == 0) {
									window.open(href, target);
								} else {
									// 打开右窗口模式一律清除左侧操作信息栏选中样式
									if ('_rIframe' == target) {
										topic
											.publish('nav.operation.clearStatus');
									}
									LUI.pageOpen(href, target);
								}

								Com_EventPreventDefault();
								Com_EventStopPropagation();
							})
						}

					}


				}
			});
		}
	});
	var TabPage = AccordionPanel.extend({
		doLayout: function ($super, obj) {
			$super(obj);

			var navs = this.element.find(".lui_tabpage_float_navs");
			var collapseBtn = this.element.find(".lui_tabpage_float_collapse");

			if (this.config.collapsed) {
				navs.hide();
			}

			if (!this.config.collapsed) {
				collapseBtn.addClass("lui_tabpage_collapsed");
				collapseBtn.attr('title', lang['ui.tabPage.collapsed']);
				this.element.find(".lui_tabpage_float_collapse .txt").html(lang['ui.tabPage.collapsed']);
				this.element.find(".lui_tabpage_float_header_close").show();
			} else {
				collapseBtn.attr('title', lang['ui.tabPage.uncollapsed']);
				this.element.find(".lui_tabpage_float_collapse .txt").html(lang['ui.tabPage.uncollapsed']);
				this.element.find(".lui_tabpage_float_header_close").hide();
			}

			var _self = this;
			collapseBtn.bind('click', function (evt) {
				if (navs.is(':hidden')) {
					navs.show(_self);
					_self.element.find(".lui_tabpage_float_navs_mark").show();
					navs.addClass('fadeInRight animated');
					setTimeout(function () {
						navs.removeClass('fadeInRight animated');
					}, 500);
					collapseBtn.addClass("lui_tabpage_collapsed");
					collapseBtn.attr('title', lang['ui.tabPage.collapsed']);
					_self.element.find(".lui_tabpage_float_collapse .txt").html(lang['ui.tabPage.collapsed']);

					_self.element.find(".lui_tabpage_float_header_close").show();
				} else {
					navs.addClass('fadeOutRight animated');
					setTimeout(function () {
						navs.removeClass('fadeOutRight animated');
						_self.element.find(".lui_tabpage_float_navs_mark").hide();
						navs.hide();
					}, 500);
					collapseBtn.removeClass("lui_tabpage_collapsed");
					collapseBtn.attr('title', lang['ui.tabPage.uncollapsed']);
					_self.element.find(".lui_tabpage_float_collapse .txt").html(lang['ui.tabPage.uncollapsed']);

					_self.element.find(".lui_tabpage_float_header_close").hide();
				}
			})

		}
	});
	var Content = AbstractContent.extend({
		initProps: function ($super, _config) {
			this.element.attr("data-lui-type", 'lui/panel!Content');
			this.route = _config.route;
			// 排序号
			if (_config.order) {
				_config.order = parseInt(_config.order);
			} else {
				// 默认排序号
				_config.order = 10;
			}
			// 禁用
			if (_config.disable) {
				_config.disable = _config.disable.replace(/(^\s*)|(\s*$)/g, "").toLowerCase() == "true";
				if (_config.disable) {
					this.element.hide();
				}
			}
			$super(_config);
		},
		startup: function ($super) {
			$super();
			if (this.layout == null) {
				var config = {
					"kind": "content",
					"src": "/sys/ui/extend/panel/content.tmpl"
				};
				if (this.config.layout)
					$.extend(config, this.config.layout);
				this.layout = new layout.Template(config);
				this.layout.startup();
			}
			if (this.config.child != null) {
				for (var i = 0; i < this.config.child.length; i++) {
					var childVar = this.config.child[i];
					this.addChild(childVar);
					if (childVar.setParent)
						childVar.setParent(this);
					this.element.append(childVar.element);
				}
			}
			if (this.config.refresh != null) {
				var self = this;
				window.setInterval(function () {
					self.refresh();
				}, parseInt(this.config.refresh) * 1000);
			}
			if(this.vars && this.vars.autoRefresh == "true"){
				var self = this;
				topic.subscribe('successReloadPage', function(){
					self.refresh();
				});
			}
		},
		getToggle: function () {
			if (this.toggle == null) {
				if (this.parent.config.expand != null) {
					if (this.parent.config.toggle == "true") {
						return true;
					} else if (this.parent.config.toggle == "false") {
						return false;
					} else {
						return true;
					}
					//return this.parent.config.toggle;
				} else
					return true;
			} else {
				return this.toggle;
			}
		},
		getExpand: function () {
			if (this.expand == null) {
				if (this.parent.config.expand != null)
					return this.parent.config.expand;
				else
					return true;
			} else {
				return this.expand;
			}
		},
		preload: function (force) {//force是否再次预绘制
			var self = this;
			if (this.preLoaded || !this.children) {
				return;
			}
			for (var i = 0; i < this.children.length; i++) {
				if (this.children[i].predraw)
					this.children[i].predraw(force);
			}
			this.preLoaded = true;
		},
		load: function () {
			var self = this;
			if (this.isLoad) {
				return;
			}
			if (this.isLayout == null || this.isLayout == false) {
				setTimeout(function () {
					self.load();
				}, 20);
			} else {
				this.resetHeight();
				if (this.children) {
					for (var i = 0; i < this.children.length; i++) {
						if (this.children[i].draw)
							this.children[i].draw();
					}
				}
				this.isLoad = true;
				this.emit("show");
			}
		},
		refresh: function () {
			var isCurrent = false;
			this.preLoaded = false;
			var idx = $.inArray(this, this.parent.contents);
			if (idx > -1) {
				if (idx == this.parent.selectedIndex || this.isShow == true) {
					isCurrent = true;
				}
			}
			if (Com_Parameter.IE) {
				var frames = this.element.context.getElementsByTagName('IFRAME');
				for (var i = 0; i < frames.length; i++) {
					var frame = frames[i];
					frame.contentWindow.document.write('');//清空iframe的内容
					frame.contentWindow.close();//避免iframe内存泄漏
					frame.removeNode(true);//删除iframe
					delete frame;
					CollectGarbage();//这个方法
				}
			}
			//当前Content是否显示。
			this.preload(true);
			if (isCurrent && this.children) {
				for (var i = 0; i < this.children.length; i++) {
					if (this.children[i].refresh)
						this.children[i].refresh();
				}
			}
		},
		resize: function () {
			if (this.children) {
				for (var i = 0; i < this.children.length; i++) {
					if (this.children[i].resize)
						this.children[i].resize();
				}
			}
		},
		doLayout: function (obj) {
			var self = this;
			if (this.config.scroll == null) {
				this.config.scroll = (String(this.parent.config.scroll) == "true");
			} else {
				this.config.scroll = (String(this.config.scroll) == "true");
			}
			this.frame = $(obj);
			this.contentInside = this.frame.find("[data-lui-mark='panel.content.inside']");
			this.element.after(this.frame);
			this.contentInside.append(this.element);
			this.isLayout = true;
			this.elementSpace = this.element.outerHeight(true) - this.element.height();
			//$super,不用调用$super,$super会将所有子元素的draw调用一次，改调用会在load里面实现
		},
		getRootElement: function () {
			if (this.frame)
				return this.frame;
			else
				return this.element;
		},
		resetHeight: function () {
			var configHeight = this.parent.config.height;        // 后台配置的部件高度
			var initWidgetHeight = this.parent.element.height() ;// 页面初始时（未渲染部件内容，只包含标题栏、操作栏）的部件容器的总高度
			if(!initWidgetHeight&&this.parent.element.height()){
				setTimeout(function(){
					initWidgetHeight = this.parent.element.height();
				},200)
			}
			var widgetContentHeight = 0; // 部件内容区高度（不包括标题栏、底部操作栏高度）
			// 计算部件内容区高度
			if ((typeof (this.config.outerHeight) == "undefined" || this.config.outerHeight == null) && configHeight != null) {
				//配置与实际渲染存在误差 ,ie下渲染垂直多标签会比在谷歌上多出2px
				if (configHeight == initWidgetHeight || configHeight == initWidgetHeight - 2) {
					// 猜测是垂直多页签
					widgetContentHeight = configHeight;
				} else {
					// 计算部件内容区(排除标题栏、底部操作栏)可使用的高度
					//切换门户页面时会在部件未渲染时获取高度导致结果为0，添加了标题和底部操作栏的最小高度
					initWidgetHeight = initWidgetHeight==0?initWidgetHeight=76:initWidgetHeight;
					widgetContentHeight = configHeight - initWidgetHeight + this.contentInside.height();
					this.config.outerHeight = widgetContentHeight;
				}
			}
			if (widgetContentHeight > 0) {
				if (this.config.scroll) {
					this.contentInside.css({
						"height": widgetContentHeight,
						"min-height": widgetContentHeight,
						"overflow": "auto"
					});
				} else {
					//#91655 选择 “去除部件边距”，会出滚动条，外观配置已经选择了高度大于上面高度时自动增大,按理说都不应该出现滚动条
					this.contentInside.css({"height": "", "min-height": widgetContentHeight, "overflow": ""});
				}
				this.element.css({"min-height": widgetContentHeight - this.elementSpace});
			} else {
				this.contentInside.css({"height": "", "min-height": "", "overflow": ""});
				this.element.css({"min-height": ""});
			}

		}
	});


	var DrawerPanel = TabPanel.extend({
		initProps: function ($super, _config) {
			$super(_config);
			this.isOpen = false;
			this.selectedIndex = null;
			this.isInitSelect = false;
			this.dir = _config.dir == "l" ? "l" : "r";
			this.cfgWidth = _config.width || "";
		},
		startup: function ($super) {
			$super();
			//防止内容会闪一下
			this.element.css({
				"position": "fixed",
				"left": "-3000px",
				"z-index": 100
			});
		},

		_slideOut: function () {
			var self = this;
			setTimeout(function () {
				self.slideOut();
				self.setSelectedIndex(0);
			}, 1)

		},

		doLayout: function ($super, obj) {
			$super(obj);
			this.on("indexChanged", function (data) {
				this.changeSlide(data);
			});

			topic.subscribe('slideOutClick', this._slideOut, this);

			var self = this;
			$(document).on("click", function (evt) {
				if (self.isOpen == true) {
					if (!$.contains(self.element[0], evt.target)) {
						self.slideIn();
						self.isOpen = false;
					}
				}
			});

			this.slideClass = this.frame.attr("data-lui-switch-class");

			this.slideOffClass = this.slideClass + "_off";

			if (this.frame)
				this.frame.addClass(this.slideOffClass);

			this.contentToolbar = this.frame.find("[data-lui-mark='panel.toolbar']");
			this.frame.find("[data-lui-mark='panel.contents.height']").css("height", this.frame.height() - 30);


			this.frame.find("[data-lui-mark='panel.toolbar.close']").on("click",
				function () {
					if (self.isOpen == true) {
						self.slideIn();
						self.isOpen = false;
					}
				});
		},

		changeSlide: function (evt) {
			if (typeof (evt.index.after) !== undefined && evt.index.before != evt.index.after) {
				var txt = env.fn
					.formatText(this.contents[evt.index.after].title);
				this.contentToolbar
					.html(this
							.buildTitleIcon(this.contents[evt.index.after].titleicon)
						+ txt);
				this.contentToolbar.addClass('lui_text_primary');
			}

			if (this.isOpen == false) {
				this.slideOut();
				this.isOpen = true;
				return;
			}
		},

		// 兼容字体和类名
		buildTitleIcon: function (titleIcon) {
			return "<i class='iconfont " + (titleIcon ? titleIcon : "")
				+ "'></i>";
		},

		parseTextMark: function (dom) {
			var self = this;
			var titleNodes = dom.find("[data-lui-mark='panel.nav.title']");
			titleNodes.each(function (index, domEle) {
				//标题文字
				var titleNode = $(domEle);
				titleNode.addClass("lui_drawerpanel_navs_item_c_hover_" + self.dir);

				self.titlesNode.push(titleNode);
//				if(!self.contents[index].titleicon) {
//					self.contents[index].titleicon = "&#xe6b4;";
//				}
				var hbox = $("<div class='lui_drawerpanel_navs_item_hbox lui_text_primary'></div>");
				titleNode.append(hbox);

				var titleIcon = self.contents[index].titleicon;
				hbox.append(self.buildTitleIcon(titleIcon));

				var title = strutil.decodeHTML(self.contents[index].title);
				hbox.append("<span class='lui_drawerpanel_titletext'> " + env.fn.formatText(title) + "</span>")

			});
		},

		slideOutToggleClass: function () {
			if (this.frame) {
				this.frame.addClass(this.slideClass);
				this.frame.removeClass(this.slideOffClass);
			}
		},

		slideInToggleClass: function () {
			if (this.frame) {
				this.frame.removeClass(this.slideClass);
				this.frame.addClass(this.slideOffClass);
			}
		},

		slideOut: function () {
			if (this.frame) {
				this.slideOutToggleClass();
				if (this.dir == "r") {
					this.frame.animate({"right": "0"});
				} else {
					this.frame.animate({"left": "0"});
				}
			}
		},
		slideIn: function () {
			if (this.frame) {
				var w = this.frameWidth;

				if (this.dir == "r") {
					this.frame.animate({"right": -(w + 2)}, $.proxy(this.slideInToggleClass, this));
				} else {
					this.frame.animate({"left": -(w + 2)}, $.proxy(this.slideInToggleClass, this));
				}

			}
		}
	});

	exports.AbstractPanel = AbstractPanel;
	exports.AbstractContent = AbstractContent;
	exports.Content = Content;
	exports.NonePanel = NonePanel;
	exports.Panel = Panel;
	exports.TabPanel = TabPanel;
	exports.TabPage = TabPage;
	exports.AccordionPanel = AccordionPanel;
	exports.DrawerPanel = DrawerPanel;
});