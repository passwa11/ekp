define([ 
	"dojo/_base/declare", 
	"dojo/dom-class", 
	"dijit/registry", 
	"dojo/dom-construct", 
	"dojo/dom-style", 
	"dojo/_base/array",
	"./AddressChannelMixin"
	], function(declare, domClass, registry, domConstruct, domStyle, array, AddressChannelMixin) {

	return declare("mui.address.AddressScrollNavMixin", AddressChannelMixin, {

		_addNavItem : function(srcObj, evt) {
			this.isSameChannel(srcObj, evt) && this.inherited(arguments);
		},
		_resizeNav:function(srcObj){
			if(srcObj.key==this.key){
				domConstruct.empty(this.domNode);
				var height = this.refrenceDom.offsetHeight;
				var absH = 0;
				if(this.absoluteDom)
					absH = this.absoluteDom.offsetHeight;
				var _self = this;
				array.forEach(this.navDatas,function(txt,idx){
					if(txt!='2' && txt!='4'){
						var navItem = domConstruct.create('span',{"className":"muiCateNavItem",innerHTML:txt},_self.domNode);
						_self.connect(navItem,'click',function(){
							_self._ItemClick({label:txt,refHeight:height});
						});
						_self.navItems.push(navItem);
					}
				});
				if(this.navItems.length>0){
					domStyle.set(this.domNode, {
						'display':'block',
						'top':(this.refrenceDom.offsetTop + absH) +'px'
					});
					
					var toTop = domConstruct.create('span',{"className":"muiCateNavItem"},this.domNode,'first');
					var searchIcon = domConstruct.create('i',{"className":"mui mui-search"},toTop);
					this.connect(toTop,'click','_toTop');
					this.navItems.push(toTop);
					domStyle.set(searchIcon,{'font-size': '12px'});
					domStyle.set(searchIcon,{'height': '18px'});
					domStyle.set(searchIcon,{'line-height': '18px'});
					array.forEach(this.navItems,function(item){
						domStyle.set(item,{'height': '18px','line-height': '18px','font-size':'12px'});
					});
				}else{
					domStyle.set(this.domNode,{'display':'none'});
				}
			}
		},

		startup : function() {
			var self = this;
			self.inherited(arguments);
			self.defer(function() {
				if(self.containerDom) {
					var containerDomWidget = registry.byId(self.containerDom);
					domConstruct.place(self.domNode, containerDomWidget.domNode);
				}
			});
		}

	});
});
