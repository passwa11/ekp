define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
		"mui/util", "sys/mportal/mobile/OpenProxyMixin", "mui/i18n/i18n!sys-mportal:sysMportalPicDisplay.tip.num" ], 
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase, util, OpenProxyMixin, msg) {

	var item = declare("sys.mportal.CardItemFirstRowImgMixin",
			[ ItemBase, OpenProxyMixin ], {

				baseClass : "muiPortalCardItem",

				label : "",

				href : "",

				icon : "",

				count : "",

				created : "",

				creator : "",

				docSubject : "",

				docCreateTime : "",

				docCreatorName : "",

				buildRendering : function() {
					this.inherited(arguments);
					this.domNode = domConstruct.create('li');
					// 绑定点击事件
					if(this.href){
					   this.proxyClick(this.domNode, this.href, '_blank');
					}
					
					// 第一行数据构建显示图片的DOM载体
					if(this.index==0){
						var imgContainerNode = domConstruct.create('div', { className : 'mui_ekp_portal_new_knowledge_row1_img_container' }, this.domNode);
						domStyle.set(imgContainerNode, {
							'background-image' : 'url(' + util.formatUrl(this.icon) + ')'
						});
					}
					// 构建基本信息显示的DOM载体
					var infoContainerNode = domConstruct.create('div', { className : 'mui_ekp_portal_new_knowledge_info_container' }, this.domNode);
					
					
					var firstRowInfoNode = domConstruct.create('div', { className : 'firstRow' }, infoContainerNode);
					// 标题
					var titleNode = domConstruct.create('span', { className : 'muiFontSizeM muiFontColorInfo lines-cut' }, firstRowInfoNode);
					var statusTagHtml = this.status ? '<span class="muiTitleLStatus muiFontSizeXS">' + this.status + '</span>': ""; // 状态标签HTML(精华)
					if (!this.label) this.label = this.docSubject;  // 标题
					titleNode.innerHTML = statusTagHtml+this.label;
					
					var secondRowInfoNode = domConstruct.create('div', { className : 'secondRow muiFontColorMuted' }, infoContainerNode);
					// 创建人
					if(this.creator){
						var creatorNode = domConstruct.create('div', { className : 'muiFontSizeS' }, secondRowInfoNode);
						creatorNode.innerText = this.creator;
					}			
					// 创建时间
					if(this.created){
						var createdNode = domConstruct.create('div', { className : 'muiFontSizeS' }, secondRowInfoNode);
						createdNode.innerText = this.created;
					}
					
					// 显示阅读数量( “N” 观看 )
			        if (this.count) {
			            this.countNode = domConstruct.create("div", { className:"muiFontSizeS", innerHTML:"<span class='muiComplexlReadNum'>"+this.count+"</span><span class='muiComplexlReadViewText'>"+msg["sysMportalPicDisplay.tip.num.hasRead"]+"</span>" } ,secondRowInfoNode);
			        }
					
				},

				_setLabelAttr : function(label) {
					if (label)
						this._set("label", label);
				}
			});
	return item;
});
