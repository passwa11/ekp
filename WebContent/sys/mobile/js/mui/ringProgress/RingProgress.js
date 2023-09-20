define([ "dojo/_base/declare", "dijit/_WidgetBase","dojo/dom-construct","dojo/dom-style" ,"dojo/dom-class"],
		function(declare,WidgetBase,domConstruct,domStyle,domClass) {

		var RingProgress = declare("mui.ringProgress.RingProgress",[ WidgetBase ],{
				
			value : 0,
			
			_value : 0,
			
			buildRendering:function(){
				this.inherited(arguments);
				this._buildRing();
			},
		
			_buildRing:function() {
				this._value = this.value > 100 ? 100 :  this.value;
				domClass.add(this.domNode,'muiRingProgress');
				//角度=360度*progress/100
				var deg=(18*parseInt(this._value)/5);
				var muiRingProgressLeftPie=domConstruct.create("div",{className:"muiRingProgressLeftPie"},this.domNode);
				var left=domConstruct.create("div",{className:"muiRingProgressLeft"},muiRingProgressLeftPie);
				if(deg > 180){
					var _deg=deg-180;
					domStyle.set(left,'transform','rotate('+_deg+'deg)');
					domStyle.set(left,'-webkit-transform','rotate('+_deg+'deg)');
				}
				var muiRingProgressRightPie=domConstruct.create("div",{className:"muiRingProgressRightPie"},this.domNode);
				var right=domConstruct.create("div",{className:"muiRingProgressRight"},muiRingProgressRightPie);
				if(deg < 180){
					domStyle.set(right,'transform','rotate('+deg+'deg)');
					domStyle.set(right,'-webkit-transform','rotate('+deg+'deg)');
				}else{
					domStyle.set(right,'transform','rotate(180deg)');
					domStyle.set(right,'-webkit-transform','rotate(180deg)');
				}
				var mask=domConstruct.create("div",{className:"muiRingProgressmask",innerHTML:'%'},this.domNode);
				var span=domConstruct.create("span",{innerHTML:this.value});
				domConstruct.place(span,mask,'first');
				
			}
					
					
		});

		return RingProgress;

});
