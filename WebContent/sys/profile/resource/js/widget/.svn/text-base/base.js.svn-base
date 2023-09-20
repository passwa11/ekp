define(function(require, exports, module) {
	
	var base = require('lui/base'),
		topic = require('lui/topic');
	
	var NavTop = base.DataView.extend({
		
		selectedItem : '',
		
		get : function(){ }	
	});
	
	var NavLeft = base.DataView.extend({
		
		selectedItem : '',
		
		get : function(){ }
	});
	
	var ModuleMain = base.Container.extend({
		
		selectedItem : '',
		
		get : function(){ }
	});
	
	var View = base.Container.extend({
		
		initProps : function($super,cfg){
			$super(cfg);
			this.hasInited = false;
		},
		
		startup : function($super){
			$super();
			//获取#内容，修改子组件的选中Item
			var hash = window.location.hash;
			if(hash.length > 1){
				hash = hash.substring(1);
				//example : 'app/ekp/km/review',m[1]=app、m[2]=ekp、m[3]=km/review
				var m = hash.match(/^([^\/]*)(\/[^\/]*)?(\/.*)?$/) || [];
				if(m[1]){
					this.navTop.selectedItem = m[1];
				}
				if(m[2] && m[2].length > 1){
					this.navLeft.selectedItem = m[2].substring(1);
				}
				if(m[3] && m[3].length > 1){
					this.moduleMain.selectedItem = m[3].substring(1);
				}
			}
			topic.subscribe('sys.profile.navTop.change',this.onNavTopChanged,this);
			topic.subscribe('sys.profile.navLeft.change',this.onNavLeftChanged,this);
			topic.subscribe('sys.profile.moduleMain.change',this.onModuleMainChanged,this);
			
			//监听hash变化
//			$(window).on('hashchange',function(){
//				console.log('hashchange');
//			});
			
			
		},
		
		setNavTop : function(navTop){
			this.navTop = navTop;
			navTop._view = this;
		},
		
		onNavTopChanged : function(evt){
			if(this.hasInited){
				this.navTop.selectedItem = evt.key;
				this.navLeft.selectedItem = '';
				this.moduleMain.selectedItem = '';
			}
			this.navLeft.get({
				key : evt.key || this.navTop.selectedItem
			});
			
			//this.navTop.selectedItem = 'org';
			this.setHash(1,evt.key || this.navTop.selectedItem);
		},
		
		setNavLeft : function(navLeft){
			this.navLeft = navLeft;
			navLeft._view = this;
		},
		
		onNavLeftChanged : function(evt){
			
			if(this.hasInited){
				this.navLeft.selectedItem = evt.key;
				this.moduleMain.selectedItem = '';
			}
			this.moduleMain.get({
				key: evt.key || this.navLeft.selectedItem,
				url : evt.url
			});
			this.setHash(2,evt.key);
		},
		
		setModuleMain : function(moduleMain){
			this.moduleMain = moduleMain;
			moduleMain._view = this;
		},
		
		onModuleMainChanged : function(evt){
			if(this.hasInited){
				this.moduleMain.selectedItem = evt.key;
			}
			this.setHash(3,evt.key);
			this.hasInited = true;
		},
		
		addChild : function(child){
			if(child instanceof NavTop){
				this.setNavTop(child);
				return;
			}
			if(child instanceof NavLeft){
				this.setNavLeft(child);
				return;
			}
			if(child instanceof ModuleMain){
				this.setModuleMain(child);
				return;
			}
			this.children.push(child);
		},
		
		draw : function(){
			if(this.isDrawed)
				return;
			this.element.show();
			this._draw();
			this.isDrawed = true;
		},
		
		_draw : function(){
			for ( var i = 0; i < this.children.length; i++) {
				if(this.children[i].draw)
					this.children[i].draw();
			}
			this.navTop.get();
		},
		
		setHash : function(level,key){
			
			var hash = window.location.hash;
			if(level == 1){
				window.location.hash = '#' + key;
			}else if(level == 2){
				var index = hash.indexOf('/') > -1 ? hash.indexOf('/') : hash.length;
				window.location.hash =  hash.substring(0,index) + '/' + key;
			}else if(level ==3){
				var array = hash.split('/');
				window.location.hash = array[0] + '/' + array[1] + '/' + key;
			}
		}
		
	});
	
	exports.NavTop = NavTop;
	exports.NavLeft = NavLeft;
	exports.ModuleMain = ModuleMain;
	exports.View = View;
	
	
});