define(	["dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
				"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
				"dojox/mobile/iconUtils", "mui/util", 
				"mui/list/item/_ListLinkItemMixin",
				"dojo/string","mui/i18n/i18n!km-forum:kmForumIndex"],
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,
				iconUtils, util, _ListLinkItemMixin, string,Msg) {
			var item = declare("km.forum.ForumSubCateItemMixin", [ItemBase,
							_ListLinkItemMixin], {

						tag : "li",
						
						buildRendering : function() {
							this._templated = !!this.templateString;
							if (!this._templated) {
								this.domNode = this.containerNode = this.srcNodeRef
										|| domConstruct.create(this.tag, {
											className : 'muiSNSGroupItem'
										});
								this.contentNode = domConstruct.create(
										'div', {
											className : 'muiSNSGroupListItem'
										}, this.domNode);
							}
							this.inherited(arguments);
							if (!this._templated)
								this.buildInternalRender();
						},

						buildInternalRender : function() {
							if(this.href){
								this.makeLinkNode(this.contentNode);
							}
							var iconNode = domConstruct.create("div" ,{
								className: "muiSNSGroupImgNode",
								innerHTML : "<img src=" + util.formatUrl(this.fdImageUrl) + ">"
							}, this.contentNode);
							var infoNode = domConstruct.create("div" ,{
								className: "muiSNSGroupInfoNode"
							}, this.contentNode);
							if (this.fdName) {
								this.hrefNode = domConstruct.create('h3', {
											className : 'textEllipsis muiSNSGroupSubject',
											innerHTML : this.fdName
										}, infoNode);
							}
							this.descNode = domConstruct.create('p', {
										innerHTML : this.fdDescription ? this.fdDescription : "",
										className : 'muiSNSGroupSummary'
									}, infoNode);
							domConstruct.create("div" , {
								className : 'muiSNSGroupDetail',
								innerHTML: '<span><i class="mui mui-posted"></i>'+Msg['kmForumIndex.postCount']+'<em>' 
											+ (this.topicCount ?this.topicCount : 0) +
											'</em></span><span><i class="mui mui-postedReply"></i>'+Msg['kmForumIndex.replyCount']+'<em>' +
											(this.replyCount ?this.replyCount : 0) + '</em></span>'
							},infoNode);
							var forwardNode = domConstruct.create("div" ,{
								className: "muiSNSGroupForwardNode"
							}, this.contentNode);
							domConstruct.create("i" , {
								className: "mui mui-forward"
							}, forwardNode);
							if(this.fdRefUrl) {
								this.href = this.fdRefUrl;
							}
						},

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
