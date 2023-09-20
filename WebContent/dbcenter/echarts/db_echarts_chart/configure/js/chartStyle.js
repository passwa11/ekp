define(function(require, exports, module) {
		var base = require('lui/base');
		var $ = require("lui/jquery");
		var topic = require('lui/topic');
		var layout = require('lui/view/layout');

		var chartStyle = base.Component.extend({
			initProps: function($super, cfg) {
				$super(cfg);
				var self = this;
				// 绑定元素
				self.domNode = cfg.domNode;
				self.chartsType = cfg.chartsType;
				self.curItem = {}; // 当前图表类型的信息
				self.value = {}; // 值
				self.fixedValue = {}; // 值
				self.chart = {};// 预览echart
				self.themes = []; // 主题
				self.nullValueControls = []; // 显示为空值的控件名称集合，实现处理在config.js中的dbecharts.read、dbecharts.write（详见：dbcenter/echarts/common/config.js） 
				// 如果有值，则渲染
				if(cfg.value){
					self.value = cfg.value;
				}
			},
			
			startup : function(){
				var self = this;
				
				var key = self.value.chartType;
				if(key){
					self.curItem = self.findItemByKey(key);
					self.draw({"item":self.curItem});
				}
				// 订阅事件
				topic.channel("dbcenterchart").subscribe('chart.select', self.draw, self);
			},
			
			draw : function(evt){
				// 格式化config，和所有类型
				var self = this;
				if(evt.item){
					self.curItem = evt.item;
					// 新增场景首次绘制图表样式配置区域或者切换选择了新的图表类型，需要设置相应图表类型的初始化配置值
					if($.isEmptyObject(self.value) || self.value.chartType!=self.curItem.chartType ){
						var editOption = self.curItem.subChart.defaultEditValueOption;
						self.value = editOption?editOption:{};
						self.value.chartType = self.curItem.chartType; // 更新chartType
					}
					if(!self.layout){
						self.layout = new layout.Template({
							src : require.resolve('../listview/chartStyle.jsp#'),
							parent : self
						});
						self.layout.startup();
					}
					self._draw();
				}
			},
			
			_draw : function(){
				var self = this;
				if(self.layout){
					self.layout.on("error",function(msg){
						self.element.append(msg);
					});
					self.layout.get(self,function(obj){
						 self.refresh(obj);
					});
				}
			},
			
			refresh : function(obj){
				var self = this;
				if(!$.isEmptyObject(self.curItem)){
					
					// 清空原有选项
					$(self.domNode).find("tr.dynamicTr").remove();
					
					// 获取HTML
					var $tbody = $(self.domNode).find("tbody");
					if($tbody.length > 0){
						$tbody.append(obj);	
					}else{
						$(self.domNode).append(obj);	
					}
					
					/******* 添加事件 start *******/
					// 展示形式 同步更新个性属性
					$(self.domNode).find(".dynamicTr [data-valuechange='true']").change(function(){
						if(this.name == "chartOption.chartType"){
							var item = self.updateCurItem($(this).find("option:selected").val());
							$(self.domNode).find("[name='chartOption.personality']").val(self.getPersonality());
						}
						renderPreviewEchart("change_style"); // 修改图表样式配置触发重新渲染预览图表
					});
					/******* 添加事件 end *******/
					
					// 填充数据
					if(!$.isEmptyObject(self.value)){
						self.value.personality = self.getPersonality();
						var val = {"chartOption":self.value};
						self._merge(val,self.fixedValue); // 首次才有作用
						val.nullValueControls = self.nullValueControls;
						val.defaultEditValueOption = {"chartOption":self.curItem.subChart.defaultEditValueOption};
						dbecharts.write("fdCode", val, self.domNode);	
					}
					
					// 发布事件
					topic.channel("dbcenterchart").publish("chart.style.onload",self);
				}
			},
			
			updateCurItem : function(key){
				var self = this;
				self.curItem = self.findItemByKey(key);
				return self.curItem;
			},
			
			// 获取json配置的个性化属性
			getCurEchartsOptions : function(){
				var echartsOptions = this.curItem.subChart.echartsOptions;
				return echartsOptions;
			},
			
			getPersonality : function(){
				var self = this;
				return JSON.stringify(self.getCurEchartsOptions()).replace(/\"/g, "&quot;");
			},
			
			findItemByKey : function(key){
				var self = this;
				var item = {};
				var arr = key.split("-");
				var charts = self.chartsType;
				item.chartType = key;
				item.chart = charts[arr[0]];
				var series = item.chart.series;
				for(var i in series){
					if(series[i].type == arr[1]){
						item.subChart = series[i]; 
						break;
					}
				}
				return item;
			},
			
			_merge:function(destination,source){
				for (var property in source){
					if(typeof source[property]==="object"){
						if(!destination[property]){
							destination[property]={};
						}
						destination[property]=this._merge(destination[property],source[property]);
					}else{
						destination[property] = source[property];
					}
				}
				return destination;
			}
			
		});
		
		exports.chartStyle = chartStyle;
})