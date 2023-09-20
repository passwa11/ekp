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
		text : modelingLang["modeling.functional.area"],
		iconClass : "label_icon_listview",
		attr : {
			title : {
				"text" : "标题",
				"drawType" : "input",
				"validate" : "required",
				"value" : ""
			},
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
	
	var mportalListView = multiListAttrBase.MultiListAttrBase.extend({
		
		widgetKey : "listView",
		
		formatData : function(data){
			var datas = this.extend(tmpData, data);
			 // 如果没有设置值，则默认生成三个未定义
			 if(datas.attr.listViews.value.length === 0){
				 datas.attr.listViews.value.push({
					 title : modelingLang["modeling.Quick.entrance.1"]
				 });
				 datas.attr.listViews.value.push({
					 title : modelingLang["modeling.Quick.entrance.2"]
				 });
				/* datas.attr.listViews.value.push({
					 title : "快捷入口3"
				 });
				 datas.attr.listViews.value.push({
					 title : "快捷入口4"
				 });
				 datas.attr.listViews.value.push({
					 title : "快捷入口5"
				 });*/
			 }
			 return datas;
		},
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.isCount = true;	// 是否需要设置总计页签
			this.setInitData(cfg.data);
	    },
	    
		draw : function($super,cfg){
			this.element.html("");
			// 画标题
			this.drawTitle();
			
			// 画方块
			this.drawContent();
			
			$super(cfg);
		},
		
		drawTitle : function(){
			var title = this.data.attr.title.value || modelingLang["modelingAppNav.docSubject"];
			var titleHtml = "";
			titleHtml += "<div class='block-title'>";
			titleHtml += "<p class=''>"+ title +"</p>";
			titleHtml += "</div>";
			this.element.append(titleHtml);
		},
		
		drawContent : function(){
			var items = this.data.attr.listViews.value;
			var contentHtml = "";
			contentHtml += "<div class='content-list'>";
			for(var i = 0;i < items.length;i++){
				contentHtml += this.getItemHtml(items[i]);
			}
			contentHtml += "</div>";
			this.element.append(contentHtml);
		},
		
		getItemHtml : function(item){
			var itemHtml = "<div class='list-item'>";
			// 左侧
			itemHtml += "<div class='item-icon'></div>";
			
			// 中间
			itemHtml += "<div class='item-detail'><p>"+ (item.title || modelingLang["modeling.Undefined"]) +"</p></div>";
			
			// 右侧
			itemHtml += "<div class='item-arrow'></div>";
			
			itemHtml += "</div>";
			return itemHtml;
		}
	});
	
	module.exports = mportalListView;
})