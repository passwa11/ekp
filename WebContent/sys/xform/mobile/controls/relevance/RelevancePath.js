define( [ "dojo/_base/declare", "mui/category/CategoryPath","dojo/dom-construct","dojo/topic",
          "dojo/dom-style" , "dojo/request", "dojo/_base/array", "mui/util","dojox/mobile/_ScrollableMixin","mui/i18n/i18n!sys-xform-base:mui",
		  "dojo/parser"],
	function(declare,CategoryPath,domConstruct,topic,domStyle,request,array,util,ScrollableMixin,Msg,parser) {
		var path = declare("sys.xform.mobile.controls.relevance.RelevancePath", [ CategoryPath,ScrollableMixin], {
				
				scrollDir : "h",
			
				modelName: null ,

				isCategory : true,

				//获取详细信息地址
				detailUrl : '/sys/xform/controls/relevance.do?method=pathList&cateId=!{curId}&fdKey=!{fdKey}',

				_initPath: function(){
					if (this.domNode.parentNode) {
						var h
						if (this.height === "inherit") {
							if (this.domNode.parentNode) {
								h = this.domNode.parentNode.offsetHeight + "px"
							}
						} else if (this.height) {
							h = this.height
						}
						domStyle.set(this.domNode, {height: h, "line-height": h})
					}
					this._chgHeaderInfo(this, {fdId: this.curId});
					topic.publish("/mui/category/changed", this, {
						fdId: this.curId,
						isBase:this.isBase,
						fdKey:this.fdKey,
						fdTemplateModelName:this.fdTemplateModelName,
						isSimpleCategory:this.isSimpleCategory,
						modelName: this.modelName
					});
				},

				buildRendering : function() {
					this.detailUrl += '&fdControlId='+this.fdControlId+'&extendXmlPath='+this.extendXmlPath;
					this.inherited(arguments);
				},
				
				postCreate : function() {
					this.inherited(arguments);
					this.subscribe("/sys/xform/relevance/category/changed","_chgHeaderInfo");
					//调整宽度
					domStyle.set(this.domNode,{width:'max-content'});
					domStyle.set(this.domNode,{minWidth:'100%'});
				},
				
				_createPathItem:function(item,label) {
					var itemTag = domConstruct.create('div', {
						value:item.fdId
					},this.containerNode);
					var textNode = domConstruct.create('div', {
						className : ''
					},itemTag);
					if (label) {													
						var labelNode = domConstruct.create('span', {
							className :''
						}, textNode);
						labelNode.innerHTML = label;
					}
					
					this.connect(itemTag, "onclick", function(){
						window._curIds = "-";//点击路径时，清除选中的分类或模板
						if(item.fdId != this.curId){
							this._chgHeaderInfo(this,{
								'fdId':item.fdId,
								'fdKey':item.fdKey
							});
							topic.publish("/mui/category/changed",this,{
								'fdId':item.fdId,
								'label':item.label,
								'fdTemplateId':item.fdTemplateId,
								'modelPath':item.modelPath,
								'fdKey':item.fdKey
							});
							
						}
					});
				},
				 _createTitleNode: function() {
					 var itemTag = domConstruct.create('div', {
						},this.containerNode);
					 var textNode = domConstruct.create('div', {
						 className : ''
					 },itemTag);
					 var labelNode = domConstruct.create('span', {
						 className :''
					 }, textNode);
					 labelNode.innerHTML = Msg["mui.relevance.path.all"];
					
					 this.connect(itemTag, "onclick", function(){
						 this._chgHeaderInfo(this,{});
						 topic.publish("/mui/category/changed",this,{});
					 });
                 },
				_chgHeaderInfo : function(srcObj,evt){
					if(srcObj.key==this.key){
						if(evt){
							if(evt.fdId){
								domStyle.set(this.domNode,{display:'block'});
								if(this.curId != evt.fdId || this.containerNode.innerHTML == Msg["mui.relevance.path.all"]){
									this.curId = evt.fdId;
									this.fdKey = evt.fdKey;
									var _url = util.urlResolver(this.detailUrl,this);
									_url = util.formatUrl(_url);
									var promise = request.post(_url, {
										handleAs : 'json'
									});
									var _self = this;
									promise.then(function(items) {
										if(items.length>0){
											_self.containerNode.innerHTML='';
											_self._createTitleNode();
											if(items.length < 3){
												array.forEach(items, function(item,index) {
													_self._createPathItem(item,item.label);
												}, this);
											}else{
												var item_length = items.length;
												array.forEach(items, function(item,index) {
													if(index == (item_length-3)){
														_self._createPathItem(item,'...');
													}else if(index > (item_length-3)){ 
														_self._createPathItem(item,item.label);
													}else{
														return;
													}
												}, this);
											}
										}else{
											//错误处理
										}
										//判定分类是否显示
										if(evt.isBase == false && evt.fdKey.indexOf("modeling") < 0) {
											if(document.querySelectorAll("div.muiFormRelevanceFilterItem").length > 0){
												document.querySelectorAll("div.muiFormRelevanceFilterItem").forEach(
													function (el){
														el.remove();
													});
											}
											var categoryMixin = null;
											if(evt.isSimpleCategory){
												categoryMixin =  'data-dojo-mixins="mui/simplecategory/SimpleCategoryMixin"';
											}else{
												categoryMixin =  'data-dojo-mixins="mui/syscategory/SysCategoryMixin"';
											}
											if(categoryMixin){
												var filterItem = '<div class="muiHeaderItemRight" data-dojo-type="mui/catefilter/FilterItem" ' +
													categoryMixin  +
													'data-dojo-props="modelName:\''+evt.fdTemplateModelName+'\'"></div>';
												parser.parse(domConstruct.create('div', {
													innerHTML: filterItem,
													className: 'muiFormRelevanceFilterItem'
												}, _self.domNode));
											}
										}
										}, function(data) {
										//错误处理
									});
								}
							}else{
								this.containerNode.innerHTML=Msg["mui.relevance.path.all"];
								this._firstPathInfo(evt);
								this.connect(this.containerNode, "onclick", function(){
									topic.publish("/mui/category/changed",this,{
										'fdId':'',
										'fdKey':''
									});
								});
								//domStyle.set(this.domNode,{display:'none'});
							}
						}
					}
				},
			//首次打开弹框，加载路径
			_firstPathInfo : function (evt){
				if(this.isCategory){
					evt.fdId = this.fdId;
					evt.fdKey = this.fdKey;
					evt.isSimpleCategory = this.isSimpleCategory;
					evt.fdTemplateModelName = this.fdTemplateModelName;
					evt.isBase = this.isBase;
					this.isCategory = false;
					domStyle.set(this.domNode,{display:'block'});
					if(this.curId != evt.fdId || this.containerNode.innerHTML == Msg["mui.relevance.path.all"]){
						this.curId = evt.fdId;
						this.fdKey = evt.fdKey;
						var _url = util.urlResolver(this.detailUrl,this);
						_url = util.formatUrl(_url);
						var promise = request.post(_url, {
							handleAs : 'json'
						});
						var _self = this;
						promise.then(function(items) {
							if(items.length>0){
								_self.containerNode.innerHTML='';
								_self._createTitleNode();
								if(items.length < 3){
									array.forEach(items, function(item,index) {
										_self._createPathItem(item,item.label);
									}, this);
								}else{
									var item_length = items.length;
									array.forEach(items, function(item,index) {
										if(index == (item_length-3)){
											_self._createPathItem(item,'...');
										}else if(index > (item_length-3)){
											_self._createPathItem(item,item.label);
										}else{
											return;
										}
									}, this);
								}
							}else{
								//错误处理
							}
							if(evt.isBase == false && evt.fdKey.indexOf("modeling") < 0) {
								if (document.querySelectorAll("div.muiFormRelevanceFilterItem").length > 0) {
									document.querySelectorAll("div.muiFormRelevanceFilterItem").forEach(
										function (el) {
											el.remove();
										});
								}
								var categoryMixin = null;
								if (evt.isSimpleCategory) {
									categoryMixin = 'data-dojo-mixins="mui/simplecategory/SimpleCategoryMixin"';
								} else {
									categoryMixin = 'data-dojo-mixins="mui/syscategory/SysCategoryMixin"';
								}
								if(categoryMixin){
									var filterItem = '<div class="muiHeaderItemRight" data-dojo-type="mui/catefilter/FilterItem" ' +
										categoryMixin +
										'data-dojo-props="modelName:\'' + evt.fdTemplateModelName + '\'"></div>';
										//'data-dojo-props="modelName:\'' + evt.fdTemplateModelName + '\',catekey: \'fdTemplate\',prefix:\'\'"></div>';
									parser.parse(domConstruct.create('div', {
										innerHTML: filterItem,
										className: 'muiFormRelevanceFilterItem'
									}, _self.domNode));
								}
							}
						}, function(data) {
							//错误处理
						});
					}
				}else{
					//把muiFormRelevanceFilterItem隐藏
					if(document.querySelectorAll("div.muiFormRelevanceFilterItem").length > 0){
						document.querySelectorAll("div.muiFormRelevanceFilterItem").forEach(
							function (el){
								el.remove();
							});
					}
				}
			}
			});
			return path;
});
