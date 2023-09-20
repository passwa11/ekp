seajs.use(['lui/jquery'], function($) {

	var POPS_DATA_NAME = '__POPS_DATA__' + window.__POPS_TARGET__;
	
	//////////////////////////////////////////////////////////
	// 弹窗数据处理逻辑
	//////////////////////////////////////////////////////////
	
	var srcData = data || [];

	// 1.获取本地存储的数据
	var localData = null;
	try {
		localData = JSON.parse(localStorage[POPS_DATA_NAME]) || [];
	} catch(e) {
		localData = [];
	}
	
	// 2.合并本地与服务器数据
	$.each(srcData, function(_, d) {
		$.each(localData, function(__, _d) {
			if(d.fdId == _d.fdId) {
				d.lastPopTime = _d.lastPopTime;
			}
		});
	});
	var tmpData = [].concat(srcData);
	
	// 3.选出需要弹窗的数据并将已弹窗的数据缓存
	var activeData = [];
	var now = new Date();
	$.each(tmpData, function(_, d) {
		
		var startTime = new Date(d.fdStartTime);
		var endTime = new Date(d.fdEndTime);
		
		var mode = d.fdMode || '0';
		var now = new Date().getTime();
		var lastPopTime = d.lastPopTime || 0;
		
		// 判断弹窗频次
		switch(mode) {
			case '1': 
				if(now - lastPopTime >= 24 * 60 * 60 * 1000) {
					d.lastPopTime = now;
					activeData.push(d);
				}
				break;
			case '2': 
				if(now - lastPopTime >= 7 * 24 * 60 * 60 * 1000) {
					d.lastPopTime = now;
					activeData.push(d);
				}
				break;
			case '3': 
				if(now - lastPopTime >= 30 * 24 * 60 * 60 * 1000) {
					d.lastPopTime = now;
					activeData.push(d);
				}
				break;
			default: 
				if(!d.lastPopTime && startTime <= now && endTime > now) {
					d.lastPopTime = now;
					activeData.push(d);
				}
				break;
		}
		
	});
	localStorage[POPS_DATA_NAME] = JSON.stringify(tmpData);
	
	//////////////////////////////////////////////////////////
	// 弹窗逻辑
	//////////////////////////////////////////////////////////
	
	// 弹窗类
	var Pop = function(data, options) {
		this.data = data || [];
		
		this.defaultOptions = {
			popClass: 'lui-pop',
			popListClass: 'lui-pop-list',
			popItemClass: 'lui-pop-item',
			popBGClass: 'lui-pop-bg',
			popComponentClass: 'lui-pop-component',
			popIndicationClass: 'lui-pop-indication',
			popIndicationDotClass: 'lui-pop-indication-dot',
			popIndicationBtnClass: 'lui-pop-indication-btn',
			popBtnClass: 'lui-pop-btn',
			onClose: function() {}
		}
		this.options = $.extend(this.defaultOptions, options);
		
		this.init();
		this.draw();
		this.bindEvents();
		
		this.startTimer(0);
	}
	
	Pop.prototype = {
		init: function() { 
			this.element = null;
			this.timer = null;
			this.activeIndex = -1;
		},
		draw: function() {
			this.element = $('<div/>').addClass(this.options.popClass);
			this.drawMain();
			this.drawBtns();
		},
		drawMain: function() {
			
			var ctx = this;
			
			var popListElement = this.popListElement = $('<div/>').addClass(this.options.popListClass).appendTo(this.element);
			
			var _popIndicationElement = this.popIndicationElement = $('<div/>').addClass(this.options.popIndicationClass)
																		.appendTo(this.element);
			
			var popIndicationElement = $('<div/>').appendTo(_popIndicationElement);
			
			this.popIndicationPrevElement = $('<span/>').text('&lt;').attr('data-type', 'btn')
												.addClass(this.options.popIndicationBtnClass)
												.attr('data-direction', 'prev')
												.appendTo(popIndicationElement);
			this.popIndicationNextElement = $('<span/>').text('&gt;').attr('data-type', 'btn')
												.addClass(this.options.popIndicationBtnClass)
												.attr('data-direction', 'next')
												.appendTo(popIndicationElement);
			$.each(this.data, function(i, d) {
				var popItemElement = $('<div/>').addClass(ctx.options.popItemClass)
											.attr('data-index', i)
											.appendTo(popListElement);
				
				var t = null;
				try {
					t = JSON.parse(d.docContent);
				} catch(e) {
					t = {};
				}
				
				$.each(t.style || {}, function(k, v) {
					popItemElement.css(k, v);
				});
				
				// 设置背景
				if(t.props.bgUrl) {
					$('<img />').attr('src', Com_Parameter.ContextPath + t.props.bgUrl)
						.addClass(ctx.options.popBGClass)
						.appendTo(popItemElement);
				} else {
					popItemElement.css('background-color', '#fff');
				}
				
				// 设置链接
				if(d.fdLink) {
					popItemElement.attr('data-link', 'true');
					popItemElement.click(function() {
						window.open(d.fdLink);
					});
				}
				
				if(t.style && t.style['background-image']) {
					$('<img />').attr('src', t.style['background-image'])
						.addClass(ctx.options.popComponentBGClass)
						.appendTo(popItemElement);
				}
				
				$.each(t.children || [], function(_i, _d) {
					
					var popComponentElement = $('<div/>').addClass(ctx.options.popComponentClass)
													.attr('data-component-type', _d.type)
													.appendTo(popItemElement);
					
					$.each(_d.style || {}, function(_k, _v) {
						popComponentElement.css(_k, _v);
					});
					
					var props = _d.props || {};
					
					
					switch(_d.type) {
						case 'textarea': 
							$('<textarea/>').val(props.value || '')
								.attr('readonly', 'true')
								.appendTo(popComponentElement);
							break;
						case 'link':
							$('<a/>').text(props.value || '')
								.attr('href', props.href)
								.attr('target', '_blank')
								.css('line-height', _d.style.height)
								.appendTo(popComponentElement);
							break;
						default: break;
					}
				});
				
				$('<span/>').attr('data-type', 'dot').attr('data-index', i)
					.addClass(ctx.options.popIndicationDotClass)
					.appendTo(popIndicationElement);
				
			});
			
		},
		drawBtns: function() {
			this.closeBtn = $('<div/>').addClass(this.options.popBtnClass)
								.attr('data-type', 'close')
								.appendTo(this.element);
		},
		
		bindEvents: function() {
			var ctx = this;
			
			// 关闭弹窗
			this.closeBtn.click(function() {
				ctx.stopTimer();
				ctx.options.onClose();
			});
			
			this.popIndicationPrevElement.click(function() {
				
				var t = (ctx.activeIndex + ctx.data.length - 1) % ctx.data.length;
				ctx.startTimer(t);
			});
			
			this.popIndicationNextElement.click(function() {
				var t = (ctx.activeIndex + 1) % ctx.data.length;
				ctx.startTimer(t);
			});
			
			this.popIndicationElement.on('click', '.' + this.options.popIndicationDotClass, function() {
				var index = parseInt($(this).attr('data-index'));
				ctx.startTimer(index);
			});
			
		},
		
		setActiveIndex: function(index) {
			
			this.activeIndex = index;
			
			$(this.element).find('.' + this.options.popItemClass).attr('data-active', 'false');
			$(this.element).find('.' + this.options.popItemClass + '[data-index="' + index + '"]').attr('data-active', 'true');
			$(this.element).find('.' + this.options.popIndicationDotClass).attr('data-active', 'false');
			$(this.element).find('.' + this.options.popIndicationDotClass + '[data-index="' + index + '"]').attr('data-active', 'true');
		},
		
		startTimer: function(index) {
			
			var ctx = this;
			
			if(ctx.timer) {
				clearTimeout(ctx.timer);
				ctx.timer = null;
			}
			
			ctx.activeIndex = index;
			ctx.setActiveIndex(index);
			
			var t = ctx.data[index];
			
			ctx.timer = setTimeout(function() {
				
				// 只有一个弹窗的情况下自动关闭
				if(ctx.data.length <= 1) {
					ctx.options.onClose();
				} else {
					ctx.startTimer((index + 1) % ctx.data.length);
				}
				
			}, t.fdDuration * 1000);
			
		},
		
		stopTimer: function() {
			clearTimeout(this.timer);
			this.timer = null;
		},
		
		getElement: function() {
			return this.element;
		}
	}
	
	if((activeData || []).length > 0) {

		// 弹窗容器
		var container = $('<div/>').addClass('lui-pop-container').css('display', 'none').appendTo(window.document.body);
		var mask = $('<div/>').addClass('lui-pop-mask').appendTo(container);
		var wrapper = $('<div/>').addClass('lui-pop-wrapper').appendTo(container);
		
		var pop = new Pop(activeData, {
			onClose: function() {
				container.fadeOut(function() {
					container.remove();
				});
			}
		});
		pop.getElement().appendTo(wrapper);
		
		container.fadeIn();
		
	}
	
	
	
});
