define([
	"dojo/_base/array",
	"dojo/_base/declare",
	"dojo/dom-class",
	"mui/datetime/_DatePickerMixin_year",
	"dojox/mobile/SpinWheel",
	"dojox/mobile/SpinWheelSlot"
], function(array, declare, domClass, _DatePickerMixin_year, SpinWheel, SpinWheelSlot){

	// module:
	//		dojox/mobile/SpinWheelDatePicke

	return declare("mui.datetime.SpinWheelDatePicker_year", [SpinWheel, _DatePickerMixin_year], {
		// summary:
		//		A SpinWheel-based date picker widget.
		// description:
		//		SpinWheelDatePicker is a date picker widget. It is a subclass of
		//		dojox/mobile/SpinWheel. It has three slots: year, month, and day.

		slotClasses: [
			SpinWheelSlot,
			SpinWheelSlot,
			SpinWheelSlot
		],

		slotProps: [
			{labelFrom:1970, labelTo:2038},
			{},
			{}
		],

		buildRendering: function(){
			this.initSlots();
			this.inherited(arguments);
			domClass.add(this.domNode, "mblSpinWheelDatePicker");
			if(dojoConfig.locale == "en-us"){
				dojo.style(dojo.query(".mblSpinWheelSlot:nth-child(1)")[0], "display", "none");

			}else {
				dojo.style(dojo.query(".mblSpinWheelSlot:nth-child(3)")[0], "display", "none");
			}
			dojo.style(dojo.query(".mblSpinWheelSlot:nth-child(2)")[0], "display", "none");
			
			dojo.query('.mblSpinWheelSlotLabel').forEach(function(element){
				element.style.textAlign="center";
			});


			this._conn = [
				this.connect(this.slots[0], "onFlickAnimationEnd", "_onYearSet"),
				this.connect(this.slots[1], "onFlickAnimationEnd", "_onMonthSet"),
				this.connect(this.slots[2], "onFlickAnimationEnd", "_onDaySet")
			];
		},

		disableValues: function(/*Number*/daysInMonth){
			// summary:
			//		Disables the end days of the month to match the specified
			//		number of days of the month.
			array.forEach(this.slots[2].panelNodes, function(panel){
				for(var i = 27; i < 31; i++){
					domClass.toggle(panel.childNodes[i], "mblSpinWheelSlotLabelGray", i >= daysInMonth);
				}
			});
		}
	});
});
