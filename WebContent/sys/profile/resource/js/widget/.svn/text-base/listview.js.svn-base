define( function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		topic = require('lui/topic'),
		env = require('lui/util/env');
	
	var ListView = base.DataView.extend({
		
		defaultItem : '',
		
		startup : function($super){
			$super();
			topic.subscribe('sys.profile.moduleMain.loaded',this.handleModuleMainLoaded,this);
			this.render.handleItemCallback = $.proxy(this.handleItemCallback,this);
			this.render.on('html', this.ellipsis, this);
			this._try_times = 0;
		},
		
		handleModuleMainLoaded : function(evt){
			this.defaultItem = evt.key;
			this.parentWin = evt.parentWin;
			if(this.isLoad){
				var data = this.data;
				for(var i = 0;i < data.length;i++){
					if(this.defaultItem == data[i].key){
						this.handleItemCallback({ key : data[i].key , url : data[i].url , target : data[i].target  });
						return;
					}
				}
			}
		},
		
		handleItemCallback : function(evt){
			if(this.parentWin){
				this._try_times = 0;
				if(this.parentWin.LUI){
					this.parentWin.LUI.fire({ type: "topic", name: "sys.profile.moduleMain.change" , key : evt.key });
				}
				window.open(env.fn.formatUrl(evt.url),evt.target || '_self');
				return;
			}
			this._try_times ++;
			if (this._try_times > 10) {
    			return;
    		};
    		var self = this;
	    	setTimeout(function() {
	    		self.handleItemCallback(evt);
	    	}, 500);
		},
		
		onDataLoad : function($super,data){
			var that = this;
			this.data = data;
			if(this.defaultItem){
				for(var i = 0;i < data.length;i++){
					if(this.defaultItem == data[i].key){
						this.handleItemCallback({ key : data[i].key , url : data[i].url , target : data[i].target  });
						return;
					}
				}
			}
			this.render.get(data);
		},
		
		//处理简介截断
		ellipsis : function(){
			var des = this.element.find('.lui_profile_block_grid_itemDes');
			des.each(function(i){
			    var divH = $(this).height();
			    var $p = $("p", $(this)).eq(0);
			    while ($p.outerHeight() > divH) {
			    	if(!$(this).attr('title')){
			    		$(this).attr('title',$p.text());
			    	}
			        $p.text($p.text().replace(/(\s)*([a-zA-Z0-9]+|\W)(\.\.\.)?$/, "..."));
			    };
			});
		}
		
	});
	
	
	exports.ListView = ListView;
	
});