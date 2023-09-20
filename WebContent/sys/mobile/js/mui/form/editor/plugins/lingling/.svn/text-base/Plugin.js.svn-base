define(
		[ "dojo/_base/declare", "dojo/dom-construct", "sys/mobile/js/lib/emoji/emoji",
				"dojo/dom-style", "dojo/_base/lang", "dojo/dom-attr",
				"dojo/dom-class", "dojo/text!../tmpl/panelV.html", "dojo/html",
				"mui/util", "dojo/topic",
				"mui/form/editor/plugins/EditorPluginBaseMixin",
				"mui/form/editor/plugins/lingling/SysFaceConfig",
				"dojo/query"],
		function(declare, domConstruct, emoji, domStyle, lang, domAttr,
				domClass, tmpl, html, util, topic, EditorPluginBaseMixin,faceCfg,query) {
			function getImgageId(html){
				var index = html.lastIndexOf("/");
				if(index == -1){
					return index;
				}
				return html.substring(index+1, html.indexOf("."));
			};
			
			return declare(
					"mui.form.editor.plugins.lingling.Plugin",
					[ EditorPluginBaseMixin ],
					{

						type : 'lingling',

						icon : 'mui-editor-lingling',

						iconImg: util.formatUrl('/resource/mobile/fix-f.png'),
						
						event : function(evt) {
							this._linglingShow(evt)
						},

						_faceIsInit : false,

						constructor : function(options) {
							this.inherited(arguments);
						},
						
						_linglingFormat: function(html){
							return html;
						},
						_linglingSaveHandler: function(html,editorObj){
							if(!html){
								return '';
							}
							var e = editorObj.__textNode;
							var textareaNode = query(".muiEditorTextarea");
							var imgs = query("img[data-type='lingling']", textareaNode[0]);
							imgs.forEach(function(item){
								var imgSrc = item.src;
								var outerHTML = item.outerHTML;
								var imgId = getImgageId(outerHTML);
								if(imgId != -1){
									var result = '[lingling'+imgId+']'; 
									html = html.replace(outerHTML, result);
								}
							});
							return html;
						},
						
						startup : function() {
							this.editor.formatContent.push(this._linglingFormat);
							this.editor.saveContentHandler.push(this._linglingSaveHandler);
							this.linglingCfg = faceCfg.getByType(this.type);
						},
						// 构建表情选择面板
						_linglingBuildPanel : function() {
							var facePanel = '';
							var index = this.linglingCfg.start;
							for(var j = this.linglingCfg.start;j<=this.linglingCfg.max;j++){
								facePanel += '<a href="javascript:;" class="muiEditorFaceItem"><img width="'+this.linglingCfg.width+'"  class="muiEditorFaceItemImg" src="'
									+this.linglingCfg.path+j+this.linglingCfg.suffix+ '" data-type="'+this.linglingCfg.type+'"></a>';
							}
							var dhs = new html._ContentSetter({
								parseContent : true,
								cleanContent : true,
								node : this.linglingNode,
								onBegin : function() {
									this.content = this.content.replace(
											/!{panel}/g, facePanel);
									this.inherited("onBegin", arguments);
								}
							});
							dhs.set(tmpl);
							dhs.tearDown();
						},

						show : function() {
							this.inherited(arguments);
							domStyle.set(this.linglingNode, {
								'display' : 'block'
							});
							this.defer(function() {
								domStyle.set(this.linglingNode, {
									'height' : this.panelHeight
								});
							}, 1);
						},

						hide : function() {
							this.inherited(arguments);
							domStyle.set(this.linglingNode, {
								'height' : 0
							});
							this.defer(function() {
								domStyle.set(this.linglingNode, {
									'display' : 'none'
								});
							}, 200);
						},

						_linglingShow : function(evt) {
							if (this._isShow) {
								this.hide();
								return;
							}

							this._isShow = true;

							if (this._falseIsInit) {
								this.show();
								return;
							}

							this.iconNode = evt.target;

							this.linglingNode = domConstruct.create('div', {
								className : 'muiEditorFace'
							}, this.editor.domNode, 'last');

							var t_h = this.editor.domNode.offsetHeight;
							this.panelHeight = '38rem';
							var self = this;
							this.connect(this.linglingNode,'click',lang.hitch(this,function(evt) {
										this.editor.__textNode.blur();
										if (!this._faceFireClick())
											return;
										var target = evt.target;
										var width =  self.linglingCfg["width"]/2;
										while (target) {
											if (target.tagName == 'IMG') {
												var src = domAttr.get(target,'src');
												var imgNode = domConstruct.create('img',{width: width,src : src,className : 'muiEditorLingLingImg','data-type' : self.type});
												this.editor.insertElement(imgNode);
												break;
											}
											target = target.parentNode;
										}
									}));
							this._linglingBuildPanel();
							this.show();
							this._falseIsInit = true;
						}
					});
		});