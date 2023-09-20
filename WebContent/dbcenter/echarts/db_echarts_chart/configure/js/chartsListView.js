define(function(require, exports, module) {
		var base = require('lui/base');
		var $ = require("lui/jquery");
		var Evented = require('lui/Evented');
		var topic = require('lui/topic');
		var layout = require('lui/view/layout');
		var source = require("lui/data/source");

		var chartsListView = base.Container.extend({
			initProps: function($super, cfg) {
				$super(cfg);
			},
			
			addChild: function(child) {
				// echartsType.json数据源
				if(child instanceof source.BaseSource){
				   this.dataSource = child;
				}
			},
			
			startup : function(){
				if (!this.layout && this.dataSource) {
					var self = this;
					self.isChosen = false; // 是否已选择
					self.navDomArr = []; // 导航元素的集合
					self.chartDomArr = []; // 所有图表样式的集合
					self.curNavChosen; // 当前选择的导航元素
					self.curChartChosen; // 当前选择的图表元素
					self.curItem = {}; // 当前选择的item
					self.layout = new layout.Template({
						src : require.resolve('../listview/content.jsp#'),
						parent : self
					});
					self.layout.startup();
				}
			},
			
			doLayout : function(obj){
				var self = this;
				var $wrap = $(obj);
				self.element.append($wrap);
				
				self.$chartViewDomNode = self.element.find(".chartView");
				
				self.$descDomNode = self.element.find(".chartDesc");
				
				/********添加元素的事件********/
				$wrap.find("[data-charttype]").each(function(index,dom){
					var charttype = $(dom).data("charttype");
					if(charttype.indexOf("-") == -1){  // 左侧导航菜单项
						self.navDomArr.push(dom);
						// 导航跳转（锚点滑动）
						$(dom).on("click",function(){
							self.curNavChosen = dom;
							$(dom).siblings().removeClass('active')
							$(dom).addClass("active");
							var offsetTop = self.element.find("[data-charttype-title='"+charttype+"']").prop("offsetTop");
							self.element.find(".chart-content").animate({scrollTop: offsetTop}, 'fast');
						});
					}else{ // 右侧图形展示项
						self.chartDomArr.push(dom);
						// 点击切换图表
						$(dom).on("click",function(){
							var item = self.findItemByKey(charttype);
							var subChart = item.subChart;
							if(self.curChartChosen){
								if(self.curChartChosen == dom){
									return;	
								}else{
									// 清空已选的图表样式
									$(self.curChartChosen).removeClass("chart-selected");
								}
							}
							$(dom).addClass("chart-selected");
							// 构建提示信息，待优化
							var html = "";
							html += "<h2 class=\"info-heading-title\">"+ subChart.text +"</h2>";
							html += "<h6 class=\"info-subhead\">"+ DbcenterLang.characteristic +"：</h6>";
							html += "<ol class=\"info-ol\">" + subChart.feature + "</ol>";
							html += "<h6 class=\"info-subhead\">"+ DbcenterLang.applicableScenarios +"：</h6>";
							html += "<ol class=\"info-ol\">" + subChart.application + "</ol>";
							html += "<h6 class=\"info-subhead\">" + subChart.summary + "</h6>";
							self.$descDomNode.html(html);
							self.isChosen = true;
							self.curChartChosen = dom;
							self.curItem = item;
						});
						$(dom).on("dblclick",function(){
							var item = self.findItemByKey(charttype);
							self.curChartChosen = dom;
							self.isChosen = true;
							self.curItem = item;
							LUI("__step").next();
						});
					}
				});
				
				// 默认选中左侧导航菜单第一项
				$wrap.find("ul.lui-chartConfig-nav li[data-charttype]").eq(0).click();
				// 默认选中右侧图形展示第一项
				$wrap.find("div.chart-content a[data-charttype]").eq(0).click();
				// 给“如何选择适合的图表？”链接绑定点击事件
				$wrap.find("div.how-to-choose-chart").click(function(){
					Com_OpenWindow("configure/how_to_choose_chart.jsp");
				});	
			},
			// 根据类型找到对应的信息
			findItemByKey : function(key){
				var self = this;
				var item = {};
				var arr = key.split("-");
				var charts = self.dataSource.source.charts;
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
			}
		});
		
		exports.chartsListView = chartsListView;
})