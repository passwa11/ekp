define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
		"mui/util", "sys/mportal/mobile/OpenProxyMixin","mui/i18n/i18n!sys-mportal:sysMportalPicDisplay.tip.num" ], 
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase, util, OpenProxyMixin, msg) {

	var item = declare("sys.mportal.CardItemMixin",
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
					this.buildSimpleRender();
				},

				buildSimpleRender : function() {
					
					this.domNode = domConstruct.create('li');
					
					var backrow = '';
					if(this.index >= 3)
						backrow = 'backrow';
					
					var colorNum = '';
					if (this.index < 3) {
						colorNum = 'mui_ekp_portal_new_knowledge_order_font' + (this.index + 1);
					}
					
					domConstruct.create('span', {
						className : colorNum + ' mui_ekp_portal_new_knowledge_order muiFontSizeL ' + backrow,
						innerHTML : this.index + 1
					}, this.domNode);

					if(this.href)
						this.proxyClick(this.domNode, this.href, '_blank');

					if (!this.label)
						this.label = this.docSubject;
					
					domConstruct.create('p', {
						className : 'lines-cut muiFontSizeM muiFontColorInfo',
						innerHTML : (this.status?'<span class="muiTitleLStatus muiFontSizeXS">' + this.status + '</span>':'') + this.label
					}, this.domNode);

                    var divInfo = domConstruct.create('div', {
                    	className : 'mui_ekp_portal_info muiFontColorMuted'
                    }, this.domNode);
                    
                    var right = domConstruct.create('ul', {
                    	className : 'left'
                    }, divInfo);

                    if (!this.creator)
						this.creator = this.docCreatorName;
                    
                    if (!this.created)
						this.created = this.docCreateTime;

                    if(this.creator){
	                    domConstruct.create('li', {
	                    	className : 'left creatorSpan',
	                    	innerHTML : '<span class="muiFontSizeS">' + this.creator + '</span>'
	                    }, right);
                    }
                    if (this.created) {
	                    domConstruct.create('li', {
	                    	className : 'left',
	                    	innerHTML : '<span class="muiFontSizeS">' + this.created + '</span>'
	                    }, right);
                    }
                    // 浏览（观看）人数
                    if (this.count) {
                    	if(this.count > 999)
                    		this.count = '999+';
                    	
						domConstruct.create('li', {
							className : 'left',
							innerHTML : '<span class="mui_ekp_portal_number muiFontSizeS">' + this.count + '</span><span class="muiFontSizeS">'+msg["sysMportalPicDisplay.tip.num.hasRead"]+'</span>'
						}, right);
					}
				},

				_setLabelAttr : function(label) {
					if (label)
						this._set("label", label);
				}
			});
	return item;
});

