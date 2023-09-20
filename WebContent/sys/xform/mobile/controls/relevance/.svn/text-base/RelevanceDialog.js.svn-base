define([ "dojo/_base/declare", "dojo/topic", "dojo/dom", "dojo/dom-construct", "dojo/dom-style",
          "dojo/_base/lang", "dojo/html", "dojo/_base/array", "mui/util", "dojo/touch", "dojox/mobile/_css3", "dijit/registry","dojo/dom-class", "dijit/_WidgetBase"],
		function(declare, topic, dom, domConstruct, domStyle, lang, html, array, util, touch, css3, registry, domClass, WidgetBase) {
			var rDialog = declare("sys.xform.mobile.controls.relevance.RelevanceDialog", [WidgetBase], {
				
				//模板地址
				templURL : "sys/xform/mobile/controls/relevance/relevance_mul.jsp",
				
				key : null,
				
				_dialogDivPrefix: '__cate_dialog_',
				
				_init: function(config) {
					topic.subscribe("/sys/xform/relevance/main/submit",lang.hitch(this,"returnDialog"));
					this.key = config.key;
					this.fdControlId = config.fdControlId;
					this.extendXmlPath = config.extendXmlPath;
					this.fdMainModelName = config.fdMainModelName;
					this.curIds = config.curIds;
					this.curSubjects = config.curSubjects;
					this.curFdModelNames = config.curFdModelNames;
					this.curIsCreators = config.curIsCreators;
					this.isUseNew = config.isUseNew;
					this.inputParams = config.inputParams;
					this.isMul = config.isMul;
					this.fdKey = config.fdKey;
					this.fdId = config.fdId;
					this.fdTemplateModelName = config.fdTemplateModelName;
					this.isBase = config.isBase;
					this.isSimpleCategory = config.isSimpleCategory;
					if(!this.isMul){
						this.templURL = "sys/xform/mobile/controls/relevance/relevance_sgl.jsp";
					}
				},
				
				returnDialog : function(srcObj , evt){
					if(evt){
						if(srcObj.key == this.key){
							topic.publish("/sys/xform/relevance/setvalue",this,evt);
							this.closeDialog(srcObj);
						}
					}
				},
				
				showAnimate :function() {
					domClass.add(this.dialogDiv,'fadeIn animated');
					domStyle.set(this.dialogDiv, 'display','block');
					domStyle.set(this.dialogDiv, 'background-color','rgba(0, 0, 0, 0.6)');
					domClass.add(this.dialogContainerDiv, 'fadeInRight animated');
					domStyle.set(this.dialogContainerDiv, 'display','block');
					var self = this;
					setTimeout(function(){
						//移除动画类名
						domClass.remove(self.dialogDiv, "fadeIn");
						domClass.remove(self.dialogContainerDiv, "fadeInRight");
					}, 500);
				},
				
				closeDialog : function(srcObj){
					if(this.dialogDiv && srcObj.key == this.key){
						domStyle.set(this.dialogDiv, css3.name('transform'),'translate3d(100%, 0, 0)');
						var _self = this;
						setTimeout(function(){
							if(_self.parseResults && _self.parseResults.length){
								array.forEach(_self.parseResults, function(w){
									if(w.destroy){
										w.destroy();
									}
								});
								delete _self.parseResults;
							}
							domConstruct.destroy(_self.dialogDiv);
							_self.dialogDiv = null;
							_self._working = false;
						},410);
					}
				},
				
				initSelData:function(){
					// 初始化搜索框
//					var searchListWgt = registry.byId('_eventdata_sgl_search_list_' + this.key);
//					if(searchListWgt){
//						searchListWgt.initSearchBars(this.argu,this.dataSource);	
//					}
					// 初始化选项数据
					//var dataWgt = registry.byId('_eventdata_sgl_list_' + this.key);
					//dataWgt.setData(this.dataSource);
					//dataWgt.setArgu(this.argu);
				},
				
				_closeDialog:function(evt){ 
					var target = evt.target;
					if(this.dialogDiv && target === this.dialogDiv){
						this.__doCloseDialog();
					}
				},
				
				__doCloseDialog:function(){
					domClass.add(this.dialogContainerDiv,'fadeOutRight animated');
					domClass.add(this.dialogDiv,'fadeOut animated');
					
					this.defer(function(){
						if(this.dialogContainerDiv){
							domStyle.set(this.dialogContainerDiv, 'display','none');
						}
						if(this.dialogDiv){
							domStyle.set(this.dialogDiv, 'display','none');
						}
					}, 500);
					
					setTimeout(lang.hitch(this,function(){
						if(this.parseResults && this.parseResults.length){
							array.forEach(this.parseResults, function(w){
								if(w.destroy){
									w.destroy();
								}
							});
							delete this.parseResults;
						}
						domConstruct.destroy(this.dialogDiv);
						this.dialogDiv = null;
						this._working = false;
					}),410);
				},
				
				select:function(config) {
					this._init(config);
					if(this.templURL && !this._working){
						var dialogId = this._dialogDivPrefix + this.key;
						this._working = true;
						this.dialogDiv = dom.byId(dialogId);
						if(this.dialogDiv == null){
							var _self = this;
							require(["dojo/text!" + util.urlResolver(this.templURL , this)], function(tmplStr){
								_self.dialogDiv = domConstruct.create("div" ,{id:dialogId, className:'muiCateDiaglog',"tabindex":"0"},document.body,'last');
								_self.dialogContainerDiv = domConstruct.create("div" ,{ className:'muiCateDiaglogContainer '},_self.dialogDiv);
								_self.dialogDiv.focus();
								_self.defer(function(){
									_self.connect(_self.dialogDiv,'click', '_closeDialog');
								},500);
								util.disableTouch(_self.dialogDiv , touch.move);
								var dhs = new html._ContentSetter({
									node:_self.dialogContainerDiv,
									parseContent : true,
									cleanContent : true,
									onBegin : function() {
										this.content = lang.replace(this.content,{argu:_self});
										this.inherited("onBegin",arguments);
									}
								});
								dhs.set(tmplStr);
								dhs.parseDeferred.then(function(results) {
									_self.parseResults = results;
									domStyle.set(_self.dialogDiv, css3.name('transform'),'translate3d(0, 0, 0)');
									_self.initSelData();
								});
								dhs.tearDown();
								_self.showAnimate();
							});
						}
					}
				}
			});
			
			return rDialog;
});