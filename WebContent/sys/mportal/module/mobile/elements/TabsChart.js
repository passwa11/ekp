/**
 * 带有Tab页签的图表组件
 * 
 */
define([
    "dojo/_base/declare",
    "dojo/dom-style",
    "dojo/dom-class",
    "dojo/dom-prop",
    "dojo/query",
    "dojo/dom-construct",
    "dojo/dom-attr",
    "dojo/parser",
    "dijit/_WidgetBase"
	], function(declare, domStyle, domClass, domProp, query, domConstruct, domAttr, parser, WidgetBase) {
		
	return declare("sys.mportal.module.TabsChart", [ WidgetBase ], {
		 
		/**
		 * 页签信息
		 * @property JSON datas
		 * @example [{text:"页签1",url:"",selected:true},{text:"页签2",url:""},...]
		 * text:页签标题、  url:ECharts图表参数资源请求路径、  selected:默认选中项
		 */
		datas:[],
		
		/** 已经初始化的页签索引 **/
		initializedTabIndex : [],
		
		buildRendering : function() {
			this.inherited(arguments);
			var tabContainerNode = domConstruct.create('div', { className : 'muiTabsChartTabContainer' }, this.domNode);
			var tabContentNode = domConstruct.create('div', { className : 'muiTabsChartTabContent' }, tabContainerNode);
			if( this.datas && this.datas.length>0 ){
				// 构建页签
				this.buildTabContent(tabContentNode);
				// 获取当前需要选中的页签数据
				var selectedTabIndex = 0;
				var selectedTabData = this.datas[0];
				for(var i=0;i<this.datas.length;i++){
					var tabData = this.datas[0];
					if(tabData.selected==true){
						selectedTabIndex = i;
						selectedTabData = tabData;
						break;
					}
				}
				// 根据页签数据显示图表
				this.showChart(selectedTabData,selectedTabIndex);
			}

		},
		
        
		/**
		* 构建Tab页签项
		* @param tabContentNode 页签容器DOM
		* @return
		*/		
		buildTabContent: function(tabContentNode){
			var isSelected = false;
			for(var i=0; i<this.datas.length; i++){
				var tabData = this.datas[i];
				var tabNode = domConstruct.create('div', { className : 'tabsChartTab' }, tabContentNode);
				tabNode.innerText = tabData.text;
				if( isSelected==false && tabData.selected==true ){
					domClass.add(tabNode,"active");
				}
				this.bindTabClick(tabNode,tabData,i);
			}
			
		},
		
		/**
		* 绑定页签点击事件
		* @param tabNode  页签DOM
		* @param tabData  页签JSON数据对象
		* @param tabIndex 页签索引
		* @return
		*/			
		bindTabClick: function(tabNode, tabData, tabIndex){
			var _self = this;
    		this.connect(tabNode, "click", (function(tabNode,tabData,tabIndex){
		          return function(evt){
						var optionDomList = query(".tabsChartTab",_self.domNode);
				    	for(var i=0;i<optionDomList.length;i++){
				    		var optionDom = optionDomList[i];
				    		if(tabNode==optionDom){
				    			domClass.add(optionDom,"active");
				    			_self.showChart(tabData,tabIndex);
				    		}else{
								domClass.remove(optionDom,"active");
				    		}
				    	}
		          }
  		     })(tabNode,tabData,tabIndex));
		},
		
		
		/**
		* 根据类型显示相应的图表
		* @param tabData 页签JSON数据对象
		* @param tabIndex 页签索引
		* @return
		*/
		showChart: function(tabData,tabIndex){
            var isExist = false;
            for(var i=0; i<this.initializedTabIndex.length; i++){
            	if(tabIndex == this.initializedTabIndex[i]){
            		isExist = true;
            		break;
            	}
            }
            if(isExist){
            	query(".muiTabsChart[tab_index='"+tabIndex+"']",this.domNode)[0].style.display="block";
            }else{
            	var chartNode = domConstruct.create("div", {className:"muiTabsChart" ,innerHTML: this.getTmplHtml(tabData.url) }, this.domNode);
            	domAttr.set(chartNode,"tab_index",tabIndex);
            	this.initializedTabIndex.push(tabIndex);
   	            parser.parse(chartNode, {noStart: true}).then(function(widgetList) {});
            }
            // 延迟隐藏需要隐藏的Tab，避免因页面高度突然减小导致页面跳动（需等待选中的Tab图表显示之后再进行隐藏）
            // setTimeout的定时毫秒不能小于图表构建(sys\mobile\js\mui\chart\Chart.js-------buildRendering)的setTimeout
            var _self = this;
            setTimeout(function(){
                for(var i=0; i<_self.initializedTabIndex.length; i++){
                	if(tabIndex != _self.initializedTabIndex[i]){
                		query(".muiTabsChart[tab_index='"+_self.initializedTabIndex[i]+"']",_self.domNode)[0].style.display="none";
                	}
                }
            },2);

		},
		
		/**
		* 根据类型获取图表DOJO模板HTML字符串
		* @param url ECharts图表参数资源请求路径
		* @return
		*/	
		getTmplHtml: function(url){
			var chartWidth = this.domNode.offsetWidth || this.domNode.clientWidth;
			var chartHeight = chartWidth;
			var tmpl = "<div data-dojo-type=\"mui/chart/Chart\" data-dojo-props=\"url:'"+url+"'\" ></div>";
		    return tmpl;
		}
		
		
	});
});