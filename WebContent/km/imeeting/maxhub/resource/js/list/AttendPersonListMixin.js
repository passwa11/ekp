define([ 'dojo/_base/declare', 'dojo/_base/array', 'dojo/_base/lang' , 'dojo/topic', 'dojo/request', 
         'dojo/dom', 'dojo/dom-construct', 'dojo/dom-style','dojo/dom-class', 'dojo/dom-attr', 'dojo/html', 
         './item/AttendPersonItem'],
	function(declare, array, lang, topic, request, dom, domCtr, domStyle, domClass, domAttr, html,
			AttendPersonItem) {
		return declare('km.imeeting.maxhub.AttendPersonItemMixin', null,{
			
			itemRenderer: AttendPersonItem,
			
			tempLength: 0,
			
			beforeInit: function() {
				this.inherited(arguments);
				domClass.add(this.domNode, 'mhui-avatar-list');
			},

			renderList: function(data) {
				
				if(data.length == this.tempLength) {
					return;
				}
				
				this.tempLength = data.length;
				this.inherited(arguments);
				
			},
			
			getList: function(cb) {
				
				request(this.url, {
					method: 'get',
					handleAs: 'json'
				}).then(function(res){
					
					cb(res || []);
					
				}, function(err) {
					console.error(err);
				});
				
			}
			
			
		});
	}
);