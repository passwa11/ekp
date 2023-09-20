define(['dojo/_base/declare','dojo/dom-style','dojo/query','dojo/dom-geometry'],function(declare,domStyle,query,domGeometry){
	return declare('sys.attend.mobile.CalendarViewMixin',[],{
		resize : function() {
			this.inherited(arguments);
			if(this.isStatReader){
				var h = domStyle.get(this.domNode,'height');
				var titleH = domGeometry.getMarginBox(query('.muiEkpSubClockInTitle')[0]).h;
				domStyle.set(this.domNode, 'height',(h - titleH) + 'px');
			}
		}
	});
});