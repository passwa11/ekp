define([
	"dojo/_base/array",
	"dojo/_base/declare",
	"dojo/dom-class",
	"mui/datetime/_DatePickerMixin7",
	"dojox/mobile/SpinWheel",
	"dojox/mobile/SpinWheelSlot"
], function(array, declare, domClass, _DatePickerMixin7, SpinWheel, SpinWheelSlot){

	// module:
	//		dojox/mobile/SpinWheelDatePicke

	return declare("mui.datetime.SpinWheelDatePicker7", [SpinWheel, _DatePickerMixin7], {
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
			$(".mblSpinWheelSlot").eq(2).hide();
	        
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
