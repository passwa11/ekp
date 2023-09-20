/**
 * 显示项样式设置生成器
 */
define(function(require, exports, module) {

	var $ = require("lui/jquery");
	var base = require("lui/base");
	var topic = require('lui/topic');
	var dialog = require('lui/dialog');
	var footStatisticsItem = require("sys/modeling/base/mobile/resources/js/footStatisticsItem");


	var FootStatistics = base.Component.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			this.channel = cfg.channel;
			// 全局变量:存放显示项字段
			this.fieldFootStaticsItem = {};
			this.operationEle = cfg.operationEle;
			this.contentTemp = cfg.contentEle.html();
			cfg.contentEle.remove();
			this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
			this.build();
			this.storeData = cfg.storeData;
			this.allField = cfg.allField;
			this.initByStoreData(this.storeData);
		},
		build : function() {
			var self = this;
			topic.subscribe("modeling.selectDisplay.change", function(data) {
				self.selectDisplayData = data;
				self.selectDisplayChange(data.data.selected);
			});
		},
		selectDisplayChange : function(selected) {
			// 1、改变悬浮下拉框的值
			// 2、改变下面样式组件的增加删除
			var self = this;
			var data = this.selectDisplayData.data;
			$(".footStatistics_create_pop").html("");
			var $ul = $("<ul/>");
			var selectedField = [];
			var hasFootStatisticsItem = false;
			for (var i = 0; i < data.selected.length; i++) {
				if(data.selected[i].type == 'BigDecimal' || data.selected[i].type == 'Double'){
					var $li = $("<li/>");
					$li.attr("title", data.selected[i].text);
					$li.attr("lui-css-pop-idx", i);
					$li.text(data.selected[i].text);
					$li.on("click", function() {
						var idx = $(this).attr("lui-css-pop-idx");
						self.drawFootStatisticsItem(idx,self.selectDisplayData.data.selected[idx].field,self.selectDisplayData.data.selected[idx].text);
					});
					$ul.append($li);
					selectedField.push(data.selected[i].field.split(".")[0]);
					hasFootStatisticsItem = true;
				}
			}
			$(".footStatistics_create_pop").append($ul);
			//如果有金额或者数字的显示项，修改数据统计标题颜色
			if(hasFootStatisticsItem){
				$('.footStatisticsTitle').css("color","#000");
				$('.footStatistics_create').addClass("footStatistics_create_has");
				$('.footStatistics_create').removeClass("footStatistics_create_none");
			}else{
				$('.footStatisticsTitle').css("color","#999999");
				$('.footStatistics_create').addClass("footStatistics_create_none");
				$('.footStatistics_create').removeClass("footStatistics_create_has");
			}

			// 当显示项取消选择时，删除对应的样式组件
			var index = 0;
			for ( var f in self.fieldFootStaticsItem) {
				var isDel = true;
				for (var i = 0; i < selected.length; i++) {
					if(selected[i].field == f){
						isDel = false;
						self.fieldFootStaticsItem[f].updateIndex(i);
						//更新下标
						break;
					}
				}
				if(isDel){
					// 显示项字段样式不在显示项已选列表中 -> 移除
					if (self.fieldFootStaticsItem[f] != null) {
						self.fieldFootStaticsItem[f].destroy(self.fieldFootStaticsItem[f].element);
					}
				}
				index++;
			}

			var liLength = $(".footStatistics_create_pop").find("li").length;
			if(liLength > 5){
				liLength = 5;
			}
			if( 0 == liLength){
				$(".footStatistics_create").unbind("mouseenter");
				$(".footStatistics_create").unbind("mouseleave");
				//显示项为空则显示样式的新增取消高亮
				$(".footStatistics_create").css("pointer-events","none");
			}else{
				// 移入出现下拉列表
				$(".footStatistics_create").css("pointer-events","auto");

				$(".footStatistics_create").on("mouseenter",function() {

					$(".footStatistics_create_pop").css("height",
						(liLength * 30)+"px");
					$(".footStatistics_create_pop").css("border",
						"1px solid #ddd");
				})

				$(".footStatistics_create").on("mouseleave", function() {
					$(".footStatistics_create_pop").css("height", "0");
					$(".footStatistics_create_pop").css("border", "0");
				})

				$(document).on("mouseenter", ".footStatistics_create_pop li",function() {
					$(this).addClass("active");
				})

				$(document).on("mouseleave", ".footStatistics_create_pop li",function() {
					$(this).removeClass("active");
				})
			}

		},
		drawFootStatisticsItem : function(index,field,text,statisticsType) {
			var self = this;
			// 判断新增
			var fKey = field;
			if (!self.fieldFootStaticsItem[fKey]) {
				// 不存在
				var cfg = {
					contentTemp : self.contentTemp,
					operationEle : self.operationEle,
					index : index,
					field : fKey,
					text : text,
					statisticsType : statisticsType,
					parent : self,
					channel : self.channel
				};
				this.fieldFootStaticsItem[fKey] = new footStatisticsItem.FootStatisticsItem(cfg);
				topic.publish("preview.refresh", {"key": self.channel});
			}else{
				//定位到选中的配置项
				$("[data-lui-position='fdFootStatistics-"+ index +"']").each(function () {
					//更新 span title
					if($(this).hasClass("model-edit-view-oper")){
						var updateSpan = $(this).find(".model-edit-view-oper-head").find(".model-edit-view-oper-head-title").find("span");
						updateSpan.text(text);
						updateSpan.attr("title",text);
					}

					if(!$(this).hasClass("model-edit-view-oper")){
						switchSelectPosition($(this),"left")
					}
				});
			}
		},
		deleteWgt: function(key){
			delete this.fieldFootStaticsItem[key];
		},
		getKeyData : function() {
			// 底部统计对象
			var footStaticsArr = [];
			for ( var key in this.fieldFootStaticsItem) {
				// 一个底部统计
				if (this.fieldFootStaticsItem[key] != null) {
					footStaticsArr.push(this.fieldFootStaticsItem[key].getKeyData());
				}
			}
			return footStaticsArr;
		},
		initByStoreData : function(storeData) {
			var self = this;
			if (storeData && JSON.stringify(storeData) !="[]") {
				storeData = $.parseJSON(storeData);
				for(var j=0;j<storeData.length;j++){
					self.drawFootStatisticsItem(storeData[j].index,storeData[j].field,storeData[j].text,storeData[j].statisticsType);
				}
			}
		}
	});

	exports.FootStatistics = FootStatistics;
});
