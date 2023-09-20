define([ "dojo/_base/declare", "dojo/text!./tmpl/pageViewer.jsp",
		"dojo/dom-attr", "dojo/dom-style", "dojo/dom-construct",
		"sys/attachment/mobile/viewer/base/BaseViewer",
		"sys/attachment/mobile/viewer/base/_PageViewerMixin", "mui/util" ],
		function(declare, tmpl, domAttr, domStyle, domConstruct, BaseViewer,
				_PageViewerMixin, util) {

			return declare("sys.attachment.mobile.pageviewer.PageViewer", [
					BaseViewer, _PageViewerMixin ], {
				templateString : tmpl,

				pageLoaded : function(pageNum,pageObj) {
					if (this.scaleStr == "false") {
						this.loadedMaxPageNum=pageNum;
						var _body;
						try {
							var frameDoc = pageObj.contentDocument
									|| pageObj.Document;
							_body = frameDoc.body;
						} catch (e) {
							//
						}
						domStyle.set(_body, {
							"background-color" : "white"
						});
						var size = {};
						size.w = _body.scrollWidth > 980 ? _body.scrollWidth
								: 980;
						size.h = _body.scrollHeight > 600 ? _body.scrollHeight
								: 600;
						pageObj.width=size.w + 8;
						pageObj.height=size.h;
						var pageWrapper=pageObj.parentNode;
						domStyle.set(pageWrapper,"width",size.w + 8 +"px");
						domStyle.set(pageWrapper,"height",size.h+"px");
						if(pageNum<this.preLoadPageNum&&pageNum+1<=this.getPageCount()){
							this.loadPage(pageNum+1);
						}
					} else {
						this.inherited(arguments);
					}
				}
			});
		});
