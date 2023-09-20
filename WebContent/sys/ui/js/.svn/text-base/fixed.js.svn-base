define(function(require, exports, module) {

	var $ = require("lui/jquery");
	var base = require("lui/base");
	var topic = require('lui/topic');
	var spaConst = require('lui/spa/const');

	var Fixed = base.Component
			.extend({

				initProps : function($super, cfg) {

					$super(cfg);
					this.__obj = $(this.config.elem);
					this.__content = $(this.config.content);
					this.___objCss = {};
					
				},

				startup : function() {

					// 只要涉及页面切换直接重置状态
					topic.subscribe(spaConst.SPA_CHANGE_VALUES, this.reset,
							this);
				},

				destroy : function($super) {

					$super();

					topic.unsubscribe(spaConst.SPA_CHANGE_VALUES, this.reset);
				},

				addChild : function($super, obj) {

					$super(obj);
				},

				beingFixed : function() {
					if (!this._top)
						this._top = this.__obj.position().top;

					if (this.tmpl && this.tmpl.position().top > 0) {
						this._top = this.tmpl.position().top;
						this.__obj.css('left', +this.tmpl.offset().left
								- $(window).scrollLeft());
					}

					if (!this.__bottom)
						this.__bottom = this.__content.position().top
								+ this.__content.outerHeight();
					
					var header_height = 0;
					//获取页面页眉高度
	      			try{
	      				if($(".lui_tlayout_header")){
	      					header_height=$(".lui_tlayout_header").height();
	      				}
	      			}catch(e){
	      				console.log(e);
	      			}
					
					if ($(document).scrollTop() >= (this._top+header_height)
							&& $(document).scrollTop() <= this.__bottom) {
						if (this.__obj.css('position') == 'fixed')
							return;
						if (!this.tmpl)
							this.tmpl = this.__obj.clone();
						this.__obj.before(this.tmpl);
						this.___objCss = {
							'width' : '',
							'position' : this.__obj.css('position'),
							'top' : this.__obj.css('top'),
							'left' : this.__obj.css('left'),
							'z-index' : this.__obj.css('z-index')
						};

						this.__obj.css({
							'width' : this.__obj.width(),
							'position' : 'fixed',
							'top' : 0,
							'left' : this.__obj.offset().left,
							'z-index' : 8
						});

					} else {
						if (this.__obj.css('position') == '')
							return;

						this.reset();
					}
				},

				bindScroll : function() {

					var self = this;
					window.onscroll = function() {

						if (!self.__obj.is(":hidden")) {
							self.beingFixed();
						}
					};
					if (document.body.attachEvent) {
						// ie8浏览器body高度发生变化不处罚body的resize事件
						if (document.documentMode && document.documentMode < 9)
							window.onresize = function() {

								// 延迟一毫米用于终极状态的位置判断
								setTimeout(function() {

									if (!self.__obj.is(":hidden")) {
										self.beingFixed();
									}
								}, 1);
							};
						else
							document.body.attachEvent('onresize', function() {

								// 延迟一毫米用于终极状态的位置判断
								setTimeout(function() {

									if (!self.__obj.is(":hidden")) {
										self.beingResize();
									}
								}, 1);
							});

					} else {
						$(window).on("resize", function() {

							self.beingResize();
						});
					}
				},

				reset : function() {

					if (this.tmpl && this.tmpl.length > 0) {
						this.tmpl.remove();
						this.tmpl = null;
						this.__obj.css(this.___objCss);
					}
				},

				beingResize : function() {

					if (!this.__obj.is(":hidden")) {
						this.beingFixed();
					}
					if (this.tmpl && this.tmpl.length > 0
							&& this.tmpl.innerWidth() > 0) {
						this.___objCss.width = this.tmpl.css('width');
						this.__obj.css({
							'width' : this.___objCss.width,
							'left' : this.tmpl.offset().left
									- $(window).scrollLeft()
						});
					}
				},

				draw : function($super) {
					if (this.isDrawed)
						return;
						
					if (this.__obj.length == 0)
						return;

					if (this.__content.length == 0)
						this.__bottom = 10000;
					this.bindScroll();
					this.onErase(function() {

						$(window).off('scroll');
					});
					this.isDrawed = true;
					return this;
				}

			})

	exports.Fixed = Fixed;

})
