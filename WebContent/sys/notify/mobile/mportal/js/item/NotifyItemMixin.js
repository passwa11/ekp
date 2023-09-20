define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class", "dojo/dom-style", "dojo/dom-attr", 
         "dojo/on", "mui/dialog/Tip", "dojox/mobile/_ItemBase", "mui/util", "sys/mportal/mobile/OpenProxyMixin" ],
		function(declare, domConstruct, domClass, domStyle, domAttr, on, Tip, ItemBase, util, OpenProxyMixin) {
			
	var item = declare("sys.notify.mportal.NotifyItemMixin",[ ItemBase,OpenProxyMixin],{

		baseClass : "muiPortalNotifyItem",
		
		creator:'',//处理人
		icon : '',//处理人头像
		created : '',//时间
		label: '',//待办内容
		modelNameText : '',//相关模块
		href:'',

		buildRendering : function() {
			this.inherited(arguments);
			var linkNode = domConstruct.create('a',{className : 'muiPortalNotifyLink' },this.domNode);
			
			if(this.href){
				this.proxyClick(linkNode, this.href, '_blank',true);
			}else{
	        	// 添加锁定样式
	        	domClass.add(linkNode,'muiPortalNotifyLock');
	        	// 绑定点击弹出锁定tip提醒: “暂不支持移动访问”
	        	this.makeLockLinkTip(linkNode);
			}
			
			var titleNode = domConstruct.create('div',{className : 'muiPortalNotifyTitle'},linkNode);
			
			domConstruct.create('h2',{innerHTML:this.label,className : 'muiPortalNotifyLabel muiFontSizeM muiFontColorInfo'},titleNode);
			
			var baseFooterNode = domConstruct.create('div',{ className:'muiPortalNotifyBaseFooter muiFontColorMuted' },linkNode);
			// 人员头像图标
			if(this.icon){
				var personHeadIconNode = domConstruct.create('div',{className : 'muiPortalNotifyPersonHeadIcon'},baseFooterNode);
				domConstruct.create('img',{src :util.formatUrl(this.icon),className : 'muiPortalNotifyPersonHeadImg'},personHeadIconNode);
			}
			var footerTextInfoNode = domConstruct.create('div',{className : 'muiPortalNotifyBaseFooterTextInfo muiFontSizeS'},baseFooterNode);
			// 创建者
			if(this.creator){
				var creatorNode = domConstruct.create('span',{ innerHTML:this.creator, className:'muiPortalNotifyName' },footerTextInfoNode);
			}
			// 创建时间
			if(this.created){
				var createdNode = domConstruct.create('span',{ innerHTML:this.created, className:'muiPortalNotifyCreated' },footerTextInfoNode);
			}
			// 业务模块名称
			if(this.modelNameText){
				var modelNode = domConstruct.create('span',{ innerHTML:this.modelNameText, className:'muiPortalNotifyModel' },footerTextInfoNode);
			}
			
		},
		
		
	    makeLockLinkTip: function(linkNode) {
	       on(linkNode, "click", function(evt) {
	           Tip.tip({icon: "mui mui-warn", text: "暂不支持移动访问"});
	       });
	    },

		_setLabelAttr : function(label) {
			if (label)
				this._set("label", label);
		}
	});
	return item;
});
