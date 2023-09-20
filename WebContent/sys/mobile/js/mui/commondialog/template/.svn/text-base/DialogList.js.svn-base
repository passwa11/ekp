define( [ "dojo/_base/declare", "dojo/topic", "dojo/dom-construct", "dojo/_base/array","dojo/_base/lang", 
          "mui/util", "mui/list/JsonStoreList"], function(declare, topic, domConstruct, array, lang,
        			util, JsonStoreList) {
	return declare("mui.commondialog.template.DialogList", [ JsonStoreList ], {
		
		modelName : null,
		
		//数据请求URL
		dataUrl : '',
		
		//当前值初始
		curIds : null,
		
		//当前值初始
		curNames : null,
		
		//单选|多选
		isMul : false,
		
		baseClass : "muiCateLists",
		
		//对外事件对应的唯一标示
		key : null,
		
		displayProp:"",
		
		rowSize:200,
		
		buildRendering : function() {
			this.dataUrl = util.setUrlParameter(this.dataUrl,"rowsize",this.rowSize);
			this.url = util.urlResolver(this.dataUrl,this);
			this.inherited(arguments);
			this.domNode.className = this.baseClass;
			this.buildLoading();
		},	
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe("/mui/category/selected","_cateSelected");
			this.subscribe("/mui/search/submit","dataFresh");
			this.subscribe("/mui/search/cancel","dataFresh");
		},
		startup : function() {
			this.inherited(arguments);
			this.reload();
		},
		
		dataFresh:function(srcObj,evt){
			if(srcObj.key==this.key){
				if(evt && evt.url){
					if(!this._oldUrl){
						this._oldUrl = this.url;
					}
					var url = util.setUrlParameter(evt.url,"q._keyword",evt.keyword);
					url = util.setUrlParameter(url,"rowsize",this.rowSize);
					
					// 支持自定义参数
					for (var k in evt) {
					  if (k === 'url' || k === 'keyword') {
					    continue
					  }
					  evt[k] !== undefined && (url = util.setUrlParameter(url, k, evt[k]))
					}
					
					this.url = url;
				}else{
					if(this._oldUrl)
						this.url = util.urlResolver(this._oldUrl,this);
				}
				this.buildLoading();
				this.reload();
			}
		},
		
		buildLoading:function(){
			if(this.tmpLoading == null){
				domConstruct.empty(this.domNode);
				this.tmpLoading = domConstruct.create("li",{className:'muiCateLoading',
					innerHTML:'<i class="mui mui-loading mui-spin"></i>'},this.domNode);
			}
		},
		
		resolveItems:function(items){
			var props = {};
			if(items.columns){
				var columns = items.columns;
				for(var i=0;i<columns.length;i++){
					if(columns[i].title){
						props[columns[i].property]=columns[i].title;
					}
				}
			}
			this.props = props;
			return this.inherited(arguments);
		},
		
		_createItemProperties: function(item){
			var rtnObj = this.inherited(arguments);
			var cloneItem = JSON.parse(JSON.stringify(item));
			rtnObj.label = cloneItem[this.displayProp];
			cloneItem.label = rtnObj.label;
			rtnObj.data =  cloneItem;
			rtnObj.displayProp =  this.displayProp;
			rtnObj.props =  this.props;
			return rtnObj;
		},
		
		onComplete:function(){
			if(this.tmpLoading){
				domConstruct.destroy(this.tmpLoading);
				this.tmpLoading = null;
			}
			this.inherited(arguments);
		},
		
		_cateSelected:function(srcObj,evt){
			if(srcObj.key==this.key){
				if(!this.isMul){
					this.curIds = evt.fdId;
					if(evt.label){
						this.curNames = evt.label;
					}else{
						this.curNames = evt[this.displayProp];
					}
					array.forEach(this.getChildren(),lang.hitch(this,function(item){
						if(item.header!='true' && item.fdId != evt.fdId){
							topic.publish("/mui/category/cancelSelected",this,{fdId:item.fdId});
						}
					}));
					topic.publish("/mui/category/submit", this, {
						key: this.key,
						curIds:this.curIds,
						curNames:this.curNames,
						data:[evt]
					});	
				}
			}
		}
	});
});