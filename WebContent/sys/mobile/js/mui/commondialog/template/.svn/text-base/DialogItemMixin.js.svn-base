define(	["dojo/_base/declare","dojo/_base/array", "dojo/dom-construct", "dojo/dom-class",
				"dojo/topic", "dojox/mobile/_ItemBase", "mui/util", "mui/iconUtils"],
		function(declare, array, domConstruct, domClass, topic, ItemBase, util, iconUtils) {
			var item = declare("mui.category.CategoryItemMixin", [ItemBase], {

						fdId : '',
						
						tag : 'li',
						
						//事件key
						key : null,
						
						_enterClass:'mblListItemSelected',
						
						data:{},
						
						displayProp:"",
						
						props:{},
						
						buildRendering : function() {
							this._templated = !!this.templateString;
							if (!this._templated) {
								this.domNode = this.containerNode = this.srcNodeRef
										|| domConstruct.create(this.tag, {
											className : 'muiCateItem'
										});
								var className = 'muiCateInfoItem';
								this.contentNode = domConstruct.create(
										'div', {
											className : className
										}, this.domNode);
							}
							this.label = this.data[this.displayProp]; 
							this.inherited(arguments);

							if (!this._templated)
								this._buildItemBase();
						},
						
						postCreate : function() {
							this.inherited(arguments);
							this.subscribe('/mui/category/cancelSelected','_cancelSelected');
							this.subscribe('/mui/category/setSelected','_setSelected');
						},
						
						//构建基本框架
						_buildItemBase : function() {
							this.cateContainer = domConstruct.create("div",{className:"muiCateContainer"},this.contentNode);
							this.iconNode = domConstruct.create('div', {
								'className' : 'muiCateIcon'
							}, this.cateContainer);
							this.buildIcon(this.iconNode);
							this.infoNode = domConstruct.create('div', {
											'className' : 'muiCateInfo'
										}, this.cateContainer);
							this.titleNode = domConstruct.create('div', {
											'className' : 'muiCateName ',
											'innerHTML' :  this.data[this.displayProp]
										}, this.infoNode);
							this.contentNode = domConstruct.create('div', {
								'className' : 'muiListSummary'
							}, this.infoNode);
							for(var tmpKey in this.props){
								if(tmpKey!=this.displayProp && this.data[tmpKey]){
									var tmpDom = domConstruct.toDom('<div>' + this.data[tmpKey] + '</div>');
									domConstruct.create('div', {
										'className' : 'muiCateProp',
										'innerHTML' :  '<span class="muiCatePropTitle">' +this.props[tmpKey] + ':</span>&nbsp;&nbsp;'
											+'<span class="muiCatePropInfo">' + tmpDom.innerText + "</span>"
									}, this.contentNode);
								}
							}
							this.connect(this.infoNode, "click", "_selectCate");
							this.connect(this.iconNode, "click", "_selectCate");
						},

						startup : function() {
							if (this._started) {
								return;
							}
							this.inherited(arguments);
							var parent = this.getParent();
							this.key = parent.key;
							this.selectArea = domConstruct.create('div', {
									'className' : 'muiCateSelArea'
								}, this.cateContainer,'first');//用于占位
							 this.selectNode = domConstruct.create('div', {
									'className' : 'muiCateSel'
								}, this.selectArea);
							 if(parent.isMul){
								 domClass.add(this.selectNode, "muiCateSelMul");
							 }
							 if(this.isSelected()){
								 this.checkedIcon= domConstruct.create('i', {
										'className' : 'mui mui-checked muiCateSelected'
									}, this.selectNode);
								 domClass.add(this.selectNode, "muiCateSeled");
							 }
							 this.connect(this.selectArea, "click", "_selectCate");
						},
						
						//是否选中
						isSelected:function(){
							var pWeiget = this.getParent();
							if(pWeiget && pWeiget.curIds){
								var arrs = pWeiget.curIds.split(";");
								if (array.indexOf(arrs,this.fdId)>-1)
									return true;
							}
							return false;
						},
						
						_openCate:function(evt){
							this.set("entered", true);
							this.defer(function(){
								this.set("entered", false);
								topic.publish("/mui/category/changed",this,{
									'fdId':this.fdId,
									'label':this.data[this.displayProp]
								});
							}, 200);
							return;
						},
						
						_cancelSelected:function(srcObj , evt){
							if(srcObj.key==this.key){
								if(evt && evt.fdId){
									if(evt.fdId.indexOf(this.fdId)>-1){
										if(this.checkedIcon){
											 domClass.remove(this.selectNode,"muiCateSeled");
											 domConstruct.destroy(this.checkedIcon);
											 this.checkedIcon= null;
											 topic.publish("/mui/category/unselected",this, this.data);
										}
									}
								}
							}
						},
						
						_setSelected:function(srcObj,evt){
							if(srcObj.key==this.key){
								if(evt && evt.fdId){
									if(evt.fdId==this.fdId){
										domClass.add(this.selectNode,"muiCateSeled");
										this.checkedIcon= domConstruct.create('i', {
											'className' : 'mui mui-checked muiCateSelected'
										}, this.selectNode);
										this.set("entered", true);
										this.defer(function(){
											this.set("entered", false);
										},200);
										topic.publish("/mui/category/selected",this,this.data);
								     }
								}
							}
						},
						
						startTime:0,
						
						_selectCate:function(evt){
							if(this.startTime!=0){
								var endTime = new Date().getMilliseconds();
								if(endTime - this.startTime < 350){
									this.startTime = 0;
									return;
								}
								this.startTime = 0;
							}else{
								this.startTime = new Date().getMilliseconds();
								this.defer(function(){
									this.startTime = 0;
								},360);
							}
							if(evt){
								if (evt.stopPropagation)
									evt.stopPropagation();
								if (evt.cancelBubble)
									evt.cancelBubble = true;
								if (evt.preventDefault)
									evt.preventDefault();
								if (evt.returnValue)
									evt.returnValue = false;
							}
							if(this.selectArea){
								if(this.selectNode){	//存在选择区域时设置是否选中
									if(this.checkedIcon != null){
										this._cancelSelected(this,this);
									}else{
										this._setSelected(this,this);
									}
									return;
								}
							}
							this.showItemDetail();
							return;
						},
	
						showItemDetail:function(){
							
						},
						
						buildIcon:function(iconNode){
							iconUtils.setIcon("mui mui-file-text", null,
									this._headerIcon, null, iconNode);
						},
						
						_setLabelAttr : function(text) {
							if (text)
								this._set("label", text);
						},
						
						_setEnteredAttr: function(entered){
							domClass.toggle(this.domNode, this._enterClass, entered);
						}
					});
			return item;
		});