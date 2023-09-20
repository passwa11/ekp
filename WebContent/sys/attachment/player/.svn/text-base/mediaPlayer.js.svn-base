define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var env = require('lui/util/env');
	var str = require("lui/util/str");
	var base = require("lui/base");
	var source = require("lui/data/source");
	var render = require("lui/view/render");
	var layout = require('lui/view/layout');
	
	require("sys/attachment/player/css/mediaPlay.css");
	
	var FileValue = require('./fileValue');
	var filePlay = require('./filePlay');
	var fileHeader = require('./fileHeader');
	var fileThumb = require('./fileThumb');
	
	var FILE_READ = window.File_EXT_READ ?  (window.File_EXT_READ + ";.pdf") : ".doc;.xls;.ppt;.xlc;.docx;.xlsx;.mppx;.pptx;.xlcx;.wps;.et;.pdf";
	var mediaPlayer = base.Container
			.extend({

				initProps : function($super, cfg) {
					$super(cfg);
					this.url = cfg.url;
					this.modelName = cfg.modelName || "";
					this.modelId = cfg.modelId || "";
					this.attObj = cfg.attObj; 
					this.isSupportDirect = cfg.isSupportDirect || "";
					this.fileList = this.attObj ? this.attObj.fileList : [];
					this.url = "/sys/attachment/sys_att_main/sysAttMain.do?method=buildHtmlComplete&fdModelId=!{modelId}&fdModelName=!{modelName}";
					this.isAsposeView = cfg.isAsposeView;
				},
				startup : function() {
					if (this.isStartup) {
						return;
					}

					if (!this.source) {
						
						this.url = str.variableResolver(this.url , {
							modelId : this.modelId,
							modelName : this.modelName
						})
						
						var _source = new source.AjaxJson({
							url : this.url,
							parent : this
						});
						this.source = _source;
						this.source.startup();
						this.source.get();
						this.source.on('data', this._startup, this);
					}
				},
				
				_startup : function(evt) {
					var fdAttHtmlIds = evt.fdAttHtmlIds ? evt.fdAttHtmlIds : "";
					
					if(!this.value) {
						this.value = new FileValue({
							fileList : this.fileList,
							completeIds : fdAttHtmlIds
						});
						
						this.value.startup();
					}
					
					
					if(!this.value || this.value.data.length <= 0) {
						this.isStartup = true;
						this.isDrawed = true;
						return;
					}
					
					if(!this.layout) {
						this.layout = new layout.Template({
							src : require.resolve('./tmpl/mediaPlay.jsp#'),
							parent : this
						});
						this.layout.startup();
						this.children.push(this.layout);
					}
					
					if(!this.play) {
						this.play = new filePlay( {
							parent : this,
							value : this.value,
							isSupportDirect : this.isSupportDirect
						});
						
						this.play.startup();
						this.children.push(this.play);
					}
					
					if(!this.header) {
						this.header = new fileHeader( {
							parent : this,
							value : this.value,
							isAsposeView : this.isAsposeView
						});
						
						this.header.startup();
						this.children.push(this.header);
					}
					
					if(!this.thumb) {
						this.thumb = new fileThumb({
							value : this.value,
							parent : this
						});
						this.thumb.startup();
						this.children.push(this.thumb);
					}
					this.isStartup = true;
					this.lazyDraw = false;
					this.draw();
				},

				doLayout : function(obj) {
					this.element.append($(obj));
					this.playNode = this.element.find("[data-lui-mark='play']");
					this.headerNode = this.element.find("[data-lui-mark='header']");
					this.thumbNode = this.element.find("[data-lui-mark='thumb']");
					for (var i = 0; i < this.children.length; i++) {
						var child = this.children[i];
						if (child instanceof filePlay)
							child.setParentNode(this.playNode);
						if (child instanceof fileHeader)
							child.setParentNode(this.headerNode);
						if (child instanceof fileThumb) {
							child.setParentNode(this.thumbNode);
							continue;
						}
						if (child.draw)
							child.draw();
					}
				},
				
				draw :function(){
					if(this.isDrawed)
						return this;
					if(this.lazyDraw == true)
						return this;
					var self = this;
					if(this.layout){
						this.layout.on("error",function(msg){
							self.element.append(msg);
						});
						this.layout.get(this,function(obj){
							 self.doLayout(obj);
						});
					} else {
						for ( var i = 0; i < this.children.length; i++) {
							if(this.children[i].draw)
								this.children[i].draw();
						}
					}
					this.element.show();
					this.isDrawed = true;
					return this;
				}

				
			});

	module.exports = mediaPlayer;
});