define([ "dojo/_base/declare", "dojo/dom-class" ], function(declare, domClass) {

	return declare("sys.evaluation.mobile.js._EvaluationReplyLinkItem", null, {

		_selClass : 'actived',
		_selStartMethod : "touch",
		_setSelectedAttr : function(selected) {
			this.inherited(arguments);
			if (this.currentUserId == this.replyerId)
				return;
			domClass.toggle(this.domNode, this._selClass, selected);
		},
		handleSelection : function(e) {
			this.set("selected", true);
		},
		makeTransition : function(e) {
			this.defer(function() {
				this.set("selected", false);
			}, 100);
		},
		_onClick : function(e) {
			this.defaultClickAction(e);
		}
	});
});