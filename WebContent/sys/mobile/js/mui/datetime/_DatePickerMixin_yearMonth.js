define([
	"dojo/_base/array",
	"dojo/_base/declare",
	"dojo/_base/lang",
	"dojo/date",
	"dojo/date/locale",
	"dojo/date/stamp"
], function(array, declare, lang, ddate, datelocale, datestamp){

	// module:
	//		dojox/mobile/_DatePickerMixin

	var slotMixin = {
		format: function(/*Date*/d){
			return datelocale.format(d, {datePattern:this.pattern, selector:"date",locale: this.picker.lang});
		}
	};

	var yearSlotMixin = lang.mixin({
		initLabels: function(){
			this.labels = [];
			if(this.labelFrom !== this.labelTo){
				var d = new Date(this.labelFrom, 0, 1);
				var i, idx;
				for(i = this.labelFrom, idx = 0; i <= this.labelTo; i++, idx++){
					d.setFullYear(i);
					this.labels.push(this.format(d));
				}
			}
		}
	}, slotMixin);

	var monthSlotMixin = lang.mixin({
		initLabels: function(){
			this.labels = [];
			// On certain BlackBerry devices, we need to init to 16 not 1 to avoid some devices bugs (see #15677)
			var d = new Date(2000, 0, 16);
			for(var i = 0; i < 12; i++){
				d.setMonth(i);
				this.labels.push(this.format(d));
			}
		}
	}, slotMixin);

	var daySlotMixin = lang.mixin({
		initLabels: function(){
			this.labels = [];
			var d = new Date(2000, 0, 1);
			for(var i = 1; i <= 31; i++){
				d.setDate(i);
				this.labels.push(this.format(d));
			}
		}
	}, slotMixin);

	return declare("mui/datetime/_DatePickerMixin_yearMonth", null, {
		// summary:
		//		A mixin for date picker widget.

		// yearPattern: String
		//		A pattern to be used to format year.
		yearPattern: "yyyy",

		// monthPattern: String
		//		A pattern to be used to format month.
		monthPattern: "MMM",

		// dayPattern: String
		//		A pattern to be used to format day.
		dayPattern: "d",

		/*=====
		// value: String
		//		A string representing the date value.
		//		The setter of this property first converts the value argument by calling
		//		the fromISOString method of the dojo/date/stamp module, then sets the
		//		values of the picker according to the resulting	Date object.
		//		If the string cannot be parsed by fromISOString, the method does nothing.
		//		Example: set("value", "2012-1-20"); // January 20, 2012
		//		The getter returns the string formatted as described in the dojo/date/stamp
		//		module.
		value: "",
		=====*/

		initSlots: function(){
			// summary:
			//		Initializes the slots.
			var c = this.slotClasses, p = this.slotProps;
			c[0] = declare(c[0], yearSlotMixin);
			c[1] = declare(c[1], monthSlotMixin);
			c[2] = declare(c[2], daySlotMixin);
			p[2].picker = p[1].picker = p[0].picker = this;
			p[0].pattern = this.yearPattern;
			p[1].pattern = this.monthPattern;
			p[2].pattern = this.dayPattern;
			this.reorderSlots();
		},

		reorderSlots: function(){
			// summary:
			//		Reorders the slots.
			if(this.slotOrder.length){ return; }
			var a = datelocale._parseInfo({locale: this.lang}).bundle["dateFormat-short"].toLowerCase().split(/[^ymd]+/, 3);
			this.slotOrder = array.map(a, function(pat){
				return {y:0, m:1, d:2}[pat.charAt(0)];
			});
		},

		reset: function(){
			// summary:
			//		Goes to today.
			var now = new Date();
			var v = array.map(this.slots, function(w){ return w.format(now); });
			this.set("colors", v);
			this._disableEndDaysOfMonth();
			if(this.value){
				this.set("value", this.value);
				this.value = null;
			}else if(this.values){
				this.set("values", this.values);
				this.values = null;
			}else{
				this.set("values", v);
			}
		},

		_onYearSet: function(){
			// summary:
			//		An internal handler called when the year value is changed.
			// tags:
			//		private
			var slot = this.slots[0];
			var newValue = slot.get("value");
			if(!(slot._previousValue && newValue == slot._previousValue)){ // do nothing if the value is unchanged
				this._disableEndDaysOfMonth();
				slot._previousValue = newValue;
				slot._set("value", newValue);
				this.onYearSet();
			}
		},

		onYearSet: function(){
			// summary:
			//		A handler called when the year value is changed.
		},

		_onMonthSet: function(){
			// summary:
			//		An internal handler called when the month value is changed.
			// tags:
			//		private
			var slot = this.slots[1];
			var newValue = slot.get("value");
			if(!(slot._previousValue && newValue == slot._previousValue)){ // do nothing if the value is unchanged
				this._disableEndDaysOfMonth();
				slot._previousValue = newValue;
				slot._set("value", newValue); // notify watches
				this.onMonthSet();
			}
		},

		onMonthSet: function(){
			// summary:
			//		A handler called when the month value is changed.
		},

		_onDaySet: function(){
			// summary:
			//		An internal handler called when the day value is changed.
			// tags:
			//		private
			var slot = this.slots[2];
			var newValue = slot.get("value");
			if(!(slot._previousValue && newValue == slot._previousValue)){ // do nothing if the value is unchanged
				if(!this._disableEndDaysOfMonth()){
					// If _disableEndDaysOfMonth has changed the day value,
					// skip notifications till next call of _onDaySet, to
					// avoid the extra notification for the (invalid)
					// intermediate value of the day.
					slot._previousValue = newValue;
					slot._set("value", newValue); // notify watches
					this.onDaySet();
				}
			}
		},

		onDaySet: function(){
			// summary:
			//		A handler called when the day value is changed.
		},

		_disableEndDaysOfMonth: function(){
			// summary:
			//		Disables the end days of the month to match the specified
			//		number of days of the month. Returns true if the day value is changed.
			// tags:
			//		private
			var pat = this.slots[0].pattern + "/" + this.slots[1].pattern,
				v = this.get("values"),
				date = datelocale.parse(v[0] + "/" + v[1], {datePattern:pat, selector:"date",locale: this.lang}),
				daysInMonth = ddate.getDaysInMonth(date);
			var changedDay = false;
			if(daysInMonth < v[2]){
				// day value is invalid for this month, change it
				changedDay = true;
				this.slots[2]._spinToValue(daysInMonth, false/*applyValue*/);
			}
			this.disableValues(daysInMonth);
			return changedDay;
		},

		_getDateAttr: function(){
			// summary:
			//		Returns a Date object for the current values.
			// tags:
			//		private
			var v = this.get("values"), // [year, month, day]
				s = this.slots,
				pat = s[0].pattern + "/" + s[1].pattern ;
			return datelocale.parse(v[0] + "/" + v[1] , {datePattern:pat, selector:"date", locale: this.lang});
		},

		_setValuesAttr: function(/*Array*/values){
			// summary:
			//		Sets the current date as an array of values.
			// description:
			//		This method takes an array that consists of three values,
			//		year, month, and day. If the values are integer, they are
			//		formatted to locale-specific strings before setting them to
			//		the slots. Month starts from 1 to 12 (Ex. 1 - Jan, 2 - Feb, etc.)
			//		If the values are NOT integer, they are directly
			//		passed to the setter of the slots without formatting.
			//
			// example:
			//	|	set("values", [2012, 1, 20]); // January 20, 2012
			// tags:
			//		private
			array.forEach(this.getSlots(), function(w, i){
				var v = values[i];
				if(typeof v == "number"){
					var arr = [1970, 1, 1];
					arr.splice(i, 1, v - 0);
					v = w.format(new Date(arr[0], arr[1] - 1, arr[2]));
				}
				w.set("value", v);
			});
		},

		_setValueAttr: function(/*String*/value){
			// summary:
			//		Sets the current date as an String formatted according to a subset of the ISO-8601 standard.
			// description:
			//		This method first converts the value argument by calling the fromISOString method of
			//		the dojo/date/stamp module, then sets the values of the picker according to the resulting
			//		Date object. If the string cannot be parsed by fromISOString, the method does nothing.
			// value:
			//		A string formatted as described in the dojo/date/stamp module.
			// example:
			//	|	set("value", "2012-1-20"); // January 20, 2012
			// tags:
			//		private
			var date = datestamp.fromISOString(value);
			this.set("values", array.map(this.slots, function(w){ return w.format(date); }));
		},

		_getValueAttr: function(){
			// summary:
			//		Gets the current date as a String formatted according to a subset of the ISO-8601 standard.
			// returns: String
			//		A string formatted as described in the dojo/date/stamp module.
			// tags:
			//		private
			return datestamp.toISOString(this.get("date"), { selector: "date" });
		}
	});
});
