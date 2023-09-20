define( function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('sys/profile/resource/js/widget/base'),
		topic = require('lui/topic');
	
	var NavTop = base.NavTop.extend({
		
		initProps : function($super,_config){
			$super(_config);
			this.config = _config;
		},
		setSelectedItem : function(){
			if(!this.selectedItem) {
				// 如果没有指定选中的菜单，默认取第一个
				var selectedItem;
				if(this.config.selectedItem) {
					selectedItem = this.config.selectedItem;
				} else {
					if(this.data && this.data.length > 0) {
						selectedItem = this.data[0].key;
					}
				}
				this.selectedItem = selectedItem || this.selectedItem;
			}
		},
		get : function(){
			this.draw();
			this.setSelectedItem();
		},
		doRender : function($super){
			$super();
			topic.publish('sys.profile.navTop.change',{
				key : this.selectedItem 
			});
		}
		
	});
	
	exports.NavTop = NavTop;
	
});