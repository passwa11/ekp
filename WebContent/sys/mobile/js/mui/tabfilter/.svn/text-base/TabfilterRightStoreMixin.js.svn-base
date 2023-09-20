define([
    "dojo/_base/declare",
	"dojo/store/Memory",
	"mui/store/JsonRest",
	"dojox/mobile/viewRegistry"
	], function(declare, Memory, JsonRest, viewRegistry) {
	
	return declare("mui.tabfilter.TabfilterRightStoreMixin",  null, {
		
		
		
		curVaules : "",
		
		curTexts : "",
		
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe("/mui/tabfilter/left/change", "_cateChange");
			this.subscribe("/mui/tabfilter/selChanged/add", "_setCurSel");
			this.subscribe("/mui/tabfilter/selChanged/delete", "_setCurSel");
		},
		
		_cateChange : function(obj, evt) {
			var sview =  viewRegistry.getEnclosingView(this.domNode);
			if(sview && sview.scrollTo) {
				sview.scrollTo({
				 x : 0,
				 y : 0
				});
			}
			if(evt) {
				var store, self = this;
				if(!evt.items && (this.url || evt.url)) {
					 this.url = evt.url;
					 store = new JsonRest({
								target : this.url
							});
				} else {
					var d = this._formatData(evt.items);
					store = new Memory({
						data : d
					});
				}
				this.store = null;
				this.setStore(store, this.query, this.queryOptions);
			}
		},
		
		_formatData : function(data) {
			if(data && data.length > 0) {
				var rtn = [];
				for(var i = 0; i < data.length; i ++) {
					if(typeof(data[i] === "string")) {
						rtn.push({
							value : data[i],
							text : data[i]
						})
					}
				}
				if(rtn.lenth == 0) {
					rtn = data;
				}
				return rtn;
			}
		},
		
		_setCurSel : function(obj, evt) {
			if(evt) {
				if(evt.curValues && evt.curValues == this.curValues) {
					return;
				}
				this.curValues = evt.values;
				this.curTexts = evt.texts;
			}
		}
		
	});
});