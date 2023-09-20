define(function(require, exports, module) {
	var base = require('lui/base');
	var $ = require("lui/jquery");
	var strutil = require("lui/util/str");
	var env = require("lui/util/env");
	
	var dataView = base.Component.extend({
		initProps: function($super, cfg) {
			$super(cfg);
			var self = this;
			self.modelItem = LUI(cfg.linedBlockId);
			self.showAreaId = cfg.showAreaId;
			self.dataViewMap = {}; // key为modelInfo的value，value为viewDom
			self.currentView = null;
			//self.tempUrl = cfg.modelUrl;
			var lang = [];
			lang.push("dbcenter-echarts-application:dbEchartsNavTree.queryNullTip");
			lang.push("dbcenter-echarts-application:dbEchartsNavTree.more");
			self.lang = Data_GetResourceString(lang.join(";"));
		},
		
		startup : function(){
			var self = this;
			self.showView = LUI(self.showAreaId);
			var modelItems = self.modelItem.modelItems;
			// 每一个model创建一个对应的视图，由于是不分页的model查询（存在性能的风险），尽量加缓存
			for(var i = 0;i < modelItems.length;i++){
				var modelItem = modelItems[i];
				// 闭包 展示当前视图，同时隐藏其他视图
				modelItem["domNode"].click(function(modelInfo,_self){
					return function(){
						var modelValue = modelInfo["modelInfo"]["value"];
						if(!_self.dataViewMap.hasOwnProperty(modelValue)){
							var $view = _self.createView(modelInfo["modelInfo"]);
							_self.dataViewMap[modelValue] = $view;
							_self.element.append($view);
						}
						_self.dataViewMap[modelValue].show();
						_self.currentView = _self.dataViewMap[modelValue];
						// 切换视图的时候，默认把第一条记录勾选上
						_self.currentView.find("li:first").trigger("click");
						// 隐藏其他视图
						for(var key in _self.dataViewMap){
							if(key != modelValue){
								_self.dataViewMap[key].hide();
							}
						}
					}
				}(modelItem,self));
			}
		},
		
		getDatas : function(modelInfo){
			return modelInfo["datas"];
		},
		
		createView : function(modelInfo){
			// 创建视图div
			var self = this;
			var $view = $("<div>");
			$view.addClass("criterion-dataView-content");
			// 发送请求
			var data = [];
			data = self.getDatas(modelInfo);
			
			// 构建元素
			if(data.length == 0){
				var html = "<div class='noRecord'>"+ this.lang[0] +"</div>";
				$view.append(html);
			}else{
				var $ul = $("<ul>");
				$ul.addClass("lui-dbcenter-panel-list");
				for(var i = 0;i < data.length;i++){
					var record = data[i];
					var html = "<li class='lui-dbcenter-panel-list-item'>";
					html += "<span>";
					html += strutil.encodeHTML(record.text);
					html += "</span>";
					html += "</li>";
					var $li = $(html);
					// 点击触发刷新iframe内容
					$li.click(function(dataUrl,_self){
						return function(){
							$(".lui-dbcenter-panel-list-item").removeClass("selected");
							$(this).addClass("selected");
							 var iframeWgt = _self.showView;
							 iframeWgt.reload(dataUrl);
						}
					}(env.fn.formatUrl(record.value),self));
					$ul.append($li);
				}
				$view.append($ul);
				$view.css("height","55px");
				// “更多”
				if(data.length > 4){
					var $expand = $("<a href='javascript:void(0);' id='expandDiv' class='lui-dbcenter-extra-more expand' data-status='0'>"+ self.lang[1] +"<i class='arrow'></i></a>");
					$expand.click(function() {
						var dom = this;
						var containerHeight = $view.find(".lui-dbcenter-panel-list").outerHeight(true);
						var lineHeight = $view.find(".lui-dbcenter-panel-list-item").outerHeight(true);
						var status = $(dom).attr('data-status');
						if(status == '0'){
							$view.animate({height:containerHeight+"px"});
							$(dom).attr('data-status','1');
							$view.find(".lui-dbcenter-extra-more").removeClass("expand");
							$view.find(".lui-dbcenter-extra-more").addClass("collapse");
						}else{
							$view.animate({height:lineHeight+15+"px"});
							$(dom).attr('data-status','0');
							$view.find(".lui-dbcenter-extra-more").removeClass("collapse");
							$view.find(".lui-dbcenter-extra-more").addClass("expand");
						}
					});
					$view.append($expand);
				}
			}
			
			return $view;
		}
	});
	exports.dataView = dataView;
})