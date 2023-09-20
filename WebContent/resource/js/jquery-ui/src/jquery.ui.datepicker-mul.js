/*! jQuery Mul Datepicker Addon v1.0.0 - 2017-11-16
* 
* Depends:
*	jquery.ui.core.js
*	jquery.ui.datepicker.js
*/
(function ($) {
	
	$.ui.muldatepicker = $.ui.muldatepicker || {};
	if ($.ui.muldatepicker.version) {
		return;
	}
	
	$.extend($.ui, {
		muldatepicker: {
			version: "1.0.0"
		}
	});
	
	function MulDatepicker(){
		this.regional = []; // Available regional settings, indexed by language code
		this.regional[''] = {};
		this._defaults = {
			mulselectClass : 'ui-muldatepicker-select',
			mulSplit : ','
		};
		$.extend(this._defaults, this.regional['']);
	}
	
	$.extend(MulDatepicker.prototype,{
		
		_newInst : function($input, opts){
			var _defaults = $.extend({},this._defaults,{
				muldatepicker : true // add muldatepicker as a property of datepicker: $.datepicker._get(dp_inst, 'muldatepicker');
			},opts);
			return {
				_defaults : _defaults
			};
		}
	});
	
	/*
	* Create a Singleton Instance
	*/
	$.muldatepicker = new MulDatepicker();
	
	$.fn.extend({
		
		muldatepicker : function(o){
			o = o || {};
			var tmp_args = arguments;
			if (typeof(o) === 'string') {
				if (o === 'getDate') {
					return $.fn.datepicker.apply($(this[0]), tmp_args);
				} else {
					return this.each(function () {
						var $t = $(this);
						$t.datepicker.apply($t, tmp_args);
					});
				}
			} else {
				return this.each(function () {
					var $t = $(this);
					$t.datepicker($.muldatepicker._newInst($t, o)._defaults);
				});
			}
		}
	});
	
	/*
	* bad hack :/ override datepicker so it can overwrite the input's value
	*/
	$.datepicker.________base_selectDate = $.datepicker._selectDate;
	$.datepicker._selectDate = function(id, dateStr){
		var onSelect,
			inst = this._getInst($(id)[0]),
			mp_inst = this._get(inst, 'muldatepicker');
		if(mp_inst){
			dateStr = (dateStr != null ? dateStr : this._formatDate(inst));
			if(!dateStr){
				dateStr = $.muldatepicker.clear(inst);
			}else{
				var split = this._get(inst,'mulSplit');
				dateStr = $.muldatepicker.has(inst, dateStr) ? $.muldatepicker.remove(inst,dateStr).join(split) : $.muldatepicker.add(inst,dateStr).join(split);
			}
			if (inst.input) {
				inst.input.val(dateStr);
			}
			this._updateAlternate(inst);
			onSelect = this._get(inst, "onSelect");
			if (onSelect) {
				onSelect.apply((inst.input ? inst.input[0] : null), [dateStr, inst]);  // trigger custom callback
			} else if (inst.input) {
				inst.input.trigger("change"); // fire the change event
			}
			this._updateDatepicker(inst);
		}else{
			this.________base_selectDate(id, dateStr);
		}
	};
	
	/*
	* second bad hack :/ override datepicker so we can redraw table cell
	*/
	$.datepicker.______base_updateDatepicker = $.datepicker._updateDatepicker;
	$.datepicker._updateDatepicker = function(inst){
		inst.dpDiv.addClass('lui_muldatepicker');
		var self = this;
		
		// #58402 允许直接修改分钟数字
		this.______base_updateDatepicker(inst);
		var muldatepicker = this._get(inst, 'muldatepicker');
		if(!muldatepicker) {				
			return;
		}
		
		var tablecells = $('[data-handler="selectDay"]',inst.dpDiv),
			muldate = inst.dpDiv.data('__muldate__') || [];
		var target = inst.dpDiv;
		target.data('__muldate__', []);
		var newMuldate = [];
		var muldate = inst.input.val().split(this._get(inst,'mulSplit'));
		for(var i = 0; i < muldate.length; i++){
			if(muldate[i]!="")
				newMuldate.push(muldate[i]);
		}
		target.data('__muldate__', newMuldate);
		$.each(tablecells,function(index, cell){
			var day = $('a',cell).html(),
				month = $(cell).attr('data-month'),
				year = $(cell).attr('data-year');
			var date = $.datepicker._formatDate(inst,day,month,year);
			if($.muldatepicker.has(inst,date)){
				$(cell).addClass(self._get(inst,'mulselectClass'));
			}
		})
	};
	
	/*
	* third bad hack :/ override _formatDate because foolish timepicker's override
	*/
	$.datepicker.______base_formatDate = $.datepicker._formatDate;
	$.datepicker._formatDate = function(inst, day, month, year){
		var mp_inst = this._get(inst, 'muldatepicker');
		if(mp_inst &&  !!this._base_formatDate){
			return this._base_formatDate(inst, day, month, year);
		}
		return this.______base_formatDate(inst, day, month, year);
	};
	
	$.muldatepicker.has = function(inst, date){
		var target = inst.dpDiv,
			muldate = target.data('__muldate__') || [];
		for(var i = 0; i < muldate.length; i++){
			var _date = muldate[i];
			if(_date == date){
				return true;
			}
		}
		return false;
	};
	
	$.muldatepicker.add = function(inst, date){
		var target = inst.dpDiv,
			muldate = target.data('__muldate__') || [];
		var flag = true;
		for(var i = 0; i < muldate.length; i++){
			var _date = muldate[i];
			if(_date == date){
				flag = false;
			}
		}
		if(flag){
			muldate.push(date);
			target.data('__muldate__', muldate); 
		}
		return muldate;
	};
	
	$.muldatepicker.remove = function(inst, date){
		var target = inst.dpDiv,
			muldate = target.data('__muldate__') || [];
			newMuldate = [];
		for(var i = 0; i < muldate.length; i++){
			var _date = muldate[i];
			if(_date != date){
				newMuldate.push(_date);
			}
		}
		target.data('__muldate__', newMuldate); 
		return newMuldate;
	};
	
	$.muldatepicker.clear = function(inst){
		var target = inst.dpDiv;
		target.data('__muldate__',[]);
		return [];
	}
	
	$.muldatepicker.version = "1.0.0";
	
	
})(jQuery);
