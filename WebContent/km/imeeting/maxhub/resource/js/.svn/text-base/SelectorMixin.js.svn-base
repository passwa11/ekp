define([ 'dojo/_base/declare'],
	function(declare) {
		return declare('km.imeeting.maxhub.SelectorMixin', null, {
			
			initProps: function() {
				
				this.inherited(arguments);

				var options = this.options;
				
				// 数据排序
				options.sort(function(a, b) {
					if(a.value > b.value) {
						return 1;
					} else if(a.value < b.value) {
						return -1;
					} else {
						return 0;
					}
				});
				
				var i = 0, l = (options || []).length;
				for(i; i < l; i++) {
					options[i].text = parseFloat(options[i].value).toFixed(1) + options[i].unit;
				}
				
				this.options = options;
				
			}
			
		});
	}
);