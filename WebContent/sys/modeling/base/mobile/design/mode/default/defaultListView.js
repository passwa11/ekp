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
	// 定义属性面板的信息
	var tmpData = {
		text : modelingLang['modeling.functional.area'],
		iconClass : "label_icon_listview",
		attr : {
			listViews : {
				"text" : "模块列表",
				"drawType" : "multiListView",	// listView对应listViewDraw（画配置页面）和listViewGetValue（获取值）方法
				"validate" : {
					title : "required",
					newlistView : "required"
				},	// 校验器
				"value" : []
			}
		}
	};
	
	var defaultListView = multiListAttrBase.MultiListAttrBase.extend({
		
		widgetKey : "listView",
		
		formatData : function(data){
			var datas = this.extend(tmpData, data);
			 // 如果没有设置值，则默认生成三个未定义
			 if(datas.attr.listViews.value.length === 0){
				 datas.attr.listViews.value.push({
					 title : modelingLang['modeling.Quick.entrance.1'],
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
				this.element.addClass("listView_sample_bg");
			}else{
				this.element.removeClass("listView_sample_bg");
				for(var i = 0;i < items.length;i++){
					var item = items[i];
					this.element.append(this.createItemDom(item));
				}				
			}
			$super(cfg);
		},
		
		createItemDom : function(item){
			var itemHtml = "<div class='view_block_listView'>";
			// 左侧
			itemHtml += "<div class='listView_item_title'>";
			itemHtml += "<i class='icon_ddlc' ></i>";
			itemHtml += "<span>"+ (item.title || modelingLang['modeling.Undefined']) +"</span>";
			itemHtml += "</div>";
			// 右侧
			itemHtml += "<div class='listView_item_more'>";
			itemHtml += "<i class='icon_more' ></i>";
			itemHtml += "</div>";
			
			itemHtml += "</div>";
			return $(itemHtml);
		}
	});
	
	module.exports = defaultListView;
})