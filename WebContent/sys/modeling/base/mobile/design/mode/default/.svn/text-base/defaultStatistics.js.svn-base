/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var render = require("lui/view/render");
	var dialog = require("lui/dialog");
	var multiListAttrBase = require("sys/modeling/base/mobile/design/mode/common/multiListAttrBase");
	var modelingLang = require("lang!sys-modeling-base");
	var tmpData = {
		text : modelingLang['modeling.Statistics.area'],
		iconClass : "label_icon_statistics",
		attr : {
			listViews : {
				"text" : "模块列表",
				"drawType" : "multiListView",	// listView对应listViewDraw（画配置页面）和listViewGetValue（获取值）方法
				"validate" : {
					title : "required",
					newlistView : "required"

				},	// 校验器
				/*
				*  "value":[
                    {
                        "title":"线索",
                        "listView":[
                            {
                                "text":"所有线索",
                                 "listView":"1788d91262f52c19598e7c34cd09657a"
                            },
                            {
                                "text":"待跟进线索",
                                "listView":"1788d91262f52c19598e7c34cd09657a"
                            },
                            {
                                "text":"已跟进线索",
                                "listView":"1788d91262f52c19598e7c34cd09657a"
                            }
                        ]
                    }
                   ] *
				* */
				"value" : []
			}
		}
	};
	
	var defaultStatistics = multiListAttrBase.MultiListAttrBase.extend({
		
		widgetKey : "statistics",
		
		formatData : function(data){
			var datas = this.extend(tmpData, data);
			 // 如果没有设置值，则默认生成三个未定义
			 if(datas.attr.listViews.value.length === 0){
				 datas.attr.listViews.value.push({
					 title : modelingLang['modeling.Example.1'],
					 newlistView:[]
				 });
				 datas.attr.listViews.value.push({
					 title : modelingLang['modeling.Example.2'],
					 newlistView:[]
				 });
			 }
			 return datas;
		},
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.setInitData(cfg.data);
	    },
	    
		draw : function($super,cfg){
			this.element.html("");
			var items = this.data.attr.listViews.value;
			if(items.length === 0){
				this.element.addClass("statistics_sample_bg");
			}else{
				this.element.removeClass("statistics_sample_bg");
				for(var i = 0;i < items.length;i++){
					var item = items[i];
					this.element.append(this.createItemDom(item));
				}				
			}
			$super(cfg);
		},
		
		createItemDom : function(item){
			var itemHtml = "<div class='view_block_statistics'>";
			// Math.floor(Math.random() * 50 + 1);
			itemHtml += "<div class='statistics_item_count'>";
			itemHtml += "<span class='statistics_item_countTxt' >5</span>";
			itemHtml += "</div>";
			
			itemHtml += "<span class='statistics_item_txt'>";
			itemHtml += item.title || modelingLang['modeling.Undefined'];
			itemHtml += "</span>";
			
			itemHtml += "</div>";
			return $(itemHtml);
		}
		
	});
	
	module.exports = defaultStatistics;
})