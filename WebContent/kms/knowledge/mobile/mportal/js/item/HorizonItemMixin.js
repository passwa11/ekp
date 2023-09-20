define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
		"mui/util", "sys/mportal/mobile/OpenProxyMixin",'mui/i18n/i18n!kms-knowledge:kmsKnowledge.read' ], function(declare,
		domConstruct, domClass, domStyle, domAttr, ItemBase, util,
		OpenProxyMixin,msg) {

	var item = declare("sys.mportal.item.HorizonItemMixin", [ ItemBase,
			OpenProxyMixin ], {
		
		tag : "li",

		label : '',
		
		labelText : '',

		icon : '',

		href : '',

		otherText : '',

		baseClass : '',

		docCreateTimeInteval : '',
		
		created : '',//时间
		
		count : '',
		
		__IS_LAST_ROW_ITEM__: false, // 是否为最后一行的元素

		buildRendering : function() {
			
			this.domNode = this.containerNode = this.srcNodeRef || domConstruct.create(this.tag);
			
			// 显示在最后一行的元素加上特殊CSS类名，用于控制最后一行的元素不显示下边框...
			if(this.__IS_LAST_ROW_ITEM__){ 
				domClass.add(this.domNode,"muiDripListLastRowItem");
			}
			
			this.inherited(arguments);

			var a = domConstruct.create('a', {
				href : 'javascript:;'
			}, this.domNode);

			this.proxyClick(a, this.href, '_blank');
			if (this.labelText) {
				var label = util.formatText(this.labelText);
				if(this.labelIcon){
					label = this.labelIcon + label;
				}
				domConstruct.create('span', {
					className : 'muiDripListTitle muiFontSizeM muiFontColorInfo',
					innerHTML : label
				}, a);

				// 添加图片
				if (this.icon) {
					var imgUrl = util.formatUrl(this.icon);

				} else {
					// 默认图
					var imgUrl = util.formatUrl('/sys/mobile/css/themes/default/images/complxItemDefaultIcon.png');

				}
				domConstruct.create('div', {
					className : 'imgFixSize',
					innerHTML : '<img style="max-width: 100%;max-height: 100%;vertical-align: middle" src="'+imgUrl+'"><img/>'
				}, a);

			}

			var info = domConstruct.create("div", {
				className : "muiDripListInfo muiFontSizeS muiFontColorMuted"
			}, a);


			if(this.created) {
				domConstruct.create("span", {
					className : "date",
					innerHTML : this.created
				}, info);
			}

			if(this.count) {
				domConstruct.create("span", {
					className : "count",
					innerHTML : "<span class='muiDripListReadNum'>"+this.count+"</span><span class='muiDripListReadViewText'>"+msg['kmsKnowledge.read']+"</span>"
				}, info);
			}
		},

		_setLabelAttr : function(text) {
			if (text)
				this._set("label", text);
		}
	});
	return item;
});