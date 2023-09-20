define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase","mui/util", "dojo/topic", "sys/mportal/mobile/OpenProxyMixin",
		"mui/i18n/i18n!sys-mobile:mui.item.views" ],
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,util, topic, OpenProxyMixin, viewsMsg) {
			
	var item = declare("sys.mportal.SimpleItemMixin",[ ItemBase,OpenProxyMixin ],{

		baseClass : "muiPortalSimpleListItem",
		label: '',     // 标题
		creator:'',    // 处理人或部门
		created : '',  // 时间
		otherText : '',// 其他信息（如阅读量、点评数等）
		href:'',
        
		/**
		 * 简单列表展现，目前使用该组件的模块
		 * 1、公文管理政务版：最新发文、最新收文、最新签报
		 * 2、规范制度：最新规范制度（简单列表）
		 * 3、公文管理：最新公文
		 * 4、工作总结：工作总结
		 */
		buildRendering : function() {
			this.inherited(arguments);
			var linkNode = domConstruct.create('a',{className : 'muiPortalSimpleListLink' },this.domNode);
			if(this.href){
				this.proxyClick(linkNode, this.href, '_blank');
			}else{
				domAttr.set(linkNode,'href','javascript:void(0);');
			}
			
			// 标题
			domConstruct.create('div',{innerHTML:this.label,className : 'muiPortalSimpleListLabel muiFontSizeM muiFontColorInfo'},linkNode);
			
			// 底部基本信息DOM（人员姓名、部门名称、时间、）
			var footerNode = domConstruct.create('div',{className : 'muiPortalSimpleListFooter muiFontSizeS muiFontColorMuted'},linkNode);
			
			// 人员名称或部门名称
			if(this.creator){
				var creatorNode = domConstruct.create('span',{className : 'muiPortalSimpleListName'},footerNode),
					textNode = domConstruct.create('span',{innerHTML: this.creator },creatorNode);
			}
			
			// 时间
			if(this.created){
				var createdNode = domConstruct.create('span',{className : 'muiPortalSimpleListCreated'},footerNode),
					textNode =  domConstruct.create('span',{innerHTML: this.created },createdNode);
			}
			
			// 其他信息（如阅读量、点评数等）
			if(this.otherText){
				var modelNode = domConstruct.create('span',{className : 'muiPortalSimpleListOther'},footerNode);
				var numReg=/^[0-9]+.?[0-9]*$/;
				var otherTextHtml = "";
				if(numReg.test(this.otherText)){
					var readNumNode = domConstruct.create('span',{className : 'muiPortalSimpleListReadNum'},modelNode);
					readNumNode.innerText = this.otherText;
					var readNumTextNode = domConstruct.create('span',{className : 'muiPortalSimpleListReadViewText'},modelNode);
					readNumTextNode.innerText = viewsMsg['mui.item.views'];
				}else{
					domConstruct.create('span',{className : 'muiPortalSimpleListOther',innerHTML: this.otherText },modelNode);
				}
				
			}
		},

		_setLabelAttr : function(label) {
			if (label)
				this._set("label", label);
		}
	});
	return item;
});
