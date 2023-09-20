define( function(require, exports, module) {
	var base = require('lui/base');
	var $ = require("lui/jquery");
	var tabPage = base.Component.extend( {
		activeTab:0,
		initialize : function($super, config) {
			$super(config);
			console.log($("#"+config.id))
			this.element = $("#"+config.id);
			this.buttonContainer = $("<div class='lui-tab-page-box clearfloat'></div>");
			this.contentContainer = $("<div class='lui-tab-page-content'></div>")
			this.element.append(this.buttonContainer);
			this.element.append(this.contentContainer);
			this.drawPage(config);
			this.bindEvent();
			this.startup();
		},
		startup : function(config) {
			var self = this;
			
		},
		bindEvent:function(){
			$(".lui-tab-page-box div").on("click",function(e){
				$(".lui-tab-page-box .lui-tab-page-button").removeClass("lui-tab-page-active");
				var currIndex = $(e.target).index();
				$(".lui-tab-page-box .lui-tab-page-button").eq(currIndex).addClass("lui-tab-page-active");
				$(".lui-tab-page-content .lui-tab-page-iframe").hide();
				$(".lui-tab-page-content .lui-tab-page-iframe").eq(currIndex).show();
			})
		},
		drawPage : function(config) {
			var buttons = config.tabButtons;
			var contents = config.tabPages;
			var self = this;
			buttons&&$.map(buttons,function(item,index){
				self.buttonContainer.append(self.createButton(item,index));
			})
			contents&&$.map(contents,function(item,index){
				self.contentContainer.append(self.createContent(item,index));
			})
		},
		createContent:function(item,index){
			var content = $("<div class='lui-tab-page-iframe'></div>")
			var iframe = $('<iframe height="490" class="lui_widget_iframe hr_org_info_iframe" frameborder="no" border="0"></iframe>')
			iframe.attr("id",item.id).attr("src",item.url);
			content.append(iframe);
			if(this.activeTab == index){
				content.show();
			}else{
				content.hide();
			}
			return content
		},
		createButton:function(item,index){
			var button = $("<div class='lui-tab-page-button'></div>").text(item.text);
			if(item.active){
				this.activeTab =index;
				button.addClass("lui-tab-page-active")
			}
			return button
		}
		
	});

	exports.tabPage = tabPage;
});