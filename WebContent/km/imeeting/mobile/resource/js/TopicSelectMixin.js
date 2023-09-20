define( [ "dojo/_base/declare", "dojo/topic", "dojo/_base/lang"], function(declare, topic, lang) {
	
	return declare("km.imeeting.mobile.js.TopicSelectMixin", null, {
		
		cache: {},
		searchName: '',
		searchUrl: '',
		value: '',
		
		postCreate: function(){
			
			var ctx = this;
			
			ctx.inherited(arguments);
			
			topic.subscribe('mui/form/select/callback', function(evt) {
				ctx.value = evt.value
				var customSearch = {}
				customSearch[ctx.searchName] = ctx.value ? ctx.value : undefined
				customSearch.url = ctx.searchUrl
				
				topic.publish('/mui/search/custom', evt, customSearch)
				topic.publish('/mui/search/submit', evt, lang.mixin({}, ctx.cache, customSearch));
			});
			
			topic.subscribe('/mui/search/submit', function(evt, res) {
				ctx.cache = res
			});
			
			topic.subscribe('/mui/search/cancel', function(evt, res) {
				ctx.cache = ''
			});
			
			topic.subscribe('/mui/search/clear', function(evt, res) {
				ctx.cache = ''
			});
			
		},
		
	});
	
});