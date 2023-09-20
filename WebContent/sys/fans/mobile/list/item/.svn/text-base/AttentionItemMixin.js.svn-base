define(
		[ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
				"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
				"mui/util", "mui/list/item/_ListLinkItemMixin", "dojo/string",
				 "dojo/_base/window",
				 "mui/i18n/i18n!sys-fans:mui","mui/i18n/i18n!sys-fans:sysFansMain.dept","mui/dialog/Tip","dojo/request"],
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,
				util, _ListLinkItemMixin, string, window,fansMsg,deptMsg,Tip,request) {
			var item = declare(
					"sys.zone.item.fans",
					[ ItemBase],
					{

						tag : "li",

						buildRendering : function() {
							this.domNode = this.containerNode = this.srcNodeRef
											|| domConstruct.create(this.tag, {
												className : 'muiMixContentItem'
											});
							this.contentNode = domConstruct.create('a', null, this.domNode);
							
							this.connect(this.contentNode , 'click', "onLinkClick");
							
							this.inherited(arguments);
							if (!this._templated)
								this.buildInternalRender();
						},

						buildInternalRender : function() {
							
							var infoNode = domConstruct.create('div', {
								className : 'muiExpertNode'
							}, this.contentNode);
							// 头像节点
							this.imageNode = domConstruct.create('div', {
								innerHTML : string.substitute('<span style="height: 100%;width: 100%;border-radius: 50%;display:inline-block;background:url(${d}) center center no-repeat; background-size: cover;" ></span>', {
									d : util.formatUrl(this.src)
								}),
								className : 'muiExpertIcon'
							}, infoNode);
							
							//右边节点
							this.detailNode = domConstruct.create('ul', {
								className : 'muiExpertDetail'
							}, infoNode);
							
							domConstruct.create("li",{
								className : 'muiExpertName muiFontSizeMS muiFontColorInfo',
								innerHTML : this.name
							}, this.detailNode);
							
							
							if(this.name) {
								domConstruct.create("li",{
									className : 'muiExpertDep textEllipsis muiFontColorMuted muiFontSizeSS',
									innerHTML : deptMsg['sysFansMain.dept'] + '：' + this.fdDept
								}, this.detailNode);
							}
							
							this.askBtn = domConstruct.create("a",{
								className : 'muiExpertAskBtn',
								innerHTML : fansMsg['mui.sysFansMain.4m.cancelText'],
								href : "javascript:;"
							}, infoNode);
							
							this.connect(this.askBtn, "click" , this.onAskClick);
							
						},
						
						onAskClick : function(evt) {
							if(evt) {
								if (evt.stopPropagation)
									evt.stopPropagation();
								if (evt.cancelBubble)
									evt.cancelBubble = true;
								if (evt.preventDefault)
									evt.preventDefault();
								if (evt.returnValue)
									evt.returnValue = false;
							}
							var url = util.formatUrl('/sys/fans/sys_fans_main/sysFansMain.do?method=cancelAtt&fdPersonId='+this.fdModelId
									);
							var promise = request.post(url, {
								data : null,
								timeout : 100000,
								headers : {
									'Accept' : 'application/json'
								},
								handleAs : 'json'
							});
							
							promise.then(function(data) {
								
								var _flag = data.flag;
								if (_flag) {
									
									Tip.success({
										 text : fansMsg['mui.sysFansMain.4m.cancel']
										});
									setTimeout(function() {
										location.href=location.href;  
									}, 800);
										
									
								}
								else if(!_flag){
									
									Tip.success({
									 text : fansMsg['mui.sysFansMain.4m.cancel.fail']
									});
								}
								else {
									
									Tip.success({
										 text : fansMsg['mui.sysFansMain.4m.cancel.fail']
									});
								}
							});
							
						},
						
						makeUrl: function() {
							var url = util.formatUrl(this.href);
							if (url.indexOf('?') > -1) {
								url += '&_mobile=1';
							} else {
								url += '?_mobile=1';
							}
							return url;
						},
						onLinkClick : function() {
							this.set("selected", true);
							var url = this.makeUrl();
							this.defer(function(){
								this.set("selected", false);
							}, 100);
							window.global.open(url, "_self");
						},
						
						startup : function() {
							if (this._started)
								return;
							this.inherited(arguments);
						},
	
						_setLabelAttr : function(text) {
							if (text)
								this._set("label", text);
						},
						
						_selClass: "mblListItemSelected",
						
						_setSelectedAttr: function(selected){
							this.inherited(arguments);
							domClass.toggle(this.domNode, this._selClass, selected);
						}
					});
			return item;
		});