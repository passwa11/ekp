define( [ "dojo/_base/declare","dojo/dom-style" ], function(declare, domStyle) {
	return declare("notify.index.notifyMainViewMixin", null , {
		resize: function(){
			domStyle.set(this.domNode,{
				'height': document.documentElement.clientHeight + 'px'
			});
		}
	});
});