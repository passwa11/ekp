define(function(require, exports, module) {
	var base = require('lui/base');
	var zonelang = require('lang!sys-zone');
	var uilang = require('lang!sys-ui');
	var topic = require('lui/topic');
	var $ = require('lui/jquery');
	var layout = require('lui/view/layout');
	var constant = require('sys/zone/address/resource/js/zoneAddressConstant');
	var menuCfg = require('sys/zone/address/resource/js/zoneAddressMenuCfg');
	var menuContent = require('sys/zone/address/resource/js/zoneAddressMenuContent');

	var ZoneAddressSearch = base.Container.extend({
		initProps : function($super, cfg) {
			this.parent = cfg.parent;
			this.parentDom = this.parent.searchDom;
			this.searchWord = null;
			this.startup();
		},

		startup : function() {
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('./tmpl/zoneAddressSearch.html#'),
					parent : this
				});
				this.layout.startup();
				this.children.push(this.layout);
			}
		},

		doLayout : function(obj) {
			this.parentDom.html($(obj));
			var search = $(this.parentDom.find('[data-lui-mark="sys-zone_address-search-btn"]'));
			var input=$(this.parentDom.find("input"));
			search.bind(
					"click propertychange",
					this,
					function(evt) {
						var searchObj = evt.data;
						searchObj.searchWord = input.val();
						topic.publish(constant.event['SEARCH_CHANGED'],
								searchObj.searchWord);
					}),
			$(this.parentDom.find("input")).bind(
					"input keydown",
					this,
					function(evt) {
						if(evt.keyCode && evt.keyCode == 13) {
							var searchObj = evt.data;
							searchObj.searchWord = $(this).val();
							topic.publish(constant.event['SEARCH_CHANGED'],
								searchObj.searchWord);
						}
					});
			$(this.parentDom.find("input")).attr("placeholder", zonelang['zoneAddress.placeholder']);
		},

		handleInput : function(e) {
			if (e.keyCode == 13) {
				if (this.searchWord != null
						&& $.trim(this.searchWord).length > 0) {
					// Enter被按下，去后台搜(实时搜不需要等Enter)
					// console.log("后台搜索");
				}
			} else {
				// 前台搜
			}
		}
//		handleClick:function(e){
//			if (e.keyCode == 13) {
//				if (this.searchWord != null
//						&& $.trim(this.searchWord).length > 0) {
//					// Enter被按下，去后台搜(实时搜不需要等Enter)
//					// console.log("后台搜索");
//				}
//			} else {
//				// 前台搜
//			}
//		}
	});

	var ZoneAddressMenu = base.Container.extend({
		initProps : function($super, cfg) {
			this.parent = cfg.parent;
			this.parentDom = this.parent.searchDom;
			this.menuCfg = this.parent.menuCfg;
			this.startup();
		},

		startup : function() {
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('./tmpl/zoneAddressMenu.html#'),
					parent : this
				});
				this.layout.startup();
				this.children.push(this.layout);
			}
			topic.subscribe(constant.event['OUTER_DOM_FINISHED'],
					this.outerDomFinished, this);// 订阅所有外层静态的DOM加载完成的事件
		},

		doLayout : function(obj) {
			this.parentDom.html($(obj));
			var menus = this.parentDom.find("[data-lui-menuid]");
			menus.bind('click', function(e) {
				var __menu = $(this), __menuid = __menu.attr('data-lui-menuid');
				menus.removeClass('active');
				__menu.addClass('active');
				topic.publish(constant.event['MENU_CHANGED'], {
					menuId : __menuid
				});
			});
		},

		outerDomFinished : function(evt) {
			// 当外层静态DOM加载完成之后开始默认加载组织架构
			this.selectMenu(0);
		},

		selectMenu : function(index) {
			var menus = this.parentDom.find("[data-lui-menuid]");
			if (menus.eq(index)) {
				var __menu = menus.eq(index), __menuid = __menu
						.attr('data-lui-menuid');
				menus.removeClass('active');
				__menu.addClass('active');
				topic.publish(constant.event['MENU_CHANGED'], {
					menuId : __menuid
				});
			}
		}
	});

	var ZoneAddressMenuContent = base.Container
			.extend({
				initProps : function($super, cfg) {
					this.parent = cfg.parent;
					this.menuCfg = this.parent.menuCfg;
					this.menuContents = [];
					this.startup();
				},

				startup : function() {
					topic.subscribe(constant.event['MENU_CHANGED'],
							this.menuChanged, this);
				},

				menuChanged : function(evt) {
					var menuId = evt.menuId;
					this.hiddenAllMenuContent();
					if (!this.menuContents[menuId]) {
						var __menucfg = this.searchMenuCfg(menuId);
						if (this
								.instanceofAbstractZoneAddressContent(__menucfg.content)) {
							var __menuContent = new __menucfg.content({
								ancestor : this.parent,
								parent : this,
								menuId : menuId,
								auth:this.config.auth
							});
							__menuContent.draw();
							this.menuContents[menuId] = __menuContent;
						}
					} else {
						this.menuContents[menuId].show();
					}
				},

				searchMenuCfg : function(menuId) {
					var __cfg = null;
					for (var i = 0; i < this.menuCfg.length; i++) {
						__cfg = this.menuCfg[i];
						if (__cfg.id == menuId) {
							return __cfg;
						}
					}
					return null;
				},

				instanceofAbstractZoneAddressContent : function(menucontent) {
					if (menucontent) {
						var tmp = menucontent.superclass;
						while (tmp) {
							if (tmp == menuContent.AbstractZoneAddressContent) {
								return true;
							}
							tmp = tmp.superclass;
						}
					}
					return false;
				},

				hiddenAllMenuContent : function() {
					for ( var key in this.menuContents) {
						this.menuContents[key].hide();
					}
				}
			});

	var ZoneAddressLeftViewContent = base.Container
			.extend({
				initProps : function($super, cfg) {
					this.parent = cfg.parent;
					this.menuCfg = this.parent.parent.menuCfg;
					this.parentDom = this.parent.parent.leftMenuContentDom;
					this.parent.leftMenuContentDom = this.parentDom;
					this.startup();
				},

				startup : function() {
					if (!this.layout) {
						this.layout = new layout.Template(
								{
									src : require
											.resolve('./tmpl/zoneAddressLeftViewContent.html#'),
									parent : this
								});
						this.layout.startup();
						this.children.push(this.layout);
					}
				},

				doLayout : function(obj) {
					this.parentDom.html($(obj));
				}
			});

	var ZoneAddressChildViewContent = base.Container
			.extend({
				initProps : function($super, cfg) {
					this.parent = cfg.parent;
					this.menuCfg = this.parent.parent.menuCfg;
					this.parentDom = this.parent.parent.childContentDom;
					this.parent.childContentDom = this.parentDom;
					this.startup();
				},

				startup : function() {
					if (!this.layout) {
						this.layout = new layout.Template(
								{
									src : require
											.resolve('./tmpl/zoneAddressChildViewContent.html#'),
									parent : this
								});
						this.layout.startup();
						this.children.push(this.layout);
					}
				},

				doLayout : function(obj) {
					this.parentDom.append($(obj));
					topic.publish(constant.event['OUTER_DOM_FINISHED'], {});
				}
			});

	var ZoneAddress = base.Container.extend({
		selectedMenuId : null,
		hideQrCode : '',

		initProps : function($super, cfg) {
			$super(cfg);
			this.elem = $(this.config.elem);
			this.auth=this.config.auth;
			this.hideQrCode = this.config.hideQrCode;
			this.menuCfg = menuCfg();
			this.startup();
		},

		startup : function() {
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('./tmpl/zoneAddress.html#'),
					parent : this
				});
				this.layout.startup();
				this.children.push(this.layout);
			}
			if (!this.search) {
				this.search = new ZoneAddressSearch({
					parent : this
				});
				this.children.push(this.search);// 搜索
			}
			if (!this.menu) {
				this.menu = new ZoneAddressMenu({
					parent : this
				});
				this.children.push(this.menu);// 菜单
			}
			if (!this.menuContent) {
				this.menuContent = new ZoneAddressMenuContent({
					parent : this,
					auth:this.auth,
					hideQrCode: this.hideQrCode
				});
				this.children.push(this.menuContent);
				// 画菜单对应的内容（左边和联动的子（子包括中间和右边）
			}
		},

		doLayout : function(obj) {
			var self = this;
			this.elem.append($(obj));
			this.childContentDom = this.elem
					.find('[data-lui-mark="zoneaddress.content"]');
			this.searchDom = this.elem
					.find('[data-lui-mark="zoneaddress.search"]');
			this.menuDom = this.elem
					.find('[data-lui-mark="zoneaddress.menus"]');
			this.leftMenuContentDom = this.elem
					.find('[data-lui-mark="zoneaddress.leftmenucontents"]');
			for (var i = 0; i < this.children.length; i++) {
				if (this.children[i] instanceof ZoneAddressSearch) {
					this.children[i].parentDom = this.searchDom;
				}
				if (this.children[i] instanceof ZoneAddressMenu) {
					this.children[i].parentDom = this.menuDom;
				}
				if (this.children[i] instanceof ZoneAddressMenuContent) {
					this.menuContent.children
							.push(new ZoneAddressLeftViewContent({
								parent : this.menuContent
							}));
					this.menuContent.children
							.push(new ZoneAddressChildViewContent({
								parent : this.menuContent
							}));
				}
				if (this.children[i].draw) {
					this.children[i].draw();
				}
			}
			this.bindEvent();
		},

		bindEvent : function() {
			var self = this;
			// 键盘回车事件:控制搜索
			// 键盘上移、下移事件:控制已选区域的展开与收缩
			$(document).keydown(function(e) {
				// 处理Enter
				if (self.search != null) {
					self.search.handleInput(e);
				}
			});
//			//搜索按钮点击事件绑定
//			$(document).click(function(e){
//				if(self.search!=null){
//					self.search.handleClick(e);
//				}
//			})
		}
	});

	exports.ZoneAddress = ZoneAddress;
});