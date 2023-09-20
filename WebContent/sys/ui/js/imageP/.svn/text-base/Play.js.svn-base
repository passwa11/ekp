define(function(require, exports, module) {
	var base = require("lui/base");
	var topic = require('lui/topic');
	var $ = require("lui/jquery");
	var Play = base.Container.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			this.url = cfg.url;
			this.data = cfg.data;
			this.startup();
		},

		lastIndex : 0,

		startup : function() {
			if (this.isStartup)
				return;
			this.image = $('<img alt="">');
			this.left = $('<div class="left" />');
			this.right = $('<div class="right" />');
			topic.subscribe('preview/change', this.change, this);
			topic.subscribe('preview/thumb/toggle', this.thumbToggle, this);
			topic.subscribe('preview/panel/toggle', this.panelToggle, this);
			this.isStartup = true;
		},

		thumbToggle : function(evt) {
			var height = this.max_height;
			if (evt.show)
				height -= evt.height;
			this.image.animate({
				'max-height' : height + 'px'
			});
		},

		panelToggle : function(evt) {
			var width = this.max_width;
			if (!evt.show)
				width += evt.width;
			this.image.animate({
				'max-width' : width + 'px'
			});
		},

		change : function(evt) {
			if (!evt)
				return;
			if (evt.value) {
				this.image.css('opacity', 0);
				
				var _src=this.formatSrc(evt.value);
				this.image.attr('src', _src );
				this.image.animate({
					opacity : 1
				});
			}
		},

		getClientHeight : function() {
			if (window.innerHeight)
				return window.innerHeight;
			return $(window).height();
		},

		draw : function() {
			this.image.appendTo(this.element);

			var height = (this.getClientHeight() - 90 - 46) + 'px';

			this.element.parent().css({
				'line-height' : height,
				'height' : height
			});
			this.image.css('max-height', height);

			this.max_height = parseInt(height);
			this.max_width = parseInt(this.image.css('max-width'));

			var _src=this.formatSrc(this.data.get('value'));
			this.image.attr('src', _src);
			this.left.appendTo(this.element);
			this.right.appendTo(this.element);
			
			var _count=this.data.data.length;
			if(_count <= 1){
				this.left.hide();
				this.right.hide();
			}
			this.bindEvent();
		},
		
		formatSrc : function(value) {
			var _src=this.url.replace('!{fdId}', value);
			if( this.data.valueType && this.data.valueType =='url' ){
				_src=value;
			}
			return _src;
		},

		pre : function(evt) {
			var lastIndex = this.data.get('index');
			if (lastIndex >= 1)
				this.data.set('index', lastIndex - 1);
		},

		next : function(evt) {
			var lastIndex = this.data.get('index');
			if (lastIndex < this.data.get('length') - 1)
				this.data.set('index', lastIndex + 1);
		},

		bindEvent : function() {
			var self = this;
			this.left.on('click', function(evt) {
				self.pre(evt)
			});
			this.right.on('click', function(evt) {
				self.next(evt)
			});
		}
	})
	module.exports = Play;
});
