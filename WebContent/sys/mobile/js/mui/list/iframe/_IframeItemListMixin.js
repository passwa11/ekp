define([
    "dojo/_base/declare",
    "dojo/topic",
    'dojo/dom-geometry',
    'resource/js/domain'
	], function(declare, topic, domGeometry) {
	
	domain.register('$ekpIframePush',function(event){
		topic.publish('/mui/list/iframe/push');
	});
	
	return declare("mui.list._IframeItemListMixin", [], {
		
		lazy: false,
		
		startup : function(){
			this.inherited(arguments);
			this.subscribe('/mui/list/iframe/push', 'handleOnIframePush');
		},
		
		handleOnIframePush : function(){
			this.loadMore && this.loadMore();
		},
		
		doLoad : function(handle, append){
			handle = handle || {};
			var __done = handle.done || function(){},
				self = this;
			handle.work = handle.work || function(){};	
			handle.done = function(context){
				__done(context);
				
				var size = domGeometry.getMarginSize(document.body);
				domain.call(parent, "$ekpIframeListLoaded",[{
					target : domain.getParam(window.location.href,'LUIID'),
					geometry : {
						height : size.h,
						width : size.w
					},
					data : {
						loadOver : self._loadOver
					}
				}]);
			};
			handle.error = handle.error || function(){};
			this.inherited(arguments,[handle, append]);
		}
		
	});
});