define(function(require, exports, module) {
	var base = require("lui/base");
	var layout = require('lui/view/layout');
	var topic = require('lui/topic');
	// 缩略图区域
	var Thumb = base.Container
			.extend({

				initProps : function($super, cfg) {
					$super(cfg);
					this.url = cfg.url;
					this.data = cfg.data;
					// 默认显示或者隐藏
					this.show = false;
					this.startup();
				},

				// 滑动步伐
				step : 5,

				startup : function() {
					if (this.isStartup)
						return;
					if (!this.layout) {
						this.layout = new layout.Template({
							src : require.resolve('./tmpl/thumb.jsp#'),
							parent : this
						});
						this.layout.startup();
					}
					topic.subscribe('preview/change', this.change, this);
					topic.subscribe('preview/reset', this.reset, this);
					this.isStartup = true;
				},

				formatSrc : function(value) {
					var _src=this.url.replace('!{fdId}', value);
					if( this.data.valueType && this.data.valueType =='url' ){
						_src=value;
					}
					return _src;
				},

				doLayout : function(obj) {
					this.element.append($(obj));
					this.toggleNode = this.element
							.find('.lui_imageP_thumb_toggle');
					this.contentNode = this.element
							.find('.lui_imageP_thumb_content');
					if (!this.show)
						this.contentNode.css('display', 'none');
					this.preNode = this.element.find('.lui_imageP_thumb_prev');
					this.nextNode = this.element.find('.lui_imageP_thumb_next');
					this.listNode = this.element.find('.lui_imageP_thumb_list');
					this.bindClickEvent();
					this.bindSlideEvent();
				},

				// 初始化相关参数
				initParameter : function() {
					if (this.initParametered)
						return;
					this.ulNode = this.listNode.find('ul');
					this.c_width = this.listNode.width();
					this.c_height = this.contentNode.height();
					// 单个缩略图宽度
					this.s_width = $(this.listNode.find('li')[0]).width();
					// 所有缩略图宽度
					this.t_width = this.s_width * this.data.get('length');
					this.initImages();
					this.initParametered = true;
				},

				initImages : function() {
					this.listNode.find('img').each(function() {
						if ($(this).attr('data-src')) {
							$(this).attr('src', $(this).attr('data-src'));
							$(this).removeAttr('data-src');
						}
					});
				},

				reset : function() {
					this.c_width = this.listNode.width();
					this.t_width = this.s_width * this.data.get('length');
				},

				bindSlideEvent : function() {
					var self = this;
					this.preNode.on('click', function(evt) {
						self.onPre(evt);
					});

					this.nextNode.on('click', function(evt) {
						self.onNext(evt);
					});
				},

				bindClickEvent : function() {
					var self = this;
					this.element.on('click', function(evt) {
						self.onClick(evt);
					});
					this.toggleNode.on('click', function(evt) {
						self.onToggle(evt);
					});
				},

				stepWidth : function() {
					return this.step * this.s_width;
				},

				slide : function(left) {
					this.ulNode.animate({
						'margin-left' : left + 'px'
					});
				},

				onNext : function(evt) {
					if (this.t_width <= this.c_width)
						return;
					var left = parseInt(this.ulNode.css('margin-left')), _left;
					if (Math.abs(left) >= this.t_width)
						return;
					if (Math.abs(left - this.stepWidth()) > this.t_width
							- this.c_width)
						_left = -this.t_width + this.c_width;
					else
						_left = left - this.stepWidth();
					this.slide(_left);
				},

				onPre : function(evt) {
					if (this.t_width <= this.c_width)
						return;
					var left = parseInt(this.ulNode.css('margin-left')), _left;
					if (left >= 0)
						return;
					if (left + this.stepWidth() > 0)
						_left = 0;
					else
						_left = left + this.stepWidth();
					this.slide(_left);
				},

				onToggle : function() {
					var self = this;
					if (self.lock)
						return;
					self.lock = true;
					function callback() {
						self.initParameter();
						self.toggleText();
						self.show = self.show == true ? false : true;
						topic.publish('preview/thumb/toggle', {
							show : self.show,
							height : self.c_height
						});
						self.lock = false;
					}
					if (!this.show)
						this.contentNode.slideDown(callback);
					else
						this.contentNode.slideUp(callback);
				},

				// 切换toggle文本
				toggleText : function() {
					var toggle_symbol = 'data-lui-toggle', btn = this.toggleNode
							.find('[' + toggle_symbol + ']'), text = btn
							.attr(toggle_symbol), oldText = btn.html();
					btn.html(text).attr(toggle_symbol, oldText);
				},

				onClick : function(evt) {
					var $target = $(evt.target);
					while ($target.length > 0) {
						var value = $target.attr('data-lui-thumb-id');
						if (value) {
							this.data.set('value', value)
							break
						}
						$target = $target.parent();
					}
				},

				change : function(evt) {
					var arr = $('[data-lui-thumb-id]');
					arr.removeClass('on');
					var index = evt.index;
					// 更改选中样式
					$(arr[index]).addClass('on');
					// 更改当前序号
					$('.lui_imageP_thumb_curr_num').html(index + 1);
				}
			});

	module.exports = Thumb;
});
