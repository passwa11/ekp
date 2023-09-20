/**
 * 移动端图表组件
 */
define([
        "dojo/_base/declare", 
        "dojo/_base/array",
        "dijit/_WidgetBase",
        "dijit/registry",
        'dojo/_base/lang',
        'dojo/dom-construct',
        'dojo/dom-style',
        'dojo/dom-class',
        "dojo/dom-geometry",
        "dojo/window",
        "dojo/request",
        "dojo/parser",
        "dojo/query",
        'mui/util',
        "lib/echart/echarts",
        "mui/chart/ChartZoomMixin",
        "mui/chart/ChartDefaultMixin"
        ],function(declare,array,WidgetBase,registry,lang,domConstruct,domStyle,domClass,domGeometry,win,request,parser,query,util,echarts,ChartZoomMixin,ChartDefaultMixin){
	
	return declare("mui.chart.chart",[WidgetBase,ChartZoomMixin,ChartDefaultMixin],{
		
		url : '',
		
		isLazy : false,//是否延时加载图表组件
		
		dbcenter : false, //是否是图表中心的图表
		
		drawed : false,
		
		chartTmpl:'<div data-dojo-type="mui/table/ScrollableHContainer" data-dojo-dom="container"><div data-dojo-dom="view"><div data-dojo-dom="content"></div></div><div data-dojo-dom="list" style="display:none"><div style="text-align:center;width:40px;color:#37ace1;margin:3px 5px;" data-dojo-dom="backlist">lang_comeBack</div><div data-dojo-type="mui/view/DocScrollableView" data-dojo-dom="scrollview"><div data-dojo-type="mui/table/ScrollableHContainer" data-dojo-dom="listview"><div data-dojo-type="mui/table/ScrollableHView"><table cellspacing="0" cellpadding="0" class="detailTableNormal"><tr><td class="detailTableNormalTd"><table class="muiAgendaNormal muiNormal" width="100%" border="0" cellspacing="0" cellpadding="0"></table></td></tr></table></div></div></div></div></div>',
		
		chartTheme : null,

		lang_props : null,
		
		_setUrlAttr : function(url){
			this._set('url',url);
			this.drawed = false;
			this.requestChart();
		},
		
		buildRendering : function(){
			var self = this;
			this.inherited(arguments);
			domClass.add(this.domNode,'muiChart');
			this.chartTmpl = this.chartTmpl.replace("lang_comeBack",this.getLangProp("lang_comeBack","返回"));
			//构建Echart图表区域
			var tmpl = lang.replace(this.chartTmpl,{
				//拓展字段
			});
			
			setTimeout(function(){
				var geometry = self.globalGeometry();
				parser.parse(domConstruct.create('div',{ innerHTML:tmpl },self.domNode ,'last'))
					.then(function(widgetList) {
						array.forEach(widgetList, function(widget, index) {
							if(index == 0){
								self.container = widget.domNode;
								self.view = query('[data-dojo-dom="view"]',widget.domNode)[0];
								self.content = query('[data-dojo-dom="content"]',widget.domNode)[0];
								self.list = query('[data-dojo-dom="list"]',widget.domNode)[0];
								var backBtn = query('[data-dojo-dom="backlist"]',self.list)[0];
								self.connect(backBtn,'click',function() {
					 				self.switchChart("0");
					 			});
								//设置默认宽高
								domStyle.set(widget.domNode,'height',geometry.h + 'px');
								domStyle.set(self.content,'width',geometry.w + 'px');
								domStyle.set(self.content,'height',geometry.h + 'px');
								if(!self.isLazy){
									self.requestChart();
								}
							}
						});
					self.buildRenderingCompleted();	
				});				
			},1);

		},
		
		buildRenderingCompleted : function(){
			this.inherited(arguments);
		},
		
		startup : function(){
			this.inherited(arguments);
		},
		
		//获取默认宽高
		globalGeometry : function(){
			var chartNodeWidth = this.getChartDomWidth(this.domNode);
			if(chartNodeWidth>0){
				return {
					w : chartNodeWidth,
					h : chartNodeWidth
				}
			}else{
				var doc = win.getBox(document);
				return {
					w : doc.w,
					h : doc.w 
				};
			}

		},
		
		// 获取图表所在的DOM的宽度（如果宽度为0，则递归向上获取父级DOM的宽度作为图表的宽度）
		getChartDomWidth: function(chartNode){
			var width = 0;
			if(chartNode){
				var width = domGeometry.getContentBox(chartNode).w;
				if(width==0){
				   return this.getChartDomWidth(chartNode.parentNode);
				}
			}
			return width;
		},
		
		//请求chart数据
		requestChart : function(url){
			var url = util.formatUrl(url || this.url),
				self = this;
			try{
				var query = location.href.split('#');
				console.log("query:"+query);
				if(query.length>1){
					var query_result = "";
					var queryPara = query[1];
					if(queryPara.indexOf("cri.q=")==0){
						queryPara = queryPara.substr(6);
						var paramPairs = queryPara.split(';');
						for(var i=0;i<paramPairs.length;i++){
							var paramPair = paramPairs[i].split(':');
							var name = paramPair[0];
							var value = "";
							if(paramPair.length>0){
								value = paramPair[1];
							}
							query_result += "&q."+name+"="+value;
						}
					}
					url += query_result;
				}
			}catch(e){
				console.log(e);
			}

			request
				.post(url)
				.response
				.then(function(datas) {
					var options = self.toJSON(datas.data);
					lang.hitch(self,'renderChart')(options);
			});
		},
		
		switchChart:function(isTable){
 			if(isTable=="1"){//当前状态显示图标
 				domStyle.set(this.view,"display","none");
 				this.showList(this.list);
 			}else{
 				domStyle.set(this.view,"display","block");
 				domStyle.set(this.list,"display","none");
 			}
 		},
		
 		showList:function(listDiv){
 			var _self = this; 
 			var xdata=[];
 			var field = [];
 			var datas=[];
 			var series = _self.result.series;
 			var listTable = query('.muiAgendaNormal',listDiv)[0];
 			if(series.length==1&&(series[0].type == 'pie'||series[0].type == 'gauge')){
 				var pData = series[0];
 				var tempdata=[];
 				field.push(pData.name);
 				for(var key in pData.data){
 					var obj = pData.data[key];
 					xdata.push(obj.name);
 					tempdata.push(obj.value);
 				}
 				datas.push(tempdata);
 			}else{
 				xdata = _self.result.xAxis[0].data;
 				for(var i=0;i<series.length;i++){
	 				var sData = series[i];
	 				field.push(sData.name);
	 				datas.push(sData.data);
	 			}
 			}
 			
 			listTable.innerHTML = '';
 			if(datas!=null && datas.length>0){
 				var titleTr = domConstruct.toDom('<tr></tr>');
 				titleTr.appendChild(domConstruct.toDom('<th></th>'));
 				for(var j=0;j<field.length;j++){
 					titleTr.appendChild(domConstruct.toDom('<th>'+field[j]+'</th>'));
 				}
 				listTable.appendChild(titleTr);
 				
 				for ( var k = 0; k < xdata.length; k++) {
					var dataTr = domConstruct.toDom('<tr></tr>');
					dataTr.appendChild(domConstruct.toDom('<td>'+xdata[k]+'</td>'));
					for ( var m = 0; m < datas.length; m++) {
						var mk = datas[m][k];
						if (typeof mk === "object") {
							if (mk != null) {
								mk = mk.value;
							}
						}
						dataTr.appendChild(domConstruct.toDom('<td>'+mk+'</td>'));
					}
					listTable.appendChild(dataTr);
				}
 			}
 			domStyle.set(listDiv,"display","block");
 			var tbHeight = domStyle.get(listTable,"height");
 			domStyle.set(query('[data-dojo-dom="listview"]',listDiv)[0],"height",(tbHeight+40)+"px");
 			domStyle.set(query('[data-dojo-dom="scrollview"]',listDiv)[0],"height",(360-30)+"px"); //30是“返回”按钮的高度
 		},
 		
		//渲染chart
		renderChart : function(options){
			if(this.content == null || this.drawed) return;
			var _self = this;
			this.drawed = true;
			if(this.chartTheme!=null){
				this.registerTheme(echarts);
			}else{
				this.concatDefaultOptions(options);
			}
			this.result = options;
			if(this.dbcenter && options.toolbox && options.toolbox.feature && !options.toolbox.feature.myDataTableView){
				var iconPath = "/sys/ui/extend/theme/default/images/chart/switch.png";
				this._merge(options,{
							toolbox:{
								right:'25px',
								itemSize:20,
								feature:{
									mark : {show: false},
						            dataZoom : {show: false},
						            dataView : {show: false},
						            magicType: {show: false},
						            restore : {show: true, title:this.getLangProp("lang_restore", "还原")},
						            saveAsImage : {show: false},
									myDataTableView:{
										show:true, 
										title:this.getLangProp("lang_dataView", "数据视图"),
										icon: 'image://' + util.formatUrl(iconPath,true),
						 				onclick:function(){
							             _self.switchChart("1");
						 				}
									}
								}
							}
						});
			}
			this.options = options;
			
			this.echart = echarts.init(this.content,this.chartTheme==null?"landrayblue":this.chartTheme);
			if(options.on){
				for(var o in options.on){
					this.echart.on(o, options.on[o]);
				}
				delete options.on;
			}
			if(options.showMode == 'dataView') {
				this.switchChart("1");
			}
			if(options.KmssData){
				this.echart.KmssData = options.KmssData;
				delete options.KmssData;
			}
			//echarts.init(this.content,'macarons');
			//options.color = null;
			this.echart.setOption(options);
		},
		
		concatDefaultOptions : function(){
			this.inherited(arguments);
		},
		
		registerTheme: function(echarts){
			this.inherited(echarts);
		},
		
		//重新刷新
		refresh : function(){
			this.echart = echarts.init(this.content);
			this.echart.setOption(this.options);
		},
		
		toJSON : function(str){
			return (new Function("return (" + str + ");"))();
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
		},

		getLangProp: function(key,defaultValue){
			if(this.lang_props && this.lang_props[key]){
				return this.lang_props[key];
			}
			return defaultValue;
		}
		
	});
	
});