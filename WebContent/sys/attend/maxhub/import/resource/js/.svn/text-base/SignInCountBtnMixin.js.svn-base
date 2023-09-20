define([ 'dojo/_base/declare', 'dojo/_base/array', 'dojo/_base/lang' , 'dojo/topic', 'dojo/query',
         'dojo/dom', 'dojo/dom-construct', 'dojo/dom-style','dojo/dom-class'],
	function(declare, array, lang, topic, query, dom, domCtr, domStyle, domClass) {
		return declare('sys.attend.maxhub.SignInCountBtnMixin', null ,{
			
			startup : function() {
				this.inherited(arguments);
				this.connect(this.domNode, "click", '_onClick');
			},
			
			_onClick: function() {

				var list = query('.sysAttendView_personList');
				array.forEach(list, function(l) {
				
					if(domClass.contains(l, 'active')) {
						domClass.remove(l, 'active');
					}
					
				});
				
				var target = dom.byId(this.targetId);
				
				if(target && !domClass.contains(target, 'active')) {
					domClass.add(target, 'active');
				}
				
				var arrow = dom.byId('sysAttendPersonArrow');
				if(arrow && this.targetId == 'sysUnAttendPersonList') {
					domClass.add(arrow, 'toggle');
				} else {
					domClass.remove(arrow, 'toggle');
				}
				
			}
			
		});
	}
);