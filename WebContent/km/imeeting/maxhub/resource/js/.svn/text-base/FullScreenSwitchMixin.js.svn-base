define([ 'dojo/_base/declare', 'dojo/_base/array', 'dojo/_base/lang' , 'dojo/topic', 'dojo/request/xhr', 
         'dojo/dom', 'dojo/dom-construct', 'dojo/dom-class', 'dojo/dom-attr'],
	function(declare, array, lang, topic, request, dom, domCtr, domClass, domAttr) {

		return declare('km.imeeting.maxhub.FullScreenSwitchMixin', null, {
			
			fullScreenClassName: 'mhui-full-screen',
			
			startup : function() {
				this.inherited(arguments);
				domClass.add(this.domNode, 'mhuiFullScreenSwitch');
				this.connect(this.domNode, "click", '_onClick');
			},
			
			_onClick: function() {

				var main = dom.byId('imeetingMain');
				var toolbar = dom.byId('imeetingToolbar');
				
				var isFullScreen = domClass.contains(main, this.fullScreenClassName);
				
				if(isFullScreen) {
					main && domClass.remove(main, this.fullScreenClassName);
					toolbar && domClass.remove(toolbar, this.fullScreenClassName + '-toolbar');
					domClass.remove(this.domNode, 'active');
				} else {
					main && domClass.add(main, this.fullScreenClassName);
					toolbar && domClass.add(toolbar, this.fullScreenClassName + '-toolbar');
					domClass.add(this.domNode, 'active');
				}
				
			}
		});
	}
);