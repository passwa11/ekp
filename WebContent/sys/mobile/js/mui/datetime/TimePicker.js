define([ "dojo/_base/declare", "dojo/dom-attr", "dojo/dom-class", "dojo/_base/lang", "dojo/_base/array", "dojo/date/locale", "dojo/date/stamp",
         "dojox/mobile/SpinWheelTimePicker", "dojox/mobile/SpinWheelSlot", "mui/i18n/i18n!sys-mobile:mui", "dojo/_base/config" ], function(
		declare, domAttr, domClass, lang, array, datelocale, datestamp, SpinWheelTimePicker, SpinWheelSlot, msg, config) {
	
	var _format = {
		format:function(d){
			return datelocale.format(d, {timePattern:this.pattern, selector:"time"});
		},
		//更改颜色,文字显示
		setColor: function(/*String*/value,/*String?*/color){
			array.forEach(this.panelNodes, function(panel){
				array.forEach(panel.childNodes, function(node, i){
					var curVal = domAttr.get(node,"data-mobile-val");
					domClass.toggle(node, color || "mblSpinWheelSlotLabelBlue", curVal === value);
					if(curVal == value){
						var tmpDate = datelocale.parse(value, {timePattern:this.pattern, selector:"time"});
						var tmpHtml = datelocale.format(tmpDate, {timePattern:this.disPattern, selector:"time"});
						tmpHtml =tmpHtml +"<span class='mblSpainWheelSlotLabelText'>" + this.disText + "</span>";
						node.innerHTML = tmpHtml;
					}else{
						node.innerHTML = curVal;
					}
				}, this);
			}, this);
		}
	};
	var hourMixin = lang.mixin({
		initLabels: function(){
			if(this.labelFrom !== this.labelTo){
				this.labels = [];
				var d = new Date(2000, 0, 1, this.labelFrom, 0);
				for(var i = this.labelFrom; i <= this.labelTo; i++){
					d.setHours(i);
					this.labels.push(this.format(d));
				}
			}
		}
	}, _format);;

	var minuteMixin = lang.mixin({
		initLabels: function(){
			if(this.labelFrom !== this.labelTo){
				this.labels = [];
				var d = new Date(2000, 0, 1, 0, this.labelFrom);
				for(var i = this.labelFrom; i <= this.labelTo; i+=(this.step || 1)){
					d.setMinutes(i);
					this.labels.push(this.format(d));
				}
			}
		}
	}, _format);
	
	var claz = declare("mui.datetime.TimePicker", [ SpinWheelTimePicker ], {
		pattern:"HH:mm",
		
		houePattern:"HH",
		
		minutePattern:"mm",
		
		disHouePattern:"HH",
		
		disHinutePattern:"mm",
		
		disHourText : msg['mui.datetime.hour'],
		
		disMinuteText : msg['mui.datetime.minute'],
		
		slotClasses:[
		             SpinWheelSlot,
		        	 SpinWheelSlot
		        	 ],
		        	 
		slotProps:[
 			{labelFrom:0, labelTo:23, style:{width:"50%"}},
 			{ 
 				labelFrom: 0, 
 				labelTo: 59, 
 				zeroPad: 2, 
 				style:{ width: "50%" }
 			}
		],
	 	
	 	buildRendering: function(){
	 	
			this.initSlots();
			this.inherited(arguments);
			this._conn = [
				this.connect(this.slots[0], "onFlickAnimationEnd", "_onHourSet"),
				this.connect(this.slots[1], "onFlickAnimationEnd", "_onMinuteSet"),
			];
			
		},
		
		initSlots: function(){
			var c = this.slotClasses, p = this.slotProps;
			
			c[0] = declare(c[0], hourMixin);
			c[1] = declare(c[1], minuteMixin);
			p[0].pattern = this.houePattern;
			p[1].pattern = this.minutePattern;
			p[0].disPattern = this.disHouePattern;
			p[1].disPattern = this.disHinutePattern;
			p[0].disText = this.disHourText;
			p[1].disText = this.disMinuteText;
		
			
			this.minuteStep = (this.minuteStep ? (parseInt(this.minuteStep) || 1) 
					: (config.calendar ? (config.calendar.minuteStep || 1) : 1));
			
			p[1].step = this.minuteStep;
		},
		
		reset:function(){
			this.inherited(arguments);
			this._resetColors();
		},
		
		onHourSet:function(){
			this._resetColors();
		},
		
		onMinuteSet:function(){
			this._resetColors();
		},
		
		_onHourSet:function(){
			var slot = this.slots[0];
			var newValue = slot.get("value");
			if(!(slot._previousValue && newValue == slot._previousValue)){
				slot._previousValue = newValue;
				slot._set("value", newValue);
				this.onHourSet();
			}
		},
		
		_onMinuteSet:function(){
			var slot = this.slots[1];
			var newValue = slot.get("value");
			
			if(!(slot._previousValue && newValue == slot._previousValue)){
				slot._previousValue = newValue;
				slot._set("value", newValue);
				this.onMinuteSet();
			}
		},
		
		_resetColors:function(){
			var v = this.get("values");
			this.set("colors", v);
		},
		
		_setValueAttr : function(value) {
			if (!value){
				var now = new Date();
				value = datelocale.format(now, {timePattern:this.pattern, selector:"time"});
			}
			
			var values = value.trim().split(':');
			
			if (values.length != 2){
				var tmpVals = [];
				var _self = this;
				array.forEach(values,function(val,idx){
					tmpVals.push(_self.slots[idx].get("value"));
				});
				
				this.values = tmpVals;
			}else{
			    this.values = values;
			}
			//按照时间间隔进行值初始化
			var m = parseInt(this.values[1]);
			if(m % this.minuteStep == 0) {
				//不做处理
			} else {
				m = (parseInt(m / this.minuteStep) + 1) * this.minuteStep;
			}
			m = m >= 60 ? 0 : m;
			m = m > 10 ? ('' + m) : ('0' + m);
			this.values[1] = m;
		},

		_getValueAttr : function() {
			var values = [];
			array.forEach(this.slots,function(w){
				values.push(w.get("value"));
			});
			if(values.length>1){
				return values[0] + ':' + values[1];
			}
			return "";
		}
	});
	return claz;
});