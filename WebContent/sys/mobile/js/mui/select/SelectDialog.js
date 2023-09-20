define([ "dojo/_base/declare", "dijit/_WidgetBase", "dojo/dom-construct",
		"dojo/dom-style", "dojo/dom-class", "dojo/_base/array",
		"dojo/_base/lang", 'dojo/parser', "mui/iconUtils" ,"dojo/request","mui/util","dojo/on","dojox/mobile/viewRegistry","dojo/query","dojo/topic","dojo/dom-attr","dojo/touch"], function(declare,
		WidgetBase, domConstruct, domStyle, domClass, array, lang, parser,
		iconUtils,request,util,on,viewRegistry,query,topic,domAttr,touch) {

	return  declare('mui.select.SelectDialog', [ WidgetBase], {
		 datas:[],

		showClass : 'ekp-mui-customer-filter-warp',
		//是否首次创建
		firstFlag:true,
		
		paramStr:[],
		
		SELECT_CHANGE:'mui/select/changed',
		
		buildRendering:function(){
			this.inherited(arguments);
			this.initData();
			var showNode = domConstruct.toDom('<div class="muiPicker">筛选<i class="mui mui-sort-filter"></i></div>');
			domConstruct.place(showNode,this.domNode);
			this.connect(showNode, 'click', '_onShowClick');
		},
		
		initData:function(){
			for(var i=0;i<this. datas.length;i++){
				var data=this. datas[i].data;
				if (data) {
					var ida = {};
					 ida['name'] = "全部";
					 ida['value'] = "";
					data.unshift(ida);
				}
			}
		},
		
		initActive:function(){
			query(".ekp-mui-customer-filter-condition li").forEach(function(item){
				domClass.remove(item.children[0],"active");
				if(!domAttr.get(item.children[0],'data-value')){
					domClass.add(item.children[0],"active");
				}
			});
		},
		
		_onShowClick : function() {
			if(this.firstFlag){
				this.buildSelectRender();
				this.initActive();
			}else{
				//显示遮罩层
				domStyle.set( this.maskNode,"display","block");
				//显示面板
				domStyle.set(this.panelNode,"display","block");
			}
		},

		buildSelectRender : function() {
			this.inherited(arguments);
			this.firstFlag=false;
			this.pickerNode = domConstruct.create('div', {
				className :  this.showClass
			}, document.body, 'last');
			//面板
			this.panelNode = domConstruct.create('div', {
				className : 'ekp-mui-customer-filter-panel'
			}, this.pickerNode);
			//遮罩层
			this.maskNode = domConstruct.create('div', {
				className : 'ekp-mui-customer-filter-mask'
			}, this.pickerNode);
			this.connect(this.maskNode, 'click', '_onMaskClick');

			// 标题节点
			var titleNode = domConstruct.create('h4', {
				className :'ekp-mui-customer-filter-title',
				innerHTML : '筛选'
			}, this.panelNode);		
			
			// 内容节点
			this.contentNode = domConstruct.create('div', {
				className : 'ekp-mui-customer-filter-condition'
			},this.panelNode);
			if(this. datas){
				array.forEach(this. datas, lang.hitch(this, function(item) {	
						this. sectionNode= domConstruct.create('section',{
							className : ''
						},  this.contentNode);
						var stl=  domConstruct.create('h5', {
							className :'ekp-mui-gray-title',
                            innerHTML : item.title
						}, this.sectionNode);
						this.buildItemRender(item);
				    }));
			}
			if(this. datas.length>1){
			this.buttonsNode = domConstruct.create('div', {
				className :'ekp-mui-customer-filter-btn'
			}, this.panelNode);
			var resetBtn= domConstruct.create('button', {
				className :'filter-btn reset',
				innerHTML : '重置'
			}, this.buttonsNode);
			this.connect(resetBtn, 'click', '_onResetClick');
			var doneBtn= domConstruct.create('button', {
				className :'filter-btn done',
				innerHTML : '完成'
			}, this.buttonsNode);
			this.connect(doneBtn, 'click', '_onDoneClick');
		    }
		},
		
		buildItemRender:function(item) {
			var ul=  domConstruct.create('ul', {
				id:item.type,
				className :'ekp-mui-customer-filter-list'
			}, this.sectionNode);
			array.forEach(item.data, lang.hitch(this, function(dataItem) {
				   var li=  domConstruct.create('li',{
						className : ''
					}, ul);
				   var spanNode = domConstruct.create('span', {	className :'',innerHTML:dataItem.name},li); 
				   domAttr.set(spanNode,'data-type',item.type);
				   domAttr.set(spanNode,'data-value',dataItem.value);
				   this.connect(spanNode,touch.press,"itemClick");
				}));   	
		},
		
		itemClick:function(evt) {
			var selectedType = domAttr.get(evt.target,'data-type');
			var selectedValue = domAttr.get(evt.target,'data-value');
			query( "#"+selectedType+" li").forEach(function(item){
				domClass.remove(item.children[0],"active");
			});
			for(var i= 0;i<this.datas.length;i++){
				if(selectedType ==this. datas[i].type){
					var data=this.datas[i].data;
					for(var j= 0;j< data.length;j++){
						if(selectedValue==data[j].value)
							this.paramStr[selectedType]=selectedValue;
						    domClass.add(evt.target,"active");
					}
				}
			}
			if(this.datas.length=1){
				this._onDoneClick();
				this._onMaskClick();
			}
		},
		
		_onMaskClick:function() {
			//隐藏遮罩层
			domStyle.set( this.maskNode,"display","none");
			//隐藏面板
			domStyle.set(this.panelNode,"display","none");
		},
		
		_onResetClick:function() {
			this.initActive();
		},
		
       _onDoneClick:function() { 
    	   this.defer(function(){
    		   console.log(this.paramStr);
			topic.publish(this.SELECT_CHANGE,{
				args : this.paramStr
			});
		   topic.publish('/mui/list/toTop' , this);
    	   this._onMaskClick();
    	   },350);
		}
	});
});