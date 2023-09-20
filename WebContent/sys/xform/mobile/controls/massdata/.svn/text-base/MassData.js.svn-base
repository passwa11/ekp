// 大数据呈现
define([ "dojo/_base/declare", "dijit/_WidgetBase", "dojo/dom-construct" ,'dojo/_base/lang',
		"mui/util","sys/xform/mobile/controls/massdata/_ViewTempMixin",
		"sys/xform/mobile/controls/massdata/_RequestDataMixin",
		"sys/xform/mobile/controls/massdata/template/_MassDataToolBarMixin","mui/base64", "dojo/topic","dojo/query","dojo/parser","dojo/dom-style"], 
		function(declare, WidgetBase , domConstruct,lang , util, _ViewTempMixin , _RequestDataMixin, _MassDataToolBarLoader, base64, topic,query,parser,domStyle) {
	var claz = declare("sys.xform.mobile.controls.massdata.MassData", [WidgetBase,_ViewTempMixin, _RequestDataMixin], {

		pageno : "1",
		
		pageSize : "10",
		
		totalSize : "0",
		
		inputParams : null,
		
		outputParams : null,
		
		excelcolumns : null,
		
		columns : {},
		
		pageNum : {},
						
		datas : [],
		
		contentWgt:null,	// _ViewTempMixin定义
		
		pagingWgt:null,	// _ViewTempMixin定义
		
		allColInvalid: true,	// 所有列失效标识
		
		buildRendering : function(){
			this.inherited(arguments);
			this.mainModelName = _xformMainModelClass;
			this.mainFormId = _xformMainModelId;
			this.initColumns();
		},
		
		postCreate : function() {
			this.inherited(arguments);
		},
		
		startup : function(){
			this.inherited(arguments);
			if(typeof this.right !="undefined" && this.right == "edit"){
				var input = domConstruct.create("input",{
					type: "hidden",
					name:this.name
				},this.domNode);
			}
			
		// 翻页功能 evt : {pageno:xx}
			this.subscribe("/sys/xform/massdata/page",lang.hitch(this,function(evt){
				if(typeof this.controlId !="undefined" && evt.controlId == this.controlId){
					if(this.pageNum[this.controlId]){
						this.pageNum[this.controlId] = evt.pageno;
					}
				}else{
					this.pageno = evt.pageno;
				}
				this.reRender();
			}));
		},
		
		// 参考pc端
		initColumns : function(){
			// 当前所有记录
			this.setRecordDatas({});
			if(typeof this.right !="undefined" && this.right == "edit"){
				this.defer(function(){
					//暂时屏蔽excel导入的按钮
					if(source != "EXCEL"){
						var toolbar = query(".massdata-toolbar",this.domNode);
						var self = this;
							toolbar[0].innerHTML = "<div data-dojo-type='mui/tabbar/TabBar'   class='massdata-tabBar'><li data-dojo-type='sys/xform/mobile/controls/massdata/template/_MassDataToolBarMixin' class='mblTabBarButton massdataTarButton' data-dojo-props='controlId:\""+this.controlId+"\"'>获取数据</li></div>";
							parser.parse(toolbar[0]).then(function(){
								var mblTabBarButton = query(".massdata-toolbar .mblTabBarButton",self.domNode);
								var massdataTarButton = query(".massdata-toolbar .massdataTarButton",self.domNode);
								domStyle.set(massdataTarButton[0],"padding","0rem");
								domStyle.set(mblTabBarButton[0],"width","8rem");
							});
					}
					
				},500);
			}
			
			
			var source = this._source;
			if(source == "EXCEL"){
					// 初始化列参数
					this.excelcolumns = this.excelcolumns || "{}";
					this.excelcolumns = $.parseJSON(this.excelcolumns.replace(/quot;/g,"\""));
					// 格式化列定义，返回以字段名为key的json：{docSubject:{fieldTitle:xxx}}，方便数据通过key定位
					// 格式化列定义，返回以字段名为key的json：{docSubject:{title:xxx}}，方便数据通过key定位
					if(typeof this.controlId !="undefined"){
						if(!this.columns[this.controlId]){
							this.columns[this.controlId] = {};
						}
						this.columns[this.controlId] = $.extend(this.columns[this.controlId],this.excelcolumns);
					}else{
						this.columns = $.extend(this.columns,this.excelcolumns);
					}
					
				
			}else{
				if(typeof this.controlId !="undefined" && !this.pageNum[this.controlId]){
					this.pageNum[this.controlId] = this.pageno;
				}
				// 初始化传入参数
				this.inputParams = this.inputParams || "{}";
				this.inputParams = $.parseJSON(this.inputParams.replace(/quot;/g,"\""));
				// 初始化传出参数
				this.outputParams = this.outputParams || "{}";
				this.outputParams = $.parseJSON(this.outputParams.replace(/quot;/g,"\""));
				// 格式化列定义，返回以字段名为key的json：{docSubject:{title:xxx}}，方便数据通过key定位
				if(typeof this.controlId !="undefined"){
					if(!this.columns[this.controlId]){
						this.columns[this.controlId] = {};
					}
					for(var key in this.outputParams){
						var param = this.outputParams[key];
						this.columns[this.controlId][base64.decode(param["fieldId"])] = {title:param["fieldTitle"]};
					}
				}else{
					for(var key in this.outputParams){
						var param = this.outputParams[key];
						this.columns[base64.decode(param["fieldId"])] = {title:param["fieldTitle"]};
					}
				}
				
			}
		},
		
		onDataLoad : function(rs){
			this.setDatas(rs);
			this.allColInvalid = true;
			this.setRecordDatas(rs);
			this.reRender();
		},
		
		reRender : function(){
			// 内容区渲染
			var evt = {};
			if(typeof this.controlId !="undefined"){
				if(!this.pageNum[this.controlId]){
					this.pageNum[this.controlId] = this.pageno;
				}
				evt.pageno = this.pageNum[this.controlId];
				evt.columns = this.columns[this.controlId];
			}else{
				evt.pageno = this.pageno;
				evt.columns = this.columns;
			}
			
			evt.pageSize = this.pageSize;
			evt.totalSize = this.totalSize;
			evt.controlId = this.controlId;
			var datas = this.divideDatasByPage();
			// 跟pc端逻辑不一样，此处先查询有效数据
			datas = this.getValidDatas(datas,evt.columns);
			evt.datas = datas;
			this.contentWgt.render(evt);
			if(datas.length > 0){
				this.pagingWgt.render(evt);				
			}
			this.contentWgt.resize();
			topic.publish("/mui/list/resize");
		},
		
		setDatas : function(rs){
			rs = rs || {};
			if(rs.hasOwnProperty("records")){
				this.datas = rs["records"];
				this.totalSize = this.datas.length;
			}else{
				this.datas = [];
				this.totalSize = "0";
			}
		},
		
		// 分页，分割数据
		divideDatasByPage : function(){
			var rs = [];
			var datas = this.datas;
			var startIndex = parseInt((this.pageno - 1)*this.pageSize);
			var endIndex = parseInt(this.pageno*this.pageSize);
			if(typeof this.controlId !="undefined"){
				if(!this.pageNum[this.controlId]){
					this.pageNum[this.controlId] = this.pageno;	
				}
				startIndex = parseInt((this.pageNum[this.controlId] - 1)*this.pageSize);
				endIndex = parseInt(this.pageNum[this.controlId]*this.pageSize);
			}
			if(datas.length >= startIndex && datas.length < endIndex){
				rs = datas.slice(startIndex);
			}else if(datas.length < startIndex){
				
			}else if(datas.length >= endIndex){
				rs = datas.slice(startIndex,endIndex);
			}
			return rs;
		},
		
		// [{xx:{value:xx}}{}]
		getValidDatas : function(datas,columns){
			var rsDatas = [];
			for(var i = 0;i < datas.length;i++){
				var data = datas[i];
				var rsData = {};
				// 根据列定义来判断当前返回数据哪些是有效数据
				for(var key in columns){
					if(data.hasOwnProperty(key)){
						rsData[key] = data[key];
					}
				}
				if(JSON.stringify(rsData) != "{}"){
					rsDatas.push(rsData);
				}
			}
			return rsDatas;
		},
		setRecordDatas : function(data){
			if(this.pageNum[this.controlId]){
				this.pageNum[this.controlId] = "1";
			}
			data = data || {};
			// 把加载的数据存储到input表单元素里面，提交时拦截机制把该字段进行相关保存处理
			$("input[name='"+ this.name +"']").val(JSON.stringify(data));
		}
		 
	});
	return claz;
});