define(	["dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class","mui/list/item/_ListLinkItemMixin",
				"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
				"dojo/dom", "mui/util", "dojo/_base/lang","dojo/request","dojo/query","dojo/topic",
				"mui/dialog/Tip","mui/device/adapter"],
		function(declare, domConstruct, domClass,_ListLinkItemMixin, domStyle, domAttr, ItemBase,
				dom, util,lang, request,query,topic, Tip,adapter) {
			var item = declare("sys.attend.locationPoiItemMixin", [ItemBase,_ListLinkItemMixin], {

						tag : "li",
						href : 'javascript:,',
						
						buildRendering : function() {
							this.inherited(arguments);
							this.domNode = domConstruct.create('li', {className : ''}, this.containerNode);
							if(this.defaultValue){
								domClass.add(this.domNode,'active');
								topic.publish('/map/location/poi/change',this,{
									name : this.name,
									location : this.location,
									detail: this.address
								});
								if(this.showStatus == 'view'){
									var navBtn = domConstruct.create('i', {className : 'mui muiNavIcon mui-navigation'}, this.domNode);
									this.connect(navBtn,'onclick','_onNavBtnClick');
									navBtn.dojoClick = true;
								}
							}
							domConstruct.create('i', {className : 'mui muiLoctionIcon mui-location'}, this.domNode);
							domConstruct.create('h2', {className : 'title',innerHTML:this.name}, this.domNode);
							domConstruct.create('p', {className : 'summary',innerHTML:this.address}, this.domNode);
							if(this.showStatus=='edit'){
								this.connect(this.domNode,'onclick','_onLocClick');
								this.domNode.dojoClick = true;
							}
						},
						 
						_onLocClick : function(event){
							var oldSelNodes = query('.muiLocationPoiList .active');
							var oldSelNode = "";
							if(oldSelNodes.length>0){
								domClass.remove(oldSelNodes[0], "active");
								oldSelNode = oldSelNodes[0];
							}
							var name = this.name;
							var location = this.location;
							var address = this.address;
							if(this.domNode==oldSelNode){
								name = location = address ='';
							}else{
								domClass.add(this.domNode,'active');
							}
							topic.publish('/map/location/poi/change',this,{
								name : name,
								location : location,
								detail : address
							});
						},
						
						_onNavBtnClick : function(){
							if(this.location){
								var link = 'http://uri.amap.com/navigation?mode=car&policy=0&src=ekp&coordinate=gaode&callnative=0',
									coord = this.location.lng + ',' + this.location.lat;
									address = this.name;
								AMap.plugin(['AMap.Geolocation'],function(){
									adapter.getCurrentPosition(function(result){
										var coordType = result.coordType;
										var lngLat = new AMap.LngLat(result.point.lng,result.point.lat);
										if(coordType==3){
											AMap.convertFrom(lngLat,'baidu',function(status,res){
												if(status=='complete'){
													var point = res.locations[0],
													curCoord = point.lng + ',' + point.lat,
													curAddress = '我的位置';
													
													link = link + '&from=' + curCoord + ',' + curAddress 
																+ '&to=' + coord + ',' + address;
													
													location.href = link;
												}else{
													alert('地图加载失败');
												}
											});
										}else{
											var point = lngLat,
											curCoord = point.lng + ',' + point.lat,
											curAddress = '我的位置';
											
											link = link + '&from=' + curCoord + ',' + curAddress 
														+ '&to=' + coord + ',' + address;
											
											location.href = link;
										}
									},function(){
										alert('地图加载失败');
									});
								});
							}
						},
						
						_onClick:function(){},
						startup : function() {
							if (this._started) {
								return;
							}
							this.inherited(arguments);
						},

						_setLabelAttr : function(text) {
							if (text)
								this._set("label", text);
						}
					});
			return item;
		});
