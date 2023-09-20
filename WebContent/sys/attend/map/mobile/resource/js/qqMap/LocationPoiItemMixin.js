define(	["dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class","mui/list/item/_ListLinkItemMixin",
				"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
				"dojo/dom", "mui/util", "dojo/_base/lang","dojo/request","dojo/query","dojo/topic",
				"mui/dialog/Tip"],
		function(declare, domConstruct, domClass,_ListLinkItemMixin, domStyle, domAttr, ItemBase,
				dom, util,lang, request,query,topic, Tip) {
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
							if(this.showStatus == 'edit'){
								this.connect(this.domNode,'onclick','_onLocClick');
								this.domNode.dojoClick = true;
							}
						},
						 
						_onLocClick : function(event){
							event.preventDefault();
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
								var link = 'http://apis.map.qq.com/uri/v1/routeplan?type=drive&coord_type=1&policy=1&referer=ekp',
									coord = this.location.lat + this.location.lng;
									address = this.name;
									
								//from为空则自动传入当前位置
								link = link + 'to' + address + 'tocoord' + coord;
								location.href = link;
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
