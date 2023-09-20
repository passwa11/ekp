define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
	"dojo/dom-construct",
	'dojo/query',
	'dojo/topic',
	'dojox/mobile/SpinWheel',
	"dojox/mobile/SpinWheelSlot",
	'mui/dialog/Dialog',
	"dojo/on"
	], function(declare,_WidgetBase,domConstruct,query,topic,SpinWheel,SpinWheelSlot,Dialog,on) {
	return declare("hr.staff.mobile.resource.js.dialogSpinWheel", [_WidgetBase], {
		data:[],
		value:'',
		fieldName:'',
		baseClass:'mui-spin-wheel',
		buildRendering:function(){
			this.inherited(arguments);
			this.valueNode = domConstruct.create("input",{name:this.fieldName,type:"hidden",value:this.value},this.domNode);
			this.labelNode = domConstruct.create("div",{className:'spinWheelNode',innerHTML:this.value},this.domNode);
			this.iconNode = domConstruct.create("i",{className:'spin-wheel-icon'},this.domNode);
		},
		startup:function(){
			this.inherited(arguments);
			var _this = this;
			on(this.domNode,"click",function(){
				_this.showDialog();
			})
		},
		showDialog:function(){
			var spinWheelWidget =new SpinWheel();
			spinWheelWidget.startup();
			var data = [];
			try{
				data = this.data.split(";");
			}catch(e){
				console.log(e)
			}
			var slot = new SpinWheelSlot({labels:data, style:{width:"100%"}});
			spinWheelWidget.addChild(slot);
			var dialogContainerNode = domConstruct.create("div",{});
			spinWheelWidget.placeAt(dialogContainerNode);
			var _this = this;
			var dialogObj = Dialog.element({
				canClose : false,
				element :dialogContainerNode,
				buttons : [{
					title:'取消',
					fn:function(){
						dialogObj.hide();
					}
				},{
					title:'确定',
					fn:function(){
						var v = slot.getCenterItem();
						_this.valueNode.value = v.innerHTML;
						_this.labelNode.innerHTML = v.innerHTML;
						dialogObj.hide();
					}
				}],
				position:'bottom',
				'scrollable' :true,
				'parseable' :true,
				showClass : 'muiFormSelect',			
			});
		}
	});
});