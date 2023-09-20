define(
		[ "dojo/_base/declare", "dojo/dom-construct", 
				 "dijit/_WidgetBase",
				"mui/util", "mui/device/adapter"],
		function(declare, domConstruct, 
				widgetBase, util, adapter) {

			return declare(
					'sys.tag.SysTag',
					[ widgetBase ],
					{
						
						tag : "",
						
						modelName : "",
						
						tags : [],
						
						baseClass : "muiTagContainer",
						
						url : "/sys/ftsearch/mobile/index.jsp?keyword=!{keyword}&modelName=!{modelName}&searchFields=tag",
						
						buildRendering : function() {
							this.inherited(arguments);
							if(this.tag) {
								var tag = this.tag;
								this.tags = tag.split(";");
								if(this.tags && this.tags.length > 0) {
									for(var i = 0; i < this.tags.length; i ++) {
										var tdom = domConstruct.create("span" , {
											className  : "muiTag",
											"data-index" : i,
											innerHTML : util.formatText(this.tags[i])
										},this.domNode);
									}
								}
							}
						},
						
						

						startup : function() {
							this.inherited(arguments);
							this.connect(this.domNode, "onclick", 'tagClick');
						},
						
						
						
						tagClick :  function(evt) {
							var target = evt.target , index =  evt.target.getAttribute("data-index");
							if(index !== null && typeof(index) !== "undefined" && index >= 0) {
								var url = util.urlResolver(this.url, {
									keyword : encodeURIComponent(this.tags[index]),
									modelName : this.modelName || ""
								});
								
								adapter.open(util.formatUrl(url), "_blank");
							}
						}
					})
		});