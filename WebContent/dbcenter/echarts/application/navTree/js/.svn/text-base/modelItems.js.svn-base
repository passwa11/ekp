define(function(require, exports, module) {
	var base = require('lui/base');
	var $ = require("lui/jquery");
	var topic = require('lui/topic');

	var modelItems = base.Component.extend({
		initProps: function($super, cfg) {
			$super(cfg);
			var self = this;
			self.modelInfos = cfg.modelInfos;
			self.modelItems = []; // {"domNode":li(node),"modelInfo":{text:xx,value:xxx,modelName:xxx}}
			self.activeDomNode = null;
		},
		startup : function(){
			var self = this;
			var $ul = $("<ul>");
			$ul.addClass("criterion-modelItems-ul");
			var categorys = self.modelInfos["categorys"];
			for(var i = 0;i < categorys.length;i++){
				var category = categorys[i];
				var item = self.createItem(category);
				self.modelItems.push({"domNode":item,"modelInfo":category});
				$ul.append(item);
			}
			self.element.append($ul);
		},
		createItem : function(info){
			var self = this;
			var $li = $("<li>");
			var html = "<span>";
			html += info.text;
			html += "</span>";
			$li.append(html);
			$li.click(function(evt){
				self.activeItem(this);
			});
			return $li;
		},
		activeItem : function(dom){
			var self = this;
			for(var i = 0;i < self.modelItems.length;i++){
				if(dom == self.modelItems[i]["domNode"][0]){
					self.activeDomNode = self.modelItems[i];
					self.modelItems[i]["domNode"].addClass("active");
				}else{
					self.modelItems[i]["domNode"].removeClass("active");
				}
			}
		}
	});
	exports.modelItems = modelItems;
})