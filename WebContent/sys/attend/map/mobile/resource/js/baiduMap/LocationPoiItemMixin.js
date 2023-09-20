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
									value : this.title,
									point : this.point,
									detail: this.address
								});
								if(this.showStatus == 'view'){
									var navBtn = domConstruct.create('i', {className : 'mui muiNavIcon mui-navigation'}, this.domNode);
									this.connect(navBtn,'onclick','_onNavBtnClick');
									navBtn.dojoClick = true;
								}
							}
							domConstruct.create('i', {className : 'mui muiLoctionIcon mui-location'}, this.domNode);
							domConstruct.create('h2', {className : 'title',innerHTML:this.title}, this.domNode);
							domConstruct.create('p', {className : 'summary',innerHTML:this.address}, this.domNode);
							
							this.connect(this.domNode,'onclick','_onLocClick');
							this.domNode.dojoClick = true;
						},
						 
						_onLocClick : function(event){
							var okBtnNode = query('.muiLocationToolbar .muiLocationOkBtn');
							if(okBtnNode.length==0){
								return ;
							}
							event.preventDefault();
							var oldSelNodes = query('.muiLocationPoiList .active');
							var oldSelNode = "";
							if(oldSelNodes.length>0){
								domClass.remove(oldSelNodes[0], "active");
								oldSelNode = oldSelNodes[0];
							}
							var value = this.title;
							var point = this.point;
							var address = this.address;
							if(this.domNode==oldSelNode){
								value = point = address ='';
							}else{
								domClass.add(this.domNode,'active');
							}
							var evt = {
								value : value,
								point : point,
								detail : address
							};
							topic.publish('/map/location/poi/change',this,evt);
							if(evt.point){
								topic.publish('/map/location/center/reset',this,evt);
							}
						},
						
						_onNavBtnClick : function(){
							var self = this;
							if(this.point){
								var link = 'http://api.map.baidu.com/direction?mode=driving&output=html&src=ekp',
									coord = this.point.lat + ',' + this.point.lng,
									address = this.title;
								
								adapter.getCurrentPosition(function(result){
									var coordType = result.coordType;
									var ___point = new BMap.Point(result.point.lng,result.point.lat);
									if(coordType==5){
										new BMap.Convertor().translate([___point],3,5,function(result){
											if(result.status==0){
						            			self._openNavUrl(result.points[0],link,coord,address);
											}else{
												alert('地图加载失败');
											}
										});
									}else{
										self._openNavUrl(___point,link,coord,address);
									}
								},function(){
									alert('地图加载失败');
								});
							}
						},
						
						_openNavUrl:function(fdLatLng,link,coord,address){
							var curCoord = fdLatLng.lat+','+fdLatLng.lng,
            				curAddress = '我的位置';
	            			var geoc = new BMap.Geocoder();
	            			geoc.getLocation(fdLatLng,function(rs){
	            				var curCity = rs.addressComponents.city || rs.addressComponents.province;
	            				// 没有region无法成功导航
	            				link = link + '&origin=latlng:' + curCoord + '|name:' + curAddress
	            							+ '&destination=latlng:' + coord + '|name:' + address + '&region=' + curCity;
	            				location.href = link;
	    					});
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
