/**
 * Select2 Chinese translation
 */
(function ($) {
    "use strict";
    $.extend($.fn.select2.defaults, {
        formatNoMatches: function () { return XformObject_Lang.relation_select_noFound; },
        formatInputTooShort: function (input, min) { 
        	var n = min - input.length;
        	var message =  XformObject_Lang.relation_select_addChars;
        	return message.replace("{n}",n);
    	},
        formatInputTooLong: function (input, max) { 
        	var n = input.length - max;
        	var message =  XformObject_Lang.relation_select_deleteChars;
        	return message.replace("{n}",n);
    	},
        formatSelectionTooBig: function (limit) {
        	var message = XformObject_Lang.relation_select_MostChooseNItem;
        	return message.replace("{n}",limit); 
    	},
        formatLoadMore: function (pageNumber) { return XformObject_Lang.relation_select_loading; },
        formatSearching: function () { return XformObject_Lang.relation_select_searching; }
    });
})(jQuery);
