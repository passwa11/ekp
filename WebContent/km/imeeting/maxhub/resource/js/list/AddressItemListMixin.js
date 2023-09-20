define([ 'dojo/_base/declare', 'dojo/_base/array', 'dojo/_base/lang' , 'dojo/topic', 'dojo/request', 
         'dojo/dom', 'dojo/dom-construct', 'dojo/dom-style','dojo/dom-class', 'dojo/dom-attr', 'dojo/html', 
         './item/AddressItem'],
	function(declare, array, lang, topic, request, dom, domCtr, domStyle, domClass, domAttr, html,
			AddressItem) {
		return declare('km.imeeting.maxhub.AddressItemListMixin', null,{
			
			itemRenderer: AddressItem,
			
			dataTemp:'',
			dataLength: 0,
			
			initialize: function() {
				this.inherited(arguments);
				domClass.add(this.domNode, 'mhuiAddressList');
			},
			
			reload: function(cb) {
				this.inherited(arguments);
				cb && cb(this.dataLength);
			},
			
			renderList: function(data) {
				
				this.dataLength = (data || []).length;
				
				var flag=false;
				var n=0;
				if(this.dataTemp!='') {
					
					if(this.dataTemp.length==data.length) {
						
						for(var jsonTemp in this.dataTemp) {
							
							for(var json in data){
								
								if(data[json].fdId==this.dataTemp[jsonTemp].fdId) {
									n=n+1;
									break;
								}
							}
						}
						
						if(n!=data.length) {
							flag=true;
						}
						
					} else {
						flag=true;
					}
					
				} else {
					flag=true;
				}
				
				this.dataTemp=data;
				
				if(flag) {
					this.inherited(arguments);
				}else{
					return;
				}
				
			},
			
			
			getList: function(cb) {
				
				request(this.url, {
					method: 'get',
					handleAs: 'json'
				}).then(function(res){
					
					var data = res || [];
					self.items= data;
					
					cb(data);
					
				}, function(err) {
					console.error(err);
				});
				
			}
			
			
		});
	}
);