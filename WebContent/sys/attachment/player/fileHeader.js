define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var env = require('lui/util/env');
	var base = require("lui/base");
	var topic = require("lui/topic");
	var msg =require('lang!sys-attachment');
	var preview=msg['sysAttMain.preview'];
	
	var PREVIEW_SELECTED_CHANGE = "file/change";
	
	var fileHeader = base.Component.extend({
		
		initProps : function($super, cfg) {
			$super(cfg);
			this.value = cfg.value;
			this.isAsposeView = cfg.isAsposeView;
		},
		
		
		startup : function() {
			if (this.isStartup) {
				return;
			}
			
			this.titleNode = $("<div class='title'></div>");
			this.previewBtnNode = $("<span class='preview'>"+preview+"</span>");
			
			this.screenBtnNode = $("<div class='screen'></div>");
			
			topic.subscribe(PREVIEW_SELECTED_CHANGE, this.change, this);
			this.isStartup = true;
		},
		
		change : function(evt) {
			this.titleNode.text(evt.fileName);
			this.titleNode.attr("title", evt.fileName);
			
			var type = this.value.getFileType(evt.data[evt.index]);
			if((type != "pdf" &&
					type != "office") || !this.isAsposeView) {
				this.screenBtnNode.hide();
			} else {
				this.screenBtnNode.show();
			}
			
		},
		
		draw : function($super) {
			var self = $super();
			this.element.append(this.titleNode);
			this.element.append(this.previewBtnNode);
			this.element.append(this.screenBtnNode);
			if(!this.isAsposeView)
				this.screenBtnNode.hide();
			
			this.previewBtnNode.on("click", function() {
				topic.publish("media/preview/show");
			});
			this.screenBtnNode.on("click", function(evt) {
				topic.publish("media/screen/show", evt);
			});
			return self;
		}
		
	});
	
	
	module.exports = fileHeader;
});