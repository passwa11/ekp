define(function(require, exports, module) { 
	    require("theme!chart");
		var base = require('lui/base');
		var Class = require("lui/Class");
		var $ = require("lui/jquery");
		var Evented = require('lui/Evented');
		var env = require('lui/util/env');
		var topic = require('lui/topic');
		var strutil = require('lui/util/str');
		var source = require("lui/data/source");
		var lang = require('lang!sys-ui');
		
		//chart绘制完成事件
		 var EVENT_CHART_LOADED = "chart.loaded";
		
		var Chart = base.Component.extend({
				initProps: function($super, cfg) {
					$super(cfg);
					this.loading = $("<img src='" + env.fn.formatUrl('/sys/ui/js/ajax.gif') + "' />");
					this.noRecordStyle = "normal";
					this.tdWidth = 0;
					if(cfg.vars) {
						this.themeName = cfg.vars.themeName;
						this.noRecordStyle = cfg.vars.noRecordStyle || 'normal';
						this.tdWidth = cfg.vars.tdWidth || 0;
					}
				},
				textV:function(text){
		 			var txtArr = text.split(" ");
		 			var tmpArr =[];
		 			for ( var i = 0; i < txtArr.length; i++) {
						if(txtArr[i]!=""){
							if(i!=0){
								tmpArr.push("\n");
							}
							var oldChar = "";
							for(var j=0;j<txtArr[i].length;j++){
				 				var tmpChar = txtArr[i].charAt(j);
				 				if((/[^\x00-\xff]/g.test(oldChar) && oldChar!= "")//上一个字符是中文的情况
				 						||(/[^\x00-\xff]/g.test(tmpChar) && oldChar!= "" && !/[^\x00-\xff]/g.test(oldChar))){//本字符是中文并且上一字符非中文
			 						tmpArr.push("\n");
			 					}
			 					tmpArr.push(tmpChar);
				 				oldChar = tmpChar;
				 			}
						}else continue;
					}
		 			return tmpArr.join("");
		 		},
		 		load: function() {
		 			var self = this;
					if(self.chartdata){
						$("#"+self.id+" .div_chart").append(self.loading);
						if(self.themeName && self.themeName!="default"){
							// 如果有设置EChart图表主题，则将主题JS文件加载完成之后再渲染图表
							require.async("lui/chart/theme/"+self.themeName,function(){ 
								self.chartdata.get();
							});
						}else{
							// 如果未设置EChart图表主题，则使用vintage主题渲染图表，否则echarts5.3.2版本默认主题会造成Y轴缺失
							self.themeName='infographic';
							require.async("lui/chart/theme/"+self.themeName,function(){
								self.chartdata.get();
							});
						}
					}
				},
				startup: function() {
					if (this.isStartup) {
						return;
					}
					var _self = this;
					if (this.chartdata) {
						this.chartdata.on('error', this.onError, this);
						this.chartdata.on('data', this.onDataLoad, this);
					}
					this.isStartup = true;
				},
				switchChart:function(isTable){
		 			if(isTable=="1"){//当前状态显示图标
		 				$("#"+this.id+" .div_chart").hide();
		 				//$("#"+this.id+" .div_chart").attr("title","test3");  //test
		 				this.showList($("#"+this.id+" .div_listSection"));
		 			}else{
		 				$("#"+this.id+" .div_listSection").hide();
		 				$("#"+this.id+" .div_chart").show();
		 			}
		 		},
		 		
		 		prepareData:function(){
		 			
		 		},
		 		showList:function(listDiv){
		 			var _self = this; 
		 			var xdata=[];
		 			var field = [];
		 			var datas=[];
		 			var series = _self.result.series;
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
		 				if(_self.result.reverseXY == 'true') {
		 					xdata = _self.result.yAxis[0].data;
		 				} else {
		 					xdata = _self.result.xAxis[0].data;
		 				}
		 				for(var i=0;i<series.length;i++){
			 				var sData = series[i];
			 				field.push(sData.name);
			 				datas.push(sData.data);
			 			}
		 			}
		 			
		 			listDiv.html('');
		 			var reButton = "<div style=\"text-align:left;width:96%;\" class='div_close com_btn_link'>" + lang['echart.comeBack'] + "</div>";
		 			listDiv.append($(reButton).click(function(){
		 				_self.switchChart("0");
		 			}));
		 			if(datas!=null && datas.length>0){
		 				var content = $('<table class="tab_listData"></table>');
		 				var titleTr = $('<tr class="tab_title"></tr>');
		 				var xaxis = "";
		 				// 添加x轴标题
		 				if(_self.result.xAxis){
		 					if(_self.result.xAxis.constructor === Array && _self.result.xAxis.length > 0){
		 						xaxis = _self.result.xAxis[0].name;
		 					}else{
		 						if(_self.result.xAxis.name){
		 							xaxis = _self.result.xAxis.name;
		 						}
		 					}
		 				}else if(_self.result.xAxisName){
		 					xaxis = _self.result.xAxisName;
		 				}
		 				if(xaxis == null) {
		 					$('<th></th>').appendTo(titleTr);
		 				} else {
		 					$('<th>'+xaxis+'</th>').appendTo(titleTr);
		 				}
		 				for(var j=0;j<field.length;j++){
		 					$('<th>'+field[j]+'</th>').appendTo(titleTr);
		 				}
		 				titleTr.appendTo(content);
		 				
		 				for ( var k = 0; k < xdata.length; k++) {
							var dataTr = $('<tr class="tab_data"></tr>');
							$('<td>'+xdata[k]+'</td>').appendTo(dataTr);
							for ( var m = 0; m < datas.length; m++) {
								var mk = datas[m][k];
								if(typeof mk === "object" && mk != null && mk.value){
									mk = mk.value;
								}
								$('<td>'+ mk +'</td>').appendTo(dataTr);
							}
							dataTr.appendTo(content);
						}
		 			}
			 			listDiv.append(content);

			 			listDiv.show();

		 		},
		 		isNoRecord: function(rtn) {
		 			var result = true;
		 			if(rtn && rtn.series && rtn.series.length > 0) {
		 				var series = rtn.series;
		 				for (var i = 0; i < series.length; i++) {
							if(series[i].data && series[i].data.length > 0) {
								result = false;
								break;
							}
						}
		 			}
		 			return result;
		 		},
		 		doNoRecord: function(rtn) {
		 			var self = this;
		 			var url;
		 			if(this.noRecordStyle == 'normal') {
		 				//移除图表原来的内容
		 				$("#"+this.id+" .div_listSection").empty();
		 				url = '/resource/jsp/list_norecord.jsp?_='+new Date().getTime();
		 			} else {
		 				url = '/resource/jsp/list_norecord_simple.jsp?_='+new Date().getTime();
		 			}
		 			$.ajax({
						url : env.fn.formatUrl(url),
						dataType : 'text',
						success : function(data, textStatus) {
							//移除初始化标记
							$("#"+self.id+" .div_chart").removeAttr('_echarts_instance_').html(data);
							$("#"+self.id+" .div_listSection").removeAttr('_echarts_instance_').html(data);
							//$("#"+self.id+" .div_chart").attr('title','test');  //test
							
							//无数据时，清除echart缓存数据
							if(self.echart){
								self.echart.clear();	
								self.echart = null;
							}
							// 补充加载完毕事件
							self.fire({
								"name": "load"
							});
							topic.publish(EVENT_CHART_LOADED,{chart:self.echart,contextData:rtn});
						}
					});
		 		},
				onDataLoad: function(rtn) {
					if(this.loading){
						this.loading.remove();
					}
					var chartDom = $("#"+this.id+" .div_chart");
					//#163414 图表集初始化时，宽度被压缩，重新设置为图表集整体宽度
					if(chartDom.width()<this.tdWidth){
						chartDom.width(this.tdWidth);
					}
		 			var _self = this;
					if(rtn.chart){
						if(rtn.chart.width){
							chartDom.width(rtn.chart.width);
						}
						if(rtn.chart.height){
							chartDom.height(rtn.chart.height);
						}
						delete rtn.chart;
					}
					//没有记录 显示
					if(this.isNoRecord(rtn)) {
						this.doNoRecord(rtn);
						return;
					}
		 			this.result = rtn;
		 			
					if(rtn.title!=null&&rtn.title.text!=null){
						//如果标题摆放的位置为左居中或右居中，则把标题竖着摆放
						if(rtn.title.y == 'center'&&(rtn.title.x == 'left'||rtn.title.x == 'right')){
							rtn.title.text = _self.textV(rtn.title.text);
							if(rtn.title.subtext!=null){
								rtn.title.subtext = _self.textV(rtn.title.subtext);
					      }
					   }
					}
					if(rtn.toolbox&&rtn.toolbox.feature&&!rtn.toolbox.feature.myDataTableView){
						this.mergeCustomOption({
							toolbox:{
								right: 20,
								feature:{
									myDataTableView:{
										show:true, 
										title: lang['echart.dataView'],
										icon: 'image://' + Com_GetCurDnsHost() + Com_Parameter.ContextPath+"sys/ui/extend/theme/default/images/chart/switch.png",
						 				onclick:function(){
							             _self.switchChart("1");
						 				}
									}
								}
							}
						});
						
					}
					
					
					//浮层国际化
					this.mergeCustomOption({
						toolbox:{
							feature:{
								saveAsImage:{
									show:true, 
									title : lang['echart.saveAsPicture']
								}
							}
						}
					});

					//长名称换行（名称过长时，按每行显示的字符数换行）
					if(this.config.lineSize) {
						var self = this;
						var lineSize = parseInt(this.config.lineSize);
						var series = this.result.series;
						if(series.length == 1 && (series[0].type == 'pie' || series[0].type == 'gauge')) {
							this.result.series[0]['itemStyle'] = {
								"normal": {
									"label": {
										formatter: function(v) {
											return self.splitStr(v.name, lineSize);
										}
									}
								}
							};
						}
					}

					if(rtn.dataZoom) {
						this.mergeCustomOption({
							toolbox:{
								feature:{
									dataZoom:{
										title :{
											zoom : lang['echart.areaScaling'],
											back : lang['echart.areaScaling.restore']
										}
									}
								}
							}
						})
					}
					
					this.mergeCustomOption({
						toolbox:{
							feature:{
								magicType:{
									title: {
										line : lang['echart.switch.polyline'],
										bar : lang['echart.switch.histogram']
									}
								}
							}
						}
					})
					
					this.mergeCustomOption({
						toolbox:{
							feature:{
								restore:{
									title: lang['echart.restore']
								}
							}
						}
					})
					
					if(this.customOption){
						this._merge(rtn, this.customOption);
					}
					
					// 自适应宽度
					_self.adaptWidth(_self.element,rtn.isAdapterSize);
					//_self.adaptWidth(chartDom,rtn.isAdapterSize);	
					//清除echart图表为空的时候的内容（无数据进行有数据切换时）
					if(!this.echart){
						chartDom.empty();					
					}
					this.echart = echarts.init(chartDom[0],this.themeName);
					
					topic.subscribe('navExpand', function() {
						_self.echart.resize();
					});
					
					if(rtn.on){
						for(var o in rtn.on){
							this.echart.on(o, rtn.on[o]);
						}
						delete rtn.on;
					}
					if(rtn.KmssData){
						this.echart.KmssData = rtn.KmssData;
						delete rtn.KmssData;
					}
					if(rtn.showMode == 'dataView') {
						this.switchChart("1");
					}
					this.echart.setOption(rtn,true);
					this.fire({
						"name": "load"
					});
					topic.publish(EVENT_CHART_LOADED,{chart:this.echart,contextData:rtn});
				},
				splitStr: function(text, len) {
					if(text) {
						var index = 0;
						var value = "";
						for(var i=0; i<text.length; i++) {
							var c = text[i];
							if(escape(c).indexOf("%u") > -1) {
								// 中文
								index += 2;
							} else {
								// 非中文
								index += 1;
							}
							value = value.concat(c);
							if(index >= len) {
								value = value.concat("\n");
								index = 0;
							}
						}
						return value;
					}
					return "";
				},
				// 自适应宽度，当设置的宽度大于窗口宽度时，取窗口的宽度
				adaptWidth : function($domNode,isAdapterSize){
					if((typeof isAdapterSize) == "boolean"){
						if(isAdapterSize){
							isAdapterSize = "true";
						}else{
							isAdapterSize = "false";
						}
					}
					//var chartWidth = $domNode.width();
					var chartWidth = $domNode.find(".div_chart").width();
					var winWidth = $(window).width();
					//#156711 当winWidth>0时，才将值赋给chartWith,以防winWidth==0时，图表数据显示空白
					if(isAdapterSize=="true" && chartWidth>winWidth && winWidth>0){
						chartWidth = winWidth;				
					}
					// 获取图表指定的外层容器DIV jQuery对象（该DIV样式设置了border，因此图表实际渲染宽度应减去左右border值，避免边框显示不全）
					var $mainChartContainer = $domNode.closest(".mainChartContainer"); 
					if($mainChartContainer.length>0){
						var chartContainerWidth = $mainChartContainer.width();
						if(chartContainerWidth > winWidth){
						   $mainChartContainer.width(winWidth-2);	
						}
						chartWidth = chartWidth - 2;	
					}
					$domNode.css('min-width','');
					$domNode.width(chartWidth);
					//#88546 chartDom的宽度如果跟外层容器不一致，ie8显示会异常
					var chartDom = $("#"+this.id+" .div_chart");
					chartDom.css('min-width','');
					chartDom.width(chartWidth);
				},
				getEchart: function(){
					return this.echart;
				},
				onError: function(msg) {
					this.doRender(msg);
				},
				addChild: function(child) {
					if(child instanceof source.BaseSource){
					   this.chartdata = child;
					}
				},
				doRender: function(html) {
					this.loading.remove();
					if (html) {
						this.element.html("");
						this.element.append(html);
					}
					this.isLoad = true;
					this.fire({
						"name": "load"
					});
				},
				erase: function($super) {
					this.element.html("");
					$super();
				},
				draw: function() {
					if (this.isDrawed)
						return;
					this.element.show();
					this.load();
				},
				
				replaceDataSource:function(sourceCfg){
					if(sourceCfg!=null){
						this.chartdata = new source[sourceCfg.type](sourceCfg);
						this.chartdata.on('error', this.onError, this);
						this.chartdata.on('data', this.onDataLoad, this);
						this.load();
					}
				},
				mergeCustomOption: function(option,isLoad){
					if(!this.customOption){
						this.customOption=option;
					}else{
						this._merge(this.customOption,option);
					}
					//this.customOption=option;
					if(isLoad){
						this.load();
					}
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

		exports.Chart = Chart;
});