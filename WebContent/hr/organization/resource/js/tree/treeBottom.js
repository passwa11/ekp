define( function(require, exports, module) {
	var base = require('lui/base');
	var $ = require("lui/jquery");
	var topic = require("lui/topic");
	var toolbar = require("lui/toolbar");
	
	var TreeBottom = base.Component.extend( {
		initialize : function($super, config) {
			$super(config);
			this.config = config;
			this.draw();
		},
		startup : function() {
			
		},
		draw : function($super) {
			if (this.isDrawed)
				return;
			var self = this;
			this.treeToolbar = $('<div class="hr_tree_tree_toolbar"></div>');
			this.element= $("<div class='hr_tree_bottom'>");
			this.element.append(this.treeToolbar);
			this.renderToolbar(this.config.bottom);
			return this.element;
		},
		renderToolbar:function(bottoms){
			for(var i = 0;i<bottoms.length;i++){
				var button = $("<div class='hr_tree_button'><span><i class='"+bottoms[i]['iconCls']+"'></i>"+bottoms[i].name+"</span></div>")
				button.on("click",bottoms[i].event)
				this.treeToolbar.append(button);
			}
			this.treeToolbar.append(button);
		},
		
	});

	exports.TreeBottom = TreeBottom;
});