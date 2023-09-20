define(function(require, exports, module) {
	require('lui/Class');
	var Evented = {
		
		    /**
			* 初始化事件信号对象
			* @return
			*/
			_init_signal: function() {
				this._signals = {};
			},
			
		    /**
			* 往事件信号对象中添加事件
			* @param type 事件类型（事件名称）
			* @param fn   触发事件时的响应处理函数
			* @param ctx  事件的作用域
			* @param one  事件是否只允许触发一次
			* @return 返回当前实例对象
			*/			
			_add_singal: function(type, fn, ctx, one) {
				var signal = this._signals[type];
				if (signal == null) {
					signal = [];
					this._signals[type] = signal;
				}
				signal.push({fn: fn, ctx: ctx, one: one});
				return this;
			},
			
			/**
			* 订阅事件
			* @param type 事件类型（事件名称）
			* @param fn   触发事件时的响应处理函数
			* @param ctx  事件的作用域
			* @return 返回当前实例对象
			*/	
			on: function(type, fn, ctx) {
				return this._add_singal(type, fn, ctx, false);
			},
			
			/**
			* 订阅一次性事件
			* @param type 事件类型（事件名称）
			* @param fn   触发事件时的响应处理函数
			* @param ctx  事件的作用域
			* @return 返回当前实例对象
			*/
			one: function(type, fn, ctx) {
				return this._add_singal(type, fn, ctx, true);
			},
			
			/**
			* 发布事件
			* @return 返回当前实例对象
			*/			
			emit: function() {
				var signal = this._signals[arguments[0]]; // 获取事件对象
				if (signal) {
					var args = []; // 事件处理函数的参数
					for (i = 1, len = arguments.length; i < len; i++) {
						args[i - 1] = arguments[i];
					}
					var destroyeds = [];  // 待销毁的事件数组
					for (var i = 0; i < signal.length; i ++) {
						try {
							var _signal = signal[i];
							if (_signal.ctx != null && this._destroyed) {
								destroyeds.push(_signal);
								continue;
							}
							_signal.fn.apply(_signal.ctx || this, args); // 执行事件响应函数
							if (_signal.one) {
								destroyeds.push(_signal);
							}
						} catch (e) {
							if (window.console)
								console.error("Evented.emit:id="+this.id, e.stack);
						}
					}
					for (var i = 0; i < destroyeds.length; i ++) {
						this.off(arguments[0], destroyeds[i].fn, destroyeds[i].ctx);
					}
				} else {
					//if (window.console)
						//console.debug("Evented.emit: no signal '" + arguments[0] + "' listener!");
				}
				return this;
			},
			
			/**
			* 销毁事件
			* 目前支持三个参数，第一个参数为事件名称，第二个参数为事件响应处理函数，第三个参数为事件作用域
			* 无参数传递时清除所有当前实例的事件,只传递事件名称时，按事件名称清除对应事件
			* 同时传递了事件响应处理函数和事件作用域的情况下，匹配函数和作用域进行事件清除
			* @return 返回当前实例对象
			*/
			off: function() {
				if (arguments.length == 0) {
					this._signals = {};
					return;
				}
				if (arguments.length == 1) {
					this._signals[arguments[0]] = [];
					return;
				}
				var signal = this._signals[arguments[0]];
				if(signal==null)
					return;
				var fn = arguments[1];
				// 增加上下文判断，避免在多个相同类生成的对象下错误移除事件
				var ctx = arguments.length > 2 ? arguments[2] : null;
				for (var i = 0; i < signal.length; i ++) {
					if (signal[i].fn == fn && (!ctx || signal[i].ctx == ctx) ) {
						signal.splice(i, 1);
						return this;
					}
				}
				return this;
			},
			
			/**
			* 发布事件(事件冒泡，触发执行当前实例以及逐级向上触发父对象的事件)
			*/	
			fire: function(evt) {
				if (!evt.source) {
					evt.source = this;
				}
				var caller = this;
				while (caller) {
					caller.emit(evt.name, evt);
					if (evt.stopType && caller instanceof evt.stopType) {
						break;
					}
					if (evt.stop == true) {
						break;
					}
					caller = caller.parent;
				}
			}
			
	};
	module.exports = Evented;
});