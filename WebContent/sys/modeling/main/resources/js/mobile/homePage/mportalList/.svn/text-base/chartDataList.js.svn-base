/**
 * 图表区
 */
define(['dojo/_base/declare',"dijit/_WidgetBase", "dojo/dom-class", "dojo/dom-construct", "mui/openProxyMixin", "mui/util", 
		"sys/modeling/main/resources/js/mobile/homePage/common/_IndexMixin", "dojo/dom-class", "dojo/request","dojo/topic","dojo/parser"],
		function(declare, WidgetBase, domClass, domConstruct, openProxyMixin, util, _IndexMixin, domClass,request,topic,parser){
	
	return declare('sys.modeling.main.resources.js.mobile.homePage.mportalList.statistics', [WidgetBase, openProxyMixin, _IndexMixin] , {
		
		url : "",
		
		DATALOAD : "/sys/modeling/mobile/index/load",
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe(this.DATALOAD, 'onComplete');
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			domClass.add(this.domNode, "mportalList-dataList");
		},
		
		onComplete : function(data){
			domConstruct.empty(this.domNode)
			var attrs = data.chartList.attr;
			this.createContent(attrs.listView.value, attrs);
		},
		
		createContent : function(values, attrs){
			var self = this;
			if(values.nodeType === "chart"){
				//图表
				var url = util.urlResolver("/sys/modeling/base/dbEchartsChart.do?method=checkChartAuth&fdId=!{listViewId}",
						{listViewId: values.listView});
				request.get(util.formatUrl(url),{handleAs : 'json'}).then(function(data){
					if(data && data.auth == false){
						//无权限
						var style1 = "border: 1px dashed #D4D6DB;border-radius: 2px;";
						var style2 = "height:20rem;";
						var imageStyle = "margin-top:7.7rem;";
						var textStyle = "margin-top:7.7rem;";
						self.showNoAuth(self.domNode,style1,style2,null,imageStyle,textStyle);
					}else{
						self.doRenderContent(values.listView);
					}
				});
			}else if(values.nodeType === "chartset"){
				//图表集
				var url = util.urlResolver("/sys/modeling/base/dbEchartsChartSet.do?method=getChartsListById&fdId=!{listViewId}",
						{listViewId: values.listView});
				request.get(util.formatUrl(url),{handleAs : 'json'}).then(function(data){
					data = self.filterByAuth(data);
					if(data.length == 0){
						var style1 = "border: 1px dashed #D4D6DB;border-radius: 2px;";
						var style2 = "height:20rem;";
						var imageStyle = "margin-top:7.7rem;";
						var textStyle = "margin-top:7.7rem;";
						self.showNoAuth(self.domNode,style1,style2,null,imageStyle,textStyle);
					}
					for(var i = 0;i < data.length; i++){
						self.doRenderContent(data[i]);
					}
				});
			}
		},
		
		filterByAuth : function(data){
			var arr = [];
			for(var i = 0;i < data.length; i++){
				if(data[i].auth === false){
					continue;
				}
				arr.push(data[i].fdId);
			}
			return arr;
		},
		
		doRenderContent : function(chartId){
			var self = this;
			self.url = "/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=chartData&fdId="+chartId;
			var tabCardContainer = domConstruct.create("div",{
					className : "mportalList-card-container",
					innerHTML: this.getTmplHtml(self.url,chartId)
				},this.domNode);
			parser.parse(tabCardContainer);
		},
		
		getTmplHtml: function(url,chartId){
			var self = this;
			//获取主题
			var getThemeUrl = util.urlResolver("/sys/modeling/base/dbEchartsChart.do?method=findChartThemes&fdId=!{listViewId}",
					{listViewId: chartId});
			var theme = "Landrayblue";
			request.get(util.formatUrl(getThemeUrl),{handleAs : 'json'}).then(function(data){
				if(data.theme){
					theme = data.theme;
				}
			});
			var chartWidth = this.domNode.offsetWidth || this.domNode.clientWidth;
			var chartHeight = chartWidth;
			var tmpl = "<div data-dojo-type=\"mui/chart/Chart\" data-dojo-mixins='sys/mobile/js/mui/chart/Chart"+theme+"Mixin,sys/modeling/main/resources/js/mobile/homePage/mportalList/ChartModelingMixin' data-dojo-props=\"url:'"+url+"'\" ></div>";
		    return tmpl;
		}
		
	});
});