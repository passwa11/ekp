define([ "dojo/_base/declare", "dijit/_WidgetBase", "dojo/_base/array",
				"dojox/mobile/ScrollableView", "dojo/dom", "dojo/dom-construct",
				"dojo/dom-style", "dojo/dom-class", "dojo/request", "dojo/topic", 
				"mui/util",  "mui/dialog/Tip" , "mui/iconUtils","mui/i18n/i18n!sys-mobile" ],
		function(declare, WidgetBase,array, ScrollableView,dom, domConstruct, domStyle, domClass,
				request, topic, util, Tip, iconUtils,Msg) {
			var selection = declare("sys.lbpmext.authorize.mobile.js.authorizescope.LbpmAuthorizeSelection",[ WidgetBase ],{
				//非必填项,用于初始值
				curIds : null,
				
				curNames : null,
				
				baseClass : 'muiCateSec muiCateSecBottom',
				
				//已选列表id, name, icon
				cateSelArr : [],
				
				itemPrefix : '_CateSecItem_',
				
				//对外事件唯一标示
				key : null,

				buildRendering : function() {
					this.inherited(arguments);
					this.cateSelArr = [];
					this.containerNode = domConstruct.create("div" ,{'className':'muiCateSecContainer muiLbpmSecContainer'},this.domNode);
					this.leftArea = domConstruct.create("div",{'className':'muiCateSecLeft'},this.containerNode);
					this.view = new ScrollableView({scrollBar:false,threshold:100,scrollDir:"h",dirLock:true});
					this.leftArea.appendChild(this.view.domNode);
					
					this.rightArea = domConstruct.create("div",{'className':'muiCateSecRight'},this.containerNode);
					this.buttonNode =  domConstruct.create("span",{'className':'muiCateSecBtn muiCateSecBtnDis','innerHTML':Msg['mui.button.ok']},this.rightArea);
				},

				postCreate : function() {
					this.inherited(arguments);
					this.subscribe("/mui/category/selected","_addSelItme");
					this.subscribe("/mui/category/unselected","_delSelItem");
				},

				startup : function() {
					if (this._started) {
						return;
					}
					this.inherited(arguments);
					this.view.startup();
					this._initSelection();
				},
				
				destroy:function(){
					this.inherited(arguments);
					if(this.view.destroy){
						this.view.destroy();
						delete this.view;
					}
				},
				
				_subSelItem:function(){
					if(this.cateSelArr.length>0){
						topic.publish("/sys/lbpmext/authorize/mobile/js/authorizescope/submit" , this, this._calcCurSel());
					}else{
						Tip.tip({icon:'mui mui-warn', text:Msg["mui.category.select.one"]});
					}
				},
				
				_initSelection:function(){
					if(this.curIds && this.curIds != "null"){
						var ids = this.curIds.split(";");
						var names = this.curNames.split(";");
						for(var i = 0;i<ids.length;i++){
							var item = {};
							item.fdId = ids[i];
							item.label = names[i].indexOf("/")>-1?names[i].substring(names[i].lastIndexOf("/")+1):names[i];
							this._addSelItme(this,item);
						}
					}
				},
				
				_buildSelItem:function(item){
					var selDom = domConstruct.create("div",{'id': this.itemPrefix + item.fdId , 'className':'muiCateSecItem'});
					var iconArea = domConstruct.create("div",{'className':'muiCateSecItemIcon'},selDom);
					var iconNode = domConstruct.create("div",{'className':'muiCateIcon'},iconArea);
					this.buildIcon(iconNode, item);
					domConstruct.create("div",{'className':'muiCateSecItemLabel','innerHTML':item.label},selDom);
					this.connect(selDom,'click',function(evt){
						this._delSelItem(this,item);
						topic.publish("/mui/category/cancelSelected",this,item);
					});
					return selDom;
				},
				
				buildIcon:function(iconNode,item){
					iconUtils.setIcon("mui mui-file-text", null, null, null,
							iconNode);
				},
				
				_checkInSelArr:function(value){
					var flag = false;
					for(var i = 0;i<this.cateSelArr.length;i++){
						if(this.cateSelArr[i].fdId == value){
							flag = true;
							break;
						}
					}
					return flag;
				},
				
				
				_addSelItme:function(srcObj,evt){
					if(srcObj.key==this.key){
						if(evt){
							if(!this._checkInSelArr(evt.fdId)){
								this.cateSelArr.push(evt);
								var selItem = this._buildSelItem(evt);
								if(!this.itemContainer){
									this.itemContainer = domConstruct.create("div",{'className':'muiCateSecItems'},this.view.containerNode);
								}
								this.itemContainer.appendChild(selItem);
							}
						}
						this._resizeSelection();
					}
				},
				
				_delSelItem:function(srcObj,evt){
					if(srcObj.key==this.key){
						if(evt && evt.fdId){
							for ( var i=0;i< this.cateSelArr.length; i++) {
								if(this.cateSelArr[i].fdId==evt.fdId){
									this.cateSelArr.splice(i,1);
									domConstruct.destroy(dom.byId(this.itemPrefix + evt.fdId));
									break;
								}
							}
							this._resizeSelection();
						}
					}
				},
				
				_resizeSelection:function(){
					var xPos = 0;
					if(this.itemContainer){
						var childCount = this.cateSelArr.length;
						if(childCount > 0){
							var oneW = this.itemContainer.children[0].offsetWidth;
							var conW = this.view.domNode.offsetWidth;
							if(oneW * childCount > conW){
								domStyle.set(this.itemContainer,{'width':(oneW * childCount + 10 ) + 'px'});
								xPos = -(oneW * childCount - conW + 10);
							}
							this.buttonNode.innerHTML = Msg['mui.button.ok'] +'('+childCount+')';
							this.buttonNode.className = "muiCateSecBtn";
							if(this.subHandle==null)
								this.subHandle = this.connect(this.buttonNode,'click','_subSelItem');
						}else{
							domStyle.set(this.itemContainer,{'width':'100%'});
							this.buttonNode.innerHTML = Msg['mui.button.ok'];
							this.buttonNode.className = "muiCateSecBtn muiCateSecBtnDis";
							if(this.subHandle){
								this.disconnect(this.subHandle);
								this.subHandle = null;
							}
						}
					}
					if(this.view.resize){
						this.view.resize();
					}
					if(this.view.scrollTo){
						this.view.scrollTo({y:0,x:xPos});
					}
					topic.publish("/mui/category/selChanged" ,this, this._calcCurSel());
				},
				
				_calcCurSel:function(){
					var eCxt = {
						curIds:null,
						curNames:null,
						key: this.key
					};
					if(this.cateSelArr.length>0){
						var ids = '';
						var names = '';
						array.forEach(this.cateSelArr,function(selItem){
							ids += ';' + selItem.fdId;
							names += ';' + selItem.label;
						});
						if(ids!=''){
							ids = ids.substring(1);
							names = names.substring(1);
							eCxt.curIds = ids;
							eCxt.curNames = names;
						}
					}
					return eCxt;
				}
			});
			return selection;
		});