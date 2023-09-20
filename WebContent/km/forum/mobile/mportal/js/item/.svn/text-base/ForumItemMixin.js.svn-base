define(["dojo/_base/declare",
		"dojox/mobile/_ItemBase",
		"dojo/dom-construct",
		"dojo/dom-class",
		"dojo/dom-attr",
		"mui/util",
		"mui/i18n/i18n!sys-mobile",
		"sys/mportal/mobile/OpenProxyMixin"],
		function(declare,_ItemBase,domConstruct,domClass,domAttr,util,msg,OpenProxyMixin){
	
	var item = declare("km.forum.mportal.ForumItemMixin", [ _ItemBase,OpenProxyMixin ], {
		
		baseClass : 'muiPortalForumItem',

		icon : '',//发帖者头像
		creator : '',//发帖者
		created : '',//发帖时间
		count : '0',//查看数
		replay : '0',//回帖数
		label : '',//主题
		summary : '',//摘要
		href : '',//链接
		
		buildRendering : function() {
			this.inherited(arguments);
			var linkNode = domConstruct.create('a',{className : 'muiPortalForumLink'},this.domNode),
				barNode = domConstruct.create('div',{className : 'muiPortalForumBar'},linkNode),
				titleNode = domConstruct.create('h2',{className : 'muiPortalForumTitle',innerHTML:this.label },linkNode);
			var barLeftNode = domConstruct.create('div',{className : 'muiPortalForumBarLeft'},barNode),
				barRightNode = domConstruct.create('div',{className : 'muiPortalForumBarRight'},barNode);
			this.proxyClick(linkNode, this.href, '_blank');
			//domAttr.set(linkNode,'href',util.formatUrl(this.href));
			if(this.creator){
				var creatorNode = domConstruct.create('span',{className : 'muiPortalForumCreator'},barLeftNode);
				domConstruct.create('img',{className : 'muiPortalForumCreatorImg',src : util.formatUrl(this.icon) },creatorNode);
				domConstruct.create('span',{className : 'muiPortalForumCreatorText',innerHTML : this.creator },creatorNode);
			}
			if(this.created){
				domConstruct.create('span',{className : 'muiPortalForumDate',innerHTML:this.created },barLeftNode);
			}
			//查看数
			var countNode = domConstruct.create('span',{className : 'muiPortalForumCount'},barRightNode);
			domConstruct.create('i',{className : 'mui mui-eyes'},countNode);
			domConstruct.create('span',{className : 'muiPortalForumCountText',innerHTML:this.count},countNode);
			//回帖数
			var replyNode = domConstruct.create('span',{className : 'muiPortalForumReply' },barRightNode);
			domConstruct.create('i',{className : 'mui mui-msg'},replyNode);
			domConstruct.create('span',{className : 'muiPortalForumReplyText',innerHTML:this.replay},replyNode);
			
			if(this.summary){
				domConstruct.create('p',{className : 'muiPortalForumSummary',innerHTML:this.summary},linkNode);
			}
		},
		
		_setLabelAttr : function(label) {
			if (label)
				this._set("label", label);
		}
		
	});
	
	return item;
	
});