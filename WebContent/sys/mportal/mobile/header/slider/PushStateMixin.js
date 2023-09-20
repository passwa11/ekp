define([ "dojo/_base/declare","dojo/topic" ], function(declare,topic) {
	var NavItem = declare('mui.portal.PushStateMixin', null, {
		buildRendering : function() {

			this.inherited(arguments);

			if (history.pushState) {

				this.connect(window, 'popstate', function(evt) {
					if(evt.state)
						if(evt.state.id){
							topic.publish('/sys/mportal/navBarMore/changeData',{
								value : evt.state.id,
								back : true
							});
						}
				})
			}
		},

	});

	return NavItem;
});