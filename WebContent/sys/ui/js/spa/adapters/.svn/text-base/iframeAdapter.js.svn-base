define(function(require, exports, module) {

	var SpaConst = require('../const');
	var topic = require('lui/topic');
	var $ = require('lui/jquery');
	var env = require('lui/util/env');
	var Spa = require('lui/spa/Spa');

	var iframeAdapter = {

		spa : env.fn.getConfig().isSpa,

		spaLock : false,

		initProps : function($super, cfg) {
		
			this.spa = env.fn.getConfig().isSpa;
			$super(cfg);
			if (!this.spa){
				return;
			}
			topic.subscribe(SpaConst.SPA_CHANGE_VALUES, this.__handle,this);
		},
		
		iframeLoaded : function($super){
			var self = this;
			$super();
			if(!this.spa){
				return;
			}
			try{
				var contentWindow = this.$iframeNode[0].contentWindow;
				if(contentWindow.LUI.luihasReady){
					setTimeout(function(){
						subscribeIframe(contentWindow);
					},1);
				}else{
					contentWindow.LUI.ready((function(cw){
						return function(){
							setTimeout(function(){
								subscribeIframe(cw);
							},1)
						};
					})(contentWindow));
				}
				
				//订阅内部IFRAME的事件
				function subscribeIframe(__win){
					if(!self.subscribeIframe){
						return;
					}
					__win && __win.seajs && __win.seajs.use(['lui/topic'],function(__topic){
						//SPA change事件
						__topic.subscribe("spa.change.values",function(evt){
							evt = $.extend(evt, {
								$publisher : '$iframe$'
							})
							self.iframeChanged(evt);
						},this);
						
						__topic.subscribe("spa.paging.changed",function(evt){
							evt = $.extend(evt, {
								$publisher : '$iframe$'
							})
							self.iframePagingChanged(evt);
						},this);
						
						//hash change事件
						__topic.subscribe("spa.change.hash",function(evt){
							self.hashChanged(evt);
						},this);
					});
				}
			}catch(e){
				//TODO 异构EKP系统（跨域）
				console.log('Iframe组件传递spa事件出错:' + e);
			}
		},
		
		iframeChanged : function(evt){
			var values = this._hackValues(top);
			evt.value = $.extend(values, evt.value || {});
			topic.publish(SpaConst.SPA_CHANGE_RESET, evt);
		},
		
		iframePagingChanged : function(evt){
			var values = this._hackValues(top);
			evt.value = $.extend(values, evt.value || {});
			topic.publish(SpaConst.SPA_PAGING_CHANGED, evt);
		},
		
		
		
		
		hashChanged : function(evt){
			var values = this._hackValues();
			values = $.extend(values, evt.value);
			var str = [];
			for ( var v in values) {
				if (values[v])
					str.push(v + "=" + encodeURIComponent(values[v]));
			}
			window.location.hash = str.join("&");
		},
		
		_hackValues : function(win){
			win = win || window;
			var value = {};
			var hackParameters = ['j_start','j_target','j_path'];
			var hash = win.location.hash,
				params = hash ? hash.substr(1).split("&") : [];
			for(var i = 0; i < params.length; i++){
				var argObj = params[i].split("=");
				if(argObj.length == 2
						&& hackParameters.join(';').indexOf(argObj[0]) >= 0 ){
					value[argObj[0]] = decodeURIComponent(argObj[1]);
				}
			}
			return value;
		},

		__handle : function(evt) {
			var self = this;
			//iframe发布的事件当前window下所有iframe忽略处理
			if(evt.$publisher == '$iframe$'){
				return;
			}
			//如果存在$parent参数，判断当前iframe组件是否处于该组件之内，不是则不处理
			if(evt.$parent){
				var isParent = false,
					evtParent = evt.$parent;
					thisParent = this.parent;
				while(thisParent){
					if(evtParent == thisParent){
						isParent = true;
						break;
					}
					thisParent = thisParent.parent;
				}
				if(!isParent){
					return;
				}
			}
			//loading过程忽略处理
			if(this.loading){
				return;
			}
			//空白页面忽略作处理
			if(this.isBlank()){
				return;
			}
			if (!evt)
				return;
			if (!evt.target || !evt.value || this.spaLock)
				return;
			this.spaLock = true;
			try{
				if(!this.$iframeNode){
					this.spaLock = false;
					return;
				}
				var contentWindow = this.$iframeNode[0].contentWindow,
					_evt = $.extend({},evt,{
						type : 'topic',
						name : SpaConst.SPA_CHANGE_RESET,
						$publisher : self,
						$target : evt.target,
						target : null,
					});
				if(contentWindow.LUI.luihasReady){
					contentWindow.LUI.fire(_evt);
				}
			}catch(e){
				//TODO 异构EKP系统（跨域）
				console.log('Iframe组件传递spa事件出错:' + e);
				this.spaLock = false;
			}
			this.spaLock = false;
		},
		
		destroy : function($super){
			topic.unsubscribe(SpaConst.SPA_CHANGE_VALUES, this.__handle,this);
			$super();
		}

	}

	module.exports = iframeAdapter;
});