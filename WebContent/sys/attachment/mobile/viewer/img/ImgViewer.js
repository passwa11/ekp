define(
		[ "dojo/_base/declare", "dojo/text!./tmpl/imgViewer.jsp",
				"sys/attachment/mobile/viewer/base/BaseViewer",
				"dojo/dom-attr", "dojo/dom-construct", "mui/util",
				"dojo/dom-style", "dojo/touch" ],
		function(declare, tmpl, BaseViewer, domAttr, domConstruct, util,
				domStyle, touch) {

			return declare(
					"sys.attachment.ImgViewer",
					[ BaseViewer ],	{
						templateString : tmpl,
						fdId : '',
						buildRendering : function() {
							this.inherited(arguments);
							domAttr.set(this.pageImg,'src',
											util.formatUrl('/third/pda/attdownload.jsp?open=1&fdId='+ this.fdId));
							var w_h = util.getScreenSize();
							domStyle.set(this.pageContent, {
								height : w_h.h + 'px',
								'line-height' : w_h.h + 'px'
							});
							//domAttr.set(this.pageImg,'width',w_h.w);
							//domAttr.set(this.pageImg,'height',w_h.h);
						}
					});
		});
