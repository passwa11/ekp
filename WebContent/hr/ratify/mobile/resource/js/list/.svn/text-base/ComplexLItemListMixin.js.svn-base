define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"../item/ComplexLItemMixin",
	'dojo/_base/lang',
	'dojo/when',
	'mui/store/JsonRest',
	'mui/store/JsonpRest',
	'mui/util'
	], function(declare, _TemplateItemListMixin, ComplexLItemMixin,lang,when,JsonStore, JsonpStore,util) {
	
	return declare("hr.ratify.mobile.list.ComplexLItemListMixin", [_TemplateItemListMixin], {
		
		startup:function(){
			this.inherited(arguments);
			var _this = this;
			
			this.subscribe('/mui/list/searchReload',function(condition){
				if(condition){
					_this.query['pageno']=1
					_this.keyword = condition['keyword'];
					_this.query=lang.mixin(_this.query,{"keyword":condition['keyword']})
					_this.refresh();
				}
			})
		},
		doLoad: function(handle, append) {
			if (this.busy) {
				return;
			}
			
			if (handle)
				handle.work(this);
			
			this.busy = true;
			this.append = !!append;
			
			if (this.append && this._loadOver) {
				if (handle)
					handle.done(this);
				this.busy = false;
				return;
			}

			var promise = null;
			var buildQuery = this.buildQuery();
			
			if(this.keyword){
				buildQuery['keyword']=this.keyword
			}
			
			if (this.store) {
				this.store.target = this.url;
				promise = this.setQuery(buildQuery, {});
			} else {
				
				if(this.dataType == 'jsonp') {
					promise = this.setStore(new JsonpStore(
							{idProperty: 'fdId', target: util.urlResolver(this.url, this)}), 
							buildQuery, {});
				} else {
					promise = this.setStore(new JsonStore(
							{idProperty: 'fdId', target: util.urlResolver(this.url, this)}), 
							buildQuery, {});
				}
			}
			var self = this;
			if (handle) {
				when(promise, 
						function() {handle.done(self);}, 
						function() {handle.error(self);});
			}
			return promise;
		},
		itemRenderer : ComplexLItemMixin,
	});
});