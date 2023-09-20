define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase","mui/util", "dojo/topic", "sys/mportal/mobile/OpenProxyMixin",
		"mui/i18n/i18n!sys-mobile:mui.item.views" ],
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,util, topic, OpenProxyMixin, viewsMsg) {
			
	var item = declare("sys.news.TextItemMixin",[ ItemBase,OpenProxyMixin ],{

		baseClass : "muiPortalSimpleListItem",
		label: '',     // 标题
		creator:'',    // 作者
		created : '',  // 时间
		count : '',// 其他信息
		href:'',
		
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
			
			// 底部基本信息DOM（人员姓名、时间、其他信息）
			var footerNode = domConstruct.create('div',{className : 'muiPortalSimpleListFooter muiFontSizeS muiFontColorMuted newsListFooter'},linkNode);
			
			// 人员名称
			if(this.creator){
				var creatorNode = domConstruct.create('span',{className : 'muiPortalSimpleListName muiPortalCreatorName newsDocCreator'},footerNode);
				creatorNode.appendChild(domConstruct.create('i', {className:'item_icon item_icon_person'}));
				creatorNode.appendChild(domConstruct.create('span', {className:'item_list_person', innerHTML: this.creator}));
			}
			
			// 时间
			if(this.created){
				var createdNode = domConstruct.create('span',{className : 'muiPortalSimpleListCreated'},footerNode);
				createdNode.appendChild(domConstruct.create('i', {className:'item_icon item_icon_date'}));
				createdNode.appendChild(domConstruct.create('span', {className:'item_list_date', innerHTML: this.created}));
			}
			
			// 其他信息（如阅读量、点评数等）
			if(this.count){
				var modelNode = domConstruct.create('span',{className : 'muiPortalSimpleListCount'},footerNode);
				var numReg=/^[0-9]+.?[0-9]*$/;
				var countHtml = "";
				if(numReg.test(this.count)){
					var readNumNode = domConstruct.create('span',{className : 'muiPortalSimpleListReadNum muiPortalListViewsNum'},modelNode);
					readNumNode.innerText = this.count;
					var readNumTextNode = domConstruct.create('span',{className : 'muiPortalSimpleListReadViewText'},modelNode);
					readNumTextNode.innerText = viewsMsg['mui.item.views'];
				}else{
					domConstruct.create('span',{className : 'muiPortalSimpleListOther',innerHTML: this.count },modelNode);
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
