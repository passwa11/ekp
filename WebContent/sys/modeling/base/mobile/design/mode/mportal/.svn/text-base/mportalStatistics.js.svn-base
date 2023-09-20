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
	var fixNum = 6;	// 收缩的时候，最多展示的方块
	
	var tmpData = {
		text : modelingLang["modeling.Statistics.area"],
		iconClass : "label_icon_statistics",
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
	
	var mportalStatistics = multiListAttrBase.MultiListAttrBase.extend({
		
		widgetKey : "statistics",
		
		formatData : function(data){
			var datas = this.extend(tmpData, data);
			 // 如果没有设置值，则默认生成三个未定义
			 if(datas.attr.listViews.value.length === 0){
				 datas.attr.listViews.value.push({
					 title : modelingLang["modeling.Example.1"]
				 });
				 datas.attr.listViews.value.push({
					 title : modelingLang["modeling.Example.2"]
				 });
				 datas.attr.listViews.value.push({
					 title : modelingLang["modeling.Example.3"]
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
			var staticsHtml = "";
			var items = this.data.attr.listViews.value;
			// 当方块大于fixNum个时，出现伸缩
			var topSize = items.length > fixNum ? fixNum:items.length;
			staticsHtml += "<div class='mportal-collapse shrink'>";
			staticsHtml += "<div class='mportal-collapse-wrap top'>";
			var width = topSize < 3 ? (parseInt(100/topSize) + "%") : "";
			for(var i = 0;i < topSize;i++){
				staticsHtml += this.getItemHtml(items[i], width);
			}
			staticsHtml += "</div>";
			// 出现伸展按钮
			if(items.length > fixNum){
				staticsHtml += "<div class='mportal-collapse-wrap bottom'>";
				for(var i = fixNum;i < items.length;i++){
					staticsHtml += this.getItemHtml(items[i]);
				}
				staticsHtml += "</div>";
				// 伸展按钮
				staticsHtml += '<div class="mportal-collapse-btn"></div>';
			}
			staticsHtml += "</div>";
			this.element.append(staticsHtml);
			/************* 添加事件 start ************/
			var self = this;
			this.element.find(".mportal-collapse-btn").on("click",function(e){
				e.stopPropagation()
				self.element.find('.mportal-collapse-wrap.bottom').slideToggle();
				self.element.find('.mportal-collapse').toggleClass('shrink');
			});
			/************* 添加事件 end ************/
		},
		
		getItemHtml : function(item, width){
			//var num = Math.round(Math.random()*1000);
			var num = "5";
			var itemHtml = "<div class='mportal-collapse-item'";
			if(width){
				itemHtml += " style='width:"+ width +"'";
			}
			itemHtml += ">";
			itemHtml += "<div class='mportal-collapse-item-wrap'>";
			itemHtml += "<div>" + num + "</div>";
			itemHtml += "<p>"+ (item.title || modelingLang["modeling.Undefined"]) +"</p>";
			itemHtml += "</div>";
			itemHtml += "</div>";
			return itemHtml;
		},
		
		doDrawPanelExtend : function(container){
			// 画外框
			var $baseAttrContainer = this.drawPanelBaseAttrFrame(container);
			
			// 画内容
			this.drawPanelBaseAttr($baseAttrContainer);
		},
		
		drawPanelBaseAttrFrame : function(container){
			var $fieldValue = $("<div class='field_item_value' />").appendTo(container);
			var $fieldContent = $("<div class='field_item_content' />").appendTo($fieldValue);
			return $("<div class='content_wrap' />").appendTo($fieldContent);
		},
		
		drawPanelBaseAttr : function(container){
			
		}
		
	});
	
	module.exports = mportalStatistics;
})