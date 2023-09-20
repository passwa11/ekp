/**
 * 极速模式
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var Iframe = require('lui/iframe');
	var topic = require('lui/topic');
	var parser = require('lui/parser');
	var dialog = require('lui/dialog');
	var env = require('lui/util/env');
	
	var BaseMode = require('./base'); 
	var Hash = require('lui/framework/view/history/hash'); 
	var quickConfig  = require('./quick-config');
	
	var QuickMode = BaseMode.extend({
		
		history : null,
		
		startup : function($super){
			$super();
			var self = this
			this.history = new Hash();//目前只支持hash
			this.history.startup();
			var startPageInHash =  this.history.getParameter('j_start') ? 
					decodeURIComponent(this.history.getParameter('j_start')) : '',
				startTargetInHash = this.history.getParameter('j_target') ?
					decodeURIComponent(this.history.getParameter('j_target')) : '';
			if(startPageInHash.toLowerCase().indexOf('javascript')!=-1 || startPageInHash.toLowerCase().indexOf('vbscript')!=-1)	
				startPageInHash = this.startPage;		
			this.startPage =  startPageInHash ? startPageInHash : this.startPage; //从hash中寻找startPage
			this.startTarget = startTargetInHash ? startTargetInHash : this.startTarget;//从hash中寻找startTarget
			
			topic.subscribe('lui.page.quick.resize',function(){
				self.resize()
			});
			
		},
		
		draw : function(){
			var self = this;
			
			// 内容区
			var $content = $('<div data-lui-page-type="content"/>');
			
			// 内容区（IFRAME形式）
			var $iframe  = this.$iframe = new Iframe.Iframe({ dyniframe : false });
			$iframe.startup();
			$iframe.draw();
			
			// 侧边区（IFRAME形式）,类似Modal的作用
			var $rIframe = this.$rIframe = new Iframe.Iframe({ dyniframe : false });
			$rIframe.startup();
			$rIframe.draw();
			
			var $view = $(this.view.element);
			$view.addClass('lui_portal_container');
			$view.append($content);
			$view.append($iframe.element.attr('data-lui-page-type','iframe'));
			$view.append($rIframe.element.attr('data-lui-page-type','rIframe'));
				
			if(this.startPage){
				var url = "";
				if(this.startPage.charAt(0)=="/"){
					url = env.fn.getConfig().contextPath + this.startPage;
				}else{
					url = this.startPage;
				}
				var portalId = Com_GetUrlParameter(url,'pageId');
				this.view.open(url, this.startTarget || '_content',{
					history : false,
					portalId : portalId
				});
				this.history.replace({
					url : url,
					target : this.startTarget || '_content'
				});
			}
			
			this.history.listen(function(stateObject){
		    	if(stateObject){
		    		self.open(stateObject.url, stateObject.target || '_content',$.extend(stateObject.features,{
		    			history : false
		    		}));
		    	}
			});
			
			topic.publish('lui.page.quick.draw');
			
		},
		
		open  : function(url, target, features, customHashParams){
			//var {url,target,features} = this.ensureOptions(url, target, features);
			var ensureOptions = this.ensureOptions(url, target, features);
			url = ensureOptions.url;
			target = ensureOptions.target;
			features = ensureOptions.features;
			
			var promise = null;
			switch(target){
				case '_content':
					features.history = typeof(features.history) !== 'undefined' ? features.history : true;
					this.makeHistory(url, target, features, customHashParams);
					promise = this.loadContent(url, features);
					break;
				case '_iframe':
					features.history = typeof(features.history) !== 'undefined' ? features.history : true;
					this.makeHistory(url, target, features, customHashParams);
					promise = this.loadIframe(url, features);
					break;
				case '_rIframe':
					this.makeHistory(url, '_iframe', features, customHashParams);
					var ____url = url;
					if(env.fn.isInternalLink(____url)){
						____url = Com_SetUrlParameter(____url,'j_rIframe','true');
					}
					var curWindow = features.curWindow;
					if(curWindow &&  typeof(curWindow.openPage) !== 'undefined' ){
						curWindow.openPage(____url, {
							transition : features.transition,
							closeable : false
						}); //如果自身页面存在openPage,使用自身的..
					}else{
						promise = this.loadRIframe(____url, features);
					}
					break;
				default:
					if(customHashParams){
						window._child_window_hash_params = customHashParams;
					}
					window.open(url, target);
			}
			if(features.pageTitle){
				 this.setPageTitle(features.pageTitle);
			}
			return promise;
		},
		
		hide : function(target, features){
			switch(target){
				case '_content':
				case '_iframe':
					console.log('暂不支持' + target + '区域的关闭,_content和_iframe必须二选一，请使用open方式进行切换');
					break;
				case '_rIframe':
					var curWindow = features.curWindow;
					if(curWindow && typeof(curWindow.openQuery) !== 'undefined' ){
						curWindow.openQuery();
						return;
					}
					if(this.$rIframe && this.$rIframe.element){
						this.$rIframe.element.css({
							visibility : 'hidden',
							opacity : '0'
						});
					}
					break;
				default:
					break;
			}
		},
		
		resize : function(evt){
			evt = evt || {};
			var $header = $('.lui_portal_header'),
				$headerH = $header && $header.length > 0 ? $header.height() : 0;
				___evt = {
					'top' :  evt['header-h'] || ($headerH + 'px' )
				};
			if(evt && evt.$lastEvt){ // 取上一次配置进行resize
				___evt = this.__lastResizeEvt || {};
			}
			this.__lastResizeEvt = ___evt;
			this.view.element.animate(___evt, 200);
			//bad hack
			var $aside = $('.lui_portal_aside');
			if($aside && $aside.length > 0){
				$aside.animate(___evt, 200);
			}
		},
		
		setPageTitle : function(value){
			document.title = value;
		},
		
		ensureOptions : function(url, target, features){
			url = decodeURIComponent(url);
			features = $.extend({ transition : 'fadeIn' },features || {});
			target = (function(_target){
				for(var index = 0; index < quickConfig.length; index++){
					var qc = quickConfig[index];
					if(qc.pattern 
							&& qc.pattern.test(url)){
						return qc.target || _target;
					}
				}
				return _target;
			})(target);
			//不是信任站点
			if(!env.fn.isTrustSiteLink(url)){
				url = Com_Parameter.ContextPath + 'resource/jsp/e403.jsp';
			}
			return {
				url : url,
				target : target,
				features : features
			}
		},
		
		makeHistory : function(url, target, features, customHashParams){
			var _makeHistory = features.history;
			if(!_makeHistory){
				return;
			}
			this.history.push({
				url : url,
				target : target,
				features : features,
				customHashParams : customHashParams
			});
		},
		
		/**
		 * 加载页面片段
		 * @param {String} url:片段链接，返回HTML片段
		 * @param {Object} features:特性
		 */
		loadContent : function(url, features){
			var self = this;
			var ____url = Com_SetUrlParameter(url,'j_timestamp',new Date().getTime()),
				defer = $.Deferred();
			____url = Com_SetUrlParameter(____url,'j_content','true');
			features.lifecycle = {
				load : $.ajax(____url),
				after : function(evt){
					var html = evt.data,
						originalPage = evt.page,
						newPage = evt.newPage;
					if(originalPage){
						//destroy components
						var nodes = $('[data-lui-type]',originalPage);
						for(var i = 0; i < nodes.length; i++){
							var node = nodes.eq(i),
								component = LUI(node.attr('id')||node.attr('data-lui-cid'));
							component && component.destroy && component.destroy();
						}
					}
					newPage.html(html);
					parser.parse(newPage,function(){
						defer.resolve();
					});
					self._hideAllPage();//隐藏其他Page
					self.resize({ $lastEvt : true });//resize页面
				}
			};
			features.pageType = 'content';
			this._load(features);
			return defer.promise();
		},
		
		/**
		 * 加载Iframe帧
		 * @param {String} url:页面连接，返回完整页面
		 * @param {Object} features:特性
		 */
		loadIframe : function(url, features){
			var self = this;
			var ___url = url;
			if(env.fn.isInternalLink(___url)){
				___url = Com_SetUrlParameter(___url,'j_timestamp',new Date().getTime());
				___url = Com_SetUrlParameter(___url,'j_iframe','true');
			}
			this.$preIframe = this.$iframe;
			var newIframe  = this.$iframe = new Iframe.Iframe({ dyniframe : false , src : ___url  });
			newIframe.startup();
			newIframe.draw();
			var	defer = $.Deferred();
			newIframe.on('iframeLoaded',function(){
				defer.resolve(); 
			});
			features.lifecycle = {
				load : defer.promise(),
				after : function(){
					self._hideAllPage();//隐藏其他Page
					self.$preIframe.destroy();
				}
			};
			features.pageType = 'iframe';
			features.newPage = newIframe.element.attr('data-lui-page-type','iframe');
			this._load(features);
		},
		
		loadRIframe : function(url, features){
			var self = this;
			var ____url = url;
			if(env.fn.isInternalLink(____url)){
				____url = Com_SetUrlParameter(____url,'j_timestamp',new Date().getTime());
			}
			this.$preRIframe = this.$rIframe;
			var newIframe  = this.$rIframe = new Iframe.Iframe({ dyniframe : false , src : ____url,  subscribeIframe : features.subscribeIframe });
			newIframe.element.css('overflow','auto');
			newIframe.startup();
			newIframe.draw();
			var	defer = $.Deferred();
			newIframe.on('iframeLoaded',function(){
				defer.resolve(); 
			});
			features.lifecycle = {
				load : defer.promise(),
				after : function(){
					setTimeout(function(){
						self.$preRIframe.destroy();
					},500);
				}
			};
			features.transition = 'fadeIn';
			features.pageType = 'rIframe';
			features.newPage = newIframe.element.attr('data-lui-page-type','rIframe');
			this._load(features);
		},
		
		_load : function(features){
			var self = this;
			var lifecycle = features.lifecycle || {};
			var pageType = features.pageType,
				page = this._find(pageType),
				newPage = features.newPage || $(page).clone().empty();
			newPage.insertAfter(page);
			newPage.attr('class',features.pageClass || '');
			//对外暴露生命周期,load before
			lifecycle.before && lifecycle.before({
				page : page, 
				newPage : newPage
			});
			
			//加载完成前隐藏页面
			this._hidePage(newPage,'visibility');
			
			//loading加载
			var loading = dialog.loading(null,this.view.element);
			if(lifecycle.load && lifecycle.load.then){ // promise
				lifecycle.load.then(function(data){
					_after(data);
				});
			}else{ // not promise
				lifecycle.load && lifecycle.load();
				_after();
			}
			//内部after逻辑
			function _after(data){
				//对外暴露生命周期,load after
				lifecycle.after && lifecycle.after({
					data : data,
					page : page,
					newPage : newPage
				});
				//动画效果,默认为闪现
				$(newPage).addClass('transition').addClass(features.transition); 
				
				//加载完成显示页面
				self._showPage(newPage, (pageType == 'iframe' || pageType == 'rIframe') ? 'visibility' : 'display' );
				
				//隐藏loading
				loading.hide();
				setTimeout(function(){
					$(newPage).removeClass('transition').removeClass(features.transition);
					//移除旧页面
					if($(page) && $(page).length > 0){
						$(page).remove();
					}	
				},650);
			}
		},
		
		_hideAllPage : function(){
			$('[data-lui-page-type="content"]').hide();
			$('[data-lui-page-type="iframe"],[data-lui-page-type="rIframe"]').css({
				visibility : 'hidden',
				opacity : '0'
			});
			try{
				var win = window;
				while(1){
					win = win.parent;
					$('[data-lui-page-type="content"]').hide();
					$('[data-lui-page-type="iframe"],[data-lui-page-type="rIframe"]').css({
						visibility : 'hidden',
						opacity : '0'
					});
					if(win == top){
						break;
					}
				}
			}catch(e){}
		},
		
		_hidePage : function(page, method){
			switch(method){
				case 'visibility' : 
					$(page).css({
						visibility : 'hidden',
						opacity : '0'
					});
					break;
				case 'display' :
					$(page).hide();
					break;
			}
		},
		
		_showPage : function(page, method){
			$(page).css({
				visibility : '',
				opacity : '1'
			});
			$(page).show();
		},
		
		_find : function(pageType){
			var page = $('[data-lui-page-type="'+ pageType +'"]');
			if(!page || page.length == 0){
				try{
					var win = window;
					while(1){
						win = win.parent;
						page = $('[data-lui-page-type="'+ pageType +'"]',win.document);
						if(!page && page.lenth > 0){
							return page[0];
						}
						if(win == top){
							break;
						}
					}
				}catch(e){
					return null;
				}
			}else{
				return page[0];
			}
		}
		
	});
	
	module.exports = QuickMode;
	
});