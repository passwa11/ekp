(function(){
	
	function Chart(evt){
		
		this.domNode;
		this.tipNode; // 提示元素
		this.url = Com_Parameter.ContextPath + "dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=previewChartData"; // 请求的url
		this.config = {}; // 页面上所有的配置项
		this.echart; // echart组件
		this.chartStyle;// 样式组件
		this.chartOption = {}; //echart 的配置信息
		this.theme = "";
		this.customFeatrue = {};
		
		this.cur_chartType="bar";
		this.reverse=false;
		
		this.init = _Chart_Init;
		this.newEchart = Chart_NewEchart;
		this.load = Chart_Load; // 数据加载
		this.dataOnLoad = _Chart_DataOnload;// 数据加载完毕
		this.getRandomData = _Chart_GetRandomData; //随机数据
		this.mergePersonality = Chart_mergePersonality; // 合并两个对象
		this.getChart = Chart_GetChart;
		this.changeTheme = Chart_ChangeTheme;
		this.getTipNode = Chart_GetTipNode;
		this.updateTip = Chart_UpdateTip; // 更新提示
		this.isReverse = Chart_reverse;
		
		this.init(evt);
	}
	
	function Chart_reverse(type){
		console.log(this);
		console.log("判断"+type+" self.cur_chartType:"+this.cur_chartType);
		if(type == 'columnBar-standard' || type == 'columnBar-stacking'){
			if(this.cur_chartType == 'row'){
				this.cur_chartType='bar';
				this.reverse=true;
			}else{
				this.reverse=false;
			}
		}else{
			if(this.cur_chartType == 'bar'){
				this.cur_chartType='row';
				this.reverse=true;
			}else{
				this.reverse=false;
			}
		}		
	}
	
	function _Chart_Init(evt){
		//初始化echart
		var self = this;
		self.domNode = evt.domNode;
		self.loading = $("<img src='"+ Com_Parameter.ContextPath +"sys/ui/js/ajax.gif' />");
	}
	
	function Chart_NewEchart(theme){
		var self = this;
		if(self.echart){
			self.echart.clear();
			self.echart.dispose();
		}
		theme = theme ? theme : "";
		self.theme = theme;
		self.echart = echarts.init(self.domNode,theme);
		self.echart.resize();
	}
	
	/**
	* 加载EChart数据所需数据并渲染图表
	* @param configType   配置方式（01：系统数据配置、10：静态数据配置）
	* @param config       配置信息对象
	* @param isSend       是否发送ajax请求按照系统数据配置的内容从后台读取数据来渲染EChart
	* @param isRandom     是否使用随机数据
	* @return
	*/
	function Chart_Load(configType, config, isSend, isRandom){
		console.log("init: isSend"+isSend);
		console.log("init: isRandom"+isRandom);
		if(isRandom == null){
			isRandom = false;
		}
		if(isSend == null){
			isSend = false;
		}
		var self = this;
		var option = {};
		$(self.domNode).append(self.loading);
		if(config){
			self.config = config;
		}
		if(isRandom){
			// 首次进来，伪造随机数据
			option = self.getRandomData();
		}
		if($.isEmptyObject(option)){
			// 初始化
			option = self.chartOption;
		}
		if(!isSend){
			var personality = $.parseJSON(config.chartOption.personality.replace(/&quot;/g, "\""));
			delete config.chartOption.personality;
			self.mergePersonality(option,config.chartOption);
			self.mergePersonality(option,personality);
			self.isReverse(option.chartType);
		}else{
			var type = option.chartType;
			if(type==""||type== 'undefined'){
				self.cur_chartType="bar";
			}else{
				if(type == 'columnBar-standard' || type == 'columnBar-stacking'){
					self.cur_chartType="bar";
				}else{
					self.cur_chartType="row";
				}
			}
			this.reverse=false;
			var postData = Chart_PostData(self.url, configType, self.config);
			if(postData.status == '01'){
				option = postData.data;			
			}else if(postData.status == '00'){
				// 后台报错，直接弹窗提示，图表不做变更
				seajs.use(['lui/dialog'],function(dialog){
					dialog.alert("<div style='max-height:300px;overflow-y:scroll'>" + DbcenterLang.chartPreviewError + ":" + postData.err + "</div>");
				});
				return;
			}
		}
		
		// 自定义工具栏函数
		if(option.toolbox && option.toolbox.feature){
			for(var key in self.customFeatrue){
				option.toolbox.feature[key] = self.customFeatrue[key]; 
			}
		}
		// 当没有xAxis数据时，yAxis也设置为空，不然echart会报错
		if(typeof(option.xAxis) == 'undefined'){
			option.xAxis = [];
			option.yAxis = [];
		}
		
		self.updateTip(option);
		console.log("isSend:"+isSend);	
		console.log("this.reverse:"+this.reverse);
		console.log("this.cur_chartType:"+this.cur_chartType);
		if(!isSend && this.reverse){
			var x_temp = option.xAxis;
			option.xAxis=option.yAxis;
			option.yAxis=x_temp;
		}
		console.log(option);
		self.chartOption = option; // 保存option，用于实例销毁后还原
		self.echart.setOption(option,true);
		self.dataOnLoad();
	}
	
	/**
	* 更新tip提醒
	* @return
	*/	
	function Chart_UpdateTip(option){
		var self = this;
		var hasRecord = true;
		var msg = DbcenterLang.queryRsNull;
		if(typeof(option.series) == 'undefined' || option.series.length == 0){
			hasRecord = false;
		}
		if(hasRecord){
			self.getTipNode().hide();
		}else{
			self.getTipNode().html(msg).show();
		}
	}
	
	
	/**
	* 获取tip提醒的DIV jQuery DOM对象
	* @return 返回 jQuery DOM
	*/
	function Chart_GetTipNode(){
		var self = this;
		if(!self.tipNode){
			var tipNode = $("<div class='preview-log'></div>");
			$(self.domNode).after(tipNode);
			self.tipNode = tipNode;
		}
		return self.tipNode;
	}
	
	
	/**
	* 修改echart主题
	* @param theme  主题名称
	* @return
	*/
	function Chart_ChangeTheme(theme){
		var self = this;
		// 原生的echart不支持动态切换主题，必须先销毁，再初始化
		self.newEchart(theme);
		self.echart.setOption(self.chartOption);
	}
	
	

	/**
	* ajax发送同步请求至后台获取渲染echart所需的option配置
	* @param url          请求后台的URL
	* @param configType   配置方式（01：系统数据配置、10：静态数据配置）
	* @param config       配置信息对象
	* @return
	*/
	function Chart_PostData( url, configType, config ){
		 var rs = {'status':'01','data':{},'err':''}; // 00:'错误'，01:'成功'
		 var requestUrl = url+"&configType="+configType;
		 $.ajax({
		        url:requestUrl,
		        type:"post",
		        dataType : 'text',
		        data:JSON.stringify(config),
		        cache : false,
		        async : false, // 同步
		        contentType: "application/json",
		        success:function(data){
		            if(data){
		            	rs.status = '01';
		            	rs.data = eval(data);
		            }
		        },
		        error: function(err) {
                	rs.status = '00';
	            	rs.err = err.responseText;
                }
		    });
		return rs;
	}
	
	function _Chart_DataOnload(){
		if(this.loading){
			this.loading.remove();
		}
	}
	
	function Chart_GetChart(){
		return this.echart;
	}
	
	/**
	* 获取用于渲染EChart图表的option系统随机数据(此函数暂仅提供固定静态option)
	* @return 返回渲染EChart的option配置
	*/	
	function _Chart_GetRandomData(){
		var option = {
				title : {
					text : DbcenterLang.title,
					subtext : DbcenterLang.subTitle
				},
				legend : {
					data : [DbcenterLang.visitNum]
				},
				toolbox:{
					feature : {
						saveAsImage : {
							show : true
						},
						restore : {
							show : true
						}
					}
				},
			    xAxis: [{
			        type: 'category',
			        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
			    }],
			    yAxis: {
			        type: 'value'
			    },
			    series: [{
			    	name : DbcenterLang.visitNum,
			        data: [{name:"Mon",value:82},{name:"Tue",value:132},{name:"Wed",value:93},
			               {name:"Thu",value:90},{name:"Fri",value:93},{name:"Sat",value:129} , {name:"Sun",value:133} ],
			        type: 'line'
			    }]
			};
		return option;	
	}
	
	/**
	* 合并两个JSON对象（普通属性复制替换，属性类型为数组时直接覆盖）
	* @param destination    目标JSON对象
	* @param source         源目标JSON对象
	* @return 返回合并后的JSON对象
	*/
	function Chart_mergePersonality(destination,source){
		// Array.isArray函数IE8不支持，下面这段逻辑用于兼容IE8
		if (!Array.isArray) {
			  Array.isArray = function(arg) {
			    return Object.prototype.toString.call(arg) === '[object Array]';
			  };
		}
		if(Array.isArray(source)){
			destination = source;
		}else{
			var propNameArray = Chart_getMergePropNameArray(source);
			for (var index in propNameArray){ 
				var property = propNameArray[index];
				if(typeof source[property]==="object"){
					if (property.indexOf("[]") > -1) {
						var sourcePro = source[property];
						property = property.replace("\[\]", "");
						if(!destination[property]){
							continue;
						}
						var destPro = destination[property];
						for(var i = 0;i < destPro.length;i++){
							this.mergePersonality(destPro[i],sourcePro);
						}
					}else{
						if(source[property] == null){
							destination[property] = null;
						}else{
							if(!destination[property]){
								if(Array.isArray(source[property])){
									destination[property]=[];
								}else{
									destination[property]={};
								}
							}
							destination[property]=this.mergePersonality(destination[property],source[property]);	
						}
					}
				}else{
					destination[property] = source[property];
				}
			}
		}
		return destination;
	}
	
	
	/**
	* 获取需要合并的属性名称数组(此函数的意义是对象的属性名称进行一个简单排序，属性名称中带有[]特殊标识的排在数组后面，避免在调用合并函数时被相同名称的普通属性提前覆盖)
	* @param source         源目标JSON对象
	* @return 返回排序后的属性名称数组
	*/
	function Chart_getMergePropNameArray(source){
		var propNameArray = []; 
		var specialPropNameArray = [];
		for (var propName in source){
			if(propName.indexOf("[]") > -1){
			    specialPropNameArray.push(propName);
			}else{
				propNameArray.push(propName);
			}
		}
		propNameArray = propNameArray.concat(specialPropNameArray);
		return propNameArray;
	}
	
	window.Chart = Chart;
	
})();