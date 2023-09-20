define([ "dojo/_base/declare", "dijit/_WidgetBase", "dojo/_base/array",
				"dojox/mobile/ScrollableView", "dojo/dom", "dojo/dom-construct",
				"dojo/dom-style", "dojo/dom-class", "dojo/request", "dojo/topic", 
				"mui/util",  "mui/dialog/Tip" , "mui/iconUtils","sys/xform/mobile/controls/fSelect/FSelectAllCheckBox",
				"mui/i18n/i18n!sys-xform-base:mui"],
		function(declare, WidgetBase,array, ScrollableView,dom, domConstruct, domStyle, domClass,
				request, topic, util, Tip, iconUtils,FSelectAllCheckBox,Msg) {
			var selection = declare("sys.xform.mobile.controls.fSelect.FSelectSelection",[ WidgetBase ],{
			
				baseClass : 'muiFSelectSec',
				
				//对外事件唯一标示
				key : null,
				
				isMul:false,
				
				count:0,
				
				CHECKED_EVENT : "/sys/xform/fSelect/checked",

				UNCHECKED_EVENT : "/sys/xform/fSelect/unchecked",
				
				SUBMIT_EVENT : "/sys/xform/fSelect/submit",
				
				buildRendering : function() {
					this.inherited(arguments);
					this.containerNode = domConstruct.create("div" ,{'className':'muiFSelectContainer'},this.domNode);
					this.leftArea = domConstruct.create("div",{'className':'muiFSelectSecLeft'},this.containerNode);
					var selectAllWiget = this._createSelectAllCheckBox();
					this.selectAllCheckboxArea = selectAllWiget.selectAllCheckboxArea;
					domConstruct.place(this.selectAllCheckboxArea, this.leftArea);
					this.rightArea = domConstruct.create("div",{'className':'muiFSelectSecRight'},this.containerNode);
					this.buttonNode =  domConstruct.create("span",{'className':'muiFSelectSecBtn','innerHTML':Msg["mui.event.selection.ok"]},this.rightArea);
				},

				postCreate : function() {
					this.inherited(arguments);
					this.subscribe(this.CHECKED_EVENT,"_addSelItme");
					this.subscribe(this.UNCHECKED_EVENT,"_delSelItem");
					this.subHandle = this.connect(this.buttonNode,'click','_subSelItem');
				},
				
				_createSelectAllCheckBox : function(){
					var selectAllCheckBox = new FSelectAllCheckBox({key:this.key, isMul:this.isMul}); 
					selectAllCheckBox.startup();
					return selectAllCheckBox;
				},
				
				//发布确定按钮提交事件
				_subSelItem:function(){
					topic.publish(this.SUBMIT_EVENT , this);
				},
				
				_addSelItme:function(){
					var srcOb = arguments[0];
					if (!srcOb.isSelectAll){
						this.count = this.count + 1;
						this._resizeSelection();
					}
				},
				
				_delSelItem:function(){
					if(this.count>0){
						this.count = this.count - 1;
					}
					this._resizeSelection();
				},
				
				_delCount:function(){
					if(this.count>0){
						this.count = 0;
						this._resizeSelection();
					}
				},
				
				_resizeSelection:function(){
					/*if(this.count > 0){*/
						this.buttonNode.innerHTML = Msg["mui.event.selection.ok"]+'('+this.count+')';
						this.buttonNode.className = "muiFSelectSecBtn";
//						if(this.subHandle==null)
//							this.subHandle = this.connect(this.buttonNode,'click','_subSelItem');
					/*}else{
						this.buttonNode.innerHTML = Msg["mui.event.selection.ok"];
						this.buttonNode.className = "muiCateSecBtn muiCateSecBtnDis";
						if(this.subHandle){
							this.disconnect(this.subHandle);
							this.subHandle = null;
						}
					}*/
				}
			});
			return selection;
		});