define([ 
	"dojo/_base/declare", 
	"dojo/_base/lang",
	"dojo/_base/array",
	"dojo/topic",
	"dojo/request",	
	"dojo/dom-construct",
	"dojo/dom-style",
	"dojo/dom-attr",
	"dojo/dom-class",
	"dojo/dom-geometry",
	"dojo/query",
	"dojox/mobile/ScrollableView",
	"mui/category/ScrollableCategoryPath",
	"mui/util",
	"sys/unit/mobile/selectdialog/DialogChannelMixin",
	"mui/i18n/i18n!sys-mobile:mui",
	],function(declare, lang, array, topic, request, domConstruct, domStyle, domAttr, domClass, domGeometry, query, ScrollableView, ScrollableCategoryPath, util, DialogChannelMixin, Msg) {
	return declare("sys.unit.UnitAllCatePath", [ ScrollableCategoryPath, DialogChannelMixin ],
		{
			
			// 是否显示面包屑
			visible: true,
			
			// 获取详细信息地址
			detailUrl : '/sys/unit/km_imissive_unit_category/kmImissiveUnitCategory.do?method=getParents&fdId=!{curId}',
						
			titleNode : Msg['mui.mobile.address.allCate'],
			
			buildRendering: function(){
				if (this._started) {
					return
				}
				this.inherited(arguments);
				domClass.add(this.domNode,'muiAddressPath');
				this.arrowNode = domConstruct.create('div',{
					className: 'muiAddressPathArrow'
				}, this.domNode);
				domConstruct.create('i',{
					className: 'muiAddressPathArrowIcon'
				}, this.arrowNode);
			},
			
			postCreate: function(){
				this.inherited(arguments);
				this.connect(this.arrowNode, 'click', '_toggleExpand');
			},
			
			 _createScrollNode: function() {
				 this.scroll = new ScrollableView({
					 scrollBar: false,
					 scrollDir: "h",
					 height: "3rem"
				 });
			     this.addChild(this.scroll);
			 },
			
			_createPath : function(items){
				// 移除所有PathItem
				var expandItemNodes = query('.muiAddressPathItemExpand', this.domNode);
				array.forEach(expandItemNodes, function(childNode){
					domConstruct.destroy(childNode);
				}, this);
				this.inherited(arguments);
				domAttr.set(this.containerNode, "data-length", items.length);
				if(!this.visible){
					domStyle.set(this.domNode, 'display', 'none');
				}
				domClass.toggle(this.domNode, 'hasExpand', false);
				this._resizePath();
			},
			
			_resizePath: function(){
				var hasExpand = this._hasExpand();
				if(hasExpand){ // 展开的情况下，将scroll中的所有pathItem移到顶层，使用"inline-block"平铺所有
					domStyle.set(this.scroll.domNode, 'display', 'none');
					var scrollContainer = this.scroll.containerNode;
					while(scrollContainer.firstElementChild){
						var childNode = scrollContainer.firstElementChild;
						domClass.add(childNode, 'muiAddressPathItemExpand');
						domConstruct.place(childNode, this.containerNode, 'last');
					}
				}else{
					var expandItemNodes = query('.muiAddressPathItemExpand', this.domNode);
					array.forEach(expandItemNodes, function(childNode){
						domClass.remove(childNode, 'muiAddressPathItemExpand');
						domConstruct.place(childNode, this.scroll.containerNode, 'last');
					}, this);
					domStyle.set(this.scroll.domNode, 'display', 'block');
				}
				// 是否可展开
				domClass.toggle(this.domNode, 'canExpand', this._hasExpand() || this._canExpand());
			},
			
			// 是否可以展开
			_canExpand: function(){
				var containerNodeW = domGeometry.getContentBox(this.scroll.containerNode).w;
				var domNodeW = domGeometry.getContentBox(this.scroll.domNode).w;
				return containerNodeW > domNodeW;
			},
			
			// 是否已展开
			_hasExpand: function(){
				return domClass.contains(this.domNode, 'hasExpand');
			},
			
			// 切换已展开状态
			_toggleExpand: function(){
				domClass.toggle(this.domNode, 'hasExpand', !this._hasExpand());
				this._resizePath();
			},
			
			_chgHeaderInfo : function(srcObj, evt) {
				if(this.isSameChannel(srcObj)){
					this.inherited(arguments);
				}
			},
			
			resize: function(){
				this.inherited(arguments);
				this._resizePath();
			}
			
		});
	});